package com.sino.soa.mis.srv.assetcategory.model;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.soa.mis.srv.assetcategory.dto.SBFIFASrvAssetCategoryDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-9-6
 * Time: 17:15:42
 * To change this template use File | Settings | File Templates.
 */
public class SBFIFASrvAssetCategoryModel extends BaseSQLProducer {

	private SfUserDTO sfUser = null;

	public SBFIFASrvAssetCategoryModel(SfUserDTO userAccount, SBFIFASrvAssetCategoryDTO dtoParameter) {
		super(userAccount, dtoParameter);
		sfUser = userAccount;
	}

	public SQLModel getDataCreateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SBFIFASrvAssetCategoryDTO srvAssetCategory = (SBFIFASrvAssetCategoryDTO)dtoParameter;
		String tmp=srvAssetCategory.getSegment1()+"."+srvAssetCategory.getSegment2()+"."+srvAssetCategory.getSegment3();
		String sqlStr = "INSERT INTO "
			+ " AMS_CONTENT_DIC("
			+ " CONTENT_ID,"
			+ " CONTENT_CODE,"
			+ " CONTENT_NAME,"
			+ " ENABLE,"
			+ " CREATE_BY,"
			+ " CREATE_DATE,"
			+ " LAST_UPDATE_BY,"
			+ " LAST_UPDATE_DATE"
			+ ") VALUES ("
			+ " NEWID(), ?, ?, ?, ?, GETDATE(), ?, GETDATE())";

		sqlArgs.add(tmp);
		sqlArgs.add(srvAssetCategory.getDescription());
		sqlArgs.add(srvAssetCategory.getEnabledFlag());
		sqlArgs.add(sfUser.getUserId());
		sqlArgs.add(sfUser.getUserId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成资产类别服务 SRV_ASSET_CATEGORY数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据更新用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataUpdateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SBFIFASrvAssetCategoryDTO srvAssetCategory = (SBFIFASrvAssetCategoryDTO)dtoParameter;
		String tmp=srvAssetCategory.getSegment1()+"."+srvAssetCategory.getSegment2()+"."+srvAssetCategory.getSegment3();
		String sqlStr = "UPDATE AMS_CONTENT_DIC"
			+ " SET"
			+ " CONTENT_CODE = ?,"
			+ " CONTENT_NAME = ?,"
			+ " ENABLE = ?,"
			+ " LAST_UPDATE_BY = ?,"
			+ " LAST_UPDATE_DATE = GETDATE()"
			+ " WHERE CONTENT_CODE=?"
			;
		sqlArgs.add(tmp);
		sqlArgs.add(srvAssetCategory.getDescription());
		sqlArgs.add(srvAssetCategory.getEnabledFlag());
		sqlArgs.add(sfUser.getUserId());
		sqlArgs.add(tmp);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	public SQLModel getEcouInforModel() {
		SQLModel sqlModel = new SQLModel();
			String sqlStr = "SELECT"
			+"	ACD.CONTENT_CODE "
			+"	FROM AMS_CONTENT_DIC ACD";
		sqlModel.setSqlStr(sqlStr);
		return sqlModel;
	}
	/**
	 * 功能：框架自动生成资产类别服务 SRV_ASSET_CATEGORY数据删除SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SBFIFASrvAssetCategoryDTO srvAssetCategory = (SBFIFASrvAssetCategoryDTO)dtoParameter;
		String sqlStr = "DELETE FROM"
				+ " SRV_ASSET_CATEGORY"	;
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成资产类别服务 SRV_ASSET_CATEGORY数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SBFIFASrvAssetCategoryDTO srvAssetCategory = (SBFIFASrvAssetCategoryDTO)dtoParameter;
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
			+ " WHERE"
			+ " ROWNUM = 1";

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成资产类别服务 SRV_ASSET_CATEGORY多条数据信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回多条数据信息查询用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getMuxDataModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			SBFIFASrvAssetCategoryDTO srvAssetCategory = (SBFIFASrvAssetCategoryDTO)dtoParameter;
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
			sqlArgs.add(srvAssetCategory.getCategoryId());
			sqlArgs.add(srvAssetCategory.getCategoryId());
			sqlArgs.add(srvAssetCategory.getDescription());
			sqlArgs.add(srvAssetCategory.getDescription());
			sqlArgs.add(srvAssetCategory.getCategoryType());
			sqlArgs.add(srvAssetCategory.getCategoryType());
			sqlArgs.add(srvAssetCategory.getSegment1());
			sqlArgs.add(srvAssetCategory.getSegment1());
			sqlArgs.add(srvAssetCategory.getSegment2());
			sqlArgs.add(srvAssetCategory.getSegment2());
			sqlArgs.add(srvAssetCategory.getSegment3());
			sqlArgs.add(srvAssetCategory.getSegment3());
			sqlArgs.add(srvAssetCategory.getAssetCostAccountCcid());
			sqlArgs.add(srvAssetCategory.getAssetCostAccountCcid());
			sqlArgs.add(srvAssetCategory.getReserveAccountCcid());
			sqlArgs.add(srvAssetCategory.getReserveAccountCcid());
			sqlArgs.add(srvAssetCategory.getAssetClearingAccountCcid());
			sqlArgs.add(srvAssetCategory.getAssetClearingAccountCcid());
			sqlArgs.add(srvAssetCategory.getLifeInMonths());
			sqlArgs.add(srvAssetCategory.getLifeInMonths());
			sqlArgs.add(srvAssetCategory.getPercentSalvageValue());
			sqlArgs.add(srvAssetCategory.getPercentSalvageValue());
			sqlArgs.add(srvAssetCategory.getEnabledFlag());
			sqlArgs.add(srvAssetCategory.getEnabledFlag());
			sqlArgs.add(srvAssetCategory.getAttribute1());
			sqlArgs.add(srvAssetCategory.getAttribute1());
			sqlArgs.add(srvAssetCategory.getInventorial());
			sqlArgs.add(srvAssetCategory.getInventorial());
			sqlArgs.add(srvAssetCategory.getCapitalizeFlag());
			sqlArgs.add(srvAssetCategory.getCapitalizeFlag());
			sqlArgs.add(srvAssetCategory.getBookTypeCode());
			sqlArgs.add(srvAssetCategory.getBookTypeCode());
			sqlArgs.add(srvAssetCategory.getLastUpdateDate());
			sqlArgs.add(srvAssetCategory.getLastUpdateDate());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成资产类别服务 SRV_ASSET_CATEGORY页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			SBFIFASrvAssetCategoryDTO srvAssetCategory = (SBFIFASrvAssetCategoryDTO)dtoParameter;
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
			sqlArgs.add(srvAssetCategory.getCategoryId());
			sqlArgs.add(srvAssetCategory.getCategoryId());
			sqlArgs.add(srvAssetCategory.getDescription());
			sqlArgs.add(srvAssetCategory.getDescription());
			sqlArgs.add(srvAssetCategory.getCategoryType());
			sqlArgs.add(srvAssetCategory.getCategoryType());
			sqlArgs.add(srvAssetCategory.getSegment1());
			sqlArgs.add(srvAssetCategory.getSegment1());
			sqlArgs.add(srvAssetCategory.getSegment2());
			sqlArgs.add(srvAssetCategory.getSegment2());
			sqlArgs.add(srvAssetCategory.getSegment3());
			sqlArgs.add(srvAssetCategory.getSegment3());
			sqlArgs.add(srvAssetCategory.getAssetCostAccountCcid());
			sqlArgs.add(srvAssetCategory.getAssetCostAccountCcid());
			sqlArgs.add(srvAssetCategory.getReserveAccountCcid());
			sqlArgs.add(srvAssetCategory.getReserveAccountCcid());
			sqlArgs.add(srvAssetCategory.getAssetClearingAccountCcid());
			sqlArgs.add(srvAssetCategory.getAssetClearingAccountCcid());
			sqlArgs.add(srvAssetCategory.getLifeInMonths());
			sqlArgs.add(srvAssetCategory.getLifeInMonths());
			sqlArgs.add(srvAssetCategory.getPercentSalvageValue());
			sqlArgs.add(srvAssetCategory.getPercentSalvageValue());
			sqlArgs.add(srvAssetCategory.getEnabledFlag());
			sqlArgs.add(srvAssetCategory.getEnabledFlag());
			sqlArgs.add(srvAssetCategory.getAttribute1());
			sqlArgs.add(srvAssetCategory.getAttribute1());
			sqlArgs.add(srvAssetCategory.getInventorial());
			sqlArgs.add(srvAssetCategory.getInventorial());
			sqlArgs.add(srvAssetCategory.getCapitalizeFlag());
			sqlArgs.add(srvAssetCategory.getCapitalizeFlag());
			sqlArgs.add(srvAssetCategory.getBookTypeCode());
			sqlArgs.add(srvAssetCategory.getBookTypeCode());
			sqlArgs.add(srvAssetCategory.getLastUpdateDate());
			sqlArgs.add(srvAssetCategory.getLastUpdateDate());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

}