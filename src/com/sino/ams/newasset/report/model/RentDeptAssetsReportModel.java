package com.sino.ams.newasset.report.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.dto.AmsMisDeptDTO;
import com.sino.ams.system.rent.dto.RentDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTOSet;

/**
 * Author: 	李轶
 * Date: 2009-5-25
 * Time: 15:55:55
 * To change this template use File | Settings | File Templates.
 */

public class RentDeptAssetsReportModel extends AMSSQLProducer {
    private String deptCodes = "";

    public RentDeptAssetsReportModel(SfUserDTO userAccount, RentDTO dtoParameter) {
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
     * 功能：获取经营租赁资产部门构成分布统计报表SQL
     *
     * @return SQLModel
     * @throws com.sino.base.exception.SQLModelException
     *
     */
    public SQLModel getPageQueryModel(){
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "";
        RentDTO dto = (RentDTO) dtoParameter;
//        String deptAssetsType = dto.getDeptAssetsType();        
//        if (userAccount.isProvAssetsManager()) {//按部门资产构成分布(省公司)
//          if(userAccount.isProvAssetsManager() || userAccount.isComAssetsManager()){ //省公司资产管理员, 地市公司资产管理员
        	  /*sqlStr = "SELECT TOTAL.COMPANY,\n" +
//        	  			"	  TOTAL.COMPANY_CODE,\n"	+
//        	  			"     TOTAL.DEPT_CODE, \n"	+
		              "       TOTAL.DEPT_NAME,\n" +
		              "       TOTAL.ITEM_QTY,\n" +
		              "       TOTAL.SUM_COST,\n" +
		              "       (DECODE(TRUNC(100 * TOTAL.ITEM_QTY / TOTAL2.SUM_QTY),\n" +
		              "               0,\n" +
		              "               STR_REPLACE(ROUND(100 * TOTAL.ITEM_QTY / TOTAL2.SUM_QTY, 3),\n" +
		              "                       '.',\n" +
		              "                       '0.'),\n" +
		              "               TO_CHAR(ROUND(100 * TOTAL.ITEM_QTY / TOTAL2.SUM_QTY, 3))) || '%') ASSETS_RATE,\n" +
		              "       (DECODE(TRUNC(100 * TOTAL.SUM_COST / TOTAL2.SUM_COST),\n" +
		              "               0,\n" +
		              "               STR_REPLACE(ROUND(100 * TOTAL.SUM_COST / TOTAL2.SUM_COST, 3),\n" +
		              "                       '.',\n" +
		              "                       '0.'),\n" +
		              "               TO_CHAR(ROUND(100 * TOTAL.SUM_COST / TOTAL2.SUM_COST, 3))) || '%') ASSETS_RATE_PRICE\n" +
		              " FROM   (SELECT EOCM.COMPANY COMPANY,\n"	+
//		              			"		EOCM.COMPANY_CODE,\n"	+
//		              			"		AMD.DEPT_CODE,\n"	+
		              			"		AMD.DEPT_NAME DEPT_NAME,\n"	+
					                "	COUNT(EII.ITEM_QTY) ITEM_QTY,\n"	+
					                "	SUM(ARI.RENTAL) SUM_COST\n"	+
					                "	FROM ETS_ITEM_INFO EII, AMS_MIS_DEPT AMD, ETS_OU_CITY_MAP EOCM, AMS_RENT_INFO ARI\n"	+ 
					                "	WHERE EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE\n"	+
					                "	  AND EII.FINANCE_PROP = 'RENT_ASSETS'\n"	+
					                "	  AND EOCM.COMPANY_CODE = AMD.COMPANY_CODE\n"	+
					                "	  AND EII.BARCODE *= ARI.BARCODE\n"	+
					                "	  AND ( " + SyBaseSQLUtil.isNull() + "  OR EOCM.ORGANIZATION_ID = ?)\n"	+
					                "	  AND ( " + SyBaseSQLUtil.isNull() + "  OR AMD.DEPT_CODE = ?)\n"	+ 
					                "	  AND ( " + SyBaseSQLUtil.isNull() + "  OR ARI.RENT_DATE > LAST_DAY(TRUNC(ADD_MONTHS(?, -1), 'MM')))"	+
					                "	  AND ( " + SyBaseSQLUtil.isNull() + "  OR ARI.RENT_DATE < TRUNC(ADD_MONTHS(?, 1), 'MM'))"	+
					                " GROUP  BY EOCM.COMPANY,\n"	+
					                "			AMD.DEPT_NAME\n"	+
					   " UNION \n" +
					         " SELECT EOCM.COMPANY COMPANY,\n"	+
//					         	   "		EOCM.COMPANY_CODE,\n"	+
//					         	   "		'' DEPT_CODE,\n"	+
					               " 		'' DEPT_NAME,\n"	+
					               "		COUNT(EII.ITEM_QTY) ITEM_QTY,\n"	+
					               "		SUM(ARI.RENTAL) SUM_COST\n"	+
					               "		FROM ETS_ITEM_INFO EII, AMS_MIS_DEPT AMD, ETS_OU_CITY_MAP EOCM, AMS_RENT_INFO ARI\n"	+ 
					               "		WHERE EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE\n"	+
					               "		  AND EII.FINANCE_PROP = 'RENT_ASSETS'\n"	+
					               "		  AND EOCM.COMPANY_CODE = AMD.COMPANY_CODE\n"	+
					               "		  AND EII.BARCODE *= ARI.BARCODE\n"	+
					               "		  AND ( " + SyBaseSQLUtil.isNull() + "  OR EOCM.ORGANIZATION_ID = ?)\n"	+
					               "		  AND ( " + SyBaseSQLUtil.isNull() + "  OR AMD.DEPT_CODE = ?)\n"	+      
					               "		  AND ( " + SyBaseSQLUtil.isNull() + "  OR ARI.RENT_DATE > LAST_DAY(TRUNC(ADD_MONTHS(?, -1), 'MM')))"	+
					                "		  AND ( " + SyBaseSQLUtil.isNull() + "  OR ARI.RENT_DATE < TRUNC(ADD_MONTHS(?, 1), 'MM'))"	+
					               " GROUP BY EOCM.COMPANY) TOTAL,\n" +
		              
		          "		(SELECT COUNT(EII.ITEM_QTY) SUM_QTY , SUM(ARI.RENTAL) SUM_COST\n" +
		          "         FROM ETS_ITEM_INFO EII, \n " +
		          "			   	 AMS_MIS_DEPT AMD, \n" +
		          "			   	 ETS_OU_CITY_MAP EOCM, \n"	+
		          "				 AMS_RENT_INFO ARI\n"		+
		          " 	     WHERE EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE"	+
		          "		   AND EII.FINANCE_PROP = 'RENT_ASSETS'"	+
		          "		   AND EII.BARCODE *= ARI.BARCODE\n"	+
		          "		   AND EOCM.COMPANY_CODE = AMD.COMPANY_CODE"	+
		          "		   AND ( " + SyBaseSQLUtil.isNull() + "  OR ARI.RENT_DATE > LAST_DAY(TRUNC(ADD_MONTHS(?, -1), 'MM')))"	+
	                "	   AND ( " + SyBaseSQLUtil.isNull() + "  OR ARI.RENT_DATE < TRUNC(ADD_MONTHS(?, 1), 'MM'))"	+
		          "		   AND ( " + SyBaseSQLUtil.isNull() + "  OR EOCM.ORGANIZATION_ID = ?)) TOTAL2 \n"	+
		          
		          "	ORDER  BY TOTAL.COMPANY,\n" +
		          "          TOTAL.DEPT_NAME DESC";
        	  try { 
			      sqlArgs.add(dto.getOrganizationId());
			      sqlArgs.add(dto.getOrganizationId());
			      sqlArgs.add(dto.getResponsibilityDept());
			      sqlArgs.add(dto.getResponsibilityDept());
		      
				  sqlArgs.add(dto.getAccountingPeriod());
				  sqlArgs.add(dto.getAccountingPeriod());
			      sqlArgs.add(dto.getAccountingPeriod());
			      sqlArgs.add(dto.getAccountingPeriod());
			
		      
		      
			      sqlArgs.add(dto.getOrganizationId());
			      sqlArgs.add(dto.getOrganizationId());
			      sqlArgs.add(dto.getResponsibilityDept());
			      sqlArgs.add(dto.getResponsibilityDept());
			      
			      sqlArgs.add(dto.getAccountingPeriod());
				  sqlArgs.add(dto.getAccountingPeriod());
			      sqlArgs.add(dto.getAccountingPeriod());
			      sqlArgs.add(dto.getAccountingPeriod());
			      
			      sqlArgs.add(dto.getAccountingPeriod());
				  sqlArgs.add(dto.getAccountingPeriod());
			      sqlArgs.add(dto.getAccountingPeriod());
			      sqlArgs.add(dto.getAccountingPeriod());
			      sqlArgs.add(dto.getOrganizationId());
			      sqlArgs.add(dto.getOrganizationId());
        	  } catch (CalendarException e) {
  				
  				e.printStackTrace();
  			}*/
		      sqlStr = "SELECT EOCM.COMPANY,\n"		+
						 "	   AMD.DEPT_NAME RESPONSIBILITY_DEPT,\n"	+
						 "     EII.BARCODE,\n" +
				       "       ESI.ITEM_NAME,\n" +
				       "       ESI.ITEM_SPEC,\n" +
				       "       ESI.ITEM_UNIT,\n" +		       
				       "       AM.MANUFACTURER_NAME,\n" +
				       "	   EII.POWER,\n" +
				       "	   EII.OTHER_INFO," +
				       "	   EII.CONTENT_CODE," +
				       "	   EII.CONTENT_NAME," +
				       "	   EII.RESPONSIBILITY_USER," +
				       
				       "       (EII.START_DATE + 731) END_RENT_DATE,\n" +
				       "       AME.USER_NAME,\n" +
				       "       EII.MAINTAIN_USER,\n" +
				       "       1 ITEM_QTY,\n" +
				       "       1 NOWQTY,\n" +
				       "       0 CYSL,\n" +
				       "       AMS_PUB_PKG.GET_WORKORDER_OBJECT_NAME(EII.ADDRESS_ID) OBJECT_NAME,\n" +
				       "       ARI.RENT_DATE,\n" +
				       "       ARI.END_DATE,\n" +
				       "       ARI.RENT_PERSON,\n" +
				       "       ARI.TENANCY,\n" +
				       "       ARI.MONTH_REANTAL,\n" +
				       "       ARI.YEAR_RENTAL,\n" +			       
				       "       ARI.REMARK,\n" +
				       "       ARI.RENT_ID,\n" +
				       "       SG.GROUP_NAME MAINTAIN_DEPT,\n" +
				       "       EII.SYSTEMID SYSTEM_ID\n" +
				       "  FROM ETS_ITEM_INFO    EII,\n" +
				       "       ETS_SYSTEM_ITEM  ESI,\n" +
				       "       AMS_RENT_INFO    ARI,\n" +
				       "       AMS_MIS_DEPT     AMD,\n" +
				       "       AMS_MIS_EMPLOYEE AME,\n" +
				       "	   AMS_MANUFACTURER AM,\n"	+
				       "	   ETS_OU_CITY_MAP  EOCM,\n" +	
				       "       SF_GROUP         SG\n" +
				       " WHERE ESI.ITEM_CODE = EII.ITEM_CODE\n" +
				       "   AND EII.BARCODE *= ARI.BARCODE\n" +
				       "   AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE\n" +
				       "   AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID\n" +
				       "   AND EII.FINANCE_PROP = 'RENT_ASSETS'\n" +
				       "   AND EII.MANUFACTURER_ID *= AM.MANUFACTURER_ID" +
				       "   AND AMD.COMPANY_CODE = EOCM.COMPANY_CODE\n" +
				       "   AND EII.MAINTAIN_DEPT *= SG.GROUP_ID\n" +
				       "   AND EII.ORGANIZATION_ID = ISNULL(?, EII.ORGANIZATION_ID)\n" +
				       "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.BARCODE LIKE ?)\n" +
				       "   AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_NAME LIKE ?)\n" +
				       "   AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?)\n" +
				       "   AND ( " + SyBaseSQLUtil.isNull() + "  OR AME.USER_NAME LIKE ?)\n"	+
				       "   AND ( " + SyBaseSQLUtil.isNull() + "  OR\n" +
				       "       AMS_PUB_PKG.GET_WORKORDER_OBJECT_NAME(EII.ADDRESS_ID) LIKE ?)\n" +
				       "	AND AMD.DEPT_CODE = ISNULL(?, AMD.DEPT_CODE)" +
				       " ORDER BY EOCM.ORGANIZATION_ID, EII.ITEM_CODE";
		sqlArgs.add(dto.getOrganizationId());
		sqlArgs.add(dto.getBarcode());
		sqlArgs.add(dto.getBarcode());
		sqlArgs.add(dto.getItemName());
		sqlArgs.add(dto.getItemName());
		sqlArgs.add(dto.getItemSpec());
		sqlArgs.add(dto.getItemSpec());
		sqlArgs.add(dto.getUsername());
		sqlArgs.add(dto.getUsername());
		sqlArgs.add(dto.getAddressloc());
		sqlArgs.add(dto.getAddressloc());
		sqlArgs.add(dto.getResponsibilityDept());
		
	    sqlModel.setSqlStr(sqlStr);
	    sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
