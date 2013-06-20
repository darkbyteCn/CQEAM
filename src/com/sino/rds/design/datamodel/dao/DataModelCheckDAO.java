package com.sino.rds.design.datamodel.dao;

import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.dao.RDSBaseDAO;
import com.sino.rds.design.datamodel.model.DataModelCheckModel;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.sql.Connection;

public class DataModelCheckDAO extends RDSBaseDAO {

    public DataModelCheckDAO(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    /**
     * 功能：检查SQL查询语句是否合法。
     * 检查方法：以SQL查询语句创建视图，如果创建失败则不合法，否则合法
     * @return 创建视图失败时返回错误信息，否则返回空
     */
    public String checkModelSQL() {
        String errorMessage = "";
        try {
            DataModelCheckModel modelProducer = (DataModelCheckModel) sqlProducer;
            SQLModel sqlModel = modelProducer.getModelCheckModel();
            SimpleQuery sq = new SimpleQuery(sqlModel, conn);
            sq.executeQuery();
        } catch (Throwable ex) {
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            ex.printStackTrace(new PrintWriter(buf, true));
            errorMessage = buf.toString();
        }
        return errorMessage;
    }
}
