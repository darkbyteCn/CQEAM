package com.sino.ams.newasset.report.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.dto.AmsAssetsCheckHeaderDTO;
import com.sino.ams.newasset.report.model.NewAssetsReportModel;
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

public class NewAssetsReportDAO extends AMSBaseDAO {

	public NewAssetsReportDAO(SfUserDTO userAccount, AmsAssetsCheckHeaderDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器baseSQLProducer的初始化。具体的DAO继承时初始化具体的SQL生成器。
	 * @param userAccount BaseUserDTO
	 * @param dtoParameter DTO
	 * @todo Implement this com.sino.framework.dao.BaseDAO method
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		SfUserDTO user = (SfUserDTO)userAccount;
		AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
		sqlProducer = new NewAssetsReportModel(user, dto);
	}



	/**
	 * 功能：获取部门盘点设备明细Excel文件
	 * @return File
	 * @throws DataTransException
	 */
	public File getExportFile() throws DataTransException {
		File file = null;
		try {
			NewAssetsReportModel modelProducer = (NewAssetsReportModel)sqlProducer;
			SQLModel sqlModel = modelProducer.getPageQueryModel();
			String reportTitle = "新增资产";
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
		fieldMap.put("TAG_NUMBER", "资产标签");
		fieldMap.put("ASSET_NUMBER", "资产编号");
		fieldMap.put("FA_CATEGORY1", "应用领域");
		fieldMap.put("FA_CATEGORY2", "资产类别");
		fieldMap.put("ASSETS_DESCRIPTION", "资产名称");
		fieldMap.put("MODEL_NUMBER", "资产型号");
		fieldMap.put("UNIT_OF_MEASURE", "计量单位");
		fieldMap.put("CURRENT_UNITS", "数量");
		fieldMap.put("ASSETS_CREATE_DATE", "资产创建日期");
		fieldMap.put("DATE_PLACED_IN_SERVICE", "启用日期");
		fieldMap.put("LIFE_IN_YEARS", "使用年限");
		fieldMap.put("ORIGINAL_COST", "原始成本");
		fieldMap.put("COST", "当前成本");
		fieldMap.put("DEPRN_COST", "资产净值");
		fieldMap.put("IMPAIR_RESERVE", "减值准备累计");
		fieldMap.put("SCRAP_VALUE", "资产残值");
		fieldMap.put("DEPRECIATION_ACCOUNT", "折旧费用账户");
		fieldMap.put("ASSIGNED_TO_NAME", "责任人");
		fieldMap.put("ASSIGNED_TO_NUMBER", "员工编号");
		fieldMap.put("ASSETS_LOCATION", "地点名称");
		fieldMap.put("ASSETS_LOCATION_CODE", "地点代码");
		fieldMap.put("PROJECT_NAME", "项目名称");
		fieldMap.put("MIS_PROJECT_NUMBER", "项目编号");
		return fieldMap;
	}
}
