package com.sino.ams.newasset.dao;

import java.sql.Connection;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.dto.AmsMisDeptDTO;
import com.sino.ams.newasset.model.DeptAssignModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.QueryException;
import com.sino.base.web.DatabaseForWeb;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class DeptAssignDAO extends AMSBaseDAO {


    public DeptAssignDAO(SfUserDTO userAccount, AmsMisDeptDTO dtoParameter,
                         Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    /**
     * 功能：SQL生成器baseSQLProducer的初始化。具体的DAO继承时初始化具体的SQL生成器。
     * @param userAccount BaseUserDTO
     * @param dtoParameter DTO
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsMisDeptDTO dto = (AmsMisDeptDTO) dtoParameter;
        sqlProducer = new DeptAssignModel((SfUserDTO) userAccount, dto);
    }

    /**
     * 功能：获取部门下拉列表框
     * @param selectedDept String
     * @return String
     * @throws QueryException
     */
    public String getDeptOptions(String selectedDept) throws QueryException {
        DeptAssignModel modelProducer = (DeptAssignModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getDeptOptionsModel();
        DatabaseForWeb optProducer = new DatabaseForWeb(sqlModel, conn);
        return optProducer.getOptionHtml(selectedDept);
    }
}
