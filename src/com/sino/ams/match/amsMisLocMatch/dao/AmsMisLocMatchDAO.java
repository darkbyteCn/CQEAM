package com.sino.ams.match.amsMisLocMatch.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.sino.ams.match.amsMisLocMatch.dto.AmsMisLocMatchDTO;
import com.sino.ams.match.amsMisLocMatch.model.AmsMisLocMatchModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: Zyun
 * Date: 2007-11-21
 * Time: 19:44:03
 * To change this template use File | Settings | File Templates.
 */
public class AmsMisLocMatchDAO extends BaseDAO {
     private SfUserDTO sfUser = null;

    /**
     * 功能：租赁房屋(EAM) AMS_HOUSE_INFO 数据访问层构造函数
     *
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsHouseInfoDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
    public AmsMisLocMatchDAO(SfUserDTO userAccount, AmsMisLocMatchDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        sfUser = userAccount;
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     *
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsMisLocMatchDTO dtoPara = (AmsMisLocMatchDTO) dtoParameter;
        super.sqlProducer = new AmsMisLocMatchModel((SfUserDTO) userAccount, dtoPara);
    }

    /**
     * 功能：插入租赁房屋(EAM)表“AMS_HOUSE_INFO”数据。
     *
     * @return boolean
     */
    public void doMatch() throws DataHandleException {          //do_save操作
//        boolean operateResult = false;
        boolean autoCommit = true;
        boolean hasError = true;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            AmsMisLocMatchDTO dtopara = (AmsMisLocMatchDTO) dtoParameter;
            AmsMisLocMatchModel AMLocMatchModel =new AmsMisLocMatchModel((SfUserDTO) userAccount, dtopara);
            DBOperator.updateRecord(AMLocMatchModel.matchETSMISLoc(),conn);

//            operateResult = true;
            conn.commit();
            hasError = false;
            getMessage().addParameterValue("租赁资产");
        } catch (SQLException ex) {
            Logger.logError(ex);
            prodMessage(MsgKeyConstant.SQL_ERROR);
        } catch (SQLModelException e) {
            e.printStackTrace();
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
//        return operateResult;
    }


  /**
     * 功能：进行解除匹配的操作。
     * @return boolean
     */
    public void unMatch(String[] subCheck) throws DataHandleException {          //do_save操作
//        boolean operateResult = false;
        boolean autoCommit = false;
        boolean hasError = true;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            AmsMisLocMatchDTO dtopara = (AmsMisLocMatchDTO) dtoParameter;
            for(int i=0; i<subCheck.length;i++){
               String[] loc= StrUtil.splitStr(subCheck[i],"@@@");
                String etsNO=loc[0];
				String misLocation=loc[1];
                dtopara.setWorkorderObjectNo(etsNO);
                dtopara.setAssetsLocation(misLocation);
               AmsMisLocMatchModel AMLocMatchModel =new AmsMisLocMatchModel((SfUserDTO) userAccount, dtopara);
               DBOperator.updateRecord(AMLocMatchModel.getDelMatchModel(),conn);
            }
//            operateResult = true;
            conn.commit();
            hasError = false;
            getMessage().addParameterValue("租赁资产");
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
//        return operateResult;
    }
    /**
     * 功能：
     * @return boolean
     */
    public void updateData() throws DataHandleException {                     //修改数据
        AmsMisLocMatchDTO dtopara = (AmsMisLocMatchDTO) dtoParameter;
        super.updateData();
        getMessage().addParameterValue("资产");
    }


    public void updateEIIData() throws DataHandleException {
        AmsMisLocMatchModel rentModel = (AmsMisLocMatchModel)sqlProducer;
        SQLModel sqlModel = rentModel.getupdataEIIModel();
        DBOperator.updateRecord(sqlModel,conn);
    }
}
