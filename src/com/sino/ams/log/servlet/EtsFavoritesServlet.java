package com.sino.ams.log.servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.log.dao.EtsFavoritesDAO;
import com.sino.ams.log.dto.EtsFavoritesDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.servlet.BaseServlet;
import org.json.JSONArray;
import org.json.JSONException;


/**
 * <p>Title: EtsFavoritesServlet</p>
 * <p>Description:程序自动生成服务程序“EtsFavoritesServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author yuyao
 * @version 1.0
 */


public class EtsFavoritesServlet extends BaseServlet {

    /**
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        String action = req.getParameter("act");
        action = StrUtil.nullToString(action);
        Connection conn = null;
        String resParId = StrUtil.nullToString(req.getParameter("resParId"));
        PrintWriter pw = null;
        JSONArray retArray = new JSONArray();
        boolean refreshOpener = false;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            EtsFavoritesDTO dtoParameter = null;
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(EtsFavoritesDTO.class.getName());
            dtoParameter = (EtsFavoritesDTO) req2DTO.getDTO(req);
            conn = getDBConnection(req);
            EtsFavoritesDAO etsFavoritesDAO = new EtsFavoritesDAO(user, dtoParameter, conn, req);

            ServletConfigDTO servletConfig = getServletConfig(req);
            String provinceCode = servletConfig.getProvinceCode();
            OptionProducer op = new OptionProducer(user, conn);
            String menu = dtoParameter.getMenu();
            String menuSelect = op.getMenuValue(menu);
            req.setAttribute(WebAttrConstant.MENU_OPTION, menuSelect);

            String smallMenu = dtoParameter.getSmallMenu();
            String smallMenuSelect = op.getSmallMenuValue(resParId, smallMenu);
            req.setAttribute(WebAttrConstant.SMALL_MENU_OPTION, smallMenuSelect);



            if (action.equals("")) {
                etsFavoritesDAO.setData();
                forwardURL = URLDefineList.GET_MENU_PAGE;
            } else if (action.equals(WebActionConstant.NEW_ACTION)) {
                etsFavoritesDAO.setData();
                forwardURL = URLDefineList.GET_MENU_PAGE;
            } else if (action.equals("menu")) {
                res.setContentType("text/xml;charset=GBK");
                pw = res.getWriter();
                RowSet rows = etsFavoritesDAO.getMenu();
                Row row = null;
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
                Row row = null;
                if (rows != null && rows.getSize() > 0) {
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
                        retArray.put(i, row.getValue("SYSTEM_ID").toString() + "$" + row.getValue("RES_NAME"));
                    }
                }
            } else if (action.equals(WebActionConstant.CREATE_ACTION)) {
                etsFavoritesDAO.deleteData();
                String menus[] = req.getParameterValues("selectedMenu");
                for (int i = 0; i < menus.length; i++) {
                    etsFavoritesDAO.insertMenu(menus[i], i);
                }
                etsFavoritesDAO.setData();
                refreshOpener = true;
                forwardURL = "/servlet/com.sino.ams.log.servlet.EtsFavoritesServlet";
//                forwardURL = URLDefineList.GET_MENU_PAGE;
            } else {
                message = getMessage(MsgKeyConstant.INVALID_REQ);
                message.setIsError(true);
                forwardURL = MessageConstant.MSG_PRC_SERVLET;
            }
        } catch (PoolPassivateException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.POOL_PASSIVATE_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DTOException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.DTO_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (QueryException ex) {
            ex.printLog();
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
