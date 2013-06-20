package com.sino.ams.instrument.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.instrument.dto.AmsInstrumentEamYbChkTaskDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: AmsInstrumentEamYbChkTaskModel</p>
 * <p>Description:仪器仪表检修任务设置</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author V-yushibo
 * @version 1.0
 */
public class AmsInstrumentEamYbChkTaskModel extends AMSSQLProducer {

	/**
     * 功能：仪器仪表检修任务表(EAM) EAM_YB_CHK_TASK 数据库SQL构造层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsInstrumentEamYbChkTaskDTO 本次操作的数据
     */
	public AmsInstrumentEamYbChkTaskModel(BaseUserDTO userAccount, AmsInstrumentEamYbChkTaskDTO dtoParameter) {
		super(userAccount, dtoParameter);
		// TODO Auto-generated constructor stub
	}

	/**
     * 功能：框架自动生成检修任务表(EAM) EAM_YB_CHK_TASK页面翻页查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getPageQueryModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsInstrumentEamYbChkTaskDTO dto = (AmsInstrumentEamYbChkTaskDTO) dtoParameter;
        
        String sqlStr = "";
        	   sqlStr = "SELECT" +
        	   			" EYCT.TASK_ID," +
        	   			" EYCT.TASK_NAME," +
        	   			" EYCT.REMARK," +
        	   			" EYCT.ORGANIZATION_ID," +
        	   			" EYCT.START_DATE," +
        	   			" EYCT.END_DATE," +
        	   			" EYCT.CREATED_BY," +
        	   			" EYCT.CREATION_DATE," +
        	   			" EYCT.LAST_UPDATE_BY," +
        	   			" EYCT.LAST_UPDATE_DATE," +
        	   			" AMS_PUB_PKG.GET_ORGNIZATION_NAME(EYCT.ORGANIZATION_ID) COMPANY" +
        	   			" FROM" +
        	   			" EAM_YB_CHK_TASK EYCT" + 
        	   			" WHERE" +
        	   			" ( " + SyBaseSQLUtil.isNull() + "  OR EYCT.TASK_NAME LIKE ?)";
        
        sqlArgs.add(dto.getTaskName());
        sqlArgs.add(dto.getTaskName());
        
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        
        return sqlModel;
    }
    
    /**
     * 功能：框架自动生成检修表(EAM) EAM_YB_CHK_TASK数据详细信息查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsInstrumentEamYbChkTaskDTO dto = (AmsInstrumentEamYbChkTaskDTO) dtoParameter;
        String sqlStr = "";
	        sqlStr = "SELECT" +
				" EYCT.TASK_ID," +
				" EYCT.TASK_NAME," +
				" EYCT.REMARK," +
				" EYCT.ORGANIZATION_ID," +
				" EYCT.START_DATE," +
				" EYCT.END_DATE," +
				" EYCT.CREATED_BY," +
				" EYCT.CREATION_DATE," +
				" EYCT.LAST_UPDATE_BY," +
				" EYCT.LAST_UPDATE_DATE," +
				" AMS_PUB_PKG.GET_ORGNIZATION_NAME(EYCT.ORGANIZATION_ID) COMPANY" +
				" FROM" +
				" EAM_YB_CHK_TASK EYCT" +
				" WHERE" +
				" EYCT.TASK_ID = ?";
	        
        sqlArgs.add(dto.getTaskId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    
    /**
     * 功能：框架自动生成检修任务表(EAM) EAM_YB_CHK_TASK数据插入SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据插入用SQLModel
     */
    public SQLModel getDataCreateModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        try {
	        AmsInstrumentEamYbChkTaskDTO dto = (AmsInstrumentEamYbChkTaskDTO) dtoParameter;
	        String sqlStr = "INSERT INTO "
	                + " EAM_YB_CHK_TASK("
	                + " TASK_ID,"
	                + " TASK_NAME,"
	                + " REMARK,"
	                + " ORGANIZATION_ID,"
	                + " START_DATE,"
	                + " END_DATE,"
	                + " CREATED_BY,"
	                + " CREATION_DATE"
	                + ") VALUES ("
	                + " NEWID() , ?, ?, ?, ?, ?, ?, GETDATE())";
	
	        sqlArgs.add(dto.getTaskName());
	        sqlArgs.add(dto.getRemark());
	        sqlArgs.add(userAccount.getOrganizationId());  
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getEndDate());
			sqlArgs.add(userAccount.getUserId());

	        sqlModel.setSqlStr(sqlStr);
	        sqlModel.setArgs(sqlArgs);
		} catch (CalendarException e) {
			e.printLog();
		}

        return sqlModel;
    }

    /**
     * 功能：框架自动生成检修任务表(EAM) EAM_YB_CHK_TASK数据更新SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据更新用SQLModel
     */
    public SQLModel getDataUpdateModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsInstrumentEamYbChkTaskDTO dto = (AmsInstrumentEamYbChkTaskDTO) dtoParameter;
        String sqlStr = "UPDATE EAM_YB_CHK_TASK"
                + " SET"
                + " TASK_NAME = ?"
                + " WHERE"
                + " TASK_ID = ?";

        sqlArgs.add(dto.getTaskName());
        sqlArgs.add(dto.getTaskId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    
    /**
     * 功能：检查TASK_NAME是否重复
     * @param objName 任务名称
     * @return SQLModel
     */
    public SQLModel getNameHasBeenModel(String objName) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = " SELECT 1 FROM EAM_YB_CHK_TASK EYCT " +
                " WHERE EYCT.TASK_NAME = ?  ";
        sqlArgs.add(objName);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
