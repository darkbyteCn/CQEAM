package com.sino.soa.util.model;


import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.util.StrUtil;
import com.sino.soa.util.dto.EtsMisfaUpdateBatchDTO;
import com.sino.soa.util.dto.EtsMisfaUpdateLogDTO;
import com.sino.soa.util.dto.SynLogDTO;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>Title: EtsAutoSynLogModel</p>
 * <p>Description:程序自动生成SQL构造器“EtsAutoSynLogModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>v
 * @author zhoujs
 * @version 1.0
 */


public class SynLogModel {


    public SQLModel getCreateLogModel(SynLogDTO logDTO) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO "
                + " ETS_AUTO_SYN_LOG("
                + " SYN_ID,"
                + " SYN_TYPE,"
                + " SYN_DATE,"
                + " SYN_MSG,"
                + " CREATION_DATE,"
                + " CREATED_BY"
                + ") VALUES ("
                + " NEWID(), ?,GETDATE(), ?, GETDATE(), ?)";

        sqlArgs.add(logDTO.getSynType());
        sqlArgs.add(logDTO.getSynMsg());
        sqlArgs.add(logDTO.getCreatedBy());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getCreateLastUpdateModel(String synType) {
        SQLModel sqlModel = new SQLModel();
        SynLogDTO synLogDto = new SynLogDTO();
        synLogDto.setSynType(synType);
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO " +
                "ETS_AUTO_SYN_DATE " +
                "(SYN_TYPE," +
                "LAST_DATE," +
                "CREATION_DATE," +
                "CREATED_BY," +
                "LAST_UPDATE_DATE," +
                "LAST_UPDATE_BY)" +
                "VALUES(?,GETDATE(),GETDATE(),?,GETDATE(),?)";
        sqlArgs.add(synLogDto.getSynType());
        sqlArgs.add(synLogDto.getCreatedBy());
        sqlArgs.add(synLogDto.getCreatedBy());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 更新同步类型最后更新日期
     * @param synType 同步类型
     * @return SQLModel
     */
    public SQLModel getUpdateLogDateModel(String synType) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE ETS_AUTO_SYN_DATE SET LAST_DATE = GETDATE() WHERE SYN_TYPE = ?";

        sqlArgs.add(synType);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    /**
     * 取同步类型最后更新日期
     * @param synType 同步类型
     * @return SQLModel
     */
    public SQLModel getLastUpdateDateModel(String synType) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
//        String sqlStr = "SELECT CONVERT(VARCHAR(10),EASD.LAST_DATE,111) LAST_DATE  FROM ETS_AUTO_SYN_DATE EASD WHERE EASD.SYN_TYPE = ?";
        String sqlStr = "SELECT EASD.LAST_DATE  FROM ETS_AUTO_SYN_DATE EASD WHERE EASD.SYN_TYPE = ?";

        sqlArgs.add(synType);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    /**
     * 取导入数据是MIS的用户编号、职责
     * @param orgId 组织ID  变更为公司ID
     * @return SQLModel
     */
    public SQLModel getMisfaRespModel(int orgId, String employeeNumber) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT TRANSACTION_TYPE,\n" +
                "       ORGANIZATION_ID,\n" +
                "       USER_ID,\n" +
                "       RESP_ID,\n" +
                "       RESP_APPL_ID,\n" +
                "       EMPLOYEE_NUMBER\n" +
                "       FROM ETS_MISFA_TRANSACTION_RESP\n" +
                "      WHERE ORGANIZATION_ID = ?\n" +     
                "        AND EMPLOYEE_NUMBER = ?";  
        sqlArgs.add(orgId);
        sqlArgs.add(employeeNumber);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    /**
     * 同步日志创建Model
     * @param batchDTO
     * @return
     */
    public SQLModel getCreateMisUpdateBatchModel(EtsMisfaUpdateBatchDTO batchDTO) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO ETS_MISFA_UPDATE_BATCH\n" +
                "  (BATCH_ID,\n" +
                "   REMARK,\n" +
                "   ORGANIZATION_ID,\n" +
                "   START_DATE,\n" +
                "   CREATION_DATE,\n" +
                "   CREATED_BY,\n" +
                "   ERRMSG,\n"+
                "   TRANS_STATUS,\n"+
                "   TRANS_TYPE)\n" +
                " VALUES (?,?,?,GETDATE(),GETDATE(),?,?,?,?)";

        sqlArgs.add(batchDTO.getBatchId());
        sqlArgs.add(batchDTO.getRemark());
        sqlArgs.add(batchDTO.getOrganizationId());
        sqlArgs.add(batchDTO.getCreatedBy());
        sqlArgs.add(batchDTO.getErrmsg());
        sqlArgs.add(batchDTO.getTransStatus());
        sqlArgs.add(batchDTO.getTransType());


        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    /**
     * 更新同步日志批Model
     * ETS_MISFA_UPDATE_BATCH
     * @param batchDTO batch
     * @return SQLModel
     */
    public SQLModel getUpdateMisUpdateBatchModel(EtsMisfaUpdateBatchDTO batchDTO) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE ETS_MISFA_UPDATE_BATCH\n" +
                "   SET REMARK = ?,TRANS_STATUS=?, END_DATE = GETDATE(), ERRMSG = ?\n" +
                " WHERE BATCH_ID = ?";

        sqlArgs.add(batchDTO.getRemark());
        sqlArgs.add(batchDTO.getTransStatus());
        sqlArgs.add(batchDTO.getErrmsg());
        sqlArgs.add(batchDTO.getBatchId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }
    
    public SQLModel getUpdateMisUpdateLogModel(String batchId, String msg,String str){
    	SQLModel sqlModel = new SQLModel();
    	List sqlArgs = new ArrayList();
    	String sqlStr ="UPDATE ETS_MISFA_UPDATE_LOG  SET TRANS_ERRORMSG =? WHERE BARCODE = ? AND BATCH_ID = ?";
    	sqlArgs.add(msg);
    	sqlArgs.add(str);
    	sqlArgs.add(batchId);
    	sqlModel.setArgs(sqlArgs);
    	sqlModel.setSqlStr(sqlStr);
    	return sqlModel;
    }

    /**
     * 功能：获取同步资产成功时更新日志记录的SQL模型列表
     * 唐明胜增加
      * @param batchId 同步批ID
     * @param tagNumbers 同步成功的资产标签列表
     * @return 获取同步资产成功时更新日志记录的SQL模型列表
     */
    public static List<SQLModel> getMisFASuccessLogModelList(String batchId, List<String> tagNumbers){
        List<SQLModel> sqlModelList = new ArrayList<SQLModel>();
        for(String tagNumber:tagNumbers){
            SQLModel sqlModel = new SQLModel();
            List<String> sqlArgs = new ArrayList<String>();
            String sqlStr ="UPDATE ETS_MISFA_UPDATE_LOG\n" +
                    "   SET TRANS_STATUS = 1\n" +
                    " WHERE BATCH_ID = ?\n" +
                    "   AND BARCODE = ?";
            sqlArgs.add(batchId);
            sqlArgs.add(tagNumber);
            sqlModel.setArgs(sqlArgs);
            sqlModel.setSqlStr(sqlStr);

            sqlModelList.add(sqlModel);
        }
    	return sqlModelList;
    }

    /**
     * 同步导入日志详细表Model
     * @param etsMisfaUpdateLog EtsMisfaUpdateLogDTO
     * @return SQLModel
     * @throws CalendarException 日期异常
     */
    public SQLModel getUpdateMisUpdateLogModel(EtsMisfaUpdateLogDTO etsMisfaUpdateLog) throws CalendarException {
        SQLModel sqlModel = new SQLModel();

        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO "
                + " ETS_MISFA_UPDATE_LOG("
                + " LOGID,"
                + " BATCH_ID,"
                + " BARCODE,"
                + " ASSET_ID,"
                + " IF_UPDATE_TAGNUMBER,"
                + " TAG_NUMBER_FROM,"
                + " TAG_NUMBER_TO,"
                + " LOCATION_FROM,"
                + " LOCATION_TO,"
                + " NAME_FROM,"
                + " NAME_TO,"
                + " MODEL_FROM,"
                + " MODEL_TO,"
                + " OWNER_FROM,"
                + " OWNER_TO,"
                + " UPDATE_TYPE,"
                + " ORGANIZATION_ID,"
                + " TRANS_STATUS,"
                + " TRANS_ERRORMSG,"
                + " TRANS_DATE,"
                + " REMARK,"
                + " COST_CENTER_FROM,"
                + " COST_CENTER_TO,"
                + " CODE_COMBINATION_ID,"
                + " CREATION_DATE,"
                + " CREATED_BY,"
                + " LAST_UPDATE_DATE,"
                + " LAST_UPDATE_BY,"
                + " ORG_TO,"
                + " CODE_COMBINATION_ID_TO,"
                + " ASSET_CATEGORY_TO,"
                + " TRANSACTION_NO,"
                + " MANUFACTURER_FROM,"
                + " MANUFACTURER_TO"
                + ") VALUES ("
                + "  NEWID(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), ?, ?, ?, ?, ?, ?, ?,?,?)";

        sqlArgs.add(etsMisfaUpdateLog.getBatchId());
        sqlArgs.add(etsMisfaUpdateLog.getBarcode());
        sqlArgs.add(etsMisfaUpdateLog.getAssetId());
        sqlArgs.add(etsMisfaUpdateLog.getIfUpdateTagnumber());
        sqlArgs.add(etsMisfaUpdateLog.getTagNumberFrom());
        sqlArgs.add(etsMisfaUpdateLog.getTagNumberTo());
        sqlArgs.add(etsMisfaUpdateLog.getLocationFrom());
        sqlArgs.add(etsMisfaUpdateLog.getLocationTo());
        sqlArgs.add(etsMisfaUpdateLog.getNameFrom());
        sqlArgs.add(etsMisfaUpdateLog.getNameTo());
        sqlArgs.add(etsMisfaUpdateLog.getModelFrom());
        sqlArgs.add(etsMisfaUpdateLog.getModelTo());
        sqlArgs.add(etsMisfaUpdateLog.getOwnerFrom());
        sqlArgs.add(etsMisfaUpdateLog.getOwnerTo());
        sqlArgs.add(etsMisfaUpdateLog.getUpdateType());
        sqlArgs.add(etsMisfaUpdateLog.getOrganizationId());
        sqlArgs.add(etsMisfaUpdateLog.getTransStatus());
        sqlArgs.add(etsMisfaUpdateLog.getTransErrormsg());
        sqlArgs.add(etsMisfaUpdateLog.getTransDate());
        sqlArgs.add(etsMisfaUpdateLog.getRemark());
        sqlArgs.add(etsMisfaUpdateLog.getCostCenterFrom());
        sqlArgs.add(etsMisfaUpdateLog.getCostCenterTo());
        sqlArgs.add(etsMisfaUpdateLog.getCodeCombinationId());
//        sqlArgs.add(etsMisfaUpdateLog.getCreationDate());
        sqlArgs.add(etsMisfaUpdateLog.getCreatedBy());
        sqlArgs.add(etsMisfaUpdateLog.getLastUpdateDate());
        sqlArgs.add(etsMisfaUpdateLog.getLastUpdateBy());
        sqlArgs.add(etsMisfaUpdateLog.getOrgTo());
        sqlArgs.add(etsMisfaUpdateLog.getCodeCombinationIdTo());
        sqlArgs.add(etsMisfaUpdateLog.getAssetCategoryTo());
        sqlArgs.add(etsMisfaUpdateLog.getTransactionNo());
        sqlArgs.add(etsMisfaUpdateLog.getManufacturerFrom());
        sqlArgs.add(etsMisfaUpdateLog.getManufacturerTo());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


}