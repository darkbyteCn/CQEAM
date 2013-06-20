package com.sino.ams.newasset.report.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.dto.AmsAssetsChangedVDTO;
import com.sino.ams.newasset.report.model.AssetsChangeReportModel;
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

public class AssetsChangeReportDAO extends AMSBaseDAO {

	public AssetsChangeReportDAO(SfUserDTO userAccount, AmsAssetsChangedVDTO dtoParameter, Connection conn) {
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
		AmsAssetsChangedVDTO dto = (AmsAssetsChangedVDTO) dtoParameter;
		sqlProducer = new AssetsChangeReportModel(user, dto);
	}


	/**
	 * 功能：获取部门盘点设备明细Excel文件
	 * @return File
	 * @throws DataTransException
	 */
	public File getExportFile() throws DataTransException {
		File file = null;
		try {
			SQLModel sqlModel = sqlProducer.getPageQueryModel();
			String reportTitle = "资产变更分析";
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

	private Map getFieldMap(){
		Map fieldMap = new HashMap();
		fieldMap.put("COMPANY", "公司名称");

		fieldMap.put("BARCODE", "EAM标签");
		fieldMap.put("TAG_NUMBER", "MIS标签");
		fieldMap.put("ITEM_NAME", "EAM名称");

		fieldMap.put("ASSETS_DESCRIPTION", "MIS名称");
		fieldMap.put("ITEM_SPEC", "EAM型号");
		fieldMap.put("MODEL_NUMBER", "MIS型号");

		fieldMap.put("EMPLOYEE_NUMBER", "EAM员工号");
		fieldMap.put("ASSIGNED_TO_NUMBER", "MIS员工号");
		fieldMap.put("USER_NAME", "EAM责任人");
		fieldMap.put("ASSIGNED_TO_NAME", "MIS责任人");

		fieldMap.put("WORKORDER_OBJECT_CODE", "EAM地点代码");
		fieldMap.put("ASSETS_LOCATION_CODE", "MIS地点代码");
		fieldMap.put("WORKORDER_OBJECT_LOCATION", "EAM地点");
		fieldMap.put("ASSETS_LOCATION", "MIS地点");

		fieldMap.put("AMS_COST_CODE", "EAM成本中心代码");
		fieldMap.put("MIS_COST_CODE", "MIS成本中心代码");
		fieldMap.put("AMS_COST_NAME", "EAM成本中心");
		fieldMap.put("MIS_COST_NAME", "MIS成本中心");

		fieldMap.put("CHANGED_CONTENT", "变更内容");

		return fieldMap;
	}
}
