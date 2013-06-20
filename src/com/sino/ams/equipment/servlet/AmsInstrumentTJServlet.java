package com.sino.ams.equipment.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.equipment.dto.AmsInstrumentTJDTO;
import com.sino.ams.equipment.model.AmsInstrumentTJModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * <p>Title: AmsInstrumentTJServlet</p>
 * <p>Description:程序自动生成服务程序“AmsInstrumentTJServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 张星
 * @version 1.0
 */
public class AmsInstrumentTJServlet extends BaseServlet {

	/**
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
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
            AmsInstrumentTJDTO dtoParameter = null;
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AmsInstrumentTJDTO.class.getName());
            dtoParameter = (AmsInstrumentTJDTO) req2DTO.getDTO(req);
            conn = getDBConnection(req);
//            AmsInstrumentTJDAO amsInstrumentInfoDAO = new AmsInstrumentTJDAO(user, dtoParameter, conn);
            OptionProducer op= new OptionProducer(user, conn);
            String cityOption1 = op.getAllOrganization(  SyBaseSQLUtil.NULL_INT_VALUE , true);
            req.setAttribute(WebAttrConstant.CITY_OPTION, cityOption1);

            if (action.equals("")) {
                req.setAttribute(WebAttrConstant.AMS_INSTRUMENT_REGISTRATION, dtoParameter);
                forwardURL = URLDefineList.INSTRUMENT_REGISTRATION_PAGE;
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {
                BaseSQLProducer sqlProducer = new AmsInstrumentTJModel(user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
//                CheckBoxProp checkProp = new CheckBoxProp("subCheck");
//                checkProp.addDbField("BARCODE");
//                pageDAO.setWebCheckProp(checkProp);
                pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
                pageDAO.produceWebData();
                req.setAttribute(WebAttrConstant.AMS_INSTRUMENT_REGISTRATION, dtoParameter);
                forwardURL = URLDefineList.INSTRUMENT_REGISTRATION_PAGE;
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
