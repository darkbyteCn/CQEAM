package com.sino.sinoflow.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.sinoflow.dto.SfApiDTO;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;


/**
 * <p>Title: SfApiModel</p>
 * <p>Description:程序自动生成SQL构造器“SfApiModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Hing
 * @version 1.0
 */


public class SfApiModel extends BaseSQLProducer {

//	private SfUserBaseDTO sfUser = null;
	/**
	 * 功能：接口程序信息 SF_API 数据库SQL构造层构造函数
	 * @param userAccount SfUserBaseDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter SfApiDTO 本次操作的数据
	 */
	public SfApiModel(SfUserBaseDTO userAccount, SfApiDTO dtoParameter) {
		super(userAccount, dtoParameter);
//		sfUser = userAccount;
	}

	/**
	 * 功能：框架自动生成接口程序信息 SF_API数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 */
	public SQLModel getDataCreateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfApiDTO sfApi = (SfApiDTO)dtoParameter;
		String sqlStr = "INSERT INTO "
			+ " SF_API("
			+ " PROJECT_NAME,"
			+ " PROCEDURE_NAME,"
			+ " TASK_TID,"
			+ " API_CONTROL,"
			+ " SFQUERYOPEN,"
			+ " SFPOSTOPEN,"
			+ " SFQUERYSIGN,"
			+ " SFPOSTSIGN,"
			+ " SFQUERYCOMPLETE,"
			+ " SFGROUPREVIEW,"
			+ " SFQUERYCYCLEREVIEW,"
			+ " SFQUERYCONDITIONALFLOW,"
			+ " SFQUERYGROUP,"
			+ " SFPARALLELFLOW,"
			+ " SFQUERYASSISTFLOW,"
			+ " SFQUERYDISTRIBUTE,"
			+ " SFQUERYGOBACK,"
			+ " SFQUERYSAVE,"
			+ " SFPOSTSAVE,"
			+ " API_NAME,"
            + " ENABLED"
            + ") VALUES ("
			+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";
		
        sqlModel.setSqlStr(sqlStr);

		sqlArgs.add(sfApi.getProjectName());
		sqlArgs.add(sfApi.getProcedureName());
		sqlArgs.add(sfApi.getTaskTid());
		sqlArgs.add(sfApi.getApiControl());
		sqlArgs.add(sfApi.getSfqueryopen());
		sqlArgs.add(sfApi.getSfpostopen());
		sqlArgs.add(sfApi.getSfquerysign());
		sqlArgs.add(sfApi.getSfpostsign());
		sqlArgs.add(sfApi.getSfquerycomplete());
		sqlArgs.add(sfApi.getSfgroupreview());
		sqlArgs.add(sfApi.getSfquerycyclereview());
		sqlArgs.add(sfApi.getSfqueryconditionalflow());
		sqlArgs.add(sfApi.getSfquerygroup());
		sqlArgs.add(sfApi.getSfparallelflow());
		sqlArgs.add(sfApi.getSfqueryassistflow());
		sqlArgs.add(sfApi.getSfquerydistribute());
		sqlArgs.add(sfApi.getSfquerygoback());
		sqlArgs.add(sfApi.getSfquerysave());
		sqlArgs.add(sfApi.getSfpostsave());
		sqlArgs.add(sfApi.getApiName());
        sqlArgs.add(sfApi.getEnabled());

        sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成接口程序信息 SF_API数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据更新用SQLModel
	 */
	public SQLModel getDataUpdateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfApiDTO sfApi = (SfApiDTO)dtoParameter;
		String sqlStr = "UPDATE SF_API"
			+ " SET"
			+ " PROJECT_NAME = ?,"
			+ " PROCEDURE_NAME = ?,"
			+ " TASK_TID = ?,"
			+ " API_CONTROL = ?,"
			+ " SFQUERYOPEN = ?,"
			+ " SFPOSTOPEN = ?,"
			+ " SFQUERYSIGN = ?,"
			+ " SFPOSTSIGN = ?,"
			+ " SFQUERYCOMPLETE = ?,"
			+ " SFGROUPREVIEW = ?,"
			+ " SFQUERYCYCLEREVIEW = ?,"
			+ " SFQUERYCONDITIONALFLOW = ?,"
			+ " SFQUERYGROUP = ?,"
			+ " SFPARALLELFLOW = ?,"
			+ " SFQUERYASSISTFLOW = ?,"
			+ " SFQUERYDISTRIBUTE = ?,"
			+ " SFQUERYGOBACK = ?,"
			+ " SFQUERYSAVE = ?,"
			+ " SFPOSTSAVE = ?,"
			+ " API_NAME = ?,"
            + " ENABLED = ?"
            + " WHERE"
			+ " API_ID = ?";
		
        sqlModel.setSqlStr(sqlStr);

		sqlArgs.add(sfApi.getProjectName());
		sqlArgs.add(sfApi.getProcedureName());
		sqlArgs.add(sfApi.getTaskTid());
		sqlArgs.add(sfApi.getApiControl());
		sqlArgs.add(sfApi.getSfqueryopen());
		sqlArgs.add(sfApi.getSfpostopen());
		sqlArgs.add(sfApi.getSfquerysign());
		sqlArgs.add(sfApi.getSfpostsign());
		sqlArgs.add(sfApi.getSfquerycomplete());
		sqlArgs.add(sfApi.getSfgroupreview());
		sqlArgs.add(sfApi.getSfquerycyclereview());
		sqlArgs.add(sfApi.getSfqueryconditionalflow());
		sqlArgs.add(sfApi.getSfquerygroup());
		sqlArgs.add(sfApi.getSfparallelflow());
		sqlArgs.add(sfApi.getSfqueryassistflow());
		sqlArgs.add(sfApi.getSfquerydistribute());
		sqlArgs.add(sfApi.getSfquerygoback());
		sqlArgs.add(sfApi.getSfquerysave());
		sqlArgs.add(sfApi.getSfpostsave());
		sqlArgs.add(sfApi.getApiName());
        sqlArgs.add(sfApi.getEnabled());
        sqlArgs.add(sfApi.getApiId());
		
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成接口程序信息 SF_API数据删除SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfApiDTO sfApi = (SfApiDTO)dtoParameter;
		String sqlStr = "DELETE FROM"
				+ " SF_API"
				+ " WHERE"
				+ " API_ID = ?";

        sqlModel.setSqlStr(sqlStr);
		sqlArgs.add(sfApi.getApiId());
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成接口程序信息 SF_API数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfApiDTO sfApi = (SfApiDTO)dtoParameter;
		String sqlStr = "SELECT "
			+ " SA.API_ID,"
			+ " SA.PROJECT_NAME,"
			+ " SA.API_CONTROL,"
			+ " SA.SFQUERYOPEN,"
			+ " SA.SFPOSTOPEN,"
			+ " SA.SFQUERYSIGN,"
			+ " SA.SFPOSTSIGN,"
			+ " SA.SFQUERYCOMPLETE,"
			+ " SA.SFGROUPREVIEW,"
			+ " SA.SFQUERYCYCLEREVIEW,"
			+ " SA.SFQUERYCONDITIONALFLOW,"
			+ " SA.SFQUERYGROUP,"
			+ " SA.SFPARALLELFLOW,"
			+ " SA.SFQUERYASSISTFLOW,"
			+ " SA.SFQUERYDISTRIBUTE,"
			+ " SA.SFQUERYGOBACK,"
			+ " SA.SFQUERYSAVE,"
			+ " SA.SFPOSTSAVE,"
			+ " SA.API_NAME,"
            + " SA.ENABLED,"
            + " SPJ.PROJECT_ID"
            + " FROM"
			+ " SF_API SA," 
			+ " SF_PROJECT_V SPJ"
			+ " WHERE"
			+ " SA.PROJECT_NAME = SPJ.PROJECT_NAME"
			+ " AND SA.API_ID = ?";

        sqlModel.setSqlStr(sqlStr);

		sqlArgs.add(sfApi.getApiId());
		
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成接口程序信息 SF_API多条数据信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回多条数据信息查询用SQLModel
	 */
	public SQLModel getMuxDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfApiDTO sfApi = (SfApiDTO)dtoParameter;
			String sqlStr = "SELECT "
			+ " API_ID,"
			+ " PROJECT_NAME,"
			+ " PROCEDURE_NAME,"
			+ " TASK_TID,"
			+ " API_CONTROL,"
			+ " SFQUERYOPEN,"
			+ " SFPOSTOPEN,"
			+ " SFQUERYSIGN,"
			+ " SFPOSTSIGN,"
			+ " SFQUERYCOMPLETE,"
			+ " SFGROUPREVIEW,"
			+ " SFQUERYCYCLEREVIEW,"
			+ " SFQUERYCONDITIONALFLOW,"
			+ " SFQUERYGROUP,"
			+ " SFPARALLELFLOW,"
			+ " SFQUERYASSISTFLOW,"
			+ " SFQUERYDISTRIBUTE,"
			+ " SFQUERYGOBACK,"
			+ " SFQUERYSAVE,"
			+ " SFPOSTSAVE,"
			+ " API_NAME,"
            + " ENABLED"
            + " FROM"
			+ " SF_API"
			+ " WHERE"
			+ " (? <= 0 OR API_ID = ?)"
			+ " AND (? = '' OR PROJECT_NAME LIKE ?)"
			+ " AND (? = '' OR PROCEDURE_NAME LIKE ?)"
			+ " AND (? <= 0 OR TASK_TID = ?)"
			+ " AND (? <= 0 OR API_CONTROL = ?)"
/*
            + " AND (? = '' OR SFQUERYOPEN LIKE ?)"
			+ " AND (? = '' OR SFPOSTOPEN LIKE ?)"
			+ " AND (? = '' OR SFQUERYSIGN LIKE ?)"
			+ " AND (? = '' OR SFPOSTSIGN LIKE ?)"
			+ " AND (? = '' OR SFQUERYCOMPLETE LIKE ?)"
			+ " AND (? = '' OR SFGROUPREVIEW LIKE ?)"
			+ " AND (? = '' OR SFQUERYCYCLEREVIEW LIKE ?)"
			+ " AND (? = '' OR SFQUERYCONDITIONALFLOW LIKE ?)"
			+ " AND (? = '' OR SFQUERYGROUP LIKE ?)"
			+ " AND (? = '' OR SFPARALLELFLOW LIKE ?)"
			+ " AND (? = '' OR SFQUERYASSISTFLOW LIKE ?)"
			+ " AND (? = '' OR SFQUERYDISTRIBUTE LIKE ?)"
			+ " AND (? = '' OR SFQUERYGOBACK LIKE ?)"
			+ " AND (? = '' OR SFQUERYSAVE LIKE ?)"
			+ " AND (? = '' OR SFPOSTSAVE LIKE ?)"
*/
			+ " AND (? = '' OR API_NAME LIKE ?)"
            + " AND (? = '' OR ENABLED = ?)";

        sqlModel.setSqlStr(sqlStr);

        sqlArgs.add(sfApi.getApiId());
		sqlArgs.add(sfApi.getApiId());
		sqlArgs.add(sfApi.getProjectName());
		sqlArgs.add(sfApi.getProjectName());
		sqlArgs.add(sfApi.getProcedureName());
		sqlArgs.add(sfApi.getProcedureName());
		sqlArgs.add(sfApi.getTaskTid());
		sqlArgs.add(sfApi.getTaskTid());
		sqlArgs.add(sfApi.getApiControl());
		sqlArgs.add(sfApi.getApiControl());
/*
        sqlArgs.add(sfApi.getSfqueryopen());
		sqlArgs.add(sfApi.getSfqueryopen());
		sqlArgs.add(sfApi.getSfpostopen());
		sqlArgs.add(sfApi.getSfpostopen());
		sqlArgs.add(sfApi.getSfquerysign());
		sqlArgs.add(sfApi.getSfquerysign());
		sqlArgs.add(sfApi.getSfpostsign());
		sqlArgs.add(sfApi.getSfpostsign());
		sqlArgs.add(sfApi.getSfquerycomplete());
		sqlArgs.add(sfApi.getSfquerycomplete());
		sqlArgs.add(sfApi.getSfgroupreview());
		sqlArgs.add(sfApi.getSfgroupreview());
		sqlArgs.add(sfApi.getSfquerycyclereview());
		sqlArgs.add(sfApi.getSfquerycyclereview());
		sqlArgs.add(sfApi.getSfqueryconditionalflow());
		sqlArgs.add(sfApi.getSfqueryconditionalflow());
		sqlArgs.add(sfApi.getSfquerygroup());
		sqlArgs.add(sfApi.getSfquerygroup());
		sqlArgs.add(sfApi.getSfparallelflow());
		sqlArgs.add(sfApi.getSfparallelflow());
		sqlArgs.add(sfApi.getSfqueryassistflow());
		sqlArgs.add(sfApi.getSfqueryassistflow());
		sqlArgs.add(sfApi.getSfquerydistribute());
		sqlArgs.add(sfApi.getSfquerydistribute());
		sqlArgs.add(sfApi.getSfquerygoback());
		sqlArgs.add(sfApi.getSfquerygoback());
		sqlArgs.add(sfApi.getSfquerysave());
		sqlArgs.add(sfApi.getSfquerysave());
		sqlArgs.add(sfApi.getSfpostsave());
		sqlArgs.add(sfApi.getSfpostsave());
*/		
		sqlArgs.add(sfApi.getApiName());
		sqlArgs.add(sfApi.getApiName());
        sqlArgs.add(sfApi.getEnabled());
        sqlArgs.add(sfApi.getEnabled());

        sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成接口程序信息 SF_API页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 */
	public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfApiDTO sfApi = (SfApiDTO)dtoParameter;
			String sqlStr = "SELECT "
			+ " CONVERT(INT,SI.API_ID) API_ID,"
			+ " SI.PROJECT_NAME,"
			+ " SI.PROCEDURE_NAME,"
			+ " SI.API_CONTROL,"
			+ " SI.SFQUERYOPEN,"
			+ " SI.SFPOSTOPEN,"
			+ " SI.SFQUERYSIGN,"
			+ " SI.SFPOSTSIGN,"
			+ " SI.SFQUERYCOMPLETE,"
			+ " SI.SFGROUPREVIEW,"
			+ " SI.SFQUERYCYCLEREVIEW,"
			+ " SI.SFQUERYCONDITIONALFLOW,"
			+ " SI.SFQUERYGROUP,"
			+ " SI.SFPARALLELFLOW,"
			+ " SI.SFQUERYASSISTFLOW,"
			+ " SI.SFQUERYDISTRIBUTE,"
			+ " SI.SFQUERYGOBACK,"
			+ " SI.SFQUERYSAVE,"
			+ " SI.SFPOSTSAVE,"
			+ " SI.API_NAME,"
            + " SI.ENABLED"
            + " FROM"
			+ " SF_API SI"
			+ " WHERE"
			+ " (? <= 0 OR SI.API_ID = ?)"
			+ " AND (? = '' OR SI.PROJECT_NAME LIKE ?)"
			+ " AND (? = '' OR SI.PROCEDURE_NAME LIKE ?)"
			+ " AND (? <= 0 OR SI.TASK_TID = ?)"
			+ " AND (? <= 0 OR SI.API_CONTROL = ?)"
/*
            + " AND (? = '' OR SI.SFQUERYOPEN LIKE ?)"
			+ " AND (? = '' OR SI.SFPOSTOPEN LIKE ?)"
			+ " AND (? = '' OR SI.SFQUERYSIGN LIKE ?)"
			+ " AND (? = '' OR SI.SFPOSTSIGN LIKE ?)"
			+ " AND (? = '' OR SI.SFQUERYCOMPLETE LIKE ?)"
			+ " AND (? = '' OR SI.SFGROUPREVIEW LIKE ?)"
			+ " AND (? = '' OR SI.SFQUERYCYCLEREVIEW LIKE ?)"
			+ " AND (? = '' OR SI.SFQUERYCONDITIONALFLOW LIKE ?)"
			+ " AND (? = '' OR SI.SFQUERYGROUP LIKE ?)"
			+ " AND (? = '' OR SI.SFPARALLELFLOW LIKE ?)"
			+ " AND (? = '' OR SI.SFQUERYASSISTFLOW LIKE ?)"
			+ " AND (? = '' OR SI.SFQUERYDISTRIBUTE LIKE ?)"
			+ " AND (? = '' OR SI.SFQUERYGOBACK LIKE ?)"
			+ " AND (? = '' OR SI.SFQUERYSAVE LIKE ?)"
			+ " AND (? = '' OR SI.SFPOSTSAVE LIKE ?)"
*/
			+ " AND (? = '' OR SI.API_NAME LIKE ?)"
            + " AND (? = '' OR SI.ENABLED = ?)"
			+ " ORDER BY SI.PROJECT_NAME, SI.API_NAME";

        sqlModel.setSqlStr(sqlStr);

		sqlArgs.add(sfApi.getApiId());
		sqlArgs.add(sfApi.getApiId());
		sqlArgs.add(sfApi.getProjectName());
		sqlArgs.add(sfApi.getProjectName());
		sqlArgs.add(sfApi.getProcedureName());
		sqlArgs.add(sfApi.getProcedureName());
		sqlArgs.add(sfApi.getTaskTid());
		sqlArgs.add(sfApi.getTaskTid());
		sqlArgs.add(sfApi.getApiControl());
		sqlArgs.add(sfApi.getApiControl());
/*
        sqlArgs.add(sfApi.getSfqueryopen());
		sqlArgs.add(sfApi.getSfqueryopen());
		sqlArgs.add(sfApi.getSfpostopen());
		sqlArgs.add(sfApi.getSfpostopen());
		sqlArgs.add(sfApi.getSfquerysign());
		sqlArgs.add(sfApi.getSfquerysign());
		sqlArgs.add(sfApi.getSfpostsign());
		sqlArgs.add(sfApi.getSfpostsign());
		sqlArgs.add(sfApi.getSfquerycomplete());
		sqlArgs.add(sfApi.getSfquerycomplete());
		sqlArgs.add(sfApi.getSfgroupreview());
		sqlArgs.add(sfApi.getSfgroupreview());
		sqlArgs.add(sfApi.getSfquerycyclereview());
		sqlArgs.add(sfApi.getSfquerycyclereview());
		sqlArgs.add(sfApi.getSfqueryconditionalflow());
		sqlArgs.add(sfApi.getSfqueryconditionalflow());
		sqlArgs.add(sfApi.getSfquerygroup());
		sqlArgs.add(sfApi.getSfquerygroup());
		sqlArgs.add(sfApi.getSfparallelflow());
		sqlArgs.add(sfApi.getSfparallelflow());
		sqlArgs.add(sfApi.getSfqueryassistflow());
		sqlArgs.add(sfApi.getSfqueryassistflow());
		sqlArgs.add(sfApi.getSfquerydistribute());
		sqlArgs.add(sfApi.getSfquerydistribute());
		sqlArgs.add(sfApi.getSfquerygoback());
		sqlArgs.add(sfApi.getSfquerygoback());
		sqlArgs.add(sfApi.getSfquerysave());
		sqlArgs.add(sfApi.getSfquerysave());
		sqlArgs.add(sfApi.getSfpostsave());
		sqlArgs.add(sfApi.getSfpostsave());
*/
		sqlArgs.add(sfApi.getApiName());
		sqlArgs.add(sfApi.getApiName());
        sqlArgs.add(sfApi.getEnabled());
        sqlArgs.add(sfApi.getEnabled());

        sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	/**
     * 功能：删除
     * @param ids ids
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
        		+ " SF_API"
        		+ " WHERE"
        		+ " API_ID IN("+str+")";
        sqlModel.setSqlStr(sqlStr);        
        return sqlModel;
    }

    public SQLModel enabled(String[] ids) {
        String str = "";
        for (int i = 0; i < ids.length; i++) {
            str += ids[i]+",";
        }
        str = str.substring(0,str.length()-1);
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "UPDATE SF_API SET"
                + " ENABLED = 'Y' "
                + " WHERE"
                + " API_ID IN("+str+")";
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }

    public SQLModel disabled(String[] ids) {
        String str = "";
        for (int i = 0; i < ids.length; i++) {
            str += ids[i]+",";
        }
        str = str.substring(0,str.length()-1);
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "UPDATE SF_API SET"
                + " ENABLED = 'N' "
                + " WHERE"
                + " API_ID IN("+str+")";
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成接口程序信息 SF_API页面翻页查询SQLModel，请根据实际需要修改。
     * @param taskId task ID
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getTaskApiModel(int taskId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
//        SfApiDTO sfApi = (SfApiDTO)dtoParameter;
            String sqlStr = "SELECT "
            + " SI.API_ID,"
            + " SI.PROJECT_NAME,"
            + " SI.API_CONTROL,"
            + " SI.SFQUERYOPEN,"
            + " SI.SFPOSTOPEN,"
            + " SI.SFQUERYSIGN,"
            + " SI.SFPOSTSIGN,"
            + " SI.SFQUERYCOMPLETE,"
            + " SI.SFGROUPREVIEW,"
            + " SI.SFQUERYCYCLEREVIEW,"
            + " SI.SFQUERYCONDITIONALFLOW,"
            + " SI.SFQUERYGROUP,"
            + " SI.SFPARALLELFLOW,"
            + " SI.SFQUERYASSISTFLOW,"
            + " SI.SFQUERYDISTRIBUTE,"
            + " SI.SFQUERYGOBACK,"
            + " SI.SFQUERYSAVE,"
            + " SI.SFPOSTSAVE,"
            + " SI.API_NAME"
            + " FROM"
            + " SF_API SI, SF_TASK ST"
            + " WHERE"
            + " ST.TASK_ID = ?"
            + " AND (SI.ENABLED = '' OR SI.ENABLED <> 'N')"
            + " AND ST.API = SI.API_NAME";

        sqlModel.setSqlStr(sqlStr);
        sqlArgs.add(taskId);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getTaskApiModel(String taskId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
//        SfApiDTO sfApi = (SfApiDTO)dtoParameter;
            String sqlStr = "SELECT "
            + " SI.API_ID,"
            + " SI.PROJECT_NAME,"
            + " SI.API_CONTROL,"
            + " SI.SFQUERYOPEN,"
            + " SI.SFPOSTOPEN,"
            + " SI.SFQUERYSIGN,"
            + " SI.SFPOSTSIGN,"
            + " SI.SFQUERYCOMPLETE,"
            + " SI.SFGROUPREVIEW,"
            + " SI.SFQUERYCYCLEREVIEW,"
            + " SI.SFQUERYCONDITIONALFLOW,"
            + " SI.SFQUERYGROUP,"
            + " SI.SFPARALLELFLOW,"
            + " SI.SFQUERYASSISTFLOW,"
            + " SI.SFQUERYDISTRIBUTE,"
            + " SI.SFQUERYGOBACK,"
            + " SI.SFQUERYSAVE,"
            + " SI.SFPOSTSAVE,"
            + " SI.API_NAME"
            + " FROM"
            + " SF_API SI, SF_TASK ST"
            + " WHERE"
            + " ST.TASK_ID = ?"
            + " AND (SI.ENABLED = '' OR SI.ENABLED <> 'N')"
            + " AND ST.API = SI.API_NAME";

        sqlModel.setSqlStr(sqlStr);
        sqlArgs.add(Integer.parseInt(taskId));
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getApiOptionModel(int apiId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT "
                + " API_NAME,"
                + " API_NAME"
                + " FROM"
                + " SF_API"
                + " WHERE"
                + " API_ID = ?";

        sqlModel.setSqlStr(sqlStr);

        sqlArgs.add(apiId);

        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    public SQLModel getApiOptionModel(String apiId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT "
                + " API_NAME,"
                + " API_NAME"
                + " FROM"
                + " SF_API"
                + " WHERE"
                + " API_ID = ?";

        sqlModel.setSqlStr(sqlStr);

        sqlArgs.add(Integer.parseInt(apiId));

        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    public SQLModel getApiNameModel(int projId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT "
                + " DISTINCT ST.API,"
                + " ST.API"
                + " FROM"
                + " SF_TASK ST, SF_PROCEDURE SP"
                + " WHERE"
                + " SP.PROJECT_ID = ?"
                + " AND SP.PROCEDURE_ID = ST.PROCEDURE_ID"
                + " AND  " + SyBaseSQLUtil.isNotNull("ST.API") + " "
                + " AND ST.API NOT IN"
                + " (SELECT SA.API_NAME"
                + " FROM SF_API SA, SF_PROJECT SPJ"
                + " WHERE SPJ.PROJECT_ID = ?"
                + " AND SPJ.PROJECT_NAME = SA.PROJECT_NAME"
                + " AND  " + SyBaseSQLUtil.isNotNull("SA.API_NAME") + " )";

        sqlModel.setSqlStr(sqlStr);

        sqlArgs.add(projId);
        sqlArgs.add(projId);

        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    public SQLModel getApiNameModel(String projId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT "
                + " DISTINCT ST.API,"
                + " ST.API"
                + " FROM"
                + " SF_TASK ST, SF_PROCEDURE SP"
                + " WHERE"
                + " SP.PROJECT_ID = ?"
                + " AND SP.PROCEDURE_ID = ST.PROCEDURE_ID"
                + " AND  " + SyBaseSQLUtil.isNotNull("ST.API") + " "
                + " AND ST.API NOT IN"
                + " (SELECT SA.API_NAME"
                + " FROM SF_API SA, SF_PROJECT SPJ"
                + " WHERE SPJ.PROJECT_ID = ?"
                + " AND SPJ.PROJECT_NAME = SA.PROJECT_NAME"
                + " AND  " + SyBaseSQLUtil.isNotNull("SA.API_NAME") + " )";

        sqlModel.setSqlStr(sqlStr);

        sqlArgs.add(Integer.parseInt(projId));
        sqlArgs.add(Integer.parseInt(projId));

        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }
}