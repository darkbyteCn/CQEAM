package com.sino.rds.execute.service.impl;

import com.sino.base.exception.ReportException;
import com.sino.rds.execute.dao.DimensionProduceDAO;
import com.sino.rds.share.form.IntersectReportExecuteFrm;

public class FixedAboveProducerImpl extends DefaultProducerImpl {

    public FixedAboveProducerImpl(IntersectReportExecuteFrm executeFrm) {
        super(executeFrm);
    }

    public void processAboveDimensions() throws ReportException {
        DimensionProduceDAO dimensionProducer = new DimensionProduceDAO();
    }

    protected void processLeftDimensions() throws ReportException{

    }
}

