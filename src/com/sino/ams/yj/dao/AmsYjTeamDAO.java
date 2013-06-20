package com.sino.ams.yj.dao;


import java.sql.Connection;
import java.io.File;
import java.util.Map;
import java.util.HashMap;

import com.sino.base.dto.DTO;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.db.datatrans.*;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.WorldConstant;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.dao.BaseDAO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.yj.dto.AmsYjTeamDTO;
import com.sino.ams.yj.ensure.model.AmsYjCommunicateEnsureModel;
import com.sino.ams.yj.model.AmsYjTeamModel;
import com.sino.ams.yj.model.AmsYjItemModel;
import com.sino.ams.constant.CustMessageKey;


/**
 * <p>Title: AmsYjTeamDAO</p>
 * <p>Description:程序自动生成服务程序“AmsYjTeamDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * User: wangzp
 * Date: 2011-09-20
 * Function:应急管理-应急保障队伍
 */


public class AmsYjTeamDAO extends BaseDAO {

    private SfUserDTO sfUser = null;

    /**
     * 功能：应急通信保障队伍表 AMS_YJ_TEAM 数据访问层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsYjTeamDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
    public AmsYjTeamDAO(SfUserDTO userAccount, AmsYjTeamDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        sfUser = userAccount;
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsYjTeamDTO dtoPara = (AmsYjTeamDTO) dtoParameter;
        super.sqlProducer = new AmsYjTeamModel((SfUserDTO) userAccount, dtoPara);
    }

    /**
     * 功能：导出Excel文件。
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

            String fileName = "应急保障队伍信息.xls";
            String filePath = WorldConstant.USER_HOME;
            filePath += WorldConstant.FILE_SEPARATOR;
            filePath += fileName;
            rule.setTarFile(filePath);

            DataRange range = new DataRange();
            rule.setDataRange(range);

            Map fieldMap = new HashMap();
            fieldMap.put("ORGANIZATION_NAME", "公司名称");
            fieldMap.put("TEAM_ID", "队伍号");
            fieldMap.put("TEAM_NAME", "队伍名称");
            fieldMap.put("RESPONSIBILITY_USER", "企业责任人");
            fieldMap.put("TEL", "手机");
            fieldMap.put("QUANTITY", "队伍人数");
            fieldMap.put("SITUATION", "队伍基本情况及特点");
            fieldMap.put("CREATE_USER", "创建人");
            fieldMap.put("CREATION_DATE", "创建日期");
            fieldMap.put("LAST_UPDATE_USER", "更新人");
            fieldMap.put("LAST_UPDATE_DATE", "更新日期");
            fieldMap.put("DISABLE_DATE", "失效日期");

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

    /**
     * 导出队伍统计
     * @return SQLModel sqlModel = ((AmsYjTeamModel) sqlProducer).getTeamStat();
     * @throws DataTransException
     */
    public File exportTeam() throws DataTransException {
//        File file = null;
//        DataTransfer transfer = null;
//        SQLModel sqlModel = ((AmsYjTeamModel) sqlProducer).getTeamStat();
//        TransRule rule = new TransRule();
//        rule.setDataSource(sqlModel);
//        rule.setCalPattern(CalendarConstant.LINE_PATTERN);
//        rule.setSourceConn(conn);
//
//        String fileName = "应急保障队伍统计.xls";
//        String filePath = WorldConstant.USER_HOME;
//        filePath += WorldConstant.FILE_SEPARATOR;
//        filePath += fileName;
//        rule.setTarFile(filePath);
//
//        DataRange range = new DataRange();
//        rule.setDataRange(range);
//
//        Map fieldMap = new HashMap();
//        fieldMap.put("ORGANIZATION_NAME", "公司名称");
//        fieldMap.put("TEAM_COUNT", "队伍数");
//        fieldMap.put("USER_COUNT", "队伍人数");
//
//        rule.setFieldMap(fieldMap);
//
//        CustomTransData custData = new CustomTransData();
//        custData.setReportTitle(fileName);
//        custData.setReportPerson(sfUser.getUsername());
//        custData.setNeedReportDate(true);
//        rule.setCustData(custData);
//        /*rule.setSheetSize(1000);*/
//        //设置分页显示
//        TransferFactory factory = new TransferFactory();
//        transfer = factory.getTransfer(rule);
//        transfer.transData();
//        file = (File) transfer.getTransResult();
//
//        return file;
        
        AmsYjTeamModel modelProducer = (AmsYjTeamModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getTeamStat();
        String reportTitle = "应急保障队伍统计";
        String fileName = reportTitle + ".xls";
        TransRule rule = new TransRule();
        rule.setDataSource(sqlModel);
        rule.setSourceConn(conn);
        String filePath = WorldConstant.USER_HOME;
        filePath += WorldConstant.FILE_SEPARATOR;
        filePath += fileName;
        rule.setTarFile(filePath);
        DataRange range = new DataRange();
        rule.setDataRange(range);
        rule.setFieldMap(getFieldMap());
        CustomTransData custData = new CustomTransData();
        custData.setReportTitle(reportTitle);
        custData.setReportPerson(sfUser.getUsername());
        custData.setNeedReportDate(true);
        rule.setCustData(custData);
        rule.setCalPattern(LINE_PATTERN);
        TransferFactory factory = new TransferFactory();
        DataTransfer transfer = factory.getTransfer(rule);
        transfer.transData();
        return (File) transfer.getTransResult();
        
    }

    /**
     * 统计栏目
     * @return
     */
    private Map getFieldMap() {
        Map fieldMap = new HashMap();  
        fieldMap.put("ORGANIZATION_NAME", "公司名称");
        fieldMap.put("TEAM_COUNT", "队伍数");
        fieldMap.put("USER_COUNT", "队伍人数");
        return fieldMap;
    }
    
    
    /**
     * 功能：生效选定队伍
     * @param teamId String
     */
    public void enableItem(String teamId) {
        try {
            AmsYjTeamModel a = (AmsYjTeamModel) sqlProducer;
            SQLModel sModel = a.getEnableModel(teamId);
            DBOperator.updateRecord(sModel, conn);
            prodMessage(CustMessageKey.ENABLE_SUCCESS);
            getMessage().addParameterValue("队伍");
        } catch (DataHandleException ex) {
            ex.printLog();
            prodMessage(CustMessageKey.ENABLE_FAILURE);
            getMessage().addParameterValue("队伍");
        }
    }

    public boolean doVerify(String teamName) throws QueryException {
        boolean success = false;
        AmsYjTeamModel aModel = (AmsYjTeamModel) sqlProducer;
        SQLModel sqlModel = aModel.doVerify(teamName);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        success = sq.hasResult();
        return success;
   }
}