package com.sino.ams.newasset.report.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.newasset.dto.AmsAssetsCheckLineDTO;
import com.sino.ams.newasset.report.model.ItemCatDetailRptModel;
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

public class ItemCatDtlReportDAO extends AMSBaseDAO {

	public ItemCatDtlReportDAO(SfUserDTO userAccount, AmsAssetsCheckLineDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器baseSQLProducer的初始化。具体的DAO继承时初始化具体的SQL生成器。
	 * @param userAccount BaseUserDTO
	 * @param dtoParameter DTO
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		SfUserDTO user = (SfUserDTO)userAccount;
		AmsAssetsCheckLineDTO dto = (AmsAssetsCheckLineDTO)dtoParameter;
		sqlProducer = new ItemCatDetailRptModel(user, dto);
	}

	/**
	 * 功能：导出公司盘点情况Excel报表文件
	 * @return File
	 * @throws DataTransException
	 */
	public File getExportFile() throws DataTransException {
		File file = null;
		try {
			AmsAssetsCheckLineDTO dto = (AmsAssetsCheckLineDTO)dtoParameter;
			String exportType = dto.getExportType();
			String reportTitle = "";
			ItemCatDetailRptModel modelProducer = (ItemCatDetailRptModel) sqlProducer;
			SQLModel sqlModel = null;
			if(exportType.equals(DictConstant.EXPORT_LOC_ITEM)){
				sqlModel = modelProducer.getOwnItemModel();
				reportTitle = "本专业现有设备(" + dto.getItemCategoryName() + ")";
			} else if(exportType.equals(DictConstant.EXPORT_SCAN_ITEM)){
				sqlModel = modelProducer.getScanedItemModel();
				reportTitle = "已盘点设备(" + dto.getItemCategoryName() + ")";
			} else if(exportType.equals(DictConstant.EXPORT_DIFF_ITEM)){
				sqlModel = modelProducer.getDiffItemModel();
				reportTitle = "盘点差异(" + dto.getItemCategoryName() + ")";
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
		fieldMap.put("BARCODE", "标签号");
		fieldMap.put("FINANCE_PROP_NAME", "资产种类");
		fieldMap.put("YEARS", "使用年限");
		fieldMap.put("ITEM_UNIT", "计量单位");
		fieldMap.put("WORKORDER_OBJECT_NAME", "所在地点");
		fieldMap.put("COUNTY_NAME", "区县位置");
		fieldMap.put("PROJECT_NAME", "项目名称");
		fieldMap.put("PROJECT_NUMBER", "项目编号");
		fieldMap.put("COMPANY_CODE", "公司代码");
		fieldMap.put("COMPANY", "公司名称");
		if(exportType.equals(DictConstant.EXPORT_DIFF_ITEM)){
			fieldMap.put("ITEM_CATEGORY_NAME", "设备专业(系统)");
			fieldMap.put("ITEM_NAME", "设备名称(系统)");
			fieldMap.put("ITEM_SPEC", "设备型号(系统)");
			fieldMap.put("VENDOR_NAME", "供应商名称(系统)");
			fieldMap.put("VENDOR_NUMBER", "供应商代码(系统)");
			fieldMap.put("RESPONSIBILITY_DEPT_NAME", "责任部门(系统)");
			fieldMap.put("RESPONSIBILITY_USER_NAME", "责任人姓名(系统)");
			fieldMap.put("EMPLOYEE_NUMBER", "责任人员工号(系统)");
			fieldMap.put("MAINTAIN_DEPT_NAME", "使用部门(系统)");
			fieldMap.put("MAINTAIN_USER", "使用人(系统)");

			fieldMap.put("SCAN_ITEM_CATEGORY_NAME","设备专业(扫描)");
			fieldMap.put("SCAN_ITEM_NAME","设备名称(扫描)");
			fieldMap.put("SCAN_ITEM_SPEC","设备型号(扫描)");
			fieldMap.put("SCAN_VENDOR_NAME","供应商名称(扫描)");
			fieldMap.put("SCAN_VENDOR_NUMBER","供应商代码(扫描)");
			fieldMap.put("SCAN_RESPONSIBILITY_DEPT_NAME","责任部门(扫描)");
			fieldMap.put("SCAN_RESPONSIBILITY_USER_NAME","责任人姓名(扫描)");
			fieldMap.put("SCAN_EMPLOYEE_NUMBER","责任人员工号(扫描)");
			fieldMap.put("SCAN_MAINTAIN_DEPT_NAME","使用部门(扫描)");
			fieldMap.put("SCAN_MAINTAIN_USER","使用人(扫描)");
		} else {
			fieldMap.put("ITEM_CATEGORY_NAME", "设备专业");
			fieldMap.put("ITEM_NAME", "设备名称");
			fieldMap.put("ITEM_SPEC", "设备型号");
			fieldMap.put("VENDOR_NAME", "供应商名称");
			fieldMap.put("VENDOR_NUMBER", "供应商代码");
			fieldMap.put("RESPONSIBILITY_DEPT_NAME", "责任部门");
			fieldMap.put("RESPONSIBILITY_USER_NAME", "责任人姓名");
			fieldMap.put("EMPLOYEE_NUMBER", "责任人员工号");
			fieldMap.put("MAINTAIN_DEPT_NAME", "使用部门");
			fieldMap.put("MAINTAIN_USER", "使用人");
		}
		return fieldMap;
	}
}
