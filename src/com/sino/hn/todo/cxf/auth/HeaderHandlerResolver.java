package com.sino.hn.todo.cxf.auth;

import java.util.List;

import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;

import org.apache.cxf.jaxws.handler.HandlerResolverImpl;

public class HeaderHandlerResolver extends HandlerResolverImpl implements
		HandlerResolver {
	public List<Handler> getHandlerChain(PortInfo portInfo) {
		List<Handler> handlesList = super.getHandlerChain(portInfo);
		SoapHeaderHandler handler = new SoapHeaderHandler();
		handlesList.add(handler);
		return handlesList;
	}
}
