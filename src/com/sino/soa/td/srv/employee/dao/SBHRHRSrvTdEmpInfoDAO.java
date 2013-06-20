package com.sino.soa.td.srv.employee.dao;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.QueryException;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.soa.td.srv.employee.dto.SBHRHRSrvTdEmployeeInfoDTO;
import com.sino.soa.td.srv.employee.model.SBHRHRSrvTdEmpInfoModel;

import java.sql.Connection;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-9-8
 * Time: 13:11:20
 * To change this template use File | Settings | File Templates.
 */
public class SBHRHRSrvTdEmpInfoDAO extends BaseDAO {

private SfUserDTO sfUser = null;


	public SBHRHRSrvTdEmpInfoDAO(SfUserDTO userAccount, SBHRHRSrvTdEmployeeInfoDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		sfUser = userAccount;
	}

	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		SBHRHRSrvTdEmployeeInfoDTO dtoPara = (SBHRHRSrvTdEmployeeInfoDTO) dtoParameter;
		super.sqlProducer = new SBHRHRSrvTdEmpInfoModel((SfUserDTO) userAccount, dtoPara);

	}

    public boolean isServiceTypeExists(String employeeNumber) throws QueryException {
    	SBHRHRSrvTdEmpInfoModel employeeInfoModel = new SBHRHRSrvTdEmpInfoModel((SfUserDTO) userAccount, (SBHRHRSrvTdEmployeeInfoDTO) dtoParameter);
        boolean isExists = true;
        SQLModel sqlModel = new SQLModel();
        sqlModel = employeeInfoModel.existsEmployeeModel(employeeNumber);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        isExists = sq.hasResult();
        return isExists;
    }

}