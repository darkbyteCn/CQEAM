package com.sino.ams.dzyh.dao;

import java.sql.Connection;

import com.sino.ams.constant.URLDefineList;
import com.sino.ams.dzyh.dto.EamDhCatalogSetDTO;
import com.sino.ams.dzyh.dto.EamDhCatalogValuesDTO;
import com.sino.ams.dzyh.model.EamDhCatalogTreeModel;
import com.sino.base.constant.TreeConstant;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.NodeException;
import com.sino.base.exception.QueryException;
import com.sino.base.treeview.RelateNode;
import com.sino.base.treeview.RelateTree;
import com.sino.base.treeview.StyleProperty;
import com.sino.base.treeview.Tree;
import com.sino.base.treeview.WebPageTree;
/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class EamDhCatalogTreeDAO {

	private Connection conn = null;
	private EamDhCatalogTreeModel treeModel = null;

	public EamDhCatalogTreeDAO(Connection conn) {
		this.conn = conn;
		treeModel = new EamDhCatalogTreeModel();
	}

	/**
	 * 功能：获取低值易耗的树形展示
	 * @return WebPageTree
	 * @throws QueryException
	 */
	public WebPageTree getDzyhTree() throws QueryException {
		WebPageTree webTree = null;
		try {
			DTOSet dzyhSet = getAllDzyhSets();
			if (dzyhSet != null && !dzyhSet.isEmpty()) {
				RelateNode rootNode = new RelateNode();
				rootNode.setDescript("低值易耗目录");
				String urlTemplate = URLDefineList.DZYHCATALOG_QUERYRY_SERVLET;
				String url = "";
				for (int i = 0; i < dzyhSet.getSize(); i++) {
					EamDhCatalogSetDTO parent = (EamDhCatalogSetDTO) dzyhSet.getDTO(i);
					RelateNode parentNode = new RelateNode(parent.getCatlogSetId());
					parentNode.setDescript(parent.getSetName());
					url = urlTemplate
						  + "&catalogSetId="
						  + parent.getCatlogSetId()
						  + "&catalogSetName="
						  + parent.getSetName();
					parentNode.setTarURL(url);
					DTOSet dzyhs = getDzyhBySetId(parent.getCatlogSetId());
					for (int j = 0; j < dzyhs.getSize(); j++) {
						EamDhCatalogValuesDTO child = (EamDhCatalogValuesDTO) dzyhs.getDTO(j);
						RelateNode childNode = new RelateNode(child.getCatalogValueId());
						childNode.setDescript(child.getCatalogName());
						url = urlTemplate +"&catalogValueId=" + child.getCatalogValueId();
						childNode.setTarURL(url);
						parentNode.addChild(childNode);
					}
					rootNode.addChild(parentNode);
				}
				Tree tree = new RelateTree(rootNode);
				webTree = new WebPageTree(tree, TreeConstant.SIMPLE_DIV_TREE);
				StyleProperty styleProperty = new StyleProperty();
				styleProperty.setShowParURL(true);
				webTree.setStyleProperty(styleProperty);
				webTree.setTarFrame("right");
				webTree.setStyleProperty(styleProperty);
//				webTree.setUrlPrefix(url);
			}
		} catch (NodeException ex) {
			ex.printLog();
			throw new QueryException(ex);
		}
		return webTree;
	}


	/**
	 * 功能：获取所有低值易耗分类
	 * @return EamDhCatalogSetDTO
	 * @throws QueryException
	 */
	public DTOSet getAllDzyhSets() throws QueryException {
		DTOSet dictSet = new DTOSet();
		SQLModel sqlModel = treeModel.getAllDzyhSetModel();
		SimpleQuery simp = new SimpleQuery(sqlModel, conn);
		simp.setDTOClassName(EamDhCatalogSetDTO.class.getName());
		simp.executeQuery();
		dictSet = simp.getDTOSet();
		return dictSet;
	}


	/**
	 * 功能：获取所有低值易耗相应目录
	 * @param setId String
	 * @return DTOSet
	 * @throws QueryException
	 */
	public DTOSet getDzyhBySetId(String setId) throws QueryException {
		DTOSet dzyhs = new DTOSet();
		SQLModel sqlModel = treeModel.getDzyhBySetIdModel(setId);
		SimpleQuery simp = new SimpleQuery(sqlModel, conn);
		simp.setDTOClassName(EamDhCatalogValuesDTO.class.getName());
		simp.executeQuery();
		dzyhs = simp.getDTOSet();
		return dzyhs;
	}


	/**
	 * 功能：获取第一个低值易耗分类
	 * @return EamDhCatalogSetDTO
	 * @throws QueryException
	 */
	public EamDhCatalogSetDTO getFirstDzyhSet() throws QueryException {
		EamDhCatalogSetDTO firstDzyhSet = new EamDhCatalogSetDTO();
		DTOSet dzyhSet = getAllDzyhSets();
		if(dzyhSet != null && !dzyhSet.isEmpty()){
			firstDzyhSet = (EamDhCatalogSetDTO)dzyhSet.getDTO(0);
		}
		return firstDzyhSet;
	}

}
