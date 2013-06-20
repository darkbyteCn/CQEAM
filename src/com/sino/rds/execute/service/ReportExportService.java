package com.sino.rds.execute.service;

import com.sino.base.exception.ReportException;
import com.sino.rds.share.form.ReportDefineFrm;

import java.io.File;

public interface ReportExportService {

    public void setReportFrm(ReportDefineFrm reportFrm);

    public File getExportReport() throws ReportException;
}