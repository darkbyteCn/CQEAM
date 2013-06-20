package com.sino.rds.execute.service.impl;

import com.sino.base.constant.WorldConstant;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DatabaseException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.service.RDSBaseService;
import com.sino.rds.design.datamodel.dao.DBConnectionDAO;
import com.sino.rds.execute.service.ReportExportService;
import com.sino.rds.share.form.DBConnectionFrm;
import com.sino.rds.share.form.ReportDefineFrm;
import com.sino.rds.share.form.ReportExecuteFrm;
import org.apache.poi.hssf.usermodel.*;

import java.io.File;
import java.sql.Connection;

public abstract class DefaultExportServiceImpl extends RDSBaseService implements ReportExportService {
    protected ReportExecuteFrm executeFrm = null;
    protected ReportDefineFrm reportFrm = null;

    public DefaultExportServiceImpl(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    public void setReportFrm(ReportDefineFrm reportFrm) {
        this.reportFrm = reportFrm;
    }

    public Connection getDataSource() throws DatabaseException {
        Connection dataSource = null;
        try {
            DBConnectionFrm dbcFrm = new DBConnectionFrm();
            dbcFrm.setConnectionId(reportFrm.getConnectionId());
            DBConnectionDAO connectionDAO = new DBConnectionDAO(userAccount, dbcFrm, conn);
            dbcFrm = connectionDAO.searchDTOByPrimaryKey();
            dataSource = dbcFrm.getDBConnection();
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new DatabaseException(ex.getMessage());
        }
        return dataSource;
    }

    protected void freeResource() {
        if (executeFrm != null) {
            executeFrm.freeResource();
        }
        reportFrm = null;
        executeFrm = null;
    }

    protected HSSFCellStyle createDataStyle(HSSFWorkbook wb) {
        HSSFFont font = wb.createFont();
        HSSFCellStyle dataStyle = wb.createCellStyle();
        dataStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        dataStyle.setFont(font);
        dataStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        dataStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        dataStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        dataStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        return dataStyle;
    }

    protected HSSFCellStyle createHeaderStyle(HSSFWorkbook wb) {
        HSSFFont font = wb.createFont();
        HSSFCellStyle dataStyle = wb.createCellStyle();
        dataStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        dataStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        dataStyle.setFont(font);
        dataStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        dataStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        dataStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        dataStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        return dataStyle;
    }


    protected void autoReSizeSheet(HSSFSheet sheet) {
        HSSFRow xlsRow = sheet.getRow(0);
        short cellCount = xlsRow.getLastCellNum();
        for (short i = 0; i < cellCount; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    protected File getReportFile() {
        File file = null;
        String filePath = getExportPath();
        String tmpFileName = reportFrm.getReportName();
        String fileName = tmpFileName + ".xls";
        file = new File(filePath, fileName);
        int i = 1;
        while (file.exists()) {
            fileName = tmpFileName + "_" + i + ".xls";
            file = new File(filePath, fileName);
            i++;
        }
        return file;
    }

    /**
     * 功能：获取文件导出的绝对路径
     *
     * @return 文件导出的绝对路径
     */
    protected String getExportPath() {
        String filePath = WorldConstant.USER_HOME;
        if (StrUtil.isEmpty(filePath)) {
            filePath = WorldConstant.TMP_DIR;
        }
        if (!filePath.endsWith(WorldConstant.FILE_SEPARATOR)) {
            filePath += WorldConstant.FILE_SEPARATOR;
        }
        filePath += "report";
        File file = new File(filePath);
        boolean isRight = true;
        if (!file.exists()) {
            isRight = file.mkdirs();
        }
        if (!isRight) {
            filePath = "";
        }
        return filePath;
    }
}