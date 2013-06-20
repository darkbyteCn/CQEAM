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
 * User: su
 * Date: 2009-3-4
 * Time: 13:29:46
 * To change this template use File | Settings | File Templates.
 */

public class TDDeptAssetsReportModel extends AMSSQLProducer {
    private String deptCodes = "";

    public TDDeptAssetsReportModel(SfUserDTO userAccount, DeptAssetsReportDTO dtoParameter) {
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
     * 功能：获取低值易耗资产构成分布统计报表SQL
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
        if (userAccount.isProvAssetsManager()) {//按部门资产构成分布(省公司)
            sqlStr = "SELECT TOTAL.COMPANY,\n" +
                    "       TOTAL.DEPT_NAME,\n" +
                    "       TOTAL.ITEM_QTY,\n" +
                    "       TOTAL.SUM_COST,\n" +
                    "       (DECODE(TRUNC(100 * TOTAL.ITEM_QTY / TOTAL2.SUM_QTY),\n" +
                    "               0,\n" +
                    "               STR_REPLACE(ROUND(100 * TOTAL.ITEM_QTY / TOTAL2.SUM_QTY, 3),\n" +
                    "                       '.',\n" +
                    "                       '0.'),\n" +
                    "               TO_CHAR(ROUND(100 * TOTAL.ITEM_QTY / TOTAL2.SUM_QTY, 3))) || '%') ASSETS_RATE\n" +
                    "FROM   (SELECT AOCM.COMPANY COMPANY,\n" +
                    "               AMD.DEPT_NAME DEPT_NAME,\n" +
                    "               COUNT(EFA.CURRENT_UNITS) ITEM_QTY,\n" +
                    "               SUM(EFA.COST) SUM_COST\n" +
                    "        FROM   ETS_ITEM_INFO   EII,\n" +
                    "               AMS_MIS_DEPT    AMD,\n" +
                    "               ETS_FA_ASSETS   EFA,\n" +
                    "               ETS_OU_CITY_MAP AOCM,\n" +
                    "               ETS_ITEM_MATCH  EIM\n" +
                    "        WHERE  EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE\n" +
                    "               AND EII.SYSTEMID = EIM.SYSTEMID\n" +
                    "               AND EFA.ASSET_ID = EIM.ASSET_ID\n" +
                    "               AND AOCM.ORGANIZATION_ID = EFA.ORGANIZATION_ID\n" +
                    "               AND EII.FINANCE_PROP = 'TD_ASSETS'\n" +
                    "               AND ( " + SyBaseSQLUtil.isNull() + "  OR AOCM.ORGANIZATION_ID = ?)\n" +
                    "        GROUP  BY AOCM.COMPANY,\n" +
                    "                  AMD.DEPT_NAME\n" +
                    "        \n" +
                    "        UNION\n" +
                    "        SELECT AOCM.COMPANY COMPANY,\n" +
                    "               '' DEPT_NAME,\n" +
                    "               COUNT(EFA.CURRENT_UNITS) ITEM_QTY,\n" +
                    "               SUM(EFA.COST) SUM_COST\n" +
                    "        FROM   ETS_ITEM_INFO   EII,\n" +
                    "               AMS_MIS_DEPT    AMD,\n" +
                    "               ETS_FA_ASSETS   EFA,\n" +
                    "               ETS_OU_CITY_MAP AOCM,\n" +
                    "               ETS_ITEM_MATCH  EIM\n" +
                    "        WHERE  EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE\n" +
                    "               AND EII.SYSTEMID = EIM.SYSTEMID\n" +
                    "               AND EFA.ASSET_ID = EIM.ASSET_ID\n" +
                    "               AND AOCM.ORGANIZATION_ID = EFA.ORGANIZATION_ID\n" +
                    "               AND EII.FINANCE_PROP = 'TD_ASSETS'\n" +
                    "               AND ( " + SyBaseSQLUtil.isNull() + "  OR AOCM.ORGANIZATION_ID = ?)\n" +
                    "        GROUP  BY AOCM.COMPANY) TOTAL,\n" +
                    "       (SELECT COUNT(EFA.CURRENT_UNITS) SUM_QTY\n" +
                    "        FROM   ETS_FA_ASSETS  EFA,\n" +
                    "               ETS_ITEM_INFO  EII,\n" +
                    "               ETS_ITEM_MATCH EIM\n" +
                    "        WHERE  EII.SYSTEMID = EIM.SYSTEMID\n" +
                    "               AND EFA.ASSET_ID = EIM.ASSET_ID\n" +
                    "               AND EII.FINANCE_PROP = 'TD_ASSETS'\n" +
                    "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.ORGANIZATION_ID = ?)) TOTAL2\n" +
                    "ORDER  BY TOTAL.COMPANY,\n" +
                    "          TOTAL.DEPT_NAME DESC";
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getOrganizationId());
        } else if (userAccount.isComAssetsManager()) {//按部门资产构成分布(地市)
            sqlStr = "SELECT TOTAL.COMPANY,\n" +
                    "       TOTAL.DEPT_NAME,\n" +
                    "       TOTAL.ITEM_QTY,\n" +
                    "       TOTAL.SUM_COST,\n" +
                    "       (DECODE(TRUNC(100 * TOTAL.ITEM_QTY / TOTAL2.SUM_QTY),\n" +
                    "               0,\n" +
                    "               STR_REPLACE(ROUND(100 * TOTAL.ITEM_QTY / TOTAL2.SUM_QTY, 3),\n" +
                    "                       '.',\n" +
                    "                       '0.'),\n" +
                    "               TO_CHAR(ROUND(100 * TOTAL.ITEM_QTY / TOTAL2.SUM_QTY, 3))) || '%') ASSETS_RATE\n" +
                    "FROM   (SELECT AOCM.COMPANY COMPANY,\n" +
                    "               AMD.DEPT_NAME DEPT_NAME,\n" +
                    "               COUNT(EFA.CURRENT_UNITS) ITEM_QTY,\n" +
                    "               SUM(EFA.COST) SUM_COST\n" +
                    "        FROM   ETS_ITEM_INFO   EII,\n" +
                    "               AMS_MIS_DEPT    AMD,\n" +
                    "               ETS_FA_ASSETS   EFA,\n" +
                    "               ETS_OU_CITY_MAP AOCM,\n" +
                    "               ETS_ITEM_MATCH  EIM\n" +
                    "        WHERE  EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE\n" +
                    "               AND EII.SYSTEMID = EIM.SYSTEMID\n" +
                    "               AND EFA.ASSET_ID = EIM.ASSET_ID\n" +
                    "               AND AOCM.ORGANIZATION_ID = EFA.ORGANIZATION_ID\n" +
                    "               AND EII.FINANCE_PROP = 'TD_ASSETS'\n" +
                    "               AND AOCM.ORGANIZATION_ID = ?\n" +
                    "               AND ( " + SyBaseSQLUtil.isNull() + "  OR AMD.DEPT_CODE LIKE ?)\n" +
                    "        GROUP  BY AOCM.COMPANY,\n" +
                    "                  AMD.DEPT_NAME\n" +
                    "        \n" +
                    "        UNION\n" +
                    "        SELECT AOCM.COMPANY COMPANY,\n" +
                    "               '' DEPT_NAME,\n" +
                    "               COUNT(EFA.CURRENT_UNITS) ITEM_QTY,\n" +
                    "               SUM(EFA.COST) SUM_COST\n" +
                    "        FROM   ETS_ITEM_INFO   EII,\n" +
                    "               AMS_MIS_DEPT    AMD,\n" +
                    "               ETS_FA_ASSETS   EFA,\n" +
                    "               ETS_OU_CITY_MAP AOCM,\n" +
                    "               ETS_ITEM_MATCH  EIM\n" +
                    "        WHERE  EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE\n" +
                    "               AND EII.SYSTEMID = EIM.SYSTEMID\n" +
                    "               AND EFA.ASSET_ID = EIM.ASSET_ID\n" +
                    "               AND AOCM.ORGANIZATION_ID = EFA.ORGANIZATION_ID\n" +
                    "               AND EII.FINANCE_PROP = 'TD_ASSETS'\n" +
                    "               AND AOCM.ORGANIZATION_ID = ?\n" +
                    "               AND ( " + SyBaseSQLUtil.isNull() + "  OR AMD.DEPT_CODE LIKE ?)\n" +
                    "        GROUP  BY AOCM.COMPANY) TOTAL,\n" +
                    "       (SELECT COUNT(EFA.CURRENT_UNITS) SUM_QTY\n" +
                    "        FROM   ETS_FA_ASSETS  EFA,\n" +
                    "               ETS_ITEM_INFO  EII,\n" +
                    "               ETS_ITEM_MATCH EIM\n" +
                    "        WHERE  EII.SYSTEMID = EIM.SYSTEMID\n" +
                    "               AND EFA.ASSET_ID = EIM.ASSET_ID\n" +
                    "               AND EII.FINANCE_PROP = 'TD_ASSETS'\n" +
                    "               AND EFA.ORGANIZATION_ID = ?) TOTAL2\n" +
                    "ORDER  BY TOTAL.COMPANY,\n" +
                    "          TOTAL.DEPT_NAME DESC";
            sqlArgs.add(userAccount.getOrganizationId());
            sqlArgs.add(dto.getResponsibilityDept());
            sqlArgs.add(dto.getResponsibilityDept());
            sqlArgs.add(userAccount.getOrganizationId());
            sqlArgs.add(dto.getResponsibilityDept());
            sqlArgs.add(dto.getResponsibilityDept());
            sqlArgs.add(userAccount.getOrganizationId());
        } else if (userAccount.isDptAssetsManager()) {//按部门资产构成分布(部门资产管理员)
            sqlStr = "SELECT TOTAL.COMPANY,\n" +
                    "       TOTAL.DEPT_NAME,\n" +
                    "       TOTAL.ITEM_QTY,\n" +
                    "       TOTAL.SUM_COST,\n" +
                    "       (DECODE(TRUNC(100 * TOTAL.ITEM_QTY / TOTAL2.SUM_QTY),\n" +
                    "               0,\n" +
                    "               STR_REPLACE(ROUND(100 * TOTAL.ITEM_QTY / TOTAL2.SUM_QTY, 3),\n" +
                    "                       '.',\n" +
                    "                       '0.'),\n" +
                    "               TO_CHAR(ROUND(100 * TOTAL.ITEM_QTY / TOTAL2.SUM_QTY, 3))) || '%') ASSETS_RATE\n" +
                    "FROM   (SELECT AOCM.COMPANY COMPANY,\n" +
                    "               AMD.DEPT_NAME DEPT_NAME,\n" +
                    "               COUNT(EII.ITEM_QTY) ITEM_QTY,\n" +
                    "               SUM(EFA.COST) SUM_COST\n" +
                    "        FROM   ETS_ITEM_INFO   EII,\n" +
                    "               AMS_MIS_DEPT    AMD,\n" +
                    "               ETS_FA_ASSETS   EFA,\n" +
                    "               ETS_OU_CITY_MAP AOCM,\n" +
                    "               ETS_ITEM_MATCH  EIM\n" +
                    "        WHERE  EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE\n" +
                    "               AND EII.SYSTEMID = EIM.SYSTEMID\n" +
                    "               AND EFA.ASSET_ID = EIM.ASSET_ID\n" +
                    "               AND EII.FINANCE_PROP = 'TD_ASSETS'\n" +
                    "               AND AOCM.ORGANIZATION_ID = EII.ORGANIZATION_ID\n" +
                    "               AND AOCM.ORGANIZATION_ID = ?\n" +
                    "               AND\n" +
                    "               EII.RESPONSIBILITY_DEPT IN " + deptCodes + "\n" +
                    "        GROUP  BY AOCM.COMPANY,\n" +
                    "                  AMD.DEPT_NAME\n" +
                    "        \n" +
                    "        UNION\n" +
                    "        SELECT AOCM.COMPANY COMPANY,\n" +
                    "               AMD.DEPT_NAME DEPT_NAME,\n" +
                    "               COUNT(EII.ITEM_QTY) ITEM_QTY,\n" +
                    "               SUM(EFA.COST) SUM_COST\n" +
                    "        FROM   ETS_ITEM_INFO   EII,\n" +
                    "               AMS_MIS_DEPT    AMD,\n" +
                    "               ETS_FA_ASSETS   EFA,\n" +
                    "               ETS_OU_CITY_MAP AOCM,\n" +
                    "               ETS_ITEM_MATCH  EIM\n" +
                    "        WHERE  EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE\n" +
                    "               AND EII.SYSTEMID = EIM.SYSTEMID\n" +
                    "               AND EFA.ASSET_ID = EIM.ASSET_ID\n" +
                    "               AND AOCM.ORGANIZATION_ID = EII.ORGANIZATION_ID\n" +
                    "               AND EII.FINANCE_PROP = 'TD_ASSETS'\n" +
                    "               AND AOCM.ORGANIZATION_ID = ?\n" +
                    "               AND\n" +
                    "               EII.RESPONSIBILITY_DEPT IN " + deptCodes + "\n" +
                    "        GROUP  BY AOCM.COMPANY,\n" +
                    "                  AMD.DEPT_NAME) TOTAL,\n" +
                    "       (SELECT COUNT(EII.ITEM_QTY) SUM_QTY\n" +
                    "        FROM   ETS_FA_ASSETS  EFA,\n" +
                    "               ETS_ITEM_INFO  EII,\n" +
                    "               ETS_ITEM_MATCH EIM\n" +
                    "        WHERE  EII.SYSTEMID = EIM.SYSTEMID\n" +
                    "               AND EFA.ASSET_ID = EIM.ASSET_ID\n" +
                    "               AND EII.FINANCE_PROP = 'TD_ASSETS'\n" +
                    "               AND EII.ORGANIZATION_ID = ?) TOTAL2\n" +
                    "ORDER  BY TOTAL.COMPANY,\n" +
                    "          TOTAL.DEPT_NAME DESC";
            sqlArgs.add(userAccount.getOrganizationId());
            sqlArgs.add(userAccount.getOrganizationId());
            sqlArgs.add(userAccount.getOrganizationId());
        }
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
