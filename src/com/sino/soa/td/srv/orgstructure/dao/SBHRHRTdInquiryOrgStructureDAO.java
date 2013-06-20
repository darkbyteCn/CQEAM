package com.sino.soa.td.srv.orgstructure.dao;

import java.sql.Connection;

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
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.log.Logger;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.soa.common.SrvType;
import com.sino.soa.mis.srv.orgstructure.dto.SBHRHRInquiryOrgStructureDTO;
import com.sino.soa.td.srv.orgstructure.dto.SBHRHRTdInquiryOrgStructureDTO;
import com.sino.soa.td.srv.orgstructure.model.SBHRHRTdInquiryOrgStructureModel;
import com.sino.soa.util.SynLogUtil;
import com.sino.soa.util.dto.SynLogDTO;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-10-16
 * Time: 21:44:15
 * To change this template use File | Settings | File Templates.
 */
public class SBHRHRTdInquiryOrgStructureDAO extends BaseDAO {

    private SfUserDTO sfUser = null;
    private int errorCount = 0;

    public SBHRHRTdInquiryOrgStructureDAO(SfUserDTO userAccount, SBHRHRTdInquiryOrgStructureDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        sfUser = userAccount;
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        SBHRHRTdInquiryOrgStructureDTO dtoPara = (SBHRHRTdInquiryOrgStructureDTO) dtoParameter;
        super.sqlProducer = new SBHRHRTdInquiryOrgStructureModel((SfUserDTO) userAccount, dtoPara);
    }

    public int synOrgStructure(DTOSet ds) {
        SynLogUtil log = new SynLogUtil();
        SQLModel sqlModel = null;
        int count = 0;
        try {
            RowSet rs = getDeptInfo();
            RowSet rs2 = getSinoDeptInfo();
            for (int i = 0; i < ds.getSize(); i++) {
                SBHRHRTdInquiryOrgStructureDTO dto = (SBHRHRTdInquiryOrgStructureDTO) ds.getDTO(i);
                initSQLProducer(sfUser, dto);
                SBHRHRTdInquiryOrgStructureModel modelProducer = (SBHRHRTdInquiryOrgStructureModel) sqlProducer;
                if (!isDeptInformation(dto, rs)) {
                    sqlModel = modelProducer.getDataCreateModel();
                } else {
                    sqlModel = modelProducer.getDataUpdateModel();
                }
                if (DBOperator.updateRecord(sqlModel, conn)) {
                    count++;
                }
                SQLModel sqlModel2 = null;
                if (!isSinoDeptInformation( dto, rs2)) {
                    sqlModel2 = modelProducer.getSinoDeptCreateModel();
                } else {
                    sqlModel2 = modelProducer.getSinoDeptUpdateModel();
                }
                DBOperator.updateRecord(sqlModel2, conn);
            }
        } catch (Throwable e) {
            errorCount++;
            Logger.logError(e);
            SimpleCalendar s = new SimpleCalendar();
            SynLogDTO logDto = new SynLogDTO();
            logDto.setSynType(SrvType.SRV_TD_ORG_STRUCTURE);
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

    public RowSet getDeptInfo() {
        SBHRHRTdInquiryOrgStructureModel modelProducer = (SBHRHRTdInquiryOrgStructureModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getDeptInfoModel();
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

    /**
     * 检查AMS_MIS_DEPT中是否存在deptCode的部门
     * @param deptCode
     * @param rs
     * @return
     */
    public boolean isDeptInformation(SBHRHRTdInquiryOrgStructureDTO dto, RowSet rs) {
        boolean returnFlag = false;
        if (rs == null) {
            return returnFlag;
        }
        Row row = null;
        for (int i = 0; i < rs.getSize(); i++) {
            row = rs.getRow(i);
            try {
                 if (dto.getOrganizationId().equals(row.getStrValue("MIS_DEPT_CODE"))&&dto.getOrganizationCode().equals(row.getStrValue("COMPANY_CODE")))
                    returnFlag = true;
            } catch (ContainerException e) {
                e.printLog();
            }
        }
        return returnFlag;

    }

    public RowSet getSinoDeptInfo() {
        SBHRHRTdInquiryOrgStructureModel modelProducer = (SBHRHRTdInquiryOrgStructureModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getSinoDeptInfoModel();
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

         public RowSet getOneDeptInfo(String companyCode,String misdeptcode) {
        SBHRHRTdInquiryOrgStructureModel modelProducer = (SBHRHRTdInquiryOrgStructureModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getOneDeptInfoModel(companyCode,misdeptcode);
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

    /**
     * 检查SINO_MIS_DEPT中是否存在deptCode的部门
     * @param deptCode
     * @param rs
     * @return
     */
    public boolean isSinoDeptInformation(SBHRHRTdInquiryOrgStructureDTO dto, RowSet rs) {
        boolean returnFlag = false;
        if (rs == null) {
            return returnFlag;
        }
          try {
          RowSet rsOneDept = getOneDeptInfo(dto.getOrganizationCode(),dto.getOrganizationId());
                   if (rsOneDept == null) {
            return returnFlag;
        }
                String eamDeptcode=rsOneDept.getRow(0).getStrValue("DEPT_CODE");
        Row row = null;
        for (int i = 0; i < rs.getSize(); i++) {
            row = rs.getRow(i);

                if (eamDeptcode.equals(row.getStrValue("DEPT_ID")))
                    returnFlag = true;

        }
               } catch (ContainerException e) {
                e.printLog();
            }
        return returnFlag;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}

}