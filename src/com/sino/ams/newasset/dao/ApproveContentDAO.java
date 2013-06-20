package com.sino.ams.newasset.dao;

import java.sql.Connection;

import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.QueryException;
import com.sino.flow.model.ApproveContentModel;

/**
 * Created by IntelliJ IDEA.
 * User: mshtang
 * Date: 2008-5-28
 * Time: 16:06:03
 * To change this template use File | Settings | File Templates.
 */
public class ApproveContentDAO {

    public static RowSet getApproveContent(Connection conn, String appId,
                                           String tableName) throws
            QueryException {
        SQLModel sqlModel = ApproveContentModel.getContentModel(appId,
                tableName);
        SimpleQuery simp = new SimpleQuery(sqlModel, conn);
        simp.executeQuery();
        return simp.getSearchResult();
    }
}
