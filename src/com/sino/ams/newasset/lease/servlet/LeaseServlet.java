package com.sino.ams.newasset.lease.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.lease.constant.LeaseURLListConstant;
import com.sino.ams.newasset.lease.dto.LeaseDTO;
import com.sino.ams.newasset.lease.dto.LeaseHeaderDTO;
import com.sino.ams.newasset.lease.dto.LeaseLineDTO;
import com.sino.ams.newasset.lease.service.LeaseService;
import com.sino.ams.newasset.servlet.AssetsTransFlowBaseServlet;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;

/**
 * 
 * @系统名称: 续租
 * @功能描述: 
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本：1.0
 * @开发作者: sj
 * @创建时间: Jul 14, 2011
 */
public class LeaseServlet extends AssetsTransFlowBaseServlet {

	@Override
	public void performTask(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String forwardURL = "";
		String msg = "";
		String action = null;
		Connection conn = null;
		LeaseService service = null;
		boolean isSuccess = false;
		LeaseDTO leaseDTO = null;
		SfUserDTO user = null;
		Message message= SessionUtil.getMessage(req);
		try {
			user =  (SfUserDTO) getUserAccount(req);
			// // 处理头参数
			leaseDTO = this.getDTOFromReq(req);
			conn = getDBConnection(req);
			action = leaseDTO.getAct();
			service = new LeaseService( user , leaseDTO , conn );
			if( action.equals( WebActionConstant.SAVE_ACTION ) ){
				isSuccess = service.doSave();
				service.prodData();
				leaseDTO = service.getForm();
                req.setAttribute( AssetsWebAttributes.ORDER_HEAD_DATA , leaseDTO ); 
//                forwardURL = LeaseURLListConstant.LEASE_SERVELT + "?act=EDIT_ACTION";
				forwardURL = LeaseURLListConstant.LEASE_EDIT_PAGE ;
			}else if( action.equals( WebActionConstant.SUBMIT_ACTION ) ){
				isSuccess = service.doSubmit();
				leaseDTO = service.getForm();
                req.setAttribute( AssetsWebAttributes.ORDER_HEAD_DATA , leaseDTO ); 
                
                //意见展现
                super.doApproveContent(req, conn, leaseDTO.getHeaderDTO().getTransId() );
                
				forwardURL = LeaseURLListConstant.LEASE_DETAIL_PAGE ;
			}else if( action.equals( WebActionConstant.EDIT_ACTION ) ){
				service.prodData();
				leaseDTO = service.getForm();
//				leaseDTO = setOption(conn, req, userInfo, applyDTO);
                req.setAttribute( AssetsWebAttributes.ORDER_HEAD_DATA , leaseDTO );
                forwardURL = LeaseURLListConstant.LEASE_EDIT_PAGE ;
			}else if( action.equals( WebActionConstant.DETAIL_ACTION ) ){
				service.prodData();
				leaseDTO = service.getForm();
//				leaseDTO = setOption(conn, req, userInfo, applyDTO);
                req.setAttribute( AssetsWebAttributes.ORDER_HEAD_DATA , leaseDTO );
                forwardURL = LeaseURLListConstant.LEASE_DETAIL_PAGE ;
			}else if( action.equals( WebActionConstant.CANCEL_ACTION ) ){
				isSuccess = service.doCancel();
				leaseDTO = service.getForm();
//				leaseDTO = setOption(conn, req, userInfo, applyDTO);
                req.setAttribute( AssetsWebAttributes.ORDER_HEAD_DATA , leaseDTO );
                //意见展现
                super.doApproveContent(req, conn, leaseDTO.getHeaderDTO().getTransId() );
                forwardURL = LeaseURLListConstant.LEASE_DETAIL_PAGE ;
			} else if (action.equals(AssetsActionConstant.REJECT_ACTION)) { //退回
				isSuccess = service.doReturn();
				leaseDTO = service.getForm();
				req.setAttribute( AssetsWebAttributes.ORDER_HEAD_DATA , leaseDTO );
                //意见展现
                super.doApproveContent(req, conn, leaseDTO.getHeaderDTO().getTransId() );
                forwardURL = LeaseURLListConstant.LEASE_DETAIL_PAGE ;
            }
			
			msg = service.getMsg();  
			if( ! StrUtil.isEmpty( msg )){
				message=new Message(); 
	            message.setMessageValue( msg );
	            req.setAttribute(MessageConstant.MESSAGE_DATA, message );
//				message.setIsError( !isSuccess ); 
			}
			
		} catch (PoolPassivateException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DTOException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (QueryException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} finally {
//			DBManager.closeDBConnection( conn );
			closeDBConnection(conn);
//			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);

			if (!StrUtil.isEmpty(forwardURL)) {
				forwarder.forwardView(forwardURL);
			} else {
				forwarder.forwardOpenerView(forwardURL, msg);
			}
		}
	}
	
	/**
	 * 取参数
	 * @param req
	 * @return
	 * @throws DTOException
	 */
	public LeaseDTO getDTOFromReq( HttpServletRequest req ) throws DTOException{
		LeaseDTO dto = new LeaseDTO();
		LeaseHeaderDTO headerDTO = null;
		DTOSet lines = null;
		
    	Request2DTO req2DTO = new Request2DTO();  
		req2DTO.setDTOClassName(LeaseDTO.class.getName());
		dto = (LeaseDTO) req2DTO.getDTO(req);

    	// 处理头参数
        req2DTO.setDTOClassName(LeaseHeaderDTO.class.getName()); 
        headerDTO = (LeaseHeaderDTO) req2DTO.getDTO(req);
        dto.setHeaderDTO(headerDTO);
         
        // 处理行参数
		req2DTO.setDTOClassName( LeaseLineDTO.class.getName());
        req2DTO.setIgnoreFields(LeaseHeaderDTO.class);
        lines = req2DTO.getDTOSet(req);  
        dto.setLines(lines);
        
		return dto; 
    }

}
