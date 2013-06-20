package com.sino.ams.task.service.soa;


import com.sino.ams.task.service.AbstractTaskService;
import com.sino.ams.task.util.BackGroundTaskUtil;
import com.sino.base.data.RowSet;
import com.sino.base.exception.QueryException;

import java.sql.Connection;

/**
 * <p>标题: SinoEAM中国移动资产管理系统后台同步任务：业务服务层对象</p>
 * <p>描述: ODI后台服务的超类</p>
 * <p>版权: 根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约审定</p>
 * <p>开发商: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */
public abstract class AbstractTaskSOAService extends AbstractTaskService {

    public AbstractTaskSOAService() {
        super();
    }


    /**
     * <p>功能说明：获取MIS值集名称列表 </p>
     *
     * @param conn 数据库连接
     * @return MIS值集名称列表
     * @throws QueryException 查询数据库出错时抛出该异常
     */
    protected RowSet getMISFlexValueSets(Connection conn) throws QueryException {
        return BackGroundTaskUtil.getMISFlexValueSets(conn);
    }

    /**
     * <p>功能说明：获取TD值集名称列表
     *
     * @param conn 数据库连接
     * @return TD值集名称列表
     * @throws QueryException 查询数据库出错时抛出该异常
     */
    protected RowSet getTDFlexValueSets(Connection conn) throws QueryException {
        return BackGroundTaskUtil.getTDFlexValueSets(conn);
    }
}
