package com.sino.ams.newasset.report.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.constant.AssetsURLList;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.report.dao.ReportInDataDAO;
import com.sino.ams.newasset.report.dto.ReportInDataDTO;
import com.sino.ams.newasset.report.model.ReportInDataModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.data.Row;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * Created by IntelliJ IDEA. User: su Date: 2009-5-14 Time: 16:49:34 To change
 * this template use File | Settings | File Templates.
 */
public class ReportInDataServlet extends BaseServlet {

	public void performTask(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO) getUserAccount(req);
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(ReportInDataDTO.class.getName());
			ReportInDataDTO dto = (ReportInDataDTO) req2DTO.getDTO(req);
			String action = dto.getAct();
			conn = getDBConnection(req);
			ReportInDataDAO rptDAO = new ReportInDataDAO(user, dto, conn);
			AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
			String orgOption = optProducer.getAllOrganization(dto
					.getOrganizationId(), true);
			req.setAttribute(WebAttrConstant.OU_OPTION, orgOption);
			String manageIndicators = "";
			if (dto.getKpi() == true) {
				manageIndicators = optProducer.getKpiReportTypeOption(dto
						.getManagerGuideType());
			} else {
				manageIndicators = optProducer.getDictOption(
						AssetsDictConstant.MANAGE_INDICATORS, dto
								.getManagerGuideType());
			}
			req.setAttribute(AssetsWebAttributes.MANAGE_INDICATORS_OPTION,
					manageIndicators);
			Row row = rptDAO.getIsKpi(dto.getManagerGuideType());
			boolean isPoint=req.getParameter("isPoint")==null?false:true;//是否为指定的
			req.setAttribute("isPoint", isPoint);
			// if(row != null){
			// dto.setKpi(true);
			// } else {
			// dto.setKpi(false);
			// }

			if (action.equals("")) {
				if (dto.getKpi() == true) {
					if (user.isProvinceUser()) {
						orgOption = optProducer.getAllCompanyCode(dto
								.getCompanyCode(), true);
					} else {
						orgOption = optProducer.getCompanyOpt(user
								.getCompanyCode());
					}
					req.setAttribute(WebAttrConstant.OU_OPTION, orgOption);
				}
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = AssetsURLList.ASSETS_IN_DATA_QUERY;
			} else if (action.equals(AssetsActionConstant.QUERY_ACTION)) {
				if (dto.getKpi() == true) {
					if (user.isProvinceUser()) {
						orgOption = optProducer.getAllCompanyCode(dto
								.getCompanyCode(), true);
					} else {
						orgOption = optProducer.getCompanyOpt(user
								.getCompanyCode());
					}
					req.setAttribute(WebAttrConstant.OU_OPTION, orgOption);
				}
				BaseSQLProducer sqlProducer = new ReportInDataModel(user, dto);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setCalPattern(LINE_PATTERN);
				pageDAO.produceWebData();
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = AssetsURLList.ASSETS_IN_DATA_QUERY;
			} else if (action.equals(WebActionConstant.NEW_ACTION)) {
				if (dto.getKpi()) {
					rptDAO.setDTOClassName(ReportInDataDTO.class.getName());
					if (dto.getPeriod().length() > 6) {
						dto.setPeriod(dto.getPeriod().substring(0, 7));
					} else {
						dto.setPeriod(dto.getPeriod().substring(0, 4) + "-"
								+ dto.getPeriod().substring(4));
					}
					ReportInDataDTO ridDto = (ReportInDataDTO) rptDAO
							.getDataByPrimaryKey();
					if (ridDto == null) {
						// dto.setKpiCode(row.getValue(0).toString());
						// dto.setKpiName(row.getValue(1).toString());
						req.setAttribute(QueryConstant.QUERY_DTO, dto);
					} else {
						ridDto.setKpi(true);
						ridDto.setKpiCode(dto.getManagerGuideType());
						// ridDto.setKpiName(row.getValue(1).toString());
						req.setAttribute(QueryConstant.QUERY_DTO, ridDto);
					}
					orgOption = optProducer.getCompanyOpt(dto.getCompanyCode());
					req.setAttribute(WebAttrConstant.OU_OPTION, orgOption);
				} else {
					req.setAttribute(QueryConstant.QUERY_DTO, dto);
				}
				if(isPoint){
					BaseSQLProducer sqlProducer = new ReportInDataModel(user, dto);
					dto.setOrganizationId(user.getOrganizationId());
					SimpleQuery sq=new SimpleQuery(sqlProducer.getPageQueryModel(),conn);
					sq.setDTOClassName(ReportInDataDTO.class.getName());
					sq.executeQuery();
					ReportInDataDTO ridDto = (ReportInDataDTO)sq.getFirstDTO();
					if(ridDto==null){
						req.setAttribute(QueryConstant.QUERY_DTO, dto);
					}else{
						dto.setReportId(ridDto.getReportId());
						rptDAO.setDTOClassName(ReportInDataDTO.class.getName());
						 ridDto = (ReportInDataDTO) rptDAO
								.getDataByPrimaryKey();
						ridDto.setManagerGuideType(dto.getManagerGuideType());
						ridDto.setOrgName(dto.getOrgName());
						req.setAttribute(QueryConstant.QUERY_DTO, ridDto);
						orgOption = optProducer.getAllOrganization(ridDto
								.getOrganizationId(), true);
					}
					
					req.setAttribute(WebAttrConstant.OU_OPTION, orgOption);
					forwardURL="/newasset/report/assetsInDataDetialPoint.jsp";
				}else{
					forwardURL = AssetsURLList.ASSETS_IN_DATA_INFO;
				}
			} else if (action.equals(WebActionConstant.CREATE_ACTION)) {
				rptDAO.setServletConfig(getServletConfig(req));
				rptDAO.createData();
				message = rptDAO.getMessage();
				if(isPoint){
					forwardURL="/servlet/com.sino.ams.newasset.report.servlet.ReportInDataServlet?act=POINT_AT_ACTION_QUERY";
				}else{
					forwardURL = AssetsURLList.ASSETS_IN_DATA_SERVLET;
				}
				
			} else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
				rptDAO.setServletConfig(getServletConfig(req));
				rptDAO.updateData();
				message = rptDAO.getMessage();
				if(isPoint){
					forwardURL="/servlet/com.sino.ams.newasset.report.servlet.ReportInDataServlet?act=POINT_AT_ACTION_QUERY";
				}else{
					forwardURL = AssetsURLList.ASSETS_IN_DATA_SERVLET;
				}
			} else if (action.equals(AssetsActionConstant.DETAIL_ACTION)) {
				rptDAO.setDTOClassName(ReportInDataDTO.class.getName());
				ReportInDataDTO ridDto = (ReportInDataDTO) rptDAO
						.getDataByPrimaryKey();
				if (ridDto == null) {
					dto = new ReportInDataDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}else{
					dto.setOrganizationId(ridDto.getOrganizationId());
				}
				orgOption = optProducer.getAllOrganization(dto
						.getOrganizationId(), true);
				if (dto.getKpi()) {
					ridDto.setKpi(true);
					req.setAttribute(QueryConstant.QUERY_DTO, ridDto);
					orgOption = optProducer.getCompanyOpt(dto.getCompanyCode());
				} else {
					req.setAttribute(QueryConstant.QUERY_DTO, ridDto);
				}
				req.setAttribute(WebAttrConstant.OU_OPTION, orgOption);
				if(isPoint){
					ridDto.setOrgName(dto.getOrgName());
					req.setAttribute(QueryConstant.QUERY_DTO, ridDto);
					forwardURL="/newasset/report/assetsInDataDetialPoint.jsp";
				}else{
					forwardURL = AssetsURLList.ASSETS_IN_DATA_INFO;
				}
			} else if (action.equals(WebActionConstant.DELETE_ACTION)) {
				rptDAO.deleteData();
				message = rptDAO.getMessage();
				if(isPoint){
					forwardURL="/servlet/com.sino.ams.newasset.report.servlet.ReportInDataServlet?act=POINT_AT_ACTION_QUERY";
				}else{
					forwardURL = AssetsURLList.ASSETS_IN_DATA_SERVLET;
				}

			} else if ("POINT_AT_ACTION".equals(action)) {

				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				manageIndicators = setOpt(dto.getManagerGuideType());
				req.setAttribute(AssetsWebAttributes.MANAGE_INDICATORS_OPTION,
						manageIndicators);
				forwardURL = "/newasset/report/assetsInDataQueryPoint.jsp";
			} else if ("POINT_AT_ACTION_QUERY".equals(action)) {

				manageIndicators = setOpt(dto.getManagerGuideType());
				req.setAttribute(AssetsWebAttributes.MANAGE_INDICATORS_OPTION,
						manageIndicators);
				
				BaseSQLProducer sqlProducer = new ReportInDataModel(user, dto);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setCalPattern(LINE_PATTERN);
				pageDAO.produceWebData();
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = "/newasset/report/assetsInDataQueryPoint.jsp";
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
		} catch (DataHandleException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (SQLModelException e) {
			e.printLog();
			message = getMessage(AssetsMessageKeys.QUERY_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
			e.printStackTrace();
		} finally {
			closeDBConnection(conn);
			setHandleMessage(req, message);
			if (!StrUtil.isEmpty(forwardURL)) {
				ServletForwarder forwarder = new ServletForwarder(req, res);
				forwarder.forwardView(forwardURL);
			}
		}
	}

	private String setOpt(String selectedValue) {
		StringBuffer str = new StringBuffer();
		/*
		 * <option value="">--请选择--</option><option value="TRUN_RATE" >转资率</option>
		 * <option value="NICETY_RATE" >转资信息准确率</option> <option
		 * value="MATCH_CASE_RATE" >抽查盘点资产账实相符率</option> <option
		 * value="IN_TIME_RATE" >资产管理系统决策分析报表上报及时率</option> <option
		 * value="COP_RATE" >日常巡检资产盘点完成率</option> <option
		 * value="COP_MATCH_RATE" >日常巡检资产盘点账实相符率</option> <option
		 * value="CHECK_RATE" >资产实物管理抽查任务完成率</option>
		 */
		ArrayList<String> arsCode = new ArrayList<String>();
		arsCode.add("");
		arsCode.add("CHECK_RATE");
		arsCode.add("TRUN_RATE");
		arsCode.add("IN_TIME_RATE");
		ArrayList<String> arsTest = new ArrayList<String>();
		arsTest.add("--请选择--");
		arsTest.add("资产实物管理抽查任务完成率");
		arsTest.add("转资率");
		arsTest.add("资产管理系统决策分析报表上报及时率");
		for (int i = 0; i < arsCode.size(); i++) {
			String code = arsCode.get(i);
			if (code.equals(selectedValue)) {
				str.append("<option value='" + arsCode.get(i) + "' selected >"
						+ arsTest.get(i) + "</option>");
			} else {
				str.append("<option value='" + arsCode.get(i) + "' >"
						+ arsTest.get(i) + "</option>");
			}
		}

		return str.toString();
	}
}
