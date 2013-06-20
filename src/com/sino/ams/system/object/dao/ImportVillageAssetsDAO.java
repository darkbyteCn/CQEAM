package com.sino.ams.system.object.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.onnet.dto.AmsItemOnNetDTO;
import com.sino.ams.system.object.dto.ImportVillageAssetsDTO;
import com.sino.ams.system.object.model.ImportVillageAssetsModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.datatrans.CustomTransData;
import com.sino.base.db.datatrans.DataRange;
import com.sino.base.db.datatrans.DataTransfer;
import com.sino.base.db.datatrans.TransRule;
import com.sino.base.db.datatrans.TransferFactory;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.CalendarUtil;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2009-5-23
 * Time: 19:44:07
 * To change this template use File | Settings | File Templates.
 */
public class ImportVillageAssetsDAO  extends AMSBaseDAO {

    private SfUserDTO sfUser = null;
    /**
     * 功能：地点导入 AMS_OBJECT_IMPORT 数据访问层构造函数
     * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsItemOnNetDTO 本次操作的数据
     * @param conn Connection 数据库连接，由调用者传入。
     */
    public ImportVillageAssetsDAO(SfUserDTO userAccount, AmsItemOnNetDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        sfUser = userAccount;
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     * @param userAccount BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        ImportVillageAssetsDTO dtoPara = (ImportVillageAssetsDTO)dtoParameter;
        super.sqlProducer = new ImportVillageAssetsModel((SfUserDTO)userAccount, dtoPara);
    }

    /**
     * 功能：删除接口表的数据。
     * @throws com.sino.base.exception.SQLModelException
     */
     public void deleteImportModel() throws SQLModelException, QueryException, DataHandleException {
            ImportVillageAssetsModel eoModel = (ImportVillageAssetsModel) sqlProducer;
            SQLModel sqlModel = eoModel.deleteImportModel();
            DBOperator.updateRecord(sqlModel, conn);
    }


    /**
     * 功能：检查接口表数据的有效性。
     */
    public void doVerifyData(DTOSet dtoSet) throws SQLModelException, QueryException, ContainerException {
        if (dtoSet != null && dtoSet.getSize() > 0) {
            for (int i = 0; i < dtoSet.getSize(); i++) {
                ImportVillageAssetsDTO villageDTO = (ImportVillageAssetsDTO) dtoSet.getDTO(i);
                if (StrUtil.isEmpty(villageDTO.getCompanyCode())) {
                    insertImprotErrorData(villageDTO.getBarcode(), "公司代码不能为空");
                } else if (StrUtil.isEmpty(villageDTO.getBarcode())) {
                    insertImprotErrorData(villageDTO.getBarcode(), "村通资产标签号不能为空");
                } else if (StrUtil.isEmpty(villageDTO.getItemName())) {
                    insertImprotErrorData(villageDTO.getBarcode(), "资产名称不能为空");
                } else if (StrUtil.isEmpty(villageDTO.getItemSpec())) {
                    insertImprotErrorData(villageDTO.getBarcode(), "规格型号不能为空");
                } else if (StrUtil.isEmpty(villageDTO.getEmployeeNumber())) {
                    insertImprotErrorData(villageDTO.getBarcode(), "责任人编号不能为空");
                } else if (StrUtil.isEmpty(villageDTO.getEmployeeName())) {
                    insertImprotErrorData(villageDTO.getBarcode(), "责任人姓名不能为空");
                } else if (StrUtil.isEmpty(villageDTO.getWorkorderObjectCode())) {
                    insertImprotErrorData(villageDTO.getBarcode(), "资产地点代码不能为空");
                } else if (!validateOU(villageDTO.getCompanyCode())) {
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
                } else if (!validateNewResDept(villageDTO.getMaintainDept()) && !StrUtil.isEmpty(villageDTO.getMaintainDept())) {
                    insertImprotErrorData(villageDTO.getBarcode(), "使用部门代码不存在");
                } else if (!validateEmployee(villageDTO.getMaintainDept(), villageDTO.getEmployeeNumber()) && !StrUtil.isEmpty(villageDTO.getMaintainDept())) {
                    insertImprotErrorData(villageDTO.getBarcode(), "使用部门和责任人编号不一致");
                } else if (!validateStartDate(villageDTO.getVillageStartDate()) && !StrUtil.isEmpty(villageDTO.getVillageStartDate())) {
                    insertImprotErrorData(villageDTO.getBarcode(), "启用日期格式不正确");
                } else if (!validateTenancy(villageDTO.getPrice()) && !StrUtil.isEmpty(villageDTO.getPrice())) {
                    insertImprotErrorData(villageDTO.getBarcode(), "成本必须是数字类型");
                } else if (!validateTenancy(villageDTO.getDeprnCost()) && !StrUtil.isEmpty(villageDTO.getDeprnCost())) {
                    insertImprotErrorData(villageDTO.getBarcode(), "净值必须是数字类型");
                } else if (!validateTenancy(villageDTO.getScrapValue()) && !StrUtil.isEmpty(villageDTO.getScrapValue())) {
                    insertImprotErrorData(villageDTO.getBarcode(), "残值必须是数字类型");
                } else if (!validateTenancy(villageDTO.getUseYear()) && !StrUtil.isEmpty(villageDTO.getUseYear())) {
                    insertImprotErrorData(villageDTO.getBarcode(), "使用年限必须是数字类型");
                } else if (!validateTenancy(villageDTO.getRemainMonth()) && !StrUtil.isEmpty(villageDTO.getRemainMonth())) {
                    insertImprotErrorData(villageDTO.getBarcode(), "剩余月数必须是数字类型");
                } else if (!validateBarcodeLength(villageDTO.getBarcode())) {
                    insertImprotErrorData(villageDTO.getBarcode(), "条码必须为13位");
                } else if (!validateBarcode(villageDTO.getBarcode())) {
                    insertImprotErrorData(villageDTO.getBarcode(), "资产标签号存在小写字母");
                } else if (!validateBarcode2(villageDTO.getBarcode())) {
                    insertImprotErrorData(villageDTO.getBarcode(), "资产标签号中不存在“CT”或“DG”");
                }
            }
        }
    }

    public boolean validateBarcode2(String barCode) {
        boolean isTrue = true;
        int count = barCode.indexOf("CT");
        int count2 = barCode.indexOf("DG");
        if (count < 0 && count2 < 0) {
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

    /**
     * 功能：校验当前用户新增村通、代管资产的权限
     * @throws SQLModelException
     */
     public boolean validateOU(String companyCode) throws SQLModelException, QueryException {
        boolean hasBarcode=false;
        ImportVillageAssetsModel eoModel = (ImportVillageAssetsModel) sqlProducer;
        SQLModel sqlModel = eoModel.validateOU(companyCode);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        if(simpleQuery.hasResult()){
           hasBarcode = true;
        }
        return hasBarcode;
    }

    /**
     * 功能：校验成本、净值、残值、使用年限、剩余月数必须是数字类型
     * @throws SQLModelException
     */
     public boolean validateTenancy(String tenancy) throws SQLModelException, QueryException {
        boolean isTrue = true;
        if (!StrUtil.isNumber(tenancy)) {
            isTrue = false;
        }
        return isTrue;
    }

    /**
     * 功能：校验启用日期格式是否正确
     * @throws SQLModelException
     */
     public boolean validateStartDate(String date) throws SQLModelException, QueryException {
        boolean isTrue = true;
        if (!CalendarUtil.isValidDate(date)) {
            isTrue = false;
        }
        return isTrue;
    }

    /**
     * 功能：校验责任人，责任部门对应是否正确
     * @throws SQLModelException
     */
     public boolean validateEmployee(String deptCode, String employeeNumber) throws SQLModelException, QueryException {
        boolean hasBarcode=false;
        ImportVillageAssetsModel eoModel = (ImportVillageAssetsModel) sqlProducer;
        SQLModel sqlModel = eoModel.validateEmployee(deptCode, employeeNumber);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        if(simpleQuery.hasResult()){
           hasBarcode = true;
        }
        return hasBarcode;
    }

    /**
     * 功能：校验AMS_VILLAGE_ASSETS_IMPORT中BARCODE是否属于本公司BARCODE
     * @throws SQLModelException
     */
     public boolean validateBarcodeOu(String barcode, String companyCode) throws SQLModelException, QueryException {
        boolean hasBarcode=false;
        String subBarcode = barcode.substring(0,4);
        if (subBarcode.equals(companyCode)) {
            hasBarcode = true;
        }
        return hasBarcode;
    }

    /**
     * 功能：校验AMS_VILLAGE_ASSETS_IMPORT中NEW_ITEM_NAME、NEW_ITEM_SPEC是否有效
     * @throws SQLModelException
     */
     public boolean validateItemNS(String itemName, String itemSpec) throws SQLModelException, QueryException {
        boolean hasBarcode=false;
        ImportVillageAssetsModel eoModel = (ImportVillageAssetsModel) sqlProducer;
        SQLModel sqlModel = eoModel.validateItemNS(itemName, itemSpec);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        if(simpleQuery.hasResult()){
           hasBarcode = true;
        }
        return hasBarcode;
    }

    /**
     * 功能：校验AMS_VILLAGE_ASSETS_IMPORT中EMPLOYEE_NUMBER是否有效
     * @throws SQLModelException
     */
     public boolean validateNewEmployeeNum(String newEmployeeNum) throws SQLModelException, QueryException {
        boolean hasBarcode=false;
        ImportVillageAssetsModel eoModel = (ImportVillageAssetsModel) sqlProducer;
        SQLModel sqlModel = eoModel.validateNewEmployeeNum(newEmployeeNum);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        if(simpleQuery.hasResult()){
           hasBarcode = true;
        }
        return hasBarcode;
    }

    /**
     * 功能：校验AMS_VILLAGE_ASSETS_IMPORT中NEW_OBJECT_CODE是否有效
     * @throws SQLModelException
     */
     public boolean validateObjectCode(String newObjectCode) throws SQLModelException, QueryException {
        boolean hasBarcode=false;
        ImportVillageAssetsModel eoModel = (ImportVillageAssetsModel) sqlProducer;
        SQLModel sqlModel = eoModel.validateObjectCode(newObjectCode);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        if(simpleQuery.hasResult()){
           hasBarcode = true;
        }
        return hasBarcode;
     }

    /**
     * 功能：校验AMS_VILLAGE_ASSETS_IMPORT中NEW_OBJECT_CODE是否有效
     * @throws SQLModelException
     */
     public boolean validateContentCode(String contentCode) throws SQLModelException, QueryException {
        boolean hasBarcode=false;
        ImportVillageAssetsModel eoModel = (ImportVillageAssetsModel) sqlProducer;
        SQLModel sqlModel = eoModel.validateContentCode(contentCode);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        if(simpleQuery.hasResult()){
           hasBarcode = true;
        }
        return hasBarcode;
     }

    /**
     * 功能：校验AMS_VILLAGE_ASSETS_IMPORT中NEW_RESPONSIBILITY_DEPT是否有效
     * @throws SQLModelException
     */
     public boolean validateNewResDept(String newResDept) throws SQLModelException, QueryException {
        boolean hasBarcode=false;
        ImportVillageAssetsModel eoModel = (ImportVillageAssetsModel) sqlProducer;
        SQLModel sqlModel = eoModel.validateNewResDept(newResDept);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        if(simpleQuery.hasResult()){
           hasBarcode = true;
        }
        return hasBarcode;
     }

    /**
     * 功能：插入错误信息。
     * @throws SQLModelException
     */
     public void insertImprotErrorData(String barcode,String codeError) throws SQLModelException {
        try {
            ImportVillageAssetsModel onNetModel = (ImportVillageAssetsModel) sqlProducer;
            SQLModel sqlModel = onNetModel.insertImprotErrorData(barcode,codeError);
            DBOperator.updateRecord(sqlModel, conn);
        } catch (DataHandleException ex) {
            ex.printLog();
        }
    }

    /**
     * 功能：校验BARCODE在ETS_ITEM_INFO中是否存在
     * @throws SQLModelException
     */
     public boolean validateSameEiiBarcode(String barcode) throws SQLModelException, QueryException, ContainerException {
        boolean hasBarcode=true;
        ImportVillageAssetsModel eoModel = (ImportVillageAssetsModel) sqlProducer;
        SQLModel sqlModel = eoModel.validateSameEiiBarcode(barcode);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        if(simpleQuery.hasResult()){
           hasBarcode = false;
        }
        return hasBarcode;
    }

    /**
     * 功能：校验AMS_VILLAGE_ASSETS_IMPORT中BARCODE条码重复
     * @throws SQLModelException
     */
     public boolean validateSameBarcode(String barcode) throws SQLModelException, QueryException, ContainerException {
        boolean hasBarcode=true;
        ImportVillageAssetsModel eoModel = (ImportVillageAssetsModel) sqlProducer;
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

    /**
     * 功能：校验在AMS_VILLAGE_ASSETS_IMPORT是否存在导入错误
     * @throws SQLModelException
     */
     public boolean importHasError() throws SQLModelException, QueryException {
        boolean hasError=false;
            ImportVillageAssetsModel eoModel = (ImportVillageAssetsModel) sqlProducer;
            SQLModel sqlModel = eoModel.hasErrorModel();
            SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
            simpleQuery.executeQuery();
            if(simpleQuery.hasResult()){
               hasError = true;
            }
        return hasError;
    }

    /**
     * 功能：在AMS_VILLAGE_ASSETS_IMPORT中获取导入成功的数据
     * @throws QueryException
     */
       public DTOSet getImport() throws QueryException, SQLModelException {
            ImportVillageAssetsModel eoModel = (ImportVillageAssetsModel) sqlProducer;
            SimpleQuery sq = new SimpleQuery(eoModel.getQueryImportModel(), conn);
            sq.setDTOClassName(ImportVillageAssetsDTO.class.getName());
            sq.executeQuery();
            return sq.getDTOSet();
        }

    /**
     * 功能：通过NEW_ITEM_NAME,NEW_ITEM_SPEC取得ITEM_CODE
     * @throws SQLModelException
     */
     public String getItemCode(String itemName, String itemSpce) throws SQLModelException, QueryException, ContainerException {
        String itemCode = "";
        ImportVillageAssetsModel eoModel = (ImportVillageAssetsModel) sqlProducer;
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

    /**
     * 功能：通过NEW_OBJECT_CODE取得ADDRESS_ID
     * @throws SQLModelException
     */
     public String getAddressId(String objectCode) throws SQLModelException, QueryException, ContainerException {
        String addressId = "";
        ImportVillageAssetsModel eoModel = (ImportVillageAssetsModel) sqlProducer;
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

    /**
     * 功能：通过NEW_EMPLOYEE_NUMBER取得EMPLOYEE_ID
     * @throws SQLModelException
     */
     public String getEmployeeId(String employeeNumber) throws SQLModelException, QueryException, ContainerException {
        String employeeId = "";
        ImportVillageAssetsModel eoModel = (ImportVillageAssetsModel) sqlProducer;
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

    /**
     * 功能：通过NEW_EMPLOYEE_NUMBER取得DEPT_CODE
     * @throws SQLModelException
     */
     public String getDeptCode(String employeeNumber) throws SQLModelException, QueryException, ContainerException {
        String employeeId = "";
        ImportVillageAssetsModel eoModel = (ImportVillageAssetsModel) sqlProducer;
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

    /**
     * 功能：错误信息导出EXCEL
     */
    public File getExportFile() throws DataTransException, SQLModelException {
        ImportVillageAssetsModel modelProducer = (ImportVillageAssetsModel) sqlProducer;
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

    private Map getFieldMap(){
        Map fieldMap = new HashMap();
        fieldMap.put("COMPANY_CODE", "公司代码");
        fieldMap.put("BARCODE", "村通资产标签号");
        fieldMap.put("ITEM_NAME", "资产名称");
        fieldMap.put("ITEM_SPEC", "规格型号");
        fieldMap.put("EMPLOYEE_NUMBER", "责任人编号");
        fieldMap.put("EMPLOYEE_NAME", "责任人姓名");
        fieldMap.put("WORKORDER_OBJECT_CODE", "地点组合编码");
        fieldMap.put("EQUIPMENT_PERFORMANCE", "设备性能");
        fieldMap.put("CONTENT_CODE", "资产类别代码组合");
        fieldMap.put("CONTENT_NAME", "资产类别描述");
        fieldMap.put("WORKORDER_OBJECT_NAME", "资产地点描述");
        fieldMap.put("SPECIALITY_DEPT", "实物部门代码");
        fieldMap.put("MAINTAIN_USER", "使用人姓名");
        fieldMap.put("MAINTAIN_DEPT", "使用部门代码");
        fieldMap.put("PRICE", "成本");
        fieldMap.put("DEPRN_COST", "净值");
        fieldMap.put("SCRAP_VALUE", "残值");
        fieldMap.put("VILLAGE_START_DATE", "启用日期");
        fieldMap.put("USE_YEAR", "使用年限");
        fieldMap.put("REMAIN_MONTH", "剩余月数");
        fieldMap.put("REMARK", "备注");
        fieldMap.put("ERROR", "错误信息");
        return fieldMap;
    }
}
