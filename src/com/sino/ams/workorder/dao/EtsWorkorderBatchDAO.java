package com.sino.ams.workorder.dao;


import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.log.Logger;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dto.EtsWorkorderBatchDTO;
import com.sino.ams.workorder.model.EtsWorkorderBatchModel;
import com.sino.ams.workorder.model.OrderExtendModel;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.sinoflow.bean.FlowCommonUtil;
import com.sino.sinoflow.flowinterface.AppFlowBaseDTO;


/**
 * <p>Title: EtsWorkorderBatchDAO</p>
 * <p>Description:程序自动生成服务程序“EtsWorkorderBatchDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author zhoujs
 * @version 1.0
 */


public class EtsWorkorderBatchDAO extends BaseDAO {

    private EtsWorkorderBatchModel etsWorkorderBatchModel = null;
    private SfUserDTO sfUser = null;
    protected FlowCommonUtil flowCommonUtil = null;
    private String hId = "";

    /**
     * 功能：工单批表(EAM) ETS_WORKORDER_BATCH 数据访问层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter EtsWorkorderBatchDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
    public EtsWorkorderBatchDAO(SfUserDTO userAccount, EtsWorkorderBatchDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        sfUser = userAccount;
        initSQLProducer(userAccount, dtoParameter);
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        EtsWorkorderBatchDTO dtoPara = (EtsWorkorderBatchDTO) dtoParameter;
        super.sqlProducer = new EtsWorkorderBatchModel((SfUserDTO) userAccount, dtoPara);
        etsWorkorderBatchModel = (EtsWorkorderBatchModel) sqlProducer;
    }

    /**
     * 功能：插入工单批表(EAM)表“ETS_WORKORDER_BATCH”数据。
     * @return boolean
     */
    public void createData() throws DataHandleException {
        super.createData();
        getMessage().addParameterValue("工单批表(EAM)");
    }

    /**
     * 功能：更新工单批表(EAM)表“ETS_WORKORDER_BATCH”数据。
     * @return boolean
     */
    public void updateData() throws DataHandleException {
        super.updateData();
        getMessage().addParameterValue("工单批表(EAM)");
    }

    /**
     * 功能：删除工单批表(EAM)表“ETS_WORKORDER_BATCH”数据。
     * @return boolean
     */
    public void deleteData() throws DataHandleException {
        super.deleteData();
        getMessage().addParameterValue("工单批表(EAM)");
    }

    /**
     * 保存工单批信息
     * @return boolean
     */
    public boolean saveData() throws DataHandleException {
        boolean operateResult = true;
        boolean isNew = true;
        if (isNew) {
            createData();
        } else {
            updateData();
        }

        return operateResult;
    }

    /**
     * 删除临时表中该工单批下工单，copy正式表至临时表
     * @param workorderBatchNo
     * @return
     * @throws com.sino.base.exception.DataHandleException
     *
     */
    public boolean copyToTmpData(String workorderBatchNo) throws DataHandleException {
        boolean operatorResult = true;
        SQLModel sqlModel = new SQLModel();
        List sqlModList = new ArrayList();
        OrderExtendModel orderExtend = new OrderExtendModel();

        //step1:删除工单批的临时配置信息表、工单临时表
        sqlModList.add(orderExtend.getDeleteSchemeDataModel(workorderBatchNo, true));
        sqlModList.add(orderExtend.getDeleteTmpDataModel(workorderBatchNo));
        //step2：复制正式表信息至临时表
        sqlModList.add(orderExtend.getCopyToTmpDataModel(workorderBatchNo));
        sqlModList.add(orderExtend.getCopySchemToTempModel(workorderBatchNo));

        operatorResult = DBOperator.updateBatchRecords(sqlModList, conn);
        return operatorResult;
    }

    public EtsWorkorderBatchDTO getBatchByNo() throws QueryException {
        EtsWorkorderBatchDTO workorderBatchDTO = (EtsWorkorderBatchDTO) dtoParameter;
        SQLModel sqlModel = etsWorkorderBatchModel.getDataByBatchNo(workorderBatchDTO.getWorkorderBatch());
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.setDTOClassName(EtsWorkorderBatchDTO.class.getName());
        simpleQuery.executeQuery();
        return (EtsWorkorderBatchDTO) simpleQuery.getFirstDTO();
    }

    public boolean processFlow(boolean isSubmit, String Sf_appFieldValue, EtsWorkorderBatchDTO headerdto) {
        EtsWorkorderBatchDTO batchDTO = (EtsWorkorderBatchDTO) dtoParameter;
        hId = batchDTO.getSystemid();
        AppFlowBaseDTO headerDTO = new AppFlowBaseDTO() {
            public String getPrimaryKey() {
                return hId;
            }

            public void setPrimaryKey(String primaryKey) {
            }
        };

        headerDTO.setApp_dataID(batchDTO.getSystemid());
        headerDTO.setPrimaryKey(batchDTO.getSystemid());
        headerDTO.setOrderNo(batchDTO.getWorkorderBatch());
        headerDTO.setOrderName("巡检流程");
        headerDTO.setSf_appFieldValue(Sf_appFieldValue);

//        fieldValue = orderNum;
//        h//eaderDTO.setSf_appFieldValue(fieldValue);
        flowCommonUtil = new FlowCommonUtil(headerDTO, conn);
        return flowCommonUtil.processProcedure(isSubmit);
    }

    public boolean cancleFlow(String proName, String Sf_appFieldValue, String batchId) throws SQLException, DataHandleException, QueryException, SQLModelException, ContainerException, ParseException {
        EtsWorkorderBatchDTO batchDTO = (EtsWorkorderBatchDTO) dtoParameter;
        hId = batchDTO.getSystemid();
        boolean aa = false;
        AppFlowBaseDTO headerDTO = new AppFlowBaseDTO() {
            public String getPrimaryKey() {
                return hId;
            }

            public void setPrimaryKey(String primaryKey) {
            }
        };

        headerDTO.setApp_dataID(batchId);
        headerDTO.setPrimaryKey(batchId);
        headerDTO.setSfAppName(proName);
        headerDTO.setSf_appFieldValue(Sf_appFieldValue);

        flowCommonUtil = new FlowCommonUtil(headerDTO, conn);
        try {
            aa = flowCommonUtil.processCancel();
        } catch (Exception e) {
            Logger.logError(e);
        }
        return aa;
    }

    public boolean flowBack(String proName, String Sf_appFieldValue, String batchId) throws SQLException, DataHandleException, QueryException, SQLModelException, ContainerException, ParseException {
        EtsWorkorderBatchDTO batchDTO = (EtsWorkorderBatchDTO) dtoParameter;
        hId = batchDTO.getSystemid();
        boolean aa = false;
        AppFlowBaseDTO headerDTO = new AppFlowBaseDTO() {
            public String getPrimaryKey() {
                return hId;
            }

            public void setPrimaryKey(String primaryKey) {
            }
        };

        headerDTO.setApp_dataID(batchId);
        headerDTO.setPrimaryKey(batchId);
        headerDTO.setSfAppName(proName);
        headerDTO.setSf_appFieldValue(Sf_appFieldValue);

        flowCommonUtil = new FlowCommonUtil(headerDTO, conn);
        try {
            aa = flowCommonUtil.reject();
        } catch (Exception e) {
            Logger.logError(e);
        }
        return aa;
    }
}