package com.sino.ams.yj.dao;

import java.sql.Connection;
import java.io.File;
import java.util.Map;
import java.util.HashMap;

import com.sino.base.dto.DTO;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.db.datatrans.*;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.WorldConstant;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.dao.BaseDAO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.yj.dto.AmsYjPlanDTO;
import com.sino.ams.yj.model.AmsYjPlanModel;

/**
 * <p>Title: AmsYjPlanDAO</p>
 * <p>Description:程序自动生成服务程序“AmsYjPlanDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * User: wangzp
 * Date: 2011-09-20
 * Function:应急管理-应急预案体系维护
 */


public class AmsYjPlanDAO extends BaseDAO {

	private SfUserDTO sfUser = null;

	/**
	 * 功能：应急预案体系表 AMS_YJ_PLAN 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsYjPlanDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public AmsYjPlanDAO(SfUserDTO userAccount, AmsYjPlanDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		sfUser = userAccount;
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) { 
		AmsYjPlanDTO dtoPara = (AmsYjPlanDTO)dtoParameter;
		super.sqlProducer = new AmsYjPlanModel((SfUserDTO)userAccount, dtoPara);
	}

     /**
         * 功能：导出Excel文件。
         * @return File
         * @throws com.sino.base.exception.DataTransException
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

                String fileName = "应急预案体系信息.xls";
                String filePath = WorldConstant.USER_HOME;
                filePath += WorldConstant.FILE_SEPARATOR;
                filePath += fileName;
                rule.setTarFile(filePath);

                DataRange range = new DataRange();
                rule.setDataRange(range);

                Map fieldMap = new HashMap();
                fieldMap.put("ORGANIZATION_NAME", "公司名称");
                fieldMap.put("PLAN_ID", "序号");
                fieldMap.put("PLAN_NAME", "预案名称");
                fieldMap.put("PLAN_LEVEL", "预案级别");
                fieldMap.put("PRO_CITY", "省或地市");
                fieldMap.put("PLAN_NO", "预案编号");
                fieldMap.put("PLAN_TYPE", "预案类别");
                fieldMap.put("PRINT_DATE", "印发时间");
                fieldMap.put("KNOW_POST", "知晓范围(职位/岗位)");
                fieldMap.put("QUANTITY", "知晓人的数量");
                fieldMap.put("LEADER_POST", "预案启动决策人的岗位/职位");
                fieldMap.put("IS_DRILL", "该预案是否演练过");
                fieldMap.put("REMARK", "备注");

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

    public File exportPlan() throws DataTransException {
        File file = null;

        DataTransfer transfer = null;
        SQLModel sqlModel = ((AmsYjPlanModel) sqlProducer).getPlanStat();
        TransRule rule = new TransRule();
        rule.setDataSource(sqlModel);
        rule.setCalPattern(CalendarConstant.LINE_PATTERN);
        rule.setSourceConn(conn);

        String fileName = "应急预案体系统计.xls";
        String filePath = WorldConstant.USER_HOME;
        filePath += WorldConstant.FILE_SEPARATOR;
        filePath += fileName;
        rule.setTarFile(filePath);

        DataRange range = new DataRange();
        rule.setDataRange(range);

        Map fieldMap = new HashMap();
        fieldMap.put("ORGANIZATION_NAME", "公司名称");
        fieldMap.put("ZT", "总体预案");
        fieldMap.put("ZX", "专项预案");
        fieldMap.put("YJ", "应急方案");

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

        return file;
    }
}