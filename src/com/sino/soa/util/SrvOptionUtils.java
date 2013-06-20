package com.sino.soa.util;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.QueryException;
import com.sino.base.web.DatabaseForWeb;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author FengYH
 *
 */
public class SrvOptionUtils {
	
	protected Connection conn = null;
	protected SfUserDTO userAccount = null;

	public SrvOptionUtils(SfUserDTO userAccount, Connection conn) {
		this.userAccount = userAccount;
		this.conn = conn;
	}

	 /**
	 * 获取所有有效期间下拉列表框,无角色限制
	 * @param selectedValue String
	 * @param addBlank      是否加入“请选择";
	 * @return String
	 * @throws QueryException
	 */
	public String getPeriodName(String selectedValue, boolean addBlank) throws QueryException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT " +
				"SAPS.PERIOD_OPEN_DATE," +
				"SAPS.PERIOD_NAME \n" +
				"FROM \n " +
				"SRV_ASSET_PERIOD_STATUS SAPS \n" +
				"WHERE \n" +
				"SAPS.PERIOD_CLOSE_DATE IS NULL " +
				"GROUP BY SAPS.PERIOD_NAME,SAPS.PERIOD_OPEN_DATE";
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
			DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
			return webFieldProducer.getOptionHtml(selectedValue, addBlank);
	}
	
	/**
	 * 功能：通过服务名称得到环境代码（用于ODI同步时）
	 * @param odiCode
	 * @return
	 */
	public SQLModel getEnvCode(String odiCode){
		SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT SOE.ENV_CODE FROM SRV_ODI_ENV SOE WHERE SOE.ODI_CODE = ?";
        sqlArgs.add(odiCode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
	}

}
