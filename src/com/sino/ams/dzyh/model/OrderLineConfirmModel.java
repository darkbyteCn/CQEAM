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
public class OrderLineConfirmModel extends AMSSQLProducer {

	public OrderLineConfirmModel(BaseUserDTO userAccount, EamDhCheckLineDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：构造还未确认的设备信息查询SQL
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getPageQueryModel() throws SQLModelException{
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			EamDhCheckLineDTO dto = (EamDhCheckLineDTO) dtoParameter;
			String sqlStr = "SELECT"
							+ " ECT.TASK_NAME,"
							+ " ECT.START_DATE,"
							+ " ECT.END_DATE,"
							+ " EDCH.HEADER_ID,"
							+ " EDCH.ORDER_NO,"
							+ " EDCH.START_TIME,"
							+ " EDCH.IMPLEMENT_DAYS,"
							+ " EDCL.BARCODE,"
							+ " EDCL.ITEM_CATEGORY,"
							+ " AMS_PUB_PKG.GET_FLEX_VALUE(EDCL.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY_VALUE,"
							+ " EDCL.ITEM_NAME,"
							+ " EDCL.ITEM_SPEC,"
							+ " EDCL.ITEM_CATEGORY2,"
							+ " EDCV.CATALOG_NAME ITEM_CATEGORY2_VALUE,"
							+ " EDCV.CATALOG_VALUE_ID,"
							+ " AMS_PUB_PKG.GET_OBJECT_NAME(EDCH.CHECK_LOCATION) LOCATION_NAME,"
							+ " EDCL.RESPONSIBILITY_USER,"
							+ " EDCL.RESPONSIBILITY_USER_NAME,"
							+ " EDCL.RESPONSIBILITY_DEPT,"
							+ " EDCL.RESPONSIBILITY_DEPT_NAME"
							+ " FROM"
							+ " EAM_CHECK_TASK        ECT,"
							+ " EAM_DH_CATALOG_VALUES EDCV,"
							+ " ETS_SYSTEM_ITEM       ESI,"
							+ " EAM_DH_CHECK_HEADER   EDCH,"
							+ " EAM_DH_CHECK_LINE     EDCL"
							+ " WHERE"
							+ " ECT.CHECK_TASK_ID = EDCH.CHECK_TASK_ID"
							+ " AND EDCH.HEADER_ID = EDCL.HEADER_ID"
							+ " AND EDCL.ITEM_CODE = ESI.ITEM_CODE"
							+ " AND ESI.ITEM_CATEGORY2 *= EDCV.CATALOG_CODE"
							+ " AND ECT.TASK_NAME LIKE dbo.NVL(?, ECT.TASK_NAME)"
							+ " AND ((ECT.START_DATE >= ISNULL(?, ECT.START_DATE)"
							+ " AND ECT.START_DATE <= ISNULL(?, ECT.START_DATE))"
							+ " OR (ECT.END_DATE >= ISNULL(?, ECT.END_DATE)"
							+ " AND ECT.END_DATE <= ISNULL(?, ECT.END_DATE)))"
							+ " AND EDCH.ORDER_NO LIKE dbo.NVL(?, EDCH.ORDER_NO)"
							+ " AND EDCL.BARCODE LIKE dbo.NVL(?, EDCL.BARCODE)"
							+ " AND EDCL.ITEM_NAME LIKE dbo.NVL(?, EDCL.ITEM_NAME)"
							+ " AND EDCH.ORDER_STATUS = ?"
							+ " AND (EDCL.CONFIRM_DATE IS NULL OR EDCL.CONFIRM_DATEE = '')"
							+ " AND EDCH.CHECK_TOOLS = ?"
							+ " AND EDCH.ORGANIZATION_ID = ?";

			sqlArgs.add(dto.getTaskName());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getOrderNo());
			sqlArgs.add(dto.getBarcode());
			sqlArgs.add(dto.getItemName());
			sqlArgs.add(LvecDicts.ORDER_STS1_DISTRIBUTED);
			sqlArgs.add(LvecDicts.CHECK_TOOLS1_WEB);
			sqlArgs.add(userAccount.getOrganizationId());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：构造进行工单行的确认SQL
	 * @return SQLModel
	 */
	public SQLModel getLineConfirmModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EamDhCheckLineDTO dto = (EamDhCheckLineDTO)dtoParameter;
		String sqlStr = "UPDATE"
						+ " EAM_DH_CHECK_LINE EDCL"
						+ " SET"
						+ " EDCL.SCAN_ITEM_CODE                = EDCL.ITEM_CODE,"
						+ " EDCL.SCAN_ITEM_CATEGORY            = EDCL.ITEM_CATEGORY,"
						+ " EDCL.SCAN_ITEM_NAME                = EDCL.ITEM_NAME,"
						+ " EDCL.SCAN_ITEM_SPEC                = EDCL.ITEM_SPEC,"
						+ " EDCL.SCAN_ITEM_CATEGORY2           = EDCL.ITEM_CATEGORY2,"
						+ " EDCL.SCAN_MAINTAIN_USER            = EDCL.MAINTAIN_USER,"
						+ " EDCL.SCAN_PRICE                    = EDCL.PRICE,"
						+ " EDCL.SCAN_RESPONSIBILITY_DEPT      = ?,"
						+ " EDCL.SCAN_RESPONSIBILITY_DEPT_NAME = ?,"
						+ " EDCL.SCAN_RESPONSIBILITY_USER      = ?,"
						+ " EDCL.SCAN_RESPONSIBILITY_USER_NAME = ?,"
						+ " EDCL.SCAN_START_DATE               = EDCL.START_DATE,"
						+ " EDCL.SCAN_VENDOR_NAME              = EDCL.VENDOR_NAME,"
						+ " EDCL.CONFIRM_DATE                  = GETDATE(),"
						+ " EDCL.CONFIRM_USER                  = ?"
						+ " WHERE"
						+ " EDCL.HEADER_ID = ?"
						+ " AND EDCL.BARCODE = ?";

		sqlArgs.add(userAccount.getDeptCode());
		sqlArgs.add(userAccount.getDeptName());
		sqlArgs.add(userAccount.getEmployeeId());
		sqlArgs.add(userAccount.getUsername());

		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(dto.getHeaderId());
		sqlArgs.add(dto.getBarcode());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：构造获取指定工单未确认设备数量SQL
	 * @return SQLModel
	 */
	public SQLModel getLeftCountModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EamDhCheckLineDTO dto = (EamDhCheckLineDTO)dtoParameter;
		String sqlStr = "SELECT"
						+ " COUNT(1) LEFT_COUNT"
						+ " FROM"
						+ " EAM_DH_CHECK_LINE EDCL"
						+ " WHERE"
						+ " (EDCL.CONFIRM_DATE IS NULL OR EDCL.CONFIRM_DATE = '') "
						+ " AND EDCL.HEADER_ID = ?";
		sqlArgs.add(dto.getHeaderId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}


	/**
	 * 功能：构造进行工单行的确认SQL
	 * @return SQLModel
	 */
	public SQLModel getLineArchiveModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EamDhCheckLineDTO dto = (EamDhCheckLineDTO)dtoParameter;
		String sqlStr = "UPDATE"
						+ " EAM_DH_CHECK_LINE EDCL"
						+ " SET"
						+ " EDCL.ARCH_ITEM_CODE                = EDCL.SCAN_ITEM_CODE,"
						+ " EDCL.ARCH_ITEM_CATEGORY            = EDCL.SCAN_ITEM_CATEGORY,"
						+ " EDCL.ARCH_ITEM_NAME                = EDCL.SCAN_ITEM_NAME,"
						+ " EDCL.ARCH_ITEM_SPEC                = EDCL.SCAN_ITEM_SPEC,"
						+ " EDCL.ARCH_ITEM_CATEGORY2           = EDCL.SCAN_ITEM_CATEGORY2,"
						+ " EDCL.ARCH_MAINTAIN_USER            = EDCL.SCAN_MAINTAIN_USER,"
						+ " EDCL.ARCH_PRICE                    = EDCL.SCAN_PRICE,"
						+ " EDCL.ARCH_RESPONSIBILITY_DEPT      = EDCL.SCAN_RESPONSIBILITY_DEPT,"
						+ " EDCL.ARCH_RESPONSIBILITY_DEPT_NAME = EDCL.SCAN_RESPONSIBILITY_DEPT_NAME,"
						+ " EDCL.ARCH_RESPONSIBILITY_USER      = EDCL.SCAN_RESPONSIBILITY_USER,"
						+ " EDCL.ARCH_RESPONSIBILITY_USER_NAME = EDCL.SCAN_RESPONSIBILITY_USER_NAME,"
						+ " EDCL.ARCH_START_DATE               = EDCL.SCAN_START_DATE,"
						+ " EDCL.ARCH_VENDOR_NAME              = EDCL.SCAN_VENDOR_NAME"
						+ " WHERE"
						+ " EDCL.HEADER_ID = ?";

		sqlArgs.add(dto.getHeaderId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}


	/**
	 * 功能：构造进行工单行的确认SQL
	 * @return SQLModel
	 */
	public SQLModel getItemInfoUpdateModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EamDhCheckLineDTO dto = (EamDhCheckLineDTO)dtoParameter;
		String sqlStr = "UPDATE"
						+ " ETS_ITEM_INFO EII"
						+ " SET"
						+ " EII.RESPONSIBILITY_USER = ?,"
						+ " EII.RESPONSIBILITY_DEPT = ?,"
						+ " EII.REMARK              = ?,"
						+ " EII.LAST_UPDATE_DATE    = GETDATE(),"
						+ " EII.LAST_UPDATE_BY      = ?"
						+ " WHERE"
						+ " EII.BARCODE = ?";

		sqlArgs.add(userAccount.getEmployeeId());
		sqlArgs.add(userAccount.getDeptCode());
		sqlArgs.add(LvecDicts.INSTRU_ARCH_REMARK);
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(dto.getBarcode());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
