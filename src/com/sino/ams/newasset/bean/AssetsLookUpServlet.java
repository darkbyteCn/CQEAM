package com.sino.ams.newasset.bean;

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
import com.sino.ams.plan.dto.AmsWorkPlanDTO;
import com.sino.ams.system.basepoint.dto.EtsObjectDTO;
import com.sino.ams.system.cost.dto.CostCenterDTO;
import com.sino.ams.system.fixing.dto.EtsItemInfoDTO;
import com.sino.ams.system.item.dto.EtsSystemItemDTO;
import com.sino.ams.system.procedure.dto.MisDeptDTO;
import com.sino.ams.system.project.dto.EtsPaProjectsAllDTO;
import com.sino.ams.system.user.dto.EtsOuCityMapDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dto.ZeroTurnLineDTO;
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
public class AssetsLookUpServlet extends BaseServlet {
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
                if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_DEPT)) { //ѡ����ղ���
					dispNames = new String[] {"COMPANY_CODE", "TO_COMPANY_NAME", "TO_DEPT_NAME"};
					dispLabels = new String[] {"��˾����", "��˾����", "��������"};
					viewPercent = new String[] {"8%", "30%", "58%"};
					retFields = new String[] {"TO_ORGANIZATION_ID",
								"COMPANY_CODE", "TO_COMPANY_NAME", "TO_DEPT",
								"TO_DEPT_NAME","TO_GROUP"};
					qryNames = new String[] {"COMPANY_NAME", "DEPT_NAME"};
					qryLabels = new String[] {"��˾����", "��������"};
					primaryKeys = new String[] {"TO_DEPT"};

					lookProp.setTotalWidth(700);
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(MisDeptDTO.class);
				}else if(lookUpName.equals(AssetsLookUpConstant.LOOK_UP_DEPT_NM)){ //����ָ��רҵ�ʲ�����Ա�Ĳ��� ѡ����ղ���
					dispNames = new String[] {"COMPANY_CODE", "TO_COMPANY_NAME",
								"TO_DEPT_NAME"};
					dispLabels = new String[] {"��˾����", "��˾����", "��������"};
					viewPercent = new String[] {"8%", "30%", "58%"};
					retFields = new String[] {"TO_ORGANIZATION_ID",
								"COMPANY_CODE", "TO_COMPANY_NAME", "TO_DEPT",
								"TO_DEPT_NAME"};
					qryNames = new String[] {"COMPANY_NAME", "DEPT_NAME"};
					qryLabels = new String[] {"��˾����", "��������"};
					primaryKeys = new String[] {"TO_DEPT"};

					lookProp.setTotalWidth(700);
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(MisDeptDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.
											 LOOK_UP_ADDRESS)) { //�ʲ�ҵ����ҵص�
					dispNames = new String[] {"WORKORDER_OBJECT_CODE",
								"TO_OBJECT_NAME",/* "WORKORDER_OBJECT_LOCATION",*/
								"COUNTY_NAME", "COMPANY"};
					dispLabels = new String[] {"�ص����", "�ص�����", "��������",
								 "��˾����"};
					viewPercent = new String[] {"16%",  "36%", "16%", "12%"};
					qryNames = new String[] {"WORKORDER_OBJECT_CODE",
							   "WORKORDER_OBJECT_NAME"};
					qryLabels = new String[] {"�ص����", "�ص�����"};
					primaryKeys = new String[] {"TO_OBJECT_NO"};

					lookProp.setTotalWidth(875);
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(EtsObjectDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.
											 LOOK_UP_RECIIVER)) { //�ʲ�ҵ�����Ŀ��������
					dispNames = new String[] {"USER_NAME", "EMPLOYEE_NUMBER",
								"DEPT_NAME", "COMPANY"};
					dispLabels = new String[] {"Ա������", "Ա������", "��������", "��˾����"};
					viewPercent = new String[] {"14%", "13%", "45%", "25%"};
					qryNames = new String[] {"USER_NAME", "EMPLOYEE_NUMBER"};
					qryLabels = new String[] {"Ա������", "Ա������"};
					primaryKeys = new String[] {"EMPLOYEE_ID"};
					lookProp.setTotalWidth(746);
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsMisEmployeeDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.
											 LOOK_UP_ASSETS)) { //�ʲ����̲����ʲ�
					dispNames = new String[] {"BARCODE", "ASSET_NUMBER",
								"ASSETS_DESCRIPTION", "MODEL_NUMBER", "COST",
								"DATE_PLACED_IN_SERVICE", "DEPRN_COST",
								"RESPONSIBILITY_USER_NAME", "DEPT_NAME"};
					dispLabels = new String[] {"�ʲ���ǩ", "�ʲ����", "�ʲ�����", "�ʲ��ͺ�",
								 "ԭֵ", "��������", "�ۼ��۾�", "������", "���β���"};
					viewPercent = new String[] {"12%", "8%", "17%", "14%", "7%",
								  "10%", "7%", "8%", "14%"};
					qryNames = new String[] {"ITEM_NAME", "BARCODE"};
					retFields = dispNames;
					qryLabels = new String[] {"�����ؼ���", "��ǩ��"};
					primaryKeys = new String[] {"BARCODE"};

					lookProp.setTotalWidth(820);
					lookProp.setDtoClass(AmsAssetsTransLineDTO.class);
				}else if(lookUpName.equals(AssetsLookUpConstant.LOOK_UP_RENT)){
					 dispNames = new String[] {"BARCODE","ITEM_NAME","ITEM_SPEC",
								"ITEM_QTY", "USER_NAME", "DEPT_NAME"};
					dispLabels = new String[] {"�ʲ���ǩ", "�ʲ�����", "�ʲ��ͺ�","����", "������", "���β���"};
					viewPercent = new String[] {"10%", "10%","15%","8%", "8%","20%"};
					qryNames = new String[] {"ITEM_NAME", "BARCODE",
							   "ITEM_SPEC", "USER_NAME"};
					qryLabels = new String[] {"�ʲ�����", "��ǩ��", "����ͺ�", "������"};
					primaryKeys = new String[] {"BARCODE"};
					lookProp.setTotalWidth(820);
					lookProp.setDtoClass(AmsAssetsTransLineDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.
											 LOOK_TRANSFER_ASSETS )
						|| lookUpName.equals(AssetsLookUpConstant.
													 LOOK_TRANSFER_DIS_ASSETS ) ) { //�ʲ��������̲����ʲ�
//					dispNames = new String[] {"BARCODE", "ASSET_NUMBER", "ITEM_NAME","ITEM_SPEC",
//							"ASSETS_DESCRIPTION", "MODEL_NUMBER", "COST",
//							"DATE_PLACED_IN_SERVICE", "DEPRN_COST",
//							"OLD_RESPONSIBILITY_USER_NAME",
//							"OLD_RESPONSIBILITY_DEPT_NAME","OLD_LOCATION_CODE",
//							"OLD_LOCATION_NAME", "MANUFACTURER_NAME" };
//					dispLabels = new String[] {"�ʲ���ǩ", "�ʲ����", "�豸����", "�豸�ͺ�", "�ʲ�����", "�ʲ��ͺ�",
//							 "ԭֵ", "��������", "��ֵ", "������", "���β���", "�ص���", "�ص���", "����" };
//					viewPercent = new String[] {"8%", "4%","5%","5%", "5%",   "5%", "4%", "4%", "4%", "3%",    "9%", "8%", "9%","9%" };
				
					dispNames = new String[] {"BARCODE", "ASSET_NUMBER","ITEM_NAME","ITEM_SPEC",
								"ASSETS_DESCRIPTION", "MODEL_NUMBER", "COST",
								"DATE_PLACED_IN_SERVICE", "DEPRN_COST",
								"OLD_RESPONSIBILITY_USER_NAME",
								"OLD_RESPONSIBILITY_DEPT_NAME", "SPECIALITY_DEPT_NAME", "OLD_LOCATION_CODE",
								"OLD_LOCATION_NAME", "MANUFACTURER_NAME","IMPORTANT_FLAG"};
					dispLabels = new String[] {"�ʲ���ǩ", "�ʲ����", "�豸����", "�豸�ͺ�", "�ʲ�����", "�ʲ��ͺ�",
								 "ԭֵ", "��������", "��ֵ","������", "���β���", "ʵ�������", "�ص���", "�ص���", "����", "��Ҫ�ʲ�"};
					viewPercent = new String[] {"8%", "4%","5%","5%", "5%", "5%", "4%", "4%", "4%", "3%", "9%", "8%", "8%", "9%","9%","4%"};
                    retFields = new String[]  {"BARCODE", "ASSET_NUMBER","ITEM_NAME","ITEM_SPEC",
								"ASSETS_DESCRIPTION", "MODEL_NUMBER", "COST",
								"DATE_PLACED_IN_SERVICE", "DEPRN_COST","SCRAP_VALUE",
								"OLD_RESPONSIBILITY_USER_NAME",
								"OLD_RESPONSIBILITY_DEPT_NAME", "SPECIALITY_DEPT_NAME", "OLD_LOCATION_CODE",
								"OLD_LOCATION_NAME", "MANUFACTURER_NAME","DEPRN_LEFT_MONTH","SUM_DEPRECIATION","IMPAIR_RESERVE","RETIREMENT_COST","CURRENT_UNITS","OLD_FA_CATEGORY_CODE","OLD_RESPONSIBILITY_USER_NAME","OLD_DEPRECIATION_ACCOUNT","OLD_LOCATION","OLD_RESPONSIBILITY_USER","OLD_RESPONSIBILITY_DEPT","IMPORTANT_FLAG","UNIT_OF_MEASURE"};
                    if( lookUpName.equals(AssetsLookUpConstant.
											 LOOK_TRANSFER_ASSETS ) ){
                    	qryNames = new String[] {"ASSETS_DESCRIPTION", "BARCODE",
 							   "OLD_RESPONSIBILITY_USER_NAME", "OLD_LOCATION_CODE"};
                    	qryLabels = new String[] {"�ʲ�����", "��ǩ��", "������", "�ص�"};
                    }else{
                    	qryNames = new String[] {"ASSETS_DESCRIPTION", "BARCODE",  "OLD_LOCATION_CODE"};
                    	qryLabels = new String[] {"�ʲ�����", "��ǩ��",  "�ص�"};
                    }
                    
					
					primaryKeys = new String[] {"BARCODE"};
					lookProp.setTotalWidth(800);
					
					String transferType = StrUtil.nullToString( req.getParameter( "transferType" ) );
					if( transferType.equals( AssetsDictConstant.TRANS_BTW_COMP ) ){
						lookProp.setCountPages( false );
					}
					lookProp.setDtoClass(AmsAssetsTransLineDTO.class);
					
				} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_TRANSFER_ASSETS_RFU )) { //���ҵ����ʲ�(�������������ܵ�����)
					dispNames = new String[] {
								"TRANS_NO",  
								"BARCODE", "ASSET_NUMBER", "ASSETS_DESCRIPTION", "MODEL_NUMBER", "CURRENT_UNITS", "UNIT_OF_MEASURE", 
								"FROM_OBJECT_NAME", "TO_OBJECT_NAME"};
					dispLabels = new String[] {
								"��������", 
								"��ǩ��", "�ʲ����", "�ʲ�����", "�ʲ��ͺ�", "����", "��λ",
								"�����ص�", "����ص�"};
					viewPercent = new String[] {
								"14%", 
								"8%", "7%", "12%", "10%", "8%", "5%", 
								"18%", "18%"};
					retFields = new String[]  {
								"REMARK", "OLD_LOCATION", "ASSIGNED_TO_LOCATION", "OLD_RESPONSIBILITY_DEPT", 
								"RESPONSIBILITY_DEPT", "OLD_RESPONSIBILITY_USER", "RESPONSIBILITY_USER", 
								"TRANS_TYPE_DEFINE", "TRANS_ID", "TRANS_NO", 
								"BARCODE", "ASSET_ID", "ASSET_NUMBER", "ASSETS_DESCRIPTION", "MODEL_NUMBER", "CURRENT_UNITS", "UNIT_OF_MEASURE", 
								"FROM_OBJECT_NO", "TO_OBJECT_NO", "FROM_OBJECT_NAME", "TO_OBJECT_NAME"};
					
					qryNames = new String[] {"TRANS_NO", "ASSETS_DESCRIPTION", "BARCODE", "FROM_OBJECT_NAME"};
					qryLabels = new String[] {"��������", "�ʲ�����", "��ǩ��", "�ص�"};
					primaryKeys = new String[] {"TRANS_NO", "BARCODE"};
					lookProp.setTotalWidth(800);
					lookProp.setDtoClass(AmsAssetsTransLineDTO.class);

				}else if (lookUpName.equals(AssetsLookUpConstant.
											 LOOK_TRANSFER_ITEM)) { //�ʲ��������̲����ʲ�
					dispNames = new String[] {"BARCODE", "ASSET_NUMBER","ITEM_NAME","ITEM_SPEC",
								"ASSETS_DESCRIPTION", "MODEL_NUMBER", "COST",
								"DATE_PLACED_IN_SERVICE", "DEPRN_COST",
								"OLD_RESPONSIBILITY_USER_NAME",
								"OLD_RESPONSIBILITY_DEPT_NAME","OLD_LOCATION_CODE",
								"OLD_LOCATION_NAME", "MANUFACTURER_NAME"};
					dispLabels = new String[] {"�ʲ���ǩ", "�ʲ����", "�豸����", "�豸�ͺ�", "�ʲ�����", "�ʲ��ͺ�",
								 "ԭֵ", "��������", "��ֵ", "������", "���β���", "�ص���", "�ص���", "����"};
					viewPercent = new String[] {"8%", "4%","6%","6%", "6%", "6%", "4%", "4%", "4%", "3%", "10%", "8%", "15%","9%"};
                    qryNames = new String[] {"ASSETS_DESCRIPTION", "BARCODE", "OLD_LOCATION_CODE","OLD_RESPONSIBILITY_USER_NAME"};
					qryLabels = new String[] {"�ʲ�����", "��ǩ��", "�ص�","������"};
					primaryKeys = new String[] {"BARCODE"};
					lookProp.setTotalWidth(850);
					lookProp.setDtoClass(AmsAssetsTransLineDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.
											 LOOK_TRANSFER_ASSETS_TD)) { //TD�ʲ��������̲����ʲ�
					dispNames = new String[] {"BARCODE", "ASSET_NUMBER","ITEM_NAME","ITEM_SPEC",
								"ASSETS_DESCRIPTION", "MODEL_NUMBER", "COST",
								"DATE_PLACED_IN_SERVICE", "DEPRN_COST",
								"OLD_RESPONSIBILITY_USER_NAME",
								"OLD_RESPONSIBILITY_DEPT_NAME", "SPECIALITY_DEPT_NAME", "OLD_LOCATION_CODE",
								"OLD_LOCATION_NAME", "MANUFACTURER_NAME"};
					dispLabels = new String[] {"�ʲ���ǩ", "�ʲ����", "�豸����", "�豸�ͺ�", "�ʲ�����", "�ʲ��ͺ�",
								 "ԭֵ", "��������", "��ֵ", "������", "���β���", "ʵ�������", "�ص���", "�ص���", "����"};
					viewPercent = new String[] {"8%", "4%","6%","6%", "6%", "6%", "4%", "4%", "4%", "3%", "10%", "8%", "8%", "10%","9%"};
                    retFields = new String[]  {"BARCODE", "ASSET_NUMBER","ITEM_NAME","ITEM_SPEC",
								"ASSETS_DESCRIPTION", "MODEL_NUMBER", "COST",
								"DATE_PLACED_IN_SERVICE", "DEPRN_COST",
								"OLD_RESPONSIBILITY_USER_NAME",
								"OLD_RESPONSIBILITY_DEPT_NAME","OLD_LOCATION_CODE",
								"OLD_LOCATION_NAME", "MANUFACTURER_NAME","DEPRN_LEFT_MONTH","SUM_DEPRECIATION","IMPAIR_RESERVE","RETIREMENT_COST","CURRENT_UNITS","OLD_FA_CATEGORY_CODE","OLD_RESPONSIBILITY_USER_NAME","OLD_DEPRECIATION_ACCOUNT","OLD_LOCATION","OLD_RESPONSIBILITY_USER","OLD_RESPONSIBILITY_DEPT"};
                    qryNames = new String[] {"ASSETS_DESCRIPTION", "BARCODE",
							   "OLD_RESPONSIBILITY_USER_NAME", "OLD_LOCATION_CODE"};
					qryLabels = new String[] {"�ʲ�����", "��ǩ��", "������", "�ص�"};
					primaryKeys = new String[] {"BARCODE"};
					lookProp.setTotalWidth(850);
					lookProp.setDtoClass(AmsAssetsTransLineDTO.class);
				}else if(lookUpName.equals(AssetsLookUpConstant.LOOK_SPECIAL_ASSETS)){//�����ʲ�
					dispNames = new String[]{"BARCODE","ASSETS_TYPE_NAME","ASSETS_DESCRIPTION","MODEL_NUMBER","ITEM_QTY","ITEM_STATUS_NAME",
							"ITEM_CATEGORY_NAME","OLD_RESPONSIBILITY_DEPT_NAME","OLD_RESPONSIBILITY_USER_NAME","OLD_LOCATION_CODE","OLD_LOCATION_NAME","MANUFACTURER_NAME"};
					dispLabels = new String[]{"�ʲ���ǩ","�ʲ�����","�ʲ�����","�ʲ��ͺ�","����","�ʲ�״̬",
							"�ʲ�רҵ","���β���","������","�ص����","�ص�����","����"};
					viewPercent = new String[]{"5%", "4%","6%","6%", "6%", "6%", "4%", "4%", "10%", "8%", "10%","15%"};
					qryNames = new String[] {"ITEM_NAME", "BARCODE",
							   "RESPONSIBILITY_USER_NAME", "WORKORDER_OBJECT_CODE"};
					qryLabels = new String[] {"�ʲ�����", "��ǩ��", "������", "�ص����"};
					primaryKeys = new String[] {"BARCODE"};
					lookProp.setTotalWidth(820);
					lookProp.setDtoClass(AmsAssetsTransLineDTO.class);
				}else if (lookUpName.equals(AssetsLookUpConstant.
											 LOOK_UP_COMPANY)) { //�ʲ����̲��ҹ�˾
					
					String isMult = req.getParameter("multipleChose");
					boolean multipleChose = false;
					if( !StrUtil.isEmpty( isMult ) ){
						 multipleChose = Boolean.parseBoolean( isMult );
					}
					
					dispNames = new String[] {"COMPANY_CODE", "COMPANY_NAME"};
					dispLabels = new String[] {"��˾����", "��˾����"};
					viewPercent = new String[] {"25%", "50%"};
					retFields = new String[] {"ORGANIZATION_ID", "COMPANY_CODE",
								"COMPANY_NAME"};
					qryNames = new String[] {"COMPANY_NAME"};
					qryLabels = new String[] {"��˾����"};
					primaryKeys = new String[] {"ORGANIZATION_ID"};
					lookProp.setTotalWidth(440);
					lookProp.setMultipleChose( multipleChose );
					lookProp.setDtoClass(EtsOuCityMapDTO.class);
				}else if (lookUpName.equals(AssetsLookUpConstant.
											 LOOK_UP_MIS_COMPANY)) { //�ʲ����̲��ҹ�˾
					
					String isMult = req.getParameter("multipleChose");
					boolean multipleChose = false;
					if( !StrUtil.isEmpty( isMult ) ){
						 multipleChose = Boolean.parseBoolean( isMult );
					}
					
					dispNames = new String[] {"COMPANY_CODE", "COMPANY_NAME"};
					dispLabels = new String[] {"��˾����", "��˾����"};
					viewPercent = new String[] {"25%", "50%"};
					retFields = new String[] {"ORGANIZATION_ID", "COMPANY_CODE",
								"COMPANY_NAME"};
					qryNames = new String[] {"COMPANY_NAME"};
					qryLabels = new String[] {"��˾����"};
					primaryKeys = new String[] {"ORGANIZATION_ID"};
					lookProp.setTotalWidth(440);
					lookProp.setMultipleChose( multipleChose );
					lookProp.setDtoClass(EtsOuCityMapDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.
											 LOOK_UP_PRI_DEPT)) { //�ʲ����̲��Ҳ���
					dispNames = new String[] {"COMPANY_CODE", "COMPANY_NAME",
								"DEPT_CODE", "DEPT_NAME"};
					dispLabels = new String[] {"��˾����", "��˾����", "���Ŵ���", "��������"};
					viewPercent = new String[] {"15%", "30%", "15%", "35%"};
					retFields = dispNames;
					qryNames = new String[] {"COMPANY_NAME", "DEPT_NAME"};
					qryLabels = new String[] {"��˾����", "��������"};
					primaryKeys = new String[] {"DEPT_CODE"};
					lookProp.setTotalWidth(700);
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsAssetsPriviDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.
											 LOOK_UP_PRI_ROLE)) { //�ʲ����̲��ҽ�ɫ
					dispNames = new String[] {"ROLE_NAME", "ROLE_DESC"};
					dispLabels = new String[] {"��ɫ����", "��ɫ����"};
					viewPercent = new String[] {"48%", "48%"};
					retFields = dispNames;
					qryNames = new String[] {"ROLE_NAME"};
					qryLabels = new String[] {"��ɫ����"};
					primaryKeys = new String[] {"ROLE_ID"};

					lookProp.setTotalWidth(700);
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsAssetsPriviDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_USER)) { //�ʲ����̲����û�
					dispNames = new String[] {"USER_NAME", "EMPLOYEE_NUMBER",
								"LOGIN_NAME", "COMPANY_CODE", "COMPANY_NAME"};
					dispLabels = new String[] {"�û�����", "�û�Ա����", "�û���¼��", "��˾����",
								 "��˾����"};
					viewPercent = new String[] {"15%", "30%", "15%", "15%",
								  "20%"};
					retFields = new String[] {"USER_ID", "COMPANY_CODE",
								"COMPANY_NAME", "USER_NAME", "EMPLOYEE_NUMBER",
								"LOGIN_NAME"};
					qryNames = new String[] {"USER_NAME", "EMPLOYEE_NUMBER",
							   "LOGIN_NAME"};
					qryLabels = new String[] {"����", "Ա����", "��¼��"};
					primaryKeys = new String[] {"USER_ID"};

					lookProp.setTotalWidth(700);
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsAssetsPriviDTO.class);					
				}
				else if(lookUpName.equals(LookUpConstant.LOOK_UP_USER_WITH_DEPT))
				{
					dispNames = new String[]{"USER_NAME","EMPLOYEE_NUMBER","LOGIN_NAME","DEPT_NAME","GROUP_NAME"};
					dispLabels = new String[]{"�û�����","�û�Ա����","��½��","����","����"};
					retFields = new String[]{"USER_ID", "USER_NAME","EMPLOYEE_NUMBER","LOGIN_NAME", "DEPT_NAME","DEPT_CODE","GROUP_NAME","GROUP_ID"};
					viewPercent = new String[]{"15%", "15%","15%","27%","28%"};
					qryNames = new String[]{"USER_NAME","EMPLOYEE_NUMBER","LOGIN_NAME"};
					qryLabels = new String[]{"����","Ա����","��½��" };
					primaryKeys = new String[]{"USER_ID"};

					lookProp.setMultipleChose(false);
					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(AmsAssetsPriviDTO.class);								
				}
				else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_USER_CHECK_BATCH)) { //�ʲ��̵㵥��������ѡ��鵵��
					dispNames = new String[] {"USER_NAME", "EMPLOYEE_NUMBER",
								"LOGIN_NAME", "COMPANY_CODE", "COMPANY_NAME"};
					dispLabels = new String[] {"�û�����", "�û�Ա����", "�û���¼��", "��˾����",
								 "��˾����"};
					viewPercent = new String[] {"15%", "30%", "15%", "15%",
								  "20%"};
					retFields = new String[] {"USER_ID", "COMPANY_CODE",
								"COMPANY_NAME", "USER_NAME", "EMPLOYEE_NUMBER",
								"LOGIN_NAME"};
					qryNames = new String[] {"USER_NAME", "EMPLOYEE_NUMBER",
							   "LOGIN_NAME"};
					qryLabels = new String[] {"����", "Ա����", "��¼��"};
					primaryKeys = new String[] {"USER_ID"};
	
					lookProp.setTotalWidth(700);
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsAssetsPriviDTO.class);
				
			} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_ZERO_USER)) { //�ʲ����̲����û�
					dispNames = new String[] {"USER_NAME", "EMPLOYEE_NUMBER",
							"LOGIN_NAME", "COMPANY_CODE", "COMPANY_NAME"};
				dispLabels = new String[] {"�û�����", "�û�Ա����", "�û���¼��", "��˾����",
							 "��˾����"};
				viewPercent = new String[] {"15%", "30%", "15%", "15%",
							  "20%"};
				retFields = new String[] {"USER_ID", "COMPANY_CODE",
							"COMPANY_NAME", "USER_NAME", "EMPLOYEE_NUMBER",
							"LOGIN_NAME"};
				qryNames = new String[] {"USER_NAME", "EMPLOYEE_NUMBER",
						   "LOGIN_NAME"};
				qryLabels = new String[] {"����", "Ա����", "��¼��"};
				primaryKeys = new String[] {"USER_ID"};

				lookProp.setTotalWidth(700);
				lookProp.setMultipleChose(false);
				lookProp.setDtoClass(AmsAssetsPriviDTO.class);
				
			}else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_USER_ZERO_ACHIEVE)) { //�鵵��
				dispNames = new String[] {"USER_NAME", "EMPLOYEE_NUMBER",
						"LOGIN_NAME", "COMPANY_CODE", "COMPANY_NAME"};
				dispLabels = new String[] {"�û�����", "�û�Ա����", "�û���¼��", "��˾����",
							 "��˾����"};
				viewPercent = new String[] {"15%", "30%", "15%", "15%",
							  "20%"};
				retFields = new String[] {"USER_ID", "COMPANY_CODE",
							"COMPANY_NAME", "USER_NAME", "EMPLOYEE_NUMBER",
							"LOGIN_NAME"};
				qryNames = new String[] {"USER_NAME", "EMPLOYEE_NUMBER",
						   "LOGIN_NAME"};
				qryLabels = new String[] {"����", "Ա����", "��¼��"};
				primaryKeys = new String[] {"USER_ID"};
	
				lookProp.setTotalWidth(700);
				lookProp.setMultipleChose(false);
				lookProp.setDtoClass(AmsAssetsPriviDTO.class);
			
			}else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_USER_ACHIEVE)) { //�鵵��
					dispNames = new String[] {"USER_NAME", "EMPLOYEE_NUMBER",
								"LOGIN_NAME", "COMPANY_CODE", "COMPANY_NAME"};
					dispLabels = new String[] {"�û�����", "�û�Ա����", "�û���¼��", "��˾����",
								 "��˾����"};
					viewPercent = new String[] {"15%", "30%", "15%", "15%",
								  "20%"};
					retFields = new String[] {"USER_ID", "COMPANY_CODE",
								"COMPANY_NAME", "USER_NAME", "EMPLOYEE_NUMBER",
								"LOGIN_NAME"};
					qryNames = new String[] {"USER_NAME", "EMPLOYEE_NUMBER",
							   "LOGIN_NAME"};
					qryLabels = new String[] {"����", "Ա����", "��¼��"};
					primaryKeys = new String[] {"USER_ID"};

					lookProp.setTotalWidth(700);
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsAssetsPriviDTO.class);
					
				} else if (lookUpName.equals("LOOK_UP_SAMPLING_USER")) { //��鹤����ѡ��ִ����
					dispNames = new String[] {"USER_NAME", "EMPLOYEE_NUMBER",
								"LOGIN_NAME", "COMPANY_CODE", "COMPANY_NAME"};
					dispLabels = new String[] {"�û�����", "�û�Ա����", "�û���¼��", "��˾����",
								 "��˾����"};
					viewPercent = new String[] {"15%", "30%", "15%", "15%",
								  "20%"};
					retFields = new String[] {"USER_ID", "COMPANY_CODE",
								"COMPANY_NAME", "USER_NAME", "EMPLOYEE_NUMBER",
								"LOGIN_NAME"};
					qryNames = new String[] {"USER_NAME", "EMPLOYEE_NUMBER",
							   "LOGIN_NAME"};
					qryLabels = new String[] {"����", "Ա����", "��¼��"};
					primaryKeys = new String[] {"USER_ID"};

					lookProp.setTotalWidth(700);
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsAssetsPriviDTO.class);
				} else if(lookUpName.equals(AssetsLookUpConstant.LOOK_UP_USER_BY_ORGANAZATION)){ //�õ�ָ��OU�µ��û�
					dispNames = new String[] {"USER_NAME", "EMPLOYEE_NUMBER",
							"LOGIN_NAME", "COMPANY_CODE", "COMPANY_NAME"};
					dispLabels = new String[] {"�û�����", "�û�Ա����", "�û���¼��", "��˾����",
								 "��˾����"};
					viewPercent = new String[] {"15%", "30%", "15%", "15%",
								  "20%"};
					retFields = new String[] {"USER_ID", "COMPANY_CODE",
								"COMPANY_NAME", "USER_NAME", "EMPLOYEE_NUMBER",
								"LOGIN_NAME"};
					qryNames = new String[] {"USER_NAME", "EMPLOYEE_NUMBER",
							   "LOGIN_NAME"};
					qryLabels = new String[] {"����", "Ա����", "��¼��"};
					primaryKeys = new String[] {"USER_ID"};
	
					lookProp.setTotalWidth(700);
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsAssetsPriviDTO.class);					
				} else if (lookUpName.equals(AssetsLookUpConstant.
											 LOOK_UP_PERSON)) { //�ʲ�ҵ�������Ա��������
					dispNames = new String[] {"USER_NAME", "EMPLOYEE_NUMBER",
								"DEPT_NAME", "COMPANY"};
					dispLabels = new String[] {"Ա������", "Ա�����", "��������", "��˾����"};
					viewPercent = new String[] {"14%", "13%", "45%", "25%"};
					qryNames = new String[] {"USERNAME", "EMPLOYEE_NUMBER"};
					qryLabels = new String[] {"Ա������", "Ա�����"};
					
					retFields = new String[] { "USER_NAME", "DEPT_NAME" , "DEPT_CODE", "EMPLOYEE_ID" , "EMPLOYEE_NUMBER" , "COMPANY" , "ORGANIZATION_ID" };
					
					primaryKeys = new String[] {"EMPLOYEE_ID"};

					lookProp.setTotalWidth(746);
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(SfUserDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.
											 LOOK_UP_LOCATION)) { //���ҵص�
					dispNames = new String[] {"COMPANY_NAME", "COUNTY_NAME",
								"OBJECT_CATEGORY", "OBJECT_CODE", "OBJECT_NAME"};
					dispLabels = new String[] {"��˾����", "�ɱ�����", "�ص�רҵ", "�ص����","�ص�����"};
					viewPercent = new String[] {"10%", "19%", "10%", "18%", "40%"};
					retFields = new String[] {"CHECK_LOCATION", "OBJECT_CODE",	"OBJECT_NAME"};
					qryNames = new String[] {"WORKORDER_OBJECT_CODE",
							   "WORKORDER_OBJECT_NAME", "COUNTY_NAME"};
					qryLabels = new String[] {"�ص����", "�ص�����", "�ɱ�����"};
					primaryKeys = new String[] {"CHECK_LOCATION"};

//                    contentReadio = "Y";

                    lookProp.setTotalWidth(950);
					lookProp.setMultipleChose(true);
					lookProp.setDtoClass(EtsObjectDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.
											 LOOK_UP_FACAT_CODE)) {
					dispNames = new String[] {"FA_CAT_CODE_1", "FA_CAT_NAME_1",
								"FA_CAT_CODE_2", "FA_CAT_NAME_2",
								"FA_CAT_CODE_3", "FA_CAT_NAME_3"};
					dispLabels = new String[] {"���1����", "���1����", "���2����",
								 "���2����", "���3����", "���3����"};
					viewPercent = new String[] {"10%", "15%", "10%", "30%",
								  "10%", "20%"};
					qryNames = new String[] {"FA_CAT_NAME_1", "FA_CAT_NAME_2",
							   "FA_CAT_NAME_3"};
					qryLabels = new String[] {"���1����", "���2����", "���3����"};
					primaryKeys = new String[] {"CONTENT_CODE"};

					lookProp.setTotalWidth(850);
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsFaCategoryVDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.
											 LOOK_UP_ACCOUNT)) { //����ĳ�����µĵص������̵㹤��
					dispNames = new String[] {"ACCOUNT_NAME_1","ACCOUNT_NAME_2","ACCOUNT_NAME_3",
								"ACCOUNT_NAME_4", "ACCOUNT_NAME_5",
								"ACCOUNT_NAME_6", "ACCOUNT_NAME_7"};
					dispLabels = new String[] {"��˾��","�ɱ����Ķ�","��ƿ�Ŀ��", "Ʒ�ƶ�", "��Ŀ��", "�ڲ�������",
								 "���ö�"};
					viewPercent = new String[] {"15%", "15%", "33%", "8%", "8%", "8%", "8%"};
					qryNames = new String[] {"ACCOUNT_NAME_2", "ACCOUNT_NAME_3"};
					qryLabels = new String[] {"�ɱ����Ķ�", "��ƿ�Ŀ��"};
					primaryKeys = new String[] {"ACCOUNT_CODE"};
					retFields = new String[] {"ACCOUNT_CODE", "ACCOUNT_NAME"};
					lookProp.setTotalWidth(800);
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsAccountVDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.
											 LOOK_UP_VENDOR)) { //����ĳ�����µĵص������̵㹤��
					dispNames = new String[] {"VENDOR_NAME", "VENDOR_NUMBER",
								"VENDOR_NAME_ALT"};
					dispLabels = new String[] {"��������", "���̱��", "���̱���"};
					qryNames = dispNames;
					qryLabels = dispLabels;
					viewPercent = new String[] {"30%", "30%", "30%"};
					primaryKeys = new String[] {"VENDOR_ID"};
					retFields = dispNames;
					lookProp.setTotalWidth(600);
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsAssetsAddressVDTO.class);
				}else if (lookUpName.equals("PROJECT_NO")) {           //��Ŀ���
					dispNames = new String[] {"PROJECT_NUMBER","MIS_PROJECT_ID", "PROJECT_NAME","PROJECT_TYPE"};
					dispLabels = new String[] {"��Ŀ���","MIS��ĿId", "��Ŀ����", "��Ŀ����"};
					retFields = new String[] {"PROJECT_NUMBER", "MIS_PROJECT_ID", "PROJECT_NAME"};
					viewPercent = new String[] {"10%", "10%", "50%", "30%"};
					qryNames = new String[] {"PROJECT_NUMBER","MIS_PROJECT_ID", "NAME","PROJECT_TYPE"};
					qryLabels = new String[] {"��Ŀ���","MIS��ĿId", "��Ŀ����", "��Ŀ����"};
					primaryKeys = new String[] {"PROJECT_ID"};
			
					lookProp.setTotalWidth(700);
			        String multipleChose = req.getParameter("multipleChose");
			        if(!StrUtil.isEmpty(multipleChose) && multipleChose.equalsIgnoreCase("true")){
			            lookProp.setMultipleChose(true);
			        } else {
			            lookProp.setMultipleChose(false);
			        }
					lookProp.setDtoClass(EtsPaProjectsAllDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_PROJECT)) {
					 
//					this.setOptions(req, res, LookUpConstant.LOOK_UP_PROJECT );
					
					dispNames = new String[] {"PROJECT_NUMBER", "PROJECT_NAME",
								"PROJECT_TYPE"};
					dispLabels = new String[] {"��Ŀ���", "��Ŀ����", "��Ŀ����"};
					retFields = new String[] {"PROJECT_NUMBER", "PROJECT_NAME"};
					viewPercent = new String[] {"10%", "60%", "30%"};
					qryNames = new String[] {"PROJECT_NUMBER", "NAME","PROJECT_TYPE"};
					qryLabels = new String[] {"��Ŀ���", "��Ŀ����", "��Ŀ����"};
					primaryKeys = new String[] {"PROJECT_ID"};

					lookProp.setTotalWidth(700);
                    String multipleChose = req.getParameter("multipleChose");
                    if(!StrUtil.isEmpty(multipleChose) && multipleChose.equalsIgnoreCase("true")){
                        lookProp.setMultipleChose(true);
                    } else {
                        lookProp.setMultipleChose(false);
                    }
					lookProp.setDtoClass(EtsPaProjectsAllDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_COST)) {
					
					String isMult = req.getParameter("multipleChose");
					boolean multipleChose = false;
					if( !StrUtil.isEmpty( isMult ) ){
						 multipleChose = Boolean.parseBoolean( isMult );
					}
					
					dispNames = new String[] {"COST_CODE", "COST_NAME"};
					dispLabels = new String[] {"�ɱ����Ĵ���", "�ɱ���������"};
					viewPercent = new String[] {"30%", "65%"};
					qryNames = dispNames;
					qryLabels = dispLabels;
					primaryKeys = new String[] {"COST_CODE"};

					lookProp.setTotalWidth(700);
					lookProp.setMultipleChose( multipleChose );
					lookProp.setDtoClass(AmsMisCostDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_ITEMNAME)) {
					dispNames = new String[] {"ITEM_NAME","ITEM_SPEC","VALUE"};
					dispLabels = new String[] {"�豸����","����ͺ�","�豸רҵ"};
					viewPercent = new String[] {"40%","40%","20%"};
					qryNames = new String[] {"ITEM_NAME","ITEM_SPEC"};
					qryLabels = new String[] {"�豸����","����ͺ�"};
					primaryKeys = new String[] {"ITEM_NAME"};
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(EtsSystemItemDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_MISLOC)) {
					String isMult = req.getParameter("multipleChose");
					boolean multipleChose = false;
					if( !StrUtil.isEmpty( isMult ) ){
						 multipleChose = Boolean.parseBoolean( isMult );
					}
					
					dispNames = new String[] {"ASSETS_LOCATION_CODE", "ASSETS_LOCATION"};
					dispLabels = new String[] {"�ص����", "�ص�����"};
					viewPercent = new String[] {"50%", "40%"};
					qryNames = dispNames;
					qryLabels = dispLabels;
					primaryKeys = new String[] {"ASSETS_LOCATION_CODE"};
					lookProp.setMultipleChose( multipleChose );
					lookProp.setDtoClass(AmsAssetsAddressVDTO.class);
				}  else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_TDLOC)) {
					dispNames = new String[] {"ASSETS_LOCATION_CODE", "ASSETS_LOCATION"};
					dispLabels = new String[] {"�ص����", "�ص�����"};
					viewPercent = new String[] {"50%", "40%"};
					qryNames = dispNames;
					qryLabels = dispLabels;
					primaryKeys = new String[] {"ASSETS_LOCATION_CODE"};
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsAssetsAddressVDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_LNE)) {
					dispNames = new String[] {"NET_CATEGORY1", "NET_CATEGORY2", "NET_UNIT_CODE", "LOG_NET_ELE", "ENG_AB"};
					dispLabels = new String[] {"����רҵ1", "����רҵ2", "��Ԫ����", "�߼�����Ԫ��", "Ӣ����д"};
					viewPercent = new String[] {"15%", "15%", "15%", "30%", "15%"};
					qryNames = new String[] {"NET_CATEGORY1", "NET_CATEGORY2", "NET_UNIT_CODE", "LOG_NET_ELE"};
					qryLabels = new String[] {"����רҵ1", "����רҵ2", "��Ԫ����", "�߼�����Ԫ��"};
					primaryKeys = new String[] {"AMS_LNE_ID"};
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsLneDTO.class);
				}else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_LNE_NOMATCH_AMS)) {
					dispNames = new String[] {"NET_CATEGORY1", "NET_CATEGORY2", "NET_UNIT_CODE", "LOG_NET_ELE", "ENG_AB"};
					dispLabels = new String[] {"����רҵ1", "����רҵ2", "��Ԫ����", "�߼�����Ԫ��", "Ӣ����д"};
					viewPercent = new String[] {"15%", "15%", "15%", "30%", "15%"};
					qryNames = new String[] {"NET_CATEGORY1", "NET_CATEGORY2", "NET_UNIT_CODE", "LOG_NET_ELE"};
					qryLabels = new String[] {"����רҵ1", "����רҵ2", "��Ԫ����", "�߼�����Ԫ��"};
					primaryKeys = new String[] {"AMS_LNE_ID"};
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsLneDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_CEX)) {
					dispNames = new String[] {"INVEST_CATEGORY1", "INVEST_CATEGORY2", "INVEST_CAT_CODE", "INVEST_CAT_NAME"};
					dispLabels = new String[] {"Ͷ�ʴ���", "Ͷ������", "Ͷ�ʷ������", "Ͷ�ʷ�������"};
					viewPercent = new String[] {"25%", "25%", "25%", "20%"};
                    qryNames = new String[] {"INVEST_CATEGORY1", "INVEST_CATEGORY2", "INVEST_CAT_CODE", "INVEST_CAT_NAME"};
					qryLabels = new String[] {"Ͷ�ʴ���", "Ͷ������", "Ͷ�ʷ������", "Ͷ�ʷ�������"};
					primaryKeys = new String[] {"AMS_CEX_ID"};
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsCexDTO.class);

				} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_CEX_NOMATCH_AMS)) {
					dispNames = new String[] {"INVEST_CATEGORY1", "INVEST_CATEGORY2", "INVEST_CAT_CODE", "INVEST_CAT_NAME"};
					dispLabels = new String[] {"Ͷ�ʴ���", "Ͷ������", "Ͷ�ʷ������", "Ͷ�ʷ�������"};
					viewPercent = new String[] {"25%", "25%", "25%", "20%"};

					qryNames = dispNames;
					qryLabels = dispLabels;
					primaryKeys = new String[] {"AMS_CEX_ID"};
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsCexDTO.class);

				} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_OPE)) {
					dispNames = new String[] {"OPE_CODE", "OPE_NAME"};
					dispLabels = new String[] {"ҵ��ƽ̨����", "ҵ��ƽ̨����"};
					viewPercent = new String[] {"40%", "50%"};
					qryNames = dispNames;
					qryLabels = dispLabels;
					primaryKeys = new String[] {"AMS_OPE_ID"};
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsOpeDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_OPE_NOMATCH_AMS)) {
					dispNames = new String[] {"OPE_CODE", "OPE_NAME"};
					dispLabels = new String[] {"ҵ��ƽ̨����", "ҵ��ƽ̨����"};
					viewPercent = new String[] {"40%", "50%"};
					qryNames = dispNames;
					qryLabels = dispLabels;
					primaryKeys = new String[] {"AMS_OPE_ID"};
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsOpeDTO.class);
				}else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_NLE)) {
					dispNames = new String[] {"LNE_CODE", "LNE_NAME"};
					dispLabels = new String[] {"�����α���", "����������"};
					viewPercent = new String[] {"40%", "50%"};
					qryNames = dispNames;
					qryLabels = dispLabels;
					primaryKeys = new String[] {"AMS_LNE_ID"};
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsNleDTO.class);
				}else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_SN)) {
					dispNames = new String[] {"SN_CODE", "SN_NAME"};
					dispLabels = new String[] {"֧�����豸���ͱ���", "֧�����豸��������"};
					viewPercent = new String[] {"40%", "50%"};
					qryNames = dispNames;
					qryLabels = dispLabels;
					primaryKeys = new String[] {"SN_ID"};
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsSnDTO.class);
				}else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_NLE_NOMATCH_AMS)) {
					dispNames = new String[] {"LNE_CODE", "LNE_NAME"};
					dispLabels = new String[] {"�����α���", "����������"};
					viewPercent = new String[] {"40%", "50%"};
					qryNames = dispNames;
					qryLabels = dispLabels;
					primaryKeys = new String[] {"AMS_LNE_ID"};
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsNleDTO.class);
				}
                
				else if (lookUpName.equals(AssetsLookUpConstant.
						LOOK_TRANSFER_ASSETS_OTHER )) { //�ʲ��������̲����ʲ�
					//�ʲ���ǩ�š��ʲ����ơ�����ͺš��ʲ�������������λ���������ڡ��ص��������������̡������ˡ����β���
					dispNames = new String[] {"BARCODE", "ASSETS_DESCRIPTION","MODEL_NUMBER", 
								"ITEM_QTY", "UNIT_OF_MEASURE",  "DATE_PLACED_IN_SERVICE",
								"OLD_RESPONSIBILITY_USER_NAME",
								"OLD_RESPONSIBILITY_DEPT_NAME","OLD_LOCATION_CODE",
								"OLD_LOCATION_NAME", "MANUFACTURER_NAME"};
					dispLabels = new String[] {"�ʲ���ǩ",  "�豸����", "�豸�ͺ�", "�ʲ�����","������λ",
								  "��������",   "������", "���β���", "�ص���", "�ص���", "����"};
					viewPercent = new String[] {"8%", "6%","6%", "6%", "6%",  "4%",  "4%", "10%", "8%", "15%","9%"};
					qryNames = new String[] {"ASSETS_DESCRIPTION", "BARCODE", "OLD_LOCATION_CODE"};
					qryLabels = new String[] {"�ʲ�����", "��ǩ��", "�ص�"};
					primaryKeys = new String[] {"BARCODE"};
					lookProp.setTotalWidth(850);
					lookProp.setDtoClass(AmsAssetsTransLineDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_ASSETS_LEASE  )) { //�ʲ���������ʲ�
					dispNames = new String[] {"BARCODE", 
								"ITEM_NAME",
								"ITEM_SPEC",  //"ASSET_NUMBER","�ʲ����","ASSETS_DESCRIPTION", "�ʲ�����",  "MODEL_NUMBER", "�ʲ��ͺ�",
								"WORKORDER_OBJECT_LOCATION", 
								"RESPONSIBILITY_USER_NAME", 
								"RENT_DATE",
								"END_DATE",
								"RENT_PERSON",
								"CONTRACT_NUMBER", 
								"CONTRACT_NAME",
								"TENANCY",
								"YEAR_RENTAL",
								"MONTH_RENTAL",
								"REMARK" 
								};
					dispLabels = new String[] {"�ʲ���ǩ",  "�豸����", "����ͺ�", 
								 "�ʲ��ص�����", "������",    "��������", "ֹ������", "ǩԼ��λ", "��ͬ���", "��ͬ����",   "����", "�����", "�����", "��ע"};
					viewPercent = new String[] {"8%", "4%","6%","6%", "6%",    "6%", "4%", "4%", "4%", "3%",   "10%", "8%", "15%","9%"};
					qryNames = new String[] {"RENT_PERSON", "WORKORDER_OBJECT_LOCATION",
							   "RESPONSIBILITY_USER_NAME" }; //, "END_DATE" , "END_DATE"
					qryLabels = new String[] {"ǩԼ��λ", "�ص�", "������"}; //, "ֹ��������", "ֹ������ֹ"
					primaryKeys = new String[] {"BARCODE"};
					lookProp.setTotalWidth(850);
					lookProp.setDtoClass(LeaseLineDTO.class);
				}else if(lookUpName.equals(AssetsLookUpConstant.LOOK_ASSETS_SHARE)) {// ѡ�����ʲ�
					
					dispNames = new String[] {"BARCODE", 
							"ITEM_NAME",
							"ITEM_SPEC", 
							"WORKORDER_OBJECT_LOCATION", 
							"SPECIALITY_DEPT_NAME",
							"EMPLOYEE_NUMBER",
							"USER_NAME",
							"START_DATE",
							"CONTENT_CODE",
							"CONTENT_NAME"
							};
				dispLabels = new String[] {"�ʲ���ǩ",  "�ʲ�����", "����ͺ�", 
							 "�ʲ��ص�����", "ʵ�������", "�����˱��", "������", "��������",  "�ʲ�Ŀ¼", "�ʲ�Ŀ¼����"};
				viewPercent = new String[] {"8%", "8%", "8%", "12%", "12%", "6%", "6%", "8%", "8%", "10%"};
				qryNames = new String[] {"BARCODE","CONTENT_NAME", "WORKORDER_OBJECT_LOCATION",
						   "USER_NAME" }; //, "END_DATE" , "END_DATE"
				qryLabels = new String[] {"��ǩ��","�ʲ�Ŀ¼", "�ʲ��ص�", "������"}; //, "ֹ��������", "ֹ������ֹ"
				primaryKeys = new String[] {"SYSTEMID"};
				lookProp.setTotalWidth(850);
				lookProp.setMultipleChose(true);
				lookProp.setDtoClass(AssetSharingLineDTO.class);

				}else if(lookUpName.equals(AssetsLookUpConstant.LOOK_ASSETS_DEVALUE)) {// ѡ��ɼ�ֵ�ʲ�

				dispNames = new String[] {"BARCODE", "ITEM_NAME", "ITEM_SPEC", 
							"WORKORDER_OBJECT_LOCATION", "SPECIALITY_DEPT_NAME",
							"EMPLOYEE_NUMBER", "USER_NAME", "START_DATE",
							"CONTENT_CODE", "CONTENT_NAME"
							};
				dispLabels = new String[] {"�ʲ���ǩ",  "�ʲ�����", "����ͺ�", 
							 "�ʲ��ص�����", "ʵ�������", "�����˱��", "������", "��������",  "�ʲ�Ŀ¼", "�ʲ�Ŀ¼����"};
				viewPercent = new String[] {"8%", "8%","8%","12%", "12%", "6%", "6%", "8%", "8%", "10%"};
				qryNames = new String[] {"BARCODE","CONTENT_NAME", "WORKORDER_OBJECT_LOCATION", "USER_NAME" }; //, "END_DATE" , "END_DATE"
				qryLabels = new String[] {"��ǩ��","�ʲ�Ŀ¼", "�ʲ��ص�", "������"}; //, "ֹ��������", "ֹ������ֹ"
				
                retFields = new String[]  {"BARCODE", "ASSET_NUMBER","ITEM_NAME","ITEM_SPEC",
						"ASSETS_DESCRIPTION", "MODEL_NUMBER", "COST",
						"DATE_PLACED_IN_SERVICE", "DEPRN_COST",
						"MANUFACTURER_NAME", "DEPRN_LEFT_MONTH", "DEPRECIATION",
						"IMPAIR_RESERVE", "RETIREMENT_COST", "CURRENT_UNITS",
						"UNIT_OF_MEASURE", "START_DATE"};
				
				primaryKeys = new String[] {"SYSTEMID"};
				lookProp.setTotalWidth(850);
				lookProp.setMultipleChose(true);
				lookProp.setDtoClass(AssetSharingLineDTO.class);
				
				} else if (lookUpName.equals("LOOK_ASSETS")) {//ѡ���ʲ�
					dispNames = new String[] {"BARCODE", 
							"ITEM_NAME",
							"ITEM_SPEC", 
							"WORKORDER_OBJECT_LOCATION", 
							"EMPLOYEE_NUMBER",
							"RESPONSIBILITY_USER_NAME",
							"SPECIALITY_DEPT_NAME",
							"START_DATE",
							"CONTENT_CODE",
							"CONTENT_NAME"
							};
				dispLabels = new String[] {"�ʲ���ǩ", "�ʲ�����", "����ͺ�", 
							 "�ʲ��ص�����", "�����˱��", "������", "ʵ�������", "��������",  "�ʲ�Ŀ¼", "�ʲ�Ŀ¼����"};
				viewPercent = new String[] {"8%", "8%","8%", "12%", "6%", "6%", "12%", "8%", "8%", "10%"};
				qryNames = new String[] {"BARCODE", "ITEM_NAME", "WORKORDER_OBJECT_LOCATION" }; 
				qryLabels = new String[] {"�ʲ���ǩ��", "�ʲ�����", "�ʲ��ص�"}; 
				primaryKeys = new String[] {"SYSTEMID"};
				lookProp.setTotalWidth(1024);
				lookProp.setMultipleChose(true);
				lookProp.setDtoClass(AmsAssetsTransLineDTO.class);
				}else if (lookUpName.equals("LOOK_ZEROTURN_ASSETS")) {//ѡ���㹺�ʲ�
						dispNames = new String[] {
							                   "BARCODE",     //"��ǩ��", 
							                   "PROCURE_CODE",//�ɹ�����
							                   "EXPECTED_DATE",//Ԥ�Ƶ�������
							                   "CONTENT_CODE",//"�ʲ�Ŀ¼", 
								               "CONTENT_NAME",//"�ʲ�Ŀ¼����", 
								               "ASSETS_DESCRIPTION",//"�ʲ�����", 
								               "ITEM_SPEC",//"����ͺ�", 
								               "ITEM_QTY",//"����",
								               "UNIT_OF_MEASURE",//"������λ",
								               "MANUFACTURER_NAME",//"����",
								               "OBJECT_NO",//"�ص���",
								               "WORKORDER_OBJECT_NAME",//"�ص�����",
								               "RESPONSIBILITY_DEPT",//"���β���",
								               "RESPONSIBILITY_USER",//"������",
//								               "SPECIALITY_DEPT",//"רҵ����",
//								               "PRICE",//"���",
								               "COST_CENTER_CODE"//,//"�ɱ�����",
//								               "CREATION_DATE", 
//								 			   "CREATED_BY",
//								 			   "RECORD",// "BC",//BARCODE
//								 			   "COMPANY_CODE",// "��˾����",
//								               "REMARK"//"��ע"
								};
					dispLabels = new String[] {//
							                   "��ǩ��", 
							                   "�ɹ�����",
							                   "Ԥ�Ƶ�������",
							                   "�ʲ�Ŀ¼", 
								               "�ʲ�Ŀ¼����", 
								               "�ʲ�����", 
								               "����ͺ�", 
								               "����",
								               "������λ",
								               "����",
								               "�ص���",
								               "�ص�����",
								               "���β���",
								               "������",
//								               "רҵ����",
//								               "���",
								               "�ɱ�����"//,
//								               "��������",
//											   "������",
//											   "��",
//											   "��˾����",
//								               "��ע"
								               };
					viewPercent = new String[] 
					                       {//
											"6%",//"��ǩ��", 
											"7%",//�ɹ�����
											"7%",//Ԥ�Ƶ�������
											"6%",//"�ʲ�Ŀ¼", 
											"7%",//"�ʲ�Ŀ¼����", 
											"7%",//"�ʲ�����", 30
											"7%",//"����ͺ�", 
											"5%",//"����",
											"7%",//"������λ",
											"6%",//"����",
											"7%",//"�ص���",
											"7%",//"�ص�����",
											"7%",//"���β���",
											"7%",//"������",
//											"4%",//"רҵ����",
//											"4%",//"���",
											"7%"/*,//"�ɱ�����",
											"4%",//"��������",
											"4%",//"������",
											"4%",//"������",
											"4%",//"��˾����",
							                "4%"*/};//"��ע" 
					retFields = new String[] {
							   "BARCODE",     //"��ǩ��", 
			                   "PROCURE_CODE",//�ɹ�����
			                   "EXPECTED_DATE",//Ԥ�Ƶ�������
			                   "CONTENT_CODE",//"�ʲ�Ŀ¼", 
				               "CONTENT_NAME",//"�ʲ�Ŀ¼����", 
				               "ASSETS_DESCRIPTION",//"�ʲ�����", 
				               "ITEM_SPEC",//"����ͺ�", 
				               "ITEM_QTY",//"����",
				               "UNIT_OF_MEASURE",//"������λ",
				               "MANUFACTURER_NAME",//"����",
				               "OBJECT_NO",//"�ص���",
				               "WORKORDER_OBJECT_NAME",//"�ص�����",
				               "RESPONSIBILITY_DEPT",//"���β���",
				               "RESPONSIBILITY_USER",//"������",
				               "SPECIALITY_DEPT",//"רҵ����",
				               "PRICE",//"���",
				               "COST_CENTER_CODE",//"�ɱ�����",
				               "CREATION_DATE", 
				 			   "CREATED_BY",
				 			   "RECORD",// "BC",//BARCODE
				 			   "COMPANY_CODE",// "��˾����",
				               "REMARK"//"��ע"
							};
					qryNames = new String[] {"PROCURE_CODE", "COST_CENTER_CODE" }; 
					qryLabels = new String[] {"�ɹ�����", "�ɱ�����"}; 
					primaryKeys = new String[] {"BARCODE"};
					lookProp.setTotalWidth(1024);
					lookProp.setMultipleChose(true);
					lookProp.setDtoClass(ZeroTurnLineDTO.class);
			     }else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_FA_CONTENT)) {
                    dispNames = new String[] {"CONTENT_CODE", "CONTENT_NAME"};
					dispLabels = new String[] {"�ʲ�������", "�ʲ��������"};
					viewPercent = new String[] {"40%", "60%"};
					qryNames = dispNames;
					qryLabels = dispLabels;
					primaryKeys = new String[] {"CONTENT_CODE"};
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(EtsItemInfoDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_FA_CONTENTS)) {
                    dispNames = new String[] {"CONTENT_CODE", "CONTENT_NAME"};
					dispLabels = new String[] {"�ʲ�Ŀ¼����", "�ʲ� Ŀ¼����"};
					viewPercent = new String[] {"40%", "60%"};
					qryNames = dispNames;
					qryLabels = dispLabels;
					primaryKeys = new String[] {"CONTENT_CODE"};
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(EtsItemInfoDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_FA_LNE)) {
                    dispNames = new String[] {"CONTENT_CODE", "CONTENT_NAME"};
					dispLabels = new String[] {"�ʲ�������", "�ʲ��������"};
					viewPercent = new String[] {"40%", "60%"};
					qryNames = dispNames;
					qryLabels = dispLabels;
					primaryKeys = new String[] {"CONTENT_CODE"};
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(EtsItemInfoDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_FA_CEX)) {
                    dispNames = new String[] {"CONTENT_CODE", "CONTENT_NAME"};
					dispLabels = new String[] {"�ʲ�������", "�ʲ��������"};
					viewPercent = new String[] {"40%", "60%"};
					qryNames = dispNames;
					qryLabels = dispLabels;
					primaryKeys = new String[] {"CONTENT_CODE"};
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(EtsItemInfoDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_FA_OPE)) {
                    dispNames = new String[] {"CONTENT_CODE", "CONTENT_NAME"};
					dispLabels = new String[] {"�ʲ�������", "�ʲ��������"};
					viewPercent = new String[] {"40%", "60%"};
					qryNames = dispNames;
					qryLabels = dispLabels;
					primaryKeys = new String[] {"CONTENT_CODE"};
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(EtsItemInfoDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_FA_NLE)) {
                    dispNames = new String[] {"CONTENT_CODE", "CONTENT_NAME"};
					dispLabels = new String[] {"�ʲ�������", "�ʲ��������"};
					viewPercent = new String[] {"40%", "60%"};
					qryNames = dispNames;
					qryLabels = dispLabels;
					primaryKeys = new String[] {"CONTENT_CODE"};
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(EtsItemInfoDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_FA_CONTENT_LINE)) {
                    dispNames = new String[] {"CONTENT_CODE", "CONTENT_NAME"};
					dispLabels = new String[] {"�ʲ�������", "�ʲ��������"};
					viewPercent = new String[] {"40%", "60%"};
					qryNames = dispNames;
					qryLabels = dispLabels;
					primaryKeys = new String[] {"CONTENT_CODE"};
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsAssetsAddressVDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_LNE_LINE)) {
                    dispNames = new String[] {"LNE_CODE", "LNE_NAME"};
					dispLabels = new String[] {"�߼�����Ԫ�ر���", "�߼�����Ԫ������"};
					viewPercent = new String[] {"40%", "60%"};
					qryNames = dispNames;
					qryLabels = dispLabels;
					primaryKeys = new String[] {"LNE_ID"};
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsAssetsAddressVDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_CEX_LINE)) {
                    dispNames = new String[] {"CEX_CODE", "CEX_NAME"};
					dispLabels = new String[] {"Ͷ�ʷ������", "Ͷ�ʷ�������"};
					viewPercent = new String[] {"40%", "60%"};
					qryNames = dispNames;
					qryLabels = dispLabels;
					primaryKeys = new String[] {"CEX_ID"};
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsAssetsAddressVDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_OPE_LINE)) {
                    dispNames = new String[] {"OPE_CODE", "OPE_NAME"};
					dispLabels = new String[] {"ҵ��ƽ̨����", "ҵ��ƽ̨����"};
					viewPercent = new String[] {"40%", "60%"};
					qryNames = dispNames;
					qryLabels = dispLabels;
					primaryKeys = new String[] {"OPE_ID"};
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsAssetsAddressVDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_NLE_LINE)) {
                    dispNames = new String[] {"NLE_CODE", "NLE_NAME"};
					dispLabels = new String[] {"�����α���", "����������"};
					viewPercent = new String[] {"40%", "60%"};
					qryNames = dispNames;
					qryLabels = dispLabels;
					primaryKeys = new String[] {"NLE_ID"};
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsAssetsAddressVDTO.class);
				}else if(lookUpName.equals("SF_RES_DEFINE")){
					dispNames = new String[] { "RES_NAME", "RES_URL"};
					dispLabels = new String[] {"��Ŀ����", "��ĿURL"};
					viewPercent = new String[] { "50%", "50%" };
					qryNames = new String[] { "RES_NAME","RES_URL" };
					qryLabels = new String[] { "��Ŀ����", "��ĿURL" };
//					primaryKeys = new String[] { "SYSTEM_ID" };
					// lookProp.setTotalWidth(746);
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(SfRoleQueryDTO.class);
				}else if(lookUpName.equals("OU_CITY_MAP")){
					dispNames = new String[] { "ORGANIZATION_ID", "COMPANY"};
					dispLabels = new String[] {"�ص�ID", "�ص�����"};
					viewPercent = new String[] { "50%", "50%" };
					qryNames = new String[] {"COMPANY" };
					qryLabels = new String[] {  "�ص�����" };
//					primaryKeys = new String[] { "SYSTEM_ID" };
					// lookProp.setTotalWidth(746);
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(SfRoleQueryDTO.class);
				} else if(lookUpName.equals("LOOK_UP_ALL_USER")){
					dispNames = new String[] { "USER_NAME", "LOGIN_NAME"};
					dispLabels = new String[] {"�û�����", "��¼��"};
					viewPercent = new String[] { "50%", "50%" };
					qryNames = new String[] {"USERNAME" };
					qryLabels = new String[] {  "�û�����" };
//					primaryKeys = new String[] { "SYSTEM_ID" };
					// lookProp.setTotalWidth(746);
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(SfUserDTO.class);
				}else if (lookUpName.equals("LOOK_UP_MANAGER")) { //�ʲ�����Ա
					dispNames = new String[] {"USER_NAME", "EMPLOYEE_NUMBER",
							"LOGIN_NAME", "COMPANY_CODE", "COMPANY_NAME"};
				    dispLabels = new String[] {"�û�����", "�û�Ա����", "�û���¼��", "��˾����",
							 "��˾����"};
				    viewPercent = new String[] {"15%", "30%", "15%", "15%",
							  "20%"};
				    retFields = new String[] {"USER_ID", "COMPANY_CODE",
							"COMPANY_NAME", "USER_NAME", "EMPLOYEE_NUMBER",
							"LOGIN_NAME"};
				    qryNames = new String[] {"USER_NAME", "EMPLOYEE_NUMBER",
						   "LOGIN_NAME"};
				    qryLabels = new String[] {"����", "Ա����", "��¼��"};
				    primaryKeys = new String[] {"USER_ID"};

					lookProp.setTotalWidth(700);
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsAssetsPriviDTO.class);
				
			   } else if(lookUpName.equals("LOOK_UP_ORUSER")){ //�õ�ָ��OU�µ��û�
					dispNames = new String[] {"USER_NAME", "EMPLOYEE_NUMBER",
							"LOGIN_NAME", "COMPANY_CODE", "COMPANY_NAME"};
					dispLabels = new String[] {"�û�����", "�û�Ա����", "�û���¼��", "��˾����",
								 "��˾����"};
					viewPercent = new String[] {"15%", "30%", "15%", "15%",
								  "20%"};
					retFields = new String[] {"USER_ID", "COMPANY_CODE",
								"COMPANY_NAME", "USER_NAME", "EMPLOYEE_NUMBER",
								"LOGIN_NAME"};
					qryNames = new String[] {"USER_NAME", "EMPLOYEE_NUMBER",
							   "LOGIN_NAME"};
					qryLabels = new String[] {"����", "Ա����", "��¼��"};
					primaryKeys = new String[] {"USER_ID"};
	
					lookProp.setTotalWidth(700);
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsAssetsPriviDTO.class);					
				}else if (lookUpName.equals("LOOK_UP_CITY")) { //�ʲ�����Ա
					dispNames = new String[] {"COMPANY", "BOOK_TYPE_NAME"};
				    dispLabels = new String[] {"��˾����", "�ʲ��˲�"};
				    viewPercent = new String[] { "50%", "50%"};
				    retFields = new String[] {"COMPANY_CODE", "BOOK_TYPE_CODE"};
				    qryNames = new String[] {"COMPANY_CODE", "COMPANY"
						   };
				    qryLabels = new String[] {"��˾����", "��˾����"};
				    primaryKeys = new String[] {"COMPANY_CODE"};

					lookProp.setTotalWidth(700);
					lookProp.setMultipleChose(true);
					lookProp.setDtoClass(AmsAssetsPriviDTO.class);
				
			   }else if (lookUpName.equals("LOOK_UP_LOCATION_TASK")) { //���ҵص�
					dispNames = new String[] {"COMPANY_NAME", "COUNTY_NAME",
								"OBJECT_CATEGORY", "OBJECT_CODE", "OBJECT_NAME"};
					dispLabels = new String[] {"��˾����", "�ɱ�����", "�ص�רҵ", "�ص����","�ص�����"};
					viewPercent = new String[] {"10%", "19%", "10%", "18%", "40%"};
					retFields = new String[] {"CHECK_LOCATION", "OBJECT_CODE",	"OBJECT_NAME"};
					qryNames = new String[] {"WORKORDER_OBJECT_CODE",
							   "WORKORDER_OBJECT_NAME", "COUNTY_NAME"};
					qryLabels = new String[] {"�ص����", "�ص�����", "�ɱ�����"};
					primaryKeys = new String[] {"CHECK_LOCATION"};

//                   contentReadio = "Y";

                   lookProp.setTotalWidth(950);
					lookProp.setMultipleChose(true);
					lookProp.setDtoClass(EtsObjectTaskDTO.class);
				} else if (lookUpName.equals("LOOK_UP_TASK")) {//���ҳɱ�����
					dispLabels = new String[] {"�ɱ����Ĵ���", "�ɱ���������"};
					viewPercent = new String[] {"30%", "60%"};
					retFields = new String[] {"COST_CENTER_CODE", "COST_CENTER_NAME"};
					qryNames = new String[] {"COST_CENTER_CODE",
							   "COST_CENTER_NAME"};
					qryLabels = new String[] {"�ɱ����Ĵ���", "�ɱ���������"};
					primaryKeys = new String[] {"COST_CENTER_CODE"};
	
	               lookProp.setTotalWidth(950);
					lookProp.setMultipleChose(true);
					lookProp.setDtoClass(EtsObjectTaskDTO.class);
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
                lookProp.setModelClass(AssetsLookUpModel.class);
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
	
	/**
	 * ����ѡ�� - SJ ADD
	 * @param req
	 * @param res
	 * @param lookupName
	 * @throws PoolException
	 * @throws QueryException
	 */
	public void setOptions( HttpServletRequest req, HttpServletResponse res , String lookupName ) throws PoolException, QueryException{
		Connection conn = null;
		try {
			conn = DBManager.getDBConnection();
			SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount( req );
			OptionProducer op = new OptionProducer( user , conn );
			
			if( lookupName.equals( LookUpConstant.LOOK_UP_PROJECT ) ){
				String projectTypeOpt = op.getDictOption3( "PROJECT_TYPE" , "" , true );
				req.setAttribute( "PROJECT_TYPE_OPT" , projectTypeOpt );
			}
		} catch (PoolException e) {
			throw e;
		} catch (QueryException e) {
			throw e;
		}finally{
			DBManager.closeDBConnection( conn );
		}
	}
}
