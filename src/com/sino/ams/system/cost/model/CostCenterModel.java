package com.sino.ams.system.cost.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.cost.dto.AmsMisCostMatchDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2008-7-31
 * Time: 13:35:06
 * To change this template use File | Settings | File Templates.
 */
public class CostCenterModel extends AMSSQLProducer {
	public CostCenterModel(BaseUserDTO userAccount, DTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsMisCostMatchDTO dto = (AmsMisCostMatchDTO) dtoParameter;
		String sqlStr = "SELECT " +
				" ACCV.COST_CENTER_CODE,\n" +
				" ACCV.COST_CENTER_NAME,\n" +
				" ACCV.COMPANY_CODE,\n" +
				" ACCV.ORGANIZATION_ID\n" +
				" FROM " +
				" AMS_COST_CENTER_V ACCV" ;
		 if ((!userAccount.isProvinceUser()) && (!userAccount.isSysAdmin())) {
	 sqlStr +=  " WHERE " +
				" ACCV.ORGANIZATION_ID = ?\n" +
				" AND ( " + SyBaseSQLUtil.isNull() + "  OR CONVERT(VARCHAR,ACCV.COST_CENTER_CODE) = ?)" +
				" AND ( " + SyBaseSQLUtil.isNull() + "  OR ACCV.COST_CENTER_NAME = ?)";
			 sqlArgs.add(userAccount.getOrganizationId());
		 }else{
			sqlStr +=  " WHERE " +
				" ( " + SyBaseSQLUtil.isNull() + " OR CONVERT(VARCHAR,ACCV.COST_CENTER_CODE) = ?)" +
				" AND ( " + SyBaseSQLUtil.isNull() + "  OR ACCV.COST_CENTER_NAME = ?)";
		 }

        sqlStr +="ORDER BY ACCV.ORGANIZATION_ID,ACCV.COST_CENTER_NAME,ACCV.COST_CENTER_CODE";

        sqlArgs.add(dto.getCostCenterCode());	
        sqlArgs.add(dto.getCostCenterCode());
		sqlArgs.add(dto.getCostCenterName());
		sqlArgs.add(dto.getCostCenterName());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}



	public SQLModel insertIntoCOSTModel(String dept, String cost,String companyCode) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr =   "INSERT INTO " +
                        "  AMS_COST_DEPT_MATCH \n" +
                        "  (COST_CENTER_CODE,\n" +
                        "   DEPT_CODE,\n" +
                        "   COMPANY_CODE,\n" +
                        "   ENABLED,\n" +
                        "   CREATION_DATE,\n" +
                        "   CREATED_BY)\n" +
                        "VALUES\n" +
                        "  (?, ?,?, ?, GETDATE(), ?)" ;

        sqlArgs.add(cost);
        sqlArgs.add(dept);
        sqlArgs.add(companyCode);
        sqlArgs.add( "Y" );
        sqlArgs.add(userAccount.getCreatedBy());

        sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

}
