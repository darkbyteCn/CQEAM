package com.sino.nm.spare2.dao;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.nm.spare2.dto.AmsItemTransHDTO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.base.dto.DTO;
import com.sino.base.db.conn.DBManager;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.SQLException;

/**
 * <p>Title: SinoEAMS</p>
 * <p>Description: 备件子库转移</p>
 * <p>Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2007 - 2008</p>
 * <p>Company: 北京思诺搏信息技术有限公司</p>
 * @author 何睿
 * @version 0.1
 *          Date: 2008-8-15
 */
public class BjInvTransDAO extends AMSBaseDAO {
    private AmsItemTransHDTO amsItemTransH = null;
    public BjInvTransDAO(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        this.amsItemTransH = (AmsItemTransHDTO) super.dtoParameter;
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {

    }

    /**
     * 确认
     * @return success
     * @throws SQLException
     */
    public boolean confirm() throws SQLException {
        boolean success = false;
        CallableStatement cStmt = null;
        String sqlStr = "{call dbo.AMS_INV_TRANS2_SUB_INV_TRANS_CONFIRM(?,?)}";
        try {
            cStmt = conn.prepareCall(sqlStr);
            cStmt.setString(1, amsItemTransH.getTransId());
            cStmt.setInt(2, userAccount.getUserId());
            cStmt.execute();
            success = true;
        } finally {
            DBManager.closeDBStatement(cStmt);
        }
        return success;
    }
}
