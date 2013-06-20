package com.sino.ams.match.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.match.model.MatchResultModel;
import com.sino.ams.system.user.dto.EtsOuCityMapDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
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
 * User: Administrator
 * Date: 2008-12-25
 * Time: 9:19:05
 * To change this template use File | Settings | File Templates.
 */
public class MatchResultDAO extends AMSBaseDAO {

    public MatchResultDAO(SfUserDTO userAccount, EtsOuCityMapDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    /**
     * 功能：SQL生成器baseSQLProducer的初始化。具体的DAO继承时初始化具体的SQL生成器。
     *
     * @param userAccount  BaseUserDTO
     * @param dtoParameter DTO
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        SfUserDTO user = (SfUserDTO) userAccount;
        EtsOuCityMapDTO dto = (EtsOuCityMapDTO) dtoParameter;
        sqlProducer = new MatchResultModel(user, dto);
    }


    /**
     * 功能：获取部门盘点设备明细Excel文件
     *
     * @return File
     * @throws com.sino.base.exception.DataTransException
     *
     */
    public File getExportFile() throws DataTransException {
        File file = null;
        try {
            SQLModel sqlModel = sqlProducer.getPageQueryModel();
            String reportTitle = "匹配监控报表";
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
            Map fieldMap = new HashMap();
            fieldMap.put("COMPANY", "公司名称");
            fieldMap.put("MIS_COUNT", "MIS资产数量");
            fieldMap.put("MATCH_COUNT", "已匹配资产数量");
            fieldMap.put("NO_MATCH_COUNT", "未匹配资产数量");
            fieldMap.put("SYN_COUNT", "已同步资产数量");
            fieldMap.put("NO_SYN_COUNT", "未同步资产数量");
            fieldMap.put("MATCH_RATE", "已匹配资产百分比");
            fieldMap.put("SYN_RATE", "已同步资产百分比");
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
}

