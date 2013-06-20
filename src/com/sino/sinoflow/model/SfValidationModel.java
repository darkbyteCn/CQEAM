package com.sino.sinoflow.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.sinoflow.dto.SfValidationDTO;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;


/**
 * <p>Title: SfValidationModel</p>
 * <p>Description:程序自动生成SQL构造器“SfValidationModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Hing
 * @version 1.0
 */


public class SfValidationModel extends BaseSQLProducer {

//	private SfUserBaseDTO sfUser = null;

	/**
	 * 功能：合法性检查信息 SF_VALIDATION 数据库SQL构造层构造函数
	 * @param userAccount SfUserBaseDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter SfValidationDTO 本次操作的数据
	 */
	public SfValidationModel(SfUserBaseDTO userAccount, SfValidationDTO dtoParameter) {
		super(userAccount, dtoParameter);
//		sfUser = userAccount;
	}

	/**
	 * 功能：框架自动生成合法性检查信息 SF_VALIDATION数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 */
	public SQLModel getDataCreateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfValidationDTO sfValidation = (SfValidationDTO)dtoParameter;
		String sqlStr = "INSERT INTO "
			+ " SF_VALIDATION("
			+ " PROJECT_NAME,"
			+ " PROCEDURE_NAME,"
			+ " TASK_TID,"
			+ " FIELD_NAME,"
			+ " FIELD_DESC,"
			+ " VALIDATION_TYPE,"
			+ " SIZE_TYPE,"
			+ " CHECK_SIZE,"
			+ " MATCH_PATTERN,"
			+ " MEMO"
			+ ") VALUES ("
			+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        sqlModel.setSqlStr(sqlStr);
        
        sqlArgs.add(sfValidation.getProjectName());
		sqlArgs.add(sfValidation.getProcedureName());
		sqlArgs.add(sfValidation.getTaskTid());
		sqlArgs.add(sfValidation.getFieldName());
		sqlArgs.add(sfValidation.getFieldDesc());
		sqlArgs.add(sfValidation.getValidationType());
		sqlArgs.add(sfValidation.getSizeType());
		sqlArgs.add(sfValidation.getCheckSize());
		sqlArgs.add(sfValidation.getMatchPattern());
		sqlArgs.add(sfValidation.getMemo());
		
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成合法性检查信息 SF_VALIDATION数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据更新用SQLModel
	 */
	public SQLModel getDataUpdateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfValidationDTO sfValidation = (SfValidationDTO)dtoParameter;
		String sqlStr = "UPDATE SF_VALIDATION"
			+ " SET"
			+ " PROJECT_NAME = ?,"
			+ " PROCEDURE_NAME = ?,"
			+ " TASK_TID = ?,"
			+ " FIELD_NAME = ?,"
			+ " FIELD_DESC = ?,"
			+ " VALIDATION_TYPE = ?,"
			+ " SIZE_TYPE = ?,"
			+ " CHECK_SIZE = ?,"
			+ " MATCH_PATTERN = ?,"
			+ " MEMO = ?"
			+ " WHERE"
			+ " VALIDATE_ID = ?";
		
        sqlModel.setSqlStr(sqlStr);

		sqlArgs.add(sfValidation.getProjectName());
		sqlArgs.add(sfValidation.getProcedureName());
		sqlArgs.add(sfValidation.getTaskTid());
		sqlArgs.add(sfValidation.getFieldName());
		sqlArgs.add(sfValidation.getFieldDesc());
		sqlArgs.add(sfValidation.getValidationType());
		sqlArgs.add(sfValidation.getSizeType());
		sqlArgs.add(sfValidation.getCheckSize());
		sqlArgs.add(sfValidation.getMatchPattern());
		sqlArgs.add(sfValidation.getMemo());
		sqlArgs.add(sfValidation.getValidateId());
		
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成合法性检查信息 SF_VALIDATION数据删除SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfValidationDTO sfValidation = (SfValidationDTO)dtoParameter;
		String sqlStr = "DELETE FROM"
				+ " SF_VALIDATION"
				+ " WHERE"
				+ " VALIDATE_ID = ?";
        sqlModel.setSqlStr(sqlStr);
		sqlArgs.add(sfValidation.getValidateId());
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成合法性检查信息 SF_VALIDATION数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfValidationDTO sfValidation = (SfValidationDTO)dtoParameter;
		String sqlStr = "SELECT "
			+ " SV.VALIDATE_ID,"
			+ " SV.PROJECT_NAME,"
			+ " SV.PROCEDURE_NAME,"
			+ " ST.TASK_NAME,"
			+ " SV.FIELD_NAME,"
			+ " SV.FIELD_DESC,"
			+ " SV.VALIDATION_TYPE,"
			+ " SV.SIZE_TYPE,"
			+ " SV.CHECK_SIZE,"
			+ " SV.MATCH_PATTERN,"
			+ " SV.MEMO,"
			+ " SPV.PROJECT_ID,"
			+ " SP.PROCEDURE_ID,"
			+ " ST.TASK_ID,"
            + " SV.TASK_TID"
            + " FROM"
			+ " SF_VALIDATION SV,"
			+ " SF_PROJECT_V SPV,"
			+ " SF_PROCEDURE SP,"
			+ " SF_TASK ST"
			+ " WHERE"
            + " SV.VALIDATE_ID = ?"
			+ " AND SV.PROJECT_NAME=SPV.PROJECT_NAME"
            + " AND SP.PROCEDURE_NAME = SV.PROCEDURE_NAME"
            + " AND SPV.PROJECT_ID = SP.PROJECT_ID"
            + " AND ST.PROCEDURE_ID = SP.PROCEDURE_ID"
			+ " AND SV.TASK_TID=ST.TID"; 

        sqlModel.setSqlStr(sqlStr);
		sqlArgs.add(sfValidation.getValidateId());
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成合法性检查信息 SF_VALIDATION多条数据信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回多条数据信息查询用SQLModel
	 */
	public SQLModel getMuxDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfValidationDTO sfValidation = (SfValidationDTO)dtoParameter;
			String sqlStr = "SELECT "
			+ " VALIDATE_ID,"
			+ " PROJECT_NAME,"
			+ " PROCEDURE_NAME,"
			+ " TASK_TID,"
			+ " FIELD_NAME,"
			+ " FIELD_DESC,"
			+ " VALIDATION_TYPE,"
			+ " SIZE_TYPE,"
			+ " CHECK_SIZE,"
			+ " MATCH_PATTERN,"
			+ " MEMO"
			+ " FROM"
			+ " SF_VALIDATION"
			+ " WHERE"
			+ " (? <= 0 OR VALIDATE_ID = ?)"
			+ " AND (? = '' OR PROJECT_NAME LIKE ?)"
			+ " AND (? = '' OR PROCEDURE_NAME LIKE ?)"
			+ " AND (? <= 0 OR TASK_TID = ?)"
			+ " AND (? = '' OR FIELD_NAME LIKE ?)"
			+ " AND (? = '' OR FIELD_DESC LIKE ?)"
			+ " AND (? <= 0 OR VALIDATION_TYPE = ?)"
			+ " AND (? <= 0 OR SIZE_TYPE = ?)"
			+ " AND (? <= 0 OR CHECK_SIZE = ?)"
			+ " AND (? = '' OR MATCH_PATTERN LIKE ?)"
			+ " AND (? = '' OR MEMO LIKE ?)";

        sqlModel.setSqlStr(sqlStr);

		sqlArgs.add(sfValidation.getValidateId());
		sqlArgs.add(sfValidation.getValidateId());
		sqlArgs.add(sfValidation.getProjectName());
		sqlArgs.add(sfValidation.getProjectName());
		sqlArgs.add(sfValidation.getProcedureName());
		sqlArgs.add(sfValidation.getProcedureName());
		sqlArgs.add(sfValidation.getTaskTid());
		sqlArgs.add(sfValidation.getTaskTid());
		sqlArgs.add(sfValidation.getFieldName());
		sqlArgs.add(sfValidation.getFieldName());
		sqlArgs.add(sfValidation.getFieldDesc());
		sqlArgs.add(sfValidation.getFieldDesc());
		sqlArgs.add(sfValidation.getValidationType());
		sqlArgs.add(sfValidation.getValidationType());
		sqlArgs.add(sfValidation.getSizeType());
		sqlArgs.add(sfValidation.getSizeType());
		sqlArgs.add(sfValidation.getCheckSize());
		sqlArgs.add(sfValidation.getCheckSize());
		sqlArgs.add(sfValidation.getMatchPattern());
		sqlArgs.add(sfValidation.getMatchPattern());
		sqlArgs.add(sfValidation.getMemo());
		sqlArgs.add(sfValidation.getMemo());
		
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成合法性检查信息 SF_VALIDATION页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 */
	public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfValidationDTO sfValidation = (SfValidationDTO)dtoParameter;
			String sqlStr = "SELECT "
			+ " CONVERT(INT,SV.VALIDATE_ID) VALIDATE_ID,"
			+ " SV.PROJECT_NAME,"
			+ " SV.PROCEDURE_NAME,"
			+ " ST.TASK_NAME,"
			+ " SV.FIELD_NAME,"
			+ " SV.FIELD_DESC,"
			+ " SV.VALIDATION_TYPE,"
			+ " SV.SIZE_TYPE,"
			+ " SV.CHECK_SIZE,"
			+ " SV.MATCH_PATTERN,"
			+ " SV.MEMO"
			+ " FROM"
			+ " SF_VALIDATION SV,"
            + " SF_PROJECT_V SPV,"
            + " SF_PROCEDURE SP,"
            + " SF_TASK ST"
            + " WHERE"
			+ " (? <= 0 OR SV.VALIDATE_ID = ?)"
			+ " AND (? = '' OR SV.PROJECT_NAME LIKE ?)"
			+ " AND (? = '' OR SV.PROCEDURE_NAME LIKE ?)"
			+ " AND (? <= 0 OR SV.TASK_TID = ?)"
			+ " AND (? = '' OR SV.FIELD_NAME LIKE ?)"
			+ " AND (? = '' OR SV.FIELD_DESC LIKE ?)"
			+ " AND (? <= 0 OR SV.VALIDATION_TYPE = ?)"
			+ " AND (? <= 0 OR SV.SIZE_TYPE = ?)"
			+ " AND (? <= 0 OR SV.CHECK_SIZE = ?)"
			+ " AND (? = '' OR SV.MATCH_PATTERN LIKE ?)"
			+ " AND (? = '' OR SV.MEMO LIKE ?)"
            + " AND SPV.PROJECT_NAME = SV.PROJECT_NAME"
            + " AND SP.PROCEDURE_NAME = SV.PROCEDURE_NAME"
            + " AND SPV.PROJECT_ID = SP.PROJECT_ID"
            + " AND SP.PROCEDURE_ID = ST.PROCEDURE_ID"
            + " AND SV.TASK_TID = ST.TID"
            + " ORDER BY SV.PROJECT_NAME,SV.PROCEDURE_NAME,SV.VALIDATE_ID";

        sqlModel.setSqlStr(sqlStr);

		sqlArgs.add(sfValidation.getValidateId());
		sqlArgs.add(sfValidation.getValidateId());
		sqlArgs.add(sfValidation.getProjectName());
		sqlArgs.add(sfValidation.getProjectName());
		sqlArgs.add(sfValidation.getProcedureName());
		sqlArgs.add(sfValidation.getProcedureName());
		sqlArgs.add(sfValidation.getTaskTid());
		sqlArgs.add(sfValidation.getTaskTid());
		sqlArgs.add(sfValidation.getFieldName());
		sqlArgs.add(sfValidation.getFieldName());
		sqlArgs.add(sfValidation.getFieldDesc());
		sqlArgs.add(sfValidation.getFieldDesc());
		sqlArgs.add(sfValidation.getValidationType());
		sqlArgs.add(sfValidation.getValidationType());
		sqlArgs.add(sfValidation.getSizeType());
		sqlArgs.add(sfValidation.getSizeType());
		sqlArgs.add(sfValidation.getCheckSize());
		sqlArgs.add(sfValidation.getCheckSize());
		sqlArgs.add(sfValidation.getMatchPattern());
		sqlArgs.add(sfValidation.getMatchPattern());
		sqlArgs.add(sfValidation.getMemo());
		sqlArgs.add(sfValidation.getMemo());
		
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
			str += ids[i]+",";
		}
		str = str.substring(0,str.length()-1);
    	SQLModel sqlModel = new SQLModel();
        String sqlStr = "DELETE" 
        		+ " FROM "
        		+ " SF_VALIDATION"
        		+ " WHERE"
        		+ " VALIDATE_ID IN("+str+")";
        sqlModel.setSqlStr(sqlStr);        
        return sqlModel;
    }

    public SQLModel getValidationModel(int taskId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
            String sqlStr = "SELECT "
                    + " SV.VALIDATE_ID,"
                    + " SV.PROJECT_NAME,"
                    + " SV.PROCEDURE_NAME,"
                    + " SV.TASK_TID,"
                    + " SV.FIELD_NAME,"
                    + " SV.FIELD_DESC,"
                    + " SV.VALIDATION_TYPE,"
                    + " SV.SIZE_TYPE,"
                    + " SV.CHECK_SIZE,"
                    + " SV.MATCH_PATTERN,"
                    + " SV.MEMO"
                    + " FROM"
                    + " SF_VALIDATION SV, SF_TASK ST, SF_PROCEDURE SP, SF_PROJECT SPJ"
                    + " WHERE"
                    + " ST.TASK_ID = ?"
                    + " AND SV.TASK_TID = ST.TID"
                    + " AND SP.PROCEDURE_ID = ST.PROCEDURE_ID"
                    + " AND SV.PROCEDURE_NAME = SP.PROCEDURE_NAME"
                    + " AND SP.PROJECT_ID = SPJ.PROJECT_ID"
                    + " AND SPJ.PROJECT_NAME = SV.PROJECT_NAME";

        sqlModel.setSqlStr(sqlStr);
        sqlArgs.add(taskId);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}