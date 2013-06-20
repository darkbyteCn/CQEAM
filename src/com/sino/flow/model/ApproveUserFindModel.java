package com.sino.flow.model;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.UploadException;
import com.sino.flow.dto.FlowParmDTO;

/**
 * <p>Title: SinoCPS</p>
 * <p>Description: 河南移动集中核算系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @version 1.2
 */
public class ApproveUserFindModel {
    private FlowParmDTO dto;
    private HttpServletRequest req;

    public ApproveUserFindModel(FlowParmDTO dto, HttpServletRequest req) {
        this.dto = dto;
        this.req = req;
    }

    public void setDto(FlowParmDTO dto) {
        this.dto = dto;
    }

    /**
     * 功能：获取对应节点的办理人员.
     *
     * @return SQLModel
     * @throws UploadException
     */
    public SQLModel getPositionUserModel() throws UploadException {
        SQLModel sqlModel = new SQLModel();
        String sqlStr =
                "SELECT DISTINCT SP.USER_ID, SP.USER_NAME,SP.POSITION_ID\n" +
                        "  FROM SF_TASK_DEFINE STD, SF_ROLE_INFO SRI, SF_POSITION SP\n" +
                        " WHERE STD.ROLE_ID = SRI.ROLE_ID\n" +
                        "   AND SRI.USER_ID = SP.USER_ID\n" +
                        "   AND STD.TASK_ID = ?\n" +
                        "UNION ALL\n" +
                        "SELECT DISTINCT SP2.USER_ID, SP2.USER_NAME,SP2.POSITION_ID\n" +
                        "  FROM SF_TASK_DEFINE STD2, SF_ROLE_INFO SRI2, SF_POSITION SP2\n" +
                        " WHERE STD2.ROLE_ID = SRI2.ROLE_ID\n" +
                        "   AND STD2.ORGANIZATION_ID = SRI2.ORGANIZATION_ID\n" +
                        "   AND SRI2.POSITION_ID = SP2.POSITION_ID\n" +
                        "   AND SP2.DEPT_ID =CASE WHEN STD2.DEPT_ID=-1 THEN ? ELSE STD2.DEPT_ID END \n" +
                        "   AND SRI2.USER_ID " + SyBaseSQLUtil.isNullNoParam() + " \n" +
                        "   AND STD2.TASK_ID = ?";

        List sqlArgs = new ArrayList();
        sqlArgs.add(dto.getNextTaskId());
        sqlArgs.add(dto.getNextDeptId());
        sqlArgs.add(dto.getNextTaskId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

//    public SQLModel getPositionTaskModel() throws UploadException {
//        SQLModel sqlModel = new SQLModel();
//        String sqlStr = "SELECT"
//                + " SP.POSITION_ID TASK_POSITION_ID,"
//                + " STD.TASK_PROP,"
//                + " STD.ORGANIZATION_ID,"
//                + " STD.TASK_ID,"
//                + " STD.PROC_ID,"
//                + " STD.TASK_NAME,"
//                + " STD.TASK_MSG,"
//                + " SP.POSITION_NAME TASK_POSITION_NAME,"
//                + " STD.SECTION_RIGHT,"
//                + " STD.HIDDEN_RIGHT,"
//                + " STD.CREATION_DATE,"
//                + " STD.CREATED_BY,"
//                + " STD.IS_HAND_USER,"
//                + " DEZCODE(STD.DEPT_ID, -1, ?, STD.DEPT_ID) DEPT_ID,"
//  + " DEZCODE(STD.DEPT_NAME, '*', ?, STD.DEPT_NAME) DEPT_NAME,"
//                + " SPD.PROC_NAME"
//                + " FROM"
//                + " SF_TASK_DEFINE STD,"
//                + " SF_PROCEDURE_DEF SPD,"
//                + " SF_POSITION SP"
//                + " WHERE"
//                + " STD.PROC_ID = SPD.PROC_ID"
//                + " AND CHARINDEX(STD.TASK_POSITION_ID, '''' || SP.POSITION_ID || '''') > 0"
//                + " AND SPD.ORGANIZATION_ID = ?"
//                + " AND SP.DEPT_ID = ?"
//                + " AND STD.TASK_ID = ?"
//                + " AND STD.PROC_ID=? ";
//
//        List sqlArgs = new ArrayList();
//        sqlArgs.add(dto.getNextDeptId());
//        sqlArgs.add(dto.getNextDeptName());
//        sqlArgs.add(dto.getOrgId());
//        sqlArgs.add(dto.getNextDeptId());
//        sqlArgs.add(dto.getNextTaskId());
//        sqlArgs.add(dto.getProcId());
//        sqlModel.setSqlStr(sqlStr);
//        sqlModel.setArgs(sqlArgs);
//        return sqlModel;
//    }
//
//    public SQLModel getTaskTypeModel() throws UploadException {
//        String sql = "SELECT STD.TASK_TYPE FROM SF_TASK_DEFINE STD WHERE STD.TASK_ID = ? ";
//        ArrayList al = new ArrayList();
//        al.add(req.getParameter("nextTaskId"));
//        SQLModel sm = new SQLModel();
//        sm.setArgs(al);
//        sm.setSqlStr(sql);
//        return sm;
//    }
//
//    public SQLModel getUsersModel() throws UploadException {
//        String sql =
//                "SELECT STD.TASK_USER_ID, SP.USER_NAME\n" +
//                        "  FROM SF_TASK_DEFINE STD, SF_POSITION SP\n" +
//                        " WHERE STD.TASK_USER_ID = SP.USER_ID\n" +
//                        "   AND STD.TASK_ID = ?";
//
//        ArrayList al = new ArrayList();
//        al.add(req.getParameter("nextTaskId"));
//        SQLModel sm = new SQLModel();
//        sm.setArgs(al);
//        sm.setSqlStr(sql);
//        return sm;
//    }
}
