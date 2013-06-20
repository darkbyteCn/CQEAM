package com.sino.rds.execute.service.impl;

import com.sino.base.exception.ReportException;
import com.sino.rds.execute.dao.DimensionProduceDAO;
import com.sino.rds.share.form.IntersectReportExecuteFrm;

public class FixedLeftProducerImpl extends DefaultProducerImpl {

    public FixedLeftProducerImpl(IntersectReportExecuteFrm executeFrm) {
        super(executeFrm);
    }


    public void processAboveDimensions() throws ReportException {
        DimensionProduceDAO dimensionProducer = new DimensionProduceDAO();
    }

    /**
     * 功能:生成报表左边纬度值
     *
     * @throws com.sino.base.exception.ReportException
     *          出错时抛出报表异常
     */
    public void processLeftDimensions() throws ReportException {
        DimensionProduceDAO dimensionProducer = new DimensionProduceDAO();
    }
}