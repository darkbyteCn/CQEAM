package com.sino.ams.newasset.urgenttrans.pda.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.urgenttrans.constant.UrgentAppConstant;

/**
 * 
 * @系统名称: 紧急调拨PDA程序
 * @功能描述: 
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本：1.0
 * @开发作者: sj
 * @创建时间: Aug 3, 2011
 */
public class UrgentOrderXMLAssistant {
	private static String rootName = "workorders";
	private static String orderName = "workorder";
	private static String itemName = "item";
	private static String orderPrimaryKey = "id";
	private static String orderType = "type";
	private static Map<String,String> orderMap = new HashMap<String,String>();
	private static Map<String,String> itemMap = new HashMap<String,String>();
	private static Map<String,String> createOrderMap = new HashMap<String,String>();
	private static Map<String,String> orderMaps = new HashMap<String,String>();
	private static List orderTypes = new ArrayList();
	
	static { 
		orderMap.put("period", "period");
		orderMap.put("id", "transNo");
		orderMap.put("type", "orderType");
		orderMap.put("creation_date", "creationDate");
		orderMap.put("creator", "created");
		orderMap.put("created_reason", "createdReason");
		
		orderMap.put("object_no", "fromObjectNo");
		orderMap.put("location", "fromObjectName");
		orderMap.put("object_code", "fromObjectCode"); 
		
		orderMap.put("to_object_no", "toObjectNo");
		orderMap.put("to_object_name", "toObjectName");
		orderMap.put("to_object_code", "toObjectCode");  
		 
		itemMap.put("code", "barcode");
		itemMap.put("item_code", "itemCode");
		itemMap.put("name", "itemName");
		itemMap.put("type", "itemSpec");
		itemMap.put("category", "itemCategory");
		itemMap.put("start_date", "startDate");
		itemMap.put("username", "responsibilityUser");
		 
		/**
		 * 3.PDA创建工单信息
		 */
		createOrderMap.put("type", "orderType");
		createOrderMap.put("from_dept", "fromDept");
		createOrderMap.put("created_by", "loginName");
		createOrderMap.put("implement_by", "toImplementBy");
		createOrderMap.put("from_object_no", "fromObjectNo");
		createOrderMap.put("to_object_no", "toObjectNo");
		createOrderMap.put("archived_by","archivedBy");
		createOrderMap.put("created_reason", "createdReason"); 
		
		/**
		 * 4.支持的盘点工单类型
		 */
		orderTypes.add( UrgentAppConstant.OUT_ORDER_NAME );  
		orderTypes.add( UrgentAppConstant.IN_ORDER_NAME );
	}

	public static String getRootName() {
		return rootName;
	}

	public static void setRootName(String rootName) {
		UrgentOrderXMLAssistant.rootName = rootName;
	}

	public static String getOrderName() {
		return orderName;
	}

	public static void setOrderName(String orderName) {
		UrgentOrderXMLAssistant.orderName = orderName;
	}

	public static String getItemName() {
		return itemName;
	}

	public static void setItemName(String itemName) {
		UrgentOrderXMLAssistant.itemName = itemName;
	}

	public static String getOrderPrimaryKey() {
		return orderPrimaryKey;
	}

	public static void setOrderPrimaryKey(String orderPrimaryKey) {
		UrgentOrderXMLAssistant.orderPrimaryKey = orderPrimaryKey;
	}

	public static String getOrderType() {
		return orderType;
	}

	public static void setOrderType(String orderType) {
		UrgentOrderXMLAssistant.orderType = orderType;
	}

	public static Map<String,String> getOrderMap() {
		return orderMap;
	}

	public static void setOrderMap(Map<String,String> orderMap) {
		UrgentOrderXMLAssistant.orderMap = orderMap;
	}

	public static Map<String,String> getItemMap() {
		return itemMap;
	}

	public static void setItemMap(Map<String,String> itemMap) {
		UrgentOrderXMLAssistant.itemMap = itemMap;
	}

	public static Map<String,String> getCreateOrderMap() {
		return createOrderMap;
	}

	public static void setCreateOrderMap(Map<String,String> createOrderMap) {
		UrgentOrderXMLAssistant.createOrderMap = createOrderMap;
	}
 

	public static Map<String,String> getOrderMaps() {
		return orderMaps;
	}

	public static void setOrderMaps(Map<String,String> orderMaps) {
		UrgentOrderXMLAssistant.orderMaps = orderMaps;
	}

	public static List getOrderTypes() {
		return orderTypes;
	}

	public static void setOrderTypes(List orderTypes) {
		UrgentOrderXMLAssistant.orderTypes = orderTypes;
	}
}
