package com.sino.ams.newasset.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.newasset.model.MisAssetsQueryModel;
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
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;

public class MisAssetsQueryDAO extends AMSBaseDAO {

	public MisAssetsQueryDAO(SfUserDTO userAccount, AmsAssetsAddressVDTO dtoParameter, Connection conn) {
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
		AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO)dtoParameter;
		sqlProducer = new MisAssetsQueryModel(user,dto);
	}


	/**
	 * 功能：导出查询SQL资产数据
	 * @return String 返回导出Excel文件
	 * @throws com.sino.base.exception.DataTransException
	 */
	public File getExportFile(String excelType) throws DataTransException {
		File file = null;
		try {
			MisAssetsQueryModel modelProducer = (MisAssetsQueryModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getPageQueryModel();
			String reportTitle = "MIS资产台账";
			if (!StrUtil.isNotEmpty(excelType)) {
				excelType = "xls";
			}
			String fileName = reportTitle + "." + excelType;
			TransRule rule = new TransRule();
			rule.setDataSource(sqlModel);
			rule.setSourceConn(conn);
			rule.setPageSize(2000);
			String filePath = WorldConstant.USER_HOME;
			filePath += WorldConstant.FILE_SEPARATOR;
			filePath += fileName;
			rule.setTarFile(filePath);
			DataRange range = new DataRange();
			rule.setDataRange(range);
			Map fieldMap = getFieldMap();
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

	private Map getFieldMap(){
		Map fieldMap = new HashMap();
		fieldMap.put("MIS_TAG_NUMBER", "标签号");
		fieldMap.put("ASSET_NUMBER", "资产编号");
		fieldMap.put("FA_CATEGORY1", "应用领域");
		fieldMap.put("SEGMENT2", "资产类别代码");
		fieldMap.put("FA_CATEGORY2", "资产类别");

		fieldMap.put("ASSETS_DESCRIPTION", "设备名称");
		fieldMap.put("MODEL_NUMBER", "规格型号");
		fieldMap.put("CURRENT_UNITS", "数量");
		fieldMap.put("UNIT_OF_MEASURE", "单位");
		fieldMap.put("ASSETS_LOCATION_CODE", "地点编号");

		fieldMap.put("ASSETS_LOCATION", "地点名称");
		fieldMap.put("ASSIGNED_TO_NAME", "责任人");
		fieldMap.put("ASSIGNED_TO_NUMBER", "员工编号");
		fieldMap.put("PROJECT_NUMBER", "项目编号");
		fieldMap.put("PROJECT_NAME", "项目名称");

		fieldMap.put("PROJECT_TYPE", "项目类型");
		fieldMap.put("LIFE_IN_YEARS", "年限");
		fieldMap.put("DATE_PLACED_IN_SERVICE", "启用日期");
		fieldMap.put("ASSETS_CREATE_DATE", "创建日期");
		fieldMap.put("ORIGINAL_COST", "原始成本");
 
        fieldMap.put("NET_ASSET_VALUE", "净值");
		fieldMap.put("COST", "当前成本");
		fieldMap.put("DEPRN_COST", "资产净额");
		fieldMap.put("SCRAP_VALUE", "资产残值");
		fieldMap.put("DEPRN_AMOUNT", "本期折旧额");
		
		fieldMap.put("YTD_DEPRN", "本年折旧额");
		fieldMap.put("DEPRN_RESERVE", "累计折旧");
		fieldMap.put("IMPAIR_AMOUNT", "资产减值准备");
		fieldMap.put("YTD_IMPAIRMENT", "本年减值准备");
		fieldMap.put("IMPAIR_RESERVE", "减值准备累计");
		
//		fieldMap.put("DEPRN_RESERVE", "累计折旧");
//		fieldMap.put("DEPRN_COST", "资产净额");
//		fieldMap.put("SCRAP_VALUE", "资产残值");
//		fieldMap.put("IMPAIR_RESERVE", "减值准备累计");

		fieldMap.put("COMPANY", "公司名称");
		fieldMap.put("BOOK_TYPE_CODE", "资产帐簿代码");
        fieldMap.put("BOOK_TYPE_NAME", "资产帐簿名称");
        fieldMap.put("DEPRECIATION_ACCOUNT", "折旧帐户代码");
		fieldMap.put("DEPRECIATION_ACCOUNT_NAME", "折旧帐户名称");
		fieldMap.put("IS_RETIREMENTS", "报废状态");
		fieldMap.put("DATE_RETIRED", "报废日期");
		return fieldMap;
	}
}
