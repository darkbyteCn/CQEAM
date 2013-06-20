package com.sino.pda;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.db.conn.DBManager;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.base.web.request.upload.UploadFile;
import com.sino.base.web.request.upload.UploadFileSaver;
import com.sino.base.web.request.upload.UploadRow;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.constant.AmsOrderConstant;
import com.sino.ams.sampling.bean.OrderXMLAssistant;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.FilterConfigDTO;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.pda.dao.OrderCreateDAO;
import com.sino.pda.dao.OrderCreateFactory;
import com.sino.pda.util.XmlUtil;

/**
 * User: zhoujs
 * Date: 2007-11-15
 * Time: 11:42:19
 * Function:创建工单(盘点工单)
 */
public class CreateWorkOrder extends BaseServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=GBK";

	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		Connection conn = null;
		String sFile = "";

		try {
			res.setContentType(CONTENT_TYPE);
			PrintWriter resout = res.getWriter();

			conn = DBManager.getDBConnection();
			String test = "";
			Logger.logInfo("PDA run CreateWorkOrder servlet begin....");
			RequestParser reqPar = new RequestParser();
			reqPar.transData(req);
			test = reqPar.getParameter("test");
			if (test.equals("Y")) {
				resout.println("<?xml version=\"1.0\" encoding=\"GB2312\" ?> ");
			} else {
				resout.println("<?xml version=\"1.0\" ?> ");
			}
			resout.println("<CreateWorkOrder>");
			UploadFile[] upFiles = null;
			UploadRow uploadRow;
			boolean hasError = false;
			String uploadPath = PDAUtil.getCurUploadFilePath(conn);

			UploadFileSaver uploadFileSaver = reqPar.getFileSaver();
			uploadFileSaver.saveFiles(uploadPath);


			uploadRow = uploadFileSaver.getRow();
			upFiles = uploadRow.getFiles();

			if (upFiles == null) {
				Logger.logError("upload file " + SyBaseSQLUtil.isNull() + " ");
				hasError = true;
				setResultValue(resout, false, "upload file " + SyBaseSQLUtil.isNull() + " ", "");
			} else {
				if (upFiles.length == 1) {
					if (upFiles[0].getFileName().equals("")) {
						Logger.logError("can't find upload xml files");
						hasError = true;
						setResultValue(resout, false, "can't find upload xml files,please check PDA prog or GPRS", "");
					}
				} else {
					Logger.logError("can't find any xml file");
					setResultValue(resout, false, "can't find any xml file", "");
					hasError = true;
				}
			}
			//==================================================
			if (!hasError) {
				sFile = upFiles[0].getAbsolutePath();

				XmlUtil xml = new XmlUtil();
				if (!xml.loadXmlFile(sFile)) {
					ClearBeforeCancel(resout, uploadPath, false, "load xml file error!");
					return;
				}
				List allOrderNos = xml.getAllRootChildren();
				int orderNum = allOrderNos.size();
				
//				if (orderNum < 1) {
//					ClearBeforeCancel(resout, uploadPath, false, "can't get XML file child");
//					return;
//				}
				
				String orderTypeDesc = "";
				//获取<workorder  type="巡检">	，root attribute
				orderTypeDesc = xml.getElementAttrValue(xml.getRootElement(), "type");
				Logger.logInfo("orderTypeDesc is " + orderTypeDesc);
				String orderNo = "";
				if (OrderXMLAssistant.getOrderTypes().contains(orderTypeDesc)) {
					FilterConfigDTO filterConfig = getFilterConfig(req);
					ServletConfigDTO servletConfig = SessionUtil.getServletConfigDTO(getServletContext());

					OrderCreateFactory fac = OrderCreateFactory.getInstance(filterConfig);
					OrderCreateDAO createDAO = fac.getOrderCreator(conn, sFile, orderTypeDesc);
					createDAO.setServletConfig(servletConfig);
					String errorMsg = "";
					if (createDAO.hasPreviousOrder()) {
						errorMsg = "该地点有工单尚未归档，不能创建工单";
					} else {
						orderNo = createDAO.createOrder();
					}
					setResultValue(resout, !orderNo.equals(""), errorMsg, orderNo);
				} else {
					PDAOrderUtil pdaOrderUtil = new PDAOrderUtil();
					orderNo = pdaOrderUtil.createWorkorder(conn, xml);
					boolean result = orderNo.equals("") || orderNo.equals(AmsOrderConstant.doubleOrder);
					String msg = orderNo.equals(AmsOrderConstant.doubleOrder) ? "该地点有巡检工单未归档，请先归档该地点巡检工单" : "";
					setResultValue(resout, !result, msg, orderNo);
				}
			} else {
				ClearBeforeCancel(resout, uploadPath, false, "can't find xml file");
			}
		} catch (PoolException e) {
			Logger.logError(e);
		} catch (ContainerException e) {
			Logger.logError(e);
		} catch (UploadException ex) {
			Logger.logError(ex);
		} catch (FileSizeException ex) {
			Logger.logError(ex);
		} catch (DataHandleException e) {
			Logger.logError(e);
		} catch (QueryException e) {
			Logger.logError(e);
		} finally {
			DBManager.closeDBConnection(conn);
			PDAUtil.clearDir(sFile);
		}
	}

	private void setResultValue(PrintWriter out, boolean b_flag, String errMsg, String m_workorder_no) {
		out.println("<result message=\"" + errMsg + "\" >" + b_flag + "</result>");
		out.println("<workorder id=\"" + m_workorder_no + "\" />");
		out.println("</CreateWorkOrder>");
		out.close();
	}

	private void ClearBeforeCancel(PrintWriter resout, String conFilePath, boolean flag, String msg) {
		setResultValue(resout, flag, msg, "");
//        PDAUtil.clearDir(conFilePath);
	}
}
