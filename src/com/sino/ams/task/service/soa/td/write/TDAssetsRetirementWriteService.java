package com.sino.ams.task.service.soa.td.write;

import com.sino.ams.task.dao.soa.td.TDAssetsRetirementSynchronizeDAO;
import com.sino.ams.task.service.soa.AbstractTaskSOAService;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.log.Logger;
import com.sino.soa.common.SrvReturnMessage;
import com.sino.soa.common.SrvType;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_importassetretirmentsrv.ErrorItem;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_importassetretirmentsrv.ImportAssetRetirmentSrvInputItem;
import com.sino.soa.td.srv.assetretire.srv.TDImportAssetRetirmentSrv;
import com.sino.soa.util.SynLogUtil;
import com.sino.soa.util.XMLGregorianCalendarUtil;
import com.sino.soa.util.dto.EtsMisfaTransactionRespDTO;
import com.sino.soa.util.dto.EtsMisfaUpdateBatchDTO;
import com.sino.soa.util.dto.SynLogDTO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>标题: SinoEAM中国移动资产管理系统后台同步任务：业务服务层对象</p>
 * <p>描述: 通过SOA服务将EAM系统发生的报废资产报废同步到TD系统</p>
 * <p>版权: 根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约审定</p>
 * <p>开发商: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */
public class TDAssetsRetirementWriteService extends AbstractTaskSOAService {

    private TDAssetsRetirementSynchronizeDAO retireDAO = null;

    /**
     * <p>功能说明：将EAM系统发生的报废资产报废同步到TD系统 </p>
     *
     * @throws com.sino.base.exception.DataHandleException
     *          将EAM系统发生的报废资产报废同步到TD系统出错时抛数据处理异常
     */
    public void writeTDAssetsRetirement() throws DataHandleException {
        Connection conn = null;
        try {
            conn = getDBConnection();
            RowSet rows = getTDCompanyList(conn);
            if (rows != null && !rows.isEmpty()) {
                int dataCount = rows.getSize();

                SynLogUtil logUtil = new SynLogUtil();
                for (int i = 0; i < dataCount; i++) {
                    Row row = rows.getRow(i);
                    String orgId = row.getStrValue("ORGANIZATION_ID");
                    int organizationId = Integer.parseInt(orgId);
                    taskExecutor = getOUTaskExecutor(conn, organizationId);
                    if (taskExecutor == null) {
                        continue;
                    }
                    initAssetsRetirementDAO(conn);
                    RowSet retiredAssets = retireDAO.getOU2RetiredAssets(organizationId);
                    if (retiredAssets == null || retiredAssets.isEmpty()) {
                        continue;
                    }
                    synchronizeRetireAssets(conn, retiredAssets, logUtil);
                }
            }
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex.getMessage());
        } finally {
            closeDBConnection(conn);
        }
    }

    /**
     * <p>功能说明：初始化报废DAO类
     *
     * @param conn 数据库连接
     */
    private void initAssetsRetirementDAO(Connection conn) {
        if (retireDAO == null) {
            retireDAO = new TDAssetsRetirementSynchronizeDAO(taskExecutor, null, conn);
        } else {
            retireDAO.setUserAccount(taskExecutor);
        }
    }

    /**
     * <p>功能说明：同步报废资产
     *
     * @param conn          数据库连接
     * @param retiredAssets 报废资产集合
     * @param logUtil       日志工具
     * @throws com.sino.base.exception.DataHandleException
     *          同步资产出错时抛出数据处理异常
     */
    private void synchronizeRetireAssets(Connection conn, RowSet retiredAssets, SynLogUtil logUtil) throws DataHandleException {
        logSynStart(conn, logUtil);
        EtsMisfaUpdateBatchDTO updateBatchDTO = createMisUpdateBatch(conn, retiredAssets, logUtil);
        TDImportAssetRetirmentSrv srv = sendData2RemoteServer(conn, retiredAssets, logUtil);
        updateSynchronizeResponse(conn, srv, updateBatchDTO, logUtil);
        logSynEnd(conn, logUtil);
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
        logDTO.setSynType(SrvType.SRV_TD_IMP_RETIRE);
        logDTO.setCreatedBy(taskExecutor.getUserId());
        logDTO.setSynMsg("报废TD资产同步开始！");
        logUtil.synLog(logDTO, conn);
    }

    /**
     * <p>功能说明：记录同步结束日志
     *
     * @param conn    数据库连接
     * @param logUtil 日志工具
     * @throws com.sino.base.exception.DataHandleException
     *          同步资产出错时抛出数据处理异常
     */
    private void logSynEnd(Connection conn, SynLogUtil logUtil) throws DataHandleException {
        SynLogDTO logDTO = new SynLogDTO();
        logDTO.setSynType(SrvType.SRV_TD_IMP_RETIRE);
        logDTO.setCreatedBy(taskExecutor.getUserId());
        logDTO.setSynMsg("报废TD资产同步结束！");
        logUtil.synLog(logDTO, conn);
    }

    /**
     * <p>功能说明：同步报废资产
     *
     * @param conn          数据库连接
     * @param retiredAssets 报废资产集合
     * @param logUtil       日志工具
     * @return EtsMisfaUpdateBatchDTO 返回同步批对象
     * @throws com.sino.base.exception.DataHandleException
     *          同步资产出错时抛出数据处理异常
     */
    private EtsMisfaUpdateBatchDTO createMisUpdateBatch(Connection conn, RowSet retiredAssets, SynLogUtil logUtil) throws DataHandleException {
        EtsMisfaUpdateBatchDTO updateBatchDTO = new EtsMisfaUpdateBatchDTO();
        try {
            SeqProducer sp = new SeqProducer(conn);
            String batchSeq = sp.getGUID();
            updateBatchDTO.setBatchId(batchSeq);
            updateBatchDTO.setOrganizationId(taskExecutor.getOrganizationId());
            updateBatchDTO.setCreatedBy(taskExecutor.getUserId());
            updateBatchDTO.setTransStatus(0);
            updateBatchDTO.setTransType(SrvType.SRV_TD_IMP_RETIRE);
            updateBatchDTO.setRemark("本次共同步" + retiredAssets.getSize() + "条");
            logUtil.createMisUpdateBatch(updateBatchDTO, conn);

            retireDAO.logRetireAssets(getSystemIds(retiredAssets), batchSeq);
        } catch (ContainerException ex) {
            ex.printLog();
            throw new DataHandleException(ex);
        }
        return updateBatchDTO;
    }

    /**
     * <p>功能说明：获取同步报废资产的所有SYSTEMID字段值构成的数组
     *
     * @param retiredAssets 报废资产
     * @return SYSTEMID字段值构成的数组
     * @throws com.sino.base.exception.ContainerException
     *          当容器不包含SYSTEMID字段时抛出该异常
     */
    private String[] getSystemIds(RowSet retiredAssets) throws ContainerException {
        String[] systemIds = new String[retiredAssets.getSize()];
        for (int i = 0; i < retiredAssets.getSize(); i++) {
            Row row = retiredAssets.getRow(i);
            systemIds[i] = row.getStrValue("SYSTEMID");
        }
        return systemIds;
    }

    /**
     * <p>功能说明：同步报废资产到远程服务器
     *
     * @param conn          数据库连接
     * @param retiredAssets 报废资产
     * @param logUtil       日志记录工具
     * @return 远程同步WebService对象
     * @throws com.sino.base.exception.DataHandleException
     *          处理数据出错时抛出数据处理异常
     */
    private TDImportAssetRetirmentSrv sendData2RemoteServer(Connection conn, RowSet retiredAssets, SynLogUtil logUtil) throws DataHandleException {
        TDImportAssetRetirmentSrv srv = new TDImportAssetRetirmentSrv();
        try {
            EtsMisfaTransactionRespDTO rsRespDTO = logUtil.getMisfaResp(taskExecutor.getOrganizationId(), taskExecutor.getEmployeeNumber(), conn);   //地市公司代码
            List<ImportAssetRetirmentSrvInputItem> inputItemList = new ArrayList<ImportAssetRetirmentSrvInputItem>();
            for (int i = 0; i < retiredAssets.getSize(); i++) {
                Row row = retiredAssets.getRow(i);
                ImportAssetRetirmentSrvInputItem inputItem = new ImportAssetRetirmentSrvInputItem();
                inputItem.setPRIKEY(row.getStrValue("BARCODE"));
                inputItem.setBOOKTYPECODE(row.getStrValue("BOOK_TYPE_CODE"));
                inputItem.setTAGNUMBER(row.getStrValue("TAG_NUMBER"));
                inputItem.setDATERRETIRED(XMLGregorianCalendarUtil.getXMLGregorianCalendar(row.getStrValue("DATE_RRETIRED")));
//	                        inputItem.setRETIREMENTTYPECODE(row.getStrValue("REJECT_TYPE"));//NORMAL、TRANSFER 报废类型
                inputItem.setRETIREMENTTYPECODE("");//NORMAL、TRANSFER 报废类型
                inputItem.setCURRENTCOST(new BigDecimal(row.getStrValue("COST")).setScale(2, BigDecimal.ROUND_HALF_UP));
                inputItem.setRETIREMENTCOST(new BigDecimal(row.getStrValue("COST")).setScale(2, BigDecimal.ROUND_HALF_UP));
                inputItem.setCREATEDBY(rsRespDTO.getUserId());
                inputItem.setEMPLOYEENUMBER(rsRespDTO.getEmployeeNumber());
                inputItemList.add(inputItem);
            }
            srv.setSrvInputItems(inputItemList);
            srv.excute();
        } catch (ContainerException ex) {
            ex.printLog();
            throw new DataHandleException(ex);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex.getMessage());
        }
        return srv;
    }

    /**
     * <p>功能说明：记录远程同步的结果
     *
     * @param conn           数据库连接
     * @param srv            远程同步WebService对象
     * @param updateBatchDTO 同步批对象
     * @param logUtil        日志记录工具
     * @throws com.sino.base.exception.DataHandleException
     *          处理数据出错时抛出数据处理异常
     */
    private void updateSynchronizeResponse(Connection conn,
                                           TDImportAssetRetirmentSrv srv,
                                           EtsMisfaUpdateBatchDTO updateBatchDTO,
                                           SynLogUtil logUtil) throws DataHandleException {
        SrvReturnMessage returnMessage = srv.getReturnMessage();
        if (returnMessage.getErrorFlag().equalsIgnoreCase("Y")) {
            updateBatchDTO.setRemark(updateBatchDTO.getRemark() + ",全部同步成功!");
            updateBatchDTO.setErrmsg("");
            updateBatchDTO.setTransStatus(2);
            logUtil.updateMisUpdateBach(updateBatchDTO, conn);
            retireDAO.updateResponseLog(updateBatchDTO.getBatchId());
        } else {
            updateBatchDTO.setRemark(updateBatchDTO.getRemark() + ",全部同步失败!");
            updateBatchDTO.setErrmsg(returnMessage.getErrorMessage());
            updateBatchDTO.setTransStatus(2);
            logUtil.updateMisUpdateBach(updateBatchDTO, conn);
            List<ErrorItem> errorItemList = srv.getErrorItemList();
            if (errorItemList != null && errorItemList.size() > 0) {
                retireDAO.updateErrorLog(errorItemList, updateBatchDTO.getBatchId());
            }
        }
    }
}
