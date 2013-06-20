package com.sino.ams.newasset.report.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.dto.AmsAssetsCheckHeaderDTO;
import com.sino.ams.newasset.report.model.NewAssetsMonitorTotalScanedModel;
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

public class NewAssetsMonitorTotalScanedDAO extends AMSBaseDAO {

	public NewAssetsMonitorTotalScanedDAO(SfUserDTO userAccount, AmsAssetsCheckHeaderDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器baseSQLProducer的初始化。具体的DAO继承时初始化具体的SQL生成器。
	 * @param userAccount BaseUserDTO
	 * @param dtoParameter DTO
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		SfUserDTO user = (SfUserDTO)userAccount;
		AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
		sqlProducer = new NewAssetsMonitorTotalScanedModel(user, dto);
	}


	/**
	 * 功能：获取部门盘点设备明细Excel文件
	 * @return File
	 * @throws com.sino.base.exception.DataTransException
	 */
	public File getExportFile() throws DataTransException {
		File file = null;
		try {
			NewAssetsMonitorTotalScanedModel modelProducer = (NewAssetsMonitorTotalScanedModel)sqlProducer;
			SQLModel sqlModel = modelProducer.getPageQueryModel();
			String reportTitle = "期间实际扫描资产";
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
	 * 功能：获取导出数据字段映射
	 * @return Map
	 */
	private Map getFieldMap(){
		Map fieldMap = new HashMap();

		fieldMap.put("COMPANY", "公司名称");
		fieldMap.put("BARCODE", "资产标签");
//		fieldMap.put("ITEM_CATEGORY", "设备分类");
		fieldMap.put("AMS_ITEM_NAME", "EAM名称");
		fieldMap.put("AMS_ITEM_SPEC", "EAM型号");
//		fieldMap.put("AMS_YEARS", "EAM使用年限");
//		fieldMap.put("MIS_YEARS", "MIS使用年限");
		fieldMap.put("AMS_START_DATE", "EAM启用日期");
//		fieldMap.put("ORIGINAL_COST", "原始成本");
//		fieldMap.put("COST", "当前成本");
//		fieldMap.put("DEPRN_COST", "资产净值");
//		fieldMap.put("IMPAIR_RESERVE", "减值准备累计");
//		fieldMap.put("SCRAP_VALUE", "资产残值");
//		fieldMap.put("DEPRECIATION_ACCOUNT", "折旧费用账户");
//		fieldMap.put("DEPT_NAME", "EAM责任部门");
		fieldMap.put("AMS_LOCATION_CODE", "EAM地点代码");
		fieldMap.put("AMS_LOCATION", "EAM地点名称");
		fieldMap.put("AMS_EMPLOYEE_NUMBER", "EAM员工编号");
		fieldMap.put("AMS_USER_NAME", "EAM责任人");
		fieldMap.put("AMS_PROJECT_NAME", "EAM项目名称");
		fieldMap.put("AMS_PROJECT_NUMBER", "EAM项目编号");
		return fieldMap;
	}
}