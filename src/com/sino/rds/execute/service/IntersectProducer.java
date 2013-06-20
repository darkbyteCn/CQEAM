package com.sino.rds.execute.service;

import com.sino.base.exception.ReportException;

public interface IntersectProducer {

    /**
     * 功能：生成报表需要的二维数组数据
     *
     * @throws ReportException 出错时抛出报表异常
     */
    public void processIntersectData() throws ReportException;
}