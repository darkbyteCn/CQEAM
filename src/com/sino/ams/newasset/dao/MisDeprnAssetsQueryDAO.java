package com.sino.ams.newasset.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.dto.AmsDeprnAssetsDTO;
import com.sino.ams.newasset.model.MisDeprnAssetsQueryModel;
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

public class MisDeprnAssetsQueryDAO extends AMSBaseDAO {

	public MisDeprnAssetsQueryDAO(SfUserDTO userAccount, AmsDeprnAssetsDTO dtoParameter, Connection conn) {
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
		AmsDeprnAssetsDTO dto = (AmsDeprnAssetsDTO)dtoParameter;
		sqlProducer = new MisDeprnAssetsQueryModel(user,dto);
	}


	/**
	 * 功能：导出查询SQL资产数据
	 * @return String 返回导出Excel文件
	 * @throws com.sino.base.exception.DataTransException
	 */
	public File getExportFile() throws DataTransException {
		File file = null;
		try {
			MisDeprnAssetsQueryModel modelProducer = (MisDeprnAssetsQueryModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getPageQueryModel();
			String reportTitle = "MIS资产折旧查询";
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
		fieldMap.put("BOOK_TYPE_CODE", "资产账簿代码");
		fieldMap.put("BOOK_TYPE_NAME", "资产账簿名称");
		fieldMap.put("ASSET_ID", "资产唯一标识");
		fieldMap.put("TAG_NUMBER", "资产标签号");
		fieldMap.put("ASSET_NUMBER", "固定资产编号");
		fieldMap.put("FA_CATEGORY1", "应用领域");
		fieldMap.put("FA_CATEGORY2", "资产类别");
		fieldMap.put("DESCRIPTION", "固定资产名称");
		fieldMap.put("SEGMENT1", "所属公司");
		
		fieldMap.put("COST", "固定资产原值");
		fieldMap.put("NET_BOOK_VALUE", "净值");
		fieldMap.put("PTD_IMPAIRMENT", "当月减值准备");
		fieldMap.put("YTD_IMPAIRMENT", "本年减值准备");
		fieldMap.put("IMPAIRMENT_RESERVE", "累计减值准备");
		
		fieldMap.put("PTD_DEPRN", "当月折旧");
		fieldMap.put("YTD_DEPRN", "本年折旧");
		fieldMap.put("DEPRN_RESERVE", "累计折旧");
		fieldMap.put("PERIOD_NAME", "折旧期间");

		fieldMap.put("DEPRN_LEFT_MONTH", "折旧剩余月数");
		fieldMap.put("LAST_UPDATE_DATE", "最后更新日期");
		return fieldMap;
	}
}

