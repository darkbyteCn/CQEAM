package com.sino.ams.instrument.model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.instrument.dto.AmsInstrumentRegistrationDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;

/**
 * <p>Title: AmsInstrumentRegistrationModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsInstrumentRegistrationModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author yushibo
 * @version 1.0
 */
public class AmsInstrumentRegistrationModel extends AMSSQLProducer {

	private SfUserDTO sfUser = null;
	
	/**
     * 功能：仪器仪表管理(EAM) ETS_ITEM_INFO  ETS_SYSTEM_ITEM   ETS_OBJECT  EAM_ITEM_DISPOSE  数据库SQL构造层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsInstrumentRegistrationDTO 本次操作的数据
     */
	public AmsInstrumentRegistrationModel(SfUserDTO userAccount, AmsInstrumentRegistrationDTO dtoParameter) {
		super(userAccount, dtoParameter);
		sfUser = userAccount;
	}
	
	public SQLModel getPageQueryModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsInstrumentRegistrationDTO dto = (AmsInstrumentRegistrationDTO)dtoParameter;
        String sqlStr = "SELECT"
        				+ " EII.BARCODE,"
        				+ " ESI.ITEM_CATEGORY2,"
        				+ " ESI.ITEM_NAME,"
        				+ " ESI.ITEM_SPEC,"
        				+ " EII.RESPONSIBILITY_DEPT,"
        				+ " AMD.DEPT_NAME,"
        				+ " EII.RESPONSIBILITY_USER,"
        				+ " AME.USER_NAME,"
        				+ " EO.WORKORDER_OBJECT_NAME,"
        				+ " EO.ORGANIZATION_ID,"
        				+ " EII.VENDOR_BARCODE,"
        				+ " ESI.ITEM_UNIT,"
        				+ " EII.ITEM_QTY,"
        				+ " EII.PRICE,"
        				+ " EII.ATTRIBUTE3,"
        				+ " EII.START_DATE,"
        				+ " EII.DISABLE_DATE,"
        				+ " EII.MAINTAIN_USER,"
        				+ " EII.REMARK,"
        				+ " EID.DISPOSE_REASON,"
        				+ " EII.ITEM_STATUS"
        				+ " FROM"
        				+ " ETS_ITEM_INFO EII,"
        				+ " ETS_SYSTEM_ITEM ESI,"
        				+ " ETS_OBJECT EO,"
        				+ " AMS_OBJECT_ADDRESS AOA,"
        				+ " EAM_ITEM_DISPOSE EID,"
        				+ " AMS_MIS_DEPT AMD,"
        				+ " AMS_MIS_EMPLOYEE AME"
        				+ " WHERE"
        				+ " EII.ITEM_CODE = ESI.ITEM_CODE"
        				+ " AND EII.SYSTEMID *= EID.SYSTEMID"
        				+ " AND EII.ADDRESS_ID = AOA.ADDRESS_ID"
        				+ " AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO"
        				+ " AND EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE"
        				+ " AND EII.RESPONSIBILITY_USER = AME.EMPLOYEE_ID"
        				+ " AND ESI.ITEM_CATEGORY = 'YQYB'"
        				+ " AND dbo.NVL(EII.DISABLE_DATE, GETDATE() + 1) > GETDATE()"
        				
        				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.ORGANIZATION_ID = ?)"
        				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_CATEGORY2 LIKE ?)"
        				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.VENDOR_BARCODE >= ?)"
        				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.VENDOR_BARCODE <= ?)"
        				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.WORKORDER_OBJECT_NO LIKE ?)"
        				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?)"
        				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.RESPONSIBILITY_DEPT LIKE ?)"
        				+ " AND EII.ITEM_STATUS = ?"
        				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.RESPONSIBILITY_USER LIKE ?)"
        				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.BARCODE >= ?)"
        				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.BARCODE <= ?)";
        
        
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getItemCategory2());
        sqlArgs.add(dto.getItemCategory2());
        sqlArgs.add(dto.getVendorBarcode());
        sqlArgs.add(dto.getVendorBarcode());
        sqlArgs.add(dto.getVendorBarcode1());
        sqlArgs.add(dto.getVendorBarcode1());
        sqlArgs.add(dto.getWorkorderObjectNo());
        sqlArgs.add(dto.getWorkorderObjectNo());
        sqlArgs.add(dto.getItemSpec());
        sqlArgs.add(dto.getItemSpec());
        sqlArgs.add(dto.getResponsibilityDept());
        sqlArgs.add(dto.getResponsibilityDept());
        sqlArgs.add(dto.getItemStatus());
        sqlArgs.add(dto.getResponsibilityUser());
        sqlArgs.add(dto.getResponsibilityUser());
        sqlArgs.add(dto.getBarcode());
        sqlArgs.add(dto.getBarcode());
        sqlArgs.add(dto.getBarcode1());
        sqlArgs.add(dto.getBarcode1());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		
		return sqlModel;
	}
	
	
	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsInstrumentRegistrationDTO dto = (AmsInstrumentRegistrationDTO) dtoParameter;
		String sqlStr = "";
		sqlStr = "SELECT"
				+ " EII.BARCODE,"
				+ " ESI.ITEM_CATEGORY2,"
				+ " ESI.ITEM_NAME,"
				+ " ESI.ITEM_CODE,"
				+ " ESI.ITEM_SPEC,"
				+ " EII.RESPONSIBILITY_DEPT,"
				+ " AMD.DEPT_CODE,"
				+ " AMD.DEPT_NAME,"
				+ " EII.RESPONSIBILITY_USER,"
				+ " AME.EMPLOYEE_ID,"
				+ " AME.USER_NAME,"
				+ " EO.WORKORDER_OBJECT_NO,"
				+ " EO.WORKORDER_OBJECT_NAME,"
				+ " EO.ORGANIZATION_ID,"
				+ " EII.VENDOR_BARCODE,"
				+ " ESI.ITEM_UNIT,"
				+ " EII.ITEM_QTY,"
				+ " EII.PRICE,"
				+ " EII.ATTRIBUTE3,"
				+ " EII.START_DATE,"
				+ " EII.MAINTAIN_USER,"
				+ " EII.REMARK,"
        		+ " EID.DISPOSE_REASON,"
				+ " EII.ITEM_STATUS"
				+ " FROM"
				+ " ETS_ITEM_INFO EII,"
				+ " ETS_SYSTEM_ITEM ESI,"
				+ " ETS_OBJECT EO,"
				+ " AMS_OBJECT_ADDRESS AOA,"
        		+ " EAM_ITEM_DISPOSE EID,"
				+ " AMS_MIS_DEPT AMD,"
				+ " AMS_MIS_EMPLOYEE AME"
				+ " WHERE"
				+ " EII.ITEM_CODE = ESI.ITEM_CODE"
				+ " AND EII.SYSTEMID *= EID.SYSTEMID"
				+ " AND EII.ADDRESS_ID = AOA.ADDRESS_ID"
				+ " AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO"
				+ " AND EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE"
				+ " AND EII.RESPONSIBILITY_USER = AME.EMPLOYEE_ID"
				+ " AND EII.BARCODE = ?";
		
		sqlArgs.add(dto.getBarcode());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	/**
	 * 功能：框架自动生成仪器仪表登记卡记录 ETS_ITEM_INFO 数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 * @throws CalendarException 
	 */
	public int createEtsItemInfoCard(Connection conn, String barcode, String vendorCode, String itemQty,
									String price, String attribute3, String responsibilityDept, 
									String responsibilityUser, String startDate, String workorderObjectNo,
									String itemStatus, String maintainUser, String remark, String ifpage, String itemCode) 
			throws SQLException, CalendarException {

				String sqlStr = "";
				sqlStr = "BEGIN ? := ETS_INSTRUMENT_CARD_PKG.INSERTETSITEMINFO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); END;";
				
				CallableStatement cst = null;
				
				int success = 0;
				
				try {
					cst = conn.prepareCall(sqlStr);

					cst.registerOutParameter(1, Types.INTEGER);
					cst.setString(2, null);
		        	cst.setString(3, barcode);
		        	cst.setString(4, vendorCode);
		        	cst.setInt(5, StrUtil.strToInt(itemQty));
		        	cst.setInt(6, StrUtil.strToInt(price));
		        	cst.setString(7, attribute3);
		        	cst.setString(8, responsibilityDept);
		        	cst.setInt(9, StrUtil.strToInt(responsibilityUser));
		        	cst.setString(10, startDate);
		        	cst.setInt(11, StrUtil.strToInt(workorderObjectNo));
		        	cst.setString(12, itemStatus);
		        	cst.setString(13, maintainUser);
		        	cst.setInt(14, userAccount.getOrganizationId());
		        	cst.setString(15, remark);
		        	cst.setString(16, ifpage);
		        	cst.setString(17, itemCode);
		        	cst.registerOutParameter(18, Types.VARCHAR);
		        	
		        	Logger.logInfo(sqlStr);
		        	cst.execute();
					
					success = cst.getInt(1);
					
					if (success == 0)
						Logger.logError(cst.getString(18));
					
					
				} finally {
					DBManager.closeDBStatement(cst);
				}

				return success;
			}
	
	/**
	 * 功能：框架自动生成仪器仪表登记卡记录 ETS_ITEM_INFO 数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 * @throws CalendarException 
	 */
	public int updateEtsItemInfoCard(Connection conn, String oldBarcode, String barcode, String vendorCode, String itemQty,
									String price, String attribute3, String responsibilityDept, 
									String responsibilityUser, String startDate, String workorderObjectNo,
									String itemStatus, String maintainUser, String remark, String ifpage, String itemCode) 
			throws SQLException, CalendarException {
		
				String sqlStr = "";
				sqlStr = "BEGIN ? := ETS_INSTRUMENT_CARD_PKG.INSERTETSITEMINFO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); END;";
				
				CallableStatement cst = null;
				int success = 0;
				try {
					cst = conn.prepareCall(sqlStr);

					cst.registerOutParameter(1, Types.INTEGER);
					cst.setString(2, oldBarcode);
		        	cst.setString(3, barcode);
		        	cst.setString(4, vendorCode);
		        	cst.setInt(5, StrUtil.strToInt(itemQty));
		        	cst.setInt(6, StrUtil.strToInt(price));
		        	cst.setString(7, attribute3);
		        	cst.setString(8, responsibilityDept);
		        	cst.setInt(9, StrUtil.strToInt(responsibilityUser));
		        	cst.setString(10, startDate);
		        	cst.setInt(11, StrUtil.strToInt(workorderObjectNo));
		        	cst.setString(12, itemStatus);
		        	cst.setString(13, maintainUser);
		        	cst.setInt(14, userAccount.getOrganizationId());
		        	cst.setString(15, remark);
		        	cst.setString(16, ifpage);
		        	cst.setString(17, itemCode);
		        	cst.registerOutParameter(18, Types.VARCHAR);
		        	
		        	Logger.logInfo(sqlStr);
		        	cst.execute();

					success = cst.getInt(1);
					
					if (success == 0)
						Logger.logError(cst.getString(18));
					
					
				} finally {
					DBManager.closeDBStatement(cst);
				}

				return success;
			}
	
	/**
	 * 功能：框架自动生成设置生效日期 ETS_ITEM_INFO 中的DISABLE_DATE字段数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据更新用SQLModel
	 */
	public SQLModel getDataUpdateModel(String barcode) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsInstrumentRegistrationDTO dto = (AmsInstrumentRegistrationDTO) dtoParameter;
		String sqlStr = "UPDATE ETS_ITEM_INFO"
						+ " SET"
						+ " DISABLE_DATE = GETDATE()"
						+ " WHERE"
						+ " BARCODE = ?";
		
		sqlArgs.add(barcode);
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
