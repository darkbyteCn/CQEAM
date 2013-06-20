package com.sino.ams.newasset.rent.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;

public class AssetsQueryModel extends AMSSQLProducer {
	/**
	 * 功能：固定资产当前信息(EAM) ETS_FA_ASSETS 数据库SQL构造层构造函数
	 * 
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsAssetsAddressVDTO 本次操作的数据
	 */
	public AssetsQueryModel(SfUserDTO userAccount, AmsAssetsAddressVDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}
	
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
		String sqlStr = 
		" SELECT EII.BARCODE, \n" +
		"        EII.ITEM_CODE, \n" + 
		"        ESI.ITEM_NAME, \n" + //资产名称
		"        ESI.ITEM_SPEC, \n" + //资产型号
		"        EII.ADDRESS_ID, \n" +
		"        AOA.OBJECT_NO, \n" +
		"        EO.WORKORDER_OBJECT_CODE, \n" + //地点代码
		"        EO.WORKORDER_OBJECT_NAME, \n" + //地点名称
		"        EII.RESPONSIBILITY_USER, \n" +
		"        AME.USER_NAME RESPONSIBILITY_USER_NAME, \n" + //责任人
		"        AME.EMPLOYEE_NUMBER, \n" + //员工号
		"        EII.RESPONSIBILITY_DEPT, \n" +
		"        AMD.DEPT_NAME DEPT_NAME, \n" + //责任部门
		"        EII.MAINTAIN_USER MAINTAIN_USER_NAME, \n" + //使用人
		"        CASE EII.ITEM_STATUS WHEN 'DISCARDED' THEN '已报废' ELSE EII.ITEM_STATUS END ITEM_STATUS \n" + //资产状态
		"   FROM ETS_ITEM_INFO EII, \n" + 
		"        ETS_SYSTEM_ITEM ESI, \n" + 
		"        AMS_OBJECT_ADDRESS AOA, \n" + 
		"        ETS_OBJECT EO, \n" +
		"        AMS_MIS_EMPLOYEE AME, \n" +
		"        AMS_MIS_DEPT AMD \n" +
		"  WHERE 1 = 1 \n" +
		"    AND EII.ITEM_CODE = ESI.ITEM_CODE \n" +
//		"    AND EII.ORGANIZATION_ID = ? \n" +
		"    AND EII.ADDRESS_ID = AOA.ADDRESS_ID \n" +
		"    AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO \n" +
		"    AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID \n" +
		"    AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE \n" +
		"    AND (  EII.ITEM_STATUS =  'SEND_REPAIR' ) \n" +
//		"    AND EII.FINANCE_PROP IN ('DH_ASSET', 'DG_ASSETS', 'SPARE') \n" +
		//"    AND ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME) \n" +
		//"    AND ESI.ITEM_SPEC LIKE dbo.NVL(?, ESI.ITEM_SPEC) \n" +
		//"    AND EII.BARCODE LIKE dbo.NVL(?, EII.BARCODE) \n" +
		"    AND (? = '' OR ESI.ITEM_NAME LIKE ?) \n" +
		"    AND (? = '' OR ESI.ITEM_SPEC LIKE ?) \n" +
		"    AND (? = '' OR EII.BARCODE LIKE ?) \n" +
		"    AND EXISTS( " +
		"    SELECT  " +
		"    NULL FROM  " +
		"    AMS_ASSETS_TRANS_HEADER AH ,  " +
		"    AMS_ASSETS_TRANS_LINE AL WHERE   " +
		"    AH.TRANS_ID = AL.TRANS_ID  " +
		"	 AND AL.BARCODE = EII.BARCODE " +
		"    AND AH.TRANS_TYPE = 'ASS-REPAIR'  " + 
		"    AND AH.TRANS_STATUS = 'APPROVED'  " +
		"    ) " 
		; 
		
		
		sqlArgs.add(dto.getItemName());
		sqlArgs.add(dto.getItemName());
		sqlArgs.add(dto.getItemSpec());
		sqlArgs.add(dto.getItemSpec());
		sqlArgs.add(dto.getBarcode());
		sqlArgs.add(dto.getBarcode());
		 
		if (userAccount.isComAssetsManager()) {
			sqlStr += "    AND EII.ORGANIZATION_ID = ? \n" ;
			sqlArgs.add( userAccount.getOrganizationId() );
		}else{
			if (!userAccount.getEmployeeId().equals("")) {
				sqlStr += "    AND EII.RESPONSIBILITY_USER = ? \n" ;
				sqlArgs.add(userAccount.getEmployeeId());
			} else {
				sqlStr += "    AND EII.RESPONSIBILITY_USER = '-1' \n" ;
			}
		}

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
