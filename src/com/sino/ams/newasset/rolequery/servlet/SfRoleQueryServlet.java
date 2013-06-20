package com.sino.ams.newasset.rolequery.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.rolequery.constant.RoleQueryConstant;
import com.sino.ams.newasset.rolequery.dto.SfRoleQueryDTO;
import com.sino.ams.newasset.rolequery.model.SfRoleQueryResModel;
import com.sino.ams.newasset.rolequery.model.SfRoleQueryUserModel;
import com.sino.base.constant.WorldConstant;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.datatrans.CustomTransData;
import com.sino.base.db.datatrans.DataRange;
import com.sino.base.db.datatrans.DataTransfer;
import com.sino.base.db.datatrans.TransRule;
import com.sino.base.db.datatrans.TransferFactory;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;

public class SfRoleQueryServlet extends BaseServlet {

	@Override
	public void performTask(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String forwardURL = RoleQueryConstant.ROLE_QUERY_SERVLET_PAGE;
		Message message = SessionUtil.getMessage(req);
		String action = req.getParameter("act");
		action = StrUtil.nullToString(action);
		Connection conn = null;
		BaseSQLProducer sqlModel = null;
		SfUserBaseDTO user = (SfUserBaseDTO) SessionUtil.getUserAccount(req);// 从session中获取数据，根据实际情况自行修改。
		try {
			SfRoleQueryDTO dto;
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(SfRoleQueryDTO.class.getName());
			dto = (SfRoleQueryDTO) req2DTO.getDTO(req);
			conn = DBManager.getDBConnection();
			String searchType="SEARCHUSER";
			if(req.getParameter("searchType")!=null)
			{
				searchType=req.getParameter("searchType");
			}
			req.setAttribute("searchType", searchType);
			req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, dto);
			if ("".equals(action)) {
				
			} else if (RoleQueryConstant.ROLE_QUERY_USER.equals(action)) {
				sqlModel=new SfRoleQueryUserModel(user,dto);
				pageQuery(sqlModel, conn, req);
			} else if (RoleQueryConstant.ROLE_QUERY_RES.equals(action)) {
//				sqlModel=new SfRoleQueryResModel(user,dto);
//				pageQuery(sqlModel, conn, req);
				req.setAttribute("DTO", dto);
			} else if (action.equals(RoleQueryConstant.ROLE_EXPORT_USER)) {
				sqlModel=new SfRoleQueryUserModel(user,dto);
				File file = exportFile(action,sqlModel,user,dto,conn);
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
			} else if (action.equals(RoleQueryConstant.ROLE_EXPORT_RES)) {
				sqlModel=new SfRoleQueryResModel(user,dto,"");
				File file = exportFile(action,sqlModel,user,dto,conn);
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
			} else {
				message = getMessage(MsgKeyConstant.INVALID_REQ);
				message.setIsError(true);
			}
			req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, dto);
		} catch (QueryException e) {
			Logger.logError(e);
			message.setIsError(true);
		} catch (Exception e) {
			Logger.logError(e);
			message.setIsError(true);
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
			// 根据实际情况修改页面跳转代码。
		}
	}

	private void pageQuery(BaseSQLProducer sqlProducer, Connection conn,
			HttpServletRequest req) throws QueryException {
		PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
		pageDAO.produceWebData();
	}
	
	 /**
	 * 功能：导出Excel文件。
	 * @return File
	 * @throws com.sino.base.exception.DataTransException
	 * @throws SQLModelException 
	 */
	public File exportFile(String action,BaseSQLProducer sqlProducer,SfUserBaseDTO user,SfRoleQueryDTO dto,Connection conn) throws DataTransException, SQLModelException {
		File file = null;
		SQLModel sqlModel = sqlProducer.getPageQueryModel();
		TransRule rule = new TransRule();
		rule.setDataSource(sqlModel);
		rule.setCalPattern(CalendarConstant.LINE_PATTERN);
		rule.setSourceConn(conn);
		
		CustomTransData custData = new CustomTransData();		
		String fileName = "";
		
		//用户角色导出
		if(action.equals(RoleQueryConstant.ROLE_EXPORT_USER)) {
			fileName = "用户角色信息.xls";
			
			Map fieldMap = new HashMap();
			fieldMap.put("USER_NAME", "用户姓名");
			fieldMap.put("GROUP_NAME", "用户组别");
			fieldMap.put("ROLE_NAME", "角色名称");
			fieldMap.put("ROLE_DESC", "角色描述");
			rule.setFieldMap(fieldMap);
			
			custData.setReportTitle("用户角色信息");
		} else if (action.equals(RoleQueryConstant.ROLE_EXPORT_RES)) { //栏目角色导出
			fileName = "栏目角色信息.xls";
			
			Map fieldMap = new HashMap();
			fieldMap.put("RES_NAME", "栏目名称");
			fieldMap.put("GROUP_NAME", "栏目组别");
			fieldMap.put("RES_URL", "栏目URL");
			fieldMap.put("ROLE_NAME", "角色名称");
			fieldMap.put("ROLE_DESC", "角色描述");
			rule.setFieldMap(fieldMap);
			
			custData.setReportTitle("栏目角色信息");
		}
		
		String filePath = WorldConstant.USER_HOME;
		filePath += WorldConstant.FILE_SEPARATOR;
		filePath += fileName;
		rule.setTarFile(filePath);
		DataRange range = new DataRange();
		rule.setDataRange(range);
		
		custData.setReportPerson(user.getUsername());
		custData.setNeedReportDate(true);
		rule.setCustData(custData);
		TransferFactory factory = new TransferFactory();
		DataTransfer transfer = factory.getTransfer(rule);
		transfer.transData();
		file = (File) transfer.getTransResult();
		return file;
	}
}
