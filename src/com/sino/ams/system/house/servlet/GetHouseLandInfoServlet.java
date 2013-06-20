package com.sino.ams.system.house.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.WebPageView;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;

import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.system.house.dao.GetMisHousInfoDAO;
import com.sino.ams.system.house.dto.AmsHouseInfoDTO;
import com.sino.ams.system.house.model.GetMisHousInfoModel;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: Zyun
 * Date: 2008-5-31
 * Time: 15:41:23
 * To change this template use File | Settings | File Templates.
 */
public class GetHouseLandInfoServlet extends BaseServlet {

    /**
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        String showMsg = "";
        Message message = SessionUtil.getMessage(req);
        String action = req.getParameter("act");
        action = StrUtil.nullToString(action);
        Connection conn = null;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            AmsHouseInfoDTO dtoParameter = null;
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AmsHouseInfoDTO.class.getName());
            dtoParameter = (AmsHouseInfoDTO) req2DTO.getDTO(req);
            conn = getDBConnection(req);
            GetMisHousInfoDAO getMisHousInfoDAO = new GetMisHousInfoDAO(user, dtoParameter, conn);
            OptionProducer prd = new OptionProducer(user, conn);
            String hasCertificate = prd.getBooleanOption(dtoParameter.getCertificate());
            req.setAttribute(WebAttrConstant.ISLAND_CERTIFICATE_OPTION, hasCertificate);
            String hasLandNo = prd.getBooleanOption(dtoParameter.getHasLandNo());
            req.setAttribute(WebAttrConstant.IS_CERTIFICATE_OPTION, hasLandNo);
            if (action.equals("")) {
                String isRent = prd.getBooleanOption(dtoParameter.getIsRent());
                req.setAttribute(WebAttrConstant.IS_RENT_OPTION, isRent);
                 req.setAttribute(WebAttrConstant.AMS_HOUSE_INFO_DTO,dtoParameter);
                forwardURL = "/system/house/houseLandSearch.jsp";
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {
                GetMisHousInfoModel sqlProducer = new GetMisHousInfoModel(user, dtoParameter);
                String isProvince = getMisHousInfoDAO.isProvince();
                dtoParameter.setProvince(isProvince);
                SQLModel sqlModel = sqlProducer.getHouseLandModel();
                WebPageView wpv = new WebPageView(req, conn);
                wpv.produceWebData(sqlModel);
//                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
//                pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
//                pageDAO.produceWebData();
                String isRent = prd.getBooleanOption(dtoParameter.getIsRent());
                req.setAttribute(WebAttrConstant.IS_RENT_OPTION, isRent);
                req.setAttribute(WebAttrConstant.AMS_HOUSE_INFO_DTO,dtoParameter);
                forwardURL = "/system/house/houseLandSearch.jsp";
             } else if (action.equals(WebActionConstant.EXPORT_ACTION)) {      //µ¼³ö
                String isProvince = getMisHousInfoDAO.isProvince();
                dtoParameter.setProvince(isProvince);
                File file = getMisHousInfoDAO.exportFile();
                getMisHousInfoDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
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
        } catch (DataTransException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
//        } catch (DataHandleException ex) {
//            ex.printLog();
//            message = getMessage(MsgKeyConstant.COMMON_ERROR);
//            message.setIsError(true);
//            forwardURL = MessageConstant.MSG_PRC_SERVLET;
           } catch (WebFileDownException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (SQLModelException e) {
            e.printLog();
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            if (!forwardURL.equals("")) {
                if (showMsg.equals("")) {
                    forwarder.forwardView(forwardURL);
                } else {
                    forwarder.forwardOpenerView(forwardURL, showMsg);
                }
            }
        }
    }
}
