package com.sino.sms.service;

import com.sino.base.SinoBaseObject;
import com.sino.framework.security.dto.ServletConfigDTO;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 中国移动资产实物管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public abstract class SMSGateWay extends SinoBaseObject {
	private ServletConfigDTO servletConfig = null;

	public void setServletConfig(ServletConfigDTO servletConfig){
		this.servletConfig = servletConfig;
	}

	/**
	 * 功能：发送短信
	 * @param recvPhoneNo String 接收手机号
	 * @param message String 短信内容
	 * @return boolean 返回true表示发送成功，返回false表示发送失败
	 */
	abstract boolean sendMessage(String recvPhoneNo,String message);
}
