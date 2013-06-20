package com.sino.soa.mis.srv.assetsinfoupdate.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.soa.mis.srv.assetsinfoupdate.dto.SrvEamSyschronizeDTO;

/**
* User: wangzp
* Date: 2011-09-26
* Function:资产基本信息修改_接口实现类
 */
public class AssetsUpdateModel extends AMSSQLProducer {

/**
	 * 功能：资产基本信息修改 数据库SQL构造层构造函数
	 * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EtsItemMatchDTO 本次操作的数据
	 */
	public AssetsUpdateModel(SfUserDTO userAccount, SrvEamSyschronizeDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

/**
	 * 功能：框架自动生成LOCUS页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 */
	public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SrvEamSyschronizeDTO dto = (SrvEamSyschronizeDTO) dtoParameter;
		String sqlStr;
		sqlStr = "SELECT" +
				 "       EII.SYSTEMID,\n" +
				 "       EFA.ASSET_ID,\n" +
				 "       EFA.TAG_NUMBER,\n" +
				 "       EII.BARCODE,\n" +
				 "       EFA.ASSET_NUMBER,\n" +
				 "       EFA.ASSETS_LOCATION,\n" +
				 "       EO.WORKORDER_OBJECT_LOCATION LOCATION_CODE,\n" +
				 "       EFA.ASSETS_DESCRIPTION,\n" +
				 "       ESI.ITEM_NAME,\n" +
				 "       EFA.MODEL_NUMBER,\n" +
				 "       EFA.MANUFACTURER_NAME,\n" +
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
//				 "       AM.MANUFACTURER_NAME  EAMMANUFNAME,\n" +
				 "		 dbo.EAM_GET_MANUFACTURER_NAME(EII.MANUFACTURER_ID) EAMMANUFNAME," +
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
				 "       AMS_MIS_EMPLOYEE    AME,\n" +
				 "       AMS_MIS_EMPLOYEE    AME2,\n" +
				 "       ETS_PA_PROJECTS_ALL EPPA,\n" +
//				 "       AMS_MANUFACTURER    AM,\n" +
				 "       AMS_MIS_DEPT        AMD\n" +
				 " WHERE EII.SYSTEMID = EIM.SYSTEMID\n" +
				 "   AND EIM.ASSET_ID = EFA.ASSET_ID\n" +
				 "   AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
				 "   AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
				 "   AND EII.ORGANIZATION_ID = AOA.ORGANIZATION_ID\n" +
				 "   AND EFA.ASSIGNED_TO_NUMBER  *= AME.EMPLOYEE_NUMBER\n" +
				 "   AND EII.RESPONSIBILITY_USER *= AME2.EMPLOYEE_ID\n" +
				 "   AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE\n" +
				 "   AND EII.ORGANIZATION_ID = AOA.ORGANIZATION_ID\n" +
				 "   AND (? = -1 OR EII.ORGANIZATION_ID = ?)\n" +
				 "   AND (EFA.MODEL_NUMBER <> ESI.ITEM_SPEC OR EFA.MANUFACTURER_NAME <> dbo.EAM_GET_MANUFACTURER_NAME(EII.MANUFACTURER_ID) OR EFA.ASSETS_DESCRIPTION <> ESI.ITEM_NAME)\n" +		 
//				 "   AND AM.MANUFACTURER_ID = EII.MANUFACTURER_ID\n" +
				 "   AND ESI.ITEM_CODE = EII.ITEM_CODE\n" +
				 "   AND AME.ENABLED = 'Y'\n" +
				 "   AND EFA.COST <> 0 \n" +
				 "   AND EIM.SYSTEMID = EIMR.SYSTEM_ID\n" + //匹配人
				 "   AND (? = -1 OR EIMR.MATCH_USER_ID = ?)" + //匹配人
				 "   AND (EO.OBJECT_CATEGORY<'70' OR EO.OBJECT_CATEGORY='80') \n" + //只有在实际地点才能同步
				 "   AND EIM.ASSET_ID = EIMR.ASSET_ID\n" +
				 "   AND NOT EXISTS\n" +
				 "   (SELECT NULL\n" +
				 "          FROM ETS_MISFA_UPDATE_LOG EMUL\n" +
				 "         WHERE EMUL.ASSET_ID = EFA.ASSET_ID\n" +
				 "           AND EMUL.UPDATE_TYPE = 'ASSETSINFO'\n" +
				 "           AND (EMUL.TRANS_STATUS = 0 OR\n" +
				 "               (EMUL.TRANS_STATUS = 1 AND\n" +
				 "                EMUL.CREATION_DATE =\n" +
				 "                GETDATE() )))\n" +
				 "   AND EII.FINANCE_PROP = 'ASSETS'\n" +
				 "   AND EII.ORGANIZATION_ID = ?\n" +
				 "   AND (? = '' OR AMD.DEPT_CODE = ?)\n" +
				 "   AND ((EO.WORKORDER_OBJECT_CODE LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_CODE)) OR\n" +
				 "       (EO.WORKORDER_OBJECT_LOCATION LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_LOCATION))" +
				 "   )\n " +
				 "   AND EII.BARCODE LIKE dbo.NVL(?, EII.BARCODE)\n" +
				 "   AND ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME)\n" +
				 "   AND EII.PROJECTID *= EPPA.PROJECT_ID \n";
//			sqlStr +=
//				"   AND (ESI.ITEM_NAME <> EFA.ASSETS_DESCRIPTION OR\n" +
//				"       NVL(ESI.ITEM_SPEC, 'A') <> NVL(EFA.MODEL_NUMBER, 'A') OR\n" +
//				"       EII.BARCODE <> EFA.TAG_NUMBER OR\n" +
//				"       NVL(EFA.ASSETS_LOCATION, 'A') <> NVL(EO.WORKORDER_OBJECT_NAME, 'A') OR\n" +
//				"       NVL(EFA.ASSIGNED_TO_NAME, 'A') <> NVL(AME2.USER_NAME, 'A'))\n";
		sqlStr +=
			"   AND (? = '' OR EPPA.NAME LIKE dbo.NVL(?, EPPA.NAME))\n" +
			"   AND (? = '' OR EPPA.SEGMENT1 LIKE dbo.NVL(?, EPPA.SEGMENT1))";
//		if (dto.getNetManger().equals("NET")) {
//			sqlStr += "   AND ESI.ITEM_CATEGORY IN ('NETOPT', 'NETMGR', 'TRANS', 'BSC', 'EXCHG',\n" +
//				"        'ELEC', 'DATA', 'BTS', 'CABEL')\n";
//		} else if (dto.getNetManger().equals("MANG")) {
//			sqlStr += "   AND ESI.ITEM_CATEGORY IN ('LAND', 'HOUSE', 'OTHERS')\n";
//		}
		sqlArgs.add(dto.getOrganizationId());
		sqlArgs.add(dto.getOrganizationId());
		sqlArgs.add(dto.getMatchUserId());
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
