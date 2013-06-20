package com.sino.ams.bean;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.QueryException;
import com.sino.flow.dto.FlowDTO;

public class ProcedureUtil {

	/**
	 * 功能：判断当前用户是否能审批指定流程
	 * @param userAccount SfUserDTO
	 * @param flowDTO FlowDTO
	 * @param conn Connection
	 * @return boolean
	 */
	public static boolean canProcess(SfUserDTO userAccount, FlowDTO flowDTO, Connection conn){
		boolean canProcess = false;
		try {
			String actId = flowDTO.getActId();
			String applyId = flowDTO.getApplyId();
			SQLModel sqlModel = new SQLModel();
			String sqlStr = "";
			List sqlArgs = new ArrayList();
			if(actId.equals("")){//不是通过流程进入页面
				if(applyId.equals("")){
					canProcess = true;
				} else {
					sqlStr = "SELECT"
							 + " 1"
							 + " FROM"
							 + " SF_ACT           SA,"
							 + " SF_ACT_INFO      SAI,"
							 + " SF_PROCEDURE_DEF SPD"
							 + " WHERE"
							 + " SA.ACTID = SAI.ACT_ID"
							 + " AND SA.PROC_ID = SPD.PROC_ID"
							 + " AND ((SAI.USER_ID = ? AND SAI.AGENT_USER_ID " + SyBaseSQLUtil.isNullNoParam() + " ) OR (SAI.AGENT_USER_ID = ?))"
							 + " AND SPD.PROC_NAME = ?"
							 + " AND SA.APP_ID = ?";
					sqlArgs.add(userAccount.getUserId());
					sqlArgs.add(userAccount.getUserId());
					sqlArgs.add(flowDTO.getProcName());
					sqlArgs.add(flowDTO.getApplyId());
					sqlModel.setSqlStr(sqlStr);
					sqlModel.setArgs(sqlArgs);
					SimpleQuery simp = new SimpleQuery(sqlModel, conn);
					simp.executeQuery();
					canProcess = simp.hasResult();
				}
			} else {
				sqlStr = "SELECT"
								+ " 1"
								+ " FROM"
								+ " SF_ACT           SA,"
								+ " SF_ACT_INFO      SAI"
								+ " WHERE"
								+ " SA.ACTID = SAI.ACT_ID"
								+ " AND SA.PROC_ID = ?"
								+ " AND SAI.ACT_ID = ?"
								+
								" AND ((SAI.USER_ID = ? AND SAI.AGENT_USER_ID " + SyBaseSQLUtil.isNullNoParam() + " ) OR (SAI.AGENT_USER_ID = ?))";
				sqlArgs.add(flowDTO.getProcId());
				sqlArgs.add(actId);
				sqlArgs.add(userAccount.getUserId());
				sqlArgs.add(userAccount.getUserId());
				sqlModel.setSqlStr(sqlStr);
				sqlModel.setArgs(sqlArgs);
				SimpleQuery simp = new SimpleQuery(sqlModel, conn);
				simp.executeQuery();
				canProcess = simp.hasResult();
			}
		} catch (QueryException ex) {
			ex.printLog();
		}
		return canProcess;
	}

	public static void main(String[] args){
		System.out.println(Boolean.valueOf("true").booleanValue());
	}
}
