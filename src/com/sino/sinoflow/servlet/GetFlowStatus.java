package com.sino.sinoflow.servlet;


//import com.sino.base.constant.message.MessageConstant;
//import com.sino.base.constant.message.MsgKeyConstant;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.log.Logger;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.servlet.BaseServlet;
import com.sino.sinoflow.util;
import com.sino.sinoflow.constant.URLDefineList;
import com.sino.sinoflow.constant.WebAttrConstant;
import com.sino.sinoflow.model.flowContentModel;

/**
 * <p>Title: CaseList</p>
 * <p>Description:显示任务列表供用户新建任务</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Hing
 * @version 1.0
 */


public class GetFlowStatus extends BaseServlet {

    /**
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String caseId = util.getReqPara(req, "sf_caseID");
        String appName = util.getReqPara(req, "sf_appName");
        String appDataId = util.getReqPara(req, "sf_appDataID");
        Connection conn = null;
        boolean autocommit = true;
        boolean commitFlag = false;
        String forwardURL = URLDefineList.ERROR_PAGE;
        try {
            conn = getDBConnection(req);
            autocommit = conn.getAutoCommit();
            conn.setAutoCommit(false);

            SQLModel sqlModel;
            if(caseId == null || caseId.equals("")) {
                sqlModel = (new flowContentModel(null, null)).getSaveStatusModelByDataID(appName, appDataId);
            } else {
                sqlModel = (new flowContentModel(null, null)).getSaveStatusModel(caseId);
            }
            SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
            simpleQuery.executeQuery();
            RowSet rowSet;
            if (simpleQuery.hasResult()) {
                rowSet = simpleQuery.getSearchResult();
            } else {
/*
                if(caseId == null || caseId.equals("")) {
                    sqlModel = (new SfActArchiveModel(null, null)).getSaveStatusModelByDataID(appName, appDataId);   
                } else {
                    sqlModel = (new SfActArchiveModel(null, null)).getSaveStatusModel(caseId);
                }
                simpleQuery = new SimpleQuery(sqlModel, conn);
                simpleQuery.executeQuery();
                if(simpleQuery.hasResult()) {
                    rowSet = simpleQuery.getSearchResult();    
                } else {
*/
                    rowSet = null;
//                }
            }
            req.setAttribute(WebAttrConstant.SINOFLOW_STATUS, rowSet);
            forwardURL = "/system/flowContent.jsp";
            commitFlag = true;
        } catch (Exception ex) {
           Logger.logError(ex);
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
            //根据实际情况修改页面跳转代码。
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
        }
	}
}