package com.sino.sinoflow.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.sinoflow.dto.SfEnoteDTO;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;


/**
 * <p>Title: SfEnoteModel</p>
 * <p>Description:程序自动生成SQL构造器“SfEnoteModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author huangzhaorong
 * @version 1.0
 */


public class SfEnoteModel extends SFSQLProducer {

	/**
	 * 功能：催办箱信息表 SF_ENOTE 数据库SQL构造层构造函数
	 * @param userAccount SfUserBaseDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter SfEnoteDTO 本次操作的数据
	 */
	public SfEnoteModel(SfUserBaseDTO userAccount, SfEnoteDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：框架自动生成催办箱信息表 SF_ENOTE数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataCreateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			SfEnoteDTO sfEnote = (SfEnoteDTO)dtoParameter;
			String sqlStr = "INSERT INTO "
				+ " SF_ENOTE("
				+ " ACT_ID,"
				+ " FROM_USER_ID,"
				+ " TO_USER_ID,"
				+ " FROM_DATE,"
				+ " URL,"
				+ " MSG,"
				+ " READ,"
                + " ENABLED"
                + ") VALUES ("
				+ " ?, ?, ?, ?, ?, ?, ?, ?)";
		
			sqlArgs.add(sfEnote.getActId());
			sqlArgs.add(sfEnote.getFromUserId());
			sqlArgs.add(sfEnote.getToUserId());
			sqlArgs.add(sfEnote.getFromDate());
			sqlArgs.add(sfEnote.getUrl());
			sqlArgs.add(sfEnote.getMsg());
			sqlArgs.add(sfEnote.getRead());
            sqlArgs.add(sfEnote.getEnabled());

            sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成催办箱信息表 SF_ENOTE数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据更新用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataUpdateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();

			List sqlArgs = new ArrayList();
			SfEnoteDTO sfEnote = (SfEnoteDTO)dtoParameter;
			String sqlStr = "UPDATE SF_ENOTE"
				+ " SET"
				+ " READ = 1,"
                + " ENABLED = ?"
                + " WHERE"
				+ " ENOTE_ID = ?";

            sqlArgs.add(sfEnote.getEnabled());
            sqlArgs.add(sfEnote.getEnoteId());
		
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成催办箱信息表 SF_ENOTE数据删除SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfEnoteDTO sfEnote = (SfEnoteDTO)dtoParameter;
		String sqlStr = "DELETE FROM"
				+ " SF_ENOTE"
				+ " WHERE"
				+ " ENOTE_ID = ?";
			sqlArgs.add(sfEnote.getEnoteId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成催办箱信息表 SF_ENOTE数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfEnoteDTO sfEnote = (SfEnoteDTO)dtoParameter;
		String sqlStr = "SELECT "
			+ " SE.ENOTE_ID,"
			+ " SE.ACT_ID,"
			+ " SE.FROM_USER_ID,"
			+ " SE.TO_USER_ID,"
			+ " SE.FROM_DATE,"
			+ " SE.URL,"
//			+ " xf_public.GET_USER_NAME(SE.TO_USER_ID) TO_USER_NAME,"
//			+ " xf_public.GET_USER_NAME(SE.FROM_USER_ID) FROM_USER_NAME,"
            + " SU1.USERNAME TO_USER_NAME,"
            + " SU2.USERNAME FROM_USER_NAME,"
			+ " SAI.SFACT_PROC_NAME,"
			+ " SAI.SFACT_APPL_MSG,"
			+ " SAI.SFACT_APPL_COLUMN_1,"
			+ " SAI.SFACT_APPL_COLUMN_2,"
			+ " SE.MSG,"
			+ " SE.READ,"
            + " SE.ENABLED"
            + " FROM"
			+ " SF_ENOTE SE,"
			+ " SF_ACT_INFO SAI,"
            + " SF_USER SU1,"
            + " SF_USER SU2"
            + " WHERE"
			+ " ( SE.ACT_ID = SAI.SFACT_ACT_ID )"
			+ " AND ( ENOTE_ID = ? )"
            + " AND SU1.USER_ID = SE.TO_USER_ID"
            + " AND SU2.USER_ID = SE.FROM_USER_ID";

        sqlModel.setSqlStr(sqlStr);
        sqlArgs.add(sfEnote.getEnoteId());
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成催办箱信息表 SF_ENOTE多条数据信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回多条数据信息查询用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getMuxDataModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			SfEnoteDTO sfEnote = (SfEnoteDTO)dtoParameter;
			String sqlStr = "SELECT "
				+ " ENOTE_ID,"
				+ " ACT_ID,"
				+ " FROM_USER_ID,"
				+ " TO_USER_ID,"
				+ " FROM_DATE,"
				+ " URL,"
				+ " MSG,"
				+ " READ,"
                + " ENABLED"
                + " FROM"
				+ " SF_ENOTE"
				+ " WHERE"
				+ " (? <= 0 OR ENOTE_ID = ?)"
				+ " AND (? = '' OR ACT_ID LIKE ?)"
				+ " AND (? <= 0 OR FROM_USER_ID = ?)"
				+ " AND (? <= 0 OR TO_USER_ID = ?)"
				+ " AND (? = '' OR FROM_DATE LIKE ?)"
				+ " AND (? = '' OR URL LIKE ?)"
				+ " AND (? = '' OR MSG LIKE ?)"
				+ " AND (? = '' OR READ LIKE ?)"
                + " AND (? = '' OR ENABLED LIKE ?)";
			sqlArgs.add(sfEnote.getEnoteId());
			sqlArgs.add(sfEnote.getEnoteId());
			sqlArgs.add(sfEnote.getActId());
			sqlArgs.add(sfEnote.getActId());
			sqlArgs.add(sfEnote.getFromUserId());
			sqlArgs.add(sfEnote.getFromUserId());
			sqlArgs.add(sfEnote.getToUserId());
			sqlArgs.add(sfEnote.getToUserId());
			sqlArgs.add(sfEnote.getFromDate());
			sqlArgs.add(sfEnote.getFromDate());
			sqlArgs.add(sfEnote.getUrl());
			sqlArgs.add(sfEnote.getUrl());
			sqlArgs.add(sfEnote.getMsg());
			sqlArgs.add(sfEnote.getMsg());
			sqlArgs.add(sfEnote.getRead());
			sqlArgs.add(sfEnote.getRead());
            sqlArgs.add(sfEnote.getEnabled());
            sqlArgs.add(sfEnote.getEnabled());

            sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成催办箱信息表 SF_ENOTE页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT "
            + " DISTINCT SE.ACT_ID,"
			+ " CONVERT(INT, SE.ENOTE_ID) ENOTE_ID,"
			+ " SE.FROM_DATE,"
//			+ " xf_public.GET_USER_NAME(SE.TO_USER_ID) TO_USER_NAME,"
//			+ " xf_public.GET_USER_NAME(SE.FROM_USER_ID) FROM_USER_NAME,"
            + " SU1.USERNAME TO_USER_NAME,"
            + " SU2.USERNAME FROM_USER_NAME,"
            + " SE.URL,"
			+ " SE.MSG," 
			+ " SAI.SFACT_PROC_NAME,"
			+ " SAI.SFACT_APPL_MSG,"
			+ " SAI.SFACT_APPL_COLUMN_1,"
			+ " SAI.SFACT_APPL_COLUMN_2,"
			+ " SAI.SFACT_DELIVERY_PRIORITY,"
			+ " SA.WINDOW_TYPE,"
			+ " SE.READ,"
            + " SE.ENABLED"
            + " FROM"
			+ " SF_ENOTE SE ," 
			+ " SF_ACT_INFO SAI,"
			+ " SF_APPLICATION SA,"
            + " SF_USER SU1,"
            + " SF_USER SU2"
            + " WHERE "
			+ " ( SE.ACT_ID = SAI.SFACT_ACT_ID )"
			+ " AND ( SAI.SFACT_APPDEF_ID = SA.APP_ID )"
//			+ " AND ( SE.READ = 0 )"
			+ " AND (? <= 0 OR SE.TO_USER_ID = ?)"
            + " AND SU1.USER_ID = SE.TO_USER_ID"
            + " AND SU2.USER_ID = SE.FROM_USER_ID"
            + " AND (SE.ENABLED = '' OR SE.ENABLED = 'Y')";

        sqlArgs.add(user.getUserId());
		sqlArgs.add(user.getUserId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

    public SQLModel setEnoteActsModel(String loginNames) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            SfEnoteDTO sfEnote = (SfEnoteDTO)dtoParameter;
            String sqlStr = "INSERT INTO "
                + " SF_ENOTE("
                + " ACT_ID,"
                + " FROM_USER_ID,"
                + " TO_USER_ID,"
                + " FROM_DATE,"
                + " URL,"
                + " MSG,"
                + " READ,"
                + " ENABLED"
                + ") VALUES ("
                + " ?, ?, ?, ?, ?, ?, ?, ?)";

            sqlArgs.add(sfEnote.getActId());
            sqlArgs.add(sfEnote.getFromUserId());
            sqlArgs.add(sfEnote.getToUserId());
            sqlArgs.add(sfEnote.getFromDate());
            sqlArgs.add(sfEnote.getUrl());
            sqlArgs.add(sfEnote.getMsg());
            sqlArgs.add(sfEnote.getRead());
            sqlArgs.add(sfEnote.getEnabled());

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
        }
        return sqlModel;
    }

    public SQLModel getNoticesModel(String URL, String keyword, String subject, String createby) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT "
            + " DISTINCT SE.ACT_ID SFACT_ACT_ID,"
            + " SE.ENOTE_ID ID,"
            + " SE.FROM_DATE SFACT_FROM_DATE,"
//			+ " xf_public.GET_USER_NAME(SE.TO_USER_ID) TO_USER_NAME,"
//			+ " xf_public.GET_USER_NAME(SE.FROM_USER_ID) FROM_USER_NAME,"
            + " SU1.USERNAME TO_USER_NAME,"
            + " SU2.USERNAME SFACT_FROM_TASK_USER,"
            + " (? + '?enoteId=' + SE.ENOTE_ID + '&action=DETAIL_ACTION') SFACT_URL,"
            + " SE.MSG SFACT_USER_MSG,"
            + " '催办' SFACT_PROC_NAME,"
            + " (SAI.SFACT_PROC_NAME + ' - ' + SAI.SFACT_APPL_MSG) SFACT_APPL_MSG,"
            + " SAI.SFACT_APPL_MSG SFACT_TASK_NAME,"
            + " SAI.SFACT_APPL_COLUMN_1,"
            + " SAI.SFACT_APPL_COLUMN_2,"
            + " SAI.SFACT_DELIVERY_PRIORITY,"
            + " SA.WINDOW_TYPE,"
            + " SE.READ,"
            + " SA.CATEGORY_NAME"    
            + " FROM"
            + " SF_ENOTE SE ,"
            + " SF_ACT_INFO SAI,"
            + " SF_APPLICATION SA,"
            + " SF_USER SU1,"
            + " SF_USER SU2"
            + " WHERE "
            + " ( SE.ACT_ID = SAI.SFACT_ACT_ID )"
            + " AND ( SAI.SFACT_APPDEF_ID = SA.APP_ID )"
//			+ " AND ( SE.READ = 0 )"
            + " AND (? <= 0 OR SE.TO_USER_ID = ?)"
            + " AND SU1.USER_ID = SE.TO_USER_ID"
            + " AND SU2.USER_ID = SE.FROM_USER_ID"
            + " AND (SE.ENABLED = '' OR SE.ENABLED = 'Y')"
            + " AND (? = '' OR SU2.USERNAME LIKE ?)"
            + " AND (? = '' OR SAI.SFACT_APPL_COLUMN_1 LIKE ?)"
            + " AND (? = '' OR SAI.SFACT_APPL_COLUMN_2 LIKE ?)";

        sqlArgs.add(URL);
        sqlArgs.add(user.getUserId());
        sqlArgs.add(user.getUserId());

        if(createby == null || createby.equals("")) {
            sqlArgs.add("");
            sqlArgs.add("");
        } else {
            sqlArgs.add("%" + createby + "%");
            sqlArgs.add("%" + createby + "%");
        }
        if(keyword == null || keyword.equals("")) {
            sqlArgs.add("");
            sqlArgs.add("");
        } else {
            sqlArgs.add("%" + keyword + "%");
            sqlArgs.add("%" + keyword + "%");
        }
        if(subject == null || subject.equals("")) {
            sqlArgs.add("");
            sqlArgs.add("");
        } else {
            sqlArgs.add("%" + subject + "%");
            sqlArgs.add("%" + subject + "%");
        }

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getNoticesCountModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT COUNT(1) COUNTNO"
            + " FROM"
            + " SF_ENOTE SE ,"
            + " SF_ACT_INFO SAI,"
            + " SF_APPLICATION SA,"
            + " SF_USER SU1,"
            + " SF_USER SU2"
            + " WHERE "
            + " ( SE.ACT_ID = SAI.SFACT_ACT_ID )"
            + " AND ( SAI.SFACT_APPDEF_ID = SA.APP_ID )"
//			+ " AND ( SE.READ = 0 )"
            + " AND (? <= 0 OR SE.TO_USER_ID = ?)"
            + " AND SU1.USER_ID = SE.TO_USER_ID"
            + " AND SU2.USER_ID = SE.FROM_USER_ID"
            + " AND (SE.ENABLED = '' OR SE.ENABLED = 'Y')";

        sqlArgs.add(user.getUserId());
        sqlArgs.add(user.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel setEnoteEnableModel(String enoteId, String enabled) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
//        SfActCopyDTO sfActCopy = (SfActCopyDTO)dtoParameter;
        String sqlStr = "UPDATE SF_ENOTE"
            + " SET"
            + " ENABLED = ?"
            + " WHERE ENOTE_ID = ?";

        sqlModel.setSqlStr(sqlStr);
        sqlArgs.add(enabled);
        sqlArgs.add(enoteId);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getMigratedModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
//        try {
//        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT * FROM "
            + " SF_ENOTE"
            + " WHERE"
            + " LAST_UPDATED > LAST_MIGRATED"
            + " OR LAST_UPDATED = ''";

        sqlModel.setSqlStr(sqlStr);
//        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel setMigratedModel(String id) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
//        try {
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE SF_ENOTE SET LAST_MIGRATED = GETDATE()"
            + " WHERE"
            + " ENOTE_ID = ?";

        sqlModel.setSqlStr(sqlStr);
        sqlArgs.add(id);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}