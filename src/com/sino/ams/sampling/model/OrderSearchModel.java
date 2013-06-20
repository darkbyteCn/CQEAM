package com.sino.ams.sampling.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.sampling.dto.AmsAssetsSamplingHeaderDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

/**
 * <p>Title: OrderSearchModel</p>
 * <p>Description:程序自动生成SQL构造器“OrderSearchModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class OrderSearchModel extends AMSSQLProducer {

	/**
	 * 功能：抽查工单表 AMS_ASSETS_SAMPLING_HEADER 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsAssetsSamplingHeaderDTO 本次操作的数据
	 */
	public OrderSearchModel(SfUserDTO userAccount, AmsAssetsSamplingHeaderDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}


	/**
	 * 功能：框架自动生成抽查工单表 AMS_ASSETS_SAMPLING_HEADER页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			AmsAssetsSamplingHeaderDTO dto = (AmsAssetsSamplingHeaderDTO) dtoParameter;
			String sqlStr = "SELECT"
							+ " AASH.*,"
							+ " dbo.APP_GET_FLEX_VALUE(AASH.ORDER_STATUS, 'CHKORDER_STATUS') ORDER_STATUS_VALUE,"
							+ " EO.WORKORDER_OBJECT_CODE SAMPLING_LOCATION_CODE,"
							+ " EO.WORKORDER_OBJECT_NAME SAMPLING_LOCATION_NAME,"
							+ " SUI.USERNAME IMPLEMENT_USER,"
							+ " SUC.USERNAME TASK_CREATED_USER,"
							+ " AAST.TASK_NO,"
							+ " AAST.TASK_NAME,"
							+ " AAST.CREATION_DATE TASK_CREATION_DATE,"
							+ " AAST.START_DATE,"
							+ " AAST.END_DATE,"
							+ " EOCMC.COMPANY CREATED_OU_NAME,"
							+ " EOCMI.COMPANY SAMPLED_OU_NAME,"
							+ " AASB.BATCH_NO"
							+ " FROM"
							+ " ETS_OU_CITY_MAP            EOCMC,"
							+ " ETS_OU_CITY_MAP            EOCMI,"
							+ " SF_USER                    SUC,"
							+ " SF_USER                    SUI,"
							+ " ETS_OBJECT                 EO,"
							+ " AMS_ASSETS_SAMPLING_TASK   AAST,"
							+ " AMS_SAMPLING_TASK_ASSIGN   ASTA,"
							+ " AMS_ASSETS_SAMPLING_BATCH  AASB,"
							+ " AMS_ASSETS_SAMPLING_HEADER AASH"
							+ " WHERE"
							+ " AAST.CREATED_BY = SUC.USER_ID"
							+ " AND AAST.CREATED_OU = EOCMC.ORGANIZATION_ID"
							+ " AND AAST.TASK_ID = ASTA.TASK_ID"
							+ " AND ASTA.TASK_ID = AASB.TASK_ID"
							+ " AND ASTA.ORGANIZATION_ID = AASB.ORGANIZATION_ID"
							+ " AND AASB.BATCH_ID = AASH.BATCH_ID" ;
							if (!dto.getSamplingLocationName().trim().equals("")) {
								sqlStr += " AND AASH.SAMPLING_LOCATION = EO.WORKORDER_OBJECT_NO" ;
							} else {
								sqlStr += " AND AASH.SAMPLING_LOCATION *= EO.WORKORDER_OBJECT_NO" ;
							}
							if (!dto.getImplementUser().trim().equals("")) {
								sqlStr += " AND AASH.IMPLEMENT_BY = SUI.USER_ID" ;
							} else {
								sqlStr += " AND AASH.IMPLEMENT_BY *= SUI.USER_ID" ;
							}
							sqlStr +=
							  " AND AASH.ORGANIZATION_ID = EOCMI.ORGANIZATION_ID"
							+ " AND EOCMI.ORGANIZATION_ID = ISNULL(?, EOCMI.ORGANIZATION_ID)"//工单执行公司
							+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR AASH.ORDER_NO LIKE dbo.NVL(?, AASH.ORDER_NO))"
							+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.WORKORDER_OBJECT_NAME LIKE ?)"
							+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR SUI.USERNAME LIKE ?)"
							+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR AAST.TASK_NAME LIKE dbo.NVL(?, AAST.TASK_NAME) )"
							+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR AAST.END_DATE >= ISNULL(?, AAST.END_DATE))"
							+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR AAST.END_DATE <= ISNULL(?, AAST.END_DATE))"
							+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR AASH.ORDER_STATUS = dbo.NVL(?, AASH.ORDER_STATUS))"
							+ " ORDER BY"
							+ " AASH.ORDER_NO DESC";

			if(userAccount.isProvinceUser() && dto.getSampledOu()>0){
				sqlArgs.add(dto.getSampledOu());
			} else {
				sqlArgs.add(userAccount.getOrganizationId());
			}
			sqlArgs.add(dto.getOrderNo());
			sqlArgs.add(dto.getOrderNo());
			sqlArgs.add(dto.getSamplingLocationName());
			sqlArgs.add(dto.getSamplingLocationName());
			sqlArgs.add(dto.getImplementUser());
			sqlArgs.add(dto.getImplementUser());
			sqlArgs.add(dto.getTaskName());
			sqlArgs.add(dto.getTaskName());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getEndDate());
			sqlArgs.add(dto.getEndDate());
			sqlArgs.add(dto.getOrderStatus());
			sqlArgs.add(dto.getOrderStatus());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}
}
