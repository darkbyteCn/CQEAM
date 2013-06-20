package com.sino.ams.system.cost.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.system.cost.dto.AmsMisCostMatchDTO;
import com.sino.ams.system.cost.model.AmsMisCostMatchModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2008-7-31
 * Time: 10:43:50
 * To change this template use File | Settings | File Templates.
 */
public class AmsMisCostMatchDAO extends AMSBaseDAO {


	/**
	 * 功能：工单对象使用专业，定义一个专业可以对哪几个专业创建工单(EAM) ETS_OBJECT_CATEGORY 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsMisCostMatchDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public AmsMisCostMatchDAO(SfUserDTO userAccount, AmsMisCostMatchDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		AmsMisCostMatchDTO dtoPara = (AmsMisCostMatchDTO)dtoParameter;
		super.sqlProducer = new AmsMisCostMatchModel((SfUserDTO)userAccount, dtoPara);
	}


     /**
     * 解除某次批量匹配的??
     * @param batchId 批次
     * @return success
     * @throws SQLException
     */
    public int unMatchBatch(String batchId) throws SQLException {
        int count = 0;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);

            conn.commit();
        } catch (SQLException e) {
            Logger.logError(e);
            conn.rollback();
            prodMessage("SAVE_FAILURE");
            count = -1;
        } finally {
            conn.setAutoCommit(autoCommit);
        }
        return count;
    }



  /**
     * 功能：进行解除匹配的操作。
     * @return boolean
     */
    public void unMatch(String[] subCheck) throws DataHandleException {          //do_save操作
        boolean autoCommit = false;
        boolean hasError = true;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
//            AmsMisLocMatchDTO dtopara = (AmsMisLocMatchDTO) dtoParameter;
            for(int i=0; i<subCheck.length;i++){
               AmsMisCostMatchModel unMatchModel =new AmsMisCostMatchModel(userAccount, null);
               DBOperator.updateRecord(unMatchModel.getDelMatchModel(subCheck[i]),conn);
            }
            conn.commit();
            hasError = false;
//            getMessage().addParameterValue("成本中心");
//            prodMessage(MsgKeyConstant.DELETE_DATA_SUCCESS);
            prodMessage(AssetsMessageKeys.ORDER_CANCEL_SUCCESS);
            getMessage().addParameterValue("成本中心和部门对照关系");
        } catch (SQLException ex) {
            Logger.logError(ex);
            prodMessage(MsgKeyConstant.SQL_ERROR);
        } finally {
            if (hasError) {
                try {
                    conn.rollback();
                    conn.setAutoCommit(autoCommit);
                } catch (SQLException ex) {
                    Logger.logError(ex);
                    prodMessage(MsgKeyConstant.ROLL_BACK_ERROR);
                }
            }
        }
    }

}
