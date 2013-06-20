package com.sino.nm.spare2.dao;


import java.sql.Connection;

import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.QueryException;

import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.nm.spare2.model.ItemCountByOuModel;

/**
 * <p>Title: SinoAMS</p>
 * <p>Description: </p>
 * <p>Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2007</p>
 * <p>Company: 北京思诺搏信息技术有限公司</p>
 * @author 何睿
 * @version 0.1
 *          Date: 2007-10-31
 */
public class ItemCountByOuDAO extends BaseDAO {
    /**
     * 功能：构造函数。
     * @param userAccount  UserAccountDTO 用户信息
     * @param dtoParameter DTO 其他与数据库交互时需要的参数。
     * @param conn         Connection 数据库连接
     */
    public ItemCountByOuDAO(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

     protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        super.sqlProducer = new ItemCountByOuModel(userAccount,dtoParameter);
    }

    public RowSet produceWebData(String itemCode,String transId) throws QueryException {
        SQLModel sqlModel = ((ItemCountByOuModel) sqlProducer).getSQLModel(itemCode,transId);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        prodMessage("UPDATE_DATA_SUCCESS");
        return sq.getSearchResult();
    }



}
