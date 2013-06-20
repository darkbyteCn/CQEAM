package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class PersonAssignModel extends AMSSQLProducer {

    public PersonAssignModel(SfUserDTO userAccount,
                             AmsAssetsAddressVDTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    /**
     * 功能：获取人员下拉列表框的SQL
     * @return SQLModel
     */
    public SQLModel getPersonOptionsModel() {
        SQLModel sqlModel = new SQLModel();
        AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT"
                        + " AME.EMPLOYEE_ID,"
                        + " AME.USER_NAME"
                        + " FROM"
                        + " AMS_MIS_EMPLOYEE AME"
                        + " WHERE"
                        + " AME.USER_NAME LIKE dbo.NVL(?, USER_NAME)"
                        + " AND AME.DEPT_CODE = ?";
        sqlArgs.add(dto.getResponsibilityUserName());
        sqlArgs.add(dto.getDeptCode());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
