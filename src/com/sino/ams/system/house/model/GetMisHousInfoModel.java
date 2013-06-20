package com.sino.ams.system.house.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.newasset.dto.EtsFaAssetsDTO;
import com.sino.ams.system.fixing.dto.EtsItemInfoDTO;
import com.sino.ams.system.house.dto.AmsHouseInfoDTO;
import com.sino.ams.system.house.dto.AmsHouseUsesDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.ArrUtil;
import com.sino.base.util.StrUtil;

/**
 * Created by IntelliJ IDEA.
 * User: Zyun
 * Date: 2008-5-28
 * Time: 11:11:39
 * To change this template use File | Settings | File Templates.
 */
public class GetMisHousInfoModel extends AMSSQLProducer {


	/**
	 * 功能：租赁土地资产(EAM) AMS_LAND_INFO 数据库SQL构造层构造函数
	 * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsHouseInfoDTO 本次操作的数据
	 */
	public GetMisHousInfoModel(SfUserDTO userAccount, AmsHouseInfoDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：框架自动生成租赁土地资产(EAM) AMS_LAND_INFO数据插入SQLModel，请根据实际需要修改。
	 *
	 * @return SQLModel 返回数据插入用SQLModel
	 * @throws com.sino.base.exception.SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataCreateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
//		try {
		List sqlArgs = new ArrayList();
		AmsHouseInfoDTO amsLandInfo = (AmsHouseInfoDTO) dtoParameter;
		String sqlStr = "INSERT INTO "
				+ " AMS_LAND_INFO("
				+ " BARCODE,"
				+ " AREA_UNIT,"
				+ " LAND_AREA,"
				+ " REMARK,"
				+ " LAND_CERTFICATE_NO,"
				+ " LAND_ADDRESS,"
				+ " IS_RENT"
				+ ") VALUES ("
				+ " ?, ?, ?, ?, ?, ?, ?)";

		sqlArgs.add(amsLandInfo.getBarcode());
		sqlArgs.add(amsLandInfo.getAreaUnit());
		sqlArgs.add(amsLandInfo.getIsRent());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
//		} catch (CalendarException ex) {
//			ex.printLog();
//			throw new SQLModelException(ex);
//		}
		return sqlModel;
	}

  /**
	 * 功能：框架自动生成租赁房屋(EAM) AMS_HOUSE_INFO数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据更新用SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getDataUpdateModel() throws SQLModelException{
		SQLModel sqlModel = new SQLModel();
//		try {
			List sqlArgs = new ArrayList();
			AmsHouseInfoDTO amsHouseInfo = (AmsHouseInfoDTO) dtoParameter;
			String sqlStr = "UPDATE "
							+ " AMS_HOUSE_INFO"
							+ " SET"
							+ " HOUSE_ADDRESS = ?,"
							+ " FLOOR_NUMBER = ?,"
							+ " HOUSE_NO = ?,"
							+ " HOUSE_AREA = ?,"
							+ " HOUSE_CERTIFICATE_NO = ?,"
							+ " HOUSE_USAGE = ?,"
							+ " BUSINESS_AREA = ?,"
							+ " PRODUCE_HOSUSE_AREA = ?,"
							+ " PRODUCE_BASE_AREA = ?,"
							+ " OFFICE_AREA = ?,"
							+ " HOUSE_STATUS = ?,"
							+ " IS_RENT = ?,"
							+ " AREA_UNIT = ?,"
							+ " OFFICE_USAGE= ?,"
							+ " OFFICE_TYPE= ?,"
							+ " LAND_CERTFICATE_NO=?,"
							+ " OCCUPY_AREA =?,"
							+ " REMARK =?,"
                            + " LAND_TYPE=?"          //土地性质
							+ " WHERE"
							+ " BARCODE = ?";

		sqlArgs.add(amsHouseInfo.getHouseAddress());
		sqlArgs.add(amsHouseInfo.getFloorNumber());
		sqlArgs.add(amsHouseInfo.getHouseNo());
		sqlArgs.add(amsHouseInfo.getHouseArea());
		sqlArgs.add(amsHouseInfo.getHouseCertificateNo());
		sqlArgs.add(amsHouseInfo.getHouseUsage());
		sqlArgs.add(amsHouseInfo.getBusinessArea());
		sqlArgs.add(amsHouseInfo.getProduceHosuseArea());
		sqlArgs.add(amsHouseInfo.getProduceBaseArea());
		sqlArgs.add(amsHouseInfo.getOfficeArea());
		sqlArgs.add(amsHouseInfo.getHouseStatus());
		sqlArgs.add(amsHouseInfo.getIsRent());
		sqlArgs.add(amsHouseInfo.getAreaUnit());

		sqlArgs.add(amsHouseInfo.getOfficeUsage());
		sqlArgs.add(amsHouseInfo.getOfficeType());
		sqlArgs.add(amsHouseInfo.getLandCertficateNo());
		sqlArgs.add(amsHouseInfo.getOccupyArea());
		sqlArgs.add(amsHouseInfo.getHremark());
		sqlArgs.add(amsHouseInfo.getLandType());

		sqlArgs.add(amsHouseInfo.getBarcode());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
//		} catch (CalendarException ex) {
//			ex.printLog();
//			throw new SQLModelException(ex);
//		}
		return sqlModel;
	}
	/**
	 * 功能：框架自动生成租赁土地资产(EAM) AMS_LAND_INFO数据删除SQLModel，请根据实际需要修改。
	 *
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsHouseInfoDTO amsLandInfo = (AmsHouseInfoDTO) dtoParameter;
		String sqlStr = "DELETE FROM"
				+ " AMS_LAND_INFO"
				+ " WHERE"
				+ " BARCODE = ?";
		sqlArgs.add(amsLandInfo.getBarcode());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成租赁土地资产(EAM) AMS_LAND_INFO数据详细信息查询SQLModel，请根据实际需要修改。
	 *
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() {          //明细
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsHouseInfoDTO amsLandInfo = (AmsHouseInfoDTO) dtoParameter;
	   String sqlStr = " SELECT " +
						" EII.ITEM_CODE,\n" +
						" EII.SYSTEMID SYSTEM_ID,\n" +
						" EII.BARCODE,\n" +
						" ESI.ITEM_NAME,\n" +
						" ESI.ITEM_SPEC,\n" +
						" AHI.BARCODE,\n" +
						" ARI.RENT_PERSON,\n" +
						" ARI.RENT_DATE,\n" +
						" AHI.HOUSE_ADDRESS,\n" +
						" AHI.FLOOR_NUMBER,\n" +
						" AHI.HOUSE_NO,\n" +
						" AHI.HOUSE_AREA,\n" +
						" AHI.AREA_UNIT,\n" +
						" AHI.HOUSE_CERTIFICATE_NO,\n" +
						" AHI.HOUSE_USAGE,\n" +
						" AHI.BUSINESS_AREA,\n"+
						" AHI.PRODUCE_HOSUSE_AREA,\n" +
						" AHI.PRODUCE_BASE_AREA,\n" +
						" AHI.OFFICE_AREA,\n" +
						" AHI.HOUSE_STATUS,"+
						" AHI.OFFICE_USAGE,"+
						" AHI.OFFICE_TYPE,"+
						" AHI.REMARK HREMARK,"+
						" AHI.LAND_CERTFICATE_NO,"+
						" AHI.OCCUPY_AREA,"+
						" ARI.RENT_ID,"+
						" ARI.RENTAL,\n" +
						" ARI.MONEY_UNIT,\n" +
						" ARI.PAY_TYPE,\n" +
						" AHI.IS_RENT,\n" +
						" ARI.END_DATE,\n" +
                        " AHI.LAND_TYPE\n" +            //土地性质
						" FROM " +
						" ETS_ITEM_INFO   EII,\n" +
						" AMS_HOUSE_INFO  AHI,\n" +
						" ETS_SYSTEM_ITEM ESI,\n" +
						" AMS_RENT_INFO   ARI\n" +
						" WHERE " +
						" EII.BARCODE = AHI.BARCODE\n" +
						" AND ESI.ITEM_CODE = EII.ITEM_CODE\n" +
						" AND AHI.BARCODE *= ARI.BARCODE"
					  + " AND EII.BARCODE = ?";
		sqlArgs.add(amsLandInfo.getBarcode());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成租赁土地资产(EAM) AMS_LAND_INFO多条数据信息查询SQLModel，请根据实际需要修改。
	 *
	 * @return SQLModel 返回多条数据信息查询用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getMuxDataModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			AmsHouseInfoDTO amsLandInfo = (AmsHouseInfoDTO) dtoParameter;
			String sqlStr = "SELECT "
					+ " BARCODE,"
					+ " RENT_PERSON,"
					+ " RENT_DATE,"
					+ " AREA_UNIT,"
					+ " RENTAL,"
					+ " MONEY_UNIT,"
					+ " PAY_TYPE,"
					+ " END_DATE,"
					+ " LAND_AREA,"
					+ " RENT_USAGE,"
					+ " REMARK"
					+ " FROM"
					+ " AMS_LAND_INFO"
					+ " WHERE"
					+ " ( " + SyBaseSQLUtil.isNull() + "  OR BARCODE LIKE ?)"
					+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR RENT_PERSON LIKE ?)"
					+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR RENT_DATE LIKE ?)"
					+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR AREA_UNIT LIKE ?)"
					+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR RENTAL LIKE ?)"
					+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR MONEY_UNIT LIKE ?)"
					+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR PAY_TYPE LIKE ?)"
					+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR END_DATE LIKE ?)"
					+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR LAND_AREA LIKE ?)"
					+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR RENT_USAGE LIKE ?)"
					+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR REMARK LIKE ?)";
			sqlArgs.add(amsLandInfo.getBarcode());
			sqlArgs.add(amsLandInfo.getBarcode());
			sqlArgs.add(amsLandInfo.getRentPerson());
			sqlArgs.add(amsLandInfo.getRentPerson());
			sqlArgs.add(amsLandInfo.getRentDate());
			sqlArgs.add(amsLandInfo.getRentDate());
			sqlArgs.add(amsLandInfo.getAreaUnit());
			sqlArgs.add(amsLandInfo.getAreaUnit());
			sqlArgs.add(amsLandInfo.getRental());
			sqlArgs.add(amsLandInfo.getRental());
			sqlArgs.add(amsLandInfo.getMoneyUnit());
			sqlArgs.add(amsLandInfo.getMoneyUnit());
			sqlArgs.add(amsLandInfo.getPayType());
			sqlArgs.add(amsLandInfo.getPayType());
			sqlArgs.add(amsLandInfo.getEndDate());
			sqlArgs.add(amsLandInfo.getEndDate());
			sqlArgs.add(amsLandInfo.getRentUsage());
			sqlArgs.add(amsLandInfo.getRentUsage());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：根据外键关联字段 barcode 构造查询数据SQL。
	 * 框架自动生成数据租赁土地资产(EAM) AMS_LAND_INFO详细信息查询SQLModel，请根据实际需要修改。
	 * @param barcode String
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	private SQLModel getDataByBarcodeModel(String barcode) {     //明细用sql ?????
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT \n"
				+ " EII.SYSTEMID,\n"
				+ " ESI.ITEM_NAME,\n"
				+ " ESI.ITEM_SPEC, \n"
				+ " ALI.BARCODE,\n"
				+ " ALI.RENT_PERSON,\n"
				+ " ALI.RENT_DATE,\n"
				+ " ALI.AREA_UNIT,\n"
				+ " ALI.RENTAL,\n"
				+ " ALI.MONEY_UNIT,\n"
				+ " ALI.PAY_TYPE,\n"
				+ " ALI.END_DATE,\n"
				+ " ALI.LAND_AREA,\n"
				+ " ALI.RENT_USAGE,\n"
				+ " ALI.REMARK\n"
				+ " FROM "
				+ " ETS_ITEM_INFO EII, "
				+ " AMS_LAND_INFO ALI,\n"
				+ " ETS_SYSTEM_ITEM ESI\n"
				+ " WHERE "
				+ " EII.BARCODE = ALI.BARCODE"
				+ " AND ESI.ITEM_CODE = EII.ITEM_CODE"
				+ " AND BARCODE = ?";
		sqlArgs.add(barcode);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据外键获取数据
	 *
	 * @param foreignKey 传入的外键字段名称。
	 * @return SQLModel
	 */
	public SQLModel getDataByForeignKeyModel(String foreignKey) {
		SQLModel sqlModel = null;
		AmsHouseInfoDTO amsLandInfo = (AmsHouseInfoDTO) dtoParameter;
		if (foreignKey.equals("barcode")) {
			sqlModel = getDataByBarcodeModel(amsLandInfo.getBarcode());
		}
		return sqlModel;
	}

	/**
	 * 功能：根据外键关联字段 barcode 构造数据删除SQL。
	 * 框架自动生成数据租赁土地资产(EAM) AMS_LAND_INFO数据删除SQLModel，请根据实际需要修改。
	 * @param barcode String
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	private SQLModel getDeleteByBarcodeModel(String barcode) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT "
				+ " RENT_PERSON,"
				+ " RENT_DATE,"
				+ " AREA_UNIT,"
				+ " RENTAL,"
				+ " MONEY_UNIT,"
				+ " PAY_TYPE,"
				+ " END_DATE,"
				+ " LAND_AREA,"
				+ " RENT_USAGE,"
				+ " REMARK"
				+ " FROM"
				+ " AMS_LAND_INFO"
				+ " WHERE"
				+ " BARCODE = ?";
		sqlArgs.add(barcode);

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据外键字段删除数据
	 *
	 * @param foreignKey 传入的外键字段名称。
	 * @return SQLModel
	 */
	public SQLModel getDeleteByForeignKeyModel(String foreignKey) {
		SQLModel sqlModel = null;
		AmsHouseInfoDTO amsLandInfo = (AmsHouseInfoDTO) dtoParameter;
		if (foreignKey.equals("barcode")) {
			sqlModel = getDeleteByBarcodeModel(amsLandInfo.getBarcode());
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成租赁土地资产(EAM) AMS_LAND_INFO页面翻页查询SQLModel，请根据实际需要修改。
	 *
	 * @return SQLModel 返回页面翻页查询SQLModel
	 */
	public SQLModel getPageQueryModel() {   //查询使用的sql
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = " SELECT " +
						" EFA.ASSET_ID,\n" +
						" EFA.FA_CATEGORY1,\n" +
						" EFA.FA_CATEGORY2,\n" +
						" EFA.ASSETS_DESCRIPTION,\n" +
						" EFA.FA_CATEGORY_CODE,\n" +
						" EFA.UNIT_OF_MEASURE,\n" +
						" EFA.LIFE_IN_YEARS,\n" +
						" EFA.MODEL_NUMBER,\n" +
						" EFA.TAG_NUMBER,\n" +
						" EFA.CURRENT_UNITS,\n" +
						" EFA.ASSETS_LOCATION,\n" +
						" EFA.PROJECT_NAME,\n" +
						" EFA.DATE_PLACED_IN_SERVICE,\n" +
						" EFA.IS_RETIREMENTS,\n" +
						" EFA.ASSET_NUMBER,\n" +
						" EFA.COST,\n" +
						" EFA.DEPRN_COST,\n" +
						" EFA.ASSETS_STATUS,\n" +
						" EFA.BOOK_TYPE_CODE,\n" +
						" EFA.ASSIGNED_TO_NAME,\n" +
						" EFA.ASSIGNED_TO_NUMBER,\n" +
						" EFA.SEGMENT1,\n" +
						" EFA.SEGMENT2,\n" +
						" EFA.TRANS_TO_MIS,\n" +
						" EFA.TRANS_TO_MIS_DESC,\n" +
						" EFA.TRANS_TO_MIS_TAG,\n" +
						" EFA.TRANS_TO_MIS_LOC,\n" +
						" EFA.CODE_COMBINATION_ID,\n" +
						" EFA.COMPANY_CODE,\n" +
						" EFA.PROJECT_ID PROJECTID,\n" +
						" EFA.ORGANIZATION_ID,\n" +
						" EFA.SCRAP_VALUE,\n" +
						" EFA.DEPRECIATION_ACCOUNT,\n" +
						" EFA.ASSETS_LOCATION_CODE,\n" +
						" EFA.SEGMENT3,\n" +
						" EFA.SEGMENT2_TEMP\n" +
						" FROM " +
						" ETS_FA_ASSETS EFA\n" +
						" WHERE " +
//						" (EFA.SEGMENT2 LIKE '09-%' OR EFA.SEGMENT2 LIKE '07-71%' OR EFA.SEGMENT2 LIKE '80-03%')\n" + //移动集团目录09代表土地07-71代表房屋
						" EFA.SEGMENT2 LIKE '08-01%'\n" +
						" AND EFA.ASSETS_STATUS = 0" + //资产状态
						" AND (EFA.IS_RETIREMENTS =0 OR EFA.IS_RETIREMENTS =2)" + //是否报废
						" AND NOT EXISTS\n" +
//						" (SELECT 1 FROM ETS_ITEM_INFO EII WHERE EII.BARCODE = EFA.TAG_NUMBER)";
						" (SELECT 1 FROM AMS_HOUSE_INFO EII WHERE EII.BARCODE = EFA.TAG_NUMBER)";
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}


	/**
	 * 功能：框架自动生成租赁土地资产(EAM) AMS_LAND_INFO页面翻页查询SQLModel，请根据实际需要修改。
	 *
	 * @return SQLModel 返回页面翻页查询SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDispositionModel() throws SQLModelException {   //查询使用的sql
		SQLModel sqlModel = new SQLModel();
//      try {
			List sqlArgs = new ArrayList();
			AmsHouseInfoDTO amsHouseInfo = (AmsHouseInfoDTO) dtoParameter;
		String sqlStr = "SELECT " +
						" EII.ITEM_CODE,\n" +
						" ESI.ITEM_NAME,\n" +
						" ESI.ITEM_SPEC,\n" +
						" EII.SYSTEMID,\n" +
						" EII.ADDRESS_ID,\n"+
						" AHI.BARCODE,\n" +
						" AHI.HOUSE_CERTIFICATE_NO,\n" + //房产证号
						" --dbo.APP_GET_COUNTY_NAME(EII.ADDRESS_ID) COUNTY_NAME,\n"+
						" AHI.HOUSE_ADDRESS,\n" +
						" --CASE WHEN AHI.IS_RENT='Y' THEN '是' ELSE '否' END AS IS_RENT,\n"+
						" --ARI.RENT_PERSON,\n" +
						" --ARI.RENT_DATE,\n" +
						" AHI.FLOOR_NUMBER,\n" +
						" AHI.HOUSE_NO,\n" +
						" AHI.HOUSE_AREA,\n" +
						" AHI.HOUSE_USAGE,\n" +
						" AHI.BUSINESS_AREA,\n"+
						" AHI.PRODUCE_HOSUSE_AREA,\n" +
						" AHI.PRODUCE_BASE_AREA,\n" +
						" AHI.OFFICE_AREA,\n" +
						" AHI.HOUSE_STATUS,\n"+
						" AHI.OCCUPY_AREA,\n"+
						" dbo.APP_GET_FLEX_VALUE(AHI.AREA_UNIT, ?) AREA_UNIT,\n" +
						" --ARI.RENTAL,\n" +
						" --ARI.MONEY_UNIT,\n" +
						" --dbo.APP_GET_FLEX_VALUE(ARI.PAY_TYPE, ?) PAY_TYPE,\n" +
						" --ARI.END_DATE,\n" +
						" AHI.OFFICE_USAGE,\n" +
						" AHI.OFFICE_TYPE, \n" +
						" EII.ATTRIBUTE2, \n" +
                        " AHI.LAND_TYPE, \n" +             //土地性质
                        " AHI.LAND_CERTFICATE_NO \n" +     //土地证号
						" FROM \n" +
						" AMS_HOUSE_INFO AHI, \n " +
						" ETS_ITEM_INFO EII, \n" +
						" ETS_SYSTEM_ITEM ESI--, \n" +
						" --AMS_RENT_INFO ARI \n" +
						" WHERE 1 = 1\n" +
						//" AHI.BARCODE = EII.BARCODE \n" +
						" AND EII.BARCODE = AHI.BARCODE" +
						" AND EII.ITEM_CODE = ESI.ITEM_CODE \n" +
						" --AND AHI.BARCODE *= ARI.BARCODE \n" +
	                    //" AND EII.ATTRIBUTE2 = '未处理'" +
						" AND AHI.BARCODE LIKE dbo.NVL(LTRIM(?), AHI.BARCODE) \n" +
						" AND (? IS NULL OR ? = '' OR ESI.ITEM_NAME LIKE ?) \n" +
						" AND (? IS NULL OR ? = '' OR ESI.ITEM_SPEC LIKE ?) \n" +
						" AND (? IS NULL OR ? = '' OR OFFICE_USAGE LIKE ?) \n" +
						" AND (? IS NULL OR ? = '' OR OFFICE_TYPE LIKE ?) \n" +
						" AND (? IS NULL OR ? = '' OR EII.ATTRIBUTE2 LIKE ?) \n" +
                        " AND (? IS NULL OR ? = '' OR AHI.HOUSE_CERTIFICATE_NO = ?) \n" +
                        " AND (? IS NULL OR ? = '' OR AHI.LAND_TYPE = ?) \n" +
                        " AND (? IS NULL OR ? = '' OR AHI.LAND_CERTFICATE_NO = ?) \n";
                        //" AND ( ? IS NULL OR  AHI.HOUSE_STATUS = ?)";

			if (!StrUtil.isEmpty(amsHouseInfo.getTemp())) { //是否已处理
				sqlArgs.add(DictConstant.AREA_UNIT);
				sqlArgs.add(DictConstant.PAY_TYPE);
				sqlArgs.add(amsHouseInfo.getBarcode());
				sqlArgs.add(amsHouseInfo.getItemName());
				sqlArgs.add(amsHouseInfo.getItemName());
				sqlArgs.add(amsHouseInfo.getItemName());
				sqlArgs.add(amsHouseInfo.getItemSpec());
				sqlArgs.add(amsHouseInfo.getItemSpec());
				sqlArgs.add(amsHouseInfo.getItemSpec());

                sqlArgs.add(amsHouseInfo.getOfficeUsage());
                sqlArgs.add(amsHouseInfo.getOfficeUsage());
                sqlArgs.add(amsHouseInfo.getOfficeUsage());
                sqlArgs.add(amsHouseInfo.getOfficeType());
                sqlArgs.add(amsHouseInfo.getOfficeType());
                sqlArgs.add(amsHouseInfo.getOfficeType());

                sqlArgs.add(amsHouseInfo.getTemp());
				sqlArgs.add(amsHouseInfo.getTemp());
				sqlArgs.add(amsHouseInfo.getTemp());
                sqlArgs.add(amsHouseInfo.getHouseCertificateNo());
                sqlArgs.add(amsHouseInfo.getHouseCertificateNo());
                sqlArgs.add(amsHouseInfo.getHouseCertificateNo());
                
                sqlArgs.add(amsHouseInfo.getLandType());
                sqlArgs.add(amsHouseInfo.getLandType());
                sqlArgs.add(amsHouseInfo.getLandType());
                sqlArgs.add(amsHouseInfo.getLandCertficateNo());
                sqlArgs.add(amsHouseInfo.getLandCertficateNo());
                sqlArgs.add(amsHouseInfo.getLandCertficateNo());
            }else{
				sqlArgs.add(DictConstant.AREA_UNIT);
				sqlArgs.add(DictConstant.PAY_TYPE);
				sqlArgs.add(amsHouseInfo.getBarcode());
				sqlArgs.add(amsHouseInfo.getItemName());
				sqlArgs.add(amsHouseInfo.getItemName());
				sqlArgs.add(amsHouseInfo.getItemName());
				sqlArgs.add(amsHouseInfo.getItemSpec());
				sqlArgs.add(amsHouseInfo.getItemSpec());
				sqlArgs.add(amsHouseInfo.getItemSpec());
				sqlArgs.add("");
				sqlArgs.add("");
				sqlArgs.add("");
				sqlArgs.add("");
				sqlArgs.add("");
				sqlArgs.add("");
				sqlArgs.add(amsHouseInfo.getTemp());
				sqlArgs.add(amsHouseInfo.getTemp());
				sqlArgs.add(amsHouseInfo.getTemp());
                sqlArgs.add(amsHouseInfo.getHouseCertificateNo());
                sqlArgs.add(amsHouseInfo.getHouseCertificateNo());
                sqlArgs.add(amsHouseInfo.getHouseCertificateNo());
                sqlArgs.add(amsHouseInfo.getLandType());
                sqlArgs.add(amsHouseInfo.getLandType());
                sqlArgs.add(amsHouseInfo.getLandType());
                sqlArgs.add(amsHouseInfo.getLandCertficateNo());
                sqlArgs.add(amsHouseInfo.getLandCertficateNo());
                sqlArgs.add(amsHouseInfo.getLandCertficateNo());
            }

        if(amsHouseInfo.getProvince().equals("N")){
        	sqlStr += "AND  EII.ORGANIZATION_ID = ?";
        	sqlArgs.add(userAccount.getOrganizationId());
        }


//          sqlStr += "AND  EII.ORGANIZATION_ID = ?";
//          sqlArgs.add(userAccount.getOrganizationId());


//		   if (amsHouseInfo.getCertificate().equals("Y")) {
//			sqlStr += " AND AHI.HOUSE_CERTIFICATE_NO IS NOT NULL";
//			} else if (amsHouseInfo.getCertificate().equals("N")) {
//			sqlStr += " AND AHI.HOUSE_CERTIFICATE_NO IS  NULL";
//			}
//		   if(amsHouseInfo.getCertificate().equals("Y")){
//			 sqlStr += " AND (? IS NULL OR  AHI.HOUSE_CERTIFICATE_NO = ?)";
//			 sqlArgs.add(amsHouseInfo.getHouseCertificateNo());
//			 sqlArgs.add(amsHouseInfo.getHouseCertificateNo());
//		   }
//		  if (amsHouseInfo.getHasLandNo().equals("Y")) {
//			sqlStr += " AND AHI.OCCUPY_AREA IS NOT NULL";
//			} else if (amsHouseInfo.getCertificate().equals("N")) {
//			sqlStr += " AND AHI.OCCUPY_AREA IS  NULL";
//			}
//		   if(amsHouseInfo.getHasLandNo().equals("Y")){
//			 sqlStr += " AND (? IS NULL OR AHI.LAND_CERTFICATE_NO = ?)";
//			 sqlArgs.add(amsHouseInfo.getLandCertficateNo());
//			 sqlArgs.add(amsHouseInfo.getLandCertficateNo());
//		   }

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：查询房屋土地的信息。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getHouseLandModel() throws SQLModelException {   //查询房屋土地信息使用的sql
		SQLModel sqlModel = new SQLModel();
//      try {
			List sqlArgs = new ArrayList();
			AmsHouseInfoDTO amsHouseInfo = (AmsHouseInfoDTO) dtoParameter;
			String sqlStr = "SELECT " +
					" EII.ITEM_CODE,\n" +
					" ESI.ITEM_NAME,\n" +
					" ESI.ITEM_SPEC,\n" +
					" EII.SYSTEMID,\n" +
					" EII.ADDRESS_ID,\n"+
					" AHI.BARCODE,\n" +
					" AHI.HOUSE_CERTIFICATE_NO,\n" +
                    //" AMS_PUB_PKG.GET_COUNTY_NAME(EII.ADDRESS_ID) COUNTY_NAME,\n"+
					" AHI.HOUSE_ADDRESS,\n" +
					" CASE WHEN AHI.IS_RENT='Y' THEN '是' ELSE '否' END AS IS_RENT,\n" +
					" ARI.RENT_PERSON,\n" +
					" ARI.RENT_DATE,\n" +
					" AHI.FLOOR_NUMBER,\n" +
					" AHI.HOUSE_NO,\n" +
					" AHI.HOUSE_AREA,\n" +
					" AHI.HOUSE_USAGE,\n" +
					" AHI.BUSINESS_AREA,\n"+
					" AHI.PRODUCE_HOSUSE_AREA,\n" +
					" AHI.PRODUCE_BASE_AREA,\n" +
					" AHI.OFFICE_AREA,\n" +
					" AHI.HOUSE_STATUS,"+
					" AHI.OCCUPY_AREA,"+
					" dbo.APP_GET_FLEX_VALUE(AHI.AREA_UNIT, ?) AREA_UNIT,\n" +
					" ARI.RENTAL,\n" +
					" ARI.MONEY_UNIT,\n" +
					" dbo.APP_GET_FLEX_VALUE(ARI.PAY_TYPE, ?) PAY_TYPE,\n" +
					" ARI.END_DATE,\n" +
					" AHI.OFFICE_USAGE,\n" +
					" AHI.OFFICE_TYPE," +
					" EII.ATTRIBUTE2" +
					" FROM " +
					" AMS_HOUSE_INFO AHI,ETS_ITEM_INFO EII,\n" +
					" ETS_SYSTEM_ITEM ESI,\n" +
					" AMS_RENT_INFO ARI\n" +
					" WHERE " +
					" AHI.BARCODE=EII.BARCODE \n" +
					" AND EII.ITEM_CODE=ESI.ITEM_CODE \n" +
					" AND AHI.BARCODE *= ARI.BARCODE"
					+ " AND EII.ATTRIBUTE2 = '已处理'"
					+ " AND ( AHI.BARCODE LIKE dbo.NVL(?,AHI.BARCODE))"
					+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_NAME LIKE ?)\n"
					+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?)\n "
					+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR OFFICE_USAGE LIKE ?)\n"
					+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR OFFICE_TYPE LIKE ?)\n"
					+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.ATTRIBUTE2 LIKE ?)\n" ;
					//+ " AND (?='' OR AHI.OFFICE_USAGE=?)"//基站?
					//+ " AND (?='' OR AHI.OFFICE_TYPE=?)"//土地?
                    //+  "AND ( " + SyBaseSQLUtil.isNull() + "  OR )";
                    //+ " AND (  " + SyBaseSQLUtil.isNull() + "  OR  AHI.HOUSE_STATUS = ?)";

		  if (!StrUtil.isEmpty(amsHouseInfo.getTemp())) {
				sqlArgs.add(DictConstant.AREA_UNIT);
				sqlArgs.add(DictConstant.PAY_TYPE);
				sqlArgs.add(amsHouseInfo.getBarcode());
				sqlArgs.add(amsHouseInfo.getItemName());
				sqlArgs.add(amsHouseInfo.getItemName());
				sqlArgs.add(amsHouseInfo.getItemSpec());
				sqlArgs.add(amsHouseInfo.getItemSpec());
				sqlArgs.add(amsHouseInfo.getBts());
				sqlArgs.add(amsHouseInfo.getBts());
				sqlArgs.add(amsHouseInfo.getCategory());
				sqlArgs.add(amsHouseInfo.getCategory());
				sqlArgs.add(amsHouseInfo.getTemp());
				sqlArgs.add(amsHouseInfo.getTemp());
			}else{
				sqlArgs.add(DictConstant.AREA_UNIT);
				sqlArgs.add(DictConstant.PAY_TYPE);
				sqlArgs.add(amsHouseInfo.getBarcode());
				sqlArgs.add(amsHouseInfo.getItemName());
				sqlArgs.add(amsHouseInfo.getItemName());
				sqlArgs.add(amsHouseInfo.getItemSpec());
				sqlArgs.add(amsHouseInfo.getItemSpec());
				/*sqlArgs.add("");
				sqlArgs.add("");
				sqlArgs.add("");
				sqlArgs.add("");*/
				sqlArgs.add(amsHouseInfo.getBts());
				sqlArgs.add(amsHouseInfo.getBts());
				sqlArgs.add(amsHouseInfo.getCategory());
				sqlArgs.add(amsHouseInfo.getCategory());
				sqlArgs.add(amsHouseInfo.getTemp());
				sqlArgs.add(amsHouseInfo.getTemp());
			}
			if (amsHouseInfo.getCertificate().equals("Y")) {
				sqlStr += " AND  " + SyBaseSQLUtil.isNotNull("AHI.HOUSE_CERTIFICATE_NO") + " ";
			} else if (amsHouseInfo.getCertificate().equals("N")) {
				sqlStr += " AND AHI.HOUSE_CERTIFICATE_NO  " + SyBaseSQLUtil.isNullNoParam() + " ";
			}
			if(amsHouseInfo.getCertificate().equals("Y")){
				sqlStr += " AND ( " + SyBaseSQLUtil.isNull() + "  OR  AHI.HOUSE_CERTIFICATE_NO = ?)";
				sqlArgs.add(amsHouseInfo.getHouseCertificateNo());
				sqlArgs.add(amsHouseInfo.getHouseCertificateNo());
			}
			if (amsHouseInfo.getHasLandNo().equals("Y")) {
				sqlStr += " AND  (  AHI.OCCUPY_AREA IS NOT NULL AND CONVERT(VARCHAR,AHI.OCCUPY_AREA) != '' ) ";
			  
			} else if (amsHouseInfo.getCertificate().equals("N")) {
				sqlStr += " AND CONVERT(VARCHAR,AHI.OCCUPY_AREA)  " + SyBaseSQLUtil.isNullNoParam() + " ";
			}
			if(amsHouseInfo.getHasLandNo().equals("Y")){
				sqlStr += " AND ( " + SyBaseSQLUtil.isNull() + "  OR AHI.LAND_CERTFICATE_NO = ?)";
				sqlArgs.add(amsHouseInfo.getLandCertficateNo());
				sqlArgs.add(amsHouseInfo.getLandCertficateNo());
			}
			if(amsHouseInfo.getProvince().equals("N")){
				sqlStr += "AND  EII.ORGANIZATION_ID = ?";
				sqlArgs.add(userAccount.getOrganizationId());
			}

            sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}


	/**
	 * 功能：获取判断当前用户是否有权限执行数据编辑操作。BARCODE 的存在性
	 * @param barcode String
	 * @return SQLModel
	 */
	public SQLModel getVerifyBarcodeModel(String barcode) {
		SQLModel sqlModel = new SQLModel();
		List strArg = new ArrayList();
		String strSql = "SELECT "
				+ " EII.*"
				+ " FROM"
				+ " ETS_ITEM_INFO  EII\n"
				+ " WHERE"
				+ " EII.BARCODE = ?";
		strArg.add(barcode);
		sqlModel.setSqlStr(strSql);
		sqlModel.setArgs(strArg);
		return sqlModel;
	}

	/**
	 * 功能:插入到表ams_rent_info
	 * @return
	 * @throws SQLModelException
	 */
	public SQLModel doCreatRentData() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			AmsHouseInfoDTO amsLandInfoDTO = (AmsHouseInfoDTO) dtoParameter;
			String sqlStr = "INSERT INTO" +
					"  AMS_RENT_INFO\n" +
					"  (RENT_ID,\n" +
					"   BARCODE,\n" +
					"   RENT_PERSON,\n" +
					"   RENT_DATE,\n" +
					"   RENTAL,\n" +
					"   MONEY_UNIT,\n" +
					"   PAY_TYPE,\n" +
					"   END_DATE,\n" +
					"   RENT_USAGE,\n" +
					"   CREATION_DATE,\n" +
					"   CREATED_BY)\n" +
					"VALUES\n" +
					"  ( NEWID() , ?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), ?)";

			sqlArgs.add(amsLandInfoDTO.getBarcode());
			sqlArgs.add(amsLandInfoDTO.getRentPerson());
			sqlArgs.add(amsLandInfoDTO.getRentDate());
			sqlArgs.add(amsLandInfoDTO.getRental());
			sqlArgs.add(amsLandInfoDTO.getMoneyUnit());
			sqlArgs.add(amsLandInfoDTO.getPayType());
			sqlArgs.add(amsLandInfoDTO.getEndDate());
			sqlArgs.add(amsLandInfoDTO.getRentUsage());
			sqlArgs.add(userAccount.getOrganizationId());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：删除ams_rent_info的数据
	 * @return
	 */
	public SQLModel doDeleteRentData() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsHouseInfoDTO amsLandInfoDTO = (AmsHouseInfoDTO) dtoParameter;
		String sqlStr = "DELETE "
				+ " FROM"
				+ " AMS_RENT_INFO"
				+ " WHERE"
				+ " RENT_ID = ?";
		sqlArgs.add(amsLandInfoDTO.getRentId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);

		return sqlModel;
	}

	/**
	 * 功能：创建新的房屋土地资产。
	 * @return SQLModel
	 */
	public SQLModel getItemCreateModel(EtsItemInfoDTO etsItemInfo) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
		List sqlArgs = new ArrayList();
		String sqlStr = "INSERT INTO ETS_ITEM_INFO ("
						+ " SYSTEMID,"
						+ " BARCODE,"
						+ " ITEM_CODE,"
                        + " PROJECTID,"
						+ " ADDRESS_ID,"
						+ " RESPONSIBILITY_USER,"
						+ " RESPONSIBILITY_DEPT,"
						+ " ORGANIZATION_ID,"
						+ " REMARK,"
                        + " START_DATE,"
						+ " CREATED_BY,"
						+ " ATTRIBUTE2,"
						+ " FINANCE_PROP"
						+ ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,'未处理',?)";

        sqlArgs.add(etsItemInfo.getSystemId());
        sqlArgs.add(etsItemInfo.getBarcode());
		sqlArgs.add(etsItemInfo.getItemCode());
		sqlArgs.add(etsItemInfo.getProjectid());
		sqlArgs.add(etsItemInfo.getAddressId());
		sqlArgs.add(etsItemInfo.getResponsibilityUser());
		sqlArgs.add(etsItemInfo.getResponsibilityDept());
		sqlArgs.add(etsItemInfo.getOrganizationId());
		sqlArgs.add(etsItemInfo.getRemark());
		sqlArgs.add(etsItemInfo.getStartDate());
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(etsItemInfo.getFinanceProp());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
       } catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
        return sqlModel;
	}

    /**
     * 功能：创建项目工程。
     * @return SQLModel
     */
    public SQLModel getProjectCreateModel(EtsItemInfoDTO itemDTO, EtsFaAssetsDTO faDTO) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO "
                + " ETS_PA_PROJECTS_ALL("
                + " PROJECT_ID,"
                + " NAME,"
                + " SEGMENT1,"
                + " PROJECT_TYPE,"
                + " PROJECT_STATUS_CODE,"
                + " START_DATE,"
                + " COMPLETION_DATE,"
                + " ENABLED_FLAG,"
                + " SOURCE,"
                + " CREATION_DATE,"
                + " CREATED_BY,"
                + " MIS_PROJECT_ID"
                + ") VALUES ("
                + "  NEWID() , ?, ?, ?, 'APPROVED', ?, ?, 'Y', 'EAM', GETDATE(), ?,?)";

        sqlArgs.add(faDTO.getProjectName());
//        sqlArgs.add(etsPaProjectsAll.getSegment1());
//        sqlArgs.add(etsPaProjectsAll.getProjectType());
//        sqlArgs.add(etsPaProjectsAll.getStartDate());
//        sqlArgs.add(etsPaProjectsAll.getCompletionDate());
        sqlArgs.add(userAccount.getUserId());
//        sqlArgs.add(etsPaProjectsAll.getMisProjectId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


    /**
	 * 功能：创建新的房屋土地资产的详细信息。
	 * @return SQLModel
	 */
	public SQLModel insertHouse(AmsHouseInfoDTO amsHouseInfo){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "INSERT INTO "
							+ " AMS_HOUSE_INFO("
							+ " BARCODE,"
//							+ " RENT_PERSON,"
//							+ " RENT_DATE,"
							+ " HOUSE_ADDRESS,"
							+ " FLOOR_NUMBER,"
							+ " HOUSE_NO,"
							+ " HOUSE_AREA,"
							+ " AREA_UNIT,"
							+ " REMARK,"
//							+ " RENTAL,"
//							+ " MONEY_UNIT,"
//							+ " PAY_TYPE,"
//                            + " END_DATE,"
							+ " HOUSE_CERTIFICATE_NO,"
							+ " HOUSE_USAGE,"
							+ " BUSINESS_AREA,"
							+ " PRODUCE_HOSUSE_AREA,"
							+ " PRODUCE_BASE_AREA,"
							+ " OFFICE_AREA,"
							+ " HOUSE_STATUS,"
							+ " IS_RENT"
							+ ") VALUES ("
							+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				sqlArgs.add(amsHouseInfo.getBarcode());
//			sqlArgs.add(amsHouseInfo.getRentPerson());
//			sqlArgs.add(amsHouseInfo.getRentDate());
			sqlArgs.add(amsHouseInfo.getHouseAddress());
			sqlArgs.add(amsHouseInfo.getFloorNumber());
			sqlArgs.add(amsHouseInfo.getHouseNo());
			sqlArgs.add(amsHouseInfo.getHouseArea());
			sqlArgs.add(amsHouseInfo.getAreaUnit());
			sqlArgs.add(amsHouseInfo.getHremark());
//			sqlArgs.add(amsHouseInfo.getRental());
//			sqlArgs.add(amsHouseInfo.getMoneyUnit());
//			sqlArgs.add(amsHouseInfo.getPayType());
//			sqlArgs.add(amsHouseInfo.getEndDate());
		sqlArgs.add(amsHouseInfo.getHouseCertificateNo());
		sqlArgs.add(amsHouseInfo.getHouseUsage());
		sqlArgs.add(amsHouseInfo.getBusinessArea());
		sqlArgs.add(amsHouseInfo.getProduceHosuseArea());
		sqlArgs.add(amsHouseInfo.getProduceBaseArea());
		sqlArgs.add(amsHouseInfo.getOfficeArea());
		sqlArgs.add(amsHouseInfo.getHouseStatus());
//        System.out.println("amsHouseInfo.getHouseStatus()="+amsHouseInfo.getHouseStatus());
		sqlArgs.add(amsHouseInfo.getIsRent());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}


	/**
		* 功能：查找mis员工信息。
		* @return SQLModel
		*/
	   public SQLModel getMISemployee(String employeeNumber){
		   SQLModel sqlModel = new SQLModel();
		   List sqlArgs = new ArrayList();
		   String sqlStr = "SELECT" +
						   "  AME.EMPLOYEE_ID, " +
						   "  AME.DEPT_CODE\n" +
						   "  FROM " +
						   "  AMS_MIS_EMPLOYEE AME\n" +
						   "  WHERE AME.EMPLOYEE_NUMBER = ?";
		   sqlArgs.add(employeeNumber);

		   sqlModel.setSqlStr(sqlStr);
		   sqlModel.setArgs(sqlArgs);
		   return sqlModel;
	   }

	  /**
		* 功能：获得addressId。
		* @return SQLModel
		*/
	   public SQLModel getLocId(String locCode, int orgId){
		   SQLModel sqlModel = new SQLModel();
		   List sqlArgs = new ArrayList();
		   String sqlStr = " SELECT " +
						   " AOA.ADDRESS_ID\n" +
						   " FROM " +
						   " ETS_OBJECT EO, " +
						   " AMS_OBJECT_ADDRESS AOA\n" +
						   " WHERE " +
						   " AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
						   " AND EO.WORKORDER_OBJECT_CODE = ?\n" +
						   " AND EO.ORGANIZATION_ID = ?";
		   sqlArgs.add(locCode);
		   sqlArgs.add(orgId);
		   sqlModel.setSqlStr(sqlStr);
		   sqlModel.setArgs(sqlArgs);
		   return sqlModel;
	   }


    /**
          * 功能：获得projectId。
          * @return SQLModel
          */
         public SQLModel getProId(String misProjId){
             SQLModel sqlModel = new SQLModel();
             List sqlArgs = new ArrayList();
             String sqlStr = " SELECT EPPA.PROJECT_ID  FROM ETS_PA_PROJECTS_ALL EPPA WHERE EPPA.MIS_PROJECT_ID = ?";
             sqlArgs.add(misProjId);
             sqlModel.setSqlStr(sqlStr);
             sqlModel.setArgs(sqlArgs);
             return sqlModel;
         }


    /**
      * 功能：判断barcode。
      * @return SQLModel
     * @param barcode
      */
     public SQLModel hasBarcode(String barcode){
         SQLModel sqlModel = new SQLModel();
         List sqlArgs = new ArrayList();
         String sqlStr = "SELECT 1 FROM ETS_ITEM_INFO EII WHERE EII.BARCODE = ?";
         sqlArgs.add(barcode);
         sqlModel.setSqlStr(sqlStr);
         sqlModel.setArgs(sqlArgs);
         return sqlModel;
     }

     /**
      * 功能：判断projectId的存在性。
      * @return SQLModel
      */
     public SQLModel hasProjId(String projectId){
         SQLModel sqlModel = new SQLModel();
         List sqlArgs = new ArrayList();
         String sqlStr = "SELECT * FROM ETS_PA_PROJECTS_ALL EPPA WHERE EPPA.MIS_PROJECT_ID = ?";
         sqlArgs.add(projectId);
         sqlModel.setSqlStr(sqlStr);
         sqlModel.setArgs(sqlArgs);
         return sqlModel;
     }


    /**
	 * 功能：改变ets_item_info的属性为已处置。
	 * @return SQLModel 返回数据更新用SQLModel
	 */
	public SQLModel getEiiData(String barcode){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
//        EtsItemInfoDTO etsItemInfo = (EtsItemInfoDTO)dtoParameter;
		String sqlStr = "UPDATE "
			+ " ETS_ITEM_INFO"
			+ " SET"
//            + " BARCODE = ?,"
//            + " ITEM_QTY = 1,"
//            + " ITEM_CODE = ?,"
//            + " ADDRESS_ID=?,"
			+ " ATTRIBUTE2 ='已处理',"
			+ " LAST_UPDATE_DATE = GETDATE(),"
			+ " LAST_UPDATE_BY = ?"
			+ " WHERE"
			+ " BARCODE = ?";
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(barcode);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	 /**
	 * 功能：判断ams_house_info的用途和类型是否为空。
	 * @return SQLModel 返回数据更新用SQLModel
	 */
	public SQLModel isTempDate(String barcode){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr =  "SELECT 1\n" +
				" FROM " +
				" AMS_HOUSE_INFO AHI\n" +
				" WHERE " +
				//" AHI.OFFICE_USAG " + SyBaseSQLUtil.isNull() + " \n" +
				//" AND AHI.OFFICE_TYP " + SyBaseSQLUtil.isNull() + " \n" +
				//" AND " +
				"AHI.BARCODE = ?";
		sqlArgs.add(barcode);

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：插入到表 ams_house_user
	 * @param dto
	 * @return
	 */
	  public SQLModel insertUsesInfo(AmsHouseUsesDTO dto )  {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr ="INSERT INTO AMS_HOUSE_USES\n" +
				"  (USES_ID,\n" +
				"   BARCODE,\n" +
				"   USAGE,\n" +
				"   AREA,\n" +
				"   REMARK)\n" +
				"VALUES\n" +
				"  ( NEWID() ,\n" +
				"   ?,\n" +
				"   ?,\n" +
				"   ?,\n" +
				"   ?)";
		sqlArgs.add(dto.getBarcode());
		sqlArgs.add(dto.getUsage());
		sqlArgs.add(dto.getArea());
		sqlArgs.add(dto.getRemark());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);

		return sqlModel;
	}

	  public SQLModel getUsesInfo()  {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsHouseInfoDTO amsHouseInfoDTO = (AmsHouseInfoDTO) dtoParameter;
		String sqlStr ="SELECT AHU.* FROM AMS_HOUSE_USES AHU WHERE AHU.BARCODE = ?";
		sqlArgs.add(amsHouseInfoDTO.getBarcode());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);

		return sqlModel;
	}

   /**
		* 功能：获得countyCode。
		* @return SQLModel
		*/
	   public SQLModel getCount(String costCode){
		   SQLModel sqlModel = new SQLModel();
		   List sqlArgs = new ArrayList();
		   String sqlStr = " SELECT \n" +
                           " EC.COUNTY_CODE\n" +
                           " FROM   \n" +
                           " ETS_COUNTY         EC,\n" +
                           " AMS_MIS_COST_MATCH AMCM\n" +
                           " WHERE\n" +
                           " EC.COUNTY_CODE_MIS = AMCM.COUNTY_CODE_MIS\n" +
                           " AND AMCM.COST_CODE = ?";
		   sqlArgs.add(costCode);
		   sqlModel.setSqlStr(sqlStr);
		   sqlModel.setArgs(sqlArgs);
		   return sqlModel;
	   }

   /**
	 * 功能：清空ams_house_info的表。
	 * @return SQLModel 返回数据更新用SQLModel
	 */
	public SQLModel updateNull(String barcode){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
//        EtsItemInfoDTO etsItemInfo = (EtsItemInfoDTO)dtoParameter;
		 String sqlStr = "UPDATE "
							+ " AMS_HOUSE_INFO"
							+ " SET"
							+ " FLOOR_NUMBER = '',"
							+ " HOUSE_NO = '',"
							+ " HOUSE_AREA = '',"
							+ " HOUSE_CERTIFICATE_NO = '',"
							+ " HOUSE_USAGE = '',"
							+ " BUSINESS_AREA = '',"
							+ " PRODUCE_HOSUSE_AREA = '',"
							+ " PRODUCE_BASE_AREA = '',"
							+ " OFFICE_AREA = '',"
							+ " HOUSE_STATUS = '',"
							+ " IS_RENT = '',"
							+ " AREA_UNIT = '',"
							+ " OFFICE_USAGE= '',"
							+ " OFFICE_TYPE= '',"
							+ " LAND_CERTFICATE_NO='',"
							+ " OCCUPY_AREA ='',"
							+ " REMARK =''"
							+ " WHERE"
							+ " BARCODE = ?";

		sqlArgs.add(barcode);

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

  /**
	 * 功能：更新ets_item_info的状态为"未处理"。
	 * @param  barcodes
	 * @return SQLModel 返回数据用SQLModel
	 */
	public SQLModel UpdateBarsModel(String[] barcodes) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String orderno = ArrUtil.arrToSqlStr(barcodes);
		String sqlStr = "UPDATE "
						+ " ETS_ITEM_INFO "
						+ " SET"
						+ " ATTRIBUTE2='未处理' "
						+ " WHERE"
						+ " SYSTEMID IN (" + orderno + ")";
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}


//       /**
//     * 功能：框架自动生成资产地点表(EAM) ETS_OBJECT数据插入SQLModel，请根据实际需要修改。
//     * @return SQLModel 返回数据插入用SQLModel
//     */
//    public SQLModel getAOACreateModel(){
//        SQLModel sqlModel = new SQLModel();
//        List sqlArgs = new ArrayList();
//        EtsObjectDTO etsObject = (EtsObjectDTO)dtoParameter;
//        String sqlStr = "INSERT INTO "
//            + " EAM_OBJECT_ADDRESS("
//            + " ADDRESS_ID,"
//            + " OBJECT_NO,"
//            + " ORGANIZATION_ID"
//            + ") VALUES ("
//            + "  " + SyBaseSQLUtil.getNewID( "EAM_OBJECT_ADDRESS_S" ) + " , ?, ?)";
//
//        sqlArgs.add(etsObject.getWorkorderObjectNo());
//        sqlArgs.add(sfUser.getOrganizationId());
//
//        sqlModel.setSqlStr(sqlStr);
//        sqlModel.setArgs(sqlArgs);
//        return sqlModel;
//    }
}
