package com.sino.ams.prematch.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.prematch.dto.AmsPaMatchLogDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;


/**
 * <p>Title: AmsPaMatchLogModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsPaMatchLogModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class AmsPaMatchLogModel extends AMSSQLProducer {

	/**
	 * 功能：EAM系统资产实物与MIS转资准备清单预匹配 AMS_PA_MATCH_LOG 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsPaMatchLogDTO 本次操作的数据
	 */
	public AmsPaMatchLogModel(SfUserDTO userAccount, AmsPaMatchLogDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：框架自动生成EAM系统资产实物与MIS转资准备清单预匹配 AMS_PA_MATCH_LOG数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataCreateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			AmsPaMatchLogDTO dto = (AmsPaMatchLogDTO) dtoParameter;
			String sqlStr = "INSERT INTO "
							+ " AMS_PA_MATCH_LOG("
							+ " LOG_ID,"
							+ " SYSTEM_ID,"
							+ " TAG_NUMBER,"
							+ " CREATION_DATE,"
							+ " CREATED_BY,"
							+ " ORGANIZATION_ID,"
							+ " ACT,"
							+ " MATCHED_BY,"
							+ " MATCHED_DATE,"
							+ " REMARK"
							+ ") VALUES ("
							+ "  NEWID() , ?, ?, GETDATE(), ?, ?, ?, ?, ?, ?)";

			sqlArgs.add(dto.getSystemId());
			sqlArgs.add(dto.getTagNumber());
			sqlArgs.add(userAccount.getUserId());
			sqlArgs.add(userAccount.getOrganizationId());
			sqlArgs.add(dto.getAct());
			sqlArgs.add(userAccount.getUserId());
			sqlArgs.add(dto.getMatchedDate());
			sqlArgs.add(dto.getRemark());
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成EAM系统资产实物与MIS转资准备清单预匹配 AMS_PA_MATCH_LOG页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			AmsPaMatchLogDTO dto = (AmsPaMatchLogDTO) dtoParameter;
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
							+ " SU.USERNAME MATCHED_USER,"
							+ " SU2.USERNAME CREATED_USER,"
							+ " APM.CREATION_DATE ACT_DATE,"
							+ " APM.MATCHED_DATE"
							+ " FROM"
							+ " AMS_PA_ASSETS       APA,"
							+ " ETS_ITEM_INFO       EII,"
							+ " ETS_SYSTEM_ITEM     ESI,"
							+ " AMS_OBJECT_ADDRESS  AOA,"
							+ " ETS_OBJECT          EO,"
							+ " SF_USER             SU,"
							+ " ETS_PA_PROJECTS_ALL EPPA,"
							+ " AMS_MIS_EMPLOYEE    AME,"
							+ " AMS_PA_MATCH_LOG    APM,"
							+ " SF_USER             SU2"
							+ " WHERE"
							+ " ESI.ITEM_CODE = EII.ITEM_CODE"
							+ " AND EII.ADDRESS_ID = AOA.ADDRESS_ID"
							+ " AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO"
							+ " AND EII.RESPONSIBILITY_USER = AME.EMPLOYEE_ID"
							+ " AND EII.PROJECTID = EPPA.PROJECT_ID"
							+ " AND EII.SYSTEMID = APM.SYSTEM_ID"
							+ " AND APM.TAG_NUMBER = APA.TAG_NUMBER"
							+ " AND APM.MATCHED_BY = SU.USER_ID"
							+ " AND APM.CREATED_BY = SU2.USER_ID"
							+ " AND SU2.USERNAME LIKE dbo.NVL(?, SU2.USERNAME)"
							+ " AND APM.CREATION_DATE >= ISNULL(?, APM.CREATION_DATE)"
							+ " AND APM.CREATION_DATE <= ISNULL(?, APM.CREATION_DATE)"
							+ " AND EII.ORGANIZATION_ID = ?";
			sqlArgs.add(dto.getCreatedUser());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

}
