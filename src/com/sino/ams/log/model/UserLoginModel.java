package com.sino.ams.log.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class UserLoginModel {
    private SfUserDTO userAccount = null;
	private boolean fromPDA = false;

    public UserLoginModel(BaseUserDTO userAccount) {
        this.userAccount = (SfUserDTO) userAccount;
    }

	/**
	 * 设置参数后可以为后续SQL构造提供新值
	 * @param userAccount SfUserDTO
	 */
	public void setDTOParameter(SfUserDTO userAccount) {
		this.userAccount = userAccount;
	}


	/**
	 * 功能：设置用户是否从PDA登录
	 * @param fromPDA boolean
	 */
	public void setFromPDA(boolean fromPDA) {
		this.fromPDA = fromPDA;
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
						+ " SU.OA_NAME,"
						+ " SU.DISPLAY_ORDER,"
						+ " SU.PASSWORD_DATE,"
						+ " SU.ORGANIZATION_ID,"
						+ " SU.IS_INNER,"
						+ " SU.CREATION_DATE,"
						+ " SU.CREATED_BY,"
						+ " SU.LAST_UPDATE_DATE,"
						+ " SU.LAST_UPDATE_BY,"
						+ " SU.EMPLOYEE_NUMBER,"
						+ " SU.ENABLED, "
						+ " SU.IS_MAINTAIN_USER,"
						+ " AMC.COMPANY_ID MAINTAIN_COMPANY,"
						+ " AMC.NAME MAINTAIN_COMPANY_NAME,"
						+ " AME.EMPLOYEE_ID,"
						+ " AMD.DEPT_CODE,"
						+ " AMD.DEPT_NAME,"
						+ " EOCM.COMPANY,"
						+ " EOCM.COMPANY_CODE,"
						+ " EOCM.BOOK_TYPE_CODE,"
						+ " EOCM.IS_TD,"
						+ " EOCM.IS_TT,"
						+ " EOCM.BOOK_TYPE_NAME,"
						+ " EOCM.MATCH_ORGANIZATION_ID"
						+ " FROM"
						+ " SF_USER              SU,"
						+ " AMS_MIS_EMPLOYEE     AME,"
						+ " AMS_MIS_DEPT         AMD,"
						+ " ETS_OU_CITY_MAP      EOCM,"
						+ " AMS_MAINTAIN_COMPANY AMC"
						+ " WHERE"
						+ " SU.EMPLOYEE_NUMBER *= AME.EMPLOYEE_NUMBER"
						+ " AND EOCM.COMPANY_CODE *=AME.COMPANY_CODE " //AME.COMPANY_CODE可能为空
						//+ " AND EOCM.COMPANY_CODE *= AMD.COMPANY_CODE "
						+ " AND AME.DEPT_CODE *= AMD.DEPT_CODE"
						+ " AND SU.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
						+ " AND SU.MAINTAIN_COMPANY *= AMC.COMPANY_ID"
						+ " AND ISNULL(SU.END_DATE, GETDATE()) >= GETDATE()"
						+ " AND EOCM.ENABLED = 'Y'"
//						+ " AND SU.ENABLED = 'Y'"
						+ " AND UPPER(SU.LOGIN_NAME) = UPPER(?)"
						
						+ " AND ISNULL(SU.PASSWORD_DATE, GETDATE()) >= GETDATE()";
		
		sqlArgs.add(userAccount.getLoginName());
		if(!fromPDA){
            sqlStr += " AND SU.PASSWORD=dbo.SFK_ENCODE(?)";
            sqlArgs.add(userAccount.getPassword());
		}
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 读取系统地点明细配置
     * @return SQLModel
     */
    public SQLModel getAddrConfigDataModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
						+ " CONFIG_ID,"
						+ " CONFIG_TYPE,"
						+ " CHECKED_FLAG "
						+ " FROM"
						+ " AMS_ADDRESS_CONFIG ";
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

	/**
	 * 功能：获取用户所属公司在途库及其地点
	 * @return SQLModel
	 */
	public SQLModel getTmpInvAddressModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
						+ " AOA.ADDRESS_ID,"
						+ " EO.WORKORDER_OBJECT_NO"
						+ " FROM"
						+ " AMS_OBJECT_ADDRESS AOA,"
						+ " ETS_OBJECT         EO"
						+ " WHERE  AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO"
						+ " AND AOA.BOX_NO = '0000'"
						+ " AND AOA.NET_UNIT = '0000'"
						+ " AND EO.ORGANIZATION_ID = ?"
						+ " AND EO.OBJECT_CATEGORY = ?";
		sqlArgs.add(userAccount.getOrganizationId());
		sqlArgs.add("74");
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
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