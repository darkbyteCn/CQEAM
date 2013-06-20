package com.sino.ams.newasset.lose.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.lease.constant.LeaseURLListConstant;
import com.sino.ams.newasset.lose.constant.LoseURLListConstant;
import com.sino.ams.newasset.lose.dto.LoseDTO;
import com.sino.ams.newasset.lose.dto.LoseHeaderDTO;
import com.sino.ams.newasset.lose.dto.LoseLineDTO;
import com.sino.ams.newasset.lose.service.LoseService;
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
 * @系统名称: 挂失
 * @功能描述: 
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本：1.0
 * @开发作者: sj
 * @创建时间: Jul 14, 2011
 */
public class LoseServlet extends AssetsTransFlowBaseServlet {

	@Override
	public void performTask(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String forwardURL = "";
		String msg = "";
		boolean isClose = false;
		String action = null;
		Connection conn = null;
		LoseService service = null;
		boolean isSuccess = false;
		LoseDTO loseDTO = null;
		SfUserDTO user = null;
		Message message= SessionUtil.getMessage(req);
		try {
			user =  (SfUserDTO) getUserAccount(req);
			// // 处理头参数
			loseDTO = this.getDTOFromReq(req);
			conn = getDBConnection(req);
			action = loseDTO.getAct();
			service = new LoseService( user , loseDTO , conn );
			if( action.equals( WebActionConstant.SAVE_ACTION ) ){
				isSuccess = service.doSave();
				service.prodData();
				loseDTO = service.getForm();
                req.setAttribute( AssetsWebAttributes.ORDER_HEAD_DATA , loseDTO ); 
//                forwardURL = LoseURLListConstant.LOSE_SERVELT + "?act=EDIT_ACTION";
				forwardURL = LoseURLListConstant.LOSE_EDIT_PAGE ;
			}else if( action.equals( WebActionConstant.SUBMIT_ACTION ) ){
				isSuccess = service.doSubmit();
				loseDTO = service.getForm();
                req.setAttribute( AssetsWebAttributes.ORDER_HEAD_DATA , loseDTO ); 
                //意见展现
                super.doApproveContent(req, conn, loseDTO.getHeaderDTO().getTransId() );
				
				forwardURL = LoseURLListConstant.LOSE_DETAIL_PAGE ;
			}else if( action.equals( WebActionConstant.EDIT_ACTION ) ){
				service.prodData();
				loseDTO = service.getForm();
//				loseDTO = setOption(conn, req, userInfo, applyDTO);
                req.setAttribute( AssetsWebAttributes.ORDER_HEAD_DATA , loseDTO );
                forwardURL = LoseURLListConstant.LOSE_EDIT_PAGE ;
			}else if( action.equals( WebActionConstant.DETAIL_ACTION ) ){
				service.prodData();
				loseDTO = service.getForm();
//				loseDTO = setOption(conn, req, userInfo, applyDTO);
                req.setAttribute( AssetsWebAttributes.ORDER_HEAD_DATA , loseDTO );
                forwardURL = LoseURLListConstant.LOSE_DETAIL_PAGE ;
			} else if( action.equals( WebActionConstant.CANCEL_ACTION ) ){
				isSuccess = service.doCancel();
				loseDTO = service.getForm();
//				leaseDTO = setOption(conn, req, userInfo, applyDTO);
                req.setAttribute( AssetsWebAttributes.ORDER_HEAD_DATA , loseDTO );
                //意见展现
                super.doApproveContent(req, conn, loseDTO.getHeaderDTO().getTransId() );
                forwardURL = LoseURLListConstant.LOSE_DETAIL_PAGE ;
			} else if (action.equals(AssetsActionConstant.REJECT_ACTION)) { //退回
				isSuccess = service.doReturn();
				loseDTO = service.getForm();
				req.setAttribute( AssetsWebAttributes.ORDER_HEAD_DATA , loseDTO );
                //意见展现
                super.doApproveContent(req, conn, loseDTO.getHeaderDTO().getTransId() );
                forwardURL = LoseURLListConstant.LOSE_DETAIL_PAGE ;
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
	public LoseDTO getDTOFromReq( HttpServletRequest req ) throws DTOException{
		LoseDTO dto = new LoseDTO();
		LoseHeaderDTO headerDTO = null;
		DTOSet lines = null;
		
    	Request2DTO req2DTO = new Request2DTO();  
		req2DTO.setDTOClassName(LoseDTO.class.getName());
		dto = (LoseDTO) req2DTO.getDTO(req);

    	// 处理头参数
        req2DTO.setDTOClassName(LoseHeaderDTO.class.getName()); 
        headerDTO = (LoseHeaderDTO) req2DTO.getDTO(req);
        dto.setHeaderDTO(headerDTO);
         
        // 处理行参数
		req2DTO.setDTOClassName( LoseLineDTO.class.getName());
        req2DTO.setIgnoreFields(LoseHeaderDTO.class);
        lines = req2DTO.getDTOSet(req);  
        dto.setLines(lines);
        
		return dto; 
    }
	
	
	
}
