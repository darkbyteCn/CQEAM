package com.sino.ams.dzyh.dao;


import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.dzyh.constant.DzyhActionConstant;
import com.sino.ams.dzyh.constant.LvecDicts;
import com.sino.ams.dzyh.constant.LvecMessageKeys;
import com.sino.ams.dzyh.dto.EamYbBorrowLogDTO;
import com.sino.ams.dzyh.model.EamYbBorrowLogModel;
import com.sino.base.constant.WorldConstant;
import com.sino.base.db.datatrans.CustomTransData;
import com.sino.base.db.datatrans.DataRange;
import com.sino.base.db.datatrans.DataTransfer;
import com.sino.base.db.datatrans.TransRule;
import com.sino.base.db.datatrans.TransferFactory;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;


/**
 * <p>Title: EamYbBorrowLogDAO</p>
 * <p>Description:程序自动生成服务程序“EamYbBorrowLogDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class EamYbBorrowLogDAO extends AMSBaseDAO {

	/**
	 * 功能：仪器仪表借用日志 EAM_YB_BORROW_LOG 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EamYbBorrowLogDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public EamYbBorrowLogDAO(BaseUserDTO userAccount, EamYbBorrowLogDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) {
		EamYbBorrowLogDTO dto = (EamYbBorrowLogDTO)dtoParameter;
		sqlProducer = new EamYbBorrowLogModel(userAccount, dto);
	}

	/**
	 * 功能：根据选中条码的新建借用单
	 * @return EamYbBorrowLogDTO
	 * @throws QueryException
	 */
	public EamYbBorrowLogDTO getBarcodeApply() throws QueryException {
		EamYbBorrowLogDTO newApply = null;
		EamYbBorrowLogModel modelProducer = (EamYbBorrowLogModel)sqlProducer;
		SQLModel sqlModel = modelProducer.getBarcodeApplyModel();
		SimpleQuery simp = new SimpleQuery(sqlModel, conn);
		simp.setDTOClassName(EamYbBorrowLogDTO.class.getName());
		simp.executeQuery();
		if(simp.hasResult()){
			newApply = (EamYbBorrowLogDTO)simp.getFirstDTO();
		} else {
			EamYbBorrowLogDTO dto = (EamYbBorrowLogDTO)dtoParameter;

			prodMessage(LvecMessageKeys.ITEM_APPLY_NOT_AVAIABLE);
			message.addParameterValue(dto.getBarcode());
			message.setIsError(true);
			message.setNeedClose(true);
		}
		return newApply;
	}

	/**
	 * 功能：保存借用单(含暂存、提交)
	 * @return boolean
	 */
	public boolean saveBorrowApply(){
		boolean operateResult = false;
		EamYbBorrowLogDTO dto = (EamYbBorrowLogDTO) dtoParameter;
		String borrowLogId = dto.getBorrowLogId();
		String act = dto.getAct();
		boolean isNew = borrowLogId.equals("");
		try {
			if(act.equals(DzyhActionConstant.SAVE_ACTION)){
				dto.setStatus(LvecDicts.YB_BR_STATUS1_ADD);
			} else if(act.equals(DzyhActionConstant.SUBMIT_ACTION)){
				dto.setStatus(LvecDicts.YB_BR_STATUS1_WAPPROVE);
			}
			if (isNew) {
				SeqProducer sepPrd = new SeqProducer(conn);
				//TODO
				borrowLogId = "" + sepPrd.getStrNextSeq("EAM_YB_BORROW_LOG");
				dto.setBorrowLogId(borrowLogId);
				setDTOParameter(dto);
				createData();
			} else {
				setDTOParameter(dto);
				updateData();
			}
			operateResult = true;
		} catch (DataHandleException ex) {
			ex.printLog();
		} catch (SQLException ex) {
			Logger.logError(ex);
		} finally{
			if(operateResult){
				if (act.equals(DzyhActionConstant.SAVE_ACTION)) {
					prodMessage(LvecMessageKeys.BORROW_APPLY_SAVE_SUCCESS);
				} else if (act.equals(DzyhActionConstant.SUBMIT_ACTION)) {
					prodMessage(LvecMessageKeys.BORROW_APPLY_SUBMIT_SUCCESS);
				}
			} else {
				dto.setBorrowLogId("");
				if (act.equals(DzyhActionConstant.SAVE_ACTION)) {
					prodMessage(LvecMessageKeys.BORROW_APPLY_SAVE_FAILURE);
				} else if (act.equals(DzyhActionConstant.SUBMIT_ACTION)) {
					prodMessage(LvecMessageKeys.BORROW_APPLY_SUBMIT_FAILURE);
				}
				message.setIsError(true);
				message.setNeedClose(true);
			}
			message.addParameterValue(dto.getBarcode());
			if (act.equals(DzyhActionConstant.SUBMIT_ACTION)) {
				message.addParameterValue(dto.getGroupName());
			}
		}
		return operateResult;
	}


	/**
	 * 功能：撤销仪器仪表借用申请
	 * @return boolean
	 */
	public boolean cancelBorrowApply(){
		boolean operateResult = false;
		EamYbBorrowLogDTO dto = (EamYbBorrowLogDTO) dtoParameter;
		try {
			dto.setStatus(LvecDicts.YB_BR_STATUS1_CANCEL);
			setDTOParameter(dto);
			EamYbBorrowLogModel modelProducer = (EamYbBorrowLogModel)sqlProducer;
			SQLModel sqlModel = modelProducer.getBorrowCancelModel();
			DBOperator.updateRecord(sqlModel, conn);
			operateResult = true;
		} catch (DataHandleException ex) {
			ex.printLog();
		} finally{
			if(operateResult){
				prodMessage(LvecMessageKeys.BORROW_APPLY_CANCEL_SUCCESS);
			} else {
				prodMessage(LvecMessageKeys.BORROW_APPLY_CANCEL_FAILURE);
				message.setIsError(true);
				message.setNeedClose(true);
			}
			message.addParameterValue(dto.getBarcode());
		}
		return operateResult;
	}

	/**
	 * 功能：导出查询SQL资产数据
	 * @return String 返回导出Excel文件
	 * @throws com.sino.base.exception.WebFileDownException
	 */
	public File getExportFile() throws WebFileDownException {
		File file = null;
		EamYbBorrowLogModel modelProducer = null;
		try {
			modelProducer = (EamYbBorrowLogModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getPageQueryModel();
			String reportTitle = "仪表设备借用申请情况";
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
		} catch (DataTransException ex) {
			ex.printLog();
			throw new WebFileDownException(ex);
		}
		return file;
	}

	private Map getFieldMap(){
		Map fieldMap = new HashMap();

		fieldMap.put("BARCODE", "仪表条码");
		fieldMap.put("ITEM_NAME", "仪表名称");
		fieldMap.put("ITEM_SPEC", "仪表型号");
		fieldMap.put("CATALOG_NAME", "目录名称");

		fieldMap.put("WORKORDER_OBJECT_NAME", "所在地点");
		fieldMap.put("START_DATE", "启用日期");
		fieldMap.put("RESPONSIBILITY_DEPT_NAME", "领用部门");
		fieldMap.put("RESPONSIBILITY_USER_NAME", "领用人");
		fieldMap.put("MAINTAIN_USER", "保管人");

		fieldMap.put("IS_APPLYED", "申请状态");

		fieldMap.put("BORROW_DATE", "申请日期");
		fieldMap.put("RETURN_DATE_PLAN", "预计归还日期");
		fieldMap.put("BORROW_USER", "申请人");

		return fieldMap;
	}
}
