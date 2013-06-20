package com.sino.ams.spare.dao;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.spare.dto.AmsItemTransLDTO;
import com.sino.ams.spare.model.SpareAttemperModel;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.ContainerException;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.datatrans.*;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.WorldConstant;
import com.sino.base.data.RowSet;
import com.sino.base.data.Row;

import java.sql.Connection;
import java.io.File;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-12-02
 * Time: 00:00:00
 * To change this template use File | Settings | File Templates.
 */
public class SpareAttemperDAO extends AMSBaseDAO {
       private SfUserDTO sfUser = null;

    public SpareAttemperDAO(SfUserDTO userAccount, AmsItemTransLDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        sfUser = userAccount;
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsItemTransLDTO dtoPara = (AmsItemTransLDTO) dtoParameter;
        super.sqlProducer = new SpareAttemperModel((SfUserDTO) userAccount, dtoPara);
    }

    /**
     * 功能：导出Excel文件。
     *
     * @return File
     * @throws com.sino.base.exception.DataTransException
     *
     */
    public File exportFile() throws DataTransException {
        File file = null;
        try {
            SQLModel sqlModel = sqlProducer.getPageQueryModel();
            TransRule rule = new TransRule();
            rule.setDataSource(sqlModel);
            rule.setCalPattern(CalendarConstant.LINE_PATTERN);
            rule.setSourceConn(conn);
            String fileName = "备件盘点差异报表.xls";
            String filePath = WorldConstant.USER_HOME;
            filePath += WorldConstant.FILE_SEPARATOR;
            filePath += fileName;
            rule.setTarFile(filePath);
            DataRange range = new DataRange();
            rule.setDataRange(range);

            Map fieldMap = new HashMap();
            fieldMap.put("BARCODE", "部件号");
            fieldMap.put("ITEM_NAME", "名称");
            fieldMap.put("ITEM_SPEC", "型号");
            fieldMap.put("SPARE_USAGE", "类型");
            fieldMap.put("QUANTITY", "盘点数量");
            fieldMap.put("KCQTY", "库存数量");

            rule.setFieldMap(fieldMap);

            CustomTransData custData = new CustomTransData();
            custData.setReportTitle("备件盘点差异报表");
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


 public String getPDNo() throws QueryException, ContainerException {
     String transNo = "";
     SQLModel sqlModel = new SQLModel();
     List strArgs = new ArrayList();
     String sqlStr = "SELECT TOP 1 A.TRANS_NO\n" +
             "  FROM (SELECT AITH.TRANS_NO\n" +
             "          FROM AMS_ITEM_TRANS_H AITH\n" +
             "         WHERE AITH.TRANS_TYPE = 'BJPD'\n" +
             "           AND AITH.TRANS_STATUS = 'COMPLETED'\n" +
             "           AND AITH.FROM_ORGANIZATION_ID = ?) A";
     strArgs.add(userAccount.getOrganizationId());
     sqlModel.setArgs(strArgs);
     sqlModel.setSqlStr(sqlStr);
     SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
     simpleQuery.executeQuery();
     if(simpleQuery.hasResult()){
         Row row = simpleQuery.getFirstRow();
         transNo = row.getStrValue("TRANS_NO");
     }
     return transNo;
 }
}
