package com.sino.ams.workorderDefine.servlet;

import java.sql.Connection;
import java.util.TimerTask;

import com.sino.ams.workorderDefine.dao.WorkorderDefineDAO;
import com.sino.ams.workorderDefine.dto.WorkorderDefineDTO;
import com.sino.base.db.conn.DBManager;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;

public class WorkorderDefineCreate extends TimerTask{
	
	Connection conn = null;
	private WorkorderDefineDTO dto = null;
	
	public WorkorderDefineCreate(WorkorderDefineDTO dto) {
		this.dto = dto;
	}
	@Override
	public void run() {
		
		WorkorderDefineDAO defineDAO = new WorkorderDefineDAO(null, dto, conn);
		try {
			conn = DBManager.getDBConnection();
			Logger.logError("开始下发工单...");
			defineDAO.createWorkorder(dto, conn);
			Logger.logError("下发工单完成...");
		} catch (DataHandleException e) {
			e.printStackTrace();
		} catch (PoolException e) {
			e.printStackTrace();
		} catch (QueryException e) {
			e.printStackTrace();
		} catch (ContainerException e) {
			e.printStackTrace();
		} finally {
			DBManager.closeDBConnection(conn);
		}
	}
	
}
