package com.sino.ams.newasset.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.model.YlAssetsQueryModel;
import com.sino.base.db.report.xls.SQLReporter;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: SinoEAMS</p>
 * <p>Description: 预龄资产查询</p>
 * <p>Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2007 - 2008</p>
 * <p>Company: 北京思诺搏信息技术有限公司</p>
 * @author 何睿
 * @version 0.1
 *          Date: 2008-7-2
 */
public class YlAssetsQueryDAO extends AMSBaseDAO {
    public YlAssetsQueryDAO(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        this.sqlProducer = new YlAssetsQueryModel(userAccount, dtoParameter);
    }

    public File exportFile() throws DataTransException {
        File file = null;
        try {
            SQLModel sqlModel = sqlProducer.getPageQueryModel();
            SQLReporter reporter = new SQLReporter(sqlModel, conn);


            Map fieldMap = new HashMap();
            fieldMap.put("ORGANIZATION_NAME", "公司名称");
            fieldMap.put("ASSET_NUMBER", "资产编号");
            fieldMap.put("TAG_NUMBER", "资产标签");
            fieldMap.put("ASSETS_DESCRIPTION", "资产名称");
            fieldMap.put("MODEL_NUMBER", "资产型号");
            fieldMap.put("FA_CATEGORY_CODE", "资产类别");
            fieldMap.put("LIFE_IN_YEARS", "折旧年限");
            fieldMap.put("DATE_PLACED_IN_SERVICE", "资产启用日期");
            fieldMap.put("ASSIGNED_TO_NAME", "责任人");

            reporter.setFieldMap(fieldMap);
            reporter.setFileName("预龄资产.xls");
            reporter.setReportUser(userAccount.getUsername());
            reporter.setNeedReportData(true);
            file = reporter.exportFile();
        } catch (SQLModelException ex) {
            ex.printLog();
            throw new DataTransException(ex);
        }
        return file;
    }
}
