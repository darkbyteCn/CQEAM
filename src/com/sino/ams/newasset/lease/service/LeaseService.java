package com.sino.ams.newasset.lease.service;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.ams.bean.OrderNumGenerator;
import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.dto.AmsItemInfoHistoryDTO;
import com.sino.ams.newasset.lease.constant.LeaseAppConstant;
import com.sino.ams.newasset.lease.dao.LeaseDAO;
import com.sino.ams.newasset.lease.dto.LeaseDTO;
import com.sino.ams.newasset.lease.dto.LeaseHeaderDTO;
import com.sino.ams.newasset.lease.dto.LeaseLineDTO;
import com.sino.ams.newasset.service.AssetsBaseService;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * @系统名称: 续租
 * @功能描述:
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本：1.0
 * @开发作者: sj
 * @创建时间: Jul 14, 2011
 */
public class LeaseService extends AssetsBaseService {
	static String ORDER_TITLE = "续租单";
	private LeaseDTO leaseDTO = null;
	private LeaseHeaderDTO headerDTO = null;

	private DTOSet lines = null;

	private  LeaseDAO headerDAO = null;

	private  String msg = null;

	public LeaseService(SfUserDTO user, LeaseDTO dto, Connection conn) {
		super(user, dto, conn);
		this.init(user, dto, conn);
	}

	
	/**
	 * 撤销
	 * 
	 * @param
	 * @return
	 */
	public boolean doCancel() {
		boolean operateResult = false;
		boolean autoCommit = true;
		try {
			autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			headerDTO.setTransStatus(AssetsDictConstant.CANCELED);
			headerDAO.updateHeaderStatus(headerDTO);
			super.deleteReserved(headerDTO.getTransId());
			headerDTO.setApp_dataID( headerDTO.getTransId() ); 
			headerDAO = new LeaseDAO( userAccount , headerDTO, conn);
			operateResult = super.cancelProcedure();
			
		} catch (SQLException ex) {
			Logger.logError(ex);
		} catch (DataHandleException ex) {
			Logger.logError(ex);
		} finally {
			try {
				if (!operateResult) {
					this.msg = ORDER_TITLE + "(" + headerDTO.getTransNo() +  ")" +  "撤销失败";
					conn.rollback();
				} else {
					this.msg = ORDER_TITLE + "(" + headerDTO.getTransNo() +  ")" +   "撤销成功";
					conn.commit();
				}
				conn.setAutoCommit(autoCommit);
			} catch (SQLException ex) {
				Logger.logError(ex);
			}
		}
		return operateResult;
	}

	/**
	 * 退回
	 * 
	 * @param
	 * @return
	 */
	public boolean doReturn() {
		boolean operateResult = false;
		boolean autoCommit = true;
		try {
			autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			headerDTO.setTransStatus(AssetsDictConstant.REJECTED);
			headerDAO.updateHeaderStatus(headerDTO);
			headerDTO.setApp_dataID( headerDTO.getTransId() ); 
			headerDAO = new LeaseDAO( userAccount , headerDTO, conn);
			operateResult = super.rejectProcedure();

		} catch (SQLException ex) {
			Logger.logError(ex);
		} catch (DataHandleException ex) {
			Logger.logError(ex);
		} finally {
			try {
				if (!operateResult) {
					this.msg = ORDER_TITLE + "(" + headerDTO.getTransNo() +  ")" +  "退回失败";
					conn.rollback();
				} else {
					this.msg = ORDER_TITLE + "(" + headerDTO.getTransNo() +  ")" +  "退回成功";
					conn.commit();
				}
				conn.setAutoCommit(autoCommit);
			} catch (SQLException ex) {
				Logger.logError(ex);
			}
		}
		return operateResult;
	}
	
	/**
	 * 保存
	 * 
	 * @param
	 * @return
	 */
	public boolean doSave() {
		boolean operateResult = false;
		boolean autoCommit = true;
		String transType = headerDTO.getTransType();
		try {
			autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			headerDTO.setTransStatus(AssetsDictConstant.SAVE_TEMP);
			this.saveHeader();
			this.saveLines();
			operateResult = super.processProcedure(false);
		} catch (SQLException ex) {
			Logger.logError(ex);
		} catch (DataHandleException ex) {
			Logger.logError(ex);
		} catch (CalendarException ex) {
			Logger.logError(ex);
		} finally {
			try {
				if (!operateResult) {
					this.msg = ORDER_TITLE + "(" + headerDTO.getTransNo() +  ")" +  "保存失败";
					conn.rollback();
				} else {
					this.msg = ORDER_TITLE + "(" + headerDTO.getTransNo() +  ")" +  "保存成功";
					conn.commit();
				}
				conn.setAutoCommit(autoCommit);
			} catch (SQLException ex) {
				Logger.logError(ex);
			}
		}
		return operateResult;
	}

	/**
	 * 保存
	 * 
	 * @param
	 * @return
	 */
	public boolean doSubmit() {
		boolean operateResult = false;
		boolean autoCommit = true;
		// String transType = headerDTO.getTransType();
		try {
			autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			String sfAtt3 = headerDTO.getSf_task_attribute3();
			String flowCode = headerDTO.getFlowCode();
			this.msg = ORDER_TITLE + "提交";

			if (sfAtt3.equals(LeaseAppConstant.ATT3_FILL_DATA)) {
				headerDTO.setTransStatus(AssetsDictConstant.IN_PROCESS);
				this.saveHeader();
				this.saveLines();
			} else if (sfAtt3.equals(LeaseAppConstant.ATT3_APPROVING)) {
				if (flowCode.equals("A1")) {
					headerDTO.setTransStatus(AssetsDictConstant.APPROVED);
				} else { // 撤销
					super.deleteReserved(headerDTO.getTransId());
					headerDTO.setTransStatus(AssetsDictConstant.CANCELED);
				}
				headerDAO.updateHeaderStatus(headerDTO);
			} else if (sfAtt3.equals(LeaseAppConstant.ATT3_ASS_APPROVE)) {
				headerDTO.setTransStatus(AssetsDictConstant.COMPLETED);
				headerDAO.completeOrder(headerDTO);
				
				/****在此处更新EII中的资产状态：'报废'改为'在用'，失效日期置空****/
				prodLines();
				if (lines != null && !lines.isEmpty()) {
					for (int i = 0; i < lines.getSize(); i++) {
						LeaseLineDTO line = (LeaseLineDTO) lines.getDTO(i);
						headerDAO.updateEii(line);
					}
				}
				super.deleteReserved(headerDTO.getTransId());
				completeRent();
				// 记录资产变更情况
				saveItemInfoHistory();
			}
			operateResult = super.processProcedure(true);
		} catch (SQLException ex) {
			Logger.logError(ex);
		} catch (DataHandleException ex) {
			Logger.logError(ex);
		} catch (CalendarException ex) {
			Logger.logError(ex);
		} catch (QueryException ex) {
			ex.printLog();
			Logger.logError(ex);
		} finally {
			try {
				if (!operateResult) {
					this.msg += ORDER_TITLE + "(" + headerDTO.getTransNo() +  ")" +  "提交失败";
					conn.rollback();
				} else {
					this.msg = ORDER_TITLE + "(" + headerDTO.getTransNo() +  ")" +   "提交成功";
					conn.commit();
				}
				conn.setAutoCommit(autoCommit);
			} catch (SQLException ex) {
				Logger.logError(ex);
			}
		}
		return operateResult;
	}

	/**
	 * 记录变更历史
	 * 
	 * @throws DataHandleException
	 */
	private void saveItemInfoHistory() throws DataHandleException {
		try {
			prodLines();
			if (lines != null && !lines.isEmpty()) {
				String orderURL = "/servlet/com.sino.ams.newasset.lease.servlet.LeaseServlet";
				orderURL += "?act=DETAIL_ACTION";
				orderURL += "&transId=" + headerDTO.getTransId();
				for (int i = 0; i < lines.getSize(); i++) {
					LeaseLineDTO line = (LeaseLineDTO) lines.getDTO(i);

					AmsItemInfoHistoryDTO historyDTO = new AmsItemInfoHistoryDTO();
					historyDTO.setBarcode(line.getBarcode());
					historyDTO
							.setOrderCategory(LeaseAppConstant.ORDER_CATEGORY);
					historyDTO.setOrderNo(headerDTO.getOrderNo());
					historyDTO.setOrderDtlUrl(orderURL);
					historyDTO.setRemark(LeaseAppConstant.TRANS_TYPE_NAME);

					super.saveItemInfoHistory(historyDTO);
				}
			}
		} catch (QueryException ex) {
			ex.printLog();
			throw new DataHandleException(ex);
		} catch (Throwable ex) {
			Logger.logError(ex);
			throw new DataHandleException(ex.getMessage());
		}
	}

	/**
	 * 功能：续租完成
	 * 
	 * @return String
	 * @throws SQLException
	 */
	public void completeRent() throws SQLException {
		CallableStatement cst = null;
		String sqlStr = "{CALL dbo.ASSET_LEASE_COMPLETE(?)}";
		try {
			cst = conn.prepareCall(sqlStr);
			cst.setString(1, headerDTO.getTransId());
			cst.execute();
		} finally {
			DBManager.closeDBStatement(cst);
		}
	}

	/**
	 * 保存头
	 * 
	 * @throws DataHandleException
	 * @throws SQLException
	 */
	public void saveHeader() throws DataHandleException, SQLException {
		if (headerDTO.getTransNo().equals(AssetsWebAttributes.ORDER_AUTO_PROD)) {
			if (StrUtil.isEmpty(headerDTO.getTransId())) {
				SeqProducer seqProducer = new SeqProducer(conn);
				String transId = seqProducer.getGUID();
				headerDTO.setTransId(transId);
			}
			String companyCode = userAccount.getCompanyCode(); // 还是采用该方法，以下考虑周子君认为没必要
			String transType = headerDTO.getTransType();
			OrderNumGenerator numberProducer = new OrderNumGenerator(conn,
					companyCode, transType);
			headerDTO.setTransNo(numberProducer.getOrderNum());
			headerDAO.createHeader(headerDTO);
		} else {
			headerDAO.updateHeader(headerDTO);
		}
	}

	/**
	 * 保存行
	 * 
	 * @throws DataHandleException
	 * @throws CalendarException
	 */
	public void saveLines() throws DataHandleException, CalendarException {
		String transId = headerDTO.getTransId();
		if (!StrUtil.isEmpty(transId)) {
			super.deleteReserved(transId);
			headerDAO.deleteLine(transId);
		}
		LeaseLineDTO line = null;
		String lineId = null;
		SeqProducer seqProducer = new SeqProducer(conn);
		for (int i = 0; i < lines.getSize(); i++) {
			line = (LeaseLineDTO) lines.getDTO(i);
			lineId = seqProducer.getGUID();
			line.setLineId(lineId);
			line.setTransId(headerDTO.getTransId());
			headerDAO.createLine(line);
			super.createReserved(transId, line.getBarcode());
		}
	}

	/**
	 * 初始化头信息
	 * 
	 * @param dto
	 * @return
	 */
	private LeaseHeaderDTO initHeaderData(LeaseHeaderDTO dto) {
		dto.setTransNo(AssetsWebAttributes.ORDER_AUTO_PROD); // 设置单据号
		dto.setCreatedBy(userAccount.getUserId()); // 设置创建人
		dto.setCreated(userAccount.getUsername()); // 设置创建人
		dto.setFromOrganizationId(userAccount.getOrganizationId());
		dto.setFromCompanyName(userAccount.getCompany());
		dto.setTransTypeValue(LeaseAppConstant.TRANS_TYPE_NAME);
		dto.setTransType(LeaseAppConstant.TRANS_TYPE);

		dto.setCurrCreationDate();
		dto.setEmail(userAccount.getEmail());
		dto.setPhoneNumber(userAccount.getMobilePhone());
		return dto;
	}

	/**
	 * 取详细数据
	 * 
	 * @throws QueryException
	 */
	public void prodData() throws QueryException {
		headerDTO = this.setFlowIdToDTO(headerDTO);
		prodHeader();
		prodLines();
	}

	/**
	 * 取头信息
	 * 
	 * @throws QueryException
	 */
	private void prodHeader() throws QueryException {
		headerDAO.setDTOClassName(LeaseHeaderDTO.class.getName());
		headerDAO.setCalPattern(CalendarConstant.LINE_PATTERN);

		LeaseHeaderDTO tmpDTO = (LeaseHeaderDTO) headerDTO.clone();

		headerDTO = (LeaseHeaderDTO) headerDAO.getDataByPrimaryKey();
		// 当新建时候
		if (null == headerDTO || StrUtil.isEmpty(headerDTO.getTransId())) {
			headerDTO = new LeaseHeaderDTO();
			headerDTO = initHeaderData(headerDTO);
		}
		headerDTO.setSf_task_attribute3(tmpDTO.getSf_task_attribute3());

		leaseDTO.setHeaderDTO(headerDTO);
	}

	/**
	 * 取行信息
	 * 
	 * @throws QueryException
	 */
	private void prodLines() throws QueryException {
		lines = headerDAO.getLinesData(headerDTO.getTransId());
		leaseDTO.setLines(lines);
	}

	public LeaseDTO getForm() throws QueryException {
		AssetsOptProducer optProducer = new AssetsOptProducer(userAccount, conn);
		if ("".equals(leaseDTO.getHeaderDTO().getEmergentLevel())) {
			leaseDTO.getHeaderDTO().setEmergentLevel("0");
		}
		String emergentLevelOption = optProducer.getAmsEmergentLevel(headerDTO
				.getEmergentLevel());
		headerDTO.setEmergentLevelOption(emergentLevelOption);
		leaseDTO.setHeaderDTO(headerDTO);
		leaseDTO.setLines(lines);
		return leaseDTO;
	}

	public void setForm(LeaseDTO leaseDTO) {
		this.leaseDTO = leaseDTO;
		this.lines = leaseDTO.getLines();
		this.headerDTO = leaseDTO.getHeaderDTO();
		this.headerDAO = new LeaseDAO(userAccount, headerDTO, conn);
	}

    /**
     * 功能：准备流程数据,由应用实现
     */
    protected void prepareProcedureData(){
        flowDTO.setApp_dataID(headerDTO.getTransId()); // 应用ID
        flowDTO.setPrimaryKey(headerDTO.getTransId()); // 应用ID
        flowDTO.setOrderNo(headerDTO.getTransNo()); // 应用的单据编号
        flowDTO.setOrderName(LeaseAppConstant.PROC_APP_NAME); // 应用的单据编号
    }


	/**
	 * 初始化
	 * 
	 * @param user
	 * @param dto
	 * @param conn
	 */
	private void init(SfUserDTO user, LeaseDTO dto, Connection conn) {
		this.leaseDTO = dto;
		this.lines = leaseDTO.getLines();
		this.headerDTO = leaseDTO.getHeaderDTO();
		this.headerDAO = new LeaseDAO(user, headerDTO, conn);
	}

	/**
	 * 将流程中保存的单据ID设置进DTO
	 * 
	 * @param dtoParameter
	 * @return
	 */
	private LeaseHeaderDTO setFlowIdToDTO(LeaseHeaderDTO dtoParameter) {
		if (StrUtil.isEmpty(dtoParameter.getTransId())) {
			dtoParameter.setTransId(StrUtil.nullToString(dtoParameter
					.getApp_dataID()));
		}
		return dtoParameter;
	}

	public File exportFile() throws DataTransException {
		return headerDAO.exportFile();
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
