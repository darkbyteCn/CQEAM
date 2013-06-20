package com.sino.ams.expand.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;

import com.sino.base.constant.message.MessageConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.ConfigException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolException;
import com.sino.base.message.Message;

import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.expand.dao.UploadFileDAO;
import com.sino.ams.expand.dto.UploadFileDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: srf
 * Date: 2008-3-12
 * To change this template use File | Settings | File Templates.
 */
public class UploadFileServlet extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws
            ServletException, IOException {
        String nextPage = "/expand/uploadFile.jsp";
        String docType = req.getParameter("docType");
        String transId = req.getParameter("transId");
        String transStatus=req.getParameter("transStatus");
        Connection conn = null;
        Message message = SessionUtil.getMessage(req);
        SfUserDTO sfDto = (SfUserDTO) getUserAccount(req);
        try {

            conn = DBManager.getDBConnection();
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(UploadFileDTO.class.getName());
            UploadFileDTO dto = (UploadFileDTO) req2DTO.getDTO(req);
            dto.setBarcode(transId);
            UploadFileDAO dao = new UploadFileDAO(conn, req, dto, sfDto);
            String[] arr = dao.upload(Integer.toString(sfDto.getUserId()));
            nextPage = "/servlet/com.sino.ams.expand.servlet.FileServlet" +
                       "?transId=" + arr[0] + "&transStatus="+transStatus;
        } catch (PoolException e) {
            e.printLog();
            message = getMessage(AssetsMessageKeys.POOL_PASSIVATE_ERROR);
            message.setIsError(true);
            nextPage = MessageConstant.MSG_PRC_SERVLET;
        } catch (ConfigException e) {
            e.printLog();
            nextPage = MessageConstant.MSG_PRC_SERVLET;
        } catch (SQLException e) {
            e.printStackTrace();
            nextPage = MessageConstant.MSG_PRC_SERVLET;
        } catch (DTOException ex) {
            ex.printLog();
            message = getMessage(AssetsMessageKeys.DTO_ERROR);
            message.setIsError(true);
            nextPage = MessageConstant.MSG_PRC_SERVLET;
        } catch (FileUploadException e) {
            message = getMessage(AssetsMessageKeys.COMMON_ERROR);
            message.setIsError(true);
            nextPage = MessageConstant.MSG_PRC_SERVLET;
        } finally {
            closeDBConnection(conn);
            req.getRequestDispatcher(nextPage).forward(req, res);
        }
    }
}
