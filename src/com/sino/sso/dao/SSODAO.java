package com.sino.sso.dao;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;

import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.exception.QueryException;
import com.sino.base.util.StrUtil;
import com.sino.sso.model.SSOModel;

/**
 * Created by wwb.
 * User: demo
 * Date: 2006-12-24
 * Time: 17:22:54
 */
public class SSODAO {
    private HttpServletRequest req;
    private Connection conn;

    public SSODAO(HttpServletRequest req, Connection conn) {
        this.req = req;
        this.conn = conn;
    }

    /**
     * 收件箱+在办箱
     * @param userId
     * @throws com.sino.base.exception.QueryException
     *
     */
    public RowSet getMyWork(int userId, String actId) throws QueryException {
        String applyNumber = StrUtil.nullToString(req.getParameter("applyNumber"));
        String procName = StrUtil.nullToString(req.getParameter("procName"));
        SimpleQuery sq = new SimpleQuery(SSOModel.getMyWorkModel(userId, applyNumber, procName, actId), conn);
        sq.setCalPattern(CalendarConstant.LINE_PATTERN);
        sq.executeQuery();
        return sq.getSearchResult();
    }

    /**
     * 发件箱
     * @param userId
     * @throws com.sino.base.exception.QueryException
     *
     */
    public RowSet getMyWorked(int userId) throws QueryException {
        String fromDate = StrUtil.nullToString(req.getParameter("fromDate"));
        String toDate = StrUtil.nullToString(req.getParameter("toDate"));
        String applyNumber = StrUtil.nullToString(req.getParameter("applyNumber"));
        String procName = StrUtil.nullToString(req.getParameter("procName"));
        SimpleQuery sq = new SimpleQuery(SSOModel.getMyWorkedModel(userId, fromDate, toDate, applyNumber, procName), conn);
        sq.setCalPattern(CalendarConstant.LINE_PATTERN);
        sq.executeQuery();
        return sq.getSearchResult();
    }
}