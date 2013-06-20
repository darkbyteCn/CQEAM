package com.sino.ams.spare.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;

import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.spare.dao.AmsItemTransLDAO;
import com.sino.ams.spare.dao.BjfxsqDAO;
import com.sino.ams.spare.dao.SpareDiffDAO;
import com.sino.ams.spare.dto.AmsItemTransHDTO;
import com.sino.ams.spare.dto.AmsItemTransLDTO;
import com.sino.ams.spare.model.SpareDiffModel;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * User: wangzp    
 * Date: 2011-12-08
 * Functon; 申请于接收差异报表。
 */
public class SpareDiffServlet extends BaseServlet {
		public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		String act = req.getParameter("act");
		act = StrUtil.nullToString(act);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
			AmsItemTransLDTO dtoParameter = null;
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(AmsItemTransLDTO.class.getName());
			dtoParameter = (AmsItemTransLDTO) req2DTO.getDTO(req);
			conn = getDBConnection(req);
			SpareDiffDAO situsDAO = new SpareDiffDAO(user, dtoParameter, conn);
			OptionProducer prd = new OptionProducer(user, conn);
			String ouOption = prd.getAllOrganization(dtoParameter.getOrganizationId(), true);
			req.setAttribute(WebAttrConstant.OU_OPTION, ouOption);
			String invOption = prd.getDictOption(DictConstant.INV_TYPE,dtoParameter.getObjectCategory());
			req.setAttribute(WebAttrConstant.INV_OPTION,invOption);
            String vendorOption = prd.getSpareVendorOption(dtoParameter.getVendorId());
            req.setAttribute(WebAttrConstant.SPARE_VENDOR_OPTION, vendorOption);
            if (act.equals("")) {
				req.setAttribute(WebAttrConstant.AMS_SPARE_DTO, dtoParameter);
				forwardURL = "/spare/spareDiffQuery.jsp";
			} else if (act.equals(WebActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new SpareDiffModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.produceWebData();
				req.setAttribute(WebAttrConstant.AMS_SPARE_DTO, dtoParameter);
				forwardURL = "/spare/spareDiffQuery.jsp";
			} else if (act.equals(WebActionConstant.EXPORT_ACTION)) {      //导出
				File file = situsDAO.exportFile();
				situsDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
			}else if (act.equals(WebActionConstant.DETAIL_ACTION)) {   //返修申请单明细

				 AmsItemTransHDTO dtoParameter3 = new AmsItemTransHDTO();
				dtoParameter3.setTransId(req.getParameter("transId"));
				BjfxsqDAO itemTransHDAO = new BjfxsqDAO(user, dtoParameter3, conn);
				itemTransHDAO.setDTOClassName(AmsItemTransHDTO.class.getName());
				AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) itemTransHDAO.getDataByPrimaryKey();
				if (amsItemTransH == null) {
					amsItemTransH = new AmsItemTransHDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
				req.setAttribute("AIT_HEADER", amsItemTransH);
				//查询行信息
				AmsItemTransLDAO ldao = new AmsItemTransLDAO(user, null, conn);
				req.setAttribute("AIT_LINES", ldao.getLines2(amsItemTransH.getTransId()));
				forwardURL = "/spare/bjfxsqOrderInfo.jsp";
			}else if (act.equals("print")) {   //返修申请单明细 打印
				AmsItemTransHDTO dtoParameter3 = new AmsItemTransHDTO();
				dtoParameter3.setTransId(req.getParameter("transId"));
				BjfxsqDAO itemTransHDAO = new BjfxsqDAO(user, dtoParameter3, conn);
				itemTransHDAO.setDTOClassName(AmsItemTransHDTO.class.getName());
				AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) itemTransHDAO.getDataByPrimaryKey();
				if (amsItemTransH == null) {
					amsItemTransH = new AmsItemTransHDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
				req.setAttribute("AIT_HEADER", amsItemTransH);
				//查询行信息
				AmsItemTransLDAO ldao = new AmsItemTransLDAO(user, null, conn);
//				req.setAttribute("AIT_LINES", ldao.getPrintLine(amsItemTransH.getTransId()));(原来的)
				req.setAttribute("AIT_LINES", ldao.getLines2(amsItemTransH.getTransId()));
//				req.setAttribute("ALLOT_LINES", ldao.getPrintAllot(amsItemTransH.getTransId()));
				forwardURL = "/spare/print/spareFXSQPrint.jsp";
			}

		} catch (PoolException e) {
			e.printStackTrace();
			Logger.logError(e.toString());
		} catch (DTOException e) {
			e.printStackTrace();
			Logger.logError(e.toString());
		} catch (QueryException e) {
			e.printStackTrace();
		} catch (DataTransException e) {
			e.printStackTrace();
		} catch (WebFileDownException e) {
			e.printStackTrace();
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
		}
	}
}
