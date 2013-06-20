package com.sino.td.synAssets.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.synchronize.dto.EamSyschronizeDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;

/**
 *
 * <p>Title: SinoEAM</p>
 * <p>Description: TD资产变动直接同步</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2007~2009</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class TdAssetsChangeSynModel extends AMSSQLProducer {

	/**
	 * 功能：TD地点同步 数据库SQL构造层构造函数
	 * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EtsItemMatchDTO 本次操作的数据
	 */
	public TdAssetsChangeSynModel(SfUserDTO userAccount, EamSyschronizeDTO dtoParameter) {
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
//		String sqlStr;
//		sqlStr = "SELECT" +
//				 "       EII.SYSTEMID,\n" +
//				 "       EFA.ASSET_ID,\n" +
//				 "       EFA.TAG_NUMBER,\n" +
//				 "       EII.BARCODE BARCODE,\n" +
//				 "       EFA.ASSET_NUMBER,\n" +
//				 "       EFA.ASSETS_LOCATION,\n" +
//				 "       EO.WORKORDER_OBJECT_LOCATION LOCATION_CODE,\n" +
//				 "       EFA.ASSETS_DESCRIPTION,\n" +
//				 "       ESI.ITEM_NAME,\n" +
//				 "       EFA.MODEL_NUMBER,\n" +
//				 "       ESI.ITEM_SPEC,\n" +
//				 "       EFA.ASSIGNED_TO_NAME OLD_ASSIGNED_TO_NAME,\n" +
//				 "       EII.RESPONSIBILITY_USER NEW_RESPONSIBILITY_USER,\n" +
//				 "       AME2.USER_NAME NEW_USER_NAME,\n" +
//				 "       EFA.ASSIGNED_TO_NUMBER OLD_ASSIGNED_TO_NUMBER,\n" +
//				 "       EII.ORGANIZATION_ID,\n" +
//				 "       EFA.DEPRECIATION_ACCOUNT,\n" +
//				 "       EII.RESPONSIBILITY_DEPT,\n" +
//				 "       EFA.ASSETS_DESCRIPTION OLD_ASSETS_DESCRIPTION,\n" +
//				 "       AME.EMPLOYEE_ID,\n" +
//				 "       EO.COST_CODE,\n" +
//				 "       EC.COUNTY_CODE_COA_CC,\n" +
//				 "       ESI.ITEM_CATEGORY," +
//				 "       EFA.DATE_PLACED_IN_SERVICE," +
//				 "       EFA.LIFE_IN_YEARS,\n" +
//				 "       EFA.COST,  " +
//				 "       AMS_PUB_PKG.GET_ORGNIZATION_NAME(EII.ORGANIZATION_ID) COMPANY_NAME,  " +
//				 "       AMS_PUB_PKG.GET_USER_NAME(EIMR.MATCH_USER_ID) MATCH_USER_NAME\n" +
//				 "  FROM ETS_SYSTEM_ITEM     ESI,\n" +
//				 "       ETS_FA_TD_ASSETS    EFA,\n" +
//				 "       ETS_OBJECT          EO,\n" +
//				 "       AMS_OBJECT_ADDRESS  AOA,\n" +
//				 "       ETS_ITEM_MATCH      EIM,\n" +
//				 "       ETS_ITEM_MATCH_REC  EIMR,\n" +
//				 "       ETS_ITEM_INFO       EII,\n" +
//				 "       ETS_COUNTY          EC,\n" +
//				 "       AMS_MIS_EMPLOYEE    AME,\n" +
//				 "       AMS_MIS_EMPLOYEE    AME2,\n" +
//				 "       ETS_PA_PROJECTS_ALL EPPA,\n" +
//				 "       AMS_MIS_DEPT        AMD\n" +
//				 " WHERE EII.SYSTEMID = EIM.SYSTEMID\n" +
//				 "   AND EIM.ASSET_ID = EFA.ASSET_ID\n" +
//				 "   AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
//				 "   AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
//				 "   AND EII.ORGANIZATION_ID = AOA.ORGANIZATION_ID\n" +
//				 "   AND EFA.ASSIGNED_TO_NUMBER *= AME.EMPLOYEE_NUMBER  \n" +
//				 "   AND EII.RESPONSIBILITY_USER *= AME2.EMPLOYEE_ID\n" +
//				 "   AND EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE\n" +
//				 "   AND EII.ORGANIZATION_ID = AOA.ORGANIZATION_ID\n" +
//				 "   AND ESI.ITEM_CODE = EII.ITEM_CODE\n" +
//				 "   AND EO.COUNTY_CODE *= EC.COUNTY_CODE\n" +
//				 "   AND AME.ENABLED = 'Y'\n" +
//				 "   AND EFA.COST <> 0 \n" +
//				 "   AND EIM.SYSTEMID = EIMR.SYSTEM_ID\n" + //匹配人
//				 "   AND EIMR.MATCH_USER_ID = CONVERT(INT, dbo.NVL(?, CONVERT(VARCHAR, EIMR.MATCH_USER_ID)))\n" + //匹配人
//				 "   AND (EO.OBJECT_CATEGORY<70 OR EO.OBJECT_CATEGORY=80) \n" + //只有在实际地点才能同步
//				 "   AND EIM.ASSET_ID = EIMR.ASSET_ID\n" +
//				 "   AND (dbo.NVL(EFA.ASSIGNED_TO_NAME, 'A') <> dbo.NVL(AME2.USER_NAME, 'A') OR\n" +
//				 "       EFA.ASSETS_DESCRIPTION <> ESI.ITEM_NAME)\n" +
//				 "   AND NOT EXISTS\n" +
//				 " (SELECT NULL\n" +
//				 "          FROM ETS_MISFA_UPDATE_LOG EMUL\n" +
//				 "         WHERE EMUL.ASSET_ID = EFA.ASSET_ID\n" +
//				 "           AND (EMUL.TRANS_STATUS = 0 OR\n" +
//				 "               (EMUL.TRANS_STATUS = 1 AND\n" +
//				 "               TRUNC(EMUL.CREATION_DATE) = TRUNC(GETDATE() ))))\n" +
//				 "   AND EII.FINANCE_PROP = 'ASSETS'\n" +
//				 "   AND EII.ORGANIZATION_ID = ?\n" +
//				 "   AND EII.PROJECTID *= EPPA.PROJECT_ID\n" +
//				 "   AND EII.BARCODE = EFA.TAG_NUMBER\n" +
//				 "   AND ESI.ITEM_NAME = EFA.ASSETS_DESCRIPTION\n" +
//				 "   AND dbo.NVL(ESI.ITEM_SPEC, 'A') = dbo.NVL(EFA.MODEL_NUMBER, 'A')\n" +
//				 "   AND (EFA.ASSETS_LOCATION_CODE<>EO.WORKORDER_OBJECT_CODE OR\n" +
//				 "       dbo.NVL(EFA.ASSIGNED_TO_NUMBER, 'A') <> dbo.NVL(AME2.EMPLOYEE_NUMBER, 'A'))\n" +
//				 "   AND (" + SyBaseSQLUtil.isNull() + " OR AMD.DEPT_CODE = ?)\n" +
//				 "   AND ((EO.WORKORDER_OBJECT_CODE LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_CODE)) OR\n" +
//				 "       (EO.WORKORDER_OBJECT_LOCATION LIKE\n" +
//				 "       dbo.NVL(?, EO.WORKORDER_OBJECT_LOCATION)))\n" +
//				 "   AND EII.BARCODE LIKE dbo.NVL(?, EII.BARCODE)\n" +
//				 "   AND ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME)\n" +
//				 "   AND (" + SyBaseSQLUtil.isNull() + " OR EPPA.NAME LIKE dbo.NVL(?, EPPA.NAME))\n" +
//				 "   AND (" + SyBaseSQLUtil.isNull() + " OR EPPA.SEGMENT1 LIKE dbo.NVL(?, EPPA.SEGMENT1))";
//		if (dto.getNetManger().equals("NET")) {
//			sqlStr += "   AND ESI.ITEM_CATEGORY IN ('NETOPT', 'NETMGR', 'TRANS', 'BSC', 'EXCHG',\n" +
//				"        'ELEC', 'DATA', 'BTS', 'CABEL')\n";
//		} else if (dto.getNetManger().equals("MANG")) {
//			sqlStr += "   AND ESI.ITEM_CATEGORY IN ('LAND', 'HOUSE', 'OTHERS')\n";
//		}
//		sqlArgs.add(dto.getMatchUserId());
//		sqlArgs.add(dto.getOrganizationId());
//		sqlArgs.add(dto.getDeptCode());
//		sqlArgs.add(dto.getDeptCode());
//		sqlArgs.add(dto.getNewAssetsLocation());
//		sqlArgs.add(dto.getNewAssetsLocation());
//		sqlArgs.add(dto.getNewBarcode());
//		sqlArgs.add(dto.getNameTo());
//		sqlArgs.add(dto.getProjectName());
//		sqlArgs.add(dto.getProjectName());
//		sqlArgs.add(dto.getProjectNumber());
//		sqlArgs.add(dto.getProjectNumber());
       String sqlStr = "SELECT" +
				 "       EII.SYSTEMID,\n" +
				 "       EFAT.ASSET_ID,\n" +
				 "       EFAT.TAG_NUMBER,\n" +
				 "       EII.BARCODE BARCODE,\n" +
                 "       AMS_PUB_PKG.GET_ORGNIZATION_NAME(EII.ORGANIZATION_ID) COMPANY_NAME," +
                 "       EFAT.ASSETS_DESCRIPTION,\n" +
				 "       ESI.ITEM_NAME,\n" +
                 "       EFAT.MODEL_NUMBER,\n" +
				 "       ESI.ITEM_SPEC,\n" +
                 "       EFAT.ASSETS_LOCATION,\n" +
				 "       EO.WORKORDER_OBJECT_LOCATION LOCATION_CODE,\n" +
                 "       EFAT.ASSIGNED_TO_NAME OLD_ASSIGNED_TO_NAME,\n" +
				 "       EII.RESPONSIBILITY_USER NEW_RESPONSIBILITY_USER,\n" +
				 "       AME2.USER_NAME NEW_USER_NAME,\n" +
                 "       EFAT.DATE_PLACED_IN_SERVICE," +
                 "       EFAT.COST,  " +
				 "       AMS_PUB_PKG.GET_USER_NAME(EIMTR.MATCH_USER_ID) MATCH_USER_NAME,\n" +
				 "       EFAT.ASSET_NUMBER,\n" +
				 "       EFAT.ASSIGNED_TO_NUMBER OLD_ASSIGNED_TO_NUMBER,\n" +
                 "       EFAT.MANUFACTURER_NAME MIS_MANUFACTURER,"  +
                 "       AM.MANUFACTURER_NAME EAM_MANUFACTURER," +
				 "       EII.ORGANIZATION_ID,\n" +
				 "       EFAT.DEPRECIATION_ACCOUNT,\n" +
				 "       EII.RESPONSIBILITY_DEPT,\n" +
				 "       EFAT.ASSETS_DESCRIPTION OLD_ASSETS_DESCRIPTION,\n" +
				 "       EO.COST_CODE,\n" +
//				 "       EC.COUNTY_CODE_COA_CC,\n" +
				 "       ESI.ITEM_CATEGORY," +
                 "       AME.EMPLOYEE_ID,\n" +
				 "       EFAT.LIFE_IN_YEARS\n" +
				 "  FROM ETS_SYSTEM_ITEM        ESI,\n" +
				 "       ETS_FA_ASSETS_TD       EFAT,\n" +
				 "       ETS_OBJECT             EO,\n" +
				 "       AMS_OBJECT_ADDRESS     AOA,\n" +
				 "       ETS_ITEM_MATCH_TD      EIMT,\n" +
				 "       ETS_ITEM_MATCH_TD_REC  EIMTR,\n" +
				 "       ETS_ITEM_INFO          EII,\n" +
//				 "       ETS_COUNTY             EC,\n" +
                 "       AMS_MIS_EMPLOYEE       AME,\n" +
                 "       ETS_OU_CITY_MAP        EOCM,\n"    +
				 "       AMS_MIS_EMPLOYEE       AME2,\n" +
				 "       ETS_PA_PROJECTS_ALL    EPPA,\n" +
				 "       AMS_MIS_DEPT           AMD,\n" +
                 "       AMS_MANUFACTURER       AM\n"   +
				 " WHERE EII.SYSTEMID = EIMT.SYSTEMID\n" +
				 "   AND EFAT.ASSET_ID = EIMT.ASSET_ID\n" +
				 "   AND EIMT.SYSTEMID = EIMTR.SYSTEM_ID\n" +
				 "   AND EIMT.ASSET_ID = EIMTR.ASSET_ID\n" +
				 "   AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
				 "   AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
				 "   AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE\n" +
                 "   AND EII.RESPONSIBILITY_USER *= AME2.EMPLOYEE_ID\n" +
				 "   AND ESI.ITEM_CODE = EII.ITEM_CODE\n" +
                 "   AND EII.MANUFACTURER_ID *= AM.MANUFACTURER_ID\n " +
                 "   AND EFAT.ASSIGNED_TO_NUMBER *= AME.EMPLOYEE_NUMBER  \n" +
                 "   AND EII.PROJECTID *= EPPA.PROJECT_ID\n"  +
                 "   AND EO.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
// 2010-4-7 SUHP
//               "   AND EC.COMPANY_CODE = EOCM.COMPANY_CODE \n "+
//				 "   AND EO.COUNTY_CODE *= EC.COUNTY_CODE\n" +
//				 "   AND EII.ORGANIZATION_ID = AOA.ORGANIZATION_ID\n" +
                 "   AND AME.ENABLED = 'Y'\n" +
				 "   AND EFAT.COST <> 0 \n" +
				 "   AND EIMTR.MATCH_USER_ID = CONVERT(INT, dbo.NVL(?, CONVERT(VARCHAR, EIMTR.MATCH_USER_ID)))\n" +
				 "   AND (EO.OBJECT_CATEGORY<70 OR EO.OBJECT_CATEGORY=80) \n" +
                 "   AND EIMTR.NEW_FINANCE_PROP = 'TD_ASSETS'"    +
				 "   AND NOT EXISTS\n" +
				 " (SELECT NULL\n" +
				 "          FROM ETS_MISFA_UPDATE_LOG EMUL\n" +
				 "         WHERE EMUL.ASSET_ID = EFAT.ASSET_ID\n" +
				 "           AND (EMUL.TRANS_STATUS = 0 OR\n" +
				 "               (EMUL.TRANS_STATUS = 1 AND\n" +
				 "               CONVAERT(VARCHAR, EMUL.CREATION_DATE, 110) =\n" +
				 "                CONVAERT(VARCHAR,GETDATE() , 110))))\n" +
				 "   AND EII.FINANCE_PROP = 'TD_ASSETS'\n" +
				 "   AND EII.ORGANIZATION_ID = ?\n" +
				 "   AND (" + SyBaseSQLUtil.isNull() + " OR AMD.DEPT_CODE = ?)\n" +
				 "   AND ((EO.WORKORDER_OBJECT_CODE LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_CODE)) OR\n" +
				 "       (EO.WORKORDER_OBJECT_LOCATION LIKE\n" +
				 "       dbo.NVL(?, EO.WORKORDER_OBJECT_LOCATION)))\n" +
				 "   AND EII.BARCODE LIKE dbo.NVL(?, EII.BARCODE)\n" +
				 "   AND ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME)\n" +
				"   AND (ESI.ITEM_NAME <> EFAT.ASSETS_DESCRIPTION OR\n" +
				"        dbo.NVL(ESI.ITEM_SPEC, 'A') <> dbo.NVL(EFAT.MODEL_NUMBER, 'A') OR\n" +
                "        dbo.NVL(AM.MANUFACTURER_NAME, 'A') <> dbo.NVL(EFAT.MANUFACTURER_NAME, 'A')"+
               ")\n"+
                "   AND (" + SyBaseSQLUtil.isNull() + " OR EPPA.NAME LIKE dbo.NVL(?, EPPA.NAME))\n" +
                "   AND (" + SyBaseSQLUtil.isNull() + " OR EPPA.SEGMENT1 LIKE dbo.NVL(?, EPPA.SEGMENT1))";
		if (dto.getNetManger().equals("NET")) {
			sqlStr += "   AND ESI.ITEM_CATEGORY IN ('NETOPT', 'NETMGR', 'TRANS', 'BSC', 'EXCHG',\n" +
				"        'ELEC', 'DATA', 'BTS', 'CABEL')\n";
		} else if (dto.getNetManger().equals("MANG")) {
			sqlStr += "   AND ESI.ITEM_CATEGORY IN ('LAND', 'HOUSE', 'OTHERS')\n";
		}
		sqlArgs.add(dto.getMatchUserId());
		sqlArgs.add(dto.getOrganizationId());
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
