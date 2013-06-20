package com.sino.ams.newasset.report.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;


/**
 * <p>Title: DiscardedAssetsReportModel</p>
 * <p>Description:程序自动生成SQL构造器“DiscardedAssetsReportModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 李轶
 * @version 1.0
 */


public class DiscardedAssetsReportModel extends AMSSQLProducer {

	private SfUserDTO sfUser = null;

	/**
	 * 功能：报废资产统计 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsHouseInfoDTO 本次操作的数据
	 */
	public DiscardedAssetsReportModel(SfUserDTO userAccount, AmsAssetsAddressVDTO dtoParameter) {
		super(userAccount, dtoParameter);
		sfUser = userAccount;
	}
	
	/**
	 * 功能：框架自动生成报废资产统计 页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getPageQueryModel() throws SQLModelException{ 
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
//        String sqlStr = "    SELECT TOTAL.COMPANY,\n" +
//				            "       TOTAL.DEPT_NAME,\n" +
//				            "       TOTAL.ITEM_NAME,\n" +
//				            "       TOTAL.ITEM_SPEC,\n" +
//				            "       TOTAL.ASSET_ID,\n" +
//				            "       TOTAL.BARCODE,\n" +
//                            "       TOTAL.ITEM_QTY,\n" +
//				            "       TOTAL.COST,\n" +
//				            "       TOTAL.NET_BOOK_VALUE,\n" +
//				            "       TOTAL.LIMIT_VALUE,\n" +
//				            "       TOTAL.IMPAIRMENT_RESERVE,\n" +
//                            "       TOTAL.DEPRN_RESERVE,\n" +
//				            "       TOTAL.PTD_DEPRN,\n" +
//                            "       (DECODE(TRUNC(100 * TOTAL.ITEM_SPEC / SUM_COST.TOTAL_COUNT),\n" +
//				            "               0,\n" +
//				            "               STR_REPLACE(ROUND(100 * TOTAL.ITEM_SPEC / SUM_COST.TOTAL_COUNT, 3),\n" +
//				            "                       '.',\n" +
//				            "                       '0.'),\n" +
//				            "               TO_CHAR(ROUND(100 * TOTAL.ITEM_SPEC / SUM_COST.TOTAL_COUNT, 3))) || '%') ASSETS_RATE_COUNT,\n" +
//				            "       (DECODE(TRUNC(100 * TOTAL.COST / SUM_COST.TOTAL),\n" +
//				            "               0,\n" +
//				            "               STR_REPLACE(ROUND(100 * TOTAL.COST / SUM_COST.TOTAL, 3),\n" +
//				            "                       '.',\n" +
//				            "                       '0.'),\n" +
//				            "               TO_CHAR(ROUND(100 * TOTAL.COST / SUM_COST.TOTAL, 3))) || '%') ASSETS_RATE,\n" +
//				            "       \n" +
//                            "       (DECODE(TRUNC(100 * TOTAL.ITEM_QTY / SUM_LAST_YEAR_COST.ITEM_QTY - 100),\n" +
//				            "               0,\n" +
//				            "               STR_REPLACE(ROUND(100 * TOTAL.ITEM_QTY / SUM_LAST_YEAR_COST.ITEM_QTY - 100, 3),\n" +
//				            "                       '.',\n" +
//				            "                       '0.'),\n" +
//				            "               TO_CHAR(ROUND(100 * TOTAL.ITEM_QTY / SUM_LAST_YEAR_COST.ITEM_QTY - 100, 3))) || '%') LAST_YEAR_RATE_COUNT,\n" +
//				            "       (DECODE(TRUNC(100 * TOTAL.COST / SUM_LAST_YEAR_COST.COST - 100),\n" +
//				            "               0,\n" +
//				            "               STR_REPLACE(ROUND(100 * TOTAL.COST / SUM_LAST_YEAR_COST.COST - 100, 3),\n" +
//				            "                       '.',\n" +
//				            "                       '0.'),\n" +
//				            "               TO_CHAR(ROUND(100 * TOTAL.COST / SUM_LAST_YEAR_COST.COST - 100, 3))) || '%') LAST_YEAR_RATE\n" +
//
//
//				            "  FROM (SELECT SUM(EFAHR.COST) TOTAL,\n" +
//                            "              COUNT(1) TOTAL_COUNT"   +
//                            "        FROM   ETS_FA_ASSETS_HIS_REP  EFAHR,\n" +
//                            "               ETS_ITEM_INFO  		   EII,\n" +
//                            "               ETS_ITEM_MATCH 		   EIM\n" +
//                            "        WHERE  EII.ITEM_STATUS = 'DISCARDED'" +
//                            "				AND EII.SYSTEMID = EIM.SYSTEMID\n" +
//                            "               AND EFAHR.ASSET_ID = EIM.ASSET_ID\n" +
//                            "               AND EFAHR.PERIOD_NAME = ? \n" +
//                            "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EFAHR.ORGANIZATION_ID = ?)) SUM_COST,\n" +
//
//                                    " (SELECT   EOCM.COMPANY,\n"		+
//                                    "	  AMD.DEPT_NAME,\n"		+
//                                   "      ESI.ITEM_NAME,\n" +
//                                   "      ESI.ITEM_SPEC,\n" +
//                                   "      EFAHR.ASSET_ID,\n" +
//                                   "      EII.BARCODE,\n" +
//                                   "       1 ITEM_QTY,\n" +
//                                   "      EFAHR.COST,\n" +
//                                   "      EFAHR.NET_ASSET_VALUE NET_BOOK_VALUE,\n" +
//                                   "      EFAHR.DEPRN_COST LIMIT_VALUE,\n" +
//                                   "      EFAHR.IMPAIR_RESERVE IMPAIRMENT_RESERVE,\n" +
//                                   "      EFAHR.DEPRN_RESERVE DEPRN_RESERVE,\n" +
//                                   "      EFAHR.DEPRN_AMOUNT PTD_DEPRN, \n" +
//                                    "     EII.SYSTEMID\n"    +
//                                   "  FROM ETS_ITEM_INFO    		EII,\n" +
//                                   "       ETS_SYSTEM_ITEM  		ESI,\n" +
//                                   "       ETS_FA_ASSETS_HIS_REP    EFAHR,\n" +
//                                   "       AMS_MIS_DEPT     		AMD,\n" +
//                                    "      ETS_OU_CITY_MAP 			EOCM,\n" +
//                                    "      ETS_ITEM_MATCH  			EIM,\n" +
//                                    "	   ETS_ITEM_INFO_ATTR_CHG   EIIAC\n"	+
//                                   " WHERE EII.ITEM_STATUS = ?\n" +
//                                   "   AND EFAHR.TAG_NUMBER = EIIAC.BAR_CODE\n"	+
//                            "          AND EII.SYSTEMID = EIM.SYSTEMID\n" +
//                            "          AND EFAHR.ASSET_ID = EIM.ASSET_ID\n" +
//                            "   	   AND ESI.ITEM_CODE = EII.ITEM_CODE\n" +
//                            "          AND EOCM.ORGANIZATION_ID = EFAHR.ORGANIZATION_ID\n" +
//                            "          AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE\n" +
//                            "		   AND EFAHR.PERIOD_NAME = EIIAC.PERIOD_NAME \n"	+
//                            "          AND EFAHR.PERIOD_NAME = ?\n" +
//                            "          AND EIIAC.CREATION_DATE BETWEEN\n" +
//                            "               	TRUNC(TO_DATE(?, 'YYYY-MM-DD'), 'MM') AND\n" +
//                            "               	TRUNC(LAST_DAY(TO_DATE(?, 'YYYY-MM-DD')) + 1)\n" +
//                            "          AND ( " + SyBaseSQLUtil.isNull() + "  OR EOCM.ORGANIZATION_ID = ?)\n" +
//                                   "   AND AMD.DEPT_CODE = ISNULL(?, AMD.DEPT_CODE)" +
//                                   "   AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_NAME LIKE ?)"	+
//                                   "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.BARCODE LIKE ?)) TOTAL "	+
//                           "   LEFT JOIN " +
//                                   " (SELECT  EFAHR.ASSET_ID,\n" +
//                                   "      1 ITEM_QTY,\n" +
//                                   "      EFAHR.COST\n" +
//                                   "  FROM ETS_ITEM_INFO    		EII,\n" +
//                                   "       ETS_SYSTEM_ITEM  		ESI,\n" +
//                                   "       ETS_FA_ASSETS_HIS_REP    EFAHR,\n" +
//                                   "       AMS_MIS_DEPT     		AMD,\n" +
//                                    "      ETS_OU_CITY_MAP 			EOCM,\n" +
//                                    "      ETS_ITEM_MATCH  			EIM,\n" +
//                                    "	   ETS_ITEM_INFO_ATTR_CHG   EIIAC\n"	+
//                                   " WHERE EII.ITEM_STATUS = ?\n" +
//                                   "   AND EFAHR.TAG_NUMBER = EIIAC.BAR_CODE\n"	+
//                            "          AND EII.SYSTEMID = EIM.SYSTEMID\n" +
//                            "          AND EFAHR.ASSET_ID = EIM.ASSET_ID\n" +
//                            "   	   AND ESI.ITEM_CODE = EII.ITEM_CODE\n" +
//                            "          AND EOCM.ORGANIZATION_ID = EFAHR.ORGANIZATION_ID\n" +
//                            "          AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE\n" +
//                            "		   AND EFAHR.PERIOD_NAME = EIIAC.PERIOD_NAME \n"	+
//                            "          AND EFAHR.PERIOD_NAME = ?\n" +
//                            "          AND EIIAC.CREATION_DATE BETWEEN\n" +
//                            "               	TRUNC(TO_DATE(?, 'YYYY-MM-DD'), 'MM') AND\n" +
//                            "               	TRUNC(LAST_DAY(TO_DATE(?, 'YYYY-MM-DD')) + 1)\n" +
//                            "          AND ( " + SyBaseSQLUtil.isNull() + "  OR EOCM.ORGANIZATION_ID = ?)\n" +
//                                   "   AND AMD.DEPT_CODE = ISNULL(?, AMD.DEPT_CODE)" +
//                                   "   AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_NAME LIKE ?)"	+
//                                   "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.BARCODE LIKE ?)) SUM_LAST_YEAR_COST "	+
//                             " ON TOTAL.ASSET_ID = SUM_LAST_YEAR_COST.ASSET_ID";


		String sqlStr = "SELECT   EOCM.COMPANY,\n"		+
							"	  AMD.DEPT_NAME,\n"		+
				           "      ESI.ITEM_NAME,\n" +
				           "      ESI.ITEM_SPEC,\n" +
				           "      EFAHR.ASSET_ID,\n" +
				           "      EII.BARCODE,\n" +
				           "       1 ITEM_QTY,\n" +
				           "      EFAHR.COST,\n" +
//				           "      EFAHR.NET_ASSET_VALUE NET_BOOK_VALUE,\n" +
//				           "      EFAHR.DEPRN_COST LIMIT_VALUE,\n" +
//				           "      EFAHR.IMPAIR_RESERVE IMPAIRMENT_RESERVE,\n" +
//				           "      EFAHR.DEPRN_RESERVE DEPRN_RESERVE,\n" +
//				           "      EFAHR.DEPRN_AMOUNT PTD_DEPRN, \n" +
                           "       (DECODE(TRUNC(100 * 1 / SUM_COST.TOTAL_COUNT),\n" +
                           "               0,\n" +
                            "               STR_REPLACE(ROUND(100 * 1 / SUM_COST.TOTAL_COUNT, 3),\n" +
                            "                       '.',\n" +
                            "                       '0.'),\n" +
                            "               TO_CHAR(ROUND(100 * 1 / SUM_COST.TOTAL_COUNT, 3))) || '%') ASSETS_RATE_COUNT,\n" +
                            "               \n" +
                            "       (DECODE(SUM_COST.TOTAL, NULL, '0', DECODE(TRUNC(100 * EFAHR.COST / SUM_COST.TOTAL),\n" +
                           "               0,\n" +
                            "               STR_REPLACE(ROUND(100 * EFAHR.COST / SUM_COST.TOTAL, 3),\n" +
                            "                       '.',\n" +
                            "                       '0.'),\n" +
                            "               TO_CHAR(ROUND(100 * EFAHR.COST / SUM_COST.TOTAL, 3)))) || '%') ASSETS_RATE,\n" +
                            "       '0%' SUM_LAST_YEAR_RATE_COUNT,\n"  +
                            "       '0%' SUM_LAST_YEAR_RATE \n" +
				           "  FROM ETS_ITEM_INFO    		EII,\n" +
				           "       ETS_SYSTEM_ITEM  		ESI,\n" +
				           "       ETS_FA_ASSETS_HIS_REP    EFAHR,\n" +
				           "       AMS_MIS_DEPT     		AMD,\n" +
		                    "      ETS_OU_CITY_MAP 			EOCM,\n" +
		                    "      ETS_ITEM_MATCH  			EIM,\n" +
		                    "	   ETS_ITEM_INFO_ATTR_CHG   EIIAC,\n"	+
                            "      (SELECT SUM(EFAHR.COST) TOTAL,\n" +
                            "              COUNT(1) TOTAL_COUNT"   +
                            "        FROM   ETS_FA_ASSETS_HIS_REP  EFAHR,\n" +
                            "               ETS_ITEM_INFO  		   EII,\n" +
                            "               ETS_ITEM_MATCH 		   EIM\n" +
                            "        WHERE  EII.ITEM_STATUS = ?" +
                            "				AND EII.SYSTEMID = EIM.SYSTEMID\n" +
                            "               AND EFAHR.ASSET_ID = EIM.ASSET_ID\n" +
                            "               AND EFAHR.PERIOD_NAME = ? \n" +
                            "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EFAHR.ORGANIZATION_ID = ?)) SUM_COST\n" +
				           " WHERE EII.ITEM_STATUS = ?\n" +
				           "   AND EFAHR.TAG_NUMBER = EIIAC.BAR_CODE\n"	+
                    "          AND EII.SYSTEMID = EIM.SYSTEMID\n" +
                    "          AND EFAHR.ASSET_ID = EIM.ASSET_ID\n" +
                    "   	   AND ESI.ITEM_CODE = EII.ITEM_CODE\n" +
                    "          AND EOCM.ORGANIZATION_ID = EFAHR.ORGANIZATION_ID\n" +
                    "          AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE\n" +
                    "		   AND EFAHR.PERIOD_NAME = EIIAC.PERIOD_NAME \n"	+
			        "          AND EFAHR.PERIOD_NAME = ?\n" +
			        "          AND EIIAC.CREATION_DATE BETWEEN\n" +
			        "               	TRUNC(TO_DATE(?, 'YYYY-MM-DD'), 'MM') AND\n" +
			        "               	TRUNC(LAST_DAY(TO_DATE(?, 'YYYY-MM-DD')) + 1)\n" +
                    "          AND ( " + SyBaseSQLUtil.isNull() + "  OR EOCM.ORGANIZATION_ID = ?)\n" +
				           "   AND AMD.DEPT_CODE = ISNULL(?, AMD.DEPT_CODE)" +
				           "   AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_NAME LIKE ?)"	+
				           "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.BARCODE LIKE ?) "	+
				           " ORDER BY EII.SYSTEMID";
         sqlArgs.add(dto.getItemStatus());
//         sqlArgs.add(dto.getPeriodNameByHisRep());
         sqlArgs.add(dto.getOrganizationId());
         sqlArgs.add(dto.getOrganizationId());


		 sqlArgs.add(dto.getItemStatus());
//		 sqlArgs.add(dto.getPeriodNameByHisRep());
//		 sqlArgs.add(dto.getYear() + "-" + dto.getMonth() + "-01");
//         sqlArgs.add(dto.getYear() + "-" + dto.getMonth() + "-01");      
         sqlArgs.add(dto.getOrganizationId());
         sqlArgs.add(dto.getOrganizationId());
	      
	     sqlArgs.add(dto.getResponsibilityDept());
	     sqlArgs.add(dto.getItemName());
	     sqlArgs.add(dto.getItemName());
	     sqlArgs.add(dto.getBarcode());
	     sqlArgs.add(dto.getBarcode());

         sqlModel.setSqlStr(sqlStr);
		 sqlModel.setArgs(sqlArgs);
		
		return sqlModel;
	}

}
