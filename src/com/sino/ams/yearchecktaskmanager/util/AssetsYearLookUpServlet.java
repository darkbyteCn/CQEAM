package com.sino.ams.yearchecktaskmanager.util;



import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.LookUpConstant;
import com.sino.ams.newasset.assetsharing.dto.AssetSharingLineDTO;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsLookUpConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.dto.AmsAccountVDTO;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.newasset.dto.AmsAssetsCheckBatchDTO;
import com.sino.ams.newasset.dto.AmsAssetsPriviDTO;
import com.sino.ams.newasset.dto.AmsAssetsTransLineDTO;
import com.sino.ams.newasset.dto.AmsCexDTO;
import com.sino.ams.newasset.dto.AmsFaCategoryVDTO;
import com.sino.ams.newasset.dto.AmsLneDTO;
import com.sino.ams.newasset.dto.AmsMisCostDTO;
import com.sino.ams.newasset.dto.AmsMisEmployeeDTO;
import com.sino.ams.newasset.dto.AmsNleDTO;
import com.sino.ams.newasset.dto.AmsOpeDTO;
import com.sino.ams.newasset.dto.AmsSnDTO;
import com.sino.ams.newasset.lease.dto.LeaseLineDTO;
import com.sino.ams.newasset.rolequery.dto.SfRoleQueryDTO;
import com.sino.ams.system.basepoint.dto.EtsObjectDTO;
import com.sino.ams.system.cost.dto.CostCenterDTO;
import com.sino.ams.system.fixing.dto.EtsItemInfoDTO;
import com.sino.ams.system.item.dto.EtsSystemItemDTO;
import com.sino.ams.system.procedure.dto.MisDeptDTO;
import com.sino.ams.system.project.dto.EtsPaProjectsAllDTO;
import com.sino.ams.system.user.dto.EtsOuCityMapDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dto.ZeroTurnLineDTO;
import com.sino.ams.yearchecktaskmanager.dto.EtsItemYearCheckDTO;
import com.sino.ams.yearchecktaskmanager.dto.EtsObjectTaskDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.web.WebConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.lookup.LookUpProp;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: ɽ���ƶ�ʵ���ʲ�����ϵͳ���ʲ�����ģ���LookUpServlet</p>
 * <p>Copyright: ����˼ŵ����Ϣ�������޹�˾��Ȩ����Copyright (c) 2007</p>
 * <p>Company: ����˼ŵ����Ϣ�������޹�˾</p>
 * @author ����ʤ
 * @version 1.0
 */
public class AssetsYearLookUpServlet extends BaseServlet {
	/**
	 * ���е�Servlet������ʵ�ֵķ�����
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
                String contentReadio = "";
                if (lookUpName.equals("LOOK_UP_ORDER_TASK")) { //ѡ���̵�����
					dispNames = new String[] {"TASK_NUMBER", "TASK_NAME"};
					dispLabels = new String[] {"������", "��������"};
					viewPercent = new String[] {"50%", "50%"};
					retFields = new String[] {"TASK_NUMBER",
								"TASK_NAME", "TASK_TYPE"};
					qryNames = new String[] {"ORDER_NUMBER ", "ORDER_NAME "};
					qryLabels = new String[] {"�������", "��������"};
					primaryKeys = new String[] {"TASK_NUMBER"};
					lookProp.setTotalWidth(700);
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsAssetsCheckBatchDTO.class);
				}else if (lookUpName.equals("LOOK_UP_LOCATION_TASK")) { //���ҵص�
					dispNames = new String[] {"COMPANY_NAME", "COUNTY_NAME",
								"OBJECT_CODE", "OBJECT_NAME"};
					dispLabels = new String[] {"��˾����", "�ɱ�����", "�ص����","�ص�����"};
//					dispNames = new String[] {"COMPANY_NAME", "COUNTY_NAME",
//							"OBJECT_CATEGORY", "OBJECT_CODE", "OBJECT_NAME"};
//					dispLabels = new String[] {"��˾����", "�ɱ�����", "�ص�רҵ", "�ص����","�ص�����"};
					viewPercent = new String[] {"20%", "20%",  "20%", "40%"};
					retFields = new String[] {"CHECK_LOCATION", "OBJECT_CODE",	"OBJECT_NAME"};
					qryNames = new String[] {"WORKORDER_OBJECT_CODE",
							   "WORKORDER_OBJECT_NAME", "COUNTY_NAME"};
					qryLabels = new String[] {"�ص����", "�ص�����", "�ɱ�����"};
					primaryKeys = new String[] {"CHECK_LOCATION"};
                    lookProp.setTotalWidth(950);
					lookProp.setMultipleChose(true);
					lookProp.setDtoClass(EtsObjectTaskDTO.class);
				} else if (lookUpName.equals("LOOK_UP_NO_ADDRESS_TASK")) { //���ҵص�
					dispNames = new String[] {"ORDER_NUMBER", "ORDER_NAME","ROLE_NAME","DEPT_NAME","ORDER_TYPE"};
					dispLabels = new String[] {"������", "��������","�����˽�ɫ","��������������","��������"};
					viewPercent = new String[] {"20%", "20%","20%","20%","20%"};
					retFields = new String[] {"ORDER_NUMBER",
								"ORDER_NAME", "ROLE_NAME","DEPT_NAME","ORDER_TYPE"};
					qryNames = new String[] {"ORDER_NUMBER", "ORDER_NAME",};
					qryLabels = new String[] {"�������", "��������"};
					primaryKeys = new String[] {"ORDER_NUMBER"};
					lookProp.setTotalWidth(700);
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(EtsItemYearCheckDTO.class);			  } 
				lookProp.setCalPattern(LINE_PATTERN);
				lookProp.setDisFieldNames(dispNames);
				lookProp.setDisFieldLabels(dispLabels);
				lookProp.setRetFields(retFields);
				lookProp.setViewPercent(viewPercent);
				lookProp.setQryFieldNames(qryNames);
				lookProp.setQryFieldLabels(qryLabels);
				lookProp.setPrimaryKeys(primaryKeys);
                lookProp.setContentReadio(contentReadio);
                lookProp.setModelClass(AssetsYearLookUpModel.class);
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
