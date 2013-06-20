package com.sino.ams.log.dao;


import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.base.web.DatabaseForWeb;
import com.sino.ams.log.dto.EtsFavoritesDTO;
import com.sino.ams.log.model.EtsFavoritesModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.sso.dao.OADAO;


/**
 * <p>Title: EtsFavoritesDAO</p>
 * <p>Description:程序自动生成服务程序“EtsFavoritesDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author yuyao
 * @version 1.0
 */


public class EtsFavoritesDAO {

    private Connection conn = null;
    private HttpServletRequest req = null;
    private EtsFavoritesModel model = null;
    private SfUserDTO userAccount = null;
    private EtsFavoritesDTO dtoParameter = null;


    public EtsFavoritesDAO(SfUserDTO userAccount, EtsFavoritesDTO dtoParameter, Connection conn, HttpServletRequest req) {
        this.req = req;
        this.conn = conn;
        this.userAccount = userAccount;
        this.dtoParameter = dtoParameter;
        this.model = new EtsFavoritesModel(userAccount, dtoParameter);
    }

    //以下是通过选择父栏目  然后传个参数  选择子栏目
    public RowSet getMenu() throws QueryException {
        SQLModel sqlModel = model.getMenu();
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        return sq.getSearchResult();
    }

    public RowSet getSmallMenu(String sfResId) throws QueryException {
        SQLModel sqlModel = model.getSmallMenu(sfResId);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        return sq.getSearchResult();

    }

    public void setMenu() throws QueryException {
        String menu = StrUtil.nullToString(req.getParameter("menu"));
        SQLModel sqlModel = model.getMenu();
        DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
        String optionHtml = webFieldProducer.getOptionHtml(menu, true);
        req.setAttribute("MENU_OPTION", optionHtml);
    }

    public void setSmallMenu() throws QueryException {
        String smallMenu = StrUtil.nullToString(req.getParameter("smallMenu"));
        String resParId = StrUtil.nullToString(req.getParameter("resParId"));
        SQLModel sqlModel = model.getSmallMenu(resParId);
        DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
        String optionHtml = webFieldProducer.getOptionHtml(smallMenu, false);
        req.setAttribute("SMALL_MENU_OPTION", optionHtml);
    }

    public void setData() throws QueryException {
        String selectedMenu = StrUtil.nullToString(req.getParameter("selectedMenu"));
        SQLModel sqlModel = model.getData();
        DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
        String optionHtml = webFieldProducer.getOptionHtml(selectedMenu, false);
        req.setAttribute("SELECTED_MENU_OPTION", optionHtml);
    }

    public void insertMenu(String menus, int i) throws SQLException, DataHandleException, QueryException {
        SQLModel sqlModel = model.insertData(menus, i);
        DBOperator.updateRecord(sqlModel, conn);
    }

    public void deleteData() throws DataHandleException {
        SQLModel sqlModel = model.deleteData();
        DBOperator.updateRecord(sqlModel, conn);
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
            for (int i = 0; i < rows.getSize(); i++) {
                Row row = rows.getRow(i);
                try {
                    if (row.getStrValue("RES_NAME").equals("在办箱")) {
                        String resName = row.getStrValue("RES_NAME");
                        OADAO oaDAO = new OADAO();
                        int count = oaDAO.getInboxCount(userAccount.getLoginName(), conn);
                        row.setField("RES_NAME", resName + " 【" + count + "】");
                        rows.set(i, row);
                    }
                } catch (ContainerException e) {
                    Logger.logError(e);
                } catch (SQLModelException e) {
                    Logger.logError(e);
                }
            }
        } catch (PoolException e) {
            rows = new RowSet();
        } finally {
            DBManager.closeDBConnection(conn);
        }

        return rows;
    }

    public Row getInboxNum() throws QueryException {
        SQLModel sqlModel = model.getNewInboxCountModel(null);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        Row row = sq.getFirstRow();
        return row;
    }

    public Row getHalfBoxNum() throws QueryException {
        SQLModel sqlModel = model.getPendingCountModel(null);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        Row row = sq.getFirstRow();
        return row;
    }

    public Row getPendingNum() throws QueryException {
        SQLModel sqlModel = model.getPendingNum();
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        Row row = sq.getFirstRow();
        return row;
    }

    public Row getFaAssetsNum() throws QueryException {
        SQLModel sqlModel = model.getFaAssetsNum();
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        Row row = sq.getFirstRow();
        return row;
    }

    public Row getReceiveNum() throws QueryException, SQLModelException {
        SQLModel sqlModel = model.getReceiveNum();
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        Row row = sq.getFirstRow();
        return row;
    }


}