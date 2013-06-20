package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.newasset.dto.AmsAssetsCommQueryDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;

/**
 * <p>Title: AmsAssetsCommQueryModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsAssetsCommQueryModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class CustomQuerySetModel extends AMSSQLProducer {

    /**
     * 功能：资产综合查询设置 AMS_ASSETS_COMM_QUERY 数据库SQL构造层构造函数
     * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsAssetsCommQueryDTO 本次操作的数据
     */
    public CustomQuerySetModel(SfUserDTO userAccount,
                               AmsAssetsCommQueryDTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    /**
     * 功能：框架自动生成资产综合查询设置 AMS_ASSETS_COMM_QUERY数据插入SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据插入用SQLModel
     */
    public SQLModel getDataCreateModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsAssetsCommQueryDTO dto = (AmsAssetsCommQueryDTO) dtoParameter;
        String sqlStr = "INSERT INTO "
                        + " AMS_ASSETS_COMM_QUERY("
                        + " USER_ID,"
                        + " FIELD_NAME,"
                        + " FIELD_DESC,"
                        + " FIELD_USAGE,"
                        + " SORT_NO"
                        + ") VALUES ("
                        + " ?, ?, ?, ?, ?)";
        sqlArgs.add(dto.getUserId());
        sqlArgs.add(dto.getFieldName());
        sqlArgs.add(dto.getFieldDesc());
        sqlArgs.add(dto.getFieldUsage());
        sqlArgs.add(dto.getSortNo());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成资产综合查询设置 AMS_ASSETS_COMM_QUERY数据删除SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据删除用SQLModel
     */
    public SQLModel getDeleteFieldsByUserIdModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM"
                        + " AMS_ASSETS_COMM_QUERY"
                        + " WHERE"
                        + " USER_ID = ?";
        sqlArgs.add(userAccount.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：获取用户自定义的字段列表
     * @param fieldUsage String
     * @return SQLModel
     */
    public SQLModel getFieldsByUserIdModel(String fieldUsage) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT "
                        + " *"
                        + " FROM"
                        + " AMS_ASSETS_COMM_QUERY"
                        + " WHERE"
                        + " USER_ID = ?"
                        + " AND FIELD_USAGE = ?"
                        + " ORDER BY"
                        + " SORT_NO";
        sqlArgs.add(userAccount.getUserId());
        sqlArgs.add(fieldUsage);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
