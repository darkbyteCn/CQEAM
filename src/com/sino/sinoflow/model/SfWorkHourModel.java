package com.sino.sinoflow.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.sinoflow.dto.SfWorkHourDTO;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;


/**
 * <p>Title: SfWorkHourModel</p>
 * <p>Description:程序自动生成SQL构造器“SfWorkHourModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Hing
 * @version 1.0
 */ 


public class SfWorkHourModel extends BaseSQLProducer {

	private SfUserBaseDTO sfUser = null;

	/**
	 * 功能：工时定义 SF_WORK_HOUR 数据库SQL构造层构造函数
	 * @param userAccount SfUserBaseDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter SfWorkHourDTO 本次操作的数据
	 */
	public SfWorkHourModel(SfUserBaseDTO userAccount, SfWorkHourDTO dtoParameter) {
		super(userAccount, dtoParameter);
		sfUser = userAccount;
	}

	/**
	 * 功能：框架自动生成工时定义 SF_WORK_HOUR数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataCreateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
			List sqlArgs = new ArrayList();
			SfWorkHourDTO sfWorkHour = (SfWorkHourDTO)dtoParameter;
			String sqlStr = "INSERT INTO "
				+ " SF_WORK_HOUR("
				+ " WORKING_DATE,"
				+ " WORK_BEGIN_1,"
				+ " WORK_END_1,"
				+ " WORK_BEGIN_2,"
				+ " WORK_END_2,"
				+ " CREATED_BY,"
				+ " CREATION_DATE,"
				+ " LAST_UPDATED_BY,"
				+ " LAST_UPDATE_DATE,"
				+ " WORK_HOUR_NAME"
				+ ") VALUES ("
				+ " ?, ?, ?, ?, ?, ?, GETDATE(), ?, ?,?)";

			sqlArgs.add(sfWorkHour.getWorkingDate());
			sqlArgs.add(sfWorkHour.getWorkBegin1().trim());
			sqlArgs.add(sfWorkHour.getWorkEnd1().trim());
			sqlArgs.add(sfWorkHour.getWorkBegin2().trim());
			sqlArgs.add(sfWorkHour.getWorkEnd2().trim());
			sqlArgs.add(sfUser.getLoginName());
			sqlArgs.add(sfWorkHour.getLastUpdatedBy());
			sqlArgs.add(sfWorkHour.getLastUpdateDate());
			sqlArgs.add(sfWorkHour.getWorkHourName());
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成工时定义 SF_WORK_HOUR数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据更新用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataUpdateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
			List sqlArgs = new ArrayList();
			SfWorkHourDTO sfWorkHour = (SfWorkHourDTO)dtoParameter;
			String sqlStr = "UPDATE SF_WORK_HOUR"
				+ " SET"
				+ " WORK_HOUR_NAME = ?,"
				+ " WORKING_DATE = ?,"
				+ " WORK_BEGIN_1 = ?,"
				+ " WORK_END_1 = ?,"
				+ " WORK_BEGIN_2 = ?,"
				+ " WORK_END_2 = ?,"
				+ " LAST_UPDATED_BY = ?,"
				+ " LAST_UPDATE_DATE = GETDATE()"
				+ " WHERE"
				+ " WORK_HOUR_ID = ?";
		
			sqlArgs.add(sfWorkHour.getWorkHourName());
			sqlArgs.add(sfWorkHour.getWorkingDate());
			sqlArgs.add(sfWorkHour.getWorkBegin1().trim());
			sqlArgs.add(sfWorkHour.getWorkEnd1().trim());
			sqlArgs.add(sfWorkHour.getWorkBegin2().trim());
			sqlArgs.add(sfWorkHour.getWorkEnd2().trim());
			sqlArgs.add(sfUser.getLoginName());
			sqlArgs.add(sfWorkHour.getWorkHourId());
		
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成工时定义 SF_WORK_HOUR数据删除SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfWorkHourDTO sfWorkHour = (SfWorkHourDTO)dtoParameter;
		String sqlStr = "DELETE FROM"
				+ " SF_WORK_HOUR"
				+ " WHERE"
				+ " WORK_HOUR_ID = ?";
			sqlArgs.add(sfWorkHour.getWorkHourId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成工时定义 SF_WORK_HOUR数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfWorkHourDTO sfWorkHour = (SfWorkHourDTO)dtoParameter;
		String sqlStr = "SELECT "
			+ " WORK_HOUR_ID,"
			+ " WORKING_DATE,"
			+ " WORK_BEGIN_1,"
			+ " WORK_END_1,"
			+ " WORK_BEGIN_2,"
			+ " WORK_END_2,"
			+ " WORK_HOUR_NAME"
			+ " FROM"
			+ " SF_WORK_HOUR"
			+ " WHERE"
			+ " WORK_HOUR_ID = ?";
		sqlArgs.add(sfWorkHour.getWorkHourId());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成工时定义 SF_WORK_HOUR多条数据信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回多条数据信息查询用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getMuxDataModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
			List sqlArgs = new ArrayList();
			SfWorkHourDTO sfWorkHour = (SfWorkHourDTO)dtoParameter;
			String sqlStr = "SELECT "
				+ " WORK_HOUR_ID,"
				+ " WORKING_DATE,"
				+ " WORK_BEGIN_1,"
				+ " WORK_END_1,"
				+ " WORK_BEGIN_2,"
				+ " WORK_END_2,"
				+ " CREATED_BY,"
				+ " CREATION_DATE,"
				+ " LAST_UPDATED_BY,"
				+ " LAST_UPDATE_DATE"
				+ " FROM"
				+ " SF_WORK_HOUR"
				+ " WHERE"
				+ " (? <= 0 OR WORK_HOUR_ID = ?)"
				+ " AND (? <= 0 OR WORKING_DATE = ?)"
				+ " AND (? = '' OR WORK_BEGIN_1 LIKE ?)"
				+ " AND (? = '' OR WORK_END_1 LIKE ?)"
				+ " AND (? = '' OR WORK_BEGIN_2 LIKE ?)"
				+ " AND (? = '' OR WORK_END_2 LIKE ?)"
				+ " AND (? = '' OR CREATED_BY LIKE ?)"
				+ " AND (? = '' OR CREATION_DATE LIKE ?)"
				+ " AND (? = '' OR LAST_UPDATED_BY LIKE ?)"
				+ " AND (? = '' OR LAST_UPDATE_DATE LIKE ?)";
			sqlArgs.add(sfWorkHour.getWorkHourId());
			sqlArgs.add(sfWorkHour.getWorkHourId());
			sqlArgs.add(sfWorkHour.getWorkingDate());
			sqlArgs.add(sfWorkHour.getWorkingDate());
			sqlArgs.add(sfWorkHour.getWorkBegin1());
			sqlArgs.add(sfWorkHour.getWorkBegin1());
			sqlArgs.add(sfWorkHour.getWorkEnd1());
			sqlArgs.add(sfWorkHour.getWorkEnd1());
			sqlArgs.add(sfWorkHour.getWorkBegin2());
			sqlArgs.add(sfWorkHour.getWorkBegin2());
			sqlArgs.add(sfWorkHour.getWorkEnd2());
			sqlArgs.add(sfWorkHour.getWorkEnd2());
			sqlArgs.add(sfWorkHour.getCreatedBy());
			sqlArgs.add(sfWorkHour.getCreatedBy());
			sqlArgs.add(sfWorkHour.getCreationDate());
			sqlArgs.add(sfWorkHour.getCreationDate());
			sqlArgs.add(sfWorkHour.getLastUpdatedBy());
			sqlArgs.add(sfWorkHour.getLastUpdatedBy());
			sqlArgs.add(sfWorkHour.getLastUpdateDate());
			sqlArgs.add(sfWorkHour.getLastUpdateDate());
		
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成工时定义 SF_WORK_HOUR页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();

			List sqlArgs = new ArrayList();
			SfWorkHourDTO sfWorkHour = (SfWorkHourDTO)dtoParameter;
			String sqlStr = "SELECT "
				+ " CONVERT(INT,WORK_HOUR_ID) WORK_HOUR_ID,"
				+ " WORKING_DATE,"
				+ " WORK_BEGIN_1,"
				+ " WORK_END_1,"
				+ " WORK_BEGIN_2,"
				+ " WORK_END_2,"
				+ " WORK_HOUR_NAME"
				+ " FROM"
				+ " SF_WORK_HOUR"
				+ " WHERE"
				+ " (? <= 0 OR WORK_HOUR_ID = ?)"
				+ " AND (? = '' OR WORK_HOUR_NAME LIKE ?)";
			sqlArgs.add(sfWorkHour.getWorkHourId());
			sqlArgs.add(sfWorkHour.getWorkHourId());
			sqlArgs.add(sfWorkHour.getWorkHourName());
			sqlArgs.add(sfWorkHour.getWorkHourName());
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		
		return sqlModel;
	}
	

	/**
     * 功能：删除
     * @return SQLModel 
     */
    public SQLModel del(String[] ids){
	    String str = "";
		for (int i = 0; i < ids.length; i++) {
			str += ids[i]+",";
		}
		str = str.substring(0,str.length()-1);
    	SQLModel sqlModel = new SQLModel();
        String sqlStr = "DELETE" 
        		+ " FROM "
        		+ " SF_WORK_HOUR"
        		+ " WHERE"
        		+ " WORK_HOUR_ID IN("+str+")";
        sqlModel.setSqlStr(sqlStr);        
        return sqlModel;
    }
	
	/**
	 * 功能：返回工作时间列表,为页生成下拉列表
	 */
	public SQLModel getWorkHour(){
		SQLModel sqlModel = new SQLModel();
		String sqlStr = "SELECT"
						+ " WORK_HOUR_ID,"
						+ " WORK_HOUR_NAME"
						+ " FROM"
						+ " SF_WORK_HOUR";
		sqlModel.setSqlStr(sqlStr);
		return sqlModel;
	}
	
	/**
	 * 功能：返回工作时间列表,为页生成下拉列表
	 */
	public SQLModel getGZSJ(String time){
		SQLModel sqlModel = new SQLModel();
		String sqlStr = "SELECT"
						+ " WORKING_DATE,"
						+ " WORK_BEGIN_1,"
						+ " WORK_END_1,"
						+ " WORK_BEGIN_2,"
						+ " WORK_END_2"
						+ " FROM"
						+ " SF_WORK_HOUR"
						+ " WHERE"
						+ " WORK_HOUR_ID = "+time;
		sqlModel.setSqlStr(sqlStr);
		return sqlModel;
	}
	
	
}