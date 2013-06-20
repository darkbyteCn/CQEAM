package com.sino.ams.system.object.servlet;

import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.system.object.dao.ImportDzyhAssetsDAOJt;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
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
import java.io.File;
import java.io.IOException;
import java.sql.Connection;

public class ImportDzyhAssetsServletJt extends BaseServlet {
    private final static int startRowNum = 5;
    private final static int columnNum = 17;

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = null;
        Connection conn = null;
        String showMsg = "";
        try {
            conn = DBManager.getDBConnection();
            SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(req);
            ImportDzyhAssetsDAOJt ImObDAO = new ImportDzyhAssetsDAOJt(userAccount, null, conn);
            String action = req.getParameter("act");
            //if (action == null) {
            if (action.equals("UPLOAD_ACTION")) { 
                Logger.logInfo("Excel submit servlet begin....");
                String conFilePath = PDAUtil.getCurUploadFilePath(conn);
                DTOSet dtoSet = parseExcel2DTO(req, conFilePath);
                message = new Message();
                if (dtoSet != null) {
                    //清除数据
                    ImObDAO.deleteImportModel();
                    //导入到接口表AMS_DZYH_ASSETS_IMPORT
                    ImObDAO.itemImportData(dtoSet);
                    //检查接口表数据的有效性。
                    showMsg = ImObDAO.doVerifyData();
                    if (StrUtil.isEmpty(showMsg)) {//校验没有错误
                        showMsg = ImObDAO.submitOrderDtl();
                        if (StrUtil.isEmpty(showMsg)) {
                            showMsg = "低值易耗资产导入成功";
                            message.setMessageValue(showMsg);
                        } else {
                            message.setMessageValue(showMsg);
                            message.setIsError(true);
                        }
                        //forwardURL = MessageConstant.MSG_PRC_SERVLET;
                        //forwardURL = "/system/object/importDzyhAssetsJt.jsp";
                        forwardURL = "/servlet/com.sino.ams.system.object.servlet.ImportDzyhAssetsServletJt?act=";
                    } else {
                        message.setMessageValue(showMsg);
                        message.setIsError(true);
                        RowSet rows = ImObDAO.getImportErrors();
                        req.setAttribute(WebAttrConstant.ETS_SPARE_DTO, rows);
                        forwardURL = "/system/object/importDzyhErrorJt.jsp";
                    }
                } else {
                    showMsg = "不能从上载的Excel中解析出正确的数据，请确认上载Excel与模板格式一致！";
                    message.setMessageValue(showMsg);
                    message.setIsError(true);
                    forwardURL = MessageConstant.MSG_PRC_SERVLET;
                }
            } else if (action == null || action.equals("")) {
            	forwardURL = "/system/object/importDzyhAssetsJt.jsp";
            } else {
                File file = ImObDAO.getExportFile();
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
            }
        } catch (Throwable ex) {
            Logger.logError(ex);
            message = getMessage(AssetsMessageKeys.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			
            if (!StrUtil.isEmpty(forwardURL)) {
    			if (showMsg.equals("") || !showMsg.equals("低值易耗资产导入成功")) {
    				forwarder.forwardView(forwardURL);
    			} else {
    				forwarder.forwardView(forwardURL, showMsg);
    				//forwarder.forwardView(forwardURL);
    			}
            }
        }
    }


    private DTOSet parseExcel2DTO(HttpServletRequest req, String conFilePath) throws UploadException {
        DTOSet dtoSet = null;
        try {
            RequestParser reqPar = new RequestParser();
            reqPar.transData(req);
            UploadFileSaver uploadFileSaver = reqPar.getFileSaver();
            uploadFileSaver.saveFiles(conFilePath);
            UploadRow uploadRow = uploadFileSaver.getRow();
            UploadFile[] upFiles = uploadRow.getFiles();
            if (upFiles != null) {
                if (upFiles.length == 1 && !upFiles[0].getFileName().equals("")) {
                    UploadFile uploadFile = upFiles[0];
                    String fileName = uploadFile.getAbsolutePath();
                    ReadDzyhAssetsInfoJt xlsUtil = new ReadDzyhAssetsInfoJt();
                    xlsUtil.setFileName(fileName);
                    xlsUtil.setNumberOfColumn(columnNum);
                    xlsUtil.setStartRowNum(startRowNum);
                    dtoSet = xlsUtil.readXls(0);
                }
            }
        } catch (IOException ex) {
            Logger.logError(ex);
            throw new UploadException(ex);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new UploadException(ex.getMessage());
        }
        return dtoSet;
    }
}