package com.sino.sinoflow.appbase.model;

import com.sino.base.dto.DTO;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;


/**
 * <p>Title: SinoFlo</p>
 * <p>Description: </p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public abstract class SFSQLProducer extends BaseSQLProducer {//该类的存在是为了免去其他SQLProducer类处处定义用户对象
    protected SfUserBaseDTO userAccount = null;

    public SFSQLProducer(SfUserBaseDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
        this.userAccount = (SfUserBaseDTO)userAccount;
    }
}