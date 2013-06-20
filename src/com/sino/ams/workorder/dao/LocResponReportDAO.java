package com.sino.ams.workorder.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.system.trust.dto.AmsMaintainCompanyDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dto.EtsWorkorderDTO;
import com.sino.ams.workorder.model.LocResponReportModel;
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
public class LocResponReportDAO extends AMSBaseDAO {
	public LocResponReportDAO(SfUserDTO userAccount, EtsWorkorderDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器baseSQLProducer的初始化。具体的DAO继承时初始化具体的SQL生成器。
	 * @param userAccount BaseUserDTO
	 * @param dtoParameter DTO
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		EtsWorkorderDTO dto = (EtsWorkorderDTO)dtoParameter;
		sqlProducer = new LocResponReportModel((SfUserDTO)userAccount, dto);
	}

	/**
	 * 功能：导出代维公司巡检情况Excel报表文件
	 * @param mainCompany AmsMaintainCompanyDTO
	 * @return File
	 * @throws DataTransException
	 */
	public File getExportFile(AmsMaintainCompanyDTO mainCompany) throws DataTransException {
		File file = null;
		try {
			EtsWorkorderDTO dto = (EtsWorkorderDTO)dtoParameter;
			String exportType = dto.getExportType();
			String reportTitle = "";
			LocResponReportModel modelProducer = (LocResponReportModel) sqlProducer;
			SQLModel sqlModel = null;
			if(exportType.equals(DictConstant.EXPORT_RES_LOC)){
				sqlModel = modelProducer.getResponLocationsModel();
				reportTitle = mainCompany.getName() +  "代维责任地点";
			} else if(exportType.equals(DictConstant.EXPORT_SCAN_LOC_Y)){
				sqlModel = modelProducer.getScanedLocationsModel();
				reportTitle = mainCompany.getName() + "已巡检地点";
			} else if(exportType.equals(DictConstant.EXPORT_SCAN_LOC_N)){
				sqlModel = modelProducer.getNotScanedLocationsModel();
				reportTitle = mainCompany.getName() + "未巡检地点";
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
			fieldMap.put("OBJECT_CATEGORY", "地点分类");
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
