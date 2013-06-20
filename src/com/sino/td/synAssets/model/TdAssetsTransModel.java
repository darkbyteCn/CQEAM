package com.sino.td.synAssets.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.synchronize.dto.EamSyschronizeDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;

/**
 * Created by   李轶
 * Date:        2009-7-22
 * Time:        18:50:55
 * Function     资产调拨结果同步
 */
public class TdAssetsTransModel extends AMSSQLProducer {
/**
	 * 功能：eAM新增地点同步 数据库SQL构造层构造函数
	 *
	 * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EtsItemMatchDTO 本次操作的数据
	 */
	public TdAssetsTransModel(SfUserDTO userAccount, EamSyschronizeDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

/**
	 * 功能：框架自动生成LOCUS页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 */
	public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EamSyschronizeDTO dto = (EamSyschronizeDTO) dtoParameter;
		String transferType = dto.getTransferType();
		String sqlStr = "";
		if (!transferType.equals("BTW_COMP")) {
			sqlStr = "SELECT\n" +
					 "       EII.SYSTEMID,\n" +
					 " CASE WHEN ASTH.TRANSFER_TYPE='INN_DEPT' THEN '部门内调拨' ELSE '部门间调拨' END TRANSFER_TYPE,\n" +
					 "       ASTH.TRANS_NO TRANS_NO,\n" +
					 "       EII.BARCODE NEW_BARCODE,\n" +
					 "       ESI.ITEM_NAME NEW_ITEM_NAME,\n" +
					 "       ESI.ITEM_SPEC NEW_ITEM_SPEC,\n" +
					 "       EII.RESPONSIBILITY_USER NEW_USER,\n" +
					 "       AME1.USER_NAME NEW_USER_NAME,\n" +
					 "       EII.RESPONSIBILITY_DEPT NEW_DEPT,\n" +
					 "       AMD1.DEPT_NAME NEW_DEPT_NAME,\n" +
					 "       EO.WORKORDER_OBJECT_LOCATION NEW_ASSETS_LOCATION,\n" +
					 "       EII.ORGANIZATION_ID,\n" +
					 "       EFAT.ASSET_ID,\n" +
					 "       EFAT.TAG_NUMBER OLD_BARDOE,\n" +
					 "       EFAT.ASSET_NUMBER,\n" +
					 "       EFAT.ASSETS_DESCRIPTION OLD_ASSETS_DESCRIPTION,\n" +
					 "       EFAT.MODEL_NUMBER OLD_MODEL_NUMBER,\n" +
					 "       EFAT.ASSETS_LOCATION OLD_ASSETS_LOCATION,\n" +
					 "       AME2.EMPLOYEE_ID OLD_USER,\n" +
					 "       EFAT.ASSIGNED_TO_NAME OLD_USER_NAME,\n" +
					 "       AMD2.DEPT_CODE OLD_DEPT_CODE,\n" +
					 "       AMD2.DEPT_NAME OLD_DEPT_NAME,\n" +
					 "       EFAT.DEPRECIATION_ACCOUNT\n" +
					 "FROM\n" +
					 "       ETS_FLEX_VALUE_SET      EFVS,\n" +
					 "       ETS_FLEX_VALUES         EFV,\n" +
					 "       AMS_MIS_EMPLOYEE        AME1,\n" +
					 "       AMS_MIS_DEPT            AMD1,\n" +
					 "       AMS_MIS_EMPLOYEE        AME2,\n" +
					 "       AMS_MIS_DEPT            AMD2,\n" +
					 "       AMS_OBJECT_ADDRESS      AOA,\n" +
					 "       ETS_OBJECT              EO,\n" +
					 "       AMS_ASSETS_TRANS_HEADER ASTH,\n" +
					 "       AMS_ASSETS_TRANS_LINE   ASTL,\n" +
					 "       ETS_SYSTEM_ITEM         ESI,\n" +
					 "       ETS_ITEM_INFO           EII,\n" +
					 "       ETS_ITEM_MATCH_TD       EIMT,\n" +
                     "       ETS_FA_ASSETS_TD        EFAT" +
					 " WHERE  EII.ITEM_CODE = ESI.ITEM_CODE\n" +
					 "       AND ESI.ITEM_CATEGORY = EFV.CODE\n" +
					 "       AND EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID\n" +
					 "       AND EFVS.CODE = 'ITEM_TYPE'\n" +
					 "       AND EII.RESPONSIBILITY_USER = AME1.EMPLOYEE_ID\n" +
					 "       AND EII.RESPONSIBILITY_DEPT = AMD1.DEPT_CODE\n" +
					 "       AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
					 "       AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
					 "       AND EII.SYSTEMID = EIMT.SYSTEMID\n" +
					 "       AND EIMT.ASSET_ID = EFAT.ASSET_ID\n" +
					 "       AND EFAT.ASSIGNED_TO_NUMBER *= AME2.EMPLOYEE_NUMBER\n" +
					 "       AND AME2.DEPT_CODE *= AMD2.DEPT_CODE\n" +
					 "       AND ASTH.TRANS_ID=ASTL.TRANS_ID\n" +
					 "       AND ASTL.BARCODE=EII.BARCODE\n" +
					 "       AND EII.FINANCE_PROP = 'TD_ASSETS'\n" +
					 "       AND AME1.ENABLED = 'Y'\n" +
					 " AND EII.ORGANIZATION_ID = ?\n" +
					 " AND NOT EXISTS (SELECT NULL\n" +
					 "              FROM ETS_MISFA_UPDATE_LOG EMUL\n" +
					 "             WHERE EMUL.ASSET_ID = EFAT.ASSET_ID\n" +
					 "               AND (EMUL.TRANS_STATUS = 0" +
					 "                  OR (EMUL.TRANS_STATUS =1 AND CONVERT(VARCHAR,EMUL.CREATION_DATE,110)=CONVERT(VARCHAR,GETDATE() ,110)" +
					 "                     ))" +
					 "                   )\n" +
					 "       AND EXISTS\n" +
					 " (SELECT NULL\n" +
					 "        FROM   AMS_ASSETS_CHK_LOG      ASCL\n" +
					 "        WHERE  EII.BARCODE = ASCL.BARCODE\n" +
					 "               AND ASTH.TRANS_NO = ASCL.LAST_CHK_NO\n" +
					 "               AND (ASCL.SYN_STATUS = 0 OR ASCL.SYN_STATUS = 2)\n" +
					 "               AND ASCL.IS_EXIST = 'Y'\n" +
					 "               AND ASCL.ORDER_TYPE = 'ASS-RED')\n" +
					 "               AND ASTH.TRANS_NO LIKE dbo.NVL(?, ASTH.TRANS_NO)\n" +
					 "               AND ASTH.TRANSFER_TYPE LIKE dbo.NVL(?, ASTH.TRANSFER_TYPE)\n" +
					 "       AND EO.WORKORDER_OBJECT_CODE LIKE dbo.NVL(?,EO.WORKORDER_OBJECT_CODE)\n" +
					 "       AND EO.WORKORDER_OBJECT_LOCATION LIKE dbo.NVL(?,EO.WORKORDER_OBJECT_LOCATION)\n" +
					 "       AND EII.BARCODE LIKE dbo.NVL(?,EII.BARCODE)\n" +
					 "       AND ESI.ITEM_NAME LIKE dbo.NVL(?,ESI.ITEM_NAME)\n" +
					 " ORDER BY\n" +
					 " ASTH.TRANS_NO";
			sqlArgs.add(userAccount.getOrganizationId());
			sqlArgs.add(dto.getTransNo());
			sqlArgs.add(dto.getTransferType());
			sqlArgs.add(dto.getWorkorderObjectCode());
			sqlArgs.add(dto.getNewAssetsLocation());
			sqlArgs.add(dto.getNewBarcode());
			sqlArgs.add(dto.getNameTo());
		} else {
			sqlStr = "SELECT EIIO.SYSTEMID,\n" +
					 "       EIIN.FINANCE_PROP,\n" +
					 "       '公司间调拨' TRANSFER_TYPE,\n" +
					 "       ASTH.TRANS_NO TRANS_NO,\n" +
					 "       ASTL.NEW_BARCODE,\n" +
					 "       ESI.ITEM_NAME NEW_ITEM_NAME,\n" +
					 "       ESI.ITEM_SPEC NEW_ITEM_SPEC,\n" +
					 "       EIIN.RESPONSIBILITY_USER NEW_USER,\n" +
					 "       AME1.USER_NAME NEW_USER_NAME,\n" +
					 "       EIIN.RESPONSIBILITY_DEPT NEW_DEPT,\n" +
					 "       AMD1.DEPT_NAME NEW_DEPT_NAME,\n" +
					 "       EO.WORKORDER_OBJECT_LOCATION NEW_ASSETS_LOCATION,\n" +
					 "       EIIN.ORGANIZATION_ID,\n" +
					 "       EFAT.ASSET_ID,\n" +
					 "       ASTL.BARCODE OLD_BARDOE,\n" +
					 "       EFAT.ASSET_NUMBER,\n" +
					 "       EFAT.ASSETS_DESCRIPTION OLD_ASSETS_DESCRIPTION,\n" +
					 "       EFAT.MODEL_NUMBER OLD_MODEL_NUMBER,\n" +
					 "       EFAT.ASSETS_LOCATION OLD_ASSETS_LOCATION,\n" +
					 "       AME2.EMPLOYEE_ID OLD_USER,\n" +
					 "       EFAT.ASSIGNED_TO_NAME OLD_USER_NAME,\n" +
					 "       AMD2.DEPT_CODE OLD_DEPT_CODE,\n" +
					 "       AMD2.DEPT_NAME OLD_DEPT_NAME,\n" +
					 "       EFAT.DEPRECIATION_ACCOUNT\n" +
					 "FROM   ETS_ITEM_INFO           EIIN,\n" +
					 "       ETS_SYSTEM_ITEM         ESI,\n" +
					 "       ETS_FLEX_VALUE_SET      EFVS,\n" +
					 "       ETS_FLEX_VALUES         EFV,\n" +
					 "       AMS_OBJECT_ADDRESS      AOA,\n" +
					 "       ETS_OBJECT              EO,\n" +
					 "       AMS_MIS_EMPLOYEE        AME1,\n" +
					 "       AMS_MIS_DEPT            AMD1,\n" +
					 "       AMS_ASSETS_TRANS_LINE   ASTL,\n" +
					 "       ETS_ITEM_INFO           EIIO,\n" +
					 "       ETS_ITEM_MATCH_TD       EIMT,\n" +
                     "       ETS_FA_ASSETS_TD        EFAT,\n" +
					 "       AMS_MIS_EMPLOYEE        AME2,\n" +
					 "       AMS_MIS_DEPT            AMD2,\n" +
					 "       AMS_ASSETS_TRANS_HEADER ASTH\n" +
					 "WHERE  EIIN.ITEM_CODE = ESI.ITEM_CODE\n" +
					 "       AND ESI.ITEM_CATEGORY = EFV.CODE\n" +
					 "       AND EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID\n" +
					 "       AND EFVS.CODE = 'ITEM_TYPE'\n" +
					 "       AND EIIN.RESPONSIBILITY_USER = AME1.EMPLOYEE_ID\n" +
					 "       AND EIIN.RESPONSIBILITY_DEPT = AMD1.DEPT_CODE\n" +
					 "       AND EIIN.ADDRESS_ID = AOA.ADDRESS_ID\n" +
					 "       AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
					 "       AND EIIN.BARCODE = ASTL.NEW_BARCODE\n" +
					 "       AND ASTL.BARCODE = EIIO.BARCODE\n" +
					 "       AND EIIO.SYSTEMID = EIMT.SYSTEMID\n" +
					 "       AND EIMT.ASSET_ID = EFAT.ASSET_ID\n" +
					 "       AND EFAT.ASSIGNED_TO_NUMBER *= AME2.EMPLOYEE_NUMBER\n" +
					 "       AND AME2.DEPT_CODE *= AMD2.DEPT_CODE\n" +
					 "       AND ASTH.TRANS_ID = ASTL.TRANS_ID\n" +
					 "       AND EIIN.FINANCE_PROP = 'TD_ASSETS'\n" +
					 "       AND AME1.ENABLED = 'Y'\n" +
					 "       AND ASTH.TRANSFER_TYPE = 'BTW_COMP'\n" +
					 "       AND NOT EXISTS (SELECT NULL\n" +
					 "        FROM   ETS_MISFA_UPDATE_LOG EMUL\n" +
					 "        WHERE  EMUL.ASSET_ID = EFAT.ASSET_ID\n" +
					 "               AND (EMUL.TRANS_STATUS = 0 OR\n" +
					 "               (EMUL.TRANS_STATUS = 1 AND\n" +
					 "               (GETDATE()  - EMUL.CREATION_DATE) <= 1)))\n" +
					 "       AND EXISTS\n" +
					 " (SELECT NULL\n" +
					 "        FROM   AMS_ASSETS_CHK_LOG ASCL\n" +
					 "        WHERE  EIIN.BARCODE = ASCL.BARCODE\n" +
					 "               AND ASTH.TRANS_NO = ASCL.LAST_CHK_NO\n" +
					 "               AND (ASCL.SYN_STATUS = 0 OR ASCL.SYN_STATUS = 2)\n" +
					 "               AND ASCL.IS_EXIST = 'Y'\n" +
					 "               AND ASCL.ORDER_TYPE = 'ASS-RED')\n" +
					 "       AND ASTH.TRANS_NO LIKE dbo.NVL(?, ASTH.TRANS_NO)\n" +
					 "       AND EO.WORKORDER_OBJECT_CODE LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_CODE)\n" +
					 "       AND EO.WORKORDER_OBJECT_LOCATION LIKE\n" +
					 "       dbo.NVL(?, EO.WORKORDER_OBJECT_LOCATION)\n" +
					 "       AND EIIN.BARCODE LIKE dbo.NVL(?, EIIN.BARCODE)\n" +
					 "       AND ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME)\n";
			sqlArgs.add(dto.getTransNo());
			sqlArgs.add(dto.getWorkorderObjectCode());
			sqlArgs.add(dto.getNewAssetsLocation());
			sqlArgs.add(dto.getNewBarcode());
			sqlArgs.add(dto.getNameTo());
			if(!userAccount.isProvinceUser()){
				sqlStr += " AND EIIN.ORGANIZATION_ID = ?";
				sqlArgs.add(userAccount.getOrganizationId());
			}
			sqlStr +=
					 " ORDER BY\n" +
					 " ASTH.TRANS_NO";
		}
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}