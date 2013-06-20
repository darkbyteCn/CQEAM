package com.sino.td.synAssets.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.synchronize.dto.EamSyschronizeDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;

/**
 * Created by   李轶
 * Date:        2009-9-9
 * Function     资产报废同步
 */
public class TdAssetsDiscardedModel extends AMSSQLProducer {
/**
	 * 功能：资产报废同步 数据库SQL构造层构造函数
	 *
	 * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EtsItemMatchDTO 本次操作的数据
	 */
	public TdAssetsDiscardedModel(SfUserDTO userAccount, EamSyschronizeDTO dtoParameter) {
		super(userAccount, dtoParameter);
        dto = dtoParameter;
	}

    private EamSyschronizeDTO dto = null;
/**
	 * 功能：框架自动生成LOCUS页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 */
	public SQLModel getPageQueryModel()  {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr;
        sqlStr = "SELECT EII.SYSTEMID,\n" +
                        "       ASTH.TRANS_NO,\n"         +
                        "       EII.BARCODE NEW_BARCODE,\n" +
                        "       ESI.ITEM_NAME NEW_ITEM_NAME,\n" +
                        "       ESI.ITEM_SPEC NEW_ITEM_SPEC,\n" +
//                        "       EII.RESPONSIBILITY_USER NEW_USER,\n" +
                        "       AME1.USER_NAME NEW_USER_NAME,\n" +
                        "       AMD1.DEPT_NAME NEW_DEPT_NAME,\n" +
//                        "       EII.RESPONSIBILITY_DEPT NEW_DEPT,\n" +
                        "       EO.WORKORDER_OBJECT_LOCATION NEW_ASSETS_LOCATION,\n" +
                        "       EII.ORGANIZATION_ID,\n" +
                        "       EFA.ASSET_ID,\n" +
                        "       EFA.TAG_NUMBER OLD_BARDOE,\n" +
                        "       EFA.ASSET_NUMBER,\n" +
                        "       EFA.ASSETS_DESCRIPTION OLD_ASSETS_DESCRIPTION,\n" +
                        "       EFA.MODEL_NUMBER OLD_MODEL_NUMBER,\n" +
                        "       EFA.ASSETS_LOCATION OLD_ASSETS_LOCATION,\n" +
//                        "       AME2.EMPLOYEE_ID OLD_USER,\n" +
                        "       EFA.ASSIGNED_TO_NAME OLD_USER_NAME,\n" +
//                        "       AMD2.DEPT_CODE OLD_DEPT_CODE,\n" +
                        "       AMD2.DEPT_NAME OLD_DEPT_NAME,\n" +
                        "       EFA.DEPRECIATION_ACCOUNT,\n" +
                        "       EFA.DATE_PLACED_IN_SERVICE,\n" +
                        "       EFA.COST,\n" +
                        "       EFA.DEPRN_RESERVE,\n" +
                        "       EFA.SCRAP_VALUE,\n" +
                        "       EFA.LIFE_IN_YEARS,\n" +
                        "       TRUNC((EFA.LIFE_IN_YEARS * 12 -MONTHS_BETWEEN(GETDATE() , EFA.DATE_PLACED_IN_SERVICE)),1) REMAIN_MONTHS\n" +
                        "  FROM ETS_ITEM_INFO           EII,\n" +
                        "       ETS_SYSTEM_ITEM         ESI,\n" +
                        "       AMS_OBJECT_ADDRESS      AOA,\n" +
                        "       ETS_OBJECT              EO,\n" +
                        "       AMS_MIS_EMPLOYEE        AME1,\n" +
                        "       AMS_MIS_DEPT            AMD1,\n" +
                        "       ETS_ITEM_MATCH_TD       EIM,\n" +
                        "       ETS_FA_ASSETS_TD        EFA,\n" +
                        "       AMS_MIS_EMPLOYEE        AME2,\n" +
                        "       AMS_MIS_DEPT            AMD2,\n" +
                        "       AMS_ASSETS_TRANS_LINE   ASTL,\n" +
                        "       AMS_ASSETS_TRANS_HEADER ASTH\n" +
                        " WHERE EII.ITEM_CODE = ESI.ITEM_CODE\n" +
                        "   AND EII.RESPONSIBILITY_USER *= AME1.EMPLOYEE_ID\n" +
                        "   AND EII.RESPONSIBILITY_DEPT = AMD1.DEPT_CODE\n" +
                        "   AND EII.ADDRESS_ID *= AOA.ADDRESS_ID\n" +
                        "   AND AOA.OBJECT_NO *= EO.WORKORDER_OBJECT_NO\n" +
                        "   AND EII.SYSTEMID = EIM.SYSTEMID\n" +
                        "   AND EIM.ASSET_ID = EFA.ASSET_ID\n" +
                        "   AND EFA.ASSIGNED_TO_NUMBER *= AME2.EMPLOYEE_NUMBER\n" +
                        "   AND AME2.DEPT_CODE *= AMD2.DEPT_CODE\n" +
                        "   AND ASTH.TRANS_ID = ASTL.TRANS_ID\n" +
                        "   AND ASTL.BARCODE = EII.BARCODE\n" +
                        "   AND EII.ITEM_STATUS = 'TO_DISCARD'\n" +
                        "   AND EII.FINANCE_PROP = 'TD_ASSETS'\n" +
                        "   AND (AME1.ENABLED = 'Y' OR AME1.ENABLED  " + SyBaseSQLUtil.isNullNoParam() + " )\n";
                if(userAccount.getOrganizationId() !=101){
                    sqlStr += "   AND EII.ORGANIZATION_ID = ?\n";
                    sqlArgs.add(userAccount.getOrganizationId());
                }
                    sqlStr += "   AND ASTH.TRANS_TYPE = 'ASS-DIS'\n" +  //单据类型
                        "   AND (ASTH.TRANS_STATUS = 'COMPLETED' OR ASTH.TRANS_STATUS = 'APPROVED')\n" + //单据状态
                        "   AND NOT EXISTS (SELECT NULL\n" +
                        "          FROM ETS_MISFA_UPDATE_LOG EMUL\n" +
                        "         WHERE EMUL.ASSET_ID = EFA.ASSET_ID\n" +
                        "           AND EMUL.TRANS_STATUS = 0)\n" +
                        "   AND EXISTS (SELECT NULL\n" +
                        "                FROM AMS_ASSETS_CHK_LOG ASCL\n" +
                        "               WHERE ASCL.BARCODE = EII.BARCODE\n" +
                        "                 AND (ASCL.SYN_STATUS = 0 OR ASCL.SYN_STATUS  " + SyBaseSQLUtil.isNullNoParam() + " )\n" +
                        "                 AND ASCL.IS_EXIST = 'Y')\n" +
                        "   AND (" + SyBaseSQLUtil.isNull() + " OR ASTH.TRANS_NO LIKE ?)\n" +
                        "   AND (" + SyBaseSQLUtil.isNull() + " OR EO.WORKORDER_OBJECT_CODE LIKE ?)\n" +
                        "   AND (" + SyBaseSQLUtil.isNull() + " OR EO.WORKORDER_OBJECT_LOCATION LIKE ?)\n" +
                        "   AND (" + SyBaseSQLUtil.isNull() + " OR EII.BARCODE LIKE ?)\n" +
                        "   AND (" + SyBaseSQLUtil.isNull() + " OR ESI.ITEM_NAME LIKE ?)" +
                        " ORDER BY EII.BARCODE";
        sqlArgs.add(dto.getTransNo());
        sqlArgs.add(dto.getTransNo());
        sqlArgs.add(dto.getWorkorderObjectCode());
        sqlArgs.add(dto.getWorkorderObjectCode());
        sqlArgs.add(dto.getNewAssetsLocation());
        sqlArgs.add(dto.getNewAssetsLocation());
    
        sqlArgs.add(dto.getNewBarcode());
        sqlArgs.add(dto.getNewBarcode());
        sqlArgs.add(dto.getNameTo());
        sqlArgs.add(dto.getNameTo());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}