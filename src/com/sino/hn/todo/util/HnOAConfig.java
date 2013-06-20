package com.sino.hn.todo.util;

import java.util.ResourceBundle;

import com.sino.base.util.StrUtil;
import com.sino.config.SinoConfig;
import com.sino.sinoflow.todo.constant.HNOAConstant;

/**
 * 
 * @系统名称:
 * @功能描述: 河南OA待办配置
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本：1.0
 * @开发作者: sj
 * @创建时间: Dec 1, 2011
 */
public class HnOAConfig extends SinoConfig {
	public static String getTodo_url() {
		return ResourceBundle.getBundle("config.HnOa").getString("todo_url");
	}
	
//	//省简称 HN等
//	public static String getProvinceSimpleName() {
//		return ResourceBundle.getBundle("config.HnOa").getString("PROVINCE_SIMPLE_NAME");
//	}

	public static String getTodo_username() {
		return ResourceBundle.getBundle("config.HnOa").getString(
				"todo_username");
	}

	public static String getTodo_password() {
		return ResourceBundle.getBundle("config.HnOa").getString(
				"todo_password");
	}

	public static String getEam_url() {
		return ResourceBundle.getBundle("config.HnOa").getString("eam_url");
	}

	public static long getOaThreadSleepTime() {
		String time = ResourceBundle.getBundle("config.HnOa").getString(
				"OA_TODO_THREAD_SLEEP_TIME");
		if (StrUtil.isEmpty(time)) {
			return HNOAConstant.OA_TODO_THREAD_SLEEP_TIME;
		} else {
			return Long.parseLong(time);
		}
	}
	
	public static boolean startOatodo(){
		String startOatodo = ResourceBundle.getBundle("config.HnOa").getString("START_OATODO");
		return Boolean.parseBoolean( startOatodo );
	}

}
