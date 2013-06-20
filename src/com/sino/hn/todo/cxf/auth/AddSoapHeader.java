package com.sino.hn.todo.cxf.auth;

import java.util.List;

import javax.xml.namespace.QName;

import org.apache.cxf.binding.soap.SoapHeader;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.sino.hn.todo.util.HnOAConfig;

/**
 * 
 * @系统名称:
 * @功能描述: 头消息设置
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本：1.0
 * @开发作者: sj
 * @创建时间: Dec 1, 2011
 */
public class AddSoapHeader extends AbstractSoapInterceptor {
	public AddSoapHeader() {
		super(Phase.WRITE);
	}

	public void handleMessage(SoapMessage message) throws Fault {
		String spPassword = HnOAConfig.getTodo_username();
		String spName = HnOAConfig.getTodo_password();

		QName qname = new QName("AuthenticationToken");
		Document doc = DOMUtils.createDocument();
		// 自定义节点
		Element spId = doc.createElement("tns:Username");
		spId.setTextContent(spName);
		// 自定义节点
		Element spPass = doc.createElement("tns:Password");
		spPass.setTextContent(spPassword);

		Element root = doc.createElementNS(HnOAConfig.getTodo_url(),
				"tns:AuthenticationToken");
		root.appendChild(spId);
		root.appendChild(spPass);

		SoapHeader head = new SoapHeader(qname, root);
		List<Header> headers = message.getHeaders();
		headers.add(head);
		// System.out.println(">>>>>添加header<<<<<<<");
	}
}
