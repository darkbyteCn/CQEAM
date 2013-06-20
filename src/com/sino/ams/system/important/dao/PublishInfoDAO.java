package com.sino.ams.system.important.dao;


import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import com.sino.ams.system.important.dto.PublishInfoDTO;
import com.sino.ams.system.important.model.PublishInfoModel;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.db.query.WebPageView;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.util.UserTransaction;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.HandlerException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.StrException;
import com.sino.base.log.Logger;
import com.sino.base.web.DatabaseForWeb;
import com.sino.base.web.EventHandler;
import com.sino.base.web.EventHandlers;
import com.sino.base.web.WebRadio;


/**
 * User: T_zhoujinsong
 * Date: 2011-3-14 17:27:48
 * Function:
 */
public class PublishInfoDAO {

    SQLModel sqlModel = null;
    Connection conn = null;
    PublishInfoModel impModel = null;
    HttpServletRequest req = null;


    public PublishInfoDAO(HttpServletRequest req, Connection conn) {
        super();
        sqlModel = new SQLModel();
        impModel = new PublishInfoModel();
        this.conn = conn;
        this.req = req;
    }

    public void setModelDTO(PublishInfoDTO dto) {
        impModel.setParaDTO(dto);
    }

    public void ProdAllUsersData(HttpServletRequest req, Connection conn) throws QueryException {
        sqlModel = impModel.getLocationModel();
        WebPageView webPageView = new WebPageView(req, conn);
        webPageView.setCalPattern(CalendarConstant.LINE_PATTERN); //表示只取年月日   不取具体的时分秒
        webPageView.produceWebData(sqlModel);
    }

    public void ProdAllTitleData(HttpServletRequest req, Connection conn) throws QueryException {
        sqlModel = impModel.getLocationTitle();
        WebPageView webPageView = new WebPageView(req, conn);
        webPageView.setPageSize(23);
        webPageView.setCalPattern(CalendarConstant.LINE_PATTERN); //表示只取年月日   不取具体的时分秒
        webPageView.produceWebData(sqlModel);
    }


    public void updateInfo(Connection conn, HttpServletRequest req) throws SQLException, DataHandleException {
        UserTransaction userTrans = null;
        try {
            userTrans = new UserTransaction(conn);
            userTrans.beginTransaction();
            sqlModel = impModel.updateInfo();
            DBOperator.updateRecord(sqlModel, conn);
            userTrans.commitTransaction();
        }
        catch (DataHandleException e) {
            try {
                if (userTrans != null) {
                    userTrans.rollbackTransaction();
                }
            } catch (DataHandleException e1) {
                Logger.logError(e1);
            }
            throw e;
        }
    }

    public void insertNewInfo(Connection conn, HttpServletRequest req) throws SQLException, DataHandleException {
        UserTransaction userTrans = null;
        try {
            userTrans = new UserTransaction(conn);
            userTrans.beginTransaction();
//            SeqProducer seqProducer = new SeqProducer(conn);
            sqlModel = impModel.getInsertNewAcceptanceModel(req);
            DBOperator.updateRecord(sqlModel, conn);
            userTrans.commitTransaction();
        } catch (DataHandleException e) {
            try {
                if (userTrans != null) {
                    userTrans.rollbackTransaction();
                }
            } catch (DataHandleException e1) {
                Logger.logError(e1);
            }
            throw e;
        }
//        return neworold;
    }

    public void getImpTypesOption(String selectedValue) throws QueryException {
        SQLModel sqlModel = new SQLModel();
        String strSql = "SELECT FLV.LOOKUP_CODE,FLV.MEANING FROM FND_LOOKUP_VALUES FLV WHERE FLV.LOOKUP_TYPE = 'BULLETIN_TYPE'";
        sqlModel.setSqlStr(strSql);
        DatabaseForWeb df = new DatabaseForWeb(sqlModel, conn);
        String optionHtml = df.getOptionHtml(selectedValue, true);
        req.setAttribute("IMP_TYPES", optionHtml);
    }

    public void getSeeUserTypeOption(String selectedValue) throws QueryException {
        SQLModel sqlModel = new SQLModel();
        String strSql = "SELECT FLV.LOOKUP_CODE, FLV.MEANING\n" +
                "  FROM FND_LOOKUP_VALUES FLV\n" +
                " WHERE FLV.LOOKUP_TYPE = 'SEE_USER_TYPE'";
        sqlModel.setSqlStr(strSql);
        DatabaseForWeb df = new DatabaseForWeb(sqlModel, conn);
        String optionHtml = df.getOptionHtml(selectedValue, false);
        req.setAttribute("SEE_USER_TYPE", optionHtml);
    }

    public void setRadioProp(HttpServletRequest req, String infoType) throws StrException {
        try {
            if (infoType.equals("")) {
                infoType = "0";
            }
            WebRadio webRadio = new WebRadio("infoType");
            webRadio.addValueCaption("0", "供应商门户公告");
            webRadio.addValueCaption("1", "系统内公告");
            webRadio.setCheckedValue(infoType);
            EventHandlers handlers = new EventHandlers();
            EventHandler eventHandler = new EventHandler();
            eventHandler.setFunName("do_ChangePageDisplay");
            eventHandler.setEventName("onclick");
            handlers.addHandler(eventHandler);
            webRadio.addEventHandlers(handlers);
            req.setAttribute("notTypeRadio", webRadio);
        } catch (HandlerException ex) {
            ex.printLog();
            throw new StrException(ex);
        }
    }


    public void deleteNoticeVendor(String publishId) throws DataHandleException {
        UserTransaction userTrans = null;
        try {
            userTrans = new UserTransaction(conn);
            userTrans.beginTransaction();
            sqlModel = impModel.deleteNoticeVendorModel(publishId);
            DBOperator.updateRecord(sqlModel, conn);
            userTrans.commitTransaction();
        } catch (DataHandleException e) {
            try {
                if (userTrans != null) {
                    userTrans.rollbackTransaction();
                }
            } catch (DataHandleException e1) {
                Logger.logError(e1);
            }
            throw e;
        }
    }
}

