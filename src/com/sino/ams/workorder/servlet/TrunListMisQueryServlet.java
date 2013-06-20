package com.sino.ams.workorder.servlet;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dao.TrunListMisQueryDAO;
import com.sino.ams.workorder.dto.EtsWorkorderDTO;
import com.sino.ams.workorder.model.TrunListMisQueryModel;
import com.sino.base.constant.db.QueryConstant;
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
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-1-18
 * Time: 上午11:11
 * To change this template use File | Settings | File Templates.
 */
public class TrunListMisQueryServlet extends BaseServlet {
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		Connection conn = null;
		String action = req.getParameter("act");
		action = StrUtil.nullToString(action);
		try {
			conn = DBManager.getDBConnection();
			SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);//从session中获取数据，根据实际情况自行修改。
			EtsWorkorderDTO dtoParameter = null;
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(EtsWorkorderDTO.class.getName());
			dtoParameter = (EtsWorkorderDTO) req2DTO.getDTO(req);
			int organizationId = StrUtil.isEmpty(dtoParameter.getOrganizationId()) ? user.getOrganizationId() : dtoParameter.getOrganizationId();
			//组织
			OptionProducer prd = new OptionProducer(user, conn);
			String ouoption = "";

			if("82".equals(user.getOrganizationId())){
				if("".equals(dtoParameter.getOrganizationId())){
					ouoption = prd.getAllOrganization(0, true);
				}else{
					ouoption = prd.getAllOrganization(organizationId, true);
				}
			}else{
				ouoption = prd.getAllOrganization(organizationId);
			}
			req.setAttribute(WebAttrConstant.OU_OPTION, ouoption);

			String faOption = this.getFAOption( dtoParameter.getFinanceProp() );
			req.setAttribute("FA_OPTION" , faOption );

            String cat = prd.getDictOption(DictConstant.OBJECT_CATEGORY, dtoParameter.getObjectCategory());
			req.setAttribute("CATEGORY", cat);

			SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(req);
			if (action.equals("")) {
				String deptOption = prd.getDeptOptionByOrgId(organizationId, dtoParameter.getDeptCode());//得到地市下的所有领用部门
				req.setAttribute(WebAttrConstant.COUNTY_OPTION, deptOption);
				req.setAttribute(QueryConstant.QUERY_DTO,dtoParameter);
				forwardURL = "/workorder/trunListMisQuery.jsp";
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				String deptOption = prd.getDeptOptionByOrgId(organizationId, dtoParameter.getDeptCode());//得到地市下的所有领用部门
				req.setAttribute(WebAttrConstant.COUNTY_OPTION, deptOption);
				req.setAttribute(QueryConstant.QUERY_DTO,dtoParameter);
				BaseSQLProducer sqlProducer = new TrunListMisQueryModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.produceWebData();
				forwardURL = "/workorder/trunListMisQuery.jsp";
			} else if (action.equals(AssetsActionConstant.EXPORT_ACTION)) {
				TrunListMisQueryDAO dao = new TrunListMisQueryDAO(user, dtoParameter, conn);
				File file = dao.getExportFile(dtoParameter);
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
			}else{
                message = getMessage(MsgKeyConstant.INVALID_REQ);
				message.setIsError(true);
				forwardURL = MessageConstant.MSG_PRC_SERVLET;
            }
		} catch (PoolException e) {
			e.printStackTrace();
		} catch (QueryException e) {
			e.printStackTrace();
		} catch (DTOException e) {
			e.printStackTrace();
		} catch (DataTransException e) {
			e.printStackTrace();
		} catch (WebFileDownException e) {
			e.printStackTrace();
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
		}
	}

	/**
	 * 功能：构造是否下拉框
	 * @param selectedValue String
	 * @return String
	 */
	public String getFAOption(String selectedValue) {
		StringBuffer strOpt = new StringBuffer();
		if (selectedValue == null) {
			selectedValue = "";
		}
		strOpt.append("<option value=\"");
		strOpt.append( "ASSETS" );
		strOpt.append("\"");
		if (selectedValue.equals( "ASSETS" )) {
			strOpt.append(" selected");
		}
		strOpt.append(">资产</option>");
		strOpt.append("<option value=\"");
		strOpt.append( "PRJ_MTL" );
		strOpt.append("\"");
		if (selectedValue.equals( "PRJ_MTL" )) {
			strOpt.append(" selected");
		}
		strOpt.append(">预转资</option>");
		return strOpt.toString();
	}
}
