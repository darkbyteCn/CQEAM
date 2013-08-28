package com.sino.ams.newasset.dao;

import com.sino.ams.appbase.dao.AMSProcedureBaseDAO;
import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.newasset.dto.AmsAssetsCheckBatchDTO;
import com.sino.ams.newasset.dto.AmsAssetsCheckHeaderDTO;
import com.sino.ams.newasset.dto.AmsAssetsCheckLineDTO;
import com.sino.ams.newasset.model.AmsAssetsCheckBatchModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.yearchecktaskmanager.util.AssetsCheckTaskOrderGeneretor;
import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.flow.bean.FlowAction;
import com.sino.flow.dto.FlowDTO;
import com.sino.framework.dto.BaseUserDTO;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class AmsAssetsCheckBatchDAO extends AMSProcedureBaseDAO {
	private DTOSet checkOrders = null;

	public AmsAssetsCheckBatchDAO(SfUserDTO userAccount,
			AmsAssetsCheckBatchDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		AmsAssetsCheckBatchDTO dto = (AmsAssetsCheckBatchDTO) dtoParameter;
		this.sqlProducer = new AmsAssetsCheckBatchModel(
				(SfUserDTO) userAccount, dto);
	}

	public boolean saveNewCheckOrders(DTOSet checkOrders) {
		boolean operateResult = false;
		boolean autoCommit = true;
		boolean isNewData = false;
		AmsAssetsCheckBatchDTO batchDTO = (AmsAssetsCheckBatchDTO) this.dtoParameter;
		try {
			autoCommit = this.conn.getAutoCommit();
			this.conn.setAutoCommit(false);
			String batchId = batchDTO.getBatchId();
			List leftOrders = new ArrayList();
			if (!(StrUtil.isEmpty(batchId)))
				leftOrders = getExistChkOrders();
			else
				isNewData = true;

			saveNewBatchData();
			if (checkOrders != null) {
				int orderCount = checkOrders.getSize();
				AmsAssetsCheckHeaderDTO headerDTO = null;
				AmsAssetsCheckHeaderDAO headerDAO = new AmsAssetsCheckHeaderDAO(
						this.userAccount, headerDTO, this.conn);
				headerDAO.setServletConfig(this.servletConfig);
				String headerId = "";
				for (int i = 0; i < orderCount; ++i) {
					headerDTO = (AmsAssetsCheckHeaderDTO) checkOrders.getDTO(i);
					headerId = headerDTO.getHeaderId();
					leftOrders.remove(headerId);
					headerDTO.setBatchId(batchDTO.getBatchId());
					headerDTO.setOrderStatus(batchDTO.getBatchStatus());
					headerDTO.setOrderType(batchDTO.getOrderType());
					headerDTO.setCostCenterCode(batchDTO.getCostCenterCode());
					// jeffery<2013-07-18>
					if (batchDTO.getTaskNumber().equals("")) {
						// fei year pk
					} else {
						headerDTO.setIsYear("Y");
					}

					// jeffery
					headerDAO.setDTOParameter(headerDTO);
					headerDAO.saveCheckHeader();
				}
			}
			if (!(leftOrders.isEmpty()))
				deleteLeftOrders(leftOrders);

			operateResult = processProcedure();
		} catch (DataHandleException ex) {
			ex.printLog();
			try {
				if (!(operateResult)) {
					this.conn.rollback();
					prodMessage("SAVE_CHECK_FAILURE");
					if (isNewData) {
						batchDTO.setBatchId("");
						setDTOParameter(batchDTO);
					}
				} else {
					this.conn.commit();
					prodMessage("SAVE_CHECK_SUCCESS");
				}
				this.message.setIsError(!(operateResult));
				this.conn.setAutoCommit(autoCommit);
			} catch (SQLException ex1) {
				Logger.logError(ex1);
			}
		} catch (SQLException ex) {
			Logger.logError(ex);
			try {
				if (!(operateResult)) {
					this.conn.rollback();
					prodMessage("SAVE_CHECK_FAILURE");
					if (isNewData) {
						batchDTO.setBatchId("");
						setDTOParameter(batchDTO);
					}
				} else {
					this.conn.commit();
					prodMessage("SAVE_CHECK_SUCCESS");
				}
				this.message.setIsError(!(operateResult));
				this.conn.setAutoCommit(autoCommit);
			} catch (SQLException ex1) {
				Logger.logError(ex1);
			}
		} catch (QueryException ex) {
			Logger.logError(ex);
			try {
				if (!(operateResult)) {
					this.conn.rollback();
					prodMessage("SAVE_CHECK_FAILURE");
					if (isNewData) {
						batchDTO.setBatchId("");
						setDTOParameter(batchDTO);
					}
				} else {
					this.conn.commit();
					prodMessage("SAVE_CHECK_SUCCESS");
				}
				this.message.setIsError(!(operateResult));
				this.conn.setAutoCommit(autoCommit);
			} catch (SQLException ex1) {
				Logger.logError(ex1);
			}
		} finally {
			try {
				if (!(operateResult)) {
					this.conn.rollback();
					prodMessage("SAVE_CHECK_FAILURE");
					if (isNewData) {
						batchDTO.setBatchId("");
						setDTOParameter(batchDTO);
					}
				} else {
					this.conn.commit();
					prodMessage("SAVE_CHECK_SUCCESS");
				}
				this.message.setIsError(!(operateResult));
				this.conn.setAutoCommit(autoCommit);
			} catch (SQLException ex1) {
				Logger.logError(ex1);
			}
		}
		return operateResult;
	}

	private void saveNewBatchData() throws DataHandleException {
		AmsAssetsCheckBatchDTO batchDTO;
		try {
			batchDTO = (AmsAssetsCheckBatchDTO) this.dtoParameter;
			String batchId = batchDTO.getBatchId();
			if (StrUtil.isEmpty(batchId)) {
				SeqProducer seqProducer = new SeqProducer(this.conn);
				batchId = seqProducer.getGUID();
				batchDTO.setBatchId(batchId);
				batchDTO.setCreatedBy(this.userAccount.getUserId());
				batchDTO.setCurrCreationDate();
				String companyCode = this.userAccount.getCompanyCode();
				String transType = "CHK-TASK";
				AssetsCheckTaskOrderGeneretor numberProducer = new AssetsCheckTaskOrderGeneretor(
						this.conn, companyCode, transType);
				numberProducer.setOrderLength(3);
				batchDTO.setBatchNo(numberProducer.getOrderNum());
				setDTOParameter(batchDTO);
				createData();

				AmsAssetsCheckBatchModel modelProducer = (AmsAssetsCheckBatchModel) this.sqlProducer;
				SQLModel sqlModel = modelProducer.getInsertTaskModel(batchId,
						batchDTO.getTaskNumber());
				DBOperator.updateRecord(sqlModel, this.conn);
				return;
			}

			batchDTO.setBatchStatus("IN_PROCESS");
			if (batchDTO.getSf_end().equals("1"))
				batchDTO.setBatchStatus("DISTRIBUTED");

			updateData();
		} catch (SQLException ex) {
			Logger.logError(ex);
			throw new DataHandleException(ex);
		}
	}

	public boolean hasPrevOrders(DTOSet checkOrders) throws QueryException {
		boolean hasPrevOrders = false;
		try {
			AmsAssetsCheckHeaderDAO headerDAO = new AmsAssetsCheckHeaderDAO(
					this.userAccount, null, this.conn);
			headerDAO.setServletConfig(this.servletConfig);
			if ((checkOrders == null) || (checkOrders.getSize() <= 0)){
			int orderCount = checkOrders.getSize();
			AmsAssetsCheckHeaderDTO headerDTO = null;
			AmsAssetsCheckBatchDTO dto = (AmsAssetsCheckBatchDTO) this.dtoParameter;
			for (int i = 0; i < orderCount; ++i) {
				headerDTO = (AmsAssetsCheckHeaderDTO) checkOrders.getDTO(i);
				headerDTO.setCostCenterCode(dto.getCostCenterCode());
				headerDAO.setDTOParameter(headerDTO);
				if (headerDAO.hasPreviousOrder()) {
					hasPrevOrders = true;
					headerDTO.setHasPreviousOrder(hasPrevOrders);
					checkOrders.set(i, headerDTO);
				}
			}
			this.checkOrders = checkOrders;
			if (!(hasPrevOrders))
			prodMessage("SUBMIT_DATA_INVALID");
			}
		} catch (DTOException ex) {
			ex.printLog();
			throw new QueryException(ex);
		}
		 return hasPrevOrders;
	}

	public DTOSet getSubmitedOrders() throws QueryException {
		AmsAssetsCheckHeaderDTO chkOrder;
		try {
			chkOrder = null;
			AssetsOptProducer optProducer = new AssetsOptProducer(
					this.userAccount, this.conn);
			String chkCategoryOpt = "";
			if (this.checkOrders == null) {
				for (int i = 0; i < this.checkOrders.getSize(); ++i) {
					chkOrder = (AmsAssetsCheckHeaderDTO) this.checkOrders
							.getDTO(i);
					chkCategoryOpt = optProducer.getChkCategoryOption(chkOrder
							.getCheckCategory());
					chkOrder.setCheckCategoryOpt(chkCategoryOpt);
					this.checkOrders.set(i, chkOrder);
				}
			}
		} catch (DTOException ex) {
			ex.printLog();
			throw new QueryException(ex);
		}
		return this.checkOrders;
	}

	public boolean submitNewCheckOrders(DTOSet checkOrders) {
		String batchStatus;
		boolean operateResult = false;
		boolean autoCommit = true;
		boolean dataCreated = false;
		AmsAssetsCheckBatchDTO batchDTO = (AmsAssetsCheckBatchDTO) this.dtoParameter;
		try {
			String batchId = batchDTO.getBatchId();
			autoCommit = this.conn.getAutoCommit();
			this.conn.setAutoCommit(false);
			List leftOrders = new ArrayList();
			if (!(StrUtil.isEmpty(batchId)))
				leftOrders = getExistChkOrders();
			else
				dataCreated = true;

			batchDTO = (AmsAssetsCheckBatchDTO) this.dtoParameter;
			if (batchDTO.getSf_end().equals("1")) {
				batchDTO.setBatchStatus("DISTRIBUTED");
				batchDTO.setFlow2End(true);
			}
			saveNewBatchData();
			if (checkOrders != null) {
				int orderCount = checkOrders.getSize();
				AmsAssetsCheckHeaderDTO headerDTO = null;
				AmsAssetsCheckHeaderDAO headerDAO = new AmsAssetsCheckHeaderDAO(
						this.userAccount, headerDTO, this.conn);
				headerDAO.setServletConfig(this.servletConfig);
				String headerId = "";
				for (int i = 0; i < orderCount; ++i) {
					headerDTO = (AmsAssetsCheckHeaderDTO) checkOrders.getDTO(i);
					headerId = headerDTO.getHeaderId();
					leftOrders.remove(headerId);
					headerDTO.setBatchId(batchDTO.getBatchId());
					headerDTO.setOrderStatus(batchDTO.getBatchStatus());
					headerDTO.setFlow2End(batchDTO.isFlow2End());
					headerDTO.setOrderType(batchDTO.getOrderType());
					headerDTO.setCostCenterCode(batchDTO.getCostCenterCode());
					// jeffery<2013-07-18>
					if (batchDTO.getTaskNumber().equals("")) {
						// fei year pk
					} else {
						headerDTO.setIsYear("Y");
					}
					headerDAO.setDTOParameter(headerDTO);
					headerDAO.saveCheckHeader();
				}
			}
			if (!(leftOrders.isEmpty()))
				deleteLeftOrders(leftOrders);

			operateResult = processProcedure();
		} catch (DataHandleException ex) {
			ex.printLog();
			try {
				batchStatus = batchDTO.getBatchStatus();
				if (!(operateResult)) {
					this.conn.rollback();
					if (batchStatus.equals("DISTRIBUTED"))
						prodMessage("DIST_ORDER_FAILURE");
					else
						prodMessage("SUBMIT_CHECK_FAILURE");

					if (dataCreated) {
						batchDTO.setBatchId("");
						setDTOParameter(batchDTO);
					}
				} else {
					this.conn.commit();
					if (batchStatus.equals("DISTRIBUTED"))
						prodMessage("DIST_ORDER_SUCCESS");
					else
						prodMessage("SUBMIT_CHECK_SUCCESS");
				}

				this.message.setIsError(!(operateResult));
				this.conn.setAutoCommit(autoCommit);
			} catch (SQLException ex1) {
				Logger.logError(ex1);
			}
		} catch (SQLException ex) {
			Logger.logError(ex);
			try {
				batchStatus = batchDTO.getBatchStatus();
				if (!(operateResult)) {
					this.conn.rollback();
					if (batchStatus.equals("DISTRIBUTED"))
						prodMessage("DIST_ORDER_FAILURE");
					else
						prodMessage("SUBMIT_CHECK_FAILURE");

					if (dataCreated) {
						batchDTO.setBatchId("");
						setDTOParameter(batchDTO);
					}
				} else {
					this.conn.commit();
					if (batchStatus.equals("DISTRIBUTED"))
						prodMessage("DIST_ORDER_SUCCESS");
					else
						prodMessage("SUBMIT_CHECK_SUCCESS");
				}

				this.message.setIsError(!(operateResult));
				this.conn.setAutoCommit(autoCommit);
			} catch (SQLException ex1) {
				Logger.logError(ex1);
			}
		} catch (QueryException ex) {
			Logger.logError(ex);
			try {
				batchStatus = batchDTO.getBatchStatus();
				if (!(operateResult)) {
					this.conn.rollback();
					if (batchStatus.equals("DISTRIBUTED"))
						prodMessage("DIST_ORDER_FAILURE");
					else
						prodMessage("SUBMIT_CHECK_FAILURE");

					if (dataCreated) {
						batchDTO.setBatchId("");
						setDTOParameter(batchDTO);
					}
				} else {
					this.conn.commit();
					if (batchStatus.equals("DISTRIBUTED"))
						prodMessage("DIST_ORDER_SUCCESS");
					else
						prodMessage("SUBMIT_CHECK_SUCCESS");
				}

				this.message.setIsError(!(operateResult));
				this.conn.setAutoCommit(autoCommit);
			} catch (SQLException ex1) {
				Logger.logError(ex1);
			}
		} finally {
			try {
				batchStatus = batchDTO.getBatchStatus();
				if (!(operateResult)) {
					this.conn.rollback();
					if (batchStatus.equals("DISTRIBUTED"))
						prodMessage("DIST_ORDER_FAILURE");
					else
						prodMessage("SUBMIT_CHECK_FAILURE");

					if (dataCreated) {
						batchDTO.setBatchId("");
						setDTOParameter(batchDTO);
					}
				} else {
					this.conn.commit();
					if (batchStatus.equals("DISTRIBUTED"))
						prodMessage("DIST_ORDER_SUCCESS");
					else
						prodMessage("SUBMIT_CHECK_SUCCESS");
				}

				this.message.setIsError(!(operateResult));
				this.conn.setAutoCommit(autoCommit);
			} catch (SQLException ex1) {
				Logger.logError(ex1);
			}
		}
		return operateResult;
	}

	private List getExistChkOrders() throws QueryException {
		List headerIds = new ArrayList();
		try {
			AmsAssetsCheckBatchDTO dto = (AmsAssetsCheckBatchDTO) this.dtoParameter;
			AmsAssetsCheckHeaderDTO headerDTO = new AmsAssetsCheckHeaderDTO();
			headerDTO.setBatchId(dto.getBatchId());
			AmsAssetsCheckHeaderDAO headerDAO = new AmsAssetsCheckHeaderDAO(
					this.userAccount, headerDTO, this.conn);
			headerDAO.setDTOClassName(AmsAssetsCheckHeaderDTO.class.getName());
			DTOSet existOrders = (DTOSet) headerDAO
					.getDataByForeignKey("batchId");
			if ((existOrders == null) || (existOrders.isEmpty()))
			headerIds = existOrders.toList("headerId");
		} catch (DTOException ex) {
			ex.printLog();
			throw new QueryException(ex);
		}
		label112: return headerIds;
	}

	private void deleteLeftOrders(List headerIds) throws DataHandleException {
		AmsAssetsCheckHeaderDAO headerDAO = new AmsAssetsCheckHeaderDAO(
				this.userAccount, null, this.conn);
		AmsAssetsCheckLineDAO lineDAO = new AmsAssetsCheckLineDAO(
				this.userAccount, null, this.conn);
		AmsAssetsCheckHeaderDTO headerDTO = new AmsAssetsCheckHeaderDTO();
		AmsAssetsCheckLineDTO lineDTO = new AmsAssetsCheckLineDTO();
		String headerId = "";
		for (int i = 0; i < headerIds.size(); ++i) {
			headerId = (String) headerIds.get(i);

			lineDTO.setHeaderId(headerId);
			lineDAO.setDTOParameter(lineDTO);
			lineDAO.DeleteByForeignKey("headerId");

			headerDTO.setHeaderId(headerId);
			headerDAO.setDTOParameter(headerDTO);
			headerDAO.deleteByPrimaryKey();
		}
	}

	public boolean distributeChkOrder(String[] batchIds) {
		boolean operateResult = false;
		boolean autoCommit = false;
		try {
			int batchCount = batchIds.length;
			AmsAssetsCheckBatchDTO batchDTO = null;
			AmsAssetsCheckHeaderDTO headerDTO = null;
			SQLModel sqlModel = null;
			autoCommit = this.conn.getAutoCommit();
			this.conn.setAutoCommit(false);
			AmsAssetsCheckBatchModel modelProducer = (AmsAssetsCheckBatchModel) this.sqlProducer;
			AmsAssetsCheckHeaderDAO orderDAO = new AmsAssetsCheckHeaderDAO(
					this.userAccount, null, this.conn);

			FlowDTO flowDTO = new FlowDTO();
			flowDTO.setProcName("资产盘点流程");
			FlowAction flowProcessor = new FlowAction(this.conn);

			for (int i = 0; i < batchCount; ++i) {
				batchDTO = new AmsAssetsCheckBatchDTO();
				batchDTO.setBatchId(batchIds[i]);
				batchDTO.setBatchStatus("DISTRIBUTED");
				batchDTO.setBatchStatusName("已下发");
				setDTOParameter(batchDTO);

				sqlModel = modelProducer.getDistributeModel();
				DBOperator.updateRecord(sqlModel, this.conn);

				headerDTO = new AmsAssetsCheckHeaderDTO();
				headerDTO.setBatchId(batchDTO.getBatchId());
				headerDTO.setOrderStatus("DISTRIBUTED");
				orderDAO.setDTOParameter(headerDTO);
				orderDAO.distributeChkOrder();

				flowDTO.setApplyId(batchIds[i]);
				flowProcessor.setDto(flowDTO);
				flowProcessor.cancel();
			}
			operateResult = true;
		} catch (DataHandleException ex) {
			ex.printLog();
			try {
				if (operateResult) {
					this.conn.commit();
					prodMessage("DIST_ORDER_SUCCESS");
				} else {
					this.conn.rollback();
					if (this.message == null)
						prodMessage("DIST_ORDER_FAILURE");
				}

				this.conn.setAutoCommit(autoCommit);
				this.message.setIsError(!(operateResult));
			} catch (SQLException ex1) {
				Logger.logError(ex1);
				prodMessage("ROLL_BACK_ERROR");
				this.message.setIsError(true);
			}
		} catch (SQLException ex) {
			Logger.logError(ex);
			try {
				if (operateResult) {
					this.conn.commit();
					prodMessage("DIST_ORDER_SUCCESS");
				} else {
					this.conn.rollback();
					if (this.message == null)
						prodMessage("DIST_ORDER_FAILURE");
				}

				this.conn.setAutoCommit(autoCommit);
				this.message.setIsError(!(operateResult));
			} catch (SQLException ex1) {
				Logger.logError(ex1);
				prodMessage("ROLL_BACK_ERROR");
				this.message.setIsError(true);
			}
		} finally {
			try {
				if (operateResult) {
					this.conn.commit();
					prodMessage("DIST_ORDER_SUCCESS");
				} else {
					this.conn.rollback();
					if (this.message == null)
						prodMessage("DIST_ORDER_FAILURE");
				}

				this.conn.setAutoCommit(autoCommit);
				this.message.setIsError(!(operateResult));
			} catch (SQLException ex1) {
				Logger.logError(ex1);
				prodMessage("ROLL_BACK_ERROR");
				this.message.setIsError(true);
			}
		}
		return operateResult;
	}

	public boolean cancelCheckTask() throws SQLException, DataHandleException,
			QueryException, SQLModelException, ContainerException,
			ParseException {
		boolean operateResult = false;
		boolean autoCommit = true;
		try {
			autoCommit = this.conn.getAutoCommit();
			this.conn.setAutoCommit(false);

			AmsAssetsCheckBatchModel modelProducer = (AmsAssetsCheckBatchModel) this.sqlProducer;
			SQLModel sqlModel = modelProducer.getBatchCancelModel();
			DBOperator.updateRecord(sqlModel, this.conn);

			AmsAssetsCheckBatchDTO batchDTO = (AmsAssetsCheckBatchDTO) this.dtoParameter;
			sqlModel = modelProducer
					.getHeaderCancelModel(batchDTO.getBatchId());
			DBOperator.updateRecord(sqlModel, this.conn);

			operateResult = super.cancelProcedure();
		} catch (DataHandleException ex) {
			ex.printLog();
			try {
				if (operateResult) {
					this.conn.commit();
					prodMessage("ORDER_CANCEL_SUCCESS");
				} else {
					this.conn.rollback();
					prodMessage("ORDER_CANCEL_FAILURE");
				}
				this.conn.setAutoCommit(autoCommit);
				this.message.addParameterValue("盘点任务");
				this.message.setIsError(!(operateResult));
			} catch (SQLException ex1) {
				Logger.logError(ex1);
				prodMessage("ROLL_BACK_ERROR");
			}
		} catch (SQLException ex) {
			Logger.logError(ex);
			try {
				if (operateResult) {
					this.conn.commit();
					prodMessage("ORDER_CANCEL_SUCCESS");
				} else {
					this.conn.rollback();
					prodMessage("ORDER_CANCEL_FAILURE");
				}
				this.conn.setAutoCommit(autoCommit);
				this.message.addParameterValue("盘点任务");
				this.message.setIsError(!(operateResult));
			} catch (SQLException ex1) {
				Logger.logError(ex1);
				prodMessage("ROLL_BACK_ERROR");
			}
		} finally {
			try {
				if (operateResult) {
					this.conn.commit();
					prodMessage("ORDER_CANCEL_SUCCESS");
				} else {
					this.conn.rollback();
					prodMessage("ORDER_CANCEL_FAILURE");
				}
				this.conn.setAutoCommit(autoCommit);
				this.message.addParameterValue("盘点任务");
				this.message.setIsError(!(operateResult));
			} catch (SQLException ex1) {
				Logger.logError(ex1);
				prodMessage("ROLL_BACK_ERROR");
			}
		}
		return operateResult;
	}

	public RowSet getImpLocation(String locationCode) throws QueryException,
			SQLModelException {
		AmsAssetsCheckBatchModel modelProducer = (AmsAssetsCheckBatchModel) this.sqlProducer;
		SQLModel sqlModel = modelProducer.getImpLocationModel(locationCode);
		SimpleQuery sq = new SimpleQuery(sqlModel, this.conn);
		sq.executeQuery();
		sq.setTotalSummary(false);
		RowSet rows = sq.getSearchResult();
		return rows;
	}

	protected void prepareProcedureData() {
		AmsAssetsCheckBatchDTO dto = (AmsAssetsCheckBatchDTO) this.dtoParameter;
		dto.setPrimaryKey(dto.getBatchId());
		dto.setOrderNo(dto.getBatchNo());
		dto.setOrderName(dto.getOrderTypeName());
	}
}