package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.newasset.dto.AmsAssetsRcvHeaderDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;

public class RcvOrderPrintModel extends AmsAssetsRcvHeaderModel {

	public RcvOrderPrintModel(SfUserDTO userAccount, AmsAssetsRcvHeaderDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：获取构造接收单的审批意见：含调拨单的审批意见的SQL
	 * @return SQLModel
	 */
	public SQLModel getApproveContentModel(){
		SQLModel sqlModel = new SQLModel();
		AmsAssetsRcvHeaderDTO dto = (AmsAssetsRcvHeaderDTO) dtoParameter;
		String sqlStr = "SELECT"
						+ " SU.USERNAME USER_NAME,"
						+ " (SELECT"
						+ " SU2.USERNAME"
						+ " FROM"
						+ " SF_USER SU2"
						+ " WHERE"
						+ " SU2.USER_ID = SAC.AGENT_USER_ID) AGENT_USER_NAME,"
						+ " SAC.APPROVE_TIME,"
						+ " '资产调出过程中：' || SAC.APPROVE_CONTENT APPROVE_CONTENT"
						+ " FROM"
						+ " SF_TASK_DEFINE     STD,"
						+ " SF_APPROVE_CONTENT SAC,"
						+ " SF_PROCEDURE_DEF   SPD,"
						+ " SF_USER            SU"
						+ " WHERE"
						+ " SAC.TASK_ID = STD.TASK_ID"
						+ " AND SAC.APPROVE_PERSON_ID = SU.USER_ID"
						+ " AND SAC.PROC_ID = SPD.PROC_ID"
						+ " AND SPD.APP_TABLE_NAME = 'AMS_ASSETS_TRANS_HEADER'"
						+ " AND SAC.APPLY_ID = ("
						+ " SELECT"
						+ " AATH.TRANS_ID"
						+ " FROM"
						+ " AMS_ASSETS_TRANS_HEADER AATH,"
						+ " AMS_ASSETS_RCV_HEADER   AARH"
						+ " WHERE"
						+ " AATH.TRANS_ID = AARH.TRANS_ID"
						+ " AND AARH.RECEIVE_HEADER_ID = ?)"
						+ " UNION ALL"
						+ " SELECT"
						+ " SU.USERNAME USER_NAME,"
						+ " (SELECT"
						+ " SU2.USERNAME"
						+ " FROM"
						+ " SF_USER SU2"
						+ " WHERE"
						+ " SU2.USER_ID = SAC.AGENT_USER_ID) AGENT_USER_NAME,"
						+ " SAC.APPROVE_TIME,"
						+ " '资产接收过程中：' || SAC.APPROVE_CONTENT APPROVE_CONTENT"
						+ " FROM"
						+ " SF_TASK_DEFINE     STD,"
						+ " SF_APPROVE_CONTENT SAC,"
						+ " SF_PROCEDURE_DEF   SPD,"
						+ " SF_USER            SU"
						+ " WHERE"
						+ " SAC.TASK_ID = STD.TASK_ID"
						+ " AND SAC.APPROVE_PERSON_ID = SU.USER_ID"
						+ " AND SAC.PROC_ID = SPD.PROC_ID"
						+ " AND SPD.APP_TABLE_NAME = 'AMS_ASSETS_RCV_HEADER'"
						+ " AND SAC.APPLY_ID = ?";
		sqlStr = "SELECT * FROM ("
				 + sqlStr
				 + ") ORDER BY APPROVE_TIME";
		List sqlArgs = new ArrayList();
		sqlArgs.add(dto.getReceiveHeaderId());
		sqlArgs.add(dto.getReceiveHeaderId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
