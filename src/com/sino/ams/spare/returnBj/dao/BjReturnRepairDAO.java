package com.sino.ams.spare.returnBj.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.ContainerException;
import com.sino.base.log.Logger;
import com.sino.base.util.CalendarUtil;
import com.sino.base.util.StrUtil;
import com.sino.base.data.RowSet;
import com.sino.base.data.Row;

import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.ams.bean.OrderNumGenerator;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.OracleAppErrorCode;
import com.sino.ams.spare.allot.dto.AmsBjsAllotHDTO;
import com.sino.ams.spare.dto.AmsItemTransLDTO;
import com.sino.ams.spare.returnBj.model.BjReturnRepairModel;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2007-11-12
 * Time: 9:34:22
 */
public class BjReturnRepairDAO extends BaseDAO {
    private SfUserDTO sfUser = null;
    private AmsBjsAllotHDTO dto = null;
    private BjReturnRepairModel sqlModel = null;
    private String barcode = "";

    public BjReturnRepairDAO(SfUserDTO userAccount, AmsBjsAllotHDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        this.sfUser = userAccount;
        this.dto = (AmsBjsAllotHDTO) super.dtoParameter;
        sqlModel = (BjReturnRepairModel) sqlProducer;
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsBjsAllotHDTO dtoPara = (AmsBjsAllotHDTO) dtoParameter;
        super.sqlProducer = new BjReturnRepairModel((SfUserDTO) userAccount, dtoPara);

    }

    public void createData() throws DataHandleException {
        super.createData();
        getMessage().addParameterValue("备件事务头表(AMS)");
    }

    public void updateData() throws DataHandleException {
        super.updateData();
        getMessage().addParameterValue("备件事务头表(AMS)");
    }

    public void deleteData() throws DataHandleException {
        super.deleteData();
        getMessage().addParameterValue("备件事务头表(AMS)");
    }

    public boolean saveData(DTOSet lineSet) throws SQLException {
        boolean success = false;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            String transId = dto.getTransId();
            dto.setCreationDate(CalendarUtil.getCurrDate());
            dto.setTransType(DictConstant.BJFH);
            dto.setTransStatus(DictConstant.SAVE_TEMP);
            AmsItemTransLDTO lineDto = null;
            if (transId.equals("")) {
                OrderNumGenerator ong = new OrderNumGenerator(conn, sfUser.getCompanyCode(), DictConstant.BJFH);
                dto.setTransNo(ong.getOrderNum());
                SeqProducer seq = new SeqProducer(conn);
//                transId = seq.getStrNextSeq("AMS_ITEM_TRANS_H_S");
                transId = seq.getGUID();
                dto.setTransId(transId);
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
            dto.setTransType(DictConstant.BJFH);
            AmsItemTransLDTO lineDto = null;
            if (transId.equals("")) {
                OrderNumGenerator ong = new OrderNumGenerator(conn, sfUser.getCompanyCode(), "BJFH");
                dto.setTransNo(ong.getOrderNum());
                SeqProducer seq = new SeqProducer(conn);
//                transId = seq.getStrNextSeq("AMS_ITEM_TRANS_H_S");
                transId = seq.getGUID();
                dto.setTransId(transId);
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
            updateSpareInfo(lineSet);
            conn.commit();
            prodMessage("SPARE_SAVE_SUCCESS");
            success = true;
        } catch (SQLException e) {
            conn.rollback();
            Logger.logError(e);
            if (e.getErrorCode() == OracleAppErrorCode.spareNotEnough2) {
                prodMessage("SPARE_NOT_ENOUGH2");
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

    public void deleteLines(String transId) throws DataHandleException {
        BjReturnRepairModel model = new BjReturnRepairModel(sfUser, null);
        DBOperator.updateRecord(model.getDeleteByTransIdModel(transId), conn);
    }

    public void saveLines(DTOSet lineSet) throws DataHandleException {
        if (lineSet != null && !lineSet.isEmpty()) {
            BjReturnRepairDAO lineDAO = new BjReturnRepairDAO(sfUser, null, conn);
            for (int i = 0; i < lineSet.getSize(); i++) {
                AmsBjsAllotHDTO lineData = (AmsBjsAllotHDTO) lineSet.getDTO(i);
                lineData.setTransId(dto.getTransId());
                lineDAO.setDTOParameter(lineData);
                lineDAO.createData();
            }
        }
    }

    public DTOSet getLines(String transId) throws QueryException {
        SimpleQuery sq = new SimpleQuery(sqlModel.getDataByForeignKeyModel(transId), conn);
        sq.setDTOClassName(AmsItemTransLDTO.class.getName());
        sq.executeQuery();
        return sq.getDTOSet();
    }

    /**
     * 更新备件信息:送修库-->正常库
     * @param lineSet 行信息
     * @throws SQLException
     */
    private void updateSpareInfo(DTOSet lineSet) throws SQLException {
        if (lineSet != null && lineSet.getSize() > 0) {
            CallableStatement cStmt = null;
            AmsItemTransLDTO lineData = null;
            String sqlStr = "{call AMS_ITEM_TRANS_SX.SPARE_RETURN_REPAIR(?,?,?,?,?,?)}";
            try {
                cStmt = conn.prepareCall(sqlStr);
                for (int i = 0; i < lineSet.getSize(); i++) {
                    lineData = (AmsItemTransLDTO) lineSet.getDTO(i);
                    barcode = lineData.getBarcode();
                    cStmt.setString(1, lineData.getBarcode());
                    cStmt.setString(2, dto.getFromObjectNo());
                    cStmt.setString(3, dto.getToObjectNo());
                    cStmt.setInt(4, this.sfUser.getOrganizationId());
                    cStmt.setInt(5, this.sfUser.getUserId());
                    cStmt.setInt(6, lineData.getQuantity());
                    cStmt.execute();
                }
            } finally {
                DBManager.closeDBStatement(cStmt);
            }
        }
    }

    public String getFromObjectName(String transId) throws DataHandleException, QueryException, ContainerException {
        String fromObjectName = "";
        BjReturnRepairModel model = new BjReturnRepairModel(sfUser, null);
        SQLModel sqlModel = model.getFromObjectNameModel(transId);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        RowSet rs = simpleQuery.getSearchResult();
        if (rs != null && rs.getSize() > 0) {
            Row row = rs.getRow(0);
            fromObjectName = StrUtil.nullToString(row.getValue("WORKORDER_OBJECT_NAME"));
        }
        return fromObjectName;
    }

    public String getToObjectName(String transId) throws DataHandleException, QueryException, ContainerException {
        String fromObjectName = "";
        BjReturnRepairModel model = new BjReturnRepairModel(sfUser, null);
        SQLModel sqlModel = model.getToObjectNameModel(transId);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        RowSet rs = simpleQuery.getSearchResult();
        if (rs != null && rs.getSize() > 0) {
            Row row = rs.getRow(0);
            fromObjectName = StrUtil.nullToString(row.getValue("COMPANY"));
        }
        return fromObjectName;
    }
}
