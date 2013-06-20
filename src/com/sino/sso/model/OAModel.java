package com.sino.sso.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.synchronize.dto.EamSyschronizeDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;

/**
 * Created by IntelliJ IDEA.
 * User: zhoujs
 * Date: 2009-2-18
 * Time: 16:40:19
 * Functiion:
 */
public class OAModel {
    private SfUserDTO userAccount = null;

    public OAModel(SfUserDTO userAccount) {
        this.userAccount = userAccount;
    }

    /**
     * 资产变动直接同步记录检查
     * @return SQLModel
     */
    public SQLModel getAssetsUpdateModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EamSyschronizeDTO dto = new EamSyschronizeDTO();
        String sqlStr;
        sqlStr = "SELECT EII.SYSTEMID\n" +
                "  FROM ETS_SYSTEM_ITEM     ESI,\n" +
                "       ETS_FA_ASSETS       EFA,\n" +
                "       ETS_OBJECT          EO,\n" +
                "       AMS_OBJECT_ADDRESS  AOA,\n" +
                "       ETS_ITEM_MATCH      EIM,\n" +
                "       ETS_ITEM_MATCH_REC  EIMR,\n" +
                "       ETS_ITEM_INFO       EII,\n" +
                "       ETS_COUNTY          EC,\n" +
                "       AMS_MIS_EMPLOYEE    AME,\n" +
                "       AMS_MIS_EMPLOYEE    AME2,\n" +
                "       ETS_PA_PROJECTS_ALL EPPA,\n" +
                "       ETS_OU_CITY_MAP     EOCM,\n"          +
                "       AMS_MIS_DEPT        AMD\n" +
                " WHERE EII.SYSTEMID = EIM.SYSTEMID\n" +
                "   AND EIM.ASSET_ID = EFA.ASSET_ID\n" +
                "   AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                "   AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                "   AND EII.ORGANIZATION_ID = AOA.ORGANIZATION_ID\n" +
                "   AND EFA.ASSIGNED_TO_NUMBER *= AME.EMPLOYEE_NUMBER  \n" +
                "   AND EII.RESPONSIBILITY_USER *= AME2.EMPLOYEE_ID\n" +
                "   AND EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE\n" +
                "   AND EII.ORGANIZATION_ID = AOA.ORGANIZATION_ID\n" +
                "   AND ESI.ITEM_CODE = EII.ITEM_CODE\n" +
                "   AND  " + SyBaseSQLUtil.isNotNull("AME2.USER_NAME") + " \n" +
                "   AND EO.COUNTY_CODE *= EC.COUNTY_CODE\n" +

                "   AND EO.ORGANIZATION_ID = EOCM.ORGANIZATION_ID \n "+
                "   AND EC.COMPANY_CODE = EOCM.COMPANY_CODE \n "      +

                "   AND AME.ENABLED = 'Y'\n" +
                "   AND EIM.SYSTEMID = EIMR.SYSTEM_ID\n" +//匹配人
                "   AND EIMR.MATCH_USER_ID = CONVERT(INT, dbo.NVL(?, CONVERT(VARCHAR, EIMR.MATCH_USER_ID)))\n" +//匹配人
                "   AND (EO.OBJECT_CATEGORY<70 OR EO.OBJECT_CATEGORY=80) \n" +//只有在实际地点才能同步
                "   AND EIM.ASSET_ID = EIMR.ASSET_ID\n" +
                "   AND (EFA.ASSIGNED_TO_NAME <> AME2.USER_NAME OR\n" +
                "       EFA.ASSETS_DESCRIPTION <> ESI.ITEM_NAME OR\n" +
                "       EFA.MODEL_NUMBER <> ESI.ITEM_SPEC OR\n" +
                "       EFA.ASSETS_LOCATION_CODE <> EO.LOCATION_CODE)\n" +
                "   AND NOT EXISTS\n" +
                " (SELECT NULL\n" +
                "          FROM ETS_MISFA_UPDATE_LOG EMUL\n" +
                "         WHERE EMUL.ASSET_ID = EFA.ASSET_ID\n" +
                "           AND (EMUL.TRANS_STATUS = 0 OR\n" +
                "               (EMUL.TRANS_STATUS = 1 AND\n" +
                "                EMUL.CREATION_DATE =\n" +
                "               GETDATE()  )))\n" +
                "   AND EII.FINANCE_PROP = 'ASSETS'\n" +
//                "   AND EII.ORGANIZATION_ID = ?\n" +
                "   AND (" + SyBaseSQLUtil.isNull() + " OR AMD.DEPT_CODE = ?)\n" +
                "   AND ((EO.WORKORDER_OBJECT_CODE LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_CODE)) OR\n" +
                "       (EO.WORKORDER_OBJECT_LOCATION LIKE\n" +
                "       dbo.NVL(?, EO.WORKORDER_OBJECT_LOCATION)))\n" +
                "   AND EII.BARCODE LIKE dbo.NVL(?, EII.BARCODE)\n" +
                "   AND ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME)\n" +
                "   AND EII.PROJECTID *= EPPA.PROJECT_ID\n" +
                "   AND (ESI.ITEM_NAME <> EFA.ASSETS_DESCRIPTION OR\n" +
                "       dbo.NVL(ESI.ITEM_SPEC, 'A') <> dbo.NVL(EFA.MODEL_NUMBER, 'A') OR\n" +
                "       EII.BARCODE <> EFA.TAG_NUMBER OR\n" +
                "       dbo.NVL(EFA.ASSETS_LOCATION, 'A') <> dbo.NVL(EO.WORKORDER_OBJECT_NAME, 'A') OR\n" +
                "       dbo.NVL(EFA.ASSIGNED_TO_NAME, 'A') <> dbo.NVL(AME2.USER_NAME, 'A'))\n" +
                "   AND ESI.ITEM_CATEGORY IN ('NETOPT', 'NETMGR', 'TRANS', 'BSC', 'EXCHG',\n" +
                "        'ELEC', 'DATA', 'BTS', 'CABEL','LAND', 'HOUSE', 'OTHERS')\n";
        sqlArgs.add(dto.getMatchUserId());
//        sqlArgs.add(userAccount.getOrganizationId());
        sqlArgs.add(dto.getDeptNameOption());
        sqlArgs.add(dto.getDeptNameOption());
        sqlArgs.add(dto.getNewAssetsLocation());
        sqlArgs.add(dto.getNewAssetsLocation());
        sqlArgs.add(dto.getNewBarcode());
        sqlArgs.add(dto.getNameTo());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    /**
     * 资产调拨结果同步(含公司间)
     * @param transferType 调拨类型
     * @return SQLModel
     */
    public SQLModel getAssetsCommitModel(String transferType) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EamSyschronizeDTO dto = new EamSyschronizeDTO();
        dto.setTransferType(transferType);
        String sqlStr = "";
        if (!transferType.equals("BTW_COMP")) {
            sqlStr = "SELECT\n" +
                    "       EII.SYSTEMID,\n" +
                    " CASE WHEN ASTH.TRANSFER_TYPE='INN_DEPT' THEN  '部门内调拨' ELSE '部门间调拨'  END TRANSFER_TYPE,"+
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
                    "       EFA.DEPRECIATION_ACCOUNT\n" +
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
                    "       ETS_ITEM_MATCH          EIM,\n" +
                    "       ETS_FA_ASSETS           EFA\n" +
                    "WHERE  EII.ITEM_CODE = ESI.ITEM_CODE\n" +
                    "       AND ESI.ITEM_CATEGORY = EFV.CODE\n" +
                    "       AND EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID\n" +
                    "       AND EFVS.CODE = 'ITEM_TYPE'\n" +
                    "       AND EII.RESPONSIBILITY_USER = AME1.EMPLOYEE_ID\n" +
                    "       AND EII.RESPONSIBILITY_DEPT = AMD1.DEPT_CODE\n" +
                    "       AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                    "       AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                    "       AND EII.SYSTEMID = EIM.SYSTEMID\n" +
                    "       AND EIM.ASSET_ID = EFA.ASSET_ID\n" +
                    "       AND EFA.ASSIGNED_TO_NUMBER *= AME2.EMPLOYEE_NUMBER\n" +
                    "       AND AME2.DEPT_CODE *= AMD2.DEPT_CODE\n" +
                    "       AND ASTH.TRANS_ID=ASTL.TRANS_ID\n" +
                    "       AND ASTL.BARCODE=EII.BARCODE\n" +
                    "       AND EII.FINANCE_PROP = 'ASSETS'\n" +
                    "       AND AME1.ENABLED = 'Y'\n" +
//                    " AND EII.ORGANIZATION_ID = ?\n" +
                    " AND NOT EXISTS (SELECT NULL\n" +
                    "              FROM ETS_MISFA_UPDATE_LOG EMUL\n" +
                    "             WHERE EMUL.ASSET_ID = EFA.ASSET_ID\n" +
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
//            sqlArgs.add(userAccount.getOrganizationId());
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
                    "       EFA.ASSET_ID,\n" +
                    "       ASTL.BARCODE OLD_BARDOE,\n" +
                    "       EFA.ASSET_NUMBER,\n" +
                    "       EFA.ASSETS_DESCRIPTION OLD_ASSETS_DESCRIPTION,\n" +
                    "       EFA.MODEL_NUMBER OLD_MODEL_NUMBER,\n" +
                    "       EFA.ASSETS_LOCATION OLD_ASSETS_LOCATION,\n" +
                    "       AME2.EMPLOYEE_ID OLD_USER,\n" +
                    "       EFA.ASSIGNED_TO_NAME OLD_USER_NAME,\n" +
                    "       AMD2.DEPT_CODE OLD_DEPT_CODE,\n" +
                    "       AMD2.DEPT_NAME OLD_DEPT_NAME,\n" +
                    "       EFA.DEPRECIATION_ACCOUNT\n" +
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
                    "       ETS_ITEM_MATCH          EIM,\n" +
                    "       ETS_FA_ASSETS           EFA,\n" +
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
                    "       AND EIIO.SYSTEMID = EIM.SYSTEMID\n" +
                    "       AND EIM.ASSET_ID = EFA.ASSET_ID\n" +
                    "       AND EFA.ASSIGNED_TO_NUMBER *= AME2.EMPLOYEE_NUMBER\n" +
                    "       AND AME2.DEPT_CODE *= AMD2.DEPT_CODE\n" +
                    "       AND ASTH.TRANS_ID = ASTL.TRANS_ID\n" +
                    "       AND EIIN.FINANCE_PROP = 'UNKNOW'\n" +
                    "       AND AME1.ENABLED = 'Y'\n" +
                    "       AND ASTH.TRANSFER_TYPE = 'BTW_COMP'\n" +
                    "       AND NOT EXISTS (SELECT NULL\n" +
                    "        FROM   ETS_MISFA_UPDATE_LOG EMUL\n" +
                    "        WHERE  EMUL.ASSET_ID = EFA.ASSET_ID\n" +
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
//            if (!userAccount.isProvinceUser()) {
//                sqlStr += " AND EIIN.ORGANIZATION_ID = ?";
//                sqlArgs.add(userAccount.getOrganizationId());
//            }
            sqlStr += " ORDER BY  ASTH.TRANS_NO";
        }
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

}
