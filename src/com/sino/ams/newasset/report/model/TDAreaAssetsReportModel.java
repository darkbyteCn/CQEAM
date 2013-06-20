package com.sino.ams.newasset.report.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.report.dto.SpecialAssetsReportDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2009-3-5
 * Time: 11:00:16
 * To change this template use File | Settings | File Templates.
 */
public class TDAreaAssetsReportModel extends AMSSQLProducer {

	public TDAreaAssetsReportModel(SfUserDTO userAccount, SpecialAssetsReportDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

/**
	 * 功能：TD区域资产构成统计报表
	 * @return SQLModel
	 * @throws com.sino.base.exception.SQLModelException
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
	    List sqlArgs = new ArrayList();
        SpecialAssetsReportDTO dto = (SpecialAssetsReportDTO) dtoParameter;
        String sqlStr = "SELECT KK.ASSETS_SPECIES,\n" +
                    "       KK.ASSETS_NAPE,\n" +
                    "       AA.A CITY_COST,\n" +
                    "       BB.B COUNTY_COST,\n" +
                    "       CC.C VILLAGE_COST,\n" +
                    "       (DECODE(TRUNC(100 * AA.A / TOTAL.SUM_COST),\n" +
                    "               0,\n" +
                    "               STR_REPLACE(ROUND(100 * AA.A / TOTAL.SUM_COST, 3), '.', '0.'),\n" +
                    "               TO_CHAR(ROUND(100 * AA.A / TOTAL.SUM_COST, 3))) || '%') CITY_RATE,\n" +
                    "       (DECODE(TRUNC(100 * BB.B / TOTAL.SUM_COST),\n" +
                    "               0,\n" +
                    "               STR_REPLACE(ROUND(100 * BB.B / TOTAL.SUM_COST, 3), '.', '0.'),\n" +
                    "               TO_CHAR(ROUND(100 * BB.B / TOTAL.SUM_COST, 3))) || '%') COUNTY_RATE,\n" +
                    "       (DECODE(TRUNC(100 * CC.C / TOTAL.SUM_COST),\n" +
                    "               0,\n" +
                    "               STR_REPLACE(ROUND(100 * CC.C / TOTAL.SUM_COST, 3), '.', '0.'),\n" +
                    "               TO_CHAR(ROUND(100 * CC.C / TOTAL.SUM_COST, 3))) || '%') VILLAGE_RATE\n" +
                    "FROM   (SELECT DECODE(SUBSTRING(EFA.FA_CATEGORY2,\n" +
                    "                             1,\n" +
                    "                             CHARINDEX(EFA.FA_CATEGORY2, '-', 1) - DATALENGTH('-')),\n" +
                    "                      NULL,\n" +
                    "                      '类为空',\n" +
                    "                      SUBSTRING(EFA.FA_CATEGORY2,\n" +
                    "                             1,\n" +
                    "                             CHARINDEX(EFA.FA_CATEGORY2, '-', 1) - DATALENGTH('-'))) ASSETS_SPECIES,\n" +
                    "               DECODE(SUBSTRING(EFA.FA_CATEGORY2,\n" +
                    "                             CHARINDEX(EFA.FA_CATEGORY2, '-', 1) + DATALENGTH('-'),\n" +
                    "                             (CHARINDEX(EFA.FA_CATEGORY2, '-', 1, 2) -\n" +
                    "                             CHARINDEX(EFA.FA_CATEGORY2, '-', 1, 1)) - DATALENGTH('-')),\n" +
                    "                      NULL,\n" +
                    "                      '项为空',\n" +
                    "                      SUBSTRING(EFA.FA_CATEGORY2,\n" +
                    "                             CHARINDEX(EFA.FA_CATEGORY2, '-', 1) + DATALENGTH('-'),\n" +
                    "                             (CHARINDEX(EFA.FA_CATEGORY2, '-', 1, 2) -\n" +
                    "                             CHARINDEX(EFA.FA_CATEGORY2, '-', 1, 1)) - DATALENGTH('-'))) ASSETS_NAPE\n" +
                    "        FROM   ETS_FA_ASSETS      EFA,\n" +
                    "               ETS_OBJECT         EO,\n" +
                    "               AMS_OBJECT_ADDRESS AOA,\n" +
                    "               ETS_ITEM_INFO      EII,\n" +
                    "               ETS_ITEM_MATCH     EIM\n" +
                    "        WHERE  EII.SYSTEMID = EIM.SYSTEMID\n" +
                    "               AND EFA.ASSET_ID = EIM.ASSET_ID\n" +
                    "               AND EO.WORKORDER_OBJECT_NO = AOA.OBJECT_NO\n" +
                    "               AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                    "               AND EII.FINANCE_PROP = 'TD_ASSETS'\n" +
                    "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.ORGANIZATION_ID = ?)\n" +
                    "        GROUP  BY SUBSTRING(EFA.FA_CATEGORY2,\n" +
                    "                         1,\n" +
                    "                         CHARINDEX(EFA.FA_CATEGORY2, '-', 1) - DATALENGTH('-')),\n" +
                    "                  SUBSTRING(EFA.FA_CATEGORY2,\n" +
                    "                         CHARINDEX(EFA.FA_CATEGORY2, '-', 1) + DATALENGTH('-'),\n" +
                    "                         (CHARINDEX(EFA.FA_CATEGORY2, '-', 1, 2) -\n" +
                    "                         CHARINDEX(EFA.FA_CATEGORY2, '-', 1, 1)) - DATALENGTH('-'))) KK,\n" +
                    "       (SELECT DECODE(SUBSTRING(EFA.FA_CATEGORY2,\n" +
                    "                             1,\n" +
                    "                             CHARINDEX(EFA.FA_CATEGORY2, '-', 1) - DATALENGTH('-')),\n" +
                    "                      NULL,\n" +
                    "                      '类为空',\n" +
                    "                      SUBSTRING(EFA.FA_CATEGORY2,\n" +
                    "                             1,\n" +
                    "                             CHARINDEX(EFA.FA_CATEGORY2, '-', 1) - DATALENGTH('-'))) ASSETS_SPECIES,\n" +
                    "               DECODE(SUBSTRING(EFA.FA_CATEGORY2,\n" +
                    "                             CHARINDEX(EFA.FA_CATEGORY2, '-', 1) + DATALENGTH('-'),\n" +
                    "                             (CHARINDEX(EFA.FA_CATEGORY2, '-', 1, 2) -\n" +
                    "                             CHARINDEX(EFA.FA_CATEGORY2, '-', 1, 1)) - DATALENGTH('-')),\n" +
                    "                      NULL,\n" +
                    "                      '项为空',\n" +
                    "                      SUBSTRING(EFA.FA_CATEGORY2,\n" +
                    "                             CHARINDEX(EFA.FA_CATEGORY2, '-', 1) + DATALENGTH('-'),\n" +
                    "                             (CHARINDEX(EFA.FA_CATEGORY2, '-', 1, 2) -\n" +
                    "                             CHARINDEX(EFA.FA_CATEGORY2, '-', 1, 1)) - DATALENGTH('-'))) ASSETS_NAPE,\n" +
                    "               SUM(EFA.COST) A\n" +
                    "        FROM   ETS_FA_ASSETS      EFA,\n" +
                    "               ETS_OBJECT         EO,\n" +
                    "               AMS_OBJECT_ADDRESS AOA,\n" +
                    "               ETS_ITEM_INFO      EII,\n" +
                    "               ETS_ITEM_MATCH     EIM\n" +
                    "        WHERE  EII.SYSTEMID = EIM.SYSTEMID\n" +
                    "               AND EFA.ASSET_ID = EIM.ASSET_ID\n" +
                    "               AND EO.WORKORDER_OBJECT_NO = AOA.OBJECT_NO\n" +
                    "               AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                    "               AND EO.AREA_TYPE IN ('01', '02', '03')\n" +
                    "               AND EII.FINANCE_PROP = 'TD_ASSETS'\n" +
                    "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.ORGANIZATION_ID = ?)\n" +
                    "        GROUP  BY SUBSTRING(EFA.FA_CATEGORY2,\n" +
                    "                         1,\n" +
                    "                         CHARINDEX(EFA.FA_CATEGORY2, '-', 1) - DATALENGTH('-')),\n" +
                    "                  SUBSTRING(EFA.FA_CATEGORY2,\n" +
                    "                         CHARINDEX(EFA.FA_CATEGORY2, '-', 1) + DATALENGTH('-'),\n" +
                    "                         (CHARINDEX(EFA.FA_CATEGORY2, '-', 1, 2) -\n" +
                    "                         CHARINDEX(EFA.FA_CATEGORY2, '-', 1, 1)) - DATALENGTH('-'))) AA,\n" +
                    "       (SELECT DECODE(SUBSTRING(EFA.FA_CATEGORY2,\n" +
                    "                             1,\n" +
                    "                             CHARINDEX(EFA.FA_CATEGORY2, '-', 1) - DATALENGTH('-')),\n" +
                    "                      NULL,\n" +
                    "                      '类为空',\n" +
                    "                      SUBSTRING(EFA.FA_CATEGORY2,\n" +
                    "                             1,\n" +
                    "                             CHARINDEX(EFA.FA_CATEGORY2, '-', 1) - DATALENGTH('-'))) ASSETS_SPECIES,\n" +
                    "               DECODE(SUBSTRING(EFA.FA_CATEGORY2,\n" +
                    "                             CHARINDEX(EFA.FA_CATEGORY2, '-', 1) + DATALENGTH('-'),\n" +
                    "                             (CHARINDEX(EFA.FA_CATEGORY2, '-', 1, 2) -\n" +
                    "                             CHARINDEX(EFA.FA_CATEGORY2, '-', 1, 1)) - DATALENGTH('-')),\n" +
                    "                      NULL,\n" +
                    "                      '项为空',\n" +
                    "                      SUBSTRING(EFA.FA_CATEGORY2,\n" +
                    "                             CHARINDEX(EFA.FA_CATEGORY2, '-', 1) + DATALENGTH('-'),\n" +
                    "                             (CHARINDEX(EFA.FA_CATEGORY2, '-', 1, 2) -\n" +
                    "                             CHARINDEX(EFA.FA_CATEGORY2, '-', 1, 1)) - DATALENGTH('-'))) ASSETS_NAPE,\n" +
                    "               SUM(EFA.COST) B\n" +
                    "        FROM   ETS_FA_ASSETS      EFA,\n" +
                    "               ETS_OBJECT         EO,\n" +
                    "               AMS_OBJECT_ADDRESS AOA,\n" +
                    "               ETS_ITEM_INFO      EII,\n" +
                    "               ETS_ITEM_MATCH     EIM\n" +
                    "        WHERE  EII.SYSTEMID = EIM.SYSTEMID\n" +
                    "               AND EFA.ASSET_ID = EIM.ASSET_ID\n" +
                    "               AND EO.WORKORDER_OBJECT_NO = AOA.OBJECT_NO\n" +
                    "               AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                    "               AND EII.FINANCE_PROP = 'TD_ASSETS'\n" +
                    "               AND EO.AREA_TYPE = '04'\n" +
                    "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.ORGANIZATION_ID = ?)\n" +
                    "        GROUP  BY SUBSTRING(EFA.FA_CATEGORY2,\n" +
                    "                         1,\n" +
                    "                         CHARINDEX(EFA.FA_CATEGORY2, '-', 1) - DATALENGTH('-')),\n" +
                    "                  SUBSTRING(EFA.FA_CATEGORY2,\n" +
                    "                         CHARINDEX(EFA.FA_CATEGORY2, '-', 1) + DATALENGTH('-'),\n" +
                    "                         (CHARINDEX(EFA.FA_CATEGORY2, '-', 1, 2) -\n" +
                    "                         CHARINDEX(EFA.FA_CATEGORY2, '-', 1, 1)) - DATALENGTH('-'))) BB,\n" +
                    "       (SELECT DECODE(SUBSTRING(EFA.FA_CATEGORY2,\n" +
                    "                             1,\n" +
                    "                             CHARINDEX(EFA.FA_CATEGORY2, '-', 1) - DATALENGTH('-')),\n" +
                    "                      NULL,\n" +
                    "                      '类为空',\n" +
                    "                      SUBSTRING(EFA.FA_CATEGORY2,\n" +
                    "                             1,\n" +
                    "                             CHARINDEX(EFA.FA_CATEGORY2, '-', 1) - DATALENGTH('-'))) ASSETS_SPECIES,\n" +
                    "               DECODE(SUBSTRING(EFA.FA_CATEGORY2,\n" +
                    "                             CHARINDEX(EFA.FA_CATEGORY2, '-', 1) + DATALENGTH('-'),\n" +
                    "                             (CHARINDEX(EFA.FA_CATEGORY2, '-', 1, 2) -\n" +
                    "                             CHARINDEX(EFA.FA_CATEGORY2, '-', 1, 1)) - DATALENGTH('-')),\n" +
                    "                      NULL,\n" +
                    "                      '项为空',\n" +
                    "                      SUBSTRING(EFA.FA_CATEGORY2,\n" +
                    "                             CHARINDEX(EFA.FA_CATEGORY2, '-', 1) + DATALENGTH('-'),\n" +
                    "                             (CHARINDEX(EFA.FA_CATEGORY2, '-', 1, 2) -\n" +
                    "                             CHARINDEX(EFA.FA_CATEGORY2, '-', 1, 1)) - DATALENGTH('-'))) ASSETS_NAPE,\n" +
                    "               SUM(EFA.COST) C\n" +
                    "        FROM   ETS_FA_ASSETS      EFA,\n" +
                    "               ETS_OBJECT         EO,\n" +
                    "               AMS_OBJECT_ADDRESS AOA,\n" +
                    "               ETS_ITEM_INFO      EII,\n" +
                    "               ETS_ITEM_MATCH     EIM\n" +
                    "        WHERE  EII.SYSTEMID = EIM.SYSTEMID\n" +
                    "               AND EFA.ASSET_ID = EIM.ASSET_ID\n" +
                    "               AND EO.WORKORDER_OBJECT_NO = AOA.OBJECT_NO\n" +
                    "               AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                    "               AND EII.FINANCE_PROP = 'TD_ASSETS'\n" +
                    "               AND EO.AREA_TYPE IN ('05', '06')\n" +
                    "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.ORGANIZATION_ID = ?)\n" +
                    "        GROUP  BY SUBSTRING(EFA.FA_CATEGORY2,\n" +
                    "                         1,\n" +
                    "                         CHARINDEX(EFA.FA_CATEGORY2, '-', 1) - DATALENGTH('-')),\n" +
                    "                  SUBSTRING(EFA.FA_CATEGORY2,\n" +
                    "                         CHARINDEX(EFA.FA_CATEGORY2, '-', 1) + DATALENGTH('-'),\n" +
                    "                         (CHARINDEX(EFA.FA_CATEGORY2, '-', 1, 2) -\n" +
                    "                         CHARINDEX(EFA.FA_CATEGORY2, '-', 1, 1)) - DATALENGTH('-'))) CC,\n" +
                    "       (SELECT SUM(EFA.COST) SUM_COST\n" +
                    "        FROM   ETS_FA_ASSETS  EFA,\n" +
                    "               ETS_ITEM_INFO  EII,\n" +
                    "               ETS_ITEM_MATCH EIM\n" +
                    "        WHERE  EFA.ASSET_ID = EIM.ASSET_ID\n" +
                    "               AND EII.SYSTEMID = EIM.SYSTEMID\n" +
                    "               AND EII.FINANCE_PROP = 'TD_ASSETS'\n" +
                    "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.ORGANIZATION_ID = ?)) TOTAL\n" +
                    "WHERE  KK.ASSETS_SPECIES *= AA.ASSETS_SPECIES\n" +
                    "       AND KK.ASSETS_SPECIES *= BB.ASSETS_SPECIES\n" +
                    "       AND KK.ASSETS_SPECIES *= CC.ASSETS_SPECIES\n" +
                    "       AND KK.ASSETS_NAPE *= AA.ASSETS_NAPE\n" +
                    "       AND KK.ASSETS_NAPE *= BB.ASSETS_NAPE\n" +
                    "       AND KK.ASSETS_NAPE *= CC.ASSETS_NAPE\n" +
                    "ORDER  BY KK.ASSETS_SPECIES,\n" +
                    "          KK.ASSETS_NAPE";
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getOrganizationId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
	}
}
