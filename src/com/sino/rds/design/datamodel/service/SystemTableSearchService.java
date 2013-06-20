package com.sino.rds.design.datamodel.service;

import com.sino.base.dto.DTO;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.service.RDSBaseService;
import com.sino.rds.design.datamodel.dao.DBConnectionDAO;
import com.sino.rds.design.datamodel.dao.SystemTableSearchDAO;
import com.sino.rds.foundation.exception.WebException;
import com.sino.rds.foundation.web.component.WebOptions;
import com.sino.rds.foundation.web.util.option.OptionProduceRule;
import com.sino.rds.foundation.web.util.option.OptionProducer;
import com.sino.rds.foundation.web.util.option.OptionProducerFactory;
import com.sino.rds.share.form.DBConnectionFrm;
import com.sino.rds.share.form.SystemAllTableFrm;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class SystemTableSearchService extends RDSBaseService {

    public SystemTableSearchService(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

//    public SystemAllTableFrm searchDataByPrimaryKey() throws QueryException {
//        return (SystemAllTableFrm) tableDAO.searchDTOByPrimaryKey();
//    }

    /**
     * 功能：生成Web组件
     *
     * @return 返回指定用户下的所有表
     * @throws com.sino.rds.foundation.exception.WebException
     *          构造下拉框出错时抛出该异常
     */
    public WebOptions getUserTableOptions() throws WebException {
        WebOptions options = null;
        Connection dataSource = null;
        try {
            SystemAllTableFrm frm = (SystemAllTableFrm) dtoParameter;
            DBConnectionFrm dbcFrm = new DBConnectionFrm();
            dbcFrm.setConnectionId(frm.getConnectionId());
            DBConnectionDAO connectionDAO = new DBConnectionDAO(userAccount, dbcFrm, conn);
            dbcFrm = connectionDAO.searchDTOByPrimaryKey();
            if (dbcFrm != null) {
                dataSource = dbcFrm.getDBConnection();
                SystemTableSearchDAO tableDAO = new SystemTableSearchDAO(userAccount, dtoParameter, dataSource);
                List<SystemAllTableFrm> tableList = tableDAO.getUserAllTables();
                OptionProduceRule optRule = new OptionProduceRule();
                optRule.setValueField("tableName");
                optRule.setDescField("optionComments");
                optRule.setDataSource(tableList);
                optRule.setAddBlank(true);
                optRule.setSelectedValue(frm.getTableName());
                OptionProducer optProducer = OptionProducerFactory.getOptionProducer(optRule);
                options = optProducer.getOptions();
            }
        } catch (QueryException ex) {
            ex.printLog();
            throw new WebException(ex);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new WebException(ex.getMessage());
        } finally {
            try {
                if (dataSource != null) {
                    dataSource.close();
                }
            } catch (SQLException ex) {
                Logger.logError(ex);
            }
        }
        return options;
    }
}
