package com.sino.ams.workorder.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dao.TrunListDifferentDAO;
import com.sino.ams.workorder.dto.EtsWorkorderDTO;
import com.sino.ams.workorder.model.TrunListDifferentModel;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.PoolException;
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
 * Author:		李轶
 * Date: 2009-6-3
 * Time: 10:25:22
 * Function:	转资清单差异
 */
public class TrunListDifferentServlet extends BaseServlet {
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
			//组织
			OptionProducer prd = new OptionProducer(user, conn);
			String ouoption = prd.getAllOrganization(dtoParameter.getOrganizationId(), true);
			req.setAttribute(WebAttrConstant.OU_OPTION, ouoption);
			
            String cat = prd.getDictOption(DictConstant.OBJECT_CATEGORY, dtoParameter.getObjectCategory());
			req.setAttribute("CATEGORY", cat);

			SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(req);
			if (action.equals("")) {
				req.setAttribute(QueryConstant.QUERY_DTO,dtoParameter);
				forwardURL = URLDefineList.TRUN_LIST_DIFFERENT;
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				req.setAttribute(QueryConstant.QUERY_DTO,dtoParameter);
				BaseSQLProducer sqlProducer = new TrunListDifferentModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.produceWebData();
				forwardURL = URLDefineList.TRUN_LIST_DIFFERENT;
			} else if (action.equals(AssetsActionConstant.EXPORT_ACTION)) {
				TrunListDifferentDAO dao = new TrunListDifferentDAO(user, dtoParameter, conn);
				File file = dao.getExportFile(dtoParameter);
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
			}  else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
				TrunListDifferentDAO dao = new TrunListDifferentDAO(user, dtoParameter, conn);
            	this.getDiffProcessDescs(req, dtoParameter, dao);
				message = dao.getMessage();
				message = getMessage(MsgKeyConstant.UPDATE_DATA_SUCCESS);
				message.addParameterValue("差异处理原因");
            	forwardURL = "/servlet/com.sino.ams.workorder.servlet.TrunListDifferentServlet?act=" + WebActionConstant.QUERY_ACTION;
            } else{
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
		} catch (UploadException e) {
			e.printStackTrace();
		} catch (DataHandleException e) {
			e.printStackTrace();
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
		}
	}
	
	private void getDiffProcessDescs(HttpServletRequest req, EtsWorkorderDTO dto, TrunListDifferentDAO dao) throws UploadException, DataHandleException {
        try {
            CheckBoxProp checkProp = new CheckBoxProp("subCheck");
            checkProp.setIgnoreOtherField(true);
            RequestParser reqParser = new RequestParser();
            reqParser.setCheckBoxProp(checkProp);
            reqParser.transData(req);
            int num = Integer.parseInt(req.getParameter("num"));
            String diffProcessDesc = "";
            for(int i = 0; i < num; i++){
            	diffProcessDesc = req.getParameter("diffProcessDesc" + i);
            	if(!diffProcessDesc.trim().equals("")){
            		String barcode = req.getParameter("barcode" + i);
            		if(!barcode.equals("")){
            			dto.setBarcode(barcode);
                		dto.setDiffProcessDesc(diffProcessDesc);
                		dao.createData();
            		}
            	}
            }
        } catch (StrException ex) {
            ex.printLog();
            throw new UploadException(ex);
        } 
	}
}
