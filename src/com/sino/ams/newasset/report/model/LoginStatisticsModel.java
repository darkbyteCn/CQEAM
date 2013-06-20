package com.sino.ams.newasset.report.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.newasset.dto.AmsAssetsCheckHeaderDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * Function:    工单统计报表
 * Author:      李轶
 * Date:        2009-10-28
 */
public class LoginStatisticsModel extends BaseSQLProducer {

    private SfUserDTO sfUser = null;


    /**
     * 功能：EQUIP_STAT 数据库SQL构造层构造函数
     *
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter EquipStatDTO 本次操作的数据
     */
    public LoginStatisticsModel(SfUserDTO userAccount, AmsAssetsCheckHeaderDTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
    }


    /**
     * 功能：框架自动生成EQUIP_STAT页面翻页查询SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getPageQueryModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
//COMPREHENSIVE	综合部
//COUNTY	县公司
//FINANCIAL	财务部
//MARKET	市场部
//NETWORK	网络部
        String sqlStr = "";
        try {
            ArrayList codes = dto.getDeptCategoryCodes();
            sqlStr = "SELECT EOCM.COMPANY,\n";
            for(int i = 0; i < codes.size(); i++){
                sqlStr += " DECODE(TOTAL." + codes.get(i) + ", NULL, 0, TOTAL." + codes.get(i) + ")  " + codes.get(i) + ",\n";
            }
//                    "       DECODE(TOTAL.FINANCIAL_COUNT, NULL, 0, TOTAL.FINANCIAL_COUNT)         FINANCIAL_COUNT,\n" +
//                    "       DECODE(TOTAL.NETWORK_COUNT, NULL, 0, TOTAL.NETWORK_COUNT)             NETWORK_COUNT,\n" +
//                    "       DECODE(TOTAL.MARKET_COUNT, NULL, 0, TOTAL.MARKET_COUNT)               MARKET_COUNT,\n" +
//                    "       DECODE(TOTAL.COMPREHENSIVE_COUNT, NULL, 0, TOTAL.COMPREHENSIVE_COUNT) COMPREHENSIVE_COUNT,\n" +
//                    "       DECODE(TOTAL.COUNTY_COUNT, NULL, 0, TOTAL.COUNTY_COUNT)               COUNTY_COUNT,\n" +
            sqlStr += "       DECODE(TOTAL.SUM_COUNT, NULL, 0, TOTAL.SUM_COUNT)                     SUM_COUNT\n" +
                    "  FROM ETS_OU_CITY_MAP EOCM,\n" +
                    "       (SELECT ";
//                    "               SUM(DECODE(SU.DEPT_CATEGORY, 'FINANCIAL', 1, 0))     FINANCIAL_COUNT,\n" +
//                    "               SUM(DECODE(SU.DEPT_CATEGORY, 'NETWORK', 1, 0))       NETWORK_COUNT,\n" +
//                    "               SUM(DECODE(SU.DEPT_CATEGORY, 'MARKET', 1, 0))        MARKET_COUNT,\n" +
//                    "               SUM(DECODE(SU.DEPT_CATEGORY, 'COMPREHENSIVE', 1, 0)) COMPREHENSIVE_COUNT,\n" +
//                    "               SUM(DECODE(SU.DEPT_CATEGORY, 'COUNTY', 1, 0))        COUNTY_COUNT,\n" +
            for(int i = 0; i < codes.size(); i++){
                sqlStr +=  "               SUM(DECODE(SU.DEPT_CATEGORY, '" + codes.get(i) + "', 1, 0))  " + codes.get(i) + ",\n";      
            }
            sqlStr += "               COUNT(1) SUM_COUNT,\n" +
                    "               SU.ORGANIZATION_ID\n" +
                    "          FROM SF_USER_LOG SUL,\n" +
                    "               SF_USER     SU\n" +
                    "         WHERE SUL.USER_ACCOUNT = SU.LOGIN_NAME\n" +
                    "           AND SU.DEPT_CATEGORY IN (";
//                    "           AND SU.DEPT_CATEGORY IN ('COMPREHENSIVE','COUNTY','FINANCIAL','MARKET','NETWORK')\n" +
            for(int i = 0; i < codes.size(); i++){
                sqlStr +=  "'" + codes.get(i) + "',";
            }
            sqlStr = sqlStr.substring(0, sqlStr.length()-1) + ")\n";
            sqlStr += "           AND SUL.LOG_TIME >= ISNULL(?, SUL.LOG_TIME)\n" +
                    "           AND SUL.LOG_TIME <= ISNULL(?, SUL.LOG_TIME)\n" +
                    "      GROUP BY SU.ORGANIZATION_ID) TOTAL\n" +
                    " WHERE EOCM.ORGANIZATION_ID *= TOTAL.ORGANIZATION_ID\n" +
                    "   AND EOCM.IS_TD = 'N'\n" +
                    "   AND EOCM.ORGANIZATION_ID = ISNULL(?, EOCM.ORGANIZATION_ID)\n" +
                    " ORDER BY EOCM.ORGANIZATION_ID";

                sqlArgs.add(dto.getStartDate());
                sqlArgs.add(dto.getSQLEndDate());
                sqlArgs.add(dto.getOrganizationId());
        } catch (CalendarException e) {
            e.printStackTrace(); 
        }
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
	 * Function:		得到字典表中定义的所有隶属部门
	 * @param dicValue	字典表中定义的隶属部门代码
	 * @return			SQLModel
	 * @author  		李轶
	 * @Date:   		Oct 29, 2009
	 */
	public SQLModel getDeptCategoryByDic(String dicValue){
		SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT EFV.CODE, EFV.VALUE\n" +
                        "  FROM ETS_FLEX_VALUE_SET EFVS, ETS_FLEX_VALUES EFV\n" +
                        " WHERE EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID\n" +
                        "   AND EFVS.CODE = ?\n" +
                        " ORDER BY EFV.FLEX_VALUE_ID";
        sqlArgs.add(dicValue);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
	}

}