package com.sino.ams.workorder.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
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
public class CompItemReportModel extends AMSSQLProducer {

	public CompItemReportModel(SfUserDTO userAccount, EtsWorkorderDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：获取设备巡检结果报表
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getPageQueryModel() throws SQLModelException{
		SQLModel sqlModel = new SQLModel();
		try {
			String sqlStr = "SELECT"
							+ " TMPV_1.ORGANIZATION_ID,"
							+ " TMPV_1.COMPANY,"
							+ " TMPV_1.ITEM_CATEGORY,"
							+ " TMPV_1.ITEM_CATEGORY_NAME,"
							+ " TMPV_1.TOTAL_COUNT,"
							+ " dbo.NVL(TMPV_2.SCAN_COUNT, 0) SCAN_COUNT,"
							+ " TMPV_1.TOTAL_COUNT - dbo.NVL(TMPV_2.SCAN_COUNT, 0) NOT_SCAN_COUNT,"
//							+ " ROUND(100 * dbo.NVL(TMPV_2.SCAN_COUNT, 0) / TMPV_1.TOTAL_COUNT, 2)||'%' SCAN_RATE"
                            + " (DECODE(TRUNC(100 * dbo.NVL(TMPV_2.SCAN_COUNT, 0) / TMPV_1.TOTAL_COUNT), \n"
                            + "                                    0, \n"
                            + "                                    STR_REPLACE(ROUND(100 * dbo.NVL(TMPV_2.SCAN_COUNT, 0)/TMPV_1.TOTAL_COUNT, 2), \n"
                            + "                                            '.', \n" 
                            + "                                            '0.'), \n"
                            + "                                    STR(ROUND(100 *dbo.NVL(TMPV_2.SCAN_COUNT, 0)/TMPV_1.TOTAL_COUNT, 2))) || '%') SCAN_RATE\n"
							+ " FROM"
							+ " (SELECT"
							+ " EOCM.ORGANIZATION_ID,"
							+ " EOCM.COMPANY,"
							+ " ESI.ITEM_CATEGORY,"
							+ " AMS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY_NAME,"
							+ " COUNT(EII.SYSTEMID) TOTAL_COUNT"
							+ " FROM"
							+ " ETS_ITEM_INFO   EII,"
							+ " ETS_SYSTEM_ITEM ESI,"
							+ " ETS_OU_CITY_MAP EOCM"
							+ " WHERE"
							+ " EII.ITEM_CODE = ESI.ITEM_CODE"
							+ " AND EII.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
							+ " AND EOCM.ORGANIZATION_ID = ISNULL(?, EOCM.ORGANIZATION_ID)"
							+ " GROUP BY"
							+ " EOCM.ORGANIZATION_ID,"
							+ " EOCM.COMPANY,"
							+ " ESI.ITEM_CATEGORY) TMPV_1,"
							+ " (SELECT"
							+ " EOCM.ORGANIZATION_ID,"
							+ " EOCM.COMPANY,"
							+ " ESI.ITEM_CATEGORY,"
							+ " AMS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY_NAME,"
							+ " COUNT(EII.SYSTEMID) SCAN_COUNT"
							+ " FROM"
							+ " ETS_ITEM_INFO   EII,"
							+ " ETS_SYSTEM_ITEM ESI,"
							+ " ETS_OU_CITY_MAP EOCM"
							+ " WHERE"
							+ " EII.ITEM_CODE = ESI.ITEM_CODE"
							+ " AND EII.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
							+ " AND EOCM.ORGANIZATION_ID = ISNULL(?, EOCM.ORGANIZATION_ID)"
							+ " AND EXISTS ("
							+ " SELECT"
							+ " NULL"
							+ " FROM"
							+ " ETS_WORKORDER_DTL EWD,"
							+ " ETS_WORKORDER     EW"
							+ " WHERE"
							+ " EII.BARCODE = EWD.BARCODE"
							+ " AND EWD.WORKORDER_NO = EW.WORKORDER_NO"
							+ " AND EW.WORKORDER_TYPE = ?"
							+ " AND (EW.WORKORDER_FLAG = ? OR EW.WORKORDER_FLAG = ?)"
							+ " AND ((EW.UPLOAD_DATE >= dbo.NVL(?, EW.UPLOAD_DATE)"
							+ " AND EW.UPLOAD_DATE <= dbo.NVL(?, EW.UPLOAD_DATE))"
							+ " OR (EW.CHECKOVER_DATE >= dbo.NVL(?, EW.CHECKOVER_DATE)"
							+ " AND EW.CHECKOVER_DATE <= dbo.NVL(?, EW.CHECKOVER_DATE)))"
							+ " AND EW.WORKORDER_NO = ("
							+ " SELECT"
							+ " MAX(EW2.WORKORDER_NO)"
							+ " FROM"
							+ " ETS_WORKORDER     EW2"
							+ " WHERE"
							+ " EW2.WORKORDER_TYPE = ?"
							+ " AND (EW2.WORKORDER_FLAG = ? OR EW2.WORKORDER_FLAG = ?)"
							+ " AND ((EW2.UPLOAD_DATE >= dbo.NVL(?, EW2.UPLOAD_DATE)"
							+ " AND EW2.UPLOAD_DATE <= dbo.NVL(?, EW2.UPLOAD_DATE))"
							+ " OR (EW2.CHECKOVER_DATE >= dbo.NVL(?, EW2.CHECKOVER_DATE)"
							+ " AND EW2.CHECKOVER_DATE <= dbo.NVL(?, EW2.CHECKOVER_DATE)))"
							+ " AND EW2.ORGANIZATION_ID = EW.ORGANIZATION_ID))"
							+ " GROUP BY"
							+ " EOCM.ORGANIZATION_ID,"
							+ " EOCM.COMPANY,"
							+ " ESI.ITEM_CATEGORY) TMPV_2"
							+ " WHERE"
							+ " TMPV_1.ORGANIZATION_ID *= TMPV_2.ORGANIZATION_ID"
							+ " AND TMPV_1.ITEM_CATEGORY *= TMPV_2.ITEM_CATEGORY"
                            + " AND  " + SyBaseSQLUtil.isNotNull("TMPV_1.ITEM_CATEGORY_NAME") + " ";
			List sqlArgs = new ArrayList();
			EtsWorkorderDTO dto = (EtsWorkorderDTO) dtoParameter;
			sqlArgs.add(dto.getOrganizationId());
			sqlArgs.add(dto.getOrganizationId());
			sqlArgs.add(DictConstant.ORDER_TYPE_CHECK);
			sqlArgs.add(DictConstant.WOR_STATUS_UPLOAD);
			sqlArgs.add(DictConstant.WOR_STATUS_ARCHIVED);
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(DictConstant.ORDER_TYPE_CHECK);
			sqlArgs.add(DictConstant.WOR_STATUS_UPLOAD);
			sqlArgs.add(DictConstant.WOR_STATUS_ARCHIVED);
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
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
