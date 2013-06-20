package com.sino.ams.system.object.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.constant.AMSActionConstant;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.dto.EtsFaAssetsDTO;
import com.sino.ams.system.basepoint.dto.EtsObjectDTO;
import com.sino.ams.system.object.dao.AssetsNoMatchResponsibleDAO;
import com.sino.ams.system.object.dao.CommonObjectDAO;
import com.sino.ams.system.object.model.AssetsNoMatchResponsibleModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.util.ResUtil;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.StrException;
import com.sino.base.exception.UploadException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.CheckBoxProp;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 陕西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2009</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 			李轶
 * @version 1.0
 */
public class AssetsNoMatchResponsibleServlet extends BaseServlet {
	/**
	 * 所有的Servlet都必须实现的方法。
	 * Function:		查询ETS_ITME_INFO表中责任人为空、责任部门为空、责任人不属于指定的责任部门
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(EtsFaAssetsDTO.class.getName());
			EtsFaAssetsDTO dto = (EtsFaAssetsDTO) req2DTO.getDTO(req);
			String action = dto.getAct();
			conn = getDBConnection(req);
			AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
			String deptOption = "";
			int organizationId = dto.getOrganizationId() == -1 ? user.getOrganizationId() : dto.getOrganizationId();
            String ASSETS_NOMATCH_RESPONSIBLE = "/system/object/assetsNoMatchResponsibleSearch.jsp"; //责任人与部门差异查询
			String orgOption = optProducer.getOrganizationOption(dto.getOrganizationId());
			deptOption =optProducer.getDeptOptionByOrgId(organizationId, dto.getResponsibilityDept());//得到地市下的所有部门
			req.setAttribute(AssetsWebAttributes.CITY_OPTION, orgOption);
		    req.setAttribute(AssetsWebAttributes.DEPT_OPTIONS, deptOption);
			AssetsNoMatchResponsibleDAO dao = new AssetsNoMatchResponsibleDAO(user, dto, conn);
			
			String allResName = ResUtil.getAllResNameByResName(conn,  "责任人与部门差异查询" );
			req.setAttribute( WebAttrConstant.ALL_RES_NAME , allResName );
			
			if (action.equals("")) {
                req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = ASSETS_NOMATCH_RESPONSIBLE;
			} else if (action.equals(AMSActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new AssetsNoMatchResponsibleModel(user, dto);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				pageDAO.produceWebData();
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = ASSETS_NOMATCH_RESPONSIBLE;
			} else if (action.equals(AMSActionConstant.EXPORT_ACTION)) {      //导出到Excel
				File file = dao.getExportFile();
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
		} catch (WebFileDownException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (ContainerException e) {
			e.printStackTrace();
		} finally {
			DBManager.closeDBConnection(conn);
			if(!forwardURL.equals("")){
				setHandleMessage(req, message);
				ServletForwarder forwarder = new ServletForwarder(req, res);
				forwarder.forwardView(forwardURL);
			}
		}
	}
}
