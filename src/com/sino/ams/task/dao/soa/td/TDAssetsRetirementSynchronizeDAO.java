package com.sino.ams.task.dao.soa.td;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.task.model.soa.td.TDAssetsRetirementSynchronizeModel;
import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.util.ArrUtil;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_importassetretirmentsrv.ErrorItem;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>标题: SinoEAM中国移动资产管理系统后台同步程序：数据访问层对象</p>
 * <p>描述: 查询EAM系统中需要同步到MIS系统的报废资产</p>
 * <p>版权: 根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约审定</p>
 * <p>开发商: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */
public class TDAssetsRetirementSynchronizeDAO extends AMSBaseDAO {

    public TDAssetsRetirementSynchronizeDAO(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        sqlProducer = new TDAssetsRetirementSynchronizeModel(userAccount, dtoParameter);
    }

    /**
     * <p>功能说明：设置用户对象</p>
     * <p>应用场景：循环OU进行同步，更换OU时需要更换同步人</p>
     *
     * @param userAccount 后台任务执行人
     */
    public void setUserAccount(BaseUserDTO userAccount) {
        this.userAccount = (SfUserDTO) userAccount;
        TDAssetsRetirementSynchronizeModel modelProducer = (TDAssetsRetirementSynchronizeModel) sqlProducer;
        modelProducer.setUserAccount(userAccount);
    }


    /**
     * <p>功能说明：获取指定OU下待同步的报废资产。</p>
     *
     * @param organizationId OU组织ID
     * @return 指定OU下待同步报废资产，没有数据则返回空的RowSet对象
     * @throws com.sino.base.exception.QueryException
     *          查询数据出错时抛出该异常
     */
    public RowSet getOU2RetiredAssets(int organizationId) throws QueryException {
        TDAssetsRetirementSynchronizeModel modelProducer = (TDAssetsRetirementSynchronizeModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getRetiredAssetsModel(organizationId);
        SimpleQuery splq = new SimpleQuery(sqlModel, conn);
        splq.setCalPattern(getCalPattern());
        splq.executeQuery();
        return splq.getSearchResult();
    }


    /**
     * 写报废同步日志
     *
     * @param systemId 资产IDS
     * @param batchId  同步批
     * @throws com.sino.base.exception.DataHandleException
     *          写日志出错时抛出该异常
     */
    public void logRetireAssets(String systemId[], String batchId) throws DataHandleException {
        TDAssetsRetirementSynchronizeModel modelProducer = (TDAssetsRetirementSynchronizeModel) sqlProducer;
        String systemIds = ArrUtil.arrToSqlStr(systemId);
        SQLModel sqlModel = modelProducer.getLogAssetsModel(systemIds, batchId);
        DBOperator.updateRecord(sqlModel, conn);
    }

    /**
     * 更新同步详细日志
     *
     * @param batchId 同步批
     * @throws com.sino.base.exception.DataHandleException
     *
     */
    public void updateResponseLog(String batchId) throws DataHandleException {
        TDAssetsRetirementSynchronizeModel modelProducer = (TDAssetsRetirementSynchronizeModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getUpdateLogCompleteModel(batchId);
        DBOperator.updateRecord(sqlModel, conn);
    }


    /**
     * 更新同步详细日志
     *
     * @param errorItemList 错误
     * @param batchId       同步批
     * @throws DataHandleException
     */
    public void updateErrorLog(List<ErrorItem> errorItemList, String batchId) throws DataHandleException {
        SQLModel sqlModel = null;
        List sqlModelList = new ArrayList();
        TDAssetsRetirementSynchronizeModel modelProducer = (TDAssetsRetirementSynchronizeModel) sqlProducer;
        for (int i = 0; i < errorItemList.size(); i++) {
            ErrorItem errorItem = errorItemList.get(i);
            sqlModel = modelProducer.getUpdateLogModel(batchId, errorItem.getRECORDNUMBER(), 2, errorItem.getERRORMESSAGE());
            sqlModelList.add(sqlModel);
        }
        if (sqlModelList.size() > 0) {
            DBOperator.updateBatchRecords(sqlModelList, conn);
        }
    }
}
