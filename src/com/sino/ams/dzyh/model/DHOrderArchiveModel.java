package com.sino.ams.dzyh.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.dzyh.constant.LvecDicts;
import com.sino.ams.dzyh.dto.EamDhCheckHeaderDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class DHOrderArchiveModel extends AMSSQLProducer {

	public DHOrderArchiveModel(BaseUserDTO userAccount, EamDhCheckHeaderDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：返回页面翻页查询时所需要的SQLModel
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getPageQueryModel() throws SQLModelException{
		SQLModel sqlModel = new SQLModel();
		try {
			EamDhCheckHeaderDTO dto = (EamDhCheckHeaderDTO) dtoParameter;
			List sqlArgs = new ArrayList();
			String sqlStr = "SELECT"
							+ " ECT.TASK_NAME,"
							+ " EDCB.BATCH_NO,"
							+ " EDCH.HEADER_ID,"
							+ " EDCH.ORDER_NO,"
							+ " EO.WORKORDER_OBJECT_NAME LOCATION_NAME,"
							+ " EDCH.ORDER_STATUS,"
							+ " AMS_PUB_PKG.GET_FLEX_VALUE(EDCH.ORDER_STATUS, 'CHKORDER_STATUS') ORDER_STATUS_VALUE,"
							+ " EDCH.ORDER_TYPE,"
							+ " AMS_PUB_PKG.GET_FLEX_VALUE(EDCH.ORDER_TYPE, 'ORDER_TYPE_ASSETS') ORDER_TYPE_VALUE,"
							+ " AMS_PUB_PKG.GET_FLEX_VALUE(EDCH.CHECK_TOOLS, 'CHECK_TOOLS') CHECK_TOOLS_VALUE,"
							+ " EDCH.DISTRIBUTE_DATE,"
							+ " AMS_PUB_PKG.GET_USER_NAME(EDCH.DISTRIBUTE_BY) DISTRIBUTE_USER,"
							+ " EDCH.START_TIME,"
							+ " EDCH.IMPLEMENT_DAYS,"
							+ " SU.USERNAME IMPLEMENT_USER,"
							+ " EDCH.DOWNLOAD_DATE,"
							+ " AMS_PUB_PKG.GET_USER_NAME(EDCH.DOWNLOAD_BY) DOWNLOAD_USER,"
							+ " EDCH.SCANOVER_DATE,"
							+ " AMS_PUB_PKG.GET_USER_NAME(EDCH.SCANOVER_BY) SCANOVER_USER,"
							+ " EDCH.UPLOAD_DATE,"
							+ " AMS_PUB_PKG.GET_USER_NAME(EDCH.UPLOAD_BY) UPLOAD_USER,"
							+ " EDCH.ARCHIVED_DATE,"
							+ " AMS_PUB_PKG.GET_USER_NAME(EDCH.ARCHIVED_BY) ARCHIVED_USER"
							+ " FROM"
							+ " ETS_OBJECT          EO,"
							+ " SF_USER             SU,"
							+ " EAM_CHECK_TASK      ECT,"
							+ " EAM_DH_CHECK_BATCH  EDCB,"
							+ " EAM_DH_CHECK_HEADER EDCH"
							+ " WHERE"
							+ " ECT.CHECK_TASK_ID = EDCB.CHECK_TASK_ID"
							+ " AND EDCB.BATCH_ID = EDCH.BATCH_ID"
							+ " AND EDCH.CHECK_LOCATION = EO.WORKORDER_OBJECT_NO"
							+ " AND EDCH.IMPLEMENT_BY = SU.USER_ID"
							+ " AND EDCH.ORDER_NO LIKE dbo.NVL(?, EDCH.ORDER_NO)"
							+ " AND EO.WORKORDER_OBJECT_NAME LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_NAME)"
							+ " AND SU.USERNAME LIKE dbo.NVL(?, SU.USERNAME)"
							+ " AND EDCH.ORDER_STATUS = ?"
							+ " AND EDCH.DISTRIBUTE_DATE >= dbo.NVL(?, EDCH.DISTRIBUTE_DATE)"
							+ " AND EDCH.DISTRIBUTE_DATE <= dbo.NVL(?, EDCH.DISTRIBUTE_DATE)"
							+ " AND EDCH.UPLOAD_DATE >= dbo.NVL(?, EDCH.UPLOAD_DATE)"
							+ " AND EDCH.UPLOAD_DATE <= dbo.NVL(?, EDCH.UPLOAD_DATE)"
							+ " AND ECT.TASK_NAME LIKE dbo.NVL(?, ECT.TASK_NAME)";

			sqlArgs.add(dto.getOrderNo());
			sqlArgs.add(dto.getLocationName());
			sqlArgs.add(dto.getImplementUser());
			sqlArgs.add(LvecDicts.ORDER_STS1_UPLOADED);
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getFromDate());
			sqlArgs.add(dto.getSQLToDate());
			sqlArgs.add(dto.getTaskName());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}
}
