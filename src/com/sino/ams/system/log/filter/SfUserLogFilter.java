package com.sino.ams.system.log.filter;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

import com.sino.base.constant.WorldConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolException;
import com.sino.base.log.Logger;
import com.sino.base.util.CalendarUtil;
import com.sino.base.util.StrUtil;
import com.sino.ams.system.log.dao.SfUserLogDAO;
import com.sino.ams.system.log.dto.SfUserLogDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.FilterConfigDTO;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class SfUserLogFilter implements Filter {
    private boolean isStartLog = false;

    /**
     * init
     * @param config FilterConfig
     * @throws ServletException
     */
    public void init(FilterConfig config) throws ServletException {
        String startLog = config.getInitParameter("startLog");
        if (!StrUtil.isEmpty(startLog)) {
            if (startLog.equalsIgnoreCase("TRUE")) {
                isStartLog = true;
            }
        }
    }

    /**
     * @param request ServletRequest
     * @param res     ServletResponse
     * @param chain   FilterChain
     * @throws IOException
     * @throws ServletException
     */
    public void doFilter(ServletRequest request, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            if (isStartLog&&!req.getRemoteAddr().equals("127.0.0.1")) {
                SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(req);
                String reqUrl = req.getRequestURI();
                if (userAccount != null) {
                    if (reqUrl.indexOf(".jsp") > -1 || reqUrl.indexOf("/servlet") > -1) {
                        String action = "查询";
                        String queryStr = req.getQueryString();
                        String[] paras = StrUtil.splitStr(queryStr, "&");
                        if (paras != null && paras.length > 0) {
                            int index = -1;
                            String singlePara = "";
                            String paraName = "";
                            for (int i = 0; i < paras.length; i++) {
                                singlePara = paras[i];
                                if (singlePara == null) {
                                    continue;
                                }
                                index = singlePara.indexOf("=");
                                if (index != -1) {
                                    paraName = singlePara.substring(0, index);
                                    if (paraName.equals("act")) {
                                        action = singlePara.substring(index + 1);
                                        break;
                                    }
                                }
                            }
                        }
                        if (!StrUtil.isEmpty(queryStr)) {
                            reqUrl = reqUrl + "?" + queryStr;
                        }
                        FilterConfigDTO filterConfigDTO = SessionUtil.getFilterConfigDTO(req);
                        if (reqUrl.equals(filterConfigDTO.getLoginSuccessURL())) {
                            action = "登录";
                        }
                        if (reqUrl.equals(filterConfigDTO.getLogOutServlet())) {
                            action = "注销";
                        }
                        SfUserLogDTO logData = new SfUserLogDTO();
                        logData.setUserId(userAccount.getUserId());
                        logData.setUserAccount(userAccount.getLoginName());
                        logData.setActionType(action);
                        logData.setClientIp(req.getRemoteAddr());
                        logData.setReqUrl(reqUrl);
                        logData.setServer(req.getServerName());
                        logData.setLogTime(CalendarUtil.getCurrCalendar());
                        logData(userAccount, logData);
                    }
                }
            }
            if (chain != null) {
                chain.doFilter(req, res);
            }
        } catch (DataHandleException ex) {
            ex.printLog();
            throw new ServletException(ex);
        } catch (CalendarException ex) {
            ex.printLog();
            throw new ServletException(ex);
        }
    }

    /**
     * 记录日志数据
     * @param userAccount SfUserDTO
     * @param logData     SfUserLogDTO
     * @throws DataHandleException
     */
    private void logData(SfUserDTO userAccount, SfUserLogDTO logData) throws
            DataHandleException {
        Connection conn = null;
        try {
            conn = DBManager.getDBConnection();
            SfUserLogDAO logDAO = new SfUserLogDAO(userAccount, logData, conn);
            logDAO.createData();
        } catch (PoolException ex) {
            ex.printLog();
            throw new DataHandleException(ex);
        } finally {
            DBManager.closeDBConnection(conn);
        }
    }

    /**
     * destroy
     */
    public void destroy() {
        logStopData();
        this.isStartLog = false;
    }

    private void logStopData() {
        try {
            SfUserLogDTO logData = new SfUserLogDTO();
            logData.setUserId(-1);
            logData.setUserAccount(WorldConstant.USER_ACCOUNT);
            logData.setActionType("应用系统停止");
            logData.setClientIp("NOT_BY_CLIENT");
            logData.setReqUrl("there's no url requested");
            InetAddress localHost = InetAddress.getLocalHost();
            logData.setServer(localHost.getHostName());
            logData.setLogTime(CalendarUtil.getCurrCalendar());
            logData(null, logData);
        } catch (CalendarException ex) {
            ex.printLog();
        } catch (UnknownHostException ex) {
            Logger.logError(ex);
        } catch (DataHandleException ex) {
            ex.printLog();
        }
    }
}
