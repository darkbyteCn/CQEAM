package com.sino.soa.mis.srv.pagequiryassetcustdetail;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.constant.ResNameConstant;
import com.sino.ams.newasset.constant.AssetsURLList;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.util.ResUtil;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.soa.common.SrvURLDefineList;

public class PagequiryassetcustdetailMainServlet extends BaseServlet {
	/**
	 * <p>Title: SinoEAM</p>
	 * <p>Description: 山西移动实物资产管理系统</p>
	 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
	 * <p>Company: 北京思诺博信息技术有限公司</p>
	 * @author 唐明胜
	 * @version 1.0
	 * Function:读取转资清单分页 MIS/TD入口
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
	       String forwardURL = "";
	        Message message = SessionUtil.getMessage(req);
	        Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
			conn = DBManager.getDBConnection();
			ResUtil.setAllResName(conn, req, ResNameConstant.ITEM_MAINTAIN );
			String isTd= user.getIsTd();
			if(!isTd.equals("Y")){ //MIS
				forwardURL = SrvURLDefineList.CUST_DETAIL_SERVLET;
			}else{  //TD
				forwardURL = SrvURLDefineList.TD_CUST_DETAIL_SERVLET;
			}
		} catch (PoolException e) {
			e.printLog(); 
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (QueryException e) {
			e.printLog(); 
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (ContainerException e) {
			e.printLog(); 
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} finally {
			DBManager.closeDBConnection( conn );
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
//          if (hasError) {
//              forwarder.forwardView(forwardURL);
//          } else {
//              forwarder.forwardView(AssetsURLList.ASSETS_ASSIGN_FRM);
//          }
		}
	}
}
