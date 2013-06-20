package com.sino.ams.spare.query.dao;

import com.sino.ams.spare.query.dto.AmsBjQueryDTO;
import com.sino.ams.spare.query.model.AmsBjQueryModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.datatrans.*;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.constant.WorldConstant;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;

import java.sql.Connection;
import java.io.File;
import java.util.Map;
import java.util.HashMap;

/**
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author TOTTI
 *         Date: 2007-11-23
 */
public class AmsBjQueryDAO extends BaseDAO {
    private SfUserDTO SfUser = null;
    private SQLModel sModel = null;
    private AmsBjQueryModel model = null;

    public AmsBjQueryDAO(SfUserDTO userAccount, AmsBjQueryDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        SfUser = userAccount;
        sModel = new SQLModel();
        initSQLProducer(userAccount, dtoParameter);
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsBjQueryDTO dtoPara = (AmsBjQueryDTO) dtoParameter;
        super.sqlProducer = new AmsBjQueryModel((SfUserDTO) userAccount, dtoPara);
         model=(AmsBjQueryModel)sqlProducer;
    }
     public File exportFile() throws DataTransException {
        File file = null;
        try {
            SQLModel sqlModel = sqlProducer.getPageQueryModel();
            TransRule rule = new TransRule();
            rule.setDataSource(sqlModel);
            rule.setSourceConn(conn);

            String fileName = "备件信息统计表.xls";
            String filePath = WorldConstant.USER_HOME;
            filePath += WorldConstant.FILE_SEPARATOR;
            filePath += fileName;
            rule.setTarFile(filePath);

            DataRange range = new DataRange();
            rule.setDataRange(range);
            Map fieldMap = new HashMap();
            fieldMap.put("COMPANY", "所属地市");
            fieldMap.put("ITEM_NAME", "备件名称");
            fieldMap.put("ITEM_SPEC", "规格型号");
            fieldMap.put("BARCODE", "备件条码");
            fieldMap.put("ITEM_CATEGORY_NAME", "分类型号");
            fieldMap.put("WORKORDER_OBJECT_NAME", "所属地址");
            fieldMap.put("VENDOR_NAME", "供应商");
            rule.setFieldMap(fieldMap);

            CustomTransData custData = new CustomTransData();
            custData.setReportTitle(fileName);
            custData.setReportPerson(SfUser.getUsername());
            custData.setNeedReportDate(true);
            rule.setCustData(custData);
            /*rule.setSheetSize(1000);*/
            //设置分页显示
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
}
