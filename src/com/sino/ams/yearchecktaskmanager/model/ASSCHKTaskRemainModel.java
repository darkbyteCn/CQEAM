package com.sino.ams.yearchecktaskmanager.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.yearchecktaskmanager.dto.ASSCHKTaskRemainDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.framework.sql.BaseSQLProducer;

public class ASSCHKTaskRemainModel extends BaseSQLProducer {
	  
	private SfUserDTO sfUser = null;
	  
	  public ASSCHKTaskRemainModel(SfUserDTO userAccount, ASSCHKTaskRemainDTO dtoParameter) {
	        super(userAccount, dtoParameter);
	        sfUser = userAccount;
	  }
	  
	  //获取盘点任务提醒列表
	  public SQLModel getData() {
	        SQLModel sqlModel = new SQLModel();
	        List sqlArgs = new ArrayList();
	        String sqlStr=
				        	" SELECT TOP 1  CTOL.ORDER_NUMBER,CTOL.ORDER_NAME,CTOH.CREATION_DATE,CTOL.ORDER_TYPE, \n"+
	        	"           CTB.CHECK_TASK_DATE_FROM,CTB.CHECK_TASK_DATE_END,\n" +
	        	"           CTOL.IMPLEMNET_ROLE_NAME \n"+
	        	"       	FROM AMS_ASSETS_YAER_CHECK_TASK_ORDER_HEADER CTOH, \n"+
	        	"       	AMS_ASSETS_YAER_CHECK_TASK_BASEDATE CTB, \n"+
	        	"       	AMS_ASSETS_YAER_CHECK_TASK_ORDER_LINE CTOL \n"+
	        	"       	WHERE CTOL.HEADER_ID=CTOH.HEADER_ID \n"+
	        	"       	AND   CTOL.IMPLEMENT_BY=? \n"+
	        	"       	AND   CTOL.IMPLEMENT_ORGANIZATION_ID=? \n"+
	        	"       	AND   CTOL.IMPLEMENT_ORGANIZATION_ID=CTB.ORGANIZATION_ID \n"+ 
	        	"       	AND   CTB.ENALBED='Y' \n"+
	        	"       	AND   CTOL.ORDER_STATUS NOT IN('DO_CANCLE','DO_RETURN') \n"+  
	        	"       	AND   CTB.BASE_DATE_TYPE='1'  \n"+  
	        	"       	ORDER BY CTB.CHECK_TASK_DATE_FROM \n";
	        
	        sqlArgs.add(sfUser.getUserId());
	        sqlArgs.add(sfUser.getOrganizationId());
	        sqlModel.setArgs(sqlArgs);
	        sqlModel.setSqlStr(sqlStr);
	        return sqlModel;
	 }
	  
	  public SQLModel getAllTaskRemainModel(String taskName) {
	        SQLModel sqlModel = new SQLModel();
	        ArrayList sqlArgs = new ArrayList();
//	        StringBuffer sqlBuffer  = new StringBuffer();
//	        sqlBuffer.append(" SELECT AH.TRANS_TYPE_VALUE,AH.CREATION_DATE,AH.TASK_START_DATE,AH.TASK_END_DATE \n")
//	        		 .append(" FROM AMS_ASSETS_CHECK_BY_HEADER AH,AMS_ASSETS_CHECK_BY_LINE AL \n")
//	        		 .append(" where AH.TRANS_ID = AL.TRANS_ID \n")
//	        		 .append(" and AH.TRANS_STATUS <>'COMPLETE' and AL.RECEIVD_BY = ? \n");
//	        if(taskName!=null && !taskName.equals("")){
//	        	sqlBuffer.append(" and AH.TRANS_TYPE_VALUE like '%"+taskName.trim()+"%' ");
//	        	
//	        }
	        String sqlStr=
				        	" SELECT   CTOL.ORDER_NUMBER,CTOL.ORDER_NAME,CTOH.CREATION_DATE, CTOL.ORDER_TYPE," +
				        	" CTOH.DISTRUBTE_BY_NAME," +//下法人
				        	" CTB.CHECK_BASE_DATE_CITY, " +//基准日
				        	" CTB.CHECK_BASE_DATE_FROM,     " +
				        	" CTB.CHECK_BASE_DATE_END,       \n"+
							"           CTB.CHECK_TASK_DATE_FROM,CTB.CHECK_TASK_DATE_END \n"+
							"       	FROM AMS_ASSETS_YAER_CHECK_TASK_ORDER_HEADER CTOH, \n"+
							"       	AMS_ASSETS_YAER_CHECK_TASK_BASEDATE CTB, \n"+
							"       	AMS_ASSETS_YAER_CHECK_TASK_ORDER_LINE CTOL \n"+
							"       	WHERE CTOL.HEADER_ID=CTOH.HEADER_ID \n"+
							"       	AND   CTOL.IMPLEMENT_BY=? \n"+
							"       	AND   CTOL.IMPLEMENT_ORGANIZATION_ID=? \n"+
							"       	AND   CTOL.IMPLEMENT_ORGANIZATION_ID=CTB.ORGANIZATION_ID \n"+ 
							"       	AND   CTB.ENALBED='Y' \n"+
							"       	AND   CTOL.ORDER_STATUS NOT IN('DO_CANCLE','DO_RETURN') \n"+  
							"       	AND   CTB.BASE_DATE_TYPE='1'  \n"; 
//							"       	ORDER BY CTB.CHECK_TASK_DATE_FROM \n";
	        if(taskName!=null && !taskName.equals("")){
	        	sqlStr+=" AND CTOL.ORDER_NAME LIKE '%"+taskName.trim()+"%'";
	        }
	        
	        sqlArgs.add(sfUser.getUserId());
	        sqlArgs.add(sfUser.getOrganizationId());
	        sqlModel.setArgs(sqlArgs);
//	        sqlModel.setSqlStr(sqlBuffer.toString());
	        sqlModel.setSqlStr(sqlStr);
	        return sqlModel;
	    }

}
