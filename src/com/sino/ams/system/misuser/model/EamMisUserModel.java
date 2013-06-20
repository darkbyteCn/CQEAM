package com.sino.ams.system.misuser.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.misuser.dto.EamMisUserDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;

/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2009-1-6
 * Time: 11:08:27
 * To change this template use File | Settings | File Templates.
 */
public class EamMisUserModel extends AMSSQLProducer {
    private SfUserDTO sfUser = null;


       public EamMisUserModel(SfUserDTO userAccount, EamMisUserDTO dtoParameter) {
           super(userAccount, dtoParameter);
           sfUser = userAccount;
       }
        public SQLModel getPageQueryModel() throws SQLModelException {   //查询使用的sql
        SQLModel sqlModel = new SQLModel();
//             try {
        List sqlArgs = new ArrayList();
        EamMisUserDTO amsLandInfo = (EamMisUserDTO) dtoParameter;
        String sqlStr ="SELECT AME.USER_NAME, AMD.DEPT_NAME, AME.EMPLOYEE_NUMBER, EOCM.COMPANY\n" +
                "  FROM AMS_MIS_DEPT AMD, AMS_MIS_EMPLOYEE AME, ETS_OU_CITY_MAP EOCM\n" +
                " WHERE AMD.DEPT_CODE = AME.DEPT_CODE\n" +
                "   AND EOCM.COMPANY_CODE = AMD.COMPANY_CODE\n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR AME.USER_NAME LIKE ?)\n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR AME.EMPLOYEE_NUMBER LIKE ?)\n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR AMD.DEPT_NAME LIKE ?)\n" +
                "   AND EOCM.ORGANIZATION_ID = ISNULL(?, EOCM.ORGANIZATION_ID)";

        sqlArgs.add(amsLandInfo.getUserName());
        sqlArgs.add(amsLandInfo.getUserName());
        sqlArgs.add(amsLandInfo.getEmployeeNumber());
        sqlArgs.add(amsLandInfo.getEmployeeNumber());
        sqlArgs.add(amsLandInfo.getDeptName());
        sqlArgs.add(amsLandInfo.getDeptName());
        sqlArgs.add(amsLandInfo.getOrganizationId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
//        } catch (CalendarException ex) {
//            ex.printLog();
//            throw new SQLModelException(ex);
//        }
        return sqlModel;
    }
    
}
