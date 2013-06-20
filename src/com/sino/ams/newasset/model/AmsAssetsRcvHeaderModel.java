package com.sino.ams.newasset.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.dto.AmsAssetsRcvHeaderDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.StrUtil;
import com.sino.flow.constant.FlowConstant;


/**
 * <p>Title: AmsAssetsRcvHeaderModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsAssetsRcvHeaderModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */

public class AmsAssetsRcvHeaderModel extends AMSSQLProducer {

	/**
	 * 功能：调拨资产接收头表(部门间和公司间需要) AMS_ASSETS_RCV_HEADER 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsAssetsRcvHeaderDTO 本次操作的数据
	 */
	public AmsAssetsRcvHeaderModel(SfUserDTO userAccount,
								   AmsAssetsRcvHeaderDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：框架自动生成调拨资产接收头表(部门间和公司间需要) AMS_ASSETS_RCV_HEADER数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataCreateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			AmsAssetsRcvHeaderDTO dto = (AmsAssetsRcvHeaderDTO) dtoParameter;
			String sqlStr = "INSERT INTO "
							+ " AMS_ASSETS_RCV_HEADER("
							+ " RECEIVE_HEADER_ID,"
							+ " TRANS_ID,"
							+ " RECEIVE_NO,"
							+ " RECEIVE_USER,"
							+ " RECEIVE_DATE,"
							+ " ORDER_STATUS,"
							+ " RECEIVE_DEPT,"
							+ " RECEIVE_ORGANIZATION_ID,"
							+ " FA_CONTENT_CODE,"
							+ " CREATION_DATE,"
							+ " CREATED_BY"
							+ " ) VALUES ("
							+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			sqlArgs.add(dto.getReceiveHeaderId());
			sqlArgs.add(dto.getTransId());
			sqlArgs.add(dto.getReceiveNo());
			sqlArgs.add(dto.getReceiveUser());
			sqlArgs.add(dto.getReceiveDate());
			sqlArgs.add(dto.getOrderStatus());
			sqlArgs.add(dto.getReceiveDept());
			sqlArgs.add(dto.getReceiveOrganizationId());
			sqlArgs.add(dto.getFaContentCode());
			sqlArgs.add(dto.getCreationDate());
			sqlArgs.add(dto.getCreatedBy());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：获取流程启动时的相关信息
	 * @return SQLModel
	 */
	public SQLModel getFirstTaskModel() {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsRcvHeaderDTO dto = (AmsAssetsRcvHeaderDTO) dtoParameter;
		String sql = "SELECT"
					 + " STD.TASK_ID,"
					 + " STD.PROC_ID,"
					 + " SPD.PROC_NAME"
					 + " FROM"
					 + " SF_PROCEDURE_DEF SPD,"
					 + " SF_TASK_DEFINE STD"
					 + " WHERE"
					 + " SPD.PROC_ID = STD.PROC_ID"
					 + " AND SPD.PROC_NAME = ?"
					 + " AND STD.TASK_PROP = ?";
		List sqlArgs = new ArrayList();
		sqlArgs.add(dto.getProcdureName());
		sqlArgs.add(FlowConstant.TASK_PROP_START);
		sqlModel.setSqlStr(sql);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成调拨资产接收头表(部门间和公司间需要) AMS_ASSETS_RCV_HEADER数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 */
	public SQLModel getDataUpdateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsRcvHeaderDTO dto = (AmsAssetsRcvHeaderDTO) dtoParameter;
		String sqlStr = "UPDATE AMS_ASSETS_RCV_HEADER"
						+ " SET"
						+ " GROUP_ID = ?,"
						+ " ORDER_STATUS = ?,"
						+ " LAST_UPDATE_DATE = GETDATE(),"
						+ " LAST_UPDATE_BY = ?"
						+ " WHERE"
						+ " RECEIVE_HEADER_ID = ?";

		sqlArgs.add(dto.getGroupId());
		sqlArgs.add(dto.getOrderStatus());
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(dto.getReceiveHeaderId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成调拨资产接收头表(部门间和公司间需要) AMS_ASSETS_RCV_HEADER数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsRcvHeaderDTO dto = (AmsAssetsRcvHeaderDTO) dtoParameter;
		String sqlStr = "SELECT"
						+ " AARH.RECEIVE_HEADER_ID,"
						+ " AARH.RECEIVE_NO,"
						+ " AARH.RECEIVE_DATE,"
						+ " AARH.RECEIVE_USER,"
						+ " AARH.FA_CONTENT_CODE,"
						+ " AARH.ORDER_STATUS,"
						+ " AARH.GROUP_ID,"
						+ " SG.GROUP_NAME GROUP_NAME,"
						+ " AMS_PUB_PKG.GET_FLEX_VALUE(AARH.FA_CONTENT_CODE, 'FA_CONTENT_CODE') FA_CONTENT_NAME,"
						+ " AMS_PUB_PKG.GET_FLEX_VALUE(AARH.ORDER_STATUS, 'ORDER_STATUS') ORDER_STATUS_NAME,"
						+ " SU.USERNAME RECEIVE_USER_NAME,"
						+ " AMD2.DEPT_NAME FROM_DEPT_NAME,"
						+ " AMS_ASSETS_PKG.GET_RCV_DEPT_NAME(AARH.RECEIVE_HEADER_ID) RECEIVE_DEPT_NAME,"
						+ " AARH.RECEIVE_ORGANIZATION_ID,"
						+ " EOCM1.COMPANY RECEIVE_COMPANY,"
						+ " EOCM2.COMPANY FROM_COMPANY,"
						+ " AATH.TRANS_NO,"
						+ " AATH.CREATED_REASON TRANS_REASON,"
						+ " AATH.TRANS_TYPE,"
						+ " AATH.TRANSFER_TYPE,"
						+ " AATH.APPROVED_DATE TRANS_OUT_DATE"
						+ " FROM"
						+ " AMS_ASSETS_RCV_HEADER   AARH,"
						+ " AMS_ASSETS_TRANS_HEADER AATH,"
						+ " SF_USER                 SU,"
						+ " SF_GROUP                SG,"
						+ " ETS_OU_CITY_MAP         EOCM1,"
						+ " ETS_OU_CITY_MAP         EOCM2,"
						+ " AMS_MIS_DEPT            AMD2"
						+ " WHERE"
						+ " AARH.TRANS_ID = AATH.TRANS_ID"
						+ " AND AARH.RECEIVE_USER = SU.USER_ID"
						+ " AND AARH.RECEIVE_ORGANIZATION_ID = EOCM1.ORGANIZATION_ID"
						+ " AND AATH.FROM_ORGANIZATION_ID = EOCM2.ORGANIZATION_ID"
						+ " AND AATH.FROM_DEPT *= AMD2.DEPT_CODE"
						+ " AND AARH.GROUP_ID *= SG.GROUP_ID"
						+ " AND AARH.RECEIVE_HEADER_ID = ?";
		sqlArgs.add(dto.getReceiveHeaderId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：获取当前用户对指定流程的指定应用的当前节点是否可进行审批的SQL
	 * @return SQLModel
	 */
	public SQLModel getCanApproveModel() {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsRcvHeaderDTO dto = (AmsAssetsRcvHeaderDTO) dtoParameter;
		String sqlStr = "SELECT"
						+ " 1"
						+ " FROM"
						+ " SF_PROCEDURE_DEF SPD,"
						+ " SF_TASK_DEFINE   STD,"
						+ " SF_ACT           SA,"
						+ " SF_ACT_INFO      SAI"
						+ " WHERE"
						+ " SPD.PROC_ID = STD.PROC_ID"
						+ " AND STD.PROC_ID = SA.PROC_ID"
						+ " AND STD.TASK_ID = SA.CUR_TASK_ID"
						+ " AND SA.ACTID = SAI.ACT_ID"
						+ " AND SPD.PROC_NAME = ?"
						+ " AND SA.APP_ID = ?"
						+ " AND ((SAI.USER_ID = ? AND SAI.AGENT_USER_ID " + SyBaseSQLUtil.isNullNoParam() + " ) OR SAI.AGENT_USER_ID = ?)"
						+ " AND NOT EXISTS("
						+ " SELECT"
						+ " NULL"
						+ " FROM"
						+ " AMS_ASSETS_RCV_HEADER AARH"
						+ " WHERE"
						+ " AARH.RECEIVE_HEADER_ID = SA.APP_ID"
						+ " AND AARH.CREATED_BY <> ?)";
		List sqlArgs = new ArrayList();
		sqlArgs.add(dto.getProcdureName());
		sqlArgs.add(dto.getReceiveHeaderId());
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(userAccount.getUserId());
		sqlModel.setArgs(sqlArgs);
		sqlModel.setSqlStr(sqlStr);
		return sqlModel;
	}

	/**
	 * 功能：根据外键关联字段 transId 构造查询数据SQL。
	 * 框架自动生成数据调拨资产接收头表(部门间和公司间需要) AMS_ASSETS_RCV_HEADER详细信息查询SQLModel，请根据实际需要修改。
	 * @param transId String
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	private SQLModel getDataByTransIdModel(String transId) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
						+ " AARH.RECEIVE_HEADER_ID,"
						+ " AARH.RECEIVE_NO,"
						+ " AARH.RECEIVE_DATE,"
						+ " AARH.RECEIVE_USER,"
						+ " AARH.FA_CONTENT_CODE,"
						+ " AMS_PUB_PKG.GET_FLEX_VALUE(AARH.FA_CONTENT_CODE, 'FA_CONTENT_CODE') FA_CONTENT_NAME,"
						+ " AMS_PUB_PKG.GET_FLEX_VALUE(AARH.ORDER_STATUS, 'ORDER_STATUS') ORDER_STATUS_NAME,"
						+ " SU.USERNAME RECEIVE_USER_NAME,"
						+ " AMD2.DEPT_NAME FROM_DEPT_NAME,"
						+ " AMS_ASSETS_PKG.GET_RCV_DEPT_NAME(AARH.RECEIVE_HEADER_ID) RECEIVE_DEPT_NAME,"
						+ " AARH.RECEIVE_ORGANIZATION_ID,"
						+ " EOCM.COMPANY RECEIVE_COMPANY,"
						+ " AATH.TRANS_NO,"
						+ " AATH.CREATED_REASON TRANS_REASON,"
						+ " AATH.TRANS_TYPE,"
						+ " AATH.TRANSFER_TYPE,"
						+ " AATH.APPROVED_DATE"
						+ " FROM"
						+ " AMS_ASSETS_RCV_HEADER   AARH,"
						+ " AMS_ASSETS_TRANS_HEADER AATH,"
						+ " SF_USER                 SU,"
						+ " ETS_OU_CITY_MAP         EOCM,"
						+ " AMS_MIS_DEPT            AMD2"
						+ " WHERE"
						+ " AARH.TRANS_ID = AATH.TRANS_ID"
						+ " AND AARH.RECEIVE_USER = SU.USER_ID"
						+ " AND AARH.RECEIVE_ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
						+ " AND AATH.FROM_DEPT *= AMD2.DEPT_CODE"
						+ " AND AARH.TRANS_ID = ?";
		sqlArgs.add(transId);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据外键获取数据
	 * @param foreignKey 传入的外键字段名称。
	 * @return SQLModel
	 */
	public SQLModel getDataByForeignKeyModel(String foreignKey) {
		SQLModel sqlModel = null;
		AmsAssetsRcvHeaderDTO dto = (AmsAssetsRcvHeaderDTO) dtoParameter;
		if (foreignKey.equals("transId")) {
			sqlModel = getDataByTransIdModel(StrUtil.nullToString(dto.getTransId()));
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成调拨资产接收头表(部门间和公司间需要) AMS_ASSETS_RCV_HEADER页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			AmsAssetsRcvHeaderDTO dto = (AmsAssetsRcvHeaderDTO) dtoParameter;
			String sqlStr = "SELECT"
							+ " AARH.RECEIVE_HEADER_ID,"
							+ " AARH.RECEIVE_NO,"
							+ " AARH.RECEIVE_DATE,"
							+ " AARH.RECEIVE_USER,"
							+ " SU.USERNAME RECEIVE_USER_NAME,"
							+ " AARH.FA_CONTENT_CODE,"
							+ " AMS_PUB_PKG.GET_FLEX_VALUE(AARH.FA_CONTENT_CODE, 'FA_CONTENT_CODE') FA_CONTENT_NAME,"
							+ " AMS_PUB_PKG.GET_FLEX_VALUE(AARH.ORDER_STATUS, 'ORDER_STATUS') ORDER_STATUS_NAME,"
							+ " AMD2.DEPT_NAME FROM_DEPT_NAME,"
							+ " AMS_ASSETS_PKG.GET_RCV_DEPT_NAME(AARH.RECEIVE_HEADER_ID) RECEIVE_DEPT_NAME,"
							+ " AARH.RECEIVE_ORGANIZATION_ID,"
							+ " EOCM1.COMPANY RECEIVE_COMPANY,"
							+ " EOCM2.COMPANY FROM_COMPANY,"
							+ " AATH.TRANS_NO,"
							+ " AATH.CREATED_REASON TRANS_REASON,"
							+ " AATH.APPROVED_DATE TRANS_OUT_DATE,"
							+ " AATH.TRANS_TYPE,"
							+ " AATH.TRANSFER_TYPE,"
							+ " AATH.APPROVED_DATE"
							+ " FROM"
							+ " AMS_ASSETS_RCV_HEADER   AARH,"
							+ " AMS_ASSETS_TRANS_HEADER AATH,"
							+ " SF_USER                 SU,"
							+ " ETS_OU_CITY_MAP         EOCM1,"
							+ " ETS_OU_CITY_MAP         EOCM2,"
							+ " AMS_MIS_DEPT            AMD2"
							+ " WHERE"
							+ " AARH.TRANS_ID = AATH.TRANS_ID"
							+ " AND AARH.RECEIVE_USER = SU.USER_ID"
							+ " AND AARH.RECEIVE_ORGANIZATION_ID = EOCM1.ORGANIZATION_ID"
							+ " AND AATH.FROM_ORGANIZATION_ID = EOCM2.ORGANIZATION_ID"
							+ " AND AATH.FROM_DEPT *= AMD2.DEPT_CODE"
							+ " AND AATH.TRANS_NO LIKE dbo.NVL(?, AATH.TRANS_NO)"
							+ " AND AARH.RECEIVE_NO LIKE dbo.NVL(?, AARH.RECEIVE_NO)"
							+ " AND AARH.RECEIVE_DATE >= ISNULL(?, AARH.RECEIVE_DATE)"
							+ " AND AARH.RECEIVE_DATE <= ISNULL(?, AARH.RECEIVE_DATE)"
                            + " AND AATH.TRANSFER_TYPE='BTW_DEPT'" ;
			sqlArgs.add(dto.getTransNo());
			sqlArgs.add(dto.getReceiveNo());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			if (!userAccount.isSysAdmin()) {
				sqlStr += " AND AARH.RECEIVE_ORGANIZATION_ID = ?";
				sqlArgs.add(userAccount.getOrganizationId());
                //如有人需要修改如下语句，请电话通知周子君
                /*if (!userAccount.isComAssetsManager() &&
					!userAccount.isCityAdmin()) {
					sqlStr = sqlStr
							 + " AND (AARH.CREATED_BY = ? OR EXISTS("
							 + " SELECT"
							 + " NULL"
							 + " FROM"
							 + " SF_ACT_LOG SAL"
							 + " WHERE"
							 + " SAL.APP_ID = AARH.RECEIVE_HEADER_ID"
							 + " AND SAL.PROC_NAME = ?"
							 +
							" AND (SAL.CUR_USERID = ? OR SAL.CREATED_BY = ?)))";
					sqlArgs.add(userAccount.getUserId());
					sqlArgs.add(dto.getProcdureName());
					sqlArgs.add(userAccount.getUserId());
					sqlArgs.add(userAccount.getUserId());
				}*/
			}
			sqlStr = sqlStr
					 + " ORDER BY"
					 + " AARH.RECEIVE_ORGANIZATION_ID,"
					 + " AARH.RECEIVE_DATE DESC";
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}
}
