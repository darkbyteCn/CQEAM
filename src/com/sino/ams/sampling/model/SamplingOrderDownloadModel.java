package com.sino.ams.sampling.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.sampling.constant.SamplingDicts;
import com.sino.ams.sampling.dto.AmsAssetsSamplingHeaderDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * <p>Title: SinoAMS</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class SamplingOrderDownloadModel extends AMSSQLProducer {

	public SamplingOrderDownloadModel(SfUserDTO userAccount, AmsAssetsSamplingHeaderDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：构造获取当前用户可下载的所有抽查工单的SQL
	 * @return SQLModel
	 */
	public SQLModel getDownloadOrdersModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String orderStatus = "'"
							 + SamplingDicts.ORDER_STS1_DISTRIBUTED
							 + "', '"
							 + SamplingDicts.ORDER_STS1_DOWNLOADED
							 + "'";
		String sqlStr = "SELECT"
						+ " AAST.TASK_NO,"
						+ " AAST.TASK_NAME,"
						+ " AASH.HEADER_ID,"
						+ " AASH.ORDER_NO,"
						+ " AASH.START_TIME,"
						+ " AASH.IMPLEMENT_DAYS,"
						+ " dbo.APP_GET_USER_NAME(AASH.CREATED_BY) CREATED_USER,"
						+ " dateadd(day,AASH.IMPLEMENT_DAYS,AASH.START_TIME) DEADLINE_DATE,"
						+ " EO.WORKORDER_OBJECT_NO SAMPLING_LOCATION,"
						+ " EO.WORKORDER_OBJECT_NAME SAMPLING_LOCATION_NAME,"
						+ " AASH.CREATION_DATE,"
						+ " SU.LOGIN_NAME CREATED_LOGIN_USER,"
						+ " AASH.DISTRIBUTE_DATE,"
						+ " AASH.ORDER_TYPE,"
						+ " '抽查' ORDER_TYPE_NAME"
						+ " FROM"
						+ " ETS_OBJECT                 EO,"
						+ " SF_USER                    SU,"
						+ " AMS_ASSETS_SAMPLING_TASK   AAST,"
						+ " AMS_ASSETS_SAMPLING_HEADER AASH"
						+ " WHERE"
						+ " AASH.TASK_ID = AAST.TASK_ID"
						+ " AND AASH.SAMPLING_LOCATION = EO.WORKORDER_OBJECT_NO"
						+ " AND AASH.CREATED_BY = SU.USER_ID"
						+ " AND AASH.ORDER_STATUS IN ("
						+ orderStatus
						+ ")"
						+ " AND AASH.IMPLEMENT_BY = ?";
		sqlArgs.add(userAccount.getUserId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：构造更新当前用户下载的工单的SQL
	 * @return SQLModel
	 */
	public SQLModel getOrderDownloadModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsSamplingHeaderDTO dto = (AmsAssetsSamplingHeaderDTO) dtoParameter;
		String sqlStr = "UPDATE"
						+ " AMS_ASSETS_SAMPLING_HEADER"
						+ " SET"
						+ " ORDER_STATUS = ?,"
						+ " DOWNLOAD_DATE = GETDATE(),"
						+ " DOWNLOAD_BY = ?"
						+ " WHERE"
						+ " HEADER_ID = ?";
		sqlArgs.add(SamplingDicts.ORDER_STS1_DOWNLOADED);
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(dto.getHeaderId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
