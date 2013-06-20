package com.sino.ams.newasset.dao;

import java.sql.Connection;

import com.sino.ams.newasset.dto.AssetsAddDTO;
import com.sino.ams.newasset.model.AssetsAddLModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: SinoIES</p>
 * <p>Description: 河南移动IES系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author ai
 * @date: 2008-3-14
 * 新增管理资产
 */
public class AssetsAddLDAO extends BaseDAO {
    private SfUserDTO sfUser = null;

    public AssetsAddLDAO(SfUserDTO userAccount, AssetsAddDTO dtoParameter,
                         Connection conn) {
        super(userAccount, dtoParameter, conn);
        sfUser = userAccount;
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AssetsAddDTO dtoPara = (AssetsAddDTO) dtoParameter;
        super.sqlProducer = new AssetsAddLModel((SfUserDTO) userAccount,
                                                dtoPara);
    }

    /**
     * 功能：插入管理资产行表(EAM)表“ETS_ASSETS_ADD_L”数据。
     */
    public void createData() throws DataHandleException {
        super.createData();
        getMessage().addParameterValue("管理资产行表(EAM)");
    }

    public RowSet getLines(String headId) throws QueryException {
        AssetsAddLModel model = (AssetsAddLModel) sqlProducer;
        SimpleQuery sq = new SimpleQuery(model.getDataByHeadIdModel(headId),
                                         conn);
        sq.executeQuery();
        return sq.getSearchResult();
    }
}
