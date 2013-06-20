package com.sino.ams.dzyh.bean;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.dzyh.constant.DzyhLookUpConstant;
import com.sino.ams.dzyh.dto.EamCheckTaskDTO;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.system.basepoint.dto.EtsObjectDTO;
import com.sino.ams.system.item.dto.EtsSystemItemDTO;
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
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统：资产管理模块的LookUpServlet</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class LvecLookUpServlet extends BaseServlet {
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
				if(lookUpName.equals(DzyhLookUpConstant.LOOK_UP_TASK)){
					dispNames = new String[] {"TASK_NAME", "CHECK_TYPE_VALUE", "START_DATE", "END_DATE"};
					dispLabels = new String[] {"任务名称", "任务类别", "开始日期", "截至日期"};
					viewPercent = new String[] {"50%", "15%", "15%", "15%"};
					qryNames = new String[] {"TASK_NAME"};
					qryLabels = new String[] {"任务名称"};
					primaryKeys = new String[] {"CHECK_TASK_ID"};
					retFields = new String[] {"CHECK_TASK_ID", "TASK_NAME", "START_DATE", "END_DATE"};
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(EamCheckTaskDTO.class);
				} else if(lookUpName.equals(DzyhLookUpConstant.LOOK_UP_USER)){
					dispNames = new String[] {"IMPLEMENT_USER", "EMPLOYEE_NUMBER","COMPANY"};
					dispLabels = new String[] {"用户姓名", "员工号", "公司名称"};
					viewPercent = new String[] {"20%", "30%", "40%"};
					retFields = new String[] {"IMPLEMENT_BY", "IMPLEMENT_USER"};
					qryNames = new String[] {"USERNAME", "EMPLOYEE_NUMBER"};
					qryLabels = new String[] {"姓名", "员工号"};
					primaryKeys = new String[] {"IMPLEMENT_BY"};
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(SfUserDTO.class);
				} else if(lookUpName.equals(DzyhLookUpConstant.LOOK_QRY_LOC)){
					dispNames = new String[] {"COMPANY", "COUNTY_NAME", "OBJECT_CATEGORY", "LOCATION_CODE", "LOCATION_NAME"};
					dispLabels = new String[] {"公司名称", "所在区县", "地点专业", "地点代码", "地点名称"};
					viewPercent = new String[] {"17%", "20%", "15%", "15%", "30%"};
					retFields = new String[] {"LOCATION_NAME"};
					qryNames = new String[] {"LOCATION_CODE", "WORKORDER_OBJECT_NAME"};
					qryLabels = new String[] {"地点代码", "地点名称"};
					primaryKeys = new String[] {"WORKORDER_OBJECT_NO"};
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(EtsObjectDTO.class);
				} else if(lookUpName.equals(DzyhLookUpConstant.LOOK_UP_ITEM)){
					dispNames = new String[] {"ITEM_NAME"};
					dispLabels = new String[] {"设备名称"};
					viewPercent = new String[] {"90%"};
					retFields = new String[] {"ITEM_NAME"};
					qryNames = new String[] {"ITEM_NAME"};
					qryLabels = new String[] {"设备名称"};
					primaryKeys = new String[] {"ITEM_NAME"};
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(EtsSystemItemDTO.class);
				}
				lookProp.setCalPattern(LINE_PATTERN);
				lookProp.setDisFieldNames(dispNames);
				lookProp.setDisFieldLabels(dispLabels);
				lookProp.setRetFields(retFields);
				lookProp.setViewPercent(viewPercent);
				lookProp.setQryFieldNames(qryNames);
				lookProp.setQryFieldLabels(qryLabels);
				lookProp.setPrimaryKeys(primaryKeys);
				lookProp.setModelClass(LvecLookUpModel.class);
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
