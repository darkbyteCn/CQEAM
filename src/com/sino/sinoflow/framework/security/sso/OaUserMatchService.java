package com.sino.sinoflow.framework.security.sso;

import java.sql.Connection;

import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;

public class OaUserMatchService {
    
	public String getOaLoginName(String cmsLoginName){
		String oaLoginName = "";
		
		OaUserMatchModel userMatchModel = new OaUserMatchModel();
		SQLModel sqlModel = userMatchModel.getDataByCmsLoginName(cmsLoginName);
		DTOSet dtoSet = getDtoSetBySQLModel(sqlModel);
	    if(dtoSet == null) return "";
	    OaUserMatchForm matchForm = (OaUserMatchForm)dtoSet.getDTO(0);
	    oaLoginName = matchForm.getOaLoginName();
		return oaLoginName;
	}	
	
	public DTOSet getCmsLoginNames(String oaloginName){
		DTOSet dtoSet = null;
		OaUserMatchModel userMatchModel = new OaUserMatchModel();
		SQLModel sqlModel = userMatchModel.getDataByOaLoginName(oaloginName);
		dtoSet = getDtoSetBySQLModel(sqlModel);
		return dtoSet;
	}
	
	private DTOSet getDtoSetBySQLModel(SQLModel sqlModel){
		Connection conn = null;
		DTOSet dtoSet = null;
		try {
			conn = DBManager.getDBConnection();
			SimpleQuery sQuery = new SimpleQuery(sqlModel,conn);
			sQuery.setDTOClassName(OaUserMatchForm.class.getName());
			sQuery.executeQuery();
			dtoSet = sQuery.getDTOSet();
			if(dtoSet.isEmpty()) dtoSet = null;
		} catch (PoolException e) {
			e.printLog();
		} catch (QueryException e) {
			e.printLog();
		} finally {
			if(conn != null) DBManager.closeDBConnection(conn);
		}
		
		return dtoSet;
	}
	
	public static void main(String[] args){	
	   OaUserMatchService matchService = new OaUserMatchService();
	   String oaloginName = matchService.getOaLoginName("CAIZHIQIANG8");
	   Logger.logInfo("oaloginName = " + oaloginName);
	   Logger.logInfo("\r\n");
	   DTOSet dtoSet = matchService.getCmsLoginNames("CAIZHIQIANG");
	   
	   for(int i = 0;i < dtoSet.getSize();i++){
		   OaUserMatchForm oForm = (OaUserMatchForm)dtoSet.getDTO(i);
		   Logger.logInfo(oForm.getCmsLoginName());
		   
	   }
	   
	   System.exit(0);
	}
}
