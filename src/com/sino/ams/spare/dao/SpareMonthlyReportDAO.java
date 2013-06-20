package com.sino.ams.spare.dao;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.spare.dto.SpareHistoryDTO;
import com.sino.ams.spare.model.SpareMonthlyReportModel;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.datatrans.*;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.WorldConstant;

import java.sql.Connection;
import java.io.File;
import java.util.Map;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2010-2-4
 * Time: 10:24:37
 * To change this template use File | Settings | File Templates.
 */
public class SpareMonthlyReportDAO extends AMSBaseDAO {
      private SfUserDTO sfUser = null;

    public SpareMonthlyReportDAO(SfUserDTO userAccount, SpareHistoryDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        sfUser = userAccount;
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        SpareHistoryDTO dtoPara = (SpareHistoryDTO) dtoParameter;
        super.sqlProducer = new SpareMonthlyReportModel((SfUserDTO) userAccount, dtoPara);
    }

    public File exportFile() throws DataTransException {
        File file = null;
        try {
            SQLModel sqlModel = sqlProducer.getPageQueryModel();
            TransRule rule = new TransRule();
            rule.setDataSource(sqlModel);
            rule.setCalPattern(CalendarConstant.LINE_PATTERN);
            rule.setSourceConn(conn);
            String fileName = "备品备件月度报表.xls";
            String filePath = WorldConstant.USER_HOME;
            filePath += WorldConstant.FILE_SEPARATOR;
            filePath += fileName;
            rule.setTarFile(filePath);
            DataRange range = new DataRange();
            rule.setDataRange(range);
            Map fieldMap = new HashMap();
            fieldMap.put("BARCODE", "ID号");
            fieldMap.put("ITEM_NAME", "设备名称");
            fieldMap.put("ITEM_SPEC", "规格型号");
            fieldMap.put("ITEM_CATEGORY", "设备类型");
            fieldMap.put("SPARE_USAGE", "用途");
            fieldMap.put("VENDOR_NAME", "厂商");
            fieldMap.put("TOTAL1_QUANTITY", "本期");
            fieldMap.put("TOTAL2_QUANTITY", "省公司");
            fieldMap.put("TOTAL3_QUANTITY", "大同");
            fieldMap.put("TOTAL4_QUANTITY", "阳泉");
            fieldMap.put("TOTAL5_QUANTITY", "长治");
            fieldMap.put("TOTAL6_QUANTITY", "晋城");
            fieldMap.put("TOTAL7_QUANTITY", "朔州");
            fieldMap.put("TOTAL8_QUANTITY", "忻州");
            fieldMap.put("TOTAL9_QUANTITY", "晋中");
            fieldMap.put("TOTAL10_QUANTITY", "吕梁");
            fieldMap.put("TOTAL11_QUANTITY", "临汾");
            fieldMap.put("TOTAL12_QUANTITY", "运城");
            fieldMap.put("TOTAL13_QUANTITY", "太原");
            rule.setFieldMap(fieldMap);
            CustomTransData custData = new CustomTransData();
            custData.setReportTitle("备品备件月度报表");
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
}
