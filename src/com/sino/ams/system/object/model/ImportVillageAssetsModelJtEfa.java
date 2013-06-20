package com.sino.ams.system.object.model;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.system.object.dto.ImportVillageAssetsDTOJtEfa;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

import java.util.ArrayList;
import java.util.List;

public class ImportVillageAssetsModelJtEfa extends AMSSQLProducer {
    private SfUserDTO sfUser = null;

    public ImportVillageAssetsModelJtEfa(SfUserDTO userAccount, ImportVillageAssetsDTOJtEfa dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
    }

    public SQLModel deleteImportModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM"
                + " AMS_VILLAGE_ASSETS_IMPORT_JT_EFA "
                + " WHERE"
                + " USER_ID = ?";
        sqlArgs.add(userAccount.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel insertImportModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
//        ImportVillageAssetsDTOJt eoDTO = (ImportVillageAssetsDTOJt) dtoParameter;
        String sqlStr = "INSERT INTO AMS_VILLAGE_ASSETS_IMPORT_JT_EFA \n" +
                "  (COMPANY_CODE,\n" +
                "   BARCODE,\n" +
                "   ITEM_NAME,\n" +
                "   ITEM_SPEC,\n" +
                "   EMPLOYEE_NUMBER,\n" +
                "   EMPLOYEE_NAME,\n" +
                "   WORKORDER_OBJECT_CODE,\n" +
                "   WORKORDER_OBJECT_NAME,\n" +
                "   ACTUAL_QTY,\n" +
                "   CONTENT_CODE,\n" +
                "   CONTENT_NAME,\n" +
                "   PRICE,\n" +
                "   VILLAGE_START_DATE,\n" +
                "   TF_DEPRECIATION,\n" +
                "   TF_NET_ASSET_VALUE,\n" +
                "   TF_DEPRN_COST,\n" +
                "   PROJECTID,\n" +
                "   PROJECT_NAME,\n" +

                "   DEPRECIATION_ACCOUNT,\n" +
                "   ASSETS_CREATE_DATE,\n" +
                "   DEPRN_LEFT_MONTH,\n" +
                "   DATE_RETIRED,\n" +
                "   MANUFACTURER_NAME,\n" +
                
                "   SCRAP_VALUE,\n" +
                "   DEPRN_AMOUNT,\n" +
                "   YTD_DEPRN,\n" +
                "   DEPRN_RESERVE,\n" +
                "   IMPAIR_AMOUNT,\n" +
                "   YTD_IMPAIRMENT,\n" +
                "   IMPAIR_RESERVE,\n" +
                
                "   CONSTRUCT_STATUS,\n" +
                "   IS_SHARE,\n" +
                "   REMARK,\n" +
                "   USER_ID)\n" +
//                "   ASSET_ID)\n" +
                " VALUES \n" +
                "   (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) \n" ;
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }

    public SQLModel validateSameEiiBarcode(String barcode) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT 1 FROM ETS_ITEM_INFO EII WHERE EII.BARCODE = ?";
        sqlArgs.add(barcode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel validateSameBarcode(String barcode) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT COUNT(1) BAR_QTY\n" +
                "FROM   AMS_VILLAGE_ASSETS_IMPORT_JT_EFA AVAI\n" +
                "WHERE  AVAI.BARCODE = ?\n" +
                "AND    AVAI.USER_ID = ?";
        sqlArgs.add(barcode);
        sqlArgs.add(userAccount.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel insertImprotErrorData(String barcode, String codeError) throws
            SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE"
                + " AMS_VILLAGE_ASSETS_IMPORT_JT_EFA "
                + " SET"
                + " ERROR = ?"
                + " WHERE"
                + " BARCODE = ?";
        sqlArgs.add(codeError);
        sqlArgs.add(barcode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel validateItemNS(String itemName, String itemSpec) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT 1\n" +
                         " FROM ETS_SYSTEM_ITEM ESI, \n" +
                              " ETS_SYSITEM_DISTRIBUTE ESD\n" +
                        " WHERE ESI.ITEM_CODE = ESD.ITEM_CODE \n" +
                          " AND ESI.ITEM_NAME = ? \n" +
                          " AND ESI.ITEM_SPEC = ? \n" +
                          " AND ESD.ORGANIZATION_ID = ?";
        sqlArgs.add(itemName);
        sqlArgs.add(itemSpec);
        sqlArgs.add(userAccount.getOrganizationId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel validateObjectCode(String newObjectCode) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT 1 FROM ETS_OBJECT EO WHERE EO.WORKORDER_OBJECT_CODE = ? AND EO.ORGANIZATION_ID = ?";
        sqlArgs.add(newObjectCode);
        sqlArgs.add(userAccount.getOrganizationId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel validateContentCode(String contentCode) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT 1 FROM AMS_CONTENT_DIC ACD WHERE ACD.CONTENT_CODE = ?";
        sqlArgs.add(contentCode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel validateNewResDept(String newResDept) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT 1 FROM AMS_MIS_DEPT AMD WHERE AMD.DEPT_CODE = ?";
        sqlArgs.add(newResDept);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel validateNewEmployeeNum(String newEmployeeNum) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT 1 FROM AMS_MIS_EMPLOYEE AME WHERE AME.EMPLOYEE_NUMBER = ?";
        sqlArgs.add(newEmployeeNum);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel hasErrorModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        ImportVillageAssetsDTOJtEfa eoDTO = (ImportVillageAssetsDTOJtEfa) dtoParameter;
        String sqlStr = "SELECT 1 FROM AMS_VILLAGE_ASSETS_IMPORT_JT_EFA AVAL WHERE AVAL.ERROR IS NOT NULL AND AVAL.USER_ID = ?";
        sqlArgs.add(userAccount.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getImportErrorModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = 
        		"SELECT AVAI.COMPANY_CODE,\n" +
                "       AVAI.BARCODE,\n" +
                "       AVAI.ITEM_NAME,\n" +
                "       AVAI.ITEM_SPEC,\n" +
                "       AVAI.EMPLOYEE_NUMBER,\n" +
                "       AVAI.EMPLOYEE_NAME,\n" +
                "       AVAI.WORKORDER_OBJECT_CODE,\n" +
                "       AVAI.WORKORDER_OBJECT_NAME,\n" +
                "       AVAI.ACTUAL_QTY,\n" +
                "       AVAI.CONTENT_CODE,\n" +
                "       AVAI.CONTENT_NAME,\n" +
                "       AVAI.PRICE,\n" +
                "       AVAI.VILLAGE_START_DATE,\n" +
                "       AVAI.TF_DEPRECIATION,\n" +
                "       AVAI.TF_NET_ASSET_VALUE,\n" +
                "       AVAI.TF_DEPRN_COST,\n" +
                "       AVAI.PROJECTID,\n" +
                "       AVAI.PROJECT_NAME,\n" +
                
                "   	AVAI.DEPRECIATION_ACCOUNT,\n" +
                "   	AVAI.ASSETS_CREATE_DATE,\n" +
                "   	AVAI.DEPRN_LEFT_MONTH,\n" +
                "   	AVAI.DATE_RETIRED,\n" +
                "   	AVAI.MANUFACTURER_NAME,\n" +
                
                "   	AVAI.SCRAP_VALUE,\n" +
                "   	AVAI.DEPRN_AMOUNT,\n" +
                "   	AVAI.YTD_DEPRN,\n" +
                "   	AVAI.DEPRN_RESERVE,\n" +
                "   	AVAI.IMPAIR_AMOUNT,\n" +
                "   	AVAI.YTD_IMPAIRMENT,\n" +
                "   	AVAI.IMPAIR_RESERVE,\n" +
                
                "   	AVAI.CONSTRUCT_STATUS,\n" +
                "   	AVAI.IS_SHARE,\n" +
                "   	AVAI.REMARK,\n" +
                "   	AVAI.USER_ID,\n" +
                "   	AVAI.ASSET_ID,\n" +
                "		AVAI.ERROR \n" +

                "FROM   AMS_VILLAGE_ASSETS_IMPORT_JT_EFA AVAI\n" +
                "WHERE  AVAI.USER_ID = ?\n" +
                "AND    LTRIM(AVAI.ERROR) IS NOT NULL\n" +
                "ORDER BY AVAI.ERROR";
        sqlArgs.add(userAccount.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getQueryImportModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT AVAI.COMPANY_CODE,\n" +
                "       AVAI.BARCODE,\n" +
                "       AVAI.ITEM_NAME,\n" +
                "       AVAI.ITEM_SPEC,\n" +
                "       AVAI.EMPLOYEE_NUMBER,\n" +
                "       AVAI.EMPLOYEE_NAME,\n" +
                "       AVAI.WORKORDER_OBJECT_CODE,\n" +
                "       AVAI.WORKORDER_OBJECT_NAME,\n" +
                "       AVAI.FINANCE_PROP,\n" +
                "       AVAI.ITEM_QTY,\n" +
                "       AVAI.ACTUAL_QTY,\n" +
                "       AVAI.EQUIPMENT_PERFORMANCE,\n" +
                "       AVAI.CONTENT_CODE,\n" +
                "       AVAI.CONTENT_NAME,\n" +
                "       AVAI.SPECIALITY_DEPT,\n" +
                "       AVAI.MAINTAIN_USER,\n" +
                "       AVAI.PRICE,\n" +
                "       AVAI.VILLAGE_START_DATE START_DATE,\n" +
                "       AVAI.TF_DEPRECIATION,\n" +
                "       AVAI.TF_NET_ASSET_VALUE,\n" +
                "       AVAI.TF_DEPRN_COST,\n" +
                "       AVAI.PROJECTID,\n" +
                "       AVAI.PROJECT_NAME,\n" +
                "       AVAI.CONSTRUCT_STATUS,\n" +
                "       AVAI.IS_SHARE,\n" +
                "       AVAI.MANUFACTURER_ID,\n" +
                "       AVAI.REMARK\n" +
                "FROM   AMS_VILLAGE_ASSETS_IMPORT_JT_EFA AVAI\n" +
                "WHERE  AVAI.ERROR IS NULL\n" +
                "       AND AVAI.USER_ID = ?";
        sqlArgs.add(userAccount.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getItemCodeModel(String itemName, String itemSpec) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT ESI.ITEM_CODE FROM ETS_SYSTEM_ITEM ESI WHERE ESI.ITEM_NAME = ? AND ESI.ITEM_SPEC = ?";
        sqlArgs.add(itemName);
        sqlArgs.add(itemSpec);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getAddressIdModel(String objectCode) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT AOA.ADDRESS_ID " +
                         " FROM ETS_OBJECT EO, " +
                              " AMS_OBJECT_ADDRESS AOA " +
                        " WHERE EO.WORKORDER_OBJECT_NO = AOA.OBJECT_NO " +
                          " AND EO.WORKORDER_OBJECT_CODE = ?";
        sqlArgs.add(objectCode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getEmployeeIdModel(String employeeNumber) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT AME.EMPLOYEE_ID FROM AMS_MIS_EMPLOYEE AME WHERE AME.EMPLOYEE_NUMBER = ?";
        sqlArgs.add(employeeNumber);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

        public SQLModel getDataCreateModel() throws SQLModelException {
            SQLModel sqlModel = new SQLModel();
            List sqlArgs = new ArrayList();
            try {
                ImportVillageAssetsDTOJtEfa eoDTO = (ImportVillageAssetsDTOJtEfa) dtoParameter;
                String sqlStr = 
                		"INSERT INTO ETS_ITEM_INFO \n" +
                        "  (SYSTEMID,\n" +
                        "   BARCODE,\n" +
                        "   ITEM_CODE,\n" +
                        "   RESPONSIBILITY_DEPT,\n" +
                        "   RESPONSIBILITY_USER,\n" +
                        "   ADDRESS_ID,\n" +
                        "   CONTENT_CODE,\n" +
                        "   CONTENT_NAME,\n" +
                        "   SPECIALITY_DEPT,\n" +
                        "   MAINTAIN_USER,\n" +
                        "   MAINTAIN_DEPT,\n" +
                        "   START_DATE,\n" +
                        "   PRICE,\n" +
                        "   REMARK,\n" +
                        "   OTHER_INFO,\n" +
                        "   CREATION_DATE,\n" +
                        "   ATTRIBUTE1,\n" +
                        "   ORGANIZATION_ID,\n" +
                        "   PROJECTID,\n" +
                        "   LNE_ID,\n" +
                        "   CEX_ID,\n" +
                        "   OPE_ID,\n" +
                        "   NLE_ID,\n" +
                        "   MANUFACTURER_ID,\n" +
                        "   POWER,\n" +
                        "   IS_SHARE,\n" +
                        "   FINANCE_PROP,\n" +
                        "   ITEM_QTY,\n" +
                        "   ACTUAL_QTY,\n" +
                        "   CONSTRUCT_STATUS,\n" +
                        "   TF_NET_ASSET_VALUE,\n" +
                        "   TF_DEPRN_COST,\n" +
                        "   TF_DEPRECIATION,\n" +
                        "   CREATED_BY)\n" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), 'CT', ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                sqlArgs.add(eoDTO.getSystemid());
                sqlArgs.add(eoDTO.getBarcode());
                sqlArgs.add(eoDTO.getItemCode());
                sqlArgs.add(eoDTO.getResponsibilityDept());
                sqlArgs.add(eoDTO.getEmployeeId());
                sqlArgs.add(eoDTO.getAddressId());
                sqlArgs.add(eoDTO.getContentCode());
                sqlArgs.add(eoDTO.getContentName());
                sqlArgs.add(eoDTO.getSpecialityDept());
                sqlArgs.add(eoDTO.getMaintainUser());
                sqlArgs.add(eoDTO.getMaintainDept());
                sqlArgs.add(eoDTO.getStartDate());
                sqlArgs.add(eoDTO.getPrice());
                sqlArgs.add(eoDTO.getRemark());
                sqlArgs.add(eoDTO.getEquipmentPerformance());
                sqlArgs.add(userAccount.getOrganizationId());
                sqlArgs.add(eoDTO.getProjectid());
                sqlArgs.add(eoDTO.getLneId());
                sqlArgs.add(eoDTO.getCexId());
                sqlArgs.add(eoDTO.getOpeId());
                sqlArgs.add(eoDTO.getNleId());
                sqlArgs.add(eoDTO.getManufacturerId());
                sqlArgs.add(eoDTO.getPower());
                sqlArgs.add(eoDTO.getShare());
                sqlArgs.add(eoDTO.getFinanceProp());
                sqlArgs.add(eoDTO.getItemQty());
                sqlArgs.add(eoDTO.getActualQty());
                sqlArgs.add(eoDTO.getConstructStatus());
                sqlArgs.add(eoDTO.getTfNetAssetValue());
                sqlArgs.add(eoDTO.getTfDeprnCost());
                sqlArgs.add(eoDTO.getTfDepreciation());
                sqlArgs.add(userAccount.getUserId());
                sqlModel.setSqlStr(sqlStr);
                sqlModel.setArgs(sqlArgs);
            } catch (CalendarException ex) {
                ex.printLog();
                throw new SQLModelException(ex);
            }
            return sqlModel;
    }

    public SQLModel validateOU(String companyCode) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT 1 FROM ETS_OU_CITY_MAP EOCM WHERE EOCM.COMPANY_CODE = ? AND EOCM.ORGANIZATION_ID = ?";
        sqlArgs.add(companyCode);
        sqlArgs.add(userAccount.getOrganizationId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel validateEmployee(String deptCode, String employeeNumber) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT 1 FROM AMS_MIS_EMPLOYEE AME WHERE AME.DEPT_CODE = ? AND AME.EMPLOYEE_NUMBER = ?";
        sqlArgs.add(deptCode);
        sqlArgs.add(employeeNumber);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getDeptCodeModel(String employeeNumber) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT AME.DEPT_CODE FROM AMS_MIS_EMPLOYEE AME WHERE AME.EMPLOYEE_NUMBER = ?";
        sqlArgs.add(employeeNumber);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel validateProject(String projectid) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT 1 FROM ETS_PA_PROJECTS_ALL EPPA WHERE EPPA.PROJECT_ID = ?";
        sqlArgs.add(projectid);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel validateManufacturer(String manufacturerId) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT 1 FROM AMS_MANUFACTURER AM WHERE AM.MANUFACTURER_NAME = ?";
        sqlArgs.add(manufacturerId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel validateNewShareStatus(String NewShareStatus) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT 1\n" +
                "  FROM ETS_FLEX_VALUE_SET EFVS,\n" +
                "       ETS_FLEX_VALUES    EFV\n" +
                " WHERE EFVS.FLEX_VALUE_SET_ID = EFV.FLEX_VALUE_SET_ID\n" +
                "   AND EFVS.CODE = 'SHARE_STATUS'\n" +
                "   AND EFV.VALUE = ?";
        sqlArgs.add(NewShareStatus);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel validateNewContractStatus(String NewContractStatus) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT 1\n" +
                "  FROM ETS_FLEX_VALUE_SET EFVS,\n" +
                "       ETS_FLEX_VALUES    EFV\n" +
                " WHERE EFVS.FLEX_VALUE_SET_ID = EFV.FLEX_VALUE_SET_ID\n" +
                "   AND EFVS.CODE = 'CONSTRUCT_STATUS'\n" +
                "   AND EFV.VALUE = ?";
        sqlArgs.add(NewContractStatus);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getManufacturerIdModel(String manufacturerName) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT AM.MANUFACTURER_ID FROM AMS_MANUFACTURER AM WHERE AM.MANUFACTURER_NAME = ?";
        sqlArgs.add(manufacturerName);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getConstructStatusIdModel(String constructStatusName) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT EFV.CODE\n" +
                "  FROM ETS_FLEX_VALUE_SET EFVS,\n" +
                "       ETS_FLEX_VALUES    EFV\n" +
                " WHERE EFVS.FLEX_VALUE_SET_ID = EFV.FLEX_VALUE_SET_ID\n" +
                "   AND EFVS.CODE = 'CONSTRUCT_STATUS'\n" +
                "   AND EFV.VALUE = ?";
        sqlArgs.add(constructStatusName);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getShareIdModel(String shareName) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT EFV.CODE\n" +
                "  FROM ETS_FLEX_VALUE_SET EFVS,\n" +
                "       ETS_FLEX_VALUES    EFV\n" +
                " WHERE EFVS.FLEX_VALUE_SET_ID = EFV.FLEX_VALUE_SET_ID\n" +
                "   AND EFVS.CODE = 'SHARE_STATUS'\n" +
                "   AND EFV.VALUE = ?";
        sqlArgs.add(shareName);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
