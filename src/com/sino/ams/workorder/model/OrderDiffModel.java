package com.sino.ams.workorder.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.constant.AmsOrderConstant;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dto.EtsWorkorderDTO;
import com.sino.ams.workorder.dto.EtsWorkorderDtlDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;

/**
 * User: zhoujs
 * Date: 2007-11-8
 * Time: 13:08:59
 * Function:
 */
public class OrderDiffModel {
    private String diffSQL =
//                    "--换下设备差异 status：8\n" +
//                    "--无差异：系统中有，不处理\n" +
//                    "-- 差异：系统中无，\n" +
//                    "--差异处理：插入在途库\n" +
            "SELECT EWD.BARCODE,\n" +
                    "       '" + DictConstant.SCAN_STATUS_NONE + "' ITEM_STATUS,\n" +
                    "       dbo.APP_GET_FLEX_VALUE('" + DictConstant.SCAN_STATUS_NONE + "','" + DictConstant.ORDER_ITEM_STATUS + "') ITEM_STATUS_DESC,\n" +
                    "       EWD.ITEM_STATUS SCAN_STATUS,\n" +
                    "       dbo.APP_GET_FLEX_VALUE(CONVERT(VARCHAR,EWD.ITEM_STATUS),'" + DictConstant.ORDER_ITEM_STATUS + "') SCAN_STATUS_DESC,\n" +
                    "       'PLACEHOLDER' PLACEHOLDER,\n" +
                    "       EWD.BOX_NO,\n" +
                    "       EWD.NET_UNIT,\n" +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       ESI.ITEM_CATEGORY,\n" +
                    "       dbo.APP_GET_FLEX_VALUE(ESI.ITEM_CATEGORY,'" + DictConstant.ITEM_TYPE + "') ITEM_CATEGORY_DESC, \n" +
                    "       EWD.REMARK \n" +
                    " FROM ETS_WORKORDER_DTL EWD, ETS_SYSTEM_ITEM ESI\n" +
                    "WHERE EWD.ITEM_CODE = ESI.ITEM_CODE\n" +
                    " AND NOT EXISTS (SELECT 1 FROM ETS_ITEM_INFO EII WHERE EII.BARCODE=EWD.BARCODE)\n" +
                    "   AND EWD.ITEM_STATUS  = " + DictConstant.SCAN_STATUS_REMAIN + "\n" +
                    "   AND EWD.WORKORDER_NO = ?\n" +
                    "\n" +
                    " UNION\n" +
//                    "--换下设备差异 status：7\n" +
//                    "--无差异：系统当前地点无，不处理 …………\n" +
//                    "--差异：系统当前地点有\n" +
//                    "--差异处理：更改设备到在途库\n" +
                    "SELECT EWD.BARCODE,\n" +
                    "       '" + DictConstant.SCAN_STATUS_EXISTS + "' ITEM_STATUS,\n" +
                    "       dbo.APP_GET_FLEX_VALUE('" + DictConstant.SCAN_STATUS_EXISTS + "','" + DictConstant.ORDER_ITEM_STATUS + "') ITEM_STATUS_DESC,\n" +
                    "       EWD.ITEM_STATUS SCAN_STATUS,\n" +
                    "       dbo.APP_GET_FLEX_VALUE(CONVERT(VARCHAR,EWD.ITEM_STATUS),'" + DictConstant.ORDER_ITEM_STATUS + "') SCAN_STATUS_DESC,\n" +
                    "       'PLACEHOLDER' PLACEHOLDER,\n" +
                    "       EWD.BOX_NO,\n" +
                    "       EWD.NET_UNIT,\n" +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       ESI.ITEM_CATEGORY,\n" +
                    "       dbo.APP_GET_FLEX_VALUE(ESI.ITEM_CATEGORY,'" + DictConstant.ITEM_TYPE + "') ITEM_CATEGORY_DESC, \n" +
                    "       EWD.REMARK \n" +
                    " FROM ETS_WORKORDER_DTL EWD, ETS_SYSTEM_ITEM ESI\n" +
                    "WHERE EWD.ITEM_CODE = ESI.ITEM_CODE\n" +
                    " AND EWD.BARCODE  IN\n" +
                    "       (SELECT BARCODE\n" +
                    "          FROM ETS_ITEM_INFO EII, AMS_OBJECT_ADDRESS AOA\n" +
                    "         WHERE EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                    "           AND AOA.OBJECT_NO = ?)\n" +
                    "   AND EWD.WORKORDER_NO = ?\n" +
                    "   AND EWD.ITEM_STATUS =" + DictConstant.SCAN_STATUS_OFFLINE + "\n" +
                    "\n" +
                    " UNION\n" +
//                    "--未扫描设备 status：6\n" +
//                    "--无差异：系统当前地点无，不处理 …………\n" +
//                    "--差异：系统当前地点有\n" +
//                    "--差异处理：更改设备到在途库\n" +
                    "SELECT EWD.BARCODE,\n" +
                    "       '" + DictConstant.SCAN_STATUS_EXISTS + "' ITEM_STATUS,\n" +
                    "       dbo.APP_GET_FLEX_VALUE('" + DictConstant.SCAN_STATUS_EXISTS + "','" + DictConstant.ORDER_ITEM_STATUS + "') ITEM_STATUS_DESC,\n" +
                    "       EWD.ITEM_STATUS SCAN_STATUS,\n" +
                    "       dbo.APP_GET_FLEX_VALUE(CONVERT(VARCHAR,EWD.ITEM_STATUS),'" + DictConstant.ORDER_ITEM_STATUS + "') SCAN_STATUS_DESC,\n" +
                    "       'PLACEHOLDER' PLACEHOLDER,\n" +
                    "       EWD.BOX_NO,\n" +
                    "       EWD.NET_UNIT,\n" +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       ESI.ITEM_CATEGORY,\n" +
                    "       dbo.APP_GET_FLEX_VALUE(ESI.ITEM_CATEGORY,'" + DictConstant.ITEM_TYPE + "') ITEM_CATEGORY_DESC, \n" +
                    "       EWD.REMARK \n" +
                    " FROM ETS_WORKORDER_DTL EWD, ETS_SYSTEM_ITEM ESI\n" +
                    "WHERE EWD.ITEM_CODE = ESI.ITEM_CODE\n" +
                    " AND EWD.BARCODE  IN\n" +
                    "       (SELECT BARCODE\n" +
                    "          FROM ETS_ITEM_INFO EII, AMS_OBJECT_ADDRESS AOA\n" +
                    "         WHERE EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                    "           AND AOA.OBJECT_NO = ?)\n" +
                    "   AND EWD.WORKORDER_NO = ?\n" +
                    "   AND EWD.ITEM_STATUS = " + DictConstant.SCAN_STATUS_NONE + "\n" +
                    "\n" +
                    " UNION\n" +
//                    "--扫描设备 status：5\n" +
//                    "--无差异：系统当前地点有，不处理 …………\n" +
//                    "--差异：系统当前地点无\n" +
//                    "--差异处理：\n" +
                    "SELECT EWD.BARCODE,\n" +
                    "       '" + DictConstant.SCAN_STATUS_NONE + "' ITEM_STATUS,\n" +
                    "       dbo.APP_GET_FLEX_VALUE('" + DictConstant.SCAN_STATUS_NONE + "','" + DictConstant.ORDER_ITEM_STATUS + "') ITEM_STATUS_DESC,\n" +
                    "       EWD.ITEM_STATUS SCAN_STATUS,\n" +
                    "       dbo.APP_GET_FLEX_VALUE(CONVERT(VARCHAR,EWD.ITEM_STATUS),'" + DictConstant.ORDER_ITEM_STATUS + "') SCAN_STATUS_DESC,\n" +
                    "       'PLACEHOLDER' PLACEHOLDER,\n" +
                    "       EWD.BOX_NO,\n" +
                    "       EWD.NET_UNIT,\n" +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       ESI.ITEM_CATEGORY,\n" +
                    "       dbo.APP_GET_FLEX_VALUE(ESI.ITEM_CATEGORY,'" + DictConstant.ITEM_TYPE + "') ITEM_CATEGORY_DESC, \n" +
                    "       EWD.REMARK \n" +
                    " FROM ETS_WORKORDER_DTL EWD, ETS_SYSTEM_ITEM ESI\n" +
                    "WHERE EWD.ITEM_CODE = ESI.ITEM_CODE\n" +
                    " AND EWD.BARCODE NOT  IN\n" +
                    "       (SELECT BARCODE\n" +
                    "          FROM ETS_ITEM_INFO EII, AMS_OBJECT_ADDRESS AOA\n" +
                    "         WHERE EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                    "           AND AOA.OBJECT_NO = ?)\n" +
                    "   AND EWD.WORKORDER_NO = ?\n" +
                    "   AND EWD.ITEM_STATUS =" + DictConstant.SCAN_STATUS_EXISTS + "\n" +
                    "\n" +
                    " UNION\n" +
//                    "--新增设备 status：0\n" +
//                    "--无差异：系统当前地点有，不处理 …………\n" +
//                    "--差异：系统当前地点无\n" +
//                    "--差异处理：\n" +
                    "SELECT EWD.BARCODE,\n" +
                    "       '" + DictConstant.SCAN_STATUS_NONE + "' ITEM_STATUS,\n" +
                    "       dbo.APP_GET_FLEX_VALUE('" + DictConstant.SCAN_STATUS_NONE + "','" + DictConstant.ORDER_ITEM_STATUS + "') ITEM_STATUS_DESC,\n" +
                    "       EWD.ITEM_STATUS SCAN_STATUS,\n" +
                    "       dbo.APP_GET_FLEX_VALUE(CONVERT(VARCHAR,EWD.ITEM_STATUS),'" + DictConstant.ORDER_ITEM_STATUS + "') SCAN_STATUS_DESC,\n" +
                    "       'PLACEHOLDER' PLACEHOLDER,\n" +
                    "       EWD.BOX_NO,\n" +
                    "       EWD.NET_UNIT,\n" +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       ESI.ITEM_CATEGORY,\n" +
                    "       dbo.APP_GET_FLEX_VALUE(ESI.ITEM_CATEGORY,'" + DictConstant.ITEM_TYPE + "') ITEM_CATEGORY_DESC, \n" +
                    "       EWD.REMARK \n" +
                    " FROM ETS_WORKORDER_DTL EWD, ETS_SYSTEM_ITEM ESI\n" +
                    "WHERE EWD.ITEM_CODE = ESI.ITEM_CODE\n" +
                    " AND EWD.BARCODE NOT IN\n" +
                    "       (SELECT BARCODE\n" +
                    "          FROM ETS_ITEM_INFO EII, AMS_OBJECT_ADDRESS AOA\n" +
                    "         WHERE EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                    "           AND AOA.OBJECT_NO = ?)\n" +
                    "   AND EWD.WORKORDER_NO = ?\n" +
                    "   AND EWD.ITEM_STATUS =" + DictConstant.SCAN_STATUS_NEW + "\n" +
                    "";

    //差异SQL，设备专业为指定的某种时候
    private String diffCateSQL =
//                    "--换下设备差异 status：8\n" +
//                    "--无差异：系统中有，不处理\n" +
//                    "-- 差异：系统中无，\n" +
//                    "--差异处理：插入在途库\n" +
            "SELECT EWD.BARCODE,\n" +
                    "       '" + DictConstant.SCAN_STATUS_NONE + "' ITEM_STATUS,\n" +
                    "       dbo.APP_GET_FLEX_VALUE('" + DictConstant.SCAN_STATUS_NONE + "','" + DictConstant.ORDER_ITEM_STATUS + "') ITEM_STATUS_DESC,\n" +
                    "       EWD.ITEM_STATUS SCAN_STATUS,\n" +
                    "       dbo.APP_GET_FLEX_VALUE(CONVERT(VARCHAR,EWD.ITEM_STATUS),'" + DictConstant.ORDER_ITEM_STATUS + "') SCAN_STATUS_DESC,\n" +
                    "       'PLACEHOLDER' PLACEHOLDER,\n" +
                    "       EWD.BOX_NO,\n" +
                    "       EWD.NET_UNIT,\n" +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       ESI.ITEM_CATEGORY,\n" +
                    "       dbo.APP_GET_FLEX_VALUE(ESI.ITEM_CATEGORY,'" + DictConstant.ITEM_TYPE + "') ITEM_CATEGORY_DESC, \n" +
                    "       EWD.REMARK \n" +
                    " FROM ETS_WORKORDER_DTL EWD, ETS_SYSTEM_ITEM ESI\n" +
                    "WHERE EWD.ITEM_CODE = ESI.ITEM_CODE\n" +
                    " AND NOT EXISTS (SELECT 1 FROM ETS_ITEM_INFO EII WHERE EII.BARCODE=EWD.BARCODE)\n" +
                    "   AND EWD.ITEM_STATUS  = " + DictConstant.SCAN_STATUS_REMAIN + "\n" +
                    "   AND EWD.WORKORDER_NO = ?\n" +
                    "\n" +
                    " UNION\n" +
//                    "--换下设备差异 status：7\n" +
//                    "--无差异：系统当前地点无，不处理 …………\n" +
//                    "--差异：系统当前地点有\n" +
//                    "--差异处理：更改设备到在途库\n" +
                    "SELECT EWD.BARCODE,\n" +
                    "       '" + DictConstant.SCAN_STATUS_EXISTS + "' ITEM_STATUS,\n" +
                    "       dbo.APP_GET_FLEX_VALUE('" + DictConstant.SCAN_STATUS_EXISTS + "','" + DictConstant.ORDER_ITEM_STATUS + "') ITEM_STATUS_DESC,\n" +
                    "       EWD.ITEM_STATUS SCAN_STATUS,\n" +
                    "       dbo.APP_GET_FLEX_VALUE(CONVERT(VARCHAR,EWD.ITEM_STATUS),'" + DictConstant.ORDER_ITEM_STATUS + "') SCAN_STATUS_DESC,\n" +
                    "       'PLACEHOLDER' PLACEHOLDER,\n" +
                    "       EWD.BOX_NO,\n" +
                    "       EWD.NET_UNIT,\n" +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       ESI.ITEM_CATEGORY,\n" +
                    "       dbo.APP_GET_FLEX_VALUE(ESI.ITEM_CATEGORY,'" + DictConstant.ITEM_TYPE + "') ITEM_CATEGORY_DESC, \n" +
                    "       EWD.REMARK \n" +
                    " FROM ETS_WORKORDER_DTL EWD, ETS_SYSTEM_ITEM ESI\n" +
                    "WHERE EWD.ITEM_CODE = ESI.ITEM_CODE\n" +
                    " AND EWD.BARCODE  IN\n" +
                    "       (SELECT BARCODE\n" +
                    "          FROM ETS_ITEM_INFO EII, AMS_OBJECT_ADDRESS AOA\n" +
                    "         WHERE EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                    "           AND AOA.OBJECT_NO = ?)\n" +
                    "   AND EWD.WORKORDER_NO = ?\n" +
                    "   AND EWD.ITEM_STATUS =" + DictConstant.SCAN_STATUS_OFFLINE + "\n" +
                    "\n" +
                    " UNION\n" +
//                    "--未扫描设备 status：6\n" +
//                    "--无差异：系统当前地点无，不处理 …………\n" +
//                    "--差异：系统当前地点有\n" +
//                    "--差异处理：更改设备到在途库\n" +
                    "SELECT EWD.BARCODE,\n" +
                    "       '" + DictConstant.SCAN_STATUS_EXISTS + "' ITEM_STATUS,\n" +
                    "       dbo.APP_GET_FLEX_VALUE('" + DictConstant.SCAN_STATUS_EXISTS + "','" + DictConstant.ORDER_ITEM_STATUS + "') ITEM_STATUS_DESC,\n" +
                    "       EWD.ITEM_STATUS SCAN_STATUS,\n" +
                    "       dbo.APP_GET_FLEX_VALUE(CONVERT(VARCHAR,EWD.ITEM_STATUS),'" + DictConstant.ORDER_ITEM_STATUS + "') SCAN_STATUS_DESC,\n" +
                    "       'PLACEHOLDER' PLACEHOLDER,\n" +
                    "       EWD.BOX_NO,\n" +
                    "       EWD.NET_UNIT,\n" +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       ESI.ITEM_CATEGORY,\n" +
                    "       dbo.APP_GET_FLEX_VALUE(ESI.ITEM_CATEGORY,'" + DictConstant.ITEM_TYPE + "') ITEM_CATEGORY_DESC, \n" +
                    "       EWD.REMARK \n" +
                    " FROM ETS_WORKORDER_DTL EWD, ETS_SYSTEM_ITEM ESI\n" +
                    "WHERE EWD.ITEM_CODE = ESI.ITEM_CODE\n" +
                    " AND EWD.BARCODE  IN\n" +
                    "       (SELECT BARCODE\n" +
                    "          FROM ETS_ITEM_INFO EII, AMS_OBJECT_ADDRESS AOA,ETS_SYSTEM_ITEM ESI1\n" +
                    "         WHERE EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                    "           AND EII.ITEM_CODE = ESI1.ITEM_CODE\n" +
                    "           AND ESI1.ITEM_CATEGORY=? \n" +
                    "           AND AOA.OBJECT_NO = ?)\n" +
                    "   AND EWD.WORKORDER_NO = ?\n" +
                    "   AND EWD.ITEM_STATUS =" + DictConstant.SCAN_STATUS_NONE + "\n" +
                    "\n" +
                    " UNION\n" +
//                    "--扫描设备 status：5\n" +
//                    "--无差异：系统当前地点有，不处理 …………\n" +
//                    "--差异：系统当前地点无\n" +
//                    "--差异处理：\n" +
                    "SELECT EWD.BARCODE,\n" +
                    "       '" + DictConstant.SCAN_STATUS_NONE + "' ITEM_STATUS,\n" +
                    "       dbo.APP_GET_FLEX_VALUE('" + DictConstant.SCAN_STATUS_NONE + "','" + DictConstant.ORDER_ITEM_STATUS + "') ITEM_STATUS_DESC,\n" +
                    "       EWD.ITEM_STATUS SCAN_STATUS,\n" +
                    "       dbo.APP_GET_FLEX_VALUE(CONVERT(VARCHAR,EWD.ITEM_STATUS),'" + DictConstant.ORDER_ITEM_STATUS + "') SCAN_STATUS_DESC,\n" +
                    "       'PLACEHOLDER' PLACEHOLDER,\n" +
                    "       EWD.BOX_NO,\n" +
                    "       EWD.NET_UNIT,\n" +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       ESI.ITEM_CATEGORY,\n" +
                    "       dbo.APP_GET_FLEX_VALUE(ESI.ITEM_CATEGORY,'" + DictConstant.ITEM_TYPE + "') ITEM_CATEGORY_DESC, \n" +
                    "       EWD.REMARK \n" +
                    " FROM ETS_WORKORDER_DTL EWD, ETS_SYSTEM_ITEM ESI\n" +
                    "WHERE EWD.ITEM_CODE = ESI.ITEM_CODE\n" +
                    " AND EWD.BARCODE NOT  IN\n" +
                    "       (SELECT BARCODE\n" +
                    "          FROM ETS_ITEM_INFO EII, AMS_OBJECT_ADDRESS AOA\n" +
                    "         WHERE EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                    "           AND AOA.OBJECT_NO = ?)\n" +
                    "   AND EWD.WORKORDER_NO = ?\n" +
                    "   AND EWD.ITEM_STATUS =" + DictConstant.SCAN_STATUS_EXISTS + "\n" +
                    "\n" +
                    " UNION\n" +
//                    "--新增设备 status：0\n" +
//                    "--无差异：系统当前地点有，不处理 …………\n" +
//                    "--差异：系统当前地点无\n" +
//                    "--差异处理：\n" +
                    "SELECT EWD.BARCODE,\n" +
                    "       '" + DictConstant.SCAN_STATUS_NONE + "' ITEM_STATUS,\n" +
                    "       dbo.APP_GET_FLEX_VALUE('" + DictConstant.SCAN_STATUS_NONE + "','" + DictConstant.ORDER_ITEM_STATUS + "') ITEM_STATUS_DESC,\n" +
                    "       EWD.ITEM_STATUS SCAN_STATUS,\n" +
                    "       dbo.APP_GET_FLEX_VALUE(CONVERT(VARCHAR,EWD.ITEM_STATUS),'" + DictConstant.ORDER_ITEM_STATUS + "') SCAN_STATUS_DESC,\n" +
                    "       'PLACEHOLDER' PLACEHOLDER,\n" +
                    "       EWD.BOX_NO,\n" +
                    "       EWD.NET_UNIT,\n" +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       ESI.ITEM_CATEGORY,\n" +
                    "       dbo.APP_GET_FLEX_VALUE(ESI.ITEM_CATEGORY,'" + DictConstant.ITEM_TYPE + "') ITEM_CATEGORY_DESC, \n" +
                    "       EWD.REMARK \n" +
                    " FROM ETS_WORKORDER_DTL EWD, ETS_SYSTEM_ITEM ESI\n" +
                    "WHERE EWD.ITEM_CODE = ESI.ITEM_CODE\n" +
                    " AND EWD.BARCODE NOT IN\n" +
                    "       (SELECT BARCODE\n" +
                    "          FROM ETS_ITEM_INFO EII, AMS_OBJECT_ADDRESS AOA\n" +
                    "         WHERE EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                    "           AND AOA.OBJECT_NO = ?)\n" +
                    "   AND EWD.WORKORDER_NO = ?\n" +
                    "   AND EWD.ITEM_STATUS =" + DictConstant.SCAN_STATUS_NEW + "\n" +
                    "";


    private String hdvDiffSQL =
            "SELECT EWD.BARCODE,\n" +
                    "       EWD.ITEM_STATUS,\n" +
                    "       dbo.APP_GET_FLEX_VALUE(CONVERT(VARCHAR, EWD.ITEM_STATUS),\n" +
                    "                              'ORDER_ITEM_STATUS') ITEM_STATUS_DESC,\n" +
                    "       EWD.ITEM_STATUS SCAN_STATUS,\n" +
                    "       dbo.APP_GET_FLEX_VALUE(CONVERT(VARCHAR, EWD.ITEM_STATUS),\n" +
                    "                              'ORDER_ITEM_STATUS') SCAN_STATUS_DESC,\n" +
                    "       'PLACEHOLDER' PLACEHOLDER,\n" +
                    "       EWD.BOX_NO,\n" +
                    "       EWD.NET_UNIT,\n" +
                    "       dbo.GET_SYS_ITEM(EWD.ITEM_CODE, 'ITEM_NAME') ITEM_NAME,\n" +
                    "       dbo.GET_SYS_ITEM(EWD.ITEM_CODE, 'ITEM_SPEC') ITEM_SPEC,\n" +
                    "       dbo.GET_SYS_ITEM(EII.ITEM_CODE, 'ITEM_NAME') SYSTEM_ITEM_NAME,\n" +
                    "       dbo.GET_SYS_ITEM(EII.ITEM_CODE, 'ITEM_SPEC') SYSTEM_ITEM_SPEC,\n" +
                    "       '' ITEM_CATEGORY,\n" +
                    "       '' ITEM_CATEGORY_DESC,\n" +
                    "       dbo.GET_AME_USER_NAME(EWD.RESPONSIBILITY_USER) RESPONSIBILITY_USER_NAME,\n" +
                    "       dbo.GET_AME_USER_NAME(EII.RESPONSIBILITY_USER) SYSTEM_RESPONSIBILITY_USER_NAME,\n" +
                    "       EWD.REMARK\n" +
                    "  FROM ETS_WORKORDER_DTL EWD, ETS_ITEM_INFO EII\n" +
                    " WHERE EWD.BARCODE = EII.BARCODE\n" +
                    "   AND EWD.WORKORDER_NO = ?\n" +
                    "\n" +
                    "UNION ALL\n" +
                    "\n" +
                    "SELECT EWD.BARCODE,\n" +
                    "       EWD.ITEM_STATUS,\n" +
                    "       dbo.APP_GET_FLEX_VALUE('6', 'ORDER_ITEM_STATUS') ITEM_STATUS_DESC,\n" +
                    "       EWD.ITEM_STATUS SCAN_STATUS,\n" +
                    "       dbo.APP_GET_FLEX_VALUE(CONVERT(VARCHAR, EWD.ITEM_STATUS),\n" +
                    "                              'ORDER_ITEM_STATUS') SCAN_STATUS_DESC,\n" +
                    "       'PLACEHOLDER' PLACEHOLDER,\n" +
                    "       EWD.BOX_NO,\n" +
                    "       EWD.NET_UNIT,\n" +
                    "       dbo.GET_SYS_ITEM(EWD.ITEM_CODE, 'ITEM_NAME') ITEM_NAME,\n" +
                    "       dbo.GET_SYS_ITEM(EWD.ITEM_CODE, 'ITEM_SPEC') ITEM_SPEC,\n" +
                    "       '' SYSTEM_ITEM_NAME,\n" +
                    "       '' SYSTEM_ITEM_SPEC,\n" +
                    "       '' ITEM_CATEGORY,\n" +
                    "       '' ITEM_CATEGORY_DESC,\n" +
                    "       dbo.GET_AME_USER_NAME(EWD.RESPONSIBILITY_USER) RESPONSIBILITY_USER_NAME,\n" +
                    "       '' SYSTEM_RESPONSIBILITY_USER_NAME,\n" +
                    "       EWD.REMARK\n" +
                    "  FROM ETS_WORKORDER_DTL EWD\n" +
                    " WHERE EWD.WORKORDER_NO = ?\n" +
                    "   AND NOT EXISTS\n" +
                    " (SELECT NULL FROM ETS_ITEM_INFO EII WHERE EII.BARCODE = EWD.BARCODE)";


    /**
     * 获取该工单地点当前扫描结果
     * @param workorderNo 工单号
     * @return SQLModel
     */
    public SQLModel getScanDtlModel(String workorderNo) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT EWD.BARCODE,\n" +
                "       EWD.ITEM_STATUS,\n" +
                "       dbo.APP_GET_FLEX_VALUE(CONVERT(VARCHAR,EWD.ITEM_STATUS), '" + DictConstant.ORDER_ITEM_STATUS + "') ITEM_STATUS_NAME,\n" +
                "       EWD.ITEM_CODE,\n" +
                "       ESI.ITEM_NAME,\n" +
                "       ESI.ITEM_SPEC,\n" +
                "       ESI.ITEM_CATEGORY,\n" +
                "       EWD.ITEM_QTY,\n" +
                "       dbo.APP_GET_FLEX_VALUE(ESI.ITEM_CATEGORY, '" + DictConstant.ITEM_TYPE + "') ITEM_CATEGORY_DESC,\n" +
                "       dbo.APP_GET_VENDOR_NAME(ESI.VENDOR_ID) VENDOR_NAME,\n" +
                "       EWD.BOX_NO,\n" +
                "       '' BOX_NAME,\n" +
                "       EWD.NET_UNIT,\n" +
                "       '' NET_UNIT_NAME,\n" +
                "       EWD.RESPONSIBILITY_DEPT,\n" +
                "       EWD.RESPONSIBILITY_USER,\n" +
                "       AMD.DEPT_NAME,\n" +
                "       AMP.USER_NAME,\n" +
                "       EWD.MAINTAIN_USER\n" +
                "  FROM ETS_WORKORDER_DTL EWD,\n" +
                "       ETS_SYSTEM_ITEM   ESI,\n" +
                "       AMS_MIS_DEPT      AMD,\n" +
                "       AMS_MIS_EMPLOYEE  AMP\n" +
                " WHERE EWD.ITEM_CODE = ESI.ITEM_CODE\n" +
                "   AND EWD.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE\n" +
                "   AND EWD.RESPONSIBILITY_USER *= AMP.EMPLOYEE_ID\n" +
                "   AND EWD.WORKORDER_NO = ?";
        sqlArgs.add(workorderNo);


        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }

    /**
     * 取该地点上次扫描的详细清单。
     * @param workorderNo 工单号
     * @return SQLModel
     */
    public SQLModel getPreScanDtlModel(String workorderNo) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT EWD.BARCODE,\n" +
                "       EWD.ITEM_STATUS,\n" +
                "       dbo.APP_GET_FLEX_VALUE(CONVERT(VARCHAR,EWD.ITEM_STATUS), '" + DictConstant.ORDER_ITEM_STATUS + "') ITEM_STATUS_NAME,\n" +
                "       EWD.ITEM_CODE,\n" +
                "       ESI.ITEM_NAME,\n" +
                "       ESI.ITEM_SPEC,\n" +
                "       ESI.ITEM_CATEGORY,\n" +
                "       EWD.ITEM_QTY,\n" +
                "       dbo.APP_GET_FLEX_VALUE(ESI.ITEM_CATEGORY, '" + DictConstant.ITEM_TYPE + "') ITEM_CATEGORY_DESC,\n" +
                "       dbo.APP_GET_VENDOR_NAME(ESI.VENDOR_ID) VENDOR_NAME,\n" +
                "       EWD.BOX_NO,\n" +
                "       '' BOX_NAME,\n" +
                "       EWD.NET_UNIT,\n" +
                "       '' NET_UNIT_NAME," +
                "       AMD.DEPT_NAME,\n" +
                "       AME.USER_NAME,\n" +
                "       EWD.MAINTAIN_USER\n" +
                "  FROM ETS_WORKORDER_DTL EWD,\n" +
                "       ETS_SYSTEM_ITEM   ESI,\n" +
                "       AMS_MIS_DEPT      AMD,\n" +
                "       AMS_MIS_EMPLOYEE  AME\n" +
                " WHERE EWD.ITEM_CODE = ESI.ITEM_CODE\n" +
                "   AND EWD.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID\n" +
                "   AND EWD.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE\n" +
                "   AND EWD.WORKORDER_NO = ?";
        sqlArgs.add(workorderNo);


        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }

    public SQLModel prepareDiffDtlMOdel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "";

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }

    /**
     * 取工单地点差异
     * @param workorderObjectNo
     * @param workorderNo
     * @param scanCategory
     * @return
     */
    public SQLModel getOrderDiffModel(String workorderObjectNo, String workorderNo, String scanCategory) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();

        String sqlStr = null;
        if (scanCategory.equals("ALL") || scanCategory.equals(AmsOrderConstant.scanAllItemCategory)) {
            sqlStr = diffSQL;

            sqlArgs.add(workorderNo);
            sqlArgs.add(workorderObjectNo);
            sqlArgs.add(workorderNo);
            sqlArgs.add(workorderObjectNo);
            sqlArgs.add(workorderNo);
            sqlArgs.add(workorderObjectNo);
            sqlArgs.add(workorderNo);
            sqlArgs.add(workorderObjectNo);
            sqlArgs.add(workorderNo);
        } else {
            sqlStr = diffCateSQL;

            sqlArgs.add(workorderNo);
            sqlArgs.add(workorderObjectNo);
            sqlArgs.add(workorderNo);
            sqlArgs.add(scanCategory);
            sqlArgs.add(workorderObjectNo);
            sqlArgs.add(workorderNo);
            sqlArgs.add(workorderObjectNo);
            sqlArgs.add(workorderNo);
            sqlArgs.add(workorderObjectNo);
            sqlArgs.add(workorderNo);
        }


        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }

    /**
     * 取工单首次扫描差异（）
     * @param workorderObjectNo
     * @param workorderNo
     * @return
     */

    public SQLModel getOrderDtlModel(String workorderObjectNo, String workorderNo) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT EWD.BARCODE,\n" +
                "       '6' ITEM_STATUS,\n" +
                "       dbo.APP_GET_FLEX_VALUE('6', 'ORDER_ITEM_STATUS') ITEM_STATUS_DESC,\n" +
                "       EWD.ITEM_STATUS SCAN_STATUS,\n" +
                "       dbo.APP_GET_FLEX_VALUE(EWD.ITEM_STATUS, 'ORDER_ITEM_STATUS') SCAN_STATUS_DESC,\n" +
                "       'PLACEHOLDER' PLACEHOLDER,\n" +
                "       EWD.BOX_NO,\n" +
                "       EWD.NET_UNIT,\n" +
                "       ESI.ITEM_NAME,\n" +
                "       ESI.ITEM_SPEC,\n" +
                "       ESI.ITEM_CATEGORY,\n" +
                "       dbo.APP_GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY_DESC\n" +
                "  FROM ETS_WORKORDER_DTL EWD, ETS_SYSTEM_ITEM ESI\n" +
                " WHERE EWD.ITEM_CODE = ESI.ITEM_CODE\n" +
                "   AND EWD.WORKORDER_NO = ?";

        sqlArgs.add(workorderNo);


        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }


    /**
     * 取该工单地点上次巡检未归档工单
     * 条件：工单已经归档、是巡检工单--------------modified by zhoujs 2008-02-25
     * @param etsWorkorderDTO
     * @return
     */
    public SQLModel getPreCheckOrder(EtsWorkorderDTO etsWorkorderDTO) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT WORKORDER_NO \n" +
                "  FROM ETS_WORKORDER\n" +
                " WHERE WORKORDER_TYPE = ? \n" +
                "   AND WORKORDER_FLAG < ? \n" +
                "   AND WORKORDER_OBJECT_NO = ?\n" +
                "   AND UPLOAD_DATE <\n" +
                "       (SELECT UPLOAD_DATE FROM ETS_WORKORDER WHERE WORKORDER_NO = ?)\n" +
                "   AND ATTRIBUTE7 = ?\n" +
                " ORDER BY UPLOAD_DATE";

        if (etsWorkorderDTO.getWorkorderType().equals(DictConstant.ORDER_TYPE_CHECK)) {
        	sqlArgs.add(DictConstant.ORDER_TYPE_CHECK);
        } else {
        	sqlArgs.add(DictConstant.ORDER_TYPE_HDV);
        }        
        sqlArgs.add(DictConstant.WOR_STATUS_ARCHIVED);
        sqlArgs.add(etsWorkorderDTO.getWorkorderObjectNo());
        sqlArgs.add(etsWorkorderDTO.getWorkorderNo());
        sqlArgs.add(etsWorkorderDTO.getAttribute7());


        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }

    /**
     * 取上次巡检的已经归档的工单
     * @param etsWorkorderDTO
     * @return
     */
    public SQLModel getPreScanedOrder(EtsWorkorderDTO etsWorkorderDTO) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT WORKORDER_NO \n" +
                "  FROM ETS_WORKORDER\n" +
                " WHERE WORKORDER_TYPE = ?\n" +
                "   AND WORKORDER_FLAG = '" + DictConstant.WOR_STATUS_ARCHIVED + "'\n" +
                "   AND WORKORDER_OBJECT_NO = ?\n" +
                "   AND UPLOAD_DATE <\n" +
                "       (SELECT UPLOAD_DATE FROM ETS_WORKORDER WHERE WORKORDER_NO = ?)\n" +
                "   AND dbo.NVL(ATTRIBUTE7,'ALL') = dbo.NVL(?,'ALL') \n" +
                " ORDER BY UPLOAD_DATE DESC";
        if (etsWorkorderDTO.getWorkorderType().equals(DictConstant.ORDER_TYPE_CHECK)) {
        	sqlArgs.add(DictConstant.ORDER_TYPE_CHECK);
        } else {
        	sqlArgs.add(DictConstant.ORDER_TYPE_HDV);
        }          
        sqlArgs.add(etsWorkorderDTO.getWorkorderObjectNo());
        sqlArgs.add(etsWorkorderDTO.getWorkorderNo());
        sqlArgs.add(etsWorkorderDTO.getAttribute7());


        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }

    public SQLModel getScanDiffDtlModel(String workorderNo) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT EW.WORKORDER_NO,\n" +
                "       EW.WORKORDER_OBJECT_NO,\n" +
                "       EW.WORKORDER_TYPE,\n" +
                "       EWD.BARCODE,\n" +
                "       EWD.ITEM_STATUS,\n" +
                "       EWD.ITEM_QTY,\n" +
                "       EWD.REMARK,\n" +
                "       EWD.ITEM_CODE,\n" +
                "       EW.PRJ_ID PRJECT_ID,\n" +
                "       EWD.BOX_NO,\n" +
                "       EWD.NET_UNIT,\n" +
                "       EWD.ITEM_REMARK,\n" +
                "       EWD.RESPONSIBILITY_DEPT,\n" +
                "       EWD.RESPONSIBILITY_USER,\n" +
                "       EWD.MAINTAIN_USER,\n" +
                "       EWD.CREATION_DATE,\n" +
                "       EWD.CREATED_BY\n" +
                "  FROM ETS_WORKORDER_DTL EWD, ETS_WORKORDER EW\n" +
                " WHERE EWD.WORKORDER_NO = EW.WORKORDER_NO\n" +
                "   AND EW.WORKORDER_NO = ?";
        sqlArgs.add(workorderNo);


        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }

    /**
     * 更新设备地点
     * @param workorderDtlDTO
     * @param addressId
     * @return
     */

    public SQLModel getUpdateItemAddrModel(EtsWorkorderDtlDTO workorderDtlDTO, String addressId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE ETS_ITEM_INFO \n" +
                "   SET ADDRESS_ID        = ?,\n" +
                "       PARENT_BARCODE  = ?,\n" +
                "       RESPONSIBILITY_DEPT  = ?,\n" +
                "       RESPONSIBILITY_USER  = ?,\n" +
                "       MAINTAIN_USER  = ?,\n" +
                "       ITEM_CODE  = ?,\n" ;//+
                //"       SPECIALITY_DEPT  = ?,\n" ;
        		if (workorderDtlDTO.getWorkorderType().equals(DictConstant.ORDER_TYPE_HDV)) {
        			sqlStr += " ITEM_STATUS = ?,\n" ;
        		}
        		sqlStr += 
                "       LAST_UPDATE_DATE  = GETDATE(),\n" +
                "       LAST_LOC_CHG_DATE = GETDATE() \n" +
                " WHERE BARCODE = ?";
        if (workorderDtlDTO.getWorkorderType().equals(DictConstant.ORDER_TYPE_HDV)) {
            sqlStr += " AND FINANCE_PROP=? AND ITEM_STATUS=? ";
        }
        sqlArgs.add(addressId);
        sqlArgs.add(workorderDtlDTO.getParentBarcode());
        sqlArgs.add(workorderDtlDTO.getResponsibilityDept());
        sqlArgs.add(workorderDtlDTO.getResponsibilityUser());
        sqlArgs.add(workorderDtlDTO.getMaintainUser());
        sqlArgs.add(workorderDtlDTO.getItemCode());
        //sqlArgs.add(""/*workorderDtlDTO.getSpecialityDept()SPECIALITY_DEPT*/);
        if (workorderDtlDTO.getWorkorderType().equals(DictConstant.ORDER_TYPE_HDV)) {
            sqlArgs.add(DictConstant.ITEM_STATUS_TO_ASSETS);
        }
        sqlArgs.add(workorderDtlDTO.getBarcode());

        if (workorderDtlDTO.getWorkorderType().equals(DictConstant.ORDER_TYPE_HDV)) {
            sqlArgs.add(DictConstant.FIN_PROP_PRJ);
            sqlArgs.add(DictConstant.ITEM_STATUS_PRE_ASSETS);
        }

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }

    /**
     * 新设备插入在途库
     * @param workorderDtlDTO
     * @param addressId
     * @param sfUserDTO
     * @return
     * @throws CalendarException
     */
    public SQLModel getInsertItemInZTModel(EtsWorkorderDtlDTO workorderDtlDTO, String addressId, SfUserDTO sfUserDTO) throws CalendarException {
        return getInsertItemModel(workorderDtlDTO, addressId, sfUserDTO);
    }

    /**
     * 系统增加新设备
     * @param workorderDtlDTO
     * @param addressId
     * @param sfUserDTO
     * @return
     * @throws CalendarException
     */
    public SQLModel getInsertItemModel(EtsWorkorderDtlDTO workorderDtlDTO, String addressId, SfUserDTO sfUserDTO) throws CalendarException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO ETS_ITEM_INFO\n" +
                "  (SYSTEMID,\n" +
                "   BARCODE,  \n" +
                "   ITEM_QTY,\n" +
                "   REMARK,\n" +
                "   START_DATE,\n" +
                "   PROJECTID,\n" +
                "   CREATION_DATE,\n" +
                "   CREATED_BY,\n" +
                "   ADDRESS_ID,\n" +
                "   ORGANIZATION_ID," +
                "   PARENT_BARCODE," +
                "   ITEM_CODE," +
                "   RESPONSIBILITY_DEPT," +
                "   RESPONSIBILITY_USER," +
                "   MAINTAIN_USER," +
                "   FINANCE_PROP," +
                "   ITEM_STATUS" +
                ")\n" +
                "VALUES\n" +
                "  ( NEWID(),\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   GETDATE(),\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?" +
                ")";
        sqlArgs.add(workorderDtlDTO.getBarcode());
        sqlArgs.add(workorderDtlDTO.getItemQty());
        sqlArgs.add("工单" + workorderDtlDTO.getWorkorderNo() + "创建.");
        sqlArgs.add(workorderDtlDTO.getUploadDate());
        sqlArgs.add(workorderDtlDTO.getPrjectId());
        sqlArgs.add(sfUserDTO.getUserId());
        sqlArgs.add(addressId);
        sqlArgs.add(sfUserDTO.getOrganizationId());
        sqlArgs.add(workorderDtlDTO.getParentBarcode());
        sqlArgs.add(workorderDtlDTO.getItemCode());
        sqlArgs.add(workorderDtlDTO.getResponsibilityDept());
        sqlArgs.add(workorderDtlDTO.getResponsibilityUser());
        sqlArgs.add(workorderDtlDTO.getMaintainUser());

        if (workorderDtlDTO.getWorkorderType().equals(DictConstant.ORDER_TYPE_HDV)) {
            sqlArgs.add(DictConstant.FIN_PROP_PRJ);
            sqlArgs.add(DictConstant.ITEM_STATUS_TO_ASSETS);
        } else {
            sqlArgs.add(DictConstant.FIN_PROP_UNKNOW);
            sqlArgs.add(DictConstant.ITEM_STATUS_NORMAL);
        }

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }

    /**
     * 将差异结果写入差异表
     * @param workorderNo
     * @param itemStatus
     * @param dealResult
     * @return
     */
    public SQLModel getUpdateDiffModel(String workorderNo, String barcode, String itemStatus, String dealResult,/*String specialityDept,*/ String diffReason, String remark) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE ETS_WORKORDER_DIFF_DTL SET\n" +
                "  ITEM_NEW_STATUS=CONVERT(INT,?),\n" +
                "  VERIFY_RESULT=?,\n" +
                //"  SPECIALITY_DEPT=?,\n" +
                "  DIFFERENCE_REASON=?,\n" +
                "  REMARK=?\n" +
                "   WHERE BARCODE=?\n" +
                "       AND WORKORDER_NO=?";

        sqlArgs.add(itemStatus);
        sqlArgs.add(dealResult);
        //sqlArgs.add(specialityDept);
        sqlArgs.add(diffReason);
        sqlArgs.add(remark);
        sqlArgs.add(barcode);
        sqlArgs.add(workorderNo);

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }

    /**
     * 初始化差异表
     * @param workorderNo
     * @return
     */
    public SQLModel getInsertDiffModel(String workorderNo) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO ETS_WORKORDER_DIFF_DTL " +
                "SELECT " +
                "   WORKORDER_NO,BARCODE,SYSTEM_STATUS,ITEM_STATUS,0,ITEM_CODE,ITEM_QTY,'',''," +
                "   PARENT_BARCODE,ADDRESS_ID,BOX_NO,NET_UNIT,REMARK,SYSTEM_ITEM_CODE,RESPONSIBILITY_DEPT," +
                "   SYSTEM_RESPONSIBILITY_DEPT,RESPONSIBILITY_USER,SYSTEM_RESPONSIBILITY_USER" +
                " FROM ETS_WORKORDER_DTL WHERE WORKORDER_NO=?";
        sqlArgs.add(workorderNo);

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }



    /**
     * 取地点ID
     * @param objectNo
     * @return
     */
    public SQLModel getAddrId(String objectNo) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT AOA.ADDRESS_ID\n" +
                "  FROM AMS_OBJECT_ADDRESS AOA\n" +
                " WHERE AOA.OBJECT_NO = ?";
        sqlArgs.add(objectNo);
//        sqlArgs.add(boxNo);
//        sqlArgs.add(netUnit);

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }

    /**
     * 搬迁
     * @return
     */
    public SQLModel getUpdateTransModel(String transAddress, String objectNo) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE ETS_ITEM_INFO\n" +
                "   SET ADDRESS_ID = ?\n" +
                " WHERE EXISTS (SELECT 1\n" +
                "          FROM AMS_OBJECT_ADDRESS AOA,ETS_ITEM_INFO EII\n" +
                "         WHERE AOA.ADDRESS_ID = EII.ADDRESS_ID\n" +
                "           AND AOA.OBJECT_NO = ?)";
        sqlArgs.add(transAddress);
        sqlArgs.add(objectNo);

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }

    public SQLModel isFirstCheckModel(String objectNo) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT *\n" +
                "  FROM ETS_WORKORDER EW\n" +
                " WHERE EW.WORKORDER_TYPE = ?\n" +
                "   AND EW.WORKORDER_OBJECT_NO = ?";

        sqlArgs.add(DictConstant.ORDER_TYPE_CHECK);
        sqlArgs.add(objectNo);

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }

    /**
     * 取交接工单差异Model
     * @return
     */
    public SQLModel getHDVOrderModel(String workorderNo) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = this.hdvDiffSQL;
        sqlArgs.add(workorderNo);
        sqlArgs.add(workorderNo);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }
    
//    public SQLModel getDelzeroTurnL(String workorderNo) {
//        SQLModel sqlModel = new SQLModel();
//        List sqlArgs = new ArrayList();
////        String sqlStr = " DELETE FROM  ZERO_TURN_ADD_L  WHERE RECORD IN ( SELECT BARCODE FROM ETS_WORKORDER_ITEM WHERE WORKORDER_NO=?)";
//        String sqlStr = " UPDATE ZERO_TURN_ADD_L " +
//        		        " SET TRANS_STATUS=?, \n" +
//        		        " TRANS_STATUS_VAL=? \n"+
//        		        " WHERE RECORD IN ( SELECT BARCODE FROM ETS_WORKORDER_ITEM WHERE WORKORDER_NO=?)";
//
//        sqlArgs.add("ORDER_ARICHE");
//        sqlArgs.add("PDA已归档");
//        sqlArgs.add(workorderNo);
//        sqlModel.setArgs(sqlArgs);
//        sqlModel.setSqlStr(sqlStr);
//
//        return sqlModel;
//    }
    public SQLModel updateEII(String workorderNo) {
    	SQLModel sqlModel = new SQLModel();
    	List sqlArgs = new ArrayList();
    	String sqlStr = " UPDATE ETS_ITEM_INFO SET START_DATE=GETDATE()\n" +
    			        " WHERE BARCODE IN( \n" +
    			        " SELECT BARCODE FROM ETS_WORKORDER_ITEM WHERE WORKORDER_NO=?)\n";
    	sqlArgs.add(workorderNo);
    	sqlModel.setArgs(sqlArgs);
    	sqlModel.setSqlStr(sqlStr);
    	
    	return sqlModel;
    }
    public SQLModel updateEtsItemTurn(String workorderNo) {
    	SQLModel sqlModel = new SQLModel();
    	List sqlArgs = new ArrayList();
    	String sqlStr = " UPDATE ETS_ITEM_TURN SET START_DATE=GETDATE()\n" +
        " WHERE BARCODE IN( \n" +
        " SELECT BARCODE FROM ETS_WORKORDER_ITEM WHERE WORKORDER_NO=?)\n";
    	sqlArgs.add(workorderNo);
    	sqlModel.setArgs(sqlArgs);
    	sqlModel.setSqlStr(sqlStr);
    	
    	return sqlModel;
    }

}
