package com.sino.ams.instrument.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.bean.OrderNumGenerator;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.instrument.dto.AmsInstrumentHDTO;
import com.sino.ams.instrument.dto.AmsInstrumentLDTO;
import com.sino.ams.instrument.model.AmsInstrumentCheckModel;
import com.sino.ams.instrument.model.AmsInstrumentDetectModel;
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
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.log.Logger;
import com.sino.base.util.CalendarUtil;
import com.sino.base.util.StrUtil;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * Created by sunny.
 * User: sunny
 * Date: 2008-4-8
 * Time: 6:27:29
 * To change this template use File | Settings | File Templates.
 */
public class AmsInstrumentDetectDAO extends BaseDAO {
     private SfUserDTO sfUser = null;
    private AmsInstrumentHDTO dto = null;

    public AmsInstrumentDetectDAO(SfUserDTO userAccount, AmsInstrumentHDTO dtoParameter, Connection conn) {
          super(userAccount, dtoParameter, conn);
          this.sfUser = userAccount;
          this.dto = (AmsInstrumentHDTO) super.dtoParameter;
      }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsInstrumentHDTO dtoPara = (AmsInstrumentHDTO) dtoParameter;
        super.sqlProducer = new AmsInstrumentDetectModel((SfUserDTO) userAccount, dtoPara);
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
         //   String isFull = dto.get
            if (transId.equals("")) {
                SeqProducer seq = new SeqProducer(conn);
                transId = StrUtil.nullToString(seq.getStrNextSeq("AMS_INSTRU_TRANS_H_S"));
                dto.setTransId(transId);
                createData();
                AmsInstrumentDetectModel model = new AmsInstrumentDetectModel(sfUser, null);
                for (int i = 0; i < barcode.length; i++) {
                    String code = barcode[i];
                    DBOperator.updateRecord(model.insertLData(code, transId), conn);
              //      DBOperator.updateRecord(model.insertRData(code, transId), conn);
                }
            } else {
                updateData();
                deleteLines(transId);
            }
            saveLines(lineSet);
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

    public boolean borrowData(DTOSet lineSet, String[] barcode) throws SQLException {
        boolean success = true;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            String transId = dto.getTransId();
            dto.setBorrowDate(CalendarUtil.getCurrDate());
            OrderNumGenerator ong = new OrderNumGenerator(conn, sfUser.getCompanyCode(), "INS-BRW");
            dto.setTransNo(ong.getOrderNum());
            if (transId.equals("")) {
                SeqProducer seq = new SeqProducer(conn);
                transId = StrUtil.nullToString(seq.getStrNextSeq("AMS_INSTRU_TRANS_H_S"));
                dto.setTransId(transId);
                createData();

                AmsInstrumentCheckModel model = new AmsInstrumentCheckModel(sfUser, null);
                for (int i = 0; i < barcode.length; i++) {
                    String code = barcode[i];
                    DBOperator.updateRecord(model.insertLData(code, transId), conn);
                }
            } else {
                updateData();
                deleteLines(transId);
            }
            saveLines(lineSet);

            if (dto.getTransType().equals(DictConstant.CREATE)) {
                /* addToItemInfo(lineSet);*/
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

    private void addToInstruInfo(DTOSet lineSet) throws DataHandleException {
        if (lineSet != null && !lineSet.isEmpty()) {
            String sqlStr = "";
            SQLModel sqlModel = new SQLModel();
            for (int i = 0; i < lineSet.getSize(); i++) {

            }
        }


    }

    public boolean submitData(DTOSet lineSet, String[] barcode) throws SQLException {
        boolean success = true;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            String transId = dto.getTransId();
            dto.setBorrowDate(CalendarUtil.getCurrDate());
            OrderNumGenerator ong = new OrderNumGenerator(conn, sfUser.getCompanyCode(), "INS-CHK");
            dto.setTransNo(ong.getOrderNum());
            if (transId.equals("")) {
                SeqProducer seq = new SeqProducer(conn);
                transId = StrUtil.nullToString(seq.getStrNextSeq("AMS_INSTRU_TRANS_H_S"));
                dto.setTransId(transId);
                createData();

                AmsInstrumentCheckModel model = new AmsInstrumentCheckModel(sfUser, null);
                for (int i = 0; i < barcode.length; i++) {
                    String code = barcode[i];
                    DBOperator.updateRecord(model.insertLData(code, transId), conn);
                }
            } else {
                updateData();
                deleteLines(transId);
            }
            saveLines(lineSet);

            /* if (dto.getTransType().equals(DictConstant.BJRK)) {
                addToItemInfo(lineSet);
            }*/
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
        AmsInstrumentDetectModel model = new AmsInstrumentDetectModel(sfUser, null);
        DBOperator.updateRecord(model.getDeleteByTransIdModel(transId), conn);
    }

    public void saveLines(DTOSet lineSet) throws DataHandleException {
        if (lineSet != null && !lineSet.isEmpty()) {
            AmsInstrumentBorrowDAO lineDAO = new AmsInstrumentBorrowDAO(sfUser, null, conn);
            for (int i = 0; i < lineSet.getSize(); i++) {
                AmsInstrumentHDTO lineData = (AmsInstrumentHDTO) lineSet.getDTO(i);
                lineData.setTransId(dto.getTransId());
                lineDAO.setDTOParameter(lineData);
                lineDAO.createData();
            }
        }
    }

    public DTOSet getLines() throws QueryException {
        AmsInstrumentDetectModel model = new AmsInstrumentDetectModel(sfUser, null);
        SimpleQuery sq = new SimpleQuery(model.getByTransIdModel(dto.getTransId()), conn);
        sq.setDTOClassName(AmsInstrumentLDTO.class.getName());
        sq.executeQuery();

        return sq.getDTOSet();
    }

    public void checkData(String check, String currKeepUser,String tranId) throws DataHandleException {
        AmsInstrumentDetectModel model = new AmsInstrumentDetectModel(sfUser, null);
        DBOperator.updateRecord(model.getDataCheckModel(check, currKeepUser), conn);
        DBOperator.updateRecord(model.updateType(tranId), conn);
    }

    public File exportFile() throws DataTransException {
        File file = null;
        try {
            SQLModel sqlModel = sqlProducer.getPageQueryModel();
            TransRule rule = new TransRule();
            rule.setDataSource(sqlModel);
            rule.setCalPattern(CalendarConstant.LINE_PATTERN);
            rule.setSourceConn(conn);
            String fileName = "仪器检测信息.xls";
            String filePath = WorldConstant.USER_HOME;
            filePath += WorldConstant.FILE_SEPARATOR;
            filePath += fileName;
            rule.setTarFile(filePath);
            DataRange range = new DataRange();
            rule.setDataRange(range);

            Map fieldMap = new HashMap();
            fieldMap.put("ORG_NAME", "公司");
            fieldMap.put("ASSET_NUMBER", "资产编号");
            fieldMap.put("BARCODE", "资产条码");
            fieldMap.put("ITEM_NAME", "资产名称");
            fieldMap.put("ITEM_SPEC", "规格型号");
            fieldMap.put("ITEM_CATEGORY", "资产类别");
            fieldMap.put("DATE_PLACED_IN_SERVICE", "启用日期");
            fieldMap.put("LIFE_IN_YEARS", "折旧年限");
            fieldMap.put("VENDOR_NAME", "供应商");

            rule.setFieldMap(fieldMap);

            CustomTransData custData = new CustomTransData();
            custData.setReportTitle("仪器检测信息");
            custData.setReportPerson(sfUser.getUsername());
            custData.setNeedReportDate(true);
            rule.setCustData(custData);
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
}
