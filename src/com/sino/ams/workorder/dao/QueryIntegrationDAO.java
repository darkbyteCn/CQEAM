package com.sino.ams.workorder.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dto.EtsWorkorderDTO;
import com.sino.ams.workorder.model.QueryIntegrationModel;
import com.sino.base.constant.WorldConstant;
import com.sino.base.db.datatrans.CustomTransData;
import com.sino.base.db.datatrans.DataRange;
import com.sino.base.db.datatrans.DataTransfer;
import com.sino.base.db.datatrans.TransRule;
import com.sino.base.db.datatrans.TransferFactory;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.dto.BaseUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: Zyun
 * Date: 2008-1-18
 * Time: 10:04:33
 * To change this template use File | Settings | File Templates.
 */
public class QueryIntegrationDAO extends AMSBaseDAO {

    private SfUserDTO sfUser = null;

    /**
     * 功能：设置(EAM) 数据访问层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsRentDeadlineDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
    public QueryIntegrationDAO(SfUserDTO userAccount, EtsWorkorderDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        sfUser = userAccount;
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        EtsWorkorderDTO dtoPara = (EtsWorkorderDTO) dtoParameter;
        super.sqlProducer = new QueryIntegrationModel( userAccount, dtoPara);
    }



    public File getExportFile() throws DataTransException {
        File file = null;
        try {
            SQLModel sqlModel = sqlProducer.getPageQueryModel();
            String reportTitle = "";

            reportTitle = "工单查询";
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
            Map fieldMap = getFieldMap();


            rule.setFieldMap(fieldMap);
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

    private Map getFieldMap() {
        Map fieldMap = new HashMap();
        fieldMap.put("SEGMENT1", "项目编号");
        fieldMap.put("NAME", "项目名称");
        fieldMap.put("ORG_NAME", "公司名称");
        fieldMap.put("WORKORDER_NO", "工单号");
        fieldMap.put("WORKORDER_FLAG_DESC", "工单状态");
        fieldMap.put("ATTRIBUTE4", "所属专业");
        fieldMap.put("WORKORDER_TYPE_DESC", "工单类型");
        fieldMap.put("WORKORDER_OBJECT_CODE", "地点编号");
        fieldMap.put("WORKORDER_OBJECT_NAME", "地点简称");
        fieldMap.put("START_DATE", "开始日期");
        fieldMap.put("IMPLEMENT_DAYS", "实施周期(天)");
        fieldMap.put("IMPLEMENT_USER", "执行人");
        fieldMap.put("UPLOAD_DATE", "实际完成日期");
        fieldMap.put("DIFF", "差异");
        fieldMap.put("OVERTIME", "超时");

        return fieldMap;
    }

}