package com.sino.ams.system.object.model;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.system.object.dto.ImportSpareAssetsDTOJt;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

import java.util.ArrayList;
import java.util.List;

public class ImportSpareAssetsModelJt extends AMSSQLProducer {
    private SfUserDTO sfUser = null;

    public ImportSpareAssetsModelJt(SfUserDTO userAccount, ImportSpareAssetsDTOJt dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
    }

    public SQLModel deleteImportModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM"
                + " AMS_SPARE_ASSETS_IMPORT_JT "
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
//        ImportSpareAssetsDTOJt eoDTO = (ImportSpareAssetsDTOJt) dtoParameter;
        String sqlStr = "INSERT INTO AMS_SPARE_ASSETS_IMPORT_JT\n" +
                "  (COMPANY_CODE,\n" +
                "   BARCODE,\n" +
                "   ITEM_NAME,\n" +
                "   ITEM_SPEC,\n" +
                "   ITEM_UNIT,\n" +
                "   WORKORDER_OBJECT_CODE,\n" +
                "   WORKORDER_OBJECT_NAME,\n" +
                "   RESPONSIBILITY_USER,\n" +
                "   EMPLOYEE_NAME,\n" +
                "   CONTENT_CODE,\n" +
                "   MANUFACTURER_ID,\n" +
                "   ITEM_STATUS,\n" +
                "   SPECIALITY_DEPT,\n" +
                "   SPECIALITY_USER,\n" +
                "   MAINTAIN_USER,\n" +
                "   SPARE_START_DATE,\n" +
                "   REMARK,\n" +
                "   USER_ID,\n" +
                "	SYSTEM_CODE)\n" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NEWID())";
//        sqlArgs.add(eoDTO.getCompanyCode());
//        sqlArgs.add(eoDTO.getBarcode());
//        sqlArgs.add(eoDTO.getItemName());
//        sqlArgs.add(eoDTO.getItemSpec());
//        sqlArgs.add(eoDTO.getItemUnit());
//        sqlArgs.add(eoDTO.getWorkorderObjectCode());
//        sqlArgs.add(eoDTO.getWorkorderObjectName());
//        sqlArgs.add(eoDTO.getResponsibilityUser());
//        sqlArgs.add(eoDTO.getEmployeeName());
//        sqlArgs.add(eoDTO.getContentCode());
//        sqlArgs.add(eoDTO.getManufacturerId());
//        sqlArgs.add(eoDTO.getItemStatus());
//        sqlArgs.add(eoDTO.getSpecialityDept());
//        sqlArgs.add(eoDTO.getSpecialityUser());
//        sqlArgs.add(eoDTO.getMaintainUser());
//        sqlArgs.add(eoDTO.getSpareStartDate());
//        sqlArgs.add(eoDTO.getRemark());
//        sqlArgs.add(userAccount.getUserId());
        sqlModel.setSqlStr(sqlStr);
//        sqlModel.setArgs(sqlArgs);
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
                "FROM   AMS_SPARE_ASSETS_IMPORT_JT ASAIJ\n" +
                "WHERE  ASAIJ.BARCODE = ?\n" +
                "AND    ASAIJ.USER_ID = ?";
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
                + " AMS_SPARE_ASSETS_IMPORT_JT "
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
        String sqlStr = "SELECT 1 FROM AMS_SPARE_ASSETS_IMPORT_JT ASAIJ WHERE ASAIJ.ERROR IS NOT NULL AND ASAIJ.USER_ID = ?";
        sqlArgs.add(userAccount.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getImportErrorModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT ASAIJ.COMPANY_CODE,\n" +
                "       ASAIJ.BARCODE,\n" +
                "       ASAIJ.ITEM_NAME,\n" +
                "       ASAIJ.ITEM_SPEC,\n" +
                "       ASAIJ.ITEM_UNIT,\n" +
                "       ASAIJ.WORKORDER_OBJECT_CODE,\n" +
                "       ASAIJ.WORKORDER_OBJECT_NAME,\n" +
                "       ASAIJ.RESPONSIBILITY_USER,\n" +
                "       ASAIJ.EMPLOYEE_NAME,\n" +
                "       ASAIJ.CONTENT_CODE,\n" +
                "       ASAIJ.MANUFACTURER_ID,\n" +
                "       ASAIJ.ITEM_STATUS,\n" +
                "       ASAIJ.SPECIALITY_DEPT,\n" +
                "       ASAIJ.SPECIALITY_USER,\n" +
                "       ASAIJ.MAINTAIN_USER,\n" +
                "       ASAIJ.SPARE_START_DATE,\n" +
                "       ASAIJ.REMARK,\n" +
                "       ASAIJ.ERROR\n" +
                "FROM   AMS_SPARE_ASSETS_IMPORT_JT ASAIJ\n" +
                "WHERE  ASAIJ.USER_ID = ?\n" +
                "ORDER BY ASAIJ.ERROR";
        sqlArgs.add(userAccount.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getQueryImportModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT ASAIJ.COMPANY_CODE,\n" +
                "       ASAIJ.BARCODE,\n" +
                "       ASAIJ.ITEM_NAME,\n" +
                "       ASAIJ.ITEM_SPEC,\n" +
                "       ASAIJ.ITEM_UNIT,\n" +
                "       ASAIJ.WORKORDER_OBJECT_CODE,\n" +
                "       ASAIJ.RESPONSIBILITY_USER,\n" +
                "       ASAIJ.CONTENT_CODE,\n" +
                "       ASAIJ.MANUFACTURER_ID,\n" +
                "       ASAIJ.ITEM_STATUS,\n" +
                "       ASAIJ.SPECIALITY_DEPT,\n" +
                "       ASAIJ.SPECIALITY_USER,\n" +
                "       ASAIJ.MAINTAIN_USER,\n" +
                "       ASAIJ.SPARE_START_DATE START_DATE,\n" +
                "       ASAIJ.REMARK\n" +
                "FROM   AMS_SPARE_ASSETS_IMPORT_JT ASAIJ\n" +
                "WHERE  ASAIJ.ERROR IS NULL\n" +
                "AND    ASAIJ.USER_ID = ?";
        sqlArgs.add(userAccount.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getItemCodeModel(String itemName, String itemSpec) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT ESI.ITEM_CODE FROM ETS_SYSTEM_ITEM ESI WHERE ESI.ITEM_NAME = ? AND ESI.ITEM_SPEC = ? AND ESI.ITEM_CATEGORY = 'DZYH'";
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

    public SQLModel getDeptCodeModel(String employeeNumber) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT AME.DEPT_CODE FROM AMS_MIS_EMPLOYEE AME WHERE AME.EMPLOYEE_NUMBER = ?";
        sqlArgs.add(employeeNumber);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

        public SQLModel getDataCreateModel() throws SQLModelException {
            SQLModel sqlModel = new SQLModel();
            List sqlArgs = new ArrayList();
            try {
                ImportSpareAssetsDTOJt eoDTO = (ImportSpareAssetsDTOJt) dtoParameter;
                String sqlStr = "INSERT INTO ETS_ITEM_INFO EII\n" +
                        "  (SYSTEMID,\n" +
                        "   BARCODE,\n" +
                        "   ITEM_CODE,\n" +
                        "   ADDRESS_ID,\n" +
                        "   ORGANIZATION_ID,\n" +
                        "   RESPONSIBILITY_DEPT,\n" +
                        "   RESPONSIBILITY_USER,\n" +
                        "   SPECIALITY_DEPT,\n" +
                        "   SPECIALITY_USER,\n" +
                        "   MAINTAIN_USER,\n" +
                        "   START_DATE,\n" +
                        "   REMARK,\n" +
                        "   FINANCE_PROP,\n" +
                        "   CREATION_DATE,\n" +
                        "   CREATED_BY,\n" +
                        "   CONTENT_CODE,\n" +
                        "   MANUFACTURER_ID,\n" +
                        "   ITEM_STATUS)\n" +
                        "VALUES (NEWID(), ?, ?, ?, ?, ?, ?, ?, CONVERT(INT, ?), ?, ?, ?, 'SPARE', GETDATE(), ?, ?, ?, ?) ";
                //sqlArgs.add(eoDTO.getSystemid());
                sqlArgs.add(eoDTO.getBarcode());
                sqlArgs.add(eoDTO.getItemCode());
                sqlArgs.add(eoDTO.getAddressId());
                sqlArgs.add(userAccount.getOrganizationId());
                sqlArgs.add(eoDTO.getResponsibilityDept());
                sqlArgs.add(eoDTO.getResponsEmployeeId());
                sqlArgs.add(eoDTO.getSpecialityDept());
                sqlArgs.add(eoDTO.getSpecialEmployeeId());
                sqlArgs.add(eoDTO.getMaintainUser());
                sqlArgs.add(eoDTO.getStartDate());
                sqlArgs.add(eoDTO.getRemark());
                sqlArgs.add(userAccount.getUserId());
                sqlArgs.add(eoDTO.getContentCode());
                sqlArgs.add(eoDTO.getManufacturerId());
                sqlArgs.add(eoDTO.getItemStatus());
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

    public SQLModel insertSystemItem(String nextItemCode, String itemName, String itemSpec, String itemUnit) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO ETS_SYSTEM_ITEM \n" +
                "  (ITEM_CODE,\n" +
                "   ITEM_NAME,\n" +
                "   ITEM_SPEC,\n" +
                "   ITEM_CATEGORY,\n" +
                "   ITEM_UNIT,\n" +
                "   CREATION_DATE,\n" +
                "   CREATED_BY)\n" +
                "VALUES(?, ?, ?, 'SPARE', ?, GETDATE(), ?)";
        sqlArgs.add(nextItemCode);
        sqlArgs.add(itemName);
        sqlArgs.add(itemSpec);
        sqlArgs.add(itemUnit);
        sqlArgs.add(userAccount.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel insertDistribute(String distributeCode, String itemCode, String orgId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO ETS_SYSITEM_DISTRIBUTE \n" +
                "  (SYSTEM_ID,\n" +
                "   ITEM_CODE,\n" +
                "   ORGANIZATION_ID,\n" +
                "   CREATION_DATE,\n" +
                "   CREATED_BY)\n" +
                "VALUES(?, ?, ?, GETDATE(), ?)";
        sqlArgs.add(distributeCode);
        sqlArgs.add(itemCode);
        sqlArgs.add(orgId);
        sqlArgs.add(userAccount.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel findOu() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT EOCM.ORGANIZATION_ID FROM ETS_OU_CITY_MAP EOCM ";
        sqlModel.setSqlStr(sqlStr);
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

    public SQLModel validateManufacturer(String manufacturerId) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT 1 FROM AMS_MANUFACTURER AM WHERE AM.MANUFACTURER_NAME = ?";
        sqlArgs.add(manufacturerId);
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
}