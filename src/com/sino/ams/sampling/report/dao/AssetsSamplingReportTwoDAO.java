package com.sino.ams.sampling.report.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.sampling.dto.AmsAssetsSamplingHeaderDTO;
import com.sino.ams.sampling.report.model.AssetsSamplingReportTwoModel;
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
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2009-9-18
 * Time: 10:30:12
 * To change this template use File | Settings | File Templates.
 */
public class AssetsSamplingReportTwoDAO extends AMSBaseDAO {

	public AssetsSamplingReportTwoDAO(SfUserDTO userAccount, AmsAssetsSamplingHeaderDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		SfUserDTO user = (SfUserDTO)userAccount;
		AmsAssetsSamplingHeaderDTO dto = (AmsAssetsSamplingHeaderDTO) dtoParameter;
		sqlProducer = new AssetsSamplingReportTwoModel(user, dto);
	}



	/**
	 * 功能：获取部门盘点设备明细Excel文件
	 * @return File
	 * @throws com.sino.base.exception.DataTransException
	 */
	public File getExportFile() throws DataTransException {
		File file = null;
		try {
			AssetsSamplingReportTwoModel modelProducer = (AssetsSamplingReportTwoModel)sqlProducer;
			SQLModel sqlModel = modelProducer.getPageQueryModel();
			String reportTitle = "账实不符";
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
		fieldMap.put("BARCODE", "设备条码");
        fieldMap.put("AMS_ITEM_NAME", "设备名称(EAM系统)");
		fieldMap.put("SCAN_ITEM_NAME", "设备名称(实际扫描)");
		fieldMap.put("AMS_ITEM_SPEC", "设备型号(EAM系统)");
		fieldMap.put("SCAN_ITEM_SPEC", "设备型号(实际扫描)");
		fieldMap.put("AMS_LOCATION_CODE", "地点代码(EAM系统)");
		fieldMap.put("SCAN_LOCATION_CODE", "地点代码(实际扫描)");
		fieldMap.put("AMS_LOCATION", "地点名称(EAM系统)");
		fieldMap.put("SCAN_LOCATION", "地点名称(实际扫描)");
		fieldMap.put("AMS_EMPLOYEE_NUMBER", "员工编号(EAM系统)");
		fieldMap.put("SCAN_EMPLOYEE_NUMBER", "员工编号(实际扫描)");
		fieldMap.put("AMS_USER_NAME", "责任人(EAM系统)");
		fieldMap.put("SCAN_USER_NAME", "责任人(EAM系统)");
		fieldMap.put("CHANGED_CONTENT", "变更数据");
		return fieldMap;
	}
}
