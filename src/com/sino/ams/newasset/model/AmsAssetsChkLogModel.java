package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.dto.AmsAssetsChkLogDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;

/**
 * <p>Title: AmsAssetsChkLogModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsAssetsChkLogModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */

public class AmsAssetsChkLogModel extends AMSSQLProducer {

	/**
	 * 功能：资产盘点记录 AMS_ASSETS_CHK_LOG 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsAssetsChkLogDTO 本次操作的数据
	 */
	public AmsAssetsChkLogModel(SfUserDTO userAccount,
								AmsAssetsChkLogDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：框架自动生成资产盘点记录 AMS_ASSETS_CHK_LOG数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 */
	public SQLModel getDataCreateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsChkLogDTO dto = (AmsAssetsChkLogDTO) dtoParameter;
		String sqlStr = "INSERT INTO "
						+ " AMS_ASSETS_CHK_LOG("
						+ " BARCODE,"
						+ " CHK_TIMES,"
						+ " LAST_CHK_DATE,"
						+ " LAST_CHK_NO,"
						+ " ITEM_CODE,"
						+ " ITEM_NAME,"
						+ " ITEM_SPEC,"
						+ " ITEM_CATEGORY,"
						+ " RESPONSIBILITY_USER,"
						+ " RESPONSIBILITY_DEPT,"
						+ " ADDRESS_ID,"
						+ " ORGANIZATION_ID,"
						+ " HEADER_ID,"
						+ " BATCH_ID,"
						+ " ORDER_DTL_URL,"
						+ " SYN_STATUS,"
						+ " CREATION_DATE,"
						+ " CREATED_BY,"
						+ " ORDER_TYPE,"
						+ " IS_EXIST"
						+ ") VALUES ("
						+ " ?, ?, GETDATE(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), ?, ?, ?)";

		sqlArgs.add(dto.getBarcode());
		sqlArgs.add(dto.getChkTimes());
		sqlArgs.add(dto.getLastChkNo());
		sqlArgs.add(dto.getItemCode());
		sqlArgs.add(dto.getItemName());
		sqlArgs.add(dto.getItemSpec());
		sqlArgs.add(dto.getItemCategory());
		sqlArgs.add(dto.getResponsibilityUser());
		sqlArgs.add(dto.getResponsibilityDept());
		sqlArgs.add(dto.getAddressId());
		sqlArgs.add(userAccount.getOrganizationId());
		sqlArgs.add(dto.getHeaderId());
		sqlArgs.add(dto.getBatchId());
		sqlArgs.add(dto.getOrderDtlUrl());
		
		sqlArgs.add( 0 );
//		sqlArgs.add(AssetsDictConstant.SYN_STATUS_NO);
		
		sqlArgs.add(dto.getCreatedBy());
		sqlArgs.add(dto.getOrderType());
		sqlArgs.add(dto.getIsExist());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成资产盘点记录 AMS_ASSETS_CHK_LOG数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据更新用SQLModel
	 */
	public SQLModel getDataUpdateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsChkLogDTO dto = (AmsAssetsChkLogDTO) dtoParameter;
		String sqlStr = "UPDATE AMS_ASSETS_CHK_LOG"
						+ " SET"
						+ " LAST_CHK_DATE = GETDATE(),"
						+ " LAST_CHK_NO = ?,"
						+ " ITEM_CODE = ?,"
						+ " ITEM_NAME = ?,"
						+ " ITEM_SPEC = ?,"
						+ " ITEM_CATEGORY = ?,"
						+ " RESPONSIBILITY_USER = ?,"
						+ " RESPONSIBILITY_DEPT = ?,"
						+ " ADDRESS_ID = ?,"
						+ " ORGANIZATION_ID = ?,"
						+ " HEADER_ID = ?,"
						+ " BATCH_ID = ?,"
						+ " ORDER_DTL_URL = ?,"
						+ " SYN_STATUS = ?,"
						+ " LAST_UPDATE_DATE = GETDATE(),"
						+ " LAST_UPDATE_BY = ?,"
						+ " ORDER_TYPE = ?,"
						+ " IS_EXIST = ?";
		if (dto.getOrderType().equals(AssetsDictConstant.ASS_CHK)) {
			sqlStr += ", CHK_TIMES = ISNULL(CHK_TIMES, 0) + 1";
		}
		sqlStr += " WHERE BARCODE = ?";
		sqlArgs.add(dto.getLastChkNo());
		sqlArgs.add(dto.getItemCode());
		sqlArgs.add(dto.getItemName());
		sqlArgs.add(dto.getItemSpec());
		sqlArgs.add(dto.getItemCategory());
		sqlArgs.add(dto.getResponsibilityUser());
		sqlArgs.add(dto.getResponsibilityDept());
		sqlArgs.add(dto.getAddressId());
		sqlArgs.add(dto.getOrganizationId());
		sqlArgs.add(dto.getHeaderId());
		sqlArgs.add(dto.getBatchId());
		sqlArgs.add(dto.getOrderDtlUrl());
		sqlArgs.add(0);
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(dto.getOrderType());
		sqlArgs.add(dto.getIsExist());
		sqlArgs.add(dto.getBarcode());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成盘点资产最新状态 AMS_ASSETS_CHECK_STATUS数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getHasDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsChkLogDTO dto = (AmsAssetsChkLogDTO) dtoParameter;
		String sqlStr = "SELECT "
						+ " 1"
						+ " FROM"
						+ " AMS_ASSETS_CHK_LOG"
						+ " WHERE"
						+ " BARCODE = ?";
		sqlArgs.add(dto.getBarcode());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
