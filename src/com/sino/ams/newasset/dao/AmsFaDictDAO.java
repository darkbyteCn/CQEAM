package com.sino.ams.newasset.dao;


import java.sql.Connection;

import com.sino.base.dto.DTO;
import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.dto.AmsFaDictDTO;
import com.sino.ams.newasset.model.AmsFaDictModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.dto.BaseUserDTO;


/**
 * <p>Title: AmsFaDictDAO</p>
 * <p>Description:程序自动生成服务程序“AmsFaDictDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class AmsFaDictDAO extends AMSBaseDAO {


    public AmsFaDictDAO(SfUserDTO userAccount, AmsFaDictDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsFaDictDTO dtoPara = (AmsFaDictDTO) dtoParameter;
        super.sqlProducer = new AmsFaDictModel((SfUserDTO) userAccount, dtoPara);
    }
}
