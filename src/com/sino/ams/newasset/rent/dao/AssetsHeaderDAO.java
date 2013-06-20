package com.sino.ams.newasset.rent.dao;

import com.sino.ams.appbase.dao.AMSProcedureBaseDAO;
import com.sino.ams.bean.OrderNumGenerator;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.dao.AmsAssetsReservedDAO;
import com.sino.ams.newasset.dao.AmsAssetsTransLineDAO;
import com.sino.ams.newasset.dao.AmsItemInfoHistoryDAO;
import com.sino.ams.newasset.dto.AmsAssetsReservedDTO;
import com.sino.ams.newasset.dto.AmsAssetsTransHeaderDTO;
import com.sino.ams.newasset.dto.AmsAssetsTransLineDTO;
import com.sino.ams.newasset.dto.AmsItemInfoHistoryDTO;
import com.sino.ams.newasset.rent.model.AssetsHeaderModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.data.Row;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.util.CalendarUtil;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;

import java.sql.Connection;
import java.sql.SQLException;

public class AssetsHeaderDAO extends AMSProcedureBaseDAO {

    private String msg = "";

    /**
     * 功能：资产业务头表(EAM)--取代原表 AMS_ASSETS_TRANS_HEADER 数据访问层构造函数
     *
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsAssetsTransHeaderDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
    public AssetsHeaderDAO(SfUserDTO userAccount, AmsAssetsTransHeaderDTO dtoParameter, Connection conn) {
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
        sqlProducer = new AssetsHeaderModel((SfUserDTO) userAccount, dtoPara);
    }


    /**
     * 功能：保存资产单据：含调拨，报废，处置
     *
     * @param lineSet DTOSet 资产数据
     * @return boolean
     */
    public boolean saveOrder(DTOSet lineSet) {
        boolean operateResult = false;
        boolean autoCommit = true;
        AmsAssetsTransHeaderDTO dtoPara = (AmsAssetsTransHeaderDTO) dtoParameter;
        String transType = dtoPara.getTransType();
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            saveOrderHeader();
            saveOrderLines(lineSet);
            operateResult = processProcedure(false);
        } catch (SQLException ex) {
            Logger.logError(ex);
        } catch (DataHandleException ex) {
            Logger.logError(ex);
        } finally {
            try {
                if (!operateResult) {
                    if (transType.equals(AssetsDictConstant.ASS_WASTEFORECAST)) {
                        prodMessage(AssetsMessageKeys.ASSETS_WASTEFORECAST_FAILURE);
                        message.addParameterValue("保存");
                    } else {
                        msg = getTransTypeValue(dtoPara) + "(" + dtoPara.getTransNo() + ")" + "保存失败";
                        message.setMessageValue(msg);
                    }
                    conn.rollback();
                } else {
                    if (transType.equals(AssetsDictConstant.ASS_WASTEFORECAST)) {
                        prodMessage(AssetsMessageKeys.ASSETS_WASTEFORECAST_SUCCESS);
                        message.addParameterValue("保存");
                    } else {
                        msg = getTransTypeValue(dtoPara) + "(" + dtoPara.getTransNo() + ")" + "保存成功";
                        message.setMessageValue(msg);
                    }
                    conn.commit();
                }
                conn.setAutoCommit(autoCommit);
                message.setIsError(!operateResult);
            } catch (SQLException ex) {
                Logger.logError(ex);
                prodMessage(AssetsMessageKeys.ROLL_BACK_ERROR);
            }
        }
        return operateResult;
    }

    /**
     * 功能：提交资产单据：含调拨，报废，处置
     *
     * @param lineSet DTOSet 资产数据
     * @return boolean
     */
    public boolean submitOrder(DTOSet lineSet) {
        boolean operateResult = false;
        boolean autoCommit = true;
        AmsAssetsTransHeaderDTO dtoPara = (AmsAssetsTransHeaderDTO) dtoParameter;
        String transType = dtoPara.getTransType();
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            saveOrderHeader();
            saveOrderLines(lineSet);
            if (dtoPara.isFlowOver()) {
                deleteReserveAssets();
                updateAssets();
                recordItemHistory(lineSet);
            }
            operateResult = processProcedure(true);
        } catch (SQLException ex) {
            Logger.logError(ex);
        } catch (DataHandleException ex) {
            Logger.logError(ex);
        } finally {
            try {
                if (!operateResult) {
                    conn.rollback();
                    msg = getTransTypeValue(dtoPara) + "(" + dtoPara.getTransNo() + ")" + "提交失败";
                    message.setIsError(false);
                } else {
                    conn.commit();
                    msg = getTransTypeValue(dtoPara) + "(" + dtoPara.getTransNo() + ")" + "提交成功";
                }
                message.setMessageValue(msg);
                conn.setAutoCommit(autoCommit);
                message.setIsError(!operateResult);
            } catch (SQLException ex) {
                Logger.logError(ex);
                prodMessage(AssetsMessageKeys.ROLL_BACK_ERROR);
            }
        }
        return operateResult;
    }

    private String getTransTypeValue(AmsAssetsTransHeaderDTO dtoPara) {
        String transTypeValue = dtoPara.getTransTypeValue();
        if (dtoPara.getTransType().equals("ASS-SELL") || dtoPara.getTransType().equals("ASS-DONATE")) {
            transTypeValue = "销售单";
        }

        return transTypeValue;
    }

    private void recordItemHistory(DTOSet lineSet) {
        if (lineSet != null && !lineSet.isEmpty()) {
            AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
            AmsItemInfoHistoryDAO historyDAO = new AmsItemInfoHistoryDAO(userAccount, null, conn);
            String orderURL = "/servlet/com.sino.ams.newasset.rent.servlet.AssetsHeaderServlet";
            orderURL += "?act=DETAIL_ACTION";
            orderURL += "&transId=" + dto.getTransId();
            for (int i = 0; i < lineSet.getSize(); i++) {
                AmsAssetsTransLineDTO lineData = (AmsAssetsTransLineDTO) lineSet.getDTO(i);
                AmsItemInfoHistoryDTO historyDTO = new AmsItemInfoHistoryDTO();

                historyDTO.setBarcode(lineData.getBarcode());
                historyDTO.setOrderCategory("3");
                historyDTO.setOrderDtlUrl(orderURL);
                historyDTO.setRemark(dto.getTransTypeValue());
                historyDTO.setOrderNo(dto.getTransNo());
                historyDTO.setCreatedBy(userAccount.getUserId());

                historyDAO.setDTOParameter(historyDTO);
                historyDAO.recordHistory();
            }
        }
    }


    /**
     * 功能：保存单据头信息
     *
     * @return String
     * @throws com.sino.base.exception.DataHandleException
     *
     */
    private void saveOrderHeader() throws DataHandleException {
        try {
            AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) dtoParameter;
            String transNo = headerDTO.getTransNo();
            headerDTO.setFromPerson(userAccount.getEmployeeNumber());
            String transType = headerDTO.getTransType();
            if (headerDTO.isFlowOver()) {
                headerDTO.setTransStatus(AssetsDictConstant.APPROVED);
                headerDTO.setTransDate(CalendarUtil.getCurrCalendar());
            }
            if (transNo.equals(AssetsWebAttributes.ORDER_AUTO_PROD)) {
                String transId = headerDTO.getTransId();
                if (StrUtil.isEmpty(transId)) {
                    SeqProducer seq = new SeqProducer(conn);
                    transId = seq.getGUID();
                    headerDTO.setTransId(transId);
                }
                String companyCode = userAccount.getCompanyCode(); //还是采用该方法，以下考虑周子君认为没必要
                OrderNumGenerator numberProducer = new OrderNumGenerator(conn, companyCode, transType);
                headerDTO.setTransNo(numberProducer.getOrderNum());
                setDTOParameter(headerDTO);
                createData();
                if (!"ASS-WASTEFORECAST".equals(transType) || !"ASS-REPAIR".equals(transType)) {
                    deleteReserveAssets();
                }
            } else {
                updateData();
                deleteLines();
                deleteReserveAssets();
            }
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex.getMessage());
        }
    }

    protected void prepareProcedureData() {
        AmsAssetsTransHeaderDTO flowDTO = (AmsAssetsTransHeaderDTO) dtoParameter;
        flowDTO.setPrimaryKey(flowDTO.getTransId());
        String transType = flowDTO.getTransType();
        flowDTO.setOrderNo(flowDTO.getTransNo());
        if ("ASS-RENT".equals(transType)) { //出租
            flowDTO.setOrderName(AssetsDictConstant.PROCEDURE_NAME_RENT);
        } else if ("ASS-DONATE".equals(transType)) { //捐赠
            flowDTO.setOrderName(AssetsDictConstant.PROCEDURE_NAME_DONA);
        } else if ("ASS-REPAIRRETURN".equals(transType)) { //送修返还
            flowDTO.setOrderName(AssetsDictConstant.PROCEDURE_NAME_REPAIRRETURN);
        } else if ("ASS-REPAIR".equals(transType)) { //送修
            flowDTO.setOrderName(AssetsDictConstant.PROCEDURE_NAME_REPAIR);
        } else if ("ASS-SELL".equals(transType)) { //销售
            flowDTO.setOrderName(AssetsDictConstant.PROCEDURE_NAME_SELL);
        } else if ("ASS-WASTEFORECAST".equals(transType)) { //预报废
            flowDTO.setOrderName(AssetsDictConstant.PROCEDURE_NAME_WASTEFORECAST);
        } else if ("ASS-EXCHANGE".equals(transType)) { //置换
            flowDTO.setOrderName(AssetsDictConstant.PROCEDURE_NAME_REPLACEMENT);
        } else if ("ASS-BORROW".equals(transType)) { //借用
            flowDTO.setOrderName(AssetsDictConstant.PROCEDURE_NAME_BORROW);
        } else if ("ASS-RETURN".equals(transType)) { //送还
            flowDTO.setOrderName(AssetsDictConstant.PROCEDURE_NAME_RETURN);
        } else if ("ASS-REPEAL".equals(transType)) { //预报废撤销
            flowDTO.setOrderName(AssetsDictConstant.PROCEDURE_NAME_REPEAL);
        }
    }

    public boolean rejectOrder() {
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
        return operateResult;
    }

    /**
     * 功能：保存单据行信息
     *
     * @param lineSet DTOSet
     * @throws com.sino.base.exception.DataHandleException
     *
     */
    private void saveOrderLines(DTOSet lineSet) throws
            DataHandleException {
        if (lineSet != null && !lineSet.isEmpty()) {
            AmsAssetsTransHeaderDTO orderDTO = (AmsAssetsTransHeaderDTO) dtoParameter;
            AmsAssetsTransLineDAO lineDAO = new AmsAssetsTransLineDAO(userAccount, null, conn);
            for (int i = 0; i < lineSet.getSize(); i++) {
                AmsAssetsTransLineDTO lineData = (AmsAssetsTransLineDTO) lineSet.getDTO(i);
                if (lineData.getBarcode().equals("")) {
                    continue;
                }
                lineData.setTransId(orderDTO.getTransId());
                lineData.setLineStatus(orderDTO.getTransStatus());
                if (lineData.getOldResponsibilityDept().equals("")) {
                    lineData.setOldResponsibilityDept(StrUtil.nullToString(orderDTO.getFromDept()));
                }
                lineDAO.setDTOParameter(lineData);
                lineDAO.createData();
                createReserveAssets(lineData.getBarcode()); //保留资产
            }
        }
    }

    /**
     * 功能：删除单据的行信息
     *
     * @throws com.sino.base.exception.DataHandleException
     *
     */
    private void deleteLines() throws DataHandleException {
        AmsAssetsTransLineDTO lineDTO = new AmsAssetsTransLineDTO();
        AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) dtoParameter;
        lineDTO.setTransId(headerDTO.getTransId());
        AmsAssetsTransLineDAO lineDAO = new AmsAssetsTransLineDAO(userAccount, lineDTO, conn);
        lineDAO.DeleteByForeignKey("transId");
    }

    /**
     * 功能：删除本单据保留的资产
     *
     * @throws com.sino.base.exception.DataHandleException
     *
     */
    private void deleteReserveAssets() throws DataHandleException {
        AmsAssetsReservedDTO reserveDTO = new AmsAssetsReservedDTO();
        AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) dtoParameter;
        reserveDTO.setTransId(headerDTO.getTransId());
        AmsAssetsReservedDAO reserveDAO = new AmsAssetsReservedDAO(userAccount, reserveDTO, conn);
        reserveDAO.DeleteByForeignKey("transId");
    }

    /**
     * 更改资产状态
     *
     * @throws DataHandleException
     */
    private void updateAssets() throws DataHandleException {
        AssetsHeaderModel headerModel = (AssetsHeaderModel) sqlProducer;
        AmsAssetsTransHeaderDTO batchDTO = (AmsAssetsTransHeaderDTO) dtoParameter;
        SQLModel sqlModel = headerModel.getUpdateAssetsModel(batchDTO.getTransId(), batchDTO.getTransType(), batchDTO.getTransTypeValue());
        DBOperator.updateRecord(sqlModel, conn);
    }

    /**
     * 功能：保留资产
     *
     * @param batrcode String
     * @throws com.sino.base.exception.DataHandleException
     *
     */
    private void createReserveAssets(String batrcode) throws
            DataHandleException {
        AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO)
                dtoParameter;
        AmsAssetsReservedDTO reserveDTO = new AmsAssetsReservedDTO();
        reserveDTO.setTransId(headerDTO.getTransId());
        reserveDTO.setBarcode(batrcode);
        reserveDTO.setCurrCalendar("reservedDate");
        AmsAssetsReservedDAO reserveDAO = new AmsAssetsReservedDAO(userAccount,
                reserveDTO, conn);
        reserveDAO.createData();
    }

    /**
     * 撤销
     *
     * @return
     */
    public boolean cancelOrders() {
        boolean operateResult = false;
        boolean autoCommit = true;
        AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) dtoParameter;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);

            cancelOrderHeader();
            cancelOrderLines();
            deleteReserveAssets();

            operateResult = cancelProcedure();
        } catch (Throwable ex) {
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
                message.addParameterValue(headerDTO.getTransTypeValue());
                message.setIsError(!operateResult);
            } catch (SQLException ex1) {
                Logger.logError(ex1);
                prodMessage(AssetsMessageKeys.ROLL_BACK_ERROR);
            }
        }
        return operateResult;
    }


    /**
     * 功能：撤销单据头
     *
     * @throws DataHandleException
     */
    private void cancelOrderHeader() throws DataHandleException {
        AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) dtoParameter;
        AssetsHeaderModel modelProducer = (AssetsHeaderModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getOrderCancelModel(headerDTO.getTransId()); //撤销单据
        DBOperator.updateRecord(sqlModel, conn);
    }

    /**
     * 功能：撤销单据行
     *
     * @throws DataHandleException
     */
    private void cancelOrderLines() throws DataHandleException {
        AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) dtoParameter;
        AmsAssetsTransLineDTO lineDTO = new AmsAssetsTransLineDTO();
        lineDTO.setTransId(headerDTO.getTransId());
        AmsAssetsTransLineDAO lineDAO = new AmsAssetsTransLineDAO(userAccount, lineDTO, conn);
        lineDAO.cancelLinesByHeader();

    }

    /**
     * 功能：查找用户所对应的PID，流程用
     */
    public boolean isGroupFlowId() throws QueryException {
        AssetsHeaderModel modelProducer = (AssetsHeaderModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getGroupPidModel();
        SimpleQuery simp = new SimpleQuery(sqlModel, conn);
        simp.executeQuery();
        return simp.hasResult();
    }

    /**
     * 判断是否是上市公司
     *
     * @return
     * @throws QueryException
     */
    public String isTd() throws QueryException {
        AssetsHeaderModel modelProducer = (AssetsHeaderModel) sqlProducer;
        String isTd = "";
        SQLModel sqlModel = modelProducer.isTD();
        SimpleQuery simp = new SimpleQuery(sqlModel, conn);
        simp.executeQuery();
        if (simp.hasResult()) {
            Row row = simp.getFirstRow();
            try {
                isTd = row.getStrValue("IS_TD");
            } catch (ContainerException e) {
                e.printStackTrace();
            }
        }
        return isTd;
    }

    //判断是否财务部
    public boolean isFinanceGroup() throws QueryException {
        AssetsHeaderModel modelProducer = (AssetsHeaderModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getIsFinanceGroupModel();
        SimpleQuery simp = new SimpleQuery(sqlModel, conn);
        simp.executeQuery();
        return simp.hasResult();
    }

    /**
     * Function:        判断用户当前建单组别是否专业组别
     * author:         李轶
     * date            2009-08-25
     * param           //fromGroupName
     *
     * @return boolean类型
     * @throws com.sino.base.exception.QueryException
     *
     */
    public boolean isProfessionalGroup(String fromGroup) throws QueryException {
        AssetsHeaderModel modelProducer = (AssetsHeaderModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getIsGroupFlowIdModel(fromGroup);
        SimpleQuery simp = new SimpleQuery(sqlModel, conn);
        simp.executeQuery();
        return simp.hasResult();
    }

    public String findGroupFlowId() throws QueryException {
        String GroupPid = "";
        try {
            AssetsHeaderModel modelProducer = (AssetsHeaderModel) sqlProducer;
            SQLModel sqlModel = modelProducer.getGroupPidModel();
            SimpleQuery simp = new SimpleQuery(sqlModel, conn);
            simp.executeQuery();
            if (simp.hasResult()) {
                Row row = simp.getFirstRow();
                GroupPid = row.getStrValue("P_FLOW_ID");
            }
        } catch (ContainerException ex) {
            ex.printLog();
            throw new QueryException(ex);
        }
        return GroupPid;
    }

    public boolean findThredDept(String fDept, String tDept) throws QueryException {
        boolean isThredDept = false;
        try {
            AssetsHeaderModel modelProducer = (AssetsHeaderModel) sqlProducer;
            SQLModel sqlModel = modelProducer.getThredDeptModel(fDept, tDept);
            SimpleQuery simp = new SimpleQuery(sqlModel, conn);
            simp.executeQuery();
            if (simp.hasResult()) {
                Row row = simp.getFirstRow();
                String thredDept = row.getStrValue("IS_THRED");
                if (!thredDept.equals("0")) {
                    isThredDept = true;
                } else {
                    isThredDept = false;
                }
            }
        } catch (ContainerException ex) {
            ex.printLog();
            throw new QueryException(ex);
        }
        return isThredDept;
    }

    //判断是否实物部门
    public boolean isSpecialGroup(int fromGroup) throws QueryException {
        AssetsHeaderModel modelProducer = (AssetsHeaderModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getIsSpecialGroupModel(fromGroup);
        SimpleQuery simp = new SimpleQuery(sqlModel, conn);
        simp.executeQuery();
        return simp.hasResult();
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
