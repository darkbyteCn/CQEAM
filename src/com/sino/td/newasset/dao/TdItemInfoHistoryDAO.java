package com.sino.td.newasset.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
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
import com.sino.framework.dto.BaseUserDTO;
import com.sino.td.newasset.dto.TdItemInfoHistoryDTO;
import com.sino.td.newasset.model.TdItemInfoHistoryModel;


/**
 * <p>Title: AmsItemInfoHistoryDAO</p>
 * <p>Description:程序自动生成服务程序“AmsItemInfoHistoryDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */


public class TdItemInfoHistoryDAO extends AMSBaseDAO {

	/**
	 * 功能：设备地点变动历史表(EAM) AMS_ITEM_INFO_HISTORY 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter TdItemInfoHistoryDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public TdItemInfoHistoryDAO(SfUserDTO userAccount, TdItemInfoHistoryDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		TdItemInfoHistoryDTO dto = (TdItemInfoHistoryDTO) dtoParameter;
		SfUserDTO user = (SfUserDTO) userAccount;
		sqlProducer = new TdItemInfoHistoryModel(user, dto);
	}

    public File getExportFile(String barcode) throws DataTransException {
		TdItemInfoHistoryModel modelProducer = (TdItemInfoHistoryModel) sqlProducer;
		SQLModel sqlModel = modelProducer.getDataByBarcodeModel(barcode);
		String reportTitle = "设备变动历史导出";
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
		fieldMap.put("CREATION_DATE", "变动时间");
		fieldMap.put("ORDER_NO", "变动单据");
		fieldMap.put("ITEM_CATEGORY_NAME", "设备专业");
		fieldMap.put("ITEM_NAME", "设备名称");
		fieldMap.put("ITEM_SPEC", "设备型号");
		fieldMap.put("WORKORDER_OBJECT_NAME", "所在地点");
		fieldMap.put("ADDRESS_NO", "组合地址");
		fieldMap.put("RESPONSIBILITY_USER_NAME", "责任人");
		fieldMap.put("RESPONSIBILITY_DEPT_NAME", "责任部门");
        return fieldMap;
	}
}
