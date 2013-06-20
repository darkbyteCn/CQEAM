package com.sino.ams.net.statistic.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.net.statistic.dao.AvisoStatDAO;
import com.sino.ams.net.statistic.dto.AvisoStatDTO;
import com.sino.ams.net.statistic.model.AvisoStatModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * Created by IntelliJ IDEA.
 * User: V-yuanshuai
 * Date: 2007-11-14
 * Time: 15:27:46
 * To change this template use File | Settings | File Templates.
 */
public class AvisoStatServlet extends BaseServlet {

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
        action = StrUtil.nullToString(action);
        Connection conn = null;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            AvisoStatDTO dtoParameter;
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AvisoStatDTO.class.getName());
            dtoParameter = (AvisoStatDTO) req2DTO.getDTO(req);
            conn = getDBConnection(req);
            AvisoStatDAO avisoStatDAO = new AvisoStatDAO(user, dtoParameter, conn);

            OptionProducer optProducer = new OptionProducer(user, conn);

            String yearOption = optProducer.getYearOption(dtoParameter.getYear());
            req.setAttribute(WebAttrConstant.LAST_FIVE_YEAR_OPTION, yearOption);
            String monthOption = optProducer.getMonthOption(dtoParameter.getMonth());
            req.setAttribute(WebAttrConstant.FULL_MONTH_OPTION, monthOption);

            String qryType = dtoParameter.getQryType();
            if (action.equals("")) {
                if (qryType.equals(WebAttrConstant.BY_CHECK)) {
                    forwardURL = URLDefineList.STAT_WO_CHECK_PAGE;
                } else if (qryType.equals(WebAttrConstant.BY_MONTH)) {
                    forwardURL = URLDefineList.STAT_WO_MONTH_PAGE;
                } else if (qryType.equals(WebAttrConstant.BY_YEAR)) {
                    forwardURL = URLDefineList.STAT_WO_YEAR_PAGE;
                } else if (qryType.equals(WebAttrConstant.BY_TIME)) {
                    forwardURL = URLDefineList.STAT_WO_TIME_PAGE;
                }
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {
                if (qryType.equals(WebAttrConstant.BY_CHECK)) {
                    BaseSQLProducer sqlProducer = new AvisoStatModel(user, dtoParameter);
                    PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                    pageDAO.produceWebData();
                    forwardURL = URLDefineList.STAT_WO_CHECK_PAGE;

                } else if (qryType.equals(WebAttrConstant.BY_MONTH)) {    //月工单统计
                    avisoStatDAO.setTimeDistance();
                    BaseSQLProducer sqlProducer = new AvisoStatModel(user, dtoParameter);
                    PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                    pageDAO.produceWebData();
                    forwardURL = URLDefineList.STAT_WO_MONTH_PAGE;

                } else if (qryType.equals(WebAttrConstant.BY_YEAR)) {
                    BaseSQLProducer sqlProducer = new AvisoStatModel(user, dtoParameter);
                    PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                    pageDAO.produceWebData();
                    forwardURL = URLDefineList.STAT_WO_YEAR_PAGE;

                } else if (qryType.equals(WebAttrConstant.BY_TIME)) {
                    BaseSQLProducer sqlProducer = new AvisoStatModel(user, dtoParameter);
                    PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                    pageDAO.produceWebData();
                    forwardURL = URLDefineList.STAT_WO_TIME_PAGE;

                }
                req.setAttribute(WebAttrConstant.AVISO_DTO, dtoParameter);
            } else if (action.equals(WebActionConstant.EXPORT_ACTION)) {      //导出到Excel
                if (qryType.equals(WebAttrConstant.BY_CHECK)) {

                } else if (qryType.equals(WebAttrConstant.BY_MONTH)) {
                    avisoStatDAO.setTimeDistance();
                } else if (qryType.equals(WebAttrConstant.BY_YEAR)) {

                } else if (qryType.equals(WebAttrConstant.BY_TIME)) {

                }

                File file = avisoStatDAO.exportFile();
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
            } else {
                message = getMessage(MsgKeyConstant.INVALID_REQ);
                message.setIsError(true);
                forwardURL = MessageConstant.MSG_PRC_SERVLET;
            }
        } catch (WebFileDownException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
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
        } catch (DataHandleException ex) {
            ex.printLog();
            //请根据实际情况处理消息
            forwardURL = "保持界面录入的数据，返回到原页面，并显示上面给出的消息";
        } catch (ContainerException e) {
            e.printLog();
            Logger.logError(e.toString());
        } catch (ParseException e) {
            Logger.logError(e.toString());
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            if (!StrUtil.isEmpty(forwardURL)) {
                ServletForwarder forwarder = new ServletForwarder(req, res);
                forwarder.forwardView(forwardURL);
                //根据实际情况修改页面跳转代码。
            }
        }
    }
}
