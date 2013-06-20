package com.sino.ams.workorder.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.ams.constant.AmsFlowConstant;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.workorder.dto.EtsWorkorderBatchDTO;
import com.sino.ams.workorder.dto.EtsWorkorderDTO;
import com.sino.ams.workorder.model.OrderDiffModel;
import com.sino.ams.workorder.model.OrderExtendModel;
import com.sino.pda.OrderModel;
import com.sino.sms.bean.MessageSaver;
import com.sino.sms.constant.SMSConstant;
import com.sino.sms.dto.SfMsgDefineDTO;

/**
 * User: zhoujs
 * Date: 2007-9-20
 * Time: 20:44:52
 * Function:工单辅助工具
 */
public class WorkOrderUtil {

	/**
	 * 取工单批号
	 * @param conn
	 * @return String
	 */
	synchronized public static String getWorkorderBatchNo(Connection conn) {

		String workorderBatchNo = "";
		String current_date_sql = "SELECT CONVERT(VARCHAR(32),GETDATE(),112) CURDT";
		String query_sql = " ";
		String currentDate = "";
		String insert_sql = "";


		try {
			//取当前日期(YYYYMMDD)
			SQLModel sqlModel = new SQLModel();
			sqlModel.setSqlStr(current_date_sql);
			SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
			simpleQuery.executeQuery();
			if (simpleQuery.hasResult()) {
				currentDate = simpleQuery.getFirstRow().getStrValue("CURDT");
			}


			query_sql = "SELECT * FROM ETS_SYSCODE WHERE CODECLASS = ? ";
			sqlModel = new SQLModel();
			List sqlArgs = new ArrayList();
			sqlArgs.add(currentDate);
			sqlModel.setSqlStr(query_sql);
			sqlModel.setArgs(sqlArgs);

			simpleQuery = new SimpleQuery(sqlModel, conn);
			simpleQuery.executeQuery();

			boolean hasRecord = simpleQuery.hasResult();

			conn.setAutoCommit(false);
			if (hasRecord) {
				insert_sql = "UPDATE ETS_SYSCODE SET SYSCODE=SYSCODE+1 WHERE CODECLASS = ?";
			} else {
				insert_sql = "INSERT INTO ETS_SYSCODE (CODECLASS,SYSCODE) VALUES(?,1)";
			}
			sqlModel = new SQLModel();
			sqlArgs = new ArrayList();
			sqlArgs.add(currentDate);
			sqlModel.setSqlStr(insert_sql);
			sqlModel.setArgs(sqlArgs);
			boolean success = DBOperator.updateRecord(sqlModel, conn);

			query_sql = "SELECT (CASE LEN(CONVERT(VARCHAR,SYSCODE)) WHEN 1 THEN '00'|| CONVERT(VARCHAR,SYSCODE)  WHEN 2 THEN '0'|| CONVERT(VARCHAR,SYSCODE) ELSE CONVERT(VARCHAR,SYSCODE) END) BATCH_NO FROM ETS_SYSCODE WHERE CODECLASS = ? ";
			sqlModel = new SQLModel();
			sqlArgs = new ArrayList();
			sqlArgs.add(currentDate);
			sqlModel.setSqlStr(query_sql);
			sqlModel.setArgs(sqlArgs);
			simpleQuery = new SimpleQuery(sqlModel, conn);
			simpleQuery.executeQuery();
			workorderBatchNo = simpleQuery.getFirstRow().getStrValue("BATCH_NO");
			workorderBatchNo = currentDate + workorderBatchNo;

			if (success) {
				conn.commit();
			} else {
				conn.rollback();
			}
			conn.setAutoCommit(true);

		} catch (Exception e) {
			Logger.logError(e);
			try {
				conn.rollback();
				conn.setAutoCommit(true);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			workorderBatchNo = "";
		}
		return workorderBatchNo;
	}

	/**
	 * @param workorderBatchNo
	 * @param conn
	 * @return String
	 */
	synchronized public static String getWorkorderNo(String workorderBatchNo, Connection conn) {

		String workorderNo = "";
		String query_sql = "";
		String insert_sql = " ";
		try {
			query_sql = "SELECT * FROM ETS_SYSCODE WHERE CODECLASS = ? ";

			SQLModel sqlModel = new SQLModel();
			List sqlArgs = new ArrayList();
			sqlArgs.add(workorderBatchNo);
			sqlModel.setSqlStr(query_sql);
			sqlModel.setArgs(sqlArgs);

			SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
			simpleQuery.executeQuery();
			boolean hasRecord = simpleQuery.hasResult();
			boolean autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			if (hasRecord) {
				query_sql = "SELECT (CASE LEN(CONVERT(VARCHAR,SYSCODE)) WHEN 1 THEN '00'|| CONVERT(VARCHAR,SYSCODE)  WHEN 2 THEN '0'|| CONVERT(VARCHAR,SYSCODE) ELSE CONVERT(VARCHAR,SYSCODE) END) ORDER_NO FROM ETS_SYSCODE WHERE CODECLASS = ? ";
				sqlModel = new SQLModel();
				sqlArgs = new ArrayList();
				sqlArgs.add(workorderBatchNo);
				sqlModel.setSqlStr(query_sql);
				sqlModel.setArgs(sqlArgs);
				simpleQuery = new SimpleQuery(sqlModel, conn);
				simpleQuery.executeQuery();
				if (simpleQuery.hasResult()) {
					workorderNo = simpleQuery.getFirstRow().getStrValue("ORDER_NO");
				}

				insert_sql = "UPDATE ETS_SYSCODE " +
						" SET SYSCODE = SYSCODE+1" +
						" WHERE CODECLASS = ?";
				sqlModel = new SQLModel();
				sqlArgs = new ArrayList();
//				sqlArgs.add(StrUtil.strToInt(workorderNo));
				sqlArgs.add(workorderBatchNo);
				sqlModel.setSqlStr(insert_sql);
				sqlModel.setArgs(sqlArgs);
				workorderNo = workorderBatchNo + workorderNo;
			} else {
				workorderNo = workorderBatchNo + "001";
				insert_sql = " INSERT INTO ETS_SYSCODE (CODECLASS,SYSCODE) values(?,2)";
				sqlModel = new SQLModel();
				sqlArgs = new ArrayList();
				sqlArgs.add(workorderBatchNo);
				sqlModel.setSqlStr(insert_sql);
				sqlModel.setArgs(sqlArgs);
			}

			boolean successFlag = DBOperator.updateRecord(sqlModel, conn);
			if (successFlag) {
				conn.commit();
				conn.setAutoCommit(autoCommit);
			} else {
				conn.rollback();
			}
		} catch (Exception e) {
			Logger.logError(e);
			workorderNo = "";
		}

		return workorderNo;
	}

	/**
	 * 根据设备专业类型取地点专业
	 * @param itemCategory
	 * @return
	 */
	public String getObjectCategory(String itemCategory) {
		String objectCategory = "";
		if (itemCategory.equals(DictConstant.CATEGORY_BTS)) {
			objectCategory = DictConstant.NETADDR_BTS;
		} else if (itemCategory.equals(DictConstant.CATEGORY_BSC)) {
			objectCategory = DictConstant.NETADDR_BSC;
		} else if (itemCategory.equals(DictConstant.CATEGORY_DATA)) {
			objectCategory = DictConstant.NETADDR_DATA;
		} else if (itemCategory.equals(DictConstant.CATEGORY_ELEC)) {
			objectCategory = DictConstant.NETADDR_ELE;
		} else if (itemCategory.equals(DictConstant.CATEGORY_EXCHG)) {
			objectCategory = DictConstant.NETADDR_EXCHG;
		} else if (itemCategory.equals(DictConstant.CATEGORY_NETOPT)) {
			objectCategory = DictConstant.NETADDR_NETOPT;
		} else if (itemCategory.equals(DictConstant.CATEGORY_TRANS)) {
			objectCategory = DictConstant.NETADDR_TRANS;
		}

		return objectCategory;
	}

	/**
	 * 根据地点专业取设备专业代码
	 * @param objectCategory 地点专业
	 * @return   String
	 */

	public String getItemCategory(String objectCategory) {
		String itemCategory = "";
		if (objectCategory.equals(DictConstant.NETADDR_BTS)) {
			itemCategory = DictConstant.CATEGORY_BTS;
		} else if (objectCategory.equals(DictConstant.NETADDR_BSC)) {
			itemCategory = DictConstant.CATEGORY_BSC;
		} else if (objectCategory.equals(DictConstant.NETADDR_DATA)) {
			itemCategory = DictConstant.CATEGORY_DATA;
		} else if (objectCategory.equals(DictConstant.NETADDR_ELE)) {
			itemCategory = DictConstant.CATEGORY_ELEC;
		} else if (objectCategory.equals(DictConstant.NETADDR_EXCHG)) {
			itemCategory = DictConstant.CATEGORY_EXCHG;
		} else if (objectCategory.equals(DictConstant.NETADDR_NETOPT)) {
			itemCategory = DictConstant.CATEGORY_NETOPT;
		} else if (objectCategory.equals(DictConstant.NETADDR_TRANS)) {
			itemCategory = DictConstant.CATEGORY_TRANS;
		}
		return itemCategory;
	}

	public String getObjectCategoryDesc(Connection conn,String objectCategory)  {
		String objectCategoryDesc="";
		SQLModel sqlModel=new SQLModel();
		List sqlArgs=new ArrayList();
		String sqlStr = "SELECT EFV.DESCRIPTION\n" +
				"  FROM ETS_FLEX_VALUES EFV, ETS_FLEX_VALUE_SET EFVS\n" +
				" WHERE EFVS.FLEX_VALUE_SET_ID = EFV.FLEX_VALUE_SET_ID\n" +
				"   AND EFVS.CODE = 'OBJECT_CATEGORY'\n" +
				"   AND EFV.CODE =?";
		sqlArgs.add(objectCategory);

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);

		try {
			SimpleQuery simpleQuery=new SimpleQuery(sqlModel, conn);
			simpleQuery.executeQuery();
			if(simpleQuery.hasResult()){
			objectCategoryDesc=simpleQuery.getFirstRow().getStrValue("DESCRIPTION");
		}
		} catch (QueryException e) {
			e.printLog();
		} catch (ContainerException e) {
			e.printLog();
		}


		return objectCategoryDesc;
	}

	/**
	 * 保存表单数据:工单批信息(ets_workorder_batch)、工单信息(ets_workorder)、工单配置信息
	 * @param conn
	 * @param workorderBatchDTO
	 */
	public void saveBatchExtends(Connection conn, EtsWorkorderBatchDTO workorderBatchDTO) throws DataHandleException {
		boolean operatorResult = true;
		try {
			SQLModel sqlModel = new SQLModel();
			OrderExtendModel workorderExtend = new OrderExtendModel();
			//删除计划配置信息主表
			operatorResult = DBOperator.updateRecord(workorderExtend.getDeleteSchemeDataModel(workorderBatchDTO.getWorkorderBatch(), false), conn);
			//删除工单主表
			operatorResult = DBOperator.updateRecord(workorderExtend.getDeleteWorkorderDataModel(workorderBatchDTO.getWorkorderBatch(), false), conn);
			//更新attribute7
			operatorResult = DBOperator.updateRecord(workorderExtend.getUpdateWorkorderTmpDataModel(workorderBatchDTO.getWorkorderBatch()), conn);
			//更新工单类型
			operatorResult = DBOperator.updateRecord(workorderExtend.getUpdateWorkorderTmpDataModel(workorderBatchDTO.getWorkorderBatch(), workorderBatchDTO.getPrjId(), workorderBatchDTO.getDistributeGroupId()), conn);
			//写工单
			operatorResult = DBOperator.updateRecord(workorderExtend.getCopyWorkorderDataModel(workorderBatchDTO.getWorkorderBatch()), conn);
			//复制工单配置信息至正式表
			operatorResult = DBOperator.updateRecord(workorderExtend.getCopySchemDataModel(workorderBatchDTO.getWorkorderBatch()), conn);
			//删除工单配置信息(临时表)
			operatorResult = DBOperator.updateRecord(workorderExtend.getDeleteSchemeDataModel(workorderBatchDTO.getWorkorderBatch(), true), conn);
			//删除工单信息(临时表)
			operatorResult = DBOperator.updateRecord(workorderExtend.getDeleteWorkorderDataModel(workorderBatchDTO.getWorkorderBatch(), true), conn);
		} catch (DataHandleException e) {
			Logger.logError(e);
			e.printStackTrace();
		}
	}

	/**
	 * 更新工单进度
	 * @param conn
	 * @param workorderBatch
	 * @param sectionRight
	 * @param isNew
	 * @param isTemp
	 * @param isOver
	 */
	public void saveWorkorderPorcess(Connection conn, String workorderBatch, String sectionRight, boolean isNew, boolean isTemp, boolean isOver) {
		SQLModel sqlModel = new SQLModel();
		OrderModel orderModel = new OrderModel();
		if (isNew) {
			sqlModel = orderModel.getInsertProcessModel(workorderBatch, isTemp, isOver);
		} else {
			sqlModel = orderModel.getUpdateProcessModel(workorderBatch, sectionRight, isTemp);
		}

		try {
			DBOperator.updateRecord(sqlModel, conn);
		} catch (DataHandleException e) {
			Logger.logError(e);
			e.printStackTrace();
		}
	}

	/**
	 * 更新工单批工单状态
	 * @param conn
	 * @param workorderBatch
	 * @param sectionRight
	 */
	public void saveOrderStatus(Connection conn, String workorderBatch, String sectionRight) {
		SQLModel sqlModel = new SQLModel();
		OrderModel orderModel = new OrderModel();
		sqlModel = orderModel.getUpdateOrderStatusModel(workorderBatch, sectionRight);

		try {
			DBOperator.updateRecord(sqlModel, conn);
			if (sectionRight.equals(DictConstant.WORKORDER_NODE_DISTRUIBUTE) || sectionRight.equals(DictConstant.WORKORDER_NODE_NEW)) {
				//saveMsg(conn, workorderBatch);
			}
		} catch (DataHandleException e) {
			Logger.logError(e);
			e.printStackTrace();
		}
	}

	/**
	 * 取上次巡检工单
	 * @param conn
	 * @param objectNo
	 * @param workorderNo
	 * @return
	 */
	public String getPrevPendingOrderOfBase(Connection conn, String objectNo, String workorderNo) {
		String str = "";
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sql = "SELECT EW.ATTRIBUTE7 FROM ETS_WORKORDER EW WHERE EW.WORKORDER_NO= ?";
		sqlArgs.add(workorderNo);
		sqlModel.setSqlStr(sql);
		sqlModel.setArgs(sqlArgs);

		SimpleQuery simpleQuery = null;
		String attribute7 = "";
		try {
			simpleQuery = new SimpleQuery(sqlModel, conn);
			simpleQuery.executeQuery();
			if (simpleQuery.hasResult()) {
				attribute7 = simpleQuery.getFirstRow().getStrValue("ATTRIBUTE7");
			}

			String sqlStr = "SELECT EW.WORKORDER_NO\n" +
					"  FROM ETS_WORKORDER EW\n" +
					" WHERE EW.WORKORDER_FLAG < " + DictConstant.WORKORDER_STATUS_ACHIEVED + "\n" +
					"   AND EW.WORKORDER_FLAG > " + DictConstant.WORKORDER_STATUS_NEW + "\n" +
					"   AND EW.WORKORDER_OBJECT_NO = ?\n" +
					"   AND EW.UPLOAD_DATE <\n" +
					"       (SELECT UPLOAD_DATE FROM ETS_WORKORDER WHERE WORKORDER_NO = ?)\n" +
					"   AND EW.ATTRIBUTE7 = ?\n" +
					" ORDER BY UPLOAD_DATE";
			sqlArgs.clear();
			sqlArgs.add(objectNo);
			sqlArgs.add(workorderNo);
			sqlArgs.add(attribute7);
			sqlModel.setSqlStr(sqlStr);
			simpleQuery = new SimpleQuery(sqlModel, conn);
			simpleQuery.executeQuery();
			if (simpleQuery.hasResult()) {
				str = simpleQuery.getFirstRow().getStrValue("WORKORDER_NO");
			}
		} catch (QueryException e) {
			e.printStackTrace();
		} catch (ContainerException e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 检查该地点下工单是否有差异
	 * @param conn
	 * @param workorderNo
	 * @param objectNo
	 * @return
	 * @throws Exception
	 */
	public boolean getDiffCountForInspect(Connection conn, String objectNo, String workorderNo) throws Exception {
		SQLModel sqlModel = new SQLModel();
		OrderModel orderModel = new OrderModel();
		sqlModel = orderModel.getDiffModel();
		SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
		simpleQuery.executeQuery();
		return simpleQuery.hasResult();
	}

	public static String getOrderProcdureName(String workorderType, String category) {
		String procdureName = "";
		if (category.equals(DictConstant.NETADDR_BTS)) { //基站
			if (workorderType.equals(DictConstant.ORDER_TYPE_NEW)) {
				procdureName = AmsFlowConstant.BTS_NEW;
			} else if (workorderType.equals(DictConstant.ORDER_TYPE_EXT)) {
				procdureName = AmsFlowConstant.BTS_EXT;
			} else if (workorderType.equals(DictConstant.ORDER_TYPE_FIX)) {
				procdureName = AmsFlowConstant.BTS_FIX;
			} else if (workorderType.equals(DictConstant.ORDER_TYPE_CHECK)) {
				procdureName = AmsFlowConstant.BTS_CHECK;
			} else if (workorderType.equals(DictConstant.ORDER_TYPE_REP)) {
				procdureName = AmsFlowConstant.BTS_REP;
			} else if (workorderType.equals(DictConstant.ORDER_TYPE_SUB)) {
				procdureName = AmsFlowConstant.BTS_SUB;
			} else if (workorderType.equals(DictConstant.ORDER_TYPE_TRANS)) {
				procdureName = AmsFlowConstant.BTS_TRANS;
			} else if (workorderType.equals(DictConstant.ORDER_TYPE_HDV)){
				procdureName = AmsFlowConstant.BTS_HDV;
			}
		} else if (category.equals(DictConstant.NETADDR_NETOPT)) {//网优
			if (workorderType.equals(DictConstant.ORDER_TYPE_NEW)) {
				procdureName = AmsFlowConstant.NET_NEW;
			} else if (workorderType.equals(DictConstant.ORDER_TYPE_EXT)) {
				procdureName = AmsFlowConstant.NET_EXT;
			} else if (workorderType.equals(DictConstant.ORDER_TYPE_FIX)) {
				procdureName = AmsFlowConstant.NET_FIX;
			} else if (workorderType.equals(DictConstant.ORDER_TYPE_CHECK)) {
				procdureName = AmsFlowConstant.NET_CHECK;
			} else if (workorderType.equals(DictConstant.ORDER_TYPE_REP)) {
				procdureName = AmsFlowConstant.NET_REP;
			} else if (workorderType.equals(DictConstant.ORDER_TYPE_SUB)) {
				procdureName = AmsFlowConstant.NET_SUB;
			} else if (workorderType.equals(DictConstant.ORDER_TYPE_TRANS)) {
				procdureName = AmsFlowConstant.NET_TRANS;
			} else if (workorderType.equals(DictConstant.ORDER_TYPE_HDV)) {
				procdureName = AmsFlowConstant.NET_HDV;
			}
		} else if (category.equals(DictConstant.NETADDR_EXCHG)) {// 交换
			if (workorderType.equals(DictConstant.ORDER_TYPE_NEW)) {
				procdureName = AmsFlowConstant.CHANG_NEW;
			} else if (workorderType.equals(DictConstant.ORDER_TYPE_EXT)) {
				procdureName = AmsFlowConstant.CHANG_EXT;
			} else if (workorderType.equals(DictConstant.ORDER_TYPE_FIX)) {
				procdureName = AmsFlowConstant.CHANG_FIX;
			} else if (workorderType.equals(DictConstant.ORDER_TYPE_CHECK)) {
				procdureName = AmsFlowConstant.CHANG_CHECK;
			} else if (workorderType.equals(DictConstant.ORDER_TYPE_REP)) {
				procdureName = AmsFlowConstant.CHANG_REP;
			} else if (workorderType.equals(DictConstant.ORDER_TYPE_SUB)) {
				procdureName = AmsFlowConstant.CHANG_SUB;
			} else if (workorderType.equals(DictConstant.ORDER_TYPE_TRANS)) {
				procdureName = AmsFlowConstant.CHANG_TRANS;
			} else if (workorderType.equals(DictConstant.ORDER_TYPE_HDV)) {
				procdureName = AmsFlowConstant.CHANG_HDV;
			}
		} else if (category.equals(DictConstant.NETADDR_TRANS)) {//传输
			if (workorderType.equals(DictConstant.ORDER_TYPE_NEW)) {
				procdureName = AmsFlowConstant.TRANSFER_NEW;
			} else if (workorderType.equals(DictConstant.ORDER_TYPE_EXT)) {
				procdureName = AmsFlowConstant.TRANSFER_EXT;
			} else if (workorderType.equals(DictConstant.ORDER_TYPE_FIX)) {
				procdureName = AmsFlowConstant.TRANSFER_FIX;
			} else if (workorderType.equals(DictConstant.ORDER_TYPE_CHECK)) {
				procdureName = AmsFlowConstant.TRANSFER_CHECK;
			} else if (workorderType.equals(DictConstant.ORDER_TYPE_REP)) {
				procdureName = AmsFlowConstant.TRANSFER_REP;
			} else if (workorderType.equals(DictConstant.ORDER_TYPE_SUB)) {
				procdureName = AmsFlowConstant.TRANSFER_SUB;
			} else if (workorderType.equals(DictConstant.ORDER_TYPE_TRANS)) {
				procdureName = AmsFlowConstant.TRANSFER_TRANS;
			} else if (workorderType.equals(DictConstant.ORDER_TYPE_HDV)) {
				procdureName = AmsFlowConstant.TRANSFER_HDV;
			}
		} else if (category.equals(DictConstant.NETADDR_ELE)) {//电力
			if (workorderType.equals(DictConstant.ORDER_TYPE_NEW)) {
				procdureName = AmsFlowConstant.ELECTRI_NEW;
			} else if (workorderType.equals(DictConstant.ORDER_TYPE_EXT)) {
				procdureName = AmsFlowConstant.ELECTRI_EXT;
			} else if (workorderType.equals(DictConstant.ORDER_TYPE_FIX)) {
				procdureName = AmsFlowConstant.ELECTRI_FIX;
			} else if (workorderType.equals(DictConstant.ORDER_TYPE_CHECK)) {
				procdureName = AmsFlowConstant.ELECTRI_CHECK;
			} else if (workorderType.equals(DictConstant.ORDER_TYPE_REP)) {
				procdureName = AmsFlowConstant.ELECTRI_REP;
			} else if (workorderType.equals(DictConstant.ORDER_TYPE_SUB)) {
				procdureName = AmsFlowConstant.ELECTRI_SUB;
			} else if (workorderType.equals(DictConstant.ORDER_TYPE_TRANS)) {
				procdureName = AmsFlowConstant.ELECTRI_TRANS;
			} else if (workorderType.equals(DictConstant.ORDER_TYPE_HDV)) {
				procdureName = AmsFlowConstant.ELECTRI_HDV;
			}
		} else if (category.equals(DictConstant.NETADDR_DATA)) {//数据
			if (workorderType.equals(DictConstant.ORDER_TYPE_NEW)) {
				procdureName = AmsFlowConstant.DATA_NEW;
			} else if (workorderType.equals(DictConstant.ORDER_TYPE_EXT)) {
				procdureName = AmsFlowConstant.DATA_EXT;
			} else if (workorderType.equals(DictConstant.ORDER_TYPE_FIX)) {
				procdureName = AmsFlowConstant.DATA_FIX;
			} else if (workorderType.equals(DictConstant.ORDER_TYPE_CHECK)) {
				procdureName = AmsFlowConstant.DATA_CHECK;
			} else if (workorderType.equals(DictConstant.ORDER_TYPE_REP)) {
				procdureName = AmsFlowConstant.DATA_REP;
			} else if (workorderType.equals(DictConstant.ORDER_TYPE_SUB)) {
				procdureName = AmsFlowConstant.DATA_SUB;
			} else if (workorderType.equals(DictConstant.ORDER_TYPE_TRANS)) {
				procdureName = AmsFlowConstant.DATA_TRANS;
			} else if (workorderType.equals(DictConstant.ORDER_TYPE_HDV)) {
				procdureName = AmsFlowConstant.DATA_HDV;
			}
		} else if (category.equals(DictConstant.NETADDR_BSC)) {//监控
			if (workorderType.equals(DictConstant.ORDER_TYPE_NEW)) {
				procdureName = AmsFlowConstant.MONITOR_NEW;
			} else if (workorderType.equals(DictConstant.ORDER_TYPE_EXT)) {
				procdureName = AmsFlowConstant.MONITOR_EXT;
			} else if (workorderType.equals(DictConstant.ORDER_TYPE_FIX)) {
				procdureName = AmsFlowConstant.MONITOR_FIX;
			} else if (workorderType.equals(DictConstant.ORDER_TYPE_CHECK)) {
				procdureName = AmsFlowConstant.MONITOR_CHECK;
			} else if (workorderType.equals(DictConstant.ORDER_TYPE_REP)) {
				procdureName = AmsFlowConstant.MONITOR_REP;
			} else if (workorderType.equals(DictConstant.ORDER_TYPE_SUB)) {
				procdureName = AmsFlowConstant.MONITOR_SUB;
			} else if (workorderType.equals(DictConstant.ORDER_TYPE_TRANS)) {
				procdureName = AmsFlowConstant.MONITOR_TRANS;
			} else if (workorderType.equals(DictConstant.ORDER_TYPE_HDV)) {
				procdureName = AmsFlowConstant.MONITOR_HDV;
			}
		} else {
			procdureName = AmsFlowConstant.WORKORDER_HAND_OVER;
		}
		return procdureName;
	}

	/**
	 * 取该工单地点上次巡检的工单号
	 * @param conn
	 * @param etsWorkorderDTO
	 * @return
	 * @throws QueryException
	 * @throws ContainerException
	 */
	public String getPrevPendingOrderOfBase(Connection conn, EtsWorkorderDTO etsWorkorderDTO) throws QueryException, ContainerException {
		String prePendingOrder = "";
		SQLModel sqlModel = new SQLModel();
		OrderDiffModel orderDiffModel = new OrderDiffModel();
		sqlModel = orderDiffModel.getPreCheckOrder(etsWorkorderDTO);
		SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
		simpleQuery.executeQuery();
		if (simpleQuery.hasResult()) {
			prePendingOrder = simpleQuery.getFirstRow().getStrValue("WORKORDER_NO");
		}
		return prePendingOrder;
	}

	public String getPrevScanOrderId(Connection conn, EtsWorkorderDTO etsWorkorderDTO) throws QueryException, ContainerException {
		String workorderNo = "";
		SQLModel sqlModel = new SQLModel();
		OrderDiffModel orderDiffModel = new OrderDiffModel();
		sqlModel = orderDiffModel.getPreScanedOrder(etsWorkorderDTO);
		SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
		simpleQuery.executeQuery();
		if (simpleQuery.hasResult()) {
			workorderNo = simpleQuery.getFirstRow().getStrValue("WORKORDER_NO");
		}

		return workorderNo;
	}

	public boolean hasSubmit(String workorderNo, Connection conn) throws QueryException, ContainerException {
		boolean hasSubmint = false;
		OrderModel orderModel = new OrderModel();
		SQLModel sqlModel = orderModel.getHasSubmitModel(workorderNo);
		SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
		simpleQuery.executeQuery();
		String count = simpleQuery.getFirstRow().getStrValue("NCOUNT");
		hasSubmint = count.equals("0");
		return hasSubmint;
	}

	/**
	 * 查询该地点是否是第一次巡检
	 * @param objectNo
	 * @param conn
	 * @return
	 */
	public boolean isFirstCheck(Connection conn, String objectNo) throws QueryException {
		OrderDiffModel orderModel = new OrderDiffModel();
		SQLModel sqlModel = orderModel.isFirstCheckModel(objectNo);
		SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
		simpleQuery.executeQuery();
		return simpleQuery.hasResult();
	}

	public void saveMsg(Connection conn, String workorderBatch) {
		MessageSaver msgSaver = new MessageSaver(conn);
		String strSql = "{CALL SF_MSG.GET_USER_PHONE(?, ?, ?)}";
		CallableStatement callStat = null;
		try {
			callStat = conn.prepareCall(strSql);
			callStat.setString(1, workorderBatch);
			callStat.registerOutParameter(2, Types.VARCHAR);
			callStat.registerOutParameter(3, Types.VARCHAR);
			callStat.execute();
			String orderNums = callStat.getString(2);
			String userPhones = callStat.getString(3);
			String[] orderNum = StrUtil.splitStr(orderNums, "$");
			String[] userPhone = StrUtil.splitStr(userPhones, "$");
			for (int i = 0; i < orderNum.length; i++) {
				String[] phones = StrUtil.splitStr(userPhone[i], ";");
				for (int j = 0; j < phones.length; j++) {
					SfMsgDefineDTO msgDefineDTO = new SfMsgDefineDTO();
					String[] phone = StrUtil.splitStr(phones[j], "@");
					if (phone[0].equals("0") || phone.length == 1) {
						continue;
					}
					String [] user=StrUtil.splitStr(phone[0],"#");
					msgDefineDTO.setMsgCategoryId(SMSConstant.ORDER_DIS_ID);
					msgDefineDTO.setCellPhone(phone[1]);
					msgDefineDTO.setApplyNumber(orderNum[i]);
					msgDefineDTO.setUserId(user[0]);
					msgDefineDTO.setMsgContent(user[1] + "：工单：" + orderNum[i] + "需要您办理，请使用PDA下载。");
					msgSaver.saveMsg(msgDefineDTO);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		} catch (Exception e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		} finally {
		}
	}
}
