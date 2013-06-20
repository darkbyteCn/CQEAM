package com.sino.soa.td.srv.employee.dao;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.QueryException;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.soa.td.srv.employee.dto.SBHRHRSrvTdEmployeeInfoDTO;
import com.sino.soa.td.srv.employee.model.SBHRHRSrvTdEmpAssignModel;
import com.sino.base.exception.ContainerException;
import java.sql.Connection;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-9-8
 * Time: 13:11:37
 * To change this template use File | Settings | File Templates.
 */
public class SBHRHRSrvTdEmpAssignDAO extends BaseDAO {

private SfUserDTO sfUser = null;


	public SBHRHRSrvTdEmpAssignDAO(SfUserDTO userAccount, SBHRHRSrvTdEmployeeInfoDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		sfUser = userAccount;
	}

	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		SBHRHRSrvTdEmployeeInfoDTO dtoPara = (SBHRHRSrvTdEmployeeInfoDTO) dtoParameter;
		super.sqlProducer = new SBHRHRSrvTdEmpAssignModel((SfUserDTO) userAccount, dtoPara);

	}

	/**
	 * @param employeeNumber
	 * @return
	 * @throws com.sino.base.exception.QueryException
	 */
	public boolean isEmployeeExists(String employeeNumber) throws QueryException {
		SBHRHRSrvTdEmpAssignModel empAssignInfoModel = new SBHRHRSrvTdEmpAssignModel((SfUserDTO) userAccount, (SBHRHRSrvTdEmployeeInfoDTO) dtoParameter);
        boolean isExists = true;
        SQLModel sqlModel = new SQLModel();
        sqlModel = empAssignInfoModel.existsEmpAssignModel(employeeNumber);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        isExists = sq.hasResult();
        return isExists;
    }

	
	 /**
	 * 功能：根据HR_DEPT_NAME在表AMS_MIS_DEPT中获取COMPANY_CODE
	 * @return SQLModel
	 */
	public String getEmpCompanyCodeModel(String hrDeptName) throws QueryException, ContainerException   {
		SBHRHRSrvTdEmpAssignModel empAssignInfoModel = new SBHRHRSrvTdEmpAssignModel((SfUserDTO) userAccount, (SBHRHRSrvTdEmployeeInfoDTO) dtoParameter);
       String companyCode="";
       SQLModel sqlModel = new SQLModel();
       sqlModel = empAssignInfoModel.getEmpCompanyCodeModel(hrDeptName);
       SimpleQuery sq = new SimpleQuery(sqlModel, conn);
       sq.executeQuery();
       if(sq.hasResult()){
    	   companyCode = sq.getFirstRow().getStrValue("COMPANY_CODE");
       }else{
    	   companyCode = "";
       }
       return companyCode;
   }
	
	 /**
	 * 功能：根据HR_DEPT_NAME在表AMS_MIS_DEPT中获取DEPT_CODE
	 * @return SQLModel
	 */
	public String getEmpDeptCodeModel(String hrDeptName) throws QueryException, ContainerException   {
		SBHRHRSrvTdEmpAssignModel empAssignInfoModel = new SBHRHRSrvTdEmpAssignModel((SfUserDTO) userAccount, (SBHRHRSrvTdEmployeeInfoDTO) dtoParameter);
       String companyCode="";
       SQLModel sqlModel = new SQLModel();
       sqlModel = empAssignInfoModel.getEmpCompanyCodeModel(hrDeptName);
       SimpleQuery sq = new SimpleQuery(sqlModel, conn);
       sq.executeQuery();
       if(sq.hasResult()){
    	   companyCode = sq.getFirstRow().getStrValue("DEPT_CODE");
       }else{
    	   companyCode = "";
       }
       return companyCode;
   }
	
}