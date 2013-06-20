package com.sino.ams.system.intangible.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.intangible.dto.IntangibleDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * <p>Title: EtsObjectModel</p>
 * <p>Description:程序自动生成SQL构造器“EtsObjectModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Zyun
 * @version 1.0
 */


public class IntangibleModel extends BaseSQLProducer {

	private SfUserDTO sfUser = null;

	/**
	 * 功能：资产地点表(EAM) ETS_OBJECT 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EtsObjectDTO 本次操作的数据
	 */
	public IntangibleModel(SfUserDTO userAccount, IntangibleDTO dtoParameter) {
		super(userAccount, dtoParameter);
		sfUser = userAccount;
	}

	/**
	 * 功能：执行新增数据操作。
	 * @return SQLModel 返回数据插入用SQLModel
	 */
	public SQLModel getDataCreateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		IntangibleDTO etsObject = (IntangibleDTO) dtoParameter;
		String sqlStr = "INSERT INTO "
						+ " ETS_OBJECT("
						+ " WORKORDER_OBJECT_NO,"
						+ " WORKORDER_OBJECT_CODE,"
						+ " WORKORDER_OBJECT_NAME,"
						+ " WORKORDER_OBJECT_LOCATION,"
						+ " ORGANIZATION_ID,"
						+ " COUNTY_CODE,"
						+ " REMARK,"
						+ " OBJECT_CATEGORY,"
						+ " ISALL,"
						+ " IS_TEMP_ADDR,"
						+ " CREATION_DATE,"
						+ " CREATED_BY,"
						+ " PROJECT_ID"
						+ ") VALUES ("
						+ "  NEWID() , ?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), ?, ?)";


		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

/**
	 * 功能：执行数据修改操作。
	 * @return SQLModel 返回数据更新用SQLModel
	 */
	public SQLModel getDataUpdateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		IntangibleDTO etsObject = (IntangibleDTO) dtoParameter;
		String sqlStr = "UPDATE ETS_OBJECT"
						+ " SET"
						+ " WORKORDER_OBJECT_CODE = ?,"
						+ " WORKORDER_OBJECT_NAME = ?,"
						+ " WORKORDER_OBJECT_LOCATION = ?,"
						+ " COUNTY_CODE = ?,"
						+ " REMARK = ?,"
						+ " ISALL = ?,"
						+ " IS_TEMP_ADDR = ?,"
						+ " LAST_UPDATE_DATE = GETDATE(),"
						+ " LAST_UPDATE_BY = ?,"
						+ " PROJECT_ID = ?"
						+ " WHERE"
						+ " WORKORDER_OBJECT_NO = ?";


		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：执行单个失效操作。
	 * @return SQLModel 返回数据失效用SQLModel
	 */
	public SQLModel getDataDeleteModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		IntangibleDTO etsObject = (IntangibleDTO) dtoParameter;
		String sqlStr = "UPDATE "
						+ " ETS_OBJECT"
						+ " SET"
						+ " DISABLE_DATE = GETDATE()"
						+ " WHERE"
						+ " WORKORDER_OBJECT_NO = ?";
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：执行明细操作用的SQLMODEL。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() { //点明细
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		IntangibleDTO etsObject = (IntangibleDTO) dtoParameter;
		String sqlStr = "SELECT "
						+ " EO.WORKORDER_OBJECT_NO,"
						+ " EO.WORKORDER_OBJECT_CODE,"
						+ " EO.WORKORDER_OBJECT_NAME,"
						+ " EO.WORKORDER_OBJECT_LOCATION,"
						+ " EO.ORGANIZATION_ID,"
						+ " EO.COUNTY_CODE,"
						+ " EO.DISABLE_DATE,"
						+ " EO.REMARK,"
						+ " EO.OBJECT_CATEGORY,"
						+ " EO.ISALL,"
						+ " EO.IS_TEMP_ADDR,"
						+ " EO.CREATION_DATE,"
						+ " EO.CREATED_BY,"
						+ " EO.LAST_UPDATE_DATE,"
						+ " EO.LAST_UPDATE_BY,"
						+ " EO.PROJECT_ID,"
                        + " AMS_PUB_PKG.GET_PROJECT_NAME(EO.PROJECT_ID) PROJECT_NAME"
						+ " FROM"
						+ " ETS_OBJECT EO"
						+ " WHERE"
						+ " EO.WORKORDER_OBJECT_NO = ?";

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}


	/**
	 * 功能：查询用的SQLMOL。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 */
	public SQLModel getPageQueryModel() { //查询
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		IntangibleDTO intangDTO = (IntangibleDTO) dtoParameter;
//		String sqlStr = "SELECT" +
//                "         EFA.ASSET_NUMBER,\n" +
//                "         EII.BARCODE,\n" +
//                "         ESI.ITEM_CODE,\n" +
//                "         ESI.ITEM_NAME," +
//                "         ESI.ITEM_SPEC,\n" +
//                "         EFA.COST\n" +
//                "    FROM " +
//                "         ETS_ITEM_INFO   EII,\n" +
//                "         ETS_SYSTEM_ITEM ESI,\n" +
//                "         ETS_FA_ASSETS   EFA,\n" +
//                "         ETS_ITEM_MATCH  EIM\n" +
//                "   WHERE " +
//                "         EFA.ASSET_ID = EIM.ASSET_ID\n" +
//                "     AND EIM.SYSTEMID = EII.SYSTEMID\n" +
//                "     AND ESI.ITEM_CODE = EII.ITEM_CODE" +
//                "     AND ESI.ITEM_CATEGORY = 'INTANGIBLE'"+
//                "     AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.ASSET_NUMBER =?) "+
//                "     AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_NAME = ?)"+
//                "     AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC = ?)" +
//                "     AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.COST = ?)";
//        sqlArgs.add(intangDTO.getAssetNumber());
//        sqlArgs.add(intangDTO.getAssetNumber());
//        sqlArgs.add(intangDTO.getItemName());
//        sqlArgs.add(intangDTO.getItemName());
//        sqlArgs.add(intangDTO.getItemSpec());
//        sqlArgs.add(intangDTO.getItemSpec());
//        sqlArgs.add(intangDTO.getCost());
//        sqlArgs.add(intangDTO.getCost());

        String sqlStr = "SELECT EFA.ASSET_NUMBER,\n" +
                "       EFA.COST,\n" +
                "       EFA.LIFE_IN_YEARS,\n" +
                "       EFA.DEPRN_COST,\n" +
                "       EFA.DATE_PLACED_IN_SERVICE,\n" +
                "       EII.BARCODE,\n" +
                "       ESI.ITEM_CODE,\n" +
                "       ESI.ITEM_NAME,\n" +
                "       ESI.ITEM_SPEC,\n" +
                "       EOCM.COMPANY,\n" +
                "       AMS_PUB_PKG.GET_VENDOR_NAME(ESI.VENDOR_ID) VENDOR_NAME\n" +
                "FROM   ETS_ITEM_INFO   EII,\n" +
                "       ETS_SYSTEM_ITEM ESI,\n" +
                "       ETS_IA_ASSETS   EFA,\n" +
                "       ETS_OU_CITY_MAP EOCM\n" +
                "WHERE  EII.ITEM_CODE = ESI.ITEM_CODE\n" +
                "       AND EII.BARCODE = EFA.TAG_NUMBER\n" +
                "       AND ESI.ITEM_CATEGORY = 'INTANGIBLE'\n" +
                "       AND EII.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
                "       AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.ORGANIZATION_ID = ?)\n" +
                "       AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.BARCODE LIKE dbo.NVL(?, EII.BARCODE))\n" +
                "       AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.ASSET_NUMBER LIKE dbo.NVL(?, EFA.ASSET_NUMBER))\n" +
                "       AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE dbo.NVL(?, ESI.ITEM_SPEC))\n" +
                "       AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME))";

        sqlArgs.add(intangDTO.getOrganizationId());
        sqlArgs.add(intangDTO.getOrganizationId());
        sqlArgs.add(intangDTO.getBarcode());
        sqlArgs.add(intangDTO.getBarcode());
        sqlArgs.add(intangDTO.getAssetNumber());
        sqlArgs.add(intangDTO.getAssetNumber());
        sqlArgs.add(intangDTO.getItemSpec());
        sqlArgs.add(intangDTO.getItemSpec());
        sqlArgs.add(intangDTO.getItemName());
        sqlArgs.add(intangDTO.getItemName());
        sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

}
