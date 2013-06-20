package com.sino.ams.inv.dzyh.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.inv.dzyh.dto.EamDhBillLDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;

/**
 * <p>Title: EamDhChgLogModel</p>
 * <p>Description:程序自动生成SQL构造器“EamDhChgLogModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 于士博
 * @version 1.0
 */
public class EamDhChgLogModel extends AMSSQLProducer {
	
	protected SfUserDTO userAccount = null;

	/**
	 * 功能：低值易耗品变动历史表(EAM) EAM_DH_CHG_LOG 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EamDhBillLDTO 本次操作的数据
	 */
	public EamDhChgLogModel(SfUserDTO userAccount, EamDhBillLDTO dtoParameter) {
		
		super(userAccount, dtoParameter);
		this.userAccount = userAccount;
	}

	/**
	 * 功能：框架自动生成资产盘点记录 EAM_DH_CHG_LOG数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 */
	public SQLModel getDataCreateModel(String barcode, String catalogValueId, String oldDept, String newDept, String oldAddress, String newAddress, String oldUser, String newUser, String oldMaintain, String newMaintain) {
		
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EamDhBillLDTO dto = (EamDhBillLDTO) dtoParameter;
		String sqlStr = "INSERT INTO "
						+ " EAM_DH_CHG_LOG("
						+ " DH_CHG_LOG_ID,"
						+ " BARCODE,"
						+ " CATALOG_VALUE_ID,"
						+ " CHG_TYPE,"
						+ " FROM_DEPT,"
						+ " TO_DEPT,"
						+ " FROM_ADDRESS_ID,"
						+ " TO_ADDRESS_ID,"
						+ " FROM_RESPONSIBILITY_USER,"
						+ " TO_RESPONSIBILITY_USER,"
						+ " FROM_MAINTAIN_USER,"
						+ " TO_MAINTAIN_USER,"
						+ " FROM_STATUS,"
						+ " TO_STATUS,"
						+ " CREATE_BY,"
						+ " CREATION_DATE"
						+ ") VALUES ("
						+ " NEWID() , ?, ?, 'TYPE_INV_REQ', ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE())";

		sqlArgs.add(barcode);
		sqlArgs.add(catalogValueId);
		
		//sqlArgs.add(dto.getChgType());
		
		sqlArgs.add(oldDept);
		sqlArgs.add(newDept);
		sqlArgs.add(oldAddress);
		sqlArgs.add(newAddress);
		sqlArgs.add(oldUser);
		sqlArgs.add(newUser);
		sqlArgs.add(oldMaintain);
		sqlArgs.add(newMaintain);
		
		
		sqlArgs.add(dto.getFromStatus());
		sqlArgs.add(dto.getToStatus());
		
		//sqlArgs.add(userAccount.getOrganizationId());
		sqlArgs.add(userAccount.getUserId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		
		return sqlModel;
	}
	
	
	/**
	 * 功能：低值易耗品出库－物资领用清单查询功能。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 */
	public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EamDhBillLDTO dto = (EamDhBillLDTO)dtoParameter;
		String sqlStr = "SELECT"
						+ " EII.SYSTEMID,"
						//+ " EDCV.CATALOG_VALUE_ID,"
						+ " EDCL.CATALOG_VALUE_ID,"
						+ " ESI.ITEM_CATEGORY2,"
						+ " EII.BARCODE,"
						+ " ESI.ITEM_NAME,"
						+ " ESI.ITEM_SPEC,"
						+ " EII.ITEM_QTY,"
						+ " EII.PRICE,"
						+ " EII.RESPONSIBILITY_DEPT,"
						+ " AMD.DEPT_NAME,"
						+ " EII.RESPONSIBILITY_USER,"
						+ " AME.USER_NAME,"
						+ " EII.ADDRESS_ID,"
						+ " EO.WORKORDER_OBJECT_NAME,"
						+ " EII.MAINTAIN_USER,"
						+ " EII.LAST_LOC_CHG_DATE,"
						+ " EII.ATTRIBUTE3,"
						+ " EII.REMARK"
						+ " FROM"
						+ " ETS_SYSTEM_ITEM ESI,"
						+ " ETS_ITEM_INFO EII,"
						+ " AMS_MIS_DEPT AMD,"
						+ " ETS_OBJECT EO,"
						+ " AMS_OBJECT_ADDRESS AOA,"
						//+ " EAM_DH_CATALOG_VALUES EDCV,"
						+ " EAM_DH_CHG_LOG EDCL,"
						+ " AMS_MIS_EMPLOYEE AME"
						+ " WHERE"
						+ " ESI.ITEM_CODE=EII.ITEM_CODE"
						+ " AND EII.RESPONSIBILITY_DEPT=AMD.DEPT_CODE"
						+ " AND EII.ADDRESS_ID=AOA.ADDRESS_ID"
						+ " AND AOA.BOX_NO='0000'"
						+ " AND AOA.NET_UNIT='0000'"
						+ " AND EO.WORKORDER_OBJECT_NO=AOA.OBJECT_NO"
						+ " AND EII.RESPONSIBILITY_USER=AME.EMPLOYEE_ID"
						//+ " AND ESI.ITEM_CATEGORY2=EDCV.CATALOG_CODE"
						+ " AND EII.BARCODE = EDCL.BARCODE"
						+ " AND EDCL.CHG_TYPE = 'TYPE_INV_REQ'"
						
						+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR  EII.LAST_LOC_CHG_DATE >= ? )"
                        + " AND ( " + SyBaseSQLUtil.isNull() + "  OR  EII.LAST_LOC_CHG_DATE <= ? )"
						
						+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR EDCL.CATALOG_VALUE_ID LIKE ?)"
						+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.BARCODE LIKE ?)"
						+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_NAME LIKE ?)"
						+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?)"
//						+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.RESPONSIBILITY_DEPT LIKE ?)"
//						+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.RESPONSIBILITY_USER LIKE ?)";
						+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR AMD.DEPT_CODE LIKE ?)"
						+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR AME.EMPLOYEE_ID = ?)";
		
		
		sqlArgs.add(dto.getMinTime());
        sqlArgs.add(dto.getMinTime());
        sqlArgs.add(dto.getMaxTime());
        sqlArgs.add(dto.getMaxTime());
		
		sqlArgs.add(dto.getCatalogValueId());
		sqlArgs.add(dto.getCatalogValueId());
		
		sqlArgs.add(dto.getBarcode());
		sqlArgs.add(dto.getBarcode());
	
		sqlArgs.add(dto.getItemName());
		sqlArgs.add(dto.getItemName());
		
		sqlArgs.add(dto.getItemSpec());
		sqlArgs.add(dto.getItemSpec());
		
//		sqlArgs.add(dto.getResponsibilityDept());
//		sqlArgs.add(dto.getResponsibilityDept());
//		
//		sqlArgs.add(dto.getResponsibilityUser());
//		sqlArgs.add(dto.getResponsibilityUser());
		
		sqlArgs.add(dto.getDeptCode());
		sqlArgs.add(dto.getDeptCode());
		
		sqlArgs.add(dto.getEmployeeId());
		sqlArgs.add(dto.getEmployeeId());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
