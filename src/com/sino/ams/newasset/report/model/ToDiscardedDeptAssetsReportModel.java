package com.sino.ams.newasset.report.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.dto.AmsMisDeptDTO;
import com.sino.ams.newasset.report.dto.DeptAssetsReportDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.SQLModelException;

/**
 * Created by IntelliJ IDEA.
 * @author 		李轶
 * Date: 2009-5-21
 * Time: 10:50:55
 * To change this template use File | Settings | File Templates.
 */

public class ToDiscardedDeptAssetsReportModel extends AMSSQLProducer {
    private String deptCodes = "";

    public ToDiscardedDeptAssetsReportModel(SfUserDTO userAccount, DeptAssetsReportDTO dtoParameter) {
        super(userAccount, dtoParameter);
        initDeptCodes();
    }

    /**
     * 功能：初始化当前用户有权限修改资产的所属部门信息
     */
    private void initDeptCodes() {
        deptCodes = "(";
        DTOSet depts = userAccount.getPriviDeptCodes();
        if (depts != null && !depts.isEmpty()) {
            AmsMisDeptDTO dept = null;
            for (int i = 0; i < depts.getSize(); i++) {
                dept = (AmsMisDeptDTO) depts.getDTO(i);
                deptCodes += "'" + dept.getDeptCode() + "', ";
            }
        }
        deptCodes += "'')";
    }

    /**
     * 功能：获取待报废部门资产构成分布统计报表SQL
     *
     * @return SQLModel
     * @throws com.sino.base.exception.SQLModelException
     *
     */
    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "";
        DeptAssetsReportDTO dto = (DeptAssetsReportDTO) dtoParameter;
        String deptAssetsType = dto.getDeptAssetsType();
        /*if (userAccount.isProvAssetsManager()) {//按部门资产构成分布(省公司)
            sqlStr = "SELECT TOTAL.COMPANY,\n" +
                    "       TOTAL.DEPT_NAME,\n" +
                    
			        "       TOTAL.SUM_COST,\n" +
			        "       TOTAL.DEPRN_RESERVE,\n" +
			        "       TOTAL.NET_BOOK_VALUE,\n" +
			        "		TOTAL.IMPAIRMENT_RESERVE,\n"	+
			        "       TOTAL.LIMIT_VALUE,\n" +
			        
			        "       TOTAL.SUM_COUNT,\n" +
			        "       TOTAL.PTD_DEPRN,\n" +
			        "       (DECODE(TRUNC(100 * TOTAL.SUM_COST / TOTAL2.SUM_COST_TOTAL),\n" +
			        "               0,\n" +
			        "               STR_REPLACE(ROUND(100 * TOTAL.SUM_COST / TOTAL2.SUM_COST_TOTAL, 3),\n" +
			        "                       '.',\n" +
			        "                       '0.'),\n" +
			        "               TO_CHAR(ROUND(100 * TOTAL.SUM_COST / TOTAL2.SUM_COST_TOTAL, 3))) || '%') ASSETS_RATE,\n" +
			        
			        
                    "       '' LAST_YEAR_RATE\n" +
                    "FROM   (SELECT AOCM.COMPANY COMPANY,\n" +
                    "               AMD.DEPT_NAME DEPT_NAME,\n" +
                    "               COUNT(EFA.CURRENT_UNITS) SUM_COUNT,\n" +
			        "               SUM(ZFDD.COST) SUM_COST,\n" +
			        "               SUM(ZFDD.COST - ZFDD.DEPRN_RESERVE) NET_BOOK_VALUE,\n" +
			        "               SUM(ZFDD.COST - ZFDD.DEPRN_RESERVE - ZFDD.IMPAIRMENT_RESERVE) LIMIT_VALUE,\n" +
			        "               SUM(ZFDD.DEPRN_RESERVE) DEPRN_RESERVE,\n" +
			        "				SUM(ZFDD.IMPAIRMENT_RESERVE) IMPAIRMENT_RESERVE,\n"	+
			        "               SUM(ZFDD.PTD_DEPRN) PTD_DEPRN\n" +
                    "        FROM   ETS_ITEM_INFO   EII,\n" +
                    "               AMS_MIS_DEPT    AMD,\n" +
                    "               ETS_FA_ASSETS   EFA,\n" +
                    "               ETS_OU_CITY_MAP AOCM,\n" +
                    "               ETS_ITEM_MATCH  EIM,\n" +
                    "               SOA.ZTE_FA_DEPRN_DETAIL ZFDD,\n" +
			        "				AMS_ASSETS_TRANS_HEADER AATH,\n"	+
			        "				AMS_ASSETS_TRANS_LINE   AATL \n"	+
                    "        WHERE  EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE\n" +
                    "               AND EII.SYSTEMID = EIM.SYSTEMID\n" +
                    "               AND EFA.ASSET_ID = EIM.ASSET_ID\n" +
                    "               AND AOCM.ORGANIZATION_ID = EFA.ORGANIZATION_ID\n" +
                    "               AND EII.ITEM_STATUS = 'TO_DISCARD'\n" +
                    "               AND EFA.ASSET_ID *= ZFDD.ASSET_ID\n"	+
			        "				AND AATH.TRANS_ID = AATL.TRANS_ID"	+
			        "				AND AATL.BARCODE = EII.BARCODE "		+
			        "               AND ZFDD.PERIOD_NAME = ?\n" +
			        "               AND AATH.LAST_UPDATE_DATE BETWEEN\n" +
	                "               TRUNC(TO_DATE(?, 'YYYY-MM-DD'), 'MM') AND\n" +
	                "               TRUNC(LAST_DAY(TO_DATE(?, 'YYYY-MM-DD')) + 1)\n" +
                    "               AND ( " + SyBaseSQLUtil.isNull() + "  OR AOCM.ORGANIZATION_ID = ?)\n" +
                    "        GROUP  BY AOCM.COMPANY,\n" +
                    "                  AMD.DEPT_NAME\n" +
                    "        \n" +
                    "        UNION\n" +
                    "        SELECT AOCM.COMPANY COMPANY,\n" +
                    "               '' DEPT_NAME,\n" +
                    "               COUNT(EFA.CURRENT_UNITS),\n" +
			        "               SUM(ZFDD.COST) SUM_COST,\n" +
			        "               SUM(ZFDD.COST - ZFDD.DEPRN_RESERVE) NET_BOOK_VALUE,\n" +
			        "               SUM(ZFDD.COST - ZFDD.DEPRN_RESERVE - ZFDD.IMPAIRMENT_RESERVE) LIMIT_VALUE,\n" +
			        "               SUM(ZFDD.DEPRN_RESERVE) DEPRN_RESERVE,\n" +
			        "				SUM(ZFDD.IMPAIRMENT_RESERVE) IMPAIRMENT_RESERVE,\n"	+
			        "               SUM(ZFDD.PTD_DEPRN) PTD_DEPRN\n" +
                    "        FROM   ETS_ITEM_INFO   EII,\n" +
                    "               AMS_MIS_DEPT    AMD,\n" +
                    "               ETS_FA_ASSETS   EFA,\n" +
                    "               ETS_OU_CITY_MAP AOCM,\n" +
                    "               ETS_ITEM_MATCH  EIM,\n" +
                    "               SOA.ZTE_FA_DEPRN_DETAIL ZFDD,\n" +
			        "				AMS_ASSETS_TRANS_HEADER AATH,\n"	+
			        "				AMS_ASSETS_TRANS_LINE   AATL \n"	+
                    "        WHERE  EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE\n" +
                    "               AND EII.SYSTEMID = EIM.SYSTEMID\n" +
                    "               AND EFA.ASSET_ID = EIM.ASSET_ID\n" +
                    "               AND AOCM.ORGANIZATION_ID = EFA.ORGANIZATION_ID\n" +
                    "               AND EII.ITEM_STATUS = 'TO_DISCARD'\n" +
                    "               AND EFA.ASSET_ID *= ZFDD.ASSET_ID\n"	+
			        "				AND AATH.TRANS_ID = AATL.TRANS_ID"	+
			        "				AND AATL.BARCODE = EII.BARCODE "		+
			        "               AND ZFDD.PERIOD_NAME = ?\n" +
			        "               AND AATH.LAST_UPDATE_DATE BETWEEN\n" +
	                "               TRUNC(TO_DATE(?, 'YYYY-MM-DD'), 'MM') AND\n" +
	                "               TRUNC(LAST_DAY(TO_DATE(?, 'YYYY-MM-DD')) + 1)\n" +
                    "               AND ( " + SyBaseSQLUtil.isNull() + "  OR AOCM.ORGANIZATION_ID = ?)\n" +
                    "        GROUP  BY AOCM.COMPANY) TOTAL,\n" +
                    "       (SELECT SUM(ZFDD.COST) SUM_COST_TOTAL\n" +
                    "        FROM   ETS_FA_ASSETS  EFA,\n" +
                    "               ETS_ITEM_INFO  EII,\n" +
                    "               ETS_ITEM_MATCH EIM,\n" +
                    "               SOA.ZTE_FA_DEPRN_DETAIL ZFDD,\n" +
			        "				AMS_ASSETS_TRANS_HEADER AATH,\n"	+
			        "				AMS_ASSETS_TRANS_LINE   AATL \n"	+
                    "        WHERE  EII.SYSTEMID = EIM.SYSTEMID\n" +
                    "               AND EFA.ASSET_ID = EIM.ASSET_ID\n" +
                    "               AND EII.ITEM_STATUS = 'TO_DISCARD'\n" +
                    "               AND EFA.ASSET_ID *= ZFDD.ASSET_ID\n"	+
			        "				AND AATH.TRANS_ID = AATL.TRANS_ID"	+
			        "				AND AATL.BARCODE = EII.BARCODE "		+
			        "               AND ZFDD.PERIOD_NAME = ?\n" +
			        "               AND AATH.LAST_UPDATE_DATE BETWEEN\n" +
	                "               TRUNC(TO_DATE(?, 'YYYY-MM-DD'), 'MM') AND\n" +
	                "               TRUNC(LAST_DAY(TO_DATE(?, 'YYYY-MM-DD')) + 1)\n" +
                    "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.ORGANIZATION_ID = ?)) TOTAL2\n" +
                    "ORDER  BY TOTAL.COMPANY,\n" +
                    "          TOTAL.DEPT_NAME DESC";
            
            sqlArgs.add(dto.getPeriodName());
            sqlArgs.add(dto.getYear() + "-" + dto.getMonth() + "-01");
            sqlArgs.add(dto.getYear() + "-" + dto.getMonth() + "-01");            
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getOrganizationId());
            
            sqlArgs.add(dto.getPeriodName());
            sqlArgs.add(dto.getYear() + "-" + dto.getMonth() + "-01");
            sqlArgs.add(dto.getYear() + "-" + dto.getMonth() + "-01");
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getOrganizationId());
            
            sqlArgs.add(dto.getPeriodName());
            sqlArgs.add(dto.getYear() + "-" + dto.getMonth() + "-01");
            sqlArgs.add(dto.getYear() + "-" + dto.getMonth() + "-01");
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getOrganizationId());
        } else if (userAccount.isComAssetsManager()) {//按部门资产构成分布(地市)
            sqlStr = "SELECT TOTAL.COMPANY,\n" +
                    "       TOTAL.DEPT_NAME,\n" +

                    "       TOTAL.SUM_COST,\n" +
			        "       TOTAL.DEPRN_RESERVE,\n" +
			        "       TOTAL.NET_BOOK_VALUE,\n" +
			        "		TOTAL.IMPAIRMENT_RESERVE,\n"	+
			        "       TOTAL.LIMIT_VALUE,\n" +
			        
			        "       TOTAL.SUM_COUNT,\n" +
			        "       TOTAL.PTD_DEPRN,\n" +
			        "       (DECODE(TRUNC(100 * TOTAL.SUM_COST / TOTAL2.SUM_COST_TOTAL),\n" +
			        "               0,\n" +
			        "               STR_REPLACE(ROUND(100 * TOTAL.SUM_COST / TOTAL2.SUM_COST_TOTAL, 3),\n" +
			        "                       '.',\n" +
			        "                       '0.'),\n" +
			        "               TO_CHAR(ROUND(100 * TOTAL.SUM_COST / TOTAL2.SUM_COST_TOTAL, 3))) || '%') ASSETS_RATE,\n" +
			        
			        
                    "       '' LAST_YEAR_RATE\n" +
                    
                    "FROM   (SELECT AOCM.COMPANY COMPANY,\n" +
                    "               AMD.DEPT_NAME DEPT_NAME,\n" +
                    "               COUNT(EFA.CURRENT_UNITS) SUM_COUNT,\n" +
			        "               SUM(ZFDD.COST) SUM_COST,\n" +
			        "               SUM(ZFDD.COST - ZFDD.DEPRN_RESERVE) NET_BOOK_VALUE,\n" +
			        "               SUM(ZFDD.COST - ZFDD.DEPRN_RESERVE - ZFDD.IMPAIRMENT_RESERVE) LIMIT_VALUE,\n" +
			        "               SUM(ZFDD.DEPRN_RESERVE) DEPRN_RESERVE,\n" +
			        "				SUM(ZFDD.IMPAIRMENT_RESERVE) IMPAIRMENT_RESERVE,\n"	+
			        "               SUM(ZFDD.PTD_DEPRN) PTD_DEPRN\n" +
                    "        FROM   ETS_ITEM_INFO   EII,\n" +
                    "               AMS_MIS_DEPT    AMD,\n" +
                    "               ETS_FA_ASSETS   EFA,\n" +
                    "               ETS_OU_CITY_MAP AOCM,\n" +
                    "               ETS_ITEM_MATCH  EIM,\n" +
                    "               SOA.ZTE_FA_DEPRN_DETAIL ZFDD,\n" +
			        "				AMS_ASSETS_TRANS_HEADER AATH,\n"	+
			        "				AMS_ASSETS_TRANS_LINE   AATL \n"	+
                    "        WHERE  EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE\n" +
                    "               AND EII.SYSTEMID = EIM.SYSTEMID\n" +
                    "               AND EFA.ASSET_ID = EIM.ASSET_ID\n" +
                    "               AND AOCM.ORGANIZATION_ID = EFA.ORGANIZATION_ID\n" +
                    "               AND EII.ITEM_STATUS = 'TO_DISCARD'\n" +
                    "               AND EFA.ASSET_ID *= ZFDD.ASSET_ID\n"	+
			        "				AND AATH.TRANS_ID = AATL.TRANS_ID"	+
			        "				AND AATL.BARCODE = EII.BARCODE "		+
			        "               AND ZFDD.PERIOD_NAME = ?\n" +
			        "               AND AATH.LAST_UPDATE_DATE BETWEEN\n" +
	                "               TRUNC(TO_DATE(?, 'YYYY-MM-DD'), 'MM') AND\n" +
	                "               TRUNC(LAST_DAY(TO_DATE(?, 'YYYY-MM-DD')) + 1)\n" +
                    "               AND AOCM.ORGANIZATION_ID = ?\n" +
                    "               AND ( " + SyBaseSQLUtil.isNull() + "  OR AMD.DEPT_CODE LIKE ?)\n" +
                    "        GROUP  BY AOCM.COMPANY,\n" +
                    "                  AMD.DEPT_NAME\n" +
                    "        \n" +
                    "        UNION\n" +
                    "        SELECT AOCM.COMPANY COMPANY,\n" +
                    "               '' DEPT_NAME,\n" +
                    "               COUNT(EFA.CURRENT_UNITS),\n" +
			        "               SUM(ZFDD.COST) SUM_COST,\n" +
			        "               SUM(ZFDD.COST - ZFDD.DEPRN_RESERVE) NET_BOOK_VALUE,\n" +
			        "               SUM(ZFDD.COST - ZFDD.DEPRN_RESERVE - ZFDD.IMPAIRMENT_RESERVE) LIMIT_VALUE,\n" +
			        "               SUM(ZFDD.DEPRN_RESERVE) DEPRN_RESERVE,\n" +
			        "				SUM(ZFDD.IMPAIRMENT_RESERVE) IMPAIRMENT_RESERVE,\n"	+
			        "               SUM(ZFDD.PTD_DEPRN) PTD_DEPRN\n" +
                    "        FROM   ETS_ITEM_INFO   EII,\n" +
                    "               AMS_MIS_DEPT    AMD,\n" +
                    "               ETS_FA_ASSETS   EFA,\n" +
                    "               ETS_OU_CITY_MAP AOCM,\n" +
                    "               ETS_ITEM_MATCH  EIM,\n" +
                    "               SOA.ZTE_FA_DEPRN_DETAIL ZFDD,\n" +
			        "				AMS_ASSETS_TRANS_HEADER AATH,\n"	+
			        "				AMS_ASSETS_TRANS_LINE   AATL \n"	+
                    "        WHERE  EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE\n" +
                    "               AND EII.SYSTEMID = EIM.SYSTEMID\n" +
                    "               AND EFA.ASSET_ID = EIM.ASSET_ID\n" +
                    "               AND AOCM.ORGANIZATION_ID = EFA.ORGANIZATION_ID\n" +
                    "               AND EII.ITEM_STATUS = 'TO_DISCARD'\n" +
                    "               AND EFA.ASSET_ID *= ZFDD.ASSET_ID\n"	+
			        "				AND AATH.TRANS_ID = AATL.TRANS_ID"	+
			        "				AND AATL.BARCODE = EII.BARCODE "		+
			        "               AND ZFDD.PERIOD_NAME = ?\n" +
			        "               AND AATH.LAST_UPDATE_DATE BETWEEN\n" +
	                "               TRUNC(TO_DATE(?, 'YYYY-MM-DD'), 'MM') AND\n" +
	                "               TRUNC(LAST_DAY(TO_DATE(?, 'YYYY-MM-DD')) + 1)\n" +
                    "               AND AOCM.ORGANIZATION_ID = ?\n" +
                    "               AND ( " + SyBaseSQLUtil.isNull() + "  OR AMD.DEPT_CODE LIKE ?)\n" +
                    "        GROUP  BY AOCM.COMPANY) TOTAL,\n" +
                    "       (SELECT SUM(ZFDD.COST) SUM_COST_TOTAL\n" +
                    "        FROM   ETS_FA_ASSETS  EFA,\n" +
                    "               ETS_ITEM_INFO  EII,\n" +
                    "               ETS_ITEM_MATCH EIM,\n" +
                    "               SOA.ZTE_FA_DEPRN_DETAIL ZFDD,\n" +
			        "				AMS_ASSETS_TRANS_HEADER AATH,\n"	+
			        "				AMS_ASSETS_TRANS_LINE   AATL \n"	+
                    "        WHERE  EII.SYSTEMID = EIM.SYSTEMID\n" +
                    "               AND EFA.ASSET_ID = EIM.ASSET_ID\n" +
                    "               AND EII.ITEM_STATUS = 'TO_DISCARD'\n" +
                    "               AND EFA.ASSET_ID *= ZFDD.ASSET_ID\n"	+
			        "				AND AATH.TRANS_ID = AATL.TRANS_ID"	+
			        "				AND AATL.BARCODE = EII.BARCODE "		+
			        "               AND ZFDD.PERIOD_NAME = ?\n" +
			        "               AND AATH.LAST_UPDATE_DATE BETWEEN\n" +
	                "               TRUNC(TO_DATE(?, 'YYYY-MM-DD'), 'MM') AND\n" +
	                "               TRUNC(LAST_DAY(TO_DATE(?, 'YYYY-MM-DD')) + 1)\n" +
                    "               AND EFA.ORGANIZATION_ID = ?) TOTAL2\n" +
                    "ORDER  BY TOTAL.COMPANY,\n" +
                    "          TOTAL.DEPT_NAME DESC";
            sqlArgs.add(dto.getPeriodName());
            sqlArgs.add(dto.getYear() + "-" + dto.getMonth() + "-01");
            sqlArgs.add(dto.getYear() + "-" + dto.getMonth() + "-01");
            sqlArgs.add(userAccount.getOrganizationId());
            sqlArgs.add(dto.getResponsibilityDept());
            sqlArgs.add(dto.getResponsibilityDept());
            
            sqlArgs.add(dto.getPeriodName());
            sqlArgs.add(dto.getYear() + "-" + dto.getMonth() + "-01");
            sqlArgs.add(dto.getYear() + "-" + dto.getMonth() + "-01");
            sqlArgs.add(userAccount.getOrganizationId());
            sqlArgs.add(dto.getResponsibilityDept());
            sqlArgs.add(dto.getResponsibilityDept());
            
            sqlArgs.add(dto.getPeriodName());
            sqlArgs.add(dto.getYear() + "-" + dto.getMonth() + "-01");
            sqlArgs.add(dto.getYear() + "-" + dto.getMonth() + "-01");
            sqlArgs.add(userAccount.getOrganizationId());
        } else if (userAccount.isDptAssetsManager()) {//按部门资产构成分布(部门资产管理员)
            sqlStr = "SELECT TOTAL.COMPANY,\n" +
                    "       TOTAL.DEPT_NAME,\n" +

                    "       TOTAL.SUM_COST,\n" +
			        "       TOTAL.DEPRN_RESERVE,\n" +
			        "       TOTAL.NET_BOOK_VALUE,\n" +
			        "		TOTAL.IMPAIRMENT_RESERVE,\n"	+
			        "       TOTAL.LIMIT_VALUE,\n" +
			        
			        "       TOTAL.SUM_COUNT,\n" +
			        "       TOTAL.PTD_DEPRN,\n" +
			        "       (DECODE(TRUNC(100 * TOTAL.SUM_COST / TOTAL2.SUM_COST_TOTAL),\n" +
			        "               0,\n" +
			        "               STR_REPLACE(ROUND(100 * TOTAL.SUM_COST / TOTAL2.SUM_COST_TOTAL, 3),\n" +
			        "                       '.',\n" +
			        "                       '0.'),\n" +
			        "               TO_CHAR(ROUND(100 * TOTAL.SUM_COST / TOTAL2.SUM_COST_TOTAL, 3))) || '%') ASSETS_RATE,\n" +
			        
			        
                    "       '' LAST_YEAR_RATE\n" +
                    
                    "FROM   (SELECT AOCM.COMPANY COMPANY,\n" +
                    "               AMD.DEPT_NAME DEPT_NAME,\n" +
                    "               COUNT(EFA.CURRENT_UNITS) SUM_COUNT,\n" +
			        "               SUM(ZFDD.COST) SUM_COST,\n" +
			        "               SUM(ZFDD.COST - ZFDD.DEPRN_RESERVE) NET_BOOK_VALUE,\n" +
			        "               SUM(ZFDD.COST - ZFDD.DEPRN_RESERVE - ZFDD.IMPAIRMENT_RESERVE) LIMIT_VALUE,\n" +
			        "               SUM(ZFDD.DEPRN_RESERVE) DEPRN_RESERVE,\n" +
			        "				SUM(ZFDD.IMPAIRMENT_RESERVE) IMPAIRMENT_RESERVE,\n"	+
			        "               SUM(ZFDD.PTD_DEPRN) PTD_DEPRN\n" +
                    "        FROM   ETS_ITEM_INFO   EII,\n" +
                    "               AMS_MIS_DEPT    AMD,\n" +
                    "               ETS_FA_ASSETS   EFA,\n" +
                    "               ETS_OU_CITY_MAP AOCM,\n" +
                    "               ETS_ITEM_MATCH  EIM,\n" +
                    "               SOA.ZTE_FA_DEPRN_DETAIL ZFDD,\n" +
			        "				AMS_ASSETS_TRANS_HEADER AATH,\n"	+
			        "				AMS_ASSETS_TRANS_LINE   AATL \n"	+
                    "        WHERE  EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE\n" +
                    "               AND EII.SYSTEMID = EIM.SYSTEMID\n" +
                    "               AND EFA.ASSET_ID = EIM.ASSET_ID\n" +
                    "               AND EII.ITEM_STATUS = 'TO_DISCARD'\n" +
                    "               AND AOCM.ORGANIZATION_ID = EII.ORGANIZATION_ID\n" +
                    "               AND EFA.ASSET_ID *= ZFDD.ASSET_ID\n"	+
			        "				AND AATH.TRANS_ID = AATL.TRANS_ID"	+
			        "				AND AATL.BARCODE = EII.BARCODE "		+
			        "               AND ZFDD.PERIOD_NAME = ?\n" +
			        "               AND AATH.LAST_UPDATE_DATE BETWEEN\n" +
	                "               TRUNC(TO_DATE(?, 'YYYY-MM-DD'), 'MM') AND\n" +
	                "               TRUNC(LAST_DAY(TO_DATE(?, 'YYYY-MM-DD')) + 1)\n" +
                    "               AND AOCM.ORGANIZATION_ID = ?\n" +
                    "               AND\n" +
                    "               EII.RESPONSIBILITY_DEPT IN " + deptCodes + "\n" +
                    "        GROUP  BY AOCM.COMPANY,\n" +
                    "                  AMD.DEPT_NAME\n" +
                    "        \n" +
                    "        UNION\n" +
                    "        SELECT AOCM.COMPANY COMPANY,\n" +
                    "               AMD.DEPT_NAME DEPT_NAME,\n" +
                    "               COUNT(EFA.CURRENT_UNITS),\n" +
			        "               SUM(ZFDD.COST) SUM_COST,\n" +
			        "               SUM(ZFDD.COST - ZFDD.DEPRN_RESERVE) NET_BOOK_VALUE,\n" +
			        "               SUM(ZFDD.COST - ZFDD.DEPRN_RESERVE - ZFDD.IMPAIRMENT_RESERVE) LIMIT_VALUE,\n" +
			        "               SUM(ZFDD.DEPRN_RESERVE) DEPRN_RESERVE,\n" +
			        "				SUM(ZFDD.IMPAIRMENT_RESERVE) IMPAIRMENT_RESERVE,\n"	+
			        "               SUM(ZFDD.PTD_DEPRN) PTD_DEPRN\n" +
                    "        FROM   ETS_ITEM_INFO   EII,\n" +
                    "               AMS_MIS_DEPT    AMD,\n" +
                    "               ETS_FA_ASSETS   EFA,\n" +
                    "               ETS_OU_CITY_MAP AOCM,\n" +
                    "               ETS_ITEM_MATCH  EIM,\n" +
                    "               SOA.ZTE_FA_DEPRN_DETAIL ZFDD,\n" +
			        "				AMS_ASSETS_TRANS_HEADER AATH,\n"	+
			        "				AMS_ASSETS_TRANS_LINE   AATL \n"	+
                    "        WHERE  EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE\n" +
                    "               AND EII.SYSTEMID = EIM.SYSTEMID\n" +
                    "               AND EFA.ASSET_ID = EIM.ASSET_ID\n" +
                    "               AND AOCM.ORGANIZATION_ID = EII.ORGANIZATION_ID\n" +
                    "               AND EII.ITEM_STATUS = 'TO_DISCARD'\n" +
                    "               AND EFA.ASSET_ID *= ZFDD.ASSET_ID\n"	+
			        "				AND AATH.TRANS_ID = AATL.TRANS_ID"	+
			        "				AND AATL.BARCODE = EII.BARCODE "		+
			        "               AND ZFDD.PERIOD_NAME = ?\n" +
			        "               AND AATH.LAST_UPDATE_DATE BETWEEN\n" +
	                "               TRUNC(TO_DATE(?, 'YYYY-MM-DD'), 'MM') AND\n" +
	                "               TRUNC(LAST_DAY(TO_DATE(?, 'YYYY-MM-DD')) + 1)\n" +
                    "               AND AOCM.ORGANIZATION_ID = ?\n" +
                    "               AND\n" +
                    "               EII.RESPONSIBILITY_DEPT IN " + deptCodes + "\n" +
                    "        GROUP  BY AOCM.COMPANY,\n" +
                    "                  AMD.DEPT_NAME) TOTAL,\n" +
                    "       (SELECT SUM(ZFDD.COST) SUM_COST_TOTAL\n" +
                    "        FROM   ETS_FA_ASSETS  EFA,\n" +
                    "               ETS_ITEM_INFO  EII,\n" +
                    "               ETS_ITEM_MATCH EIM,\n" +
                    "               SOA.ZTE_FA_DEPRN_DETAIL ZFDD,\n" +
			        "				AMS_ASSETS_TRANS_HEADER AATH,\n"	+
			        "				AMS_ASSETS_TRANS_LINE   AATL \n"	+
                    "        WHERE  EII.SYSTEMID = EIM.SYSTEMID\n" +
                    "               AND EFA.ASSET_ID = EIM.ASSET_ID\n" +
                    "               AND EII.ITEM_STATUS = 'TO_DISCARD'\n" +
                    "               AND EFA.ASSET_ID *= ZFDD.ASSET_ID\n"	+
			        "				AND AATH.TRANS_ID = AATL.TRANS_ID"	+
			        "				AND AATL.BARCODE = EII.BARCODE "		+
			        "               AND ZFDD.PERIOD_NAME = ?\n" +
			        "               AND AATH.LAST_UPDATE_DATE BETWEEN\n" +
	                "               TRUNC(TO_DATE(?, 'YYYY-MM-DD'), 'MM') AND\n" +
	                "               TRUNC(LAST_DAY(TO_DATE(?, 'YYYY-MM-DD')) + 1)\n" +
                    "               AND EII.ORGANIZATION_ID = ?) TOTAL2\n" +
                    "ORDER  BY TOTAL.COMPANY,\n" +
                    "          TOTAL.DEPT_NAME DESC";
            sqlArgs.add(dto.getPeriodName());
            sqlArgs.add(dto.getYear() + "-" + dto.getMonth() + "-01");
            sqlArgs.add(dto.getYear() + "-" + dto.getMonth() + "-01");
            sqlArgs.add(userAccount.getOrganizationId());
            
            sqlArgs.add(dto.getPeriodName());
            sqlArgs.add(dto.getYear() + "-" + dto.getMonth() + "-01");
            sqlArgs.add(dto.getYear() + "-" + dto.getMonth() + "-01");
            sqlArgs.add(userAccount.getOrganizationId());
            
            sqlArgs.add(dto.getPeriodName());
            sqlArgs.add(dto.getYear() + "-" + dto.getMonth() + "-01");
            sqlArgs.add(dto.getYear() + "-" + dto.getMonth() + "-01");
            sqlArgs.add(userAccount.getOrganizationId());
        }*/
        sqlStr = "SELECT TOTAL.COMPANY,\n" +
        "       TOTAL.DEPT_NAME,\n" +
        "       TOTAL.SUM_COUNT,\n" +
        "       TOTAL.SUM_COST,\n" +
        "       TOTAL.DEPRN_RESERVE,\n" +
        "       TOTAL.NET_BOOK_VALUE,\n" +
        "       TOTAL.IMPAIRMENT_RESERVE,\n" +
        "       TOTAL.LIMIT_VALUE,\n" +
        "       TOTAL.PTD_DEPRN,\n" +
        "       (DECODE(TRUNC(100 * TOTAL.SUM_COST / SUM_COST.TOTAL),\n" +
        "               0,\n" +
        "               STR_REPLACE(ROUND(100 * TOTAL.SUM_COST / SUM_COST.TOTAL, 3),\n" +
        "                       '.',\n" +
        "                       '0.'),\n" +
        "               TO_CHAR(ROUND(100 * TOTAL.SUM_COST / SUM_COST.TOTAL, 3))) || '%') ASSETS_RATE,\n" +
        "               \n" +
        "       (DECODE(SUM_LAST_YEAR_COST.SUM_COST, NULL , '0', DECODE(TRUNC(100 * TOTAL.SUM_COST / SUM_LAST_YEAR_COST.SUM_COST - 100),\n" +
            "               0,\n" +
            "               STR_REPLACE(ROUND(100 * TOTAL.SUM_COST / SUM_LAST_YEAR_COST.SUM_COST - 100, 3),\n" +
            "                       '.',\n" +
            "                       '0.'),\n" +
            "               TO_CHAR(ROUND(100 * TOTAL.SUM_COST / SUM_LAST_YEAR_COST.SUM_COST - 100, 3)))) || '%') LAST_YEAR_RATE,\n" +
            "       \n" +
            "       (DECODE(SUM_LAST_FOUR_YEAR_COST.SUM_COST, NULL , '0', DECODE(TRUNC(100 * SUM_LAST_THREE_YEAR_COST.SUM_COST / SUM_LAST_FOUR_YEAR_COST.SUM_COST - 100),\n" +
            "               0,\n" +
            "               STR_REPLACE(ROUND(100 * SUM_LAST_THREE_YEAR_COST.SUM_COST / SUM_LAST_FOUR_YEAR_COST.SUM_COST - 100, 3),\n" +
            "                       '.',\n" +
            "                       '0.'),\n" +
            "               TO_CHAR(ROUND(100 * SUM_LAST_THREE_YEAR_COST.SUM_COST / SUM_LAST_FOUR_YEAR_COST.SUM_COST - 100, 3)))) || '%') THREE_YEER_THREE_RATE,\n" +
            "               \n" +
            "       (DECODE(SUM_LAST_THREE_YEAR_COST.SUM_COST, NULL , '0', DECODE(TRUNC(100 * SUM_LAST_TWO_YEAR_COST.SUM_COST / SUM_LAST_THREE_YEAR_COST.SUM_COST - 100),\n" +
            "               0,\n" +
            "               STR_REPLACE(ROUND(100 * SUM_LAST_TWO_YEAR_COST.SUM_COST / SUM_LAST_THREE_YEAR_COST.SUM_COST - 100, 3),\n" +
            "                       '.',\n" +
            "                       '0.'),\n" +
            "               TO_CHAR(ROUND(100 * SUM_LAST_TWO_YEAR_COST.SUM_COST / SUM_LAST_THREE_YEAR_COST.SUM_COST - 100, 3)))) || '%') THREE_YEER_TWO_RATE,\n" +
            "               \n" +
            "       (DECODE(SUM_LAST_TWO_YEAR_COST.SUM_COST, NULL , '0', DECODE(TRUNC(100 * SUM_LAST_ONE_YEAR_COST.SUM_COST / SUM_LAST_TWO_YEAR_COST.SUM_COST - 100),\n" +
            "               0,\n" +
            "               STR_REPLACE(ROUND(100 * SUM_LAST_ONE_YEAR_COST.SUM_COST / SUM_LAST_TWO_YEAR_COST.SUM_COST - 100, 3),\n" +
            "                       '.',\n" +
            "                       '0.'),\n" +
            "               TO_CHAR(ROUND(100 * SUM_LAST_ONE_YEAR_COST.SUM_COST / SUM_LAST_TWO_YEAR_COST.SUM_COST - 100, 3)))) || '%')  THREE_YEER_ONE_RATE\n" +
        " FROM   (SELECT SUM(EFAHR.COST) TOTAL\n" +
        "        FROM   ETS_FA_ASSETS_HIS_REP  EFAHR,\n" +
        "               ETS_ITEM_INFO  		   EII,\n" +
        "               ETS_ITEM_MATCH 		   EIM\n" +
        "        WHERE  EII.ITEM_STATUS = 'TO_DISCARD'" +
        "				AND EII.SYSTEMID = EIM.SYSTEMID\n" +
        "               AND EFAHR.ASSET_ID = EIM.ASSET_ID\n" +
        "               AND EII.FINANCE_PROP = 'ASSETS'\n" +
        "               AND EFAHR.PERIOD_NAME = ? \n" +
        "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EFAHR.ORGANIZATION_ID = ?)) SUM_COST,\n" +   

        "       (SELECT AOCM.COMPANY COMPANY,\n" +
        "               AMD.DEPT_NAME DEPT_NAME,\n" +
        "               COUNT(EII.ITEM_QTY) SUM_COUNT,\n" +
        "               SUM(EFAHR.COST) SUM_COST,\n" +
        "               SUM(EFAHR.NET_ASSET_VALUE) NET_BOOK_VALUE,\n" +
        "               SUM(EFAHR.DEPRN_COST) LIMIT_VALUE,\n" +
        "               SUM(EFAHR.IMPAIR_RESERVE) IMPAIRMENT_RESERVE,\n" +
        "               SUM(EFAHR.DEPRN_RESERVE) DEPRN_RESERVE,\n" +
        "               SUM(EFAHR.DEPRN_AMOUNT) PTD_DEPRN,\n" +
        "               AOCM.ORGANIZATION_ID,\n" +
        "               AMD.DEPT_CODE\n" +
        "        FROM   ETS_ITEM_INFO   		EII,\n" +
        "               AMS_MIS_DEPT    		AMD,\n" +
        "               ETS_FA_ASSETS_HIS_REP   EFAHR,\n" +
        "				ETS_ITEM_INFO_ATTR_CHG  EIIAC,\n"	+
        "               ETS_OU_CITY_MAP 		AOCM,\n" +
        "               ETS_ITEM_MATCH  		EIM\n" +
        "        WHERE  EII.ITEM_STATUS = 'TO_DISCARD' \n" +
        "				AND EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE\n" +
        "               AND EII.SYSTEMID = EIM.SYSTEMID\n" +
        "				AND EFAHR.TAG_NUMBER = EIIAC.BAR_CODE\n"	+
        "               AND EFAHR.ASSET_ID = EIM.ASSET_ID\n" +
        "               AND AOCM.ORGANIZATION_ID = EFAHR.ORGANIZATION_ID\n" +
        "               AND EII.FINANCE_PROP = 'ASSETS'\n" +
        "				AND EIIAC.PERIOD_NAME = EFAHR.PERIOD_NAME"	+
        "               AND EFAHR.PERIOD_NAME = ? \n" +
        "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EFAHR.ORGANIZATION_ID = ?)"	+
        "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.RESPONSIBILITY_DEPT = ?)\n" +
        "               AND EIIAC.CREATION_DATE BETWEEN\n" +
        "               TRUNC(TO_DATE(?, 'YYYY-MM-DD'), 'MM') AND\n" +
        "               TRUNC(LAST_DAY(TO_DATE(?, 'YYYY-MM-DD')) + 1)\n" +
        "        GROUP  BY AOCM.COMPANY,\n" +
        "                  AMD.DEPT_NAME,\n" +
        "                  AOCM.ORGANIZATION_ID,\n" +
        "                  AMD.DEPT_CODE\n" +
        "        \n" +
        "        UNION\n" +
        "        SELECT AOCM.COMPANY COMPANY,\n" +
        "               '' DEPT_NAME,\n" +
        "               COUNT(EII.ITEM_QTY) SUM_COUNT,\n" +
        "				SUM(EFAHR.COST) SUM_COST,\n" +
        "               SUM(EFAHR.NET_ASSET_VALUE) NET_BOOK_VALUE,\n" +
        "               SUM(EFAHR.DEPRN_COST) LIMIT_VALUE,\n" +
        "               SUM(EFAHR.IMPAIR_RESERVE) IMPAIRMENT_RESERVE,\n" +
        "               SUM(EFAHR.DEPRN_RESERVE) DEPRN_RESERVE,\n" +
        "               SUM(EFAHR.DEPRN_AMOUNT) PTD_DEPRN,\n" +
        "               AOCM.ORGANIZATION_ID,\n" +
        "               '0' DEPT_CODE\n" +
        "        FROM   ETS_ITEM_INFO   EII,\n" +
        "               AMS_MIS_DEPT    AMD,\n" +
        "               ETS_FA_ASSETS_HIS_REP   EFAHR,\n" +
        "				ETS_ITEM_INFO_ATTR_CHG  EIIAC,\n"	+
        "               ETS_OU_CITY_MAP AOCM,\n" +
        "               ETS_ITEM_MATCH  EIM\n" +
        "        WHERE  EII.ITEM_STATUS = 'TO_DISCARD' \n" +
        "				AND EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE\n" +
        "               AND EII.SYSTEMID = EIM.SYSTEMID\n" +
        "				AND EFAHR.TAG_NUMBER = EIIAC.BAR_CODE\n"	+
        "               AND EFAHR.ASSET_ID = EIM.ASSET_ID\n" +
        "               AND AOCM.ORGANIZATION_ID = EFAHR.ORGANIZATION_ID\n" +
        "               AND EII.FINANCE_PROP = 'ASSETS'\n" +
        "				AND EIIAC.PERIOD_NAME = EFAHR.PERIOD_NAME"	+
        "               AND EFAHR.PERIOD_NAME = ? \n" +
        "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EFAHR.ORGANIZATION_ID = ?)"	+
        "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.RESPONSIBILITY_DEPT = ?)\n" +
        "               AND EIIAC.CREATION_DATE BETWEEN\n" +
        "               TRUNC(TO_DATE(?, 'YYYY-MM-DD'), 'MM') AND\n" +
        "               TRUNC(LAST_DAY(TO_DATE(?, 'YYYY-MM-DD')) + 1)\n" +
        "        GROUP  BY AOCM.COMPANY, AOCM.ORGANIZATION_ID) TOTAL \n" +
        "  LEFT JOIN \n" +
        "       (SELECT AOCM.COMPANY COMPANY,\n" +
        "               AMD.DEPT_NAME DEPT_NAME,\n" +
        "               COUNT(EII.ITEM_QTY) SUM_COUNT,\n" +
        "               SUM(EFAHR.COST) SUM_COST,\n" +
        "               AOCM.ORGANIZATION_ID,\n" +
        "               AMD.DEPT_CODE\n" +
        "        FROM   ETS_ITEM_INFO   		EII,\n" +
        "               AMS_MIS_DEPT    		AMD,\n" +
        "               ETS_FA_ASSETS_HIS_REP   EFAHR,\n" +
        "				ETS_ITEM_INFO_ATTR_CHG  EIIAC,\n"	+
        "               ETS_OU_CITY_MAP 		AOCM,\n" +
        "               ETS_ITEM_MATCH  		EIM\n" +
        "        WHERE  EII.ITEM_STATUS = 'TO_DISCARD' \n" +
        "				AND EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE\n" +
        "               AND EII.SYSTEMID = EIM.SYSTEMID\n" +
        "				AND EFAHR.TAG_NUMBER = EIIAC.BAR_CODE\n"	+
        "				AND EIIAC.PERIOD_NAME = EFAHR.PERIOD_NAME"	+
        "               AND EFAHR.ASSET_ID = EIM.ASSET_ID\n" +
        "               AND AOCM.ORGANIZATION_ID = EFAHR.ORGANIZATION_ID\n" +
        "               AND EII.FINANCE_PROP = 'ASSETS'\n" +
        "               AND EFAHR.PERIOD_NAME = ? \n" +
        "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EFAHR.ORGANIZATION_ID = ?)"	+
        "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.RESPONSIBILITY_DEPT = ?)\n" +
        "               AND EIIAC.CREATION_DATE BETWEEN\n" +
        "               TRUNC(TO_DATE(?, 'YYYY-MM-DD'), 'MM') AND\n" +
        "               TRUNC(LAST_DAY(TO_DATE(?, 'YYYY-MM-DD')) + 1)\n" +
        "        GROUP  BY AOCM.COMPANY,\n" +
        "                  AMD.DEPT_NAME,\n" +
        "                  AOCM.ORGANIZATION_ID,\n" +
        "                  AMD.DEPT_CODE\n" +
        "        UNION\n" +
        "        SELECT AOCM.COMPANY COMPANY,\n" +
        "               '' DEPT_NAME,\n" +
        "               COUNT(EII.ITEM_QTY) SUM_COUNT,\n" +
        "               SUM(EFAHR.COST) SUM_COST,\n" +
        "               AOCM.ORGANIZATION_ID,\n" +
        "               '0' DEPT_CODE\n" +
        "        FROM   ETS_ITEM_INFO   		EII,\n" +
        "               AMS_MIS_DEPT    		AMD,\n" +
        "               ETS_FA_ASSETS_HIS_REP   EFAHR,\n" +
        "				ETS_ITEM_INFO_ATTR_CHG  EIIAC,\n"	+
        "               ETS_OU_CITY_MAP 		AOCM,\n" +
        "               ETS_ITEM_MATCH  		EIM\n" +
        "        WHERE  EII.ITEM_STATUS = 'TO_DISCARD' \n" +
        "				AND EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE\n" +
        "               AND EII.SYSTEMID = EIM.SYSTEMID\n" +
        "				AND EFAHR.TAG_NUMBER = EIIAC.BAR_CODE\n"	+
        "				AND EIIAC.PERIOD_NAME = EFAHR.PERIOD_NAME"	+
        "               AND EFAHR.ASSET_ID = EIM.ASSET_ID\n" +
        "               AND AOCM.ORGANIZATION_ID = EFAHR.ORGANIZATION_ID\n" +
        "               AND EII.FINANCE_PROP = 'ASSETS'\n" +
        "               AND EFAHR.PERIOD_NAME = ? \n" +
        "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EFAHR.ORGANIZATION_ID = ?)"	+
        "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.RESPONSIBILITY_DEPT = ?)\n" +
        "               AND EIIAC.CREATION_DATE BETWEEN\n" +
        "               TRUNC(TO_DATE(?, 'YYYY-MM-DD'), 'MM') AND\n" +
        "               TRUNC(LAST_DAY(TO_DATE(?, 'YYYY-MM-DD')) + 1)\n" +
        "        GROUP  BY AOCM.COMPANY, AOCM.ORGANIZATION_ID) SUM_LAST_YEAR_COST\n" +
        "  ON TOTAL.ORGANIZATION_ID = SUM_LAST_YEAR_COST.ORGANIZATION_ID\n" +
        "  AND TOTAL.DEPT_CODE = SUM_LAST_YEAR_COST.DEPT_CODE\n" +
        
        "  LEFT JOIN \n" +
        "       (SELECT COUNT(EII.ITEM_QTY) SUM_COUNT,\n" +
        "               SUM(EFAHR.COST) SUM_COST,\n" +
        "               AOCM.ORGANIZATION_ID,\n" +
        "               AMD.DEPT_CODE\n" +
        "        FROM   ETS_ITEM_INFO   		EII,\n" +
        "               AMS_MIS_DEPT    		AMD,\n" +
        "               ETS_FA_ASSETS_HIS_REP   EFAHR,\n" +
        "				ETS_ITEM_INFO_ATTR_CHG  EIIAC,\n"	+
        "               ETS_OU_CITY_MAP 		AOCM,\n" +
        "               ETS_ITEM_MATCH  		EIM\n" +
        "        WHERE  EII.ITEM_STATUS = 'TO_DISCARD' \n" +
        "				AND EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE\n" +
        "               AND EII.SYSTEMID = EIM.SYSTEMID\n" +
        "				AND EFAHR.TAG_NUMBER = EIIAC.BAR_CODE\n"	+
        "				AND EIIAC.PERIOD_NAME = EFAHR.PERIOD_NAME"	+
        "               AND EFAHR.ASSET_ID = EIM.ASSET_ID\n" +
        "               AND AOCM.ORGANIZATION_ID = EFAHR.ORGANIZATION_ID\n" +
        "               AND EII.FINANCE_PROP = 'ASSETS'\n" +
        "               AND EFAHR.PERIOD_NAME = ? \n" +
        "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EFAHR.ORGANIZATION_ID = ?)"	+
        "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.RESPONSIBILITY_DEPT = ?)\n" +
        "				AND EIIAC.CREATION_DATE >= TO_DATE(?, 'YY-MM-DD')"	+
        "				AND EIIAC.CREATION_DATE < TO_DATE(?, 'YY-MM-DD')"	+
        "        GROUP  BY AOCM.ORGANIZATION_ID,\n" +
        "                  AMD.DEPT_CODE\n" +
        "        UNION\n" +
        "        SELECT COUNT(EII.ITEM_QTY) SUM_COUNT,\n" +
        "               SUM(EFAHR.COST) SUM_COST,\n" +
        "               AOCM.ORGANIZATION_ID,\n" +
        "               '0' DEPT_CODE\n" +
        "        FROM   ETS_ITEM_INFO   		EII,\n" +
        "               AMS_MIS_DEPT    		AMD,\n" +
        "               ETS_FA_ASSETS_HIS_REP   EFAHR,\n" +
        "				ETS_ITEM_INFO_ATTR_CHG  EIIAC,\n"	+
        "               ETS_OU_CITY_MAP 		AOCM,\n" +
        "               ETS_ITEM_MATCH  		EIM\n" +
        "        WHERE  EII.ITEM_STATUS = 'TO_DISCARD' \n" +
        "				AND EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE\n" +
        "               AND EII.SYSTEMID = EIM.SYSTEMID\n" +
        "				AND EFAHR.TAG_NUMBER = EIIAC.BAR_CODE\n"	+
        "				AND EIIAC.PERIOD_NAME = EFAHR.PERIOD_NAME"	+
        "               AND EFAHR.ASSET_ID = EIM.ASSET_ID\n" +
        "               AND AOCM.ORGANIZATION_ID = EFAHR.ORGANIZATION_ID\n" +
        "               AND EII.FINANCE_PROP = 'ASSETS'\n" +
        "               AND EFAHR.PERIOD_NAME = ? \n" +
        "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EFAHR.ORGANIZATION_ID = ?)"	+
        "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.RESPONSIBILITY_DEPT = ?)\n" +
        "				AND EIIAC.CREATION_DATE >= TO_DATE(?, 'YY-MM-DD')"	+
        "				AND EIIAC.CREATION_DATE < TO_DATE(?, 'YY-MM-DD')"	+
        "        GROUP  BY AOCM.COMPANY, AOCM.ORGANIZATION_ID) SUM_LAST_FOUR_YEAR_COST\n" +
        "  ON SUM_LAST_YEAR_COST.ORGANIZATION_ID = SUM_LAST_FOUR_YEAR_COST.ORGANIZATION_ID\n" +
        "  AND SUM_LAST_YEAR_COST.DEPT_CODE = SUM_LAST_FOUR_YEAR_COST.DEPT_CODE\n" +
        
        "  LEFT JOIN \n" +
        "       (SELECT COUNT(EII.ITEM_QTY) SUM_COUNT,\n" +
        "               SUM(EFAHR.COST) SUM_COST,\n" +
        "               AOCM.ORGANIZATION_ID,\n" +
        "               AMD.DEPT_CODE\n" +
        "        FROM   ETS_ITEM_INFO   		EII,\n" +
        "               AMS_MIS_DEPT    		AMD,\n" +
        "               ETS_FA_ASSETS_HIS_REP   EFAHR,\n" +
        "				ETS_ITEM_INFO_ATTR_CHG  EIIAC,\n"	+
        "               ETS_OU_CITY_MAP 		AOCM,\n" +
        "               ETS_ITEM_MATCH  		EIM\n" +
        "        WHERE  EII.ITEM_STATUS = 'TO_DISCARD' \n" +
        "				AND EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE\n" +
        "               AND EII.SYSTEMID = EIM.SYSTEMID\n" +
        "				AND EFAHR.TAG_NUMBER = EIIAC.BAR_CODE\n"	+
        "				AND EIIAC.PERIOD_NAME = EFAHR.PERIOD_NAME"	+
        "               AND EFAHR.ASSET_ID = EIM.ASSET_ID\n" +
        "               AND AOCM.ORGANIZATION_ID = EFAHR.ORGANIZATION_ID\n" +
        "               AND EII.FINANCE_PROP = 'ASSETS'\n" +
        "               AND EFAHR.PERIOD_NAME = ? \n" +
        "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EFAHR.ORGANIZATION_ID = ?)"	+
        "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.RESPONSIBILITY_DEPT = ?)\n" +
        "				AND EIIAC.CREATION_DATE >= TO_DATE(?, 'YY-MM-DD')"	+
        "				AND EIIAC.CREATION_DATE < TO_DATE(?, 'YY-MM-DD')"	+
        "        GROUP  BY AOCM.ORGANIZATION_ID,\n" +
        "                  AMD.DEPT_CODE\n" +
        "        UNION\n" +
        "        SELECT COUNT(EII.ITEM_QTY) SUM_COUNT,\n" +
        "               SUM(EFAHR.COST) SUM_COST,\n" +
        "               AOCM.ORGANIZATION_ID,\n" +
        "               '0' DEPT_CODE\n" +
        "        FROM   ETS_ITEM_INFO   		EII,\n" +
        "               AMS_MIS_DEPT    		AMD,\n" +
        "               ETS_FA_ASSETS_HIS_REP   EFAHR,\n" +
        "				ETS_ITEM_INFO_ATTR_CHG  EIIAC,\n"	+
        "               ETS_OU_CITY_MAP 		AOCM,\n" +
        "               ETS_ITEM_MATCH  		EIM\n" +
        "        WHERE  EII.ITEM_STATUS = 'TO_DISCARD' \n" +
        "				AND EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE\n" +
        "               AND EII.SYSTEMID = EIM.SYSTEMID\n" +
        "				AND EFAHR.TAG_NUMBER = EIIAC.BAR_CODE\n"	+
        "				AND EIIAC.PERIOD_NAME = EFAHR.PERIOD_NAME"	+
        "               AND EFAHR.ASSET_ID = EIM.ASSET_ID\n" +
        "               AND AOCM.ORGANIZATION_ID = EFAHR.ORGANIZATION_ID\n" +
        "               AND EII.FINANCE_PROP = 'ASSETS'\n" +
        "               AND EFAHR.PERIOD_NAME = ? \n" +
        "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EFAHR.ORGANIZATION_ID = ?)"	+
        "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.RESPONSIBILITY_DEPT = ?)\n" +
        "				AND EIIAC.CREATION_DATE >= TO_DATE(?, 'YY-MM-DD')"	+
        "				AND EIIAC.CREATION_DATE < TO_DATE(?, 'YY-MM-DD')"	+
        "        GROUP  BY AOCM.COMPANY, AOCM.ORGANIZATION_ID) SUM_LAST_THREE_YEAR_COST\n" +
        "  ON SUM_LAST_FOUR_YEAR_COST.ORGANIZATION_ID = SUM_LAST_THREE_YEAR_COST.ORGANIZATION_ID\n" +
        "  AND SUM_LAST_FOUR_YEAR_COST.DEPT_CODE = SUM_LAST_THREE_YEAR_COST.DEPT_CODE\n" +
        
        "  LEFT JOIN \n" +
        "       (SELECT COUNT(EII.ITEM_QTY) SUM_COUNT,\n" +
        "               SUM(EFAHR.COST) SUM_COST,\n" +
        "               AOCM.ORGANIZATION_ID,\n" +
        "               AMD.DEPT_CODE\n" +
        "        FROM   ETS_ITEM_INFO   		EII,\n" +
        "               AMS_MIS_DEPT    		AMD,\n" +
        "               ETS_FA_ASSETS_HIS_REP   EFAHR,\n" +
        "				ETS_ITEM_INFO_ATTR_CHG  EIIAC,\n"	+
        "               ETS_OU_CITY_MAP 		AOCM,\n" +
        "               ETS_ITEM_MATCH  		EIM\n" +
        "        WHERE  EII.ITEM_STATUS = 'TO_DISCARD' \n" +
        "				AND EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE\n" +
        "               AND EII.SYSTEMID = EIM.SYSTEMID\n" +
        "				AND EFAHR.TAG_NUMBER = EIIAC.BAR_CODE\n"	+
        "				AND EIIAC.PERIOD_NAME = EFAHR.PERIOD_NAME"	+
        "               AND EFAHR.ASSET_ID = EIM.ASSET_ID\n" +
        "               AND AOCM.ORGANIZATION_ID = EFAHR.ORGANIZATION_ID\n" +
        "               AND EII.FINANCE_PROP = 'ASSETS'\n" +
        "               AND EFAHR.PERIOD_NAME = ? \n" +
        "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EFAHR.ORGANIZATION_ID = ?)"	+
        "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.RESPONSIBILITY_DEPT = ?)\n" +
        "				AND EIIAC.CREATION_DATE >= TO_DATE(?, 'YY-MM-DD')"	+
        "				AND EIIAC.CREATION_DATE < TO_DATE(?, 'YY-MM-DD')"	+
        "        GROUP  BY AOCM.ORGANIZATION_ID,\n" +
        "                  AMD.DEPT_CODE\n" +
        "        UNION\n" +
        "        SELECT COUNT(EII.ITEM_QTY) SUM_COUNT,\n" +
        "               SUM(EFAHR.COST) SUM_COST,\n" +
        "               AOCM.ORGANIZATION_ID,\n" +
        "               '0' DEPT_CODE\n" +
        "        FROM   ETS_ITEM_INFO   		EII,\n" +
        "               AMS_MIS_DEPT    		AMD,\n" +
        "               ETS_FA_ASSETS_HIS_REP   EFAHR,\n" +
        "				ETS_ITEM_INFO_ATTR_CHG  EIIAC,\n"	+
        "               ETS_OU_CITY_MAP 		AOCM,\n" +
        "               ETS_ITEM_MATCH  		EIM\n" +
        "        WHERE  EII.ITEM_STATUS = 'TO_DISCARD' \n" +
        "				AND EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE\n" +
        "               AND EII.SYSTEMID = EIM.SYSTEMID\n" +
        "				AND EFAHR.TAG_NUMBER = EIIAC.BAR_CODE\n"	+
        "				AND EIIAC.PERIOD_NAME = EFAHR.PERIOD_NAME"	+
        "               AND EFAHR.ASSET_ID = EIM.ASSET_ID\n" +
        "               AND AOCM.ORGANIZATION_ID = EFAHR.ORGANIZATION_ID\n" +
        "               AND EII.FINANCE_PROP = 'ASSETS'\n" +
        "               AND EFAHR.PERIOD_NAME = ? \n" +
        "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EFAHR.ORGANIZATION_ID = ?)"	+
        "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.RESPONSIBILITY_DEPT = ?)\n" +
        "				AND EIIAC.CREATION_DATE >= TO_DATE(?, 'YY-MM-DD')"	+
        "				AND EIIAC.CREATION_DATE < TO_DATE(?, 'YY-MM-DD')"	+
        "        GROUP  BY AOCM.COMPANY, AOCM.ORGANIZATION_ID) SUM_LAST_TWO_YEAR_COST\n" +
        "  ON SUM_LAST_THREE_YEAR_COST.ORGANIZATION_ID = SUM_LAST_TWO_YEAR_COST.ORGANIZATION_ID\n" +
        "  AND SUM_LAST_THREE_YEAR_COST.DEPT_CODE = SUM_LAST_TWO_YEAR_COST.DEPT_CODE\n" +
        
        "  LEFT JOIN \n" +
        "       (SELECT COUNT(EII.ITEM_QTY) SUM_COUNT,\n" +
        "               SUM(EFAHR.COST) SUM_COST,\n" +
        "               AOCM.ORGANIZATION_ID,\n" +
        "               AMD.DEPT_CODE\n" +
        "        FROM   ETS_ITEM_INFO   		EII,\n" +
        "               AMS_MIS_DEPT    		AMD,\n" +
        "               ETS_FA_ASSETS_HIS_REP   EFAHR,\n" +
        "				ETS_ITEM_INFO_ATTR_CHG  EIIAC,\n"	+
        "               ETS_OU_CITY_MAP 		AOCM,\n" +
        "               ETS_ITEM_MATCH  		EIM\n" +
        "        WHERE  EII.ITEM_STATUS = 'TO_DISCARD' \n" +
        "				AND EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE\n" +
        "               AND EII.SYSTEMID = EIM.SYSTEMID\n" +
        "				AND EFAHR.TAG_NUMBER = EIIAC.BAR_CODE\n"	+
        "				AND EIIAC.PERIOD_NAME = EFAHR.PERIOD_NAME"	+
        "               AND EFAHR.ASSET_ID = EIM.ASSET_ID\n" +
        "               AND AOCM.ORGANIZATION_ID = EFAHR.ORGANIZATION_ID\n" +
        "               AND EII.FINANCE_PROP = 'ASSETS'\n" +
        "               AND EFAHR.PERIOD_NAME = ? \n" +
        "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EFAHR.ORGANIZATION_ID = ?)"	+
        "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.RESPONSIBILITY_DEPT = ?)\n" +
        "				AND EIIAC.CREATION_DATE >= TO_DATE(?, 'YY-MM-DD')"	+
        "				AND EIIAC.CREATION_DATE < TO_DATE(?, 'YY-MM-DD')"	+
        "        GROUP  BY AOCM.ORGANIZATION_ID,\n" +
        "                  AMD.DEPT_CODE\n" +
        "        UNION\n" +
        "        SELECT COUNT(EII.ITEM_QTY) SUM_COUNT,\n" +
        "               SUM(EFAHR.COST) SUM_COST,\n" +
        "               AOCM.ORGANIZATION_ID,\n" +
        "               '0' DEPT_CODE\n" +
        "        FROM   ETS_ITEM_INFO   		EII,\n" +
        "               AMS_MIS_DEPT    		AMD,\n" +
        "               ETS_FA_ASSETS_HIS_REP   EFAHR,\n" +
        "				ETS_ITEM_INFO_ATTR_CHG  EIIAC,\n"	+
        "               ETS_OU_CITY_MAP 		AOCM,\n" +
        "               ETS_ITEM_MATCH  		EIM\n" +
        "        WHERE  EII.ITEM_STATUS = 'TO_DISCARD' \n" +
        "				AND EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE\n" +
        "               AND EII.SYSTEMID = EIM.SYSTEMID\n" +
        "				AND EFAHR.TAG_NUMBER = EIIAC.BAR_CODE\n"	+
        "				AND EIIAC.PERIOD_NAME = EFAHR.PERIOD_NAME"	+
        "               AND EFAHR.ASSET_ID = EIM.ASSET_ID\n" +
        "               AND AOCM.ORGANIZATION_ID = EFAHR.ORGANIZATION_ID\n" +
        "               AND EII.FINANCE_PROP = 'ASSETS'\n" +
        "               AND EFAHR.PERIOD_NAME = ? \n" +
        "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EFAHR.ORGANIZATION_ID = ?)"	+
        "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.RESPONSIBILITY_DEPT = ?)\n" +
        "				AND EIIAC.CREATION_DATE >= TO_DATE(?, 'YY-MM-DD')"	+
        "				AND EIIAC.CREATION_DATE < TO_DATE(?, 'YY-MM-DD')"	+
        "        GROUP  BY AOCM.COMPANY, AOCM.ORGANIZATION_ID) SUM_LAST_ONE_YEAR_COST\n" +
        "  ON SUM_LAST_TWO_YEAR_COST.ORGANIZATION_ID = SUM_LAST_ONE_YEAR_COST.ORGANIZATION_ID\n" +
        "  AND SUM_LAST_TWO_YEAR_COST.DEPT_CODE = SUM_LAST_ONE_YEAR_COST.DEPT_CODE\n" +
        " ORDER  BY TOTAL.COMPANY,\n" +
        "          TOTAL.DEPT_NAME DESC";
    	
         sqlArgs.add(dto.getPeriodNameByHisRep());
         sqlArgs.add(dto.getOrganizationId());
         sqlArgs.add(dto.getOrganizationId());
         
         sqlArgs.add(dto.getPeriodNameByHisRep());
         sqlArgs.add(dto.getOrganizationId());
         sqlArgs.add(dto.getOrganizationId());
         sqlArgs.add(dto.getResponsibilityDept());
         sqlArgs.add(dto.getResponsibilityDept());
         sqlArgs.add(dto.getYear() + "-" + dto.getMonth() + "-01");
         sqlArgs.add(dto.getYear() + "-" + dto.getMonth() + "-01");
         
         sqlArgs.add(dto.getPeriodNameByHisRep());
         sqlArgs.add(dto.getOrganizationId());
         sqlArgs.add(dto.getOrganizationId());
         sqlArgs.add(dto.getResponsibilityDept());
         sqlArgs.add(dto.getResponsibilityDept());
         sqlArgs.add(dto.getYear() + "-" + dto.getMonth() + "-01");
         sqlArgs.add(dto.getYear() + "-" + dto.getMonth() + "-01");
         
         sqlArgs.add(dto.getLastYearPeriodNameByHisRep());
         sqlArgs.add(dto.getOrganizationId());
         sqlArgs.add(dto.getOrganizationId());
         sqlArgs.add(dto.getResponsibilityDept());
         sqlArgs.add(dto.getResponsibilityDept());
         sqlArgs.add(dto.getLastYear() + "-" + dto.getMonth() + "-01");
         sqlArgs.add(dto.getLastYear() + "-" + dto.getMonth() + "-01");
         
         sqlArgs.add(dto.getLastYearPeriodNameByHisRep());
         sqlArgs.add(dto.getOrganizationId());
         sqlArgs.add(dto.getOrganizationId());
         sqlArgs.add(dto.getResponsibilityDept());
         sqlArgs.add(dto.getResponsibilityDept());
         sqlArgs.add(dto.getLastYear() + "-" + dto.getMonth() + "-01");
         sqlArgs.add(dto.getLastYear() + "-" + dto.getMonth() + "-01");
         
         sqlArgs.add(dto.getLastFourYearPeriodNameByHisRep());
         sqlArgs.add(dto.getOrganizationId());
         sqlArgs.add(dto.getOrganizationId());
         sqlArgs.add(dto.getResponsibilityDept());
         sqlArgs.add(dto.getResponsibilityDept());
         sqlArgs.add(dto.getLastFourYear() + "-01-01");
         sqlArgs.add(dto.getLastThreeYear() + "-01-01");
         
         sqlArgs.add(dto.getLastFourYearPeriodNameByHisRep());
         sqlArgs.add(dto.getOrganizationId());
         sqlArgs.add(dto.getOrganizationId());
         sqlArgs.add(dto.getResponsibilityDept());
         sqlArgs.add(dto.getResponsibilityDept());
         sqlArgs.add(dto.getLastFourYear() + "-01-01");
         sqlArgs.add(dto.getLastThreeYear() + "-01-01");
         
         sqlArgs.add(dto.getLastThreeYearPeriodNameByHisRep());
         sqlArgs.add(dto.getOrganizationId());
         sqlArgs.add(dto.getOrganizationId());
         sqlArgs.add(dto.getResponsibilityDept());
         sqlArgs.add(dto.getResponsibilityDept());
         sqlArgs.add(dto.getLastThreeYear() + "-01-01");
         sqlArgs.add(dto.getLastTwoYear() + "-01-01");
         
         sqlArgs.add(dto.getLastThreeYearPeriodNameByHisRep());
         sqlArgs.add(dto.getOrganizationId());
         sqlArgs.add(dto.getOrganizationId());
         sqlArgs.add(dto.getResponsibilityDept());
         sqlArgs.add(dto.getResponsibilityDept());
         sqlArgs.add(dto.getLastThreeYear() + "-01-01");
         sqlArgs.add(dto.getLastTwoYear() + "-01-01");
         
         sqlArgs.add(dto.getLastTwoYearPeriodNameByHisRep());
         sqlArgs.add(dto.getOrganizationId());
         sqlArgs.add(dto.getOrganizationId());
         sqlArgs.add(dto.getResponsibilityDept());
         sqlArgs.add(dto.getResponsibilityDept());
         sqlArgs.add(dto.getLastTwoYear() + "-01-01");
         sqlArgs.add(dto.getLastYear() + "-01-01");
         
         sqlArgs.add(dto.getLastTwoYearPeriodNameByHisRep());
         sqlArgs.add(dto.getOrganizationId());
         sqlArgs.add(dto.getOrganizationId());
         sqlArgs.add(dto.getResponsibilityDept());
         sqlArgs.add(dto.getResponsibilityDept());
         sqlArgs.add(dto.getLastTwoYear() + "-01-01");
         sqlArgs.add(dto.getLastYear() + "-01-01");
         
         sqlArgs.add(dto.getLastOneYearPeriodNameByHisRep());
         sqlArgs.add(dto.getOrganizationId());
         sqlArgs.add(dto.getOrganizationId());
         sqlArgs.add(dto.getResponsibilityDept());
         sqlArgs.add(dto.getResponsibilityDept());
         sqlArgs.add(dto.getLastYear() + "-01-01");
         sqlArgs.add(dto.getYear() + "-01-01");
         
         sqlArgs.add(dto.getLastOneYearPeriodNameByHisRep());
         sqlArgs.add(dto.getOrganizationId());
         sqlArgs.add(dto.getOrganizationId());
         sqlArgs.add(dto.getResponsibilityDept());
         sqlArgs.add(dto.getResponsibilityDept());
         sqlArgs.add(dto.getLastYear() + "-01-01");
         sqlArgs.add(dto.getYear() + "-01-01");
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
