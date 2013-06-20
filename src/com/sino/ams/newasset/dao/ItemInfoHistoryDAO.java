package com.sino.ams.newasset.dao;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.dto.ItemInfoHistoryDTO;
import com.sino.ams.newasset.model.ItemInfoHistoryModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.db.datatrans.*;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataTransException;
import com.sino.base.util.StrUtil;
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


public class ItemInfoHistoryDAO extends AMSBaseDAO {

	/**
	 * 功能：设备地点变动历史表(EAM) AMS_ITEM_INFO_HISTORY 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter ItemInfoHistoryDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public ItemInfoHistoryDAO(SfUserDTO userAccount, ItemInfoHistoryDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		sqlProducer = new ItemInfoHistoryModel(userAccount, dtoParameter);
	}

    public File getExportFile(String excelType) throws DataTransException {
		ItemInfoHistoryModel modelProducer = (ItemInfoHistoryModel) sqlProducer;
		SQLModel sqlModel = modelProducer.getPageQueryModel();
		String reportTitle = "设备变动历史导出";
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
		fieldMap.put("LOG_USER", "变动人");
		fieldMap.put("ORDER_NO", "单据号");
		fieldMap.put("ITEM_CATEGORY_NAME", "设备专业");
		
		fieldMap.put("ITEM_NAME", "设备名称");
		fieldMap.put("ITEM_SPEC", "设备型号");
		fieldMap.put("LOG_NET_ELE", "逻辑网络元素");
		fieldMap.put("INVEST_CAT_NAME", "投资分类");
		fieldMap.put("OPE_NAME", "业务平台");
		
		fieldMap.put("LNE_NAME", "网络层次");
		fieldMap.put("WORKORDER_OBJECT_NAME", "地点名称");
		fieldMap.put("WORKORDER_OBJECT_CODE", "地点代码");
        fieldMap.put("DEPT_NAME", "责任部门");
        fieldMap.put("USER_NAME", "责任人");
        
        fieldMap.put("EMPLOYEE_NUMBER", "责任人编号");
        fieldMap.put("MAINTAIN_USER", "使用人");
		fieldMap.put("OLD_CONTENT_CODE", "旧目录代码");
		fieldMap.put("CONTENT_CODE", "目录代码");
		fieldMap.put("CONTENT_NAME", "目录描述");
		
		fieldMap.put("ITEM_STATUS_NAME", "设备状态");
		
        return fieldMap;
	}
}
