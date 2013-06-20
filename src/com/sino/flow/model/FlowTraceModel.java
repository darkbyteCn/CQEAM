package com.sino.flow.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.base.db.sql.model.SQLModel;

/**
 * Created by wwb.
 * User: demo
 * Date: 2006-12-24
 * Time: 17:10:23
 * 流程跟踪MODEL
 * 包含一些收件箱,发件箱,个人申请查询等
 */
public class FlowTraceModel {
    /**
     * 通过当前用户ID,查找当前用户有多少要处理的任务
     * @param currUserId 当前办理人
     * @return SQLModel
     */
    public static SQLModel getInboxCountModel(String currUserId) {
        String sql =
                "select COUNT(*) TOTAL from  SF_ACT_INFO WHERE SFACT_PICK_USER=?";
        ArrayList al = new ArrayList();
        al.add(currUserId);
        SQLModel sm = new SQLModel();
        sm.setArgs(al);
        sm.setSqlStr(sql);
        return sm;
    }

    /**
     * 取发件箱总数
     * @param currUserId 当前办理人
     * @return SQLModel
     */
    public static SQLModel getOutBoxCountModel(String currUserId) {
        String sql =
                "SELECT COUNT(*) TOTAL\n" +
                        "  FROM SF_ACT SA\n" +
                        " WHERE SA.FROM_USER_ID = ?";
        ArrayList al = new ArrayList();
        al.add(currUserId);
        SQLModel sm = new SQLModel();
        sm.setArgs(al);
        sm.setSqlStr(sql);
        return sm;
    }

    /**
     * 取个人申请总数
     * @param currUserId 当前办理人
     * @return SQLModel
     */
    public static SQLModel getPersonalModel(String currUserId) {
        String sql =
                "SELECT COUNT(*) TOTAL\n" +
                        "  FROM SF_ACT SA\n" +
                        " WHERE SA.CREATED_BY = ?";
        ArrayList al = new ArrayList();
        al.add(currUserId);
        SQLModel sm = new SQLModel();
        sm.setArgs(al);
        sm.setSqlStr(sql);
        return sm;
    }

    /**
     * 取所有申请总数
     * @return SQLModel
     */
    public static SQLModel getAllModel() {
        String sql =
                "SELECT COUNT(*) TOTAL\n" +
                        "  FROM SF_ACT SA";
        // ArrayList al=new ArrayList();
        SQLModel sm = new SQLModel();
        sm.setSqlStr(sql);
        return sm;
    }

    public static SQLModel getInboxModel(String userId, String applyNumber, String procName) {
        SQLModel sm = new SQLModel();
        String sqlStr = "";
        List al = new ArrayList();
        sqlStr = "SELECT" +
                "       SP.USERNAME,\n" +
                "       SP2.USERNAME FROM_USER,\n" +
                "       SA.FROM_DT FROM_TIME,\n" +
                "       SA.ACTID,\n" +
                "       SA.CUR_TASK_ID,\n" +
                "       SA.PROC_ID,\n" +
                "       SA.APP_ID,\n" +
                "       SA.APPLY_NUMBER,\n" +
                "       SA.CREATION_DATE,\n" +
                "       SF_FLOW_PKG.GET_FROM_TASK_USER(SA.CUR_TASK_ID, SA.ACTID) FROM_TASK_USER,\n" +
                "       SF_FLOW_PKG.GET_CUR_USERS_TOTAL(SA.ACTID) TOTAL_USERS,\n" +
                "       STD.TASK_PROP,\n" +
                "       dbo.NVL(STD.FORWARD_PATH,\n" +
                " CASE WHEN STD.TASK_PROP='1' THEN SPD.EDIT_PATH ELSE SPD.APPROVE_PATH END FORWARD_PATH,\n" +
                "       STD.SECTION_RIGHT,\n" +
                "       STD.HIDDEN_RIGHT,\n" +
                "       STD.ATTRIBUTE1,\n" +
                "       STD.ATTRIBUTE2,\n" +
                "       STD.ATTRIBUTE3,\n" +
                "       STD.ATTRIBUTE4,\n" +
                "       STD.ATTRIBUTE5,\n" +
                "       STD.SECTION_RIGHT,\n" +
                "       STD.HIDDEN_RIGHT,\n" +
                "       STD.TASK_NAME,\n" +
                "       SPD.PROC_NAME,\n" +
                "       SAI.SIGN_FLAG\n" +
                "  FROM SF_USER          SP,\n" +
                "       SF_USER          SP2,\n" +
                "       SF_ACT           SA,\n" +
                "       SF_ACT_INFO      SAI,\n" +
                "       SF_TASK_DEFINE   STD,\n" +
                "       SF_PROCEDURE_DEF SPD\n" +
                " WHERE SA.FROM_USER_ID *= SP2.USER_ID\n" +
                "   AND SA.CREATED_BY = SP.USER_ID\n" +
                "   AND SA.ACTID = SAI.ACT_ID\n" +
                "   AND SA.CUR_TASK_ID = STD.TASK_ID\n" +
                "   AND SA.PROC_ID = SPD.PROC_ID\n" +
                "   AND ((SAI.USER_ID = ? AND SAI.AGENT_USER_ID " + SyBaseSQLUtil.isNull() + " ) OR\n" +
                "       (SAI.AGENT_USER_ID = ?))\n" +
                "   AND SAI.SIGN_FLAG = 'N'" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR SPD.PROC_NAME LIKE ?)\n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR SA.APPLY_NUMBER LIKE ?)\n" +
                " ORDER BY SA.CREATION_DATE DESC  ";

        al.add(userId);
        al.add(userId);
        al.add(procName);
        al.add(procName);
        al.add(applyNumber);
        al.add(applyNumber);
        sm.setArgs(al);
        sm.setSqlStr(sqlStr);
        return sm;
    }

    /**
     * 在办箱SQL
     * @param currUserId  当前用户
     * @param applyNumber 模糊查询条件，应用编号
     * @param procName    模糊查询条件，流程名称
     * @return SQLModel
     */
    public static SQLModel getHandelModel(String currUserId, String applyNumber, String procName) {
        SQLModel sm = new SQLModel();
        String sql = "SELECT SP.USERNAME,\n" +
                "       SP2.USERNAME FROM_USER,\n" +
                "       SA.FROM_DT FROM_TIME,\n" +
                "       SA.ACTID,\n" +
                "       SA.CUR_TASK_ID,\n" +
                "       SA.PROC_ID,\n" +
                "       SA.APP_ID,\n" +
                "       SA.APPLY_NUMBER,\n" +
                "       SA.CREATION_DATE,\n" +
                "       SF_FLOW_PKG.GET_FROM_TASK_USER(SA.CUR_TASK_ID, SA.ACTID) FROM_TASK_USER,\n" +
                "       SF_FLOW_PKG.GET_CUR_USERS_TOTAL(SA.ACTID) TOTAL_USERS,\n" +
                "       STD.TASK_PROP,\n" +
                "       dbo.NVL(STD.FORWARD_PATH,\n" +
                "           DECODE(STD.TASK_PROP, '1', SPD.EDIT_PATH, SPD.APPROVE_PATH)) FORWARD_PATH,\n" +
                "       STD.SECTION_RIGHT,\n" +
                "       STD.HIDDEN_RIGHT,\n" +
                "       STD.ATTRIBUTE1,\n" +
                "       STD.ATTRIBUTE2,\n" +
                "       STD.ATTRIBUTE3,\n" +
                "       STD.ATTRIBUTE4,\n" +
                "       STD.ATTRIBUTE5,\n" +
                "       STD.SECTION_RIGHT,\n" +
                "       STD.HIDDEN_RIGHT,\n" +
                "       STD.TASK_NAME,\n" +
                "       SPD.PROC_NAME,\n" +
                "       SAI.SIGN_FLAG\n" +
                "  FROM SF_USER          SP,\n" +
                "       SF_USER          SP2,\n" +
                "       SF_ACT           SA,\n" +
                "       SF_ACT_INFO      SAI,\n" +
                "       SF_TASK_DEFINE   STD,\n" +
                "       SF_PROCEDURE_DEF SPD\n" +
                " WHERE SA.FROM_USER_ID *= SP2.USER_ID\n" +
                "   AND SA.CREATED_BY = SP.USER_ID\n" +
                "   AND SA.ACTID = SAI.ACT_ID\n" +
                "   AND SA.CUR_TASK_ID = STD.TASK_ID\n" +
                "   AND SA.PROC_ID = SPD.PROC_ID\n" +
                "   AND ((SAI.USER_ID = ? AND SAI.AGENT_USER_ID " + SyBaseSQLUtil.isNullNoParam() + " ) OR\n" +
                "       (SAI.AGENT_USER_ID = ?))\n" +
                "   AND SAI.SIGN_FLAG = 'Y'\n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR SPD.PROC_NAME LIKE ?)\n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR SA.APPLY_NUMBER LIKE ?)\n" +
                " ORDER BY SA.CREATION_DATE DESC";
        ArrayList al = new ArrayList();
        al.add(currUserId);
        al.add(currUserId);
        al.add(procName);
        al.add(procName);
        al.add(applyNumber);
        al.add(applyNumber);
        sm.setArgs(al);
        sm.setSqlStr(sql);
        return sm;
    }

    /**
     * 我的工作（待办箱+在办箱）
     * @param currUserId
     * @param applyNumber
     * @param procName
     * @return
     */
    public static SQLModel getMyWorkModel(String currUserId, String applyNumber, String procName, String actId) {
        SQLModel sm = new SQLModel();
        ArrayList al = new ArrayList();
        String sql = "SELECT SP.USERNAME,\n" +
                "       SP.USER_ID,\n" +
                "       SP2.USERNAME FROM_USER,\n" +
                "       SA.FROM_DT FROM_TIME,\n" +
                "       SA.ACTID,\n" +
                "       SA.CUR_TASK_ID,\n" +
                "       SA.PROC_ID,\n" +
                "       SA.APP_ID,\n" +
                "       SA.APPLY_NUMBER,\n" +
                "       SA.CREATION_DATE,\n" +
                "       SF_FLOW_PKG.GET_FROM_TASK_USER(SA.CUR_TASK_ID, SA.ACTID) FROM_TASK_USER,\n" +
                "       SF_FLOW_PKG.GET_CUR_USERS_TOTAL(SA.ACTID) TOTAL_USERS,\n" +
                "       STD.TASK_PROP,\n" +
                "       dbo.NVL(STD.FORWARD_PATH,\n" +
                "           DECODE(STD.TASK_PROP, '1', SPD.EDIT_PATH, SPD.APPROVE_PATH)) FORWARD_PATH,\n" +
                "       STD.SECTION_RIGHT,\n" +
                "       STD.HIDDEN_RIGHT,\n" +
                "       STD.ATTRIBUTE1,\n" +
                "       STD.ATTRIBUTE2,\n" +
                "       STD.ATTRIBUTE3,\n" +
                "       STD.ATTRIBUTE4,\n" +
                "       STD.ATTRIBUTE5,\n" +
                "       STD.SECTION_RIGHT,\n" +
                "       STD.HIDDEN_RIGHT,\n" +
                "       STD.TASK_NAME,\n" +
                "       SPD.PROC_NAME,\n" +
                "       SAI.SIGN_FLAG,\n" +
                " CASE WHEN SAI.SIGN_FLAG='Y' THEN 0 ELSE 4 END TYPE "+
                "  FROM SF_USER          SP,\n" +
                "       SF_USER          SP2,\n" +
                "       SF_ACT           SA,\n" +
                "       SF_ACT_INFO      SAI,\n" +
                "       SF_TASK_DEFINE   STD,\n" +
                "       SF_PROCEDURE_DEF SPD\n" +
                " WHERE SA.FROM_USER_ID *= SP2.USER_ID\n" +
                "   AND SA.CREATED_BY = SP.USER_ID\n" +
                "   AND SA.ACTID = SAI.ACT_ID\n" +
                "   AND SA.CUR_TASK_ID = STD.TASK_ID\n" +
                "   AND SA.PROC_ID = SPD.PROC_ID\n" +
                "   AND ((SAI.USER_ID = ? AND SAI.AGENT_USER_ID " + SyBaseSQLUtil.isNullNoParam() + " ) OR\n" +
                "       (SAI.AGENT_USER_ID = ?))\n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR SPD.PROC_NAME LIKE ?)\n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR SA.APPLY_NUMBER LIKE ?)\n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR SA.ACTID = ?)\n" +
                " ORDER BY SA.CREATION_DATE DESC";
        al.add(currUserId);
        al.add(currUserId);
        al.add(procName);
        al.add(procName);
        al.add(applyNumber);
        al.add(applyNumber);
        al.add(actId);
        al.add(actId);

        sm.setArgs(al);
        sm.setSqlStr(sql);
        return sm;
    }

    /**
     * 个人发件箱
     * @param currUserId
     * @param fromDate
     * @param toDate
     * @param applyNum
     * @param procName
     * @return
     */
    public static SQLModel getMyWorkedModel(String currUserId, String fromDate, String toDate, String applyNum, String procName) {
        SQLModel sm = new SQLModel();
        String sql = "SELECT SA.APP_ID,\n" +
                "       SA.APPLY_NUMBER,\n" +
                "       SA.FROM_DT,\n" +
                "       SU.USERNAME CREATED_USER,\n" +
                "       SU.CREATION_DATE,\n" +
                "       SA.ACTID,\n" +
                "       SF_FLOW_PKG.GET_CUR_USER_NAMES(SA.ACTID) CUR_USER,\n" +
                "       SPD.PROC_NAME,\n" +
                "       SPD.QUERY_PATH\n" +
                "  FROM SF_ACT SA, SF_USER SU, SF_PROCEDURE_DEF SPD\n" +
                " WHERE SA.CREATED_BY = SU.USER_ID\n" +
                "   AND SA.PROC_ID = SPD.PROC_ID\n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR SA.FROM_DT >= TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss'))\n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR SA.FROM_DT <= TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss'))\n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR SA.APPLY_NUMBER LIKE ?)\n" +
                "   AND SA.FROM_USER_ID = ?\n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR SPD.PROC_NAME LIKE ?)\n" +
                " ORDER BY SA.FROM_DT DESC";
        if (!fromDate.equals("")) {
            fromDate += " 00:00:00";
        }
        if (!toDate.equals("")) {
            toDate += " 23:59:59";
        }
        ArrayList al = new ArrayList();
        al.add(fromDate);
        al.add(fromDate);
        al.add(toDate);
        al.add(toDate);
        al.add(applyNum);
        al.add(applyNum);
        al.add(currUserId);
        al.add(procName);
        al.add(procName);
        sm.setArgs(al);
        sm.setSqlStr(sql);

        return sm;
    }


    public static SQLModel getOutboxModel(String currUserId, String fromDate, String toDate, String applyNum, String procName) {
        SQLModel sm = new SQLModel();
        String sql = "SELECT SA.APP_ID,\n" +
                "       SA.APPLY_NUMBER,\n" +
                "       SA.FROM_DT,\n" +
                "       SU.USERNAME CREATED_USER,\n" +
                "       SA.ACTID,\n" +
                "       SF_FLOW_PKG.GET_CUR_USER_NAMES(SA.ACTID) CUR_USER,\n" +
                "       SPD.PROC_NAME,\n" +
                "       SPD.QUERY_PATH\n" +
                "  FROM SF_ACT SA, SF_USER SU, SF_PROCEDURE_DEF SPD\n" +
                " WHERE SA.CREATED_BY = SU.USER_ID\n" +
                "   AND SA.PROC_ID = SPD.PROC_ID\n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR SA.FROM_DT >= TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss'))\n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR SA.FROM_DT <= TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss'))\n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR SA.APPLY_NUMBER LIKE ?)\n" +
                "   AND SA.FROM_USER_ID = ?\n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR SPD.PROC_NAME LIKE ?)\n" +
                " ORDER BY SA.FROM_DT DESC";
        if (!fromDate.equals("")) {
            fromDate += " 00:00:00";
        }
        if (!toDate.equals("")) {
            toDate += " 23:59:59";
        }
        ArrayList al = new ArrayList();
        al.add(fromDate);
        al.add(fromDate);
        al.add(toDate);
        al.add(toDate);
        al.add(applyNum);
        al.add(applyNum);
        al.add(currUserId);
        al.add(procName);
        al.add(procName);
        sm.setArgs(al);
        sm.setSqlStr(sql);

        return sm;
    }

    public static SQLModel getPersonalDetailModel(String currUserId, String fromData, String toDate, String expenseType, String headerStatus, String reportNumber) {
        SQLModel sm = new SQLModel();
        String sql =
                "SELECT EAH.HEADER_ID,\n" +
                        "       EAH.EXPENSE_TYPE,\n" +
                        "       EAH.REPORT_NUMBER,\n" +
                        "       EAS_PKG.GET_FLEX_VALUE(EAH.HEADER_STATUS, 'APPLY_STATUS') HEADER_STATUS," +
                        "       EAS_PKG.GET_FLEX_VALUE(EAH.EXPENSE_TYPE, 'EXPENSE_TYPE') EXPENSE_TYPE_DESC," +
                        "       EAH.TOTAL_MONEY,\n" +
                        "       EAH.CREATED_DATE,\n" +
                        "       CUV.USER_NAME,\n" +
                        "       SF_FLOW_PKG.GET_CUR_USER_NAMES_BY_APP_ID(EAH.HEADER_ID,\n" +
                        "                                                'EAS_APPLY_HEADER') CUR_USER\n" +
                        "  FROM EAS_APPLY_HEADER EAH, CPS_USER_V CUV\n" +
                        " WHERE EAH.CREATED_BY = CUV.USER_ID\n" +
                        "   AND EAH.CREATED_BY = ?\n" +
                        "   AND ( " + SyBaseSQLUtil.isNull() + "  OR\n" +
                        "       EAH.CREATED_DATE >= TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss'))\n" +
                        "   AND ( " + SyBaseSQLUtil.isNull() + "  OR\n" +
                        "       EAH.CREATED_DATE <= TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss'))\n" +
                        "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EAH.EXPENSE_TYPE = ?)\n" +
                        "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EAH.HEADER_STATUS=?)\n" +
                        "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EAH.REPORT_NUMBER LIKE ?) " +
                        " ORDER BY EAH.REPORT_NUMBER DESC";

        ArrayList al = new ArrayList();
        al.add(currUserId);
        al.add(fromData);
        al.add(fromData);
        al.add(toDate);
        al.add(toDate);
        al.add(expenseType);
        al.add(expenseType);
        al.add(headerStatus);
        al.add(headerStatus);
        al.add(reportNumber);
        al.add(reportNumber);
        sm.setArgs(al);
        sm.setSqlStr(sql);
        return sm;
    }

    /**
     * 取可以取消的申请
     * @param currUserId 当前用户ID
     * @return SQLModel
     */
    public static SQLModel getCancelModel(String currUserId) {
        SQLModel sm = new SQLModel();
        String sql =
                "SELECT EAH.HEADER_ID,\n" +
                        "       EAH.TOTAL_MONEY,\n" +
                        "       CUV.USER_NAME,\n" +
                        "       CUV2.USER_NAME FROM_USER,\n" +
                        "       SA.FROM_DT FROM_TIME,\n" +
                        "       SA.ACTID,\n" +
                        "       SA.CUR_TASK_ID,\n" +
                        "       SA.PROC_ID,\n" +
                        "       SA.APPLY_NUMBER,\n" +
                        "       SA.CREATION_DATE,\n" +
                        "       SF_FLOW_PKG.GET_FROM_TASK_USER(SA.CUR_TASK_ID, SA.ACTID) FROM_TASK_USER,\n" +
                        "       SF_FLOW_PKG.GET_CUR_USERS_TOTAL(SA.ACTID) TOTAL_USERS,\n" +
                        "       STD.TASK_PROP,\n" +
                        "       STD.SECTION_RIGHT,\n" +
                        "       STD.HIDDEN_RIGHT,\n" +
                        "       STD.TASK_NAME\n" +
                        "  FROM EAS_APPLY_HEADER EAH,\n" +
                        "       CPS_USER_V       CUV,\n" +
                        "       CPS_USER_V       CUV2,\n" +
                        "       SF_ACT           SA,\n" +
                        "       SF_ACT_INFO      SAI,\n" +
                        "       SF_TASK_DEFINE   STD\n" +
                        " WHERE EAH.CREATED_BY = CUV.USER_ID\n" +
                        "   AND EAH.HEADER_ID = SA.APP_ID\n" +
                        "   AND SA.FROM_USER_ID = CUV2.USER_ID\n" +
                        "   AND SA.ACTID = SAI.ACT_ID\n" +
                        "   AND SA.CUR_TASK_ID = STD.TASK_ID\n" +
                        "   AND ((SAI.USER_ID = ? AND SAI.AGENT_USER_ID " + SyBaseSQLUtil.isNullNoParam() + " ) OR\n" +
                        "       (SAI.AGENT_USER_ID = ?))\n" +
                        "    AND SA.CREATED_BY=?\n " +
                        " ORDER BY EAH.REPORT_NUMBER DESC";
        ArrayList al = new ArrayList();
        al.add(currUserId);
        al.add(currUserId);
        al.add(currUserId);
        sm.setArgs(al);
        sm.setSqlStr(sql);
        return sm;
    }

    public static SQLModel getCancelTotalModel(String currUserId) {
        String sql =
                "SELECT COUNT(*) TOTAL\n" +
                        "  FROM SF_ACT SA\n" +
                        " WHERE SA.CUR_USERID = ?\n" +
                        "   AND SA.CREATED_BY = ?";
        ArrayList al = new ArrayList();
        al.add(currUserId);
        al.add(currUserId);
        SQLModel sm = new SQLModel();
        sm.setArgs(al);
        sm.setSqlStr(sql);
        return sm;
    }

    public static SQLModel getArchieveModel(String userId, String reportNumber) {
        SQLModel sm = new SQLModel();
        String sqlStr = "";
        List al = new ArrayList();
        sqlStr = "SELECT EAH.HEADER_ID,\n" +
                "       EAH.TOTAL_MONEY,\n" +
                "       EAH.EXPENSE_TYPE,\n" +
                "       EAS_PKG.GET_FLEX_VALUE(EAH.EXPENSE_TYPE, 'EXPENSE_TYPE') EXPENSE_TYPE_DESC," +
                "       CUV.USER_NAME,\n" +
                "       CUV2.USER_NAME FROM_USER,\n" +
                "       SA.FROM_DT FROM_TIME,\n" +
                "       SA.ACTID,\n" +
                "       SA.CUR_TASK_ID,\n" +
                "       SA.PROC_ID,\n" +
                "       SA.APPLY_NUMBER,\n" +
                "       SA.CREATION_DATE,\n" +
                "       SF_FLOW_PKG.GET_FROM_TASK_USER(SA.CUR_TASK_ID, SA.ACTID) FROM_TASK_USER,\n" +
                "       SF_FLOW_PKG.GET_CUR_USERS_TOTAL(SA.ACTID) TOTAL_USERS,\n" +
                "       STD.TASK_PROP,\n" +
                "       STD.SECTION_RIGHT,\n" +
                "       STD.HIDDEN_RIGHT,\n" +
                "       STD.TASK_NAME\n" +
                "  FROM EAS_APPLY_HEADER EAH,\n" +
                "       CPS_USER_V       CUV,\n" +
                "       CPS_USER_V       CUV2,\n" +
                "       SF_ACT           SA,\n" +
                "       SF_ACT_INFO      SAI,\n" +
                "       SF_TASK_DEFINE   STD\n" +
                " WHERE EAH.CREATED_BY = CUV.USER_ID\n" +
                "   AND EAH.HEADER_ID = SA.APP_ID\n" +
                "   AND EAH.HEADER_STATUS =?\n" +
                "   AND SA.FROM_USER_ID = CUV2.USER_ID\n" +
                "   AND SA.ACTID = SAI.ACT_ID\n" +
                "   AND SA.CUR_TASK_ID = STD.TASK_ID\n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EAH.REPORT_NUMBER LIKE ?)" +
                "   AND ((SAI.USER_ID = ? AND SAI.AGENT_USER_ID " + SyBaseSQLUtil.isNullNoParam() + " ) OR\n" +
                "       (SAI.AGENT_USER_ID = ?))\n" +
                " ORDER BY EAH.REPORT_NUMBER DESC";
        //al.add(FndConstant.APPLY_STATUS_APPROVED);
        al.add(reportNumber);
        al.add(reportNumber);
        al.add(userId);
        al.add(userId);
        sm.setArgs(al);
        sm.setSqlStr(sqlStr);
        return sm;
    }
}
