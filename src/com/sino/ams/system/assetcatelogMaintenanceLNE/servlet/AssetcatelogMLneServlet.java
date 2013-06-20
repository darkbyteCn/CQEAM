package com.sino.ams.system.assetcatelogMaintenanceLNE.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.constant.AMSActionConstant;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.system.assetcatelogMaintenanceLNE.dao.AssetcatelogMlneDAO;
import com.sino.ams.system.assetcatelogMaintenanceLNE.dto.AssetcatelogMLneDTO;
import com.sino.ams.system.assetcatelogMaintenanceLNE.model.AssetcatelogMlneModel;
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
import com.sino.base.exception.StrException;
import com.sino.base.exception.UploadException;
import com.sino.base.message.Message;
import com.sino.base.web.CheckBoxProp;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

public class AssetcatelogMLneServlet extends BaseServlet{

	@Override
	public void performTask(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
				Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(AssetcatelogMLneDTO.class.getName());
			AssetcatelogMLneDTO dto = (AssetcatelogMLneDTO) req2DTO.getDTO(req);
			String action = dto.getAct();
			conn = getDBConnection(req);
			AssetcatelogMlneDAO dao=new AssetcatelogMlneDAO(user, dto, conn);
			if (action.equals("")) {
                req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = "/system/assetcatelogMaintenanceLNE/assetcatelogMLne.jsp";
			}else if (action.equals(AMSActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new AssetcatelogMlneModel(user, dto);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				pageDAO.produceWebData();
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = "/system/assetcatelogMaintenanceLNE/assetcatelogMLne.jsp";
			} else if (action.equals("create")) {
            	forwardURL = "/system/assetcatelogMaintenanceLNE/assetcatelogMLneCreate.jsp";
            } else if (action.equals(AMSActionConstant.SAVE_ACTION)) {
            	dao.createData();
				forwardURL = "/system/assetcatelogMaintenanceLNE/assetcatelogMLne.jsp";
			} else if (action.equals(WebActionConstant.DELETE_ACTION)){
				this.getIds(req, dto);
				String[] tmp = dto.getContentCode().split(",");
				for (int i = 0; i < tmp.length; i++) {
					dto.setContentCode(tmp[i].substring(0, tmp[i].indexOf("|")));
					dto.setMatchCode(tmp[i].substring(tmp[i].indexOf("|")+1,tmp[i].length()));
				dao.deleteData();
				}
            	forwardURL = "/system/assetcatelogMaintenanceLNE/assetcatelogMLne.jsp";
			} else if (action.equals("selectAsset")){
            	forwardURL = "/system/assetcatelogMaintenanceLNE/assetcatelogSelectA.jsp";
			} else {
				message = getMessage(MsgKeyConstant.INVALID_REQ);
				message.setIsError(true);
				forwardURL = MessageConstant.MSG_PRC_SERVLET;
			}

		} catch (DataHandleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PoolPassivateException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.POOL_PASSIVATE_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (UploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DTOException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.DTO_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		}catch (QueryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBManager.closeDBConnection(conn);
			if(!forwardURL.equals("")){
				setHandleMessage(req, message);
				ServletForwarder forwarder = new ServletForwarder(req, res);
				forwarder.forwardView(forwardURL);
			}
		}
	}
	private void getIds(HttpServletRequest req, AssetcatelogMLneDTO dto) throws UploadException {
        try {
            CheckBoxProp checkProp = new CheckBoxProp("subCheck");
            checkProp.setIgnoreOtherField(true);
            RequestParser reqParser = new RequestParser();
            reqParser.setCheckBoxProp(checkProp);
            reqParser.transData(req);
            String[] exarr = reqParser.getParameterValues("coutentCode");
            if (exarr != null) {
                StringBuffer coutentCodes = new StringBuffer();
                for (int i = 0; i < exarr.length; i++) {
                	coutentCodes.append(exarr[i] + ",");
                }
                dto.setContentCode((coutentCodes.substring(0, coutentCodes.length()-1)));
            }
        } catch (StrException ex) {
            ex.printLog();
            throw new UploadException(ex);
        } 
  }

}
