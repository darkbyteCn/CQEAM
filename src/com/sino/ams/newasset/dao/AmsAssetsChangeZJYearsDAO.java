package com.sino.ams.newasset.dao;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.dto.AmsAssetsCJYCDTO;
import com.sino.ams.newasset.model.AmsAssetsChangeZJYearsModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.datatrans.CustomTransData;
import com.sino.base.db.datatrans.DataRange;
import com.sino.base.db.datatrans.DataTransfer;
import com.sino.base.db.datatrans.TransRule;
import com.sino.base.db.datatrans.TransferFactory;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: srf
 * Date: 2008-4-7
 * Time: 17:19:22
 * To change this template use File | Settings | File Templates.
 */
public class AmsAssetsChangeZJYearsDAO extends AMSBaseDAO {
    private SfUserDTO sfUser = null;
    private AmsAssetsCJYCDTO dto = null;

    public AmsAssetsChangeZJYearsDAO(SfUserDTO userAccount, AmsAssetsCJYCDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        this.sfUser = userAccount;
        this.dto = (AmsAssetsCJYCDTO) super.dtoParameter;
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsAssetsCJYCDTO dtoPara = (AmsAssetsCJYCDTO) dtoParameter;
        super.sqlProducer = new AmsAssetsChangeZJYearsModel((SfUserDTO) userAccount, dtoPara);
    }

    public boolean batchUpdateYears() throws DataHandleException, SQLException {
        boolean autoCommit = false;
        boolean success = true;
        CallableStatement cStmt = null;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            AmsAssetsChangeZJYearsModel model = new AmsAssetsChangeZJYearsModel(sfUser, dto);
            DBOperator.updateRecord(model.UpdateYears(), conn);
            DBOperator.updateRecord(model.deleteAmount(), conn);
            DBOperator.updateRecord(model.insertAmount(), conn);
            String sqlStr = "{call AMS_DEPRECIATION_PKG.UPDATE_DEPRECIATION_AMOUNT(?,?)}";
            cStmt = conn.prepareCall(sqlStr);
            cStmt.setString(1, dto.getFaCatName2());
            cStmt.setString(2, dto.getNewYears());
            cStmt.execute();
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            success = false;
            Logger.logError(e);
            prodMessage("SAVE_FAILURE");
        } catch (DataHandleException e) {
            conn.rollback();
            throw e;
        } finally {
            DBManager.closeDBStatement(cStmt);
            conn.setAutoCommit(autoCommit);
        }
        return success;
    }

    public boolean updateYear(String faCategory2, String year,String orgId) throws DataHandleException, SQLException {
        boolean autoCommit = false;
        boolean success = true;
        CallableStatement cStmt = null;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            AmsAssetsChangeZJYearsModel model = new AmsAssetsChangeZJYearsModel(sfUser, dto);
            DBOperator.updateRecord(model.updateYear(faCategory2, year), conn);
             DBOperator.updateRecord(model.deleteAmount1(faCategory2), conn);
            DBOperator.updateRecord(model.insertAmount1(faCategory2), conn);
            String sqlStr = "{call AMS_DEPRECIATION_PKG.UPDATE_DEPRECIATION_AMOUNT1(?,?)}";
            cStmt = conn.prepareCall(sqlStr);
            cStmt.setString(1, faCategory2);
            cStmt.setString(2, year);
            cStmt.execute();
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            success = false;
            Logger.logError(e);
            prodMessage("SAVE_FAILURE");
        } catch (DataHandleException e) {
            conn.rollback();
            throw e;
        } finally {
            DBManager.closeDBStatement(cStmt);
            conn.setAutoCommit(autoCommit);
        }
        return success;
    }
    public File exportFile() throws DataTransException {
        File file = null;
        try {
            SQLModel sqlModel = sqlProducer.getPageQueryModel();
            TransRule rule = new TransRule();
            rule.setDataSource(sqlModel);
            rule.setSourceConn(conn);

            String fileName = "资产折旧年限报表.xls";
            String filePath = WorldConstant.USER_HOME;
            filePath += WorldConstant.FILE_SEPARATOR;
            filePath += fileName;
            rule.setTarFile(filePath);

            DataRange range = new DataRange();
            rule.setDataRange(range);

            Map fieldMap = new HashMap();
            fieldMap.put("ORGNIZATION_NAME", "公司名称");
            fieldMap.put("FA_CAT_NAME_2", "资产分类名称");
            fieldMap.put("STANDARDS_YEARS", "标准折旧年限");
            fieldMap.put("NEW_YEARS", "新折旧年限");
            rule.setFieldMap(fieldMap);

            CustomTransData custData = new CustomTransData();
            custData.setReportTitle(fileName);
            custData.setReportPerson(sfUser.getUsername());
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
}
