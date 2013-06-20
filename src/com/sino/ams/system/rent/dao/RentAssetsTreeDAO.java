package com.sino.ams.system.rent.dao;

import java.sql.Connection;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsURLList;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.system.rent.model.RentAssetsTreeModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.NodeException;
import com.sino.base.exception.QueryException;
import com.sino.base.treeview.DataBaseTree;
import com.sino.base.treeview.FieldSQLProperty;
import com.sino.base.treeview.Tree;
import com.sino.base.treeview.WebPageTree;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.security.dto.ServletConfigDTO;


/**
 * <p>Title: AmsAssetsPriviServlet</p>
 * <p>Description:程序自动生成服务程序“AmsAssetsPriviServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class RentAssetsTreeDAO extends AMSBaseDAO {
    private String constant = "";
	private String newValue = "";
	/**
	 * 功能：固定租赁资产当前信息(EAM) ETS_FA_ASSETS 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsAssetsAddressVDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public RentAssetsTreeDAO(SfUserDTO userAccount, AmsAssetsAddressVDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	public void setServletConfig(ServletConfigDTO servletConfig){
		super.setServletConfig(servletConfig);
		RentAssetsTreeModel modelProducer = (RentAssetsTreeModel)sqlProducer;
		constant = "a";
		modelProducer.setServletConfig(servletConfig, constant);
		newValue = servletConfig.getProCompanyName();
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		AmsAssetsAddressVDTO dtoPara = (AmsAssetsAddressVDTO)dtoParameter;
		super.sqlProducer = new RentAssetsTreeModel((SfUserDTO)userAccount, dtoPara);
	}


	/**
	 * 功能：获取租赁资产台账管理平台左侧展示树：用户个人租赁资产。
	 * @return String
	 * @throws com.sino.base.exception.QueryException
     */
	public String getPersonalAssetsTree() throws QueryException {
		StringBuffer assetsTree = new StringBuffer();
		RentAssetsTreeModel modelProducer = (RentAssetsTreeModel) sqlProducer;
		SQLModel sqlModel = modelProducer.getPersonalTreeModel();
		assetsTree.append(prodAssetsTree(sqlModel));
		return assetsTree.toString();
	}


	/**
	 * 功能：获取用户调出但对方未接收的租赁资产
	 * @return String
	 * @throws QueryException
	 */
	public String getTransferAssetsTree() throws QueryException {
		StringBuffer assetsTree = new StringBuffer();
		RentAssetsTreeModel modelProducer = (RentAssetsTreeModel) sqlProducer;
		SQLModel sqlModel = modelProducer.getTransferTreeModel();
		assetsTree.append(prodAssetsTree(sqlModel));
		return assetsTree.toString();
	}

	/**
	 * 功能：获取租赁资产台账管理平台左侧展示树：部门租赁资产
	 * @param priviDepts String[] 有权访问的部门
	 * @return String
	 * @throws QueryException
	 */
	public String getDeptTree(String[] priviDepts) throws QueryException {
		StringBuffer assetsTree = new StringBuffer();
		RentAssetsTreeModel modelProducer = (RentAssetsTreeModel) sqlProducer;
		SQLModel sqlModel = modelProducer.getDeptTreeModel(priviDepts);
		assetsTree.append(prodAssetsTree(sqlModel));
		return assetsTree.toString();
	}

	/**
	 * 功能：获取租赁资产台账管理平台左侧展示树：本公司及其下各部门租赁资产
	 * @return String
	 * @throws QueryException
	 */
	public String getCompanyTree() throws QueryException {
		StringBuffer assetsTree = new StringBuffer();
		RentAssetsTreeModel modelProducer = (RentAssetsTreeModel) sqlProducer;
		SQLModel sqlModel = modelProducer.getCompanyTreeModel();
		assetsTree.append(prodAssetsTree(sqlModel));
		return assetsTree.toString();
	}

	/**
	 * 功能：获取租赁资产台账管理平台左侧展示树：全省租赁资产
	 * @return String
	 * @throws QueryException
	 */
	public String getProvinceTree() throws QueryException {
		StringBuffer assetsTree = new StringBuffer();
		RentAssetsTreeModel modelProducer = (RentAssetsTreeModel) sqlProducer;
		SQLModel sqlModel = modelProducer.getProvinceTreeModel();
		assetsTree.append(prodAssetsTree(sqlModel));
		return assetsTree.toString();
	}

	/**
	 * 功能：获取租赁资产台账管理平台左侧展示树：用户个人待确定租赁资产。
	 * @return String
	 * @throws QueryException
	 */
	public String getConfirmAssetsTree() throws QueryException {
		StringBuffer assetsTree = new StringBuffer();
		RentAssetsTreeModel modelProducer = (RentAssetsTreeModel) sqlProducer;
		SQLModel sqlModel = modelProducer.getConfirmTreeModel();
		assetsTree.append(prodAssetsTree(sqlModel));
		return assetsTree.toString();
	}

	/**
	 * 功能：根据不同的SQL构造“租赁资产台账管理”树形结构
	 * @param sqlModel SQLModel
	 * @return StringBuffer
	 * @throws QueryException
	 */
	private StringBuffer prodAssetsTree(SQLModel sqlModel) throws QueryException {
		StringBuffer assetsTree = new StringBuffer();
		try {
			AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO)dtoParameter;
			String treeCategory = dto.getTreeCategory();
			String treeTitle = "";
			if(treeCategory.equals(AssetsWebAttributes.RENT_ASSETS_TREE_PERSON)){//个人租赁资产
				treeTitle = AssetsWebAttributes.RENT_ASSETS_PERSON;
			} else if(treeCategory.equals(AssetsWebAttributes.RENT_ASSETS_TREE_DEPART)){//部门租赁资产
				treeTitle = AssetsWebAttributes.RENT_ASSETS_DEPART;
			} else if(treeCategory.equals(AssetsWebAttributes.RENT_ASSETS_TREE_COMPAN)){//公司租赁资产
				treeTitle = AssetsWebAttributes.RENT_ASSETS_COMPAN;
			} else if(treeCategory.equals(AssetsWebAttributes.RENT_ASSETS_TREE_PROVIN)){//全省租赁资产
				treeTitle = AssetsWebAttributes.RENT_ASSETS_PROVIN;
//			} else if(treeCategory.equals(AssetsWebAttributes.ASSETS_TREE_CONFIRM)){//待确认租赁资产
//				treeTitle = AssetsWebAttributes.ASSETS_CONFIRM;
//			} else if(treeCategory.equals(AssetsWebAttributes.ASSETS_TREE_TRANSFER)){//调出租赁资产
//				treeTitle = AssetsWebAttributes.ASSETS_TRANSFER;
//			} else if(treeCategory.equals(AssetsWebAttributes.ASSETS_TREE_DISCARD)){//报废租赁资产
//				treeTitle = AssetsWebAttributes.ASSETS_DISCARD;
//			} else if(treeCategory.equals(AssetsWebAttributes.ASSETS_TREE_CLEAR)){//处置租赁资产
//				treeTitle = AssetsWebAttributes.ASSETS_CLEAR;
			}
			FieldSQLProperty sqlProperty = new FieldSQLProperty();
			sqlProperty.setSqlModel(sqlModel);
			DataBaseTree treeBuilder = new DataBaseTree(conn, sqlProperty);
			treeBuilder.setTreeName(treeTitle);
			if(treeCategory.equals(AssetsWebAttributes.ASSETS_TREE_PROVIN)){
				treeBuilder.setReplaceValue(constant, newValue);
			}
//			treeBuilder.setMethod("do_AssetsQuery()");
			Tree tree = treeBuilder.getTree();
			WebPageTree webTree = new WebPageTree(tree);
			webTree.setTarFrame("assetsMain");
//			String url = AssetsURLList.ASSETS_QRY_SERVLET;
//			String url = "/servlet/com.sino.ams.newasset.servlet.EtsFaAssetsServlet?act=AssetsActionConstant.QUERY_ACTION;";
            String url = "/servlet/com.sino.ams.system.rent.servlet.AmsRentAssetServlet?act=QUERY_ACTION";
			url += "&treeCategory=" + treeCategory;
			webTree.setUrlPrefix(url);
			assetsTree.append(webTree.getPageJs());
			assetsTree.append(webTree.getTreeDataHtml());
		} catch (NodeException ex) {
			ex.printLog();
			throw new QueryException(ex);
		}
		return assetsTree;
	}

	/**
	 * 功能：获取租赁资产归属范围的默认树形展示。
	 * @return String
	 */
	public String getFirstTreeCategory(){
//		return AssetsWebAttributes.ASSETS_TREE_TYPES[0];
		return AssetsWebAttributes.RENT_ASSETS_TYPES[0];
	}

	/**
	 * 功能:获取地点查询的树
	 * @return String
	 * @throws QueryException
	 */
	public String getLocationQueryTree() throws QueryException {
		StringBuffer assetsTree = new StringBuffer();
		try {
			RentAssetsTreeModel modelProducer = (RentAssetsTreeModel) sqlProducer;
			AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
			SQLModel sqlModel = modelProducer.getCompanyCountyModel();
			FieldSQLProperty sqlProperty = new FieldSQLProperty();
			sqlProperty.setSqlModel(sqlModel);
			DataBaseTree treeBuilder = new DataBaseTree(conn, sqlProperty);
			treeBuilder.setTreeName("地点查询");
			Tree tree = treeBuilder.getTree();
			WebPageTree webTree = new WebPageTree(tree);
			webTree.setTarFrame("assetsMain");
			String url = AssetsURLList.LOCATION_QUERY_SERVLET;
			url += "?act=" + AssetsActionConstant.QUERY_ACTION;
			url += "&treeCategory=" + dto.getTreeCategory();
			webTree.setUrlPrefix(url);
			assetsTree.append(webTree.getPageJs());
			assetsTree.append(webTree.getTreeDataHtml());
		} catch (NodeException ex) {
			ex.printLog();
			throw new QueryException(ex);
		}
		return assetsTree.toString();
	}

	/**
	 * 功能:获取自定义查询的树
	 * @return String
	 */
	public String getCustomQueryTree(){
		StringBuffer queryTree = new StringBuffer();
		queryTree.append("<script language=\"JavaScript\" src=\"/WebLibary/js/MzTreeView10.js\"></script>");
		queryTree.append(WorldConstant.ENTER_CHAR);
		queryTree.append(WorldConstant.TAB_CHAR);
		queryTree.append("<script language=\"JavaScript\">");
		queryTree.append(WorldConstant.ENTER_CHAR);
		queryTree.append(WorldConstant.TAB_CHAR);
		queryTree.append("var tree = new MzTreeView(\"tree\");");
		queryTree.append(WorldConstant.ENTER_CHAR);
		queryTree.append(WorldConstant.TAB_CHAR);
		queryTree.append("with(tree){");
		queryTree.append(WorldConstant.ENTER_CHAR);
		queryTree.append(WorldConstant.TAB_CHAR);
		queryTree.append(WorldConstant.TAB_CHAR);
		queryTree.append("icons[\"property\"] = \"property.gif\";");
		queryTree.append(WorldConstant.ENTER_CHAR);
		queryTree.append(WorldConstant.TAB_CHAR);
		queryTree.append(WorldConstant.TAB_CHAR);
		queryTree.append("icons[\"css\"] = \"collection.gif\";");
		queryTree.append(WorldConstant.ENTER_CHAR);
		queryTree.append(WorldConstant.TAB_CHAR);
		queryTree.append(WorldConstant.TAB_CHAR);
		queryTree.append("icons[\"book\"]  = \"book.gif\";");
		queryTree.append(WorldConstant.ENTER_CHAR);
		queryTree.append(WorldConstant.TAB_CHAR);
		queryTree.append(WorldConstant.TAB_CHAR);
		queryTree.append("iconsExpand[\"book\"] = \"bookopen.gif\";");
		queryTree.append(WorldConstant.ENTER_CHAR);
		queryTree.append(WorldConstant.TAB_CHAR);
		queryTree.append(WorldConstant.TAB_CHAR);
		queryTree.append("setIconPath(\"/images/mzTree/\");");
		queryTree.append(WorldConstant.ENTER_CHAR);
		queryTree.append(WorldConstant.TAB_CHAR);
		queryTree.append(WorldConstant.TAB_CHAR);
		queryTree.append("nodes[\"0_rootId\"]=\"text:租赁资产自定义查询;\";");
		queryTree.append(WorldConstant.ENTER_CHAR);
		queryTree.append(WorldConstant.TAB_CHAR);
		queryTree.append(WorldConstant.TAB_CHAR);
		queryTree.append("nodes[\"rootId_1\"]=\"text:查询定义;url:/servlet/com.sino.ams.newasset.servlet.CustomQuerySetServlet\";");
		queryTree.append(WorldConstant.ENTER_CHAR);
		queryTree.append(WorldConstant.TAB_CHAR);
		queryTree.append(WorldConstant.TAB_CHAR);
		queryTree.append("nodes[\"rootId_2\"]=\"text:租赁资产查询;url:/servlet/com.sino.ams.newasset.servlet.CustomQueryServlet\";");
		queryTree.append(WorldConstant.ENTER_CHAR);
		queryTree.append(WorldConstant.TAB_CHAR);
		queryTree.append(WorldConstant.TAB_CHAR);
		queryTree.append("setTarget(\"assetsMain\");");
		queryTree.append(WorldConstant.ENTER_CHAR);
		queryTree.append(WorldConstant.TAB_CHAR);
		queryTree.append("}");
		queryTree.append(WorldConstant.ENTER_CHAR);
		queryTree.append(WorldConstant.TAB_CHAR);
		queryTree.append("document.write(tree.toString());");
		queryTree.append(WorldConstant.ENTER_CHAR);
		queryTree.append("</script>");
		return queryTree.toString();
	}
}
