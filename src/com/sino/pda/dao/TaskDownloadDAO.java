package com.sino.pda.dao;

import java.sql.Connection;

import com.sino.base.constant.WorldConstant;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.pda.model.TaskDownloadModel;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class TaskDownloadDAO {
	private Connection conn = null;
	private TaskDownloadModel downloadModel = null;
	private SimpleQuery simp = null;

	public TaskDownloadDAO(int organizationId, Connection conn) {
		this.conn = conn;
		downloadModel = new TaskDownloadModel(organizationId);
	}

	public StringBuffer getTaskXML(){
		StringBuffer taskXML = new StringBuffer();
		taskXML.append("<?xml version=\"1.0\" encoding=\"GB2312\"?> ");
		taskXML.append(WorldConstant.ENTER_CHAR);
		taskXML.append("<tasks>");
		try {
			if (isValidOrg()) {
				SQLModel sqlModel = downloadModel.getTaskDownloadModel();
				simp.setSql(sqlModel);
				simp.executeQuery();
				RowSet rows = simp.getSearchResult();
				int rowCount = rows.getSize();
				Row row = null;
				for(int i = 0; i < rowCount; i++){
					taskXML.append(WorldConstant.ENTER_CHAR);
					taskXML.append(WorldConstant.TAB_CHAR);
					row = rows.getRow(i);
					taskXML.append("<task taskNo=\"");
					taskXML.append(row.getValue("TASK_NO"));
					taskXML.append("\" taskName=\"");
					taskXML.append(row.getValue("TASK_NAME"));
					taskXML.append("\" Enabled=\"");
					taskXML.append(row.getValue("ENABLED"));
					taskXML.append("\"/>");
				}
			} else {
				taskXML.append(WorldConstant.ENTER_CHAR);
				taskXML.append(WorldConstant.TAB_CHAR);
				taskXML.append("false");
			}
		} catch (Exception ex) {
			Logger.logError(ex);
			taskXML.append(WorldConstant.ENTER_CHAR);
			taskXML.append(WorldConstant.TAB_CHAR);
			taskXML.append("false");
		} finally {
			taskXML.append(WorldConstant.ENTER_CHAR);
			taskXML.append("</tasks>");
		}
		return taskXML;
	}

	/**
	 * 功能：判断传入的OU组织是否合法
	 * @return boolean
	 */
	private boolean isValidOrg(){
		boolean isValidOrg = false;
		try {
			SQLModel sqlModel = downloadModel.getOrgValidModel();
			simp = new SimpleQuery(sqlModel, conn);
			simp.executeQuery();
			isValidOrg = simp.hasResult();
		} catch (QueryException ex) {
			ex.printLog();
		}
		return isValidOrg;
	}
}
