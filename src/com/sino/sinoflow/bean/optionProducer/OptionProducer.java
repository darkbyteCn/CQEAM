package com.sino.sinoflow.bean.optionProducer;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.QueryException;
import com.sino.base.util.StrUtil;
import com.sino.base.web.DatabaseForWeb;
import com.sino.sinoflow.constant.WebAttrConstant;
import com.sino.sinoflow.model.SfApiModel;
import com.sino.sinoflow.model.SfHolidaysModel;
import com.sino.sinoflow.model.SfProcedureModel;
import com.sino.sinoflow.model.SfTaskModel;
import com.sino.sinoflow.model.SfWorkHourModel;
import com.sino.sinoflow.model.SfWorkScheduleModel;
import com.sino.sinoflow.user.dto.SfProjectDTO;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;
import com.sino.sinoflow.user.model.SfGroupModel;
import com.sino.sinoflow.user.model.SfProjectModel;
import com.sino.sinoflow.user.model.SfRoleModel;
import com.sino.sinoflow.user.model.SfUserBaseModel;

/**
 * <p>Title: SinoIES</p>
 * <p>Description: 河南移动IES系统</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class OptionProducer {

	private Connection conn = null;
	private SfUserBaseDTO userAccount = null;

	public OptionProducer(SfUserBaseDTO userAccount, Connection conn) {
		this.userAccount = userAccount;
		this.conn = conn;
	}


  /**
   * 功能：获取所有角色构成的下拉列表
   * @param resId 指定资源编号
   * @return String
   * @throws QueryException
   */
  public String getAllRoleOption(int resId, String projName) throws QueryException {
	  SQLModel sqlModel = new SQLModel();
	  List sqlArgs = new ArrayList();
	  String sqlStr = "SELECT"
			  + " SR.ROLE_NAME,"
			  + " SR.ROLE_NAME"
			  + " FROM"
			  + " SF_ROLE SR"
              + " WHERE"
              + " SR.PROJECT_NAME = ?";
      sqlArgs.add(projName);
	  if (resId > 0) {
		  sqlStr += " AND"
				  + " NOT EXISTS("
				  + " SELECT"
				  + " NULL"
				  + " FROM"
				  + " SF_RES_PRIVS SRP"
				  + " WHERE"
				  + " SR.ROLE_NAME = SRP.ROLE_NAME"
				  + " AND SRP.SYSTEM_ID = ?"
				  + ")";
		  sqlArgs.add(resId);
	  }
      sqlStr += " ORDER BY SR.ROLE_NAME";
      sqlModel.setSqlStr(sqlStr);
	  sqlModel.setArgs(sqlArgs);
	  DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
	  return webFieldProducer.getOptionHtml();
  }

    public String getAllRoleOption(int resId) throws QueryException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT"
                + " SR.ROLE_NAME,"
                + " SR.ROLE_NAME"
                + " FROM"
                + " SF_ROLE SR";
        if (!StrUtil.isEmpty(resId)) {
            sqlStr += " WHERE"
                    + " NOT EXISTS("
                    + " SELECT"
                    + " NULL"
                    + " FROM"
                    + " SF_RES_PRIVS SRP"
                    + " WHERE"
                    + " SR.ROLE_NAME = SRP.ROLE_NAME"
                    + " AND SRP.SYSTEM_ID = ?"
                    + ")AND SR.ENABLED = 'Y'";
            sqlArgs.add(resId);
        }
        sqlStr += " ORDER BY SR.ROLE_NAME";
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
        return webFieldProducer.getOptionHtml();
    }    

    /**
     * ???????????ù??×é±?????????????±í
     * @param sysId ???¨×???±à??
     * @return String
     * @throws QueryException
     */
    public String getAllGroupOption(int sysId) throws QueryException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        //String sqlStr = "select distinct group_name,group_name from sf_group";

        String sqlStr = "SELECT "
                  + " DISTINCT"
                + " SG.GROUP_NAME,"
                + " SG.GROUP_NAME"
                + " FROM"
                + " SF_GROUP SG";
        if (!StrUtil.isEmpty(sysId)) {
            sqlStr += " WHERE"
                    + " NOT EXISTS("
                    + " SELECT"
                    + " NULL"
                    + " FROM"
                    + " SF_RES_PRIVS SRP"
                    + " WHERE"
                    + " SG.GROUP_NAME = SRP.GROUP_NAME"
                    + " AND SRP.SYSTEM_ID = ?"
                    + ")";
            sqlArgs.add(sysId);
        }
        sqlStr += " ORDER BY SG.GROUP_NAME";
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
        return webFieldProducer.getOptionHtml();
    }

  /**
   * 功能：获取能访问指定资源的角色构成的下拉列表
   * @param resourceId String
   * @return String
   * @throws QueryException
   */
  public String getViewRoleOption(int resourceId) throws QueryException {
	   SQLModel sqlModel = new SQLModel();
	   List sqlArgs = new ArrayList();

		  String sqlStr = "SELECT"
		  + " SRP.ROLE_NAME,"
		  + " SRP.ROLE_NAME"
		  + " FROM"
		  + " SF_RES_PRIVS SRP,"
		  + " SF_RES_DEFINE SRD"
		  + " WHERE"
		  + " SRP.SYSTEM_ID = SRD.SYSTEM_ID"
		  + " AND SRD.SYSTEM_ID = ?";

	   sqlModel.setSqlStr(sqlStr);
	   sqlArgs.add(resourceId);
	   sqlModel.setArgs(sqlArgs);
	   DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
	   return webFieldProducer.getOptionHtml();
  }


	/**
	 * 获取所有OU组织下拉列表框
	 * @param selectedValue String
	 * @param addBlank      是否加入“请选择";
	 * @return String
	 * @throws QueryException
	 *
	 * 修改后:
	 * 获取工程的下拉列表 option 字符串
	 */
	public String getAllOrganization(String selectedValue, boolean addBlank) throws QueryException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
			+ " ORG_ID,"
			+ " COMPANY_NAME"
			+ " FROM"
			+ " SINO_MIS_ORG SMO"
			+ " WHERE SMO.ENABLED='Y'";

		if (!(userAccount.isProvinceUser() && userAccount.isSysAdmin())) {
			sqlStr += " AND ORG_ID = ?";
			sqlArgs.add(userAccount.getOrganizationId());
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
			DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
			return webFieldProducer.getOptionHtml(selectedValue, false);
		} else {
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
			DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
			return webFieldProducer.getOptionHtml(selectedValue, addBlank);
		}
	}


	/**
	 * 功能：获取所有组别下拉框
	 * @param selectedGroup String
	 * @return String
	 * @throws QueryException                                T
	 */
	public String getAllGroup(String selectedGroup) throws QueryException {
		return getAllGroup(selectedGroup, -1, false, true);
	}

	/**
	 * 功能：获取指定组别下选中的组别下拉框
	 * @param selectedGroup  String
	 * @param orgId String
	 * @param onlySelf       boolean
	 * @return String
	 * @throws QueryException
	 */
	public String getAllGroup(String selectedGroup, int orgId, boolean onlySelf, boolean addBlank) throws QueryException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT" +
				" GROUP_ID," +
				" GROUPNAME " +
				" FROM SF_GROUP " +
				" WHERE IS_DESIGNER='0'";
		if (onlySelf) {
			sqlStr += " AND GROUP_ID=?";
			sqlArgs.add(selectedGroup);
		} else {
            if (orgId <= 0)  {
                if (!userAccount.isSysAdmin()) {
					sqlStr += " AND ORG_ID=?";
					sqlArgs.add(userAccount.getOrganizationId());
                }
			} else {
                sqlStr += " AND ORG_ID=?";
				sqlArgs.add(orgId);
			}
		}
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
		return webFieldProducer.getOptionHtml(selectedGroup, addBlank);
	}

	/**
	 * 功能：构造是否下拉框
	 * @param selectedValue String
	 * @return String
	 */
	public String getBooleanOption(String selectedValue) {
		StringBuffer strOpt = new StringBuffer();
		if (selectedValue == null) {
			selectedValue = "";
		}
		strOpt.append("<option value=\"\">--请选择--</option>");
		strOpt.append("<option value=\"");
		strOpt.append(WebAttrConstant.TRUE_VALUE);
		strOpt.append("\"");
		if (selectedValue.equals(WebAttrConstant.TRUE_VALUE)) {
			strOpt.append(" selected");
		}
		strOpt.append(">是</option>");
		strOpt.append("<option value=\"");
		strOpt.append(WebAttrConstant.FALSE_VALUE);
		strOpt.append("\"");
		if (selectedValue.equals(WebAttrConstant.FALSE_VALUE)) {
			strOpt.append(" selected");
		}
		strOpt.append(">否</option>");
		return strOpt.toString();
	}

	/**
	 * 功能:获取区县下拉列表框
	 * @param selectedValue 选中项值
	 * @return String
	 * @throws QueryException
	 */
	public String getDictParentOption(String selectedValue) throws QueryException {
		SQLModel sqlModel = new SQLModel();
		String sqlStr = "SELECT"
				+ " EFVS.FLEX_VALUE_SET_ID,"
				+ " EFVS.CODE+'('+EFVS.NAME+')'"
				+ " FROM"
				+ " ETS_FLEX_VALUE_SET EFVS";
		sqlModel.setSqlStr(sqlStr);
		DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
		return webFieldProducer.getOptionHtml(selectedValue, true);
	}




	/**
	 * 选择指定组别下用户
	 * @param groupId      String
	 * @param selectedUser 是否选中指定用户
	 * @return String
	 * @throws QueryException
	 */
	public String getUsersOfGroup(String groupId, String selectedUser) throws QueryException {
		return getUsersOfGroup(groupId, selectedUser, true);
	}

	public String getUsersOfGroup(String groupId, String selectedUser, boolean addBlank) throws QueryException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT DISTINCT SU.USER_ID, SU.LOGIN_NAME" +
				"  FROM SF_USER_RIGHT SUR, SF_USER SU" +
				" WHERE SU.USER_ID = SUR.USER_ID" +
				"   AND SUR.GROUP_ID = ?" +
				"   AND SU.DISABLE_DATE = ''" +
				" ORDER BY LOGIN_NAME";

		sqlArgs.add(groupId);

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);

		DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
		return webFieldProducer.getOptionHtml(selectedUser, addBlank);
	}




  public String getMenuValue(String selectedMenu) throws QueryException {
  SQLModel sqlModel = new SQLModel();
  List sqlArgs = new ArrayList();
  String sqlStr =
		  "SELECT SRD.RES_ID, SRD.RES_NAME" +
				  "  FROM SF_RES_DEFINE SRD" +
				  " WHERE SRD.RES_PAR_ID = ''" +
				  "   AND EXISTS (SELECT NULL TEMPNAME" +
				  "          FROM SF_RES_PRIVS SRP, SF_USER_AUTHORITY SUA" +
				  "         WHERE SRP.ROLE_NAME = SUA.ROLE_NAME" +
				  "           AND SRP.SYSTEM_ID = SRD.SYSTEM_ID" +
				  "           AND SUA.USER_ID = ?)" +
				  " ORDER BY SRD.RES_ID, SRD.RES_PAR_ID";
  sqlArgs.add(userAccount.getUserId());
  sqlModel.setSqlStr(sqlStr);
  sqlModel.setArgs(sqlArgs);
  DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
  return webFieldProducer.getOptionHtml("", true);
}

  public String getSmallMenuValue(String selectedMenu, String resParId) throws QueryException {
  SQLModel sqlModel = new SQLModel();
  List sqlArgs = new ArrayList();
  String sqlStr = "SELECT SRD.SYSTEM_ID,SRD.RES_NAME" +
		  "  FROM SF_RES_DEFINE SRD" +
		  " WHERE SRD.RES_PAR_ID <> ''" +
		  "   AND SRD.RES_URL <> ''" +
		  "   AND EXISTS (SELECT NULL TEMPNAME" +
		  "          FROM SF_RES_PRIVS SRP, SF_USER_AUTHORITY SUA" +
		  "         WHERE SRP.ROLE_NAME = SUA.ROLE_NAME" +
		  "           AND SRP.SYSTEM_ID = SRD.SYSTEM_ID" +
		  "           AND SUA.USER_ID = ?)" +
		  "   AND SRD.RES_PAR_ID LIKE ? + '.%'" +
		  " ORDER BY SRD.RES_ID, SRD.RES_PAR_ID";
  sqlArgs.add(userAccount.getUserId());
  sqlArgs.add(resParId);
  sqlModel.setSqlStr(sqlStr);
  sqlModel.setArgs(sqlArgs);
  DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
  return webFieldProducer.getOptionHtml(selectedMenu, false);
}



	/**
	 * 生成流程组别列表
	 * @param selectedInv String
	 * @return String
	 * @throws QueryException
	 */
	public String getFlowGroupOption(String selectedInv) throws QueryException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT SG.GROUP_ID, SG.GROUPNAME" +
				"  FROM SF_GROUP SG" +
				" WHERE SG.IS_DESIGNER = 1";
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
		return webFieldProducer.getOptionHtml(selectedInv, true);
	}


	/**
	 * 生成流程组别列表
	 * @param selectedInv String
	 * @return String
	 * @throws QueryException
	 */
	public String getGroupOption(String orgId, String selectedInv) throws QueryException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT SG.GROUP_ID, SG.GROUPNAME" +
				"  FROM SF_GROUP SG" +
				" WHERE SG.IS_DESIGNER = '0'" +
				" AND SG.ENABLED = 'Y'" +
				" AND SG.ORG_ID = ?";
		sqlArgs.add(orgId);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
		return webFieldProducer.getOptionHtml(selectedInv, true);
	}

    /**
     * 生成权限组别列表
     * @param resId SystemId
     * @return String
     * @throws QueryException
     */
    public String getGroupOption(int resId) throws QueryException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT DISTINCT GROUP_NAME, GROUP_NAME FROM SF_RES_PRIVS SG WHERE SYSTEM_ID = ?";
        sqlArgs.add(resId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
        return webFieldProducer.getOptionHtml();
    }


	/**
	 * 功能：返回工程表的下拉列表
	 */
	public String getProjectOption(String value,String tag) throws QueryException{

		SfProjectModel sfProjectModel = new SfProjectModel(userAccount,new SfProjectDTO());
		DatabaseForWeb webFieldProducer = new DatabaseForWeb(sfProjectModel.getOptionProjectModel(), conn);
		if(tag.equals(WebAttrConstant.PROJECT_OPTION_STR_ALL)){
			return webFieldProducer.getOptionHtml();
		}else{
			return webFieldProducer.getOptionHtml(value);
		}
	}

	  /**
	 * 功能：返回工程表的下拉列表
	 */
	public String getProjectOption2(String value,String tag) throws QueryException{

		SfProjectModel sfProjectModel = new SfProjectModel(userAccount,new SfProjectDTO());
		DatabaseForWeb webFieldProducer = new DatabaseForWeb(sfProjectModel.getOptionProjectModelId(), conn);
		if(tag.equals(WebAttrConstant.PROJECT_OPTION_STR_ALL)){
			return webFieldProducer.getOptionHtml();
		}else{
			return webFieldProducer.getOptionHtml(value);
		}
	}

	/**
	 * 功能：返回所有组别下拉列表
	 */
	public String getGroupOption(String value,String pName,String tag, int orgId) throws QueryException{

		SfGroupModel sfGroupModel = new SfGroupModel(userAccount, null);
		DatabaseForWeb webFieldProducer = new DatabaseForWeb(sfGroupModel.getOptionGroupModel(pName, orgId), conn);
		if(tag.equals(WebAttrConstant.GROUP_OPTION_STR_SELECT)){
			return webFieldProducer.getOptionHtml(value);
		}else{
			return webFieldProducer.getOptionHtml();
		}
	}

	/**
	 * 功能：返回所有组别下拉列表
	 */
	public String getGroupOption2(String value,String pName,String tag) throws QueryException{

		SfGroupModel sfGroupModel = new SfGroupModel(userAccount, null);
		DatabaseForWeb webFieldProducer = new DatabaseForWeb(sfGroupModel.getOptionGroupModel2(pName), conn);
		if(tag.equals(WebAttrConstant.GROUP_OPTION_STR_SELECT)){
			return webFieldProducer.getOptionHtml(value);
		}else{
			return webFieldProducer.getOptionHtml();
		}
	}

	/**
	 * 功能：返回角色下拉列表
	 */
	public String getRoleOption(String value,String pName,String tag) throws QueryException{

		SfRoleModel sfRoleModel = new SfRoleModel(userAccount, null);
		DatabaseForWeb webFieldProducer = new DatabaseForWeb(sfRoleModel.getRoleOptionModel(pName), conn);
		if(tag.equals(WebAttrConstant.ROLE_OPTION_STR_SELECT)){
			return webFieldProducer.getOptionHtml(value);
		}else{
			return webFieldProducer.getOptionHtml();
		}
	}

	/**
	 * 功能：返回用户下拉列表
	 */
	public String getUserOption(String tag,int value) throws QueryException{

		SfUserBaseModel sf = new SfUserBaseModel(null, userAccount);
		DatabaseForWeb webFieldProducer = new DatabaseForWeb(sf.displayUsers(), conn);
		if(tag == null){
			return webFieldProducer.getOptionHtml();
		}else{
			return webFieldProducer.getOptionHtml(String.valueOf(value));
		}
	}

    public String getDelegateUserOption(String tag,int value) throws QueryException{

        SfUserBaseModel sf = new SfUserBaseModel(null, userAccount);
        DatabaseForWeb webFieldProducer = new DatabaseForWeb(sf.displayDelegateUsers(), conn);
        if(tag == null){
            return webFieldProducer.getOptionHtml();
        }else{
            return webFieldProducer.getOptionHtml(String.valueOf(value));
        }
    }

    /**
	 * 功能：返回过程下拉列表
	 * @throws QueryException
	 */
	public String getProcedureOption(int proId,String value,String tag) throws QueryException{

		SfProcedureModel fm = new SfProcedureModel(userAccount, null);
		DatabaseForWeb webFieldProducer = new DatabaseForWeb(fm.getProjectProcedureModel(proId), conn);
//		if(tag.equals(WebAttrConstant.PROCEDURE_OPTION_STR_SELECT)){
		if(tag.equals(WebAttrConstant.ROLE_OPTION_STR_SELECT)) {
			return webFieldProducer.getOptionHtml(value);
		}else{
			return webFieldProducer.getOptionHtml();
		}
	}

	/**
	 * 功能：返回过程下拉列表
	 * @throws QueryException
	 */
	public String getProcedureOption2(int projId,String value,String tag) throws QueryException{

		SfProcedureModel fm = new SfProcedureModel(userAccount, null);
		DatabaseForWeb webFieldProducer = new DatabaseForWeb(fm.getProjectProcedureModel2(projId), conn);
		if(tag.equals(WebAttrConstant.ROLE_OPTION_STR_SELECT)){
			return webFieldProducer.getOptionHtml(value);
		}else{
			return webFieldProducer.getOptionHtml();
		}
	}

	/**
	 * 功能：返回过程下任务列表
	 * @throws QueryException
	 */
	public String getTask(int proId,String value,String tag) throws QueryException{

		SfTaskModel fm = new SfTaskModel(userAccount, null);
		DatabaseForWeb webFieldProducer = new DatabaseForWeb(fm.getProcedureTaskModel2(proId), conn);
		if(tag.equals(WebAttrConstant.ROLE_OPTION_STR_SELECT)){
			return webFieldProducer.getOptionHtml(value);
		}else{
			return webFieldProducer.getOptionHtml();
		}
	}

	/**
	 * 功能：返回工作时间列表
	 * @throws QueryException
	 */
	public String getHolidays (String tag) throws QueryException{

		SfHolidaysModel fm = new SfHolidaysModel(null, null);
		DatabaseForWeb webFieldProducer = new DatabaseForWeb(fm.getHolidays(), conn);
		if(tag != null){
			return webFieldProducer.getOptionHtml(tag);
		}else{
			return webFieldProducer.getOptionHtml();
		}
	}

	/**
	 * 功能：返回节假日列表
	 * @throws QueryException
	 */
	public String getWorkHour(String tag) throws QueryException{

		SfWorkHourModel fm = new SfWorkHourModel(null, null);
		DatabaseForWeb webFieldProducer = new DatabaseForWeb(fm.getWorkHour(), conn);
		if(tag != null){
			return webFieldProducer.getOptionHtml(tag);
		}else{
			return webFieldProducer.getOptionHtml();
		}
	}

	/**
	 * 功能：返回工作时间节假定义下拉列表
	 * @throws QueryException
	 */
	public String getWorkScheduleOption(String tag) throws QueryException{

		SfWorkScheduleModel fm = new SfWorkScheduleModel(null, null);
		DatabaseForWeb webFieldProducer = new DatabaseForWeb(fm.getSelectOption(), conn);
		if(tag != null){
			return webFieldProducer.getOptionHtml(tag);
		}else{
			return webFieldProducer.getOptionHtml();
		}
	}


    /**
     * 功能：根据工程名称获取组别下拉框
     * @param selectedValue 选中项值
     * @param projectName 项目名称
     * @param orgId 公司组织ID
     * @return String
     * @throws QueryException
     */
    public String getPrjGroupOption(String selectedValue, String projectName, int orgId) throws QueryException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT"
                        + " SG.GROUP_NAME,"
                        + " SG.GROUP_NAME"
                        + " FROM"
                        + " SF_GROUP SG"
                        + " WHERE"
                        + " (? = '' OR SG.PROJECT_NAME = ?)"
                        + " AND EXISTS("
                        + " SELECT"
                        + " NULL"
                        + " FROM"
                        + " SINO_MIS_DEPT    SMD,"
                        + " SINO_GROUP_MATCH SGM"
                        + " WHERE"
                        + " SG.GROUP_ID = SGM.GROUP_ID"
                        + " AND SGM.DEPT_ID = SMD.DEPT_ID"
                        + " AND (? <= 0 OR SMD.ORG_ID = ?))";
        sqlArgs.add(projectName);
        sqlArgs.add(projectName);
        sqlArgs.add(orgId);
        sqlArgs.add(orgId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
        return webFieldProducer.getOptionHtml(selectedValue, true);
    }

	/**
	 * 功能：根据工程名称获取角色下拉框
	 * @param selectedValue String
	 * @param projectName String
	 * @return String
	 * @throws QueryException
	 */
	public String getPrjRoleOption(String selectedValue, String projectName) throws QueryException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
						+ " SR.ROLE_NAME,"
						+ " SR.ROLE_NAME"
						+ " FROM"
						+ " SF_ROLE SR"
						+ " WHERE"
						+ " SR.PROJECT_NAME = ?";
		sqlArgs.add(projectName);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
		return webFieldProducer.getOptionHtml(selectedValue, true);
	}

    /**
     * 功能：返回角色下拉列表
     * @param value String
     * @param pName String
     * @param tag String
     * @return String
     * @throws QueryException
     */
    public String getRoleOption2(String value, String pName, String tag) throws QueryException {

        SfRoleModel sfRoleModel = new SfRoleModel(userAccount, null);
        DatabaseForWeb webFieldProducer = new DatabaseForWeb(sfRoleModel.getRoleOptionModel2(pName), conn);
        if (tag.equals(WebAttrConstant.ROLE_OPTION_STR_SELECT)) {
            return webFieldProducer.getOptionHtml(value, true);
        } else {
            return webFieldProducer.getOptionHtml();
        }
    }

    public String getApi(int apiId, String value, String tag) throws QueryException {
        SfApiModel fm = new SfApiModel(userAccount, null);
        DatabaseForWeb webFieldProducer = new DatabaseForWeb(fm.getApiOptionModel(apiId), conn);
        if (tag.equals(WebAttrConstant.API_OPTION_STR)) {
            return webFieldProducer.getOptionHtml(value);
        } else {
            return webFieldProducer.getOptionHtml();
        }
    }

    public String getAllDeptOption(int orgId, String selectedInv) throws QueryException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT DISTINCT SMD.DEPT_NAME, SMD.DEPT_NAME, SMD.DISPLAY_ORDER" +
                "  FROM SINO_MIS_DEPT SMD" +
                " WHERE (? <= 0 OR SMD.ORG_ID = ?)" +
                " ORDER BY DISPLAY_ORDER";

        sqlArgs.add(orgId);
        sqlArgs.add(orgId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
        return webFieldProducer.getOptionHtml(selectedInv, false);
    }

    public String getAskUserOption(String actId, String selectedInv) throws QueryException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT DISTINCT SU.LOGIN_NAME, SU.USERNAME + '\\' + SMD.DEPT_NAME USERNAME, SU.DISPLAY_ORDER" +
                "  FROM SF_USER SU, SF_ACT_INFO SAI, SF_ACT_LOG SAL, SINO_MIS_DEPT SMD" +
                " WHERE SAI.SFACT_ACT_ID = ? AND SAI.SFACT_CASE_ID = SAL.SFACT_CASE_ID" +
                " AND SAL.SFACT_SIGN_USER = SU.LOGIN_NAME AND SAL.SFACT_SIGN_USER <> 'SYSTEM'" +
                " AND SU.DEPT_CODE = SMD.DEPT_ID" +
                " ORDER BY SU.DISPLAY_ORDER";

        sqlArgs.add(actId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
        return webFieldProducer.getOptionHtml(selectedInv, true);
    }

    public String getProcedure(String selectedValue) throws QueryException {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT SP.PROCEDURE_NAME, SP.PROCEDURE_NAME FROM SF_PROCEDURE SP, SF_PROJECT_V SFV"
            + " WHERE SFV.PROJECT_NAME = '资产管理工程' AND SFV.PROJECT_ID = SP.PROJECT_ID";

        sqlModel.setSqlStr(sqlStr);

        DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
        return webFieldProducer.getOptionHtml(selectedValue, true);
    }

    public String getTaskOpt(String procName, String selectedValue) throws QueryException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT ST.TASK_NAME, ST.TASK_NAME FROM SF_TASK ST, SF_PROCEDURE SP, SF_PROJECT_V SFV"
            + " WHERE SFV.PROJECT_NAME = '资产管理工程' AND SFV.PROJECT_ID = SP.PROJECT_ID AND SP.PROCEDURE_NAME = ?"
            + " AND SP.PROCEDURE_ID = ST.PROCEDURE_ID AND ST.TASK_NAME <> 'SPLIT' AND ST.TASK_NAME <> 'JOIN' AND ST.TASK_NAME <> 'STOP'";

        sqlArgs.add(procName);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
        return webFieldProducer.getOptionHtml(selectedValue, true);
    }

    public String getComapnyName(String selectedValue, boolean addBlank) throws QueryException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT"
            + " COMPANY_NAME,"
            + " COMPANY_NAME"
            + " FROM"
            + " SINO_MIS_ORG SMO"
            + " WHERE SMO.ENABLED='Y'";

        if ((!userAccount.isProvinceUser()) && (!userAccount.isSysAdmin())) {
            sqlStr += " AND COMPANY_NAME = ?";
            sqlArgs.add(userAccount.getOrganizationId());
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
            DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
            return webFieldProducer.getOptionHtml(selectedValue, false);
        } else {
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
            DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
            return webFieldProducer.getOptionHtml(selectedValue, addBlank);
        }
    }
}
