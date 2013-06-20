package com.sino.ams.newasset.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.newasset.dto.AmsItemCorrectLogDTO;
import com.sino.ams.newasset.model.ItemMaintainModel2;
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
import com.sino.base.dto.DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.ReflectException;
import com.sino.base.log.Logger;
import com.sino.base.util.CalendarUtil;
import com.sino.base.util.ReflectionUtil;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;

/**
 *
 * <p>Title: SinoEAM</p>
 * <p>Description: 陕西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author suhp
 */
public class ItemMaintainDAO2 extends AMSBaseDAO {
    private AmsItemCorrectLogDAO logDAO = null;
    private SimpleQuery simp = null;

    public ItemMaintainDAO2(SfUserDTO userAccount, AmsAssetsAddressVDTO dtoParameter,Connection conn) {
        super(userAccount, dtoParameter, conn);
        logDAO = new AmsItemCorrectLogDAO(userAccount, null, conn);
    }

    /**
     * 功能：SQL生成器baseSQLProducer的初始化。具体的DAO继承时初始化具体的SQL生成器。
     * @param userAccount BaseUserDTO
     * @param dtoParameter DTO
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO)dtoParameter;
        sqlProducer = new ItemMaintainModel2((SfUserDTO)userAccount, dto);
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
                            + "通过设备台账维护更改";
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
        } finally{
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
     * 功能：构造台账维护日志
     * @return AmsItemCorrectLogDTO
     * @throws QueryException
     */
    private AmsItemCorrectLogDTO getLogDTO() throws QueryException{
        AmsItemCorrectLogDTO logDTO = null;
        try {
            ItemMaintainModel2 modelProducer = (ItemMaintainModel2) sqlProducer;
            SQLModel sqlModel = modelProducer.getPrimaryKeyDataModel();
            if (simp == null) {
                simp = new SimpleQuery(sqlModel, conn);
                simp.setDTOClassName(AmsAssetsAddressVDTO.class.getName());
            } else {
                simp.setSql(sqlModel);
            }
            simp.executeQuery();
            if (simp.hasResult()) {
                AmsAssetsAddressVDTO oldDTO = (AmsAssetsAddressVDTO) simp.getFirstDTO();
                AmsAssetsAddressVDTO newDTO = (AmsAssetsAddressVDTO)dtoParameter;
                logDTO = new AmsItemCorrectLogDTO();
                logDTO.setBarcode(newDTO.getBarcode());
                String correctContent = "";
                String[] fieldNames = {"ITEM_CODE", "ITEM_NAME", "ITEM_SPEC", "WORKORDER_OBJECT_CODE",
                                      "WORKORDER_OBJECT_NAME", "RESPONSIBILITY_USER_NAME","RESPONSIBILITY_DEPT_NAME",
                                      "MAINTAIN_USER", "MAINTAIN_DEPT_NAME"};
                String[] fieldDescs = {"设备分类代码", "设备名称", "设备型号", "地点代码", "地点名称",
                                      "责任人","责任部门",
                                      "使用人", "使用部门"};
                String fieldName = "";
                String javaField = "";
                String oldValue = "";
                String newValue = "";
                for (int i = 0; i < fieldNames.length; i++) {
                    fieldName = fieldNames[i];
                    javaField = StrUtil.getJavaField(fieldName);
                    oldValue = String.valueOf(ReflectionUtil.getProperty(oldDTO, javaField));
                    if(oldValue.equals("")){
                        oldValue = "无";
                    }
                    newValue = String.valueOf(ReflectionUtil.getProperty(newDTO, javaField));
                    newDTO.getMaintainDept();
                    if (!oldValue.equals(newValue) && !newValue.equals("")) {
                        correctContent += fieldDescs[i]
                            + ":"
                            + oldValue
                            + "-->>"
                            + newValue;
                        correctContent += WorldConstant.ENTER_CHAR;
                    }
                }
                logDTO.setCorrectContent(correctContent);
            }
        } catch (ReflectException ex) {
            ex.printLog();
            throw new QueryException(ex);
        }
        return logDTO;
    }

    public File getExportFile() throws DataTransException {
        ItemMaintainModel2 modelProducer = (ItemMaintainModel2) sqlProducer;
        SQLModel sqlModel = modelProducer.getPageQueryModel();
        String reportTitle = "台账查询导出设备";
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
        fieldMap.put("BARCODE", "标签号");
        fieldMap.put("ITEM_CATEGORY_NAME", "设备分类");
        fieldMap.put("ITEM_NAME", "设备名称");
        fieldMap.put("ITEM_SPEC", "设备型号");
        fieldMap.put("ITEM_UNIT", "计量单位");
        fieldMap.put("YEARS", "使用年限");
        fieldMap.put("START_DATE", "启用日期");
        fieldMap.put("ITEM_STATUS_NAME", "设备状态");
        fieldMap.put("FINANCE_PROP_NAME", "资产种类");
        fieldMap.put("WORKORDER_OBJECT_CODE", "地点代码");
        fieldMap.put("WORKORDER_OBJECT_NAME", "地点简称");
        fieldMap.put("WORKORDER_OBJECT_LOCATION", "地点位置");
        fieldMap.put("COUNTY_NAME", "所在区县");
        fieldMap.put("RESPONSIBILITY_USER_NAME", "责任人姓名");
        fieldMap.put("EMPLOYEE_NUMBER", "责任人员工号");
        fieldMap.put("RESPONSIBILITY_DEPT_NAME", "责任部门");
        fieldMap.put("MAINTAIN_USER", "使用人");
        fieldMap.put("MAINTAIN_DEPT_NAME", "使用部门");
        fieldMap.put("PROJECT_NAME", "项目名称");
        fieldMap.put("PROJECT_NUMBER", "项目编号");
        fieldMap.put("MANUFACTURER_NAME", "厂商名称");
        fieldMap.put("MANUFACTURER_CODE", "厂商代码");
        fieldMap.put("CONTENT_NAME", "资产目录描述");
		fieldMap.put("CONTENT_CODE", "资产目录代码");
        fieldMap.put("SHARE_STATUS_NAME", "共享共建状态");
		fieldMap.put("POWER", "额定功率");
        fieldMap.put("LOG_NET_ELE", "逻辑网络元素");
        fieldMap.put("INVEST_CAT_NAME", "投资分类");
        fieldMap.put("OPE_NAME", "业务平台");
        fieldMap.put("LNE_NAME", "网络层次");     
        return fieldMap;
    }

    public Map getFincePropCount (AmsAssetsAddressVDTO dto) throws QueryException, ContainerException{
        Map map = new HashMap();
        ItemMaintainModel2 modelProducer = (ItemMaintainModel2) sqlProducer;
        SQLModel sqlModel = modelProducer.getFincePropCount(dto);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        RowSet rs = simpleQuery.getSearchResult();
        for (int i = 0; i< rs.getSize(); i++) {
            Row row = rs.getRow(i);
            map.put(row.getValue("CODE"),row.getValue("CNT"));
        }
        return map;
    }
}
