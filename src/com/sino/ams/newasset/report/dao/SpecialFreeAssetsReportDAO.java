package com.sino.ams.newasset.report.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.report.dto.SpecialAssetsReportDTO;
import com.sino.ams.newasset.report.model.SpecialFreeAssetsReportModel;
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
 * @author:			李轶
 * Function:		应用领域统计(闲置)
 * Date: 2009-5-25
 * Time: 17:53:30
 * To change this template use File | Settings | File Templates.
 */
public class SpecialFreeAssetsReportDAO extends AMSBaseDAO {

	public SpecialFreeAssetsReportDAO(SfUserDTO userAccount, SpecialAssetsReportDTO dtoParameter, Connection conn) {
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
		sqlProducer = new SpecialFreeAssetsReportModel(user, dto);
	}


	/**
	 * 功能：获取闲置资产应用领域统计Excel文件
	 * @return File
	 * @throws com.sino.base.exception.DataTransException
	 */
	public File getExportFile(SpecialAssetsReportDTO dto) throws DataTransException {
		File file = null;
		try {
			SQLModel sqlModel = sqlProducer.getPageQueryModel();
			String reportTitle = "应用领域统计(闲置)";
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
			rule.setFieldMap(getFieldMap(dto));
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

    private Map getFieldMap(SpecialAssetsReportDTO dto){
		Map fieldMap = new HashMap();
        fieldMap.put("COMPANY", "公司");
        fieldMap.put("FA_DESCRIPTION", "应用领域");
        fieldMap.put("SUM_COST", "原值");
		fieldMap.put("DEPRN_RESERVE", "累计折旧");
		fieldMap.put("NET_BOOK_VALUE", "净值");
		fieldMap.put("IMPAIRMENT_RESERVE", "累计减值准备");
		fieldMap.put("LIMIT_VALUE", "净额");
		fieldMap.put("PTD_DEPRN", "当期折旧");
		fieldMap.put("SUM_COUNT", "资产数量");
		fieldMap.put("ASSETS_RATE", "占当期资产总额比重");
		fieldMap.put("LAST_YEAR_RATE", "较去年同期增长率");
        return fieldMap;
	}
}
