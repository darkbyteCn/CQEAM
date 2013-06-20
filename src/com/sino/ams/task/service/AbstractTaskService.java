package com.sino.ams.task.service;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.task.util.BackGroundTaskUtil;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.soa.util.SynLogUtil;

import java.sql.Connection;

/**
 * <p>标题: SinoEAM中国移动资产管理系统后台同步任务：业务服务层对象</p>
 * <p>描述: 后台服务的超类</p>
 * <p>版权: 根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约审定</p>
 * <p>开发商: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */
public abstract class AbstractTaskService {

    protected SfUserDTO taskExecutor = null;

    /**
     * <p>功能说明：获取后台任务执行人。该方法仅为了记录日志方便  </p>
     * <p>以具有“系统管理员”角色的第一个用户为准</p>
     *
     * @param conn 数据库连接
     */
    protected void initTaskExecutor(Connection conn) {
        taskExecutor = BackGroundTaskUtil.getTaskExecutor(conn);
    }

    /**
     * <p>功能说明：获取数据库连接
     *
     * @return 数据库连接
     * @throws com.sino.base.exception.PoolException
     *          数据库连接出错时抛出数据库连接池异常
     */
    protected Connection getDBConnection() throws PoolException {
        return DBManager.getDBConnection();
    }


    /**
     * <p>功能说明：将数据库连接返回给连接池
     *
     * @param conn 数据库连接对象
     */
    protected void closeDBConnection(Connection conn) {
        DBManager.closeDBConnection(conn);
    }

    /**
     * <p>功能说明：获取指定同步类型上一次同步的时间
     *
     * @param synType 同步类型
     * @param conn    数据库连接
     * @return 指定同步类型上一次同步的时间
     * @throws QueryException 获取指定同步类型上一次同步的时间出错时抛出查询异常
     */
    protected String getLastUpdateDate(String synType, Connection conn) throws QueryException {
        String lastUpdateDate = "";
        try {
            SynLogUtil synLogUtil = new SynLogUtil();
            lastUpdateDate = synLogUtil.getLastUpdateDate(synType, conn);
            if (lastUpdateDate.length() == 0) {
                lastUpdateDate = "2011-11-01 00:00:00";
            }
        } catch (ContainerException ex) {
            ex.printLog();
            throw new QueryException(ex);
        }
        return lastUpdateDate;
    }

    /**
     * <p>功能说明：获取上市公司资产账簿列表(含无形资产账簿)
     *
     * @param conn 数据库连接
     * @return 上市公司资产账簿列表
     * @throws QueryException 查询数据库出错时抛出该异常
     */
    protected RowSet getMISBookTypeCodes(Connection conn) throws QueryException {
        return BackGroundTaskUtil.getMISBookTypeCodes(conn);
    }

    /**
     * <p>功能说明：获取TD公司资产账簿列表(含无形资产账簿)
     *
     * @param conn 数据库连接
     * @return TD公司资产账簿列表
     * @throws QueryException 查询数据库出错时抛出该异常
     */
    protected RowSet getTDBookTypeCodes(Connection conn) throws QueryException {
        return BackGroundTaskUtil.getTDBookTypeCodes(conn);
    }


    /**
     * <p>功能说明：获取上市公司列表
     *
     * @param conn 数据库连接
     * @return 上市公司资产账簿列表
     * @throws QueryException 查询数据库出错时抛出该异常
     */
    protected RowSet getCompanyList(Connection conn) throws QueryException {
        return BackGroundTaskUtil.getCompanyList(conn);
    }

    /**
     * <p>功能说明：获取TD公司列表
     *
     * @param conn 数据库连接
     * @return TD公司资产账簿列表
     * @throws QueryException 查询数据库出错时抛出该异常
     */
    protected RowSet getTDCompanyList(Connection conn) throws QueryException {
        return BackGroundTaskUtil.getTDCompanyList(conn);
    }

    /**
     * <p>功能说明：获取指定资产账簿最新的关闭的会计期间
     *
     * @param bookTypeCode 资产账簿代码
     * @param conn         数据库连接
     * @return 指定资产账簿最新的关闭的会计期间
     * @throws QueryException 获取数据出错时抛出查询异常
     */
    protected String getPeriodName(String bookTypeCode, Connection conn) throws QueryException {
        return BackGroundTaskUtil.getPeriodName(bookTypeCode, conn);
    }

    /**
     * <p>功能说明：获取最新关闭的会计期间</p>
     *
     * @param conn         数据库连接
     * @return 指定资产账簿最新的关闭的会计期间
     * @throws QueryException 获取最新关闭的会计期间
     */
    protected String getMaxPeriodName(Connection conn) throws QueryException {
        return BackGroundTaskUtil.getMaxPeriodName(conn);
    }

    /**
     * <p>功能说明：获取后台任务执行人，每个OU指定一个
     *
     * @param conn           数据库连接
     * @param organizationId OU组织ID
     * @return 后台任务执行人
     */
    protected SfUserDTO getOUTaskExecutor(Connection conn, int organizationId) {
        return BackGroundTaskUtil.getOUTaskExecutor(conn, organizationId);
    }
}
