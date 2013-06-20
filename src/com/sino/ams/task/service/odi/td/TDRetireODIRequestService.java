package com.sino.ams.task.service.odi.td;

import com.sino.ams.task.service.odi.AbstractTaskODIService;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.exception.DataHandleException;
import com.sino.base.log.Logger;
import com.sino.soa.common.SrvType;
import com.sino.soa.td.srv.assetretire.srv.TDTransRetiredAssetDetailSrv;
import com.sino.soa.util.SynLogUtil;
import com.sino.soa.util.SynUpdateDateUtils;
import com.sino.soa.util.dto.SynLogDTO;

import java.sql.Connection;

/**
 * <p>标题: SinoEAM中国移动资产管理系统后台同步任务：业务服务层对象</p>
 * <p>描述: 通过ODI服务从TD系统读取报废资产清单到EAM系统</p>
 * <p>版权: 根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约审定</p>
 * <p>开发商: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */
public class TDRetireODIRequestService extends AbstractTaskODIService {

    @Override
    protected void initODIServiceName() {
        odiServiceName = "TDTransRetiredAssetDetailSrv";
    }

    /**
     * <p>功能说明：从TD系统读取报废资产清单到EAM系统</p>
     *
     * @throws com.sino.base.exception.DataHandleException
     *          读取报废资产清单到EAM系统出错时抛数据处理异常
     */
    public void requestODI2ProcessTDRetire() throws DataHandleException {
        Connection conn = null;
        SynLogUtil logUtil = new SynLogUtil();
        try {
            conn = getDBConnection();
            initTaskExecutor(conn);
            initODIServiceName();
            logSynStart(conn, logUtil);
           truncateData("ZTE_TD_RETIREMENT_BASIC_INFO",conn);
            RowSet rows = getTDBookTypeCodes(conn);
            boolean hasError = false;
            if (rows != null && !rows.isEmpty()) {
                int dataCount = rows.getSize();
                TDTransRetiredAssetDetailSrv srv = new TDTransRetiredAssetDetailSrv();
                String envCode = getODIEnvCode(conn);
                srv.setEnvCode(envCode);
                String lastUpdateDate = getLastUpdateDate(SrvType.SRV_TD_FA_RETIRE, conn);
                srv.setStartRetireDate(lastUpdateDate);
                for (int i = 0; i < dataCount; i++) {
                    Row row = rows.getRow(i);
                    String bookTypeCode = row.getStrValue("BOOK_TYPE_CODE");
                    srv.setBookTypeCode(bookTypeCode);
                    try {
                        srv.excute();
                        logSynEnd(conn, logUtil, bookTypeCode);
                    } catch (Throwable ex) {
                        hasError = true;
                        logSynError(conn, logUtil, bookTypeCode);
                    }
                }
            }
            if (!hasError) {//记录日志放在最后，以免数据读取遗漏
                SynUpdateDateUtils.createLastUpdateDate(SrvType.SRV_TD_FA_RETIRE, conn);
                SynUpdateDateUtils.updateLastUpdateDate(SrvType.SRV_TD_FA_RETIRE, conn);
            }
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex.getMessage());
        } finally {
            closeDBConnection(conn);
        }
    }

    /**
     * <p>功能说明：记录同步开始日志
     *
     * @param conn    数据库连接
     * @param logUtil 日志工具
     * @throws com.sino.base.exception.DataHandleException
     *          同步资产出错时抛出数据处理异常
     */
    private void logSynStart(Connection conn, SynLogUtil logUtil) throws DataHandleException {
        SynLogDTO logDTO = new SynLogDTO();
        logDTO.setSynType(SrvType.SRV_TD_FA_RETIRE);
        logDTO.setCreatedBy(taskExecutor.getUserId());
        logDTO.setSynMsg("同步报废TD资产开始！");
        logUtil.synLog(logDTO, conn);
    }

    /**
     * <p>功能说明：记录同步结束日志
     *
     * @param conn    数据库连接
     * @param logUtil 日志工具
     * @param bookTypeCode 资产账簿代码
     * @throws com.sino.base.exception.DataHandleException
     *          同步资产出错时抛出数据处理异常
     */
    private void logSynEnd(Connection conn, SynLogUtil logUtil, String bookTypeCode) throws DataHandleException {
        SynLogDTO logDTO = new SynLogDTO();
        logDTO.setSynType(SrvType.SRV_TD_FA_RETIRE);
        logDTO.setCreatedBy(taskExecutor.getUserId());
        logDTO.setSynMsg("同步报废TD资产成功，资产账簿：" + bookTypeCode);
        logUtil.synLog(logDTO, conn);
    }

    /**
     * <p>功能说明：记录同步结束日志
     *
     * @param conn    数据库连接
     * @param logUtil 日志工具
     * @param bookTypeCode 资产账簿代码
     * @throws com.sino.base.exception.DataHandleException
     *          同步资产出错时抛出数据处理异常
     */
    private void logSynError(Connection conn, SynLogUtil logUtil, String bookTypeCode) throws DataHandleException {
        SynLogDTO logDTO = new SynLogDTO();
        logDTO.setSynType(SrvType.SRV_TD_FA_RETIRE);
        logDTO.setCreatedBy(taskExecutor.getUserId());
        logDTO.setSynMsg("同步报废TD资产失败，资产账簿：" + bookTypeCode);
        logUtil.synLog(logDTO, conn);
    }
    public void truncateData(String tableName,Connection conn) {
//                Connection conn = null;
               try {
//                    conn = DBManager.getDBConnection();
          SQLModel sqlModel = new SQLModel();
            String      sqlStr="TRUNCATE TABLE  "+tableName;
            sqlModel.setSqlStr(sqlStr);
		DBOperator.updateRecord(sqlModel, conn);
        } catch (Exception e) {
            e.printStackTrace();
        }/*finally {
            DBManager.closeDBConnection(conn);
        }*/
           }
}
