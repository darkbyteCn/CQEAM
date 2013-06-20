package com.sino.ams.spare.dao;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DataHandleException;

import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.ams.spare.dto.AmsItemTransDDTO;
import com.sino.ams.spare.model.AmsItemTransDModel;
import com.sino.ams.system.user.dto.SfUserDTO;


/**
 * <p>Title: AmsItemTransDDAO</p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Herui
 * @version 1.0
 */


public class AmsItemTransDDAO extends BaseDAO {

	private SfUserDTO sfUser = null;

	/**
	 * 功能：备件业务明细表(AMS) AMS_ITEM_TRANS_D 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsItemTransDDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public AmsItemTransDDAO(SfUserDTO userAccount, AmsItemTransDDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		sfUser = userAccount;
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) { 
		AmsItemTransDDTO dtoPara = (AmsItemTransDDTO)dtoParameter;
		super.sqlProducer = new AmsItemTransDModel((SfUserDTO)userAccount, dtoPara);
	}

	/**
	 * 功能：插入备件业务明细表(AMS)表“AMS_ITEM_TRANS_D”数据。
	 */
	public void createData() throws DataHandleException {
		super.createData();
		getMessage().addParameterValue("备件业务明细表(AMS)");
	}

	/**
	 * 功能：更新备件业务明细表(AMS)表“AMS_ITEM_TRANS_D”数据。
	 */
	public void updateData() throws DataHandleException {
		super.updateData();
		getMessage().addParameterValue("备件业务明细表(AMS)");
	}

	/**
	 * 功能：删除备件业务明细表(AMS)表“AMS_ITEM_TRANS_D”数据。
	 */
	public void deleteData() throws DataHandleException {
		super.deleteData();
		getMessage().addParameterValue("备件业务明细表(AMS)");
	}

    /**
     * 保存明细
     * @param lines  DTOSet
     * @throws SQLException
     */
    public void writeDetails(DTOSet lines) throws SQLException {
            CallableStatement cStmt = null;
            String sqlStr = "{call AMS_ITEM_TRANS.SAVE_DETAIL(?,?,?,?,?,?,?,?)}";
            try {
                cStmt = conn.prepareCall(sqlStr);
                for (int i = 0; i < lines.getSize(); i++) {
                    AmsItemTransDDTO dto = (AmsItemTransDDTO) lines.getDTO(i);
                    cStmt.setString(1,((AmsItemTransDDTO)dtoParameter).getTransId());
                    cStmt.setString(2,((AmsItemTransDDTO)dtoParameter).getLineId());
                    cStmt.setString(3,dto.getDetailId());
                    cStmt.setInt(4,dto.getOrganizationId());
                    cStmt.setString(5,((AmsItemTransDDTO)dtoParameter).getItemCode());
                    cStmt.setInt(6,dto.getQuantity());
                    cStmt.setString(7,dto.getConfirmQuantity());
                    cStmt.setString(8,dto.getCurOnhandQty());
                    cStmt.execute();
                }
                prodMessage("UPDATE_DATA_SUCCESS");
            } finally {
                DBManager.closeDBStatement(cStmt);
            }
        }

}