package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.StrUtil;
import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.dto.AssetsTagNumberQueryDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * User: srf
 * Date: 2008-3-31
 * Time: 12:01:16
 * Function:
 */
public class AssetsTagNumberQueryModel extends AMSSQLProducer {
	private AssetsTagNumberQueryDTO dto = null;

	public AssetsTagNumberQueryModel(SfUserDTO userAccount, AssetsTagNumberQueryDTO dtoParameter) {
		super(userAccount, dtoParameter);
		this.dto = (AssetsTagNumberQueryDTO) dtoParameter;
	}

	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "";
		try {
			if (dto.getAssetsType().equals("EAM")) {
				sqlStr = "SELECT EII.SYSTEMID ASSET_NUMBER,\n" +
				         "	     dbo.GET_PRINT_HISTORY_COUNT( EII.ORGANIZATION_ID , EII.BARCODE ) PRINT_NUM,"+
						 "       EII.BARCODE TAG_NUMBER,\n" +
						 "       ESI.ITEM_NAME ASSETS_DESCRIPTION,\n" +
						 "       ESI.ITEM_SPEC MODEL_NUMBER,\n" +
						 "       EO.WORKORDER_OBJECT_CODE ASSETS_LOCATION_CODE,\n" +
						 "       EII.START_DATE DATE_PLACED_IN_SERVICE,\n" +
						 "       dbo.APP_GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') FA_CATEGORY_CODE,\n" +
						 "       EO.WORKORDER_OBJECT_NAME ASSETS_LOCATION,\n" +
						 "       dbo.APP_GET_ORGNIZATION_NAME(EII.ORGANIZATION_ID) ORGNIZATION_NAME,\n" +
						 "       AME.USER_NAME ASSIGNED_TO_NAME,\n" +
						 "       AMD.DEPT_NAME RESPONSIBILITY_DEPT,\n" +   ////
						 "       ACDM.COST_CENTER_CODE DEPRECIATION_ACCOUNT,\n" +
						 "       dbo.APP_GET_PROJECT_NAME(EII.PROJECTID) PROJECT_NAME\n" +
						 "  FROM ETS_ITEM_INFO      EII,\n" +
						 "       ETS_SYSTEM_ITEM    ESI,\n" +
						 "       AMS_OBJECT_ADDRESS AOA,\n" +
						 "       ETS_OBJECT         EO,\n" +
						 "       ETS_OU_CITY_MAP   EOCM,\n" +
						 "       AMS_MIS_EMPLOYEE   AME,\n" +
						 "       AMS_COST_DEPT_MATCH ACDM,\n" + //add by zhoujs
						 "       AMS_MIS_DEPT       AMD,\n" +
						 "       ETS_PA_PROJECTS_ALL EPPA\n" +
						 " WHERE EII.ITEM_CODE = ESI.ITEM_CODE\n" +
						 "   AND EII.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
						 "   AND AOA.ADDRESS_ID = EII.ADDRESS_ID\n" +
						 "   AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" ;
				
				// SJ modify  2011-11-22
				if( !StrUtil.isEmpty( dto.getCostCenter() ) ){
					sqlStr += "   AND EII.RESPONSIBILITY_DEPT = ACDM.DEPT_CODE \n"  + 
							  "   AND EOCM.COMPANY_CODE = ACDM.COMPANY_CODE \n"  + 
					          "   AND ACDM.COST_CENTER_CODE LIKE ? \n" ; //成本中心
				}else{
					sqlStr += "   AND EII.RESPONSIBILITY_DEPT *= ACDM.DEPT_CODE\n" + 
							  "   AND EOCM.COMPANY_CODE *= ACDM.COMPANY_CODE \n"  ;
				}
				
				// SJ modify  2011-11-22
				if( !StrUtil.isEmpty( dto.getProjectName() ) || !StrUtil.isEmpty( dto.getProjectNumber() ) ){
					sqlStr +=  "   AND EII.PROJECTID = EPPA.PROJECT_ID \n" + 
							   "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EPPA.NAME LIKE dbo.NVL(?, EPPA.NAME)) \n" +
							   "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EPPA.SEGMENT1 LIKE dbo.NVL(?, EPPA.SEGMENT1)) \n";
				}else{
					sqlStr += "   AND EII.PROJECTID *= EPPA.PROJECT_ID\n" ;
				}
				
				sqlStr += 
					 "   AND (EO.OBJECT_CATEGORY <= '73' OR EO.OBJECT_CATEGORY >= '75') \n" ;
				
				
				sqlStr += 
					 "   AND EII.ORGANIZATION_ID = ?\n" +
					 "   AND ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME)\n" +
					 "   AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE dbo.NVL(?, ESI.ITEM_SPEC))\n" +
					 "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.START_DATE <= ISNULL(?, EII.START_DATE))\n" +
					 "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.START_DATE >= ISNULL(?, EII.START_DATE))" ;
						 
				 if( StrUtil.isEmpty(dto.getResponsibilityUser())){
					 sqlStr =sqlStr + "   AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID\n" ;
					 
				 }else{
					 sqlStr =sqlStr + "   AND EII.RESPONSIBILITY_USER = AME.EMPLOYEE_ID\n" +
					 				  "   AND ( AME.USER_NAME LIKE ? )" ;
				 }
				 if( StrUtil.isEmpty(dto.getResponsibilityDept())){
					 sqlStr =sqlStr + "   AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE\n" ;
				 }else{
					 sqlStr =sqlStr + "   AND EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE\n" +
					                  "   AND (AMD.DEPT_NAME LIKE ?)" ;    ////
				 }
				 
				 sqlStr =sqlStr +
				 "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.WORKORDER_OBJECT_NAME LIKE ?)" +
//						 "   AND ( " + SyBaseSQLUtil.isNull() + "  OR ACDM.COST_CENTER_CODE LIKE ?) " + //成本中心
				 "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.WORKORDER_OBJECT_CODE LIKE ?)" ; //区域代码.地点代码
//				 "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EPPA.NAME LIKE dbo.NVL(?, EPPA.NAME))" +
//				 "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EPPA.SEGMENT1 LIKE dbo.NVL(?, EPPA.SEGMENT1))";
				if ((!dto.getTagNumber().equals("")) && (!dto.getToTagNumber().equals(""))) {
					sqlStr += "   AND EII.BARCODE>=? AND EII.BARCODE<=? ";
				} else {
					sqlStr += "   AND (  " + SyBaseSQLUtil.isNull() + "  OR EII.BARCODE LIKE ?)";
				}

				sqlStr += " ORDER BY EO.WORKORDER_OBJECT_CODE,EII.BARCODE ";
				
				if( !StrUtil.isEmpty( dto.getCostCenter() ) ){
					sqlArgs.add(dto.getCostCenter());
				}
				
				if( !StrUtil.isEmpty( dto.getProjectName() ) || !StrUtil.isEmpty( dto.getProjectNumber() ) ){
					sqlArgs.add(dto.getProjectName());
					sqlArgs.add(dto.getProjectName());
					sqlArgs.add(dto.getProjectNumber());
					sqlArgs.add(dto.getProjectNumber());
				}
				
				sqlArgs.add(userAccount.getOrganizationId());
				sqlArgs.add(dto.getAssetsDescription());
				sqlArgs.add(dto.getModelNumber());
				sqlArgs.add(dto.getModelNumber());
//                sqlArgs.add(dto.getTagNumber());
				sqlArgs.add(dto.getSQLToDate());
				sqlArgs.add(dto.getSQLToDate());
				sqlArgs.add(dto.getFromDate());
				sqlArgs.add(dto.getFromDate());
				if(!StrUtil.isEmpty(dto.getResponsibilityUser()))
				{
					sqlArgs.add(dto.getResponsibilityUser());
				}
				if(!StrUtil.isEmpty(dto.getResponsibilityDept()))
				{
					sqlArgs.add(dto.getResponsibilityDept());
				}
				sqlArgs.add(dto.getObjectName());
				sqlArgs.add(dto.getObjectName());
				
				String workorderObjectCode = "";
				if (dto.getWorkorderObjectCode().equals("")) {
					if (!dto.getAreaCode().equals("")) {
						workorderObjectCode = dto.getAreaCode() + "%";
					}
				} else {
					if (dto.getAreaCode().equals("")) {
						workorderObjectCode = "%" + dto.getWorkorderObjectCode() +
											  "%";
					} else {
						workorderObjectCode = dto.getAreaCode() + "." +
											  dto.getWorkorderObjectCode() +
											  "%";
					}

				}
				sqlArgs.add(workorderObjectCode);
				sqlArgs.add(workorderObjectCode);
				

				if ((!dto.getTagNumber().equals("")) && (!dto.getToTagNumber().equals(""))) {
					sqlArgs.add(dto.getTagNumber());
					sqlArgs.add(dto.getToTagNumber());
				} else {
					sqlArgs.add(dto.getTagNumber());
					sqlArgs.add(dto.getTagNumber());
				}

			} else if (dto.getAssetsType().equals("MIS")) {
				sqlStr = "SELECT EFA.ASSET_NUMBER,\n" +
				         "	     dbo.GET_PRINT_HISTORY_COUNT( EFA.ORGANIZATION_ID , EFA.TAG_NUMBER ) PRINT_NUM,"+
						 "       EFA.TAG_NUMBER,\n" +
						 "       EFA.ASSETS_DESCRIPTION,\n" +
						 "       EFA.MODEL_NUMBER,\n" +
						 "       EFA.ASSETS_LOCATION_CODE,\n" +
						 "       EFA.DATE_PLACED_IN_SERVICE,\n" +
						 "       EFA.FA_CATEGORY_CODE,\n" +
						 "       EFA.ASSETS_LOCATION,\n" +
						 "       dbo.APP_GET_ORGNIZATION_NAME(EFA.ORGANIZATION_ID) ORGNIZATION_NAME," +
						 "       AMD.DEPT_NAME RESPONSIBILITY_DEPT,\n" +/////
						 "       EFA.DEPRECIATION_ACCOUNT,\n" +
						 "       EFA.ASSETS_LOCATION OBJECT_NAME,\n" +
						 "       EFA.ASSIGNED_TO_NAME,\n" +
						 "       EFA.PROJECT_NAME\n" +
						 "  FROM ETS_FA_ASSETS EFA," +
						 //                "       ETS_ITEM_MATCH_COMPLET EIMC," +
						 "       AMS_MIS_EMPLOYEE AME, AMS_MIS_DEPT AMD\n" +
						 " WHERE \n" + //EFA.ASSET_ID = EIMC.ASSET_ID\n" +
						 
												 
//                        "   AND EFA.CURRENT_UNITS > EIMC.CURRENT_UNITS\n" + //20080710 zhoujs 可以查询所有ets_fa_assets标签
						 "    EFA.ASSETS_DESCRIPTION LIKE dbo.NVL(?, EFA.ASSETS_DESCRIPTION)\n" +
						 "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.MODEL_NUMBER LIKE ?)\n" +
//                        "   AND EFA.TAG_NUMBER LIKE dbo.NVL(?, EFA.TAG_NUMBER)\n" +
						 "   AND ( " + SyBaseSQLUtil.isNull() + "  OR\n" +
						 "       EFA.DATE_PLACED_IN_SERVICE <= ISNULL(?, EFA.DATE_PLACED_IN_SERVICE))\n" +
						 "   AND ( " + SyBaseSQLUtil.isNull() + "  OR\n" +
						 "       EFA.DATE_PLACED_IN_SERVICE >= ISNULL(?, EFA.DATE_PLACED_IN_SERVICE))" +
						 "  AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.ASSIGNED_TO_NAME LIKE ?)" ;
						 if("".equals(dto.getResponsibilityDept())){
							 sqlStr =sqlStr+	 "  AND EFA.ASSIGNED_TO_NUMBER *= AME.EMPLOYEE_NUMBER \n" +
							 "  AND AME.DEPT_CODE *= AMD.DEPT_CODE\n" ;
						 }else{
							 sqlStr =sqlStr+	 "  AND EFA.ASSIGNED_TO_NUMBER = AME.EMPLOYEE_NUMBER \n" +
							 "  AND AME.DEPT_CODE = AMD.DEPT_CODE\n" +
							 "  AND ( AMD.DEPT_NAME LIKE ?)" ;
						 }
						
						 sqlStr =	sqlStr +
						 "  AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.ASSETS_LOCATION LIKE ?)" +
						 "  AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.DEPRECIATION_ACCOUNT LIKE ?)" +
						 "  AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.ASSETS_LOCATION_CODE LIKE ?)" +
						 "  AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.PROJECT_NAME LIKE ?)" +
						 "  AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.MIS_PROJECT_NUMBER LIKE ?)" +
						 "  AND (? IS NULL OR ? = '' OR EFA.ASSETS_CREATE_DATE >= ?)" +
						 "  AND (? IS NULL OR ? = '' OR EFA.ASSETS_CREATE_DATE <= ?)"; //区域代码.地点代码
				if ((!dto.getTagNumber().equals("")) && (!dto.getToTagNumber().equals(""))) {
					sqlStr += "   AND EFA.TAG_NUMBER>=? AND EFA.TAG_NUMBER<=? ";
				} else {
					sqlStr += "   AND (  " + SyBaseSQLUtil.isNull() + "  OR EFA.TAG_NUMBER LIKE ?)";
				}
				sqlStr += " AND EFA.ORGANIZATION_ID = ?";

				sqlStr += "   ORDER BY EFA.ASSETS_LOCATION_CODE,EFA.TAG_NUMBER";

				sqlArgs.add(dto.getAssetsDescription());
				sqlArgs.add(dto.getModelNumber());
				sqlArgs.add(dto.getModelNumber());
//                sqlArgs.add(dto.getTagNumber());
				sqlArgs.add(dto.getSQLToDate());
				sqlArgs.add(dto.getSQLToDate());
				sqlArgs.add(dto.getFromDate());
				sqlArgs.add(dto.getFromDate());
				sqlArgs.add(dto.getResponsibilityUser());
				sqlArgs.add(dto.getResponsibilityUser());
				
				 if("".equals(dto.getResponsibilityDept())){
				 }else{
					 sqlArgs.add(dto.getResponsibilityDept());
				 }
				
				sqlArgs.add(dto.getObjectName());
				sqlArgs.add(dto.getObjectName());
				String costCenter = dto.getCostCenter().equals("") ? "" :
									("%" + dto.getCostCenter() + "%");
				sqlArgs.add(costCenter);
				sqlArgs.add(costCenter);
				String workorderObjectCode = "";
				if (dto.getWorkorderObjectCode().equals("")) {
					if (!dto.getAreaCode().equals("")) {
						workorderObjectCode = dto.getAreaCode() + "%";
					}
				} else {
					if (dto.getAreaCode().equals("")) {
						workorderObjectCode = "%" + dto.getWorkorderObjectCode() + "%";
					} else {
						workorderObjectCode = dto.getAreaCode() + "." + dto.getWorkorderObjectCode() + "%";
					}

				}
				sqlArgs.add(workorderObjectCode);
				sqlArgs.add(workorderObjectCode);
				sqlArgs.add(dto.getProjectName());
				sqlArgs.add(dto.getProjectName());
				sqlArgs.add(dto.getProjectNumber());
				sqlArgs.add(dto.getProjectNumber());
				sqlArgs.add(dto.getStartDate());
				sqlArgs.add(dto.getStartDate());
				sqlArgs.add(dto.getStartDate());
				sqlArgs.add(dto.getSQLEndDate());
				sqlArgs.add(dto.getSQLEndDate());
				sqlArgs.add(dto.getSQLEndDate());
				if ((!dto.getTagNumber().equals("")) && (!dto.getToTagNumber().equals(""))) {
					sqlArgs.add(dto.getTagNumber());
					sqlArgs.add(dto.getToTagNumber());
				} else {
					sqlArgs.add(dto.getTagNumber());
					sqlArgs.add(dto.getTagNumber());
				}
				sqlArgs.add(userAccount.getOrganizationId());
			}
		} catch (CalendarException e) {
			e.printLog();
			throw new SQLModelException(e);
		}
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
