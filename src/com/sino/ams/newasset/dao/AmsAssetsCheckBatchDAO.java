package com.sino.ams.newasset.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.ams.appbase.dao.AMSProcedureBaseDAO;
import com.sino.ams.bean.OrderNumGenerator;
import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.dto.AmsAssetsCheckBatchDTO;
import com.sino.ams.newasset.dto.AmsAssetsCheckHeaderDTO;
import com.sino.ams.newasset.dto.AmsAssetsCheckLineDTO;
import com.sino.ams.newasset.model.AmsAssetsCheckBatchModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.flow.bean.FlowAction;
import com.sino.flow.dto.FlowDTO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: AmsAssetsCheckBatchDAO</p>
 * <p>Description:程序自动生成服务程序“AmsAssetsCheckBatchDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */


public class AmsAssetsCheckBatchDAO extends AMSProcedureBaseDAO {
    private DTOSet checkOrders = null;

    /**
     * 功能：资产盘点批表(EAM) AMS_ASSETS_CHECK_BATCH 数据访问层构造函数
     *
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsAssetsCheckBatchDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
    public AmsAssetsCheckBatchDAO(SfUserDTO userAccount, AmsAssetsCheckBatchDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     *
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsAssetsCheckBatchDTO dto = (AmsAssetsCheckBatchDTO) dtoParameter;
        sqlProducer = new AmsAssetsCheckBatchModel((SfUserDTO) userAccount, dto);
    }


    public boolean saveNewCheckOrders(DTOSet checkOrders) {
        boolean operateResult = false;
        boolean autoCommit = true;
        boolean isNewData = false;
        AmsAssetsCheckBatchDTO batchDTO = (AmsAssetsCheckBatchDTO) dtoParameter;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            String batchId = batchDTO.getBatchId();
            List leftOrders = new ArrayList();
            if (!StrUtil.isEmpty(batchId)) {
                leftOrders = getExistChkOrders();
            } else {
                isNewData = true;
            }
            saveNewBatchData();
            if (checkOrders != null) {
                int orderCount = checkOrders.getSize();
                AmsAssetsCheckHeaderDTO headerDTO = null;
                AmsAssetsCheckHeaderDAO headerDAO = new AmsAssetsCheckHeaderDAO(userAccount, headerDTO, conn);
                headerDAO.setServletConfig(servletConfig);
                String headerId = "";
                for (int i = 0; i < orderCount; i++) {
                    headerDTO = (AmsAssetsCheckHeaderDTO) checkOrders.getDTO(i);
                    headerId = headerDTO.getHeaderId();
                    leftOrders.remove(headerId);
                    headerDTO.setBatchId(batchDTO.getBatchId());
                    headerDTO.setOrderStatus(batchDTO.getBatchStatus());
                    headerDTO.setOrderType(batchDTO.getOrderType());
                    headerDTO.setCostCenterCode(batchDTO.getCostCenterCode());
                    headerDAO.setDTOParameter(headerDTO);
                    headerDAO.saveCheckHeader();
                }
            }
            if (!leftOrders.isEmpty()) {
                deleteLeftOrders(leftOrders);
            }
            operateResult = processProcedure();
        } catch (DataHandleException ex) {
            ex.printLog();
        } catch (SQLException ex) {
            Logger.logError(ex);
        } catch (QueryException ex) {
            Logger.logError(ex);
        } finally {
            try {
                if (!operateResult) {
                    conn.rollback();
                    prodMessage(AssetsMessageKeys.SAVE_CHECK_FAILURE);
                    if (isNewData) {
                        batchDTO.setBatchId("");
                        setDTOParameter(batchDTO);
                    }
                } else {
                    conn.commit();
                    prodMessage(AssetsMessageKeys.SAVE_CHECK_SUCCESS);
                }
                message.setIsError(!operateResult);
                conn.setAutoCommit(autoCommit);
            } catch (SQLException ex1) {
                Logger.logError(ex1);
            }
        }
        return operateResult;
    }

    private void saveNewBatchData() throws DataHandleException {
        try {
            AmsAssetsCheckBatchDTO batchDTO = (AmsAssetsCheckBatchDTO) dtoParameter;
            String batchId = batchDTO.getBatchId();
            if (StrUtil.isEmpty(batchId)) {
                SeqProducer seqProducer = new SeqProducer(conn);
                batchId = seqProducer.getGUID();
                batchDTO.setBatchId(batchId);
                batchDTO.setCreatedBy(userAccount.getUserId());
				batchDTO.setCurrCreationDate();
                String companyCode = userAccount.getCompanyCode();
                String transType = AssetsDictConstant.ASS_CHK_TASK;
                OrderNumGenerator numberProducer = new OrderNumGenerator(conn, companyCode, transType);
                numberProducer.setOrderLength(3);
                batchDTO.setBatchNo(numberProducer.getOrderNum());
                setDTOParameter(batchDTO);
                createData();
            } else {
                batchDTO.setBatchStatus(AssetsDictConstant.IN_PROCESS);
                if (batchDTO.getSf_end().equals("1")) {
                	batchDTO.setBatchStatus(AssetsDictConstant.CHK_STATUS_DISTRUIBUTED);
                }
                updateData();
            }

        } catch (SQLException ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex);
        }
    }

    /**
     * 功能：检查本次提交大的待盘点工单地点是否存在先前未归档工单
     *
     * @param checkOrders DTOSet
     * @return boolean
     * @throws QueryException
     */
    public boolean hasPrevOrders(DTOSet checkOrders) throws QueryException {
        boolean hasPrevOrders = false;
        try {
            AmsAssetsCheckHeaderDAO headerDAO = new AmsAssetsCheckHeaderDAO(userAccount, null, conn);
            headerDAO.setServletConfig(servletConfig);
            if (checkOrders != null && checkOrders.getSize() > 0) {
                int orderCount = checkOrders.getSize();
                AmsAssetsCheckHeaderDTO headerDTO = null;
                AmsAssetsCheckBatchDTO dto = (AmsAssetsCheckBatchDTO) dtoParameter;
                for (int i = 0; i < orderCount; i++) {
                    headerDTO = (AmsAssetsCheckHeaderDTO) checkOrders.getDTO(i);
                    headerDTO.setCostCenterCode(dto.getCostCenterCode());
                    headerDAO.setDTOParameter(headerDTO);
                    if (headerDAO.hasPreviousOrder()) {
                        hasPrevOrders = true;
                        headerDTO.setHasPreviousOrder(hasPrevOrders);
                        checkOrders.set(i, headerDTO);
                    }
                }
                this.checkOrders = checkOrders;
                if (hasPrevOrders) {
                    prodMessage(AssetsMessageKeys.SUBMIT_DATA_INVALID);
                }
            }
        } catch (DTOException ex) {
            ex.printLog();
            throw new QueryException(ex);
        }
        return hasPrevOrders;
    }

    /**
     * 功能：获取本次提交的待创建的的盘点工单数据
     *
     * @return DTOSet
     * @throws QueryException
     */
    public DTOSet getSubmitedOrders() throws QueryException {
        try {
            AmsAssetsCheckHeaderDTO chkOrder = null;
            AssetsOptProducer optProducer = new AssetsOptProducer(userAccount, conn);
            String chkCategoryOpt = "";
            if (checkOrders != null) {
                for (int i = 0; i < checkOrders.getSize(); i++) {
                    chkOrder = (AmsAssetsCheckHeaderDTO) checkOrders.getDTO(i);
                    chkCategoryOpt = optProducer.getChkCategoryOption(chkOrder.getCheckCategory());
                    chkOrder.setCheckCategoryOpt(chkCategoryOpt);
                    checkOrders.set(i, chkOrder);
                }
            }
        } catch (DTOException ex) {
            ex.printLog();
            throw new QueryException(ex);
        }
        return checkOrders;
    }

    public boolean submitNewCheckOrders(DTOSet checkOrders) {
        boolean operateResult = false;
        boolean autoCommit = true;
        boolean dataCreated = false;
        AmsAssetsCheckBatchDTO batchDTO = (AmsAssetsCheckBatchDTO) dtoParameter;
        try {
            String batchId = batchDTO.getBatchId();
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            List leftOrders = new ArrayList();
            if (!StrUtil.isEmpty(batchId)) {
                leftOrders = getExistChkOrders();
            } else {
                dataCreated = true;
            }
            batchDTO = (AmsAssetsCheckBatchDTO) dtoParameter;
            if (batchDTO.getSf_end().equals("1")) {
            	batchDTO.setBatchStatus(AssetsDictConstant.CHK_STATUS_DISTRUIBUTED);
                batchDTO.setFlow2End(true);
            }
            saveNewBatchData();
            if (checkOrders != null) {
                int orderCount = checkOrders.getSize();
                AmsAssetsCheckHeaderDTO headerDTO = null;
                AmsAssetsCheckHeaderDAO headerDAO = new AmsAssetsCheckHeaderDAO(userAccount, headerDTO, conn);
                headerDAO.setServletConfig(servletConfig);
                String headerId = "";
                for (int i = 0; i < orderCount; i++) {
                    headerDTO = (AmsAssetsCheckHeaderDTO) checkOrders.getDTO(i);
                    headerId = headerDTO.getHeaderId();
                    leftOrders.remove(headerId);
                    headerDTO.setBatchId(batchDTO.getBatchId());
                    headerDTO.setOrderStatus(batchDTO.getBatchStatus());
                    headerDTO.setFlow2End(batchDTO.isFlow2End());
                    headerDTO.setOrderType(batchDTO.getOrderType());
                    headerDTO.setCostCenterCode(batchDTO.getCostCenterCode());
                    headerDAO.setDTOParameter(headerDTO);
                    headerDAO.saveCheckHeader();
                }
            }
            if (!leftOrders.isEmpty()) {
                deleteLeftOrders(leftOrders);
            }
            operateResult = processProcedure();
        } catch (DataHandleException ex) {
            ex.printLog();
        } catch (SQLException ex) {
            Logger.logError(ex);
        } catch (QueryException ex) {
            Logger.logError(ex);
        } finally {
            try {
                String batchStatus = batchDTO.getBatchStatus();
                if (!operateResult) {
                    conn.rollback();
                    if (batchStatus.equals(AssetsDictConstant.CHK_STATUS_DISTRUIBUTED)) {
                        prodMessage(AssetsMessageKeys.DIST_ORDER_FAILURE);
                    } else {
                        prodMessage(AssetsMessageKeys.SUBMIT_CHECK_FAILURE);
                    }
                    if (dataCreated) {
                        batchDTO.setBatchId("");
                        setDTOParameter(batchDTO);
                    }
                } else {
                    conn.commit();
                    if (batchStatus.equals(AssetsDictConstant.CHK_STATUS_DISTRUIBUTED)) {
                        prodMessage(AssetsMessageKeys.DIST_ORDER_SUCCESS);
                    } else {
                        prodMessage(AssetsMessageKeys.SUBMIT_CHECK_SUCCESS);
                    }
                }
                message.setIsError(!operateResult);
                conn.setAutoCommit(autoCommit);
            } catch (SQLException ex1) {
                Logger.logError(ex1);
            }
        }
        return operateResult;
    }

    /**
     * 功能：获取任务批下已经存在的盘点工单
     *
     * @return List
     * @throws QueryException
     */
    private List getExistChkOrders() throws QueryException {
        List headerIds = new ArrayList();
        try {
            AmsAssetsCheckBatchDTO dto = (AmsAssetsCheckBatchDTO) dtoParameter;
            AmsAssetsCheckHeaderDTO headerDTO = new AmsAssetsCheckHeaderDTO();
            headerDTO.setBatchId(dto.getBatchId());
            AmsAssetsCheckHeaderDAO headerDAO = new AmsAssetsCheckHeaderDAO(userAccount, headerDTO, conn);
            headerDAO.setDTOClassName(AmsAssetsCheckHeaderDTO.class.getName());
            DTOSet existOrders = (DTOSet) headerDAO.getDataByForeignKey("batchId");
            if (existOrders != null && !existOrders.isEmpty()) {
                headerIds = existOrders.toList("headerId");
            }
        } catch (DTOException ex) {
            ex.printLog();
            throw new QueryException(ex);
        }
        return headerIds;
    }

    /**
     * 功能：删除盘点工单行数据
     *
     * @param headerIds List
     * @throws DataHandleException
     */
    private void deleteLeftOrders(List headerIds) throws DataHandleException {
        AmsAssetsCheckHeaderDAO headerDAO = new AmsAssetsCheckHeaderDAO(userAccount, null, conn);
        AmsAssetsCheckLineDAO lineDAO = new AmsAssetsCheckLineDAO(userAccount, null, conn);
        AmsAssetsCheckHeaderDTO headerDTO = new AmsAssetsCheckHeaderDTO();
        AmsAssetsCheckLineDTO lineDTO = new AmsAssetsCheckLineDTO();
        String headerId = "";
        for (int i = 0; i < headerIds.size(); i++) {
            headerId = (String) headerIds.get(i);

            lineDTO.setHeaderId(headerId);
            lineDAO.setDTOParameter(lineDTO);
            lineDAO.DeleteByForeignKey("headerId");

            headerDTO.setHeaderId(headerId);
            headerDAO.setDTOParameter(headerDTO);
            headerDAO.deleteByPrimaryKey();
        }
    }

    /**
     * 功能：下发盘点工单
     *
     * @param batchIds 多选或单选的盘点任务批
     * @return boolean
     */
    public boolean distributeChkOrder(String[] batchIds) {
        boolean operateResult = false;
        boolean autoCommit = false;
        try {
            int batchCount = batchIds.length;
            AmsAssetsCheckBatchDTO batchDTO = null;
            AmsAssetsCheckHeaderDTO headerDTO = null;
            SQLModel sqlModel = null;
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            AmsAssetsCheckBatchModel modelProducer = (AmsAssetsCheckBatchModel) sqlProducer;
            AmsAssetsCheckHeaderDAO orderDAO = new AmsAssetsCheckHeaderDAO(userAccount, null, conn);

            FlowDTO flowDTO = new FlowDTO();
            flowDTO.setProcName(AssetsDictConstant.PROCEDURE_NAME_CHECK);
            FlowAction flowProcessor = new FlowAction(conn);

            for (int i = 0; i < batchCount; i++) {
                batchDTO = new AmsAssetsCheckBatchDTO();
                batchDTO.setBatchId(batchIds[i]);
                batchDTO.setBatchStatus(AssetsDictConstant.CHK_STATUS_DISTRUIBUTED);
                batchDTO.setBatchStatusName(AssetsDictConstant.CHKORDER_STATUS_DISTRUIBUTED);
                setDTOParameter(batchDTO);

                sqlModel = modelProducer.getDistributeModel();// 下发任务本身
                DBOperator.updateRecord(sqlModel, conn);

                headerDTO = new AmsAssetsCheckHeaderDTO();//更改盘电工单状态
                headerDTO.setBatchId(batchDTO.getBatchId());
                headerDTO.setOrderStatus(AssetsDictConstant.CHK_STATUS_DISTRUIBUTED);
                orderDAO.setDTOParameter(headerDTO);
                orderDAO.distributeChkOrder();

                flowDTO.setApplyId(batchIds[i]);//删除在办箱数据
                flowProcessor.setDto(flowDTO);
                flowProcessor.cancel();
            }
            operateResult = true;
        } catch (DataHandleException ex) {
            ex.printLog();
        } catch (SQLException ex) {
            Logger.logError(ex);
        } finally {
            try {
                if (operateResult) {
                    conn.commit();
                    prodMessage(AssetsMessageKeys.DIST_ORDER_SUCCESS);
                } else {
                    conn.rollback();
                    if (message == null) {
                        prodMessage(AssetsMessageKeys.DIST_ORDER_FAILURE);
                    }
                }
                conn.setAutoCommit(autoCommit);
                message.setIsError(!operateResult);
            } catch (SQLException ex1) {
                Logger.logError(ex1);
                prodMessage(AssetsMessageKeys.ROLL_BACK_ERROR);
                message.setIsError(true);
            }
        }
        return operateResult;
    }


    /**
     * 功能：撤销盘点任务
     *
     * @return boolean
     */
    public boolean cancelCheckTask() throws SQLException, DataHandleException, QueryException, SQLModelException, ContainerException, ParseException {
        boolean operateResult = false;
        boolean autoCommit = true;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);

            AmsAssetsCheckBatchModel modelProducer = (AmsAssetsCheckBatchModel) sqlProducer;
            SQLModel sqlModel = modelProducer.getBatchCancelModel(); //撤销任务本身
            DBOperator.updateRecord(sqlModel, conn);

            AmsAssetsCheckBatchDTO batchDTO = (AmsAssetsCheckBatchDTO) dtoParameter;
            sqlModel = modelProducer.getHeaderCancelModel(batchDTO.getBatchId()); //撤销任务下的盘点工单
            DBOperator.updateRecord(sqlModel, conn);

            operateResult = super.cancelProcedure();
        } catch (DataHandleException ex) {
            ex.printLog();
        } catch (SQLException ex) {
            Logger.logError(ex);
        } finally {
            try {
                if (operateResult) {
                    conn.commit();
                    prodMessage(AssetsMessageKeys.ORDER_CANCEL_SUCCESS);
                } else {
                    conn.rollback();
                    prodMessage(AssetsMessageKeys.ORDER_CANCEL_FAILURE);
                }
                conn.setAutoCommit(autoCommit);
                message.addParameterValue("盘点任务");
                message.setIsError(!operateResult);
            } catch (SQLException ex1) {
                Logger.logError(ex1);
                prodMessage(AssetsMessageKeys.ROLL_BACK_ERROR);
            }
        }
        return operateResult;
    }

    public RowSet getImpLocation(String locationCode) throws QueryException, SQLModelException {
        AmsAssetsCheckBatchModel modelProducer = (AmsAssetsCheckBatchModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getImpLocationModel(locationCode);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        sq.setTotalSummary(false);
        RowSet rows = sq.getSearchResult();
        return rows;
    }


    /**
     * 功能：设置流程数据。覆盖父类方法。
      */
    protected void prepareProcedureData(){
        AmsAssetsCheckBatchDTO dto = (AmsAssetsCheckBatchDTO) dtoParameter;
        dto.setPrimaryKey(dto.getBatchId());
        dto.setOrderNo(dto.getBatchNo());
        dto.setOrderName(dto.getOrderTypeName());
    }
}
