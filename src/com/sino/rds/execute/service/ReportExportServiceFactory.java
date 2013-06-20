package com.sino.rds.execute.service;

import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.ReportException;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.execute.service.impl.CommonExportServiceImpl;
import com.sino.rds.execute.service.impl.IntersectExportServiceImpl;
import com.sino.rds.share.form.ReportDefineFrm;
import com.sino.rds.share.form.SearchParameterFrm;

import java.sql.Connection;

public class ReportExportServiceFactory {

    public static ReportExportService getExportService(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) throws ReportException {
        ReportExportService service = null;
        try {
            ReportDefineFrm reportFrm = constructActualSQL(userAccount, dtoParameter, conn);
            if(reportFrm != null){
                if(reportFrm.isIntersectReport()){
                    service = new IntersectExportServiceImpl(userAccount, dtoParameter, conn);
                } else {
                    service = new CommonExportServiceImpl(userAccount, dtoParameter, conn);
                }
                service.setReportFrm(reportFrm);
            }
        } catch (DataHandleException ex) {
            ex.printLog();
            throw new ReportException(ex);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new ReportException(ex.getMessage());
        }
        return service;
    }

    private static ReportDefineFrm constructActualSQL(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) throws DataHandleException {
        ReportDefineFrm dto = new ReportDefineFrm();
        SearchParameterFrm frm = (SearchParameterFrm) dtoParameter;
        dto.setReportCode(frm.getReportCode());
        dto.setReportId(frm.getReportId());
        dto.setLookUpCode(frm.getLookUpCode());
        dto.setLookUpId(frm.getLookUpId());
        ActualSQLConstructService constructService = new ActualSQLConstructService(userAccount, dto, conn);
        constructService.constructReportActualSQL();
        ReportDefineFrm reportFrm = (ReportDefineFrm) constructService.getDTOParameter();
        if (reportFrm != null) {
            reportFrm.setAct(frm.getAct());
        }
        return reportFrm;
    }
}