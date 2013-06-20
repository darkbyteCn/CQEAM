package com.sino.ams.spare.repair.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;
import com.sino.base.util.CalendarUtil;
import com.sino.base.util.StrUtil;
import com.sino.base.util.ArrUtil;

import com.sino.framework.dto.BaseUserDTO;
import com.sino.ams.bean.OrderNumGenerator;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.OracleAppErrorCode;
import com.sino.ams.spare.dto.AmsItemTransHDTO;
import com.sino.ams.spare.dto.AmsItemTransLDTO;
import com.sino.ams.spare.dto.AmsItemTransDDTO;
import com.sino.ams.spare.repair.dto.AmsCustomerInfoDTO;
import com.sino.ams.spare.repair.dto.AmsVendorInfoDTO;
import com.sino.ams.spare.repair.model.SpareRepairModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.spare.assistant.SpareMessageKeys;
import com.sino.flow.dto.FlowDTO;
import com.sino.flow.bean.FlowAction;
import com.sino.flow.constant.FlowConstant;

/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2007-11-12
 * Time: 9:27:13
 */
public class SpareRepairDAO extends AMSBaseDAO {
    private String barcode = "";

    public SpareRepairDAO(SfUserDTO userAccount, AmsItemTransHDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsItemTransHDTO dto = (AmsItemTransHDTO) dtoParameter;
        super.sqlProducer = new SpareRepairModel((SfUserDTO) userAccount, dto);
    }

    public void createData() throws DataHandleException {
        super.createData();
        getMessage().addParameterValue("仪器头表(AMS)");
    }

    public void updateData() throws DataHandleException {
        super.updateData();
        getMessage().addParameterValue("仪器头表(AMS)");
    }

    public void deleteData() throws DataHandleException {
        super.deleteData();
        getMessage().addParameterValue("仪器头表(AMS)");
    }

    /**
     * 保存单据信息，如果新增则创建单据及行信息，否则，更新相应数据
     * @param lineSet
     * @param isNew
     * @return
     */
    public boolean saveBillData(DTOSet lineSet, boolean isNew) {
        boolean operateResult = false;
        boolean autoCommit = true;
        AmsItemTransHDTO dto = (AmsItemTransHDTO) dtoParameter;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            String transId = dto.getTransId();
            dto.setCreationDate(CalendarUtil.getCurrDate());
            AmsItemTransLDTO lineDto = null;
            if (isNew) {
                createData();
                for (int i = 0; i < lineSet.getSize(); i++) {
                    lineDto = (AmsItemTransLDTO) lineSet.getDTO(i);
                    DBOperator.updateRecord(((SpareRepairModel) sqlProducer).insertLData(transId, lineDto), conn);
                }
            } else {         //要跟据要求流程审批后不能修改数量
                updateData();
                deleteLines(transId);
                for (int i = 0; i < lineSet.getSize(); i++) {
                    lineDto = (AmsItemTransLDTO) lineSet.getDTO(i);
                    DBOperator.updateRecord(((SpareRepairModel) sqlProducer).insertLData(transId, lineDto), conn);
                }
            }
            operateResult = true;
        } catch (CalendarException e) {
            Logger.logError(e);
        } catch (DataHandleException e) {
            Logger.logError(e);
        } catch (SQLException ex) {
            Logger.logError(ex);
        } finally {
            try {
                if (operateResult) {
                    conn.commit();
                    prodMessage(SpareMessageKeys.REPAIR_SUBMIT_SUCCESS);
                    message.addParameterValue(dto.getTransNo());
                } else {
                    conn.rollback();
                    prodMessage(SpareMessageKeys.REPAIR_SUBMIT_FAILURE);
                    message.setNeedClose(true);
                }
                message.setIsError(!operateResult);
                conn.setAutoCommit(autoCommit);
            } catch (SQLException ex) {
                Logger.logError(ex);
            }
        }
        return operateResult;
    }

    public boolean saveData(DTOSet lineSet, boolean isNew) throws SQLException {
        boolean operateResult = false;
        boolean autoCommit = false;
        AmsItemTransHDTO dto = (AmsItemTransHDTO) dtoParameter;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            String transId = dto.getTransId();
            dto.setCreationDate(CalendarUtil.getCurrDate());
            AmsItemTransLDTO lineDto = null;
            if (isNew) {
//                OrderNumGenerator ong = new OrderNumGenerator(conn, userAccount.getCompanyCode(), DictConstant.BJSX);
//                dto.setTransNo(ong.getOrderNum());
//                SeqProducer seq = new SeqProducer(conn);
//                transId = seq.getStrNextSeq("AMS_ITEM_TRANS_H_S");
                SimpleQuery sq = new SimpleQuery(((SpareRepairModel) sqlProducer).getToObjectNoModel(), conn);
                sq.executeQuery();
                dto.setToObjectNo(sq.getFirstRow().getValue("WORKORDER_OBJECT_NO").toString());
                dto.setTransId(transId);
                dto.setTransStatus(DictConstant.SAVE_TEMP);
                dto.setTransType(DictConstant.BJSX);
                createData();
                for (int i = 0; i < lineSet.getSize(); i++) {
                    lineDto = (AmsItemTransLDTO) lineSet.getDTO(i);
                    DBOperator.updateRecord(((SpareRepairModel) sqlProducer).insertLData(transId, lineDto), conn);
                }
            } else {
                dto.setTransStatus(DictConstant.SAVE_TEMP);
                updateData();
                deleteLines(transId);

                for (int i = 0; i < lineSet.getSize(); i++) {
                    lineDto = (AmsItemTransLDTO) lineSet.getDTO(i);
                    DBOperator.updateRecord(((SpareRepairModel) sqlProducer).insertLData(transId, lineDto), conn);
                }
            }
//            saveLines(lineSet);
            conn.commit();
            prodMessage("SPARE_SAVE_SUCCESS");
            operateResult = true;
        } catch (SQLException e) {
            conn.rollback();
            Logger.logError(e);
            prodMessage("SPARE_SAVE_FAILURE");
        } catch (DataHandleException e) {
            conn.rollback();
            Logger.logError(e);
            prodMessage("SPARE_SAVE_FAILURE");
        } catch (CalendarException e) {
            conn.rollback();
            Logger.logError(e);
            prodMessage("SPARE_SAVE_FAILURE");
        } catch (QueryException e) {
            conn.rollback();
            Logger.logError(e);
            prodMessage("SPARE_SAVE_FAILURE");
        } catch (ContainerException e) {
            conn.rollback();
            Logger.logError(e);
            prodMessage("SPARE_SAVE_FAILURE");
        } finally {
            conn.setAutoCommit(autoCommit);
        }
        return operateResult;
    }


    public boolean submitData(DTOSet detailSet) throws SQLException {
        boolean operateResult = false;
        boolean autoCommit = false;
        AmsItemTransHDTO dto = (AmsItemTransHDTO) dtoParameter;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            String transId = dto.getTransId();
            dto.setCreationDate(CalendarUtil.getCurrDate());

            AmsItemTransLDTO lineDto = null;
            if (transId.equals("")) {
                OrderNumGenerator ong = new OrderNumGenerator(conn, userAccount.getCompanyCode(), DictConstant.BJSX);
                dto.setTransNo(ong.getOrderNum());
                SeqProducer seq = new SeqProducer(conn);
//                transId = seq.getStrNextSeq("AMS_ITEM_TRANS_H_S");
                transId = seq.getGUID();
                dto.setTransId(transId);
                dto.setTransStatus(DictConstant.COMPLETED);
                dto.setTransType(DictConstant.BJSX);
                createData();
                for (int i = 0; i < detailSet.getSize(); i++) {
                    lineDto = (AmsItemTransLDTO) detailSet.getDTO(i);
                    DBOperator.updateRecord(((SpareRepairModel) sqlProducer).insertLData(transId, lineDto), conn);
                }
            } else {
                dto.setTransStatus(DictConstant.COMPLETED);
                updateData();
                deleteLines(transId);
                deleteDetails(transId);

                for (int i = 0; i < detailSet.getSize(); i++) {
                    lineDto = (AmsItemTransLDTO) detailSet.getDTO(i);
                    DBOperator.updateRecord(((SpareRepairModel) sqlProducer).insertLData(transId, lineDto), conn);
                }
            }

            updateSpareInfo(detailSet);
            conn.commit();
            prodMessage("SPARE_SAVE_SUCCESS");
            operateResult = true;
        } catch (SQLException e) {
            conn.rollback();
            Logger.logError(e);
            if (e.getErrorCode() == OracleAppErrorCode.spareNotEnough1) {
                prodMessage("SPARE_NOT_ENOUGH1");
                message.addParameterValue(barcode);
            } else {
                prodMessage("SPARE_SAVE_FAILURE");
            }
        } catch (DataHandleException e) {
            conn.rollback();
            Logger.logError(e);
            prodMessage("SPARE_SAVE_FAILURE");
        } catch (CalendarException e) {
            conn.rollback();
            Logger.logError(e);
            prodMessage("SPARE_SAVE_FAILURE");
        } finally {
            conn.setAutoCommit(autoCommit);
        }
        return operateResult;
    }

    private void updateAddressId(String[] barcode) throws SQLException {
        if (barcode != null) {
            CallableStatement cStmt = null;
            String sqlStr = "{call AMS_ITEM_TRANS.ITEM_TO_REPAIR(?,?,?)}";
            try {
                cStmt = conn.prepareCall(sqlStr);
                for (int i = 0; i < barcode.length; i++) {
                    cStmt.setString(1, barcode[i]);
                    cStmt.setInt(2, this.userAccount.getOrganizationId());
                    cStmt.setInt(3, this.userAccount.getUserId());
                    cStmt.execute();
                }

            } finally {
                DBManager.closeDBStatement(cStmt);
            }
        }
    }

    public void deleteLines(String transId) throws DataHandleException {
        SpareRepairModel model = new SpareRepairModel(userAccount, null);
        DBOperator.updateRecord(model.deleteLinesModel(transId), conn);
    }

    public void deleteDetails(String transId) throws DataHandleException {
        SpareRepairModel model = new SpareRepairModel(userAccount, null);
        DBOperator.updateRecord(model.deleteDetailsModel(transId), conn);
    }

    public void saveLines(DTOSet lineSet) throws DataHandleException {
        if (lineSet != null && !lineSet.isEmpty()) {
            AmsItemTransHDTO dto = (AmsItemTransHDTO) dtoParameter;
            BjSendRepairDAO lineDAO = new BjSendRepairDAO(userAccount, null, conn);
            for (int i = 0; i < lineSet.getSize(); i++) {
                AmsItemTransHDTO lineData = (AmsItemTransHDTO) lineSet.getDTO(i);
                lineData.setTransId(dto.getTransId());
                lineDAO.setDTOParameter(lineData);
                lineDAO.createData();
            }
        }
    }

    public RowSet produceWebData() throws QueryException {
        SpareRepairModel model = new SpareRepairModel(userAccount, null);
        SQLModel sqlModel = model.getSQLModel();
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        return sq.getSearchResult();
    }

    public RowSet getLines(String transId) throws QueryException {
        SpareRepairModel model = new SpareRepairModel(userAccount, null);
        SimpleQuery sq = new SimpleQuery(model.getByTransIdModel(transId), conn);
        sq.executeQuery();
        return sq.getSearchResult();
    }

    /**
     * 取单据详细信息（detail表）
     * @param transId
     * @return
     * @throws com.sino.base.exception.QueryException
     *
     */
    public RowSet getDetails(String transId) throws QueryException {
        SpareRepairModel model = new SpareRepairModel(userAccount, null);
        SimpleQuery sq = new SimpleQuery(model.getDtlByTransIdModel(transId), conn);
        sq.executeQuery();
        return sq.getSearchResult();
    }

    public AmsVendorInfoDTO getVendorInfo(String Company) throws QueryException {
        SpareRepairModel model = new SpareRepairModel(userAccount, null);
        SimpleQuery sq = new SimpleQuery(model.getVendorInfo(Company), conn);
        sq.setDTOClassName(AmsVendorInfoDTO.class.getName());
        sq.executeQuery();
        return (AmsVendorInfoDTO) sq.getFirstDTO();
    }

    public AmsItemTransHDTO getPrintH(String transId) throws QueryException {
        SpareRepairModel model = new SpareRepairModel(userAccount, null);
        SimpleQuery sq = new SimpleQuery(model.getPrintHModel(transId), conn);
        sq.setDTOClassName(AmsItemTransHDTO.class.getName());
        sq.executeQuery();
//        return sq.getSearchResult();
        return (AmsItemTransHDTO) sq.getFirstDTO();

    }

    /**
     * 统计送修设备的数量,将送修的设备地点更改为送修
     * @param lineSet DTOSet
     */
    private void updateSpareInfo(DTOSet lineSet) throws SQLException {
        if (lineSet != null && lineSet.getSize() > 0) {
            CallableStatement cStmt = null;
            AmsItemTransLDTO lineData = null;
            String sqlStr = "{call AMS_INV_TRANS.SPARE_SEND_REPAIR(?,?,?)}";
            try {
                cStmt = conn.prepareCall(sqlStr);
                for (int i = 0; i < lineSet.getSize(); i++) {
                    lineData = (AmsItemTransLDTO) lineSet.getDTO(i);
                    barcode = lineData.getBarcode();
                    cStmt.setString(1, lineData.getBarcode());
                    cStmt.setInt(2, this.userAccount.getOrganizationId());
                    cStmt.setInt(3, this.userAccount.getUserId());
                    cStmt.execute();
                }
            } finally {
                DBManager.closeDBStatement(cStmt);
            }
        }
    }

    public AmsCustomerInfoDTO getByOU() throws QueryException {
        SpareRepairModel model = new SpareRepairModel(userAccount, null);
        SimpleQuery sq = new SimpleQuery(model.getByOu(), conn);
        sq.setDTOClassName(AmsCustomerInfoDTO.class.getName());
        sq.executeQuery();
        AmsCustomerInfoDTO dto1 = (AmsCustomerInfoDTO) sq.getFirstDTO();
        return dto1;
    }
    /*   public RowSet getByOU(String transId) throws QueryException {
         SpareRepairModel model = new SpareRepairModel(userAccount, null);
         SimpleQuery sq = new SimpleQuery(model.getByOu(), conn);
         sq.executeQuery();
         return sq.getSearchResult();
     }*/

    public void updateStorage(String transId) throws SQLException {
        if (StrUtil.isNotEmpty(transId)) {
            CallableStatement cStmt = null;
            String sqlStr = "{call AMS_ITEM_TRANS_SX.SPARE_REPAIR(?)}";
            try {
                cStmt = conn.prepareCall(sqlStr);
                cStmt.setString(1, transId);
                cStmt.execute();

            } finally {
                DBManager.closeDBStatement(cStmt);
            }
        }
    }

    public void completeTrans(String transId, String transStaus) throws DataHandleException {
        SpareRepairModel model = (SpareRepairModel) sqlProducer;
        SQLModel sqlModel = model.updateStatusModels(transId, transStaus);
        DBOperator.updateRecord(sqlModel, conn);
    }


    public String getObjectN0() throws QueryException, ContainerException {
        String objectN0 = "";
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr =
                "SELECT EO.WORKORDER_OBJECT_NO, EO.WORKORDER_OBJECT_LOCATION||EO.WORKORDER_OBJECT_NAME\n" +
                        "  FROM ETS_OBJECT EO\n" +
                        " WHERE (EO.OBJECT_CATEGORY = '72' )\n" +
                        "   AND EO.ORGANIZATION_ID = ?";
        sqlArgs.add(userAccount.getOrganizationId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        if (simpleQuery.hasResult()) {
            objectN0 = simpleQuery.getFirstRow().getStrValue("WORKORDER_OBJECT_NO");
        } else {
            objectN0 = "";
        }
        return objectN0;
    }


    public void saveData(DTOSet ds, FlowDTO flowDTO, String flowAction, boolean isFirstNode) throws ContainerException {
        boolean operateResult = false;
        FlowAction fa = new FlowAction(conn);
        boolean autoCommit = false;
        AmsItemTransHDTO dto = (AmsItemTransHDTO) dtoParameter;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            String transId = dto.getTransId();
            dto.setCreationDate(CalendarUtil.getCurrDate());

            AmsItemTransDDTO lineDto = null;
            if (transId.equals("")) {
                OrderNumGenerator ong = new OrderNumGenerator(conn, userAccount.getCompanyCode(), DictConstant.BJSX);
                dto.setTransNo(ong.getOrderNum());
                SeqProducer seq = new SeqProducer(conn);
//                transId = seq.getStrNextSeq("AMS_ITEM_TRANS_H_S");
                transId = seq.getGUID();
                dto.setTransId(transId);
                dto.setTransStatus(DictConstant.COMPLETED);
                dto.setTransType(DictConstant.BJSX);
                createData();
                for (int i = 0; i < ds.getSize(); i++) {
                    lineDto = (AmsItemTransDDTO) ds.getDTO(i);
                    DBOperator.updateRecord(((SpareRepairModel) sqlProducer).insertDData(transId, lineDto), conn);
                }

                DBOperator.updateRecord(((SpareRepairModel) sqlProducer).insertLineModel(transId), conn);
                DBOperator.updateRecord(((SpareRepairModel) sqlProducer).updateDetailModel(transId), conn);

                flowDTO.setApplyId(dto.getTransId());
                flowDTO.setApplyNo(dto.getTransNo());
                flowDTO.setSessionUserId(userAccount.getUserId());
                flowDTO.setSessionUserName(userAccount.getUsername());
                flowDTO.setActivity(FlowConstant.FLOW_CODE_NEXT);
                excuteFlow(flowDTO, flowAction, true);

            } else {
                dto.setTransStatus(DictConstant.COMPLETED);
                if (isFirstNode) {
                    updateData();
                    deleteLines(transId);
                    deleteDetails(transId);

                    for (int i = 0; i < ds.getSize(); i++) {
                        lineDto = (AmsItemTransDDTO) ds.getDTO(i);
                        DBOperator.updateRecord(((SpareRepairModel) sqlProducer).insertDData(transId, lineDto), conn);
                    }
                    DBOperator.updateRecord(((SpareRepairModel) sqlProducer).insertLineModel(transId), conn);
                    DBOperator.updateRecord(((SpareRepairModel) sqlProducer).updateDetailModel(transId), conn);
                }

                flowDTO.setApplyId(dto.getTransId());
                flowDTO.setApplyNo(dto.getTransNo());
                flowDTO.setSessionUserId(userAccount.getUserId());
                flowDTO.setSessionUserName(userAccount.getUsername());
                flowDTO.setActivity(FlowConstant.FLOW_CODE_NEXT);
                excuteFlow(flowDTO, flowAction, true);

            }
            fa.setDto(flowDTO);
            if (fa.isFlowToEnd()) {
//                     updateSpareInfo(detailSet);
                updateStorage(dto.getTransId());
                completeTrans(dto.getTransId(), DictConstant.COMPLETED);
            }
            conn.commit();
            conn.setAutoCommit(autoCommit);
            prodMessage("SPARE_SAVE_SUCCESS");
            operateResult = true;
        } catch (DataHandleException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (CalendarException e) {
            e.printStackTrace();
        } catch (QueryException e) {
            e.printStackTrace();
        }


    }

    public void excuteFlow(FlowDTO flowDTO, String flowAction, boolean isNew) throws DataHandleException, SQLException, QueryException {
        FlowAction fa = new FlowAction(conn);
        fa.setDto(flowDTO);
        if (flowAction.equals(DictConstant.FLOW_SAVE)) {//暂存
            if (isNew) {
                fa.add2Flow(flowDTO.getProcName());
            }
        } else if (flowAction.equals(DictConstant.FLOW_COMPLETE)) {
            fa.flow();
        } else if (flowAction.equals(DictConstant.FLOW_CANCEL)) {
            fa.cancel();
        } else if (flowAction.equals(DictConstant.FLOW_BACK)) {
            flowDTO.setActivity(FlowConstant.FLOW_CODE_PREV);
            fa.setDto(flowDTO);
            fa.reject2Begin();
        }
    }

    public String verifyBarcodes(String[] barcodes) throws QueryException {
        String codes = "";
        List codeList = new ArrayList();
        SQLModel sqlModel;
        SimpleQuery simpleQuery;
        for (int i = 0; i < barcodes.length; i++) {
            String barcode = barcodes[i];
            sqlModel = ((SpareRepairModel) sqlProducer).verifyBarcode(barcode);
            simpleQuery = new SimpleQuery(sqlModel, conn);
            simpleQuery.executeQuery();
            if (simpleQuery.hasResult()) {
                codeList.add(barcode);
            }
        }
        codes = ArrUtil.lstToSqlStr(codeList);
        return codes;
    }

}