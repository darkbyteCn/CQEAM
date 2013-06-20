package com.sino.nm.spare2.repair.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

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
import com.sino.ams.bean.OrderNumGenerator;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.OracleAppErrorCode;
import com.sino.nm.spare2.dto.AmsItemTransHDTO;
import com.sino.nm.spare2.dto.AmsItemTransLDTO;
import com.sino.nm.spare2.repair.dto.AmsCustomerInfoDTO;
import com.sino.nm.spare2.repair.model.BjSendRepairModel;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2007-11-12
 * Time: 9:27:13
 */
public class BjSendRepairDAO extends BaseDAO {
    private SfUserDTO sfUser = null;
    private AmsItemTransHDTO dto = null;
    private String barcode = "";

    public BjSendRepairDAO(SfUserDTO userAccount, AmsItemTransHDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        this.sfUser = userAccount;
        this.dto = (AmsItemTransHDTO) super.dtoParameter;
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsItemTransHDTO dtoPara = (AmsItemTransHDTO) dtoParameter;
        super.sqlProducer = new BjSendRepairModel((SfUserDTO) userAccount, dtoPara);
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


    public boolean saveData(DTOSet lineSet) throws SQLException {
        boolean success = false;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            String transId = dto.getTransId();
            dto.setCreationDate(CalendarUtil.getCurrDate());
            AmsItemTransLDTO lineDto = null;
            if (transId.equals("")) {
                OrderNumGenerator ong = new OrderNumGenerator(conn, sfUser.getCompanyCode(), DictConstant.BJSX);
                dto.setTransNo(ong.getOrderNum());
                SeqProducer seq = new SeqProducer(conn);
                transId = seq.getGUID();
//                SimpleQuery sq = new SimpleQuery(((BjSendRepairModel) sqlProducer).getToObjectNoModel(), conn);
//                sq.executeQuery();
//                dto.setToObjectNo(sq.getFirstRow().getValue("WORKORDER_OBJECT_NO").toString());
                dto.setTransId(transId);
                dto.setTransStatus(DictConstant.SAVE_TEMP);
                dto.setTransType(DictConstant.BJSX);
                createData();
                for (int i = 0; i < lineSet.getSize(); i++) {
                    lineDto = (AmsItemTransLDTO) lineSet.getDTO(i);
                    DBOperator.updateRecord(((BjSendRepairModel) sqlProducer).insertLData(transId, lineDto), conn);
                }
            } else {
                dto.setTransStatus(DictConstant.SAVE_TEMP);
                updateData();
                deleteLines(transId);

                for (int i = 0; i < lineSet.getSize(); i++) {
                    lineDto = (AmsItemTransLDTO) lineSet.getDTO(i);
                    DBOperator.updateRecord(((BjSendRepairModel) sqlProducer).insertLData(transId, lineDto), conn);
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
        } finally {
            conn.setAutoCommit(autoCommit);
        }
        return success;
    }


    public boolean submitData(DTOSet lineSet) throws SQLException {
        boolean success = false;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            String transId = dto.getTransId();
            dto.setCreationDate(CalendarUtil.getCurrDate());
            dto.setTransStatus(DictConstant.COMPLETED);
            AmsItemTransLDTO lineDto = null;
            if (transId.equals("")) {
                OrderNumGenerator ong = new OrderNumGenerator(conn, sfUser.getCompanyCode(), DictConstant.BJSX);
                dto.setTransNo(ong.getOrderNum());
                SeqProducer seq = new SeqProducer(conn);
                transId = seq.getGUID();
                dto.setTransId(transId);

                dto.setTransType(DictConstant.BJSX);
                createData();
                for (int i = 0; i < lineSet.getSize(); i++) {
                    lineDto = (AmsItemTransLDTO) lineSet.getDTO(i);
                    DBOperator.updateRecord(((BjSendRepairModel) sqlProducer).insertLData(transId, lineDto), conn);
//                    DBOperator.updateRecord(model.insertRData(transId, count, code), conn);
//                    DBOperator.updateRecord(model.updateInfoModel(addresId, lineDto.getBarcode()), conn);

                }
            } else {
                updateData();
                deleteLines(transId);

                for (int i = 0; i < lineSet.getSize(); i++) {
                    lineDto = (AmsItemTransLDTO) lineSet.getDTO(i);
                    DBOperator.updateRecord(((BjSendRepairModel) sqlProducer).insertLData(transId, lineDto), conn);
//                    DBOperator.updateRecord(model.updateInfoModel(addresId, lineDto.getBarcode()), conn);
                }
            }
//            saveLines(lineSet);
//            updateAddressId(barcode);
            //更新地点为送修库
            updateSpareInfo(lineSet);
            conn.commit();
            prodMessage("SPARE_SAVE_SUCCESS");
            success = true;
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
        return success;
    }

    private void updateAddressId(String[] barcode) throws SQLException {
        if (barcode != null) {
            CallableStatement cStmt = null;
            String sqlStr = "{call AMS_ITEM_TRANS.ITEM_TO_REPAIR(?,?,?)}";
            try {
                cStmt = conn.prepareCall(sqlStr);
                for (int i = 0; i < barcode.length; i++) {
                    cStmt.setString(1, barcode[i]);
                    cStmt.setString(2, Integer.toString(this.sfUser.getOrganizationId()));
                    cStmt.setString(3, Integer.toString(this.sfUser.getUserId()));
                    cStmt.execute();
                }

            } finally {
                DBManager.closeDBStatement(cStmt);
            }
        }
    }

    public void deleteLines(String transId) throws DataHandleException {
        BjSendRepairModel model = new BjSendRepairModel(sfUser, null);
        DBOperator.updateRecord(model.getDeleteByTransIdModel(transId), conn);
    }

    public void saveLines(DTOSet lineSet) throws DataHandleException {
        if (lineSet != null && !lineSet.isEmpty()) {
            BjSendRepairDAO lineDAO = new BjSendRepairDAO(sfUser, null, conn);
            for (int i = 0; i < lineSet.getSize(); i++) {
                AmsItemTransHDTO lineData = (AmsItemTransHDTO) lineSet.getDTO(i);
                lineData.setTransId(dto.getTransId());
                lineDAO.setDTOParameter(lineData);
                lineDAO.createData();
            }
        }
    }

    public RowSet produceWebData() throws QueryException {
        BjSendRepairModel model = new BjSendRepairModel(sfUser, null);
        SQLModel sqlModel = model.getSQLModel();
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        return sq.getSearchResult();
    }

    public RowSet getLines(String transId) throws QueryException {
        BjSendRepairModel model = new BjSendRepairModel(sfUser, null);
        SimpleQuery sq = new SimpleQuery(model.getByTransIdModel(transId), conn);
        sq.executeQuery();
        return sq.getSearchResult();
    }

    /**
     * 统计送修设备的数量,将送修的设备地点更改为送修
     * @param lineSet DTOSet
     */
    private void updateSpareInfo(DTOSet lineSet) throws SQLException {
        if (lineSet != null && lineSet.getSize() > 0) {
            CallableStatement cStmt = null;
            AmsItemTransLDTO lineData = null;
            conn.setAutoCommit(true);
            String sqlStr = "{call dbo.AMS_INV_TRANS2_SPARE_SEND_REPAIR(?,?,?,?,?)}";
            try {
                cStmt = conn.prepareCall(sqlStr);
                for (int i = 0; i < lineSet.getSize(); i++) {
                    lineData = (AmsItemTransLDTO) lineSet.getDTO(i);
                    barcode = lineData.getBarcode();
                    cStmt.setString(1, lineData.getBarcode());
                    cStmt.setString(2, dto.getFromObjectNo());
                    cStmt.setInt(3, lineData.getQuantity());
                    cStmt.setInt(4, this.sfUser.getUserId());
                    cStmt.registerOutParameter(5, Types.VARCHAR);
                    cStmt.execute();
                }
            } finally {
                DBManager.closeDBStatement(cStmt);
            }
        }
    }

    public AmsCustomerInfoDTO getByOU() throws QueryException {
        BjSendRepairModel model = new BjSendRepairModel(sfUser, null);
        SimpleQuery sq = new SimpleQuery(model.getByOu(), conn);
        sq.setDTOClassName(AmsCustomerInfoDTO.class.getName());
        sq.executeQuery();
        AmsCustomerInfoDTO dto1 = (AmsCustomerInfoDTO) sq.getFirstDTO();
        return dto1;
    }
    /*   public RowSet getByOU(String transId) throws QueryException {
        BjSendRepairModel model = new BjSendRepairModel(sfUser, null);
        SimpleQuery sq = new SimpleQuery(model.getByOu(), conn);
        sq.executeQuery();
        return sq.getSearchResult();
    }*/
}
