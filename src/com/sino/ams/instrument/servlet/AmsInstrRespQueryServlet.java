package com.sino.ams.instrument.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.instrument.dao.AmsInstrumentInfoDAO;
import com.sino.ams.instrument.dto.AmsInstrumentInfoDTO;
import com.sino.ams.instrument.model.AmsInstrumentInfoModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.WebPageView;
import com.sino.base.db.sql.model.SQLModel;
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
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;

/**
 * Created by IntelliJ IDEA.
 * User: Zyun
 * Date: 2008-5-19
 * Time: 17:39:10
 * To change this template use File | Settings | File Templates.
 */
public class AmsInstrRespQueryServlet extends BaseServlet {

    /**
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        String action = req.getParameter("act");
        String itemCategory = StrUtil.nullToString(req.getParameter("type"));
        action = StrUtil.nullToString(action);
        Connection conn = null;
        String showMsg = "";
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            AmsInstrumentInfoDTO dtoParameter = null;
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AmsInstrumentInfoDTO.class.getName());
            dtoParameter = (AmsInstrumentInfoDTO) req2DTO.getDTO(req);
            conn = getDBConnection(req);
            AmsInstrumentInfoDAO amsInstrumentInfoDAO = new AmsInstrumentInfoDAO(user, dtoParameter, conn);
            dtoParameter.setItemCategory(itemCategory);
            OptionProducer op= new OptionProducer(user, conn);
            String cityOption1 = op.getAllOrganization(0,true);
            req.setAttribute(WebAttrConstant.CITY_OPTION, cityOption1);
            if (action.equals("")) {
                req.setAttribute(WebAttrConstant.AMS_INSTRUMENT_DTO, dtoParameter);
                forwardURL = "/instrument/instrumentRespQuery.jsp";
            } else if (action.equals("QUERY_ACTION2")){ //责任人的查询操作
                  AmsInstrumentInfoModel sqlModel = new AmsInstrumentInfoModel(user, dtoParameter);
                  SQLModel tmodel = sqlModel.getQueryRespModel();
                  WebPageView wpv = new WebPageView(req, conn);
//                wpv.setCalPattern(CalendarConstant.LINE_PATTERN);
                  wpv.produceWebData(tmodel);
                  req.setAttribute(WebAttrConstant.AMS_INSTRUMENT_DTO, dtoParameter);
                  forwardURL = "/instrument/instrumentRespQuery.jsp";
             } else if (action.equals(WebActionConstant.EXPORT_ACTION)) {
                File file = amsInstrumentInfoDAO.exportFile();
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
              } else if (action.equals("EXPORT2")) {
                File file = amsInstrumentInfoDAO.exportRes();
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
          } catch (DataTransException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
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
            //根据实际情况修改页面跳转代码。
        }
    }
}
