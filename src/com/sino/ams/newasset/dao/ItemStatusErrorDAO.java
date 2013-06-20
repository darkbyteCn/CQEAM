package com.sino.ams.newasset.dao;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.model.ItemStatusErrorModel;
import com.sino.ams.synchronize.dto.EamSyschronizeDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.datatrans.CustomTransData;
import com.sino.base.db.datatrans.DataRange;
import com.sino.base.db.datatrans.DataTransfer;
import com.sino.base.db.datatrans.TransRule;
import com.sino.base.db.datatrans.TransferFactory;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.DataTransException;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2009-8-20
 * Time: 13:23:57
 * To change this template use File | Settings | File Templates.
 */
public class ItemStatusErrorDAO extends AMSBaseDAO {

    public ItemStatusErrorDAO(SfUserDTO userAccount, EamSyschronizeDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        EamSyschronizeDTO dtoPara = (EamSyschronizeDTO) dtoParameter;
        super.sqlProducer = new ItemStatusErrorModel((SfUserDTO) userAccount, dtoPara);
    }

    public void syschronizeAssets(String[] barcodes) {
        boolean operateResult = false;
        boolean autoCommit = true;
        CallableStatement cs = null;
        try {
            int assetsCount = barcodes.length;
            for (int i = 0; i < assetsCount; i++) {
                String barcode = barcode = barcodes[i];
                changeItemStatus(barcode);
            }
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            operateResult = true;
        } catch (SQLException e) {
            Logger.logError(e);
        } catch (DataHandleException ex) {
			ex.printLog();
		} finally {
            try {
                if (operateResult) {
                    conn.commit();
                    prodMessage("ITEM_UPDATE_SUCCESS");
                } else {
                    conn.rollback();
                    prodMessage("ITEM_UPDATE_FAILURE");
                    message.setIsError(true);
                }
                conn.setAutoCommit(autoCommit);
                DBManager.closeDBStatement(cs);
            } catch (SQLException ex) {
                Logger.logError(ex);
            }
        }
    }

    public File getExportFile() throws DataTransException {
        ItemStatusErrorModel modelProducer = (ItemStatusErrorModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getPageQueryModel();
        String reportTitle = "MIS报废资产与EAM设备状态不一致情况";
        String fileName = reportTitle + ".xls";
        TransRule rule = new TransRule();
        rule.setDataSource(sqlModel);
        rule.setSourceConn(conn);
        rule.setCalPattern(LINE_PATTERN);
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
        TransferFactory factory = new TransferFactory();
        DataTransfer transfer = factory.getTransfer(rule);
        transfer.transData();
        return (File) transfer.getTransResult();
    }

    private Map getFieldMap() {
        Map fieldMap = new HashMap();
        fieldMap.put("COMPANY", "公司");
        fieldMap.put("BARCODE", "设备条码");
        fieldMap.put("ITEM_NAME", "设备名称");
        fieldMap.put("ITEM_SPEC", "规格型号");
        fieldMap.put("WORKORDER_OBJECT_NAME", "地点");
        fieldMap.put("DEPT_NAME", "责任部门");
        fieldMap.put("USER_NAME", "责任人");
        fieldMap.put("ITEM_STATUS_NAME", "设备状态");
        return fieldMap;
    }

    private void changeItemStatus(String barcode) throws DataHandleException {
		ItemStatusErrorModel modelProducer = (ItemStatusErrorModel) sqlProducer;
		SQLModel sqlModel = modelProducer.changeItemStatus(barcode);
		DBOperator.updateRecord(sqlModel, conn);
	}
}