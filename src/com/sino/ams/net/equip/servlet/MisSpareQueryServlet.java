package com.sino.ams.net.equip.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.constant.URLDefineList;
import com.sino.ams.net.equip.dao.MisSpareDAO;
import com.sino.ams.net.equip.dto.ItemInfoDTO;
import com.sino.ams.net.equip.model.MisSpareModel;
import com.sino.ams.system.user.dto.SfUserDTO;
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

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2008-4-17
 * Time: 10:27:05
 * To change this template use File | Settings | File Templates.
 */
public class MisSpareQueryServlet extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        String action = req.getParameter("act");
        action = StrUtil.nullToString(action);
        Connection conn = null;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            ItemInfoDTO dtoParameter = new ItemInfoDTO();
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(ItemInfoDTO.class.getName());
            dtoParameter = (ItemInfoDTO) req2DTO.getDTO(req);
            conn = getDBConnection(req);
            MisSpareDAO misSpareDAO = new MisSpareDAO(user, dtoParameter, conn);

            if (action.equals("")) {
                forwardURL = URLDefineList.MIS_SPARE_PAGE;
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {
                BaseSQLProducer sqlProducer = new MisSpareModel(user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.produceWebData();
                forwardURL = URLDefineList.MIS_SPARE_PAGE;
            } else if (action.equals(WebActionConstant.EXPORT_ACTION)) {      //导出
                File file = misSpareDAO.exportFile();
                misSpareDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
            } else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
                misSpareDAO.setDTOClassName(ItemInfoDTO.class.getName());
                ItemInfoDTO spareInfo = (ItemInfoDTO) misSpareDAO.getDataByPrimaryKey();
                if (spareInfo == null) {
                    spareInfo = new ItemInfoDTO();
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                }
                req.setAttribute("详细数据属性，请根据实际情况修改", spareInfo);
                forwardURL = "com.sino.ams.net.equip.servlet.ItemInfoServlet详细数据JSP页面，一般在类似于URLDefineList的常量接口中定义";
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
        } catch (DataTransException e) {
            e.printStackTrace();
        } catch (WebFileDownException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            if(!StrUtil.isEmpty(forwardURL)){
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
            //根据实际情况修改页面跳转代码。
                 }
        }
    }
}
