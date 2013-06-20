package com.sino.flow.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.base.db.sql.model.SQLModel;

/**
 * Created by wwb.
 * User: V-wangwenbin
 * Date: 2007-11-9
 * Time: 17:35:05
 */
public class FlowActionModel {
    //取用户的电话号码
    public SQLModel getPhoneModel(int userId, String sendType) {
        String sql = "SELECT SUP.MSG_CELL_PHONE\n" +
                "  FROM SF_USER_PHONE SUP\n" +
                " WHERE SUP.USER_ID = ?\n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR SUP.SEND_TYPE = ?)";
        ArrayList al = new ArrayList();
        al.add(userId);
        al.add(sendType);
        al.add(sendType);
        SQLModel sm = new SQLModel();
        sm.setSqlStr(sql);
        sm.setArgs(al);
        return sm;
    }

    //取用户的代理人
    public SQLModel getAgent(String userId, String procId) {
        String sql = "SELECT SFA.AGENT_USER_ID\n" +
                "  FROM SF_FLOW_AGENT SFA\n" +
                " WHERE SFA.USER_ID = ?\n" +
                "   AND (GETDATE() BETWEEN SFA.ACTIVE_START_DATE AND SFA.ACTIVE_END_DATE)\n" +
                "   AND SFA.DISABLE_DATE " + SyBaseSQLUtil.isNullNoParam() + " \n" +
                "   AND (SFA.PROC_ID " + SyBaseSQLUtil.isNullNoParam() + "  OR SFA.PROC_ID = ?)";
        ArrayList al = new ArrayList();
        al.add(userId);
        al.add(procId);
        SQLModel sm = new SQLModel();
        sm.setSqlStr(sql);
        sm.setArgs(al);
        return sm;
    }

    //取流程记录的当前信息
    public SQLModel getSfAct(String actId) {
        String sql = "SELECT * FROM SF_ACT SA WHERE SA.ACTID = ?";
        ArrayList al = new ArrayList();
        al.add(actId);
        SQLModel sm = new SQLModel();
        sm.setSqlStr(sql);
        sm.setArgs(al);
        return sm;
    }

    //取流程的开始节点，和启动人
    public SQLModel getBeginTaskUser(String actId) {
        String sql = " SELECT SF_FLOW_PKG.GET_BEGIN_TASK(SA.PROC_ID) BEGIN_TASK, SA.CREATED_BY\n" +
                "      INTO L_FIRST_TASK, L_FIRST_USER\n" +
                "      FROM SF_ACT SA\n" +
                "     WHERE SA.ACTID = ?";
        ArrayList al = new ArrayList();
        al.add(actId);
        SQLModel sm = new SQLModel();
        sm.setSqlStr(sql);
        sm.setArgs(al);
        return sm;
    }

    //流程提交时，确定是否为重复提交，先锁住此条记录
    public SQLModel lockAct(String actId, String sessionUserId) {
        String sql = " UPDATE SF_ACT SA SET SA.LOCKED_BY = ?, SA.LOCKED_DATE = GETDATE()\n" +
                " WHERE SA.ACTID = ?";
        ArrayList al = new ArrayList();
        al.add(sessionUserId);
        al.add(actId);
        SQLModel sm = new SQLModel();
        sm.setArgs(al);
        sm.setSqlStr(sql);
        return sm;
    }

    //流程重复提交检查完成后，清除锁
    public SQLModel clearLock(String actId) {
        String sql = " UPDATE SF_ACT SA SET SA.LOCKED_BY = NULL, SA.LOCKED_DATE =NULL\n" +
                " WHERE SA.ACTID = ?";
        ArrayList al = new ArrayList();
        al.add(actId);
        SQLModel sm = new SQLModel();
        sm.setSqlStr(sql);
        sm.setArgs(al);
        return sm;
    }

    /**
     * 取下一个流向的个数，如果个数大于1，就应该加上流向类型。
     *
     */
    public SQLModel getNextTaskCount(String curTaskId) {
        String sql =
                "SELECT COUNT(*) TOTAL\n" +
                        "  FROM SF_FLOW_DEFINE SFD\n" +
                        " WHERE SFD.FROM_TASK_ID =?\n" +
                        "   AND SFD.DISABLED = 'N' ";
        ArrayList al = new ArrayList();
        al.add(curTaskId);
        SQLModel sm = new SQLModel();
        sm.setArgs(al);
        sm.setSqlStr(sql);
        return sm;
    }

    //取taskInfo by id
    public SQLModel getTaskInfoByTaskId(String taskId) {
        String sql = "SELECT * FROM SF_TASK_DEFINE STD WHERE STD.TASK_ID = ?";
        ArrayList al = new ArrayList();
        al.add(taskId);
        SQLModel sm = new SQLModel();
        sm.setSqlStr(sql);
        sm.setArgs(al);
        return sm;
    }

    //根据应用反查扭转记录
    public SQLModel getSfActByApply(String applyId, String appTableName) {
        String sql = "SELECT SA.*\n" +
                "  FROM SF_ACT SA, SF_PROCEDURE_DEF SPD\n" +
                " WHERE SA.PROC_ID = SPD.PROC_ID\n" +
                "   AND SPD.APP_TABLE_NAME = ?\n" +
                "   AND SA.APP_ID = ?";
        ArrayList al = new ArrayList();
        SQLModel sm = new SQLModel();
        sm.setSqlStr(sql);
        sm.setArgs(al);
        return sm;
    }

    //根据应用查找应用当前所在的节点信息
    public SQLModel getCurTaskInfoByApp(String applyId, String appTableName) {
        String sql = "SELECT STD.*\n" +
                "  FROM SF_ACT SA, SF_PROCEDURE_DEF SPD, SF_TASK_DEFINE STD\n" +
                " WHERE SA.PROC_ID = SPD.PROC_ID\n" +
                "   AND SA.CUR_TASK_ID = STD.TASK_ID\n" +
                "   AND SPD.APP_TABLE_NAME = ?\n" +
                "   AND SA.APP_ID = ?";
        ArrayList al = new ArrayList();
        al.add(appTableName);
        al.add(applyId);
        SQLModel sm = new SQLModel();
        sm.setSqlStr(sql);
        sm.setArgs(al);
        return sm;
    }

    //某应用是否已经走过流程
    public SQLModel isAddedModel(String procId, String applyId) {
        String sql = "SELECT *\n" +
                "  FROM SF_ACT SA\n" +
                " WHERE SA.PROC_ID = ?\n" +
                "   AND SA.APP_ID = ?";
        ArrayList al = new ArrayList();
        al.add(procId);
        al.add(applyId);
        SQLModel sm = new SQLModel();
        sm.setSqlStr(sql);
        sm.setArgs(al);
        return sm;
    }

    //通过流程名取流程信息
    public SQLModel getProcByName(String procName) {
        String sql = "SELECT * FROM SF_PROCEDURE_DEF SPD WHERE SPD.PROC_NAME = ?";
        ArrayList al = new ArrayList();
        al.add(procName);
        SQLModel sm = new SQLModel();
        sm.setSqlStr(sql);
        sm.setArgs(al);
        return sm;
    }

    //取某一流程的第一个节点
    public SQLModel getFirstTask(String procId) {   //2009.4.1修改（su）
        String sql = "SELECT *\n" +
                "  FROM SF_TASK_DEFINE STD\n" +
                " WHERE STD.PROC_ID = ?\n" +
                "   AND STD.TASK_PROP = 1 AND STD.DISABLED = 'N'";
        ArrayList al = new ArrayList();
        al.add(procId);
        SQLModel sm = new SQLModel();
        sm.setSqlStr(sql);
        sm.setArgs(al);
        return sm;
    }

    //判断某个节点是否为第一个节点
    public SQLModel getTaskProp(String taskId) {
        String sql = "SELECT STD.TASK_PROP FROM SF_TASK_DEFINE STD WHERE STD.TASK_ID = ?";
        ArrayList al = new ArrayList();
        al.add(taskId);
        SQLModel sm = new SQLModel();
        sm.setSqlStr(sql);
        sm.setArgs(al);
        return sm;
    }

    /**
     * 根据两个几点取流信息
     * @param fromTaskId
     * @param toTaskId
     * @return
     */
    public SQLModel getFlowDefine(String fromTaskId,String toTaskId){
        SQLModel sqlModel=new SQLModel();
        List sqlArgs=new ArrayList();
        String sqlStr = "SELECT *\n" +
                "  FROM SF_FLOW_DEFINE SFD\n" +
                " WHERE SFD.FROM_TASK_ID = ?\n" +
                "   AND SFD.TO_TASK_ID = ?\n" +
                "   AND SFD.DISABLED = 'N'";
        sqlArgs.add(fromTaskId);
        sqlArgs.add(toTaskId);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

  
}
