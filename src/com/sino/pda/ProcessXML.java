package com.sino.pda;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.base.db.conn.DBManager;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.FileSizeException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.UploadException;
import com.sino.base.file.FileAssistant;
import com.sino.base.log.Logger;
import com.sino.base.web.request.upload.UploadFile;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 处理PDA上传的XML文件</p>
 * <p>Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2007</p>
 * <p>Company: 北京思诺搏信息技术有限公司</p>
 * @author 何睿
 * @version 0.1
 *          Date: 2007-11-8
 */
public abstract class ProcessXML extends BaseServlet {
    private static final String sContentType = "application/xml; charset=GBK";
    private String uploadFilePath = "/temp";
    private String rootName = "";      //请设置根元素的名称
    public HttpServletRequest req = null;
    public PrintWriter out = null;
    public Connection conn = null;
    public BaseUserDTO userAccount = null;


    private void printResult(boolean b_flag, String message) {
        out.println("<result message=\"" + message + "\">" + String.valueOf(b_flag) + "</result>");
    }


    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        initData();
        this.req = req;
        userAccount = SessionUtil.getUserAccount(req);
        boolean success = false;
        boolean parseXmlError = false;

        res.setContentType(sContentType);
        out = res.getWriter();
        out.println("<" + rootName + ">");
        FileAssistant filePs = new FileAssistant();

        UploadFile[] upFiles = null;

        try {
            upFiles = filePs.getUploadFiles(uploadFilePath, req);
        } catch (UploadException e) {
            parseXmlError = true;
            Logger.logError(e);
            printResult(false, "Upload file error.");
        } catch (FileSizeException e) {
            parseXmlError = true;
            Logger.logError(e);
            printResult(false, "Upload file error.");
        } catch (ContainerException e) {
            parseXmlError = true;
            Logger.logError(e);
            printResult(false, "Upload file error.");
        }
        if (upFiles == null) {
            parseXmlError = true;
            printResult(false, "The file uploade " + SyBaseSQLUtil.isNull() + " .");
            Logger.logError("PDA upload xm " + SyBaseSQLUtil.isNull() + " ");
        } else {
            if (upFiles.length > 1) {
                parseXmlError = true;
                printResult(false, "The number of files uploaded is more than one.");
                Logger.logError("PDA upload xml file more than one");
            }
        }
        try {
            if (!parseXmlError) {
                SAXBuilder builder = new SAXBuilder();
                Document xmlDoc = builder.build(new File(upFiles[0].getAbsolutePath()));
                //            Element root = xmlDoc.getRootElement();
                //            List items = root.getChildren();
                //            String orderNo = root.getAttributeValue("no");
                //已获得文件,只需继承该类来完成业务处理
                conn = getDBConnection(req);
                success = processData(xmlDoc);
            }
            if (success) {
                printResult(true, "success");
            } else {
                printResult(false, "Process error.");
            }
        } catch (JDOMException e) {
            printResult(false, "Process error.");
            Logger.logError(e);
        } catch (PoolPassivateException e) {
            printResult(false, "Process error.");
            Logger.logError(e);
        } finally {
            DBManager.closeDBConnection(conn);
            out.println("</" + rootName + ">");
            out.flush();
            out.close();
        }
    }

    /**
     * 需实现该方法,初始化数据,如根元素名称
     */
    public abstract void initData();

    /**
     * 需实现该方法
     * @param xmlDoc Document
     * @return boolean
     */
    public abstract boolean processData(Document xmlDoc);

    public void setUploadFilePath(String uploadFilePath) {
        this.uploadFilePath = uploadFilePath;
    }


    public String getRootName() {
        return rootName;
    }

    public void setRootName(String rootName) {
        this.rootName = rootName;
    }
}
