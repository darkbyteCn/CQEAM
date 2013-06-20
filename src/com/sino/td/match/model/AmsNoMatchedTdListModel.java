package com.sino.td.match.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.match.dto.AmsNoMactingAssetDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;

/**
 * Created by IntelliJ IDEA.
 * User: jiangtao
 * Date: 2007-11-21
 * Time: 11:55:32
 * To change this template use File | Settings | File Templates.
 */
public class AmsNoMatchedTdListModel extends AMSSQLProducer {

	/**
	 * 功能：未匹配资产清单 数据库SQL构造层构造函数
	 * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsHouseInfoDTO 本次操作的数据
	 */
	public AmsNoMatchedTdListModel(SfUserDTO userAccount, AmsNoMactingAssetDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 框架自动生成未匹配资产清单 查询 。
	 * @return SQLModel
	 */
	public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();
		AmsNoMactingAssetDTO dto = (AmsNoMactingAssetDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
						+ " EFA.TAG_NUMBER,"
						+ " EFA.ASSETS_DESCRIPTION,"
						+ " EFA.MODEL_NUMBER,"
						+ " EFA.ASSETS_LOCATION,"
						+ " EFA.DATE_PLACED_IN_SERVICE,"
						+ " EFA.LIFE_IN_YEARS,"
						+ " EFA.COST,"
						+ " EFA.ASSET_NUMBER,"
						+ " EFA.BOOK_TYPE_CODE,"
						+ " EFA.ASSETS_LOCATION_CODE,"
						+ " ACCV.COST_CENTER_NAME"
						+ " FROM"
						+ " ETS_FA_ASSETS_TD      EFA,"
						+ " AMS_COST_CENTER_V  ACCV"
						+ " WHERE"
						+ " SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) *= ACCV.COST_CENTER_CODE"
						+ " AND (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)"
						+ " AND EFA.TAG_NUMBER LIKE dbo.NVL(?, EFA.TAG_NUMBER)"
						+ " AND EFA.ASSETS_DESCRIPTION LIKE dbo.NVL(?, EFA.ASSETS_DESCRIPTION)"
						+ " AND (EFA.ASSETS_LOCATION_CODE LIKE dbo.NVL(?, EFA.ASSETS_LOCATION_CODE)"
						+ " OR EFA.ASSETS_LOCATION LIKE dbo.NVL(?, EFA.ASSETS_LOCATION))"
						+ "   AND ((" + SyBaseSQLUtil.isNull() + " OR ACCV.COST_CENTER_CODE LIKE ?) OR\n" +
                "        (" + SyBaseSQLUtil.isNull() + " OR ACCV.COST_CENTER_NAME LIKE ?))"

						+ " AND NOT EXISTS ("
						+ " SELECT"
						+ " NULL"
						+ " FROM"
						+ " ETS_ITEM_MATCH_TD EIM"
						+ " WHERE"
						+ " EIM.ASSET_ID = EFA.ASSET_ID)"

						+ " AND NOT EXISTS ("
						+ " SELECT"
						+ " NULL"
						+ " FROM"
						+ " ETS_ITEM_MATCH_ASSIST_MIS EIMAM"
						+ " WHERE"
						+ " EIMAM.ASSET_ID = EFA.ASSET_ID)"

						+ " AND EFA.ORGANIZATION_ID = ?"
						+ " ORDER BY"
						+ " EFA.ASSETS_LOCATION_CODE DESC";

		sqlArgs.add(dto.getTagNumber());
		sqlArgs.add(dto.getAssetsDescription());
		sqlArgs.add(dto.getWorkorderObjectName());
		sqlArgs.add(dto.getWorkorderObjectName());
		sqlArgs.add(dto.getCostCenterName());
		sqlArgs.add(dto.getCostCenterName());
		sqlArgs.add(dto.getCostCenterName());
		sqlArgs.add(dto.getCostCenterName());
		sqlArgs.add(userAccount.getOrganizationId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);

		return sqlModel;
	}

}
