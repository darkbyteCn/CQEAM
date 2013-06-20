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
 * Date: 2009-3-2
 * Time: 21:41:49
 * To change this template use File | Settings | File Templates.
 */
public class DHSpecialAssetsReportModel extends AMSSQLProducer {

	public DHSpecialAssetsReportModel(SfUserDTO userAccount, SpecialAssetsReportDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

/**
	 * 功能：获取低值易耗资产专业统计报表SQL
	 * @return SQLModel
	 * @throws com.sino.base.exception.SQLModelException
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
	    List sqlArgs = new ArrayList();
        SpecialAssetsReportDTO dto = (SpecialAssetsReportDTO) dtoParameter;
        String specialAssetsType = dto.getSpecialAssetsType();
        String sqlStr = "SELECT DECODE(TOTAL.ASSETS_SPECIES, '', '类项为空', TOTAL.ASSETS_SPECIES) ASSETS_SPECIES,\n" +
                     "       TOTAL.ASSETS_NAPE,\n" +
                     "       TOTAL.SUM_NAPE,\n" +
                     "       (DECODE(TRUNC(100 * TOTAL.SUM_NAPE / TOTAL2.SUM_QTY),\n" +
                     "               0,\n" +
                     "               STR_REPLACE(ROUND(100 * TOTAL.SUM_NAPE / TOTAL2.SUM_QTY, 3),\n" +
                     "                       '.',\n" +
                     "                       '0.'),\n" +
                     "               TO_CHAR(ROUND(100 * TOTAL.SUM_NAPE / TOTAL2.SUM_QTY, 3))) || '%') ASSETS_RATE\n" +
                     " FROM   (SELECT SUBSTRING(EII.CONTENT_NAME,\n" +
                     "                      1,\n" +
                     "                      CHARINDEX(EII.CONTENT_NAME, '-', 1) - DATALENGTH('-')) ASSETS_SPECIES,\n" +
                     "               SUBSTRING(EII.CONTENT_NAME,\n" +
                     "                      CHARINDEX(EII.CONTENT_NAME, '-', 1) + DATALENGTH('-'),\n" +
                     "                      (CHARINDEX(EII.CONTENT_NAME, '-', 1, 2) -\n" +
                     "                      CHARINDEX(EII.CONTENT_NAME, '-', 1, 1)) - DATALENGTH('-')) ASSETS_NAPE,\n" +
                     "               COUNT(EII.ITEM_QTY) SUM_NAPE\n" +
                     "        FROM   ETS_ITEM_INFO  EII\n" +
                     "        WHERE  EII.FINANCE_PROP = 'DH_ASSETS'\n"		+
                     "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.CONTENT_CODE LIKE ?)\n" +
                     "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.ORGANIZATION_ID = ?)\n" +
                     "        GROUP  BY SUBSTRING(EII.CONTENT_NAME,\n" +
                     "                         1,\n" +
                     "                         CHARINDEX(EII.CONTENT_NAME, '-', 1) - DATALENGTH('-')),\n" +
                     "                  SUBSTRING(EII.CONTENT_NAME,\n" +
                     "                         CHARINDEX(EII.CONTENT_NAME, '-', 1) + DATALENGTH('-'),\n" +
                     "                         (CHARINDEX(EII.CONTENT_NAME, '-', 1, 2) -\n" +
                     "                         CHARINDEX(EII.CONTENT_NAME, '-', 1, 1)) - DATALENGTH('-'))\n" +
                     "        UNION\n" +
                     "        SELECT SUBSTRING(EII.CONTENT_NAME,\n" +
                     "                      1,\n" +
                     "                      CHARINDEX(EII.CONTENT_NAME, '-', 1) - DATALENGTH('-')) ASSETS_SPECIES,\n" +
                     "               '' ASSETS_NAPE,\n" +
                     "               COUNT(EII.ITEM_QTY)\n" +
                     "        FROM   ETS_ITEM_INFO  EII\n" +
                     "        WHERE  EII.FINANCE_PROP = 'DH_ASSETS'\n"		+
                     "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.CONTENT_CODE LIKE ?)\n" +
                     "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.ORGANIZATION_ID = ?)\n" +
                     "        GROUP  BY SUBSTRING(EII.CONTENT_NAME,\n" +
                     "                         1,\n" +
                     "                         CHARINDEX(EII.CONTENT_NAME, '-', 1) - DATALENGTH('-'))) TOTAL,\n" +
                     "       (SELECT COUNT(EII.ITEM_QTY) SUM_QTY\n" +
                     "        FROM   ETS_ITEM_INFO  EII\n" +
                     "        WHERE  EII.FINANCE_PROP = 'DH_ASSETS'"		+
                     "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.ORGANIZATION_ID = ?)) TOTAL2\n" +
                     " ORDER  BY TOTAL.ASSETS_SPECIES,\n" +
                     "          TOTAL.ASSETS_NAPE DESC  ";
			sqlArgs.add(dto.getContentCode());
			sqlArgs.add(dto.getContentCode());
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getContentCode());
			sqlArgs.add(dto.getContentCode());
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getOrganizationId());
            sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);
	    sqlModel.setArgs(sqlArgs);
        return sqlModel;
	}
}
