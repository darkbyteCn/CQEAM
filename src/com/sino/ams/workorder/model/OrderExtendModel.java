package com.sino.ams.workorder.model;
/**
 * User: zhoujs
 * Date: 2007-9-21
 * Time: 11:40:07
 * Function:
 */

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.ArrUtil;
import com.sino.base.util.StrUtil;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.constant.AmsOrderConstant;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.system.basepoint.dto.EtsObjectDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dto.EtsWoSchemeDTO;
import com.sino.ams.workorder.dto.EtsWorkorderDTO;



public class OrderExtendModel {

    /**
     * 查询工单批下的工单清单
     * @param workorderBatchNo
     * @return SQLModel
     */
    public SQLModel getWorkorderQueryModel(String workorderBatchNo, boolean isTmp) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String orderTable = "ETS_WORKORDER";
        String schemeTable = "ETS_WO_SCHEME";
        if (isTmp) {
            orderTable = "ETS_WORKORDER_TMP";
            schemeTable = "ETS_WO_SCHEME_TMP";
        }
        String sqlStr = "SELECT " +
                "       EWT.SYSTEMID," +
                "       EWT.WORKORDER_NO," +
                "       EO.WORKORDER_OBJECT_NO," +
                "       EO.WORKORDER_OBJECT_CODE," +
                "       EO.WORKORDER_OBJECT_NAME," +
                "       EWT.ATTRIBUTE5 BSC," +
                "       dbo.APP_GET_USER_NAME(EWT.CHECKOVER_BY) ARC_USER," +
                "       EWT.ATTRIBUTE1 ," +
                "       CONVERT(DATETIME,EWT.START_DATE) START_DATE," +
                "       EWT.IMPLEMENT_DAYS," +
                "       SGV.GROUP_NAME || '/' || SUV.USERNAME IMPLEMENT," +
                "       dbo.AWP_HASCONFIG(EWT.WORKORDER_NO,'" + isTmp + "') HASCONFIG," +
                "       EWT.WORKORDER_BATCH" +
                "  FROM " + orderTable + " EWT," +
                "       ETS_OBJECT EO," +
                "       SF_USER_V SUV," +
                "       SF_GROUP_V SGV" +
//                "       (SELECT DISTINCT (WORKORDER_NO) FROM "+schemeTable+") WS" +
                " WHERE EWT.WORKORDER_OBJECT_NO *= EO.WORKORDER_OBJECT_NO" +
//                "   AND EWT.WORKORDER_NO *= WS.WORKORDER_NO" +
                "   AND EWT.GROUP_ID *= SGV.GROUP_ID" +

                "   AND EWT.IMPLEMENT_BY *= SUV.USER_ID" +
                "   AND  " + SyBaseSQLUtil.isNotNull("EWT.WORKORDER_NO") + " " +
                "   AND EWT.WORKORDER_BATCH = ?  ";
//        sqlArgs.add(workorderBatchNo+"%");
        sqlArgs.add(workorderBatchNo);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 根据工单批号删除工单数据
     * @param workorderBatchNo
     * @return SQLModel
     */
    public SQLModel getDeleteWorkorderDataModel(String workorderBatchNo, boolean isTemp) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String tableName = "ETS_WORKORDER";
        if (isTemp) {
            tableName = "ETS_WORKORDER_TMP";
        }
        String sqlStr = "DELETE " + tableName + "  WHERE WORKORDER_BATCH = ?";
        sqlArgs.add(workorderBatchNo);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


    /**
     * 删除计划配置信息(指定工单批号)
     * @param workorderBatchNo
     * @return SQLModel
     */
    public SQLModel getDeleteSchemeDataModel(String workorderBatchNo, boolean isTemp) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();

        String schemeTable = "ETS_WO_SCHEME";
        if (isTemp) {
            schemeTable = "ETS_WO_SCHEME_TMP";
        }
        String sqlStr = "DELETE " + schemeTable +
                " WHERE WORKORDER_NO LIKE ?";
        sqlArgs.add(workorderBatchNo + "%");
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    /**
     * 生成临时工单
     * @param etsWorkorderDTO
     * @return SQLModel
     */
    public SQLModel getInsertWorkorderDataModel(EtsWorkorderDTO etsWorkorderDTO) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            String sqlStr = "INSERT INTO "
                    + " ETS_WORKORDER_TMP("
                    + " SYSTEMID,"
                    + " WORKORDER_BATCH,"
                    + " WORKORDER_NO,"
                    + " WORKORDER_TYPE,"
                    + " WORKORDER_OBJECT_NO,"
                    + " START_DATE,"
                    + " IMPLEMENT_DAYS,"
                    + " GROUP_ID,"
                    + " IMPLEMENT_BY,"
                    + " PRJ_ID,"
                    + " DISTRIBUTE_DATE,"
                    + " DISTRIBUTE_BY,"
                    + " DOWNLOAD_DATE,"
                    + " DOWNLOAD_BY,"
                    + " SCANOVER_DATE,"
                    + " SCANOVER_BY,"
                    + " UPLOAD_DATE,"
                    + " UPLOAD_BY,"
                    + " CHECKOVER_DATE,"
                    + " CHECKOVER_BY,"
                    + " RESPONSIBILITY_USER,"
                    + " DIFFERENCE_REASON,"
                    + " ORGANIZATION_ID,"
                    + " WORKORDER_FLAG,"
                    + " REMARK,"
                    + " ACTID,"
                    + " CASEID,"
                    + " ARCHFLAG,"
                    + " ATTRIBUTE1,"
                    + " ATTRIBUTE2,"
                    + " ATTRIBUTE3,"
                    + " ATTRIBUTE4,"
                    + " ATTRIBUTE5,"
                    + " ATTRIBUTE6,"
                    + " DISTRIBUTE_GROUP,"
                    + " ATTRIBUTE7,"
                    + " CREATION_DATE,"
                    + " CREATED_BY,"
                    + " COST_CENTER_CODE"
                    + ") VALUES ("
                    + "  NEWID() , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), ?,?)";

            sqlArgs.add(etsWorkorderDTO.getWorkorderBatch());
            sqlArgs.add(etsWorkorderDTO.getWorkorderNo());
            sqlArgs.add(etsWorkorderDTO.getWorkorderType());
            sqlArgs.add(etsWorkorderDTO.getWorkorderObjectNo());
            sqlArgs.add(etsWorkorderDTO.getStartDate());
            sqlArgs.add(etsWorkorderDTO.getImplementDays());
            sqlArgs.add(etsWorkorderDTO.getGroupId());
            sqlArgs.add(etsWorkorderDTO.getImplementBy());
            sqlArgs.add(etsWorkorderDTO.getPrjId());
//            sqlArgs.add(etsWorkorderDTO.getDistributeDate());
            sqlArgs.add(null);
            sqlArgs.add(etsWorkorderDTO.getDistributeBy());
//            sqlArgs.add(etsWorkorderDTO.getDownloadDate());
            sqlArgs.add(null);
            sqlArgs.add(etsWorkorderDTO.getDownloadBy());
//            sqlArgs.add(etsWorkorderDTO.getScanoverDate());
            sqlArgs.add(null);
            sqlArgs.add(etsWorkorderDTO.getScanoverBy());
//            sqlArgs.add(etsWorkorderDTO.getUploadDate());
            sqlArgs.add(null);
            sqlArgs.add(etsWorkorderDTO.getUploadBy());
//            sqlArgs.add(etsWorkorderDTO.getCheckoverDate());
            sqlArgs.add(null);
            sqlArgs.add(etsWorkorderDTO.getCheckoverBy());
            sqlArgs.add(etsWorkorderDTO.getResponsibilityUser());
            sqlArgs.add(etsWorkorderDTO.getDifferenceReason());
            sqlArgs.add(etsWorkorderDTO.getOrganizationId());
            sqlArgs.add(etsWorkorderDTO.getWorkorderFlag());
            sqlArgs.add(etsWorkorderDTO.getRemark());
            sqlArgs.add(etsWorkorderDTO.getActid());
            sqlArgs.add(etsWorkorderDTO.getCaseid());
            sqlArgs.add(etsWorkorderDTO.getArchflag());
            sqlArgs.add(etsWorkorderDTO.getAttribute1());
            sqlArgs.add(etsWorkorderDTO.getAttribute2());
            sqlArgs.add(etsWorkorderDTO.getAttribute3());
            sqlArgs.add(etsWorkorderDTO.getAttribute4());
            sqlArgs.add(etsWorkorderDTO.getAttribute5());
            sqlArgs.add(etsWorkorderDTO.getAttribute6());
            sqlArgs.add(etsWorkorderDTO.getDistributeGroup());
            sqlArgs.add(etsWorkorderDTO.getAttribute7());
            sqlArgs.add(etsWorkorderDTO.getCreatedBy());
            sqlArgs.add(etsWorkorderDTO.getCostCenterCode());

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
        }

        return sqlModel;
    }

    /**
     * 更新工单临时表ATTRIBUTE7值
     * @param workorderBatchNo
     * @return SQLModel
     */
    public SQLModel getUpdateWorkorderTmpDataModel(String workorderBatchNo) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();

        String sqlStr = "UPDATE ETS_WORKORDER_TMP" +
                "   SET ATTRIBUTE7 =  '"+ AmsOrderConstant.scanAllItemCategory+"'" +
                " WHERE  " + SyBaseSQLUtil.isNotNull("WORKORDER_NO") + " " +
                "   AND WORKORDER_BATCH = ?";
        sqlArgs.add(workorderBatchNo);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    /**
     * 更新工单临时表项目ID、下单部门ID、工单批
     * @param projectId
     * @param groupId
     * @param workorderBatchNo
     * @return SQLModel
     */
    public SQLModel getUpdateWorkorderTmpDataModel(String workorderBatchNo, String projectId, int groupId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();

        String sqlStr = "UPDATE ETS_WORKORDER_TMP" +
                "   SET PRJ_ID           = ?," +
                "       DISTRIBUTE_GROUP = ?," +
                "       WORKORDER_TYPE   = (SELECT DISTINCT EWB.WORKORDER_TYPE" +
                "                                 FROM ETS_WORKORDER_BATCH EWB,ETS_WORKORDER_TMP EWT" +
                "                                WHERE EWB.WORKORDER_BATCH = ?)" +
                " WHERE WORKORDER_BATCH = ?";
        sqlArgs.add(projectId);
        sqlArgs.add(groupId);
        sqlArgs.add(workorderBatchNo);
        sqlArgs.add(workorderBatchNo);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    /**
     * copy 临时表下该工单批下的非空工单
     * @param workorderBatchNo
     * @return SQLModel
     */
    public SQLModel getCopyWorkorderDataModel(String workorderBatchNo) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();

        String sqlStr = "INSERT INTO ETS_WORKORDER " +
                "  SELECT  NEWID() ," +
                "       WORKORDER_BATCH," +
                "       WORKORDER_NO," +
                "       WORKORDER_TYPE," +
                "       WORKORDER_OBJECT_NO," +
                "       START_DATE," +
                "       IMPLEMENT_DAYS," +
                "       GROUP_ID," +
                "       IMPLEMENT_BY," +
                "       PRJ_ID," +
                "       DISTRIBUTE_DATE," +
                "       DISTRIBUTE_BY," +
                "       DOWNLOAD_DATE," +
                "       DOWNLOAD_BY," +
                "       SCANOVER_DATE," +
                "       SCANOVER_BY," +
                "       UPLOAD_DATE," +
                "       UPLOAD_BY," +
                "       CHECKOVER_DATE," +
                "       CHECKOVER_BY," +
                "       RESPONSIBILITY_USER," +
                "       DIFFERENCE_REASON," +
                "       ORGANIZATION_ID," +
                "       WORKORDER_FLAG," +
                "       REMARK," +
                "       ACTID," +
                "       CASEID," +
                "       ARCHFLAG," +
                "       ATTRIBUTE1," +
                "       ATTRIBUTE2," +
                "       ATTRIBUTE3," +
                "       ATTRIBUTE4," +
                "       ATTRIBUTE5," +
                "       ATTRIBUTE6," +
                "       DISTRIBUTE_GROUP," +
                "       ATTRIBUTE7," +
                "       CREATION_DATE," +
                "       CREATED_BY," +
                "       LAST_UPDATE_DATE," +
                "       LAST_UPDATE_BY," +
                "       COST_CENTER_CODE" +
                "  FROM ETS_WORKORDER_TMP" +
                "  	WHERE WORKORDER_NO IS NOT NULL AND WORKORDER_NO != ''" +
                "   AND WORKORDER_BATCH = ?";

        sqlArgs.add(workorderBatchNo);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }


    /**
     * Function:      查询未归档的交接工单中已经存在指定项目下的地点
     * @param workorderObjectNos
     * @param workorderDTO
     * @return SQLModel
     */
    public SQLModel getExistHandoverWorkorderModel(String[] workorderObjectNos, EtsWorkorderDTO workorderDTO) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT EW.WORKORDER_OBJECT_NO\n" +
                        "  FROM ETS_WORKORDER EW\n" +
                        " WHERE EW.WORKORDER_TYPE = ?\n" +
                        "   AND EW.WORKORDER_FLAG <> 14\n" +
                        "   AND EWB.PRJ_ID = ?\n" +
                        "   AND EW.WORKORDER_OBJECT_NO IN (" + workorderObjectNos.toString() +")";

        sqlArgs.add(DictConstant.ORDER_TYPE_HDV);
        sqlArgs.add(workorderDTO.getPrjId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }


    /**
     * copy 工单配置表
     * @param workorderBatchNo
     * @return SQLModel
     */
    public SQLModel getCopySchemDataModel(String workorderBatchNo) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();

        String sqlStr = "INSERT INTO ETS_WO_SCHEME" +
                "  SELECT EWST.WORKORDER_NO," +
                "          EWST.ITEM_CODE," +
                "          EWST.ITEM_QTY," +
                "          EWST.ATTRIBUTE1," +
                "          EWST.ATTRIBUTE2," +
                "          EWST.ATTRIBUTE3," +
                "          EWST.ATTRIBUTE4," +
                "          EWST.ATTRIBUTE5," +
                "          EWST.ATTRIBUTE6" +
                "     FROM ETS_WORKORDER_TMP EWT, ETS_WO_SCHEME_TMP EWST" +
                "    WHERE EWT.SYSTEMID = EWST.SYSTEMID" +
                "      AND EWT.WORKORDER_BATCH = ?";

        sqlArgs.add(workorderBatchNo);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    public SQLModel getCopySchemToTempModel(String workorderBatchNo) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();

        String sqlStr = "INSERT INTO ETS_WO_SCHEME_TMP" +
                "  SELECT EWT.SYSTEMID," +
                "          EWS.WORKORDER_NO," +
                "          EWS.ITEM_CODE," +
                "          EWS.ITEM_QTY," +
                "          EWS.ATTRIBUTE1," +
                "          EWS.ATTRIBUTE2," +
                "          EWS.ATTRIBUTE3," +
                "          EWS.ATTRIBUTE4," +
                "          EWS.ATTRIBUTE5," +
                "          EWS.ATTRIBUTE6" +
                "     FROM ETS_WORKORDER_TMP EWT, ETS_WO_SCHEME EWS" +
                "    WHERE EWT.WORKORDER_NO = EWS.WORKORDER_NO" +
                "      AND EWT.WORKORDER_BATCH = ?";

        sqlArgs.add(workorderBatchNo);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    /**
     * 查询工单能选择的地点信息
     * @param userAccount    --   用户对象
     * @param etsObject      --   地点对象
     * @param workorderType  --   工单类型
     * @param workorderBatch --   工单批号
     * @return SQLModel
     */
    public SQLModel getQueryLocDataModel(SfUserDTO userAccount,
                                         EtsObjectDTO etsObject,
                                         String workorderType,
                                         String workorderBatch
                                         ) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "";
        if(!workorderType.equals("18")){
            sqlStr = "SELECT dbo.AWP_CAN_DISTRIBUTE_BY_HANDOVER(EO.WORKORDER_OBJECT_NO, ?, ?) CAN_DISTRIBUTE,\n" +
                    "       EO.WORKORDER_OBJECT_NO,\n" +
                    "       EO.WORKORDER_OBJECT_CODE,\n" +
                    "       EO.WORKORDER_OBJECT_NAME,\n" +
                    "       EO.WORKORDER_OBJECT_LOCATION,\n" +
                    "       ? PROJECT_ID,\n" +
                    "       dbo.APP_GET_PROJECT_NAME(?) PROJECT_NAME\n" +
                    "FROM   ETS_OBJECT EO\n" +
                    "WHERE  (EO.DISABLE_DATE IS NULL OR EO.DISABLE_DATE = '' OR EO.DISABLE_DATE > GETDATE())\n" +
                    "       AND (? = '' OR EO.WORKORDER_OBJECT_CODE LIKE ?)\n" +
                    "       AND (? = '' OR EO.WORKORDER_OBJECT_NAME LIKE ?)\n" +
                    "       AND (? = '' OR EO.COST_CODE = ?)" +
                    "       AND (? = '' OR EO.CITY = ?)\n" +
                    "       AND (? = '' OR EO.COUNTY = ?)\n" +
                    "       AND EO.ORGANIZATION_ID = ?\n" +
                    "       AND (EO.OBJECT_CATEGORY >= '75' OR EO.OBJECT_CATEGORY <= '73')\n" +
                    "       AND EXISTS (SELECT NULL\n" +
                    "        FROM   AMS_OBJECT_ADDRESS  AOA,\n" +
                    "               ETS_ITEM_INFO       EII,\n" +
                    "               ETS_PA_PROJECTS_ALL EPPA\n" +
                    "        WHERE  EO.WORKORDER_OBJECT_NO = AOA.OBJECT_NO\n" +
                    "               AND AOA.ADDRESS_ID = EII.ADDRESS_ID\n" +
                    "               AND EII.PROJECTID = EPPA.PROJECT_ID\n" +
                    "               AND EPPA.PROJECT_ID = ?\n" +
                    "               AND (EII.ITEM_STATUS =? OR EII.ITEM_STATUS =? ))";

            sqlArgs.add(etsObject.getProjectId());
            sqlArgs.add(workorderType);
            sqlArgs.add(etsObject.getProjectId());
            sqlArgs.add(etsObject.getProjectId());
            sqlArgs.add(etsObject.getWorkorderObjectCode());
            sqlArgs.add(etsObject.getWorkorderObjectCode());
            sqlArgs.add(etsObject.getWorkorderObjectName());
            sqlArgs.add(etsObject.getWorkorderObjectName());
            sqlArgs.add(etsObject.getCostCenterCode());
            sqlArgs.add(etsObject.getCostCenterCode());
            sqlArgs.add(etsObject.getCountyCodeShi());
            sqlArgs.add(etsObject.getCountyCodeShi());
            sqlArgs.add(etsObject.getCountyCodeXian());
            sqlArgs.add(etsObject.getCountyCodeXian());
            sqlArgs.add(userAccount.getOrganizationId());
            sqlArgs.add(etsObject.getProjectId());
            sqlArgs.add(DictConstant.ITEM_STATUS_PRE_ASSETS);
            sqlArgs.add(DictConstant.ITEM_STATUS_TO_ASSETS);
        } else {
            sqlStr = "SELECT" +
                    "       dbo.AWP_CAN_DISTRIBUTE(EO.WORKORDER_OBJECT_NO,'" + workorderType + "') CAN_DISTRIBUTE," +
                    "       EO.WORKORDER_OBJECT_NO," +
                    "       EO.WORKORDER_OBJECT_CODE," +
                    "       EO.WORKORDER_OBJECT_NAME," +
                    "       EO.WORKORDER_OBJECT_LOCATION," +
                    "       EO.PROJECT_ID," +
                    "       dbo.APP_GET_PROJECT_NAME(EO.PROJECT_ID) PROJECT_NAME" +
                    "  FROM ETS_OBJECT EO" +
                    " WHERE " +
                    "  NOT EXISTS\n" +
                    " (SELECT 1\n" +
                    "         FROM ETS_WORKORDER_TMP EWT\n" +
                    "        WHERE EWT.WORKORDER_OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                    "          AND EWT.WORKORDER_BATCH = ?)\n" +
                    " AND (EO.OBJECT_CATEGORY >= '75' OR EO.OBJECT_CATEGORY <= '73')" +
                    " AND EO.ORGANIZATION_ID=?" +
                    " AND (EO.DISABLE_DATE IS NULL OR EO.DISABLE_DATE = '' OR EO.DISABLE_DATE > GETDATE()) " +
                    " AND ( ? = '' OR EO.WORKORDER_OBJECT_CODE = ?)" +
                    " AND ( ? = '' OR EO.WORKORDER_OBJECT_NAME LIKE ?)" +
                    " AND ( ? = '' OR EO.COST_CODE = ?)" +
                    " AND ( ? = '' OR EO.CITY = ?)" +
                    " AND ( ? = '' OR EO.COUNTY = ?)" ;
            sqlArgs.add(workorderBatch);
            sqlArgs.add(userAccount.getOrganizationId());
            sqlArgs.add(etsObject.getWorkorderObjectCode());
            sqlArgs.add(etsObject.getWorkorderObjectCode());
            sqlArgs.add(etsObject.getWorkorderObjectName());
            sqlArgs.add(etsObject.getWorkorderObjectName());
            sqlArgs.add(etsObject.getCostCenterCode());
            sqlArgs.add(etsObject.getCostCenterCode());
            sqlArgs.add(etsObject.getCountyCodeShi());
            sqlArgs.add(etsObject.getCountyCodeShi());
            sqlArgs.add(etsObject.getCountyCodeXian());
            sqlArgs.add(etsObject.getCountyCodeXian());
        }
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    /**
     * 根据Excel查询
     * @param userAccount
     * @param etsObject
     * @param workorderType
     * @param workorderBatch
     * @param excel
     * @return
     */
    public SQLModel getQueryLocExcelModel(SfUserDTO userAccount, EtsObjectDTO etsObject, String workorderType, String workorderBatch, String excel) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "";
        if(workorderType.equals("18")){
            sqlStr = "SELECT " +
                    "       dbo.AWP_CAN_DISTRIBUTE_BY_HANDOVER(EO.WORKORDER_OBJECT_NO, ?, ?) CAN_DISTRIBUTE," +
                    "       EO.WORKORDER_OBJECT_NO,\n" +
                    "       EO.WORKORDER_OBJECT_CODE,\n" +
                    "       EO.WORKORDER_OBJECT_NAME,\n" +
                    "       EO.WORKORDER_OBJECT_LOCATION,\n" +
                    "       EO.PROJECT_ID,\n" +
                    "       dbo.APP_GET_PROJECT_NAME(EO.PROJECT_ID) PROJECT_NAME\n" +
                    "  FROM ETS_OBJECT EO\n" +
                    " WHERE EO.ORGANIZATION_ID = ?\n" +
                    "   AND (EO.DISABLE_DATE IS NULL OR EO.DISABLE_DATE = '') \n" +
                    " AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.WORKORDER_OBJECT_CODE LIKE ?)" +
                    " AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.WORKORDER_OBJECT_NAME LIKE ?)" +
                    " AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.WORKORDER_OBJECT_LOCATION LIKE ?)";
            sqlArgs.add(etsObject.getProjectId());
            sqlArgs.add(workorderType);
            sqlArgs.add(userAccount.getOrganizationId());
            sqlArgs.add(etsObject.getWorkorderObjectCode());
            sqlArgs.add(etsObject.getWorkorderObjectCode());
            sqlArgs.add(etsObject.getWorkorderObjectName());
            sqlArgs.add(etsObject.getWorkorderObjectName());
            sqlArgs.add(etsObject.getWorkorderObjectLocation());
            sqlArgs.add(etsObject.getWorkorderObjectLocation());
        } else {
            sqlStr = "SELECT" +
                "       dbo.AWP_CAN_DISTRIBUTE(EO.WORKORDER_OBJECT_NO,'" + workorderType + "') CAN_DISTRIBUTE," +
                "       EO.WORKORDER_OBJECT_NO," +
                "       EO.WORKORDER_OBJECT_CODE," +
                "       EO.WORKORDER_OBJECT_NAME," +
                "       EO.WORKORDER_OBJECT_LOCATION," +
                "       EO.PROJECT_ID," +
                "       dbo.APP_GET_PROJECT_NAME(EO.PROJECT_ID) PROJECT_NAME" +
                "  FROM ETS_OBJECT EO" +
                " WHERE " +
                "  NOT EXISTS\n" +
                " (SELECT 1\n" +
                "         FROM ETS_WORKORDER_TMP EWT\n" +
                "        WHERE EWT.WORKORDER_OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                "          AND EWT.WORKORDER_BATCH = ?)\n" +
                " AND EO.ORGANIZATION_ID=?" +
                " AND (EO.DISABLE_DATE IS NULL OR EO.DISABLE_DATE = '') " +
//                " AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.PROJECT_ID=?)" +
                " AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.WORKORDER_OBJECT_CODE LIKE ?)" +
                " AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.WORKORDER_OBJECT_NAME LIKE ?)" +
                " AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.WORKORDER_OBJECT_LOCATION LIKE ?)" +
                "";           
            sqlArgs.add(workorderBatch);
            sqlArgs.add(userAccount.getOrganizationId());
//            sqlArgs.add(etsObject.getProjectId());
//            sqlArgs.add(etsObject.getProjectId());
            sqlArgs.add(etsObject.getWorkorderObjectCode());
            sqlArgs.add(etsObject.getWorkorderObjectCode());
            sqlArgs.add(etsObject.getWorkorderObjectName());
            sqlArgs.add(etsObject.getWorkorderObjectName());
            sqlArgs.add(etsObject.getWorkorderObjectLocation());
            sqlArgs.add(etsObject.getWorkorderObjectLocation());
        }
        if (!excel.equals("")) {
            sqlStr += " AND EO.WORKORDER_OBJECT_CODE IN (" + excel + ")";
        }
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    /**
     * 根据Excel查询
     * @param userAccount
     * @param etsObject
     * @param workorderType
     * @param workorderBatch
     * @return
     */
    public SQLModel getQueryLocExcelModel(SfUserDTO userAccount, EtsObjectDTO etsObject, String workorderType, String workorderBatch, List locList) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();

        String sqlStr = "SELECT" +
                "       dbo.AWP_CAN_DISTRIBUTE(CONVERT(VARCHAR,EO.WORKORDER_OBJECT_NO),'" + workorderType + "') CAN_DISTRIBUTE," +
                "       EO.WORKORDER_OBJECT_NO," +
                "       EO.WORKORDER_OBJECT_CODE," +
                "       EO.WORKORDER_OBJECT_NAME," +
                "       EO.WORKORDER_OBJECT_LOCATION," +
                "       EO.PROJECT_ID," +
                "       dbo.APP_GET_PROJECT_NAME(CONVERT(VARCHAR,EO.PROJECT_ID)) PROJECT_NAME" +
                "  FROM ETS_OBJECT EO" +
                " WHERE " +
                "  NOT EXISTS\n" +
                " (SELECT 1\n" +
                "         FROM ETS_WORKORDER_TMP EWT\n" +
                "        WHERE EWT.WORKORDER_OBJECT_NO = CONVERT(INT,EO.WORKORDER_OBJECT_NO)\n" +
                "          AND EWT.WORKORDER_BATCH = ?)\n" +
                " AND EO.ORGANIZATION_ID=?" +
                " AND (EO.DISABLE_DATE IS NULL OR EO.DISABLE_DATE = '')" +
//                " AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.PROJECT_ID=?)" +
                " AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.WORKORDER_OBJECT_CODE LIKE ?)" +
                " AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.WORKORDER_OBJECT_NAME LIKE ?)" +
                " AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.WORKORDER_OBJECT_LOCATION LIKE ?)" +
                "";
        if (locList != null && locList.size() > 0) {
            sqlStr += " AND  EO.WORKORDER_OBJECT_CODE IN (" + ArrUtil.lstToSqlStr(locList) + ")";
        }
        sqlArgs.add(workorderBatch);
        sqlArgs.add(userAccount.getOrganizationId());
//        sqlArgs.add(etsObject.getProjectId());
//        sqlArgs.add(etsObject.getProjectId());
        sqlArgs.add(etsObject.getWorkorderObjectCode());
        sqlArgs.add(etsObject.getWorkorderObjectCode());
        sqlArgs.add(etsObject.getWorkorderObjectName());
        sqlArgs.add(etsObject.getWorkorderObjectName());
        sqlArgs.add(etsObject.getWorkorderObjectLocation());
        sqlArgs.add(etsObject.getWorkorderObjectLocation());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    /**
     * 查询工单能选择的地点信息(自定义)
     * @param userAccount    --   用户对象
     * @param etsObject      --   地点对象
     * @param workorderType  --   工单类型
     * @param workorderBatch --   工单批号
     * @return SQLModel
     */
    public SQLModel getMineLocDataModel(SfUserDTO userAccount, EtsObjectDTO etsObject, String workorderType, String workorderBatch) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "";
        if(workorderType.equals("18")){
            sqlStr = "SELECT" +
                    "       dbo.AWP_CAN_DISTRIBUTE_BY_HANDOVER(EO.WORKORDER_OBJECT_NO, '" + etsObject.getProjectId() +"' ,'" + workorderType + "') CAN_DISTRIBUTE," +
                    "       EO.WORKORDER_OBJECT_NO,\n" +
                    "       EO.WORKORDER_OBJECT_CODE,\n" +
                    "       EO.WORKORDER_OBJECT_NAME,\n" +
                    "       EO.WORKORDER_OBJECT_LOCATION,\n" +
                    "       EO.PROJECT_ID,\n" +
                    "       dbo.APP_GET_PROJECT_NAME(EO.PROJECT_ID) PROJECT_NAME\n" +
                    "  FROM ETS_OBJECT EO\n" +
                    " WHERE EO.ORGANIZATION_ID = ?\n" +
//                    " AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.PROJECT_ID=?)\n" +
                    " AND (EO.DISABLE_DATE IS NULL OR EO.DISABLE_DATE = '') \n" +
                    " AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.WORKORDER_OBJECT_CODE LIKE ?)" +
                    " AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.WORKORDER_OBJECT_NAME LIKE ?)" +
                    " AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.WORKORDER_OBJECT_LOCATION LIKE ?)";

            sqlArgs.add(userAccount.getOrganizationId());
//            sqlArgs.add(etsObject.getProjectId());
//            sqlArgs.add(etsObject.getProjectId());
            sqlArgs.add(etsObject.getWorkorderObjectCode());
            sqlArgs.add(etsObject.getWorkorderObjectCode());
            sqlArgs.add(etsObject.getWorkorderObjectName());
            sqlArgs.add(etsObject.getWorkorderObjectName());
            sqlArgs.add(etsObject.getWorkorderObjectLocation());
            sqlArgs.add(etsObject.getWorkorderObjectLocation());
        } else {
            sqlStr = "SELECT" +
                "       dbo.AWP_CAN_DISTRIBUTE(EO.WORKORDER_OBJECT_NO,'" + workorderType + "') CAN_DISTRIBUTE," +
                "       EO.WORKORDER_OBJECT_NO," +
                "       EO.WORKORDER_OBJECT_CODE," +
                "       EO.WORKORDER_OBJECT_NAME," +
                "       EO.WORKORDER_OBJECT_LOCATION," +
                "       EO.PROJECT_ID," +
                "       dbo.APP_GET_PROJECT_NAME(EO.PROJECT_ID) PROJECT_NAME" +
                "  FROM ETS_OBJECT EO,AMS_USER_OBJECT AUO" +
                "  WHERE AUO.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                "  AND AUO.USER_ID = ?\n" +
                "  AND NOT EXISTS\n" +
                " (SELECT 1\n" +
                "         FROM ETS_WORKORDER_TMP EWT\n" +
                "        WHERE EWT.WORKORDER_OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                "          AND EWT.WORKORDER_BATCH = ?)\n" +
                " AND EO.ORGANIZATION_ID=?" +
                " AND (EO.DISABLE_DATE IS NULL OR EO.DISABLE_DATE = '') " +
//                " AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.PROJECT_ID=?)" +
                " AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.WORKORDER_OBJECT_CODE LIKE ?)" +
                " AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.WORKORDER_OBJECT_NAME LIKE ?)" +
                " AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.WORKORDER_OBJECT_LOCATION LIKE ?)";

            sqlArgs.add(userAccount.getUserId());
            sqlArgs.add(workorderBatch);
            sqlArgs.add(userAccount.getOrganizationId());
//            sqlArgs.add(etsObject.getProjectId());
//            sqlArgs.add(etsObject.getProjectId());
            sqlArgs.add(etsObject.getWorkorderObjectCode());
            sqlArgs.add(etsObject.getWorkorderObjectCode());
            sqlArgs.add(etsObject.getWorkorderObjectName());
            sqlArgs.add(etsObject.getWorkorderObjectName());
            sqlArgs.add(etsObject.getWorkorderObjectLocation());
            sqlArgs.add(etsObject.getWorkorderObjectLocation());
        }
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    /**
     * 保存当前工单批号下的工单至临时表
     * @param workorderBatchNo
     * @return SQLModel
     */
    public SQLModel getCopyToTmpDataModel(String workorderBatchNo) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();

        String sqlStr = "INSERT INTO ETS_WORKORDER_TMP " +
                " SELECT * FROM ETS_WORKORDER EW " +
                " WHERE EW.WORKORDER_BATCH = ?";
        sqlArgs.add(workorderBatchNo);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    /**
     * 删除临时表下某工单批的数据
     * @param workorderBatchNo
     * @return SQLModel
     */
    public SQLModel getDeleteTmpDataModel(String workorderBatchNo) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();

        String sqlStr = "DELETE ETS_WORKORDER_TMP " +
                "WHERE WORKORDER_BATCH = ?";
        sqlArgs.add(workorderBatchNo);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    /**
     * 删除临时表数据（根据systemids）
     * @param systemids
     * @return SQLModel
     */
    public SQLModel getDeleteTmpDataModel(String[] systemids) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();

        String inStr = ArrUtil.arrToSqlStr(systemids);

        String sqlStr = "DELETE ETS_WORKORDER_TMP " +
                "WHERE SYSTEMID IN (" + inStr + ")";

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    public SQLModel getUpdateTmpDataModel(String[] systemids, String groupId, String implementBy, String checkoverBy) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();

        String inStr = ArrUtil.arrToSqlStr(systemids);

        StringBuilder sqlStr = new StringBuilder();
   /*     if (implementBy.equals("")) {
            sqlStr = "UPDATE ETS_WORKORDER_TMP " +
                    " SET CHECKOVER_BY = " + checkoverBy + "" +
                    " WHERE SYSTEMID IN" +
                    " (" + inStr + ")";
        }
        if (checkoverBy.equals("")) {
        */
            sqlStr.append("UPDATE ETS_WORKORDER_TMP SET IMPLEMENT_BY = "); 
            sqlStr.append(implementBy);
            sqlStr.append(",CHECKOVER_BY = ");
            sqlStr.append(checkoverBy);
            sqlStr.append(", GROUP_ID = ");
            sqlStr.append(groupId);
            sqlStr.append(" WHERE SYSTEMID IN");
            sqlStr.append(" (");
            sqlStr.append(inStr);
            sqlStr.append(")");
 //       }


        sqlModel.setSqlStr(sqlStr.toString());
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    /**
     * 更改工单开始时间、执行人、执行天数、搬迁地点
     * @param etsWorkorderDTO
     * @return SQLModel
     */
    public SQLModel getUpdateImplmentModel(EtsWorkorderDTO etsWorkorderDTO) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            String sqlStr = "UPDATE ETS_WORKORDER_TMP \n  " +
                    " SET START_DATE =?,\n" +
                    " IMPLEMENT_DAYS=?,\n" +
                    " IMPLEMENT_BY=?,\n" +
                    " ATTRIBUTE1=?,\n" +
                    " ATTRIBUTE5=?\n" +
                    " WHERE SYSTEMID=?";
            sqlArgs.add(etsWorkorderDTO.getStartDate());
            sqlArgs.add(etsWorkorderDTO.getImplementDays());
            sqlArgs.add(etsWorkorderDTO.getImplementBy());
            sqlArgs.add(etsWorkorderDTO.getAttribute1());
            sqlArgs.add(etsWorkorderDTO.getAttribute5());
            sqlArgs.add(etsWorkorderDTO.getSystemid());
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
        }
        return sqlModel;
    }


    /**
     * 删除工单配置信息
     * @param isTemp      是否临时信息
     * @param workorderNo 工单号
     * @return SQLModel
     */
    public SQLModel getDeleteSchemeModel(boolean isTemp, String workorderNo) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();

        String tableName = "ETS_WO_SCHEME";
        if (isTemp) {
            tableName = "ETS_WO_SCHEME_TMP";
        }
        String sqlStr = "DELETE " + tableName + "  WHERE WORKORDER_NO=?";
        sqlArgs.add(workorderNo);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 写工单配置信息
     * @param isTemp
     * @param etsWoScheme
     * @return SQLModel
     */

    public SQLModel getInsertSchemeModel(boolean isTemp, EtsWoSchemeDTO etsWoScheme) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
//        String tableName = "ETS_WO_SCHEME";

        String sqlStr = "INSERT INTO ETS_WO_SCHEME_TMP \n" +
                "  (SYSTEMID, WORKORDER_NO, ITEM_CODE, ITEM_QTY)\n" +
                " VALUES(?,?,?,?)";

        sqlArgs.add(etsWoScheme.getSystemid());
        sqlArgs.add(etsWoScheme.getWorkorderNo());
        sqlArgs.add(etsWoScheme.getItemCode());
        sqlArgs.add(etsWoScheme.getItemQty());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getSchemeQueryModel(boolean isTmp, String workorderNo) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String tableName = "ETS_WO_SCHEME";
        if (isTmp) {
            tableName = "ETS_WO_SCHEME_TMP";
        }
        String sqlStr = "SELECT EWS.WORKORDER_NO, EWS.ITEM_CODE, ESI.ITEM_NAME, ESI.ITEM_SPEC, EWS.ITEM_QTY\n" +
                "  FROM " + tableName + " EWS, ETS_SYSTEM_ITEM ESI\n" +
                " WHERE EWS.ITEM_CODE = ESI.ITEM_CODE" +
                "   AND EWS.WORKORDER_NO=?";

        sqlArgs.add(workorderNo);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 获取当前地点配置信息
     * @param objectNo
     * @param organizationId
     * @return SQLModel
     */
    public SQLModel getSchemeOfObjectModel(String objectNo, int organizationId, String itemCategory) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();

        String sqlStr = "SELECT ESI.ITEM_NAME, ESI.ITEM_SPEC, COUNT(1) ITEM_QTY\n" +
                "  FROM ETS_ITEM_INFO EII, ETS_SYSTEM_ITEM ESI, AMS_OBJECT_ADDRESS AOA\n" +
                " WHERE EII.ITEM_CODE = ESI.ITEM_CODE\n" +
                "   AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                "   AND EII.ADDRESS_ID IS NOT NULL OR EII.ADDRESS_ID > '0' \n" +
                "   AND AOA.OBJECT_NO = ?\n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_CATEGORY = ?)\n" +
                "   AND EII.ORGANIZATION_ID = ?\n" +
                " GROUP BY ESI.ITEM_NAME, ESI.ITEM_SPEC";
        sqlArgs.add(StrUtil.nullToString(objectNo));
        sqlArgs.add(itemCategory);
        sqlArgs.add(itemCategory);
        sqlArgs.add(organizationId);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 获取差异结果信息
     * @param workorderNo
     * @return SQLModel
     */
    public SQLModel getDiffOfOrderModel(String workorderNo) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT EWDD.BARCODE,\n" +
                "       EWDD.VERIFY_RESULT,\n" +
                "       dbo.APP_GET_FLEX_VALUE(CONVERT(VARCHAR,EWDD.SCAN_STATUS), '" + DictConstant.ORDER_ITEM_STATUS + "') SCAN_STATUS_DESC,\n" +
                "       dbo.APP_GET_FLEX_VALUE(CONVERT(VARCHAR,EWDD.ITEM_STATUS), '" + DictConstant.ORDER_ITEM_STATUS + "') ITEM_STATUS_DESC,\n" +
                "       ESI.ITEM_NAME,\n" +
                "       ESI.ITEM_SPEC,\n" +
                "       dbo.AMP_GET_DEPT_NAME(EWDD.RESPONSIBILITY_DEPT) RESPONSIBILITY_DEPT,\n" +
                "       dbo.AMP_GET_EMPLOYEE_NAME(EWDD.RESPONSIBILITY_USER) RESPONSIBILITY_USER,\n" +
                "       dbo.F_GET_ITEM_NAME(EWDD.SYSTEM_ITEM_CODE) SYSTEM_ITEM_NAME,\n" +
                "       dbo.F_GET_ITEM_SPEC(EWDD.SYSTEM_ITEM_CODE) SYSTEM_ITEM_SPEC,\n" +
                "       dbo.AMP_GET_DEPT_NAME(EWDD.SYSTEM_RESPONSIBILITY_DEPT) SYSTEM_RESPONSIBILITY_DEPT,\n" +
                "       dbo.AMP_GET_EMPLOYEE_NAME(EWDD.SYSTEM_RESPONSIBILITY_USER) SYSTEM_RESPONSIBILITY_USER,\n" +
                "       dbo.APP_GET_FLEX_VALUE(ESI.ITEM_CATEGORY, '" + DictConstant.ITEM_TYPE + "') ITEM_CATEGORY_DESC,\n" +
                "       dbo.APP_GET_VENDOR_NAME(ESI.VENDOR_ID) VENDOR_NAME,\n" +
                "       EWDD.REMARK\n" +
                "  FROM ETS_WORKORDER_DIFF_DTL EWDD, ETS_SYSTEM_ITEM ESI\n" +
                " WHERE EWDD.ITEM_CODE = ESI.ITEM_CODE\n" +
                "   AND EWDD.WORKORDER_NO = ?";
        sqlArgs.add(workorderNo);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getOldDiffOfOrderModel(String workorderNo) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT EWDD.BARCODE,\n" +
                "       EWDD.VERIFY_RESULT,\n" +
                "       dbo.APP_GET_FLEX_VALUE(CONVERT(VARCHAR,EWDD.SCAN_STATUS), '" + DictConstant.ORDER_ITEM_STATUS + "') SCAN_STATUS_DESC,\n" +
                "       dbo.APP_GET_FLEX_VALUE(CONVERT(VARCHAR,EWDD.ITEM_STATUS), '" + DictConstant.ORDER_ITEM_STATUS + "') ITEM_STATUS_DESC,\n" +
                "       ESI.ITEM_NAME,\n" +
                "       ESI.ITEM_SPEC,\n" +
                "       dbo.APP_GET_FLEX_VALUE(ESI.ITEM_CATEGORY, '" + DictConstant.ITEM_TYPE + "') ITEM_CATEGORY_DESC,\n" +
                "       dbo.APP_GET_VENDOR_NAME(ESI.VENDOR_ID) VENDOR_NAME,\n" +
                "       EWDD.REMARK\n" +
                "  FROM ETS_WORKORDER_DIFF_DTL EWDD, ETS_SYSTEM_ITEM ESI\n" +
                " WHERE EWDD.ITEM_CODE = ESI.ITEM_CODE\n" +
                "   AND EWDD.WORKORDER_NO = ?";
        sqlArgs.add(workorderNo);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 签收工单（批量）
     * 1.更改工单状态；2.写工单进度表
     * @param workorders
     * @param userAccount
     * @return
     */
    public List getSignOrdersModel(String[] workorders, SfUserDTO userAccount) {
        List sqlModelList = new ArrayList();
        String strWorkorders = ArrUtil.arrToSqlStr(workorders);
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr =
                "UPDATE ETS_WORKORDER\n" +
                        "   SET WORKORDER_FLAG = ?,\n" +
                        "       IMPLEMENT_BY   = ?,\n" +
                        "       DOWNLOAD_DATE  = GETDATE(),\n" +
                        "       DOWNLOAD_BY    = ?\n" +
                        " WHERE WORKORDER_NO IN (" + strWorkorders + ")\n" +
                        "       AND WORKORDER_FLAG = ?";
        sqlArgs.add(DictConstant.WOR_STATUS_DOWNLOAD);
        sqlArgs.add(userAccount.getUserId());
        sqlArgs.add(userAccount.getUserId());
//        sqlArgs.add(strWorkorders);
        sqlArgs.add(DictConstant.WOR_STATUS_DEPLOY);

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);
        sqlModelList.add(sqlModel);

        WorkorderModel workorderModel = new WorkorderModel();
        sqlModel = workorderModel.getUpdateOrderProcessModel(workorders, DictConstant.WORKORDER_NODE_DOWNLOADED, true);
        sqlModelList.add(sqlModel);
        return sqlModelList;
    }

    /**
     * 根据工单号去地点ID
     * @param workorderNo 工单号
     * @return SQLModel
     */
    public SQLModel getAddressByOrderNo(String workorderNo) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr =
                "SELECT AOA.ADDRESS_ID, EW.WORKORDER_OBJECT_NO,AOA.BOX_NO,AOA.NET_UNIT\n" +
                        "  FROM ETS_WORKORDER EW, AMS_OBJECT_ADDRESS AOA\n" +
                        " WHERE EW.WORKORDER_OBJECT_NO = AOA.OBJECT_NO\n" +
                        "   AND EW.WORKORDER_NO = ?";
        sqlArgs.add(workorderNo);

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }
    /**
     * 获取当前有效的资产管理员。
     * @param workorderObjNo	地点编码ID
     * @organizationId			组织ID
     * @return SQLModel
     */
    public SQLModel getAvailableAssetsAdmin(String workorderObjNo,int organizationId)
    {
    	SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr ="SELECT SG.GROUP_NAME,SG.GROUP_ID,SU.USERNAME,SU.USER_ID FROM ETS_OBJECT EO,ETS_OU_CITY_MAP EOCM,AMS_MIS_DEPT AMD,SINO_GROUP_MATCH SGM,SF_GROUP SG,SF_USER_AUTHORITY SUA,SF_USER SU WHERE SUBSTRING(EO.WORKORDER_OBJECT_CODE,1,6)=AMD.COST_CENTER_CODE AND EO.ORGANIZATION_ID=EOCM.ORGANIZATION_ID AND AMD.COMPANY_CODE=EOCM.COMPANY_CODE AND AMD.DEPT_CODE=SGM.DEPT_ID AND SGM.GROUP_ID=SG.GROUP_ID AND SG.GROUP_NAME=SUA.GROUP_NAME AND SUA.USER_ID=SU.USER_ID AND SUA.ROLE_NAME='单位资产管理员' AND EO.WORKORDER_OBJECT_NO=? AND EO.ORGANIZATION_ID=?";
        sqlArgs.add(workorderObjNo);
        sqlArgs.add(organizationId);
        sqlModel.setArgs(sqlArgs);
       
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }
}
