package com.sino.ams.sampling.dao;

import java.sql.Connection;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.sampling.constant.SamplingActions;
import com.sino.ams.sampling.constant.SamplingURLs;
import com.sino.ams.sampling.dto.AmsAssetsSamplingBatchDTO;
import com.sino.ams.sampling.dto.AmsAssetsSamplingTaskDTO;
import com.sino.ams.sampling.model.TaskBatchTreeModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.NodeException;
import com.sino.base.exception.QueryException;
import com.sino.base.treeview.DataBaseTree;
import com.sino.base.treeview.FieldSQLProperty;
import com.sino.base.treeview.Tree;
import com.sino.base.treeview.WebPageTree;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: SinoAMS</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class TaskBatchTreeDAO extends AMSBaseDAO {

	public TaskBatchTreeDAO(SfUserDTO userAccount, AmsAssetsSamplingTaskDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器baseSQLProducer的初始化。具体的DAO继承时初始化具体的SQL生成器。
	 * @param userAccount BaseUserDTO
	 * @param dtoParameter DTO
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		SfUserDTO user = (SfUserDTO)userAccount;
		AmsAssetsSamplingTaskDTO dto = (AmsAssetsSamplingTaskDTO)dtoParameter;
		sqlProducer = new TaskBatchTreeModel(user, dto);
	}

	/**
	 * 功能：构造抽查任务树
	 * @return StringBuffer
	 * @throws QueryException
	 */
	public StringBuffer getTaskTree() throws QueryException {
		StringBuffer taskTree = new StringBuffer();
		try {
			TaskBatchTreeModel modelProducer = (TaskBatchTreeModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getTaskTreeModel();
			FieldSQLProperty sqlProperty = new FieldSQLProperty();
			sqlProperty.setSqlModel(sqlModel);
			DataBaseTree treeBuilder = new DataBaseTree(conn, sqlProperty);
			treeBuilder.setTreeName("抽查任务");
			Tree tree = treeBuilder.getTree();
			WebPageTree webTree = new WebPageTree(tree);
			webTree.setTarFrame("orderMain");
			String url = SamplingURLs.BATCH_ORDER_SERVLET;
			url += "?act=" + SamplingActions.QUERY_ACTION;
			webTree.setUrlPrefix(url);
			taskTree.append(webTree.getPageJs());
			taskTree.append(webTree.getTreeDataHtml());
		} catch (NodeException ex) {
			ex.printLog();
			throw new QueryException(ex);
		}
		return taskTree;
	}

	/**
	 * 功能：获取第一个工单批
	 * @return AmsAssetsSamplingBatchDTO
	 * @throws QueryException
	 */
	public AmsAssetsSamplingBatchDTO getFirstBatch() throws QueryException {
		AmsAssetsSamplingBatchDTO firstBatch = null;
		TaskBatchTreeModel modelProducer = (TaskBatchTreeModel) sqlProducer;
		SQLModel sqlModel = modelProducer.getTaskTreeModel();
		SimpleQuery simp = new SimpleQuery(sqlModel, conn);
		simp.setDTOClassName(AmsAssetsSamplingBatchDTO.class.getName());
		simp.executeQuery();
		firstBatch = (AmsAssetsSamplingBatchDTO)simp.getFirstDTO();
		return firstBatch;
	}
}
