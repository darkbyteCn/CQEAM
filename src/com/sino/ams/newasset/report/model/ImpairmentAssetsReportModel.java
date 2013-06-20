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
 * User: 李轶
 * Date: 2009-6-15
 * Time: 10:30:55
 * Function	:		资产减值情况报表
 */

public class ImpairmentAssetsReportModel extends AMSSQLProducer {
    private String deptCodes = "";

    public ImpairmentAssetsReportModel(SfUserDTO userAccount, DeptAssetsReportDTO dtoParameter) {
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
     * 功能：获取资产减值情况统计报表SQL
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
        	sqlStr = "SELECT TOTAL.COMPANY,\n" +
		        	"       TOTAL.SUM_COUNT,\n" +
		            "       TOTAL.IMPAIR_AMOUNT,\n" +
		            "       TOTAL.IMPAIR_RESERVE,\n" +            
		            "       (DECODE(TRUNC(100 * TOTAL.SUM_COUNT / SUM_COST.TOTAL),\n" +
		            "               0,\n" +
		            "               STR_REPLACE(ROUND(100 * TOTAL.SUM_COUNT / SUM_COST.TOTAL, 3),\n" +
		            "                       '.',\n" +
		            "                       '0.'),\n" +
		            "               TO_CHAR(ROUND(100 * TOTAL.SUM_COUNT / SUM_COST.TOTAL, 3))) || '%') ASSETS_RATE\n" +
		            " FROM   (SELECT COUNT(EII.ITEM_QTY) TOTAL\n" +
		            "        FROM   ETS_FA_ASSETS   		EFA,\n" +
		            "               ETS_ITEM_INFO           EII,\n" +
		            "               ETS_ITEM_MATCH          EIM\n" +
		            "        WHERE  EII.SYSTEMID = EIM.SYSTEMID\n" +
		            "               AND EFA.ASSET_ID = EIM.ASSET_ID\n" +
		            "				AND EII.ITEM_STATUS = 'IMPAIRMENT'"	+
		            "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.ORGANIZATION_ID = ?)) SUM_COST,\n" +   
		    
		            "       (SELECT AOCM.COMPANY COMPANY,\n" +
		            "               COUNT(EII.ITEM_QTY) SUM_COUNT,\n" +
		            "               SUM(EFAHR.IMPAIR_AMOUNT) IMPAIR_AMOUNT,\n" +	//当期减值
		            "               SUM(EFAHR.IMPAIR_RESERVE) IMPAIR_RESERVE,\n" +	//累计减值
		            "               AOCM.ORGANIZATION_ID\n" +
		            "        FROM   ETS_ITEM_INFO   		EII,\n" +
		            "               ETS_FA_ASSETS_HIS_REP   EFAHR,\n" +
		            "               ETS_OU_CITY_MAP 		AOCM,\n" +
		            "               ETS_ITEM_MATCH  		EIM,\n" +
		            "				ETS_ITEM_INFO_ATTR_CHG  EIIAC \n"	+
		            "        WHERE  EII.ITEM_STATUS = 'IMPAIRMENT'" +
		            "				AND EII.SYSTEMID = EIM.SYSTEMID\n" +
		            "               AND EFAHR.ASSET_ID = EIM.ASSET_ID\n" +
		            "               AND AOCM.ORGANIZATION_ID = EFAHR.ORGANIZATION_ID\n" +
		            "				AND EIIAC.BAR_CODE = EFAHR.TAG_NUMBER \n"	+
		            "				AND EIIAC.PERIOD_NAME = EFAHR.PERIOD_NAME\n"	+
		            "               AND EFAHR.PERIOD_NAME = ? \n" +
		            "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EFAHR.ORGANIZATION_ID = ?)"	+
		            "        GROUP  BY AOCM.COMPANY,\n" +
		            "                  AOCM.ORGANIZATION_ID) TOTAL \n" +
		            " ORDER  BY TOTAL.ORGANIZATION_ID";
        	
         sqlArgs.add(dto.getOrganizationId());
         sqlArgs.add(dto.getOrganizationId());
         
         sqlArgs.add(dto.getPeriodNameByHisRep());
         sqlArgs.add(dto.getOrganizationId());
         sqlArgs.add(dto.getOrganizationId());
             
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
