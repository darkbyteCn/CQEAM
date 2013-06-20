package com.sino.ams.print.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.print.dto.BarcodeMaxMaintainDTO;
import com.sino.ams.print.model.BarcodeMaxMaintainModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.db.datatrans.CustomTransData;
import com.sino.base.db.datatrans.DataRange;
import com.sino.base.db.datatrans.DataTransfer;
import com.sino.base.db.datatrans.TransRule;
import com.sino.base.db.datatrans.TransferFactory;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataTransException;
import com.sino.framework.dto.BaseUserDTO;

public class BarcodeMaxMaintainDAO  extends AMSBaseDAO{
	
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) {
		BarcodeMaxMaintainDTO dtoPara = (BarcodeMaxMaintainDTO)dtoParameter;
		sqlProducer = new BarcodeMaxMaintainModel(userAccount, dtoPara);
	}

	public BarcodeMaxMaintainDAO(SfUserDTO userAccount, BarcodeMaxMaintainDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		barcodMaxMaintainModel = new BarcodeMaxMaintainModel((SfUserDTO) userAccount, dtoParameter);
	}
	
	private BarcodeMaxMaintainModel barcodMaxMaintainModel = null;
	
   /**
	 * 功能：导出Excel文件。
	 * @return File
	 * @throws com.sino.base.exception.DataTransException
	 *
	 */
	public File exportFile() throws DataTransException {     
		File file = null;
			BarcodeMaxMaintainModel  barcodeMaxMaintainModel =(BarcodeMaxMaintainModel)sqlProducer;
			SQLModel sqlModel = barcodeMaxMaintainModel.getBarcodeMaxMaintainModel();
			TransRule rule = new TransRule();
			rule.setDataSource(sqlModel);
			rule.setCalPattern(CalendarConstant.LINE_PATTERN);
			rule.setSourceConn(conn);
			String fileName = "标签号规则定制维护.xls";
			String filePath = WorldConstant.USER_HOME;
			filePath += WorldConstant.FILE_SEPARATOR;
			filePath += fileName;
			rule.setTarFile(filePath);
			DataRange range = new DataRange();
			rule.setDataRange(range);

			Map fieldMap = new HashMap();
			fieldMap.put("COMPANY_ID", "公司代码");
			fieldMap.put("COMPANY_NAME", "公司名称");
			fieldMap.put("TAG_SEQ", "最大可用标签号");
			fieldMap.put("ASSETS_TYPE", "标签类型");
			
			rule.setFieldMap(fieldMap);

			CustomTransData custData = new CustomTransData();
			custData.setReportTitle("标签号规则定制");
			custData.setReportPerson(userAccount.getUsername());
			custData.setNeedReportDate(true);
			rule.setCustData(custData);
			TransferFactory factory = new TransferFactory();
			DataTransfer transfer = factory.getTransfer(rule);
			transfer.transData();
			file = (File) transfer.getTransResult();
		return file;
	}
}
