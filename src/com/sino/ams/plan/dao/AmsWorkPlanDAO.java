package com.sino.ams.plan.dao;


import java.sql.Connection;

import com.sino.ams.plan.dto.AmsWorkPlanDTO;
import com.sino.ams.plan.model.AmsWorkPlanModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;


/**
 * <p>Title: AmsWorkPlanDAO</p>
 * <p>Description:程序自动生成服务程序“AmsWorkPlanDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author yuyao
 * @version 1.0
 */


public class AmsWorkPlanDAO extends BaseDAO {

    private AmsWorkPlanModel amsWorkPlanModel = null;
    private SfUserDTO SfUser = null;
    private SQLModel sModel = null;

    /**
     * 功能：工作计划管理 AMS_WORK_PLAN 数据访问层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsWorkPlanDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
    public AmsWorkPlanDAO(SfUserDTO userAccount, AmsWorkPlanDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        SfUser = userAccount;
        sModel = new SQLModel();
        initSQLProducer(userAccount, dtoParameter);
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsWorkPlanDTO dtoPara = (AmsWorkPlanDTO) dtoParameter;
        super.sqlProducer = new AmsWorkPlanModel((SfUserDTO) userAccount, dtoPara);
        amsWorkPlanModel = (AmsWorkPlanModel) sqlProducer;
    }

    /**
     * 功能：插入工作计划管理表“AMS_WORK_PLAN”数据。
     */
    public void createData() throws DataHandleException {
        super.createData();
        getMessage().addParameterValue("工作计划管理");

    }

    /**
     * 功能：更新工作计划管理表“AMS_WORK_PLAN”数据。
     */
    public void updateData() throws DataHandleException {
        super.updateData();
        getMessage().addParameterValue("工作计划管理");

    }

    /**
     * 功能：删除工作计划管理表“AMS_WORK_PLAN”数据。
     */
    public void deleteData() throws DataHandleException {
        super.deleteData();
        getMessage().addParameterValue("工作计划管理");

    }

    public void repealData(String planId) throws DataHandleException {
        sModel = amsWorkPlanModel.getDataRepealModel(planId);
        DBOperator.updateRecord(sModel,conn);
  }
    public void compData(String planId)throws DataHandleException {
        sModel = amsWorkPlanModel.updateStatus(planId);
        DBOperator.updateRecord(sModel,conn);
    }
}