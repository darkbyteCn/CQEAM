package com.sino.ams.spare.dao;

import com.sino.ams.appbase.dao.AMSProcedureBaseDAO;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.spare.dto.AmsItemTransHDTO;
import com.sino.ams.spare.dto.AmsItemTransLDTO;
import com.sino.ams.spare.model.AmsItemTransHModel;
import com.sino.ams.spare.model.BjfxsqModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.flow.bean.FlowAction;
import com.sino.flow.dto.FlowDTO;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.sms.bean.MessageSaver;
import com.sino.sms.constant.SMSConstant;
import com.sino.sms.dto.SfMsgDefineDTO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import com.sino.ams.spare.assistant.SpareMessageKeys;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-12-02
 * Time: 00:00:00
 * To change this template use File | Settings | File Templates.
 */
public class BjslApproveDAO extends AMSProcedureBaseDAO {

	public BjslApproveDAO(SfUserDTO userAccount, AmsItemTransHDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		AmsItemTransHDTO dto = (AmsItemTransHDTO) dtoParameter;
		sqlProducer = new AmsItemTransHModel((SfUserDTO) userAccount, dto);
	}

	public void updateOut(DTOSet lineSet) throws SQLException {
		CallableStatement cStmt = null;
		String sqlStr = "{call AMS_ITEM_TRANS_SX.INV_DAIXIU_OUT(?,?,?,?)}";
		AmsItemTransHDTO headerDTO = (AmsItemTransHDTO)dtoParameter;
		try {
			cStmt = conn.prepareCall(sqlStr);
			for (int i = 0; i < lineSet.getSize(); i++) {
				AmsItemTransLDTO dto = (AmsItemTransLDTO) lineSet.getDTO(i);
				cStmt = conn.prepareCall(sqlStr);
				cStmt.setString(1, dto.getBarcode());
				cStmt.setInt(2, headerDTO.getFromOrganizationId());
				cStmt.setInt(3, dto.getQuantity());
				cStmt.setInt(4, userAccount.getUserId());
				cStmt.execute();
			}
		} finally {
			DBManager.closeDBStatement(cStmt);
		}
	}

	public void updateIn(DTOSet lineSet) throws SQLException {
		CallableStatement cStmt = null;
		String sqlStr = "{call AMS_ITEM_TRANS_SX.INV_SX_IN(?,?,?)}";
		try {
			cStmt = conn.prepareCall(sqlStr);
			for (int i = 0; i < lineSet.getSize(); i++) {
				AmsItemTransLDTO dto = (AmsItemTransLDTO) lineSet.getDTO(i);
				cStmt = conn.prepareCall(sqlStr);
				cStmt.setString(1, dto.getBarcode());
				cStmt.setInt(2, dto.getQuantity());
				cStmt.setInt(3, userAccount.getUserId());
				cStmt.execute();
			}
		} finally {
			DBManager.closeDBStatement(cStmt);
		}
	}

	/**
	 * 功能:备件申领流程
	 * @param lineSet DTOSet
	 * @return operateResult
	 * @throws SQLException
	 */
	public boolean approveOrder(DTOSet lineSet, AmsItemTransHDTO transDTO) throws SQLException, ContainerException {
		boolean operateResult = false;
		boolean autoCommit = false;
		AmsItemTransHDTO headerDTO = (AmsItemTransHDTO)dtoParameter;
		try {
			autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
            if (transDTO.getSf_end().equals("1")) {
                buildOrders();
				String status = DictConstant.COMPLETED;
				headerDTO.setTransStatus(DictConstant.COMPLETED);
				BjfxsqModel model = new BjfxsqModel(userAccount, headerDTO);
				DBOperator.updateRecord(model.updateStatusModel(status), conn);
				saveDifQty(lineSet);
//				saveMessage(); //发送手机短信  暂时不用
            }
            transDTO.setPrimaryKey(headerDTO.getTransId());
            transDTO.setOrderNo(headerDTO.getTransNo());
            transDTO.setOrderName("备件申领");
            processProcedure(true);
			operateResult = true;
		} catch (SQLException e) {
			Logger.logError(e);
		} catch (QueryException e) {
			Logger.logError(e);
		} catch (DataHandleException e) {
			Logger.logError(e);
		} finally {
			if(operateResult){
				conn.commit();
				prodMessage(SpareMessageKeys.APPLY_APPROVE_SUCCESS);
			} else {
				conn.rollback();
				prodMessage(SpareMessageKeys.APPLY_APPROVE_FAILURE);
				message.setNeedClose(true);
			}
			message.addParameterValue(headerDTO.getTransNo());
			message.setIsError(!operateResult);
			conn.setAutoCommit(autoCommit);
		}
		return operateResult;
	}


	/**
	 * 功能：发送手机短信给备件调拨人
	 * @throws DataHandleException
	 */
	private void saveMessage() throws DataHandleException {
		String userId = "";
		String orderNum = "";
		String userName = "";
		String userTel = "";
		String transId = "";
		Row row = null;
		Row rowh = null;
		AmsItemTransHDTO headerDTO = (AmsItemTransHDTO)dtoParameter;
		try {
			MessageSaver msgSaver = new MessageSaver(conn);
			RowSet rowss = getDBhead(conn, headerDTO.getTransId());
			if (rowss != null && rowss.getSize() > 0) {
				for (int k = 0; k < rowss.getSize(); k++) {
					rowh = rowss.getRow(k);
					transId = rowh.getStrValue("TRANS_ID");
					orderNum = rowh.getStrValue("TRANS_NO");
					String fromOrgId = getFromOrgId(conn, transId);
					String toCompanyName = getToCompanyName(conn, transId);
					if (!StrUtil.isEmpty(fromOrgId)) {
						RowSet rows = getUserNames(conn, fromOrgId);
						if (rows != null && rows.getSize() > 0) {
							for (int i = 0; i < rows.getSize(); i++) {
								row = rows.getRow(i);
								userId = row.getValue("USER_ID").toString();
								userName = row.getValue("USERNAME").toString();
								userTel = getMovetel(conn, userId);
								SfMsgDefineDTO msgDefineDTO = new SfMsgDefineDTO();
								msgDefineDTO.setMsgCategoryId(SMSConstant.SPARE_ALLOT_ID);
								msgDefineDTO.setCreatedBy(userAccount.getUserId());
								msgDefineDTO.setCellPhone(userTel);
								msgDefineDTO.setApplyNumber(orderNum);
								msgDefineDTO.setUserId(userId);
								msgDefineDTO.setMsgContent("您好" + userName + ": 请安排贵公司正常仓库调拨给" + toCompanyName + "公司，调拨单号:" + orderNum);
								msgSaver.saveMsg(msgDefineDTO);
							}
						}
					}
				}
			}
		} catch (QueryException ex) {
			ex.printLog();
			throw new DataHandleException(ex);
		} catch (ContainerException ex) {
			ex.printLog();
			throw new DataHandleException(ex);
		} catch (Exception ex) {
			Logger.logError(ex);
			throw new DataHandleException(ex);
		}
	}


	private String getFromOrgId(Connection conn, String transId) throws QueryException, ContainerException {
		String fromOrgId = "";
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = " SELECT" +
				" AIAH.FROM_ORGANIZATION_ID\n" +
				" FROM " +
				" AMS_ITEM_ALLOCATE_H AIAH\n" +
				" WHERE " +
				" AIAH.TRANS_ID = ?";
		sqlArgs.add(transId);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		SimpleQuery sq = new SimpleQuery(sqlModel, conn);
		sq.executeQuery();
		Row row = sq.getFirstRow();
		fromOrgId = row.getValue("FROM_ORGANIZATION_ID").toString();
		return fromOrgId;
	}

	/**
	 * 功能：获得派送的公司的名称
	 * @param conn
	 * @param transId
	 * @return
	 * @throws QueryException
	 * @throws ContainerException
	 */
	private String getToCompanyName(Connection conn, String transId) throws QueryException, ContainerException {
		String toCompanyName = "";
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = " SELECT " +
				" AIAH.TO_ORGANIZATION_ID,\n" +
				" AMS_PUB_PKG.GET_ORGNIZATION_NAME(AIAH.TO_ORGANIZATION_ID) COMPANY_ANME\n" +
				" FROM " +
				" AMS_ITEM_ALLOCATE_H AIAH\n" +
				" WHERE " +
				" AIAH.TRANS_ID = ?";
		sqlArgs.add(transId);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		SimpleQuery sq = new SimpleQuery(sqlModel, conn);
		sq.executeQuery();
		Row row = sq.getFirstRow();
		toCompanyName = row.getValue("COMPANY_ANME").toString();
		return toCompanyName;
	}


	private RowSet getUserNames(Connection conn, String orgId) throws QueryException, ContainerException {
		String fromOrgId = "";
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT DISTINCT" +
				" SU.USER_ID,\n" +
				" SU.USERNAME,\n" +
				" SU.ORGANIZATION_ID,\n" +
				" AMS_PUB_PKG.GET_ORGNIZATION_NAME(SU.ORGANIZATION_ID)\n" +
				" FROM " +
				" SF_USER SU, SF_USER_RIGHT SUR, SF_ROLE SR\n" +
				" WHERE " +
				" SUR.ROLE_ID = SR.ROLE_ID\n" +
				" AND SUR.USER_ID = SU.USER_ID\n" +
				" AND (SR.ROLENAME LIKE '%地市备品备件管理员%' OR SR.ROLENAME LIKE '%省公司备品备件管理员%')\n" +
				" AND SU.ORGANIZATION_ID = ?";
		sqlArgs.add(orgId);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		SimpleQuery sq = new SimpleQuery(sqlModel, conn);
		sq.executeQuery();
		return sq.getSearchResult();
	}


	/**
	 * 功能: 获得调拨单的rowset
	 * @param conn
	 * @param sourceId
	 * @return
	 * @throws QueryException
	 * @throws ContainerException
	 */
	private RowSet getDBhead(Connection conn, String sourceId) throws QueryException, ContainerException {
		String fromOrgId = "";
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = " SELECT " +
				" AIAH.TRANS_ID,\n" +
				" AIAH.TRANS_NO" +
				" FROM " +
				" AMS_ITEM_ALLOCATE_H AIAH\n" +
				" WHERE " +
				" AIAH.SOURCE_ID = ?";
		sqlArgs.add(sourceId);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		SimpleQuery sq = new SimpleQuery(sqlModel, conn);
		sq.executeQuery();
		return sq.getSearchResult();
	}

	/**
	 * @param conn
	 * @param userId
	 * @return
	 * @throws QueryException
	 * @throws ContainerException
	 */
	private String getMovetel(Connection conn, String userId) throws QueryException, ContainerException {
		String movetel = "";
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = " SELECT DISTINCT SU.MOVETEL FROM SF_USER SU WHERE SU.USER_ID = ?";
		sqlArgs.add(userId);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		SimpleQuery sq = new SimpleQuery(sqlModel, conn);
		sq.executeQuery();
		Row row = sq.getFirstRow();
		movetel = row.getValue("MOVETEL").toString();
		return movetel;
	}

	/**
	 * 保存条码信息
	 * @param lineSet DTOSet
	 * @throws DataHandleException
	 */
	public void saveLines(DTOSet lineSet) throws DataHandleException {
		if (lineSet != null && !lineSet.isEmpty()) {
			AmsItemTransLDAO lineDAO = new AmsItemTransLDAO(userAccount, null, conn);
			AmsItemTransHDTO headerDTO = (AmsItemTransHDTO)dtoParameter;
			for (int i = 0; i < lineSet.getSize(); i++) {
				AmsItemTransLDTO lineData = (AmsItemTransLDTO) lineSet.getDTO(i);
				lineData.setTransId(headerDTO.getTransId());
				lineDAO.setDTOParameter(lineData);
				lineDAO.createData();
			}
		}
	}

	/**
	 * 更新单据状态
	 * @param status 状态
	 * @throws DataHandleException
	 */
	public void updateStatus(String status) throws DataHandleException {
		String sqlStr = " UPDATE AMS_ITEM_TRANS_H\n" +
				" SET TRANS_STATUS = ?" +
				" WHERE TRANS_ID = ?";
		List list = new ArrayList();
		list.add(status);
		AmsItemTransHDTO headerDTO = (AmsItemTransHDTO)dtoParameter;
		list.add(headerDTO.getTransId());
		SQLModel sqlModel = new SQLModel();
		sqlModel.setArgs(list);
		sqlModel.setSqlStr(sqlStr);
		DBOperator.updateRecord(sqlModel, conn);
	}

	/**
	 * 写保留量
	 * @param lineSet DTOSet
	 * @throws DataHandleException
	 */
	private void writeReservQty(DTOSet lineSet) throws DataHandleException {
		if (lineSet != null && !lineSet.isEmpty()) {
			Map rservedItem = new HashMap();

			String itemCode = "";
			for (int i = 0; i < lineSet.getSize(); i++) {
				int count = 0;
				AmsItemTransLDTO lineData = (AmsItemTransLDTO) lineSet.getDTO(i);
				itemCode = lineData.getItemCode();
				if (rservedItem.get(itemCode) == null) {
					count++;
				} else {
					count = Integer.parseInt(rservedItem.get(itemCode).toString()) + 1;
				}
				rservedItem.put(itemCode, String.valueOf(count));
			}
			Iterator keys = rservedItem.keySet().iterator();
			Object key = null;
			String resItem = "";
			String resCount = "";
			String sqlStr = " INSERT INTO AMS_ITEM_RESERVED VALUES(?,SYSDATE,?,?)";
			SQLModel sqlModel = new SQLModel();
			sqlModel.setSqlStr(sqlStr);
			AmsItemTransHDTO headerDTO = (AmsItemTransHDTO)dtoParameter;
			while (keys.hasNext()) {
				key = keys.next();
				resItem = (String) key;
				resCount = (String) rservedItem.get(key);
				List args = new ArrayList();
				args.add(headerDTO.getTransId());
				args.add(resItem);
				args.add(resCount);
				sqlModel.setArgs(args);
				DBOperator.updateRecord(sqlModel, conn);
			}

		}
	}


	/**
	 * 退回
	 * @param flowDTO FlowDTO
	 * @throws SQLException
	 */
	public boolean reject(FlowDTO flowDTO, DTOSet lineSet) throws SQLException {
		boolean autoCommit = true;
		boolean operateResult = false;
		AmsItemTransHDTO dto = (AmsItemTransHDTO) dtoParameter;
		try {
			autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			updateStatus(DictConstant.REJECTED);
            DTOSet dtoSet = getTransDQuenty(dto);//查找退回单据保留量减少(正常库)
            cancelTrandDQuery(dtoSet);
            cancelFP(dto);//退回单据撤销分配行信息
            FlowAction fb = new FlowAction(conn, flowDTO);
			fb.reject2Begin();
			operateResult = true;
		} catch (DataHandleException e) {
			throw new SQLException(e.getMessage());
		} catch (SQLModelException e) {
            throw new SQLException(e.getMessage());
        } catch (QueryException e) {
            throw new SQLException(e.getMessage());
        } finally {
			if(operateResult){
				conn.commit();
				prodMessage(SpareMessageKeys.APPLY_REJECT_SUCCESS);
			} else {
				conn.rollback();
				prodMessage(SpareMessageKeys.APPLY_REJECT_FAILURE);
				message.setNeedClose(true);
			}
			message.addParameterValue(dto.getTransNo());
			message.setIsError(!operateResult);
			conn.setAutoCommit(autoCommit);
		}
        return operateResult;
    }

	/**
	 * 生成调拨单和分配单
	 * @param flowDTO FlowDTO
	 * @return operateResult
	 * @throws SQLException
	 */
	public boolean submitOrder(FlowDTO flowDTO) throws SQLException {
		boolean operateResult = false;
		boolean autoCommit = true;
		AmsItemTransHDTO headerDTO = (AmsItemTransHDTO)dtoParameter;
		try {
			autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			updateStatus(headerDTO.getTransStatus());
			buildOrders();
			//流程处理
			FlowAction fb = new FlowAction(conn, flowDTO);
			fb.flow();
			conn.commit();
			operateResult = true;
		} catch (DataHandleException e) {
			conn.rollback();
			Logger.logError(e);
			prodMessage("SQL_ERROR");
		} catch (SQLException e) {
			conn.rollback();
			Logger.logError(e);
			prodMessage("SQL_ERROR");
		} catch (QueryException e) {
			conn.rollback();
			Logger.logError(e);
			prodMessage("SQL_ERROR");
		} finally {
			conn.setAutoCommit(autoCommit);
		}
		return operateResult;
	}

	public void buildOrders() throws SQLException {
		CallableStatement cStmt = null;
		String sqlStr = "{call dbo.AITS_BUILD_BJSL_ORDERS(?,?)}";
		AmsItemTransHDTO headerDTO = (AmsItemTransHDTO)dtoParameter;
		try {
			cStmt = conn.prepareCall(sqlStr);
			cStmt.setString(1, headerDTO.getTransId());
			cStmt.setInt(2, userAccount.getUserId());
			cStmt.execute();
		} finally {
			DBManager.closeDBStatement(cStmt);
		}
	}

	public void writeDetails(String lineId, String barcode, String transId, String orgId[], String HoldQty[], String detailId[], String objectNo[]) throws SQLException, DataHandleException {
		CallableStatement cStmt = null;
		String sqlStr = "{CALL dbo.AITS_SAVE_FXSQ_DETAIL(?,?,?,?,?,?,?,?)}";
		try {
			cStmt = conn.prepareCall(sqlStr);
			for (int i = 0; i < orgId.length; i++) {
				if (HoldQty[i].equals("")) {
					BjfxsqModel model = new BjfxsqModel(userAccount, null);
					DBOperator.updateRecord(model.deleteData(detailId[i], transId), conn);
				} else {
                    int organizationId = Integer.parseInt(orgId[i]);
                    int qty = Integer.parseInt(HoldQty[i]);
					cStmt.setString(1, transId);
					cStmt.setString(2, lineId);
					cStmt.setString(3, detailId[i]);
					cStmt.setInt(4, organizationId);
					cStmt.setString(5, "-1");
					cStmt.setString(6, barcode);
					cStmt.setInt(7, qty);
					cStmt.setString(8, objectNo[i]);
					cStmt.execute();
				}
			}
			prodMessage("UPDATE_DATA_SUCCESS");
			message.addParameterValue("分配信息");
		} finally {
			DBManager.closeDBStatement(cStmt);
		}
	}

	public RowSet produceWebData(String barcode, String transId, String lineId1, String org, String objectNo) throws QueryException {
		BjfxsqModel model = new BjfxsqModel(userAccount, null);
		SQLModel sqlModel = model.getQty(barcode, transId, lineId1, org, objectNo);
		SimpleQuery sq = new SimpleQuery(sqlModel, conn);
		sq.executeQuery();
		return sq.getSearchResult();
	}

	/**
	 * 功能:更新备件差异表
	 * @param lineSet
	 * @throws DataHandleException
	 * @throws QueryException
	 */
	  public void saveDifQty(DTOSet lineSet) throws DataHandleException, QueryException {
		if (lineSet != null && !lineSet.isEmpty()) {
			AmsItemTransHDTO headerDTO = (AmsItemTransHDTO)dtoParameter;
			for (int i = 0; i < lineSet.getSize(); i++) {
				BjfxsqModel model = new BjfxsqModel(userAccount, null);
				AmsItemTransLDTO lineData = (AmsItemTransLDTO) lineSet.getDTO(i);
				lineData.setTransId(headerDTO.getTransId());
				SimpleQuery sq = new SimpleQuery(model.selectQtyModel(lineData.getBarcode(),headerDTO.getFromOrganizationId()), conn);
				sq.executeQuery();
				if (sq.getSearchResult().getSize() > 0) {
					DBOperator.updateRecord(model.updateQtyModel(lineData.getQuantity(),lineData.getActualQty(),headerDTO.getFromOrganizationId(), lineData.getBarcode()), conn);
				} else {
					DBOperator.updateRecord(model.insertQtyModel(lineData.getBarcode(), lineData.getQuantity(),lineData.getActualQty(),headerDTO.getFromOrganizationId()), conn);
				}
			}
		}
	}

    public String hasObjectNo(String transId, String barcode) throws QueryException, ContainerException {
        String objectNo = "";
        BjfxsqModel model = new BjfxsqModel(userAccount, null);
        SQLModel sqlModel = model.hasObjectNo(transId, barcode);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        Row row = sq.getFirstRow();
        if (sq.hasResult()) {
            objectNo = row.getStrValue("OBJECT_NO");
        }
        return objectNo;
    }

    public void cancelFP(AmsItemTransHDTO dto) throws SQLException, DataHandleException {
        BjfxsqModel model = new BjfxsqModel(userAccount, null);
        DBOperator.updateRecord(model.cancelFP(dto), conn);
    }

    public void cancelDB(DTOSet lineSet, AmsItemTransHDTO hdto) throws SQLException {
        CallableStatement cStmt = null;
        String sqlStr = "{call AMS_ITEM_TRANS_SX.CANCEL_FXSQ_QTY(?,?,?,?)}";
        try {
            cStmt = conn.prepareCall(sqlStr);
            for (int i = 0; i < lineSet.getSize(); i++) {
                AmsItemTransLDTO dto = (AmsItemTransLDTO) lineSet.getDTO(i);
                cStmt = conn.prepareCall(sqlStr);
                cStmt.setString(1, dto.getBarcode());
				cStmt.setInt(2, dto.getQuantity());
				cStmt.setString(3, hdto.getFromObjectNo());
                cStmt.setInt(4, userAccount.getOrganizationId());
                cStmt.execute();
            }
        } finally {
            DBManager.closeDBStatement(cStmt);
        }
    }

    public DTOSet getTransDQuenty(AmsItemTransHDTO dto) throws DataHandleException, QueryException, SQLModelException {
		BjfxsqModel model = new BjfxsqModel(userAccount, null);
        SimpleQuery sq = new SimpleQuery(model.getTransDQuenty(dto.getTransId()), conn);
        sq.setDTOClassName(AmsItemTransLDTO.class.getName());
        sq.executeQuery();
        return sq.getDTOSet();
	}

    public void cancelTrandDQuery(DTOSet lineSet) throws SQLException, DataHandleException {
		for (int i = 0; i < lineSet.getSize(); i++) {
            AmsItemTransLDTO dto = (AmsItemTransLDTO) lineSet.getDTO(i);
            BjfxsqModel model = new BjfxsqModel(userAccount, null);
            DBOperator.updateRecord(model.cancelTrandDQuery(dto), conn);
        }
	}
}
