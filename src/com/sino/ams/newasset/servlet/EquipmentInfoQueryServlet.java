package com.sino.ams.newasset.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;

import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.ams.bean.OptionProducer;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.dao.EquipmentInfoQueryDAO;
import com.sino.ams.newasset.dto.AssetsTagNumberQueryDTO;
import com.sino.ams.newasset.model.EquipmentInfoQueryModel;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: srf
 * Date: 2008-4-1
 * Time: 14:57:09
 * To change this template use File | Settings | File Templates.
 */
public class EquipmentInfoQueryServlet extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws
            ServletException, IOException {
        String forwardURL = "";
        String type = StrUtil.nullToString(req.getParameter("type"));
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AssetsTagNumberQueryDTO.class.getName());
            AssetsTagNumberQueryDTO dtoParameter = (AssetsTagNumberQueryDTO)
                    req2DTO.getDTO(req);
            conn = getDBConnection(req);
            EquipmentInfoQueryDAO dao = new EquipmentInfoQueryDAO(user,
                    dtoParameter, conn);
            String action = req.getParameter("act");
            action = StrUtil.nullToString(action);
            OptionProducer op = new OptionProducer(user, conn);
            int organizationId = dtoParameter.getOrganizationId();
            String companySelect = op.getAllOrganization(organizationId, true);
            String dix=dtoParameter.getFinanceProp();
            req.setAttribute("PROP",op.getDictOption("FINANCE_PROP",dix));
            req.setAttribute("OU", companySelect);
            req.setAttribute("TAG_NUMBER", dtoParameter);
            if (action.equals("")) {
                if (type.equals("dept")) {
                    forwardURL = "/newasset/equipmentInfoDeptQuery.jsp";
                } else if (type.equals("task")) {
                    forwardURL = "/newasset/equipmentInfoTaskQuery.jsp";
                } else {
                    forwardURL = "/newasset/equipmentInfoQuery.jsp";
                }
            } else if (action.equals(AssetsActionConstant.QUERY_ACTION)) {
                BaseSQLProducer sqlProducer = new EquipmentInfoQueryModel(user,
                        dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setCalPattern(LINE_PATTERN);
                pageDAO.produceWebData();
                req.setAttribute("TAG_NUMBER", dtoParameter);
                if (type.equals("dept")) {
                    forwardURL = "/newasset/equipmentInfoDeptQuery.jsp";
                } else if (type.equals("task")) {
                    forwardURL = "/newasset/equipmentInfoTaskQuery.jsp";
                } else {
                    forwardURL = "/newasset/equipmentInfoQuery.jsp";
                }
            } else if (action.equals(AssetsActionConstant.EXPORT_ACTION)) {
                File file = dao.exportFile();
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
            }
        } catch (DTOException ex) {
            ex.printLog();
            message = getMessage(AssetsMessageKeys.DTO_ERROR);
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
        } catch (QueryException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.QUERY_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (PoolPassivateException ex) {
            ex.printLog();
            message = getMessage(AssetsMessageKeys.POOL_PASSIVATE_ERROR);
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