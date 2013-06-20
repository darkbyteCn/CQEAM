package com.sino.ams.spare.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.calen.CalendarConstant;
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
import com.sino.ams.spare.dao.SpareOnHandDAO;
import com.sino.ams.spare.dto.AmsItemTransLDTO;
import com.sino.ams.spare.model.SpareOnHandModel;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * User: wangzp
 * Date: 2011-12-08
 * Functon; ±¸¼þ¿â´æÁ¿
 */
public class SpareOnHandServlet extends BaseServlet {
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
			SpareOnHandDAO situsDAO = new SpareOnHandDAO(user, dtoParameter, conn);
			OptionProducer prd = new OptionProducer(user, conn);
			String ouOption = prd.getAllOrganization(dtoParameter.getOrganizationId(), true);
			req.setAttribute(WebAttrConstant.OU_OPTION, ouOption);
			String invOption = prd.getDictOption(DictConstant.INV_TYPE,dtoParameter.getObjectCategory());
			req.setAttribute(WebAttrConstant.INV_OPTION,invOption);
            String vendorOption = prd.getSpareVendorOption(dtoParameter.getVendorId());
            req.setAttribute(WebAttrConstant.SPARE_VENDOR_OPTION, vendorOption);
            if (act.equals("")) {
				req.setAttribute(WebAttrConstant.AMS_SPARE_DTO, dtoParameter);
				forwardURL = "/spare/spareOnNetQuery.jsp";
			} else if (act.equals(WebActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new SpareOnHandModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.produceWebData();
                Map map = situsDAO.getCount();
                String count = map.get("TATOL_COUNT1").toString();
                String count1 = map.get("TATOL_COUNT2").toString();
                req.setAttribute("TATOL_COUNT1", count);
                req.setAttribute("TATOL_COUNT2", count1);
                req.setAttribute(WebAttrConstant.AMS_SPARE_DTO, dtoParameter);
				forwardURL = "/spare/spareOnNetQuery.jsp";
			} else if (act.equals(WebActionConstant.EXPORT_ACTION)) {    //EXCEL 
                String vendorId = dtoParameter.getVendorId();
                String vendorName = "";
                if (!vendorId.equals("")) {
                    vendorName = situsDAO.getVendorName(vendorId);
                }
                dtoParameter.setVendorName(vendorName);
                File file = situsDAO.exportFile(dtoParameter);
                situsDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
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
		} catch (ContainerException e) {
            e.printStackTrace();
        } catch (SQLModelException ex) {
           ex.printLog();
        } finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
		}
	}
}
