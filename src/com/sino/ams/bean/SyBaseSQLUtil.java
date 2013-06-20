package com.sino.ams.bean;

import java.util.List;
import java.util.UUID;

import com.sino.base.calen.SimpleCalendar;
 
/**
 * 
 * @系统名称: 资产管理
 * @功能描述: Sybase 数据库特性常用工具类
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本：1.0
 * @开发作者: sj
 * @创建时间: May 27, 2011
 */
public class SyBaseSQLUtil {
	
	public static int ORG_ID = 86;
	
	public static String NEW_ID_FUNCTION = " SELECT NEWID() ";
	
	//默认的Int 初始值
	public static int NULL_INT_VALUE = -1;

	//得到当前日期
	public static String getCurDate(){
		return "GETDATE()";
	}
	//获取数据库用户
	public static String getDBOwner(){
		return "dbo.";
	}
	
	//查询条件中 String 参数 SQL
	public static String nullStringParam(){
		return "  ? IS NULL  OR ? = '' ";
	}
	//查询条件中 INT 参数 SQL
	public static String nullIntParam(){
		return " ? IS NULL  OR ? = " + NULL_INT_VALUE ;
	}
	
	//查询条件中 SimpleCalendar 参数 SQL
	public static String nullSimpleCalendarParam(){  
		return "  ? IS NULL  OR ? = '' ";
	}
	
	//替换 ? IS NULL 
	public static String isNull(){
		return " ? = '' ";
	}
	
	//替换 IS NULL 
	public static String isNullNoParam(){
		return "=NULL ";
	}
	
	//替换 SF.XXX 字段名 IS NOT NULL 这种
	public static String isNotNull( String column ){
//		return " ( " + column + " != '' ) ";
		return " ( " + column + " IS NOT NULL AND " + column + " != NULL ) ";
	}
	
	//查询条件中 String 参数  
	public static void nullStringParamArgs(List sqlArgs , String param){
		sqlArgs.add( param );	
		sqlArgs.add( param );
		sqlArgs.add( param );
	}
	//查询条件中 SimpleCalendar 参数 
	public static void nullSimpleCalendarParamArgs(List sqlArgs , SimpleCalendar param){
		sqlArgs.add( param );	
		sqlArgs.add( param );
		sqlArgs.add( param );
	}
	//查询条件中 INT 参数 
	public static void nullIntParamArgs(List sqlArgs , int param){
		sqlArgs.add( param );	
		sqlArgs.add( param ); 
		sqlArgs.add( param ); 
	}
}
