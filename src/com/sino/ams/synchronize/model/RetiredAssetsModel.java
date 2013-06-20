package com.sino.ams.synchronize.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.synchronize.dto.EamSyschronizeDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;

/**
 * User: zhoujs
 * Date: 2008-7-3
 * Time: 11:10:34
 * Function:
 */
public class RetiredAssetsModel extends AMSSQLProducer {
    private EamSyschronizeDTO dto = null;

    /**
     * 功能：eAM新增地点同步 数据库SQL构造层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter EtsItemMatchDTO 本次操作的数据
     */
    public RetiredAssetsModel(SfUserDTO userAccount, EamSyschronizeDTO dtoParameter) {
        super(userAccount, dtoParameter);
        dto = dtoParameter;
    }

    /**
     * 功能：框架自动生成LOCUS页面翻页查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getPageQueryModel()  {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EamSyschronizeDTO locus = (EamSyschronizeDTO) dtoParameter;
        String sqlStr;
        sqlStr =
                "SELECT EII.SYSTEMID,\n" +
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
                        "       EII.BARCODE,\n" +
                        "       ESI.ITEM_NAME,\n" +
                        "       EFA.DEPRECIATION_ACCOUNT,\n" +
                        "       EFA.DATE_PLACED_IN_SERVICE,\n" +
                        "       EFA.COST,\n" +
                        "       EFA.DEPRN_RESERVE,\n" +
                        "       EFA.SCRAP_VALUE,\n" +
                        "       EFA.LIFE_IN_YEARS,\n" +
                        "       TRUNC((EFA.LIFE_IN_YEARS * 12 -MONTHS_BETWEEN(GETDATE(), EFA.DATE_PLACED_IN_SERVICE)),1) REMAIN_MONTHS\n" +
                        "  FROM ETS_ITEM_INFO           EII,\n" +
                        "       ETS_SYSTEM_ITEM         ESI,\n" +
                        "       ETS_FLEX_VALUE_SET      EFVS,\n" +
                        "       ETS_FLEX_VALUES         EFV,\n" +
                        "       AMS_OBJECT_ADDRESS      AOA,\n" +
                        "       ETS_OBJECT              EO,\n" +
                        "       AMS_MIS_EMPLOYEE        AME1,\n" +
                        "       AMS_MIS_DEPT            AMD1,\n" +
                        "       ETS_ITEM_MATCH          EIM,\n" +
                        "       ETS_FA_ASSETS           EFA,\n" +
                        "       AMS_MIS_EMPLOYEE        AME2,\n" +
                        "       AMS_MIS_DEPT            AMD2,\n" +
                        "       AMS_ASSETS_TRANS_LINE   ASTL,\n" +
                        "       AMS_ASSETS_TRANS_HEADER ASTH\n" +
                        " WHERE EII.ITEM_CODE = ESI.ITEM_CODE\n" +
                        "   AND ESI.ITEM_CATEGORY = EFV.CODE\n" +
                        "   AND EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID\n" +
                        "   AND EFVS.CODE = 'ITEM_TYPE'\n" +
                        "   AND EII.RESPONSIBILITY_USER = AME1.EMPLOYEE_ID\n" +
                        "   AND EII.RESPONSIBILITY_DEPT = AMD1.DEPT_CODE\n" +
                        "   AND EII.ADDRESS_ID *= AOA.ADDRESS_ID\n" +
                        "   AND AOA.OBJECT_NO *= EO.WORKORDER_OBJECT_NO\n" +
                        "   AND EII.SYSTEMID = EIM.SYSTEMID\n" +
                        "   AND EIM.ASSET_ID = EFA.ASSET_ID\n" +
                        "   AND EFA.ASSIGNED_TO_NUMBER *= AME2.EMPLOYEE_NUMBER\n" +
                        "   AND AME2.DEPT_CODE *= AMD2.DEPT_CODE\n" +
                        "   AND ASTH.TRANS_ID = ASTL.TRANS_ID\n" +
                        "   AND ASTL.BARCODE = EII.BARCODE\n" +
                        "   AND EII.FINANCE_PROP = 'ASSETS'\n" +
                        "   AND AME1.ENABLED = 'Y'\n" +
                        "   AND  " + SyBaseSQLUtil.isNotNull("AME1.ENABLED") + " \n" +
                        "   AND EII.ORGANIZATION_ID = ?\n" +
                        "   AND ASTH.TRANS_TYPE = 'ASS-DIS'\n" +  //单据类型
                        "   AND ASTH.TRANS_STATUS = 'APPROVED'\n" + //单据状态
                        "   AND NOT EXISTS (SELECT NULL\n" +
                        "          FROM ETS_MISFA_UPDATE_LOG EMUL\n" +
                        "         WHERE EMUL.ASSET_ID = EFA.ASSET_ID\n" +
                        "           AND EMUL.TRANS_STATUS = 0)\n" +
                        "   AND EXISTS (SELECT NULL\n" +
                        "          FROM AMS_ASSETS_CHK_LOG ASCL\n" +
                        "         WHERE ASCL.BARCODE = EII.BARCODE\n" +
                        "           AND ASCL.SYN_STATUS IN (0, 2)\n" +
                        "           AND ASCL.IS_EXIST = 'Y'\n" +
                        "           AND ASCL.ORDER_TYPE = 'ASS-DIS')\n" +
                        "   AND ASTH.TRANS_NO LIKE dbo.NVL(?, ASTH.TRANS_NO)\n" +
                        "   AND EO.WORKORDER_OBJECT_LOCATION LIKE\n" +
                        "       dbo.NVL(?, EO.WORKORDER_OBJECT_LOCATION)\n" +
                        "   AND EII.BARCODE LIKE dbo.NVL(?, EII.BARCODE)\n" +
                        "   AND ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME)";

        //   sqlArgs.add(locus.getWorkorderObjectCode());
        sqlArgs.add(userAccount.getOrganizationId());
        sqlArgs.add(dto.getTransNo());
        sqlArgs.add(dto.getNewAssetsLocation());
        sqlArgs.add(dto.getNewBarcode());
        sqlArgs.add(dto.getNameTo());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

}
