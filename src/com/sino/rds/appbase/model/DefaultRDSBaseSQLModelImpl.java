package com.sino.rds.appbase.model;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.rds.appbase.RDSConstantConfigManager;

public class DefaultRDSBaseSQLModelImpl extends BaseSQLProducer implements RDSBaseSQLModel {//该类的存在是为了免去其他SQLProducer类处处定义用户对象

    public DefaultRDSBaseSQLModelImpl(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    public SQLModel getDataCreateModel() {
        String errorMsg = "尚未实现"
                + getClass().getName()
                + "的方法getDataCreateModel";
        throw new UnsupportedOperationException(errorMsg);
    }

    public SQLModel getDataUpdateModel()  {
        String errorMsg = "尚未实现"
                + getClass().getName()
                + "的方法getDataUpdateModel";
        throw new UnsupportedOperationException(errorMsg);
    }

    public SQLModel getDataDeleteModel()  {
        String errorMsg = "尚未实现"
                + getClass().getName()
                + "的方法getDataDeleteModel";
        throw new UnsupportedOperationException(errorMsg);
    }

    public SQLModel getDeleteByPrimaryKeyModel() {
        String errorMsg = "尚未实现"
                + getClass().getName()
                + "的方法getDeleteByPrimaryKeyModel";
        throw new UnsupportedOperationException(errorMsg);
    }

    public SQLModel getPrimaryKeyDataModel()  {
        String errorMsg = "尚未实现"
                + getClass().getName()
                + "的方法getPrimaryKeyDataModel";
        throw new UnsupportedOperationException(errorMsg);
    }

    public SQLModel getDataByForeignKeyModel(String foreignKey)  {
        String errorMsg = "尚未实现"
                + getClass().getName()
                + "的方法getDataByForeignKeyModel";
        throw new UnsupportedOperationException(errorMsg);
    }

    public SQLModel getDeleteByForeignKeyModel(String foreignKey)  {
        String errorMsg = "尚未实现"
                + getClass().getName()
                + "的方法getDeleteByForeignKeyModel";
        throw new UnsupportedOperationException(errorMsg);
    }

    public SQLModel getMuxDataModel()  {
        String errorMsg = "尚未实现"
                + getClass().getName()
                + "的方法getMuxDataModel";
        throw new UnsupportedOperationException(errorMsg);
    }


    public SQLModel getPageQueryModel()  {
        String errorMsg = "尚未实现"
                + getClass().getName()
                + "的方法getPageQueryModel";
        throw new UnsupportedOperationException(errorMsg);
    }

    protected String getUserId(){
        return RDSConstantConfigManager.getUserId(userAccount);
    }
}
