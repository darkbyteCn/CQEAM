package com.sino.ams.workorder.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.system.user.dto.SfGroupDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;

/**
 * User: zhoujs
 * Date: 2007-10-31
 * Time: 13:55:46
 * Function:Ñ¡Ôñ×¨Òµ
 */
public class ChooseCategoryServlet extends BaseServlet {

	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		Connection conn = null;

		try {
			SfUserDTO userAccount=(SfUserDTO) SessionUtil.getUserAccount(req);
			conn = DBManager.getDBConnection();
			SQLModel sqlModel=new SQLModel();
			List sqlArgs=new ArrayList();
			String sqlStr= "SELECT"
						   + " *"
						   + " FROM"
						   + " SF_GROUP SG"
						   + " WHERE"
						   + " EXISTS ("
						   + " SELECT"
						   + " 1"
						   + " FROM"
						   + " SF_USER SU,"
						   + " SF_USER_RIGHT SUR"
						   + " WHERE"
						   + " SU.USER_ID = SUR.USER_ID"
						   + " AND SUR.GROUP_ID = SG.GROUP_ID"
						   + " AND SU.USER_ID = ?)"
						   + "";
			sqlArgs.add(userAccount.getUserId());
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
			SimpleQuery simpleQuery=new SimpleQuery(sqlModel,conn);
			simpleQuery.setDTOClassName(SfGroupDTO.class.getName());
			simpleQuery.executeQuery();
			DTOSet groupDTOSet=new DTOSet();
			if (simpleQuery.hasResult()) {
				groupDTOSet=simpleQuery.getDTOSet();
			}
			req.setAttribute(WebAttrConstant.GROUP_DTOSET,groupDTOSet);
			forwardURL="/workorder/util/chooseCategoryGroup.jsp";
		} catch (PoolException ex) {
			ex.printLog();
		} catch (QueryException ex) {
			ex.printLog();
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
		}
	}
}
