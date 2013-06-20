package com.sino.ams.newasset.dao;

import java.sql.Connection;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.constant.AssetsURLList;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.newasset.model.itemFrmTreeModel;
import com.sino.ams.system.user.dto.SfUserDTO;
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
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-3-14
 * Time: 12:04:30
 * To change this template use File | Settings | File Templates.
 */
public class itemFrmTreeDAO extends AMSBaseDAO {

	public itemFrmTreeDAO(SfUserDTO userAccount, AmsAssetsAddressVDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		SfUserDTO user = (SfUserDTO)userAccount;
		AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO)dtoParameter;
		sqlProducer = new itemFrmTreeModel(user, dto);
	}

	/**
	 * 功能：构造抽查任务树
	 * @return StringBuffer
	 * @throws com.sino.base.exception.QueryException
	 */
	public StringBuffer getItemTree() throws QueryException {
		StringBuffer taskTree = new StringBuffer();
		try {
			itemFrmTreeModel modelProducer = (itemFrmTreeModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getDeptTreeModel();
			FieldSQLProperty sqlProperty = new FieldSQLProperty();
			sqlProperty.setSqlModel(sqlModel);
			DataBaseTree treeBuilder = new DataBaseTree(conn, sqlProperty);
			treeBuilder.setTreeName("公司部门");
			Tree tree = treeBuilder.getTree();
			WebPageTree webTree = new WebPageTree(tree);
			webTree.setTarFrame("orderMain");
            String url = AssetsURLList.ITEM_MAINTAIN_SERVLET3;
            url += "?act=";
            webTree.setUrlPrefix(url);
			taskTree.append(webTree.getPageJs());
			taskTree.append(webTree.getTreeDataHtml());
		} catch (NodeException ex) {
			ex.printLog();
			throw new QueryException(ex);
		}
		return taskTree;
	}
}

