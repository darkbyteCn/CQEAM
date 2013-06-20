package com.sino.nm.ams.mss.dao;

import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.nm.ams.mss.dto.MssAssetsAddDTO;
import com.sino.nm.ams.mss.model.MssAssetsAddLModel;

import java.sql.Connection;

/**
 * Created by IntelliJ IDEA.
 * User: T_yuyao
 * Date: 2011-4-26
 * Time: 11:52:11
 * To change this template use File | Settings | File Templates.
 */
public class MssAssetsAddLDAO extends BaseDAO {
    private SfUserDTO sfUser = null;

    public MssAssetsAddLDAO(SfUserDTO userAccount, MssAssetsAddDTO dtoParameter,
                         Connection conn) {
        super(userAccount, dtoParameter, conn);
        sfUser = userAccount;
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        MssAssetsAddDTO dtoPara = (MssAssetsAddDTO) dtoParameter;
        super.sqlProducer = new MssAssetsAddLModel((SfUserDTO) userAccount,
                                                dtoPara);
    }

    /**
     * 功能：插入管理资产行表(AMS)表“ETS_ASSETS_ADD_L”数据。
     */
    public void createData() throws DataHandleException {
        super.createData();
        getMessage().addParameterValue("管理资产行表(AMS)");
    }

    public RowSet getLines(String headId) throws QueryException {
        MssAssetsAddLModel model = (MssAssetsAddLModel) sqlProducer;
        SimpleQuery sq = new SimpleQuery(model.getDataByHeadIdModel(headId),
                                         conn);
        sq.executeQuery();
        return sq.getSearchResult();
    }
}
