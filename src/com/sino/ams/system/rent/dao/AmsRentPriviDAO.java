package com.sino.ams.system.rent.dao;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.SQLException;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.dto.AmsMisDeptDTO;
import com.sino.ams.system.rent.dto.AmsRentPriviDTO;
import com.sino.ams.system.rent.model.AmsRentPriviModel;
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
 * <p>Title: AmsRentPriviModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsRentPriviModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */


public class AmsRentPriviDAO extends AMSBaseDAO {

	/**
	 * 功能：租赁权限表 AMS_RENT_PRIVI 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsRentPriviDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public AmsRentPriviDAO(SfUserDTO userAccount, AmsRentPriviDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) { 
		AmsRentPriviDTO dtoPara = (AmsRentPriviDTO)dtoParameter;
		super.sqlProducer = new AmsRentPriviModel((SfUserDTO)userAccount, dtoPara);
	}

   //--------------------------------------------------------------------------------------------------------

    private String constant = "";
	private String newValue = "";


	public void setServletConfig(ServletConfigDTO servletConfig){
		super.setServletConfig(servletConfig);
		AmsRentPriviModel modelProducer = (AmsRentPriviModel)sqlProducer;
		constant = "a";
		modelProducer.setServletConfig(servletConfig, constant);
		newValue = servletConfig.getProCompanyName();
	}



	/**
	 * 功能：删除资产权限数据
	 * @param priviIds String[]
	 * @return boolean
	 */
	public boolean deletePrivis(String[] priviIds) {
		boolean operateResult = false;
		try {
			AmsRentPriviModel modelProducer = (AmsRentPriviModel) sqlProducer;
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
	 * 功能：获取租赁资产管理员权限的单选按钮视图
	 * @return String
	 * @throws com.sino.base.exception.QueryException
     */
	public String getPriviRoleRadio() throws QueryException {
		StringBuffer priviRadio = new StringBuffer();
		try {
			AmsRentPriviModel modelProducer = (AmsRentPriviModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getPriviRoleModel();
			SimpleQuery simp = new SimpleQuery(sqlModel, conn);
			simp.executeQuery();
			RowSet rows = simp.getSearchResult();
			String value = "";
			String caption = "";
			Row row = null;
			for(int i = 0; i < rows.getSize(); i++){
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
	 * 功能：获取第一个租赁资产管理员角色
	 * @return AmsRentPriviDTO
	 * @throws QueryException
	 */
	public SfRoleDTO getPriviFirstRole() throws QueryException {
		AmsRentPriviModel modelProducer = (AmsRentPriviModel) sqlProducer;
		SQLModel sqlModel = modelProducer.getFirstRoleModel();
		SimpleQuery simp = new SimpleQuery(sqlModel, conn);
		simp.setDTOClassName(SfRoleDTO.class.getName());
		simp.executeQuery();
		return (SfRoleDTO)simp.getFirstDTO();
	}

	/**
	 * 功能：产生部门的树形结构
	 * @return String
	 * @throws QueryException
	 */
	public String getDeptTree() throws QueryException {
		StringBuffer deptTree = new StringBuffer();
		try {
			AmsRentPriviDTO dto = (AmsRentPriviDTO)dtoParameter;
			AmsRentPriviModel modelProducer = (AmsRentPriviModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getDeptTreeModel();
			FieldSQLProperty sqlProperty = new FieldSQLProperty();
			sqlProperty.setSqlModel(sqlModel);
			DataBaseTree treeBuilder = new DataBaseTree(conn, sqlProperty);
			treeBuilder.setTreeName("租赁资产管理权限维护");
			treeBuilder.setReplaceValue(constant, newValue);
			Tree tree = treeBuilder.getTree();
			WebPageTree webTree = new WebPageTree(tree);
			webTree.setTarFrame("priviMain");
//			String url = AssetsURLList.PRIVI_RIGHT_SERVLET;
			String url = "/servlet/com.sino.ams.system.rent.servlet.RentPriviRightServlet";
			url += "?roleId=" + dto.getRoleId() + "&roleName=" + URLEncoder.encode(dto.getRoleName(), "GBK");
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
			AmsRentPriviDTO dto = (AmsRentPriviDTO)dtoParameter;
			AmsRentPriviModel modelProducer = (AmsRentPriviModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getCompanyTreeModel();
			FieldSQLProperty sqlProperty = new FieldSQLProperty();
			sqlProperty.setSqlModel(sqlModel);
			DataBaseTree treeBuilder = new DataBaseTree(conn, sqlProperty);
			treeBuilder.setTreeName("租赁资产管理权限维护");
			treeBuilder.setReplaceValue(constant, newValue);
			Tree tree = treeBuilder.getTree();
			WebPageTree webTree = new WebPageTree(tree);
			webTree.setTarFrame("priviMain");

//            String url = AssetsURLList.PRIVI_RIGHT_SERVLET;
            String url = "/servlet/com.sino.ams.system.rent.servlet.RentPriviRightServlet";
			url += "?roleId=" + dto.getRoleId() + "&roleName=" + dto.getRoleName();
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
		AmsRentPriviDTO dto = (AmsRentPriviDTO)dtoParameter;
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
		queryTree.append("nodes[\"0_rootId\"]=\"text:租赁资产管理权限维护;\";");
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
//		queryTree.append(";url:/servlet/com.sino.ams.newasset.servlet.PriviRightServlet\";");
		queryTree.append(";url:/servlet/com.sino.ams.system.rent.servlet.RentPriviRightServlet\";");
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
		AmsRentPriviModel modelProducer = (AmsRentPriviModel) sqlProducer;
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
		AmsRentPriviModel modelProducer = (AmsRentPriviModel) sqlProducer;
		AmsRentPriviDTO dto = (AmsRentPriviDTO)dtoParameter;
		SQLModel sqlModel = null;
		if(dto.getAct().equals(AssetsActionConstant.QUERY_ACTION)){
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
	public boolean savePrivi(String[] userIds){
		boolean operateResult = false;
		boolean autoCommit = true;
		try {
			autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			delExistPrivis();
			if (userIds != null) {
				int userCount = userIds.length;
				AmsRentPriviDTO dto = (AmsRentPriviDTO) dtoParameter;
				AmsRentPriviDTO priviData = null;
				for (int i = 0; i < userCount; i++) {
					priviData = new AmsRentPriviDTO();
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
		} finally{
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
	 * @param priviDTO AmsRentPriviDTO
	 * @throws QueryException
	 */
	private void fillData(AmsRentPriviDTO priviDTO) throws QueryException {
		String deptName = priviDTO.getDeptName();
		String companyName = priviDTO.getCompanyName();
		AmsRentPriviModel modelProducer = (AmsRentPriviModel)sqlProducer;
		SQLModel sqlModel = null;
		SimpleQuery simp = new SimpleQuery(sqlModel, conn);
		simp.setDTOClassName(AmsRentPriviDTO.class.getName());
		if(!deptName.equals("")){
			sqlModel = modelProducer.getDeptCodeModel();
			simp.setSql(sqlModel);
			simp.executeQuery();
			if(simp.hasResult()){
				AmsRentPriviDTO data = (AmsRentPriviDTO)simp.getFirstDTO();
				priviDTO.setDeptCode(data.getDeptCode());
				priviDTO.setCompanyCode(data.getCompanyCode());
			}
		} else if(!companyName.equals("")){
			sqlModel = modelProducer.getCompanyCodeModel();
			simp.setSql(sqlModel);
			simp.executeQuery();
			if(simp.hasResult()){
				AmsRentPriviDTO data = (AmsRentPriviDTO)simp.getFirstDTO();
				priviDTO.setCompanyCode(data.getCompanyCode());
			}
		}
		priviDTO.setProvinceCode(servletConfig.getProvinceCode());
	}

	/**
	 * 功能：删除已经存在的租赁资产权限。
	 * @throws DataHandleException
	 */
	private void delExistPrivis() throws DataHandleException {
		AmsRentPriviModel modelProducer = (AmsRentPriviModel)sqlProducer;
		SQLModel sqlModel = modelProducer.getDelExistPrivisModel();
		DBOperator.updateRecord(sqlModel, conn);
	}

	/**
	 * 功能：获取当前用户能访问的部门的租赁资产。
	 * @return String[]
	 * @throws QueryException
	 */
	public String[] getPriviDept() throws QueryException {
		String[] priviDept = null;
		DTOSet depts = getPriviDepts();
		if(depts != null){
			AmsMisDeptDTO dept = null;
			priviDept = new String[depts.getSize()];
			for(int i = 0; i < depts.getSize(); i++){
				dept = (AmsMisDeptDTO)depts.getDTO(i);
				priviDept[i] = dept.getDeptCode();
			}
		}
		return priviDept;
	}

	/**
	 * 功能：获取当前用户能访问的部门的租赁资产。
	 * @return String[]
	 * @throws QueryException
	 */
	public DTOSet getPriviDepts() throws QueryException {
		DTOSet priviDepts = new DTOSet();
		AmsRentPriviModel modelProducer = (AmsRentPriviModel) sqlProducer;
		SQLModel sqlModel = modelProducer.getPriviDeptModel();
		SimpleQuery simp = new SimpleQuery(sqlModel, conn);
		simp.setDTOClassName(AmsMisDeptDTO.class.getName());
		simp.executeQuery();
		priviDepts = simp.getDTOSet();
		return priviDepts;
	}


	/**
	 * 功能：判断当前用户是否本公司租赁资产管理员
	 * @return boolean
	 * @throws QueryException
	 */
	public boolean isCompanyManager() throws QueryException {
		AmsRentPriviModel modelProducer = (AmsRentPriviModel) sqlProducer;
		SQLModel sqlModel = modelProducer.getIsCompanyMgrModel();
		SimpleQuery simp = new SimpleQuery(sqlModel, conn);
		simp.executeQuery();
		return simp.hasResult();
	}

	/**
	 * 功能：判断当前用户是否全省租赁资产管理员
	 * @return boolean
	 * @throws QueryException
	 */
	public boolean isProvinceManager() throws QueryException {
		AmsRentPriviModel modelProducer = (AmsRentPriviModel) sqlProducer;
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
			AmsRentPriviDTO dtoPara = (AmsRentPriviDTO) dtoParameter;
			AmsRentPriviModel modelProducer = (AmsRentPriviModel) sqlProducer;
			SQLModel sqlModel = null;
			AmsRentPriviDTO priviData = null;
			int mtlCount = mtlCodes.length;
			for (int i = 0; i < mtlCount; i++) {
				priviData = new AmsRentPriviDTO();
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
		} finally{
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
			AmsRentPriviDTO dtoPara = (AmsRentPriviDTO) dtoParameter;
			AmsRentPriviModel modelProducer = (AmsRentPriviModel) sqlProducer;
			SQLModel sqlModel = null;
			AmsRentPriviDTO priviData = null;
			int mtlCount = mtlCodes.length;
			for (int i = 0; i < mtlCount; i++) {
				priviData = new AmsRentPriviDTO();
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
		} finally{
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
	 * 功能：判断当前用户是否有维护本次租赁资产权限的权限
	 * @return boolean
	 */
	public boolean hasOperatePrivi(){
		boolean hasOperatePrivi = true;
		AmsRentPriviDTO dtoPara = (AmsRentPriviDTO) dtoParameter;
		String userComapny = userAccount.getCompany();
		String operateCompany = dtoPara.getCompanyName();
		if(!operateCompany.equals("")){
			if(!userAccount.isSysAdmin()){
				if(!userComapny.equals(operateCompany)){
					hasOperatePrivi = false;
				}
			}
		}
		if(!hasOperatePrivi){
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
	 * 功能：获取用户的专业租赁资产管理员权限
	 * @return String
	 * @throws QueryException
	 */
	public String getMtlMgrProps() throws QueryException {
        String mtlMgrProps = "";
		AmsRentPriviModel modelProducer = (AmsRentPriviModel)sqlProducer;
		SQLModel sqlModel = modelProducer.getMtlMgrPropsModel();
		SimpleQuery simp = new SimpleQuery(sqlModel, conn);
		simp.setDTOClassName(AmsRentPriviDTO.class.getName());
		simp.executeQuery();
		DTOSet dtos = simp.getDTOSet();
		if(dtos != null && !dtos.isEmpty()){
			AmsRentPriviDTO dto = null;
			int loopCount = 0;
			mtlMgrProps = "'";
			for(int i = 0; i < dtos.getSize(); i++){
				dto = (AmsRentPriviDTO)dtos.getDTO(i);
				if(mtlMgrProps.indexOf(dto.getFaCategoryCode()) == -1){
					mtlMgrProps += dto.getFaCategoryCode() + "', '";
					loopCount++;
				}
			}
			if(loopCount > 0){
				mtlMgrProps = mtlMgrProps.substring(0, mtlMgrProps.length() - 3);
			}
		}
        return mtlMgrProps;
    }
}