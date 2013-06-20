package com.sino.ams.inv.common.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.constant.AMSActionConstant;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.inv.common.bean.InvOptionProducer;
import com.sino.ams.inv.common.dao.EtsObjectDAO;
import com.sino.ams.inv.common.model.EtsObjectModel;
import com.sino.ams.system.basepoint.dto.EtsObjectDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * <p>Title: EtsObjectServlet</p>
 * <p>Description:仓库地点设置</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author V-Yushibo
 * @version 1.0
 */
public class EtsObjectServlet extends BaseServlet {

	/**
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		String action = req.getParameter("act");

		String[] workorderObjectCodes = req.getParameterValues("workorderObjectCodes");
		action = StrUtil.nullToString(action);
		Connection conn = null;

		try {
			SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
			EtsObjectDTO dtoParameter = null;
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(EtsObjectDTO.class.getName());
			dtoParameter = (EtsObjectDTO) req2DTO.getDTO(req);
			conn = getDBConnection(req);
			EtsObjectDAO etsObjectDAO = new EtsObjectDAO(user, dtoParameter, conn);
			InvOptionProducer optProducer = new InvOptionProducer(user, conn);

			//所属成本中心列表
			String countyOption = optProducer.getCountyOption(dtoParameter.getCountyCode());
			req.setAttribute(WebAttrConstant.COUNTY_OPTION, countyOption);
			//仓库类型列表
			String warehouseTypeOption = optProducer.getWarehouseTypeOption( dtoParameter.getObjectCategory());
			req.setAttribute(WebAttrConstant.WAREHOUSE_TYPE_OPTION, warehouseTypeOption);
			//业务类型列表
			String busihouseTypeOption = optProducer.getDictOption("INV_BIZ_CATEGORY",  String.valueOf(dtoParameter.getBusinessCategory()));
			req.setAttribute(WebAttrConstant.BUSIHOUSE_TYPE_OPTION, busihouseTypeOption);

			if (action.equals("")) {
				forwardURL = "/inv/warehouseQuery.jsp";
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new EtsObjectModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.produceWebData();
				forwardURL = "/inv/warehouseQuery.jsp";
			} else if (action.equals(WebActionConstant.NEW_ACTION)) {
				EtsObjectDTO etsObject = (EtsObjectDTO) req.getAttribute(WebAttrConstant.WAREHOUSE_ATTR);
				if (etsObject == null) {
					etsObject = dtoParameter;
				}
				req.setAttribute(WebAttrConstant.WAREHOUSE_ATTR, etsObject);
				forwardURL = "/inv/warehouseDetail.jsp";
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
				etsObjectDAO.setDTOClassName(EtsObjectDTO.class.getName());
				EtsObjectDTO etsObject = (EtsObjectDTO) etsObjectDAO.getDataByPrimaryKey();

				//所属成本中心列表
				countyOption = optProducer.getCountyOption(etsObject.getCountyCode());
				req.setAttribute(WebAttrConstant.COUNTY_OPTION, countyOption);
				//仓库类型列表
				warehouseTypeOption = optProducer.getWarehouseTypeOption( etsObject.getObjectCategory());
				req.setAttribute(WebAttrConstant.WAREHOUSE_TYPE_OPTION, warehouseTypeOption);
				//业务类型列表
				busihouseTypeOption = optProducer.getDictOption("INV_BIZ_CATEGORY",  String.valueOf(dtoParameter.getBusinessCategory()));
				req.setAttribute(WebAttrConstant.BUSIHOUSE_TYPE_OPTION, busihouseTypeOption);

				req.setAttribute(WebAttrConstant.WAREHOUSE_ATTR, etsObject);
				forwardURL = "/inv/warehouseDetail.jsp";
			} else if (action.equals("CHECK_CATEGORY_ACTION")) {     //INV_NORMAL以外类型的仓库一个OU只允许有一个
				String objCategory = req.getParameter("objectCategory");
				PrintWriter out = res.getWriter();
				if (!objCategory.equals(DictConstant.INV_NORMAL)) {
					String objectCode = etsObjectDAO.checkCategory(objCategory);
					if (!objectCode.equals("")) {
						out.print(objectCode);
					} else out.print(WebAttrConstant.CATEGORY_NOT_EXIST);
				} else out.print(WebAttrConstant.CATEGORY_NOT_EXIST);
				out.flush();
				out.close();
			} else if (action.equals("CHECK_CODE_ACTION")) {     //INV_NORMAL以外类型的仓库一个OU只允许有一个
				String objCode = req.getParameter("workorderObjectCode");
				PrintWriter out = res.getWriter();
				boolean hasBeen = etsObjectDAO.checkCode(objCode);
				if (hasBeen) out.print(WebAttrConstant.CODE_EXIST);
				else out.print(WebAttrConstant.CODE_NOT_EXIST);
				out.flush();
				out.close();
			} else if (action.equals(WebActionConstant.CREATE_ACTION)) {
				etsObjectDAO.createEoData();
				message = etsObjectDAO.getMessage();
				req.setAttribute(WebAttrConstant.WAREHOUSE_ATTR, dtoParameter);
				forwardURL = URLDefineList.WINDOW_CLOSE_PAGE;
			} else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
				etsObjectDAO.updateData();
				message = etsObjectDAO.getMessage();
				forwardURL = URLDefineList.WINDOW_CLOSE_PAGE;
			} /*else if (action.equals(WebActionConstant.DELETE_ACTION)) {
				boolean operateResult = etsObjectDAO.deleteData();
				message = etsObjectDAO.getMessage();
				message.setIsError(!operateResult);
				forwardURL = URLDefineList.WAREHOUSE_QUERY_PAGE;
			}*/ /*else if (action.equals(WebActionConstant.DELETE_ACTION)) {        //详细页面失效操作（单个失效）
				boolean operateResult = etsObjectDAO.deleteData();
				message = etsObjectDAO.getMessage();
				message.setIsError(!operateResult);
				//区县列表
				countyOption = optProducer.getCountyOption(dtoParameter.getCountyCode());
				req.setAttribute(WebAttrConstant.COUNTY_OPTION, countyOption);
				//仓库类型列表
				warehouseTypeOption = optProducer.getWarehouseTypeOption(dtoParameter.getObjectCategory());
				req.setAttribute(WebAttrConstant.WAREHOUSE_TYPE_OPTION, warehouseTypeOption);

				req.setAttribute(WebAttrConstant.WAREHOUSE_ATTR, dtoParameter);
				forwardURL = URLDefineList.WAREHOUSE_DETAIL_PAGE;
			} else if (action.equals(AMSActionConstant.INURE_ACTION)) {    // 详细页面生效操作 （单个生效）
				etsObjectDAO.inureData();
				//区县列表
				countyOption = optProducer.getCountyOption(dtoParameter.getCountyCode());
				req.setAttribute(WebAttrConstant.COUNTY_OPTION, countyOption);
				//仓库类型列表
				warehouseTypeOption = optProducer.getWarehouseTypeOption(dtoParameter.getObjectCategory());
				req.setAttribute(WebAttrConstant.WAREHOUSE_TYPE_OPTION, warehouseTypeOption);

				req.setAttribute(WebAttrConstant.WAREHOUSE_ATTR, dtoParameter);
				forwardURL = URLDefineList.WAREHOUSE_DETAIL_PAGE;
			}*/
			else if (action.equals(AMSActionConstant.DISABLED_ACTION)) {    //查询页面 批量失效
				etsObjectDAO.do_disable(workorderObjectCodes);
				message = etsObjectDAO.getMessage();
				forwardURL = "/servlet/com.sino.ams.inv.common.servlet.EtsObjectServlet?act=" + WebActionConstant.QUERY_ACTION;
			} else if (action.equals(AMSActionConstant.EFFICIENT_ACTION)) {      //查询页面 批量生效
				etsObjectDAO.efficientData(workorderObjectCodes);
				message = etsObjectDAO.getMessage();
				forwardURL = "/servlet/com.sino.ams.inv.common.servlet.EtsObjectServlet?act=" + WebActionConstant.QUERY_ACTION;
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
		} catch (ContainerException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
		}
	}

}
