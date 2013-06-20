package com.sino.ams.net.equip.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.net.equip.dto.IntegratedDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;

/**
 * Created by IntelliJ IDEA.
 * User: Owner
 * Date: 2008-1-28
 * Time: 11:46:19
 * To change this template use File | Settings | File Templates.
 */
public class IntegratedModel extends AMSSQLProducer {

    /**
     * 功能：未匹配资产清单 数据库SQL构造层构造函数
     *
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsHouseInfoDTO 本次操作的数据
     */
    public IntegratedModel(SfUserDTO userAccount,IntegratedDTO dtoParameter) {
        super(userAccount, dtoParameter);

    }

    public SQLModel getDataCreateModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        IntegratedDTO dto = (IntegratedDTO) dtoParameter;
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
//        try {
//            FileUtil.appendStrContent(sqlModel.toString(), "C:\\cust.sql");
//            FileUtil.appendStrContent(WorldConstant.ENTER_CHAR, "C:\\cust.sql");
//        } catch (FileException ex) {
//        }
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
