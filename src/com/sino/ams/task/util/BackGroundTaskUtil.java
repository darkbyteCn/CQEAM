package com.sino.ams.task.util;


import java.sql.Connection;

import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.task.model.TaskModelProducer;
import com.sino.soa.common.MIS_CONSTANT;

/**
 * <p>标题: SinoEAM中国移动资产管理系统后台同步任务</p>
 * <p>描述: 后台任务工具类</p>
 * <p>版权: 根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约审定</p>
 * <p>开发商: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */
public abstract class BackGroundTaskUtil {


    /**
     * <p>功能说明：获取上市公司列表 </p>
     *
     * @param conn 数据库连接
     * @return 上市公司资产账簿列表
     * @throws QueryException 查询数据库出错时抛出该异常
     */
    public static RowSet getCompanyList(Connection conn) throws QueryException {
        SQLModel sqlModel = TaskModelProducer.getCompanyListModel("N");
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        return sq.getSearchResult();
    }

    /**
     * <p>功能说明：获取TD公司列表
     *
     * @param conn 数据库连接
     * @return TD公司资产账簿列表
     * @throws QueryException 查询数据库出错时抛出该异常
     */
    public static RowSet getTDCompanyList(Connection conn) throws QueryException {
        SQLModel sqlModel = TaskModelProducer.getCompanyListModel("Y");
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        return sq.getSearchResult();
    }


    /**
     * <p>功能说明：获取上市公司资产账簿列表(含无形资产账簿)
     *
     * @param conn 数据库连接
     * @return 上市公司资产账簿列表
     * @throws QueryException 查询数据库出错时抛出该异常
     */
    public static RowSet getMISBookTypeCodes(Connection conn) throws QueryException {
        SQLModel sqlModel = TaskModelProducer.getBookTypeCodeModel("N");
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        return sq.getSearchResult();
    }

    /**
     * <p>功能说明：获取TD公司资产账簿列表(含无形资产账簿)
     *
     * @param conn 数据库连接
     * @return TD公司资产账簿列表
     * @throws QueryException 查询数据库出错时抛出该异常
     */
    public static RowSet getTDBookTypeCodes(Connection conn) throws QueryException {
        SQLModel sqlModel = TaskModelProducer.getBookTypeCodeModel("Y");
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        return sq.getSearchResult();
    }

    /**
     * <p>功能说明：获取MIS值集名称列表
     *
     * @param conn 数据库连接
     * @return MIS值集名称列表
     * @throws QueryException 查询数据库出错时抛出该异常
     */
    public static RowSet getMISFlexValueSets(Connection conn) throws QueryException {
        SQLModel sqlModel = TaskModelProducer.getFlexValueSetModel("MIS");
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        return sq.getSearchResult();
    }

    /**
     * <p>功能说明：获取TD值集名称列表
     *
     * @param conn 数据库连接
     * @return TD值集名称列表
     * @throws QueryException 查询数据库出错时抛出该异常
     */
    public static RowSet getTDFlexValueSets(Connection conn) throws QueryException {
        SQLModel sqlModel = TaskModelProducer.getFlexValueSetModel(MIS_CONSTANT.SOURCE_TD);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        return sq.getSearchResult();
    }

    /**
     * <p>功能说明：获取后台任务执行人
     * <p>以具有“系统管理员”角色的第一个用户为准</p>
     *
     * @param conn 数据库连接
     * @return 后台任务执行人
     */
    public static SfUserDTO getTaskExecutor(Connection conn) {
        SfUserDTO executor = null;
        try {
            SQLModel sqlModel = TaskModelProducer.getTaskExecutorModel();
            SimpleQuery sq = new SimpleQuery(sqlModel, conn);
            sq.setDTOClassName(SfUserDTO.class.getName());
            sq.executeQuery();
            if (sq.hasResult()) {
                executor = (SfUserDTO) sq.getFirstDTO();
            }
        } catch (Throwable ex) {
            Logger.logError(ex);
        }
        return executor;
    }

    /**
     * <p>功能说明：获取后台任务执行人，每个OU指定一个
     *
     * @param conn           数据库连接
     * @param organizationId OU组织ID
     * @return 后台任务执行人
     */
    public static SfUserDTO getOUTaskExecutor(Connection conn, int organizationId) {
        SfUserDTO executor = null;
        try {
            SQLModel sqlModel = TaskModelProducer.getOUTaskExecutorModel(organizationId);
            SimpleQuery sq = new SimpleQuery(sqlModel, conn);
            sq.setDTOClassName(SfUserDTO.class.getName());
            sq.executeQuery();
            if (sq.hasResult()) {
                executor = (SfUserDTO) sq.getFirstDTO();
            }
        } catch (Throwable ex) {
            Logger.logError(ex);
        }
        return executor;
    }


    /**
     * <p>功能说明：获取指定资产账簿最新的关闭的会计期间
     *
     * @param bookTypeCode 资产账簿代码
     * @param conn         数据库连接
     * @return 指定资产账簿最新的关闭的会计期间
     * @throws QueryException 获取数据出错时抛出查询异常
     */
    public static String getPeriodName(String bookTypeCode, Connection conn) throws QueryException {
        String periodName = "";
        try {
            SQLModel sqlModel = TaskModelProducer.getPeriodNameModel(bookTypeCode);
            SimpleQuery sq = new SimpleQuery(sqlModel, conn);
            sq.executeQuery();
            if (sq.hasResult()) {
                Row row = sq.getFirstRow();
                row.getStrValue("MIS_PERIOD_NAME");
            }
        } catch (ContainerException ex) {
            ex.printLog();
            throw new QueryException(ex);
        }
        return periodName;
    }

    /**
     * <p>功能说明：获取最新关闭的会计期间
     *
     * @param conn         数据库连接
     * @return 获取最新关闭的会计期间
     * @throws QueryException 获取数据出错时抛出查询异常
     */
    public static String getMaxPeriodName(Connection conn) throws QueryException {
        String periodName = "";
        try {
            SQLModel sqlModel = TaskModelProducer.getMaxPeriodNameModel();
            SimpleQuery sq = new SimpleQuery(sqlModel, conn);
            sq.executeQuery();
            if (sq.hasResult()) {
                Row row = sq.getFirstRow();
                periodName=row.getStrValue("MIS_PERIOD_NAME");
            }
        } catch (ContainerException ex) {
            ex.printLog();
            throw new QueryException(ex);
        }
        return periodName;
    }
}
