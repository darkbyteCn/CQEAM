package com.sino.ams.net.reportforms.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.net.reportforms.dao.IndividualComRateDAO;
import com.sino.ams.net.reportforms.dto.SitusStatisticsDTO;
import com.sino.ams.net.reportforms.model.IndividualComRateModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.PoolException;
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
 * User: Zyun
 * Date: 2007-11-13
 * Time: 9:32:36
 * To change this template use File | Settings | File Templates.
 */
public class IndividualComRateServlet extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        String act = req.getParameter("act");
        act = StrUtil.nullToString(act);
        Connection conn = null;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            SitusStatisticsDTO dtoParameter = null;
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(SitusStatisticsDTO.class.getName());
            dtoParameter = (SitusStatisticsDTO) req2DTO.getDTO(req);
            conn = getDBConnection(req);
            IndividualComRateDAO situsDAO = new IndividualComRateDAO(user, dtoParameter, conn);
            OptionProducer prd = new OptionProducer(user, conn);
            String ouOption = prd.getAllOrganization(dtoParameter.getOrganizationId(), true);
            req.setAttribute(WebAttrConstant.OU_OPTION, ouOption);
            if (act.equals("")) {
                req.setAttribute(WebAttrConstant.ETS_WORKORDER_DTO, dtoParameter);
                forwardURL = URLDefineList.INDIVIDUAL_RATE_STATISTIC;
            } else if (act.equals(WebActionConstant.QUERY_ACTION)) {                             //查询出所有
                BaseSQLProducer sqlProducer = new IndividualComRateModel(user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.produceWebData();
                req.setAttribute(WebAttrConstant.ETS_WORKORDER_DTO, dtoParameter);
                forwardURL = URLDefineList.INDIVIDUAL_RATE_STATISTIC;
            } else if (act.equals(WebActionConstant.EXPORT_ACTION)) {      //导出
                File file = situsDAO.exportFile();
                situsDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
            }
        } catch (PoolException e) {
             e.printLog();
            Logger.logError(e.toString());
        } catch (DTOException e) {
            e.printLog();
            Logger.logError(e.toString());
        } catch (QueryException e) {
            e.printLog();
            Logger.logError(e.toString());
        } catch (DataTransException e) {
            e.printLog();
            Logger.logError(e.toString());
        } catch (WebFileDownException e) {
           e.printLog();
            Logger.logError(e.toString());
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            if (!StrUtil.isEmpty(forwardURL)) {
                ServletForwarder forwarder = new ServletForwarder(req, res);
                forwarder.forwardView(forwardURL);
            }
        }
    }
}
