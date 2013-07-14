package com.sino.ams.yearchecktaskmanager.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.yearchecktaskmanager.dto.AssetsYearCheckTaskLineDTO;
import com.sino.ams.yearchecktaskmanager.dto.AssetsYeartParentOrderDTO;
import com.sino.ams.yearchecktaskmanager.model.CheckTaskLookUpModel;
import com.sino.ams.yearchecktaskmanager.util.AssetsCheckTaskConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.web.WebConstant;
import com.sino.base.log.Logger;
import com.sino.base.lookup.LookUpProp;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;

public class CheckTaskLookUpServlet extends BaseServlet {

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
                String contentReadio = "";
                if (lookUpName.equals(AssetsCheckTaskConstant.LOOK_UP_CITY_MANAGER)) { //ʡ��˾�·�����̵�������ҵ��й�˾�ʲ�����Ա
					dispNames = new String[] {"COMPANY_CODE", "COMPANY",
								"IMPLEMENT_DEPT_NAME", "IMPLEMENT_NAME"};
					dispLabels = new String[] {"��˾����", "��˾����", "���ղ���", "����ִ����"};
					viewPercent = new String[] {"20%", "20%", "40%", "20%"};
					qryNames = new String[] {"COMPANY_CODE", "IMPLEMENT_NAME"};
					retFields = new String[] {"IMPLEMENT_ORGANIZATION_ID","ORDER_NUMBER","COMPANY_CODE", "COMPANY",
							"IMPLEMENT_DEPT_NAME", "IMPLEMENT_NAME","IMPLEMENT_DEPT_ID","IMPLEMENT_BY"};
					qryLabels = new String[] {"��˾����", "ִ��������"};
					primaryKeys = new String[] {"IMPLEMENT_NAME"};

					lookProp.setTotalWidth(600);
					lookProp.setMultipleChose(true);
					lookProp.setDtoClass(AssetsYearCheckTaskLineDTO.class);
				}else if(lookUpName.equals("LOOK_UP_PARENT_ORDER_LEVE_2")||lookUpName.equals("LOOK_UP_PARENT_ORDER_LEVE_3")){//���ҵ�ǰ�˵��̵�����
					
					dispNames = new String[] {"PARENT_ORDER_NAME", "PARENT_ORDER_NUMBER","PARENT_ORDER_TYPE_NAME",
							"PARENT_IMPLEMNET_DEPT_NAME"};
					dispLabels = new String[] {"��������", "������", "��������","���ղ���"};
					viewPercent = new String[] {"20%", "20%","20%","40%"};
					qryNames = new String[] {"ORDER_NUMBER"};
					retFields = new String[] {"PARENT_ORDER_NAME", "PARENT_ORDER_NUMBER","PARENT_ORDER_TYPE_NAME","PARENT_ORDER_TYPE","PARENT_IMPLEMNET_DEPT_NAME"};
					qryLabels = new String[] {"������"};
					primaryKeys = new String[] {"PARENT_ORDER_NUMBER"};
	
					lookProp.setTotalWidth(600);
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AssetsYeartParentOrderDTO.class);
				}else if(lookUpName.equals("CITY_ADDRESS_FOR_CHECK_PERSON") || lookUpName.equals("CITY_ADDRESS_FOR_DEPT")
						||lookUpName.equals("CITY_NON_ADDRESS_SOFTEWARE_1")||lookUpName.equals("CITY_NON_ADDRESS_SOFTEWARE_2")
						|| lookUpName.equals("CITY_NON_ADDRESS_SOFTEWARE_3") || lookUpName.equals("CITY_NON_ADDRESS_CLIENT_1")
						|| lookUpName.equals("CITY_NON_ADDRESS_CLIENT_2") || lookUpName.equals("CITY_NON_ADDRESS_CLIENT_3")
						|| lookUpName.equals("CITY_NON_ADDRESS_PIPELINE_1")||lookUpName.equals("CITY_NON_ADDRESS_PIPELINE_2")
						|| lookUpName.equals("CITY_NON_ADDRESS_PIPELINE_3")){//�����·���ѯ
					//ʵ���ʲ������߲����̵������ˣ��������Ҳ��Ź���Ա
					//��ʵ���ʲ������Ҳ��Ź���Ա
					dispNames = new String[] {"IMPLEMENT_DEPT_NAME","EMPLOYEE_NUMBER", "IMPLEMENT_NAME"
							};
					dispLabels = new String[] {"���ղ���", "Ա�����","����ִ����"};
					viewPercent = new String[] {"40%", "20%", "40%"};
					qryNames = new String[] {"EMPLOYEE_NUMBER","IMPLEMENT_DEPT_NAME"};
					retFields = new String[] {"ORDER_NUMBER","ORDER_NAME","ORDER_TYPE", "ORDER_TYPE_NAME","IMPLEMENT_DEPT_NAME",
							"IMPLEMENT_DEPT_ID", "IMPLEMENT_ORGANIZATION_ID","IMPLEMENT_NAME","IMPLEMENT_BY","EMPLOYEE_NUMBER"};
					qryLabels = new String[] {"Ա�����","���ղ���"};
					primaryKeys = new String[] {"IMPLEMENT_BY"};
					lookProp.setTotalWidth(600);
					lookProp.setMultipleChose(true);
					lookProp.setDtoClass(AssetsYearCheckTaskLineDTO.class);
				}else if(lookUpName.equals("DEPT_ADDRESS_FOR_CHECK_PERSON") || lookUpName.equals("DEPT_ADDRESS_FOR_DEPT")
						||lookUpName.equals("DEPT_NON_ADDRESS_SOFTEWARE") || lookUpName.equals("DEPT_NON_ADDRESS_CLIENT")
						|| lookUpName.equals("DEPT_NON_ADDRESS_PIPELINE")){//�����·���ѯ
					//ʵ���ʲ������߲����̵������ˣ��������Ҳ��Ź���Ա
					//��ʵ���ʲ������Ҳ��Ź���Ա
					dispNames = new String[] {"IMPLEMENT_DEPT_NAME","EMPLOYEE_NUMBER", "IMPLEMENT_NAME"
							};
					dispLabels = new String[] {"���ղ���", "Ա�����","����ִ����"};
					viewPercent = new String[] {"40%", "20%", "40%"};
					qryNames = new String[] {"EMPLOYEE_NUMBER","IMPLEMENT_DEPT_NAME"};
					retFields = new String[] {"ORDER_NUMBER","ORDER_NAME","ORDER_TYPE", "ORDER_TYPE_NAME","IMPLEMENT_DEPT_NAME",
							"IMPLEMENT_DEPT_ID", "IMPLEMENT_ORGANIZATION_ID","IMPLEMENT_NAME","IMPLEMENT_BY","EMPLOYEE_NUMBER"};
					qryLabels = new String[] {"Ա�����","���ղ���"};
					primaryKeys = new String[] {"IMPLEMENT_BY"};
					lookProp.setTotalWidth(600);
					lookProp.setMultipleChose(true);
					lookProp.setDtoClass(AssetsYearCheckTaskLineDTO.class);
				}
				lookProp.setCalPattern(LINE_PATTERN);
				lookProp.setDisFieldNames(dispNames);
				lookProp.setDisFieldLabels(dispLabels);
				lookProp.setRetFields(retFields);
				lookProp.setViewPercent(viewPercent);
				lookProp.setQryFieldNames(qryNames);
				lookProp.setQryFieldLabels(qryLabels);
				lookProp.setPrimaryKeys(primaryKeys);
                lookProp.setContentReadio(contentReadio);
                lookProp.setModelClass(CheckTaskLookUpModel.class);
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
