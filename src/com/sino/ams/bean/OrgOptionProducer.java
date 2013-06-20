package com.sino.ams.bean;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.QueryException;
import com.sino.base.util.StrUtil;
import com.sino.base.web.DatabaseForWeb;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * User: zhoujs
 * Date: 2009-3-27 15:29:45
 * Function:公司相关的下拉框
 */
@SuppressWarnings("unchecked")
public class OrgOptionProducer extends OptionProducer {
    public OrgOptionProducer(SfUserDTO userAccount, Connection conn) {
        super(userAccount, conn);
    }

    /**
     * 公司代码下拉框
     * @param selectValue String
     * @return String
     * @throws QueryException 查询异常
     */
    public String getCompanyOption(String selectValue) throws QueryException {
        return getCompanyOption(selectValue, "");
    }

    /**
     * @param selectValue
     * @param range       'Y'-TD;'N'-非TD;''-ALL
     * @return
     * @throws QueryException
     */
    public String getCompanyOption(String selectValue, String range) throws QueryException {
        SQLModel sqlModel = new SQLModel();
        boolean addBlank = false;
        List sqlArgs = new ArrayList();
        String sqlStr =
                "SELECT COMPANY_CODE, COMPANY_CODE || STR_REPLACE(COMPANY, 'OU_', ' ') COMPANY\n" +
                        "  FROM ETS_OU_CITY_MAP \n" +
                        "   WHERE ENABLED='Y'";
        if (StrUtil.isNotEmpty(range)) {
            sqlStr += "   \n  AND IS_TD=?";
            sqlArgs.add(range);
        }
        if (userAccount.isProvinceUser() || userAccount.isSysAdmin()) {
            addBlank = true;
        } else {
            sqlStr += " \n  AND ORGANIZATION_ID=?";
            sqlArgs.add(userAccount.getOrganizationId());
        }
        sqlStr += " \nORDER BY COMPANY_CODE";

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
        return webFieldProducer.getOptionHtml(selectValue, addBlank);

    }

    /**
     * 资产账簿下拉框
     * @param selectValue String
     * @return String
     * @throws QueryException 查询异常
     */
    public String getBookTypeCodeOption(String selectValue) throws QueryException {
        return getBookTypeCodeOption(selectValue, "");
    }

    /**
     * 资产账簿下拉框
     * @param selectValue
     * @param range       'Y'-TD;'N'-非TD;''-ALL
     * @return
     * @throws QueryException
     */
    public String getBookTypeCodeOption(String selectValue, String range) throws QueryException {
        SQLModel sqlModel = new SQLModel();
        boolean addBlank = false;
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT BOOK_TYPE_CODE, COMPANY_CODE || STR_REPLACE(COMPANY, 'OU_', ' ') COMPANY\n" +
                "  FROM ETS_OU_CITY_MAP\n" +
                "   WHERE ENABLED='Y'\n ";
        if (StrUtil.isNotEmpty(range)) {
            sqlStr += " AND IS_TD=?";
            sqlArgs.add(range);
        }
        if (userAccount.isProvinceUser() || userAccount.isSysAdmin()) {
            addBlank = true;
        } else {
            sqlStr += " AND ORGANIZATION_ID=?";
            sqlArgs.add(userAccount.getOrganizationId());
        }
        sqlStr += " \nORDER BY COMPANY_CODE";
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
        return webFieldProducer.getOptionHtml(selectValue, addBlank);
    }

    /**
     * 公司ID下拉框
     * @param selectValue
     * @return
     * @throws QueryException
     */
    public String getOrgnizationOption(String selectValue) throws QueryException {
        return getOrgnizationOption(selectValue);
    }

    /**
     * 公司ID下拉框
     * @param selectValue
     * @param range       'Y'-TD;'N'-非TD;''-ALL
     * @return
     * @throws QueryException
     */
    public String getOrgnizationOption(int selectValue, String range) throws QueryException {
        boolean addBlank = false;
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT ORGANIZATION_ID,COMPANY_CODE || STR_REPLACE(COMPANY, 'OU_', ' ')  COMPANY\n" +
                "  FROM ETS_OU_CITY_MAP\n" +
                "   WHERE ENABLED='Y'\n ";
        if (StrUtil.isNotEmpty(range)) {
            sqlStr += " AND IS_TD=?";
            sqlArgs.add(range);
        }
//        if (userAccount.isProvinceUser() || userAccount.isSysAdmin()) {
        if (userAccount.isProvinceUser() && userAccount.isSysAdmin()) {
            addBlank = true;
        } else {
            sqlStr += " AND ORGANIZATION_ID=?";
            sqlArgs.add(userAccount.getOrganizationId());
        }
        sqlStr += " \nORDER BY COMPANY_CODE";
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
        return webFieldProducer.getOptionHtml(StrUtil.nullToString(selectValue), addBlank);
    }

    public String getAllOrgnizationOption(String selectValue) throws QueryException {
        boolean addBlank = false;
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT ORGANIZATION_ID,COMPANY\n" +
                "  FROM ETS_OU_CITY_MAP\n" +
                "   WHERE ENABLED='Y'\n" +
                "  ORDER BY COMPANY_CODE";

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
        return webFieldProducer.getOptionHtml(selectValue, addBlank);
    }


    /**
     * 读取资产同步权限公司下拉框,根据用户员工编号进行过滤
     * @param selectValue 所选公司
     * @param range       公司类型：Y-TD公司；N-有限公司
     * @return
     * @throws QueryException
     */
    public String getSynOrgnizationOption(int selectValue, String range) throws QueryException {
        boolean addBlank = false;
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr =
                "SELECT EOCM.ORGANIZATION_ID,\n" +
                        "       EOCM.COMPANY_CODE || STR_REPLACE(EOCM.COMPANY, 'OU_', ' ')\n" +
                        "  FROM ETS_MISFA_TRANSACTION_RESP EMTR, ETS_OU_CITY_MAP EOCM\n" +
                        " WHERE EOCM.ENABLED = 'Y'\n" +
                        "   AND EOCM.COMPANY_CODE = EMTR.COMPANY_CODE\n" +
                        "   AND EMTR.EMPLOYEE_NUMBER = ?\n";

        sqlArgs.add(userAccount.getEmployeeNumber());
        if (StrUtil.isNotEmpty(range)) {
            sqlStr += "   AND EOCM.IS_TD = ?\n";
            sqlArgs.add(range);
        }
        addBlank = true;
        sqlStr += " ORDER BY EOCM.COMPANY_CODE";
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
        return webFieldProducer.getOptionHtml(StrUtil.nullToString(selectValue), addBlank);
    }
    
    //默认必选
    public String getSynOrgnizationOption2(int selectValue, String range) throws QueryException {
        boolean addBlank = false;
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr =
                "SELECT EOCM.ORGANIZATION_ID,\n" +
                        "       EOCM.COMPANY_CODE || STR_REPLACE(EOCM.COMPANY, 'OU_', ' ')\n" +
                        "  FROM ETS_MISFA_TRANSACTION_RESP EMTR, ETS_OU_CITY_MAP EOCM\n" +
                        " WHERE EOCM.ENABLED = 'Y'\n" +
                        "   AND EOCM.COMPANY_CODE = EMTR.COMPANY_CODE\n" +
                        "   AND EMTR.EMPLOYEE_NUMBER = ?\n";

        sqlArgs.add(userAccount.getEmployeeNumber());
        if (StrUtil.isNotEmpty(range)) {
            sqlStr += "   AND EOCM.IS_TD = ?\n";
            sqlArgs.add(range);
        }
        sqlStr += " ORDER BY EOCM.COMPANY_CODE";
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
        return webFieldProducer.getOptionHtml(StrUtil.nullToString(selectValue), addBlank);
    }

    /**
	 * 获取所有OU组织下拉列表框(SOA同步)
	 * @param selectedValue String
	 * @param addBlank      是否加入“请选择";
	 * @return String
	 */
	public String getSynAllOrganization(String selectedValue, boolean addBlank) throws QueryException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();

			String sqlStr =" SELECT EOCM.ORGANIZATION_ID,\n" +
				           "       EOCM.COMPANY_CODE || STR_REPLACE(EOCM.COMPANY, 'OU_', ' ')\n" +
				           "  FROM ETS_MISFA_TRANSACTION_RESP EMTR, ETS_OU_CITY_MAP EOCM\n" +
				           " WHERE EOCM.ENABLED = 'Y'\n" +
				           "   AND EOCM.COMPANY_CODE = EMTR.COMPANY_CODE\n" +
				           "   AND EMTR.EMPLOYEE_NUMBER = ?\n";
			sqlArgs.add(userAccount.getEmployeeNumber());
		if (!(userAccount.isProvinceUser() && userAccount.isSysAdmin())) {
			sqlStr += " AND EOCM.ORGANIZATION_ID = ?";
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
