package com.sino.ams.sampling.bean;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.sampling.constant.SamplingLookUpConstant;
import com.sino.ams.system.basepoint.dto.EtsObjectDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.web.WebConstant;
import com.sino.base.log.Logger;
import com.sino.base.lookup.LookUpProp;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;

/**
 * <p>Title: SinoAMS</p>
 * <p>Description: 山西移动实物资产管理系统：资产管理模块的LookUpServlet</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class SamplingLookUpServlet extends BaseServlet {
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
				message = getMessage(AssetsMessageKeys.INVALID_REQ);
				message.setIsError(true);
				message.setNeedClose(true);
				forwardURL = MessageConstant.MSG_PRC_SERVLET;
			} else {
				LookUpProp lookProp = new LookUpProp(lookUpName);
				String[] dispNames = null;
				String[] retFields = null;
				String[] dispLabels = null;
				String[] viewPercent = null;
				String[] qryNames = null;
				String[] qryLabels = null;
				String[] primaryKeys = null;
				if(lookUpName.equals(SamplingLookUpConstant.LOOK_SAMPLING_LOC)){
					dispNames = new String[] {"COMPANY", "COUNTY_NAME", "OBJECT_CATEGORY",
								"SAMPLING_LOCATION_CODE", "SAMPLING_LOCATION_NAME", "EII_COUNT"};
					dispLabels = new String[] {"公司名称", "所在区县", "地点专业", "地点代码", "地点名称","数量"};
					viewPercent = new String[] {"10%", "15%", "8%", "15%", "25%","8%"};
					retFields = new String[] {"SAMPLING_LOCATION", "SAMPLING_LOCATION_CODE", "SAMPLING_LOCATION_NAME"};
					qryNames = new String[] {"WORKORDER_OBJECT_CODE",
							   "WORKORDER_OBJECT_NAME"};
					qryLabels = new String[] {"地点代码", "地点名称"};
					primaryKeys = new String[] {"SAMPLING_LOCATION"};
					lookProp.setMultipleChose(true);
					lookProp.setDtoClass(EtsObjectDTO.class);
				} else if(lookUpName.equals(SamplingLookUpConstant.LOOK_QRY_LOC)){
					dispNames = new String[] {"COMPANY", "COUNTY_NAME", "OBJECT_CATEGORY", "SAMPLING_LOCATION_CODE", "SAMPLING_LOCATION_NAME"};
					dispLabels = new String[] {"公司名称", "所在区县", "地点专业", "地点代码", "地点名称"};
					viewPercent = new String[] {"17%", "20%", "15%", "15%", "30%"};
					retFields = new String[] {"SAMPLING_LOCATION_NAME"};
					qryNames = new String[] {"WORKORDER_OBJECT_CODE", "WORKORDER_OBJECT_NAME"};
					qryLabels = new String[] {"地点代码", "地点名称"};
					primaryKeys = new String[] {"SAMPLING_LOCATION"};
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(EtsObjectDTO.class);
				} else if (lookUpName.equals(SamplingLookUpConstant.LOOK_QRY_USER)) {
					dispNames = new String[] {"IMPLEMENT_USER", "EMPLOYEE_NUMBER", "LOGIN_NAME", "COMPANY"};
					dispLabels = new String[] {"用户姓名", "用户员工号", "用户登录名", "公司名称"};
					viewPercent = new String[] {"18%", "18%", "18%", "40%"};
					retFields = new String[] {"IMPLEMENT_USER"};
					qryNames = new String[] {"USERNAME", "EMPLOYEE_NUMBER", "LOGIN_NAME"};
					qryLabels = new String[] {"姓名", "员工号", "登录名"};
					primaryKeys = new String[] {"USER_ID"};

					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(SfUserDTO.class);
				}
				lookProp.setCalPattern(LINE_PATTERN);
				lookProp.setDisFieldNames(dispNames);
				lookProp.setDisFieldLabels(dispLabels);
				lookProp.setRetFields(retFields);
				lookProp.setViewPercent(viewPercent);
				lookProp.setQryFieldNames(qryNames);
				lookProp.setQryFieldLabels(qryLabels);
				lookProp.setPrimaryKeys(primaryKeys);
				lookProp.setModelClass(SamplingLookUpModel.class);
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
