package com.sino.ams.workorder.dao;

import java.sql.Connection;
import java.util.List;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dto.EtsWorkorderDTO;
import com.sino.ams.workorder.model.OrderExtendModel;
import com.sino.ams.workorder.model.WorkPersonModel;
import com.sino.base.constant.db.DBActionConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.util.DataUniqueChecker;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.ValidateException;
import com.sino.framework.dto.BaseUserDTO;

/**
 * User: zhoujs
 * Date: 2008-1-9
 * Time: 11:39:27
 * Function:个人工单清单签收、签收、重新分配执行人
 */
public class PersonOrderDAO extends AMSBaseDAO {

    private SfUserDTO sfUser = null;

    /**
     * 功能：设置(EAM) 数据访问层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsRentDeadlineDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
    public PersonOrderDAO(SfUserDTO userAccount, EtsWorkorderDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        sfUser = userAccount;
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        EtsWorkorderDTO dtoPara = (EtsWorkorderDTO) dtoParameter;
        super.sqlProducer = new WorkPersonModel((SfUserDTO) userAccount, dtoPara);
    }

    /**
     * 功能：插入表“”数据。
     * @return boolean
     */
    public void createData() {
        boolean operateResult = false;
        try {
            String tableName = "";
            DataUniqueChecker datChecker = new DataUniqueChecker(tableName, dtoParameter, conn);
            datChecker.setDBAction(DBActionConstant.INSERT);
            datChecker.setServletConfig(servletConfig);
            boolean isValid = datChecker.isDataValid();
            if (!isValid) {
                prodMessage(MsgKeyConstant.UNIQUE_ERROR);
                getMessage().addParameterValue("");
            } else {
                super.createData();
                operateResult = true;
                getMessage().addParameterValue("");
            }
        } catch (ValidateException ex) {
            ex.printLog();
            prodMessage(MsgKeyConstant.COMMON_ERROR);
        } catch (DataHandleException ex) {
        }
//        return operateResult;
    }

    /**
     * 功能：更新(EAM)表“”数据。
     * @return boolean
     */
    public void updateData() throws DataHandleException {
//		boolean operateResult = super.updateData();
        super.updateData();
        getMessage().addParameterValue("");
//		return operateResult;
    }

    /**
     * 功能：删除表数据。
     * @return boolean
     */
    public void deleteData() throws DataHandleException {
        super.deleteData();
        getMessage().addParameterValue("");
    }


    /**
     * 签收工单
     * @param workorders
     * @return
     * @throws DataHandleException
     */
    public boolean signOrders(String[] workorders) throws DataHandleException {
        boolean operateResult = true;
        OrderExtendModel orderExtendModel=new OrderExtendModel();
        List sqlModelList=orderExtendModel.getSignOrdersModel(workorders,userAccount);
        operateResult= DBOperator.updateBatchRecords(sqlModelList,conn);
        return operateResult;
    }

}
