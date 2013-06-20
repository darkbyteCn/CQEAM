package com.sino.pda;//package pda;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.base.web.request.upload.UploadFile;
import com.sino.base.web.request.upload.UploadFileSaver;
import com.sino.base.web.request.upload.UploadRow;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.pda.util.XmlUtil;
import org.jdom.Element;

/**
 * User: zhoujs
 * Date: 2008-1-23
 * Time: 9:21:41
 * Function:签收工单
 */
public class SignWorkOrder extends BaseServlet {
    private String conFilePath = "";
    private static final String CONTENT_TYPE = "text/xml; charset=GBK";

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        Connection conn = null;
        res.setContentType(CONTENT_TYPE);
        PrintWriter resout = res.getWriter();

        String sFile = "";
        String m_submitUser = "";
        String test = "";

        boolean successFlag;
        RequestParser reqPar = null;
        UploadRow uploadRow;
        UploadFile[] upFiles = null;
        try {
            conn = DBManager.getDBConnection();
            boolean autoCommit = conn.getAutoCommit();

            reqPar = new RequestParser();
            reqPar.transData(req);
			test= StrUtil.nullToString(reqPar.getParameter("test"));

			Logger.logInfo("PDA run SignWorkOrder servlet begin....");
			if (test.compareTo("Y") == 0) {
				resout.println("<?xml version=\"1.0\" encoding=\"GB2312\" ?> ");
			} else {
				resout.println("<?xml version=\"1.0\" ?> ");
			}
			resout.println("<SignWorkOrder>");
            conFilePath = PDAUtil.getCurUploadFilePath(conn);
            UploadFileSaver uploadFileSaver = reqPar.getFileSaver();
            uploadFileSaver.saveFiles(conFilePath);

            int k = uploadFileSaver.getFileCount();
            uploadRow = uploadFileSaver.getRow();
            upFiles = uploadRow.getFiles();
            boolean b_returnFlag = false;


//            upFiles = reqPar.getFiles();

            if (upFiles == null) {
                Logger.logError("upload file " + SyBaseSQLUtil.isNull() + " ");
                b_returnFlag = true;
                setResultValue(resout, false, "upload file " + SyBaseSQLUtil.isNull() + " ", "");
            } else {
                if (upFiles.length == 1) {
                    String m_SEPARATOR = System.getProperty("file.separator");
                    if (m_SEPARATOR.compareTo("/") == 0) {
                        conFilePath = PDAUtil.getCurUploadFilePath(conn) + "/";
                    } else {
                        conFilePath = PDAUtil.getCurUploadFilePath(conn) + "\\";
                    }


                    java.io.File dir = new java.io.File(conFilePath);
                    if (!dir.exists()) {
                        dir.mkdir();
                    }
                    //zzj add 20060925 14:21 /////////////////////////////
                    if (upFiles[0].getFileName().equals("")) {
                        Logger.logError("can't find upload xml files");
                        b_returnFlag = true;
                        setResultValue(resout, false, "can't find upload xml files,please check PDA prog or GPRS", "");
                    } else {

//                        reqPar.saveFiles(conFilePath);
                    }
                    ///////////////////////////////////////////////////
                } else {
                    setResultValue(resout, false, "can't find any xml file", "");
                    b_returnFlag = true;
                }
            }


            if (!b_returnFlag) {

                int nSuccess = 999;
                successFlag = true;
                sFile = upFiles[0].getAbsolutePath();

                //读取xml信息
                XmlUtil xml = new XmlUtil();
                if (!xml.loadXmlFile(sFile)) {
                    ClearBeforeCancel(resout, conFilePath, false, "load xml file error!");
                    return;
                }

                List xmlAllWOs = xml.getAllRootChildren();

                int nChildNum = xmlAllWOs.size();
                if (nChildNum < 1) {
                    ClearBeforeCancel(resout, conFilePath, false, "can't get XML file child");
                    return;
                }

                String m_create_user = "", workorderNo = "";

                //获取<workorders  signuser ="sysadmin">	，root attribute
                String m = xml.getNthListName(xmlAllWOs, 0);
                m_create_user = xml.getElementAttrValue(xml.getRootElement(), "signuser");

                SfUserDTO userAccount = PDAUtil.getUserInfo(conn, m_create_user);
                int m_userid = userAccount.getUserId();
                int m_user_org_id = userAccount.getOrganizationId();
                //String m_user_group_id=userinfo.mobile;

                if (m_userid==0) {
                    ClearBeforeCancel(resout, conFilePath, false, "can't get userinfo:" + m_create_user);
                    return;
                }

                int nItemNum = xmlAllWOs.size();

                conn.setAutoCommit(false);
                for (int j = 0; j < nItemNum; j++) {
                    Element xmlWO = xml.getNthElement(xmlAllWOs, j);
                    workorderNo = xml.getElementAttrValue(xmlWO, "id");

                    //检测工单目前状态
                    String implentUser = hasImplentUser(conn, workorderNo);
                    if (!implentUser.equals("")) {
                        String tt = "当前工单已经被其他人签收过了[" + implentUser + "]";
                        ClearBeforeCancel(resout, conFilePath, false, tt);
                        return;
                    }

                    //更新工单信息
                    successFlag = updateOrderStatus(conn, m_userid, workorderNo);
                    if (!successFlag) {
                        break;
                    }

                }

                if (!successFlag) {
                    conn.rollback();
                    conn.setAutoCommit(autoCommit);
                    ClearBeforeCancel(resout, conFilePath, false, "签收工单失败");
                    return;
                }

                conn.commit();
                conn.setAutoCommit(autoCommit);
                PDAUtil.clearDir(conFilePath);
                setResultValue(resout, true, "", "");


            } else {
                ClearBeforeCancel(resout, conFilePath, false, "can't find xml file");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (QueryException e) {
            e.printStackTrace();
        } catch (PoolException e) {
            e.printStackTrace();
        } catch (UploadException e) {
            e.printStackTrace();
        } catch (ContainerException e) {
            e.printStackTrace();
        } catch (FileSizeException e) {
            e.printStackTrace();
        } finally {
            DBManager.closeDBConnection(conn);
        }
    }

    private void ClearBeforeCancel(PrintWriter resout, String conFilePath, boolean flag, String msg) {
        setResultValue(resout, flag, msg, "");
        PDAUtil.clearDir(conFilePath);
    }


    /**
     * 查某工单的执行人
     *
     * @param conn        数据库连接
     * @param workorderNo 工单号
     * @return 执行人
     */
    private String hasImplentUser(Connection conn, String workorderNo) {

        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT EW.IMPLEMENT_BY FROM ETS_WORKORDER EW WHERE EW.WORKORDER_NO = ?";
        sqlArgs.add(workorderNo);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        String implentUser = "";

        try {
            SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
            simpleQuery.executeQuery();
            if (simpleQuery.hasResult()) {
                implentUser = simpleQuery.getFirstRow().getStrValue("IMPLEMENT_BY");
            }
        } catch (QueryException e) {
            Logger.logError(e);
        } catch (ContainerException e) {
            Logger.logError(e);
        }
        return implentUser;
    }

    /**
     * 更新工单状态、执行人
     *
     * @param conn        数据库连接
     * @param userId      执行人
     * @param workorderNo 工单号
     * @return 更新是否成功
     */
    private boolean updateOrderStatus(Connection conn, int userId, String workorderNo) {
        boolean flag = false;
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE ETS_WORKORDER\n" +
                "   SET IMPLEMENT_BY = ?, WORKORDER_FLAG = 12\n" +
                " WHERE WORKORDER_NO = ?";

        sqlArgs.add(userId);
        sqlArgs.add(workorderNo);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        try {
            flag = DBOperator.updateRecord(sqlModel, conn);
        } catch (DataHandleException e) {
            Logger.logError(e);
        }
        return flag;
    }

    private void setResultValue(PrintWriter out, boolean b_flag, String errMsg, String m_workorder_no) {
        out.println("<result message=\"" + errMsg + "\" >"
                + String.valueOf(b_flag)
                + "</result>");
        out.println("</SignWorkOrder>");
        out.close();
    }
}
