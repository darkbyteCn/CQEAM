package com.sino.nm.ams.newasset.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.bean.OrderNumGenerator;
import com.sino.ams.newasset.dto.AssetsAddLDTO;
import com.sino.nm.ams.newasset.dto.InformationMaterialDeleteDTO;
import com.sino.nm.ams.newasset.dto.InformationMaterialManageDTO;
import com.sino.nm.ams.newasset.model.InformationMaterialManageModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.db.DBActionConstant;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.util.CalendarUtil;
import com.sino.framework.dto.BaseUserDTO;

public class InformationMaterialManageDAO extends AMSBaseDAO {

	public InformationMaterialManageDAO(BaseUserDTO userAccount,
			DTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		SfUserDTO user = (SfUserDTO)userAccount;
		InformationMaterialManageDTO dto = (InformationMaterialManageDTO)dtoParameter;
		this.sqlProducer = new InformationMaterialManageModel(user, dto); 
	}
	
	public boolean deleteData(DTOSet lineSet) throws SQLException {
		boolean success = false;
		boolean autoCommit = true;
		
		try {
			autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			deleteLines(lineSet);
			success = true;
		} catch (SQLException e) {
			Logger.logError(e);
		} catch (DataHandleException e) {
			e.printLog();
		} finally {
			if(success){
				prodMessage("SPARE_SAVE_SUCCESS");
				conn.commit();
			} else {
				prodMessage("SPARE_SAVE_FAILURE");
				conn.rollback();
			}
			conn.setAutoCommit(autoCommit);
		}
		return success;
		
	}
	
	public void deleteLines(DTOSet lineSet) throws DataHandleException {
		if (lineSet != null && !lineSet.isEmpty()) {
			InformationMaterialManageDAO lineDAO = new InformationMaterialManageDAO(userAccount, null, conn);
			for (int i = 0; i < lineSet.getSize(); i++) {
				InformationMaterialDeleteDTO lineData = (InformationMaterialDeleteDTO) lineSet.getDTO(i);
				lineDAO.setDTOParameter(lineData);
				lineDAO.deleteData();
			}
		}
	}

	public boolean submitData(DTOSet lineSet) throws SQLException {
		boolean success = false;
		boolean autoCommit = true;
		
		try {
			autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			//CalendarUtil.getCurrDate()
			saveLines(lineSet);
			success = true;
		} catch (SQLException e) {
			Logger.logError(e);
		} catch (DataHandleException e) {
			e.printLog();
		} finally {
			if(success){
				prodMessage("SPARE_SAVE_SUCCESS");
				conn.commit();
			} else {
				prodMessage("SPARE_SAVE_FAILURE");
				conn.rollback();
			}
			conn.setAutoCommit(autoCommit);
		}
		return success;
	}
	
	
	public void saveLines(DTOSet lineSet) throws DataHandleException {
		if (lineSet != null && !lineSet.isEmpty()) {
			InformationMaterialManageDAO lineDAO = new InformationMaterialManageDAO(userAccount, null, conn);
			for (int i = 0; i < lineSet.getSize(); i++) {
				InformationMaterialManageDTO lineData = (InformationMaterialManageDTO) lineSet.getDTO(i);
				lineDAO.setDTOParameter(lineData);
				lineDAO.createData();
			}
		}
	}
	
	public boolean update() throws SQLException {
		boolean success = false;
		
		try {
			this.updateData();
			success = true;
		}  catch (DataHandleException e) {
			e.printLog();
		} 
		
		return success;
	}

}
