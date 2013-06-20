package com.sino.sinoflow.dao;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.data.RowSet;
import com.sino.base.db.datatrans.*;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.sinoflow.dto.ProcedureLookUpDTO;
import com.sino.sinoflow.model.ProcedureLookUpModel;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 *  项目名称：cms
 *  创建时间：下午03:56:41
 *  author：黄召戎
 *  合同进度维护
 *
 */
public class ProcedureDAO extends BaseDAO {
    protected SfUserDTO userAccount = null;
    private Object dataSource = null;

	public ProcedureDAO(BaseUserDTO userAccount, ProcedureLookUpDTO dtoParameter,
			Connection conn) {
		super(userAccount, dtoParameter, conn);
        this.userAccount = (SfUserDTO) userAccount;
	}

	@Override
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		ProcedureLookUpDTO dto = (ProcedureLookUpDTO)dtoParameter;
		sqlProducer = new ProcedureLookUpModel( (SfUserBaseDTO) userAccount, dto);

	}

	 /**
     * 设置导出字段
     * @return Map
     */
    public Map getExportMap() {
        Map fieldMap = new HashMap();
        fieldMap.put("COMPANY_NAME", "公司");
        fieldMap.put("PROCEDURE_NAME", "审批流程名称");
        fieldMap.put("PROCESS_TIME", "平均审批时间");
        return fieldMap;
    }

    public void setDataSource(RowSet rows) {
        this.dataSource = rows;
    }

    public void setDataSource(DTOSet dtos) {
        this.dataSource = dtos;
    }

    public void setDataSource(List forms) {
        try {
            if (forms != null && !forms.isEmpty()) {
                DTOSet dtos = new DTOSet();
                Iterator iterator = forms.iterator();
                while (iterator.hasNext()) {
                    dtos.addDTO((DTO) iterator.next());
                }
                this.dataSource = dtos;
            }
        } catch (DTOException ex) {
            ex.printLog();
        }
    }

    /**
     * 获取导出文件
     *
     * @param reportTitle 导出文件名
     * @return File
     */
    public File getExportFile2(String reportTitle) throws DataTransException, SQLModelException {
        String fileName = reportTitle + ".xls";
        TransRule rule = new TransRule();
        if (dataSource != null) {
            if (dataSource instanceof DTOSet) {
                rule.setDataSource((DTOSet) dataSource);
            } else if (dataSource instanceof RowSet) {
                rule.setDataSource((RowSet) dataSource);
            }
        } else {
            rule.setDataSource(sqlProducer.getExportModel());
            rule.setSourceConn(conn);
        }
        String filePath = WorldConstant.USER_HOME;
        filePath += WorldConstant.FILE_SEPARATOR;
        filePath += fileName;
        rule.setTarFile(filePath);
        DataRange range = new DataRange();
        rule.setDataRange(range);
        Map map = getExportMap();
        if (map == null) {
            throw new DataTransException("导出EXCEL的字段未设置");
        }
        rule.setFieldMap(map);
        CustomTransData custData = new CustomTransData();
        //custData.setReportTitle(reportTitle);
        //custData.setReportPerson(userAccount.getUsername());
        custData.setNeedReportDate(false);
        rule.setCustData(custData);
        rule.setCalPattern(LINE_PATTERN);
        TransferFactory factory = new TransferFactory();
        DataTransfer transfer = factory.getTransfer(rule);
        transfer.transData();
        return (File) transfer.getTransResult();
    }

     /**
     * 获取导出文件
     *
     * @param reportTitle 导出文件名
     * @return File
     */
    public File getExportFile(String reportTitle) throws DataTransException, SQLModelException {
        String fileName = reportTitle + ".xls";
        TransRule rule = new TransRule();
        if (dataSource != null) {
            if (dataSource instanceof DTOSet) {
                rule.setDataSource((DTOSet) dataSource);
            } else if (dataSource instanceof RowSet) {
                rule.setDataSource((RowSet) dataSource);
            }
        } else {
            rule.setDataSource(sqlProducer.getExportModel());
            rule.setSourceConn(conn);
        }
        String filePath = WorldConstant.USER_HOME;
        filePath += WorldConstant.FILE_SEPARATOR;
        filePath += fileName;
        rule.setTarFile(filePath);
        DataRange range = new DataRange();
        rule.setDataRange(range);
        Map map = getExportMap();
        if (map == null) {
            throw new DataTransException("导出EXCEL的字段未设置");
        }
        rule.setFieldMap(map);
        CustomTransData custData = new CustomTransData();
        custData.setReportTitle(reportTitle);
        custData.setReportPerson(userAccount.getUsername());
        custData.setNeedReportDate(false);
        rule.setCustData(custData);
        rule.setCalPattern(LINE_PATTERN);
        TransferFactory factory = new TransferFactory();
        DataTransfer transfer = factory.getTransfer(rule);
        transfer.transData();
        return (File) transfer.getTransResult();
    }
	public Object getDataSource() {
		return dataSource;
	}
}
