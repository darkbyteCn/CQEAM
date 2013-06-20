package com.sino.td.newasset.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.bean.OrderNumGenerator;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.data.Row;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.flow.bean.FlowAction;
import com.sino.flow.constant.FlowConstant;
import com.sino.flow.dto.FlowDTO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.td.newasset.dto.TdAssetsRcvHeaderDTO;
import com.sino.td.newasset.dto.TdAssetsTransHeaderDTO;
import com.sino.td.newasset.model.TdAssetsRcvHeaderModel;

/**
 * <p>Title: AmsAssetsRcvHeaderDAO</p>
 * <p>Description:程序自动生成服务程序“AmsAssetsRcvHeaderDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class TdAssetsRcvHeaderDAO extends AMSBaseDAO {

	/**
	 * 功能：调拨资产接收头表(部门间和公司间需要) AMS_ASSETS_RCV_HEADER 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter TdAssetsRcvHeaderDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public TdAssetsRcvHeaderDAO(SfUserDTO userAccount, TdAssetsRcvHeaderDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		TdAssetsRcvHeaderDTO dto = (TdAssetsRcvHeaderDTO) dtoParameter;
		sqlProducer = new TdAssetsRcvHeaderModel((SfUserDTO) userAccount,dto);
	}

	/**
	 * 功能：根据调拨单头信息和接收分配行信息自动生成接收审批单
	 * @param order TdAssetsTransHeaderDTO
	 * @param assignLines DTOSet
	 * @throws DataHandleException
	 */
	public void autoCreateRcvDatas(TdAssetsTransHeaderDTO order, DTOSet assignLines) throws DataHandleException {
		createRcvOrder(order);
		TdAssetsRcvLineDAO rcvLineDAO = new TdAssetsRcvLineDAO(userAccount, null, conn);
		TdAssetsRcvHeaderDTO dto = (TdAssetsRcvHeaderDTO) dtoParameter;
		rcvLineDAO.setRcvHeader(dto);
		rcvLineDAO.createRcvLines(assignLines);
	}

	/**
	 * 功能：根据调拨单头信息生成调拨接收单头信息
	 * @param order TdAssetsTransHeaderDTO
	 * @throws DataHandleException
	 */
	private void createRcvOrder(TdAssetsTransHeaderDTO order) throws
			DataHandleException {
		try {
			TdAssetsRcvHeaderDTO dto = new TdAssetsRcvHeaderDTO();
			SeqProducer seqProducer = new SeqProducer(conn);
			String rcvId = StrUtil.nullToString(seqProducer.getStrNextSeq("AMS_ASSETS_RCV_HEADER_S"));
			dto.setReceiveHeaderId(rcvId);
			String companyCode = userAccount.getCompanyCode();
			OrderNumGenerator noProducer = new OrderNumGenerator(conn, companyCode, AssetsDictConstant.ASS_RCV);
			dto.setReceiveNo(noProducer.getOrderNum());
			dto.setTransId(order.getTransId());
			dto.setReceiveUser(StrUtil.nullToString(userAccount.getUserId()));
			dto.setOrderStatus(AssetsDictConstant.SAVE_TEMP);
			dto.setReceiveOrganizationId(StrUtil.nullToString(userAccount.getOrganizationId()));
			dto.setCreatedBy(userAccount.getUserId());
			dto.setCurrCalendar("receiveDate");
			dto.setCurrCreationDate();
			dto.setFaContentCode(order.getFaContentCode());
			setDTOParameter(dto);
			TdAssetsRcvHeaderModel modelProducer = (TdAssetsRcvHeaderModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getDataCreateModel();
			DBOperator.updateRecord(sqlModel, conn);
			add2Flow();
		} catch (SQLModelException ex) {
			ex.printLog();
			throw new DataHandleException(ex);
		} catch (SQLException ex) {
			Logger.logError(ex);
			throw new DataHandleException(ex);
		}
	}

	/**
	 * 功能：加入在办箱
	 * @throws DataHandleException
	 */
	private void add2Flow() throws DataHandleException {
		try {
			TdAssetsRcvHeaderModel modelProducer = (TdAssetsRcvHeaderModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getFirstTaskModel();
			SimpleQuery simp = new SimpleQuery(sqlModel, conn);
			simp.executeQuery();
			if (simp.hasResult()) {
				Row row = simp.getFirstRow();
				TdAssetsRcvHeaderDTO dto = (TdAssetsRcvHeaderDTO) dtoParameter;
				FlowDTO flowDTO = new FlowDTO();
				flowDTO.setProcName(dto.getProcdureName());
				flowDTO.setProcId(row.getStrValue("PROC_ID"));
				flowDTO.setToTaskId(row.getStrValue("TASK_ID"));
				flowDTO.setSessionUserId(userAccount.getUserId());
				flowDTO.setApplyId(dto.getReceiveHeaderId());
				flowDTO.setApplyNo(dto.getReceiveNo());
				flowDTO.setApproveContent("接收分配资产，并创建资产接收单");
				FlowAction flowProcessor = new FlowAction(conn);
				flowProcessor.setDto(flowDTO);
				flowProcessor.add2Flow(dto.getProcdureName());
			}
		} catch (ContainerException ex) {
			ex.printLog();
			throw new DataHandleException(ex);
		} catch (QueryException ex) {
			ex.printLog();
			throw new DataHandleException(ex);
		} catch (SQLException ex) {
			Logger.logError(ex);
			throw new DataHandleException(ex);
		}
	}

	/**
	 * 功能：提交调拨接收单到下一环节
	 * @param flowDTO FlowDTO
	 * @return boolean
	 */
	public boolean submitRcvOrder(FlowDTO flowDTO) {
		boolean operateResult = false;
		boolean autoCommit = true;
		TdAssetsRcvHeaderDTO dto = (TdAssetsRcvHeaderDTO) dtoParameter;
		try {
			autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			flowDTO.setApproveContent(FlowConstant.APPROVE_CONTENT_NEW);
			flowDTO.setProcName(dto.getProcdureName());
			flowDTO.setActivity(FlowConstant.FLOW_CODE_NEXT);
			flowDTO.setApplyId(dto.getReceiveHeaderId());
			flowDTO.setSessionUserId(userAccount.getUserId());
			flowDTO.setSessionUserName(userAccount.getUsername());
			flowDTO.setApplyNo(dto.getReceiveNo());
			dto.setOrderStatus(AssetsDictConstant.IN_PROCESS);
			setDTOParameter(dto);
			updateData();
			FlowAction flowProcessor = new FlowAction(conn);
			flowProcessor.setDto(flowDTO);
			flowProcessor.flow();
			operateResult = true;
		} catch (QueryException ex) {
			ex.printLog();
		} catch (DataHandleException ex) {
			ex.printLog();
		} catch (SQLException ex) {
			Logger.logError(ex);
		} finally {
			try {
				if (!operateResult) {
					conn.rollback();
					prodMessage(AssetsMessageKeys.RCVORDER_SUBMIT_FAILURE);
				} else {
					conn.commit();
					prodMessage(AssetsMessageKeys.RCVORDER_SUBMIT_SUCCESS);
				}
				conn.setAutoCommit(autoCommit);
				message.setIsError(!operateResult);
			} catch (SQLException ex) {
				Logger.logError(ex);
				prodMessage(AssetsMessageKeys.ROLL_BACK_ERROR);
			}
		}
		return operateResult;
	}

	/**
	 * 功能：判断当前用户对指定流程的指定应用的当前节点是否可进行审批
	 * @return boolean
	 */
	public boolean canApproveCurrTask() {
		boolean canApprove = false;
		try {
			TdAssetsRcvHeaderModel modelProducer = (TdAssetsRcvHeaderModel)
					sqlProducer;
			SQLModel sqlModel = modelProducer.getCanApproveModel();
			SimpleQuery simp = new SimpleQuery(sqlModel, conn);
			simp.executeQuery();
			if (simp.hasResult()) {
				canApprove = true;
			}
		} catch (QueryException ex) {
			ex.printLog();
		}
		return canApprove;
	}

	/**
	 * 功能：审批调拨资产接收单
	 * @param flowDTO FlowDTO
	 * @return boolean
	 */
	public boolean approveRcvOrder(FlowDTO flowDTO) {
		boolean operateResult = false;
		boolean autoCommit = true;
		TdAssetsRcvHeaderDTO dto = (TdAssetsRcvHeaderDTO) dtoParameter;
		String flowCode = dto.getFlowCode();
		try {
			autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			flowDTO.setApproveContent(FlowConstant.APPROVE_CONTENT_NEW);
			flowDTO.setProcName(dto.getProcdureName());
			flowDTO.setActivity(FlowConstant.FLOW_CODE_NEXT);
			flowDTO.setApplyId(dto.getReceiveHeaderId());
			flowDTO.setSessionUserId(userAccount.getUserId());
			flowDTO.setSessionUserName(userAccount.getUsername());
			flowDTO.setApplyNo(dto.getReceiveNo());
			FlowAction flowProcessor = new FlowAction(conn, flowDTO);
			boolean flow2End = flowProcessor.isFlowToEnd();
			dto.setFlow2End(flow2End);
			if (flowCode.equals(FlowConstant.FLOW_CODE_NEXT)) {
				if (flow2End) {
					dto.setOrderStatus(AssetsDictConstant.APPROVED);
					flowDTO.setApproveContent(FlowConstant.APPROVE_CONTENT_END);
				} else {
					dto.setOrderStatus(AssetsDictConstant.IN_PROCESS);
					flowDTO.setApproveContent(FlowConstant.
											  APPROVE_CONTENT_AGREE);
				}
				flowProcessor.setDto(flowDTO);
				flowProcessor.flow();
			} else {
				dto.setOrderStatus(AssetsDictConstant.REJECTED);
				flowDTO.setApproveContent(FlowConstant.APPROVE_CONTENT_REJECT);
				flowProcessor.reject2Begin();
			}
			setDTOParameter(dto);
			updateData();
			operateResult = true;
		} catch (QueryException ex) {
			ex.printLog();
		} catch (DataHandleException ex) {
			ex.printLog();
		} catch (SQLException ex) {
			Logger.logError(ex);
		} catch (Exception ex) {
			Logger.logError(ex);
		} finally {
			try {
				if (!operateResult) {
					conn.rollback();
				} else {
					conn.commit();
				}
				processMessage(operateResult, flowCode);
				conn.setAutoCommit(autoCommit);
				message.setIsError(!operateResult);
			} catch (SQLException ex) {
				Logger.logError(ex);
				prodMessage(AssetsMessageKeys.ROLL_BACK_ERROR);
			}
		}
		return operateResult;
	}

	/**
	 * 功能：构造单据审批消息提示
	 * @param operateResult boolean
	 * @param flowCode String
	 */
	private void processMessage(boolean operateResult, String flowCode) {
		TdAssetsRcvHeaderDTO dto = (TdAssetsRcvHeaderDTO) dtoParameter;
		if (flowCode.equals(FlowConstant.FLOW_CODE_NEXT)) {
			if (operateResult) {
				prodMessage(AssetsMessageKeys.PASS_ORDER_SUCCESS);
			} else {
				prodMessage(AssetsMessageKeys.PASS_ORDER_FAILURE);
			}
		} else {
			if (operateResult) {
				prodMessage(AssetsMessageKeys.REJECT_ORDER_SUCCESS);
			} else {
				prodMessage(AssetsMessageKeys.REJECT_ORDER_FAILURE);
			}
		}
		message.addParameterValue("资产调拨接收");
		message.addParameterValue(dto.getReceiveNo());
		message.setIsError(!operateResult);
	}
}
