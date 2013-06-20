package com.sino.pda;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.system.user.dao.SfUserDAO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.system.user.util.UserUtil;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.UploadException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.pda.dao.OrderDownloadService;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 中国移动资产实物管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */
public class GetWorkOrders extends BaseServlet {
    private static final String CONTENT_TYPE = "application/xml; charset=GBK";
    private static final String DOC_TYPE = null;

    /**
     * 所有的Servlet都必须实现的方法。
     *
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        res.setContentType(CONTENT_TYPE);
        PrintWriter out = res.getWriter();
        Connection conn = null;
        SfUserDTO sfUser = new SfUserDTO();
        SfUserDTO userAccount = null;
        RequestParser reqPar = new RequestParser();

        try {
            reqPar.transData(req);
            String userName = StrUtil.nullToString(reqPar.getParameter("username")).toUpperCase();//登录名
            String m_force = StrUtil.nullToString(reqPar.getParameter("force"));
            conn = DBManager.getDBConnection();
            userAccount = (SfUserDTO) SessionUtil.getUserAccount(req);

            sfUser.setLoginName(userName);
            SfUserDAO sfUserDAO = new SfUserDAO(userAccount, sfUser, conn);

            ServletConfigDTO servletConfig = SessionUtil.getServletConfigDTO(req);
            sfUserDAO.setDTOClassName(SfUserDTO.class.getName());
            DTOSet dtoSet = (DTOSet) sfUserDAO.getMuxData();
            if (dtoSet.getSize() > 0) {
                sfUser = (SfUserDTO) dtoSet.getDTO(0);
                UserUtil.enrichUserAccount(sfUser, conn);
            }

            if (DOC_TYPE != null) {
                out.println(DOC_TYPE);
            }

            Logger.logInfo("PDA run GetWorkOrders servlet begin....");

            out.println("<?xml version=\"1.0\" encoding=\"GB2312\"?> ");

            out.println("<workorders>");
            PDAOrderUtil orderUtil = new PDAOrderUtil();
            out.println(orderUtil.getAllWorkorder(conn, sfUser, m_force, servletConfig.getProvinceCode()));

            OrderDownloadService downService = new OrderDownloadService(sfUser, conn);
            out.println(downService.getOrderXml()); 
            
            out.println("</workorders>");

            Logger.logInfo("PDA run GetWorkOrders servlet End....");
            out.close();
        } catch (PoolException e) {
            e.printStackTrace();
        } catch (QueryException e) {
            e.printStackTrace();
        } catch (UploadException e) {
            e.printStackTrace();
        } finally {
            DBManager.closeDBConnection(conn);
        }
    }
}
