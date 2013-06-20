package com.sino.hn.portal.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.log.model.UserLoginModel;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.hn.portal.dto.UserParameter;

/** 
 * 
 * @系统名称:  
 * @功能描述: 单点登录
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本：1.0
 * @开发作者: sj
 * @创建时间: Oct 24, 2011
 */
@SuppressWarnings("unchecked")
public class UserInfoModel extends UserLoginModel {

    public UserInfoModel(BaseUserDTO userAccount) {
		super(userAccount);
		// TODO Auto-generated constructor stub
	}
    
    /**
     * 功能：获取用户信息SQL
     * @return SQLModel
     */
    public SQLModel getUserLoginModel(String oaName , String isTd) {
    	
    	if( StrUtil.isEmpty( isTd ) ){
    		isTd = "N";
    	}
    	
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append( " SELECT \n " );
		sb.append( " SU.USER_ID, \n " );
		sb.append( " SU.LOGIN_NAME, \n " );
		sb.append( " SU.PASSWORD, \n " );
		sb.append( " SU.USERNAME, \n " );
		sb.append( " SU.WORKNO, \n " );
		sb.append( " SU.OFFICE_TEL, \n " );
		sb.append( " SU.FAX, \n " );
		sb.append( " SU.MOBILE_PHONE, \n " );
		sb.append( " SU.END_DATE, \n " );
		sb.append( " SU.EMAIL, \n " );
		sb.append( " SU.DISPLAY_ORDER, \n " );
		sb.append( " SU.PASSWORD_DATE, \n " );
		sb.append( " SU.ORGANIZATION_ID, \n " );
		sb.append( " SU.IS_INNER, \n " );
		sb.append( " SU.CREATION_DATE, \n " );
		sb.append( " SU.CREATED_BY, \n " );
		sb.append( " SU.LAST_UPDATE_DATE, \n " );
		sb.append( " SU.LAST_UPDATE_BY, \n " );
		sb.append( " SU.EMPLOYEE_NUMBER, \n " );
		sb.append( " SU.IS_MAINTAIN_USER, \n " );
		sb.append( " AMC.COMPANY_ID MAINTAIN_COMPANY, \n " );
		sb.append( " AMC.NAME MAINTAIN_COMPANY_NAME, \n " );
		sb.append( " AME.EMPLOYEE_ID, \n " );
		sb.append( " AME.EMPLOYEE_NUMBER, \n " );
		sb.append( " AMD.DEPT_CODE, \n " );
		sb.append( " AMD.DEPT_NAME, \n " );
		sb.append( " EOCM.COMPANY, \n " );
		sb.append( " EOCM.COMPANY_CODE, \n " );
		sb.append( " EOCM.BOOK_TYPE_CODE, \n " );
		sb.append( " EOCM.IS_TD, \n " );
		sb.append( " EOCM.BOOK_TYPE_NAME, \n " );
		sb.append( " EOCM.MATCH_ORGANIZATION_ID \n ");
		sb.append( " FROM \n ");
		sb.append( " SF_USER              SU, \n " );
		sb.append( " AMS_MIS_EMPLOYEE     AME, \n " );
		sb.append( " AMS_MIS_DEPT         AMD, \n " );
		sb.append( " ETS_OU_CITY_MAP      EOCM, \n " );
		sb.append( " AMS_MAINTAIN_COMPANY AMC \n ");
		sb.append( " WHERE \n ");
		sb.append( " SU.EMPLOYEE_NUMBER *= AME.EMPLOYEE_NUMBER \n ");
		sb.append( " AND AME.DEPT_CODE *= AMD.DEPT_CODE \n ");
		sb.append( " AND SU.ORGANIZATION_ID = EOCM.ORGANIZATION_ID \n ");
		sb.append( " AND SU.MAINTAIN_COMPANY *= AMC.COMPANY_ID \n ");
		sb.append( " AND ISNULL(SU.END_DATE, GETDATE()) >= GETDATE() \n ");
		sb.append( " AND EOCM.ENABLED = 'Y' \n ");
		sb.append( " AND EOCM.IS_TD = ? \n ");
		sb.append( " AND ( UPPER(SU.LOGIN_NAME) = UPPER(?) OR UPPER(SU.OA_NAME) = UPPER(?) ) " );
		
		sqlArgs.add( isTd );
		sqlArgs.add( oaName );
		sqlArgs.add( oaName );
		 
        sqlModel.setSqlStr( sb.toString() );
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

	/**
     * 功能：构造检查用户是否存在的SQL语句。</B>
     * @param userParameter UserParameter
     * @return String
     * @throws SQLModelException
     */
    public SQLModel getHasUserModel(UserParameter userParameter) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String loginName = userParameter.getLoginName();
        String loginPwd = userParameter.getLoginPwd();
        String srcPage = userParameter.getSource();//新增，用于处理从OA系统Portal登录的情况
        String sqlStr = "SELECT "
                + " 1"
                + " FROM "
                + " IES_USER_VENDOR_V IUV"
                + " WHERE"
                + " UPPER(IUV.LOGIN_NAME) = NVL(?, UPPER(IUV.LOGIN_NAME))";
        sqlArgs.add(loginName.toUpperCase());
        if (StrUtil.isEmpty(srcPage) || !srcPage.equals("oa")) {//并非从OA系统Portal登录，需要验证登录密码
            sqlStr += " AND 1 = IES_USER_PKG.LOGIN(?, ?)";       //增加自定义用户的验证
            sqlArgs.add(loginName.toUpperCase());
            sqlArgs.add(loginPwd);
        }
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getUserDataModel(UserParameter userParameter) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String loginName = userParameter.getLoginName();
        String loginPwd = userParameter.getLoginPwd();
        String srcPage = userParameter.getSource();//新增，用于处理从OA系统Portal登录的情况
        String sqlStr = "SELECT IUV.USER_ID,\n" +
                "       IUV.EMPLOYEE_ID,\n" +
                "       IUV.LOGIN_NAME,\n" +
                "       IUV.USER_NAME,\n" +
                "       IUV.USER_TYPE,\n" +
                "       IUV.UNIT_ID UNIT_ID,\n" +
                "       IUV.UNIT_NAME UNIT_NAME,\n" +
                "       IUV.EMAIL,\n" +
                "       IUV.TEL TELEPHONE,\n" +
                "       IUV.ORG_CODE,\n" +
                "       IR.ROLE_ID,\n" +
                "       IR.ROLE_NAME,\n" +
                "       IR.ROLE_DESC,\n" +
                "       FUOV.ORGANIZATION_ID,\n" +
                "       FUOV.COMPANY_CODE,\n" +
                "       FUOV.OU_NAME,\n" +
                "       SUBSTR(FUOV.OU_NAME, 4) COMPANY_NAME,\n" +
                "       FUOV.LOCATION_ID,\n" +
                "       FUOV.LOCATION_CODE,\n" +
                "       FUOV.ADDRESS_LINE_1 SHIP_TO_ADDRESS,\n" +
                "       FUIOV.ORGANIZATION_ID INV_ORG_ID,\n" +
                "       FUIOV.IO_NAME INV_ORG_NAME,\n" +
                "       FUIOV.ORGANIZATION_CODE\n" +
                "  FROM IES_USER_VENDOR_V IUV,\n" +
                "       IES_ROLE          IR,\n" +
                "       IES_USER_INFO     IUI,\n" +
                "       FND_USER_OU_V     FUOV,\n" +
                "       FND_USER_IO_OU_V  FUIOV\n" +
                " WHERE IUV.USER_ID = IUI.USER_ID\n" +
                "   AND IUI.ROLE_ID = IR.ROLE_ID\n" +
                "   AND IUV.USER_ID = FUOV.USER_ID(+)\n" +
                "   AND IUV.USER_ID = FUIOV.USER_ID(+)"
                + " AND UPPER(IUV.LOGIN_NAME) = NVL(?, UPPER(IUV.LOGIN_NAME))";
        sqlArgs.add(loginName.toUpperCase());
        if (StrUtil.isEmpty(srcPage) || !srcPage.equals("oa")) {//并非从OA系统Portal登录，需要验证登录密码
            sqlStr += " AND 1 = IES_USER_PKG.LOGIN(?, ?)";       //增加自定义用户的验证
            sqlArgs.add(loginName.toUpperCase());
            sqlArgs.add(loginPwd);
        }
        sqlStr += " ORDER BY IUV.USER_ID, IR.ROLE_ID";
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 根据OA_NAME查找MIS登录名
     * @param portalUserId   OA_NAME
     * @return  SQLModel
     */
    public SQLModel getLoginNameByOaName(String portalUserId) {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT " +
                "       FU.EMPLOYEE_ID, FU.USER_NAME LOGIN_N, HU.OA_NAME\n" +
                "  FROM HR_USER HU, FND_USER FU\n" +
                " WHERE FU.EMPLOYEE_ID = HU.PERSON_ID\n" +
                "   AND (FU.END_DATE > SYSDATE OR FU.END_DATE IS NULL)\n" +
                "   AND UPPER(HU.OA_NAME) = ?";
        List sqlArgs = new ArrayList();
        sqlArgs.add(portalUserId.toUpperCase());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        Logger.logWarn("portalUserName="+portalUserId);
        return sqlModel;
    }

    public SQLModel getLoginName(String employeeId) {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT"
                + " FU.USER_NAME  LOGIN_NAME"
                + " FROM"
                + " FND_USER FU"
                + " WHERE"
                + " UPPER(FU.EMPLOYEE_ID) = ?";
        List sqlArgs = new ArrayList();
        sqlArgs.add(employeeId.toUpperCase());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getUserPortalModel(String portalUserId) {
        SQLModel sqlModel = new SQLModel();
          
        String sqlStr = "SELECT"
                + " IUV.LOGIN_NAME"
                + " FROM"
                + " IES_USER_V IUV,"
                + " FND_USER_PORTAL_MATCH FUPM"
                + " WHERE"
                + " UPPER(IUV.LOGIN_NAME) = UPPER(FUPM.LOGIN_NAME)"
                + " AND UPPER(FUPM.OA_NAME) = ?";
        List sqlArgs = new ArrayList();
        sqlArgs.add(portalUserId.toUpperCase());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
