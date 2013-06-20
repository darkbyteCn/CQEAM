package com.sino.hn.todo.xfire.service;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.mochasoft.todo.beans.Close;
import com.mochasoft.todo.beans.Open;
import com.mochasoft.todo.service.ITodoService;
import com.sino.base.exception.DataHandleException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.hn.todo.dto.OaResponseDTO;
import com.sino.hn.todo.log.OaTodoLogService;
import com.sino.hn.todo.service.IOaTodoService;
import com.sino.hn.todo.xfire.client.TodoClient;
import com.sino.hn.todo.xfire.util.ConvertUtil;
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
public class OaTodoService implements IOaTodoService {
//	TodoService todoService;
	ITodoService todoService;
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
//				OaResponseDTO resDTO = this.parseResponseXML(responseDTO
//						.getRetXML());
				isSuccess = TodoClient.isSuccess( responseDTO.getRetXML() );
//				isSuccess = resDTO.isSuccess();
				responseDTO.setResultCode( TodoClient.getResultCode() );
				responseDTO.setResultDesc( TodoClient.getMessage() );
			}
		} catch (RuntimeException e) {
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
		List<Open> opens = new ArrayList<Open>();
		opens.add(open);
		try {
			String retXML = saveOpens( opens );
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
		
		List<Close> closes = new ArrayList<Close>();
		closes.add(close);
		try {
			String retXML = saveCloses(closes);
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
	protected String saveCloses(List<Close> closes) throws Throwable {
		prodService();
		return todoService.saveClose(closes);
	}

	/**
	 * OA端保存
	 * 
	 * @param opens
	 * @return
	 * @throws Throwable
	 */
	protected String saveOpens( List<Open> opens ) throws Throwable {
		prodService();
		return todoService.saveOpen( opens );
	}
	
	protected void prodService() throws Throwable{
		todoService = TodoClient.getClient();
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public OaResponseDTO getResponseDTO() {
		return responseDTO;
	}

}
