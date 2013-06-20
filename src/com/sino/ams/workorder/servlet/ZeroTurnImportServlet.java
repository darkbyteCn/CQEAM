package com.sino.ams.workorder.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.system.object.dao.ImportObjectDAO;
import com.sino.ams.system.object.servlet.ReadObjectInfo;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dao.ZeroTurnImportDAO;
import com.sino.ams.workorder.dto.ZeroImportDTO;
import com.sino.ams.workorder.model.QueryIntegrationModel;
import com.sino.ams.workorder.model.ZeroTurnImportModel;
import com.sino.ams.workorder.util.ZeroReadObjectInfo;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.FileSizeException;
import com.sino.base.exception.UploadException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.base.web.request.upload.UploadFile;
import com.sino.base.web.request.upload.UploadFileSaver;
import com.sino.base.web.request.upload.UploadRow;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.pda.PDAUtil;
/**
 * 零购资产导入
 * @author mengmin
 * 2012年9月7日 16:44:47
 */
public class ZeroTurnImportServlet extends BaseServlet{

    private static final int startRowNum = 5;
    private static final int columnNum = 23;

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = null;
        Connection conn = null;
        String showMsg = "";
        String action ="";
        try {
        	Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(ZeroImportDTO.class.getName());
			ZeroImportDTO dto = (ZeroImportDTO) req2DTO.getDTO(req);
			action = req.getParameter("act");
			req.getParameter("flName");
			action = StrUtil.nullToString(action);
			conn = getDBConnection(req);
			
            SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(req);
            if(action.equals("")){
            	
                forwardURL="/workorder/ZeroImport.jsp";
            }
            if(action.equals("ZERO_QUERY")){
            	BaseSQLProducer sqlProducer = new ZeroTurnImportModel(userAccount, dto);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.produceWebData();
                forwardURL="/workorder/ZeroImport.jsp";
            }
            if(action.equals("ZERO_IMPORT")){
            	Logger.logInfo("Excel submit servlet begin....");

            	RequestParser reqPar = new RequestParser();
                reqPar.transData(req);
                
            	dto.setExcel(req.getParameter("excel"));
                DTOSet dtoSet = parseExcel2DTOSet(req);
                ZeroTurnImportDAO ImObDAO = new ZeroTurnImportDAO(userAccount, null, conn);
                boolean importResult = ImObDAO.importObject(dtoSet);
                int flag=ImObDAO.validata();
                if (flag==0) {
                    forwardURL = "/servlet/com.sino.ams.workorder.servlet.ZeroTurnImportServlet?act="+"ZERO_QUERY";
                    showMsg = "报账状态更新成功！";
                } else {
//                    RowSet rows = ImObDAO.getImportError();
//                    req.setAttribute(WebAttrConstant.ETS_SPARE_DTO, rows);
                	showMsg = "报账状态更新失败，请看错误信息！";
                    forwardURL = "/servlet/com.sino.ams.workorder.servlet.ZeroTurnImportServlet?act="+"ZERO_QUERY";
                }
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
    private DTOSet parseExcel2DTOSet(HttpServletRequest req) throws DTOException {
        DTOSet dtoSet = null;
        try {
        	String fileName = req.getParameter("excelPath");     //文件路径
            ZeroReadObjectInfo xlsUtil = new ZeroReadObjectInfo();
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
