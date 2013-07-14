package com.sino.ams.yearchecktaskmanager.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.yearchecktaskmanager.dto.AssetsYearCheckTaskHeaderDTO;
import com.sino.ams.yearchecktaskmanager.dto.AssetsYearCheckTaskLineDTO;
import com.sino.ams.yearchecktaskmanager.util.CommonUtil;
import com.sino.base.db.sql.model.SQLModel;

public class AssetsYearCheckTaskDeptModel extends AMSSQLProducer{
	private SfUserDTO user;
	public AssetsYearCheckTaskDeptModel(SfUserDTO userAccount, AssetsYearCheckTaskHeaderDTO dtoParameter) {
		super(userAccount, dtoParameter);
		user = (SfUserDTO)userAccount;
	}
	
	public SQLModel getCreateTaksLineModel(AssetsYearCheckTaskLineDTO yearLineDTO) {
		SQLModel sqlModel = new SQLModel();
	    List sqlArgs = new ArrayList();
	    StringBuffer sqlStr = new StringBuffer();
	    sqlStr.append(" insert into AMS_ASSETS_YAER_CHECK_TASK_ORDER_LINE(  ")
	          .append(" TASK_ORDER_ID,HEADER_ID,ORDER_NAME,ORDER_NUMBER,IMPLEMENT_BY, ")
	          .append(" IMPLEMENT_NAME,ORDER_TYPE,ORDER_STATUS,ORDER_LEVEL,IS_LAST_LEVLE, ")
	          .append(" IMPLEMENT_ORGANIZATION_ID,IMPLEMNET_DEPT_NAME,IMPLEMENT_DEPT_ID, ")
	          .append(" IMPLEMNET_ROLE_NAME,CREATION_DATE,LAST_UPDATE_DATE_BY,LAST_UPDATE_DATE) ")
	          .append(" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,getdate(),?,getdate()) ");
	    sqlArgs.add(yearLineDTO.getTaskOrderId());
	    sqlArgs.add(yearLineDTO.getHeaderId());
	    sqlArgs.add(yearLineDTO.getOrderName());
	    sqlArgs.add(yearLineDTO.getOrderNumber());
	    sqlArgs.add(yearLineDTO.getImplementBy());
	    sqlArgs.add(yearLineDTO.getImplementName());
	    sqlArgs.add(yearLineDTO.getOrderType());
	    sqlArgs.add(yearLineDTO.getOrderStatus());
	    sqlArgs.add(yearLineDTO.getOrderLevel());
	    sqlArgs.add(yearLineDTO.getIsLastLevel());
	    sqlArgs.add(yearLineDTO.getImplementOrganizationId());
	    sqlArgs.add(yearLineDTO.getImplementDeptName());
	    sqlArgs.add(yearLineDTO.getImplementDeptId());
	    sqlArgs.add(yearLineDTO.getImplementRoleName());
	    sqlArgs.add(user.getUserId());
	    
	    sqlModel.setSqlStr(sqlStr.toString());
	    sqlModel.setArgs(sqlArgs);
	    return sqlModel;
	}

	public SQLModel getcreateTaskHeaderModel(AssetsYearCheckTaskHeaderDTO headerDto) {
		SQLModel sqlModel = new SQLModel();
	    List sqlArgs = new ArrayList();
	    StringBuffer sqlStr = new StringBuffer();
	    sqlStr.append(" insert into AMS_ASSETS_YAER_CHECK_TASK_ORDER_HEADER(  ")
	          .append(" HEADER_ID,DISTRUBTE_BY,DISTRUBTE_BY_NAME,ASSETS_TYPE, ")
	          .append(" NON_ADDR_ASS_DISTRIBUTE_METHOD,NON_ADDR_ASS_CATEGORY,PARENT_ORDER_NUMBER,ASSETS_BIG_CLASS,CREATION_DATE) ")
	          .append(" values(?,?,?,?,?,?,?,?,getdate()) ");
	   
	    sqlArgs.add(headerDto.getHeaderId());
	    sqlArgs.add(user.getUserId());
	    sqlArgs.add(user.getUsername());
	    sqlArgs.add(headerDto.getAssetsType());
	    sqlArgs.add(headerDto.getNonAddressDistributeMethod());
	    sqlArgs.add(headerDto.getNonAddressCategory());
	    sqlArgs.add(headerDto.getParentOrderNumber());
	    sqlArgs.add(headerDto.getAssetsBigClass());
	    sqlModel.setSqlStr(sqlStr.toString());
	    sqlModel.setArgs(sqlArgs);
	    return sqlModel;
	}

}
