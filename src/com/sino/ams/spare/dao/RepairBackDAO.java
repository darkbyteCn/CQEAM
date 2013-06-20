package com.sino.ams.spare.dao;

import com.sino.ams.bean.OrderNumGenerator;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.spare.dto.AmsItemTransDDTO;
import com.sino.ams.spare.dto.AmsItemTransHDTO;
import com.sino.ams.spare.dto.AmsItemTransLDTO;
import com.sino.ams.spare.model.AmsItemTransLModel;
import com.sino.ams.spare.model.BjfxsqModel;
import com.sino.ams.spare.model.RepairBackModel;
import com.sino.ams.spare.model.SpareModel;
import com.sino.ams.spare.constant.SparePROCConstant;
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
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.util.CalendarUtil;
import com.sino.base.util.StrUtil;
import com.sino.flow.bean.FlowAction;
import com.sino.flow.constant.FlowConstant;
import com.sino.flow.dto.FlowDTO;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.sms.bean.MessageSaver;
import com.sino.sms.constant.SMSConstant;
import com.sino.sms.dto.SfMsgDefineDTO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: srf
 * Date: 2008-3-18
 * Time: 21:20:52
 * Function:备件返修申请
 */
public class RepairBackDAO extends BaseDAO {
    private SfUserDTO sfUser = null;
    private AmsItemTransHDTO amsItemTransH = null;

    public RepairBackDAO(SfUserDTO userAccount, AmsItemTransHDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        this.sfUser = userAccount;
        this.amsItemTransH = (AmsItemTransHDTO) super.dtoParameter;
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsItemTransHDTO dtoPara = (AmsItemTransHDTO) dtoParameter;
        super.sqlProducer = new RepairBackModel((SfUserDTO) userAccount, dtoPara);
    }

    public void createData() throws DataHandleException {
        super.createData();
        getMessage().addParameterValue("备件事务头表(AMS)");
    }

    /**
     * 功能：更新备件事务头表(AMS)表“AMS_ITEM_TRANS_H”数据。
     */
    public void updateData() throws DataHandleException {
        super.updateData();
        getMessage().addParameterValue("备件事务头表(AMS)");
    }

    /**
     * 功能：删除备件事务头表(AMS)表“AMS_ITEM_TRANS_H”数据。
     */
    public void deleteData() throws DataHandleException {
        super.deleteData();
        getMessage().addParameterValue("备件事务头表(AMS)");
    }

    public boolean saveOrder(DTOSet ds, FlowDTO flowDTO,String flowAction,boolean isFirstNode) throws SQLException {
        boolean success = true;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            String transId = amsItemTransH.getTransId();
            boolean isNew=transId.equals("");
            if (transId.equals("")) {
                OrderNumGenerator ong = new OrderNumGenerator(conn, sfUser.getCompanyCode(), amsItemTransH.getTransType());
                amsItemTransH.setTransNo(ong.getOrderNum());
                SeqProducer seq = new SeqProducer(conn);
//                transId = seq.getStrNextSeq("AMS_ITEM_TRANS_H_S");
                transId = seq.getGUID();
                amsItemTransH.setTransId(transId);
                createData();

                saveOrderDetails(ds, transId);

                flowDTO.setApplyId(amsItemTransH.getTransId());
                flowDTO.setApplyNo(amsItemTransH.getTransNo());
                flowDTO.setActivity(FlowConstant.FLOW_CODE_NEXT);
                excuteFlow(flowDTO,flowAction,isNew);                

            } else {
                if (isFirstNode) {
                    updateData();
                    deleteLines(transId);
                    deleteDetails(transId);
                    saveOrderDetails(ds, transId);
                }

                flowDTO.setApplyId(amsItemTransH.getTransId());
                flowDTO.setApplyNo(amsItemTransH.getTransNo());
                flowDTO.setActivity(FlowConstant.FLOW_CODE_NEXT);
                excuteFlow(flowDTO, flowAction, isNew);
            }


            conn.commit();
            prodMessage("SAVE_SUCCESS");
            success = true;
        } catch (SQLException e) {
            conn.rollback();
            success = false;
            Logger.logError(e);
            prodMessage("SAVE_FAILURE");
        } catch (DataHandleException e) {
            conn.rollback();
            success = false;
            Logger.logError(e);
            prodMessage("SAVE_FAILURE");
        } catch (QueryException e) {
            conn.rollback();
            success = false;
            Logger.logError(e);
            prodMessage("SAVE_FAILURE");
        } finally {
            conn.setAutoCommit(autoCommit);
        }

        return success;
    }

    /**
     * 保存单据的详细信息，并且生成
     * @param ds
     * @param transId
     * @throws DataHandleException
     */
    private void saveOrderDetails(DTOSet ds, String transId) throws DataHandleException {
        AmsItemTransDDTO transDDTO = null;
        for (int i = 0; i < ds.getSize(); i++) {
            transDDTO = (AmsItemTransDDTO) ds.getDTO(i);
            DBOperator.updateRecord(SpareModel.insertDData(transId, transDDTO), conn);
        }

        DBOperator.updateRecord(SpareModel.insertLineModel(transId), conn);
        DBOperator.updateRecord(SpareModel.updateDetailModel(transId), conn);
    }

    public void deleteDetails(String transId) throws DataHandleException {
        SQLModel sqlModel = SpareModel.deleteDetailsModel(transId);
        DBOperator.updateRecord(sqlModel, conn);
    }

    /**
     * 提交单据
     * @param lineSet 行数据
     * @param flowDTO FlowDTO
     * @return success
     * @throws java.sql.SQLException
     */
    public boolean submitOrder(DTOSet lineSet, FlowDTO flowDTO) throws SQLException {
        boolean success = true;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            String transId = amsItemTransH.getTransId();
//            amsItemTransH.setTransStatus(DictConstant.COMPLETED);
            amsItemTransH.setTransDate(CalendarUtil.getCurrDate());
//              flowDTO.get
            if (flowDTO.getActId().equals("")) {
                flowDTO.setApproveContent(FlowConstant.APPROVE_CONTENT_NEW);
            } else {
                flowDTO.setApproveContent(FlowConstant.APPROVE_CONTENT_RESUBMIT);
            }

            FlowAction fa = null;

            if (transId.equals("")) {
                OrderNumGenerator ong = new OrderNumGenerator(conn, sfUser.getCompanyCode(), amsItemTransH.getTransType());
                amsItemTransH.setTransNo(ong.getOrderNum());
                SeqProducer seq = new SeqProducer(conn);
//                transId = seq.getStrNextSeq("AMS_ITEM_TRANS_H_S");
                transId = seq.getGUID();
                amsItemTransH.setTransId(transId);
                createData();
            } else {
                updateData();
                //有数据，删行信息
                deleteLines(transId);
            }
            saveDifQty(lineSet);
            saveLines(lineSet);
            flowDTO.setApplyId(transId);
            flowDTO.setApplyNo(amsItemTransH.getTransNo());
            flowDTO.setActivity(FlowConstant.FLOW_CODE_NEXT);
            fa = new FlowAction(conn, flowDTO);

            String flowCode = fa.getFlowCode();
            if (flowCode.equals(DictConstant.SCANING)) {
                String status = DictConstant.SCANING;
                BjfxsqModel model = new BjfxsqModel(sfUser, amsItemTransH);
                DBOperator.updateRecord(model.updateStatusModel(status), conn);
            } else if (flowCode.equals(DictConstant.IN_PROCESS)) {
                String status = DictConstant.IN_PROCESS;
                BjfxsqModel model = new BjfxsqModel(sfUser, amsItemTransH);
                DBOperator.updateRecord(model.updateStatusModel(status), conn);
            }


            fa.flow();
            conn.commit();
            prodMessage("SAVE_SUCCESS");
            success = true;
        } catch (SQLException e) {
            conn.rollback();
            success = false;
            Logger.logError(e);
            prodMessage("SAVE_FAILURE");
        } catch (DataHandleException e) {
            conn.rollback();
            success = false;
            Logger.logError(e);
            prodMessage("SAVE_FAILURE");
        } catch (CalendarException e) {
            conn.rollback();
            success = false;
            Logger.logError(e);
            prodMessage("SAVE_FAILURE");
        } catch (QueryException e) {
            conn.rollback();
            success = false;
            Logger.logError(e);
            prodMessage("SAVE_FAILURE");
        } catch (ContainerException e) {
            e.printStackTrace();
        } finally {
            conn.setAutoCommit(autoCommit);
        }
        return success;
    }

    public void buildOrders() throws SQLException {
        CallableStatement cStmt = null;
        String sqlStr = "{call AMS_ITEM_TRANS_SX.BUILD_FXSQ_ORDERS(?,?)}";
        try {
            cStmt = conn.prepareCall(sqlStr);
            cStmt.setString(1, amsItemTransH.getTransId());
            cStmt.setInt(2, sfUser.getUserId());
            cStmt.execute();
        } finally {
            DBManager.closeDBStatement(cStmt);
        }
    }

    public void updateOut(DTOSet lineSet) throws SQLException {
        CallableStatement cStmt = null;
        String sqlStr = "{call AMS_ITEM_TRANS_SX.INV_DAIXIU_OUT(?,?,?,?)}";
        try {
            cStmt = conn.prepareCall(sqlStr);
//            if (lineSet != null && lineSet.isEmpty()) {

            for (int i = 0; i < lineSet.getSize(); i++) {
                AmsItemTransLDTO dto = (AmsItemTransLDTO) lineSet.getDTO(i);
                cStmt = conn.prepareCall(sqlStr);
                cStmt.setString(1, dto.getBarcode());
                cStmt.setInt(2, amsItemTransH.getFromOrganizationId());
                cStmt.setInt(3, dto.getQuantity());
                cStmt.setInt(4, sfUser.getUserId());
                cStmt.execute();
            }
//            }
        } finally {
            DBManager.closeDBStatement(cStmt);
        }
    }

    public void updateIn(DTOSet lineSet) throws SQLException {
        CallableStatement cStmt = null;
        String sqlStr = "{call AMS_ITEM_TRANS_SX.INV_SX_IN(?,?,?)}";
        try {
            cStmt = conn.prepareCall(sqlStr);
            /*     if (lineSet != null && lineSet.isEmpty()) {*/

            for (int i = 0; i < lineSet.getSize(); i++) {
                AmsItemTransLDTO dto = (AmsItemTransLDTO) lineSet.getDTO(i);
                cStmt = conn.prepareCall(sqlStr);
                cStmt.setString(1, dto.getBarcode());
                cStmt.setInt(2, dto.getQuantity());
                cStmt.setInt(3, sfUser.getUserId());
                cStmt.execute();
            }
//            }
        } finally {
            DBManager.closeDBStatement(cStmt);
        }
    }

    public String approveOrder(DTOSet lineSet, FlowDTO flowDTO, boolean isOut) throws SQLException, ContainerException {
        String success = "";
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);

            /* if (sectionRight.equals("OUT")) {

            }*/
            // 出库
            /*else if(sectionRight.equals("RECEIVE")){   //接收 作为一个单独的Act
                //,并将条码所在仓库改为在正常库

                updateAddressIdOnNormal();
                updateStatus(DictConstant.COMPLETED); //暂不改变单据状态,接收后才改变状态
            }*/

            //流程处理
            FlowAction fa = new FlowAction(conn, flowDTO);
            if (fa.isFlowToEnd()) {
                buildOrders();
                updateIn(lineSet);
                updateOut(lineSet);
                String status = DictConstant.COMPLETED;
                amsItemTransH.setTransStatus(DictConstant.COMPLETED);
                BjfxsqModel model = new BjfxsqModel(sfUser, amsItemTransH);
                DBOperator.updateRecord(model.updateStatusModel(status), conn);

                saveMessage();//发送手机短信

                success = "end";
            }

//            if (isOut) {
//                updateIn(lineSet);
//                updateOut(lineSet);
//            }
            if (fa.getFlowCode().equals(DictConstant.SCANING)) {
                String status = DictConstant.SCANING;
                BjfxsqModel model = new BjfxsqModel(sfUser, amsItemTransH);
                DBOperator.updateRecord(model.updateStatusModel(status), conn);
            }
            if (fa.getFlowCode().equals(DictConstant.IN_PROCESS)) {
                String status = DictConstant.IN_PROCESS;
                amsItemTransH.setTransStatus(DictConstant.IN_PROCESS);
                BjfxsqModel model = new BjfxsqModel(sfUser, amsItemTransH);
                DBOperator.updateRecord(model.updateStatusModel(status), conn);
            }
            fa.flow();
            conn.commit();
//            success = "ok";
        } catch (SQLException e) {
            prodMessage("SQL_ERROR");
            Logger.logError(e);
            conn.rollback();
        } catch (QueryException e) {
            prodMessage("SQL_ERROR");
            Logger.logError(e);
            conn.rollback();
        } catch (DataHandleException e) {
            prodMessage("SQL_ERROR");
            Logger.logError(e);
            conn.rollback();
        } finally {
            conn.setAutoCommit(autoCommit);
        }
        return success;
    }


    /**
     * 功能：发送手机短信给备件调拨人
     * @throws com.sino.base.exception.DataHandleException
     *
     */
    private void saveMessage() throws DataHandleException {

//        AmsItemAllocateHDTO headerDTO = (AmsItemAllocateHDTO) dtoParameter;
        String userId = "";
        String orderNum = "";
        String userName = "";
        String userTel = "";
        String transId = "";
        Row row = null;
        Row rowh = null;
        try {
            MessageSaver msgSaver = new MessageSaver(conn);
            RowSet rowss = getDBhead(conn, amsItemTransH.getTransId());
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
                                msgDefineDTO.setCreatedBy(sfUser.getUserId());
                                msgDefineDTO.setCellPhone(userTel);
                                msgDefineDTO.setApplyNumber(orderNum);
                                msgDefineDTO.setUserId(userId);
                                msgDefineDTO.setMsgContent("您好" + userName + "：请安排贵公司正常仓库调拨给" + toCompanyName + "公司，调拨单号:" + orderNum);
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
     * @throws com.sino.base.exception.QueryException
     *
     * @throws com.sino.base.exception.ContainerException
     *
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
//         sq.setDTOClassName(AmsItemOnNetDTO.class.getName());
        sq.executeQuery();
//          Row row = sq.getFirstRow();
//          fromOrgId = row.getValue("USERNAME").toString();
        return sq.getSearchResult();
    }


    /**
     * 功能: 获得调拨单的rowset
     * @param conn
     * @param sourceId
     * @return
     * @throws com.sino.base.exception.QueryException
     *
     * @throws com.sino.base.exception.ContainerException
     *
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
     * @throws com.sino.base.exception.QueryException
     *
     * @throws com.sino.base.exception.ContainerException
     *
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


    public String firstN(DTOSet lineSet, FlowDTO flowDTO, String sectionRight) throws SQLException, ContainerException {
        String success = "";
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
//             } else {
//                updateData();
            //有数据，删行信息
//                deleteLines(transId);
            deleteLines(amsItemTransH.getTransId());
//            }
            saveDifQty(lineSet);
            saveLines(lineSet);
            //流程处理
            FlowAction fa = new FlowAction(conn, flowDTO);
            if (fa.isFlowToEnd()) {
                buildOrders();
                updateIn(lineSet);
                updateOut(lineSet);
                String status = DictConstant.COMPLETED;
                amsItemTransH.setTransStatus(DictConstant.COMPLETED);
                BjfxsqModel model = new BjfxsqModel(sfUser, amsItemTransH);
                DBOperator.updateRecord(model.updateStatusModel(status), conn);
                success = "end";
            }
            if (fa.getFlowCode().equals(DictConstant.SCANING)) {
                String status = DictConstant.SCANING;
                BjfxsqModel model = new BjfxsqModel(sfUser, amsItemTransH);
                DBOperator.updateRecord(model.updateStatusModel(status), conn);
            }
            if (fa.getFlowCode().equals(DictConstant.IN_PROCESS)) {
                String status = DictConstant.IN_PROCESS;
                amsItemTransH.setTransStatus(DictConstant.IN_PROCESS);
                BjfxsqModel model = new BjfxsqModel(sfUser, amsItemTransH);
                DBOperator.updateRecord(model.updateStatusModel(status), conn);
            }
            fa.flow();
            conn.commit();
//            success = "ok";
        } catch (SQLException e) {
            prodMessage("SQL_ERROR");
            Logger.logError(e);
            conn.rollback();
        } catch (QueryException e) {
            prodMessage("SQL_ERROR");
            Logger.logError(e);
            conn.rollback();
        } catch (DataHandleException e) {
            prodMessage("SQL_ERROR");
            Logger.logError(e);
            conn.rollback();
        } finally {
            conn.setAutoCommit(autoCommit);
        }
        return success;
    }

    /**
     * 功能：发送短信。
     */
    public void sendMessage() {
        String no = null;
        String message = "";
        CallableStatement cst = null;
//        String sqlStr = "begin ? := ; end;";
        String sqlStr = "{call SAVE_MSG(?,?,?,?,?,?,?,?,?)}";
        try {
            cst = conn.prepareCall(sqlStr);
            cst.setString(1, message);
            cst.setString(2, message);
            cst.execute();
//            no = cst.getString(1);
        } catch (SQLException e) {
            Logger.logError(e);

        } finally {
            DBManager.closeDBStatement(cst);
        }
    }

    public boolean approveScaning(DTOSet lineSet, FlowDTO flowDTO, String sectionRight) throws SQLException, ContainerException {
        boolean success = false;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);

            /* if (sectionRight.equals("OUT")) {

            }*/
            // 出库
            /*else if(sectionRight.equals("RECEIVE")){   //接收 作为一个单独的Act
                //,并将条码所在仓库改为在正常库

                updateAddressIdOnNormal();
                updateStatus(DictConstant.COMPLETED); //暂不改变单据状态,接收后才改变状态
            }*/

            //流程处理
            FlowAction fa = new FlowAction(conn, flowDTO);

            if (fa.getFlowCode().equals(DictConstant.IN_PROCESS)) {
                String status = DictConstant.IN_PROCESS;
                amsItemTransH.setTransStatus(DictConstant.IN_PROCESS);
                BjfxsqModel model = new BjfxsqModel(sfUser, amsItemTransH);
                DBOperator.updateRecord(model.updateStatusModel(status), conn);
            }
            fa.flow();
            conn.commit();
            success = true;
        } catch (SQLException e) {
            prodMessage("SQL_ERROR");
            Logger.logError(e);
            conn.rollback();
        } catch (QueryException e) {
            prodMessage("SQL_ERROR");
            Logger.logError(e);
            conn.rollback();
        } catch (DataHandleException e) {
            prodMessage("SQL_ERROR");
            Logger.logError(e);
            conn.rollback();
        } finally {
            conn.setAutoCommit(autoCommit);
        }
        return success;
    }

    public void saveDifQty(DTOSet lineSet) throws DataHandleException, QueryException {
        if (lineSet != null && !lineSet.isEmpty()) {
            for (int i = 0; i < lineSet.getSize(); i++) {
                BjfxsqModel model = new BjfxsqModel(sfUser, null);
                AmsItemTransLDTO lineData = (AmsItemTransLDTO) lineSet.getDTO(i);
                SimpleQuery sq = new SimpleQuery(model.selectQtyModel(lineData.getBarcode(), amsItemTransH.getFromOrganizationId()), conn);
                sq.executeQuery();
                if (sq.getSearchResult().getSize() > 0) {
                    DBOperator.updateRecord(model.updateQtyModel(lineData.getQuantity(), lineData.getActualQty(), amsItemTransH.getFromOrganizationId(), lineData.getBarcode()), conn);
                } else {
                    DBOperator.updateRecord(model.insertQtyModel(lineData.getBarcode(), lineData.getQuantity(), lineData.getActualQty(), amsItemTransH.getFromOrganizationId()), conn);
                }
            }
        }
    }

    /**
     * 保存行信息至单据表
     * @param lineSet 行数据
     */
    public void saveLines(DTOSet lineSet) throws DataHandleException, QueryException {
        if (lineSet != null && !lineSet.isEmpty()) {
            AmsItemTransLDAO lineDAO = new AmsItemTransLDAO(sfUser, null, conn);
            for (int i = 0; i < lineSet.getSize(); i++) {
                AmsItemTransLDTO lineData = (AmsItemTransLDTO) lineSet.getDTO(i);
                lineData.setTransId(amsItemTransH.getTransId());
                lineDAO.setDTOParameter(lineData);
                lineDAO.createData();
            }
        }
    }

    public void deleteLines(String transId) throws DataHandleException {
        AmsItemTransLModel model = new AmsItemTransLModel(sfUser, null);
        DBOperator.updateRecord(model.getDeleteByTransIdModel(transId), conn);
    }

    private void addToItemInfo(DTOSet lineSet, String financeProp) throws DataHandleException, SQLException {
        if (lineSet != null && !lineSet.isEmpty()) {
            CallableStatement cStmt = null;
            String sqlStr = "{call AMS_ITEM_TRANS.ADD_ITEM_INFO(?,?,?,?,?,?)}";
            try {
                cStmt = conn.prepareCall(sqlStr);
                for (int i = 0; i < lineSet.getSize(); i++) {
                    AmsItemTransLDTO lineData = (AmsItemTransLDTO) lineSet.getDTO(i);
                    cStmt.setString(1, lineData.getBarcode());
                    cStmt.setString(2, lineData.getItemCode());
                    cStmt.setInt(3, sfUser.getOrganizationId());
                    cStmt.setInt(4, sfUser.getUserId());
                    cStmt.setString(5, amsItemTransH.getToObjectNo());
                    cStmt.setString(6, financeProp);
                    cStmt.execute();
                }
            } finally {
                DBManager.closeDBStatement(cStmt);
            }
        }
    }

    /**
     * 写AMS_SPARE_INFO表
     * @param lineSet 行数据
     * @throws com.sino.base.exception.DataHandleException
     *
     */
    private void addSpareInfo(DTOSet lineSet) throws DataHandleException, SQLException {
        if (lineSet != null && !lineSet.isEmpty()) {
            CallableStatement cStmt = null;
            String sqlStr = "{CALL AMS_INV_TRANS2.ADD_SPARE_INFO(?,?,?,?,?,?)}";
            try {
                cStmt = conn.prepareCall(sqlStr);
                for (int i = 0; i < lineSet.getSize(); i++) {
                    AmsItemTransLDTO lineData = (AmsItemTransLDTO) lineSet.getDTO(i);
                    cStmt.setString(1, lineData.getBarcode());
                    cStmt.setString(2, lineData.getItemCode());
                    cStmt.setInt(3, lineData.getQuantity());
                    cStmt.setInt(4, sfUser.getOrganizationId());
                    cStmt.setInt(5, sfUser.getUserId());
                    cStmt.setString(6, amsItemTransH.getToObjectNo());
                    cStmt.execute();
                }
            } finally {
                DBManager.closeDBStatement(cStmt);
            }
        }
    }

    public void InvStorageIn(DTOSet lineSet) throws SQLException {
        if (lineSet != null && !lineSet.isEmpty()) {
            CallableStatement cStmt = null;
            String sqlStr = "{CALL AMS_ITEM_TRANS.INV_STORAGE_IN(?,?,?,?,?,?)}";
            try {
                cStmt = conn.prepareCall(sqlStr);
                for (int i = 0; i < lineSet.getSize(); i++) {
                    AmsItemTransLDTO lineData = (AmsItemTransLDTO) lineSet.getDTO(i);
                    cStmt.setString(1, lineData.getBatchNo());
                    cStmt.setString(2, amsItemTransH.getToObjectNo());
                    cStmt.setInt(3, amsItemTransH.getToOrganizationId());
                    cStmt.setString(4, lineData.getItemCode());
                    cStmt.setInt(5, lineData.getQuantity());
                    cStmt.setInt(6, sfUser.getUserId());
                    cStmt.execute();
                }
            } finally {
                DBManager.closeDBStatement(cStmt);
            }
        }
    }

    public void InvStorageOut(DTOSet lineSet) throws SQLException {
        if (lineSet != null && !lineSet.isEmpty()) {
            CallableStatement cStmt = null;
            String sqlStr = "{call AMS_ITEM_TRANS.INV_STORAGE_OUT(?,?,?)}";
            try {
                cStmt = conn.prepareCall(sqlStr);
                for (int i = 0; i < lineSet.getSize(); i++) {
                    AmsItemTransLDTO lineData = (AmsItemTransLDTO) lineSet.getDTO(i);
                    cStmt.setString(1, lineData.getStorageId());
                    cStmt.setInt(2, lineData.getOutQuantity());
                    cStmt.setInt(3, sfUser.getUserId());
                    cStmt.execute();
                }
            } finally {
                DBManager.closeDBStatement(cStmt);
            }
        }
    }

    public RowSet produceWebData(String barcode, String transId, String lineId1, String org, String objectNo) throws QueryException {
        BjfxsqModel model = new BjfxsqModel(sfUser, null);
        SQLModel sqlModel = model.getQty(barcode, transId, lineId1, org, objectNo);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        return sq.getSearchResult();
    }

    public RowSet getBanchNo(String transId) throws QueryException {
        BjfxsqModel model = new BjfxsqModel(sfUser, null);
        SQLModel sqlModel = model.getNo(transId);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        return sq.getSearchResult();
    }

    public void writeDetails(String lineId, String barcode, String transId, String orgId[], String HoldQty[], String detailId[]) throws SQLException, DataHandleException {
        CallableStatement cStmt = null;
        String sqlStr = "{CALL AMS_ITEM_TRANS_SX.SAVE_FXSQ_DETAIL(?,?,?,?,?,?,?)}";
        try {
            cStmt = conn.prepareCall(sqlStr);
            for (int i = 0; i < orgId.length; i++) {
                if (HoldQty[i].equals("")) {
                    BjfxsqModel model = new BjfxsqModel(sfUser, null);
                    DBOperator.updateRecord(model.deleteData(detailId[i], transId), conn);
                } else {
                    cStmt.setString(1, transId);
                    cStmt.setString(2, lineId);
                    cStmt.setString(3, detailId[i]);
                    cStmt.setString(4, orgId[i]);
                    cStmt.setString(5, "-1");
                    cStmt.setString(6, barcode);
                    cStmt.setString(7, HoldQty[i]);
                    cStmt.execute();
                }
            }


            prodMessage("UPDATE_DATA_SUCCESS");
            message.addParameterValue("分配信息");
        } finally {
            DBManager.closeDBStatement(cStmt);
        }
    }

    /**
     * 退回单据
     * @param transHDTO
     * @param flowDTO
     * @throws SQLException
     */
    public void reject(AmsItemTransHDTO transHDTO, FlowDTO flowDTO) throws SQLException {
        try {
            conn.setAutoCommit(false);

            transHDTO.setTransStatus(DictConstant.REJECTED);
            DBOperator.updateRecord(SpareModel.updateHeaderStatusModel(sfUser,transHDTO), conn);

            flowDTO.setProcName(SparePROCConstant.REPAIRE_BACK_PROC);
            FlowAction fb = new FlowAction(conn, flowDTO);
            fb.reject2Begin();
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } catch (DataHandleException e) {
            conn.rollback();
            Logger.logError(e);
            prodMessage("SPARE_SAVE_FAILURE");
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public boolean do_checkQty(String transId) throws SQLException, QueryException, ContainerException {
        boolean success = false;
        int SMQty = 0;
        int SQQty = 0;
        String smQty = reSMQty(transId);
        String sqQty = reSQQty(transId);
        if (!StrUtil.isEmpty(smQty)) {
            SMQty = Integer.parseInt(smQty);
        }
        if (!StrUtil.isEmpty(sqQty)) {
            SQQty = Integer.parseInt(sqQty);
        }
        success = SMQty == SQQty;
        return success;
    }


    public String reSMQty(String transId) throws QueryException, ContainerException {
        String qty = "";
        BjfxsqModel model = new BjfxsqModel(sfUser, null);
        SQLModel sqlModel = model.doveif(transId);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        Row row = sq.getFirstRow();
        if (sq.hasResult()) {
            qty = row.getStrValue("QUANTITY");
        }
//        return sq.getSearchResult();
        return qty;
    }


    public String reSQQty(String transId) throws QueryException, ContainerException {
        String qty = "";
        BjfxsqModel model = new BjfxsqModel(sfUser, null);
        SQLModel sqlModel = model.getSq(transId);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        Row row = sq.getFirstRow();
        if (sq.hasResult()) {
            qty = row.getStrValue("QUANTITY");
        }
        return qty;
    }

    public RowSet getDetailData(String transId) throws QueryException {
        SQLModel sqlModel=SpareModel.getDtlByTransIdModel(transId);
        SimpleQuery simpleQuery=new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        return simpleQuery.getSearchResult();
    }

    public void excuteFlow(FlowDTO flowDTO, String flowAction, boolean isNew) throws DataHandleException, SQLException, QueryException {
        FlowAction fa = new FlowAction(conn);
        fa.setDto(flowDTO);
        if (flowAction.equals(DictConstant.FLOW_SAVE)) {//暂存
            if (isNew) {
                fa.add2Flow(flowDTO.getProcName());
            }
        } else if (flowAction.equals(DictConstant.FLOW_COMPLETE)) {
            fa.flow();
        } else if (flowAction.equals(DictConstant.FLOW_CANCEL)) {
            fa.cancel();
        } else if (flowAction.equals(DictConstant.FLOW_BACK)) {
            flowDTO.setActivity(FlowConstant.FLOW_CODE_PREV);
            fa.setDto(flowDTO);
            fa.reject2Begin();
        }
    }

    public String hasObjectNo(String transId, String barcode) throws QueryException, ContainerException {
        String objectNo = "";
        BjfxsqModel model = new BjfxsqModel(sfUser, null);
        SQLModel sqlModel = model.hasObjectNo(transId, barcode);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        Row row = sq.getFirstRow();
        if (sq.hasResult()) {
            objectNo = row.getStrValue("OBJECT_NO");
        }
        return objectNo;
    }
}