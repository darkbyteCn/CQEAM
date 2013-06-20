package com.sino.soa.util;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.soa.service.SrvProcessModel;
import com.sino.soa.util.dto.EtsMisfaTransactionRespDTO;
import com.sino.soa.util.dto.EtsMisfaUpdateBatchDTO;
import com.sino.soa.util.dto.EtsMisfaUpdateLogDTO;
import com.sino.soa.util.dto.SynLogDTO;
import com.sino.soa.util.model.SynLogModel;

/**
 * User: zhoujs
 * Date: 2009-4-27 11:35:54
 * Function:
 */
public class SynLogUtil {
    /**
     * 同步日志记录 ETS_AUTO_SYN_LOG
     *
     * @param log  日志
     * @param conn 数据库连接
     * @return boolean
     * @throws DataHandleException
     */
    public boolean synLog(SynLogDTO log, Connection conn) throws DataHandleException {
        boolean operatorResult = false;
        SynLogModel logModel = new SynLogModel();
        SQLModel sqlModel = logModel.getCreateLogModel(log);

        operatorResult = DBOperator.updateRecord(sqlModel, conn);

        return operatorResult;
    }

    public boolean createUpdateDate(String synType, Connection conn) throws DataHandleException {
        boolean operatorResult = false;
        SynLogModel logModel = new SynLogModel();
        SQLModel sqlModel = logModel.getCreateLastUpdateModel(synType);
        operatorResult = DBOperator.updateRecord(sqlModel, conn);

        return operatorResult;
    }

    /**
     * 记录同步最后更新日期
     *
     * @param synType 同步类别
     * @param conn    数据库连接
     * @return boolean
     * @throws DataHandleException
     */
    public boolean logUpdate(String synType, Connection conn) throws DataHandleException {
        boolean operatorResult = false;
        SynLogModel logModel = new SynLogModel();
        SQLModel sqlModel = logModel.getUpdateLogDateModel(synType);

        operatorResult = DBOperator.updateRecord(sqlModel, conn);

        return operatorResult;
    }

    /**
     * 取服务类型最后更新日期
     *
     * @param synType 同步类别
     * @param conn    数据库连接
     * @return String
     * @throws QueryException
     * @throws ContainerException
     */
    public String getLastUpdateDate(String synType, Connection conn) throws QueryException, ContainerException {
        String lastUpdateDate = "";
        SynLogModel logModel = new SynLogModel();
        SQLModel sqlModel = logModel.getLastUpdateDateModel(synType);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        if (sq.hasResult()) {
            lastUpdateDate = sq.getFirstRow().getStrValue("LAST_DATE");
        } else {//唐明胜增加
            lastUpdateDate = "2011-11-01 00:00:00";
        }
        return lastUpdateDate;
    }

    /**
     * 取MIS更新相关人员及职责
     *
     * @param conn
     * @return
     * @throws QueryException
     */
    public EtsMisfaTransactionRespDTO getMisfaResp(int orgId, String employeeNumber, Connection conn) throws QueryException {
        EtsMisfaTransactionRespDTO dto = null;
        SynLogModel logModel = new SynLogModel();
        SQLModel sqlModel = logModel.getMisfaRespModel(orgId, employeeNumber);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.setDTOClassName(EtsMisfaTransactionRespDTO.class.getName());
        sq.executeQuery();
        if (sq.hasResult()) {
            dto = (EtsMisfaTransactionRespDTO) sq.getFirstDTO();
        }

        return dto;
    }

    /**
     * 写同步日志到 ETS_MISFA_UPDATE_BATCH
     *
     * @param updateBatchDTO
     * @param conn
     * @return
     * @throws DataHandleException
     */
    public boolean createMisUpdateBatch(EtsMisfaUpdateBatchDTO updateBatchDTO, Connection conn) throws DataHandleException {
        boolean operatorResult = true;
        SynLogModel logModel = new SynLogModel();
        SQLModel sqlModel = logModel.getCreateMisUpdateBatchModel(updateBatchDTO);
        operatorResult = DBOperator.updateRecord(sqlModel, conn);

        return operatorResult;
    }

    /**
     * 同步批表记录   ETS_MISFA_UPDATE_BATCH
     *
     * @param updateBatchDTO
     * @param conn
     * @return
     * @throws DataHandleException
     */
    public boolean updateMisUpdateBach(EtsMisfaUpdateBatchDTO updateBatchDTO, Connection conn) throws DataHandleException {
        boolean operatorResult = true;
        SynLogModel logModel = new SynLogModel();
        SQLModel sqlModel = logModel.getUpdateMisUpdateBatchModel(updateBatchDTO);
        operatorResult = DBOperator.updateRecord(sqlModel, conn);

        return operatorResult;
    }

    /**
     * 同步详细信息记录
     *
     * @param updateLogDTO
     * @param conn
     * @return
     * @throws DataHandleException
     * @throws CalendarException
     */
    public boolean updateMisUpdateLog(EtsMisfaUpdateLogDTO updateLogDTO, Connection conn) throws DataHandleException, CalendarException {
        boolean operatorResult = true;
        SynLogModel logModel = new SynLogModel();
        SQLModel sqlModel = logModel.getUpdateMisUpdateLogModel(updateLogDTO);
        operatorResult = DBOperator.updateRecord(sqlModel, conn);

        return operatorResult;
    }

    /**
     * 更新ETS_MISFA_UPDATE_LOG 表
     * 写错误信息
     */
    public boolean getUpdateMisUpdateLogModel(String batchId, String msg, String str, Connection conn) throws DataHandleException, CalendarException {
        boolean operatorResult = true;
        SynLogModel logModel = new SynLogModel();
        SQLModel sqlModel = logModel.getUpdateMisUpdateLogModel(batchId, msg, str);
        operatorResult = DBOperator.updateRecord(sqlModel, conn);
        return operatorResult;
    }

    /**
     * 功能：更新ETS_MISFA_UPDATE_LOG 表，修改器状态为同步成功
     * @param batchId 同步批ID
     * @param tagNumbers 同步成功的资产标签号
     * @param conn 数据库连接
     * @throws DataHandleException 执行出错时抛出数据处理异常
     */
    public void writeMisFASuccessLog(String batchId, List<String> tagNumbers, Connection conn) throws DataHandleException {
        List<SQLModel> sqlModelList = SynLogModel.getMisFASuccessLogModelList(batchId, tagNumbers);
        DBOperator.updateBatchRecords(sqlModelList, conn);
    }

    public String[] getBookTypeCodeModel(Connection conn, String source) {
        SrvProcessModel spm = new SrvProcessModel();
        SQLModel sqlModel = spm.getBookTypeCodeModel(source);
        String temp[] = null;
        SimpleQuery simp = new SimpleQuery(sqlModel, conn);
        try {
            simp.executeQuery();
            RowSet rs = null;
            Row row = null;
            if (simp.hasResult()) {
                rs = simp.getSearchResult();
                temp = new String[rs.getSize()];
                for (int i = 0; i < rs.getSize(); i++) {
                    row = rs.getRow(i);
                    temp[i] = row.getStrValue("BOOK_TYPE_CODE");
                }
            }
        } catch (QueryException e) {

            e.printStackTrace();
        } catch (ContainerException e) {

            e.printStackTrace();
        }
        return temp;
    }

    public String[] getBookTypeCode(Connection conn, String source) {
        SrvProcessModel spm = new SrvProcessModel();
        SQLModel sqlModel = spm.getBookTypeCodeModel2(source);
        String temp[] = null;
        SimpleQuery simp = new SimpleQuery(sqlModel, conn);
        try {
            simp.executeQuery();
            RowSet rs = null;
            Row row = null;
            if (simp.hasResult()) {
                rs = simp.getSearchResult();
                temp = new String[rs.getSize()];
                for (int i = 0; i < rs.getSize(); i++) {
                    row = rs.getRow(i);
                    temp[i] = row.getStrValue("BOOK_TYPE_CODE");
                }
            }
        } catch (QueryException e) {

            e.printStackTrace();
        } catch (ContainerException e) {

            e.printStackTrace();
        }
        return temp;
    }


    public static int getOrganizationId(Connection conn, String bookTypeCode) {
        int organizationId = 0;
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT ORGANIZATION_ID FROM ETS_OU_CITY_MAP WHERE BOOK_TYPE_CODE=?";
        List sqlArgs = new ArrayList();
        sqlArgs.add(bookTypeCode);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        try {
            sq.executeQuery();

            if (sq.hasResult()) {
                organizationId = Integer.parseInt(sq.getFirstRow().getStrValue("ORGANIZATION_ID"));
            }
        } catch (QueryException e) {
            Logger.logError(e);
        } catch (ContainerException e) {
            Logger.logError(e);
        }
        return organizationId;
    }
}
