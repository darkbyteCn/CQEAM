package com.sino.ams.task.service.internal.mis;

import com.sino.ams.task.service.AbstractTaskService;
import com.sino.base.db.conn.DBManager;
import com.sino.base.exception.DataHandleException;
import com.sino.base.log.Logger;

import java.sql.*;

/**
 * <p>标题: SinoEAM中国移动资产管理系统后台同步任务：业务服务层对象</p>
 * <p>描述: 将数据从ZTE表转移到ETS_FA_ASSETS表</p>
 * <p>版权: 根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约审定</p>
 * <p>开发商: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */
public class ZTEAssetsData2EFAService extends AbstractTaskService {


    /**
     * <p>功能说明：将ODI插入到ZTE表的数据转移到EFA表</p>
     *
     * @throws DataHandleException 数据转移出错时抛出数据处理异常
     */
    public void readZTEAssetsData2EFA() throws DataHandleException {
        Connection conn = null;
        CallableStatement cStmt = null;
        try {
            String sqlStr = "{call dbo.AUTO_SYN_FA_ASSETS (?)}";
            conn = DBManager.getDBConnection();
            cStmt = conn.prepareCall(sqlStr);
            cStmt.registerOutParameter(1, Types.INTEGER);
            cStmt.execute();
            int a = cStmt.getInt(1);
//            System.out.println(a);
        } catch (SQLException ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex.getMessage());
        } finally {
            try {
                DBManager.closeDBStatement(cStmt);
            DBManager.closeDBConnection(conn);
            } catch (Throwable ex) {
                Logger.logError(ex);
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws DataHandleException {
        ZTEAssetsData2EFAService service = new ZTEAssetsData2EFAService();
        service.readZTEAssetsData2EFA();
    }
}
