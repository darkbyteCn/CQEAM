package com.sino.rds.execute.helper;

import com.sino.base.exception.ReportException;
import com.sino.rds.share.form.ReportExecuteFrm;

public interface PageHtmlProducer {

    public void setHeadDIVTop(int headDIVTop);

    public void setExecuteFrm(ReportExecuteFrm executeFrm);

    public StringBuilder getUndefinedReport();

    public StringBuilder getParameterHTML() throws ReportException;

    public StringBuilder getReportDataHTML() throws ReportException;

    public StringBuilder getHiddenHTML() throws ReportException;
}