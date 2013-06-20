package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.instrument.dto.AmsInstrumentInfoDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2008-4-9
 * Time: 1:59:18
 * To change this template use File | Settings | File Templates.
 */
public class AssetsBeforedModel extends BaseSQLProducer {
	private SfUserDTO sfUser = null;

	public AssetsBeforedModel(SfUserDTO userAccount,
							  AmsInstrumentInfoDTO dtoParameter) {
		super(userAccount, dtoParameter);
		sfUser = userAccount;
	}

	public SQLModel getPageQueryModel() { //ÒÇÆ÷ÒÇ±íÎ¬»¤²éÑ¯sql
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsInstrumentInfoDTO amsInstrumentInfo = (AmsInstrumentInfoDTO)
												 dtoParameter;
		String sqlStr = "SELECT EII.SYSTEMID,\n" +
						"      EOCM.COMPANY COMPANY_NAME, \n" +
						"      EFA.ASSET_NUMBER, \n" +
						"      EII.BARCODE,\n" +
						"      AMS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY,\n" +
						"      ESI.ITEM_NAME,\n" +
						"      ESI.ITEM_SPEC,\n" +
						"      EFA.LIFE_IN_YEARS, \n" +
						"      EFA.DATE_PLACED_IN_SERVICE,\n" +
						"      EMPV.VENDOR_NAME, \n" +
						"      TRUNC(ADD_MONTHS(EFA.DATE_PLACED_IN_SERVICE, 12*EFA.LIFE_IN_YEARS)) OEVER_DATE\n" +
						"FROM ETS_FA_ASSETS EFA ,\n" +
						"     ETS_OU_CITY_MAP EOCM,\n" +
						"     ETS_ITEM_INFO EII ,\n" +
						"     ETS_SYSTEM_ITEM ESI,\n" +
						"     ETS_MIS_PO_VENDORS EMPV\n" +
						"WHERE (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)\n" +
						"AND EII.FINANCE_PROP = 'ASSETS'\n" +
						"AND EOCM.COMPANY_CODE = EFA.COMPANY_CODE\n" +
						"AND EII.ASSET_ID = EFA.ASSET_ID\n" +
						"AND ESI.ITEM_CODE = EII.ITEM_CODE\n" +
						"AND EMPV.VENDOR_ID = ESI.VENDOR_ID\n" +
						"AND  TRUNC(ADD_MONTHS(EFA.DATE_PLACED_IN_SERVICE, 12*EFA.LIFE_IN_YEARS)) <= GETDATE()\n" +
						"AND EFA.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
						"AND EFA.ORGANIZATION_ID = ?\n" +
						"AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.ORGANIZATION_ID = ?)\n" +
						"AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.ASSET_NUMBER LIKE ?)\n" +
						"AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.BARCODE LIKE ?)\n" +
						"AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_NAME LIKE ?)\n" +
						"AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?)\n" +
						"AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_CATEGORY LIKE ?)\n" +
						"";
		sqlArgs.add(sfUser.getOrganizationId());
		sqlArgs.add(amsInstrumentInfo.getOrganizationId());
		sqlArgs.add(amsInstrumentInfo.getOrganizationId());
		sqlArgs.add(amsInstrumentInfo.getAssetNumber());
		sqlArgs.add(amsInstrumentInfo.getAssetNumber());
		sqlArgs.add(amsInstrumentInfo.getBarcode());
		sqlArgs.add(amsInstrumentInfo.getBarcode());
		sqlArgs.add(amsInstrumentInfo.getItemName());
		sqlArgs.add(amsInstrumentInfo.getItemName());
		sqlArgs.add(amsInstrumentInfo.getItemSpec());
		sqlArgs.add(amsInstrumentInfo.getItemSpec());
		sqlArgs.add(amsInstrumentInfo.getItemCategory());
		sqlArgs.add(amsInstrumentInfo.getItemCategory());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
