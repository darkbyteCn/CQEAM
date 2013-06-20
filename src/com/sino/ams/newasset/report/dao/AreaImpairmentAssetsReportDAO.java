package com.sino.ams.newasset.report.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.report.dto.SpecialAssetsReportDTO;
import com.sino.ams.newasset.report.model.AreaImpairmentAssetsReportModel;
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
 * User: 李轶
 * Date: 2009-6-14
 * Time: 22:56:55
 * Function:	按地域资产构成分布(减值)
 */
public class AreaImpairmentAssetsReportDAO extends AMSBaseDAO {

	public AreaImpairmentAssetsReportDAO(SfUserDTO userAccount, SpecialAssetsReportDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器baseSQLProducer的初始化。具体的DAO继承时初始化具体的SQL生成器。
	 *
	 * @param userAccount BaseUserDTO
	 * @param dtoParameter DTO
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		SfUserDTO user = (SfUserDTO)userAccount;
		SpecialAssetsReportDTO dto = (SpecialAssetsReportDTO) dtoParameter;
		sqlProducer = new AreaImpairmentAssetsReportModel(user, dto);
	}


	/**
	 * 功能：获取部门盘点设备明细Excel文件
	 * @return File
	 * @throws com.sino.base.exception.DataTransException
	 */
	public File getExportFile() throws DataTransException {
		File file = null;
		try {
			SQLModel sqlModel = sqlProducer.getPageQueryModel();
			String reportTitle = "地域资产构成分布(减值)";
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
			fieldMap.put("ASSETS_SPECIES", "类");
			fieldMap.put("ASSETS_NAPE", "项");
			fieldMap.put("CITY_IMPAIR_AMOUNT", "省市区域固定资产当期减值额");
			fieldMap.put("COUNTY_IMPAIR_AMOUNT", "县城区域固定资产当期减值额");
			fieldMap.put("VILLAGE_IMPAIR_AMOUNT", "农村区域固定资产当期减值额");
			fieldMap.put("CITY_COUNT", "省市区域资产数量");
			fieldMap.put("COUNTY_COUNT", "县城区域资产数量");
			fieldMap.put("VILLAGE_COUNT", "农村区域资产数量");
			
            fieldMap.put("CITY_RATE", "省市区域占当期资产总数比重");
            fieldMap.put("COUNTY_RATE", "县城区域占当期资产总数比重");
            fieldMap.put("VILLAGE_RATE", "农村区域占当期资产总数比重");
            
            fieldMap.put("CITY_IMPAIR_RATE", "省市区域占当期资产减值额比重");
            fieldMap.put("COUNTY_IMPAIR_RATE", "县城区域占当期资产减值额比重");
            fieldMap.put("VILLAGE_IMPAIR_RATE", "农村区域占当期资产减值额比重");
            
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
