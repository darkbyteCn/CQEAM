package com.sino.ams.system.object.dao;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.system.object.dto.ImportSpareAssetsDTOJt;
import com.sino.ams.system.object.dto.ImportVillageAssetsDTOJt;
import com.sino.ams.system.object.model.ImportSpareAssetsModelJt;
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
import com.sino.base.db.util.SeqProducer;
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
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-6-10
 * Time: 20:37:17
 * To change this template use File | Settings | File Templates.
 */
public class ImportSpareAssetsDAOJt extends AMSBaseDAO {
    private final static String[] fieldNames = {
            "companyCode", "barcode", "itemName", "itemSpec",
            "itemUnit", "workorderObjectCode", "workorderObjectName",
            "responsibilityUser", "employeeName", "contentCode",
            "manufacturerId", "itemStatus", "specialityDept",
            "specialityUser", "maintainUser", "spareStartDate", "remark"
    };


    public ImportSpareAssetsDAOJt(SfUserDTO userAccount, ImportSpareAssetsDTOJt dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        ImportSpareAssetsDTOJt dtoPara = (ImportSpareAssetsDTOJt) dtoParameter;
        super.sqlProducer = new ImportSpareAssetsModelJt((SfUserDTO) userAccount, dtoPara);
    }

    public void deleteImportModel() throws SQLModelException, QueryException, DataHandleException {
        ImportSpareAssetsModelJt eoModel = (ImportSpareAssetsModelJt) sqlProducer;
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
                ImportSpareAssetsModelJt modelProducer = (ImportSpareAssetsModelJt) sqlProducer;
                SQLModel sqlModel = modelProducer.insertImportModel();
                pstmt = conn.prepareCall(sqlModel.getSqlStr());
                int fieldCount = fieldNames.length;
                for (int i = 0; i < dtoSet.getSize(); i++) {
                    ImportSpareAssetsDTOJt eoDTO = (ImportSpareAssetsDTOJt) dtoSet.getDTO(i);
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
            String callStr = "{CALL dbo.ASIP_CHECK_DATA(?, ?)}";
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
            //conn.setAutoCommit(false);
            String callStr = "{CALL dbo.ASIP_TRANS_DATA(?, ?)}";
            cs = conn.prepareCall(callStr);
            cs.setInt(1, userAccount.getUserId());
            cs.registerOutParameter(2, Types.VARCHAR);
            cs.execute();
            errorMsg = cs.getString(2);
            operateResult = true;
        } catch (SQLException ex) {
            Logger.logError(ex);
            errorMsg = "导入失败: SQLException代码(" + ex.getErrorCode() + ")" ;
//            errorMsg = "导入失败：" + ex.getMessage().replaceAll( "'" , " ").replaceAll( ";" , ".");
//            throw new DataHandleException(ex);
        } catch (Throwable ex) {
            Logger.logError(ex);
            errorMsg = "导入失败: " + ex.getMessage()  ;
//            throw new DataHandleException(ex.getMessage());
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
        ImportSpareAssetsModelJt modelProducer = (ImportSpareAssetsModelJt) sqlProducer;
        SQLModel sqlModel = modelProducer.getImportErrorModel();
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        return simpleQuery.getSearchResult();
    }


    public void doVerifyData(DTOSet dtoSet) throws SQLModelException, QueryException, ContainerException {
        if (dtoSet != null && dtoSet.getSize() > 0) {
            for (int i = 0; i < dtoSet.getSize(); i++) {
                ImportSpareAssetsDTOJt dzyhDTO = (ImportSpareAssetsDTOJt) dtoSet.getDTO(i);
                if (StrUtil.isEmpty(dzyhDTO.getCompanyCode())) {
                    insertImprotErrorData(dzyhDTO.getBarcode(), "公司代码不能为空");
                } else if (StrUtil.isEmpty(dzyhDTO.getBarcode())) {
                    insertImprotErrorData(dzyhDTO.getBarcode(), "备品备件标签号不能为空");
                } else if (StrUtil.isEmpty(dzyhDTO.getItemName())) {
                    insertImprotErrorData(dzyhDTO.getBarcode(), "备件名称不能为空");
                }
//                else if (StrUtil.isEmpty(dzyhDTO.getItemSpec())) {
//                    insertImprotErrorData(dzyhDTO.getBarcode(), "规格型号不能为空");
//                }
                else if (StrUtil.isEmpty(dzyhDTO.getItemUnit())) {
                    insertImprotErrorData(dzyhDTO.getBarcode(), "计量单位不能为空");
                } else if (StrUtil.isEmpty(dzyhDTO.getWorkorderObjectCode())) {
                    insertImprotErrorData(dzyhDTO.getBarcode(), "资产地点三段组合编码");
                } else if (StrUtil.isEmpty(dzyhDTO.getResponsibilityUser())) {
                    insertImprotErrorData(dzyhDTO.getBarcode(), "资产管理员员工编号不能为空");
                } else if (StrUtil.isEmpty(dzyhDTO.getEmployeeName())) {
                    insertImprotErrorData(dzyhDTO.getBarcode(), "责任人姓名不能为空");
                } else if (StrUtil.isEmpty(dzyhDTO.getContentCode())) {
                    insertImprotErrorData(dzyhDTO.getBarcode(), "资产目录类别组合代码不能为空");
                }
//                else if (StrUtil.isEmpty(dzyhDTO.getManufacturerId())) {
//                    insertImprotErrorData(dzyhDTO.getBarcode(), "厂商不能为空");
//                }
                else if (StrUtil.isEmpty(dzyhDTO.getItemStatus())) {
                    insertImprotErrorData(dzyhDTO.getBarcode(), "设备状态代码不能为空");
                } else if (!validateOU(dzyhDTO.getCompanyCode())) {
                    insertImprotErrorData(dzyhDTO.getBarcode(), "该用户对应的公司代码不正确");
                } else if (!validateBarcodeOu(dzyhDTO.getBarcode(), dzyhDTO.getCompanyCode())) {
                    insertImprotErrorData(dzyhDTO.getBarcode(), "该条码对应公司不正确");
                } else if (!validateSameBarcode(dzyhDTO.getBarcode())) {
                    insertImprotErrorData(dzyhDTO.getBarcode(), "EXCEL中条码重复");
                } else if (!validateSameEiiBarcode(dzyhDTO.getBarcode())) {
                    insertImprotErrorData(dzyhDTO.getBarcode(), "条码在实物系统中已存在");
                } else if (!validateNewResDept(dzyhDTO.getSpecialityDept()) && !StrUtil.isEmpty(dzyhDTO.getSpecialityDept())) {
                    insertImprotErrorData(dzyhDTO.getBarcode(), "实物部门代码不存在");
                } else if (!validateNewEmployeeNum(dzyhDTO.getSpecialityUser()) && !StrUtil.isEmpty(dzyhDTO.getSpecialityUser())) {
                    insertImprotErrorData(dzyhDTO.getBarcode(), "专业责任人员工编号不存在");
                } else if (!validateNewEmployeeNum(dzyhDTO.getResponsibilityUser()) && !StrUtil.isEmpty(dzyhDTO.getResponsibilityUser())) {
                    insertImprotErrorData(dzyhDTO.getBarcode(), "资产管理员员工编号不存在");
                } else if (!validateEmployee(dzyhDTO.getSpecialityDept(), dzyhDTO.getSpecialityUser()) && (!dzyhDTO.getSpecialityDept().equals("") || !dzyhDTO.getSpecialityDept().equals(""))) {
                    insertImprotErrorData(dzyhDTO.getBarcode(), "实物部门和专业责任人编号不一致");
                } else if (!validateStartDate(dzyhDTO.getSpareStartDate()) && !StrUtil.isEmpty(dzyhDTO.getSpareStartDate())) {
                    insertImprotErrorData(dzyhDTO.getBarcode(), "启用日期格式不正确");
                } else if (!validateBarcode(dzyhDTO.getBarcode())) {
                    insertImprotErrorData(dzyhDTO.getBarcode(), "资产标签号存在小写字母");
                } else if (!validateBarcodeLength(dzyhDTO.getBarcode())) {
                    insertImprotErrorData(dzyhDTO.getBarcode(), "条码必须为13位");
                } else if (!validateBarcode2(dzyhDTO.getBarcode())) {
                    insertImprotErrorData(dzyhDTO.getBarcode(), "备品备件标签号中不存在“BJ”");
                } else if (!validateObjectCode(dzyhDTO.getWorkorderObjectCode())) {
                    insertImprotErrorData(dzyhDTO.getBarcode(), "资产地点三段组合编码不存在");
                } else if (!validateContentCode(dzyhDTO.getContentCode()) && !StrUtil.isEmpty(dzyhDTO.getContentCode())) {
                    insertImprotErrorData(dzyhDTO.getBarcode(), "资产目录类别组合代码不存在");
                } else if (!validateManufacturer(dzyhDTO.getManufacturerId()) && !StrUtil.isEmpty(dzyhDTO.getManufacturerId())) {
                    insertImprotErrorData(dzyhDTO.getBarcode(), "厂商不存在");
                } else if (!validateItemStatus(dzyhDTO.getItemStatus())) {
                    insertImprotErrorData(dzyhDTO.getBarcode(), "设备状态必须为“NORMAL”或“FREE”");
                }
            }
        }
    }

    public boolean validateItemStatus(String itemStatus) {
        boolean isTrue = true;
        if (!itemStatus.equals("NORMAL") && !itemStatus.equals("FREE")) {
            isTrue = false;
        }
        return isTrue;
    }

    public boolean validateManufacturer(String manufacturerId) throws SQLModelException, QueryException {
        boolean hasBarcode = false;
        ImportSpareAssetsModelJt eoModel = (ImportSpareAssetsModelJt) sqlProducer;
        SQLModel sqlModel = eoModel.validateManufacturer(manufacturerId);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        if (simpleQuery.hasResult()) {
            hasBarcode = true;
        }
        return hasBarcode;
    }

    public boolean validateContentCode(String contentCode) throws SQLModelException, QueryException {
        boolean hasBarcode = false;
        ImportSpareAssetsModelJt eoModel = (ImportSpareAssetsModelJt) sqlProducer;
        SQLModel sqlModel = eoModel.validateContentCode(contentCode);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        if (simpleQuery.hasResult()) {
            hasBarcode = true;
        }
        return hasBarcode;
    }

    public boolean validateObjectCode(String newObjectCode) throws SQLModelException, QueryException {
        boolean hasBarcode = false;
        ImportSpareAssetsModelJt eoModel = (ImportSpareAssetsModelJt) sqlProducer;
        SQLModel sqlModel = eoModel.validateObjectCode(newObjectCode);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        if (simpleQuery.hasResult()) {
            hasBarcode = true;
        }
        return hasBarcode;
    }

    public boolean validateBarcode2(String barCode) {
        boolean isTrue = true;
        int count = barCode.indexOf("BJ");
        if (count < 0) {
            isTrue = false;
        }
        return isTrue;
    }

    private String getNextSysDistributeCode(Connection conn) throws SQLException {
        SeqProducer seqProducer = new SeqProducer(conn);
        return String.valueOf(seqProducer.getStrNextSeq("ETS_SYSITEM_DISTRIBUTE_S"));
    }

    public boolean validateBarcodeLength(String barCode) {
        boolean isTrue = true;
        int length = barCode.length();
        if (length != 13) {
            isTrue = false;
        }
        return isTrue;
    }

    public boolean validateBarcode(String barCode) {
        boolean isUpper = barCode.toUpperCase().equals(barCode);
        return isUpper;
    }

    public void insertDistribute(String itemCode, Connection conn) throws DataHandleException, SQLException, SQLModelException, QueryException, ContainerException {
        try {
            ImportSpareAssetsModelJt eoModel = (ImportSpareAssetsModelJt) sqlProducer;
            SQLModel sqlModel = eoModel.findOu();
            SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
            simpleQuery.executeQuery();
            RowSet rs = simpleQuery.getSearchResult();
            for (int i = 0; i < rs.getSize(); i++) {
                String distributeCode = getNextSysDistributeCode(conn);
                String orgId = StrUtil.nullToString(rs.getRow(i).getValue("ORGANIZATION_ID"));
                DBOperator.updateRecord(eoModel.insertDistribute(distributeCode, itemCode, orgId), conn);
            }
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                Logger.logError(e1);
            }
            e.printStackTrace();
            throw e;
        } catch (DataHandleException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                Logger.logError(e1);
            }
            Logger.logError(e);
            throw e;
        }
    }

    public void insertSystemItem(String newItemCode, String itemName, String itemSpce, String itemUnit) throws DataHandleException, SQLException {
        try {
            ImportSpareAssetsModelJt eoModel = (ImportSpareAssetsModelJt) sqlProducer;
            SQLModel sqlModel = eoModel.insertSystemItem(newItemCode, itemName, itemSpce, itemUnit);
            DBOperator.updateRecord(sqlModel, conn);
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                Logger.logError(e1);
            }
            e.printStackTrace();
            throw e;
        } catch (DataHandleException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                Logger.logError(e1);
            }
            Logger.logError(e);
            throw e;
        }
    }

    public boolean validateOU(String companyCode) throws SQLModelException, QueryException {
        boolean hasBarcode = false;
        ImportSpareAssetsModelJt eoModel = (ImportSpareAssetsModelJt) sqlProducer;
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
        ImportSpareAssetsModelJt eoModel = (ImportSpareAssetsModelJt) sqlProducer;
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

    public boolean validateNewEmployeeNum(String newEmployeeNum) throws SQLModelException, QueryException {
        boolean hasBarcode = false;
        ImportSpareAssetsModelJt eoModel = (ImportSpareAssetsModelJt) sqlProducer;
        SQLModel sqlModel = eoModel.validateNewEmployeeNum(newEmployeeNum);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        if (simpleQuery.hasResult()) {
            hasBarcode = true;
        }
        return hasBarcode;
    }

    public boolean validateNewResDept(String newResDept) throws SQLModelException, QueryException {
        boolean hasBarcode = false;
        ImportSpareAssetsModelJt eoModel = (ImportSpareAssetsModelJt) sqlProducer;
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
            ImportSpareAssetsModelJt onNetModel = (ImportSpareAssetsModelJt) sqlProducer;
            SQLModel sqlModel = onNetModel.insertImprotErrorData(barcode, codeError);
            DBOperator.updateRecord(sqlModel, conn);
        } catch (DataHandleException ex) {
            ex.printLog();
        }
    }

    public boolean validateSameEiiBarcode(String barcode) throws SQLModelException, QueryException, ContainerException {
        boolean hasBarcode = true;
        ImportSpareAssetsModelJt eoModel = (ImportSpareAssetsModelJt) sqlProducer;
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
        ImportSpareAssetsModelJt eoModel = (ImportSpareAssetsModelJt) sqlProducer;
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
        ImportSpareAssetsModelJt eoModel = (ImportSpareAssetsModelJt) sqlProducer;
        SQLModel sqlModel = eoModel.hasErrorModel();
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        if (simpleQuery.hasResult()) {
            hasError = true;
        }
        return hasError;
    }

    public DTOSet getImport() throws QueryException, SQLModelException {
        ImportSpareAssetsModelJt eoModel = (ImportSpareAssetsModelJt) sqlProducer;
        SimpleQuery sq = new SimpleQuery(eoModel.getQueryImportModel(), conn);
        sq.setDTOClassName(ImportSpareAssetsDTOJt.class.getName());
        sq.executeQuery();
        return sq.getDTOSet();
    }

    public String getItemCode(String itemName, String itemSpce) throws SQLModelException, QueryException, ContainerException {
        String itemCode = "";
        ImportSpareAssetsModelJt eoModel = (ImportSpareAssetsModelJt) sqlProducer;
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

    public String getEmployeeId(String employeeNumber) throws SQLModelException, QueryException, ContainerException {
        String employeeId = "";
        ImportSpareAssetsModelJt eoModel = (ImportSpareAssetsModelJt) sqlProducer;
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
        ImportSpareAssetsModelJt eoModel = (ImportSpareAssetsModelJt) sqlProducer;
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
        ImportSpareAssetsModelJt eoModel = (ImportSpareAssetsModelJt) sqlProducer;
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

    public File getExportFile() throws DataTransException, SQLModelException {
        ImportSpareAssetsModelJt modelProducer = (ImportSpareAssetsModelJt) sqlProducer;
        SQLModel sqlModel = modelProducer.getImportErrorModel();
        String reportTitle = "备品备件导入错误信息";
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
        fieldMap.put("BARCODE", "低值易耗资产标签号");
        fieldMap.put("ITEM_NAME", "低值易耗品名称");
        fieldMap.put("ITEM_SPEC", "规格型号");
        fieldMap.put("ITEM_UNIT", "计量单位");
        fieldMap.put("WORKORDER_OBJECT_CODE", "资产地点三段组合编码");
        fieldMap.put("WORKORDER_OBJECT_NAME", "资产地点描述");
        fieldMap.put("RESPONSIBILITY_USER", "资产管理员员工编号");
        fieldMap.put("EMPLOYEE_NAME", "责任人姓名");
        fieldMap.put("CONTENT_CODE", "重要低耗对应目录类别组合代码");
        fieldMap.put("MANUFACTURER_ID", "厂商");
        fieldMap.put("ITEM_STATUS", "设备状态代码");
        fieldMap.put("SPECIALITY_DEPT", "实物部门代码");
        fieldMap.put("SPECIALITY_USER", "专业责任人员工编号");
        fieldMap.put("MAINTAIN_USER", "实际使用人姓名");
        fieldMap.put("PRICE", "低值易耗品价值");
        fieldMap.put("DZYH_START_DATE", "启用日期");
        fieldMap.put("REMARK", "备注");
        fieldMap.put("ERROR", "错误信息");
        return fieldMap;
    }

    public String getAddressId(String objectCode) throws SQLModelException, QueryException, ContainerException {
        String addressId = "";
        ImportSpareAssetsModelJt eoModel = (ImportSpareAssetsModelJt) sqlProducer;
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
}