package com.sino.sinoflow.model;


import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.util.StrUtil;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.sinoflow.dto.DeptGroupDTO;
import com.sino.sinoflow.dto.DeptGroupLineDTO;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: SfGroupModel</p>
 * <p>Description:程序自动生成SQL构造器“SfGroupModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Hing
 * @version 1.0
 */


public class DeptGroupModel extends BaseSQLProducer {

	private SfUserBaseDTO sfUser = null;
	private DeptGroupDTO deptGroup = null;

	/**
	 * 功能：组别属性 SF_GROUP 数据库SQL构造层构造函数
	 * @param userAccount SfUserBaseDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter SfGroupDTO 本次操作的数据
	 */
	public DeptGroupModel(SfUserBaseDTO userAccount, DeptGroupDTO dtoParameter) {
		super(userAccount, dtoParameter);
		deptGroup = dtoParameter;
		sfUser = userAccount;
	}

	/**
	 * 功能：框架自动生成组别属性 SF_GROUP页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 */
	public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
			String sqlStr = "SELECT "
			+ " SG.PROJECT_NAME,"
			+ " SMD.DEPT_NAME,"
            + " SMD.DEPT_ID,"
            + " SG.GROUP_NAME,"
            + " SG.GROUP_ID,"
            + " SMD2.DEPT_NAME PARENT_NAME,"
            + " SMD2.DEPT_ID PARENT_ID,"
            + " SMD.DISPLAY_ORDER,"
            + " CASE SMD.SECOND_DEPT WHEN '' THEN 'N' WHEN NULL THEN 'N' ELSE SMD.SECOND_DEPT END SECOND_DEPT,"
            + " CASE SMD.SPECIALITY_DEPT WHEN '' THEN 'N' WHEN NULL THEN 'N' ELSE SMD.SPECIALITY_DEPT END SPECIALITY_DEPT,"
            + " SMD.ORG_ID"
            + " FROM"
			+ " SF_GROUP SG, SINO_MIS_DEPT SMD, SINO_GROUP_MATCH SGM, SINO_MIS_DEPT SMD2"
			+ " WHERE"
			+ " (? = '' OR SG.PROJECT_NAME LIKE ?)"
			+ " AND (? = '' OR SG.GROUP_NAME LIKE ?)"
            + " AND (? = '' OR SMD.DEPT_NAME LIKE ?)"
            + " AND SG.GROUP_ID = SGM.GROUP_ID"
            + " AND SGM.DEPT_ID = SMD.DEPT_ID"
            + " AND SMD.PARENT_DEPT_ID *= SMD2.DEPT_ID"
            + " ORDER BY SMD.DISPLAY_ORDER, SMD.DEPT_NAME";

        sqlArgs.add(deptGroup.getProject());
		sqlArgs.add(deptGroup.getProject());
		sqlArgs.add(deptGroup.getGroup());
		sqlArgs.add("%" + deptGroup.getGroup() + "%");
        sqlArgs.add(deptGroup.getDept());
        sqlArgs.add("%" + deptGroup.getDept() + "%");

        sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

    public SQLModel getDeptGroupDataModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
            String sqlStr = "SELECT "
            + " SG.PROJECT_NAME,"
            + " SMD.DEPT_NAME,"
            + " SMD.DEPT_ID,"
            + " SG.GROUP_NAME,"
            + " SG.GROUP_ID,"
            + " SMD2.DEPT_NAME PARENT_NAME,"
            + " SMD2.DEPT_ID PARENT_ID,"
            + " SMD.DISPLAY_ORDER,"
            + " CASE SMD.SECOND_DEPT WHEN '' THEN 'N' WHEN NULL THEN 'N' ELSE SMD.SECOND_DEPT END SECOND_DEPT,"
            + " CASE SMD.SPECIALITY_DEPT WHEN '' THEN 'N' WHEN NULL THEN 'N' ELSE SMD.SPECIALITY_DEPT END SPECIALITY_DEPT,"
            + " SMD.ORG_ID"
            + " FROM"
            + " SF_GROUP SG, SINO_MIS_DEPT SMD, SINO_GROUP_MATCH SGM, SINO_MIS_DEPT SMD2"
            + " WHERE"
            + " (? = '' OR SG.PROJECT_NAME LIKE ?)"
            + " AND (? = '' OR SG.GROUP_NAME LIKE ?)"
            + " AND (? = '' OR SMD.DEPT_NAME LIKE ?)"
            + " AND SG.GROUP_ID = SGM.GROUP_ID"
            + " AND SGM.DEPT_ID = SMD.DEPT_ID"
            + " AND SMD.PARENT_DEPT_ID *= SMD2.DEPT_ID"
            + " ORDER BY SMD.DISPLAY_ORDER, SMD.DEPT_NAME";

        sqlArgs.add(deptGroup.getProject());
        sqlArgs.add(deptGroup.getProject());
        sqlArgs.add(deptGroup.getGroup());
        sqlArgs.add("%" + deptGroup.getGroup() + "%");
        sqlArgs.add(deptGroup.getDept());
        sqlArgs.add("%" + deptGroup.getDept() + "%");

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getDeptGroupMatchModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
/*
            String sqlStr = "SELECT "
            + " ? PROJECT_NAME,"
            + " SMD.DEPT_NAME,"
            + " SMD.DEPT_ID,"
            + " '' GROUP_NAME,"
            + " NULL GROUP_ID,"
            + " SMD2.DEPT_NAME PARENT_NAME,"
            + " SMD2.DEPT_ID PARENT_ID,"
            + " SMD.DISPLAY_ORDER,"
            + " SMD.SECOND_DEPT,"
            + " SMD.SPECIALITY_DEPT"
            + " FROM"
            + " SINO_MIS_DEPT SMD, SINO_MIS_DEPT SMD2"
            + " WHERE"
            + " NOT EXISTS (SELECT NULL FROM SINO_GROUP_MATCH SGM WHERE SMD.DEPT_ID = SGM.DEPT_ID)"
            + " AND SMD.PARENT_DEPT_ID *= SMD2.PARENT_DEPT_ID"
            + " UNION ALL"
            + " SELECT"
            + " SG.PROJECT_NAME,"
            + " '' DEPT_NAME,"
            + " NULL DEPT_ID,"
            + " SG.GROUP_NAME,"
            + " SG.GROUP_ID,"
            + " '' PARENT_NAME,"
            + " NULL PARENT_ID,"
            + " NULL DISPLAY_ORDER,"
            + " '' SECOND_DEPT,"
            + " '' SPECIALITY_DEPT"
            + " FROM SF_GROUP SG"
            + " WHERE"
            + " NOT EXISTS (SELECT NULL FROM SINO_GROUP_MATCH SGM WHERE SG.GROUP_ID = SGM.GROUP_ID)";
*/
        String sqlStr = "SELECT "
        + " ? PROJECT_NAME,"
        + " SMD.DEPT_NAME,"
        + " SMD.DEPT_ID,"
        + " '' GROUP_NAME,"
        + " NULL GROUP_ID,"
        + " SMD2.DEPT_NAME PARENT_NAME,"
        + " SMD2.DEPT_ID PARENT_ID,"
        + " SMD.DISPLAY_ORDER,"
        + " CASE SMD.SECOND_DEPT WHEN '' THEN 'N' WHEN NULL THEN 'N' ELSE SMD.SECOND_DEPT END SECOND_DEPT,"
        + " CASE SMD.SPECIALITY_DEPT WHEN '' THEN 'N' WHEN NULL THEN 'N' ELSE SMD.SPECIALITY_DEPT END SPECIALITY_DEPT,"
        + " SMD.ORG_ID"
        + " FROM"
        + " SINO_MIS_DEPT SMD, SINO_MIS_DEPT SMD2"
        + " WHERE"
        + " NOT EXISTS (SELECT NULL FROM SINO_GROUP_MATCH SGM WHERE SMD.DEPT_ID = SGM.DEPT_ID)"
        + " AND SMD.PARENT_DEPT_ID *= SMD2.DEPT_ID"
        + " AND SMD.ENABLED = 'Y'"
        + " ORDER BY SMD.DISPLAY_ORDER, SMD.DEPT_NAME";

        sqlArgs.add(deptGroup.getSfProject());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel updateDept(DeptGroupLineDTO dto) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE SINO_MIS_DEPT"
                + " SET DEPT_NAME = ?,"
                + " PARENT_DEPT_ID = ?,"
                + " DISPLAY_ORDER = ?,"
                + " SECOND_DEPT = ?,"
                + " SPECIALITY_DEPT = ?"
                + " WHERE DEPT_ID = ?";

        sqlArgs.add(dto.getDeptName());
        if(dto.getParentId().equals(""))
            sqlArgs.add(null);
        else
            sqlArgs.add(dto.getParentId());
        if(dto.getDisplayOrder().equals(""))
            sqlArgs.add(null);
        else
            sqlArgs.add(Integer.parseInt(dto.getDisplayOrder()));
        sqlArgs.add(dto.getSecondDept());
        sqlArgs.add(dto.getSpeicalityDept());
        sqlArgs.add(dto.getDeptId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel insertGroupMatch(String deptId, int groupId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO SINO_GROUP_MATCH(DEPT_ID, GROUP_ID) VALUES(?, ?)";

        sqlArgs.add(deptId);
        sqlArgs.add(groupId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel updateGroupName(DeptGroupLineDTO dto) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE SF_GROUP"
                + " SET GROUP_NAME = ?"
                + " WHERE GROUP_NAME = ?";

        sqlArgs.add(dto.getGroupName());
        sqlArgs.add(dto.getCurGroupName());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel updateAuthority(DeptGroupLineDTO dto) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE SF_USER_AUTHORITY"
                + " SET GROUP_NAME = ?"
                + " WHERE GROUP_NAME = ?";

        sqlArgs.add(dto.getGroupName());
        sqlArgs.add(dto.getCurGroupName());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel updateActInfo(DeptGroupLineDTO dto) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE SF_ACT_INFO"
                + " SET SFACT_TASK_GROUP = CASE SFACT_TASK_GROUP WHEN ? THEN ? ELSE SFACT_TASK_GROUP END,"
                + " SFACT_HANDLER_GROUP = CASE SFACT_TASK_GROUP WHEN ? THEN ? ELSE SFACT_HANDLER_GROUP END,"
                + " SFACT_PLUS_GROUP = CASE SFACT_PLUS_GROUP WHEN ? THEN ? ELSE SFACT_PLUS_GROUP END"
                + " WHERE SFACT_TASK_GROUP = ? OR SFACT_HANDLER_GROUP = ? OR SFACT_PLUS_GROUP = ?";

        sqlArgs.add(dto.getCurGroupName());
        sqlArgs.add(dto.getGroupName());
        sqlArgs.add(dto.getCurGroupName());
        sqlArgs.add(dto.getGroupName());
        sqlArgs.add(dto.getCurGroupName());
        sqlArgs.add(dto.getGroupName());
        sqlArgs.add(dto.getGroupName());
        sqlArgs.add(dto.getGroupName());
        sqlArgs.add(dto.getGroupName());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel updateActLog(DeptGroupLineDTO dto) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE SF_ACT_LOG"
                + " SET SFACT_TASK_GROUP = CASE SFACT_TASK_GROUP WHEN ? THEN ? ELSE SFACT_TASK_GROUP END,"
                + " SFACT_HANDLER_GROUP = CASE SFACT_TASK_GROUP WHEN ? THEN ? ELSE SFACT_HANDLER_GROUP END,"
                + " SFACT_PLUS_GROUP = CASE SFACT_PLUS_GROUP WHEN ? THEN ? ELSE SFACT_PLUS_GROUP END"
                + " WHERE SFACT_TASK_GROUP = ? OR SFACT_HANDLER_GROUP = ? OR SFACT_PLUS_GROUP = ?";

        sqlArgs.add(dto.getCurGroupName());
        sqlArgs.add(dto.getGroupName());
        sqlArgs.add(dto.getCurGroupName());
        sqlArgs.add(dto.getGroupName());
        sqlArgs.add(dto.getCurGroupName());
        sqlArgs.add(dto.getGroupName());
        sqlArgs.add(dto.getGroupName());
        sqlArgs.add(dto.getGroupName());
        sqlArgs.add(dto.getGroupName());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}