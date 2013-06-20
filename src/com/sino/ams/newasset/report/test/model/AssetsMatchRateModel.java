package com.sino.ams.newasset.report.test.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.report.test.dto.SpecialAssetsReportDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2009-3-10
 * Time: 13:57:53
 * To change this template use File | Settings | File Templates.
 */
public class AssetsMatchRateModel extends AMSSQLProducer {

	public AssetsMatchRateModel(SfUserDTO userAccount, SpecialAssetsReportDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

/**
	 * 功能：年底盘点资产账实相符率（公司层面）
	 * @return SQLModel
	 * @throws com.sino.base.exception.SQLModelException
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
	    List sqlArgs = new ArrayList();
        try {
            SpecialAssetsReportDTO dto = (SpecialAssetsReportDTO) dtoParameter;
            String matchAssetsType = dto.getMatchAssetsType();
            String sqlStr = "";
            if (matchAssetsType.equals("MATCH_DEPT")) {
//                sqlStr = "SELECT TOTAL.COMPANY,\n" +
//                        "       TOTAL.DEPT_NAME,\n" +
//                        "       TOTAL.SUM_COUNT,\n" +
//                        "       TOTAL.SUM_COST,\n" +
//                        "       (DECODE(TRUNC(100 * TOTAL.SUM_COST / TOTAL2.SUM_COST2),\n" +
//                        "               0,\n" +
//                        "               STR_REPLACE(ROUND(100 * TOTAL.SUM_COST / TOTAL2.SUM_COST2, 3),\n" +
//                        "                       '.',\n" +
//                        "                       '0.'),\n" +
//                        "               TO_/CHAR(ROUND(100 * TOTAL.SUM_COST / TOTAL2.SUM_COST2, 3))) || '%') MONEY_RATE,\n" +
//              "       (DECODE(TRUNC(100 * TOTAL.SUM_COUNT / TOTAL3.SUM_UNITS),\n" +
//              "               0,\n" +
//              "               STR_REPLACE(ROUND(100 * TOTAL.SUM_COUNT / TOTAL3.SUM_UNITS, 3),\n" +
//              "                       '.',\n" +
//              "                       '0.'),\n" +
//              "               TO_/CHAR(ROUND(100 * TOTAL.SUM_COUNT / TOTAL3.SUM_UNITS, 3))) || '%') AMOUNT_RATE\n" +
//                        "FROM   (SELECT EOCM.COMPANY COMPANY,\n" +
//                        "               AMD.DEPT_NAME DEPT_NAME,\n" +
//                        "               COUNT(EFA.CURRENT_UNITS) SUM_COUNT,\n" +
//                        "               SUM(EFA.COST) SUM_COST\n" +
//                        "        FROM   ETS_FA_ASSETS      EFA,\n" +
//                        "               ETS_ITEM_INFO      EII,\n" +
//                        "               ETS_ITEM_MATCH     EIM,\n" +
//                        "               ETS_OU_CITY_MAP    EOCM,\n" +
//                        "               ETS_SYSTEM_ITEM    ESI,\n" +
//                        "               ETS_COUNTY         EC,\n" +
//                        "               AMS_MIS_EMPLOYEE   AME,\n" +
//                        "               ETS_OBJECT         EO,\n" +
//                        "               AMS_OBJECT_ADDRESS AOA,\n" +
//                        "               AMS_MIS_DEPT       AMD\n" +
//                        "        WHERE  EFA.ASSET_ID = EIM.ASSET_ID\n" +
//                        "               AND EII.SYSTEMID = EIM.SYSTEMID\n" +
//                        "               AND EFA.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
//                        "               AND EII.ITEM_CODE = ESI.ITEM_CODE\n" +
//                        "               AND EOCM.COMPANY_CODE = EC.COMPANY_CODE\n" +
////                        "               AND EC.COUNTY_CODE = EO.COUNTY_CODE"    +
//                        "               AND EII.RESPONSIBILITY_USER = AME.EMPLOYEE_ID\n" +
//                        "               AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
//                        "               AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
//                        "               AND EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE\n" +
//                        "               AND (EII.BARCODE = EFA.TAG_NUMBER AND\n" +
//                        "               ESI.ITEM_NAME = EFA.ASSETS_DESCRIPTION AND\n" +
//                        "               ESI.ITEM_SPEC = EFA.MODEL_NUMBER AND\n" +
//                        "               SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) = EC.COUNTY_CODE_MIS AND\n" +
//                        "               AME.USER_NAME = EFA.ASSIGNED_TO_NAME AND\n" +
//                        "               EO.WORKORDER_OBJECT_NAME = EFA.ASSETS_LOCATION)\n" +
//                        "               AND EIM.MATCH_DATE <= ISNULL(?, EIM.MATCH_DATE)\n" +
//                        "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.RESPONSIBILITY_DEPT LIKE ?)\n" +
//                        "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EOCM.ORGANIZATION_ID = ?)\n" +
//                        "        GROUP  BY EOCM.COMPANY,\n" +
//                        "                  AMD.DEPT_NAME) TOTAL,\n" +
//                        "       (SELECT SUM(EFA.COST) SUM_COST2\n" +
//                        "        FROM   ETS_FA_ASSETS EFA\n" +
//                        "        WHERE  ( " + SyBaseSQLUtil.isNull() + "  OR EFA.ORGANIZATION_ID = ?)) TOTAL2,\n" +
//                        "       (SELECT COUNT(EFA.CURRENT_UNITS) SUM_UNITS\n" +
//                        "        FROM   ETS_FA_ASSETS EFA\n" +
//                        "        WHERE  ( " + SyBaseSQLUtil.isNull() + "  OR EFA.ORGANIZATION_ID = ?)) TOTAL3\n" +
//                        "ORDER  BY TOTAL.COMPANY,\n" +
//                        "          TOTAL.DEPT_NAME";
                sqlStr = "SELECT TOTAL.COMPANY,\n" +
                        "       TOTAL.DEPT_NAME,\n" +
                        "       TOTAL2.SUM_UNITS,\n" +
                        "       TOTAL2.SUM_COST2,\n" +
                        "       TOTAL.SUM_COUNT,\n" +
                        "       TOTAL.SUM_COST,\n" +
                        "       (DECODE(TRUNC(100 * TOTAL.SUM_COST / TOTAL2.SUM_COST2),\n" +
                        "               0,\n" +
                        "               STR_REPLACE(ROUND(100 * TOTAL.SUM_COST / TOTAL2.SUM_COST2, 3),\n" +
                        "                       '.',\n" +
                        "                       '0.'),\n" +
                        "               STR(ROUND(100 * TOTAL.SUM_COST / TOTAL2.SUM_COST2, 3))) || '%') MONEY_RATE,\n" +
                        "       (DECODE(TRUNC(100 * TOTAL.SUM_COUNT / TOTAL2.SUM_UNITS),\n" +
                        "               0,\n" +
                        "               STR_REPLACE(ROUND(100 * TOTAL.SUM_COUNT / TOTAL2.SUM_UNITS, 3),\n" +
                        "                       '.',\n" +
                        "                       '0.'),\n" +
                        "               STR(ROUND(100 * TOTAL.SUM_COUNT / TOTAL2.SUM_UNITS, 3))) || '%') AMOUNT_RATE\n" +
                        "FROM   (SELECT EOCM.COMPANY COMPANY,\n" +
                        "               EOCM.ORGANIZATION_ID,\n" +
                        "               AMD.DEPT_NAME DEPT_NAME,\n" +
                        "               COUNT(EFA.CURRENT_UNITS) SUM_COUNT,\n" +
                        "               SUM(EFA.COST) SUM_COST\n" +
                        "        FROM   ETS_FA_ASSETS      EFA,\n" +
                        "               ETS_ITEM_INFO      EII,\n" +
                        "               ETS_ITEM_MATCH     EIM,\n" +
                        "               ETS_OU_CITY_MAP    EOCM,\n" +
                        "               AMS_MIS_DEPT       AMD\n" +
                        "        WHERE  EFA.ASSET_ID = EIM.ASSET_ID\n" +
                        "               AND EII.SYSTEMID = EIM.SYSTEMID\n" +
                        "               AND EFA.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
                        "               AND EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE              \n" +
                        "               AND EIM.MATCH_DATE <= ISNULL(?, EIM.MATCH_DATE)\n" +
                        "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.RESPONSIBILITY_DEPT LIKE ?)\n" +
                        "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EOCM.ORGANIZATION_ID = ?)\n" +
                        "        GROUP  BY EOCM.COMPANY,\n" +
                        "                  EOCM.ORGANIZATION_ID,\n" +
                        "                  AMD.DEPT_NAME) TOTAL,\n" +
                        "       (SELECT SUM(EFA.COST) SUM_COST2,\n" +
                        "               COUNT(EFA.CURRENT_UNITS) SUM_UNITS\n" +
                        "        FROM   ETS_FA_ASSETS EFA\n" +
                        "        WHERE  ( " + SyBaseSQLUtil.isNull() + "  OR EFA.ORGANIZATION_ID = ?)) TOTAL2       \n" +
                        "ORDER  BY TOTAL.ORGANIZATION_ID,\n" +
                        "          TOTAL.DEPT_NAME";
                sqlArgs.add(dto.getEndDate());
                sqlArgs.add(dto.getResponsibilityDept());
                sqlArgs.add(dto.getResponsibilityDept());
                sqlArgs.add(dto.getOrganizationId());
                sqlArgs.add(dto.getOrganizationId());
                sqlArgs.add(dto.getOrganizationId());
                sqlArgs.add(dto.getOrganizationId());
            } else {
//                sqlStr = "SELECT EOCM.COMPANY,\n" +
//                        "       TOTAL.SUM_COUNT,\n" +
//                        "       TOTAL.SUM_COST,\n" +
//                        "       (DECODE(TRUNC(100 * TOTAL.SUM_COST / TOTAL2.SUM_COST2),\n" +
//                        "               0,\n" +
//                        "               STR_REPLACE(ROUND(100 * TOTAL.SUM_COST / TOTAL2.SUM_COST2, 3),\n" +
//                        "                       '.',\n" +
//                        "                       '0.'),\n" +
//                        "               TO_zCHAR(ROUND(100 * TOTAL.SUM_COST / TOTAL2.SUM_COST2, 3))) || '%') MONEY_RATE,\n" +
//              "       (DECODE(TRUNC(100 * TOTAL.SUM_COUNT / TOTAL3.SUM_UNITS),\n" +
//              "               0,\n" +
//              "               STR_REPLACE(ROUND(100 * TOTAL.SUM_COUNT / TOTAL3.SUM_UNITS, 3),\n" +
//              "                       '.',\n" +
//              "                       '0.'),\n" +
//              "               TOz_CHAR(ROUND(100 * TOTAL.SUM_COUNT / TOTAL3.SUM_UNITS, 3))) || '%') AMOUNT_RATE\n" +
//                        "FROM   ETS_OU_CITY_MAP EOCM,\n" +
//                        "       (SELECT EFA.ORGANIZATION_ID,\n" +
//                        "               COUNT(EFA.CURRENT_UNITS) SUM_COUNT,\n" +
//                        "               SUM(EFA.COST) SUM_COST\n" +
//                        "        FROM   ETS_FA_ASSETS      EFA,\n" +
//                        "               ETS_ITEM_INFO      EII,\n" +
//                        "               ETS_ITEM_MATCH     EIM,\n" +
//                        "               ETS_SYSTEM_ITEM    ESI,\n" +
//                        "               ETS_COUNTY         EC,\n" +
//                        "               AMS_MIS_EMPLOYEE   AME,\n" +
//                        "               ETS_OBJECT         EO,\n" +
//                        "               AMS_OBJECT_ADDRESS AOA\n" +
//                        "        WHERE  EFA.ASSET_ID = EIM.ASSET_ID\n" +
//                        "               AND EII.SYSTEMID = EIM.SYSTEMID\n" +
//                        "               AND EII.ITEM_CODE = ESI.ITEM_CODE\n" +
//                        "               AND EII.RESPONSIBILITY_USER = AME.EMPLOYEE_ID\n" +
//                        "               AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
//                        "               AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
//                        "               AND (EII.BARCODE = EFA.TAG_NUMBER AND\n" +
//                        "               ESI.ITEM_NAME = EFA.ASSETS_DESCRIPTION AND\n" +
//                        "               ESI.ITEM_SPEC = EFA.MODEL_NUMBER AND\n" +
//                        "               SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) = EC.COUNTY_CODE_MIS AND\n" +
//                        "               AME.USER_NAME = EFA.ASSIGNED_TO_NAME AND\n" +
//                        "               EO.WORKORDER_OBJECT_CODE = EFA.ASSETS_LOCATION_CODE)\n" +
//                        "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.ORGANIZATION_ID = ?)\n" +
//                        "               AND EIM.MATCH_DATE <= ISNULL(?, EIM.MATCH_DATE)\n" +
//                        "        GROUP  BY EFA.ORGANIZATION_ID) TOTAL,\n" +
//                        "       (SELECT EFA.ORGANIZATION_ID,\n" +
//                        "               SUM(EFA.COST) SUM_COST2\n" +
//                        "        FROM   ETS_FA_ASSETS EFA\n" +
//                        "        WHERE  ( " + SyBaseSQLUtil.isNull() + "  OR EFA.ORGANIZATION_ID = ?)\n" +
//                        "        GROUP  BY EFA.ORGANIZATION_ID) TOTAL2,\n" +
//                        "       (SELECT EFA.ORGANIZATION_ID,\n" +
//                        "               COUNT(EFA.CURRENT_UNITS) SUM_UNITS\n" +
//                        "        FROM   ETS_FA_ASSETS EFA\n" +
//                        "        WHERE  ( " + SyBaseSQLUtil.isNull() + "  OR EFA.ORGANIZATION_ID = ?)\n" +
//                        "        GROUP  BY EFA.ORGANIZATION_ID) TOTAL3\n" +
//                        "WHERE   EOCM.ORGANIZATION_ID = TOTAL.ORGANIZATION_ID AND\n" +
//                        "  EOCM.ORGANIZATION_ID = TOTAL2.ORGANIZATION_ID AND\n" +
//                        "  EOCM.ORGANIZATION_ID = TOTAL3.ORGANIZATION_ID";
                sqlStr = "SELECT EOCM.COMPANY,\n" +
                        "       TOTAL2.SUM_UNITS,\n" +
                        "       TOTAL2.SUM_COST2,\n" +
                        //"       TOTAL.SUM_COUNT,\n" +
                        "       ROUND(TOTAL2.SUM_UNITS*0.8+TOTAL.SUM_COUNT*0.2) SUM_COUNT,\n" +
                        //"       TOTAL.SUM_COST,\n" +
                        "       ROUND(TOTAL2.SUM_COST2*0.8+TOTAL.SUM_COST*0.2,2) SUM_COST,\n" +
                        "       (DECODE(TRUNC(100 * ROUND(TOTAL2.SUM_COST2*0.8+TOTAL.SUM_COST*0.2,2) / TOTAL2.SUM_COST2),\n" +
                        "               0,\n" +
                        "               STR_REPLACE(ROUND(100 * ROUND(TOTAL2.SUM_COST2*0.8+TOTAL.SUM_COST*0.2,2) / TOTAL2.SUM_COST2, 3),\n" +
                        "                       '.',\n" +
                        "                       '0.'),\n" +
                        "               STR(ROUND(100 * ROUND(TOTAL2.SUM_COST2*0.8+TOTAL.SUM_COST*0.2,2) / TOTAL2.SUM_COST2, 3))) || '%') MONEY_RATE,\n" +
                        "       (DECODE(TRUNC(100 * ROUND(TOTAL2.SUM_UNITS*0.8+TOTAL.SUM_COUNT*0.2) / TOTAL2.SUM_UNITS),\n" +
                        "               0,\n" +
                        "               STR_REPLACE(ROUND(100 * ROUND(TOTAL2.SUM_UNITS*0.8+TOTAL.SUM_COUNT*0.2) / TOTAL2.SUM_UNITS, 3),\n" +
                        "                       '.',\n" +
                        "                       '0.'),\n" +
                        "               STR(ROUND(100 * ROUND(TOTAL2.SUM_UNITS*0.8+TOTAL.SUM_COUNT*0.2) / TOTAL2.SUM_UNITS, 3))) || '%') AMOUNT_RATE\n" +
                        "FROM   ETS_OU_CITY_MAP EOCM,\n" +
                        "       (SELECT EFA.ORGANIZATION_ID,\n" +
                        "               COUNT(EFA.CURRENT_UNITS) SUM_COUNT,\n" +
                        "               SUM(EFA.COST) SUM_COST\n" +
                        "        FROM   ETS_FA_ASSETS      EFA,\n" +
                        "               ETS_ITEM_INFO      EII,\n" +
                        "               ETS_ITEM_MATCH     EIM\n" +
                        "        WHERE  EFA.ASSET_ID = EIM.ASSET_ID\n" +
                        "               AND EII.SYSTEMID = EIM.SYSTEMID\n" +
                        "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.ORGANIZATION_ID = ?)\n" +
                        "               AND EIM.MATCH_DATE <= ISNULL(?, EIM.MATCH_DATE)\n" +
                        "        GROUP  BY EFA.ORGANIZATION_ID) TOTAL,\n" +
                        "       (SELECT EFA.ORGANIZATION_ID,\n" +
                        "               SUM(EFA.COST) SUM_COST2,\n" +
                        "               COUNT(EFA.CURRENT_UNITS) SUM_UNITS\n" +
                        "        FROM   ETS_FA_ASSETS EFA\n" +
                        "        WHERE  ( " + SyBaseSQLUtil.isNull() + "  OR EFA.ORGANIZATION_ID = ?)\n" +
                        "        GROUP  BY EFA.ORGANIZATION_ID) TOTAL2       \n" +
                        "WHERE   EOCM.ORGANIZATION_ID = TOTAL.ORGANIZATION_ID AND\n" +
                        "  EOCM.ORGANIZATION_ID = TOTAL2.ORGANIZATION_ID \n" +
                        " ORDER BY EOCM.ORGANIZATION_ID";
                sqlArgs.add(dto.getOrganizationId());
                sqlArgs.add(dto.getOrganizationId());
                sqlArgs.add(dto.getEndDate());
                sqlArgs.add(dto.getOrganizationId());
                sqlArgs.add(dto.getOrganizationId());
            }
                sqlModel.setSqlStr(sqlStr);
                sqlModel.setArgs(sqlArgs);
            } catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
            return sqlModel;
	}
}
