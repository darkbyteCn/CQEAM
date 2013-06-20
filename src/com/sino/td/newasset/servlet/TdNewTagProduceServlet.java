package com.sino.td.newasset.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONUtil;

import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.dto.AmsMisTagChgDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.td.newasset.dao.TdNewTagProduceDAO;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class TdNewTagProduceServlet extends BaseServlet {

	/**
	 * 所有的Servlet都必须实现的方法。
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		boolean hasError = true;
		Message message = SessionUtil.getMessage(req);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO) getUserAccount(req);
			conn = getDBConnection(req);
			TdNewTagProduceDAO newTagProducer = new TdNewTagProduceDAO(user, null, conn);
			DTOSet dtos = getBarcodes(req);
			newTagProducer.produceNewTagNumber(dtos);
			res.setContentType("text/html;charset=GBK");
			PrintWriter out = res.getWriter();
			AmsMisTagChgDTO dto = null;
			StringBuffer responseContent = new StringBuffer();
			for(int i = 0; i < dtos.getSize(); i++){
				dto = (AmsMisTagChgDTO)dtos.getDTO(i);
				responseContent.append(dto.getTagNumberFrom() + ";" + dto.getTagNumberTo());
				if(i < dtos.getSize() - 1){
					responseContent.append("&&&");
				}
			}
			out.println(responseContent.toString());
			hasError = false;
		} catch (PoolPassivateException ex) {
			ex.printLog();
		} catch (DataHandleException ex) {
			ex.printLog();
		} catch (JSONException ex) {
			Logger.logError(ex);
		} catch (DTOException ex) {
			ex.printLog();
		} finally {
			closeDBConnection(conn);
			if (hasError) {
				message = getMessage(AssetsMessageKeys.COMMON_ERROR);
				message.setIsError(true);
				setHandleMessage(req, message);
				ServletForwarder forwarder = new ServletForwarder(req, res);
				forwarder.forwardView(MessageConstant.MSG_PRC_SERVLET);
			}
		}
	}

	private DTOSet getBarcodes(HttpServletRequest req) throws JSONException, DTOException {
		DTOSet dtos = new DTOSet();
		String jsonString = JSONUtil.readJSONString(req);
		String transId = req.getParameter("transId");
		String refNumber = req.getParameter("refNumber");
		String fromOrganizationId = req.getParameter("fromOrganizationId");
		String toOrganizationId = req.getParameter("toOrganizationId");
		JSONArray barcodeArr = new JSONArray(jsonString);
		int barcodeCount = barcodeArr.length();
		for(int i = 0; i < barcodeCount; i++){
			AmsMisTagChgDTO dto = new AmsMisTagChgDTO();
			dto.setTagNumberFrom(barcodeArr.getString(i));
			dto.setTransId(transId);
			dto.setRefNumber(refNumber);
			dto.setFromOrganizationId(StrUtil.strToInt(fromOrganizationId));
			dto.setToOrganizationId(StrUtil.strToInt(toOrganizationId));
			dtos.addDTO(dto);
		}
		return dtos;
	}
}

