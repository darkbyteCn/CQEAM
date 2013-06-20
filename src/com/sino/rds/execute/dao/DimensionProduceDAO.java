package com.sino.rds.execute.dao;

import com.sino.base.exception.ReportException;

import java.util.List;

public class DimensionProduceDAO {


    /**
     * 功能：构造报表上方分组数据
     *
     * @return List<List<String>> 生成报表左方分组数据
     * @throws com.sino.base.exception.ReportException
     *          出错时抛出报表异常
     */
    public List<List<String>> getAboveDimensions() throws ReportException {
        List<List<String>> dimensions = null;
        return dimensions;
    }


    /**
     * 功能：构造报表上方分组数据
     *
     * @return List<List<String>> 生成报表左方分组数据
     * @throws com.sino.base.exception.ReportException
     *          出错时抛出报表异常
     */
    public List<List<String>> getLeftDimensions() throws ReportException {
        List<List<String>> dimensions = null;
        return dimensions;
    }
}