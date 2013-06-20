package com.sino.ams.spare.servlet;

import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.base.message.Message;
import com.sino.base.dto.Request2DTO;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.exception.*;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.newasset.constant.*;
import com.sino.ams.spare.repair.dto.AmsVendorInfoDTO;
import com.sino.ams.spare.dao.BjVendorDAO;
import com.sino.ams.spare.model.BjVendorModel;
import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.WebAttrConstant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-12-02
 * Time: 00:00:00
 * To change this template use File | Settings | File Templates.
 */
public class BjVendorServlet extends BaseServlet {

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO) getUserAccount(req);
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(AmsVendorInfoDTO.class.getName());
			AmsVendorInfoDTO dto = (AmsVendorInfoDTO) req2DTO.getDTO(req);
			String action = dto.getAct();
			conn = getDBConnection(req);
            BjVendorDAO rptDAO = new BjVendorDAO(user, dto, conn);
            OptionProducer  optProducer = new OptionProducer(user,conn);
            String vendorOption = optProducer.getSpareVendorOption(dto.getVendorId());
            req.setAttribute(WebAttrConstant.SPARE_VENDOR_OPTION, vendorOption);
            if (action.equals("")) {
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = AssetsURLList.BJ_VENDOR_QUERY;
			} else if (action.equals(AssetsActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new BjVendorModel(user, dto);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setCalPattern(LINE_PATTERN);
                pageDAO.produceWebData();
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = AssetsURLList.BJ_VENDOR_QUERY;
			} else if (action.equals(WebActionConstant.NEW_ACTION)) {
                req.setAttribute(QueryConstant.QUERY_DTO, dto);
                forwardURL = AssetsURLList.BJ_VENDOR_INFO;
            } else if (action.equals(WebActionConstant.CREATE_ACTION)) {
				rptDAO.setServletConfig(getServletConfig(req));
				rptDAO.createData();
				message = rptDAO.getMessage();
                forwardURL = AssetsURLList.BJ_VENDOR_SERVLET;
            } else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
				rptDAO.setServletConfig(getServletConfig(req));
				rptDAO.updateData();
				message = rptDAO.getMessage();
                forwardURL = AssetsURLList.BJ_VENDOR_SERVLET;
            } else if (action.equals(AssetsActionConstant.DETAIL_ACTION)) {
                rptDAO.setDTOClassName(AmsVendorInfoDTO.class.getName());
				AmsVendorInfoDTO ridDto = (AmsVendorInfoDTO)rptDAO.getDataByPrimaryKey();
				if(ridDto == null){
					dto = new AmsVendorInfoDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
                vendorOption = optProducer.getSpareVendorOption(ridDto.getVendorId());
                req.setAttribute(WebAttrConstant.SPARE_VENDOR_OPTION, vendorOption);
                req.setAttribute(QueryConstant.QUERY_DTO, ridDto);
                forwardURL = AssetsURLList.BJ_VENDOR_INFO;
			} else if (action.equals(WebActionConstant.DELETE_ACTION)) {
				rptDAO.deleteData();
				message = rptDAO.getMessage();
				forwardURL = AssetsURLList.BJ_VENDOR_SERVLET;
			} else if (action.equals("verifyworkNo")) {
                String vendorId = req.getParameter("vendorId");
                String vendorName = req.getParameter("vendorName");
                boolean success = rptDAO.doVerify(vendorId, vendorName);
                PrintWriter out = res.getWriter();
                if (success) {
                    out.print("Y");
                }
                out.flush();
                out.close();
            } else {
				message = getMessage(MsgKeyConstant.INVALID_REQ);
				message.setIsError(true);
				forwardURL = MessageConstant.MSG_PRC_SERVLET;
			}
		} catch (PoolPassivateException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.POOL_PASSIVATE_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DTOException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.DTO_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (QueryException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.QUERY_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DataHandleException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (ContainerException e) {
            e.printStackTrace();
        } finally {
			closeDBConnection(conn);
			setHandleMessage(req, message);
			if(!StrUtil.isEmpty(forwardURL)){
				ServletForwarder forwarder = new ServletForwarder(req, res);
				forwarder.forwardView(forwardURL);
			}
		}
	}
}
