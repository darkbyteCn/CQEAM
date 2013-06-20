package com.sino.sinoflow.framework.security.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.sinoflow.constant.DictConstant;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;


/**
 *
 * <p>Title: SinoAMS</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class PrivilegeAuthenticatorModel {

    private SfUserBaseDTO userAccount = null;

    public PrivilegeAuthenticatorModel(SfUserBaseDTO userAccount){
        super();
        this.userAccount = userAccount;
    }

    /**
     * 功能：获取检查指定URL资源是否需要权限验证的SQLModel
     * @param requestURL 待检查的URL资源
     * @return SQLModel
     */
    public SQLModel getHasInControlModel(String requestURL){
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT"
                        + " *"
                        + " FROM"
                        + " SF_RES_DEFINE SRD"
                        + " WHERE"
                        + " SRD.RES_URL = ?";
        List sqlArgs = new ArrayList();
        sqlArgs.add(requestURL);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：获取对指定URL资源权限验证的SQLModel
     * @param requestURL 待验证的URL资源
     * @return SQLModel
     */
    public SQLModel getAuthenticateModel(String requestURL){
        SQLModel sqlModel = new SQLModel();
/*
        String sqlStr = "SELECT"
                        + " 1"
                        + " FROM"
                        + " SF_USER SU,"
                        + " SF_GROUP SG,"
                        + " SF_ROLE SR,"
                        + " SF_USER_AUTHORITY SUA,"
                        + " SF_RES_DEFINE SRD,"
                        + " SF_RES_PRIVS SRP"
                        + " WHERE"
                        + " SUA.USER_ID = SU.USER_ID"
                        + " AND SUA.GROUP_NAME = SG.GROUP_NAME"
                        + " AND SUA.ROLE_NAME = SR.ROLE_NAME"
                        + " AND (SR.ROLE_NAME = SRP.ROLE_NAME OR SRP.ROLE_NAME LIKE '*')"
                        + " AND (SG.GROUP_NAME = SRP.GROUP_NAME OR SRP.GROUP_NAME LIKE '*')"	
                        + " AND SRP.SYSTEM_ID = SRD.SYSTEM_ID"
                        + " AND SU.LOGIN_NAME = ?"
                        + " AND SRD.RES_URL = ?";
*/
        String sqlStr = "SELECT"
                        + " 1 FNAME"
                        + " FROM"
                        + " SF_USER SU,"
                        + " SF_USER_AUTHORITY SUA,"
                        + " SF_RES_DEFINE SRD,"
                        + " SF_RES_PRIVS SRP"
                        + " WHERE"
                        + " SUA.USER_ID = SU.USER_ID"
                        + " AND (SUA.ROLE_NAME = SRP.ROLE_NAME OR SRP.ROLE_NAME LIKE '*')"
                        + " AND ((dbo.SFK_IS_SAME_GROUP_WITH_MASK(SRP.GROUP_NAME, SUA.GROUP_NAME) <> 0 OR CHARINDEX('$',SRP.GROUP_NAME) > 0)"
                        + "   AND ((CHARINDEX('" + DictConstant.ORG_PROVENCE + "',SRP.GROUP_NAME) > 0 AND CHARINDEX('" + DictConstant.ORG_PROVENCE + "',SUA.GROUP_NAME) > 0)"
                        + "   OR (CHARINDEX('$',SRP.GROUP_NAME) > 0 AND CHARINDEX('" + DictConstant.ORG_PROVENCE + "', SUA.GROUP_NAME) <= 0))"
                        + "   OR SRP.GROUP_NAME = '*' OR SRP.GROUP_NAME  " + SyBaseSQLUtil.isNullNoParam() + "  OR SRP.GROUP_NAME = '')"
                        + " AND SRP.SYSTEM_ID = SRD.SYSTEM_ID"
                        + " AND SU.LOGIN_NAME = ?"
                        + " AND SRD.RES_URL LIKE ?";

        List sqlArgs = new ArrayList();
        sqlArgs.add(userAccount.getLoginName());
        sqlArgs.add(requestURL + "%");
        //sqlArgs.add(requestURL);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
