package com.sino.ams.system.object.servlet;

import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.system.object.dao.ImportVillageAssetsDAOJt;
import com.sino.ams.system.object.dao.ImportVillageAssetsDAOJtEfa;
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
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-5-25
 * Time: 9:47:25
 * To change this template use File | Settings | File Templates.
 */
public class ImportVillageAssetsServletJt extends BaseServlet {
    private final static int startRowNum = 5;
    private final static int columnNum = 34;

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = null;
        Connection conn = null;
        String showMsg = "";
        try {
            conn = DBManager.getDBConnection();
            SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(req);
            ImportVillageAssetsDAOJt ImObDAO = new ImportVillageAssetsDAOJt(userAccount, null, conn);
            ImportVillageAssetsDAOJtEfa ImObDAOEFA =new ImportVillageAssetsDAOJtEfa(userAccount, null, conn);
            String action = req.getParameter("act");
            //if (action == null) {
            if (action.equals("UPLOAD_ACTION")) { 
                Logger.logInfo("Excel submit servlet begin....");
                String conFilePath = PDAUtil.getCurUploadFilePath(conn);
                DTOSet dtoSet = parseExcel2DTO(req, conFilePath);
                message = new Message();
                if(dtoSet != null){
                    //清除数据
                    ImObDAO.deleteImportModel();
                    ImObDAO.itemImportData(dtoSet);
                    ImObDAOEFA.deleteImportModel();
                    ImObDAOEFA.itemImportData(dtoSet);
                    dtoSet.clearData();
                    showMsg = ImObDAO.doVerifyData();
                    if (StrUtil.isEmpty(showMsg)) {//校验没有错误
                        showMsg = ImObDAO.submitOrderDtl();
                        if(StrUtil.isEmpty(showMsg)) {
                            showMsg = "通服资产导入成功";
                            message.setMessageValue(showMsg);
                        } else {
                            message.setMessageValue(showMsg);
                            message.setIsError(true);
                        }
                        //forwardURL = MessageConstant.MSG_PRC_SERVLET;
                        //forwardURL = "/system/object/importVillageAssetsJt.jsp";
        				forwardURL = "/servlet/com.sino.ams.system.object.servlet.ImportVillageAssetsServletJt?act=";
                    } else {
                        message.setMessageValue(showMsg);
                        message.setIsError(true);
                        RowSet rows = ImObDAO.getImportErrors();
                        req.setAttribute(WebAttrConstant.ETS_SPARE_DTO, rows);
                        forwardURL = "/system/object/importVillageErrorJt.jsp";
                    }
                } else {
                    showMsg = "不能从上载的Excel中解析出正确的数据，请确认上载Excel与模板格式一致！";
                    message.setMessageValue(showMsg);
                    message.setIsError(true);
                    forwardURL = MessageConstant.MSG_PRC_SERVLET;
                }
            } else if (action == null || action.equals("")) {
            	forwardURL = "/system/object/importVillageAssetsJt.jsp";
            } else {
                File file = ImObDAO.getExportFile();
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
            }
        } catch (Throwable ex) {
            Logger.logError(ex);
            showMsg = ex.getMessage();
            message = getMessage(AssetsMessageKeys.COMMON_ERROR);
            message.setMessageValue( showMsg );
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
            if (!StrUtil.isEmpty(forwardURL)) {
    			if ( StrUtil.isEmpty( showMsg ) || !showMsg.equals("通服资产导入成功")) {
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
                    ReadVillageAssetsInfoJt xlsUtil = new ReadVillageAssetsInfoJt();
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
