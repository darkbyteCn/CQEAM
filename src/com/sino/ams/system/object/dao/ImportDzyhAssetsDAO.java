package com.sino.ams.system.object.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.system.object.dto.ImportDzyhAssetsDTO;
import com.sino.ams.system.object.model.ImportDzyhAssetsModel;
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
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.log.Logger;
import com.sino.base.util.CalendarUtil;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2009-5-27
 * Time: 10:09:23
 * To change this template use File | Settings | File Templates.
 */
public class ImportDzyhAssetsDAO  extends AMSBaseDAO {

    private SfUserDTO sfUser = null;
    /**
     * 功能：地点导入 AMS_OBJECT_IMPORT 数据访问层构造函数
     * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsItemOnNetDTO 本次操作的数据
     * @param conn Connection 数据库连接，由调用者传入。
     */
    public ImportDzyhAssetsDAO(SfUserDTO userAccount, ImportDzyhAssetsDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        sfUser = userAccount;
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     * @param userAccount BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        ImportDzyhAssetsDTO dtoPara = (ImportDzyhAssetsDTO)dtoParameter;
        super.sqlProducer = new ImportDzyhAssetsModel((SfUserDTO)userAccount, dtoPara);
    }

    /**
     * 功能：删除接口表的数据。
     * @throws com.sino.base.exception.SQLModelException
     */
     public void deleteImportModel() throws SQLModelException, QueryException, DataHandleException {
            ImportDzyhAssetsModel eoModel = (ImportDzyhAssetsModel) sqlProducer;
            SQLModel sqlModel = eoModel.deleteImportModel();
            DBOperator.updateRecord(sqlModel, conn);
    }


    /**
     * 功能：检查接口表数据的有效性。
     */
    public void doVerifyData(DTOSet dtoSet) throws SQLModelException, QueryException, ContainerException {
        if (dtoSet != null && dtoSet.getSize() > 0) {
            for (int i = 0; i < dtoSet.getSize(); i++) {
                ImportDzyhAssetsDTO dzyhDTO = (ImportDzyhAssetsDTO) dtoSet.getDTO(i);
                if (StrUtil.isEmpty(dzyhDTO.getCompanyCode())) {
                    insertImprotErrorData(dzyhDTO.getBarcode(), "公司代码不能为空");
                } else if (StrUtil.isEmpty(dzyhDTO.getBarcode())) {
                    insertImprotErrorData(dzyhDTO.getBarcode(), "低值易耗资产标签号不能为空");
                } else if (StrUtil.isEmpty(dzyhDTO.getItemName())) {
                    insertImprotErrorData(dzyhDTO.getBarcode(), "资产名称不能为空");
                } else if (StrUtil.isEmpty(dzyhDTO.getItemSpec())) {
                    insertImprotErrorData(dzyhDTO.getBarcode(), "规格型号不能为空");
                } else if (StrUtil.isEmpty(dzyhDTO.getItemUnit())) {
                    insertImprotErrorData(dzyhDTO.getBarcode(), "单位不能为空");
                } else if (StrUtil.isEmpty(dzyhDTO.getSpecialityDept())) {
                    insertImprotErrorData(dzyhDTO.getBarcode(), "实物部门代码不能为空");
                } else if (StrUtil.isEmpty(dzyhDTO.getSpecialityUser())) {
                    insertImprotErrorData(dzyhDTO.getBarcode(), "专业责任人编号不能为空");
                } else if (StrUtil.isEmpty(dzyhDTO.getAddress())) {
                    insertImprotErrorData(dzyhDTO.getBarcode(), "地点不能为空");
                }
//                else if (StrUtil.isEmpty(lastingDTO.getResponsibilityDept())) {
//                    insertImprotErrorData(lastingDTO.getBarcode(), "责任部门不能为空");
//                }
                else if (StrUtil.isEmpty(dzyhDTO.getResponsibilityUser())) {
                    insertImprotErrorData(dzyhDTO.getBarcode(), "责任部门资产管理员编号不能为空");
                } else if (!validateOU(dzyhDTO.getCompanyCode())) {
                    insertImprotErrorData(dzyhDTO.getBarcode(), "该用户对应的公司代码不正确");
                } else if (!validateBarcodeOu(dzyhDTO.getBarcode(), dzyhDTO.getCompanyCode())) {
                    insertImprotErrorData(dzyhDTO.getBarcode(), "该条码对应公司不正确");
                } else if (!validateSameBarcode(dzyhDTO.getBarcode())) {
                    insertImprotErrorData(dzyhDTO.getBarcode(), "EXCEL中条码重复");
                } else if (!validateSameEiiBarcode(dzyhDTO.getBarcode())) {
                    insertImprotErrorData(dzyhDTO.getBarcode(), "条码在实物系统中已存在");
                } else if (!validateNewResDept(dzyhDTO.getSpecialityDept())) {
                    insertImprotErrorData(dzyhDTO.getBarcode(), "实物部门代码不存在");
                } else if (!validateNewEmployeeNum(dzyhDTO.getSpecialityUser())) {
                    insertImprotErrorData(dzyhDTO.getBarcode(), "公司专业责任人编号不存在");
                }
//                else if (!validateNewResDept(lastingDTO.getResponsibilityDept())) {
//                    insertImprotErrorData(lastingDTO.getBarcode(), "责任部门代码不存在");
//                }
                else if (!validateNewEmployeeNum(dzyhDTO.getResponsibilityUser())) {
                    insertImprotErrorData(dzyhDTO.getBarcode(), "责任部门资产管理员编号不存在");
                } else if (!validateEmployee(dzyhDTO.getSpecialityDept(), dzyhDTO.getSpecialityUser())) {
                    insertImprotErrorData(dzyhDTO.getBarcode(), "实物部门和专业责任人编号不一致");
                }
//                else if (!validateEmployee(lastingDTO.getResponsibilityDept(), lastingDTO.getResponsibilityUser())) {
//                    insertImprotErrorData(lastingDTO.getBarcode(), "责任部门和责任人编号不一致");
//                }
                else if (!validateStartDate(dzyhDTO.getDzyhStartDate()) && !StrUtil.isEmpty(dzyhDTO.getDzyhStartDate())) {
                    insertImprotErrorData(dzyhDTO.getBarcode(), "启用日期格式不正确");
                } else if (!validateTenancy(dzyhDTO.getPrice()) && !StrUtil.isEmpty(dzyhDTO.getPrice())) {
                    insertImprotErrorData(dzyhDTO.getBarcode(), "成本必须是数字类型");
                //模板和导入表都没有IS_TD字段
/*                } else if (!validateTD(dzyhDTO.getTd()) && !StrUtil.isEmpty(dzyhDTO.getTd())) {
                    insertImprotErrorData(dzyhDTO.getBarcode(), "TD属性填写错误");*/
                } else if (!validateBarcode(dzyhDTO.getBarcode())) {
                    insertImprotErrorData(dzyhDTO.getBarcode(), "资产标签号存在小写字母");
                } else if (!validateBarcodeLength(dzyhDTO.getBarcode())) {
                    insertImprotErrorData(dzyhDTO.getBarcode(), "条码必须为13位");
                }
            }
        }
    }

    private String getNextSysDistributeCode(Connection conn) throws SQLException {
        SeqProducer seqProducer = new SeqProducer(conn);
		return String.valueOf( seqProducer.getStrNextSeq("ETS_SYSITEM_DISTRIBUTE") );
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

    /**
     * 功能：插入ETS_SYSITEM_DISTRIBUTE设备分类权限表
     */
     public void insertDistribute(String itemCode, Connection conn) throws DataHandleException, SQLException, SQLModelException, QueryException, ContainerException {
        try {
            ImportDzyhAssetsModel eoModel = (ImportDzyhAssetsModel) sqlProducer;
            SQLModel sqlModel = eoModel.findOu();
            SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
            simpleQuery.executeQuery();
            RowSet rs = simpleQuery.getSearchResult();
            for (int i=0; i<rs.getSize(); i++) {
                String distributeCode = getNextSysDistributeCode(conn);
                String orgId = StrUtil.nullToString(rs.getRow(i).getValue("ORGANIZATION_ID"));
                DBOperator.updateRecord(eoModel.insertDistribute(distributeCode, itemCode, orgId), conn);
            }
            conn.commit();
        }  catch (SQLException e) {
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

    /**
     * 功能：在ETS_SYSTEM_ITEM中找不到对应的ITEM_CODE,新增ITEM_CODE
     */
     public void insertSystemItem(String newItemCode, String itemName, String itemSpce, String itemUnit) throws DataHandleException, SQLException {
        try {
            ImportDzyhAssetsModel eoModel = (ImportDzyhAssetsModel) sqlProducer;
            SQLModel sqlModel = eoModel.insertSystemItem(newItemCode, itemName, itemSpce, itemUnit);
            DBOperator.updateRecord(sqlModel, conn);
            conn.commit();
        }  catch (SQLException e) {
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

    /**
     * 功能：校验TD属性
     */
     public boolean validateTD(String td){
        boolean isTrue = false;
        if (td.equals("Y") || td.equals("N")) {
            isTrue = true;
        }
        return isTrue;
    }

    /**
     * 功能：校验当前用户新增低值易耗资产的权限
     * @throws SQLModelException
     */
     public boolean validateOU(String companyCode) throws SQLModelException, QueryException {
        boolean hasBarcode=false;
        ImportDzyhAssetsModel eoModel = (ImportDzyhAssetsModel) sqlProducer;
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
        ImportDzyhAssetsModel eoModel = (ImportDzyhAssetsModel) sqlProducer;
        SQLModel sqlModel = eoModel.validateEmployee(deptCode, employeeNumber);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        if(simpleQuery.hasResult()){
           hasBarcode = true;
        }
        return hasBarcode;
    }

    /**
     * 功能：校验AMS_DZYH_ASSETS_IMPORT中BARCODE是否属于本公司BARCODE
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
     * 功能：校验AMS_DZYH_ASSETS_IMPORT中EMPLOYEE_NUMBER是否有效
     * @throws SQLModelException
     */
     public boolean validateNewEmployeeNum(String newEmployeeNum) throws SQLModelException, QueryException {
        boolean hasBarcode=false;
        ImportDzyhAssetsModel eoModel = (ImportDzyhAssetsModel) sqlProducer;
        SQLModel sqlModel = eoModel.validateNewEmployeeNum(newEmployeeNum);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        if(simpleQuery.hasResult()){
           hasBarcode = true;
        }
        return hasBarcode;
    }

    /**
     * 功能：校验AMS_DZYH_ASSETS_IMPORT中NEW_RESPONSIBILITY_DEPT是否有效
     * @throws SQLModelException
     */
     public boolean validateNewResDept(String newResDept) throws SQLModelException, QueryException {
        boolean hasBarcode=false;
        ImportDzyhAssetsModel eoModel = (ImportDzyhAssetsModel) sqlProducer;
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
            ImportDzyhAssetsModel onNetModel = (ImportDzyhAssetsModel) sqlProducer;
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
        ImportDzyhAssetsModel eoModel = (ImportDzyhAssetsModel) sqlProducer;
        SQLModel sqlModel = eoModel.validateSameEiiBarcode(barcode);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        if(simpleQuery.hasResult()){
           hasBarcode = false;
        }
        return hasBarcode;
    }

    /**
     * 功能：校验AMS_DZYH_ASSETS_IMPORT中BARCODE条码重复
     * @throws SQLModelException
     */
     public boolean validateSameBarcode(String barcode) throws SQLModelException, QueryException, ContainerException {
        boolean hasBarcode=true;
        ImportDzyhAssetsModel eoModel = (ImportDzyhAssetsModel) sqlProducer;
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
     * 功能：校验在AMS_DZYH_ASSETS_IMPORT是否存在导入错误
     * @throws SQLModelException
     */
     public boolean importHasError() throws SQLModelException, QueryException {
        boolean hasError=false;
            ImportDzyhAssetsModel eoModel = (ImportDzyhAssetsModel) sqlProducer;
            SQLModel sqlModel = eoModel.hasErrorModel();
            SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
            simpleQuery.executeQuery();
            if(simpleQuery.hasResult()){
               hasError = true;
            }
        return hasError;
    }

    /**
     * 功能：在AMS_DZYH_ASSETS_IMPORT中获取导入成功的数据
     * @throws QueryException
     */
       public DTOSet getImport() throws QueryException, SQLModelException {
            ImportDzyhAssetsModel eoModel = (ImportDzyhAssetsModel) sqlProducer;
            SimpleQuery sq = new SimpleQuery(eoModel.getQueryImportModel(), conn);
            sq.setDTOClassName(ImportDzyhAssetsDTO.class.getName());
            sq.executeQuery();
            return sq.getDTOSet();
        }

    /**
     * 功能：通过NEW_ITEM_NAME,NEW_ITEM_SPEC取得ITEM_CODE
     * @throws SQLModelException
     */
     public String getItemCode(String itemName, String itemSpce) throws SQLModelException, QueryException, ContainerException {
        String itemCode = "";
        ImportDzyhAssetsModel eoModel = (ImportDzyhAssetsModel) sqlProducer;
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
     * 功能：通过NEW_EMPLOYEE_NUMBER取得EMPLOYEE_ID
     * @throws SQLModelException
     */
     public String getEmployeeId(String employeeNumber) throws SQLModelException, QueryException, ContainerException {
        String employeeId = "";
        ImportDzyhAssetsModel eoModel = (ImportDzyhAssetsModel) sqlProducer;
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
        ImportDzyhAssetsModel eoModel = (ImportDzyhAssetsModel) sqlProducer;
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
        ImportDzyhAssetsModel modelProducer = (ImportDzyhAssetsModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getImportErrorModel();
        String reportTitle = "低值易耗资产导入错误信息";
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
        fieldMap.put("BARCODE", "低值易耗资产标签号");
        fieldMap.put("ITEM_NAME", "资产名称");
        fieldMap.put("ITEM_SPEC", "规格型号");
        fieldMap.put("ITEM_UNIT", "单位");
        fieldMap.put("SPECIALITY_DEPT", "实物部门代码");
        fieldMap.put("SPECIALITY_USER", "专业责任人编号");
        fieldMap.put("ADDRESS", "地点");
        fieldMap.put("RESPONSIBILITY_DEPT", "责任部门代码");
        fieldMap.put("RESPONSIBILITY_USER", "责任部门管理员编号");
        fieldMap.put("MAINTAIN_USER", "使用人姓名");
        fieldMap.put("PRICE", "成本");
        fieldMap.put("IS_TD", "是否TD");
        fieldMap.put("DZYH_START_DATE", "启用日期");
        fieldMap.put("REMARK", "备注");
        fieldMap.put("ERROR", "错误信息");
        return fieldMap;
    }
}
