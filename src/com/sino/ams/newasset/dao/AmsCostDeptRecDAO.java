package com.sino.ams.newasset.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.model.AmsCostDeptRecModel;
import com.sino.ams.system.fixing.dto.EtsItemInfoDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.db.datatrans.CustomTransData;
import com.sino.base.db.datatrans.DataRange;
import com.sino.base.db.datatrans.DataTransfer;
import com.sino.base.db.datatrans.TransRule;
import com.sino.base.db.datatrans.TransferFactory;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.DataTransException;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2009-8-26
 * Time: 16:50:29
 * To change this template use File | Settings | File Templates.
 */
public class AmsCostDeptRecDAO extends AMSBaseDAO {

    public AmsCostDeptRecDAO(SfUserDTO userAccount, EtsItemInfoDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        EtsItemInfoDTO dtoPara = (EtsItemInfoDTO) dtoParameter;
        super.sqlProducer = new AmsCostDeptRecModel((SfUserDTO) userAccount, dtoPara);
    }

    public File getExportFile() throws DataTransException {
        AmsCostDeptRecModel modelProducer = (AmsCostDeptRecModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getPageQueryModel();
        String reportTitle = "部门-成本中心已匹配关系";
        String fileName = reportTitle + ".xls";
        TransRule rule = new TransRule();
        rule.setDataSource(sqlModel);
        rule.setSourceConn(conn);
        rule.setCalPattern(LINE_PATTERN);
        String filePath = WorldConstant.USER_HOME;
        filePath += WorldConstant.FILE_SEPARATOR;
        filePath += fileName;
        rule.setTarFile(filePath);
        DataRange range = new DataRange();
        rule.setDataRange(range);
        rule.setFieldMap(getFieldMap());
        CustomTransData custData = new CustomTransData();
        custData.setReportTitle(reportTitle);
        custData.setReportPerson(userAccount.getUsername());
        custData.setNeedReportDate(true);
        rule.setCustData(custData);
        TransferFactory factory = new TransferFactory();
        DataTransfer transfer = factory.getTransfer(rule);
        transfer.transData();
        return (File) transfer.getTransResult();
    }

    private Map getFieldMap() {
        Map fieldMap = new HashMap();
        fieldMap.put("COMPANY", "公司");
        fieldMap.put("COUNTY_CODE", "成本中心代码");
        fieldMap.put("COUNTY_NAME", "成本中心名称");
        fieldMap.put("DEPT_CODE", "部门代码");
        fieldMap.put("DEPT_NAME", "部门名称");
        return fieldMap;
    }

    public void delDTOs(DTOSet dtos) throws DataHandleException {
        if (dtos != null && dtos.getSize() > 0) {
            int dtoCount = dtos.getSize();
            for (int i = 0; i < dtoCount; i++) {
                EtsItemInfoDTO dto = (EtsItemInfoDTO) dtos.getDTO(i);
                delDTO(dto);
            }
        }
    }

    public void delDTO(EtsItemInfoDTO dto) throws DataHandleException {
        boolean hasError = false;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            AmsCostDeptRecModel modelProducer = (AmsCostDeptRecModel) sqlProducer;
            SQLModel sqlModel = modelProducer.deleteCostDeptMatch(dto);
            DBOperator.updateRecord(sqlModel, conn);
            hasError = true;
        } catch (SQLException e) {
            Logger.logError(e);
        } finally {
            try {
                if (hasError) {
                    conn.commit();
                    prodMessage("DELETE_MATCH_SUCCESS");
                } else {
                    conn.rollback();
                    prodMessage("DELETE_MATCH_FAILURE");
//                    message.setIsError(true);
                }
                conn.setAutoCommit(autoCommit);
            }
            catch (SQLException e) {
                Logger.logError(e);
            }
        }

    }
}
