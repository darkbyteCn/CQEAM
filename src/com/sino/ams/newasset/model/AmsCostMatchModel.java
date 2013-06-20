package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.dto.EtsFaAssetsDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.security.dto.ServletConfigDTO;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2009-8-25
 * Time: 16:02:32
 * To change this template use File | Settings | File Templates.
 */
public class AmsCostMatchModel extends AMSSQLProducer {
	public AmsCostMatchModel(BaseUserDTO userAccount, DTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	public void setServletConfig(ServletConfigDTO servletConfig) {
		super.setServletConfig(servletConfig);
	}

	public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsFaAssetsDTO dto = (EtsFaAssetsDTO) dtoParameter;
        String sqlStr = "SELECT EOCM.COMPANY,\n" +
                "       EOCM.COMPANY_CODE,\n" +
                "       EC.COUNTY_CODE,\n" +
                "       EC.COUNTY_NAME\n" +
                "  FROM ETS_COUNTY      EC,\n" +
                "       ETS_OU_CITY_MAP EOCM\n" +
                " WHERE EC.COMPANY_CODE = EOCM.COMPANY_CODE\n" +
                "       AND SUBSTRING(EOCM.COMPANY_CODE, 0, 2) <> 80\n" +
                "       AND (( " + SyBaseSQLUtil.isNull() + "  OR EC.COUNTY_CODE LIKE ?) OR\n" +
                "       ( " + SyBaseSQLUtil.isNull() + "  OR EC.COUNTY_NAME LIKE ?))\n" +
                " ORDER BY EOCM.COMPANY";
        sqlArgs.add(dto.getPrjName());
        sqlArgs.add(dto.getPrjName());
        sqlArgs.add(dto.getPrjName());
        sqlArgs.add(dto.getPrjName());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
