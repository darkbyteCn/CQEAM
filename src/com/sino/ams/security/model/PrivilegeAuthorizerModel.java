package com.sino.ams.security.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.sinoflow.constant.DictConstant;

/**
 *
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class PrivilegeAuthorizerModel {
	private SfUserDTO userAccount = null;

	public PrivilegeAuthorizerModel(BaseUserDTO userAccount){
		super();
		this.userAccount = (SfUserDTO)userAccount;
	}

	/**
	 * 功能：获取第一层菜单。用于顶层菜单。
	 * @return SQLModel
	 */
	public SQLModel getAuthorizeRootModel() {
		SQLModel sqlModel = new SQLModel();
		String sqlStr = "";
		List sqlArgs = new ArrayList();
        if (userAccount.isSysAdmin()) {
		sqlStr = "SELECT * FROM("
            		 + "SELECT"
                     + " SRD.*"
                     + " FROM"
                     + " SF_RES_DEFINE SRD"
                     + " WHERE"
                     + " SRD.RES_PAR_ID = ''"
                     + " AND SRD.VISIBLE = ?"
                     + " AND SRD.ENABLED = ?) T"
                     + " ORDER BY"
                     + " SORT_NO,"
                     + " RES_ID";
            sqlArgs.add(WebAttrConstant.TRUE_VALUE);
            sqlArgs.add(WebAttrConstant.TRUE_VALUE);
        } else {
//			sqlStr = "SELECT DISTINCT"
//                     + " SRD.*"
//                     + " FROM"
//                     + " SF_USER SU,"
//                     + " SF_ROLE SR,"
//                     + " SF_GROUP SG,"
//                     + " SF_USER_RIGHT SUR,"
//                     + " SF_RES_DEFINE SRD,"
//                     + " SF_RES_PRIVS SRP"
//                     + " WHERE"
//                     + " SU.USER_ID = SUR.USER_ID"
//                     + " AND SUR.ROLE_ID = SR.ROLE_ID"
//                     + " AND SUR.GROUP_ID = SG.GROUP_ID"
//                     + " AND SR.ROLE_NAME = SRP.ROLE_NAME"
//                     + " AND SRP.SYSTEM_ID = SRD.SYSTEM_ID"
//                     + " AND SRD.RES_PAR_ID " + SyBaseSQLUtil.isNull() + " "
//                     + " AND SRD.ENABLED = ?"
//                     + " AND SRD.VISIBLE = ?"
//                     + " AND SU.USER_ID = ?"
//                     + " ORDER BY"
//                     + " SRD.SORT_NO";
              sqlStr =
                      " SELECT DISTINCT SRD.*"
                    + " FROM "
                    //+ " SF_USER SU,"
                    + " SF_USER_AUTHORITY SUA,"
                    + " SF_RES_PRIVS SRP,"
                    + " SF_RES_DEFINE SRD"
                    + " WHERE"
                    + " SUA.USER_ID = ?"
                    + " AND SRD.ENABLED = ?"
                    + " AND SRD.VISIBLE = ?"
                    //+ " AND SU.USER_ID = SUA.USER_ID"
                    + " AND ((dbo.SFK_IS_SAME_GROUP_WITH_MASK(SRP.GROUP_NAME, SUA.GROUP_NAME) <> 0"
                    + "   OR (CHARINDEX('$',SRP.GROUP_NAME) > 0 AND CHARINDEX('" + DictConstant.ORG_PROVENCE + "', SUA.GROUP_NAME) <= 0))"
 
                    + "   OR SRP.GROUP_NAME = '*' OR SRP.GROUP_NAME " + SyBaseSQLUtil.isNullNoParam() + "  )"
 
                    + " AND (SUA.ROLE_NAME = SRP.ROLE_NAME OR SRP.ROLE_NAME = '*')"
                    + " AND  SRP.SYSTEM_ID = SRD.SYSTEM_ID"
                    + " AND SRD.RES_PAR_ID = ''"
                    + " ORDER BY SRD.SORT_NO, SRD.RES_ID";
              
            sqlArgs.add(userAccount.getUserId());
            sqlArgs.add(com.sino.sinoflow.constant.WebAttrConstant.TRUE_VALUE);
            sqlArgs.add(com.sino.sinoflow.constant.WebAttrConstant.TRUE_VALUE);
        }
        sqlModel.setArgs(sqlArgs);
		sqlModel.setSqlStr(sqlStr);
		return sqlModel;
	}


	/**
	 * 功能：获取第二层及更深层次URL资源：用于左边菜单栏目
	 * @param resourcePid 资源父编号 不为空
	 * @return SQLModel
	 */
	public SQLModel getAuthorizeDeepModel(String resourcePid) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "";
		if(!userAccount.isSysAdmin()){
//			sqlStr =
//                "SELECT SRD.SYSTEM_ID,\n" +
//                "       SRD.RES_ID,\n" +
//                "       SRD.RES_PAR_ID,\n" +
//                "       SRD.RES_NAME,\n" +
//                "       SRD.RES_ID RES_URL,\n" +
//                "       SRD.IS_POPUP,\n" +
//                "       SRD.POPSCRIPT,\n" +
//                "       SRD.SORT_NO\n" +
//                "  FROM SF_RES_DEFINE SRD\n" +
//                " WHERE SRD.ENABLED = ?\n" +
//                "   AND SRD.VISIBLE = ?\n" +
//                "   AND EXISTS (SELECT NULL\n" +
//                "          FROM SF_USER       SU,\n" +
//                "               SF_ROLE       SR,\n" +
//                "               SF_GROUP      SG,\n" +
//                "               SF_USER_RIGHT SUR,\n" +
//                "               SF_RES_PRIVS  SRP\n" +
//                "         WHERE SU.USER_ID = SUR.USER_ID\n" +
//                "           AND SUR.ROLE_ID = SR.ROLE_ID\n" +
//                "           AND SUR.GROUP_ID = SG.GROUP_ID\n" +
//                "           AND SR.ROLE_NAME = SRP.ROLE_NAME\n" +
//                "           AND SRP.SYSTEM_ID = SRD.SYSTEM_ID\n" +
//                "           AND SU.USER_ID = ?)\n" +
//                " START WITH SRD.RES_PAR_ID " + SyBaseSQLUtil.isNull() + "  AND SRD.RES_ID=?\n" +
//                "CONNECT BY PRIOR SRD.RES_ID = SRD.RES_PAR_ID\n" +
//                " ORDER SIBLINGS BY SRD.SORT_NO";
//			sqlArgs.add(WebAttrConstant.TRUE_VALUE);
//			sqlArgs.add(WebAttrConstant.TRUE_VALUE);
//			sqlArgs.add(userAccount.getUserId());
//            sqlArgs.add(resourcePid);
            sqlStr = "exec sp_get_res '" + resourcePid + "','" + WebAttrConstant.TRUE_VALUE + "','" + WebAttrConstant.TRUE_VALUE + "',0," + userAccount.getUserId() ;
		} else {
//			sqlStr =
//                "SELECT SRD.SYSTEM_ID,\n" +
//                "       SRD.RES_ID,\n" +
//                "       SRD.RES_PAR_ID,\n" +
//                "       SRD.RES_NAME,\n" +
//                "       SRD.RES_ID RES_URL,\n" +
//                "       SRD.IS_POPUP,\n" +
//                "       SRD.POPSCRIPT\n" +
//                "  FROM SF_RES_DEFINE SRD\n" +
//                " WHERE SRD.ENABLED = ?\n" +
//                "   AND SRD.VISIBLE = ?\n" +
//                " START WITH SRD.RES_PAR_ID " + SyBaseSQLUtil.isNull() + " \n" +
//                "        AND SRD.RES_ID = ?\n" +
//                "CONNECT BY PRIOR SRD.RES_ID = SRD.RES_PAR_ID\n" +
//                " ORDER SIBLINGS BY SRD.SORT_NO";
             sqlStr = "exec sp_get_res '" + resourcePid + "','" + WebAttrConstant.TRUE_VALUE + "','" + WebAttrConstant.TRUE_VALUE + "',1," + userAccount.getUserId() ;

			sqlArgs.add(WebAttrConstant.TRUE_VALUE);
			sqlArgs.add(WebAttrConstant.TRUE_VALUE);
			sqlArgs.add(resourcePid);
		}
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据主键获取URL资源。
	 * @param resourceId String
	 * @return SQLModel
	 */
	public SQLModel getAuthResourceByIdModel(String resourceId) {
		SQLModel sqlModel = new SQLModel();
		String sqlStr = "";
		List sqlArgs = new ArrayList();
		if(!userAccount.isSysAdmin()){
			sqlStr = "SELECT"
					 + " SRD.*,"
					 + " SF_RESOURCE_PKG.HAS_CHILD(SRD.RES_ID) HAS_CHILD"
					 + " FROM"
					 + " SF_USER SU,"
					 + " SF_ROLE SR,"
					 + " SF_GROUP SG,"
					 + " SF_USER_RIGHT SUR,"
					 + " SF_RES_DEFINE SRD,"
					 + " SF_RES_PRIVS SRP"
					 + " WHERE"
					 + " SU.USER_ID = SUR.USER_ID"
					 + " AND SUR.ROLE_ID = SR.ROLE_ID"
					 + " AND SUR.GROUP_ID = SG.GROUP_ID"
					 + " AND SR.ROLE_NAME = SRP.ROLE_NAME"
					 + " AND SRP.SYSTEM_ID = SRD.SYSTEM_ID"
					 + " AND SRD.ENABLED = ?"
					 + " AND SRD.VISIBLE = ?"
					 + " AND SU.USER_ID = ?"
					 + " AND SRD.RES_ID = ?";
			sqlArgs.add(WebAttrConstant.TRUE_VALUE);
			sqlArgs.add(WebAttrConstant.TRUE_VALUE);
			sqlArgs.add(userAccount.getUserId());
			sqlArgs.add(resourceId);
		} else {
			sqlStr = "SELECT"
					 + " SRD.*,"
					 + " SF_RESOURCE_PKG.HAS_CHILD(SRD.RES_ID) HAS_CHILD"
					 + " FROM"
					 + " SF_RES_DEFINE SRD"
					 + " WHERE"
					 + " SRD.ENABLED = ?"
					 + " AND SRD.VISIBLE = ?"
					 + " AND SRD.RES_ID = ?";
			sqlArgs.add(WebAttrConstant.TRUE_VALUE);
			sqlArgs.add(WebAttrConstant.TRUE_VALUE);
			sqlArgs.add(resourceId);
		}
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
