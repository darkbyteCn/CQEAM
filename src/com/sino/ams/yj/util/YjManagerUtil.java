package com.sino.ams.yj.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.QueryException;
import com.sino.base.web.DatabaseForWeb;
import java.util.ArrayList;
import java.util.List;

public class YjManagerUtil {
	/**
     * 应急管理工具类
     * 2011-11-15
     * wangzp
	 */
	protected Connection conn = null;
	protected SfUserDTO userAccount = null;

	public YjManagerUtil(SfUserDTO userAccount, Connection conn) {
		this.userAccount = userAccount;
		this.conn = conn;
	}
    /**
     * 取当前应急类型的最大编号
     * @return
     */
	public int getYjManagerMax(String yj_type) {
    	int isSyn= 0;
        CallableStatement cStmt = null;
        String sqlStr = "{call dbo.YJ_MANAGE_GET_MAX(?,?)}";
        try {
			cStmt = conn.prepareCall(sqlStr);
			cStmt.setString(1,yj_type);
			cStmt.registerOutParameter(2,java.sql.Types.INTEGER);
            cStmt.execute();
            isSyn= cStmt.getInt(2);
            System.out.println("seq=="+isSyn);
		} catch (SQLException e) {
			e.printStackTrace();
			isSyn= -1;
		} finally {
            DBManager.closeDBStatement(cStmt);
        }
        return isSyn;
    }
	
	
	
	
}
