package com.sino.ams.sampling.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sino.ams.newasset.urgenttrans.constant.UrgentAppConstant;
import com.sino.ams.sampling.constant.SamplingDicts;

/**
 * <p>Title: SinoAMS</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public abstract class OrderXMLAssistant { //本类的存在是因为PDA上的程序没有按照思诺博公司开发规范进行命名，因此添加了该映射关系。

	private static String rootName = "workorders";
	private static String orderName = "workorder";
	private static String itemName = "item";
	private static String orderPrimaryKey = "id";
	private static String orderType = "type";
	private static Map samOrderMap = new HashMap();
	private static Map downloadItemMap = new HashMap();
	private static Map uploadItemMap = new HashMap();

	private static Map createOrderMap = new HashMap();
	private static List orderTypes = new ArrayList();
	private static Map orderMaps = new HashMap();

	static {
		/**
		 * 1.上下载盘点工单头信息
		 */
		samOrderMap.put("id", "orderNo");
		samOrderMap.put("taskNo", "taskNo");
		samOrderMap.put("taskName", "taskName");
		samOrderMap.put("type", "orderType");
		samOrderMap.put("object_no", "samplingLocation");
		samOrderMap.put("location", "samplingLocationName");
		samOrderMap.put("creator", "createdUser");
		samOrderMap.put("creation_date", "creationDate");
		samOrderMap.put("start_date", "startTime");
		samOrderMap.put("period", "implementDays");
		samOrderMap.put("deadline_date", "deadlineDate");
		samOrderMap.put("AssignDate", "distributeDate");
		samOrderMap.put("scanover_date", "scanoverDate");

		/**
		 * 2.下载盘点工单行信息
		 */
		downloadItemMap.put("code", "barcode");
		downloadItemMap.put("item_code", "itemCode");
		downloadItemMap.put("name", "itemName");
		downloadItemMap.put("type", "itemSpec");
		downloadItemMap.put("category", "itemCategory");
		downloadItemMap.put("start_date", "startDate");
		downloadItemMap.put("assign_userid", "responsibilityUser");
		downloadItemMap.put("assign_groupid", "responsibilityDept");
		downloadItemMap.put("username", "maintainUser");
		downloadItemMap.put("scandate", "scanDate");
		downloadItemMap.put("box_no", "boxNo");
		downloadItemMap.put("net_unit", "netUnit");
		downloadItemMap.put("status", "systemStatus"); //设备扫描状态：5表示扫描到，6表示未扫描到.


		/**
		 * 3.上载盘点工单行信息
		 */
		uploadItemMap.put("code", "barcode");
		uploadItemMap.put("item_code", "itemCode");
		uploadItemMap.put("name", "itemName");
		uploadItemMap.put("type", "itemSpec");
		uploadItemMap.put("category", "itemCategory");
		uploadItemMap.put("start_date", "startDate");
		uploadItemMap.put("assign_userid", "responsibilityUser");
		uploadItemMap.put("assign_groupid", "responsibilityDept");
		uploadItemMap.put("username", "maintainUser");
		uploadItemMap.put("scandate", "scanDate");
		uploadItemMap.put("box_no", "boxNo");
		uploadItemMap.put("net_unit", "netUnit");
		uploadItemMap.put("status", "scanStatus"); //设备扫描状态：5表示扫描到，6表示未扫描到.

		/**
		 * 4.PDA创建工单信息
		 */
		createOrderMap.put("type", "orderType");
		createOrderMap.put("GroupID", "groupId");
		createOrderMap.put("category", "category");
		createOrderMap.put("name", "loginName");
		createOrderMap.put("object_no", "samplingLocation");
		createOrderMap.put("user_id", "archivedBy");
		createOrderMap.put("scan_category","checkCategory");
		createOrderMap.put("costCode", "costCenterCode");
		createOrderMap.put("taskNo", "taskNo");

		/**
		 * 5.支持的抽查工单类型(为便于工单上载时统一处理，加入盘点部分的工单)
		 */
		orderTypes.add(SamplingDicts.ASS_CHK_PAD); //资产盘点
		orderTypes.add(SamplingDicts.INS_CHK_PAD); //仪表盘点
		orderTypes.add(SamplingDicts.RNT_CHK_PAD); //租赁盘点
		orderTypes.add(SamplingDicts.ASS_SAM_PDA);//资产抽查
		
		orderTypes.add( UrgentAppConstant.PDA_CREATE_TYPE_NAME );//资产紧急调拨
		
		/**
		 * 6.盘点工单PDA端描述到代码的映射
		 */
		orderMaps.put(SamplingDicts.ASS_CHK_PAD, SamplingDicts.ASS_CHK);
		orderMaps.put(SamplingDicts.INS_CHK_PAD, SamplingDicts.INS_CHK);
		orderMaps.put(SamplingDicts.RNT_CHK_PAD, SamplingDicts.RNT_CHK);
		orderMaps.put(SamplingDicts.ASS_SAM_PDA, SamplingDicts.ORDR_NO_MARK);
	}

	/**
	 * 功能：获取盘点工单行数据PDA与服务器端字段映射关系
	 * @return Map
	 */
	public static Map getDownloadItemMap() {
		return downloadItemMap;
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
	public static Map getSamOrderMap() {
		return samOrderMap;
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

	public static Map getUploadItemMap(){
		return uploadItemMap;
	}
}
