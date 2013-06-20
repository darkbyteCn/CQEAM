package com.sino.ams.newasset.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;

import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;
import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.dto.AmsMisTagChgDTO;
import com.sino.ams.newasset.model.NewTagProduceModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.soa.mis.srv.assetTagNumber.dto.SBFIFAInquiryAssetTagNumberDTO;
import com.sino.soa.mis.srv.assetTagNumber.srv.SBFIFAInquiryAssetTagNumberSrv;
import com.sino.soa.td.srv.assetTagNumber.srv.SBFIFATdInquiryAssetTagNumberSrv;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */
public class NewTagProduceDAO extends AMSBaseDAO {

    public NewTagProduceDAO(SfUserDTO userAccount, AmsMisTagChgDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    /**
     * 功能：SQL生成器baseSQLProducer的初始化。具体的DAO继承时初始化具体的SQL生成器。
     *
     * @param userAccount  BaseUserDTO
     * @param dtoParameter DTO
     * @todo Implement this com.sino.framework.dao.BaseDAO method
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        SfUserDTO user = (SfUserDTO) userAccount;
        AmsMisTagChgDTO dto = (AmsMisTagChgDTO) dtoParameter;
        sqlProducer = new NewTagProduceModel(user, dto);
    }

    public void produceNewTagNumber(DTOSet dtos) throws DataHandleException {
        boolean operateResult = false;
        boolean autoCommit = true;
        DataHandleException error = null;
        CallableStatement cst = null;
        try {
            int dataCount = dtos.getSize();
            AmsMisTagChgDTO dto = null;
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            String sqlStr = "BEGIN ? := AMS_BARCODE_PKG.GETASSETBARCODE(?); END;";
            cst = conn.prepareCall(sqlStr);
            SQLModel sqlModel = null;
            NewTagProduceModel modelProducer = (NewTagProduceModel) sqlProducer;
            for (int i = 0; i < dataCount; i++) {
                dto = (AmsMisTagChgDTO) dtos.getDTO(i);
                setDTOParameter(dto);
                int index = 1;
                cst.registerOutParameter(index++, Types.VARCHAR);
                cst.setString(index++, userAccount.getCompanyCode());
                cst.execute();
                dto.setTagNumberTo(cst.getString(1));
                setDTOParameter(dto);
                createData();
                sqlModel = modelProducer.getTransLineUpdateModel();
                DBOperator.updateRecord(sqlModel, conn);
                dtos.set(i, dto);
            }
            operateResult = true;
        } catch (SQLException ex) {
            Logger.logError(ex);
            error = new DataHandleException(ex);
        } catch (DTOException ex) {
            Logger.logError(ex);
            error = new DataHandleException(ex);
        } finally {
            try {
                if (operateResult) {
                    conn.commit();
                } else {
                    conn.rollback();
                }
                conn.setAutoCommit(autoCommit);
                if (!operateResult) {
                    throw error;
                }
                DBManager.closeDBStatement(cst);
            } catch (SQLException ex) {
                Logger.logError(ex);
                throw new DataHandleException(ex);
            }
        }
    }

    public DTOSet genNewBarcodes(String organizationId, int count) throws QueryException, ContainerException {

        DTOSet ds = new DTOSet();
        String companyCode = "";
        String bookTypeCode = "";
        boolean isTd = false;

        SQLModel sqlModel = getOrganizationModel(organizationId);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        Row row = null;
        if (sq.hasResult()) {
            row = sq.getFirstRow();
            companyCode = row.getStrValue("COMPANY_CODE");
            bookTypeCode = row.getStrValue("BOOK_TYPE_CODE");
            isTd = row.getStrValue("IS_TD").equalsIgnoreCase("Y");
            if (isTd) {
                ds = this.genNewBarcodesOfTD(bookTypeCode, companyCode, count);
            } else {
                ds = this.genNewBarcodes(bookTypeCode, companyCode, count);
            }
        }
        return ds;

    }

    public void logBarcodes(String transId, DTOSet ds) throws DataHandleException {
        boolean operateResult = false;
        boolean autoCommit = true;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            NewTagProduceModel newTagProduceModel = (NewTagProduceModel) sqlProducer;
            SQLModel sqlModel = newTagProduceModel.getTransOrderModel(transId);
            SimpleQuery sq = new SimpleQuery(sqlModel, conn);
            sq.executeQuery();
            RowSet rs = null;
            if (sq.hasResult()) {
                rs = sq.getSearchResult();
                for (int i = 0; i < rs.getSize(); i++) {
                    Row row = rs.getRow(i);
                    String barcode = row.getStrValue("BARCODE");
                    String newBarcode = ((SBFIFAInquiryAssetTagNumberDTO) ds.getDTO(i)).getTagNumber();
                    sqlModel = newTagProduceModel.getUpdateTransLineModel(newBarcode, transId, barcode);
                    DBOperator.updateRecord(sqlModel, conn);
                }
                sqlModel = newTagProduceModel.getInsertTransLineModel(transId);
                DBOperator.updateRecord(sqlModel, conn);
            }
            operateResult = true;
        } catch (QueryException ex) {
            ex.printLog();
            throw new DataHandleException();
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new DataHandleException();
        } finally {
            try {
                if (operateResult) {
                    conn.commit();
                } else {
                    conn.rollback();
                }
                conn.setAutoCommit(autoCommit);
            } catch (SQLException ex) {
                Logger.logError(ex);
            }
        }
    }

    private DTOSet genNewBarcodes(String bookTypeCode, String companyCode, int count) {
        DTOSet ds = new DTOSet();
        SBFIFAInquiryAssetTagNumberSrv srv = new SBFIFAInquiryAssetTagNumberSrv();


        try {
            srv.setBookTypeCode(bookTypeCode);
            srv.setAccount(count);
            srv.setSegment1(companyCode);
            srv.excute();
            if (srv.getReturnMessage().getErrorFlag().equalsIgnoreCase("Y")) {
                ds = srv.getDs();
            } else {
                Logger.logError(srv.getReturnMessage().getErrorMessage());
            }
        } catch (CalendarException e) {
            Logger.logError(e);
        } catch (DTOException e) {
            Logger.logError(e);
        } catch (DatatypeConfigurationException e) {
            Logger.logError(e);
        }

        return ds;

    }

    private DTOSet genNewBarcodesOfTD(String bookTypeCode, String companyCode, int count) {
        DTOSet ds = new DTOSet();
        SBFIFATdInquiryAssetTagNumberSrv srv = new SBFIFATdInquiryAssetTagNumberSrv();


        try {
            srv.setBookTypeCode(bookTypeCode);
            srv.setAccount(count);
            srv.setSegment1(companyCode);
            srv.excute();
            if (srv.getReturnMessage().getErrorFlag().equalsIgnoreCase("Y")) {
                ds = srv.getDs();
            } else {
                Logger.logError(srv.getReturnMessage().getErrorMessage());
            }
        } catch (CalendarException e) {
            Logger.logError(e);
        } catch (DTOException e) {
            Logger.logError(e);
        } catch (DatatypeConfigurationException e) {
            Logger.logError(e);
        }

        return ds;

    }

    private SQLModel getOrganizationModel(String organizationId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT BOOK_TYPE_CODE,COMPANY_CODE,IS_TD  from ETS_OU_CITY_MAP where ORGANIZATION_ID=?";

        sqlArgs.add(Integer.valueOf(organizationId));

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

}
