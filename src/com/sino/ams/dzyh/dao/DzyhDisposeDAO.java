package com.sino.ams.dzyh.dao;


import java.sql.Connection;
import java.sql.SQLException;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.dzyh.dto.EamItemDisposeDTO;
import com.sino.ams.dzyh.model.DzyhDisposeModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;


/**
 * <p>Title: DzyhDisposeDAO</p>
 * <p>Description:程序自动生成服务程序“DzyhDisposeDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 张星
 * @version 1.0
 */


public class DzyhDisposeDAO extends AMSBaseDAO {

	/**
	 * 功能：物资处置单据(EAM) EAM_ITEM_DISPOSE 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EamItemDisposeDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public DzyhDisposeDAO(SfUserDTO userAccount, EamItemDisposeDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) { 
		EamItemDisposeDTO dtoPara = (EamItemDisposeDTO)dtoParameter;
		super.sqlProducer = new DzyhDisposeModel((SfUserDTO)userAccount, dtoPara);
	}

	public void createDisposeUpdate(EamItemDisposeDTO disponseDto) throws DataHandleException{
		boolean autoCommit = true;
		DataHandleException error = null;
		boolean operateResult = false;

		DzyhDisposeModel disposeModel=new DzyhDisposeModel(userAccount,null);
		try {
			autoCommit = conn.getAutoCommit();
	        conn.setAutoCommit(false);
	        int success = 0;
	        success = disposeModel.createDisposeUpdateEii(conn, disponseDto);
	        if(success > 0){
	        	operateResult = true;
	        }
		} catch (SQLException ex) {
			Logger.logError(ex);
			error = new DataHandleException(ex);
		} finally{
			try {
				if (operateResult) {
					conn.commit();
					prodMessage("DISPOSE_SAVE_SUCCESS");
				} else {
					conn.rollback();
					prodMessage("DISPOSE_SAVE_FAILURE");
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