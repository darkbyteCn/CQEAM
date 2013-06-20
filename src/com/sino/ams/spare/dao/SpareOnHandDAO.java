package com.sino.ams.spare.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.base.constant.WorldConstant;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.db.datatrans.*;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.ContainerException;
import com.sino.base.data.RowSet;
import com.sino.base.data.Row;
import com.sino.base.util.StrUtil;

import com.sino.framework.dto.BaseUserDTO;
import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.spare.dto.AmsItemTransLDTO;
import com.sino.ams.spare.model.SpareOnHandModel;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: wangzp
 * Date: 2011-12-08
 * To change this template use File | Settings | File Templates.
 */
public class SpareOnHandDAO extends AMSBaseDAO {
     private SfUserDTO sfUser = null;

    public SpareOnHandDAO(SfUserDTO userAccount, AmsItemTransLDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        sfUser = userAccount;
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsItemTransLDTO dtoPara = (AmsItemTransLDTO) dtoParameter;
        super.sqlProducer = new SpareOnHandModel((SfUserDTO) userAccount, dtoPara);
    }

    /**
     * 功能：导出Excel文件。
     *
     * @return File
     * @throws com.sino.base.exception.DataTransException
     *
     */
    public File exportFile(AmsItemTransLDTO dto) throws DataTransException {
        File file = null;
        try {
            SQLModel sqlModel = sqlProducer.getPageQueryModel();
            TransRule rule = new TransRule();
            rule.setDataSource(sqlModel);
            rule.setCalPattern(CalendarConstant.LINE_PATTERN);
            rule.setSourceConn(conn);
            String fileName = "备件库存量统计.xls";
            String filePath = WorldConstant.USER_HOME;
            filePath += WorldConstant.FILE_SEPARATOR;
            filePath += fileName;
            rule.setTarFile(filePath);
            DataRange range = new DataRange();
            rule.setDataRange(range);
            Map fieldMap = new HashMap();
            fieldMap.put("ORGNIZATION_NAME", "公司");
            fieldMap.put("INV_TYPE", "仓库类型");
            fieldMap.put("BARCODE", "ID号");
            fieldMap.put("ITEM_NAME", "设备名称");
            fieldMap.put("ITEM_SPEC", "设备型号");
            fieldMap.put("ITEM_CATEGORY", "设备类型");
            fieldMap.put("VENDOR_NAME", "厂商");
            fieldMap.put("SPARE_USAGE", "用途");
            fieldMap.put("WORKORDER_OBJECT_NAME", "仓库");
            fieldMap.put("QUANTITY", "库存量");
            fieldMap.put("USERABLE_QTY", "可用量");
            rule.setFieldMap(fieldMap);
            CustomTransData custData = new CustomTransData();
            String objectName = dto.getWorkorderObjectName();
            custData.setReportTitle(objectName+"备件库存量"+"("+dto.getVendorName()+")");
            custData.setReportPerson(sfUser.getUsername());
            custData.setNeedReportDate(true);
            rule.setCustData(custData);
            TransferFactory factory = new TransferFactory();
            DataTransfer transfer = factory.getTransfer(rule);
            transfer.transData();
            file = (File) transfer.getTransResult();
        } catch (SQLModelException ex) {
            ex.printLog();
            throw new DataTransException(ex);
        }
        return file;
    }
    
    /**
     * 统计库存总量
     */
    public Map getCount() throws SQLModelException, QueryException, ContainerException {
        Map map = new HashMap();
        String count = "0";
        String count1 = "0";
        SpareOnHandModel onNetModel = (SpareOnHandModel) sqlProducer;
        SQLModel sqlModel = onNetModel.getCount();
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        RowSet rs = simpleQuery.getSearchResult();
        if(simpleQuery.hasResult()){
           Row row = rs.getRow(0);
           count = StrUtil.nullToString(row.getValue("TATOL_COUNT1"));
           count1 = StrUtil.nullToString(row.getValue("TATOL_COUNT2"));
        }
        map.put("TATOL_COUNT1", count);
        map.put("TATOL_COUNT2", count1);
        return map;
    }

    public String getVendorName(String vendorId) throws SQLModelException, QueryException, ContainerException {
        String vendorName = "";
        SpareOnHandModel onNetModel = (SpareOnHandModel) sqlProducer;
        SQLModel sqlModel = onNetModel.getVendorName(vendorId);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        RowSet rs = simpleQuery.getSearchResult();
        if(simpleQuery.hasResult()){
           Row row = rs.getRow(0);
           vendorName = StrUtil.nullToString(row.getValue("VENDOR_NAME"));
        }
        return vendorName;
    }
}
