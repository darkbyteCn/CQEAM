package com.sino.ams.newasset.report.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.report.dto.AssetsGeneralDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2009-5-13
 * Time: 15:28:17
 * To change this template use File | Settings | File Templates.
 */
public class AssetsGeneralModel extends AMSSQLProducer {

	public AssetsGeneralModel(SfUserDTO userAccount, AssetsGeneralDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

    /**
	 * 功能：总账模块和资产模块数据准确率(地市明细信息)/固定资产回报率(地市明细信息)/固定资产周转率（地市明细信息）
	 * @return SQLModel
	 * @throws com.sino.base.exception.SQLModelException
	 */
    public SQLModel getGenVerCheckModel(AssetsGeneralDTO dto) throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "";
        if (dto.getManagerGuideType().equals("DATA_NICETY")) {
            sqlStr = "SELECT TOTAL2.COMPANY,\n" +
                "       TOTAL2.PERIOD,\n" +
                "       ROUND(100 *\n" +
                "             (1 - ((ABS(TOTAL1.COST_END_BALANCE_GL - TOTAL2.COST_END_BALANCE) +\n" +
                "             ABS(TOTAL1.SUM_NBV_GL - TOTAL2.SUM_NBV) +\n" +
                "             ABS(TOTAL1.DEPRN_RESERVE_GL - TOTAL2.DEPRN_RESERVE) +\n" +
                "             ABS(TOTAL1.END_JZZB_GL - TOTAL2.END_JZZB)) /\n" +
                "             (TOTAL2.COST_END_BALANCE + TOTAL2.SUM_NBV +\n" +
                "              TOTAL2.DEPRN_RESERVE + TOTAL2.END_JZZB))),\n" +
                "             2) || '%' ASSETS_RATE\n" +
                "FROM   (SELECT AAGV.COST_END_BALANCE COST_END_BALANCE_GL,\n" +
                "               AAGV.SUM_NBV SUM_NBV_GL,\n" +
                "               AAGV.DEPRN_RESERVE DEPRN_RESERVE_GL,\n" +
                "               AAGV.END_JZZB END_JZZB_GL,\n" +
                "               AAGV.COMPANY_CODE\n" +
                "        FROM   AMS_ASSETS_GENERAL_VERACITY AAGV\n" +
                "        WHERE  AAGV.COMPANY_CODE <> 'CTOT'\n" +
                "               AND SUBSTRING(AAGV.ZHIBIAO_CODE, 1, 4) = 'FAGL') TOTAL1,\n" +
                "       (SELECT EOCM.COMPANY COMPANY,\n" +
                "               AAGV.PERIOD,\n" +
                "               AAGV.COST_END_BALANCE,\n" +
                "               AAGV.SUM_NBV,\n" +
                "               AAGV.DEPRN_RESERVE,\n" +
                "               AAGV.END_JZZB,\n" +
                "               AAGV.COMPANY_CODE\n" +
                "        FROM   AMS_ASSETS_GENERAL_VERACITY AAGV,\n" +
                "               ETS_OU_CITY_MAP             EOCM\n" +
                "        WHERE  AAGV.COMPANY_CODE = EOCM.COMPANY_CODE\n" +
                "               AND AAGV.COMPANY_CODE <> 'CTOT'\n" +
                "               AND SUBSTRING(AAGV.ZHIBIAO_CODE, 1, 4) = 'FA00') TOTAL2\n" +
                "WHERE  TOTAL1.COMPANY_CODE = TOTAL2.COMPANY_CODE\n" +
                "       AND TOTAL2.PERIOD = ?";
        } else if (dto.getManagerGuideType().equals("ASSETS_REDOUND")) {
            sqlStr = "SELECT TOTAL.COMPANY,\n" +
                    "       TOTAL.PERIOD,\n" +
                    "       TOTAL.CURRENT_AMOUNT,\n" +
                    "       (TOTAL1.BEGIN_AMOUNT + TOTAL2.BEGIN_AMOUNT) BEGIN_AMOUNT,\n" +
                    "       (TOTAL3.END_AMOUNT + TOTAL4.END_AMOUNT) END_AMOUNT,\n" +
                    "       ROUND(100 * (TOTAL.CURRENT_AMOUNT * 2 /\n" +
                    "             (TOTAL1.BEGIN_AMOUNT + TOTAL2.BEGIN_AMOUNT +\n" +
                    "             TOTAL3.END_AMOUNT + TOTAL4.END_AMOUNT)),\n" +
                    "             2) || '%' ASSETS_RATE\n" +
                    "FROM   (SELECT AART.COMPANY_CODE,\n" +
                    "               EOCM.COMPANY,\n" +
                    "               AART.PERIOD,\n" +
                    "               AART.CURRENT_AMOUNT\n" +
                    "        FROM   AMS_ASSETS_RESPOND_TURNOVER AART,\n" +
                    "               ETS_OU_CITY_MAP             EOCM\n" +
                    "        WHERE  AART.COMPANY_CODE <> 'CTOT'\n" +
                    "               AND EOCM.COMPANY_CODE = AART.COMPANY_CODE\n" +
                    "               AND AART.SUBJECT = 'CPL510100') TOTAL,\n" +
                    "       (SELECT AART.COMPANY_CODE,\n" +
                    "               AART.PERIOD,\n" +
                    "               AART.BEGIN_AMOUNT\n" +
                    "        FROM   AMS_ASSETS_RESPOND_TURNOVER AART\n" +
                    "        WHERE  AART.COMPANY_CODE <> 'T'\n" +
                    "               AND AART.SUBJECT = '1501') TOTAL1,\n" +
                    "       (SELECT AART.COMPANY_CODE,\n" +
                    "               AART.PERIOD,\n" +
                    "               AART.BEGIN_AMOUNT\n" +
                    "        FROM   AMS_ASSETS_RESPOND_TURNOVER AART\n" +
                    "        WHERE  AART.COMPANY_CODE <> 'T'\n" +
                    "               AND AART.SUBJECT = '1502') TOTAL2,\n" +
                    "       (SELECT AART.COMPANY_CODE,\n" +
                    "               AART.PERIOD,\n" +
                    "               AART.END_AMOUNT\n" +
                    "        FROM   AMS_ASSETS_RESPOND_TURNOVER AART\n" +
                    "        WHERE  AART.COMPANY_CODE <> 'T'\n" +
                    "               AND AART.SUBJECT = '1501') TOTAL3,\n" +
                    "       (SELECT AART.COMPANY_CODE,\n" +
                    "               AART.PERIOD,\n" +
                    "               AART.END_AMOUNT\n" +
                    "        FROM   AMS_ASSETS_RESPOND_TURNOVER AART\n" +
                    "        WHERE  AART.COMPANY_CODE <> 'T'\n" +
                    "               AND AART.SUBJECT = '1502') TOTAL4\n" +
                    "WHERE  TOTAL.PERIOD = TOTAL1.PERIOD\n" +
                    "       AND TOTAL.PERIOD = TOTAL2.PERIOD\n" +
                    "       AND TOTAL.PERIOD = TOTAL3.PERIOD\n" +
                    "       AND TOTAL.PERIOD = TOTAL4.PERIOD\n" +
                    "       AND TOTAL.COMPANY_CODE = TOTAL1.COMPANY_CODE\n" +
                    "       AND TOTAL.COMPANY_CODE = TOTAL2.COMPANY_CODE\n" +
                    "       AND TOTAL.COMPANY_CODE = TOTAL3.COMPANY_CODE\n" +
                    "       AND TOTAL.COMPANY_CODE = TOTAL4.COMPANY_CODE\n" +
                    "       AND TOTAL.PERIOD = ?";
        } else if (dto.getManagerGuideType().equals("ASSETS_TURNOVER")) {
            sqlStr = "SELECT TOTAL.COMPANY,\n" +
                    "       TOTAL.PERIOD,\n" +
                    "       TOTAL.CURRENT_AMOUNT,\n" +
                    "       (TOTAL1.BEGIN_AMOUNT + TOTAL2.BEGIN_AMOUNT) BEGIN_AMOUNT,\n" +
                    "       (TOTAL3.END_AMOUNT + TOTAL4.END_AMOUNT) END_AMOUNT,\n" +
                    "       ROUND(100 * (TOTAL.CURRENT_AMOUNT * 2 /\n" +
                    "             (TOTAL1.BEGIN_AMOUNT + TOTAL2.BEGIN_AMOUNT +\n" +
                    "             TOTAL3.END_AMOUNT + TOTAL4.END_AMOUNT)),\n" +
                    "             2) || '%' ASSETS_RATE\n" +
                    "FROM   (SELECT AART.COMPANY_CODE,\n" +
                    "               EOCM.COMPANY,\n" +
                    "               AART.PERIOD,\n" +
                    "               ABS(AART.CURRENT_AMOUNT) CURRENT_AMOUNT\n" +
                    "        FROM   AMS_ASSETS_RESPOND_TURNOVER AART,\n" +
                    "               ETS_OU_CITY_MAP             EOCM\n" +
                    "        WHERE  AART.COMPANY_CODE <> 'T'\n" +
                    "               AND EOCM.COMPANY_CODE = AART.COMPANY_CODE\n" +
                    "               AND AART.SUBJECT = '5114') TOTAL,\n" +
                    "       (SELECT AART.COMPANY_CODE,\n" +
                    "               AART.PERIOD,\n" +
                    "               AART.BEGIN_AMOUNT\n" +
                    "        FROM   AMS_ASSETS_RESPOND_TURNOVER AART\n" +
                    "        WHERE  AART.COMPANY_CODE <> 'T'\n" +
                    "               AND AART.SUBJECT = '1501') TOTAL1,\n" +
                    "       (SELECT AART.COMPANY_CODE,\n" +
                    "               AART.PERIOD,\n" +
                    "               AART.BEGIN_AMOUNT\n" +
                    "        FROM   AMS_ASSETS_RESPOND_TURNOVER AART\n" +
                    "        WHERE  AART.COMPANY_CODE <> 'T'\n" +
                    "               AND AART.SUBJECT = '1502') TOTAL2,\n" +
                    "       (SELECT AART.COMPANY_CODE,\n" +
                    "               AART.PERIOD,\n" +
                    "               AART.END_AMOUNT\n" +
                    "        FROM   AMS_ASSETS_RESPOND_TURNOVER AART\n" +
                    "        WHERE  AART.COMPANY_CODE <> 'T'\n" +
                    "               AND AART.SUBJECT = '1501') TOTAL3,\n" +
                    "       (SELECT AART.COMPANY_CODE,\n" +
                    "               AART.PERIOD,\n" +
                    "               AART.END_AMOUNT\n" +
                    "        FROM   AMS_ASSETS_RESPOND_TURNOVER AART\n" +
                    "        WHERE  AART.COMPANY_CODE <> 'T'\n" +
                    "               AND AART.SUBJECT = '1502') TOTAL4\n" +
                    "WHERE  TOTAL.PERIOD = TOTAL1.PERIOD\n" +
                    "       AND TOTAL.PERIOD = TOTAL2.PERIOD\n" +
                    "       AND TOTAL.PERIOD = TOTAL3.PERIOD\n" +
                    "       AND TOTAL.PERIOD = TOTAL4.PERIOD\n" +
                    "       AND TOTAL.COMPANY_CODE = TOTAL1.COMPANY_CODE\n" +
                    "       AND TOTAL.COMPANY_CODE = TOTAL2.COMPANY_CODE\n" +
                    "       AND TOTAL.COMPANY_CODE = TOTAL3.COMPANY_CODE\n" +
                    "       AND TOTAL.COMPANY_CODE = TOTAL4.COMPANY_CODE\n" +
                    "       AND TOTAL.PERIOD = ?";
        }
        sqlArgs.add(dto.getPeriod());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

    /**
	 * 功能：总账模块和资产模块数据准确率/固定资产回报率/固定资产周转率
	 * @return SQLModel
	 * @throws com.sino.base.exception.SQLModelException
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
	    List sqlArgs = new ArrayList();
        AssetsGeneralDTO dto = (AssetsGeneralDTO) dtoParameter;
        String sqlStr = "";
        if (dto.getManagerGuideType().equals("DATA_NICETY")) {
            sqlStr = "SELECT TOTAL2.COMPANY,\n" +
                "       TOTAL2.PERIOD,\n" +
                "        CONVERT(VARCHAR,ROUND(100 *\n" +
                "             (1 - ((ABS(TOTAL1.COST_END_BALANCE_GL - TOTAL2.COST_END_BALANCE) +\n" +
                "             ABS(TOTAL1.SUM_NBV_GL - TOTAL2.SUM_NBV) +\n" +
                "             ABS(TOTAL1.DEPRN_RESERVE_GL - TOTAL2.DEPRN_RESERVE) +\n" +
                "             ABS(TOTAL1.END_JZZB_GL - TOTAL2.END_JZZB)) /\n" +
                "             (TOTAL2.COST_END_BALANCE + TOTAL2.SUM_NBV +\n" +
                "             TOTAL2.DEPRN_RESERVE + TOTAL2.END_JZZB))),\n" +
                "             2)) || '%' ASSETS_RATE\n" +
                "FROM   (SELECT AAGV.COST_END_BALANCE COST_END_BALANCE_GL,\n" +
                "               AAGV.SUM_NBV SUM_NBV_GL,\n" +
                "               AAGV.DEPRN_RESERVE DEPRN_RESERVE_GL,\n" +
                "               AAGV.END_JZZB END_JZZB_GL,\n" +
                "               AAGV.COMPANY_CODE\n" +
                "        FROM   AMS_ASSETS_GENERAL_VERACITY AAGV\n" +
                "        WHERE  AAGV.COMPANY_CODE = 'CTOT'\n" +
                "               AND SUBSTRING(AAGV.ZHIBIAO_CODE, 1, 4) = 'FAGL') TOTAL1,\n" +
                "       (SELECT (CASE AAGV.COMPANY_CODE WHEN 'CTOT' THEN '省公司总账' ELSE NULL END) COMPANY,\n" +
                "               AAGV.PERIOD,\n" +
                "               AAGV.COST_END_BALANCE,\n" +
                "               AAGV.SUM_NBV,\n" +
                "               AAGV.DEPRN_RESERVE,\n" +
                "               AAGV.END_JZZB,\n" +
                "               AAGV.COMPANY_CODE\n" +
                "        FROM   AMS_ASSETS_GENERAL_VERACITY AAGV\n" +
                "        WHERE  AAGV.COMPANY_CODE = 'CTOT'\n" +
                "               AND SUBSTRING(AAGV.ZHIBIAO_CODE, 1, 4) = 'FA00') TOTAL2\n" +
                "WHERE  TOTAL1.COMPANY_CODE = TOTAL2.COMPANY_CODE\n" +
                "       AND ( " + SyBaseSQLUtil.isNull() + "  OR TOTAL2.PERIOD = CONVERT(INT, ?))";
        } else if (dto.getManagerGuideType().equals("ASSETS_REDOUND")) {
        	sqlStr = "	SELECT ( CASE TOTAL.COMPANY_CODE  WHEN  'CTOT' THEN   '省公司总账' ELSE '' END ) COMPANY, " +
//            sqlStr = "SELECT DECODE(TOTAL.COMPANY_CODE, 'CTOT', '省公司总账', NULL) COMPANY,\n" +
                    "       TOTAL.PERIOD,\n" +
                    "       TOTAL.CURRENT_AMOUNT,\n" +
                    "       (TOTAL1.BEGIN_AMOUNT + TOTAL2.BEGIN_AMOUNT) BEGIN_AMOUNT,\n" +
                    "       (TOTAL3.END_AMOUNT + TOTAL4.END_AMOUNT) END_AMOUNT,\n" +
                    "        CONVERT( VARCHAR , ROUND(100 * (TOTAL.CURRENT_AMOUNT * 2 /\n" +
                    "             (TOTAL1.BEGIN_AMOUNT + TOTAL2.BEGIN_AMOUNT +\n" +
                    "             TOTAL3.END_AMOUNT + TOTAL4.END_AMOUNT)),\n" +
                    "             2) ) || '%' ASSETS_RATE\n" +
                    "FROM   (SELECT AART.COMPANY_CODE,\n" +
                    "               AART.PERIOD,\n" +
                    "               AART.CURRENT_AMOUNT\n" +
                    "        FROM   AMS_ASSETS_RESPOND_TURNOVER AART\n" +
                    "        WHERE  AART.COMPANY_CODE = 'CTOT'\n" +
                    "               AND AART.SUBJECT = 'CPL510100') TOTAL,\n" +
                    "       (SELECT AART.PERIOD,\n" +
                    "               AART.BEGIN_AMOUNT\n" +
                    "        FROM   AMS_ASSETS_RESPOND_TURNOVER AART\n" +
                    "        WHERE  AART.COMPANY_CODE = 'T'\n" +
                    "               AND AART.SUBJECT = '1501') TOTAL1,\n" +
                    "       (SELECT AART.PERIOD,\n" +
                    "               AART.BEGIN_AMOUNT\n" +
                    "        FROM   AMS_ASSETS_RESPOND_TURNOVER AART\n" +
                    "        WHERE  AART.COMPANY_CODE = 'T'\n" +
                    "               AND AART.SUBJECT = '1502') TOTAL2,\n" +
                    "       (SELECT AART.PERIOD,\n" +
                    "               AART.END_AMOUNT\n" +
                    "        FROM   AMS_ASSETS_RESPOND_TURNOVER AART\n" +
                    "        WHERE  AART.COMPANY_CODE = 'T'\n" +
                    "               AND AART.SUBJECT = '1501') TOTAL3,\n" +
                    "       (SELECT AART.PERIOD,\n" +
                    "               AART.END_AMOUNT\n" +
                    "        FROM   AMS_ASSETS_RESPOND_TURNOVER AART\n" +
                    "        WHERE  AART.COMPANY_CODE = 'T'\n" +
                    "               AND AART.SUBJECT = '1502') TOTAL4\n" +
                    "WHERE  TOTAL.PERIOD = TOTAL1.PERIOD\n" +
                    "       AND TOTAL.PERIOD = TOTAL2.PERIOD\n" +
                    "       AND TOTAL.PERIOD = TOTAL3.PERIOD\n" +
                    "       AND TOTAL.PERIOD = TOTAL4.PERIOD\n" +
                    "       AND ( " + SyBaseSQLUtil.isNull() + "  OR CONVERT( VARCHAR , TOTAL.PERIOD ) = ?)";
        } else if (dto.getManagerGuideType().equals("ASSETS_TURNOVER")) {
            sqlStr = "SELECT (CASE TOTAL.COMPANY_CODE WHEN 'T' THEN '省公司总账' ELSE NULL END) COMPANY,\n" +
                    "       TOTAL.PERIOD,\n" +
                    "       TOTAL.CURRENT_AMOUNT,\n" +
                    "       (TOTAL1.BEGIN_AMOUNT + TOTAL2.BEGIN_AMOUNT) BEGIN_AMOUNT,\n" +
                    "       (TOTAL3.END_AMOUNT + TOTAL4.END_AMOUNT) END_AMOUNT,\n" +
                    "       CONVERT(VARCHAR ,ROUND(100 * (TOTAL.CURRENT_AMOUNT * 2 /\n" +
                    "             (TOTAL1.BEGIN_AMOUNT + TOTAL2.BEGIN_AMOUNT +\n" +
                    "             TOTAL3.END_AMOUNT + TOTAL4.END_AMOUNT)),\n" +
                    "             2)) || '%' ASSETS_RATE\n" +
                    "FROM   (SELECT AART.COMPANY_CODE,\n" +
                    "               AART.PERIOD,\n" +
                    "               ABS(AART.CURRENT_AMOUNT) CURRENT_AMOUNT\n" +
                    "        FROM   AMS_ASSETS_RESPOND_TURNOVER AART\n" +
                    "        WHERE  AART.COMPANY_CODE = 'T'\n" +
                    "               AND AART.SUBJECT = '5114') TOTAL,\n" +
                    "       (SELECT AART.PERIOD,\n" +
                    "               AART.BEGIN_AMOUNT\n" +
                    "        FROM   AMS_ASSETS_RESPOND_TURNOVER AART\n" +
                    "        WHERE  AART.COMPANY_CODE = 'T'\n" +
                    "               AND AART.SUBJECT = '1501') TOTAL1,\n" +
                    "       (SELECT AART.PERIOD,\n" +
                    "               AART.BEGIN_AMOUNT\n" +
                    "        FROM   AMS_ASSETS_RESPOND_TURNOVER AART\n" +
                    "        WHERE  AART.COMPANY_CODE = 'T'\n" +
                    "               AND AART.SUBJECT = '1502') TOTAL2,\n" +
                    "       (SELECT AART.PERIOD,\n" +
                    "               AART.END_AMOUNT\n" +
                    "        FROM   AMS_ASSETS_RESPOND_TURNOVER AART\n" +
                    "        WHERE  AART.COMPANY_CODE = 'T'\n" +
                    "               AND AART.SUBJECT = '1501') TOTAL3,\n" +
                    "       (SELECT AART.PERIOD,\n" +
                    "               AART.END_AMOUNT\n" +
                    "        FROM   AMS_ASSETS_RESPOND_TURNOVER AART\n" +
                    "        WHERE  AART.COMPANY_CODE = 'T'\n" +
                    "               AND AART.SUBJECT = '1502') TOTAL4\n" +
                    "WHERE  TOTAL.PERIOD = TOTAL1.PERIOD\n" +
                    "       AND TOTAL.PERIOD = TOTAL2.PERIOD\n" +
                    "       AND TOTAL.PERIOD = TOTAL3.PERIOD\n" +
                    "       AND TOTAL.PERIOD = TOTAL4.PERIOD\n" +
                    "       AND ( " + SyBaseSQLUtil.isNull() + "  OR TOTAL.PERIOD = CONVERT(decimal,?))";
        }
        sqlArgs.add(dto.getPeriod());
        sqlArgs.add(dto.getPeriod());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
	}
}
