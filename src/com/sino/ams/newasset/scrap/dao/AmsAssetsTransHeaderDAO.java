package com.sino.ams.newasset.scrap.dao;

import com.sino.ams.appbase.dao.AMSProcedureBaseDAO;
import com.sino.ams.bean.OrderNumGenerator;
import com.sino.ams.newasset.constant.*;
import com.sino.ams.newasset.dao.AmsAssetsChkLogDAO;
import com.sino.ams.newasset.dao.AmsAssetsReservedDAO;
import com.sino.ams.newasset.dao.AmsAssetsTransLineDAO;
import com.sino.ams.newasset.dao.AmsItemInfoHistoryDAO;
import com.sino.ams.newasset.dto.*;
import com.sino.ams.newasset.lease.constant.LeaseAppConstant;
import com.sino.ams.newasset.scrap.constant.ScrapAppConstant;
import com.sino.ams.newasset.scrap.model.TransHeaderModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.data.Row;
import com.sino.base.db.datatrans.*;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title: AmsAssetsTransHeaderDAO</p>
 * <p>Description:程序自动生成服务程序“AmsAssetsTransHeaderDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */
public class AmsAssetsTransHeaderDAO extends AMSProcedureBaseDAO {
    private AmsAssetsTransHeaderDTO headerDTO = null;
    private TransHeaderModel transHeaderModel = null;
    private String msg = null;

    /**
     * 功能：资产业务头表(EAM)--取代原表 AMS_ASSETS_TRANS_HEADER 数据访问层构造函数
     *
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsAssetsTransHeaderDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
    public AmsAssetsTransHeaderDAO(SfUserDTO userAccount, AmsAssetsTransHeaderDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        this.initSQLProducer(userAccount, dtoParameter);
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     *
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        headerDTO = (AmsAssetsTransHeaderDTO) dtoParameter;
        transHeaderModel = new TransHeaderModel((SfUserDTO) userAccount, headerDTO);
        sqlProducer = transHeaderModel;
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
            operateResult = processProcedure();
        } catch (SQLException ex) {
            Logger.logError(ex);
        } catch (DataHandleException ex) {
            Logger.logError(ex);
        } finally {
            try {
                if (!operateResult) {
                    conn.rollback();
                    if (headerDTO.getAct().equals(AssetsActionConstant.SUBMIT_ACTION)) {
                        msg = "报废单(" + headerDTO.getTransNo() + ")提交失败";
                    } else {
                        msg = "报废单(" + headerDTO.getTransNo() + ")保存失败";
                    }
                    if (transType.equals(AssetsDictConstant.ASS_RED)) {
                        prodMessage(AssetsMessageKeys.ASSETS_TRANSFER_FAILURE);
                    } else if (transType.equals(AssetsDictConstant.ASS_DIS)) {
                        prodMessage(AssetsMessageKeys.ASSETS_DISCARD_FAILURE);
                    } else if (transType.equals(AssetsDictConstant.ASS_CLR)) {
                        prodMessage(AssetsMessageKeys.ASSETS_CLEAR_FAILURE);
                    } else if (transType.equals(AssetsDictConstant.ASS_FREE)) {
                        prodMessage(AssetsMessageKeys.ASSETS_FREE_FAILURE);
                    } else if (transType.equals(AssetsDictConstant.ASS_SUB)) {
                        prodMessage(AssetsMessageKeys.ASSETS_SUB_FAILURE);
                    }
                } else {
                    conn.commit();
                    if (headerDTO.getAct().equals(AssetsActionConstant.SUBMIT_ACTION)) {
                        msg = "报废单(" + headerDTO.getTransNo() + ")提交成功";
                    } else {
                        msg = "报废单(" + headerDTO.getTransNo() + ")保存成功";
                    }
                    if (transType.equals(AssetsDictConstant.ASS_RED)) {
                        prodMessage(AssetsMessageKeys.ASSETS_TRANSFER_SUCCESS);
                    } else if (transType.equals(AssetsDictConstant.ASS_DIS)) {
                        prodMessage(AssetsMessageKeys.ASSETS_DISCARD_SUCCESS);
                    } else if (transType.equals(AssetsDictConstant.ASS_CLR)) {
                        prodMessage(AssetsMessageKeys.ASSETS_CLEAR_SUCCESS);
                    } else if (transType.equals(AssetsDictConstant.ASS_FREE)) {
                        prodMessage(AssetsMessageKeys.ASSETS_FREE_SUCCESS);
                    } else if (transType.equals(AssetsDictConstant.ASS_SUB)) {
                        prodMessage(AssetsMessageKeys.ASSETS_SUB_SUCCESS);
                    }
                }
                conn.setAutoCommit(autoCommit);
                message.addParameterValue("保存");
                message.setIsError(!operateResult);
            } catch (SQLException ex) {
                Logger.logError(ex);
                prodMessage(AssetsMessageKeys.ROLL_BACK_ERROR);
            }
        }
        return operateResult;
    }

    public boolean doDelete(DTOSet lineSet) {
        boolean operateResult = false;
        boolean autoCommit = true;
        AmsAssetsTransHeaderDTO dtoPara = (AmsAssetsTransHeaderDTO) dtoParameter;
        String transType = dtoPara.getTransType();
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            deleteLines();
            deleteReserveAssets();
            saveOrderLines(lineSet);
            operateResult = processProcedure(true);
        } catch (SQLException ex) {
            Logger.logError(ex);
        } catch (DataHandleException ex) {
            Logger.logError(ex);
        } finally {
            try {
                if (!operateResult) {
                    conn.rollback();
                    if (transType.equals(AssetsDictConstant.ASS_RED)) {
                        prodMessage(AssetsMessageKeys.ASSETS_TRANSFER_FAILURE);
                    } else if (transType.equals(AssetsDictConstant.ASS_DIS)) {
                        prodMessage(AssetsMessageKeys.ASSETS_DISCARD_FAILURE);
                    } else if (transType.equals(AssetsDictConstant.ASS_CLR)) {
                        prodMessage(AssetsMessageKeys.ASSETS_CLEAR_FAILURE);
                    } else if (transType.equals(AssetsDictConstant.ASS_FREE)) {
                        prodMessage(AssetsMessageKeys.ASSETS_FREE_FAILURE);
                    } else if (transType.equals(AssetsDictConstant.ASS_SUB)) {
                        prodMessage(AssetsMessageKeys.ASSETS_SUB_FAILURE);
                    }
                } else {
                    conn.commit();
                    if (transType.equals(AssetsDictConstant.ASS_RED)) {
                        prodMessage(AssetsMessageKeys.ASSETS_TRANSFER_SUCCESS);
                    } else if (transType.equals(AssetsDictConstant.ASS_DIS)) {
                        prodMessage(AssetsMessageKeys.ASSETS_DISCARD_SUCCESS);
                    } else if (transType.equals(AssetsDictConstant.ASS_CLR)) {
                        prodMessage(AssetsMessageKeys.ASSETS_CLEAR_SUCCESS);
                    } else if (transType.equals(AssetsDictConstant.ASS_FREE)) {
                        prodMessage(AssetsMessageKeys.ASSETS_FREE_SUCCESS);
                    } else if (transType.equals(AssetsDictConstant.ASS_SUB)) {
                        prodMessage(AssetsMessageKeys.ASSETS_SUB_SUCCESS);
                    }
                }
                conn.setAutoCommit(autoCommit);
                message.addParameterValue("删除");
                message.setIsError(!operateResult);
            } catch (SQLException ex) {
                Logger.logError(ex);
                prodMessage(AssetsMessageKeys.ROLL_BACK_ERROR);
            }
        }
        return operateResult;
    }

    /**
     * 功能：保存单据行信息
     *
     * @param lineSet DTOSet
     * @throws DataHandleException
     */
    private void saveOrderLines(DTOSet lineSet) throws
            DataHandleException {
        if (lineSet != null && !lineSet.isEmpty()) {
            AmsAssetsTransHeaderDTO orderDTO = (AmsAssetsTransHeaderDTO) dtoParameter;
            AmsAssetsTransLineDAO lineDAO = new AmsAssetsTransLineDAO(userAccount, null, conn);
            String transferType = orderDTO.getTransferType();
            String transId = orderDTO.getTransId();
            for (int i = 0; i < lineSet.getSize(); i++) {
                AmsAssetsTransLineDTO lineData = (AmsAssetsTransLineDTO) lineSet.getDTO(i);
                if (lineData.getBarcode().equals("")) {
                    continue;
                }
                lineData.setTransId(transId);
                lineData.setLineStatus(orderDTO.getTransStatus());
                if (lineData.getOldResponsibilityDept().equals("")) {
                    lineData.setOldResponsibilityDept(StrUtil.nullToString(orderDTO.getFromDept()));
                }
                if (transferType.equals(AssetsDictConstant.TRANS_INN_DEPT)) { //部门内调拨
                    lineData.setResponsibilityDept(StrUtil.nullToString(orderDTO.getFromDept()));
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
     * @throws DataHandleException
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
     * @throws DataHandleException
     */
    private void deleteReserveAssets() throws DataHandleException {
        AmsAssetsReservedDTO reserveDTO = new AmsAssetsReservedDTO();
        AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) dtoParameter;
        reserveDTO.setTransId(headerDTO.getTransId());
        AmsAssetsReservedDAO reserveDAO = new AmsAssetsReservedDAO(userAccount, reserveDTO, conn);
        reserveDAO.DeleteByForeignKey("transId");
    }

    /**
     * 功能：保留资产
     *
     * @param batrcode String
     * @throws DataHandleException
     */
    private void createReserveAssets(String batrcode) throws DataHandleException {
        AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) dtoParameter;
        AmsAssetsReservedDTO reserveDTO = new AmsAssetsReservedDTO();
        reserveDTO.setTransId(headerDTO.getTransId());
        reserveDTO.setBarcode(batrcode);
        reserveDTO.setCurrCalendar("reservedDate");
        AmsAssetsReservedDAO reserveDAO = new AmsAssetsReservedDAO(userAccount, reserveDTO, conn);
        reserveDAO.createData();
    }

    /**
     * 功能：撤销暂存的单据
     *
     * @return boolean
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
            deleteReservedAssets();
            operateResult = super.cancelProcedure();
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
        TransHeaderModel modelProducer = (TransHeaderModel) sqlProducer;
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
     * 功能：撤销保留资产
     *
     * @throws DataHandleException
     */
    private void deleteReservedAssets() throws DataHandleException {
        AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) dtoParameter;
        AmsAssetsReservedDTO reserveDTO = new AmsAssetsReservedDTO();
        reserveDTO.setTransId(headerDTO.getTransId()); //删除保留数据
        AmsAssetsReservedDAO reserveDAO = new AmsAssetsReservedDAO(userAccount, reserveDTO, conn);
        reserveDAO.DeleteByForeignKey("transId");
    }


    public File exportFile() throws DataTransException { //导出模板
        File file = null;
        DataTransfer transfer = null;
        TransHeaderModel modelProducer = (TransHeaderModel)
                sqlProducer;
        SQLModel sqlModel = modelProducer.getOrderModel();
        TransRule rule = new TransRule();
        rule.setDataSource(sqlModel);
        rule.setCalPattern(CalendarConstant.LINE_PATTERN);
        rule.setSourceConn(conn);

        AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO)
                dtoParameter;
        String transferType = headerDTO.getTransferType();
        String fileName = "";
        if (transferType.equals(AssetsDictConstant.TRANS_INN_DEPT)) { //部门内调拨
            fileName = "部门内调拨.xls";
        } else if (transferType.equals(AssetsDictConstant.TRANS_BTW_DEPT)) { //部门内调拨
            fileName = "部门间调拨.xls";
        } else if (transferType.equals(AssetsDictConstant.TRANS_BTW_COMP)) { //地市间调拨
            fileName = "地市间调拨.xls";
        }

        String filePath = WorldConstant.USER_HOME;
        filePath += WorldConstant.FILE_SEPARATOR;
        filePath += fileName;
        rule.setTarFile(filePath);
        DataRange range = new DataRange();
        rule.setDataRange(range);

        Map fieldMap = new HashMap();
        fieldMap.put("MB1", "资产标签");
        fieldMap.put("MB2", "资产编号");
        fieldMap.put("MB3", "资产名称");
        fieldMap.put("MB4", "资产型号");
        fieldMap.put("MB5", "数量");
        if (transferType.equals(AssetsDictConstant.TRANS_INN_DEPT)) {
            fieldMap.put("MB6", "调出地点NO");
            fieldMap.put("MB7", "调出地点");
            fieldMap.put("MB8", "原责任人员工ID");
            fieldMap.put("MB9", "原责任人");
            fieldMap.put("MB10", "调入地点NO");
            fieldMap.put("MB11", "调入地点");
            fieldMap.put("MB12", "新责任人员工ID");
            fieldMap.put("MB13", "新责任人");
            fieldMap.put("MB14", "调动日期");
            fieldMap.put("MB15", "摘要");
        } else if (transferType.equals(AssetsDictConstant.TRANS_BTW_DEPT)) {
            fieldMap.put("MB6", "调出地点NO");
            fieldMap.put("MB7", "调出地点");
            fieldMap.put("MB8", "原责任人员工ID");
            fieldMap.put("MB9", "原责任人");
            fieldMap.put("MB10", "调入部门代码");
            fieldMap.put("MB11", "调入部门");
            fieldMap.put("MB12", "调入地点N0");
            fieldMap.put("MB13", "调入地点");
            fieldMap.put("MB14", "新责任人员工ID");
            fieldMap.put("MB15", "新责任人");
            fieldMap.put("MB16", "调动日期");
            fieldMap.put("MB17", "备注");
        } else if (transferType.equals(AssetsDictConstant.TRANS_BTW_COMP)) {
            fieldMap.put("MB6", "原值");
            fieldMap.put("MB7", "累计折旧");
            fieldMap.put("MB8", "残值");
            fieldMap.put("MB9", "启用日期");
            fieldMap.put("MB10", "调出部门代码");
            fieldMap.put("MB11", "调出部门");
            fieldMap.put("MB12", "调出地点NO");
            fieldMap.put("MB13", "调出地点");
            fieldMap.put("MB14", "原责任人员工ID");
            fieldMap.put("MB15", "原责任人");
            fieldMap.put("MB16", "原折旧账户");
            fieldMap.put("MB17", "原类别");
            fieldMap.put("MB18", "调入部门代码");
            fieldMap.put("MB19", "调入部门");
            fieldMap.put("MB20", "调入地点NO");
            fieldMap.put("MB21", "调入地点");
            fieldMap.put("MB22", "新责任人员工ID");
            fieldMap.put("MB23", "新责任人");
            fieldMap.put("MB24", "新折旧账户");
            fieldMap.put("MB25", "新类别");
            fieldMap.put("MB26", "调动日期");
            fieldMap.put("MB27", "备注");
        }

        rule.setFieldMap(fieldMap);
        CustomTransData custData = new CustomTransData();
//            custData.setReportTitle(fileName);
//            custData.setReportPerson(sfUser.getUsername());
        custData.setNeedReportDate(false);
        rule.setCustData(custData);

        TransferFactory factory = new TransferFactory();
        transfer = factory.getTransfer(rule);
        transfer.transData();
        file = (File) transfer.getTransResult();
        return file;
    }

    /**
     * 功能：查找用户所对应的PID，流程用
     */
    public boolean isGroupFlowId() throws QueryException {
        TransHeaderModel modelProducer = (TransHeaderModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getGroupPidModel();
        SimpleQuery simp = new SimpleQuery(sqlModel, conn);
        simp.executeQuery();
        return simp.hasResult();
    }

    //判断是否财务部
    public boolean isFinanceGroup() throws QueryException {
        TransHeaderModel modelProducer = (TransHeaderModel) sqlProducer;
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
     * @throws QueryException
     */
    public boolean isProfessionalGroup(String fromGroup) throws QueryException {
        TransHeaderModel modelProducer = (TransHeaderModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getIsGroupFlowIdModel(fromGroup);
        SimpleQuery simp = new SimpleQuery(sqlModel, conn);
        simp.executeQuery();
        return simp.hasResult();
    }

    public String findGroupFlowId() throws QueryException {
        String GroupPid = "";
        try {
            TransHeaderModel modelProducer = (TransHeaderModel) sqlProducer;
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


    /**
     * 查找部门
     *
     * @param fDept
     * @param tDept
     * @return
     * @throws QueryException
     */
    public boolean findThredDept(String fDept, String tDept) throws QueryException {
        boolean isThredDept = false;
        try {
            TransHeaderModel modelProducer = (TransHeaderModel) sqlProducer;
            SQLModel sqlModel = modelProducer.getThredDeptModel(fDept, tDept);
            SimpleQuery simp = new SimpleQuery(sqlModel, conn);
            simp.executeQuery();
            if (simp.hasResult()) {
                Row row = simp.getFirstRow();
                String thredDept = row.getStrValue("IS_THRED");
//                if (thredDept.equals("2")) {
//                    isThredDept = true;
//                }
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


    /**
     * 保存头信息
     *
     * @return
     * @throws DataHandleException
     */
    private boolean saveOrderHeader() throws DataHandleException {
        boolean isSuccess = false;
        try {
            String transId = headerDTO.getTransId();
            String transNo = headerDTO.getTransNo();
            headerDTO.setFromPerson(userAccount.getEmployeeNumber());
            String act = headerDTO.getAct();
            if (transNo.equals(AssetsWebAttributes.ORDER_AUTO_PROD)) {
                if (StrUtil.isEmpty(transId)) {
                    SeqProducer seq = new SeqProducer(conn);
                    transId = seq.getGUID();
                    headerDTO.setTransId(transId);
                }
                String companyCode = userAccount.getCompanyCode(); //还是采用该方法，以下考虑周子君认为没必要
                String transType = headerDTO.getTransType();
                OrderNumGenerator numberProducer = new OrderNumGenerator(conn, companyCode, transType);
                headerDTO.setTransNo(numberProducer.getOrderNum());
                setDTOParameter(headerDTO);
                createData();
            } else {
                updateData();
                deleteLines();
                deleteReserveAssets();
            }
            isSuccess = true;
        } catch (SQLException ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex);
        } finally {
            return isSuccess;
        }
    }


    private boolean saveNewOrderHeader() throws DataHandleException {
        boolean isSuccess = false;
        try {
            String transId = headerDTO.getTransId();
            String transNo = headerDTO.getTransNo();
            headerDTO.setFromPerson(userAccount.getEmployeeNumber());

            if (transNo.equals(AssetsWebAttributes.ORDER_AUTO_PROD)) {
                if (StrUtil.isEmpty(transId)) {
                    SeqProducer seq = new SeqProducer(conn);
                    transId = seq.getGUID();
                    headerDTO.setTransId(transId);
                }
                String companyCode = userAccount.getCompanyCode(); //还是采用该方法，以下考虑周子君认为没必要
                String transType = headerDTO.getTransType();
                OrderNumGenerator numberProducer = new OrderNumGenerator(conn, companyCode, transType);
                headerDTO.setTransNo(numberProducer.getOrderNum());
                setDTOParameter(headerDTO);
                createData();

            } else {
                updateData();
                deleteLines();
                deleteReserveAssets();
            }
            isSuccess = true;
        } catch (SQLException ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex);
        } finally {
            return isSuccess;
        }
    }

    /**
     * 更新头状态
     *
     * @throws DataHandleException
     */
    public void updateHeaderStatus() throws DataHandleException {
        SQLModel sqlModel = transHeaderModel.getOrderApproveModel();
        DBOperator.updateRecord(sqlModel, conn);
    }

    /**
     * 更新行状态
     *
     * @throws DataHandleException
     */
    public void updateLineStatus() throws DataHandleException {
        SQLModel sqlModel = transHeaderModel.getLineStatusUpdateModel(); //更新资产单据行数据状态为已审批
        DBOperator.updateRecord(sqlModel, conn);
    }

    /**
     * 更新行状态
     *
     * @throws DataHandleException
     */
    public void updateAssetsDiscard() throws DataHandleException {
        SQLModel sqlModel = transHeaderModel.getAssetsDiscardModel(); //获取资产报废SQL
        DBOperator.updateRecord(sqlModel, conn);
    }


    /**
     * 功能：记录设备最新一次交易情况：例如报废，其他的视具体需要而定
     * 需要同步到MIS的时候调用该方法。
     *
     * @param orderType String
     * @param isExist   String
     * @throws DataHandleException
     */
    private void recordChkLog(String orderType, String isExist) throws DataHandleException {
        try {
            AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
            AmsAssetsTransLineDTO line = new AmsAssetsTransLineDTO();
            line.setTransId(dto.getTransId());
            AmsAssetsTransLineDAO lineDAO = new AmsAssetsTransLineDAO(userAccount, line, conn);
            lineDAO.setDTOClassName(AmsAssetsTransLineDTO.class.getName());
            DTOSet dtos = (DTOSet) lineDAO.getDataByForeignKey("transId");
            if (dtos != null && !dtos.isEmpty()) {
                int lineCount = dtos.getSize();
                String orderUrl = "";
                AmsAssetsChkLogDTO chkLogDTO = null;
                AmsAssetsChkLogDAO chkLogDAO = new AmsAssetsChkLogDAO(userAccount, null, conn);
                for (int i = 0; i < lineCount; i++) {
                    line = (AmsAssetsTransLineDTO) dtos.getDTO(i);
                    chkLogDTO = new AmsAssetsChkLogDTO();
                    chkLogDTO.setBarcode(line.getBarcode());
                    chkLogDTO.setLastChkNo(line.getTransNo());
                    chkLogDTO.setHeaderId(line.getTransId());
                    chkLogDTO.setResponsibilityUser(line.getResponsibilityUser());
                    chkLogDTO.setResponsibilityDept(line.getDeptCode());
                    chkLogDTO.setAddressId(line.getAddressId());
                    chkLogDTO.setOrganizationId(userAccount.getOrganizationId());
                    chkLogDTO.setCreatedBy(userAccount.getUserId());
                    chkLogDTO.setOrderType(orderType);
                    chkLogDTO.setIsExist(isExist);
                    orderUrl = AssetsURLList.ASSETS_TRANS_SERVLET;
                    orderUrl += "?act=" + AssetsActionConstant.DETAIL_ACTION;
                    orderUrl += "&transId=" + line.getTransId();
                    chkLogDTO.setOrderDtlUrl(orderUrl);
                    chkLogDAO.setDTOParameter(chkLogDTO);
                    chkLogDAO.saveCheckLogData();
                }
            }
        } catch (QueryException ex) {
            ex.printLog();
            throw new DataHandleException(ex);
        }
    }


    /**
     * 提交事件
     *
     * @param lineSet
     * @return
     */
    public boolean doSave(DTOSet lineSet) {
        String att3 = headerDTO.getSf_task_attribute3();
        if (att3.equals(ScrapAppConstant.ATT3_FILL_DATA)) { //起草节点
            return this.saveOrder(lineSet);
        }//流程结束
        else if (att3.equals(ScrapAppConstant.ATT3_APPROVING)) { //审批节点
            return this.doApprove(lineSet);
        } else {
            return false;
        }
    }

    /**
     * 审批
     *
     * @return
     * @throws SQLException
     */
    private boolean doApprove(DTOSet lineSet) {
        boolean isSuccess = false;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            if (headerDTO.isFlowOver()) {
                headerDTO.setTransStatus(AssetsDictConstant.COMPLETED); //DISCARDED
                this.updateHeaderStatus(); //更新头状态
                this.updateLineStatus();   //更新行状态
                this.updateAssetsDiscard(); //报废
                deleteReserveAssets();      //删除保留
                saveEIIHistoty(lineSet);
                this.recordChkLog(AssetsDictConstant.ASS_DIS_OTHER, AssetsDictConstant.STATUS_NO);
            } else {
                this.updateHeaderStatus();
            }
            isSuccess = super.processProcedure();
        } catch (DataHandleException e) {
            e.printStackTrace();
        } finally {
            try {
                if (isSuccess) {
                    msg = "报废单(" + headerDTO.getTransNo() + ")提交成功";
                    conn.commit();
                } else {
                    msg = "报废单(" + headerDTO.getTransNo() + ")提交失败";
                    conn.rollback();
                }
                conn.setAutoCommit(autoCommit);
            } catch (SQLException e) {
                msg = e.getMessage();
                Logger.logError(e);
            }
            return isSuccess;
        }
    }

    private void saveEIIHistoty(DTOSet orderLines) {
        if (orderLines != null && !orderLines.isEmpty()) {
            String orderURL = "/servlet/com.sino.ams.newasset.scrap.servlet.TransServlet";
            orderURL += "?act=DETAIL_ACTION";
            orderURL += "&transId=" + headerDTO.getTransId();
            AmsItemInfoHistoryDAO historyDAO = new AmsItemInfoHistoryDAO(userAccount, null, conn);

            for (int i = 0; i < orderLines.getSize(); i++) {
                AmsAssetsTransLineDTO line = (AmsAssetsTransLineDTO) orderLines.getDTO(i);

                AmsItemInfoHistoryDTO historyDTO = new AmsItemInfoHistoryDTO();

                historyDTO.setOrderCategory(LeaseAppConstant.ORDER_CATEGORY);
                historyDTO.setOrderNo(headerDTO.getTransNo());
                historyDTO.setOrderDtlUrl(orderURL);
                historyDTO.setRemark("其他资产报废流程");
                historyDTO.setBarcode(line.getBarcode());
                historyDTO.setCreatedBy(userAccount.getUserId());

                historyDAO.setDTOParameter(historyDTO);
                historyDAO.recordHistory();
            }
        }
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    protected void prepareProcedureData() {
        AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) dtoParameter;
        headerDTO.setPrimaryKey(headerDTO.getTransId());
        headerDTO.setOrderNo(headerDTO.getTransNo());
        headerDTO.setOrderName(headerDTO.getTransTypeValue());
    }
}
