package com.sino.hn.todo.xfire.client;

import java.io.StringReader;
import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.codehaus.xfire.XFireFactory;
import org.codehaus.xfire.client.Client;
import org.codehaus.xfire.client.XFireProxy;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;

import com.mochasoft.todo.beans.Open;
import com.mochasoft.todo.service.ITodoService;
//import com.sino.hn.todo.xfire.beans.Open;
import com.sino.hn.todo.xfire.service.ConfigReader;
//import com.sino.hn.todo.xfire.service.ITodoService;

public class TodoClient {
	
	/**
	 * 反馈信息
	 */
	private static String message;
	
	private static String resultCode;
	/**
	 * 得到webservice客户端实例
	 * @return
	 * @throws MalformedURLException
	 */
	public static ITodoService getClient() throws MalformedURLException
	{
		Service srvcModel = new ObjectServiceFactory().create(ITodoService.class);
		XFireProxyFactory factory = new XFireProxyFactory(XFireFactory.newInstance().getXFire());
		
		//配置文件信息
		String todo_url = ConfigReader.TODO_URL;
		String todo_username = ConfigReader.TODO_USERNAME;
		String todo_password = ConfigReader.TODO_PASSWORD;
		
		ITodoService service = (ITodoService) factory.create(srvcModel, todo_url);
		//添加验证信息
		XFireProxy proxy = (XFireProxy)Proxy.getInvocationHandler(service);
	    Client client = proxy.getClient();
	    client.addOutHandler(new ClientAuthenticationHandler(todo_username,todo_password));
	    
	    //添加其他参数
	   /* client.setProperty(CommonsHttpMessageSender.HTTP_TIMEOUT, "300");
	    client.setProperty(CommonsHttpMessageSender.DISABLE_KEEP_ALIVE, "true");
	    client.setProperty(CommonsHttpMessageSender.DISABLE_EXPECT_CONTINUE, "true");
	    client.setProperty(CommonsHttpMessageSender.HTTP_PROXY_HOST, "MY_PROXY_ADDRESS");
	    client.setProperty(CommonsHttpMessageSender.HTTP_PROXY_PORT, "MY_PROXY_PORT");
*/
	    
	    return service;
	};
	/**
	 * 处理成功失败xml，成功返回true，失败返回false，getMessage可以得到具体失败信息
	 * @param responseXml
	 * @return
	 */
	public static boolean isSuccess(String responseXml) 
	{
		TodoClient.setMessage(null);//清空结果
		boolean ret = false;
		DocumentBuilder docBuilder = null;
		Document doc = null;
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse(new InputSource(new StringReader(responseXml)));   
		} catch (Exception e) {
			throw new RuntimeException("读取待办反馈xml内容出错");
		} 
		doc.getDocumentElement().normalize();
		Element orgsNode=(Element)doc.getFirstChild();//得到orgs节点
		
		Element org=orgsNode;//得到一个result
		NodeList attList = org.getChildNodes();//得到属性集合
		for (int j = 0; j < attList.getLength(); j++) {
			if(attList.item(j).getParentNode()==(Node)org)//不查询子节点
			{
				if(attList.item(j) instanceof Element)
				{
					Element att = (Element)attList.item(j);//得到一个属性
					String name = att.getNodeName();//得到属性名称
					
					NodeList attValueNode = att.getChildNodes(); 
					Text text = (Text)attValueNode.item(0);
					String value = text.getData(); //得到属性值
					
					if("resultCode".equals(name))
					{
						resultCode = value;
						if("1".equals(value))
						{
							ret = true;
						}else if("-1".equals(value))
						{
							ret = false;
						}
					}else if("resultDesc".equals(name))
					{
						TodoClient.setMessage(value);
					}
				}
			}
		}
		return ret;
	}
	public static String getMessage() {
		return message;
	}
	public static void setMessage(String message) {
		TodoClient.message = message;
	}
	
	/**
	 * 测试
	 * @param args
	 */
	public static void main(String[] args) {
		try {
		    Service serviceModel = new ObjectServiceFactory().create(ITodoService.class);
//		    ITodoService service = (ITodoService) new XFireProxyFactory().create(serviceModel,
//		        "http://10.87.13.247:9081/todoWs/services/TodoService" );  
		    ITodoService service = (ITodoService) new XFireProxyFactory().create(serviceModel,
			        "http://10.87.9.32:9080/todoWs/services/TodoService" ); 
		    XFireProxy proxy = (XFireProxy)Proxy.getInvocationHandler(service);
		    Client client = proxy.getClient();
		    client.addOutHandler(new ClientAuthenticationHandler("abcd","1234"));
		    //发送授权信息
//		    client.addOutHandler(new ClientAuthenticationHandler("mocha_ha_portal_todo","Ha3slkC6"));
		    //输出调用web services方法的返回信息
		    
		    List list = new ArrayList();
			Open open = new Open();
//			open.setPri("1");
//			open.setDoc_id("123123");
//			open.setDoc_type("公司收文");
//			open.setSender("张三2");
//			open.setSource_id("PR");
//			open.setStart_time("2010-11-23 14:40:57");
//			open.setSys_id("2");
//			opeb.setTitle("采购申请单子办理");
//			open.setType("1");
//			open.setUrl("/snoa01.sn.cmcc:80/SGSOA/fwgl.nsf/0/FE5798DB97BB6C4F482575DE00140421?OpenDocument");
//			open.setUser_id("1234567");
//			open.setWork_id("12345");
			
			open.setPri("1");
			open.setDoc_id("111231312");
			open.setDoc_type("sdfsfsd");
			open.setSender("chchchchchchchchc");
			open.setSource_id("PR");
			open.setStart_time("2010-11-23 14:40:57");
			open.setSys_id("123");
			open.setTitle("132213123123");
			open.setType("1");
			open.setUrl("/snoa01.sn.cmcc:80/SGSOA/fwgl.nsf/0/FE5798DB97BB6C4F482575DE00140421?OpenDocument");
			open.setUser_id("chchchchchchchchc");
			open.setWork_id("33fbe5f001340ceed16d515733fbe5f001340817c70f34bb");
			
			list.add(open);
			//调用方法
			String ret = service.saveOpen(list);
		    System.out.println(ret);
		   } catch (MalformedURLException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		   } catch (IllegalArgumentException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		   } catch( Throwable e){
			   e.printStackTrace();
		   }

	}
	public static String getResultCode() {
		return resultCode;
	}
	public static void setResultCode(String resultCode) {
		TodoClient.resultCode = resultCode;
	}

}
