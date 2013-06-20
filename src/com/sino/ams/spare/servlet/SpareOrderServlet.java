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
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
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
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.spare.dao.AmsItemTransHDAO;
import com.sino.ams.spare.dao.SpareOrderDAO;
import com.sino.ams.spare.dto.AmsItemTransHDTO;
import com.sino.ams.spare.dto.SpareOrderDTO;
import com.sino.ams.spare.model.AmsItemTransLModel;
import com.sino.ams.spare.model.SpareOrderModel;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-12-02
 * Time: 00:00:00
 * To change this template use File | Settings | File Templates.
 */
public class SpareOrderServlet extends BaseServlet {
	 public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		String act = req.getParameter("act");
		act = StrUtil.nullToString(act);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
			SpareOrderDTO dtoParameter = null;
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(SpareOrderDTO.class.getName());
			dtoParameter = (SpareOrderDTO) req2DTO.getDTO(req);
			conn = getDBConnection(req);
			SpareOrderDAO situsDAO = new SpareOrderDAO(user, dtoParameter, conn);
			OptionProducer prd = new OptionProducer(user, conn);
            String vendorOption = prd.getSpareVendorOption(dtoParameter.getVendorId());
            req.setAttribute(WebAttrConstant.SPARE_VENDOR_OPTION, vendorOption);
			String ouOption = prd.getAllOrganization(dtoParameter.getOrganizationId(), true);
			req.setAttribute(WebAttrConstant.OU_OPTION, ouOption);
			OptionProducer op = new OptionProducer(user, conn);
			String transStaSelect = op.getDictOption("ORDER_STATUS", dtoParameter.getTransStatus());
			req.setAttribute(WebAttrConstant.TRANS_STATUS, transStaSelect);
			String transTypeSelect = op.getDictOption("ORDER_TYPE_SPARE", dtoParameter.getTransType());
			req.setAttribute(WebAttrConstant.TRANS_TYPE, transTypeSelect);
			  RowSet rowSet= new RowSet();
			if (act.equals("")) {
				req.setAttribute(WebAttrConstant.AMS_SPARE_DTO, dtoParameter);
				forwardURL = "/spare/spareOrderQuery.jsp";
			} else if (act.equals(WebActionConstant.QUERY_ACTION)) {                             //查询出所有
				BaseSQLProducer sqlProducer = new SpareOrderModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.produceWebData();
				req.setAttribute(WebAttrConstant.AMS_SPARE_DTO, dtoParameter);
				forwardURL = "/spare/spareOrderQuery.jsp";
			} else if (act.equals(WebActionConstant.EXPORT_ACTION)) {      //导出
				File file = situsDAO.exportFile();
				situsDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
			} else if (act.equals(WebActionConstant.DETAIL_ACTION)) {    //盘点单明细
				AmsItemTransHDTO dtoParameter2 = null;
				dtoParameter2.setTransId(dtoParameter.getTransId());
				AmsItemTransHDAO itemTransHDAO = new AmsItemTransHDAO(user, dtoParameter2, conn);
				itemTransHDAO.setDTOClassName(AmsItemTransHDTO.class.getName());
				AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) itemTransHDAO.getDataByPrimaryKey();
				if (amsItemTransH == null) {
					amsItemTransH = new AmsItemTransHDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
				String orderReason = op.getDictOption("SPARE_REASON",amsItemTransH.getSpareReason());
				req.setAttribute("ORDER_REASON", orderReason);
				req.setAttribute("AIT_HEADER", amsItemTransH);
				SQLModel sqlModel = null;
				AmsItemTransLModel amsItemTransLModel = new AmsItemTransLModel(user,null);
				sqlModel= amsItemTransLModel.getDiffModel(amsItemTransH.getTransId());
				SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
				simpleQuery.executeQuery();
				if(simpleQuery.hasResult()){
					rowSet = simpleQuery.getSearchResult();
				}
				req.setAttribute("AIT_LINES",rowSet);
				forwardURL = "/spare/spareAttemperDetail.jsp";     //盘点单明细
			 } else if (act.equals("print")) {    //盘点单明细打印
				AmsItemTransHDTO dtoParameter2 = null;
				dtoParameter2.setTransId(dtoParameter.getTransId());
				AmsItemTransHDAO itemTransHDAO = new AmsItemTransHDAO(user, dtoParameter2, conn);
				itemTransHDAO.setDTOClassName(AmsItemTransHDTO.class.getName());
				AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) itemTransHDAO.getDataByPrimaryKey();
				if (amsItemTransH == null) {
					amsItemTransH = new AmsItemTransHDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
				String orderReason = op.getDictOption("SPARE_REASON",amsItemTransH.getSpareReason());
				req.setAttribute("ORDER_REASON", orderReason);
				req.setAttribute("AIT_HEADER", amsItemTransH);
				//查询行信息
				SQLModel sqlModel = null;
				AmsItemTransLModel amsItemTransLModel = new AmsItemTransLModel(user,null);
				sqlModel= amsItemTransLModel.getDiffModel(amsItemTransH.getTransId());
				SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
				simpleQuery.executeQuery();
				if(simpleQuery.hasResult()){
					rowSet = simpleQuery.getSearchResult();
				}
				req.setAttribute("AIT_LINES",rowSet);
				forwardURL = "/spare/print/sparePDPrint.jsp";     //盘点单明细打印
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
