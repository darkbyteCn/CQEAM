package com.sino.pda;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.config.ConfigLoader;
import com.sino.base.config.UploadConfig;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.*;
import com.sino.base.file.FileProcessor;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.constant.AmsOrderConstant;
import com.sino.ams.system.user.dao.SfUserDAO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.system.user.util.UserUtil;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.pda.dao.OrderUploadService;
import com.sino.pda.util.XmlUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

/**
 * User: zhoujs
 * Date: 2007-10-25
 * Time: 14:50:24
 * Function:工单提交（管理资产和网络资产）
 */
public class WorkOrderSubmit extends BaseServlet {
    private String conFilePath = "";
    private static final String CONTENT_TYPE = "application/xml; charset=GBK";
    private String splitor = "";
    private File file;
    private String submitUser;

    private void setResultValue(PrintWriter out, boolean b_flag, String errMsg, String wo_no) {
        out.println("<result>" + String.valueOf(b_flag) + "</result>");
    }

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType(CONTENT_TYPE);
        PrintWriter resout = res.getWriter();

        SfUserDTO sfUser = new SfUserDTO();
        SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(req);
        String sFile = "";


        boolean submitResult = false;
        String returnMsg = "";

        Logger.logInfo("PDA run WorkOrderSubmit servlet begin....");

        resout.println("<workordersubmit>");

        RequestParser reqPar = null;
        boolean returnFlag = false;
        Connection conn = null;
        boolean operatorResult = false;

        try {
            splitor = System.getProperty("file.separator");
            conn = DBManager.getDBConnection();
            getFiles(req, conn);

            /**********************************
             **** Step1:读取提交用户信息 ******
             *********************************/
            if (!submitUser.equals("")) {
                sfUser.setLoginName(submitUser.toUpperCase());
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


            if (file == null) {
                returnFlag = true;
                setResultValue(resout, false, "upload file " + SyBaseSQLUtil.isNull() + " ", "");
                Logger.logError("upload file " + SyBaseSQLUtil.isNull() + " ");
            } else {
                try {
                    boolean autoCommit = conn.getAutoCommit();
                    sFile = file.getAbsolutePath();

                    String path = sFile.substring(0, sFile.lastIndexOf(splitor));
                    FileProcessor fp = new FileProcessor();
                    //读取xml信息
                    XmlUtil xml = new XmlUtil();

                    if (!xml.loadXmlFile(sFile)) {
                        ClearBeforeCancel(resout, false, "load xml file error!");
                        Logger.logError("load xml file error!file=" + sFile);
                        resout.println("</workordersubmit>");
                        return;
                    }

                    String webPath = req.getRealPath(AmsOrderConstant.orderBackupPath);
//                    conn.setAutoCommit(false);
                    PDAOrderUtil pdaOrderUtil = new PDAOrderUtil();
                    boolean operatorFlag = pdaOrderUtil.submitOrder(conn, xml, sfUser, sFile, webPath);

                    OrderUploadService uploadService = new OrderUploadService(sfUser, conn);
                    uploadService.setServletConfig(getServletConfig(req));
                    operatorResult = uploadService.uploadOrder(sFile);
                    if (operatorResult && operatorFlag) {
//                        conn.commit();
                        submitResult = true;
                    } else {
//                        conn.rollback();
                        submitResult = false;
                    }
//                    conn.setAutoCommit(autoCommit);

                    fp.delete(sFile);

                } catch (SQLException e) {
                    resout.println("<error>workorder submit Error!</error>");
                    Logger.logError(e);
                } catch (FileException e) {
                    resout.println("<error>workorder submit Error!</error>");
                    Logger.logError(e);
                } catch (Exception e) {
                    resout.println("<error>workorder submit Error!</error>");
                    Logger.logError(e);
                }
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
        } catch (Exception e) {
            Logger.logError(e);
        } finally {
            DBManager.closeDBConnection(conn);
            ClearBeforeCancel(resout, submitResult, returnMsg);
        }


        resout.println("</workordersubmit>");
        resout.close();
    }

    private void ClearBeforeCancel(PrintWriter resout, boolean flag, String msg) {
        setResultValue(resout, flag, msg, "");
    }


    private void getFiles(HttpServletRequest req, Connection conn) throws Exception {
        boolean isMultipart = ServletFileUpload.isMultipartContent(req);
        UploadConfig uploadConfig = ConfigLoader.loadUploadConfig();
        if (isMultipart) {
            DiskFileItemFactory factory = new DiskFileItemFactory();

            //临时交换空间
            String savePath = uploadConfig.getSavePath();
            String exchgPath = savePath + File.separator + "chg";
            String newPath = savePath + File.separator + "sub";


            File tmpDir = new File(exchgPath);
            if (!tmpDir.exists()) {
                tmpDir.mkdirs();
            }
            tmpDir = new File(newPath);
            if (!tmpDir.exists()) {
                tmpDir.mkdirs();
            }

            factory.setRepository(new File(exchgPath));
            // Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload(factory);
            List items = upload.parseRequest(req);

            SeqProducer seqProducer = new SeqProducer(conn);
            for (int i = 0; i < items.size(); i++) {
                FileItem fileItem = (FileItem) items.get(i);
                if (fileItem.isFormField()) {
                    if (fileItem.getFieldName().equals("SubmitUser")) {
                        submitUser = StrUtil.nullToString(fileItem.getString());
                    }
                } else {
                    String tarFileStr = newPath + File.separator + seqProducer.getGUID();
                    File tarFile = new File(tarFileStr + "." + FilenameUtils.getExtension(fileItem.getName()));
                    fileItem.write(tarFile);
                    this.file = tarFile;
                }

            }
        }
    }


}
