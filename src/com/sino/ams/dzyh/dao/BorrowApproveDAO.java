package com.sino.ams.dzyh.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.dzyh.constant.LvecDicts;
import com.sino.ams.dzyh.constant.LvecMessageKeys;
import com.sino.ams.dzyh.dto.EamYbBorrowLogDTO;
import com.sino.ams.dzyh.model.BorrowApproveModel;
import com.sino.base.constant.WorldConstant;
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
import com.sino.base.exception.QueryException;
import com.sino.base.exception.StrException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.util.StrUtil;
import com.sino.base.web.WebRadio;
import com.sino.framework.dto.BaseUserDTO;


/**
 * <p>Title: BorrowApproveDAO</p>
 * <p>Description:程序自动生成服务程序“EamYbBorrowLogDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class BorrowApproveDAO extends AMSBaseDAO {

	/**
	 * 功能：仪器仪表借用日志 EAM_YB_BORROW_LOG 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EamYbBorrowLogDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public BorrowApproveDAO(BaseUserDTO userAccount, EamYbBorrowLogDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) {
		EamYbBorrowLogDTO dto = (EamYbBorrowLogDTO)dtoParameter;
		sqlProducer = new BorrowApproveModel(userAccount, dto);
	}

	public Object getDataByPrimaryKey() throws QueryException {
		Object data = super.getDataByPrimaryKey();
		try {
			if (!StrUtil.isEmpty(dtoClassName)) {
				EamYbBorrowLogDTO dto = (EamYbBorrowLogDTO) data;
				dto.setStatusRadio(getSatusRadio(dto));
				dto.setApproveUserId(userAccount.getUserId());
				dto.setApproveUser(userAccount.getUsername());
				dto.setCurrCalendar("approveDate");
				data = dto;
			}
		} catch (StrException ex) {
			ex.printLog();
			throw new QueryException(ex);
		}
		return data;
	}

	/**
	 * 功能：产生审批单选按钮
	 * @param dto EamYbBorrowLogDTO
	 * @return String
	 * @throws StrException
	 */
	private String getSatusRadio(EamYbBorrowLogDTO dto) throws StrException {
		StringBuffer approveRadio = new StringBuffer();
		WebRadio radio = new WebRadio("status");
		radio.addValueCaption(LvecDicts.YB_BR_STATUS1_APPROVE, "通过");
		radio.addValueCaption(LvecDicts.YB_BR_STATUS1_NAPPRO, "不通过");
		radio.setCheckedValue(dto.getStatus());
		approveRadio.append(radio);
		return approveRadio.toString();
	}


	/**
	 * 功能：导出查询SQL资产数据
	 * @return String 返回导出Excel文件
	 * @throws com.sino.base.exception.WebFileDownException
	 */
	public File getExportFile() throws WebFileDownException {
		File file = null;
		BorrowApproveModel modelProducer = null;
		try {
			modelProducer = (BorrowApproveModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getPageQueryModel();
			String reportTitle = "待审批仪表设备借用申请待审批";
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
		fieldMap.put("TASK_NAME", "任务名称");
		fieldMap.put("START_DATE", "起始日期");
		fieldMap.put("END_DATE", "截至日期");
		fieldMap.put("TASK_STATUS_VALUE", "任务状态");
		fieldMap.put("CREATION_DATE", "创建日期");
		return fieldMap;
	}
	/**
	 * 功能：审批仪器仪表借用申请
	 * @return boolean
	 */
	public boolean approveBorrowApply(){
		boolean operateResult = false;
		try {
			BorrowApproveModel modelProducer = (BorrowApproveModel)sqlProducer;
			SQLModel sqlModel = modelProducer.getBorrowApproveModel();
			DBOperator.updateRecord(sqlModel, conn);
			operateResult = true;
		} catch (DataHandleException ex) {
			ex.printLog();
		} finally{
			if(operateResult){
				prodMessage(LvecMessageKeys.BORROW_APPLY_APPROVE_SUCCESS);
			} else {
				prodMessage(LvecMessageKeys.BORROW_APPLY_APPROVE_FAILURE);
				message.setIsError(true);
			}
			EamYbBorrowLogDTO dto = (EamYbBorrowLogDTO) dtoParameter;
			message.addParameterValue(dto.getBarcode());
			message.setNeedClose(true);
		}
		return operateResult;
	}
}
