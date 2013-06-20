package com.sino.ams.task.service.internal;

import com.sino.ams.task.dao.internal.AccountPeriodSearchDAO;
import com.sino.ams.task.service.AbstractTaskService;
import com.sino.base.db.conn.DBManager;
import com.sino.base.exception.DataHandleException;
import com.sino.base.log.Logger;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

/**
 * <p>标题: SinoEAM中国移动资产管理系统后台同步任务：业务服务层对象</p>
 * <p>描述: 报表跑数的后台服务，该服务仅用于启动存储过程</p>
 * <p>版权: 根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约审定</p>
 * <p>开发商: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */
public class ReportDataProduceService extends AbstractTaskService {


    /**
     * <p>功能说明：从各相关应用表生成报表数据到报表相关模型表。</p>
     *
     * @throws com.sino.base.exception.DataHandleException
     *          数据转移出错时抛出数据处理异常
     */
    public void produceReportData() throws DataHandleException {
        Connection conn = null;
        CallableStatement cStmt = null;
        try {
            conn = DBManager.getDBConnection();
            initTaskExecutor(conn);
            AccountPeriodSearchDAO accountPeriodDAO = new AccountPeriodSearchDAO(taskExecutor, conn);
            String accountPeriod = accountPeriodDAO.getMaxAccountPeriod();
            if (accountPeriod.length() > 0) {
                String sqlStr = "{call dbo.APP_EAM_FA_ANALYSIS_DATA(?,?)}";
                cStmt = conn.prepareCall(sqlStr);
                cStmt.setString(1, accountPeriod);
                cStmt.registerOutParameter(2, Types.INTEGER);
                cStmt.execute();
            }
        } catch (SQLException ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex.getMessage());
        } finally {
            DBManager.closeDBStatement(cStmt);
            DBManager.closeDBConnection(conn);
        }
    }
}
