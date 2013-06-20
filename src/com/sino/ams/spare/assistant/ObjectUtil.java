package com.sino.ams.spare.assistant;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.QueryException;

/**
 * <p>Title: SinoAMS</p>
 * <p>Description: </p>
 * <p>Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2007</p>
 * <p>Company: 北京思诺搏信息技术有限公司</p>
 * @author 贾龙川
 * @version 0.1
 *          Date: 2008-3-17
 */
public class ObjectUtil {

    /**
     * 根据专业类型和OU获取OJBECT_NO,只适用于待修库,送修库等每个地市只有一个的情况
     * @param objectCategory 地点类型
     * @param organizationId 组织ID
     * @param conn           连接
     * @return objectNo
     * @throws com.sino.base.exception.QueryException
     * @throws com.sino.base.exception.ContainerException
     */
    public static String getObjectNo(String objectCategory, int organizationId, Connection conn) throws QueryException, ContainerException {
        String objectNo = "";
        String sqlStr = "SELECT EO.WORKORDER_OBJECT_NO\n" +
                "  FROM ETS_OBJECT EO\n" +
                " WHERE EO.OBJECT_CATEGORY = ?\n" +
                "   AND EO.ORGANIZATION_ID = ?";
        List argList = new ArrayList();
        argList.add(objectCategory);
        argList.add(organizationId);
        SQLModel sqlModel = new SQLModel();
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(argList);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        objectNo = sq.getFirstRow().getValue("WORKORDER_OBJECT_NO").toString();
        return objectNo;

    }
}
