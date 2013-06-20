package com.sino.ams.newasset.report.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.newasset.bean.AssetsReportDateUtil;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.constant.AssetsURLList;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.report.dao.ToDiscardedDeptAssetsReportDAO;
import com.sino.ams.newasset.report.dto.DeptAssetsReportDTO;
import com.sino.ams.newasset.report.model.ToDiscardedDeptAssetsReportModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
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
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * Created by IntelliJ IDEA.
 * @author 	李轶
 * Date: 2009-5-21
 * Time: 10:36:55
 * To change this template use File | Settings | File Templates.
 */

public class ToDiscardedDeptAssetsReportServlet extends BaseServlet {
	/**
	 * 所有的Servlet都必须实现的方法。
	 * Function:		待报废资产部门统计
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws javax.servlet.ServletException
	 * @throws java.io.IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO) getUserAccount(req);
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(DeptAssetsReportDTO.class.getName());
			DeptAssetsReportDTO dto = (DeptAssetsReportDTO) req2DTO.getDTO(req);
			String action = dto.getAct();
			conn = getDBConnection(req);
            AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
            String cityOption = "";
            String deptOpt = "";
            int organizationId = StrUtil.isEmpty(dto.getOrganizationId()) ? user.getOrganizationId() : dto.getOrganizationId();
            if (user.isProvAssetsManager()) {	//省资产管理员
            	cityOption = optProducer.getAllOrganization(dto.getOrganizationId(), true);
            	deptOpt = optProducer.getDeptOptionByOrgId(organizationId, "");
            } else if (user.isComAssetsManager()){	//地市资产管理员
                cityOption = optProducer.getOrganizationOpt(user.getOrganizationId());
                deptOpt = optProducer.getDeptOptionByOrgId(organizationId, "");
            } else{
            	cityOption = optProducer.getOrganizationOpt(user.getOrganizationId());
            	deptOpt = optProducer.getUserAsssetsDeptOption(user.getDeptCode());
            }
            
            req.setAttribute(AssetsWebAttributes.CITY_OPTION, cityOption);
		    req.setAttribute(AssetsWebAttributes.DEPT_OPTIONS, deptOpt);

             //年份
            String yearOption = optProducer.getYearOption(dto.getYear());
            req.setAttribute(WebAttrConstant.LAST_FIVE_YEAR_OPTION, yearOption);
            //月份
            String monthOption = optProducer.getMonthOption(dto.getMonth());
            req.setAttribute(WebAttrConstant.FULL_MONTH_OPTION, monthOption);

            if (action.equals("")) {
                req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = AssetsURLList.TODISCARDED_DEPT_ASSETS_REPORT;
			} else if (action.equals(AssetsActionConstant.QUERY_ACTION)) {
                AssetsReportDateUtil ardu = new AssetsReportDateUtil(dto);
				BaseSQLProducer sqlProducer = new ToDiscardedDeptAssetsReportModel(user, dto);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setPageSize(100);
				pageDAO.produceWebData();
                req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = AssetsURLList.TODISCARDED_DEPT_ASSETS_REPORT;
			} else if (action.equals(AssetsActionConstant.EXPORT_ACTION)) {
                AssetsReportDateUtil ardu = new AssetsReportDateUtil(dto);
				ToDiscardedDeptAssetsReportDAO rptDAO = new ToDiscardedDeptAssetsReportDAO(user, dto, conn);
				File file = rptDAO.getExportFile();
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
			} else {
				message = getMessage(MsgKeyConstant.INVALID_REQ);
				message.setIsError(true);
				forwardURL = MessageConstant.MSG_PRC_SERVLET;
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
		} catch (QueryException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.QUERY_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (WebFileDownException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DataTransException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DataHandleException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} finally {
			closeDBConnection(conn);
			setHandleMessage(req, message);
			if(!StrUtil.isEmpty(forwardURL)){
				ServletForwarder forwarder = new ServletForwarder(req, res);
				forwarder.forwardView(forwardURL);
			}
		}
	}
}
