package com.sino.ams.newasset.message.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;
import com.sino.ams.newasset.message.dto.FreeAssetsMsgSetAndQueryDTO;

public class FreeAssetsMsgSetAndQueryModel extends AMSSQLProducer {
	/**
	 * 功能：消息类别定义 EAM_MSG_CATEGORY 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter SfMsgCategoryDTO 本次操作的数据
	 */
	public FreeAssetsMsgSetAndQueryModel(SfUserDTO userAccount, FreeAssetsMsgSetAndQueryDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：框架自动生成消息类别定义 EAM_MSG_CATEGORY数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 */
	public SQLModel getDataCreateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		FreeAssetsMsgSetAndQueryDTO dto = (FreeAssetsMsgSetAndQueryDTO) dtoParameter;
		String sqlStr = "INSERT INTO "
						+ " EAM_MSG_CATEGORY("
						+ " MSG_CATEGORY_ID,"
						+ " MSG_DESC,"
						+ " NEED_RESEND,"
						+ " RESEND_MAXTIMES,"
						+ " RESEND_DISTANCE,"
						+ " ORGANIZATION_ID,"
						+ " CREATED_BY,"
						+ " ENABLED,"
						+ " COLLECT_SEND,"
						+ " CREATION_DATE"
						+ ") VALUES ("
						+ " ?, ?, ?, ?, ?, ?, ?, ?, ?,  " +  SyBaseSQLUtil.getCurDate() + " )";
		sqlArgs.add(dto.getMsgCategoryId());
		sqlArgs.add(dto.getMsgDesc());
		sqlArgs.add(dto.getNeedResend());
		sqlArgs.add(dto.getResendMaxtimes());
		sqlArgs.add(dto.getResendDistance());
		sqlArgs.add(dto.getOrganizationId());
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(dto.getEnabled());
		sqlArgs.add(dto.getCollectSend());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成消息类别定义 EAM_MSG_CATEGORY数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据更新用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataUpdateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		FreeAssetsMsgSetAndQueryDTO dto = (FreeAssetsMsgSetAndQueryDTO) dtoParameter;
		String sqlStr = "UPDATE EAM_MSG_CATEGORY"
						+ " SET"
						+ " MSG_DESC = ?,"
						+ " NEED_RESEND = ?,"
						+ " RESEND_MAXTIMES = ?,"
						+ " RESEND_DISTANCE = ?,"
						+ " LAST_UPDATE_DATE =  " +  SyBaseSQLUtil.getCurDate() + " ,"
						+ " LAST_UPDATE_BY = ?,"
						+ " COLLECT_SEND = ?,"
						+ " ENABLED = ?"
						+ " WHERE"
						+ " MSG_CATEGORY_ID = ?";

		sqlArgs.add(dto.getMsgDesc());
		sqlArgs.add(dto.getNeedResend());
		sqlArgs.add(dto.getResendMaxtimes());
		sqlArgs.add(dto.getResendDistance());
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(dto.getCollectSend());
		sqlArgs.add(dto.getEnabled());
		sqlArgs.add(dto.getMsgCategoryId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成消息类别定义 EAM_MSG_CATEGORY数据删除SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		FreeAssetsMsgSetAndQueryDTO dto = (FreeAssetsMsgSetAndQueryDTO) dtoParameter;
		String sqlStr = "DELETE FROM"
						+ " EAM_MSG_CATEGORY"
						+ " WHERE"
						+ " MSG_CATEGORY_ID = ?";
		sqlArgs.add(dto.getMsgCategoryId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成消息类别定义 EAM_MSG_CATEGORY数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		FreeAssetsMsgSetAndQueryDTO dto = (FreeAssetsMsgSetAndQueryDTO) dtoParameter;
		String sqlStr = "SELECT "
						+ " EMC.MSG_CATEGORY_ID,"
						+ " EOCM.COMPANY COMPANY_NAME,"
						+ " EMC.MSG_DESC,"
						+ " EMC.NEED_RESEND,"
						+ " EMC.RESEND_MAXTIMES,"
						+ " EMC.RESEND_DISTANCE,"
						+ " EMC.ORGANIZATION_ID,"
						+ " EMC.CREATED_BY,"
						+ " EMC.LAST_UPDATE_DATE,"
						+ " EMC.LAST_UPDATE_BY,"
						+ " EMC.ENABLED,"
						+ " EMC.COLLECT_SEND,"
						+ " EMC.CREATION_DATE"
						+ " FROM"
						+ " EAM_MSG_CATEGORY EMC,"
						+ " ETS_OU_CITY_MAP EOCM"
						+ " WHERE"
						+ " EMC.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
						+ " AND EMC.MSG_CATEGORY_ID = ?";
		sqlArgs.add(dto.getMsgCategoryId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成消息类别定义 EAM_MSG_CATEGORY页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 */
	public SQLModel getPageQueryModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		FreeAssetsMsgSetAndQueryDTO dto = (FreeAssetsMsgSetAndQueryDTO) dtoParameter;
		String sqlStr = "SELECT "
						+ " EMC.MSG_CATEGORY_ID,"
						+ " EOCM.COMPANY COMPANY_NAME,"
						+ " EMC.MSG_DESC,"
						+ " EMC.NEED_RESEND,"
						+ " EMC.RESEND_MAXTIMES,"
						+ " EMC.RESEND_DISTANCE,"
						+ " EMC.ORGANIZATION_ID,"
						+ " EMC.CREATED_BY,"
						+ " EMC.LAST_UPDATE_DATE,"
						+ " EMC.LAST_UPDATE_BY,"
						+ " EMC.ENABLED,"
						+ " EMC.CREATION_DATE"
						+ " FROM"
						+ " EAM_MSG_CATEGORY EMC,"
						+ " ETS_OU_CITY_MAP EOCM"
						+ " WHERE"
						+ " EMC.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
						//+ " AND EOCM.ORGANIZATION_ID = CONVERT(INT, dbo.NVL(?, CONVERT(VARCHAR, EOCM.ORGANIZATION_ID)))"
						+ " AND (" + SyBaseSQLUtil.nullIntParam() + " OR EOCM.ORGANIZATION_ID = ?)\n"
						+ " AND ( ? = '' OR ? IS NULL OR EMC.MSG_DESC LIKE '%' || dbo.NVL(?, EMC.MSG_DESC) || '%')"
						+ " AND ( ? = '' OR ? IS NULL OR EMC.NEED_RESEND = dbo.NVL(?, EMC.NEED_RESEND))"
						+ " AND ( ? = '' OR ? IS NULL OR EMC.COLLECT_SEND = dbo.NVL(?, EMC.COLLECT_SEND))";
		sqlArgs.add(dto.getOrganizationId());
		sqlArgs.add(dto.getOrganizationId());
		sqlArgs.add(dto.getOrganizationId());
		sqlArgs.add(dto.getMsgDesc());
		sqlArgs.add(dto.getMsgDesc());
		sqlArgs.add(dto.getMsgDesc());
		sqlArgs.add(dto.getNeedResend());
		sqlArgs.add(dto.getNeedResend());
		sqlArgs.add(dto.getNeedResend());
		sqlArgs.add(dto.getCollectSend());
		sqlArgs.add(dto.getCollectSend());
		sqlArgs.add(dto.getCollectSend());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：获取短信类别设置初始化SQLModel。系统管理员使用。
	 * @return SQLModel 短消息初始化SQL
	 */
	public SQLModel getMessageInitModel(){
		SQLModel sqlModel = new SQLModel();
		FreeAssetsMsgSetAndQueryDTO dto = (FreeAssetsMsgSetAndQueryDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String strSql = //SyBaseSQLUtil.createNextSeqInInsert( "AMS_NLE" ) 
						 "INSERT INTO EAM_MSG_CATEGORY("
						+ " MSG_CATEGORY_ID,"
						+ " MSG_DESC,"
						+ " NEED_RESEND,"
						+ " RESEND_MAXTIMES,"
						+ " RESEND_DISTANCE,"
						+ " ORGANIZATION_ID,"
						+ " ENABLED,"
						+ " FLOW_ID,"
						+ " COLLECT_SEND,"
						+ " CREATION_DATE,"
						+ " CREATED_BY) ("
						+ " SELECT"
						+ "  NEWID() , "
						+ " SPD.PROC_NAME || '--' || SFD.FLOW_DESC,"
						+ " ?, ?, ?,"
						+ " EOCM.ORGANIZATION_ID, ?, SFD.FLOW_ID, ?,  " +  SyBaseSQLUtil.getCurDate() + " , ?"
						+ " FROM"
						+ " SF_PROCEDURE_DEF SPD,"
						+ " SF_FLOW_DEFINE SFD,"
						+ " ETS_OU_CITY_MAP EOCM"
						+ " WHERE"
						+ " SPD.PROC_ID = SFD.PROC_ID"
						+ " AND NOT EXISTS ("
						+ " SELECT"
						+ " NULL"
						+ " FROM"
						+ " EAM_MSG_CATEGORY EMC"
						+ " WHERE"
						+ " SFD.FLOW_ID = EMC.FLOW_ID"
						+ " AND EMC.ORGANIZATION_ID = EOCM.ORGANIZATION_ID))";
		sqlArgs.add(dto.getNeedResend());
		sqlArgs.add(dto.getResendMaxtimes());
		sqlArgs.add(dto.getResendDistance());
		sqlArgs.add(dto.getEnabled());
		sqlArgs.add(dto.getCollectSend());
		sqlArgs.add(userAccount.getUserId());
		sqlModel.setSqlStr(strSql);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
    }
}
