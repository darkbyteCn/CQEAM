package com.sino.pda;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.util.StrUtil;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.system.fixing.dto.EtsItemInfoDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.system.user.util.UserUtil;
import com.sino.ams.workorder.dto.EtsWorkorderDtlDTO;

/**
 * User: zhoujs
 * Date: 2007-10-23
 * Time: 16:57:07
 * Function:
 */
public class OrderModel {
    public SQLModel getOrdersModel(SfUserDTO sfUser, boolean forceDown) {
        UserUtil userUtil = new UserUtil(sfUser);
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String flags = "";
        if (forceDown) {
            flags = "'" + DictConstant.WORKORDER_STATUS_DISTRUIBUTED + "','" + DictConstant.WORKORDER_STATUS_DOWNLOADED + "'";
        } else {
            flags = "'" + DictConstant.WORKORDER_STATUS_DISTRUIBUTED + "'";
        }
        String sqlStr = "SELECT EW.PRJ_ID,EW.WORKORDER_NO,\n" +
                "       dbo.APP_GET_FLEX_VALUE(EW.WORKORDER_TYPE, 'WORKORDER_TYPE') WORKORDER_TYPE_DESC,\n" +
                "       EO.WORKORDER_OBJECT_LOCATION,\n" +
                "       EW.CREATION_DATE,\n" +
                "       dbo.APP_GET_USER_NAME(EW.CREATED_BY) CREATE_USER,\n" +
                "       EW.START_DATE,\n" +
                "       EW.IMPLEMENT_DAYS,\n" +
                "       dateadd(day,EW.IMPLEMENT_DAYS,EW.START_DATE) DEADLINE_DATE,\n" +
                "       EW.DISTRIBUTE_DATE,\n" +
                "       EW.DOWNLOAD_DATE,\n" +
                "       EW.WORKORDER_OBJECT_NO,\n" +
                "       dbo.APP_GET_USER_NAME(EW.IMPLEMENT_BY) IMPLEMENT_USER,\n" +
                "       dbo.APP_GET_PROJECT_NAME(EW.PRJ_ID) PRJ_NAME,\n" +
                "       dbo.APP_GET_OBJECT_NAME(EW.ATTRIBUTE1) NEWOBJNAME,\n" +
                "       EW.ATTRIBUTE1 NEW_OBJECT_NO,\n" +
                "       EW.GROUP_ID,\n" +
                "       EW.ATTRIBUTE7 CATEGORY,\n" +
                "       EW.COST_CENTER_CODE,\n" +
                "       case EW.ATTRIBUTE7 when 'ALL' then '1' when '' then '1' else '0' end  INSPECT_RANGE\n" +
                "  FROM ETS_WORKORDER EW, ETS_OBJECT EO\n" +
                " WHERE EW.WORKORDER_OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
//                " AND EW.GROUP_ID IN (" + userUtil.getUserGroupIds() + ") \n" +
                " AND EW.WORKORDER_FLAG IN (" + flags + ") \n" +
                " AND (dbo.APP_GET_USER_NAME(EW.IMPLEMENT_BY)=? OR (EW.IMPLEMENT_BY =-1 AND EW.GROUP_ID IN (" + userUtil.getUserGroupIds() +")))\n" +
                " AND EW.ORGANIZATION_ID=?";

//        sqlArgs.add(UserUtil.getUserGroupIds(sfUser));
//        if (forceDown) {
//            sqlArgs.add("'" + DictConstant.WORKORDER_STATUS_DISTRUIBUTED + "','" + DictConstant.WORKORDER_STATUS_DOWNLOADED + "'");
//        } else {
//            sqlArgs.add("" + DictConstant.WORKORDER_STATUS_DISTRUIBUTED + "");
//        }
//        sqlArgs.add(sfUser.getUsername());
        sqlArgs.add(sfUser.getUsername());
        sqlArgs.add(sfUser.getOrganizationId());

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }

    /**
     * 获取指定工单地点下设备信息
     * @param workorderNo   String
     * @param itemCategory  String
     * @param orgnizationId String
     * @return SQLModel
     */
    public SQLModel getItemsModel(String workorderNo, String itemCategory, int orgnizationId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT EII.BARCODE,\n" +
                "       ESI.ITEM_NAME,\n" +
                "       ESI.ITEM_SPEC,\n" +
                "       ESI.ITEM_CATEGORY,\n" +
                "       EII.ITEM_QTY,\n" +
                "       EII.ITEM_CODE,\n" +
                "       EII.VENDOR_BARCODE,\n" +
                "       EII.START_DATE,\n" +
                "       EII.PARENT_BARCODE,\n" +
                "       AOA.ADDRESS_ID,\n" +
                "       AOA.BOX_NO,\n" +
                "       AOA.NET_UNIT,\n" +
                "       EII.ADDRESS_ID,\n" +
                "       EII.RESPONSIBILITY_DEPT ASSIGN_GROUPID,\n" +
                "       EII.RESPONSIBILITY_USER ASSIGN_USERID,\n" +
                "       EII.MAINTAIN_USER USERNAME,\n" +
                "       EW.WORKORDER_NO,\n" +
                "       EII.CONTENT_CODE,\n" +
                "       EII.CONTENT_NAME, \n" +
                "       EII.SHARE_STATUS, \n" +
                "       EII.CONSTRUCT_STATUS, \n" + 
                "       EII.LNE_ID, \n" + 
                "       EII.CEX_ID, \n" +  
                "       EII.OPE_ID, \n" + 
                "       EII.NLE_ID \n" +  
                "  FROM ETS_ITEM_INFO      EII,\n" +
                "       ETS_SYSTEM_ITEM    ESI,\n" +
                "       ETS_WORKORDER      EW,\n" +
                "       AMS_OBJECT_ADDRESS AOA\n" +
                " WHERE EII.ITEM_CODE = ESI.ITEM_CODE\n" +
                "   AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                "   AND  " + SyBaseSQLUtil.isNotNull("EII.ADDRESS_ID") + " \n" +
                "   AND EW.WORKORDER_OBJECT_NO = AOA.OBJECT_NO\n" +
                "   AND EW.WORKORDER_TYPE=?\n" +
                "   AND EW.WORKORDER_NO=?\n" +
                "   AND EII.ORGANIZATION_ID=?\n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_CATEGORY=?)";

        sqlArgs.add(DictConstant.ORDER_TYPE_CHECK);
        sqlArgs.add(workorderNo);
        sqlArgs.add(orgnizationId);
        sqlArgs.add(itemCategory);
        sqlArgs.add(itemCategory);


        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }

    /**
     * 取交接工单的清单
     * @param workorderNo
     * @param itemCategory
     * @param orgnizationId
     * @return
     */
    public SQLModel getOrderItemsModel(String workorderNo, String itemCategory, int orgnizationId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT EII.BARCODE,\n" +
                "       ESI.ITEM_NAME,\n" +
                "       ESI.ITEM_SPEC,\n" +
                "       ESI.ITEM_CATEGORY,\n" +
                "       EII.ITEM_QTY,\n" +
                "       EII.ITEM_CODE,\n" +
                "       EII.VENDOR_BARCODE,\n" +
                "       EII.START_DATE,\n" +
                "       EII.PARENT_BARCODE,\n" +
                "       EII.CONTENT_CODE,\n" +
                "       EII.CONTENT_NAME,\n" +
                "       EII.MANUFACTURER_ID,\n" +
                "       EII.MANUFACTURER_ID,\n" +
                "       EII.SHARE_STATUS,\n" +
                "       EII.CONSTRUCT_STATUS,\n" +
                "       EII.LNE_ID,\n" +
                "       EII.CEX_ID,\n" +
                "       EII.OPE_ID,\n" +
                "       EII.NLE_ID,\n" +
                "       AOA.ADDRESS_ID,\n" +
                "       AOA.BOX_NO,\n" +
                "       AOA.NET_UNIT,\n" +
                "       EII.ADDRESS_ID,\n" +
                "       EII.RESPONSIBILITY_DEPT ASSIGN_GROUPID,\n" +
                "       EII.RESPONSIBILITY_USER ASSIGN_USERID,\n" +
                "       EII.MAINTAIN_USER USERNAME,\n" +
                "       EW.WORKORDER_NO\n" +
                "  FROM ETS_ITEM_INFO      EII,\n" +
                "       ETS_SYSTEM_ITEM    ESI,\n" +
                "       ETS_WORKORDER      EW,\n" +
                "       AMS_OBJECT_ADDRESS AOA\n" +
                " WHERE EII.ITEM_CODE = ESI.ITEM_CODE\n" +
                "   AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                "   AND  " + SyBaseSQLUtil.isNotNull("EII.ADDRESS_ID") + " \n" +
                "   AND EW.WORKORDER_OBJECT_NO = AOA.OBJECT_NO\n" +
                "   AND EW.PRJ_ID = EII.PROJECTID\n" +
                "   AND EW.WORKORDER_TYPE=?\n" +
                "   AND EW.WORKORDER_NO=?\n" +
                "   AND EII.ORGANIZATION_ID=?\n" +
                "   AND EII.FINANCE_PROP=?\n" +
                "   AND EII.ITEM_STATUS=?\n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_CATEGORY=?)";

        sqlArgs.add(DictConstant.ORDER_TYPE_HDV);
        sqlArgs.add(workorderNo);
        sqlArgs.add(orgnizationId);
        sqlArgs.add(DictConstant.FIN_PROP_PRJ);
        sqlArgs.add(DictConstant.ITEM_STATUS_PRE_ASSETS);
        sqlArgs.add(itemCategory);
        sqlArgs.add(itemCategory);


        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }

    /**
     * 山西用，根据部门下载设备清单
     * @param workorderNo    工单号
     * @param itemCategory   设备分类
     * @param orgnizationId  组织ID
     * @param hasMatchedDetp 是否有对应的MIS部门
     * @return SQLModel
     */
    public SQLModel getSXItemsModel(String workorderNo, String itemCategory, String orgnizationId, boolean hasMatchedDetp) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "";

        sqlStr = "SELECT EII.BARCODE,\n" +
                "       ESI.ITEM_NAME,\n" +
                "       ESI.ITEM_SPEC,\n" +
                "       ESI.ITEM_CATEGORY,\n" +
                "       EII.ITEM_QTY,\n" +
                "       EII.ITEM_CODE,\n" +
                "       EII.VENDOR_BARCODE,\n" +
                "       EII.START_DATE,\n" +
                "       EII.PARENT_BARCODE,\n" +
                "       AOA.ADDRESS_ID,\n" +
                "       AOA.BOX_NO,\n" +
                "       AOA.NET_UNIT,\n" +
                "       EII.ADDRESS_ID,\n" +
                "       EII.RESPONSIBILITY_DEPT ASSIGN_GROUPID,\n" +
                "       EII.RESPONSIBILITY_USER ASSIGN_USERID,\n" +
                "       EII.MAINTAIN_USER USERNAME,\n" +
                "       EW.WORKORDER_NO\n" +
                "  FROM ETS_ITEM_INFO      EII,\n" +
                "       ETS_SYSTEM_ITEM    ESI,\n" +
                "       ETS_WORKORDER      EW,\n" +
                "       AMS_OBJECT_ADDRESS AOA,\n" +
                "       AMS_MIS_DEPT       AMD,\n" +
                "       SF_GROUP_MATCH     SGM,\n" +
                "       SF_GROUP           SG\n" +
                " WHERE EII.ITEM_CODE = ESI.ITEM_CODE\n" +
                "   AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                "   AND  " + SyBaseSQLUtil.isNotNull("EII.ADDRESS_ID") + " \n" +
                "   AND EW.WORKORDER_OBJECT_NO = AOA.OBJECT_NO\n" +
                "   AND EW.WORKORDER_TYPE = ?\n" +
                "   AND EW.WORKORDER_NO = ?\n" +
                "   AND EII.ORGANIZATION_ID = ?\n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_CATEGORY = ?)\n" +
                "   AND EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE\n" +
                "   AND AMD.DEPT_CODE = SGM.DEPT_CODE";
        if (hasMatchedDetp) {
            sqlStr += "\n     AND SG.GROUP_ID = SGM.GROUP_ID";
            sqlStr += "\n     AND EW.GROUP_ID = SG.GROUP_ID";
        }

        sqlArgs.add(DictConstant.ORDER_TYPE_CHECK);
        sqlArgs.add(workorderNo);
        sqlArgs.add(orgnizationId);
        sqlArgs.add(itemCategory);
        sqlArgs.add(itemCategory);


        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }

    /**
     * 下载指定成本中心的设备
     * @param workorderNo    工单号
     * @param itemCategory   扫描专业
     * @param orgnizationId  组织ID
     * @param costCenterCode 成本中心代码
     * @return
     */
    public SQLModel getCostCenterItemsModel(String workorderNo, String itemCategory, int orgnizationId, String costCenterCode) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();

        String sqlStr = "SELECT EII.BARCODE,\n" +
                "       ESI.ITEM_NAME,\n" +
                "       ESI.ITEM_SPEC,\n" +
                "       ESI.ITEM_CATEGORY,\n" +
                "       EII.ITEM_QTY,\n" +
                "       EII.ITEM_CODE,\n" +
                "       EII.VENDOR_BARCODE,\n" +
                "       EII.START_DATE,\n" +
                "       EII.PARENT_BARCODE,\n" +
                "       AOA.ADDRESS_ID,\n" +
                "       AOA.BOX_NO,\n" +
                "       AOA.NET_UNIT,\n" +
                "       EII.ADDRESS_ID,\n" +
                "       EII.RESPONSIBILITY_DEPT ASSIGN_GROUPID,\n" +
                "       EII.RESPONSIBILITY_USER ASSIGN_USERID,\n" +
                "       EII.MAINTAIN_USER USERNAME,\n" +
                "       EW.WORKORDER_NO\n" +
                "  FROM ETS_ITEM_INFO      EII,\n" +
                "       ETS_SYSTEM_ITEM    ESI,\n" +
                "       ETS_WORKORDER      EW,\n" +
                "       AMS_OBJECT_ADDRESS AOA\n" +
                " WHERE EII.ITEM_CODE = ESI.ITEM_CODE\n" +
                "   AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                "   AND  " + SyBaseSQLUtil.isNotNull("EII.ADDRESS_ID") + " \n" +
                "   AND EW.WORKORDER_OBJECT_NO = AOA.OBJECT_NO\n" +
                "   AND EW.WORKORDER_TYPE=?\n" +
                "   AND EW.WORKORDER_NO=?\n" +
                "   AND EII.ORGANIZATION_ID=?\n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_CATEGORY=?)\n" +
                " AND EXISTS (SELECT 1\n" +
                "         FROM AMS_COST_DEPT_MATCH ACDM\n" +
                "        WHERE ACDM.DEPT_CODE = EII.RESPONSIBILITY_DEPT\n" +
                "          AND ACDM.COST_CENTER_CODE = ?)";


        sqlArgs.add(DictConstant.ORDER_TYPE_CHECK);
        sqlArgs.add(workorderNo);
        sqlArgs.add(orgnizationId);
        sqlArgs.add(itemCategory);
        sqlArgs.add(itemCategory);
        sqlArgs.add(costCenterCode);


        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }

    public SQLModel getNewConfigModel(String workorderNo) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT ESI.ITEM_NAME, ESI.ITEM_SPEC, ESI.ITEM_CATEGORY, EWS.ITEM_QTY\n" +
                "  FROM ETS_WO_SCHEME EWS, ETS_SYSTEM_ITEM ESI\n" +
                " WHERE EWS.ITEM_CODE = ESI.ITEM_CODE\n" +
                "   AND EWS.WORKORDER_NO = ?";
        sqlModel.setSqlStr(sqlStr);
        sqlArgs.add(workorderNo);

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }
    
//    public SQLModel getNewConfigModelByPrjId(String workorderNo,String prjId) {
//        SQLModel sqlModel = new SQLModel();
//        List sqlArgs = new ArrayList();
//        String sqlStr = "SELECT ESI.ITEM_NAME, ESI.ITEM_SPEC, ESI.ITEM_CATEGORY, EWS.ITEM_QTY\n" +
//                "  FROM ETS_WO_SCHEME EWS, ETS_SYSTEM_ITEM ESI\n" +
//                " WHERE EWS.ITEM_CODE = ESI.ITEM_CODE\n" +
//                "   AND EWS.WORKORDER_NO = ?";
//        sqlModel.setSqlStr(sqlStr);
//        sqlArgs.add(workorderNo);
//
//        sqlModel.setArgs(sqlArgs);
//        sqlModel.setSqlStr(sqlStr);
//
//        return sqlModel;
//    }

    public SQLModel getCurrentConfigModel(String objectNo, String itemCategory) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT ESI.ITEM_NAME, ESI.ITEM_SPEC, COUNT(1) NCOUNT\n" +
                "  FROM ETS_ITEM_INFO EII, ETS_SYSTEM_ITEM ESI, AMS_OBJECT_ADDRESS AOA\n" +
                " WHERE EII.ITEM_CODE = ESI.ITEM_CODE\n" +
                "   AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
               // "   AND  " + SyBaseSQLUtil.isNotNull("EII.ADDRESS_ID") + " \n" +
                "   AND AOA.OBJECT_NO =?\n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_CATEGORY=?)\n" +
                " GROUP BY ESI.ITEM_NAME, ESI.ITEM_SPEC";
        sqlArgs.add(objectNo);
        sqlArgs.add(itemCategory);
        sqlArgs.add(itemCategory);

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }
    
    public SQLModel getCurrentConfigModelByPrjId(String objectNo, String itemCategory,String prjId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT ESI.ITEM_NAME, ESI.ITEM_SPEC, COUNT(1) NCOUNT\n" +
                "  FROM ETS_ITEM_INFO EII, ETS_SYSTEM_ITEM ESI, AMS_OBJECT_ADDRESS AOA\n" +
                " WHERE EII.ITEM_CODE = ESI.ITEM_CODE\n" +
                " AND EII.BARCODE LIKE '3910-70%'\n" +
                "   AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
               // "   AND  " + SyBaseSQLUtil.isNotNull("EII.ADDRESS_ID") + " \n" +
                "   AND AOA.OBJECT_NO =?\n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_CATEGORY=?)\n" +
                " GROUP BY ESI.ITEM_NAME, ESI.ITEM_SPEC";
        sqlArgs.add(objectNo);
        sqlArgs.add(itemCategory);
        sqlArgs.add(itemCategory);

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }


    /**
     * 更新工单状态（上传时)
     * @param workorderNo  String
     * @param scanoverDate String
     * @return SQLModel
     */
    public SQLModel getUpdateUploadOrderModel(String workorderNo, String scanoverDate) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE ETS_WORKORDER SET " +
                " LAST_UPDATE_DATE=GETDATE()," +
                " WORKORDER_FLAG='" + DictConstant.WORKORDER_STATUS_UPLOADED + "'," +
                " UPLOAD_DATE=GETDATE()," +
                " SCANOVER_DATE=GETDATE()" +
                " WHERE WORKORDER_NO=?";
        sqlArgs.add(workorderNo);
        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }

    /**
     * 工单扫描结果存贮
     * @param etsItemInfo
     * @param workorderNo
     * @param status
     * @return SQLModel
     */
    public SQLModel getInsertDtlModel(EtsItemInfoDTO etsItemInfo, String workorderNo, String status) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO ETS_WORKORDER_DTL\n" +
                "  (WORKORDER_NO,\n" +
                "   BARCODE,\n" +
                "   ITEM_STATUS,\n" +
                "   ITEM_QTY,\n" +
                "   REMARK,\n" +
                "   ITEM_CODE,\n" +
                "   ADDRESS_ID,\n" +
                "   BOX_NO,\n" +
                "   NET_UNIT,\n" +
                "   PARENT_BARCODE,\n" +
                "   ITEM_REMARK,\n" +
                "   RESPONSIBILITY_DEPT,\n" +
                "   RESPONSIBILITY_USER,\n" +
                "   MAINTAIN_USER,\n" +
                "   MANUFACTURER_ID,\n" +
                "   IS_SHARE,\n" +
                "   POWER)\n" +
                " VALUES\n" +
                "  (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        sqlArgs.add(workorderNo);
        sqlArgs.add(etsItemInfo.getBarcode());
        sqlArgs.add(StrUtil.strToInt(status));
        sqlArgs.add(StrUtil.strToInt(etsItemInfo.getItemQty()));
        sqlArgs.add(etsItemInfo.getRemark());
        sqlArgs.add(etsItemInfo.getItemCode());
        sqlArgs.add(etsItemInfo.getAddressId());
        sqlArgs.add(etsItemInfo.getBoxNo());
        sqlArgs.add(etsItemInfo.getNetUnit());
        sqlArgs.add(etsItemInfo.getParentBarcode());
        sqlArgs.add("");
        sqlArgs.add(etsItemInfo.getResponsibilityDept());
        sqlArgs.add(etsItemInfo.getResponsibilityUser());
        sqlArgs.add(etsItemInfo.getMaintainUser());
        sqlArgs.add(etsItemInfo.getManufacturerId());
        sqlArgs.add(etsItemInfo.getShare());
        sqlArgs.add(etsItemInfo.getPower());

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }

    /**
     * 更新扫描工单系统信息(系统当前信息--责任人、责任部门、设备分类)
     * @param workorderNo
     * @param objectNo
     * @return
     */
    public SQLModel getUpdateDtlModel(String workorderNo,String objectNo) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr ="UPDATE ETS_WORKORDER_DTL SET " +
                " SYSTEM_ITEM_CODE=(SELECT ITEM_CODE FROM ETS_ITEM_INFO EII WHERE EII.BARCODE=ETS_WORKORDER_DTL.BARCODE),\n" +
                " SYSTEM_RESPONSIBILITY_DEPT=(SELECT RESPONSIBILITY_DEPT FROM ETS_ITEM_INFO EII WHERE EII.BARCODE=ETS_WORKORDER_DTL.BARCODE),\n" +
                " SYSTEM_RESPONSIBILITY_USER=(SELECT RESPONSIBILITY_USER FROM ETS_ITEM_INFO EII WHERE EII.BARCODE=ETS_WORKORDER_DTL.BARCODE),\n" +
                " SYSTEM_STATUS=5\n" +
                " WHERE EXISTS(SELECT 1 FROM ETS_ITEM_INFO EII,AMS_OBJECT_ADDRESS AOA WHERE EII.ADDRESS_ID=AOA.ADDRESS_ID AND AOA.OBJECT_NO=? AND EII.BARCODE=ETS_WORKORDER_DTL.BARCODE )\n" +
                " AND WORKORDER_NO=?";

        sqlArgs.add(objectNo);
        sqlArgs.add(workorderNo);

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }

    /**
     * 写入Dtl表
     * @param orderDtlDTO
     * @return
     */
    public SQLModel getInsertDtlModel(EtsWorkorderDtlDTO orderDtlDTO) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO ETS_WORKORDER_DTL\n" +
                "  (WORKORDER_NO,\n" +
                "   BARCODE,\n" +
                "   ITEM_STATUS,\n" +
                "   ITEM_QTY,\n" +
                "   REMARK,\n" +
                "   ITEM_CODE,\n" +
                "   ADDRESS_ID,\n" +
                "   BOX_NO,\n" +
                "   NET_UNIT,\n" +
                "   PARENT_BARCODE,\n" +
                "   ITEM_REMARK)\n" +
                " VALUES\n" +
                "  (?,?,?,?,?,?,?,?,?,?,?)";

        sqlArgs.add(orderDtlDTO.getWorkorderNo());
        sqlArgs.add(orderDtlDTO.getBarcode());
        sqlArgs.add(orderDtlDTO.getItemStatus());
        sqlArgs.add(orderDtlDTO.getItemQty());
        sqlArgs.add(orderDtlDTO.getRemark());
        sqlArgs.add(orderDtlDTO.getItemCode());
        sqlArgs.add(orderDtlDTO.getAddressId());
        sqlArgs.add(orderDtlDTO.getBoxNo());
        sqlArgs.add(orderDtlDTO.getNetUnit());
        sqlArgs.add(orderDtlDTO.getParentBarcode());
        sqlArgs.add(orderDtlDTO.getItemRemark());


        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }

    /**
     * 插入接口表
     * @param etsItemInfo
     * @param workorderNo
     * @param status
     * @return
     */
    public SQLModel getInsertInterfaceModel(EtsItemInfoDTO etsItemInfo, String workorderNo, String status) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO ETS_WORKORDER_INTERFACE\n" +
                "  (WORKORDER_NO,\n" +
                "   BARCODE,\n" +
                "   ITEM_STATUS,\n" +
                "   ITEM_QTY,\n" +
                "   REMARK,\n" +
                "   ITEM_CODE,\n" +
                "   ADDRESS_ID,\n" +
                "   BOX_NO,\n" +
                "   NET_UNIT,\n" +
                "   PARENT_BARCODE,\n" +
                "   ITEM_REMARK,\n" +
                "   RESPONSIBILITY_DEPT,\n" +
                "   RESPONSIBILITY_USER,\n" +
                "   MAINTAIN_USER,\n" +
                "   MANUFACTURER_ID,\n" +
                "   IS_SHARE,\n" +
                "   POWER)\n" +
                " VALUES\n" +
                "  (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        sqlArgs.add(workorderNo);
        sqlArgs.add(etsItemInfo.getBarcode());
        sqlArgs.add(StrUtil.strToInt(status));
        sqlArgs.add(StrUtil.strToInt(etsItemInfo.getItemQty()));
        sqlArgs.add(etsItemInfo.getRemark());
        sqlArgs.add(etsItemInfo.getItemCode());
        sqlArgs.add(etsItemInfo.getAddressId());
        sqlArgs.add(etsItemInfo.getBoxNo());
        sqlArgs.add(etsItemInfo.getNetUnit());
        sqlArgs.add(etsItemInfo.getParentBarcode());
        sqlArgs.add("");
        sqlArgs.add(etsItemInfo.getResponsibilityDept());
        sqlArgs.add(etsItemInfo.getResponsibilityUser());
        sqlArgs.add(etsItemInfo.getMaintainUser());
        sqlArgs.add(etsItemInfo.getManufacturerId());
        sqlArgs.add(etsItemInfo.getShare());
        sqlArgs.add(etsItemInfo.getPower());

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }

    public SQLModel getInsertInterfaceModel(EtsWorkorderDtlDTO orderDtlDTO) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO ETS_WORKORDER_INTERFACE\n" +
                "  (WORKORDER_NO,\n" +
                "   BARCODE,\n" +
                "   ITEM_STATUS,\n" +
                "   ITEM_QTY,\n" +
                "   REMARK,\n" +
                "   ITEM_CODE,\n" +
                "   ADDRESS_ID,\n" +
                "   BOX_NO,\n" +
                "   NET_UNIT,\n" +
                "   PARENT_BARCODE,\n" +
                "   ITEM_REMARK)\n" +
                " VALUES\n" +
                "  (?,?,?,?,?,?,?,?,?,?,?)";

        sqlArgs.add(orderDtlDTO.getWorkorderNo());
        sqlArgs.add(orderDtlDTO.getBarcode());
        sqlArgs.add(orderDtlDTO.getItemStatus());
        sqlArgs.add(orderDtlDTO.getItemQty());
        sqlArgs.add(orderDtlDTO.getRemark());
        sqlArgs.add(orderDtlDTO.getItemCode());
        sqlArgs.add(orderDtlDTO.getAddressId());
        sqlArgs.add(orderDtlDTO.getBoxNo());
        sqlArgs.add(orderDtlDTO.getNetUnit());
        sqlArgs.add(orderDtlDTO.getParentBarcode());
        sqlArgs.add(orderDtlDTO.getItemRemark());


        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }

    /**
     * 首次写工单进度表 (插入)
     * @param workorderBach
     * @param isTemp
     * @param isOver
     * @return
     */
    public SQLModel getInsertProcessModel(String workorderBach, boolean isTemp, boolean isOver) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO ETS_PROCESSBAR \n" +
                "  (BATCH_NO, WORKORDER_NO, PROCESS1)\n" +
                "  SELECT WORKORDER_BATCH, WORKORDER_NO, ?\n" +
                "    FROM ETS_WORKORDER \n" +
                "   WHERE WORKORDER_BATCH = ?";
        if (isOver) {
            sqlStr = "INSERT INTO ETS_PROCESSBAR \n" +
                    "  (BATCH_NO, WORKORDER_NO, PROCESS1,PROCESS3)\n" +
                    "  SELECT WORKORDER_BATCH, WORKORDER_NO, ?,'100'\n" +
                    "    FROM ETS_WORKORDER \n" +
                    "   WHERE WORKORDER_BATCH = ?";
        }
        if (isTemp) {
            sqlArgs.add("50");
        } else {
            sqlArgs.add("100");
        }
        sqlArgs.add(workorderBach);

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }

    /**
     * 更新工单进度条
     * @param workorderBach
     * @param isTemp
     * @return
     */
    public SQLModel getUpdateProcessModel(String workorderBach, String sectionRight, boolean isTemp) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE ETS_PROCESSBAR\n" +
//                "  SET ?=?\n" +
                "  SET " + sectionRight + " = ?\n" +
                "   WHERE BATCH_NO = ?";
        if (isTemp) {
            sqlArgs.add("50");
        } else {
            sqlArgs.add("100");
        }
        sqlArgs.add(workorderBach);

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }

    /**
     * 更新工单状态,下载前
     * @param workorderBach
     * @param workorderNode
     * @return
     */
    public SQLModel getUpdateOrderStatusModel(String workorderBach, String workorderNode) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "";
        if (workorderNode.equals(DictConstant.WORKORDER_NODE_DISTRUIBUTE) || workorderNode.equals(DictConstant.WORKORDER_NODE_NEW)) {
            sqlStr = "UPDATE ETS_WORKORDER\n" +
                    "   SET WORKORDER_FLAG=?,\n" +
                    "       DISTRIBUTE_DATE=GETDATE()\n" +
                    " WHERE WORKORDER_BATCH=?";
            sqlArgs.add(DictConstant.WORKORDER_STATUS_DISTRUIBUTED);
            sqlArgs.add(workorderBach);
        } else if (workorderNode.equals(DictConstant.WORKORDER_NODE_CANCEL)) {
            sqlStr = "UPDATE ETS_WORKORDER\n" +
                    "   SET WORKORDER_FLAG=?\n" +
                    " WHERE WORKORDER_BATCH=?";
            sqlArgs.add(DictConstant.WORKORDER_STATUS_CANCELE);
            sqlArgs.add(workorderBach);
        }

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }

    //================================================ 归档
    public SQLModel getOrderModel(SfUserDTO sfUser, String workorderNo, String locName) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT EW.WORKORDER_BATCH,\n" +
                "       EWB.WORKORDER_BATCH_NAME,\n" +
                "       EW.SYSTEMID,\n" +
                "       EW.WORKORDER_NO,\n" +
                "       EW.WORKORDER_OBJECT_NO,\n" +
                "       'PLACEHOLDER' PLACEHOLDER,\n" +
                "       EW.WORKORDER_TYPE,\n" +
                "       dbo.APP_GET_FLEX_VALUE(EW.WORKORDER_TYPE,'WORKORDER_TYPE') WORKORDER_TYPE_DESC,\n" +
                "       EW.START_DATE,\n" +
                "       EW.IMPLEMENT_BY,\n" +
                "       EO.WORKORDER_OBJECT_LOCATION OBJECT_NAME,\n" +
                "       SUV.USERNAME,\n" +
                "        EW.UPLOAD_DATE  UPLOAD_DATE,\n" +
                "       EW.ORGANIZATION_ID,\n" +
                "       '' FIRSTPENDINGORDER,\n" +
                "       EW.DISTRIBUTE_DATE,\n" +
                "       '' HASDIFF\n" +
                "  FROM ETS_WORKORDER EW, SF_USER_V SUV,ETS_WORKORDER_BATCH EWB,ETS_OBJECT EO\n" +
                " WHERE EW.WORKORDER_FLAG = '" + DictConstant.WORKORDER_STATUS_UPLOADED + "'\n" +
                "   AND EW.WORKORDER_BATCH=EWB.WORKORDER_BATCH\n" +
                "   AND EW.IMPLEMENT_BY = SUV.USER_ID\n" +
                "   AND EW.WORKORDER_OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                "   AND EW.ORGANIZATION_ID = EO.ORGANIZATION_ID\n" +
                "   AND EW.ORGANIZATION_ID=?\n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EW.WORKORDER_NO LIKE ?)\n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.WORKORDER_OBJECT_LOCATION LIKE ?)\n" +
                /*"   AND (EW.CHECKOVER_BY=? OR EW.CHECKOVER_BY " + SyBaseSQLUtil.isNull() + " )\n" +
                    "   AND (EXISTS (SELECT 'A'\n" +
                    "          FROM SF_USER_RIGHT SUR\n" +
                    "         WHERE SUR.USER_ID = ?\n" +
                    "           AND EW.GROUP_ID = SUR.GROUP_ID)\n)" +*/
                " AND (\n" +
                "        EW.CHECKOVER_BY=?\n" +
                "        OR (EW.CHECKOVER_BY " + SyBaseSQLUtil.isNullNoParam() + " \n" +
                "             AND EXISTS (SELECT 'A'\n" +
                "                    FROM SF_USER_RIGHT SUR,SF_ROLE SR\n" +
                "                   WHERE SUR.USER_ID = ?\n" +
                "                     AND EW.GROUP_ID = SUR.GROUP_ID" +
                "                     AND SUR.ROLE_ID=SR.ROLE_ID\n" +
                "                     AND SR.ROLE_NAME ='工单归档人')\n" +
                "       ))" +
                " ORDER BY EW.UPLOAD_DATE";

        sqlArgs.add(sfUser.getOrganizationId());
        sqlArgs.add(workorderNo);
        sqlArgs.add(workorderNo);
        sqlArgs.add(locName);
        sqlArgs.add(locName);
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(sfUser.getUserId());


        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }

    public SQLModel getDiffModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr =
                //系统中无，扫描结果有
                "SELECT BARCODE, '6' PREV_STATUS, ITEM_STATUS, 'PLACEHOLDER' PLACEHOLDER\n" +
                        "  FROM ETS_WORKORDER_DTL EWI\n" +
                        " WHERE EWI.BARCODE NOT IN\n" +
                        "       (SELECT BARCODE\n" +
                        "          FROM ETS_ITEM_INFO EII, AMS_OBJECT_ADDRESS AOA\n" +
                        "         WHERE EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                        "           AND AOA.OBJECT_NO = '43562')\n" +
                        "   AND EWI.WORKORDER_NO = '20071018001001'\n" +
                        "   AND EWI.ITEM_STATUS IN ('0', '5')";
        sqlStr += " UNION ";
        //系统中有,在扫描结果中未出现
        sqlStr +=
                "SELECT EII.BARCODE, '5' PREV_STATUS, 6, 'PLACEHOLDER' PLACEHOLDER\n" +
                        "  FROM ETS_ITEM_INFO EII, AMS_OBJECT_ADDRESS AOA\n" +
                        " WHERE EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                        "   AND NOT EXISTS (SELECT BARCODE\n" +
                        "          FROM ETS_WORKORDER_INTERFACE EWI\n" +
                        "         WHERE EWI.BARCODE = EII.BARCODE\n" +
                        "           AND EWI.WORKORDER_NO = '20071018001001')\n" +
                        "   AND AOA.OBJECT_NO = '43562'";
        sqlStr += " UNION ";
        //系统中有,在扫描结果中状态为无或在途
        sqlStr += "SELECT EII.BARCODE, '5' PREV_STATUS, 6, 'PLACEHOLDER' PLACEHOLDER\n" +
                "  FROM ETS_ITEM_INFO EII, AMS_OBJECT_ADDRESS AOA\n" +
                " WHERE EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                "   AND EXISTS (SELECT BARCODE\n" +
                "          FROM ETS_WORKORDER_DTL EWI\n" +
                "         WHERE EWI.BARCODE = EII.BARCODE\n" +
                "           AND EWI.WORKORDER_NO = '20071018001001'\n" +
                "           AND EWI.ITEM_STATUS IN ('6', '7'))\n" +
                "   AND AOA.OBJECT_NO = '43562'";


        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }

    public SQLModel getOrderDiffModel(String workorderNo, String objectNo) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "";

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }

    /**
     * 检查工单是否已被提交
     * @param workorderNo
     * @return
     */
    public SQLModel getHasSubmitModel(String workorderNo) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();

        String sqlStr = "SELECT COUNT(1) NCOUNT\n" +
                "  FROM ETS_WORKORDER EW\n" +
                " WHERE EW.WORKORDER_NO = ?\n" +
                "   AND WORKORDER_FLAG=?";
        sqlArgs.add(workorderNo);
        sqlArgs.add(DictConstant.WOR_STATUS_DOWNLOAD);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

		return sqlModel;
	}
    
    /**
     * 下载指定成本中心的设备
     * @param workorderNo    工单号
     * @param itemCategory   扫描专业
     * @param orgnizationId  组织ID
     * @param costCenterCode 成本中心代码
     * @return
     */
    public SQLModel getZeroCostCenterItemsModel(String workorderNo, String itemCategory, int orgnizationId, String costCenterCode) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();

        String sqlStr = "SELECT EII.BARCODE,\n" +
                "       ESI.ITEM_NAME,\n" +
                "       ESI.ITEM_SPEC,\n" +
                "       ESI.ITEM_CATEGORY,\n" +
                "       EII.ITEM_QTY,\n" +
                "       EII.ITEM_CODE,\n" +
                "       EII.VENDOR_BARCODE,\n" +
                "       EII.START_DATE,\n" +
                "       EII.PARENT_BARCODE,\n" +
                "       AOA.ADDRESS_ID,\n" +
                "       AOA.BOX_NO,\n" +
                "       AOA.NET_UNIT,\n" +
                "       EII.ADDRESS_ID,\n" +
                "       EII.RESPONSIBILITY_DEPT ASSIGN_GROUPID,\n" +
                "       EII.RESPONSIBILITY_USER ASSIGN_USERID,\n" +
                "       EII.MAINTAIN_USER USERNAME,\n" +
                "       EW.WORKORDER_NO,\n" +
                "		EII.CONTENT_CODE,\n" +
                "       EII.CONTENT_NAME, \n" +
                "       EII.SHARE_STATUS, \n" +
                "       EII.CONSTRUCT_STATUS, \n" + 
                "       EII.LNE_ID, \n" + 
                "       EII.CEX_ID, \n" +  
                "       EII.OPE_ID, \n" + 
                "       EII.NLE_ID \n" +  
                "  FROM ETS_ITEM_INFO      EII,\n" +
                "       ETS_SYSTEM_ITEM    ESI,\n" +
                "       ETS_WORKORDER      EW,\n" +
                "       AMS_OBJECT_ADDRESS AOA\n" +
                " WHERE EII.ITEM_CODE = ESI.ITEM_CODE\n" +
                "   AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                "   AND  " + SyBaseSQLUtil.isNotNull("EII.ADDRESS_ID") + " \n" +
                "   AND EW.WORKORDER_OBJECT_NO = AOA.OBJECT_NO\n" +
                "   AND EW.WORKORDER_TYPE=?\n" +
                "   AND EW.WORKORDER_NO=?\n" +
                "   AND EII.ORGANIZATION_ID=?\n" +
                "   AND EII.BARCODE LIKE '3910-70%'" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_CATEGORY=?)\n" +
                " AND EXISTS (SELECT 1\n" +
                "         FROM AMS_COST_DEPT_MATCH ACDM\n" +
                "        WHERE ACDM.DEPT_CODE = EII.RESPONSIBILITY_DEPT\n" +
                "          AND ACDM.COST_CENTER_CODE = ?\n)"+
                "   AND EII.BARCODE IN ( SELECT EWI.BARCODE FROM ETS_WORKORDER_ITEM EWI" +
                "                       WHERE EWI.WORKORDER_NO=?)";


//        sqlArgs.add(DictConstant.ORDER_TYPE_ZERO);ORDER_TYPE_HDV
        sqlArgs.add(DictConstant.ORDER_TYPE_HDV);
        sqlArgs.add(workorderNo);
        sqlArgs.add(orgnizationId);
        sqlArgs.add(itemCategory);
        sqlArgs.add(itemCategory);
        sqlArgs.add(costCenterCode);
        sqlArgs.add(workorderNo);


        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }
}
