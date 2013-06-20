package com.sino.ams.system.rent.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.ArrUtil;


/**
 * <p>Title: AmsAssetsPriviServlet</p>
 * <p>Description:程序自动生成服务程序“AmsAssetsPriviServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class AmsRentAssetModel extends AMSSQLProducer {


	/**
	 * 功能：固定资产当前信息(EAM) ETS_FA_ASSETS 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsAssetsAddressVDTO 本次操作的数据
	 */
	public AmsRentAssetModel(SfUserDTO userAccount, AmsAssetsAddressVDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：框架自动生成固定资产当前信息(EAM) AMS_FA_ASSETS多条数据信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回多条数据信息查询用SQLModel
	 * @param checkedAssets 选中的资产
	 */
	public SQLModel getExpCheckedAssetsModel(String[] checkedAssets) {
		SQLModel sqlModel = getPageQueryModel();
		String barcodes = ArrUtil.arrToSqlStr(checkedAssets);
		String sqlStr = sqlModel.getSqlStr();
		sqlStr = "SELECT * FROM (" + sqlStr + ") TMP_V WHERE TMP_V.BARCODE IN (" + barcodes + ")";
		sqlModel.setSqlStr(sqlStr);
		return sqlModel;
	}


	/**
	 * 功能：根据标签号获取设备详细信息SQL
	 * @return SQLModel
	 * @throws com.sino.base.exception.SQLModelException
     */
	public SQLModel getPrimaryKeyDataModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
						+ " AAAV.*"
						+ " FROM"
						+ " AMS_ASSETS_ADDRESS_V AAAV"
						+ " WHERE"
						+ " AAAV.BARCODE = ?";
		sqlArgs.add(dto.getBarcode());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
    }


	/**
	 * 功能：框架自动生成固定资产当前信息(EAM) AMS_FA_ASSETS页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 */
	public SQLModel getPageQueryModel(){
		SQLModel sqlModel = null;
		AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
		String treeCategory = dto.getTreeCategory();
		if(treeCategory.equals(AssetsWebAttributes.RENT_ASSETS_TREE_PERSON)){//个人资产
			sqlModel = getPersonalAssetsModel();
		} else if(treeCategory.equals(AssetsWebAttributes.RENT_ASSETS_TREE_DEPART)){//部门资产
			sqlModel = getDeptAssetsModel();
		} else if(treeCategory.equals(AssetsWebAttributes.RENT_ASSETS_TREE_COMPAN)){//公司资产
			sqlModel = getCompanyAssetsModel();
		} else if(treeCategory.equals(AssetsWebAttributes.RENT_ASSETS_TREE_PROVIN)){//全省资产
			sqlModel = getProvinceAssetsModel();
//		} else if(treeCategory.equals(AssetsWebAttributes.ASSETS_TREE_CONFIRM)){//待确认资产
//			sqlModel = getConfirmAssetsModel();
//		} else if(treeCategory.equals(AssetsWebAttributes.ASSETS_TREE_TRANSFER)){//调出资产
//			sqlModel = getTransferAssetsModel();
		}
		return sqlModel;
	}

	/**
	 * 功能：构造获取个人待确认资产的SQL
	 * @return SQLModel
	 */
	private SQLModel getConfirmAssetsModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
		String sqlStr = "SELECT"
						+ " AATL.BARCODE,"
						+ " AATH.TRANS_ID,"
						+ " AATH.TRANS_NO,"
						+ " AATH.TRANSFER_TYPE,"
						+ " EFA.ASSET_NUMBER,"
						+ " EFA.ASSETS_DESCRIPTION,"
						+ " EFA.MODEL_NUMBER,"
						+ " ISNULL(EFA.CURRENT_UNITS, 1) CURRENT_UNITS,"
						+ " EFA.COST,"
						+ " EFA.DEPRN_COST,"
						+ " EFA.DATE_PLACED_IN_SERVICE,"
						+ " EOO.WORKORDER_OBJECT_NAME OLD_LOCATION_NAME,"
						+ " AMEO.USER_NAME OLD_RESPONSIBILITY_USER_NAME,"
						+ " AMDO.DEPT_NAME OLD_RESPONSIBILITY_DEPT_NAME,"
						+ " EON.WORKORDER_OBJECT_NO ASSIGNED_TO_LOCATION,"
						+ " EON.WORKORDER_OBJECT_NAME ASSIGNED_TO_LOCATION_NAME,"
						+ " AMEN.EMPLOYEE_ID RESPONSIBILITY_USER,"
						+ " AMEN.USER_NAME RESPONSIBILITY_USER_NAME,"
						+ " AMDN.DEPT_CODE RESPONSIBILITY_DEPT,"
						+ " AMDN.DEPT_NAME RESPONSIBILITY_DEPT_NAME,"
						+ " (SELECT"
						+ " AOA.ADDRESS_ID"
						+ " FROM"
						+ " AMS_OBJECT_ADDRESS AOA"
						+ " WHERE"
						+ " AOA.OBJECT_NO = EON.WORKORDER_OBJECT_NO"
						+ " AND AOA.BOX_NO = '0000'"
						+ " AND AOA.NET_UNIT = '0000'"
						+ " ) ADDRESS_ID,"
						+ " EON.ORGANIZATION_ID TO_ORGANIZATION_ID,"
						+ " AATL.REMARK"
						+ " FROM"
						+ " AMS_ASSETS_TRANS_HEADER AATH,"
						+ " AMS_MIS_EMPLOYEE        AMEO,"
						+ " AMS_MIS_DEPT            AMDO,"
						+ " ETS_OBJECT              EOO,"
						+ " AMS_ASSETS_TRANS_LINE   AATL,"
						+ " AMS_MIS_EMPLOYEE        AMEN,"
						+ " AMS_MIS_DEPT            AMDN,"
						+ " ETS_OBJECT              EON,"
						+ " ETS_ITEM_INFO           EII,"
						+ " ETS_ITEM_MATCH          EIM,"
						+ " ETS_FA_ASSETS           EFA"
						+ " WHERE"
						+ " EFA.ASSET_ID = EIM.ASSET_ID"
						+ " AND EIM.SYSTEMID = EII.SYSTEMID"
						+ " AND EII.BARCODE = AATL.BARCODE"
						+ " AND AATL.OLD_LOCATION = EOO.WORKORDER_OBJECT_NO"
						+ " AND AATL.OLD_RESPONSIBILITY_USER *= AMEO.EMPLOYEE_ID"
						+ " AND AATL.OLD_RESPONSIBILITY_DEPT *= AMDO.DEPT_CODE"
						+ " AND AATL.ASSIGNED_TO_LOCATION *= EON.WORKORDER_OBJECT_NO"
						+ " AND AATL.RESPONSIBILITY_USER *= AMEN.EMPLOYEE_ID"
						+ " AND AATL.RESPONSIBILITY_DEPT *= AMDN.DEPT_CODE"
						+ " AND AATL.TRANS_ID = AATH.TRANS_ID"
						+ " AND ((AATL.LINE_STATUS = ? AND AATH.TRANSFER_TYPE = ?) OR (AATL.LINE_STATUS = ? AND AATH.TRANSFER_TYPE <> ?))"
						+ " AND AATL.RESPONSIBILITY_USER = ?"
						+ " AND AATL.CONFIRM_DATE " + SyBaseSQLUtil.isNullNoParam() + " "
						+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR AATH.FROM_DEPT = dbo.NVL(?, AATH.FROM_DEPT))"
						+ " AND EFA.ASSETS_DESCRIPTION LIKE dbo.NVL(?, EFA.ASSETS_DESCRIPTION)"
						+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.MODEL_NUMBER LIKE dbo.NVL(?, EFA.MODEL_NUMBER))"
						+ " AND EII.BARCODE LIKE dbo.NVL(?, EII.BARCODE)"
						+ " AND EFA.ASSET_NUMBER LIKE dbo.NVL(?, EFA.ASSET_NUMBER)"
						+ " ORDER BY AATH.TRANS_NO DESC";
		sqlArgs.add(AssetsDictConstant.APPROVED);
		sqlArgs.add(AssetsDictConstant.TRANS_INN_DEPT);
		sqlArgs.add(AssetsDictConstant.ORDER_STS_ASSIGNED);
		sqlArgs.add(AssetsDictConstant.TRANS_INN_DEPT);
		sqlArgs.add(userAccount.getEmployeeId());
		sqlArgs.add(dto.getDeptCode());
		sqlArgs.add(dto.getDeptCode());
		sqlArgs.add(dto.getAssetsDescription());
		sqlArgs.add(dto.getModelNumber());
		sqlArgs.add(dto.getModelNumber());
		sqlArgs.add(dto.getBarcode());
		sqlArgs.add(dto.getAssetNumber());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return  sqlModel;
	}

	/**
	 * 功能：构造获取调出资产的SQL
	 * @return SQLModel
	 */
	private SQLModel getTransferAssetsModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
		String sqlStr = "SELECT"
						+ " AATL.BARCODE,"
						+ " AATH.TRANS_ID,"
						+ " AATH.TRANS_NO,"
						+ " AATH.TRANSFER_TYPE,"
						+ " AAAV.ASSET_NUMBER,"
						+ " AAAV.ASSETS_DESCRIPTION,"
						+ " AAAV.MODEL_NUMBER,"
						+ " ISNULL(AAAV.CURRENT_UNITS, 1) CURRENT_UNITS,"
						+ " AAAV.COST,"
						+ " AAAV.DEPRN_COST,"
						+ " AAAV.DATE_PLACED_IN_SERVICE,"
						+ " EOO.WORKORDER_OBJECT_NAME OLD_LOCATION_NAME,"
						+ " AMEO.USER_NAME OLD_RESPONSIBILITY_USER_NAME,"
						+ " AMDO.DEPT_NAME OLD_RESPONSIBILITY_DEPT_NAME,"
						+ " EON.WORKORDER_OBJECT_NO ASSIGNED_TO_LOCATION,"
						+ " EON.WORKORDER_OBJECT_NAME ASSIGNED_TO_LOCATION_NAME,"
						+ " AMEN.EMPLOYEE_ID RESPONSIBILITY_USER,"
						+ " AMEN.USER_NAME RESPONSIBILITY_USER_NAME,"
						+ " AMDN.DEPT_CODE RESPONSIBILITY_DEPT,"
						+ " AMDN.DEPT_NAME RESPONSIBILITY_DEPT_NAME,"
						+ " (SELECT"
						+ " AOA.ADDRESS_ID"
						+ " FROM"
						+ " AMS_OBJECT_ADDRESS AOA"
						+ " WHERE"
						+ " AOA.OBJECT_NO = EON.WORKORDER_OBJECT_NO"
						+ " AND AOA.BOX_NO = '0000'"
						+ " AND AOA.NET_UNIT = '0000'"
						+ " ) ADDRESS_ID,"
						+ " EON.ORGANIZATION_ID TO_ORGANIZATION_ID,"
						+ " AATL.REMARK"
						+ " FROM"
						+ " AMS_ASSETS_TRANS_HEADER AATH,"
						+ " AMS_ASSETS_TRANS_LINE   AATL,"
						+ " AMS_MIS_EMPLOYEE        AMEO,"
						+ " AMS_MIS_DEPT            AMDO,"
						+ " ETS_OBJECT              EOO,"
						+ " AMS_MIS_EMPLOYEE        AMEN,"
						+ " AMS_MIS_DEPT            AMDN,"
						+ " ETS_OBJECT              EON,"
						+ " AMS_ASSETS_ADDRESS_V    AAAV"
						+ " WHERE"
						+ " AATL.OLD_LOCATION = EOO.WORKORDER_OBJECT_NO"
						+ " AND AATL.OLD_RESPONSIBILITY_USER *= AMEO.EMPLOYEE_ID"
						+ " AND AATL.OLD_RESPONSIBILITY_DEPT *= AMDO.DEPT_CODE"
						+ " AND AATL.ASSIGNED_TO_LOCATION *= EON.WORKORDER_OBJECT_NO"
						+ " AND AATL.RESPONSIBILITY_USER *= AMEN.EMPLOYEE_ID"
						+ " AND AATL.RESPONSIBILITY_DEPT *= AMDN.DEPT_CODE"
						+ " AND AATL.BARCODE = AAAV.BARCODE"
						+ " AND AATL.TRANS_ID = AATH.TRANS_ID"
						+ " AND AATL.CONFIRM_DATE " + SyBaseSQLUtil.isNullNoParam() + " "
						+ " AND AATH.TRANS_TYPE = ?"
						+ " AND AATH.TRANSFER_TYPE = dbo.NVL(?, AATH.TRANSFER_TYPE)"
						+ " AND AATH.CREATED_BY = ?"
						+ " AND AAAV.FA_CATEGORY1 LIKE dbo.NVL (?, AAAV.FA_CATEGORY1)"
						+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR AAAV.FA_CATEGORY2 LIKE dbo.NVL (?, AAAV.FA_CATEGORY2))"
						+ " AND AAAV.ASSETS_DESCRIPTION LIKE dbo.NVL(?, AAAV.ASSETS_DESCRIPTION)"
						+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR AAAV.MODEL_NUMBER LIKE dbo.NVL(?, AAAV.MODEL_NUMBER))"
						+ " AND AAAV.BARCODE LIKE dbo.NVL(?, AAAV.BARCODE)"
						+ " AND AAAV.ASSET_NUMBER LIKE dbo.NVL(?, AAAV.ASSET_NUMBER)"
						+ " AND EXISTS("
						+ " SELECT"
						+ " NULL"
						+ " FROM"
						+ " ETS_SYSITEM_DISTRIBUTE ESD"
						+ " WHERE"
						+ " ESD.ITEM_CODE = AAAV.ITEM_CODE"
						+ " AND ESD.ORGANIZATION_ID = ?)"
						+ " ORDER BY AATH.TRANS_NO DESC";
		sqlArgs.add(AssetsDictConstant.ASS_RED);
		sqlArgs.add(dto.getTransferType());
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(dto.getFaCategory1());
		sqlArgs.add(dto.getFaCategory2());
		sqlArgs.add(dto.getFaCategory2());
		sqlArgs.add(dto.getAssetsDescription());
		sqlArgs.add(dto.getModelNumber());
		sqlArgs.add(dto.getModelNumber());
		sqlArgs.add(dto.getBarcode());
		sqlArgs.add(dto.getAssetNumber());
		sqlArgs.add(userAccount.getOrganizationId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
//		try {
//			FileUtil.saveStrContent(sqlModel.toString(), "C:\\assets.sql");
//		} catch (Exception ex) {
//		}
		return sqlModel;
	}

	/**
	 * 功能：构造获取个人资产的SQL
	 * @return SQLModel
	 */
	private SQLModel getPersonalAssetsModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
        String sqlStr =  "SELECT ROWNUM,\n" +
                   "       ARI.BARCODE,\n" +
                   "       ESI.ITEM_NAME,\n" +
                   "       ESI.ITEM_SPEC,\n" +
//                   "       EII.FINANCE_PROP,\n" +
                   "       ARI.RENT_PERSON,\n" +
//                   "       AMS_PUB_PKG.GET_FLEX_VALUE(EII.FINANCE_PROP, 'FINANCE_PROP') FINANCE_PROP,\n" +
                   " CASE WHEN EII.FINANCE_PROP='UNKNOW' THEN '' ELSE dbo.APP_GET_FLEX_VALUE(EII.FINANCE_PROP,'FINANCE_PROP') END FINANCE_PROP,"+
                   "       AMS_PUB_PKG.GET_WORKORDER_OBJECT_NAME(EII.ADDRESS_ID) OBJECT_NAME,\n" +
                   "       AMS_PUB_PKG.GET_DEPT_NAME(EII.MAINTAIN_DEPT) MAINTAIN_DEPT," +
//                   "       EII.MAINTAIN_DEPT,\n" +
                   "       ARI.TENANCY,\n" +
                   "       ARI.YEAR_RENTAL,\n" +
                   "       ARI.MONTH_REANTAL,\n" +
                   "       ARI.RENT_DATE,\n" +
                   "       ARI.END_DATE,\n" +
                   "       EII.RESPONSIBILITY_USER,\n" +
                   "       AMS_PUB_PKG.GET_USER_NAME(EII.RESPONSIBILITY_USER) USER_NAME,\n" +
                   "       AMS_PUB_PKG.GET_DEPT_NAME(EII.RESPONSIBILITY_DEPT) GROUP_NAME,\n" +
                   "       EII.RESPONSIBILITY_DEPT,\n" +
                   "       ARI.REMARK,\n" +
                   "       ARI.RENT_ID\n" +
                   "  FROM ETS_ITEM_INFO EII, ETS_SYSTEM_ITEM ESI, AMS_RENT_INFO ARI\n" +
                   " WHERE ESI.ITEM_CODE = EII.ITEM_CODE\n" +
                   "   AND EII.BARCODE = ARI.BARCODE\n" +
                   "   AND EII.ATTRIBUTE1 = 'RENT'"+
                   "   AND EII.RESPONSIBILITY_USER= ?"+
                   " AND ( " + SyBaseSQLUtil.isNull() + "  OR  EII.BARCODE LIKE ?)"+
                   " AND ( " + SyBaseSQLUtil.isNull() + "  OR  ESI.ITEM_NAME LIKE ?)"+
                   " AND ( " + SyBaseSQLUtil.isNull() + "  OR  ESI.ITEM_SPEC LIKE ?)"+
                   " AND ( " + SyBaseSQLUtil.isNull() + "  OR AMS_PUB_PKG.GET_WORKORDER_OBJECT_NAME(EII.ADDRESS_ID) LIKE ? )\n";


        sqlArgs.add(userAccount.getEmployeeId());

        sqlArgs.add(dto.getBarcode());
        sqlArgs.add(dto.getBarcode());
        sqlArgs.add(dto.getAssetsDescription());
        sqlArgs.add(dto.getAssetsDescription());
        sqlArgs.add(dto.getModelNumber());
        sqlArgs.add(dto.getModelNumber());
        sqlArgs.add(dto.getAssetsLocation());
        sqlArgs.add(dto.getAssetsLocation());

        sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return  sqlModel;
	}

	/**
	 * 功能：构造获取部门资产的SQL
	 * @return SQLModel
	 */
	private SQLModel getDeptAssetsModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
	    String sqlStr =  "SELECT " +
                       " ROWNUM,\n" +
                       " ARI.BARCODE,\n" +
                       " ESI.ITEM_NAME,\n" +
                       " ESI.ITEM_SPEC,\n" +
                       " ARI.RENT_PERSON,\n" +
                       " CASE WHEN EII.FINANCE_PROP='UNKNOW' THEN '' ELSE dbo.APP_GET_FLEX_VALUE(EII.FINANCE_PROP,'FINANCE_PROP') END FINANCE_PROP,"+
                       " AMS_PUB_PKG.GET_WORKORDER_OBJECT_NAME(EII.ADDRESS_ID) OBJECT_NAME,\n" +
                       " AMS_PUB_PKG.GET_DEPT_NAME(EII.MAINTAIN_DEPT) MAINTAIN_DEPT," +
                       " ARI.TENANCY,\n" +
                       " ARI.YEAR_RENTAL,\n" +
                       " ARI.MONTH_REANTAL,\n" +
                       " ARI.RENT_DATE,\n" +
                       " ARI.END_DATE,\n" +
                       " EII.RESPONSIBILITY_USER,\n" +
                       " AMS_PUB_PKG.GET_USER_NAME(EII.RESPONSIBILITY_USER) USER_NAME,\n" +
//                       " AMS_PUB_PKG.GET_GROUP_NAME(EII.RESPONSIBILITY_DEPT) GROUP_NAME,\n" +
                       " AMD.DEPT_NAME GROUP_NAME,\n" +
                       " EII.RESPONSIBILITY_DEPT,\n" +
                       " ARI.REMARK,\n" +
                       " ARI.RENT_ID\n" +
                       " FROM " +
                       " ETS_ITEM_INFO EII, " +
                       " ETS_SYSTEM_ITEM ESI, " +
                       " AMS_RENT_INFO ARI\n," +
                       " AMS_MIS_DEPT     AMD" +
                       " WHERE " +
                       " ESI.ITEM_CODE = EII.ITEM_CODE\n" +
                       " AND EII.BARCODE = ARI.BARCODE\n" +
                       " AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE" +
                       " AND EII.ATTRIBUTE1 = 'RENT'"+
//                       " AND (AMD.DEPT_NAME LIKE dbo.NVL(?,AMD.DEPT_NAME))"+
                       " AND (AMD.DEPT_NAME LIKE ? )"+
                       " AND ( " + SyBaseSQLUtil.isNull() + "  OR  EII.BARCODE LIKE ?)"+
                       " AND ( " + SyBaseSQLUtil.isNull() + "  OR  ESI.ITEM_NAME LIKE ?)"+
                       " AND ( " + SyBaseSQLUtil.isNull() + "  OR  ESI.ITEM_SPEC LIKE ?)"+
                       " AND ( " + SyBaseSQLUtil.isNull() + "  OR AMS_PUB_PKG.GET_WORKORDER_OBJECT_NAME(EII.ADDRESS_ID) LIKE ? )\n";
//                       " AND ( " + SyBaseSQLUtil.isNull() + "  OR AMS_PUB_PKG.GET_GROUP_NAME(EII.RESPONSIBILITY_DEPT) LIKE ?)";
//                       " AND ( " + SyBaseSQLUtil.isNull() + "  OR AMD.DEPT_NAME LIKE ?)";
//                       " AND RESPONSIBILITY_USER= ?";

        sqlArgs.add(dto.getDeptName());
        sqlArgs.add(dto.getBarcode());
        sqlArgs.add(dto.getBarcode());
        sqlArgs.add(dto.getAssetsDescription());
        sqlArgs.add(dto.getAssetsDescription());
        sqlArgs.add(dto.getModelNumber());
        sqlArgs.add(dto.getModelNumber());
        sqlArgs.add(dto.getAssetsLocation());
        sqlArgs.add(dto.getAssetsLocation());

//         sqlArgs.add(dto.getDeptName());
//         sqlArgs.add(dto.getDeptName());

        sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return  sqlModel;
	}

	/**
	 * 功能：获取公司资产查询SQL
	 * @return SQLModel
	 */
	private SQLModel getCompanyAssetsModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
	    String sqlStr =  "SELECT " +
                       " ROWNUM,\n" +
                       " ARI.BARCODE,\n" +
                       " ESI.ITEM_NAME,\n" +
                       " ESI.ITEM_SPEC,\n" +
                       " ARI.RENT_PERSON,\n" +
                       " CASE WHEN EII.FINANCE_PROP='UNKNOW' THEN '' ELSE dbo.APP_GET_FLEX_VALUE(EII.FINANCE_PROP,'FINANCE_PROP') END FINANCE_PROP,"+
                       " AMS_PUB_PKG.GET_WORKORDER_OBJECT_NAME(EII.ADDRESS_ID) OBJECT_NAME,\n" +
                       " AMS_PUB_PKG.GET_DEPT_NAME(EII.MAINTAIN_DEPT) MAINTAIN_DEPT," +
                       " ARI.TENANCY,\n" +
                       " ARI.YEAR_RENTAL,\n" +
                       " ARI.MONTH_REANTAL,\n" +
                       " ARI.RENT_DATE,\n" +
                       " ARI.END_DATE,\n" +
                       " EII.RESPONSIBILITY_USER,\n" +
                       " AMS_PUB_PKG.GET_USER_NAME(EII.RESPONSIBILITY_USER) USER_NAME,\n" +
                       " AMS_PUB_PKG.GET_DEPT_NAME(EII.RESPONSIBILITY_DEPT) GROUP_NAME,\n" +
                       " EII.RESPONSIBILITY_DEPT,\n" +
                       " ARI.REMARK,\n" +
                       " ARI.RENT_ID\n" +
                       " FROM " +
                       " ETS_ITEM_INFO EII, " +
                       " ETS_SYSTEM_ITEM ESI, " +
                       " AMS_RENT_INFO ARI," +
                       " AMS_MIS_EMPLOYEE AME,\n" +
                       " AMS_MIS_DEPT     AMD,\n" +
                       " ETS_OU_CITY_MAP  EOCM\n" +
                       " WHERE " +
                       " ESI.ITEM_CODE = EII.ITEM_CODE\n" +
                       " AND EII.BARCODE = ARI.BARCODE\n" +
                       " AND EII.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
                       " AND EII.RESPONSIBILITY_USER = AME.EMPLOYEE_ID\n" +
                       " AND EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE" +
                       " AND EII.ATTRIBUTE1 = 'RENT'"+
                       " AND ( EOCM.COMPANY = dbo.NVL(?,EOCM.COMPANY))\n" +
                       " AND ( " + SyBaseSQLUtil.isNull() + "  OR AMD.DEPT_NAME LIKE ?)"+
                       " AND ( " + SyBaseSQLUtil.isNull() + "  OR  EII.BARCODE LIKE ?)"+
                       " AND ( " + SyBaseSQLUtil.isNull() + "  OR  ESI.ITEM_NAME LIKE ?)"+
                       " AND ( " + SyBaseSQLUtil.isNull() + "  OR  ESI.ITEM_SPEC LIKE ?)"+
                       " AND ( " + SyBaseSQLUtil.isNull() + "  OR AMS_PUB_PKG.GET_WORKORDER_OBJECT_NAME(EII.ADDRESS_ID) LIKE ? )\n" +
                       " AND ( " + SyBaseSQLUtil.isNull() + "  OR AMS_PUB_PKG.GET_DEPT_NAME(EII.RESPONSIBILITY_DEPT) LIKE ?)";


        sqlArgs.add(dto.getCompanyName());
        sqlArgs.add(dto.getDeptName());
        sqlArgs.add(dto.getDeptName());

        sqlArgs.add(dto.getBarcode());
        sqlArgs.add(dto.getBarcode());
        sqlArgs.add(dto.getAssetsDescription());
        sqlArgs.add(dto.getAssetsDescription());
        sqlArgs.add(dto.getModelNumber());
        sqlArgs.add(dto.getModelNumber());
        sqlArgs.add(dto.getAssetsLocation());
        sqlArgs.add(dto.getAssetsLocation());

         sqlArgs.add(dto.getDeptName());
         sqlArgs.add(dto.getDeptName());

        sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return  sqlModel;
	}

	/**
	 * 功能：获取全省资产查询SQL
	 * @return SQLModel
	 */
	private SQLModel getProvinceAssetsModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
	   String sqlStr =  "SELECT " +
                       " ROWNUM,\n" +
                       " ARI.BARCODE,\n" +
                       " ESI.ITEM_NAME,\n" +
                       " ESI.ITEM_SPEC,\n" +
                       " ARI.RENT_PERSON,\n" +
                       " CASE WHEN EII.FINANCE_PROP='UNKNOW' THEN '' ELSE dbo.APP_GET_FLEX_VALUE(EII.FINANCE_PROP,'FINANCE_PROP') END FINANCE_PROP,"+
                       " AMS_PUB_PKG.GET_WORKORDER_OBJECT_NAME(EII.ADDRESS_ID) OBJECT_NAME,\n" +
                       " AMS_PUB_PKG.GET_DEPT_NAME(EII.MAINTAIN_DEPT) MAINTAIN_DEPT," +
                       " ARI.TENANCY,\n" +
                       " ARI.YEAR_RENTAL,\n" +
                       " ARI.MONTH_REANTAL,\n" +
                       " ARI.RENT_DATE,\n" +
                       " ARI.END_DATE,\n" +
                       " EII.RESPONSIBILITY_USER,\n" +
                       " AMS_PUB_PKG.GET_USER_NAME(EII.RESPONSIBILITY_USER) USER_NAME,\n" +
                       " AMS_PUB_PKG.GET_DEPT_NAME(EII.RESPONSIBILITY_DEPT) GROUP_NAME,\n" +
                       " EII.RESPONSIBILITY_DEPT,\n" +
                       " ARI.REMARK,\n" +
                       " ARI.RENT_ID\n" +
                       " FROM " +
                       " ETS_ITEM_INFO EII, " +
                       " ETS_SYSTEM_ITEM ESI, " +
                       " AMS_RENT_INFO ARI," +
                       " ETS_OU_CITY_MAP  EOCM\n" +
                       " WHERE " +
                       " ESI.ITEM_CODE = EII.ITEM_CODE\n" +
                       " AND EII.BARCODE = ARI.BARCODE\n" +
                       " AND EII.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
                       " AND EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE"  +
                       " AND EII.ATTRIBUTE1 = 'RENT'" +
                       " AND (EOCM.COMPANY LIKE dbo.NVL(?,EOCM.COMPANY))" +
                       " AND ( " + SyBaseSQLUtil.isNull() + "  OR  EII.BARCODE LIKE ?)"+
                       " AND ( " + SyBaseSQLUtil.isNull() + "  OR  ESI.ITEM_NAME LIKE ?)"+
                       " AND ( " + SyBaseSQLUtil.isNull() + "  OR  ESI.ITEM_SPEC LIKE ?)"+
                       " AND ( " + SyBaseSQLUtil.isNull() + "  OR AMS_PUB_PKG.GET_WORKORDER_OBJECT_NAME(EII.ADDRESS_ID) LIKE ? )\n" +
                       " AND ( " + SyBaseSQLUtil.isNull() + "  OR AMS_PUB_PKG.GET_DEPT_NAME(EII.RESPONSIBILITY_DEPT) LIKE ?)";

        sqlArgs.add(dto.getCompanyName());
        sqlArgs.add(dto.getBarcode());
        sqlArgs.add(dto.getBarcode());
        sqlArgs.add(dto.getAssetsDescription());
        sqlArgs.add(dto.getAssetsDescription());
        sqlArgs.add(dto.getModelNumber());
        sqlArgs.add(dto.getModelNumber());
        sqlArgs.add(dto.getAssetsLocation());
        sqlArgs.add(dto.getAssetsLocation());

         sqlArgs.add(dto.getDeptName());
         sqlArgs.add(dto.getDeptName());

        sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return  sqlModel;
	}
}
