package com.sino.nm.spare2.constant;

import com.sino.ams.constant.DictConstant;

/**
 * <p>Title: SinoEAMS</p>
 * <p>Description: </p>
 * <p>Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2007 - 2008</p>
 * <p>Company: 北京思诺搏信息技术有限公司</p>
 * @author 何睿
 * @version 0.1
 *          Date: 2008-6-18
 */
public interface SpareConstant extends DictConstant {
    String ACCEPTED     = "ACCEPTED";                 //已接收
    String HAS_RECEIVED = "HAS_RECEIVED";             //已收到
    String NOT_RECEIVED = "NOT_RECEIVED";             //未收到
    String RETURNED     = "RETURNED";                 //已归还
    String TO_ACCEPT    = "TO_ACCEPT";                //待接收
    String TO_CONFIRM   = "TO_CONFIRM";               //待确认
    String TO_CONSIGN   = "TO_CONSIGN";               //待出库

}
