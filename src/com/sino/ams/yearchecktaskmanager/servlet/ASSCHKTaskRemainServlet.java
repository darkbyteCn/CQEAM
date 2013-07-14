package com.sino.ams.yearchecktaskmanager.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.yearchecktaskmanager.dao.ASSCHKTaskRemainDAO;
import com.sino.ams.yearchecktaskmanager.util.AssetsCheckTaskConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;

public class ASSCHKTaskRemainServlet extends BaseServlet {

	public void performTask(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String forwardURL = "";
        Connection conn = null;
        String forward = StrUtil.nullToString(req.getParameter("forward"));
        String taskName = StrUtil.nullToString(req.getParameter("taskName"));
        String orderNumber = StrUtil.nullToString(req.getParameter("orderNumber"));//任务编号
        String orderType = StrUtil.nullToString(req.getParameter("orderType"));//任务类型
        String message = "";
        SfUserDTO userAccount = (SfUserDTO)SessionUtil.getUserAccount(req);
        try{
        	 conn = DBManager.getDBConnection();
        	 ASSCHKTaskRemainDAO taskRemainDAO = new ASSCHKTaskRemainDAO(userAccount,null,conn,req);
        	 if (forward.equals("show_all")) {
        		 taskRemainDAO.ProdAllTitleData(req, conn,taskName);
        		 if(taskName!=null && !taskName.equals("")){
        			 req.setAttribute("taskName", taskName);
        		 }
                 forwardURL = "/yearchecktaskmanager/TaskRemainMore.jsp";
        	 }/*else if(forward.equals("go_page")){
        		 //地市公司资产管理员接受省下发盘点任务
        		 if(orderType.equals(AssetsCheckTaskConstant.ORDER_TYPE_ASS_CHK_TASK)){
        		 }
        	 }*/
        }catch(Exception e){
        	e.printStackTrace();
        }finally {
            try {
                closeDBConnection(conn);
            } catch (Exception e) {
                Logger.logError(e);
            }
            ServletForwarder sf = new ServletForwarder(req, res);
            if (!message.equals("")) {
                sf.forwardOpenerView(forwardURL,message);
            } else {
                sf.forwardView(forwardURL);
            }
        }
	}

}
