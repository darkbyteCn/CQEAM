package com.sino.ams.system.object.model;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.system.object.dto.LastingAssetsDTOJt;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-5-26
 * Time: 11:31:33
 * To change this template use File | Settings | File Templates.
 */
public class ImportLastingAssetsModelJt extends AMSSQLProducer {
    private SfUserDTO sfUser = null;

    public ImportLastingAssetsModelJt(SfUserDTO userAccount, LastingAssetsDTOJt dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
    }

    public SQLModel deleteImportModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM"
                + " AMS_LASTING_ASSETS_IMPORT  "
                + " WHERE"
                + " USER_ID = ?";
        sqlArgs.add(userAccount.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel insertImportModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
//        LastingAssetsDTOJt eoDTO = (LastingAssetsDTOJt) dtoParameter;
        String sqlStr = "INSERT INTO AMS_LASTING_ASSETS_IMPORT\n" +
                "  (COMPANY_CODE,\n" +
                "   BARCODE,\n" +
                "   ITEM_NAME,\n" +
                "   ITEM_SPEC,\n" +
                "   EMPLOYEE_NUMBER,\n" +
                "   EMPLOYEE_NAME,\n" +
                "   WORKORDER_OBJECT_CODE,\n" +
                "   WORKORDER_OBJECT_NAME,\n" +
                "   ITEM_QTY,\n" +
                "   MANUFACTURER_NAME,\n" +
                "   EQUIPMENT_PERFORMANCE,\n" +
                "   CONTENT_CODE,\n" +
                "   CONTENT_NAME,\n" +
                "   SPECIALITY_DEPT,\n" +
                "   MAINTAIN_USER,\n" +
                "   START_DATE,\n" +
                "   END_DATE,\n" +
                "   RENT_PERSON,\n" +
                "   CONTRACT_NUMBER,\n" +
                "   CONTRACT_NAME,\n" +
                "   TENANCY,\n" +
                "   YEAR_RENTAL,\n" +
                "   MONTH_REANTAL,\n" +
                "   REMARK,\n" +
                "   USER_ID\n" +
                "   )\n" +
                "   VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//        sqlArgs.add(eoDTO.getCompanyCode());
//        sqlArgs.add(eoDTO.getBarcode());
//        sqlArgs.add(eoDTO.getItemName());
//        sqlArgs.add(eoDTO.getItemSpec());
//        sqlArgs.add(eoDTO.getEmployeeNumber());
//        sqlArgs.add(eoDTO.getEmployeeName());
//        sqlArgs.add(eoDTO.getWorkorderObjectCode());
//        sqlArgs.add(eoDTO.getWorkorderObjectName());
//        sqlArgs.add(eoDTO.getItemQty());
//        sqlArgs.add(eoDTO.getManufacturerName());
//        sqlArgs.add(eoDTO.getEquipmentPerformance());
//        sqlArgs.add(eoDTO.getContentCode());
//        sqlArgs.add(eoDTO.getContentName());
//        sqlArgs.add(eoDTO.getSpecialityDept());
//        sqlArgs.add(eoDTO.getMaintainUser());
//        sqlArgs.add(eoDTO.getRentStartDate());
//        sqlArgs.add(eoDTO.getRentEndDate());
//        sqlArgs.add(eoDTO.getRentPerson());
//        sqlArgs.add(eoDTO.getContractNumber());
//        sqlArgs.add(eoDTO.getContractName());
//        sqlArgs.add(eoDTO.getTenancy());
//        sqlArgs.add(eoDTO.getYearRental());
//        sqlArgs.add(eoDTO.getMonthReantal());
//        sqlArgs.add(eoDTO.getRemark());
//        sqlArgs.add(userAccount.getUserId());
        sqlModel.setSqlStr(sqlStr);
//        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel validateSameRentBarcode(String barcode) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT 1 FROM AMS_RENT_INFO ARI WHERE ARI.BARCODE = ?";
        sqlArgs.add(barcode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
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
                "FROM   AMS_LASTING_ASSETS_IMPORT ALAI\n" +
                "WHERE  ALAI.BARCODE = ?\n" +
                "AND    ALAI.USER_ID = ?";
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
                + " AMS_LASTING_ASSETS_IMPORT "
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
        LastingAssetsDTOJt eoDTO = (LastingAssetsDTOJt) dtoParameter;
        String sqlStr = "SELECT 1 FROM AMS_LASTING_ASSETS_IMPORT ALAI WHERE ALAI.ERROR IS NOT NULL AND ALAI.USER_ID = ?";
        sqlArgs.add(userAccount.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getImportErrorModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT ALAI.COMPANY_CODE,\n" +
                "       ALAI.BARCODE,\n" +
                "       ALAI.ITEM_NAME,\n" +
                "       ALAI.ITEM_SPEC,\n" +
                "       ALAI.EMPLOYEE_NUMBER,\n" +
                "       ALAI.EMPLOYEE_NAME,\n" +
                "       ALAI.WORKORDER_OBJECT_CODE,\n" +
                "       ALAI.ITEM_QTY,\n" +
                "       ALAI.MANUFACTURER_ID,\n" +
                "       ALAI.POWER,\n" +
                "       ALAI.EQUIPMENT_PERFORMANCE,\n" +
                "       ALAI.CONTENT_CODE,\n" +
                "       ALAI.CONTENT_NAME,\n" +
                "       ALAI.WORKORDER_OBJECT_NAME,\n" +
                "       ALAI.SPECIALITY_DEPT,\n" +
                "       ALAI.MAINTAIN_USER,\n" +
                "       ALAI.MAINTAIN_DEPT,\n" +
                "       ALAI.START_DATE,\n" +
                "       ALAI.END_DATE,\n" +
                "       ALAI.RENT_PERSON,\n" +
                "       ALAI.TENANCY,\n" +
                "       ALAI.YEAR_RENTAL,\n" +
                "       ALAI.MONTH_REANTAL,\n" +
                "       ALAI.REMARK,\n" +
                "       ALAI.ERROR\n" +
                "FROM   AMS_LASTING_ASSETS_IMPORT ALAI\n" +
                "WHERE  ALAI.USER_ID = ?\n" +
                "ORDER BY ALAI.ERROR";
        sqlArgs.add(userAccount.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getPageQueryModel(){
        return getImportErrorModel();
    }

    public SQLModel getImportFailModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT AII.BOOK_TYPE_CODE, \n" +
                              " AII.BARCODE, \n" +
                              " AII.NEW_ITEM_NAME, \n" +
                              " AII.NEW_ITEM_SPEC, \n" +
                              " AII.NEW_OBJECT_CODE,\n" +
                              " AII.NEW_RESPONSIBILITY_DEPT, \n" +
                              " AII.NEW_EMPLOYEE_NUMBER, \n" +
                              " AII.NEW_MAINTAIN_DEPT, \n" +
                              " AII.NEW_MAINTAIN_USER,\n" +
                              " AII.NEW_MANUFACTURER_ID, \n" +
                              " AII.NEW_LNE_ID, \n" +
                              " AII.NEW_CEX_ID, \n" +
                              " AII.NEW_OPE_ID, \n" +
                              " AII.NEW_NLE_ID\n" +
                              " FROM AMS_ITEM_IMPORT AII\n" +
                       " WHERE AII.ERROR IS NULL " +
                         " AND AII.USER_ID = ?";
        sqlArgs.add(userAccount.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getQueryImportModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT ALAI.COMPANY_CODE,\n" +
                "        ALAI.BARCODE,\n" +
                "        ALAI.ITEM_NAME,\n" +
                "        ALAI.ITEM_SPEC,\n" +
                "        ALAI.EMPLOYEE_NUMBER,\n" +
                "        ALAI.EMPLOYEE_NAME,\n" +
                "        ALAI.WORKORDER_OBJECT_CODE,\n" +
                "        ALAI.ITEM_QTY,\n" +
                "        ALAI.MANUFACTURER_ID,\n" +
                "        ALAI.MANUFACTURER_NAME,\n" +
                "        ALAI.POWER,\n" +
                "        ALAI.EQUIPMENT_PERFORMANCE,\n" +
                "        ALAI.CONTENT_CODE,\n" +
                "        ALAI.CONTENT_NAME,\n" +
                "        ALAI.WORKORDER_OBJECT_NAME,\n" +
                "        ALAI.SPECIALITY_DEPT,\n" +
                "        ALAI.MAINTAIN_USER,\n" +
                "        ALAI.MAINTAIN_DEPT,\n" +
                "        ALAI.START_DATE,\n" +
                "        ALAI.END_DATE,\n" +
                "        ALAI.RENT_PERSON,\n" +
                "        ALAI.TENANCY,\n" +
                "        ALAI.YEAR_RENTAL,\n" +
                "        ALAI.MONTH_REANTAL,\n" +
                "        ALAI.CONTRACT_NUMBER,\n" +
                "        ALAI.CONTRACT_NAME,\n" +
                "        ALAI.REMARK\n" +
                " FROM   AMS_LASTING_ASSETS_IMPORT ALAI\n" +
                " WHERE  ALAI.ERROR IS NULL\n" +
                "    AND ALAI.USER_ID = ?";
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

    public SQLModel getDeptCodeModel(String employeeNumber) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT AME.DEPT_CODE FROM AMS_MIS_EMPLOYEE AME WHERE AME.EMPLOYEE_NUMBER = ?";
        sqlArgs.add(employeeNumber);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

        public SQLModel getDataCreateModel() {
            SQLModel sqlModel = new SQLModel();
            List sqlArgs = new ArrayList();
            LastingAssetsDTOJt eoDTO = (LastingAssetsDTOJt) dtoParameter;
            String sqlStr = "INSERT INTO ETS_ITEM_INFO \n" +
                    "  (SYSTEMID,\n" +
                    "   BARCODE,\n" +
                    "   ITEM_CODE,\n" +
                    "   RESPONSIBILITY_DEPT,\n" +
                    "   RESPONSIBILITY_USER,\n" +
                    "   ADDRESS_ID,\n" +
                    "   POWER,\n" +
                    "   CONTENT_CODE,\n" +
                    "   CONTENT_NAME,\n" +
                    "   SPECIALITY_DEPT,\n" +
                    "   MAINTAIN_USER,\n" +
                    "   MAINTAIN_DEPT,\n" +
                    "   CREATION_DATE,\n" +
                    "   FINANCE_PROP,\n" +
                    "   OTHER_INFO,\n" +
                    "   ORGANIZATION_ID,\n" +
                    "   ITEM_QTY,\n" +
                    "   MANUFACTURER_ID,\n" +
                    "   CREATED_BY)\n" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE() , 'RENT_ASSETS', ?, ?, ?, ?, ?)";
            sqlArgs.add(eoDTO.getSystemid());
            sqlArgs.add(eoDTO.getBarcode());
            sqlArgs.add(eoDTO.getItemCode());
            sqlArgs.add(eoDTO.getResponsibilityDept());
            sqlArgs.add(eoDTO.getEmployeeId());
            sqlArgs.add(eoDTO.getAddressId());
            sqlArgs.add(eoDTO.getPower());
            sqlArgs.add(eoDTO.getContentCode());
            sqlArgs.add(eoDTO.getContentName());
            sqlArgs.add(eoDTO.getSpecialityDept());
            sqlArgs.add(eoDTO.getMaintainUser());
            sqlArgs.add(eoDTO.getMaintainDept());
            sqlArgs.add(eoDTO.getEquipmentPerformance());
            sqlArgs.add(userAccount.getOrganizationId());
            sqlArgs.add(eoDTO.getItemQty());
            sqlArgs.add(eoDTO.getManufacturerId());
            sqlArgs.add(userAccount.getUserId());

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
            return sqlModel;
    }

        public SQLModel getRENTCreateModel() throws SQLModelException {
            SQLModel sqlModel = new SQLModel();
            List sqlArgs = new ArrayList();
            try {
                LastingAssetsDTOJt eoDTO = (LastingAssetsDTOJt) dtoParameter;
                String sqlStr = "INSERT INTO AMS_RENT_INFO\n" +
                        "  (RENT_ID,\n" +
                        "   BARCODE,\n" +
                        "   RENT_PERSON,\n" +
                        "   RENT_DATE,\n" +
                        "   END_DATE,\n" +
                        "   YEAR_RENTAL,\n" +
                        "   MONTH_REANTAL,\n" +
                        "   TENANCY,\n" +
                        "   CONTRACT_NUMBER,\n" +
                        "   CONTRACT_NAME,\n" +
                        "   CREATION_DATE,\n" +
                        "   CREATED_BY,\n" +
                        "   REMARK)\n" +
                        "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), ?, ?)";
                sqlArgs.add(eoDTO.getRentId());
                sqlArgs.add(eoDTO.getBarcode());
                sqlArgs.add(eoDTO.getRentPerson());
                sqlArgs.add(eoDTO.getStartDate());
                sqlArgs.add(eoDTO.getEndDate());
                sqlArgs.add(eoDTO.getYearRental());
                sqlArgs.add(eoDTO.getMonthReantal());
                sqlArgs.add(eoDTO.getTenancy());
                sqlArgs.add(eoDTO.getContractNumber());
                sqlArgs.add(eoDTO.getContractName());
                sqlArgs.add(userAccount.getUserId());
                sqlArgs.add(eoDTO.getRemark());
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

    public SQLModel validateManufacturer(String manufacturerName) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT 1 FROM AMS_MANUFACTURER AM WHERE AM.MANUFACTURER_NAME = ?";
        sqlArgs.add(manufacturerName);
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