package com.sino.ams.prematch.dao;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import oracle.jdbc.driver.OracleTypes;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.constant.CustMessageKey;
import com.sino.ams.prematch.dto.AmsPaMatchDTO;
import com.sino.ams.prematch.model.AutoMatchModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.datatrans.CustomTransData;
import com.sino.base.db.datatrans.DataRange;
import com.sino.base.db.datatrans.DataTransfer;
import com.sino.base.db.datatrans.TransRule;
import com.sino.base.db.datatrans.TransferFactory;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.DataTransException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;

public class AutoMatchDAO extends AMSBaseDAO {
	public AutoMatchDAO(SfUserDTO userAccount, AmsPaMatchDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器baseSQLProducer的初始化。具体的DAO继承时初始化具体的SQL生成器。
	 *
	 * @param userAccount BaseUserDTO
	 * @param dtoParameter DTO
	 * @todo Implement this com.sino.framework.dao.BaseDAO method
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		SfUserDTO user = (SfUserDTO)userAccount;
		AmsPaMatchDTO dto = (AmsPaMatchDTO)dtoParameter;
		sqlProducer = new AutoMatchModel(user, dto);
	}

	/**
	 * 功能：导出转资准备清单到Excel文件
	 * @return File
	 * @throws DataTransException
	 */
	public File getExportFile() throws DataTransException {
		File file = null;
		AutoMatchModel modelProducer = (AutoMatchModel) sqlProducer;
		SQLModel sqlModel = modelProducer.getPageQueryModel();
		String reportTitle = userAccount.getCompany() + "待自动匹配清单";
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
	 * 功能：获取转资准备清单导出字段映射
	 * @return Map
	 */
	private Map getFieldMap(){
		Map fieldMap = new HashMap();
		fieldMap.put("BARCODE", "EAM标签");
		fieldMap.put("ITEM_NAME", "EAM名称");
		fieldMap.put("ITEM_SPEC", "EAM型号");
		fieldMap.put("PROJECT_NUMBER_AMS", "EAM项目编号");
		fieldMap.put("PROJECT_NAME_AMS", "EAM项目名称");
		fieldMap.put("WORKORDER_OBJECT_CODE", "EAM地点代码");
		fieldMap.put("WORKORDER_OBJECT_NAME", "EAM地点名称");
		fieldMap.put("EMPLOYEE_NUMBER", "EAM员工编号");
		fieldMap.put("USER_NAME", "EAM责任人");
		fieldMap.put("START_DATE", "EAM启用日期");

		fieldMap.put("TAG_NUMBER", "PA标签");
		fieldMap.put("ASSETS_DESCRIPTION", "PA名称");
		fieldMap.put("MODEL_NUMBER", "PA型号");
		fieldMap.put("PROJECT_NUMBER", "PA项目编号");
		fieldMap.put("PROJECT_NAME", "PA项目名称");
		fieldMap.put("ASSETS_LOCATION_CODE", "PA地点代码");
		fieldMap.put("ASSETS_LOCATION", "PA地点名称");
		fieldMap.put("ASSIGNED_TO_NUMBER", "PA员工编号");
		fieldMap.put("ASSIGNED_TO_NAME", "PA责任人");
		fieldMap.put("DATE_PLACED_IN_SERVICE", "PA启用日期");

		return fieldMap;
	}

	/**
	 * 功能：自动匹配(多选匹配)
	 * @param systemIds String[]
	 * @param tagNumbers String[]
	 * @throws DataHandleException
	 */
	public void matchItems(String[] systemIds, String[] tagNumbers) throws DataHandleException {
		boolean operateResult = false;
		boolean autoCommit = true;
		CallableStatement cStmt = null;
		try {
			autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			AmsPaMatchDAO matchDAO = new AmsPaMatchDAO(userAccount, null, conn);
			AmsPaMatchDTO dto = null;
			cStmt = conn.prepareCall("{CALL AMS_PA_IMPORT_PKG.SYN_PA_DATA(?, ?, ?)}");
			for (int i = 0; i < systemIds.length; i++) {
				dto = new AmsPaMatchDTO();
				dto.setSystemId(systemIds[i]);
				dto.setTagNumber(tagNumbers[i]);
				dto.setRemark("自动匹配");
				setDTOParameter(dto);
				if(StrUtil.isEmpty(systemIds[i])){
					importPaAssets(cStmt);
					dto = (AmsPaMatchDTO) dtoParameter;
				}
				matchDAO.setDTOParameter(dto);
				matchDAO.createData();
			}
			operateResult = true;
		} catch (Exception ex) {
			Logger.logError(ex);
		} finally{
			try {
				if (operateResult) {
					prodMessage(CustMessageKey.MATCH_SUCCESS);
					conn.commit();
				} else {
					conn.rollback();
					prodMessage(CustMessageKey.MATCH_FAILURE);
				}
				conn.setAutoCommit(autoCommit);
				DBManager.closeDBStatement(cStmt);
				message.setIsError(!operateResult);
			} catch (SQLException ex1) {
				Logger.logError(ex1);
			}
		}
	}

	/**
	 * 功能：导PA资产到ETS_ITEM_INFO表
	 * @param cStmt CallableStatement
	 * @throws SQLException
	 */
	private void importPaAssets(CallableStatement cStmt) throws SQLException{
		AmsPaMatchDTO dto = (AmsPaMatchDTO) dtoParameter;
		cStmt.registerOutParameter(1, OracleTypes.NUMBER);
		cStmt.setString(2, dto.getTagNumber());
		cStmt.setInt(3, userAccount.getUserId());
		cStmt.execute();
		dto.setSystemId(StrUtil.nullToString(cStmt.getString(1)));
		setDTOParameter(dto);
	}
}
