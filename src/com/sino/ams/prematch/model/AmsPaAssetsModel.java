package com.sino.ams.prematch.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.prematch.dto.AmsPaAssetsDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;


/**
 * <p>Title: AmsPaAssetsModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsPaAssetsModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */

public class AmsPaAssetsModel extends AMSSQLProducer {

/**
	 * 功能：MIS转资准备清单 AMS_PA_ASSETS 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsPaAssetsDTO 本次操作的数据
	 */
	public AmsPaAssetsModel(SfUserDTO userAccount, AmsPaAssetsDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}


	/**
	 * 功能：框架自动生成MIS转资准备清单 AMS_PA_ASSETS数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsPaAssetsDTO dto = (AmsPaAssetsDTO) dtoParameter;
		String sqlStr = "SELECT "
						+ " *"
						+ " FROM"
						+ " AMS_PA_ASSETS APA"
						+ " WHERE"
						+ " TAG_NUMBER = ?";
		sqlArgs.add(dto.getTagNumber());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成MIS转资准备清单 AMS_PA_ASSETS页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT "
						+ " *"
						+ " FROM"
						+ " AMS_PA_ASSETS APA"
						+ " WHERE"
						+ " (APA.PROJECT_NUMBER LIKE dbo.NVL(?, APA.PROJECT_NUMBER)"
						+ " OR APA.PROJECT_NAME LIKE dbo.NVL(?, APA.PROJECT_NAME))"
						+ " AND (APA.ASSETS_LOCATION_CODE LIKE dbo.NVL(?, APA.ASSETS_LOCATION_CODE)"
						+ " OR APA.ASSETS_LOCATION LIKE dbo.NVL(?, APA.ASSETS_LOCATION))"
						+ " AND (APA.TASK_NAME LIKE dbo.NVL(?, APA.TASK_NAME)"
						+ " OR APA.TASK_NUMBER LIKE dbo.NVL(?, APA.TASK_NUMBER))"
						+ " AND APA.ORGANIZATION_ID = ?";
		AmsPaAssetsDTO dto = (AmsPaAssetsDTO) dtoParameter;
		sqlArgs.add(dto.getProjectNumber());
		sqlArgs.add(dto.getProjectNumber());
		sqlArgs.add(dto.getAssetsLocationCode());
		sqlArgs.add(dto.getAssetsLocationCode());
		sqlArgs.add(dto.getTaskNumber());
		sqlArgs.add(dto.getTaskNumber());

		sqlArgs.add(userAccount.getOrganizationId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
