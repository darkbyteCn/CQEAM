package com.sino.sinoflow.framework.resource.dao;


import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.util.StrUtil;
import com.sino.base.web.DatabaseForWeb;
import com.sino.sinoflow.framework.resource.dto.SFFavoritesDTO;
import com.sino.sinoflow.framework.resource.model.SFFavoritesModel;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;


/**
 * <p>Title: EtsFavoritesDAO</p>
 * <p>Description:程序自动生成服务程序“EtsFavoritesDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author yuyao
 * @version 1.0
 */


public class SFFavoritesDAO {

    private Connection conn = null;
    private HttpServletRequest req = null;
    private SFFavoritesModel model = null;
    private SfUserBaseDTO userAccount = null;
    private SFFavoritesDTO dtoParameter = null;


    public SFFavoritesDAO(SfUserBaseDTO userAccount, SFFavoritesDTO dtoParameter, Connection conn, HttpServletRequest req) {
        this.req = req;
        this.conn = conn;
        this.userAccount = userAccount;
        this.dtoParameter = dtoParameter;
        this.model = new SFFavoritesModel(userAccount, dtoParameter);


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

    public void insertMenu(String menus,String i) throws SQLException, DataHandleException, QueryException {
        SQLModel sqlModel = model.insertData(menus,i);
        DBOperator.updateRecord(sqlModel, conn);

    }

    public void deleteData() throws DataHandleException {
        SQLModel sqlModel = model.deleteData();
        DBOperator.updateRecord(sqlModel, conn);
    }

    public RowSet getAllData() throws QueryException {
        SQLModel sqlModel = model.getData();
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        RowSet rows = sq.getSearchResult();
//        req.setAttribute("SELECTED_MENU", rows);
        return rows;
    }
}