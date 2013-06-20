package com.sino.nm.spare2.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import com.sino.ams.bean.OrderNumGenerator;
import com.sino.ams.constant.OracleAppErrorCode;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.util.CalendarUtil;
import com.sino.base.util.ConvertUtil;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.nm.spare2.dto.AmsItemTransHDTO;
import com.sino.nm.spare2.dto.AmsItemTransLDTO;
import com.sino.nm.spare2.model.AmsItemTransHModel;
import com.sino.nm.spare2.model.BjckModel;

/**
 * <p>Title: SinoAMS</p>
 * <p>Description: </p>
 * <p>Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2007</p>
 * <p>Company: 北京思诺搏信息技术有限公司</p>
 * @author 何睿
 * @version 0.1
 *          Date: 2007-11-12
 */
public class BjckDAO extends BaseDAO {

    private SfUserDTO sfUser = null;
    private AmsItemTransHDTO amsItemTransH = null;
    private String barcode = "";

    /**
     * 功能：构造函数。
     * @param userAccount  UserAccountDTO 用户信息
     * @param dtoParameter DTO 其他与数据库交互时需要的参数。
     * @param conn         Connection 数据库连接
     */
    public BjckDAO(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        this.sfUser = (SfUserDTO) userAccount;
        this.amsItemTransH = (AmsItemTransHDTO) super.dtoParameter;
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        super.sqlProducer = new AmsItemTransHModel((SfUserDTO) userAccount, (AmsItemTransHDTO) dtoParameter);
    }

    public boolean submitOrder(DTOSet lineSet, HttpServletRequest req) throws SQLException {
        boolean success = false;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            AmsItemTransHDAO aitDAO = new AmsItemTransHDAO(sfUser, amsItemTransH, conn);
            String transId = amsItemTransH.getTransId();
//            amsItemTransH.setTransStatus(DictConstant.COMPLETED);
            amsItemTransH.setTransDate(CalendarUtil.getCurrDate());

            if (transId.equals("")) {
                SeqProducer seq = new SeqProducer(conn);
                transId = seq.getGUID();
                amsItemTransH.setTransId(transId);
                OrderNumGenerator ong = new OrderNumGenerator(conn, sfUser.getCompanyCode(), amsItemTransH.getTransType());
                amsItemTransH.setTransNo(ong.getOrderNum());
                createData();
            } else {
                updateData();
                //有数据，删行信息
                aitDAO.deleteLines(transId);
            }
            aitDAO.saveLines(lineSet);
//            if (amsItemTransH.getTransType().equals(DictConstant.BJRK)) {
//                addToItemInfo(lineSet);
//            }
//            updateAddressIdOnWay(lineSet);  //方案1
            spareOutInv(lineSet);            //方案2
//            FlowBusiness fb = new FlowBusiness(conn, req, transId, amsItemTransH.getTransNo(), sfUser.getUserId(), sfUser.getUsername());
//            fb.flow2Next("", true);
            conn.commit();
            prodMessage("SPARE_SAVE_SUCCESS");
            success = true;
        } catch (SQLException e) {
            conn.rollback();
            Logger.logError(e);
            if (e.getErrorCode() == OracleAppErrorCode.QUANTITY_NOT_ENOUGH) {
                prodMessage("QUANTITY_NOT_ENOUGH");
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

    /**
     * 更新条码所在仓库为在途库
     * @param dtoSet DTOSet
     * @throws SQLException
     */
    private void updateAddressIdOnWay(DTOSet dtoSet) throws SQLException {
        if (dtoSet != null && !dtoSet.isEmpty()) {
            CallableStatement cStmt = null;
            String sqlStr = "{call AMS_ITEM_TRANS.ITEM_ON_THE_WAY(?,?,?)}";
            try {
                cStmt = conn.prepareCall(sqlStr);
                for (int i = 0; i < dtoSet.getSize(); i++) {
                    AmsItemTransLDTO lineData = (AmsItemTransLDTO) dtoSet.getDTO(i);
                    barcode = lineData.getBarcode();
                    cStmt.setString(1, barcode);
                    cStmt.setString(2, ConvertUtil.int2String(this.amsItemTransH.getToOrganizationId()));
                    cStmt.setString(3, ConvertUtil.int2String(this.sfUser.getUserId()));
                    cStmt.execute();
                }
            } finally {
                DBManager.closeDBStatement(cStmt);
            }
        }
    }

    /**
     * 写AMS_SPARE_INFO表
     * @param lineSet     行数据
     * @throws DataHandleException
     */
    private void spareOutInv(DTOSet lineSet) throws DataHandleException, SQLException {
        if (lineSet != null && !lineSet.isEmpty()) {
            CallableStatement cStmt = null;
            conn.setAutoCommit(true);
            String sqlStr = "{call dbo.AMS_INV_TRANS2_SPARE_OUT_INV(?,?,?,?,?)}";
            try {
                cStmt = conn.prepareCall(sqlStr);
                for (int i = 0; i < lineSet.getSize(); i++) {
                    AmsItemTransLDTO lineData = (AmsItemTransLDTO) lineSet.getDTO(i);
                    barcode = lineData.getBarcode();
                    cStmt.setString(1, barcode);
                    cStmt.setString(2, lineData.getItemCode());
                    cStmt.setString(3, amsItemTransH.getFromObjectNo());
                    cStmt.setInt(4, lineData.getQuantity());
                    cStmt.setInt(5, sfUser.getUserId());
                    cStmt.execute();
                }
            } finally {
                DBManager.closeDBStatement(cStmt);
            }
        }
    }

    /**
     * 备件出库行信息
     * @param transId 头ID
     * @return RowSet
     * @throws QueryException
     */
    public RowSet getLines(String transId) throws QueryException {
        BjckModel model = new BjckModel(userAccount, dtoParameter);
        SimpleQuery sq = new SimpleQuery(model.getDataByForeignKeyModel(transId),conn);
        sq.executeQuery();
        return sq.getSearchResult();
    }
}
