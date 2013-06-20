package com.sino.ams.newasset.dao;


import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.dto.ObjectAssetsDTO;
import com.sino.ams.newasset.model.ObjectAssetsModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.db.datatrans.*;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataTransException;
import com.sino.framework.dto.BaseUserDTO;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;


/**
 * <p>Title: AmsItemInfoHistoryDAO</p>
 * <p>Description:程序自动生成服务程序“AmsItemInfoHistoryDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */


public class ObjectAssetsDAO extends AMSBaseDAO {

	/**
	 * 功能：设备地点变动历史表(EAM) AMS_ITEM_INFO_HISTORY 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter ObjectAssetsDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public ObjectAssetsDAO(SfUserDTO userAccount, ObjectAssetsDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		sqlProducer = new ObjectAssetsModel(userAccount, dtoParameter);
	}

    public File getExportFile() throws DataTransException {
		ObjectAssetsModel modelProducer = (ObjectAssetsModel) sqlProducer;
		SQLModel sqlModel = modelProducer.getPageQueryModel();
		String reportTitle = "设备地点列表";
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
		fieldMap.put("ITEM_CATEGORY_NAME", "设备专业");
		fieldMap.put("ITEM_NAME", "设备名称");
		fieldMap.put("ITEM_SPEC", "设备型号");
        fieldMap.put("DEPT_NAME", "责任部门");
        fieldMap.put("USER_NAME", "责任人");
		fieldMap.put("WORKORDER_OBJECT_CODE", "地点编码");
		fieldMap.put("WORKORDER_OBJECT_NAME", "所在地点");
        return fieldMap;
	}
}
