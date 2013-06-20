package com.sino.ams.match.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.match.dao.ManualMatchDAO;
import com.sino.ams.system.fixing.dto.EtsItemInfoDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: </p>
 * <p>Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2007</p>
 * <p>Company: 北京思诺搏信息技术有限公司</p>
 * @author 何睿
 * @version 0.1
 *          Date: 2007-11-30
 */
public class ManualMatchServlet extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Connection conn = null;
        Message message = SessionUtil.getMessage(req);
        SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
        String act = StrUtil.nullToString(req.getParameter("act"));
        boolean matchSuccess = false;
        Request2DTO req2dto = new Request2DTO();
        ManualMatchDAO matchDAO = null;
        try {
            req2dto.setDTOClassName(EtsItemInfoDTO.class.getName());
            EtsItemInfoDTO itemInfo = (EtsItemInfoDTO) req2dto.getDTO(req);

            conn = getDBConnection(req);
            matchDAO = new ManualMatchDAO(user, itemInfo, conn);
            String systemid = req.getParameter("systemid");
            String assetId = StrUtil.nullToString(req.getParameter("assetId"));
            if (act.equals("MatchByLocation")) {       //按地点匹配
                matchSuccess = matchDAO.matchByType(systemid, assetId,1);
            } else if (act.equals("MatchByCounty")) {  //按县匹配
                matchSuccess = matchDAO.matchByType(systemid, assetId,2);
            } else if (act.equals("MatchByCity")) {    //按市匹配
                matchSuccess = matchDAO.matchByType(systemid, assetId,3);
            }
        } catch (DTOException e) {
            e.printLog();
            message = getMessage(MsgKeyConstant.DTO_ERROR);
            message.setIsError(true);
        } catch (PoolPassivateException e) {
            e.printLog();
            message = getMessage(MsgKeyConstant.POOL_PASSIVATE_ERROR);
            message.setIsError(true);
        } catch (SQLException e) {
            Logger.logError(e);
            message = getMessage(MsgKeyConstant.SQL_ERROR);
            message.setIsError(true);
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            res.setContentType("text/html;charset=GBK");
            PrintWriter out = res.getWriter();

//            out.println("alert(\"" + showMsg + "\");");
            String outPutStr = "对不起,匹配过程中出错了,请通知相关人员,谢谢!";
            if (matchSuccess) {
                outPutStr = "一共匹配了<font color='red'>" + matchDAO.getMatchedItemCount() + "</font>条记录!";
            }
            //在showModalDialog弹出的窗口中无法取到值
            /*out.print("<script language=\"javascript\">\n");
            out.print("parent.document.getElementById(\"matchNumber\").innerText = " + outPutStr);
            out.println(";");
            out.println("parent.document.getElementById(\"wait\").style.display = \"none\";" );
            out.println("parent.document.getElementById(\"ok\").style.display = \"block\";" );
            out.print("</script>");*/
            out.println("<input type=\"hidden\" name=\"retMsg\" value=\"" + outPutStr + "\">");
            out.flush();
            out.close();
        }
    }
}
