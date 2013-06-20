package com.sino.ams.newasset.report.dao;
import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.newasset.dto.AmsAssetsCheckHeaderDTO;
import com.sino.ams.newasset.report.model.ItemResponReportModel;
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
 * <p>Title: SinoEAM</p>
 * <p>Description: 中国移动资产实物管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class ItemResponReportDAO extends AMSBaseDAO {

	public ItemResponReportDAO(SfUserDTO userAccount, AmsAssetsCheckHeaderDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器baseSQLProducer的初始化。具体的DAO继承时初始化具体的SQL生成器。
	 * @param userAccount BaseUserDTO
	 * @param dtoParameter DTO
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO)dtoParameter;
		sqlProducer = new ItemResponReportModel((SfUserDTO)userAccount, dto);
	}

	/**
	 * 功能：导出代维公司盘点情况Excel报表文件
	 * @return File
	 * @throws DataTransException
	 */
	public File getExportFile() throws DataTransException {
		File file = null;
		try {
			AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO)dtoParameter;
			String exportType = dto.getExportType();
			String reportTitle = "";
			ItemResponReportModel modelProducer = (ItemResponReportModel) sqlProducer;
			SQLModel sqlModel = null;
			if(exportType.equals(DictConstant.EXPORT_LOC_ITEM)){
				sqlModel = modelProducer.getResponItemsModel();
				reportTitle = "地点" + dto.getCheckLocationName() +  "设备清单";
			} else if(exportType.equals(DictConstant.EXPORT_SCAN_ITEM)){
				sqlModel = modelProducer.getScanedItemsModel();
				reportTitle = "地点" + dto.getCheckLocationName() + "最近盘点结果";
			} else if(exportType.equals(DictConstant.EXPORT_DIFF_ITEM)){
				sqlModel = modelProducer.getDiffItemsModel();
				reportTitle = "地点" + dto.getCheckLocationName() + "差异情况";
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
			fieldMap.put("BARCODE", "标签号");
			fieldMap.put("ITEM_CATEGORY", "设备分类");
			fieldMap.put("ITEM_NAME", "设备名称");
			fieldMap.put("ITEM_SPEC", "设备型号");
			fieldMap.put("ITEM_UNIT", "计量单位");
			if(exportType.equals(DictConstant.EXPORT_DIFF_ITEM)){
				fieldMap.put("SYSTEM_EXISTS", "系统状态");
				fieldMap.put("SCAN_EXISTS", "扫描状态");
			}
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
