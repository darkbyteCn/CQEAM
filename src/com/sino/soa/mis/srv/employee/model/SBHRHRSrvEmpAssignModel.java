package com.sino.soa.mis.srv.employee.model;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.soa.mis.srv.employee.dto.SBHRHRSrvEmployeeInfoDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-9-7
 * Time: 15:48:08
 * To change this template use File | Settings | File Templates.
 */
public class SBHRHRSrvEmpAssignModel extends BaseSQLProducer {

	 private SfUserDTO sfUser = null;

	 public SBHRHRSrvEmpAssignModel(SfUserDTO userAccount, SBHRHRSrvEmployeeInfoDTO dtoParameter){
		 super(userAccount,dtoParameter);
		 sfUser =userAccount;
	 }

	public SQLModel getDataUpdateModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SBHRHRSrvEmployeeInfoDTO srcEmployeeInfo =(SBHRHRSrvEmployeeInfoDTO)dtoParameter;
		String sqlStr = "UPDATE AMS_MIS_EMPLOYEE"
					   +" SET "
					   +" HR_DEPT_ID = ?,"
					   +" HR_DEPT_NAME = ?,"
					   +" DEPT_CODE = CONVERT(VARCHAR , ?)," 
					   +" COMPANY_CODE =? "
					   +" WHERE " 
					   +" EMPLOYEE_NUMBER = ?";
		 sqlArgs.add(srcEmployeeInfo.getOrganizationID());
		 sqlArgs.add(srcEmployeeInfo.getOrganization());
		 sqlArgs.add(srcEmployeeInfo.getDeptCode());
		 sqlArgs.add(srcEmployeeInfo.getCompanyCode());
		 sqlArgs.add(srcEmployeeInfo.getEmployeeNumber());
		 sqlModel.setArgs(sqlArgs);
		 sqlModel.setSqlStr(sqlStr);
		 return sqlModel;
	}

	 /**
	 * 功能：判断是否有该条数据。
	 * @return SQLModel
	 */
	public SQLModel existsEmpAssignModel(String employeeNumber){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr ="SELECT * FROM AMS_MIS_EMPLOYEE AME WHERE AME.EMPLOYEE_NUMBER = ?";
		sqlArgs.add(employeeNumber);
		sqlModel.setArgs(sqlArgs);
		sqlModel.setSqlStr(sqlStr);
		return sqlModel;
	}
	
	 /**
	 * 功能：根据HR_DEPT_NAME在表AMS_MIS_DEPT中获取COMPANY_CODE,DEPT_CODE
	 * @return SQLModel
	 */
	public SQLModel getEmpCompanyCodeModel(String hrDeptName){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr ="SELECT DEPT_CODE,COMPANY_CODE FROM AMS_MIS_DEPT WHERE STR_REPLACE(DEPT_NAME,'中国移动通信集团内蒙古有限公司',NULL)= STR_REPLACE(?,'中国移动通信集团内蒙古有限公司',NULL)";
		sqlArgs.add(hrDeptName);
		sqlModel.setArgs(sqlArgs);
		sqlModel.setSqlStr(sqlStr);
		return sqlModel;
	}
	
	
}