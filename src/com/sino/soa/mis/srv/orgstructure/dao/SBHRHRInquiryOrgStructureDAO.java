package com.sino.soa.mis.srv.orgstructure.dao;

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
import com.sino.soa.mis.srv.orgstructure.model.SBHRHRInquiryOrgStructureModel;
import com.sino.soa.util.SynLogUtil;
import com.sino.soa.util.dto.SynLogDTO;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-10-16
 * Time: 18:27:00
 * To change this template use File | Settings | File Templates.
 */
public class SBHRHRInquiryOrgStructureDAO extends BaseDAO {

    private SfUserDTO sfUser = null;
    private int errorCount = 0;

    public SBHRHRInquiryOrgStructureDAO(SfUserDTO userAccount, SBHRHRInquiryOrgStructureDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        sfUser = userAccount;
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        SBHRHRInquiryOrgStructureDTO dtoPara = (SBHRHRInquiryOrgStructureDTO) dtoParameter;
        super.sqlProducer = new SBHRHRInquiryOrgStructureModel((SfUserDTO) userAccount, dtoPara);
    }

    public int synOrgStructure(DTOSet ds) {
        SynLogUtil log = new SynLogUtil();
        SQLModel sqlModel = null;
        int count = 0;
        try {
            RowSet rs = getDeptInfo();
            RowSet rs2 = getSinoDeptInfo();
            SBHRHRInquiryOrgStructureModel modelProducer = (SBHRHRInquiryOrgStructureModel) sqlProducer;
            for (int i = 0; i < ds.getSize(); i++) {
                SBHRHRInquiryOrgStructureDTO dto = (SBHRHRInquiryOrgStructureDTO) ds.getDTO(i);
                setDTOParameter(dto);
                if (!isDeptInformation(dto, rs)) {
                    sqlModel = modelProducer.getDataCreateModel();
                } else {
                    sqlModel = modelProducer.getDataUpdateModel();
                }
                if (DBOperator.updateRecord(sqlModel, conn)) {
                    count++;
                }
                SQLModel sqlModel2 = null;
                if (!isSinoDeptInformation(dto, rs2)) {
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
            logDto.setSynType(SrvType.SRV_ORG_STRUCTURE);
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
        SBHRHRInquiryOrgStructureModel modelProducer = (SBHRHRInquiryOrgStructureModel) sqlProducer;
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
      public RowSet getOneDeptInfo(String companyCode,String misdeptcode) {
        SBHRHRInquiryOrgStructureModel modelProducer = (SBHRHRInquiryOrgStructureModel) sqlProducer;
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
    public boolean isDeptInformation(SBHRHRInquiryOrgStructureDTO dto, RowSet rs) {

    /**
     * 检查AMS_MIS_DEPT中是否存在deptCode的部门
     * @param deptCode
     * @param rs
     * @return
     */

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
        SBHRHRInquiryOrgStructureModel modelProducer = (SBHRHRInquiryOrgStructureModel) sqlProducer;
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

    public boolean isSinoDeptInformation(SBHRHRInquiryOrgStructureDTO dto, RowSet rs) {
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