package com.sino.ams.spare.repair.dao;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.bean.OrderNumGenerator;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.OracleAppErrorCode;
import com.sino.ams.spare.assistant.SpareMessageKeys;
import com.sino.ams.spare.dto.AmsItemTransHDTO;
import com.sino.ams.spare.dto.AmsItemTransLDTO;
import com.sino.ams.spare.repair.dto.AmsCustomerInfoDTO;
import com.sino.ams.spare.repair.dto.AmsVendorInfoDTO;
import com.sino.ams.spare.repair.model.BjSendRepairModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;
import com.sino.base.util.CalendarUtil;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2007-11-12
 * Time: 9:27:13
 */
public class BjSendRepairDAO extends AMSBaseDAO {
	private String barcode = "";

	public BjSendRepairDAO(SfUserDTO userAccount, AmsItemTransHDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		AmsItemTransHDTO dto = (AmsItemTransHDTO) dtoParameter;
		super.sqlProducer = new BjSendRepairModel((SfUserDTO) userAccount, dto);
	}

	public void createData() throws DataHandleException {
		super.createData();
		getMessage().addParameterValue("仪器头表(AMS)");
	}

	public void updateData() throws DataHandleException {
		super.updateData();
		getMessage().addParameterValue("仪器头表(AMS)");
	}

	public void deleteData() throws DataHandleException {
		super.deleteData();
		getMessage().addParameterValue("仪器头表(AMS)");
	}

	/**
	 * 保存单据信息，如果新增则创建单据及行信息，否则，更新相应数据
	 * @param lineSet
	 * @param isNew
	 * @return
	 */
	public boolean saveBillData(DTOSet lineSet, boolean isNew) {
		boolean operateResult = false;
		boolean autoCommit = true;
		AmsItemTransHDTO dto = (AmsItemTransHDTO) dtoParameter;
		try {
			autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			String transId = dto.getTransId();
			dto.setCreationDate(CalendarUtil.getCurrDate());
			AmsItemTransLDTO lineDto = null;
			if (isNew) {
				createData();
				for (int i = 0; i < lineSet.getSize(); i++) {
					lineDto = (AmsItemTransLDTO) lineSet.getDTO(i);
					DBOperator.updateRecord(((BjSendRepairModel) sqlProducer).insertLData(transId, lineDto), conn);
				}

			} else {         //要跟据要求流程审批后不能修改数量
				updateData();
				deleteLines(transId);
				for (int i = 0; i < lineSet.getSize(); i++) {
					lineDto = (AmsItemTransLDTO) lineSet.getDTO(i);
					DBOperator.updateRecord(((BjSendRepairModel) sqlProducer).insertLData(transId, lineDto), conn);
				}
			}



            operateResult = true;
		} catch (CalendarException e) {
			Logger.logError(e);
		} catch (DataHandleException e) {
			Logger.logError(e);
		} catch (SQLException ex) {
			Logger.logError(ex);
		} finally{
			try {
				if (operateResult) {
					conn.commit();
					prodMessage(SpareMessageKeys.REPAIR_SUBMIT_SUCCESS);
					message.addParameterValue(dto.getTransNo());
				} else {
					conn.rollback();
					prodMessage(SpareMessageKeys.REPAIR_SUBMIT_FAILURE);
					message.setNeedClose(true);
				}
				message.setIsError(!operateResult);
				conn.setAutoCommit(autoCommit);
			} catch (SQLException ex) {
				Logger.logError(ex);
			}
		}
		return operateResult;
	}

    /**
     * 更新保留量
     * @param objectNo
     * @param lineSet
     * @throws DataHandleException
     */
      public void updateResQty(String objectNo,DTOSet lineSet,boolean isNext) throws DataHandleException {
        if (lineSet != null && !lineSet.isEmpty()) {
            List sqModels = new ArrayList();
            AmsItemTransLDTO lineData = null;
            for (int i = 0; i < lineSet.getSize(); i++) {
                lineData = (AmsItemTransLDTO) lineSet.getDTO(i);
                SQLModel sqlModel = new SQLModel();
                List sqlArgs = new ArrayList();
                String sqlStr ="";
                if (isNext) {
                    sqlStr = " UPDATE AMS_SPARE_INFO\n" +
                            "       SET RESERVE_QUANTITY = RESERVE_QUANTITY + ?,\n" +
                            "           LAST_UPDATE_DATE = SYSDATE,\n" +
                            "           LAST_UPDATE_BY   = ?\n" +
                            "     WHERE BARCODE = ? AND OBJECT_NO=?";
                    sqlArgs.add(lineData.getQuantity());
                    sqlArgs.add(userAccount.getUserId());
                    sqlArgs.add(lineData.getBarcode());
                    sqlArgs.add(objectNo);
                } else {
                    sqlStr = " UPDATE AMS_SPARE_INFO\n" +
                            "       SET QUANTITY = QUANTITY - ?,\n" +
                            "           RESERVE_QUANTITY = RESERVE_QUANTITY - ?,\n" +
                            "           LAST_UPDATE_DATE = SYSDATE,\n" +
                            "           LAST_UPDATE_BY   = ?\n" +
                            "     WHERE BARCODE = ? AND OBJECT_NO=?";
                    sqlArgs.add(lineData.getQuantity());
                    sqlArgs.add(lineData.getQuantity());
                    sqlArgs.add(userAccount.getUserId());
                    sqlArgs.add(lineData.getBarcode());
                    sqlArgs.add(objectNo);
                }


                sqlModel.setSqlStr(sqlStr);
                sqlModel.setArgs(sqlArgs);
                sqModels.add(sqlModel);
            }
            DBOperator.updateBatchRecords(sqModels, conn);
        }
    }
	public boolean saveData(DTOSet lineSet, boolean isNew) throws SQLException {
		boolean operateResult = false;
		boolean autoCommit = false;
		AmsItemTransHDTO dto = (AmsItemTransHDTO) dtoParameter;
		try {
			autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			String transId = dto.getTransId();
			dto.setCreationDate(CalendarUtil.getCurrDate());
			AmsItemTransLDTO lineDto = null;
			if (isNew) {
//                OrderNumGenerator ong = new OrderNumGenerator(conn, userAccount.getCompanyCode(), DictConstant.BJSX);
//                dto.setTransNo(ong.getOrderNum());
//                SeqProducer seq = new SeqProducer(conn);
//                transId = seq.getStrNextSeq("AMS_ITEM_TRANS_H_S");
				SimpleQuery sq = new SimpleQuery(((BjSendRepairModel) sqlProducer).getToObjectNoModel(), conn);
				sq.executeQuery();
				dto.setToObjectNo(sq.getFirstRow().getValue("WORKORDER_OBJECT_NO").toString());
				dto.setTransId(transId);
				dto.setTransStatus(DictConstant.SAVE_TEMP);
				dto.setTransType(DictConstant.BJSX);
				createData();
				for (int i = 0; i < lineSet.getSize(); i++) {
					lineDto = (AmsItemTransLDTO) lineSet.getDTO(i);
					DBOperator.updateRecord(((BjSendRepairModel) sqlProducer).insertLData(transId, lineDto), conn);
				}
			} else {
				dto.setTransStatus(DictConstant.SAVE_TEMP);
				updateData();
				deleteLines(transId);

				for (int i = 0; i < lineSet.getSize(); i++) {
					lineDto = (AmsItemTransLDTO) lineSet.getDTO(i);
					DBOperator.updateRecord(((BjSendRepairModel) sqlProducer).insertLData(transId, lineDto), conn);
				}
			}
//            saveLines(lineSet);
			conn.commit();
			prodMessage("SPARE_SAVE_SUCCESS");
			operateResult = true;
		} catch (SQLException e) {
			conn.rollback();
			Logger.logError(e);
			prodMessage("SPARE_SAVE_FAILURE");
		} catch (DataHandleException e) {
			conn.rollback();
			Logger.logError(e);
			prodMessage("SPARE_SAVE_FAILURE");
		} catch (CalendarException e) {
			conn.rollback();
			Logger.logError(e);
			prodMessage("SPARE_SAVE_FAILURE");
		} catch (QueryException e) {
			conn.rollback();
			Logger.logError(e);
			prodMessage("SPARE_SAVE_FAILURE");
		} catch (ContainerException e) {
			conn.rollback();
			Logger.logError(e);
			prodMessage("SPARE_SAVE_FAILURE");
		} finally {
			conn.setAutoCommit(autoCommit);
		}
		return operateResult;
	}


	public boolean submitData(DTOSet lineSet) throws SQLException {
		boolean operateResult = false;
		boolean autoCommit = false;
		AmsItemTransHDTO dto = (AmsItemTransHDTO) dtoParameter;
		try {
			autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			String transId = dto.getTransId();
			dto.setCreationDate(CalendarUtil.getCurrDate());

			AmsItemTransLDTO lineDto = null;
			if (transId.equals("")) {
				OrderNumGenerator ong = new OrderNumGenerator(conn, userAccount.getCompanyCode(), DictConstant.BJSX);
				dto.setTransNo(ong.getOrderNum());
				SeqProducer seq = new SeqProducer(conn);
//				transId = seq.getStrNextSeq("AMS_ITEM_TRANS_H_S");
				transId = seq.getGUID();
				dto.setTransId(transId);
				dto.setTransStatus(DictConstant.COMPLETED);
				dto.setTransType(DictConstant.BJSX);
				createData();
				for (int i = 0; i < lineSet.getSize(); i++) {
					lineDto = (AmsItemTransLDTO) lineSet.getDTO(i);
					DBOperator.updateRecord(((BjSendRepairModel) sqlProducer).insertLData(transId, lineDto), conn);
				}
			} else {
				dto.setTransStatus(DictConstant.COMPLETED);
				updateData();
				deleteLines(transId);

				for (int i = 0; i < lineSet.getSize(); i++) {
					lineDto = (AmsItemTransLDTO) lineSet.getDTO(i);
					DBOperator.updateRecord(((BjSendRepairModel) sqlProducer).insertLData(transId, lineDto), conn);
//                    DBOperator.updateRecord(model.updateInfoModel(addresId, lineDto.getBarcode()), conn);
				}
			}
//            saveLines(lineSet);
//            updateAddressId(barcode);
			//更新地点为送修库
			updateSpareInfo(lineSet);
			conn.commit();
			prodMessage("SPARE_SAVE_SUCCESS");
			operateResult = true;
		} catch (SQLException e) {
			conn.rollback();
			Logger.logError(e);
			if (e.getErrorCode() == OracleAppErrorCode.spareNotEnough1) {
				prodMessage("SPARE_NOT_ENOUGH1");
				message.addParameterValue(barcode);
			} else {
				prodMessage("SPARE_SAVE_FAILURE");
			}
		} catch (DataHandleException e) {
			conn.rollback();
			Logger.logError(e);
			prodMessage("SPARE_SAVE_FAILURE");
		} catch (CalendarException e) {
			conn.rollback();
			Logger.logError(e);
			prodMessage("SPARE_SAVE_FAILURE");
		} finally {
			conn.setAutoCommit(autoCommit);
		}
		return operateResult;
	}

	private void updateAddressId(String[] barcode) throws SQLException {
		if (barcode != null) {
			CallableStatement cStmt = null;
			String sqlStr = "{call AMS_ITEM_TRANS.ITEM_TO_REPAIR(?,?,?)}";
			try {
				cStmt = conn.prepareCall(sqlStr);
				for (int i = 0; i < barcode.length; i++) {
					cStmt.setString(1, barcode[i]);
					cStmt.setInt(2, this.userAccount.getOrganizationId());
					cStmt.setInt(3, this.userAccount.getUserId());
					cStmt.execute();
				}

			} finally {
				DBManager.closeDBStatement(cStmt);
			}
		}
	}

	public void deleteLines(String transId) throws DataHandleException {
		BjSendRepairModel model = new BjSendRepairModel(userAccount, null);
		DBOperator.updateRecord(model.getDeleteByTransIdModel(transId), conn);
	}

	public void saveLines(DTOSet lineSet) throws DataHandleException {
		if (lineSet != null && !lineSet.isEmpty()) {
			AmsItemTransHDTO dto = (AmsItemTransHDTO) dtoParameter;
			BjSendRepairDAO lineDAO = new BjSendRepairDAO(userAccount, null, conn);
			for (int i = 0; i < lineSet.getSize(); i++) {
				AmsItemTransHDTO lineData = (AmsItemTransHDTO) lineSet.getDTO(i);
				lineData.setTransId(dto.getTransId());
				lineDAO.setDTOParameter(lineData);
				lineDAO.createData();
			}
		}
	}

	public RowSet produceWebData() throws QueryException {
		BjSendRepairModel model = new BjSendRepairModel(userAccount, null);
		SQLModel sqlModel = model.getSQLModel();
		SimpleQuery sq = new SimpleQuery(sqlModel, conn);
		sq.executeQuery();
		return sq.getSearchResult();
	}

	public RowSet getLines(String transId) throws QueryException {
		BjSendRepairModel model = new BjSendRepairModel(userAccount, null);
		SimpleQuery sq = new SimpleQuery(model.getByTransIdModel(transId), conn);
		sq.executeQuery();
		return sq.getSearchResult();
	}

	/**
	 * 取单据详细信息（detail表）
	 * @param transId
	 * @return
	 * @throws QueryException
	 */
	public RowSet getDetails(String transId) throws QueryException {
		BjSendRepairModel model = new BjSendRepairModel(userAccount, null);
		SimpleQuery sq = new SimpleQuery(model.getDtlByTransIdModel(transId), conn);
		sq.executeQuery();
		return sq.getSearchResult();
	}

	public AmsVendorInfoDTO getVendorInfo(String Company) throws QueryException {
		BjSendRepairModel model = new BjSendRepairModel(userAccount,null);
		SimpleQuery sq = new SimpleQuery(model.getVendorInfo(Company),conn);
		sq.setDTOClassName(AmsVendorInfoDTO.class.getName());
		sq.executeQuery();
		return (AmsVendorInfoDTO)sq.getFirstDTO();
	}

	public AmsItemTransHDTO getPrintH(String transId) throws QueryException {
		BjSendRepairModel model = new BjSendRepairModel(userAccount, null);
		SimpleQuery sq = new SimpleQuery(model.getPrintHModel(transId), conn);
		sq.setDTOClassName(AmsItemTransHDTO.class.getName());
		sq.executeQuery();
//        return sq.getSearchResult();
		return (AmsItemTransHDTO)sq.getFirstDTO();

	}

	/**
	 * 统计送修设备的数量,将送修的设备地点更改为送修
	 * @param lineSet DTOSet
	 */
	private void updateSpareInfo(DTOSet lineSet) throws SQLException {
		if (lineSet != null && lineSet.getSize() > 0) {
			CallableStatement cStmt = null;
			AmsItemTransLDTO lineData = null;
			String sqlStr = "{call AMS_INV_TRANS.SPARE_SEND_REPAIR(?,?,?)}";
			try {
				cStmt = conn.prepareCall(sqlStr);
				for (int i = 0; i < lineSet.getSize(); i++) {
					lineData = (AmsItemTransLDTO) lineSet.getDTO(i);
					barcode = lineData.getBarcode();
					cStmt.setString(1, lineData.getBarcode());
					cStmt.setInt(2, this.userAccount.getOrganizationId());
					cStmt.setInt(3, this.userAccount.getUserId());
					cStmt.execute();
				}
			} finally {
				DBManager.closeDBStatement(cStmt);
			}
		}
	}

	public AmsCustomerInfoDTO getByOU() throws QueryException {
		BjSendRepairModel model = new BjSendRepairModel(userAccount, null);
		SimpleQuery sq = new SimpleQuery(model.getByOu(), conn);
		sq.setDTOClassName(AmsCustomerInfoDTO.class.getName());
		sq.executeQuery();
		AmsCustomerInfoDTO dto1 = (AmsCustomerInfoDTO) sq.getFirstDTO();
		return dto1;
	}
	/*   public RowSet getByOU(String transId) throws QueryException {
		BjSendRepairModel model = new BjSendRepairModel(userAccount, null);
		SimpleQuery sq = new SimpleQuery(model.getByOu(), conn);
		sq.executeQuery();
		return sq.getSearchResult();
	}*/

	public void updateStorage(String transId, String storeType) throws SQLException {
		if (StrUtil.isNotEmpty(transId)) {
			CallableStatement cStmt = null;
			String sqlStr = "{call AMS_ITEM_TRANS_SX.SPARE_REPAIR(?,?)}";
			try {
				cStmt = conn.prepareCall(sqlStr);
				cStmt.setString(1, transId);
				cStmt.setString(2, storeType);
				cStmt.execute();
			} finally {
				DBManager.closeDBStatement(cStmt);
			}
		}
	}

    /**
     * 更新保留量
     * @param transId
     * @param storeType
     * @throws SQLException
     */
    public void updateRevQty(String transId, String storeType) throws SQLException {
		if (StrUtil.isNotEmpty(transId)) {
			CallableStatement cStmt = null;
			String sqlStr = "{call AMS_ITEM_TRANS_SX.SPARE_REPAIR(?,?)}";
			try {
				cStmt = conn.prepareCall(sqlStr);
				cStmt.setString(1, transId);
				cStmt.setString(2, storeType);
				cStmt.execute();

			} finally {
				DBManager.closeDBStatement(cStmt);
			}
		}
	}



	public void completeTrans(String transId,String transStaus) throws DataHandleException {
		 BjSendRepairModel model = (BjSendRepairModel)sqlProducer;
		SQLModel sqlModel = model.updateStatusModels(transId,transStaus);
		DBOperator.updateRecord(sqlModel,conn);
	}



   public String  getObjectN0() throws QueryException, ContainerException {
	   String objectN0 = "";
	   SQLModel sqlModel = new SQLModel();
	   List sqlArgs = new ArrayList();
	   String sqlStr =
					  "SELECT EO.WORKORDER_OBJECT_NO, EO.WORKORDER_OBJECT_LOCATION||EO.WORKORDER_OBJECT_NAME\n" +
					  "  FROM ETS_OBJECT EO\n" +
					  " WHERE (EO.OBJECT_CATEGORY = '72' )\n" +
					  "   AND EO.ORGANIZATION_ID = ?" +
                       "  AND EO.WORKORDER_OBJECT_NAME NOT LIKE '%工程%'";
	   sqlArgs.add(userAccount.getOrganizationId());
	   sqlModel.setSqlStr(sqlStr);
	   sqlModel.setArgs(sqlArgs);
	   SimpleQuery   simpleQuery = new SimpleQuery(sqlModel,conn);
	   simpleQuery.executeQuery();
	   if(simpleQuery.hasResult()){
		  objectN0 = simpleQuery.getFirstRow().getStrValue("WORKORDER_OBJECT_NO");
	   }else{
		  objectN0 = "";
	   }
	   return objectN0;
  }
    public String  getPRJObjectN0() throws QueryException, ContainerException {
	   String objectN0 = "";
	   SQLModel sqlModel = new SQLModel();
	   List sqlArgs = new ArrayList();
	   String sqlStr =
					  "SELECT EO.WORKORDER_OBJECT_NO, EO.WORKORDER_OBJECT_LOCATION||EO.WORKORDER_OBJECT_NAME\n" +
					  "  FROM ETS_OBJECT EO\n" +
					  " WHERE (EO.OBJECT_CATEGORY = '72' )\n" +
					  "   AND EO.ORGANIZATION_ID = ?" +
                      "   AND EO.WORKORDER_OBJECT_NAME LIKE '%工程%'";
	   sqlArgs.add(userAccount.getOrganizationId());
	   sqlModel.setSqlStr(sqlStr);
	   sqlModel.setArgs(sqlArgs);
	   SimpleQuery   simpleQuery = new SimpleQuery(sqlModel,conn);
	   simpleQuery.executeQuery();
	   if(simpleQuery.hasResult()){
		  objectN0 = simpleQuery.getFirstRow().getStrValue("WORKORDER_OBJECT_NO");
	   }else{
		  objectN0 = "";
	   }
	   return objectN0;
  }

  public void updateResQty2(String objectNo,DTOSet lineSet,boolean isNext) throws DataHandleException {
        if (lineSet != null && !lineSet.isEmpty()) {
            List sqModels = new ArrayList();
            AmsItemTransLDTO lineData = null;
            for (int i = 0; i < lineSet.getSize(); i++) {
                lineData = (AmsItemTransLDTO) lineSet.getDTO(i);
                SQLModel sqlModel = new SQLModel();
                List sqlArgs = new ArrayList();
                String sqlStr ="";
                    sqlStr = " UPDATE AMS_SPARE_INFO\n" +
                            "       SET RESERVE_QUANTITY = RESERVE_QUANTITY - ?,\n" +
                            "           LAST_UPDATE_DATE = SYSDATE,\n" +
                            "           LAST_UPDATE_BY   = ?\n" +
                            "     WHERE BARCODE = ? AND OBJECT_NO=?";
                sqlArgs.add(lineData.getQuantity());
                sqlArgs.add(userAccount.getUserId());
                sqlArgs.add(lineData.getBarcode());
                sqlArgs.add(objectNo);
                sqlModel.setSqlStr(sqlStr);
                sqlModel.setArgs(sqlArgs);
                sqModels.add(sqlModel);
            }
            DBOperator.updateBatchRecords(sqModels, conn);
        }
    }

    public void updateSXQty(String storeType,DTOSet lineSet,boolean isNext) throws DataHandleException {
        if (lineSet != null && !lineSet.isEmpty()) {
            List sqModels = new ArrayList();
            AmsItemTransLDTO lineData = null;
            for (int i = 0; i < lineSet.getSize(); i++) {
                lineData = (AmsItemTransLDTO) lineSet.getDTO(i);
                SQLModel sqlModel = new SQLModel();
                List sqlArgs = new ArrayList();
                String sqlStr = "";
                if (storeType.equals("1")) {
                    sqlStr = "UPDATE AMS_SPARE_INFO ASI\n" +
                            "SET    ASI.QUANTITY = ?\n" +
                            "WHERE  EXISTS (SELECT 1\n" +
                            "\t\t\t\tFROM   ETS_OBJECT EO\n" +
                            "\t\t\t\tWHERE  EO.WORKORDER_OBJECT_NO = ASI.OBJECT_NO\n" +
                            "\t\t\t\tAND    EO.OBJECT_CATEGORY = 73\n" +
                            "\t\t\t\tAND    EO.WORKORDER_OBJECT_NAME NOT LIKE '%工程%'\n" +
                            "\t\t\t\tAND    EO.ORGANIZATION_ID = 82)\n" +
                            "AND    ASI.ORGANIZATION_ID = 82\n" +
                            "AND    ASI.BARCODE = ?";
                } else {
                    sqlStr = "UPDATE AMS_SPARE_INFO ASI\n" +
                        "SET    ASI.QUANTITY = ?\n" +
                        "WHERE  EXISTS (SELECT 1\n" +
                        "\t\t\t\tFROM   ETS_OBJECT EO\n" +
                        "\t\t\t\tWHERE  EO.WORKORDER_OBJECT_NO = ASI.OBJECT_NO\n" +
                        "\t\t\t\tAND    EO.OBJECT_CATEGORY = 73\n" +
                        "\t\t\t\tAND    EO.WORKORDER_OBJECT_NAME LIKE '%工程%'\n" +
                        "\t\t\t\tAND    EO.ORGANIZATION_ID = 82)\n" +
                        "AND    ASI.ORGANIZATION_ID = 82\n" +
                        "AND    ASI.BARCODE = ?";
                }
                sqlArgs.add(lineData.getQuantity());
                sqlArgs.add(lineData.getBarcode());
                sqlModel.setSqlStr(sqlStr);
                sqlModel.setArgs(sqlArgs);
                sqModels.add(sqlModel);
            }
            DBOperator.updateBatchRecords(sqModels, conn);
        }
    }

    public void updateSX(String fromObjectNo,  DTOSet lineSet) throws SQLModelException, QueryException, ContainerException, DataHandleException {
        if (lineSet != null && !lineSet.isEmpty()) {
            List sqModels = new ArrayList();
            AmsItemTransLDTO lineData = null;
            String toObjectNo = getTOObjectNo(fromObjectNo);
            for (int i = 0; i < lineSet.getSize(); i++) {
                lineData = (AmsItemTransLDTO) lineSet.getDTO(i);
                SQLModel sqlModel = new SQLModel();
                List sqlArgs = new ArrayList();
                String sqlStr = "";
                boolean haxResult = hasSxResult(toObjectNo, lineData.getBarcode());
                if (haxResult) {
                    sqlStr = "UPDATE AMS_SPARE_INFO ASI\n" +
                            "   SET ASI.QUANTITY = ASI.QUANTITY + ?\n" +
                            " WHERE ASI.BARCODE = ?\n" +
                            "       AND ASI.OBJECT_NO = ?";
                    sqlArgs.add(lineData.getQuantity());
                    sqlArgs.add(lineData.getBarcode());
                    sqlArgs.add(toObjectNo);
                } else {
                    sqlStr = "INSERT INTO AMS_SPARE_INFO\n" +
                            "  (SPARE_ID,\n" +
                            "   BARCODE,\n" +
                            "   QUANTITY,\n" +
                            "   ORGANIZATION_ID,\n" +
                            "   OBJECT_NO,\n" +
                            "   ITEM_STATUS,\n" +
                            "   REMARK,\n" +
                            "   CREATION_DATE,\n" +
                            "   CREATED_BY)\n" +
                            "VALUES\n" +
                            "  (AMS_SPARE_INFO_S.NEXTVAL,\n" +
                            "   ?,\n" +
                            "   ?,\n" +
                            "   '82',\n" +
                            "   ?,\n" +
                            "   '送修',\n" +
                            "   '送修',\n" +
                            "   SYSDATE,\n" +
                            "   ?)";
                    sqlArgs.add(lineData.getBarcode());
                    sqlArgs.add(lineData.getQuantity());
                    sqlArgs.add(toObjectNo);
                    sqlArgs.add(userAccount.getUserId());
                }
                sqlModel.setSqlStr(sqlStr);
                sqlModel.setArgs(sqlArgs);
                sqModels.add(sqlModel);
            }
            DBOperator.updateBatchRecords(sqModels, conn);
        }
    }

    public String getTOObjectNo(String fromObjectNo) throws SQLModelException, QueryException, ContainerException {
        String objectNo = "";
        BjSendRepairModel eoModel = (BjSendRepairModel) sqlProducer;
        SQLModel sqlModel = eoModel.getTOObjectNo(fromObjectNo);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        RowSet rs = simpleQuery.getSearchResult();
        if (rs != null && rs.getSize() > 0) {
            Row row = rs.getRow(0);
            objectNo = StrUtil.nullToString(row.getValue("SX_OBJECT_NO"));
        }
        return objectNo;
    }

    public boolean hasSxResult(String toObjectNo, String barcode) throws SQLModelException, QueryException, ContainerException {
        boolean hasBarcode=false;
        BjSendRepairModel eoModel = (BjSendRepairModel) sqlProducer;
        SQLModel sqlModel = eoModel.hasSxResult(toObjectNo, barcode);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        if(simpleQuery.hasResult()){
           hasBarcode = true;
        }
        return hasBarcode;
    }
}
