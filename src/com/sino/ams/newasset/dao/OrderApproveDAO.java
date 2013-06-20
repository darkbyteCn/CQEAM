package com.sino.ams.newasset.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.sino.ams.appbase.dao.AMSProcedureBaseDAO;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.constant.AssetsURLList;
import com.sino.ams.newasset.dto.*;
import com.sino.ams.newasset.model.OrderApproveModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.flow.constant.FlowConstant;
import com.sino.flow.dto.FlowDTO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>
 * Title: AmsAssetsTransHeaderDAO
 * </p>
 * <p>
 * Description:程序自动生成服务程序“AmsAssetsTransHeaderDAO”，请根据需要自行修改
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company: 北京思诺博信息技术有限公司
 * </p>
 *
 * @author 唐明胜
 * @version 1.0
 */

public class OrderApproveDAO extends AMSProcedureBaseDAO {

    /**
     * 功能：资产业务头表(EAM)--取代原表 AMS_ASSETS_TRANS_HEADER 数据访问层构造函数
     *
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsAssetsTransHeaderDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
    public OrderApproveDAO(SfUserDTO userAccount, AmsAssetsTransHeaderDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     *
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
        super.sqlProducer = new OrderApproveModel((SfUserDTO) userAccount, dto);
    }

    /**
     * 功能：审批单据，含调拨单，报废单，处置单
     *
     * @param orderLines 更改调拨单行的折旧费用账户(2008-12-01 17:37)
     * @return boolean
     */
    public boolean approveOrder(DTOSet orderLines, String[] barcodes) {
        boolean operateResult = false;
        boolean autoCommit = true;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
            boolean flow2End = dto.isFlowOver();
            if (flow2End) {
                if (dto.getTransType().equals(
                        AssetsDictConstant.ASS_DIS)
                        || dto.getTransType().equals(
                        AssetsDictConstant.ASS_SUB)
                        || dto.getTransType().equals(
                        AssetsDictConstant.ASS_SELL)
                        || dto.getTransType().equals(
                        AssetsDictConstant.ASS_RENT)
                        || dto.getTransType().equals(
                        AssetsDictConstant.ASS_DONA)) {
                    dto.setTransStatus(AssetsDictConstant.COMPLETED);
                } else {
                    dto.setTransStatus(AssetsDictConstant.APPROVED);
                }
            } else {
                dto.setTransStatus(AssetsDictConstant.IN_PROCESS);
            }
            setDTOParameter(dto);
            OrderApproveModel modelProducer = (OrderApproveModel) sqlProducer;
            SQLModel sqlModel = modelProducer.getOrderApproveModel();
            DBOperator.updateRecord(sqlModel, conn);

            String provinceCode = servletConfig.getProvinceCode();
            if (provinceCode.equals(AssetsDictConstant.PROVINCE_CODE_JIN)) {// 仅山西需要该功能。
                if (dto.getTransferType().equals("BTW_COMP")) {
                    AmsAssetsTransLineDAO lineDAO = new AmsAssetsTransLineDAO(userAccount, null, conn);
                    lineDAO.uodateAccount(orderLines);

                }
            }
            if (provinceCode.equals(AssetsDictConstant.PROVINCE_CODE_SHAN)) {// 陕西功能
                if (dto.getTransferType().equals("BTW_COMP")) {
                    AmsAssetsTransLineDAO lineDAO = new AmsAssetsTransLineDAO(userAccount, null, conn);
                    // lineDAO.uodateAccount(orderLines);
                    lineDAO.updateTransLine(orderLines);// 更新调入地点和接收人
                }
            }
            if (dto.getTransType().equals(AssetsDictConstant.ASS_DIS)
                    && dto.getAttribute4().equals("MTL_ASSETS")) {
                if (barcodes != null && barcodes.length != 0) {
                    updateTransLineRemark(barcodes);
                }
            }
            if (dto.getTransType().equals(AssetsDictConstant.ASS_DIS)
                    && dto.getAttribute4().equals("PROV_ASSETS")) {
                // 特殊处理，先清除选中资产REMARK
                if (barcodes != null && barcodes.length != 0) {
                    deleteTransLineRemark(barcodes);
                    updateTransLineRemark(barcodes);
                }
            }
            if (flow2End) {
                sqlModel = modelProducer.getLineStatusUpdateModel(); // 更新资产单据行数据状态为已审批
                DBOperator.updateRecord(sqlModel, conn);
                if (dto.getTransType().equals(AssetsDictConstant.ASS_DIS)) { // 报废
                    sqlModel = modelProducer.getAssetsDiscardModel();
                    DBOperator.updateRecord(sqlModel, conn);
                    deleteReserveAssets();
                    if (provinceCode.equals(AssetsDictConstant.PROVINCE_CODE_SX)) {// 山西省报废要同步
                        recordChkLog(AssetsDictConstant.ASS_DIS, AssetsDictConstant.STATUS_NO);
                    }
                } else if (dto.getTransType().equals(AssetsDictConstant.ASS_CLR)) { // 处置
                    sqlModel = modelProducer.getAssetsClearModel();
                    DBOperator.updateRecord(sqlModel, conn);
                    deleteReserveAssets();
                } else if (dto.getTransType().equals(AssetsDictConstant.ASS_FREE)) { // 闲置
                    sqlModel = modelProducer.getAssetsFreeModel();
                    DBOperator.updateRecord(sqlModel, conn);
                    deleteReserveAssets();
                } else if (dto.getTransType().equals(AssetsDictConstant.ASS_SUB)) { // 减值
                    sqlModel = modelProducer.getAssetsSubModel();
                    DBOperator.updateRecord(sqlModel, conn);
                    deleteReserveAssets();
                } else if (dto.getTransType().equals(AssetsDictConstant.ASS_SELL)) {
                    sqlModel = modelProducer.getAssetsSellModel();
                    DBOperator.updateRecord(sqlModel, conn);
                    deleteReserveAssets();
                } else if (dto.getTransType().equals(AssetsDictConstant.ASS_RENT)) {
                    sqlModel = modelProducer.getAssetsRentModel();
                    DBOperator.updateRecord(sqlModel, conn);
                    deleteReserveAssets();
                } else if (dto.getTransType().equals(AssetsDictConstant.ASS_DONA)) {
                    sqlModel = modelProducer.getAssetsDonaModel();
                    DBOperator.updateRecord(sqlModel, conn);
                    deleteReserveAssets();
                }
            }
            operateResult = processProcedure(true);
        } catch (DataHandleException ex) {
            ex.printLog();
        } catch (Throwable ex) {
            Logger.logError(ex);
        } finally {
            try {
                if (!operateResult) {
                    conn.rollback();
                } else {
                    conn.commit();
                }
                conn.setAutoCommit(autoCommit);
            } catch (SQLException ex1) {
                Logger.logError(ex1);
            }
        }
        return operateResult;
    }

    protected void prepareProcedureData(){
        AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
        dto.setPrimaryKey(dto.getTransId());
        dto.setOrderNo(dto.getTransNo());
        dto.setOrderName(dto.getTransTypeValue());//待确定...
    }

    public boolean newApproveOrder() {
        boolean operateResult = false;
        boolean autoCommit = true;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
            dto.setTransStatus(AssetsDictConstant.IN_PROCESS);
            if (dto.isFlowOver()) {
                dto.setTransStatus(AssetsDictConstant.COMPLETED);
            }
            setDTOParameter(dto);
            OrderApproveModel modelProducer = (OrderApproveModel) sqlProducer;
            SQLModel sqlModel = modelProducer.getOrderApproveModel();
            DBOperator.updateRecord(sqlModel, conn);

            if (dto.isFlowOver()) {
                if (dto.getTransType().equals(AssetsDictConstant.ASS_DIS)) { // 报废
                    String sfAttribute1 = dto.getSf_task_attribute1();
                    if(!sfAttribute1.equals("CANCEL")){
                        sqlModel = modelProducer.getLineStatusUpdateModel(); // 更新资产单据行数据状态为已审批
                        DBOperator.updateRecord(sqlModel, conn);
                        sqlModel = modelProducer.getAssetsDiscardModel();
                        DBOperator.updateRecord(sqlModel, conn);
                        recordChkLog(AssetsDictConstant.ASS_DIS, AssetsDictConstant.STATUS_NO);
                    }
                    deleteReserveAssets();
                }
            }
            operateResult = processProcedure(true);
        } catch (DataHandleException ex) {
            ex.printLog();
        } catch (Throwable ex) {
            Logger.logError(ex);
        } finally {
            try {
                if (!operateResult) {
                    conn.rollback();
                } else {
                    conn.commit();
                }
                conn.setAutoCommit(autoCommit);
                processMessage(operateResult);
            } catch (SQLException ex1) {
                Logger.logError(ex1);
            }
        }
        return operateResult;
    }

    /**
     * 功能：构造单据审批消息提示
     *
     * @param operateResult boolean
     */
    private void processMessage(boolean operateResult) {
        AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
        if (operateResult) {
            prodMessage(AssetsMessageKeys.PASS_ORDER_SUCCESS);
        } else {
            prodMessage(AssetsMessageKeys.PASS_ORDER_FAILURE);
        }
        String orderType = dto.getTransTypeValue();
        if (orderType.indexOf("单") > -1) {
            orderType = orderType.substring(0, orderType.length() - 1);
        }
        message.addParameterValue(orderType);
        message.addParameterValue(dto.getTransNo());
        message.setIsError(!operateResult);
    }

    /**
     * 功能：删除本单据保留的资产：仅用于报废和处置单据
     *
     * @throws DataHandleException
     */
    private void deleteReserveAssets() throws DataHandleException {
        AmsAssetsReservedDTO reserveDTO = new AmsAssetsReservedDTO();
        AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
        reserveDTO.setTransId(dto.getTransId());
        AmsAssetsReservedDAO reserveDAO = new AmsAssetsReservedDAO(userAccount,
                reserveDTO, conn);
        reserveDAO.DeleteByForeignKey("transId");
    }


    /**
     * 功能：记录设备最新一次交易情况：例如报废，其他的视具体需要而定 需要同步到MIS的时候调用该方法。
     *
     * @param orderType String
     * @param isExist   String
     * @throws DataHandleException
     */
    private void recordChkLog(String orderType, String isExist) throws DataHandleException {
        try {
            AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
            AmsAssetsTransLineDTO line = new AmsAssetsTransLineDTO();
            line.setTransNo(dto.getTransNo());
            line.setTransId(dto.getTransId());
            AmsAssetsTransLineDAO lineDAO = new AmsAssetsTransLineDAO(userAccount, line, conn);
            lineDAO.setDTOClassName(AmsAssetsTransLineDTO.class.getName());
            DTOSet dtos = (DTOSet) lineDAO.getDataByForeignKey("transId");
            if (dtos != null && !dtos.isEmpty()) {
                int lineCount = dtos.getSize();
                AmsAssetsChkLogDTO chkLogDTO = getChkLogData(orderType, isExist);
                AmsAssetsChkLogDAO chkLogDAO = new AmsAssetsChkLogDAO(userAccount, chkLogDTO, conn);

                AmsItemInfoHistoryDTO historyDTO = getHistoryData();
                AmsItemInfoHistoryDAO historyDAO = new AmsItemInfoHistoryDAO(userAccount, historyDTO, conn);

                for (int i = 0; i < lineCount; i++) {
                    line = (AmsAssetsTransLineDTO) dtos.getDTO(i);

                    processChkLogData(chkLogDTO, line);
                    chkLogDAO.saveCheckLogData();

                    //设备变更历史
                    historyDTO.setBarcode(line.getBarcode());
                    historyDAO.recordHistory();
                }
            }
        } catch (QueryException ex) {
            ex.printLog();
            throw new DataHandleException(ex);
        }
    }

    private AmsAssetsChkLogDTO getChkLogData(String orderType, String isExist){
        AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
        AmsAssetsChkLogDTO chkLogDTO = new AmsAssetsChkLogDTO();
        chkLogDTO.setOrderType(orderType);
        chkLogDTO.setIsExist(isExist);

        String orderURL = AssetsURLList.ASSETS_TRANS_SERVLET;
        orderURL += "?act=" + AssetsActionConstant.DETAIL_ACTION;
        orderURL += "&transId=" + dto.getTransId();
        chkLogDTO.setOrderDtlUrl(orderURL);
        return chkLogDTO;
    }

    private AmsItemInfoHistoryDTO getHistoryData(){
        AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
        String orderURL = "/servlet/com.sino.ams.newasset.servlet.AmsAssetsDisTransHeaderServlet";
        orderURL += "?act=DETAIL_ACTION";
        orderURL += "&transId=" + dto.getTransId();
        AmsItemInfoHistoryDTO itemDTO = new AmsItemInfoHistoryDTO();
        itemDTO.setOrderNo(dto.getTransNo());
        itemDTO.setOrderCategory("3");
        itemDTO.setCreatedBy(userAccount.getUserId());
        itemDTO.setOrderDtlUrl(orderURL);
        return itemDTO;
    }

    private void processChkLogData(AmsAssetsChkLogDTO chkLogDTO, AmsAssetsTransLineDTO line){
        chkLogDTO.setBarcode(line.getBarcode());
        chkLogDTO.setLastChkNo(line.getTransNo());
        chkLogDTO.setHeaderId(line.getTransId());
        chkLogDTO.setResponsibilityUser(line.getResponsibilityUser());
        chkLogDTO.setResponsibilityDept(line.getDeptCode());
        chkLogDTO.setAddressId(line.getAddressId());
        chkLogDTO.setOrganizationId(userAccount.getOrganizationId());
        chkLogDTO.setCreatedBy(userAccount.getUserId());
    }

    /**
     * 功能：根据主键获取资产调拨单据，并补充当前审批角色
     *
     * @return Object
     * @throws QueryException
     */
    public Object getDataByPrimaryKey() throws QueryException {
        Object primaryKeyData = super.getDataByPrimaryKey();
        AmsAssetsTransHeaderDTO data = (AmsAssetsTransHeaderDTO) primaryKeyData;
        data.setCurrRoleName(getCurrRoleName());
        data.setServletConfig(servletConfig);
        primaryKeyData = data;
        return primaryKeyData;
    }

    /**
     * 功能：获取当前的审批角色名称
     *
     * @return String
     * @throws QueryException
     */
    public String getCurrRoleName() throws QueryException {
        String currRoleName = "";
        try {
            OrderApproveModel modelProducer = (OrderApproveModel) sqlProducer;
            SQLModel sqlModel = modelProducer.getCurrApproveRoleModel();
            SimpleQuery simp = new SimpleQuery(sqlModel, conn);
            simp.executeQuery();
            if (simp.hasResult()) {
                currRoleName = simp.getFirstRow().getStrValue("ROLE_NAME");
            }
        } catch (ContainerException ex) {
            ex.printLog();
            throw new QueryException(ex);
        }
        return currRoleName;
    }

    /**
     * 功能：获取附件张数
     *
     * @return String
     * @throws QueryException
     */
    public String getAccessSheet() throws QueryException {
        String accessSheet = "";
        try {
            OrderApproveModel modelProducer = (OrderApproveModel) sqlProducer;
            SQLModel sqlModel = modelProducer.getAccessSheet();
            SimpleQuery simp = new SimpleQuery(sqlModel, conn);
            simp.executeQuery();
            if (simp.hasResult()) {
                accessSheet = simp.getFirstRow().getStrValue("ACCESS_SHEET");
            }
        } catch (ContainerException ex) {
            ex.printLog();
            throw new QueryException(ex);
        }
        return accessSheet;
    }

    /**
     * 功能：获取并更新不能报废的资产，并更新行表REMARK为“不允许报废”；
     */
    private void updateTransLineRemark(String[] barcodes)
            throws DataHandleException {
        OrderApproveModel modelProducer = (OrderApproveModel) sqlProducer;
        String aggBarcodes = initBarcodes(barcodes);
        // SQLModel sqlModel = modelProducer.updateTransLineRemark(aggBarcodes);
        SQLModel sqlModel = modelProducer.updateTransLineRemark();
        DBOperator.updateRecord(sqlModel, conn);
    }

    // 初始化获取的BARCODE
    private String initBarcodes(String[] barcodes) {
        String aggBarcodes = "(";
        for (int i = 0; i < barcodes.length; i++) {
            String barcode = barcodes[i];
            aggBarcodes += "'" + barcode + "', ";
        }
        aggBarcodes += "'aa')";
        // int cc = aggBarcodes.lastIndexOf(",");
        // aggBarcodes = aggBarcodes.substring(0,cc)+")";
        return aggBarcodes;
    }

    /**
     * 功能：特殊处理，先清除选中资产REMARK
     */
    private void deleteTransLineRemark(String[] barcodes)
            throws DataHandleException {
        OrderApproveModel modelProducer = (OrderApproveModel) sqlProducer;
        for (int i = 0; i < barcodes.length; i++) {
            String barcode = barcodes[i];
            SQLModel sqlModel = modelProducer.deleteTransLineRemark(barcode);
            DBOperator.updateRecord(sqlModel, conn);
        }
    }

    public void rejectOrder() {
        boolean operateResult = rejectProcedure();
        if (operateResult) {
            prodMessage(AssetsMessageKeys.REJECT_ORDER_SUCCESS);
        } else {
            prodMessage(AssetsMessageKeys.REJECT_ORDER_FAILURE);
            message.setIsError(true);
        }
        AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
        message.addParameterValue(dto.getTransTypeValue());
        message.addParameterValue(dto.getTransNo());
    }
}
