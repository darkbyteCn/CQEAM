package com.sino.ams.sampling.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.sampling.constant.SamplingDicts;
import com.sino.ams.sampling.dto.AmsAssetsSamplingLineDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: SinoAMS</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class OrderLineUploadModel extends AMSSQLProducer {

	/**
	 * 功能：资产盘点行表--待盘点资产表(AMS) AMS_ASSETS_SAMPLING_LINE 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsAssetsSamplingLineDTO 本次操作的数据
	 */
	public OrderLineUploadModel(BaseUserDTO userAccount,  AmsAssetsSamplingLineDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：更新剩余条码为未扫描到信息。
	 * @return SQLModel
	 */
	public SQLModel getLeftBarcodesUpdateModel() {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsSamplingLineDTO dto = (AmsAssetsSamplingLineDTO)dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "UPDATE "
						+ " AMS_ASSETS_SAMPLING_LINE"
						+ " SET"
						+ " SCAN_STATUS = ?,"
						+ " REMARK = ?"
						+ " WHERE"
						+ " SCAN_STATUS = NULL"
						+ " AND HEADER_ID = ?";
		sqlArgs.add(SamplingDicts.STATUS_NO);
		sqlArgs.add("PDA未扫描到该设备");
		sqlArgs.add(dto.getHeaderId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 *
	 * @param itemExist boolean
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getLineUploadModel(boolean itemExist) throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			String sqlStr = "";
			AmsAssetsSamplingLineDTO dto = (AmsAssetsSamplingLineDTO) dtoParameter;
			if (itemExist) {
				sqlStr = "UPDATE "
						 + " AMS_ASSETS_SAMPLING_LINE"
						 + " SET"
						 + " SCAN_STATUS = ?,"
						 + " SCAN_ITEM_CODE = ?,"
						 + " SCAN_ITEM_CATEGORY = ?,"
						 + " SCAN_ITEM_NAME = ?,"
						 + " SCAN_ITEM_SPEC = ?,"
						 + " SCAN_RESPONSIBILITY_USER = ?,"
						 + " SCAN_RESPONSIBILITY_DEPT = ?,"
						 + " SCAN_START_DATE = ?,"
						 + " SCAN_MAINTAIN_USER = ?"
						 + " WHERE"
						 + " HEADER_ID = ?"
						 + " AND BARCODE = ?";
				sqlArgs.add(dto.getScanStatus());
				sqlArgs.add(dto.getScanItemCode());
				sqlArgs.add(dto.getScanItemCategory());
				sqlArgs.add(dto.getScanItemName());
				sqlArgs.add(dto.getScanItemSpec());
				sqlArgs.add(dto.getScanResponsibilityUser());
				sqlArgs.add(dto.getScanResponsibilityDept());
				sqlArgs.add(dto.getScanStartDate());
				sqlArgs.add(dto.getScanMaintainUser());
				sqlArgs.add(dto.getHeaderId());
				sqlArgs.add(dto.getBarcode());
			} else {
				sqlStr = "INSERT INTO"
						 + " AMS_ASSETS_SAMPLING_LINE("
						 + " HEADER_ID,"
						 + " BARCODE,"
						 + " SYSTEM_STATUS,"
						 + " SCAN_STATUS,"
						 + " SCAN_ITEM_CODE,"
						 + " SCAN_ITEM_CATEGORY,"
						 + " SCAN_ITEM_NAME,"
						 + " SCAN_ITEM_SPEC,"
						 + " SCAN_RESPONSIBILITY_USER,"
						 + " SCAN_RESPONSIBILITY_DEPT,"
						 + " SCAN_MAINTAIN_USER,"
						 + " SCAN_START_DATE,"
						 + " REMARK"
						 + ") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				sqlArgs.add(dto.getHeaderId());
				sqlArgs.add(dto.getBarcode());
				sqlArgs.add(dto.getSystemStatus());
				sqlArgs.add(dto.getScanStatus());
				sqlArgs.add(dto.getScanItemCode());
				sqlArgs.add(dto.getScanItemCategory());
				sqlArgs.add(dto.getScanItemName());
				sqlArgs.add(dto.getScanItemSpec());
				sqlArgs.add(dto.getScanResponsibilityUser());
				sqlArgs.add(dto.getScanResponsibilityDept());
				sqlArgs.add(dto.getScanMaintainUser());
				sqlArgs.add(dto.getScanStartDate());
				sqlArgs.add(dto.getRemark());
			}
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}


}
