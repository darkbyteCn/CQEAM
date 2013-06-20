package com.sino.sinoflow.user.dao;


import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.base.constant.WorldConstant;
import com.sino.base.db.datatrans.*;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.SQLModelException;
import com.sino.ams.prematch.model.AmsPaAssetsModel;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;
import com.sino.sinoflow.user.dto.SfUserChgLogDTO;
import com.sino.sinoflow.user.model.SfUserChgLogModel;


/**
 * <p>Title: SfGroupDAO</p>
 * <p>Description:程序自动生成服务程序“SfGroupDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author mshtang
 * @version 1.0
 */


public class SfUserChgLogDAO extends BaseDAO {
    SfUserChgLogDTO sfUserChgLogDTO = new SfUserChgLogDTO();
    SfUserBaseDTO userAccount;

    /**
     * 功能：SF_GROUP 数据访问层构造函数
     *
     * @param userAccount  SfUserBaseDTO 代表本系统的最终操作用户对象
     * @param dtoParameter SfGroupDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
    public SfUserChgLogDAO(SfUserBaseDTO userAccount, SfUserChgLogDTO dtoParameter, Connection conn) {

        super(userAccount, dtoParameter, conn);
        this.initSQLProducer(userAccount, dtoParameter);
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     *
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        this.userAccount = (SfUserBaseDTO) userAccount;
        sfUserChgLogDTO = (SfUserChgLogDTO) dtoParameter;
        super.sqlProducer = new SfUserChgLogModel((SfUserBaseDTO) userAccount, sfUserChgLogDTO);
    }

    public File getExportFile() throws DataTransException {
        File file = null;
        try {
            SfUserChgLogModel modelProducer = (SfUserChgLogModel) sqlProducer;
            SQLModel sqlModel = modelProducer.getPageQueryModel();
            String reportTitle = "用户信息变更记录";
            String fileName = reportTitle + ".xls";
            String filePath = WorldConstant.USER_HOME;
            filePath += WorldConstant.FILE_SEPARATOR;
            filePath += fileName;
            TransRule rule = new TransRule();
            rule.setDataSource(sqlModel);
            rule.setSourceConn(conn);
            rule.setTarFile(filePath);
            DataRange range = new DataRange();
            rule.setDataRange(range);
            rule.setFieldMap(getFieldMap());
            CustomTransData custData = new CustomTransData();
            custData.setReportTitle(reportTitle);
            custData.setReportPerson(userAccount.getUsername());
            custData.setNeedReportDate(true);
            rule.setCustData(custData);
            rule.setCalPattern(LINE_PATTERN);
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
     * 功能：获取用户变更记录清单导出字段映射
     *
     * @return Map
     */
    private Map getFieldMap() {
        Map fieldMap = new HashMap();
        fieldMap.put("USER_ID", "用户ID");
        fieldMap.put("USER_NAME", "用户名称");
        fieldMap.put("CHG_TYPE", "变更类型");
        fieldMap.put("OPERATOR", "经办人员ID");
        fieldMap.put("OPERATOR_NAME", "经办人员");
        fieldMap.put("OPERATOR_DATE", "经办日期");
//        fieldMap.put("LOG_FROM", "变更前");
//        fieldMap.put("LOG_TO", "变更后");
        fieldMap.put("REMARK", "说明");
        return fieldMap;
    }
}
