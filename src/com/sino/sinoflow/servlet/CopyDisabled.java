package com.sino.sinoflow.servlet;


//import com.sino.base.constant.message.MessageConstant;
//import com.sino.base.constant.message.MsgKeyConstant;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.db.conn.DBManager;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.log.Logger;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.sinoflow.dto.SfActCopyDTO;
import com.sino.sinoflow.model.SfActCopyModel;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;

/**
 * <p>Title: CaseList</p>
 * <p>Description:显示任务列表供用户新建任务</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Hing
 * @version 1.0
 */


public class CopyDisabled extends BaseServlet {

    private static final String m_sContentType = "text/html; charset=GBK";
    /**
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType(m_sContentType);
        PrintWriter resout = res.getWriter();
        int id = Integer.parseInt(req.getParameter("id"));
        Connection conn = null;
        boolean autocommit = true;
        boolean commitFlag = false;
        try {
			SfUserBaseDTO user = (SfUserBaseDTO)SessionUtil.getUserAccount(req);
            SfActCopyDTO copy = new SfActCopyDTO();
            conn = getDBConnection(req);
            autocommit = conn.getAutoCommit();
            conn.setAutoCommit(false);

            copy.setCopyId(id);
            SQLModel sqlModel = (new SfActCopyModel(user, copy)).setDisableCopyModel(id);
            DBOperator.updateRecord(sqlModel, conn);
            resout.write("Sign Success!");
            commitFlag = true;
        } catch (Exception ex) {
            Logger.logError(ex);
            resout.write("[{status:'ERROR', message:'抄送案件已读錯误!'}]");
        } finally {
            if(conn != null) {
                try {
                    if(commitFlag)
                        conn.commit();
                    else
                        conn.rollback();
                    conn.setAutoCommit(autocommit);
                } catch(SQLException ex) {
                    Logger.logError(ex);
                }
	    		DBManager.closeDBConnection(conn);
            }
        }
	}
}