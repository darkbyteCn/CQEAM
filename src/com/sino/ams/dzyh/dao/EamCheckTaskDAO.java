package com.sino.ams.dzyh.dao;


import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.dzyh.constant.DzyhActionConstant;
import com.sino.ams.dzyh.constant.LvecMessageKeys;
import com.sino.ams.dzyh.dto.EamCheckTaskDTO;
import com.sino.ams.dzyh.model.EamCheckTaskModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.db.datatrans.CustomTransData;
import com.sino.base.db.datatrans.DataRange;
import com.sino.base.db.datatrans.DataTransfer;
import com.sino.base.db.datatrans.TransRule;
import com.sino.base.db.datatrans.TransferFactory;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.util.DataEmptyChecker;
import com.sino.base.db.util.DataLengthChecker;
import com.sino.base.db.util.DataUniqueChecker;
import com.sino.base.db.util.DataValidChecker;
import com.sino.base.db.util.NumberChecker;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.DTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.exception.ValidateException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.framework.dto.BaseUserDTO;


/**
 * <p>Title: EamCheckTaskDAO</p>
 * <p>Description:程序自动生成服务程序“EamCheckTaskDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */

public class EamCheckTaskDAO extends AMSBaseDAO {

	/**
	 * 功能：盘点任务表 EAM_CHECK_TASK 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EamCheckTaskDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public EamCheckTaskDAO(SfUserDTO userAccount, EamCheckTaskDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) {
		EamCheckTaskDTO dto = (EamCheckTaskDTO)dtoParameter;
		sqlProducer = new EamCheckTaskModel((SfUserDTO)userAccount, dto);
	}

	/**
	 * 功能：保存任务
	 * @return boolean
	 */
	public boolean saveTask() {
		boolean operateResult = false;
		EamCheckTaskDTO dto = (EamCheckTaskDTO)dtoParameter;
		boolean isNewTask = dto.getCheckTaskId().equals("");
		boolean validData = false;
		try {
			validData = isDataValid(isNewTask);
			if(validData){
				if (isNewTask) {
					SeqProducer seqPrd = new SeqProducer(conn);
//					dto.setCheckTaskId( seqPrd.getStrNextSeq("EAM_CHECK_TASK") );
					//TODO
					dto.setCheckTaskId( "" + seqPrd.getStrNextSeq("EAM_CHECK_TASK") );
					setDTOParameter(dto);
					createData();
				} else {
					updateData();
				}
			}
			operateResult = true;
		} catch (DataHandleException ex) {
			ex.printLog();
		} catch (SQLException ex) {
			Logger.logError(ex);
		} finally{
			if(validData){
				prodTaskSaveMsg(operateResult, isNewTask);
			}
		}
		return operateResult;
	}

	/**
	 * 功能：为保存任务时产生消息
	 * @param operateResult boolean
	 * @param isNewTask boolean
	 */
	private void prodTaskSaveMsg(boolean operateResult, boolean isNewTask) {
		EamCheckTaskDTO dto = (EamCheckTaskDTO)dtoParameter;
		String act = dto.getAct();
		if(operateResult){
			if(isNewTask){
				if(act.equals(DzyhActionConstant.SAVE_ACTION)){
					prodMessage(LvecMessageKeys.TASK_CREATE_SUCCESS);
				} else if(act.equals(DzyhActionConstant.SUBMIT_ACTION)){
					prodMessage(LvecMessageKeys.TASK_SUBMIT_SUCCESS);
				}
			} else {
				if(act.equals(DzyhActionConstant.SAVE_ACTION)){
					prodMessage(LvecMessageKeys.TASK_UPDATE_SUCCESS);
				} else if(act.equals(DzyhActionConstant.SUBMIT_ACTION)){
					prodMessage(LvecMessageKeys.TASK_SUBMIT_SUCCESS);
				}
			}
		} else {
			if(isNewTask){
				dto.setCheckTaskId("");
				setDTOParameter(dto);
				if(act.equals(DzyhActionConstant.SAVE_ACTION)){
					prodMessage(LvecMessageKeys.TASK_CREATE_FAILURE);
				} else if(act.equals(DzyhActionConstant.SUBMIT_ACTION)){
					prodMessage(LvecMessageKeys.TASK_SUBMIT_FAILURE);
				}
			} else {
				if(act.equals(DzyhActionConstant.SAVE_ACTION)){
					prodMessage(LvecMessageKeys.TASK_UPDATE_FAILURE);
				} else if(act.equals(DzyhActionConstant.SUBMIT_ACTION)){
					prodMessage(LvecMessageKeys.TASK_SUBMIT_FAILURE);
				}
			}
			message.setIsError(true);
		}
		message.addParameterValue(dto.getTaskName());
	}

	/**
	 * 功能：检查数据的合法性
	 * @param isNewTask boolean
	 * @return boolean
	 */
	private boolean isDataValid(boolean isNewTask){
		boolean isValid = true;
		try {
			EamCheckTaskDTO dto = (EamCheckTaskDTO) dtoParameter;
			String tableName = "EAM_CHECK_TASK";
			DataValidChecker checker = new DataEmptyChecker(tableName, dto, conn);
			checker.setServletConfig(servletConfig);
			checker.setAnswerAsComment(true);
			isValid = checker.isDataValid();
			if(!isValid){
				prodMessage(LvecMessageKeys.EMPTY_ERROR);
				message.addParameterValue(tableName);
				message.addParameterValue(checker.getInValidData());
			} else {
				checker = new DataLengthChecker(tableName, dto, conn);
				checker.setServletConfig(servletConfig);
				checker.setAnswerAsComment(true);
				isValid = checker.isDataValid();
				if (!isValid) {
					message = new Message();
					message.setMessageValue(checker.getInValidData());
				} else {
					checker = new NumberChecker(tableName, dto, conn);
					checker.setServletConfig(servletConfig);
					checker.setAnswerAsComment(true);
					isValid = checker.isDataValid();
					if (!isValid) {
						message = new Message();
						message.setMessageValue(checker.getInValidData());
					} else {
						DataUniqueChecker uniquer = new DataUniqueChecker(tableName, dto, conn);
						uniquer.setAnswerAsComment(true);
						String dbAction = isNewTask ? "INSERT" : "UPDATE";
						uniquer.setDBAction(dbAction);
						uniquer.setServletConfig(servletConfig);
						isValid = uniquer.isDataValid();
						if (!isValid) {
							prodMessage(LvecMessageKeys.UNIQUE_ERROR);
							message.addParameterValue(uniquer.getInValidData());
						} else {//开始时间－结束时间范围内，同一个任务类型，只能定义一条记录
							isValid = !isDuplicateTask();
							if(!isValid){
								prodMessage(LvecMessageKeys.TASK_DUPDEFINE);
								message.addParameterValue(dto.getStartDate().getCalendarValue());
								message.addParameterValue(dto.getEndDate().getCalendarValue());
								message.addParameterValue(dto.getCheckTypeValue());
								message.addParameterValue(dto.getTaskName());
							}
						}
					}
				}
			}
		} catch (ValidateException ex) {
			ex.printLog();
		} catch (CalendarException ex) {
			ex.printLog();
		} finally{
			if(!isValid){
				message.setIsError(true);
			}
		}
		return isValid;
	}

	/**
	 * 功能：判断当前任务是否重复
	 * @return boolean
	 */
	private boolean isDuplicateTask() {
		boolean isDuplicate = false;
		try {
			EamCheckTaskModel modelProducer = (EamCheckTaskModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getDupicateTaskModel();
			SimpleQuery simp = new SimpleQuery(sqlModel, conn);
			simp.executeQuery();
			isDuplicate = simp.hasResult();
		} catch (QueryException ex) {
			ex.printLog();
		} catch (SQLModelException ex) {
			ex.printLog();
		}
		return isDuplicate;
	}

	/**
	 * 功能：导出查询SQL资产数据
	 * @return String 返回导出Excel文件
	 * @throws com.sino.base.exception.WebFileDownException
	 */
	public File getExportFile() throws WebFileDownException {
		File file = null;
		EamCheckTaskModel modelProducer = null;
		try {
			modelProducer = (EamCheckTaskModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getPageQueryModel();
			String reportTitle = "盘点任务";
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
			throw new WebFileDownException(ex);
		} catch (DataTransException ex) {
			ex.printLog();
			throw new WebFileDownException(ex);
		}
		return file;
	}

	private Map getFieldMap(){
		Map fieldMap = new HashMap();
		fieldMap.put("TASK_NAME", "任务名称");
		fieldMap.put("START_DATE", "起始日期");
		fieldMap.put("END_DATE", "截至日期");
		fieldMap.put("TASK_STATUS_VALUE", "任务状态");
		fieldMap.put("CREATION_DATE", "创建日期");
		return fieldMap;
	}


	/**
	 * 功能：批量取消任务
	 * @param taskIds String[]
	 * @param singleCancel 是否详细信息页面点击的撤销
	 * @throws DataHandleException
	 */
	public void cancelTasks(String[] taskIds, boolean singleCancel) throws DataHandleException{
		boolean operateResult = false;
		EamCheckTaskDTO dto = (EamCheckTaskDTO) dtoParameter;
		try {
			EamCheckTaskModel modelProducer = (EamCheckTaskModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getMulTaskCancelModel(taskIds);
			DBOperator.updateRecord(sqlModel, conn);
			operateResult = true;
		} finally{
			if(operateResult){
				if(singleCancel){
					prodMessage(LvecMessageKeys.TASK_CANCEL_SUCCESS);
					message.addParameterValue(dto.getTaskName());
				} else {
					prodMessage(LvecMessageKeys.TASK_MULCEL_SUCCESS);
				}
			} else {
				if(singleCancel){
					prodMessage(LvecMessageKeys.TASK_CANCEL_FAILURE);
					message.addParameterValue(dto.getTaskName());
				} else {
					prodMessage(LvecMessageKeys.TASK_MULCEL_FAILURE);
				}
			}
		}
	}

	/**
	 * 功能：批量确定任务
	 * @param taskIds String[]
	 * @throws DataHandleException
	 */
	public void submitTasks(String[] taskIds) throws DataHandleException{
		boolean operateResult = false;
		try {
			EamCheckTaskModel modelProducer = (EamCheckTaskModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getMulTaskSubmitModel(taskIds);
			DBOperator.updateRecord(sqlModel, conn);
			operateResult = true;
		} finally{
			if(operateResult){
				prodMessage(LvecMessageKeys.TASK_MULSUB_SUCCESS);
			} else {
				prodMessage(LvecMessageKeys.TASK_MULSUB_FAILURE);
			}
		}
	}
}
