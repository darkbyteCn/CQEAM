package com.sino.ams.newasset.report.model;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.newasset.report.dto.AssetsInDataReportDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2009-5-16
 * Time: 11:26:09
 * To change this template use File | Settings | File Templates.
 */
public class AssetsInDataReportModel extends AMSSQLProducer {

	public AssetsInDataReportModel(SfUserDTO userAccount, AssetsInDataReportDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

    /**
	 * 功能：管理指标类录入数据报表
	 * @return SQLModel
	 * @throws com.sino.base.exception.SQLModelException
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
	    List sqlArgs = new ArrayList();
        AssetsInDataReportDTO dto = (AssetsInDataReportDTO) dtoParameter;
        String sqlStr = "";
        if (dto.getManagerGuideType().equals("TRUN_RATE")) {
            sqlStr = "SELECT EOCM.COMPANY,\n" +
                    "       AIDR.PERIOD,\n" +
                    "       AIDR.PROJECT_TRUN_ASSETS,\n" +
                    "       AIDR.PROJECT_SUM_ASSETS,\n" +
                    "       STR(CONVERT(FLOAT,AIDR.PROJECT_TRUN_ASSETS)/CONVERT(FLOAT,AIDR.PROJECT_SUM_ASSETS)*100,20,2)||'%' AS ASSETS_RATE\n" +
                    "FROM   AMS_IN_DATA_REPORT AIDR,\n" +
                    "       ETS_OU_CITY_MAP    EOCM\n" +
                    "WHERE  AIDR.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
                    "       AND (? IS NULL OR AIDR.ORGANIZATION_ID = ?)\n" +
                    "       AND (? IS NULL OR AIDR.PERIOD = ?)\n" +
                    "       AND AIDR.REPORT_TYPE = ?";
            if(dto.getOrganizationId()==0||dto.getOrganizationId()==-1){
            	sqlArgs.add(null);
            } else {
            	sqlArgs.add(dto.getOrganizationId());
            }
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getPeriod());
            sqlArgs.add(dto.getPeriod());
            sqlArgs.add(dto.getManagerGuideType());
        } else if (dto.getManagerGuideType().equals("IN_TIME_RATE")) {
            sqlStr = "SELECT AIDR.PERIOD,\n" +
                    "       AIDR.NO_TIMELY_REPORT_NUM,\n" +
                    "       AIDR.ASSETSMENT_REPORT_NUM,\n" +
                    "        STR((1-CONVERT(FLOAT,AIDR.NO_TIMELY_REPORT_NUM)/CONVERT(FLOAT,AIDR.ASSETSMENT_REPORT_NUM))*100,20,2)||'%' AS ASSETS_RATE\n" +
                    "FROM   AMS_IN_DATA_REPORT AIDR\n" +
                    "WHERE  (? IS NULL OR AIDR.PERIOD = ?)\n" +
                    "       AND AIDR.REPORT_TYPE = ?";
            sqlArgs.add(dto.getPeriod());
            sqlArgs.add(dto.getPeriod());
            sqlArgs.add(dto.getManagerGuideType());
        } else if (dto.getManagerGuideType().equals("NICETY_RATE")) {
            sqlStr = "SELECT AIDR.PERIOD,\n" +
                    "       AIDR.ASSETSMENT_FALSE_NUM,\n" +
                    "       AIDR.ASSETSMENT_ASSETS_SUM,\n" +
                    "        CASE B.A WHEN\n" +
                    "              0 THEN\n" +
                    "                STR_REPLACE(ROUND(100 * AIDR.ASSETSMENT_FALSE_NUM /\n" +
                    "                             AIDR.ASSETSMENT_ASSETS_SUM,\n" +
                    "                             2),\n" +
                    "                       '.',\n" +
                    "                       '0.') ELSE\n" +
                    "               CONVERT(VARCHAR,ROUND(100 * AIDR.ASSETSMENT_FALSE_NUM /\n" +
                    "                             AIDR.ASSETSMENT_ASSETS_SUM,\n" +
                    "                              2)) || '%' END ASSETS_RATE\n" +
                    "FROM   AMS_IN_DATA_REPORT AIDR,(SELECT ROUND(100 * AIDR.ASSETSMENT_FALSE_NUM /AIDR.ASSETSMENT_ASSETS_SUM,2) A FROM AMS_IN_DATA_REPORT AIDR) B\n" +
                    "WHERE  (? IS NULL OR AIDR.PERIOD = ?)\n" +
                    "       AND AIDR.REPORT_TYPE = ?";
            sqlArgs.add(dto.getPeriod());
            sqlArgs.add(dto.getPeriod());
            sqlArgs.add(dto.getManagerGuideType());
        } else if (dto.getManagerGuideType().equals("CHECK_RATE")) {
            sqlStr = "SELECT EOCM.COMPANY,\n" +
                    "       AIDR.PERIOD,\n" +
                    "       AIDR.COMPLETE_CHECK_NUM,\n" +
                    "       AIDR.PLAN_CHECK_NUM,\n" +
                   
                    "        STR((1-CONVERT(FLOAT,AIDR.COMPLETE_CHECK_NUM)/CONVERT(FLOAT,AIDR.PLAN_CHECK_NUM))*100,20,2)||'%' AS ASSETS_RATE\n" +
                    "FROM   AMS_IN_DATA_REPORT AIDR,\n" +
                    "       ETS_OU_CITY_MAP    EOCM\n" +
                    "WHERE  AIDR.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
                    "       AND (? IS NULL OR AIDR.ORGANIZATION_ID = ?)\n" +
                    "       AND (? IS NULL OR AIDR.PERIOD = ?)\n" +
                    "       AND AIDR.REPORT_TYPE = ?";
            if(dto.getOrganizationId()==0||dto.getOrganizationId()==-1){
            	sqlArgs.add(null);
            }
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getPeriod());
            sqlArgs.add(dto.getPeriod());
            sqlArgs.add(dto.getManagerGuideType());
        } else if (dto.getManagerGuideType().equals("MATCH_CASE_RATE")) {
            sqlStr = "SELECT EOCM.COMPANY,\n" +
                    "       AIDR.PERIOD,\n" +
                    "       AIDR.ACCOUNT_MATCH_CASE,\n" +
                    "       AIDR.CHECK_ASSETS_SUM,\n" +
                    "       CASE B.A WHEN\n" +
                    "               0 THEN\n" +
                    "               STR_REPLACE(ROUND(100 * AIDR.ACCOUNT_MATCH_CASE /\n" +
                    "                             AIDR.CHECK_ASSETS_SUM,\n" +
                    "                             2),\n" +
                    "                       '.',\n" +
                    "                       '0.') ELSE\n" +
                    "               CONVERT(VARCHAR,ROUND(100 * AIDR.ACCOUNT_MATCH_CASE /\n" +
                    "                             AIDR.CHECK_ASSETS_SUM,\n" +
                    "                              2)) || '%' END ASSETS_RATE\n" +
                    "FROM   AMS_IN_DATA_REPORT AIDR,\n" +
                    "       ETS_OU_CITY_MAP    EOCM,(SELECT ROUND(100 * AIDR.ASSETSMENT_FALSE_NUM /AIDR.ASSETSMENT_ASSETS_SUM,2) A FROM AMS_IN_DATA_REPORT AIDR) B\n" +
                    "WHERE  AIDR.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
                    "       AND (-1=? OR ? IS NULL OR AIDR.ORGANIZATION_ID = ?)\n" +
                    "       AND (? IS NULL OR AIDR.PERIOD = ?)\n" +
                    "       AND AIDR.REPORT_TYPE = ?";
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getPeriod());
            sqlArgs.add(dto.getPeriod());
            sqlArgs.add(dto.getManagerGuideType());
        } else if (dto.getManagerGuideType().equals("COP_RATE")) {
            sqlStr = /*"SELECT EOCM.COMPANY,\n" +
                    "       AIDR.PERIOD,\n" +
                    "       AIDR.ASSETS_COP_NUM,\n" +
                    "       AIDR.ASSETS_COP_SUM,\n" +
                    "       CASE B.A WHEN\n" +
                    "               0 THEN\n" +
                    "               STR_REPLACE(ROUND(100 * AIDR.ASSETS_COP_NUM / AIDR.ASSETS_COP_SUM, 2),\n" +
                    "                       '.',\n" +
                    "                        '0.')ELSE\n" +
                    "               CONVERT(VARCHAR,ROUND(100 * AIDR.ASSETS_COP_NUM / AIDR.ASSETS_COP_SUM, 2)) || '%' END ASSETS_RATE\n" +
                    "FROM   AMS_IN_DATA_REPORT AIDR,\n" +
                    "       ETS_OU_CITY_MAP    EOCM,\n" +
                    "       (SELECT ROUND(100 * AIDR.ASSETS_COP_NUM / (CASE AIDR.ASSETS_COP_SUM WHEN 0 THEN 1 ELSE AIDR.ASSETS_COP_SUM END), 2) A FROM AMS_IN_DATA_REPORT AIDR) B\n" +
                    "WHERE  AIDR.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
                    "       AND (-1=? OR ? IS NULL OR AIDR.ORGANIZATION_ID = ?)\n" +
                    "       AND (? IS NULL OR AIDR.PERIOD = ?)\n" +
                    "       AND AIDR.REPORT_TYPE = ?";*/
            	
            	"SELECT EOCM.COMPANY, \n" +
                "		AIDR.PERIOD, \n" +
                "		AIDR.ASSETS_COP_NUM, \n" +
                "		AIDR.ASSETS_COP_SUM, \n" +
                "		CASE AIDR.ASSETS_COP_SUM \n" +
                "	      WHEN 0 THEN '0.00%' \n" +
                "	      ELSE CONVERT(VARCHAR, CONVERT(FLOAT(18), ROUND(100.0 * AIDR.ASSETS_COP_NUM / AIDR.ASSETS_COP_SUM, 4))) || '%' \n" +
                "	    END ASSETS_RATE \n" +
                "  FROM AMS_IN_DATA_REPORT AIDR, \n" +
                "	    ETS_OU_CITY_MAP    EOCM  \n" +
                " WHERE AIDR.ORGANIZATION_ID = EOCM.ORGANIZATION_ID \n" +
            	"   AND (-1 = ? OR ? IS NULL OR AIDR.ORGANIZATION_ID = ?) \n" +
                "   AND (? IS NULL OR AIDR.PERIOD = ?) \n" +
                "   AND AIDR.REPORT_TYPE = ? \n" ;

            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getPeriod());
            sqlArgs.add(dto.getPeriod());
            sqlArgs.add(dto.getManagerGuideType());
        } else if (dto.getManagerGuideType().equals("COP_MATCH_RATE")) {
            sqlStr = /*"SELECT EOCM.COMPANY,\n" +
                    "       AIDR.PERIOD,\n" +
                    "       AIDR.ASSETS_MATCH_CASE,\n" +
                    "       AIDR.ASSETS_CHECK_SUM,\n" +
                    "        CASE B.A WHEN\n" +
                    "                0 THEN\n" +
                    "               STR_REPLACE(ROUND(100 * AIDR.ASSETS_MATCH_CASE /\n" +
                    "                             AIDR.ASSETS_CHECK_SUM,\n" +
                    "                             2),\n" +
                    "                       '.',\n" +
                    "                       '0.')ELSE\n" +
                    "               CONVERT(VARCHAR,ROUND(100 * AIDR.ASSETS_MATCH_CASE /\n" +
                    "                             AIDR.ASSETS_CHECK_SUM,\n" +
                    "                             2)) || '%' END ASSETS_RATE\n" +
                    "FROM   AMS_IN_DATA_REPORT AIDR,\n" +
                    "       ETS_OU_CITY_MAP    EOCM,(SELECT ROUND(100 * AIDR.ASSETS_MATCH_CASE / AIDR.ASSETS_CHECK_SUM,2) A FROM AMS_IN_DATA_REPORT AIDR) B\n" +
                    "WHERE  AIDR.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
                    "       AND (-1 =? OR ? IS NULL OR AIDR.ORGANIZATION_ID = ?)\n" +
                    "       AND (? IS NULL OR AIDR.PERIOD = ?)\n" +
                    "       AND AIDR.REPORT_TYPE = ?";*/

            	"SELECT EOCM.COMPANY, \n" +
                "		AIDR.PERIOD, \n" +
                "		AIDR.ASSETS_MATCH_CASE, \n" +
                "		AIDR.ASSETS_CHECK_SUM, \n" +
                "		CASE AIDR.ASSETS_CHECK_SUM \n" +
                "	      WHEN 0 THEN '0.00%' \n" +
                "	      ELSE CONVERT(VARCHAR, CONVERT(FLOAT(18), ROUND(100.0 * AIDR.ASSETS_MATCH_CASE / AIDR.ASSETS_CHECK_SUM, 4))) || '%' \n" +
                "	    END ASSETS_RATE \n" +
                "  FROM AMS_IN_DATA_REPORT AIDR, \n" +
                "	    ETS_OU_CITY_MAP    EOCM  \n" +
                " WHERE AIDR.ORGANIZATION_ID = EOCM.ORGANIZATION_ID \n" +
            	"   AND (-1 = ? OR ? IS NULL OR AIDR.ORGANIZATION_ID = ?) \n" +
                "   AND (? IS NULL OR AIDR.PERIOD = ?) \n" +
                "   AND AIDR.REPORT_TYPE = ? \n" ;

            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getPeriod());
            sqlArgs.add(dto.getPeriod());
            sqlArgs.add(dto.getManagerGuideType());
        } else if (dto.getManagerGuideType().equals("ACCOUNTING_ACCURATE")) {
            sqlStr = "SELECT EOCM.COMPANY,\n" +
                    "       AIDR.PERIOD,\n" +
                    "       AIDR.ACCURATE_ERROR_NUMBER\n" +
                    "FROM   AMS_IN_DATA_REPORT AIDR,\n" +
                    "       ETS_OU_CITY_MAP    EOCM\n" +
                    "WHERE  AIDR.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
                    "       AND (? IS NULL OR AIDR.ORGANIZATION_ID = ?)\n" +
                    "       AND (? IS NULL OR AIDR.PERIOD = ?)\n" +
                    "       AND AIDR.REPORT_TYPE = ?";
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getPeriod());
            sqlArgs.add(dto.getPeriod());
            sqlArgs.add(dto.getManagerGuideType()); 
        }
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
	}
}
