package com.sino.ams.net.equip.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.net.equip.dao.PlantMessageDAO;
import com.sino.ams.net.equip.dto.PlantMessageDTO;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;

/**
 * Created by IntelliJ IDEA.
 * User: Owner
 * Date: 2008-2-21
 * Time: 16:02:23
 * To change this template use File | Settings | File Templates.
 */
public class PlantMessageServlet extends BaseServlet {

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        String act = req.getParameter("act");
        act = StrUtil.nullToString(act);
        Connection conn = null;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            PlantMessageDTO dtoParameter = null;
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(PlantMessageDTO.class.getName());
            dtoParameter = (PlantMessageDTO) req2DTO.getDTO(req);
            conn = DBManager.getDBConnection();
            PlantMessageDAO PlantMessageDAO = new PlantMessageDAO(user, dtoParameter, conn);

            if (act.equals(WebActionConstant.DETAIL_ACTION)) {


                PlantMessageDAO.setDTOClassName(PlantMessageDTO.class.getName());
                PlantMessageDAO.setCalPattern(LINE_PATTERN);
                dtoParameter = (PlantMessageDTO) PlantMessageDAO.getDataByPrimaryKey();
                if (dtoParameter == null) {
                    dtoParameter = new PlantMessageDTO();
                    message = getMessage(AssetsMessageKeys.DATA_NOT_EXIST);
                    message.setIsError(true);
                }

                req.setAttribute(WebAttrConstant.ASSETS_DATA, dtoParameter);
                forwardURL = URLDefineList.PLANTMESSAGE;
            }

        } catch (PoolPassivateException e) {
            e.printLog();
            Logger.logError(e.toString());
        } catch (DTOException e) {
            e.printLog();
            Logger.logError(e.toString());
        } catch (QueryException e) {
            e.printLog();
            Logger.logError(e.toString());
        } catch (PoolException e) {
            e.printLog();
            Logger.logError(e.toString());
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
        }
    }
}
