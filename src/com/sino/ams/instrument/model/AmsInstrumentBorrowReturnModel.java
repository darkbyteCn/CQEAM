package com.sino.ams.instrument.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.instrument.dto.AmsInstrumentRegistrationDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

public class AmsInstrumentBorrowReturnModel extends AMSSQLProducer {

	/**
	 * 功能：仪器仪表管理(EAM) ETS_ITEM_INFO  ETS_SYSTEM_ITEM   EAM_YB_BORROW_LOG  数据库SQL构造层构造函数
	 * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsInstrumentRegistrationDTO 本次操作的数据
	 */

	public AmsInstrumentBorrowReturnModel(SfUserDTO userAccount,
			AmsInstrumentRegistrationDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			AmsInstrumentRegistrationDTO dto = (AmsInstrumentRegistrationDTO) dtoParameter;
			String sqlStr = "SELECT" + " EII.BARCODE,"
					+ " EII.RESPONSIBILITY_USER," + " ESI.ITEM_CATEGORY2,"
					+ " ESI.ITEM_NAME," + " ESI.ITEM_SPEC,"
					+ " AME.EMPLOYEE_ID," + " AME.USER_NAME,"
					+ " EYBL.BORROW_USER_ID," + " EII.START_DATE,"
					+ " EYBL.BORROW_DATE," + " EYBL.RETURN_DATE_PLAN" + " FROM"
					+ " ETS_ITEM_INFO EII," + " EAM_YB_BORROW_LOG EYBL,"
					+ " AMS_MIS_EMPLOYEE AME," + " ETS_SYSTEM_ITEM ESI"
					+ " WHERE" 
					+ " EII.BARCODE = EYBL.BARCODE"
					+ " AND EII.ITEM_CODE = ESI.ITEM_CODE"
					+ " AND EII.RESPONSIBILITY_USER = AME.EMPLOYEE_ID"
					+ " AND EYBL.STATUS = 'YB_BR_STATUS_BORROW'"
					+ " AND EYBL.ORG_ID = ?"
					+ " AND EYBL.BARCODE LIKE dbo.NVL(?, EYBL.BARCODE)"
					+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_CATEGORY2 LIKE ?)"
					+ " AND ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME)"
					+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.RESPONSIBILITY_USER LIKE ?)"
					+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?)"
					+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR EYBL.BORROW_DATE LIKE ?)";

			sqlArgs.add(userAccount.getOrganizationId());
			sqlArgs.add(dto.getBarcode());
			sqlArgs.add(dto.getItemCategory2());
			sqlArgs.add(dto.getItemCategory2());
			sqlArgs.add(dto.getItemName());
			sqlArgs.add(dto.getResponsibilityUser1());
			sqlArgs.add(dto.getResponsibilityUser1());
			sqlArgs.add(dto.getItemSpec());
			sqlArgs.add(dto.getItemSpec());
			sqlArgs.add(dto.getBorrowDate1());
			sqlArgs.add(dto.getBorrowDate1());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);

		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}

		return sqlModel;
	}

	//得到正常的仪器仪表库
	public SQLModel getInsNorInvModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
			+ " EO.WORKORDER_OBJECT_NO"
			+ " FROM"
			+ " ETS_OBJECT EO"
			+ " WHERE"
			+ " EO.ORGANIZATION_ID = ?"
			+ " AND EO.OBJECT_CATEGORY = ?"
			+ " AND EO.BUSINESS_CATEGORY = ?";
		sqlArgs.add(userAccount.getOrganizationId());
		sqlArgs.add(DictConstant.INV_NORMAL);
		sqlArgs.add("INV_BIZ_INSTRU");
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
