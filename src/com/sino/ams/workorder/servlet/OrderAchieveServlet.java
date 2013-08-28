package com.sino.ams.workorder.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.ams.constant.AmsOrderConstant;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dto.EtsWorkorderDTO;
import com.sino.ams.workorder.dto.EtsWorkorderDtlDTO;
import com.sino.ams.workorder.model.OrderDiffModel;
import com.sino.ams.workorder.model.WorkorderModel;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;

/**
 * User: zhoujs
 * Date: 2007-11-8
 * Time: 11:51:20
 * Function:工单归档处理Servlet
 * 1.写差异表ets_workorder_diff_dtl（巡检工单）
 * 2.处理扫描回来的其他数据。
 * 3.删除接口表数据
 * 4.记录变动历史
 */
public class OrderAchieveServlet extends BaseServlet {
//	private List changedBarcodes = new ArrayList();//变动条码清单

	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		List changedBarcodes = new ArrayList();
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		Connection conn = null;
		String action = StrUtil.nullToString(req.getParameter("act"));

		String currentAddr = "";//当前地点ID
		String ztAddr = "";//在途地点ID
		String transAddr = "";//搬迁地点ID;
		
		try {
			forwardURL = "/public/windowClose.jsp?retValue=refresh";
			SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(req);
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(EtsWorkorderDTO.class.getName());
			EtsWorkorderDTO dtoParameter = (EtsWorkorderDTO) req2DTO.getDTO(req);
			String workorderNo = dtoParameter.getWorkorderNo();
			conn = DBManager.getDBConnection();

			SQLModel sqlModel = new SQLModel();
			OrderDiffModel orderDiffModel = new OrderDiffModel();
			WorkorderModel orderModel = new WorkorderModel();
			DTOSet dtlSet = new DTOSet();
			List sqlModList = new ArrayList();
			boolean autoCommit = conn.getAutoCommit();

			String diffReason = StrUtil.nullToString(req.getParameter("diffReason"));
			String responsibilityUser = StrUtil.nullToString(req.getParameter("respUser"));
			if (action.equals("confirm")) { //归档
				conn.setAutoCommit(false);
				if (dtoParameter.getWorkorderType().equals(DictConstant.ORDER_TYPE_CHECK)||dtoParameter.getWorkorderType().equals(DictConstant.ORDER_TYPE_HDV)) { //巡检、交接
					String[] barcodes = req.getParameterValues("barcodes");
					String[] results = req.getParameterValues("dealResult");
					String[] specialityDeptCodes=req.getParameterValues("specialDept");
					String[] itemStatus = req.getParameterValues("itemStatus");
					String[] remarks = req.getParameterValues("remarks");
					Map mp = new HashMap();
					Map mp_speciality_dept = new HashMap();
					if (barcodes != null) {
						for (int i = 0; i < barcodes.length; i++) {
							String barcode = barcodes[i];
							String dealResult = results[i];
							String remark = remarks[i];
							mp.put(barcode, dealResult);
							mp_speciality_dept.put(barcode, specialityDeptCodes[i]);
//                            changedBarcodes.add(barcode);
						}

						List sqlModels = doAchieveCheck(conn, userAccount, dtoParameter, mp, mp_speciality_dept, changedBarcodes);//归档
						DBOperator.updateBatchRecords(sqlModels, conn);
						sqlModels = getInsertDiffResult(workorderNo, barcodes, results, specialityDeptCodes, itemStatus, diffReason,remarks);//记录差异结果
						DBOperator.updateBatchRecords(sqlModels, conn);
					}
					
					//零购转资特殊处理
					if(dtoParameter.getWorkorderType().equals(DictConstant.ORDER_TYPE_HDV)&&dtoParameter.getPrjId().equals(DictConstant.ZERO_PRJ_ID)){
						 String wn=dtoParameter.getWorkorderNo();
						 SQLModel sqM = new SQLModel();
						 SQLModel sqM2 = new SQLModel();
						 OrderDiffModel odModel = new OrderDiffModel();
						 sqM=odModel.updateEII(workorderNo);
						 sqM2=odModel.updateEtsItemTurn(workorderNo);
						 DBOperator.updateRecord(sqM, conn);
						 DBOperator.updateRecord(sqM2, conn);
					}
					
				} else if (dtoParameter.getWorkorderType().equals(DictConstant.ORDER_TYPE_TRANS)) {//搬迁工单
					String transObjectNo = dtoParameter.getAttribute1();
					transAddr = getAddress(transObjectNo,conn);
					if (transAddr.equals("")) {
						Logger.logError("地点未定义!");
					} else {
						sqlModel = orderDiffModel.getUpdateTransModel(transAddr, dtoParameter.getWorkorderObjectNo());
						DBOperator.updateRecord(sqlModel, conn);
					}
				} else {//除巡检、搬迁外的其他工单
					List sqlModels = doAchieve(conn, userAccount, workorderNo, StrUtil.nullToString(dtoParameter.getWorkorderObjectNo()), changedBarcodes);
					DBOperator.updateBatchRecords(sqlModels, conn);
				}

				sqlModel = orderModel.getDeleteWorkorderInterModel(workorderNo);//删除接口表数据
				sqlModList.add(sqlModel);

				sqlModel = orderModel.getOrderAchieveModel(workorderNo, userAccount);//更新工单状态至已归档
				sqlModList.add(sqlModel);

				sqlModel = orderModel.getUpdateOrderDiffModel(workorderNo, responsibilityUser, diffReason);
				sqlModList.add(sqlModel);

				sqlModel = orderModel.getUpdateOrderProcessModel(workorderNo, DictConstant.WORKORDER_NODE_ACHIEVE, true); //更新工单进度
				sqlModList.add(sqlModel);

				sqlModel = orderModel.getBackupItemModel(changedBarcodes, workorderNo, userAccount);//备份变动设备信息
				sqlModList.add(sqlModel);

				DBOperator.updateBatchRecords(sqlModList, conn);
				conn.commit();
				conn.setAutoCommit(autoCommit);

			} else if (action.equals("match")) {//自动归档
				String[] workorderNos = req.getParameterValues("workorderNo");
				String[] objectNos = req.getParameterValues("objectNo");
				String[] hasDiffs = req.getParameterValues("hasDiff");
				conn.setAutoCommit(false);
				if (workorderNos != null && workorderNos.length > 0) {
					List sqlModels = null;
					for (int i = 0; i < workorderNos.length; i++) {
						String orderNo = workorderNos[i];
						String objectNo = objectNos[i];
						String hasDiff = hasDiffs[i];
						if (hasDiff.equals("有")) {
							break;
						}
						sqlModels = new ArrayList();
						/**
						 * 归档
						 */
						sqlModels = doAchieve(conn, userAccount, orderNo, objectNo, changedBarcodes);
//                        DBOperator.updateBatchRecords(sqlModels, conn);
						/**
						 * 删除接口表数据
						 */
						sqlModel = orderModel.getDeleteWorkorderDtlModel(workorderNo);
//                        DBOperator.updateRecord(sqlModel, conn);
						sqlModels.add(sqlModel);
						/**
						 * 更新工单状态至已归档
						 */
						sqlModel = orderModel.getOrderAchieveModel(workorderNo, userAccount);
//                        DBOperator.updateRecord(sqlModel, conn);
						sqlModels.add(sqlModel);
						/**
						 * 更新工单进度
						 */
						sqlModel = orderModel.getUpdateOrderProcessModel(workorderNo, DictConstant.WORKORDER_NODE_ACHIEVE, true);
//                        DBOperator.updateRecord(sqlModel, conn);
						sqlModels.add(sqlModel);
						/**
						 * 备份变动设备信息
						 */
						boolean success = DBOperator.updateBatchRecords(sqlModels, conn);
//                        System.out.println(orderNo + ":= " + success);
					}

					forwardURL = "/servlet/com.sino.ams.workorder.servlet.PendingOrderServlet";
				} 
				conn.commit();
				conn.setAutoCommit(autoCommit);
			} else if (action.equals("workorderBack")) { //巡检工单退回
				Logger.logInfo("退回工单:"+workorderNo);
				
				//修改工单状态
				sqlModel = orderModel.getOrderDeployModel(workorderNo);
				sqlModList.add(sqlModel);					
				
				//删除工单明细行
				sqlModel = orderModel.getDeleteWorkorderDtlModel(workorderNo);
				sqlModList.add(sqlModel);

                	//删除工单接口明细行
                sqlModel = orderModel.getDeleteWorkorderInterModel(workorderNo);
				sqlModList.add(sqlModel);

				DBOperator.updateBatchRecords(sqlModList, conn);
			} else {
				forwardURL = URLDefineList.ERROR_PAGE;
			}
		} catch (PoolException e) {
			forwardURL = URLDefineList.ERROR_PAGE;
			e.printStackTrace();
		} catch (DTOException e) {
			forwardURL = URLDefineList.ERROR_PAGE;
			e.printStackTrace();
		} catch (QueryException e) {
			forwardURL = URLDefineList.ERROR_PAGE;
			e.printStackTrace();
		} catch (ContainerException e) {
			forwardURL = URLDefineList.ERROR_PAGE;
			e.printStackTrace();
		} catch (CalendarException e) {
			forwardURL = URLDefineList.ERROR_PAGE;
			e.printStackTrace();
		} catch (DataHandleException e) {
			forwardURL = URLDefineList.ERROR_PAGE;
			e.printStackTrace();
		} catch (SQLException e) {
			forwardURL = URLDefineList.ERROR_PAGE;
			e.printStackTrace();
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			if (forwardURL.equals("")) {
				forwarder.forwardView(URLDefineList.WINDOW_CLOSE_PAGE);
			} else {
				forwarder.forwardView(forwardURL);
			}
		}
	}

	/**
	 * 工单归档
	 * @param conn
	 * @param user
	 * @param workorderNo
	 * @param objectNo
	 * @return
	 * @throws QueryException
	 * @throws ContainerException
	 * @throws CalendarException
	 */
	private List doAchieve(Connection conn, SfUserDTO user, String workorderNo, String objectNo, List changedBarcodes) throws QueryException, ContainerException, CalendarException {

		List sqlModels = new ArrayList();
		SQLModel sqlModel = new SQLModel();
		OrderDiffModel orderDiffModel = new OrderDiffModel();
		sqlModel = orderDiffModel.getScanDiffDtlModel(workorderNo);
		DTOSet dtlSet = new DTOSet();
		SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
		simpleQuery.setDTOClassName(EtsWorkorderDtlDTO.class.getName());
		simpleQuery.executeQuery();
		if (simpleQuery.hasResult()) {
			dtlSet = simpleQuery.getDTOSet();
		}
		EtsWorkorderDtlDTO workorderDtl = new EtsWorkorderDtlDTO();
		String addressZT = getAddrZT(conn, user.getOrganizationId());
		String addressId = ""; //但前扫描地点

		for (int i = 0; i < dtlSet.getSize(); i++) {
			workorderDtl = (EtsWorkorderDtlDTO) dtlSet.getDTO(i);
			addressId = getAddress(objectNo,conn);
			String scanStatus = StrUtil.nullToString(workorderDtl.getItemStatus());
			boolean hasRecord = hasRecord(conn, workorderDtl.getBarcode());
			boolean hasRecordInCurObj = hasRecord(conn, workorderDtl.getBarcode(), workorderDtl.getWorkorderObjectNo());

			if (scanStatus.equals(DictConstant.SCAN_STATUS_NEW)) {//新增---"0"
				/**
				 * 系统没有:添加设备至ets_item_info，地点为当前扫描地点
				 * 系统有：1.最后地点更新时间>工单提交时间   不处理
				 *         2.最后地点更新时间<工单提交时间   更新到当前地点
				 */
				if (hasRecord) {
					if (!isUpdateAfterUpload(conn, workorderDtl.getBarcode(), workorderDtl.getUploadDate())) {
						sqlModels.add(orderDiffModel.getUpdateItemAddrModel(workorderDtl, addressId));
						changedBarcodes.add(workorderDtl.getBarcode());
					}

				} else {//新增设备
					sqlModels.add(orderDiffModel.getInsertItemModel(workorderDtl, addressId, user));
				}

			} else if (scanStatus.equals(DictConstant.SCAN_STATUS_EXISTS)) {//扫描到---"5"
				/**
				 * 系统中当前地点有：不处理
				 * 系统中当前地点没有：1.最后地点更新时间>工单提交时间   不处理
				 *                     2.最后地点更新时间<工单提交时间   更新到当前地点
				 */
				if (hasRecord) {
					if (!hasRecordInCurObj) {
						if (!isUpdateAfterUpload(conn, workorderDtl.getBarcode(), workorderDtl.getUploadDate())) {
							sqlModels.add(orderDiffModel.getUpdateItemAddrModel(workorderDtl, addressId));
							changedBarcodes.add(workorderDtl.getBarcode());
						}
					}

				} else {  //add by zhoujs at 2008-02-17 21:03
					sqlModels.add(orderDiffModel.getInsertItemModel(workorderDtl, addressId, user));
				}

			} else if (scanStatus.equals(DictConstant.SCAN_STATUS_NONE)) {//未扫描到--"6"
				/**
				 * 系统中当前地点无: 不处理
				 * 系统中但前地点有：设为在途
				 */
				if (hasRecordInCurObj) {
					sqlModels.add(orderDiffModel.getUpdateItemAddrModel(workorderDtl, addressZT));
					changedBarcodes.add(workorderDtl.getBarcode());
				}


			} else if (scanStatus.equals(DictConstant.SCAN_STATUS_OFFLINE)) {//换下设备---"7"
				/**
				 * 系统当前地点有：设为在途
				 * 系统当前地点无：不处理
				 */
				if (hasRecordInCurObj) {
					sqlModels.add(orderDiffModel.getUpdateItemAddrModel(workorderDtl, addressZT));
					changedBarcodes.add(workorderDtl.getBarcode());
				} else if (!hasRecord) {
					sqlModels.add(orderDiffModel.getInsertItemModel(workorderDtl, addressZT, user));
				}

			} else if (scanStatus.equals(DictConstant.SCAN_STATUS_REMAIN)) { //剩余设备---"8"
				/**
				 * 系统中无：插入在途库
				 * 系统中有：不处理
				 */
				if (!hasRecord) {
					sqlModels.add(orderDiffModel.getInsertItemInZTModel(workorderDtl, addressZT, user));
					changedBarcodes.add(workorderDtl.getBarcode());
				}

			}

		}
		return sqlModels;
	}

	/**
	 * 巡检工单归档
	 * @param conn
	 * @param user
	 * @param workorderDTO
	 * @param mp
	 * @return
	 * @throws QueryException
	 * @throws ContainerException
	 * @throws CalendarException
	 */
	private List doAchieveCheck(Connection conn, SfUserDTO user, EtsWorkorderDTO workorderDTO, Map mp, Map mp_speciality_dept, List changedBarcodes) throws QueryException, ContainerException, CalendarException {
		String workorderNo = workorderDTO.getWorkorderNo();
		String objectNo = StrUtil.nullToString(workorderDTO.getWorkorderObjectNo());

		List sqlModels = new ArrayList();
		SQLModel sqlModel = new SQLModel();
		OrderDiffModel orderDiffModel = new OrderDiffModel();
		sqlModel = orderDiffModel.getScanDiffDtlModel(workorderNo);
		DTOSet dtlSet = new DTOSet();
		SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
		simpleQuery.setDTOClassName(EtsWorkorderDtlDTO.class.getName());
		simpleQuery.executeQuery();
		if (simpleQuery.hasResult()) {
			dtlSet = simpleQuery.getDTOSet();
		}
		EtsWorkorderDtlDTO workorderDtl = new EtsWorkorderDtlDTO();
		String addressZT = getAddrZT(conn, user.getOrganizationId());
		String addressId = ""; //但前扫描地点

		for (int i = 0; i < dtlSet.getSize(); i++) {
			workorderDtl = (EtsWorkorderDtlDTO) dtlSet.getDTO(i);
			String barcode = workorderDtl.getBarcode();

			if (mp.keySet().contains(barcode)) {
				String confirmResult = mp.get(barcode).toString();
				String deptCode = mp_speciality_dept.get(barcode).toString();
				workorderDtl.setSpecialityDept(deptCode);
				
//                confirmResult="系统结果为准";
				if (!confirmResult.equals(AmsOrderConstant.CONFIRM_SYSTEM)) {
					addressId = getAddress(workorderDTO.getWorkorderObjectNo(),conn);//当前地点
					String scanStatus = StrUtil.nullToString(workorderDtl.getItemStatus());
					boolean hasRecord = hasRecord(conn, workorderDtl.getBarcode());
					boolean hasRecordInCurObj = hasRecord(conn, workorderDtl.getBarcode(), workorderDtl.getWorkorderObjectNo());

					if (scanStatus.equals(DictConstant.SCAN_STATUS_NEW)) {//新增
						/**
						 * 系统没有:添加设备至ets_item_info，地点为当前扫描地点
						 * 系统有：1.最后地点更新时间>工单提交时间   不处理
						 *         2.最后地点更新时间<工单提交时间   更新到当前地点
						 */
						if (hasRecord) {
							if (!isUpdateAfterUpload(conn, workorderDtl.getBarcode(), workorderDtl.getUploadDate())) {
								sqlModels.add(orderDiffModel.getUpdateItemAddrModel(workorderDtl, addressId));
								changedBarcodes.add(barcode);
							}
						} else {
							sqlModels.add(orderDiffModel.getInsertItemModel(workorderDtl, addressId, user));
//                            changedBarcodes.add(barcode);
						}

					} else if (scanStatus.equals(DictConstant.SCAN_STATUS_EXISTS)) {//扫描到
						/**
						 * 系统中当前地点有：不处理
						 * 系统中当前地点没有：1.最后地点更新时间>工单提交时间   不处理
						 *                     2.最后地点更新时间<工单提交时间   更新到当前地点
						 */
						if (hasRecord) {
//							if (!hasRecordInCurObj) {
								if (!isUpdateAfterUpload(conn, workorderDtl.getBarcode(), workorderDtl.getUploadDate())) {
									sqlModels.add(orderDiffModel.getUpdateItemAddrModel(workorderDtl, addressId));
									changedBarcodes.add(barcode);
								}
//							}
						} else {
							sqlModels.add(orderDiffModel.getInsertItemModel(workorderDtl, addressId, user));
//                            changedBarcodes.add(barcode);
						}

					} else if (scanStatus.equals(DictConstant.SCAN_STATUS_NONE)) {//未扫描到
						/**
						 * 系统中当前地点无: 不处理
						 * 系统中但前地点有：设为在途
						 */
                    if(!workorderDTO.getWorkorderType().equals(DictConstant.ORDER_TYPE_HDV)){
						if (hasRecordInCurObj) {
							sqlModels.add(orderDiffModel.getUpdateItemAddrModel(workorderDtl, addressZT));
							changedBarcodes.add(barcode);
						}
                    }


					} else if (scanStatus.equals(DictConstant.SCAN_STATUS_OFFLINE)) {//换下设备 --不应该有这种状态
						/**
						 * 系统当前地点有：设为在途
						 * 系统当前地点无：不处理
						 */
						if (hasRecordInCurObj) {
							sqlModels.add(orderDiffModel.getUpdateItemAddrModel(workorderDtl, addressZT));
							changedBarcodes.add(barcode);
						}

					} else if (scanStatus.equals(DictConstant.SCAN_STATUS_REMAIN)) { //剩余设备 -不应该有这种状态
						/**
						 * 系统中无：插入在途库
						 * 系统中有：不处理
						 */
						if (!hasRecord) {
							sqlModels.add(orderDiffModel.getInsertItemInZTModel(workorderDtl, addressZT, user));
							changedBarcodes.add(barcode);
						}

					}
				}
			}
		}

		return sqlModels;
	}


	/**
	 * 取指定地市的在途库
	 * @param conn
	 * @param organizationId
	 * @return
	 * @throws QueryException
	 * @throws ContainerException
	 */
	private String getAddrZT(Connection conn, int organizationId) throws QueryException, ContainerException {
		String ztAddr = "";
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT AOA.ADDRESS_ID\n" +
				"  FROM ETS_OBJECT EO, AMS_OBJECT_ADDRESS AOA\n" +
				" WHERE EO.WORKORDER_OBJECT_NO = AOA.OBJECT_NO\n" +
				"   AND EO.OBJECT_CATEGORY = ?\n" +
				"   AND EO.ORGANIZATION_ID = ?";

		sqlArgs.add(DictConstant.INV_ON_WAY);
		sqlArgs.add(organizationId);

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
		simpleQuery.executeQuery();
		if (simpleQuery.hasResult()) {
			ztAddr = simpleQuery.getFirstRow().getStrValue("ADDRESS_ID");
		}

		return ztAddr;
	}

	/**
	 * 查询系统中是否有该条码
	 * @param conn
	 * @param barcode
	 * @return
	 * @throws QueryException
	 * @throws ContainerException
	 */
	private boolean hasRecord(Connection conn, String barcode) throws QueryException {
		boolean hasRecord = false;
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT EII.BARCODE FROM ETS_ITEM_INFO EII WHERE EII.BARCODE = ?";

		sqlArgs.add(barcode);

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
		simpleQuery.executeQuery();
		if (simpleQuery.hasResult()) {
			hasRecord = true;
		}

		return hasRecord;
	}

	private boolean hasRecord(Connection conn, String barcode, String objectNo) throws QueryException {
		boolean hasRecord = false;
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT EII.BARCODE\n" +
				"  FROM ETS_ITEM_INFO EII, AMS_OBJECT_ADDRESS AOA\n" +
				" WHERE EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
				"   AND EII.BARCODE = ?\n" +
				"   AND AOA.OBJECT_NO = ?";


		sqlArgs.add(barcode);
		sqlArgs.add(objectNo);

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
		simpleQuery.executeQuery();
		if (simpleQuery.hasResult()) {
			hasRecord = true;
		}

		return hasRecord;
	}

	/**
	 * 查询条码最后更新地点时间是否大于工单提交时间
	 * @param conn
	 * @param barcode
	 * @param uploadDate
	 * @return
	 */
	private boolean isUpdateAfterUpload(Connection conn, String barcode, SimpleCalendar uploadDate) {
		boolean isAfter = false;

		return isAfter;
	}

	private List getInsertDiffResult(String workorderNo, String[] barcodes, String[] dealResults, String[] specialityDeptCodes, String[] itemStatus, String diffReason,String[] remarks) {
		List sqlModList = new ArrayList();
		SQLModel sqlModel = new SQLModel();
		OrderDiffModel orderDiffModel = new OrderDiffModel();
        sqlModList.add(orderDiffModel.getInsertDiffModel(workorderNo));
		for (int i = 0; i < barcodes.length; i++) {
			String barcode = barcodes[i];
			String dealResult = dealResults[i];
			String specialityDeptCode=getSpecialityDeptCode(specialityDeptCodes[i]);
			String itemStatu = itemStatus[i];
			String remark = remarks[i];
			sqlModList.add(orderDiffModel.getUpdateDiffModel(workorderNo, barcode, itemStatu, dealResult,specialityDeptCode, diffReason,remark));
		}
		return sqlModList;
	}

	private String getSpecialityDeptCode(String dept) {
		int start = dept.lastIndexOf("[");
    	int end = dept.lastIndexOf("]");
    	String code = "";
    	
    	if(start != -1 && end != -1 && start < end - 1) {
    		code = dept.substring(start + 1, end);
    	}
    	
    	return code;
	}
	
	private String getAddress(String workorderNo, Connection conn) throws QueryException, ContainerException {
		String address = "";
		OrderDiffModel orderDiffModel = new OrderDiffModel();
		SQLModel sqlModel = new SQLModel();
		sqlModel = orderDiffModel.getAddrId(workorderNo);
		SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
		simpleQuery.executeQuery();
		if (simpleQuery.hasResult()) {
			address = simpleQuery.getFirstRow().getStrValue("ADDRESS_ID");
		}
		return address;
	}
}
