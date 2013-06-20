package com.sino.ams.match.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;
import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.system.user.dto.EtsOuCityMapDTO;
import com.sino.ams.system.user.dto.SfUserDTO;


public class TFMatchResultModel extends AMSSQLProducer {
    public TFMatchResultModel(SfUserDTO userAccount, EtsOuCityMapDTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsOuCityMapDTO dto = (EtsOuCityMapDTO) dtoParameter;
        String sqlStr = 
        	    "SELECT EOCM.ORGANIZATION_ID, \n" +
                "       EOCM.COMPANY, \n" +
                "       TOTAL_MIS_COUNT.MIS_COUNT MIS_COUNT, \n" +
                "       ISNULL(TOTAL_MATCH_COUNT.MATCH_COUNT, 0) MATCH_COUNT, \n" +
                "       ISNULL(TOTAL_NO_MATCH_COUNT.NO_MATCH_COUNT, 0) NO_MATCH_COUNT, \n" +
                //"       DECODE(TOTAL_SYN_COUNT.SYN_COUNT, NULL, 0, TOTAL_SYN_COUNT.SYN_COUNT) SYN_COUNT, \n" +
                " 		(CASE TOTAL_SYN_COUNT.SYN_COUNT WHEN NULL THEN 0 ELSE TOTAL_SYN_COUNT.SYN_COUNT END) SYN_COUNT, \n" +
                "       TOTAL_NO_SYN_COUNT.NO_SYN_COUNT, \n" +
                
                //"       (DECODE(TRUNC(100 * TOTAL_MATCH_COUNT.MATCH_COUNT / TOTAL_MIS_COUNT.MIS_COUNT), 0, \n" +
                //"                     REPLACE(ROUND(100 * TOTAL_MATCH_COUNT.MATCH_COUNT / TOTAL_MIS_COUNT.MIS_COUNT, 2), '.', '0.'), \n" +
                //"                     TO_CHAR(ROUND(100 * TOTAL_MATCH_COUNT.MATCH_COUNT / TOTAL_MIS_COUNT.MIS_COUNT, 2))) || '%') MATCH_RATE, \n" +

                "       (CASE dbo.CHARINDEX_4( '.', CONVERT(VARCHAR, ROUND(100.0 * ISNULL(TOTAL_MATCH_COUNT.MATCH_COUNT, 0) / ISNULL(TOTAL_MIS_COUNT.MIS_COUNT, 1), 2)) || '%', 1, 1) \n" +
                "        WHEN 1 THEN\n" +
                "          '0' || CONVERT(VARCHAR, CONVERT(DECIMAL(18,2), ROUND(100.0 * ISNULL(TOTAL_MATCH_COUNT.MATCH_COUNT, 0) / ISNULL(TOTAL_MIS_COUNT.MIS_COUNT, 1), 2))) || '%' \n" +
                "        ELSE\n" +
                "          CONVERT(VARCHAR, CONVERT(DECIMAL(18,2), ROUND(100.0 * ISNULL(TOTAL_MATCH_COUNT.MATCH_COUNT, 0) / ISNULL(TOTAL_MIS_COUNT.MIS_COUNT, 1), 2))) || '%' \n" +
                "        END) MATCH_RATE, \n" +

                //"       (DECODE(TRUNC(100 * TOTAL_SYN_COUNT.SYN_COUNT / TOTAL_MIS_COUNT.MIS_COUNT), 0, \n" +
                //"                     REPLACE(ROUND(100 * TOTAL_SYN_COUNT.SYN_COUNT / TOTAL_MIS_COUNT.MIS_COUNT, 2), '.', '0.'), \n" +
                //"                     TO_CHAR(ROUND(100 * TOTAL_SYN_COUNT.SYN_COUNT / TOTAL_MIS_COUNT.MIS_COUNT, 2))) || '%') SYN_RATE \n" +
                
                "       (CASE dbo.CHARINDEX_4( '.', CONVERT(VARCHAR, ROUND(100.0 * ISNULL(TOTAL_SYN_COUNT.SYN_COUNT, 0) / ISNULL(TOTAL_MIS_COUNT.MIS_COUNT, 1), 2)) || '%', 1, 1) \n" +
                "        WHEN 1 THEN\n" +
                "          '0' || CONVERT(VARCHAR, CONVERT(DECIMAL(18,2), ROUND(100.0 * ISNULL(TOTAL_SYN_COUNT.SYN_COUNT, 0) / ISNULL(TOTAL_MIS_COUNT.MIS_COUNT, 1), 2))) || '%' \n" +
                "        ELSE\n" +
                "          CONVERT(VARCHAR, CONVERT(DECIMAL(18,2), ROUND(100.0 * ISNULL(TOTAL_SYN_COUNT.SYN_COUNT, 0) / ISNULL(TOTAL_MIS_COUNT.MIS_COUNT, 1), 2))) || '%' \n" +
                "        END) SYN_RATE \n" +
                
                "  FROM ETS_OU_CITY_MAP EOCM, \n" +
                " 		(SELECT EFA.ORGANIZATION_ID, \n" +
                "       		COUNT(EFA.ASSET_ID) MIS_COUNT \n" +
                " 		   FROM ETS_FA_ASSETS_TF EFA \n" +
                " 		  WHERE EFA.BOOK_TYPE_CODE LIKE '%FA%' \n" +
                "  			AND EFA.ORGANIZATION_ID = ISNULL(?, EFA.ORGANIZATION_ID) \n" +
                " 		  GROUP BY EFA.ORGANIZATION_ID) TOTAL_MIS_COUNT, \n" +
                " 		(SELECT EFA.ORGANIZATION_ID, \n" +
                "       		COUNT(EFA.ASSET_ID) MATCH_COUNT \n" +
                " 		   FROM ETS_FA_ASSETS_TF EFA, \n" +
                "     			ETS_ITEM_MATCH_TF EIM \n" +
                " 		  WHERE EFA.ASSET_ID = EIM.ASSET_ID \n" +
                "  			AND EFA.ORGANIZATION_ID = ISNULL(?, EFA.ORGANIZATION_ID) \n" +
                " 		  GROUP BY EFA.ORGANIZATION_ID) TOTAL_MATCH_COUNT, \n" +
                " 		(SELECT EFA.ORGANIZATION_ID, \n" +
                "       		COUNT(EFA.ASSET_ID) NO_MATCH_COUNT \n" +
                " 		   FROM ETS_FA_ASSETS_TF EFA \n" +
                " 		  WHERE NOT EXISTS \n" +
                " 				(SELECT 1 FROM ETS_ITEM_MATCH_TF EIM WHERE EFA.ASSET_ID = EIM.ASSET_ID) \n" +
                "  			AND EFA.ORGANIZATION_ID = ISNULL(?, EFA.ORGANIZATION_ID) \n" +
                " 		  GROUP BY EFA.ORGANIZATION_ID) TOTAL_NO_MATCH_COUNT, \n" +
                " 		(SELECT EFA.ORGANIZATION_ID, \n" +
                "       		COUNT(EFA.ASSET_ID) SYN_COUNT \n" +
                " 		   FROM ETS_FA_ASSETS_TF EFA, ETS_ITEM_MATCH_TF EIM \n" +
                " 		  WHERE EFA.ASSET_ID = EIM.ASSET_ID \n" +
                "  			AND EXISTS \n" +
                "     			(SELECT 1 FROM ETS_MISFA_UPDATE_LOG EMUL WHERE EMUL.ASSET_ID = EFA.ASSET_ID) \n" +
                " 			AND EFA.ORGANIZATION_ID = ISNULL(?, EFA.ORGANIZATION_ID) \n" +
                " 		  GROUP BY EFA.ORGANIZATION_ID) TOTAL_SYN_COUNT, \n" +
                " 		(SELECT EFA.ORGANIZATION_ID, \n" +
                "       		COUNT(EFA.ASSET_ID) NO_SYN_COUNT \n" +
                " 		   FROM ETS_FA_ASSETS_TF EFA, ETS_ITEM_MATCH_TF EIM \n" +
                " 		  WHERE EFA.ASSET_ID = EIM.ASSET_ID \n" +
                "  			AND NOT EXISTS \n" +
                "     			(SELECT 1 FROM ETS_MISFA_UPDATE_LOG EMUL WHERE EMUL.ASSET_ID = EFA.ASSET_ID) \n" +
                " 			AND EFA.ORGANIZATION_ID = ISNULL(?, EFA.ORGANIZATION_ID) \n" +
                " 		  GROUP BY EFA.ORGANIZATION_ID) TOTAL_NO_SYN_COUNT \n" +
                "WHERE EOCM.ORGANIZATION_ID = TOTAL_MIS_COUNT.ORGANIZATION_ID \n" +
                "  AND EOCM.ORGANIZATION_ID *= TOTAL_MATCH_COUNT.ORGANIZATION_ID \n" +
                "  AND EOCM.ORGANIZATION_ID *= TOTAL_NO_MATCH_COUNT.ORGANIZATION_ID \n" +
                "  AND EOCM.ORGANIZATION_ID *= TOTAL_SYN_COUNT.ORGANIZATION_ID \n" +
                "  AND EOCM.ORGANIZATION_ID *= TOTAL_NO_SYN_COUNT.ORGANIZATION_ID \n" +
                "  AND EOCM.ORGANIZATION_ID = ISNULL(?, EOCM.ORGANIZATION_ID) \n" +
                "  AND dbo.CHARINDEX_4('TD', EOCM.COMPANY, 1, 1) < 0 \n" ;
        
            if (dto.getOrganizationId() == -1) {
                sqlArgs.add(null);
                sqlArgs.add(null);
                sqlArgs.add(null);
                sqlArgs.add(null);
                sqlArgs.add(null);
                sqlArgs.add(null);
            } else {
                sqlArgs.add(dto.getOrganizationId());
                sqlArgs.add(dto.getOrganizationId());
                sqlArgs.add(dto.getOrganizationId());
                sqlArgs.add(dto.getOrganizationId());
                sqlArgs.add(dto.getOrganizationId());
                sqlArgs.add(dto.getOrganizationId());
            }


        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}

