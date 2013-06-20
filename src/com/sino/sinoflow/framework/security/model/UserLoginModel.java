package com.sino.sinoflow.framework.security.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;


/**
 * <p>Title: SinoAMS</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class UserLoginModel {
    private SfUserBaseDTO userAccount = null;

    public UserLoginModel(BaseUserDTO userAccount) {
        this.userAccount = (SfUserBaseDTO) userAccount;
    }

    /**
     * 设置参数后可以为后续SQL构造提供新值
     * @param userAccount SfUserBaseDTO
     */
    public void setDTOParameter(SfUserBaseDTO userAccount) {
        this.userAccount = userAccount;
    }


    /**
     * 功能：获取用户信息SQL
     * @return SQLModel
     */
    public SQLModel getUserLoginModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT"
                + " SU.USER_ID,"
                + " SU.LOGIN_NAME,"
                + " SU.PASSWORD,"
                + " SU.USERNAME,"
                + " SU.WORKNO,"
                + " SU.OFFICE_TEL,"
                + " SU.FAX,"
                + " SU.MOBILE_PHONE,"
                + " SU.END_DATE,"
                + " SU.EMAIL,"
                + " SU.DISPLAY_ORDER,"
                + " SU.PASSWORD_DATE,"
                + " SU.ORGANIZATION_ID,"
                + " SU.IS_INNER,"
                + " SU.CREATION_DATE,"
                + " SU.CREATED_BY,"
                + " SU.LAST_UPDATE_DATE,"
                + " SU.LAST_UPDATE_BY,"
                + " SU.EMPLOYEE_NUMBER,"
                + " SU.IS_MAINTAIN_USER,"
                + " AMC.COMPANY_ID MAINTAIN_COMPANY,"
                + " AMC.NAME MAINTAIN_COMPANY_NAME,"
                + " AME.EMPLOYEE_ID,"
                + " AME.EMPLOYEE_NUMBER,"
                + " AMD.DEPT_CODE,"
                + " AMD.DEPT_NAME,"
                + " EOCM.COMPANY,"
                + " EOCM.COMPANY_CODE,"
                + " EOCM.BOOK_TYPE_CODE,"
                + " EOCM.IS_TD,"
                + " EOCM.BOOK_TYPE_NAME"
                + " FROM"
                + " SF_USER              SU,"
                + " AMS_MIS_EMPLOYEE     AME,"
                + " AMS_MIS_DEPT         AMD,"
                + " ETS_OU_CITY_MAP      EOCM,"
                + " AMS_MAINTAIN_COMPANY AMC"
                + " WHERE"
                + " SU.EMPLOYEE_NUMBER *= AME.EMPLOYEE_NUMBER"
                + " AND AME.DEPT_CODE *= AMD.DEPT_CODE"
                + " AND SU.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
                + " AND SU.MAINTAIN_COMPANY *= AMC.COMPANY_ID"
                + " AND ((SU.START_DATE  " + SyBaseSQLUtil.isNullNoParam() + "  OR SU.START_DATE = '' OR SU.START_DATE <= GETDATE()) AND (SU.END_DATE  " + SyBaseSQLUtil.isNullNoParam() + "  OR SU.END_DATE = '' OR SU.END_DATE >= GETDATE()))"
                + " AND EOCM.ENABLED = 'Y'"
                + " AND UPPER(SU.LOGIN_NAME) = UPPER(?)";
        sqlArgs.add(userAccount.getLoginName());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：获取用户信息SQL
     * @return SQLModel
     */
    public SQLModel getUserLoginModelForSSO() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT"
                + " SU.USER_ID,"
                + " SU.USERNAME,"
                + " SU.PASSWORD,"
                + " SU.EMPLOYEE_ID,"
                + " SU.OFFICE_TEL,"
                + " SU.FAX,"
                + " SU.MOBILE_PHONE,"
                + " SU.EMAIL,"
                + " SU.ORGANIZATION,"
                + " SU.WORK_SCHEDULE_ID,"
                + " SU.ORGANIZATION_ID"
                + " SU.LOGIN_NAME,"
                + " FROM"
                + " SF_USER           SU"
                + " WHERE"
                + " UPPER(SU.LOGIN_NAME) = UPPER(?)";
        sqlArgs.add(userAccount.getLoginName());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getUsersPendingTray() {
        SQLModel sqlModel = new SQLModel();

        StringBuffer sb = new StringBuffer("SELECT SU.USER_ID,SU.MOBILE_PHONE,COUNT(1) COUNTNO");
        sb.append(" FROM SF_ACT_INFO SAI,");
        sb.append(" SF_USER SU");
        sb.append(" WHERE ((SAI.SFACT_SIGN_USER = SU.LOGIN_NAME AND");
        sb.append(" dbo.SFK_GET_DELEGATE_USER(SU.LOGIN_NAME) = '') OR");
        sb.append(" SAI.SFACT_SIGN_USER = dbo.SFK_GET_ASSIGN_USER(SU.LOGIN_NAME))");
        sb.append(" AND (SAI.SFACT_SIGN_STATUS = 1 AND SAI.SFACT_SUSPEND_FLAG <> 1)");
        sb.append(" AND SAI.SFACT_COMPLETE_STATUS <> 1");
        sb.append(" GROUP BY SU.USER_ID,SU.MOBILE_PHONE");
        sqlModel.setSqlStr(sb.toString());

        return sqlModel;
    }

    public SQLModel getUsersPendingTrayDetail() {
        SQLModel sqlModel = new SQLModel();
        StringBuffer sb = new StringBuffer("SELECT SAI.SFACT_TASK_ATTRIBUTE_4 TASK_ATTRIBUTE4 ,SU.USER_ID USER_ID,SU.LOGIN_NAME,SU.MOBILE_PHONE MOBILE_PHONE,");
        sb.append("CCI.CONTRACT_NAME CONTRACT_NAME, CCI.CREATED_BY, SU1.USERNAME USERNAME,SAI.SFACT_ACT_ID SFACT_ACT_ID");
        sb.append(" FROM SF_ACT_INFO SAI, SF_USER SU,SF_USER SU1, CMS_CONTRACT_INFO CCI ");
        sb.append(" WHERE (SAI.SFACT_SIGN_USER = SU.LOGIN_NAME AND dbo.SFK_GET_DELEGATE_USER(SU.LOGIN_NAME) = '' ");
        sb.append(" OR SAI.SFACT_SIGN_USER = dbo.SFK_GET_ASSIGN_USER(SU.LOGIN_NAME)) AND CCI.CONTRACT_ID = CONVERT(INT, SAI.SFACT_APPL_ID) ");
        sb.append(" AND SU1.USER_ID = CCI.CREATED_BY AND SAI.SFACT_SIGN_STATUS = 1  ");
        sb.append(" AND SAI.SFACT_SUSPEND_FLAG <> 1 and SAI.SFACT_COMPLETE_STATUS <> 1 ");
        sb.append(" AND SAI.SFACT_SIGN_USER <> 'SYSTEM' AND SAI.SFACT_TASK_NAME <> 'SPLIT' AND SAI.SFACT_TASK_NAME <> 'JOIN' ");
        sb.append(" AND NOT EXISTS ( SELECT * FROM CMS_SHORTMES_SENT SENT WHERE SAI.SFACT_ACT_ID = SENT.SFACT_ACT_ID) ");
        sb.append(" ORDER BY SU.USER_ID  ");
        sqlModel.setSqlStr(sb.toString());

        return sqlModel;
    }

    public SQLModel getEmployeeIdModel(String employeeNumber, int organizationId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT EMPLOYEE_ID FROM AMS_MIS_EMPLOYEE AME,ETS_OU_CITY_MAP EOCM \n" +
                "   WHERE EOCM.COMPANY_CODE=AME.COMPANY_CODE AND EOCM.ORGANIZATION_ID=? AND AME.EMPLOYEE_NUMBER=?";

        sqlArgs.add(organizationId);
        sqlArgs.add(employeeNumber);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }
}