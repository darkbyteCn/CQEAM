package com.sino.soa.td.srv.assetretire.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;
import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.soa.td.srv.assetretire.dto.TDAssetRetirementDTO;

/**
 * User: wangzp
 * Date: 2011-09-08 
 * Function : 资产报废同步_TD
 */
public class TDAssetRetirementModel extends AMSSQLProducer {
    private TDAssetRetirementDTO dto = null;

    public TDAssetRetirementModel(BaseUserDTO userAccount, TDAssetRetirementDTO dtoParameter) {
        super(userAccount, dtoParameter);
        dto = dtoParameter;
    }
    
    /** 
     * 查询公司底下符合条件的报废资产(不区分TD和非TD)
     */
    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT EII.SYSTEMID,\n" +
                "       EOCM.ORGANIZATION_ID,\n" +
                "       EOCM.COMPANY,\n" +
                "       EOCM.BOOK_TYPE_CODE,\n" +
                "       EII.BARCODE,\n" +
                "       ESI.ITEM_NAME,\n" +
                "       EII.ITEM_STATUS,\n" +
                "       AATH.CREATION_DATE RRETIRED_DATE,\n" +
                "       EFA.ASSET_ID,\n" +
                "       EFA.TAG_NUMBER,\n" +
                "       EFA.COST,\n" +
                "       AATL.RETIREMENT_COST,\n" +
                "       GETDATE() DATE_RRETIRED\n" +
                "  FROM AMS_ASSETS_TRANS_HEADER AATH,\n" +
                "       AMS_ASSETS_TRANS_LINE   AATL,\n" +
                "       ETS_ITEM_INFO           EII,\n" +
                "       ETS_SYSTEM_ITEM         ESI,\n" +
                "       ETS_OU_CITY_MAP         EOCM,\n" +
                "       ETS_FA_ASSETS_TD        EFA,\n" +
                "       ETS_ITEM_MATCH_TD       EIM\n" +
                " WHERE AATH.TRANS_ID = AATL.TRANS_ID\n" +
                "   AND AATH.TRANS_TYPE = 'ASS-DIS'\n" +
                "   AND AATH.TRANS_STATUS = 'COMPLETED'\n" +
                "   AND EIM.SYSTEMID = EII.SYSTEMID\n" +
                "   AND EIM.ASSET_ID = EFA.ASSET_ID\n" +
                "   AND EII.BARCODE = AATL.BARCODE\n" +
                "   AND EII.ITEM_CODE = ESI.ITEM_CODE\n" +
                "   AND EII.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
                "   AND EII.ITEM_STATUS = 'TO_DISCARD'\n" +
                "   AND NOT EXISTS (SELECT NULL\n" +
                "          FROM ETS_MISFA_UPDATE_LOG EMUL\n" +
                "         WHERE EMUL.BARCODE = EII.BARCODE\n" +
                "           AND EMUL.UPDATE_TYPE = 'SRV_RETIRE'\n" +
                "           AND (EMUL.TRANS_STATUS = 0 OR EMUL.TRANS_STATUS = 1))\n" ;
                
                if (userAccount.isProvinceUser() || userAccount.isSysAdmin()) {  //系统管理员
                	sqlStr += "  AND ( ? = -1 OR EII.ORGANIZATION_ID = ?) \n" ;
                	sqlArgs.add(dto.getOrganizationId());
                	sqlArgs.add(dto.getOrganizationId());
                	
                }else{
                	sqlStr += "  AND EII.ORGANIZATION_ID = ? \n" ;
                	sqlArgs.add(dto.getOrganizationId());
                }
     
                if(!dto.getBarcode().equals("")){
                	sqlStr += "  AND EII.BARCODE = ? \n" ;
                	sqlArgs.add(dto.getBarcode());
                	
                }
                if(!dto.getItemname().equals("")){
                	sqlStr += "  AND ESI.ITEM_NAME = ? \n" ;
                	sqlArgs.add(dto.getItemname());
                }
                sqlStr += " ORDER BY EOCM.ORGANIZATION_ID";

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    public SQLModel getRetireAssetsModel(String systemIds) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr =
                "SELECT EII.SYSTEMID,\n" +
                        "       EOCM.ORGANIZATION_ID,\n" +
                        "       EOCM.COMPANY,\n" +
                        "       EOCM.BOOK_TYPE_CODE,\n" +
                        "       EII.BARCODE,\n" +
                        "       ESI.ITEM_NAME,\n" +
                        "       GETDATE() DATE_RRETIRED,\n" +
                        "       EII.ITEM_STATUS,\n" +
                        "       AATL.RETIREMENT_COST,\n" +
                        "       AATH.CREATION_DATE RRETIRED_DATE,\n" +
                        "       EFA.ASSET_ID,\n" +
                        "       EFA.TAG_NUMBER,\n" +
                        "       EFA.COST,\n" +
                        "       AATL.REJECT_TYPE \n" + 
                        "  FROM AMS_ASSETS_TRANS_HEADER AATH,\n" +
                        "       AMS_ASSETS_TRANS_LINE   AATL,\n" +
                        "       ETS_ITEM_INFO           EII,\n" +
                        "       ETS_SYSTEM_ITEM         ESI,\n" +
                        "       ETS_OU_CITY_MAP         EOCM,\n" +
                        "       ETS_FA_ASSETS_TD        EFA,\n" +
                        "       ETS_ITEM_MATCH_TD       EIM\n" +
                        " WHERE AATH.TRANS_ID = AATL.TRANS_ID\n" +
                        "   AND AATH.TRANS_TYPE = 'ASS-DIS'\n" +
                        "   AND AATH.TRANS_STATUS = 'COMPLETED'\n" +
                        "   AND EIM.SYSTEMID = EII.SYSTEMID\n" +
                        "   AND EIM.ASSET_ID = EFA.ASSET_ID\n" +
                        "   AND EII.BARCODE = AATL.BARCODE\n" +
                        "   AND EII.ITEM_CODE = ESI.ITEM_CODE\n" +
                        "   AND EII.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
                        "   AND EII.ITEM_STATUS = 'TO_DISCARD'\n" +
                        "   AND EII.SYSTEMID IN (" + systemIds + ")\n" +
                        " ORDER BY EOCM.ORGANIZATION_ID";


        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    /**
     * 写报废同步日志   ETS_MISFA_UPDATE_LOG    TRANS_STATUS:2
     * @param systemIds 资产IDS
     * @param batchId   同步批
     * @return SQLModel
     */
    public SQLModel getLogAssetsModel(String systemIds, String batchId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr =
                "INSERT INTO ETS_MISFA_UPDATE_LOG\n" +
                        "  (LOGID,\n" +
                        "   BATCH_ID,\n" +
                        "   BARCODE,\n" +
                        "   ASSET_ID,\n" +   //
                        "   ORGANIZATION_ID,\n" +  //
                        "   TRANS_STATUS,\n" +   //
                        "   UPDATE_TYPE)\n" +
                        "  (SELECT NEWID(),\n" +
                        "          '" + batchId + "',\n" +
                        "          EII.BARCODE,\n" +
                        "         CONVERT(int,EFA.ASSET_ID),\n" +
                        "         CONVERT(int,EII.ORGANIZATION_ID),\n" +
                        "          2,\n" +
                        "          'SRV_RETIRE'\n" +
                        "     FROM AMS_ASSETS_TRANS_HEADER AATH,\n" +
                        "          AMS_ASSETS_TRANS_LINE   AATL,\n" +
                        "          ETS_ITEM_INFO           EII,\n" +
                        "          ETS_FA_ASSETS           EFA,\n" +
                        "          ETS_ITEM_MATCH          EIM\n" +
                        "    WHERE AATH.TRANS_ID = AATL.TRANS_ID\n" +
                        "      AND AATH.TRANS_TYPE = 'ASS-DIS'\n" +
                        "      AND AATH.TRANS_STATUS = 'COMPLETED'\n" +
                        "      AND EIM.SYSTEMID = EII.SYSTEMID\n" +
                        "      AND EIM.ASSET_ID = EFA.ASSET_ID\n" +
                        "      AND EII.BARCODE = AATL.BARCODE\n" +
                        "      AND EII.ITEM_STATUS = 'TO_DISCARD'\n" +
                        "      AND EII.SYSTEMID IN (" + systemIds + "))";


        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    /**
     * 更新报废日志
     * @param batchId     报废批
     * @param barcode     条码
     * @param transStatus 同步状态
     * @param errorMsg    同步信息
     * @return SQLModel
     */
    public SQLModel getUpdateLogModel(String batchId, String barcode, int transStatus, String errorMsg) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE ETS_MISFA_UPDATE_LOG \n" +
                "   SET TRANS_STATUS = ?, TRANS_ERRORMSG = ?\n" +
                " WHERE BATCH_ID = ?\n" +
                "   AND BARCODE = ?";

        sqlArgs.add(transStatus);
        sqlArgs.add(errorMsg);
        sqlArgs.add(batchId);
        sqlArgs.add(barcode);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }
    
    /**
     * 更新日志 ETS_MISFA_UPDATE_LOG  成功: TRANS_STATUS：1
     * @param batchId
     * @return
     */
     public SQLModel getUpdateLogCompleteModel(String batchId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE ETS_MISFA_UPDATE_LOG \n" +
                "   SET TRANS_STATUS = ?\n" +
                " WHERE BATCH_ID = ?";

        sqlArgs.add(1);
        sqlArgs.add(batchId);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }
}
