package com.sino.sinoflow.servlet;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.sinoflow.bean.optionProducer.OptionProducer;
import com.sino.sinoflow.dao.TaskDao;
import com.sino.sinoflow.dto.TaskLookUpDTO;
import com.sino.sinoflow.model.TaskLookUpModel;
import com.sino.sinoflow.utilities.TimeUtil2;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;

public class TaskQuery extends BaseServlet {

	/**
	 * 合同进度维护
	 * 
	 */
	@Override
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Connection conn = null;
		String forwardURL = ""; // 页面跳转控制
        Message message = SessionUtil.getMessage(req);

		try {
			SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(TaskLookUpDTO.class.getName());
            TaskLookUpDTO lookUPDTO = (TaskLookUpDTO) req2DTO.getDTO(req);
			String action = lookUPDTO.getAct();
            lookUPDTO.setCalPattern(LINE_PATTERN);
			conn = getDBConnection(req);
			if(action.equals("")){//
                OptionProducer op = new OptionProducer(user, conn);
                lookUPDTO.setCompanyNameOpt(op.getAllOrganization(lookUPDTO.getCompanyName(), true));
                lookUPDTO.setProcedureNameOpt(op.getProcedure(lookUPDTO.getProcedureName()));
                lookUPDTO.setTaskNameOpt(op.getTaskOpt(lookUPDTO.getProcedureName(), lookUPDTO.getTaskName()));
                req.setAttribute(QueryConstant.QUERY_DTO, lookUPDTO);
                forwardURL="/system/taskQuery.jsp";
			} else if (action.equals(WebActionConstant.QUERY_ACTION)){//查询结果集
                OptionProducer op = new OptionProducer(user, conn);
                lookUPDTO.setCompanyNameOpt(op.getAllOrganization(lookUPDTO.getCompanyName(), true));
                lookUPDTO.setProcedureNameOpt(op.getProcedure(lookUPDTO.getProcedureName()));
                lookUPDTO.setTaskNameOpt(op.getTaskOpt(lookUPDTO.getProcedureName(), lookUPDTO.getTaskName()));
                TimeUtil2 tu = new TimeUtil2(user, conn);
                lookUPDTO.setHoursPerDay(tu.getWorktimeADay()/(float)3600000);
				BaseSQLProducer sqlProducer = new TaskLookUpModel(user, lookUPDTO);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.produceWebData();

                req.setAttribute(QueryConstant.QUERY_DTO, lookUPDTO);
				forwardURL="/system/taskQuery.jsp";
			}else if (action.equals(WebActionConstant.EXPORT_ACTION)) {
				TaskDao TaskDao = new TaskDao(user, lookUPDTO, conn);
                File file = TaskDao.getExportFile("审批节点时间统计");
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
            } else if(action.equals("CHANGE_PROC")) {
                String procName = req.getParameter("procedureName");
                OptionProducer op = new OptionProducer(user, conn);
                String taskOption = op.getTaskOpt(procName, "");
                res.setContentType("text/html; charset=GBK");
                PrintWriter out = res.getWriter();
                out.print(taskOption);
                out.close();
            }
        } catch (PoolPassivateException ex) {
            ex.printLog();
            message = getMessage(AssetsMessageKeys.POOL_PASSIVATE_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DTOException ex) {
            ex.printLog();
            message = getMessage(AssetsMessageKeys.DTO_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (SQLModelException ex) {
            ex.printLog();
            message = getMessage(AssetsMessageKeys.QUERY_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (QueryException ex) {
            ex.printLog();
            message = getMessage(AssetsMessageKeys.QUERY_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DataTransException ex) {
            ex.printLog();
            message = getMessage(AssetsMessageKeys.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (WebFileDownException ex) {
            ex.printLog();
            message = getMessage(AssetsMessageKeys.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } finally {
            closeDBConnection(conn);
            setHandleMessage(req, message);
            if (!forwardURL.equals("")) {
                ServletForwarder forwarder = new ServletForwarder(req, res);
                forwarder.forwardView(forwardURL);
            }
		}
	}
}
