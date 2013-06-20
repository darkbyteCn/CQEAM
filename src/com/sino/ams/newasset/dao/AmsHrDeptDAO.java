package com.sino.ams.newasset.dao;


import java.sql.Connection;

import com.sino.ams.newasset.dto.AmsHrDeptDTO;
import com.sino.ams.newasset.model.AmsHrDeptModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.dto.DTO;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;


/**
 * <p>Title: AmsHrDeptDAO</p>
 * <p>Description:程序自动生成服务程序“AmsHrDeptDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class AmsHrDeptDAO extends BaseDAO {


    /**
     * 功能：MIS部门(HR) AMS_MIS_DEPT 数据访问层构造函数
     * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsHrDeptDTO 本次操作的数据
     * @param conn Connection 数据库连接，由调用者传入。
     */
    public AmsHrDeptDAO(SfUserDTO userAccount, AmsHrDeptDTO dtoParameter,
                        Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     * @param userAccount BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsHrDeptDTO dtoPara = (AmsHrDeptDTO) dtoParameter;
        super.sqlProducer = new AmsHrDeptModel((SfUserDTO) userAccount, dtoPara);
    }
}
