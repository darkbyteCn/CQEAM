package com.sino.ams.newasset.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.message.MessageConstant;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.StrException;
import com.sino.base.exception.UploadException;
import com.sino.base.message.Message;
import com.sino.base.web.CheckBoxProp;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.constant.AssetsURLList;
import com.sino.ams.newasset.dao.AssetsAssignDAO;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class AssetsAssignServlet extends BaseServlet {
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
        try {
            SfUserDTO user = (SfUserDTO) getUserAccount(req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AmsAssetsAddressVDTO.class.getName());
            AmsAssetsAddressVDTO dtoParameter = (AmsAssetsAddressVDTO) req2DTO.
                                                getDTO(req);
            String action = dtoParameter.getAct();
            dtoParameter.setCalPattern(LINE_PATTERN);
            conn = getDBConnection(req);
            AssetsAssignDAO assignDAO = new AssetsAssignDAO(user, dtoParameter,
                    conn);
            String assProp = dtoParameter.getAssProp();
            if (action.equals(AssetsActionConstant.ASSIGN_ACTION)) {
                assignDAO.assignAssets(getAssignData(req, assProp));
                message = assignDAO.getMessage();
                forwardURL = AssetsURLList.ASSIGN_RIGHT_SERVLET;
                forwardURL += "?act=" + AssetsActionConstant.QUERY_ACTION;
            } else {
                message = getMessage(AssetsMessageKeys.INVALID_REQ);
                message.setIsError(true);
                forwardURL = MessageConstant.MSG_PRC_SERVLET;
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
        } finally {
            closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
        }
    }

    private DTOSet getAssignData(HttpServletRequest req, String assProp) throws
            ServletException {
        DTOSet assignDatas = new DTOSet();
        try {
            RequestParser parser = new RequestParser();
            CheckBoxProp checkProp = new CheckBoxProp("subCheck");
            parser.setCheckBoxProp(checkProp);
            parser.transData(req);
            String[] barcodes = parser.getParameterValues("barcode");
       //    barcodes = req.getParameterValues("subCheck");
            String responsibilityUser = parser.getParameter(
                    "responsibilityUser");
            String maintainUser = parser.getParameter("maintainUser");
            String responsibilityDept = parser.getParameter(
                    "responsibilityDept");
            AmsAssetsAddressVDTO dto = null;
            for (int i = 0; i < barcodes.length; i++) {
                dto = new AmsAssetsAddressVDTO();
                dto.setBarcode(barcodes[i]);
                dto.setResponsibilityUser(responsibilityUser);
                dto.setMaintainUser(maintainUser);
                dto.setResponsibilityDept(responsibilityDept);
                dto.setAssProp(assProp);
                assignDatas.addDTO(dto);
            }
        } catch (UploadException ex) {
            ex.printLog();
            throw new ServletException(ex);
        } catch (StrException ex) {
            ex.printLog();
            throw new ServletException(ex);
        } catch (DTOException ex) {
            ex.printLog();
            throw new ServletException(ex);
        }
        return assignDatas;
    }
}
