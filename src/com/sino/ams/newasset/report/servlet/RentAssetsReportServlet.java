package com.sino.ams.newasset.report.servlet;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.newasset.bean.AssetsReportDateUtil;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.newasset.report.dao.RentAssetsReportDAO;
import com.sino.ams.newasset.report.model.RentAssetsReportModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
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
 * Function	:	出租资产统计
 * <p>Description:程序自动生成服务程序“RentAssetsReportServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 		李轶
 * @version 1.0
 */


public class RentAssetsReportServlet extends BaseServlet {

	/**
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		String action = req.getParameter("act");
		action = StrUtil.nullToString(action);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO)SessionUtil.getUserAccount(req);
			AmsAssetsAddressVDTO dtoParameter = null;
            Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(AmsAssetsAddressVDTO.class.getName());
			dtoParameter = (AmsAssetsAddressVDTO)req2DTO.getDTO(req);
			conn = getDBConnection(req);
			AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
            String cityOption = "";
            String deptOpt = "";
            int organizationId = StrUtil.isEmpty(dtoParameter.getOrganizationId()) ? user.getOrganizationId() : dtoParameter.getOrganizationId();
            if (user.isProvAssetsManager()) {	//省资产管理员
            	cityOption = optProducer.getAllOrganization(dtoParameter.getOrganizationId(), true);
            	deptOpt = optProducer.getDeptOptionByOrgId(organizationId, dtoParameter.getResponsibilityDept());
            } else if (user.isComAssetsManager()){	//地市资产管理员
                cityOption = optProducer.getOrganizationOpt(user.getOrganizationId());
                deptOpt = optProducer.getDeptOptionByOrgId(organizationId, dtoParameter.getResponsibilityDept());
            } else{
            	cityOption = optProducer.getOrganizationOpt(user.getOrganizationId());
            	deptOpt = optProducer.getUserAsssetsDeptOption(user.getDeptCode());
            }
            req.setAttribute(AssetsWebAttributes.CITY_OPTION, cityOption);
		    req.setAttribute(AssetsWebAttributes.DEPT_OPTIONS, deptOpt);
                               //年份
//            String yearOption = optProducer.getYearOption(dtoParameter.getYear());
//            req.setAttribute(WebAttrConstant.LAST_FIVE_YEAR_OPTION, yearOption);
//            //月份
//            String monthOption = optProducer.getMonthOption(dtoParameter.getMonth());
//            req.setAttribute(WebAttrConstant.FULL_MONTH_OPTION, monthOption);
            
            if (action.equals("")) {
                req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);   
                forwardURL = URLDefineList.RENT_ASSETS_REPORT;
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
//                AssetsReportDateUtil ardu = new AssetsReportDateUtil(dtoParameter);
				BaseSQLProducer sqlProducer = new RentAssetsReportModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);           //
                pageDAO.produceWebData();
                req.setAttribute(QueryConstant.QUERY_DTO , dtoParameter);
                forwardURL = URLDefineList.RENT_ASSETS_REPORT;
			}  else if (action.equals(WebActionConstant.EXPORT_ACTION)) {
//                AssetsReportDateUtil ardu = new AssetsReportDateUtil(dtoParameter);
				RentAssetsReportDAO dao = new RentAssetsReportDAO(user, dtoParameter, conn);
                File file = dao.exportFile();
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
             }  else {
				message = getMessage(MsgKeyConstant.INVALID_REQ);
				message.setIsError(true);
				forwardURL = MessageConstant.MSG_PRC_SERVLET;
			}
		} catch (PoolPassivateException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.POOL_PASSIVATE_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DTOException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.DTO_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (QueryException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.QUERY_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DataHandleException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (WebFileDownException e) {
             e.printLog();
        } finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
              if (!forwardURL.equals("")) {
            forwarder.forwardView(forwardURL);
              }
            //根据实际情况修改页面跳转代码。
		}
	}
}
