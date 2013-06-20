package com.sino.ams.newasset.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.sino.base.constant.WorldConstant;
import com.sino.base.db.datatrans.*;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;
import com.sino.base.util.CalendarUtil;
import com.sino.base.util.ReflectionUtil;
import com.sino.base.util.StrUtil;
import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.newasset.dto.AmsItemCorrectLogDTO;
import com.sino.ams.newasset.dto.AmsItemInfoHistoryDTO;
import com.sino.ams.newasset.model.ItemMaintainModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.security.dto.ServletConfigDTO;

/**
 * <p>Title: SinoAMS</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class ItemMaintainDAO extends AMSBaseDAO {
    private AmsItemCorrectLogDAO logDAO = null;
    private SimpleQuery simp = null;

    public ItemMaintainDAO(SfUserDTO userAccount, AmsAssetsAddressVDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        logDAO = new AmsItemCorrectLogDAO(userAccount, null, conn);
    }

    /**
     * 功能：SQL生成器baseSQLProducer的初始化。具体的DAO继承时初始化具体的SQL生成器。
     * @param userAccount  BaseUserDTO
     * @param dtoParameter DTO
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
        sqlProducer = new ItemMaintainModel((SfUserDTO) userAccount, dto);
    }

    public boolean updateItems(String[] barcodeNos) {
        boolean operateResult = false;
        boolean autoCommit = true;
        String barcodes = "";
        try {
            AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            String currTime = CalendarUtil.getCurrCalendar(CAL_PATT_45);
            String remark = userAccount.getUsername()
                    + "在"
                    + currTime
                    + "通过实物台帐维护更改";
            for (int i = 0; i < barcodeNos.length; i++) {
                barcodes += barcodeNos[i] + ",";
                dto.setBarcode(barcodeNos[i]);
                dto.setRemark(remark);
                setDTOParameter(dto);
                logDAO.setDTOParameter(getLogDTO());
                updateData();
                logDAO.createData();
            }
            operateResult = true;
        } catch (DataHandleException ex) {
            ex.printLog();
        } catch (SQLException ex) {
            Logger.logError(ex);
        } catch (QueryException ex) {
            ex.printLog();
        } finally {
            try {
                if (operateResult) {
                    prodMessage(AssetsMessageKeys.ITEM_UPDATE_SUCCESS);
                    conn.commit();
                } else {
                    prodMessage(AssetsMessageKeys.ITEM_UPDATE_FAILURE);
                    conn.rollback();
                }
                message.setIsError(!operateResult);
                conn.setAutoCommit(autoCommit);
            } catch (SQLException ex1) {
                Logger.logError(ex1);
            }
        }
        return operateResult;
    }

    /**
     * 功能：构造台帐维护日志
     * @return AmsItemCorrectLogDTO
     * @throws QueryException
     */
    private AmsItemCorrectLogDTO getLogDTO() throws QueryException {
        AmsItemCorrectLogDTO logDTO = new AmsItemCorrectLogDTO();
        try {
            ItemMaintainModel modelProducer = (ItemMaintainModel) sqlProducer;
            SQLModel sqlModel = modelProducer.getPrimaryKeyDataModel();
            if (simp == null) {
                simp = new SimpleQuery(sqlModel, conn);
                simp.setDTOClassName(AmsAssetsAddressVDTO.class.getName());
                simp.setCalPattern(LINE_PATTERN);
            } else {
                simp.setSql(sqlModel);
            }
            simp.executeQuery();
            if (simp.hasResult()) {
                AmsAssetsAddressVDTO oldDTO = (AmsAssetsAddressVDTO) simp.getFirstDTO();
                AmsAssetsAddressVDTO newDTO = (AmsAssetsAddressVDTO) dtoParameter;
                logDTO = new AmsItemCorrectLogDTO();
                logDTO.setBarcode(newDTO.getBarcode());
                StringBuffer correctContent = new StringBuffer();
                String[] fieldNames = {"ITEM_CODE", "ITEM_CATEGORY", "ITEM_CATEGORY_NAME", "ITEM_NAME",
                        "ITEM_SPEC", "ADDRESS_ID", "WORKORDER_OBJECT_CODE", "WORKORDER_OBJECT_NAME",
                        "RESPONSIBILITY_USER", "EMPLOYEE_NUMBER", "RESPONSIBILITY_USER_NAME", "RESPONSIBILITY_DEPT",
                        "RESPONSIBILITY_DEPT_NAME", "MAINTAIN_USER", "MAINTAIN_DEPT_NAME", "START_DATE"};
                String[] fieldDescs = {"设备分类代码", "设备专业代码", "设备专业名称", "设备名称",
                        "设备型号", "地点ID", "地点代码", "地点名称",
                        "责任人员工ID", "责任人员工编号", "责任人姓名", "责任部门代码",
                        "责任部门名称", "使用人", "使用部门", "启用日期"};
                String fieldName = "";
                String javaField = "";
                String oldValue = "";
                String newValue = "";
                for (int i = 0; i < fieldNames.length; i++) {
                    fieldName = fieldNames[i];
                    javaField = StrUtil.getJavaField(fieldName);
                    oldValue = String.valueOf(ReflectionUtil.getProperty(oldDTO, javaField));
                    if (oldValue.equals("")) {
                        oldValue = "无";
                    }
                    newValue = String.valueOf(ReflectionUtil.getProperty(newDTO, javaField));
                    if (!oldValue.equals(newValue) && !newValue.equals("")) {
                        correctContent.append(fieldDescs[i]);
                        correctContent.append(":");
                        correctContent.append(oldValue);
                        correctContent.append("-->>");
                        correctContent.append(newValue);
                        correctContent.append(WorldConstant.ENTER_CHAR);
                    }
                }
                logDTO.setCorrectContent(correctContent.toString());
            }
        } catch (ReflectException ex) {
            ex.printLog();
            throw new QueryException(ex);
        }
        return logDTO;
    }

    public File getExportFile(ServletConfigDTO servletConfig,String excelType) throws DataTransException, SQLModelException {
        ItemMaintainModel modelProducer = (ItemMaintainModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getPageQueryModel();
        String reportTitle = "实物台帐维护";
        if (!StrUtil.isNotEmpty(excelType)) {
			excelType = "xls";
		}
		String fileName = reportTitle + "." + excelType;
        TransRule rule = new TransRule();
        rule.setDataSource(sqlModel);
        rule.setSourceConn(conn);
        String filePath = WorldConstant.USER_HOME;
        filePath += WorldConstant.FILE_SEPARATOR;
        filePath += fileName;
        rule.setTarFile(filePath);
        DataRange range = new DataRange();
        rule.setDataRange(range);
        rule.setFieldMap(getFieldMap(servletConfig));
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

    private Map getFieldMap(ServletConfigDTO servletConfig) {
        Map fieldMap = new HashMap();
        fieldMap.put("BARCODE", "标签号");
        fieldMap.put("ITEM_CATEGORY_NAME", "设备专业");
        fieldMap.put("ITEM_NAME", "设备名称");
        fieldMap.put("ITEM_SPEC", "规格型号");
        fieldMap.put("ITEM_UNIT", "计量单位");
        fieldMap.put("ACTUAL_QTY", "实际数量");
        fieldMap.put("FINANCE_PROP_VALUE", "资产种类");
        fieldMap.put("ITEM_STATUS_NAME", "设备状态");
        fieldMap.put("WORKORDER_OBJECT_CODE", "地点编号");
        fieldMap.put("WORKORDER_OBJECT_NAME", "地点名称");
//        fieldMap.put("CITY", "行政市");
//        fieldMap.put("COUNTY", "行政县");
//        fieldMap.put("AREA_TYPE_NAME", "行政区划");
        fieldMap.put("RESPONSIBILITY_USER_NAME", "责任人");
        fieldMap.put("EMPLOYEE_NUMBER", "员工编号");
        fieldMap.put("DEPT_CODE", "责任部门代码");
        fieldMap.put("RESPONSIBILITY_DEPT_NAME", "责任部门");
        fieldMap.put("MAINTAIN_USER", "使用人");
        fieldMap.put("MAINTAIN_DEPT_NAME", "使用部门");
        fieldMap.put("SPECIALITY_DEPT_NAME", "实物部门");
        fieldMap.put("PROJECT_NAME", "项目名称");
        fieldMap.put("PROJECT_NUMBER", "项目编号");
        fieldMap.put("MANUFACTURER_NAME", "厂商名称");
        fieldMap.put("CONTENT_CODE", "资产目录代码");
        fieldMap.put("CONTENT_NAME", "资产目录描述");
        fieldMap.put("IS_SHARE", "是否共享");
        fieldMap.put("CONSTRUCT_STATUS", "是否共建");
        fieldMap.put("LOG_NET_ELE", "逻辑网络元素");
        fieldMap.put("INVEST_CAT_NAME", "投资分类");
        fieldMap.put("OPE_NAME", "业务平台");
        fieldMap.put("LNE_NAME", "网络层次");
        fieldMap.put("LIFE_IN_YEARS", "资产使用年限");
        fieldMap.put("DATE_PLACED_IN_SERVICE", "资产启用日期");
        fieldMap.put("ASSETS_CREATE_DATE", "资产创建日期");
        fieldMap.put("ORIGINAL_COST", "资产原值");
        fieldMap.put("SCRAP_VALUE", "资产残值");
        fieldMap.put("IMPAIR_RESERVE", "资产减值准备");
        fieldMap.put("DEPRN_RESERVE", "资产累计折旧");
        fieldMap.put("DEPRN_COST", "资产净额");
        fieldMap.put("SEGMENT2", "资产类别代码");
        fieldMap.put("FA_CATEGORY2", "资产类别");
        fieldMap.put("REMARK", "备注");
        fieldMap.put("REMARK1", "备注一");
        fieldMap.put("REMARK2", "备注二");
        return fieldMap;
    }

    public int checkItemStatus(String[] barcodes, String itemStatus) throws QueryException, SQLModelException {
        int count = 0;
        ItemMaintainModel modelProducer = (ItemMaintainModel) sqlProducer;
        for (int i=0; i<barcodes.length; i++) {
            String barcode = barcodes[i];
            SQLModel sqlModel = modelProducer.getCheckItemStatusModel(barcode, itemStatus);
            SimpleQuery simp = new SimpleQuery(sqlModel,conn);
            simp.executeQuery();
            if (simp.hasResult()) {
                count++;
            }
        }
        return count;
    }

    public void logItemChgHistory(String [] barcodes){
        AmsItemInfoHistoryDAO historyDAO = new AmsItemInfoHistoryDAO(userAccount,null,conn);
        for (int i = 0; i < barcodes.length; i++) {
            String barcode = barcodes[i];

            AmsItemInfoHistoryDTO historyDTO=new AmsItemInfoHistoryDTO();
            historyDTO.setOrderCategory("3");
            historyDTO.setOrderNo("实物台账维护");
            historyDTO.setCreatedBy(userAccount.getUserId());
            historyDTO.setBarcode(barcode);

            historyDAO.setDTOParameter(historyDTO);
            historyDAO.recordHistory();
        }


    }
}
