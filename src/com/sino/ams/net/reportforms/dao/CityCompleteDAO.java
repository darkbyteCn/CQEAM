package com.sino.ams.net.reportforms.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.net.reportforms.dto.SitusStatisticsDTO;
import com.sino.ams.net.reportforms.model.CityCompleteModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.db.datatrans.CustomTransData;
import com.sino.base.db.datatrans.DataRange;
import com.sino.base.db.datatrans.DataTransfer;
import com.sino.base.db.datatrans.TransRule;
import com.sino.base.db.datatrans.TransferFactory;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: Zyun
 * Date: 2007-11-13
 * Time: 9:30:04
 * To change this template use File | Settings | File Templates.
 */
public class CityCompleteDAO extends BaseDAO {
    private SfUserDTO sfUser = null;

    public CityCompleteDAO(SfUserDTO userAccount, SitusStatisticsDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        sfUser = userAccount;
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        SitusStatisticsDTO dtoPara = (SitusStatisticsDTO) dtoParameter;
        super.sqlProducer = new CityCompleteModel((SfUserDTO) userAccount, dtoPara);
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
            String fileName = "地市完成率统计表.xls";
            String filePath = WorldConstant.USER_HOME;
            filePath += WorldConstant.FILE_SEPARATOR;
            filePath += fileName;
            rule.setTarFile(filePath);
            DataRange range = new DataRange();
            rule.setDataRange(range);

            Map fieldMap = new HashMap();
            fieldMap.put("COMPANY", "公司");
            fieldMap.put("IN_TIME_COUNT", "按时完成工单数");
            fieldMap.put("OVER_TIME_COUNT1", "超时完成工单数");
            fieldMap.put("OVER_TIME_COUNT2", "超时未完成工单数");
            fieldMap.put("NORMAL_PROCESS_COUNT", "正常处理中工单数");
            fieldMap.put("RATE", "完成及时率");

            rule.setFieldMap(fieldMap);

            CustomTransData custData = new CustomTransData();
            custData.setReportTitle("地市完成率统计报表");
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
