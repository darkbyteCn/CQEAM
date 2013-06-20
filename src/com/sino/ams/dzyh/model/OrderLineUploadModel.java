package com.sino.ams.dzyh.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.dzyh.constant.LvecDicts;
import com.sino.ams.dzyh.dto.EamDhCheckLineDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class OrderLineUploadModel extends AMSSQLProducer {

	public OrderLineUploadModel(BaseUserDTO userAccount,  EamDhCheckLineDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：更新剩余条码为未扫描到信息。
	 * @return SQLModel
	 */
	public SQLModel getLeftBarcodesUpdateModel() {
		EamDhCheckLineDTO dto = (EamDhCheckLineDTO)dtoParameter;
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "UPDATE "
						+ " EAM_DH_CHECK_LINE EDCL"
						+ " SET"
						+ " EDCL.SCAN_STATUS = ?,"
						+ " EDCL.REMARK = ?"
						+ " WHERE"
						+ " EDCL.SCAN_STATUS " + SyBaseSQLUtil.isNullNoParam() + " "
						+ " AND EDCL.HEADER_ID = ?";
		sqlArgs.add(LvecDicts.STATUS_NO);
		sqlArgs.add("PDA未扫描到该设备");
		sqlArgs.add(dto.getHeaderId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：获取工单行设备上载处理SQL
	 * @param itemExist boolean
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getLineUploadModel(boolean itemExist) throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			String sqlStr = "";
			EamDhCheckLineDTO dto = (EamDhCheckLineDTO) dtoParameter;
			if (itemExist) {
				sqlStr = "UPDATE "
						 + " EAM_DH_CHECK_LINE EDCL"
						 + " SET"
						 + " EDCL.SCAN_STATUS = ?,"
						 + " EDCL.SCAN_ITEM_CODE = ?,"
						 + " EDCL.SCAN_ITEM_CATEGORY = ?,"
						 + " EDCL.SCAN_ITEM_NAME = ?,"
						 + " EDCL.SCAN_ITEM_SPEC = ?,"
						 + " EDCL.SCAN_RESPONSIBILITY_USER = ?,"
						 + " EDCL.SCAN_RESPONSIBILITY_DEPT = ?,"
						 + " EDCL.SCAN_START_DATE = ?,"
						 + " EDCL.SCAN_MAINTAIN_USER = ?,"
						 + " EDCL.SCAN_ITEM_CATEGORY2 = ?,"
						 + " EDCL.SCAN_VENDOR_ID = ?,"
						 + " EDCL.SCAN_VENDOR_NAME = ?,"
						 + " EDCL.SCAN_RESPONSIBILITY_USER_NAME = ?,"
						 + " EDCL.SCAN_RESPONSIBILITY_DEPT_NAME = ?,"
						 + " EDCL.SCAN_PRICE = ?"
						 + " WHERE"
						 + " EDCL.HEADER_ID = ?"
						 + " AND EDCL.BARCODE = ?";
				sqlArgs.add(dto.getScanStatus());
				sqlArgs.add(dto.getScanItemCode());
				sqlArgs.add(dto.getScanItemCategory());
				sqlArgs.add(dto.getScanItemName());
				sqlArgs.add(dto.getScanItemSpec());
				sqlArgs.add(dto.getScanResponsibilityUser());
				sqlArgs.add(dto.getScanResponsibilityDept());
				sqlArgs.add(dto.getScanStartDate());
				sqlArgs.add(dto.getScanMaintainUser());
				sqlArgs.add(dto.getScanItemCategory2());
				sqlArgs.add(dto.getScanVendorId());
				sqlArgs.add(dto.getScanVendorName());
				sqlArgs.add(dto.getScanResponsibilityUserName());
				sqlArgs.add(dto.getScanResponsibilityDeptName());
				sqlArgs.add(dto.getScanPrice());
				sqlArgs.add(dto.getHeaderId());
				sqlArgs.add(dto.getBarcode());
			} else {
				sqlStr = "INSERT INTO"
						 + " EAM_DH_CHECK_LINE("
						 + " HEADER_ID,"
						 + " BARCODE,"
						 + " SYSTEM_STATUS,"
						 + " SCAN_STATUS,"
						 + " SCAN_ITEM_CODE,"
						 + " SCAN_ITEM_CATEGORY,"
						 + " SCAN_ITEM_NAME,"
						 + " SCAN_ITEM_SPEC,"
						 + " SCAN_ITEM_CATEGORY2,"
						 + " SCAN_VENDOR_ID,"
						 + " SCAN_VENDOR_NAME,"
						 + " SCAN_RESPONSIBILITY_USER,"
						 + " SCAN_RESPONSIBILITY_USER_NAME,"
						 + " SCAN_RESPONSIBILITY_DEPT,"
						 + " SCAN_RESPONSIBILITY_DEPT_NAME,"
						 + " SCAN_MAINTAIN_USER,"
						 + " SCAN_START_DATE,"
						 + " SCAN_PRICE,"
						 + " REMARK"
						 + ") VALUES(?, ?, 'N', 'Y', ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

				sqlArgs.add(dto.getHeaderId());
				sqlArgs.add(dto.getBarcode());
				sqlArgs.add(dto.getScanItemCode());
				sqlArgs.add(dto.getScanItemCategory());
				sqlArgs.add(dto.getScanItemName());
				sqlArgs.add(dto.getScanItemSpec());
				sqlArgs.add(dto.getScanItemCategory2());
				sqlArgs.add(dto.getScanVendorId());
				sqlArgs.add(dto.getScanVendorName());
				sqlArgs.add(dto.getScanResponsibilityUser());
				sqlArgs.add(dto.getScanResponsibilityUserName());
				sqlArgs.add(dto.getScanResponsibilityDept());
				sqlArgs.add(dto.getScanResponsibilityDeptName());
				sqlArgs.add(dto.getScanMaintainUser());
				sqlArgs.add(dto.getScanStartDate());
				sqlArgs.add(dto.getScanPrice());
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
