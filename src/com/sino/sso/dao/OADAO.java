package com.sino.sso.dao;

import java.sql.Connection;

import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.sinoflow.model.PendingTrayModel2;
import com.sino.sso.model.OAModel;

/**
 * Created by IntelliJ IDEA.
 * User: zhoujs
 * Date: 2009-2-18
 * Time: 16:37:46
 * Functiion:山西OA接口相关
 */
public class OADAO {


    /**
     * 检查是否有直接变动的资产需要同步
     * @param conn      数据库连接
     * @param sfUserDTO 用户
     * @return boolean
     * @throws QueryException 查询异常
     */
    public boolean hasAssetsUpdate(Connection conn, SfUserDTO sfUserDTO) throws QueryException {
        OAModel oaModel = new OAModel(sfUserDTO);
        SQLModel sqlModel = oaModel.getAssetsUpdateModel();
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        return simpleQuery.hasResult();
    }

    public boolean hasAssetsCommit(Connection conn, SfUserDTO sfUserDTO, String transferType) throws QueryException {
        OAModel oaModel = new OAModel(sfUserDTO);
        SQLModel sqlModel = oaModel.getAssetsCommitModel(transferType);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        return simpleQuery.hasResult();
    }

    /**
     * 山西OA读取在办箱信息
     * @param loginName
     * @param conn
     * @return
     * @throws QueryException
     * @throws SQLModelException
     */
    public RowSet getInbox(String loginName, Connection conn) throws QueryException, SQLModelException {
        PendingTrayModel2 trayModel = new PendingTrayModel2(null, null);
        SimpleQuery sq = new SimpleQuery(trayModel.getPendingTrayModel(loginName), conn);
        sq.setCalPattern(CalendarConstant.LINE_PATTERN);
        sq.executeQuery();
        RowSet rs =new RowSet();
        if (sq.hasResult()) {
            rs= sq.getSearchResult();
        }
        return rs;
    }

    /**
     * 去待办数量
     * @param loginName
     * @param conn
     * @return
     * @throws QueryException
     * @throws SQLModelException
     */
    public int getInboxCount(String loginName, Connection conn) throws QueryException, SQLModelException {
        int count=0;
        PendingTrayModel2 trayModel = new PendingTrayModel2(null, null);
        SimpleQuery sq = new SimpleQuery(trayModel.getPendingTrayModel(loginName), conn);
        sq.setCalPattern(CalendarConstant.LINE_PATTERN);
        sq.executeQuery();
        if (sq.hasResult()) {
            count=sq.getSearchResult().getSize();
        }
        return count;
    }
}
