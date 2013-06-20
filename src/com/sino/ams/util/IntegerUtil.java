package com.sino.ams.util;

import com.sino.base.util.StrUtil;

public class IntegerUtil {
	public static Integer parseInt( String str ){
		if( !StrUtil.isEmpty( str ) ){
			return Integer.parseInt( str );
		}else{
			return 0;
//			throw new RuntimeException( "传入值为空" );
		}
	}
}
