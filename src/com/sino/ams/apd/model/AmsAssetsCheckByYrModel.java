package com.sino.ams.apd.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.apd.dto.AmsAssetsCheckByYrHeaderDTO;
import com.sino.ams.apd.dto.AmsAssetsCheckByYrLineDTO;
import com.sino.ams.newasset.dto.AmsAssetsTransHeaderDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.framework.sql.BaseSQLProducer;

public class AmsAssetsCheckByYrModel extends BaseSQLProducer {
	private SfUserDTO user = null;

	public AmsAssetsCheckByYrModel(SfUserDTO userAccount, AmsAssetsCheckByYrHeaderDTO dtoParameter) {
		 super(userAccount, dtoParameter);
		 this.user = userAccount;
	}
	
	 
	 public SQLModel getPageQueryModel()
	  {
		    SQLModel sqlModel = new SQLModel();
		    List sqlArgs = new ArrayList();
		    AmsAssetsCheckByYrHeaderDTO dto = (AmsAssetsCheckByYrHeaderDTO)this.dtoParameter;
		    String transId=dto.getTransId();
		    String sqlStr = "";
		    if(!transId.equals("")){
		    	  sqlStr =  " SELECT EOC.RECEIVD_BY, EOC.RECEIVD_BY_NAME,EOC.COMPANY_CODE,EOC.COMPANY,EOC.BOOK_TYPE_CODE, EOC.BOOK_TYPE_NAME \n" 
					      + " FROM AMS_ASSETS_CHECK_BY_LINE EOC \n"
					      + " WHERE EOC.TRANS_ID=? \n";
		    	  sqlArgs.add(transId);
		    	
		    }else {
				  sqlStr = "SELECT"
				        + " EOC.COMPANY_CODE,EOC.COMPANY,EOC.BOOK_TYPE_CODE, EOC.BOOK_TYPE_NAME,0 RECEIVD_BY,'' RECEIVD_BY_NAME "
				        + " FROM"
				        + " ETS_OU_CITY_MAP EOC";
//				        + " WHERE"
//				        + " EOC.IS_TD='N' " ;//为了测试注释掉
		    }
		    sqlModel.setSqlStr(sqlStr);
		    sqlModel.setArgs(sqlArgs);
		    return sqlModel;
	  }
	 
	 
	 public SQLModel getLineModel()
	  {
		    SQLModel sqlModel = new SQLModel();
		    List sqlArgs = new ArrayList();
		    AmsAssetsCheckByYrHeaderDTO dto = (AmsAssetsCheckByYrHeaderDTO)this.dtoParameter;
		    String transId=dto.getTransId();
		    String sqlStr = "";
		    if(!transId.equals("")){
		    	  sqlStr =  " SELECT EOC.RECEIVD_BY, EOC.RECEIVD_BY_NAME,EOC.COMPANY_CODE,EOC.COMPANY,EOC.BOOK_TYPE_CODE, EOC.BOOK_TYPE_NAME \n" 
					      + " FROM AMS_ASSETS_CHECK_BY_LINE EOC \n"
					      + " WHERE EOC.TRANS_ID=? \n";
		    	  sqlArgs.add(transId);
		    	
		    }else {
				  sqlStr = "SELECT"
				        + " EOC.COMPANY_CODE,EOC.COMPANY,EOC.BOOK_TYPE_CODE, EOC.BOOK_TYPE_NAME,0 RECEIVD_BY,'' RECEIVD_BY_NAME "
				        + " FROM"
				        + " ETS_OU_CITY_MAP EOC";
//				        + " WHERE"
//				        + " EOC.IS_TD='N' " ;//为了测试注释掉
		    }
		    sqlModel.setSqlStr(sqlStr);
		    sqlModel.setArgs(sqlArgs);
		    return sqlModel;
	  }
	 
	 
	 
	 //插入工单任务行信息
	 public SQLModel getWorkLineInsert(AmsAssetsCheckByYrLineDTO dto)
	  {
		 SQLModel sqlModel = new SQLModel();
		 List sqlArgs = new ArrayList();
	     String sqlStr = " INSERT INTO AMS_ASSETS_CHECK_BY_LINE( \n"
											    	           + " TRANS_ID,\n"
											    	           + " LINE_ID,\n"
//											    	           + " CHECK_DATE,\n"
											    	           + " RECEIVD_BY,\n"
											    	           + " RECEIVD_BY_NAME,\n"
											    	           + " RB_ORGANIZATION_ID,\n"
											    	           + " TRANS_STATUS, \n"
											    	           + " TRANS_STATUS_VALUE, \n"
											    	           + " COMPANY_CODE, \n"
											    	           + " COMPANY, \n"
											    	           + " BOOK_TYPE_CODE, \n"
											    	           + " BOOK_TYPE_NAME \n"
	    	               + " )VALUES(\n"
											    	           + " ?,\n"
											    	           + " newid(),\n"
//											    	           + " getDate(),\n"
											    	           + " ?,\n"
											    	           + " ?,\n"
											    	           + " ?,\n"
											    	           + " ?,\n"
											    	           + " ?,\n"
											    	           + " ?,\n"
											    	           + " ?,\n"
											    	           + " ?,\n"
											    	           + " ? \n"
	    	              + " )\n";
	    sqlArgs.add(dto.getTransId());         
	    sqlArgs.add(dto.getReceivdBy());         
	    sqlArgs.add(dto.getReceivdByName());         
	    sqlArgs.add(dto.getRborganizationId());         
	    sqlArgs.add(dto.getTransStatus());         
	    sqlArgs.add(dto.getTransStatusValue());         
	    sqlArgs.add(dto.getCompanyCode());         
	    sqlArgs.add(dto.getCompany());         
	    sqlArgs.add(dto.getBookTypeCode());         
	    sqlArgs.add(dto.getBookTypeName());         
	    sqlModel.setSqlStr(sqlStr);
	    sqlModel.setArgs(sqlArgs);
	    return sqlModel;
	  }
	 
	 //插入任务头表信息
	public SQLModel getWorkHeaderInsert(AmsAssetsCheckByYrHeaderDTO dto) throws CalendarException
	  {
		 SQLModel sqlModel = new SQLModel();
		 List sqlArgs = new ArrayList();
	     String sqlStr = " INSERT INTO AMS_ASSETS_CHECK_BY_HEADER( \n"
											    	           + " TRANS_ID,\n"
											    	           + " TRANS_TYPE,\n"
											    	           + " TRANS_TYPE_VALUE,\n"
											    	           + " CREATION_DATE,\n"
											    	           + " CREATED_BY,\n"
											    	           + " CREATED_BY_NAME,\n"
											    	           + " ORGANIZATION_ID,\n"
											    	           + " BASIC_DATE_BEGIN,\n"
											    	           + " BASIC_DATE_END,\n"
											    	           + " TRANS_STATUS, \n"
											    	           + " TRANS_STATUS_VALUE, \n" 
											    	           + " TASK_START_DATE, \n" 
											    	           + " TASK_END_DATE \n"
	    	               + " )VALUES(\n"
											    	           + " ?,\n"
											    	           + " ?,\n"
											    	           + " ?,\n"
											    	           + " getDate(),\n"
											    	           + " ?,\n"
											    	           + " ?,\n"
											    	           + " ?,\n"
											    	           + " ?,\n"
											    	           + " ?,\n"
											    	           + " ?,\n"
											    	           + " ?, \n"
											    	           + " ?, \n"
											    	           + " ? \n"
	    	              + " )\n";
	    sqlArgs.add(dto.getTransId());         
	    sqlArgs.add(dto.getTransType());         
	    sqlArgs.add(dto.getTransTypeValue());         
	    sqlArgs.add(dto.getCreatedBy());         
	    sqlArgs.add(dto.getCreatedByName());         
	    sqlArgs.add(dto.getOrganizationId());         
	    sqlArgs.add(dto.getStartDate());         
	    sqlArgs.add(dto.getEndDate());         
	    sqlArgs.add(dto.getTransStatus());         
	    sqlArgs.add(dto.getTransStatusValue());         
	    sqlArgs.add(dto.getAssetsCreationDate());         
	    sqlArgs.add(dto.getAssetsEndDate());         
	    sqlModel.setSqlStr(sqlStr);
	    sqlModel.setArgs(sqlArgs);
	    return sqlModel;
	  }
}
