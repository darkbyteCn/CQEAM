package com.sino.ams.yearchecktaskmanager.util;

import java.util.HashMap;
import java.util.Map;

public class CommonUseUtil {

	 //��ȡ��������
	 public static String getNameByType(String type){
    	Map<String,String> map=new HashMap<String,String>();
    	String retStr="";
    	map.put(AssetsCheckTaskConstant.ORDER_TYPE_ASS_CHK_TASK, "����̵�");
    	map.put(AssetsCheckTaskConstant.ORDER_TYPE_NON_ADDRESS_SOFTWARE, "��ʵ��[�����]");
    	map.put(AssetsCheckTaskConstant.ORDER_TYPE_NON_ADDRESS_CLIENT, "��ʵ��[�ͻ�����]");
    	map.put(AssetsCheckTaskConstant.ORDER_TYPE_NON_ADDRESS_PIPELINE, "��ʵ��[���¡���·���ܵ���]");
    	map.put(AssetsCheckTaskConstant.ORDER_TYPE_ADDRESS_WIRELESS, "ʵ��������");
    	map.put(AssetsCheckTaskConstant.ORDER_TYPE_ADDRESS_NON_WIRELESS, "ʵ�ط�������");
    	retStr=map.get(type);
    	if(retStr==null){
    		retStr="";
    	}
	    return retStr;
	 }
   
    //��ȡ��������SQL
   public static String getSqlStr(){
	   String sqlStr=
		    " CASE WHEN CTOL.ORDER_TYPE='"+AssetsCheckTaskConstant.ORDER_TYPE_ASS_CHK_TASK+"' THEN '����̵�' " +
 		    " WHEN CTOL.ORDER_TYPE='"+AssetsCheckTaskConstant.ORDER_TYPE_NON_ADDRESS_SOFTWARE+"' THEN '��ʵ��[�����]'" +
 		    " WHEN CTOL.ORDER_TYPE='"+AssetsCheckTaskConstant.ORDER_TYPE_NON_ADDRESS_CLIENT+"' THEN '��ʵ��[�ͻ�����]'" +
 		    " WHEN CTOL.ORDER_TYPE='"+AssetsCheckTaskConstant.ORDER_TYPE_NON_ADDRESS_PIPELINE+"' THEN '��ʵ��[���¡���·���ܵ���]' " +
 		    " WHEN CTOL.ORDER_TYPE='"+AssetsCheckTaskConstant.ORDER_TYPE_ADDRESS_WIRELESS+"' THEN 'ʵ��������'" +
 		    " WHEN CTOL.ORDER_TYPE='"+AssetsCheckTaskConstant.ORDER_TYPE_ADDRESS_NON_WIRELESS+"' then 'ʵ�ط�������' " +
 		    " ELSE '����̵�' END ORDER_TYPE_NAME \n" ;
	   return sqlStr;
   }
}
