package com.sino.sso.constant;

import com.sino.ams.constant.WebAttrConstant;

/**
 * Created by IntelliJ IDEA.
 * User: zhoujs
 * Date: 2009-2-18
 * Time: 17:09:22
 * Functiion:
 */
public interface SSOWebAttributes extends WebAttrConstant {
    String SYNC_CHANGES="SYNC_CHANGES";//资产直接变动同步
    String SYNC_TRANS_IN_COMP ="SYNC_TRANS_IN_COMP";// 公司间调拨同步
    String SYNC_TRANS_RESULT="SYNC_TRANS_RESULT";//调拨结果直接同步
    String ASSETS_CONFIRM_INFO = "ASSETS_CONFIRM_INFO";//资产确认信息

}
