package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.newasset.dto.AmsMisDeptDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTOSet;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class DeptAssignModel extends AMSSQLProducer {

    public DeptAssignModel(SfUserDTO userAccount, AmsMisDeptDTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    /**
     * 功能：获取成本中心下拉列表框的SQL
     * @return SQLModel
     */
    public SQLModel getDeptOptionsModel() {
        SQLModel sqlModel = new SQLModel();
        AmsMisDeptDTO dto = (AmsMisDeptDTO) dtoParameter;
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT"
                        + " AMD.DEPT_CODE,"
                        + " AMD.DEPT_NAME"
                        + " FROM"
                        + " AMS_MIS_DEPT AMD"
                        + " WHERE"
                        + " AMD.COMPANY_CODE = ?"
                        + " AND AMD.DEPT_NAME LIKE dbo.NVL(?, AMD.DEPT_NAME)";
        sqlArgs.add(userAccount.getCompanyCode());
        sqlArgs.add(dto.getDeptName());
        if (!userAccount.isComAssetsManager()) { //不是公司资产管理员
            String dept = "'";
            if (userAccount.isDptAssetsManager()) { //是部门资产管理员
                DTOSet depts = userAccount.getPriviDeptCodes();
                AmsMisDeptDTO deptDTO = null;
                if( null != depts ){
	                for (int i = 0; i < depts.getSize(); i++) {
	                    deptDTO = (AmsMisDeptDTO) depts.getDTO(i);
	                    dept += deptDTO.getDeptCode() + "'";
	                    if (i < depts.getSize() - 1) {
	                        dept += ", '";
	                    }
	                }
                }
            } else {
                dept += "'";
            }
            sqlStr += " AND AMD.DEPT_CODE IN (" + dept + ")";
        }
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
