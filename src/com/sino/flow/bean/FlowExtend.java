package com.sino.flow.bean;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.flow.dto.FlowExtendDTO;

/**
 * Created by wwb.
 * User: V-wangwenbin
 * Date: 2007-11-6
 * Time: 14:56:33
 * 流程的扩展方法，像流程某一节点办理人的职责划分等。
 * 流程转向的主要方法在FlowBusiness类里
 */
public class FlowExtend {
    private Connection conn;
    private FlowExtendDTO dto;

    //需要调用有参数的方法，用此构造器
    public FlowExtend(Connection conn, FlowExtendDTO dto) {
        this.conn = conn;
        this.dto = dto;
    }

    //调用无参数的方法，调用此构造器
    public FlowExtend(Connection conn) {
        this.conn = conn;
    }

    //某一节点如果有多个办理人，一般会按职责划分到具体的人，
    //本方法就是取具体哪个人，
    public String getAppointPerson() throws SQLException {
        int userId;
        CallableStatement cStmt = null;
        try {
            String sql = "{? = call SF_EXTEND_PKG.GET_APPOINT_PERSON(?,?,?)}";
            cStmt = conn.prepareCall(sql);
            cStmt.registerOutParameter(1, Types.INTEGER); //返回userId
            cStmt.setString(2, dto.getKey());//用来比较的关键值
            cStmt.setString(3, dto.getOrgId());//有些流程，不同的OU，是不同的，这个时候，需要传入OU
            cStmt.setString(4, dto.getAppointType());//指定哪种类型
            cStmt.execute();
            userId = cStmt.getInt(1);
        } finally {
            DBManager.closeDBStatement(cStmt);
        }
        return String.valueOf(userId);
    }

    /**
     * 根据应用主键和表名获取actId
     * @param appId     应用主键
     * @param tableName 表名
     * @return String
     * @throws QueryException
     */
    public String getActId(String appId, String tableName) throws QueryException {
        String actId = "";
        String sqlStr = "SELECT SAC.ACTID\n" +
                "  FROM SF_APPROVE_CONTENT SAC, SF_PROCEDURE_DEF SPD\n" +
                " WHERE SAC.PROC_ID = SPD.PROC_ID\n" +
                "   AND SAC.APPLY_ID = ?\n" +
                "   AND SPD.APP_TABLE_NAME = ?";
        SQLModel sqlModel = new SQLModel();
        List argList = new ArrayList();
        argList.add(appId);
        argList.add(tableName);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(argList);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        try {
            actId = sq.getFirstRow().getValue("ACTID").toString();
        } catch (ContainerException e) {
            Logger.logError("获取actId出错!" + e);
        }
        return actId;
    }
}
