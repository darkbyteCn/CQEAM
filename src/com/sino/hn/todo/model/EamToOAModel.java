package com.sino.hn.todo.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.hn.todo.log.OaLogConstant;
import com.sino.sinoflow.todo.constant.HNOAConstant;
import com.sino.sinoflow.todo.dto.OaTodoDTO;
import com.sino.sinoflow.todo.model.OaTodoModel;

public class EamToOAModel extends OaTodoModel{
	OaTodoDTO oaTodoDTO = null;
	public EamToOAModel(BaseUserDTO userAccount, DTO dtoParameter) {
		super(userAccount, dtoParameter);
		oaTodoDTO = (OaTodoDTO) dtoParameter;
	}
	
	private String getDBName(OaTodoDTO oaTodoDTO) {
		String dBName = OaLogConstant.DB_OA_TODO;
		if (oaTodoDTO.getTodoType().equals(HNOAConstant.OA_TODO_TYPE_OPEN)) {
			dBName = OaLogConstant.DB_OA_TODO;
		} else if (oaTodoDTO.getTodoType().equals(
				HNOAConstant.OA_TODO_TYPE_CLOSE)) {
			dBName = OaLogConstant.DB_OA_TODO_DELE;
		}
		return dBName;
	}
	
	private String getLogDBName(OaTodoDTO oaTodoDTO) {
		String logDBName = OaLogConstant.DB_OA_TODO_LOG;
		if (oaTodoDTO.getTodoType().equals(HNOAConstant.OA_TODO_TYPE_OPEN)) {
			logDBName = OaLogConstant.DB_OA_TODO_LOG;
		} else if (oaTodoDTO.getTodoType().equals(
				HNOAConstant.OA_TODO_TYPE_CLOSE)) {
			logDBName = OaLogConstant.DB_OA_TODO_DELE_LOG;
		}
		return logDBName;
	}
	
	public SQLModel getEamToOASQLModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		
		StringBuilder sb = new StringBuilder();
		
		sb.append( " SELECT \n " );
		sb.append( " DOC_ID, \n " );
		sb.append( " WORK_ID, \n " );
		sb.append( " USER_ID, \n " );
		sb.append( " TITLE, \n " );
		sb.append( " START_TIME, \n " );
		sb.append( " TODO_URL, \n " ); 
		sb.append( " PRI, \n " );
		sb.append( " TODO_TYPE, \n " );
		sb.append( " DOC_TYPE, \n " );
		sb.append( " SENDER, \n " ); 
		
		sb.append( " SOURCE_ID, \n " );
		sb.append( " SYS_ID, \n " );
		sb.append( " CLOSE_TIME, \n " );
		sb.append( " LAST_UPDATE_DATE \n " );
		sb.append( " FROM \n " );
		sb.append( " 	" + getDBName( oaTodoDTO )+ " OT  \n " );
		sb.append( " WHERE \n " );
		sb.append( " 	DATEADD( DD , -5 , GETDATE() ) < OT.START_TIME  \n " );
		sb.append( "    AND NOT EXISTS  \n " );
		sb.append( "     ( \n " );
		sb.append( " ¡¡¡¡SELECT \n " );
		sb.append( "         NULL  \n " );
		sb.append( "     FROM \n " );
		sb.append( "         " + getLogDBName( oaTodoDTO )+ " OTL  \n " );
		sb.append( "     WHERE \n " );
		sb.append( "         OTL.DOC_ID = OT.DOC_ID  \n " );
		sb.append( "         AND OTL.WORK_ID = OT.WORK_ID \n " ); 
		sb.append( "         AND OTL.RESULT_CODE = '1' \n " ); 
		sb.append( " 	) \n " );
		
		sb.append( " AND NOT EXISTS  \n " );
		sb.append( " ( \n " );
		sb.append( " 	SELECT \n " );
		sb.append( " 		NULL \n " );
		sb.append( " 	FROM \n " ); 
		sb.append( " 		(SELECT \n " );
		sb.append( " 			DOC_ID, \n " );
		sb.append( " 			WORK_ID , \n " );
		sb.append( " 			COUNT(1) T_COUNT  \n " ); 
		sb.append( " 		FROM \n " );
		sb.append( " 			" + getLogDBName( oaTodoDTO )+ "  \n " );
		sb.append( " 		GROUP BY \n " );
		sb.append( " 			DOC_ID, \n " );
		sb.append( " 			WORK_ID ) OTL_TMP  \n " );
		sb.append( " 	WHERE  \n " );
		sb.append( " 	OTL_TMP.DOC_ID = OT.DOC_ID  \n " );
		sb.append( " 	AND OTL_TMP.WORK_ID = OT.WORK_ID  \n " );
		sb.append( " 	AND OTL_TMP.T_COUNT >= 3 \n " ); 
		sb.append( " ) \n " );
		
		if (oaTodoDTO.getTodoType().equals(HNOAConstant.OA_TODO_TYPE_OPEN)) {
			sb.append( " AND EXISTS( \n " );
			sb.append( " SELECT \n " );
			sb.append( " NULL \n " );
			sb.append( " FROM \n " );
			sb.append( " SF_ACT_INFO SAI \n " );
			sb.append( " WHERE \n " );
			sb.append( " SAI.SFACT_APPL_ID = OT.DOC_ID \n " );
			sb.append( " AND SAI.SFACT_ACT_ID = OT.WORK_ID \n " );
			sb.append( " ) \n " );
		}
		
		sqlModel.setSqlStr( sb.toString() );
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

}
