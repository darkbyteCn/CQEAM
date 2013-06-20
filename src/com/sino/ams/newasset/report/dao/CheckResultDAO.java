package com.sino.ams.newasset.report.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.dto.AmsAssetsCheckHeaderDTO;
import com.sino.ams.newasset.report.model.CheckResultModel;
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
import com.sino.base.exception.SinoBaseException;
import com.sino.framework.dto.BaseUserDTO;

public class CheckResultDAO extends AMSBaseDAO {

	public CheckResultDAO(SfUserDTO userAccount, AmsAssetsCheckHeaderDTO dtoParameter, Connection conn) {
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
		sqlProducer = new CheckResultModel(user, dto);
	}



	/**
	 * 功能：获取部门盘点设备明细Excel文件
	 * @return File
	 * @throws DataTransException
	 * @throws SinoBaseException 
	 */
	public File getExportFile() throws DataTransException, SinoBaseException {
		File file = null;
		CheckResultModel modelProducer = (CheckResultModel)sqlProducer;
		SQLModel sqlModel = modelProducer.getExportModel();
		String reportTitle = getExportTitle();
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
		return file;
	}

	/**
	 * 功能：获取导出Excel的标题
	 * @return String
	 */
	private String getExportTitle() {
		String expTitle = "";
		AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
		String analyseType = dto.getAnalyseType();
		if(analyseType.equals(AssetsDictConstant.CHECK_RESULT_1)){//帐实一致
			expTitle = "已盘MIS清单";
		} else if(analyseType.equals(AssetsDictConstant.CHECK_RESULT_3)){//有卡无物
			expTitle = "未盘MIS清单";
		} else if(analyseType.equals(AssetsDictConstant.CHECK_RESULT_4)){//有物无卡
			expTitle = "有物无卡";
		} else if(analyseType.equals(AssetsDictConstant.CHECK_RESULT_5)){//有卡无物
			expTitle = "有卡无物(已盘地点)";
		} else if(analyseType.equals(AssetsDictConstant.CHECK_RESULT_6)){//有卡无物
			expTitle = "无需PDA扫描清单";
		}
		return expTitle;
	}

	/**
	 * 功能：获取导出数据字段映射
	 * @return Map
	 */
	private Map getFieldMap(){
		Map fieldMap = new HashMap();
		AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
		String analyseType = dto.getAnalyseType();
		if(analyseType.equals(AssetsDictConstant.CHECK_RESULT_1)){//帐实一致
			fieldMap.put("MIS_TAG_NUMBER", "资产标签");
			fieldMap.put("DEPT_NAME", "专业部门");
			fieldMap.put("ITEM_QTY", "原始数量");
			fieldMap.put("ACTUAL_QTY", "实际数量");
			fieldMap.put("ITEM_NAME", "EAM名称");
			fieldMap.put("ASSETS_DESCRIPTION", "MIS名称");
			fieldMap.put("ITEM_SPEC", "EAM型号");
			fieldMap.put("MODEL_NUMBER", "MIS型号");
			fieldMap.put("START_DATE", "EAM启用日期");
			fieldMap.put("DATE_PLACED_IN_SERVICE", "MIS启用日期");
			fieldMap.put("WORKORDER_OBJECT_CODE", "EAM地点代码");
			fieldMap.put("ASSETS_LOCATION_CODE", "MIS地点代码");
			fieldMap.put("WORKORDER_OBJECT_NAME", "EAM地点名称");
			fieldMap.put("ASSETS_LOCATION", "MIS地点名称");
			fieldMap.put("EMPLOYEE_NUMBER", "EAM员工编号");
			fieldMap.put("ASSIGNED_TO_NUMBER", "MIS员工编号");
			fieldMap.put("USER_NAME", "EAM责任人");
			fieldMap.put("ASSIGNED_TO_NAME", "MIS责任人");
			fieldMap.put("CHANGED_CONTENT", "变更数据");
			String disabled = dto.getDisabled();
			if(disabled.equals("")){
				fieldMap.put("AMS_COST_CODE", "EAM成本中心代码");
				fieldMap.put("MIS_COST_CODE", "MIS成本中心代码");
				fieldMap.put("AMS_COST_NAME", "EAM成本中心名称");
				fieldMap.put("MIS_COST_NAME", "MIS成本中心名称");
			}
		} else if(analyseType.equals(AssetsDictConstant.CHECK_RESULT_3) || analyseType.equals(AssetsDictConstant.CHECK_RESULT_5)|| analyseType.equals(AssetsDictConstant.CHECK_RESULT_6)){//有卡无物
			fieldMap.put("TAG_NUMBER", "资产标签");
            fieldMap.put("DEPT_NAME", "专业部门");
//			fieldMap.put("ITEM_QTY", "原始数量");
			fieldMap.put("ACTUAL_QTY", "实际数量");
			fieldMap.put("FA_CATEGORY1", "应用领域");
			fieldMap.put("FA_CATEGORY2", "资产类别");
			fieldMap.put("ASSETS_DESCRIPTION", "资产名称");
			fieldMap.put("MODEL_NUMBER", "资产型号");
			fieldMap.put("CURRENT_UNITS", "资产数量");
			fieldMap.put("UNIT_OF_MEASURE", "计量单位");
			fieldMap.put("ASSIGNED_TO_NUMBER", "员工号");
			fieldMap.put("ASSIGNED_TO_NAME", "责任人");
			fieldMap.put("ASSETS_LOCATION_CODE", "地点代码");
			fieldMap.put("ASSETS_LOCATION", "地点名称");
			fieldMap.put("ASSETS_CREATE_DATE", "资产创建日期");
			fieldMap.put("DATE_PLACED_IN_SERVICE", "启用日期");
			fieldMap.put("ORIGINAL_COST", "原始成本");
			fieldMap.put("COST", "当前成本");
			fieldMap.put("DEPRN_COST", "资产净值");
			fieldMap.put("IMPAIR_RESERVE", "减值准备累计");
			fieldMap.put("SCRAP_VALUE", "资产残值");
			fieldMap.put("DEPRECIATION_ACCOUNT", "折旧费用账户");
			fieldMap.put("MIS_PROJECT_NUMBER", "项目编号");
			fieldMap.put("PROJECT_NAME", "项目名称");
		} else if(analyseType.equals(AssetsDictConstant.CHECK_RESULT_4)){//有物无卡
			fieldMap.put("BARCODE", "设备条码");
            fieldMap.put("DEPT_NAME", "专业部门");
			fieldMap.put("ITEM_QTY", "原始数量");
			fieldMap.put("ACTUAL_QTY", "实际数量");
			fieldMap.put("ITEM_NAME", "设备名称");
			fieldMap.put("ITEM_SPEC", "设备型号");
			fieldMap.put("WORKORDER_OBJECT_CODE", "地点代码");

			fieldMap.put("WORKORDER_OBJECT_NAME", "地点名称");
			fieldMap.put("EMPLOYEE_NUMBER", "员工号");
			fieldMap.put("USER_NAME", "责任人");
			fieldMap.put("COST_CENTER_NAME", "成本中心名称");

			fieldMap.put("PROJECT_NUMBER", "项目编号");
			fieldMap.put("PROJECT_NAME", "项目名称");
		}
		return fieldMap;
	}


	/**
	 * 功能：将OU盘点排名导出到Excel。
	 * @return File
	 * @throws DataTransException
	 */
	public File getPageExportFile() throws DataTransException {
		File file = null;
		try {
			CheckResultModel modelProducer = (CheckResultModel)sqlProducer;
			SQLModel sqlModel = modelProducer.getPageQueryModel();
			String reportTitle = "盘点结果报表（按公司）";
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
			rule.setFieldMap(getPageFieldMap());
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

	private Map getPageFieldMap() {
		Map fieldMap = new HashMap();
		fieldMap.put("COMPANY", "公司名称");
		fieldMap.put("TOTAL_COUNT", "MIS资产数量");
		fieldMap.put("NEED_COUNT", "MIS需PDA扫描数量");
		fieldMap.put("NOT_NEED_COUNT", "MIS无需PDA扫描数量");
		fieldMap.put("SCANED_COUNT", "已盘点总量");
		fieldMap.put("IDENTICAL_COUNT", "已盘MIS数量");
		fieldMap.put("UNMATCHED_COUNT", "已盘实物数量");
		fieldMap.put("IDENTICAL_RATE_1", "盘点率");
//		fieldMap.put("IDENTICAL_RATE_2", "帐实相符率(按盘点数)");
		return fieldMap;
	}
}
