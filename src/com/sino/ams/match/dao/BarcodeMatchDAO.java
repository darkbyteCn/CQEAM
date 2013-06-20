package com.sino.ams.match.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.sino.base.constant.WorldConstant;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.db.datatrans.*;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.log.Logger;
import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.match.dto.BarcodeMatchDTO;
import com.sino.ams.match.model.BarcodeMatchModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: Zyun
 * Date: 2007-11-29
 * Time: 9:36:46
 * To change this template use File | Settings | File Templates.
 */
public class BarcodeMatchDAO extends AMSBaseDAO {

    /**
     * 功能：资产匹配-匹配数据存储(EAM) ETS_ITEM_MATCH 数据访问层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter EtsItemMatchDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
    public BarcodeMatchDAO(SfUserDTO userAccount, BarcodeMatchDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        BarcodeMatchDTO dtoPara = (BarcodeMatchDTO) dtoParameter;
        super.sqlProducer = new BarcodeMatchModel((SfUserDTO) userAccount, dtoPara);
    }

    public void deleteData() throws DataHandleException {
        super.deleteData();
    }

    /**
     * 功能：对选中的数据进行匹配
     * @param checkedAssets DTOSet
     * @return String
     */
    public String matchCheckedAssets(DTOSet checkedAssets) {
        String ret = "Y";
        boolean hasError = true;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            BarcodeMatchModel modelProducer = (BarcodeMatchModel) sqlProducer;
            SQLModel sqlModel = null;
            SimpleQuery sq = null;
            int checkedCount = checkedAssets.getSize();
            for (int i = 0; i < checkedCount; i++) {
                BarcodeMatchDTO bardto = (BarcodeMatchDTO) checkedAssets.getDTO(i);
                setDTOParameter(bardto);
                super.createData(); //写ETS_ITEM_MATCH表
                super.updateData(); //更新表ETS_ITEM_INFO    FINAMCE_PROP='ASSETS'  LAST_UPDATE_DATE='GETDATE()'  LAST_UPDATE_BY=当前用户
                sqlModel = modelProducer.getHasBeenModel();
                if (sq == null) {
                    sq = new SimpleQuery(sqlModel, conn);
                } else {
                    sq.setSql(sqlModel);
                }
                sq.executeQuery();
                if (!sq.hasResult()) { // 判断 表 ETS_ITEM_MATCH_REC 1若有就修改操作 2若没有就插入操作
                    insertIntoEIMR();
                } else {
                    updateEIMR();
                }
                insertIntoEIMRL(); //插入表 ETS_ITEM_MATCH_REC_LOG
                updateEIMC(); //更新表ETS_ITEM_MATCH_COMPLET.CURRENT_UNITS+1
            }
            conn.commit();
            hasError = false;
        } catch (SQLException e) {
            Logger.logError(e);
        } catch (DataHandleException e) {
            e.printStackTrace();
        } catch (QueryException e) {
            e.printStackTrace();
        } finally {
            try {
                if (hasError) {
                    ret = "N";
                    conn.rollback();
                }
                conn.setAutoCommit(autoCommit);
            } catch (SQLException e) {
                Logger.logError(e);
            }
        }
        return ret;
    }

    public String doMatch(String[] systemids) {
        String ret = "Y";
        boolean hasError = true;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            BarcodeMatchModel modelProducer = (BarcodeMatchModel) sqlProducer;
            SQLModel sqlModel = null;
            SimpleQuery sq = null;
            for (int i = 0; i < systemids.length; i++) {
                BarcodeMatchDTO bardto = (BarcodeMatchDTO) dtoParameter;
                String syssa[] = systemids[i].split("@@@");
                bardto.setSystemid(syssa[0]);
                bardto.setAssetId(StrUtil.strToInt(syssa[1]));
                setDTOParameter(bardto);
                super.createData(); //写ETS_ITEM_MATCH表
                super.updateData(); //更新表ETS_ITEM_INFO    FINAMCE_PROP='ASSETS'  LAST_UPDATE_DATE='GETDATE()'  LAST_UPDATE_BY=当前用户
                boolean hasMatchRecord=hasMatchRec();
                if (hasMatchRecord) { // 判断 表 ETS_ITEM_MATCH_REC 1若有:删除、新增操作 2若没有就插入操作
                    deleteEIMR();
                    insertIntoEIMR();
                } else {
                    insertIntoEIMR();
                }
                insertIntoEIMRL(); //插入表 ETS_ITEM_MATCH_REC_LOG
                updateEIMC(); //更新表ETS_ITEM_MATCH_COMPLET.CURRENT_UNITS+1
            }
            conn.commit();
            hasError = false;
        } catch (SQLException e) {
            Logger.logError(e);
        } catch (DataHandleException e) {
            e.printStackTrace();
        } catch (QueryException e) {
            e.printStackTrace();
        } finally {
            try {
                if (hasError) {
                    ret = "N";
                    conn.rollback();
                }
                conn.setAutoCommit(autoCommit);
            } catch (SQLException e) {
                Logger.logError(e);
            }
        }
        return ret;
    }

    /**
     * 功能：插入ETS_ITEM_MATCH_REC
     * @throws DataHandleException
     */
    public void insertIntoEIMR() throws DataHandleException {
        BarcodeMatchModel modelProducer = (BarcodeMatchModel) sqlProducer;
        SQLModel sqlModel = modelProducer.insertIntoEIMRModel();
        DBOperator.updateRecord(sqlModel, conn);
    }

    /**
     * 更新ETS_ITEM_MATCH_REC
     * @throws DataHandleException
     */
    public void updateEIMR() throws DataHandleException {
        BarcodeMatchModel modelProducer = (BarcodeMatchModel) sqlProducer;
        SQLModel sqlModel = modelProducer.updateEIMRModel();
        DBOperator.updateRecord(sqlModel, conn);
    }

    /**
     * 删除ETS_ITEM_MATCH_REC
     * @throws DataHandleException
     */
    public void deleteEIMR() throws DataHandleException {
        BarcodeMatchModel modelProducer = (BarcodeMatchModel) sqlProducer;
        SQLModel sqlModel = modelProducer.deleteEIMRModel();
        DBOperator.updateRecord(sqlModel, conn);
    }

    /**
     * 检查是否有匹配记录
     * @return
     * @throws QueryException
     */
    public boolean hasMatchRec() throws QueryException {
        BarcodeMatchModel modelProducer = (BarcodeMatchModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getHasBeenModel();
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        return sq.hasResult();
    }


    /**
     * 插入ETS_ITEM_MATCH_REC_LOG
     * @throws DataHandleException
     */
    public void insertIntoEIMRL() throws DataHandleException {
        BarcodeMatchModel modelProducer = (BarcodeMatchModel) sqlProducer;
        SQLModel sqlModel = modelProducer.insertIntoEIMRLModel();
        DBOperator.updateRecord(sqlModel, conn);
    }

    /**
     * 更新ETS_ITEM_MATCH_COMPLET
     * @throws DataHandleException
     */
    public void updateEIMC() throws DataHandleException {
        BarcodeMatchModel modelProducer = (BarcodeMatchModel) sqlProducer;
        SQLModel sqlModel = modelProducer.updateEIMCModel();
        DBOperator.updateRecord(sqlModel, conn);
    }

    /**
     * 功能：导出Excel文件。
     * @return File
     * @throws com.sino.base.exception.DataTransException
     *
     */
    public File exportFile() throws DataTransException {
        File file = null;
        try {
            SQLModel sqlModel = sqlProducer.getPageQueryModel();
            TransRule rule = new TransRule();
            rule.setDataSource(sqlModel);
            rule.setCalPattern(CalendarConstant.LINE_PATTERN);
            rule.setSourceConn(conn);
            String fileName = "条码匹配信息表.xls";
            String filePath = WorldConstant.USER_HOME;
            filePath += WorldConstant.FILE_SEPARATOR;
            filePath += fileName;
            rule.setTarFile(filePath);
            rule.setCalPattern(LINE_PATTERN);
            DataRange range = new DataRange();
            rule.setDataRange(range);
            Map fieldMap = new HashMap();
            fieldMap.put("BARCODE", "EAM条码");
            fieldMap.put("FA_BARCODE", "MIS资产条码");
            fieldMap.put("ITEM_DESCRIPTION", "EAM设备名称");
            fieldMap.put("ASSETS_DESCRIPTION", "MIS设备名称");
            fieldMap.put("SPEC", "EAM规格型号");
            fieldMap.put("MIS_SPEC", "MIS规格型号");
            fieldMap.put("WORKORDER_OBJECT_CODE", "EAM地点代码");
            fieldMap.put("ETS_LOCATION", "EAM地点");
            fieldMap.put("ASSETS_LOCATION_CODE", "MIS地点代码");
            fieldMap.put("MIS_LOCATION", "MIS地点");
            fieldMap.put("USER_NAME", "EAM责任人");
            fieldMap.put("ASSIGNED_TO_NAME", "MIS责任人");
            fieldMap.put("CURRENT_UNITS", "MIS数量");
            fieldMap.put("FA_CODE", "MIS目录代码");
            fieldMap.put("FA_CATEGORY2", "MIS目录描述");
            fieldMap.put("CONTENT_CODE", "EAM目录代码");
            fieldMap.put("CONTENT_NAME", "EAM目录描述");
            rule.setFieldMap(fieldMap);
            CustomTransData custData = new CustomTransData();
            custData.setReportTitle("条码匹配信息表");
            custData.setReportPerson(userAccount.getUsername());
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
