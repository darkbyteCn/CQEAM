package com.sino.hn.todo.cxf.auth;

import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import com.sino.hn.todo.util.HnOAConfig;

public class SoapHeaderHandler implements SOAPHandler<SOAPMessageContext> {

	public Set<QName> getHeaders() {
		// TODO Auto-generated method stub
		return null;
	}

	public void close(MessageContext arg0) {
		// TODO Auto-generated method stub

	}

	public boolean handleFault(SOAPMessageContext arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean handleMessage(SOAPMessageContext messageContext) {
		String spName = HnOAConfig.getTodo_username();
		String spPassword = HnOAConfig.getTodo_password();
		try {
			Boolean outMessageIndicator = (Boolean) messageContext
					.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
			if (outMessageIndicator.booleanValue()) {

				SOAPEnvelope evelope = messageContext.getMessage()
						.getSOAPPart().getEnvelope();
				SOAPHeader header = evelope.addHeader();
				SOAPElement authHeaderEle = header.addChildElement("header","","http://service.todo.mochasoft.com/TodoService");
				SOAPElement authEle = authHeaderEle
						.addChildElement("AuthenticationToken");
				SOAPElement userEle = authEle.addChildElement("Username");
				userEle.addTextNode(spName);
				SOAPElement pwdEle = authEle.addChildElement("Password");
				pwdEle.addTextNode(spPassword);

			}
		} catch (SOAPException e) {
			e.printStackTrace();
		}
		return false;
	}

}
