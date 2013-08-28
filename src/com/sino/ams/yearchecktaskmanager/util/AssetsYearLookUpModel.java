package com.sino.ams.yearchecktaskmanager.util;



import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.LookUpConstant;
import com.sino.ams.newasset.assetsharing.dto.AssetSharingLineDTO;
import com.sino.ams.newasset.bean.sql.AssetsLookupSQL;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsLookUpConstant;
import com.sino.ams.newasset.dto.AmsAccountVDTO;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.newasset.dto.AmsAssetsCheckBatchDTO;
import com.sino.ams.newasset.dto.AmsAssetsPriviDTO;
import com.sino.ams.newasset.dto.AmsAssetsTransLineDTO;
import com.sino.ams.newasset.dto.AmsCexDTO;
import com.sino.ams.newasset.dto.AmsFaCategoryVDTO;
import com.sino.ams.newasset.dto.AmsLneDTO;
import com.sino.ams.newasset.dto.AmsMisCostDTO;
import com.sino.ams.newasset.dto.AmsMisDeptDTO;
import com.sino.ams.newasset.dto.AmsMisEmployeeDTO;
import com.sino.ams.newasset.dto.AmsNleDTO;
import com.sino.ams.newasset.dto.AmsOpeDTO;
import com.sino.ams.newasset.dto.AmsSnDTO;
import com.sino.ams.newasset.lease.constant.LeaseAppConstant;
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
import com.sino.ams.yearchecktaskmanager.dto.AssetsYeartParentOrderDTO;
import com.sino.ams.yearchecktaskmanager.dto.EtsItemYearCheckDTO;
import com.sino.ams.yearchecktaskmanager.dto.EtsObjectTaskDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.lookup.LookUpModel;
import com.sino.base.lookup.LookUpProp;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.security.dto.ServletConfigDTO;

/**
 * <p>
 * Title: SinoEAM
 * </p>
 * <p>
 * Description: ɽ���ƶ�ʵ���ʲ�����ϵͳ
 * </p>
 * <p>
 * Copyright: ����˼ŵ����Ϣ�������޹�˾��Ȩ����Copyright (c) 2007
 * </p>
 * <p>
 * Company: ����˼ŵ����Ϣ�������޹�˾
 * </p>
 * 
 * @author ����ʤ
 * @version 1.0
 */
public class AssetsYearLookUpModel extends LookUpModel {
	private SfUserDTO user = null;
	private String proCode = "";

	public AssetsYearLookUpModel(BaseUserDTO userAccount, DTO dtoParameter,
			LookUpProp lookProp) {
		super(userAccount, dtoParameter, lookProp);
		this.user = (SfUserDTO) userAccount;

	}

	public void setServletConfig(ServletConfigDTO servletConfig) {
		super.setServletConfig(servletConfig);
		this.proCode = servletConfig.getProvinceCode();
	}

	/**
	 * ���ܣ������ѯSQL���ɾ�����ҪLookUp������Ӧ��ʵ��
	 */
	protected void produceSQLModel() {
		sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "";
		String lookUpName = lookProp.getLookUpName();
		if (lookUpName.equals("LOOK_UP_ORDER_TASK")) {
			AmsAssetsCheckBatchDTO dto = (AmsAssetsCheckBatchDTO) dtoParameter;
			String taskName=dto.getTaskName();//��������
			String taskNumber=dto.getTaskNumber();
			sqlStr+=" SELECT  CTOH.DISTRUBTE_BY, CTOH.DISTRUBTE_BY_NAME,CTOL.ORDER_NAME TASK_NAME ,CTOL.ORDER_NUMBER TASK_NUMBER," +
					" SUBSTRING(CONVERT(VARCHAR(32),CTOH.CREATION_DATE,112),1,8) SEND_DATE,CTOL.ORDER_TYPE TASK_TYPE ," ;
			sqlStr+=
				    " CASE WHEN CTOL.ORDER_TYPE='"+AssetsCheckTaskConstant.ORDER_TYPE_ASS_CHK_TASK+"' THEN '����̵�' " +
		 		    " WHEN CTOL.ORDER_TYPE='"+AssetsCheckTaskConstant.ORDER_TYPE_NON_ADDRESS_SOFTWARE+"' THEN '��ʵ��[�����]'" +
		 		    " WHEN CTOL.ORDER_TYPE='"+AssetsCheckTaskConstant.ORDER_TYPE_NON_ADDRESS_CLIENT+"' THEN '��ʵ��[�ͻ�����]'" +
		 		    " WHEN CTOL.ORDER_TYPE='"+AssetsCheckTaskConstant.ORDER_TYPE_NON_ADDRESS_PIPELINE+"' THEN '��ʵ��[���¡���·���ܵ���]' " +
		 		    " WHEN CTOL.ORDER_TYPE='"+AssetsCheckTaskConstant.ORDER_TYPE_ADDRESS_WIRELESS+"' THEN 'ʵ��������'" +
		 		    " WHEN CTOL.ORDER_TYPE='"+AssetsCheckTaskConstant.ORDER_TYPE_ADDRESS_NON_WIRELESS+"' then 'ʵ�ط�������' " +
		 		    " ELSE '����̵�' END TASK_TYPE_NAME \n" ;
				
			sqlStr+=" FROM "+
			        " AMS_ASSETS_YAER_CHECK_TASK_ORDER_LINE CTOL,"+
			        " AMS_ASSETS_YAER_CHECK_TASK_ORDER_HEADER CTOH"+
					" WHERE CTOL.HEADER_ID=CTOH.HEADER_ID"+
					" AND CTOL.IMPLEMENT_BY=?"+
					" AND   CTOL.ORDER_NUMBER LIKE '%' || dbo.NVL(?, CTOL.ORDER_NUMBER) || '%' " +
			        " AND   CTOL.ORDER_NAME LIKE '%' || dbo.NVL(?, CTOL.ORDER_NAME) || '%'" +
			        //" AND  CTOL.ORDER_TYPE NOT IN('NON-ADDRESS-SOFTWARE','NON-ADDRESS-CLIENT','NON-ADDRESS-PIPELINE')";
			        " AND  CTOL.ORDER_TYPE  IN('ADDRESS-WIRELESS','ADDRESS-NON-WIRELESS')";
			
			
			
			sqlStr+=" UNION SELECT  CTOH.DISTRUBTE_BY, CTOH.DISTRUBTE_BY_NAME,CTOL.ORDER_NAME TASK_NAME ,CTOL.ORDER_NUMBER TASK_NUMBER," +
					" SUBSTRING(CONVERT(VARCHAR(32),CTOH.CREATION_DATE,112),1,8) SEND_DATE,CTOL.ORDER_TYPE TASK_TYPE ," ;
			sqlStr+=
				    " CASE WHEN CTOL.ORDER_TYPE='"+AssetsCheckTaskConstant.ORDER_TYPE_ASS_CHK_TASK+"' THEN '����̵�' " +
		 		    " WHEN CTOL.ORDER_TYPE='"+AssetsCheckTaskConstant.ORDER_TYPE_NON_ADDRESS_SOFTWARE+"' THEN '��ʵ��[�����]'" +
		 		    " WHEN CTOL.ORDER_TYPE='"+AssetsCheckTaskConstant.ORDER_TYPE_NON_ADDRESS_CLIENT+"' THEN '��ʵ��[�ͻ�����]'" +
		 		    " WHEN CTOL.ORDER_TYPE='"+AssetsCheckTaskConstant.ORDER_TYPE_NON_ADDRESS_PIPELINE+"' THEN '��ʵ��[���¡���·���ܵ���]' " +
		 		    " WHEN CTOL.ORDER_TYPE='"+AssetsCheckTaskConstant.ORDER_TYPE_ADDRESS_WIRELESS+"' THEN 'ʵ��������'" +
		 		    " WHEN CTOL.ORDER_TYPE='"+AssetsCheckTaskConstant.ORDER_TYPE_ADDRESS_NON_WIRELESS+"' then 'ʵ�ط�������' " +
		 		    " ELSE '����̵�' END TASK_TYPE_NAME \n" ;
		
			sqlStr+=" FROM "+
			        " AMS_ASSETS_YAER_CHECK_TASK_ORDER_LINE CTOL,\n" +
			        " SF_USER SU,\n" +
			        " AMS_MIS_EMPLOYEE AME, \n"+
			        " AMS_ASSETS_YAER_CHECK_TASK_ORDER_HEADER CTOH \n"+
					" WHERE CTOL.HEADER_ID=CTOH.HEADER_ID \n"+
					" AND   CTOL.ORDER_NUMBER LIKE '%' || dbo.NVL(?, CTOL.ORDER_NUMBER) || '%' \n" +
			        " AND   CTOL.ORDER_NAME LIKE '%' || dbo.NVL(?, CTOL.ORDER_NAME) || '%' \n" +
			        " AND   CTOL.ORDER_TYPE ='ADDRESS-WIRELESS' \n"+
			        " AND   AME.EMPLOYEE_NUMBER = SU.EMPLOYEE_NUMBER \n"+
				    " AND   CTOL.IMPLEMENT_BY= SU.USER_ID \n" +
				    " AND   SU.EMPLOYEE_NUMBER IN ( \n" +
				    "          SELECT ARML.EMPLOYEE_NUMBER FROM \n"+//----��ǰ��½�˶�Ӧ�ɱ������µ��̵�������
				    "                 SF_USER SU,\n"+
				    "                 AMS_MIS_DEPT AMD,\n"+
				    "                 AMS_MIS_EMPLOYEE AME,\n"+
				    "                 AMS_ASSETS_YEAR_CHECK_RESP_MAP_LOCTION ARML,\n"+
				    "                 ETS_OBJECT EO\n"+
				    " WHERE AME.EMPLOYEE_NUMBER = SU.EMPLOYEE_NUMBER\n"+
				    " AND   AME.DEPT_CODE = AMD.DEPT_CODE\n"+
				    " AND   EO.WORKORDER_OBJECT_CODE=ARML.WORKORDER_OBJECT_CODE\n"+
//				    " AND   EO.COST_CODE=AMD.COST_CENTER_CODE\n"+
				    " AND   EO.COUNTY_CODE=AMD.COST_CENTER_CODE\n"+//JX
				    " AND   SU.USER_ID=? )\n";//-----��ǰ��½�˵ĳɱ�����
			
			
			sqlArgs.add(user.getUserId());
			sqlArgs.add(taskNumber);
			sqlArgs.add(taskName);
			sqlArgs.add(taskNumber);
			sqlArgs.add(taskName);
			sqlArgs.add(user.getUserId());
		} else if (lookUpName.equals("LOOK_UP_LOCATION_TASK")) {
			/**
			 * 1.ʵ�����߸����̵������˲��ҵص�
			 * 2.ʵ�ط����ߣ�
			 *    �ʲ�����Ա���ʲ������ˡ��ض�������
			 *    
			 * ���ڷ�ʵ�صĵص㲻�ܲ鵽
			 * 
			 */
		   
			
            EtsObjectTaskDTO dto = (EtsObjectTaskDTO) dtoParameter;
            String contentBlurred = dto.getContentBlurred();
            String taskType=dto.getTaskType();//������[������������޸ĸ�SQL]
            if(taskType.equals(AssetsCheckTaskConstant.ORDER_TYPE_ADDRESS_WIRELESS)){
            	//ʵ��������  ��Ӧ�̵�������
            	sqlStr = "SELECT"
					 + " EOCM.COMPANY COMPANY_NAME,\n"
					 + " EC.COUNTY_NAME,\n"
					 //+ " EFV.VALUE OBJECT_CATEGORY,"
					 + " EO.WORKORDER_OBJECT_NO CHECK_LOCATION,\n"
					 + " EO.WORKORDER_OBJECT_CODE OBJECT_CODE,\n"
					 + " EO.WORKORDER_OBJECT_NAME OBJECT_NAME,\n"
					 + " EO.WORKORDER_OBJECT_LOCATION OBJECT_LOCATION\n"
					 + " FROM \n"
					 + " ETS_OBJECT         EO,\n"
					 + " ETS_COUNTY         EC,\n"
					 + " ETS_FLEX_VALUE_SET EFVS,\n"
					 + " ETS_FLEX_VALUES    EFV,\n"
					 + " ETS_OU_CITY_MAP    EOCM,\n" +
					   " AMS_ASSETS_YEAR_CHECK_RESP_MAP_LOCTION CRML,\n " +
					   " AMS_ASSETS_YAER_CHECK_TASK_ORDER_LINE CTOL,\n" +
					   " SF_USER SU\n" //�̵������˶�Ӧ�ص��
					 + " WHERE\n"
					 + " EO.COUNTY_CODE *= EC.COUNTY_CODE\n"
					 + " AND EO.ORGANIZATION_ID *= EC.ORGANIZATION_ID\n"
					 
					 + " AND CRML.EMPLOYEE_NUMBER = SU.EMPLOYEE_NUMBER\n"
					 + " AND CTOL.IMPLEMENT_BY = SU.USER_ID\n"
					 + " AND CTOL.IMPLEMENT_ORGANIZATION_ID = SU.ORGANIZATION_ID\n"
					 + " AND CRML.WORKORDER_OBJECT_CODE = EO.WORKORDER_OBJECT_CODE\n" +
					   " AND CRML.ORGANIZATION_ID = EO.ORGANIZATION_ID "
					 + " AND CTOL.ORDER_NUMBER=?\n"
					 
					 
					 + " AND EO.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n"
					 + " AND EO.OBJECT_CATEGORY = EFV.CODE\n"
					 + " AND EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID\n"
                    + " AND EFVS.CODE = ?\n"
					 + " AND EO.ORGANIZATION_ID = ?\n"
					 + " AND (EO.DISABLE_DATE "+SyBaseSQLUtil.isNullNoParam()+" OR EO.DISABLE_DATE > GETDATE() OR EO.DISABLE_DATE IS NULL)\n" +
                   "    AND NOT EXISTS\n" +
                   " (SELECT 'A'\n" +
                   "          FROM AMS_ASSETS_CHECK_HEADER AACH\n" +
                   "         WHERE AACH.CHECK_LOCATION = EO.WORKORDER_OBJECT_NO\n" +
                   "           AND AACH.ORDER_STATUS NOT IN ('CANCELED', 'ARCHIEVED')\n" +
                   "           AND AACH.CHECK_CATEGORY ='')\n";
            	sqlArgs.add(dto.getOrderNumber());
             sqlArgs.add(AssetsDictConstant.OBJECT_CATEGORY);
		     sqlArgs.add(dto.getOrganizationId());
           sqlStr +=    
					     " AND (? ='' OR EO.WORKORDER_OBJECT_CODE LIKE ?)\n"
					   + " AND (? ='' OR EO.WORKORDER_OBJECT_NAME LIKE ?)\n"
           		   + " AND (? ='' OR EO.COST_CODE = ?)\n";
           sqlStr+= " AND NOT EXISTS(SELECT 'A'\n" +
                         "                  FROM AMS_ASSETS_CHECK_HEADER AACH1\n" +
                         "                 WHERE AACH1.CHECK_LOCATION = EO.WORKORDER_OBJECT_NO\n" +
                         "                   AND (AACH1.ORDER_STATUS  = 'DOWNLOADED' OR AACH1.ORDER_STATUS  = 'UPLOADED')\n" +
                         "                   AND AACH1.CHECK_CATEGORY ='')\n";
			   sqlArgs.add(dto.getWorkorderObjectCode());
			   sqlArgs.add(dto.getWorkorderObjectCode());
			   sqlArgs.add(dto.getWorkorderObjectName());
			   sqlArgs.add(dto.getWorkorderObjectName());
              sqlArgs.add(dto.getCountyName());
              sqlArgs.add(dto.getCountyName());
              sqlStr+= " AND EO.OBJECT_CATEGORY IN ('15','10','20','30') \n";
            	
            }else {
            sqlStr = "SELECT"
					 + " EOCM.COMPANY COMPANY_NAME,"
					 + " EC.COUNTY_NAME,"
					 //+ " EFV.VALUE OBJECT_CATEGORY,"
					 + " EO.WORKORDER_OBJECT_NO CHECK_LOCATION,"
					 + " EO.WORKORDER_OBJECT_CODE OBJECT_CODE,"
					 + " EO.WORKORDER_OBJECT_NAME OBJECT_NAME,"
					 + " EO.WORKORDER_OBJECT_LOCATION OBJECT_LOCATION"
					 + " FROM"
					 + " ETS_OBJECT         EO,"
					 + " ETS_COUNTY         EC,"
					 + " ETS_FLEX_VALUE_SET EFVS,"
					 + " ETS_FLEX_VALUES    EFV,"
					 + " ETS_OU_CITY_MAP    EOCM"
					 + " WHERE"
					 + " EO.COUNTY_CODE *= EC.COUNTY_CODE"
					 + " AND EO.ORGANIZATION_ID *= EC.ORGANIZATION_ID"
					 + " AND EO.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
					 + " AND EO.OBJECT_CATEGORY = EFV.CODE"
					 + " AND EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID"
                     + " AND EFVS.CODE = ?"
					 + " AND EO.ORGANIZATION_ID = ?"
					 + " AND (EO.DISABLE_DATE "+SyBaseSQLUtil.isNullNoParam()+" OR EO.DISABLE_DATE > GETDATE() OR EO.DISABLE_DATE IS NULL)" +
                    "    AND NOT EXISTS\n" +
                    " (SELECT 'A'\n" +
                    "          FROM AMS_ASSETS_CHECK_HEADER AACH\n" +
                    "         WHERE AACH.CHECK_LOCATION = EO.WORKORDER_OBJECT_NO\n" +
                    "           AND AACH.ORDER_STATUS NOT IN ('CANCELED', 'ARCHIEVED')" +
                    "           AND AACH.CHECK_CATEGORY ='')";
           sqlArgs.add(AssetsDictConstant.OBJECT_CATEGORY);
		   sqlArgs.add(dto.getOrganizationId());
            sqlStr +=    //" AND (? ='' OR EC.COUNTY_NAME LIKE ?)"
					     " AND (? ='' OR EO.WORKORDER_OBJECT_CODE LIKE ?)"
					   + " AND (? ='' OR EO.WORKORDER_OBJECT_NAME LIKE ?)"
            		   + " AND (? ='' OR EO.COST_CODE = ?)";
            sqlStr+= " AND NOT EXISTS(SELECT 'A'\n" +
                          "                  FROM AMS_ASSETS_CHECK_HEADER AACH1\n" +
                          "                 WHERE AACH1.CHECK_LOCATION = EO.WORKORDER_OBJECT_NO\n" +
                          "                   AND (AACH1.ORDER_STATUS  = 'DOWNLOADED' OR AACH1.ORDER_STATUS  = 'UPLOADED')" +
                          "                   AND AACH1.CHECK_CATEGORY ='')";
               //sqlArgs.add(dto.getCountyName());
               //sqlArgs.add(dto.getCountyName());
			   sqlArgs.add(dto.getWorkorderObjectCode());
			   sqlArgs.add(dto.getWorkorderObjectCode());
			   sqlArgs.add(dto.getWorkorderObjectName());
			   sqlArgs.add(dto.getWorkorderObjectName());
               sqlArgs.add(dto.getCountyName());
               sqlArgs.add(dto.getCountyName());
			   //sqlArgs.add(dto.getCostCenterCode());
			   //sqlArgs.add(dto.getCostCenterCode());
               sqlStr+= " AND EO.OBJECT_CATEGORY NOT IN ('15','10','20','30') ";
            }
            //<!-- 2013-07-04 Jeffery Ӧ�����¹�����ʱ������ʲ������Ǵ˴�-->
            //�ų���ʵ���ʲ�
/*            sqlStr+=
//	            	" AND EO.OBJECT_CATEGORY_NAME<>'�ͻ��ص�' \n"+//---�ͻ����ʲ��ص�
//            	    " AND  EII.BARCODE NOT IN (SELECT BARCODE FROM AMS_ASSETS_YAER_CHECK_CLIENT WHERE ORGANIZATION_ID=?)\n"+
	            	" AND EO.WORKORDER_OBJECT_NO NOT IN(\n"+
	            	" SELECT AOA.OBJECT_NO  \n"+
	            	" FROM   AMS_OBJECT_ADDRESS AOA,\n"+
	            	"        ETS_ITEM_INFO EII\n"+
	            	" WHERE EII.ADDRESS_ID=AOA.ADDRESS_ID\n"+
	            	" AND (\n"+
	            	"      EII.CONTENT_CODE like '%9999'\n"+//---������ʲ�
	            	"     OR EII.CONTENT_CODE LIKE '%.04-07%' \n"+
	            	"     OR EII.CONTENT_CODE LIKE '%.04-06%'\n"+
	            	"     OR EII.CONTENT_CODE LIKE '%.04-04%'\n"+
	            	"     OR EII.CONTENT_CODE LIKE '%.04-05%'\n" +
	            	"     OR EII.BARCODE IN (SELECT BARCODE FROM AMS_ASSETS_YAER_CHECK_CLIENT WHERE ORGANIZATION_ID=?)"+
	            	" )\n" +
	            	" )\n";
            sqlArgs.add(dto.getOrganizationId());*/
		} else if(lookUpName.equals("LOOK_UP_NO_ADDRESS_TASK")){
			EtsItemYearCheckDTO dto = (EtsItemYearCheckDTO) dtoParameter;
			String taskName=dto.getOrderName();//��������
			String taskNumber=dto.getOrderNumber();
			sqlStr+=" SELECT  CTOL.ORDER_NAME  ,CTOL.ORDER_NUMBER ,\n" +
					" CTOL.IMPLEMNET_ROLE_NAME  ROLE_NAME , \n" +
					" CTOL.IMPLEMNET_DEPT_NAME DEPT_NAME,\n" +
					" CTOL.ORDER_TYPE,\n" +
					
					" CASE WHEN ORDER_TYPE='"+AssetsCheckTaskConstant.ORDER_TYPE_ASS_CHK_TASK+"' then '����̵�' " +
    		        " WHEN ORDER_TYPE='"+AssetsCheckTaskConstant.ORDER_TYPE_NON_ADDRESS_SOFTWARE+"' then '��ʵ��[�����]'" +
        		    " WHEN ORDER_TYPE='"+AssetsCheckTaskConstant.ORDER_TYPE_NON_ADDRESS_CLIENT+"' then '��ʵ��[�ͻ�����]'" +
        		    " WHEN ORDER_TYPE='"+AssetsCheckTaskConstant.ORDER_TYPE_NON_ADDRESS_PIPELINE+"' then '��ʵ��[���¡���·���ܵ���]' " +
        		    " WHEN ORDER_TYPE='"+AssetsCheckTaskConstant.ORDER_TYPE_ADDRESS_WIRELESS+"' then ' ʵ��������'" +
                    " WHEN ORDER_TYPE='"+AssetsCheckTaskConstant.ORDER_TYPE_ADDRESS_NON_WIRELESS+"' then 'ʵ�ط�������' " +
                    " ELSE '����̵�' end ORDER_TYPE_NAME\n" +
					
					" FROM \n"+
			        " AMS_ASSETS_YAER_CHECK_TASK_ORDER_LINE CTOL,\n"+
			        " AMS_ASSETS_YAER_CHECK_TASK_ORDER_HEADER CTOH\n"+
					" WHERE CTOL.HEADER_ID=CTOH.HEADER_ID\n"+
					" AND   CTOL.IMPLEMENT_BY=?\n"+
					" AND   CTOL.ORDER_NUMBER LIKE '%' || dbo.NVL(?, CTOL.ORDER_NUMBER) || '%' \n" +
			        " AND   CTOL.ORDER_NAME LIKE '%' || dbo.NVL(?, CTOL.ORDER_NAME) || '%'\n" +
			        " AND   CTOL.ORDER_TYPE  IN('NON-ADDRESS-SOFTWARE','NON-ADDRESS-CLIENT','NON-ADDRESS-PIPELINE')\n" +
			        " AND   CTOL.ORDER_STATUS NOT IN('DO_CANCLE','DO_RETURN','DO_COMPLETE')";
			sqlArgs.add(user.getUserId());
			sqlArgs.add(taskNumber);
			sqlArgs.add(taskName);
		}else if (lookUpName.equals("LOOK_UP_USER")) {//GROUP
			AmsAssetsPriviDTO dto = (AmsAssetsPriviDTO) dtoParameter;
			String isTd=user.getIsTd();  //�Ƿ�TD�û�
			sqlStr = "SELECT"
					+ " DISTINCT(EOCM.COMPANY_CODE),"
					+ " EOCM.COMPANY COMPANY_NAME,"
					+ " SU.LOGIN_NAME,"
					+ " SU.USER_ID,"
					+ " SU.USERNAME USER_NAME,"
					+ " SU.EMPLOYEE_NUMBER"
					+ " FROM"
					+ " SF_USER         SU,"
//					+ " SF_USER_RIGHT   SUR,"
					+ " AMS_MIS_EMPLOYEE   AME,"
					+ " AMS_MIS_DEPT   AMD,"
					+ " ETS_OU_CITY_MAP EOCM"
					+ " WHERE"
					+ " SU.ENABLED = 'Y'"
					+ " AND SU.ORGANIZATION_ID *= EOCM.ORGANIZATION_ID"
//					+ " AND SUR.USER_ID = SU.USER_ID"
					+ " AND AME.EMPLOYEE_NUMBER = SU.EMPLOYEE_NUMBER";
					if("Y".equalsIgnoreCase(isTd)){
//						sqlStr+= " AND EOCM.COMPANY_CODE=AME.COMPANY_CODE ";
					}else {
					    sqlStr+= " AND AME.DEPT_CODE = AMD.DEPT_CODE";
					}
					sqlStr+=
					  " AND SU.ORGANIZATION_ID = ?" + " AND ("
					+ SyBaseSQLUtil.isNull() + "  OR SU.USERNAME LIKE ?)"
					+ " AND (" + SyBaseSQLUtil.isNull()
					+ " OR SU.LOGIN_NAME LIKE UPPER(?))" + " AND ( "
					+ SyBaseSQLUtil.isNull()
					+ " OR SU.EMPLOYEE_NUMBER LIKE ?)"
//					+ " AND SUR.GROUP_ID = CONVERT(INT, dbo.NVL( (CASE ? WHEN '-1' THEN NULL WHEN '0' THEN NULL ELSE ? END), CONVERT(VARCHAR, SUR.GROUP_ID)))"
					+ " AND AMD.DEPT_CODE=?"
//					+ " AND EXISTS("
//					+ " SELECT"
//					+ " NULL"
//					+ " FROM"
//					+ " SF_USER_RIGHT SUR"
//					+ " WHERE"
//					+ " SUR.USER_ID = SU.USER_ID"
//					+ " AND SUR.GROUP_ID = CONVERT(INT, dbo.NVL( (CASE ? WHEN '-1' THEN NULL WHEN '0' THEN NULL ELSE ? END), CONVERT(VARCHAR, SUR.GROUP_ID))))"
//					+ " AND EXISTS("
//					+ " SELECT NULL FROM AMS_MIS_DEPT AMD,AMS_MIS_EMPLOYEE AME " +
//					  " WHERE SU.EMPLOYEE_NUMBER=AME.EMPLOYEE_NUMBER" +
//					  " AND   AME.DEPT_CODE=AMD.DEPT_CODE"
//					+ ")"
					
					+ " ORDER BY SU.LOGIN_NAME ";
			sqlArgs.add(user.getOrganizationId());
			sqlArgs.add(dto.getUserName());
			sqlArgs.add(dto.getUserName());
			sqlArgs.add(dto.getLoginName());
			sqlArgs.add(dto.getLoginName());
			sqlArgs.add(dto.getEmployeeNumber());
			sqlArgs.add(dto.getEmployeeNumber());
//			sqlArgs.add("" + dto.getGroupId());
//			sqlArgs.add("" + dto.getGroupId());
			sqlArgs.add( dto.getDeptCode());
			
			
			
//			AmsAssetsPriviDTO dto = (AmsAssetsPriviDTO) dtoParameter;
//			sqlStr = "SELECT"
//					+ " EOCM.COMPANY_CODE,"
//					+ " EOCM.COMPANY COMPANY_NAME,"
//					+ " SU.LOGIN_NAME,"
//					+ " SU.USER_ID,"
//					+ " SU.USERNAME USER_NAME,"
//					+ " SU.EMPLOYEE_NUMBER"
//					+ " FROM"
//					+ " SF_USER         SU,"
//					+ " ETS_OU_CITY_MAP EOCM"
//					+ " WHERE"
//					+ " SU.ENABLED = 'Y'"
//					+ " AND SU.ORGANIZATION_ID *= EOCM.ORGANIZATION_ID"
//					+ " AND SU.ORGANIZATION_ID = ?" + " AND ("
//					+ SyBaseSQLUtil.isNull() + "  OR SU.USERNAME LIKE ?)"
//					+ " AND (" + SyBaseSQLUtil.isNull()
//					+ " OR SU.LOGIN_NAME LIKE UPPER(?))" + " AND ( "
//					+ SyBaseSQLUtil.isNull()
//					+ "  OR SU.EMPLOYEE_NUMBER LIKE ?)"
//					+ " AND EXISTS("
//					+ " SELECT"
//					+ " NULL"
//					+ " FROM"
//					+ " SF_USER_RIGHT SUR"
//					+ " WHERE"
//					+ " SUR.USER_ID = SU.USER_ID"
//					+ " AND SUR.GROUP_ID = CONVERT(INT, dbo.NVL( (CASE ? WHEN '-1' THEN NULL WHEN '0' THEN NULL ELSE ? END), CONVERT(VARCHAR, SUR.GROUP_ID))))"
//					+ " ORDER BY SU.LOGIN_NAME ";
//			sqlArgs.add(user.getOrganizationId());
//			sqlArgs.add(dto.getUserName());
//			sqlArgs.add(dto.getUserName());
//			sqlArgs.add(dto.getLoginName());
//			sqlArgs.add(dto.getLoginName());
//			sqlArgs.add(dto.getEmployeeNumber());
//			sqlArgs.add(dto.getEmployeeNumber());
//			sqlArgs.add("" + dto.getGroupId());
//			sqlArgs.add("" + dto.getGroupId());
			
			
		}else if (lookUpName.equals("LOOK_UP_USER_CHECK_BATCH")) {//�ʲ��̵㵥��������ѡ��鵵��
			AmsAssetsPriviDTO dto = (AmsAssetsPriviDTO) dtoParameter;
			String isTd=user.getIsTd();  //�Ƿ�TD�û�
			sqlStr = "SELECT"
					+ " DISTINCT(EOCM.COMPANY_CODE),"
					+ " EOCM.COMPANY COMPANY_NAME,"
					+ " SU.LOGIN_NAME,"
					+ " SU.USER_ID,"
					+ " SU.USERNAME USER_NAME,"
					+ " SU.EMPLOYEE_NUMBER"
					+ " FROM"
					+ " SF_USER         SU,"
					+ " AMS_MIS_EMPLOYEE   AME,"
					+ " AMS_MIS_DEPT   AMD,"
					+ " ETS_OU_CITY_MAP EOCM"
					+ " WHERE"
					+ " SU.ENABLED = 'Y'"
					+ " AND SU.ORGANIZATION_ID *= EOCM.ORGANIZATION_ID"
					+ " AND AME.EMPLOYEE_NUMBER = SU.EMPLOYEE_NUMBER";
			
					if("Y".equalsIgnoreCase(isTd)){
//						sqlStr+= " AND EOCM.COMPANY_CODE=AME.COMPANY_CODE ";
					}else {
					    sqlStr+= " AND AME.DEPT_CODE = AMD.DEPT_CODE";
					}
					sqlStr+=
					 " AND SU.ORGANIZATION_ID = ?" + " AND ("
					+ SyBaseSQLUtil.isNull() + "  OR SU.USERNAME LIKE ?)"
					+ " AND (" + SyBaseSQLUtil.isNull()
					+ " OR SU.LOGIN_NAME LIKE UPPER(?))" + " AND ( "
					+ SyBaseSQLUtil.isNull()
					+ "  OR SU.EMPLOYEE_NUMBER LIKE ?)"
//					+ " AND EXISTS("
//					+ " SELECT"
//					+ " NULL"
//					+ " FROM"
//					+ " SF_USER_RIGHT SUR"
//					+ " WHERE"
//					+ " SUR.USER_ID = SU.USER_ID"
//					+ " AND SUR.ROLE_ID = 1251"//�鵵��Ȩ��
//					+ " AND SUR.GROUP_ID = CONVERT(INT, dbo.NVL( (CASE ? WHEN '-1' THEN NULL WHEN '0' THEN NULL ELSE ? END), 
//					CONVERT(VARCHAR, SUR.GROUP_ID))))"
					+ " AND EXISTS "
		            + "	      (SELECT NULL " 
		            + "       	 FROM SF_USER_AUTHORITY SUA " 
		            + "       	WHERE SU.USER_ID = SUA.USER_ID " 
		            + "            AND SUA.ROLE_NAME = '�����鵵��') " 
		            + " AND AMD.DEPT_CODE=?"
					+ " ORDER BY SU.LOGIN_NAME ";
			sqlArgs.add(user.getOrganizationId());
			sqlArgs.add(dto.getUserName());
			sqlArgs.add(dto.getUserName());
			sqlArgs.add(dto.getLoginName());
			sqlArgs.add(dto.getLoginName());
			sqlArgs.add(dto.getEmployeeNumber());
			sqlArgs.add(dto.getEmployeeNumber());
			sqlArgs.add( dto.getDeptCode());
//			sqlArgs.add("" + dto.getGroupId());
//			sqlArgs.add("" + dto.getGroupId());
			
//			AmsAssetsPriviDTO dto = (AmsAssetsPriviDTO) dtoParameter;
//			sqlStr = "SELECT"
//					+ " EOCM.COMPANY_CODE,"
//					+ " EOCM.COMPANY COMPANY_NAME,"
//					+ " SU.LOGIN_NAME,"
//					+ " SU.USER_ID,"
//					+ " SU.USERNAME USER_NAME,"
//					+ " SU.EMPLOYEE_NUMBER"
//					+ " FROM"
//					+ " SF_USER         SU,"
//					+ " ETS_OU_CITY_MAP EOCM"
//					+ " WHERE"
//					+ " SU.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
//					+ " AND SU.ORGANIZATION_ID = ?" + " AND ("
//					+ SyBaseSQLUtil.isNull() + "  OR SU.USERNAME LIKE ?)"
//					+ " AND (" + SyBaseSQLUtil.isNull()
//					+ " OR SU.LOGIN_NAME LIKE UPPER(?))" + " AND ( "
//					+ SyBaseSQLUtil.isNull()
//					+ "  OR SU.EMPLOYEE_NUMBER LIKE ?)"
//		            + " AND EXISTS "
//		            + "	      (SELECT NULL " 
//		            + "       	 FROM SF_USER_AUTHORITY SUA " 
//		            + "       	WHERE SU.USER_ID = SUA.USER_ID " 
//		            + "            AND SUA.ROLE_NAME = '�����鵵��') " 
//		            
//					+ " AND EXISTS "
//					+ " 		(SELECT NULL"
//					+ " 		   FROM SF_USER_RIGHT SUR"
//					+ " 		  WHERE SUR.USER_ID = SU.USER_ID"
//					+ " 			AND SUR.GROUP_ID = CONVERT(INT, dbo.NVL( (CASE ? WHEN '-1' THEN NULL WHEN '0' THEN NULL ELSE ? END), CONVERT(VARCHAR, SUR.GROUP_ID))))"
//					+ " ORDER BY SU.LOGIN_NAME ";
//			sqlArgs.add(user.getOrganizationId());
//			sqlArgs.add(dto.getUserName());
//			sqlArgs.add(dto.getUserName());
//			sqlArgs.add(dto.getLoginName());
//			sqlArgs.add(dto.getLoginName());
//			sqlArgs.add(dto.getEmployeeNumber());
//			sqlArgs.add(dto.getEmployeeNumber());
//			sqlArgs.add("" + dto.getGroupId());
//			sqlArgs.add("" + dto.getGroupId());
			
		} else if(lookUpName.equals("LOOK_UP_PARENT_ORDER_LEVE_3")){//���Ҹ�������
			AssetsYeartParentOrderDTO dto = (AssetsYeartParentOrderDTO) dtoParameter;
			StringBuffer sqlStrs = new StringBuffer();
			String orderLevel = "";
			if(lookUpName.equals("LOOK_UP_PARENT_ORDER_LEVE_3")){
				orderLevel = "3";
			}
			sqlStrs.append(" SELECT ORDER_NAME PARENT_ORDER_NAME, ")
				  .append(" ORDER_NUMBER PARENT_ORDER_NUMBER,ORDER_TYPE PARENT_ORDER_TYPE, ")
				  .append(" case when ORDER_TYPE='"+AssetsCheckTaskConstant.ORDER_TYPE_ASS_CHK_TASK+"' then '����̵�' " )
				  .append(" when ORDER_TYPE='"+AssetsCheckTaskConstant.ORDER_TYPE_NON_ADDRESS_SOFTWARE+"' then '��ʵ��[�����]' " )
				  .append(" when ORDER_TYPE='"+AssetsCheckTaskConstant.ORDER_TYPE_NON_ADDRESS_CLIENT+"' then '��ʵ��[�ͻ�����]' " )
				  .append(" when ORDER_TYPE='"+AssetsCheckTaskConstant.ORDER_TYPE_NON_ADDRESS_PIPELINE+"' then '��ʵ��[���¡���·���ܵ���]' " )
				  .append(" when ORDER_TYPE='"+AssetsCheckTaskConstant.ORDER_TYPE_ADDRESS_WIRELESS+"' then ' ʵ��������' " )
				  .append(" when ORDER_TYPE='"+AssetsCheckTaskConstant.ORDER_TYPE_ADDRESS_NON_WIRELESS+"' then 'ʵ�ط�������' " )
				  .append(" else '����̵�' end PARENT_ORDER_TYPE_NAME, ")
				  .append(" IMPLEMNET_DEPT_NAME PARENT_IMPLEMNET_DEPT_NAME ")
			      .append(" FROM AMS_ASSETS_YAER_CHECK_TASK_ORDER_LINE \n")
			      .append(" where ORDER_LEVEL = ?  AND IMPLEMENT_BY = ? ")
			      .append(" AND ORDER_TYPE IN('NON-ADDRESS-SOFTWARE','NON-ADDRESS-PIPELINE','NON-ADDRESS-CLIENT')");
			sqlArgs.add(orderLevel);
			sqlArgs.add(user.getUserId());
			if(dto.getParetnOrderNumber()!=null && !dto.getParetnOrderNumber().equals("")){
				sqlStrs.append(" AND ORDER_NUMBER like ? ");
				sqlStrs.append("%"+dto.getParetnOrderNumber().trim()+"%");
			}
			sqlStr=sqlStrs.toString();
		}
		sqlModel.setArgs(sqlArgs);
		sqlModel.setSqlStr(sqlStr);
	}
	
	
}
