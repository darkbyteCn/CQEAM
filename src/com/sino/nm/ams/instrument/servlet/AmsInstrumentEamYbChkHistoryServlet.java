package com.sino.nm.ams.instrument.servlet;

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
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;

import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.nm.ams.instrument.dao.AmsInstrumentEamYbChkHistoryDAO;
import com.sino.nm.ams.instrument.dto.AmsInstrumentEamYbChkMaintainDTO;
import com.sino.nm.ams.instrument.model.AmsInstrumentEamYbChkHistoryModel;
import com.sino.ams.system.user.dto.SfUserDTO;

public class AmsInstrumentEamYbChkHistoryServlet extends BaseServlet {

	public void performTask(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        String action = req.getParameter("act");
        action = StrUtil.nullToString(action);
        Connection conn = null;
        String showMsg = "";
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            AmsInstrumentEamYbChkMaintainDTO dtoParameter = null;
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AmsInstrumentEamYbChkMaintainDTO.class.getName());
            dtoParameter = (AmsInstrumentEamYbChkMaintainDTO) req2DTO.getDTO(req);
            conn = getDBConnection(req);
            AmsInstrumentEamYbChkHistoryDAO amsInstrumentInfoDAO = new AmsInstrumentEamYbChkHistoryDAO(user, dtoParameter, conn);
            OptionProducer op= new OptionProducer(user, conn);
//            String cityOption1 = op.getAllOrganization("", true);
            String cityOption1 = op.getAllOrganization(dtoParameter.getOrganizationId(), true);
            req.setAttribute(WebAttrConstant.CITY_OPTION, cityOption1);

            if (action.equals("")) {
                req.setAttribute(WebAttrConstant.AMS_INSTRUMENT_CHK_HISTORY, dtoParameter);
                forwardURL = "/nm/instrument/instrumentYbChkHistory.jsp";
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {
                BaseSQLProducer sqlProducer = new AmsInstrumentEamYbChkHistoryModel(user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
                pageDAO.produceWebData();
                req.setAttribute(WebAttrConstant.AMS_INSTRUMENT_CHK_HISTORY, dtoParameter);
                forwardURL = "/nm/instrument/instrumentYbChkHistory.jsp";
            } else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
                amsInstrumentInfoDAO.setDTOClassName(AmsInstrumentEamYbChkMaintainDTO.class.getName());
                amsInstrumentInfoDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
                AmsInstrumentEamYbChkMaintainDTO amsInstrumentInfo = (AmsInstrumentEamYbChkMaintainDTO) amsInstrumentInfoDAO.getDataByPrimaryKey();    
                if (amsInstrumentInfo == null) {
                    amsInstrumentInfo = new AmsInstrumentEamYbChkMaintainDTO();
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                }
                req.setAttribute("ifpage", "detail");
                req.setAttribute(WebAttrConstant.AMS_INSTRUMENT_CHK_HISTORY, amsInstrumentInfo);
                forwardURL = URLDefineList.INSTRUMENT_REGISTRATION_DETAIL;
            } else if (action.equals(WebActionConstant.EXPORT_ACTION)) {      //导出Excel
            	
                File file = amsInstrumentInfoDAO.exportFile();
                amsInstrumentInfoDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();

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
            message = getMessage(MsgKeyConstant.QUERY_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (WebFileDownException ex) {
			ex.printLog();
            message = getMessage(MsgKeyConstant.QUERY_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
		}
        finally {
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
            //根据实际情况修改页面跳转代码。
        }
	}

}
