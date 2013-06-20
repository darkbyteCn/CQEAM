package com.sino.ams.newasset.dao;

import java.sql.Connection;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: SinoEAMS</p>
 * <p>Description: 打印调入方新标签号</p>
 * <p>Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2007 - 2008</p>
 * <p>Company: 北京思诺搏信息技术有限公司</p>
 * @author 何睿
 * @version 0.1
 *          Date: 2008-10-14
 */
public class NewAssetsTagPrintDAO extends AMSBaseDAO {
    public NewAssetsTagPrintDAO(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {

    }
}
