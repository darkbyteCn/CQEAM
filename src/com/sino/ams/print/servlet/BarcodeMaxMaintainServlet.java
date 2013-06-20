package com.sino.ams.print.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.ResNameConstant;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.print.dao.BarcodeMaxMaintainDAO;
import com.sino.ams.print.dto.BarcodeMaxMaintainDTO;
import com.sino.ams.print.model.BarcodeMaxMaintainModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.system.user.util.UserUtil;
import com.sino.ams.util.ResUtil;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

public class BarcodeMaxMaintainServlet  extends BaseServlet {

	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
			String forwardURL = "";
			Message message = SessionUtil.getMessage(req);
			String action = req.getParameter("act");
			action = StrUtil.nullToString(action);
			Connection conn = null;		
			SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(req);
			UserUtil sfUserUtil = new UserUtil(userAccount);
			String orgOption = "";
			try {		
				Request2DTO req2DTO = new Request2DTO();
				req2DTO.setDTOClassName(BarcodeMaxMaintainDTO.class.getName());			
				BarcodeMaxMaintainDTO dtoParameter = (BarcodeMaxMaintainDTO) req2DTO.getDTO(req);
				conn = DBManager.getDBConnection();	
				sfUserUtil.setServletConfig(getServletConfig(req));
				
				BarcodeMaxMaintainDAO barcodeMaxMaintainDAO = new BarcodeMaxMaintainDAO(userAccount, dtoParameter, conn);
				OptionProducer op = new OptionProducer(userAccount, conn);
				String companyId = StrUtil.isEmpty(dtoParameter.getCompanyId()) ? userAccount.getCompanyCode() : dtoParameter.getCompanyId();
				req.setAttribute(WebAttrConstant.BARCODE_MAX_MAINTAIN_DTO, dtoParameter);
				if("4010".equals(userAccount.getCompanyCode())){
					if("".equals(dtoParameter.getCompanyId())){
						orgOption = op.getAllCompanyCode("", true);
					}else{
						orgOption = op.getAllCompanyCode(companyId, true);
					}					
				}else{
					orgOption = op.getCompanyOpt(companyId);
				}
				req.setAttribute(WebAttrConstant.CITY_OPTION, orgOption);
				if (action.equals("")) {			
					//栏目定义标头
					ResUtil.setAllResName(conn, req, ResNameConstant.BARCODE_MAINTAIN );
					
					forwardURL = URLDefineList.BARCODE_MAX_MAINTAIN_PAGE;
				} else if (action.equals(WebActionConstant.QUERY_ACTION)) {       //查询
					//栏目定义标头
					ResUtil.setAllResName(conn, req, ResNameConstant.BARCODE_MAINTAIN );
					
					BaseSQLProducer sqlProducer = new BarcodeMaxMaintainModel(userAccount, dtoParameter);
					PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
			        pageDAO.setCalPattern(LINE_PATTERN);
			        pageDAO.produceWebData();
					forwardURL = URLDefineList.BARCODE_MAX_MAINTAIN_PAGE;
				} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {      //点击查看详细信息
					barcodeMaxMaintainDAO.setDTOClassName(BarcodeMaxMaintainDTO.class.getName());
					dtoParameter = (BarcodeMaxMaintainDTO)barcodeMaxMaintainDAO.getDataByPrimaryKey();
					if(dtoParameter == null){
						dtoParameter = new BarcodeMaxMaintainDTO();
						message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
						message.setIsError(true);
					}else{
						String tagSeq = dtoParameter.getTagSeq();
					    companyId = dtoParameter.getCompanyId();
					    String assetsType = dtoParameter.getAssetsType();
					    String completeBarcode = this.getCompleteBarcode(companyId, tagSeq, assetsType);
					    dtoParameter.setCompleteBarcode(completeBarcode);
					}					
					req.setAttribute(WebAttrConstant.BARCODE_MAX_MAINTAIN_DTO, dtoParameter);
					forwardURL = URLDefineList.BARCODE_MAX_MAINTAIN_DETAIL;
				} else if (action.equals(WebActionConstant.UPDATE_ACTION)) {    //修改操作,只能修改最大数,不能修改OU			
					barcodeMaxMaintainDAO.updateData();
					message = barcodeMaxMaintainDAO.getMessage();
					message = getMessage(MsgKeyConstant.UPDATE_DATA_SUCCESS);
					message.addParameterValue("最大标签号");
					forwardURL = URLDefineList.BARCODE_MAX_MAINTAIN_PAGE;
				}else if (action.equals(WebActionConstant.EXPORT_ACTION)) { //导出错误统计
					File file = barcodeMaxMaintainDAO.exportFile();
					barcodeMaxMaintainDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
					WebFileDownload fileDown = new WebFileDownload(req, res);
					fileDown.setFilePath(file.getAbsolutePath());
					fileDown.download();
					file.delete();
				} else {
					message = getMessage(MsgKeyConstant.INVALID_REQ);
					message.setIsError(true);
					forwardURL = MessageConstant.MSG_PRC_SERVLET;
				} 
			} catch (PoolPassivateException ex) {
				ex.printLog();
				message = getMessage(MsgKeyConstant.POOL_PASSIVATE_ERROR);
				message.setIsError(true);
				forwardURL = MessageConstant.MSG_PRC_SERVLET;
			} catch (DTOException ex) {
				ex.printLog();
				message = getMessage(MsgKeyConstant.DTO_ERROR);
				message.setIsError(true);
				forwardURL = MessageConstant.MSG_PRC_SERVLET;
			} catch (QueryException ex) {
				ex.printLog();
				message = getMessage(MsgKeyConstant.QUERY_ERROR);
				message.setIsError(true);
				forwardURL = MessageConstant.MSG_PRC_SERVLET;
			} catch (DataHandleException ex) {
				ex.printLog();
				message = getMessage(MsgKeyConstant.COMMON_ERROR);
				message.setIsError(true);
				forwardURL = MessageConstant.MSG_PRC_SERVLET;
			} catch (PoolException e) {
				e.printStackTrace();
			} catch (WebFileDownException e) {
				e.printStackTrace();
			} catch (ContainerException e) {
				e.printStackTrace();
			}  finally {
				DBManager.closeDBConnection(conn);
				setHandleMessage(req, message);
				ServletForwarder forwarder = new ServletForwarder(req, res);
				if (!forwardURL.equals("")) {
					forwarder.forwardView(forwardURL);
				}
			}
	}
	
	public String getCompleteBarcode(String companyId, String tagSeq, String assetsType){
		StringBuffer completeBarcode = new StringBuffer(companyId);
		completeBarcode.append("-");
		completeBarcode.append(assetsType);
		if("ZY".equals(assetsType) || "TD".equals(assetsType)){
			completeBarcode.append(tagSeq);
		}else{
			if(tagSeq.length() == 1){
				completeBarcode.append("00000" + tagSeq);
			}else if(tagSeq.length() == 2){
				completeBarcode.append("0000" + tagSeq);
			}else if(tagSeq.length() == 3){
				completeBarcode.append("000" + tagSeq);
			}else if(tagSeq.length() == 4){
				completeBarcode.append("00" + tagSeq);
			}else if(tagSeq.length() == 5){
				completeBarcode.append("0" + tagSeq);
			}else{
				completeBarcode.append(tagSeq);
			}
		}
		return completeBarcode.toString();
	}
}
