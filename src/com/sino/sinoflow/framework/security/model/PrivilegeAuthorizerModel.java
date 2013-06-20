package com.sino.sinoflow.framework.security.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.sinoflow.constant.DictConstant;
import com.sino.sinoflow.constant.WebAttrConstant;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;


/**
 *
 * <p>Title: SinoAMS</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * 
 * 修改：白嘉
 * 修改时间：2008.8.14
 * @version 1.0
 * 
 */
public class PrivilegeAuthorizerModel {
    private SfUserBaseDTO userAccount = null;

    public PrivilegeAuthorizerModel(BaseUserDTO userAccount){
        super();
        this.userAccount = (SfUserBaseDTO)userAccount;
    }

    /**
     * 功能：获取第一层菜单。用于顶层菜单。
     * @return SQLModel
     */
    public SQLModel getAuthorizeRootModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "";
        List sqlArgs = new ArrayList();
        if(!userAccount.isSysAdmin()){
/*
                sqlStr =
						  " SELECT DISTINCT SRD.*"
						+ " FROM " 
						//+ " SF_USER SU,"
						+ " SF_USER_AUTHORITY SUA,"
						+ " SF_GROUP SG,"
						+ " SF_ROLE SR,"
						+ " SF_RES_PRIVS SRP,"
						+ " SF_RES_DEFINE SRD"
						+ " WHERE"
						+ " SUA.USER_ID = ?"
						+ " AND SRD.ENABLED = ?"
						+ " AND SRD.VISIBLE = ?"
						//+ " AND SU.USER_ID = SUA.USER_ID"
						+ " AND SUA.GROUP_NAME = SG.GROUP_NAME"
                        + " AND SUA.ROLE_NAME = SR.ROLE_NAME"
						+ " AND (dbo.SFK_IS_SAME_GROUP_WITH_MASK(SRP.GROUP_NAME, SG.GROUP_NAME) <> 0 OR SRP.GROUP_NAME = '*' OR SRP.GROUP_NAME  " + SyBaseSQLUtil.isNullNoParam() + " )"
						+ " AND (SR.ROLE_NAME = SRP.ROLE_NAME OR SRP.ROLE_NAME = '*')"
						+ " AND  SRP.SYSTEM_ID = SRD.SYSTEM_ID"
						+ " AND SRD.RES_PAR_ID  " + SyBaseSQLUtil.isNullNoParam() + " "
						+ " ORDER BY SRD.SORT_NO, SRD.RES_ID";
*/
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
                    + "   OR SRP.GROUP_NAME = '*' OR SRP.GROUP_NAME  " + SyBaseSQLUtil.isNullNoParam() + "  OR SRP.GROUP_NAME = '')"
                    + " AND (SUA.ROLE_NAME = SRP.ROLE_NAME OR SRP.ROLE_NAME = '*')"
                    + " AND  SRP.SYSTEM_ID = SRD.SYSTEM_ID"
                    + " AND SRD.RES_PAR_ID = ''"
                    + " ORDER BY SRD.SORT_NO, SRD.RES_ID";
            sqlArgs.add(userAccount.getUserId());
            sqlArgs.add(WebAttrConstant.TRUE_VALUE);
            sqlArgs.add(WebAttrConstant.TRUE_VALUE);
           
        } else {
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
        }
        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }


    /**
     * 功能：获取第二层及更深层次URL资源：用于左边菜单栏目
     * @param resourcePid 资源父编号
     * @return SQLModel
     * 
     * 修改SQL
     */
    public SQLModel getAuthorizeDeepModel(String resourcePid) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "";
        if(!userAccount.isSysAdmin()){
/*
            sqlStr =
					  " SELECT DISTINCT " 
        			+ " SRD.RES_PAR_ID,"
					+ " SRD.SYSTEM_ID,"
					+ " SRD.RES_NAME,"
                    + " SRD.COUNT_CLASS,"
                    + " SRD.RES_ID,"
					+ " SRD.RES_URL,"
					+ " SRD.IS_POPUP,"
					+ " SRD.POPSCRIPT,"
					+ " SRD.SORT_NO"
					+ " FROM" 
					+ " SF_USER_AUTHORITY SUA,"
					+ " SF_GROUP SG,"
					+ " SF_ROLE SR,"
					+ " SF_RES_PRIVS SRP,"
					+ " SF_RES_DEFINE SRD"
					+ " WHERE "
					+ " SUA.USER_ID = ?"
					+ " AND SRD.RES_PAR_ID = ?"
					+ " AND SRD.ENABLED = ?"
					+ " AND SRD.VISIBLE = ?"
					+ " AND SUA.GROUP_NAME = SG.GROUP_NAME"
					+ " AND SUA.ROLE_NAME = SR.ROLE_NAME"
					+ " AND (dbo.SFK_IS_SAME_GROUP_WITH_MASK(SRP.GROUP_NAME, SG.GROUP_NAME) <> 0 OR SRP.GROUP_NAME = '*')"
					+ " AND (SR.ROLE_NAME = SRP.ROLE_NAME OR SRP.ROLE_NAME = '*')"
					+ " AND SRD.SYSTEM_ID = SRP.SYSTEM_ID"
					+ " ORDER BY SRD.SORT_NO, SRD.RES_ID";
*/
/*
            sqlStr =
					  " SELECT DISTINCT "
        			+ " SRD.RES_PAR_ID,"
					+ " SRD.SYSTEM_ID,"
					+ " SRD.RES_NAME,"
                    + " SRD.COUNT_CLASS,"
                    + " SRD.RES_ID,"
					+ " SRD.RES_URL,"
					+ " SRD.IS_POPUP,"
					+ " SRD.POPSCRIPT,"
					+ " SRD.SORT_NO"
					+ " FROM"
					+ " SF_USER_AUTHORITY SUA,"
					+ " SF_RES_PRIVS SRP,"
					+ " SF_RES_DEFINE SRD"
					+ " WHERE "
					+ " SUA.USER_ID = ?"
					+ " AND SRD.RES_PAR_ID = ?"
					+ " AND SRD.ENABLED = ?"
					+ " AND SRD.VISIBLE = ?"
                    + " AND ((dbo.SFK_IS_SAME_GROUP_WITH_MASK(SRP.GROUP_NAME, SUA.GROUP_NAME) <> 0"
                    + "   OR (CHARINDEX('$',SRP.GROUP_NAME) > 0 AND CHARINDEX('" + DictConstant.ORG_PROVENCE + "', SUA.GROUP_NAME) <= 0))"
                    + "   OR SRP.GROUP_NAME = '*' OR SRP.GROUP_NAME  " + SyBaseSQLUtil.isNullNoParam() + "  OR SRP.GROUP_NAME = '')"
					+ " AND (SUA.ROLE_NAME = SRP.ROLE_NAME OR SRP.ROLE_NAME = '*')"
					+ " AND SRD.SYSTEM_ID = SRP.SYSTEM_ID"
					+ " ORDER BY SRD.SORT_NO, SRD.RES_ID";

            sqlArgs.add(userAccount.getUserId());
        	sqlArgs.add(resourcePid);
    		sqlArgs.add(WebAttrConstant.TRUE_VALUE);
            sqlArgs.add(WebAttrConstant.TRUE_VALUE);
*/
            sqlStr = "exec sp_get_res '" + resourcePid + "','" + WebAttrConstant.TRUE_VALUE + "','" + WebAttrConstant.TRUE_VALUE + "',0," + userAccount.getUserId() ;
        }else{
/*
        	sqlStr=	"SELECT * FROM("
        		+   " SELECT"
				+   " SRD.SYSTEM_ID, "
				+	" SRD.RES_ID, "
			    +   " SRD.RES_PAR_ID, "
			    +   " SRD.RES_NAME, "
                +   " SRD.COUNT_CLASS,"
                +   " SRD.RES_ID RES_URL, "
			    +   " SRD.IS_POPUP, "
			    +   " SRD.POPSCRIPT, " 
			    +   " SRD.SORT_NO"
			    +   " FROM " 
			    +   " SF_RES_DEFINE SRD "
			    +   " WHERE "
			    +   " SRD.RES_PAR_ID = ? " 
			    +	" AND SRD.VISIBLE = ? "
                + 	" AND SRD.ENABLED = ?) T"
			    +   " ORDER BY SORT_NO,RES_ID";

        	sqlArgs.add(resourcePid);
        	sqlArgs.add(WebAttrConstant.TRUE_VALUE);
            sqlArgs.add(WebAttrConstant.TRUE_VALUE);
*/
            sqlStr = "exec sp_get_res '" + resourcePid + "','" + WebAttrConstant.TRUE_VALUE + "','" + WebAttrConstant.TRUE_VALUE + "',1," + userAccount.getUserId() ;
        }
        
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    
    /**
     * 功能：获取第三层资源：用于左边菜单栏目
     * @param pid 资源父编号
     * @return SQLModel
     * 新加方法
     */
    public SQLModel getMenu3Model(String pid){
    	
    	SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        
       String sqlStr = "";
       if(!userAccount.isSysAdmin()){
/*
           sqlStr =
				  " SELECT DISTINCT " 
    		   	+ " SRD.RES_PAR_ID,"
				+ " SRD.SYSTEM_ID,"
				+ " SRD.RES_NAME,"
                + " SRD.COUNT_CLASS,"
                + " SRD.RES_ID,"
				+ " SRD.RES_URL,"
				+ " SRD.IS_POPUP,"
				+ " SRD.POPSCRIPT,"
				+ " SRD.SORT_NO"
				+ " FROM" 
				+ " SF_USER_AUTHORITY SUA,"
				+ " SF_GROUP SG,"
				+ " SF_ROLE SR,"
				+ " SF_RES_PRIVS SRP,"
				+ " SF_RES_DEFINE SRD"
				+ " WHERE "
				+ " SUA.USER_ID = ?"
				+ " AND SRD.RES_PAR_ID = ?"
				+ " AND SRD.ENABLED = ?"
				+ " AND SRD.VISIBLE = ?"
				+ " AND SUA.GROUP_NAME = SG.GROUP_NAME"
				+ " AND SUA.ROLE_NAME = SR.ROLE_NAME"
				+ " AND (dbo.SFK_IS_SAME_GROUP_WITH_MASK(SRP.GROUP_NAME, SG.GROUP_NAME) <> 0 OR SRP.GROUP_NAME = '*')"
				+ " AND (SR.ROLE_NAME = SRP.ROLE_NAME OR SRP.ROLE_NAME = '*')"
				+ " AND SRD.SYSTEM_ID = SRP.SYSTEM_ID"
				+ " ORDER BY SRD.SORT_NO, SRD.RES_ID";
*/
           sqlStr =
                  " SELECT DISTINCT "
                   + " SRD.RES_PAR_ID,"
                + " SRD.SYSTEM_ID,"
                + " SRD.RES_NAME,"
                + " SRD.COUNT_CLASS,"
                + " SRD.RES_ID,"
                + " SRD.RES_URL,"
                + " SRD.IS_POPUP,"
                + " SRD.POPSCRIPT,"
                + " SRD.SORT_NO"
               + " FROM"
                + " SF_USER_AUTHORITY SUA,"
                + " SF_RES_PRIVS SRP,"
                + " SF_RES_DEFINE SRD"
                + " WHERE "
                + " SUA.USER_ID = ?"
                + " AND SRD.RES_PAR_ID = ?"
                + " AND SRD.ENABLED = ?"
                + " AND SRD.VISIBLE = ?"
                + " AND ((dbo.SFK_IS_SAME_GROUP_WITH_MASK(SRP.GROUP_NAME, SUA.GROUP_NAME) <> 0"
                + "   OR (CHARINDEX('$',SRP.GROUP_NAME) > 0 AND CHARINDEX('" + DictConstant.ORG_PROVENCE + "', SUA.GROUP_NAME) <= 0))"
                + "   OR SRP.GROUP_NAME = '*' OR SRP.GROUP_NAME  " + SyBaseSQLUtil.isNullNoParam() + "  OR SRP.GROUP_NAME = '')"
                + " AND (SUA.ROLE_NAME = SRP.ROLE_NAME OR SRP.ROLE_NAME = '*')"
                + " AND SRD.SYSTEM_ID = SRP.SYSTEM_ID"
                + " ORDER BY SRD.SORT_NO, SRD.RES_ID";

             sqlArgs.add(userAccount.getUserId());
		 	sqlArgs.add(pid);
		 	sqlArgs.add(WebAttrConstant.TRUE_VALUE);
		 	sqlArgs.add(WebAttrConstant.TRUE_VALUE);
       }else{
    	   sqlStr = "SELECT * FROM("
    		    + " SELECT"
	    	   	+ " DISTINCT"
	    	   	+ " SRD.RES_PAR_ID,"
				+ " SRD.SYSTEM_ID,"
				+ " SRD.RES_NAME,"
                + " SRD.COUNT_CLASS,"
                + " SRD.RES_ID,"
				+ " SRD.RES_URL,"
				+ " SRD.IS_POPUP,"
				+ " SRD.POPSCRIPT,"
				+ " SRD.SORT_NO"
				+ " FROM"
				+ " SF_RES_DEFINE SRD"  
				+ " WHERE"  
				+ " SRD.RES_PAR_ID LIKE ?"
				+ " AND SRD.VISIBLE = ? "
                + " AND SRD.ENABLED = ? ) T"
				+ " ORDER BY SORT_NO,RES_ID";
    	   		sqlArgs.add(pid); 
    	   		sqlArgs.add(WebAttrConstant.TRUE_VALUE);
    	        sqlArgs.add(WebAttrConstant.TRUE_VALUE);
       }
    	sqlModel.setArgs(sqlArgs);
    	sqlModel.setSqlStr(sqlStr);
    	
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
/*
            sqlStr = "SELECT"
                     + " SRD.*,"
                     + " SF_RESOURCE_PKG.HAS_CHILD(SRD.RES_ID) HAS_CHILD"
                     + " FROM"
                     + " SF_USER SU,"
                     + " SF_ROLE SR,"
                     + " SF_GROUP SG,"
                     + " SF_USER_AUTHORITY SUA,"
                     + " SF_RES_DEFINE SRD,"
                     + " SF_RES_PRIVS SRP"
                     + " WHERE"
                     + " SU.USER_ID = SUA.USER_ID"
                     + " AND SUA.ROLE_NAME = SR.ROLE_NAME"
                     + " AND SUA.GROUP_NAME = SG.GROUP_NAME"
                     + " AND SR.ROLE_NAME = SRP.ROLE_NAME"
                     + " AND SRP.SYSTEM_ID = SRD.SYSTEM_ID"
                     + " AND SRD.ENABLED = ?"
                     + " AND SRD.VISIBLE = ?"
                     + " AND SU.USER_ID = ?"
                     + " AND SRD.RES_ID = ?";
*/
            sqlStr = "SELECT"
                     + " SRD.*,"
                     + " dbo.SRP_HAS_CHILD(SRD.RES_ID) HAS_CHILD"
                     + " FROM"
                     + " SF_USER SU,"
                     + " SF_USER_AUTHORITY SUA,"
                     + " SF_RES_DEFINE SRD,"
                     + " SF_RES_PRIVS SRP"
                     + " WHERE"
                     + " SU.USER_ID = SUA.USER_ID"
                     + " AND (SUA.ROLE_NAME = SRP.ROLE_NAME OR SRP.ROLE_NAME = '*')"
                     + " AND ((dbo.SFK_IS_SAME_GROUP_WITH_MASK(SRP.GROUP_NAME, SUA.GROUP_NAME) <> 0"
                     + "   OR (CHARINDEX('$',SRP.GROUP_NAME) > 0 AND CHARINDEX('" + DictConstant.ORG_PROVENCE + "', SUA.GROUP_NAME) <= 0))"
                     + "   OR SRP.GROUP_NAME = '*' OR SRP.GROUP_NAME  " + SyBaseSQLUtil.isNullNoParam() + "  OR SRP.GROUP_NAME = '')"
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
                     + " dbo.SRP_HAS_CHILD(SRD.RES_ID) HAS_CHILD"
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
