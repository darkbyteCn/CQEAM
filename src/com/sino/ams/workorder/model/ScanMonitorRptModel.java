package com.sino.ams.workorder.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dto.EtsWorkorderDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 中国移动资产实物管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class ScanMonitorRptModel extends AMSSQLProducer {

	public ScanMonitorRptModel(SfUserDTO userAccount, EtsWorkorderDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：返回页面翻页查询时所需要的SQLModel
	 * <B>默认为空实现。可由具体应用选择是否需要实现</B>
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getPageQueryModel() throws SQLModelException{
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			String sqlStr = "SELECT"
							+ " AMC.COMPANY_ID,"
							+ " AMC.NAME MAINTAIN_COMPANY_NAME,"
							+ " dbo.NVL(T_RES.RESPONSIBILITY_COUNT, 0) RESPONSIBILITY_COUNT,"
							+ " dbo.NVL(S_RES.SCAN_OVER_COUNT, 0) SCAN_OVER_COUNT,"
							+ " dbo.NVL(T_RES.RESPONSIBILITY_COUNT, 0) - dbo.NVL(S_RES.SCAN_OVER_COUNT, 0) NOT_SCAN_COUNT,"
							+ " ROUND((dbo.NVL(S_RES.SCAN_OVER_COUNT, 0) / dbo.NVL(T_RES.RESPONSIBILITY_COUNT, 1)) * 100, 2) || '%' SCAN_OVER_RATE"
							+ " FROM"
							+ " AMS_MAINTAIN_COMPANY AMC,"
							+ " (SELECT"
							+ " AMR.COMPANY_ID,"
							+ " COUNT(1) RESPONSIBILITY_COUNT"
							+ " FROM"
							+ " AMS_MAINTAIN_RESPONSIBILITY AMR,"
							+ " ETS_OBJECT                  EO"
							+ " WHERE"
							+ " AMR.OBJECT_NO = EO.WORKORDER_OBJECT_NO"
							+ " GROUP BY"
							+ " AMR.COMPANY_ID) T_RES,"
							+ " (SELECT"
							+ " AMR.COMPANY_ID,"
							+ " COUNT(1) SCAN_OVER_COUNT"
							+ " FROM"
							+ " AMS_MAINTAIN_RESPONSIBILITY AMR,"
							+ " ETS_OBJECT                  EO"
							+ " WHERE"
							+ " AMR.OBJECT_NO = EO.WORKORDER_OBJECT_NO"
							+ " AND EXISTS"
							+ " (SELECT"
							+ " NULL"
							+ " FROM"
							+ " ETS_WORKORDER EW"
							+ " WHERE"
							+ " EO.WORKORDER_OBJECT_NO = EW.WORKORDER_OBJECT_NO"
							+ " AND EW.WORKORDER_TYPE = ?"
							+ " AND (EW.WORKORDER_FLAG = ? OR EW.WORKORDER_FLAG = ?)"
							+ " AND ((EW.UPLOAD_DATE >= dbo.NVL(?, EW.UPLOAD_DATE)"
							+ " AND EW.UPLOAD_DATE <= dbo.NVL(?, EW.UPLOAD_DATE))"
							+ " OR (EW.CHECKOVER_DATE >= dbo.NVL(?, EW.CHECKOVER_DATE)"
							+ " AND EW.CHECKOVER_DATE <= dbo.NVL(?, EW.CHECKOVER_DATE))))"
							+ " GROUP BY"
							+ " AMR.COMPANY_ID) S_RES"
							+ " WHERE"
							+ " AMC.COMPANY_ID *= T_RES.COMPANY_ID"
							+ " AND T_RES.COMPANY_ID *= S_RES.COMPANY_ID"
							+ " AND AMC.COMPANY_ID = dbo.NVL(?, AMC.COMPANY_ID)"
							+ " AND AMC.ORGANIZATION_ID = ?";
			EtsWorkorderDTO dto = (EtsWorkorderDTO) dtoParameter;
			sqlArgs.add(DictConstant.ORDER_TYPE_CHECK);
			sqlArgs.add(DictConstant.WORKORDER_STATUS_UPLOADED);
			sqlArgs.add(DictConstant.WOR_STATUS_ARCHIVED);
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getMaintainCompany());
			sqlArgs.add(userAccount.getOrganizationId());
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}
}
