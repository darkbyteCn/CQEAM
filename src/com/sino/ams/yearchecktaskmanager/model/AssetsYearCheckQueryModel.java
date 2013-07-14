package com.sino.ams.yearchecktaskmanager.model;



import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.yearchecktaskmanager.dto.AssetsYearCheckTaskQueryDTO;


public class AssetsYearCheckQueryModel extends AMSSQLProducer {
	public AssetsYearCheckQueryModel(SfUserDTO userAccount, AssetsYearCheckTaskQueryDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
			List sqlArgs = new ArrayList();
			AssetsYearCheckTaskQueryDTO dto = (AssetsYearCheckTaskQueryDTO)dtoParameter;
			String taskType=dto.getTaskType();
			String sqlStr="";
			if(taskType.equals("send")){
				sqlStr+=" SELECT CTOL.TASK_ORDER_ID,CTOL.ORDER_NUMBER,CTOL.ORDER_NAME,CTOL.IMPLEMENT_BY,\n" +
					" CTOL.IMPLEMENT_NAME,CTOL.ORDER_TYPE,CTOL.IMPLEMENT_ORGANIZATION_ID,\n" +
					" CTOL.IMPLEMENT_DEPT_ID,CTOL.IMPLEMNET_DEPT_NAME  \n" +
					" FROM AMS_ASSETS_YAER_CHECK_TASK_ORDER_HEADER CTOH, \n" +
					" AMS_ASSETS_YAER_CHECK_TASK_ORDER_LINE   CTOL \n" +
					" WHERE CTOH.DISTRUBTE_BY=? \n" +
					" AND   CTOH.HEADER_ID=CTOL.HEADER_ID \n"+
			        " AND   CTOL.ORDER_NUMBER LIKE '%' || dbo.NVL(?, CTOL.ORDER_NUMBER) || '%' " +
			        " AND   CTOL.ORDER_NAME LIKE '%' || dbo.NVL(?, CTOL.ORDER_NAME) || '%'";
			}else if(taskType.equals("received")){
				sqlStr+=" SELECT  CTOH.DISTRUBTE_BY, CTOH.DISTRUBTE_BY_NAME,CTOL.ORDER_NAME,CTOL.ORDER_NUMBER," +
						" SUBSTRING(CONVERT(VARCHAR(32),CTOH.CREATION_DATE,112),1,8) SEND_DATE " +
						" FROM "+
				        " AMS_ASSETS_YAER_CHECK_TASK_ORDER_LINE CTOL,"+
				        " AMS_ASSETS_YAER_CHECK_TASK_ORDER_HEADER CTOH"+
						" WHERE CTOL.HEADER_ID=CTOH.HEADER_ID"+
						" AND CTOL.IMPLEMENT_BY=?"+
						" AND   CTOL.ORDER_NUMBER LIKE '%' || dbo.NVL(?, CTOL.ORDER_NUMBER) || '%' " +
				        " AND   CTOL.ORDER_NAME LIKE '%' || dbo.NVL(?, CTOL.ORDER_NAME) || '%'";
			}
			sqlArgs.add(userAccount.getUserId());
			sqlArgs.add(dto.getOrderNumber());
			sqlArgs.add(dto.getOrderName());
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
