package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.fixing.dto.EtsItemInfoDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2009-8-25
 * Time: 15:21:02
 * To change this template use File | Settings | File Templates.
 */
public class AmsDeptMatchModel extends AMSSQLProducer {
	public AmsDeptMatchModel(BaseUserDTO userAccount, DTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsItemInfoDTO dto = (EtsItemInfoDTO) dtoParameter;
		String sqlStr = "SELECT EOCM.COMPANY,\n" +
                "       AMD.DEPT_CODE,\n" +
                "       AMD.DEPT_NAME\n" +
                "  FROM AMS_MIS_DEPT    AMD,\n" +
                "       ETS_OU_CITY_MAP EOCM\n" +
                " WHERE AMD.COMPANY_CODE = EOCM.COMPANY_CODE\n" +
                "       AND AMD.ENABLED = 'Y'\n" +
                "       AND NOT EXISTS (SELECT 1\n" +
                "          FROM AMS_COST_DEPT_MATCH ACDM\n" +
                "         WHERE ACDM.DEPT_CODE = AMD.DEPT_CODE)\n" +
                "       AND (( " + SyBaseSQLUtil.isNull() + "  OR AMD.DEPT_CODE LIKE ?)\n" +
                "         OR ( " + SyBaseSQLUtil.isNull() + "  OR AMD.DEPT_NAME LIKE ?))\n" +
                " ORDER BY EOCM.COMPANY";
		sqlArgs.add(dto.getResponsibilityDept());
		sqlArgs.add(dto.getResponsibilityDept());
		sqlArgs.add(dto.getResponsibilityDept());
		sqlArgs.add(dto.getResponsibilityDept());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

    public SQLModel insertCostDeptMatch(String deptCode, String countyCode, String companyCode) {
		SQLModel sqlModel = new SQLModel();
		String sqlStr = "INSERT INTO AMS_COST_DEPT_MATCH\n" +
                "   (COST_CENTER_CODE,\n" +
                "    DEPT_CODE,\n" +
                "    CREATION_DATE,\n" +
                "    CREATED_BY,\n" +
                "    ENABLED,\n" +
                "    COMPANY_CODE)\n" +
                " VALUES (?, ?, GETDATE(), ?, 'Y', ?)";
		List sqlArgs = new ArrayList();
		sqlArgs.add(countyCode);
		sqlArgs.add(deptCode);
		sqlArgs.add(userAccount.getUserId());
        sqlArgs.add(companyCode);
        sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
