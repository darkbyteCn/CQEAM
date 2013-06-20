package com.sino.ams.sampling.dao;


import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.sampling.constant.SamplingMessages;
import com.sino.ams.sampling.dto.AmsAssetsSamplingHeaderDTO;
import com.sino.ams.sampling.dto.AmsAssetsSamplingLineDTO;
import com.sino.ams.sampling.model.AmsAssetsSamplingHeaderModel;
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
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.dto.BaseUserDTO;


/**
 * <p>Title: AmsAssetsSamplingHeaderDAO</p>
 * <p>Description:程序自动生成服务程序“AmsAssetsSamplingHeaderDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class AmsAssetsSamplingHeaderDAO extends AMSBaseDAO {


	/**
	 * 功能：抽查工单表 AMS_ASSETS_SAMPLING_HEADER 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsAssetsSamplingHeaderDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public AmsAssetsSamplingHeaderDAO(BaseUserDTO userAccount, AmsAssetsSamplingHeaderDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) {
		AmsAssetsSamplingHeaderDTO dto = (AmsAssetsSamplingHeaderDTO)dtoParameter;
		SfUserDTO user = (SfUserDTO)userAccount;
		sqlProducer = new AmsAssetsSamplingHeaderModel(user, dto);
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
			String reportTitle = "抽查工单";
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
		fieldMap.put("ORDER_NO", "工单编号");
		fieldMap.put("SAMPLING_LOCATION_NAME", "抽查地点");
		fieldMap.put("CREATION_DATE", "工单创建日期");
		fieldMap.put("IMPLEMENT_USER", "工单执行人");
		fieldMap.put("IMPLEMENT_DAYS", "工单执行周期");
		fieldMap.put("ORDER_STATUS_VALUE", "工单状态");
		fieldMap.put("SAMPLEDED_OU_NAME", "执行公司");
		fieldMap.put("TASK_NO", "任务编号");
		fieldMap.put("TASK_NAME", "任务名称");
		fieldMap.put("TASK_CREATION_DATE", "任务创建日期");
		fieldMap.put("START_DATE", "任务起始日期");
		fieldMap.put("END_DATE", "任务截止日期");
		fieldMap.put("CREATED_OU_NAME", "任务创建公司");
		fieldMap.put("TASK_CREATED_USER", "任务创建人");

		return fieldMap;
	}


	/**
	 * 功能：批量撤销工单。工单撤销后，不可进行进一步的操作。
	 * @param headerIds 工单ID
	 * @return boolean
	 */
	public boolean cancelOrders(String[] headerIds){
		boolean operateResult = false;
		try {
			AmsAssetsSamplingHeaderModel modelProducer = (AmsAssetsSamplingHeaderModel)sqlProducer;
			SQLModel sqlModel = modelProducer.getMulOrderCanceledModel(headerIds);
			DBOperator.updateRecord(sqlModel, conn);
			operateResult = true;
		} catch (DataHandleException ex) {
			ex.printLog();
		} finally{
			if (operateResult) {
				prodMessage(SamplingMessages.MUL_CALORD_SUCCESS);
			} else {
				prodMessage(SamplingMessages.MUL_CALORD_FAILURE);
			}
		}
		return operateResult;
	}

	/**
	 * 功能：批量下发工单。工单下发后，PDA可以下载。
	 * @param headerIds 工单ID
	 * @return boolean
	 */
	public boolean distributeOrders(String[] headerIds){
		boolean operateResult = false;
		try {
			AmsAssetsSamplingHeaderModel modelProducer = (AmsAssetsSamplingHeaderModel)sqlProducer;
			SQLModel sqlModel = modelProducer.getMulOrderDistributedModel(headerIds);
			DBOperator.updateRecord(sqlModel, conn);
			operateResult = true;
		} catch (DataHandleException ex) {
			ex.printLog();
		} finally{
			if (operateResult) {
				prodMessage(SamplingMessages.MUL_DISORD_SUCCESS);
			} else {
				prodMessage(SamplingMessages.MUL_DISORD_FAILURE);
			}
		}
		return operateResult;
	}

	/**
	 * 功能：获取指定工单批下暂存的工单
	 * @return DTOSet
	 * @throws QueryException
	 */
	public DTOSet getTempSavedOrdersByBatchId() throws QueryException {
		AmsAssetsSamplingHeaderModel modelProducer = (AmsAssetsSamplingHeaderModel)sqlProducer;
		SQLModel sqlModel = modelProducer.getTempSavedOrderModelByBatchId();
		SimpleQuery simp = new SimpleQuery(sqlModel, conn);
		simp.setDTOClassName(AmsAssetsSamplingHeaderDTO.class.getName());
		simp.executeQuery();
		return simp.getDTOSet();
	}

	/**
	 * 功能：删除选中的工单
	 * @param headerIds String[]
	 * @throws DataHandleException
	 */
//	public void deleteOrders(String[] headerIds) throws DataHandleException {
//		AmsAssetsSamplingLineDTO orderLine = new AmsAssetsSamplingLineDTO();
//		AmsAssetsSamplingLineDAO lineDAO = new AmsAssetsSamplingLineDAO(userAccount, null, conn);
//		AmsAssetsSamplingHeaderDTO orderHeader = new AmsAssetsSamplingHeaderDTO();
//		for(int i = 0; i < headerIds.length; i++){
//			orderLine.setHeaderId(headerIds[i]);
//			lineDAO.setDTOParameter(orderLine);
//			lineDAO.DeleteByForeignKey("headerId");
//
//			orderHeader.setHeaderId(headerIds[i]);
//			setDTOParameter(orderHeader);
//			deleteData();
//		}
//	}

	/**
	 * 功能：获取当前工单下的设备
	 * @param includePDA boolean
	 * @return DTOSet
	 * @throws QueryException
	 */
	public DTOSet getOrderBarcodes(boolean includePDA, AmsAssetsSamplingHeaderDTO dto, String headerId) throws QueryException {
//		AmsAssetsSamplingHeaderModel modelProducer = (AmsAssetsSamplingHeaderModel) sqlProducer;
        AmsAssetsSamplingHeaderModel modelProducer = new AmsAssetsSamplingHeaderModel(userAccount, dto);
        SQLModel sqlModel = modelProducer.getOrderBarcodesModel(includePDA, headerId);
		SimpleQuery simp = new SimpleQuery(sqlModel, conn);
		simp.setDTOClassName(AmsAssetsSamplingLineDTO.class.getName());
		simp.executeQuery();
		return simp.getDTOSet();
	}
}
