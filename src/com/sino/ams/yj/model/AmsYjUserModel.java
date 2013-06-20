package com.sino.ams.yj.model;


import java.util.*;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.math.AdvancedNumber;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.*;
import com.sino.ams.yj.dto.AmsYjUserDTO;


/**
 * <p>Title: AmsYjUserModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsYjUserModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * User: wangzp
 * Date: 2011-09-20
 * Function:应急管理-应急保障人员维护
 */


public class AmsYjUserModel extends BaseSQLProducer {

    private SfUserDTO sfUser = null;

    /**
     * 功能：应急通信保障队伍表 AMS_YJ_USER 数据库SQL构造层构造函数
     *
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsYjUserDTO 本次操作的数据
     */
    public AmsYjUserModel(SfUserDTO userAccount, AmsYjUserDTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
    }

    /**
     * 功能：框架自动生成应急通信保障队伍表 AMS_YJ_USER数据插入SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据插入用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getDataCreateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsYjUserDTO amsYjUser = (AmsYjUserDTO) dtoParameter;
        String sqlStr = "INSERT INTO "
                + " AMS_YJ_USER("
                + " TEAM_ID,"
                + " USER_NAME,"
                + " POST,"
                + " TEL,"
                + " CATEGORY,"
                + " ATTRIBUTE,"
                + " REMARK,"
                + " ORGANIZATION_ID,"
                + " CREATION_DATE,"
                + " CREATE_USER,"
                + " USER_ID"
                + ") VALUES ("
                + " CONVERT(FLOAT,?), ?, ?, ?, ?, ?, ?, ?, GETDATE(), ?,CONVERT(FLOAT,?))";

	        sqlArgs.add(amsYjUser.getTeamId());
	        sqlArgs.add(amsYjUser.getUserName());
	        sqlArgs.add(amsYjUser.getPost());
	        sqlArgs.add(amsYjUser.getTel());
	        sqlArgs.add(amsYjUser.getCategory());
	        sqlArgs.add(amsYjUser.getAttribute());
	        sqlArgs.add(amsYjUser.getRemark());
	        sqlArgs.add(amsYjUser.getOrganizationId());
	        sqlArgs.add(sfUser.getUserId());
	        sqlArgs.add(amsYjUser.getUserId() );
	        sqlModel.setSqlStr(sqlStr);
	        sqlModel.setArgs(sqlArgs);
	        return sqlModel;
    }

    /**
     * 功能：框架自动生成应急通信保障队伍表 AMS_YJ_USER数据更新SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据更新用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getDataUpdateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsYjUserDTO amsYjUser = (AmsYjUserDTO) dtoParameter;
        String sqlStr = "UPDATE AMS_YJ_USER"
                + " SET"
                + " TEAM_ID = CONVERT(FLOAT,?),"
                + " USER_NAME = ?,"
                + " POST = ?,"
                + " TEL = ?,"
                + " CATEGORY = ?,"
                + " ATTRIBUTE = ?,"
                + " REMARK = ?,"
                + " LAST_UPDATE_DATE = GETDATE(),"
                + " LAST_UPDATE_USER = ?"
                + " WHERE"
                + " USER_ID = CONVERT(FLOAT,?)";
        sqlArgs.add(amsYjUser.getTeamId());
        sqlArgs.add(amsYjUser.getUserName());
        sqlArgs.add(amsYjUser.getPost());
        sqlArgs.add(amsYjUser.getTel());
        sqlArgs.add(amsYjUser.getCategory());
        sqlArgs.add(amsYjUser.getAttribute());
        sqlArgs.add(amsYjUser.getRemark());
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(amsYjUser.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成应急通信保障队伍表 AMS_YJ_USER数据删除SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据删除用SQLModel
     */
    public SQLModel getDataDeleteModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsYjUserDTO amsYjUser = (AmsYjUserDTO) dtoParameter;
        String sqlStr = "DELETE FROM"
                + " AMS_YJ_USER"
                + " WHERE"
                + " USER_ID =CONVERT(FLOAT,?)";

        sqlArgs.add(amsYjUser.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成应急通信保障队伍表 AMS_YJ_USER数据详细信息查询SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsYjUserDTO amsYjUser = (AmsYjUserDTO) dtoParameter;
        String sqlStr = "SELECT "
                + " TEAM_ID,"
                + " USER_NAME,"
                + " POST,"
                + " TEL,"
                + " CATEGORY,"
                + " ATTRIBUTE,"
                + " REMARK,"
                + " ORGANIZATION_ID,"
                + " CREATION_DATE,"
                + " CREATE_USER,"
                + " LAST_UPDATE_DATE,"
                + " LAST_UPDATE_USER,"
                + " USER_ID"
                + " FROM"
                + " AMS_YJ_USER"
                + " WHERE"
                + " USER_ID = CONVERT(FLOAT,?)";

        sqlArgs.add(amsYjUser.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成应急通信保障队伍表 AMS_YJ_USER多条数据信息查询SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回多条数据信息查询用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getMuxDataModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            AmsYjUserDTO amsYjUser = (AmsYjUserDTO) dtoParameter;
            String sqlStr = "SELECT "
                    + " TEAM_ID,"
                    + " USER_NAME,"
                    + " POST,"
                    + " TEL,"
                    + " CATEGORY,"
                    + " ATTRIBUTE,"
                    + " ORGANIZATION_ID,"
                    + " CREATION_DATE,"
                    + " CREATE_USER,"
                    + " LAST_UPDATE_DATE,"
                    + " LAST_UPDATE_USER,"
                    + " USER_ID"
                    + " FROM"
                    + " AMS_YJ_USER"
                    + " WHERE"
                    + " (? IS NULL OR TEAM_ID LIKE ?)"
                    + " AND (? IS NULL OR USER_NAME LIKE ?)"
                    + " AND (? IS NULL OR POST LIKE ?)"
                    + " AND (? IS NULL OR TEL LIKE ?)"
                    + " AND (? IS NULL OR CATEGORY LIKE ?)"
                    + " AND (? IS NULL OR ATTRIBUTE LIKE ?)"
                    + " AND (? IS NULL OR ORGANIZATION_ID LIKE ?)"
                    + " AND (? IS NULL OR CREATION_DATE LIKE ?)"
                    + " AND (? IS NULL OR CREATE_USER LIKE ?)"
                    + " AND (? IS NULL OR LAST_UPDATE_DATE LIKE ?)"
                    + " AND (? IS NULL OR LAST_UPDATE_USER LIKE ?)"
                    + " AND (? IS NULL OR USER_ID LIKE ?)";
            sqlArgs.add(amsYjUser.getTeamId());
            sqlArgs.add(amsYjUser.getTeamId());
            sqlArgs.add(amsYjUser.getUserName());
            sqlArgs.add(amsYjUser.getUserName());
            sqlArgs.add(amsYjUser.getPost());
            sqlArgs.add(amsYjUser.getPost());
            sqlArgs.add(amsYjUser.getTel());
            sqlArgs.add(amsYjUser.getTel());
            sqlArgs.add(amsYjUser.getCategory());
            sqlArgs.add(amsYjUser.getCategory());
            sqlArgs.add(amsYjUser.getAttribute());
            sqlArgs.add(amsYjUser.getAttribute());
            sqlArgs.add(amsYjUser.getOrganizationId());
            sqlArgs.add(amsYjUser.getOrganizationId());
            sqlArgs.add(amsYjUser.getCreationDate());
            sqlArgs.add(amsYjUser.getCreationDate());
            sqlArgs.add(amsYjUser.getCreateUser());
            sqlArgs.add(amsYjUser.getCreateUser());
            sqlArgs.add(amsYjUser.getLastUpdateDate());
            sqlArgs.add(amsYjUser.getLastUpdateDate());
            sqlArgs.add(amsYjUser.getLastUpdateUser());
            sqlArgs.add(amsYjUser.getLastUpdateUser());
            sqlArgs.add(amsYjUser.getUserId());
            sqlArgs.add(amsYjUser.getUserId());

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
        }
        return sqlModel;
    }

    /**
     * 功能：根据外键关联字段 teamId 构造查询数据SQL。
     * 框架自动生成数据应急通信保障队伍表 AMS_YJ_USER详细信息查询SQLModel，请根据实际需要修改。
     *
     * @param teamId AdvancedNumber
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    private SQLModel getDataByTeamIdModel(String teamId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT "
                + " USER_NAME,"
                + " POST,"
                + " TEL,"
                + " CATEGORY,"
                + " ATTRIBUTE,"
                + " REMARK,"
                + " ORGANIZATION_ID,"
                + " CREATION_DATE,"
                + " CREATE_USER,"
                + " LAST_UPDATE_DATE,"
                + " LAST_UPDATE_USER,"
                + " USER_ID"
                + " FROM"
                + " AMS_YJ_USER"
                + " WHERE"
                + " TEAM_ID = CONVERT(FLOAT,?)";
        sqlArgs.add(teamId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：根据外键获取数据
     *
     * @param foreignKey 传入的外键字段名称。
     * @return SQLModel
     */
    public SQLModel getDataByForeignKeyModel(String foreignKey) {
        SQLModel sqlModel = null;
        AmsYjUserDTO amsYjUser = (AmsYjUserDTO) dtoParameter;
        if (foreignKey.equals("teamId")) {
            sqlModel = getDataByTeamIdModel(amsYjUser.getTeamId());
        }
        return sqlModel;
    }

    /**
     * 功能：根据外键关联字段 teamId 构造数据删除SQL。
     * 框架自动生成数据应急通信保障队伍表 AMS_YJ_USER数据删除SQLModel，请根据实际需要修改。
     *
     * @param teamId AdvancedNumber
     * @return SQLModel 返回数据删除用SQLModel
     */
    private SQLModel getDeleteByTeamIdModel(String teamId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE "
                + " USER_NAME,"
                + " POST,"
                + " TEL,"
                + " CATEGORY,"
                + " ATTRIBUTE,"
                + " ORGANIZATION_ID,"
                + " CREATION_DATE,"
                + " CREATE_USER,"
                + " LAST_UPDATE_DATE,"
                + " LAST_UPDATE_USER"
                + " FROM"
                + " AMS_YJ_USER"
                + " WHERE"
                + " TEAM_ID = CONVERT(FLOAT,?)";
        sqlArgs.add(teamId);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：根据外键字段删除数据
     *
     * @param foreignKey 传入的外键字段名称。
     * @return SQLModel
     */
    public SQLModel getDeleteByForeignKeyModel(String foreignKey) {
        SQLModel sqlModel = null;
        AmsYjUserDTO amsYjUser = (AmsYjUserDTO) dtoParameter;
        if (foreignKey.equals("teamId")) {
            sqlModel = getDeleteByTeamIdModel(amsYjUser.getTeamId());
        }
        return sqlModel;
    }

    /**
     * 功能：框架自动生成应急通信保障队伍表 AMS_YJ_USER页面翻页查询SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回页面翻页查询SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsYjUserDTO amsYjUser = (AmsYjUserDTO) dtoParameter;
        String sqlStr = "SELECT "
                + " dbo.APP_GET_ORGNIZATION_NAME(AYU.ORGANIZATION_ID) ORGANIZATION_NAME,"
                + " AYU.TEAM_ID,"
                + " AYT.TEAM_NAME,"
                + " AYT.RESPONSIBILITY_USER,"
                + " AYT.TEL TEL1,"
                + " AYT.SITUATION,"
                + " AYU.USER_NAME,"
                + " AYU.POST,"
                + " AYU.TEL,"
                + " AYU.CATEGORY,"
                + " AYU.ATTRIBUTE,"
                + " AYU.REMARK,"
                + " AYU.CREATION_DATE,"
                + " dbo.APP_GET_USER_NAME(AYU.CREATE_USER) CREATE_USER,"
                + " AYU.LAST_UPDATE_DATE,"
                + " dbo.APP_GET_USER_NAME(AYU.LAST_UPDATE_USER) LAST_UPDATE_USER,"
                + " AYU.USER_ID"
                + " FROM"
                + " AMS_YJ_USER AYU,"
                + " AMS_YJ_TEAM AYT"
                + " WHERE"
                + " AYT.TEAM_ID=AYU.TEAM_ID"
                + " AND ("+ SyBaseSQLUtil.isNull() +" OR AYT.TEAM_NAME LIKE ?)"
                + " AND ("+ SyBaseSQLUtil.isNull() +" OR AYU.USER_NAME LIKE ?)"
                + " AND (? =-1 OR AYU.ORGANIZATION_ID= ?)" ;

        sqlArgs.add(amsYjUser.getTeamName());
        sqlArgs.add(amsYjUser.getTeamName());
        sqlArgs.add(amsYjUser.getUserName());
        sqlArgs.add(amsYjUser.getUserName());
        sqlArgs.add(amsYjUser.getOrganizationId());
        sqlArgs.add(amsYjUser.getOrganizationId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
	}

}