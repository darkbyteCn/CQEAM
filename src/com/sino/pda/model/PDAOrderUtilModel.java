package com.sino.pda.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.DataHandleException;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.workorder.dto.EtsWorkorderBatchDTO;
import com.sino.ams.workorder.dto.EtsWorkorderDTO;

/**
 * User: zhoujs
 * Date: 2008-7-30
 * Time: 11:45:18
 * Function:工单PDA上用到的一些SQL
 */
public class PDAOrderUtilModel {
	/**
	 * 判断该工单下发组别是否存在与MIS部门的对应关系
	 * @param workorderNo 工单号
	 * @return SQLModel
	 */
	public SQLModel getExistsMisGroupModel(String workorderNo) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT 1\n" +
				"  FROM ETS_WORKORDER EW\n" +
				" WHERE EXISTS\n" +
				" (SELECT 1 FROM SF_GROUP_MATCH SGM WHERE EW.GROUP_ID = SGM.GROUP_ID)\n" +
				"   AND EW.WORKORDER_NO = ?";

		sqlArgs.add(workorderNo);

		sqlModel.setArgs(sqlArgs);
		sqlModel.setSqlStr(sqlStr);

		return sqlModel;
	}

	/**
	 * 取工单的attribute7（扫描设备专业）
	 * @param workorderNo 工单号
	 * @return SQLModel
	 */
	public SQLModel getAttribute7(String workorderNo) {
		SQLModel sqlModel = new SQLModel();

		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT WO.ATTRIBUTE7 FROM ETS_WORKORDER WO WHERE WO.WORKORDER_NO=?";

		sqlArgs.add(workorderNo);

		sqlModel.setArgs(sqlArgs);
		sqlModel.setSqlStr(sqlStr);

		return sqlModel;
	}

	public SQLModel getCostCenter(String workorderNo) {
		SQLModel sqlModel = new SQLModel();

		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT WO.COST_CENTER_CODE FROM ETS_WORKORDER WO WHERE WO.WORKORDER_NO=?";

		sqlArgs.add(workorderNo);

		sqlModel.setArgs(sqlArgs);
		sqlModel.setSqlStr(sqlStr);

		return sqlModel;
	}



	public SQLModel getInserBatchModel(EtsWorkorderBatchDTO batchDTO) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "INSERT INTO ETS_WORKORDER_BATCH\n" +
				"  (SYSTEMID,\n" +
				"   WORKORDER_BATCH,\n" +
				"   WORKORDER_BATCH_NAME,\n" +
				"   REMARK,\n" +
				"   PRJ_ID,\n" +
				"   WORKORDER_TYPE,\n" +
				"   STATUS,\n" +
				"   ARCHFLAG,\n" +
				"   DISTRIBUTE_GROUP_ID,\n" +
				"   CREATION_DATE,\n" +
				"   CREATED_BY)\n" +
				"VALUES\n" +
				"  ( NEWID() , ?, ?, ?, ?, ?, ?,  ?, ?, GETDATE(), ?)";
		sqlArgs.add(batchDTO.getWorkorderBatch());
		sqlArgs.add(batchDTO.getWorkorderBatchName());
		sqlArgs.add(batchDTO.getRemark());
		sqlArgs.add(batchDTO.getPrjId());
		sqlArgs.add(batchDTO.getWorkorderType());
		sqlArgs.add(0);
		sqlArgs.add(0);
		sqlArgs.add(batchDTO.getDistributeGroupId());
		sqlArgs.add(batchDTO.getCreatedBy());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);

		return sqlModel;
	}

	public SQLModel getInsertOrderModel(EtsWorkorderDTO orderDTO) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "INSERT INTO ETS_WORKORDER\n" +
				"  (SYSTEMID,\n" +
				"   WORKORDER_BATCH,\n" +
				"   WORKORDER_NO,\n" +
				"   WORKORDER_TYPE,\n" +
				"   WORKORDER_OBJECT_NO,\n" +
				"   START_DATE,\n" +
				"   IMPLEMENT_DAYS,\n" +
				"   GROUP_ID,\n" +
				"   IMPLEMENT_BY,\n" +
				"   PRJ_ID,\n" +
				"   CREATION_DATE,\n" +
				"   CREATED_BY,\n" +
				"   DISTRIBUTE_DATE,\n" +
				"   DISTRIBUTE_BY,\n" +
				"   ORGANIZATION_ID,\n" +
				"   WORKORDER_FLAG,\n" +
				"   REMARK,\n" +
				"   ARCHFLAG,\n" +
				"   DISTRIBUTE_GROUP,\n" +
				"   ATTRIBUTE4,\n" +
				"   CHECKOVER_BY,\n" +
				"   ATTRIBUTE7,\n" +
				"   COST_CENTER_CODE)\n" +
				" VALUES\n" +
				"  ( NEWID() ,?,?,?,?,GETDATE(),1,?,?,?,GETDATE(),?,GETDATE(),?,?,?,?,?,?,?,?,?,?)";
		sqlArgs.add(orderDTO.getWorkorderBatch());
		sqlArgs.add(orderDTO.getWorkorderNo());
		sqlArgs.add(orderDTO.getWorkorderType());
		sqlArgs.add(orderDTO.getWorkorderObjectNo());
		sqlArgs.add(orderDTO.getGroupId());
		sqlArgs.add(orderDTO.getImplementBy());
		sqlArgs.add(orderDTO.getPrjId());
		sqlArgs.add(orderDTO.getCreatedBy());
		sqlArgs.add(orderDTO.getDistributeBy());
		sqlArgs.add(orderDTO.getOrganizationId());
		sqlArgs.add(DictConstant.WOR_STATUS_DEPLOY);
		sqlArgs.add(orderDTO.getWorkorderBatchName());
		sqlArgs.add(0);
		sqlArgs.add(orderDTO.getGroupId());
		sqlArgs.add(orderDTO.getAttribute4());
		sqlArgs.add(orderDTO.getCheckoverBy());
		sqlArgs.add(orderDTO.getAttribute7());
		sqlArgs.add(orderDTO.getCostCenterCode());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 增加工单进度条（工单监控用）
	 * @param batchNo String
	 * @param workorderNo String
	 * @return SQLModel
	 * @throws DataHandleException
	 */
	public SQLModel getAddProcessModel(String batchNo, String workorderNo) throws DataHandleException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "INSERT INTO ETS_PROCESSBAR\n" +
				"  (BATCH_NO, WORKORDER_NO, PROCESS1, PROCESS3)\n" +
				" VALUES\n" +
				"  (?, ?, '100', '100')";
		sqlArgs.add("'"+batchNo+"'");
		sqlArgs.add("'"+workorderNo+"'");

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);

		return sqlModel;
	}

	/**
	 * 取指定ou下的成本中心清单
	 * @param organizationId String
	 * @return SQLModel
	 */
	public SQLModel getCostCenterModel(String organizationId){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr ="SELECT"
					   + " COST_CENTER_CODE,"
					   + " COST_CENTER_CODE||'.'||COST_CENTER_NAME COST_CENTER_NAME"
					   + " FROM"
					   + " AMS_COST_CENTER_V ACCV"
					   + " WHERE"
					   + " ACCV.ORGANIZATION_ID = ?"
					   + " AND ACCV.ENABLED_FLAG = 'Y'"
					   + " ORDER BY"
					   + " ACCV.COST_CENTER_CODE";
		sqlArgs.add(organizationId);
		sqlModel.setArgs(sqlArgs);
		sqlModel.setSqlStr(sqlStr);
		return sqlModel;
	}
}
