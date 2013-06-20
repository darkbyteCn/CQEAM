package com.sino.ams.system.object.dao;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.onnet.dto.AmsItemOnNetDTO;
import com.sino.ams.system.object.dto.ImportVillageAssetsDTOJt;
import com.sino.ams.system.object.dto.LastingAssetsDTOJt;
import com.sino.ams.system.object.model.ImportLastingAssetsModelJt;
import com.sino.ams.system.object.model.ImportVillageAssetsModelJt;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.datatrans.*;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;
import com.sino.base.util.CalendarUtil;
import com.sino.base.util.ReflectionUtil;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImportVillageAssetsDAOJt extends AMSBaseDAO {

    private final static String[] fieldNames = {
            "companyCode", "barcode", "itemName", "itemSpec",
            "employeeNumber", "employeeName", "workorderObjectCode",
            "workorderObjectName", "financeProp", "itemQty", "actualQty",
            "equipmentPerformance", "contentCode", "contentName",
            "specialityDept", "maintainUser", "price", "villageStartDate",
            "tfDepreciation", "tfNetAssetValue", "tfDeprnCost", "projectid",
            "projectName", "constructStatus", "isShare", "manufacturerId", "remark"
    };

    public ImportVillageAssetsDAOJt(SfUserDTO userAccount, AmsItemOnNetDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        ImportVillageAssetsDTOJt dtoPara = (ImportVillageAssetsDTOJt) dtoParameter;
        super.sqlProducer = new ImportVillageAssetsModelJt((SfUserDTO) userAccount, dtoPara);
    }

    public void deleteImportModel() throws SQLModelException, QueryException, DataHandleException {
        ImportVillageAssetsModelJt eoModel = (ImportVillageAssetsModelJt) sqlProducer;
        SQLModel sqlModel = eoModel.deleteImportModel();
        DBOperator.updateRecord(sqlModel, conn);
    }

    /**
     * 功能：插入到接口表
     *
     * @param dtoSet DTOSet
     * @return boolean
     * @throws DataHandleException
     * @throws SQLModelException
     */
    public boolean itemImportData(DTOSet dtoSet) throws DataHandleException {
        boolean operateResult = false;
        boolean autoCommit = true;
        PreparedStatement pstmt = null;
        try {
            if (dtoSet != null && dtoSet.getSize() > 0) {
                autoCommit = conn.getAutoCommit();
                conn.setAutoCommit(false);
                ImportVillageAssetsModelJt modelProducer = (ImportVillageAssetsModelJt) sqlProducer;
                SQLModel sqlModel = modelProducer.insertImportModel();
                pstmt = conn.prepareCall(sqlModel.getSqlStr());
                int fieldCount = fieldNames.length;
                for (int i = 0; i < dtoSet.getSize(); i++) {
                    ImportVillageAssetsDTOJt eoDTO = (ImportVillageAssetsDTOJt) dtoSet.getDTO(i);
                    for (int j = 0; j < fieldCount; j++) {
                        String fieldName = fieldNames[j];
                        String fieldValue = StrUtil.nullToString(ReflectionUtil.getProperty(eoDTO, fieldName));
                        pstmt.setString(j + 1, fieldValue);
                    }
                    pstmt.setInt(fieldCount + 1, userAccount.getUserId());
                    pstmt.addBatch();
                    if (i % 100 == 0 && i > 0) {
                        pstmt.executeBatch();
                    }
                }
                pstmt.executeBatch();
            }
            operateResult = true;
        } catch (SQLException ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex.getMessage());
        } finally {
            try {
                DBManager.closeDBStatement(pstmt);
                if (operateResult) {
                    conn.commit();
                } else {
                    conn.rollback();
                }
                conn.setAutoCommit(autoCommit);
            } catch (SQLException ex) {
                Logger.logError(ex);
            }
        }
        return operateResult;
    }


    public String doVerifyData() throws DataHandleException {
        CallableStatement cs = null;
        String errorMsg = "";
        try {
            String callStr = "{CALL dbo.CTZC_CHECK_DATA(?, ?)}";
            cs = conn.prepareCall(callStr);
            cs.setInt(1, userAccount.getUserId());
            cs.registerOutParameter(2, Types.VARCHAR);
            cs.execute();
            errorMsg = cs.getString(2);
        } catch (SQLException ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex.getMessage());
        } finally {
            DBManager.closeDBStatement(cs);
        }
        return errorMsg;
    }


    /**
     * 功能：导入到表ets_item_info提交工单
     *
     * @throws DataHandleException
     */
    public String submitOrderDtl() throws DataHandleException {
        CallableStatement cs = null;
        String errorMsg = "";
        boolean operateResult = false;
        boolean autoCommit = true;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            String callStr = "{CALL dbo.CTZC_TRANS_DATA(?, ?)}";
            cs = conn.prepareCall(callStr);
            cs.setInt(1, userAccount.getUserId());
            cs.registerOutParameter(2, Types.VARCHAR);
            cs.execute();
            errorMsg = cs.getString(2);
            
            if( StrUtil.isEmpty( errorMsg )){
            	operateResult = true;
            }else{
            	operateResult = false;
            }
        } catch (SQLException ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex.getMessage());
        } finally {
            try {
                DBManager.closeDBStatement(cs);
                if (operateResult) {
                    conn.commit();
                } else {
                    conn.rollback();
                }
                conn.setAutoCommit(autoCommit);
            } catch (SQLException ex) {
                Logger.logError(ex);
            }
        }
        return errorMsg;
    }

    public RowSet getImportErrors() throws QueryException {
        ImportVillageAssetsModelJt modelProducer = (ImportVillageAssetsModelJt) sqlProducer;
        SQLModel sqlModel = modelProducer.getImportErrorModel();
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        return simpleQuery.getSearchResult();
    }


    public void doVerifyData(DTOSet dtoSet) throws SQLModelException, QueryException, ContainerException {
        if (dtoSet != null && dtoSet.getSize() > 0) {
            for (int i = 0; i < dtoSet.getSize(); i++) {
                ImportVillageAssetsDTOJt villageDTO = (ImportVillageAssetsDTOJt) dtoSet.getDTO(i);
                if (StrUtil.isEmpty(villageDTO.getCompanyCode())) {
                    insertImprotErrorData(villageDTO.getBarcode(), "公司代码不能为空");
                } else if (StrUtil.isEmpty(villageDTO.getBarcode())) {
                    insertImprotErrorData(villageDTO.getBarcode(), "资产标签号不能为空");
                } else if (StrUtil.isEmpty(villageDTO.getItemName())) {
                    insertImprotErrorData(villageDTO.getBarcode(), "资产名称不能为空");
                }
//                else if (StrUtil.isEmpty(villageDTO.getItemSpec())) {
//                    insertImprotErrorData(villageDTO.getBarcode(), "规格型号不能为空");
//                }
                else if (StrUtil.isEmpty(villageDTO.getEmployeeNumber())) {
                    insertImprotErrorData(villageDTO.getBarcode(), "责任人编号不能为空");
                } else if (StrUtil.isEmpty(villageDTO.getEmployeeName())) {
                    insertImprotErrorData(villageDTO.getBarcode(), "责任人姓名不能为空");
                } else if (StrUtil.isEmpty(villageDTO.getWorkorderObjectCode())) {
                    insertImprotErrorData(villageDTO.getBarcode(), "资产地点三段组合编码不能为空");
                } else if (StrUtil.isEmpty(villageDTO.getFinanceProp())) {
                    insertImprotErrorData(villageDTO.getBarcode(), "通服资产类型不能为空");
                } else if (StrUtil.isEmpty(villageDTO.getContentCode())) {
                    insertImprotErrorData(villageDTO.getBarcode(), "资产类别代码组合不能为空");
                }
//                else if (StrUtil.isEmpty(villageDTO.getConstructStatus())) {
//                    insertImprotErrorData(villageDTO.getBarcode(), "是否共建设备不能为空");
//                } else if (StrUtil.isEmpty(villageDTO.getShare())) {
//                    insertImprotErrorData(villageDTO.getBarcode(), "是否共享设备不能为空");
//                }
//                else if (StrUtil.isEmpty(villageDTO.getManufacturerId())) {
//                    insertImprotErrorData(villageDTO.getBarcode(), "厂商代码不能为空");
//                }
                else if (!validateOU(villageDTO.getCompanyCode())) {
                    insertImprotErrorData(villageDTO.getBarcode(), "公司代码不正确");
                } else if (!validateBarcodeOu(villageDTO.getBarcode(), villageDTO.getCompanyCode())) {
                    insertImprotErrorData(villageDTO.getBarcode(), "该条码对应公司不正确");
                } else if (!validateSameBarcode(villageDTO.getBarcode())) {
                    insertImprotErrorData(villageDTO.getBarcode(), "EXCEL中条码重复");
                } else if (!validateSameEiiBarcode(villageDTO.getBarcode())) {
                    insertImprotErrorData(villageDTO.getBarcode(), "条码在实物系统中已存在");
                } else if (!validateItemNS(villageDTO.getItemName(), villageDTO.getItemSpec()) && (!StrUtil.isEmpty(villageDTO.getItemName()) || !StrUtil.isEmpty(villageDTO.getItemSpec()))) {
                    insertImprotErrorData(villageDTO.getBarcode(), "名称和型号不存在");
                } else if (!validateNewEmployeeNum(villageDTO.getEmployeeNumber()) && !StrUtil.isEmpty(villageDTO.getEmployeeNumber())) {
                    insertImprotErrorData(villageDTO.getBarcode(), "责任人编号不存在");
                } else if (!validateObjectCode(villageDTO.getWorkorderObjectCode()) && !StrUtil.isEmpty(villageDTO.getWorkorderObjectCode())) {
                    insertImprotErrorData(villageDTO.getBarcode(), "资产地点编码不存在");
                } else if (!validateContentCode(villageDTO.getContentCode()) && !StrUtil.isEmpty(villageDTO.getContentCode())) {
                    insertImprotErrorData(villageDTO.getBarcode(), "资产类别代码组合不存在");
                } else if (!validateNewResDept(villageDTO.getSpecialityDept()) && !StrUtil.isEmpty(villageDTO.getSpecialityDept())) {
                    insertImprotErrorData(villageDTO.getBarcode(), "实物部门代码不存在");
                } else if (!validateBarcodeLength(villageDTO.getBarcode())) {
                    insertImprotErrorData(villageDTO.getBarcode(), "条码必须为13位");
                } else if (!validateBarcode(villageDTO.getBarcode())) {
                    insertImprotErrorData(villageDTO.getBarcode(), "资产标签号存在小写字母");
                } else if (!validateBarcode2(villageDTO.getBarcode())) {
                    insertImprotErrorData(villageDTO.getBarcode(), "资产标签号中不存在“TF”");
                } else if (!validateFinanceProp(villageDTO.getFinanceProp())) {
                    insertImprotErrorData(villageDTO.getBarcode(), "通服资产类型必须为“CT”或“QD”");
                } else if (!validateTenancy(villageDTO.getItemQty()) && !StrUtil.isEmpty(villageDTO.getItemQty())) {
                    insertImprotErrorData(villageDTO.getBarcode(), "数量必须是数字类型");
                } else if (!validateTenancy(villageDTO.getActualQty()) && !StrUtil.isEmpty(villageDTO.getActualQty())) {
                    insertImprotErrorData(villageDTO.getBarcode(), "传输线路资源量必须是数字类型");
                } else if (!validateProject(villageDTO.getProjectid()) && !StrUtil.isEmpty(villageDTO.getProjectid())) {
                    insertImprotErrorData(villageDTO.getBarcode(), "项目编号不存在");
                } else if (!validateManufacturer(villageDTO.getManufacturerId()) && !StrUtil.isEmpty(villageDTO.getManufacturerId())) {
                    insertImprotErrorData(villageDTO.getBarcode(), "厂商不存在");
                } else if (!validateContractStatus(villageDTO.getConstructStatus()) && !StrUtil.isEmpty(villageDTO.getConstructStatus())) {
                    insertImprotErrorData(villageDTO.getBarcode(), "是否共建设备不存在");
                } else if (!validateShareStatus(villageDTO.getShare()) && !StrUtil.isEmpty(villageDTO.getShare())) {
                    insertImprotErrorData(villageDTO.getBarcode(), "是否共享设备不存在");
                } else if (!validateStartDate(villageDTO.getVillageStartDate()) && !StrUtil.isEmpty(villageDTO.getVillageStartDate())) {
                    insertImprotErrorData(villageDTO.getBarcode(), "启用日期格式不正确");
                } else if (!validateTenancy(villageDTO.getPrice()) && !StrUtil.isEmpty(villageDTO.getPrice())) {
                    insertImprotErrorData(villageDTO.getBarcode(), "通服资产原值必须是数字类型");
                } else if (!validateTenancy(villageDTO.getTfNetAssetValue()) && !StrUtil.isEmpty(villageDTO.getTfNetAssetValue())) {
                    insertImprotErrorData(villageDTO.getBarcode(), "通服资产净值必须是数字类型");
                } else if (!validateTenancy(villageDTO.getTfDeprnCost()) && !StrUtil.isEmpty(villageDTO.getTfDeprnCost())) {
                    insertImprotErrorData(villageDTO.getBarcode(), "通服资产净额必须是数字类型");
                }
            }
        }
    }

    public boolean validateShareStatus(String NewShareStatus) throws SQLModelException, QueryException {
        boolean hasBarcode = false;
        ImportVillageAssetsModelJt eoModel = (ImportVillageAssetsModelJt) sqlProducer;
        SQLModel sqlModel = eoModel.validateNewShareStatus(NewShareStatus);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        if (simpleQuery.hasResult()) {
            hasBarcode = true;
        }
        return hasBarcode;
    }

    public boolean validateContractStatus(String NewContractStatus) throws SQLModelException, QueryException {
        boolean hasBarcode = false;
        ImportVillageAssetsModelJt eoModel = (ImportVillageAssetsModelJt) sqlProducer;
        SQLModel sqlModel = eoModel.validateNewContractStatus(NewContractStatus);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        if (simpleQuery.hasResult()) {
            hasBarcode = true;
        }
        return hasBarcode;
    }

    public boolean validateManufacturer(String manufacturerId) throws SQLModelException, QueryException {
        boolean hasBarcode = false;
        ImportVillageAssetsModelJt eoModel = (ImportVillageAssetsModelJt) sqlProducer;
        SQLModel sqlModel = eoModel.validateManufacturer(manufacturerId);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        if (simpleQuery.hasResult()) {
            hasBarcode = true;
        }
        return hasBarcode;
    }

    public boolean validateProject(String projectid) throws SQLModelException, QueryException {
        boolean hasBarcode = false;
        ImportVillageAssetsModelJt eoModel = (ImportVillageAssetsModelJt) sqlProducer;
        SQLModel sqlModel = eoModel.validateProject(projectid);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        if (simpleQuery.hasResult()) {
            hasBarcode = true;
        }
        return hasBarcode;
    }

    public boolean validateFinanceProp(String financeProp) {
        boolean isTrue = true;
        if (!financeProp.equals("CT") && !financeProp.equals("QD")) {
            isTrue = false;
        }
        return isTrue;
    }

    public boolean validateBarcode2(String barCode) {
        boolean isTrue = true;
        int count = barCode.indexOf("TF");
        if (count < 0) {
            isTrue = false;
        }
        return isTrue;
    }

    public boolean validateBarcode(String barCode) {
        boolean isUpper = barCode.toUpperCase().equals(barCode);
        return isUpper;
    }

    public boolean validateBarcodeLength(String barCode) {
        boolean isTrue = true;
        int length = barCode.length();
        if (length != 13) {
            isTrue = false;
        }
        return isTrue;
    }

    public boolean validateOU(String companyCode) throws SQLModelException, QueryException {
        boolean hasBarcode = false;
        ImportVillageAssetsModelJt eoModel = (ImportVillageAssetsModelJt) sqlProducer;
        SQLModel sqlModel = eoModel.validateOU(companyCode);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        if (simpleQuery.hasResult()) {
            hasBarcode = true;
        }
        return hasBarcode;
    }

    public boolean validateTenancy(String tenancy) throws SQLModelException, QueryException {
        boolean isTrue = true;
        if (!StrUtil.isNumber(tenancy)) {
            isTrue = false;
        }
        return isTrue;
    }

    public boolean validateStartDate(String date) throws SQLModelException, QueryException {
        boolean isTrue = true;
        if (!CalendarUtil.isValidDate(date)) {
            isTrue = false;
        }
        return isTrue;
    }

    public boolean validateEmployee(String deptCode, String employeeNumber) throws SQLModelException, QueryException {
        boolean hasBarcode = false;
        ImportVillageAssetsModelJt eoModel = (ImportVillageAssetsModelJt) sqlProducer;
        SQLModel sqlModel = eoModel.validateEmployee(deptCode, employeeNumber);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        if (simpleQuery.hasResult()) {
            hasBarcode = true;
        }
        return hasBarcode;
    }

    public boolean validateBarcodeOu(String barcode, String companyCode) throws SQLModelException, QueryException {
        boolean hasBarcode = false;
        String subBarcode = barcode.substring(0, 4);
        if (subBarcode.equals(companyCode)) {
            hasBarcode = true;
        }
        return hasBarcode;
    }

    public boolean validateItemNS(String itemName, String itemSpec) throws SQLModelException, QueryException {
        boolean hasBarcode = false;
        ImportVillageAssetsModelJt eoModel = (ImportVillageAssetsModelJt) sqlProducer;
        SQLModel sqlModel = eoModel.validateItemNS(itemName, itemSpec);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        if (simpleQuery.hasResult()) {
            hasBarcode = true;
        }
        return hasBarcode;
    }

    public boolean validateNewEmployeeNum(String newEmployeeNum) throws SQLModelException, QueryException {
        boolean hasBarcode = false;
        ImportVillageAssetsModelJt eoModel = (ImportVillageAssetsModelJt) sqlProducer;
        SQLModel sqlModel = eoModel.validateNewEmployeeNum(newEmployeeNum);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        if (simpleQuery.hasResult()) {
            hasBarcode = true;
        }
        return hasBarcode;
    }

    public boolean validateObjectCode(String newObjectCode) throws SQLModelException, QueryException {
        boolean hasBarcode = false;
        ImportVillageAssetsModelJt eoModel = (ImportVillageAssetsModelJt) sqlProducer;
        SQLModel sqlModel = eoModel.validateObjectCode(newObjectCode);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        if (simpleQuery.hasResult()) {
            hasBarcode = true;
        }
        return hasBarcode;
    }

    public boolean validateContentCode(String contentCode) throws SQLModelException, QueryException {
        boolean hasBarcode = false;
        ImportVillageAssetsModelJt eoModel = (ImportVillageAssetsModelJt) sqlProducer;
        SQLModel sqlModel = eoModel.validateContentCode(contentCode);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        if (simpleQuery.hasResult()) {
            hasBarcode = true;
        }
        return hasBarcode;
    }

    public boolean validateNewResDept(String newResDept) throws SQLModelException, QueryException {
        boolean hasBarcode = false;
        ImportVillageAssetsModelJt eoModel = (ImportVillageAssetsModelJt) sqlProducer;
        SQLModel sqlModel = eoModel.validateNewResDept(newResDept);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        if (simpleQuery.hasResult()) {
            hasBarcode = true;
        }
        return hasBarcode;
    }

    public void insertImprotErrorData(String barcode, String codeError) throws SQLModelException {
        try {
            ImportVillageAssetsModelJt onNetModel = (ImportVillageAssetsModelJt) sqlProducer;
            SQLModel sqlModel = onNetModel.insertImprotErrorData(barcode, codeError);
            DBOperator.updateRecord(sqlModel, conn);
        } catch (DataHandleException ex) {
            ex.printLog();
        }
    }

    public boolean validateSameEiiBarcode(String barcode) throws SQLModelException, QueryException, ContainerException {
        boolean hasBarcode = true;
        ImportVillageAssetsModelJt eoModel = (ImportVillageAssetsModelJt) sqlProducer;
        SQLModel sqlModel = eoModel.validateSameEiiBarcode(barcode);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        if (simpleQuery.hasResult()) {
            hasBarcode = false;
        }
        return hasBarcode;
    }

    public boolean validateSameBarcode(String barcode) throws SQLModelException, QueryException, ContainerException {
        boolean hasBarcode = true;
        ImportVillageAssetsModelJt eoModel = (ImportVillageAssetsModelJt) sqlProducer;
        SQLModel sqlModel = eoModel.validateSameBarcode(barcode);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        RowSet rs = simpleQuery.getSearchResult();
        if (rs != null && rs.getSize() > 0) {
            Row row = rs.getRow(0);
            int count = Integer.parseInt(StrUtil.nullToString(row.getValue("BAR_QTY")));
            if (count > 1) {
                hasBarcode = false;
            }
        }
        return hasBarcode;
    }

    public boolean importHasError() throws SQLModelException, QueryException {
        boolean hasError = false;
        ImportVillageAssetsModelJt eoModel = (ImportVillageAssetsModelJt) sqlProducer;
        SQLModel sqlModel = eoModel.hasErrorModel();
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        if (simpleQuery.hasResult()) {
            hasError = true;
        }
        return hasError;
    }

    public DTOSet getImport() throws QueryException, SQLModelException {
        ImportVillageAssetsModelJt eoModel = (ImportVillageAssetsModelJt) sqlProducer;
        SimpleQuery sq = new SimpleQuery(eoModel.getQueryImportModel(), conn);
        sq.setDTOClassName(ImportVillageAssetsDTOJt.class.getName());
        sq.executeQuery();
        return sq.getDTOSet();
    }

    public String getItemCode(String itemName, String itemSpce) throws SQLModelException, QueryException, ContainerException {
        String itemCode = "";
        ImportVillageAssetsModelJt eoModel = (ImportVillageAssetsModelJt) sqlProducer;
        SQLModel sqlModel = eoModel.getItemCodeModel(itemName, itemSpce);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        RowSet rs = simpleQuery.getSearchResult();
        if (rs != null && rs.getSize() > 0) {
            Row row = rs.getRow(0);
            itemCode = StrUtil.nullToString(row.getValue("ITEM_CODE"));
        }
        return itemCode;
    }

    public String getAddressId(String objectCode) throws SQLModelException, QueryException, ContainerException {
        String addressId = "";
        ImportVillageAssetsModelJt eoModel = (ImportVillageAssetsModelJt) sqlProducer;
        SQLModel sqlModel = eoModel.getAddressIdModel(objectCode);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        RowSet rs = simpleQuery.getSearchResult();
        if (rs != null && rs.getSize() > 0) {
            Row row = rs.getRow(0);
            addressId = StrUtil.nullToString(row.getValue("ADDRESS_ID"));
        }
        return addressId;
    }

    public String getEmployeeId(String employeeNumber) throws SQLModelException, QueryException, ContainerException {
        String employeeId = "";
        ImportVillageAssetsModelJt eoModel = (ImportVillageAssetsModelJt) sqlProducer;
        SQLModel sqlModel = eoModel.getEmployeeIdModel(employeeNumber);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        RowSet rs = simpleQuery.getSearchResult();
        if (rs != null && rs.getSize() > 0) {
            Row row = rs.getRow(0);
            employeeId = StrUtil.nullToString(row.getValue("EMPLOYEE_ID"));
        }
        return employeeId;
    }

    public String getDeptCode(String employeeNumber) throws SQLModelException, QueryException, ContainerException {
        String employeeId = "";
        ImportVillageAssetsModelJt eoModel = (ImportVillageAssetsModelJt) sqlProducer;
        SQLModel sqlModel = eoModel.getDeptCodeModel(employeeNumber);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        RowSet rs = simpleQuery.getSearchResult();
        if (rs != null && rs.getSize() > 0) {
            Row row = rs.getRow(0);
            employeeId = StrUtil.nullToString(row.getValue("DEPT_CODE"));
        }
        return employeeId;
    }

    public String getManufacturerId(String manufacturerName) throws SQLModelException, QueryException, ContainerException {
        String manufacturerId = "";
        ImportVillageAssetsModelJt eoModel = (ImportVillageAssetsModelJt) sqlProducer;
        SQLModel sqlModel = eoModel.getManufacturerIdModel(manufacturerName);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        RowSet rs = simpleQuery.getSearchResult();
        if (rs != null && rs.getSize() > 0) {
            Row row = rs.getRow(0);
            manufacturerId = StrUtil.nullToString(row.getValue("MANUFACTURER_ID"));
        }
        return manufacturerId;
    }

    public String getConstructStatusId(String constructStatusName) throws SQLModelException, QueryException, ContainerException {
        String constructStatusId = "";
        ImportVillageAssetsModelJt eoModel = (ImportVillageAssetsModelJt) sqlProducer;
        SQLModel sqlModel = eoModel.getConstructStatusIdModel(constructStatusName);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        RowSet rs = simpleQuery.getSearchResult();
        if (rs != null && rs.getSize() > 0) {
            Row row = rs.getRow(0);
            constructStatusId = StrUtil.nullToString(row.getValue("CODE"));
        }
        return constructStatusId;
    }

    public String getShareId(String shareName) throws SQLModelException, QueryException, ContainerException {
        String shareId = "";
        ImportVillageAssetsModelJt eoModel = (ImportVillageAssetsModelJt) sqlProducer;
        SQLModel sqlModel = eoModel.getShareIdModel(shareName);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        RowSet rs = simpleQuery.getSearchResult();
        if (rs != null && rs.getSize() > 0) {
            Row row = rs.getRow(0);
            shareId = StrUtil.nullToString(row.getValue("CODE"));
        }
        return shareId;
    }

    public File getExportFile() throws DataTransException, SQLModelException {
        ImportVillageAssetsModelJt modelProducer = (ImportVillageAssetsModelJt) sqlProducer;
        SQLModel sqlModel = modelProducer.getImportErrorModel();
        String reportTitle = "村通资产导入错误信息";
        String fileName = reportTitle + ".xls";
        TransRule rule = new TransRule();
        rule.setDataSource(sqlModel);
        rule.setSourceConn(conn);
        String filePath = WorldConstant.USER_HOME;
        filePath += WorldConstant.FILE_SEPARATOR;
        filePath += fileName;
        rule.setTarFile(filePath);
        DataRange range = new DataRange();
        rule.setDataRange(range);
        rule.setFieldMap(getFieldMap());
        CustomTransData custData = new CustomTransData();
        custData.setReportTitle(reportTitle);
        custData.setReportPerson(userAccount.getUsername());
        custData.setNeedReportDate(true);
        rule.setCustData(custData);
        rule.setCalPattern(LINE_PATTERN);
        TransferFactory factory = new TransferFactory();
        DataTransfer transfer = factory.getTransfer(rule);
        transfer.transData();
        return (File) transfer.getTransResult();
    }

    private Map getFieldMap() {
        Map fieldMap = new HashMap();
        fieldMap.put("COMPANY_CODE", "公司代码");
        fieldMap.put("BARCODE", "资产标签号");
        fieldMap.put("ITEM_NAME", "资产名称");
        fieldMap.put("ITEM_SPEC", "规格型号");
        fieldMap.put("EMPLOYEE_NUMBER", "责任人编号");
        fieldMap.put("EMPLOYEE_NAME", "责任人姓名");
        fieldMap.put("WORKORDER_OBJECT_CODE", "地点组合编码");
        fieldMap.put("WORKORDER_OBJECT_NAME", "资产地点描述");
        fieldMap.put("FINANCE_PROP", "通服资产类型");
        fieldMap.put("ITEM_QTY", "数量");
        fieldMap.put("ACTUAL_QTY", "传输线路资源量");
        fieldMap.put("EQUIPMENT_PERFORMANCE", "设备性能");
        fieldMap.put("CONTENT_CODE", "资产类别代码组合");
        fieldMap.put("CONTENT_NAME", "资产类别描述");
        fieldMap.put("SPECIALITY_DEPT", "实物部门代码");
        fieldMap.put("MAINTAIN_USER", "使用人姓名");
        fieldMap.put("PRICE", "通服资产原值");
        fieldMap.put("VILLAGE_START_DATE", "资产启用日期");
        fieldMap.put("TF_DEPRECIATION", "折旧年限");
        fieldMap.put("TF_NET_ASSET_VALUE", "通服资产净值");
        fieldMap.put("TF_DEPRN_COST", "通服资产净额");
        fieldMap.put("PROJECTID", "项目编号");
        fieldMap.put("PROJECT_NAME", "项目名称");
        fieldMap.put("CONSTRUCT_STATUS", "是否共建设备");
        fieldMap.put("IS_SHARE", "是否共享设备");
        fieldMap.put("MANUFACTURER_ID", "厂商");
        fieldMap.put("REMARK", "备注");
        fieldMap.put("ERROR", "错误信息");
        return fieldMap;
    }
}
