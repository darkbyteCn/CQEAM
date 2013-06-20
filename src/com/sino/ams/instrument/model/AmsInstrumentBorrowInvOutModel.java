package com.sino.ams.instrument.model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.instrument.dto.AmsInstrumentRegistrationDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;

public class AmsInstrumentBorrowInvOutModel extends AMSSQLProducer {
	
	private SfUserDTO sfUser = null;

	/**
     * 功能：仪器仪表管理(EAM) ETS_ITEM_INFO  ETS_SYSTEM_ITEM   EAM_YB_BORROW_LOG  数据库SQL构造层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsInstrumentRegistrationDTO 本次操作的数据
     */
	public AmsInstrumentBorrowInvOutModel(SfUserDTO userAccount, AmsInstrumentRegistrationDTO dtoParameter) {
		super(userAccount, dtoParameter);
		sfUser = userAccount;
	}

	public SQLModel getPageQueryModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsInstrumentRegistrationDTO dto = (AmsInstrumentRegistrationDTO)dtoParameter;
        String sqlStr = "SELECT"
			        	+ " EII.BARCODE,"
			        	+ " EII.RESPONSIBILITY_USER,"
			        	+ " EO.WORKORDER_OBJECT_NO,"
			        	+ " ESI.ITEM_CATEGORY2,"
			        	+ " ESI.ITEM_NAME,"
			        	+ " ESI.ITEM_SPEC,"
			        	+ " AME.EMPLOYEE_ID,"
			        	+ " AME.USER_NAME,"
			        	+ " EYBL.BORROW_USER_ID,"
			        	+ " EII.START_DATE,"
			        	+ " EYBL.HANDLE_DATE,"
			        	+ " EYBL.HANDLE_USER_ID,"
			        	+ " EYBL.BORROW_DATE"
        				+ " FROM"
        				+ " ETS_ITEM_INFO EII,"
        				+ " AMS_OBJECT_ADDRESS AOA,"
        				+ " ETS_OBJECT EO,"
						+ " EAM_YB_BORROW_LOG EYBL,"
						+ " AMS_MIS_EMPLOYEE AME,"
						+ " ETS_SYSTEM_ITEM ESI"
        				+ " WHERE"
        				+ " EII.BARCODE = EYBL.BARCODE"
        				+ " AND EII.ADDRESS_ID = AOA.ADDRESS_ID"
        				+ " AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO"
        				+ " AND EII.ITEM_CODE = ESI.ITEM_CODE"
        				+ " AND EII.RESPONSIBILITY_USER = AME.EMPLOYEE_ID"
        				+ " AND EYBL.STATUS = 'YB_BR_STATUS_APPROVE'"
        				+ " AND EYBL.ORG_ID = ?"
        				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.BARCODE LIKE ?)"
        				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_CATEGORY2 LIKE ?)"
        				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_NAME LIKE ?)"
        				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR EYBL.BORROW_USER_ID LIKE ?)"
        				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?)";
        
        sqlArgs.add(userAccount.getOrganizationId());
        sqlArgs.add(dto.getBarcode1());
        sqlArgs.add(dto.getBarcode1());
        sqlArgs.add(dto.getItemCategory2());
        sqlArgs.add(dto.getItemCategory2());
        sqlArgs.add(dto.getItemName());
        sqlArgs.add(dto.getItemName());
//        sqlArgs.add(dto.getBorrowUserId());
//        sqlArgs.add(dto.getBorrowUserId());
        sqlArgs.add(dto.getResponsibilityUser1());
        sqlArgs.add(dto.getResponsibilityUser1());
        sqlArgs.add(dto.getItemSpec());
        sqlArgs.add(dto.getItemSpec());
        
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		
		return sqlModel;
	}
	
	/**
	 * 功能：框架自动生成仪器仪表登记卡记录 EAM_YB_BORROW_LOG 数据插入SQLModel，同时更新ETS_ITEM_INFO 数据，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 * @throws CalendarException 
	 */
	public int updateEamYbBorrowLog(Connection conn, String handleDate, String handleUserId, 
									String borrowDate, String responsibilityUser, 
									String barcode, String workorderObjectNo) 
			throws SQLException, CalendarException {

				String sqlStr = "";
				sqlStr = "BEGIN ? := ETS_INSTRUMENT_CARD_PKG.EII_INSTRUMENT_INV_OUT(?,?,?,?,?,?,?,?); END;";
				
				CallableStatement cst = null;
				
				int success = 0;
				
				try {
					cst = conn.prepareCall(sqlStr);

					cst.registerOutParameter(1, Types.INTEGER);
					cst.setString(2, handleDate);
					cst.setInt(3, StrUtil.strToInt(handleUserId));
					cst.setString(4, borrowDate);
					cst.setInt(5, StrUtil.strToInt(responsibilityUser));
		        	cst.setString(6, barcode);
		        	cst.setInt(7, userAccount.getOrganizationId());
		        	cst.setInt(8, StrUtil.strToInt(workorderObjectNo));
		        	cst.registerOutParameter(9, Types.VARCHAR);
		        	
		        	Logger.logInfo(sqlStr);
		        	cst.execute();
					
					success = cst.getInt(1);
					
					if (success == 0)
						Logger.logError(cst.getString(9));
					
					
				} finally {
					DBManager.closeDBStatement(cst);
				}

				return success;
			}
}
