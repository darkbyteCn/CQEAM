package com.sino.ams.task.service.soa.td.write;

import com.sino.ams.task.dao.soa.td.TDLocationSegment2SearchDAO;
import com.sino.ams.task.service.soa.AbstractTaskSOAService;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.util.ArrUtil;
import com.sino.soa.mis.srv.assetLoc2Comb.dao.SrvAssetLoc2CombDAO;
import com.sino.soa.mis.srv.assetLocComb.dao.SrvAssetLocCombDAO;

import java.sql.Connection;

/**
 * <p>标题: SinoEAM中国移动资产管理系统后台同步任务：业务服务层对象</p>
 * <p>描述: 通过SOA服务将EAM系统发生的地点第二段变动同步到TD系统</p>
 * <p>版权: 根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约审定</p>
 * <p>开发商: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */
public class TDLocationSegment2WriteService extends AbstractTaskSOAService {

    /**
     * <p>功能说明：将EAM系统发生的地点第二段变动同步到TD系统 </p>
     *
     * @throws com.sino.base.exception.DataHandleException
     *          将EAM系统发生的地点第二段变动同步到TD系统出错时抛数据处理异常
     */
    public void writeTDLocationSegment2() throws DataHandleException {
        Connection conn = null;
        try {
            conn = getDBConnection();
            RowSet rows = getTDCompanyList(conn);
            if (rows != null && !rows.isEmpty()) {
                int dataCount = rows.getSize();

                for (int i = 0; i < dataCount; i++) {
                    Row row = rows.getRow(i);
                    String orgId = row.getStrValue("ORGANIZATION_ID");
                    int organizationId = Integer.parseInt(orgId);
                    taskExecutor = getOUTaskExecutor(conn, organizationId);
                    if (taskExecutor == null) {
                        continue;
                    }
                    String[] objectNos = getOU2SynchronizeLocationSegment2(conn, organizationId);
                    if (objectNos == null || objectNos.length == 0) {
                        continue;
                    }
                    SrvAssetLoc2CombDAO assetsUpdateDAO = new SrvAssetLoc2CombDAO(taskExecutor, null, conn);
                    assetsUpdateDAO.synLocComb(objectNos, "Y");
                }
            }
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex.getMessage());
        } finally {
            closeDBConnection(conn);
        }
    }



    /**
     * <p>功能说明：获取指定OU待同步的物理地点数据</p>
     *
     * @param conn           数据库连接
     * @param organizationId OU组织ID
     * @return 待同步的资产ID数组
     * @throws com.sino.base.exception.QueryException
     *          查询数据出错时抛出该异常
     */
    private String[] getOU2SynchronizeLocationSegment2(Connection conn, int organizationId) throws QueryException {
        String[] objects = null;
        try {
            TDLocationSegment2SearchDAO searchDAO = new TDLocationSegment2SearchDAO(taskExecutor, null, conn);
            RowSet rows = searchDAO.getOU2SynchronizeLocationSegment2(organizationId);
            if (rows != null && !rows.isEmpty()) {
                int dataCount = rows.getSize();
                objects = new String[dataCount];
                for (int i = 0; i < dataCount; i++) {
                    Row row = rows.getRow(i);
                    objects[i] = row.getStrValue("LOC2_CODE");
                    objects[i] += ",";
                    objects[i] += row.getStrValue("LOC2_DESC");
                }
            }
        } catch (ContainerException ex) {
            ex.printLog();
            throw new QueryException(ex);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new QueryException(ex.getMessage());
        }
        return objects;
    }
}

