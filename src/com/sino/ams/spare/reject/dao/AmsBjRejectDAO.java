package com.sino.ams.spare.reject.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;
import com.sino.base.util.CalendarUtil;

import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.flow.bean.FlowAction;
import com.sino.flow.constant.FlowConstant;
import com.sino.flow.dto.FlowDTO;
import com.sino.ams.bean.OrderNumGenerator;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.OracleAppErrorCode;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.spare.allot.dto.AmsBjsAllotHDTO;
import com.sino.ams.spare.assistant.ObjectUtil;
import com.sino.ams.spare.dto.AmsItemTransHDTO;
import com.sino.ams.spare.dto.AmsItemTransLDTO;
import com.sino.ams.spare.reject.model.AmsBjRejectModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.util.BillUtil;

/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2007-11-13
 * Time: 22:59:23
 */
public class AmsBjRejectDAO extends BaseDAO {
    private SfUserDTO sfUser = null;
    private AmsItemTransHDTO dto = null;
    private AmsBjRejectModel sqlModel = null;
    private String barcode = "";

    public AmsBjRejectDAO(SfUserDTO userAccount, AmsItemTransHDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        this.sfUser = userAccount;
        this.dto = (AmsItemTransHDTO) super.dtoParameter;
        sqlModel = (AmsBjRejectModel) sqlProducer;
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsItemTransHDTO dtoPara = (AmsItemTransHDTO) dtoParameter;
        super.sqlProducer = new AmsBjRejectModel((SfUserDTO) userAccount, dtoPara);
    }

    public void createData() throws DataHandleException {
        super.createData();
        getMessage().addParameterValue("备件头表(AMS)");
    }

    public void updateData() throws DataHandleException {
        super.updateData();
        getMessage().addParameterValue("备件头表(AMS)");
    }

    public void deleteData() throws DataHandleException {
        super.deleteData();
        getMessage().addParameterValue("备件头表(AMS)");
    }

    /**
     * 撤消单据
     * @param transId 头ID
     * @return operateResult
     */
    public boolean cancelData(String transId) {
        boolean operateResult = false;
        boolean autoCommit = true;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            FlowAction flowProcessor = new FlowAction(conn);
            FlowDTO flowDTO = new FlowDTO();
            flowDTO.setProcName("备件报废流程");
            DBOperator.updateRecord(sqlModel.getOrderCancelModel(transId), conn);

            flowDTO.setApplyId(transId);//删除在办箱数据
            flowProcessor.setDto(flowDTO);
            flowProcessor.cancel();
            operateResult = true;
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
                message.setIsError(!operateResult);
            } catch (SQLException ex1) {
                Logger.logError(ex1);
                prodMessage(AssetsMessageKeys.ROLL_BACK_ERROR);
            }
        }
        return operateResult;
    }

    public boolean saveData(DTOSet lineSet, FlowDTO flowDTO) throws SQLException {
        boolean success = false;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            String transId = dto.getTransId();
            dto.setCreationDate(CalendarUtil.getCurrDate());
            AmsItemTransLDTO lineDto = null;
            if (transId.equals("")) {
                OrderNumGenerator ong = new OrderNumGenerator(conn, sfUser.getCompanyCode(), "BJBF");
                String no = ong.getOrderNum();
                dto.setTransNo(no);
                SeqProducer seq = new SeqProducer(conn);
//                transId = seq.getStrNextSeq("AMS_ITEM_TRANS_H_S");
                transId = seq.getGUID();
                dto.setTransId(transId);
                dto.setTransStatus(DictConstant.SAVE_TEMP);
                dto.setTransType(DictConstant.BJBF);
                dto.setFromObjectNo(ObjectUtil.getObjectNo(DictConstant.INV_TO_REPAIR, sfUser.getOrganizationId(), conn));
                createData();
                for (int i = 0; i < lineSet.getSize(); i++) {
                    lineDto = (AmsItemTransLDTO) lineSet.getDTO(i);
                    DBOperator.updateRecord(sqlModel.insertLData(transId, lineDto), conn);
//                    DBOperator.updateRecord(model.insertRData(transId, count, code), conn);
//                    DBOperator.updateRecord(model.updateInfoModel(addresId, bar), conn);
                }
                FlowAction fa = new FlowAction(conn);
                flowDTO.setApplyId(transId);
                flowDTO.setApplyNo(dto.getTransNo());
                flowDTO.setActivity(FlowConstant.FLOW_CODE_NEXT);
                fa.setDto(flowDTO);
                fa.add2Flow("备件报废流程");
            } else {
                updateData();
                deleteLines(transId);

                for (int i = 0; i < lineSet.getSize(); i++) {
                    lineDto = (AmsItemTransLDTO) lineSet.getDTO(i);
                    DBOperator.updateRecord(sqlModel.insertLData(transId, lineDto), conn);
                    //DBOperator.updateRecord(model.insertRData(transId, count, code), conn);
                    // DBOperator.updateRecord(model.updateInfoModel(addresId, bar), conn);

                }
            }
//            saveLines(lineSet);
            conn.commit();
            prodMessage("SPARE_SAVE_SUCCESS");
            success = true;
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
        return success;
    }

    /**
     * 审批通过
     * @param flowDTO FlowDTO
     * @return success
     * @throws SQLException
     */
    public boolean passApprove(FlowDTO flowDTO) throws SQLException {
        boolean success = false;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            FlowAction fa = new FlowAction(conn, flowDTO);
//            String transId = dto.getTransId();
            flowDTO.setApproveContent(FlowConstant.APPROVE_CONTENT_AGREE);
            if (fa.isFlowToEnd()) {   //流程结束
                dto.setTransStatus(DictConstant.COMPLETED);
                flowDTO.setApproveContent(FlowConstant.APPROVE_CONTENT_END);
//              更新单据状态
                BillUtil.updateStatus("AMS_ITEM_TRANS_H", "TRANS_ID", dto.getTransId(), "TRANS_STATUS", dto.getTransStatus(), conn);
                //将报废设备入报废库
                updateSpareInfo(getLines(dto.getTransId()));
            }

            fa.flow();
            conn.commit();
            prodMessage("SPARE_SAVE_SUCCESS");
            success = true;
        } catch (SQLException e) {
            conn.rollback();
            Logger.logError(e);
            if (e.getErrorCode() == OracleAppErrorCode.spareNotEnough3) {
                prodMessage("SPARE_NOT_ENOUGH3");
                message.addParameterValue(barcode);
            } else {
                prodMessage("SPARE_SAVE_FAILURE");
            }
        } catch (DataHandleException e) {
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
        return success;
    }

    /**
     * 统计报废设备的数量,将报废的设备地点更改为报废
     * @param lineSet DTOSet
     */
    private void updateSpareInfo(DTOSet lineSet) throws SQLException {
        if (lineSet != null && lineSet.getSize() > 0) {
            CallableStatement cStmt = null;
            AmsItemTransLDTO lineData = null;
            String sqlStr = "{call AMS_INV_TRANS.SPARE_REJECT(?,?,?,?)}";
            try {
                cStmt = conn.prepareCall(sqlStr);
                for (int i = 0; i < lineSet.getSize(); i++) {
                    lineData = (AmsItemTransLDTO) lineSet.getDTO(i);
                    barcode = lineData.getBarcode();
                    cStmt.setString(1, lineData.getBarcode());
                    cStmt.setInt(2, lineData.getQuantity());
                    cStmt.setInt(3, this.sfUser.getOrganizationId());
                    cStmt.setInt(4, this.sfUser.getUserId());
                    cStmt.execute();
                }
            } finally {
                DBManager.closeDBStatement(cStmt);
            }
        }
    }

    public boolean submit(HttpServletRequest req, String[] barcode, String sectionRight, FlowDTO flowDTO) throws SQLException {
        boolean success = true;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
//            String transId = dto.getTransId();
            flowDTO.setApproveContent(FlowConstant.APPROVE_CONTENT_AGREE);
            if (sectionRight.equals("OUT")) {
                dto.setTransStatus(DictConstant.APPROVED);
                flowDTO.setApproveContent(FlowConstant.APPROVE_CONTENT_END);
                updateAddressId(barcode);
//               updateAddressIdOnWay(lineSet);
                /*  AmsBjRejectModel model = new AmsBjRejectModel(sfUser, null);
                for (int i = 0; i < barcode.length; i++) {
                    String code = barcode[i];
                    DBOperator.updateRecord(model.insertDData(transId, detailId, itemCode, code), conn);
                }*/

            }
            updateData();
            FlowAction fa = new FlowAction(conn, flowDTO);
            fa.flow();
            conn.commit();
            prodMessage("SPARE_SAVE_SUCCESS");
            success = true;
        } catch (SQLException e) {
            conn.rollback();
            success = false;
            Logger.logError(e);
            prodMessage("SPARE_SAVE_FAILURE");
        } catch (DataHandleException e) {
            conn.rollback();
            success = false;
            Logger.logError(e);
            prodMessage("SPARE_SAVE_FAILURE");
        } catch (QueryException e) {
            conn.rollback();
            success = false;
            Logger.logError(e);
            prodMessage("SPARE_SAVE_FAILURE");
        } finally {
            conn.setAutoCommit(autoCommit);
        }
        return success;
    }

    /**
     * 审批不通过
     * @param flowDTO FlowDTO
     * @throws SQLException
     */
    public void reject(FlowDTO flowDTO) throws SQLException {
        try {
            conn.setAutoCommit(false);
            //业务处理
            dto.setTransStatus(DictConstant.REJECTED);
            //流程处理
            updateData();
            flowDTO.setProcName("备件报废流程");
            FlowAction fb = new FlowAction(conn, flowDTO);
            fb.reject2Begin();
            //fb.reject();
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

    /**
     * 提交审批
     * @param lineSet 行信息
     * @param flowDTO FlowDTO
     * @return success
     * @throws SQLException
     */
    public boolean submitData(DTOSet lineSet, FlowDTO flowDTO) throws SQLException {
        boolean success = false;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            String transId = dto.getTransId();
            dto.setCreationDate(CalendarUtil.getCurrDate());

            AmsItemTransLDTO lineDto = null;
            dto.setTransStatus(DictConstant.IN_PROCESS);
            if (transId.equals("")) {
                OrderNumGenerator ong = new OrderNumGenerator(conn, sfUser.getCompanyCode(), "BJBF");
                String no = ong.getOrderNum();
                dto.setTransNo(no);
                SeqProducer seq = new SeqProducer(conn);
//                transId = seq.getStrNextSeq("AMS_ITEM_TRANS_H_S");
                transId = seq.getGUID();
                dto.setTransId(transId);
                dto.setFromObjectNo(ObjectUtil.getObjectNo(DictConstant.INV_TO_REPAIR, sfUser.getOrganizationId(), conn));
                createData();
                for (int i = 0; i < lineSet.getSize(); i++) {
                    lineDto = (AmsItemTransLDTO) lineSet.getDTO(i);
                    DBOperator.updateRecord(sqlModel.insertLData(transId, lineDto), conn);
                }
            } else {
                updateData();
                deleteLines(transId);
                for (int i = 0; i < lineSet.getSize(); i++) {
                    lineDto = (AmsItemTransLDTO) lineSet.getDTO(i);
                    DBOperator.updateRecord(sqlModel.insertLData(transId, lineDto), conn);

                }
            }
//            saveLines(lineSet);
//            updateAddressId(barcode);
            flowDTO.setApplyId(transId);
            flowDTO.setApplyNo(dto.getTransNo());

            if (flowDTO.getActId().equals("")) {
                flowDTO.setApproveContent(FlowConstant.APPROVE_CONTENT_NEW);
            } else {
                flowDTO.setApproveContent(FlowConstant.APPROVE_CONTENT_RESUBMIT);
            }
            flowDTO.setActivity(FlowConstant.FLOW_CODE_NEXT);
            FlowAction fa = new FlowAction(conn, flowDTO);
            fa.flow();
            conn.commit();
            prodMessage("SPARE_SAVE_SUCCESS");
            success = true;
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
        return success;
    }

    public void deleteLines(String transId) throws DataHandleException {
        AmsBjRejectModel model = new AmsBjRejectModel(sfUser, null);
        DBOperator.updateRecord(model.getDeleteByTransIdModel(transId), conn);
    }

    public void deleteRines(String transId) throws DataHandleException {
        AmsBjRejectModel model = new AmsBjRejectModel(sfUser, null);
        DBOperator.updateRecord(model.getDeleteByTransIdModel(transId), conn);
    }

    public void saveLines(DTOSet lineSet) throws DataHandleException {
        if (lineSet != null && !lineSet.isEmpty()) {
            AmsBjRejectDAO lineDAO = new AmsBjRejectDAO(sfUser, null, conn);
            for (int i = 0; i < lineSet.getSize(); i++) {
                AmsBjsAllotHDTO lineData = (AmsBjsAllotHDTO) lineSet.getDTO(i);
                lineData.setTransId(dto.getTransId());
                lineDAO.setDTOParameter(lineData);
                lineDAO.createData();
            }
        }
    }

    public DTOSet getLines(String transId) throws QueryException {
        AmsBjRejectModel model = new AmsBjRejectModel(sfUser, null);
        SimpleQuery sq = new SimpleQuery(model.getByTransIdModel(dto.getTransId()), conn);
        sq.setDTOClassName(AmsItemTransLDTO.class.getName());
        sq.executeQuery();
        return sq.getDTOSet();
    }

    private void updateAddressId(String barcode[]) throws SQLException {
        if (barcode != null && !barcode.equals("")) {
            CallableStatement cStmt = null;
            String sqlStr = "{call AMS_ITEM_TRANS.ITEM_TO_REJECT(?,?,?)}";
            try {
                cStmt = conn.prepareCall(sqlStr);
                for (int i = 0; i < barcode.length; i++) {
                    cStmt.setString(1, barcode[i]);
                    cStmt.setInt(2, sfUser.getOrganizationId());
                    cStmt.setInt(3, sfUser.getUserId());
                    cStmt.execute();
                }
            } finally {
                DBManager.closeDBStatement(cStmt);
            }
        }
    }
}
