package com.sino.ams.constant;

/**
 * User: zhoujs
 * Date: 2007-11-1
 * Time: 11:55:27
 * Function:
 */
public interface AmsOrderConstant {
	String orderUploadPath="/workorder";
    String orderBackupPath="/uploadFiles/bak";

    String scanAllItemCategory="";//扫描所有专业

    String doubleOrder="DOUBLE";
    String EXCEED_ORDER="EXCEEDr";
    String ACHIEVE_ROLE="工单归档人";

    //==================工单差异比较
    String CONFIRM_NONE="未处理";
    String CONFIRM_SPECIAL_DEPT="请选择专业管理部门";
    String CONFIRM_SCAN="扫描结果为准";
    String CONFIRM_SYSTEM="系统数据为准";

    String ORDER="1";//单据类型,1为工单

}