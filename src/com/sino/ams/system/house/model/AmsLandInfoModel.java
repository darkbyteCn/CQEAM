package com.sino.ams.system.house.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.system.house.dto.AmsLandInfoDTO;
import com.sino.ams.system.user.dto.SfUserDTO;


/**
 * <p>Title: AmsLandInfoModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsLandInfoModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author Zyun
 * @version 1.0
 */


public class AmsLandInfoModel extends AMSSQLProducer {

    private SfUserDTO sfUser = null;

    /**
     * 功能：租赁土地资产(EAM) AMS_LAND_INFO 数据库SQL构造层构造函数
     *
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsLandInfoDTO 本次操作的数据
     */
    public AmsLandInfoModel(SfUserDTO userAccount, AmsLandInfoDTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
    }

    /**
     * 功能：框架自动生成租赁土地资产(EAM) AMS_LAND_INFO数据插入SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据插入用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getDataCreateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
//		try {
        List sqlArgs = new ArrayList();
        AmsLandInfoDTO amsLandInfo = (AmsLandInfoDTO) dtoParameter;
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
        sqlArgs.add(amsLandInfo.getLandArea());
        sqlArgs.add(amsLandInfo.getRemark());
        sqlArgs.add(amsLandInfo.getLandCertficateNo());
        sqlArgs.add(amsLandInfo.getLandAddress());
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
     * 功能：框架自动生成租赁土地资产(EAM) AMS_LAND_INFO数据更新SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据更新用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getDataUpdateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
//		try {
        List sqlArgs = new ArrayList();
        AmsLandInfoDTO amsLandInfo = (AmsLandInfoDTO) dtoParameter;
        String sqlStr = "UPDATE" +
                "    AMS_LAND_INFO"
                + " SET"
                + " AREA_UNIT = ?,"
                + " LAND_AREA = ?,"
                + " REMARK = ?,"
                + " LAND_CERTFICATE_NO = ?,"
                + " LAND_ADDRESS = ?,"
                + " IS_RENT = ?"
                + " WHERE"
                + " BARCODE = ?";

        sqlArgs.add(amsLandInfo.getAreaUnit());
        sqlArgs.add(amsLandInfo.getLandArea());
        sqlArgs.add(amsLandInfo.getRemark());
        sqlArgs.add(amsLandInfo.getLandCertficateNo());
        sqlArgs.add(amsLandInfo.getLandAddress());
        sqlArgs.add(amsLandInfo.getIsRent());
        sqlArgs.add(amsLandInfo.getBarcode());

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
        AmsLandInfoDTO amsLandInfo = (AmsLandInfoDTO) dtoParameter;
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
        AmsLandInfoDTO amsLandInfo = (AmsLandInfoDTO) dtoParameter;
        String sqlStr = "SELECT EII.ITEM_CODE,\n" +
                " EII.SYSTEMID SYSTEM_ID,\n" +
                " EII.BARCODE,\n" +
                " EII.ADDRESS_ID,\n" +
                " ESI.ITEM_NAME,\n" +
                " ESI.ITEM_SPEC,\n" +
                " ARI.RENT_PERSON,\n" +
                " ARI.RENT_DATE,\n" +
                " ALI.BARCODE,\n" +
                " ALI.AREA_UNIT,\n" +
                " ALI.LAND_AREA,\n" +
                " ALI.REMARK,\n" +
                " ALI.LAND_CERTFICATE_NO,\n" +
                " ALI.LAND_ADDRESS,\n" +
                " nvl(ALI.IS_RENT,'N') IS_RENT,\n" +
                " ARI.RENT_ID,\n" +
                " ARI.RENTAL,\n" +
                " ARI.MONEY_UNIT,\n" +
                " ARI.PAY_TYPE,\n" +
                " ARI.END_DATE\n" +
                " FROM " +
                " ETS_ITEM_INFO   EII,\n" +
                " AMS_LAND_INFO   ALI,\n" +
                " ETS_SYSTEM_ITEM ESI,\n" +
                " AMS_RENT_INFO   ARI\n" +
                " WHERE EII.BARCODE = ALI.BARCODE(+)\n" +
                " AND ESI.ITEM_CODE = EII.ITEM_CODE\n" +
                " AND ARI.BARCODE(+) = ALI.BARCODE\n" +
                " AND EII.BARCODE = ?";
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
            AmsLandInfoDTO amsLandInfo = (AmsLandInfoDTO) dtoParameter;
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
                    + " (? IS NULL OR BARCODE LIKE ?)"
                    + " AND (? IS NULL OR RENT_PERSON LIKE ?)"
                    + " AND (? IS NULL OR RENT_DATE LIKE ?)"
                    + " AND (? IS NULL OR AREA_UNIT LIKE ?)"
                    + " AND (? IS NULL OR RENTAL LIKE ?)"
                    + " AND (? IS NULL OR MONEY_UNIT LIKE ?)"
                    + " AND (? IS NULL OR PAY_TYPE LIKE ?)"
                    + " AND (? IS NULL OR END_DATE LIKE ?)"
                    + " AND (? IS NULL OR LAND_AREA LIKE ?)"
                    + " AND (? IS NULL OR RENT_USAGE LIKE ?)"
                    + " AND (? IS NULL OR REMARK LIKE ?)";
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
            sqlArgs.add(amsLandInfo.getLandArea());
            sqlArgs.add(amsLandInfo.getLandArea());
            sqlArgs.add(amsLandInfo.getRentUsage());
            sqlArgs.add(amsLandInfo.getRentUsage());
            sqlArgs.add(amsLandInfo.getRemark());
            sqlArgs.add(amsLandInfo.getRemark());

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
     *
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
        AmsLandInfoDTO amsLandInfo = (AmsLandInfoDTO) dtoParameter;
        if (foreignKey.equals("barcode")) {
            sqlModel = getDataByBarcodeModel(amsLandInfo.getBarcode());
        }
        return sqlModel;
    }

    /**
     * 功能：根据外键关联字段 barcode 构造数据删除SQL。
     * 框架自动生成数据租赁土地资产(EAM) AMS_LAND_INFO数据删除SQLModel，请根据实际需要修改。
     *
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
        AmsLandInfoDTO amsLandInfo = (AmsLandInfoDTO) dtoParameter;
        if (foreignKey.equals("barcode")) {
            sqlModel = getDeleteByBarcodeModel(amsLandInfo.getBarcode());
        }
        return sqlModel;
    }

    /**
     * 功能：框架自动生成租赁土地资产(EAM) AMS_LAND_INFO页面翻页查询SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回页面翻页查询SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getPageQueryModel() throws SQLModelException {   //查询使用的sql
        SQLModel sqlModel = new SQLModel();
             try {
        List sqlArgs = new ArrayList();
        AmsLandInfoDTO amsLandInfo = (AmsLandInfoDTO) dtoParameter;
        String sqlStr = "SELECT" +
                " EII.SYSTEMID,\n" +
                " EII.ADDRESS_ID,\n" +
                " EII.BARCODE,\n" +
                " ALI.LAND_AREA,\n" +
                " AMS_PUB_PKG.GET_FLEX_VALUE(ALI.AREA_UNIT, ?) AREA_UNIT,\n" +
                " ALI.LAND_CERTFICATE_NO,\n" +
                " AMS_PUB_PKG.GET_COUNTY_NAME(EII.ADDRESS_ID) COUNTY_NAME,\n" +
                " CASE WHEN ALI.IS_RENT='Y' THEN '是' ELSE '否' END IS_RENT ,\n" +
                " ARI.RENT_PERSON,\n" +
                " ARI.RENT_DATE,\n" +
                " ALI.REMARK,\n" +
                " ALI.LAND_ADDRESS,\n" +
                " ARI.RENTAL,\n" +
                " ARI.MONEY_UNIT,\n" +
                " AMS_PUB_PKG.GET_FLEX_VALUE(ARI.PAY_TYPE, ?) PAY_TYPE,\n" +
                " ARI.END_DATE\n" +
                " FROM ETS_ITEM_INFO EII, AMS_LAND_INFO ALI, AMS_RENT_INFO ARI,\n" +
                "      ETS_SYSTEM_ITEM ESI\n" +
                " WHERE ESI.ITEM_CODE = EII.ITEM_CODE\n" +
                " AND EII.BARCODE = ALI.BARCODE(+)\n" +
                " AND ALI.BARCODE = ARI.BARCODE(+)\n" +
                " AND  ESI.ITEM_CATEGORY = 'LAND'\n" +
                " AND ( EII.BARCODE(+) LIKE NVL(?,EII.BARCODE(+)))\n" +
//                    " AND (ALI.IS_RENT(+) = NVL(?,ALI.IS_RENT(+)))\n" +
                " AND EII.ORGANIZATION_ID =? \n" /*+
                "  AND ARI.RENT_DATE(+) >= nvl(?,ARI.RENT_DATE(+))\n" +
                "         AND ARI.RENT_DATE(+) <= nvl(?,ARI.RENT_DATE(+))" ;*/+

                " AND (? IS NULL OR ARI.RENT_DATE >= ?)  " +
                "AND (? IS NULL OR ARI.RENT_DATE <= ?)";

        sqlArgs.add(DictConstant.LAND_AREA_UNIT);
        sqlArgs.add(DictConstant.PAY_TYPE);
//            sqlArgs.add(amsLandInfo.getRentPerson());
//            sqlArgs.add(amsLandInfo.getRentPerson());
        sqlArgs.add(amsLandInfo.getBarcode());
        //sqlArgs.add(amsLandInfo.getBarcode());
//            sqlArgs.add(amsLandInfo.getIsRent());
        //sqlArgs.add(amsLandInfo.getIsRent());
//            sqlArgs.add(amsLandInfo.getRentUsage());
//            sqlArgs.add(amsLandInfo.getRentUsage());
        //sqlArgs.add(amsLandInfo.getRentDate());
        //sqlArgs.add(amsLandInfo.getRentDate());
        //sqlArgs.add(amsLandInfo.getRentDate());
        //sqlArgs.add(amsLandInfo.getRentDate());
        sqlArgs.add(sfUser.getOrganizationId());
        sqlArgs.add(amsLandInfo.getFromDate());
        sqlArgs.add(amsLandInfo.getFromDate());
        sqlArgs.add(amsLandInfo.getToDate());
        sqlArgs.add(amsLandInfo.getToDate());

        if (amsLandInfo.getHasCertficate().equals("Y")) {
            sqlStr += " AND ALI.LAND_CERTFICATE_NO IS NOT NULL";
        } else if (amsLandInfo.getHasCertficate().equals("N")) {
            sqlStr += " AND ALI.LAND_CERTFICATE_NO IS  NULL";
        }
        if (amsLandInfo.getIsRent().equals("Y")) {
            sqlStr += " AND EII.ATTRIBUTE1='RENT'";
        } else if (amsLandInfo.getIsRent().equals("N")) {
            sqlStr += " AND EII.ATTRIBUTE1 IS NULL";
        }
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        } catch (CalendarException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
        }
        return sqlModel;
    }


    /**
     * 功能：获取判断当前用户是否有权限执行数据编辑操作。BARCODE 的存在性
     *
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


    public SQLModel doCreatRentData() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            AmsLandInfoDTO amsLandInfoDTO = (AmsLandInfoDTO) dtoParameter;
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
                    "  (AMS_RENT_INFO_S.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), ?)";

            sqlArgs.add(amsLandInfoDTO.getBarcode());
            sqlArgs.add(amsLandInfoDTO.getRentPerson());
            sqlArgs.add(amsLandInfoDTO.getRentDate());
            sqlArgs.add(amsLandInfoDTO.getRental());
            sqlArgs.add(amsLandInfoDTO.getMoneyUnit());
            sqlArgs.add(amsLandInfoDTO.getPayType());
            sqlArgs.add(amsLandInfoDTO.getEndDate());
//            sqlArgs.add(amsHouseInfo.getIsRent());
            sqlArgs.add(amsLandInfoDTO.getRentUsage());
            sqlArgs.add(sfUser.getOrganizationId());

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
        }
        return sqlModel;
    }

    public SQLModel doDeleteRentData() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsLandInfoDTO amsLandInfoDTO = (AmsLandInfoDTO) dtoParameter;
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
    public SQLModel getAttribute1Model(String barcode) {
            SQLModel sqlModel = new SQLModel();
            List sqlArgs = new ArrayList();
            AmsLandInfoDTO amsHouseInfo = (AmsLandInfoDTO) dtoParameter;
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
            AmsLandInfoDTO amsHouseInfo = (AmsLandInfoDTO) dtoParameter;
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
}