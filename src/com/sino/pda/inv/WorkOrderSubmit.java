package com.sino.pda.inv;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.*;
import com.sino.base.file.FileProcessor;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.base.web.request.upload.UploadFile;
import com.sino.base.web.request.upload.UploadFileSaver;
import com.sino.base.web.request.upload.UploadRow;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.constant.AmsOrderConstant;
import com.sino.ams.system.user.dao.SfUserDAO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.system.user.util.UserUtil;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.pda.PDAUtil;
import com.sino.pda.util.XmlUtil;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2009-1-7
 * Time: 20:57:32
 * To change this template use File | Settings | File Templates.
 */
public class WorkOrderSubmit extends BaseServlet {
    private String conFilePath = "";
    private static final String CONTENT_TYPE = "application/xml; charset=GBK";
    private String splitor = "";

    private void setResultValue(PrintWriter out, boolean b_flag, String errMsg, String wo_no) {
        out.println("<result>" + String.valueOf(b_flag) + "</result>");
    }

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType(CONTENT_TYPE);
        PrintWriter resout = res.getWriter();

        SfUserDTO sfUser = new SfUserDTO();
        SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(req);
        String sFile = "";
        String submitUser = "";

        boolean submitResult = false;
        String returnMsg = "";

        Logger.logInfo("PDA run inv.WorkOrderSubmit servlet begin....");

        resout.println("<workordersubmit>");

        RequestParser reqPar = null;
        UploadRow uploadRow;
        UploadFile[] upFiles = null;
        boolean returnFlag = false;
        Connection conn = null;
        boolean operatorResult = false;

        try {
            splitor = System.getProperty("file.separator");
            conn = DBManager.getDBConnection();
            reqPar = new RequestParser();
            reqPar.transData(req);
            submitUser = StrUtil.nullToString(reqPar.getParameter("SubmitUser")).toUpperCase();
            /**********************************
             **** Step1:读取提交用户信息 ******
             *********************************/
            if (!submitUser.equals("")) {
                sfUser.setLoginName(submitUser);
                SfUserDAO sfUserDAO = new SfUserDAO(userAccount, sfUser, conn);

                sfUserDAO.setDTOClassName(SfUserDTO.class.getName());
                DTOSet dtoSet = (DTOSet) sfUserDAO.getMuxData();
                if (dtoSet.getSize() > 0) {
                    sfUser = (SfUserDTO) dtoSet.getDTO(0);
                    UserUtil.enrichUserAccount(sfUser, conn);
                }
            }
            /************************************
             **** Step1:读取用户信息--OVER ******
             ************************************/

            /*******************************************************
             ******** Step2:Get The xml File and backup it ********
             *****************************************************/

            conFilePath = PDAUtil.getCurUploadFilePath(conn);

            UploadFileSaver uploadFileSaver = reqPar.getFileSaver();
            uploadFileSaver.saveFiles(conFilePath);

            uploadRow = uploadFileSaver.getRow();
            upFiles = uploadRow.getFiles();

            if (upFiles == null) {
                returnFlag = true;
                setResultValue(resout, false, "upload file " + SyBaseSQLUtil.isNull() + " ", "");
                Logger.logError("upload file " + SyBaseSQLUtil.isNull() + " ");
            } else if (upFiles.length > 1) {
                setResultValue(resout, false, "can't find any xml file", "");
                Logger.logError("upload files quantity >1");
                returnFlag = true;
            }
        } catch (ContainerException e) {
            e.printLog();
            setResultValue(resout, false, e.toString(), "");
            returnFlag = true;
        } catch (UploadException e) {
            e.printLog();
            setResultValue(resout, false, e.toString(), "");
            returnFlag = true;
        } catch (FileSizeException e) {
            e.printLog();
            setResultValue(resout, false, e.toString(), "");
            returnFlag = true;
        } catch (QueryException e) {
            e.printLog();
            setResultValue(resout, false, e.toString(), "");
            returnFlag = true;
        }

/*******************************
 ******* Step3: 解析xml ********
 **************************** */
        if (!returnFlag) {
            FileProcessor fp = new FileProcessor();
            String path="";
            try {
                boolean autoCommit = conn.getAutoCommit();
                sFile = upFiles[0].getAbsolutePath();

                path = sFile.substring(0, sFile.lastIndexOf(splitor));

//                sFile = conFilePath + upFiles[0].getFileName();
                //读取xml信息
                XmlUtil xml = new XmlUtil();

                if (!xml.loadXmlFile(sFile)) {
                    ClearBeforeCancel(resout, conFilePath, false, "load xml file error!");
                    Logger.logError("load xml file error!file=" + sFile);
                    resout.println("</workordersubmit>");
                    return;
                }

                String webPath = req.getRealPath(AmsOrderConstant.orderBackupPath);

                conn.setAutoCommit(false);
                PDAOrderUtil pdaOrderUtil = new PDAOrderUtil();
                boolean operatorFlag = pdaOrderUtil.submitOrder(conn, xml, sfUser, sFile, webPath);

                if (operatorFlag) {
                    conn.commit();
                    submitResult = true;
                } else {
                    conn.rollback();
                    submitResult = false;
                }
                conn.setAutoCommit(autoCommit);

            } catch (SQLException e) {
                resout.println("<error>workorder submit Error!</error>");
                Logger.logError(e);
            } catch (DataHandleException e) {
                resout.println("<error>workorder submit Error!</error>");
                Logger.logError(e);
            } catch (QueryException e) {
                resout.println("<error>workorder submit Error!</error>");
                Logger.logError(e);
            } catch (ContainerException e) {
                resout.println("<error>workorder submit Error!</error>");
                Logger.logError(e);
            } catch (FileException e) {
                resout.println("<error>workorder submit Error!</error>");
                Logger.logError(e);
            } catch (Exception e) {
                resout.println("<error>workorder submit Error!</error>");
                Logger.logError(e);
            } finally {
                DBManager.closeDBConnection(conn);
                System.out.println("sFile = " + sFile);
                try{
                    fp.delete(path);
                }catch(Exception e)
                {}
            }
        } else {
            DBManager.closeDBConnection(conn);
            returnMsg = "can't find xml file";
        }
        ClearBeforeCancel(resout, conFilePath, submitResult, returnMsg);

        resout.println("</workordersubmit>");
        resout.close();
    }

    private void ClearBeforeCancel(PrintWriter resout, String conFilePath, boolean flag, String msg) {
        setResultValue(resout, flag, msg, "");
//        PDAUtil.clearDir(conFilePath);
    }
}

