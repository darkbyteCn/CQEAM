package com.sino.td.newasset.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.dao.AmsAssetsChkLogDAO;
import com.sino.ams.newasset.dto.AmsAssetsChkLogDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.data.Row;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.td.commom.TdURLDefineList;
import com.sino.td.newasset.dto.TdAssetsTransLineDTO;
import com.sino.td.newasset.dto.TdItemInfoHistoryDTO;
import com.sino.td.newasset.model.TdAssetsConfirmModel;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class TdAssetsConfirmDAO extends AMSBaseDAO {

	private TdItemInfoHistoryDAO historyDAO = null;
	private AmsAssetsChkLogDAO chkLogDAO = null; //用于保存资产的最新调拨数据

	public TdAssetsConfirmDAO(SfUserDTO userAccount, TdAssetsTransLineDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		historyDAO = new TdItemInfoHistoryDAO(userAccount, null, conn);
		chkLogDAO = new AmsAssetsChkLogDAO(userAccount, null, conn);
	}

	/**
	 * 功能：SQL生成器baseSQLProducer的初始化。具体的DAO继承时初始化具体的SQL生成器。
	 * @param userAccount BaseUserDTO
	 * @param dtoParameter DTO
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		TdAssetsTransLineDTO dto = (TdAssetsTransLineDTO) dtoParameter;
		sqlProducer = new TdAssetsConfirmModel((SfUserDTO) userAccount, dto);
	}

	/**
	 * 功能：进行资产的确认。
	 * @param confirmAssets DTOSet
	 * @return boolean
	 */
	public boolean confirmAssets(DTOSet confirmAssets) {
		boolean operateResult = false;
		boolean autoCommit = true;
		try {
			autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			int assetsCount = confirmAssets.getSize();
			TdAssetsConfirmModel modelProducer = (TdAssetsConfirmModel) sqlProducer;
			SQLModel sqlModel = null;
			List transIds = new ArrayList();
			String transId = "";
			TdAssetsTransLineDTO dto = null;
			String transferType = null;
			for (int i = 0; i < assetsCount; i++) {
				dto = (TdAssetsTransLineDTO)confirmAssets.getDTO(i);
				transId = dto.getTransId();
				transIds.add(transId);
				fillAddressId(dto); //填充addressId信息
				transferType = dto.getTransferType();

				setDTOParameter(dto);

				sqlModel = modelProducer.getAssetsConfirmModel();//确认资产(更新AMS_ASSETS_TRANS_LINE表信息)
				DBOperator.updateRecord(sqlModel, conn);
				sqlModel = modelProducer.getTmpDistributeModel(); //插入设备分类分配信息
				DBOperator.updateRecord(sqlModel, conn);
				if(transferType.equals(AssetsDictConstant.TRANS_BTW_COMP)){//公司间调拨
					sqlModel = modelProducer.getDiscardOldBarcodeModel(); //报废旧标签(更新ETS_ITEM_INFO信息)
					DBOperator.updateRecord(sqlModel, conn);

					sqlModel = modelProducer.getCreateNewBarcodeModel(); //插入新标签(插入ETS_ITEM_INFO信息)
					DBOperator.updateRecord(sqlModel, conn);
				} else {//非公司间调拨
					sqlModel = modelProducer.getItemUpdateModel(); //更新设备主表ETS_ITEM_INFO信息
					DBOperator.updateRecord(sqlModel, conn);
				}
				logAssetsHistory(); //记录设备变动日志
				if (!transIds.contains(transId)) {
					transIds.add(transId);
				}
				recordChkLog(); //记录设备最新调拨情况，为数据同步做准备
			}
			if (!transIds.isEmpty()) {
				sqlModel = modelProducer.getDeleteReservedAssetsModel(transIds); //确认后的资产，其保留数据予以删除
				DBOperator.updateRecord(sqlModel, conn);

				sqlModel = modelProducer.getOrdersConfirmModel(transIds); //更新资产调拨单
				DBOperator.updateRecord(sqlModel, conn);
			}
			operateResult = true;
		} catch (DataHandleException ex) {
			ex.printLog();
		} catch (SQLModelException ex) {
			ex.printLog();
		} catch (SQLException ex) {
			Logger.logError(ex);
		} finally {
			try {
				if (!operateResult) {
					conn.rollback();
					prodMessage(AssetsMessageKeys.CFM_ASSETS_FAILURE);
				} else {
					conn.commit();
					prodMessage(AssetsMessageKeys.CFM_ASSETS_SUCCESS);
				}
				conn.setAutoCommit(autoCommit);
			} catch (SQLException ex1) {
				Logger.logError(ex1);
				prodMessage(AssetsMessageKeys.ROLL_BACK_ERROR);
			}
		}
		return operateResult;
	}

	/**
	 * 功能：填充AddressId属性
	 * @param dto TdAssetsTransLineDTO
	 * @throws DataHandleException
	 */
	private void fillAddressId(TdAssetsTransLineDTO dto) throws
			DataHandleException {
		try {
			String addressId = dto.getAddressId();
			if ( StrUtil.isEmpty( addressId ) ) {
				TdAssetsConfirmModel modelProducer = (TdAssetsConfirmModel) sqlProducer;
				SQLModel sqlModel = modelProducer.getAddressQueryModel();
				SimpleQuery simp = new SimpleQuery(sqlModel, conn);
				simp.executeQuery();
				if (simp.hasResult()) {
					Row row = simp.getFirstRow();
					addressId = (String) row.getValue("ADDRESS_ID");
					System.out.println("addressId = " + addressId);
					dto.setAddressId(addressId);
					setDTOParameter(dto);
				} else {
					SeqProducer seqProducer = new SeqProducer(conn);
					addressId = seqProducer.getGUID();
					dto.setAddressId(addressId);
					setDTOParameter(dto);
					sqlModel = modelProducer.getAddressCreateModel();
					DBOperator.updateRecord(sqlModel, conn);
				}
			}
		} catch (QueryException ex) {
			ex.printLog();
			throw new DataHandleException(ex);
		} catch (ContainerException ex) {
			ex.printLog();
			throw new DataHandleException(ex);
		}
	}

	/**
	 * 功能：创建资产调拨历史
	 * @throws DataHandleException
	 */
	private void logAssetsHistory() throws DataHandleException {
		TdAssetsTransLineDTO dto = (TdAssetsTransLineDTO)dtoParameter;
		TdItemInfoHistoryDTO historyDTO = new TdItemInfoHistoryDTO();
		String transferType = dto.getTransferType();
		historyDTO.setOrderNo(dto.getTransNo());
		historyDTO.setOrderCategory(AssetsDictConstant.LOG_ORDER_ASSETS);
		historyDTO.setCurrCreationDate();
		historyDTO.setCreatedBy(userAccount.getUserId());
		String orderURL = TdURLDefineList.ASSETS_TRANS_SERVLET_TD;
		orderURL += "?act=" + AssetsActionConstant.DETAIL_ACTION;
		orderURL += "&transId=" + dto.getTransId();
		historyDTO.setOrderDtlUrl(orderURL);

		if (transferType.equals(AssetsDictConstant.TRANS_BTW_COMP)) {
			historyDTO.setBarcode(dto.getBarcode());
			historyDTO.setAddressId( dto.getOldAddressId() );
			historyDTO.setResponsibilityUser(dto.getOldResponsibilityUser());
			historyDTO.setResponsibilityDept(dto.getOldResponsibilityDept());
			historyDTO.setRemark(AssetsDictConstant.TRANS_OUT_REMARK);
			historyDAO.setDTOParameter(historyDTO);
			historyDAO.createData(); //记录旧标签

			historyDTO.setBarcode(dto.getNewBarcode());
			historyDTO.setAddressId(dto.getAddressId());
			historyDTO.setResponsibilityUser(dto.getResponsibilityUser());
			historyDTO.setResponsibilityDept(dto.getResponsibilityDept());
			historyDTO.setRemark(AssetsDictConstant.TRANS_IN_REMARK);
			historyDAO.setDTOParameter(historyDTO);
			historyDAO.createData(); //记录新标签
		} else {
			historyDTO.setBarcode(dto.getBarcode());
			historyDTO.setAddressId(dto.getAddressId());
			if (transferType.equals(AssetsDictConstant.TRANS_INN_DEPT)) {
				if (!StrUtil.nullToString(dto.getResponsibilityUser()).equals(dto.getOldResponsibilityUser())) {
					historyDTO.setResponsibilityUser(dto.getResponsibilityUser());
				}
				if (!dto.getResponsibilityDept().equals(dto.getOldResponsibilityDept())) {
					historyDTO.setResponsibilityDept(dto.getResponsibilityDept());
				}
			} else {
				historyDTO.setResponsibilityUser(dto.getResponsibilityUser());
				historyDTO.setResponsibilityDept(dto.getResponsibilityDept());
			}
			historyDAO.setDTOParameter(historyDTO);
			historyDAO.createData();
		}
	}

	/**
	 * 功能：记录设备最新一次盘点情况
	 * @throws DataHandleException
	 */
	private void recordChkLog() throws DataHandleException {
		TdAssetsTransLineDTO dto = (TdAssetsTransLineDTO)dtoParameter;
		AmsAssetsChkLogDTO chkLogDTO = new AmsAssetsChkLogDTO();
		String transferType = dto.getTransferType();

		chkLogDTO.setLastChkNo(dto.getTransNo());
		chkLogDTO.setHeaderId(dto.getTransId());
		String orderUrl = TdURLDefineList.ASSETS_TRANS_SERVLET_TD;
		orderUrl += "?act=" + AssetsActionConstant.DETAIL_ACTION;
		orderUrl += "&transId=" + dto.getTransId();
		chkLogDTO.setOrderDtlUrl(orderUrl);
		chkLogDTO.setCreatedBy(userAccount.getUserId());
		chkLogDTO.setOrderType(AssetsDictConstant.ASS_RED);
		chkLogDTO.setSynStatus(0);
		if(transferType.equals(AssetsDictConstant.TRANS_BTW_COMP)){
			chkLogDTO.setBarcode(dto.getBarcode());//记录旧标签的报废
			chkLogDTO.setAddressId( dto.getOldAddressId() );
			chkLogDTO.setResponsibilityUser(dto.getOldResponsibilityUser());
			chkLogDTO.setResponsibilityDept(dto.getOldResponsibilityDept());
			chkLogDTO.setIsExist(AssetsDictConstant.STATUS_NO);
			chkLogDTO.setOrganizationId(dto.getFromOrganizationId());
			chkLogDAO.setDTOParameter(chkLogDTO);
			chkLogDAO.saveCheckLogData();

			chkLogDTO.setBarcode(dto.getNewBarcode());//记录新标签的产生
			chkLogDTO.setAddressId(dto.getAddressId());
			chkLogDTO.setResponsibilityUser(dto.getResponsibilityUser());
			chkLogDTO.setResponsibilityDept(dto.getResponsibilityDept());
			chkLogDTO.setIsExist(AssetsDictConstant.STATUS_YES);
			chkLogDTO.setChkTimes(0);
			chkLogDAO.setDTOParameter(chkLogDTO);
			chkLogDAO.saveCheckLogData();

		} else {
			chkLogDTO.setBarcode(dto.getBarcode());
			chkLogDTO.setAddressId(dto.getAddressId());
			chkLogDTO.setResponsibilityUser(dto.getResponsibilityUser());
			chkLogDTO.setResponsibilityDept(dto.getResponsibilityDept());
			chkLogDTO.setIsExist(AssetsDictConstant.STATUS_YES);
			chkLogDTO.setOrganizationId(userAccount.getOrganizationId());

			chkLogDAO.setDTOParameter(chkLogDTO);
			chkLogDAO.saveCheckLogData();
		}
	}
}
