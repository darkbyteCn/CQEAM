package com.sino.pda;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.base.web.request.upload.UploadFile;
import com.sino.base.web.request.upload.UploadFileSaver;
import com.sino.base.web.request.upload.UploadRow;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.pda.util.XmlUtil;
import org.jdom.Element;

/**
 * User: zhoujs
 * Date: 2008-1-16
 * Time: 16:06:05
 * Function:
 */
public class AssignWorkOrders extends BaseServlet {
    private String conFilePath = "";
    private static final String CONTENT_TYPE = "text/xml; charset=GBK";
    private static Vector ORDER_I = new Vector();
    private static Vector ORDER_II = new Vector();
    private static Vector ORDER_III = new Vector();
    private static Vector ORDER_IV = new Vector();

    static {
        ORDER_I.add("巡检");
        ORDER_I.add("交接");
        ORDER_II.add("盘点");
        ORDER_III.add("抽查");
        ORDER_IV.add("紧急调拨");
    }

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        res.setContentType(CONTENT_TYPE);
        PrintWriter resout = res.getWriter();

        String sFile = "";
        String m_submitUser = "";
        String test = "";

        Logger.logInfo("PDA run AssignWorkOrders servlet begin....");
        Connection conn = null;
        boolean autoCommit = true;
        boolean operatorFlag = false;
        UploadFile[] upFiles = null;
        UploadRow uploadRow;

        RequestParser reqPar = new RequestParser();
        try {
            conn = DBManager.getDBConnection();
            autoCommit = conn.getAutoCommit();
            conFilePath = PDAUtil.getCurUploadFilePath(conn);
            Logger.logInfo("....................");

            reqPar.transData(req);
            UploadFileSaver uploadFileSaver = reqPar.getFileSaver();
            uploadFileSaver.saveFiles(conFilePath);

            uploadRow = uploadFileSaver.getRow();
            upFiles = uploadRow.getFiles();

            test = StrUtil.nullToString(reqPar.getParameter("test"));
        } catch (Exception e1) {
            resout.println(e1.toString());
            return;
        }

        if (test.compareTo("Y") == 0) {
            resout.println("<?xml version=\"1.0\" encoding=\"GB2312\" ?> ");
        } else {
            resout.println("<?xml version=\"1.0\" ?> ");
        }
        resout.println("<AssignWorkOrders>");

        boolean b_returnFlag = false;


        try {

            if (upFiles == null) {
                Logger.logInfo("upload file " + SyBaseSQLUtil.isNull() + " ");
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

                    File dir = new File(conFilePath);
                    if (!dir.exists()) {
                        dir.mkdir();
                    }
                } else {
                    setResultValue(resout, false, "can't find any xml file", "");
                    b_returnFlag = true;
                }
            }


            if (!b_returnFlag) {

                int nSuccess = 999;
                try {
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

                    String m_fromuser = "";
                    String m_targetuser = "";
                    String workorderNo = "";
                    String workorderType = "";

                    //获取<workorder  type="巡检工单">	，root attribute
                    String m = xml.getNthListName(xmlAllWOs, 0);
                    m_fromuser = xml.getElementAttrValue(xml.getRootElement(), "fromuser");
                    m_targetuser = xml.getElementAttrValue(xml.getRootElement(), "targetuser");


                    if (StrUtil.isEmpty(m_fromuser)) {
                        ClearBeforeCancel(resout, conFilePath, false, "输入参数错误: can't get from user");
                        return;
                    }

                    if (StrUtil.isEmpty(m_targetuser)) {
                        ClearBeforeCancel(resout, conFilePath, false, "输入参数错误:  can't get target user");
                        return;
                    }

                    //get userid by user loginname
                    SfUserDTO sfUser = PDAUtil.getUserInfo(conn, m_fromuser);
                    int m_fromuserid = sfUser.getUserId();

                    if (StrUtil.isEmpty(m_fromuserid)) {
                        ClearBeforeCancel(resout, conFilePath, false, "can't get sfUser:" + m_fromuser);
                        return;
                    }

                    sfUser = PDAUtil.getUserInfo(conn, m_targetuser);
                    int m_targetuserid = sfUser.getUserId();

                    if (StrUtil.isEmpty(m_targetuserid)) {
                        ClearBeforeCancel(resout, conFilePath, false, "can't get sfUser:" + m_targetuser);
                        return;
                    }

                    //update all wo


                    conn.setAutoCommit(false);

                    int nItemNum = xmlAllWOs.size();
                    String errMsg = "";
                    for (int j = 0; j < nItemNum; j++) {
                        Element xmlWO = xml.getNthElement(xmlAllWOs, j);
                        workorderNo = xml.getElementAttrValue(xmlWO, "id");
                        workorderType = xml.getElementAttrValue(xmlWO, "type");

                        //检测工单状态是否已接收下载
                        boolean isExists = chkWorkOrderStatus(conn, workorderType, workorderNo, m_fromuserid);

                        if (!isExists) {
                            errMsg = "没有找到工单 [工单号：" + workorderNo + ",执行人:" + m_fromuser + "] 或 当前工单已完成并上传，无法重新分配";
                            break;
                        }
                        //10-新增，11-已下发,12-已下载，13-已完成,14-已核实,15-已取消
                        //更改工单执行人
                        operatorFlag = updateOrder(conn, m_targetuserid, workorderNo, workorderType);

                        if (!operatorFlag) {
                            errMsg = "重新分配失败:";
                            break;
                        }

                        operatorFlag = chgLog(conn, m_fromuserid, m_targetuserid, workorderNo, workorderType);
                        if (!operatorFlag) {
                            errMsg = "重新分配失败:";
                            break;
                        }
                    }

                    if (!operatorFlag) {
                        conn.rollback();
                        conn.setAutoCommit(autoCommit);
                        ClearBeforeCancel(resout, conFilePath, false, errMsg);
                        return;
                    }

                    conn.commit();
                    conn.setAutoCommit(autoCommit);
                    PDAUtil.clearDir(conFilePath);
                    setResultValue(resout, true, "", "");

                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (QueryException e) {
                    e.printStackTrace();
                }
            } else {
                ClearBeforeCancel(resout, conFilePath, false, "can't find xml file");
            }
        } finally {
            DBManager.closeDBConnection(conn);
        }
    }

    private void ClearBeforeCancel(PrintWriter resout, String conFilePath, boolean flag, String msg) {
        setResultValue(resout, flag, msg, "");
        PDAUtil.clearDir(conFilePath);
    }


    /**
     * 检查对应工单状态，只有已下载未上传的对应工单返回TRUE
     * @param conn
     * @param workorderType
     * @param workorderNo
     * @param m_fromuserid
     * @return
     */
    private boolean chkWorkOrderStatus(Connection conn, String workorderType, String workorderNo, int m_fromuserid) {

        boolean s1 = false;

        //10-新增，11-已下发,12-已下载，13-已完成,14-已核实,15-已取消
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "";
        if (ORDER_I.contains(workorderType)) {
            sqlStr = "SELECT WORKORDER_FLAG\n" +
                    "  FROM ETS_WORKORDER EW\n" +
                    " WHERE EW.WORKORDER_NO = ?\n" +
                    "   AND EW.IMPLEMENT_BY = ?\n" +
                    "   AND (EW.WORKORDER_FLAG = '11' OR EW.WORKORDER_FLAG = '12')\n";
        } else if (ORDER_II.contains(workorderType)) {
            sqlStr = "SELECT * FROM AMS_ASSETS_CHECK_HEADER" +
                    " WHERE TRANS_NO=? AND IMPLEMENT_BY=? AND " +
                    "(ORDER_STATUS='" + AssetsDictConstant.CHK_STATUS_DISTRUIBUTED + "' OR ORDER_STATUS='" + AssetsDictConstant.CHK_STATUS_DOWNLOADED + "') ";
        } else if (ORDER_III.contains(workorderType)) {
            sqlStr = "SELECT * FROM AMS_ASSETS_SAMPLING_HEADER" +
                    " WHERE ORDER_NO=? AND IMPLEMENT_BY=? AND " +
                    "(ORDER_STATUS='" + AssetsDictConstant.CHK_STATUS_DISTRUIBUTED + "' OR ORDER_STATUS='" + AssetsDictConstant.CHK_STATUS_DOWNLOADED + "') ";
        } else {
             sqlStr = "SELECT * FROM AMS_ASSETS_TRANS_HEADER" +
                    " WHERE TRANS_NO=? AND IMPLEMENT_BY=? AND " +
                    "(TRANS_STATUS='" + AssetsDictConstant.CHK_STATUS_DISTRUIBUTED + "' OR TRANS_STATUS='" + AssetsDictConstant.CHK_STATUS_DOWNLOADED + "') ";
        }
        sqlArgs.add(workorderNo);
        sqlArgs.add(m_fromuserid);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        try {
            SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
            simpleQuery.executeQuery();
            s1 = simpleQuery.hasResult();
        } catch (QueryException e) {
            e.printStackTrace();
            Logger.logError(e);
        }

        return s1;
    }

    /**
     * 更新工单执行人
     * @param conn
     * @param userId
     * @param workorderNo
     * @param workorderType
     * @return
     */
    private boolean updateOrder(Connection conn, int userId, String workorderNo, String workorderType) {
        boolean flag = false;
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String strSql = "";
        if (ORDER_I.contains(workorderType)) {
            strSql = "UPDATE ETS_WORKORDER SET IMPLEMENT_BY = ? WHERE WORKORDER_NO = ?";
        } else if (ORDER_II.contains(workorderType)) {
            strSql = "UPDATE AMS_ASSETS_CHECK_HEADER SET IMPLEMENT_BY=? WHERE TRANS_NO=?";
        } else if (ORDER_III.contains(workorderType)) {
            strSql = "UPDATE AMS_ASSETS_SAMPLING_HEADER SET IMPLEMENT_BY=? WHERE ORDER_NO=?";
        } else {
            strSql = "UPDATE AMS_ASSETS_TRANS_HEADER SET  IMPLEMENT_BY=? WHERE TRANS_NO=?";
        }
        sqlArgs.add(userId);
        sqlArgs.add(workorderNo);

        sqlModel.setSqlStr(strSql);
        sqlModel.setArgs(sqlArgs);
        try {
            flag = DBOperator.updateRecord(sqlModel, conn);
        } catch (DataHandleException e) {
            Logger.logError(e);
            flag = false;
        }

        return flag;
    }

    private boolean chgLog(Connection conn, int fromUserId, int toUserId, String workorderNo, String workorderType) {
        boolean flag = false;
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String strSql = "INSERT INTO ETS_EXECUTOR_CHG_LOG\n" +
                "  (SYSTEMID,\n" +
                "   WORKORDER_BATCH,\n" +
                "   WORKORDER_NO,\n" +
                "   ORI_IMPLEMENT_BY,\n" +
                "   IMPLEMENT_BY,\n" +
                "   CREATION_DATE,\n" +
                "   CREATED_BY)\n" +
                "   values(NEWID(),\n" +
                "          ?,\n" +
                "          ?,\n" +
                "          ?,\n" +
                "          ?,\n" +
                "          GETDATE(),\n" +
                "          ?)";
        sqlArgs.add(workorderType);
        sqlArgs.add(workorderNo);
        sqlArgs.add(fromUserId);
        sqlArgs.add(toUserId);
        sqlArgs.add(fromUserId);
        sqlModel.setSqlStr(strSql);
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
        out.println("</AssignWorkOrders>");
        out.close();
    }
}
