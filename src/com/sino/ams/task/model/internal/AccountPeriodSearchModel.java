package com.sino.ams.task.model.internal;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * <p>标题: SinoEAM中国移动资产管理系统后台同步程序：模型构造对象</p>
 * <p>描述: 查询EAM系统中需要同步到MIS系统的资产地点三段组合</p>
 * <p>版权: 根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约审定</p>
 * <p>开发商: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */
public class AccountPeriodSearchModel extends BaseSQLProducer {

    public AccountPeriodSearchModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    /**
     * <p>功能说明：构造获取最大会计期间的模型对象</p>
     *
     * @return SQLModel 获取最大会计期间的模型对象
     */
    public SQLModel getMaxAccountPeriodModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT MAX(T.PERIOD_NAME) AS PERIOD_NAME\n" +
                "FROM   SRV_ASSET_PERIOD_STATUS T\n" +
                "WHERE  T.PERIOD_STATUS = 'CLOSE'";
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }
}
