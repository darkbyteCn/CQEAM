package com.sino.ams.dzyh.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.dzyh.constant.DzyhLookUpConstant;
import com.sino.ams.dzyh.dto.EamDhPriviDTO;
import com.sino.ams.dzyh.model.DzyhPriviLookUpModel;
import com.sino.ams.system.user.dto.EtsOuCityMapDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebConstant;
import com.sino.base.log.Logger;
import com.sino.base.lookup.LookUpProp;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统：资产管理模块的LookUpServlet</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 张星
 * @version 1.0
 */
public class DzyhPriviLookUpServlet extends BaseServlet {
	/**
	 * 所有的Servlet都必须实现的方法。
	 *
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws
			ServletException, IOException {
		String forwardURL = "";
		String lookUpName = req.getParameter("lookUpName");
		lookUpName = StrUtil.nullToString(lookUpName);
		Message message = SessionUtil.getMessage(req);
		try {
			if (lookUpName.equals("")) {
				message = getMessage(MsgKeyConstant.INVALID_REQ);
				message.setIsError(true);
				message.setNeedClose(true);
				forwardURL = MessageConstant.MSG_PRC_SERVLET;
			} else {
				LookUpProp lookProp = new LookUpProp(lookUpName);
				String[] dispNames = null;
				String[] dispLabels = null;
				String[] retFields = null;
				String[] viewPercent = null;
				String[] qryNames = null;
				String[] qryLabels = null;
				String[] primaryKeys = null;
				if (lookUpName.equals(DzyhLookUpConstant.
											 LOOK_UP_COMPANY)) { //查找公司
					dispNames = new String[] {"COMPANY_CODE", "COMPANY_NAME"};
					dispLabels = new String[] {"公司代码", "公司名称"};
					viewPercent = new String[] {"25%", "50%"};
					retFields = new String[] {"ORGANIZATION_ID", "COMPANY_CODE",
								"COMPANY_NAME"};
					qryNames = new String[] {"COMPANY_NAME"};
					qryLabels = new String[] {"公司名称"};
					primaryKeys = new String[] {"ORGANIZATION_ID"};
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(EtsOuCityMapDTO.class);
				} else if (lookUpName.equals(DzyhLookUpConstant.
											 LOOK_UP_PRI_DEPT)) { //查找部门
					dispNames = new String[] {"COMPANY_CODE", "COMPANY_NAME",
								"DEPT_CODE", "DEPT_NAME"};
					dispLabels = new String[] {"公司代码", "公司名称", "部门代码", "部门名称"};
					viewPercent = new String[] {"15%", "30%", "15%", "35%"};
					retFields = dispNames;
					qryNames = new String[] {"COMPANY_NAME", "DEPT_NAME"};
					qryLabels = new String[] {"公司名称", "部门名称"};
					primaryKeys = new String[] {"DEPT_CODE"};
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(EamDhPriviDTO.class);
				} else if (lookUpName.equals(DzyhLookUpConstant.LOOK_UP_PRI_USER)) { //查找用户
					dispNames = new String[] {"USER_NAME", "EMPLOYEE_NUMBER",
								"LOGIN_NAME", "COMPANY_CODE", "COMPANY_NAME"};
					dispLabels = new String[] {"用户姓名", "用户员工号", "用户登录名", "公司代码",
								 "公司名称"};
					viewPercent = new String[] {"15%", "30%", "15%", "15%",
								  "20%"};
					retFields = new String[] {"USER_ID", "COMPANY_CODE",
								"COMPANY_NAME", "USER_NAME", "EMPLOYEE_NUMBER",
								"LOGIN_NAME"};
					qryNames = new String[] {"USER_NAME", "EMPLOYEE_NUMBER",
							   "LOGIN_NAME"};
					qryLabels = new String[] {"姓名", "员工号", "登录名"};
					primaryKeys = new String[] {"USER_ID"};

					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(EamDhPriviDTO.class);
				}
				lookProp.setCalPattern(LINE_PATTERN);
				lookProp.setDisFieldNames(dispNames);
				lookProp.setDisFieldLabels(dispLabels);
				lookProp.setRetFields(retFields);
				lookProp.setViewPercent(viewPercent);
				lookProp.setQryFieldNames(qryNames);
				lookProp.setQryFieldLabels(qryLabels);
				lookProp.setPrimaryKeys(primaryKeys);
				lookProp.setModelClass(DzyhPriviLookUpModel.class);
				SessionUtil.saveLoopUpProp(req, lookProp);

				forwardURL = WebConstant.LOOK_UP_SERVLET;
			}
		} catch (Exception ex) {
			Logger.logError(ex);
			throw new ServletException(ex);
		} finally {
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
		}
	}
}
