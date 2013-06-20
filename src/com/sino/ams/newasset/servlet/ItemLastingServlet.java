package com.sino.ams.newasset.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.web.CheckBoxProp;
import com.sino.base.log.Logger;
import com.sino.base.web.EventHandler;
import com.sino.base.web.EventHandlers;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.ams.bean.OptionProducer;
import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.newasset.constant.*;
import com.sino.ams.newasset.dao.AmsItemCorrectLogDAO;
import com.sino.ams.newasset.dao.ItemLastingDAO;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.newasset.dto.AmsItemCorrectLogDTO;
import com.sino.ams.newasset.model.ItemLastingModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2009-6-4
 * Time: 15:29:09
 * To change this template use File | Settings | File Templates.
 */
public class ItemLastingServlet extends BaseServlet {

	/**
	 * 功能：租赁台帐维护
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws javax.servlet.ServletException
	 * @throws java.io.IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws
		ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		Connection conn = null;
        try {
			SfUserDTO user = (SfUserDTO) getUserAccount(req);
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(AmsAssetsAddressVDTO.class.getName());
			AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) req2DTO.getDTO(req);
			if(dto.getAttribute1().equals("")){
				dto.setAttribute1(AssetsDictConstant.STATUS_NO);
			}
			String action = dto.getAct();
			conn = getDBConnection(req);
			ItemLastingDAO costEasyDAO = new ItemLastingDAO(user, dto, conn);
			AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
            if (action.equals("")) {
                produceWebComponent(dto, req, optProducer, user);
				forwardURL = "/newasset/itemLastingData.jsp";
			} else if (action.equals(AssetsActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new ItemLastingModel(user, dto);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                CheckBoxProp checkProp = new CheckBoxProp("subCheck");
				checkProp.addDbField("BARCODE");
				EventHandlers handlers = new EventHandlers();
				EventHandler handler = new EventHandler();
				handler.setFunName("do_TransData");
				handler.setEventName("onClick");
				handlers.addHandler(handler);
				checkProp.setHandlers(handlers);
				pageDAO.setWebCheckProp(checkProp);
				pageDAO.setCalPattern(LINE_PATTERN);
                pageDAO.setPageSize(20);
				pageDAO.produceWebData();

                produceWebComponent(dto, req, optProducer, user);
				forwardURL = "/newasset/itemLastingData.jsp";
			} else if (action.equals(AssetsActionConstant.DETAIL_ACTION)) {
				costEasyDAO.setDTOClassName(AmsAssetsAddressVDTO.class.getName());
				dto = (AmsAssetsAddressVDTO) costEasyDAO.getDataByPrimaryKey();
				AmsItemCorrectLogDTO logDTO = new AmsItemCorrectLogDTO();
				logDTO.setBarcode(dto.getBarcode());
				AmsItemCorrectLogDAO logDAO= new AmsItemCorrectLogDAO(user, logDTO, conn);
				logDAO.setDTOClassName(AmsItemCorrectLogDTO.class.getName());
				DTOSet barcodeLogs = (DTOSet)logDAO.getDataByForeignKey("barcode");
				req.setAttribute(AssetsWebAttributes.BARCODE_LOGS, barcodeLogs);
				req.setAttribute(AssetsWebAttributes.ITEM_INFO_DTO, dto);
				forwardURL = AssetsURLList.ITEM_DETAIL_PAGE;
			} else if (action.equals(AssetsActionConstant.UPDATE_ACTION)) {
				RequestParser parser = new RequestParser();
				CheckBoxProp checkProp = new CheckBoxProp("subCheck");
				checkProp.setIgnoreOtherField(true);
				parser.setCheckBoxProp(checkProp);
				parser.transData(req);
				String[] barcodes = parser.getParameterValues("barcode");
				costEasyDAO.updateItems(barcodes);
				costEasyDAO.logItemChgHistory(barcodes);
				message = costEasyDAO.getMessage();
				forwardURL = "/servlet/com.sino.ams.newasset.servlet.ItemLastingServlet";
				forwardURL += "?act=" + AssetsActionConstant.QUERY_ACTION;
			} else if (action.equals(AssetsActionConstant.EXPORT_ACTION)) {
				File file = costEasyDAO.getExportFile();
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
			} else if(action.equals("GET_DEPT_OPTION")){
                String organizationId = req.getParameter("organizationId");
                String deptOptionX = optProducer.getResDeptOption(organizationId,"");
                res.setContentType("text/html;charset=GBK");
                PrintWriter out = res.getWriter();
                out.print(deptOptionX);
                out.flush();
                out.close();
            }
            else {
				message = getMessage(AssetsMessageKeys.INVALID_REQ);
				message.setIsError(true);
				forwardURL = MessageConstant.MSG_PRC_SERVLET;
			}
		} catch (Throwable ex) {
            Logger.logError(ex);
			message = getMessage(AssetsMessageKeys.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} finally {
			closeDBConnection(conn);
			setHandleMessage(req, message);
			if(!forwardURL.equals("")){
				ServletForwarder forwarder = new ServletForwarder(req, res);
				forwarder.forwardView(forwardURL);
			}
		}
	}

    private void produceWebComponent(AmsAssetsAddressVDTO dto,
                                     HttpServletRequest req,
                                     AssetsOptProducer optProducer,
                                     SfUserDTO user) throws QueryException {
        String opt = optProducer.getItemCategoryOption(dto.getItemCategory());
        dto.setItemCategoryOpt(opt);
        opt = optProducer.getMainCompanyOption(dto.getMaintainCompany());
        dto.setMaintainCompanyOpt(opt);
        String deptOpt = optProducer.getUserAsssetsDeptOption(dto.getResponsibilityDept());
        req.setAttribute(AssetsWebAttributes.DEPT_OPTIONS, deptOpt);
        String specialDepOpt = optProducer.getSpecialAsssetsDeptOption(dto.getSpecialityDept());
        req.setAttribute("DEPT_OPTIONS2", specialDepOpt);

        String itemStatus = optProducer.getDictOption(AssetsDictConstant.ITEM_STATUS, dto.getItemStatus());
        req.setAttribute(AssetsWebAttributes.ITEM_STATUS_OPTIONS, itemStatus);

        String cityOption = optProducer.getAllOrganization(user.getOrganizationId(), false);
        req.setAttribute(AssetsWebAttributes.CITY_OPTION, cityOption);

        req.setAttribute(QueryConstant.QUERY_DTO, dto);
    }
}
