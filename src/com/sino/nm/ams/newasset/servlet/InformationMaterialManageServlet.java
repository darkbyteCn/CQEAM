package com.sino.nm.ams.newasset.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.constant.AssetsURLList;
import com.sino.nm.ams.newasset.dao.InformationMaterialManageDAO;
import com.sino.nm.ams.newasset.dao.InformationMaterialManageExportDAO;
import com.sino.nm.ams.newasset.dto.InformationMaterialDeleteDTO;
import com.sino.nm.ams.newasset.dto.InformationMaterialDeleteIgnoreDTO;
import com.sino.nm.ams.newasset.dto.InformationMaterialIgnoreDTO;
import com.sino.nm.ams.newasset.dto.InformationMaterialManageDTO;
import com.sino.nm.ams.newasset.model.InformationMaterialManageModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataTransException;
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

public class InformationMaterialManageServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	public void performTask(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		Connection conn = null;
		String showMsg = "";
		
		try {
			SfUserDTO user = (SfUserDTO) getUserAccount(req);

			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(InformationMaterialManageDTO.class
					.getName());
			InformationMaterialManageDTO dto = (InformationMaterialManageDTO) req2DTO
					.getDTO(req);
			String action = dto.getAct();

			conn = this.getDBConnection(req);

			if ("".equals(action)) {
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = AssetsURLList.INFORMATION_MATERIAL_QUERY;
			} else if (WebActionConstant.QUERY_ACTION.equals(action)) {
				BaseSQLProducer sqlProducer = new InformationMaterialManageModel(
						user, dto);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setCalPattern(LINE_PATTERN);
				pageDAO.produceWebData();
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = AssetsURLList.INFORMATION_MATERIAL_QUERY;
			} else if(WebActionConstant.NEW_ACTION.equals(action)){
				/*InformationMaterialManageDAO addDAO = new InformationMaterialManageDAO(user, dto, conn);
				Request2DTO r2 = new Request2DTO();
				r2.setDTOClassName(InformationMaterialManageDTO.class.getName());
				DTOSet lineSet = r2.getDTOSet(req);
				addDAO.submitData(lineSet);*/
				
				forwardURL = AssetsURLList.INFORMATION_MATERIAL_ADD;
			} else if (WebActionConstant.EXPORT_ACTION.equals(action)){
				InformationMaterialManageExportDAO rptDAO = new InformationMaterialManageExportDAO(user, dto, conn);
				File file = rptDAO.getExportFile();
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
			} else if (WebActionConstant.SUBMIT_ACTION.equals(action)){ 
				InformationMaterialManageDAO addDAO = new InformationMaterialManageDAO(user, dto, conn);
				Request2DTO r2 = new Request2DTO();
				r2.setDTOClassName(InformationMaterialManageDTO.class.getName());
				r2.setIgnoreFields(InformationMaterialIgnoreDTO.class);
				DTOSet lineSet = r2.getDTOSet(req);
				boolean operateResult = addDAO.submitData(lineSet);
				
				//forwardURL = "/newasset/saveInformationFinish.jsp";
				
				if (operateResult) {
					forwardURL =
						"/servlet/com.sino.ams.newasset.servlet.InformationMaterialManageServlet?act=" +
						WebActionConstant.QUERY_ACTION;
					showMsg = "信息保存已完成!";
				} else {
					forwardURL =
						"/servlet/com.sino.ams.newasset.servlet.InformationMaterialManageServlet?act=" +
						WebActionConstant.QUERY_ACTION;
					showMsg = "信息保存出错!";
							
				}
			}else if (WebActionConstant.DELETE_ACTION.equals(action)){
				InformationMaterialManageDAO deleteDAO = new InformationMaterialManageDAO(user, dto, conn);
				Request2DTO r2 = new Request2DTO();
				//r2.setDTOClassName(InformationMaterialManageDTO.class.getName());
				r2.setDTOClassName(InformationMaterialDeleteDTO.class.getName());
				r2.setIgnoreFields(InformationMaterialDeleteIgnoreDTO.class);
				DTOSet lineSet = r2.getDTOSet(req);
				deleteDAO.deleteData(lineSet);
				
				forwardURL =
					"/servlet/com.sino.nm.ams.newasset.servlet.InformationMaterialManageServlet?act=" +
					AssetsActionConstant.QUERY_ACTION;
			}else if (WebActionConstant.EDIT_ACTION.equals(action)){
				InformationMaterialManageDAO queryDAO = new InformationMaterialManageDAO(user, dto, conn);
				queryDAO.setDTOClassName(InformationMaterialManageDTO.class.getName());
				
				InformationMaterialManageDTO  queryDTO = (InformationMaterialManageDTO)queryDAO.getDataByPrimaryKey();
				req.setAttribute("queryDTO", queryDTO);
				
				forwardURL = "/newasset/updateInformationMaterial.jsp";
			}else if (WebActionConstant.UPDATE_ACTION.equals(action)){
				InformationMaterialManageDAO updateDAO = new InformationMaterialManageDAO(user, dto, conn);
				updateDAO.setDTOClassName(InformationMaterialManageDTO.class.getName());
				
				boolean operateResult = updateDAO.update();
				
				if (operateResult) {
					forwardURL =
						"/servlet/com.sino.ams.newasset.servlet.InformationMaterialManageServlet?act=" +
						WebActionConstant.QUERY_ACTION;
					showMsg = "信息更新完成!";
				} else {
					forwardURL =
						"/servlet/com.sino.ams.newasset.servlet.InformationMaterialManageServlet?act=" +
						WebActionConstant.QUERY_ACTION;
					showMsg = "信息更新出错!";
							
				}
			}

		} catch (DTOException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.DTO_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (PoolPassivateException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.POOL_PASSIVATE_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (QueryException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.QUERY_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DataTransException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (WebFileDownException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			if (!StrUtil.isEmpty(forwardURL)) {
				if (showMsg.equals("")) {
					forwarder.forwardView(forwardURL);
				} else {
					forwarder.forwardOpenerView(forwardURL, showMsg);
				}
			}
		}

	}

}
