package com.sino.nm.spare2.bean;

import com.sino.ams.constant.LookUpConstant;

/**
 * <p>Title: SinoEAMS</p>
 * <p>Description: 用于备件部分</p>
 * <p>Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2007 - 2008</p>
 * <p>Company: 北京思诺搏信息技术有限公司</p>
 * @author 何睿
 * @version 0.1
 *          Date: 2008-3-26
 */
public interface SpareLookUpConstant extends LookUpConstant {
    String BJCK_SPARE_INFO = "BJCK_SPARE_INFO";     //备件出库查找设备(NM)
    String BJBF_SPARE_INFO_DX = "BJBF_SPARE_INFO_DX";     //备件报废查找设备(NM)--从待修数量报废
    String BJBF_SPARE_INFO_SX = "BJBF_SPARE_INFO_SX";     //备件报废查找设备(NM)--从送修数量报废
    String OBJECT_NO = "OBJECT_NO";                 //备件仓库
    String OU_OBJECT = "OU_OBJECT";                 //备件仓库和OU
}
