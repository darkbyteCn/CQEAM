package com.sino.ams.newasset.servlet;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;

import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.dao.ApproveContentDAO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.data.RowSet;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.framework.servlet.BaseServlet;

/**
 * 
 * @系统名称: 
 * @功能描述: 资产事务流程转向基础类
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本：1.0
 * @开发作者: sj
 * @创建时间: Nov 1, 2011
 */
public abstract class AssetsTransFlowBaseServlet extends BaseServlet {
	
	/**
	 * @功能描述 增加流程列表
	 * @param req
	 * @param conn
	 * @param loseDTO
	 * @throws QueryException
	 */
	public void doApproveContent(HttpServletRequest req, Connection conn,
			String transId ) throws QueryException {
		String tableName = "AMS_ASSETS_TRANS_HEADER";
		RowSet rows = null;
		rows = ApproveContentDAO.getApproveContent(conn,  transId , tableName);
		req.setAttribute(AssetsWebAttributes.APPROVE_CONTENT, rows);
	}
	
	public void setHandleMessage( HttpServletRequest req,  Message message){
		req.setAttribute(MessageConstant.MESSAGE_DATA, message );
	}
}
