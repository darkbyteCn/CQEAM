package com.sino.ams.newasset.report.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.dto.AmsAssetsCheckHeaderDTO;
import com.sino.ams.newasset.report.model.NewAssetsMonitorModel;
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

public class NewAssetsMonitorDAO extends AMSBaseDAO {

	public NewAssetsMonitorDAO(SfUserDTO userAccount, AmsAssetsCheckHeaderDTO dtoParameter, Connection conn) {
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
		sqlProducer = new NewAssetsMonitorModel(user, dto);
	}



	/**
	 * 功能：获取部门盘点设备明细Excel文件
	 * @return File
	 * @throws DataTransException
	 */
	public File getExportFile() throws DataTransException {
		File file = null;
		try {
			NewAssetsMonitorModel modelProducer = (NewAssetsMonitorModel)sqlProducer;
			SQLModel sqlModel = modelProducer.getPageQueryModel();
			String reportTitle = "新增资产监控报表";
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
		fieldMap.put("TOTAL_COUNT", "MIS新增资产数量");
		fieldMap.put("TOTAL_NEED_SCANCEL_COUNT", "需PDA扫描确认数量");

		fieldMap.put("SCANED_TOTAL_COUNT", "期间实际扫描数量");
		fieldMap.put("SCANCER_IDENTICAL_COUNT", "账实相符数量");
        fieldMap.put("IDENTICAL_RATE", "账实相符百分比");

        fieldMap.put("SCANED_FILTER_COUNT", "其中新转资资产扫描数量");
		fieldMap.put("SCANER_NEED_SCANED_COUNT", "已完成新转资资产并且需PDA扫描确认的数量");
        fieldMap.put("NOT_SCANED_COUNT", "新转资资产尚未扫描确认数量");
        fieldMap.put("SCANED_RATE", "新转资资产扫描确认百分比");
        return fieldMap;
	}
}
