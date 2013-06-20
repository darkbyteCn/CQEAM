package com.sino.sinoflow.framework.security.sso;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;

public class OaUserMatchModel {
	
     public SQLModel getDataByOaLoginName(String oaloginName){
    	 SQLModel sqlModel = new SQLModel();
    	 List<String> parList = new ArrayList<String>();
    	 
    	 String sqlStr = "SELECT ID,OA_LOGIN_NAME,CMS_LOGIN_NAME,ROLE_DESC " +
    	 		" FROM OA_USER_MATCH " +
    	 		" WHERE upper(OA_LOGIN_NAME) = upper(?)";
         	 
    	 parList.add(oaloginName);
    	 sqlModel.setSqlStr(sqlStr);
    	 sqlModel.setArgs(parList);
    	 
    	 return sqlModel;
     }
     
     public SQLModel getDataByCmsLoginName(String cmsloginName){
    	 SQLModel sqlModel = new SQLModel();
    	 List<String> parList = new ArrayList<String>();
    	 
    	 String sqlStr = "SELECT ID,OA_LOGIN_NAME,CMS_LOGIN_NAME,ROLE_DESC " +
    	 		" FROM OA_USER_MATCH " +
    	 		" WHERE upper(CMS_LOGIN_NAME) = upper(?)";
         	 
    	 parList.add(cmsloginName);
    	 sqlModel.setSqlStr(sqlStr);
    	 sqlModel.setArgs(parList);
    	 
    	 return sqlModel;
     }
}
