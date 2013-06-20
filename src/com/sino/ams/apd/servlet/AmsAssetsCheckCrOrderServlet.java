package com.sino.ams.apd.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.apd.dao.AmsAssetsCheckByYrLineDAO;
import com.sino.ams.apd.dto.AmsAssetsCheckByYrLineDTO;
import com.sino.ams.apd.dto.AmsAssetsCheckOrderDTO;
import com.sino.ams.apd.model.AmsAssetsCheckByYrLineModel;
import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.calen.SimpleCalendar;
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

public class AmsAssetsCheckCrOrderServlet extends BaseServlet {

	@Override
	public void performTask(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO) getUserAccount(req);
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(AmsAssetsCheckByYrLineDTO.class.getName());
			AmsAssetsCheckByYrLineDTO dto = (AmsAssetsCheckByYrLineDTO) req2DTO.getDTO(req);
			String action = dto.getAct();
			conn = getDBConnection(req);
			AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
			AmsAssetsCheckByYrLineDAO headerDAO = new AmsAssetsCheckByYrLineDAO(user, dto, conn);
			String sc=getNowCalendar(); 
			dto.setEndDisableDate(sc);
			
			if(action.equals("")){
				 AmsAssetsCheckByYrLineDTO headerDTO = (AmsAssetsCheckByYrLineDTO)req.getAttribute("ORDER_HEAD_DATA");
				 if (headerDTO == null) {
					 headerDTO=(AmsAssetsCheckByYrLineDTO) headerDAO.getTraskUserModel(conn);
			     } 
				 String transtatus=dto.getTransStatus();
				 if(!transtatus.equals("")){
					 AmsAssetsCheckByYrLineModel sqlProducer = new AmsAssetsCheckByYrLineModel(user, headerDTO);
					 PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
					 dto.setRborganizationId(user.getOrganizationId());
		             pageDAO.setPageSize(20);
				     pageDAO.produceWebData();
				 }
				 String emergentLevelOption = optProducer.getApdStatus(headerDTO.getCreateType(),"A");
				 headerDTO.setTypeValueOption(emergentLevelOption);
	             headerDTO.setCalPattern("YYYY-MM-DD");
	             headerDTO.setRborganizationId(user.getOrganizationId());
	             headerDTO.setEndDisableDate(sc);
	             req.setAttribute("ORDER_HEAD_DATA", headerDTO);
	             forwardURL = "/apd/assetsCheckCrOrder.jsp";
			}else if(action.equals("DO_CREATE")){
				AmsAssetsCheckByYrLineModel sqlProducer = new AmsAssetsCheckByYrLineModel(user, dto);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				String emergentLevelOption = optProducer.getApdStatus(dto.getCreateType(),"A");
				dto.setTypeValueOption(emergentLevelOption);
				dto.setRborganizationId(user.getOrganizationId());
                pageDAO.setPageSize(20);
				pageDAO.produceWebData();
				req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, dto);
	            forwardURL = "/apd/assetsCheckCrOrder.jsp";
			}else if(action.equals("DO_SAVE")){
				req2DTO.setDTOClassName(AmsAssetsCheckOrderDTO.class.getName());
                req2DTO.setIgnoreFields(AmsAssetsCheckByYrLineDTO.class);
                DTOSet deptLines = req2DTO.getDTOSet(req);
                AmsAssetsCheckByYrLineDAO lineDAO = new AmsAssetsCheckByYrLineDAO(user, dto, conn);
                lineDAO.saveWorkTask(deptLines);
                AmsAssetsCheckByYrLineModel sqlProducer = new AmsAssetsCheckByYrLineModel(user, dto);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                String emergentLevelOption = optProducer.getApdStatus(dto.getCreateType(),"A");
				dto.setTypeValueOption(emergentLevelOption);
				dto.setRborganizationId(user.getOrganizationId());
                pageDAO.setPageSize(20);
                pageDAO.produceWebData();
                req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, dto);
                forwardURL = "/apd/assetsCheckCrOrder.jsp";
			}else if(action.equals("DO_SEND")){
				req2DTO.setDTOClassName(AmsAssetsCheckOrderDTO.class.getName());
                req2DTO.setIgnoreFields(AmsAssetsCheckByYrLineDTO.class);
                DTOSet deptLines = req2DTO.getDTOSet(req);
                AmsAssetsCheckByYrLineDAO lineDAO = new AmsAssetsCheckByYrLineDAO(user, dto, conn);
                lineDAO.sendWorkTask(deptLines);
                AmsAssetsCheckByYrLineModel sqlProducer = new AmsAssetsCheckByYrLineModel(user, dto);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                String emergentLevelOption = optProducer.getApdStatus(dto.getCreateType(),"A");
				dto.setTypeValueOption(emergentLevelOption);
				dto.setRborganizationId(user.getOrganizationId());
                pageDAO.setPageSize(20);
                pageDAO.produceWebData();
                req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, dto);
                forwardURL = "/apd/assetsCheckCrOrder.jsp";
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
	
	
private AmsAssetsCheckByYrLineDTO fillData(AmsAssetsCheckByYrLineDTO dto, SfUserDTO user, Connection conn)
    throws DTOException, QueryException, CalendarException
  {
    dto.setReceivdBy(user.getUserId());
    dto.setReceivdByName(user.getUsername());
    dto.setRborganizationId(user.getOrganizationId());
    dto.setCompanyCode(user.getCompanyCode());
    dto.setCompany(user.getCompany());
    dto.setBookTypeCode(user.getBookTypeCode());
    dto.setBookTypeName(user.getBookTypeName());
    return dto;
  }

//获取当前日历时间
public String  getNowCalendar(){
	Date now = new Date(); 
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	String cal=sdf.format(now);
//	SimpleCalendar sc=new SimpleCalendar(cal);
	return cal;
}
}