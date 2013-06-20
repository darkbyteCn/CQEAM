package com.sino.ams.workorder.util;

import com.sino.ams.constant.DictConstant;

/**
 * User: zhoujs
 * Date: 2008-7-31
 * Time: 20:40:45
 * Function: 工单差异SQL
 */
public interface OrderDiffSQL {
    public final static String verifySql =
            "SELECT BARCODE,\n" +
                    "       '5' PREV_STATUS,\n" +
                    "       ITEM_STATUS,\n" +
                    "       'PLACEHOLDER' PLACEHOLDER,\n" +
                    "       '' BOX_NO,\n" +
                    "       '' NET_UNIT\n" +
                    "  FROM ETS_WORKORDER_INTERFACE EWI\n" +
                    " WHERE EWI.ITEM_STATUS IN ('6', '7')\n" +
                    "   AND EWI.BARCODE IN\n" +
                    "       (SELECT BARCODE\n" +
                    "          FROM ETS_ITEM_INFO EII, AMS_OBJECT_ADDRESS AOA\n" +
                    "         WHERE EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                    "           AND AOA.OBJECT_NO = '{BASEID}')\n" +
                    "   AND EWI.WORKORDER_NO = '{ORDERID}'";


    public String diffSQL =
//                    "--换下设备差异 status：8\n" +
//                    "--无差异：系统中有，不处理\n" +
//                    "-- 差异：系统中无，\n" +
//                    "--差异处理：插入在途库\n" +
            "SELECT EWD.BARCODE,\n" +
                    "       '" + DictConstant.SCAN_STATUS_NONE + "' ITEM_STATUS,\n" +
                    "       AMS_PUB_PKG.GET_FLEX_VALUE('" + DictConstant.SCAN_STATUS_NONE + "','" + DictConstant.ORDER_ITEM_STATUS + "') ITEM_STATUS_DESC,\n" +
                    "       EWD.ITEM_STATUS SCAN_STATUS,\n" +
                    "       AMS_PUB_PKG.GET_FLEX_VALUE(EWD.ITEM_STATUS,'" + DictConstant.ORDER_ITEM_STATUS + "') SCAN_STATUS_DESC,\n" +
                    "       'PLACEHOLDER' PLACEHOLDER,\n" +
                    "       EWD.BOX_NO,\n" +
                    "       EWD.NET_UNIT,\n" +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       ESI.ITEM_CATEGORY,\n" +
                    "       AMS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_CATEGORY,'" + DictConstant.ITEM_TYPE + "') ITEM_CATEGORY_DESC, \n" +
                    "       EWD.REMARK \n" +
                    " FROM ETS_WORKORDER_DTL EWD, ETS_SYSTEM_ITEM ESI\n" +
                    "WHERE EWD.ITEM_CODE = ESI.ITEM_CODE\n" +
                    " AND NOT EXISTS (SELECT 1 FROM ETS_ITEM_INFO EII WHERE EII.BARCODE=EWD.BARCODE)\n" +
                    "   AND EWD.ITEM_STATUS  = '" + DictConstant.SCAN_STATUS_REMAIN + "'\n" +
                    "   AND EWD.WORKORDER_NO = ?\n" +
                    "\n" +
                    " UNION\n" +
//                    "--换下设备差异 status：7\n" +
//                    "--无差异：系统当前地点无，不处理 …………\n" +
//                    "--差异：系统当前地点有\n" +
//                    "--差异处理：更改设备到在途库\n" +
                    "SELECT EWD.BARCODE,\n" +
                    "       '" + DictConstant.SCAN_STATUS_EXISTS + "' ITEM_STATUS,\n" +
                    "       AMS_PUB_PKG.GET_FLEX_VALUE('" + DictConstant.SCAN_STATUS_EXISTS + "','" + DictConstant.ORDER_ITEM_STATUS + "') ITEM_STATUS_DESC,\n" +
                    "       EWD.ITEM_STATUS SCAN_STATUS,\n" +
                    "       AMS_PUB_PKG.GET_FLEX_VALUE(EWD.ITEM_STATUS,'" + DictConstant.ORDER_ITEM_STATUS + "') SCAN_STATUS_DESC,\n" +
                    "       'PLACEHOLDER' PLACEHOLDER,\n" +
                    "       EWD.BOX_NO,\n" +
                    "       EWD.NET_UNIT,\n" +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       ESI.ITEM_CATEGORY,\n" +
                    "       AMS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_CATEGORY,'" + DictConstant.ITEM_TYPE + "') ITEM_CATEGORY_DESC, \n" +
                    "       EWD.REMARK \n" +
                    " FROM ETS_WORKORDER_DTL EWD, ETS_SYSTEM_ITEM ESI\n" +
                    "WHERE EWD.ITEM_CODE = ESI.ITEM_CODE\n" +
                    " AND EWD.BARCODE  IN\n" +
                    "       (SELECT BARCODE\n" +
                    "          FROM ETS_ITEM_INFO EII, AMS_OBJECT_ADDRESS AOA\n" +
                    "         WHERE EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                    "           AND AOA.OBJECT_NO = ?)\n" +
                    "   AND EWD.WORKORDER_NO = ?\n" +
                    "   AND EWD.ITEM_STATUS ='" + DictConstant.SCAN_STATUS_OFFLINE + "'\n" +
                    "\n" +
                    " UNION\n" +
//                    "--未扫描设备 status：6\n" +
//                    "--无差异：系统当前地点无，不处理 …………\n" +
//                    "--差异：系统当前地点有\n" +
//                    "--差异处理：更改设备到在途库\n" +
                    "SELECT EWD.BARCODE,\n" +
                    "       '" + DictConstant.SCAN_STATUS_EXISTS + "' ITEM_STATUS,\n" +
                    "       AMS_PUB_PKG.GET_FLEX_VALUE('" + DictConstant.SCAN_STATUS_EXISTS + "','" + DictConstant.ORDER_ITEM_STATUS + "') ITEM_STATUS_DESC,\n" +
                    "       EWD.ITEM_STATUS SCAN_STATUS,\n" +
                    "       AMS_PUB_PKG.GET_FLEX_VALUE(EWD.ITEM_STATUS,'" + DictConstant.ORDER_ITEM_STATUS + "') SCAN_STATUS_DESC,\n" +
                    "       'PLACEHOLDER' PLACEHOLDER,\n" +
                    "       EWD.BOX_NO,\n" +
                    "       EWD.NET_UNIT,\n" +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       ESI.ITEM_CATEGORY,\n" +
                    "       AMS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_CATEGORY,'" + DictConstant.ITEM_TYPE + "') ITEM_CATEGORY_DESC, \n" +
                    "       EWD.REMARK \n" +
                    " FROM ETS_WORKORDER_DTL EWD, ETS_SYSTEM_ITEM ESI\n" +
                    "WHERE EWD.ITEM_CODE = ESI.ITEM_CODE\n" +
                    " AND EWD.BARCODE  IN\n" +
                    "       (SELECT BARCODE\n" +
                    "          FROM ETS_ITEM_INFO EII, AMS_OBJECT_ADDRESS AOA\n" +
                    "         WHERE EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                    "           AND AOA.OBJECT_NO = ?)\n" +
                    "   AND EWD.WORKORDER_NO = ?\n" +
                    "   AND EWD.ITEM_STATUS ='" + DictConstant.SCAN_STATUS_NONE + "'\n" +
                    "\n" +
                    " UNION\n" +
//                    "--扫描设备 status：5\n" +
//                    "--无差异：系统当前地点有，不处理 …………\n" +
//                    "--差异：系统当前地点无\n" +
//                    "--差异处理：\n" +
                    "SELECT EWD.BARCODE,\n" +
                    "       '" + DictConstant.SCAN_STATUS_NONE + "' ITEM_STATUS,\n" +
                    "       AMS_PUB_PKG.GET_FLEX_VALUE('" + DictConstant.SCAN_STATUS_NONE + "','" + DictConstant.ORDER_ITEM_STATUS + "') ITEM_STATUS_DESC,\n" +
                    "       EWD.ITEM_STATUS SCAN_STATUS,\n" +
                    "       AMS_PUB_PKG.GET_FLEX_VALUE(EWD.ITEM_STATUS,'" + DictConstant.ORDER_ITEM_STATUS + "') SCAN_STATUS_DESC,\n" +
                    "       'PLACEHOLDER' PLACEHOLDER,\n" +
                    "       EWD.BOX_NO,\n" +
                    "       EWD.NET_UNIT,\n" +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       ESI.ITEM_CATEGORY,\n" +
                    "       AMS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_CATEGORY,'" + DictConstant.ITEM_TYPE + "') ITEM_CATEGORY_DESC, \n" +
                    "       EWD.REMARK \n" +
                    " FROM ETS_WORKORDER_DTL EWD, ETS_SYSTEM_ITEM ESI\n" +
                    "WHERE EWD.ITEM_CODE = ESI.ITEM_CODE\n" +
                    " AND EWD.BARCODE NOT  IN\n" +
                    "       (SELECT BARCODE\n" +
                    "          FROM ETS_ITEM_INFO EII, AMS_OBJECT_ADDRESS AOA\n" +
                    "         WHERE EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                    "           AND AOA.OBJECT_NO = ?)\n" +
                    "   AND EWD.WORKORDER_NO = ?\n" +
                    "   AND EWD.ITEM_STATUS ='" + DictConstant.SCAN_STATUS_EXISTS + "'\n" +
                    "\n" +
                    " UNION\n" +
//                    "--新增设备 status：0\n" +
//                    "--无差异：系统当前地点有，不处理 …………\n" +
//                    "--差异：系统当前地点无\n" +
//                    "--差异处理：\n" +
                    "SELECT EWD.BARCODE,\n" +
                    "       '" + DictConstant.SCAN_STATUS_NONE + "' ITEM_STATUS,\n" +
                    "       AMS_PUB_PKG.GET_FLEX_VALUE('" + DictConstant.SCAN_STATUS_NONE + "','" + DictConstant.ORDER_ITEM_STATUS + "') ITEM_STATUS_DESC,\n" +
                    "       EWD.ITEM_STATUS SCAN_STATUS,\n" +
                    "       AMS_PUB_PKG.GET_FLEX_VALUE(EWD.ITEM_STATUS,'" + DictConstant.ORDER_ITEM_STATUS + "') SCAN_STATUS_DESC,\n" +
                    "       'PLACEHOLDER' PLACEHOLDER,\n" +
                    "       EWD.BOX_NO,\n" +
                    "       EWD.NET_UNIT,\n" +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       ESI.ITEM_CATEGORY,\n" +
                    "       AMS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_CATEGORY,'" + DictConstant.ITEM_TYPE + "') ITEM_CATEGORY_DESC, \n" +
                    "       EWD.REMARK \n" +
                    " FROM ETS_WORKORDER_DTL EWD, ETS_SYSTEM_ITEM ESI\n" +
                    "WHERE EWD.ITEM_CODE = ESI.ITEM_CODE\n" +
                    " AND EWD.BARCODE NOT IN\n" +
                    "       (SELECT BARCODE\n" +
                    "          FROM ETS_ITEM_INFO EII, AMS_OBJECT_ADDRESS AOA\n" +
                    "         WHERE EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                    "           AND AOA.OBJECT_NO = ?)\n" +
                    "   AND EWD.WORKORDER_NO = ?\n" +
                    "   AND EWD.ITEM_STATUS ='" + DictConstant.SCAN_STATUS_NEW + "'\n" +
                    "";

    //差异SQL，设备专业为指定的某种时候
    public String diffCateSQL =
////                    "--换下设备差异 status：8\n" +
////                    "--无差异：系统中有，不处理\n" +
////                    "-- 差异：系统中无，\n" +
////                    "--差异处理：插入在途库\n" +
//            "SELECT EWD.BARCODE,\n" +
//                    "       '" + DictConstant.SCAN_STATUS_NONE + "' ITEM_STATUS,\n" +
//                    "       AMS_PUB_PKG.GET_FLEX_VALUE('" + DictConstant.SCAN_STATUS_NONE + "','" + DictConstant.ORDER_ITEM_STATUS + "') ITEM_STATUS_DESC,\n" +
//                    "       EWD.ITEM_STATUS SCAN_STATUS,\n" +
//                    "       AMS_PUB_PKG.GET_FLEX_VALUE(EWD.ITEM_STATUS,'" + DictConstant.ORDER_ITEM_STATUS + "') SCAN_STATUS_DESC,\n" +
//                    "       'PLACEHOLDER' PLACEHOLDER,\n" +
//                    "       EWD.BOX_NO,\n" +
//                    "       EWD.NET_UNIT,\n" +
//                    "       ESI.ITEM_NAME,\n" +
//                    "       ESI.ITEM_SPEC,\n" +
//                    "       ESI.ITEM_CATEGORY,\n" +
//                    "       AMS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_CATEGORY,'" + DictConstant.ITEM_TYPE + "') ITEM_CATEGORY_DESC, \n" +
//                    "       EWD.REMARK \n" +
//                    " FROM ETS_WORKORDER_DTL EWD, ETS_SYSTEM_ITEM ESI\n" +
//                    "WHERE EWD.ITEM_CODE = ESI.ITEM_CODE\n" +
//                    " AND NOT EXISTS (SELECT 1 FROM ETS_ITEM_INFO EII WHERE EII.BARCODE=EWD.BARCODE)\n" +
//                    "   AND EWD.ITEM_STATUS  = '" + DictConstant.SCAN_STATUS_REMAIN + "'\n" +
//                    "   AND EWD.WORKORDER_NO = ?\n" +
//                    "\n" +
//                    " UNION\n" +
////                    "--换下设备差异 status：7\n" +
////                    "--无差异：系统当前地点无，不处理 …………\n" +
////                    "--差异：系统当前地点有\n" +
////                    "--差异处理：更改设备到在途库\n" +
//                    "SELECT EWD.BARCODE,\n" +
//                    "       '" + DictConstant.SCAN_STATUS_EXISTS + "' ITEM_STATUS,\n" +
//                    "       AMS_PUB_PKG.GET_FLEX_VALUE('" + DictConstant.SCAN_STATUS_EXISTS + "','" + DictConstant.ORDER_ITEM_STATUS + "') ITEM_STATUS_DESC,\n" +
//                    "       EWD.ITEM_STATUS SCAN_STATUS,\n" +
//                    "       AMS_PUB_PKG.GET_FLEX_VALUE(EWD.ITEM_STATUS,'" + DictConstant.ORDER_ITEM_STATUS + "') SCAN_STATUS_DESC,\n" +
//                    "       'PLACEHOLDER' PLACEHOLDER,\n" +
//                    "       EWD.BOX_NO,\n" +
//                    "       EWD.NET_UNIT,\n" +
//                    "       ESI.ITEM_NAME,\n" +
//                    "       ESI.ITEM_SPEC,\n" +
//                    "       ESI.ITEM_CATEGORY,\n" +
//                    "       AMS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_CATEGORY,'" + DictConstant.ITEM_TYPE + "') ITEM_CATEGORY_DESC, \n" +
//                    "       EWD.REMARK \n" +
//                    " FROM ETS_WORKORDER_DTL EWD, ETS_SYSTEM_ITEM ESI\n" +
//                    "WHERE EWD.ITEM_CODE = ESI.ITEM_CODE\n" +
//                    " AND EWD.BARCODE  IN\n" +
//                    "       (SELECT BARCODE\n" +
//                    "          FROM ETS_ITEM_INFO EII, AMS_OBJECT_ADDRESS AOA\n" +
//                    "         WHERE EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
//                    "           AND AOA.OBJECT_NO = ?)\n" +
//                    "   AND EWD.WORKORDER_NO = ?\n" +
//                    "   AND EWD.ITEM_STATUS ='" + DictConstant.SCAN_STATUS_OFFLINE + "'\n" +
//                    "\n" +
//                    " UNION\n" +
//                    "--未扫描设备 status：6\n" +
//                    "--无差异：系统当前地点无，不处理 …………\n" +
//                    "--差异：系统当前地点有\n" +
//                    "--差异处理：更改设备到在途库\n" +
                    "SELECT EWD.BARCODE,\n" +
                    "       '" + DictConstant.SCAN_STATUS_EXISTS + "' ITEM_STATUS,\n" +
                    "       AMS_PUB_PKG.GET_FLEX_VALUE('" + DictConstant.SCAN_STATUS_EXISTS + "','" + DictConstant.ORDER_ITEM_STATUS + "') ITEM_STATUS_DESC,\n" +
                    "       EWD.ITEM_STATUS SCAN_STATUS,\n" +
                    "       AMS_PUB_PKG.GET_FLEX_VALUE(EWD.ITEM_STATUS,'" + DictConstant.ORDER_ITEM_STATUS + "') SCAN_STATUS_DESC,\n" +
                    "       'PLACEHOLDER' PLACEHOLDER,\n" +
                    "       EWD.BOX_NO,\n" +
                    "       EWD.NET_UNIT,\n" +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       ESI.ITEM_CATEGORY,\n" +
                    "       AMS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_CATEGORY,'" + DictConstant.ITEM_TYPE + "') ITEM_CATEGORY_DESC, \n" +
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
                    "   AND EWD.ITEM_STATUS ='" + DictConstant.SCAN_STATUS_NONE + "'\n" +
                    "\n" +
                    " UNION\n" +
//                    "--扫描设备 status：5\n" +
//                    "--无差异：系统当前地点有，不处理 …………\n" +
//                    "--差异：系统当前地点无\n" +
//                    "--差异处理：\n" +
                    "SELECT EWD.BARCODE,\n" +
                    "       '" + DictConstant.SCAN_STATUS_NONE + "' ITEM_STATUS,\n" +
                    "       AMS_PUB_PKG.GET_FLEX_VALUE('" + DictConstant.SCAN_STATUS_NONE + "','" + DictConstant.ORDER_ITEM_STATUS + "') ITEM_STATUS_DESC,\n" +
                    "       EWD.ITEM_STATUS SCAN_STATUS,\n" +
                    "       AMS_PUB_PKG.GET_FLEX_VALUE(EWD.ITEM_STATUS,'" + DictConstant.ORDER_ITEM_STATUS + "') SCAN_STATUS_DESC,\n" +
                    "       'PLACEHOLDER' PLACEHOLDER,\n" +
                    "       EWD.BOX_NO,\n" +
                    "       EWD.NET_UNIT,\n" +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       ESI.ITEM_CATEGORY,\n" +
                    "       AMS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_CATEGORY,'" + DictConstant.ITEM_TYPE + "') ITEM_CATEGORY_DESC, \n" +
                    "       EWD.REMARK \n" +
                    " FROM ETS_WORKORDER_DTL EWD, ETS_SYSTEM_ITEM ESI\n" +
                    "WHERE EWD.ITEM_CODE = ESI.ITEM_CODE\n" +
                    " AND EWD.BARCODE NOT  IN\n" +
                    "       (SELECT BARCODE\n" +
                    "          FROM ETS_ITEM_INFO EII, AMS_OBJECT_ADDRESS AOA\n" +
                    "         WHERE EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                    "           AND AOA.OBJECT_NO = ?)\n" +
                    "   AND EWD.WORKORDER_NO = ?\n" +
                    "   AND EWD.ITEM_STATUS ='" + DictConstant.SCAN_STATUS_EXISTS + "'\n" +
                    "\n" +
                    " UNION\n" +
//                    "--新增设备 status：0\n" +
//                    "--无差异：系统当前地点有，不处理 …………\n" +
//                    "--差异：系统当前地点无\n" +
//                    "--差异处理：\n" +
                    "SELECT EWD.BARCODE,\n" +
                    "       '" + DictConstant.SCAN_STATUS_NONE + "' ITEM_STATUS,\n" +
                    "       AMS_PUB_PKG.GET_FLEX_VALUE('" + DictConstant.SCAN_STATUS_NONE + "','" + DictConstant.ORDER_ITEM_STATUS + "') ITEM_STATUS_DESC,\n" +
                    "       EWD.ITEM_STATUS SCAN_STATUS,\n" +
                    "       AMS_PUB_PKG.GET_FLEX_VALUE(EWD.ITEM_STATUS,'" + DictConstant.ORDER_ITEM_STATUS + "') SCAN_STATUS_DESC,\n" +
                    "       'PLACEHOLDER' PLACEHOLDER,\n" +
                    "       EWD.BOX_NO,\n" +
                    "       EWD.NET_UNIT,\n" +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       ESI.ITEM_CATEGORY,\n" +
                    "       AMS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_CATEGORY,'" + DictConstant.ITEM_TYPE + "') ITEM_CATEGORY_DESC, \n" +
                    "       EWD.REMARK \n" +
                    " FROM ETS_WORKORDER_DTL EWD, ETS_SYSTEM_ITEM ESI\n" +
                    "WHERE EWD.ITEM_CODE = ESI.ITEM_CODE\n" +
                    " AND EWD.BARCODE NOT IN\n" +
                    "       (SELECT BARCODE\n" +
                    "          FROM ETS_ITEM_INFO EII, AMS_OBJECT_ADDRESS AOA\n" +
                    "         WHERE EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                    "           AND AOA.OBJECT_NO = ?)\n" +
                    "   AND EWD.WORKORDER_NO = ?\n" +
                    "   AND EWD.ITEM_STATUS ='" + DictConstant.SCAN_STATUS_NEW + "'\n" +
                    "";
}
