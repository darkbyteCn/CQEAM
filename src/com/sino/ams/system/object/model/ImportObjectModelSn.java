package com.sino.ams.system.object.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.object.dto.ImportObjectDTOSn;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-6-1
 * Time: 17:47:20
 * To change this template use File | Settings | File Templates.
 */
public class ImportObjectModelSn extends AMSSQLProducer {

    private SfUserDTO sfUser = null;

    public ImportObjectModelSn(SfUserDTO userAccount, ImportObjectDTOSn dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
    }

    public SQLModel getUpdateLocationModel(ImportObjectDTOSn dto) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE AMS_OBJECT_IMPORT\n" +
                "   SET WORKORDER_OBJECT_NAME = ?\n" +
                " WHERE LOCATION_CODE = ?";
        sqlArgs.add(dto.getWorkorderObjectName());
        sqlArgs.add(dto.getLocationCode());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getUpdateLocationCodeModel(ImportObjectDTOSn dto) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE AMS_OBJECT_IMPORT\n" +
                "   SET WORKORDER_OBJECT_CODE = ?\n" +
                " WHERE LOCATION_CODE = ?";
        sqlArgs.add(dto.getWorkorderObjectCode());
        sqlArgs.add(dto.getLocationCode());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getDataCreateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        ImportObjectDTOSn eoDTO = (ImportObjectDTOSn) dtoParameter;
        String sqlStr = "INSERT INTO" +
                "   ETS_OBJECT EO\n" +
                "  (EO.WORKORDER_OBJECT_NO,\n" +
                "   EO.WORKORDER_OBJECT_CODE,\n" +
                "   EO.WORKORDER_OBJECT_NAME,\n" +
                "   EO.WORKORDER_OBJECT_LOCATION,\n" +
                "   EO.ORGANIZATION_ID,\n" +
                "   EO.COUNTY_CODE,\n" +
                "   EO.IS_TD,\n" +
                "   EO.OBJECT_CATEGORY,\n" +
                "   EO.ISALL,\n" +
                "   EO.CREATED_BY,\n" +
                "   EO.AREA_TYPE,\n" +
                "   EO.REMARK,\n" +
                "   EO.LOCATION_CODE,\n" +
                "   EO.LAST_UPDATE_DATE,\n" +
                "   EO.LAST_UPDATE_BY,\n" +
                "   EO.CITY,\n" +
                "   EO.COUNTY,\n" +
                "   EO.LOCATION\n"
                + " ) VALUES ("
                + " ?, ?, ?, ?, ?, ?, ?, ?, '1', ?, ?, ?, ?,GETDATE(),?, ?, ?, ?)";

        sqlArgs.add(eoDTO.getWorkorderObjectNo());
        sqlArgs.add(eoDTO.getWorkorderObjectCode());
        sqlArgs.add(eoDTO.getWorkorderObjectName());
        sqlArgs.add(eoDTO.getWorkorderObjectName());
        sqlArgs.add(eoDTO.getOrganizationId());
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

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    public SQLModel getAOACreateModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        ImportObjectDTOSn etsObject = (ImportObjectDTOSn) dtoParameter;
        String sqlStr = "INSERT INTO "
                + " AMS_OBJECT_ADDRESS("
                + " ADDRESS_ID,"
                + " OBJECT_NO,"
                + " ORGANIZATION_ID"
                + ") VALUES ("
                + " AMS_OBJECT_ADDRESS_S.NEXTVAL, ?, ?)";

        sqlArgs.add(etsObject.getWorkorderObjectNo());
        sqlArgs.add(sfUser.getOrganizationId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getDataUpdateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        ImportObjectDTOSn eoDTO = (ImportObjectDTOSn) dtoParameter;
        String sqlStr = "UPDATE AMS_ITEM_ON_NET"
                + " SET"
                + " QUANTITY = ?,"
                + " LAST_UPDATE_DATE = GETDATE(),"
                + " LAST_UPDATE_USER = ?"
                + " WHERE"
                + " ID = ?";
        sqlArgs.add(sfUser.getUserId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getDataDeleteModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        ImportObjectDTOSn eoDTO = (ImportObjectDTOSn) dtoParameter;
        String sqlStr = "DELETE FROM"
                + " AMS_ITEM_ON_NET";
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        ImportObjectDTOSn eoDTO = (ImportObjectDTOSn) dtoParameter;
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
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        ImportObjectDTOSn eoDTO = (ImportObjectDTOSn) dtoParameter;
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
                + " AND ( AION.PART_NO LIKE NVL(?,AION.PART_NO))"
                + " AND (? IS NULL OR AMSC.ITEM_NAME LIKE ?)"
                + " AND (? IS NULL OR AMSC.ITEM_SPEC LIKE ?)"
                + " AND (? IS NULL OR AMS_PUB_PKG.GET_VENDOR_NAME(AMSC.VENDOR_ID) LIKE ?)"
                + " AND (? IS NULL OR AMSC.SPARE_USAGE LIKE ?)"
                + " AND (? IS NULL OR AION.ORGANIZATION_ID LIKE ?)";

        sqlArgs.add(eoDTO.getOrganizationId());
        sqlArgs.add(eoDTO.getOrganizationId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getUpdateOnNetModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        ImportObjectDTOSn eoDTO = (ImportObjectDTOSn) dtoParameter;
        String sqlStr = "UPDATE AMS_ITEM_ON_NET"
                + " SET"
                + " QUANTITY = ?,"
                + " LAST_UPDATE_DATE = GETDATE(),"
                + " LAST_UPDATE_USER = ?"
                + " WHERE"
                + " ID = ?";
        sqlArgs.add(sfUser.getUserId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel insertPartErrorModel(String code, String codeError) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE"
                + " AMS_OBJECT_IMPORT AOI"
                + " SET"
                + " AOI.OBJECT_CODE_ERROR = ?"
                + " WHERE"
                + " AOI.WORKORDER_OBJECT_CODE = ?";
        sqlArgs.add(codeError);
        sqlArgs.add(code);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel insertEmtyErrorData(String codeName, String objCategory, String countyCode, String codeError) throws
            SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE AMS_OBJECT_IMPORT\n" +
                "   SET OBJECT_CODE_ERROR = ?\n" +
                " WHERE LOCATION = ?";

        sqlArgs.add(codeError);
        sqlArgs.add(codeName);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel inEmtyNameErrorData(String code, String objCategory, String countyCode, String codeNameError) throws
            SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE AMS_OBJECT_IMPORT\n" +
                "   SET OBJECT_NAME_ERROR = ?\n" +
                " WHERE LOCATION_CODE = ?";
        sqlArgs.add(codeNameError);
        sqlArgs.add(code);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel inEmtyCategoryErrorData(String code, String codeName, String category, String categoryError) throws
            SQLModelException {
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

    public SQLModel inEmtyCountyErrorData(String code, String codeName, String category, String countryError) throws
            SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE"
                + " AMS_OBJECT_IMPORT"
                + " SET"
                + " COUNTY_CODE_ERROR = ?"
                + " WHERE"
                + " WORKORDER_OBJECT_CODE = ?"
                + " AND WORKORDER_OBJECT_NAME = ?"
                + " AND OBJECT_CATEGORY = ?";
        sqlArgs.add(countryError);
        sqlArgs.add(code);
        sqlArgs.add(codeName);
        sqlArgs.add(category);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel insertCaErrorModel(String code, String objCategoryError) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE"
                + " AMS_OBJECT_IMPORT"
                + " SET"
                + " OBJECT_CATEGORY_ERROR= ?"
                + " WHERE"
                + " WORKORDER_OBJECT_CODE = ?";
        sqlArgs.add(objCategoryError);
        sqlArgs.add(code);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel insertCtyErrorModel(String code, String ctyError) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE"
                + " AMS_OBJECT_IMPORT AOI"
                + " SET"
                + " AOI.COUNTY_CODE_ERROR= ?"
                + " WHERE"
                + " AOI.WORKORDER_OBJECT_CODE = ?";
        sqlArgs.add(ctyError);
        sqlArgs.add(code);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel insertCountyErrorModel(String barcode, String countyError) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE"
                + " AMS_OBJECT_IMPORT"
                + " SET"
                + " OBJECT_CATEGORY_ERROR= ?"
                + " WHERE"
                + " WORKORDER_OBJECT_CODE = ?";
        sqlArgs.add(countyError);
        sqlArgs.add(barcode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel noBarModel(String code, String companyCode) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT 1\n" +
                "  FROM ETS_OBJECT      EO,\n" +
                "       ETS_OU_CITY_MAP EOCM\n" +
                " WHERE EO.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
                "       AND EO.WORKORDER_OBJECT_CODE = ?\n" +
                "       AND EOCM.COMPANY_CODE = ?";
        sqlArgs.add(code);
        sqlArgs.add(companyCode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel isExistAreaType(String dictCode){
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

    public SQLModel OCModel(String obCategory) throws SQLModelException {
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
                + " AND EFV.CODE=?";
        sqlArgs.add(obCategory);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel hasOnNetModel(String barcode) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = " SELECT 1\n" +
                " FROM " +
                " AMS_ITEM_ON_NET AION\n" +
                " WHERE" +
                " AION.BARCODE =?\n";
        sqlArgs.add(barcode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel hasErrorModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        ImportObjectDTOSn eoDTO = (ImportObjectDTOSn) dtoParameter;
        String sqlStr = "SELECT 1\n"
                + " FROM "
                + " AMS_OBJECT_IMPORT AOI\n"
                + " WHERE AOI.ORGANIZATION_ID=?  "
                + " AND ( " + SyBaseSQLUtil.isNotNull("AOI.OBJECT_CODE_ERROR") + " \n"
                + " OR  " + SyBaseSQLUtil.isNotNull("AOI.OBJECT_NAME_ERROR") + " \n"
                + " OR  " + SyBaseSQLUtil.isNotNull("AOI.OBJECT_CATEGORY_ERROR") + " \n"
                + " OR  " + SyBaseSQLUtil.isNotNull("AOI.COMPANY_CODE_ERROR") + " \n"
                + " OR  " + SyBaseSQLUtil.isNotNull("AOI.IS_TD_ERROR") + " \n"
                + " OR  " + SyBaseSQLUtil.isNotNull("AOI.COUNTY_CODE_ERROR") + " )";

        sqlArgs.add(userAccount.getOrganizationId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel insertImportModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        ImportObjectDTOSn eoDTO = (ImportObjectDTOSn) dtoParameter;
        String sqlStr = "INSERT INTO "
                + " AMS_OBJECT_IMPORT AOI("
                + " AOI.LOCATION_CODE,\n"
                + " AOI.LOCATION,\n"
                + " AOI.CITY,\n"
                + " AOI.COUNTY,\n"
                + " AOI.OBJECT_CATEGORY,\n"
                + " AOI.COUNTY_CODE,\n"
                + " AOI.ORGANIZATION_ID,\n"
                + " AOI.AREA_TYPE,\n"
                + " AOI.REMARK,\n"
                + " AOI.IS_TD,\n"
                + " AOI.COMPANY_CODE\n"
                + ") VALUES ("
                + "  ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        sqlArgs.add(eoDTO.getLocationCode());
        sqlArgs.add(eoDTO.getLocation());
        sqlArgs.add(eoDTO.getCity());
        sqlArgs.add(eoDTO.getCounty());
        sqlArgs.add(eoDTO.getObjectCategory());
        sqlArgs.add(eoDTO.getCountyCode());
        sqlArgs.add(userAccount.getOrganizationId());
        sqlArgs.add(eoDTO.getAreaType());
        sqlArgs.add(eoDTO.getRemark());
        sqlArgs.add(eoDTO.getIsTd());
        sqlArgs.add(eoDTO.getCompanyCode());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel hasCountyModel(String countyCode) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT EC.COUNTY_CODE,\n" +
                "       EC.COUNTY_NAME\n" +
                "  FROM ETS_COUNTY EC\n" +
                " WHERE EC.ENABLED = 'Y'\n" +
                "       AND EC.COUNTY_CODE_MIS = ?";
        sqlArgs.add(countyCode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel hasModel(String organizationId) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT 1 FROM AMS_ITEM_ON_NET AION WHERE AION.PART_NO = ? AND AION.ORGANIZATION_ID = ? ";
        sqlArgs.add(organizationId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getQueryImportModel() throws SQLModelException {
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
                + " AOI.LOCATION"
                + " FROM"
                + " AMS_OBJECT_IMPORT AOI"
                + " WHERE"
                + " AOI.OBJECT_CODE_ERROR IS  NULL"
                + " AND AOI.OBJECT_NAME_ERROR IS  NULL"
                + " AND AOI.OBJECT_CATEGORY_ERROR IS  NULL"
                + " AND AOI.COUNTY_CODE_ERROR IS  NULL"
                + " AND AOI.ORGANIZATION_ID = ?";
        sqlArgs.add(userAccount.getOrganizationId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getImportErrorModel() throws SQLModelException {
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
                + " AOI.OBJECT_CODE_ERROR,"
                + " AOI.OBJECT_NAME_ERROR,"
                + " AOI.OBJECT_CATEGORY_ERROR,"
                + " AOI.COMPANY_CODE_ERROR,"
                + " AOI.IS_TD_ERROR,"
                + " AOI.COUNTY_CODE_ERROR,"
                + " AOI.CITY_ERROR,"
                + " AOI.COUNTY_ERROR"
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
                + " OR  " + SyBaseSQLUtil.isNotNull("AOI.COUNTY_CODE_ERROR") + " )"
                + " AND AOI.ORGANIZATION_ID = ?";
        sqlArgs.add(userAccount.getOrganizationId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getImpOnNetModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        ImportObjectDTOSn eoDTO = (ImportObjectDTOSn) dtoParameter;
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
                "  (SELECT ETS_OBJECT_S.NEXTVAL,\n" +
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

    public SQLModel deleteImportModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM"
                + " AMS_OBJECT_IMPORT AOI"
                + " WHERE"
                + " AOI.ORGANIZATION_ID = ?";
        sqlArgs.add(userAccount.getOrganizationId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel doubleModel(String workorderCode, int organizationId) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        ImportObjectDTOSn eoDTO = (ImportObjectDTOSn) dtoParameter;
        String sqlStr = "SELECT 1\n" +
                "  FROM (SELECT COUNT(*) UM\n" +
                "          FROM AMS_OBJECT_IMPORT AOI\n" +
                "         WHERE AOI.WORKORDER_OBJECT_CODE = ?\n" +
                "         GROUP BY AOI.WORKORDER_OBJECT_CODE) ACC\n" +
                " WHERE ACC.UM > 1";
        sqlArgs.add(workorderCode);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel doubleNameModel(String workorderName, String organizationId) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        ImportObjectDTOSn eoDTO = (ImportObjectDTOSn) dtoParameter;
        String sqlStr = "SELECT 1\n" +
                "  FROM (SELECT COUNT(*) UM\n" +
                "          FROM AMS_OBJECT_IMPORT AOI\n" +
                "         WHERE AOI.WORKORDER_OBJECT_NAME = ?\n" +
                "         GROUP BY AONI.PART_NO) ACC\n" +
                " WHERE ACC.UM > 1";
        sqlArgs.add(workorderName);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel inEtyCompanyErrorData(String code, String companyError) throws
            SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE"
                + " AMS_OBJECT_IMPORT AOI"
                + " SET"
                + " AOI.COMPANY_CODE_ERROR = ?"
                + " WHERE"
                + " AOI.WORKORDER_OBJECT_CODE = ?";
        sqlArgs.add(companyError);
        sqlArgs.add(code);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel hasCompanyModel(String companyCode) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT EOCM.COMPANY_CODE FROM ETS_OU_CITY_MAP EOCM WHERE EOCM.COMPANY_CODE = ?";
        sqlArgs.add(companyCode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel inIsTdErrorData(String code, String tdError) throws
            SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE"
                + " AMS_OBJECT_IMPORT AOI"
                + " SET"
                + " AOI.IS_TD_ERROR = ?"
                + " WHERE"
                + " AOI.WORKORDER_OBJECT_CODE = ?";
        sqlArgs.add(tdError);
        sqlArgs.add(code);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getOrgIdModel(String companyCode) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT EOCM.ORGANIZATION_ID FROM ETS_OU_CITY_MAP EOCM WHERE EOCM.COMPANY_CODE = ?";
        sqlArgs.add(companyCode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getCityCodeModel(String county) throws SQLModelException {
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

    public SQLModel getCountyCode2Model(String county) throws SQLModelException {
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

    public SQLModel getCountyNameModel(String countyCode) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT EC.COUNTY_NAME FROM ETS_COUNTY EC WHERE EC.COUNTY_CODE_MIS = ?";
        sqlArgs.add(countyCode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel insertEmtyCityData(String locationCode, String codeError) throws
            SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE AMS_OBJECT_IMPORT\n" +
                "   SET CITY_ERROR = ?\n" +
                " WHERE LOCATION_CODE = ?";

        sqlArgs.add(codeError);
        sqlArgs.add(locationCode);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel insertEmtyCountyData(String locationCode, String codeError) throws
            SQLModelException {
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
}