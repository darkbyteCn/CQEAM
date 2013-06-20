/**
 * 
 */
package com.sino.ams.newasset.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.bean.OrderNumGenerator;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.dto.AmsAssetsReservedDTO;
import com.sino.ams.newasset.dto.AmsAssetsTransHeaderDTO;
import com.sino.ams.newasset.dto.AmsAssetsTransLineDTO;
import com.sino.ams.newasset.model.AmsAssetsSubModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.db.datatrans.CustomTransData;
import com.sino.base.db.datatrans.DataRange;
import com.sino.base.db.datatrans.DataTransfer;
import com.sino.base.db.datatrans.TransRule;
import com.sino.base.db.datatrans.TransferFactory;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.flow.bean.FlowAction;
import com.sino.flow.constant.FlowConstant;
import com.sino.flow.dto.FlowDTO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * @author dell
 *
 */
public class AmsAssetsSubHeaderDAO extends AMSBaseDAO{


	/**
	 * 功能：资产业务头表(EAM)--取代原表 AMS_ASSETS_TRANS_HEADER 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsAssetsTransHeaderDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public AmsAssetsSubHeaderDAO(SfUserDTO userAccount, AmsAssetsTransHeaderDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		AmsAssetsTransHeaderDTO dtoPara = (AmsAssetsTransHeaderDTO) dtoParameter;
		sqlProducer = new AmsAssetsSubModel((SfUserDTO)userAccount, dtoPara);
	}


	/**
	 * 功能：保存资产单据：含调拨，报废，处置
	 * @param lineSet DTOSet 资产数据
	 * @param flowDTO 流程数据
	 * @return boolean
	 */
	public boolean saveOrder(DTOSet lineSet, FlowDTO flowDTO) {
		boolean operateResult = false;
		boolean autoCommit = true;
		AmsAssetsTransHeaderDTO dtoPara = (AmsAssetsTransHeaderDTO) dtoParameter;
		String transType = dtoPara.getTransType();
		try {
			autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			String transId = saveOrderHeader(flowDTO);
			saveOrderLines(transId, lineSet);
			operateResult = true;
		} catch (SQLException ex) {
			Logger.logError(ex);
		} catch (DataHandleException ex) {
			Logger.logError(ex);
		} finally {
			try {
				if (!operateResult) {
					conn.rollback();
					if (transType.equals(AssetsDictConstant.ASS_RED)) {
						prodMessage(AssetsMessageKeys.ASSETS_TRANSFER_FAILURE);
					} else if (transType.equals(AssetsDictConstant.ASS_DIS)) {
						prodMessage(AssetsMessageKeys.ASSETS_DISCARD_FAILURE);
					} else if (transType.equals(AssetsDictConstant.ASS_CLR)) {
						prodMessage(AssetsMessageKeys.ASSETS_CLEAR_FAILURE);
					} else if (transType.equals(AssetsDictConstant.ASS_FREE)) {
						prodMessage(AssetsMessageKeys.ASSETS_FREE_FAILURE);
					} else if (transType.equals(AssetsDictConstant.ASS_SUB)) {
						prodMessage(AssetsMessageKeys.ASSETS_SUB_FAILURE);
					}
				} else {
					conn.commit();
					if (transType.equals(AssetsDictConstant.ASS_RED)) {
						prodMessage(AssetsMessageKeys.ASSETS_TRANSFER_SUCCESS);
					} else if (transType.equals(AssetsDictConstant.ASS_DIS)) {
						prodMessage(AssetsMessageKeys.ASSETS_DISCARD_SUCCESS);
					} else if (transType.equals(AssetsDictConstant.ASS_CLR)) {
						prodMessage(AssetsMessageKeys.ASSETS_CLEAR_SUCCESS);
					} else if (transType.equals(AssetsDictConstant.ASS_FREE)) {
						prodMessage(AssetsMessageKeys.ASSETS_FREE_SUCCESS);
					} else if (transType.equals(AssetsDictConstant.ASS_SUB)) {
						prodMessage(AssetsMessageKeys.ASSETS_SUB_SUCCESS);
					}
				}
				conn.setAutoCommit(autoCommit);
				message.addParameterValue("保存");
				message.setIsError(!operateResult);
			} catch (SQLException ex) {
				Logger.logError(ex);
				prodMessage(AssetsMessageKeys.ROLL_BACK_ERROR);
			}
		}
		return operateResult;
	}
	 /**
	 * 功能：查找用户所对应的PID，流程用
	 */
    public boolean isGroupFlowId() throws QueryException{
    	AmsAssetsSubModel modelProducer = (AmsAssetsSubModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getGroupPidModel();
        SimpleQuery simp = new SimpleQuery(sqlModel,conn);
        simp.executeQuery();
        return simp.hasResult();
    }

	/**
	 * 功能：提交资产单据：含调拨，报废，处置
	 * @param lineSet DTOSet 资产数据
	 * @param flowDTO 流程数据
	 * @return boolean
	 */
	public boolean submitOrder(DTOSet lineSet, FlowDTO flowDTO) {
		boolean operateResult = false;
		boolean autoCommit = true;
		AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
		String transType = dto.getTransType();
		boolean newOrder = dto.getTransId().equals("");
		int successCount = 0;
		try {
			autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			String transId = saveOrderHeader(flowDTO);
			successCount = saveOrderLines(transId, lineSet);
			operateResult = true;
		} catch (SQLException ex) {
			Logger.logError(ex);
		} catch (DataHandleException ex) {
			Logger.logError(ex);
		} finally {
			try {
				if (!operateResult) {
					conn.rollback();
					if (transType.equals(AssetsDictConstant.ASS_RED)) {
						prodMessage(AssetsMessageKeys.ASSETS_TRANSFER_FAILURE);
					} else if (transType.equals(AssetsDictConstant.ASS_DIS)) {
						prodMessage(AssetsMessageKeys.ASSETS_DISCARD_FAILURE);
					} else if (transType.equals(AssetsDictConstant.ASS_CLR)) {
						prodMessage(AssetsMessageKeys.ASSETS_CLEAR_FAILURE);
					} else if (transType.equals(AssetsDictConstant.ASS_FREE)) {
						prodMessage(AssetsMessageKeys.ASSETS_FREE_FAILURE);
					} else if (transType.equals(AssetsDictConstant.ASS_SUB)) {
						prodMessage(AssetsMessageKeys.ASSETS_SUB_FAILURE);
					}
				} else {
					if (transType.equals(AssetsDictConstant.ASS_RED)) {
						if(!servletConfig.getProvinceCode().equals(AssetsDictConstant.PROVINCE_CODE_NM)){
							if (successCount > 0) {
								prodMessage(AssetsMessageKeys.ASSETS_TRANSFER_SUCCESS);
								conn.commit();
							} else {
								prodMessage(AssetsMessageKeys.ASSETS_ALL_RESERVED);
								conn.rollback();
								operateResult = false;
								if (newOrder) {
									dto.setTransId("");
									dto.setTransNo(AssetsWebAttributes.ORDER_AUTO_PROD);
								}
							}
						} else {
							prodMessage(AssetsMessageKeys.ASSETS_TRANSFER_SUCCESS);
							conn.commit();
						}
					} else {
						conn.commit();
						if (transType.equals(AssetsDictConstant.ASS_DIS)) {
							prodMessage(AssetsMessageKeys.ASSETS_DISCARD_SUCCESS);
						} else if (transType.equals(AssetsDictConstant.ASS_CLR)) {
							prodMessage(AssetsMessageKeys.ASSETS_CLEAR_SUCCESS);
						} else if (transType.equals(AssetsDictConstant.ASS_FREE)) {
							prodMessage(AssetsMessageKeys.ASSETS_FREE_SUCCESS);
						} else if (transType.equals(AssetsDictConstant.ASS_SUB)) {
							prodMessage(AssetsMessageKeys.ASSETS_SUB_SUCCESS);
						}
					}
				}
				conn.setAutoCommit(autoCommit);
				message.addParameterValue("提交");
				message.setIsError(!operateResult);
			} catch (SQLException ex) {
				Logger.logError(ex);
				prodMessage(AssetsMessageKeys.ROLL_BACK_ERROR);
			}
		}
		return operateResult;
	}


	public boolean doDelete(DTOSet lineSet, FlowDTO flowDTO) {
		boolean operateResult = false;
		boolean autoCommit = true;
		AmsAssetsTransHeaderDTO dtoPara = (AmsAssetsTransHeaderDTO)
										  dtoParameter;
		String transType = dtoPara.getTransType();
		try {
			autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			String transId = "";
			AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO)
												dtoParameter;
			transId = headerDTO.getTransId();
			deleteLines();
			deleteReserveAssets();
			saveOrderLines(transId, lineSet);
			operateResult = true;
		} catch (SQLException ex) {
			Logger.logError(ex);
		} catch (DataHandleException ex) {
			Logger.logError(ex);
		} finally {
			try {
				if (!operateResult) {
					conn.rollback();
					if (transType.equals(AssetsDictConstant.ASS_RED)) {
						prodMessage(AssetsMessageKeys.ASSETS_TRANSFER_FAILURE);
					} else if (transType.equals(AssetsDictConstant.ASS_DIS)) {
						prodMessage(AssetsMessageKeys.ASSETS_DISCARD_FAILURE);
					} else if (transType.equals(AssetsDictConstant.ASS_CLR)) {
						prodMessage(AssetsMessageKeys.ASSETS_CLEAR_FAILURE);
					} else if (transType.equals(AssetsDictConstant.ASS_FREE)) {
						prodMessage(AssetsMessageKeys.ASSETS_FREE_FAILURE);
					} else if (transType.equals(AssetsDictConstant.ASS_SUB)) {
						prodMessage(AssetsMessageKeys.ASSETS_SUB_FAILURE);
					}
				} else {
					conn.commit();
					if (transType.equals(AssetsDictConstant.ASS_RED)) {
						prodMessage(AssetsMessageKeys.ASSETS_TRANSFER_SUCCESS);
					} else if (transType.equals(AssetsDictConstant.ASS_DIS)) {
						prodMessage(AssetsMessageKeys.ASSETS_DISCARD_SUCCESS);
					} else if (transType.equals(AssetsDictConstant.ASS_CLR)) {
						prodMessage(AssetsMessageKeys.ASSETS_CLEAR_SUCCESS);
					} else if (transType.equals(AssetsDictConstant.ASS_FREE)) {
						prodMessage(AssetsMessageKeys.ASSETS_FREE_SUCCESS);
					} else if (transType.equals(AssetsDictConstant.ASS_SUB)) {
						prodMessage(AssetsMessageKeys.ASSETS_SUB_SUCCESS);
					}
				}
				conn.setAutoCommit(autoCommit);
				message.addParameterValue("删除");
				message.setIsError(!operateResult);
			} catch (SQLException ex) {
				Logger.logError(ex);
				prodMessage(AssetsMessageKeys.ROLL_BACK_ERROR);
			}
		}
		return operateResult;
	}
	/**
	 * 功能：保存单据头信息
	 * @param flowDTO 流程所需数据
	 * @return String
	 * @throws DataHandleException
	 */
	private String saveOrderHeader(FlowDTO flowDTO) throws DataHandleException {
		String transId = "";
		try {
			AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO)dtoParameter;
			transId = headerDTO.getTransId();
			String transNo = headerDTO.getTransNo();
			headerDTO.setFromPerson(userAccount.getEmployeeNumber());
			String act = headerDTO.getAct();
			flowDTO.setApproveContent(FlowConstant.APPROVE_CONTENT_NEW);
			if (transNo.equals(AssetsWebAttributes.ORDER_AUTO_PROD)) {
				if(StrUtil.isEmpty(transId)){
					SeqProducer seq = new SeqProducer(conn);
//					transId = seq.getStrNextSeq("AMS_ASSETS_TRANS_HEADER_S");
					//TODO ID 
					transId = seq.getGUID();
					
					headerDTO.setTransId(transId);
				}
				String companyCode = userAccount.getCompanyCode(); //还是采用该方法，以下考虑周子君认为没必要
				String transType = headerDTO.getTransType();
				OrderNumGenerator numberProducer = new OrderNumGenerator(conn,companyCode, transType);
				headerDTO.setTransNo(numberProducer.getOrderNum());
				setDTOParameter(headerDTO);
				createData();
				if (act.equals(AssetsActionConstant.SUBMIT_ACTION)) {
					executeFlow(flowDTO, false); //加入流程
				} else {
					executeFlow(flowDTO, true); //流程流转
				}
			} else {
				updateData();
				deleteLines();
				deleteReserveAssets();
				if (act.equals(AssetsActionConstant.SUBMIT_ACTION)) {
					executeFlow(flowDTO, false); //流程流转
				}
			}
		} catch (SQLException ex) {
			Logger.logError(ex);
			throw new DataHandleException(ex);
		}
		return transId;
	}

	/**
	 * 功能：执行流程
	 * @param flowDTO FlowDTO
	 * @param add2Flow 是初次加入流程还是流程审批过程中
	 * @throws SQLException
	 * @throws DataHandleException
	 */
	private void executeFlow(FlowDTO flowDTO, boolean add2Flow) throws
			SQLException, DataHandleException {
		try {
			AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO)
												dtoParameter;
			flowDTO.setProcName(headerDTO.getProcdureName());
			flowDTO.setActivity(FlowConstant.FLOW_CODE_NEXT);
			flowDTO.setApplyId(headerDTO.getTransId());
			flowDTO.setSessionUserId(userAccount.getUserId());
			flowDTO.setSessionUserName(userAccount.getUsername());
			flowDTO.setApplyNo(headerDTO.getTransNo());
			if (add2Flow) {
				FlowAction fa = new FlowAction(conn);
				fa.setDto(flowDTO);
				fa.add2Flow(flowDTO.getProcName());
			} else {
				FlowAction fa = new FlowAction(conn, flowDTO);
				fa.flow();
			}
		} catch (QueryException ex) {
			ex.printLog();
			throw new DataHandleException(ex);
		}
	}

	/**
	 * 功能：保存单据行信息。
	 * @param transId String
	 * @param lineSet DTOSet
	 * @return int 返回保存成功的单据行条数
	 * @throws DataHandleException
	 */
	private int saveOrderLines(String transId, DTOSet lineSet) throws DataHandleException {
		int submitCount = 0;
		try {
			AmsAssetsTransHeaderDTO orderDTO = (AmsAssetsTransHeaderDTO) dtoParameter;
			AmsAssetsTransLineDAO lineDAO = new AmsAssetsTransLineDAO(userAccount, null, conn);
			String transferType = orderDTO.getTransferType();
			AmsAssetsTransLineDTO lineData = null;
			for (int i = 0; i < lineSet.getSize(); i++) {
				lineData = (AmsAssetsTransLineDTO) lineSet.getDTO(i);
				if (lineData.getBarcode().equals("")) {
					continue;
				}
				lineData.setTransId(transId);
				lineData.setLineStatus(orderDTO.getTransStatus());
				if (lineData.getOldResponsibilityDept().equals("")) {
					lineData.setOldResponsibilityDept(StrUtil.nullToString(orderDTO.getFromDept()));
				}
				if (transferType.equals(AssetsDictConstant.TRANS_INN_DEPT)) { //部门内调拨
					lineData.setResponsibilityDept(StrUtil.nullToString(orderDTO.getFromDept()));
				}
				lineDAO.setDTOParameter(lineData);
				if (!lineDAO.hasReserved()) {
					lineDAO.createData();
					createReserveAssets(lineData.getBarcode()); //保留资产
					submitCount++;
				}
			}
		} catch (QueryException ex) {
			ex.printLog();
			throw new DataHandleException(ex);
		}
		return submitCount;
	}

	/**
	 * 功能：删除单据的行信息
	 * @throws DataHandleException
	 */
	private void deleteLines() throws DataHandleException {
		AmsAssetsTransLineDTO lineDTO = new AmsAssetsTransLineDTO();
		AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO)
											dtoParameter;
		lineDTO.setTransId(headerDTO.getTransId());
		AmsAssetsTransLineDAO lineDAO = new AmsAssetsTransLineDAO(userAccount,
				lineDTO, conn);
		lineDAO.DeleteByForeignKey("transId");
	}

	/**
	 * 功能：删除本单据保留的资产
	 * @throws DataHandleException
	 */
	private void deleteReserveAssets() throws DataHandleException {
		AmsAssetsReservedDTO reserveDTO = new AmsAssetsReservedDTO();
		AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO)
											dtoParameter;
		reserveDTO.setTransId(headerDTO.getTransId());
		AmsAssetsReservedDAO reserveDAO = new AmsAssetsReservedDAO(userAccount,
				reserveDTO, conn);
		reserveDAO.DeleteByForeignKey("transId");
	}

	/**
	 * 功能：保留资产
	 * @param batrcode String
	 * @throws DataHandleException
	 */
	private void createReserveAssets(String batrcode) throws
			DataHandleException {
		AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO)
											dtoParameter;
		AmsAssetsReservedDTO reserveDTO = new AmsAssetsReservedDTO();
		reserveDTO.setTransId(headerDTO.getTransId());
		reserveDTO.setBarcode(batrcode);
		reserveDTO.setCurrCalendar("reservedDate");
		AmsAssetsReservedDAO reserveDAO = new AmsAssetsReservedDAO(userAccount,
				reserveDTO, conn);
		reserveDAO.createData();
	}

	/**
	 * 功能：撤销暂存的单据
	 * @param transIds String[]
	 * @return boolean
	 */
	public boolean cancelOrders(String[] transIds) {
		boolean operateResult = false;
		boolean autoCommit = true;
		AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) dtoParameter;
		AmsAssetsSubModel modelProducer = (AmsAssetsSubModel) sqlProducer;
		try {
			autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			String transId = transIds[0];
			headerDTO.setTransId(transId);
			setDTOParameter(headerDTO);
			setDTOClassName(AmsAssetsTransHeaderDTO.class.getName());
			headerDTO = (AmsAssetsTransHeaderDTO) getDataByPrimaryKey();
			headerDTO.setServletConfig(servletConfig);
			FlowAction flowProcessor = new FlowAction(conn);
			FlowDTO flowDTO = new FlowDTO();
			flowDTO.setProcName(headerDTO.getProcdureName());
			SQLModel sqlModel = null;
			AmsAssetsReservedDAO reserveDAO = new AmsAssetsReservedDAO(userAccount, null, conn);
			AmsAssetsReservedDTO reserveDTO = new AmsAssetsReservedDTO();
			AmsAssetsTransLineDAO lineDAO = new AmsAssetsTransLineDAO(userAccount, null, conn);
			AmsAssetsTransLineDTO lineDTO = new AmsAssetsTransLineDTO();
			for (int i = 0; i < transIds.length; i++) {
				transId = transIds[i];
				sqlModel = modelProducer.getOrderCancelModel(transId); //撤销单据
				DBOperator.updateRecord(sqlModel, conn);

				reserveDTO.setTransId(transId); //删除保留数据
				reserveDAO.setDTOParameter(reserveDTO);
				reserveDAO.DeleteByForeignKey("transId");

				lineDTO.setTransId(transId);
				lineDAO.setDTOParameter(lineDTO);
				lineDAO.cancelLinesByHeader();

				flowDTO.setApplyId(transId); //删除在办箱数据
				flowProcessor.setDto(flowDTO);
				flowProcessor.cancel();
			}
			operateResult = true;
		} catch (DataHandleException ex) {
			ex.printLog();
		} catch (SQLException ex) {
			Logger.logError(ex);
		} catch (QueryException ex) {
			ex.printLog();
		} finally {
			try {
				if (operateResult) {
					conn.commit();
					prodMessage(AssetsMessageKeys.ORDER_CANCEL_SUCCESS);
				} else {
					conn.rollback();
					prodMessage(AssetsMessageKeys.ORDER_CANCEL_FAILURE);
				}
				conn.setAutoCommit(autoCommit);
				message.addParameterValue(headerDTO.getTransTypeValue());
				message.setIsError(!operateResult);
			} catch (SQLException ex1) {
				Logger.logError(ex1);
				prodMessage(AssetsMessageKeys.ROLL_BACK_ERROR);
			}
		}
		return operateResult;
	}

	public File exportFile() throws DataTransException { //导出模板
		File file = null;
		DataTransfer transfer = null;
		AmsAssetsSubModel modelProducer = (AmsAssetsSubModel)
												  sqlProducer;
		SQLModel sqlModel = modelProducer.getOrderModel();
		TransRule rule = new TransRule();
		rule.setDataSource(sqlModel);
		rule.setCalPattern(CalendarConstant.LINE_PATTERN);
		rule.setSourceConn(conn);

		AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO)
											dtoParameter;
		String transferType = headerDTO.getTransferType();
		String fileName = "";
		if (transferType.equals(AssetsDictConstant.TRANS_INN_DEPT)) { //部门内调拨
			fileName = "部门内调拨.xls";
		} else if (transferType.equals(AssetsDictConstant.TRANS_BTW_DEPT)) { //部门内调拨
			fileName = "部门间调拨.xls";
		} else if (transferType.equals(AssetsDictConstant.TRANS_BTW_COMP)) { //地市间调拨
			fileName = "地市间调拨.xls";
		}

		String filePath = WorldConstant.USER_HOME;
		filePath += WorldConstant.FILE_SEPARATOR;
		filePath += fileName;
		rule.setTarFile(filePath);
		DataRange range = new DataRange();
		rule.setDataRange(range);

		Map fieldMap = new HashMap();
		fieldMap.put("MB1", "资产标签");
		fieldMap.put("MB2", "资产编号");
		fieldMap.put("MB3", "资产名称");
		fieldMap.put("MB4", "资产型号");
		fieldMap.put("MB5", "数量");
		if (transferType.equals(AssetsDictConstant.TRANS_INN_DEPT)) {
			fieldMap.put("MB6", "调出地点NO");
			fieldMap.put("MB7", "调出地点");
			fieldMap.put("MB8", "原责任人员工ID");
			fieldMap.put("MB9", "原责任人");
			fieldMap.put("MB10", "调入地点NO");
			fieldMap.put("MB11", "调入地点");
			fieldMap.put("MB12", "新责任人员工ID");
			fieldMap.put("MB13", "新责任人");
			fieldMap.put("MB14", "调动日期");
			fieldMap.put("MB15", "摘要");
		} else if (transferType.equals(AssetsDictConstant.TRANS_BTW_DEPT)) {
			fieldMap.put("MB6", "调出地点NO");
			fieldMap.put("MB7", "调出地点");
			fieldMap.put("MB8", "原责任人员工ID");
			fieldMap.put("MB9", "原责任人");
			fieldMap.put("MB10", "调入部门代码");
			fieldMap.put("MB11", "调入部门");
			fieldMap.put("MB12", "调入地点N0");
			fieldMap.put("MB13", "调入地点");
			fieldMap.put("MB14", "新责任人员工ID");
			fieldMap.put("MB15", "新责任人");
			fieldMap.put("MB16", "调动日期");
			fieldMap.put("MB17", "备注");
		} else if (transferType.equals(AssetsDictConstant.TRANS_BTW_COMP)) {
			fieldMap.put("MB6", "原值");
			fieldMap.put("MB7", "累计折旧");
			fieldMap.put("MB8", "残值");
			fieldMap.put("MB9", "启用日期");
			fieldMap.put("MB10", "调出部门代码");
			fieldMap.put("MB11", "调出部门");
			fieldMap.put("MB12", "调出地点NO");
			fieldMap.put("MB13", "调出地点");
			fieldMap.put("MB14", "原责任人员工ID");
			fieldMap.put("MB15", "原责任人");
			fieldMap.put("MB16", "原折旧账户");
			fieldMap.put("MB17", "原类别");
			fieldMap.put("MB18", "调入部门代码");
			fieldMap.put("MB19", "调入部门");
			fieldMap.put("MB20", "调入地点NO");
			fieldMap.put("MB21", "调入地点");
			fieldMap.put("MB22", "新责任人员工ID");
			fieldMap.put("MB23", "新责任人");
			fieldMap.put("MB24", "新折旧账户");
			fieldMap.put("MB25", "新类别");
			fieldMap.put("MB26", "调动日期");
			fieldMap.put("MB27", "备注");
		}

		rule.setFieldMap(fieldMap);
		CustomTransData custData = new CustomTransData();
//            custData.setReportTitle(fileName);
//            custData.setReportPerson(sfUser.getUsername());
		custData.setNeedReportDate(false);
		rule.setCustData(custData);

		TransferFactory factory = new TransferFactory();
		transfer = factory.getTransfer(rule);
		transfer.transData();
		file = (File) transfer.getTransResult();
		return file;
	}

	

}
