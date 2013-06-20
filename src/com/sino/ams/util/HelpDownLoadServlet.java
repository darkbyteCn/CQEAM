package com.sino.ams.util;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;

/**
 * User: T_zhoujinsong
 * Date: 11-12-21 下午5:42
 * Function:首页帮助文件下载
 */

public class HelpDownLoadServlet extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        try {
            conn = getDBConnection(req);
            String fileName = req.getParameter("fileName");

            System.out.println(StrUtil.valPara(fileName));
//            if (!StrUtil.valPara(fileName)) {
                String filePath = req.getSession().getServletContext().getRealPath("/document/help/");
                filePath += "/" + fileName;
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(filePath);
                fileDown.setFileName(fileName);
                fileDown.download();
//            }

        } catch (PoolPassivateException e) {
            e.printLog();
            message = getMessage(MsgKeyConstant.POOL_PASSIVATE_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (WebFileDownException e) {
            Logger.logError(e);
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            if (StrUtil.isNotEmpty(forwardURL)) {
                ServletForwarder forwarder = new ServletForwarder(req, res);
                forwarder.forwardView(forwardURL);
            }
        }
    }
}

