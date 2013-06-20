package com.sino.ams.system.item.assistant;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.fixing.dto.EtsItemInfoDTO;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.util.StrUtil;

/**
 * User: zhoujs
 * Date: 2007-12-11
 * Time: 12:01:54
 * Function: 检查item_code，如果没有则自动创建
 */
public class SystemItemUtil {
    public EtsItemInfoDTO checkSysItem(Connection conn, EtsItemInfoDTO etsItemInfo) throws QueryException, ContainerException, SQLException, DataHandleException {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "";
        if (etsItemInfo.getItemCode().equals("")) {
            String itemCode = hasSystemItem(conn, etsItemInfo);
            if (itemCode.equals("")) {
                //无此设备分类，需要新加入此设备分类，并标记为临时
                SeqProducer sq = new SeqProducer(conn);
                String code = sq.getGUID();
                sqlModel = getInsertSystemModel(code, etsItemInfo);
                DBOperator.updateRecord(sqlModel, conn);
                sqlModel = getInsertDistributeModel(code, etsItemInfo.getOrganizationId());
                DBOperator.updateRecord(sqlModel, conn);
                etsItemInfo.setItemCode(StrUtil.nullToString(code));
            } else {
                if (hasDistribute(conn, itemCode, etsItemInfo.getOrganizationId())) {
                    etsItemInfo.setItemCode(itemCode);
                } else {
                    sqlModel = getInsertDistributeModel(itemCode, etsItemInfo.getOrganizationId());
                    DBOperator.updateRecord(sqlModel, conn);
                    etsItemInfo.setItemCode(itemCode);
                }
            }
        }

        return etsItemInfo;
    }

    private String hasSystemItem(Connection conn, EtsItemInfoDTO etsItemInfo) throws QueryException, ContainerException {
        String itemCode = "";
        String sqlStr = "SELECT *\n" +
                "  FROM ETS_SYSTEM_ITEM ESI\n" +
                " WHERE ESI.ITEM_NAME = ?\n" +
                "   AND ESI.ITEM_CATEGORY = ?\n";
        if (StrUtil.isEmpty(etsItemInfo.getItemSpec())) {
            sqlStr += " AND ESI.ITEM_SPEC " + SyBaseSQLUtil.isNullNoParam() + " ";
        } else {
            sqlStr += " AND ESI.ITEM_SPEC=?";
        }
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        sqlArgs.add(etsItemInfo.getItemName());
        sqlArgs.add(etsItemInfo.getItemCategory());
        if (StrUtil.isNotEmpty(etsItemInfo.getItemSpec())) {
            sqlArgs.add(etsItemInfo.getItemSpec());
        }
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        if (simpleQuery.hasResult()) {
            itemCode = simpleQuery.getFirstRow().getStrValue("ITEM_CODE");
        }

        return itemCode;
    }

    private SQLModel getInsertSystemModel(String itemCode, EtsItemInfoDTO etsItemInfo) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO ETS_SYSTEM_ITEM\n" +
                "  (ITEM_CODE,\n" +
                "   ITEM_NAME,\n" +
                "   ITEM_SPEC,\n" +
                "   ITEM_CATEGORY,\n" +
                "   IS_TMP_CODE,\n" +
                "   MASTER_ORGANIZATION_ID)\n" +
                "VALUES\n" +
                "  (?,?,?,?,?,?)";
        sqlArgs.add(itemCode);
        sqlArgs.add(etsItemInfo.getItemName());
        sqlArgs.add(etsItemInfo.getItemSpec());
        sqlArgs.add(etsItemInfo.getItemCategory());
        sqlArgs.add("Y");
        sqlArgs.add(etsItemInfo.getOrganizationId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }


    /**
     * 插入分配表，标记为临时
     * @param itemCode
     * @param organizationId
     * @return
     */
    public SQLModel getInsertDistributeModel(String itemCode, int organizationId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO ETS_SYSITEM_DISTRIBUTE\n" +
                "  (SYSTEM_ID, ITEM_CODE, ORGANIZATION_ID, IS_TMP)\n" +
                " VALUES\n" +
                "  (NEWID(), ?, ?, ?)";
        sqlArgs.add(itemCode);
        sqlArgs.add(organizationId);
        sqlArgs.add("Y");

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }

    private boolean hasDistribute(Connection conn, String itemCode, int organizationId) throws QueryException {
        boolean hasRecord = false;
        String sqlStr = "SELECT *\n" +
                "  FROM ETS_SYSITEM_DISTRIBUTE ESD\n" +
                " WHERE ESD.ITEM_CODE = ?\n" +
                "   AND ESD.ORGANIZATION_ID = ?";

        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        sqlArgs.add(itemCode);
        sqlArgs.add(organizationId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        if (simpleQuery.hasResult()) {
            hasRecord = true;
        }
        return hasRecord;
    }

}
