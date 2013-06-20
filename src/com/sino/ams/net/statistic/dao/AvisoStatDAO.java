package com.sino.ams.net.statistic.dao;

import java.io.File;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.net.statistic.dto.AvisoStatDTO;
import com.sino.ams.net.statistic.model.AvisoStatModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.db.DataTransConstant;
import com.sino.base.data.RowSet;
import com.sino.base.db.datatrans.CustomTransData;
import com.sino.base.db.datatrans.DataRange;
import com.sino.base.db.datatrans.DataTransfer;
import com.sino.base.db.datatrans.TransRule;
import com.sino.base.db.datatrans.TransferFactory;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: V-yuanshuai
 * Date: 2007-11-14
 * Time: 15:27:04
 * To change this template use File | Settings | File Templates.
 */
public class AvisoStatDAO extends BaseDAO {

    private SfUserDTO sfUser = null;

    /**
     * 功能：EQUIP_STAT 数据访问层构造函数
     *
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter EquipStatDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
    public AvisoStatDAO(SfUserDTO userAccount, AvisoStatDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        sfUser = userAccount;
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     *
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AvisoStatDTO dtoPara = (AvisoStatDTO) dtoParameter;
        super.sqlProducer = new AvisoStatModel((SfUserDTO) userAccount, dtoPara);
    }

    /**
     * 功能:获取时间间隔
     */
    public void setTimeDistance() throws QueryException, ParseException, ContainerException {
        AvisoStatModel modelClass = (AvisoStatModel) sqlProducer;
        SQLModel sqlModel = modelClass.getTimeDistance();
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        RowSet rs = sq.getSearchResult();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateA[] = new Date[7];
        for (int i = 0; i < 7; i++) {
            dateA[i] = sdf.parse(rs.getRow(0).getValue(i).toString());
        }
        int n = 6;
        for (int i = 5; i > -1; i--) {
            if (dateA[6].before(dateA[i])) {
                dateA[i] = dateA[6];
                n -= 1;
            }
        }
        Date firDayOfWeek[] = new Date[n];
        Date lasDayOfWeek[] = new Date[n];
        for (int i = 0; i < n; i++) {
            firDayOfWeek[i] = dateA[i];
            lasDayOfWeek[i] = dateA[i + 1];
        }
        AvisoStatDTO aviso = (AvisoStatDTO) dtoParameter;
        aviso.setFirDayOfWeek(firDayOfWeek);
        aviso.setLasDayOfWeek(lasDayOfWeek);
        aviso.setWeekCount(n);
        setDTOParameter(aviso);
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
            DataTransfer transfer = null;
            SQLModel sqlModel = sqlProducer.getPageQueryModel();
            TransRule rule = new TransRule();
            rule.setDataSource(sqlModel);
            rule.setCalPattern(CalendarConstant.LINE_PATTERN);
            rule.setSourceConn(conn);
            AvisoStatDTO dtoPara = (AvisoStatDTO) dtoParameter;
            String qryType = dtoPara.getQryType();
            String fileName = "";
            Map fieldMap = new HashMap();
            if (qryType.equals(WebAttrConstant.BY_CHECK)) {
                fileName = "巡检工单统计";
                fieldMap.put("ORGANIZATION_NAME", "公司名称");
                fieldMap.put("E1", "1次");
                fieldMap.put("E2", "2次");
                fieldMap.put("E3", "3次");
                fieldMap.put("E4", "4次");
                fieldMap.put("E5", "5次");
                fieldMap.put("EL6", "6次及以上");
                fieldMap.put("CNT", "基站总数");

            } else if (qryType.equals(WebAttrConstant.BY_MONTH)) {
                fileName = "工单业务月度统计";
                fieldMap.put("ORGANIZATION_NAME", "公司名称");
                fieldMap.put("MONTH_NO", "月份");
                fieldMap.put("WEEK_NO", "周次");
                fieldMap.put("T1", "交接");
                fieldMap.put("T2", "巡检");
                fieldMap.put("T3", "维修");
                fieldMap.put("T4", "搬迁");
                fieldMap.put("T5", "调拨");
                fieldMap.put("T6", "报废");
                fieldMap.put("SUM", "合计");
            } else if (qryType.equals(WebAttrConstant.BY_YEAR)) {
                fileName = "工单业务年度统计";
                fieldMap.put("ORGANIZATION_NAME", "公司名称");
                fieldMap.put("T1", "交接");
                fieldMap.put("T2", "巡检");
                fieldMap.put("T3", "维修");
                fieldMap.put("T4", "搬迁");
                fieldMap.put("T5", "调拨");
                fieldMap.put("T6", "报废");
                fieldMap.put("SUM", "合计");
            } else if (qryType.equals(WebAttrConstant.BY_TIME)) {
                fileName = "工单时间点分布统计报表";
                fieldMap.put("ORGANIZATION_NAME", "公司名称");
                fieldMap.put("T1", "8点到18点");
                fieldMap.put("T2", "18点到0点");
                fieldMap.put("T3", "0点到8点");
      //          fieldMap.put("SUM", "合计");
            }

            String filePath = WorldConstant.USER_HOME;
            filePath += WorldConstant.FILE_SEPARATOR;
            filePath += fileName + DataTransConstant.SF_XLS;
            rule.setTarFile(filePath);

            DataRange range = new DataRange();
            rule.setDataRange(range);

//		fieldMap.put("IS_TEMP_ADDR", "是否临时地点");
//			fieldMap.put("CREATION_DATE", "创建日期");
//			fieldMap.put("CREATED_BY", "创建人");
//			fieldMap.put("PROJECT_NAME", "所属工程");

            rule.setFieldMap(fieldMap);

            CustomTransData custData = new CustomTransData();
            custData.setReportTitle(fileName);
            custData.setReportPerson(sfUser.getUsername());
            custData.setNeedReportDate(true);
            rule.setCustData(custData);
            /*rule.setSheetSize(1000);*/
            //设置分页显示
            TransferFactory factory = new TransferFactory();
            transfer = factory.getTransfer(rule);
            transfer.transData();
            file = (File) transfer.getTransResult();
        } catch (SQLModelException ex) {
            ex.printLog();
            throw new DataTransException(ex);
        }
        return file;
    }
}
