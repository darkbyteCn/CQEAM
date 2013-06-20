package com.sino.ams.newasset.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.newasset.model.MisDiscardedAssetsQueryModel;
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

public class MisDiscardedAssetsQueryDAO extends AMSBaseDAO {

	/**
	 * Function：		MIS资产报废查询			
	 * Title: 			SinoApplication
	 * @param 			userAccount
	 * @param 			dtoParameter
	 * @param conn
	 * Description:		Java Enterprise Edition 应用开发
	 * Copyright:		李轶版权所有Copyright (c)2009~2022。
	 * Copyright: 		其中使用到的第三方组件，根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约，版权属原作者所有。
	 * Copyright: 		作者授权北京思诺博信息技术有限公司在一定范围内使用
	 * Company: 		北京思诺博信息技术有限公司
	 * @author 			李轶
	 * @version 		0.1
	 * @Date			May 12, 2009
	 */
	public MisDiscardedAssetsQueryDAO(SfUserDTO userAccount, AmsAssetsAddressVDTO dtoParameter, Connection conn) {
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
		sqlProducer = new MisDiscardedAssetsQueryModel(user,dto);
	}


	/**
	 * 功能：导出查询SQL资产数据
	 * @return String 返回导出Excel文件
	 * @throws com.sino.base.exception.DataTransException
	 */
	public File getExportFile() throws DataTransException {
		File file = null;
		try {
			MisDiscardedAssetsQueryModel modelProducer = (MisDiscardedAssetsQueryModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getPageQueryModel();
			String reportTitle = "MIS资产报废查询";
			String fileName = reportTitle + ".xls";
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
		fieldMap.put("TAG_NUMBER", "资产标签");
		fieldMap.put("MIS_TAG_NUMBER", "原MIS标签");
		fieldMap.put("ASSET_NUMBER", "资产编号");
		fieldMap.put("FA_CATEGORY1", "应用领域");
		fieldMap.put("SEGMENT2", "资产类别代码");
		fieldMap.put("FA_CATEGORY2", "资产类别");

		fieldMap.put("ASSETS_DESCRIPTION", "资产名称");
		fieldMap.put("MODEL_NUMBER", "资产型号");
		fieldMap.put("CURRENT_UNITS", "资产数量");
		fieldMap.put("UNIT_OF_MEASURE", "计量单位");
		fieldMap.put("ASSETS_LOCATION_CODE", "地点代码");

		fieldMap.put("ASSETS_LOCATION", "地点位置");
		fieldMap.put("ASSIGNED_TO_NAME", "责任人姓名");
		fieldMap.put("ASSIGNED_TO_NUMBER", "责任人员工号");
		fieldMap.put("PROJECT_NUMBER", "项目编号");
		fieldMap.put("PROJECT_NAME", "项目名称");

		fieldMap.put("PROJECT_TYPE", "项目类型");
		fieldMap.put("LIFE_IN_YEARS", "折旧年限");
		fieldMap.put("DATE_PLACED_IN_SERVICE", "启用日期");
		fieldMap.put("ASSETS_CREATE_DATE", "创建日期");
		fieldMap.put("ORIGINAL_COST", "原始成本");

		fieldMap.put("COST", "当前成本");
		fieldMap.put("DEPRN_RESERVE", "累计折旧");
		fieldMap.put("DEPRN_COST", "资产净值");
		fieldMap.put("SCRAP_VALUE", "资产残值");
		fieldMap.put("IMPAIR_RESERVE", "减值准备累计");

		fieldMap.put("COMPANY", "公司名称");
		fieldMap.put("BOOK_TYPE_CODE", "资产账簿");
		fieldMap.put("DEPRECIATION_ACCOUNT", "折旧账户代码");
		fieldMap.put("DEPRECIATION_ACCOUNT_NAME", "折旧账户名称");
		
		fieldMap.put("DATE_RETIRED", "报废日期");
		fieldMap.put("DATE_EFFECTIVE", "报废生效日期");
		fieldMap.put("COST_RETIRED", "报废成本");
		fieldMap.put("RETIREMENT_TYPE_CODE", "报废类型");
		return fieldMap;
	}
}

