package com.sino.ams.spare.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sino.base.db.conn.DBManager;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.log.Logger;
import com.sino.base.util.CalendarUtil;

import com.sino.framework.dto.BaseUserDTO;
import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.bean.OrderNumGenerator;
import com.sino.ams.spare.dto.AmsItemTransHDTO;
import com.sino.ams.spare.dto.AmsItemTransLDTO;
import com.sino.ams.spare.model.AmsItemTransHModel;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-12-02
 * Time: 00:00:00
 * To change this template use File | Settings | File Templates.
 */
public class SpareBFDAO extends AMSBaseDAO {

    private SfUserDTO sfUser = null;
    private AmsItemTransHDTO amsItemTransH = null;

    public SpareBFDAO(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        this.sfUser = (SfUserDTO) userAccount;
        this.amsItemTransH = (AmsItemTransHDTO) super.dtoParameter;
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        super.sqlProducer = new AmsItemTransHModel((SfUserDTO) userAccount, (AmsItemTransHDTO) dtoParameter);
    }

    public boolean submitOrder(DTOSet lineSet, HttpServletRequest req) throws SQLException {
        boolean success = true;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            AmsItemTransHDAO aitDAO = new AmsItemTransHDAO(sfUser, amsItemTransH, conn);
            String transId = amsItemTransH.getTransId();
            amsItemTransH.setTransDate(CalendarUtil.getCurrDate());
            OrderNumGenerator ong = new OrderNumGenerator(conn, sfUser.getCompanyCode(), amsItemTransH.getTransType());
            amsItemTransH.setTransNo(ong.getOrderNum());
            if (transId.equals("")) {
                SeqProducer seq = new SeqProducer(conn);
                transId = seq.getGUID();
                amsItemTransH.setTransId(transId);
                createData();
            } else {
                updateData();
                //有数据，删行信息
                aitDAO.deleteLines(transId);
            }
            aitDAO.saveLines(lineSet);   //插入行信息
            SpareBF(lineSet);
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
    }

   /**
     * 从待修库或送修库转到报废库
     * @param dtoSet DTOSet
     * @throws SQLException
     */
    private void SpareBF(DTOSet dtoSet) throws SQLException {
        if (dtoSet != null && !dtoSet.isEmpty()) {
            CallableStatement cStmt = null;
            String sqlStr = "{call dbo.AITS_SPARE_BF(?,?,?,?,?,?)}";
            try {
                cStmt = conn.prepareCall(sqlStr);
                for (int i = 0; i < dtoSet.getSize(); i++) {
                    AmsItemTransLDTO lineData = (AmsItemTransLDTO) dtoSet.getDTO(i);
                    cStmt.setString(1, lineData.getBarcode());
                    cStmt.setString(2, lineData.getItemCode());
                    cStmt.setInt(3, lineData.getQuantity());
                    cStmt.setInt(4, this.amsItemTransH.getToOrganizationId());
                    cStmt.setInt(5, this.sfUser.getUserId());
                    cStmt.setString(6, amsItemTransH.getFromObjectNo());
                    cStmt.execute();
                }
            } finally {
                DBManager.closeDBStatement(cStmt);
            }
        }
    }


   private void updateQty(DTOSet lineSet) throws DataHandleException {
     if (lineSet != null && !lineSet.isEmpty()) {
       List sqModels = new ArrayList();
       AmsItemTransLDTO lineData = null;
       for (int i= 0;i<lineSet.getSize();i++){
       lineData = (AmsItemTransLDTO) lineSet.getDTO(i);
       SQLModel sqlModel = new SQLModel();
       List  sqlArgs = new ArrayList();
       String  sqlStr = " UPDATE AMS_SPARE_INFO\n" +
               "       SET QUANTITY         = QUANTITY - ?,\n" +
               "           LAST_UPDATE_DATE = SYSDATE,\n" +
               "           LAST_UPDATE_BY   = ?\n" +
               "     WHERE SPARE_ID = ?";
       sqlArgs.add(lineData.getQuantity()) ;
       sqlArgs.add(sfUser.getUserId());
       sqlArgs.add(lineData.getSpareId());
       sqlModel.setSqlStr(sqlStr);
       sqlModel.setArgs(sqlArgs);
       sqModels.add(sqlModel);
        }
       DBOperator.updateBatchRecords(sqModels, conn);
   }
  }

}
