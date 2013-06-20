package com.sino.ams.dzyh.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.dzyh.constant.LvecMessageKeys;
import com.sino.ams.dzyh.dto.EamDhCheckHeaderDTO;
import com.sino.ams.dzyh.dto.EamDhCheckLineDTO;
import com.sino.ams.dzyh.model.OrderHeaderConfirmModel;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class InstruConfirmDAO extends AMSBaseDAO {
	private OrderLineConfirmDAO lineDAO = null;

	public InstruConfirmDAO(BaseUserDTO userAccount, EamDhCheckHeaderDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		lineDAO = new OrderLineConfirmDAO(userAccount, null, conn);
	}

	/**
	 * 功能：SQL生成器baseSQLProducer的初始化。具体的DAO继承时初始化具体的SQL生成器。
	 *
	 * @param userAccount BaseUserDTO
	 * @param dtoParameter DTO
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		EamDhCheckHeaderDTO dto = (EamDhCheckHeaderDTO)dtoParameter;
		sqlProducer = new OrderHeaderConfirmModel(userAccount, dto);
	}

	/**
	 * 功能：确认设备
	 * @param orderLines DTOSet
	 * @throws DataHandleException
	 */
	public void ConfirmInstrument(DTOSet orderLines) throws DataHandleException{
		boolean operateResult = false;
		boolean autoCommit = true;
		try {
			int itemCount = orderLines.getSize();
			EamDhCheckLineDTO dto = null;
			autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			for (int i = 0; i < itemCount; i++) {
				dto = (EamDhCheckLineDTO)orderLines.getDTO(i);

				lineDAO.setDTOParameter(dto);
				lineDAO.confirmItem();

				if (lineDAO.isLastBarcode()) {//归档工单：头、行、写日志、写历史
					setDTOParameter(dto);//EamDhCheckLineDTO是EamDhCheckHeaderDTO的子类，故直接设置该DTO即可
					archiveOrderHeader();
				}
			}
			operateResult = true;
		} catch (SQLException ex) {
			Logger.logError(ex);
			throw new DataHandleException(ex);
		} catch (QueryException ex) {
			ex.printLog();
			throw new DataHandleException(ex);
		} finally{
			try {
				if (operateResult) {
					conn.commit();
					prodMessage(LvecMessageKeys.INSTR_CONFIRM_SUCCESS);
				} else {
					conn.rollback();
					prodMessage(LvecMessageKeys.INSTR_CONFIRM_FAILURE);
					message.setIsError(true);
				}
				conn.setAutoCommit(autoCommit);
			} catch (SQLException ex) {
				Logger.logError(ex);
				throw new DataHandleException(ex);
			}
		}
	}

	/**
	 * 功能：归档工单头
	 * @throws DataHandleException
	 */
	private void archiveOrderHeader() throws DataHandleException{
		OrderHeaderConfirmModel modelProducer = (OrderHeaderConfirmModel) sqlProducer;
		SQLModel sqlModel = modelProducer.getOrderArchiveModel();
		DBOperator.updateRecord(sqlModel, conn);
	}
}
