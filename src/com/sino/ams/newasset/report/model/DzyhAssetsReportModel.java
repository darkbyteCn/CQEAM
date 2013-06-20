package com.sino.ams.newasset.report.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.SQLModelException;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2009-6-9
 * Time: 18:15:12
 * To change this template use File | Settings | File Templates.
 */
public class DzyhAssetsReportModel extends AMSSQLProducer {

    public DzyhAssetsReportModel(SfUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    public SQLModel getPageQueryModel() throws SQLModelException {
        AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT TOTAL1.ORGANIZATION_ID,\n" +
                "       TOTAL1.COMPANY,\n" +
                "       TOTAL1.DZYH_COUNT,\n" +
                "       TOTAL1.SUM_PRICE,\n" +
                //"       DECODE(TOTAL2.DZYH_TD_COUNT, NULL, 0, TOTAL2.DZYH_TD_COUNT) DZYH_TD_COUNT,\n" +
                "       CASE TOTAL2.DZYH_TD_COUNT WHEN NULL THEN 0 ELSE TOTAL2.DZYH_TD_COUNT END DZYH_TD_COUNT,\n" +
                "       TOTAL1.DZYH_COUNT -\n" +
                //"       DECODE(TOTAL2.DZYH_TD_COUNT, NULL, 0, TOTAL2.DZYH_TD_COUNT) DZYH_NOTD_COUNT\n" +
                "       CASE TOTAL2.DZYH_TD_COUNT WHEN NULL THEN 0 ELSE TOTAL2.DZYH_TD_COUNT END DZYH_NOTD_COUNT\n" +
                "  FROM (SELECT EOCM.ORGANIZATION_ID,\n" +
                "               EOCM.COMPANY,\n" +
                "               COUNT(1) DZYH_COUNT,\n" +
                "               SUM(EII.PRICE) SUM_PRICE\n" +
                "          FROM ETS_ITEM_INFO   EII,\n" +
                "               ETS_OU_CITY_MAP EOCM\n" +
                "         WHERE EII.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
                "               AND EII.FINANCE_PROP = 'DH_ASSETS'\n" +
                //"               AND EII.DISABLE_DAT " + SyBaseSQLUtil.isNull() + " \n" +
                //"               AND EII.DISABLE_DATE IS NULL \n" +
                "				  AND (EII.DISABLE_DATE IS NULL OR CONVERT(VARCHAR, EII.DISABLE_DATE) " + SyBaseSQLUtil.isNullNoParam() + ") \n" +
                //"               AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.ORGANIZATION_ID = ?)\n" +
                //"   			AND ( CONVERT(VARCHAR, ?) = '' OR EII.ORGANIZATION_ID = ? )\n" +
                "				AND (" + SyBaseSQLUtil.nullIntParam() + " OR EII.ORGANIZATION_ID = ?)\n" +
                "         GROUP BY EOCM.ORGANIZATION_ID,\n" +
                "                  EOCM.COMPANY) TOTAL1,\n" +
                "       (SELECT EII.ORGANIZATION_ID,\n" +
                "               COUNT(1) DZYH_TD_COUNT\n" +
                "          FROM ETS_ITEM_INFO EII\n" +
                "         WHERE EII.FINANCE_PROP = 'DH_ASSETS'\n" +
                //"               AND EII.DISABLE_DAT " + SyBaseSQLUtil.isNull() + " \n" +
                "               AND EII.DISABLE_DATE IS NULL \n" +
                "               AND EII.IS_TD = 'Y'\n" +
                //"               AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.ORGANIZATION_ID = ?)\n" +
                "   			AND ( CONVERT(VARCHAR, ?) = '' OR EII.ORGANIZATION_ID = ? )\n" +
                "         GROUP BY EII.IS_TD,\n" +
                "                  EII.ORGANIZATION_ID) TOTAL2\n" +
                " WHERE TOTAL1.ORGANIZATION_ID *= TOTAL2.ORGANIZATION_ID";
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
	 * 功能：获取部门低值易耗统计数据
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getDeptDzyhModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "";

        AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
