package com.sino.ams.system.rent.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.bean.OrderNumGenerator;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.dto.AmsAssetsTransHeaderDTO;
import com.sino.ams.newasset.dto.AmsAssetsTransLineDTO;
import com.sino.ams.system.rent.model.AMSRentChangeModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.flow.bean.FlowAction;
import com.sino.flow.constant.FlowConstant;
import com.sino.flow.dto.FlowDTO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.sinoflow.bean.FlowCommonUtil;
import com.sino.sinoflow.flowinterface.AppFlowBaseDTO;

/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2008-7-10
 * Time: 9:56:44
 * To change this template use File | Settings | File Templates.
 */
public class AMSRentChangeDAO extends AMSBaseDAO {
      protected FlowCommonUtil flowCommonUtil = null;
    private String hId = "";
    public AMSRentChangeDAO(SfUserDTO userAccount, AmsAssetsTransHeaderDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     *
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsAssetsTransHeaderDTO dtoPara = (AmsAssetsTransHeaderDTO) dtoParameter;
        sqlProducer = new AMSRentChangeModel((SfUserDTO) userAccount, dtoPara);
    }
    public boolean submitOrder(DTOSet lineSet, FlowDTO flowDTO) {
        boolean operateResult = false;
        boolean autoCommit = true;
        AmsAssetsTransHeaderDTO dtoPara = (AmsAssetsTransHeaderDTO) dtoParameter;
        String transType = dtoPara.getTransType();
        String transId = dtoPara.getTransId();
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            if (flowDTO.getActId().equals("")) {
                flowDTO.setApproveContent(FlowConstant.APPROVE_CONTENT_NEW);
            } else {
                flowDTO.setApproveContent(FlowConstant.APPROVE_CONTENT_RESUBMIT);
            }
            FlowAction fa = null;
            if (transId.equals("")) {
                OrderNumGenerator ong = new OrderNumGenerator(conn, userAccount.getCompanyCode(), dtoPara.getTransType());
                dtoPara.setTransNo(ong.getOrderNum());
                SeqProducer seq = new SeqProducer(conn);
                transId = seq.getGUID(); 
//                transId = seq.getStrNextSeq("AMS_RENTASSETS_TRANS_H_S");
                dtoPara.setTransId(transId);


                createData();
                /*if (amsItemTransH.getTransType().equals(DictConstant.BJSL)){
                    fa.add2Flow(flowDTO.getProcName());
                }*/

            } else {
                updateData();
                //有数据，删行信息
                deleteData();
            }
            saveOrderLines(transId, lineSet);

            operateResult = true;
        } catch (SQLException ex) {
            Logger.logError(ex);
        } catch (DataHandleException ex) {
            Logger.logError(ex);
        } finally {
            try {
                if (!operateResult) {
                    conn.rollback();
                    prodMessage(AssetsMessageKeys.ASSETS_TRANSFER_FAILURE);
                } else {
                    conn.commit();
                    prodMessage(AssetsMessageKeys.ASSETS_TRANSFER_SUCCESS);
                }
                conn.setAutoCommit(autoCommit);
                message.addParameterValue("提交");
                message.setIsError(!operateResult);
            } catch (SQLException ex) {
                Logger.logError(ex);
                prodMessage(AssetsMessageKeys.ROLL_BACK_ERROR);
            }
        }
        return operateResult;
    }
    public boolean submitNewOrder(DTOSet lineSet, FlowDTO flowDTO,String sf_appFieldValue) {
        boolean operateResult = false;
        boolean autoCommit = true;
        AmsAssetsTransHeaderDTO dtoPara = (AmsAssetsTransHeaderDTO) dtoParameter;
        String transType = dtoPara.getTransType();
        String transId = dtoPara.getTransId();
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            if (flowDTO.getActId().equals("")) {
                flowDTO.setApproveContent(FlowConstant.APPROVE_CONTENT_NEW);
            } else {
                flowDTO.setApproveContent(FlowConstant.APPROVE_CONTENT_RESUBMIT);
            }
//            FlowAction fa = null;
            if (transId.equals("")) {
                OrderNumGenerator ong = new OrderNumGenerator(conn, userAccount.getCompanyCode(), dtoPara.getTransType());
                dtoPara.setTransNo(ong.getOrderNum());
                SeqProducer seq = new SeqProducer(conn);
                transId = StrUtil.nullToString(seq.getStrNextSeq("AMS_RENTASSETS_TRANS_H_S"));
                dtoPara.setTransId(transId);


                createData();
                /*if (amsItemTransH.getTransType().equals(DictConstant.BJSL)){
                    fa.add2Flow(flowDTO.getProcName());
                }*/

            } else {
                updateData();
                //有数据，删行信息
                deleteData();
            }
            saveOrderLines(transId, lineSet);
            processFlow(true,sf_appFieldValue ,dtoPara);
            operateResult = true;
        } catch (SQLException ex) {
            Logger.logError(ex);
        } catch (DataHandleException ex) {
            Logger.logError(ex);
        } finally {
            try {
                if (!operateResult) {
                    conn.rollback();
                    prodMessage(AssetsMessageKeys.ASSETS_TRANSFER_FAILURE);
                } else {
                    conn.commit();
                    prodMessage(AssetsMessageKeys.ASSETS_TRANSFER_SUCCESS);
                }
                conn.setAutoCommit(autoCommit);
                message.addParameterValue("提交");
                message.setIsError(!operateResult);
            } catch (SQLException ex) {
                Logger.logError(ex);
                prodMessage(AssetsMessageKeys.ROLL_BACK_ERROR);
            }
        }
        return operateResult;
    }
      public boolean processFlow(boolean isSubmit, String Sf_appFieldValue, AmsAssetsTransHeaderDTO headerdto) {
        AmsAssetsTransHeaderDTO batchDTO = (AmsAssetsTransHeaderDTO) dtoParameter;
        hId = batchDTO.getTransId();
        AppFlowBaseDTO headerDTO = new AppFlowBaseDTO() {
            public String getPrimaryKey() {
                return hId;
            }

            public void setPrimaryKey(String primaryKey) {
            }
        };

        headerDTO.setApp_dataID(batchDTO.getTransId());
        headerDTO.setPrimaryKey(batchDTO.getTransId());
        headerDTO.setOrderNo(batchDTO.getTransNo());
        headerDTO.setOrderName(batchDTO.getTransNo());
        headerDTO.setSf_appFieldValue(Sf_appFieldValue);

        flowCommonUtil = new FlowCommonUtil(headerDTO, conn);
        return flowCommonUtil.processProcedure(isSubmit);
    }
    private void saveOrderLines(String transId, DTOSet lineSet) throws
            DataHandleException {
        if (lineSet != null && !lineSet.isEmpty()) {
            AmsAssetsTransHeaderDTO orderDTO = (AmsAssetsTransHeaderDTO)
                    dtoParameter;
            AMSRentChangeLDAO lineDAO = new AMSRentChangeLDAO(
                    userAccount, null, conn);
            String transferType = orderDTO.getTransferType();
            for (int i = 0; i < lineSet.getSize(); i++) {
                AmsAssetsTransLineDTO lineData = (AmsAssetsTransLineDTO)
                        lineSet.getDTO(i);
                if (lineData.getBarcode().equals("")) {
                    continue;
                }
                lineData.setTransId(transId);
                lineData.setLineStatus(orderDTO.getTransStatus());
                lineDAO.setDTOParameter(lineData);
                lineDAO.createData();
//				createReserveAssets(lineData.getBarcode()); //保留资产
            }
        }
    }
     public void updateIn(DTOSet lineSet) throws SQLException {
        CallableStatement cStmt = null;
            AmsAssetsTransHeaderDTO orderDTO = (AmsAssetsTransHeaderDTO)
                    dtoParameter;
        String sqlStr = "{call AMS_PUB_PKG.UPDATE_RENT_INF(?,?,?,?,?,?)}";
        try {
            cStmt = conn.prepareCall(sqlStr);
            /*     if (lineSet != null && lineSet.isEmpty()) {*/

            for (int i = 0; i < lineSet.getSize(); i++) {
                AmsAssetsTransLineDTO dto = (AmsAssetsTransLineDTO) lineSet.getDTO(i);
                cStmt = conn.prepareCall(sqlStr);
                cStmt.setString(1, dto.getBarcode());
                cStmt.setString(2, orderDTO.getToDept());
                cStmt.setInt(3, userAccount.getUserId());
                cStmt.setString(4, orderDTO.getTransId());
                cStmt.setString(5, dto.getResponsibilityUser());
                cStmt.setString(6, dto.getAssignedToLocation());
                cStmt.execute();
            }
//            }
        } finally {
            DBManager.closeDBStatement(cStmt);
        }
    }
    public String approveOrder(DTOSet lineSet, FlowDTO flowDTO, String sectionRight) throws SQLException, ContainerException {
        String success = "";
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);

            //流程处理
            FlowAction fa = new FlowAction(conn, flowDTO);
            if (fa.isFlowToEnd()) {

                String status = DictConstant.COMPLETED;
                 updateIn(lineSet);

            }

            if(fa.getFlowCode().equals(DictConstant.IN_PROCESS)){
                 String status = DictConstant.IN_PROCESS;

            }
            fa.flow();
            conn.commit();
            success = "ok";
        } catch (SQLException e) {
            prodMessage("SQL_ERROR");
            Logger.logError(e);
            conn.rollback();
        } catch (QueryException e) {
            prodMessage("SQL_ERROR");
            Logger.logError(e);
            conn.rollback();
        } catch (DataHandleException e) {
            prodMessage("SQL_ERROR");
            Logger.logError(e);
            conn.rollback();
        } finally {
            conn.setAutoCommit(autoCommit);
        }
        return success;
    }
    public String approveNewOrder(DTOSet lineSet, FlowDTO flowDTO, String sectionRight,String  sf_appFieldValue,String to_end) throws SQLException, ContainerException {
        String success = "";
        boolean autoCommit = false;
         AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);

            //流程处理
//            FlowAction fa = new FlowAction(conn, flowDTO);
             if(to_end.equals("1")){

                String status = DictConstant.COMPLETED;
                 updateIn(lineSet);

            }
            processFlow(true,sf_appFieldValue,dto);
//            if(fa.getFlowCode().equals(DictConstant.IN_PROCESS)){
//                 String status = DictConstant.IN_PROCESS;
//
//            }
//            fa.flow();
            conn.commit();
            success = "ok";
        } catch (SQLException e) {
            prodMessage("SQL_ERROR");
            Logger.logError(e);
            conn.rollback();
        }/* catch (QueryException e) {
            prodMessage("SQL_ERROR");
            Logger.logError(e);
            conn.rollback();
        } catch (DataHandleException e) {
            prodMessage("SQL_ERROR");
            Logger.logError(e);
            conn.rollback();
        }*/ finally {
            conn.setAutoCommit(autoCommit);
        }
        return success;
    }
    public void reject(AmsAssetsTransHeaderDTO dto, FlowDTO flowDTO,String appName,String sf_appFieldValue) throws SQLException {
        try {
            conn.setAutoCommit(false);
            //业务处理

            AMSRentChangeModel model = new AMSRentChangeModel(userAccount, dto);
            String status = DictConstant.REJECTED;
            DBOperator.updateRecord(model.updateStatusModel(status), conn);
            //流程处理
            flowDTO.setApproveContent(FlowConstant.APPROVE_CONTENT_REJECT);
            flowDTO.setProcName("租赁资产调拨流程");
//            FlowAction fb = new FlowAction(conn, flowDTO);
//            fb.reject2Begin();
            //  fb.reject();
            rejectApp(sf_appFieldValue,dto.getTransId()) ;
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } catch (DataHandleException e) {
            conn.rollback();
            Logger.logError(e);
            prodMessage("SPARE_SAVE_FAILURE");
        } finally {
            conn.setAutoCommit(true);
        }
    }
    public void cancle(AmsAssetsTransHeaderDTO dto, FlowDTO flowDTO,String appName,String sf_appFieldValue) throws SQLException, DataHandleException,QueryException, SQLModelException, ContainerException, ParseException {
        try {
            conn.setAutoCommit(false);
            //业务处理

            AMSRentChangeModel model = new AMSRentChangeModel(userAccount, dto);
            String status = DictConstant.CANCELED;
            DBOperator.updateRecord(model.updateStatusModel(status), conn);
            //流程处理
            flowDTO.setApproveContent(FlowConstant.APPROVE_CONTENT_REJECT);
            flowDTO.setProcName("租赁资产调拨流程");
//            FlowAction fb = new FlowAction(conn, flowDTO);
//            fb.reject2Begin();
            //  fb.reject();
            cancleFlow(appName,sf_appFieldValue,dto.getTransId()) ;
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } catch (DataHandleException e) {
            conn.rollback();
            Logger.logError(e);
            prodMessage("SPARE_SAVE_FAILURE");
        } finally {
            conn.setAutoCommit(true);
        }
    }
     public boolean rejectApp(String Sf_appFieldValue, String heId) throws DataHandleException {
        hId = heId;
        AppFlowBaseDTO headerDTO = new AppFlowBaseDTO() {
            public String getPrimaryKey() {
                return hId;
            }

            public void setPrimaryKey(String primaryKey) {
            }
        };
           headerDTO.setApp_dataID(heId);
        headerDTO.setSf_appFieldValue(Sf_appFieldValue);
        flowCommonUtil = new FlowCommonUtil(headerDTO, conn);
        return flowCommonUtil.reject();
    }
     public boolean cancleFlow(String proName,String Sf_appFieldValue, String batchId)throws SQLException, DataHandleException,QueryException, SQLModelException, ContainerException, ParseException {
       AmsAssetsTransHeaderDTO transDto = (AmsAssetsTransHeaderDTO) dtoParameter;
         hId=transDto.getTransId();
        boolean  aa=false;
        AppFlowBaseDTO headerDTO =new AppFlowBaseDTO() {
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

        flowCommonUtil = new FlowCommonUtil( headerDTO , conn);
        try {
        aa=flowCommonUtil.processDel();
        } catch (Exception e) {
            Logger.logError(e);
        }
        return aa;
    }
}
