package com.sino.ams.plan.dao;

import java.sql.Connection;

import com.sino.ams.plan.dto.AmsWorkPlanDTO;
import com.sino.ams.plan.model.AmsWorkPlanQueryModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.dto.DTO;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2007-9-21
 * Time: 14:50:23
 * To change this template use File | Settings | File Templates.
 */
public class AmsWorkPlanQueryDAO extends BaseDAO {
    private AmsWorkPlanQueryModel amsWorkPlanQueryModel = null;
    private SfUserDTO SfUser = null;

    public AmsWorkPlanQueryDAO(SfUserDTO userAccount, AmsWorkPlanDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        SfUser = userAccount;
        initSQLProducer(userAccount, dtoParameter);
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsWorkPlanDTO dtoPara = (AmsWorkPlanDTO) dtoParameter;
        super.sqlProducer = new AmsWorkPlanQueryModel((SfUserDTO) userAccount, dtoPara);
        amsWorkPlanQueryModel = (AmsWorkPlanQueryModel) sqlProducer;
    }

}
