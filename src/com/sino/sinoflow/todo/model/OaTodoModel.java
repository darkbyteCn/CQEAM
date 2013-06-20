package com.sino.sinoflow.todo.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.hn.todo.util.HnOAConfig;
import com.sino.sinoflow.todo.constant.HNOAConstant;
import com.sino.sinoflow.todo.dto.OaTodoDTO;
import com.sino.sinoflow.todo.util.DateUtil;

public class OaTodoModel extends BaseSQLProducer{

	public OaTodoModel(BaseUserDTO userAccount, DTO dtoParameter) {
		super(userAccount, dtoParameter);
	}
	
	/**
	 * 统一待办接口表 OA_TODO数据插入
	 */
	public SQLModel getDataCreateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();

		List sqlArgs = new ArrayList();
		OaTodoDTO oaTodo = (OaTodoDTO) dtoParameter;
		StringBuilder sql = new StringBuilder(); 
		sql.append( " INSERT INTO \n " );
		sql.append( " OA_TODO \n " );
		sql.append( " ( \n " );
		sql.append( " DOC_ID, \n " );
		sql.append( " WORK_ID, \n " );
		sql.append( " USER_ID, \n " );
		sql.append( " TITLE, \n " );
		sql.append( " START_TIME, \n " );
		sql.append( " TODO_URL, \n " ); 
		sql.append( " PRI, \n " );
		sql.append( " TODO_TYPE, \n " );
		sql.append( " DOC_TYPE, \n " );
		sql.append( " SENDER, \n " ); 
		
		sql.append( " SOURCE_ID, \n " );
		sql.append( " SYS_ID, \n " );
		sql.append( " CLOSE_TIME, \n " );
		sql.append( " LAST_UPDATE_DATE \n " );
		
		sql.append( " ) VALUES (  \n " );
		sql.append( " ?, ?, ?, ?, ?,    ?, ?, ?, ?, ?,   ?, ?, ? , GETDATE() ) \n " ); 
		sqlArgs.add(oaTodo.getDocId());
		sqlArgs.add(oaTodo.getWorkId());
		sqlArgs.add(oaTodo.getUserId());
		sqlArgs.add(oaTodo.getTitle());
		sqlArgs.add(DateUtil.getSqlFromDate(oaTodo.getStartTime()));
		sqlArgs.add(oaTodo.getTodoUrl());
		sqlArgs.add(oaTodo.getPri());
		sqlArgs.add(oaTodo.getTodoType());
		sqlArgs.add(oaTodo.getDocType());
		sqlArgs.add(oaTodo.getSender());
		sqlArgs.add(oaTodo.getSourceId());
		sqlArgs.add(oaTodo.getSysId());
		sqlArgs.add(DateUtil.getSqlFromDate(oaTodo.getCloseTime()));
		// sqlArgs.add(oaTodo.getLastUpdateDate());

		sqlModel.setSqlStr( sql.toString() );
		sqlModel.setArgs(sqlArgs);

		return sqlModel;
	}
 
	/**
	 * 删除OA待办
	 */
	public SQLModel getDeleteByPrimaryKeyModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		OaTodoDTO oaTodo = (OaTodoDTO) dtoParameter;
		
		StringBuilder sql = new StringBuilder();
		
		sql.append( " DELETE \n " );
		sql.append( " OA_TODO  \n " );
		sql.append( " WHERE \n " );
		sql.append( " DOC_ID = ? \n " );
		sql.append( " AND WORK_ID = ? \n " );
		sql.append( " AND USER_ID = ? \n " );
		sql.append( " AND SYS_ID = ? \n " );
		sql.append( " AND SOURCE_ID = ? \n " );  

		sqlArgs.add(oaTodo.getDocId());
		sqlArgs.add(oaTodo.getWorkId());
		sqlArgs.add(oaTodo.getUserId());
		sqlArgs.add(oaTodo.getSysId());
		sqlArgs.add(oaTodo.getSourceId());
		sqlModel.setSqlStr( sql.toString() );
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	
	/**
	 * 
	 * 提交时，将新待办写入OA_TODO表
	 * @return SQLModel
	 */
	public SQLModel getInsertOATodoFromEAMModel( String appId  ) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList(); 
		OaTodoDTO dto = ( OaTodoDTO ) this.dtoParameter;
		StringBuilder sql = new StringBuilder();
		sql.append(" INSERT INTO \n ");
		sql.append(" OA_TODO \n ");
		sql.append(" SELECT \n ");
		sql.append(" SAI.SFACT_APPL_ID DOC_ID,  \n ");        	//外部系统文档唯一编号（没有，需要约定）
		sql.append(" SAI.SFACT_ACT_ID WORK_ID,   \n ");   		//外部系统待办的唯一编号（B+ID、Y+id）（确认）
		
		sql.append(" LOWER( dbo.APP_GET_OA_NAME(  \n" );
		sql.append(" 		dbo.NVL( \n ");
		sql.append(" 				dbo.SFK_GET_DELEGATE_USER(SAI.SFACT_TASK_USERS),\n ");
		sql.append(" 				SAI.SFACT_TASK_USERS ) " );
		sql.append(" 		)) USER_ID, \n "); //用户名
		
		sql.append(" SAI.SFACT_APPL_COLUMN_2 ||'(' || SAI.SFACT_APPL_COLUMN_1 || ')' TITLE,   \n "); //待办标题
		sql.append(" SAI.SFACT_FROM_DATE START_TIME,  \n ");  	//外部系统待办生成的时间（到达时间）
		sql.append(" ('" + HnOAConfig.getEam_url() + "portalLogin.jsp?source=oa&is_td='|| dbo.APP_USER_IS_TD( SAI.SFACT_TASK_USERS ) ||'&sf_actID=' || SAI.SFACT_ACT_ID ||  '&sf_appMask=' || CONVERT(VARCHAR,SA.ALLOW_OPERATION) || '&sf_caseID=' || SAI.SFACT_CASE_ID ) TODO_URL ,  \n "); 
		sql.append(" ? , \n "); 		//PRI	
		sql.append(" ? , \n "); 		//TODO_TYPE
		sql.append(" ? , \n "); 		//DOC_TYPE
		sql.append(" LOWER(SAI.SFACT_COMPOSE_USER) SENDER, \n" ); //起草人
		sql.append(" ? , \n "); 		//SOURCE_ID
		sql.append(" ? , \n "); 		//SYS_ID
		sql.append(" SAI.SFACT_SIGN_DATE , \n "); 		//CLOSE_TIME 
		sql.append(" GETDATE() \n "); 	//LAST_UPDATE_DATE 
		sql.append(" FROM  \n "); 
		sql.append(" SF_APPLICATION SA,  \n "); 
		sql.append(" SF_ACT_INFO SAI \n "); 
		sql.append(" WHERE  \n "); 
		//AND
		sql.append(" SAI.SFACT_APPDEF_ID = SA.APP_ID \n "); 
		sql.append(" AND (SAI.SFACT_SIGN_STATUS = 1 \n "); 
		sql.append(" AND SAI.SFACT_SUSPEND_FLAG <> 1) \n "); 
		sql.append(" AND SAI.SFACT_COMPLETE_STATUS <> 1 \n ");    
		sql.append(" AND SAI.SFACT_SIGN_USER <> 'SYSTEM' \n "); 
		sql.append(" AND SAI.SFACT_TASK_NAME <> 'SPLIT' \n "); 
		sql.append(" AND SAI.SFACT_TASK_NAME <> 'JOIN' \n "); 
		sql.append(" AND SAI.SFACT_APPL_ID = ?  \n "); 
		
		sql.append(" AND NOT EXISTS(  \n "); 
		sql.append(" SELECT NULL \n "); 
		sql.append(" FROM \n "); 
		sql.append(" OA_TODO OT \n "); 
		sql.append(" WHERE  \n "); 
		sql.append(" OT.DOC_ID = SAI.SFACT_APPL_ID  \n "); 
		sql.append(" AND OT.WORK_ID = SAI.SFACT_ACT_ID ) \n "); 
		
		sqlArgs.add( dto.getPri() );	
		sqlArgs.add( HNOAConstant.OA_TODO_TYPE_OPEN );	
		sqlArgs.add( dto.getDocType() );	
		sqlArgs.add( dto.getSourceId() );	
		sqlArgs.add( dto.getSysId() );	
		
		sqlArgs.add( appId );
		sqlModel.setSqlStr( sql.toString() );
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	
	/**
	 * 
	 * 提交时，将新待办写入OA_TODO表
	 * @return SQLModel
	 */
	public SQLModel getInsertOATodoDeleFromEAMModel( String appId  ) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList(); 
		OaTodoDTO dto = ( OaTodoDTO ) this.dtoParameter;
		StringBuilder sql = new StringBuilder();
		sql.append(" INSERT INTO \n ");
		sql.append(" OA_TODO_DELE \n ");
		sql.append(" SELECT \n ");
		sql.append(" SAI.SFACT_APPL_ID DOC_ID,  \n ");        	//外部系统文档唯一编号（没有，需要约定）
		sql.append(" SAI.SFACT_ACT_ID WORK_ID,   \n ");   		//外部系统待办的唯一编号（B+ID、Y+id）（确认）
//		sql.append(" dbo.NVL(LOWER(dbo.SFK_GET_DELEGATE_USER(SAI.SFACT_TASK_USERS)),\n ");
//		sql.append(" LOWER(SAI.SFACT_TASK_USERS)) USER_ID, \n "); //用户名
		
		sql.append(" LOWER( dbo.APP_GET_OA_NAME(  \n" );
		sql.append(" 		dbo.NVL( \n ");
		sql.append(" 				dbo.SFK_GET_DELEGATE_USER(SAI.SFACT_TASK_USERS),\n ");
		sql.append(" 				SAI.SFACT_TASK_USERS ) " );
		sql.append(" 		)) USER_ID, \n "); //用户名
		
		sql.append(" SAI.SFACT_APPL_COLUMN_2 ||'(' || SAI.SFACT_APPL_COLUMN_1 || ')' TITLE,   \n "); //待办标题
		sql.append(" SAI.SFACT_FROM_DATE START_TIME,  \n ");  	//外部系统待办生成的时间（到达时间）
		sql.append(" ('" + HnOAConfig.getEam_url() + "portalLogin.jsp?source=oa&is_td='|| dbo.APP_USER_IS_TD( SAI.SFACT_TASK_USERS ) ||'&sf_actID=' || SAI.SFACT_ACT_ID ||  '&sf_appMask=' || CONVERT(VARCHAR,SA.ALLOW_OPERATION) || '&sf_caseID=' || SAI.SFACT_CASE_ID ) TODO_URL ,  \n "); 
		sql.append(" ? , \n "); 		//PRI	
		sql.append(" ? , \n "); 		//TODO_TYPE
		sql.append(" ? , \n "); 		//DOC_TYPE
		sql.append(" LOWER(SAI.SFACT_COMPOSE_USER) SENDER, \n" ); //起草人
		sql.append(" ? , \n "); 		//SOURCE_ID
		sql.append(" ? , \n "); 		//SYS_ID
		sql.append(" GETDATE() , \n "); 		//CLOSE_TIME 
		sql.append(" GETDATE() \n "); 	//LAST_UPDATE_DATE 
		sql.append(" FROM  \n "); 
		sql.append(" SF_APPLICATION SA,  \n "); 
		sql.append(" SF_ACT_INFO SAI \n "); 
		sql.append(" WHERE  \n "); 
		sql.append(" SAI.SFACT_APPDEF_ID = SA.APP_ID \n "); 
		sql.append(" AND (SAI.SFACT_SIGN_STATUS = 1 \n "); 
		sql.append(" AND SAI.SFACT_SUSPEND_FLAG <> 1) \n "); 
		sql.append(" AND SAI.SFACT_COMPLETE_STATUS <> 1 \n ");   

		sql.append(" AND SAI.SFACT_SIGN_USER <> 'SYSTEM' \n "); 
		sql.append(" AND SAI.SFACT_TASK_NAME <> 'SPLIT' \n "); 
		sql.append(" AND SAI.SFACT_TASK_NAME <> 'JOIN' \n "); 
		sql.append(" AND SAI.SFACT_APPL_ID = ?  \n "); 
		
		//+ "       AND SAI.SFACT_SIGN_DATE > SYSDATE - 7";
		//OAConstant.OA_TODO_TYPE_OPEN
		sqlArgs.add( dto.getPri() );	
		sqlArgs.add( HNOAConstant.OA_TODO_TYPE_CLOSE );	
		sqlArgs.add( dto.getDocType() );	
		sqlArgs.add( dto.getSourceId() );	
		sqlArgs.add( dto.getSysId() );	
		
//		sqlArgs.add( HNOAConstant.OA_TODO_PRI_DEFAULT );	
//		sqlArgs.add( HNOAConstant.OA_TODO_TYPE_CLOSE );	
//		sqlArgs.add( HNOAConstant.OA_TODO_DOC_TYPE );	
//		sqlArgs.add( HNOAConstant.OA_TODO_SOURCE_ID );	
//		sqlArgs.add( HNOAConstant.OA_TODO_SYSID );	
		sqlArgs.add( appId );
		sqlModel.setSqlStr( sql.toString() );
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
 
//	/**
//	 * 
//	 * 提交时，将新待办写入OA_TODO表
//	 * @return SQLModel
//	 */
//	public SQLModel getInsertOATodoDeleFromEAMModel( String actId , String appId  ) {
//		SQLModel sqlModel = new SQLModel();
//		List sqlArgs = new ArrayList(); 
//		OaTodoDTO dto = ( OaTodoDTO ) this.dtoParameter;
//		StringBuilder sql = new StringBuilder();
//		sql.append(" INSERT INTO \n ");
//		sql.append(" OA_TODO_DELE \n ");
//		sql.append(" SELECT * FROM \n ");
//		sql.append(" (SELECT \n ");
//		sql.append(" 	SAI.SFACT_APPL_ID DOC_ID,  \n ");        	//外部系统文档唯一编号（没有，需要约定）
//		sql.append(" 	SAI.SFACT_ACT_ID WORK_ID,   \n ");   		//外部系统待办的唯一编号（B+ID、Y+id）（确认）
//		sql.append(" 	LOWER(SU.LOGIN_NAME) USER_ID,   \n "); 	//用户UID（OA登陆名）
//		sql.append(" 	SAI.SFACT_APPL_COLUMN_2 ||'(' || SAI.SFACT_APPL_COLUMN_1 || ')' TITLE,   \n "); //待办标题
//		sql.append(" 	SAI.SFACT_FROM_DATE START_TIME,  \n ");  	//外部系统待办生成的时间（到达时间）
//		sql.append(" 	('" + HnOAConfig.getEam_url() + "portalLogin.jsp?source=oa&is_td='|| dbo.APP_USER_IS_TD( SAI.SFACT_TASK_USERS ) ||'&sf_actID=' || SAI.SFACT_ACT_ID ||  '&sf_appMask==' || CONVERT(VARCHAR,SA.ALLOW_OPERATION) || '&sf_caseID=' || SAI.SFACT_CASE_ID ) TODO_URL ,  \n "); 
//		sql.append(" 	? , \n "); 		//PRI
//		sql.append(" 	? , \n "); 		//TODO_TYPE
//		sql.append(" 	? , \n "); 		//DOC_TYPE
//		sql.append(" 	LOWER(SU.LOGIN_NAME) SENDER,   \n "); 		//起草人
//		sql.append(" 	? , \n "); 		//SOURCE_ID
//		sql.append(" 	? , \n "); 		//SYS_ID
//		sql.append(" 	SAI.SFACT_SIGN_DATE , \n "); 		//CLOSE_TIME 
//		sql.append(" 	GETDATE() \n "); 	//LAST_UPDATE_DATE 
//		sql.append("   FROM  \n "); 
//		sql.append(" 	SF_ACT_LOG SAI,  \n "); 
//		sql.append(" 	SF_APPLICATION SA,  \n "); 
//		sql.append(" 	SF_USER SU  \n "); 
//		sql.append(" 	WHERE  \n "); 
//		//AND
//		sql.append(" 	(SAI.SFACT_SIGN_STATUS = 1 \n "); 
//		sql.append(" 	AND SAI.SFACT_SUSPEND_FLAG <> 1) \n "); 
////		sql.append(" 	AND SAI.SFACT_COMPLETE_STATUS <> 1 \n ");   
//		
//		//OATO表中没有相应记录 		
//		sql.append(" AND SAI.SFACT_ACT_ID = ? \n "); 
//		sql.append(" AND SAI.SFACT_APPL_ID = ? \n "); 
//		
//		sql.append(" UNION ALL \n ");
//		
//		sql.append(" SELECT \n ");
//		sql.append(" 	SAR.SFACT_APPL_ID DOC_ID,  \n ");        	//外部系统文档唯一编号（没有，需要约定）
//		sql.append(" 	SAR.SFACT_ACT_ID WORK_ID,   \n ");   		//外部系统待办的唯一编号（B+ID、Y+id）（确认）
//		sql.append(" 	LOWER(SU.LOGIN_NAME) USER_ID,   \n "); 	//用户UID（OA登陆名）
//		sql.append(" 	SAR.SFACT_APPL_COLUMN_2 ||'(' || SAR.SFACT_APPL_COLUMN_1 || ')' TITLE,   \n "); //待办标题
//		sql.append(" 	SAR.SFACT_FROM_DATE START_TIME,  \n ");  	//外部系统待办生成的时间（到达时间）
//		sql.append(" 	('" + HnOAConfig.getEam_url() + "portalLogin.jsp?source=oa&is_td='|| dbo.APP_USER_IS_TD( SAI.SFACT_TASK_USERS ) ||'&sf_actID=' || SAR.SFACT_ACT_ID ||  '&sf_appMask==' || CONVERT(VARCHAR,SA.ALLOW_OPERATION) || '&sf_caseID=' || SAR.SFACT_CASE_ID ) TODO_URL   \n "); 
//		sql.append(" 	? , \n "); 		//PRI
//		sql.append(" 	? , \n "); 		//TODO_TYPE
//		sql.append(" 	? , \n "); 		//DOC_TYPE
//		sql.append(" 	LOWER(SU.LOGIN_NAME) SENDER,   \n "); 		//起草人
//		sql.append(" 	? , \n "); 		//SOURCE_ID
//		sql.append(" 	? , \n "); 		//SYS_ID
//		sql.append(" 	SAR.SFACT_SIGN_DATE , \n "); 		//CLOSE_TIME 
//		sql.append(" 	GETDATE() \n "); 	//LAST_UPDATE_DATE 
//		sql.append("   FROM  \n "); 
//		sql.append(" 	SF_ACT_ARCHIVE SAR,  \n "); 
//		sql.append(" 	SF_APPLICATION SA,  \n "); 
//		sql.append(" 	SF_USER SU  \n "); 
//		sql.append(" 	WHERE  \n "); 
//		//AND
//		sql.append(" 	(SAR.SFACT_SIGN_STATUS = 1 \n "); 
//		sql.append(" 	AND SAR.SFACT_SUSPEND_FLAG <> 1) \n "); 
////		sql.append(" 	AND SAI.SFACT_COMPLETE_STATUS <> 1 \n ");   
//		//OATO表中没有相应记录 		
//		sql.append(" AND SAR.SFACT_ACT_ID = ?  \n "); 
//		sql.append(" AND SAR.SFACT_APPL_ID = ? ) \n "); 
//		sql.append(" tmp \n "); 
//		
//		//+ "       AND SAI.SFACT_SIGN_DATE > SYSDATE - 7";
//		//OAConstant.OA_TODO_TYPE_OPEN
//		sqlArgs.add( dto.getPri() );	
//		sqlArgs.add( HNOAConstant.OA_TODO_TYPE_CLOSE );	
//		sqlArgs.add( dto.getDocType() );	
//		sqlArgs.add( dto.getSourceId() );	
//		sqlArgs.add( dto.getSysId() );	
//		
////		sqlArgs.add( OAConstant.OA_TODO_PRI_DEFAULT );	
////		sqlArgs.add( OAConstant.OA_TODO_TYPE_OPEN );	
////		sqlArgs.add( OAConstant.OA_TODO_DOC_TYPE );	
////		sqlArgs.add( OAConstant.OA_TODO_SOURCE_ID );	
////		sqlArgs.add( OAConstant.OA_TODO_SYSID );	
//		sqlArgs.add( actId );
//		sqlArgs.add( appId );
//		
//		sqlArgs.add( dto.getPri() );	
//		sqlArgs.add( HNOAConstant.OA_TODO_TYPE_CLOSE );	
//		sqlArgs.add( dto.getDocType() );	
//		sqlArgs.add( dto.getSourceId() );	
//		sqlArgs.add( dto.getSysId() );	
////		sqlArgs.add( OAConstant.OA_TODO_PRI_DEFAULT );	
////		sqlArgs.add( OAConstant.OA_TODO_TYPE_OPEN );	
////		sqlArgs.add( OAConstant.OA_TODO_DOC_TYPE );	
////		sqlArgs.add( OAConstant.OA_TODO_SOURCE_ID );	
////		sqlArgs.add( OAConstant.OA_TODO_SYSID );	
//		sqlArgs.add( actId );
//		sqlArgs.add( appId );
//		
//		sqlModel.setSqlStr( sql.toString() );
//		sqlModel.setArgs(sqlArgs);
//		return sqlModel;
//	}
}
