package com.sino.ams.workorder.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dao.ZeroTurnBursurDAO;
import com.sino.ams.workorder.dto.ZeroTurnBursurHDTO;
import com.sino.ams.workorder.dto.ZeroturnLineBursurDTO;
import com.sino.ams.workorder.model.ZeroTurnBursurModel;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.sinoflow.dao.DeptGroupDAO;

public class ZeroTurnReimburServlet extends BaseServlet {

	@Override
	public void performTask(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		String action = req.getParameter("act");
        action = StrUtil.nullToString(action);
		Connection conn = null;
		
        try {
        	SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);// 从session中获取数据，根据实际情况自行修改。
			ZeroTurnBursurHDTO dto;
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(ZeroTurnBursurHDTO.class.getName());
			dto = (ZeroTurnBursurHDTO) req2DTO.getDTO(req);
			conn = DBManager.getDBConnection();
			
			AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
	        String option = optProducer.getReimburseStatus(dto.getArrivalStatus());
	        dto.setArrivalStatusName(option);

            if (action.equals("")) {
            	   req.setAttribute(AssetsWebAttributes.ORDER_LINE_DATA, dto);
			       forwardURL ="/workorder/zeroTurnReimbur.jsp";
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
					ZeroTurnBursurModel sqlProducer = new ZeroTurnBursurModel(user, dto);
					PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
	                pageDAO.setPageSize(20);
					pageDAO.produceWebData();
					req.setAttribute(AssetsWebAttributes.ORDER_LINE_DATA, dto);
					forwardURL = "/workorder/zeroTurnReimbur.jsp";
            }else if (action.equals(WebActionConstant.SAVE_ACTION)) {
                req2DTO.setDTOClassName(ZeroturnLineBursurDTO.class.getName());
                req2DTO.setIgnoreFields(ZeroTurnBursurHDTO.class);
                DTOSet deptLines = req2DTO.getDTOSet(req);
                ZeroTurnBursurDAO lineDAO = new ZeroTurnBursurDAO(user, dto, conn);
                lineDAO.reimburser(deptLines);
                ZeroTurnBursurModel sqlProducer = new ZeroTurnBursurModel(user, dto);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setPageSize(20);
                pageDAO.produceWebData();
                req.setAttribute(AssetsWebAttributes.ORDER_LINE_DATA, dto);
                forwardURL = "/workorder/zeroTurnReimbur.jsp";
			}else if (action.equals("cancel")) {
                req2DTO.setDTOClassName(ZeroturnLineBursurDTO.class.getName());
                req2DTO.setIgnoreFields(ZeroTurnBursurHDTO.class);
                DTOSet deptLines = req2DTO.getDTOSet(req);
                ZeroTurnBursurDAO lineDAO = new ZeroTurnBursurDAO(user, dto, conn);
                lineDAO.reimburserCancel(deptLines);
                ZeroTurnBursurModel sqlProducer = new ZeroTurnBursurModel(user, dto);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setPageSize(20);
                pageDAO.produceWebData();
                req.setAttribute(AssetsWebAttributes.ORDER_LINE_DATA, dto);
                forwardURL = "/workorder/zeroTurnReimbur.jsp";
			} else if(action.equals(WebActionConstant.EXPORT_ACTION)) {
                ZeroTurnBursurDAO deptGroupDAO = new ZeroTurnBursurDAO(user, dto, conn);
                File file = deptGroupDAO.exportFile();
                deptGroupDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
            } }catch (ContainerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} /*catch (QueryException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/ catch (DTOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (QueryException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DataTransException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (WebFileDownException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
	            closeDBConnection(conn);
	            setHandleMessage(req, message);
	            if (!StrUtil.isEmpty(forwardURL)) {
	                ServletForwarder forwarder = new ServletForwarder(req, res);
	                forwarder.forwardView(forwardURL);
	            }
			}
	}

}
