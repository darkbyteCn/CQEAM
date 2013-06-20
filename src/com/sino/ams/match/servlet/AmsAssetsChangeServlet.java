package com.sino.ams.match.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.OptionProducer;
import com.sino.ams.match.model.AmsAssetsChangeModel;
import com.sino.ams.newasset.dto.EtsFaAssetsDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;

/**
 * Created by IntelliJ IDEA.
 * User: srf
 * Date: 2008-3-17
 * Time: 22:23:55
 * To change this template use File | Settings | File Templates.
 */
public class AmsAssetsChangeServlet extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "/newasset/assetsNoMatch.jsp";
        Connection conn = null;
        Message message = SessionUtil.getMessage(req);
        SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
        String act = StrUtil.nullToString(req.getParameter("act"));

        Request2DTO req2dto = new Request2DTO();
        try {
            req2dto.setDTOClassName(EtsFaAssetsDTO.class.getName());
            EtsFaAssetsDTO itemInfo = (EtsFaAssetsDTO) req2dto.getDTO(req);
            conn = getDBConnection(req);
            OptionProducer op = new OptionProducer(user, conn);
            req.setAttribute("COUNTY_OPTION", op.getCountyOptionMIS(itemInfo.getCountyCodeMis()));
            req.setAttribute("MIS_HEADER", itemInfo);
            if (act.equals(WebActionConstant.QUERY_ACTION)) {
                AMSSQLProducer sqlProducer = new AmsAssetsChangeModel(user, itemInfo);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setPageSize(20);
                pageDAO.setCalPattern(LINE_PATTERN);
                pageDAO.produceWebData();
            }
        } catch (DTOException e) {
            e.printLog();
            message = getMessage(MsgKeyConstant.DTO_ERROR);
            message.setIsError(true);
        } catch (PoolPassivateException e) {
            e.printLog();
            message = getMessage(MsgKeyConstant.POOL_PASSIVATE_ERROR);
            message.setIsError(true);
        } catch (QueryException e) {
            e.printLog();
            message = getMessage(MsgKeyConstant.QUERY_ERROR);
            message.setIsError(true);
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder sf = new ServletForwarder(req, res);
            sf.forwardView(forwardURL);
        }
    }
}


