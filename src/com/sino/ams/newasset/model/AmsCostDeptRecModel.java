package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.fixing.dto.EtsItemInfoDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2009-8-26
 * Time: 16:50:18
 * To change this template use File | Settings | File Templates.
 */
public class AmsCostDeptRecModel extends AMSSQLProducer {

	public AmsCostDeptRecModel(SfUserDTO userAccount, EtsItemInfoDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsItemInfoDTO dto = (EtsItemInfoDTO) dtoParameter;
		String sqlStr = "SELECT EOCM.COMPANY,\n" +
                "       ACDM.COST_CENTER_CODE COUNTY_CODE,\n" +
                "       EC.COUNTY_NAME,\n" +
                "       ACDM.DEPT_CODE DEPT_CODE,\n" +
                "       AMD.DEPT_NAME\n" +
                "  FROM AMS_COST_DEPT_MATCH ACDM,\n" +
                "       ETS_COUNTY          EC,\n" +
                "       AMS_MIS_DEPT        AMD,\n" +
                "       ETS_OU_CITY_MAP     EOCM\n" +
                " WHERE ACDM.COST_CENTER_CODE *= EC.COUNTY_CODE_MIS\n" +
                "       AND ACDM.DEPT_CODE = AMD.DEPT_CODE\n" +
                "       AND ACDM.COMPANY_CODE = EOCM.COMPANY_CODE\n" +
                "       AND SUBSTRING(EC.COMPANY_CODE, 0, 2) <> 80\n" +
                "       AND (( " + SyBaseSQLUtil.isNull() + "  OR ACDM.COST_CENTER_CODE LIKE ?) OR\n" +
                "       ( " + SyBaseSQLUtil.isNull() + "  OR EC.COUNTY_NAME LIKE ?))\n" +
                "       AND (( " + SyBaseSQLUtil.isNull() + "  OR ACDM.DEPT_CODE LIKE ?) OR\n" +
                "       ( " + SyBaseSQLUtil.isNull() + "  OR AMD.DEPT_NAME LIKE ?))\n" +
                " ORDER BY ACDM.CREATION_DATE DESC";
        sqlArgs.add(dto.getPrjName());
		sqlArgs.add(dto.getPrjName());
		sqlArgs.add(dto.getPrjName());
		sqlArgs.add(dto.getPrjName());
        sqlArgs.add(dto.getResponsibilityDept());
		sqlArgs.add(dto.getResponsibilityDept());
		sqlArgs.add(dto.getResponsibilityDept());
		sqlArgs.add(dto.getResponsibilityDept());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

    public SQLModel deleteCostDeptMatch(EtsItemInfoDTO dto) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM AMS_COST_DEPT_MATCH\n" +
                " WHERE COST_CENTER_CODE = ?\n" +
                "       AND DEPT_CODE = ?";
        sqlArgs.add(dto.getCountyCode());
        sqlArgs.add(dto.getResponsibilityDept());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
