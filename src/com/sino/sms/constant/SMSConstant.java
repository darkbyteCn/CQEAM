package com.sino.sms.constant;

/**
 * <p>Title: SinoIES</p>
 * <p>Description: 河南移动应急采购系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public interface SMSConstant {
    String MSG_ENABLED_YES = "Y";
    String MSG_ENABLED_NO = "N";
    String NEED_RESEND_YES = "Y";
    String NEED_RESEND_NO = "N";
    String MSG_PROCESSED_YES = "Y";
    String MSG_PROCESSED_NO = "N";
    String MSG_CATEGORY_FLOW = "1";//流程用短信息提醒
    String MSG_CATEGORY_NO_FLOW = "161";//汇总单通过审批，生成采购订单，短信通知采购员，申购员，供应商

    //====================山西
//    String uri = "http://211.142.25.74:10086/alert/services/HuilinAlertService";
    String uri = "http://10.204.4.38:8080/alert/services/HuilinAlertService";
    String appId = "101014";

    //=================短信分类
    String INBOX = "INBOX";//收件箱
    String ORDER_DIS = "ORDER_DIS";//工单下发
    String ORDER_ACHIEVE = "ORDER_ACHIEVE";//工单归档
    String SPARE_ALLOT = "SPARE_ALLOT";//备件调拨通知
    String SPARE_RECIEVE = "SPARE_RECIEVE";//备件接收通知
    String ASSET_DIS = "ASSET_DIS";//资产盘点下发通知
    String CHECK_ORDER_ACHIEVE = "CHECK_ORDER_ACHIEVE";//资产盘点工单归档
    String RENT_MIND = "RENT_MIND";//租赁资产超时提醒
    String ASSET_CONFIRM = "ASSET_CONFIRM";//资产接收确认

    String INBOX_ID = "1";//收件箱
    String ORDER_DIS_ID = "2";//工单下发
    String ORDER_ACHIEVE_ID = "3";//工单归档
    String SPARE_ALLOT_ID = "4";//备件调拨通知
    String SPARE_RECIEVE_ID = "5";//备件接收通知
    String ASSET_DIS_ID = "6";//资产盘点下发通知
    String CHECK_ORDER_ACHIEVE_ID = "9";//资产盘点工单归档
    String RENT_MIND_ID = "7";//租赁资产超时提醒
    String ASSET_CONFIRM_ID = "8";//资产接收确认

}
