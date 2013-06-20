package com.sino.ams.system.manydimensions.dao;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.dao.AmsItemInfoHistoryDAO;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.newasset.dto.AmsItemInfoHistoryDTO;
import com.sino.ams.system.fixing.dto.EtsItemInfoDTO;
import com.sino.ams.system.manydimensions.model.ManyDimensionsModel1;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.db.datatrans.*;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-8-18
 * Time: 11:28:56
 * To change this template use File | Settings | File Templates.
 */
public class ManyDimensionsDAO1 extends AMSBaseDAO {

    public ManyDimensionsDAO1(SfUserDTO userAccount, AmsAssetsAddressVDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        SfUserDTO user = (SfUserDTO) userAccount;
        AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
        sqlProducer = new ManyDimensionsModel1(user, dto);
    }

    public boolean updateItems(String[] barcodes,
                               String[] contentCodes,
                               String[] contentNames,
                               String[] lneIds,
                               String[] cexIds,
                               String[] opeIds,
                               String[] nleIds,
                               String[] oldContentCodes) {
        boolean operateResult = false;
        boolean autoCommit = true;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            updateData(barcodes, contentCodes, contentNames, lneIds, cexIds, opeIds, nleIds, oldContentCodes);
            operateResult = true;
        } catch (Throwable ex) {
            Logger.logError(ex);
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

    private void updateData(String[] barcodes,
                            String[] contentCodes,
                            String[] contentNames,
                            String[] lneIds,
                            String[] cexIds,
                            String[] opeIds,
                            String[] nleIds,
                            String[] oldContentCodes) throws DataHandleException {
        try {
            ManyDimensionsModel1 modelProducer = new ManyDimensionsModel1(userAccount, null);
            SQLModel sqlModel = null;
            AmsItemInfoHistoryDAO historyDAO = new AmsItemInfoHistoryDAO(userAccount, null, conn);
            String oldContentCode = "";
            String[] oldContentC;
            String barcode = "";
            String contentCode = "";
            String contentName = "";
            String oldConCode = "";
            String lneId = "";
            String cexId = "";
            String opeId = "";
            String nleId = "";
            DTOSet ds = null;
            boolean isChanged = false;
            //for (int i = 0; i < contentCodes.length; i++) {
            for (int i = 0; i < barcodes.length; i++) {
                //oldContentCode = contentCodes[i];
                //oldContentC = oldContentCode.split(";");
                //for (int j = 0; j < barcodes.length; j++) {
                barcode = barcodes[i];
                for (int j = 0; j < contentCodes.length; j++) {
                    oldContentCode = contentCodes[j];
                    oldContentC = oldContentCode.split(";");
	                if (barcode.equals(oldContentC[0])) {
	                    contentCode = oldContentC[1];
	                    contentName = contentNames[j];
	                    oldConCode = oldContentCodes[j];
	                    lneId = lneIds[j];
	                    cexId = cexIds[j];
	                    opeId = opeIds[j];
	                    nleId = nleIds[j];
	                    
	                    //插入修改历史表
	                    ds = getItemInfo(barcode);
	                    isChanged = false;
	                    EtsItemInfoDTO changeDTO = new EtsItemInfoDTO();
	                    if (ds.getSize() > 0) {
	                        EtsItemInfoDTO itemDTO = (EtsItemInfoDTO) ds.getDTO(0);
	                        if (!contentCode.equals(itemDTO.getContentCode())) {
	                            changeDTO.setContentCode(contentCode);
	                            changeDTO.setContentName(contentName);
	                            changeDTO.setOldContentCode(oldConCode);
	                            isChanged = true;
	                        }
	                        if (!lneId.equals(itemDTO.getLneId())) {
	                            changeDTO.setLneId(lneId);
	                            isChanged = true;
	                        }
	                        if (!cexId.equals(itemDTO.getCexId())) {
	                            changeDTO.setCexId(cexId);
	                            isChanged = true;
	                        }
	                        if (!nleId.equals(itemDTO.getNleId())) {
	                            changeDTO.setNleId(nleId);
	                            isChanged = true;
	                        }
                            if (!opeId.equals(itemDTO.getOpeId())) {
	                            changeDTO.setOpeId(opeId);
	                            isChanged = true;
	                        }
	                        if (isChanged) {
	                            //更新ETS_ITEM_INFO
	                            sqlModel = modelProducer.updateDataModel(barcode, contentCode, contentName, lneId, cexId, opeId, nleId);
	                            DBOperator.updateRecord(sqlModel, conn);
	                            
	                            changeDTO.setBarcode(barcode);
	                            insertHistory(historyDAO, changeDTO);
	                        }
	                    }
	                }
                }
            }
        } catch (QueryException ex) {
            ex.printLog();
            throw new DataHandleException(ex);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex.getMessage());
        }
    }

    private DTOSet getItemInfo(String barcode) throws QueryException {
        ManyDimensionsModel1 eoModel = (ManyDimensionsModel1) sqlProducer;
        SQLModel sqlModel = eoModel.getItemInfoModel(barcode);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.setDTOClassName(EtsItemInfoDTO.class.getName());
        sq.executeQuery();
        return sq.getDTOSet();
    }

    private void insertHistory(AmsItemInfoHistoryDAO historyDAO, EtsItemInfoDTO itemDTO) throws DataHandleException {
        AmsItemInfoHistoryDTO historyDTO = new AmsItemInfoHistoryDTO();
        historyDTO.setBarcode(itemDTO.getBarcode());
        historyDTO.setCreatedBy(userAccount.getUserId());
        historyDTO.setOrderCategory("0");//非业务单据
        historyDTO.setRemark("通过多维度维护更新");
        historyDAO.setDTOParameter(historyDTO);
        historyDAO.recordHistory();
    }

    public File getExportFile(String excelType) throws DataTransException {
        File file = null;
        try {
            ManyDimensionsModel1 modelProducer = (ManyDimensionsModel1) sqlProducer;
            SQLModel sqlModel = modelProducer.getPageQueryModel();
            String reportTitle = "多维度信息维护";
            if (!StrUtil.isNotEmpty(excelType)) {
                excelType = "xls";
            }
            String fileName = reportTitle + "." + excelType;
            String filePath = WorldConstant.USER_HOME;
            filePath += WorldConstant.FILE_SEPARATOR;
            filePath += fileName;
            TransRule rule = new TransRule();
            rule.setDataSource(sqlModel);
            rule.setSourceConn(conn);
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
            file = (File) transfer.getTransResult();
        } catch (SQLModelException ex) {
            ex.printLog();
            throw new DataTransException(ex);
        }
        return file;
    }

    private Map getFieldMap() {
        Map fieldMap = new HashMap();
        fieldMap.put("BARCODE", "标签号");
        fieldMap.put("ITEM_NAME", "资产名称");
        fieldMap.put("ITEM_SPEC", "规格型号");
        fieldMap.put("CONTENT_CODE", "资产类别");
        fieldMap.put("CONTENT_NAME", "类别描述");
        fieldMap.put("LNE_NAME", "逻辑网络元素");
        fieldMap.put("CEX_NAME", "投资分类");
        fieldMap.put("OPE_NAME", "业务平台");
        fieldMap.put("NLE_NAME", "网络层次");
        fieldMap.put("USER_NAME", "责任人");
        fieldMap.put("WORKORDER_OBJECT_CODE", "地点代码");
        fieldMap.put("WORKORDER_OBJECT_NAME", "地点描述");
        return fieldMap;
    }
}