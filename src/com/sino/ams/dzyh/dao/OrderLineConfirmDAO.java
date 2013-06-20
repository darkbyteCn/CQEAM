package com.sino.ams.dzyh.dao;

import java.sql.Connection;

import com.sino.base.data.Row;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.dzyh.constant.DzyhActionConstant;
import com.sino.ams.dzyh.constant.LvecDicts;
import com.sino.ams.dzyh.constant.LvecURLs;
import com.sino.ams.dzyh.dto.EamDhCheckLineDTO;
import com.sino.ams.dzyh.dto.EamDhChgLogDTO;
import com.sino.ams.dzyh.model.OrderLineConfirmModel;
import com.sino.ams.newasset.dao.AmsItemInfoHistoryDAO;
import com.sino.ams.newasset.dto.AmsItemInfoHistoryDTO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class OrderLineConfirmDAO extends AMSBaseDAO {
	private SimpleQuery simp = null;

	public OrderLineConfirmDAO(BaseUserDTO userAccount, EamDhCheckLineDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		simp = new SimpleQuery(null, conn);
	}

	/**
	 * 功能：SQL生成器baseSQLProducer的初始化。具体的DAO继承时初始化具体的SQL生成器。
	 *
	 * @param userAccount BaseUserDTO
	 * @param dtoParameter DTO
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		EamDhCheckLineDTO dto = (EamDhCheckLineDTO)dtoParameter;
		sqlProducer = new OrderLineConfirmModel(userAccount, dto);
	}

	/**
	 * 功能：确认设备信息
	 * <B>本方法由有事务控制的方法调用</B>
	 * @throws DataHandleException
	 */
	public void confirmItem()throws DataHandleException{
		confirmOrderLine();
		archiveOrderLine();
		createLog();
		createHistory();
		updateItemInfo();
	}

	/**
	 * 功能：确认工单行信息
	 * @throws DataHandleException
	 */
	private void confirmOrderLine() throws DataHandleException{
		OrderLineConfirmModel modelProducer = (OrderLineConfirmModel) sqlProducer;
		SQLModel sqlModel = modelProducer.getLineConfirmModel();
		DBOperator.updateRecord(sqlModel, conn);
	}


	/**
	 * 功能：归档工单行。
	 * @throws DataHandleException
	 */
	private void archiveOrderLine() throws DataHandleException {
		OrderLineConfirmModel modelProducer = (OrderLineConfirmModel) sqlProducer;
		SQLModel sqlModel = modelProducer.getLineArchiveModel();
		DBOperator.updateRecord(sqlModel, conn);
	}

	/**
	 * 功能：创建低耗表设备变动日志
	 * @throws DataHandleException
	 */
	private void createLog() throws DataHandleException {
		EamDhCheckLineDTO dto = (EamDhCheckLineDTO)dtoParameter;

		EamDhChgLogDTO logDTO = new EamDhChgLogDTO();
		logDTO.setBarcode(dto.getBarcode());
		logDTO.setRefNo(dto.getOrderNo());

		logDTO.setFromDept(dto.getResponsibilityDept());
		logDTO.setToDept(userAccount.getDeptCode());

		logDTO.setFromResponsibilityUser(dto.getResponsibilityUser());
		logDTO.setToResponsibilityUser(userAccount.getEmployeeId());

		logDTO.setChgType(LvecDicts.LOG_CHG_TYPE);
		logDTO.setCatalogValueId(dto.getCatalogValueId());

		EamDhChgLogDAO logDAO = new EamDhChgLogDAO(userAccount, logDTO, conn);
		logDAO.createData();
	}


	/**
	 * 功能：创建设备变动历史
	 * @throws DataHandleException
	 */
	private void createHistory() throws DataHandleException {
		EamDhCheckLineDTO dto = (EamDhCheckLineDTO)dtoParameter;
		AmsItemInfoHistoryDTO historyDTO = new AmsItemInfoHistoryDTO();

		historyDTO.setBarcode(dto.getBarcode());
		historyDTO.setOrderNo(dto.getOrderNo());
		historyDTO.setResponsibilityDept(userAccount.getDeptCode());
		historyDTO.setResponsibilityUser(userAccount.getEmployeeId());
		historyDTO.setOrderCategory(LvecDicts.ORDER_CATEGORY_4);
        historyDTO.setCreatedBy(userAccount.getUserId());

		String orderDtlURL = LvecURLs.ORDER_SERVLET;
		orderDtlURL += "?act=" + DzyhActionConstant.DETAIL_ACTION;
		orderDtlURL += "&headerId=" + dto.getHeaderId();
		historyDTO.setOrderDtlUrl(orderDtlURL);
		historyDTO.setRemark(LvecDicts.INSTRU_ARCH_REMARK);

		AmsItemInfoHistoryDAO historyDAO = new AmsItemInfoHistoryDAO(userAccount, historyDTO, conn);
		historyDAO.recordHistory();
	}

	/**
	 * 功能：更新设备信息
	 * @throws DataHandleException
	 */
	private void updateItemInfo() throws DataHandleException {
		OrderLineConfirmModel modelProducer = (OrderLineConfirmModel) sqlProducer;
		SQLModel sqlModel = modelProducer.getItemInfoUpdateModel();
		DBOperator.updateRecord(sqlModel, conn);
	}

	/**
	 * 功能：获取当前工单剩余未确认设备数量
	 * @return int
	 * @throws QueryException
	 */
	public boolean isLastBarcode() throws QueryException {
		boolean lastBarcode = false;
		try {
			OrderLineConfirmModel modelProducer = (OrderLineConfirmModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getLeftCountModel();
			simp.setSql(sqlModel);
			simp.executeQuery();
			Row row = simp.getFirstRow();
			lastBarcode = (Integer.parseInt(row.getStrValue("LEFT_COUNT")) == 0);
		} catch (ContainerException ex) {
			ex.printLog();
			throw new QueryException(ex);
		} catch (NumberFormatException ex) {
			Logger.logError(ex);
			throw new QueryException(ex);
		}
		return lastBarcode;
	}
}
