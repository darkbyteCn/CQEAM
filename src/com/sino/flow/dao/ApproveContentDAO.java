package com.sino.flow.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sino.base.constant.WorldConstant;
import com.sino.base.data.RowSet;
import com.sino.base.db.datatrans.DataRange;
import com.sino.base.db.datatrans.DataTransfer;
import com.sino.base.db.datatrans.TransRule;
import com.sino.base.db.datatrans.TransferFactory;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.QueryException;
import com.sino.flow.constant.ReqAttributeList;
import com.sino.flow.model.ApproveContentModel;

/**
 * Created by wwb.
 * User: demo
 * Date: 2006-12-21
 * Time: 12:03:43
 */
public class ApproveContentDAO {
    private Connection conn;
    private HttpServletRequest req;


    public ApproveContentDAO(Connection conn, HttpServletRequest req) {
        this.conn = conn;
        this.req = req;
    }

	/**
	 * 功能：查询应用的审批意见，返回结果集 ；根据应用ID查找审批记录
	 * @param applyId String
	 * @param appTableName String
	 * @throws QueryException
	 */
    public void getApproveContent(String applyId, String appTableName) throws QueryException {
        SimpleQuery sq = new SimpleQuery(ApproveContentModel.getContentModel(applyId, appTableName), conn);
        sq.executeQuery();
        RowSet rs = sq.getSearchResult();
        req.setAttribute(ReqAttributeList.APPROVE_CONTENT_DATA,rs);
    }

	/**
	 * 功能：获取审批意见。用于审批过程中
	 * @param actId String
	 * @throws QueryException
	 */
    public void getApproveContent(String actId) throws QueryException {
        SimpleQuery sq = new SimpleQuery(ApproveContentModel.getContentModel(actId), conn);
        sq.executeQuery();
        RowSet rs = sq.getSearchResult();
        req.setAttribute(ReqAttributeList.APPROVE_CONTENT_DATA,rs);
    }

	/**
	 * 功能：将审批意见导出为excel文件.用于审批过程中
	 * @param actId String
	 * @return
	 * @throws DataTransException
	 */
	public File getExportFile(String actId) throws DataTransException {
		String reportTitle = "审批流转意见";
		String fileName = reportTitle + ".xls";
		TransRule rule = new TransRule();
		SQLModel sqlModel = ApproveContentModel.getContentModel(actId);
		rule.setDataSource(sqlModel);
		rule.setSourceConn(conn);
		String filePath = WorldConstant.USER_HOME;
		filePath += WorldConstant.FILE_SEPARATOR;
		filePath += fileName;
		rule.setTarFile(filePath);
		DataRange range = new DataRange();
		rule.setDataRange(range);
		Map fieldMap = new HashMap();
		fieldMap.put("USER_NAME", "审批人");
		fieldMap.put("APPROVE_TIME", "审批时间");
		fieldMap.put("APPROVE_CONTENT", "审批意见");
		rule.setFieldMap(fieldMap);
		TransferFactory factory = new TransferFactory();
		DataTransfer transfer = factory.getTransfer(rule);
		transfer.transData();
		return (File) transfer.getTransResult();
	}


	/**
	 * 功能：将审批意见导出为excel文件
	 * @param applyId String
	 * @param appTableName String
	 * @return
	 * @throws DataTransException
	 */
	public File getExportFile(String applyId, String appTableName) throws DataTransException {
		String reportTitle = "审批流转意见";
		String fileName = reportTitle + ".xls";
		TransRule rule = new TransRule();
		SQLModel sqlModel = ApproveContentModel.getContentModel(applyId, appTableName);
		rule.setDataSource(sqlModel);
		rule.setSourceConn(conn);
		String filePath = WorldConstant.USER_HOME;
		filePath += WorldConstant.FILE_SEPARATOR;
		filePath += fileName;
		rule.setTarFile(filePath);
		DataRange range = new DataRange();
		rule.setDataRange(range);
		Map fieldMap = new HashMap();
		fieldMap.put("USER_NAME", "审批人");
		fieldMap.put("APPROVE_TIME", "审批时间");
		fieldMap.put("APPROVE_CONTENT", "审批意见");
		rule.setFieldMap(fieldMap);
		TransferFactory factory = new TransferFactory();
		DataTransfer transfer = factory.getTransfer(rule);
		transfer.transData();
		return (File) transfer.getTransResult();
	}
}
