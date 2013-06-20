package com.sino.soa.td.srv.employee.model;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.soa.td.srv.employee.dto.SBHRHRSrvTdEmployeeInfoDTO;
import com.sino.base.exception.CalendarException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-9-8
 * Time: 13:10:16
 * To change this template use File | Settings | File Templates.
 */
public class SBHRHRSrvTdEmpInfoModel extends BaseSQLProducer {

	 private SfUserDTO sfUser = null;

	 public SBHRHRSrvTdEmpInfoModel(SfUserDTO userAccount, SBHRHRSrvTdEmployeeInfoDTO dtoParameter){
		 super(userAccount,dtoParameter);
		 sfUser =userAccount;
	 }

	 public SQLModel getDataCreateModel(){
		 SQLModel sqlModel = new SQLModel();
		 List sqlArgs = new ArrayList();
		 SBHRHRSrvTdEmployeeInfoDTO srcEmployeeInfo = (SBHRHRSrvTdEmployeeInfoDTO) dtoParameter;	 	 
		 String sqlStr = "INSERT INTO AMS_MIS_EMPLOYEE ( " 
				 		+"EMPLOYEE_ID," 
				 		+"USER_NAME," 
				 		+"EMPLOYEE_NUMBER," 
				 		+"CREATION_DATE," 
			            +"CREATED_BY," 
				 		+"ENABLED," 
				 		+"PERSON_ID," 
				 		+"LAST_UPDATE_DATE" 
				 		+")VALUES(" 
				 		+"NEWID(), ?, ?, GETDATE(), ?, ?, CONVERT(FLOAT,?), ?)";
		 sqlArgs.add(srcEmployeeInfo.getFullName());
		 sqlArgs.add(srcEmployeeInfo.getEmployeeNumber());
	     sqlArgs.add(sfUser.getUserId());
		 sqlArgs.add(srcEmployeeInfo.getIsEndbled());
		 sqlArgs.add(srcEmployeeInfo.getPersonId());
		 try {
			sqlArgs.add(srcEmployeeInfo.getLastUpdateDate());
		} catch (CalendarException e) {
			e.printStackTrace();
		}
		 sqlModel.setArgs(sqlArgs);
		 sqlModel.setSqlStr(sqlStr);
		 return sqlModel;
	 }

	 /**
	  * 功能：员工基本信息服务 SrvEmployeeInfoDTO数据更新SQLModel。
	  * @return SQLModel 返回数据更新用SQLModel
	  * @throws com.sino.base.exception.SQLModelException 发生日历异常时转化为该异常抛出
	  */
	public SQLModel getDataUpdateModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SBHRHRSrvTdEmployeeInfoDTO srcEmployeeInfo =(SBHRHRSrvTdEmployeeInfoDTO)dtoParameter;		 
		String sqlStr = "UPDATE AMS_MIS_EMPLOYEE"
					+" SET "
					+" USER_NAME = ?,"
					+" ENABLED = ?,"
		            +" LAST_UPDATE_DATE = ?," 
		            +" PERSON_ID = CONVERT(FLOAT,?) ,"
		            +" LAST_UPDATE_BY = ?"
					+" WHERE EMPLOYEE_NUMBER =?";
		 sqlArgs.add(srcEmployeeInfo.getFullName());
		 sqlArgs.add(srcEmployeeInfo.getIsEndbled());
		 try {
			sqlArgs.add(srcEmployeeInfo.getLastUpdateDate());
		} catch (CalendarException e) {
			e.printStackTrace();
		}
		 sqlArgs.add(srcEmployeeInfo.getPersonId());
	     sqlArgs.add(sfUser.getUserId());
		 sqlArgs.add(srcEmployeeInfo.getEmployeeNumber());
		 sqlModel.setArgs(sqlArgs);
		 sqlModel.setSqlStr(sqlStr);
		 return sqlModel;
	}

	 /**
	 * 功能：判断是否有该条数据。
	 * @return SQLModel
	 */
	public SQLModel existsEmployeeModel(String employeeNumber){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SBHRHRSrvTdEmployeeInfoDTO srcEmployeeInfo =(SBHRHRSrvTdEmployeeInfoDTO)dtoParameter;
		String sqlStr ="SELECT * FROM AMS_MIS_EMPLOYEE AME WHERE AME.EMPLOYEE_NUMBER = ?";
		sqlArgs.add(employeeNumber);
		sqlModel.setArgs(sqlArgs);
		sqlModel.setSqlStr(sqlStr);
		return sqlModel;
	}
}
