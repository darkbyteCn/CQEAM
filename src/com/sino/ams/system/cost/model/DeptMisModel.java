package com.sino.ams.system.cost.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.cost.dto.AmsMisCostMatchDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.security.dto.ServletConfigDTO;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2008-7-31
 * Time: 13:35:27
 * To change this template use File | Settings | File Templates.
 */
public class DeptMisModel extends AMSSQLProducer {
    public DeptMisModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    public void setServletConfig(ServletConfigDTO servletConfig) {
        super.setServletConfig(servletConfig);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public SQLModel getPageQueryModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsMisCostMatchDTO dto = (AmsMisCostMatchDTO) dtoParameter;
        String sqlStr = "SELECT \n" +
                        " AMD.COMPANY_CODE, \n" +
                        " AMD.DEPT_CODE, \n" +
                        " AMD.DEPT_NAME, \n" +
                        " AMD.LAST_UPDATE_DATE\n" +
                        " FROM " +
                        " AMS_MIS_DEPT AMD" +
                        " WHERE " +
                        " NOT EXISTS (SELECT 1\n" +
                        " FROM AMS_COST_DEPT_MATCH AMDM\n" +
                        " WHERE AMDM.DEPT_CODE = AMD.DEPT_CODE)" +
                        " AND ( " + SyBaseSQLUtil.isNull() + "  OR AMD.DEPT_CODE LIKE ?) "+
                        " AND ( " + SyBaseSQLUtil.isNull() + "  OR AMD.DEPT_NAME LIKE ?) ";

        sqlArgs.add(dto.getDeptCode());
        sqlArgs.add(dto.getDeptCode());
        sqlArgs.add(dto.getDeptName());
        sqlArgs.add(dto.getDeptName());
                        
          if ((!userAccount.isProvinceUser()) && (!userAccount.isSysAdmin())) {
                 sqlStr +="AND AMD.COMPANY_CODE= ?\n" ;
              sqlArgs.add(userAccount.getCompanyCode());
          }

        sqlStr += "ORDER BY AMD.DEPT_NAME, AMD.DEPT_CODE";
        
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
