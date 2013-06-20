package com.sino.pda;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.data.Row;
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
import com.sino.ams.bean.OrderNumGenerator;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.pda.util.XmlUtil;
import org.jdom.Element;

/**
 * Created by IntelliJ IDEA.
 * User: Zyun
 * Date: 2008-3-11
 * Time: 10:32:53
 * Function:备件创建单据(盘点工单)
 */
public class CreateSpareOrder extends BaseServlet {
    private String conFilePath = "";
    private static final String m_sContentType = "text/xml; charset=GBK";

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        Connection conn = null;

        try {
            conn = DBManager.getDBConnection();
            PrintWriter resout = res.getWriter();
            String sFile = "";
            Logger.logInfo("PDA run CreateSpareOrder servlet begin....");
            RequestParser reqPar = new RequestParser();
            reqPar.transData(req);
            String test = StrUtil.nullToString(reqPar.getParameter("test"));
            resout.println("<?xml version=\"1.0\" encoding=\"GB2312\" ?> ");
            resout.println("<CreateSpareOrder>");

            UploadFile[] upFiles = null;
            UploadRow uploadRow;
            boolean hasError = false;
            conFilePath = PDAUtil.getCurUploadFilePath(conn);   //获得”工单“文件路径

            UploadFileSaver uploadFileSaver = reqPar.getFileSaver();
            uploadFileSaver.saveFiles(conFilePath);

            uploadRow = uploadFileSaver.getRow();
            upFiles = uploadRow.getFiles();

            if (upFiles == null) {
                Logger.logError("upload file " + SyBaseSQLUtil.isNull() + " ");
                hasError = true;
                setResultValue(resout, false, "upload file " + SyBaseSQLUtil.isNull() + " ", "");
            } else {
                if (upFiles.length == 1) {
                    if (upFiles[0].getFileName().equals("")) {
                        Logger.logError("can't find upload xml files");
                        hasError = true;
                        setResultValue(resout, false, "can't find upload xml files,please check PDA prog or GPRS", "");
                    }
                } else {
                    Logger.logError("can't find any xml file");
                    setResultValue(resout, false, "can't find any xml file", "");
                    hasError = true;
                }
            }
            //==================================================
            if (!hasError) {
                sFile = upFiles[0].getAbsolutePath();
                XmlUtil xml = new XmlUtil();
                if (!xml.loadXmlFile(sFile)) {
                    ClearBeforeCancel(resout, conFilePath, false, "load xml file error!");
                    return;
                }
                Element el = xml.getRootElement();

                if (!el.getName().equals("spareOrder")) {
                    ClearBeforeCancel(resout, conFilePath, false, "can't get XML file child");
                    return;
                }
                String transType = "PJPD";
//                String type = "";
                String createdBy = "";
                String objectNo = "";
                String organizationId = "";
                String remark = "";
                //获取<spareOrder  transType=>	，root attribute
                transType = xml.getElementAttrValue(el, "type");
                createdBy = xml.getElementAttrValue(el, "createBy");
                objectNo = xml.getElementAttrValue(el, "objectNo");
                organizationId = xml.getElementAttrValue(el, "organizationId");
                remark = xml.getElementAttrValue(el, "remark");
                SfUserDTO userAccount = PDAUtil.getUserInfo(conn, createdBy);
                String companyCode = userAccount.getCompanyCode();
                OrderNumGenerator numberProducer = new OrderNumGenerator(conn, companyCode, "BJPD");
                String userId = getUserId(conn, createdBy);
                String orderNo = numberProducer.getOrderNum();
                creatSpareOrder(conn, orderNo, transType, userId, objectNo, organizationId, remark);
                setResultValue(resout, true, "", orderNo);
            } else {
                ClearBeforeCancel(resout, conFilePath, false, "can't find xml file");
            }
        } catch (PoolException e) {
            Logger.logError(e);
        } catch (ContainerException e) {
            Logger.logError(e);
        } catch (UploadException ex) {
            ex.printLog();
        } catch (FileSizeException ex) {
            ex.printLog();
        } catch (DataHandleException e) {
            Logger.logError(e);
        } catch (QueryException e) {
            Logger.logError(e);
        } catch (SQLException e) {
            Logger.logError(e);
        } finally {
            DBManager.closeDBConnection(conn);
        }
    }


    private void setResultValue(PrintWriter out, boolean b_flag, String errMsg, String spareOrder) {
        out.println("<result message=\"" + errMsg + "\" >"
                + String.valueOf(b_flag)
                + "</result>");
        out.println("<spareOrder  transNo=\"" + spareOrder + "\" />");
        out.println("</CreateSpareOrder>");
        out.close();
    }


    private boolean creatSpareOrder(Connection conn, String orderNo, String transType, String userId, String objectNo, String organizationId, String remark) throws DataHandleException {
        boolean flag = false;                    //备件盘点
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO AMS_ITEM_TRANS_H\n" +
                "  (TRANS_ID,TRANS_NO,TRANS_TYPE, TRANS_STATUS, FROM_OBJECT_NO, FROM_ORGANIZATION_ID,CREATION_DATE,CREATED_BY,REMARK)\n" +
                " VALUES\n" +
                "  (AMS_ITEM_TRANS_H_S.NEXTVAL,?,'BJPD','SCANING',?, ?, GETDATE(),?,?)";
//        sqlArgs.add(transType);
//        sqlArgs.add();'BJPD',
        sqlArgs.add(orderNo);
//        sqlArgs.add(transType);
        sqlArgs.add(objectNo);
        sqlArgs.add(organizationId);
        sqlArgs.add(userId);
        sqlArgs.add(remark);
//        sqlArgs.add(userAccount.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        flag = DBOperator.updateRecord(sqlModel, conn);

        return flag;
    }


    private void ClearBeforeCancel(PrintWriter resout, String conFilePath, boolean flag, String msg) {
        setResultValue(resout, flag, msg, "");
        PDAUtil.clearDir(conFilePath);
    }

    private String getUserId(Connection conn, String loginName) throws QueryException, ContainerException {
        String userId = "";
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT SU.USER_ID FROM SF_USER SU WHERE SU.LOGIN_NAME = ?";
        sqlArgs.add(loginName);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        Row row = sq.getFirstRow();
        userId = row.getValue("USER_ID").toString();
        return userId;
    }
}