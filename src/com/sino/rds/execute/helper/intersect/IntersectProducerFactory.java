package com.sino.rds.execute.helper.intersect;

import com.sino.rds.execute.service.IntersectProducer;
import com.sino.rds.execute.service.impl.DefaultProducerImpl;
import com.sino.rds.execute.service.impl.FixedAboveProducerImpl;
import com.sino.rds.execute.service.impl.FixedBothProducerImpl;
import com.sino.rds.execute.service.impl.FixedLeftProducerImpl;
import com.sino.rds.share.constant.RDSDictionaryList;
import com.sino.rds.share.form.IntersectReportExecuteFrm;
import com.sino.rds.share.form.ReportDefineFrm;

public class IntersectProducerFactory {
    private IntersectProducerFactory(){

    }

    public static IntersectProducer getProducer(IntersectReportExecuteFrm executeFrm){
        IntersectProducer producer = null;
        if(executeFrm != null){
            ReportDefineFrm definedReport = executeFrm.getDefinedReport();
            String reportType = definedReport.getReportType();
            if(reportType.equals(RDSDictionaryList.REPORT_TYPE_INTERSECT)){
                producer = new DefaultProducerImpl(executeFrm);
            } else if(reportType.equals(RDSDictionaryList.REPORT_TYPE_FIXED_COLS)){
                producer = new FixedAboveProducerImpl(executeFrm);
            } else if(reportType.equals(RDSDictionaryList.REPORT_TYPE_FIXED_ROWS)){
                producer = new FixedLeftProducerImpl(executeFrm);
            } else if(reportType.equals(RDSDictionaryList.REPORT_TYPE_FIXED_BOTH)){
                producer = new FixedBothProducerImpl(executeFrm);
            }
        }
        return producer;
    }
}
