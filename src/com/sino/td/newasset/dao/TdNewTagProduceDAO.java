package com.sino.td.newasset.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.dto.AmsMisTagChgDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.td.newasset.model.TdNewTagProduceModel;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class TdNewTagProduceDAO extends AMSBaseDAO {

	public TdNewTagProduceDAO(SfUserDTO userAccount, AmsMisTagChgDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器baseSQLProducer的初始化。具体的DAO继承时初始化具体的SQL生成器。
	 * @param userAccount BaseUserDTO
	 * @param dtoParameter DTO
	 * @todo Implement this com.sino.framework.dao.BaseDAO method
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		SfUserDTO user = (SfUserDTO)userAccount;
		AmsMisTagChgDTO dto = (AmsMisTagChgDTO) dtoParameter;
		sqlProducer = new TdNewTagProduceModel(user, dto);
	}

	public void produceNewTagNumber(DTOSet dtos) throws DataHandleException {
		boolean operateResult = false;
		boolean autoCommit = true;
		DataHandleException error = null;
		CallableStatement cst = null;
		try {
			int dataCount = dtos.getSize();
			AmsMisTagChgDTO dto = null;
			autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			String sqlStr = "BEGIN ? := AMS_BARCODE_PKG.GETASSETBARCODE(?); END;";
			cst = conn.prepareCall(sqlStr);
			SQLModel sqlModel = null;
			TdNewTagProduceModel modelProducer = (TdNewTagProduceModel)sqlProducer;
			for (int i = 0; i < dataCount; i++) {
				dto = (AmsMisTagChgDTO) dtos.getDTO(i);
				setDTOParameter(dto);
				int index = 1;
				cst.registerOutParameter(index++, Types.VARCHAR);
				cst.setString(index++, userAccount.getCompanyCode());
				cst.execute();
				dto.setTagNumberTo(cst.getString(1));
				setDTOParameter(dto);
				createData();
				sqlModel = modelProducer.getTransLineUpdateModel();
				DBOperator.updateRecord(sqlModel, conn);
				dtos.set(i, dto);
			}
			operateResult = true;
		} catch (SQLException ex) {
			Logger.logError(ex);
			error = new DataHandleException(ex);
		} catch (DTOException ex) {
			Logger.logError(ex);
			error = new DataHandleException(ex);
		} finally{
			try {
				if (operateResult) {
					conn.commit();
				} else {
					conn.rollback();
				}
				conn.setAutoCommit(autoCommit);
				if(!operateResult){
					throw error;
				}
				DBManager.closeDBStatement(cst);
			} catch (SQLException ex) {
				Logger.logError(ex);
				throw new DataHandleException(ex);
			}
		}
	}
}
