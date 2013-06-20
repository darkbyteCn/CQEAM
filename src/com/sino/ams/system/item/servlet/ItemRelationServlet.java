package com.sino.ams.system.item.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.system.fixing.dto.EtsItemInfoDTO;
import com.sino.ams.system.item.dao.ItemRelationDAO;
import com.sino.ams.system.item.model.ItemRelationModel;
import com.sino.ams.system.item.model.SetSubItemsModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.StrException;
import com.sino.base.exception.UploadException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.CheckBoxProp;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: </p>
 * <p>Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2007</p>
 * <p>Company: 北京思诺搏信息技术有限公司</p>
 * @author 何睿
 * @version 0.1
 *          Date: 2007-11-22
 */
public class ItemRelationServlet extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "/system/item/itemRelationMain.jsp";
        boolean closeWindow = false;
        Connection conn = null;
        Message message = SessionUtil.getMessage(req);
        SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
        String act = StrUtil.nullToString(req.getParameter("act"));
        String flag = StrUtil.nullToString(req.getParameter("flag"));
        String barcode = StrUtil.nullToString(req.getParameter("barcode"));
        String parentBarcode = StrUtil.nullToString(req.getParameter("parent_barcode"));
        EtsItemInfoDTO itemInfo = new EtsItemInfoDTO();
        itemInfo.setBarcode(barcode);
        itemInfo.setParentBarcode(parentBarcode);
        try {
            conn = getDBConnection(req);
            ItemRelationDAO relationDAO = new ItemRelationDAO(user, itemInfo, conn);
            if (act.equals("")) {
                if (!barcode.equals("")) {
                    BaseSQLProducer sqlProducer = new ItemRelationModel(user, itemInfo);
                    PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                    pageDAO.produceWebData();
                    req.setAttribute("SUB_ITEMS", relationDAO.getItemInfoByBarcode());
                }
            } else if (act.equals(WebActionConstant.DELETE_ACTION)) {
                String[] barcodes = req.getParameterValues("subCheck");
                if (relationDAO.deleteSubItems(barcodes)) {
                    message = getMessage("SAVE_SUCCESS");
                    forwardURL = "/servlet/com.sino.ams.system.item.servlet.ItemRelationServlet?act=";
                }
            }
            if (flag.equals("ADD")) {
                forwardURL = "/system/item/setSubItems.jsp";
                if (act.equals("")) {

                } else if (act.equals(WebActionConstant.QUERY_ACTION)) {
                    BaseSQLProducer sqlProducer = new SetSubItemsModel(user, itemInfo);
                    PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                    CheckBoxProp checkProp = new CheckBoxProp("subCheck");
                    checkProp.addDbField("BARCODE");
                    pageDAO.setWebCheckProp(checkProp);
                    pageDAO.produceWebData();
                } else if (act.equals(WebActionConstant.SAVE_ACTION)) {
                    RequestParser parser = new RequestParser();
                    CheckBoxProp checkProp = new CheckBoxProp("subCheck");
                    parser.setCheckBoxProp(checkProp);
                    parser.transData(req);
                    String[] barcodes = parser.getParameterValues("subCheck");
                    if (relationDAO.addSubItems(barcodes)) {
                        closeWindow = true;
                        message = getMessage("SAVE_SUCCESS");
                        forwardURL = "/servlet/com.sino.ams.system.item.servlet.ItemRelationServlet?flag=&act=";
                    } else {
                        forwardURL = "/servlet/com.sino.ams.system.item.servlet.ItemRelationServlet?act=" + WebActionConstant.QUERY_ACTION;
                    }
                }
            }
        } catch (PoolPassivateException e) {
            e.printLog();
            message = getMessage(MsgKeyConstant.POOL_PASSIVATE_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (QueryException e) {
            e.printLog();
            message = getMessage(MsgKeyConstant.QUERY_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (StrException e) {
            e.printLog();
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (UploadException e) {
            e.printLog();
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder sf = new ServletForwarder(req, res);
            if (closeWindow) {
                sf.forwardOpenerView(forwardURL, "保存成功");
            } else {
                sf.forwardView(forwardURL);
            }
        }
    }
}
