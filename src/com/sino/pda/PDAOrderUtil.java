package com.sino.pda;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.file.FileProcessor;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.config.SinoConfig;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.constant.AmsOrderConstant;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.system.fixing.dto.EtsItemInfoDTO;
import com.sino.ams.system.item.assistant.SystemItemUtil;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dto.EtsWorkorderBatchDTO;
import com.sino.ams.workorder.dto.EtsWorkorderDTO;
import com.sino.ams.workorder.model.WorkorderModel;
import com.sino.ams.workorder.util.WorkOrderUtil;
import com.sino.pda.model.PDAOrderUtilModel;
import com.sino.pda.util.XmlUtil;
import org.jdom.Element;

/**
 * User: zhoujs
 * Date: 2008-1-29
 * Time: 18:24:38
 * Function:工单PDA处理辅助类
 */
public class PDAOrderUtil {

	private static List netWorders = new ArrayList();//限制本程序处理的工单为网络类工单
    private static List itemOrders=new ArrayList();
	static{
		netWorders.add("新建");
		netWorders.add("扩容");
		netWorders.add("巡检");
		netWorders.add("维修");
		netWorders.add("搬迁");
		netWorders.add("减容");
		netWorders.add("替换");
		netWorders.add("调拨");
		netWorders.add("交接");

        itemOrders.add("交接");
        itemOrders.add("巡检");
//        itemOrders.add("零购");
	}

	/**
	 * PDA创建巡检工单
	 * @param conn
	 * @param xml
	 * @return String
	 * @throws QueryException
	 * @throws ContainerException
	 * @throws DataHandleException
	 */
	public String createWorkorder(Connection conn, XmlUtil xml) throws QueryException, ContainerException, DataHandleException {

		boolean operatorResult = false;
		String workorderNo = "";
		List allOrderNos = xml.getAllRootChildren();
		String groupId = xml.getElementAttrValue(xml.getRootElement(), "GroupID");
		String orderTypeDesc = xml.getElementAttrValue(xml.getRootElement(), "type");
		String orderType = PDAUtil.getOrderType(conn, orderTypeDesc);

		if (orderType.equals("")) {
			Logger.logError(" can't get workorder type info from system[ orderTypeDesc=" + orderTypeDesc + "]");
			operatorResult = false;
		} else {
			String createUser = "", objectNo = "", archived_by = "", scan_category = "",costCenterCode="",projectId="";
			int count = allOrderNos.size();
			for (int j = 0; j < count; j++) {
				Element xmlWO = xml.getNthElement(allOrderNos, j);
				if (xml.getElementName(xmlWO).equals("created_by")) {
					createUser = xml.getElementAttrValue(xmlWO, "name");
				}
				if (xml.getElementName(xmlWO).equals("location")) {
					objectNo = xml.getElementAttrValue(xmlWO, "object_no");
				}
				if (xml.getElementName(xmlWO).equals("archived_by")) {
					archived_by = xml.getElementAttrValue(xmlWO, "user_id");
				}
				if (xml.getElementName(xmlWO).equals("scan_category")) {
					scan_category = xml.getElementAttrValue(xmlWO, "scan_category");
				}
				if (xml.getElementName(xmlWO).equals("costCode")) {  //取成本中心
					costCenterCode = xml.getElementAttrValue(xmlWO, "costCode");
				}
                if (xml.getElementName(xmlWO).equals("projectId")) {  //项目编号
					projectId = xml.getElementAttrValue(xmlWO, "projectId");
				}
			}
			scan_category = StrUtil.isEmpty(scan_category) ? AmsOrderConstant.scanAllItemCategory : scan_category;


			SfUserDTO userAccount = PDAUtil.getUserInfo(conn, createUser);
			int userId = userAccount.getUserId();
			int organizationId = userAccount.getOrganizationId();

			boolean validateResult = true;
			if (userId ==0) {
				Logger.logInfo("can't get userinfo:" + createUser + " orderTypeDesc=" + orderTypeDesc + " objectNo=" + objectNo);
				validateResult = false;
			}
			if (StrUtil.isEmpty(objectNo)) {
				Logger.logError("非法的工单对象地点");
				validateResult = false;
			}
			//检测是否存在没有完成的工单 --暂不检查
			if (hasRecord(conn, objectNo, groupId, scan_category)) {
				Logger.logError("当前对象存在没有完成的巡检工单");
				workorderNo = AmsOrderConstant.doubleOrder;
				validateResult = false;
			}
			 if (exceedMaxCount(conn, objectNo)) {
	                Logger.logError("该地点设备数量超过系统设定最大值"+ SinoConfig.getMaxItemCount()+"不允许进行巡检");
	                workorderNo = AmsOrderConstant.EXCEED_ORDER;
	                validateResult = false;
	            }
			//check group and location
//			if (!validLocation(conn, objectNo, groupId)) {
//				Logger.logError("当前用户组别不允许建立当前对象地点的巡检工单");
//				workorderNo = "";
//				validateResult = false;
//			}

			if (validateResult) {
				String batchNo = WorkOrderUtil.getWorkorderBatchNo(conn);
				String orderNo = WorkOrderUtil.getWorkorderNo(batchNo, conn);

				String batchName = "PDA创建工单-" + getObjectCode(conn, objectNo);

				EtsWorkorderBatchDTO batchDTO = new EtsWorkorderBatchDTO();
				batchDTO.setWorkorderBatch(batchNo);
				batchDTO.setWorkorderBatchName(batchName);
				batchDTO.setWorkorderType(orderType);
				batchDTO.setCreatedBy(userId);
				batchDTO.setDistributeGroupId(StrUtil.strToInt(groupId));

				String cateDesc = getCatDescByGroup(groupId, conn);

				EtsWorkorderDTO orderDTO = new EtsWorkorderDTO();
				orderDTO.setWorkorderBatch(batchNo);
				orderDTO.setWorkorderNo(orderNo);
				orderDTO.setWorkorderType(orderType);
				orderDTO.setWorkorderObjectNo(objectNo);
				orderDTO.setGroupId(StrUtil.strToInt(groupId));
				orderDTO.setCreatedBy(userId);
				orderDTO.setOrganizationId(organizationId);
				orderDTO.setRemark(batchName);
				orderDTO.setDistributeGroup(StrUtil.strToInt(groupId));
				orderDTO.setAttribute4(cateDesc);
				orderDTO.setCheckoverBy(StrUtil.strToInt(archived_by));
				orderDTO.setImplementBy(userId);
				orderDTO.setAttribute7(scan_category);
				orderDTO.setCostCenterCode(costCenterCode);
                orderDTO.setPrjId(projectId);

				operatorResult = createOrder(conn, batchDTO, orderDTO);
				if (operatorResult) {
					workorderNo = orderDTO.getWorkorderNo();
				}
			}
		}

		return workorderNo;
	}

	/**
	 * PDA下载工单
	 * @param conn         数据库连接
	 * @param sfUser       用户DTO
	 * @param m_force      是否强制重新下载清单
	 * @param provinceCode 省代码（山西-41）
	 * @return String
	 */
	public String getAllWorkorder(Connection conn, SfUserDTO sfUser, String m_force, String provinceCode) {
		SQLModel sqlModel = new SQLModel();

		OrderModel orderModel = new OrderModel();
		WorkorderModel workorderModel = new WorkorderModel();
		boolean forceDownload = StrUtil.nullToString(m_force).equalsIgnoreCase("Y");
		forceDownload = true;

		StringBuffer returnStr = new StringBuffer("");
		try {
			sqlModel = orderModel.getOrdersModel(sfUser, forceDownload);
			SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
			simpleQuery.executeQuery();
			boolean isSuccess = true;
			if (simpleQuery.hasResult()) {
				RowSet rs = simpleQuery.getSearchResult();
				Row row = null;
				for (int i = 0; i < rs.getSize(); i++) {
					row = rs.getRow(i);
					String prjId=row.getStrValue("PRJ_ID");//工程编码
					String workorderNo = row.getStrValue("WORKORDER_NO");
					String objectNo = row.getStrValue("WORKORDER_OBJECT_NO");
					String orderTypeDesc = row.getStrValue("WORKORDER_TYPE_DESC");
					returnStr.append("<workorder id=\"").append(workorderNo).append("\"");
					returnStr.append(" type=\"").append(orderTypeDesc).append("\"");
					returnStr.append(" object_no=\"").append(objectNo).append("\"");
					returnStr.append(" location=\"").append(PDAUtil.xmlFormat(row.getStrValue("WORKORDER_OBJECT_LOCATION"))).append("\"");
					returnStr.append(" creation_date=\"").append(row.getStrValue("CREATION_DATE")).append("\"");
					returnStr.append(" creator=\"").append(row.getStrValue("CREATE_USER")).append("\"");
					returnStr.append(" start_date=\"").append(row.getStrValue("START_DATE") != null && !row.getStrValue("START_DATE").equals("") ? row.getStrValue("START_DATE").substring(0, 10) : row.getStrValue("START_DATE")).append("\"");
					returnStr.append(" period=\"").append(row.getStrValue("IMPLEMENT_DAYS")).append("\"");
					returnStr.append(" deadline_date=\"").append(row.getStrValue("DEADLINE_DATE")).append("\"");
					returnStr.append(" AssignDate=\"").append(row.getStrValue("DISTRIBUTE_DATE")).append("\"");
					returnStr.append(" signedDate=\"").append(row.getStrValue("DOWNLOAD_DATE")).append("\"");


					if (StrUtil.isEmpty(row.getStrValue("IMPLEMENT_USER"))) {
						returnStr.append(" IsGroup=\"Y\"");
					} else {
						returnStr.append(" IsGroup=\"N\"");
					}
					returnStr.append(" GroupID=\"").append(row.getStrValue("GROUP_ID")).append("\"");
					returnStr.append(" projectname=\"").append(PDAUtil.xmlFormat(row.getStrValue("PRJ_NAME"))).append("\"");
					returnStr.append(" category=\"").append(row.getStrValue("CATEGORY")).append("\"");
					returnStr.append(" inspect_range=\"").append(row.getStrValue("INSPECT_RANGE")).append("\"");
					returnStr.append(" costCode=\"").append(row.getStrValue("COST_CENTER_CODE")).append("\"");
//                    returnStr.append(" new_object_no=\"").append(PDAUtil.xmlFormat(row.getStrValue("NEW_OBJECT_NO"))).append("\"");
//                    returnStr.append(" new_location=\"").append(PDAUtil.xmlFormat(row.getStrValue("NEWOBJNAME"))).append("\"");

					returnStr.append(" > \n");

					returnStr = printAllItemInfoByWo(sfUser, workorderNo, returnStr, conn, orderTypeDesc, provinceCode,prjId);
					returnStr = printAllConfigInfoByWo(workorderNo, objectNo, orderTypeDesc, returnStr, conn,prjId);
//
					returnStr.append("</workorder>\n");
//                    isGroup = true;
//                    if (!isGroup) {
					List sqlModList = new ArrayList();

					//更新工单状态
					sqlModel = workorderModel.getUpdateOrderStatusModel(workorderNo, sfUser, String.valueOf (DictConstant.WOR_STATUS_DOWNLOAD));
					sqlModList.add(sqlModel);
					//更新工单进度
					sqlModel = workorderModel.getUpdateOrderProcessModel(workorderNo, DictConstant.WORKORDER_NODE_DOWNLOADED, true);
					sqlModList.add(sqlModel);

					isSuccess = DBOperator.updateBatchRecords(sqlModList, conn);
					if (!isSuccess) {
						Logger.logError("更新工单信息失败！");
					}
//                    }
				}
			}

			if (!isSuccess) {
				returnStr = new StringBuffer("");
			}
		} catch (DataHandleException e) {
			Logger.logError("获取工单信息失败！" + e.toString());
		} catch (QueryException e) {
			e.printStackTrace();
			Logger.logError("获取工单信息失败！" + e.toString());
		} catch (ContainerException e) {
			Logger.logError("获取工单信息失败！" + e.toString());
			e.printStackTrace();
		}

		return returnStr.toString();
	}

	/**
	 * PDA提交工单(非资产类工单)
	 * @param conn
	 * @param xml
	 * @param sfUser
	 * @param filePath
	 * @param webPath
	 * @return
	 */
	public boolean submitOrder(Connection conn, XmlUtil xml, SfUserDTO sfUser, String filePath, String webPath) {
		boolean submitResult = true;//由于需要网络类和资产类同时成功,才提交事务,故应设为true,即使没有处理任何工单.
		try {
			SQLModel sqlModel = new SQLModel();
			OrderModel orderModel = new OrderModel();
			WorkorderModel workorderModel = new WorkorderModel();
			SystemItemUtil systemItemUtil = new SystemItemUtil();
			boolean isSuccess = false;

			List workorders = xml.getAllRootChildren();
			int workorderCount = workorders.size();
			String workorderNo, workorderTypeDesc, objectNo, loc,scanoverDate;
			for (int i = 0; i < workorderCount; i++) { //工单循环
				Element xmlWO = xml.getNthElement(workorders, i);
				workorderNo = xml.getElementAttrValue(xmlWO, "id");
				workorderTypeDesc = xml.getElementAttrValue(xmlWO, "type");
                objectNo = xml.getElementAttrValue(xmlWO, "object_no");
				if (!netWorders.contains(workorderTypeDesc)) { //不在网络类工单列表中，则不予处理。
					continue;
				}
//            if (workorderTypeDesc.equals("盘点")) {
//                continue;
//            }

				/******************************************
				 *********** backup workorder file*********
				 ******************************************/

				String bkFlName = "No_" + workorderNo + ".xml";
				String bkFile = webPath + System.getProperty("file.separator") + bkFlName;
				File kk = new File(bkFile);
				FileProcessor fp = new FileProcessor();

				fp.copyFile(filePath, bkFile);

				loc = xml.getElementAttrValue(xmlWO, "location");
				scanoverDate = xml.getElementAttrValue(xmlWO, "scanoverDate");
				String workorderType = PDAUtil.getOrderType(conn, workorderTypeDesc);

				//如果工单已经被提交，则处理下个工单
				if (hasSubmit(conn, workorderNo)) {
					continue;
				}

				/**
				 * A:更新工单状态
				 */

				sqlModel = orderModel.getUpdateUploadOrderModel(workorderNo, scanoverDate);

				isSuccess = DBOperator.updateRecord(sqlModel, conn);

				if (!isSuccess) {
					submitResult = false;
					break;
				}

				/**
				 * B: 更新工单进度条
				 */
				sqlModel = workorderModel.getUpdateOrderProcessModel(workorderNo, DictConstant.WORKORDER_NODE_UPLODADED, true);

				isSuccess = DBOperator.updateRecord(sqlModel, conn);

				if (!isSuccess) {
					Logger.logError("PDA 更新工单进度条失败……");
					break;
				}

				/**
				 * C:写入dtl表
				 */
				List xmlAllBarCodes = xml.getAllChild(xmlWO);

				int itemCount = xmlAllBarCodes.size();

				for (int j = 0; j < itemCount; j++) {

					Element barcodeXml = xml.getNthElement(xmlAllBarCodes, j);
					if (!barcodeXml.getName().equals("item")) {
						break;
					}
					String status = xml.getElementAttrValue(barcodeXml, "status");
					String m_isnew = xml.getElementAttrValue(barcodeXml, "manual");

					EtsItemInfoDTO etsItemInfo = getEtsItemInfo(xml, barcodeXml);

					etsItemInfo.setOrganizationId(sfUser.getOrganizationId());

					etsItemInfo = systemItemUtil.checkSysItem(conn, etsItemInfo);

					List sqlModelList = new ArrayList();
					sqlModel = orderModel.getInsertDtlModel(etsItemInfo, workorderNo, status);
					sqlModelList.add(sqlModel);
                    sqlModel = orderModel.getUpdateDtlModel(workorderNo,objectNo);
                    sqlModelList.add(sqlModel);
					sqlModel = orderModel.getInsertInterfaceModel(etsItemInfo, workorderNo, status);
					sqlModelList.add(sqlModel);
					isSuccess = DBOperator.updateBatchRecords(sqlModelList, conn);
				}
				submitResult = true;
			}
		} catch (Exception ex) {
			Logger.logError(ex);
			submitResult = false;
		}
		return submitResult;
	}


	/**
	 * 获取工单地点下的当前设备
	 * @param sfUser       SfUserDTO
	 * @param orderNo      String
	 * @param strBuff      StringBuffer
	 * @param conn         Connection
	 * @param orderType    String
	 * @param provinceCode String
	 * @return StringBuffer
	 */
	private StringBuffer printAllItemInfoByWo(SfUserDTO sfUser, String orderNo, StringBuffer strBuff, Connection conn, String orderType, String provinceCode,String prjId) {

//		if ((orderType.equals("巡检"))) {
		if (itemOrders.contains(orderType)) {
			OrderModel orderModel = new OrderModel();
			SQLModel sqlModel = new SQLModel();
			String sql = "";

			/**
			 * 修改目的：当工单是巡检工单时，要得到ets_workorder中attribute7的值
			 */
			try {

				String itemCategory = getItemCategoryOfOrder(conn, orderNo);

				String costCenterCode = getCostCenterOfOrder(conn, orderNo);


				if (provinceCode.equals("41")) {//山西按照部门下载对应的设备清单，如果组别没有对应的MIS部门，则忽略该条件
					if (StrUtil.isEmpty(costCenterCode)) {
						sqlModel = orderModel.getItemsModel(orderNo, itemCategory, sfUser.getOrganizationId());
//                        if (orderType.equals("交接")&&!prjId.equals(DictConstant.ZERO_PRJ_ID)) {
//                            sqlModel = orderModel.getOrderItemsModel(orderNo, itemCategory, sfUser.getOrganizationId());
//                        }else if(orderType.equals("交接")&&prjId.equals(DictConstant.ZERO_PRJ_ID)){
//                        	
//                        }
                        if (orderType.equals("交接")) {
                            sqlModel = orderModel.getOrderItemsModel(orderNo, itemCategory, sfUser.getOrganizationId());
                        }
					} else {
						sqlModel = orderModel.getCostCenterItemsModel(orderNo, itemCategory, sfUser.getOrganizationId(), costCenterCode);
						if (orderType.equals("交接")&&prjId.equals(DictConstant.ZERO_PRJ_ID)) {
                            sqlModel = orderModel.getZeroCostCenterItemsModel(orderNo, itemCategory, sfUser.getOrganizationId(), costCenterCode);
                        }
					}
				} else {
					sqlModel = orderModel.getItemsModel(orderNo, itemCategory, sfUser.getOrganizationId());
                    if(orderType.equals("交接")&&!prjId.equals(DictConstant.ZERO_PRJ_ID)){
                        sqlModel=orderModel.getOrderItemsModel(orderNo, itemCategory, sfUser.getOrganizationId());
                    }
                    if (orderType.equals("交接")&&prjId.equals(DictConstant.ZERO_PRJ_ID)) {
                        sqlModel = orderModel.getZeroCostCenterItemsModel(orderNo, itemCategory, sfUser.getOrganizationId(), costCenterCode);
                    }
				}

				Logger.logInfo("下载工单--设备信息： " + sql);
				SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
				simpleQuery.executeQuery();
				if (simpleQuery.hasResult()) {
					RowSet rowSet = simpleQuery.getSearchResult();
					Row row = null;
					for (int i = 0; i < rowSet.getSize(); i++) {
						row = rowSet.getRow(i);
						strBuff.append("<item code=\"").append(row.getStrValue("BARCODE")).append("\"");
						strBuff.append(" name=\"").append(PDAUtil.xmlFormat(row.getStrValue("ITEM_NAME"))).append("\"");
						strBuff.append(" type=\"").append(PDAUtil.xmlFormat(row.getStrValue("ITEM_SPEC"))).append("\"");
						strBuff.append(" status=\"6\"");
						strBuff.append(" quantity=\"").append(row.getStrValue("ITEM_QTY")).append("\"");
						strBuff.append(" item_code=\"").append(row.getStrValue("ITEM_CODE")).append("\"");
						strBuff.append(" category=\"").append(row.getStrValue("ITEM_CATEGORY")).append("\"");
						strBuff.append(" vendor_barcode=\"").append(row.getStrValue("VENDOR_BARCODE")).append("\"");
						strBuff.append(" start_date=\"").append(row.getStrValue("START_DATE") != null && !row.getStrValue("START_DATE").equals("") ? row.getStrValue("START_DATE").substring(0, 10) : row.getStrValue("START_DATE")).append("\"");
						strBuff.append(" address_id=\"").append(row.getStrValue("ADDRESS_ID")).append("\"");
						strBuff.append(" assign_groupid=\"").append(row.getStrValue("ASSIGN_GROUPID")).append("\"");// 责任部门
						strBuff.append(" assign_userid=\"").append(row.getStrValue("ASSIGN_USERID")).append("\"");//责任人
						strBuff.append(" box_no=\"").append(row.getStrValue("BOX_NO")).append("\"");
						strBuff.append(" net_unit=\"").append(row.getStrValue("NET_UNIT")).append("\"");
						strBuff.append(" parent_barcode=\"").append(row.getStrValue("PARENT_BARCODE")).append("\"");
						strBuff.append(" username=\"").append(row.getStrValue("USERNAME")).append("\"");//使用人
						strBuff.append(" contentCode=\"").append(row.getStrValue("CONTENT_CODE")).append("\"");//使用人
						strBuff.append(" contentName=\"").append(row.getStrValue("CONTENT_NAME")).append("\"");//使用人
						strBuff.append(" shareStatus=\"").append(row.getStrValue("SHARE_STATUS")).append("\"");//使用人
						strBuff.append(" constructStatus=\"").append(row.getStrValue("CONSTRUCT_STATUS")).append("\"");//使用人
						strBuff.append(" lneId=\"").append(row.getStrValue("LNE_ID")).append("\"");//使用人
						strBuff.append(" cexId=\"").append(row.getStrValue("CEX_ID")).append("\"");//使用人
						strBuff.append(" opeId=\"").append(row.getStrValue("OPE_ID")).append("\"");//使用人
						strBuff.append(" nleId=\"").append(row.getStrValue("NLE_ID")).append("\"");//使用人

						strBuff.append("/> \n");
					}
				}
			} catch (QueryException e) {
				Logger.logError("获取工单巡检地点设备信息错误：" + e.toString());
			} catch (ContainerException e) {
				Logger.logError("获取工单巡检地点设备信息错误：" + e.toString());
			}
		}
		return strBuff;
	}

	private StringBuffer printAllConfigInfoByWo(String workorderNo, String objNo, String orderType, StringBuffer m_sb, Connection conn,String prjId) {
		OrderModel orderModel = new OrderModel();
		String conDesc = "", newConDesc = "", workorderType = "", itemCategory = "";
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sql;

		try {
			SimpleQuery simpleQuery;

			//get config description infomation
			sql = "SELECT EW.ATTRIBUTE5 NEWCONDESC,EOA.ATTRIBUTE65 CONDESC\n" +
					"  FROM ETS_OBJECT_ATTRIBUTE EOA, ETS_WORKORDER EW, ETS_OBJECT EO\n" +
					" WHERE EO.WORKORDER_OBJECT_NO = EW.WORKORDER_OBJECT_NO\n" +
					"   AND EO.WORKORDER_OBJECT_NO *= EOA.OBJECT_NO\n" +
					" AND EW.WORKORDER_NO=?";
			sqlArgs.add(workorderNo);
			sqlModel.setSqlStr(sql);
			sqlModel.setArgs(sqlArgs);

			simpleQuery = new SimpleQuery(sqlModel, conn);
			simpleQuery.executeQuery();

			if (simpleQuery.hasResult()) {
				Row row = simpleQuery.getSearchResult().getRow(0);
				conDesc = row.getStrValue("CONDESC");
				newConDesc = row.getStrValue("NEWCONDESC");
			}

			//get config information

			sqlModel = orderModel.getNewConfigModel(workorderNo);
//			if (prjId.equals(DictConstant.ZERO_PRJ_ID)) {
//				sqlModel = orderModel.getNewConfigModelByPrjId(workorderNo,prjId);
//			}

			simpleQuery = new SimpleQuery(sqlModel, conn);
			simpleQuery.executeQuery();

			m_sb.append("<new_config description=\"").append(PDAUtil.xmlFormat(newConDesc)).append("\">\n");

			if (simpleQuery.hasResult()) {
				RowSet rowSet = simpleQuery.getSearchResult();
				Row row = null;
				for (int i = 0; i < rowSet.getSize(); i++) {
					row = rowSet.getRow(i);
					m_sb.append("<item name=\"").append(PDAUtil.xmlFormat(row.getStrValue("ITEM_NAME"))).append("\"");
					m_sb.append(" type=\"").append(PDAUtil.xmlFormat(row.getStrValue("ITEM_SPEC"))).append("\"");
					m_sb.append(" qty=\"").append(row.getStrValue("ITEM_QTY")).append("\"");
					m_sb.append("/>\n");
				}
			}
			m_sb.append("</new_config>\n");

			/**
			 * 判断：当工单类型是巡检工单时，根据attribute7下发基站配置信息
			 * 修改人：wangwenbin
			 * 于2006-5-11修改
			 */
			if (orderType.equals("巡检工单")) {
				String attr_sql = "SELECT EW.ATTRIBUTE7  FROM ETS_WORKORDER EW WHERE EW.WORKORDER_NO=?";
				sqlArgs.clear();
				sqlArgs.add(workorderNo);
				sqlModel.setSqlStr(attr_sql);
				sqlModel.setArgs(sqlArgs);
				simpleQuery = new SimpleQuery(sqlModel, conn);
				simpleQuery.executeQuery();
				if (simpleQuery.hasResult()) {
					Row row = simpleQuery.getSearchResult().getRow(0);
					itemCategory = row.getStrValue("ATTRIBUTE7");
					if (StrUtil.isEmpty(itemCategory)) {
						itemCategory = AmsOrderConstant.scanAllItemCategory;
					}
				}
				if (itemCategory.equalsIgnoreCase("ALL")) {
					itemCategory = AmsOrderConstant.scanAllItemCategory;
				}
			}


			Logger.logInfo("下载工单--<old_config>：" + sql);

			sqlModel = orderModel.getCurrentConfigModel(objNo, itemCategory);
			if (prjId.equals(DictConstant.ZERO_PRJ_ID)) {
				sqlModel = orderModel.getCurrentConfigModelByPrjId(objNo, itemCategory, prjId);
		    }
			simpleQuery = new SimpleQuery(sqlModel, conn);
			simpleQuery.executeQuery();


			m_sb.append("<old_config description=\"").append(PDAUtil.xmlFormat(conDesc)).append("\">\n");
			if (simpleQuery.hasResult()) {
				RowSet rowSet = simpleQuery.getSearchResult();
				Row row;
				for (int i = 0; i < rowSet.getSize(); i++) {
					row = rowSet.getRow(i);
					m_sb.append("<item name=\"").append(PDAUtil.xmlFormat(row.getStrValue("ITEM_NAME"))).append("\"");
					m_sb.append(" type=\"").append(PDAUtil.xmlFormat(row.getStrValue("ITEM_SPEC"))).append("\"");
					m_sb.append(" qty=\"").append(row.getStrValue("NCOUNT")).append("\"");
					m_sb.append("/> \n");
				}
			}
			m_sb.append("</old_config>\n");

		} catch (QueryException e) {
			Logger.logError("获取工单地点配置信息出错：" + e.toString());
		} catch (ContainerException e) {
			Logger.logError("获取工单地点配置信息出错：" + e.toString());
		}
		return m_sb;
	}

	/**
	 * 检查工单是否已经被提交
	 * @param conn        Connection
	 * @param workorderNo String
	 * @return boolean
	 */
	private boolean hasSubmit(Connection conn, String workorderNo) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();

		String sqlStr = "SELECT COUNT(1) NCOUNT\n" +
				"  FROM ETS_WORKORDER EW\n" +
				" WHERE EW.WORKORDER_NO = ?\n" +
				"   AND WORKORDER_FLAG=?";
		sqlArgs.add(workorderNo);
		sqlArgs.add(DictConstant.WOR_STATUS_DOWNLOAD);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		String count = "";

		try {
			SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
			simpleQuery.executeQuery();

			if (simpleQuery.hasResult()) {
				Row row = simpleQuery.getSearchResult().getRow(0);
				count = row.getStrValue("NCOUNT");
			}

		} catch (QueryException e) {
			Logger.logError(e);
			e.printStackTrace();
		} catch (ContainerException e) {
			Logger.logError(e);
			e.printStackTrace();
		}


		return count.equals("0");
	}


	private String getObjectCode(Connection conn, String objectNo) throws QueryException, ContainerException {

		String objectCode = "";
		String sql = "SELECT EO.WORKORDER_OBJECT_CODE\n" +
				"  FROM ETS_OBJECT EO\n" +
				" WHERE EO.WORKORDER_OBJECT_NO = ?";
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		sqlArgs.add(objectNo);
		sqlModel.setSqlStr(sql);
		sqlModel.setArgs(sqlArgs);

		SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
		simpleQuery.executeQuery();
		if (simpleQuery.hasResult()) {
			objectCode = simpleQuery.getFirstRow().getStrValue("WORKORDER_OBJECT_CODE");
		}

		return objectCode;
	}
	private boolean exceedMaxCount(Connection conn, String objectNo) {
        boolean isValidate = false;
        SQLModel sqlModel = new SQLModel();
        String sqlStr =
                "SELECT COUNT(1)\n" +
                        "  FROM AMS_OBJECT_ADDRESS AOA, ETS_ITEM_INFO EII\n" +
                        " WHERE EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                        "   AND AOA.OBJECT_NO = ?";

        List sqlArgs = new ArrayList();
        sqlArgs.add(objectNo);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        try {
            simpleQuery.executeQuery();
            if (simpleQuery.hasResult()) {
                int count = Integer.parseInt(String.valueOf(simpleQuery.getFirstRow().getStrValue(0)));
                isValidate = count > SinoConfig.getMaxItemCount();
            }
        } catch (QueryException e) {
            e.printLog();
        } catch (ContainerException e) {
            e.printLog();
        }

        return isValidate;
    }
	private boolean hasRecord(Connection conn, String objectNo, String groupId, String scan_category) throws QueryException, ContainerException {
		//10-新增，11-已下发,12-已下载，13-已完成,14-已核实,15-已取消

		String sql = "", catSql = "", category = "";
		boolean flag = false;
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();

		sql = "SELECT EW.*\n" +
				"  FROM ETS_WORKORDER EW, ETS_OBJECT EO\n" +
				" WHERE EW.WORKORDER_OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
				"   AND EW.WORKORDER_TYPE = '12'\n" +
				"   AND EW.WORKORDER_FLAG IN ('11','12','13')\n" +
				"   AND EW.WORKORDER_OBJECT_NO = ?\n" +
				"   AND ( " + SyBaseSQLUtil.isNull() + "  OR EW.ATTRIBUTE7=?)";

		sqlArgs.clear();
		sqlArgs.add(objectNo);
		sqlArgs.add(scan_category);
		sqlArgs.add(scan_category);

		sqlModel.setSqlStr(sql);
		sqlModel.setArgs(sqlArgs);
		SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
		simpleQuery.executeQuery();
		flag = simpleQuery.hasResult();

		return flag;
	}

	private boolean validLocation(Connection conn, String objectNo, String groupId) throws QueryException {
		String sql = "SELECT *\n" +
				"  FROM ETS_OBJECT EO, ETS_OBJECT_CAT_GROUP EOCG\n" +
				" WHERE EO.OBJECT_CATEGORY = EOCG.OBJECT_CATEGORY\n" +
				"   AND EO.WORKORDER_OBJECT_NO = ?\n" +
				"   AND EOCG.GROUP_ID = ?";
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		sqlArgs.add(objectNo);
		sqlArgs.add(groupId);
		sqlModel.setSqlStr(sql);
		sqlModel.setArgs(sqlArgs);

		SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
		simpleQuery.executeQuery();

//        return simpleQuery.hasResult();//todo 检查
		return true;
	}

	/**
	 * 取该组别对应的专业,
     * 新流程中去除组别专业的关系
	 * @param groupId
	 * @param conn
	 * @return String
	 */
	private String getCatDescByGroup(String groupId, Connection conn) {
		String catDesc = "", cat = "";
//		String sql = "SELECT SG.CATEGORY FROM SF_GROUP SG WHERE SG.GROUP_ID = ?";
//		SQLModel sqlModel = new SQLModel();
//		List sqlArgs = new ArrayList();
//		sqlArgs.add(groupId);
//		sqlModel.setSqlStr(sql);
//		sqlModel.setArgs(sqlArgs);
//
//		try {
//			SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
//			simpleQuery.executeQuery();
//			if (simpleQuery.hasResult()) {
//				cat = simpleQuery.getFirstRow().getStrValue("CATEGORY");
//			}
//			if (cat.equals("BTS")) {
//				catDesc = "基站专业";
//			} else if (cat.equals("EXCHG")) {
//				catDesc = "交换专业";
//			} else if (cat.equals("BSC")) {
//				catDesc = "监控专业";
//			} else if (cat.equals("TRANS")) {
//				catDesc = "传输专业";
//			} else if (cat.equals("DATA")) {
//				catDesc = "数据专业";
//			} else if (cat.equals("ELEC")) {
//				catDesc = "电力专业";
//			} else if (cat.equals("NETOP")) {
//				catDesc = "网优专业";
//			} else if (cat.equals("OTHERS")) {
//				catDesc = "其他专业";
//			}
//		} catch (Exception e) {
//			Logger.logError(e.toString());
//		}
		return catDesc;
	}


	/**
	 * PDA创建工单（工单批、工单、工单进度）
	 * @param conn
	 * @param workorderBatchDTO
	 * @param workorderDTO
	 * @return
	 * @throws DataHandleException
	 */
	private boolean createOrder(Connection conn, EtsWorkorderBatchDTO workorderBatchDTO, EtsWorkorderDTO workorderDTO) throws DataHandleException {
		boolean flag = false;
		PDAOrderUtilModel pdaOrderUtilModel = new PDAOrderUtilModel();
		List sqlModList = new ArrayList();

		sqlModList.add(pdaOrderUtilModel.getInserBatchModel(workorderBatchDTO));
		sqlModList.add(pdaOrderUtilModel.getInsertOrderModel(workorderDTO));
		sqlModList.add(pdaOrderUtilModel.getAddProcessModel(workorderBatchDTO.getWorkorderBatch(), workorderDTO.getWorkorderNo()));

		flag = DBOperator.updateBatchRecords(sqlModList, conn);

		return flag;
	}

	/**
	 * 取扫描结果
	 * @param xml
	 * @param barcodeXml
	 * @return
	 */
	private EtsItemInfoDTO getEtsItemInfo(XmlUtil xml, Element barcodeXml) {

		String qty = xml.getElementAttrValue(barcodeXml, "quantity");
		if (StrUtil.isEmpty(qty)) qty = "1";

		EtsItemInfoDTO etsItemInfoDTO = new EtsItemInfoDTO();
		etsItemInfoDTO.setBarcode(xml.getElementAttrValue(barcodeXml, "code"));
		etsItemInfoDTO.setItemCode(xml.getElementAttrValue(barcodeXml, "item_code"));
		etsItemInfoDTO.setItemName(xml.getElementAttrValue(barcodeXml, "name"));
		etsItemInfoDTO.setItemSpec(xml.getElementAttrValue(barcodeXml, "type"));
		etsItemInfoDTO.setItemCategory(xml.getElementAttrValue(barcodeXml, "category"));
		etsItemInfoDTO.setItemQty(qty);
		etsItemInfoDTO.setAddressId(xml.getElementAttrValue(barcodeXml, "address_id"));
		etsItemInfoDTO.setBoxNo(xml.getElementAttrValue(barcodeXml, "box_no"));
		etsItemInfoDTO.setNetUnit(xml.getElementAttrValue(barcodeXml, "net_unit"));
		etsItemInfoDTO.setParentBarcode(xml.getElementAttrValue(barcodeXml, "parent_code"));
		etsItemInfoDTO.setResponsibilityDept(xml.getElementAttrValue(barcodeXml, "assign_groupid"));
		etsItemInfoDTO.setResponsibilityUser(xml.getElementAttrValue(barcodeXml, "assign_userid"));
		etsItemInfoDTO.setMaintainUser(xml.getElementAttrValue(barcodeXml, "username"));
		etsItemInfoDTO.setManufacturerId(xml.getElementAttrValue(barcodeXml, "manufacturerId")); //新增
		etsItemInfoDTO.setShare(xml.getElementAttrValue(barcodeXml, "isShare"));
		etsItemInfoDTO.setPower(xml.getElementAttrValue(barcodeXml, "power"));

		return etsItemInfoDTO;
	}

	/**
	 * * 取工单的attribute7（扫描专业）
	 * @param conn        数据库连接
	 * @param workorderNo 工单号
	 * @return String
	 * @throws QueryException
	 * @throws ContainerException
	 */
	public String getItemCategoryOfOrder(Connection conn, String workorderNo) throws QueryException, ContainerException {
		String itemCategory = "";
		PDAOrderUtilModel pdaOrderUtilModel = new PDAOrderUtilModel();
		SQLModel sqlModel = pdaOrderUtilModel.getAttribute7(workorderNo);
		SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
		simpleQuery.executeQuery();
		if (simpleQuery.hasResult()) {
			Row row = simpleQuery.getFirstRow();
			itemCategory = row.getStrValue("ATTRIBUTE7");
		}
		if (itemCategory.equalsIgnoreCase("ALL")) {
			itemCategory = AmsOrderConstant.scanAllItemCategory;
		}
		return itemCategory;
	}

	/**
	 * 取工单的成本中心代码
	 * @param conn
	 * @param workorderNo
	 * @return
	 * @throws QueryException
	 * @throws ContainerException
	 */
	public String getCostCenterOfOrder(Connection conn, String workorderNo) throws QueryException, ContainerException {
		String costCenterCode = "";
		PDAOrderUtilModel pdaOrderUtilModel = new PDAOrderUtilModel();
		SQLModel sqlModel = pdaOrderUtilModel.getCostCenter(workorderNo);
		SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
		simpleQuery.executeQuery();
		if (simpleQuery.hasResult()) {
			Row row = simpleQuery.getFirstRow();
			costCenterCode = row.getStrValue("COST_CENTER_CODE");
		}

		return costCenterCode;
	}

	/**
	 * 查询该工单下发组别是否有对应的MIS部门
	 * @param conn        数据库连接
	 * @param workorderNo 工单号
	 * @return boolean
	 * @throws QueryException
	 */
	public boolean hasMatchedMISDetp(Connection conn, String workorderNo) throws QueryException {
		PDAOrderUtilModel pdaOrderUtilModel = new PDAOrderUtilModel();
		SQLModel sqlModel = pdaOrderUtilModel.getExistsMisGroupModel(workorderNo);
		SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
		simpleQuery.executeQuery();
		return simpleQuery.hasResult();
	}
}
