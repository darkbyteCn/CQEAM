package com.sino.ams.system.log.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.constant.CustMessageKey;
import com.sino.ams.constant.URLDefineList;
import com.sino.base.config.ConfigLoader;
import com.sino.base.config.LogConfig;
import com.sino.base.constant.WorldConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;

public class SystemRunLogServlet extends BaseServlet {
    /**
     * 所有的Servlet都必须实现的方法。
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        try {
            LogConfig logConfig = ConfigLoader.loadLogConfig();
            String logPath = logConfig.getLogPath();
            String act = req.getParameter("act");
            String fileName = req.getParameter("fileName");
            if (StrUtil.isEmpty(act)) {
                req.setAttribute("LOG_PATH", logPath);
                forwardURL = URLDefineList.SYS_LOG_PAGE;
            } else if (act.equals(WebActionConstant.DOWNLOAD_ACTION)) {
                if (!logPath.endsWith(WorldConstant.FILE_SEPARATOR)) {
                    logPath += WorldConstant.FILE_SEPARATOR;
                }
                fileName = logPath + fileName;
                File file = new File(fileName);
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
            } else if (act.equals(WebActionConstant.DELETE_ACTION)) {//删除日志文件
                if (!logPath.endsWith(WorldConstant.FILE_SEPARATOR)) {
                    logPath += WorldConstant.FILE_SEPARATOR;
                }
                fileName = logPath + fileName;
                File file = new File(fileName);
                file.delete();
                forwardURL = "/servlet/com.sino.ams.system.log.servlet.SystemRunLogServlet?act=";
            } else {
                message = getMessage(MsgKeyConstant.INVALID_REQ);
                message.setIsError(true);
                forwardURL = MessageConstant.MSG_PRC_SERVLET;
            }
        } catch (WebFileDownException ex) {
            ex.printLog();
            message = getMessage(CustMessageKey.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } finally {
            setHandleMessage(req, message);
            if (!StrUtil.isEmpty(forwardURL)) {
                ServletForwarder forwarder = new ServletForwarder(req, res);
                forwarder.forwardView(forwardURL);
            }
        }
    }
}
