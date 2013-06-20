package com.sino.ams.newasset.urgenttrans.servlet;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.newasset.assetsharing.constant.AssetSharingConstant;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.dao.ApproveContentDAO;
import com.sino.ams.newasset.urgenttrans.constant.UrgentURLListConstant;
import com.sino.ams.newasset.urgenttrans.dto.UrgentDTO;
import com.sino.ams.newasset.urgenttrans.dto.UrgentHeaderDTO;
import com.sino.ams.newasset.urgenttrans.dto.UrgentLineDTO;
import com.sino.ams.newasset.urgenttrans.service.UrgentService;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.framework.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;

/**
 * 
 * @系统名称: 紧急调拨单
 * @功能描述: 
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本：1.0
 * @开发作者: sj
 * @创建时间: Aug 1, 2011
 */
public class UrgentServlet extends BaseServlet {

	@Override
	public void performTask(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String forwardURL = "";
		String msg = "";
		String action = null;
		Connection conn = null;
		UrgentService service = null;
		UrgentDTO leaseDTO = null;
		SfUserDTO user = null;
		String selectedUser= "";
		try {
			user = (SfUserDTO) getUserAccount(req);
			// // 处理头参数
			leaseDTO = this.getDTOFromReq(req);
			conn = getDBConnection(req);
			OptionProducer  op = new OptionProducer(user, conn);
			String userOption=op.getUsersOfGroup(user.getCurrGroupId(), selectedUser, true);
			req.setAttribute(WebAttrConstant.USER_OPTION, userOption);
			action = leaseDTO.getAct();
			service = new UrgentService(user, leaseDTO, conn);
			if (action.equals(WebActionConstant.SAVE_ACTION)) {
                service.doSave();
            } else if (action.equals(WebActionConstant.SUBMIT_ACTION)) {
                service.doSubmit();
            } else if (action.equals(WebActionConstant.EDIT_ACTION)) {
				service.prodData();
				leaseDTO = service.getForm();
				req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, leaseDTO);
				forwardURL = UrgentURLListConstant.URGENT_EDIT_PAGE;
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
				service.prodData();
				leaseDTO = service.getForm();
				req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, leaseDTO);
				forwardURL = UrgentURLListConstant.URGENT_DETAIL_PAGE;
			} else if (action.equals(WebActionConstant.REJECT_ACTION)) {
                if(service.rejectProcedure()){
                    msg = "单据"+leaseDTO.getHeaderDTO().getTransNo()+"退回成功";
                } else {
                    msg = "单据"+leaseDTO.getHeaderDTO().getTransNo()+"退回失败";
                }
			} else if (action.equals(WebActionConstant.CANCEL_ACTION)) {
                service.doCancel();
			} else if(WebActionConstant.QUERY_ACTION.equals(action)||"".equals(action)){
				forwardURL = UrgentURLListConstant.URGENT_QUERY_PAGE;
				if(!"".equals(action)){
					service.pageQuery(req);
				}
				req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, leaseDTO);
			}else if(AssetSharingConstant.QUERY_DETAIL.equals(action)){
				service.prodArchiveData();
				leaseDTO = service.getForm();
				req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, leaseDTO);
				req.setAttribute(AssetsWebAttributes.APPROVE_CONTENT, ApproveContentDAO.getApproveContent(conn, leaseDTO.getHeaderDTO()
								.getTransId(),
								"AMS_ASSETS_TRANS_HEADER"));
				forwardURL = UrgentURLListConstant.URGENT_DETAIL_PAGE;
			} else if (action.equals(AssetsActionConstant.EXPORT_ACTION)) {
                File file = service.exportFile();
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
            }
			msg = service.getMsg();
		} catch (Throwable ex) {
            Logger.logError(ex);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} finally {
			closeDBConnection(conn);
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
	 * 
	 * @param req
	 * @return
	 * @throws DTOException
	 */
	public UrgentDTO getDTOFromReq(HttpServletRequest req) throws DTOException {
		UrgentDTO dto = new UrgentDTO();
		UrgentHeaderDTO headerDTO = null;
		DTOSet lines = null;

		Request2DTO req2DTO = new Request2DTO();
		req2DTO.setDTOClassName(UrgentDTO.class.getName());
		dto = (UrgentDTO) req2DTO.getDTO(req);

		// 处理头参数
		req2DTO.setDTOClassName(UrgentHeaderDTO.class.getName());
		headerDTO = (UrgentHeaderDTO) req2DTO.getDTO(req);
		dto.setHeaderDTO(headerDTO);

		// 处理行参数
		req2DTO.setDTOClassName(UrgentLineDTO.class.getName());
		req2DTO.setIgnoreFields(UrgentHeaderDTO.class);
		lines = req2DTO.getDTOSet(req);
		dto.setLines(lines);

		return dto;
	}

}
