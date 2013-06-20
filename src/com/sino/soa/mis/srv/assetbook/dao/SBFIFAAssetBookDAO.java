package com.sino.soa.mis.srv.assetbook.dao;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.soa.mis.srv.assetbook.dto.SBFIFAAssetBookDTO;
import com.sino.soa.mis.srv.assetbook.model.SBFIFAAssetBookModel;
import com.sino.soa.common.SrvType;
import com.sino.soa.util.SynLogUtil;
import com.sino.soa.util.dto.SynLogDTO;

import java.sql.Connection;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-9-5
 * Time: 20:01:52
 * To change this template use File | Settings | File Templates.
 */
public class SBFIFAAssetBookDAO extends BaseDAO {

    private SfUserDTO sfUser = null;
    private int errorCount = 0;

    public SBFIFAAssetBookDAO(SfUserDTO userAccount, SBFIFAAssetBookDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        sfUser = userAccount;
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        SBFIFAAssetBookDTO dtoPara = (SBFIFAAssetBookDTO) dtoParameter;
        super.sqlProducer = new SBFIFAAssetBookModel((SfUserDTO) userAccount, dtoPara);
    }

    /**
     * 功能：对ou信息的更新是否成功。
     * @param ds 传入的数据
     */
    public int synAssetBook(DTOSet ds) {
        SynLogUtil log = new SynLogUtil();
        SQLModel sqlModel = null;
        int count = 0;
        try {
            RowSet rs = getOuInfom();
            for (int i = 0; i < ds.getSize(); i++) {
                SBFIFAAssetBookDTO dto = (SBFIFAAssetBookDTO) ds.getDTO(i);
                initSQLProducer(sfUser, dto);
                SBFIFAAssetBookModel modelProducer = (SBFIFAAssetBookModel) sqlProducer;
                if (!isEcouInformation(dto.getOrgId(), rs)) {
                    sqlModel = modelProducer.getDataCreateModel();
                } else {
                    sqlModel = modelProducer.getDataUpdateModel();
                }
                if (DBOperator.updateRecord(sqlModel, conn)) {
                    count++;
                }
            }
        } catch (DataHandleException e) {
            errorCount++;
            e.printLog();
            SimpleCalendar s = new SimpleCalendar();
            SynLogDTO logDto = new SynLogDTO();
            logDto.setSynType(SrvType.SRV_BOOK);
            logDto.setSynMsg(e.getMessage());
            logDto.setCreatedBy(sfUser.getUserId());
            try {
                logDto.setCreationDate(s.getCalendarValue());
            } catch (CalendarException e1) {
                e1.printLog();
            }
            try {
                log.synLog(logDto, conn);
            } catch (DataHandleException e1) {
                e1.printLog();
            }
        }
        return count;
    }

    /**
     * 功能：传入的ou参数是否存在数据库ou表中。
     * @param rs 传入的数据
     */
    public boolean isEcouInformation(int ou, RowSet rs) {
        boolean returnFlag = false;
        if (rs == null) {
            return returnFlag;
        }
        Row row = null;
        for (int i = 0; i < rs.getSize(); i++) {
            row = rs.getRow(i);
            try {
                if (ou == Integer.parseInt(row.getStrValue("ORGANIZATION_ID")))
                    returnFlag = true;
            } catch (ContainerException e) {
                e.printLog();
            }
        }
        return returnFlag;

    }

    public RowSet getOuInfom() {
        SBFIFAAssetBookModel modelProducer = (SBFIFAAssetBookModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getEcouInforModel();
        SimpleQuery simp = new SimpleQuery(sqlModel, conn);
        RowSet rs = null;
        try {
            simp.executeQuery();
            if (simp.hasResult()) {
                rs = simp.getSearchResult();
            }

        } catch (QueryException e) {
            e.printLog();
        }
        return rs;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}

}
