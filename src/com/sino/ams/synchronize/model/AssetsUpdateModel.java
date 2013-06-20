package com.sino.ams.synchronize.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.synchronize.dto.EamSyschronizeDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;

/**
 * User: Administrator
 * Date: 2008-3-14 10:01:10
 * Function:
 */
public class AssetsUpdateModel extends AMSSQLProducer {

/**
	 * 功能：eAM新增地点同步 数据库SQL构造层构造函数
	 * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EtsItemMatchDTO 本次操作的数据
	 */
	public AssetsUpdateModel(SfUserDTO userAccount, EamSyschronizeDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	
	/**
	 * 功能：框架自动生成LOCUS页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 */
	public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EamSyschronizeDTO dto = (EamSyschronizeDTO) dtoParameter;
		String provinceCode = servletConfig.getProvinceCode();
	 
		StringBuffer sb = new StringBuffer();
		 
		 
	    sb.append( " SELECT \n " );
		sb.append( "        EII.SYSTEMID,\n" );
		sb.append( "        EFA.ASSET_ID,\n " );
		sb.append( "        EFA.TAG_NUMBER,\n " );
		sb.append( "        EII.BARCODE BARCODE,\n " );
		sb.append( "        EFA.ASSET_NUMBER,\n " );
		sb.append( "        EFA.ASSETS_LOCATION,\n " );
		sb.append( "        EO.WORKORDER_OBJECT_LOCATION LOCATION_CODE,\n " );
		sb.append( "        EFA.ASSETS_DESCRIPTION,\n " );
		sb.append( "        ESI.ITEM_NAME,\n " );
		sb.append( "        EFA.MODEL_NUMBER,\n " );
		sb.append( "        ESI.ITEM_SPEC,\n " );
		sb.append( "        EFA.ASSIGNED_TO_NAME OLD_ASSIGNED_TO_NAME,\n " );
		sb.append( "        EII.RESPONSIBILITY_USER NEW_RESPONSIBILITY_USER,\n " );
		sb.append( "        AME2.USER_NAME NEW_USER_NAME,\n " );
		sb.append( "        EFA.ASSIGNED_TO_NUMBER OLD_ASSIGNED_TO_NUMBER,\n " );
		sb.append( "        EII.ORGANIZATION_ID,\n " );
		sb.append( "        EFA.DEPRECIATION_ACCOUNT,\n " );
		sb.append( "        EII.RESPONSIBILITY_DEPT,\n " );
		sb.append( "        EFA.ASSETS_DESCRIPTION OLD_ASSETS_DESCRIPTION,\n " );
		sb.append( "        AME.EMPLOYEE_ID,\n " );
		sb.append( "        EO.COST_CODE,\n " );
		sb.append( "        EC.COUNTY_CODE_COA_CC,\n " );
		sb.append( "        ESI.ITEM_CATEGORY, " );
		sb.append( "        EFA.DATE_PLACED_IN_SERVICE, " );
		sb.append( "        EFA.LIFE_IN_YEARS,\n " );
		sb.append( "        EFA.COST,   " );
		sb.append( "        dbo.APP_GET_ORGNIZATION_NAME(EII.ORGANIZATION_ID) COMPANY_NAME,   " );
		sb.append( "        dbo.APP_GET_USER_NAME(EIMR.MATCH_USER_ID) MATCH_USER_NAME\n " );
		sb.append( "   FROM ETS_SYSTEM_ITEM     ESI,\n " );
		sb.append( "        ETS_FA_ASSETS       EFA,\n " );
		sb.append( "        ETS_OBJECT          EO,\n " );
		sb.append( "        AMS_OBJECT_ADDRESS  AOA,\n " );
		sb.append( "        ETS_ITEM_MATCH      EIM,\n " );
		sb.append( "        ETS_ITEM_MATCH_REC  EIMR,\n " );
		sb.append( "        ETS_ITEM_INFO       EII,\n " );
		sb.append( "        ETS_COUNTY          EC,\n " );

		sb.append( "        ETS_OU_CITY_MAP    EOCM,\n" );

		sb.append( "        AMS_MIS_EMPLOYEE    AME,\n " );
		sb.append( "        AMS_MIS_EMPLOYEE    AME2,\n " );
		sb.append( "        ETS_PA_PROJECTS_ALL EPPA,\n " );
		sb.append( "        AMS_MIS_DEPT        AMD\n " );
		sb.append( "  WHERE EII.SYSTEMID = EIM.SYSTEMID\n " );
		sb.append( "    AND EIM.ASSET_ID = EFA.ASSET_ID\n " );
		sb.append( "    AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n " );
		sb.append( "    AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n " );
		sb.append( "    AND EII.ORGANIZATION_ID = AOA.ORGANIZATION_ID\n " );
		sb.append( "    AND EFA.ASSIGNED_TO_NUMBER *= AME.EMPLOYEE_NUMBER\n " );
		sb.append( "    AND EII.RESPONSIBILITY_USER *= AME2.EMPLOYEE_ID\n " );
		sb.append( "    AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE\n " );
		sb.append( "    AND EII.ORGANIZATION_ID = AOA.ORGANIZATION_ID\n " );
		sb.append( "    AND ESI.ITEM_CODE = EII.ITEM_CODE\n " );
		sb.append( "    AND EO.COUNTY_CODE *= EC.COUNTY_CODE\n " );
		sb.append( "    AND EO.ORGANIZATION_ID = EOCM.ORGANIZATION_ID \n  " );
		sb.append( "    AND EOCM.COMPANY_CODE *= EC.COMPANY_CODE \n " );
//		sb.append( "    AND AME.ENABLED = 'Y'\n " );
		sb.append( "    AND EFA.COST <> 0 \n " );
		sb.append( "    AND EIM.SYSTEMID = EIMR.SYSTEM_ID\n " ); //匹配人 
		sb.append( "    AND ( ? = '' OR CONVERT( VARCHAR , EIMR.MATCH_USER_ID ) LIKE ? )\n " ); //匹配人
		sb.append( "    AND ( CONVERT( INT , EO.OBJECT_CATEGORY ) <70 OR CONVERT( INT , EO.OBJECT_CATEGORY )  =80) \n " ); //只有在实际地点才能同步
		sb.append( "    AND EIM.ASSET_ID = EIMR.ASSET_ID\n " );
		sb.append( "    AND NOT EXISTS\n " );
		sb.append( "  (SELECT NULL\n " );
		sb.append( "           FROM ETS_MISFA_UPDATE_LOG EMUL\n " );
		sb.append( "          WHERE EMUL.ASSET_ID = EFA.ASSET_ID\n " );
		sb.append( "            AND (EMUL.TRANS_STATUS = 0 OR\n " );
		sb.append( "                (EMUL.TRANS_STATUS = 1 AND\n " );
		sb.append( "        		 EMUL.CREATION_DATE = GETDATE() )))   " );
		sb.append( "    AND EII.FINANCE_PROP = 'ASSETS'\n " );
		sb.append( "    AND EII.ORGANIZATION_ID = ?\n " );
		sb.append( "    AND ( ? = ''  OR AMD.DEPT_CODE = ?)\n " );
		sb.append( "    AND ( ? = '' OR EO.WORKORDER_OBJECT_CODE LIKE ? OR\n " );
		sb.append( "          EO.WORKORDER_OBJECT_LOCATION LIKE ? ) \n " ); 
		sb.append( "    AND (? = '' OR EII.BARCODE LIKE ? )\n " );
		sb.append( "    AND (? = '' OR ESI.ITEM_NAME LIKE ? )\n " );
		sb.append( "    AND EII.PROJECTID *= EPPA.PROJECT_ID\n" );
		
		//TODO SJ 
		if (provinceCode.equals(DictConstant.PROVINCE_CODE_JIN)) { //山西由省公司同步
			 sb.append( "       AND (ESI.ITEM_NAME <> EFA.ASSETS_DESCRIPTION OR\n " );
			 sb.append( "       dbo.NVL(ESI.ITEM_SPEC, 'A') <> dbo.NVL(EFA.MODEL_NUMBER, 'A') OR\n " );
			 sb.append( "       EII.BARCODE <> EFA.TAG_NUMBER)\n " );
		} else {
			 
			 sb.append( "   AND (ESI.ITEM_NAME <> EFA.ASSETS_DESCRIPTION OR\n " );
			 sb.append( "      dbo.NVL(ESI.ITEM_SPEC, 'A') <> dbo.NVL(EFA.MODEL_NUMBER, 'A') OR\n " );
			 sb.append( "       EII.BARCODE <> EFA.TAG_NUMBER OR\n " );
			 sb.append( "       dbo.NVL(EFA.ASSETS_LOCATION_CODE, 'A') <> dbo.NVL(EO.WORKORDER_OBJECT_CODE, 'A') \n " );
//				sb.append( "       OR dbo.NVL(EFA.ASSIGNED_TO_NUMBER, 'A') <> dbo.NVL(AME2.EMPLOYEE_NUMBER, 'A') +
			 sb.append( ")\n" );
		}
			sb.append( "   AND (  " + SyBaseSQLUtil.isNull() + "  OR EPPA.NAME LIKE ? ) \n " );
			sb.append( "   AND (  " + SyBaseSQLUtil.isNull() + "  OR EPPA.SEGMENT1 LIKE ? ) \n " );
		if (dto.getNetManger().equals("NET")) {
			sb.append( "   AND ESI.ITEM_CATEGORY IN ('NETOPT', 'NETMGR', 'TRANS', 'BSC', 'EXCHG',\n " );
			sb.append( "        'ELEC', 'DATA', 'BTS', 'CABEL')\n" );
		} else if (dto.getNetManger().equals("MANG")) {
			sb.append( "   AND ESI.ITEM_CATEGORY IN ('LAND', 'HOUSE', 'OTHERS')\n");
		}
		
		sqlArgs.add(dto.getMatchUserId());
		sqlArgs.add(dto.getMatchUserId());
		
		if (provinceCode.equals(DictConstant.PROVINCE_CODE_JIN)) { //山西由省公司同步
			sqlArgs.add(dto.getOrganizationId());
		} else { //其他省由各地市公司自行同步
			sqlArgs.add(userAccount.getOrganizationId());
		}
		sqlArgs.add(dto.getDeptCode());
		sqlArgs.add(dto.getDeptCode());
		
		sqlArgs.add(dto.getNewAssetsLocation());
		sqlArgs.add(dto.getNewAssetsLocation());
		sqlArgs.add(dto.getNewAssetsLocation());
		
		sqlArgs.add(dto.getNewBarcode());
		sqlArgs.add(dto.getNewBarcode());
		
		sqlArgs.add(dto.getNameTo());
		sqlArgs.add(dto.getNameTo());
		
		sqlArgs.add(dto.getProjectName());
		sqlArgs.add(dto.getProjectName());
		
		sqlArgs.add(dto.getProjectNumber());
		sqlArgs.add(dto.getProjectNumber());
		
		sqlModel.setSqlStr( sb.toString() );
		sqlModel.setArgs(sqlArgs);

		return sqlModel;
	}
	
	/**
	 * 功能：框架自动生成LOCUS页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 */
	public SQLModel getPageQueryModel_OLD() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EamSyschronizeDTO dto = (EamSyschronizeDTO) dtoParameter;
		String provinceCode = servletConfig.getProvinceCode();
		String sqlStr;
		StringBuffer sb = new StringBuffer();
		sqlStr = "SELECT" +
				 "       EII.SYSTEMID,\n" +
				 "       EFA.ASSET_ID,\n" +
				 "       EFA.TAG_NUMBER,\n" +
				 "       EII.BARCODE BARCODE,\n" +
				 "       EFA.ASSET_NUMBER,\n" +
				 "       EFA.ASSETS_LOCATION,\n" +
				 "       EO.WORKORDER_OBJECT_LOCATION LOCATION_CODE,\n" +
				 "       EFA.ASSETS_DESCRIPTION,\n" +
				 "       ESI.ITEM_NAME,\n" +
				 "       EFA.MODEL_NUMBER,\n" +
				 "       ESI.ITEM_SPEC,\n" +
				 "       EFA.ASSIGNED_TO_NAME OLD_ASSIGNED_TO_NAME,\n" +
				 "       EII.RESPONSIBILITY_USER NEW_RESPONSIBILITY_USER,\n" +
				 "       AME2.USER_NAME NEW_USER_NAME,\n" +
				 "       EFA.ASSIGNED_TO_NUMBER OLD_ASSIGNED_TO_NUMBER,\n" +
				 "       EII.ORGANIZATION_ID,\n" +
				 "       EFA.DEPRECIATION_ACCOUNT,\n" +
				 "       EII.RESPONSIBILITY_DEPT,\n" +
				 "       EFA.ASSETS_DESCRIPTION OLD_ASSETS_DESCRIPTION,\n" +
				 "       AME.EMPLOYEE_ID,\n" +
				 "       EO.COST_CODE,\n" +
				 "       EC.COUNTY_CODE_COA_CC,\n" +
				 "       ESI.ITEM_CATEGORY," +
				 "       EFA.DATE_PLACED_IN_SERVICE," +
				 "       EFA.LIFE_IN_YEARS,\n" +
				 "       EFA.COST,  " +
				 "       dbo.APP_GET_ORGNIZATION_NAME(EII.ORGANIZATION_ID) COMPANY_NAME,  " +
				 "       dbo.APP_GET_USER_NAME(EIMR.MATCH_USER_ID) MATCH_USER_NAME\n" +
				 "  FROM ETS_SYSTEM_ITEM     ESI,\n" +
				 "       ETS_FA_ASSETS       EFA,\n" +
				 "       ETS_OBJECT          EO,\n" +
				 "       AMS_OBJECT_ADDRESS  AOA,\n" +
				 "       ETS_ITEM_MATCH      EIM,\n" +
				 "       ETS_ITEM_MATCH_REC  EIMR,\n" +
				 "       ETS_ITEM_INFO       EII,\n" +
				 "       ETS_COUNTY          EC,\n" +

                 "       ETS_OU_CITY_MAP    EOCM,\n"    +

				 "       AMS_MIS_EMPLOYEE    AME,\n" +
				 "       AMS_MIS_EMPLOYEE    AME2,\n" +
				 "       ETS_PA_PROJECTS_ALL EPPA,\n" +
				 "       AMS_MIS_DEPT        AMD\n" +
				 " WHERE EII.SYSTEMID = EIM.SYSTEMID\n" +
				 "   AND EIM.ASSET_ID = EFA.ASSET_ID\n" +
				 "   AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
				 "   AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
				 "   AND EII.ORGANIZATION_ID = AOA.ORGANIZATION_ID\n" +
				 "   AND EFA.ASSIGNED_TO_NUMBER *= AME.EMPLOYEE_NUMBER\n" +
				 "   AND EII.RESPONSIBILITY_USER *= AME2.EMPLOYEE_ID\n" +
				 "   AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE\n" +
				 "   AND EII.ORGANIZATION_ID = AOA.ORGANIZATION_ID\n" +
				 "   AND ESI.ITEM_CODE = EII.ITEM_CODE\n" +
				 "   AND EO.COUNTY_CODE *= EC.COUNTY_CODE\n" +
                 "   AND EO.ORGANIZATION_ID = EOCM.ORGANIZATION_ID \n " +
                 "   AND EC.COMPANY_CODE = EOCM.COMPANY_CODE \n "+
//				 "   AND AME.ENABLED = 'Y'\n" +
				 "   AND EFA.COST <> 0 \n" +
				 "   AND EIM.SYSTEMID = EIMR.SYSTEM_ID\n" + //匹配人
				 "   AND CONVERT( VARCHAR , EIMR.MATCH_USER_ID ) = dbo.NVL(?, CONVERT( VARCHAR , EIMR.MATCH_USER_ID ) )\n" + //匹配人
				 "   AND ( CONVERT( INT , EO.OBJECT_CATEGORY ) <70 OR CONVERT( INT , EO.OBJECT_CATEGORY )  =80) \n" + //只有在实际地点才能同步
				 "   AND EIM.ASSET_ID = EIMR.ASSET_ID\n" +
				 "   AND NOT EXISTS\n" +
				 " (SELECT NULL\n" +
				 "          FROM ETS_MISFA_UPDATE_LOG EMUL\n" +
				 "         WHERE EMUL.ASSET_ID = EFA.ASSET_ID\n" +
				 "           AND (EMUL.TRANS_STATUS = 0 OR\n" +
				 "               (EMUL.TRANS_STATUS = 1 AND\n" +
				 "       		 EMUL.CREATION_DATE = GETDATE() )))  " +
				 "   AND EII.FINANCE_PROP = 'ASSETS'\n" +
				 "   AND EII.ORGANIZATION_ID = ?\n" +
				 "   AND ( " + SyBaseSQLUtil.isNull() + "  OR AMD.DEPT_CODE = ?)\n" +
				 "   AND ((EO.WORKORDER_OBJECT_CODE LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_CODE)) OR\n" +
				 "       (EO.WORKORDER_OBJECT_LOCATION LIKE\n" +
				 "       dbo.NVL(?, EO.WORKORDER_OBJECT_LOCATION)))\n" +
				 "   AND EII.BARCODE LIKE dbo.NVL(?, EII.BARCODE)\n" +
				 "   AND ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME)\n" +
				 "   AND EII.PROJECTID *= EPPA.PROJECT_ID\n";
		
		//TODO SJ 
		if (provinceCode.equals(DictConstant.PROVINCE_CODE_JIN)) { //山西由省公司同步
			sqlStr +=
				"   AND (ESI.ITEM_NAME <> EFA.ASSETS_DESCRIPTION OR\n" +
				"       dbo.NVL(ESI.ITEM_SPEC, 'A') <> dbo.NVL(EFA.MODEL_NUMBER, 'A') OR\n" +
				"       EII.BARCODE <> EFA.TAG_NUMBER)\n";
		} else {
			sqlStr +=
				"   AND (ESI.ITEM_NAME <> EFA.ASSETS_DESCRIPTION OR\n" +
				"       dbo.NVL(ESI.ITEM_SPEC, 'A') <> dbo.NVL(EFA.MODEL_NUMBER, 'A') OR\n" +
				"       EII.BARCODE <> EFA.TAG_NUMBER OR\n" +
				"       dbo.NVL(EFA.ASSETS_LOCATION_CODE, 'A') <> dbo.NVL(EO.WORKORDER_OBJECT_CODE, 'A') \n" +
//				"       OR dbo.NVL(EFA.ASSIGNED_TO_NUMBER, 'A') <> dbo.NVL(AME2.EMPLOYEE_NUMBER, 'A') +
		        ")\n";
		}
		sqlStr +=
			"   AND ( " + SyBaseSQLUtil.isNull() + "  OR EPPA.NAME LIKE dbo.NVL(?, EPPA.NAME))\n" +
			"   AND ( " + SyBaseSQLUtil.isNull() + "  OR EPPA.SEGMENT1 LIKE dbo.NVL(?, EPPA.SEGMENT1))";
		if (dto.getNetManger().equals("NET")) {
			sqlStr += "   AND ESI.ITEM_CATEGORY IN ('NETOPT', 'NETMGR', 'TRANS', 'BSC', 'EXCHG',\n" +
				"        'ELEC', 'DATA', 'BTS', 'CABEL')\n";
		} else if (dto.getNetManger().equals("MANG")) {
			sqlStr += "   AND ESI.ITEM_CATEGORY IN ('LAND', 'HOUSE', 'OTHERS')\n";
		}
		sqlArgs.add(dto.getMatchUserId());
		if (provinceCode.equals(DictConstant.PROVINCE_CODE_JIN)) { //山西由省公司同步
			sqlArgs.add(dto.getOrganizationId());
		} else { //其他省由各地市公司自行同步
			sqlArgs.add(userAccount.getOrganizationId());
		}
		sqlArgs.add(dto.getDeptCode());
		sqlArgs.add(dto.getDeptCode());
		sqlArgs.add(dto.getNewAssetsLocation());
		sqlArgs.add(dto.getNewAssetsLocation());
		sqlArgs.add(dto.getNewBarcode());
		sqlArgs.add(dto.getNameTo());
		sqlArgs.add(dto.getProjectName());
		sqlArgs.add(dto.getProjectName());
		sqlArgs.add(dto.getProjectNumber());
		sqlArgs.add(dto.getProjectNumber());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);

		return sqlModel;
	}
}
