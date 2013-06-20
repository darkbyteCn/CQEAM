package com.sino.soa.mis.srv.assettransbtwcompanysrv.model;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.soa.mis.srv.assettransbtwcompanysrv.dto.SBFIFAAssetsTransBtwCompanyDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-9-15
 * Time: 15:43:00
 * To change this template use File | Settings | File Templates.
 */
public class SBFIFAAssetsTransBtwCompanyModel extends AMSSQLProducer {
	private SfUserDTO sfUser = null;

	public SBFIFAAssetsTransBtwCompanyModel(SfUserDTO userAccount, SBFIFAAssetsTransBtwCompanyDTO dtoParameter) {
		super(userAccount, dtoParameter);
		sfUser = userAccount;
	}

	public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SBFIFAAssetsTransBtwCompanyDTO dto = (SBFIFAAssetsTransBtwCompanyDTO) dtoParameter;
		String sqlStr = "SELECT EII.SYSTEMID,\n" +
                "       EOCM1.BOOK_TYPE_CODE FROM_BOOK_TYPE_CODE,\n" +
                "       EOCM2.BOOK_TYPE_CODE TO_BOOK_TYPE_CODE,\n" +
                "       EOCM1.COMPANY_CODE COMPANY_FROM,\n" +
                "       EOCM2.COMPANY_CODE COMPANY_TO,\n" +
                "       AATL.FA_CATEGORY_CODE TO_FA_CATEGORY_CODE,\n" +
                "       EFA.ASSET_ID,\n" +
                "       EFA.TAG_NUMBER FROM_TAG_NUMBER,\n" +
                "       AATL.NEW_BARCODE TO_TAG_NUMBER,\n" +
                "       AATH.TRANS_DATE,\n" +
                "       EO.WORKORDER_OBJECT_CODE TO_WORKORDER_OBJECT_CODE,\n" +
                "       AATL.DEPRECIATION_ACCOUNT TO_DEPRECIATION_ACCOUNT,\n" +
                "       AME.EMPLOYEE_NUMBER TO_EMPLOYEE_NUMBER,\n" +
                "       '公司间调拨' TRANSFER_TYPE,\n" +
                "       AATH.TRANS_NO,\n" +
                "       ESI.ITEM_NAME NEW_ITEM_NAME,\n" +
                "       ESI.ITEM_SPEC NEW_ITEM_SPEC,\n" +
                "       EO2.WORKORDER_OBJECT_NAME OLD_ASSETS_LOCATION,\n" +
                "       EO.WORKORDER_OBJECT_NAME NEW_ASSETS_LOCATION,\n" +
                "       AMD1.DEPT_NAME OLD_DEPT_NAME,\n" +
                "       AMD2.DEPT_NAME NEW_DEPT_NAME,\n" +
                "       AME1.USER_NAME OLD_USER_NAME,\n" +
                "       AME2.USER_NAME NEW_USER_NAME\n" +
                "  FROM ETS_ITEM_INFO           EII,\n" +
                "       AMS_ASSETS_TRANS_HEADER AATH,\n" +
                "       AMS_ASSETS_TRANS_LINE   AATL,\n" +
                "       ETS_FA_ASSETS           EFA,\n" +
                "       ETS_ITEM_MATCH          EIM,\n" +
                "       ETS_OU_CITY_MAP         EOCM1,\n" +
                "       ETS_OU_CITY_MAP         EOCM2,\n" +
                "       ETS_OBJECT              EO,\n" +
                "       AMS_OBJECT_ADDRESS      AOA,\n" +
                "       AMS_MIS_EMPLOYEE        AME,\n" +
                "       ETS_SYSTEM_ITEM         ESI,\n" +
                "       ETS_OBJECT              EO2,\n" +
                "       AMS_MIS_DEPT            AMD1,\n" +
                "       AMS_MIS_DEPT            AMD2,\n" +
                "       AMS_MIS_EMPLOYEE        AME1,\n" +
                "       AMS_MIS_EMPLOYEE        AME2\n" +
                " WHERE AATH.TRANS_ID = AATL.TRANS_ID\n" +
                "   AND AATL.BARCODE = EII.BARCODE\n" +
                "   AND EII.SYSTEMID = EIM.SYSTEMID\n" +
                "   AND EFA.ASSET_ID = EIM.ASSET_ID\n" +
                "   AND AATH.FROM_ORGANIZATION_ID = EOCM1.ORGANIZATION_ID\n" +
                "   AND AATH.TO_ORGANIZATION_ID = EOCM2.ORGANIZATION_ID\n" +
                "   AND AATL.ASSIGNED_TO_LOCATION = EO.WORKORDER_OBJECT_NO\n" +
                "   AND EO.WORKORDER_OBJECT_NO = AOA.OBJECT_NO\n" +
                "   AND AATL.RESPONSIBILITY_USER = AME.EMPLOYEE_ID\n" +
                "   AND AATH.TRANSFER_TYPE = 'BTW_COMP'\n" +
                "   AND AATH.TRANS_STATUS = 'CONFIRMD'\n" +
                "   AND EII.ITEM_CODE = ESI.ITEM_CODE\n" +
                "   AND AATL.OLD_LOCATION = EO2.WORKORDER_OBJECT_NO\n" +
                "   AND AATL.OLD_RESPONSIBILITY_DEPT *= AMD1.DEPT_CODE\n" +
                "   AND AATL.RESPONSIBILITY_DEPT *= AMD2.DEPT_CODE\n" +
                "   AND AATL.OLD_RESPONSIBILITY_USER *= AME1.EMPLOYEE_ID\n" +
                "   AND AATL.RESPONSIBILITY_USER *= AME2.EMPLOYEE_ID\n" +
                "   AND AATL.NEW_BARCODE IS NOT NULL\n" +
//                "   AND AATH.TO_ORGANIZATION_ID = ?\n" +
                "   AND NOT EXISTS (SELECT NULL\n" +
                "                     FROM ETS_MISFA_UPDATE_LOG EMUL\n" +
                "                    WHERE EMUL.ASSET_ID = EFA.ASSET_ID\n" +
                "                      AND EMUL.TRANSACTION_NO = AATH.TRANS_NO\n" +
                "                      AND (EMUL.TRANS_STATUS = 1 OR EMUL.TRANS_STATUS = 0))\n" +
                "   AND (? = '' OR AATL.NEW_BARCODE = ?)\n" +
                "   AND (? = '' OR AATH.TRANS_NO = ?)\n" +
                "   AND (? = '' OR EO.WORKORDER_OBJECT_CODE = ?)\n" +
                "   AND (? = '' OR EO.WORKORDER_OBJECT_NAME = ?)\n" +
                " ORDER BY AATH.TRANS_NO";
//      sqlArgs.add(sfUser.getOrganizationId());
        sqlArgs.add(dto.getNewBarcode());
        sqlArgs.add(dto.getNewBarcode());
        sqlArgs.add(dto.getTransNo());
        sqlArgs.add(dto.getTransNo());
        sqlArgs.add(dto.getWorkorderObjectCode());
        sqlArgs.add(dto.getWorkorderObjectCode());
        sqlArgs.add(dto.getNameTo());
        sqlArgs.add(dto.getNameTo());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	public SQLModel getSynAssModel(String systemids) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SBFIFAAssetsTransBtwCompanyDTO dto = (SBFIFAAssetsTransBtwCompanyDTO) dtoParameter;
		String sqlStr = "SELECT EII1.SYSTEMID,\n" +
                "       EOCM1.BOOK_TYPE_CODE FROM_BOOK_TYPE_CODE,\n" +
                "       EOCM2.BOOK_TYPE_CODE TO_BOOK_TYPE_CODE,\n" +
                "       EOCM1.COMPANY_CODE COMPANY_FROM,\n" +
                "       EOCM2.COMPANY_CODE COMPANY_TO,\n" +
                "       AATL.FA_CATEGORY_CODE TO_FA_CATEGORY_CODE,\n" +
                "       EFA.ASSET_ID,\n" +
                "       EFA.TAG_NUMBER FROM_TAG_NUMBER,\n" +
                "       AATL.NEW_BARCODE TO_TAG_NUMBER,\n" +
                "       AATH.TRANS_DATE,\n" +
                "       EO.WORKORDER_OBJECT_CODE TO_WORKORDER_OBJECT_CODE,\n" +
                "       AATL.DEPRECIATION_ACCOUNT TO_DEPRECIATION_ACCOUNT,\n" +
                "       AME.EMPLOYEE_NUMBER TO_EMPLOYEE_NUMBER,\n" +
                "       '公司间调拨' TRANSFER_TYPE,\n" +
                "       AATH.TRANS_NO,\n" +
                "       ESI.ITEM_NAME NEW_ITEM_NAME,\n" +
                "       ESI.ITEM_SPEC NEW_ITEM_SPEC,\n" +
                "       EO2.WORKORDER_OBJECT_NAME OLD_ASSETS_LOCATION,\n" +
                "       EO.WORKORDER_OBJECT_NAME NEW_ASSETS_LOCATION,\n" +
                "       AMD1.DEPT_CODE OLD_DEPT_CODE,\n" +
                "       AMD1.DEPT_NAME OLD_DEPT_NAME,\n" +
                "       AMD2.DEPT_CODE NEW_DEPT,\n" +
                "       AMD2.DEPT_NAME NEW_DEPT_NAME,\n" +
                "       AME1.USER_NAME OLD_USER_NAME,\n" +
                "       AME2.USER_NAME NEW_USER_NAME\n" +
                "  FROM ETS_ITEM_INFO           EII1,\n" +
                "       ETS_ITEM_INFO           EII2,\n" +
                "       AMS_ASSETS_TRANS_HEADER AATH,\n" +
                "       AMS_ASSETS_TRANS_LINE   AATL,\n" +
                "       ETS_FA_ASSETS           EFA,\n" +
                "       ETS_ITEM_MATCH          EIM,\n" +
                "       ETS_OU_CITY_MAP         EOCM1,\n" +
                "       ETS_OU_CITY_MAP         EOCM2,\n" +
                "       ETS_OBJECT              EO,\n" +
                "       AMS_OBJECT_ADDRESS      AOA,\n" +
                "       AMS_MIS_EMPLOYEE        AME,\n" +
                "       ETS_SYSTEM_ITEM         ESI,\n" +
                "       ETS_OBJECT              EO2,\n" +
                "       AMS_MIS_DEPT            AMD1,\n" +
                "       AMS_MIS_DEPT            AMD2,\n" +
                "       AMS_MIS_EMPLOYEE        AME1,\n" +
                "       AMS_MIS_EMPLOYEE        AME2\n" +
                " WHERE AATH.TRANS_ID = AATL.TRANS_ID\n" +
                "   AND AATL.BARCODE = EII1.BARCODE\n" +
                "   AND AATL.NEW_BARCODE = EII2.BARCODE\n" +
                "   AND EII1.SYSTEMID = EIM.SYSTEMID\n" +
                "   AND EFA.ASSET_ID = EIM.ASSET_ID\n" +
                "   AND AATH.FROM_ORGANIZATION_ID = EOCM1.ORGANIZATION_ID\n" +
                "   AND AATH.TO_ORGANIZATION_ID = EOCM2.ORGANIZATION_ID\n" +
                "   AND AATL.ASSIGNED_TO_LOCATION = EO.WORKORDER_OBJECT_NO\n" +
                "   AND EO.WORKORDER_OBJECT_NO = AOA.OBJECT_NO\n" +
                "   AND AATL.RESPONSIBILITY_USER = AME.EMPLOYEE_ID\n" +
                "   AND AATH.TRANSFER_TYPE = 'BTW_COMP'\n" +
                "   AND AATH.TRANS_STATUS = 'CONFIRMD'\n" +
                "   AND EII2.ITEM_CODE = ESI.ITEM_CODE\n" +
                "   AND AATL.OLD_LOCATION = EO2.WORKORDER_OBJECT_NO\n" +
                "   AND AATL.OLD_RESPONSIBILITY_DEPT *= AMD1.DEPT_CODE\n" +
                "   AND AATL.RESPONSIBILITY_DEPT *= AMD2.DEPT_CODE\n" +
                "   AND AATL.OLD_RESPONSIBILITY_USER *= AME1.EMPLOYEE_ID\n" +
                "   AND AATL.RESPONSIBILITY_USER *= AME2.EMPLOYEE_ID\n" +
//                "   AND AATH.TO_ORGANIZATION_ID = ?\n" +
                "   AND NOT EXISTS (SELECT NULL\n" +
                "                     FROM ETS_MISFA_UPDATE_LOG EMUL\n" +
                "                    WHERE EMUL.ASSET_ID = EFA.ASSET_ID\n" +
                "                      AND EMUL.TRANSACTION_NO = AATH.TRANS_NO\n" +
                "                      AND EMUL.TRANS_STATUS = 1)\n" +
                "   AND EII1.SYSTEMID IN ("+systemids +")";
//        sqlArgs.add(sfUser.getOrganizationId());
        sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

    public SQLModel getAutoSynAssModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT EII1.SYSTEMID,\n" +
                "       EOCM1.BOOK_TYPE_CODE FROM_BOOK_TYPE_CODE,\n" +
                "       EOCM2.BOOK_TYPE_CODE TO_BOOK_TYPE_CODE,\n" +
                "       EOCM1.COMPANY_CODE COMPANY_FROM,\n" +
                "       EOCM2.COMPANY_CODE COMPANY_TO,\n" +
                "       AATL.FA_CATEGORY_CODE TO_FA_CATEGORY_CODE,\n" +
                "       EFA.ASSET_ID,\n" +
                "       EFA.TAG_NUMBER FROM_TAG_NUMBER,\n" +
                "       AATL.NEW_BARCODE TO_TAG_NUMBER,\n" +
                "       AATH.TRANS_DATE,\n" +
                "       EO.WORKORDER_OBJECT_CODE TO_WORKORDER_OBJECT_CODE,\n" +
                "       AATL.DEPRECIATION_ACCOUNT TO_DEPRECIATION_ACCOUNT,\n" +
                "       AME.EMPLOYEE_NUMBER TO_EMPLOYEE_NUMBER,\n" +
                "       '公司间调拨' TRANSFER_TYPE,\n" +
                "       AATH.TRANS_NO,\n" +
                "       ESI.ITEM_NAME NEW_ITEM_NAME,\n" +
                "       ESI.ITEM_SPEC NEW_ITEM_SPEC,\n" +
                "       EO2.WORKORDER_OBJECT_NAME OLD_ASSETS_LOCATION,\n" +
                "       EO.WORKORDER_OBJECT_NAME NEW_ASSETS_LOCATION,\n" +
                "       AMD1.DEPT_CODE OLD_DEPT_CODE,\n" +
                "       AMD1.DEPT_NAME OLD_DEPT_NAME,\n" +
                "       AMD2.DEPT_CODE NEW_DEPT,\n" +
                "       AMD2.DEPT_NAME NEW_DEPT_NAME,\n" +
                "       AME1.USER_NAME OLD_USER_NAME,\n" +
                "       AME2.USER_NAME NEW_USER_NAME\n" +
                "  FROM ETS_ITEM_INFO           EII1,\n" +
                "       ETS_ITEM_INFO           EII2,\n" +
                "       AMS_ASSETS_TRANS_HEADER AATH,\n" +
                "       AMS_ASSETS_TRANS_LINE   AATL,\n" +
                "       ETS_FA_ASSETS           EFA,\n" +
                "       ETS_ITEM_MATCH          EIM,\n" +
                "       ETS_OU_CITY_MAP         EOCM1,\n" +
                "       ETS_OU_CITY_MAP         EOCM2,\n" +
                "       ETS_OBJECT              EO,\n" +
                "       AMS_OBJECT_ADDRESS      AOA,\n" +
                "       AMS_MIS_EMPLOYEE        AME,\n" +
                "       ETS_SYSTEM_ITEM         ESI,\n" +
                "       ETS_OBJECT              EO2,\n" +
                "       AMS_MIS_DEPT            AMD1,\n" +
                "       AMS_MIS_DEPT            AMD2,\n" +
                "       AMS_MIS_EMPLOYEE        AME1,\n" +
                "       AMS_MIS_EMPLOYEE        AME2\n" +
                " WHERE AATH.TRANS_ID = AATL.TRANS_ID\n" +
                "   AND AATL.BARCODE = EII1.BARCODE\n" +
                "   AND AATL.NEW_BARCODE = EII2.BARCODE\n" +
                "   AND EII1.SYSTEMID = EIM.SYSTEMID\n" +
                "   AND EFA.ASSET_ID = EIM.ASSET_ID\n" +
                "   AND AATH.FROM_ORGANIZATION_ID = EOCM1.ORGANIZATION_ID\n" +
                "   AND AATH.TO_ORGANIZATION_ID = EOCM2.ORGANIZATION_ID\n" +
                "   AND AATL.ASSIGNED_TO_LOCATION = EO.WORKORDER_OBJECT_NO\n" +
                "   AND EO.WORKORDER_OBJECT_NO = AOA.OBJECT_NO\n" +
                "   AND AATL.RESPONSIBILITY_USER = AME.EMPLOYEE_ID\n" +
                "   AND AATH.TRANSFER_TYPE = 'BTW_COMP'\n" +
                "   AND AATH.TRANS_STATUS = 'CONFIRMD'\n" +
                "   AND EII2.ITEM_CODE = ESI.ITEM_CODE\n" +
                "   AND AATL.OLD_LOCATION = EO2.WORKORDER_OBJECT_NO\n" +
                "   AND AATL.OLD_RESPONSIBILITY_DEPT *= AMD1.DEPT_CODE\n" +
                "   AND AATL.RESPONSIBILITY_DEPT *= AMD2.DEPT_CODE\n" +
                "   AND AATL.OLD_RESPONSIBILITY_USER *= AME1.EMPLOYEE_ID\n" +
                "   AND AATL.RESPONSIBILITY_USER *= AME2.EMPLOYEE_ID\n" +
                "   AND NOT EXISTS (SELECT NULL\n" +
                "          FROM ETS_MISFA_UPDATE_LOG EMUL\n" +
                "         WHERE EMUL.ASSET_ID = EFA.ASSET_ID\n" +
                "           AND EMUL.TRANSACTION_NO = AATH.TRANS_NO\n" +
                "           AND EMUL.TRANS_STATUS = 1)";
        sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}