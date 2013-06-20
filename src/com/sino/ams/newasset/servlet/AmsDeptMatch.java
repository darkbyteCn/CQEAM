package com.sino.ams.newasset.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.OptionProducer;
import com.sino.ams.newasset.dao.AmsDeptMatchDAO;
import com.sino.ams.newasset.model.AmsDeptMatchModel;
import com.sino.ams.system.fixing.dto.EtsItemInfoDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
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
import com.sino.base.exception.ContainerException;
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
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.servlet.BaseServlet;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2009-8-25
 * Time: 14:59:17
 * To change this template use File | Settings | File Templates.
 */
public class AmsDeptMatch extends BaseServlet {
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "/newasset/amsDeptMatch.jsp";
		Connection conn = null;
		Message message = SessionUtil.getMessage(req);
		SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
		String act = StrUtil.nullToString(req.getParameter("act"));
		boolean matchSuccess = false;
		String showMsg = "";
		Request2DTO req2dto = new Request2DTO();
		EtsItemInfoDTO dto = null;
		try {
			req2dto.setDTOClassName(EtsItemInfoDTO.class.getName());
			dto = (EtsItemInfoDTO) req2dto.getDTO(req);
			conn = getDBConnection(req);
			OptionProducer op = new OptionProducer(user, conn);
			req.setAttribute("COUNTY_OPTION", op.getCountyOption(dto.getCountyCode()));
			req.setAttribute("AMS_HEADER", dto);
			String itemCategory = op.getDictOption("ITEM_TYPE", dto.getItemCategory());
			req.setAttribute("ITEM_CATEGORY", itemCategory);
			AmsDeptMatchDAO matchDAO = new AmsDeptMatchDAO(user, dto, conn);
			ServletConfigDTO configDTO = getServletConfig(req);
			if (act.equals(WebActionConstant.QUERY_ACTION)) {
				AMSSQLProducer sqlProducer = new AmsDeptMatchModel(user, dto);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setServletConfig(configDTO);
				pageDAO.setCalPattern(LINE_PATTERN);
				pageDAO.setPageSize(100);
				pageDAO.produceWebData();
			} else if (act.equals(WebActionConstant.SAVE_ACTION)) {
                String deptCode = StrUtil.nullToString(req.getParameter("deptCode"));
                String countyCode = StrUtil.nullToString(req.getParameter("tempRadio"));
                String companyCode = StrUtil.nullToString(req.getParameter("companyCode"));
				matchSuccess = matchDAO.saveMatch(deptCode, countyCode, companyCode);
				showMsg = "匹配成功!";
			} else if (act.equals(WebActionConstant.EXPORT_ACTION)) {
				forwardURL="";
				File file = exportFile(user, dto,conn,configDTO);
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
            e.printLog();
		} catch (ContainerException e) {
            e.printLog();
        } finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			if (matchSuccess) {
				res.setContentType("text/html;charset=GBK");
				PrintWriter out = res.getWriter();
				out.print("<script language=\"javascript\">\n");
				out.println("alert(\"" + showMsg + "\");");
				out.println("location.href=\"/servlet/com.sino.ams.newasset.servlet.AmsDeptMatch?act=" + WebActionConstant.QUERY_ACTION + "&key=" + dto.getKey().replaceAll("%","%25") + "&maintainUser="+StrUtil.nullToString(dto.getMaintainUser()).replaceAll("%","%25")+ "&workorderObjectName="+dto.getWorkorderObjectName().replaceAll("%","%25")+"&workorderObjectNo="+dto.getWorkorderObjectNo()+"&countyCode="+dto.getCountyCode()+"&responsibilityDept="+dto.getResponsibilityDept().replaceAll("%","%25")+"&itemName="+dto.getItemName().replaceAll("%","%25")+"&itemSpec="+dto.getItemSpec().replaceAll("%","%25") +"\";");
				out.println("parent.misInfo.document.forms[0].submit();");
				out.print("</script>");
			} else if(!act.equals("VERIFY")){
				ServletForwarder sf = new ServletForwarder(req, res);
				sf.forwardView(forwardURL);
			}
		}
	}


	/**
	 * 功能：导出Excel文件。
	 * @param user SfUserDTO
	 * @param dto EtsItemInfoDTO
	 * @param conn Connection
	 * @param configDTO ServletConfigDTO
	 * @return File
	 * @throws DataTransException
	 */
	public File exportFile( SfUserDTO user,EtsItemInfoDTO dto,Connection conn,ServletConfigDTO configDTO) throws DataTransException {
		File file = null;
		try {
			AMSSQLProducer sqlProducer = new AmsDeptMatchModel( user, dto);
			sqlProducer.setServletConfig(configDTO);
			SQLModel sqlModel = sqlProducer.getPageQueryModel();
			TransRule rule = new TransRule();
			rule.setDataSource(sqlModel);
			rule.setSourceConn(conn);
			String fileName = "部门.xls";
			String filePath = WorldConstant.USER_HOME;
			filePath += WorldConstant.FILE_SEPARATOR;
			filePath += fileName;
			rule.setTarFile(filePath);
			DataRange range = new DataRange();
			rule.setDataRange(range);

			Map fieldMap = new HashMap();
			fieldMap.put("COMPANY", "公司");
			fieldMap.put("DEPT_CODE", "部门代码");
			fieldMap.put("DEPT_NAME", "部门名称");
			rule.setFieldMap(fieldMap);
			CustomTransData custData = new CustomTransData();
			custData.setReportTitle("部门");
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
