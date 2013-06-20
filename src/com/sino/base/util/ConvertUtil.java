package com.sino.base.util;


public class ConvertUtil {
	public static String int2String( Integer num ){
		return String.valueOf( num );
	}
	
	public static int String2Int( String str ){
		if( !StrUtil.isEmpty( str ) ){
			return Integer.parseInt( str );
		}else{
			return 0;
		}
		
	}

}
