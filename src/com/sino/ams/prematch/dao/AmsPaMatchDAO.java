package com.sino.ams.prematch.dao;


import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.constant.AMSActionConstant;
import com.sino.ams.constant.CustMessageKey;
import com.sino.ams.prematch.dto.AmsPaMatchDTO;
import com.sino.ams.prematch.dto.AmsPaMatchLogDTO;
import com.sino.ams.prematch.model.AmsPaMatchModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.datatrans.CustomTransData;
import com.sino.base.db.datatrans.DataRange;
import com.sino.base.db.datatrans.DataTransfer;
import com.sino.base.db.datatrans.TransRule;
import com.sino.base.db.datatrans.TransferFactory;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.ReflectException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.log.Logger;
import com.sino.base.util.CalendarUtil;
import com.sino.base.util.ReflectionUtil;
import com.sino.framework.dto.BaseUserDTO;


/**
 * <p>Title: AmsPaMatchDAO</p>
 * <p>Description:程序自动生成服务程序“AmsPaMatchDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class AmsPaMatchDAO extends AMSBaseDAO {

	/**
	 * 功能：EAM系统资产实物与MIS转资准备清单预匹配 AMS_PA_MATCH 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsPaMatchDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public AmsPaMatchDAO(SfUserDTO userAccount, AmsPaMatchDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) {
		SfUserDTO user = (SfUserDTO)userAccount;
		AmsPaMatchDTO dto = (AmsPaMatchDTO)dtoParameter;
		sqlProducer = new AmsPaMatchModel(user, dto);
	}

	/**
	 * 功能：插入匹配数据，同时记录匹配日志
	 * @throws DataHandleException
	 */
	public void createData() throws DataHandleException{
		try {
			AmsPaMatchDTO dto = (AmsPaMatchDTO) dtoParameter;
			AmsPaMatchLogDTO logDTO = new AmsPaMatchLogDTO();
			ReflectionUtil.copyData(dto, logDTO);
			logDTO.setMatchedBy(userAccount.getUserId());
			logDTO.setMatchedDate(CalendarUtil.getCurrCalendar());
			logDTO.setOrganizationId(userAccount.getOrganizationId());
			logDTO.setAct(AMSActionConstant.CREATE_ACTION);
			super.createData();
			AmsPaMatchLogDAO logDAO = new AmsPaMatchLogDAO(userAccount, logDTO, conn);
			logDAO.createData();
		} catch (CalendarException ex) {
			ex.printLog();
			throw new DataHandleException(ex);
		} catch (ReflectException ex) {
			ex.printLog();
			throw new DataHandleException(ex);
		}
	}

	/**
	 * 功能：导出已匹配清单到Excel文件
	 * @return File
	 * @throws DataTransException
	 */
	public File getExportFile() throws DataTransException {
		File file = null;
		try {
			AmsPaMatchModel modelProducer = (AmsPaMatchModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getPageQueryModel();
			String reportTitle = userAccount.getCompany() + "已匹配清单";
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

	/**
	 * 功能：获取已匹配清单导出字段映射
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

		fieldMap.put("TAG_NUMBER", "PA标签");
		fieldMap.put("ASSETS_DESCRIPTION", "PA名称");
		fieldMap.put("MODEL_NUMBER", "PA型号");
		fieldMap.put("PROJECT_NUMBER", "PA项目编号");
		fieldMap.put("PROJECT_NAME", "PA项目名称");
		fieldMap.put("ASSETS_LOCATION_CODE", "PA地点代码");
		fieldMap.put("ASSETS_LOCATION", "PA地点名称");
		fieldMap.put("ASSIGNED_TO_NUMBER", "PA员工编号");
		fieldMap.put("ASSIGNED_TO_NAME", "PA责任人");

		fieldMap.put("USERNAME", "匹配人");
		fieldMap.put("CREATION_DATE", "匹配时间");
		fieldMap.put("REMARK", "匹配备注");
		return fieldMap;
	}

	/**
	 * 功能：获取选中的匹配关系中不可删除的匹配关系
	 * @param systemIds String[]
	 * @return boolean
	 * @throws QueryException
	 */
	private String getCanNotDelMathes(String[] systemIds) throws QueryException {
		String canNotDelMatches = "";
		try {
			AmsPaMatchModel modelProducer = (AmsPaMatchModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getCanDeleteModel(systemIds);
			SimpleQuery simp = new SimpleQuery(sqlModel, conn);
			simp.executeQuery();
			if (simp.hasResult()) {
				RowSet rows = simp.getSearchResult();
				Row row = null;
				for (int i = 0; i < rows.getSize(); i++) {
					row = rows.getRow(i);
					canNotDelMatches += row.getValue("BARCODE") + ":";
					canNotDelMatches += row.getValue("TAG_NUMBER") + "&nbsp;";
				}
			}
		} catch (ContainerException ex) {
			ex.printLog();
			throw new QueryException(ex);
		}
		return canNotDelMatches;
	}

	/**
	 * 功能：自动匹配(多选匹配)
	 * @param systemIds String[]
	 * @throws DataHandleException
	 */
	public void deleteMatchs(String[] systemIds) throws DataHandleException {
		boolean operateResult = false;
		boolean autoCommit = true;
		String canNotDelMatches = "";
		try {
			canNotDelMatches = getCanNotDelMathes(systemIds);
			if(canNotDelMatches.equals("")){
				autoCommit = conn.getAutoCommit();
				conn.setAutoCommit(false);
				AmsPaMatchModel modelProducer = (AmsPaMatchModel) sqlProducer;
				SQLModel sqlModel = modelProducer.getDeleteMatchLogModel(systemIds);
				DBOperator.updateRecord(sqlModel, conn);
				AmsPaMatchDTO dto = null;
				for (int i = 0; i < systemIds.length; i++) {
					dto = new AmsPaMatchDTO();
					dto.setSystemId(systemIds[i]);
					setDTOParameter(dto);
					deleteByPrimaryKey();
				}
				operateResult = true;
			}
		} catch (Exception ex) {
			Logger.logError(ex);
		} finally{
			try {
				if(canNotDelMatches.equals("")){
					if (operateResult) {
						prodMessage(CustMessageKey.DELETE_MATCH_SUCCESS);
						conn.commit();
					} else {
						conn.rollback();
						prodMessage(CustMessageKey.DELETE_MATCH_FAILURE);
					}
					conn.setAutoCommit(autoCommit);
				} else {
					prodMessage(CustMessageKey.CAN_NOT_DELETE);
					message.addParameterValue(canNotDelMatches);
				}
				message.setIsError(!operateResult);
			} catch (SQLException ex1) {
				Logger.logError(ex1);
			}
		}
	}
}
