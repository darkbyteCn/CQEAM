package com.sino.ams.task.dao.internal;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.task.model.internal.AccountPeriodSearchModel;
import com.sino.base.data.Row;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;

import java.sql.Connection;

/**
 * <p>标题: SinoEAM中国移动资产管理系统后台同步程序：数据访问层对象</p>
 * <p>描述: 查询已关闭的最大会计期间</p>
 * <p>版权: 根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约审定</p>
 * <p>开发商: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */
public class AccountPeriodSearchDAO extends AMSBaseDAO {

    public AccountPeriodSearchDAO(BaseUserDTO userAccount, Connection conn) {
        super(userAccount, null, conn);
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        sqlProducer = new AccountPeriodSearchModel(userAccount, dtoParameter);
    }

    /**
     * <p>功能说明：获取最大的已关闭的会计期间。</p>
     * <p>应用场景：后台任务生成报表时需要获取会计期间</p>
     *
     * @return 返回最大的已关闭的会计期间，未查询到时则返回空字符串
     * @throws QueryException 查询会计期间出错时抛出该异常
     */
    public String getMaxAccountPeriod() throws QueryException {
        String maxAccountPeriod = "";
        try {
            AccountPeriodSearchModel modelProducer = (AccountPeriodSearchModel) sqlProducer;
            SQLModel sqlModel = modelProducer.getMaxAccountPeriodModel();
            SimpleQuery sq = new SimpleQuery(sqlModel, conn);
            sq.executeQuery();
            if (sq.hasResult()) {
                Row row = sq.getFirstRow();
                maxAccountPeriod = row.getStrValue("PERIOD_NAME");
            }
        } catch (ContainerException ex) {
            ex.printLog();
            throw new QueryException(ex);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new QueryException(ex.getMessage());
        }
        return maxAccountPeriod;
    }
}
