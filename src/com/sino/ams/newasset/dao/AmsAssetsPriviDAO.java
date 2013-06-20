package com.sino.ams.newasset.dao;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.SQLException;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.constant.AssetsURLList;
import com.sino.ams.newasset.dto.AmsAssetsPriviDTO;
import com.sino.ams.newasset.dto.AmsMisDeptDTO;
import com.sino.ams.newasset.model.AmsAssetsPriviModel;
import com.sino.ams.system.user.dao.EtsOuCityMapDAO;
import com.sino.ams.system.user.dto.SfRoleDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.HandlerException;
import com.sino.base.exception.NodeException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.StrException;
import com.sino.base.log.Logger;
import com.sino.base.treeview.DataBaseTree;
import com.sino.base.treeview.FieldSQLProperty;
import com.sino.base.treeview.Tree;
import com.sino.base.treeview.WebPageTree;
import com.sino.base.util.ReflectionUtil;
import com.sino.base.web.DatabaseForWeb;
import com.sino.base.web.EventHandler;
import com.sino.base.web.EventHandlers;
import com.sino.base.web.WebRadio;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.security.dto.ServletConfigDTO;

/**
 * <p>Title: AmsAssetsPriviDAO</p>
 * <p>Description:程序自动生成服务程序“AmsAssetsPriviDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class AmsAssetsPriviDAO extends AMSBaseDAO {
	private String constant = "";
	private String newValue = "";


	/**
	 * 功能：资产管理权限表(EAM) AMS_ASSETS_PRIVI 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsAssetsPriviDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public AmsAssetsPriviDAO(SfUserDTO userAccount,
							 AmsAssetsPriviDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	public void setServletConfig(ServletConfigDTO servletConfig) {
		super.setServletConfig(servletConfig);
		AmsAssetsPriviModel modelProducer = (AmsAssetsPriviModel) sqlProducer;
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
		AmsAssetsPriviDTO dtoPara = (AmsAssetsPriviDTO) dtoParameter;
		super.sqlProducer = new AmsAssetsPriviModel((SfUserDTO) userAccount,
				dtoPara);
	}

	/**
	 * 功能：删除资产权限数据
	 * @param priviIds String[]
	 * @return boolean
	 */
	public boolean deletePrivis(String[] priviIds) {
		boolean operateResult = false;
		try {
			AmsAssetsPriviModel modelProducer = (AmsAssetsPriviModel)
												sqlProducer;
			SQLModel sqlModel = modelProducer.getPriviDeleteModel(priviIds);
			DBOperator.updateRecord(sqlModel, conn);
			operateResult = true;
			prodMessage(AssetsMessageKeys.PRIVI_DELETE_SUCCESS);
		} catch (DataHandleException ex) {
			ex.printLog();
			prodMessage(AssetsMessageKeys.PRIVI_DELETE_FAILURE);
		}
		return operateResult;
	}

	/**
	 * 功能：获取资产管理员权限的单选按钮视图
	 * @return String
	 * @throws QueryException
	 */
	public String getPriviRoleRadio() throws QueryException {
		StringBuffer priviRadio = new StringBuffer();
		try {
			AmsAssetsPriviModel modelProducer = (AmsAssetsPriviModel)
												sqlProducer;
			SQLModel sqlModel = modelProducer.getPriviRoleModel();
			SimpleQuery simp = new SimpleQuery(sqlModel, conn);
			simp.executeQuery();
			RowSet rows = simp.getSearchResult();
			String value = "";
			String caption = "";
			Row row = null;
			for (int i = 0; i < rows.getSize(); i++) {
				row = rows.getRow(i);
				WebRadio webRadio = new WebRadio("roleId");
				value = row.getStrValue("ROLE_ID");
				caption = row.getStrValue("ROLE_NAME");
				value += "_" + caption;
				webRadio.addValueCaption(value, caption);
				webRadio.setFontColor("#FFFFFF");
				webRadio.setFontSize(2);
				EventHandler handler = new EventHandler();
				handler.setEventName("onClick");
				handler.setFunName("do_ChageRoleId");
				EventHandlers handlers = new EventHandlers();
				handlers.addHandler(handler);
				webRadio.addEventHandlers(handlers);
				priviRadio.append(webRadio.toString());
			}
		} catch (StrException ex) {
			ex.printLog();
			throw new QueryException(ex);
		} catch (HandlerException ex) {
			ex.printLog();
			throw new QueryException(ex);
		} catch (ContainerException ex) {
			ex.printLog();
			throw new QueryException(ex);
		}
		return priviRadio.toString();
	}

	/**
	 * 功能：获取第一个资产管理员角色
	 * @return AmsAssetsPriviDTO
	 * @throws QueryException
	 */
	public SfRoleDTO getPriviFirstRole() throws QueryException {
		AmsAssetsPriviModel modelProducer = (AmsAssetsPriviModel) sqlProducer;
		SQLModel sqlModel = modelProducer.getFirstRoleModel();
		SimpleQuery simp = new SimpleQuery(sqlModel, conn);
		simp.setDTOClassName(SfRoleDTO.class.getName());
		simp.executeQuery();
		return (SfRoleDTO) simp.getFirstDTO();
	}

	/**
	 * 功能：产生部门的树形结构
	 * @return String
	 * @throws QueryException
	 */
	public String getDeptTree() throws QueryException {
		StringBuffer deptTree = new StringBuffer();
		try {
			AmsAssetsPriviDTO dto = (AmsAssetsPriviDTO) dtoParameter;
			AmsAssetsPriviModel modelProducer = (AmsAssetsPriviModel)
												sqlProducer;
			SQLModel sqlModel = modelProducer.getDeptTreeModel();
			FieldSQLProperty sqlProperty = new FieldSQLProperty();
			sqlProperty.setSqlModel(sqlModel);
			DataBaseTree treeBuilder = new DataBaseTree(conn, sqlProperty);
			treeBuilder.setTreeName("资产管理权限维护");
			treeBuilder.setReplaceValue(constant, newValue);
			Tree tree = treeBuilder.getTree();
			WebPageTree webTree = new WebPageTree(tree);
			webTree.setTarFrame("priviMain");
			String url = AssetsURLList.PRIVI_RIGHT_SERVLET;
			url += "?roleId=" + dto.getRoleId() + "&roleName=" +
					URLEncoder.encode(dto.getRoleName(), "GBK");
			webTree.setUrlPrefix(url);
			deptTree.append(webTree.getPageJs());
			deptTree.append(webTree.getTreeDataHtml());
		} catch (NodeException ex) {
			ex.printLog();
			throw new QueryException(ex);
		} catch (UnsupportedEncodingException ex) {
			Logger.logError(ex);
			throw new QueryException(ex);
		}
		return deptTree.toString();
	}

	/**
	 * 功能：产生地市公司的树形结构
	 * @return String
	 * @throws QueryException
	 */
	public String getCompanyTree() throws QueryException {
		StringBuffer deptTree = new StringBuffer();
		try {
			AmsAssetsPriviDTO dto = (AmsAssetsPriviDTO) dtoParameter;
			AmsAssetsPriviModel modelProducer = (AmsAssetsPriviModel)
												sqlProducer;
			SQLModel sqlModel = modelProducer.getCompanyTreeModel();
			FieldSQLProperty sqlProperty = new FieldSQLProperty();
			sqlProperty.setSqlModel(sqlModel);
			DataBaseTree treeBuilder = new DataBaseTree(conn, sqlProperty);
			treeBuilder.setTreeName("资产管理权限维护");
			treeBuilder.setReplaceValue(constant, newValue);
			Tree tree = treeBuilder.getTree();
			WebPageTree webTree = new WebPageTree(tree);
			webTree.setTarFrame("priviMain");
			String url = AssetsURLList.PRIVI_RIGHT_SERVLET;
			url += "?roleId=" + dto.getRoleId() + "&roleName=" +
					dto.getRoleName();
			webTree.setUrlPrefix(url);
			deptTree.append(webTree.getPageJs());
			deptTree.append(webTree.getTreeDataHtml());
		} catch (NodeException ex) {
			ex.printLog();
			throw new QueryException(ex);
		}
		return deptTree.toString();
	}

	/**
	 * 功能：产生省公司的树形结构
	 * @return String
	 * @throws QueryException
	 */
	public String getProvinceTree() throws QueryException {
		AmsAssetsPriviDTO dto = (AmsAssetsPriviDTO) dtoParameter;
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
		queryTree.append("nodes[\"0_rootId\"]=\"text:资产管理权限维护;\";");
		queryTree.append(WorldConstant.ENTER_CHAR);
		queryTree.append(WorldConstant.TAB_CHAR);
		queryTree.append(WorldConstant.TAB_CHAR);
		queryTree.append("nodes[\"rootId_1\"]=\"text:");
		queryTree.append(servletConfig.getProCompanyName());
		queryTree.append(";data:roleId=");
		queryTree.append(dto.getRoleId());
		queryTree.append("&roleName=");
		queryTree.append(dto.getRoleName());
		queryTree.append("&companyName=");
		queryTree.append(getProvOuName());
		queryTree.append(
				";url:/servlet/com.sino.ams.newasset.servlet.PriviRightServlet\";");
		queryTree.append(WorldConstant.ENTER_CHAR);
		queryTree.append(WorldConstant.TAB_CHAR);
		queryTree.append(WorldConstant.TAB_CHAR);
		queryTree.append("setTarget(\"priviMain\");");
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

	/**
	 * 功能：产生待选用户下拉列表
	 * @return String
	 * @throws QueryException
	 */
	public String getAllUsersOption() throws QueryException {
		AmsAssetsPriviModel modelProducer = (AmsAssetsPriviModel) sqlProducer;
		SQLModel sqlModel = modelProducer.getAllUsersModel();
		DatabaseForWeb optProducer = new DatabaseForWeb(sqlModel, conn);
		return optProducer.getOptionHtml();
	}


	/**
	 * 功能：产生已选择用户下拉列表
	 * @param userIds 已选中用户列表
	 * @return String
	 * @throws QueryException
	 */
	public String getExistUsersOption(String[] userIds) throws QueryException {
		AmsAssetsPriviModel modelProducer = (AmsAssetsPriviModel) sqlProducer;
		AmsAssetsPriviDTO dto = (AmsAssetsPriviDTO) dtoParameter;
		SQLModel sqlModel = null;
		if (dto.getAct().equals(AssetsActionConstant.QUERY_ACTION)) {
			sqlModel = modelProducer.getSelectedUsersModel(userIds);
		} else {
			sqlModel = modelProducer.getExistUsersModel();
		}
		DatabaseForWeb optProducer = new DatabaseForWeb(sqlModel, conn);
		return optProducer.getOptionHtml();
	}

	/**
	 * 功能：保存新增加的权限
	 * @param userIds String[]
	 * @return boolean
	 */
	public boolean savePrivi(String[] userIds) {
		boolean operateResult = false;
		boolean autoCommit = true;
		try {
			autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			delExistPrivis();
			if (userIds != null) {
				int userCount = userIds.length;
				AmsAssetsPriviDTO dto = (AmsAssetsPriviDTO) dtoParameter;
				AmsAssetsPriviDTO priviData = null;
				for (int i = 0; i < userCount; i++) {
					priviData = new AmsAssetsPriviDTO();
					ReflectionUtil.copyData(dto, priviData);
					priviData.setUserId(userIds[i]);
					setDTOParameter(priviData);
					fillData(priviData);
					setDTOParameter(priviData);
					super.createData();
				}
			}
			conn.commit();
			operateResult = true;
		} catch (Exception ex) {
			Logger.logError(ex);
		} finally {
			try {
				if (!operateResult) {
					conn.rollback();
					prodMessage(AssetsMessageKeys.ASS_PRIVI_SAVE_FAILURE);
				} else {
					prodMessage(AssetsMessageKeys.ASS_PRIVI_SAVE_SUCCESS);
				}
				message.setIsError(!operateResult);
				conn.setAutoCommit(autoCommit);
			} catch (SQLException ex1) {
				Logger.logError(ex1);
				prodMessage(AssetsMessageKeys.ROLL_BACK_ERROR);
				message.setIsError(true);
			}
		}
		return operateResult;
	}

	/**
	 * 功能：补充部门代码或公司代码数据
	 * @param priviDTO AmsAssetsPriviDTO
	 * @throws QueryException
	 */
	private void fillData(AmsAssetsPriviDTO priviDTO) throws QueryException {
		String deptName = priviDTO.getDeptName();
		String companyName = priviDTO.getCompanyName();
		AmsAssetsPriviModel modelProducer = (AmsAssetsPriviModel) sqlProducer;
		SQLModel sqlModel = null;
		SimpleQuery simp = new SimpleQuery(sqlModel, conn);
		simp.setDTOClassName(AmsAssetsPriviDTO.class.getName());
		if (!deptName.equals("")) {
			sqlModel = modelProducer.getDeptCodeModel();
			simp.setSql(sqlModel);
			simp.executeQuery();
			if (simp.hasResult()) {
				AmsAssetsPriviDTO data = (AmsAssetsPriviDTO) simp.getFirstDTO();
				priviDTO.setDeptCode(data.getDeptCode());
				priviDTO.setCompanyCode(data.getCompanyCode());
			}
		} else if (!companyName.equals("")) {
			sqlModel = modelProducer.getCompanyCodeModel();
			simp.setSql(sqlModel);
			simp.executeQuery();
			if (simp.hasResult()) {
				AmsAssetsPriviDTO data = (AmsAssetsPriviDTO) simp.getFirstDTO();
				priviDTO.setCompanyCode(data.getCompanyCode());
			}
		}
		priviDTO.setProvinceCode(servletConfig.getProvinceCode());
	}

	/**
	 * 功能：删除已经存在的资产权限。
	 * @throws DataHandleException
	 */
	private void delExistPrivis() throws DataHandleException {
		AmsAssetsPriviModel modelProducer = (AmsAssetsPriviModel) sqlProducer;
		SQLModel sqlModel = modelProducer.getDelExistPrivisModel();
		DBOperator.updateRecord(sqlModel, conn);
	}

	/**
	 * 功能：获取当前用户能访问的部门的资产。
	 * @return String[]
	 * @throws QueryException
	 */
	public String[] getPriviDept() throws QueryException {
		String[] priviDept = null;
		DTOSet depts = getPriviDepts();
		if (depts != null) {
			AmsMisDeptDTO dept = null;
			priviDept = new String[depts.getSize()];
			for (int i = 0; i < depts.getSize(); i++) {
				dept = (AmsMisDeptDTO) depts.getDTO(i);
				priviDept[i] = dept.getDeptCode();
			}
		}
		return priviDept;
	}

	/**
	 * 功能：获取当前用户能访问的部门的资产。
	 * @return String[]
	 * @throws QueryException
	 */
	public DTOSet getPriviDepts() throws QueryException {
		DTOSet priviDepts = new DTOSet();
		AmsAssetsPriviModel modelProducer = (AmsAssetsPriviModel) sqlProducer;
		SQLModel sqlModel = null;
		if(!isCompanyManager()){
			sqlModel = modelProducer.getPriviDeptModel();
			SimpleQuery simp = new SimpleQuery(sqlModel, conn);
			simp.setDTOClassName(AmsMisDeptDTO.class.getName());
			simp.executeQuery();
			priviDepts = simp.getDTOSet();
		} else {
			AmsMisDeptDAO deptDAO = new AmsMisDeptDAO(userAccount, null, conn);
			priviDepts = deptDAO.getAllMisDept();
		}
		return priviDepts;
	}


	/**
	 * 功能：判断当前用户是否本公司资产管理员
	 * @return boolean
	 * @throws QueryException
	 */
	public boolean isCompanyManager() throws QueryException {
		AmsAssetsPriviModel modelProducer = (AmsAssetsPriviModel) sqlProducer;
		SQLModel sqlModel = modelProducer.getIsCompanyMgrModel();
		SimpleQuery simp = new SimpleQuery(sqlModel, conn);
		simp.executeQuery();
		return simp.hasResult();
	}

	/**
	 * 功能：判断当前用户是否全省资产管理员
	 * @return boolean
	 * @throws QueryException
	 */
	public boolean isProvinceManager() throws QueryException {
		AmsAssetsPriviModel modelProducer = (AmsAssetsPriviModel) sqlProducer;
		SQLModel sqlModel = modelProducer.getIsProvinceMgrModel();
		SimpleQuery simp = new SimpleQuery(sqlModel, conn);
		simp.executeQuery();
		return simp.hasResult();
	}

    /**
	 * 功能：保存专业管理员权限
	 * @param mtlCodes String[]
	 * @return boolean
	 */
	public boolean saveMtlPrivis(String[] mtlCodes) {
		boolean operateResult = false;
		boolean autoCommit = true;
		try {
			autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			AmsAssetsPriviDTO dtoPara = (AmsAssetsPriviDTO) dtoParameter;
			AmsAssetsPriviModel modelProducer = (AmsAssetsPriviModel)
												sqlProducer;
			SQLModel sqlModel = null;
			AmsAssetsPriviDTO priviData = null;
			int mtlCount = mtlCodes.length;
			for (int i = 0; i < mtlCount; i++) {
				priviData = new AmsAssetsPriviDTO();
				priviData.setUserId(dtoPara.getUserId());
				priviData.setFaCategoryCode(mtlCodes[i]);
				setDTOParameter(priviData);
				sqlModel = modelProducer.getMtlPriviSaveModel();
				DBOperator.updateRecord(sqlModel, conn);
			}
			operateResult = true;
		} catch (DataHandleException ex) {
			ex.printLog();
		} catch (SQLException ex) {
			Logger.logError(ex);
		} finally {
			try {
				if (!operateResult) {
					conn.rollback();
					prodMessage(AssetsMessageKeys.ASS_PRIVI_SAVE_FAILURE);
				} else {
					prodMessage(AssetsMessageKeys.ASS_PRIVI_SAVE_SUCCESS);
				}
				message.setIsError(!operateResult);
				conn.setAutoCommit(autoCommit);
			} catch (SQLException ex1) {
				Logger.logError(ex1);
				prodMessage(AssetsMessageKeys.ROLL_BACK_ERROR);
				message.setIsError(true);
			}
		}
		return operateResult;
	}

	/**
	 * 功能：删除专业管理员权限
	 * @param mtlCodes String[]
	 * @return boolean
	 */
	public boolean delMtlPrivis(String[] mtlCodes) {
		boolean operateResult = false;
		boolean autoCommit = true;
		try {
			autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			AmsAssetsPriviDTO dtoPara = (AmsAssetsPriviDTO) dtoParameter;
			AmsAssetsPriviModel modelProducer = (AmsAssetsPriviModel)
												sqlProducer;
			SQLModel sqlModel = null;
			AmsAssetsPriviDTO priviData = null;
			int mtlCount = mtlCodes.length;
			for (int i = 0; i < mtlCount; i++) {
				priviData = new AmsAssetsPriviDTO();
				priviData.setUserId(dtoPara.getUserId());
				priviData.setFaCategoryCode(mtlCodes[i]);
				setDTOParameter(priviData);
				sqlModel = modelProducer.getMtlPriviDelModel();
				DBOperator.updateRecord(sqlModel, conn);
			}
			operateResult = true;
		} catch (DataHandleException ex) {
			ex.printLog();
		} catch (SQLException ex) {
			Logger.logError(ex);
		} finally {
			try {
				if (!operateResult) {
					conn.rollback();
					prodMessage(AssetsMessageKeys.PRIVI_DELETE_FAILURE);
				} else {
					prodMessage(AssetsMessageKeys.PRIVI_DELETE_SUCCESS);
				}
				message.setIsError(!operateResult);
				conn.setAutoCommit(autoCommit);
			} catch (SQLException ex1) {
				Logger.logError(ex1);
				prodMessage(AssetsMessageKeys.ROLL_BACK_ERROR);
				message.setIsError(true);
			}
		}
		return operateResult;
	}


	/**
	 * 功能：判断当前用户是否有维护本次资产权限的权限
	 * @return boolean
	 */
	public boolean hasOperatePrivi() {
		boolean hasOperatePrivi = true;
		AmsAssetsPriviDTO dtoPara = (AmsAssetsPriviDTO) dtoParameter;
		String userComapny = userAccount.getCompany();
		String operateCompany = dtoPara.getCompanyName();
		if (!operateCompany.equals("")) {
			if (!userAccount.isSysAdmin()) {
				if (!userComapny.equals(operateCompany)) {
					hasOperatePrivi = false;
				}
			}
		}
		if (!hasOperatePrivi) {
			prodMessage(AssetsMessageKeys.HAS_NO_OPERATE_PRIVI);
			message.setIsError(true);
		}
		return hasOperatePrivi;
	}

	/**
	 * 功能：获取省公司OU本部名称
	 * @return String
	 * @throws QueryException
	 */
	private String getProvOuName() throws QueryException {
		EtsOuCityMapDAO dao = new EtsOuCityMapDAO(userAccount, null, conn);
		dao.setServletConfig(servletConfig);
		return dao.getProvOuName();
	}

	/**
	 * 功能：获取用户的专业资产管理员权限
	 * @return String
	 * @throws QueryException
	 */
	public String getMtlMgrProps() throws QueryException {
		String mtlMgrProps = "";
		AmsAssetsPriviModel modelProducer = (AmsAssetsPriviModel) sqlProducer;
		SQLModel sqlModel = modelProducer.getMtlMgrPropsModel();
		SimpleQuery simp = new SimpleQuery(sqlModel, conn);
		simp.setDTOClassName(AmsAssetsPriviDTO.class.getName());
		simp.executeQuery();
		DTOSet dtos = simp.getDTOSet();
		if (dtos != null && !dtos.isEmpty()) {
			AmsAssetsPriviDTO dto = null;
			int loopCount = 0;
			mtlMgrProps = "'";
			for (int i = 0; i < dtos.getSize(); i++) {
				dto = (AmsAssetsPriviDTO) dtos.getDTO(i);
				if (mtlMgrProps.indexOf(dto.getFaCategoryCode()) == -1) {
					mtlMgrProps += dto.getFaCategoryCode() + "', '";
					loopCount++;
				}
			}
			if (loopCount > 0) {
				mtlMgrProps = mtlMgrProps.substring(0, mtlMgrProps.length() - 3);
			}
		}
		return mtlMgrProps;
	}
}
