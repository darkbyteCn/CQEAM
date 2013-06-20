package com.sino.ams.match.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.OptionProducer;
import com.sino.ams.match.model.AmsAssetsLeasingModel;
import com.sino.ams.newasset.dao.AmsAssetsMatchDAO;
import com.sino.ams.system.fixing.dto.EtsItemInfoDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
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
 * Time: 22:19:10
 * To change this template use File | Settings | File Templates.
 */
public class AmsAssetsLeasingServlet extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "/newasset/assetsLeasing.jsp";
        Connection conn = null;
        Message message = SessionUtil.getMessage(req);
        SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
        String act = StrUtil.nullToString(req.getParameter("act"));
        boolean matchSuccess = false;
        String showMsg = "";
        Request2DTO req2dto = new Request2DTO();
        try {
            req2dto.setDTOClassName(EtsItemInfoDTO.class.getName());
            EtsItemInfoDTO itemInfo = (EtsItemInfoDTO) req2dto.getDTO(req);

            conn = getDBConnection(req);
            OptionProducer op = new OptionProducer(user, conn);
            req.setAttribute("COUNTY_OPTION", op.getCountyOption(itemInfo.getCountyCode()));
            req.setAttribute("AMS_HEADER", itemInfo);
            AmsAssetsMatchDAO matchDAO = new AmsAssetsMatchDAO(user, itemInfo, conn);
            if (act.equals(WebActionConstant.QUERY_ACTION)) {
                AMSSQLProducer sqlProducer = new AmsAssetsLeasingModel(user, itemInfo);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setPageSize(20);
                 pageDAO.setCalPattern(LINE_PATTERN);
                pageDAO.produceWebData();
            } else if (act.equals(WebActionConstant.SAVE_ACTION)) {   //单个匹配
                String barcode = req.getParameter("tempRadio1");
                String assetId = StrUtil.nullToString(req.getParameter("tempRadio2"));
                matchSuccess = matchDAO.saveItemMatch(barcode, assetId);
                showMsg = "租赁转资处理!";
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
        } catch (SQLException e) {
            Logger.logError(e);
            message = getMessage(MsgKeyConstant.SQL_ERROR);
            message.setIsError(true);
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            if (matchSuccess) {
                res.setContentType("text/html;charset=GBK");
                PrintWriter out = res.getWriter();
                out.print("<script language=\"javascript\">\n");
                out.println("alert(\"" + showMsg + "\");");
                out.println("location.href=\"/servlet/com.sino.ams.match.servlet.AmsAssetsLeasingServlet?act="+WebActionConstant.QUERY_ACTION+"\";");
                out.println("parent.misInfo.document.forms[0].submit();");
                out.print("</script>");
            } else {
                ServletForwarder sf = new ServletForwarder(req, res);
                sf.forwardView(forwardURL);
            }
        }
    }
}

