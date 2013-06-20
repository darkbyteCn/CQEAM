package com.sino.ams.system.update4pda.servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.constant.URLDefineList;
import com.sino.ams.plan.model.AmsWorkPlanQueryModel;
import com.sino.ams.system.update4pda.dao.EtsAutoupdateDAO;
import com.sino.ams.system.update4pda.dto.EtsAutoupdateDTO;
import com.sino.ams.system.update4pda.model.EtsAutoupdateModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.FileException;
import com.sino.base.exception.FileSizeException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.exception.UploadException;
import com.sino.base.file.FileProcessor;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.base.web.request.upload.UploadFile;
import com.sino.base.web.request.upload.UploadFileSaver;
import com.sino.base.web.request.upload.UploadRow;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: EtsAutoupdateServlet</p>
 * <p>Description:程序自动生成服务程序“EtsAutoupdateServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author aidy
 * @version 1.0
 */


public class EtsAutoupdateServlet extends BaseServlet {

    /**
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        try {

            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(EtsAutoupdateDTO.class.getName());
            RequestParser parser = new RequestParser();
            parser.transData(req);
            EtsAutoupdateDTO dtoParameter = (EtsAutoupdateDTO) req2DTO.getDTO(parser);
            String action = dtoParameter.getAct();
            conn = getDBConnection(req);
            EtsAutoupdateDAO etsAutoupdateDAO = new EtsAutoupdateDAO(user, dtoParameter, conn);
            if (action.equals("") || action.equals(WebActionConstant.QUERY_ACTION)) {
                BaseSQLProducer sqlProducer = new EtsAutoupdateModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				pageDAO.produceWebData();
            	forwardURL = "/system/update4pda/pdaQuery.jsp?flag=N";
            } else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
                String module = StrUtil.nullToString(req.getParameter("module"));
                EtsAutoupdateDTO dto = etsAutoupdateDAO.getDataByModule(module);
                req.setAttribute("PDA_MODULE",dto);
                forwardURL = "/system/update4pda/update.jsp?flag=N";
            } else if (action.equals("getVersion")) {
                String module = StrUtil.nullToString(req.getParameter("module"));
                EtsAutoupdateDTO dto = etsAutoupdateDAO.getDataByModule(module);
                PrintWriter out = res.getWriter();
                out.print(dto.getVersion());
                out.flush();
                out.close();
            } else if (action.equals(WebActionConstant.UPLOAD_ACTION)) {
                String webRoot = req.getSession().getServletContext().getRealPath("/update/");
                UploadFileSaver saver = parser.getFileSaver();
                saver.saveFiles("/");
                UploadRow row = saver.getRow();
                UploadFile uploadFile = row.getFiles()[0];
                int fileSize = uploadFile.getSize();
                dtoParameter.setFilesize(fileSize);
                //String module = StrUtil.nullToString(req.getParameter("module"));
                //dtoParameter.setModule(module);
                dtoParameter.setLastUpdateBy(user.getUserId());
                etsAutoupdateDAO.setDataByModule(dtoParameter);
                FileProcessor fp=new FileProcessor();
                fp.copyFile(uploadFile.getAbsolutePath(),webRoot);
                forwardURL = "/system/update4pda/update.jsp?flag=Y";

                //forwardURL = "/servlet/com.sino.ams.system.update4pda.servlet.EtsAutoupdateServlet";
                
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
        } catch (DataHandleException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.DTO_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (UploadException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.DTO_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (ContainerException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.DTO_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (SQLModelException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.DTO_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (QueryException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.DTO_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (FileSizeException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.DTO_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (FileException e) {
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