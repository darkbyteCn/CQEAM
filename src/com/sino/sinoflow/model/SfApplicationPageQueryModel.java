package com.sino.sinoflow.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.sinoflow.dto.SfApplicationDTO;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;

public class SfApplicationPageQueryModel extends BaseSQLProducer {
	
	public SfApplicationPageQueryModel(SfUserBaseDTO userAccount, SfApplicationDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}
	/**
     * 功能：为页应用定义面查使用
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getPageQueryModel(){
    	SfApplicationDTO sfApplication = (SfApplicationDTO)dtoParameter;
    	SQLModel sqlModel = new SQLModel();
    	List sqlArgs = new ArrayList();
        String sqlStr = "SELECT "
			+ " CONVERT(INT,APP_ID) APP_ID,"
			+ " APP_NAME,"
            + " IS_FLOW_PROCESS,"
            + " GROUP_NAME,"
            + " ROLE_NAME,"
			+ " CATEGORY_NAME,"
			+ " WINDOW_TYPE"
			+ " FROM"
			+ " SF_APPLICATION"
			+ " WHERE "
			+ " (? <= 0 OR APP_ID = ?)"
			+ " AND (? = '' OR APP_NAME LIKE ?)"
			+ " AND (? = '' OR IS_FLOW_PROCESS LIKE ?)"
			+ " AND (? = '' OR GROUP_NAME LIKE ?)"
			+ " AND (? = '' OR ROLE_NAME LIKE ?)"
			+ " AND (? = '' OR CATEGORY_NAME LIKE ?)"
			+ " AND (? <= 0 OR WINDOW_TYPE = ?)"
			+ " ORDER BY APP_ID";
        sqlArgs.add(sfApplication.getAppId());
        sqlArgs.add(sfApplication.getAppId());
        sqlArgs.add(sfApplication.getAppName());
        sqlArgs.add(sfApplication.getAppName());
        sqlArgs.add(sfApplication.getIsFlowProcess());
        sqlArgs.add(sfApplication.getIsFlowProcess());
        sqlArgs.add(sfApplication.getGroupName());
        sqlArgs.add(sfApplication.getGroupName());
        sqlArgs.add(sfApplication.getRoleName());
        sqlArgs.add(sfApplication.getRoleName());
        sqlArgs.add(sfApplication.getCategoryName());
        sqlArgs.add(sfApplication.getCategoryName());
        sqlArgs.add(sfApplication.getWindowType());
        sqlArgs.add(sfApplication.getWindowType());
        
        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);        
        return sqlModel;
    }
}
