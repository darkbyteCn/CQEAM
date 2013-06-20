package com.sino.ams.system.user.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.system.user.dao.AmsLoginDAO;
import com.sino.base.config.PoolConfig;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.ReflectException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.ReflectionUtil;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dao.BaseLoginDAO;
import com.sino.framework.security.dto.FilterConfigDTO;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.servlet.BaseServlet;

/**
 * <p>Title: SinoEAMS</p>
 * <p>Description: 区别于UserLoginServlet主要是增加了密码的校验,包括是否为初始密码,并检查密码是否已过有效期</p>
 * <p>Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2007 - 2008</p>
 * <p>Company: 北京思诺搏信息技术有限公司</p>
 * @author 何睿
 * @version 0.1
 *          Date: 2008-7-23
 */
public class AmsLoginServlet extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String act = StrUtil.nullToString(req.getParameter("act"));
        String password = StrUtil.nullToString(req.getParameter("password"));
        Connection conn = null;
        Request2DTO req2DTO = new Request2DTO();
        String forwardURL = "";
        Message message = null;
        FilterConfigDTO filterDTO = null;
        try {
            filterDTO = SessionUtil.getFilterConfigDTO(req);
            req2DTO.setDTOClassName(filterDTO.getUserDTO());
            BaseUserDTO loginUser = (BaseUserDTO) req2DTO.getDTO(req);
            conn = getDBConnection(req);
            forwardURL = filterDTO.getLoginUrl();
            AmsLoginDAO ald = new AmsLoginDAO(loginUser, loginUser, conn);
            if (act.equals("GET_PASSWORD")) {
                //
                boolean success = ald.getPassword();
                message = ald.getMessage();
                if (success) {
                    message.setIsError(true);
                }
            } else {
                String loginDAO = filterDTO.getLoginDAO();
                if (conn == null) {
                    message = getMessage(MsgKeyConstant.INVALID_CONN_POOL);
                    PoolConfig poolConfig = getPoolConfig();
                    if (poolConfig != null) {
                        message.addParameterValue(poolConfig.toString());
                    }
                    message.setIsError(true);
                    message.setNeedBack(true);
                    forwardURL = MessageConstant.MSG_PRC_SERVLET;
                } else {
                    Object[] consParas = new Object[2];
                    consParas[0] = loginUser;
                    consParas[1] = conn;
                    ServletConfigDTO servletConfig = SessionUtil.getServletConfigDTO(req);
                    BaseLoginDAO userLoginDAO = (BaseLoginDAO) ReflectionUtil.getInstance(loginDAO, consParas);
                    userLoginDAO.setServletConfig(servletConfig);
                    //首先检查登录错误次数,如果为5次,则直接提示该账户已锁定
                    int count = ald.getLoginErrCount();
                  if(ald.getLoginDisable()){


                    if (count < 5) {
                        if (userLoginDAO.isValidUser()) {
                            //将用户登录错误次数清零
                            ald.clearLoginErrCount();
                            if (password.equals(WebAttrConstant.DEFAULT_PASSWORD) || ald.isPasswordExpired()) {  //密码是初始密码或者密码已过期
                                forwardURL = "/firstLogin.jsp";
                            } else {
                                BaseUserDTO userAccount = userLoginDAO.getUserAccount();
                                SessionUtil.saveUserSession(req, userAccount);
                                forwardURL = filterDTO.getLoginSuccessURL();
                            }
                        } else {
                            //将用户登录错误次数加1
                            ald.addLoginErrCount();
                            message = userLoginDAO.getMessage();
                            message.setMessageValue(message.getMessageValue() + "<br>您已经尝试" + (count + 1) + "次，最多有5次机会．");
                            message.setIsError(true);
                            forwardURL = filterDTO.getLoginUrl();
                        }
                    } else {
                        message = ald.getMessage();
                        message.setMessageValue("该账号连续登录错误次数达5次，已被系统锁定，请使用取回密码功能或联系系统管理员！");
                        message.setIsError(true);
                        forwardURL = filterDTO.getLoginUrl();
                    }
                  }else{
                          message = ald.getMessage();
                        message.setMessageValue("该账号已经被失效，如需重新启用,请联系系统管理员！");
                        message.setIsError(true);
                        forwardURL = filterDTO.getLoginUrl();
                  }
                }
            }
        } catch (PoolPassivateException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.POOL_PASSIVATE_ERROR);
            message.setIsError(true);
        } catch (DTOException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.DTO_ERROR);
            message.setIsError(true);
        } catch (QueryException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.QUERY_ERROR);
            message.setIsError(true);
        } catch (ReflectException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
        } catch (ContainerException e) {     //只有找不到该用户数据时抛此错误
            e.printLog();
            message = getMessage(MsgKeyConstant.USER_NOT_EXIST);
            message.setIsError(true);
        } catch (SQLException e) {
            Logger.logError(e);
            message = getMessage(MsgKeyConstant.SQL_ERROR);
            message.setIsError(true);
        } catch (DataHandleException e) {
            Logger.logError(e);
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
        }
    }
}
