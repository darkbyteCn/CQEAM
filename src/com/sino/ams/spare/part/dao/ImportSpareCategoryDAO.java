package com.sino.ams.spare.part.dao;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.spare.part.model.ImportSpareCategoryModel;
import com.sino.ams.spare.part.dto.ImportSpareCategoryDTO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.*;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.datatrans.*;
import com.sino.base.util.StrUtil;
import com.sino.base.data.RowSet;
import com.sino.base.data.Row;
import com.sino.base.constant.WorldConstant;

import java.sql.Connection;
import java.io.File;
import java.util.Map;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: wangzp
 * Date: 2011-12-12
 * Function: 备件设备分类导入
 * To change this template use File | Settings | File Templates.
 */
public class ImportSpareCategoryDAO  extends AMSBaseDAO {
    private SfUserDTO sfUser = null;

    public ImportSpareCategoryDAO(SfUserDTO userAccount, ImportSpareCategoryDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        sfUser = userAccount;
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        ImportSpareCategoryDTO dtoPara = (ImportSpareCategoryDTO)dtoParameter;
        super.sqlProducer = new ImportSpareCategoryModel((SfUserDTO)userAccount, dtoPara);
    }
    
     /**
      * 清空当前用户的临时表
      */
     public void deleteImportModel() throws SQLModelException, QueryException, DataHandleException {
            ImportSpareCategoryModel eoModel = (ImportSpareCategoryModel) sqlProducer;
            SQLModel sqlModel = eoModel.deleteImportModel();
            DBOperator.updateRecord(sqlModel, conn);
    }

     /**
      * 导入数据校验
      */
    public void doVerifyData(DTOSet dtoSet) throws SQLModelException, QueryException, ContainerException {
        if (dtoSet != null && dtoSet.getSize() > 0) {
            for (int i = 0; i < dtoSet.getSize(); i++) {
                ImportSpareCategoryDTO dto = (ImportSpareCategoryDTO) dtoSet.getDTO(i);
                if (StrUtil.isEmpty(dto.getItemName())) {
                    insertImprotErrorData(dto.getId(), "设备名称不能为空");
                } else if (StrUtil.isEmpty(dto.getItemSpec())) {
                    insertImprotErrorData(dto.getId(), "设备型号不能为空");
                } else if (StrUtil.isEmpty(dto.getItemCategory())) {
                    insertImprotErrorData(dto.getId(), "设备类型不能为空");
                } else if (StrUtil.isEmpty(dto.getSpareUsage())) {
                    insertImprotErrorData(dto.getId(), "用途不能为空");
                } else if (StrUtil.isEmpty(dto.getVendorId())) {
                    insertImprotErrorData(dto.getId(), "厂商不能为空");
                } else if (StrUtil.isEmpty(dto.getItemUnit())) {
                    insertImprotErrorData(dto.getId(), "单位不能为空");
                } else if (!validateVendorId(dto.getVendorId())) {
                    insertImprotErrorData(dto.getId(), "厂商ID不存在");
                } else if (!validateSameCategory(dto.getItemName(), dto.getItemSpec(), dto.getItemCategory(), dto.getSpareUsage(), dto.getVendorId())) {
                    insertImprotErrorData(dto.getId(), "EXCEL中存在相同行信息");
                } else if (!validateExistCategory(dto.getItemName(), dto.getItemSpec(), dto.getItemCategory(), dto.getSpareUsage(), dto.getVendorId())) {
                    insertImprotErrorData(dto.getId(), "备件设备分类在系统中已存在");
                }
            }
        }
    }

    public void insertImprotErrorData(String id,String codeError) throws SQLModelException {
        try {
            ImportSpareCategoryModel onNetModel = (ImportSpareCategoryModel) sqlProducer;
            SQLModel sqlModel = onNetModel.insertImprotErrorData(id,codeError);
            DBOperator.updateRecord(sqlModel, conn);
        } catch (DataHandleException ex) {
            ex.printLog();
        }
    }

    public boolean validateSameCategory(String itemName, String itemSpec, String itemCategory, String spareUsage, String vendorId) throws SQLModelException, QueryException, ContainerException {
        boolean hasBarcode=true;
        ImportSpareCategoryModel eoModel = (ImportSpareCategoryModel) sqlProducer;
        SQLModel sqlModel = eoModel.validateSameCategory(itemName, itemSpec, itemCategory, spareUsage, vendorId);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        RowSet rs = simpleQuery.getSearchResult();
        if (rs != null && rs.getSize() > 0) {
            Row row = rs.getRow(0);
            int count = Integer.parseInt(StrUtil.nullToString(row.getValue("RET_QTY")));
            if (count > 1) {
                hasBarcode = false;
            }
        }
        return hasBarcode;
    }

    public boolean validateExistCategory(String itemName, String itemSpec, String itemCategory, String spareUsage, String vendorId) throws SQLModelException, QueryException, ContainerException {
        boolean hasBarcode=true;
        ImportSpareCategoryModel eoModel = (ImportSpareCategoryModel) sqlProducer;
        SQLModel sqlModel = eoModel.validateExistCategory(itemName, itemSpec, itemCategory, spareUsage, vendorId);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        if(simpleQuery.hasResult()){
           hasBarcode = false;
        }
        return hasBarcode;
    }

    public boolean validateVendorId(String vendorId) throws SQLModelException, QueryException {
        boolean hasBarcode=false;
        ImportSpareCategoryModel eoModel = (ImportSpareCategoryModel) sqlProducer;
        SQLModel sqlModel = eoModel.validateVendorId(vendorId);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        if(simpleQuery.hasResult()){
           hasBarcode = true;
        }
        return hasBarcode;
     }
    /**
     * 判断校验后的数据是否存在错误
     */
    public boolean importHasError() throws SQLModelException, QueryException {
        boolean hasError=false;
            ImportSpareCategoryModel eoModel = (ImportSpareCategoryModel) sqlProducer;
            SQLModel sqlModel = eoModel.hasErrorModel();
            SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
            simpleQuery.executeQuery();
            if(simpleQuery.hasResult()){
               hasError = true;
            }
        return hasError;
    }
    
    /**
     * 获取临时表中的数据 （条件：当前用户）
     * @return DTOSet
     */
    public DTOSet getImport() throws QueryException, SQLModelException {
        ImportSpareCategoryModel eoModel = (ImportSpareCategoryModel) sqlProducer;
        SimpleQuery sq = new SimpleQuery(eoModel.getQueryImportModel(), conn);
        sq.setDTOClassName(ImportSpareCategoryDTO.class.getName());
        sq.executeQuery();
        return sq.getDTOSet();
    }

    //不用
    public File getExportFile() throws DataTransException, SQLModelException {
        ImportSpareCategoryModel modelProducer = (ImportSpareCategoryModel) sqlProducer;
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
        fieldMap.put("SPECIALITY_DEPT", "专业部门代码");
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
