package com.sino.ams.system.object.servlet;

import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.system.object.dao.ImportObjectDAO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.FileSizeException;
import com.sino.base.exception.UploadException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.base.web.request.upload.UploadFile;
import com.sino.base.web.request.upload.UploadFileSaver;
import com.sino.base.web.request.upload.UploadRow;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.pda.PDAUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

/**
 * Created by IntelliJ IDEA.
 * User: zjs
 * Date: 2008-6-26
 * Time: 15:46:41
 * Function:地点批量导入.
 */
public class ImportObjectServlet extends BaseServlet {
    private static final int startRowNum = 1; //原先为何是20？;
    private static final int columnNum = 8;

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = null;
        Connection conn = null;
        String showMsg = "";
        try {
            conn = DBManager.getDBConnection();
            SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(req);
            Logger.logInfo("Excel submit servlet begin....");

            UploadFile uploadFile = getUploadFile(req, conn);
            if(uploadFile == null){
                return;
            }
            String fileName = uploadFile.getAbsolutePath();
            DTOSet dtoSet = parseExcel2DTOSet(fileName);
            ImportObjectDAO ImObDAO = new ImportObjectDAO(userAccount, null, conn);
            boolean importResult = ImObDAO.importObject(dtoSet);
            if (importResult) {
                forwardURL = "/system/object/importObject.jsp";
                showMsg = "地点信息导入成功！";
            } else {
                RowSet rows = ImObDAO.getImportError();
                req.setAttribute(WebAttrConstant.ETS_SPARE_DTO, rows);
                forwardURL = "/system/object/importObjectError.jsp";
            }
        } catch (Throwable e) {
            Logger.logError(e);
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            if (showMsg.equals("")) {
                forwarder.forwardView(forwardURL);
            } else {
                forwarder.forwardView(forwardURL, showMsg);
            }
        }
    }

    private UploadFile getUploadFile(HttpServletRequest req, Connection conn) throws UploadException {
        UploadFile uploadFile = null;
        try {
            RequestParser reqPar = new RequestParser();
            reqPar.transData(req);
            UploadRow uploadRow;
            String conFilePath = PDAUtil.getCurUploadFilePath(conn);
            UploadFileSaver uploadFileSaver = reqPar.getFileSaver();
            uploadFileSaver.saveFiles(conFilePath);
            uploadRow = uploadFileSaver.getRow();
            UploadFile[] upFiles = uploadRow.getFiles();
            if (upFiles != null && upFiles.length == 1 && !upFiles[0].getFileName().equals("")) {
                uploadFile = upFiles[0];
            }
        } catch (ContainerException ex) {
            ex.printLog();
            throw new UploadException(ex);
        } catch (FileSizeException ex) {
            ex.printLog();
            throw new UploadException(ex);
        }
        return uploadFile;
    }

    /**
     * 功能：将上载的Excel数据解析到DTOSet中
     *
     * @param fileName 文件全路径
     * @return DTOSet数据
     * @throws DTOException 读取Excel出错时抛出DTO异常
     */
    private DTOSet parseExcel2DTOSet(String fileName) throws DTOException {
        DTOSet dtoSet = null;
        try {
            ReadObjectInfo xlsUtil = new ReadObjectInfo();
            xlsUtil.setFileName(fileName);
            xlsUtil.setNumberOfColumn(columnNum);
            xlsUtil.setStartRowNum(startRowNum);
            dtoSet = xlsUtil.readXls(0);
        } catch (IOException ex) {
            Logger.logError(ex);
            throw new DTOException(ex);
        }
        return dtoSet;
    }
}
