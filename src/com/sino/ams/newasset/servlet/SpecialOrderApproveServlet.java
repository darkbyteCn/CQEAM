package com.sino.ams.newasset.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.constant.AssetsURLList;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.dao.AmsAssetsTransLineDAO;
import com.sino.ams.newasset.dao.AmsSpecialAssetsDAO;
import com.sino.ams.newasset.dao.SpecialOrderApproveDAO;
import com.sino.ams.newasset.dto.AmsAssetsTransLineDTO;
import com.sino.ams.newasset.dto.AmsSpecialAssetsDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.UploadException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.flow.bean.FlowAction;
import com.sino.flow.dto.FlowDTO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.servlet.BaseServlet;


/**
 * <p>Title: SpecialOrderApproveServlet</p>
 * <p>Description:程序自动生成服务程序“OrderApproveServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class SpecialOrderApproveServlet extends BaseServlet {

	/**
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws
			ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO) getUserAccount(req);
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(AmsSpecialAssetsDTO.class.getName());
			AmsSpecialAssetsDTO dto = (AmsSpecialAssetsDTO) req2DTO.getDTO(req);
			ServletConfigDTO servletConfig = getServletConfig(req);
			dto.setServletConfig(servletConfig);
			FlowDTO flowDTO = FlowAction.getDTOFromReq(req);
			String action = dto.getAct();
			conn = getDBConnection(req);
            //陕西报废资产处理,获取选中的报废资产，并初始化
            RequestParser par = new RequestParser();
            par.transData(req);
            String[] barcodess = par.getParameterValues("subCheck");
            dto.setBarcodess(barcodess);

            SpecialOrderApproveDAO approveDAO = new SpecialOrderApproveDAO(user, dto, conn);
			approveDAO.setServletConfig(servletConfig);
			
			if (action.equals(AssetsActionConstant.EDIT_ACTION)) { //进入审批页面
				
				approveDAO.setDTOClassName(AmsSpecialAssetsDTO.class.getName());
				AmsSpecialAssetsDTO headerDTO = (AmsSpecialAssetsDTO)approveDAO.getDataByPrimaryKey();
                String accessSheet = approveDAO.getAccessSheet();//附件张数
                headerDTO.setAccessSheet(accessSheet);
                if (headerDTO == null) {
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
					forwardURL = MessageConstant.MSG_PRC_SERVLET;
				} else {
					AmsSpecialAssetsDAO headerDAO = new AmsSpecialAssetsDAO(user, dto, conn);
					boolean isGroupPID = headerDAO.isGroupFlowId();
		            req.setAttribute(AssetsWebAttributes.IS_GROUP_PID, isGroupPID+"");
					headerDTO.setAttribute1(dto.getAttribute1());
					headerDTO.setAttribute2(dto.getAttribute2());
					headerDTO.setAttribute3(dto.getAttribute3());
					headerDTO.setAttribute4(dto.getAttribute4());
					headerDTO.setAttribute5(dto.getAttribute5());
					headerDTO.setSectionRight(dto.getSectionRight());
					headerDTO.setHiddenRight(dto.getHiddenRight());

					headerDTO.setServletConfig(servletConfig);
					AmsAssetsTransLineDTO lineDTO = new AmsAssetsTransLineDTO();
					lineDTO.setTransId(headerDTO.getTransId());
					lineDTO.setTransType(headerDTO.getTransType());
					lineDTO.setAccessType(headerDTO.getAssetsType());
					AmsAssetsTransLineDAO lineDAO = new AmsAssetsTransLineDAO(user, lineDTO, conn);
					lineDAO.setDTOClassName(AmsAssetsTransLineDTO.class.getName());
					DTOSet ds = (DTOSet) lineDAO.getDataByForeignKey("transId");
					req.setAttribute(AssetsWebAttributes.ORDER_LINE_DATA, ds);
					headerDTO.setCalPattern(LINE_PATTERN);
					req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, headerDTO);
					forwardURL = AssetsURLList.SPECIAL_APPROVE_EDIT_PAGE;
				}
			} else if (action.equals(AssetsActionConstant.APPROVE_ACTION)) { //审批流程，增加更改这就费用账户功能(2008-12-01 17:34)
				DTOSet orderLines = new DTOSet();
				if(dto.getAttribute4().equals(AssetsDictConstant.EDIT_ACCOUNT)){//该节点是折旧费用账户可编辑节点
					req2DTO.setDTOClassName(AmsAssetsTransLineDTO.class.getName());
					req2DTO.setIgnoreFields(AmsSpecialAssetsDTO.class);
					orderLines = req2DTO.getDTOSet(req);
				}
				if(orderLines != null){
					int lineCount = orderLines.getSize();
					for(int i = 0; i < lineCount; i++){
						AmsAssetsTransLineDTO lineDTO = (AmsAssetsTransLineDTO)orderLines.getDTO(i);
						lineDTO.setTransId(dto.getTransId());
						orderLines.set(i, lineDTO);
					}
				}
                //陕西报废资产处理
                RequestParser parser = new RequestParser();
                parser.transData(req);
                String[] barcodes = parser.getParameterValues("subCheck");
                boolean operateResult = approveDAO.approveOrder(flowDTO, orderLines, barcodes);
				dto = (AmsSpecialAssetsDTO) approveDAO.getDTOParameter();
				message = approveDAO.getMessage();
				if (operateResult) {
					forwardURL = AssetsURLList.SPECIAL_ORDER_APPROVE_SERVLET;
					forwardURL += "?act=" + AssetsActionConstant.EDIT_ACTION;
				}
				forwardURL += "&transType=" + dto.getTransType();
				forwardURL += "&transId=" + dto.getTransId();
			} else if (action.equals(AssetsActionConstant.DETAIL_ACTION)) { //进入审批页面
				approveDAO.setDTOClassName(AmsSpecialAssetsDTO.class.getName());
				approveDAO.setCalPattern(LINE_PATTERN);
				AmsSpecialAssetsDTO headerDTO = (AmsSpecialAssetsDTO)approveDAO.getDataByPrimaryKey();
                String accessSheet = approveDAO.getAccessSheet();//附件张数
                headerDTO.setAccessSheet(accessSheet);
                if (headerDTO == null) {
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
					forwardURL = MessageConstant.MSG_PRC_SERVLET;
				} else {
					headerDTO.setServletConfig(servletConfig);
					AmsAssetsTransLineDTO lineDTO = new AmsAssetsTransLineDTO();
					lineDTO.setTransId(headerDTO.getTransId());
					lineDTO.setTransType(headerDTO.getTransType());
					AmsAssetsTransLineDAO lineDAO = new AmsAssetsTransLineDAO(user, lineDTO, conn);
					lineDAO.setDTOClassName(AmsAssetsTransLineDTO.class.getName());
					DTOSet ds = (DTOSet) lineDAO.getDataByForeignKey("transId");
					req.setAttribute(AssetsWebAttributes.ORDER_LINE_DATA, ds);
					headerDTO.setCalPattern(LINE_PATTERN);
					req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA,headerDTO);
					forwardURL = AssetsURLList.SPECIAL_APPROVE_DETL_PAGE;
				}
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
		} catch (UploadException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} finally {
			closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
		}
	}

	private AmsSpecialAssetsDTO fillOptions(AmsSpecialAssetsDTO dto, SfUserDTO user, Connection conn) throws QueryException {
		AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
		String deptOptions = optProducer.getUserAsssetsDeptOption(StrUtil.nullToString(dto.
				getFromDept()));
		dto.setFromDeptOption(deptOptions);
		String opt = optProducer.getAllOrganization(dto.getToOrganizationId());
		dto.setToCompanyOption(opt);
		int fromOrgId = dto.getFromOrganizationId();
		opt = optProducer.getAllOrganization(fromOrgId);
		dto.setFromCompanyOption(opt);
		opt = optProducer.getBookTypeOption2(StrUtil.nullToString(fromOrgId));
		dto.setBookTypeOption(opt);
		String transOption = optProducer.getFAContentOption(dto.getFaContentCode());
		dto.setFaContentOption(transOption);
		return dto;
	}


}
