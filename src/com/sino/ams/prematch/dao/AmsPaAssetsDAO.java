package com.sino.ams.prematch.dao;


import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.prematch.dto.AmsPaAssetsDTO;
import com.sino.ams.prematch.model.AmsPaAssetsModel;
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
 * <p>Title: AmsPaAssetsDAO</p>
 * <p>Description:程序自动生成服务程序“AmsPaAssetsDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class AmsPaAssetsDAO extends AMSBaseDAO {

	/**
	 * 功能：MIS转资准备清单 AMS_PA_ASSETS 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsPaAssetsDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public AmsPaAssetsDAO(SfUserDTO userAccount, AmsPaAssetsDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) {
		AmsPaAssetsDTO dto = (AmsPaAssetsDTO)dtoParameter;
		super.sqlProducer = new AmsPaAssetsModel((SfUserDTO)userAccount, dto);
	}

	/**
	 * 功能：导出转资准备清单到Excel文件
	 * @return File
	 * @throws DataTransException
	 */
	public File getExportFile() throws DataTransException {
		File file = null;
		try {
			AmsPaAssetsModel modelProducer = (AmsPaAssetsModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getPageQueryModel();
			String reportTitle = "MIS转资准备清单";
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

	/**
	 * 功能：获取转资准备清单导出字段映射
	 * @return Map
	 */
	private Map getFieldMap(){
		Map fieldMap = new HashMap();
		fieldMap.put("TAG_NUMBER", "标签号");
		fieldMap.put("ASSETS_DESCRIPTION", "资产名称");
		fieldMap.put("MODEL_NUMBER", "规格型号");
		fieldMap.put("PROJECT_NUMBER", "项目编号");
		fieldMap.put("PROJECT_NAME", "项目名称");
		fieldMap.put("ASSETS_LOCATION_CODE", "地点代码");
		fieldMap.put("ASSETS_LOCATION", "地点名称");
		fieldMap.put("ASSIGNED_TO_NUMBER", "员工编号");
		fieldMap.put("ASSIGNED_TO_NAME", "责任人");
		fieldMap.put("DATE_PLACED_IN_SERVICE", "启用日期");
		fieldMap.put("ASSET_UNITS", "资产数量");
		fieldMap.put("TASK_NAME", "任务名称");
		fieldMap.put("FA_CATEGORY_CODE", "资产类别");
		return fieldMap;
	}

	public void readPaAssets(){
		AmsPaAssetsDTO dto = (AmsPaAssetsDTO)dtoParameter;

	}
}
