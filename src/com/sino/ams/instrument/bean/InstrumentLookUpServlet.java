package com.sino.ams.instrument.bean;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.instrument.constant.InstrumentLookUpConstant;
import com.sino.ams.instrument.dto.AmsInstrumentEamYbChkMaintainDTO;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
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

public class InstrumentLookUpServlet extends BaseServlet {

	/**
	 * 所有的Servlet都必须实现的方法。
	 *
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
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
				if(lookUpName.equals(InstrumentLookUpConstant.LOOK_UP_CHECK_USER)){ //选择检修人
					dispNames = new String[] {"USER_ID", "USERNAME"};
					dispLabels = new String[] {"检修人ID号", "检修人姓名"};
					viewPercent = new String[] {"30%", "40%"};
					retFields = new String[] {"USER_ID", "USERNAME"};
					qryNames = new String[] {"USER_ID", "USERNAME"};
					qryLabels = new String[] {"检修人ID号", "检修人姓名"};
					primaryKeys = new String[] {"USER_ID"};
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(SfUserDTO.class);
				} else if(lookUpName.equals(InstrumentLookUpConstant.LOOK_UP_CHECK_RESULT)) { //选择检修结果
					dispNames = new String[] {"CHECK_STATUS", "CHECK_STATUS_NAME"};
					dispLabels = new String[] {"检修结果代码", "检修结果名称"};
					viewPercent = new String[] {"30%", "40%"};
					retFields = new String[] {"CHECK_STATUS", "CHECK_STATUS_NAME"};
					qryNames = new String[] {"CHECK_STATUS"};
					qryLabels = new String[] {"检修结果代码"};
					primaryKeys = new String[] {"CHECK_STATUS"};
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsInstrumentEamYbChkMaintainDTO.class);
				} else if(lookUpName.equals(InstrumentLookUpConstant.LOOK_UP_TASK_ID)) { //选择检修任务批号
					dispNames = new String[] {"TASK_ID", "TASK_NAME"};
					dispLabels = new String[] {"任务批号代码", "任务批号名称"};
					viewPercent = new String[] {"30%", "40%"};
					retFields = new String[] {"TASK_ID", "TASK_NAME"};
					qryNames = new String[] {"TASK_ID", "TASK_NAME"};
					qryLabels = new String[] {"任务批号代码", "任务批号名称"};
					primaryKeys = new String[] {"TASK_ID"};
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsInstrumentEamYbChkMaintainDTO.class);
				}
				lookProp.setCalPattern(LINE_PATTERN);
				lookProp.setDisFieldNames(dispNames);
				lookProp.setDisFieldLabels(dispLabels);
				lookProp.setRetFields(retFields);
				lookProp.setViewPercent(viewPercent);
				lookProp.setQryFieldNames(qryNames);
				lookProp.setQryFieldLabels(qryLabels);
				lookProp.setPrimaryKeys(primaryKeys);
				lookProp.setModelClass(InstrumentLoopUpModel.class);
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
