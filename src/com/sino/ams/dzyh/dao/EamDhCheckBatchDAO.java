package com.sino.ams.dzyh.dao;


import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.bean.OrderNumGenerator;
import com.sino.ams.dzyh.constant.DzyhActionConstant;
import com.sino.ams.dzyh.constant.LvecDicts;
import com.sino.ams.dzyh.constant.LvecMessageKeys;
import com.sino.ams.dzyh.constant.LvecWebAttributes;
import com.sino.ams.dzyh.dto.EamDhCheckBatchDTO;
import com.sino.ams.dzyh.dto.EamDhCheckHeaderDTO;
import com.sino.ams.dzyh.dto.EamDhCheckLineDTO;
import com.sino.ams.dzyh.model.EamDhCheckBatchModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.db.datatrans.CustomTransData;
import com.sino.base.db.datatrans.DataRange;
import com.sino.base.db.datatrans.DataTransfer;
import com.sino.base.db.datatrans.TransRule;
import com.sino.base.db.datatrans.TransferFactory;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;


/**
 * <p>Title: EamDhCheckBatchDAO</p>
 * <p>Description:程序自动生成服务程序“EamDhCheckBatchDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */

public class EamDhCheckBatchDAO extends AMSBaseDAO {

	/**
	 * 功能：低值易耗盘点工单批表 EAM_DH_CHECK_BATCH 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EamDhCheckBatchDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public EamDhCheckBatchDAO(SfUserDTO userAccount, EamDhCheckBatchDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		EamDhCheckBatchDTO dto = (EamDhCheckBatchDTO) dtoParameter;
		sqlProducer = new EamDhCheckBatchModel((SfUserDTO) userAccount, dto);
	}

	/**
	 * 功能：保存工单
	 * @param orders DTOSet
	 * @return boolean
	 */
	public boolean saveOrders(DTOSet orders) {
		boolean operateResult = false;
		boolean autoCommit = true;
		EamDhCheckBatchDTO dto = (EamDhCheckBatchDTO) dtoParameter;
		boolean isNewBatch = dto.getBatchId().equals("");
		String act = dto.getAct();
		try {
			autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			if (isNewBatch) {
				SeqProducer seqPrd = new SeqProducer(conn);
				//TODO
				dto.setBatchId(seqPrd.getStrNextSeq("EAM_DH_CHECK_BATCH") + "" );
				String companyCode = userAccount.getCompanyCode();
				String orderType = LvecDicts.CHECK_BATCH;
				OrderNumGenerator numPrd = new OrderNumGenerator(conn, companyCode, orderType);
				numPrd.setOrderLength(3);
				dto.setBatchNo(numPrd.getOrderNum());
				setDTOParameter(dto);
				createData();
			} else {
				updateData();
			}
			EamDhCheckLineDAO lineDAO = new EamDhCheckLineDAO(userAccount, null, conn);
			EamDhCheckLineDTO orderLine = new EamDhCheckLineDTO();
			if(!dto.getOrderType().equals(LvecDicts.ORD_TYPE1_DHBS)){
				orderLine.setBatchId(dto.getBatchId());
				lineDAO.setDTOParameter(orderLine);
				lineDAO.DeleteByForeignKey("batchId");
			}
			EamDhCheckHeaderDTO orderHeader = new EamDhCheckHeaderDTO();
			orderHeader.setBatchId(dto.getBatchId());
			EamDhCheckHeaderDAO headerDAO = new EamDhCheckHeaderDAO(userAccount, orderHeader, conn);
			headerDAO.DeleteByForeignKey("batchId");

			int orderCount = orders.getSize();
			for(int i = 0; i < orderCount; i++){
				orderHeader = (EamDhCheckHeaderDTO)orders.getDTO(i);

				if(orderHeader.getCheckLocation()==0){

					continue;
				}
				orderHeader.setBatchId(dto.getBatchId());
				orderHeader.setCheckTaskId(dto.getCheckTaskId());
				orderHeader.setAct(act);
				headerDAO.setDTOParameter(orderHeader);
				headerDAO.saveOrder();

				if(!orderHeader.getOrderType().equals(LvecDicts.ORD_TYPE1_DHBS)){//非补扫工单才需要创建行信息
					orderHeader = (EamDhCheckHeaderDTO) headerDAO.getDTOParameter();
					orderLine.setHeaderId(orderHeader.getHeaderId());
					orderLine.setOrderType(orderHeader.getOrderType());
					orderLine.setBatchId(dto.getBatchId());
					lineDAO.setDTOParameter(orderLine);
					lineDAO.createData();
				}
			}
			operateResult = true;
		} catch (Exception ex) {
			Logger.logError(ex);
		} finally{
			try {
				if(operateResult){
					conn.commit();
					if(act.equals(DzyhActionConstant.SAVE_ACTION)){//暂存工单
						prodMessage(LvecMessageKeys.ORDER_SAVE_SUCCESS);
					} else if(act.equals(DzyhActionConstant.DISTRI_ORDER)){//下发工单
						prodMessage(LvecMessageKeys.ORDER_DISTRI_SUCCESS);
					}
				} else {
					conn.rollback();
					if (isNewBatch) {
						dto.setBatchId("");
						dto.setBatchNo(LvecWebAttributes.ORDER_NO_AUTO_PRODUCE);
					}
					if(act.equals(DzyhActionConstant.SAVE_ACTION)){//暂存工单
						prodMessage(LvecMessageKeys.ORDER_SAVE_FAILURE);
					} else if(act.equals(DzyhActionConstant.DISTRI_ORDER)){//下发工单
						prodMessage(LvecMessageKeys.ORDER_DISTRI_FAILURE);
					}
					message.setIsError(true);
				}
				conn.setAutoCommit(autoCommit);
				setDTOParameter(dto);
			} catch (SQLException ex) {
				Logger.logError(ex);
			}
		}
		return operateResult;
	}

	/**
	 * 功能：批量撤销工单批
	 * @param batchIds String[]
	 * @param singleCancel boolean
	 */
	public void cancelOrderBatchs(String[] batchIds, boolean singleCancel){
		boolean operateResult = false;
		boolean autoCommit = true;
		EamDhCheckBatchDTO dto = (EamDhCheckBatchDTO) dtoParameter;
		try {
			EamDhCheckBatchModel modelProducer = (EamDhCheckBatchModel)sqlProducer;
			SQLModel sqlModel = modelProducer.getMulBatchCancelModel(batchIds);
			DBOperator.updateRecord(sqlModel, conn);
			EamDhCheckHeaderDAO headerDAO = new EamDhCheckHeaderDAO(userAccount, null, conn);
			headerDAO.cancelOrders(batchIds);
			setDTOParameter(dto);
			operateResult = true;
		} catch (DataHandleException ex) {
			ex.printLog();
		} finally{
			try {
				if(operateResult){
					conn.commit();
					if(singleCancel){//详细信息页面点击的撤销
						prodMessage(LvecMessageKeys.BATCH_CANCEL_SUCCESS);
					} else {//列表页面点击的撤销
						prodMessage(LvecMessageKeys.BATCH_MULCEL_SUCCESS);
					}
				} else {
					conn.rollback();
					if(singleCancel){//详细信息页面点击的撤销
						prodMessage(LvecMessageKeys.BATCH_CANCEL_FAILURE);
					} else {//列表页面点击的撤销
						prodMessage(LvecMessageKeys.BATCH_MULCEL_FAILURE);
					}
					message.setIsError(true);
				}
				conn.setAutoCommit(autoCommit);
			} catch (SQLException ex) {
				Logger.logError(ex);
			}
		}
	}


	/**
	 * 功能：导出查询SQL资产数据
	 * @return String 返回导出Excel文件
	 * @throws DataTransException
	 */
	public File getExportFile() throws DataTransException {
		File file = null;
		try {
			SQLModel sqlModel = sqlProducer.getPageQueryModel();
			String reportTitle = "盘点工单批";
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
			throw new DataTransException(ex);
		}
		return file;
	}

	private Map getFieldMap(){
		Map fieldMap = new HashMap();
		fieldMap.put("BATCH_NO", "工单批编号");
		fieldMap.put("CREATION_DATE", "工单批创建日期");
		fieldMap.put("CHEC_DEPT_NAME", "工单批创建部门");
		fieldMap.put("BATCH_STATUS_VALUE", "工单批状态");
		fieldMap.put("TASK_NAME", "任务名称");
		fieldMap.put("CHECK_TYPE_VALUE", "任务类别");
		fieldMap.put("TASK_STATUS_VALUE", "任务状态");
		fieldMap.put("START_DATE", "任务起始日期");
		fieldMap.put("END_DATE", "任务截止日期");
		return fieldMap;
	}
}
