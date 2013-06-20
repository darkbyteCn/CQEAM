package com.sino.sinoflow.bean;

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
import com.sino.sinoflow.constant.LookUpConstant;
import com.sino.sinoflow.dto.SinoMisDeptDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by IntelliJ IDEA. User: Date: 2007-9-24 Time: 10:26:58
 */
public class SfLookUpServlet extends BaseServlet {

	private static final long serialVersionUID = -8942009582351380303L;

	public void performTask(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String forwardURL = "";
		String lookUpName = req.getParameter("lookUpName");
		boolean mulChoose = false;
		String tmpMulChoose = req.getParameter("mulChoose"); // 是单选还是多选
		// boolean memorySpan = false;
		// String tmpMemorySpan = req.getParameter("memorySpan");
		// //是否打开页面时直接执行查询
		boolean arrived = false;
		String tmpArrived = req.getParameter("arrived");
		forwardURL = WebConstant.LOOK_UP_SERVLET;

		if ("true".equalsIgnoreCase(tmpMulChoose)) {
			mulChoose = true;
		}

		// if ("true".equalsIgnoreCase(tmpMemorySpan)) {
		// memorySpan = true;
		// }

		if ("true".equalsIgnoreCase(tmpArrived)) {
			arrived = true;
		}

		lookUpName = StrUtil.nullToString(lookUpName);
		Message message = null;

		try {
			if (lookUpName.equals("")) {
				message = getMessage(MsgKeyConstant.INVALID_REQ);
				message.setIsError(true);
				forwardURL = MessageConstant.MSG_PRC_SERVLET;
			} else {
				LookUpProp lookProp = new LookUpProp(lookUpName);
				String[] dispFieldNames = null;
				String[] retFields = null;
				String[] dispFieldLabels = null;
				String[] viewPercent = null;
				String[] qryFieldNames = null;
				String[] qryFieldLabels = null;
				String[] primaryKeys = null;
				if (lookUpName.equals(LookUpConstant.LOOK_UP_PARENT_DEPT)) { // 查找普通用户
                    dispFieldNames = new String[] {"DEPT_ID", "DEPT_NAME",
                                "DEPT_CODE"};
                    dispFieldLabels = new String[] {"部门ID", "部门名称", "部门代码"};
                    viewPercent = new String[] {"10%", "40%", "40%"};
                    retFields = dispFieldNames;
                    qryFieldNames = new String[] {"DEPT_NAME", "DEPT_CODE"};
                    qryFieldLabels = new String[] {"部门名称", "部门代码"};
                    primaryKeys = new String[] {"DEPT_ID"};
                    lookProp.setTotalWidth(700);
                    lookProp.setMultipleChose(false);
                    lookProp.setDtoClass(SinoMisDeptDTO.class);
				}
				lookProp.setMultipleChose(mulChoose);
				lookProp.setDisFieldNames(dispFieldNames);
				lookProp.setDisFieldLabels(dispFieldLabels);
				lookProp.setRetFields(retFields);
				lookProp.setViewPercent(viewPercent);
				lookProp.setQryFieldNames(qryFieldNames);
				lookProp.setQryFieldLabels(qryFieldLabels);
				lookProp.setPrimaryKeys(primaryKeys);
				lookProp.setModelClass(SfLookUpModel.class);

				req.setAttribute("arrived", arrived);
				SessionUtil.saveLoopUpProp(req, lookProp);
			}
		} catch (Exception ex) {
			Logger.logError(ex);
		} finally {
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
		}
	}
}
