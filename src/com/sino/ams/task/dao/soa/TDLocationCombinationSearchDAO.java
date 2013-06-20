package com.sino.ams.task.dao.soa;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.task.model.soa.LocationCombinationSearchModel;
import com.sino.ams.task.model.soa.TDLocationCombinationSearchModel;
import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.QueryException;
import com.sino.framework.dto.BaseUserDTO;

import java.sql.Connection;


/**
 * <p>标题: SinoEAM中国移动资产管理系统后台同步程序：数据访问层对象</p>
 * <p>描述: 查询EAM系统中需要同步到MIS系统的资产地点三段组合</p>
 * <p>版权: 根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约审定</p>
 * <p>开发商: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */
public class TDLocationCombinationSearchDAO extends AMSBaseDAO {

    public TDLocationCombinationSearchDAO(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        sqlProducer = new TDLocationCombinationSearchModel(userAccount, dtoParameter);
    }


    /**
     * <p>功能说明：获取指定OU下待同步的资产地点三段组合。</p>
     * <p>特殊说明：查询出EAM系统符合以下条件的地点：</p>
     * <li>EAM系统新增的组合地点；</>
     * <li>描述发生变动的组合地点；</>
     * <li>先前同步失败的地点；</>
     *
     * @param organizationId OU组织ID
     * @return 指定OU下待同步资产地点三段组合，没有数据则返回空的RowSet对象
     * @throws com.sino.base.exception.QueryException
     *          查询数据出错时抛出该异常
     */
    public RowSet getTDOU2SynchronizeLocations(int organizationId) throws QueryException {
        TDLocationCombinationSearchModel modelProducer = (TDLocationCombinationSearchModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getTDChangedLocationCombinationModel(organizationId);
        SimpleQuery splq = new SimpleQuery(sqlModel, conn);
        splq.setCalPattern(getCalPattern());
        splq.executeQuery();
        return splq.getSearchResult();
    }
}
