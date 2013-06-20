package com.sino.ams.newasset.servlet;


import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.constant.AssetsURLList;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.dao.CustomQueryDAO;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.query.WebPageView;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.message.Message;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;


/**
 * <p>Title: CustomQueryServlet</p>
 * <p>Description:程序自动生成服务程序“CustomQuerySetServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */

public class CustomQueryServlet extends BaseServlet {

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
            req2DTO.setDTOClassName(AmsAssetsAddressVDTO.class.getName());
            AmsAssetsAddressVDTO dtoParameter = (AmsAssetsAddressVDTO) req2DTO.
                                                getDTO(req);
            String action = dtoParameter.getAct();
            conn = getDBConnection(req);
            CustomQueryDAO custQueryDAO = new CustomQueryDAO(user, dtoParameter,
                    conn);
            if (!custQueryDAO.hasCustomizedFields()) {
                message = getMessage(AssetsMessageKeys.NOT_DEFINE_FIELDS);
                message.setIsError(true);
                forwardURL = AssetsURLList.CUST_QRY_SET_SERVLET;
            } else {
                String queryParas = custQueryDAO.getQueryParas();
                DTOSet fields = custQueryDAO.getDisplayFields();
                String headerDivTopPx = custQueryDAO.getHeaderDivTopPx();
                String dataDivTopPx = custQueryDAO.getDataDivTopPx();
                String tableWidthPx = custQueryDAO.getTableWidthPx();
                String tdWidthPx = custQueryDAO.getTdWidthPx();
                String dataDivHeightPx = custQueryDAO.getDataDiveightPx();

                req.setAttribute(AssetsWebAttributes.CUST_QUERY_PARA,
                                 queryParas);
                req.setAttribute(AssetsWebAttributes.COMM_QUERY_HEAD, fields);
                req.setAttribute(AssetsWebAttributes.HEADER_DIV_TOP,
                                 headerDivTopPx);
                req.setAttribute(AssetsWebAttributes.DATA_DIV_TOP, dataDivTopPx);
                req.setAttribute(AssetsWebAttributes.TABLE_WIDTH, tableWidthPx);
                req.setAttribute(AssetsWebAttributes.TD_WIDTH, tdWidthPx);
                req.setAttribute(AssetsWebAttributes.DATA_DIV_HEIGHT,
                                 dataDivHeightPx);
                if (action.equals("")) {
                    forwardURL = AssetsURLList.CUST_QRY_PAGE;
                } else if (action.equals(WebActionConstant.QUERY_ACTION)) {
                    WebPageView pageView = new WebPageView(req, conn);
                    SQLModel sqlModel = custQueryDAO.getCommonQueryModel();
                    pageView.setDTOClassName(AmsAssetsAddressVDTO.class.getName());
                    pageView.setCalPattern(LINE_PATTERN);
                    pageView.setCountPages(false);
                    pageView.produceWebData(sqlModel);
                    forwardURL = AssetsURLList.CUST_QRY_PAGE;
                } else if (action.equals(WebActionConstant.EXPORT_ACTION)) {
                    File file = custQueryDAO.getExportFile();
                    WebFileDownload fileDown = new WebFileDownload(req, res);
                    fileDown.setFilePath(file.getAbsolutePath());
                    fileDown.download();
                    file.delete();
                } else {
                    message = getMessage(MsgKeyConstant.INVALID_REQ);
                    message.setIsError(true);
                    forwardURL = MessageConstant.MSG_PRC_SERVLET;
                }
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
        } catch (DataTransException ex) {
            ex.printLog();
            message = getMessage(AssetsMessageKeys.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (WebFileDownException ex) {
            ex.printLog();
            message = getMessage(AssetsMessageKeys.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } finally {
            closeDBConnection(conn);
            setHandleMessage(req, message);
            if (!forwardURL.equals("")) {
                ServletForwarder forwarder = new ServletForwarder(req, res);
                forwarder.forwardView(forwardURL);
            }
        }
    }
}
