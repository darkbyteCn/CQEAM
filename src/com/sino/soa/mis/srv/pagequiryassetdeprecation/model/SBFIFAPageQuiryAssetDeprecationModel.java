package com.sino.soa.mis.srv.pagequiryassetdeprecation.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;
import com.sino.config.SinoConfig;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.soa.mis.srv.pagequiryassetdeprecation.dto.SBFIFAPageQuiryAssetDeprecationDTO;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-10-14
 * Time: 16:05:13
 * To change this template use File | Settings | File Templates.
 */
public class SBFIFAPageQuiryAssetDeprecationModel extends BaseSQLProducer {

	private SfUserDTO sfUser = null;

	public SBFIFAPageQuiryAssetDeprecationModel(SfUserDTO userAccount, SBFIFAPageQuiryAssetDeprecationDTO dtoParameter) {
		super(userAccount, dtoParameter);
		sfUser = userAccount;
	}

    public SQLModel getEcouInforModel(String tagNumber, String periodName) {
		SQLModel sqlModel = new SQLModel();
	    List sqlArgs = new ArrayList();
        String sqlStr = "SELECT ZPDD.TAG_NUMBER, ZPDD.PERIOD_NAME FROM ZTE_FA_DEPRN_DETAIL ZPDD WHERE ZPDD.TAG_NUMBER = ? AND ZPDD.PERIOD_NAME = ?";
		sqlArgs.add(tagNumber);
		sqlArgs.add(periodName);
        sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	public SQLModel getDataCreateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SBFIFAPageQuiryAssetDeprecationDTO srvAssetCategory = (SBFIFAPageQuiryAssetDeprecationDTO)dtoParameter;
		String sqlStr = "INSERT INTO "
			+ " ZTE_FA_DEPRN_DETAIL("
			+ " BOOK_TYPE_CODE,"
			+ " ASSET_ID,"
			+ " TAG_NUMBER,"
			+ " ASSET_NUMBER,"
			+ " DESCRIPTION,"
			+ " SEGMENT1,"
			+ " SEGMENT2,"
			+ " COST,"
			+ " NET_BOOK_VALUE,"
			+ " PTD_IMPAIRMENT,"
			+ " YTD_IMPAIRMENT,"
			+ " IMPAIRMENT_RESERVE,"
			+ " PTD_DEPRN,"
			+ " YTD_DEPRN,"
			+ " DEPRN_RESERVE,"
			+ " PERIOD_NAME,"
			+ " DEPRN_LEFT_MONTH,"
			+ " LAST_UPDATE_DATE,"
			+ " SALVAGE_VALUE,"
			+ " LIFE_YEARS,"
			+ " RETIREMENT_PENDING_FLAG"
			+ ") VALUES ("
			+ " ?, CONVERT(FLOAT, ?), ?, ?, ?, ?, ?, CONVERT(FLOAT, ?), CONVERT(FLOAT, ?), CONVERT(FLOAT, ?), CONVERT(FLOAT, ?), CONVERT(FLOAT, ?), CONVERT(FLOAT, ?), CONVERT(FLOAT, ?), CONVERT(FLOAT, ?), ?, CONVERT(FLOAT, ?), ?, CONVERT(FLOAT, ?), CONVERT(FLOAT, ?), ?)";
		sqlArgs.add(srvAssetCategory.getBookTypeCode());
		sqlArgs.add(srvAssetCategory.getAssetId());
		sqlArgs.add(srvAssetCategory.getTagNumber());
		sqlArgs.add(srvAssetCategory.getAssetNumber());
		sqlArgs.add(srvAssetCategory.getDescription());
		sqlArgs.add(srvAssetCategory.getSegment1());
		sqlArgs.add(srvAssetCategory.getSegment2());
        sqlArgs.add(srvAssetCategory.getCost());
        sqlArgs.add(srvAssetCategory.getNetBookValue());
        sqlArgs.add(srvAssetCategory.getPtdImpairment());
        sqlArgs.add(srvAssetCategory.getYtdImpairment());
        sqlArgs.add(srvAssetCategory.getImpairmentReserve());
        sqlArgs.add(srvAssetCategory.getPtdDeprn());
        sqlArgs.add(srvAssetCategory.getYtdDeprn());
        sqlArgs.add(srvAssetCategory.getDeprnReserve());
        sqlArgs.add(srvAssetCategory.getPeriodName());
        sqlArgs.add(srvAssetCategory.getDeprnLeftMonth());
        sqlArgs.add(srvAssetCategory.getLastUpdateDate().subSequence(0, 10));
        sqlArgs.add(srvAssetCategory.getSalvageValue());
        sqlArgs.add(srvAssetCategory.getLifeYears());
        sqlArgs.add(srvAssetCategory.getRetirementPendingFlag());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	public SQLModel getDataUpdateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SBFIFAPageQuiryAssetDeprecationDTO srvAssetCategory = (SBFIFAPageQuiryAssetDeprecationDTO)dtoParameter;
		String sqlStr = "UPDATE ZTE_FA_DEPRN_DETAIL"
                    + " SET"
                    + " BOOK_TYPE_CODE = ?,"
                    + " ASSET_ID = CONVERT(FLOAT, ?),"
                    + " TAG_NUMBER = ?,"
                    + " ASSET_NUMBER = ?,"
                    + " DESCRIPTION = ?,"
                    + " SEGMENT1 = ?,"
                    + " SEGMENT2 = ?,"
                    + " COST = CONVERT(FLOAT, ?),"
                    + " NET_BOOK_VALUE = CONVERT(FLOAT, ?),"
                    + " PTD_IMPAIRMENT = CONVERT(FLOAT, ?),"
                    + " YTD_IMPAIRMENT = CONVERT(FLOAT, ?),"
                    + " IMPAIRMENT_RESERVE = CONVERT(FLOAT, ?),"
                    + " PTD_DEPRN = CONVERT(FLOAT, ?),"
                    + " YTD_DEPRN = CONVERT(FLOAT, ?),"
                    + " DEPRN_RESERVE = CONVERT(FLOAT, ?),"
                    + " PERIOD_NAME = ?,"
                    + " DEPRN_LEFT_MONTH = CONVERT(FLOAT, ?),"
                    + " LAST_UPDATE_DATE = ?,"
                    + " SALVAGE_VALUE = CONVERT(FLOAT, ?),"
                    + " LIFE_YEARS = CONVERT(FLOAT, ?),"
                    + " RETIREMENT_PENDING_FLAG = ?"
                    + " WHERE TAG_NUMBER = ?"
                    + " AND PERIOD_NAME = ?";
		sqlArgs.add(srvAssetCategory.getBookTypeCode());
		sqlArgs.add(srvAssetCategory.getAssetId());
		sqlArgs.add(srvAssetCategory.getTagNumber());
		sqlArgs.add(srvAssetCategory.getAssetNumber());
		sqlArgs.add(srvAssetCategory.getDescription());
		sqlArgs.add(srvAssetCategory.getSegment1());
		sqlArgs.add(srvAssetCategory.getSegment2());
        sqlArgs.add(srvAssetCategory.getCost());
        sqlArgs.add(srvAssetCategory.getNetBookValue());
        sqlArgs.add(srvAssetCategory.getPtdImpairment());
        sqlArgs.add(srvAssetCategory.getYtdImpairment());
        sqlArgs.add(srvAssetCategory.getImpairmentReserve());
        sqlArgs.add(srvAssetCategory.getPtdDeprn());
        sqlArgs.add(srvAssetCategory.getYtdDeprn());
        sqlArgs.add(srvAssetCategory.getDeprnReserve());
        sqlArgs.add(srvAssetCategory.getPeriodName());
        sqlArgs.add(srvAssetCategory.getDeprnLeftMonth());
        sqlArgs.add(srvAssetCategory.getLastUpdateDate().substring(0, 10));
        sqlArgs.add(srvAssetCategory.getSalvageValue());
        sqlArgs.add(srvAssetCategory.getLifeYears());
        sqlArgs.add(srvAssetCategory.getRetirementPendingFlag());
        sqlArgs.add(srvAssetCategory.getTagNumber());
        sqlArgs.add(srvAssetCategory.getPeriodName());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        SBFIFAPageQuiryAssetDeprecationDTO srvAssetCategory = (SBFIFAPageQuiryAssetDeprecationDTO)dtoParameter;
        String sqlStr = "SELECT "
            + " CATEGORY_ID,"
            + " DESCRIPTION,"
            + " CATEGORY_TYPE,"
            + " SEGMENT1,"
            + " SEGMENT2,"
            + " SEGMENT3,"
            + " ASSET_COST_ACCOUNT_CCID,"
            + " RESERVE_ACCOUNT_CCID,"
            + " ASSET_CLEARING_ACCOUNT_CCID,"
            + " LIFE_IN_MONTHS,"
            + " PERCENT_SALVAGE_VALUE,"
            + " ENABLED_FLAG,"
            + " ATTRIBUTE1,"
            + " INVENTORIAL,"
            + " CAPITALIZE_FLAG,"
            + " BOOK_TYPE_CODE,"
            + " LAST_UPDATE_DATE"
            + " FROM"
            + " SRV_ASSET_CATEGORY"
            + " WHERE"
            + " (? IS NULL OR CATEGORY_ID LIKE ?)"
            + " AND (? IS NULL OR DESCRIPTION LIKE ?)"
            + " AND (? IS NULL OR CATEGORY_TYPE LIKE ?)"
            + " AND (? IS NULL OR SEGMENT1 LIKE ?)"
            + " AND (? IS NULL OR SEGMENT2 LIKE ?)"
            + " AND (? IS NULL OR SEGMENT3 LIKE ?)"
            + " AND (? IS NULL OR ASSET_COST_ACCOUNT_CCID LIKE ?)"
            + " AND (? IS NULL OR RESERVE_ACCOUNT_CCID LIKE ?)"
            + " AND (? IS NULL OR ASSET_CLEARING_ACCOUNT_CCID LIKE ?)"
            + " AND (? IS NULL OR LIFE_IN_MONTHS LIKE ?)"
            + " AND (? IS NULL OR PERCENT_SALVAGE_VALUE LIKE ?)"
            + " AND (? IS NULL OR ENABLED_FLAG LIKE ?)"
            + " AND (? IS NULL OR ATTRIBUTE1 LIKE ?)"
            + " AND (? IS NULL OR INVENTORIAL LIKE ?)"
            + " AND (? IS NULL OR CAPITALIZE_FLAG LIKE ?)"
            + " AND (? IS NULL OR BOOK_TYPE_CODE LIKE ?)"
            + " AND (? IS NULL OR LAST_UPDATE_DATE LIKE ?)";
        sqlArgs.add(srvAssetCategory.getBookTypeCode());
        sqlArgs.add(srvAssetCategory.getBookTypeCode());
        sqlArgs.add(srvAssetCategory.getLastUpdateDate());
        sqlArgs.add(srvAssetCategory.getLastUpdateDate());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

}