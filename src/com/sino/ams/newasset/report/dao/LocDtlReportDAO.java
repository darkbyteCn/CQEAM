package com.sino.ams.newasset.report.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.newasset.dto.AmsAssetsCheckHeaderDTO;
import com.sino.ams.newasset.report.model.LocDetailReportModel;
import com.sino.ams.system.user.dto.EtsOuCityMapDTO;
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

public class LocDtlReportDAO extends AMSBaseDAO {

	public LocDtlReportDAO(SfUserDTO userAccount, AmsAssetsCheckHeaderDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器baseSQLProducer的初始化。具体的DAO继承时初始化具体的SQL生成器。
	 * @param userAccount BaseUserDTO
	 * @param dtoParameter DTO
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		SfUserDTO user = (SfUserDTO)userAccount;
		AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO)dtoParameter;
		sqlProducer = new LocDetailReportModel(user, dto);
	}


	/**
	 * 功能：导出公司盘点情况Excel报表文件
	 * @param company EtsOuCityMapDTO
	 * @return File
	 * @throws DataTransException
	 */
	public File getExportFile(EtsOuCityMapDTO company) throws DataTransException {
		File file = null;
		try {
			AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO)dtoParameter;
			String exportType = dto.getExportType();
			String reportTitle = "";
			LocDetailReportModel modelProducer = (LocDetailReportModel) sqlProducer;
			SQLModel sqlModel = null;
			if(exportType.equals(DictConstant.EXPORT_RES_LOC)){
				sqlModel = modelProducer.getOwnLocModel();
				reportTitle = company.getCompany() +  "所有地点";
			} else if(exportType.equals(DictConstant.EXPORT_SCAN_LOC_Y)){
				sqlModel = modelProducer.getScanedLocModel();
				reportTitle = company.getCompany() + "已盘点地点";
			} else if(exportType.equals(DictConstant.EXPORT_SCAN_LOC_N)){
				sqlModel = modelProducer.getNotScanedLocModel();
				reportTitle = company.getCompany() + "未盘点地点";
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
			Map fieldMap = new HashMap();
			fieldMap.put("WORKORDER_OBJECT_CODE", "地点代码");
			fieldMap.put("WORKORDER_OBJECT_NAME", "地点简称");
			fieldMap.put("WORKORDER_OBJECT_LOCATION", "所在位置");
			fieldMap.put("OBJECT_CATEGORY_NAME", "地点分类");
			rule.setFieldMap(fieldMap);
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
}
