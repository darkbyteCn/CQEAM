package com.sino.sinoflow.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.log.Logger;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.sinoflow.dto.SfAutoValueDTO;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;


/**
 * <p>Title: SfAutoValueModel</p>
 * <p>Description:程序自动生成SQL构造器“SfAutoValueModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Hing
 * @version 1.0
 */


public class SfAutoValueModel extends BaseSQLProducer {

//	private SfUserBaseDTO sfUser = null;

	/**
	 * 功能：自动赋值信息 SF_AUTO_VALUE 数据库SQL构造层构造函数
	 * @param userAccount SfUserBaseDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter SfAutoValueDTO 本次操作的数据
	 */
	public SfAutoValueModel(SfUserBaseDTO userAccount, SfAutoValueDTO dtoParameter) {
		super(userAccount, dtoParameter);
//		sfUser = userAccount;
	}

	/**
	 * 功能：框架自动生成自动赋值信息 SF_AUTO_VALUE数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 */
	public SQLModel getDataCreateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfAutoValueDTO sfAutoValue = (SfAutoValueDTO)dtoParameter;
		String sqlStr = "INSERT INTO "
			+ " SF_AUTO_VALUE("
			+ " PROJECT_NAME,"
			+ " PROCEDURE_NAME,"
			+ " TASK_TID,"
			+ " FIELD_NAME,"
			+ " FIELD_DESC,"
			+ " ASSIGN_ON,"
			+ " ASSIGN_TYPE,"
			+ " ASSIGN_VALUE,"
			+ " MENO"
			+ ") VALUES ("
			+ " ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        sqlModel.setSqlStr(sqlStr);
        
        sqlArgs.add(sfAutoValue.getProjectName());
		sqlArgs.add(sfAutoValue.getProcedureName());
		sqlArgs.add(sfAutoValue.getTaskTid());
		sqlArgs.add(sfAutoValue.getFieldName());
		sqlArgs.add(sfAutoValue.getFieldDesc());
		sqlArgs.add(sfAutoValue.getAssignOn());
		sqlArgs.add(sfAutoValue.getAssignType());
		sqlArgs.add(sfAutoValue.getAssignValue());
		sqlArgs.add(sfAutoValue.getMeno());
		
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成自动赋值信息 SF_AUTO_VALUE数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据更新用SQLModel
	 */
	public SQLModel getDataUpdateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfAutoValueDTO sfAutoValue = (SfAutoValueDTO)dtoParameter;
		String sqlStr = "UPDATE SF_AUTO_VALUE"
			+ " SET"
			+ " PROJECT_NAME = ?,"
			+ " PROCEDURE_NAME = ?,"
			+ " TASK_TID = ?,"
			+ " FIELD_NAME = ?,"
			+ " FIELD_DESC = ?,"
			+ " ASSIGN_ON = ?,"
			+ " ASSIGN_TYPE = ?,"
			+ " ASSIGN_VALUE = ?,"
			+ " MENO = ?"
			+ " WHERE"
			+ " AUTO_VALUE_ID = ?";

        sqlModel.setSqlStr(sqlStr);

		sqlArgs.add(sfAutoValue.getProjectName());
		sqlArgs.add(sfAutoValue.getProcedureName());
		sqlArgs.add(sfAutoValue.getTaskTid());
		sqlArgs.add(sfAutoValue.getFieldName());
		sqlArgs.add(sfAutoValue.getFieldDesc());
		sqlArgs.add(sfAutoValue.getAssignOn());
		sqlArgs.add(sfAutoValue.getAssignType());
		sqlArgs.add(sfAutoValue.getAssignValue());
		sqlArgs.add(sfAutoValue.getMeno());
		sqlArgs.add(sfAutoValue.getAutoValueId());
		
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成自动赋值信息 SF_AUTO_VALUE数据删除SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfAutoValueDTO sfAutoValue = (SfAutoValueDTO)dtoParameter;
		String sqlStr = "DELETE FROM"
				+ " SF_AUTO_VALUE"
				+ " WHERE"
				+ " AUTO_VALUE_ID = ?";

        sqlModel.setSqlStr(sqlStr);

		sqlArgs.add(sfAutoValue.getAutoValueId());
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成自动赋值信息 SF_AUTO_VALUE数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfAutoValueDTO sfAutoValue = (SfAutoValueDTO)dtoParameter;
		String sqlStr = "SELECT "
			+ " SAV.AUTO_VALUE_ID,"
			+ " SAV.PROJECT_NAME,"
			+ " SAV.PROCEDURE_NAME,"
			+ " ST.TASK_NAME,"
			+ " SAV.FIELD_NAME,"
			+ " SAV.FIELD_DESC,"
			+ " SAV.ASSIGN_ON,"
			+ " SAV.ASSIGN_TYPE,"
			+ " SAV.ASSIGN_VALUE,"
			+ " SAV.MENO,"
			+ " SPV.PROJECT_ID,"
			+ " SP.PROCEDURE_ID,"
			+ " ST.TASK_ID,"
            + " SAV.TASK_TID"
            + " FROM"
			+ " SF_AUTO_VALUE SAV,"
			+ " SF_PROJECT_V SPV,"
			+ " SF_PROCEDURE SP,"
			+ " SF_TASK ST"
			+ " WHERE"
            + " SAV.AUTO_VALUE_ID = ?"
            + " AND SAV.PROJECT_NAME = SPV.PROJECT_NAME"
			+ " AND SAV.PROCEDURE_NAME = SP.PROCEDURE_NAME"
            + " AND SP.PROJECT_ID = SPV.PROJECT_ID"
            + " AND SP.PROCEDURE_ID = ST.PROCEDURE_ID"
            + " AND SAV.TASK_TID = ST.TID";

        sqlModel.setSqlStr(sqlStr);

		sqlArgs.add(new Integer(sfAutoValue.getAutoValueId()));
		sqlModel.setArgs(sqlArgs);
		Logger.logInfo("sql produced");
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成自动赋值信息 SF_AUTO_VALUE多条数据信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回多条数据信息查询用SQLModel
	 */
	public SQLModel getMuxDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfAutoValueDTO sfAutoValue = (SfAutoValueDTO)dtoParameter;
			String sqlStr = "SELECT "
			+ " AUTO_VALUE_ID,"
			+ " PROJECT_NAME,"
			+ " PROCEDURE_NAME,"
			+ " TASK_TID,"
			+ " FIELD_NAME,"
			+ " FIELD_DESC,"
			+ " ASSIGN_ON,"
			+ " ASSIGN_TYPE,"
			+ " ASSIGN_VALUE,"
			+ " MENO"
			+ " FROM"
			+ " SF_AUTO_VALUE"
			+ " WHERE"
			+ " (? <= 0 OR AUTO_VALUE_ID = ?)"
			+ " AND (? = '' OR PROJECT_NAME LIKE ?)"
			+ " AND (? = '' OR PROCEDURE_NAME LIKE ?)"
			+ " AND (? <= 0 OR TASK_TID = ?)"
			+ " AND (? = '' OR FIELD_NAME LIKE ?)"
			+ " AND (? = '' OR FIELD_DESC LIKE ?)"
			+ " AND (? <= 0 OR ASSIGN_ON = ?)"
			+ " AND (? <= 0 OR ASSIGN_TYPE = ?)"
			+ " AND (? <= 0 OR ASSIGN_VALUE = ?)"
			+ " AND (? = '' OR MENO LIKE ?)";

        sqlModel.setSqlStr(sqlStr);

		sqlArgs.add(sfAutoValue.getAutoValueId());
		sqlArgs.add(sfAutoValue.getAutoValueId());
		sqlArgs.add(sfAutoValue.getProjectName());
		sqlArgs.add(sfAutoValue.getProjectName());
		sqlArgs.add(sfAutoValue.getProcedureName());
		sqlArgs.add(sfAutoValue.getProcedureName());
		sqlArgs.add(sfAutoValue.getTaskTid());
		sqlArgs.add(sfAutoValue.getTaskTid());
		sqlArgs.add(sfAutoValue.getFieldName());
		sqlArgs.add(sfAutoValue.getFieldName());
		sqlArgs.add(sfAutoValue.getFieldDesc());
		sqlArgs.add(sfAutoValue.getFieldDesc());
		sqlArgs.add(sfAutoValue.getAssignOn());
		sqlArgs.add(sfAutoValue.getAssignOn());
		sqlArgs.add(sfAutoValue.getAssignType());
		sqlArgs.add(sfAutoValue.getAssignType());
		sqlArgs.add(sfAutoValue.getAssignValue());
		sqlArgs.add(sfAutoValue.getAssignValue());
		sqlArgs.add(sfAutoValue.getMeno());
		sqlArgs.add(sfAutoValue.getMeno());
		
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成自动赋值信息 SF_AUTO_VALUE页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 */
	public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfAutoValueDTO sfAutoValue = (SfAutoValueDTO)dtoParameter;
			String sqlStr = "SELECT "
			+ " CONVERT(INT,SAV.AUTO_VALUE_ID) AUTO_VALUE_ID,"
			+ " SAV.PROJECT_NAME,"
			+ " SAV.PROCEDURE_NAME,"
			+ " ST.TASK_NAME,"
			+ " SAV.FIELD_NAME,"
			+ " SAV.FIELD_DESC,"
			+ " SAV.ASSIGN_ON,"
			+ " SAV.ASSIGN_TYPE,"
			+ " SAV.ASSIGN_VALUE,"
			+ " SAV.MENO"
			+ " FROM"
			+ " SF_AUTO_VALUE SAV, SF_TASK ST, SF_PROJECT_V SPV, SF_PROCEDURE SP"
			+ " WHERE"
			+ " (? <= 0 OR SAV.AUTO_VALUE_ID = ?)"
			+ " AND (? = '' OR SAV.PROJECT_NAME LIKE ?)"
			+ " AND (? = '' OR SAV.PROCEDURE_NAME LIKE ?)"
			+ " AND (? <= 0 OR SAV.TASK_TID = ?)"
			+ " AND (? = '' OR SAV.FIELD_NAME LIKE ?)"
			+ " AND (? = '' OR SAV.FIELD_DESC LIKE ?)"
			+ " AND (? <= 0 OR SAV.ASSIGN_ON = ?)"
			+ " AND (? <= 0 OR SAV.ASSIGN_TYPE = ?)"
			+ " AND (? <= 0 OR SAV.ASSIGN_VALUE = ?)"
			+ " AND (? = '' OR SAV.MENO LIKE ?)"
            + " AND SPV.PROJECT_NAME = SAV.PROJECT_NAME"
            + " AND SP.PROCEDURE_NAME = SAV.PROCEDURE_NAME"
            + " AND SPV.PROJECT_ID = SP.PROJECT_ID"
            + " AND SP.PROCEDURE_ID = ST.PROCEDURE_ID"        
            + " AND SAV.TASK_TID = ST.TID"
            + " ORDER BY SAV.PROJECT_NAME,SAV.PROCEDURE_NAME,SAV.AUTO_VALUE_ID";

        sqlModel.setSqlStr(sqlStr);
		sqlArgs.add(sfAutoValue.getAutoValueId());
		sqlArgs.add(sfAutoValue.getAutoValueId());
		sqlArgs.add(sfAutoValue.getProjectName());
		sqlArgs.add(sfAutoValue.getProjectName());
		sqlArgs.add(sfAutoValue.getProcedureName());
		sqlArgs.add(sfAutoValue.getProcedureName());
		sqlArgs.add(sfAutoValue.getTaskTid());
		sqlArgs.add(sfAutoValue.getTaskTid());
		sqlArgs.add(sfAutoValue.getFieldName());
		sqlArgs.add(sfAutoValue.getFieldName());
		sqlArgs.add(sfAutoValue.getFieldDesc());
		sqlArgs.add(sfAutoValue.getFieldDesc());
		sqlArgs.add(sfAutoValue.getAssignOn());
		sqlArgs.add(sfAutoValue.getAssignOn());
		sqlArgs.add(sfAutoValue.getAssignType());
		sqlArgs.add(sfAutoValue.getAssignType());
		sqlArgs.add(sfAutoValue.getAssignValue());
		sqlArgs.add(sfAutoValue.getAssignValue());
		sqlArgs.add(sfAutoValue.getMeno());
		sqlArgs.add(sfAutoValue.getMeno());
		
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
     * 功能：删除
     * @param ids IDs
     * @return SQLModel 
     */
    public SQLModel del(String[] ids){
	    String str = "";
		for (int i = 0; i < ids.length; i++) {
			str += ids[i].trim()+",";
		}
		str = str.substring(0,str.length()-1);
    	SQLModel sqlModel = new SQLModel();
        String sqlStr = "DELETE" 
        		+ " FROM "
        		+ " SF_AUTO_VALUE"
        		+ " WHERE"
        		+ " AUTO_VALUE_ID IN("+str+")";
        sqlModel.setSqlStr(sqlStr);        
        return sqlModel;
    }

    public SQLModel getAutoValueModel(int taskId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
            String sqlStr = "SELECT "
            + " SAV.AUTO_VALUE_ID,"
            + " SAV.PROJECT_NAME,"
            + " SAV.PROCEDURE_NAME,"
            + " SAV.TASK_TID,"
            + " SAV.FIELD_NAME,"
            + " SAV.FIELD_DESC,"
            + " SAV.ASSIGN_ON,"
            + " SAV.ASSIGN_TYPE,"
            + " SAV.ASSIGN_VALUE,"
            + " SAV.MENO"
            + " FROM"
            + " SF_AUTO_VALUE SAV, SF_TASK ST, SF_PROCEDURE SP, SF_PROJECT SPJ"
            + " WHERE"
            + " ST.TASK_ID = ?"
            + " AND SAV.TASK_TID = ST.TID"
            + " AND SP.PROCEDURE_ID = ST.PROCEDURE_ID"
            + " AND SAV.PROCEDURE_NAME = SP.PROCEDURE_NAME"
            + " AND SP.PROJECT_ID = SPJ.PROJECT_ID"
            + " AND SPJ.PROJECT_NAME = SAV.PROJECT_NAME";

        sqlModel.setSqlStr(sqlStr);
        sqlArgs.add(taskId);

        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}