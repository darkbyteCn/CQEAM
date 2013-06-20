package com.sino.soa.mis.srv.ouorganization.servlet;

import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.soa.common.*;
import com.sino.soa.mis.srv.ouorganization.dao.SBFIGLOuOrganizationDAO;
import com.sino.soa.mis.srv.ouorganization.dto.SBFIGLOuOrganizationDTO;
import com.sino.soa.mis.srv.ouorganization.srv.SBFIGLInquiryOuOrganizationSrv;
import com.sino.soa.util.SynUpdateDateUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-9-8
 * Time: 16:05:13
 * To change this template use File | Settings | File Templates.
 */
public class SBFIGLOuOrganizationServlet extends BaseServlet {

	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		MessagePrint message=new MessagePrint();
		Connection conn = null;
		int count=0;
		int falsecount=0;
		try {
			SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(SBFIGLOuOrganizationDTO.class.getName());
			SBFIGLOuOrganizationDTO dtoParameter = (SBFIGLOuOrganizationDTO)req2DTO.getDTO(req);
			String action = dtoParameter.getAct();
			conn = getDBConnection(req);
			SBFIGLOuOrganizationDAO srvOuOrganizationDAO = new SBFIGLOuOrganizationDAO(user, dtoParameter, conn);
			if (action.equals("")) {
				long start=System.currentTimeMillis();
				SBFIGLInquiryOuOrganizationSrv server=new SBFIGLInquiryOuOrganizationSrv();
				server.excute();
				SrvReturnMessage srm=server.getReturnMessage();
				if(srm.getErrorFlag().equals("Y")){
					SynUpdateDateUtils.createLastUpdateDate(SrvType.SRV_OU, conn);
					DTOSet ds=server.getDs();
					count=srvOuOrganizationDAO.isSavaOuInfor(ds);
                    falsecount = srvOuOrganizationDAO.getErrorCount();
					if(count>0){
						message.setMessageValue(SrvWebActionConstant.SYNSUCCESS);
						message.setSuccess(true);
					}
					else {
						message.setMessageValue(SrvWebActionConstant.SYNFALSE);
						message.setSuccess(false);
					}
					SynUpdateDateUtils.updateLastUpdateDate(SrvType.SRV_OU, conn);
				}else {
					message.setMessageValue(SrvWebActionConstant.SYNWEBSERVICE+srm.getErrorMessage());
					message.setSuccess(false);
				}
				long end=System.currentTimeMillis();
				long resume=end-start;
				if(message.isSuccess())
					message.setMessageValue(message.getMessageValue()+resume+"毫秒"+","+"成功记录数"+count+"条;"+"失败记录数"+falsecount+"条。");
				forwardURL= SrvURLDefineList.MESSAGE_PRINT_PUB;
			}
		} catch (PoolPassivateException ex) {
			ex.printLog();
			message.setMessageValue("同步失败");
			message.setSuccess(false);
			forwardURL = SrvURLDefineList.MESSAGE_PRINT_PUB;
		} catch (DTOException ex) {
			ex.printLog();
			message.setMessageValue("同步失败");
			message.setSuccess(false);
			forwardURL = SrvURLDefineList.MESSAGE_PRINT_PUB;
		} catch (QueryException ex) {
			ex.printLog();
			message.setMessageValue("同步失败");
			message.setSuccess(false);
			forwardURL = SrvURLDefineList.MESSAGE_PRINT_PUB;
		} catch (DataHandleException ex) {
			ex.printLog();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.closeDBConnection(conn);
			req.setAttribute("MESSAGEPRINT", message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
		}
	}
}