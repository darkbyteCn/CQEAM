package com.sino.ams.instrument.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.instrument.dto.AmsInstrumentEamYbMaintainDTO;
import com.sino.ams.instrument.model.AmsInstrumentEamYbMaintainModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.data.Row;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;

public class AmsInstrumentEamYbMaintainDAO extends AMSBaseDAO {

	AmsInstrumentEamYbMaintainModel modelProducer = null;
	
	/**
     * 功能：仪器仪表管理(EAM) ETS_ITEM_INFO  ETS_SYSTEM_ITEM   ETS_OBJECT  EAM_ITEM_DISPOSE 数据访问层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsInstrumentEamYbMaintainDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
	public AmsInstrumentEamYbMaintainDAO(SfUserDTO userAccount,
			AmsInstrumentEamYbMaintainDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		modelProducer = (AmsInstrumentEamYbMaintainModel)sqlProducer;
	}

	/**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		AmsInstrumentEamYbMaintainDTO dtoPara = (AmsInstrumentEamYbMaintainDTO) dtoParameter;
		super.sqlProducer = new AmsInstrumentEamYbMaintainModel((SfUserDTO)userAccount, dtoPara);
	}

	/**
	 * 功能：更新仪器仪表维修-送修记录(EAM)表"EAM_YB_MAINTAIN"数据。
	 * @throws CalendarException 
	 * 
	 */
	public void updateBorrowLogData(String[] barcodes) throws DataHandleException {
		boolean hasInv = false;
		boolean autoCommit = true;
		boolean operateResult = false;
		int succeedRecord = 0;
		CallableStatement cst = null;
		try {
			AmsInstrumentEamYbMaintainModel modelProducer = (AmsInstrumentEamYbMaintainModel)sqlProducer;
			SQLModel sqlModel = modelProducer.getInsSendRepairInvModel();
			SimpleQuery simp = new SimpleQuery(sqlModel, conn);
			simp.executeQuery();
			hasInv = simp.hasResult();
			
			if(hasInv){//有仪器仪表送修库
				Row row = simp.getFirstRow();
				String workorderObjectNo = row.getStrValue("WORKORDER_OBJECT_NO");
				
				autoCommit = conn.getAutoCommit();
		        conn.setAutoCommit(false);
		        
		        String sqlStr = "BEGIN ? := ETS_INSTRUMENT_CARD_PKG.EII_INSTRUMENT_REPAIR(?,?,?); END;";
		        cst = conn.prepareCall(sqlStr);

		        cst.registerOutParameter(1, Types.INTEGER);
				cst.setInt(3, StrUtil.strToInt(workorderObjectNo));
				cst.registerOutParameter(4, Types.VARCHAR);
				int barcodeCount = barcodes.length;
				
				for(int i = 0; i < barcodeCount; i++){
					cst.setString(2, barcodes[i]);
					cst.execute();
					succeedRecord += cst.getInt(1);
		        }
				operateResult = (succeedRecord == barcodeCount);
			}
		} catch (SQLException ex) {
			Logger.logError(ex);
			throw new DataHandleException(ex);
		} catch (QueryException ex) {
			Logger.logError(ex);
			throw new DataHandleException(ex);
		} catch (ContainerException ex) {
			Logger.logError(ex);
			throw new DataHandleException(ex);
		} finally{
			try {
				if(hasInv){
					if (operateResult) {
						conn.commit();
						prodMessage("INS_REPAIR_SUCCESS");
					  } else {
						conn.rollback();
						prodMessage("INS_REPAIR_FAILURE");
						message.setIsError(true);
					}
				} else {
					prodMessage("INS_REPAIR_INV_TYPE_NOT_EXIST");
					message.setIsError(true);
				}
				conn.setAutoCommit(autoCommit);
				DBManager.closeDBStatement(cst);
			} catch (SQLException ex) {
				Logger.logError(ex);
				throw new DataHandleException(ex);
			}
		}
	}
}
