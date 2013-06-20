package com.sino.ams.system.dept.model;

import com.sino.framework.sql.BaseSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.system.fixing.dto.EtsItemInfoDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.calen.SimpleCalendar;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2009-7-9
 * Time: 10:18:19
 * To change this template use File | Settings | File Templates.
 */
public class AmsItemDeptConfirmModel extends BaseSQLProducer {
    private SfUserDTO sfUser = null;

    public AmsItemDeptConfirmModel(SfUserDTO userAccount, EtsItemInfoDTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
    }
         public SQLModel getPageQueryModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
            EtsItemInfoDTO dto = (EtsItemInfoDTO) dtoParameter;
        String sqlStr = "SELECT EOCM.COMPANY,\n" +
                "       AMD.DEPT_NAME,\n" +
                "       CASE AMD.ENABLED WHEN 'Y ' THEN 'ÓÐÐ§' WHEN 'N' THEN 'Ê§Ð§' END ENABLED_NAME,\n" +
                "       AMD.DEPT_CODE,\n" +
                "       EOCM.ORGANIZATION_ID," +
                "       AMD.COMPANY_CODE\n" +
                "  FROM AMS_MIS_DEPT AMD, ETS_OU_CITY_MAP EOCM\n" +
                " WHERE AMD.COMPANY_CODE = EOCM.COMPANY_CODE\n" +
                "   AND EXISTS (SELECT 'A'\n" +
                "          FROM ETS_ITEM_INFO EII\n" +
                "         WHERE EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE)\n" +
               // "  AND EOCM.ORGANIZATION_ID = ISNULL(?, EOCM.ORGANIZATION_ID)\n" +
                "   AND ( ?=-1 OR EOCM.ORGANIZATION_ID=? )\n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR  AMD.DEPT_NAME LIKE '%"+dto.getDeptName()+"%' )\n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + " OR AMD.ENABLED=? )\n" +
                " ORDER BY EOCM.COMPANY";
             sqlArgs.add(dto.getOrganizationId());
             sqlArgs.add(dto.getOrganizationId());
             sqlArgs.add(dto.getDeptName());
             //sqlArgs.add(dto.getDeptName());
             sqlArgs.add(dto.getAttribute1());
             sqlArgs.add(dto.getAttribute1());
               sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
         return sqlModel;
         }
     public SQLModel getHasUnknowModel(String sysId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr ="SELECT 'A' FROM AMS_ITEM_DEPT_LOG T WHERE T.DEPT_CODE = ?";
        sqlArgs.add(sysId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
       public SQLModel updateLogModel(String oldDept,String newDept)throws  SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE AMS_ITEM_DEPT_LOG \n" +
                "   SET OLD_DEPT_CODE    = ?,\n" +
                "       NEW_DEPT_CODE    = ?,\n" +
                "       LAST_UPDATE_DATE = GETDATE(),\n" +
                "       LAST_UPDATE_BY   = ?\n" +
                " WHERE DEPT_CODE = ?";
        sqlArgs.add(oldDept);
        sqlArgs.add(newDept);
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(oldDept);

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }
     public SQLModel insertLogModel(String oldDept,String newDept)throws  SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO AMS_ITEM_DEPT_LOG\n" +
                "  (LOG_ID,\n" +
                "   OLD_DEPT_CODE,\n" +
                "   NEW_DEPT_CODE,\n" +
                "   CREATION_DATE,\n" +
                "   CREATED_BY,\n" +
                "   DEPT_CODE)\n" +
                "   VALUES(("+SyBaseSQLUtil.NEW_ID_FUNCTION+"),?,?,GETDATE(),?,?)";
        sqlArgs.add(oldDept);
        sqlArgs.add(newDept);
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(oldDept);

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }
    public SQLModel insertLogInfoModel(String oldDept,String newDept)throws  SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO AMS_ITEM_DEPT_LOG_INFO\n" +
                "  (LOG_ID,\n" +
                "   OLD_DEPT_CODE,\n" +
                "   NEW_DEPT_CODE,\n" +
                "   CREATION_DATE,\n" +
                "   CREATED_BY,\n" +
                "   DEPT_CODE)\n" +
                "   VALUES(("+SyBaseSQLUtil.NEW_ID_FUNCTION+"),?,?,GETDATE(),?,?)";
        sqlArgs.add(oldDept);
        sqlArgs.add(newDept);
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(oldDept);

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }
}
