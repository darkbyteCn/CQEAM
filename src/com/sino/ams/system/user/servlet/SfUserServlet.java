package com.sino.ams.system.user.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.CustMessageKey;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.system.user.dao.SfUserDAO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.system.user.model.SfUserModel;
import com.sino.ams.system.user.util.UserUtil;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.constant.web.WebConstant;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: SinoApplication</p>
 * <p>Description: Java Enterprise Edition 平台应用开发基础框架</p>
 * <p>Copyright: 唐明胜版权所有Copyright (c) 2003~2007。
 * <p>Copyright: 其中使用到的第三方组件，根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约，版权属原作者所有。</p>
 * <p>Copyright: 作者授权北京思诺博信息技术有限公司在一定范围内使用</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 0.1
 */


public class SfUserServlet extends BaseServlet {

	/**
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		String action = req.getParameter("act");
		action = StrUtil.nullToString(action);
		String userRightStr = "";
		Connection conn = null;
		SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(req);
		UserUtil sfUserUtil = new UserUtil(userAccount);
		PrintWriter pw = null;
		JSONArray retArray = new JSONArray();
		try {
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(SfUserDTO.class.getName());
			SfUserDTO dtoParameter = (SfUserDTO) req2DTO.getDTO(req);
			conn = DBManager.getDBConnection();
			sfUserUtil.setServletConfig(getServletConfig(req));
			SfUserDAO sfUserDAO = new SfUserDAO(userAccount, dtoParameter, conn);
//            sfUserDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
            OptionProducer op = new OptionProducer(userAccount, conn);
			String orgOption = "";
			String groupOption = "";
			String maintainCompanyOption = "";
			String roleOption = "";
			int organizationId = StrUtil.isEmpty(Integer.valueOf(dtoParameter.getOrganizationId())) ? userAccount.getOrganizationId() : dtoParameter.getOrganizationId();
			if (action.equals("")) {
				orgOption = op.getAllOrganization(0, true);
				groupOption = op.getAllGroup(StrUtil.nullToString( dtoParameter.getCurrGroupId() ), dtoParameter.getOrganizationId(), false, true);
				maintainCompanyOption = op.getMainCorpOption(dtoParameter.getMaintainCompany());
				roleOption = op.getRoleOption(dtoParameter.getRoleId());
				req.setAttribute(WebAttrConstant.MAINTAIN_CORP_OPTION, maintainCompanyOption);
				req.setAttribute(WebAttrConstant.OU_OPTION, orgOption);
				req.setAttribute(WebAttrConstant.GROUP_OPTION, groupOption);
				req.setAttribute(WebAttrConstant.ROLE_OPTION, roleOption);
				forwardURL = URLDefineList.USER_LIST_PAGE;
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new SfUserModel(userAccount, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
                pageDAO.produceWebData();
				orgOption = op.getAllOrganization(dtoParameter.getOrganizationId(), true);
				groupOption = op.getAllGroup(String.valueOf(dtoParameter.getCurrGroupId()), dtoParameter.getOrganizationId(), false, true);
				maintainCompanyOption = op.getMainCorpOption(dtoParameter.getMaintainCompany());
				roleOption = op.getRoleOption(dtoParameter.getRoleId());
				req.setAttribute(WebAttrConstant.MAINTAIN_CORP_OPTION, maintainCompanyOption);
				req.setAttribute(WebAttrConstant.OU_OPTION, orgOption);
				req.setAttribute(WebAttrConstant.GROUP_OPTION, groupOption);
				req.setAttribute(WebAttrConstant.ROLE_OPTION, roleOption);
				forwardURL = URLDefineList.USER_LIST_PAGE;
			} else if (action.equals(WebActionConstant.NEW_ACTION)) {
				SfUserDTO sfUser = new SfUserDTO();
				sfUser.setUserRights(sfUserUtil.getDTOSet(sfUser, conn));
				OptionProducer optionProducer = new OptionProducer(userAccount, conn);
				req.setAttribute(WebAttrConstant.OU_OPTION, optionProducer.getAllOrganization(sfUser.getOrganizationId()));
				req.setAttribute(WebAttrConstant.USER_ATTR, sfUser);
				req.setAttribute(WebAttrConstant.ALL_ROLE_ATTR, sfUserUtil.getRoles(conn));
				req.setAttribute(WebAttrConstant.ORG_GROUP_ATTR, sfUserUtil.getGroupMap(userAccount, conn));
				req.setAttribute(WebAttrConstant.USER_RIGHT_ATTR, sfUserUtil.getUserRight(sfUser));
				req.setAttribute(WebAttrConstant.MAINTAIN_CORP_OPTION, optionProducer.getMainCorpOption(sfUser.getMaintainCompany()));
                req.setAttribute(WebAttrConstant.DEPT_CATEGORYS, optionProducer.getDictOption("DEPT_CATEGORY", sfUser.getDeptCategory()));
				forwardURL = URLDefineList.USER_DETAIL_PAGE;
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
                sfUserDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
                sfUserDAO.setDTOClassName(SfUserDTO.class.getName());
				SfUserDTO sfUser = (SfUserDTO) sfUserDAO.getDataByPrimaryKey();
                sfUser.setCalPattern(CalendarConstant.LINE_PATTERN);
				if (sfUser == null) {
					sfUser = new SfUserDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
				sfUser.setUserRights(sfUserUtil.getDTOSet(sfUser, conn));
				OptionProducer optionProducer = new OptionProducer(userAccount, conn);
				req.setAttribute(WebAttrConstant.OU_OPTION, optionProducer.getAllOrganization(sfUser.getOrganizationId()));
				req.setAttribute(WebAttrConstant.USER_ATTR, sfUser);
				req.setAttribute(WebAttrConstant.ALL_ROLE_ATTR, sfUserUtil.getRoles(conn));
				req.setAttribute(WebAttrConstant.ORG_GROUP_ATTR, sfUserUtil.getGroupMap(userAccount, conn));
				req.setAttribute(WebAttrConstant.USER_RIGHT_ATTR, sfUserUtil.getUserRight(sfUser));
				req.setAttribute(WebAttrConstant.MAINTAIN_CORP_OPTION, optionProducer.getMainCorpOption(sfUser.getMaintainCompany()));
                req.setAttribute(WebAttrConstant.DEPT_CATEGORYS, optionProducer.getDictOption("DEPT_CATEGORY", sfUser.getDeptCategory()));
				forwardURL = URLDefineList.USER_DETAIL_PAGE;
			} else if (action.equals(WebActionConstant.CREATE_ACTION)) {
				SfUserDTO sfUser = (SfUserDTO) dtoParameter;
				boolean hasRecord = sfUserDAO.checkSfUser(sfUser);
				if (!hasRecord) {
					userRightStr = StrUtil.nullToString(req.getParameter("right"));
					DTOSet userRightDTOSet = sfUserUtil.convertStr2DTOSet(userRightStr, String.valueOf(dtoParameter.getUserId()));
					boolean operateResult = sfUserDAO.saveData(dtoParameter, userRightDTOSet);

					message = sfUserDAO.getMessage();
					message.setIsError(!operateResult);
					if (operateResult) {
						forwardURL = URLDefineList.USER_LIST_SERVLET;
					} else {
						req.setAttribute(WebAttrConstant.USER_ATTR, dtoParameter);
						forwardURL = URLDefineList.USER_DETAIL_PAGE;
					}
				} else {
					sfUser.setUserRights(sfUserUtil.getDTOSet(sfUser, conn));
					OptionProducer optionProducer = new OptionProducer(userAccount, conn);
					req.setAttribute(WebAttrConstant.OU_OPTION, optionProducer.getAllOrganization(sfUser.getOrganizationId()));
					req.setAttribute(WebAttrConstant.USER_ATTR, sfUser);
					req.setAttribute(WebAttrConstant.ALL_ROLE_ATTR, sfUserUtil.getRoles(conn));
					req.setAttribute(WebAttrConstant.ORG_GROUP_ATTR, sfUserUtil.getGroupMap(userAccount, conn));
					req.setAttribute(WebAttrConstant.USER_RIGHT_ATTR, sfUserUtil.getUserRight(sfUser));
					req.setAttribute(WebAttrConstant.MAINTAIN_CORP_OPTION, optionProducer.getMainCorpOption(sfUser.getMaintainCompany()));

					forwardURL = URLDefineList.USER_DETAIL_PAGE;
					forwardURL = URLDefineList.USER_DETAIL_PAGE;
					message.setMessageKey(CustMessageKey.INNER_RES);

				}
			} else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
				userRightStr = StrUtil.nullToString(req.getParameter("right"));
				DTOSet dtoSet = sfUserUtil.convertStr2DTOSet(userRightStr, String.valueOf(dtoParameter.getUserId()));
				boolean operateResult = sfUserDAO.saveData(dtoParameter, dtoSet);

				message = sfUserDAO.getMessage();
				message.setIsError(!operateResult);
				if (operateResult) {
					forwardURL = URLDefineList.USER_LIST_SERVLET;
				} else {
					req.setAttribute(WebConstant.USER_INFO, dtoParameter);
					forwardURL = URLDefineList.USER_DETAIL_PAGE;
				}
			} else if (action.equals(WebActionConstant.DELETE_ACTION)) {
				sfUserDAO.deleteData();
				message = sfUserDAO.getMessage();
				forwardURL = URLDefineList.USER_LIST_PAGE;
			} else if (action.equals("CHOOSE_GROUP")) {
				res.setContentType("text/xml;charset=GBK");
				pw = res.getWriter();
				RowSet rows = sfUserDAO.getGroupOfOu(String.valueOf(organizationId));
				Row row = null;
				if (rows != null && rows.getSize() > 0) {
					for (int i = 0; i < rows.getSize(); i++) {
						row = rows.getRow(i);
						retArray.put(i, row.getValue("GROUP_ID").toString() + "$" + row.getValue("GROUPNAME"));
					}
				}

			} else {
				message = getMessage(MsgKeyConstant.INVALID_REQ);
				message.setIsError(true);
				forwardURL = MessageConstant.MSG_PRC_SERVLET;
			}
		} catch (PoolException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.CONN_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (ContainerException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.CONN_ERROR);
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
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			if (!forwardURL.equals("")) {
				forwarder.forwardView(forwardURL);
			} else {
				pw.print(retArray.toString());
				pw.flush();
				pw.close();
			}
		}
	}
}
