package com.sino.ams.newasset.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.constant.AssetsURLList;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.newasset.model.AssetsAssignModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.StrException;
import com.sino.base.message.Message;
import com.sino.base.web.CheckBoxProp;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class AssignRightServlet extends BaseServlet {

    /**
     * 所有的Servlet都必须实现的方法。
     *
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws
            ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        boolean hasError = false;
        try {
            SfUserDTO user = (SfUserDTO) getUserAccount(req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AmsAssetsAddressVDTO.class.getName());
            AmsAssetsAddressVDTO dtoParameter = (AmsAssetsAddressVDTO) req2DTO.
                                                getDTO(req);
            String action = dtoParameter.getAct();
            conn = getDBConnection(req);
            ServletConfigDTO servletConfig = getServletConfig(req);
            String assProp = dtoParameter.getAssProp();
            boolean isDeprMgr = user.isDptAssetsManager();
            boolean isCompMgr = user.isComAssetsManager();
            if (assProp.equals(AssetsWebAttributes.ASSIGN_RESPONSIBLE_USER)) {
                if (!isDeprMgr && !isCompMgr) {
                    message = getMessage(AssetsMessageKeys.ONLY_DEPTMGR_PRIVI);
                    message.setIsError(true);
                    forwardURL = MessageConstant.MSG_PRC_SERVLET;
                    hasError = true;
                }
            } else if (assProp.equals(AssetsWebAttributes.ASSIGN_COST_CENTER)) {
                if (!isCompMgr) {
                    message = getMessage(AssetsMessageKeys.ONLY_COMPMGR_PRIVI);
                    message.setIsError(true);
                    forwardURL = MessageConstant.MSG_PRC_SERVLET;
                    hasError = true;
                }
            }
            if (!hasError) {
                if (action.equals("")) {
                    req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
                    forwardURL = AssetsURLList.ASSETS_ASSIGN_RIGHT;
                } else if (action.equals(AssetsActionConstant.QUERY_ACTION)) {
                    BaseSQLProducer sqlProducer = new AssetsAssignModel(user,
                            dtoParameter);
                    PageQueryDAO pageDAO = new PageQueryDAO(req, conn,
                            sqlProducer);
                    pageDAO.setServletConfig(servletConfig);
                    CheckBoxProp checkProp = new CheckBoxProp("subCheck");
                    checkProp.addDbField("BARCODE");
                    pageDAO.setWebCheckProp(checkProp);
                    pageDAO.setPageSize(22);
                    pageDAO.setCountPages(false);
                    pageDAO.setCalPattern(LINE_PATTERN);
                    pageDAO.produceWebData();
                    req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
                    forwardURL = AssetsURLList.ASSETS_ASSIGN_RIGHT;
                } else {
                    message = getMessage(AssetsMessageKeys.INVALID_REQ);
                    message.setIsError(true);
                    forwardURL = MessageConstant.MSG_PRC_SERVLET;
                }
            }
        } catch (PoolPassivateException ex) {
            ex.printLog();
            message = getMessage(AssetsMessageKeys.POOL_PASSIVATE_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DTOException ex) {
            ex.printLog();
            message = getMessage(AssetsMessageKeys.DTO_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (QueryException ex) {
            ex.printLog();
            message = getMessage(AssetsMessageKeys.QUERY_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (StrException ex) {
            ex.printLog();
            message = getMessage(AssetsMessageKeys.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } finally {
            closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
        }
    }
}
