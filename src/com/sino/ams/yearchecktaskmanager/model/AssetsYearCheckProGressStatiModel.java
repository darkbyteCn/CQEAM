package com.sino.ams.yearchecktaskmanager.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.yearchecktaskmanager.dto.AssetsYearCheckProGressStatiDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;

public class AssetsYearCheckProGressStatiModel extends AMSSQLProducer {
	
	private AssetsYearCheckProGressStatiDTO dto;
	
	public AssetsYearCheckProGressStatiModel(SfUserDTO userAccount,
			AssetsYearCheckProGressStatiDTO dtoParameter) {
		super(userAccount, dtoParameter);
		dto = dtoParameter;
	}
	
	public SQLModel getPageQueryModel() throws SQLModelException {
			
		    SQLModel sqlModel = new SQLModel();
		    List sqlArgs = new ArrayList();
		    boolean sureUser = false;
		    StringBuffer sqlBuffer = new StringBuffer();
		    sqlBuffer.append(" SELECT STAT_ID,COMPANY_CODE, \n")
            .append(" COMPANY,\n")
            .append(" ORGANIZATION_ID,\n")
            .append(" RESP_DEPT_CODE,\n")
            .append(" RESP_DEPT_NAME,\n")
            .append(" SUM_ASSETS,\n")
            .append(" SUM_CHECK_ASSETS,\n")
            .append(" PERCENT_BY_ASSETS,\n")
            .append(" SUM_WORKORDER_OBJECT_CODE,\n")
            .append(" SUM_CHECK_WORKORDER_OBJECT_CODE,\n")
            .append(" PERCENT_BY_OBJECT,\n")
            .append(" STATISTICES_TYPE \n")
            .append(" FROM ( \n");
		    if(userAccount.isProvAssetsManager() || userAccount.isSysAdmin()){//全省资产管理员看到全省和各个地市
		    	 sureUser =true;
		    	 sqlBuffer.append(" select STAT_ID,COMPANY_CODE, \n")
	             .append(" COMPANY,\n")
	             .append(" ORGANIZATION_ID,\n")
	             .append(" RESP_DEPT_CODE,\n")
	             .append(" RESP_DEPT_NAME,\n")
	             .append(" SUM_ASSETS,\n")
	             .append(" SUM_CHECK_ASSETS,\n")
	             .append(" PERCENT_BY_ASSETS,\n")
	             .append(" SUM_WORKORDER_OBJECT_CODE,\n")
	             .append(" SUM_CHECK_WORKORDER_OBJECT_CODE,\n")
	             .append(" PERCENT_BY_OBJECT,\n")
	             .append(" STATISTICES_TYPE \n")
	             .append(" from AMS_ASSETS_YEAR_CHECK_STATISTICES where STATISTICES_TYPE='1'AND CURRENT_YEAR=convert(varchar,datepart(yy,getdate())) \n") //查询全省总的
	             .append("union all \n")
		    	 .append(" select STAT_ID,COMPANY_CODE,\n")
	             .append(" COMPANY,\n")
	             .append(" ORGANIZATION_ID,\n")
	             .append(" RESP_DEPT_CODE,\n")
	             .append(" RESP_DEPT_NAME,\n")
	             .append(" SUM_ASSETS,\n")
	             .append(" SUM_CHECK_ASSETS,\n")
	             .append(" PERCENT_BY_ASSETS,\n")
	             .append(" SUM_WORKORDER_OBJECT_CODE,\n")
	             .append(" SUM_CHECK_WORKORDER_OBJECT_CODE,\n")
	             .append(" PERCENT_BY_OBJECT,\n")
	             .append(" STATISTICES_TYPE \n")
	             .append(" from AMS_ASSETS_YEAR_CHECK_STATISTICES where STATISTICES_TYPE='2' AND CURRENT_YEAR=convert(varchar,datepart(yy,getdate())) \n"); //各个地市总的
		    }else if(sureUser=false && userAccount.isComAssetsManager()){//地市资产管理员看到全省的总进度，本地市总进度，各个部门的进度
		    	sureUser = true; 
		    	sqlBuffer.append(" select STAT_ID,COMPANY_CODE,\n")
	             .append(" COMPANY,\n")
	             .append(" ORGANIZATION_ID,\n")
	             .append(" RESP_DEPT_CODE,\n")
	             .append(" RESP_DEPT_NAME,\n")
	             .append(" SUM_ASSETS,\n")
	             .append(" SUM_CHECK_ASSETS,\n")
	             .append(" PERCENT_BY_ASSETS,\n")
	             .append(" SUM_WORKORDER_OBJECT_CODE,\n")
	             .append(" SUM_CHECK_WORKORDER_OBJECT_CODE,\n")
	             .append(" PERCENT_BY_OBJECT,\n")
	             .append(" STATISTICES_TYPE \n")
	             .append(" from AMS_ASSETS_YEAR_CHECK_STATISTICES where STATISTICES_TYPE='1' AND CURRENT_YEAR=convert(varchar,datepart(yy,getdate())) \n") //查询全省的
	             .append("union all \n")
		    	 .append(" select STAT_ID,COMPANY_CODE,\n")
	             .append(" COMPANY,\n")
	             .append(" ORGANIZATION_ID,\n")
	             .append(" RESP_DEPT_CODE,\n")
	             .append(" RESP_DEPT_NAME,\n")
	             .append(" SUM_ASSETS,\n")
	             .append(" SUM_CHECK_ASSETS,\n")
	             .append(" PERCENT_BY_ASSETS,\n")
	             .append(" SUM_WORKORDER_OBJECT_CODE,\n")
	             .append(" SUM_CHECK_WORKORDER_OBJECT_CODE,\n")
	             .append(" PERCENT_BY_OBJECT,\n")
	             .append(" STATISTICES_TYPE \n")
	             .append(" from AMS_ASSETS_YEAR_CHECK_STATISTICES where  CURRENT_YEAR=convert(varchar,datepart(yy,getdate())) \n")
	             .append(" AND (STATISTICES_TYPE='2' or STATISTICES_TYPE='3')\n"); //本地的总 和 各个部门的
		    }else if(sureUser=false){//部门资产管理员，只能看到自己部门的
		    	 sqlBuffer.append(" select STAT_ID,COMPANY_CODE," )
	             .append(" COMPANY,\n")
	             .append(" ORGANIZATION_ID,\n")
	             .append(" RESP_DEPT_CODE,\n")
	             .append(" RESP_DEPT_NAME,\n")
	             .append(" SUM_ASSETS,\n")
	             .append(" SUM_CHECK_ASSETS,\n")
	             .append(" PERCENT_BY_ASSETS,\n")
	             .append(" SUM_WORKORDER_OBJECT_CODE,\n")
	             .append(" SUM_CHECK_WORKORDER_OBJECT_CODE,\n")
	             .append(" PERCENT_BY_OBJECT,\n")
	             .append(" STATISTICES_TYPE \n")
	             .append(" from AMS_ASSETS_YEAR_CHECK_STATISTICES where STATISTICES_TYPE='1' AND CURRENT_YEAR=convert(varchar,datepart(yy,getdate())) \n") //查询全省总的
	             .append("union all \n")
		    	 .append(" select STAT_ID,COMPANY_CODE," )
	             .append(" COMPANY,\n")
	             .append(" ORGANIZATION_ID,\n")
	             .append(" RESP_DEPT_CODE,\n")
	             .append(" RESP_DEPT_NAME,\n")
	             .append(" SUM_ASSETS,\n")
	             .append(" SUM_CHECK_ASSETS,\n")
	             .append(" PERCENT_BY_ASSETS,\n")
	             .append(" SUM_WORKORDER_OBJECT_CODE,\n")
	             .append(" SUM_CHECK_WORKORDER_OBJECT_CODE,\n")
	             .append(" PERCENT_BY_OBJECT,\n")
	             .append(" STATISTICES_TYPE \n")
	             .append(" from AMS_ASSETS_YEAR_CHECK_STATISTICES where STATISTICES_TYPE='2' AND CURRENT_YEAR=convert(varchar,datepart(yy,getdate())) \n") //本地时总的
	             .append(" AND  ORGANIZATION_ID=? " ); //本地市总的
		    	 sqlArgs.add(dto.getOrganizationId());
	             sqlBuffer.append("union all \n")
		    	 .append(" select STAT_ID,COMPANY_CODE," )
	             .append(" COMPNAY,\n")
	             .append(" ORGANIZATION_ID,\n")
	             .append(" RESP_DEPT_CODE,\n")
	             .append(" RESP_DEPT_NAME,\n")
	             .append(" SUM_ASSETS,\n")
	             .append(" SUM_CHECK_ASSETS,\n")
	             .append(" PERCENT_BY_ASSETS,\n")
	             .append(" SUM_WORKORDER_OBJECT_CODE,\n")
	             .append(" SUM_CHECK_WORKORDER_OBJECT_CODE,\n")
	             .append(" PERCENT_BY_OBJECT,\n")
	             .append(" STATISTICES_TYPE \n")
	             .append(" from AMS_ASSETS_YEAR_CHECK_STATISTICES where CURRENT_YEAR=convert(varchar,datepart(yy,getdate())) \n")
		    	 .append(" AND RESP_DEPT_CODE IN(select DEPT_ID from SF_GROUP SG,SF_GROUP_MATCH SGM where SG.GROUP_ID = SGM.GROUP_ID and SGM.DEPT_ID=?) \n");
		    	sqlArgs.add(userAccount.getDeptCode());
		    }
		    
		    //OU限制
		    if(dto.getOrganizationId()!=-1 || dto.getOrganizationId()!=0 && !String.valueOf(dto.getOrganizationId()).equals("\n") ){
		    	sqlBuffer.append(" AND ORGANIZATION_ID=? \n");
		    	sqlArgs.add(dto.getOrganizationId());
		    }
		    
		    sqlBuffer.append(" ) A ORDER BY STATISTICES_TYPE \n");
		    System.out.println("sql="+sqlBuffer.toString());
		    sqlModel.setArgs(sqlArgs);
		    sqlModel.setSqlStr(sqlBuffer.toString());
		    return sqlModel;
	}

}
