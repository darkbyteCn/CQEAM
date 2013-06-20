package com.sino.ams.newasset.servlet;

import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.base.message.Message;
import com.sino.base.dto.Request2DTO;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.base.web.ServletForwarder;
import com.sino.base.exception.*;
import com.sino.base.db.conn.DBManager;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.newasset.dao.CheckDataQueryDAO;
import com.sino.ams.newasset.model.CheckDataQueryModel;
import com.sino.ams.newasset.constant.AssetsURLList;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.newasset.dto.AmsAssetsCheckHeaderDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.File;
import java.sql.Connection;

public class CheckDataQueryServlet extends BaseServlet {
    /**
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AmsAssetsCheckHeaderDTO.class.getName());
            AmsAssetsCheckHeaderDTO dtoParameter = (AmsAssetsCheckHeaderDTO) req2DTO.getDTO(req);
            String action = dtoParameter.getAct();
            conn = getDBConnection(req);
            CheckDataQueryDAO objectMaxDAO = new CheckDataQueryDAO(user, dtoParameter, conn);
            AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
            String cityOption = optProducer.getAllOrganization(dtoParameter.getOrganizationId(), true);
            req.setAttribute(AssetsWebAttributes.CITY_OPTION, cityOption);
            String orderStatus = optProducer.getDictOption(AssetsDictConstant.CHKORDER_STATUS, dtoParameter.getOrderStatus());
			req.setAttribute(AssetsWebAttributes.ORDER_STATUS_OPT, orderStatus);
            String groupOPtion = optProducer.getAllGroup(dtoParameter.getGroupId() + "");
			dtoParameter.setGroupOpt(groupOPtion);
            if (action.equals("")) {
                req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
                forwardURL = AssetsURLList.CHECK_DATA_PAGE;
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {
                BaseSQLProducer sqlProducer = new CheckDataQueryModel(user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.produceWebData();
                req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
                forwardURL = AssetsURLList.CHECK_DATA_PAGE;
            } else if (action.equals(WebActionConstant.EXPORT_ACTION)) {
                File file = objectMaxDAO.exportFile();
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
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
        } catch (WebFileDownException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DataTransException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
        }
    }
}
