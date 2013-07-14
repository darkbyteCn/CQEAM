package com.sino.ams.yearchecktaskmanager.dao;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.yearchecktaskmanager.dto.ASSCHKTaskRemainDTO;
import com.sino.ams.yearchecktaskmanager.model.ASSCHKTaskRemainModel;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.query.WebPageView;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;

/**
 * 盘点任务提醒
 * @author Administrator
 *
 */
public class ASSCHKTaskRemainDAO {
	private Connection conn = null;
    private HttpServletRequest req = null;
    private ASSCHKTaskRemainModel model = null;
    private SfUserDTO userAccount = null;
    private ASSCHKTaskRemainDTO dtoParameter = null;


    public ASSCHKTaskRemainDAO(SfUserDTO userAccount, ASSCHKTaskRemainDTO dtoParameter, Connection conn, HttpServletRequest req) {
        this.req = req;
        this.conn = conn;
        this.userAccount = userAccount;
        this.dtoParameter = dtoParameter;
        this.model = new ASSCHKTaskRemainModel(userAccount, dtoParameter);
    }
    
    public RowSet getAllData() throws QueryException {
        SQLModel sqlModel = model.getData();
        RowSet rows = new RowSet();
        try {
            conn = DBManager.getDBConnection();
            SimpleQuery sq = new SimpleQuery(sqlModel, conn);
            sq.executeQuery();
            if (sq.hasResult()) {
                rows = sq.getSearchResult();
            }
        } catch (PoolException e) {
            rows = new RowSet();
        } finally {
            DBManager.closeDBConnection(conn);
        }
System.out.println(rows.getSize());
        return rows;
    }
    
    public void ProdAllTitleData(HttpServletRequest req, Connection conn,String taskName) throws QueryException {
        SQLModel sqlModel = model.getAllTaskRemainModel(taskName);
        WebPageView webPageView = new WebPageView(req, conn);
        webPageView.setPageSize(10);
        webPageView.setCalPattern(CalendarConstant.LINE_PATTERN); //表示只取年月日   不取具体的时分秒
        webPageView.produceWebData(sqlModel);
    }
}
