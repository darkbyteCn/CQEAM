package com.sino.ams.match.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.match.dto.EtsItemMatchMisDTO;
import com.sino.ams.match.model.EtsItemMatchMisModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.db.datatrans.CustomTransData;
import com.sino.base.db.datatrans.DataRange;
import com.sino.base.db.datatrans.DataTransfer;
import com.sino.base.db.datatrans.TransRule;
import com.sino.base.db.datatrans.TransferFactory;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.DataTransException;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: Suhp
 * Date: 2007-11-26
 * Time: 20:18:58
 * To change this template use File | Settings | File Templates.
 */

public class EtsItemMatchMisDAO extends BaseDAO {
	private SfUserDTO sfUser = null;
	private EtsItemMatchMisModel itemMatchMis = null;

	public EtsItemMatchMisDAO(SfUserDTO userAccount, EtsItemMatchMisDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		sfUser = userAccount;
		itemMatchMis = new EtsItemMatchMisModel((SfUserDTO) userAccount, dtoParameter);
	}

	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		EtsItemMatchMisDTO dtoPara = (EtsItemMatchMisDTO) dtoParameter;
		super.sqlProducer = new EtsItemMatchMisModel((SfUserDTO) userAccount, dtoPara);

	}

	/**
	 * 功能：导出已屏蔽ETS设备Excel文件。
	 *
	 * @return File
	 * @throws com.sino.base.exception.DataTransException
	 *
	 */
	public File exportEtsFile() throws DataTransException {
		File file = null;
//        try {
//            SQLModel sqlModel = sqlProducer.getPageQueryModel();
		SQLModel sqlModel = itemMatchMis.getPageQueryModel();
		TransRule rule = new TransRule();
		rule.setDataSource(sqlModel);
		rule.setCalPattern(CalendarConstant.LINE_PATTERN);
		rule.setSourceConn(conn);
		String fileName = "已屏蔽设备清单.xls";
		String filePath = WorldConstant.USER_HOME;
		filePath += WorldConstant.FILE_SEPARATOR;
		filePath += fileName;
		rule.setTarFile(filePath);
		DataRange range = new DataRange();
		rule.setDataRange(range);

		Map fieldMap = new HashMap();
		fieldMap.put("BARCODE", "标签号");
		fieldMap.put("ITEM_NAME", "设备名称");
		fieldMap.put("ITEM_SPEC", "规格型号");
		fieldMap.put("ITEM_CATEGORY", "设备专业");
		fieldMap.put("WORKORDER_OBJECT_CODE", "地点代码");
		fieldMap.put("WORKORDER_OBJECT_NAME", "地点简称");
		fieldMap.put("COST_CENTER_NAME", "成本中心");
		rule.setFieldMap(fieldMap);

		CustomTransData custData = new CustomTransData();
		custData.setReportTitle("已屏蔽设备报表");
		custData.setReportPerson(sfUser.getUsername());
		custData.setNeedReportDate(true);
		rule.setCustData(custData);
		TransferFactory factory = new TransferFactory();
		DataTransfer transfer = factory.getTransfer(rule);
		transfer.transData();
		file = (File) transfer.getTransResult();
//        } catch (SQLModelException ex) {
//            ex.printLog();
//            throw new DataTransException(ex);
//        }
		return file;
	}

	/**
	 * 功能：导出已屏蔽Mis资产Excel文件。
	 *
	 * @return File
	 * @throws com.sino.base.exception.DataTransException
	 *
	 */
	public File exportMisFile() throws DataTransException {
		File file = null;
//        try {
//            SQLModel sqlModel = sqlProducer.getPageQueryModel();
			SQLModel sqlModel = itemMatchMis.getMisPageQueryModel();
			TransRule rule = new TransRule();
			rule.setDataSource(sqlModel);
			rule.setCalPattern(CalendarConstant.LINE_PATTERN);
			rule.setSourceConn(conn);
			String fileName = "已屏蔽资产清单.csv";
			String filePath = WorldConstant.USER_HOME;
			filePath += WorldConstant.FILE_SEPARATOR;
			filePath += fileName;
			rule.setTarFile(filePath);
			DataRange range = new DataRange();
			rule.setDataRange(range);

			Map fieldMap = new HashMap();
			fieldMap.put("TAG_NUMBER", "资产条码");
			fieldMap.put("ASSETS_DESCRIPTION", "资产名称");
			fieldMap.put("MODEL_NUMBER", "资产型号");
			fieldMap.put("FA_CATEGORY1", "资产类型1");
			fieldMap.put("FA_CATEGORY2", "资产类型2");
			fieldMap.put("REMARK", "屏蔽原因");
			rule.setFieldMap(fieldMap);

			CustomTransData custData = new CustomTransData();
			custData.setReportTitle("已屏蔽资产报表");
			custData.setReportPerson(sfUser.getUsername());
			custData.setNeedReportDate(true);
			rule.setCustData(custData);
			TransferFactory factory = new TransferFactory();
			DataTransfer transfer = factory.getTransfer(rule);
			transfer.transData();
			file = (File) transfer.getTransResult();
//        } catch (SQLModelException ex) {
//            ex.printLog();
//            throw new DataTransException(ex);
//        }
		return file;
	}

	/**
	 * 功能：撤消被屏蔽的ETS设备。
	 *
	 * @ File
	 * @throws com.sino.base.exception.DataTransException
	 *
	 */
	public void repealEtsData(String[] systemids) throws DataHandleException {
		EtsItemMatchMisModel etsItemMatchModel = (EtsItemMatchMisModel) sqlProducer;
		SQLModel sqlModel = etsItemMatchModel.getDisabledEtsModel(systemids);
		DBOperator.updateRecord(sqlModel, conn);
	}

	/**
	 * 功能：撤消被屏蔽的MIS资产。
	 *
	 * @ File
	 * @throws com.sino.base.exception.DataTransException
	 *
	 */
	public void repealMisData(String[] assetIds) throws DataHandleException {
		EtsItemMatchMisModel etsItemMatchModel = (EtsItemMatchMisModel) sqlProducer;
		SQLModel sqlModel = etsItemMatchModel.getDisabledMisModel(assetIds);
		DBOperator.updateRecord(sqlModel, conn);
	}

}
