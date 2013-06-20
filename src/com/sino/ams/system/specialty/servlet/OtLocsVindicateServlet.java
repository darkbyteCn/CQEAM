package com.sino.ams.system.specialty.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.AMSActionConstant;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.system.basepoint.dto.EtsObjectDTO;
import com.sino.ams.system.specialty.dao.OtLocsVindicateDAO;
import com.sino.ams.system.specialty.model.OtLocsVindicateModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * Created by IntelliJ IDEA.
 * User: Zyun
 * Date: 2007-12-20
 * Time: 11:50:48
 * function;其他地点维护.
 */
public class OtLocsVindicateServlet extends BaseServlet {

	/**
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws javax.servlet.ServletException
	 * @throws java.io.IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		String action = req.getParameter("act");
		action = StrUtil.nullToString(action);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
			EtsObjectDTO dtoParameter = null;
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(EtsObjectDTO.class.getName());
			dtoParameter = (EtsObjectDTO) req2DTO.getDTO(req);
			conn = getDBConnection(req);
			OtLocsVindicateDAO etsObjectDAO = new OtLocsVindicateDAO(user, dtoParameter, conn);
			dtoParameter.setCategoryName(etsObjectDAO.getCategoryName());
			OptionProducer optProducer = new OptionProducer(user, conn);
            if (action.equals("")) {
				String countyOption = optProducer.getCountyOption(dtoParameter.getCountyCode());
				req.setAttribute(WebAttrConstant.COUNTY_OPTION, countyOption);
                req.setAttribute(WebAttrConstant.ETS_OBJECT_DTO, dtoParameter);
				forwardURL = URLDefineList.OTHER_LOCATIONS_QUERY;
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				String countyOption = optProducer.getCountyOption(dtoParameter.getCountyCode());
				req.setAttribute(WebAttrConstant.COUNTY_OPTION, countyOption);
				BaseSQLProducer sqlProducer = new OtLocsVindicateModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				pageDAO.produceWebData();
				req.setAttribute(WebAttrConstant.ETS_OBJECT_DTO, dtoParameter);
				forwardURL = URLDefineList.OTHER_LOCATIONS_QUERY;
			} else if (action.equals(WebActionConstant.NEW_ACTION)) {      //点新增操作
				String patrolOption = optProducer.getDictOption(DictConstant.CHECK_MODLE, StrUtil.nullToString(dtoParameter.getIsall()));
				req.setAttribute(WebAttrConstant.CHECK_OPTION, patrolOption);
				String countyOption = optProducer.getCountyOption(dtoParameter.getCountyCode());
				req.setAttribute(WebAttrConstant.COUNTY_OPTION, countyOption);
                String areaTypeSelect = optProducer.getDictOption("ADDR_AREA_TYPE", dtoParameter.getAreaType());
	            req.setAttribute(WebAttrConstant.AREA_OPTION, areaTypeSelect);
                dtoParameter = new EtsObjectDTO();
				dtoParameter.setObjectCategory("80");
				req.setAttribute(WebAttrConstant.ETS_OBJECT_DTO, dtoParameter);
				forwardURL = URLDefineList.OTHER_LOCATIONS_INFO;
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {        //点明细操作
				etsObjectDAO.setDTOClassName(EtsObjectDTO.class.getName());
				EtsObjectDTO etsObject = (EtsObjectDTO) etsObjectDAO.getDataByPrimaryKey();
				if (etsObject == null) {
					etsObject = new EtsObjectDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
				String countyOption = optProducer.getCountyOption(etsObject.getCountyCode());
				req.setAttribute(WebAttrConstant.COUNTY_OPTION, countyOption);
			    String areaTypeSelect = optProducer.getDictOption("ADDR_AREA_TYPE", etsObject.getAreaType());
	            req.setAttribute(WebAttrConstant.AREA_OPTION, areaTypeSelect);
                String patrolOption = optProducer.getDictOption(DictConstant.CHECK_MODLE, StrUtil.nullToString(etsObject.getIsall()));
				req.setAttribute(WebAttrConstant.CHECK_OPTION, patrolOption);
				etsObject.setCategoryName(dtoParameter.getCategoryName());
				req.setAttribute(WebAttrConstant.ETS_OBJECT_DTO, etsObject);
				forwardURL = URLDefineList.OTHER_LOCATIONS_INFO;
			} else if (action.equals(WebActionConstant.CREATE_ACTION)) {  //点save操作
				etsObjectDAO.createData2();
				message = etsObjectDAO.getMessage();
				String countyOption = optProducer.getCountyOption(dtoParameter.getCountyCode());
				req.setAttribute(WebAttrConstant.COUNTY_OPTION, countyOption);
				req.setAttribute(WebAttrConstant.ETS_OBJECT_DTO, dtoParameter);
				forwardURL = URLDefineList.OTHER_LOCATIONS_QUERY;
			} else if (action.equals(WebActionConstant.UPDATE_ACTION)) {    //修改操作
				etsObjectDAO.updateData();
				message = etsObjectDAO.getMessage();
				String countyOption = optProducer.getCountyOption(dtoParameter.getCountyCode());
                req.setAttribute(WebAttrConstant.COUNTY_OPTION, countyOption);
                req.setAttribute(WebAttrConstant.ETS_OBJECT_DTO, dtoParameter);
				forwardURL = URLDefineList.OTHER_LOCATIONS_QUERY;
			} else if (action.equals(WebActionConstant.DELETE_ACTION)) {      //失效操作 （单个失效）
				etsObjectDAO.deleteData();
				message = etsObjectDAO.getMessage();

				forwardURL = URLDefineList.OTHER_QUERY_SERVLET;
			} else if (action.equals(AMSActionConstant.INURE_ACTION)) {    // 详细页面生效操作 （单个生效）
				etsObjectDAO.inureData();
				String countyOption = optProducer.getCountyOption(dtoParameter.getCountyCode());
				req.setAttribute(WebAttrConstant.COUNTY_OPTION, countyOption);
				String cityOption = optProducer.getAllOrganization(0);
				req.setAttribute(WebAttrConstant.CITY_OPTION, cityOption);
				forwardURL = URLDefineList.OTHER_QUERY_SERVLET;
			} else if (action.equals(AMSActionConstant.DISABLED_ACTION)) {    //查询页面 批量失效
				String[] workorderObjectNos = req.getParameterValues("workorderObjectNos");

				etsObjectDAO.disabledData(workorderObjectNos);
				message = etsObjectDAO.getMessage();
				String countyOption = optProducer.getCountyOption(dtoParameter.getCountyCode());
				req.setAttribute(WebAttrConstant.COUNTY_OPTION, countyOption);
				String cityOption = optProducer.getAllOrganization(0);
				req.setAttribute(WebAttrConstant.CITY_OPTION, cityOption);
//                forwardURL = URLDefineList.SWITCHES_QUERY_PAGE;
				forwardURL = URLDefineList.OTHER_QUERY_SERVLET;
			} else if (action.equals(AMSActionConstant.EFFICIENT_ACTION)) {      //查询页面 批量生效
				String[] workorderObjectNos = req.getParameterValues("workorderObjectNos");

				etsObjectDAO.efficientData(workorderObjectNos);
				message = etsObjectDAO.getMessage();
				String countyOption = optProducer.getCountyOption(dtoParameter.getCountyCode());
				req.setAttribute(WebAttrConstant.COUNTY_OPTION, countyOption);
				String cityOption = optProducer.getAllOrganization(0);
				req.setAttribute(WebAttrConstant.CITY_OPTION, cityOption);
//                forwardURL = URLDefineList.SWITCHES_QUERY_PAGE;
				forwardURL = URLDefineList.OTHER_QUERY_SERVLET;
			} else if (action.equals(WebActionConstant.EXPORT_ACTION)) {      //导出到Excel
//                etsObjectDAO.setParameter(paTasksDTO);
//                Export2Excel excel = new Export2Excel(conn, req);
//                etsObjectDAO.setExportProp(excel);
//                excel.setFileName("交换机房");
//                excel.excute();
				File file = etsObjectDAO.exportFile();
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
			} else if (action.equals("verifyworkNo")) {                                          //验证workorderObjectCode是否存在
			   String workorderObjectCode = StrUtil.nullToString(req.getParameter("workorderObjectCode"));
				boolean success = etsObjectDAO.doVerifyWorkNo(workorderObjectCode);
				PrintWriter out = res.getWriter();
				if (success) {
					out.print("Y");
				}
				out.flush();
				out.close();
			 } else if (action.equals("verifyObjectNos")) {                                          //验证该地点下是否存在设备
			 String[] workorderObjectNos = req.getParameterValues("workorderObjectNos");
//                 System.out.println("1111111111111");
				 System.out.println("workorderObjectNos="+workorderObjectNos);
				boolean success = etsObjectDAO.doVerifyWorkBarcode(workorderObjectNos);
				PrintWriter out = res.getWriter();
				if (success) {
					out.print("Y");
				}
				out.flush();
				out.close();
			} else {
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
		} catch (WebFileDownException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
			//根据实际情况修改页面跳转代码。
		}
	}
}
