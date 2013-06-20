package com.sino.ams.expand.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;

import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.expand.dao.UploadFileDAO;
import com.sino.ams.expand.dto.UploadFileDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: srf
 * Date: 2008-3-13
 * To change this template use File | Settings | File Templates.
 */
public class FileServlet extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws
            ServletException, IOException {
        String nextPage = null;
        Connection conn = null;
        Message message = SessionUtil.getMessage(req);
        try {
            conn = DBManager.getDBConnection();
            String process = StrUtil.nullToString(req.getParameter("act"));
            SfUserDTO sfDto = (SfUserDTO) getUserAccount(req);
            String docType = req.getParameter("docType");
//            String fileType = req.getParameter("fileType");
            String transId = req.getParameter("transId");
            String transStatus=req.getParameter("transStatus");
//            String orderType = req.getParameter("orderType");
            String[] fileIds = req.getParameterValues("subCheck");
            
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(UploadFileDTO.class.getName());
            UploadFileDTO dto = (UploadFileDTO) req2DTO.getDTO(req);
//            UploadFileDTO dto = new UploadFileDTO();
            UploadFileDAO dao = new UploadFileDAO(conn, req, dto, sfDto);
            if (process.equals("")) { //查询出该单据下的附件
                if (docType.equals("UPLOAD")) {
                    
                    nextPage = "/expand/uploadFile.jsp"+"?transStatus="+transStatus;
                } else {
                    nextPage = "/expand/uploadFile.jsp"+"?transStatus="+transStatus;
                }
                RowSet rs = dao.getFile(transId);
                req.setAttribute("FILE_ROW_SET", rs);
            } else if (process.equals("DOWNLOAD_ACTION")) {
                if (docType.equals("UPLOAD")) {
                    nextPage = "/expand/uploadFile.jsp";
                } else {
                    nextPage = "/expand/uploadInclude.jsp";
                }

                String fileId = req.getParameter("fileId");
                dao.download(fileId, res);
            } else if (process.equals(WebActionConstant.DELETE_ACTION)) {
                dao.deleteFiles(fileIds);
                //重新查询
                RowSet rs = dao.getFile(transId);
                req.setAttribute("FILE_ROW_SET", rs);
                nextPage = "/expand/uploadFile.jsp" + "?transId=" + transId+"?transStatus="+transStatus;
            }
        } catch (PoolException e) {
            e.printLog();
            message = getMessage(AssetsMessageKeys.POOL_PASSIVATE_ERROR);
            message.setIsError(true);
            nextPage = MessageConstant.MSG_PRC_SERVLET;
        } catch (ConfigException e) {
            e.printLog();
            nextPage = MessageConstant.MSG_PRC_SERVLET;
        } catch (QueryException e) {
            e.printLog();
            message = getMessage(AssetsMessageKeys.QUERY_ERROR);
            message.setIsError(true);
            nextPage = MessageConstant.MSG_PRC_SERVLET;
        } catch (SQLException e) {
            e.printStackTrace();
            nextPage = MessageConstant.MSG_PRC_SERVLET;
        } catch (DataHandleException e) {
            e.printLog();
            nextPage = MessageConstant.MSG_PRC_SERVLET;
        } catch (ContainerException e) {
            e.printLog();
            nextPage = MessageConstant.MSG_PRC_SERVLET;
        } catch (WebFileDownException e) {
            e.printLog();
            nextPage = MessageConstant.MSG_PRC_SERVLET;
        } catch (DTOException ex) {
            ex.printLog();
            message = getMessage(AssetsMessageKeys.DTO_ERROR);
            message.setIsError(true);
            nextPage = MessageConstant.MSG_PRC_SERVLET;
        } finally {
            closeDBConnection(conn);
            req.getRequestDispatcher(nextPage).forward(req, res);
        }
    }
}
