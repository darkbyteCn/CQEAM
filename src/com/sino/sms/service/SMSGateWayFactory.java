package com.sino.sms.service;

import com.sino.base.exception.SinoBaseException;
import com.sino.base.log.Logger;
import com.sino.base.util.SystemUtil;
import com.sino.framework.security.dto.ServletConfigDTO;
/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 中国移动资产实物管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class SMSGateWayFactory {
	private static SMSGateWayFactory factory = null;

	private SMSGateWayFactory() {
		super();
	}

	/**
	 * 功能：获取短信网管工厂实例
	 * @return SMSGateWayFactory
	 */
	public static SMSGateWayFactory getFactory(){
		if(factory == null){
			factory = new SMSGateWayFactory();
		}
		return factory;
	}

	/**
	 * 功能：获取短信网关接口实例
	 * @param servletConfig ServletConfigDTO
	 * @return SMSGateWay
	 * @throws SinoBaseException
	 */
	public SMSGateWay getSMSGateWay(ServletConfigDTO servletConfig) throws SinoBaseException{
		SMSGateWay ggateWay = null;
		try {
			String instanceName = SystemUtil.getClassName(SMSGateWay.class);
			String packageName = SystemUtil.getPackageName(SMSGateWay.class);
			instanceName = packageName + "." + instanceName + servletConfig.getProvinceCode();
			ggateWay = (SMSGateWay) Class.forName(instanceName).newInstance();
			ggateWay.setServletConfig(servletConfig);
		} catch (ClassNotFoundException ex) {
			Logger.logError(ex);
			throw new SinoBaseException(ex);
		} catch (IllegalAccessException ex) {
			Logger.logError(ex);
			throw new SinoBaseException(ex);
		} catch (InstantiationException ex) {
			Logger.logError(ex);
			throw new SinoBaseException(ex);
		}
		return ggateWay;
	}
}
