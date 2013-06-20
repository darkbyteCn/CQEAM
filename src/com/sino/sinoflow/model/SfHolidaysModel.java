package com.sino.sinoflow.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.sinoflow.dto.SfHolidaysDTO;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;


/**
 * <p>Title: SfHolidaysModel</p>
 * <p>Description:程序自动生成SQL构造器“SfHolidaysModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 白嘉
 * @version 1.0
 */ 


public class SfHolidaysModel extends BaseSQLProducer {

//	private SfUserBaseDTO sfUser = null;

	/**
	 * 功能：SF_HOLIDAYS 数据库SQL构造层构造函数
	 * @param userAccount SfUserBaseDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter SfHolidaysDTO 本次操作的数据
	 */
	public SfHolidaysModel(SfUserBaseDTO userAccount, SfHolidaysDTO dtoParameter) {
		super(userAccount, dtoParameter);
//		sfUser = userAccount;
	}

	/**
	 * 功能：框架自动生成SF_HOLIDAYS数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 */
	public SQLModel getDataCreateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfHolidaysDTO sfHolidays = (SfHolidaysDTO)dtoParameter;
		String sqlStr = "INSERT INTO "
			+ " SF_HOLIDAYS("
			+ " HOLIDAYS,"
			+ " NAME,"
			+ " YEAR"
			+ ") VALUES ("
			+ " ?,?,?)";
		sqlArgs.add(sfHolidays.getHolidays().trim());
		sqlArgs.add(sfHolidays.getName().trim());
		sqlArgs.add(sfHolidays.getYear());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成SF_HOLIDAYS数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据更新用SQLModel
	 */
	public SQLModel getDataUpdateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfHolidaysDTO sfHolidays = (SfHolidaysDTO)dtoParameter;
		String sqlStr = "UPDATE SF_HOLIDAYS"
			+ " SET"
			+ " HOLIDAYS = ?,"
			+ " NAME = ?,"
			+ " YEAR = ?"
			+ " WHERE"
			+ " HOLIDAYS_ID = ?";
		
		sqlArgs.add(sfHolidays.getHolidays());
		sqlArgs.add(sfHolidays.getName());
		sqlArgs.add(sfHolidays.getYear());
		sqlArgs.add(sfHolidays.getHolidaysId());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成SF_HOLIDAYS数据删除SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfHolidaysDTO sfHolidays = (SfHolidaysDTO)dtoParameter;
		String sqlStr = "DELETE FROM"
				+ " SF_HOLIDAYS"
				+ " WHERE"
				+ " HOLIDAYS_ID = ?";
			sqlArgs.add(sfHolidays.getHolidaysId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成SF_HOLIDAYS数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfHolidaysDTO sfHolidays = (SfHolidaysDTO)dtoParameter;
		String sqlStr = "SELECT "
			+ " HOLIDAYS_ID,"
			+ " HOLIDAYS,"
			+ " YEAR,"
			+ " NAME"
			+ " FROM"
			+ " SF_HOLIDAYS"
			+ " WHERE"
			+ " HOLIDAYS_ID = ?";
		sqlArgs.add(sfHolidays.getHolidaysId());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成SF_HOLIDAYS多条数据信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回多条数据信息查询用SQLModel
	 */
	public SQLModel getMuxDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfHolidaysDTO sfHolidays = (SfHolidaysDTO)dtoParameter;
			String sqlStr = "SELECT "
			+ " HOLIDAYS_ID,"
			+ " HOLIDAYS"
			+ " FROM"
			+ " SF_HOLIDAYS"
			+ " WHERE"
			+ " (? <= 0 OR HOLIDAYS_ID = ?)"
			+ " AND (? = '' OR HOLIDAYS LIKE ?)";
		sqlArgs.add(sfHolidays.getHolidaysId());
		sqlArgs.add(sfHolidays.getHolidaysId());
		sqlArgs.add(sfHolidays.getHolidays());
		sqlArgs.add(sfHolidays.getHolidays());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成SF_HOLIDAYS页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 */
	public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfHolidaysDTO sfHolidays = (SfHolidaysDTO)dtoParameter;
			String sqlStr = "SELECT "
			+ " CONVERT(INT,HOLIDAYS_ID) HOLIDAYS_ID,"
			+ " NAME,"
			+ " YEAR,"
			+ " HOLIDAYS"
			+ " FROM"
			+ " SF_HOLIDAYS"
			+ " WHERE"
			+ " (? <= 0 OR HOLIDAYS_ID = ?)"
			+ " AND (? = '' OR NAME LIKE ?)"
			+ " AND (? <= 0 OR YEAR = ?)"
			+ " AND (? = '' OR HOLIDAYS LIKE ?)"
			+ " ORDER BY NAME,HOLIDAYS_ID";
		sqlArgs.add(sfHolidays.getHolidaysId());
		sqlArgs.add(sfHolidays.getHolidaysId());
		sqlArgs.add(sfHolidays.getName());
		sqlArgs.add(sfHolidays.getName());
		sqlArgs.add(sfHolidays.getYear());
		sqlArgs.add(sfHolidays.getYear());
		sqlArgs.add(sfHolidays.getHolidays());
		sqlArgs.add(sfHolidays.getHolidays());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	/**
	 * 功能：返回节假日列表,为页面生成下拉列表
     * @return SQLModel SQL Model
	 */
	public SQLModel getHolidays() {
		SQLModel sqlModel = new SQLModel();
//		SfHolidaysDTO sfHolidays = (SfHolidaysDTO)dtoParameter;
			String sqlStr = "SELECT DISTINCT"
			+ " NAME,"
			+ " NAME"
			+ " FROM"
			+ " SF_HOLIDAYS";
		
		sqlModel.setSqlStr(sqlStr);
		return sqlModel;
	}

	/**
     * 功能：根据传来的条件，返回下拉列表
     * @param JJR JJR
     * @return SQLModel SQL Model
     */
    public SQLModel getYear(String JJR){
    	SQLModel sqlModel = new SQLModel();
    	String sqlStr = "SELECT "
    					+ " YEAR"
    					+ " FROM"
    					+ " SF_HOLIDAYS"
    					+ " WHERE"
    					+ " NAME"
    					+ " LIKE '" + JJR+"'";
    	sqlModel.setSqlStr(sqlStr);
    	return sqlModel;
    }
    
    /**
     * 功能：根据传来的条件，返回下拉列表
     * @param JJR JJR
     * @param Year year
     * @return SQLModel SQL Model
     */
    public SQLModel getRQ(String JJR,String Year){
    	SQLModel sqlModel = new SQLModel();
    	String sqlStr = "SELECT "
    					+ " HOLIDAYS"
    					+ " FROM"
    					+ " SF_HOLIDAYS"
    					+ " WHERE"
    					+ " NAME LIKE '" + JJR
    					+ "' AND"
    					+ " YEAR = " + Year;
    	sqlModel.setSqlStr(sqlStr);
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
        		+ " SF_HOLIDAYS"
        		+ " WHERE"
        		+ " HOLIDAYS_ID IN("+str+")";
        sqlModel.setSqlStr(sqlStr);        
        return sqlModel;
    }

    /**
     * 功能：返回节假日列表,年份大于或等于传入的年份並且名称等于传入的名称
     * @param name 假期名称
     * @param year 年份
     * @return SQLModel SQL Model
     */
    public SQLModel getHolidaysAfter(String name, int year) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
//        SfHolidaysDTO sfHolidays = (SfHolidaysDTO)dtoParameter;
            String sqlStr = "SELECT *"
            + " FROM"
            + " SF_HOLIDAYS"
            + " WHERE NAME = ?";

        sqlArgs.add(name);
        if(year > 0) {
            sqlStr += " AND YEAR >= ?";
            sqlArgs.add(String.valueOf(year));
        }

        sqlStr += " ORDER BY YEAR";

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}