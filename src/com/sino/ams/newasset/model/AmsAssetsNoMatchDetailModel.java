package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.newasset.dto.AmsAssetsNoMatchDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

/**
 * Created by IntelliJ IDEA.
 * User: srf
 * Date: 2008-3-14
 * To change this template use File | Settings | File Templates.
 */
public class AmsAssetsNoMatchDetailModel extends AMSSQLProducer {
	private AmsAssetsNoMatchDTO dto = null;

	public AmsAssetsNoMatchDetailModel(SfUserDTO userAccount,
									   AmsAssetsNoMatchDTO dtoParameter) {
		super(userAccount, dtoParameter);
		this.dto = (AmsAssetsNoMatchDTO) dtoParameter;
	}

	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "";
		try {
			sqlStr = "SELECT EFA.ORGANIZATION_ID,\n" +
					 "       EFA.ASSET_NUMBER,\n" +
					 "       EFA.ASSETS_DESCRIPTION,\n" +
					 "       EFA.MODEL_NUMBER,\n" +
					 "       EFA.DATE_PLACED_IN_SERVICE,\n" +
					 "       EFA.ASSIGNED_TO_NAME,\n" +
					 "       EFA.ASSIGNED_TO_NUMBER,\n" +
					 "       AMS_PUB_PKG.GET_ORGNIZATION_NAME(EFA.ORGANIZATION_ID) ORGANIZATION_NAME,\n" +
					 "       EFA.FA_CATEGORY_CODE\n" +
					 "  FROM ETS_FA_ASSETS EFA\n" +
					 " WHERE (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)\n" +
					 "   AND NOT EXISTS\n" +
					 " (SELECT 1 FROM ETS_ITEM_MATCH EIM WHERE EIM.ASSET_ID = EFA.ASSET_ID)\n" +
					 "   AND NOT EXISTS\n" +
					 " (SELECT 1\n" +
					 "          FROM ETS_ITEM_MATCH_ASSIST_MIS EIMAM\n" +
					 "         WHERE EIMAM.ASSET_ID = EFA.ASSET_ID)\n" +
					 "   AND ORGANIZATION_ID = ISNULL(?, ORGANIZATION_ID)\n" +
					 "   AND EFA.DATE_PLACED_IN_SERVICE >= ISNULL(?, EFA.DATE_PLACED_IN_SERVICE)\n" +
					 "   AND EFA.DATE_PLACED_IN_SERVICE <= ISNULL(?, EFA.DATE_PLACED_IN_SERVICE)";
			sqlArgs.add(dto.getOrganizationId());
			sqlArgs.add(dto.getFromDate());
			sqlArgs.add(dto.getToDate());
		} catch (CalendarException e) {
			e.printLog();
			throw new SQLModelException(e);
		}
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
