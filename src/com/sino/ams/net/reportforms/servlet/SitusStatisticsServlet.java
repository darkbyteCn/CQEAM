package com.sino.ams.net.reportforms.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.net.reportforms.dao.SitusStatisticsDAO;
import com.sino.ams.net.reportforms.dto.SitusStatisticsDTO;
import com.sino.ams.net.reportforms.model.SitusStatisticsModel;
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
 * Date: 2007-11-12
 * Time: 10:29:29
 * To change this template use File | Settings | File Templates.
 */
public class SitusStatisticsServlet extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        String act = req.getParameter("act");
        act = StrUtil.nullToString(act);
        Connection conn = null;

        try {
//            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
//            SitusStatisticsDTO dtoParameter = null;
//            Request2DTO req2 = new Request2DTO();
//            req2.setDTOClassName(SitusStatisticsDTO.class.getName());
//            dtoParameter = (SitusStatisticsDTO) req2.getDTO(req);
//            conn = getDBConnection(req);
//            SitusStatisticsDAO situsDAO = new SitusStatisticsDAO(user, dtoParameter, conn);

            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            SitusStatisticsDTO dtoParameter = null;
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(SitusStatisticsDTO.class.getName());
            dtoParameter = (SitusStatisticsDTO) req2DTO.getDTO(req);
            conn = getDBConnection(req);
            SitusStatisticsDAO situsDAO = new SitusStatisticsDAO(user, dtoParameter, conn);
//            dtoParameter.setCategoryName(etsObjectDAO.getCategoryName());
//            OptionProducer optProducer = new OptionProducer(user, conn);
            OptionProducer prd = new OptionProducer(user, conn);
            String itemCatOption = prd.getDictOption(DictConstant.OBJECT_CATEGORY, dtoParameter.getObjectCategory());
            req.setAttribute(WebAttrConstant.EQUIPMENT_OPTION, itemCatOption);
            if (act.equals("")) {
                forwardURL = URLDefineList.SITUS_STATISTIC_QUERY;
            } else if (act.equals(WebActionConstant.QUERY_ACTION)) {                             //查询出所有
//                projectDAO.produceProjects();
                BaseSQLProducer sqlProducer = new SitusStatisticsModel(user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.produceWebData();
                forwardURL = URLDefineList.SITUS_STATISTIC_QUERY;
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
        } catch (DataTransException e) {
            e.printLog();
        } catch (WebFileDownException e) {
            e.printLog();
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            if (!StrUtil.isEmpty(forwardURL)) {
                forwarder.forwardView(forwardURL);
            }
        }
    }
}
