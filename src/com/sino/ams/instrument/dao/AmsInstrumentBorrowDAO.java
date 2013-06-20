package com.sino.ams.instrument.dao;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.bean.OrderNumGenerator;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.instrument.dto.AmsInstrumentHDTO;
import com.sino.ams.instrument.dto.AmsInstrumentLDTO;
import com.sino.ams.instrument.model.AmsInstrumentBorrowModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
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
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.log.Logger;
import com.sino.base.util.CalendarUtil;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2007-10-23
 * Time: 9:24:41
 * To change this template use File | Settings | File Templates.
 */
public class AmsInstrumentBorrowDAO extends AMSBaseDAO {
    private AmsInstrumentHDTO dto = null;

    public AmsInstrumentBorrowDAO(SfUserDTO userAccount, AmsInstrumentHDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        this.dto = (AmsInstrumentHDTO) super.dtoParameter;
    }


    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsInstrumentHDTO dtoPara = (AmsInstrumentHDTO) dtoParameter;
        super.sqlProducer = new AmsInstrumentBorrowModel((SfUserDTO) userAccount, dtoPara);
    }

    public void createData() throws DataHandleException {
        super.createData();
        getMessage().addParameterValue("仪器头表(EAM)");
    }

    public void updateData() throws DataHandleException {
        super.updateData();
        getMessage().addParameterValue("仪器头表(EAM)");
    }

    public void deleteData() throws DataHandleException {
        super.deleteData();
        getMessage().addParameterValue("仪器头表(EAM)");
    }

    public boolean saveData(DTOSet lineSet, String[] barcode) throws SQLException {
        boolean success = true;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            String transId = dto.getTransId();
            if (transId.equals("")) {
                SeqProducer seq = new SeqProducer(conn);
                transId = StrUtil.nullToString(seq.getStrNextSeq("AMS_INSTRU_TRANS_H_S"));
                dto.setTransId(transId);
                dto.setTransType(DictConstant.INS_BRW);
                createData();
                AmsInstrumentBorrowModel model = new AmsInstrumentBorrowModel(userAccount, null);
                for (int i = 0; i < barcode.length; i++) {
                    String code = barcode[i];
                    DBOperator.updateRecord(model.insertLData(code, transId), conn);
//                    DBOperator.updateRecord(model.insertRData(code, transId), conn);
                }
            } else {
                updateData();                  //更改头表
                deleteLines(transId);           //删除行表
            }
//            saveLines(lineSet);                  //头表新增操作
            conn.commit();
            prodMessage("ASSETS_TRANSFER_SUCCESS");
            success = true;
        } catch (SQLException e) {
            conn.rollback();
            success = false;
            Logger.logError(e);
            prodMessage("SPARE_SAVE_FAILURE");
        } catch (DataHandleException e) {
            conn.rollback();
            success = false;
            Logger.logError(e);
            prodMessage("SPARE_SAVE_FAILURE");
        } finally {
            conn.setAutoCommit(autoCommit);
        }

        return success;
    }

    public boolean borrowData(DTOSet lineSet, String[] barcode, String cuser, String transId, String transStatus) throws SQLException {
        boolean success = true;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            dto.setConfirmDate(CalendarUtil.getCurrDate());
            dto.setTransType(DictConstant.INS_BRW);
            AmsInstrumentBorrowModel model = new AmsInstrumentBorrowModel(userAccount, null);
            DBOperator.updateRecord(model.updateHModel(transId, transStatus), conn);
            for (int i = 0; i < barcode.length; i++) {
                String code = barcode[i];
                DBOperator.updateRecord(model.updateInfoModel(cuser, code), conn);
            }
            updateAddress(barcode);    //更改地址
            conn.commit();
            prodMessage("SPARE_SAVE_SUCCESS");
            success = true;
        } catch (SQLException e) {
            conn.rollback();
            success = false;
            Logger.logError(e);
            prodMessage("SPARE_SAVE_FAILURE");
        } catch (DataHandleException e) {
            conn.rollback();
            success = false;
            Logger.logError(e);
            prodMessage("SPARE_SAVE_FAILURE");
        } catch (CalendarException e) {
            conn.rollback();
            success = false;
            Logger.logError(e);
            prodMessage("SPARE_SAVE_FAILURE");
        } finally {
            conn.setAutoCommit(autoCommit);
        }
        return success;
    }

    //单据撤销操作
    public boolean repalData(DTOSet lineSet, String transId, String transStatus, String[] barcodeNo) throws SQLException {
        boolean success = true;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            dto.setConfirmDate(CalendarUtil.getCurrDate());
            AmsInstrumentBorrowModel model = new AmsInstrumentBorrowModel(userAccount, null);
            DBOperator.updateRecord(model.updateHRepalModel(transId, transStatus), conn);   //修改仪器仪表的头表的信息为撤销
//            for (int i = 0; i < barcodeNo.length; i++) {
//                String codeNo = barcodeNo[i];
//                DBOperator.updateRecord(model.deleteByBarcodeNoModel(codeNo), conn);
//            }
//            deleteLines(transId);        //删除行表信息
            for (int i = 0; i < barcodeNo.length; i++) {
                String codeNo = barcodeNo[i];
//                DBOperator.updateRecord(model.deleteByBarcodeNoModel(codeNo), conn);
                DBOperator.updateRecord(model.cancelItemInfo(codeNo), conn);          //更改ets_item_info的责任人，仪具持有人
            }
//            DBOperator.updateRecord(model,conn);
            conn.commit();
            prodMessage("SPARE_SAVE_SUCCESS");
            success = true;
        } catch (SQLException e) {
            conn.rollback();
            success = false;
            Logger.logError(e);
            prodMessage("SPARE_SAVE_FAILURE");
        } catch (DataHandleException e) {
            conn.rollback();
            success = false;
            Logger.logError(e);
            prodMessage("SPARE_SAVE_FAILURE");
        } catch (CalendarException e) {
            conn.rollback();
            success = false;
            Logger.logError(e);
            prodMessage("SPARE_SAVE_FAILURE");
        } finally {
            conn.setAutoCommit(autoCommit);
        }
        return success;
    }


    public boolean submitData(DTOSet lineSet, String[] barcode) throws SQLException {
        boolean success = true;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            String transId = dto.getTransId();
            dto.setBorrowDate(CalendarUtil.getCurrDate());
            OrderNumGenerator ong = new OrderNumGenerator(conn, userAccount.getCompanyCode(), "INS-BRW");
            dto.setTransNo(ong.getOrderNum());    //获得单据号
            if (transId.equals("")) {
                SeqProducer seq = new SeqProducer(conn);
                transId = StrUtil.nullToString(seq.getStrNextSeq("AMS_INSTRU_TRANS_H_S"));
                dto.setTransId(transId);
                createData();                   //对仪器仪表头表进行插入操作

                AmsInstrumentBorrowModel model = new AmsInstrumentBorrowModel(userAccount, null);
                for (int i = 0; i < barcode.length; i++) {
                    String code = barcode[i];
                    DBOperator.updateRecord(model.insertLData(code, transId), conn);      //仪器仪表行表
                    DBOperator.updateRecord(model.insertRData(code, transId), conn);       //记录表
                }
            } else {
                updateData();         //对仪器仪表头表进行修改操作
                deleteLines(transId);     //删除行表信息
                AmsInstrumentBorrowModel model = new AmsInstrumentBorrowModel(userAccount, null);
                for (int i = 0; i < barcode.length; i++) {
                    String code = barcode[i];
                    DBOperator.updateRecord(model.insertLData(code, transId), conn);    //对行表进行新增操作
                }
            }
            saveLines(lineSet);           //仪器仪表头表新增操作
            conn.commit();
            prodMessage("SPARE_SAVE_SUCCESS");
            success = true;
        } catch (SQLException e) {
            conn.rollback();
            success = false;
            Logger.logError(e);
            prodMessage("SPARE_SAVE_FAILURE");
        } catch (DataHandleException e) {
            conn.rollback();
            success = false;
            Logger.logError(e);
            prodMessage("SPARE_SAVE_FAILURE");
        } catch (CalendarException e) {
            conn.rollback();
            success = false;
            Logger.logError(e);
            prodMessage("SPARE_SAVE_FAILURE");
        } finally {
            conn.setAutoCommit(autoCommit);
        }
        return success;
    }

    public void deleteLines(String transId) throws DataHandleException {
        AmsInstrumentBorrowModel model = new AmsInstrumentBorrowModel(userAccount, null);
        DBOperator.updateRecord(model.getDeleteByTransIdModel(transId), conn);
    }

    public void saveLines(DTOSet lineSet) throws DataHandleException {
        if (lineSet != null && !lineSet.isEmpty()) {
            AmsInstrumentBorrowDAO lineDAO = new AmsInstrumentBorrowDAO(userAccount, null, conn);
            for (int i = 0; i < lineSet.getSize(); i++) {
                AmsInstrumentHDTO lineData = (AmsInstrumentHDTO) lineSet.getDTO(i);
                lineData.setTransId(dto.getTransId());
                lineDAO.setDTOParameter(lineData);
                lineDAO.createData();
            }
        }
    }

    public DTOSet getLines() throws QueryException {
        AmsInstrumentBorrowModel model = new AmsInstrumentBorrowModel(userAccount, null);
        SimpleQuery sq = new SimpleQuery(model.getByTransIdModel(dto.getTransId()), conn);
        sq.setDTOClassName(AmsInstrumentLDTO.class.getName());
        sq.executeQuery();

        return sq.getDTOSet();
    }

    public File exportFile() throws DataTransException {
        File file = null;
        try {
            AmsInstrumentHDTO itemDTO = (AmsInstrumentHDTO) dtoParameter;
            SQLModel sqlModel = sqlProducer.getPageQueryModel();
            TransRule rule = new TransRule();
            rule.setDataSource(sqlModel);
            rule.setSourceConn(conn);

            String fileName = "仪具借用单据统计表.xls";
            String filePath = WorldConstant.USER_HOME;
            filePath += WorldConstant.FILE_SEPARATOR;
            filePath += fileName;
            rule.setTarFile(filePath);

            DataRange range = new DataRange();
            rule.setDataRange(range);

            Map fieldMap = new HashMap();
            fieldMap.put("TRANS_NO", "返还单据号");
            fieldMap.put("RNAME", "返还人");
            fieldMap.put("BORROW_DATE", "返还日期");
            fieldMap.put("QNAME", "确认人");
            fieldMap.put("CONFIRM_DATE", "确认时间");
            fieldMap.put("TRANS_STATUS", "单据状态");
            rule.setFieldMap(fieldMap);

            CustomTransData custData = new CustomTransData();
            custData.setReportTitle(fileName);
            custData.setReportPerson(userAccount.getUsername());
            custData.setNeedReportDate(true);
            rule.setCustData(custData);
            /*rule.setSheetSize(1000);*/
            //设置分页显示
            TransferFactory factory = new TransferFactory();
            DataTransfer transfer = factory.getTransfer(rule);
            transfer.transData();
            file = (File) transfer.getTransResult();
        } catch (SQLModelException ex) {
            ex.printLog();
            throw new DataTransException(ex);
        }
        return file;
    }

    private void updateAddress(String[] barcode) throws SQLException {
        CallableStatement cStmt = null;
        String sqlStr = "{call AMS_ITEM_TRANS.INSTRU_BORROW(?,?)}";
        try {
            cStmt = conn.prepareCall(sqlStr);
            for (int i = 0; i < barcode.length; i++) {
                cStmt.setString(1, barcode[i]);
                cStmt.setString(2, StrUtil.nullToString(userAccount.getUserId()));
                cStmt.execute();
            }
        }
        finally {
            DBManager.closeDBStatement(cStmt);
        }
    }

    public boolean borrowData(DTOSet lineSet, String[] barcode) throws SQLException {
        // 完成借用操作
        //  1.AMS_INSTRU_TRANS_H.TRANS_STATUS 新增操作  单据状态：新增 (新增，暂存，完成，撤销)
        //  2。AMS_INSTRU_TRANS_L新增操作
        //  3.更改ETS_ITEM_INFO.ITEM_STATUS的状态为：借出（正常，借出，送修，报废） ETS_ITEM_INFO.ATTRIBUTE3为设备用途
        //  ETS_ITEM_INFO.RESPONSIBILITY_USER 责任人为 仪器仪表管理员  ETS_ITEM_INFO.MAINTAIN_USER 为仪具在谁的手里"空"表示在仪具库（77）
        boolean success = true;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
//             AmsInstrumentHDTO itemDTO = (AmsInstrumentHDTO) dtoParameter;
            String transId = dto.getTransId();
            dto.setBorrowDate(CalendarUtil.getCurrDate());
            OrderNumGenerator ong = new OrderNumGenerator(conn, userAccount.getCompanyCode(), "INS-BRW");
            dto.setTransNo(ong.getOrderNum());    //获得单据号
            if (transId.equals("")) {             //新增即确认
                SeqProducer seq = new SeqProducer(conn);
                transId = StrUtil.nullToString(seq.getStrNextSeq("AMS_INSTRU_TRANS_H_S"));
                dto.setTransId(transId);
                createData();                   //对仪器仪表头表进行插入操作
//                String amintain = dto.getBorrowUser();
//                String amintain = dto.getUserId();
                String amintain = dto.getBorrowName();
                AmsInstrumentBorrowModel model = new AmsInstrumentBorrowModel(userAccount, null);

                for (int i = 0; i < barcode.length; i++) {
                    String code = barcode[i];
                    DBOperator.updateRecord(model.insertLData(code, transId), conn);      //仪器仪表行表
//                    DBOperator.updateRecord(model.insertRData(code, transId), conn);       //记录表
                    DBOperator.updateRecord(model.updateItemInfo(amintain, code), conn);    //更改ets_item_info的责任人，仪具持有人
                  //  DBOperator.updateRecord(model.updateItemStatus(code),conn);
                }
            } else {     //暂存后确认（当表中ets_item_info中的状态不为1，借出；2，送修；3，报废）
//                String amintain = dto.getUserId();
                String amintain = dto.getBorrowName();
                updateData();         //对仪器仪表头表进行修改操作
                deleteLines(transId);     //删除行表信息
                AmsInstrumentBorrowModel model = new AmsInstrumentBorrowModel(userAccount, null);
                for (int i = 0; i < barcode.length; i++) {
                    String code = barcode[i];
                    DBOperator.updateRecord(model.insertLData(code, transId), conn);    //对 行 表进行新增操作
                    DBOperator.updateRecord(model.updateItemInfo(amintain, code), conn);    //更改ets_item_info的责任人，仪具持有人
                    //DBOperator.updateRecord(model.updateItemStatus(code),conn);
                }
            }
//            saveLines(lineSet);           //仪器仪表头表新增操作
            conn.commit();
            prodMessage("SPARE_SAVE_SUCCESS");
            success = true;
        } catch (SQLException e) {
            conn.rollback();
            success = false;
            Logger.logError(e);
            prodMessage("SPARE_SAVE_FAILURE");
        } catch (DataHandleException e) {
            conn.rollback();
            success = false;
            Logger.logError(e);
            prodMessage("SPARE_SAVE_FAILURE");
        } catch (CalendarException e) {
            conn.rollback();
            success = false;
            Logger.logError(e);
            prodMessage("SPARE_SAVE_FAILURE");
        } finally {
            conn.setAutoCommit(autoCommit);
        }
        return success;
    }

    /**
     * 功能:单据的暂存.
     * @param lineSet
     * @param barcode
     * @return
     * @throws SQLException
     */
    public boolean tempSaveData(DTOSet lineSet, String[] barcode) throws SQLException {    //暂存信息操作
        boolean success = true;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
//             AmsInstrumentHDTO itemDTO = (AmsInstrumentHDTO) dtoParameter;
            String transId = dto.getTransId();
            dto.setBorrowDate(CalendarUtil.getCurrDate());
            OrderNumGenerator ong = new OrderNumGenerator(conn, userAccount.getCompanyCode(), "INS-BRW");
//            dto.setTransNo(ong.getOrderNum());    //获得单据号
            if (transId.equals("")) {
                SeqProducer seq = new SeqProducer(conn);
                transId = StrUtil.nullToString(seq.getStrNextSeq("AMS_INSTRU_TRANS_H_S"));
                dto.setTransId(transId);
                createData();                   //对仪器仪表头表进行插入操作
//                String amintain = dto.getBorrowUser();
                String amintain = dto.getBorrowName();
                AmsInstrumentBorrowModel model = new AmsInstrumentBorrowModel(userAccount, null);
                for (int i = 0; i < barcode.length; i++) {
                    String code = barcode[i];
                    DBOperator.updateRecord(model.insertLData(code, transId), conn);      //仪器仪表行表
                    DBOperator.updateRecord(model.tempItemInfo(amintain, code), conn);    //更改ets_item_info的责任人，仪具持有人
                }
            }
            conn.commit();
            prodMessage("SPARE_SAVE_SUCCESS");
            success = true;
        } catch (SQLException e) {
            conn.rollback();
            success = false;
            Logger.logError(e);
            prodMessage("SPARE_SAVE_FAILURE");
        } catch (DataHandleException e) {
            conn.rollback();
            success = false;
            Logger.logError(e);
            prodMessage("SPARE_SAVE_FAILURE");
        } catch (CalendarException e) {
            conn.rollback();
            success = false;
            Logger.logError(e);
            prodMessage("SPARE_SAVE_FAILURE");
        } finally {
            conn.setAutoCommit(autoCommit);
        }
        return success;
    }

        //地市向省公司借用还要写地点信息
    public boolean borrowProData(DTOSet lineSet, String[] barcode) throws SQLException {
        // 完成借用操作
        //  1.AMS_INSTRU_TRANS_H.TRANS_STATUS 新增操作  单据状态：新增 (新增，暂存，完成，撤销)
        //  2。AMS_INSTRU_TRANS_L新增操作
        //  3.更改ETS_ITEM_INFO.ITEM_STATUS的状态为：借出（正常，借出，送修，报废） ETS_ITEM_INFO.ATTRIBUTE3为设备用途
        //  ETS_ITEM_INFO.RESPONSIBILITY_USER 责任人为 仪器仪表管理员  ETS_ITEM_INFO.MAINTAIN_USER 为仪具在谁的手里"空"表示在仪具库（77）
        boolean success = true;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
//             AmsInstrumentHDTO itemDTO = (AmsInstrumentHDTO) dtoParameter;
            String transId = dto.getTransId();
            dto.setBorrowDate(CalendarUtil.getCurrDate());
            OrderNumGenerator ong = new OrderNumGenerator(conn, userAccount.getCompanyCode(), "INS-BRW");
            dto.setTransNo(ong.getOrderNum());    //获得单据号
            if (transId.equals("")) {             //新增即确认
                SeqProducer seq = new SeqProducer(conn);
                transId = StrUtil.nullToString(seq.getStrNextSeq("AMS_INSTRU_TRANS_H_S"));
                dto.setTransId(transId);
                createData();                   //对仪器仪表头表进行插入操作
//                String amintain = dto.getBorrowUser();
                String amintain = StrUtil.nullToString(dto.getUserId());
                int organizationId = dto.getOrganizationId();
                String addressId = dto.getAddressId();

                AmsInstrumentBorrowModel model = new AmsInstrumentBorrowModel(userAccount, null);
                for (int i = 0; i < barcode.length; i++) {
                    String code = barcode[i];
                    DBOperator.updateRecord(model.insertLData(code, transId), conn);      //仪器仪表行表
//                    DBOperator.updateRecord(model.insertRData(code, transId), conn);       //记录表
                    DBOperator.updateRecord(model.provItemInfo(amintain, code,organizationId,addressId), conn);    //更改ets_item_info的责任人，仪具持有人
                }
            } else {     //暂存后确认（当表中ets_item_info中的状态不为1，借出；2，送修；3，报废）
                String amintain = StrUtil.nullToString(dto.getUserId());
                updateData();         //对仪器仪表头表进行修改操作
                deleteLines(transId);     //删除行表信息
                AmsInstrumentBorrowModel model = new AmsInstrumentBorrowModel(userAccount, null);
                for (int i = 0; i < barcode.length; i++) {
                    String code = barcode[i];
                    DBOperator.updateRecord(model.insertLData(code, transId), conn);    //对 行 表进行新增操作
                    DBOperator.updateRecord(model.updateItemInfo(amintain, code), conn);    //更改ets_item_info的责任人，仪具持有人
                }
            }
//            saveLines(lineSet);           //仪器仪表头表新增操作


            conn.commit();
            prodMessage("SPARE_SAVE_SUCCESS");
            success = true;
        } catch (SQLException e) {
            conn.rollback();
            success = false;
            Logger.logError(e);
            prodMessage("SPARE_SAVE_FAILURE");
        } catch (DataHandleException e) {
            conn.rollback();
            success = false;
            Logger.logError(e);
            prodMessage("SPARE_SAVE_FAILURE");
        } catch (CalendarException e) {
            conn.rollback();
            success = false;
            Logger.logError(e);
            prodMessage("SPARE_SAVE_FAILURE");
        } finally {
            conn.setAutoCommit(autoCommit);
        }
        return success;
    }

    /**
        * 功能：根据UserId获得 organizationId
        * @return String
        * @throws com.sino.base.exception.QueryException
        *
        */
        public String getAddressId(String organization) throws QueryException {
        String addresId = "";
//        AmsInstrumentHDTO amsInstrumentInfo = (AmsInstrumentHDTO) dtoParameter;
         AmsInstrumentBorrowModel model = new AmsInstrumentBorrowModel(userAccount, null);
        SimpleQuery sq = new SimpleQuery(model.selectAddressId(organization), conn);
        sq.executeQuery();
        if (sq.hasResult()) {
            RowSet row = sq.getSearchResult();
            try {
                addresId = (String) row.getRow(0).getValue("ADDRESS_ID");
            } catch (ContainerException e) {
                e.printStackTrace();
                throw new QueryException();
            }
        }
        return addresId;
    }

}
