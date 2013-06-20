package com.sino.ams.newasset.report.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;
import com.sino.base.exception.CalendarException;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.newasset.dto.AmsAssetsCheckBatchDTO;
import com.sino.ams.newasset.dto.AmsAssetsCheckHeaderDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

public class CostCheckResultModel extends AMSSQLProducer {

    public CostCheckResultModel(SfUserDTO userAccount, AmsAssetsCheckHeaderDTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    public SQLModel getPageQueryModel() throws SQLModelException {
        return getStaticModel();
    }

    private SQLModel getStaticModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsAssetsCheckBatchDTO dto = (AmsAssetsCheckBatchDTO) dtoParameter;
        try {
        	String sqlStr = "";
        	if (dto.getCheckTpye().equals("cost")) {
        		sqlStr ="SELECT DISTINCT ACCT.COUNTY_CODE_MIS COST_CENTER_CODE,\n" +
        				//"SELECT ACCT.COUNTY_CODE_MIS COST_CENTER_CODE,\n" +
                        "       ACCT.COUNTY_NAME COST_CENTER_NAME,\n" +
                        "       ISNULL(TOTAL_V.TOTAL_COUNT, 0) TOTAL_COUNT,\n" +
                        "       ISNULL(NEED_V.TOTAL_COUNT, 0) NEED_COUNT,\n" +
                        "       ISNULL(TOTAL_V.TOTAL_COUNT, 0)-ISNULL(NEED_V.TOTAL_COUNT, 0) NOT_NEED_COUNT,\n" +
                        "       ISNULL(ISNULL(UNMATCHED_V.UNMATCHED_COUNT, 0)+ ISNULL(IDENTICAL_V.IDENTICAL_COUNT, 0), 0) SCANED_COUNT,\n" +
                        "       ISNULL(IDENTICAL_V.IDENTICAL_COUNT, 0) IDENTICAL_COUNT,\n" +
                        "       ISNULL(UNMATCHED_V.UNMATCHED_COUNT, 0) UNMATCHED_COUNT,\n" +
                        "       --CONVERT(VARCHAR, CONVERT(DECIMAL(18,2), ROUND(100.0 * ISNULL(IDENTICAL_V.IDENTICAL_COUNT, 0) / ISNULL(NEED_V.TOTAL_COUNT, 1), 2))) || '%' IDENTICAL_RATE_1 \n" +
                        "       (CASE CHARINDEX( '.', CONVERT(VARCHAR, ROUND(100.0 * ISNULL(IDENTICAL_V.IDENTICAL_COUNT, 0) / ISNULL(NEED_V.TOTAL_COUNT, 1), 2))) \n" +
                        "        WHEN 1 THEN\n" +
                        "          '0' || CONVERT(VARCHAR, CONVERT(DECIMAL(18,2), ROUND(100.0 * ISNULL(IDENTICAL_V.IDENTICAL_COUNT, 0) / ISNULL(NEED_V.TOTAL_COUNT, 1), 2))) || '%' \n" +
                        "        ELSE\n" +
                        "          CONVERT(VARCHAR, CONVERT(DECIMAL(18,2), ROUND(100.0 * ISNULL(IDENTICAL_V.IDENTICAL_COUNT, 0) / ISNULL(NEED_V.TOTAL_COUNT, 1), 2))) || '%' \n" +
                        "        END) IDENTICAL_RATE_1\n" +
                        "  FROM ETS_COUNTY ACCT,\n" +
                        "       (SELECT SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) COST_CENTER_CODE,\n" +
                        "               COUNT(EFA.ASSET_ID) TOTAL_COUNT\n";
                        if (userAccount.getIsTd().equals("N")) {
                            sqlStr += " FROM ETS_FA_ASSETS EFA\n";
                        } else {
                            sqlStr += " FROM ETS_FA_ASSETS_TD EFA\n";
                        }

                        sqlStr += 
                        "         WHERE EFA.BOOK_TYPE_CODE LIKE '%FA%'\n" +
                        "           AND (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)\n" +
                        "           AND EXISTS\n" +
                        "       		(SELECT NULL\n" +
                        "          		  FROM ETS_COUNTY ACCT\n" +
                        "         		 WHERE SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) = ACCT.COUNTY_CODE_MIS)\n" +
                        "           AND EFA.ASSETS_CREATE_DATE >= ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                        "           AND EFA.ASSETS_CREATE_DATE <= ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                        "           AND EFA.TAG_NUMBER >= ISNULL(LTRIM(?), EFA.TAG_NUMBER)\n" +
                        "           AND EFA.TAG_NUMBER <= ISNULL(LTRIM(?), EFA.TAG_NUMBER)\n" +
                        "           AND (LTRIM(?) IS NULL OR SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) = LTRIM(?))\n" +
                        "           AND EFA.ORGANIZATION_ID = ?\n" +
                        
                        "			AND NOT EXISTS (SELECT NULL FROM AMS_IGNORE_DATA H WHERE EFA.SEGMENT2 = H.SEGMENT AND EFA.SEGMENT3 = H.NAME) \n" +
                        
                        "         GROUP BY SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6)) TOTAL_V,\n" +
                        "       (SELECT SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) COST_CENTER_CODE,\n" +
                        "               COUNT(EFA.ASSET_ID) TOTAL_COUNT\n";
                        if (userAccount.getIsTd().equals("N")) {
                        	sqlStr += " FROM ETS_FA_ASSETS EFA\n";
                        } else {
                        	sqlStr += " FROM ETS_FA_ASSETS_TD EFA\n";
                        }

                        sqlStr += 
                        "         WHERE EFA.BOOK_TYPE_CODE LIKE '%FA%'\n" +
                        "           AND (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)\n" +
                        "           AND EFA.ASSETS_CREATE_DATE >= ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                        "           AND EFA.ASSETS_CREATE_DATE <= ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                        "           AND EFA.TAG_NUMBER >= ISNULL(LTRIM(?), EFA.TAG_NUMBER)\n" +
                        "           AND EFA.TAG_NUMBER <= ISNULL(LTRIM(?), EFA.TAG_NUMBER)\n" +
                        "           AND EXISTS\n" +
                        "               (SELECT NULL\n" +
                        "                  FROM AMS_ITEM_CATEGORY_MAP AICM\n" +
                        "                 WHERE EFA.SEGMENT1 || '.' || EFA.SEGMENT2 || '.' || EFA.SEGMENT3 = AICM.FA_CATEGORY_CODE\n" +
                        "                   AND AICM.NEED_SCAN = 'Y')\n" +
                        "           AND (LTRIM(?) IS NULL OR SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) = LTRIM(?))\n" +
                        "           AND EFA.ORGANIZATION_ID = ?\n" +
                        
                        "			AND NOT EXISTS (SELECT NULL FROM AMS_IGNORE_DATA H WHERE EFA.SEGMENT2 = H.SEGMENT AND EFA.SEGMENT3 = H.NAME) \n" +
                        
                        "         GROUP BY SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6)) NEED_V,\n" +
                        " 		(SELECT SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) COST_CENTER_CODE,\n" +
                        "               COUNT(EFA.ASSET_ID) IDENTICAL_COUNT\n";
                        if (userAccount.getIsTd().equals("N")) {
                            sqlStr += " FROM ETS_FA_ASSETS EFA,\n";
                        } else {
                            sqlStr += " FROM ETS_FA_ASSETS_TD EFA,\n";
                        }

                        sqlStr += 
                        "       		ETS_ITEM_INFO      EII,\n" +
                        "       		AMS_OBJECT_ADDRESS AOA,\n" +
                        "       		ETS_OBJECT         EO\n" +
                        "         WHERE EII.ORGANIZATION_ID = EFA.ORGANIZATION_ID\n" +
                        "           AND EII.BARCODE = EFA.TAG_NUMBER\n" +
                        "       	AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                        "       	AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                        "       	AND (EO.OBJECT_CATEGORY < = '70' OR EO.OBJECT_CATEGORY = '80')\n"+
                        "       	AND EXISTS\n" +
                        "               (SELECT NULL\n" +
                        "                  FROM AMS_ASSETS_CHECK_HEADER AACH,\n" +
                        "                       AMS_ASSETS_CHECK_LINE   AACL\n" +
                        "                 WHERE AACH.HEADER_ID = AACL.HEADER_ID\n" +
                        "                   AND AACH.ORDER_STATUS = 'ARCHIEVED'\n" +
                        "                   AND AACH.SCANOVER_DATE >= ISNULL(?, AACH.SCANOVER_DATE)\n" +
                        "                   AND AACH.SCANOVER_DATE <= ISNULL(?, AACH.SCANOVER_DATE)\n" +
                        "                   AND ((AACL.ARCHIVE_STATUS = '0' AND AACL.SCAN_STATUS = 'Y') OR\n" +
                        "                        (AACL.ARCHIVE_STATUS = '1' AND AACL.SYSTEM_STATUS = 'Y'))\n" +
                        "                   AND AACH.ORGANIZATION_ID =  EII.ORGANIZATION_ID\n" +
                        "                   AND EII.BARCODE = AACL.BARCODE)\n" +
                        "           AND EFA.BOOK_TYPE_CODE LIKE '%FA%'\n" +
                        "           AND (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)\n" +
                        "           AND EXISTS\n" +
                        "               (SELECT NULL\n" +
                        "                  FROM AMS_ITEM_CATEGORY_MAP AICM\n" +
                        "                 WHERE EFA.SEGMENT1 || '.' || EFA.SEGMENT2 || '.' || EFA.SEGMENT3 = AICM.FA_CATEGORY_CODE\n" +
                        "                   AND AICM.NEED_SCAN = 'Y')\n" +
                        "           AND EFA.ASSETS_CREATE_DATE >= ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                        "           AND EFA.ASSETS_CREATE_DATE <= ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                        "           AND SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) =\n" +
                        "               ISNULL(LTRIM(?), SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6))\n" +
                        "           AND EFA.TAG_NUMBER >= ISNULL(LTRIM(?), EFA.TAG_NUMBER)\n" +
                        "           AND EFA.TAG_NUMBER <= ISNULL(LTRIM(?), EFA.TAG_NUMBER)\n" +
                        "           AND EFA.ORGANIZATION_ID = ?\n" +
                        
                        "			AND NOT EXISTS (SELECT NULL FROM AMS_IGNORE_DATA H WHERE EFA.SEGMENT2 = H.SEGMENT AND EFA.SEGMENT3 = H.NAME) \n" +
                        
                        "         GROUP BY SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6)) IDENTICAL_V,\n" +
                        " 		(SELECT ACCT.COUNTY_CODE_MIS COST_CENTER_CODE,COUNT(1) UNMATCHED_COUNT \n" +
	                    "  		   FROM ETS_ITEM_INFO       EII,\n" +
	                    "       		ETS_SYSTEM_ITEM     ESI,\n" +
	                    "       		AMS_COST_DEPT_MATCH ACDM,\n" +
	                    "       		ETS_COUNTY ACCT,\n" +
	                    "       		ETS_OBJECT          EO,\n" +
	                    "       		AMS_OBJECT_ADDRESS  AOA\n" +
	                    " 		  WHERE EII.ITEM_CODE *= ESI.ITEM_CODE \n" +
	                    "   		AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
	                    "   		AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
	                    "   		AND ISNULL(EII.RESPONSIBILITY_DEPT,'000')= ACDM.DEPT_CODE\n" +
	                    "   		AND ACDM.COST_CENTER_CODE = ACCT.COUNTY_CODE_MIS\n" +
	                    "   		AND (EO.OBJECT_CATEGORY < = '70' OR EO.OBJECT_CATEGORY = '80') \n";
	                    if (userAccount.getIsTd().equals("N")) {
	                        sqlStr += " AND (EII.FINANCE_PROP = 'UNKNOW' OR EII.FINANCE_PROP = 'ASSETS')\n";
	                    } else {
	                        sqlStr += " AND (EII.FINANCE_PROP = 'UNKNOW' OR EII.FINANCE_PROP = 'TD_ASSETS')\n";
	                    }
	                    sqlStr += " AND EXISTS\n" +
	                    "               (SELECT NULL\n" +
	                    "                  FROM AMS_ASSETS_CHECK_HEADER AACH,\n" +
	                    "                       AMS_ASSETS_CHECK_LINE   AACL\n" +
	                    "                 WHERE AACH.HEADER_ID = AACL.HEADER_ID\n" +
	                    "                   AND AACH.ORDER_STATUS = 'ARCHIEVED'\n" +
	                    "                   AND AACH.SCANOVER_DATE >= ISNULL(?, AACH.SCANOVER_DATE)\n" +
	                    "                   AND AACH.SCANOVER_DATE <= ISNULL(?, AACH.SCANOVER_DATE)\n" +
	                    "                   AND ((AACL.ARCHIVE_STATUS = '0' AND AACL.SCAN_STATUS = 'Y') OR\n" +
	                    "                        (AACL.ARCHIVE_STATUS = '1' AND AACL.SYSTEM_STATUS = 'Y'))\n" +
	                    "                   AND AACH.ORGANIZATION_ID = EII.ORGANIZATION_ID  \n" +
	                    "         		    AND EII.BARCODE = AACL.BARCODE)\n" +
	                    "   		AND NOT EXISTS\n" +
	                    "            (SELECT NULL\n";
	                    if (userAccount.getIsTd().equals("N")) {
	                        sqlStr += " FROM ETS_FA_ASSETS EFA\n";
	                    } else {
	                        sqlStr += " FROM ETS_FA_ASSETS_TD EFA\n";
	                    }

	                    sqlStr += "    WHERE EFA.ORGANIZATION_ID = EII.ORGANIZATION_ID\n" +
	                    "                AND EFA.TAG_NUMBER = EII.BARCODE\n" +

	                    "                AND \n" +
	                    "                  (EXISTS\n" +
	                    "                     (SELECT NULL\n" +
	                    "                        FROM AMS_ITEM_CATEGORY_MAP AICM\n" +
	                    "                       WHERE AICM.FA_CATEGORY_CODE = EFA.SEGMENT1 || '.' || EFA.SEGMENT2 || '.' || EFA.SEGMENT3\n" +
	                    "                         AND AICM.NEW_ASSET_NEED_SCAN = 'N') OR\n" +
	                    "                     ((SELECT COUNT(1)\n" +
	                    "                         FROM AMS_NOT_SCAN_ASSETS ANSA\n" +
	                    "                        WHERE dbo.CHARINDEX_4(ANSA.ASSETS_DESCRIPTION, EFA.ASSETS_DESCRIPTION, 1, 1) > 0) > 0) OR\n" +
	                    "                     EFA.IS_RETIREMENTS = 1 OR\n" +
	                    "                     EFA.BOOK_TYPE_CODE NOT LIKE '%FA%')) \n" +
	                    
	                    //"				 AND NOT EXISTS (SELECT NULL FROM AMS_IGNORE_DATA H WHERE EFA.SEGMENT2 = H.SEGMENT AND EFA.SEGMENT3 = H.NAME)) \n" +
	                    
	                    "   		AND NOT EXISTS\n" +
	                    " 			 (SELECT 1\n";
	                    if (userAccount.getIsTd().equals("N")) {
	                        sqlStr += " FROM ETS_FA_ASSETS EFA\n";
	                    } else {
	                        sqlStr += " FROM ETS_FA_ASSETS_TD EFA\n";
	                    }

	                    sqlStr += 
	                    "         WHERE EFA.TAG_NUMBER = EII.BARCODE\n" +
	                    "           AND EFA.ORGANIZATION_ID = EII.ORGANIZATION_ID\n" +
	                    "           AND EXISTS\n" +
	                    "               (SELECT NULL\n" +
	                    "                  FROM AMS_ITEM_CATEGORY_MAP AICM\n" +
	                    "                 WHERE AICM.FA_CATEGORY_CODE = EFA.SEGMENT1 || '.' || EFA.SEGMENT2 || '.' || EFA.SEGMENT3\n" +
	                    "                   AND AICM.NEED_SCAN = 'Y')\n" +
	                    "           AND EFA.BOOK_TYPE_CODE LIKE '%FA%'\n" +
	                    "           AND (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)) \n" +
	                    
	                    //"			AND NOT EXISTS (SELECT NULL FROM AMS_IGNORE_DATA H WHERE EFA.SEGMENT2 = H.SEGMENT AND EFA.SEGMENT3 = H.NAME)) \n" +
	                    
	                    "   AND ACCT.COUNTY_CODE_MIS = ISNULL(LTRIM(?), ACCT.COUNTY_CODE_MIS)\n" +
	                    "   AND EII.BARCODE >= ISNULL(LTRIM(?), EII.BARCODE)\n" +
	                    "   AND EII.BARCODE <= ISNULL(LTRIM(?), EII.BARCODE)\n" +
	                    "   AND ACCT.ORGANIZATION_ID = EII.ORGANIZATION_ID\n" +
	                    "   AND EII.ORGANIZATION_ID = ?\n";
	                    if (userAccount.getIsTd().equals("N")) {
	                        sqlStr += " AND ACCT.COMPANY_CODE LIKE '"+dto.getProviceCode()+"%'\n";
	                    } else {
	                        sqlStr += " AND ACCT.COMPANY_CODE LIKE '"+dto.getTdProviceCode()+"%'\n";
	                    }

	                    sqlStr +=    "GROUP BY ACCT.COUNTY_CODE_MIS) UNMATCHED_V\n"+
                        " WHERE ACCT.COUNTY_CODE_MIS *= TOTAL_V.COST_CENTER_CODE\n" +
                        "   AND ACCT.COUNTY_CODE_MIS *= NEED_V.COST_CENTER_CODE\n" +
                        "   AND ACCT.COUNTY_CODE_MIS *= IDENTICAL_V.COST_CENTER_CODE\n" +
                        "   AND ACCT.COUNTY_CODE_MIS *= UNMATCHED_V.COST_CENTER_CODE\n" +
                        "   AND ACCT.COUNTY_CODE_MIS = ISNULL(?, ACCT.COUNTY_CODE_MIS)\n" +
                        "   AND ACCT.ORGANIZATION_ID = ?\n";
                        if (userAccount.getIsTd().equals("N")) {
                        	sqlStr += " AND ACCT.COMPANY_CODE LIKE '"+dto.getProviceCode()+"%'\n";
                        } else {
                            sqlStr += " AND ACCT.COMPANY_CODE LIKE '"+dto.getTdProviceCode()+"%'\n";
                        }

                        sqlStr += "   	AND (ISNULL(TOTAL_V.TOTAL_COUNT, 0) > 0 OR\n" +
                        "       			ISNULL(ISNULL(UNMATCHED_V.UNMATCHED_COUNT, 0)+ ISNULL(IDENTICAL_V.IDENTICAL_COUNT, 0), 0) > 0)";

		if (dto.getCreationDate().toString().equals("")) {
			sqlArgs.add(null);
		} else {
			sqlArgs.add(dto.getCreationDate());
		}
		if (dto.getSQLLastUpdateDate().toString().equals("")) {
			sqlArgs.add(null);
		} else {
			sqlArgs.add(dto.getSQLLastUpdateDate());
		}                
        
        //sqlArgs.add(dto.getCreationDate());
        //sqlArgs.add(dto.getSQLLastUpdateDate());
        sqlArgs.add(dto.getFromBarcode());
        sqlArgs.add(dto.getToBarcode());
        sqlArgs.add(dto.getCostCenterCode());
        sqlArgs.add(dto.getCostCenterCode());
        sqlArgs.add(userAccount.getOrganizationId());

		if (dto.getCreationDate().toString().equals("")) {
			sqlArgs.add(null);
		} else {
			sqlArgs.add(dto.getCreationDate());
		}
		if (dto.getSQLLastUpdateDate().toString().equals("")) {
			sqlArgs.add(null);
		} else {
			sqlArgs.add(dto.getSQLLastUpdateDate());
		}  
        //sqlArgs.add(dto.getCreationDate());
        //sqlArgs.add(dto.getSQLLastUpdateDate());
        sqlArgs.add(dto.getFromBarcode());
        sqlArgs.add(dto.getToBarcode());
        sqlArgs.add(dto.getCostCenterCode());
        sqlArgs.add(dto.getCostCenterCode());
        sqlArgs.add(userAccount.getOrganizationId());

		if (dto.getStartDate().toString().equals("")) {
			sqlArgs.add(null);
		} else {
			sqlArgs.add(dto.getStartDate());
		}
		if (dto.getSQLEndDate().toString().equals("")) {
			sqlArgs.add(null);
		} else {
			sqlArgs.add(dto.getSQLEndDate());
		}  
		if (dto.getCreationDate().toString().equals("")) {
			sqlArgs.add(null);
		} else {
			sqlArgs.add(dto.getCreationDate());
		}
		if (dto.getSQLLastUpdateDate().toString().equals("")) {
			sqlArgs.add(null);
		} else {
			sqlArgs.add(dto.getSQLLastUpdateDate());
		} 
        //sqlArgs.add(dto.getStartDate());
        //sqlArgs.add(dto.getSQLEndDate());
        //sqlArgs.add(dto.getCreationDate());
        //sqlArgs.add(dto.getSQLLastUpdateDate());
		if (dto.getCostCenterCode().equals("")) {
			sqlArgs.add(null);
		} else {
			sqlArgs.add(dto.getCostCenterCode());
		}
        //sqlArgs.add(dto.getCostCenterCode());
        sqlArgs.add(dto.getFromBarcode());
        sqlArgs.add(dto.getToBarcode());
        sqlArgs.add(userAccount.getOrganizationId());

		if (dto.getStartDate().toString().equals("")) {
			sqlArgs.add(null);
		} else {
			sqlArgs.add(dto.getStartDate());
		}
		if (dto.getSQLEndDate().toString().equals("")) {
			sqlArgs.add(null);
		} else {
			sqlArgs.add(dto.getSQLEndDate());
		}  
        //sqlArgs.add(dto.getStartDate());
        //sqlArgs.add(dto.getSQLEndDate());
//        sqlArgs.add(dto.getCreationDate());
//        sqlArgs.add(dto.getSQLLastUpdateDate());
		
		if (dto.getCostCenterCode().equals("")) {
			sqlArgs.add(null);
		} else {
			sqlArgs.add(dto.getCostCenterCode());
		}
        //sqlArgs.add(dto.getCostCenterCode());
        sqlArgs.add(dto.getFromBarcode());
        sqlArgs.add(dto.getToBarcode());
        sqlArgs.add(userAccount.getOrganizationId());

		if (dto.getCostCenterCode().equals("")) {
			sqlArgs.add(null);
		} else {
			sqlArgs.add(dto.getCostCenterCode());
		}
        //sqlArgs.add(dto.getCostCenterCode());
        sqlArgs.add(userAccount.getOrganizationId());
        
        
            } else if (dto.getCheckTpye().equals("dept")) {
                sqlStr ="SELECT DISTINCT ACCT.COUNTY_CODE_MIS COST_CENTER_CODE,\n" +
                		//"SELECT ACCT.COUNTY_CODE_MIS COST_CENTER_CODE,\n" +
                        "       ACCT.COUNTY_NAME COST_CENTER_NAME,\n" +
                        "       ISNULL(TOTAL_V.TOTAL_COUNT, 0) TOTAL_COUNT,\n" +
                        "       ISNULL(NEED_V.TOTAL_COUNT, 0) NEED_COUNT,\n" +
                        "       ISNULL(TOTAL_V.TOTAL_COUNT, 0) - ISNULL(NEED_V.TOTAL_COUNT, 0) NOT_NEED_COUNT,\n" +
                        "       ISNULL(ISNULL(UNMATCHED_V.UNMATCHED_COUNT, 0) +\n" +
                        "              ISNULL(IDENTICAL_V.IDENTICAL_COUNT, 0),\n" +
                        "              0) SCANED_COUNT,\n" +
                        "       ISNULL(IDENTICAL_V.IDENTICAL_COUNT, 0) IDENTICAL_COUNT,\n" +
                        "       ISNULL(UNMATCHED_V.UNMATCHED_COUNT, 0) UNMATCHED_COUNT,\n" +
                        
                        "       --CONVERT(VARCHAR, CONVERT(DECIMAL(18,2), ROUND(100.0 * ISNULL(IDENTICAL_V.IDENTICAL_COUNT, 0) / ISNULL(NEED_V.TOTAL_COUNT, 1), 2))) || '%' IDENTICAL_RATE_1 \n" +
                        "       (CASE CHARINDEX( '.', CONVERT(VARCHAR, ROUND(100.0 * ISNULL(IDENTICAL_V.IDENTICAL_COUNT, 0) / ISNULL(NEED_V.TOTAL_COUNT, 1), 2))) \n" +
                        "        WHEN 1 THEN \n" +
                        "          '0' || CONVERT(VARCHAR, CONVERT(DECIMAL(18,2), ROUND(100.0 * ISNULL(IDENTICAL_V.IDENTICAL_COUNT, 0) / ISNULL(NEED_V.TOTAL_COUNT, 1), 2))) || '%' \n" +
                        "        ELSE\n" +
                        "          CONVERT(VARCHAR, CONVERT(DECIMAL(18,2), ROUND(100.0 * ISNULL(IDENTICAL_V.IDENTICAL_COUNT, 0) / ISNULL(NEED_V.TOTAL_COUNT, 1), 2))) || '%' \n" +
                        "        END) IDENTICAL_RATE_1\n" +
                        
                        "  FROM ETS_COUNTY ACCT,\n" +
                        "       AMS_COST_DEPT_MATCH ACDM,\n" +
                        "       AMS_MIS_DEPT AMD,\n" +
                        "       (SELECT SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) COST_CENTER_CODE,\n" +
                        "               COUNT(EFA.ASSET_ID) TOTAL_COUNT \n";
                        if (userAccount.getIsTd().equals("N")) {
                            sqlStr += " FROM ETS_FA_ASSETS EFA\n";
                        } else {
                            sqlStr += " FROM ETS_FA_ASSETS_TD EFA\n";
                        }
                        sqlStr += 
                        "         WHERE EFA.BOOK_TYPE_CODE LIKE '%FA%'\n" +
                        "           AND (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)\n" +
                        "           AND EXISTS\n" +
                        "         		(SELECT NULL\n" +
                        "                  FROM ETS_COUNTY          ACCT,\n" +
                        "                       AMS_COST_DEPT_MATCH ACDM,\n" +
                        "                       AMS_MIS_DEPT        AMD\n" +
                        "                 WHERE SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) =\n" +
                        "                       ACCT.COUNTY_CODE_MIS\n" +
                        "                   AND ACCT.COUNTY_CODE_MIS *= ACDM.COST_CENTER_CODE \n" +
                        "                   AND ACDM.DEPT_CODE *= AMD.DEPT_CODE \n" +
                        "                   AND (LTRIM(?) IS NULL OR AMD.DEPT_CODE = LTRIM(?)))\n" +
                        "           AND EFA.ASSETS_CREATE_DATE >= ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                        "           AND EFA.ASSETS_CREATE_DATE <= ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                        "           AND EFA.ORGANIZATION_ID = ? \n" +
                        
                        //"			AND NOT EXISTS (SELECT NULL FROM AMS_IGNORE_DATA H WHERE EFA.SEGMENT2 = H.SEGMENT AND EFA.SEGMENT3 = H.NAME) \n" +
                        
                        "         GROUP BY SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6)) TOTAL_V,\n" +
                        "       (SELECT SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) COST_CENTER_CODE,\n" +
                        "               COUNT(EFA.ASSET_ID) TOTAL_COUNT \n";
                        if (userAccount.getIsTd().equals("N")) {
                            sqlStr += " FROM ETS_FA_ASSETS EFA\n";
                        } else {
                            sqlStr += " FROM ETS_FA_ASSETS_TD EFA\n";
                        }
                        sqlStr += 
                        "         WHERE EFA.BOOK_TYPE_CODE LIKE '%FA%'\n" +
                        "           AND (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)\n" +
                        "           AND EFA.ASSETS_CREATE_DATE >= ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                        "           AND EFA.ASSETS_CREATE_DATE <= ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                        "           AND EXISTS \n" +
                        "				(SELECT NULL\n" +
                        "                  FROM AMS_ITEM_CATEGORY_MAP AICM\n" +
                        "                 WHERE EFA.SEGMENT1 || '.' || EFA.SEGMENT2 || '.' ||\n" +
                        "                       EFA.SEGMENT3 = AICM.FA_CATEGORY_CODE\n" +
                        "                   AND AICM.NEED_SCAN = 'Y')\n" +
                        "           AND EXISTS\n" +
                        "         		(SELECT NULL\n" +
                        "                  FROM ETS_COUNTY          ACCT,\n" +
                        "                       AMS_COST_DEPT_MATCH ACDM,\n" +
                        "                       AMS_MIS_DEPT        AMD\n" +
                        "                 WHERE SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) =\n" +
                        "                       ACCT.COUNTY_CODE_MIS\n" +
                        "                   AND ACCT.COUNTY_CODE_MIS *= ACDM.COST_CENTER_CODE \n" +
                        "                   AND ACDM.DEPT_CODE *= AMD.DEPT_CODE \n" +
                        "                   AND (LTRIM(?) IS NULL OR AMD.DEPT_CODE = LTRIM(?)))\n" +
                        "           AND EFA.ORGANIZATION_ID = ? \n" +
                        
                        //"			AND NOT EXISTS (SELECT NULL FROM AMS_IGNORE_DATA H WHERE EFA.SEGMENT2 = H.SEGMENT AND EFA.SEGMENT3 = H.NAME) \n" +
                        
                        "         GROUP BY SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6)) NEED_V,\n" +
                        "       (SELECT SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) COST_CENTER_CODE,\n" +
                        "               COUNT(EFA.ASSET_ID) IDENTICAL_COUNT\n";
                        if (userAccount.getIsTd().equals("N")) {
                            sqlStr += " FROM ETS_FA_ASSETS EFA,\n";
                        } else {
                            sqlStr += " FROM ETS_FA_ASSETS_TD EFA,\n";
                        }
                        sqlStr += 
                        "               ETS_ITEM_INFO      EII,\n" +
                        "               AMS_OBJECT_ADDRESS AOA,\n" +
                        "               ETS_OBJECT         EO\n" +
                        "         WHERE EII.ORGANIZATION_ID = EFA.ORGANIZATION_ID\n" +
                        "           AND EII.BARCODE = EFA.TAG_NUMBER\n" +
                        "           AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                        "           AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                        "           AND (EO.OBJECT_CATEGORY < = '70' OR EO.OBJECT_CATEGORY = '80')\n" +
                        "           AND EXISTS\n" +
                        "         		(SELECT NULL\n" +
                        "                  FROM AMS_ASSETS_CHECK_HEADER AACH,\n" +
                        "                       AMS_ASSETS_CHECK_LINE   AACL\n" +
                        "                 WHERE AACH.HEADER_ID = AACL.HEADER_ID\n" +
                        "                   AND AACH.ORDER_STATUS = 'ARCHIEVED'\n" +
                        "                   AND AACH.SCANOVER_DATE >= ISNULL(?, AACH.SCANOVER_DATE)\n" +
                        "                   AND AACH.SCANOVER_DATE <= ISNULL(?, AACH.SCANOVER_DATE)\n" +
                        "                   AND ((AACL.ARCHIVE_STATUS = '0' AND AACL.SCAN_STATUS = 'Y') OR \n" +
                        "                        (AACL.ARCHIVE_STATUS = '1' AND AACL.SYSTEM_STATUS = 'Y')) \n" +
                        "                   AND AACH.ORGANIZATION_ID = EII.ORGANIZATION_ID \n" +
                        "                   and EII.BARCODE = AACL.BARCODE) \n" +
                        "           AND EFA.BOOK_TYPE_CODE LIKE '%FA%'\n" +
                        "           AND (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)\n" +
                        "           AND EXISTS \n" +
                        "				(SELECT NULL\n" +
                        "                  FROM AMS_ITEM_CATEGORY_MAP AICM\n" +
                        "                 WHERE EFA.SEGMENT1 || '.' || EFA.SEGMENT2 || '.' ||\n" +
                        "                       EFA.SEGMENT3 = AICM.FA_CATEGORY_CODE\n" +
                        "                   AND AICM.NEED_SCAN = 'Y')\n" +
                        "           AND EXISTS\n" +
                        "         		(SELECT NULL\n" +
                        "                  FROM ETS_COUNTY          ACCT,\n" +
                        "                       AMS_COST_DEPT_MATCH ACDM,\n" +
                        "                       AMS_MIS_DEPT        AMD\n" +
                        "                 WHERE SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) =\n" +
                        "                       ACCT.COUNTY_CODE_MIS\n" +
                        "                   AND ACCT.COUNTY_CODE_MIS *= ACDM.COST_CENTER_CODE \n" +
                        "                   AND ACDM.DEPT_CODE *= AMD.DEPT_CODE \n" +
                        "                   AND (LTRIM(?) IS NULL OR AMD.DEPT_CODE = LTRIM(?)))\n" +
                        "           AND EFA.ASSETS_CREATE_DATE >= ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                        "           AND EFA.ASSETS_CREATE_DATE <= ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                        "           AND EFA.ORGANIZATION_ID = ? \n" +
                        
                        //"			AND NOT EXISTS (SELECT NULL FROM AMS_IGNORE_DATA H WHERE EFA.SEGMENT2 = H.SEGMENT AND EFA.SEGMENT3 = H.NAME) \n" +
                        
                        "         GROUP BY SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6)) IDENTICAL_V,\n" +
                        "       (SELECT ACCT.COUNTY_CODE_MIS COST_CENTER_CODE,\n" +
                        "               COUNT(1) UNMATCHED_COUNT\n" +
                        "          FROM ETS_ITEM_INFO       EII,\n" +
                        "               ETS_SYSTEM_ITEM     ESI,\n" +
                        "               AMS_COST_DEPT_MATCH ACDM,\n" +
                        "               ETS_COUNTY          ACCT,\n" +
                        "               ETS_OBJECT          EO,\n" +
                        "               AMS_OBJECT_ADDRESS  AOA\n" +
                        "         WHERE EII.ITEM_CODE *= ESI.ITEM_CODE \n" +
                        "           AND EII.ADDRESS_ID = AOA.ADDRESS_ID \n" +
                        "           AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO \n" +
                        "           AND ISNULL(EII.RESPONSIBILITY_DEPT, '000') = ACDM.DEPT_CODE \n" +
                        "           AND ACDM.COST_CENTER_CODE = ACCT.COUNTY_CODE_MIS \n" +
                        "           AND (EO.OBJECT_CATEGORY < = '70' OR EO.OBJECT_CATEGORY = '80') \n" +
                        "           AND (EII.FINANCE_PROP = 'UNKNOW' OR EII.FINANCE_PROP = 'ASSETS') \n" +
                        "           AND EXISTS \n" +
                        "         		(SELECT NULL \n" +
                        "                  FROM AMS_ASSETS_CHECK_HEADER AACH,\n" +
                        "                       AMS_ASSETS_CHECK_LINE   AACL\n" +
                        "                 WHERE AACH.HEADER_ID = AACL.HEADER_ID\n" +
                        "                   AND AACH.ORDER_STATUS = 'ARCHIEVED'\n" +
                        "                   AND AACH.SCANOVER_DATE >= ISNULL(?, AACH.SCANOVER_DATE)\n" +
                        "                   AND AACH.SCANOVER_DATE <= ISNULL(?, AACH.SCANOVER_DATE)\n" +
                        "                   AND ((AACL.ARCHIVE_STATUS = '0' AND AACL.SCAN_STATUS = 'Y') OR \n" +
                        "                        (AACL.ARCHIVE_STATUS = '1' AND AACL.SYSTEM_STATUS = 'Y')) \n" +
                        "                   AND AACH.ORGANIZATION_ID = EII.ORGANIZATION_ID \n" +
                        "                   and EII.BARCODE = AACL.BARCODE)\n" +
                        "           AND NOT EXISTS\n" +
                        "         		(SELECT NULL\n";
                        if (userAccount.getIsTd().equals("N")) {
                            sqlStr += " FROM ETS_FA_ASSETS EFA\n";
                        } else {
                            sqlStr += " FROM ETS_FA_ASSETS_TD EFA\n";
                        }
                        sqlStr += 
                        "                 WHERE EFA.ORGANIZATION_ID = EII.ORGANIZATION_ID\n" +
                        "                   AND EFA.TAG_NUMBER = EII.BARCODE\n" +
                        "                   AND (EXISTS \n" +
                        "						   (SELECT NULL \n" +
                        "                                  FROM AMS_ITEM_CATEGORY_MAP AICM\n" +
                        "                                 WHERE AICM.FA_CATEGORY_CODE = \n" +
                        "                                       EFA.SEGMENT1 || '.' || EFA.SEGMENT2 || '.' || \n" +
                        "                                       EFA.SEGMENT3 \n" +
                        "                                   AND AICM.NEW_ASSET_NEED_SCAN = 'N') OR\n" +
                        "                        ((SELECT COUNT(1)\n" +
                        "                            FROM AMS_NOT_SCAN_ASSETS ANSA\n" +
                        "                           WHERE dbo.CHARINDEX_4(ANSA.ASSETS_DESCRIPTION, EFA.ASSETS_DESCRIPTION, 1, 1) > 0) > 0) OR \n" +
                        "                        		  EFA.IS_RETIREMENTS = 1 OR \n" +
                        "                        		  EFA.BOOK_TYPE_CODE NOT LIKE '%FA%')) \n" +
                        
                        //"					AND NOT EXISTS (SELECT NULL FROM AMS_IGNORE_DATA H WHERE EFA.SEGMENT2 = H.SEGMENT AND EFA.SEGMENT3 = H.NAME)) \n" +
                        
                        "           AND NOT EXISTS\n" +
                        "      (SELECT 1\n";
                        if (userAccount.getIsTd().equals("N")) {
                            sqlStr += " FROM ETS_FA_ASSETS EFA\n";
                        } else {
                            sqlStr += " FROM ETS_FA_ASSETS_TD EFA\n";
                        }
                        sqlStr += 
                        "                 WHERE EFA.TAG_NUMBER = EII.BARCODE\n" +
                        "                   AND EFA.ORGANIZATION_ID = EII.ORGANIZATION_ID\n" +
                        "                   AND EXISTS\n" +
                        "                 		(SELECT NULL\n" +
                        "                          FROM AMS_ITEM_CATEGORY_MAP AICM \n" +
                        "                         WHERE AICM.FA_CATEGORY_CODE = \n" +
                        "                               EFA.SEGMENT1 || '.' || EFA.SEGMENT2 || '.' ||\n" +
                        "                               EFA.SEGMENT3\n" +
                        "                           AND AICM.NEED_SCAN = 'Y')\n" +
                        "                   AND EFA.BOOK_TYPE_CODE LIKE '%FA%'\n" +
                        "                   AND (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)) \n" +
                        
                        //"					AND NOT EXISTS (SELECT NULL FROM AMS_IGNORE_DATA H WHERE EFA.SEGMENT2 = H.SEGMENT AND EFA.SEGMENT3 = H.NAME)) \n" +
                        
                        "           AND ACCT.ORGANIZATION_ID = EII.ORGANIZATION_ID \n" +
                        "           AND (LTRIM(?) IS NULL OR EII.RESPONSIBILITY_DEPT = LTRIM(?)) \n" +
                        "           AND EII.ORGANIZATION_ID = ? \n";
                        if (userAccount.getIsTd().equals("N")) {
                            sqlStr += " AND ACCT.COMPANY_CODE LIKE '"+dto.getProviceCode()+"%'\n";
                        } else {
                            sqlStr += " AND ACCT.COMPANY_CODE LIKE '"+dto.getTdProviceCode()+"%'\n";
                        }
                        sqlStr += 
                        "         GROUP BY ACCT.COUNTY_CODE_MIS) UNMATCHED_V \n" +
                        " WHERE ACCT.COUNTY_CODE_MIS *= TOTAL_V.COST_CENTER_CODE \n" +
                        "   AND ACCT.COUNTY_CODE_MIS *= NEED_V.COST_CENTER_CODE \n" +
                        "   AND ACCT.COUNTY_CODE_MIS *= IDENTICAL_V.COST_CENTER_CODE \n" +
                        "   AND ACCT.COUNTY_CODE_MIS *= UNMATCHED_V.COST_CENTER_CODE \n" +
                        "   AND EXISTS\n" +
                                " (SELECT NULL\n" +
                                "          FROM AMS_COST_DEPT_MATCH ACDM,\n" +
                                "               AMS_MIS_DEPT        AMD\n" +
                                //"         WHERE ACCT.COUNTY_CODE_MIS *= ACDM.COST_CENTER_CODE \n" +
                                //"           AND ACDM.DEPT_CODE *= AMD.DEPT_CODE \n" +
                                
                                "         WHERE ACCT.COUNTY_CODE_MIS = ACDM.COST_CENTER_CODE \n" +
                                "           AND ACDM.DEPT_CODE = AMD.DEPT_CODE \n" +
                                
                                "           AND (LTRIM(?) IS NULL OR AMD.DEPT_CODE = LTRIM(?))) \n" +
                        "   AND ACCT.ORGANIZATION_ID = ? \n";
                        if (userAccount.getIsTd().equals("N")) {
                            sqlStr += " AND ACCT.COMPANY_CODE LIKE '"+dto.getProviceCode()+"%'\n";
                        } else {
                            sqlStr += " AND ACCT.COMPANY_CODE LIKE '"+dto.getTdProviceCode()+"%'\n";
                        }
                        sqlStr += 
                        "   AND (ISNULL(TOTAL_V.TOTAL_COUNT, 0) > 0 OR\n" +
                        "        ISNULL(ISNULL(UNMATCHED_V.UNMATCHED_COUNT, 0) + \n" +
                        "               ISNULL(IDENTICAL_V.IDENTICAL_COUNT, 0), \n" +
                        "               0) > 0)" ;
                sqlArgs.add(dto.getDeptCode());
                sqlArgs.add(dto.getDeptCode());
                
        		if (dto.getCreationDate().toString().equals("")) {
        			sqlArgs.add(null);
        		} else {
        			sqlArgs.add(dto.getCreationDate());
        		}
        		if (dto.getSQLLastUpdateDate().toString().equals("")) {
        			sqlArgs.add(null);
        		} else {
        			sqlArgs.add(dto.getSQLLastUpdateDate());
        		}  
                //sqlArgs.add(dto.getCreationDate());
                //sqlArgs.add(dto.getSQLLastUpdateDate());
                sqlArgs.add(userAccount.getOrganizationId());
                
        		if (dto.getCreationDate().toString().equals("")) {
        			sqlArgs.add(null);
        		} else {
        			sqlArgs.add(dto.getCreationDate());
        		}
        		if (dto.getSQLLastUpdateDate().toString().equals("")) {
        			sqlArgs.add(null);
        		} else {
        			sqlArgs.add(dto.getSQLLastUpdateDate());
        		}  
                //sqlArgs.add(dto.getCreationDate());
                //sqlArgs.add(dto.getSQLLastUpdateDate());
                sqlArgs.add(dto.getDeptCode());
                sqlArgs.add(dto.getDeptCode());
                sqlArgs.add(userAccount.getOrganizationId());
                
        		if (dto.getCreationDate().toString().equals("")) {
        			sqlArgs.add(null);
        		} else {
        			sqlArgs.add(dto.getCreationDate());
        		}
        		if (dto.getSQLLastUpdateDate().toString().equals("")) {
        			sqlArgs.add(null);
        		} else {
        			sqlArgs.add(dto.getSQLLastUpdateDate());
        		}  
                //sqlArgs.add(dto.getCreationDate());
                //sqlArgs.add(dto.getSQLLastUpdateDate());
                sqlArgs.add(dto.getDeptCode());
                sqlArgs.add(dto.getDeptCode());
                
        		if (dto.getCreationDate().toString().equals("")) {
        			sqlArgs.add(null);
        		} else {
        			sqlArgs.add(dto.getCreationDate());
        		}
        		if (dto.getSQLLastUpdateDate().toString().equals("")) {
        			sqlArgs.add(null);
        		} else {
        			sqlArgs.add(dto.getSQLLastUpdateDate());
        		}  
                //sqlArgs.add(dto.getCreationDate());
                //sqlArgs.add(dto.getSQLLastUpdateDate());
                sqlArgs.add(userAccount.getOrganizationId());
                
        		if (dto.getCreationDate().toString().equals("")) {
        			sqlArgs.add(null);
        		} else {
        			sqlArgs.add(dto.getCreationDate());
        		}
        		if (dto.getSQLLastUpdateDate().toString().equals("")) {
        			sqlArgs.add(null);
        		} else {
        			sqlArgs.add(dto.getSQLLastUpdateDate());
        		}  
                //sqlArgs.add(dto.getCreationDate());
                //sqlArgs.add(dto.getSQLLastUpdateDate());
                sqlArgs.add(dto.getDeptCode());
                sqlArgs.add(dto.getDeptCode());
                sqlArgs.add(userAccount.getOrganizationId());
                sqlArgs.add(dto.getDeptCode());
                sqlArgs.add(dto.getDeptCode());
                sqlArgs.add(userAccount.getOrganizationId());
                
            }


        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);
        } catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
        return sqlModel;
    }
}
