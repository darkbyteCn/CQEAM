package com.sino.ams.system.user.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.constant.ResNameConstant;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.system.user.dao.ChangeUserPasswordDAO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.util.ResUtil;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.sinoflow.user.dao.SfUserChgLogDAO;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;
import com.sino.sinoflow.user.dto.SfUserChgLogDTO;

/**
 * User: Zyun
 * Date: 2008-1-11
 * Time: 14:49:02
 */
public class ChangeUserPasswordServlet extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        String msg = "";
        String act = req.getParameter("act");
        act = StrUtil.nullToString(act);
        String oldPswd = StrUtil.nullToString(req.getParameter("oldPswd"));
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            SfUserDTO dtoParameter = null;
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(SfUserDTO.class.getName());
            dtoParameter = (SfUserDTO) req2DTO.getDTO(req);
            conn = getDBConnection(req);
            ChangeUserPasswordDAO changeUserPasswordDAO = new ChangeUserPasswordDAO(user, dtoParameter, conn);
            if (act.equals("")) {
            	ResUtil.setAllResName(conn, req, ResNameConstant.CHANGE_USER );
            	
                SfUserDTO dto = changeUserPasswordDAO.queryUserInfo();
                req.setAttribute(WebAttrConstant.USER_ATTR, dto);
                forwardURL = URLDefineList.CHANGE_USER_PAGE;
            } else if (act.equals(WebActionConstant.UPDATE_ACTION)) {
                if (changeUserPasswordDAO.submit(oldPswd)) {
                    SfUserChgLogDTO chgLogDTO = fillChgLog(user, dtoParameter, "修改", "修改用户" + dtoParameter.getUsername() + "个人信息(密码、电话、E-Mail)");
                    SfUserChgLogDAO chgLogDAO = new SfUserChgLogDAO(user, chgLogDTO, conn);
                    chgLogDAO.createData();
                    msg = "个人信息修改成功！";
                    req.setAttribute("sfuser", user);
                } else {
                    message = changeUserPasswordDAO.getMessage();
                    message.setIsError(true);
                    forwardURL = "/servlet/com.sino.ams.system.user.servlet.ChangeUserPasswordServlet?act=";
                }
//                forwardURL = URLDefineList.CHANGE_USER_PAGE;
            }
        } catch (PoolException e) {
            message.setIsError(true);
            Logger.logError(e);
        } catch (DTOException e) {
            message.setIsError(true);
            Logger.logError(e);
        } catch (DataTransException e) {
            message.setIsError(true);
            Logger.logError(e);
        } catch (DataHandleException e) {
            message.setIsError(true);
            Logger.logError(e);
        } catch (QueryException e) {
            message.setIsError(true);
            Logger.logError(e);
        } catch (ContainerException e) {
        	message.setIsError(true);
            Logger.logError(e);
		} finally {
            DBManager.closeDBConnection(conn);
            if (!msg.equals("")) {
                res.setContentType("text/html;charset=GBK");
                PrintWriter out = res.getWriter();
                out.print("<script language=\"javascript\">\n");
                out.println("alert(\"" + msg + "\");");
                out.println("window.close();\n");
                out.print("</script>");
            } else {
                ServletForwarder sforwarder = new ServletForwarder(req, res);
                setHandleMessage(req, message);
                sforwarder.forwardView(forwardURL);
            }
        }
    }

    private SfUserChgLogDTO fillChgLog(SfUserBaseDTO userAccount, SfUserBaseDTO user, String chgType, String remark) {
        SfUserChgLogDTO chgLogDTO = new SfUserChgLogDTO();
        chgLogDTO.setUserId(user.getUserId());
        chgLogDTO.setChgType(chgType);
        chgLogDTO.setOperator(userAccount.getUserId());
        chgLogDTO.setRemark(remark);
        return chgLogDTO;
    }
}
