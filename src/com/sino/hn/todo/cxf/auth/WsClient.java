package com.sino.hn.todo.cxf.auth;

import java.util.ArrayList;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.jaxws.JaxWsClientFactoryBean;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import com.sino.hn.todo.cxf.service.todoservice.TodoService;
import com.sino.hn.todo.cxf.service.todoservice.TodoServicePortType;
import com.sino.hn.todo.util.HnOAConfig;
import com.sino.sinoflow.todo.constant.HNOAConstant;

/**
 * 
 * @系统名称:
 * @功能描述: 创建一个WS链接服务
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本：1.0
 * @开发作者: sj
 * @创建时间: Dec 1, 2011
 */
public class WsClient {

	public static TodoServicePortType createTodoService() throws Throwable {
		TodoServicePortType service = null;
		try {
			String webServiceConTimeout = HNOAConstant.WS_CON_TIMEOUT;
			String webServiceRevTimeout = HNOAConstant.WS_REV_TIMEOUT;
			
			JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
			ArrayList<Interceptor> list = new ArrayList<Interceptor>();
			// 添加soap header
			list.add(new AddSoapHeader());
			// 添加soap消息日志打印
			list.add(new org.apache.cxf.interceptor.LoggingOutInterceptor());
			factory.setOutInterceptors(list);
			factory.setServiceClass(TodoServicePortType.class);
			factory.setAddress(HnOAConfig.getTodo_url());
			service = (TodoServicePortType) factory.create();
		 
			// 超时时间设置
			Client clientP = ClientProxy.getClient(service);
			HTTPConduit http = (HTTPConduit) clientP.getConduit();
			HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
			httpClientPolicy.setConnectionTimeout(Integer
					.valueOf(webServiceConTimeout));
			httpClientPolicy.setReceiveTimeout(Integer
					.valueOf(webServiceRevTimeout));
			httpClientPolicy.setAllowChunking(false);
			http.setClient(httpClientPolicy);
		} catch (Throwable ex) {
			throw ex;
		}
		return service;
	}

}
