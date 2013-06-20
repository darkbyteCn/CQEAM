package com.sino.ams.spare.dao;

import java.sql.Connection;

import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;

import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.ams.spare.dto.AmsItemTransLDTO;
import com.sino.ams.spare.model.AmsItemTransLModel;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-12-02
 * Time: 00:00:00
 * To change this template use File | Settings | File Templates.
 */

public class AmsItemTransLDAO extends BaseDAO {

    private SfUserDTO sfUser = null;

    public AmsItemTransLDAO(SfUserDTO userAccount, AmsItemTransLDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        sfUser = userAccount;
    }

    protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) {
        AmsItemTransLDTO dtoPara = (AmsItemTransLDTO)dtoParameter;
        super.sqlProducer = new AmsItemTransLModel((SfUserDTO)userAccount, dtoPara);
    }

    /**
     * 功能：插入备件事务行表(AMS)表“AMS_ITEM_TRANS_L”数据。
     */
    public void createData() throws DataHandleException {
        super.createData();
        getMessage().addParameterValue("备件事务行表(AMS)");
    }

    /**
     * 功能：更新备件事务行表(AMS)表“AMS_ITEM_TRANS_L”数据。
     */
    public void updateData() throws DataHandleException {
        super.updateData();
        getMessage().addParameterValue("备件事务行表(AMS)");
    }

    /**
     * 功能：删除备件事务行表(AMS)表“AMS_ITEM_TRANS_L”数据。
     */
    public void deleteData() throws DataHandleException {
        super.deleteData();
        getMessage().addParameterValue("备件事务行表(AMS)");
    }

    public RowSet getLines(String transId) throws QueryException {
        AmsItemTransLModel model = (AmsItemTransLModel) sqlProducer;
        SimpleQuery sq = new SimpleQuery(model.getDataByTransIdModel(transId),conn);
        sq.executeQuery();
        return sq.getSearchResult();
    }
     public RowSet getLines2(String transId) throws QueryException {
        AmsItemTransLModel model = (AmsItemTransLModel) sqlProducer;
        SimpleQuery sq = new SimpleQuery(model.getDataByTransIdMode2(transId),conn);
        sq.executeQuery();
        return sq.getSearchResult();
    }
    public RowSet getPrintLine(String transId) throws QueryException {
        AmsItemTransLModel model = (AmsItemTransLModel) sqlProducer;
        SimpleQuery sq = new SimpleQuery(model.getPrintDataByTransIdMode(transId),conn);
        sq.executeQuery();
        return sq.getSearchResult();
    }
    public RowSet getPrintAllot(String transId) throws QueryException {
        AmsItemTransLModel model = (AmsItemTransLModel) sqlProducer;
        SimpleQuery sq = new SimpleQuery(model.getPrintAllotByTransIdMode(transId),conn);
        sq.executeQuery();
        return sq.getSearchResult();
    }
     public RowSet getLines3(String transId) throws QueryException {
        AmsItemTransLModel model = (AmsItemTransLModel) sqlProducer;
        SimpleQuery sq = new SimpleQuery(model.getDataByTransIdMode3(transId),conn);
        sq.executeQuery();
        return sq.getSearchResult();
    }
      public RowSet getLines4(String transId) throws QueryException {
        AmsItemTransLModel model = (AmsItemTransLModel) sqlProducer;
        SimpleQuery sq = new SimpleQuery(model.getDataByTransIdMode4(transId),conn);
        sq.executeQuery();
        return sq.getSearchResult();
    }
     public RowSet getLines1(String transId) throws QueryException {
        AmsItemTransLModel model = (AmsItemTransLModel) sqlProducer;
        SimpleQuery sq = new SimpleQuery(model.getDataByTransIdModel1(transId),conn);
        sq.executeQuery();
        return sq.getSearchResult();
    }
}
