package com.sino.ams.sampling.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.sampling.constant.SamplingDicts;
import com.sino.ams.sampling.dto.AmsAssetsSamplingHeaderDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

/**
 * <p>Title: SinoAMS</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class BatchOrderModel extends AMSSQLProducer {
	public BatchOrderModel(SfUserDTO userAccount, AmsAssetsSamplingHeaderDTO dtoParameter) {
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
							+ " AND AASB.BATCH_ID = AASH.BATCH_ID"
							+ " AND AASH.SAMPLING_LOCATION *= EO.WORKORDER_OBJECT_NO"
							+ " AND AASH.IMPLEMENT_BY *= SUI.USER_ID"
							+ " AND AASH.ORGANIZATION_ID = EOCMI.ORGANIZATION_ID"
                            + " AND ( ? = -1 OR EOCMC.ORGANIZATION_ID = ?)"//任务创建公司
                            + " AND ( ? = -1 OR EOCMI.ORGANIZATION_ID = ?)"//工单执行公司
                            + " AND ( " + SyBaseSQLUtil.isNull() + "  OR AASH.ORDER_NO LIKE ?)"
							+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.WORKORDER_OBJECT_NAME LIKE ?)"
							+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR SUI.USERNAME LIKE ?)"
                            + " AND ( " + SyBaseSQLUtil.isNull() + "  OR AAST.TASK_NO LIKE ?)"
                            + " AND ( " + SyBaseSQLUtil.isNull() + "  OR AASB.BATCH_NO LIKE ?)"
							+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR AASH.START_TIME >= dbo.NVL(?, AASH.START_TIME))"
							+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR AASH.START_TIME <= dbo.NVL(?, AASH.START_TIME))"
							+ " AND AASH.ORDER_STATUS = ?"
							+ " AND AASH.CREATED_OU = ?";

			sqlArgs.add(dto.getCreatedOu());
			sqlArgs.add(dto.getCreatedOu());
			sqlArgs.add(dto.getSampledOu());
			sqlArgs.add(dto.getSampledOu());
			sqlArgs.add(dto.getOrderNo());
			sqlArgs.add(dto.getOrderNo());
			sqlArgs.add(dto.getSamplingLocationName());
			sqlArgs.add(dto.getSamplingLocationName());
			sqlArgs.add(dto.getImplementUser());
			sqlArgs.add(dto.getImplementUser());
			sqlArgs.add(dto.getTaskNo());
			sqlArgs.add(dto.getTaskNo());
			sqlArgs.add(dto.getBatchNo());
			sqlArgs.add(dto.getBatchNo());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getEndDate());
			sqlArgs.add(dto.getEndDate());
			sqlArgs.add(SamplingDicts.ORDER_STS1_SAVE_TEMP);
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
	 * 功能：构造任务创建公司，任务执行公司相关信息
	 * @return SQLModel
	 */
	public SQLModel getOrgInfoModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsSamplingHeaderDTO dto = (AmsAssetsSamplingHeaderDTO) dtoParameter;
		String sqlStr = "";
		if(dto.getSampledOuName().equals("")){
			sqlStr = "SELECT"
					 + " ? SAMPLED_OU,"
					 + " ? SAMPLED_OU_NAME,"
					 + " EOCM.ORGANIZATION_ID CREATED_OU,"
					 + " EOCM.COMPANY CREATED_OU_NAME"
					 + " FROM"
					 + " ETS_OU_CITY_MAP EOCM"
					 + " WHERE  EOCM.COMPANY = ?";
			sqlArgs.add(userAccount.getOrganizationId());
			sqlArgs.add(userAccount.getCompany());
			sqlArgs.add(dto.getCreatedOuName());
		} else {
			sqlStr = "SELECT"
					 + " EOCM.ORGANIZATION_ID SAMPLED_OU,"
					 + " EOCM.COMPANY SAMPLED_OU_NAME,"
					 + " ? CREATED_OU,"
					 + " ? CREATED_OU_NAME"
					 + " FROM"
					 + " ETS_OU_CITY_MAP EOCM"
					 + " WHERE  EOCM.COMPANY = ?";
			sqlArgs.add(userAccount.getOrganizationId());
			sqlArgs.add(userAccount.getCompany());
			sqlArgs.add(dto.getSampledOuName());
		}
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
