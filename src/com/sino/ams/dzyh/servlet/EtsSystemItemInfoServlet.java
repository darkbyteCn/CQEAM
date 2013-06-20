package com.sino.ams.dzyh.servlet;


import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.dzyh.OptionProducer.EamProducer;
import com.sino.ams.dzyh.constant.DzyhActionConstant;
import com.sino.ams.dzyh.dao.EtsItemInfoDAO;
import com.sino.ams.dzyh.dto.EtsItemInfoDTO;
import com.sino.ams.dzyh.model.EtsItemInfoModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
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
 * <p>Title: EtsItemInfoServlet</p>
 * <p>Description:程序自动生成服务程序“EtsItemInfoServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Administrator
 * @version 1.0
 */


public class EtsSystemItemInfoServlet extends BaseServlet {

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
			EtsItemInfoDTO dtoParameter=null;
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(EtsItemInfoDTO.class.getName());
			dtoParameter = (EtsItemInfoDTO)req2DTO.getDTO(req);
			conn = getDBConnection(req);
			EtsItemInfoDAO etsItemInfoDAO = new EtsItemInfoDAO(user, dtoParameter, conn);
			EamProducer eamProducer = new EamProducer(user, conn);

		    String parameter=req.getParameter("dzyh");
		    
			if (action.equals("")) {
				forwardURL = URLDefineList.DZYH_TZ_QUERY_PAGE;
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new EtsItemInfoModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.produceWebData();
				
				forwardURL = URLDefineList.DZYH_TZ_QUERY_PAGE;
				if(parameter.equals(DzyhActionConstant.CHAXUN_ACTION)){
					forwardURL +="?dzyh="+DzyhActionConstant.CHAXUN_ACTION;
				}else if(parameter.equals(DzyhActionConstant.WEIHU_ACTION)){
					forwardURL +="?dzyh="+DzyhActionConstant.WEIHU_ACTION;
				}
				
			} else if (action.equals(WebActionConstant.NEW_ACTION)) {
				EtsItemInfoDTO etsItemInfo = (EtsItemInfoDTO)req.getAttribute("");
				if(etsItemInfo == null){
					etsItemInfo= dtoParameter;//表示没有因失败而保持的数据，则产生默认的对象数据，数据由com.sino.ams.dzyh.dto.EtsItemInfoDTO的构造函数确定
				}
				req.setAttribute("", etsItemInfo);
				forwardURL = "";
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
				etsItemInfoDAO.setDTOClassName(EtsItemInfoDTO.class.getName());
				EtsItemInfoDTO etsItemInfo = (EtsItemInfoDTO)etsItemInfoDAO.getDataByPrimaryKey();
				if(etsItemInfo == null){
					etsItemInfo = new EtsItemInfoDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}else{
					req.setAttribute(WebAttrConstant.DZYH_DATA, etsItemInfo);

					String category2Opt=eamProducer.getCategory2Option(dtoParameter.getEiiItemCategory2());
					String itemNameOpt=eamProducer.getItemNameOption(dtoParameter.getEiiItemName());
					String itemSpecOpt=eamProducer.getItemSpecOption(dtoParameter.getEiiItemSpec());
					String deptOpt=eamProducer.getResponsibilityDeptOption(dtoParameter.getEiiDeptName());
					String userOpt=eamProducer.getResponsibilityUserOption(dtoParameter.getEiiUserName());
					String addressOpt=eamProducer.getAddressNameOption(dtoParameter.getEiiWorkorderObjectName());
					
					req.setAttribute(WebAttrConstant.DZYH_CATEGORY2_OPT, category2Opt);
					req.setAttribute(WebAttrConstant.DZYH_ITEM_NAME_OPT, itemNameOpt);
					req.setAttribute(WebAttrConstant.DZYH_ITEM_SPEC_OPT, itemSpecOpt);
					req.setAttribute(WebAttrConstant.DZYH_RESPONSIBILITY_DEPT_OPT, deptOpt);
					req.setAttribute(WebAttrConstant.DZYH_RESPONSIBILITY_USER_OPT, userOpt);
					req.setAttribute(WebAttrConstant.DZYH_ADDRESS_NAME_OPT, addressOpt);
					
					req.setAttribute(WebAttrConstant.DZYH_DATA, etsItemInfo);
					forwardURL = URLDefineList.DZYH_TZ_DETAIL_PAGE;
				}
			} else if (action.equals(WebActionConstant.CREATE_ACTION)) {
				etsItemInfoDAO.createData();
				forwardURL = "";
			} else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
				etsItemInfoDAO.updateData();
				forwardURL = "";
			} else if (action.equals(WebActionConstant.DELETE_ACTION)) {
				etsItemInfoDAO.deleteData();
				forwardURL = "";
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
			//请根据实际情况处理消息
			forwardURL = "保持界面录入的数据，返回到原页面，并显示上面给出的消息";
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
			//根据实际情况修改页面跳转代码。
		}
	}
}