package com.sino.ams.spare.allot.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
import com.sino.base.util.StrUtil;

import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.flow.bean.FlowAction;
import com.sino.flow.constant.FlowConstant;
import com.sino.flow.dto.FlowDTO;
import com.sino.ams.bean.OrderNumGenerator;
import com.sino.ams.spare.allot.dto.AmsBjsAllotDDto;
import com.sino.ams.spare.allot.dto.AmsBjsAllotHDTO;
import com.sino.ams.spare.allot.model.AmsBjsAllotModel;
import com.sino.ams.system.user.dto.SfUserDTO;


/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2007-11-6
 * Time: 11:31:25
 */
public class AmsBjsAllotDAO extends BaseDAO {
    private SfUserDTO sfUser = null;
    private AmsBjsAllotHDTO dto = null;

    public AmsBjsAllotDAO(SfUserDTO userAccount, AmsBjsAllotHDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        this.sfUser = userAccount;
        this.dto = (AmsBjsAllotHDTO) super.dtoParameter;
    }


    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsBjsAllotHDTO dtoPara = (AmsBjsAllotHDTO) dtoParameter;
        super.sqlProducer = new AmsBjsAllotModel((SfUserDTO) userAccount, dtoPara);
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

    public boolean saveData(DTOSet lineSet, String[] barcode) throws SQLException {
        boolean success = true;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            String transId = dto.getTransId();
            if (transId.equals("")) {
                SeqProducer seq = new SeqProducer(conn);
//                transId = seq.getStrNextSeq("AMS_INSTRU_TRANS_H_S");
                transId = seq.getGUID();
                dto.setTransId(transId);
                createData();
                AmsBjsAllotModel model = new AmsBjsAllotModel(sfUser, null);
                for (int i = 0; i < barcode.length; i++) {
                    String code = barcode[i];
                    DBOperator.updateRecord(model.insertLData(code, transId), conn);
                    DBOperator.updateRecord(model.insertRData(code, transId), conn);
                }

            } else {
                updateData();
                deleteLines(transId);
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
        } finally {
            conn.setAutoCommit(autoCommit);
        }

        return success;
    }

    /*public boolean repalData(DTOSet lineSet, String transId, String transStatus,String[] barcodeNo) throws SQLException {
        boolean success = true;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            dto.setConfirmDate(CalendarUtil.getCurrDate());
            AmsInstrumentBorrowModel model = new AmsInstrumentBorrowModel(sfUser, null);
            DBOperator.updateRecord(model.updateHRepalModel(transId, transStatus), conn);
            for(int i=0;i<barcodeNo.length;i++){
              String codeNo=barcodeNo[i];
            DBOperator.updateRecord(model.deleteByBarcodeNoModel(codeNo), conn);
            }
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
        } finally {
            conn.setAutoCommit(autoCommit);
        }
        return success;
    }*/


    public boolean submitData(DTOSet lineSet, String[] barcode) throws SQLException {
        boolean success = true;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
//            conn.setAutoCommit(false);
            String transId = dto.getTransId();
            dto.setCreationDate(CalendarUtil.getCurrDate());
            OrderNumGenerator ong = new OrderNumGenerator(conn, sfUser.getCompanyCode(), "INS-BRW");
            dto.setTransNo(ong.getOrderNum());
            if (transId.equals("")) {
                SeqProducer seq = new SeqProducer(conn);
//                transId = seq.getStrNextSeq("AMS_INSTRU_TRANS_H_S");
                transId = seq.getGUID();
                dto.setTransId(transId);
                createData();

                AmsBjsAllotModel model = new AmsBjsAllotModel(sfUser, null);
                for (int i = 0; i < barcode.length; i++) {
                    String code = barcode[i];
                    DBOperator.updateRecord(model.insertLData(code, transId), conn);
                    DBOperator.updateRecord(model.insertRData(code, transId), conn);
                }
            } else {
                updateData();
                deleteLines(transId);
                AmsBjsAllotModel model = new AmsBjsAllotModel(sfUser, null);
                for (int i = 0; i < barcode.length; i++) {
                    String code = barcode[i];
                    DBOperator.updateRecord(model.insertLData(code, transId), conn);
                }
            }
            saveLines(lineSet);
//            conn.commit();
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
        } finally {
            conn.setAutoCommit(autoCommit);
        }
        return success;
    }

    public void deleteLines(String transId) throws DataHandleException {
        AmsBjsAllotModel model = new AmsBjsAllotModel(sfUser, null);
        DBOperator.updateRecord(model.getDeleteByTransIdModel(transId), conn);
    }

    /*public void saveLines(DTOSet lineSet) throws DataHandleException {
        if (lineSet != null && !lineSet.isEmpty()) {
            AmsBjsAllotDAO lineDAO = new AmsBjsAllotDAO(sfUser, null, conn);
            for (int i = 0; i < lineSet.getSize(); i++) {
                AmsBjsAllotHDTO lineData = (AmsBjsAllotHDTO) lineSet.getDTO(i);
                lineData.setTransId(dto.getTransId());
                lineDAO.setDTOParameter(lineData);
                lineDAO.createData();
            }
        }
    }*/

    public DTOSet getLines(String transId) throws QueryException {
        AmsBjsAllotModel model = (AmsBjsAllotModel) sqlProducer;
        SimpleQuery sq = new SimpleQuery(model.getDataByTransIdModel(transId), conn);
        sq.setDTOClassName(AmsBjsAllotDDto.class.getName());
        sq.executeQuery();
        return sq.getDTOSet();
    }

    public boolean saveData(DTOSet lines, String detailIds, FlowDTO flowDTO) throws SQLException {
        boolean success = false;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            OrderNumGenerator ong = new OrderNumGenerator(conn, sfUser.getCompanyCode(),"BJFP");
            dto.setTransNo(ong.getOrderNum());
            //save header
            saveHeader();
            //删除已删掉的行
            delLines(detailIds);
            //save lines
            saveLines(lines);
            FlowAction fa = new FlowAction(conn);
            flowDTO.setApplyId(((AmsBjsAllotHDTO) dtoParameter).getTransId());
            flowDTO.setApplyNo(dto.getTransNo());
            flowDTO.setActivity(FlowConstant.FLOW_CODE_NEXT);
            fa.setDto(flowDTO);
            fa.add2Flow("备件分配流程");
            success = true;
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            Logger.logError(e);
            prodMessage("SPARE_SAVE_FAILURE");
        } catch (DataHandleException e) {
            conn.rollback();
            Logger.logError(e);
            prodMessage("SPARE_SAVE_FAILURE");
        } catch (QueryException e) {
            conn.rollback();
            Logger.logError(e);
            prodMessage("SPARE_SAVE_FAILURE");
        } finally {
            conn.setAutoCommit(autoCommit);
        }
        return success;
    }

    public boolean submitData(DTOSet lines, String detailIds, FlowDTO flowDTO) throws SQLException {
        boolean success = false;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            if(dto.getTransId().equals("")){
                OrderNumGenerator ong = new OrderNumGenerator(conn, sfUser.getCompanyCode(),"BJFP");
                dto.setTransNo(ong.getOrderNum());
            }
            //save header
            saveHeader();
            //删除已删掉的行
            delLines(detailIds);
            //save lines
            saveLines(lines);
            if (flowDTO.getActId().equals("")) {
                flowDTO.setApproveContent(FlowConstant.APPROVE_CONTENT_NEW);
            } else {
                flowDTO.setApproveContent(FlowConstant.APPROVE_CONTENT_RESUBMIT);
            }
            FlowAction fa = new FlowAction(conn);
            flowDTO.setApplyId(((AmsBjsAllotHDTO) dtoParameter).getTransId());
            flowDTO.setApplyNo(dto.getTransNo());
            flowDTO.setActivity(FlowConstant.FLOW_CODE_NEXT);
            fa.setDto(flowDTO);
            fa.flow();
            success = true;
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            Logger.logError(e);
            prodMessage("SPARE_SAVE_FAILURE");
        } catch (DataHandleException e) {
            conn.rollback();
            Logger.logError(e);
            prodMessage("SPARE_SAVE_FAILURE");
        } catch (QueryException e) {
            conn.rollback();
            Logger.logError(e);
            prodMessage("SPARE_SAVE_FAILURE");
        } finally {
            conn.setAutoCommit(autoCommit);
        }
        return success;
    }

    public void saveHeader() throws SQLException {
        CallableStatement cStmt = null;
        String sqlStr = "{call AMS_ITEM_TRANS.SAVE_ALLOCATE_HEADER(?,?,?,?,?,?,?,?,?,?)}";
        try {
            cStmt = conn.prepareCall(sqlStr);
            cStmt.setString(1, dto.getTransId());
            cStmt.setString(2, dto.getTransNo());
            cStmt.setString(3, dto.getTransType());
            cStmt.setString(4, dto.getTransStatus());
            cStmt.setString(5, dto.getFromObjectNo());
            cStmt.setString(6, dto.getToObjectNo());
            cStmt.setInt(7, sfUser.getOrganizationId());
            cStmt.setInt(8, dto.getToOrganizationId());
            cStmt.setString(9, dto.getRcvUser());
            cStmt.setInt(10, sfUser.getUserId());
            cStmt.registerOutParameter(1, Types.VARCHAR);
            cStmt.execute();
            dto.setTransId(cStmt.getString(1));
            prodMessage("UPDATE_DATA_SUCCESS");
        } finally {
            DBManager.closeDBStatement(cStmt);
        }
    }

    public void saveLines(DTOSet lines) throws SQLException {
        CallableStatement cStmt = null;
        String sqlStr = "{call AMS_ITEM_TRANS.SAVE_ALLOCATE_LINE(?,?,?,?,?,?)}";
        try {
            cStmt = conn.prepareCall(sqlStr);
            for (int i = 0; i < lines.getSize(); i++) {
                AmsBjsAllotDDto lineDto = (AmsBjsAllotDDto) lines.getDTO(i);
                cStmt.setString(1, dto.getTransId());
                cStmt.setString(2, lineDto.getDetailId());
                cStmt.setInt(3, dto.getToOrganizationId());
                cStmt.setString(4, lineDto.getItemCode());
                cStmt.setString(5, lineDto.getQuantity());
                cStmt.setString(6, lineDto.getConfirmQuantity());
                cStmt.execute();
            }
            prodMessage("UPDATE_DATA_SUCCESS");
        } finally {
            DBManager.closeDBStatement(cStmt);
        }
    }

    /**
     * 删除已删掉的数据库中有的行
     * @param detailIds 行ID,以","分隔
     */
    public void delLines(String detailIds) throws DataHandleException {
        if (!detailIds.equals("")) {
            detailIds = StrUtil.trim(detailIds,",",false);
            String sqlStr = "DELETE FROM AMS_ITEM_TRANS_D WHERE DETAIL_ID IN (" + detailIds + ")" +
                    "   AND TRANS_ID = ?";
            SQLModel sm = new SQLModel();
            List list = new ArrayList();
            list.add(dto.getTransId());
            sm.setArgs(list);
            sm.setSqlStr(sqlStr);
            DBOperator.updateRecord(sm, conn);
        }

    }
}
