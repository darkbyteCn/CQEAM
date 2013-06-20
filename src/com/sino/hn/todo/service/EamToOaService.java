package com.sino.hn.todo.service;

import java.sql.Connection;

import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.hn.todo.dao.EamToOADAO;
import com.sino.hn.todo.xfire.service.OaTodoService;
import com.sino.sinoflow.todo.constant.HNOAConstant;
import com.sino.sinoflow.todo.dto.OaTodoDTO;

/**
 * 
 * 
 * @系统名称:
 * @功能描述: 循环推送
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本：1.0
 * @开发作者: sangjun
 * @创建时间: Dec 3, 2011
 */
public class EamToOaService {

	IOaTodoService service = null;

	public EamToOaService(){
		service = new OaTodoService();
	}
	/**
	 * 推送待办
	 */
	public void sendOatodo() {
		OaTodoDTO dto = new OaTodoDTO();
		dto.setTodoType(HNOAConstant.OA_TODO_TYPE_OPEN);
		this.sendOatodo(dto);
	}

	/**
	 * 推送已办
	 */
	public void sendOatodoDele() {
		OaTodoDTO dto = new OaTodoDTO();
		dto.setTodoType(HNOAConstant.OA_TODO_TYPE_CLOSE);
		this.sendOatodo(dto);
	}

	public void sendOatodo(String type) {
		OaTodoDTO dto = new OaTodoDTO();
		dto.setTodoType(type);
		this.sendOatodo(dto);
	}

	/**
	 * 获取需要推送的数据
	 * @param dto
	 * @return
	 * @throws PoolException
	 * @throws QueryException
	 * @throws RuntimeException
	 */
	public DTOSet preSendDatas(OaTodoDTO dto) throws PoolException,
			QueryException , RuntimeException {
		Connection conn = null;
		EamToOADAO eamToOADAO = null;
		DTOSet dtos = null;
		try {
			conn = DBManager.getDBConnection();
			eamToOADAO = new EamToOADAO(null, dto, conn);
			dtos = eamToOADAO.getTodoDataSet();
		} catch (PoolException e) {
			throw e;
		} catch (QueryException e) {
			throw e;
		} catch (Throwable e) {
			throw new RuntimeException( e.getMessage() );
		} finally {
			DBManager.closeDBConnection(conn);
			return dtos;
		}
	}

	public void sendOatodo(OaTodoDTO dto) {
		OaTodoDTO todoDTO = null;
		try {
			DTOSet dtos = preSendDatas(dto);
			if (null != dtos && !dtos.isEmpty()) {
				for (int i = 0; i < dtos.getSize(); i++) {
					todoDTO = (OaTodoDTO) dtos.getDTO(i);
					service.clear();
					service.saveSender(todoDTO);
				}
			}
		} catch (PoolException e) {
			Logger.logError(e);
		} catch (QueryException e) {
			Logger.logError(e);
		} catch (Throwable e) {
			Logger.logError(e);
		}
	}

	public IOaTodoService getService() {
		return service;
	}

	public void setService(IOaTodoService service) {
		this.service = service;
	}

	public static void main(String[] args) {
//		IOaTodoService todoService = new OaTodoService();
		EamToOaService oaToEamService = new EamToOaService();
//		oaToEamService.setService(todoService);
		oaToEamService.sendOatodoDele();
		System.exit(0);
	}

}
