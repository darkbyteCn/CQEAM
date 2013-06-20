package com.sino.ams.net.equip.model;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.net.equip.dto.IntadgratedQueryDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;

/**
 * <p>Title: AssetsCommQueryModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsAssetsCommQueryModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */
public class IntadgratedQueryModel extends AMSSQLProducer {

    /**
     * 功能：资产综合查询设置 AMS_ASSETS_COMM_QUERY 数据库SQL构造层构造函数
     * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsAssetsCommQueryDTO 本次操作的数据
     */
    public IntadgratedQueryModel(SfUserDTO userAccount, IntadgratedQueryDTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    /**
     * 功能：返回页面翻页查询时所需要的SQLModel
     * <B>默认为空实现。可由具体应用选择是否需要实现</B>
     * @return SQLModel
     * @throws com.sino.base.exception.SQLModelException
     */
    public SQLModel getPageQueryModel() throws SQLModelException {
        return null;
    }
}
