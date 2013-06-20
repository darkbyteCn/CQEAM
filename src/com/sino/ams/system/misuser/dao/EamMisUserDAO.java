package com.sino.ams.system.misuser.dao;

import java.sql.Connection;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.system.misuser.dto.EamMisUserDTO;
import com.sino.ams.system.misuser.model.EamMisUserModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2009-1-6
 * Time: 11:08:38
 * To change this template use File | Settings | File Templates.
 */
public class EamMisUserDAO extends AMSBaseDAO {
    private SfUserDTO sfUser = null;
      public EamMisUserDAO(SfUserDTO userAccount, EamMisUserDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        sfUser = userAccount;
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     *
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        EamMisUserDTO dtoPara = (EamMisUserDTO) dtoParameter;
        super.sqlProducer = new EamMisUserModel((SfUserDTO) userAccount, dtoPara);
    }
}
