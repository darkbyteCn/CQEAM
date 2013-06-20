package com.sino.ams.bean;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.security.dao.PrivilegeAuthorizer;
import com.sino.ams.system.resource.dao.SfResDefineDAO;
import com.sino.ams.system.resource.dto.SfResDefineDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.FilterConfigDTO;
import com.sino.framework.servlet.BaseServlet;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 */
public class URLForwardServlet extends BaseServlet {
    public URLForwardServlet() {
    }

    /**
     * 所有的Servlet都必须实现的方法。
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        String resourceId = req.getParameter("resourceId");
        Connection conn = null;
        SfUserDTO userAccount = (SfUserDTO) getUserAccount(req);
        Message message = SessionUtil.getMessage(req);
        try {
            conn = getDBConnection(req);
            PrivilegeAuthorizer authorizer = new PrivilegeAuthorizer(userAccount, conn);
            SfResDefineDTO resourceDTO = authorizer.getAuthorizedResourceById(resourceId);
            if (resourceDTO != null) {
                forwardURL = resourceDTO.getResUrl();
                SessionUtil.setPageTitle(req,resourceDTO.getResName());
                if (StrUtil.isEmpty(forwardURL) && resourceDTO.getHasChild().equals("0")) {
                    forwardURL = MessageConstant.MSG_PRC_SERVLET;
                    message = getMessage(MsgKeyConstant.RES_NOT_FINISHED);
                    message.addParameterValue(resourceDTO.getResName());
                    message.addParameterValue(resourceDTO.getPrincipal());
                }
            } else {
                resourceDTO = new SfResDefineDTO();
                resourceDTO.setResId(resourceId);
                SfResDefineDAO resourceDAO = new SfResDefineDAO(userAccount, resourceDTO, conn);
                resourceDTO = resourceDAO.getResourceById();
                FilterConfigDTO filterDTO = getFilterConfig(req);
                forwardURL = filterDTO.getNoPriviledgeURL();
                message = getMessage(MsgKeyConstant.NO_RES_PRIVI);
                message.setIsError(true);
                message.addParameterValue(resourceDTO.getResName());
            }
        } catch (QueryException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.QUERY_ERROR);
            message.setIsError(true);
        } catch (PoolPassivateException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.CONN_ERROR);
            message.setIsError(true);
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            RequestDispatcher rdis = req.getRequestDispatcher(forwardURL);
            rdis.forward(req, res);
        }
    }
}
