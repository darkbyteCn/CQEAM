package com.sino.ams.newasset.report.model;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.dto.AmsAssetsCheckBatchDTO;
import com.sino.ams.newasset.dto.AmsAssetsCheckHeaderDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

import java.util.ArrayList;
import java.util.List;

public class CheckResultModel extends AMSSQLProducer {

    public CheckResultModel(SfUserDTO userAccount, AmsAssetsCheckHeaderDTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    public SQLModel getPageQueryModel() throws SQLModelException {
        return newPageModel();
    }

    private SQLModel newPageModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsAssetsCheckBatchDTO dto = (AmsAssetsCheckBatchDTO) dtoParameter;
        try {
        String sqlStr = 
        		"SELECT EOCM.ORGANIZATION_ID,\n" +
                "       EOCM.COMPANY,\n" +
                "       ISNULL(TOTAL_V.TOTAL_COUNT, 0) TOTAL_COUNT,\n" +
                "       ISNULL(NEED_V.TOTAL_COUNT, 0) NEED_COUNT,\n" +
                "       ISNULL(NOT_NEED_V.TOTAL_COUNT, 0) NOT_NEED_COUNT,\n" +
                "       ISNULL(IDENTICAL_V.IDENTICAL_COUNT, 0) IDENTICAL_COUNT,\n" +
                "       ISNULL(UNMATCHED_V.UNMATCHED_COUNT, 0) UNMATCHED_COUNT,\n" +
                "       (ISNULL(IDENTICAL_V.IDENTICAL_COUNT, 0) +\n" +
                "       ISNULL(UNMATCHED_V.UNMATCHED_COUNT, 0)) SCANED_COUNT,\n" +
                "       (CASE dbo.CHARINDEX_4( '.', CONVERT(VARCHAR, ROUND(100.0 * ISNULL(IDENTICAL_V.IDENTICAL_COUNT, 0) / ISNULL(NEED_V.TOTAL_COUNT, 1), 2)) || '%', 1, 1) \n" +
                "        WHEN 1 THEN\n" +
                "          '0' || CONVERT(VARCHAR, CONVERT(DECIMAL(18,2), ROUND(100.0 * ISNULL(IDENTICAL_V.IDENTICAL_COUNT, 0) / ISNULL(NEED_V.TOTAL_COUNT, 1), 2))) || '%' \n" +
                "        ELSE\n" +
                "          CONVERT(VARCHAR, CONVERT(DECIMAL(18,2), ROUND(100.0 * ISNULL(IDENTICAL_V.IDENTICAL_COUNT, 0) / ISNULL(NEED_V.TOTAL_COUNT, 1), 2))) || '%' \n" +
                "        END) IDENTICAL_RATE_1,\n" +
                
                "       (CASE dbo.CHARINDEX_4( '.', CONVERT(VARCHAR, ROUND(100.0 * ISNULL(IDENTICAL_V.IDENTICAL_COUNT, 0) / (CASE (ISNULL(IDENTICAL_V.IDENTICAL_COUNT, 0) + ISNULL(UNMATCHED_V.UNMATCHED_COUNT, 0)) WHEN 0 THEN 1 ELSE (ISNULL(IDENTICAL_V.IDENTICAL_COUNT, 0) + ISNULL(UNMATCHED_V.UNMATCHED_COUNT, 0)) END), 2)) || '%', 1, 1) \n" +
                "        WHEN 1 THEN\n" +
                "          '0' || CONVERT(VARCHAR, CONVERT(DECIMAL(18,2), ROUND(100.0 * ISNULL(IDENTICAL_V.IDENTICAL_COUNT, 0) / (CASE (ISNULL(IDENTICAL_V.IDENTICAL_COUNT, 0) + ISNULL(UNMATCHED_V.UNMATCHED_COUNT, 0)) WHEN 0 THEN 1 ELSE (ISNULL(IDENTICAL_V.IDENTICAL_COUNT, 0) + ISNULL(UNMATCHED_V.UNMATCHED_COUNT, 0)) END), 2))) || '%' \n" +
                "        ELSE\n" +
                "          CONVERT(VARCHAR, CONVERT(DECIMAL(18,2), ROUND(100.0 * ISNULL(IDENTICAL_V.IDENTICAL_COUNT, 0) / (CASE (ISNULL(IDENTICAL_V.IDENTICAL_COUNT, 0) + ISNULL(UNMATCHED_V.UNMATCHED_COUNT, 0)) WHEN 0 THEN 1 ELSE (ISNULL(IDENTICAL_V.IDENTICAL_COUNT, 0) + ISNULL(UNMATCHED_V.UNMATCHED_COUNT, 0)) END), 2))) || '%' \n" +
                "        END) IDENTICAL_RATE_2\n" +
                "  FROM ETS_OU_CITY_MAP EOCM,\n" +
                "       (SELECT EFA.ORGANIZATION_ID,\n" +
                "               COUNT(EFA.ASSET_ID) TOTAL_COUNT\n";
                if (userAccount.getIsTd().equals("N")) {
                    sqlStr += 
                    	"  FROM ETS_FA_ASSETS EFA\n";
                } else {
                    sqlStr += 
                    	"  FROM ETS_FA_ASSETS_TD EFA\n";
                }
//                "          FROM ETS_FA_ASSETS EFA\n" +
                sqlStr +=
                "         WHERE EFA.BOOK_TYPE_CODE LIKE '%FA%'\n" +
                "           AND (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)\n" +
                "           AND EXISTS\n" +
                "       		  (SELECT NULL\n" +
                "          			 FROM ETS_COUNTY ACCT\n" +
                "                   WHERE SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) = ACCT.COUNTY_CODE_MIS)\n" +
                "               	  AND EFA.ASSETS_CREATE_DATE >= ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                "               	  AND EFA.ASSETS_CREATE_DATE <= ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                "               	  AND EFA.TAG_NUMBER >= ISNULL(LTRIM(?), EFA.TAG_NUMBER)\n" +
                "               	  AND EFA.TAG_NUMBER <= ISNULL(LTRIM(?), EFA.TAG_NUMBER)\n" +
                "               	  AND EFA.ORGANIZATION_ID = ISNULL(?, EFA.ORGANIZATION_ID)\n" +
                
                "					  AND NOT EXISTS (SELECT NULL FROM AMS_IGNORE_DATA H WHERE EFA.SEGMENT2 = H.SEGMENT AND EFA.SEGMENT3 = H.NAME)\n" +

                "         			GROUP BY EFA.ORGANIZATION_ID) TOTAL_V,\n" +
                "       (SELECT EFA.ORGANIZATION_ID,\n" +
                "               COUNT(EFA.ASSET_ID) TOTAL_COUNT\n";
                if (userAccount.getIsTd().equals("N")) {
                    sqlStr += 
                    	"  FROM ETS_FA_ASSETS EFA\n";
                } else {
                    sqlStr += 
                    	"  FROM ETS_FA_ASSETS_TD EFA\n";
                }
//                "          FROM ETS_FA_ASSETS EFA\n" +
                sqlStr += 
                "         WHERE EFA.BOOK_TYPE_CODE LIKE '%FA%'\n" +
                "           AND (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)\n" +
                "           AND EXISTS " +
                "				  (SELECT NULL\n" +
                "                 	 FROM AMS_ITEM_CATEGORY_MAP AICM\n" +
                "                   WHERE EFA.SEGMENT1 || '.' || EFA.SEGMENT2 || '.' || EFA.SEGMENT3 = AICM.FA_CATEGORY_CODE\n" +
                "                     AND AICM.NEED_SCAN = 'Y')\n" +
                "           AND EXISTS\n" +
                "         		(SELECT NULL\n" +
                "                  FROM ETS_COUNTY ACCT\n" +
                "                 WHERE SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) =\n" +
                "                       ACCT.COUNTY_CODE_MIS)\n" +
                "               AND EFA.ASSETS_CREATE_DATE >= ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                "               AND EFA.ASSETS_CREATE_DATE <= ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                "               AND EFA.TAG_NUMBER >= ISNULL(LTRIM(?), EFA.TAG_NUMBER)\n" +
                "               AND EFA.TAG_NUMBER <= ISNULL(LTRIM(?), EFA.TAG_NUMBER)\n" +
                "               AND EFA.ORGANIZATION_ID = ISNULL(?, EFA.ORGANIZATION_ID)\n" +
                
                "				AND NOT EXISTS (SELECT NULL FROM AMS_IGNORE_DATA H WHERE EFA.SEGMENT2 = H.SEGMENT AND EFA.SEGMENT3 = H.NAME)\n" +
                
                "         GROUP BY EFA.ORGANIZATION_ID) NEED_V,\n" +
                "       (SELECT EFA.ORGANIZATION_ID,\n" +
                "               COUNT(EFA.ASSET_ID) TOTAL_COUNT\n";
                if (userAccount.getIsTd().equals("N")) {
                    sqlStr += 
                    	"  FROM ETS_FA_ASSETS EFA\n";
                } else {
                    sqlStr += 
                    	"  FROM ETS_FA_ASSETS_TD EFA\n";
                }
//                "          FROM ETS_FA_ASSETS EFA\n" +
                sqlStr +=
                "         WHERE EFA.BOOK_TYPE_CODE LIKE '%FA%'\n" +
                "           AND (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)\n" +
                "           AND (EXISTS\n" +
                "               (SELECT NULL\n" +
                "                  FROM AMS_ITEM_CATEGORY_MAP AICM\n" +
                "                 WHERE AICM.FA_CATEGORY_CODE =\n" +
                "                       EFA.SEGMENT1 || '.' || EFA.SEGMENT2 || '.' || EFA.SEGMENT3\n" +
                "                       AND AICM.NEW_ASSET_NEED_SCAN = 'N') OR\n" +
                "               ((SELECT COUNT(1)\n" +
                "                   FROM AMS_NOT_SCAN_ASSETS ANSA\n" +
                "				   WHERE dbo.CHARINDEX_4(ANSA.ASSETS_DESCRIPTION, EFA.ASSETS_DESCRIPTION, 1, 1) > 0) > 0)) \n" +	
                "           AND EXISTS\n" +
                "               (SELECT NULL\n" +
                "                  FROM ETS_COUNTY ACCT\n" +
                "                 WHERE SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) =\n" +
                "                       ACCT.COUNTY_CODE_MIS)\n" +
                "           AND EFA.ASSETS_CREATE_DATE >= ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                "           AND EFA.ASSETS_CREATE_DATE <= ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                "           AND EFA.ORGANIZATION_ID = ISNULL(?, EFA.ORGANIZATION_ID)\n" +
                "           AND EFA.TAG_NUMBER >= ISNULL(LTRIM(?), EFA.TAG_NUMBER)\n" +
                "           AND EFA.TAG_NUMBER <= ISNULL(LTRIM(?), EFA.TAG_NUMBER)\n" +
                
                "			AND NOT EXISTS (SELECT NULL FROM AMS_IGNORE_DATA H WHERE EFA.SEGMENT2 = H.SEGMENT AND EFA.SEGMENT3 = H.NAME)\n" +
                
                "         GROUP BY EFA.ORGANIZATION_ID) NOT_NEED_V,\n" +
                "       (SELECT EII.ORGANIZATION_ID,\n" +
                "               COUNT(EII.BARCODE) IDENTICAL_COUNT\n" +
                "          FROM ETS_ITEM_INFO      EII,\n";
                if (userAccount.getIsTd().equals("N")) {
                    sqlStr += " ETS_FA_ASSETS EFA,\n";
                } else {
                    sqlStr += " ETS_FA_ASSETS_TD EFA,\n";
                }
//                "               ETS_FA_ASSETS      EFA,\n" +
                	sqlStr +=
                "               AMS_OBJECT_ADDRESS AOA,\n" +
                "               ETS_OBJECT         EO\n" +
                "         WHERE EFA.ORGANIZATION_ID = EII.ORGANIZATION_ID\n" +
                "               AND EFA.TAG_NUMBER = EII.BARCODE\n" +
                "               AND EFA.BOOK_TYPE_CODE LIKE '%FA%'\n" +
                "               AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                "               AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                "               AND (EO.OBJECT_CATEGORY < = '70' OR EO.OBJECT_CATEGORY = '80')\n" +
                "               AND EFA.ASSETS_CREATE_DATE >= ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                "               AND EFA.ASSETS_CREATE_DATE <= ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                "               AND EFA.TAG_NUMBER >= ISNULL(LTRIM(?), EFA.TAG_NUMBER)\n" +
                "               AND EFA.TAG_NUMBER <= ISNULL(LTRIM(?), EFA.TAG_NUMBER)\n" +
                "               AND (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)\n" +
                "               AND EXISTS " +
                "					(SELECT NULL\n" +
                "                      FROM AMS_ITEM_CATEGORY_MAP AICM\n" +
                "                 	  WHERE EFA.SEGMENT1 || '.' || EFA.SEGMENT2 || '.' || EFA.SEGMENT3 = AICM.FA_CATEGORY_CODE\n" +
                "                       AND AICM.NEED_SCAN = 'Y')\n" +
                "               AND EXISTS\n" +
                "         		(SELECT NULL\n" +
                "                  FROM (SELECT DISTINCT AACH.ORGANIZATION_ID,\n" +
                "                                        AACL.BARCODE\n" +
                "                          FROM AMS_ASSETS_CHECK_HEADER AACH,\n" +
                "                               AMS_ASSETS_CHECK_LINE   AACL\n" +
                "                         WHERE AACH.HEADER_ID = AACL.HEADER_ID\n" +
                "                           AND AACH.ORDER_STATUS = 'ARCHIEVED'\n" +
                "                           AND AACH.ARCHIVED_DATE >= ISNULL(?, AACH.ARCHIVED_DATE)\n" +
                "                           AND AACH.ARCHIVED_DATE <= ISNULL(?, AACH.ARCHIVED_DATE)\n" +
                "                           AND ((AACL.ARCHIVE_STATUS = '0' AND AACL.SCAN_STATUS = 'Y') OR\n" +
                "                                (AACL.ARCHIVE_STATUS = '1' AND AACL.SYSTEM_STATUS = 'Y'))\n" +
                "                           AND AACH.ORGANIZATION_ID = ISNULL(?, AACH.ORGANIZATION_ID)) TMP_1\n" +
                "                 WHERE EII.ORGANIZATION_ID = TMP_1.ORGANIZATION_ID\n" +
                "                       AND EII.BARCODE = TMP_1.BARCODE)\n" +
                "               AND EII.ORGANIZATION_ID = ISNULL(?, EII.ORGANIZATION_ID)\n" +
                
                "				AND NOT EXISTS (SELECT NULL FROM AMS_IGNORE_DATA H WHERE EFA.SEGMENT2 = H.SEGMENT AND EFA.SEGMENT3 = H.NAME)\n" +
                
                "         GROUP BY EII.ORGANIZATION_ID) IDENTICAL_V,\n" +
                "       (SELECT EII.ORGANIZATION_ID,\n" +
                "               COUNT(1) UNMATCHED_COUNT\n" +
                "          FROM ETS_ITEM_INFO      EII,\n" +
                "               ETS_SYSTEM_ITEM    ESI,\n" +
                "               ETS_OBJECT         EO,\n" +
                "               AMS_OBJECT_ADDRESS AOA\n" +
                "         WHERE EII.ITEM_CODE *= ESI.ITEM_CODE \n" +
                "               AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                "               AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                "               AND (EO.OBJECT_CATEGORY < = '70' OR EO.OBJECT_CATEGORY = '80')\n";
                if (userAccount.getIsTd().equals("N")) {
                    sqlStr += " AND (EII.FINANCE_PROP = 'UNKNOW' OR EII.FINANCE_PROP = 'ASSETS')\n";
                } else {
                    sqlStr += " AND (EII.FINANCE_PROP = 'UNKNOW' OR EII.FINANCE_PROP = 'TD_ASSETS')\n";
                }
                sqlStr += "     AND EII.BARCODE >= ISNULL(LTRIM(?), EII.BARCODE)\n" +
                "               AND EII.BARCODE <= ISNULL(LTRIM(?), EII.BARCODE)\n" +
                "               AND EXISTS\n" +
                "         			(SELECT NULL\n" +
                "                  	   FROM (SELECT DISTINCT AACH.ORGANIZATION_ID,\n" +
                "                                        	 AACL.BARCODE\n" +
                "                          	   FROM AMS_ASSETS_CHECK_HEADER AACH,\n" +
                "                               	AMS_ASSETS_CHECK_LINE   AACL\n" +
                "                         	  WHERE AACH.HEADER_ID = AACL.HEADER_ID\n" +
                "                               AND AACH.ORDER_STATUS = 'ARCHIEVED'\n" +
                "                               AND AACH.ARCHIVED_DATE >= ISNULL(?, AACH.ARCHIVED_DATE)\n" +
                "                               AND AACH.ARCHIVED_DATE <= ISNULL(?, AACH.ARCHIVED_DATE)\n" +
                "                               AND ((AACL.ARCHIVE_STATUS = '0' AND AACL.SCAN_STATUS = 'Y') OR\n" +
                "                               	 (AACL.ARCHIVE_STATUS = '1' AND AACL.SYSTEM_STATUS = 'Y'))\n" +
                "                               AND (? IS NULL OR AACH.ORGANIZATION_ID = ?)) TMP_1\n" +
                "                     WHERE TMP_1.BARCODE = EII.BARCODE AND TMP_1.ORGANIZATION_ID = EII.ORGANIZATION_ID)\n" +
                "               AND NOT EXISTS\n" +
                "         	 		(SELECT NULL\n";
                if (userAccount.getIsTd().equals("N")) {
                    sqlStr += " 	   FROM ETS_FA_ASSETS EFA\n";
                } else {
                    sqlStr += " 	   FROM ETS_FA_ASSETS_TD EFA\n";
                }
//                "                  FROM ETS_FA_ASSETS EFA\n" +
                sqlStr +=
                	"                 WHERE EFA.BOOK_TYPE_CODE LIKE '%FA%'\n" +
                "                       AND (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)\n" +
                "                       AND EXISTS\n" +
                "                 			(SELECT NULL\n" +
                "                          	   FROM AMS_ITEM_CATEGORY_MAP AICM\n" +
                "                             WHERE AICM.FA_CATEGORY_CODE = EFA.SEGMENT1 || '.' || EFA.SEGMENT2 || '.' || EFA.SEGMENT3\n" +
                "                               AND AICM.NEED_SCAN = 'Y')\n" +
                "                       AND EFA.ORGANIZATION_ID = EII.ORGANIZATION_ID\n" +
                "                       AND EII.BARCODE = EFA.TAG_NUMBER \n" +
                
                "					    AND NOT EXISTS (SELECT NULL FROM AMS_IGNORE_DATA H WHERE EFA.SEGMENT2 = H.SEGMENT AND EFA.SEGMENT3 = H.NAME) )\n" +
                
                "               AND NOT EXISTS\n" +
                "         			(SELECT NULL\n";
                if (userAccount.getIsTd().equals("N")) {
                    sqlStr += " 	   FROM ETS_FA_ASSETS EFA\n";
                } else {
                    sqlStr += " 	   FROM ETS_FA_ASSETS_TD EFA\n";
                }
//                "                  FROM ETS_FA_ASSETS EFA\n" +
                sqlStr +=
                	"                 WHERE (EFA.IS_RETIREMENTS = 1 OR\n" +
                "                       	 EFA.BOOK_TYPE_CODE NOT LIKE '%FA%' OR " +
                "							 EXISTS\n" +
                "                        	   (SELECT NULL\n" +
                "                           	  FROM AMS_ITEM_CATEGORY_MAP AICM\n" +
                "                                WHERE AICM.FA_CATEGORY_CODE = EFA.SEGMENT1 || '.' || EFA.SEGMENT2 || '.' || EFA.SEGMENT3\n" +
                "                                  AND AICM.NEED_SCAN = 'N'))\n" +
                "                       AND EFA.ORGANIZATION_ID = EII.ORGANIZATION_ID\n" +
                "                       AND EFA.TAG_NUMBER = EII.BARCODE \n" +
                
                "					    AND NOT EXISTS (SELECT NULL FROM AMS_IGNORE_DATA H WHERE EFA.SEGMENT2 = H.SEGMENT AND EFA.SEGMENT3 = H.NAME) )\n" +
                
                "               AND (? IS NULL OR EII.ORGANIZATION_ID = ?)\n" +
                "         GROUP BY EII.ORGANIZATION_ID) UNMATCHED_V\n" +
                " WHERE EOCM.ORGANIZATION_ID *= TOTAL_V.ORGANIZATION_ID \n" +
                "   AND EOCM.ORGANIZATION_ID *= NEED_V.ORGANIZATION_ID \n" +
                "   AND EOCM.ORGANIZATION_ID *= NOT_NEED_V.ORGANIZATION_ID \n" +
                "   AND EOCM.ORGANIZATION_ID *= IDENTICAL_V.ORGANIZATION_ID \n" +
                "   AND EOCM.ORGANIZATION_ID *= UNMATCHED_V.ORGANIZATION_ID \n" +
                "   AND EOCM.ORGANIZATION_ID = ISNULL(?, EOCM.ORGANIZATION_ID)\n";
            if (userAccount.getIsTd().equals("N")) {
                sqlStr += " AND EOCM.IS_TD = 'N'";
            } else {
                sqlStr += " AND EOCM.IS_TD = 'Y'";
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
        //sqlArgs.add(dto.getCreationDate());
        //sqlArgs.add(dto.getSQLLastUpdateDate());
        sqlArgs.add(dto.getFromBarcode());
        sqlArgs.add(dto.getToBarcode());
        sqlArgs.add(dto.getOrganizationId());
        
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
        sqlArgs.add(dto.getOrganizationId());
        
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
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getFromBarcode());
        sqlArgs.add(dto.getToBarcode());
        
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
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getFromBarcode());
        sqlArgs.add(dto.getToBarcode());
        
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
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        } catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
        return sqlModel;
    }

    //已盘MIS清单
    public SQLModel resultModelOne() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
        String disabled = dto.getDisabled();
        String sqlStr = "";
        try {
            if (!disabled.equals("")) { //市用
                sqlStr = "SELECT\n" +
                        "       EFA.MIS_TAG_NUMBER,\n" +
                        "       EII.BARCODE,\n" +
                        //"       AMS_MIS_PKG.GET_DEPT_NAME(EII.SPECIALITY_DEPT) DEPT_NAME,\n" +
                        "		dbo.AMP_GET_DEPT_NAME(EII.SPECIALITY_DEPT) DEPT_NAME,\n" +
                        "       EII.ITEM_QTY,\n" +
                        "       EII.ACTUAL_QTY,\n" +
                        "       ESI.ITEM_NAME,\n" +
                        "       EFA.ASSETS_DESCRIPTION,\n" +
                        "       ESI.ITEM_SPEC ITEM_SPEC,\n" +
                        "       EFA.MODEL_NUMBER,\n" +
                        "       EII.START_DATE,\n" +
                        "       EFA.DATE_PLACED_IN_SERVICE,\n" +
                        "       EO.WORKORDER_OBJECT_CODE,\n" +
                        "       EFA.ASSETS_LOCATION_CODE,\n" +
                        "       EO.WORKORDER_OBJECT_NAME,\n" +
                        "       EFA.ASSETS_LOCATION,\n" +
                        "       dbo.AMP_GET_EMPLOYEE_NUMBER(EII.RESPONSIBILITY_USER) EMPLOYEE_NUMBER,\n" +
                        "       EFA.ASSIGNED_TO_NUMBER,\n" +
                        "       dbo.AMP_GET_EMPLOYEE_NAME(EII.RESPONSIBILITY_USER) USER_NAME,\n" +
                        "       EFA.ASSIGNED_TO_NAME,\n" +
                        "		ISNULL( LTRIM((CASE ESI.ITEM_NAME WHEN EFA.ASSETS_DESCRIPTION THEN '' ELSE '资产名称;' END) || \n" + 
                        " 		  		(CASE ESI.ITEM_SPEC WHEN EFA.MODEL_NUMBER THEN '' ELSE '资产型号;' END) || \n" +
                        " 		  		(CASE EII.START_DATE WHEN EFA.DATE_PLACED_IN_SERVICE THEN '' ELSE '启用日期;' END) || \n" +
                        " 		  		(CASE dbo.AMP_GET_EMPLOYEE_NUMBER(EII.RESPONSIBILITY_USER) WHEN EFA.ASSIGNED_TO_NUMBER THEN '' ELSE '责任人' END)), \n" +
                        "				'无') CHANGED_CONTENT \n" ;
                        
/*                        "       ISNULL(DECODE(ESI.ITEM_NAME, EFA.ASSETS_DESCRIPTION, '', '资产名称;') || \n" +
                        "              DECODE(ESI.ITEM_SPEC, EFA.MODEL_NUMBER, '', '资产型号;') || \n" +
                        "              DECODE(EII.START_DATE, EFA.DATE_PLACED_IN_SERVICE, '', '启用日期;') || \n" +
                        "			   DECODE(dbo.AMP_GET_EMPLOYEE_NUMBER(EII.RESPONSIBILITY_USER), \n" +
                        "                                     EFA.ASSIGNED_TO_NUMBER, '', '责任人'), '无') \n" +
                        "              CHANGED_CONTENT \n";*/
                if (userAccount.getIsTd().equals("N")) {
                    sqlStr += " FROM ETS_FA_ASSETS  EFA,\n";
                } else {
                    sqlStr += " FROM ETS_FA_ASSETS_TD EFA,\n";
                }
//                        "  FROM ETS_FA_ASSETS       EFA,\n" +
                        sqlStr +=
                        "       ETS_ITEM_INFO       EII,\n" +
                        "       ETS_OBJECT          EO,\n" +
                        "       AMS_OBJECT_ADDRESS  AOA,\n" +
                        "       ETS_SYSTEM_ITEM     ESI\n" +
                        " WHERE EII.ORGANIZATION_ID = EFA.ORGANIZATION_ID\n" +
                        "   AND EII.BARCODE = EFA.TAG_NUMBER\n" +
                        "   AND EO.WORKORDER_OBJECT_NO = AOA.OBJECT_NO\n" +
                        "   AND AOA.ADDRESS_ID = EII.ADDRESS_ID\n" +
                        "   AND EII.ITEM_CODE *= ESI.ITEM_CODE \n" +
                        "   AND (EO.OBJECT_CATEGORY < = '70' OR EO.OBJECT_CATEGORY = '80')" +
                        "   AND EXISTS\n" +
                        " 		(SELECT NULL\n" +
                        "          FROM AMS_ASSETS_CHECK_HEADER AACH,\n" +
                        "               AMS_ASSETS_CHECK_LINE   AACL\n" +
                        "         WHERE AACH.HEADER_ID = AACL.HEADER_ID\n" +
                        "           AND AACH.ORDER_STATUS = 'ARCHIEVED'\n" +
                        "           AND AACH.ARCHIVED_DATE >= ISNULL(?, AACH.ARCHIVED_DATE)\n" +
                        "           AND AACH.ARCHIVED_DATE <= ISNULL(?, AACH.ARCHIVED_DATE)\n" +
                        "           AND ((AACL.ARCHIVE_STATUS = '0' AND AACL.SCAN_STATUS = 'Y') OR\n" +
                        "                (AACL.ARCHIVE_STATUS = '1' AND AACL.SYSTEM_STATUS = 'Y'))\n" +
                        "           AND AACH.ORGANIZATION_ID = ?\n" +
                        "           AND EII.BARCODE = AACL.BARCODE)\n" +
                        "   AND EFA.BOOK_TYPE_CODE LIKE '%FA%'\n" +
                        "   AND (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)\n" +
                        "   AND EXISTS\n" +
                        " 		(SELECT NULL\n" +
                        "          FROM AMS_ITEM_CATEGORY_MAP AICM\n" +
                        "         WHERE EFA.SEGMENT1 || '.' || EFA.SEGMENT2 || '.' || EFA.SEGMENT3 = AICM.FA_CATEGORY_CODE\n" +
                        "           AND AICM.NEED_SCAN = 'Y')\n" +
                        "   AND EFA.ASSETS_CREATE_DATE >= ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                        "   AND EFA.ASSETS_CREATE_DATE <= ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                        "   AND EFA.TAG_NUMBER >= ISNULL(LTRIM(?), EFA.TAG_NUMBER)\n" +
                        "   AND EFA.TAG_NUMBER <= ISNULL(LTRIM(?), EFA.TAG_NUMBER)\n" +
                        "   AND SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) =\n" +
                        "       ISNULL(?, SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6))\n" +
                        "   AND (EO.WORKORDER_OBJECT_CODE LIKE ISNULL(LTRIM(?), EO.WORKORDER_OBJECT_CODE) OR\n" +
                        "        EO.WORKORDER_OBJECT_NAME LIKE ISNULL(LTRIM(?), EO.WORKORDER_OBJECT_NAME) OR\n" +
                        "        EFA.ASSETS_LOCATION_CODE LIKE ISNULL(LTRIM(?), EFA.ASSETS_LOCATION_CODE) OR\n" +
                        "        EFA.ASSETS_LOCATION LIKE ISNULL(LTRIM(?), EFA.ASSETS_LOCATION)\n" +
                        "       OR EFA.MIS_TAG_NUMBER LIKE ISNULL(LTRIM(?),EFA.MIS_TAG_NUMBER)\n" +
                        "       OR dbo.AMP_GET_EMPLOYEE_NUMBER(EII.RESPONSIBILITY_USER) LIKE ISNULL(LTRIM(?), dbo.AMP_GET_EMPLOYEE_NUMBER(EII.RESPONSIBILITY_USER))\n" +
                        "       OR EFA.ASSIGNED_TO_NAME LIKE ISNULL(LTRIM(?),EFA.ASSIGNED_TO_NAME)" +
                        "       OR ESI.ITEM_NAME LIKE ISNULL(LTRIM(?),ESI.ITEM_NAME)\n" +
                        "       OR ESI.ITEM_SPEC LIKE ISNULL(LTRIM(?),ESI.ITEM_SPEC)\n" +
                        "       OR EFA.ASSETS_DESCRIPTION LIKE ISNULL(LTRIM(?),EFA.ASSETS_DESCRIPTION)\n" +
                        "       OR EFA.MODEL_NUMBER LIKE ISNULL(LTRIM(?),EFA.MODEL_NUMBER))\n" +
                        "   AND EFA.ORGANIZATION_ID = ?";
                        
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
                sqlArgs.add(dto.getOrganizationId());
                sqlArgs.add(dto.getCreationDate());
                
                if (dto.getSQLLastUpdateDate().toString().equals("")) {
                	sqlArgs.add(null);
                } else {
                	sqlArgs.add(dto.getSQLLastUpdateDate());
                }  
                //sqlArgs.add(dto.getSQLLastUpdateDate());
                sqlArgs.add(dto.getFromBarcode());
                sqlArgs.add(dto.getToBarcode());
                
                if (dto.getCostCode().toString().equals("")) {
                	sqlArgs.add(null);
                } else {
                	sqlArgs.add(dto.getCostCode());
                }  
                //sqlArgs.add(dto.getCostCode());
                
                sqlArgs.add(dto.getCheckLocationName());
                sqlArgs.add(dto.getCheckLocationName());
                sqlArgs.add(dto.getCheckLocationName());
                sqlArgs.add(dto.getCheckLocationName());
                sqlArgs.add(dto.getCheckLocationName());
                sqlArgs.add(dto.getCheckLocationName());
                sqlArgs.add(dto.getCheckLocationName());
                sqlArgs.add(dto.getCheckLocationName());
                sqlArgs.add(dto.getCheckLocationName());
                sqlArgs.add(dto.getCheckLocationName());
                sqlArgs.add(dto.getCheckLocationName());
                sqlArgs.add(dto.getOrganizationId());
            } else { //省用
                sqlStr = "SELECT\n" +
                        "       EFA.MIS_TAG_NUMBER,\n" +
                        "       dbo.AMP_GET_DEPT_NAME(EII.SPECIALITY_DEPT) DEPT_NAME,\n" +
                        "       EII.ITEM_QTY,\n" +
                        "       EII.ACTUAL_QTY,\n" +
                        "       EII.BARCODE BARCODE,\n" +
                        "       ESI.ITEM_NAME ITEM_NAME,\n" +
                        "       EFA.ASSETS_DESCRIPTION,\n" +
                        "       ESI.ITEM_SPEC,\n" +
                        "       EFA.MODEL_NUMBER,\n" +
                        "       EII.START_DATE,\n" +
                        "       EFA.DATE_PLACED_IN_SERVICE,\n" +
                        "       EO.WORKORDER_OBJECT_CODE,\n" +
                        "       EFA.ASSETS_LOCATION_CODE,\n" +
                        "       EO.WORKORDER_OBJECT_NAME,\n" +
                        "       EFA.ASSETS_LOCATION,\n" +
                        "       dbo.AMP_GET_EMPLOYEE_NUMBER(EII.RESPONSIBILITY_USER) EMPLOYEE_NUMBER,\n" +
                        "       EFA.ASSIGNED_TO_NUMBER,\n" +
                        "       dbo.AMP_GET_EMPLOYEE_NAME(EII.RESPONSIBILITY_USER) USER_NAME,\n" +
                        "       EFA.ASSIGNED_TO_NAME,\n" +
                        "       dbo.AMP_GET_COST_CENTER_CODE(EII.RESPONSIBILITY_DEPT) AMS_COST_CODE,\n" +
                        "       SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) MIS_COST_CODE,\n" +
                        "       dbo.AMP_GET_COST_CENTER_NAME(EII.RESPONSIBILITY_DEPT) AMS_COST_NAME,\n" +
                        "       ACCT.COUNTY_NAME MIS_COST_NAME,\n" +
                        "       ISNULL( LTRIM((CASE ESI.ITEM_NAME WHEN EFA.ASSETS_DESCRIPTION THEN '' ELSE '资产名称;' END) || \n" +
                        "               (CASE ESI.ITEM_SPEC WHEN EFA.MODEL_NUMBER THEN '' ELSE '资产型号;' END) || \n" +
                        "               (CASE EII.START_DATE WHEN EFA.DATE_PLACED_IN_SERVICE THEN '' ELSE '启用日期;' END) || \n" +
                        "			    (CASE dbo.AMP_GET_EMPLOYEE_NUMBER(EII.RESPONSIBILITY_USER) WHEN EFA.ASSIGNED_TO_NUMBER THEN '' ELSE '责任人;' END) || \n" +
                        "               (CASE dbo.AMP_GET_COST_CENTER_CODE(EII.RESPONSIBILITY_DEPT) WHEN SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) THEN '' ELSE '成本中心' END)), \n" +
                        "               '无') CHANGED_CONTENT \n";
/*                        "       ISNULL(DECODE(ESI.ITEM_NAME, EFA.ASSETS_DESCRIPTION, '', '资产名称;') || \n" +
                        "              DECODE(ESI.ITEM_SPEC, EFA.MODEL_NUMBER, '', '资产型号;') || \n" +
                        "              DECODE(EII.START_DATE, EFA.DATE_PLACED_IN_SERVICE, '', '启用日期;') || \n" +
                        "			   DECODE(dbo.AMP_GET_EMPLOYEE_NUMBER(EII.RESPONSIBILITY_USER),\n" +
                        "                                     EFA.ASSIGNED_TO_NUMBER, '', '责任人;') || \n" +
                        "              DECODE(dbo.AMP_GET_COST_CENTER_CODE(EII.RESPONSIBILITY_DEPT), \n" +
                        "                     SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6), '', '成本中心'), \n" +
                        "              '无') CHANGED_CONTENT \n";*/
                        if (userAccount.getIsTd().equals("N")) {
                            sqlStr += " FROM ETS_FA_ASSETS  EFA,\n";
                        } else {
                            sqlStr += " FROM ETS_FA_ASSETS_TD EFA,\n";
                        }
//                        "  FROM ETS_FA_ASSETS       EFA,\n" +
                        sqlStr += 
                        "       ETS_ITEM_INFO       EII,\n" +
                        "       ETS_SYSTEM_ITEM     ESI,\n" +
                        "       ETS_OBJECT          EO,\n" +
                        "       AMS_OBJECT_ADDRESS  AOA,\n" +
                        "       ETS_COUNTY          ACCT\n" +
                        " WHERE EII.ITEM_CODE *= ESI.ITEM_CODE \n" +
                        "   AND EII.ADDRESS_ID = AOA.ADDRESS_ID \n" +
                        "   AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO \n" +
                        "   AND EII.ORGANIZATION_ID = EFA.ORGANIZATION_ID \n" +
                        "   AND EII.BARCODE = EFA.TAG_NUMBER \n" +
                        //"   AND SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) *= ACCT.COUNTY_CODE_MIS \n" +
                        "   AND SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) = ACCT.COUNTY_CODE_MIS \n" +
                        "   AND (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2) \n" +
                        "   AND EFA.BOOK_TYPE_CODE LIKE '%FA%' \n" +
                        "   AND (EO.OBJECT_CATEGORY < = '70' OR EO.OBJECT_CATEGORY = '80') \n" +
                        "   AND EFA.TAG_NUMBER >= ISNULL(LTRIM(?), EFA.TAG_NUMBER)\n" +
                        "   AND EFA.TAG_NUMBER <= ISNULL(LTRIM(?), EFA.TAG_NUMBER)\n" +
                        "   AND EXISTS (SELECT NULL\n" +
                        "          FROM AMS_ITEM_CATEGORY_MAP AICM\n" +
                        "         WHERE EFA.SEGMENT1 || '.' || EFA.SEGMENT2 || '.' || EFA.SEGMENT3 = AICM.FA_CATEGORY_CODE\n" +
                        "           AND AICM.NEED_SCAN = 'Y')\n" +
                        "   AND EXISTS\n" +
                        " 		(SELECT NULL\n" +
                        "          FROM (SELECT DISTINCT AACH.ORGANIZATION_ID, AACL.BARCODE\n" +
                        //"          FROM (SELECT DISTINCT AACL.BARCODE\n" +
                        "                  FROM AMS_ASSETS_CHECK_HEADER AACH,\n" +
                        "                       AMS_ASSETS_CHECK_LINE   AACL\n" +
                        "                 WHERE AACH.HEADER_ID = AACL.HEADER_ID\n" +
                        "                   AND AACH.ORDER_STATUS = 'ARCHIEVED'\n" +
                        "                   AND AACH.ARCHIVED_DATE >= ISNULL(?, AACH.ARCHIVED_DATE)\n" +
                        "                   AND AACH.ARCHIVED_DATE <= ISNULL(?, AACH.ARCHIVED_DATE)\n" +
                        "                   AND ((AACL.ARCHIVE_STATUS = '0' AND AACL.SCAN_STATUS = 'Y') OR\n" +
                        "                       (AACL.ARCHIVE_STATUS = '1' AND\n" +
                        "                       AACL.SYSTEM_STATUS = 'Y'))\n" +
                        "                   AND AACH.ORGANIZATION_ID = ?) TMP_1\n" +
                        "         WHERE EII.BARCODE = TMP_1.BARCODE)\n" +
                        "   AND EFA.ASSETS_CREATE_DATE >= ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                        "   AND EFA.ASSETS_CREATE_DATE <= ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                        "   AND (EO.WORKORDER_OBJECT_CODE LIKE ISNULL(LTRIM(?), EO.WORKORDER_OBJECT_CODE) OR\n" +
                        "        EO.WORKORDER_OBJECT_NAME LIKE ISNULL(LTRIM(?), EO.WORKORDER_OBJECT_NAME) OR\n" +
                        "        EFA.ASSETS_LOCATION_CODE LIKE ISNULL(LTRIM(?), EFA.ASSETS_LOCATION_CODE) OR\n" +
                        "        EFA.ASSETS_LOCATION LIKE ISNULL(LTRIM(?), EFA.ASSETS_LOCATION)" +
                        "       OR EFA.MIS_TAG_NUMBER LIKE ISNULL(LTRIM(?),EFA.MIS_TAG_NUMBER)\n" +
                        "       OR dbo.AMP_GET_EMPLOYEE_NUMBER(EII.RESPONSIBILITY_USER) LIKE ISNULL(LTRIM(?),dbo.AMP_GET_EMPLOYEE_NUMBER(EII.RESPONSIBILITY_USER))\n" +
                        "       OR EFA.ASSIGNED_TO_NAME LIKE ISNULL(LTRIM(?),EFA.ASSIGNED_TO_NAME)" +
                        "       OR ESI.ITEM_NAME LIKE ISNULL(LTRIM(?),ESI.ITEM_NAME)\n" +
                        "       OR ESI.ITEM_SPEC LIKE ISNULL(LTRIM(?),ESI.ITEM_SPEC)\n" +
                        "       OR EFA.ASSETS_DESCRIPTION LIKE ISNULL(LTRIM(?),EFA.ASSETS_DESCRIPTION)\n" +
                        "       OR EFA.MODEL_NUMBER LIKE ISNULL(LTRIM(?),EFA.MODEL_NUMBER)\n" +
                        "       OR SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) LIKE ISNULL(LTRIM(?),SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6)))\n" +
                        "   AND ACCT.ORGANIZATION_ID = EFA.ORGANIZATION_ID\n" +
                        "   AND EII.ORGANIZATION_ID = ?";
                sqlArgs.add(dto.getFromBarcode());
                sqlArgs.add(dto.getToBarcode());
                
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
                sqlArgs.add(dto.getOrganizationId());
                
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
                sqlArgs.add(dto.getCheckLocationName());
                sqlArgs.add(dto.getCheckLocationName());
                sqlArgs.add(dto.getCheckLocationName());
                sqlArgs.add(dto.getCheckLocationName());
                sqlArgs.add(dto.getCheckLocationName());
                sqlArgs.add(dto.getCheckLocationName());
                sqlArgs.add(dto.getCheckLocationName());
                sqlArgs.add(dto.getCheckLocationName());
                sqlArgs.add(dto.getCheckLocationName());
                sqlArgs.add(dto.getCheckLocationName());
                sqlArgs.add(dto.getCheckLocationName());
                sqlArgs.add(dto.getCheckLocationName());
                sqlArgs.add(dto.getOrganizationId());

            }
        } catch (CalendarException ex) {
                ex.printLog();
                throw new SQLModelException(ex);
        }
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：获取盘点统计报表SQL
     *
     * @return SQLModel
     * @throws SQLModelException
     */
    public SQLModel getExportModel() throws SQLModelException {
        SQLModel sqlModel = null;
        try {
            AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
            String analyseType = dto.getAnalyseType();
            if (analyseType.equals(AssetsDictConstant.CHECK_RESULT_1)) { //已盘MIS清单
                sqlModel = resultModelOne();
//			} else if (analyseType.equals(AssetsDictConstant.CHECK_RESULT_2)) { //条码一致：存在其他不一致情况
//				sqlModel = getResultModelTwo();--唐
            } else if (analyseType.equals(AssetsDictConstant.CHECK_RESULT_3)) { //未盘MIS清单
                sqlModel = resultModelThree();
            } else if (analyseType.equals(AssetsDictConstant.CHECK_RESULT_4)) { //有物无卡
                sqlModel = resultModelFour();
            } else if (analyseType.equals(AssetsDictConstant.CHECK_RESULT_5)) { //对于已盘点地点的资产统计有卡无物
                sqlModel = resultModelFive();
            } else if (analyseType.equals(AssetsDictConstant.CHECK_RESULT_6)) {  //无需PDA扫描清单
                sqlModel = resultModelSix();
            }
        } catch (CalendarException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
        }
        return sqlModel;
    }

    /**
     * 功能：获取盘点统计报表SQL：资产属性有差异
     *
     * @return SQLModel
     * @throws CalendarException
     */
    private SQLModel getResultModelTwo() throws CalendarException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
        String sqlStr =
                "SELECT EII.BARCODE,\n" +
                        "       EFA.TAG_NUMBER,\n" +
                        "       ESI.ITEM_NAME,\n" +
                        "       EFA.ASSETS_DESCRIPTION,\n" +
                        "       ESI.ITEM_SPEC,\n" +
                        "       EFA.MODEL_NUMBER,\n" +
                        "       EO.WORKORDER_OBJECT_CODE,\n" +
                        "       EFA.ASSETS_LOCATION_CODE,\n" +
                        "       EO.WORKORDER_OBJECT_NAME,\n" +
                        "       EFA.ASSETS_LOCATION,\n" +
                        "       AME.EMPLOYEE_NUMBER,\n" +
                        "       EFA.ASSIGNED_TO_NUMBER,\n" +
                        "       AME.USER_NAME,\n" +
                        "       EFA.ASSIGNED_TO_NAME,\n" +
                        "       AMD.DEPT_NAME,\n" +
                        "       DECODE(EII.BARCODE, EFA.TAG_NUMBER, '', '资产标签;') ||\n" +
                        "       DECODE(ESI.ITEM_NAME, EFA.ASSETS_DESCRIPTION, '', '资产名称;') ||\n" +
                        "       DECODE(ESI.ITEM_SPEC, EFA.MODEL_NUMBER, '', '资产型号;') ||\n" +
                        "       DECODE(AME.EMPLOYEE_NUMBER, EFA.ASSIGNED_TO_NUMBER, '', '责任人;') ||\n" +
                        "       DECODE(EO.WORKORDER_OBJECT_CODE,\n" +
                        "              EFA.ASSETS_LOCATION_CODE,\n" +
                        "              '',\n" +
                        "              '资产地点;') ||'不一致' CHANGED_CONTENT\n" +
                        "FROM   ETS_SYSTEM_ITEM    ESI,\n" +
                        "       ETS_ITEM_INFO      EII,\n" +
                        "       AMS_OBJECT_ADDRESS AOA,\n" +
                        "       ETS_OBJECT         EO,\n" +
                        "       AMS_MIS_EMPLOYEE   AME,\n" +
                        "       AMS_MIS_DEPT       AMD,\n";
                        if (userAccount.getIsTd().equals("N")) {
                            sqlStr += " ETS_FA_ASSETS EFA\n";
                        } else {
                            sqlStr += " ETS_FA_ASSETS_TD EFA\n";
                        }
//                        "       ETS_FA_ASSETS      EFA\n" +
                        sqlStr += "WHERE  ESI.ITEM_CODE = EII.ITEM_CODE\n" +
                        "       AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                        "       AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                        "       AND EII.RESPONSIBILITY_USER = AME.EMPLOYEE_ID\n" +
                        "       AND EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE\n" +
                        "       AND EII.BARCODE = EFA.TAG_NUMBER\n" +
                        "       AND (ESI.ITEM_NAME <> EFA.ASSETS_DESCRIPTION OR\n" +
                        "       ISNULL(ESI.ITEM_SPEC, '1') <> ISNULL(EFA.MODEL_NUMBER, '1') OR\n" +
                        "       AME.EMPLOYEE_NUMBER <> EFA.ASSIGNED_TO_NUMBER OR\n" +
                        "       EO.WORKORDER_OBJECT_CODE <> EFA.ASSETS_LOCATION_CODE)\n" +
                        "       AND EO.ORGANIZATION_ID = EFA.ORGANIZATION_ID\n" +
                        "       AND EFA.ASSETS_CREATE_DATE <=\n" +
                        "       TO_DATE(TO_CHAR(TO_DATE(?, 'YYYY-MM-DD'), 'YYYY') ||\n" +
                        "                   '-12-31 23:59:59',\n" +
                        "                   'YYYY-MM-DD HH24:MI:SS')\n" +
                        "       AND EFA.ORGANIZATION_ID = ?\n" +
                        "       AND EII.ORGANIZATION_ID = ?\n" +
                        "       AND (EO.WORKORDER_OBJECT_CODE LIKE ISNULL(?, EO.WORKORDER_OBJECT_CODE) OR\n" +
                        "       EO.WORKORDER_OBJECT_NAME LIKE ISNULL(?, EO.WORKORDER_OBJECT_NAME) OR\n" +
                        "       EFA.ASSETS_LOCATION_CODE LIKE ISNULL(?, EFA.ASSETS_LOCATION_CODE) OR\n" +
                        "       EFA.ASSETS_LOCATION LIKE ISNULL(?, EFA.ASSETS_LOCATION))\n" +
                        "       AND AMD.DEPT_CODE = ISNULL(?, AMD.DEPT_CODE)\n" +
                        "       AND EXISTS\n" +
                        " (SELECT NULL\n" +
                        "        FROM   AMS_ASSETS_CHECK_LINE   AACL,\n" +
                        "               AMS_ASSETS_CHECK_HEADER AACH\n" +
                        "        WHERE  EII.BARCODE = AACL.BARCODE\n" +
                        "               AND AACL.HEADER_ID = AACH.HEADER_ID\n" +
                        "               AND (AACH.UPLOAD_DATE <=\n" +
                        "               TO_DATE(TO_CHAR(TO_DATE(?, 'YYYY-MM-DD'), 'YYYY') ||\n" +
                        "                            '-12-31 23:59:59',\n" +
                        "                            'YYYY-MM-DD HH24:MI:SS') OR\n" +
                        "               AACH.ARCHIVED_DATE <=\n" +
                        "               TO_DATE(TO_CHAR(TO_DATE(?, 'YYYY-MM-DD'), 'YYYY') ||\n" +
                        "                            '-12-31 23:59:59',\n" +
                        "                            'YYYY-MM-DD HH24:MI:SS')))";
        sqlArgs.add(dto.getEndDate().getCalendarValue());
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getCheckLocationName());
        sqlArgs.add(dto.getCheckLocationName());
        sqlArgs.add(dto.getCheckLocationName());
        sqlArgs.add(dto.getCheckLocationName());
        sqlArgs.add(dto.getCheckDept());
        sqlArgs.add(dto.getEndDate().getCalendarValue());
        sqlArgs.add(dto.getEndDate().getCalendarValue());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    //未盘MIS清单
    private SQLModel resultModelThree() throws CalendarException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
        String disabled = dto.getDisabled();
        String sqlStr = "";
        if (!disabled.equals("")) { //市用
            sqlStr ="SELECT EFA.MIS_TAG_NUMBER TAG_NUMBER,\n" +
                    "       dbo.AMP_GET_SPEC_DEPT_NAME(EFA.TAG_NUMBER) DEPT_NAME,\n" +
                    "       NULL ACTUAL_QTY,\n" +
                    "       EFA.FA_CATEGORY1,\n" +
                    "       EFA.FA_CATEGORY2,\n" +
                    "       EFA.ASSETS_DESCRIPTION,\n" +
                    "       EFA.MODEL_NUMBER,\n" +
                    "       EFA.CURRENT_UNITS,\n" +
                    "       EFA.UNIT_OF_MEASURE,\n" +
                    "       EFA.ASSETS_LOCATION_CODE,\n" +
                    "       EFA.ASSETS_LOCATION,\n" +
                    "       EFA.ASSIGNED_TO_NUMBER,\n" +
                    "       EFA.ASSIGNED_TO_NAME,\n" +
                    "       EFA.ASSETS_CREATE_DATE,\n" +
                    "       EFA.DATE_PLACED_IN_SERVICE,\n" +
                    "       EFA.ORIGINAL_COST,\n" +
                    "       EFA.COST,\n" +
                    "       EFA.DEPRN_COST,\n" +
                    "       EFA.IMPAIR_RESERVE,\n" +
                    "       EFA.SCRAP_VALUE,\n" +
                    "       EFA.DEPRECIATION_ACCOUNT,\n" +
                    "       EFA.MIS_PROJECT_NUMBER,\n" +
                    "       EFA.PROJECT_NAME\n";
                    if (userAccount.getIsTd().equals("N")) {
                        sqlStr += " FROM ETS_FA_ASSETS EFA\n";
                    } else {
                        sqlStr += " FROM ETS_FA_ASSETS_TD EFA\n";
                    }
//                    "  FROM ETS_FA_ASSETS EFA\n" +
                    sqlStr += 
                    " WHERE EFA.BOOK_TYPE_CODE LIKE '%FA%'\n" +
                    "   AND (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)\n" +
                    "   AND EFA.ASSETS_CREATE_DATE >= ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                    "   AND EFA.ASSETS_CREATE_DATE <= ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                    "   AND EFA.TAG_NUMBER >= ISNULL(LTRIM(?), EFA.TAG_NUMBER)\n" +
                    "   AND EFA.TAG_NUMBER <= ISNULL(LTRIM(?), EFA.TAG_NUMBER)\n" +
                    "   AND (EFA.ASSETS_LOCATION_CODE LIKE ISNULL(LTRIM(?), EFA.ASSETS_LOCATION_CODE) OR\n" +
                    "       EFA.ASSETS_LOCATION LIKE ISNULL(LTRIM(?), EFA.ASSETS_LOCATION)\n" +
                    "       OR EFA.ASSIGNED_TO_NAME LIKE ISNULL(LTRIM(?),EFA.ASSIGNED_TO_NAME)" +
                    "       OR EFA.ASSETS_DESCRIPTION LIKE ISNULL(LTRIM(?),EFA.ASSETS_DESCRIPTION)" +
                    "       OR EFA.MODEL_NUMBER LIKE ISNULL(LTRIM(?),EFA.MODEL_NUMBER)\n" +
                    "       OR EFA.MIS_TAG_NUMBER LIKE ISNULL(LTRIM(?),EFA.MIS_TAG_NUMBER)\n" +
                    "       OR SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) LIKE\n" +
                    "       ISNULL(LTRIM(?), SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6)))\n" +
                    "   AND EXISTS \n" +
                    "		(SELECT NULL\n" +
                    "          FROM AMS_ITEM_CATEGORY_MAP AICM\n" +
                    "         WHERE EFA.SEGMENT1 || '.' || EFA.SEGMENT2 || '.' || EFA.SEGMENT3 = AICM.FA_CATEGORY_CODE\n" +
                    "           AND AICM.NEED_SCAN = 'Y')\n" +
                    "   AND EXISTS\n" +
                    " 		(SELECT NULL\n" +
                    "          FROM ETS_COUNTY ACCT\n" +
                    "         WHERE SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) =\n" +
                    "               ACCT.COUNTY_CODE_MIS)\n" +
                    "   AND SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) =\n" +
                    "       ISNULL(LTRIM(?), SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6))\n" +
                    "   AND NOT EXISTS\n" +
                    " 		(SELECT NULL\n" +
                    "          FROM AMS_ASSETS_CHECK_HEADER AACH,\n" +
                    "               AMS_ASSETS_CHECK_LINE   AACL\n" +
                    "         WHERE AACH.HEADER_ID = AACL.HEADER_ID\n" +
                    "           AND AACH.ORDER_STATUS = 'ARCHIEVED'\n" +
                    "           AND AACH.ARCHIVED_DATE >= ISNULL(?, AACH.ARCHIVED_DATE)\n" +
                    "           AND AACH.ARCHIVED_DATE <= ISNULL(?, AACH.ARCHIVED_DATE)\n" +
                    "           AND ((AACL.ARCHIVE_STATUS = '0' AND AACL.SCAN_STATUS = 'Y') OR\n" +
                    "                (AACL.ARCHIVE_STATUS = '1' AND AACL.SYSTEM_STATUS = 'Y'))\n" +
                    "           AND AACH.ORGANIZATION_ID = ?\n" +
                    "           AND EFA.TAG_NUMBER = AACL.BARCODE)\n" +
                    "   AND EFA.ORGANIZATION_ID = ? \n" +
                    "UNION ALL\n" +
                    "SELECT EFA.MIS_TAG_NUMBER TAG_NUMBER,\n" +
                    "       dbo.AMP_GET_SPEC_DEPT_NAME(EFA.TAG_NUMBER) DEPT_NAME,\n" +
                    "       EII.ACTUAL_QTY,\n" +
                    "       EFA.FA_CATEGORY1,\n" +
                    "       EFA.FA_CATEGORY2,\n" +
                    "       EFA.ASSETS_DESCRIPTION,\n" +
                    "       EFA.MODEL_NUMBER,\n" +
                    "       EFA.CURRENT_UNITS,\n" +
                    "       EFA.UNIT_OF_MEASURE,\n" +
                    "       EFA.ASSETS_LOCATION_CODE,\n" +
                    "       EFA.ASSETS_LOCATION,\n" +
                    "       EFA.ASSIGNED_TO_NUMBER,\n" +
                    "       EFA.ASSIGNED_TO_NAME,\n" +
                    "       EFA.ASSETS_CREATE_DATE,\n" +
                    "       EFA.DATE_PLACED_IN_SERVICE,\n" +
                    "       EFA.ORIGINAL_COST,\n" +
                    "       EFA.COST,\n" +
                    "       EFA.DEPRN_COST,\n" +
                    "       EFA.IMPAIR_RESERVE,\n" +
                    "       EFA.SCRAP_VALUE,\n" +
                    "       EFA.DEPRECIATION_ACCOUNT,\n" +
                    "       EFA.MIS_PROJECT_NUMBER,\n" +
                    "       EFA.PROJECT_NAME\n";
                    if (userAccount.getIsTd().equals("N")) {
                        sqlStr += " FROM ETS_FA_ASSETS EFA,\n";
                    } else {
                        sqlStr += " FROM ETS_FA_ASSETS_TD EFA,\n";
                    }
//                    "  FROM ETS_FA_ASSETS       EFA,\n" +
                    sqlStr += 
                    "       ETS_ITEM_INFO       EII,\n" +
                    "       ETS_OBJECT          EO,\n" +
                    "       AMS_OBJECT_ADDRESS  AOA\n" +
                    " WHERE EII.ORGANIZATION_ID = EFA.ORGANIZATION_ID\n" +
                    "   AND EII.BARCODE = EFA.TAG_NUMBER\n" +
                    "   AND EO.WORKORDER_OBJECT_NO = AOA.OBJECT_NO\n" +
                    "   AND AOA.ADDRESS_ID = EII.ADDRESS_ID\n" +
                    "   AND EXISTS\n" +
                    " 				(SELECT NULL\n" +
                    "                  FROM AMS_ASSETS_CHECK_HEADER AACH,\n" +
                    "                       AMS_ASSETS_CHECK_LINE   AACL\n" +
                    "                 WHERE AACH.HEADER_ID = AACL.HEADER_ID\n" +
                    "                   AND AACH.ORDER_STATUS = 'ARCHIEVED'\n" +
                    "                   AND AACH.ARCHIVED_DATE >= ISNULL(?, AACH.ARCHIVED_DATE)\n" +
                    "                   AND AACH.ARCHIVED_DATE <= ISNULL(?, AACH.ARCHIVED_DATE)\n" +
                    "                   AND ((AACL.ARCHIVE_STATUS = '0' AND AACL.SCAN_STATUS = 'Y') OR\n" +
                    "                        (AACL.ARCHIVE_STATUS = '1' AND AACL.SYSTEM_STATUS = 'Y'))\n" +
                    "                   AND AACH.ORGANIZATION_ID =  EII.ORGANIZATION_ID\n" +
                    "         			AND EII.BARCODE = AACL.BARCODE)\n" +
                    "   AND EFA.BOOK_TYPE_CODE LIKE '%FA%'\n" +
                    "   AND (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)\n" +
                    "   AND EXISTS\n" +
                    " 		(SELECT NULL\n" +
                    "          FROM ETS_COUNTY ACCT\n" +
                    "         WHERE SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) =\n" +
                    "               ACCT.COUNTY_CODE_MIS)\n" +
                    "   AND EXISTS\n" +
                    " 		(SELECT NULL\n" +
                    "          FROM AMS_ITEM_CATEGORY_MAP AICM\n" +
                    "         WHERE EFA.SEGMENT1 || '.' || EFA.SEGMENT2 || '.' || EFA.SEGMENT3 = AICM.FA_CATEGORY_CODE\n" +
                    "           AND AICM.NEED_SCAN = 'Y')\n" +
                    "   AND EFA.ASSETS_CREATE_DATE >= ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                    "   AND EFA.ASSETS_CREATE_DATE <= ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                    "   AND EFA.TAG_NUMBER >= ISNULL(LTRIM(?), EFA.TAG_NUMBER)\n" +
                    "   AND EFA.TAG_NUMBER <= ISNULL(LTRIM(?), EFA.TAG_NUMBER)\n" +
                    "   AND SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) =\n" +
                    "       ISNULL(LTRIM(?), SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6))\n" +
                    "   AND (EFA.ASSETS_LOCATION_CODE LIKE ISNULL(LTRIM(?), EFA.ASSETS_LOCATION_CODE) OR\n" +
                    "       EFA.ASSETS_LOCATION LIKE ISNULL(LTRIM(?), EFA.ASSETS_LOCATION)\n" +
                    "       OR EFA.ASSIGNED_TO_NAME LIKE ISNULL(LTRIM(?),EFA.ASSIGNED_TO_NAME)" +
                    "       OR EFA.ASSETS_DESCRIPTION LIKE ISNULL(LTRIM(?),EFA.ASSETS_DESCRIPTION)" +
                    "       OR EFA.MODEL_NUMBER LIKE ISNULL(LTRIM(?),EFA.MODEL_NUMBER)\n" +
                    "       OR EFA.MIS_TAG_NUMBER LIKE ISNULL(LTRIM(?),EFA.MIS_TAG_NUMBER)\n" +
                    "       OR SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) LIKE\n" +
                    "       ISNULL(LTRIM(?), SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6)))\n" +
                    "   AND EFA.ORGANIZATION_ID = ? \n" +
                    "   AND EO.OBJECT_CATEGORY = '74' \n";
                    
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
            sqlArgs.add(dto.getCheckLocationName());
            sqlArgs.add(dto.getCheckLocationName());
            sqlArgs.add(dto.getCheckLocationName());
            sqlArgs.add(dto.getCheckLocationName());
            sqlArgs.add(dto.getCheckLocationName());
            sqlArgs.add(dto.getCheckLocationName());
            sqlArgs.add(dto.getCheckLocationName());
            sqlArgs.add(dto.getCostCode());
            
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
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getOrganizationId());
            
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
            sqlArgs.add(dto.getFromBarcode());
            sqlArgs.add(dto.getToBarcode());
            sqlArgs.add(dto.getCostCode());
            sqlArgs.add(dto.getCheckLocationName());
            sqlArgs.add(dto.getCheckLocationName());
            sqlArgs.add(dto.getCheckLocationName());
            sqlArgs.add(dto.getCheckLocationName());
            sqlArgs.add(dto.getCheckLocationName());
            sqlArgs.add(dto.getCheckLocationName());
            sqlArgs.add(dto.getCheckLocationName());
            sqlArgs.add(dto.getOrganizationId());
        } else { //省用
            sqlStr ="SELECT EFA.MIS_TAG_NUMBER TAG_NUMBER,\n" +
                    "       dbo.AMP_GET_SPEC_DEPT_NAME(EFA.TAG_NUMBER) DEPT_NAME,\n" +
                    "       0 ACTUAL_QTY,\n" +
                    "       EFA.FA_CATEGORY1,\n" +
                    "       EFA.FA_CATEGORY2,\n" +
                    "       EFA.ASSETS_DESCRIPTION,\n" +
                    "       EFA.MODEL_NUMBER,\n" +
                    "       EFA.CURRENT_UNITS,\n" +
                    "       EFA.UNIT_OF_MEASURE,\n" +
                    "       EFA.ASSETS_LOCATION_CODE,\n" +
                    "       EFA.ASSETS_LOCATION,\n" +
                    "       EFA.ASSIGNED_TO_NUMBER,\n" +
                    "       EFA.ASSIGNED_TO_NAME,\n" +
                    "       EFA.ASSETS_CREATE_DATE,\n" +
                    "       EFA.DATE_PLACED_IN_SERVICE,\n" +
                    "       EFA.ORIGINAL_COST,\n" +
                    "       EFA.COST,\n" +
                    "       EFA.DEPRN_COST,\n" +
                    "       EFA.IMPAIR_RESERVE,\n" +
                    "       EFA.SCRAP_VALUE,\n" +
                    "       EFA.DEPRECIATION_ACCOUNT,\n" +
                    "       EFA.MIS_PROJECT_NUMBER,\n" +
                    "       EFA.PROJECT_NAME\n";
                    if (userAccount.getIsTd().equals("N")) {
                        sqlStr += " FROM ETS_FA_ASSETS EFA\n";
                    } else {
                        sqlStr += " FROM ETS_FA_ASSETS_TD EFA\n";
                    }
//                    "  FROM ETS_FA_ASSETS EFA\n" +
                    sqlStr += 
                    " WHERE (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)\n" +
                    "   AND EFA.ASSETS_CREATE_DATE >= ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                    "   AND EFA.ASSETS_CREATE_DATE <= ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                    "   AND EFA.TAG_NUMBER >= ISNULL(LTRIM(?), EFA.TAG_NUMBER)\n" +
                    "   AND EFA.TAG_NUMBER <= ISNULL(LTRIM(?), EFA.TAG_NUMBER)\n" +
                    "   AND EFA.BOOK_TYPE_CODE LIKE '%FA%'\n" +
                    "   AND EXISTS\n" +
                    " 		(SELECT NULL\n" +
                    "          FROM AMS_ITEM_CATEGORY_MAP AICM\n" +
                    "         WHERE EFA.SEGMENT1 || '.' || EFA.SEGMENT2 || '.' || EFA.SEGMENT3 = AICM.FA_CATEGORY_CODE\n" +
                    "           AND AICM.NEED_SCAN = 'Y')\n" +
                    "   AND EXISTS\n" +
                    " 		(SELECT NULL\n" +
                    "          FROM ETS_COUNTY ACCT\n" +
                    "         WHERE SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) =\n" +
                    "               ACCT.COUNTY_CODE_MIS)\n" +
                    "   AND EFA.ORGANIZATION_ID = ?\n" +
                    "   AND (EFA.ASSETS_LOCATION_CODE LIKE ISNULL(LTRIM(?), EFA.ASSETS_LOCATION_CODE) OR\n" +
                    "       EFA.ASSETS_LOCATION LIKE ISNULL(LTRIM(?), EFA.ASSETS_LOCATION)" +
                    "       OR EFA.ASSIGNED_TO_NAME LIKE ISNULL(LTRIM(?), EFA.ASSIGNED_TO_NAME)" +
                    "       OR EFA.ASSETS_DESCRIPTION LIKE ISNULL(LTRIM(?), EFA.ASSETS_DESCRIPTION)" +
                    "       OR EFA.MODEL_NUMBER LIKE ISNULL(LTRIM(?), EFA.MODEL_NUMBER)\n" +
                    "       OR EFA.MIS_TAG_NUMBER LIKE ISNULL(LTRIM(?), EFA.MIS_TAG_NUMBER)\n" +
                    "       OR SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) LIKE\n" +
                    "       ISNULL(LTRIM(?), SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6)))\n" +
                    "   AND NOT EXISTS\n" +
                    " 		(SELECT NULL\n" +
                    "          FROM ETS_ITEM_INFO EII\n" +
                    "         WHERE EII.ORGANIZATION_ID = EFA.ORGANIZATION_ID\n" +
                    "           AND EII.BARCODE = EFA.TAG_NUMBER\n" +
                    "           AND EXISTS\n" +
                    //"         		(SELECT DISTINCT AACH.ORGANIZATION_ID, AACL.BARCODE\n" +
                    "         		(SELECT DISTINCT AACL.BARCODE\n" +
                    "                  FROM AMS_ASSETS_CHECK_HEADER AACH,\n" +
                    "                       AMS_ASSETS_CHECK_LINE   AACL\n" +
                    "                 WHERE AACH.HEADER_ID = AACL.HEADER_ID\n" +
                    "                   AND AACH.ORDER_STATUS = 'ARCHIEVED'\n" +
                    "                   AND AACH.ARCHIVED_DATE >= ISNULL(?, AACH.ARCHIVED_DATE)\n" +
                    "                   AND AACH.ARCHIVED_DATE <= ISNULL(?, AACH.ARCHIVED_DATE)\n" +
                    "                   AND ((AACL.ARCHIVE_STATUS = '0' AND AACL.SCAN_STATUS = 'Y') OR\n" +
                    "                        (AACL.ARCHIVE_STATUS = '1' AND AACL.SYSTEM_STATUS = 'Y'))\n" +
                    "                   AND AACL.BARCODE = EII.BARCODE \n" +
                    "                   AND AACH.ORGANIZATION_ID = ?)) \n" +
                    " UNION ALL\n" +
                    "SELECT EFA.MIS_TAG_NUMBER TAG_NUMBER,\n" +
                    "       dbo.AMP_GET_SPEC_DEPT_NAME(EFA.TAG_NUMBER) DEPT_NAME,\n" +
                    "       EII.ACTUAL_QTY,\n" +
                    "       EFA.FA_CATEGORY1,\n" +
                    "       EFA.FA_CATEGORY2,\n" +
                    "       EFA.ASSETS_DESCRIPTION,\n" +
                    "       EFA.MODEL_NUMBER,\n" +
                    "       EFA.CURRENT_UNITS,\n" +
                    "       EFA.UNIT_OF_MEASURE,\n" +
                    "       EFA.ASSETS_LOCATION_CODE,\n" +
                    "       EFA.ASSETS_LOCATION,\n" +
                    "       EFA.ASSIGNED_TO_NUMBER,\n" +
                    "       EFA.ASSIGNED_TO_NAME,\n" +
                    "       EFA.ASSETS_CREATE_DATE,\n" +
                    "       EFA.DATE_PLACED_IN_SERVICE,\n" +
                    "       EFA.ORIGINAL_COST,\n" +
                    "       EFA.COST,\n" +
                    "       EFA.DEPRN_COST,\n" +
                    "       EFA.IMPAIR_RESERVE,\n" +
                    "       EFA.SCRAP_VALUE,\n" +
                    "       EFA.DEPRECIATION_ACCOUNT,\n" +
                    "       EFA.MIS_PROJECT_NUMBER,\n" +
                    "       EFA.PROJECT_NAME\n";
                    if (userAccount.getIsTd().equals("N")) {
                        sqlStr += " FROM ETS_FA_ASSETS EFA,\n";
                    } else {
                        sqlStr += " FROM ETS_FA_ASSETS_TD EFA,\n";
                    }
//                    "  FROM ETS_FA_ASSETS       EFA,\n" +
                    sqlStr += 
                    "       ETS_ITEM_INFO       EII,\n" +
                    "       ETS_SYSTEM_ITEM     ESI,\n" +
                    "       ETS_OBJECT          EO,\n" +
                    "       AMS_OBJECT_ADDRESS  AOA,\n" +
                    "       ETS_COUNTY          ACCT\n" +
                    " WHERE EII.ITEM_CODE *= ESI.ITEM_CODE \n" +
                    "   AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                    "   AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                    "   AND EII.ORGANIZATION_ID = EFA.ORGANIZATION_ID\n" +
                    "   AND EII.BARCODE = EFA.TAG_NUMBER\n" +
                    //"   AND SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) *= ACCT.COUNTY_CODE_MIS \n" +
                    "   AND SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) = ACCT.COUNTY_CODE_MIS \n" +
                    "   AND (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)\n" +
                    "   AND EFA.BOOK_TYPE_CODE LIKE '%FA%'\n" +
                    "   AND EXISTS \n" +
                    "		(SELECT NULL\n" +
                    "          FROM AMS_ITEM_CATEGORY_MAP AICM\n" +
                    "         WHERE EFA.SEGMENT1 || '.' || EFA.SEGMENT2 || '.' || EFA.SEGMENT3 = AICM.FA_CATEGORY_CODE\n" +
                    "           AND AICM.NEED_SCAN = 'Y')\n" +
                    "   AND EXISTS\n" +
                    " 		(SELECT NULL\n" +
                    "          FROM ETS_COUNTY ACCT\n" +
                    "         WHERE SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) =\n" +
                    "               ACCT.COUNTY_CODE_MIS)\n" +
                    "   AND EXISTS\n" +
                    " 		(SELECT NULL\n" +
                    "          FROM (SELECT DISTINCT AACH.ORGANIZATION_ID, AACL.BARCODE\n" +
                    //"          FROM (SELECT DISTINCT AACL.BARCODE\n" +
                    "                  FROM AMS_ASSETS_CHECK_HEADER AACH,\n" +
                    "                       AMS_ASSETS_CHECK_LINE   AACL\n" +
                    "                 WHERE AACH.HEADER_ID = AACL.HEADER_ID\n" +
                    "                   AND AACH.ORDER_STATUS = 'ARCHIEVED'\n" +
                    "                   AND AACH.ARCHIVED_DATE >= ISNULL(?, AACH.ARCHIVED_DATE)\n" +
                    "                   AND AACH.ARCHIVED_DATE <= ISNULL(?, AACH.ARCHIVED_DATE)\n" +
                    "                   AND ((AACL.ARCHIVE_STATUS = '0' AND AACL.SCAN_STATUS = 'Y') OR\n" +
                    "                        (AACL.ARCHIVE_STATUS = '1' AND AACL.SYSTEM_STATUS = 'Y'))\n" +
                    "                   AND AACH.ORGANIZATION_ID = ?) TMP_1\n" +
                    "         WHERE EII.BARCODE = TMP_1.BARCODE)\n" +
                    "   AND ACCT.ORGANIZATION_ID = EFA.ORGANIZATION_ID\n" +
                    "   AND EII.ORGANIZATION_ID = ?\n" +
                    "   AND EFA.ASSETS_CREATE_DATE >= ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                    "   AND EFA.ASSETS_CREATE_DATE <= ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                    "   AND EFA.TAG_NUMBER >= ISNULL(LTRIM(?), EFA.TAG_NUMBER)\n" +
                    "   AND EFA.TAG_NUMBER <= ISNULL(LTRIM(?), EFA.TAG_NUMBER)\n" +
                    "   AND (EFA.ASSETS_LOCATION_CODE LIKE ISNULL(LTRIM(?), EFA.ASSETS_LOCATION_CODE) OR\n" +
                    "       EFA.ASSETS_LOCATION LIKE ISNULL(LTRIM(?), EFA.ASSETS_LOCATION)" +
                    "       OR EFA.ASSIGNED_TO_NAME LIKE ISNULL(LTRIM(?), EFA.ASSIGNED_TO_NAME)" +
                    "       OR EFA.ASSETS_DESCRIPTION LIKE ISNULL(LTRIM(?), EFA.ASSETS_DESCRIPTION)" +
                    "       OR EFA.MODEL_NUMBER LIKE ISNULL(LTRIM(?), EFA.MODEL_NUMBER)\n" +
                    "       OR EFA.MIS_TAG_NUMBER LIKE ISNULL(LTRIM(?), EFA.MIS_TAG_NUMBER)\n" +
                    "       OR SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) LIKE\n" +
                    "       ISNULL(LTRIM(?), SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6)))\n" +
                    "  AND EO.OBJECT_CATEGORY = '74' \n";
                    
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
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getCheckLocationName());
            sqlArgs.add(dto.getCheckLocationName());
            sqlArgs.add(dto.getCheckLocationName());
            sqlArgs.add(dto.getCheckLocationName());
            sqlArgs.add(dto.getCheckLocationName());
            sqlArgs.add(dto.getCheckLocationName());
            sqlArgs.add(dto.getCheckLocationName());
            
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
            sqlArgs.add(dto.getOrganizationId());
            
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
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getOrganizationId());
            
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
            sqlArgs.add(dto.getCheckLocationName());
            sqlArgs.add(dto.getCheckLocationName());
            sqlArgs.add(dto.getCheckLocationName());
            sqlArgs.add(dto.getCheckLocationName());
            sqlArgs.add(dto.getCheckLocationName());
            sqlArgs.add(dto.getCheckLocationName());
            sqlArgs.add(dto.getCheckLocationName());
        }
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    //有物无卡
    private SQLModel resultModelFour() throws CalendarException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
        String disabled = dto.getDisabled();
        String sqlStr = "";
        if (!disabled.equals("")) { //市用
            sqlStr ="SELECT EII.BARCODE,\n" +
                    "       dbo.AMP_GET_DEPT_NAME(EII.SPECIALITY_DEPT) DEPT_NAME,\n" +
                    "       EII.ITEM_QTY,\n" +
                    "       EII.ACTUAL_QTY,\n" +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       EO.WORKORDER_OBJECT_CODE,\n" +
                    "       EO.WORKORDER_OBJECT_NAME,\n" +
                    "       dbo.AMP_GET_EMPLOYEE_NUMBER(EII.RESPONSIBILITY_USER) EMPLOYEE_NUMBER,\n" +
                    "       dbo.AMP_GET_EMPLOYEE_NAME(EII.RESPONSIBILITY_USER) USER_NAME,\n" +
                    "       dbo.AMP_GET_COST_CENTER_NAME(EII.RESPONSIBILITY_DEPT) COST_CENTER_NAME,\n" +
                    "       ACCT.COUNTY_NAME,\n" +
                    "       dbo.AAP_GET_CHK_PROJECT_NUMBER(EII.BARCODE) PROJECT_NUMBER,\n" +
                    "       dbo.AAP_GET_CHK_PROJECT_NAME(EII.BARCODE) PROJECT_NAME\n" +
                    "  FROM ETS_ITEM_INFO       EII,\n" +
                    "       ETS_SYSTEM_ITEM     ESI,\n" +
                    "       AMS_COST_DEPT_MATCH ACDM,\n" +
                    "       ETS_COUNTY          ACCT,\n" +
                    "       ETS_OBJECT          EO,\n" +
                    "       AMS_OBJECT_ADDRESS  AOA\n" +
                    " WHERE EII.ITEM_CODE *= ESI.ITEM_CODE \n" +
                    "   AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                    "   AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                    "   AND ISNULL(EII.RESPONSIBILITY_DEPT,'000') = ACDM.DEPT_CODE\n" +
                    "   AND ACDM.COST_CENTER_CODE = ACCT.COUNTY_CODE_MIS\n" +
                    "   AND (EO.OBJECT_CATEGORY < = '70' OR EO.OBJECT_CATEGORY = '80') \n";
                    if (userAccount.getIsTd().equals("N")) {
                        sqlStr += " AND (EII.FINANCE_PROP = 'UNKNOW' OR EII.FINANCE_PROP = 'ASSETS')\n";
                    } else {
                        sqlStr += " AND (EII.FINANCE_PROP = 'UNKNOW' OR EII.FINANCE_PROP = 'TD_ASSETS')\n";
                    }
                    sqlStr += 
                    "   AND (EO.WORKORDER_OBJECT_CODE LIKE ISNULL(LTRIM(?), EO.WORKORDER_OBJECT_CODE) OR\n" +
                    "        EO.WORKORDER_OBJECT_NAME LIKE ISNULL(LTRIM(?), EO.WORKORDER_OBJECT_NAME)\n" +
                    "        OR EII.BARCODE LIKE ISNULL(LTRIM(?),EII.BARCODE)\n" +
                    "        OR ESI.ITEM_NAME LIKE ISNULL(LTRIM(?),ESI.ITEM_NAME)\n" +
                    "        OR ESI.ITEM_SPEC LIKE ISNULL(LTRIM(?),ESI.ITEM_SPEC)\n" +
                    "        OR dbo.AMP_GET_EMPLOYEE_NAME(EII.RESPONSIBILITY_USER) \n" +
                    "           LIKE ISNULL(LTRIM(?), dbo.AMP_GET_EMPLOYEE_NAME(EII.RESPONSIBILITY_USER)))" +
                    "   AND EXISTS\n" +
                    " 				(SELECT NULL\n" +
                    "                  FROM AMS_ASSETS_CHECK_HEADER AACH,\n" +
                    "                       AMS_ASSETS_CHECK_LINE   AACL\n" +
                    "                 WHERE AACH.HEADER_ID = AACL.HEADER_ID\n" +
                    "                   AND AACH.ORDER_STATUS = 'ARCHIEVED'\n" +
                    "                   AND AACH.ARCHIVED_DATE >= ISNULL(?, AACH.ARCHIVED_DATE)\n" +
                    "                   AND AACH.ARCHIVED_DATE <= ISNULL(?, AACH.ARCHIVED_DATE)\n" +
                    "                   AND ((AACL.ARCHIVE_STATUS = '0' AND AACL.SCAN_STATUS = 'Y') OR\n" +
                    "                        (AACL.ARCHIVE_STATUS = '1' AND AACL.SYSTEM_STATUS = 'Y'))\n" +
                    "                   AND AACH.ORGANIZATION_ID =  EII.ORGANIZATION_ID  \n" +
                    "         			AND EII.BARCODE = AACL.BARCODE)\n" +
                    "   AND NOT EXISTS\n" +
                    " (SELECT NULL\n";
                    if (userAccount.getIsTd().equals("N")) {
                        sqlStr += " FROM ETS_FA_ASSETS EFA\n";
                    } else {
                        sqlStr += " FROM ETS_FA_ASSETS_TD EFA\n";
                    }
//                    "          FROM ETS_FA_ASSETS EFA\n" +
                    sqlStr += 
                    "         WHERE EFA.ORGANIZATION_ID = EII.ORGANIZATION_ID\n" +
                    "           AND EFA.TAG_NUMBER = EII.BARCODE\n" +
                    "           AND (EXISTS\n" +
                    "               (SELECT NULL\n" +
                    "                  FROM AMS_ITEM_CATEGORY_MAP AICM\n" +
                    "                 WHERE EFA.SEGMENT1 || '.' || EFA.SEGMENT2 || '.' || EFA.SEGMENT3 = AICM.FA_CATEGORY_CODE\n" +
                    "                   AND AICM.NEED_SCAN = 'N')\n" +
                    "                OR EFA.IS_RETIREMENTS = 1\n" +
                    "                OR EFA.BOOK_TYPE_CODE NOT LIKE '%FA%'))\n" +
                    "   AND NOT EXISTS\n" +
                    " (SELECT 1\n";
                    if (userAccount.getIsTd().equals("N")) {
                        sqlStr += " FROM ETS_FA_ASSETS EFA\n";
                    } else {
                        sqlStr += " FROM ETS_FA_ASSETS_TD EFA\n";
                    }
//                    "          FROM ETS_FA_ASSETS EFA\n" +
                    sqlStr += 
                    "         WHERE EFA.TAG_NUMBER = EII.BARCODE\n" +
                    "           AND EFA.ORGANIZATION_ID = EII.ORGANIZATION_ID\n" +
                    "           AND EXISTS\n" +
                    "         		(SELECT NULL\n" +
                    "                  FROM AMS_ITEM_CATEGORY_MAP AICM\n" +
                    "                 WHERE AICM.FA_CATEGORY_CODE = EFA.SEGMENT1 || '.' || EFA.SEGMENT2 || '.' || EFA.SEGMENT3\n" +
                    "                   AND AICM.NEED_SCAN = 'Y')\n" +
                    "           AND EFA.BOOK_TYPE_CODE LIKE '%FA%'\n" +
                    "           AND (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)\n" +
                    ")\n" +
                    "   AND ACCT.COUNTY_CODE_MIS = ISNULL(LTRIM(?), ACCT.COUNTY_CODE_MIS)\n" +
                    "   AND EII.BARCODE >= ISNULL(LTRIM(?), EII.BARCODE)\n" +
                    "   AND EII.BARCODE <= ISNULL(LTRIM(?), EII.BARCODE)\n" +
                    "   AND ACCT.ORGANIZATION_ID = EII.ORGANIZATION_ID\n" +
                    "   AND EII.ORGANIZATION_ID = ?";
            sqlArgs.add(dto.getCheckLocationName());
            sqlArgs.add(dto.getCheckLocationName());
            sqlArgs.add(dto.getCheckLocationName());
            sqlArgs.add(dto.getCheckLocationName());
            sqlArgs.add(dto.getCheckLocationName());
            sqlArgs.add(dto.getCheckLocationName());
            
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
            sqlArgs.add(dto.getCostCode());
            sqlArgs.add(dto.getFromBarcode());
            sqlArgs.add(dto.getToBarcode());
            sqlArgs.add(dto.getOrganizationId());
        } else {
            sqlStr ="SELECT EII.BARCODE,\n" +
                    "       dbo.AMP_GET_DEPT_NAME(EII.SPECIALITY_DEPT) DEPT_NAME,\n" +
                    "       EII.ITEM_QTY,\n" +
                    "       EII.ACTUAL_QTY,\n" +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       EO.WORKORDER_OBJECT_CODE,\n" +
                    "       EO.WORKORDER_OBJECT_NAME,\n" +
                    "       dbo.AMP_GET_EMPLOYEE_NUMBER(EII.RESPONSIBILITY_USER) EMPLOYEE_NUMBER,\n" +
                    "       dbo.AMP_GET_EMPLOYEE_NAME(EII.RESPONSIBILITY_USER) USER_NAME,\n" +
                    "       dbo.AMP_GET_COST_CENTER_NAME(EII.RESPONSIBILITY_DEPT) COST_CENTER_NAME,\n" +
                    "       dbo.AAP_GET_CHK_PROJECT_NUMBER(EII.BARCODE) PROJECT_NUMBER,\n" +
                    "       dbo.AAP_GET_CHK_PROJECT_NAME(EII.BARCODE) PROJECT_NAME\n" +
                    "  FROM ETS_ITEM_INFO      EII,\n" +
                    "       ETS_SYSTEM_ITEM    ESI,\n" +
                    "       ETS_OBJECT         EO,\n" +
                    "       AMS_OBJECT_ADDRESS AOA\n" +
                    " WHERE EII.ITEM_CODE *= ESI.ITEM_CODE \n" +
                    "   AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                    "   AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                    "   AND (EO.OBJECT_CATEGORY < = '70' OR EO.OBJECT_CATEGORY = '80') \n";
                    if (userAccount.getIsTd().equals("N")) {
                        sqlStr += " AND (EII.FINANCE_PROP = 'UNKNOW' OR EII.FINANCE_PROP = 'ASSETS')\n";
                    } else {
                        sqlStr += " AND (EII.FINANCE_PROP = 'UNKNOW' OR EII.FINANCE_PROP = 'TD_ASSETS')\n";
                    }
                    sqlStr += 
                    "   AND EII.BARCODE >= ISNULL(LTRIM(?), EII.BARCODE)\n" +
                    "   AND EII.BARCODE <= ISNULL(LTRIM(?), EII.BARCODE)\n" +
                    "   AND EXISTS\n" +
                    " 		(SELECT NULL\n" +
                    "          FROM (SELECT DISTINCT AACH.ORGANIZATION_ID, AACL.BARCODE\n" +
                    "                  FROM AMS_ASSETS_CHECK_HEADER AACH,\n" +
                    "                       AMS_ASSETS_CHECK_LINE   AACL\n" +
                    "                 WHERE AACH.HEADER_ID = AACL.HEADER_ID\n" +
                    "                   AND AACH.ORDER_STATUS = 'ARCHIEVED'\n" +
                    "                   AND AACH.ARCHIVED_DATE >= ISNULL(?, AACH.ARCHIVED_DATE)\n" +
                    "                   AND AACH.ARCHIVED_DATE <= ISNULL(?, AACH.ARCHIVED_DATE)\n" +
                    "                   AND ((AACL.ARCHIVE_STATUS = '0' AND AACL.SCAN_STATUS = 'Y') OR\n" +
                    "                        (AACL.ARCHIVE_STATUS = '1' AND AACL.SYSTEM_STATUS = 'Y'))\n" +
                    "                   AND AACH.ORGANIZATION_ID = ?) TMP_1\n" +
                    "         WHERE TMP_1.BARCODE = EII.BARCODE)\n" +
                    "   AND NOT EXISTS\n" +
                    " (SELECT NULL\n";
                    if (userAccount.getIsTd().equals("N")) {
                        sqlStr += " FROM ETS_FA_ASSETS EFA\n";
                    } else {
                        sqlStr += " FROM ETS_FA_ASSETS_TD EFA\n";
                    }
//                    "          FROM ETS_FA_ASSETS EFA\n" +
                    sqlStr += 
                    "         WHERE EFA.BOOK_TYPE_CODE LIKE '%FA%'\n" +
                    "           AND (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)\n" +
                    "           AND EXISTS\n" +
                    "         		(SELECT NULL\n" +
                    "                  FROM AMS_ITEM_CATEGORY_MAP AICM\n" +
                    "                 WHERE AICM.FA_CATEGORY_CODE = EFA.SEGMENT1 || '.' || EFA.SEGMENT2 || '.' || EFA.SEGMENT3\n" +
                    "                   AND AICM.NEED_SCAN = 'Y')\n" +
                    "           AND EFA.ORGANIZATION_ID = EII.ORGANIZATION_ID\n" +
                    "           AND EII.BARCODE = EFA.TAG_NUMBER)\n" +
                    "   AND NOT EXISTS\n" +
                    " (SELECT NULL\n";
                    if (userAccount.getIsTd().equals("N")) {
                        sqlStr += " FROM ETS_FA_ASSETS EFA\n";
                    } else {
                        sqlStr += " FROM ETS_FA_ASSETS_TD EFA\n";
                    }
//                    "          FROM ETS_FA_ASSETS EFA\n" +
                    sqlStr += 
                    "         WHERE (EFA.IS_RETIREMENTS = 1 OR EFA.BOOK_TYPE_CODE NOT LIKE '%FA%' OR\n" +
                    "               EXISTS (SELECT NULL\n" +
                    "                         FROM AMS_ITEM_CATEGORY_MAP AICM\n" +
                    "                        WHERE AICM.FA_CATEGORY_CODE = EFA.SEGMENT1 || '.' || EFA.SEGMENT2 || '.' || EFA.SEGMENT3\n" +
                    "                          AND AICM.NEED_SCAN = 'N'))\n" +
                    "           AND EFA.ORGANIZATION_ID = EII.ORGANIZATION_ID\n" +
                    "           AND EFA.TAG_NUMBER = EII.BARCODE)\n" +
                    "   AND (EO.WORKORDER_OBJECT_CODE LIKE ISNULL(LTRIM(?), EO.WORKORDER_OBJECT_CODE) OR\n" +
                    "        EO.WORKORDER_OBJECT_NAME LIKE ISNULL(LTRIM(?), EO.WORKORDER_OBJECT_NAME)\n" +
                    "        OR EII.BARCODE LIKE ISNULL(LTRIM(?), EII.BARCODE)\n" +
                    "        OR ESI.ITEM_NAME LIKE ISNULL(LTRIM(?), ESI.ITEM_NAME)\n" +
                    "        OR ESI.ITEM_SPEC LIKE ISNULL(LTRIM(?), ESI.ITEM_SPEC)\n" +
                    "        OR dbo.AMP_GET_COST_CENTER_CODE(EII.RESPONSIBILITY_DEPT) LIKE ISNULL(LTRIM(?), dbo.AMP_GET_COST_CENTER_CODE(EII.RESPONSIBILITY_DEPT))" +
                    "        OR dbo.AMP_GET_EMPLOYEE_NAME(EII.RESPONSIBILITY_USER) \n" +
                    "           LIKE ISNULL(LTRIM(?), dbo.AMP_GET_EMPLOYEE_NAME(EII.RESPONSIBILITY_USER))) \n" +
                    "   AND EII.ORGANIZATION_ID = ? \n";
            sqlArgs.add(dto.getFromBarcode());
            sqlArgs.add(dto.getToBarcode());
            
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
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getCheckLocationName());
            sqlArgs.add(dto.getCheckLocationName());
            sqlArgs.add(dto.getCheckLocationName());
            sqlArgs.add(dto.getCheckLocationName());
            sqlArgs.add(dto.getCheckLocationName());
            sqlArgs.add(dto.getCheckLocationName());
            sqlArgs.add(dto.getCheckLocationName());
            sqlArgs.add(dto.getOrganizationId());
        }
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：获取盘点统计报表SQL：有卡无物(按盘点资产)
     * 对于已盘点地点的资产统计有卡无物
     * @return SQLModel
     * @throws CalendarException
     */
    private SQLModel resultModelFive() throws CalendarException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
        String sqlStr =
                "SELECT EFA.TAG_NUMBER,\n" +
                        "       EFA.FA_CATEGORY1,\n" +
                        "       EFA.FA_CATEGORY2,\n" +
                        "       EFA.ASSETS_DESCRIPTION,\n" +
                        "       EFA.MODEL_NUMBER,\n" +
                        "       EFA.CURRENT_UNITS,\n" +
                        "       EFA.UNIT_OF_MEASURE,\n" +
                        "       EFA.ASSETS_LOCATION_CODE,\n" +
                        "       EFA.ASSETS_LOCATION,\n" +
                        "       EFA.ASSIGNED_TO_NUMBER,\n" +
                        "       EFA.ASSIGNED_TO_NAME,\n" +
                        "       EFA.ASSETS_CREATE_DATE,\n" +
                        "       EFA.DATE_PLACED_IN_SERVICE,\n" +
                        "       EFA.ORIGINAL_COST,\n" +
                        "       EFA.COST,\n" +
                        "       EFA.DEPRN_COST,\n" +
                        "       EFA.IMPAIR_RESERVE,\n" +
                        "       EFA.SCRAP_VALUE,\n" +
                        "       EFA.DEPRECIATION_ACCOUNT,\n" +
                        "       EFA.MIS_PROJECT_NUMBER,\n" +
                        "       EFA.PROJECT_NAME\n";
                        if (userAccount.getIsTd().equals("N")) {
                            sqlStr += " FROM ETS_FA_ASSETS EFA,\n";
                        } else {
                            sqlStr += " FROM ETS_FA_ASSETS_TD EFA,\n";
                        }
//                        "FROM   ETS_FA_ASSETS      EFA,\n" +
                        sqlStr += 
                        "       ETS_ITEM_INFO      EII,\n" +
                        "       AMS_OBJECT_ADDRESS AOA,\n" +
                        "       ETS_OBJECT         EO\n" +
                        "WHERE  EFA.MIS_TAG_NUMBER = EII.BARCODE\n" +
                        "AND    AOA.ADDRESS_ID = EII.ADDRESS_ID\n" +
                        "AND    AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                        "AND    EO.OBJECT_CATEGORY = '74' \n" +
                        "AND    (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)\n" +
                        "AND    NOT EXISTS(SELECT NULL FROM ETS_ITEM_MATCH EIM WHERE EIM.ASSET_ID = EFA.ASSET_ID)\n" +
                        "AND    EFA.ASSETS_CREATE_DATE >= ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                        "AND    EFA.ASSETS_CREATE_DATE <= ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                        "AND    EFA.TAG_NUMBER >= ISNULL(LTRIM(?), EFA.TAG_NUMBER)\n" +
                        "AND 	EFA.TAG_NUMBER <= ISNULL(LTRIM(?), EFA.TAG_NUMBER)\n" +
                        "AND    EFA.ORGANIZATION_ID = ?\n" +
                        "AND    (EFA.ASSETS_LOCATION_CODE LIKE ISNULL(LTRIM(?), EFA.ASSETS_LOCATION_CODE) OR\n" +
                        "      	EFA.ASSETS_LOCATION LIKE ISNULL(LTRIM(?), EFA.ASSETS_LOCATION) OR\n" +
                        "      	EFA.ASSIGNED_TO_NAME LIKE ISNULL(LTRIM(?), EFA.ASSIGNED_TO_NAME) OR\n" +
                        "      	EFA.ASSETS_DESCRIPTION LIKE ISNULL(LTRIM(?), EFA.ASSETS_DESCRIPTION) OR\n" +
                        "      	EFA.MODEL_NUMBER LIKE ISNULL(LTRIM(?), EFA.MODEL_NUMBER) OR\n" +
                        "      	EFA.TAG_NUMBER LIKE ISNULL(LTRIM(?), EFA.TAG_NUMBER) OR\n" +
                        "      	SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) LIKE ISNULL(LTRIM(?), SUBSTRING(EFA.DEPRECIATION_ACCOUNT,6,6)))\n" +
                        "AND    NOT EXISTS \n" +
                        "			  (SELECT NULL\n" +
                        "        		 FROM AMS_ITEM_CATEGORY_MAP AICM\n" +
                        "        		WHERE EFA.SEGMENT1 || '.' || EFA.SEGMENT2 || '.' || EFA.SEGMENT3 = AICM.FA_CATEGORY_CODE\n" +
                        "        		  AND AICM.NEED_SCAN = 'N')\n" +
                        "AND EXISTS\n" +
                        " 		(SELECT NULL\n" +
                        "          FROM ETS_COUNTY ACCT\n" +
                        "         WHERE SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) =\n" +
                        "               ACCT.COUNTY_CODE_MIS)\n" +
                        "AND   SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) LIKE ISNULL(LTRIM(?),SUBSTRING(EFA.DEPRECIATION_ACCOUNT,6,6))";
        
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
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getCheckLocationName());
        sqlArgs.add(dto.getCheckLocationName());
        sqlArgs.add(dto.getCheckLocationName());
        sqlArgs.add(dto.getCheckLocationName());
        sqlArgs.add(dto.getCheckLocationName());
        sqlArgs.add(dto.getCheckLocationName());
        sqlArgs.add(dto.getCheckLocationName());
        sqlArgs.add(dto.getCostCode());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

  //无需PDA扫描清单
    private SQLModel resultModelSix() throws CalendarException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
        String disabled = dto.getDisabled();
        String sqlStr = "";
        if (!disabled.equals("")) { //市用
            sqlStr ="SELECT EFA.MIS_TAG_NUMBER TAG_NUMBER,\n" +
                    "       dbo.AMP_GET_SPEC_DEPT_NAME(EFA.TAG_NUMBER) DEPT_NAME,\n" +
                    "       NULL ACTUAL_QTY,\n" +
                    "       EFA.FA_CATEGORY1,\n" +
                    "       EFA.FA_CATEGORY2,\n" +
                    "       EFA.ASSETS_DESCRIPTION,\n" +
                    "       EFA.MODEL_NUMBER,\n" +
                    "       EFA.CURRENT_UNITS,\n" +
                    "       EFA.UNIT_OF_MEASURE,\n" +
                    "       EFA.ASSETS_LOCATION_CODE,\n" +
                    "       EFA.ASSETS_LOCATION,\n" +
                    "       EFA.ASSIGNED_TO_NUMBER,\n" +
                    "       EFA.ASSIGNED_TO_NAME,\n" +
                    "       EFA.ASSETS_CREATE_DATE,\n" +
                    "       EFA.DATE_PLACED_IN_SERVICE,\n" +
                    "       EFA.ORIGINAL_COST,\n" +
                    "       EFA.COST,\n" +
                    "       EFA.DEPRN_COST,\n" +
                    "       EFA.IMPAIR_RESERVE,\n" +
                    "       EFA.SCRAP_VALUE,\n" +
                    "       EFA.DEPRECIATION_ACCOUNT,\n" +
                    "       EFA.MIS_PROJECT_NUMBER,\n" +
                    "       EFA.PROJECT_NAME\n";
                    if (userAccount.getIsTd().equals("N")) {
                        sqlStr += " FROM ETS_FA_ASSETS EFA\n";
                    } else {
                        sqlStr += " FROM ETS_FA_ASSETS_TD EFA\n";
                    }
//                    "  FROM ETS_FA_ASSETS EFA\n" +
                    sqlStr += 
                    " WHERE EFA.BOOK_TYPE_CODE LIKE '%FA%'\n" +
                    "   AND (EXISTS\n" +
                    "          (SELECT NULL\n" +
                    "             FROM AMS_ITEM_CATEGORY_MAP AICM\n" +
                    "            WHERE AICM.FA_CATEGORY_CODE =\n" +
                    "                  EFA.SEGMENT1 || '.' || EFA.SEGMENT2 || '.' || EFA.SEGMENT3\n" +
                    "              AND AICM.NEW_ASSET_NEED_SCAN = 'N') OR\n" +
                    "           ((SELECT COUNT(1)\n" +
                    "               FROM AMS_NOT_SCAN_ASSETS ANSA\n" +
                    "              WHERE dbo.CHARINDEX_4(ANSA.ASSETS_DESCRIPTION, EFA.ASSETS_DESCRIPTION, 1, 1) > 0) > 0))\n" +
                    "    AND EXISTS\n" +
                    " 		   (SELECT NULL\n" +
                    "             FROM ETS_COUNTY ACCT\n" +
                    "            WHERE SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) =\n" +
                    "                  ACCT.COUNTY_CODE_MIS)\n" +
                    "   AND (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)\n" +
                    "   AND EFA.ASSETS_CREATE_DATE >= ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                    "   AND EFA.ASSETS_CREATE_DATE <= ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                    "   AND EFA.TAG_NUMBER >= ISNULL(LTRIM(?), EFA.TAG_NUMBER)\n" +
                    "   AND EFA.TAG_NUMBER <= ISNULL(LTRIM(?), EFA.TAG_NUMBER)\n" +
                    "   AND (EFA.ASSETS_LOCATION_CODE LIKE ISNULL(LTRIM(?), EFA.ASSETS_LOCATION_CODE) OR\n" +
                    "       EFA.ASSETS_LOCATION LIKE ISNULL(LTRIM(?), EFA.ASSETS_LOCATION)\n" +
                    "       OR EFA.ASSIGNED_TO_NAME LIKE ISNULL(LTRIM(?),EFA.ASSIGNED_TO_NAME)" +
                    "       OR EFA.ASSETS_DESCRIPTION LIKE ISNULL(LTRIM(?),EFA.ASSETS_DESCRIPTION)" +
                    "       OR EFA.MODEL_NUMBER LIKE ISNULL(LTRIM(?),EFA.MODEL_NUMBER)\n" +
                    "       OR EFA.MIS_TAG_NUMBER LIKE ISNULL(LTRIM(?),EFA.MIS_TAG_NUMBER))\n" +
                    "   AND SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) LIKE \n" +
                    "       ISNULL(LTRIM(?), SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6)) \n" +
                    "   AND EFA.ORGANIZATION_ID = ? \n";
                    
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
            sqlArgs.add(dto.getCheckLocationName());
            sqlArgs.add(dto.getCheckLocationName());
            sqlArgs.add(dto.getCheckLocationName());
            sqlArgs.add(dto.getCheckLocationName());
            sqlArgs.add(dto.getCheckLocationName());
            sqlArgs.add(dto.getCheckLocationName());
            sqlArgs.add(dto.getCostCode());
            sqlArgs.add(dto.getOrganizationId());
        } else { //省用
            sqlStr =
                    		"SELECT EFA.MIS_TAG_NUMBER TAG_NUMBER,\n" +
                            "       dbo.AMP_GET_SPEC_DEPT_NAME(EFA.TAG_NUMBER) DEPT_NAME,\n" +
                            "       NULL ACTUAL_QTY,\n" +
                            "       EFA.FA_CATEGORY1,\n" +
                            "       EFA.FA_CATEGORY2,\n" +
                            "       EFA.ASSETS_DESCRIPTION,\n" +
                            "       EFA.MODEL_NUMBER,\n" +
                            "       EFA.CURRENT_UNITS,\n" +
                            "       EFA.UNIT_OF_MEASURE,\n" +
                            "       EFA.ASSETS_LOCATION_CODE,\n" +
                            "       EFA.ASSETS_LOCATION,\n" +
                            "       EFA.ASSIGNED_TO_NUMBER,\n" +
                            "       EFA.ASSIGNED_TO_NAME,\n" +
                            "       EFA.ASSETS_CREATE_DATE,\n" +
                            "       EFA.DATE_PLACED_IN_SERVICE,\n" +
                            "       EFA.ORIGINAL_COST,\n" +
                            "       EFA.COST,\n" +
                            "       EFA.DEPRN_COST,\n" +
                            "       EFA.IMPAIR_RESERVE,\n" +
                            "       EFA.SCRAP_VALUE,\n" +
                            "       EFA.DEPRECIATION_ACCOUNT,\n" +
                            "       EFA.MIS_PROJECT_NUMBER,\n" +
                            "       EFA.PROJECT_NAME\n";
                            if (userAccount.getIsTd().equals("N")) {
                                sqlStr += " FROM ETS_FA_ASSETS EFA\n";
                            } else {
                                sqlStr += " FROM ETS_FA_ASSETS_TD EFA\n";
                            }
//                            "  FROM ETS_FA_ASSETS EFA\n" +
                            sqlStr += 
                            " WHERE  EXISTS\n" +
                            "        (SELECT NULL\n" +
                            "           FROM AMS_ITEM_CATEGORY_MAP AICM\n" +
                            "          WHERE EFA.SEGMENT1 || '.' || EFA.SEGMENT2 || '.' || EFA.SEGMENT3 = AICM.FA_CATEGORY_CODE\n" +
                            "            AND AICM.NEED_SCAN = 'N')\n" +
                            "    AND (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)\n" +
                            "    AND EFA.BOOK_TYPE_CODE  LIKE '%FA%'\n" +
                            "    AND EXISTS\n" +
                            " 		(SELECT NULL\n" +
                            "          FROM ETS_COUNTY ACCT\n" +
                            "         WHERE SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) =\n" +
                            "               ACCT.COUNTY_CODE_MIS)\n" +
                            "   AND EFA.ASSETS_CREATE_DATE >= ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                            "   AND EFA.ASSETS_CREATE_DATE <= ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                            "   AND EFA.TAG_NUMBER >= ISNULL(LTRIM(?), EFA.TAG_NUMBER)\n" +
                            "   AND EFA.TAG_NUMBER <= ISNULL(LTRIM(?), EFA.TAG_NUMBER)\n" +
                            "   AND EFA.ORGANIZATION_ID = ?\n" +
                            "   AND (EFA.ASSETS_LOCATION_CODE LIKE ISNULL(LTRIM(?), EFA.ASSETS_LOCATION_CODE) OR\n" +
                            "       EFA.ASSETS_LOCATION LIKE ISNULL(LTRIM(?), EFA.ASSETS_LOCATION)\n" +
                            "       OR EFA.ASSIGNED_TO_NAME LIKE ISNULL(LTRIM(?),EFA.ASSIGNED_TO_NAME)" +
                            "       OR EFA.ASSETS_DESCRIPTION LIKE ISNULL(LTRIM(?),EFA.ASSETS_DESCRIPTION)" +
                            "       OR EFA.MODEL_NUMBER LIKE ISNULL(LTRIM(?),EFA.MODEL_NUMBER)\n" +
                            "       OR EFA.MIS_TAG_NUMBER LIKE ISNULL(LTRIM(?),EFA.MIS_TAG_NUMBER)\n" +
                            "       OR SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) LIKE\n" +
                            "       ISNULL(LTRIM(?), SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6)))\n";

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
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getCheckLocationName());
            sqlArgs.add(dto.getCheckLocationName());
            sqlArgs.add(dto.getCheckLocationName());
            sqlArgs.add(dto.getCheckLocationName());
            sqlArgs.add(dto.getCheckLocationName());
            sqlArgs.add(dto.getCheckLocationName());
            sqlArgs.add(dto.getCheckLocationName());
        }
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
