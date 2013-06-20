package com.sino.ams.system.object.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.system.basepoint.dto.EtsObjectDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.SQLModelException;
import com.sino.config.SinoConfig;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.soa.common.MIS_CONSTANT;

/**
 * Created by IntelliJ IDEA.
 * User: zjs
 * Date: 2008-6-26
 * Time: 20:17:06
 * Function:地点批量导入.
 */
public class ImportObjectModel extends AMSSQLProducer {

    /**
     * 功能：设备在网数量 AMS_ITEM_ON_NET 数据库SQL构造层构造函数
     *
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsItemOnNetDTO 本次操作的数据
     */
    public ImportObjectModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
    }


    /**
     * 功能：框架自动生成设备在网数量 AMS_ITEM_ON_NET数据插入SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据插入用SQLModel
     */
    public SQLModel getDataCreateModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsObjectDTO eoDTO = (EtsObjectDTO) dtoParameter;
        String sqlStr = "INSERT INTO" +
                "   ETS_OBJECT \n" +
                "  (WORKORDER_OBJECT_NO,\n" +
                "   WORKORDER_OBJECT_CODE,\n" +
                "   WORKORDER_OBJECT_NAME,\n" +
                "   WORKORDER_OBJECT_LOCATION,\n" +
                "   ORGANIZATION_ID,\n" +
                "   COUNTY_CODE,\n" +
                "   IS_TD,\n" +
                "   OBJECT_CATEGORY,\n" +
                "   ISALL,\n" +
                "   CREATED_BY,\n" +
                "   AREA_TYPE,\n" +
                "   REMARK,\n" +
                "   LOCATION_CODE,\n" +
                "   LAST_UPDATE_DATE,\n" +
                "   LAST_UPDATE_BY,\n" +
                "   CITY,\n" +
                "   COUNTY,\n" +
                "   LOCATION,\n" +
                "	BTS_NO,\n" +
                "   LOC2_CODE, \n" +
                "   LOC2_DESC \n"
                + " ) VALUES ( \n"
                + " ?, ?, ?, ?, ?, ?, ?, ?, 1, ?, ?, ?, ?, GETDATE(), ?, ?, ?, ?, ?," +
                "SUBSTRING(?,dbo.CHARINDEX_3('.', ?, 1) + 1,LEN(?) - dbo.CHARINDEX_3('.', ?, 1) - 4)" +
                ",SUBSTRING(?,charindex('.',?)+1,len(?)-charindex('.',reverse(?))-charindex('.',?)))\n";
        sqlArgs.add(eoDTO.getWorkorderObjectNo());
        sqlArgs.add(eoDTO.getWorkorderObjectCode());
        sqlArgs.add(eoDTO.getWorkorderObjectName());
        sqlArgs.add(eoDTO.getWorkorderObjectName());
        sqlArgs.add(userAccount.getOrganizationId());
        sqlArgs.add(eoDTO.getCountyCode());
        sqlArgs.add(eoDTO.getIsTd());
        sqlArgs.add(eoDTO.getObjectCategory());

        sqlArgs.add(userAccount.getUserId());
        sqlArgs.add(eoDTO.getAreaType());
        sqlArgs.add(eoDTO.getRemark());
        sqlArgs.add(eoDTO.getWorkorderObjectCode());

        sqlArgs.add(userAccount.getUserId());
        sqlArgs.add(eoDTO.getCityCode());
        sqlArgs.add(eoDTO.getCountyCode2());
        sqlArgs.add(eoDTO.getLocation());
        sqlArgs.add(eoDTO.getBtsNo());

        sqlArgs.add(eoDTO.getWorkorderObjectCode());
        sqlArgs.add(eoDTO.getWorkorderObjectCode());
        sqlArgs.add(eoDTO.getWorkorderObjectCode());
        sqlArgs.add(eoDTO.getWorkorderObjectCode());
        sqlArgs.add(eoDTO.getWorkorderObjectName());
        sqlArgs.add(eoDTO.getWorkorderObjectName());
        sqlArgs.add(eoDTO.getWorkorderObjectName());
        sqlArgs.add(eoDTO.getWorkorderObjectName());
        sqlArgs.add(eoDTO.getWorkorderObjectName());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成资产地点表(EAM) ETS_OBJECT数据插入SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据插入用SQLModel
     */
    public SQLModel getAOACreateModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsObjectDTO etsObject = (EtsObjectDTO) dtoParameter;
        String sqlStr = "INSERT INTO "
                + " AMS_OBJECT_ADDRESS("
                + " ADDRESS_ID,"
                + " OBJECT_NO,"
                + " ORGANIZATION_ID,"
                + " CREATED_BY,"
                + " REMARK \n"
                + ") VALUES ("
                + "  NEWID(), ?, ?, ?, ?)";

        sqlArgs.add(etsObject.getWorkorderObjectNo());
        sqlArgs.add(userAccount.getOrganizationId());
        sqlArgs.add(userAccount.getUserId());
        sqlArgs.add("地点信息导入创建");
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成设备在网数量 AMS_ITEM_ON_NET数据更新SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据更新用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getDataUpdateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsObjectDTO eoDTO = (EtsObjectDTO) dtoParameter;
        String sqlStr = "UPDATE AMS_ITEM_ON_NET"
                + " SET"
                + " QUANTITY = ?,"
                + " LAST_UPDATE_DATE = GETDATE(),"
                + " LAST_UPDATE_USER = ?"
                + " WHERE"
                + " ID = ?";

        sqlArgs.add(userAccount.getUserId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    /**
     * 功能：框架自动生成设备在网数量 AMS_ITEM_ON_NET数据删除SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据删除用SQLModel
     */
    public SQLModel getDataDeleteModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsObjectDTO eoDTO = (EtsObjectDTO) dtoParameter;
        String sqlStr = "DELETE FROM  AMS_ITEM_ON_NET";
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成设备在网数量 AMS_ITEM_ON_NET数据详细信息查询SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    public SQLModel getPrimaryKeyDataModel() { //明细查询SQL
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsObjectDTO eoDTO = (EtsObjectDTO) dtoParameter;
        String sqlStr = " SELECT " +
                " AION.ID,\n" +
                " AION.ORGANIZATION_ID,\n" +
                " AMS_PUB_PKG.GET_ORGNIZATION_NAME(AION.ORGANIZATION_ID) ORGNIZATION_NAME,\n" +
                " AION.PART_NO, --备件部件号\\n \n" +
                " AMSC.ITEM_NAME,\n" +
                " AMSC.ITEM_SPEC,\n" +
                " AMSC.SPARE_USAGE,\n" +
                " AMS_PUB_PKG.GET_VENDOR_NAME(AMSC.VENDOR_ID) VENDOR_NAME,\n" +
                " AION.QUANTITY\n" +
                " FROM " +
                " AMS_ITEM_ON_NET AION, " +
                " AMS_SPARE_CATEGORY AMSC\n" +
                " WHERE " +
                " AION.PART_NO = AMSC.BARCODE\n" +
                " AND AION.ID = ?";
//        sqlArgs.add(eoDTO.getId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


    /**
     * 功能：框架自动生成设备在网数量 AMS_ITEM_ON_NET页面翻页查询SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回页面翻页查询SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsObjectDTO eoDTO = (EtsObjectDTO) dtoParameter;
        String sqlStr = "SELECT "
                + " AION.ID,\n"
                + " AION.ORGANIZATION_ID,\n"
                + " AMS_PUB_PKG.GET_ORGNIZATION_NAME(AION.ORGANIZATION_ID) ORGNIZATION_NAME,\n"
                + " AION.PART_NO, \n"
                + " AMSC.ITEM_NAME,\n"
                + " AMSC.ITEM_SPEC,\n"
                + " AMSC.SPARE_USAGE,\n"
                + " AMS_PUB_PKG.GET_VENDOR_NAME(AMSC.VENDOR_ID) VENDOR_NAME,\n"
                + " AION.QUANTITY\n"
                + " FROM "
                + " AMS_ITEM_ON_NET AION, AMS_SPARE_CATEGORY AMSC\n"
                + " WHERE "
                + " AION.PART_NO = AMSC.BARCODE"
                + " AND ( AION.PART_NO LIKE dbo.NVL(?,AION.PART_NO))"
                + " AND ( " + SyBaseSQLUtil.isNull() + "  OR AMSC.ITEM_NAME LIKE ?)"
                + " AND ( " + SyBaseSQLUtil.isNull() + "  OR AMSC.ITEM_SPEC LIKE ?)"
                + " AND ( " + SyBaseSQLUtil.isNull() + "  OR AMS_PUB_PKG.GET_VENDOR_NAME(AMSC.VENDOR_ID) LIKE ?)"
                + " AND ( " + SyBaseSQLUtil.isNull() + "  OR AMSC.SPARE_USAGE LIKE ?)"
                + " AND ( " + SyBaseSQLUtil.isNull() + "  OR AION.ORGANIZATION_ID LIKE ?)";

        sqlArgs.add(eoDTO.getOrganizationId());
        sqlArgs.add(eoDTO.getOrganizationId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


    public SQLModel getObjectCodeLogModel(String codeName, String codeError) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE AMS_OBJECT_IMPORT\n" +
                "   SET OBJECT_CODE_ERROR = ?\n" +
                " WHERE LOCATION = ?\n" +
                " AND ORGANIZATION_ID = ?"
                ;

        sqlArgs.add(codeError);
        sqlArgs.add(codeName);
        sqlArgs.add(userAccount.getOrganizationId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getObjectNameLogModel(String code, String codeNameError) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE AMS_OBJECT_IMPORT\n" +
                "   SET OBJECT_NAME_ERROR = ?\n" +
                " WHERE LOCATION_CODE = ?\n"
                + " AND ORGANIZATION_ID = ?";
        sqlArgs.add(codeNameError);
        sqlArgs.add(code);
        sqlArgs.add(userAccount.getOrganizationId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


    /**
     * 功能：构造地点专业错误更新记录SQL
     *
     * @param code          String
     * @param codeName      String
     * @param category      String
     * @param categoryError String
     * @return SQLModel
     * @throws SQLModelException
     */
    public SQLModel getObjectCategoryLogModel(String code, String codeName, String category, String categoryError) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE"
                + " AMS_OBJECT_IMPORT"
                + " SET"
                + " OBJECT_CATEGORY_ERROR = ?"
                + " WHERE"
                + " WORKORDER_OBJECT_CODE = ?"
                + " AND WORKORDER_OBJECT_NAME = ?"
                + " AND COUNTY_CODE = ?";
        sqlArgs.add(categoryError);
        sqlArgs.add(code);
        sqlArgs.add(codeName);
        sqlArgs.add(category);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：构造行政区划错误更新记录SQL
     *
     * @param locationCode  String
     * @param areaTypeError String
     * @return SQLModel
     * @throws SQLModelException
     */
    public SQLModel getAreaTypeLogModel(String locationCode, String areaTypeError) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr =
                "UPDATE AMS_OBJECT_IMPORT \n" +
                        "   SET AREA_TYPE_ERROR = ? \n" +
                        " WHERE LOCATION_CODE = ? \n";
        sqlArgs.add(areaTypeError);
        sqlArgs.add(locationCode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：构造区县ID错误更新记录SQL
     *
     * @param code         String
     * @param countryError String
     * @return SQLModel
     * @throws SQLModelException
     */
    public SQLModel getAreaCodeLogModel(String code, String countryError) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE"
                + " AMS_OBJECT_IMPORT"
                + " SET"
                + " COUNTY_CODE_ERROR = ?"
                + " WHERE"
                + " WORKORDER_OBJECT_CODE = ?"
                + " AND ORGANIZATION_ID = ?";
        sqlArgs.add(countryError);
        sqlArgs.add(code);
        sqlArgs.add(userAccount.getOrganizationId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


    /**
     * 功能：构造地点是否存在的SQL
     *
     * @return SQLModel
     */
    public SQLModel getObjectCodeExistModel(String objectCode) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT 1\n" +
                "  FROM ETS_OBJECT      EO\n" +
//                "       ETS_OU_CITY_MAP EOCM\n" +
                " WHERE EO.ORGANIZATION_ID = ? AND EO.WORKORDER_OBJECT_CODE = ?\n";//+
//                "       AND EOCM.COMPANY_CODE = ?";
        sqlArgs.add(userAccount.getOrganizationId());
        sqlArgs.add(objectCode);
//        sqlArgs.add(userAccount.getCompanyCode());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getObjectCodeOrganizationIdModel(String objectCode) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT ORGANBIZATION_ID\n" +
                "  FROM ETS_OBJECT      EO\n" +
                "       ETS_OU_CITY_MAP EOCM\n" +
                " WHERE EO.WORKORDER_OBJECT_CODE = ?\n";//+
//                "       AND EOCM.COMPANY_CODE = ?";
        sqlArgs.add(objectCode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：得到区域
     *
     * @param dictCode
     */
    public SQLModel isExistAreaType(String dictCode) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT EFV.CODE, EFV.VALUE FROM \n" +
                "ETS_FLEX_VALUES EFV, ETS_FLEX_VALUE_SET EFVS WHERE EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID AND EFV.ENABLED = 'Y' " +
                "AND EFVS.CODE='ADDR_AREA_TYPE' AND EFV.CODE = ?";
        sqlArgs.add(dictCode);
        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }

    /**
     * 功能：构造地点专业SQL
     *
     * @param obCategory String
     * @return SQLModel
     */
    public SQLModel OCModel(String obCategory) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT"
                + " 1"
                + " FROM"
                + " ETS_FLEX_VALUES EFV,"
                + " ETS_FLEX_VALUE_SET EFVS"
                + " WHERE"
                + " EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID"
                + " AND EFVS.CODE = 'OBJECT_CATEGORY'"
                //+ " AND EFV.CODE = ?";
                + " AND EFV.ATTRIBUTE2 = ? \n";
        sqlArgs.add(obCategory);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


    /**
     * 功能：框架自动生成设备在网数量 AMS_ITEM_ON_NET数据插入SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据插入用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel hasErrorModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsObjectDTO eoDTO = (EtsObjectDTO) dtoParameter;
        String sqlStr = "SELECT 1\n"
                + " FROM "
                + " AMS_OBJECT_IMPORT AOI\n"
                + " WHERE AOI.ORGANIZATION_ID=?  "
                + " AND ( " + SyBaseSQLUtil.isNotNull("AOI.OBJECT_CODE_ERROR") + " \n"
                + " OR  " + SyBaseSQLUtil.isNotNull("AOI.OBJECT_NAME_ERROR") + " \n"
                + " OR  " + SyBaseSQLUtil.isNotNull("AOI.OBJECT_CATEGORY_ERROR") + " "
                + " OR  " + SyBaseSQLUtil.isNotNull("AOI.COMPANY_CODE_ERROR") + " "
                + " OR  " + SyBaseSQLUtil.isNotNull("AOI.IS_TD_ERROR") + " "
                + " OR  " + SyBaseSQLUtil.isNotNull("AOI.COUNTY_CODE_ERROR") + " )";

        sqlArgs.add(userAccount.getOrganizationId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：插入到接口表。
     *
     * @return SQLModel 返回数据插入用SQLModel
     */
    public SQLModel insertImportModel(String countyCode) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsObjectDTO eoDTO = (EtsObjectDTO) dtoParameter;

        String sqlStr = "INSERT INTO "
                + " AMS_OBJECT_IMPORT ("
                + " WORKORDER_OBJECT_CODE,"
                + " WORKORDER_OBJECT_NAME,"
                + " LOCATION_CODE,\n"
                + " LOCATION,\n"
                + " CITY,\n"
                + " COUNTY,\n"
                + " OBJECT_CATEGORY,\n"
                + " COUNTY_CODE,\n"
                + " ORGANIZATION_ID,\n"
                + " AREA_TYPE,\n"
                + " REMARK,\n"
                + " IS_TD,\n"
                + " COMPANY_CODE, \n"
                + " BTS_NO \n"
                + ") VALUES ("
                + " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        sqlArgs.add(eoDTO.getWorkorderObjectCode());
        sqlArgs.add(eoDTO.getWorkorderObjectName());
        sqlArgs.add(eoDTO.getLocationCode());
        sqlArgs.add(eoDTO.getLocation());
        sqlArgs.add(eoDTO.getCity());
        sqlArgs.add(eoDTO.getCounty());
        sqlArgs.add(eoDTO.getObjectCategory());
        if (countyCode.equals("")) {
            sqlArgs.add(eoDTO.getCountyCode());
        } else {
            sqlArgs.add(countyCode);
        }
        sqlArgs.add(userAccount.getOrganizationId());
        sqlArgs.add(eoDTO.getAreaType());
        sqlArgs.add(eoDTO.getRemark());
        sqlArgs.add(eoDTO.getIsTd());
        sqlArgs.add(eoDTO.getCompanyCode());
        sqlArgs.add(eoDTO.getBtsNo());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


    /**
     * @param countyCode String
     * @return SQLModel
     */
    public SQLModel hasCountyModel(String countyCode) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "";
        if (SinoConfig.getProvinceCode().equals(DictConstant.PROVINCE_CODE_JIN)) {
            sqlStr = "SELECT FFV.FLEX_VALUE, FFV.DESCRIPTION \n" +
                    "  FROM M_FND_FLEX_VALUE_SETS FFVS, \n" +
                    "       M_FND_FLEX_VALUES     FFV \n" +
                    " WHERE FFVS.FLEX_VALUE_SET_ID = FFV.FLEX_VALUE_SET_ID \n" +
                    "   AND FFVS.FLEX_VALUE_SET_NAME = ? \n" +
                    "	AND FFVS.SOURCE = ? \n" +
                    "   AND FFV.ENABLED_FLAG = 'Y' \n" +
                    "   AND FFV.FLEX_VALUE = ? \n";
        } else {
            sqlStr = "SELECT FFV.FLEX_VALUE, FFV.DESCRIPTION \n" +
                    "  FROM M_FND_FLEX_VALUE_SETS FFVS, \n" +
                    "       M_FND_FLEX_VALUES     FFV, \n" +
                    "       ETS_OU_CITY_MAP       EOCM \n" +
                    " WHERE FFVS.FLEX_VALUE_SET_ID = FFV.FLEX_VALUE_SET_ID \n" +
                    "   AND FFVS.FLEX_VALUE_SET_NAME = ? \n" +
                    "	AND FFVS.SOURCE = ? \n" +
                    "   AND FFV.COMPANY_CODE = EOCM.COMPANY_CODE \n" +
                    "   AND FFV.ENABLED_FLAG = 'Y' \n" +
                    "   AND FFV.FLEX_VALUE = ? \n";
        }

        if (userAccount.getIsTd().equals("Y")) {
            sqlArgs.add(SinoConfig.getLoc1SetNameTD());
            sqlArgs.add(MIS_CONSTANT.SOURCE_TD);
        } else {
            sqlArgs.add(SinoConfig.getLoc1SetNameMis());
            sqlArgs.add(MIS_CONSTANT.SOURCE_MIS);
        }
        sqlArgs.add(countyCode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getCityExistModel(String city){
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT 1\n" +
                "FROM   AMS_COUNTY AC\n" +
                "WHERE  AC.COUNTY_NAME = ?\n" +
                "       AND (AC.PARENT_CODE IS NULL OR AC.PARENT_CODE = '')";
        sqlArgs.add(city);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    public SQLModel getCountyExistModel(String city, String county){
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT 1\n" +
                "FROM   AMS_COUNTY AC,\n" +
                "       AMS_COUNTY ACP\n" +
                "WHERE  AC.PARENT_CODE = ACP.COUNTY_CODE\n" +
                "       AND AC.COUNTY_NAME = ?\n" +
                "       AND ACP.COUNTY_NAME = ?\n" +
                "       AND (ACP.PARENT_CODE IS NULL OR ACP.PARENT_CODE = '')";
        sqlArgs.add(county);
        sqlArgs.add(city);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    /**
     * 功能：表 AMS_ON_NET_IMPORT 的查询sql。
     *
     * @return SQLModel 返回多条数据信息查询用SQLModel
     */
    public SQLModel getQueryImportModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT "
                + " AOI.WORKORDER_OBJECT_CODE,"
                + " AOI.WORKORDER_OBJECT_NAME,"
                + " AOI.OBJECT_CATEGORY,"
                + " AOI.COUNTY_CODE,"
                + " AOI.IS_TD,"
                + " AOI.COMPANY_CODE,"
                + " AOI.AREA_TYPE,"
                + " AOI.REMARK,"
                + " AOI.OBJECT_CODE_ERROR,"
                + " AOI.OBJECT_NAME_ERROR,"
                + " AOI.OBJECT_CATEGORY_ERROR,"
                + " AOI.IS_TD_ERROR,"
                + " AOI.COMPANY_CODE_ERROR,"
                + " AOI.COUNTY_CODE_ERROR,"
                + " AOI.CITY,"
                + " AOI.COUNTY,"
                + " AOI.LOCATION,"
                + " AOI.BTS_NO,"
                + " AOI.AREA_TYPE_ERROR"
                + " FROM"
                + " AMS_OBJECT_IMPORT AOI"
                + " WHERE"
                + "     LTRIM(AOI.OBJECT_CODE_ERROR) IS NULL \n"
                + " AND LTRIM(AOI.AREA_TYPE_ERROR) IS NULL \n"
                + " AND LTRIM(AOI.OBJECT_NAME_ERROR) IS NULL \n"
                + " AND LTRIM(AOI.OBJECT_CATEGORY_ERROR) IS NULL \n"
                + " AND LTRIM(AOI.COUNTY_CODE_ERROR) IS NULL \n"
                + " AND AOI.ORGANIZATION_ID = ? \n";
        sqlArgs.add(userAccount.getOrganizationId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：表 AMS_ON_NET_IMPORT 的查询sql。
     *
     * @return SQLModel 返回多条数据信息查询用SQLModel
     */
    public SQLModel getImportErrorModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT "
                + " AOI.WORKORDER_OBJECT_CODE,"
                + " AOI.CITY,"
                + " AOI.COUNTY,"
                + " AOI.WORKORDER_OBJECT_NAME,"
                + " AOI.OBJECT_CATEGORY,"
                + " AOI.COMPANY_CODE,"
                + " AOI.IS_TD,"
                + " AOI.COUNTY_CODE,"
                + " AOI.REMARK,"
                + " AOI.AREA_TYPE,"
                + " AOI.OBJECT_CODE_ERROR,"
                + " AOI.OBJECT_NAME_ERROR,"
                + " AOI.OBJECT_CATEGORY_ERROR,"
                + " AOI.COMPANY_CODE_ERROR,"
                + " AOI.IS_TD_ERROR,"
                + " AOI.COUNTY_CODE_ERROR,"
                + " AOI.CITY_ERROR,"
                + " AOI.COUNTY_ERROR,"
                + " AOI.AREA_TYPE_ERROR,"
                + " AOI.BTS_NO "
                + " FROM"
                + " AMS_OBJECT_IMPORT AOI"
                + " WHERE"
                + " ( " + SyBaseSQLUtil.isNotNull("AOI.OBJECT_CODE_ERROR") + " "
                + " OR  " + SyBaseSQLUtil.isNotNull("AOI.OBJECT_NAME_ERROR") + " "
                + " OR  " + SyBaseSQLUtil.isNotNull("AOI.OBJECT_CATEGORY_ERROR") + " "
                + " OR  " + SyBaseSQLUtil.isNotNull("AOI.COMPANY_CODE_ERROR") + " "
                + " OR  " + SyBaseSQLUtil.isNotNull("AOI.IS_TD_ERROR") + " "
                + " OR  " + SyBaseSQLUtil.isNotNull("AOI.CITY_ERROR") + " "
                + " OR  " + SyBaseSQLUtil.isNotNull("AOI.COUNTY_ERROR") + " "
                + " OR  " + SyBaseSQLUtil.isNotNull("AOI.AREA_TYPE_ERROR") + " "
                + " OR  " + SyBaseSQLUtil.isNotNull("AOI.COUNTY_CODE_ERROR") + " )"
                + " AND AOI.ORGANIZATION_ID = ?";
        sqlArgs.add(userAccount.getOrganizationId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


    /**
     * 功能：导入数据从接口表到正式表。
     *
     * @return SQLModel 返回数据更新用SQLModel
     */
    public SQLModel getImpOnNetModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsObjectDTO eoDTO = (EtsObjectDTO) dtoParameter;
        String sqlStr = "INSERT INTO ETS_OBJECT EO\n" +
                "  (EO.WORKORDER_OBJECT_NO,\n" +
                "   EO.WORKORDER_OBJECT_CODE,\n" +
                "   EO.WORKORDER_OBJECT_NAME,\n" +
                "   EO.WORKORDER_OBJECT_LOCATION,\n" +
                "   EO.ORGANIZATION_ID,\n" +
                "   EO.COUNTY_CODE,\n" +
                "   EO.OBJECT_CATEGORY,\n" +
                "   EO.ISALL,\n" +
                "   EO.CREATED_BY,\n" +
                "   EO.REMARK)\n" +
                "  (SELECT  NEWID() ,\n" +
                "          AOI.WORKORDER_OBJECT_CODE,\n" +
                "          AOI.WORKORDER_OBJECT_NAME,\n" +
                "          AOI.WORKORDER_OBJECT_NAME,\n" +
                "          \n" +
                "          AOI.COUNTY_CODE,\n" +
                "          AOI.OBJECT_CATEGORY,\n" +
                "          '1',\n" +
                "          \n" +
                "          AOI.REMARK,\n" +
                "     FROM AMS_OBJECT_IMPORT AOI)";

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：删除接口表的数据。
     *
     * @return SQLModel 返回数据更新用SQLModel
     */
    public SQLModel deleteImportModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM"
                + " AMS_OBJECT_IMPORT"
                + " WHERE ORGANIZATION_ID = ?";
        sqlArgs.add(userAccount.getOrganizationId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * @param objectCode  String
     * @return SQLModel
     */
    public SQLModel getObjectCodeSelfDuplicateModel(String objectCode) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT 1\n" +
                "  FROM (SELECT COUNT(*) UM\n" +
                "          FROM AMS_OBJECT_IMPORT AOI\n" +
                "         WHERE AOI.WORKORDER_OBJECT_CODE = ?\n" +
                "               AND AOI.ORGANIZATION_ID = ?\n" +
                "         GROUP BY AOI.WORKORDER_OBJECT_CODE) ACC\n" +
                " WHERE ACC.UM > 1";
        sqlArgs.add(objectCode);
        sqlArgs.add(userAccount.getOrganizationId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * @param workorderName  String
     * @param organizationId String
     * @return SQLModel
     */
    public SQLModel doubleNameModel(String workorderName, String organizationId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsObjectDTO eoDTO = (EtsObjectDTO) dtoParameter;
        String sqlStr = "SELECT 1\n" +
                "  FROM (SELECT COUNT(*) UM\n" +
                "          FROM AMS_OBJECT_IMPORT AOI\n" +
                "         WHERE AOI.WORKORDER_OBJECT_NAME = ?\n" +
                "         GROUP BY AOI.PART_NO) ACC\n" +
                " WHERE ACC.UM > 1";
        sqlArgs.add(workorderName);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


    public SQLModel getCityCodeModel(String county) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT AC.PARENT_CODE CITY_CODE\n" +
                "  FROM AMS_COUNTY AC\n" +
                " WHERE AC.COUNTY_NAME = ?";
        sqlArgs.add(county);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getCountyCodeModel(String county) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT AC.COUNTY_CODE\n" +
                "  FROM AMS_COUNTY AC\n" +
                " WHERE AC.COUNTY_NAME = ?";
        sqlArgs.add(county);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


    public SQLModel getCityErrorLogModel(String locationCode, String codeError) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE AMS_OBJECT_IMPORT\n" +
                "   SET CITY_ERROR = ?\n" +
                " WHERE LOCATION_CODE = ?\n"
                + " AND ORGANIZATION_ID = ?";

        sqlArgs.add(codeError);
        sqlArgs.add(locationCode);
        sqlArgs.add(userAccount.getOrganizationId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getCountyErrorLogModel(String locationCode, String codeError) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE AMS_OBJECT_IMPORT\n" +
                "   SET COUNTY_ERROR = ?\n" +
                " WHERE LOCATION_CODE = ?";

        sqlArgs.add(codeError);
        sqlArgs.add(locationCode);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getNewId() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT NEWID() NEWID \n";
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getObjectCategoryCode(String objectCategory) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr =
                "SELECT EFV.CODE ,EFV.DESCRIPTION FROM ETS_FLEX_VALUES EFV, ETS_FLEX_VALUE_SET EFVS \n" +
                        " WHERE EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID \n" +
                        "   AND EFVS.CODE = 'OBJECT_CATEGORY' \n" +
                        "   AND EFV.ATTRIBUTE2 = ? \n";

        sqlArgs.add(objectCategory);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 获取区域代码
     *
     * @param str
     * @return
     */
    public SQLModel getAreaCountyCode(String str) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT TOP 1 COUNTY_CODE FROM ETS_COUNTY WHERE COUNTY_CODE_MIS=?";
        sqlArgs.add(str);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 增加地点信息ETS_OBJECT_LOC2 (Excel地点第二段信息导入)
     */
    public SQLModel createDoEtsObjectLoc() {
        EtsObjectDTO eoDTO = (EtsObjectDTO) dtoParameter;
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr =
                "INSERT INTO ETS_OBJECT_LOC2\n" +
                        "  (LOC2_CODE,\n" +
                        "   LOC2_DESC,\n" +
                        "	COMPANY_CODE,\n" +
                        "   DISABLE_DATE,\n" +
                        "   OBJECT_CATEGORY,\n" +
                        "   OBJECT_CATEGORY_NAME,\n" +
                        "   AREA_TYPE,\n" +
                        "   CITY,\n" +
                        "   COUNTY,\n" +
                        "   BTS_NO,\n" +
                        "   LATITUDE_LONGITUDE,\n" +
                        "   AUXILIARY_INFO,\n" +
                        "   REMARK,\n" +
                        "   CREATION_DATE,\n" +
                        "   CREATED_BY,\n" +
                        "   LAST_UPDATE_DATE,\n" +
                        "   LAST_UPDATE_BY,\n" +
                        "	SHARE_TYPE)\n" +
                        "VALUES\n" +
                        "  (SUBSTRING(?,dbo.CHARINDEX_3('.', ?, 1) + 1,LEN(?) - dbo.CHARINDEX_3('.', ?, 1) - 4)," +
                        "	SUBSTRING(?,charindex('.',?)+1,len(?)-charindex('.',reverse(?))-charindex('.',?))," +
                        "	SUBSTRING(SUBSTRING(?,dbo.CHARINDEX_3('.', ?, 1) + 1,LEN(?) - dbo.CHARINDEX_3('.', ?, 1) - 4),1,4),NULL,?,?,?,?,?,?,?,?,?,GETDATE(),?,NULL,NULL,?)";
        sqlArgs.add(eoDTO.getWorkorderObjectCode());
        sqlArgs.add(eoDTO.getWorkorderObjectCode());
        sqlArgs.add(eoDTO.getWorkorderObjectCode());
        sqlArgs.add(eoDTO.getWorkorderObjectCode());
        
        sqlArgs.add(eoDTO.getWorkorderObjectName());
        sqlArgs.add(eoDTO.getWorkorderObjectName());
        sqlArgs.add(eoDTO.getWorkorderObjectName());
        sqlArgs.add(eoDTO.getWorkorderObjectName());
        sqlArgs.add(eoDTO.getWorkorderObjectName());
        
        sqlArgs.add(eoDTO.getWorkorderObjectCode());
        sqlArgs.add(eoDTO.getWorkorderObjectCode());
        sqlArgs.add(eoDTO.getWorkorderObjectCode());
        sqlArgs.add(eoDTO.getWorkorderObjectCode());
        sqlArgs.add(eoDTO.getObjectCategory());
        sqlArgs.add(eoDTO.getObjectCategoryName());
        sqlArgs.add(eoDTO.getAreaType());
        sqlArgs.add(eoDTO.getCityCode());
        sqlArgs.add(eoDTO.getCountyCode2());
        sqlArgs.add(eoDTO.getBtsNo());
        sqlArgs.add(eoDTO.getLatitudeLongitude());
        sqlArgs.add(eoDTO.getAuxiliaryInfo());
        sqlArgs.add(eoDTO.getRemark());
        sqlArgs.add(eoDTO.getCreatedBy());
        if (userAccount.getIsTd().equals("N")) {
        	sqlArgs.add("1");
        } else {
        	sqlArgs.add("3");
		}

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 判断地点第二段代码是否存在
     *
     * @param workorderObjectCode
     * @return
     */
    public SQLModel getIsExistsLocCode(String workorderObjectCode) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr =
                "SELECT 1 \n" +
                        "  FROM ETS_OBJECT_LOC2\n" +
                        " WHERE LOC2_CODE = \n" +
                        "       (SUBSTRING(?,\n" +
                        "                  dbo.CHARINDEX_3('.', ?, 1) + 1,\n" +
                        "                  LEN(?) - dbo.CHARINDEX_3('.', ?, 1) - 4))";

        sqlArgs.add(workorderObjectCode);
        sqlArgs.add(workorderObjectCode);
        sqlArgs.add(workorderObjectCode);
        sqlArgs.add(workorderObjectCode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


    /**
     * 功能：构造判断基站或营业厅编号是否存在的SQL
     * @return SQLModel
     */
    public SQLModel getBTSNoExistModel(String btsNo) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT"
                + " 1"
                + " FROM"
                + " ETS_OBJECT_LOC2 EOL"
                + " WHERE"
                + " EOL.BTS_NO = ?";
        sqlArgs.add(btsNo);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getLocCode2ByDescModel(String locDesc) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT EOL.LOC2_CODE\n" +
                "FROM   ETS_OBJECT_LOC2 EOL\n" +
                "WHERE  EOL.LOC2_DESC = ?\n";
        sqlArgs.add(locDesc);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    
    public SQLModel getLocDesc2ByDescModel(String locCode) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT EOL.LOC2_DESC\n" +
                "FROM   ETS_OBJECT_LOC2 EOL\n" +
                "WHERE  EOL.LOC2_CODE = ?\n";
        sqlArgs.add(locCode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    
    /**
     * 功能：构造地点名称是否存在的SQL
     *
     * @return SQLModel
     */
    public SQLModel getObjectNameExistModel(String objectName) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT 1\n" +
                "  FROM ETS_OBJECT      EO\n" +
                " WHERE EO.ORGANIZATION_ID = ? \n" +
                " 	AND EO.WORKORDER_OBJECT_NAME = ?\n";
        sqlArgs.add(userAccount.getOrganizationId());
        sqlArgs.add(objectName);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    
    /**
     * 功能：获取地点下是否有资产的SQL
     *
     * @return 获取地点下是否有资产的SQL
     */
    public SQLModel getLocationAssetsModel(String objectCode) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT 1\n" +
                "FROM   ETS_OBJECT        EO,\n" +
                "       AMS_OBJECT_IMPORT AOI\n" +
                "WHERE  EO.ORGANIZATION_ID = ?\n" +
                "       AND EO.WORKORDER_OBJECT_CODE = AOI.WORKORDER_OBJECT_CODE\n" +
                "       AND AOI.WORKORDER_OBJECT_CODE = ?\n" +
                "       AND EXISTS (SELECT NULL\n" +
                "        FROM   AMS_OBJECT_ADDRESS AOA,\n" +
                "               ETS_ITEM_INFO      EII\n" +
                "        WHERE  EO.WORKORDER_OBJECT_NO = AOA.OBJECT_NO\n" +
                "               AND AOA.ADDRESS_ID = EII.ADDRESS_ID)";
        sqlArgs.add(userAccount.getOrganizationId());
        sqlArgs.add(objectCode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    
    /**
     * 修改地点信息
     */
    public SQLModel updateEtsObjectInfo(EtsObjectDTO eoDTO) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        
        String sqlStr =
            " UPDATE ETS_OBJECT" +
                    " SET WORKORDER_OBJECT_NAME = STR_REPLACE(WORKORDER_OBJECT_NAME, SUBSTRING(WORKORDER_OBJECT_NAME,charindex('.',WORKORDER_OBJECT_NAME)+1,len(WORKORDER_OBJECT_NAME)-4 - charindex('.',WORKORDER_OBJECT_NAME)), SUBSTRING(?,charindex('.',?)+1,len(?)-charindex('.',reverse(?))-charindex('.',?))), " +
                    " WORKORDER_OBJECT_LOCATION	= STR_REPLACE(WORKORDER_OBJECT_LOCATION, SUBSTRING(WORKORDER_OBJECT_LOCATION,charindex('.',WORKORDER_OBJECT_LOCATION)+1,len(WORKORDER_OBJECT_LOCATION)-4 - charindex('.',WORKORDER_OBJECT_LOCATION)), SUBSTRING(?,charindex('.',?)+1,len(?)-charindex('.',reverse(?))-charindex('.',?)))," +
                    " LOC2_DESC = SUBSTRING(?,charindex('.',?)+1,len(?)-charindex('.',reverse(?))-charindex('.',?))," +
                    " COUNTY_CODE = ?," +
                    " OBJECT_CATEGORY = ?," +
                    " AREA_TYPE = ?," +
                    " CITY = ?," +
                    " COUNTY = ?," +
                    " LAST_UPDATE_BY = ?," +
                    " LAST_UPDATE_DATE = GETDATE()," +
                    " BTS_NO = ?" +
                    " WHERE patindex('[0-9][0-9][0-9][0-9][0-9][0-9].[0-9][0-9][0-9][0-9][A-Za-z][A-Za-z][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9].___',WORKORDER_OBJECT_CODE)<>0 " +
                    " AND LOC2_CODE = SUBSTRING(?,dbo.CHARINDEX_3('.', ?, 1) + 1,LEN(?) - dbo.CHARINDEX_3('.', ?, 1) - 4)";
	    sqlArgs.add(eoDTO.getWorkorderObjectName());
	    sqlArgs.add(eoDTO.getWorkorderObjectName());
	    sqlArgs.add(eoDTO.getWorkorderObjectName());
	    sqlArgs.add(eoDTO.getWorkorderObjectName());
	    sqlArgs.add(eoDTO.getWorkorderObjectName());
	    
	    sqlArgs.add(eoDTO.getWorkorderObjectName());
	    sqlArgs.add(eoDTO.getWorkorderObjectName());
	    sqlArgs.add(eoDTO.getWorkorderObjectName());
	    sqlArgs.add(eoDTO.getWorkorderObjectName());
	    sqlArgs.add(eoDTO.getWorkorderObjectName());
	    
	    sqlArgs.add(eoDTO.getWorkorderObjectName());
	    sqlArgs.add(eoDTO.getWorkorderObjectName());
	    sqlArgs.add(eoDTO.getWorkorderObjectName());
	    sqlArgs.add(eoDTO.getWorkorderObjectName());
	    sqlArgs.add(eoDTO.getWorkorderObjectName());
	    
	    sqlArgs.add(eoDTO.getCountyCode());
	    sqlArgs.add(eoDTO.getObjectCategory());
	    sqlArgs.add(eoDTO.getAreaType());
	    sqlArgs.add(eoDTO.getCityCode());
	    sqlArgs.add(eoDTO.getCountyCode2());
	    sqlArgs.add(userAccount.getUserId());
	    sqlArgs.add(eoDTO.getBtsNo());
	    sqlArgs.add(eoDTO.getWorkorderObjectCode());
	    sqlArgs.add(eoDTO.getWorkorderObjectCode());
	    sqlArgs.add(eoDTO.getWorkorderObjectCode());
	    sqlArgs.add(eoDTO.getWorkorderObjectCode());
        
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    
    /**
     * 修改物理地点信息
     */
    public SQLModel updateEtsObjectLocInfo(EtsObjectDTO eoDTO) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr =
                " UPDATE ETS_OBJECT_LOC2" +
                        " SET LOC2_DESC = SUBSTRING(?,charindex('.',?)+1,len(?)-charindex('.',reverse(?))-charindex('.',?)), " +
                        " OBJECT_CATEGORY = ?," +
                        " AREA_TYPE = ?," +
                        " CITY = ?," +
                        " COUNTY = ?," +
                        " LAST_UPDATE_BY = ?," +
                        " LAST_UPDATE_DATE = GETDATE()," +
                        " BTS_NO = ?," +
                        " SHARE_TYPE = ?" +
                        " WHERE LOC2_CODE= SUBSTRING(?,dbo.CHARINDEX_3('.', ?, 1) + 1,LEN(?) - dbo.CHARINDEX_3('.', ?, 1) - 4)";
        sqlArgs.add(eoDTO.getWorkorderObjectName());
        sqlArgs.add(eoDTO.getWorkorderObjectName());
        sqlArgs.add(eoDTO.getWorkorderObjectName());
        sqlArgs.add(eoDTO.getWorkorderObjectName());
        sqlArgs.add(eoDTO.getWorkorderObjectName());
        
        sqlArgs.add(eoDTO.getObjectCategory());
        sqlArgs.add(eoDTO.getAreaType());
        sqlArgs.add(eoDTO.getCityCode());
        sqlArgs.add(eoDTO.getCountyCode2());
        sqlArgs.add(userAccount.getUserId());
        sqlArgs.add(eoDTO.getBtsNo());
        sqlArgs.add(eoDTO.getShareType());
        sqlArgs.add(eoDTO.getWorkorderObjectCode());
        sqlArgs.add(eoDTO.getWorkorderObjectCode());
        sqlArgs.add(eoDTO.getWorkorderObjectCode());
        sqlArgs.add(eoDTO.getWorkorderObjectCode());
        
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    
    /**
     * 修改地点失效日期
     */
    public SQLModel disabledEtsObject(String objectCode) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr =
                " UPDATE ETS_OBJECT" +
                        " SET DISABLE_DATE= GETDATE()" +
                        " WHERE WORKORDER_OBJECT_CODE=? ";
        sqlArgs.add(objectCode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

}
