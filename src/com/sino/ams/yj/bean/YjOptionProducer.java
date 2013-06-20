package com.sino.ams.yj.bean;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.QueryException;
import com.sino.base.web.DatabaseForWeb;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;


public class YjOptionProducer extends OptionProducer {
    public YjOptionProducer(SfUserDTO userAccount, Connection conn) {
        super(userAccount, conn);
    }

    public String getEquipmentOpt(String selectValue, boolean addBlank) throws QueryException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT ITEM_NAME ITEM_CODE, ITEM_NAME FROM AMS_YJ_ITEM";

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
        return webFieldProducer.getOptionHtml(selectValue, addBlank);
    }
}