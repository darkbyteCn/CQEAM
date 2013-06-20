package com.sino.ams.apd.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.apd.dao.AmsAssetsCheckByYrHeaderDAO;
import com.sino.ams.apd.dao.AmsAssetsCheckOrderDAO;
import com.sino.ams.apd.dto.AmsAssetsCheckByYrHeaderDTO;
import com.sino.ams.apd.dto.AmsAssetsCheckByYrLineDTO;
import com.sino.ams.apd.dto.AmsAssetsCheckOrderDTO;
import com.sino.ams.apd.dto.EtsItemCheckDTO;
import com.sino.ams.apd.model.AmsAssetsCheckByYrModel;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.model.ZeroTurnBursurModel;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;

public class AmsAssetsCheckByYrServlet extends BaseServlet {

	@Override
	public void performTask(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO) getUserAccount(req);
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(AmsAssetsCheckByYrHeaderDTO.class.getName());
			AmsAssetsCheckByYrHeaderDTO dto = (AmsAssetsCheckByYrHeaderDTO) req2DTO.getDTO(req);
			String action = dto.getAct();
			conn = getDBConnection(req);
			AmsAssetsCheckByYrHeaderDAO assetsDAO = new AmsAssetsCheckByYrHeaderDAO(user, dto, conn);
			
			if(action.equals("")){
				 AmsAssetsCheckByYrHeaderDTO headerDTO = (AmsAssetsCheckByYrHeaderDTO)req.getAttribute("ORDER_HEAD_DATA");
				 if (headerDTO == null) {
			         headerDTO = fillData(dto, user, conn);
			     } 
	             headerDTO.setCalPattern("YYYY-MM-DD");
	             req.setAttribute("ORDER_HEAD_DATA", headerDTO);
	             forwardURL = "/apd/assetsCheckByYrEdit.jsp";
			}else if(action.equals("DO_CREATE")){
				AmsAssetsCheckByYrModel sqlProducer = new AmsAssetsCheckByYrModel(user, dto);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setPageSize(20);
				pageDAO.produceWebData();
				req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, dto);
	            forwardURL = "/apd/assetsCheckByYrEdit.jsp";
//                DTOSet ds = (DTOSet) req.getAttribute(AssetsWebAttributes.ORDER_LINE_DATA);
//                if (ds == null) {
//                    ds = (DTOSet) assetsDAO.getLineData();
//                }
//                req.setAttribute(AssetsWebAttributes.ORDER_LINE_DATA, ds);
//                req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, dto);
//	            forwardURL = "/apd/assetsCheckByYrEdit.jsp";
	            
			}else if(action.equals("DO_SEND")){
				req2DTO.setDTOClassName(AmsAssetsCheckByYrLineDTO.class.getName());
                req2DTO.setIgnoreFields(AmsAssetsCheckByYrHeaderDTO.class);
                DTOSet deptLines = req2DTO.getDTOSet(req);
                assetsDAO.sendWorkTask(deptLines);
                AmsAssetsCheckByYrModel sqlProducer = new AmsAssetsCheckByYrModel(user, dto);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setPageSize(20);
                pageDAO.produceWebData();
                req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, dto);
                forwardURL = "/apd/assetsCheckByYrEdit.jsp";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			 ServletForwarder forwarder;
		     closeDBConnection(conn);
		     setHandleMessage(req, message);
		     if (!StrUtil.isEmpty(forwardURL)) {
		        forwarder = new ServletForwarder(req, res);
		        forwarder.forwardView(forwardURL);
		     }
	    }
	}
	
	
 private AmsAssetsCheckByYrHeaderDTO fillData(AmsAssetsCheckByYrHeaderDTO dto, SfUserDTO user, Connection conn)
    throws DTOException, QueryException, CalendarException
  {
    dto.setCreatedBy(user.getUserId());
    dto.setCreatedByName(user.getUsername());
    dto.setOrganizationId(user.getOrganizationId());
    String transType=dto.getTransType();
    if(transType.equals("APD")){
    	dto.setTransTypeValue("≈Ãµ„»ŒŒÒ");
    }
    return dto;
  }
}
