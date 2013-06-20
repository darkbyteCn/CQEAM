package com.sino.ams.prematch.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.constant.AMSActionConstant;
import com.sino.ams.prematch.dto.AmsPaMatchDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.ArrUtil;


/**
 * <p>Title: AmsPaMatchModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsPaMatchModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class AmsPaMatchModel extends AMSSQLProducer {

	/**
	 * 功能：EAM系统资产实物与MIS转资准备清单预匹配 AMS_PA_MATCH 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsPaMatchDTO 本次操作的数据
	 */
	public AmsPaMatchModel(SfUserDTO userAccount, AmsPaMatchDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：框架自动生成EAM系统资产实物与MIS转资准备清单预匹配 AMS_PA_MATCH数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 */
	public SQLModel getDataCreateModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsPaMatchDTO dto = (AmsPaMatchDTO) dtoParameter;
		String sqlStr = "INSERT INTO "
						+ " AMS_PA_MATCH("
						+ " SYSTEM_ID,"
						+ " TAG_NUMBER,"
						+ " CREATION_DATE,"
						+ " CREATED_BY,"
						+ " ORGANIZATION_ID,"
						+ " REMARK"
						+ ") VALUES (?, ?, GETDATE(), ?, ?, ?)";

		sqlArgs.add(dto.getSystemId());
		sqlArgs.add(dto.getTagNumber());
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(userAccount.getOrganizationId());
		sqlArgs.add(dto.getRemark());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}


	/**
	 * 功能：框架自动生成EAM系统资产实物与MIS转资准备清单预匹配 AMS_PA_MATCH数据删除SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDeleteByPrimaryKeyModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsPaMatchDTO dto = (AmsPaMatchDTO) dtoParameter;
		String sqlStr = "DELETE"
						+ " FROM"
						+ " AMS_PA_MATCH"
						+ " WHERE"
						+ " SYSTEM_ID = ?";
		sqlArgs.add(dto.getSystemId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}


	/**
	 * 功能：框架自动生成EAM系统资产实物与MIS转资准备清单预匹配 AMS_PA_MATCH页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			AmsPaMatchDTO dto = (AmsPaMatchDTO)dtoParameter;
			String sqlStr = "SELECT"
							+ " EII.SYSTEMID,"
							+ " EII.BARCODE,"
							+ " APA.TAG_NUMBER,"
							+ " ESI.ITEM_CATEGORY,"
							+ " AMS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY_NAME,"
							+ " ESI.ITEM_NAME,"
							+ " APA.ASSETS_DESCRIPTION,"
							+ " ESI.ITEM_SPEC,"
							+ " APA.MODEL_NUMBER,"
							+ " EPPA.SEGMENT1 PROJECT_NUMBER_AMS,"
							+ " APA.PROJECT_NUMBER,"
							+ " EPPA.NAME PROJECT_NAME_AMS,"
							+ " APA.PROJECT_NAME,"
							+ " EO.WORKORDER_OBJECT_CODE,"
							+ " APA.ASSETS_LOCATION_CODE,"
							+ " EO.WORKORDER_OBJECT_NAME,"
							+ " APA.ASSETS_LOCATION,"
							+ " AME.EMPLOYEE_NUMBER,"
							+ " APA.ASSIGNED_TO_NUMBER,"
							+ " AME.USER_NAME,"
							+ " APA.ASSIGNED_TO_NAME,"
							+ " SU.USERNAME,"
							+ " APM.CREATION_DATE,"
							+ " APM.REMARK"
							+ " FROM"
							+ " AMS_PA_MATCH        APM,"
							+ " AMS_PA_ASSETS       APA,"
							+ " ETS_ITEM_INFO       EII,"
							+ " ETS_SYSTEM_ITEM     ESI,"
							+ " AMS_OBJECT_ADDRESS  AOA,"
							+ " ETS_OBJECT          EO,"
							+ " SF_USER             SU,"
							+ " ETS_PA_PROJECTS_ALL EPPA,"
							+ " AMS_MIS_EMPLOYEE    AME"
							+ " WHERE"
							+ " ESI.ITEM_CODE = EII.ITEM_CODE"
							+ " AND EII.ADDRESS_ID = AOA.ADDRESS_ID"
							+ " AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO"
							+ " AND EII.RESPONSIBILITY_USER = AME.EMPLOYEE_ID"
							+ " AND EII.PROJECTID = EPPA.PROJECT_ID"
							+ " AND EII.SYSTEMID = APM.SYSTEM_ID"
							+ " AND APM.TAG_NUMBER = APA.TAG_NUMBER"
							+ " AND APM.CREATED_BY = SU.USER_ID"
							+ " AND (APA.PROJECT_NUMBER LIKE dbo.NVL(?, APA.PROJECT_NUMBER)"
							+ " OR EPPA.SEGMENT1 LIKE dbo.NVL(?, EPPA.SEGMENT1))"
							+ " AND (APA.PROJECT_NAME LIKE dbo.NVL(?, APA.PROJECT_NAME)"
							+ " OR EPPA.NAME LIKE dbo.NVL(?, EPPA.NAME))"
							+ " AND (EO.WORKORDER_OBJECT_CODE LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_CODE)"
							+ " OR APA.ASSETS_LOCATION_CODE LIKE dbo.NVL(?, APA.ASSETS_LOCATION_CODE))"
							+ " AND (EO.WORKORDER_OBJECT_NAME LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_NAME)"
							+ " OR APA.ASSETS_LOCATION LIKE dbo.NVL(?, APA.ASSETS_LOCATION))"
							+ " AND APM.CREATION_DATE >= ISNULL(?, APM.CREATION_DATE)"
							+ " AND APM.CREATION_DATE <= ISNULL(?, APM.CREATION_DATE)"
							+ " AND EII.ORGANIZATION_ID = ?"
							+ " ORDER BY"
							+ " APM.CREATION_DATE DESC";
			sqlArgs.add(dto.getProjectNumber());
			sqlArgs.add(dto.getProjectNumber());
			sqlArgs.add(dto.getProjectName());
			sqlArgs.add(dto.getProjectName());
			sqlArgs.add(dto.getAssetsLocationCode());
			sqlArgs.add(dto.getAssetsLocationCode());
			sqlArgs.add(dto.getAssetsLocation());
			sqlArgs.add(dto.getAssetsLocation());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(userAccount.getOrganizationId());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：当删除匹配关系时，将原来的记录备份至日志表，并标示为已删除
	 * @param systemIdArr String[]
	 * @return SQLModel
	 */
	public SQLModel getDeleteMatchLogModel(String[] systemIdArr){
		SQLModel sqlModel = new SQLModel();
		String systemIds = ArrUtil.arrToSqlStr(systemIdArr);
		String sqlStr = "INSERT INTO AMS_PA_MATCH_LOG("
						+ " LOG_ID,"
						+ " SYSTEM_ID,"
						+ " TAG_NUMBER,"
						+ " CREATION_DATE,"
						+ " CREATED_BY,"
						+ " ORGANIZATION_ID,"
						+ " ACT,"
						+ " MATCHED_DATE,"
						+ " MATCHED_BY,"
						+ " REMARK)"
						+ " (SELECT"
						+ "  NEWID() ,"
						+ " APM.SYSTEM_ID,"
						+ " APM.TAG_NUMBER,"
						+ " GETDATE(),"
						+ " ?,"
						+ " ?,"
						+ " ?,"
						+ " APM.CREATION_DATE,"
						+ " APM.CREATED_BY,"
						+ " APM.REMARK"
						+ " FROM"
						+ " AMS_PA_MATCH APM"
						+ " WHERE"
						+ " APM.SYSTEM_ID IN (" + systemIds + "))";
		List sqlArgs = new ArrayList();
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(userAccount.getOrganizationId());
		sqlArgs.add(AMSActionConstant.DELETE_ACTION);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：获取判断匹配关系是否可删除的SQL
	 * @param systemIdArr String[]
	 * @return SQLModel
	 */
	public SQLModel getCanDeleteModel(String[] systemIdArr){
		SQLModel sqlModel = new SQLModel();
		String systemIds = ArrUtil.arrToSqlStr(systemIdArr);
		String sqlStr = "SELECT"
						+ " EII.BARCODE,"
						+ " APM.TAG_NUMBER"
						+ " FROM"
						+ " ETS_ITEM_INFO  EII,"
						+ " ETS_ITEM_MATCH EIM,"
						+ " ETS_FA_ASSETS  EFA,"
						+ " AMS_PA_MATCH   APM"
						+ " WHERE"
						+ " EII.SYSTEMID = EIM.SYSTEMID"
						+ " AND EIM.ASSET_ID = EFA.ASSET_ID"
						+ " AND EII.SYSTEMID = APM.SYSTEM_ID"
						+ " AND APM.TAG_NUMBER = EFA.TAG_NUMBER"
						+ " AND EII.SYSTEMID IN (" + systemIds + ")"
						+ " AND EII.ORGANIZATION_ID = ?";
		List sqlArgs = new ArrayList();
		sqlArgs.add(userAccount.getOrganizationId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
