package com.sino.soa.mis.srv.employee.dao;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.QueryException;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.soa.mis.srv.employee.dto.SBHRHRSrvEmployeeInfoDTO;
import com.sino.soa.mis.srv.employee.model.SBHRHRSrvEmpInfoModel;

import java.sql.Connection;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-9-7
 * Time: 16:54:19
 * To change this template use File | Settings | File Templates.
 */
public class SBHRHRSrvEmpInfoDAO extends BaseDAO {

private SfUserDTO sfUser = null;


	public SBHRHRSrvEmpInfoDAO(SfUserDTO userAccount, SBHRHRSrvEmployeeInfoDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		sfUser = userAccount;
	}

	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		SBHRHRSrvEmployeeInfoDTO dtoPara = (SBHRHRSrvEmployeeInfoDTO) dtoParameter;
		super.sqlProducer = new SBHRHRSrvEmpInfoModel((SfUserDTO) userAccount, dtoPara);

	}
    
	/**
	 * AMS_MIS_EMPLOYEE
	 * 功能：判断是否有该条数据
	 */
    public boolean isServiceTypeExists(String employeeNumber) throws QueryException {
    	SBHRHRSrvEmpInfoModel employeeInfoModel = new SBHRHRSrvEmpInfoModel((SfUserDTO) userAccount, (SBHRHRSrvEmployeeInfoDTO) dtoParameter);
        boolean isExists = true;
        SQLModel sqlModel = new SQLModel();
        sqlModel = employeeInfoModel.existsEmployeeModel(employeeNumber);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        isExists = sq.hasResult();
        return isExists;
    }

}