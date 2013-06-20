package com.sino.ams.newasset.report.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.newasset.report.model.AssetsAnalysisModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.data.RowSet;
import com.sino.base.db.datatrans.CustomTransData;
import com.sino.base.db.datatrans.DataRange;
import com.sino.base.db.datatrans.DataTransfer;
import com.sino.base.db.datatrans.TransRule;
import com.sino.base.db.datatrans.TransferFactory;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.exception.WebFileDownException;
import com.sino.framework.dto.BaseUserDTO;

public class AssetsAnalysisDAO extends AMSBaseDAO {
	public AssetsAnalysisDAO(SfUserDTO userAccount, AmsAssetsAddressVDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器baseSQLProducer的初始化。具体的DAO继承时初始化具体的SQL生成器。
	 * @param userAccount BaseUserDTO
	 * @param dtoParameter DTO
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		SfUserDTO user = (SfUserDTO) userAccount;
		AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
		sqlProducer = new AssetsAnalysisModel(user, dto);
	}

	/**
	 * 功能：导出查询SQL资产数据
	 * @return String 返回导出Excel文件
	 * @throws WebFileDownException
	 */
	public File getExportFile() throws WebFileDownException {
		File file = null;
		try {
			SQLModel sqlModel = sqlProducer.getPageQueryModel();
			String reportTitle = "固定资产增减变动分析表";
			String fileName = reportTitle + ".xls";
			TransRule rule = new TransRule();
			rule.setDataSource(sqlModel);
			rule.setSourceConn(conn);
			String filePath = WorldConstant.USER_HOME;
			filePath += WorldConstant.FILE_SEPARATOR;
			filePath += fileName;
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
			throw new WebFileDownException(ex);
		} catch (DataTransException ex) {
			ex.printLog();
			throw new WebFileDownException(ex);
		}
		return file;
	}

	/**
	 * 功能：构造导出数据的字段映射
	 * @return Map
	 */
	private Map getFieldMap() {
		Map fieldMap = new HashMap();
		fieldMap.put("COMPANY", "公司名称");
		fieldMap.put("TOTAL_COUNT", "资产数量");
		fieldMap.put("TOTAL_COST", "资产金额");
		fieldMap.put("YEAR_ADD_COUNT", "本年新增数量");
		fieldMap.put("YEAR_ADD_COST", "本年新增金额");
		fieldMap.put("MONTH_ADD_COUNT", "本月新增数量");
		fieldMap.put("MONTH_ADD_COST", "本月新增金额");
		fieldMap.put("YEAR_DIS_COUNT", "本年报废数量");
		fieldMap.put("YEAR_DIS_COST", "本年报废金额");
		fieldMap.put("MONTH_DIS_COUNT", "本月报废数量");
		fieldMap.put("MONTH_DIS_COST", "本月报废金额");
		return fieldMap;
	}

	/**
	 * 功能：获取特定公司的资产变动数据
	 * @return RowSet
	 * @throws QueryException
	 */
	public RowSet getOrgAssetsChangeData() throws QueryException {
		RowSet rows = new RowSet();
		try {
			AssetsAnalysisModel modelProducer = (AssetsAnalysisModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getOrgAssetsChangeModel();
			SimpleQuery simp = new SimpleQuery(sqlModel, conn);
			simp.executeQuery();
			rows = simp.getSearchResult();
		} catch (SQLModelException ex) {
			ex.printLog();
			throw new QueryException(ex);
		}
		return rows;
	}

	/**
	 * 功能：导出查询SQL资产数据
	 * @return String 返回导出Excel文件
	 * @throws WebFileDownException
	 */
	public File exportOrgAssets() throws WebFileDownException {
		File file = null;
		try {
			AssetsAnalysisModel modelProducer = (AssetsAnalysisModel)sqlProducer;
			SQLModel sqlModel = modelProducer.getOrgAssetsChangeModel();
			AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
			String reportTitle = dto.getCompany() + "资产变动分析";
			String fileName = reportTitle + ".xls";
			TransRule rule = new TransRule();
			rule.setDataSource(sqlModel);
			rule.setSourceConn(conn);
			String filePath = WorldConstant.USER_HOME;
			filePath += WorldConstant.FILE_SEPARATOR;
			filePath += fileName;
			rule.setTarFile(filePath);
			DataRange range = new DataRange();
			rule.setDataRange(range);
			rule.setFieldMap(getOrgAssetsFieldMap());
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
			throw new WebFileDownException(ex);
		} catch (DataTransException ex) {
			ex.printLog();
			throw new WebFileDownException(ex);
		}
		return file;
	}

	/**
	 * 功能：获取资产变动分析报表第二层中的字段映射
	 * @return Map
	 */
	private Map getOrgAssetsFieldMap(){
		Map fieldMap = new HashMap();
		AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
		String analyseType = dto.getAnalyseType();
		fieldMap.put("TOTAL_COUNT", "资产数量");
		fieldMap.put("TOTAL_COST", "资产金额");
		fieldMap.put("YEAR_ADD_COUNT", "本年新增数量");
		fieldMap.put("YEAR_ADD_COST", "本年新增金额");
		fieldMap.put("MONTH_ADD_COUNT", "本月新增数量");
		fieldMap.put("MONTH_ADD_COST", "本月新增金额");
		fieldMap.put("YEAR_DIS_COUNT", "本年报废数量");
		fieldMap.put("YEAR_DIS_COST", "本年报废金额");
		fieldMap.put("MONTH_DIS_COUNT", "本月报废数量");
		fieldMap.put("MONTH_DIS_COST", "本月报废金额");
		if(analyseType.equals(AssetsDictConstant.ANALYZE_CATEGORY_1)){
			fieldMap.put("SEGMENT", "应用领域代码");
			fieldMap.put("FA_CATEGORY", "应用领域");
		} else if(analyseType.equals(AssetsDictConstant.ANALYZE_CATEGORY_2)){
			fieldMap.put("SEGMENT", "资产大类代码");
			fieldMap.put("FA_CATEGORY", "资产大类");
		} else if(analyseType.equals(AssetsDictConstant.ANALYZE_CATEGORY_3)){
			fieldMap.put("SEGMENT", "资产项代码");
			fieldMap.put("FA_CATEGORY", "资产项");
		}
		return fieldMap;
	}
}
