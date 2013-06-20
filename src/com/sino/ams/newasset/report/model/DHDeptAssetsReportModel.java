package com.sino.ams.newasset.report.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.dto.AmsMisDeptDTO;
import com.sino.ams.newasset.report.dto.DeptAssetsReportDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.SQLModelException;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2009-3-4
 * Time: 13:29:46
 * To change this template use File | Settings | File Templates.
 */

public class DHDeptAssetsReportModel extends AMSSQLProducer {
    private String deptCodes = "";

    public DHDeptAssetsReportModel(SfUserDTO userAccount, DeptAssetsReportDTO dtoParameter) {
        super(userAccount, dtoParameter);
        initDeptCodes();
    }

    /**
     * 功能：初始化当前用户有权限修改资产的所属部门信息
     */
    private void initDeptCodes() {
        deptCodes = "(";
        DTOSet depts = userAccount.getPriviDeptCodes();
        if (depts != null && !depts.isEmpty()) {
            AmsMisDeptDTO dept = null;
            for (int i = 0; i < depts.getSize(); i++) {
                dept = (AmsMisDeptDTO) depts.getDTO(i);
                deptCodes += "'" + dept.getDeptCode() + "', ";
            }
        }
        deptCodes += "'')";
    }

    /**
     * 功能：获取低值易耗资产构成分布统计报表SQL
     *
     * @return SQLModel
     * @throws com.sino.base.exception.SQLModelException
     *
     */
    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "";
        DeptAssetsReportDTO dto = (DeptAssetsReportDTO) dtoParameter;
        String deptAssetsType = dto.getDeptAssetsType();
//        if (userAccount.isProvAssetsManager()) {//按部门资产构成分布(省公司)
//          if(userAccount.isProvAssetsManager() || userAccount.isComAssetsManager()){ //省公司资产管理员, 地市公司资产管理员
        	  sqlStr = "SELECT TOTAL.COMPANY,\n" +
//        	  			"	  TOTAL.COMPANY_CODE,\n"	+
//        	  			"     TOTAL.DEPT_CODE, \n"	+
		              "       TOTAL.DEPT_NAME,\n" +
		              "       TOTAL.ITEM_QTY,\n" +
		              "       TOTAL.SUM_COST,\n" +
//		              "       (DECODE(TRUNC(100 * TOTAL.ITEM_QTY / TOTAL2.SUM_QTY),\n" +
//		              "               0,\n" +
//		              "               STR_REPLACE(ROUND(100 * TOTAL.ITEM_QTY / TOTAL2.SUM_QTY, 3),\n" +
//		              "                       '.',\n" +
//		              "                       '0.'),\n" +
//		              "               TO_CHAR(ROUND(100 * TOTAL.ITEM_QTY / TOTAL2.SUM_QTY, 3))) || '%') ASSETS_RATE\n" +
		              " CONVERT(VARCHAR,(CASE (100 * TOTAL.ITEM_QTY / TOTAL2.SUM_QTY)\n" +
		              "         WHEN 0 THEN\n" + 
		              "          STR_REPLACE	(ROUND(100 * TOTAL.ITEM_QTY / TOTAL2.SUM_QTY, 3),\n" + 
		              "                  '.',\n" + 
		              "                  '0.')\n" + 
		              "         ELSE\n" + 
		              "          ROUND(100 * TOTAL.ITEM_QTY / TOTAL2.SUM_QTY, 3)\n" + 
		              "       END)) || '%' ASSETS_RATE \n" +
		              " FROM   (SELECT EOCM.COMPANY COMPANY,\n"	+
//		              			"		EOCM.COMPANY_CODE,\n"	+
//		              			"		AMD.DEPT_CODE,\n"	+
		              			"		AMD.DEPT_NAME DEPT_NAME,\n"	+
					                "	COUNT(EII.ITEM_QTY) ITEM_QTY,\n"	+
					                "	SUM(EII.PRICE) SUM_COST\n"	+
					                "	FROM ETS_ITEM_INFO EII, AMS_MIS_DEPT AMD, ETS_OU_CITY_MAP EOCM\n"	+ 
					                "	WHERE EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE\n"	+
					                "	AND EII.FINANCE_PROP = 'DH_ASSETS'\n"	+
					                "	AND EOCM.COMPANY_CODE = AMD.COMPANY_CODE\n"	+
					                "	AND EOCM.ORGANIZATION_ID = ISNULL(?,EOCM.ORGANIZATION_ID)\n"	+
					                "	AND ( " + SyBaseSQLUtil.isNull() + "  OR AMD.DEPT_CODE = ?)\n"	+          
					                " GROUP  BY EOCM.COMPANY,\n"	+
					                "			AMD.DEPT_NAME\n"	+
					   " UNION \n" +
					         " SELECT EOCM.COMPANY COMPANY,\n"	+
//					         	   "		EOCM.COMPANY_CODE,\n"	+
//					         	   "		'' DEPT_CODE,\n"	+
					               " 		'' DEPT_NAME,\n"	+
					               "		COUNT(EII.ITEM_QTY) ITEM_QTY,\n"	+
					               "		SUM(EII.PRICE) SUM_COST\n"	+
					               "		FROM ETS_ITEM_INFO EII, AMS_MIS_DEPT AMD, ETS_OU_CITY_MAP EOCM\n"	+ 
					               "		WHERE EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE\n"	+
					               "		AND EII.FINANCE_PROP = 'DH_ASSETS'\n"	+
					               "		AND EOCM.COMPANY_CODE = AMD.COMPANY_CODE\n"	+
					               "		AND EOCM.ORGANIZATION_ID = ISNULL(?,EOCM.ORGANIZATION_ID)\n"	+
					               "		AND ( " + SyBaseSQLUtil.isNull() + "  OR AMD.DEPT_CODE = ?)\n"	+          
					               " GROUP BY EOCM.COMPANY) TOTAL,\n" +
		              
		          "		(SELECT COUNT(EII.ITEM_QTY) SUM_QTY \n" +
		          "         FROM ETS_ITEM_INFO EII, \n " +
		          "			   AMS_MIS_DEPT AMD, \n" +
		          "			   ETS_OU_CITY_MAP EOCM \n"	+
		          " 	     WHERE EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE"	+
		          "		   AND EII.FINANCE_PROP = 'DH_ASSETS'"	+
		          "		   AND EOCM.COMPANY_CODE = AMD.COMPANY_CODE"	+
		          "		   AND EOCM.ORGANIZATION_ID = ISNULL(?,EOCM.ORGANIZATION_ID)) TOTAL2 \n";
//		          "	ORDER  BY TOTAL.COMPANY,\n" +
//		          "          TOTAL.DEPT_NAME DESC";
		      
		      sqlArgs.add(dto.getOrganizationId());
		      sqlArgs.add(dto.getResponsibilityDept());
		      sqlArgs.add(dto.getResponsibilityDept());
		      
	    	  sqlArgs.add(dto.getOrganizationId());
		      
		      sqlArgs.add(dto.getResponsibilityDept());
		      sqlArgs.add(dto.getResponsibilityDept());
		      
		      sqlArgs.add(dto.getOrganizationId());
    	
	        sqlModel.setSqlStr(sqlStr);
	        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
