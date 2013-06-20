package com.sino.ams.system.cost.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.OptionProducer;
import com.sino.ams.system.cost.dao.CostDeptDAO;
import com.sino.ams.system.cost.dto.AmsMisCostMatchDTO;
import com.sino.ams.system.cost.model.CostCenterModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.datatrans.CustomTransData;
import com.sino.base.db.datatrans.DataRange;
import com.sino.base.db.datatrans.DataTransfer;
import com.sino.base.db.datatrans.TransRule;
import com.sino.base.db.datatrans.TransferFactory;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2008-7-31
 * Time: 13:30:45
 * To change this template use File | Settings | File Templates.
 */
public class CostCenterServlet extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "/system/cost/costCenter.jsp";
		Connection conn = null;
		Message message = SessionUtil.getMessage(req);
		SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
		String act = StrUtil.nullToString(req.getParameter("act"));
		boolean matchSuccess = false;
		String showMsg = "";
		AmsMisCostMatchDTO costdto = null;
		Request2DTO req2dto = new Request2DTO();
		try {
			req2dto.setDTOClassName(AmsMisCostMatchDTO.class.getName());
			costdto = (AmsMisCostMatchDTO) req2dto.getDTO(req);

			conn = getDBConnection(req);
			OptionProducer op = new OptionProducer(user, conn);
//			req.setAttribute("COUNTY_OPTION", op.getCountyOption(costdto.getCountyCode()));
			req.setAttribute("AMS_HEADER", costdto);
			CostDeptDAO matchDAO = new CostDeptDAO(user, costdto, conn);

            if (act.equals(WebActionConstant.QUERY_ACTION)) {
				AMSSQLProducer sqlProducer = new CostCenterModel(user, costdto);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setCalPattern(LINE_PATTERN);
				pageDAO.setPageSize(100);
				pageDAO.produceWebData();
			} else if (act.equals(WebActionConstant.SAVE_ACTION)) {   //单个匹配
				String[] systemIds = req.getParameterValues("subCheck");
				String assetId = StrUtil.nullToString(req.getParameter("tempRadio"));
				matchSuccess = matchDAO.saveItemMatch(systemIds, assetId);
				showMsg = "匹配成功!";
			} else if (act.equals(WebActionConstant.EXPORT_ACTION)) {      //导出
				forwardURL="";
			    File file = exportFile(user, costdto,conn);
//                amsHouseInfoDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
			}
		} catch (DTOException e) {
			e.printLog();
			message = getMessage(MsgKeyConstant.DTO_ERROR);
			message.setIsError(true);
		} catch (PoolPassivateException e) {
			e.printLog();
			message = getMessage(MsgKeyConstant.POOL_PASSIVATE_ERROR);
			message.setIsError(true);
		} catch (QueryException e) {
			e.printLog();
			message = getMessage(MsgKeyConstant.QUERY_ERROR);
			message.setIsError(true);
		} catch (SQLException e) {
			Logger.logError(e);
			message = getMessage(MsgKeyConstant.SQL_ERROR);
			message.setIsError(true);
		} catch (WebFileDownException e) {
			Logger.logError(e);
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
		} catch (DataTransException e) {
			 Logger.logError(e);
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
//			if (matchSuccess) {
//				res.setContentType("text/html;charset=GBK");
//				PrintWriter out = res.getWriter();
//				out.print("<script language=\"javascript\">\n");
//				out.println("alert(\"" + showMsg + "\");");
//				out.println("parent.misInfo.document.forms[0].submit();");
//				out.print("</script>");
//			} else {
				ServletForwarder sf = new ServletForwarder(req, res);
				sf.forwardView(forwardURL);
//			}
		}
	}

	/**
	 * 功能：导出Excel文件。
	 *
	 * @return File
	 * @throws com.sino.base.exception.DataTransException
	 *
	 */
	private File exportFile( SfUserDTO user,AmsMisCostMatchDTO costdto,Connection conn) throws DataTransException {
		File file = null;
		try {
			AMSSQLProducer sqlProducer = new CostCenterModel( user, costdto);
			SQLModel sqlModel = sqlProducer.getPageQueryModel();
			TransRule rule = new TransRule();
			rule.setDataSource(sqlModel);
            rule.setCalPattern(CalendarConstant.LINE_PATTERN);
			rule.setSourceConn(conn);
			String fileName = "信息.xls";
			String filePath = WorldConstant.USER_HOME;
			filePath += WorldConstant.FILE_SEPARATOR;
			filePath += fileName;
			rule.setTarFile(filePath);
			DataRange range = new DataRange();
			rule.setDataRange(range);

			Map fieldMap = new HashMap();
			fieldMap.put("", "");
			rule.setFieldMap(fieldMap);

			CustomTransData custData = new CustomTransData();
			custData.setReportTitle("信息");
			custData.setReportPerson(user.getUsername());
			custData.setNeedReportDate(true);
			rule.setCustData(custData);
			TransferFactory factory = new TransferFactory();
			DataTransfer transfer = factory.getTransfer(rule);
			transfer.transData();
			file = (File) transfer.getTransResult();
		} catch (SQLModelException ex) {
			ex.printLog();
			throw new DataTransException(ex);
		}
		return file;
	}
}
