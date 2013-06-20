package com.sino.hn.todo.cxf.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.codehaus.xfire.client.Client;
import org.codehaus.xfire.client.XFireProxy;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.mochasoft.todo.service.ITodoService;
import com.sino.base.exception.DataHandleException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.hn.todo.cxf.auth.WsClient;
import com.sino.hn.todo.cxf.beans.ArrayOfClose;
import com.sino.hn.todo.cxf.beans.ArrayOfOpen;
import com.sino.hn.todo.cxf.beans.Close;
import com.sino.hn.todo.cxf.beans.Open;
import com.sino.hn.todo.cxf.service.todoservice.TodoServicePortType;
import com.sino.hn.todo.cxf.util.ConvertUtil;
import com.sino.hn.todo.dto.OaResponseDTO;
import com.sino.hn.todo.log.OaTodoLogService;
import com.sino.hn.todo.service.IOaTodoService;
import com.sino.hn.todo.xfire.client.ClientAuthenticationHandler;
import com.sino.sinoflow.todo.constant.HNOAConstant;
import com.sino.sinoflow.todo.dto.OaTodoDTO;
import com.sino.sinoflow.todo.util.DateUtil;

/**
 * 
 * @系统名称:
 * @功能描述: 接口推送业务
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本：1.0
 * @开发作者: sj
 * @创建时间: Dec 1, 2011
 */
public class OaTodoService implements IOaTodoService{

	TodoServicePortType todoServicePortType ;
	OaResponseDTO responseDTO = null;
	Connection conn = null;

	OaTodoLogService logService = null;

	public OaTodoService() {
		this.init();
	}

	public void init() {
		logService = new OaTodoLogService();
		responseDTO = new OaResponseDTO();
	}

	public void clear() {
		responseDTO = new OaResponseDTO();
	}

	/**
	 * 推送待办/已办(一条一条推送) 方式一 :先推送，只要返回，就插入记录到日志表中
	 * 
	 * @param open
	 * @return
	 */
	public boolean saveSender(OaTodoDTO todoDTO) {
		boolean isSuccess = false; // 推送成功
		boolean isAccess = false;
		try {
			Logger.logInfo( " start sender " + todoDTO.getTitle() );
			responseDTO.setBeginSendTime(DateUtil.getCurDateTimeStr());
			if (todoDTO.getTodoType().trim().equals(
					HNOAConstant.OA_TODO_TYPE_OPEN)) {
				Open open = ConvertUtil.getOpenFromDTO(todoDTO);
				isAccess = saveOpens(open); // 对接成功，网络是否联通
			} else if (todoDTO.getTodoType().trim().equals(
					HNOAConstant.OA_TODO_TYPE_CLOSE)) {
				Close close = ConvertUtil.getCloseFromDTO(todoDTO);
				isAccess = saveCloses(close); // 对接成功，网络是否联通
			}

			if (isAccess) {
				OaResponseDTO resDTO = this.parseResponseXML(responseDTO
						.getRetXML());
				isSuccess = resDTO.isSuccess();
				responseDTO.setResultCode(resDTO.getResultCode());
				responseDTO.setResultDesc(resDTO.getResultDesc());
			}
		} catch (DocumentException e) {
			e.printStackTrace();
			responseDTO.setEamMsg(StrUtil.nullToString(responseDTO.getEamMsg())
					+ e.getMessage());
		} catch (Throwable e) {
			e.printStackTrace();
			responseDTO.setEamMsg(StrUtil.nullToString(responseDTO.getEamMsg())
					+ e.getMessage());
		} finally {
			responseDTO.setEndSendTime(DateUtil.getCurDateTimeStr());
			Logger.logInfo( " end sender " + todoDTO.getTitle() );
			responseDTO.setAccess(isAccess);
			responseDTO.setSuccess(isSuccess);

			logService.saveLog(todoDTO, responseDTO);

			return isSuccess;
		}
	}

	/**
	 * 推送待办/已办(一条一条推送) 方式一 :先推送，只要返回，就插入记录到日志表中
	 * 
	 * @param open
	 * @return
	 * @throws SQLException
	 */
	public boolean saveSender2(OaTodoDTO todoDTO) throws SQLException {
		boolean isSuccess = false; // 推送成功
		boolean isAccess = false;
		try {
			logService.saveLogInTrans(todoDTO, conn);
			if (todoDTO.getTodoType().equals(HNOAConstant.OA_TODO_TYPE_OPEN)) {
				Open open = ConvertUtil.getOpenFromDTO(todoDTO);
				isAccess = saveOpens(open); // 对接成功，网络是否联通
			} else if (todoDTO.getTodoType().equals(
					HNOAConstant.OA_TODO_TYPE_CLOSE)) {
				Close close = ConvertUtil.getCloseFromDTO(todoDTO);
				isAccess = saveCloses(close); // 对接成功，网络是否联通
			}
			if (isAccess) {
				OaResponseDTO resDTO = this.parseResponseXML(responseDTO
						.getRetXML());
				isSuccess = resDTO.isSuccess();
				responseDTO.setResultCode(resDTO.getResultCode());
				responseDTO.setResultDesc(resDTO.getResultDesc());
			}
		} catch (DocumentException e) {
			responseDTO.setEamMsg(StrUtil.nullToString(responseDTO.getEamMsg())
					+ e.getMessage());
		} catch (DataHandleException e) {
			responseDTO.setEamMsg(StrUtil.nullToString(responseDTO.getEamMsg())
					+ e.getMessage());
		} catch (IllegalAccessException e) {
			responseDTO.setEamMsg(StrUtil.nullToString(responseDTO.getEamMsg())
					+ e.getMessage());
		} catch (InvocationTargetException e) {
			responseDTO.setEamMsg(StrUtil.nullToString(responseDTO.getEamMsg())
					+ e.getMessage());
		} finally {
			responseDTO.setAccess(isAccess);
			responseDTO.setSuccess(isSuccess);
			if (isSuccess) {
				conn.commit();
			} else {
				conn.rollback();
			}
			return isSuccess;
		}
	}

	/**
	 * 推送待办(一条一条推送)
	 * 
	 * @param open
	 * @return
	 */
	public boolean saveOpen(OaTodoDTO todoDTO) {
		boolean isSuccess = false; // 推送成功
		boolean isAccess = false;
		try {
			Open open = ConvertUtil.getOpenFromDTO(todoDTO);
			isAccess = saveOpens(open); // 对接成功，网络是否联通
			if (isAccess) {
				OaResponseDTO resDTO = this.parseResponseXML(responseDTO
						.getRetXML());
				isSuccess = resDTO.isSuccess();
				responseDTO.setResultCode(resDTO.getResultCode());
				responseDTO.setResultDesc(resDTO.getResultDesc());
			}
		} catch (DocumentException e) {
			responseDTO.setEamMsg(StrUtil.nullToString(responseDTO.getEamMsg())
					+ e.getMessage());
		} finally {
			responseDTO.setAccess(isAccess);
			responseDTO.setSuccess(isSuccess);
			return isSuccess;
		}
	}

	/**
	 * 推送待办(一条一条推送)
	 * 
	 * @param open
	 * @return
	 */
	public boolean saveOpen(Open open) {
		boolean isSuccess = false; // 推送成功
		boolean isAccess = false;
		try {
			isAccess = saveOpens(open); // 对接成功，网络是否联通
			if (isAccess) {
				OaResponseDTO resDTO = this.parseResponseXML(responseDTO
						.getRetXML());
				isSuccess = resDTO.isSuccess();
				responseDTO.setResultCode(resDTO.getResultCode());
				responseDTO.setResultDesc(resDTO.getResultDesc());
			}
		} catch (DocumentException e) {
			responseDTO.setEamMsg(StrUtil.nullToString(responseDTO.getEamMsg())
					+ e.getMessage());
		} finally {
			responseDTO.setAccess(isAccess);
			responseDTO.setSuccess(isSuccess);
			return isSuccess;
		}
	}

	/**
	 * 推送已办(一条一条推送)
	 * 
	 * @param close
	 * @return
	 */
	public boolean saveClose(Close close) {
		boolean isSuccess = false; // 推送成功
		boolean isAccess = false;
		try {
			isAccess = saveCloses(close); // 对接成功，网络是否联通
			if (isAccess) {
				OaResponseDTO resDTO = this.parseResponseXML(responseDTO
						.getRetXML());
				isSuccess = resDTO.isSuccess();
				responseDTO.setResultCode(resDTO.getResultCode());
				responseDTO.setResultDesc(resDTO.getResultDesc());
			}
		} catch (DocumentException e) {
			responseDTO.setEamMsg(StrUtil.nullToString(responseDTO.getEamMsg())
					+ e.getMessage());
		} finally {
			responseDTO.setAccess(isAccess);
			responseDTO.setSuccess(isSuccess);
			return isSuccess;
		}
	}

	/**
	 * OA端保存以及返回信息处理
	 * 
	 * @param open
	 */
	protected boolean saveOpens(Open open) {
		boolean isSuccess = false; // 记录是否成功返回信息，对接是否成功
		ArrayOfOpen arrayOfOpen = new ArrayOfOpen();
		arrayOfOpen.addOpen(open);
		try {
			String retXML = saveOpens(arrayOfOpen);
			responseDTO.setRetXML(retXML);
			isSuccess = true;
		} catch (Throwable ex) {
			Logger.logError( ex );
			responseDTO.setEamMsg(ex.getMessage());
		} finally {
			if (StrUtil.isEmpty(responseDTO.getRetXML())
					&& StrUtil.isEmpty(responseDTO.getEamMsg())) {
				responseDTO.setEamMsg("没有返回信息，可能超时了!");
				isSuccess = false;
			}
			return isSuccess;
		}
	}

	/**
	 * OA端保存以及返回信息处理
	 * 
	 * @param open
	 */
	protected boolean saveCloses(Close close) {
		boolean isSuccess = false; // 记录是否成功返回信息，对接是否成功
		ArrayOfClose arrayOfClose = new ArrayOfClose();
		arrayOfClose.addClose(close);
		try {
			String retXML = saveCloses(arrayOfClose);
			responseDTO.setRetXML(retXML);
			isSuccess = true;
		} catch (Throwable ex) {
			responseDTO.setEamMsg(ex.getMessage());
		} finally {
			if (StrUtil.isEmpty(responseDTO.getRetXML())
					&& StrUtil.isEmpty(responseDTO.getEamMsg())) {
				responseDTO.setEamMsg("没有返回信息，可能超时了!");
				isSuccess = false;
			}
			return isSuccess;
		}
	}

	/**
	 * 解析返回XML结果
	 * 
	 * @param retXML
	 * @return
	 * @throws DocumentException
	 */
	private OaResponseDTO parseResponseXML(String retXML)
			throws DocumentException {
		OaResponseDTO resDTO = null;
		String resultCode = "";
		String resultDesc = "";
		boolean isSuccess = false;
		SAXReader saxReadr = new SAXReader();// 得到SAXReader对象
		Document doc = saxReadr.read(retXML);
		Element root = doc.getRootElement();
		Element resultCodeEle = root.element("resultCode");
		resultCode = resultCodeEle.getText();
		if (resultCode.equals(HNOAConstant.RESULT_CODE_FAILURE)) {
			Element resultDescEle = root.element("resultDesc");
			resultDesc = resultDescEle.getText();
			isSuccess = false;
		} else {
			isSuccess = true;
		}
		resDTO = new OaResponseDTO();
		resDTO.setResultCode(resultCode);
		resDTO.setResultDesc(resultDesc);
		resDTO.setSuccess(isSuccess);
		return resDTO;
	}

	/**
	 * OA端保存
	 * 
	 * @param opens
	 * @return
	 * @throws Throwable
	 */
	protected String saveCloses(ArrayOfClose closes) throws Throwable {
//		todoService = WsClient.createTodoService();
//		todoServicePortType = WsClient.createTodoService();
//		TodoServicePortType service = todoService.getTodoServiceHttpPort();
		prodService();
		return todoServicePortType.saveClose(closes);
	}

	/**
	 * OA端保存
	 * 
	 * @param opens
	 * @return
	 * @throws Throwable
	 */
	protected String saveOpens(ArrayOfOpen opens) throws Throwable {
//		todoServicePortType = WsClient.createTodoService();
		prodService();
		return todoServicePortType.saveOpen(opens);
	}
	
	protected void prodService() throws Throwable{
//		TodoService todoService = new TodoService();
//		HeaderHandlerResolver resolver = new HeaderHandlerResolver();
//		todoService.setHandlerResolver( resolver ); 
//		todoServicePortType = todoService.getTodoServiceHttpPort();
		
//		TodoService todoService = new TodoService();
//		todoServicePortType = todoService.getTodoServiceHttpPort();
		todoServicePortType = WsClient.createTodoService();
		
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public OaResponseDTO getResponseDTO() {
		return responseDTO;
	}
	
	/**
	 * 测试
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			OaTodoDTO todoDTO = new OaTodoDTO();
			
			todoDTO.setPri("1");
			todoDTO.setDocId("111231312");
			todoDTO.setDocType("sdfsfsd");
			todoDTO.setSender("chchchchchchchchc");
			todoDTO.setSourceId("PR");
			todoDTO.setStartTime("2010-11-23 14:40:57");
			todoDTO.setSysId("123");
			todoDTO.setTitle("132213123123");
			todoDTO.setTodoType("1");
			todoDTO.setTodoUrl("/snoa01.sn.cmcc:80/SGSOA/fwgl.nsf/0/FE5798DB97BB6C4F482575DE00140421?OpenDocument");
			todoDTO.setUserId("chchchchchchchchc");
			todoDTO.setWorkId("33fbe5f001340ceed16d515733fbe5f001340817c70f34bb");
			
			IOaTodoService service = new OaTodoService();
			service.saveSender(todoDTO);
			
			OaResponseDTO resDto = service.getResponseDTO();
			//调用方法
		    System.out.println( resDto.getResultDesc() );
		    System.out.println( resDto.getResultCode() );
		   } catch (IllegalArgumentException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		   } catch( Throwable e){
			   e.printStackTrace();
		   }

	}

}
