package com.sino.nm.ams.instrument.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

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

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.nm.ams.instrument.dto.AmsInstrumentEamYbChkMaintainDTO;
import com.sino.nm.ams.instrument.model.AmsInstrumentEamYbChkHistoryModel;
import com.sino.ams.system.user.dto.SfUserDTO;

public class AmsInstrumentEamYbChkHistoryDAO extends AMSBaseDAO {

	AmsInstrumentEamYbChkHistoryModel modelProducer = null;
	
	public AmsInstrumentEamYbChkHistoryDAO(SfUserDTO userAccount, AmsInstrumentEamYbChkMaintainDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		modelProducer = (AmsInstrumentEamYbChkHistoryModel)sqlProducer;
	}

	/**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		AmsInstrumentEamYbChkMaintainDTO dtoPara = (AmsInstrumentEamYbChkMaintainDTO) dtoParameter;
		super.sqlProducer = new AmsInstrumentEamYbChkHistoryModel((SfUserDTO)userAccount, dtoPara);
	}

	public File exportFile() throws DataTransException {
		
        File file = null;
        try {
            SQLModel sqlModel = sqlProducer.getPageQueryModel();
            TransRule rule = new TransRule();
            rule.setDataSource(sqlModel);
            rule.setCalPattern(CalendarConstant.LINE_PATTERN);
            rule.setSourceConn(conn);
            String fileName = "仪器仪表检修历史信息.xls";
            String filePath = WorldConstant.USER_HOME;
            filePath += WorldConstant.FILE_SEPARATOR;
            filePath += fileName;
            rule.setTarFile(filePath);
            DataRange range = new DataRange();
            rule.setDataRange(range);
            
            Map fieldMap = new HashMap();
            fieldMap.put("BARCODE", "条码编号");
            fieldMap.put("ITEM_NAME", "品名");
            fieldMap.put("ITEM_SPEC", "规格型号");
            fieldMap.put("CHECK_STATUS_VALUE", "检修结果");
            fieldMap.put("REMARK", "备注");
            fieldMap.put("CHECK_USER_NAME", "检修人");
            fieldMap.put("CHECK_DATE", "检修日期");

            rule.setFieldMap(fieldMap);
            
            CustomTransData custData = new CustomTransData();
            custData.setReportTitle("仪器仪表检修历史信息");
            custData.setReportPerson(userAccount.getUsername());
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
