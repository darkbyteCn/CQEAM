package com.sino.flow.model;

import java.util.ArrayList;

import com.sino.base.db.sql.model.SQLModel;

/**
 * Created by wwb.
 * User: demo
 * Date: 2006-12-21
 * Time: 11:59:32
 */
public class ApproveContentModel {
    /**
     * 根据应用ID查找审批记录
     *
     * @param applyId
     * @param appTableName
     * @return
     */
    public static SQLModel getContentModel(String applyId, String appTableName) {
        SQLModel sm = new SQLModel();
        String sql ="SELECT SFACT_COMPLETE_USER USER_NAME,\n" +
                "       SFACT_COMPLETE_USER AGENT_USER_NAME,\n" +
                "       SFACT_COMPLETE_DATE APPROVE_TIME,\n" +
                "       SFACT_OPINION APPROVE_CONTENT,\n" +
                "       SFACT_TASK_NAME\n" +
                "  FROM (SELECT dbo.SFK_GET_REALNAMES(SAL.SFACT_COMPLETE_USER) SFACT_COMPLETE_USER,\n" +
                "               dbo.SFK_GET_DEPT_NAME(CONVERT(VARCHAR,SAL.SFACT_COMPLETE_USER)) SFACT_TASK_GROUP,\n" +
//                "                             dbo.SFK_GET_MASK_GROUP(SAL.SFACT_TASK_GROUP,\n" +
//                "                                            SAL.SFACT_HANDLER_GROUP,\n" +
//                "                                            SAL.SFACT_PLUS_GROUP)) SFACT_TASK_GROUP,\n" +
                "               SAL.SFACT_TASK_NAME,\n" +
                "               SAL.SFACT_COMPLETE_DATE,\n" +
                "               SAL.SFACT_SORT_COLUMN_1,\n" +
                "               SAL.SFACT_SORT_COLUMN_2,\n" +
                "               SAL.SFACT_SORT_COLUMN_3,\n" +
                "               SAL.SFACT_APPL_COLUMN_1,\n" +
                "               SAL.SFACT_APPL_COLUMN_2,\n" +
                "               SAL.SFACT_APPL_COLUMN_3,\n" +
                "               SAL.SFACT_APPL_COLUMN_4,\n" +
                "               SAL.SFACT_APPL_COLUMN_5,\n" +
                "               SAL.SFACT_APPL_COLUMN_6,\n" +
                "               SAL.SFACT_APPL_COLUMN_7,\n" +
                "               SAL.SFACT_APPL_COLUMN_8,\n" +
                "               SAL.SFACT_APPL_COLUMN_9,\n" +
                "               SAL.SFACT_SPLIT_TASK_ID SFACT_OPINION,\n" +
                "               CASE SAL.SFACT_NEXT_TASK_NAME\n" +
                "                 WHEN '' THEN\n" +
                "                  ''\n" +
                "                 WHEN 'SPLIT' THEN\n" +
                "                  dbo.SFK_GET_NEXT_TASKS(SAL.SFACT_NEXT_TASK_ID)\n" +
                "                 WHEN 'JOIN' THEN\n" +
                "                  dbo.SFK_GET_NEXT_TASKS(SAL.SFACT_NEXT_TASK_ID)\n" +
                "                 WHEN 'STOP' THEN\n" +
                "                  dbo.SFK_GET_NEXT_TASKS(SAL.SFACT_NEXT_TASK_ID)\n" +
                "                 ELSE\n" +
                "                  SAL.SFACT_NEXT_TASK_NAME\n" +
                "               END SFACT_NEXT_TASK_NAME,\n" +
                "               dbo.SFK_GET_NEXT_FLOW_HINT(SAL.SFACT_TASK_ID, SAL.SFACT_NEXT_TASK_ID) SFACT_FLOW_HINT\n" +
                "          FROM SF_ACT_LOG SAL\n" +
                "         WHERE SAL.SFACT_APPL_ID=?\n" +
                "           AND SAL.SFACT_PICK_USER <> 'SYSTEM'\n" +
                "        UNION ALL\n" +
                "        SELECT dbo.SFK_GET_REALNAMES(SAA.SFACT_COMPLETE_USER) SFACT_COMPLETE_USER,\n" +
                "               dbo.SFK_GET_DEPT_NAME(CONVERT(VARCHAR,SAA.SFACT_COMPLETE_USER)) SFACT_TASK_GROUP,\n" +
//                "                             dbo.SFK_GET_MASK_GROUP(SAA.SFACT_TASK_GROUP,\n" +
//                "                                            SAA.SFACT_HANDLER_GROUP,\n" +
//                "                                            SAA.SFACT_PLUS_GROUP)) SFACT_TASK_GROUP,\n" +
                "               SAA.SFACT_TASK_NAME,\n" +
                "               SAA.SFACT_COMPLETE_DATE,\n" +
                "               SAA.SFACT_SORT_COLUMN_1,\n" +
                "               SAA.SFACT_SORT_COLUMN_2,\n" +
                "               SAA.SFACT_SORT_COLUMN_3,\n" +
                "               SAA.SFACT_APPL_COLUMN_1,\n" +
                "               SAA.SFACT_APPL_COLUMN_2,\n" +
                "               SAA.SFACT_APPL_COLUMN_3,\n" +
                "               SAA.SFACT_APPL_COLUMN_4,\n" +
                "               SAA.SFACT_APPL_COLUMN_5,\n" +
                "               SAA.SFACT_APPL_COLUMN_6,\n" +
                "               SAA.SFACT_APPL_COLUMN_7,\n" +
                "               SAA.SFACT_APPL_COLUMN_8,\n" +
                "               SAA.SFACT_APPL_COLUMN_9,\n" +
                "               SAA.SFACT_SPLIT_TASK_ID SFACT_OPINION,\n" +
                "               CASE SAA.SFACT_NEXT_TASK_NAME\n" +
                "                 WHEN '' THEN\n" +
                "                  ''\n" +
                "                 WHEN 'SPLIT' THEN\n" +
                "                  dbo.SFK_GET_NEXT_TASKS(SAA.SFACT_NEXT_TASK_ID)\n" +
                "                 WHEN 'JOIN' THEN\n" +
                "                  dbo.SFK_GET_NEXT_TASKS(SAA.SFACT_NEXT_TASK_ID)\n" +
                "                 WHEN 'STOP' THEN\n" +
                "                  dbo.SFK_GET_NEXT_TASKS(SAA.SFACT_NEXT_TASK_ID)\n" +
                "                 ELSE\n" +
                "                  SAA.SFACT_NEXT_TASK_NAME\n" +
                "               END SFACT_NEXT_TASK_NAME,\n" +
                "               dbo.SFK_GET_NEXT_FLOW_HINT(SAA.SFACT_TASK_ID, SAA.SFACT_NEXT_TASK_ID) SFACT_FLOW_HINT\n" +
                "          FROM SF_ACT_ARCHIVE SAA\n" +
                "         WHERE SAA.SFACT_APPL_ID=?\n" +
                "           AND SAA.SFACT_PICK_USER <> 'SYSTEM') T\n" +
                " ORDER BY T.SFACT_COMPLETE_DATE";

        ArrayList al = new ArrayList();
//        al.add(appTableName); //应用表名
        al.add(applyId); //应用ID
        al.add(applyId); //应用ID
        sm.setArgs(al);
        sm.setSqlStr(sql);
        return sm;
    }

    /**
     * 根据流程记录查找审批意见
     *
     * @param actId
     */
    public static SQLModel getContentModel(String actId) {
        SQLModel sm = new SQLModel();
        String sql="SELECT STD.TASK_ID,\n" +
                "       STD.PROC_ID,\n" +
                "       STD.TASK_NAME,\n" +
                "       STD.TASK_MSG,\n" +
                "       SAC.APPROVE_ID,\n" +
                "       SU.USERNAME USER_NAME,\n" +
                "       (SELECT SU2.USERNAME\n" +
                "          FROM SF_USER SU2\n" +
                "         WHERE SU2.USER_ID = SAC.AGENT_USER_ID) AGENT_USER_NAME,\n" +
                "       SAC.APPROVE_TIME,\n" +
                "       SAC.APPROVE_CONTENT\n" +
                "  FROM SF_TASK_DEFINE STD, SF_APPROVE_CONTENT SAC, SF_USER SU\n" +
                " WHERE SAC.TASK_ID = STD.TASK_ID\n" +
                "   AND SAC.APPROVE_PERSON_ID = SU.USER_ID\n" +
                "   AND SAC.ACTID = ?\n" +
                " ORDER BY SAC.APPROVE_TIME";
        ArrayList al=new ArrayList();
        al.add(actId);
        sm.setSqlStr(sql);
        sm.setArgs(al);
        return sm;
    }
}
