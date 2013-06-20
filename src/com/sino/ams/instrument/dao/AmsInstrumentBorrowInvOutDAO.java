package com.sino.ams.instrument.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.instrument.dto.AmsInstrumentRegistrationDTO;
import com.sino.ams.instrument.model.AmsInstrumentBorrowInvOutModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.dto.DTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;

public class AmsInstrumentBorrowInvOutDAO extends AMSBaseDAO {

	AmsInstrumentBorrowInvOutModel modelProducer = null;
	
	/**
     * 功能：仪器仪表管理(EAM) ETS_ITEM_INFO  ETS_SYSTEM_ITEM   ETS_OBJECT  EAM_ITEM_DISPOSE 数据访问层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsInstrumentRegistrationDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
	public AmsInstrumentBorrowInvOutDAO(SfUserDTO userAccount,
			AmsInstrumentRegistrationDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		modelProducer = (AmsInstrumentBorrowInvOutModel)sqlProducer;
	}

	/**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		AmsInstrumentRegistrationDTO dtoPara = (AmsInstrumentRegistrationDTO) dtoParameter;
		super.sqlProducer = new AmsInstrumentBorrowInvOutModel((SfUserDTO) userAccount, dtoPara);
	}

	/**
	 * 功能：插入仪器仪表借用-实物出库记录(EAM)表"EAM_YB_BORROW_LOG"数据。
	 * @throws CalendarException 
	 * 
	 */
	public void updateBorrowLogData(Connection conn, String barcode, String handleUserId, 
			String handleDate, String responsibilityUser, 
			String borrowDate, String workorderObjectNo) throws DataHandleException, CalendarException {
		
//		System.out.println("开始打印参数列表");
//		System.out.println(barcode);
//		System.out.println(handleUserId);
//		System.out.println(handleDate);
//		System.out.println(responsibilityUser);
//		System.out.println(borrowDate);
//		System.out.println(workorderObjectNo);
		
		boolean autoCommit = true;
		DataHandleException error = null;
		boolean operateResult = false;

		try {
			autoCommit = conn.getAutoCommit();
	        conn.setAutoCommit(false);
	        int success = 0;

	        success = modelProducer.updateEamYbBorrowLog(conn, handleDate, handleUserId, borrowDate, responsibilityUser, barcode, workorderObjectNo);
	        	
        	if(success > 0) {
        		operateResult = true;
        	}
		} catch (SQLException ex) {
			Logger.logError(ex);
			error = new DataHandleException(ex);
		} finally{
			try {
				if (operateResult) {
					conn.commit();
					prodMessage("INSTRUMENT_INV_OUT_SUCCESS");
				} else {
					conn.rollback();
					prodMessage("INSTRUMENT_INV_OUT_FAILURE");
					message.setIsError(true);
				}
				conn.setAutoCommit(autoCommit);
				if(!operateResult){
					throw error;
				}
			} catch (SQLException ex) {
				Logger.logError(ex);
				throw new DataHandleException(ex);
			}
		}
	}
}
