package com.sino.sinoflow.framework.resource.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;

import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.sinoflow.bean.optionProducer.OptionProducer;
import com.sino.sinoflow.constant.URLDefineList;
import com.sino.sinoflow.constant.WebAttrConstant;
import com.sino.sinoflow.framework.resource.dao.SFFavoritesDAO;
import com.sino.sinoflow.framework.resource.dto.SFFavoritesDTO;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;


/**
 * <p>Title: SFFavoritesServlet</p>
 * <p>Description:程序自动生成服务程序“SFFavoritesServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author yuyao
 * @version 1.0
 */


public class SFFavoritesServlet extends BaseServlet {

    /**
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = null;
        String action = req.getParameter("act");
        action = StrUtil.nullToString(action);
        Connection conn = null;
        String resParId = StrUtil.nullToString(req.getParameter("resParId"));
        PrintWriter pw = null;
        JSONArray retArray = new JSONArray();
        boolean refreshOpener = false;
        try {
            SfUserBaseDTO user = (SfUserBaseDTO) SessionUtil.getUserAccount(req);
            SFFavoritesDTO dtoParameter;
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(SFFavoritesDTO.class.getName());
            dtoParameter = (SFFavoritesDTO) req2DTO.getDTO(req);
            conn = getDBConnection(req);
            SFFavoritesDAO etsFavoritesDAO = new SFFavoritesDAO(user, dtoParameter, conn, req);

            OptionProducer op = new OptionProducer(user, conn);
            String menu = dtoParameter.getMenu();
            String menuSelect = op.getMenuValue(menu);
            req.setAttribute(WebAttrConstant.MENU_OPTION, menuSelect);

            String smallMenu = dtoParameter.getSmallMenu();
            String smallMenuSelect = op.getSmallMenuValue(resParId, smallMenu);
            req.setAttribute(WebAttrConstant.SMALL_MENU_OPTION, smallMenuSelect);


            if (action.equals("")) {
                etsFavoritesDAO.setData();
//                forwardURL = URLDefineList.INDEX_PAGE;
                action = WebActionConstant.QUERY_ACTION;
            }
            if (action.equals(WebActionConstant.QUERY_ACTION)) {

                RowSet rowSet = etsFavoritesDAO.getAllData();

                req.setAttribute("SELECTED_MENU", rowSet);

                forwardURL = URLDefineList.INDEX_PAGE;
            } else if (action.equals(WebActionConstant.NEW_ACTION)) {
                etsFavoritesDAO.setData();
                forwardURL = URLDefineList.GET_MENU_PAGE;
            } else if (action.equals("menu")) {
                res.setContentType("text/xml;charset=GBK");
                pw = res.getWriter();
                RowSet rows = etsFavoritesDAO.getMenu();
                Row row;
                if (rows != null && rows.getSize() > 0) {
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
                        retArray.put(i, row.getValue("RES_ID") + "$" + row.getValue("RES_NAME"));
                    }
                }

            } else if (action.equals("smallMenu")) {
                res.setContentType("text/xml;charset=GBK");
                pw = res.getWriter();

                RowSet rows = etsFavoritesDAO.getSmallMenu(resParId);
                Row row;
                if (rows != null && rows.getSize() > 0) {
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
                        retArray.put(i, row.getValue("SYSTEM_ID").toString() + "$" + row.getValue("RES_NAME"));
                    }
                }
            } else if (action.equals(WebActionConstant.CREATE_ACTION)) {
                etsFavoritesDAO.deleteData();
                String menus[] = req.getParameterValues("selectedMenu");
                for (int i = 0; menus != null && i < menus.length; i++) {
                    etsFavoritesDAO.insertMenu(menus[i], String.valueOf(i));
                }
                etsFavoritesDAO.setData();
                refreshOpener = true;
                forwardURL = "/servlet/com.sino.sinoflow.framework.resource.servlet.SFFavoritesServlet";
//                forwardURL = URLDefineList.GET_MENU_PAGE;
            } else {
                message = getMessage(MsgKeyConstant.INVALID_REQ);
                message.setIsError(true);
                forwardURL = MessageConstant.MSG_PRC_SERVLET;
            }
        } catch (PoolPassivateException ex) {
            Logger.logError(ex);
            message = getMessage(MsgKeyConstant.POOL_PASSIVATE_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DTOException ex) {
            Logger.logError(ex);
            message = getMessage(MsgKeyConstant.DTO_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (QueryException ex) {
            Logger.logError(ex);
            message = getMessage(MsgKeyConstant.QUERY_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (JSONException e) {
            Logger.logError(e);
            retArray.put("ERROR");
        }
        catch (ContainerException e) {
            e.printLog();
            message = getMessage(MsgKeyConstant.CONTAINER_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DataHandleException e) {
            e.printLog();
            message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder sf = new ServletForwarder(req, res);
            if (!forwardURL.equals("")) {
                if (!refreshOpener) {
                    sf.forwardView(forwardURL);
                } else {
                    sf.forwardOpenerView(forwardURL, "");
                }
            } else {
                pw.print(retArray.toString());
                pw.flush();
                pw.close();
            }

        }

    }

}