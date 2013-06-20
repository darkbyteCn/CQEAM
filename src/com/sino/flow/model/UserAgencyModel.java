package com.sino.flow.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.flow.dto.UserAgencyDTO;

/**
 * Created by IntelliJ IDEA.
 * User: Lijun
 * Date: 2007-1-23
 * Time: 10:10:23
 * To change this template use File | Settings | File Templates.
 */
public class UserAgencyModel {
    private SQLModel sqlModel = null;
    private UserAgencyDTO dtoParameter = null;

    public UserAgencyDTO getDtoParameter() {
        return dtoParameter;
    }

    public void setDtoParameter(UserAgencyDTO dtoParameter) {
        this.dtoParameter = dtoParameter;
    }

    public UserAgencyModel() {
        this.sqlModel = new SQLModel();
        this.dtoParameter = new UserAgencyDTO();
    }

    public SQLModel getProdUserAgencyModel() {
        List strArg = new ArrayList();
        String strSql =
                "SELECT SP.USERNAME  AGENT_USER_NAME, SP.USER_ID AGENT_USER_ID, SFA.USER_ID\n" +
                        "  FROM SF_FLOW_AGENT SFA, SF_USER SP\n" +
                        " WHERE SP.USER_ID = SFA.AGENT_USER_ID\n" +
                        "   AND SFA.ACTIVE_END_DATE >= TRUNC(GETDATE(), 'DD')\n" +
                        "   AND SFA.DISABLE_DATE " + SyBaseSQLUtil.isNullNoParam() + " \n" +
                        "   AND SFA.USER_ID = ?";
        strArg.add(dtoParameter.getUserId());
        sqlModel.setArgs(strArg);
        sqlModel.setSqlStr(strSql);
        return sqlModel;
    }

    public SQLModel getProdUserAgencyDetailModel() {
        List strArg = new ArrayList();
        String strSql ="SELECT DISTINCT SP.USERNAME AGENT_USER_NAME,\n" +
                "                SP.USER_ID AGENT_USER_ID,\n" +
                "                '' DEPT_ID,\n" +
                "                '' DEPT_NAME,\n" +
                "                SFA.ACTIVE_START_DATE,\n" +
                "                SFA.ACTIVE_END_DATE,\n" +
                "                SFA.NOTE,\n" +
                "                SFA.USER_ID,\n" +
                "                SFA.ID\n" +
                "  FROM SF_FLOW_AGENT SFA, SF_USER SP\n" +
                " WHERE SFA.AGENT_USER_ID = SP.USER_ID\n" +
                "   AND SFA.DISABLE_DATE " + SyBaseSQLUtil.isNullNoParam() + " \n" +
                "   AND SFA.USER_ID = ?\n" +
                "   AND SFA.AGENT_USER_ID = ?";

        strArg.add(dtoParameter.getUserId());
        strArg.add(dtoParameter.getAgentUserId());
        sqlModel.setArgs(strArg);
        sqlModel.setSqlStr(strSql);
        return sqlModel;
    }

    public SQLModel getInsertNewUserAgencyModel() {
        List strArg = new ArrayList();
        String strSql =
                "INSERT INTO SF_FLOW_AGENT " +
                        "  (ID, " +
                        "   USER_ID, " +
                        "   AGENT_USER_ID, " +
                        "   ACTIVE_START_DATE, " +
                        "   ACTIVE_END_DATE, " +
                        "   PROC_ID, " +
                        "   DEPT_ID, " +
                        "   NOTE, " +
                        "   DISABLE_DATE) " +
                        "VALUES " +
                        "  (?, ?, ?, TO_DATE(?, 'YYYY-MM-DD'), TO_DATE(?, 'YYYY-MM-DD'), ?, ?, ?, TO_DATE(?, 'YYYY-MM-DD'))";
        strArg.add(dtoParameter.getId());
        strArg.add(dtoParameter.getUserId());
        strArg.add(dtoParameter.getAgentUserId());
        strArg.add(dtoParameter.getActiveStartDate());
        strArg.add(dtoParameter.getActiveEndDate());
        strArg.add(dtoParameter.getProcId());
        strArg.add(dtoParameter.getDeptId());
        strArg.add(dtoParameter.getNote());
        strArg.add(dtoParameter.getDisableDate());
        sqlModel.setArgs(strArg);
        sqlModel.setSqlStr(strSql);
        return sqlModel;
    }

    public SQLModel getUpdateUserAgencyModel() {
        List strArg = new ArrayList();
        String strStr =
                "UPDATE SF_FLOW_AGENT IFA " +
                        "   SET IFA.AGENT_USER_ID     = ?, " +
                        "       IFA.ACTIVE_START_DATE = TO_DATE(?, 'YYYY-MM-DD'), " +
                        "       IFA.ACTIVE_END_DATE   = TO_DATE(?, 'YYYY-MM-DD'), " +
                        "       IFA.DEPT_ID           = ?, " +
                        "       IFA.NOTE              = ? " +
                        " WHERE IFA.ID = ? ";
        strArg.add(dtoParameter.getAgentUserId());
        strArg.add(dtoParameter.getActiveStartDate());
        strArg.add(dtoParameter.getActiveEndDate());
        strArg.add(dtoParameter.getDeptId());
        strArg.add(dtoParameter.getNote());
        strArg.add(dtoParameter.getId());
        sqlModel.setArgs(strArg);
        sqlModel.setSqlStr(strStr);
        return sqlModel;
    }

    public SQLModel getDisableUserAgencyModel() {
        List strArg = new ArrayList();
        String strSql = "UPDATE SF_FLOW_AGENT IFA " +
                "   SET IFA.DISABLE_DATE = GETDATE() " +
                " WHERE IFA.ID = ? ";
        strArg.add(dtoParameter.getId());
        sqlModel.setArgs(strArg);
        sqlModel.setSqlStr(strSql);
        return sqlModel;
    }

    //当代理人失效时，代理人的信息全部转到代办人的信箱中
    public SQLModel updateSfActInfo(String userId) {
        String sql = "UPDATE SF_ACT_INFO SAI SET SAI.AGENT_USER_ID = NULL WHERE SAI.USER_ID = ?";
        ArrayList al = new ArrayList();
        al.add(userId);
        SQLModel sm = new SQLModel();
        sm.setSqlStr(sql);
        sm.setArgs(al);
        return sm;
    }

    public SQLModel addAgntInActInfo(String userId) {
        String sql="UPDATE SF_ACT_INFO SAI SET SAI.AGENT_USER_ID = ? WHERE SAI.USER_ID = ?" ;
        ArrayList al = new ArrayList();
        al.add(dtoParameter.getAgentUserId());
        al.add(userId);
        SQLModel sm=new SQLModel();
        sm.setSqlStr(sql);
        sm.setArgs(al);
        return sm;
    }
}
