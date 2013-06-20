package com.sino.ams.plan.dao;

import java.sql.Connection;

import com.sino.ams.plan.dto.AmsWorkPlanDTO;
import com.sino.ams.plan.model.AmsWorkPlanPigeonholeModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2007-9-23
 * Time: 15:24:16
 * To change this template use File | Settings | File Templates.
 */
public class AmsWorkPlanPigeonholeDAO extends BaseDAO {
    private AmsWorkPlanPigeonholeModel model = null;
    private SfUserDTO SfUser = null;
    private SQLModel sqlModel=null;

    public AmsWorkPlanPigeonholeDAO(SfUserDTO userAccount, AmsWorkPlanDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        SfUser = userAccount;
        sqlModel=new SQLModel();
        initSQLProducer(userAccount, dtoParameter);
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsWorkPlanDTO dtoPara = (AmsWorkPlanDTO) dtoParameter;
        super.sqlProducer = new AmsWorkPlanPigeonholeModel((SfUserDTO) userAccount, dtoPara);
        model = (AmsWorkPlanPigeonholeModel) sqlProducer;
    }

    public void updateData()throws DataHandleException {
        super.updateData();
        getMessage().addParameterValue("工作计划管理");
    }
    public void repealData(String planId)throws DataHandleException {
       sqlModel= model.getDataPigeonholeModel(planId);
         DBOperator.updateRecord(sqlModel,conn);
    }
}
