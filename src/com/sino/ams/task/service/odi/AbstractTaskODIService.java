package com.sino.ams.task.service.odi;


import com.sino.ams.task.service.AbstractTaskService;
import com.sino.ams.task.util.BackGroundTaskUtil;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.soa.util.SynUpdateDateUtils;

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
public abstract class AbstractTaskODIService extends AbstractTaskService {

    protected String odiServiceName = "";

    public AbstractTaskODIService() {
        super();
        initODIServiceName();
    }

    /**
     * <p>功能说明：初始化ODI服务名 </p>
     */
    protected abstract void initODIServiceName();

    /**
     * <p>功能说明：获取ODI服务的代码  </p>
     *
     * @param conn 数据库连接
     * @return 成功则返回ODI服务的代码，否则返回空字符串
     * @throws QueryException 查询ODI服务代码出错时抛出该查询异常
     */
    protected String getODIEnvCode(Connection conn) throws QueryException {
        String envCode = "";
        try {
            if (!StrUtil.isEmpty(odiServiceName)) {
                envCode = SynUpdateDateUtils.getEnvCode(odiServiceName, conn);
            }
        } catch (ContainerException ex) {
            ex.printLog();
            throw new QueryException(ex);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new QueryException(ex.getMessage());
        }
        return envCode;
    }
}
