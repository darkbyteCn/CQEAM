package com.sino.ams.newasset.constant;

import com.sino.ams.constant.LookUpConstant;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public interface AssetsLookUpConstant extends LookUpConstant {
	String LOOK_UP_DEPT = "LOOK_UP_DEPT"; //资产调拨中查找部门
	String LOOK_UP_DEPT_NM = "LOOK_UP_DEPT_NM"; //资产调拨中查找部门
	String LOOK_UP_ADDRESS = "LOOK_UP_ADDRESS"; //查找接收地点
	String LOOK_UP_RECIIVER = "LOOK_UP_RECIIVER"; //选择接收人
	String LOOK_UP_ASSETS = "LOOK_UP_ASSETS"; //查找资产
	String LOOK_TRANSFER_ASSETS = "LOOK_TRANSFER_ASSETS"; //查找调拨资产
	
	String LOOK_TRANSFER_DIS_ASSETS = "LOOK_TRANSFER_DIS_ASSETS"; //查找调拨资产
	
    String LOOK_TRANSFER_ITEM = "LOOK_TRANSFER_ITEM"; //查找调拨资产
    String LOOK_ZEROTURN_ASSETS="LOOK_ZEROTURN_ASSETS";//查找零购资产
	String LOOK_TRANSFER_ASSETS_TD = "LOOK_TRANSFER_ASSETS_TD"; //查找调拨资产
	String LOOK_UP_FACAT_CODE = "LOOK_UP_FACAT_CODE"; //查找资产类别
	String LOOK_UP_ACCOUNT = "LOOK_UP_ACCOUNT"; //查找折旧账户
	String LOOK_UP_COMPANY = "LOOK_UP_COMPANY"; //资产管理权限中查找公司
	String LOOK_UP_MIS_COMPANY = "LOOK_UP_MIS_COMPANY"; 
	
	String LOOK_UP_PRI_DEPT = "LOOK_UP_PRI_DEPT"; //资产管理权限中查找部门
	String LOOK_UP_PRI_ROLE = "LOOK_UP_PRI_ROLE"; //资产管理权限中查找角色
	String LOOK_UP_PERSON = "LOOK_UP_PERSON"; //选择公司职员。
	String LOOK_UP_USER = "LOOK_UP_USER"; //选择系统用户
	String LOOK_UP_ZERO_USER = "LOOK_UP_ZERO_USER"; //选择成本中心用户
	String LOOK_UP_USER_ZERO_ACHIEVE = "LOOK_UP_USER_ZERO_ACHIEVE"; //选择成本中心归档人
	String LOOK_UP_USER_ACHIEVE = "LOOK_UP_USER_ACHIEVE"; //选择归档人
	String LOOK_UP_LOCATION = "LOOK_UP_LOCATION"; //选择盘点地点
	String LOOK_UP_VENDOR = "LOOK_UP_VENDOR"; //选择厂商
	String LOOK_UP_RENT = "LOOK_UP_RENT"; //选择租赁资产
	String LOOK_UP_COST = "LOOK_UP_COST"; //选择成本中心
	String LOOK_UP_ITEMNAME = "LOOK_UP_ITEMNAME";//选择设备名称
	String LOOK_UP_MISLOC = "LOOK_UP_MISLOC";//选择MIS地点
	String LOOK_UP_TDLOC = "LOOK_UP_TDLOC";//选择TD地点
	String LOOK_UP_USER_BY_ORGANAZATION = "LOOK_UP_USER_BY_ORGANAZATION"; //得到指定OU下的所有用户
	String LOOK_SPECIAL_ASSETS = "LOOK_SPECIAL_ASSETS"; //查找调拨资产
	String LOOK_UP_SPECIAL_USER ="LOOK_UP_SPECIAL_USER";
	//2011-07-07
	String LOOK_TRANSFER_ASSETS_OTHER = "LOOK_TRANSFER_ASSETS_OTHER"; //查找其他实物报废资产
	String LOOK_ASSETS_LEASE = "LOOK_ASSETS_LEASE"; //资产续租
	String LOOK_ASSETS_SHARE = "LOOK_ASSETS_SHARE"; //资产共享
	
	String LOOK_UP_LNE = "LOOK_UP_LNE";
	String LOOK_UP_LNE_NOMATCH_AMS = "LOOK_UP_LNE_NOMATCH_AMS";	//没有和资产目录建立对应关系的逻辑网络元素
	String LOOK_UP_CEX = "LOOK_UP_CEX";
	String LOOK_UP_CEX_NOMATCH_AMS = "LOOK_UP_CEX_NOMATCH_AMS"; //没有和资产目录建立对应关系的投资分类
	String LOOK_UP_OPE = "LOOK_UP_OPE";
	String LOOK_UP_OPE_NOMATCH_AMS = "LOOK_UP_OPE_NOMATCH_AMS"; //没有和资产目录建立对应关系的业务平台
	String LOOK_UP_NLE = "LOOK_UP_NLE";
	String LOOK_UP_NLE_NOMATCH_AMS = "LOOK_UP_NLE_NOMATCH_AMS"; //没有和资产目录建立对应关系的网络层次属性
    String LOOK_UP_SN = "LOOK_UP_SN";

    String LOOK_UP_FA_CONTENT = "LOOK_UP_FA_CONTENT";
    String LOOK_UP_FA_CONTENTS = "LOOK_UP_FA_CONTENTS";
    String LOOK_UP_FA_CONTENT_LINE = "LOOK_UP_FA_CONTENT_LINE";
    String LOOK_UP_FA_LNE = "LOOK_UP_FA_LNE";
    String LOOK_UP_FA_CEX = "LOOK_UP_FA_CEX";
    String LOOK_UP_FA_OPE = "LOOK_UP_FA_OPE";
    String LOOK_UP_FA_NLE = "LOOK_UP_FA_NLE";
    String LOOK_UP_LNE_LINE = "LOOK_UP_LNE_LINE";
    String LOOK_UP_CEX_LINE = "LOOK_UP_CEX_LINE";
    String LOOK_UP_OPE_LINE = "LOOK_UP_OPE_LINE";
    String LOOK_UP_NLE_LINE = "LOOK_UP_NLE_LINE";
    
    String LOOK_TRANSFER_ASSETS_RFU = "LOOK_TRANSFER_ASSETS_RFU"; //查找调拨资产(紧急调拨补汇总单据用)
    String LOOK_ASSETS_DEVALUE = "LOOK_ASSETS_DEVALUE"; //资产减值
	String LOOK_UP_USER_CHECK_BATCH = "LOOK_UP_USER_CHECK_BATCH";//资产盘点单任务批，选择归档人
}
