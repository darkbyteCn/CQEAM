package com.sino.ams.yearchecktaskmanager.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.yearchecktaskmanager.dto.AssetsRespMapLocationDTO;
import com.sino.base.db.sql.model.SQLModel;

public class AssetsRespMapLocationModel extends AMSSQLProducer {
private SfUserDTO sfUser = null;
	
	public AssetsRespMapLocationModel(SfUserDTO userAccount, AssetsRespMapLocationDTO dtoParameter){
		super(userAccount, dtoParameter);
		sfUser = userAccount;
	}
	
	//导出本地地市的所有无线地点
	public SQLModel getLocationsModel()
	{
		SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
		String sqlStr = " SELECT WORKORDER_OBJECT_CODE,WORKORDER_OBJECT_NAME FROM ETS_OBJECT WHERE ORGANIZATION_ID=? and OBJECT_CATEGORY IN ('15','10','20','30') ";
		sqlArgs.add(userAccount.getOrganizationId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
		
	}
	//删除临时表数据
	public SQLModel getDeleteTmpModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		
		String sqlStr = "DELETE FROM" + " AMS_ASSETS_YEAR_CHECK_RML_TMP " + " WHERE"
				+ " CREATEED_BY = ?";
		
		sqlArgs.add(userAccount.getUserId());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	//保存数据到临时表
	public SQLModel getSaveDataModel(AssetsRespMapLocationDTO dto){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("INSERT INTO AMS_ASSETS_YEAR_CHECK_RML_TMP( ")
			  .append("TMP_ID,")
			  .append("USER_NAME,")
			  .append("EMPLOYEE_NUMBER,")
			  .append("	DEPT_NAME,")
			  .append("ORGANIZATION_ID,")
			  .append("WORKORDER_OBJECT_CODE,")
			  .append("WORKORDER_OBJECT_NMAE,")
			  .append("	CREATEED_BY, ")
			  .append("	ERROR_MESSAGE, )")
			  .append(" EXCEL_ROW_NUMBER, ")
			  .append("	CREATION_DATE )")
			  .append("	VALUES(NEWID(),?,?,?,?,?,?,?,?,?,GETDATE())");
		
		sqlArgs.add(dto.getUserName());
		sqlArgs.add(dto.getEmployeeNumber());
		sqlArgs.add(dto.getDeptName());
		sqlArgs.add(userAccount.getOrganizationId());
		sqlArgs.add(dto.getWorkOrderObjectCode());
		sqlArgs.add(dto.getWorkOrderObjectName());
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(dto.getErrorMsg());
		sqlArgs.add(dto.getExcelLineId());
		
		sqlModel.setSqlStr(sqlStr.toString());
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	//保存数据到正式表
	public SQLModel getsaveOrUpdateDataModel(AssetsRespMapLocationDTO dto){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("INSERT INTO AMS_ASSETS_YEAR_CHECK_RESP_MAP_LOCTION( ")
			  .append("ID,")
			  .append("USER_NAME,")
			  .append("EMPLOYEE_NUMBER,")
			  .append("	DEPT_NAME,")
			  .append("ORGANIZATION_ID,")
			  .append("WORKORDER_OBJECT_CODE,")
			  .append("WORKORDER_OBJECT_NMAE,")
			  .append("	CREATEED_BY, ")
			  .append("	CREATION_DATE )")
			  .append("	VALUES(NEWID(),?,?,?,?,?,?,?,GETDATE())");
		
		sqlArgs.add(dto.getUserName());
		sqlArgs.add(dto.getEmployeeNumber());
		sqlArgs.add(dto.getDeptName());
		sqlArgs.add(userAccount.getOrganizationId());
		sqlArgs.add(dto.getWorkOrderObjectCode());
		sqlArgs.add(dto.getWorkOrderObjectName());
		sqlArgs.add(userAccount.getUserId());
		
		sqlModel.setSqlStr(sqlStr.toString());
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	//获取导入信息明细
	public SQLModel getImportDataDetal(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("SELECT ")
		  .append(" TMP_ID,")
		  .append(" USER_NAME,")
		  .append(" EMPLOYEE_NUMBER,")
		  .append("	DEPT_NAME,")
		  .append(" ORGANIZATION_ID, ")
		  .append(" WORKORDER_OBJECT_CODE,")
		  .append(" WORKORDER_OBJECT_NMAE,")
		  .append(" ERROR_MESSAGE,")
		  .append("	CREATEED_BY,")
		  .append("	CREATE_DATE,")
		  .append("	EXCEL_ROW_NUMBER ")
		  .append(" FROM AMS_ASSETS_YEAR_CHECK_RML_TMP WHERE CREATEED_BY = ? ")
		  .append(" order by EXCEL_ROW_NUMBER ");
		
		sqlArgs.add(userAccount.getUserId());
		
		sqlModel.setSqlStr(sqlStr.toString());
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	//写入错误信息
	public SQLModel getUpdateErrorModel(AssetsRespMapLocationDTO dto,String errorMsg){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append(" UPDATE AMS_ASSETS_YEAR_CHECK_RML_TMP ")
		      .append(" SET ERROR_MESSAGE = ? ")
		      .append(" WHERE WORKORDER_OBJECT_CODE = ? AND CREATEED_BY =? ");
		
		sqlArgs.add(errorMsg);
		sqlArgs.add(dto.getWorkOrderObjectCode());
		sqlArgs.add(userAccount.getUserId());
		
		sqlModel.setSqlStr(sqlStr.toString());
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	//校验WORKORDER_OBJECT_CODE是否存在重复
	public SQLModel getVaidateWorkOrderObjectCodeHasRepeat(String workOrderObjectCode){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("select WORKORDER_OBJECT_CODE from AMS_ASSETS_YEAR_CHECK_RML_TMP ")
		      .append(" where CREATEED_BY = ? and WORKORDER_OBJECT_CODE = ? ")
		      .append(" group by WORKORDER_OBJECT_CODE ")
		      .append("having count(WORKORDER_OBJECT_CODE)> 1 ");
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(workOrderObjectCode);
		sqlModel.setSqlStr(sqlStr.toString());
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	//校验地点组合编码
	public SQLModel getVaidateWorkOrderObjectCode(String workOrderObjectCode){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("select WORKORDER_OBJECT_CODE FROM ETS_OBJECT where WORKORDER_OBJECT_CODE = ? ");
		sqlArgs.add(workOrderObjectCode);
		
		sqlModel.setSqlStr(sqlStr.toString());
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	//校验员工编码
	public SQLModel getVaidateEmployeeNumber(String employeeNumber){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("select EMPLOYEE_NUMBER FROM AMS_MIS_EMPLOYEE where EMPLOYEE_NUMBER = ? ");
		
		sqlArgs.add(employeeNumber);
		sqlModel.setSqlStr(sqlStr.toString());
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
}
