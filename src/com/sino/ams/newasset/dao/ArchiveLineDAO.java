package com.sino.ams.newasset.dao;

import java.sql.Connection;

import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsURLList;
import com.sino.ams.newasset.dto.AmsAssetsCheckHeaderDTO;
import com.sino.ams.newasset.dto.AmsAssetsCheckLineDTO;
import com.sino.ams.newasset.dto.AmsAssetsChkLogDTO;
import com.sino.ams.newasset.dto.AmsItemInfoHistoryDTO;
import com.sino.ams.newasset.model.ArchiveLineModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class ArchiveLineDAO extends AMSBaseDAO {
	private DTOSet orderItems = null;
	private DTOSet locItems = null; //本次盘点地点下的设备(用于归档时使用)
	private AmsAssetsCheckLineDTO dto = null;
	private AmsAssetsCheckHeaderDTO headerDTO = null;

	private AmsItemInfoHistoryDAO historyDAO = null;
	private AmsAssetsChkLogDAO chkLogDAO = null; //用于保存资产的最新盘点数据

	public ArchiveLineDAO(SfUserDTO userAccount, AmsAssetsCheckLineDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		historyDAO = new AmsItemInfoHistoryDAO(userAccount, null, conn);
		chkLogDAO = new AmsAssetsChkLogDAO(userAccount, null, conn);
	}

	public void setOrderHeader(AmsAssetsCheckHeaderDTO headerDTO) {
		this.headerDTO = headerDTO;
		ArchiveLineModel modelProducer = (ArchiveLineModel) sqlProducer;
		modelProducer.setOrderHeader(headerDTO);
	}

	/**
	 * 功能：SQL生成器baseSQLProducer的初始化。具体的DAO继承时初始化具体的SQL生成器。
	 * @param userAccount BaseUserDTO
	 * @param dtoParameter DTO
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		dto = (AmsAssetsCheckLineDTO) dtoParameter;
		sqlProducer = new ArchiveLineModel((SfUserDTO) userAccount, dto);
	}

	/**
	 * 功能：覆盖父类的参数设置方法
	 * @param dtoParameter DTO
	 */
	public void setDTOParameter(DTO dtoParameter) {
		super.setDTOParameter(dtoParameter);
		this.dto = (AmsAssetsCheckLineDTO) dtoParameter;
	}

	/**
	 * 功能：设置待盘点设备
	 * @param orderItems DTOSet
	 */
	public void setOrderItems(DTOSet orderItems) {
		this.orderItems = orderItems;
	}

	/**
	 * 功能：设置本次盘点地点下的设备
	 * @param locItems DTOSet
	 */
	public void setLocItems(DTOSet locItems) {
		this.locItems = locItems;
	}

	/**
	 * 功能：归档资产行
	 * @throws DataHandleException
	 */
	public void archiveChkLine() throws DataHandleException {
		try {
			String barcode = dto.getBarcode();
			String archiveStatus = dto.getArchiveStatus();
			String scanStatus = dto.getScanStatus();
			if(!scanStatus.equals(AssetsDictConstant.STATUS_YES)){
				scanStatus = AssetsDictConstant.STATUS_NO;
			}
			if (archiveStatus.equals(AssetsDictConstant.ARCHIVE_AS_SCAN)) { //以扫描结果为准
				dto.setArchMaintainUser(dto.getScanMaintainUser());
				dto.setArchiveRemark(AssetsDictConstant.ARCHIVE_SCAN_REMARK);
				dto.setArchItemCode(dto.getScanItemCode());
				dto.setArchResponsibilityUser(dto.getScanResponsibilityUser());
				dto.setArchResponsibilityDept(dto.getScanResponsibilityDept());
				dto.setArchStartDate(dto.getScanStartDate().getCalendarValue());
				dto.setScanStatus(scanStatus);
				String scanItemCode = dto.getScanItemCode();
				if (scanStatus.equals(AssetsDictConstant.STATUS_NO)) { //PDA未扫描到该设备
					if (locItems.contains("barcode", barcode)) { //且系统记录设备在本次盘点地点
						locateItem2TmpInv(); //地点变更到在途库，状态变为在途
						dto.setArchToTempInv(AssetsDictConstant.STATUS_YES);
					} //反之不作处理。
				} else {
					dto.setArchToTempInv(AssetsDictConstant.STATUS_NO);
					if (scanItemCode.equals("")) { //新添加的设备分类
						utilizeExistItemCode();
						if (dto.getScanItemCode().equals("")) {
							createSystemItem();
						} else {
							processItemDistribute();
						}
					}
//					if (orderItems.contains("barcode", barcode)) { //设备在原地点，状态归为正常
//						updateItemProp();
//					} else {
						if (hasItemInDB()) { //该设备在数据库中存在，在该地点创建新的设备
                            updateItemProp();
						} else { //创建新的设备
							dto.setRemark("资产盘点中PDA创建新的标签号。");
                            dto.setFinanceProp(DictConstant.FIN_PROP_UNKNOW);
							createItem();
							String orderType = headerDTO.getOrderType();
							if (orderType.equals(AssetsDictConstant.INS_CHK)) { //仪器仪表盘点
								createInsItem();
							} else if (orderType.equals(AssetsDictConstant.RNT_CHK)) { //租赁盘点
								createRentItem();
							}
						}
//					}
				}
				if (scanStatus.equals(AssetsDictConstant.STATUS_NO)) { //PDA未扫描到该设备
					if (locItems.contains("barcode", barcode)) { //且系统记录设备在本次盘点地点
						logItemHistory(); //创建设备变更历史
					}
				} else { //否则则不需要往AMS_ITEM_INFO_HISTORY表插入历史记录
					logItemHistory();
				}
			} else {
				dto.setArchiveRemark(AssetsDictConstant.ARCHIVE_CURR_REMARK);
				dto.setArchMaintainUser(dto.getMaintainUser());
				dto.setArchItemCode(dto.getItemCode());
				dto.setArchResponsibilityUser(dto.getResponsibilityUser());
				dto.setArchResponsibilityDept(dto.getResponsibilityDept());
				dto.setArchToTempInv(AssetsDictConstant.STATUS_NO);
			}
			archiveOrderLine(); //工单行归档
			if (scanStatus.equals(AssetsDictConstant.STATUS_NO)) { //PDA未扫描到该设备
				if (locItems.contains("barcode", barcode)) { //且系统记录设备在本次盘点地点
					recordChkLog(); //记录设备最新盘点情况，为数据同步做准备
				}
			} else { //否则则不需要往AMS_ASSETS_CHK_LOG表插入记录
				recordChkLog();
			}
		} catch (QueryException ex) {
			ex.printLog();
			throw new DataHandleException(ex);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new DataHandleException(ex);
		}
	}

	/**
	 * 功能：设置本行归档信息
	 * @throws DataHandleException
	 */
	private void archiveOrderLine() throws DataHandleException {
		try {
			ArchiveLineModel modelProducer = (ArchiveLineModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getOrderLineArchiveModel();
			DBOperator.updateRecord(sqlModel, conn);
		} catch (SQLModelException ex) {
			ex.printLog();
			throw new DataHandleException(ex);
		}
	}


	/**
	 * 功能：将设备变更到在途库
	 * @throws DataHandleException
	 */
	private void locateItem2TmpInv() throws DataHandleException {
		String addressId = userAccount.getTmpAddressId();
		if (addressId.equals("")) { //未设置在途库
			String errorMsg = userAccount.getCompany() + "未设置在途库，所有归档操作已全部撤销。";
			throw new DataHandleException(errorMsg);
		}
		ArchiveLineModel modelProducer = (ArchiveLineModel) sqlProducer;
		SQLModel sqlModel = modelProducer.getItem2TmpInvModel();
		DBOperator.updateRecord(sqlModel, conn);
	}

	/**
	 * 功能：判断分类代码在表ets_system_item是否存在
	 * @throws QueryException
	 */
	private void utilizeExistItemCode() throws QueryException {
		ArchiveLineModel modelProducer = (ArchiveLineModel) sqlProducer;
		SQLModel sqlModel = modelProducer.getExistItemCatgoryModel();
		SimpleQuery simp = new SimpleQuery(sqlModel, conn);
		simp.setDTOClassName(AmsAssetsCheckLineDTO.class.getName());
		simp.executeQuery();
		if (simp.hasResult()) {
			AmsAssetsCheckLineDTO tmpDTO = (AmsAssetsCheckLineDTO) simp.getFirstDTO();
			dto.setScanItemCode(tmpDTO.getItemCode());
			setDTOParameter(dto);
		}
	}

	/**
	 * 功能：生成新的设备分类
	 * @throws DataHandleException
	 */
	private void createSystemItem() throws DataHandleException {
		try {
			SeqProducer seqProducer = new SeqProducer(conn);
//			String itemCode = seqProducer.getStrNextSeq("ETS_SYSTEM_ITEM_S");
			//TODO
			String itemCode = "" + seqProducer.getGUID(); //.getStrNextSeq("ETS_SYSTEM_ITEM");
			dto.setScanItemCode(itemCode);
			dto.setRemark("资产盘点中PDA创建新的设备分类");
			dto.setCurrCreationDate();
			setDTOParameter(dto);
			ArchiveLineModel modelProducer = (ArchiveLineModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getSystemItemCreateModel();
			DBOperator.updateRecord(sqlModel, conn);
			sqlModel = modelProducer.getItemDistributeModel();
			DBOperator.updateRecord(sqlModel, conn);
			sqlModel = modelProducer.getItemDisApplyModel();
			DBOperator.updateRecord(sqlModel, conn);
		} catch (SQLModelException ex) {
			ex.printLog();
			throw new DataHandleException(ex);
		}
	}

	/**
	 * 功能：创建设备分类分配
	 * @throws DataHandleException
	 */
	private void processItemDistribute() throws DataHandleException {
		try {
			ArchiveLineModel modelProducer = (ArchiveLineModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getHasItemDistributeModel();
			SimpleQuery simp = new SimpleQuery(sqlModel, conn);
			simp.executeQuery();
			if (!simp.hasResult()) { //如果没有分配，则创建新的临时分配
				sqlModel = modelProducer.getItemDistributeModel();
				DBOperator.updateRecord(sqlModel, conn);
			}
		} catch (SQLModelException ex) {
			ex.printLog();
			throw new DataHandleException(ex);
		} catch (QueryException ex) {
			ex.printLog();
			throw new DataHandleException(ex);
		}
	}

	/**
	 * 功能：更新设备各种属性。
	 * 适用于原地点下有该设备的情况
	 * @throws DataHandleException
	 */
	private void updateItemProp() throws DataHandleException {
		try {
			ArchiveLineModel modelProducer = (ArchiveLineModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getItemPropUpdateModel();
			DBOperator.updateRecord(sqlModel, conn);
		} catch (SQLModelException ex) {
			ex.printLog();
			throw new DataHandleException(ex);
		}
	}

	/**
	 * 功能：更新设备地点为当前盘点单地点。
	 * 适用于原地点下没有该设备的情况
	 * @throws DataHandleException
	 */
	private void updateItemAddress() throws DataHandleException {
		try {
			ArchiveLineModel modelProducer = (ArchiveLineModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getItemAddressUpdateModel();
			DBOperator.updateRecord(sqlModel, conn);
		} catch (SQLModelException ex) {
			ex.printLog();
			throw new DataHandleException(ex);
		}
	}

	/**
	 * 功能：创建新的设备
	 * @throws DataHandleException
	 */
	private void createItem() throws DataHandleException {
		try {
			ArchiveLineModel modelProducer = (ArchiveLineModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getItemCreateModel();
			DBOperator.updateRecord(sqlModel, conn);
		} catch (SQLModelException ex) {
			ex.printLog();
			throw new DataHandleException(ex);
		}
	}

	/**
	 * 功能:记录条码变动历史
	 * @throws DataHandleException
	 */
	private void logItemHistory() throws DataHandleException {
		AmsItemInfoHistoryDTO historyDTO = new AmsItemInfoHistoryDTO();
		if (dto.getArchToTempInv().equals(AssetsDictConstant.STATUS_YES)) {
			historyDTO.setAddressId( userAccount.getTmpAddressId() );
		}
		AmsAssetsCheckLineDTO oriItem = (AmsAssetsCheckLineDTO) orderItems.getDTO("barcode", dto.getBarcode());
		if (oriItem == null) { //条码为新加入条码
			historyDTO.setItemCode(dto.getScanItemCode());
			historyDTO.setResponsibilityUser(dto.getScanResponsibilityUser());
			historyDTO.setResponsibilityDept(dto.getScanResponsibilityDept());
			historyDTO.setAddressId(dto.getAddressId());
		} else { //原来地点下有该条码
			if (!oriItem.getItemCode().equals(dto.getScanItemCode())) {
				historyDTO.setItemCode(dto.getScanItemCode());
			}
			if ( oriItem.getResponsibilityUser() != dto.getScanResponsibilityUser() ) {
				historyDTO.setResponsibilityUser(dto.getScanResponsibilityUser());
			}
			if (!oriItem.getResponsibilityDept().equals(dto.getScanResponsibilityDept())) {
				historyDTO.setResponsibilityDept(dto.getScanResponsibilityDept());
			}
		}
		if (historyDTO.needLogHistory()) {
			historyDTO.setBarcode(dto.getBarcode());
			historyDTO.setOrderNo(headerDTO.getTransNo());
			historyDTO.setOrderCategory(AssetsDictConstant.LOG_ORDER_ASSETS);
			String orderUrl = AssetsURLList.ARCHIVE_ORDER_SERVLET;
			orderUrl += "?act=" + AssetsActionConstant.DETAIL_ACTION;
			orderUrl += "&headerId=" + dto.getHeaderId();
			historyDTO.setOrderDtlUrl(orderUrl);
			historyDTO.setCreatedBy(userAccount.getUserId());
			historyDAO.setDTOParameter(historyDTO);
			historyDAO.recordHistory();
		}
	}

	/**
	 * 功能：判断该条码是否存在于数据库中
	 * @return boolean
	 * @throws QueryException
	 */
	private boolean hasItemInDB() throws QueryException {
		ArchiveLineModel modelProducer = (ArchiveLineModel) sqlProducer;
		SQLModel sqlModel = modelProducer.getHasItemInDBModel();
		SimpleQuery simp = new SimpleQuery(sqlModel, conn);
		simp.executeQuery();
		return (simp.hasResult());
	}


	/**
	 * 功能：记录设备最新一次盘点情况
	 * @throws DataHandleException
	 */
	private void recordChkLog() throws DataHandleException {
		AmsAssetsChkLogDTO chkLogDTO = new AmsAssetsChkLogDTO();
		chkLogDTO.setBarcode(dto.getBarcode());
		chkLogDTO.setLastChkNo(headerDTO.getTransNo());
		chkLogDTO.setItemCode(dto.getArchItemCode());
		chkLogDTO.setResponsibilityUser(dto.getArchResponsibilityUser());
		chkLogDTO.setResponsibilityDept(dto.getArchResponsibilityDept());
		chkLogDTO.setAddressId(dto.getAddressId());
		String orderUrl = AssetsURLList.ARCHIVE_ORDER_SERVLET;
		orderUrl += "?act=" + AssetsActionConstant.DETAIL_ACTION;
		orderUrl += "&headerId=" + dto.getHeaderId();
		chkLogDTO.setOrderDtlUrl(orderUrl);
		chkLogDTO.setBatchId(headerDTO.getBatchId());
		chkLogDTO.setHeaderId(headerDTO.getHeaderId());
		chkLogDTO.setOrganizationId(userAccount.getOrganizationId());
		chkLogDTO.setOrderType(AssetsDictConstant.ASS_CHK);
		String archiveStatus = dto.getArchiveStatus();
		if (archiveStatus.equals(AssetsDictConstant.ARCHIVE_AS_CURR)) { //以目前状态为准
			chkLogDTO.setIsExist(dto.getSystemStatus());
		} else {
			chkLogDTO.setIsExist(dto.getScanStatus());
		}
		chkLogDAO.setDTOParameter(chkLogDTO);
		chkLogDAO.saveCheckLogData();
	}

	/**
	 * 功能：创建仪器仪表设备
	 * @throws DataHandleException
	 */
	private void createInsItem() throws DataHandleException {
		ArchiveLineModel modelProducer = (ArchiveLineModel) sqlProducer;
		SQLModel sqlModel = modelProducer.getInsItemCreateModel();
		DBOperator.updateRecord(sqlModel, conn);
	}

	/**
	 * 功能：创建租赁设备
	 * @throws DataHandleException
	 */
	private void createRentItem() throws DataHandleException {
		ArchiveLineModel modelProducer = (ArchiveLineModel) sqlProducer;
		SQLModel sqlModel = modelProducer.getRentItemCreateModel();
		DBOperator.updateRecord(sqlModel, conn);
	}
}
