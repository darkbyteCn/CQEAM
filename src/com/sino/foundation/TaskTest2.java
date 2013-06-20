package com.sino.foundation;


import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.GridQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.util.CalendarUtil;

import java.sql.Connection;

public class TaskTest2 {

    private SQLModel getEAMUserSearchModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT * FROM SF_USER";
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }

    public void searchSinoEAMUsers() throws QueryException {
        Connection conn = null;
        try {
            conn = DBManager.getDBConnection();
            GridQuery gq = new GridQuery(getEAMUserSearchModel(), conn);
            gq.setPageSize(30);
            gq.executeQuery();
            while (gq.nextPage()) {
                RowSet rows = gq.getSearchResult();
                System.out.println("===============第" + (gq.getCurrPageNum()) + "页数据如下==============");
                for(int i = 0 ; i < rows.getSize(); i++){
                    System.out.println("用户姓名：" + rows.getRow(i).getStrValue("USERNAME"));
                }
            }
        } catch (PoolException ex) {
            ex.printLog();
            throw new QueryException(ex);
        } catch (ContainerException ex) {
            ex.printLog();
            throw new QueryException(ex);
        } finally {
            DBManager.closeDBConnection(conn);
        }
    }
}
