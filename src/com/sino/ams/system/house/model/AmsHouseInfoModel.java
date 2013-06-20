package com.sino.ams.system.house.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.ArrUtil;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.system.house.dto.AmsHouseInfoDTO;
import com.sino.ams.system.house.dto.AmsHouseUsesDTO;
import com.sino.ams.system.user.dto.SfUserDTO;


/**
 * <p>Title: AmsHouseInfoModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsHouseInfoModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Zyun
 * @version 1.0
 */


public class AmsHouseInfoModel extends AMSSQLProducer {

	private SfUserDTO sfUser = null;

	/**
	 * 功能：租赁房屋(EAM) AMS_HOUSE_INFO 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsHouseInfoDTO 本次操作的数据
	 */
	public AmsHouseInfoModel(SfUserDTO userAccount, AmsHouseInfoDTO dtoParameter) {
		super(userAccount, dtoParameter);
		sfUser = userAccount;
	}
	/**
	 * 功能：框架自动生成租赁房屋(EAM) AMS_HOUSE_INFO数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getDataCreateModel() throws SQLModelException{
		SQLModel sqlModel = new SQLModel();
//		try {
			List sqlArgs = new ArrayList();
			AmsHouseInfoDTO amsHouseInfo = (AmsHouseInfoDTO) dtoParameter;
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
							+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			sqlArgs.add(amsHouseInfo.getBarcode());
//			sqlArgs.add(amsHouseInfo.getRentPerson());
//			sqlArgs.add(amsHouseInfo.getRentDate());
			sqlArgs.add(amsHouseInfo.getHouseAddress());
			sqlArgs.add(amsHouseInfo.getFloorNumber());
			sqlArgs.add(amsHouseInfo.getHouseNo());
			sqlArgs.add(amsHouseInfo.getHouseArea());
			sqlArgs.add(amsHouseInfo.getAreaUnit());
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
                            + " AREA_UNIT = ?"
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
	 * 功能：框架自动生成租赁房屋(EAM) AMS_HOUSE_INFO数据删除SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsHouseInfoDTO amsHouseInfo = (AmsHouseInfoDTO)dtoParameter;
		String sqlStr = "DELETE FROM"
			+ " AMS_HOUSE_INFO"
			+ " WHERE"
			+ " BARCODE = ?";
		sqlArgs.add(amsHouseInfo.getBarcode());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成租赁房屋(EAM) AMS_HOUSE_INFO数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel(){                   //明晰
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsHouseInfoDTO amsHouseInfo = (AmsHouseInfoDTO)dtoParameter;
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
                        " AHI.HOUSE_USAGE,\n" +                       //
                        " AHI.BUSINESS_AREA,\n"+
                        " AHI.PRODUCE_HOSUSE_AREA,\n" +
                        " AHI.PRODUCE_BASE_AREA,\n" +
                        " AHI.OFFICE_AREA,\n" +
                        " AHI.HOUSE_STATUS,"+                         //
                        " ARI.RENT_ID,"+
                        " ARI.RENTAL,\n" +
                        " ARI.MONEY_UNIT,\n" +
                        " ARI.PAY_TYPE,\n" +
                        " nvl(AHI.IS_RENT,'N') IS_RENT,\n" +
                        " ARI.END_DATE\n" +
                        " FROM " +
                        " ETS_ITEM_INFO   EII,\n" +
                        " AMS_HOUSE_INFO  AHI,\n" +
                        " ETS_SYSTEM_ITEM ESI,\n" +
                        " AMS_RENT_INFO   ARI\n" +
                        " WHERE " +
                        " EII.BARCODE = AHI.BARCODE(+)\n" +
                        " AND ESI.ITEM_CODE = EII.ITEM_CODE\n" +
                        " AND ARI.BARCODE(+) = AHI.BARCODE"
                        + " AND EII.barcode = ?";
		sqlArgs.add(amsHouseInfo.getBarcode());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}


	/**
	 * 功能：根据外键关联字段 barcode 构造查询数据SQL。
	 * 框架自动生成数据租赁房屋(EAM) AMS_HOUSE_INFO详细信息查询SQLModel，请根据实际需要修改。
	 * @param barcode String
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	private SQLModel getDataByBarcodeModel(String barcode){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT "
			+ " RENT_PERSON,"
			+ " RENT_DATE,"
			+ " HOUSE_ADDRESS,"
			+ " FLOOR_NUMBER,"
			+ " HOUSE_NO,"
			+ " HOUSE_AREA,"
			+ " AREA_UNIT,"
			+ " RENTAL,"
			+ " MONEY_UNIT,"
			+ " PAY_TYPE,"
			+ " END_DATE"
			+ " FROM"
			+ " AMS_HOUSE_INFO"
			+ " WHERE"
			+ " BARCODE = ?";
		sqlArgs.add(barcode);

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据外键获取数据
	 * @param foreignKey 传入的外键字段名称。
	 * @return SQLModel
	 */
	public SQLModel getDataByForeignKeyModel(String foreignKey){
		SQLModel sqlModel = null;
		AmsHouseInfoDTO amsHouseInfo = (AmsHouseInfoDTO)dtoParameter;
		if(foreignKey.equals("barcode")){
			sqlModel = getDataByBarcodeModel(amsHouseInfo.getBarcode());
		}
		return sqlModel;
	}

	/**
	 * 功能：根据外键关联字段 barcode 构造数据删除SQL。
	 * 框架自动生成数据租赁房屋(EAM) AMS_HOUSE_INFO 数据删除SQLModel，请根据实际需要修改。
	 * @param barcode String
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	private SQLModel getDeleteByBarcodeModel(String barcode){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "DELETE FROM"
						+ " AMS_HOUSE_INFO"
						+ " WHERE"
						+ " BARCODE = ?";
		sqlArgs.add(barcode);

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据外键字段删除数据
	 * @param foreignKey 传入的外键字段名称。
	 * @return SQLModel
	 */
	public SQLModel getDeleteByForeignKeyModel(String foreignKey){
		SQLModel sqlModel = null;
		AmsHouseInfoDTO amsHouseInfo = (AmsHouseInfoDTO)dtoParameter;
		if(foreignKey.equals("barcode")){
			sqlModel = getDeleteByBarcodeModel(amsHouseInfo.getBarcode());
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成租赁房屋(EAM) AMS_HOUSE_INFO页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getPageQueryModel() throws SQLModelException{             //查询用的sql
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			AmsHouseInfoDTO amsHouseInfo = (AmsHouseInfoDTO) dtoParameter;
            String sqlStr = "SELECT " +
                    " EII.SYSTEMID,\n" +
                    " EII.ADDRESS_ID,\n"+
                    " eii.BARCODE,\n" +
                    " AHI.HOUSE_CERTIFICATE_NO,\n" +
                    " AMS_PUB_PKG.GET_COUNTY_NAME(EII.ADDRESS_ID) COUNTY_NAME,\n"+
                    " AHI.HOUSE_ADDRESS,\n" +
                    " CASE WHEN IS_RENT='Y' THEN '是' ELSE '否' END IS_RENT ,\n" +
                    " ARI.RENT_PERSON,\n" +
                    " ARI.RENT_DATE,\n" +
                    " AHI.FLOOR_NUMBER,\n" +
                    " AHI.HOUSE_NO,\n" +
                    " AHI.HOUSE_AREA,\n" +
                    " AHI.HOUSE_USAGE,\n" +                 //
                    " AHI.BUSINESS_AREA,\n"+
                    " AHI.PRODUCE_HOSUSE_AREA,\n" +
                    " AHI.PRODUCE_BASE_AREA,\n" +
                    " AHI.OFFICE_AREA,\n" +
                    " AHI.HOUSE_STATUS,"+                   //
                    " AMS_PUB_PKG.GET_FLEX_VALUE(AHI.AREA_UNIT, ?) AREA_UNIT,\n" +
                    " ARI.RENTAL,\n" +
                    " ARI.MONEY_UNIT,\n" +
                    " AMS_PUB_PKG.GET_FLEX_VALUE(ARI.PAY_TYPE, ?) PAY_TYPE,\n" +
                    " ARI.END_DATE\n" +
                    " FROM ETS_ITEM_INFO EII, AMS_HOUSE_INFO AHI, AMS_RENT_INFO ARI,   "+
                    "      ets_system_item esi" +
                    " WHERE esi.item_code = eii.item_code"+
                    "       AND EII.BARCODE = AHI.BARCODE(+)"+
                    "       AND AHI.BARCODE =ARI.BARCODE(+)" +
//                    "       AND AHI.IS_RENT(+) = NVL(?, AHI.IS_RENT(+))"+
                    "       AND (? IS NULL OR ARI.RENT_DATE >= ?) "+
                    "       AND (? IS NULL OR ARI.RENT_DATE <= ?)"+
                    "       AND eii.BARCODE(+) LIKE NVL(?, eii.BARCODE(+))"+
                    "       AND (? IS NULL OR AHI.HOUSE_STATUS  =?)"+
                    "       AND esi.item_category = 'HOUSE'" +
                    "       AND EII.ORGANIZATION_ID = ? ";
            sqlArgs.add(DictConstant.AREA_UNIT);
            sqlArgs.add(DictConstant.PAY_TYPE);
//            sqlArgs.add(amsHouseInfo.getIsRent());
			sqlArgs.add(amsHouseInfo.getFromDate());
			sqlArgs.add(amsHouseInfo.getFromDate());
			sqlArgs.add(amsHouseInfo.getToDate());
			sqlArgs.add(amsHouseInfo.getToDate());
            sqlArgs.add(amsHouseInfo.getBarcode());

            sqlArgs.add(amsHouseInfo.getHouseStatus());
            sqlArgs.add(amsHouseInfo.getHouseStatus());
            sqlArgs.add(sfUser.getOrganizationId());
            if (amsHouseInfo.getCertificate().equals("Y")) {
            sqlStr += " AND AHI.HOUSE_CERTIFICATE_NO IS NOT NULL";
            } else if (amsHouseInfo.getCertificate().equals("N")) {
            sqlStr += " AND AHI.HOUSE_CERTIFICATE_NO IS  NULL" ;
            }
          if (amsHouseInfo.getIsRent().equals("Y")) {
            sqlStr +=  "  AND EII.ATTRIBUTE1='RENT'";
            } else if (amsHouseInfo.getIsRent().equals("N")) {
            sqlStr +=  "  AND EII.ATTRIBUTE1 IS NULL";
            }

        
            sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}


      public SQLModel getEnableModel(String systemId[]) {   //执行批量生效操作
		SQLModel sqlModel = new SQLModel();
		String systemIds = ArrUtil.arrToSqlStr(systemId);
		String sqlStr = "UPDATE"
						+ " ETS_ITEM_INFO"
						+ " SET"
						+ " DISABLE_DATE = NULL"
						+ " WHERE"
						+ " SYSTEMID IN (" + systemIds + ")";
		sqlModel.setSqlStr(sqlStr);
		return sqlModel;
    }


    public SQLModel getDisableModel(String systemId[]) {     //执行批量失笑操作
        SQLModel sqlModel = new SQLModel();
		String systemIds = ArrUtil.arrToSqlStr(systemId);
        String sqlStr = "UPDATE"
						+ " ETS_ITEM_INFO"
						+ " SET"
						+ " DISABLE_DATE = GETDATE()"
						+ " WHERE"
						+ " SYSTEMID IN (" + systemIds + ")";
        sqlModel.setSqlStr(sqlStr);
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
        String strSql =
                         " SELECT"
                        +" AHI.* "
                        +" FROM "
                        +" AMS_HOUSE_INFO AHI "
                        +" WHERE AHI.BARCODE = ?";
        strArg.add(barcode);
        sqlModel.setSqlStr(strSql);
        sqlModel.setArgs(strArg);
        return sqlModel;
    }


    	/**
	 * 功能：获取判断当前用户是否有权限执行数据编辑操作。BARCODE 的存在性
	 * @param barcode String
	 * @return SQLModel
	 */
	public SQLModel getHasBarcodeModel(String barcode) {
        SQLModel sqlModel = new SQLModel();
        List strArg = new ArrayList();
        String strSql =
                     "SELECT "
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

    public SQLModel getAttribute1Model(String barcode) {
            SQLModel sqlModel = new SQLModel();
            List sqlArgs = new ArrayList();
            AmsHouseInfoDTO amsHouseInfo = (AmsHouseInfoDTO) dtoParameter;
            String sqlStr ="UPDATE "
                        + " ETS_ITEM_INFO"
                        + " SET"
                        + " ATTRIBUTE1 = ?,"
                        + " ITEM_QTY = 1,"
//                        + " ITEM_CODE = ?,"
                        + " LAST_UPDATE_DATE = GETDATE(),"
                        + " LAST_UPDATE_BY = ?"
                        + " WHERE"
                        + " BARCODE = ?";
            sqlArgs.add(DictConstant.RENT);
            sqlArgs.add(sfUser.getOrganizationId());
            sqlArgs.add(barcode);

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);

            return sqlModel;
        }
         public SQLModel getAttribute1NotModel(String barcode) {
            SQLModel sqlModel = new SQLModel();
            List sqlArgs = new ArrayList();
            AmsHouseInfoDTO amsHouseInfo = (AmsHouseInfoDTO) dtoParameter;
            String sqlStr ="UPDATE "
                        + " ETS_ITEM_INFO"
                        + " SET"
                        + " ATTRIBUTE1 = '',"
                        + " ITEM_QTY = 1,"
//                        + " ITEM_CODE = ?,"
                        + " LAST_UPDATE_DATE = GETDATE(),"
                        + " LAST_UPDATE_BY = ?"
                        + " WHERE"
                        + " BARCODE = ?";
            sqlArgs.add(sfUser.getOrganizationId());
            sqlArgs.add(barcode);

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);

            return sqlModel;
        }
 /**
    *功能：   对表AMS_RENT_INFO进行删除操作 。
  **/
//    public SQLModel doCreatRentData() throws SQLModelException{
//            SQLModel sqlModel = new SQLModel();
//        try {
//          List  sqlArgs = new ArrayList();
//           AmsHouseInfoDTO amsHouseInfo = (AmsHouseInfoDTO) dtoParameter;
//         String sqlStr =  "DELETE FROM AMS_RENT_INFO WHERE RENT_ID =?";
//
//            sqlArgs.add(amsHouseInfo.getRentId());
//
//             sqlModel.setSqlStr(sqlStr);
//             sqlModel.setArgs(sqlArgs);
//		} catch (CalendarException ex) {
//			ex.printLog();
//			throw new SQLModelException(ex);
//		}
//           return sqlModel;
//    }

     public SQLModel doCreatRentData() throws SQLModelException{
            SQLModel sqlModel = new SQLModel();
        try {
          List  sqlArgs = new ArrayList();
           AmsHouseInfoDTO amsHouseInfo = (AmsHouseInfoDTO) dtoParameter;
         String sqlStr =  "INSERT INTO" +
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
                 "  (AMS_RENT_INFO_S.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), ?)";

            sqlArgs.add(amsHouseInfo.getBarcode());
			sqlArgs.add(amsHouseInfo.getRentPerson());
			sqlArgs.add(amsHouseInfo.getRentDate());
			sqlArgs.add(amsHouseInfo.getRental());
			sqlArgs.add(amsHouseInfo.getMoneyUnit());
			sqlArgs.add(amsHouseInfo.getPayType());
			sqlArgs.add(amsHouseInfo.getEndDate());
//            sqlArgs.add(amsHouseInfo.getIsRent());
            sqlArgs.add(amsHouseInfo.getRentUsage());
            sqlArgs.add(sfUser.getOrganizationId());

             sqlModel.setSqlStr(sqlStr);
             sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
           return sqlModel;
    }


    public SQLModel doUpdateRentData() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try{
        List sqlArgs = new ArrayList();
        AmsHouseInfoDTO amsHouseInfoDTO = (AmsHouseInfoDTO) dtoParameter;
        String sqlStr = "UPDATE " +
                " AMS_RENT_INFO\n" +
                " SET" +
                " RENT_PERSON = ?,\n" +
                " RENT_DATE = ?,\n" +
                " RENTAL = ?,\n" +
                " MONEY_UNIT = ?,\n" +
                " PAY_TYPE = ?,\n" +
                " END_DATE = ?,\n" +
                " RENT_USAGE = ?,\n" +
                " LAST_UPDATE_DATE = GETDATE(),\n" +
                " LAST_UPDATE_BY = ?\n" +
                " WHERE" +
                " RENT_ID = ?";
        sqlArgs.add(amsHouseInfoDTO.getRentPerson());
        sqlArgs.add(amsHouseInfoDTO.getRentDate());
        sqlArgs.add(amsHouseInfoDTO.getRental());
        sqlArgs.add(amsHouseInfoDTO.getMoneyUnit());
        sqlArgs.add(amsHouseInfoDTO.getPayType());
        sqlArgs.add(amsHouseInfoDTO.getEndDate());
        sqlArgs.add(amsHouseInfoDTO.getRentUsage());
        sqlArgs.add(sfUser.getOrganizationId());
        sqlArgs.add(amsHouseInfoDTO.getRentId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
           } catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
        return sqlModel;
    }


     public SQLModel doDeleteRentData()  {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsHouseInfoDTO amsHouseInfoDTO = (AmsHouseInfoDTO) dtoParameter;
        String sqlStr ="DELETE "
                    + " FROM"
                    + " AMS_RENT_INFO"
                    + " WHERE"
                    + " RENT_ID = ?";
        sqlArgs.add(amsHouseInfoDTO.getRentId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }
    public SQLModel insertUsesInfo(AmsHouseUsesDTO dto )  {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
//        AmsHouseUsesDTO amsHouseInfoDTO = (AmsHouseUsesDTO) dtoParameter;
        String sqlStr ="INSERT INTO AMS_HOUSE_USES\n" +
                "  (USES_ID,\n" +
                "   BARCODE,\n" +
                "   USAGE,\n" +
                "   AREA,\n" +
                "   REMARK)\n" +
                "VALUES\n" +
                "  (AMS_HOUSE_USES_S.NEXTVAL,\n" +
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
    public SQLModel deleteUsesInfo()  {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsHouseInfoDTO amsHouseInfoDTO = (AmsHouseInfoDTO) dtoParameter;
        String sqlStr ="DELETE FROM AMS_HOUSE_USES WHERE BARCODE = ?";
        sqlArgs.add(amsHouseInfoDTO.getBarcode());
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
}
