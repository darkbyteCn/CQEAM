package com.sino.ams.newasset.report.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.dto.AmsAssetsCheckHeaderDTO;
import com.sino.ams.newasset.report.model.CostDiffDtlReportModel;
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

public class CostDiffDtlReportDAO extends AMSBaseDAO {

	public CostDiffDtlReportDAO(SfUserDTO userAccount, AmsAssetsCheckHeaderDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器baseSQLProducer的初始化。具体的DAO继承时初始化具体的SQL生成器。
	 *
	 * @param userAccount BaseUserDTO
	 * @param dtoParameter DTO
	 * @todo Implement this com.sino.framework.dao.BaseDAO method
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		SfUserDTO user = (SfUserDTO)userAccount;
		AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO)dtoParameter;
		sqlProducer = new CostDiffDtlReportModel(user, dto);
	}

	/**
	 * 功能：获取部门盘点设备明细Excel文件
	 * @return File
	 * @throws DataTransException
	 */
	public File getExportFile() throws DataTransException {
		File file = null;
		try {
			CostDiffDtlReportModel modelProducer = (CostDiffDtlReportModel) sqlProducer;
			AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO)dtoParameter;
			String exportType = dto.getExportType();
			String reportTitle = "";
			SQLModel sqlModel = null;
			if(exportType.equals(DictConstant.OWN_ITEM)){
				sqlModel = modelProducer.getOwnAssetsModel();
				reportTitle = "成本中心"
							  + dto.getCostName()
							  + "现有资产";
			} else if(exportType.equals(DictConstant.SCAN_ITEM_Y)){
				sqlModel = modelProducer.getScanedItemsModel();
				reportTitle ="成本中心"
							 + dto.getCostName()
							 + "已盘点设备";
			} else if(exportType.equals(DictConstant.SCAN_ITEM_N)){
				sqlModel = modelProducer.getNotScanedAssetsModel();
				reportTitle ="成本中心"
							 + dto.getCostName()
							 + "未盘点资产";
			}
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
			rule.setFieldMap(getFieldMap(exportType));
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

	private Map getFieldMap(String exportType){
		Map fieldMap = new HashMap();
		if(!exportType.endsWith(AssetsDictConstant.SCAN_ITEM_Y)){
			fieldMap.put("TAG_NUMBER", "资产条码");
			fieldMap.put("FA_CATEGORY1", "应用领域");
			fieldMap.put("FA_CATEGORY2", "资产类别");
			fieldMap.put("SEGMENT2", "类别代码");
			fieldMap.put("ASSETS_DESCRIPTION", "资产名称");
			fieldMap.put("MODEL_NUMBER", "资产型号");
			fieldMap.put("CURRENT_UNITS", "资产数量");
			fieldMap.put("UNIT_OF_MEASURE", "计量单位");
			fieldMap.put("ASSETS_LOCATION_CODE", "地点代码");
			fieldMap.put("ASSETS_LOCATION", "资产地点");
			fieldMap.put("ASSIGNED_TO_NUMBER", "员工编号");
			fieldMap.put("ASSIGNED_TO_NAME", "责任人");
			fieldMap.put("MIS_PROJECT_NUMBER", "项目编号");
			fieldMap.put("PROJECT_NAME", "项目名称");
			fieldMap.put("ORIGINAL_COST", "原始成本");
			fieldMap.put("COST", "当前成本");
			fieldMap.put("LIFE_IN_YEARS", "折旧年限");
			fieldMap.put("ASSETS_CREATE_DATE", "创建日期");
			fieldMap.put("DATE_PLACED_IN_SERVICE", "启用日期");
			fieldMap.put("DEPRN_RESERVE", "累计折旧");
			fieldMap.put("DEPRN_COST", "资产净值");
			fieldMap.put("SCRAP_VALUE", "资产残值");
		} else {
			fieldMap.put("BARCODE", "标签号");
			fieldMap.put("ITEM_CATEGORY_NAME", "设备分类");
			fieldMap.put("ITEM_NAME", "设备名称");
			fieldMap.put("ITEM_SPEC", "设备型号");
			fieldMap.put("START_DATE", "启用日期");
			fieldMap.put("WORKORDER_OBJECT_CODE", "地点代码");
			fieldMap.put("WORKORDER_OBJECT_NAME", "地点简称");
			fieldMap.put("USER_NAME", "责任人");
			fieldMap.put("EMPLOYEE_NUMBER", "员工号");
			fieldMap.put("DEPT_NAME", "责任部门");
			fieldMap.put("SCAN_MAINTAIN_USER", "使用人");
		}
		return fieldMap;
	}
}
