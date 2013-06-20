package com.sino.ams.print.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.print.dao.BarcodePrintDAO;
import com.sino.ams.print.dto.BarcodeReceiveDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.system.user.util.UserUtil;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;


public class BarcodePrintServlet extends BaseServlet {

	/**
	 * Title: 			SinoApplication
	 * Description:		Java Enterprise Edition 应用开发
	 * Copyright:		李轶版权所有Copyright (c)2009~2022。
	 * Copyright: 		其中使用到的第三方组件，根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约，版权属原作者所有。
	 * Copyright: 		作者授权北京思诺博信息技术有限公司在一定范围内使用
	 * Company: 		北京思诺博信息技术有限公司
	 * @author 			李轶
	 * @version 		0.1
	 * @Date			Nov 2, 2009
	 */
	public BarcodePrintServlet() {

	}

	/**
     * Function:		标签领用打印数量确认维护
     * @param 			req   HttpServletRequest
     * @param 			res   HttpServletResponse
     * @return			void
     * @author  		李轶
     * @Version 		0.1
     * @Date:   		Nov 2, 2009
     */
	public void performTask(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		String action = req.getParameter("act");
		action = StrUtil.nullToString(action);
		Connection conn = null;
		SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(req);
		UserUtil sfUserUtil = new UserUtil(userAccount);
		PrintWriter pw = null;
		try {
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(BarcodeReceiveDTO.class.getName());
			BarcodeReceiveDTO dtoParameter = (BarcodeReceiveDTO) req2DTO.getDTO(req);
			conn = DBManager.getDBConnection();
			sfUserUtil.setServletConfig(getServletConfig(req));

			BarcodePrintDAO dao = new BarcodePrintDAO(userAccount, dtoParameter, conn);
			OptionProducer op = new OptionProducer(userAccount, conn);
			req.setAttribute(WebAttrConstant.BARCODE_RECEIVE_DTO, dtoParameter);

			if (action.equals("")) {
				forwardURL = URLDefineList.BARCODE_PRINT_PAGE;
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {       //查询
                dao.setDTOClassName(BarcodeReceiveDTO.class.getName());
				BarcodeReceiveDTO brDTO = (BarcodeReceiveDTO)dao.getDataByPrimaryKey();
				if(brDTO == null){
                    dtoParameter.setBarcodePrintNum(0);
                    req.setAttribute(WebAttrConstant.BARCODE_RECEIVE_DTO, dtoParameter);
				} else {
                    req.setAttribute(WebAttrConstant.BARCODE_RECEIVE_DTO, brDTO);
                }
				forwardURL = URLDefineList.BARCODE_PRINT_PAGE;
			} else if (action.equals(AssetsActionConstant.CONFIRM_ACTION)) {    //确认操作
                boolean exist = dao.getBarcodeIsExist();
				if(exist){
				    boolean existInPrint = dao.getBarcodeIsExistInPrint();
                    if(existInPrint){
                        dao.updateData();
                    } else {
                        dao.createData();
                    }
                    message = dao.getMessage();
                    message = getMessage(MsgKeyConstant.SUBMIT_DATA_SUCCESS);
                    message.setMessageValue("标签打印确认");
                    //message.addParameterValue("标签打印确认");	
				} else {
                    message = dao.getMessage();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.addParameterValue(dtoParameter.getFromBarcode() +"标签号");
                }
				forwardURL = "/servlet/com.sino.ams.print.servlet.BarcodePrintServlet?act=" + WebActionConstant.QUERY_ACTION;
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
		} catch (QueryException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.QUERY_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DataHandleException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (PoolException e) {
			e.printStackTrace();
		} catch (ContainerException e) {
			e.printStackTrace();
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			if (!forwardURL.equals("")) {
				forwarder.forwardView(forwardURL);
			} else {
				pw.flush();
				pw.close();
			}
		}
	}


}