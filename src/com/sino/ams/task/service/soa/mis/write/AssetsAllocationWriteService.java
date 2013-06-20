package com.sino.ams.task.service.soa.mis.write;

import com.sino.ams.task.service.soa.AbstractTaskSOAService;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.exception.DataHandleException;
import com.sino.base.log.Logger;
import com.sino.soa.service.SrvDAO;

import java.sql.Connection;

/**
 * <p>标题: SinoEAM中国移动资产管理系统后台同步任务：业务服务层对象</p>
 * <p>描述: 通过SOA服务将EAM系统发生的公司内资产调拨同步到MIS系统</p>
 * <p>版权: 根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约审定</p>
 * <p>开发商: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */
public class AssetsAllocationWriteService extends AbstractTaskSOAService {

    /**
     * <p>功能说明：将EAM系统发生的公司内资产调拨同步到MIS系统 </p>
     *
     * @throws com.sino.base.exception.DataHandleException
     *          将EAM系统发生的公司内资产调拨同步到MIS系统出错时抛数据处理异常
     */
    public void writeAssetsAllocations() throws DataHandleException {
        Connection conn = null;
        try {
            conn = getDBConnection();
            RowSet rows = getCompanyList(conn);
            if (rows != null && !rows.isEmpty()) {
                int dataCount = rows.getSize();
                SrvDAO srvDAO = new SrvDAO();
                for (int i = 0; i < dataCount; i++) {
                    Row row = rows.getRow(i);
                    String orgId = row.getStrValue("ORGANIZATION_ID");
                    int organizationId = Integer.parseInt(orgId);
                    taskExecutor = getOUTaskExecutor(conn, organizationId);
                    if (taskExecutor == null) {
                        continue;
                    }
                    srvDAO.synTransInCompany(conn, taskExecutor);
                }
            }
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex.getMessage());
        } finally {
            closeDBConnection(conn);
        }
    }
}
