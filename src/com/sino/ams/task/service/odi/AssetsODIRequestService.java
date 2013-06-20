package com.sino.ams.task.service.odi;

import com.sino.ams.task.service.AbstractTaskService;
import com.sino.base.exception.DataHandleException;
import com.sino.base.log.Logger;
import com.sino.soa.service.SrvDAO;
import com.sino.soa.service.TDSrvDAO;

import java.sql.Connection;

/**
 * <p>标题: SinoEAM中国移动资产管理系统后台同步任务：业务服务层对象</p>
 * <p>描述: 通过ODI服务读取上市公司和TD公司MIS系统资产头基本信息</p>
 * <p>版权: 根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约审定</p>
 * <p>开发商: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */
public class AssetsODIRequestService extends AbstractTaskService {

    /**
     * <p>功能说明：读取“上市公司和TD公司MIS系统资产头基本信息(ODI)”</p>
     *
     * @throws DataHandleException 读取数据失败时抛出该异常。
     */
    public void requestODI2ProcessAssets() throws DataHandleException {
        Connection conn = null;
        try {
            conn = getDBConnection();
            initTaskExecutor(conn);
            SrvDAO srvDAO = new SrvDAO();
            srvDAO.synAssetHeaderInfo(conn, taskExecutor);
            srvDAO.synAssetDistribute(conn, taskExecutor);

            TDSrvDAO tdSrvDAO = new TDSrvDAO();
            tdSrvDAO.synAssetHeaderInfo(conn, taskExecutor);
            tdSrvDAO.synAssetDistribute(conn, taskExecutor);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex.getMessage());
        } finally {
            closeDBConnection(conn);
        }
    }
}
