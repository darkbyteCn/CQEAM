package com.sino.sinoflow.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.sinoflow.dto.SfDelegationDTO;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;


/**
 * <p>Title: SfDelegationModel</p>
 * <p>Description:程序自动生成SQL构造器“SfDelegationModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Hing
 * @version 1.0
 */


public class SfDelegationModel extends BaseSQLProducer {

	/**
	 * 功能：委派定义 SF_DELEGATION 数据库SQL构造层构造函数
	 * @param userAccount SfUserBaseDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter SfDelegationDTO 本次操作的数据
	 */
	public SfDelegationModel(SfUserBaseDTO userAccount, SfDelegationDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：框架自动生成委派定义 SF_DELEGATION数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataCreateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
	
			List sqlArgs = new ArrayList();
			SfDelegationDTO sfDelegation = (SfDelegationDTO)dtoParameter;
			String sqlStr = "INSERT INTO "
				+ " SF_DELEGATION("
				+ " USER_ID,"
				+ " DELEGATE_TO,"
				+ " STATUS_CTL,"
				+ " START_DATE,"
				+ " END_DATE"
				+ ") VALUES ("
				//+ "  " + SyBaseSQLUtil.getNewID( "SF_DELEGATION_S" ) + " , ?, ?, ?,CONVERT(DATETIME, ?),CONVERT(DATETIME, ?) )";
				+ " ?, ?, ?,CONVERT(DATETIME, ?),CONVERT(DATETIME, ?) )";
			
			sqlArgs.add(sfDelegation.getUserId());
			sqlArgs.add(sfDelegation.getDelegateTo());
			sqlArgs.add(sfDelegation.getStatusCtl());
			sqlArgs.add(sfDelegation.getStartDate());
			sqlArgs.add(sfDelegation.getEndDate());
			
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
	
		return sqlModel;
	}
	

	/**
	 * 功能：框架自动生成委派定义 SF_DELEGATION数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据更新用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataUpdateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		
			List sqlArgs = new ArrayList();
			SfDelegationDTO sfDelegation = (SfDelegationDTO)dtoParameter;
			String sqlStr = "UPDATE SF_DELEGATION"
				+ " SET"
				+ " USER_ID = ?,"
				+ " DELEGATE_TO = ?,"
				+ " STATUS_CTL = ?,"
				+ " START_DATE = CONVERT(DATETIME, ?),"
				+ " END_DATE = CONVERT(DATETIME, ?)"
				+ " WHERE"
				+ " DELEGATION_ID = ?";
		
			sqlArgs.add(sfDelegation.getUserId());
			sqlArgs.add(sfDelegation.getDelegateTo());
			sqlArgs.add(sfDelegation.getStatusCtl());
			sqlArgs.add(sfDelegation.getStartDate());
			sqlArgs.add(sfDelegation.getEndDate());
			sqlArgs.add(sfDelegation.getDelegationId());
		
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
	
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成委派定义 SF_DELEGATION数据删除SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfDelegationDTO sfDelegation = (SfDelegationDTO)dtoParameter;
		String sqlStr = "DELETE FROM"
				+ " SF_DELEGATION"
				+ " WHERE"
				+ " DELEGATION_ID = ?";
			sqlArgs.add(sfDelegation.getDelegationId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成委派定义 SF_DELEGATION数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfDelegationDTO sfDelegation = (SfDelegationDTO)dtoParameter;
		String sqlStr = "SELECT" 
						+ " SD.DELEGATION_ID," 
						+ " SD.USER_ID, "
						+ " SD.DELEGATE_TO," 
						+ " SD.STATUS_CTL," 
						+ " SD.START_DATE," 
						+ " SD.END_DATE,"
						+ " SUS.USERNAME S_NAME,"
						+ " SUE.USERNAME E_NAME"
						+ " FROM "
						+ " SF_DELEGATION SD,"
						+ " SF_USER SUS,"
						+ " SF_USER SUE "
						+ " WHERE" 
						+ " SUS.USER_ID = SD.USER_ID" 
						+ " AND" 
						+ " SUE.USER_ID = SD.DELEGATE_TO" 
						+ " AND SD.DELEGATION_ID=?";
		
		sqlArgs.add(sfDelegation.getDelegationId());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成委派定义 SF_DELEGATION多条数据信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回多条数据信息查询用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getMuxDataModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
	
			List sqlArgs = new ArrayList();
			SfDelegationDTO sfDelegation = (SfDelegationDTO)dtoParameter;
			String sqlStr = "SELECT "
				+ " DELEGATION_ID,"
				+ " USER_ID,"
				+ " DELEGATE_TO,"
				+ " STATUS_CTL,"
				+ " START_DATE,"
				+ " END_DATE"
				+ " FROM"
				+ " SF_DELEGATION"
				+ " WHERE"
				+ " (? <= 0 OR DELEGATION_ID = ?)"
				+ " AND (? <= 0 OR USER_ID = ?)"
				+ " AND (? <= 0 OR DELEGATE_TO = ?)"
				+ " AND (? <= 0 OR STATUS_CTL = ?)"
				+ " AND (? = '' OR START_DATE LIKE ?)"
				+ " AND (? = '' OR END_DATE LIKE ?)";
			sqlArgs.add(sfDelegation.getDelegationId());
			sqlArgs.add(sfDelegation.getDelegationId());
			sqlArgs.add(sfDelegation.getUserId());
			sqlArgs.add(sfDelegation.getUserId());
			sqlArgs.add(sfDelegation.getDelegateTo());
			sqlArgs.add(sfDelegation.getDelegateTo());
			sqlArgs.add(sfDelegation.getStatusCtl());
			sqlArgs.add(sfDelegation.getStatusCtl());
			sqlArgs.add(sfDelegation.getStartDate());
			sqlArgs.add(sfDelegation.getStartDate());
			sqlArgs.add(sfDelegation.getEndDate());
			sqlArgs.add(sfDelegation.getEndDate());
		
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		
		return sqlModel;
	}

	/**
	 * 功能：根据外键关联字段 userId 构造查询数据SQL。
	 * 框架自动生成数据委派定义 SF_DELEGATION详细信息查询SQLModel，请根据实际需要修改。
	 * @param userId String 
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	private SQLModel getDataByUserIdModel(int userId) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT "
			+ " DELEGATION_ID,"
			+ " DELEGATE_TO,"
			+ " STATUS_CTL,"
			+ " START_DATE,"
			+ " END_DATE"
			+ " FROM"
			+ " SF_DELEGATION"
			+ " WHERE"
			+ " USER_ID = ?";
		sqlArgs.add(userId);
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据外键获取数据
	 * @param foreignKey 传入的外键字段名称。
	 * @return SQLModel
	 */
	public SQLModel getDataByForeignKeyModel(String foreignKey) {
		SQLModel sqlModel = null;
		SfDelegationDTO sfDelegation = (SfDelegationDTO)dtoParameter;
		if(foreignKey.equals("userId")){
			sqlModel = getDataByUserIdModel(sfDelegation.getUserId());
		}
		return sqlModel;
	}

	/**
	 * 功能：根据外键关联字段 userId 构造数据删除SQL。
	 * 框架自动生成数据委派定义 SF_DELEGATION数据删除SQLModel，请根据实际需要修改。
	 * @param userId String 
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	private SQLModel getDeleteByUserIdModel(int userId) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "DELETE "
			+ " FROM"
			+ " SF_DELEGATION"
			+ " WHERE"
			+ " USER_ID = ?";
		sqlArgs.add(userId);
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据外键字段删除数据
	 * @param foreignKey 传入的外键字段名称。
	 * @return SQLModel
	 */
	public SQLModel getDeleteByForeignKeyModel(String foreignKey) {
		SQLModel sqlModel = null;
		SfDelegationDTO sfDelegation = (SfDelegationDTO)dtoParameter;
		if(foreignKey.equals("userId")){
			sqlModel = getDeleteByUserIdModel(sfDelegation.getUserId());
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成委派定义 SF_DELEGATION页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
	
			List sqlArgs = new ArrayList();
			SfDelegationDTO sfDelegation = (SfDelegationDTO)dtoParameter;
/*
            String sqlStr = "SELECT "
				+ " D.DELEGATION_ID,"
				+ " D.USER_ID,"
				+ " D.DELEGATE_TO,"
				+ " D.STATUS_CTL,"
				+ " D.START_DATE,"
				+ " D.END_DATE,"
				+ " US.USERNAME S_NAME,"
				+ " UE.USERNAME E_NAME"
				+ " FROM"
				+ " SF_DELEGATION D,"
				+ " SF_USER US,"
				+ " SF_USER UE"
				+ " WHERE"
				+ " (? <= 0 OR D.DELEGATION_ID = ?)"
				+ " AND (? <= 0 OR D.USER_ID = ?)"
				+ " AND (? <= 0 OR D.DELEGATE_TO = ?)"
				+ " AND (? <= 0 OR D.STATUS_CTL = ?)"
                + " AND D.USER_ID = ?"
                + " AND D.USER_ID = US.USER_ID"
				+ " AND D.DELEGATE_TO = UE.USER_ID";
*/

        String sqlStr = "SELECT "
            + " CONVERT(INT,D.DELEGATION_ID) DELEGATION_ID,"
            + " D.USER_ID,"
            + " D.DELEGATE_TO,"
            + " D.STATUS_CTL,"
            + " D.START_DATE,"
            + " D.END_DATE,"
            + " US.USERNAME S_NAME,"
            + " UE.USERNAME E_NAME"
            + " FROM"
            + " SF_DELEGATION D,"
            + " SF_USER US,"
            + " SF_USER UE"
            + " WHERE"
            + " (? <= 0 OR D.DELEGATION_ID = ?)"
            + " AND (? <= 0 OR D.USER_ID = ?)"
            + " AND (? <= 0 OR D.DELEGATE_TO = ?)"
            + " AND (? <= 0 OR D.STATUS_CTL = ?)"
            + " AND D.USER_ID = US.USER_ID"
            + " AND D.DELEGATE_TO = UE.USER_ID";
        
            sqlArgs.add(sfDelegation.getDelegationId());
			sqlArgs.add(sfDelegation.getDelegationId());
			sqlArgs.add(sfDelegation.getUserId());
			sqlArgs.add(sfDelegation.getUserId());
			sqlArgs.add(sfDelegation.getDelegateTo());
			sqlArgs.add(sfDelegation.getDelegateTo());
			sqlArgs.add(sfDelegation.getStatusCtl());
			sqlArgs.add(sfDelegation.getStatusCtl());
//            sqlArgs.add(((SfUserBaseDTO)this.userAccount).getUserId());

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
        		+ " SF_DELEGATION"
        		+ " WHERE"
        		+ " DELEGATION_ID IN("+str+")";
        sqlModel.setSqlStr(sqlStr);        
        return sqlModel;
    }

    public SQLModel getDelegateUser(int toUserId) {
        List sqlArgs = new ArrayList();
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT SU.LOGIN_NAME FROM SF_DELEGATION SD, SF_USER SU"
                + " WHERE SD.DELEGATE_TO = ? AND SD.USER_ID = SU.USER_ID";
        sqlModel.setSqlStr(sqlStr);
        sqlArgs.add(toUserId);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}