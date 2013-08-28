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
 * Function:�����鵵����Servlet
 * 1.д�����ets_workorder_diff_dtl��Ѳ�칤����
 * 2.����ɨ��������������ݡ�
 * 3.ɾ���ӿڱ�����
 * 4.��¼�䶯��ʷ
 */
public class OrderAchieveServlet extends BaseServlet {
//	private List changedBarcodes = new ArrayList();//�䶯�����嵥

	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		List changedBarcodes = new ArrayList();
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		Connection conn = null;
		String action = StrUtil.nullToString(req.getParameter("act"));

		String currentAddr = "";//��ǰ�ص�ID
		String ztAddr = "";//��;�ص�ID
		String transAddr = "";//��Ǩ�ص�ID;
		
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
			if (action.equals("confirm")) { //�鵵
				conn.setAutoCommit(false);
				if (dtoParameter.getWorkorderType().equals(DictConstant.ORDER_TYPE_CHECK)||dtoParameter.getWorkorderType().equals(DictConstant.ORDER_TYPE_HDV)) { //Ѳ�졢����
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

						List sqlModels = doAchieveCheck(conn, userAccount, dtoParameter, mp, mp_speciality_dept, changedBarcodes);//�鵵
						DBOperator.updateBatchRecords(sqlModels, conn);
						sqlModels = getInsertDiffResult(workorderNo, barcodes, results, specialityDeptCodes, itemStatus, diffReason,remarks);//��¼������
						DBOperator.updateBatchRecords(sqlModels, conn);
					}
					
					//�㹺ת�����⴦��
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
					
				} else if (dtoParameter.getWorkorderType().equals(DictConstant.ORDER_TYPE_TRANS)) {//��Ǩ����
					String transObjectNo = dtoParameter.getAttribute1();
					transAddr = getAddress(transObjectNo,conn);
					if (transAddr.equals("")) {
						Logger.logError("�ص�δ����!");
					} else {
						sqlModel = orderDiffModel.getUpdateTransModel(transAddr, dtoParameter.getWorkorderObjectNo());
						DBOperator.updateRecord(sqlModel, conn);
					}
				} else {//��Ѳ�졢��Ǩ�����������
					List sqlModels = doAchieve(conn, userAccount, workorderNo, StrUtil.nullToString(dtoParameter.getWorkorderObjectNo()), changedBarcodes);
					DBOperator.updateBatchRecords(sqlModels, conn);
				}

				sqlModel = orderModel.getDeleteWorkorderInterModel(workorderNo);//ɾ���ӿڱ�����
				sqlModList.add(sqlModel);

				sqlModel = orderModel.getOrderAchieveModel(workorderNo, userAccount);//���¹���״̬���ѹ鵵
				sqlModList.add(sqlModel);

				sqlModel = orderModel.getUpdateOrderDiffModel(workorderNo, responsibilityUser, diffReason);
				sqlModList.add(sqlModel);

				sqlModel = orderModel.getUpdateOrderProcessModel(workorderNo, DictConstant.WORKORDER_NODE_ACHIEVE, true); //���¹�������
				sqlModList.add(sqlModel);

				sqlModel = orderModel.getBackupItemModel(changedBarcodes, workorderNo, userAccount);//���ݱ䶯�豸��Ϣ
				sqlModList.add(sqlModel);

				DBOperator.updateBatchRecords(sqlModList, conn);
				conn.commit();
				conn.setAutoCommit(autoCommit);

			} else if (action.equals("match")) {//�Զ��鵵
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
						if (hasDiff.equals("��")) {
							break;
						}
						sqlModels = new ArrayList();
						/**
						 * �鵵
						 */
						sqlModels = doAchieve(conn, userAccount, orderNo, objectNo, changedBarcodes);
//                        DBOperator.updateBatchRecords(sqlModels, conn);
						/**
						 * ɾ���ӿڱ�����
						 */
						sqlModel = orderModel.getDeleteWorkorderDtlModel(workorderNo);
//                        DBOperator.updateRecord(sqlModel, conn);
						sqlModels.add(sqlModel);
						/**
						 * ���¹���״̬���ѹ鵵
						 */
						sqlModel = orderModel.getOrderAchieveModel(workorderNo, userAccount);
//                        DBOperator.updateRecord(sqlModel, conn);
						sqlModels.add(sqlModel);
						/**
						 * ���¹�������
						 */
						sqlModel = orderModel.getUpdateOrderProcessModel(workorderNo, DictConstant.WORKORDER_NODE_ACHIEVE, true);
//                        DBOperator.updateRecord(sqlModel, conn);
						sqlModels.add(sqlModel);
						/**
						 * ���ݱ䶯�豸��Ϣ
						 */
						boolean success = DBOperator.updateBatchRecords(sqlModels, conn);
//                        System.out.println(orderNo + ":= " + success);
					}

					forwardURL = "/servlet/com.sino.ams.workorder.servlet.PendingOrderServlet";
				} 
				conn.commit();
				conn.setAutoCommit(autoCommit);
			} else if (action.equals("workorderBack")) { //Ѳ�칤���˻�
				Logger.logInfo("�˻ع���:"+workorderNo);
				
				//�޸Ĺ���״̬
				sqlModel = orderModel.getOrderDeployModel(workorderNo);
				sqlModList.add(sqlModel);					
				
				//ɾ��������ϸ��
				sqlModel = orderModel.getDeleteWorkorderDtlModel(workorderNo);
				sqlModList.add(sqlModel);

                	//ɾ�������ӿ���ϸ��
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
	 * �����鵵
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
		String addressId = ""; //��ǰɨ��ص�

		for (int i = 0; i < dtlSet.getSize(); i++) {
			workorderDtl = (EtsWorkorderDtlDTO) dtlSet.getDTO(i);
			addressId = getAddress(objectNo,conn);
			String scanStatus = StrUtil.nullToString(workorderDtl.getItemStatus());
			boolean hasRecord = hasRecord(conn, workorderDtl.getBarcode());
			boolean hasRecordInCurObj = hasRecord(conn, workorderDtl.getBarcode(), workorderDtl.getWorkorderObjectNo());

			if (scanStatus.equals(DictConstant.SCAN_STATUS_NEW)) {//����---"0"
				/**
				 * ϵͳû��:����豸��ets_item_info���ص�Ϊ��ǰɨ��ص�
				 * ϵͳ�У�1.���ص����ʱ��>�����ύʱ��   ������
				 *         2.���ص����ʱ��<�����ύʱ��   ���µ���ǰ�ص�
				 */
				if (hasRecord) {
					if (!isUpdateAfterUpload(conn, workorderDtl.getBarcode(), workorderDtl.getUploadDate())) {
						sqlModels.add(orderDiffModel.getUpdateItemAddrModel(workorderDtl, addressId));
						changedBarcodes.add(workorderDtl.getBarcode());
					}

				} else {//�����豸
					sqlModels.add(orderDiffModel.getInsertItemModel(workorderDtl, addressId, user));
				}

			} else if (scanStatus.equals(DictConstant.SCAN_STATUS_EXISTS)) {//ɨ�赽---"5"
				/**
				 * ϵͳ�е�ǰ�ص��У�������
				 * ϵͳ�е�ǰ�ص�û�У�1.���ص����ʱ��>�����ύʱ��   ������
				 *                     2.���ص����ʱ��<�����ύʱ��   ���µ���ǰ�ص�
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

			} else if (scanStatus.equals(DictConstant.SCAN_STATUS_NONE)) {//δɨ�赽--"6"
				/**
				 * ϵͳ�е�ǰ�ص���: ������
				 * ϵͳ�е�ǰ�ص��У���Ϊ��;
				 */
				if (hasRecordInCurObj) {
					sqlModels.add(orderDiffModel.getUpdateItemAddrModel(workorderDtl, addressZT));
					changedBarcodes.add(workorderDtl.getBarcode());
				}


			} else if (scanStatus.equals(DictConstant.SCAN_STATUS_OFFLINE)) {//�����豸---"7"
				/**
				 * ϵͳ��ǰ�ص��У���Ϊ��;
				 * ϵͳ��ǰ�ص��ޣ�������
				 */
				if (hasRecordInCurObj) {
					sqlModels.add(orderDiffModel.getUpdateItemAddrModel(workorderDtl, addressZT));
					changedBarcodes.add(workorderDtl.getBarcode());
				} else if (!hasRecord) {
					sqlModels.add(orderDiffModel.getInsertItemModel(workorderDtl, addressZT, user));
				}

			} else if (scanStatus.equals(DictConstant.SCAN_STATUS_REMAIN)) { //ʣ���豸---"8"
				/**
				 * ϵͳ���ޣ�������;��
				 * ϵͳ���У�������
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
	 * Ѳ�칤���鵵
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
		String addressId = ""; //��ǰɨ��ص�

		for (int i = 0; i < dtlSet.getSize(); i++) {
			workorderDtl = (EtsWorkorderDtlDTO) dtlSet.getDTO(i);
			String barcode = workorderDtl.getBarcode();

			if (mp.keySet().contains(barcode)) {
				String confirmResult = mp.get(barcode).toString();
				String deptCode = mp_speciality_dept.get(barcode).toString();
				workorderDtl.setSpecialityDept(deptCode);
				
//                confirmResult="ϵͳ���Ϊ׼";
				if (!confirmResult.equals(AmsOrderConstant.CONFIRM_SYSTEM)) {
					addressId = getAddress(workorderDTO.getWorkorderObjectNo(),conn);//��ǰ�ص�
					String scanStatus = StrUtil.nullToString(workorderDtl.getItemStatus());
					boolean hasRecord = hasRecord(conn, workorderDtl.getBarcode());
					boolean hasRecordInCurObj = hasRecord(conn, workorderDtl.getBarcode(), workorderDtl.getWorkorderObjectNo());

					if (scanStatus.equals(DictConstant.SCAN_STATUS_NEW)) {//����
						/**
						 * ϵͳû��:����豸��ets_item_info���ص�Ϊ��ǰɨ��ص�
						 * ϵͳ�У�1.���ص����ʱ��>�����ύʱ��   ������
						 *         2.���ص����ʱ��<�����ύʱ��   ���µ���ǰ�ص�
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

					} else if (scanStatus.equals(DictConstant.SCAN_STATUS_EXISTS)) {//ɨ�赽
						/**
						 * ϵͳ�е�ǰ�ص��У�������
						 * ϵͳ�е�ǰ�ص�û�У�1.���ص����ʱ��>�����ύʱ��   ������
						 *                     2.���ص����ʱ��<�����ύʱ��   ���µ���ǰ�ص�
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

					} else if (scanStatus.equals(DictConstant.SCAN_STATUS_NONE)) {//δɨ�赽
						/**
						 * ϵͳ�е�ǰ�ص���: ������
						 * ϵͳ�е�ǰ�ص��У���Ϊ��;
						 */
                    if(!workorderDTO.getWorkorderType().equals(DictConstant.ORDER_TYPE_HDV)){
						if (hasRecordInCurObj) {
							sqlModels.add(orderDiffModel.getUpdateItemAddrModel(workorderDtl, addressZT));
							changedBarcodes.add(barcode);
						}
                    }


					} else if (scanStatus.equals(DictConstant.SCAN_STATUS_OFFLINE)) {//�����豸 --��Ӧ��������״̬
						/**
						 * ϵͳ��ǰ�ص��У���Ϊ��;
						 * ϵͳ��ǰ�ص��ޣ�������
						 */
						if (hasRecordInCurObj) {
							sqlModels.add(orderDiffModel.getUpdateItemAddrModel(workorderDtl, addressZT));
							changedBarcodes.add(barcode);
						}

					} else if (scanStatus.equals(DictConstant.SCAN_STATUS_REMAIN)) { //ʣ���豸 -��Ӧ��������״̬
						/**
						 * ϵͳ���ޣ�������;��
						 * ϵͳ���У�������
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
	 * ȡָ�����е���;��
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
	 * ��ѯϵͳ���Ƿ��и�����
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
	 * ��ѯ���������µص�ʱ���Ƿ���ڹ����ύʱ��
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
