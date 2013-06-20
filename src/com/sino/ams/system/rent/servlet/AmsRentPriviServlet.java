package com.sino.ams.system.rent.servlet;


import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.constant.AssetsURLList;
import com.sino.ams.newasset.dto.AmsAssetsPriviDTO;
import com.sino.ams.system.rent.dao.AmsRentPriviDAO;
import com.sino.ams.system.rent.dto.AmsRentPriviDTO;
import com.sino.ams.system.rent.model.AmsRentPriviModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.StrException;
import com.sino.base.exception.UploadException;
import com.sino.base.message.Message;
import com.sino.base.web.CheckBoxProp;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: AmsAssetsPriviServlet</p>
 * <p>Description:程序自动生成服务程序“AmsAssetsPriviServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */


public class AmsRentPriviServlet extends BaseServlet {
	/**
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO)SessionUtil.getUserAccount(req);
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(AmsRentPriviDTO.class.getName());
			AmsRentPriviDTO dtoParameter = (AmsRentPriviDTO)req2DTO.getDTO(req);
			String action = dtoParameter.getAct();
			conn = getDBConnection(req);
            ServletConfigDTO  servletConfig = getServletConfig(req);
            AmsRentPriviDAO amsRentPriviDAO = new AmsRentPriviDAO(user, dtoParameter, conn);
            amsRentPriviDAO.setServletConfig(servletConfig);
            if (action.equals("")) {
                	if(!user.isProvinceUser()){
					dtoParameter.setCompanyCode(user.getCompanyCode());
					dtoParameter.setCompanyName(user.getCompany());
				}
				req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
                forwardURL = "/system/rent/rentPriviQuery.jsp";
           } else if (action.equals(AssetsActionConstant.QUERY_ACTION)) {
				if(!user.isProvinceUser()){
					dtoParameter.setCompanyCode(user.getCompanyCode());
					dtoParameter.setCompanyName(user.getCompany());
				}
				BaseSQLProducer sqlProducer = new AmsRentPriviModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setServletConfig(servletConfig);
				CheckBoxProp checkProp = new CheckBoxProp("subCheck");
				checkProp.addDbField("PRIVI_ID");
				pageDAO.setWebCheckProp(checkProp);
				pageDAO.setCalPattern(LINE_PATTERN);
				pageDAO.produceWebData();
				req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);

//                forwardURL = AssetsURLList.ASSETS_PRIVI_QRY;
                forwardURL = "/system/rent/rentPriviQuery.jsp";
			} else if (action.equals(AssetsActionConstant.SAVE_ACTION)) {
				if(amsRentPriviDAO.hasOperatePrivi()){
					String[] userIds = req.getParameterValues("userIds");
					amsRentPriviDAO.savePrivi(userIds);
				}
				forwardURL = getForwardURL(servletConfig, dtoParameter);
				message = amsRentPriviDAO.getMessage();
			} else if(action.equals(AssetsActionConstant.SAVE_MTL_PRIVI)){  //保存资产专业权限
				CheckBoxProp checkProp = new CheckBoxProp("faCategoryCode");
				RequestParser parser = new RequestParser();
				parser.setCheckBoxProp(checkProp);
				parser.transData(req);
				String[] faCategoryCodes = parser.getParameterValues("faCategoryCode");
				amsRentPriviDAO.saveMtlPrivis(faCategoryCodes);
				message = amsRentPriviDAO.getMessage();
				forwardURL = AssetsURLList.FA_CAT_QRY_SERVLET;
				forwardURL += "?act=" + AssetsActionConstant.QUERY_ACTION;
			} else if(action.equals(AssetsActionConstant.DEL_MTL_PRIVI)){    //删除资产专业权限
				CheckBoxProp checkProp = new CheckBoxProp("faCategoryCode");
				RequestParser parser = new RequestParser();
				parser.setCheckBoxProp(checkProp);
				parser.transData(req);
				String[] faCategoryCodes = parser.getParameterValues("faCategoryCode");
				amsRentPriviDAO.delMtlPrivis(faCategoryCodes);
				message = amsRentPriviDAO.getMessage();
				forwardURL = AssetsURLList.FA_CAT_QRY_SERVLET;
				forwardURL += "?act=" + AssetsActionConstant.QUERY_ACTION;
			} else if (action.equals(AssetsActionConstant.DELETE_ACTION)) {
				CheckBoxProp checkProp = new CheckBoxProp("subCheck");
				RequestParser parser = new RequestParser();
				parser.setCheckBoxProp(checkProp);
				parser.transData(req);
				String[] privis = parser.getParameterValues("subCheck");
				amsRentPriviDAO.deletePrivis(privis);
				message = amsRentPriviDAO.getMessage();
				forwardURL = AssetsURLList.ASSETS_PRIVI_SERVLET;
				forwardURL += "?act=" + AssetsActionConstant.QUERY_ACTION;
			} else {
				message = getMessage(AssetsMessageKeys.INVALID_REQ);
				message.setIsError(true);
				forwardURL = MessageConstant.MSG_PRC_SERVLET;
			}
       	} catch (PoolPassivateException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.POOL_PASSIVATE_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DTOException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.DTO_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (QueryException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.QUERY_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (StrException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (UploadException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} finally {
			closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
		}
	}


   /**
	 * 功能：保存权限成功后构造导向路径
	 * @param servletConfig ServletConfigDTO
	 * @param dtoParameter AmsAssetsPriviDTO
	 * @return String
	 */
	private String getForwardURL(ServletConfigDTO servletConfig, AmsAssetsPriviDTO dtoParameter){
		StringBuffer url = new StringBuffer();
//		url.append(AssetsURLList.PRIVI_RIGHT_SERVLET);
		url.append("/servlet/com.sino.ams.system.rent.servlet.RentPriviRightServlet");
		url.append("?roleId=" + dtoParameter.getRoleId());
		url.append("&roleName=");
		url.append(dtoParameter.getRoleName());
		url.append("&provinceCode=");
		url.append(servletConfig.getProCompanyName());
		url.append("&companyName=");
		url.append(dtoParameter.getCompanyName());
		url.append("&deptName=");
		url.append(dtoParameter.getDeptName());
		return url.toString();
	}

}