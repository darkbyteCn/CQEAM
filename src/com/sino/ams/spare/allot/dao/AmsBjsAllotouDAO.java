package com.sino.ams.spare.allot.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.util.CalendarUtil;

import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.flow.bean.FlowAction;
import com.sino.flow.constant.FlowConstant;
import com.sino.flow.dto.FlowDTO;
import com.sino.ams.bean.OrderNumGenerator;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.spare.allot.dto.AmsBjsAllotHDTO;
import com.sino.ams.spare.allot.model.AmsBjsAllotouModel;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2007-11-7
 * Time: 23:25:59
 * To change this template use File | Settings | File Templates.
 */
public class AmsBjsAllotouDAO extends BaseDAO {
    private SfUserDTO sfUser = null;
    private AmsBjsAllotHDTO dto = null;


    public AmsBjsAllotouDAO(SfUserDTO userAccount, AmsBjsAllotHDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        this.sfUser = userAccount;
        this.dto = (AmsBjsAllotHDTO) super.dtoParameter;
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsBjsAllotHDTO dtoPara = (AmsBjsAllotHDTO) dtoParameter;
        super.sqlProducer = new AmsBjsAllotouModel((SfUserDTO) userAccount, dtoPara);
    }

    public void createData() throws DataHandleException {
        super.createData();
        getMessage().addParameterValue("备件(AMS)");
    }

    public void updateData() throws DataHandleException {
        super.updateData();
        getMessage().addParameterValue("备件(AMS)");
    }

    public void deleteData() throws DataHandleException {
        super.deleteData();
        getMessage().addParameterValue("备件(AMS)");
    }

    public RowSet produceWebData(String itemCode, String transId) throws QueryException {
        AmsBjsAllotouModel model = new AmsBjsAllotouModel(sfUser, null);
        SQLModel sqlModel = model.getSQLModel(itemCode, transId);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        return sq.getSearchResult();
    }

    public RowSet produceBarcode(String itemCode) throws QueryException {
        AmsBjsAllotouModel model = new AmsBjsAllotouModel(sfUser, null);
        SQLModel sqlModel = model.getBarcode(itemCode);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        return sq.getSearchResult();
    }

    public boolean saveData(DTOSet lineSet, String[] organizationId, String[] holdCount, String itemCode, FlowDTO flowDTO) throws SQLException {
        boolean success = true;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            String transId = dto.getTransId();
            if (transId.equals("")) {
                SeqProducer seq = new SeqProducer(conn);
//                transId = seq.getStrNextSeq("AMS_ITEM_TRANS_H_S");
                transId = seq.getGUID();
                dto.setTransId(transId);
                dto.setTransNo(WebAttrConstant.ORDER_NO_AUTO_PRODUCE);
                dto.setTransStatus(DictConstant.SAVE_TEMP);
                dto.setTransType(DictConstant.BJFP);
                createData();
                AmsBjsAllotouModel model = new AmsBjsAllotouModel(sfUser, null);
                for (int i = 0; i < organizationId.length; i++) {
                    String organization = organizationId[i];
                    String count = holdCount[i];
                    DBOperator.updateRecord(model.insertLData(organization, transId, count, itemCode), conn);
                    DBOperator.updateRecord(model.insertRData(organization, transId, count, itemCode), conn);
                }
                FlowAction fa = new FlowAction(conn);
                flowDTO.setApplyId(transId);
                flowDTO.setApplyNo(dto.getTransNo());
                flowDTO.setActivity(FlowConstant.FLOW_CODE_NEXT);
                fa.setDto(flowDTO);
                fa.add2Flow("备件分配流程");

            } else {
                dto.setTransStatus(DictConstant.SAVE_TEMP);
                dto.setTransType(DictConstant.BJFP);
                deleteLines(transId, itemCode);
                AmsBjsAllotouModel model = new AmsBjsAllotouModel(sfUser, null);
                for (int i = 0; i < organizationId.length; i++) {
                    String organization = organizationId[i];
                    String count = holdCount[i];
                    DBOperator.updateRecord(model.insertLData(organization, transId, count, itemCode), conn);
//                    DBOperator.updateRecord(model.insertRData(organization, transId, count, itemCode), conn);
                }
            }
            saveLines(lineSet);
            conn.commit();
            prodMessage("ASSETS_TRANSFER_SUCCESS");
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
            prodMessage("SAVE_FAILURE");
        } finally {
            conn.setAutoCommit(autoCommit);
        }

        return success;
    }

    public void insertDetail(String transId, String detailId, String itemCode, String barcode) throws DataHandleException, SQLException {
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            AmsBjsAllotouModel model = new AmsBjsAllotouModel(sfUser, null);
            DBOperator.updateRecord(model.insertDData(transId, detailId, itemCode, barcode), conn);
            updateAddressIdOnWay(barcode);
            conn.commit();
        }
        catch (SQLException e) {
            conn.rollback();
            Logger.logError(e);
            prodMessage("SPARE_SAVE_FAILURE");
        } finally {
            conn.setAutoCommit(autoCommit);
        }

    }

    public boolean submitData(AmsBjsAllotHDTO dto, DTOSet lineSet, String[] organizationId, String[] holdCount, String itemCode, FlowDTO flowDTO) throws SQLException {
        boolean success = true;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            String transId = dto.getTransId();
            dto.setCreationDate(CalendarUtil.getCurrDate());
            OrderNumGenerator ong = new OrderNumGenerator(conn, sfUser.getCompanyCode(), "BJFP");
            dto.setTransNo(ong.getOrderNum());
            if (transId.equals("")) {
                SeqProducer seq = new SeqProducer(conn);
//                transId = seq.getStrNextSeq("AMS_ITEM_TRANS_H_S");
                transId = seq.getGUID();
                dto.setTransId(transId);
                dto.setTransStatus(DictConstant.IN_PROCESS);
                dto.setTransType(DictConstant.BJFP);
                createData();
                AmsBjsAllotouModel model = new AmsBjsAllotouModel(sfUser, null);
                for (int i = 0; i < organizationId.length; i++) {
                    String organization = organizationId[i];
                    String count = holdCount[i];
                    DBOperator.updateRecord(model.insertLData(organization, transId, count, itemCode), conn);
//                    DBOperator.updateRecord(model.insertRData(organization, transId, count, itemCode), conn);
                }

            } else {
                dto.setTransStatus(DictConstant.IN_PROCESS);
                dto.setTransType(DictConstant.BJFP);
                updateData();
                deleteLines(transId, itemCode);
                AmsBjsAllotouModel model = new AmsBjsAllotouModel(sfUser, null);
                for (int i = 0; i < organizationId.length; i++) {
                    String organization = organizationId[i];
                    String count = holdCount[i];
                    DBOperator.updateRecord(model.insertLData(organization, transId, count, itemCode), conn);
                }
            }
            saveLines(lineSet);
            flowDTO.setApplyId(transId);
            flowDTO.setApproveContent(FlowConstant.APPROVE_CONTENT_NEW);
            flowDTO.setApplyNo(dto.getTransNo());
            flowDTO.setActivity(FlowConstant.FLOW_CODE_NEXT);
            FlowAction fa = new FlowAction(conn, flowDTO);
            //fa.add2Flow("备件分配流程");
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
        } catch (CalendarException e) {
            conn.rollback();
            success = false;
            Logger.logError(e);
            prodMessage("SPARE_SAVE_FAILURE");
        }
        catch (QueryException e) {
            conn.rollback();
            success = false;
            Logger.logError(e);
            prodMessage("SPARE_SAVE_FAILURE");
        } catch (Exception e) {
            Logger.logError(e);
        } finally {
            conn.setAutoCommit(autoCommit);
        }
        return success;
    }

    //  流程审批
    public boolean submit(AmsBjsAllotHDTO dto, DTOSet lineSet, String[] organizationId, String[] holdCount, String itemCode, HttpServletRequest req, String[] barcode, String sectionRight, String detailId, FlowDTO flowDTO) throws SQLException {
        boolean success = true;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            dto.setTransStatus(DictConstant.IN_PROCESS);
            String transId = dto.getTransId();
            flowDTO.setApproveContent(FlowConstant.APPROVE_CONTENT_AGREE);
            if (sectionRight.equals("OUT")) {
                flowDTO.setApproveContent(FlowConstant.APPROVE_CONTENT_END);
//               updateAddressIdOnWay(lineSet);
//                AmsBjsAllotouModel model = new AmsBjsAllotouModel(sfUser, null);
//                for (int i = 0; i < barcode.length; i++) {
//                    String code = barcode[i];
//                    DBOperator.updateRecord(model.insertDData(transId, detailId, itemCode, code), conn);
//                }
                dto.setTransStatus(DictConstant.APPROVED);
                updateData();
            }

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

    private void updateAddressIdOnWay(String barcode) throws SQLException {
        if (barcode != null) {
            CallableStatement cStmt = null;
            String sqlStr = "{call AMS_ITEM_TRANS.ITEM_ON_THE_WAY(?,?,?)}";
            try {
                cStmt = conn.prepareCall(sqlStr);

                cStmt.setString(1, barcode);
                cStmt.setInt(2, this.sfUser.getOrganizationId());
                cStmt.setInt(3, this.sfUser.getUserId());
                cStmt.execute();

            } finally {
                DBManager.closeDBStatement(cStmt);
            }
        }
    }

    public boolean cancelData(String transId) {
        boolean operateResult = false;
        boolean autoCommit = true;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            FlowAction flowProcessor = new FlowAction(conn);
            FlowDTO flowDTO = new FlowDTO();
            flowDTO.setProcName("备件分配流程");
            AmsBjsAllotouModel model = new AmsBjsAllotouModel(sfUser, null);
            DBOperator.updateRecord(model.getOrderCancelModel(transId), conn);

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

    public void reject(AmsBjsAllotHDTO dto, FlowDTO flowDTO) throws SQLException {
        try {
            conn.setAutoCommit(false);
            //业务处理
            dto.setTransStatus(DictConstant.REJECTED);
            updateData();
            //流程处理
            flowDTO.setApproveContent(FlowConstant.APPROVE_CONTENT_REJECT);
            flowDTO.setProcName("备件分配流程");
            FlowAction fb = new FlowAction(conn, flowDTO);
            fb.reject2Begin();
          //  fb.reject();
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

    public void deleteLines(String transId, String itemCode) throws DataHandleException {
        AmsBjsAllotouModel model = new AmsBjsAllotouModel(sfUser, null);
        DBOperator.updateRecord(model.getDeleteByTransIdModel(transId, itemCode), conn);
//        DBOperator.updateRecord(model.getDeleteByTransIdModel2(transId, itemCode), conn);
    }

    public void saveLines(DTOSet lineSet) throws DataHandleException {
        if (lineSet != null && !lineSet.isEmpty()) {
            AmsBjsAllotouDAO lineDAO = new AmsBjsAllotouDAO(sfUser, null, conn);
            for (int i = 0; i < lineSet.getSize(); i++) {
                AmsBjsAllotHDTO lineData = (AmsBjsAllotHDTO) lineSet.getDTO(i);
                lineData.setTransId(dto.getTransId());
                lineDAO.setDTOParameter(lineData);
                lineDAO.createData();
            }
        }
    }

    public RowSet getLines(String transId) throws QueryException {
        AmsBjsAllotouModel model = (AmsBjsAllotouModel) sqlProducer;
        SimpleQuery sq = new SimpleQuery(model.getDataByTransIdModel(transId), conn);
        sq.executeQuery();
        return sq.getSearchResult();
    }
}
