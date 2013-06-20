package com.sino.soa.mis.srv.transTaskInfo.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;
import com.sino.config.SinoConfig;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.soa.mis.srv.transTaskInfo.dto.PageInquiryTaskinfoDTO;

/**
 * <p>Title: SrvTaskinfoModel</p>
 * <p>Description:程序自动生成SQL构造器“SrvTaskinfoModel”</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author wangzp
 * function:查询项目任务信息服务(分页) 
 */

public class PageInquiryTaskinfoModel extends BaseSQLProducer {

	private SfUserDTO sfUser = null;

	/**
	 * 功能：SRV_TASKINFO 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter SrvTaskinfoDTO 本次操作的数据
	 */
	public PageInquiryTaskinfoModel(SfUserDTO userAccount, PageInquiryTaskinfoDTO dtoParameter) {
		super(userAccount, dtoParameter);
		sfUser = userAccount;
	}

	/**
	 * 功能：框架自动生成SRV_TASKINFO数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataCreateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
			List sqlArgs = new ArrayList();
			PageInquiryTaskinfoDTO srvTaskinfo = (PageInquiryTaskinfoDTO)dtoParameter;
			String sqlStr = "INSERT INTO "
				+ " ZTE_PA_PROJECT_TASK_INFO("
				+ " ORG_ID,"
				+ " ORG_NAME,"
				+ " PROJECT_ID,"
				+ " SEGMENT1,"
				+ " TASK_ID,"
				+ " TASK_NUMBER,"
				+ " TASK_NAME,"
				+ " DESCRIPTION,"
				+ " WBS_LEVEL,"
				+ " TASK_MANAGER,"
				+ " PARENT_TASK_ID,"
				+ " PARENT_TASK_NUM," 
				+ " START_DATE,"
				+ " COMPLETION_DATE,"
				+ " SERVICE_TYPE_CODE,"
				+ " CHARGEABLE_FLAG,"
				+ " BILLABLE_FLAG,"
				+ " COST_FLAG,"
				+ " ATTRIBUTE1,"
				+ " ATTRIBUTE2,"
				+ " ATTRIBUTE3,"
				+ " ATTRIBUTE4,"
				+ " ATTRIBUTE5,"
				+ " ATTRIBUTE6,"
				+ " PM_PRODUCT_CODE,"
				+ " PM_TASK_REFERENCE,"
				+ " CREATION_DATE,"
				+ " LAST_UPDATE_DATE"
				+ ") VALUES ("
				+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
			sqlArgs.add(Integer.parseInt(srvTaskinfo.getOrgId()));
			sqlArgs.add(srvTaskinfo.getOrgName());
			sqlArgs.add(Integer.parseInt(srvTaskinfo.getProjectId()));
			sqlArgs.add(srvTaskinfo.getSegment1());
			sqlArgs.add(Integer.parseInt(srvTaskinfo.getTaskId()));
			sqlArgs.add(srvTaskinfo.getTaskNumber());
			sqlArgs.add(srvTaskinfo.getTaskName());
			sqlArgs.add(srvTaskinfo.getDescription());
			sqlArgs.add(Integer.parseInt(srvTaskinfo.getWbsLevel()));
			sqlArgs.add(srvTaskinfo.getTaskManager());
			if(!srvTaskinfo.getParentTaskId().equals("")){
				sqlArgs.add(Integer.parseInt(srvTaskinfo.getParentTaskId()));
			}else{
				sqlArgs.add(0);
			}
			sqlArgs.add(srvTaskinfo.getParentTaskNum());
			sqlArgs.add(srvTaskinfo.getStartDate());
			sqlArgs.add(srvTaskinfo.getCompletionDate());
			sqlArgs.add(srvTaskinfo.getServiceTypeCode());
			sqlArgs.add(srvTaskinfo.getChargeableFlag());
			sqlArgs.add(srvTaskinfo.getBillableFlag());
			sqlArgs.add(srvTaskinfo.getCostFlag());
			sqlArgs.add(srvTaskinfo.getAttribute1());
			sqlArgs.add(srvTaskinfo.getAttribute2());
			sqlArgs.add(srvTaskinfo.getAttribute3());
			sqlArgs.add(srvTaskinfo.getAttribute4());
			sqlArgs.add(srvTaskinfo.getAttribute5());
			sqlArgs.add(srvTaskinfo.getAttribute6());
			sqlArgs.add(srvTaskinfo.getPmProductCode());
			sqlArgs.add(srvTaskinfo.getPmTaskReference());
			sqlArgs.add(srvTaskinfo.getCreationDate());
			sqlArgs.add(srvTaskinfo.getLastUpdateDate());
			
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);

		return sqlModel;
	}

	/**
	 * 功能：框架自动生成SRV_TASKINFO数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据更新用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataUpdateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
			List sqlArgs = new ArrayList();
			PageInquiryTaskinfoDTO srvTaskinfo = (PageInquiryTaskinfoDTO)dtoParameter;
			String sqlStr = "UPDATE ZTE_PA_PROJECT_TASK_INFO"
				+ " SET"
				+ " ORG_ID = ?,"
				+ " ORG_NAME = ?,"
				+ " PROJECT_ID = ?,"
				+ " TASK_ID = ?,"
				+ " TASK_NAME = ?,"
				+ " DESCRIPTION = ?,"
				+ " WBS_LEVEL = ?,"
				+ " TASK_MANAGER = ?,"
				+ " PARENT_TASK_ID = ?,"
				+ " PARENT_TASK_NUM = ?,"
				
				+ " START_DATE = ?,"
				+ " COMPLETION_DATE = ?,"
				+ " SERVICE_TYPE_CODE = ?,"
				+ " CHARGEABLE_FLAG = ?,"
				+ " BILLABLE_FLAG = ?,"
				+ " COST_FLAG = ?,"
				+ " ATTRIBUTE1 = ?,"
				+ " ATTRIBUTE2 = ?,"
				+ " ATTRIBUTE3 = ?,"
				+ " ATTRIBUTE4 = ?,"
				+ " ATTRIBUTE5 = ?,"
				+ " ATTRIBUTE6 = ?,"
				+ " PM_PRODUCT_CODE = ?,"
				+ " PM_TASK_REFERENCE = ?,"
				+ " CREATION_DATE = ?,"
				+ " LAST_UPDATE_DATE = ? \n"
				+ " WHERE \n"
				+ " TASK_NUMBER = ?"
				+ " AND  SEGMENT1 = ?"
				;
			sqlArgs.add(Integer.parseInt(srvTaskinfo.getOrgId()));
			sqlArgs.add(srvTaskinfo.getOrgName());
			sqlArgs.add(Integer.parseInt(srvTaskinfo.getProjectId()));
			sqlArgs.add(Integer.parseInt(srvTaskinfo.getTaskId()));
			sqlArgs.add(srvTaskinfo.getTaskName());
			sqlArgs.add(srvTaskinfo.getDescription());
			sqlArgs.add(Integer.parseInt(srvTaskinfo.getWbsLevel()));
			sqlArgs.add(srvTaskinfo.getTaskManager());
			if(!srvTaskinfo.getParentTaskId().equals("")){
				sqlArgs.add(Integer.parseInt(srvTaskinfo.getParentTaskId()));
			}else{
				sqlArgs.add(0);
			}
			sqlArgs.add(srvTaskinfo.getParentTaskNum());
			
			sqlArgs.add(srvTaskinfo.getStartDate());
			sqlArgs.add(srvTaskinfo.getCompletionDate());
			sqlArgs.add(srvTaskinfo.getServiceTypeCode());
			sqlArgs.add(srvTaskinfo.getChargeableFlag());
			sqlArgs.add(srvTaskinfo.getBillableFlag());
			sqlArgs.add(srvTaskinfo.getCostFlag());
			sqlArgs.add(srvTaskinfo.getAttribute1());
			sqlArgs.add(srvTaskinfo.getAttribute2());
			sqlArgs.add(srvTaskinfo.getAttribute3());
			sqlArgs.add(srvTaskinfo.getAttribute4());
			sqlArgs.add(srvTaskinfo.getAttribute5());
			sqlArgs.add(srvTaskinfo.getAttribute6());
			sqlArgs.add(srvTaskinfo.getPmProductCode());
			sqlArgs.add(srvTaskinfo.getPmTaskReference());
			sqlArgs.add(srvTaskinfo.getCreationDate());
			sqlArgs.add(srvTaskinfo.getLastUpdateDate());
			sqlArgs.add(srvTaskinfo.getTaskNumber());
			sqlArgs.add(srvTaskinfo.getSegment1());
		
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成SRV_TASKINFO数据删除SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		PageInquiryTaskinfoDTO srvTaskinfo = (PageInquiryTaskinfoDTO)dtoParameter;
		String sqlStr = "DELETE FROM ZTE_PA_PROJECT_TASK_INFO"	;
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成SRV_TASKINFO数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		PageInquiryTaskinfoDTO srvTaskinfo = (PageInquiryTaskinfoDTO)dtoParameter;
		String sqlStr = "SELECT "
			+ " ORG_ID,"
			+ " ORG_NAME,"
			+ " PROJECT_ID,"
			+ " SEGMENT1,"
			+ " TASK_ID,"
			+ " TASK_NUMBER,"
			+ " TASK_NAME,"
			+ " DESCRIPTION,"
			+ " WBS_LEVEL,"
			+ " TASK_MANAGER,"
			+ " PARENT_TASK_ID,"
			+ " PARENT_TASK_NUM,"
			+ " START_DATE,"
			+ " COMPLETION_DATE,"
			+ " SERVICE_TYPE_CODE,"
			+ " CHARGEABLE_FLAG,"
			+ " BILLABLE_FLAG,"
			+ " COST_FLAG,"
			+ " ATTRIBUTE1,"
			+ " ATTRIBUTE2,"
			+ " ATTRIBUTE3,"
			+ " ATTRIBUTE4,"
			+ " ATTRIBUTE5,"
			+ " ATTRIBUTE6,"
			+ " PM_PRODUCT_CODE,"
			+ " PM_TASK_REFERENCE,"
			+ " CREATION_DATE,"
			+ " LAST_UPDATE_DATE"
			+ " WHERE"
			+ " ROWNUM = 1";
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成SRV_TASKINFO多条数据信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回多条数据信息查询用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getMuxDataModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();

			List sqlArgs = new ArrayList();
			PageInquiryTaskinfoDTO srvTaskinfo = (PageInquiryTaskinfoDTO)dtoParameter;
			String sqlStr = "SELECT "
				+ " ORG_ID,"
				+ " ORG_NAME,"
				+ " PROJECT_ID,"
				+ " SEGMENT1,"
				+ " TASK_ID,"
				+ " TASK_NUMBER,"
				+ " TASK_NAME,"
				+ " DESCRIPTION,"
				+ " WBS_LEVEL,"
				+ " TASK_MANAGER,"
				+ " PARENT_TASK_ID,"
				+ " PARENT_TASK_NUMBER,"
				+ " START_DATE,"
				+ " COMPLETION_DATE,"
				+ " SERVICE_TYPE_CODE,"
				+ " CHARGEABLE_FLAG,"
				+ " BILLABLE_FLAG,"
				+ " COST_FLAG,"
				+ " ATTRIBUTE1,"
				+ " ATTRIBUTE2,"
				+ " ATTRIBUTE3,"
				+ " ATTRIBUTE4,"
				+ " ATTRIBUTE5,"
				+ " ATTRIBUTE6,"
				+ " PM_PRODUCT_CODE,"
				+ " PM_TASK_REFERENCE,"
				+ " CREATION_DATE,"
				+ " LAST_UPDATE_DATE"
				+ " FROM"
				+ " ZTE_PA_PROJECT_TASK_INFO"
				+ " WHERE"
				+ " (? IS NULL OR ORG_ID LIKE ?)"
				+ " AND (? IS NULL OR ORG_NAME LIKE ?)"
				+ " AND (? IS NULL OR PROJECT_ID LIKE ?)"
				+ " AND (? IS NULL OR SEGMENT1 LIKE ?)"
				+ " AND (? IS NULL OR TASK_ID LIKE ?)"
				+ " AND (? IS NULL OR TASK_NUMBER LIKE ?)"
				+ " AND (? IS NULL OR TASK_NAME LIKE ?)"
				+ " AND (? IS NULL OR DESCRIPTION LIKE ?)"
				+ " AND (? IS NULL OR WBS_LEVEL LIKE ?)"
				+ " AND (? IS NULL OR TASK_MANAGER LIKE ?)"
				+ " AND (? IS NULL OR PARENT_TASK_ID LIKE ?)"
				+ " AND (? IS NULL OR PARENT_TASK_NUM LIKE ?)"
				+ " AND (? IS NULL OR START_DATE LIKE ?)"
				+ " AND (? IS NULL OR COMPLETION_DATE LIKE ?)"
				+ " AND (? IS NULL OR SERVICE_TYPE_CODE LIKE ?)"
				+ " AND (? IS NULL OR CHARGEABLE_FLAG LIKE ?)"
				+ " AND (? IS NULL OR BILLABLE_FLAG LIKE ?)"
				+ " AND (? IS NULL OR COST_FLAG LIKE ?)"
				+ " AND (? IS NULL OR ATTRIBUTE1 LIKE ?)"
				+ " AND (? IS NULL OR ATTRIBUTE2 LIKE ?)"
				+ " AND (? IS NULL OR ATTRIBUTE3 LIKE ?)"
				+ " AND (? IS NULL OR ATTRIBUTE4 LIKE ?)"
				+ " AND (? IS NULL OR ATTRIBUTE5 LIKE ?)"
				+ " AND (? IS NULL OR ATTRIBUTE6 LIKE ?)"
				+ " AND (? IS NULL OR PM_PRODUCT_CODE LIKE ?)"
				+ " AND (? IS NULL OR PM_TASK_REFERENCE LIKE ?)"
				+ " AND (? IS NULL OR CREATION_DATE LIKE ?)"
				+ " AND (? IS NULL OR LAST_UPDATE_DATE LIKE ?)";
			sqlArgs.add(srvTaskinfo.getOrgId());
			sqlArgs.add(srvTaskinfo.getOrgId());
			sqlArgs.add(srvTaskinfo.getOrgName());
			sqlArgs.add(srvTaskinfo.getOrgName());
			sqlArgs.add(srvTaskinfo.getProjectId());
			sqlArgs.add(srvTaskinfo.getProjectId());
			sqlArgs.add(srvTaskinfo.getSegment1());
			sqlArgs.add(srvTaskinfo.getSegment1());
			sqlArgs.add(srvTaskinfo.getTaskId());
			sqlArgs.add(srvTaskinfo.getTaskId());
			sqlArgs.add(srvTaskinfo.getTaskNumber());
			sqlArgs.add(srvTaskinfo.getTaskNumber());
			sqlArgs.add(srvTaskinfo.getTaskName());
			sqlArgs.add(srvTaskinfo.getTaskName());
			sqlArgs.add(srvTaskinfo.getDescription());
			sqlArgs.add(srvTaskinfo.getDescription());
			sqlArgs.add(srvTaskinfo.getWbsLevel());
			sqlArgs.add(srvTaskinfo.getWbsLevel());
			sqlArgs.add(srvTaskinfo.getTaskManager());
			sqlArgs.add(srvTaskinfo.getTaskManager());
			sqlArgs.add(srvTaskinfo.getParentTaskId());
			sqlArgs.add(srvTaskinfo.getParentTaskId());
			sqlArgs.add(srvTaskinfo.getParentTaskNum());
			sqlArgs.add(srvTaskinfo.getParentTaskNum());
			sqlArgs.add(srvTaskinfo.getStartDate());
			sqlArgs.add(srvTaskinfo.getStartDate());
			sqlArgs.add(srvTaskinfo.getCompletionDate());
			sqlArgs.add(srvTaskinfo.getCompletionDate());
			sqlArgs.add(srvTaskinfo.getServiceTypeCode());
			sqlArgs.add(srvTaskinfo.getServiceTypeCode());
			sqlArgs.add(srvTaskinfo.getChargeableFlag());
			sqlArgs.add(srvTaskinfo.getChargeableFlag());
			sqlArgs.add(srvTaskinfo.getBillableFlag());
			sqlArgs.add(srvTaskinfo.getBillableFlag());
			sqlArgs.add(srvTaskinfo.getCostFlag());
			sqlArgs.add(srvTaskinfo.getCostFlag());
			sqlArgs.add(srvTaskinfo.getAttribute1());
			sqlArgs.add(srvTaskinfo.getAttribute1());
			sqlArgs.add(srvTaskinfo.getAttribute2());
			sqlArgs.add(srvTaskinfo.getAttribute2());
			sqlArgs.add(srvTaskinfo.getAttribute3());
			sqlArgs.add(srvTaskinfo.getAttribute3());
			sqlArgs.add(srvTaskinfo.getAttribute4());
			sqlArgs.add(srvTaskinfo.getAttribute4());
			sqlArgs.add(srvTaskinfo.getAttribute5());
			sqlArgs.add(srvTaskinfo.getAttribute5());
			sqlArgs.add(srvTaskinfo.getAttribute6());
			sqlArgs.add(srvTaskinfo.getAttribute6());
			sqlArgs.add(srvTaskinfo.getPmProductCode());
			sqlArgs.add(srvTaskinfo.getPmProductCode());
			sqlArgs.add(srvTaskinfo.getPmTaskReference());
			sqlArgs.add(srvTaskinfo.getPmTaskReference());
			sqlArgs.add(srvTaskinfo.getCreationDate());
			sqlArgs.add(srvTaskinfo.getCreationDate());
			sqlArgs.add(srvTaskinfo.getLastUpdateDate());
			sqlArgs.add(srvTaskinfo.getLastUpdateDate());
		
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成SRV_TASKINFO页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
			List sqlArgs = new ArrayList();
			PageInquiryTaskinfoDTO srvTaskinfo = (PageInquiryTaskinfoDTO)dtoParameter;
			String sqlStr = "SELECT "
				+ " ORG_ID,"
				+ " ORG_NAME,"
				+ " PROJECT_ID,"
				+ " SEGMENT1,"
				+ " TASK_ID,"
				+ " TASK_NUMBER,"
				+ " TASK_NAME,"
				+ " DESCRIPTION,"
				+ " WBS_LEVEL,"
				+ " TASK_MANAGER,"
				+ " PARENT_TASK_ID,"
				+ " PARENT_TASK_NUM,"
				+ " START_DATE,"
				+ " COMPLETION_DATE,"
				+ " SERVICE_TYPE_CODE,"
				+ " CHARGEABLE_FLAG,"
				+ " BILLABLE_FLAG,"
				+ " COST_FLAG,"
				+ " ATTRIBUTE1,"
				+ " ATTRIBUTE2,"
				+ " ATTRIBUTE3,"
				+ " ATTRIBUTE4,"
				+ " ATTRIBUTE5,"
				+ " ATTRIBUTE6,"
				+ " PM_PRODUCT_CODE,"
				+ " PM_TASK_REFERENCE,"
				+ " CREATION_DATE,"
				+ " LAST_UPDATE_DATE"
				+ " FROM"
				+ " ZTE_PA_PROJECT_TASK_INFO"
				+ " WHERE"
				+ " (? IS NULL OR ORG_ID LIKE ?)"
				+ " AND (? IS NULL OR ORG_NAME LIKE ?)"
				+ " AND (? IS NULL OR PROJECT_ID LIKE ?)"
				+ " AND (? IS NULL OR SEGMENT1 LIKE ?)"
				+ " AND (? IS NULL OR TASK_ID LIKE ?)"
				+ " AND (? IS NULL OR TASK_NUMBER LIKE ?)"
				+ " AND (? IS NULL OR TASK_NAME LIKE ?)"
				+ " AND (? IS NULL OR DESCRIPTION LIKE ?)"
				+ " AND (? IS NULL OR WBS_LEVEL LIKE ?)"
				+ " AND (? IS NULL OR TASK_MANAGER LIKE ?)"
				+ " AND (? IS NULL OR PARENT_TASK_ID LIKE ?)"
				+ " AND (? IS NULL OR PARENT_TASK_NUM LIKE ?)"
				+ " AND (? IS NULL OR START_DATE LIKE ?)"
				+ " AND (? IS NULL OR COMPLETION_DATE LIKE ?)"
				+ " AND (? IS NULL OR SERVICE_TYPE_CODE LIKE ?)"
				+ " AND (? IS NULL OR CHARGEABLE_FLAG LIKE ?)"
				+ " AND (? IS NULL OR BILLABLE_FLAG LIKE ?)"
				+ " AND (? IS NULL OR COST_FLAG LIKE ?)"
				+ " AND (? IS NULL OR ATTRIBUTE1 LIKE ?)"
				+ " AND (? IS NULL OR ATTRIBUTE2 LIKE ?)"
				+ " AND (? IS NULL OR ATTRIBUTE3 LIKE ?)"
				+ " AND (? IS NULL OR ATTRIBUTE4 LIKE ?)"
				+ " AND (? IS NULL OR ATTRIBUTE5 LIKE ?)"
				+ " AND (? IS NULL OR ATTRIBUTE6 LIKE ?)"
				+ " AND (? IS NULL OR PM_PRODUCT_CODE LIKE ?)"
				+ " AND (? IS NULL OR PM_TASK_REFERENCE LIKE ?)"
				+ " AND (? IS NULL OR CREATION_DATE LIKE ?)"
				+ " AND (? IS NULL OR LAST_UPDATE_DATE LIKE ?)";
			sqlArgs.add(srvTaskinfo.getOrgId());
			sqlArgs.add(srvTaskinfo.getOrgId());
			sqlArgs.add(srvTaskinfo.getOrgName());
			sqlArgs.add(srvTaskinfo.getOrgName());
			sqlArgs.add(srvTaskinfo.getProjectId());
			sqlArgs.add(srvTaskinfo.getProjectId());
			sqlArgs.add(srvTaskinfo.getSegment1());
			sqlArgs.add(srvTaskinfo.getSegment1());
			sqlArgs.add(srvTaskinfo.getTaskId());
			sqlArgs.add(srvTaskinfo.getTaskId());
			sqlArgs.add(srvTaskinfo.getTaskNumber());
			sqlArgs.add(srvTaskinfo.getTaskNumber());
			sqlArgs.add(srvTaskinfo.getTaskName());
			sqlArgs.add(srvTaskinfo.getTaskName());
			sqlArgs.add(srvTaskinfo.getDescription());
			sqlArgs.add(srvTaskinfo.getDescription());
			sqlArgs.add(srvTaskinfo.getWbsLevel());
			sqlArgs.add(srvTaskinfo.getWbsLevel());
			sqlArgs.add(srvTaskinfo.getTaskManager());
			sqlArgs.add(srvTaskinfo.getTaskManager());
			sqlArgs.add(srvTaskinfo.getParentTaskId());
			sqlArgs.add(srvTaskinfo.getParentTaskId());
			sqlArgs.add(srvTaskinfo.getParentTaskNum());
			sqlArgs.add(srvTaskinfo.getParentTaskNum());
			sqlArgs.add(srvTaskinfo.getStartDate());
			sqlArgs.add(srvTaskinfo.getStartDate());
			sqlArgs.add(srvTaskinfo.getCompletionDate());
			sqlArgs.add(srvTaskinfo.getCompletionDate());
			sqlArgs.add(srvTaskinfo.getServiceTypeCode());
			sqlArgs.add(srvTaskinfo.getServiceTypeCode());
			sqlArgs.add(srvTaskinfo.getChargeableFlag());
			sqlArgs.add(srvTaskinfo.getChargeableFlag());
			sqlArgs.add(srvTaskinfo.getBillableFlag());
			sqlArgs.add(srvTaskinfo.getBillableFlag());
			sqlArgs.add(srvTaskinfo.getCostFlag());
			sqlArgs.add(srvTaskinfo.getCostFlag());
			sqlArgs.add(srvTaskinfo.getAttribute1());
			sqlArgs.add(srvTaskinfo.getAttribute1());
			sqlArgs.add(srvTaskinfo.getAttribute2());
			sqlArgs.add(srvTaskinfo.getAttribute2());
			sqlArgs.add(srvTaskinfo.getAttribute3());
			sqlArgs.add(srvTaskinfo.getAttribute3());
			sqlArgs.add(srvTaskinfo.getAttribute4());
			sqlArgs.add(srvTaskinfo.getAttribute4());
			sqlArgs.add(srvTaskinfo.getAttribute5());
			sqlArgs.add(srvTaskinfo.getAttribute5());
			sqlArgs.add(srvTaskinfo.getAttribute6());
			sqlArgs.add(srvTaskinfo.getAttribute6());
			sqlArgs.add(srvTaskinfo.getPmProductCode());
			sqlArgs.add(srvTaskinfo.getPmProductCode());
			sqlArgs.add(srvTaskinfo.getPmTaskReference());
			sqlArgs.add(srvTaskinfo.getPmTaskReference());
			sqlArgs.add(srvTaskinfo.getCreationDate());
			sqlArgs.add(srvTaskinfo.getCreationDate());
			sqlArgs.add(srvTaskinfo.getLastUpdateDate());
			sqlArgs.add(srvTaskinfo.getLastUpdateDate());
		
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);

		return sqlModel;
	}
	
	public SQLModel existsProjectTaskModel(String taskNumber , String projectNum){
		
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		PageInquiryTaskinfoDTO srvTaskinfo = (PageInquiryTaskinfoDTO)dtoParameter;
		String sqlStr ="SELECT * FROM ZTE_PA_PROJECT_TASK_INFO ST WHERE ST.TASK_NUMBER = ? AND ST.SEGMENT1= ?";
		sqlArgs.add(taskNumber);
		sqlArgs.add(projectNum);
		sqlModel.setArgs(sqlArgs);
		sqlModel.setSqlStr(sqlStr);
		return sqlModel;
		
	}
	

}