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
 * Description: 山西移动实物资产管理系统
 * </p>
 * <p>
 * Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007
 * </p>
 * <p>
 * Company: 北京思诺博信息技术有限公司
 * </p>
 * 
 * @author 唐明胜
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
	 * 功能：构造查询SQL。由具体需要LookUp操作的应用实现
	 */
	protected void produceSQLModel() {
		sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "";
		String lookUpName = lookProp.getLookUpName();
		if (lookUpName.equals("LOOK_UP_ORDER_TASK")) {
			AmsAssetsCheckBatchDTO dto = (AmsAssetsCheckBatchDTO) dtoParameter;
			String taskName=dto.getTaskName();//工单名称
			String taskNumber=dto.getTaskNumber();
			sqlStr+=" SELECT  CTOH.DISTRUBTE_BY, CTOH.DISTRUBTE_BY_NAME,CTOL.ORDER_NAME TASK_NAME ,CTOL.ORDER_NUMBER TASK_NUMBER," +
					" SUBSTRING(CONVERT(VARCHAR(32),CTOH.CREATION_DATE,112),1,8) SEND_DATE,CTOL.ORDER_TYPE TASK_TYPE " +
					" FROM "+
			        " AMS_ASSETS_YAER_CHECK_TASK_ORDER_LINE CTOL,"+
			        " AMS_ASSETS_YAER_CHECK_TASK_ORDER_HEADER CTOH"+
					" WHERE CTOL.HEADER_ID=CTOH.HEADER_ID"+
					" AND CTOL.IMPLEMENT_BY=?"+
					" AND   CTOL.ORDER_NUMBER LIKE '%' || dbo.NVL(?, CTOL.ORDER_NUMBER) || '%' " +
			        " AND   CTOL.ORDER_NAME LIKE '%' || dbo.NVL(?, CTOL.ORDER_NAME) || '%'" +
			        " AND  CTOL.ORDER_TYPE NOT IN('NON-ADDRESS-SOFTWARE','NON-ADDRESS-CLIENT','NON-ADDRESS-PIPELINE')";
			sqlArgs.add(user.getUserId());
//			sqlArgs.add(dto.getDeptCode());
//			sqlArgs.add(user.getOrganizationId());
			sqlArgs.add(taskNumber);
			sqlArgs.add(taskName);
		} else if (lookUpName.equals("LOOK_UP_LOCATION_TASK")) {
			/**
			 * 1.实地无线根据盘点责任人查找地点
			 * 2.实地非无线：
			 *    资产管理员、资产责任人、特定责任人
			 *    
			 * 属于非实地的地点不能查到
			 * 
			 */
		   
			
            EtsObjectTaskDTO dto = (EtsObjectTaskDTO) dtoParameter;
            String contentBlurred = dto.getContentBlurred();
            String taskType=dto.getTaskType();//任务编号[根据立忠设计修改该SQL]
            if(taskType.equals(AssetsCheckTaskConstant.ORDER_TYPE_ADDRESS_WIRELESS)){
            	//实地无线类  对应盘点责任人
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
					 + " ETS_OU_CITY_MAP    EOCM," +
					   " AMS_ASSETS_YEAR_CHECK_RESP_MAP_LOCTION CRML, " +
					   " AMS_ASSETS_YAER_CHECK_TASK_ORDER_LINE CTOL," +
					   " SF_USER SU" //盘点责任人对应地点表
					 + " WHERE"
					 + " EO.COUNTY_CODE *= EC.COUNTY_CODE"
					 + " AND EO.ORGANIZATION_ID *= EC.ORGANIZATION_ID"
					 
					 + " AND CRML.EMPLOYEE_NUMBER = SU.EMPLOYEE_NUMBER"
					 + " AND CTOL.IMPLEMENT_BY = SU.USER_ID"
					 + " AND CTOL.IMPLEMENT_ORGANIZATION_ID = SU.ORGANIZATION_ID"
					 + " AND CRML.WORKORDER_OBJECT_CODE = EO.WORKORDER_OBJECT_CODE"
					 + " AND CTOL.ORDER_NUMBER=?"
					 
					 
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
            	sqlArgs.add(dto.getOrderNumber());
             sqlArgs.add(AssetsDictConstant.OBJECT_CATEGORY);
		     sqlArgs.add(dto.getOrganizationId());
           sqlStr +=    
					     " AND (? ='' OR EO.WORKORDER_OBJECT_CODE LIKE ?)"
					   + " AND (? ='' OR EO.WORKORDER_OBJECT_NAME LIKE ?)"
           		   + " AND (? ='' OR EO.COST_CODE = ?)";
           sqlStr+= " AND NOT EXISTS(SELECT 'A'\n" +
                         "                  FROM AMS_ASSETS_CHECK_HEADER AACH1\n" +
                         "                 WHERE AACH1.CHECK_LOCATION = EO.WORKORDER_OBJECT_NO\n" +
                         "                   AND (AACH1.ORDER_STATUS  = 'DOWNLOADED' OR AACH1.ORDER_STATUS  = 'UPLOADED')" +
                         "                   AND AACH1.CHECK_CATEGORY ='')";
			   sqlArgs.add(dto.getWorkorderObjectCode());
			   sqlArgs.add(dto.getWorkorderObjectCode());
			   sqlArgs.add(dto.getWorkorderObjectName());
			   sqlArgs.add(dto.getWorkorderObjectName());
              sqlArgs.add(dto.getCountyName());
              sqlArgs.add(dto.getCountyName());
              sqlStr+= " AND EO.OBJECT_CATEGORY IN ('15','10','20','30') ";
            	
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
            //排除非实地资产
            sqlStr+=
//	            	" AND EO.OBJECT_CATEGORY_NAME<>'客户地点' \n"+//---客户端资产地点
            	    " AND  EII.BARCODE NOT IN (SELECT BARCODE FROM AMS_ASSETS_YAER_CHECK_CLIENT WHERE ORGANIZATION_ID=?)"+
	            	" AND EO.WORKORDER_OBJECT_NO NOT IN(\n"+
	            	" SELECT AOA.OBJECT_NO  \n"+
	            	" FROM   AMS_OBJECT_ADDRESS AOA,\n"+
	            	"         ETS_ITEM_INFO EII\n"+
	            	" WHERE EII.ADDRESS_ID=AOA.ADDRESS_ID\n"+
	            	" AND (\n"+
	            	"      EII.CONTENT_CODE like '%9999'\n"+//---软件类资产
	            	"     OR EII.CONTENT_CODE LIKE '%.04-07%' \n"+
	            	"     OR EII.CONTENT_CODE LIKE '%.04-06%'\n"+
	            	"     OR EII.CONTENT_CODE LIKE '%.04-04%'\n"+
	            	"     OR EII.CONTENT_CODE LIKE '%.04-05%'\n"+
	            	" )\n"+
	            	" )\n";
            
		} else if(lookUpName.equals("LOOK_UP_NO_ADDRESS_TASK")){
			EtsItemYearCheckDTO dto = (EtsItemYearCheckDTO) dtoParameter;
			String taskName=dto.getOrderName();//工单名称
			String taskNumber=dto.getOrderNumber();
			sqlStr+=" SELECT  CTOL.ORDER_NAME  ,CTOL.ORDER_NUMBER ," +
					" CTOL.IMPLEMNET_ROLE_NAME  ROLE_NAME , " +
					" CTOL.IMPLEMNET_DEPT_NAME DEPT_NAME," +
					" CTOL.ORDER_TYPE " +
					" FROM "+
			        " AMS_ASSETS_YAER_CHECK_TASK_ORDER_LINE CTOL,"+
			        " AMS_ASSETS_YAER_CHECK_TASK_ORDER_HEADER CTOH"+
					" WHERE CTOL.HEADER_ID=CTOH.HEADER_ID"+
					" AND CTOL.IMPLEMENT_BY=?"+
					" AND   CTOL.ORDER_NUMBER LIKE '%' || dbo.NVL(?, CTOL.ORDER_NUMBER) || '%' " +
			        " AND   CTOL.ORDER_NAME LIKE '%' || dbo.NVL(?, CTOL.ORDER_NAME) || '%'" +
			        " AND  CTOL.ORDER_TYPE  IN('NON-ADDRESS-SOFTWARE','NON-ADDRESS-CLIENT','NON-ADDRESS-PIPELINE')";
			sqlArgs.add(user.getUserId());
//			sqlArgs.add(dto.getDeptCode());
//			sqlArgs.add(user.getOrganizationId());
			sqlArgs.add(taskNumber);
			sqlArgs.add(taskName);
		}
		sqlModel.setArgs(sqlArgs);
		sqlModel.setSqlStr(sqlStr);
	}
	
	
}
