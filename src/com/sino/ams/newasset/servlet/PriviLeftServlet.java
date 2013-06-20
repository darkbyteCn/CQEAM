package com.sino.ams.newasset.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.constant.AssetsURLList;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.dao.AmsAssetsPriviDAO;
import com.sino.ams.newasset.dto.AmsAssetsPriviDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MessageConstant;
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
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class PriviLeftServlet extends BaseServlet {

    /**
     * 所有的Servlet都必须实现的方法。
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
            AmsAssetsPriviDTO dto = (AmsAssetsPriviDTO) req2DTO.getDTO(req);
            conn = getDBConnection(req);
            String roleName = dto.getRoleName();
            ServletConfigDTO servletConfig = getServletConfig(req);
            AmsAssetsPriviDAO priviDAO = new AmsAssetsPriviDAO(user, dto, conn);
            priviDAO.setServletConfig(servletConfig);
            if (!roleName.equals(servletConfig.getMtlAssetsMgr())) {
                String treeHtml = "";
                if (roleName.indexOf(servletConfig.getDeptAssetsMgr()) > -1) {
                    treeHtml = priviDAO.getDeptTree();
                } else if (roleName.indexOf(servletConfig.getCompAssetsMgr()) >
                           -1) {
                    treeHtml = priviDAO.getCompanyTree();
                } else if (roleName.indexOf(servletConfig.getProvAssetsMgr()) >
                           -1) {
                    treeHtml = priviDAO.getProvinceTree();
                }
                req.setAttribute(AssetsWebAttributes.PRIVI_DEPT_TREE, treeHtml);
                forwardURL = AssetsURLList.ASSETS_PRIVI_LEFT;
            } else {
                forwardURL = AssetsURLList.ASSIGN_USER_SERVLET;
                forwardURL += "?srcPage=" + req.getRequestURI();
            }
        } catch (PoolPassivateException ex) {
            ex.printLog();
            message = getMessage(AssetsMessageKeys.POOL_PASSIVATE_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (QueryException ex) {
            ex.printLog();
            message = getMessage(AssetsMessageKeys.QUERY_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DTOException ex) {
            ex.printLog();
            message = getMessage(AssetsMessageKeys.DTO_ERROR);
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
