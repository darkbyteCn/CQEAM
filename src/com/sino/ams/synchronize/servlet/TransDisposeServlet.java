package com.sino.ams.synchronize.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.synchronize.dao.TransStatusDAO;
import com.sino.ams.synchronize.dto.EamSyschronizeDTO;
import com.sino.ams.synchronize.model.TransStatusQueryModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
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
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * Created by 
 * User: wangzp
 * Date: 2011-12-21 
 * Time: 3:00:27
 * To 事务处理状态查询
 */
public class TransDisposeServlet extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        String action = req.getParameter("act");
        action = StrUtil.nullToString(action);
        Connection conn = null;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            EamSyschronizeDTO dtoParameter = null;  //声明DTO
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(EamSyschronizeDTO.class.getName());
            dtoParameter = (EamSyschronizeDTO) req2DTO.getDTO(req);
            conn = getDBConnection(req);
            TransStatusDAO eamNewLocusDAO = new TransStatusDAO(user, dtoParameter, conn);
            ServletConfigDTO servletConfig = getServletConfig(req);
            if (action.equals("")) {
                forwardURL = URLDefineList.TRANS_QUERY;
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {  //查询操作
                BaseSQLProducer sqlProducer = new TransStatusQueryModel(user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setServletConfig(servletConfig);
                pageDAO.setCalPattern(LINE_PATTERN);
                pageDAO.produceWebData();
                req.setAttribute(WebAttrConstant.SYSCHRONIZE_DTO, dtoParameter);
                forwardURL = URLDefineList.TRANS_QUERY;
            } else if (action.equals(WebActionConstant.EXPORT_ACTION)) {  //导出Excel
                File file = eamNewLocusDAO.getExportFile();
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
            } else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
                eamNewLocusDAO.setDTOClassName(EamSyschronizeDTO.class.getName());
                String batchId = req.getParameter("id");
                dtoParameter.setBatchId(batchId);
                eamNewLocusDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
                //   EamSyschronizeDTO dto = (EamSyschronizeDTO)eamNewLocusDAO.getDataByForeignKey( batchId);
                //    if(dto == null){
                //	dto = new EamSyschronizeDTO();
                //		message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                //		message.setIsError(true);
                //	   	}  else{
//                      dto = new EamSyschronizeDTO();
//                    EamSyschronizeDTO orderDTO = new EamSyschronizeDTO();
//                    orderDTO.setBatchId(dtoParameter.getBatchId());
//                    TransStatusDAO orderDAO = new TransStatusDAO(user, orderDTO, conn);
//                    orderDAO.setDTOClassName(EamSyschronizeDTO.class.getName());
//                    orderDAO.setCalPattern(LINE_PATTERN);
                DTOSet orders = (DTOSet) eamNewLocusDAO.getDataByForeignKey(batchId);
                req.setAttribute("DTOSET", orders);
                //   }
                //  req.setAttribute(WebAttrConstant.SYSCHRONIZE_DTO, dto);
                forwardURL = "/synchronize/StatusDetail.jsp";
            } else if (action.equals("show")) {
                eamNewLocusDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
                String logId = req.getParameter("logId");
                RowSet rs = eamNewLocusDAO.getLog(logId);
                req.setAttribute("DATA", rs);
                forwardURL = "/synchronize/logInfo.jsp";
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
        } catch (WebFileDownException e) {
            e.printLog();
        } catch (DataTransException e) {
            e.printLog();
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
            //根据实际情况修改页面跳转代码。
        }

    }

}
