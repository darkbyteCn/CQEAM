package com.sino.ams.newasset.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.newasset.model.CommonQueryModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.db.datatrans.CustomTransData;
import com.sino.base.db.datatrans.DataRange;
import com.sino.base.db.datatrans.DataTransfer;
import com.sino.base.db.datatrans.TransRule;
import com.sino.base.db.datatrans.TransferFactory;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 中国移动资产实物管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class CommonQueryDAO extends AMSBaseDAO {

    public CommonQueryDAO(SfUserDTO userAccount,
                          AmsAssetsAddressVDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    /**
     * 功能：SQL生成器baseSQLProducer的初始化。具体的DAO继承时初始化具体的SQL生成器。
     * @param userAccount BaseUserDTO
     * @param dtoParameter DTO
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsAssetsAddressVDTO dtoPara = (AmsAssetsAddressVDTO) dtoParameter;
        super.sqlProducer = new CommonQueryModel((SfUserDTO) userAccount,
                                                 dtoPara);
    }

    public File getExportFile() throws DataTransException {
        File file = null;
        try {
            CommonQueryModel assetsModel = (CommonQueryModel) sqlProducer;
            SQLModel sqlModel = assetsModel.getPageQueryModel();
            String reportTitle = "综合查询导出资产";
            String fileName = reportTitle + ".xls";
            String filePath = WorldConstant.USER_HOME;
            filePath += WorldConstant.FILE_SEPARATOR;
            filePath += fileName;

            TransRule rule = new TransRule();
            rule.setDataSource(sqlModel);
            rule.setSourceConn(conn);
            rule.setTarFile(filePath);
            DataRange range = new DataRange();
            rule.setDataRange(range);
            Map fieldMap = getFieldMap();
            rule.setFieldMap(fieldMap);
            CustomTransData custData = new CustomTransData();
            custData.setReportTitle(reportTitle);
            custData.setReportPerson(userAccount.getUsername());
            custData.setNeedReportDate(true);
            rule.setCustData(custData);
//			rule.setSheetSize(2000);
            rule.setCalPattern(LINE_PATTERN);
            TransferFactory factory = new TransferFactory();
            DataTransfer transfer = factory.getTransfer(rule);
            transfer.transData();
            file = (File) transfer.getTransResult();
        } catch (SQLModelException ex) {
            ex.printLog();
            throw new DataTransException(ex);
        }
        return file;
    }

    private Map getFieldMap() {
        Map fieldMap = new HashMap();
        fieldMap.put("BARCODE", "资产标签");
        fieldMap.put("ASSET_NUMBER", "资产编号");
        fieldMap.put("FA_CATEGORY1", "资产类别一");
        fieldMap.put("FA_CATEGORY2", "资产类别二");
        fieldMap.put("SEGMENT1", "类别代码一");
        fieldMap.put("SEGMENT2", "类别代码二");
        fieldMap.put("FA_CATEGORY_CODE", "类别代码");
        fieldMap.put("ASSETS_DESCRIPTION", "资产名称");
        fieldMap.put("MODEL_NUMBER", "资产型号");
        fieldMap.put("ITEM_CATEGORY_NAME", "设备分类");
        fieldMap.put("ITEM_NAME", "设备名称");
        fieldMap.put("ITEM_SPEC", "设备型号");
        fieldMap.put("UNIT_OF_MEASURE", "计量单位");
        fieldMap.put("CURRENT_UNITS", "数量");
        fieldMap.put("COST", "资产原值");
        fieldMap.put("LIFE_IN_YEARS", "服务年限");
        fieldMap.put("DATE_PLACED_IN_SERVICE", "启用日期");
        fieldMap.put("DEPRN_COST", "资产净值");
        fieldMap.put("DEPRECIATION", "累计折旧");
        fieldMap.put("DEPRECIATION_ACCOUNT", "折旧账户");
        fieldMap.put("SCRAP_VALUE", "资产残值");
        fieldMap.put("BOOK_TYPE_CODE", "资产账簿");
        fieldMap.put("PROJECT_NUMBER", "项目编号");
        fieldMap.put("PROJECT_NAME", "项目名称");
        fieldMap.put("VENDOR_NUMBER", "厂家编号");
        fieldMap.put("VENDOR_NAME", "厂家名称");
        fieldMap.put("ITEM_STATUS_NAME", "资产状态");
        fieldMap.put("DEPT_NAME", "责任部门");
        fieldMap.put("RESPONSIBILITY_USER_NAME", "责任人");
        fieldMap.put("EMPLOYEE_NUMBER", "责任人员工号");
        fieldMap.put("MAINTAIN_USER_NAME", "保管人");
        fieldMap.put("WORKORDER_OBJECT_CODE", "地点代码");
        fieldMap.put("WORKORDER_OBJECT_NAME", "地点简称");
        fieldMap.put("WORKORDER_OBJECT_LOCATION", "所在地点");
        fieldMap.put("COUNTY_NAME", "所在区县");
        fieldMap.put("COMPANY", "所属公司");
        return fieldMap;
    }
}
