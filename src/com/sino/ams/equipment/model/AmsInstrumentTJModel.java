package com.sino.ams.equipment.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.equipment.dto.AmsInstrumentTJDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;

/**
 * <p>Title: AmsInstrumentTJModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsInstrumentTJModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 张星
 * @version 1.0
 */
public class AmsInstrumentTJModel extends AMSSQLProducer {

	
	/**
     * 功能：仪器仪表管理(EAM) ETS_ITEM_INFO  ETS_SYSTEM_ITEM   ETS_OBJECT  EAM_ITEM_DISPOSE  数据库SQL构造层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsInstrumentRegistrationDTO 本次操作的数据
     */
	public AmsInstrumentTJModel(SfUserDTO userAccount, AmsInstrumentTJDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}
	
	public SQLModel getPageQueryModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsInstrumentTJDTO dto = (AmsInstrumentTJDTO)dtoParameter;
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
        				+ " EAM_OBJECT_ADDRESS AOA,"
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
        
        System.out.println("\n================WorkorderObjectNo====================="+dto.getWorkorderObjectNo());
        
        sqlArgs.add(dto.getItemSpec());
        sqlArgs.add(dto.getItemSpec());
        
        sqlArgs.add(dto.getResponsibilityDept());
        sqlArgs.add(dto.getResponsibilityDept());

        System.out.println("\n================WorkorderObjectNo====================="+dto.getResponsibilityDept());
        
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
	
}
