package com.sino.ams.yearchecktaskmanager.util;

import java.util.HashMap;
import java.util.Map;

public class CommonUseUtil {

	 //获取工单类型
	 public static String getNameByType(String type){
    	Map<String,String> map=new HashMap<String,String>();
    	String retStr="";
    	map.put(AssetsCheckTaskConstant.ORDER_TYPE_ASS_CHK_TASK, "年度盘点");
    	map.put(AssetsCheckTaskConstant.ORDER_TYPE_NON_ADDRESS_SOFTWARE, "非实地[软件类]");
    	map.put(AssetsCheckTaskConstant.ORDER_TYPE_NON_ADDRESS_CLIENT, "非实地[客户端类]");
    	map.put(AssetsCheckTaskConstant.ORDER_TYPE_NON_ADDRESS_PIPELINE, "非实地[光缆、杆路及管道类]");
    	map.put(AssetsCheckTaskConstant.ORDER_TYPE_ADDRESS_WIRELESS, "实地无线类");
    	map.put(AssetsCheckTaskConstant.ORDER_TYPE_ADDRESS_NON_WIRELESS, "实地非无线类");
    	retStr=map.get(type);
    	if(retStr==null){
    		retStr="";
    	}
	    return retStr;
	 }
   
    //获取工单类型SQL
   public static String getSqlStr(){
	   String sqlStr=
		    " CASE WHEN CTOL.ORDER_TYPE='"+AssetsCheckTaskConstant.ORDER_TYPE_ASS_CHK_TASK+"' THEN '年度盘点' " +
 		    " WHEN CTOL.ORDER_TYPE='"+AssetsCheckTaskConstant.ORDER_TYPE_NON_ADDRESS_SOFTWARE+"' THEN '非实地[软件类]'" +
 		    " WHEN CTOL.ORDER_TYPE='"+AssetsCheckTaskConstant.ORDER_TYPE_NON_ADDRESS_CLIENT+"' THEN '非实地[客户端类]'" +
 		    " WHEN CTOL.ORDER_TYPE='"+AssetsCheckTaskConstant.ORDER_TYPE_NON_ADDRESS_PIPELINE+"' THEN '非实地[光缆、杆路及管道类]' " +
 		    " WHEN CTOL.ORDER_TYPE='"+AssetsCheckTaskConstant.ORDER_TYPE_ADDRESS_WIRELESS+"' THEN '实地无线类'" +
 		    " WHEN CTOL.ORDER_TYPE='"+AssetsCheckTaskConstant.ORDER_TYPE_ADDRESS_NON_WIRELESS+"' then '实地非无线类' " +
 		    " ELSE '年度盘点' END ORDER_TYPE_NAME \n" ;
	   return sqlStr;
   }
}
