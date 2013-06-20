package com.sino.ams.system.cost.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.system.cost.dao.CostDeptDAO;
import com.sino.ams.system.cost.dto.AmsMisCostMatchDTO;
import com.sino.ams.system.cost.model.DeptMisModel;
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
import com.sino.base.exception.WebFileDownException;
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
 * User: Administrator
 * Date: 2008-7-31
 * Time: 13:31:00
 * To change this template use File | Settings | File Templates.
 */
public class DeptMisServlet extends BaseServlet {
     public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "/system/cost/deptMis.jsp";
        Connection conn = null;
       	boolean matchSuccess = false;
        Message message = SessionUtil.getMessage(req);
        SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
        String act = StrUtil.nullToString(req.getParameter("act"));
        Request2DTO req2dto = new Request2DTO();
        String showMsg = "";
        try {
            req2dto.setDTOClassName(AmsMisCostMatchDTO.class.getName());
            AmsMisCostMatchDTO costdto = (AmsMisCostMatchDTO) req2dto.getDTO(req);
            conn = getDBConnection(req);
            ServletConfigDTO configDTO = getServletConfig(req);
            CostDeptDAO matchDAO = new CostDeptDAO(user, costdto, conn);
             req.setAttribute("MIS_HEADER", costdto);
            if (act.equals(WebActionConstant.QUERY_ACTION)) {
                AMSSQLProducer sqlProducer = new DeptMisModel(user, costdto);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setServletConfig(configDTO);
                pageDAO.setPageSize(100);
                pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
                pageDAO.produceWebData();
          	} else if (act.equals(WebActionConstant.SAVE_ACTION)) {   //匹配
				String[] depts = req.getParameterValues("subCheck");
				String cost = StrUtil.nullToString(req.getParameter("tempRadio"));
				String companyCode = StrUtil.nullToString(req.getParameter("tempCompanyCode"));
//                matchSuccess = matchDAO.saveItemMatch(depts, cost);
                matchSuccess = matchDAO.doMatch(depts, cost,companyCode);
                showMsg = "匹配成功!";
            } else if (act.equals(WebActionConstant.EXPORT_ACTION)) {      //导出
                forwardURL = "";
                DeptMisModel sqlProducer = new DeptMisModel(user, costdto);
                sqlProducer.setServletConfig(configDTO);
                SQLModel sqlMode2 = sqlProducer.getPageQueryModel();

                File file = exportFile(sqlMode2,user, costdto, conn);
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
        } catch (DataTransException e) {
            e.printLog();
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
        } catch (WebFileDownException e) {
            e.printLog();
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            	if (matchSuccess) {
				res.setContentType("text/html;charset=GBK");
				PrintWriter out = res.getWriter();
				out.print("<script language=\"javascript\">\n");
				out.println("alert(\"" + showMsg + "\");");
                out.println("location.href=\"/servlet/com.sino.ams.system.cost.servlet.DeptMisServlet?act=" + WebActionConstant.QUERY_ACTION +  "\";");
                out.println("parent.amsInfo.document.forms[0].submit();");
				out.print("</script>");
			} else {
            ServletForwarder sf = new ServletForwarder(req, res);
            sf.forwardView(forwardURL);
                }
        }
    }

    /**
     * 功能：导出Excel文件。
     *
     * @return File
     * @throws com.sino.base.exception.DataTransException
     *
     */
    private File exportFile(SQLModel sqlMode2,SfUserDTO user, AmsMisCostMatchDTO itemInfo2, Connection conn) throws DataTransException {
        File file = null;
//        try {
            TransRule rule = new TransRule();
            rule.setDataSource(sqlMode2);
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
//        } catch (SQLModelException ex) {
//            ex.printLog();
//            throw new DataTransException(ex);
//        }
        return file;
    }
}
