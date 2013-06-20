package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.newasset.dto.AmsDeprnAssetsDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.security.dto.ServletConfigDTO;

public class MisDeprnAssetsQueryModel extends AMSSQLProducer {

    public MisDeprnAssetsQueryModel(SfUserDTO userAccount, AmsDeprnAssetsDTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    public void setServletConfig(ServletConfigDTO servletConfig) {
        super.setServletConfig(servletConfig);    //To change body of overridden methods use File | Settings | File Templates.
    }

    /**
     * 功能：返回页面翻页查询"MIS资产折旧"时所需要的SQLModel
     * @return SQLModel
     * @throws SQLModelException
     */
    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        AmsDeprnAssetsDTO dto = (AmsDeprnAssetsDTO) dtoParameter;
		String sqlStr = "SELECT" 
					+	"		ZFDD.BOOK_TYPE_CODE,\n"
					+	"		EOCM.BOOK_TYPE_NAME,\n"
					+	"		ZFDD.ASSET_ID,\n"
					+	"		ZFDD.TAG_NUMBER,\n"
					+	"		ZFDD.ASSET_NUMBER,\n"
					+	"		EFA.FA_CATEGORY1,\n"
				    +   "		EFA.FA_CATEGORY2,\n"
					+	"		ZFDD.DESCRIPTION,\n"
					+	"		EOCM.COMPANY SEGMENT1,\n"
					+	"		ZFDD.COST,\n"
					+	"		ZFDD.NET_BOOK_VALUE,\n"
					+	"		ZFDD.PTD_IMPAIRMENT,\n"
					+	"		ZFDD.YTD_IMPAIRMENT,\n"
					+	"		ZFDD.IMPAIRMENT_RESERVE,\n"
					+   " 		(ZFDD.COST - ZFDD.DEPRN_RESERVE - ZFDD.IMPAIRMENT_RESERVE) LIMIT_VALUE,\n" 
					+	"		ZFDD.PTD_DEPRN,\n"
					+	"		ZFDD.YTD_DEPRN,\n"
					+	"		ZFDD.DEPRN_RESERVE,\n"
					+	"		ZFDD.PERIOD_NAME,\n"
					+	"		ZFDD.DEPRN_LEFT_MONTH,\n"
					+	"		ZFDD.LAST_UPDATE_DATE \n"
					+	"  FROM SOA.ZTE_FA_DEPRN_DETAIL ZFDD, ETS_OU_CITY_MAP EOCM, ETS_FA_ASSETS EFA\n"
					+	" WHERE ZFDD.SEGMENT1 = EOCM.COMPANY_CODE\n"
					+	"   AND ZFDD.ASSET_ID = EFA.ASSET_ID\n"
					+	"	AND ZFDD.BOOK_TYPE_CODE = dbo.NVL(?, ZFDD.BOOK_TYPE_CODE)\n"
					+	"	AND ZFDD.TAG_NUMBER = dbo.NVL(?, ZFDD.TAG_NUMBER)\n"
					+	"	AND ZFDD.DESCRIPTION LIKE dbo.NVL(?, ZFDD.DESCRIPTION)\n"
					+	"	AND ZFDD.SEGMENT1 = dbo.NVL(?, ZFDD.SEGMENT1)\n"
					+	"	AND ZFDD.PERIOD_NAME LIKE dbo.NVL(?, ZFDD.PERIOD_NAME)\n"
					+	"	AND EOCM.ORGANIZATION_ID = ISNULL(?, EOCM.ORGANIZATION_ID)\n";
		List sqlArgs = new ArrayList();
		sqlArgs.add(dto.getBookTypeCode());
		sqlArgs.add(dto.getTagNumber());
		sqlArgs.add(dto.getDescription());
		sqlArgs.add(dto.getSegment1());
		sqlArgs.add(dto.getPeriodName());
		sqlArgs.add(dto.getOrganizationId());

		if (!(userAccount.isProvinceUser())) {
		    sqlStr += " AND EOCM.ORGANIZATION_ID = ?";
		    sqlArgs.add(userAccount.getOrganizationId());
		}
		sqlStr += "	ORDER BY ZFDD.BOOK_TYPE_CODE,ZFDD.ASSET_ID";
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

}
