package com.sino.rds.appbase.model;

import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.db.sql.model.SQLModel;

public interface RDSBaseSQLModel extends CalendarConstant {

    /**
     * 功能：构造数据插入SQL
     * @return 返回数据插入SQL模型对象
     */
    SQLModel getDataCreateModel();

    /**
     * 功能：构造数据更新SQL
     * @return 返回数据更新SQL模型对象
     */
    SQLModel getDataUpdateModel();

    /**
     * 功能：构造数据删除SQL
     * @return 返回数据删除SQL模型对象
     */
    SQLModel getDataDeleteModel();

    /**
     * 功能：构造根据主键删除数据SQL
     * @return 返回根据主键删除数据SQL模型对象
     */
    SQLModel getDeleteByPrimaryKeyModel();

    /**
     * 功能：构造根据主键查询数据SQL
     * @return 返回根据主键查询数据SQL模型对象
     */
    SQLModel getPrimaryKeyDataModel();

    /**
     * 功能：构造根据外键查询数据的SQL
     * @param foreignKey 外键名称。此处是DTO属性名
     * @return 返回根据外键查询数据的SQL
     */
    SQLModel getDataByForeignKeyModel(String foreignKey);

    /**
     * 功能：构造根据外键删除数据的SQL
     * @param foreignKey 外键名称。此处是DTO属性名
     * @return 返回根据外键删除数据的SQL
     */
    SQLModel getDeleteByForeignKeyModel(String foreignKey);

    /**
     * 功能：构造根据组合条件查询数据SQL
     * @return 返回根据组合条件查询数据SQL模型对象
     */
    SQLModel getMuxDataModel();

    /**
     * 功能：构造根据页面输入条件查询数据的SQL，主要用于页面的分页查询，有时也作为数据导出SQL使用
     * @return 返回构造根据页面输入条件查询数据的SQL模型对象
     */
    SQLModel getPageQueryModel();
}
