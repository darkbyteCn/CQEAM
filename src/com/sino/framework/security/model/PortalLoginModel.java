package com.sino.framework.security.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.StrUtil;
import com.sino.framework.security.bean.UserParameter;

/**
 * 
 * @系统名称: Portal登录
 * @功能描述:
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本：1.0
 * @开发作者: sj
 * @创建时间: Oct 12, 2011
 * 
 * FND_USER_PORTAL_MATCH: 用户对照
 * 
 */
public class PortalLoginModel {
	public SQLModel getHasUserModel(UserParameter userParameter)
			throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String loginName = userParameter.getLoginName();
		String loginPwd = userParameter.getLoginPwd();
		String srcPage = userParameter.getSource();// 新增，用于处理从OA系统Portal登录的情况
		
		StringBuilder sqlStr = new StringBuilder();
		sqlStr.append( " SELECT \n ");
		sqlStr.append( " 1 \n ");
		sqlStr.append( " FROM  \n ");
		sqlStr.append( " SF_USER SU \n ");
		sqlStr.append( " WHERE  \n ");
		sqlStr.append( " SU.LOGIN_NAME = ?  \n "); 
		sqlArgs.add(loginName.toUpperCase()); 
		
		if (StrUtil.isEmpty(srcPage) || !srcPage.equals("oa")) {// 并非从OA系统Portal登录，需要验证登录密码
			sqlStr.append( " AND SU.PASSWORD=dbo.SFK_ENCODE(?) \n "); // 增加自定义用户的验证
			sqlArgs.add(loginPwd);
		}
		sqlModel.setSqlStr( sqlStr.toString() );
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	public SQLModel getUserDataModel(UserParameter userParameter)
			throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String loginName = userParameter.getLoginName();
		String loginPwd = userParameter.getLoginPwd();
		String srcPage = userParameter.getSource();// 新增，用于处理从OA系统Portal登录的情况
		String sqlStr = "SELECT IUV.USER_ID,\n" + "       IUV.EMPLOYEE_ID,\n"
				+ "       IUV.LOGIN_NAME,\n" + "       IUV.USER_NAME,\n"
				+ "       IUV.USER_TYPE,\n" + "       IUV.UNIT_ID UNIT_ID,\n"
				+ "       IUV.UNIT_NAME UNIT_NAME,\n" + "       IUV.EMAIL,\n"
				+ "       IUV.TEL TELEPHONE,\n" + "       IUV.ORG_CODE,\n"
				+ "       IR.ROLE_ID,\n" + "       IR.ROLE_NAME,\n"
				+ "       IR.ROLE_DESC,\n" + "       FUOV.ORGANIZATION_ID,\n"
				+ "       FUOV.COMPANY_CODE,\n" + "       FUOV.OU_NAME,\n"
				+ "       SUBSTR(FUOV.OU_NAME, 4) COMPANY_NAME,\n"
				+ "       FUOV.LOCATION_ID,\n" + "       FUOV.LOCATION_CODE,\n"
				+ "       FUOV.ADDRESS_LINE_1 SHIP_TO_ADDRESS,\n"
				+ "       FUIOV.ORGANIZATION_ID INV_ORG_ID,\n"
				+ "       FUIOV.IO_NAME INV_ORG_NAME,\n"
				+ "       FUIOV.ORGANIZATION_CODE\n"
				+ "  FROM IES_USER_VENDOR_V IUV,\n"
				+ "       IES_ROLE          IR,\n"
				+ "       IES_USER_INFO     IUI,\n"
				+ "       FND_USER_OU_V     FUOV,\n"
				+ "       FND_USER_IO_OU_V  FUIOV\n"
				+ " WHERE IUV.USER_ID = IUI.USER_ID\n"
				+ "   AND IUI.ROLE_ID = IR.ROLE_ID\n"
				+ "   AND IUV.USER_ID = FUOV.USER_ID(+)\n"
				+ "   AND IUV.USER_ID = FUIOV.USER_ID(+)"
				+ " AND UPPER(IUV.LOGIN_NAME) = NVL(?, UPPER(IUV.LOGIN_NAME))";
		sqlArgs.add(loginName.toUpperCase());
		if (StrUtil.isEmpty(srcPage) || !srcPage.equals("oa")) {// 并非从OA系统Portal登录，需要验证登录密码
			sqlStr += " AND 1 = IES_USER_PKG.LOGIN(?, ?)"; // 增加自定义用户的验证
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
	 * 
	 * @param portalUserId
	 *            OA_NAME
	 * @return SQLModel
	 */
	public SQLModel getLoginNameByOaName(String portalUserId) {
		SQLModel sqlModel = new SQLModel();
		String sqlStr = "SELECT FU.EMPLOYEE_ID, FU.USER_NAME LOGIN_NAME, HU.OA_NAME\n"
				+ "  FROM HR_USER HU, FND_USER FU\n"
				+ " WHERE FU.EMPLOYEE_ID = HU.PERSON_ID\n"
				+ "   AND (FU.END_DATE > SYSDATE OR FU.END_DATE IS NULL)\n"
				+ "   AND UPPER(HU.OA_NAME) = ?";
		List sqlArgs = new ArrayList();
		sqlArgs.add(portalUserId.toUpperCase());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	public SQLModel getLoginName(String employeeId) {
		SQLModel sqlModel = new SQLModel();
		String sqlStr = "SELECT" + " FU.USER_NAME  LOGIN_NAME" + " FROM"
				+ " FND_USER FU" + " WHERE" + " UPPER(FU.EMPLOYEE_ID) = ?";
		List sqlArgs = new ArrayList();
		sqlArgs.add(employeeId.toUpperCase());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 获取根据OA登录名得到物流方登录名Model
	 * 
	 * @param portalUserId
	 * @return
	 */
	public SQLModel getUserPortalModel(String portalUserId) {
		SQLModel sqlModel = new SQLModel();
		StringBuilder sqlStr = new StringBuilder();
		// String sqlStr =
		sqlStr.append("SELECT IUV.LOGIN_NAME\n");
		sqlStr.append("  FROM IES_USER_ALL_V IUV, ");
		sqlStr.append("		  FND_USER_PORTAL_MATCH FUP\n");
		sqlStr.append(" WHERE UPPER(IUV.LOGIN_NAME) = UPPER(FUP.LOGIN_NAME)\n");
		sqlStr.append("   AND UPPER(FUP.OA_NAME) = ?");
		List sqlArgs = new ArrayList();
		sqlArgs.add(portalUserId.toUpperCase());
		sqlModel.setSqlStr( sqlStr.toString() );
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}

