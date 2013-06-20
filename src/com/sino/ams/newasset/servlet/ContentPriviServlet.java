package com.sino.ams.newasset.servlet;


import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsURLList;
import com.sino.ams.newasset.dao.ContentPriviDAO;
import com.sino.ams.newasset.dto.AmsAssetsPriviDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.servlet.BaseServlet;

/**
 * <p>Title: AmsFaCategoryVServlet</p>
 * <p>Description:程序自动生成服务程序“AmsFaCategoryVServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class ContentPriviServlet extends BaseServlet {

    /**
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
        try {
            SfUserDTO user = (SfUserDTO) getUserAccount(req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AmsAssetsPriviDTO.class.getName());
            AmsAssetsPriviDTO dtoParameter = (AmsAssetsPriviDTO) req2DTO.getDTO(
                    req);
            String action = dtoParameter.getAct();
            conn = getDBConnection(req);
            dtoParameter.setCompanyCode(user.getCompanyCode());
            if (action.equals("")) {
                forwardURL = AssetsURLList.FA_CONTENT_PAGE;
                req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {
                AssetsOptProducer optProducer = new AssetsOptProducer(user,
                        conn);
                String existPrivi = optProducer.getExistContentPrivi(
                        dtoParameter.getUserId());
                String noPrivi = optProducer.getNoContentPrivi(dtoParameter.
                        getUserId());
                dtoParameter.setExistPriviOption(existPrivi);
                dtoParameter.setNoPriviOption(noPrivi);
                forwardURL = AssetsURLList.FA_CONTENT_PAGE;
                req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
            } else if (action.equals(WebActionConstant.SAVE_ACTION)) {
                DTOSet priviDTOs = getContentPrivis(req, dtoParameter);
                ContentPriviDAO priviDAO = new ContentPriviDAO(user,
                        dtoParameter, conn);
                ServletConfigDTO servletConfig = getServletConfig(req);
                priviDAO.setServletConfig(servletConfig);
                priviDAO.savePrivi(priviDTOs);
                message = priviDAO.getMessage();
                forwardURL = AssetsURLList.FA_CONTENT_SERVLET;
                forwardURL += "?act=" + AssetsActionConstant.QUERY_ACTION;
            } else {
                message = getMessage(MsgKeyConstant.INVALID_REQ);
                message.setIsError(true);
                forwardURL = MessageConstant.MSG_PRC_SERVLET;
            }
        } catch (PoolPassivateException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.POOL_PASSIVATE_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DTOException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.DTO_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (QueryException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.QUERY_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } finally {
            closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
        }
    }

    /**
     * 功能：构造专业资产权限DTO
     * @param req HttpServletRequest
     * @param dtoParameter AmsAssetsPriviDTO
     * @return DTOSet
     * @throws DTOException
     */
    private DTOSet getContentPrivis(HttpServletRequest req,
                                    AmsAssetsPriviDTO dtoParameter) throws
            DTOException {
        DTOSet contentPrivis = new DTOSet();
        String[] faCategoryCodes = req.getParameterValues("faCategoryCode");
        if (faCategoryCodes != null && faCategoryCodes.length > 0) {
            for (int i = 0; i < faCategoryCodes.length; i++) {
                AmsAssetsPriviDTO dto = new AmsAssetsPriviDTO();
                dto.setUserId(dtoParameter.getUserId());
                dto.setCompanyCode(dtoParameter.getCompanyCode());
                dto.setRoleId(dtoParameter.getRoleId());
                dto.setFaCategoryCode(faCategoryCodes[i]);
                contentPrivis.addDTO(dto);
            }
        }
        return contentPrivis;
    }
}
