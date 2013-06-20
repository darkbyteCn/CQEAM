package com.sino.ams.task.dao.soa.mis;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.task.model.soa.mis.AssetsChangeSearchModel;
import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.QueryException;
import com.sino.framework.dto.BaseUserDTO;

import java.sql.Connection;


/**
 * <p>标题: SinoEAM中国移动资产管理系统后台同步程序：数据访问层对象</p>
 * <p>描述: 查询EAM系统中需要同步到MIS系统的资产</p>
 * <p>版权: 根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约审定</p>
 * <p>开发商: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */
public class AssetsChangeSearchDAO extends AMSBaseDAO {

    public AssetsChangeSearchDAO(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        sqlProducer = new AssetsChangeSearchModel(userAccount, dtoParameter);
    }


    /**
     * <p>功能说明：获取指定OU下待同步的资产。</p>
     * <p>应用场景：资产基本信息变动同步</p>
     * <p>特殊说明：与MIS系统相比，在EAM系统发生了如下三个变化之一的资产需要同步到MIS系统</p>
     * <li>名称发生变更；</li>
     * <li>型号发生变更；</li>
     * <li>厂商发生变更；</li>
     * <li>其他的属性变更，如责任人变更、地点变更需要通过资产调拨同步实现</li>
     * <p/>
     *
     * @param organizationId OU组织ID
     * @return 指定OU下待同步资产，没有数据则返回空的RowSet对象
     * @throws QueryException 查询数据出错时抛出该异常
     */
    public RowSet getOU2SynchronizeAssets(int organizationId) throws QueryException {
        AssetsChangeSearchModel modelProducer = (AssetsChangeSearchModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getChangedAssetsModel(organizationId);
        SimpleQuery splq = new SimpleQuery(sqlModel, conn);
        splq.setCalPattern(getCalPattern());
        splq.executeQuery();
        return splq.getSearchResult();
    }
}
