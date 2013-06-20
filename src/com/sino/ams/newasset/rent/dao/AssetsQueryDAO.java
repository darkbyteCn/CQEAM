package com.sino.ams.newasset.rent.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.newasset.rent.model.AssetsQueryModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.constant.calen.CalendarConstant;
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

public class AssetsQueryDAO extends AMSBaseDAO {
	private SfUserDTO sfUser = null;

    /**
     * 功能：固定资产当前信息(EAM) ETS_FA_ASSETS 数据访问层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsAssetsAddressVDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
    public AssetsQueryDAO(SfUserDTO userAccount, AmsAssetsAddressVDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        sfUser = userAccount;
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsAssetsAddressVDTO dtoPara = (AmsAssetsAddressVDTO) dtoParameter;
        sqlProducer = new AssetsQueryModel((SfUserDTO) userAccount, dtoPara);
    }

    /**
     * 功能：导出查询SQL资产数据
     * @return String 返回导出Excel文件
     * @throws DataTransException
     * @throws SQLModelException 
     */
    public File exportQueryAssets() throws DataTransException, SQLModelException {
    	AssetsQueryModel assetsModel = (AssetsQueryModel) sqlProducer;
        SQLModel sqlModel = assetsModel.getPageQueryModel();
        return getExportFile(sqlModel);
    }

    /**
     * 功能：根据SQL导出文件
     * @param sqlModel SQLModel
     * @return File
     * @throws DataTransException
     */
    private File getExportFile(SQLModel sqlModel) throws DataTransException {
        File file = null;
        String reportTitle = getFormatTitle();
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
        Map fieldMap = getFieldMap();
        rule.setFieldMap(fieldMap);
        CustomTransData custData = new CustomTransData();
        custData.setReportTitle(reportTitle);
        custData.setReportPerson(userAccount.getUsername());
        custData.setNeedReportDate(true);
        rule.setCustData(custData);
//		rule.setSheetSize(2000);
        rule.setCalPattern(LINE_PATTERN);
        TransferFactory factory = new TransferFactory();
        DataTransfer transfer = factory.getTransfer(rule);
        transfer.transData();
        file = (File) transfer.getTransResult();
        return file;
    }

    private Map getFieldMap() {
        Map fieldMap = null;
        AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
        String treeCategory = dto.getTreeCategory();
        if (treeCategory.equals(AssetsWebAttributes.ASSETS_TREE_PERSON)) { //个人资产
            fieldMap = getAssetsMap();
        }
        return fieldMap;
    }

    /**
     * 功能：获取个人资产的导出表头
     * @return Map
     */
    private static Map getAssetsMap() {
        Map fieldMap = new HashMap();
        fieldMap.put("BARCODE", "资产标签");
        fieldMap.put("ASSET_NUMBER", "资产编号");
        fieldMap.put("FA_CATEGORY1", "资产类别一");
        fieldMap.put("FA_CATEGORY2", "资产类别二");
        fieldMap.put("ASSETS_DESCRIPTION", "资产名称");
        fieldMap.put("MODEL_NUMBER", "资产型号");
        fieldMap.put("UNIT_OF_MEASURE", "计量单位");
        fieldMap.put("ITEM_STATUS", "资产状态");
        fieldMap.put("CURRENT_UNITS", "数量");
        fieldMap.put("COST", "资产原值");
        fieldMap.put("DATE_PLACED_IN_SERVICE", "启用日期");
        fieldMap.put("LIFE_IN_YEARS", "服务年限");
        fieldMap.put("DEPRECIATION", "累计折旧");
        fieldMap.put("DEPRN_COST", "资产净值");
        fieldMap.put("SCRAP_VALUE", "资产残值");
        fieldMap.put("DEPRECIATION_ACCOUNT", "折旧账户代码");
        fieldMap.put("BOOK_TYPE_CODE", "资产账簿");
        fieldMap.put("WORKORDER_OBJECT_CODE", "地点代码");
        fieldMap.put("WORKORDER_OBJECT_NAME", "所在地点");
        fieldMap.put("PROJECT_NAME", "项目名称");
        fieldMap.put("RESPONSIBILITY_USER_NAME", "责任人");
        fieldMap.put("DEPT_NAME", "责任部门");
        fieldMap.put("MAINTAIN_USER_NAME", "保管人");
        fieldMap.put("ITEM_STATUS_NAME", "资产状态");
        fieldMap.put("EMPLOYEE_NUMBER", "员工号编");
        fieldMap.put("ASSETS_CREATE_DATE", "资产创建日期");
        fieldMap.put("DEPRECIATION_ACCOUNT_NAME", "折旧账户名称");
        return fieldMap;
    }

    /**
     * 功能：构造导出Excel的标题
     * @return String
     */
    private String getFormatTitle() {
        StringBuffer reportTitle = new StringBuffer();
        AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
        String treeCategory = dto.getTreeCategory();
        String companyName = dto.getCompanyName();
        String deptName = dto.getDeptName();
        String userName = userAccount.getUsername();
        if (treeCategory.equals(AssetsWebAttributes.ASSETS_TREE_PERSON)) { //个人资产导出
            reportTitle.append(userAccount.getCompany());
            reportTitle.append(WorldConstant.EMPTY_SPACE);
            reportTitle.append(userName);
            reportTitle.append(WorldConstant.EMPTY_SPACE);
            reportTitle.append("资产");
        }
        return reportTitle.toString();
	}
    
    public File exportFile() throws DataTransException {
        File file = null;
        try {
            SQLModel sqlModel = sqlProducer.getPageQueryModel();
            TransRule rule = new TransRule();
            rule.setDataSource(sqlModel);
            rule.setCalPattern(CalendarConstant.LINE_PATTERN);
            rule.setSourceConn(conn);
            String fileName = "送修未返还资产.xls";
            String filePath = WorldConstant.USER_HOME;
            filePath += WorldConstant.FILE_SEPARATOR;
            filePath += fileName;
            rule.setTarFile(filePath);
            DataRange range = new DataRange();
            rule.setDataRange(range);
            //"资产名称", "资产型号", "标签号", "资产状态","地点代码","地点名称","责任人","员工号","责任部门","使用人"
            Map fieldMap = new HashMap();
            fieldMap.put("ITEM_NAME", "资产名称");
            fieldMap.put("ITEM_SPEC", "资产型号");
            fieldMap.put("BARCODE", "标签号");
            fieldMap.put("ITEM_STATUS", "资产状态");
            fieldMap.put("WORKORDER_OBJECT_CODE", "地点代码");
            fieldMap.put("WORKORDER_OBJECT_NAME", "地点名称");
            fieldMap.put("RESPONSIBILITY_USER_NAME", "责任人");
            fieldMap.put("EMPLOYEE_NUMBER", "员工号");
            fieldMap.put("DEPT_NAME", "责任部门");
            fieldMap.put("MAINTAIN_USER_NAME", "使用人");
            rule.setFieldMap(fieldMap);

            CustomTransData custData = new CustomTransData();
            custData.setReportTitle("送修未返还资产");
            custData.setReportPerson(sfUser.getUsername());
            custData.setNeedReportDate(true);
            rule.setCustData(custData);
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
    
}
