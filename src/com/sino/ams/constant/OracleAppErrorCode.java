package com.sino.ams.constant;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: </p>
 * <p>Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2007</p>
 * <p>Company: 北京思诺搏信息技术有限公司</p>
 * @author 何睿
 * @version 0.1
 *          Date: 2008-2-22
 */
public interface OracleAppErrorCode {

    //注: 在写存储过程时如果有自定义的应用异常,该定义应和数据库中AMS_CONSTANT_PKG中的定义一致
    // 请将代码在此注册,AMS_CONSTANT_PKG中的定义请在前面加上"-",已避免跟ORACLE的异常代码冲突,这里不需要加"-"

    int spareNotEnough1 = 20001;      //待修库中已无此部件号的设备
    int spareNotEnough2 = 20002;      //送修库中已无此部件号的设备
    int spareNotEnough3 = 20003;      //仓库中已此部件号的设备数量不足

    int APPROVE_USER_NOT_FOUND = 20011;  //流程审批人未找到

    int QUANTITY_NOT_ENOUGH = 20021;  //数量不足
}
