package com.sino.ams.newasset.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.model.AmsDeptMatchModel;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2009-8-25
 * Time: 15:20:42
 * To change this template use File | Settings | File Templates.
 */
public class AmsDeptMatchDAO extends AMSBaseDAO {
    private int matchedItemCount = 0;   //本次匹配成功的数量

    public AmsDeptMatchDAO(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        super.sqlProducer = new AmsDeptMatchModel(userAccount, dtoParameter);
    }


    public boolean saveMatch(String deptCode, String countyCode, String companyCode) throws SQLException {
        boolean success = false;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            insertCostDeptMatch(deptCode, countyCode, companyCode);
            conn.commit();
            success = true;
        } catch (DataHandleException ex) {
			ex.printLog();
		} catch (SQLException e) {
            Logger.logError(e);
            conn.rollback();
            prodMessage("SAVE_FAILURE");
        } finally {
            conn.setAutoCommit(autoCommit);
        }
        return success;
    }

    private void insertCostDeptMatch(String deptCode, String countyCode, String companyCode) throws DataHandleException {
		AmsDeptMatchModel modelProducer = (AmsDeptMatchModel) sqlProducer;
		SQLModel sqlModel = modelProducer.insertCostDeptMatch(deptCode, countyCode, companyCode);
		DBOperator.updateRecord(sqlModel, conn);
	}
}
