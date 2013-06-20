package com.sino.appbase.help.bean;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.QueryException;
import com.sino.base.util.StrUtil;

/**
 * <p>Title: SinoIES</p>
 * <p>Description: 河南移动IES系统</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class HelpIDProducer {

    private HelpIDProducer() {
    }

    public static synchronized String getResourceId(String resParId, Connection conn) throws QueryException{
        String resourceId = "";
        SQLModel sqlModel = getSQLModel(resParId);
        try {
            SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
            simpleQuery.executeQuery();
            RowSet rows = simpleQuery.getSearchResult();
            if (!rows.isEmpty()) {
                resourceId = rows.getRow(0).getStrValue("RES_ID");
            }
        } catch (ContainerException ex) {
            ex.printLog();
            throw new QueryException(ex.getMessage());
        }
        return resourceId;
    }

    private static SQLModel getSQLModel(String resParId){
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "";
        List sqlArgs = new ArrayList();
        if(StrUtil.isEmpty(resParId)){
        	  sqlStr = "SELECT IES_PUBLIC_FNC.GET_NEXT_RESID(NULL) RES_ID  ";
        } else {
            sqlStr = "SELECT IES_PUBLIC_FNC.GET_NEXT_RESID(?) RES_ID  ";
            sqlArgs.add(resParId);
        }
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
