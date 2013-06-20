package com.sino.soa.td.srv.assettransincompanysrv.model;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.soa.td.srv.assettransincompanysrv.dto.SBFIFATdAssetsTransInCompanyDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-9-14
 * Time: 9:53:21
 * To change this template use File | Settings | File Templates.
 */
public class SBFIFATdAssetsTransInCompanyModel extends AMSSQLProducer {
	private SfUserDTO sfUser = null;

	public SBFIFATdAssetsTransInCompanyModel(SfUserDTO userAccount, SBFIFATdAssetsTransInCompanyDTO dtoParameter) {
		super(userAccount, dtoParameter);
		sfUser = userAccount;
	}

	public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SBFIFATdAssetsTransInCompanyDTO dto = (SBFIFATdAssetsTransInCompanyDTO) dtoParameter;
		String sqlStr = "";
		if (!dto.getTransferType().equals("OTHER")) {
			sqlStr = "SELECT\n" +
					 "       EII.SYSTEMID,\n" +
                     "       CASE ASTH.TRANSFER_TYPE WHEN 'INN_DEPT' THEN '部门内调拨' ELSE '部门间调拨' END TRANSFER_TYPE,\n" +
					 "       ASTH.TRANS_NO TRANS_NO,\n" +
                     "       EII.BARCODE OLD_BARCODE,\n" +
					 "       EFA.TAG_NUMBER NEW_BARCODE,\n" +
					 "       ESI.ITEM_NAME NEW_ITEM_NAME,\n" +
					 "       ESI.ITEM_SPEC NEW_ITEM_SPEC,\n" +
					 "       EII.RESPONSIBILITY_USER NEW_USER,\n" +
					 "       AME1.USER_NAME NEW_USER_NAME,\n" +
					 "       EII.RESPONSIBILITY_DEPT NEW_DEPT,\n" +
					 "       AMD1.DEPT_NAME NEW_DEPT_NAME,\n" +
					 "       EO.WORKORDER_OBJECT_LOCATION NEW_ASSETS_LOCATION,\n" +
					 "       EII.ORGANIZATION_ID,\n" +
					 "       EFA.ASSET_ID,\n" +
					 "       EFA.TAG_NUMBER OLD_BARDOE,\n" +
					 "       EFA.ASSET_NUMBER,\n" +
					 "       EFA.ASSETS_DESCRIPTION OLD_ASSETS_DESCRIPTION,\n" +
					 "       EFA.MODEL_NUMBER OLD_MODEL_NUMBER,\n" +
					 "       EFA.ASSETS_LOCATION OLD_ASSETS_LOCATION,\n" +
					 "       AME2.EMPLOYEE_ID OLD_USER,\n" +
					 "       EFA.ASSIGNED_TO_NAME OLD_USER_NAME,\n" +
					 "       AMD2.DEPT_CODE OLD_DEPT_CODE,\n" +
					 "       AMD2.DEPT_NAME OLD_DEPT_NAME,\n" +
					 "       EFA.DEPRECIATION_ACCOUNT,\n" +
                     "       dbo.APP_GET_COUNTY_CODE(ASTL.RESPONSIBILITY_DEPT) COST_CENTER_CODE\n" +
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
					 "       ETS_ITEM_MATCH_TD       EIM,\n" +
					 "       ETS_FA_ASSETS_TD        EFA\n" +
					 "WHERE  EII.ITEM_CODE = ESI.ITEM_CODE\n" +
					 "       AND ESI.ITEM_CATEGORY = EFV.CODE\n" +
					 "       AND EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID\n" +
					 "       AND EFVS.CODE = 'ITEM_TYPE'\n" +
					 "       AND EII.RESPONSIBILITY_USER = AME1.EMPLOYEE_ID\n" +
					 "       AND EII.RESPONSIBILITY_DEPT *= AMD1.DEPT_CODE\n" +
					 "       AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
					 "       AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
					 "       AND EII.SYSTEMID = EIM.SYSTEMID\n" +
					 "       AND EIM.ASSET_ID = EFA.ASSET_ID\n" +
					 "       AND EFA.ASSIGNED_TO_NUMBER *= AME2.EMPLOYEE_NUMBER\n" +
					 "       AND EFA.COMPANY_CODE *= AME2.COMPANY_CODE\n" +     
					 "       AND AME2.DEPT_CODE *= AMD2.DEPT_CODE\n" +
					 "       AND ASTH.TRANS_ID=ASTL.TRANS_ID\n" +
					 "       AND ASTL.BARCODE=EII.BARCODE\n" +
					 "       AND EII.FINANCE_PROP = 'TD_ASSETS'\n" +
					 "       AND AME1.ENABLED = 'Y'\n" +
                     "        AND ((ASTH.TRANS_STATUS ='APPROVED' AND ASTH.TRANSFER_TYPE='INN_DEPT_RFU' ) OR ASTH.TRANS_STATUS = 'CONFIRMD')  \n" +
					 "       AND EII.ORGANIZATION_ID = ?\n" +
					 "       AND NOT EXISTS (SELECT NULL\n" +
                     "                         FROM ETS_MISFA_UPDATE_LOG EMUL\n" +
                     "                        WHERE EMUL.ASSET_ID = EFA.ASSET_ID\n" +
                     "                          AND EMUL.TRANSACTION_NO = ASTH.TRANS_NO\n" +
                     "                          AND EMUL.TRANS_STATUS = 1)\n" +
					 "       AND ASTH.TRANS_NO LIKE dbo.NVL(?, ASTH.TRANS_NO)\n" +
					 "       AND ASTH.TRANSFER_TYPE LIKE dbo.NVL(?, ASTH.TRANSFER_TYPE)\n" +
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
			sqlStr = "SELECT EII.SYSTEMID,\n" +
                    "       '其它' TRANSFER_TYPE,\n" +
                    "       '' TRANS_NO,\n" +
                    "       EII.BARCODE OLD_BARCODE,\n" +
                    "       EFA.TAG_NUMBER NEW_BARCODE,\n" +
                    "       EFA.ASSET_NUMBER,\n" +
                    "       ESI.ITEM_NAME NEW_ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC NEW_ITEM_SPEC,\n" +
                    "       EII.RESPONSIBILITY_DEPT NEW_DEPT,\n" +
                    "       AMD.DEPT_NAME NEW_DEPT_NAME,\n" +
                    "       EO.WORKORDER_OBJECT_LOCATION NEW_ASSETS_LOCATION,\n" +
                    "       EFA.ASSET_ID,\n" +
                    "       EFA.TAG_NUMBER OLD_BARDOE,\n" +
                    "       EFA.ASSETS_LOCATION OLD_ASSETS_LOCATION,\n" +
                    "       '' OLD_DEPT_CODE,\n" +
                    "       '' OLD_DEPT_NAME,\n" +
                    "       EII.RESPONSIBILITY_USER NEW_USER,\n" +
                    "       AME2.USER_NAME NEW_USER_NAME,\n" +
                    "       EFA.ASSIGNED_TO_NAME OLD_USER_NAME,\n" +
                    "       AME2.EMPLOYEE_NUMBER NEW_EMPLOYEE_NUMBER,\n" +
                    "       dbo.APP_GET_COUNTY_CODE(EII.RESPONSIBILITY_DEPT) COST_CENTER_CODE\n" +
                    "  FROM ETS_ITEM_INFO      EII,\n" +
                    "       ETS_FA_ASSETS_TD      EFA,\n" +
                    "       ETS_ITEM_MATCH_TD     EIM,\n" +
                    "       ETS_ITEM_MATCH_TD_REC EIMR,\n" +  
                    "       ETS_OBJECT         EO,\n" +
                    "       AMS_OBJECT_ADDRESS AOA,\n" +
                    "       AMS_MIS_DEPT       AMD,\n" +
                    "       AMS_MIS_EMPLOYEE   AME,\n" +
                    "       AMS_MIS_EMPLOYEE   AME2,\n" +
                    "       ETS_SYSTEM_ITEM    ESI\n" +
                    " WHERE EII.SYSTEMID = EIM.SYSTEMID\n" +
                    "   AND EIM.ASSET_ID = EFA.ASSET_ID\n" +
                    "   AND EIM.SYSTEMID = EIMR.SYSTEM_ID\n" +
                    "   AND EIM.ASSET_ID = EIMR.ASSET_ID\n" +
                    "   AND EII.BARCODE = EFA.TAG_NUMBER\n" +
                    "   AND EO.WORKORDER_OBJECT_NO = AOA.OBJECT_NO\n" +
                    "   AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                    "   AND EII.ORGANIZATION_ID = AOA.ORGANIZATION_ID\n" +
                    "   AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE\n" +
                    "   AND EFA.ASSIGNED_TO_NUMBER *= AME.EMPLOYEE_NUMBER\n" +
                    "   AND EFA.COMPANY_CODE *= AME.COMPANY_CODE\n" +     
                    "   AND EII.RESPONSIBILITY_USER = AME2.EMPLOYEE_ID\n" +
                    "   AND ESI.ITEM_CODE = EII.ITEM_CODE\n" +
                    "   AND EFA.COST <> 0\n" +
                    "   AND (EO.OBJECT_CATEGORY < '70' OR EO.OBJECT_CATEGORY = '80')\n" +
                    "   AND NOT EXISTS (SELECT NULL\n" +
                    "                     FROM ETS_MISFA_UPDATE_LOG EMUL\n" +
                    "                    WHERE EMUL.ASSET_ID = EFA.ASSET_ID\n" +
                    "                      AND EMUL.TRANS_STATUS = 1\n" +
                    "                      AND CONVERT(INT, CONVERT(CHAR, EMUL.CREATION_DATE, 112)) = CONVERT(INT, CONVERT(CHAR, GETDATE(), 112)))\n" +
                    "   AND EII.FINANCE_PROP = 'TD_ASSETS'\n" +
                    "   AND AME.ENABLED = 'Y'\n" +
                    "   AND (EFA.ASSETS_LOCATION_CODE <> EO.WORKORDER_OBJECT_CODE OR\n" +
                    "       dbo.NVL(EFA.ASSIGNED_TO_NUMBER, 'A') <>\n" +
                    "       dbo.NVL(AME2.EMPLOYEE_NUMBER, 'A'))\n" +
                    "   AND EII.ORGANIZATION_ID = ?\n" +
                    "   AND EII.BARCODE LIKE dbo.NVL(?, EII.BARCODE)\n" +
                    "   AND ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME)\n" +
                    "   AND EO.WORKORDER_OBJECT_CODE LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_CODE)\n" +
                    "   AND EO.WORKORDER_OBJECT_LOCATION LIKE\n" +
                    "       dbo.NVL(?, EO.WORKORDER_OBJECT_LOCATION)";
            sqlArgs.add(userAccount.getOrganizationId());
            sqlArgs.add(dto.getNewBarcode());
            sqlArgs.add(dto.getNameTo());
            sqlArgs.add(dto.getWorkorderObjectCode());
            sqlArgs.add(dto.getNewAssetsLocation());
		}
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	public SQLModel getSynAssModel(String systemId) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SBFIFATdAssetsTransInCompanyDTO dto = (SBFIFATdAssetsTransInCompanyDTO) dtoParameter;
		String sqlStr = "";
        if (!dto.getTransferType().equals("OTHER")) {
            sqlStr = "SELECT\n" +
                     "       EII.SYSTEMID,\n" +
                     "       CASE ASTH.TRANSFER_TYPE WHEN 'INN_DEPT' THEN '部门内调拨' ELSE '部门间调拨' END TRANSFER_TYPE,\n" +
                     "       ASTH.TRANS_NO TRANS_NO,\n" +
                     "       EII.BARCODE OLD_BARCODE,\n" +
                     "       EFA.TAG_NUMBER NEW_BARCODE,\n" +
                     "       ESI.ITEM_NAME NEW_ITEM_NAME,\n" +
                     "       ESI.ITEM_SPEC NEW_ITEM_SPEC,\n" +
                     "       EII.RESPONSIBILITY_USER NEW_USER,\n" +
                     "       AME1.USER_NAME NEW_USER_NAME,\n" +
                     "       AME1.EMPLOYEE_NUMBER NEW_EMPLOYEE_NUMBER,\n" +
                     "       EII.RESPONSIBILITY_DEPT NEW_DEPT,\n" +
                     "       AMD1.DEPT_NAME NEW_DEPT_NAME,\n" +
                     "       EO.WORKORDER_OBJECT_LOCATION NEW_ASSETS_LOCATION,\n" +
                     "  	 EO.WORKORDER_OBJECT_CODE ,\n"+
                     " 		 AME2.EMPLOYEE_NUMBER,\n"+
                     "       EII.ORGANIZATION_ID,\n" +
                     "       EFA.ASSET_ID,\n" +
                     "       EFA.TAG_NUMBER OLD_BARDOE,\n" +
                     "       EFA.ASSET_NUMBER,\n" +
                     "       EFA.ASSETS_DESCRIPTION OLD_ASSETS_DESCRIPTION,\n" +
                     "       EFA.MODEL_NUMBER OLD_MODEL_NUMBER,\n" +
                     "       EFA.ASSETS_LOCATION OLD_ASSETS_LOCATION,\n" +
                     "       AME2.EMPLOYEE_ID OLD_USER,\n" +
                     "       EFA.ASSIGNED_TO_NAME OLD_USER_NAME,\n" +
                     "       AMD2.DEPT_CODE OLD_DEPT_CODE,\n" +
                     "       AMD2.DEPT_NAME OLD_DEPT_NAME,\n" +
                     "       EFA.DEPRECIATION_ACCOUNT,\n" +
                     "       dbo.APP_GET_COUNTY_CODE(ASTL.RESPONSIBILITY_DEPT) COST_CENTER_CODE\n" +
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
                     "       ETS_ITEM_MATCH_TD          EIM,\n" +
                     "       ETS_FA_ASSETS_TD        EFA\n" +
                     "WHERE  EII.ITEM_CODE = ESI.ITEM_CODE\n" +
                     "       AND ESI.ITEM_CATEGORY = EFV.CODE\n" +
                     "       AND EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID\n" +
                     "       AND EFVS.CODE = 'ITEM_TYPE'\n" +
//                    "        AND EFA.COMPANY_CODE=AMD1.COMPANY_CODE\n" +
//                    "      AND EFA.COMPANY_CODE=AMD2.COMPANY_CODE\n" +
//                    "       AND EFA.COMPANY_CODE=AME1.COMPANY_CODE\n" +
//                    "      AND EFA.COMPANY_CODE=AME2.COMPANY_CODE  " +
                     "       AND EII.RESPONSIBILITY_USER = AME1.EMPLOYEE_ID\n" +
                     "       AND EII.RESPONSIBILITY_DEPT *= AMD1.DEPT_CODE\n" +
                     "       AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                     "       AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                     "       AND EII.SYSTEMID = EIM.SYSTEMID\n" +
                     "       AND EIM.ASSET_ID = EFA.ASSET_ID\n" +
                     "       AND EFA.ASSIGNED_TO_NUMBER *= AME2.EMPLOYEE_NUMBER\n" +
                     "       AND EFA.COMPANY_CODE *= AME2.COMPANY_CODE\n" +     
                     "       AND AME2.DEPT_CODE *= AMD2.DEPT_CODE\n" +
                     "       AND ASTH.TRANS_ID=ASTL.TRANS_ID\n" +
                     "       AND ASTL.BARCODE=EII.BARCODE\n" +
                     "       AND EII.FINANCE_PROP = 'TD_ASSETS'\n" +
                     "       AND AME1.ENABLED = 'Y'\n" +
                     "       AND ((ASTH.TRANS_STATUS ='APPROVED' AND ASTH.TRANSFER_TYPE='INN_DEPT_RFU' ) OR ASTH.TRANS_STATUS = 'CONFIRMD')  \n" +
                     "		 AND EII.SYSTEMID IN ("	+systemId+")\n"+
                     "       AND EII.ORGANIZATION_ID = ?\n" +
                     "       AND ASTH.TRANSFER_TYPE = ?\n" +
                     "       AND NOT EXISTS (SELECT NULL\n" +
                     "                         FROM ETS_MISFA_UPDATE_LOG EMUL\n" +
                     "                        WHERE EMUL.ASSET_ID = EFA.ASSET_ID\n" +
                     "                          AND EMUL.TRANSACTION_NO = ASTH.TRANS_NO\n" +
                     "                          AND EMUL.TRANS_STATUS = 1)";
            sqlArgs.add(userAccount.getOrganizationId());
            sqlArgs.add(dto.getTransferType());
        } else {
            sqlStr = "SELECT EII.SYSTEMID,\n" +
                    "       '其它' TRANSFER_TYPE,\n" +
                    "       '' TRANS_NO,\n" +
                    "       EII.BARCODE OLD_BARCODE,\n" +
                    "       EFA.TAG_NUMBER NEW_BARCODE,\n" +
                    "       EFA.ASSET_NUMBER,\n" +
                    "       ESI.ITEM_NAME NEW_ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC NEW_ITEM_SPEC,\n" +
                    "       EII.RESPONSIBILITY_DEPT NEW_DEPT,\n" +
                    "       AMD.DEPT_NAME NEW_DEPT_NAME,\n" +
                    "       EO.WORKORDER_OBJECT_LOCATION NEW_ASSETS_LOCATION,\n" +
                    "       EO.WORKORDER_OBJECT_CODE,\n" +
                    "       EFA.ASSET_ID,\n" +
                    "       EFA.TAG_NUMBER OLD_BARDOE,\n" +
                    "       EFA.ASSETS_LOCATION OLD_ASSETS_LOCATION,\n" +
                    "       '' OLD_DEPT_CODE,\n" +
                    "       '' OLD_DEPT_NAME,\n" +
                    "       EII.RESPONSIBILITY_USER NEW_USER,\n" +
                    "       AME2.USER_NAME NEW_USER_NAME,\n" +
                    "       EFA.ASSIGNED_TO_NAME OLD_USER_NAME,\n" +
                    "       AME2.EMPLOYEE_NUMBER NEW_EMPLOYEE_NUMBER,\n" +
                    "       dbo.APP_GET_COUNTY_CODE(EII.RESPONSIBILITY_DEPT) COST_CENTER_CODE\n" +
                    "  FROM ETS_ITEM_INFO      EII,\n" +
                    "       ETS_FA_ASSETS_TD      EFA,\n" +
                    "       ETS_ITEM_MATCH_TD     EIM,\n" +
                    "       ETS_ITEM_MATCH_TD_REC EIMR,\n" +
                    "       ETS_OBJECT         EO,\n" +
                    "       AMS_OBJECT_ADDRESS AOA,\n" +
                    "       AMS_MIS_DEPT       AMD,\n" +
                    "       AMS_MIS_EMPLOYEE   AME,\n" +
                    "       AMS_MIS_EMPLOYEE   AME2,\n" +
                    "       ETS_SYSTEM_ITEM    ESI\n" +
                    " WHERE EII.SYSTEMID = EIM.SYSTEMID\n" +
                    "   AND EIM.ASSET_ID = EFA.ASSET_ID\n" +
                    "   AND EIM.SYSTEMID = EIMR.SYSTEM_ID\n" +
                    "   AND EIM.ASSET_ID = EIMR.ASSET_ID\n" +
                    "   AND EII.BARCODE = EFA.TAG_NUMBER\n" +
                    "   AND EO.WORKORDER_OBJECT_NO = AOA.OBJECT_NO\n" +
                    "   AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                    "   AND EII.ORGANIZATION_ID = AOA.ORGANIZATION_ID\n" +
                    "   AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE\n" +
                    "   AND EFA.ASSIGNED_TO_NUMBER *= AME.EMPLOYEE_NUMBER\n" +
                    "   AND EFA.COMPANY_CODE *= AME.COMPANY_CODE\n" +     
                    "   AND EII.RESPONSIBILITY_USER = AME2.EMPLOYEE_ID\n" +
                    "   AND ESI.ITEM_CODE = EII.ITEM_CODE\n" +
                    "   AND EFA.COST <> 0\n" +
                    "   AND (EO.OBJECT_CATEGORY < '70' OR EO.OBJECT_CATEGORY = '80')\n" +
                    "   AND NOT EXISTS (SELECT NULL\n" +
                    "                     FROM ETS_MISFA_UPDATE_LOG EMUL\n" +
                    "                    WHERE EMUL.ASSET_ID = EFA.ASSET_ID\n" +
                    "                      AND EMUL.TRANS_STATUS = 1\n" +
                    "                      AND CONVERT(INT, CONVERT(CHAR, EMUL.CREATION_DATE, 112)) = CONVERT(INT, CONVERT(CHAR, GETDATE(), 112)))\n" +
                    "   AND EII.FINANCE_PROP = 'TD_ASSETS'\n" +
                    "   AND AME.ENABLED = 'Y'\n" +
                    "   AND (EFA.ASSETS_LOCATION_CODE <> EO.WORKORDER_OBJECT_CODE OR\n" +
                    "       dbo.NVL(EFA.ASSIGNED_TO_NUMBER, 'A') <>\n" +
                    "       dbo.NVL(AME2.EMPLOYEE_NUMBER, 'A'))\n" +
                    "   AND EII.ORGANIZATION_ID = ?\n" +
                    "   AND EII.SYSTEMID IN("+systemId+")";
            sqlArgs.add(userAccount.getOrganizationId());
        }
        sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

    public SQLModel getAutoSynAssModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
        String sqlStr = "SELECT EII.SYSTEMID,\n" +
                "       CASE ASTH.TRANSFER_TYPE\n" +
                "         WHEN 'INN_DEPT' THEN\n" +
                "          '部门内调拨'\n" +
                "         ELSE\n" +
                "          '部门间调拨'\n" +
                "       END TRANSFER_TYPE,\n" +
                "       ASTH.TRANS_NO TRANS_NO,\n" +
                "       EII.BARCODE OLD_BARCODE,\n" +
                "       EFA.TAG_NUMBER NEW_BARCODE,\n" +
                "       ESI.ITEM_NAME NEW_ITEM_NAME,\n" +
                "       ESI.ITEM_SPEC NEW_ITEM_SPEC,\n" +
                "       EII.RESPONSIBILITY_USER NEW_USER,\n" +
                "       AME1.USER_NAME NEW_USER_NAME,\n" +
                "       AME1.EMPLOYEE_NUMBER NEW_EMPLOYEE_NUMBER,\n" +
                "       EII.RESPONSIBILITY_DEPT NEW_DEPT,\n" +
                "       AMD1.DEPT_NAME NEW_DEPT_NAME,\n" +
                "       EO.WORKORDER_OBJECT_LOCATION NEW_ASSETS_LOCATION,\n" +
                "       EO.WORKORDER_OBJECT_CODE,\n" +
                "       AME2.EMPLOYEE_NUMBER,\n" +
                "       EII.ORGANIZATION_ID,\n" +
                "       EFA.ASSET_ID,\n" +
                "       EFA.TAG_NUMBER OLD_BARDOE,\n" +
                "       EFA.ASSET_NUMBER,\n" +
                "       EFA.ASSETS_DESCRIPTION OLD_ASSETS_DESCRIPTION,\n" +
                "       EFA.MODEL_NUMBER OLD_MODEL_NUMBER,\n" +
                "       EFA.ASSETS_LOCATION OLD_ASSETS_LOCATION,\n" +
                "       AME2.EMPLOYEE_ID OLD_USER,\n" +
                "       EFA.ASSIGNED_TO_NAME OLD_USER_NAME,\n" +
                "       AMD2.DEPT_CODE OLD_DEPT_CODE,\n" +
                "       AMD2.DEPT_NAME OLD_DEPT_NAME,\n" +
                "       EFA.DEPRECIATION_ACCOUNT,\n" +
                "       dbo.APP_GET_COUNTY_CODE(ASTL.RESPONSIBILITY_DEPT) COST_CENTER_CODE\n" +
                "  FROM ETS_FLEX_VALUE_SET      EFVS,\n" +
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
                "       ETS_ITEM_MATCH_TD       EIM,\n" +
                "       ETS_FA_ASSETS_TD        EFA\n" +
                " WHERE EII.ITEM_CODE = ESI.ITEM_CODE\n" +
                "   AND ESI.ITEM_CATEGORY = EFV.CODE\n" +
                "   AND EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID\n" +
                "   AND EFVS.CODE = 'ITEM_TYPE'\n" +
                "   AND EII.RESPONSIBILITY_USER = AME1.EMPLOYEE_ID\n" +
                "   AND EII.RESPONSIBILITY_DEPT *= AMD1.DEPT_CODE\n" +
                "   AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                "   AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                "   AND EII.SYSTEMID = EIM.SYSTEMID\n" +
                "   AND EIM.ASSET_ID = EFA.ASSET_ID\n" +
                "   AND EFA.ASSIGNED_TO_NUMBER *= AME2.EMPLOYEE_NUMBER\n" +
                "   AND EFA.COMPANY_CODE *= AME2.COMPANY_CODE\n" +     
                "   AND AME2.DEPT_CODE *= AMD2.DEPT_CODE\n" +
                "   AND ASTH.TRANS_ID = ASTL.TRANS_ID\n" +
                "   AND ASTL.BARCODE = EII.BARCODE\n" +
                "   AND EII.FINANCE_PROP = 'TD_ASSETS'\n" +
                "   AND AME1.ENABLED = 'Y'\n" +
                "    AND ((ASTH.TRANS_STATUS ='APPROVED' AND ASTH.TRANSFER_TYPE='INN_DEPT_RFU' ) OR ASTH.TRANS_STATUS = 'CONFIRMD') \n" +
                "   AND (ASTH.TRANSFER_TYPE = 'INN_DEPT' OR ASTH.TRANSFER_TYPE = 'BTW_DEPT' OR ASTH.TRANSFER_TYPE = 'INN_DEPT_RFU')\n" +
                "   AND NOT EXISTS (SELECT NULL\n" +
                "          FROM ETS_MISFA_UPDATE_LOG EMUL\n" +
                "         WHERE EMUL.ASSET_ID = EFA.ASSET_ID\n" +
                "           AND EMUL.TRANSACTION_NO = ASTH.TRANS_NO\n" +
                "           AND EMUL.TRANS_STATUS = 1)";
        sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}