package com.sino.ams.newasset.report.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.dto.AmsAssetsCheckHeaderDTO;
import com.sino.ams.newasset.report.model.GroupCheckDetailModel;
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
import com.sino.framework.dto.BaseUserDTO;

public class GroupCheckDetailDAO extends AMSBaseDAO {

	public GroupCheckDetailDAO(SfUserDTO userAccount, AmsAssetsCheckHeaderDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器baseSQLProducer的初始化。具体的DAO继承时初始化具体的SQL生成器。
	 *
	 * @param userAccount BaseUserDTO
	 * @param dtoParameter DTO
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO)dtoParameter;
		sqlProducer = new GroupCheckDetailModel((SfUserDTO)userAccount, dto);
	}


	/**
	 * 功能：获取第一个扫描地点
	 * @return AmsAssetsCheckHeaderDTO
	 * @throws QueryException
	 */
	public AmsAssetsCheckHeaderDTO getFirstScanLocation() throws QueryException {
		AmsAssetsCheckHeaderDTO firstDTO = new AmsAssetsCheckHeaderDTO();
		try {
			GroupCheckDetailModel modelProducer = (GroupCheckDetailModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getPageQueryModel();
			SimpleQuery simp = new SimpleQuery(sqlModel, conn);
			simp.setDTOClassName(AmsAssetsCheckHeaderDTO.class.getName());
			simp.executeQuery();
			if(simp.hasResult()){
				firstDTO = (AmsAssetsCheckHeaderDTO)simp.getFirstDTO();
			}
		} catch (SQLModelException ex) {
			ex.printLog();
			throw new QueryException(ex);
		}
		return firstDTO;
	}

	/**
	 * 功能：获取部门盘点设备明细
	 * @return RowSet
	 * @throws QueryException
	 */
	public RowSet getScanedItems() throws QueryException {
		RowSet rows = new RowSet();
		try {
			GroupCheckDetailModel modelProducer = (GroupCheckDetailModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getItemsModel();
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
	 * 功能：获取部门盘点设备明细Excel文件
	 * @return File
	 * @throws DataTransException
	 */
	public File getExportFile() throws DataTransException {
		File file = null;
		try {
			AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO)dtoParameter;
			GroupCheckDetailModel modelProducer = (GroupCheckDetailModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getItemsModel();
			String reportTitle = "地点"
								 + dto.getCheckLocationName()
								 + "扫描设备";
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
			fieldMap.put("BARCODE", "标签号");
			fieldMap.put("ITEM_CATEGORY", "设备专业");
			fieldMap.put("ITEM_NAME", "设备名称");
			fieldMap.put("ITEM_SPEC", "设备型号");
			fieldMap.put("ITEM_UNIT", "计量单位");
			fieldMap.put("WORKORDER_OBJECT_LOCATION", "当前地点");
			fieldMap.put("USER_NAME", "责任人");
			fieldMap.put("DEPT_NAME", "责任部门");
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
