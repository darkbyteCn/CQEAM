package com.sino.ams.newasset.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sino.ams.newasset.constant.AssetsDictConstant;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class OrderXMLAssistant { //本类的存在是因为PDA上的程序没有按照思诺博公司开发规范进行命名，因此添加了该映射关系。

	private static String rootName = "workorders";
	private static String orderName = "workorder";
	private static String itemName = "item";
	private static String orderPrimaryKey = "id";
	private static String orderType = "type";
	private static Map chkOrderMap = new HashMap();
	private static Map chkItemMap = new HashMap();
	private static Map createOrderMap = new HashMap();
	private static List orderTypes = new ArrayList();
	private static Map orderMaps = new HashMap();

	static {
		/**
		 * 1.上下载盘点工单头信息
		 */
		chkOrderMap.put("id", "transNo");
		chkOrderMap.put("type", "orderType");
		chkOrderMap.put("object_no", "checkLocation");
		chkOrderMap.put("location", "objectLocation");
		chkOrderMap.put("location_code", "objectCode");
		chkOrderMap.put("creator", "createdLoginUser");
		chkOrderMap.put("creation_date", "creationDate");
		chkOrderMap.put("start_date", "startTime");
		chkOrderMap.put("period", "implementDays");
		chkOrderMap.put("deadline_date", "deadlineDate");
//        chkOrderMap.put("locationname", "workorderObjectName");
		chkOrderMap.put("AssignDate", "distributeDate");
		chkOrderMap.put("scanover_date", "scanoverDate");
		chkOrderMap.put("scan_category", "checkCategory");
		chkOrderMap.put("costCode", "costCenterCode");
		chkOrderMap.put("new_location", "newLocation");

		/**
		 * 2.上下载盘点工单行信息
		 */
		chkItemMap.put("code", "barcode");
		chkItemMap.put("item_code", "itemCode");
		chkItemMap.put("name", "itemName");
		chkItemMap.put("type", "itemSpec");
		chkItemMap.put("category", "itemCategory");
		chkItemMap.put("vendor_code", "vendorBarcode");
		chkItemMap.put("start_date", "startDate");
		chkItemMap.put("assign_userid", "responsibilityUser");
		chkItemMap.put("assign_groupid", "responsibilityDept");
		chkItemMap.put("username", "maintainUser");
		chkItemMap.put("scandate", "scanDate");
		chkItemMap.put("box_no", "boxNo");
		chkItemMap.put("net_unit", "netUnit");
		chkItemMap.put("status", "scanStatus"); //设备扫描状态：5表示扫描到，6表示未扫描到.
		chkItemMap.put("attribute1", "attribute1"); //设备  是否租赁资产
		chkItemMap.put("manufacturerId", "manufacturerId");
		chkItemMap.put("isShare", "isShare");
		chkItemMap.put("contentCode", "contentCode");
		chkItemMap.put("contentName", "contentName");
		chkItemMap.put("power", "power");
		chkItemMap.put("replace_flag", "replaceFlag");
		chkItemMap.put("new_tag", "newTag");
		chkItemMap.put("construct_status", "constructStatus");
		chkItemMap.put("lne_id", "lneId");
		chkItemMap.put("lne_name", "lneName");
		chkItemMap.put("cex_id", "cexId");
		chkItemMap.put("cex_name", "cexName");
		chkItemMap.put("ope_id", "opeId");
		chkItemMap.put("ope_name", "opeName");
		chkItemMap.put("nle_id", "nleId");
		chkItemMap.put("nle_name", "nleName");


		/**
		 * 3.PDA创建工单信息
		 */
		createOrderMap.put("type", "orderType");
		createOrderMap.put("GroupID", "groupId");
		createOrderMap.put("category", "category");
		createOrderMap.put("name", "loginName");
		createOrderMap.put("object_no", "checkLocation");
		createOrderMap.put("user_id", "archivedBy");
		createOrderMap.put("scan_category","checkCategory");
		createOrderMap.put("costCode", "costCenterCode");

		/**
		 * 4.支持的盘点工单类型
		 */
		orderTypes.add(AssetsDictConstant.ASS_CHK_PAD); //资产盘点
		orderTypes.add(AssetsDictConstant.INS_CHK_PAD); //仪表盘点
		orderTypes.add(AssetsDictConstant.RNT_CHK_PAD); //租赁盘点

		/**
		 * 5.盘点工单PDA端描述到代码的映射
		 */
		orderMaps.put(AssetsDictConstant.ASS_CHK_PAD, AssetsDictConstant.ASS_CHK);
		orderMaps.put(AssetsDictConstant.INS_CHK_PAD, AssetsDictConstant.INS_CHK);
		orderMaps.put(AssetsDictConstant.RNT_CHK_PAD, AssetsDictConstant.RNT_CHK);
	}

	/**
	 * 功能：构造函数私有化，防止外部调用实例化本类
	 */
	private OrderXMLAssistant() {
		super();
	}

	/**
	 * 功能：获取盘点工单行数据PDA与服务器端字段映射关系
	 * @return Map
	 */
	public static Map getChkItemMap() {
		return chkItemMap;
	}

	/**
	 * 功能：获取盘点工单行数据节点名称
	 * @return String
	 */
	public static String getItemName() {
		return itemName;
	}

	/**
	 * 功能：获取盘点工单PDA与服务器端字段映射关系
	 * @return Map
	 */
	public static Map getChkOrderMap() {
		return chkOrderMap;
	}


	/**
	 * 功能：获取PDA创建盘点工单与服务器端字段映射关系
	 * @return Map
	 */
	public static Map getOrderCreateMap() {
		return createOrderMap;
	}

	/**
	 * 功能：获取盘点工单节点名称
	 * @return String
	 */
	public static String getOrderName() {
		return orderName;
	}

	/**
	 * 功能:获取盘点工单XML文件根节点名称
	 * @return String
	 */
	public static String getRootName() {
		return rootName;
	}

	/**
	 * 功能：获取工单主键字段。
	 * @return String
	 */
	public static String getOrderPrimaryKey() {
		return orderPrimaryKey;
	}

	/**
	 * 功能：获取工单类型字段。
	 * @return String
	 */
	public static String getOrderType() {
		return orderType;
	}

	public static List getOrderTypes() {
		return orderTypes;
	}

	public static Map getOrderMaps() {
		return orderMaps;
	}
}
