package com.sino.soa.mis.srv.assetsinfoupdate.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.DTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.soa.common.SrvReturnMessage;
import com.sino.soa.mis.eip.fi.fa.sb_fi_fa_updateassetretirmentsrv.ErrorItem;
import com.sino.soa.mis.eip.fi.fa.sb_fi_fa_updateassetretirmentsrv.UpdateAssetRetirmentSrvInputItem;
import com.sino.soa.mis.srv.assetsinfoupdate.dto.SrvAssetsUpdateDTO;
import com.sino.soa.mis.srv.assetsinfoupdate.dto.SrvEamSyschronizeDTO;
import com.sino.soa.mis.srv.assetsinfoupdate.model.AssetsUpdateModel;
import com.sino.soa.mis.srv.assetsinfoupdate.model.SrvAssetsUpdateModel;
import com.sino.soa.mis.srv.assetsinfoupdate.model.SynAttributeModel;
import com.sino.soa.mis.srv.assetsinfoupdate.srv.UpdateAssetRetirmentSrv;
import com.sino.soa.util.SynLogUtil;
import com.sino.soa.util.dto.EtsMisfaTransactionRespDTO;
import com.sino.soa.util.dto.EtsMisfaUpdateBatchDTO;
import com.sino.soa.util.dto.EtsMisfaUpdateLogDTO;

/**
 * User: wangzp Date: 2011-09-26 Function:资产基本信息修改_接口实现类
 */
public class AssetsUpdateDAO extends AMSBaseDAO {

    private SfUserDTO sfUser = null;
    private SrvReturnMessage returnMessage = null;

    /**
     * 功能：资产基本信息修改 数据访问层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter EtsItemMatchDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
    public AssetsUpdateDAO(SfUserDTO userAccount, SrvEamSyschronizeDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        sfUser = userAccount;
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        SrvEamSyschronizeDTO dtoPara = (SrvEamSyschronizeDTO) dtoParameter;
        super.sqlProducer = new AssetsUpdateModel((SfUserDTO) userAccount, dtoPara);
    }

    /**
     * 功能:同步详细信息记录
     * @param orgnization_id
     * @param assetIds
     * @param batchId
     * @param status
     */
    public void getEtsMisfaUpdateLog(int orgnization_id, String[] assetIds, String batchId, String status) {
        try {
            if (assetIds != null && assetIds.length > 0) {
                String targetStr = "";
                int orgId = 0;
                SynLogUtil synLogUtil = new SynLogUtil();
                for (int i = 0; i < assetIds.length; i++) {
                    targetStr = assetIds[i];
                    SrvAssetsUpdateDAO assetsUpdateDao = new SrvAssetsUpdateDAO();
                    SrvAssetsUpdateDTO assetsUpdateDto;
                    assetsUpdateDto = assetsUpdateDao.getAssetsDtoBydId(targetStr, conn); // 获取资产变更对象
                    if (assetsUpdateDto.getOrganizationId() > 0) {
                        EtsMisfaTransactionRespDTO emDto = synLogUtil.getMisfaResp(userAccount.getOrganizationId(), userAccount.getEmployeeNumber(), conn); // 职责信息
                        assetsUpdateDto.setCreatedBy(emDto.getUserId().toString());
                        assetsUpdateDto.setResponsibilityId(emDto.getRespId().toString());
                        assetsUpdateDto.setEmployeeNumber(emDto.getEmployeeNumber());
                    }
                    orgId = assetsUpdateDto.getOrganizationId();
                    EtsMisfaUpdateLogDTO etsMisfaUpdateLogDto = new EtsMisfaUpdateLogDTO();
                    etsMisfaUpdateLogDto.setBatchId(batchId);
                    etsMisfaUpdateLogDto.setBarcode(assetsUpdateDto.getTagNumber());
                    etsMisfaUpdateLogDto.setAssetId(Integer.parseInt(targetStr));
                    etsMisfaUpdateLogDto.setOrganizationId(orgId);
                    etsMisfaUpdateLogDto.setNameFrom(assetsUpdateDto.getItemName());
                    etsMisfaUpdateLogDto.setNameTo(assetsUpdateDto.getAssetsDescription());
                    etsMisfaUpdateLogDto.setManufacturerFrom(assetsUpdateDto.getEammanufname());
                    etsMisfaUpdateLogDto.setManufacturerTo(assetsUpdateDto.getManufacturerName());
                    etsMisfaUpdateLogDto.setTagNumberFrom(assetsUpdateDto.getBarcode());
                    etsMisfaUpdateLogDto.setTagNumberTo(assetsUpdateDto.getTagNumber());
                    etsMisfaUpdateLogDto.setModelFrom(assetsUpdateDto.getItemSpec());
                    etsMisfaUpdateLogDto.setModelTo(assetsUpdateDto.getModelNumber());
                    etsMisfaUpdateLogDto.setUpdateType("ASSETSINFO");
                    etsMisfaUpdateLogDto.setTransStatus(Integer.parseInt(status));
                    synLogUtil.updateMisUpdateLog(etsMisfaUpdateLogDto, conn);
                }
            }

        } catch (QueryException e) {
            e.printStackTrace();
        } catch (DataHandleException e) {
            e.printStackTrace();
        } catch (CalendarException e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能：资产基本信息同步
     * @param orgnization_id String
     * @param assetIds       String []
     * @throws QueryException
     * @throws SQLException
     * @throws DataHandleException
     * @throws CalendarException
     */
    public String syschronizeAssets(int orgnization_id, String[] assetIds) throws QueryException, SQLException, DataHandleException, CalendarException {
        List<UpdateAssetRetirmentSrvInputItem> list1 = new ArrayList<UpdateAssetRetirmentSrvInputItem>();
        List<String> totalTagNumbers = new ArrayList<String>();
        UpdateAssetRetirmentSrvInputItem inputItem = null;
        EtsMisfaUpdateBatchDTO batchDTO = null;
        SynLogUtil synLogUtil = new SynLogUtil();
        int totalCount = 0;
        int orgId = -1;
        String isRespExist = "YES";
        try {
            EtsMisfaTransactionRespDTO emDto = synLogUtil.getMisfaResp(userAccount.getOrganizationId(), userAccount.getEmployeeNumber(), conn);
            if (emDto == null) {
                isRespExist = "NO";
                return isRespExist;
            } else {
                if (assetIds != null && assetIds.length > 0) {
                    String targetStr = "";
                    UpdateAssetRetirmentSrv updateAssetSrv = new UpdateAssetRetirmentSrv();
                    for (int i = 0; i < assetIds.length; i++) {
                        inputItem = new UpdateAssetRetirmentSrvInputItem();
                        targetStr = assetIds[i];
                        SrvAssetsUpdateDAO assetsUpdateDao = new SrvAssetsUpdateDAO();
                        SrvAssetsUpdateDTO assetsUpdateDto;

                        // 根据ASSET_ID获取对象
                        assetsUpdateDto = assetsUpdateDao.getAssetsDtoBydId(targetStr, conn);
                        if (assetsUpdateDto.getOrganizationId() > -1) {
                            assetsUpdateDto.setCreatedBy(emDto.getUserId().toString());
                            assetsUpdateDto.setResponsibilityId(emDto.getRespId().toString());
                            assetsUpdateDto.setEmployeeNumber(emDto.getEmployeeNumber());
                        }
                        if (!assetsUpdateDto.getCexId().equals("")) { // 投资分类（获取支撑网设备类型）
                            EtsMisfaTransactionRespDTO dto1 = this.getCexType(assetsUpdateDto.getCexId());
                            if (null != dto1) {
                                assetsUpdateDto.setSnCode(dto1.getSnCode());
                            }
                        } else {
                            assetsUpdateDto.setSnCode("");
                        }
                        if (!assetsUpdateDto.getOpeId().equals("")) { // 业务平台
                            EtsMisfaTransactionRespDTO dto1 = this.getOpe(assetsUpdateDto.getOpeId());
                            if (null != dto1) {
                                assetsUpdateDto.setOpeCode(dto1.getOpeCode());
                            }
                        } else {
                            assetsUpdateDto.setOpeCode("");
                        }
                        if (!assetsUpdateDto.getNleId().equals("")) { // 网络层次
                            EtsMisfaTransactionRespDTO dto1 = this.getOpe(assetsUpdateDto.getNleId());
                            if (null != dto1) {
                                assetsUpdateDto.setNleCode(dto1.getOpeCode());
                            }
                        } else {
                            assetsUpdateDto.setNleCode("");
                        }
                        inputItem.setPRIKEY(assetsUpdateDto.getTagNumber());
                        inputItem.setBOOKTYPECODE(assetsUpdateDto.getBookTypeCode());
                        inputItem.setTAGNUMBER(assetsUpdateDto.getTagNumber());
                        inputItem.setDESCRIPTION(assetsUpdateDto.getItemName());
                        inputItem.setMANUFACTURERNAME(assetsUpdateDto.getEammanufname());
                        inputItem.setMODELNUMBER(assetsUpdateDto.getItemSpec());
//                        inputItem.setCREATEDBY(new BigDecimal(assetsUpdateDto.getCreatedBy()));
                        inputItem.setEMPLOYEENUMBER(assetsUpdateDto.getEmployeeNumber());
                        inputItem.setRESPONSIBILITYID(new BigDecimal(assetsUpdateDto.getResponsibilityId()));

                        inputItem.setATTRIBUTE8(assetsUpdateDto.getConstructStatus()); // 是否共建设备
                        inputItem.setATTRIBUTE9(assetsUpdateDto.getSnCode()); // 支撑网设备类型 ---
                        inputItem.setATTRIBUTE10(assetsUpdateDto.getOpeCode()); // 业务平台编码
                        inputItem.setATTRIBUTE11(assetsUpdateDto.getNleCode()); // 网络层次编码
                        list1.add(inputItem);
                        totalCount++;
                        orgId = assetsUpdateDto.getOrganizationId();

                        totalTagNumbers.add(assetsUpdateDto.getTagNumber());//唐明胜增加
                    }
                    updateAssetSrv.setSrvInputItems(list1);
                    updateAssetSrv.excute();

                    SrvReturnMessage srvMessage = updateAssetSrv.getReturnMessage();
                    returnMessage = updateAssetSrv.getReturnMessage();
                    if (srvMessage.getErrorFlag().equalsIgnoreCase("Y")) {
                        batchDTO = new EtsMisfaUpdateBatchDTO();
                        batchDTO.setBatchId(this.getNextSeq(conn));
                        batchDTO.setTransType("ASSETSINFO");
//                        batchDTO.setTransStatus(2);
                        batchDTO.setTransStatus(1);//唐明胜修改为1，标示成功
                        batchDTO.setOrganizationId(orgId);
                        batchDTO.setCreatedBy(userAccount.getUserId());
                        batchDTO.setErrmsg("");
                        batchDTO.setRemark("本次共同步'资产基本信息'" + totalCount + "条记录，全部同步成功");
                        synLogUtil.createMisUpdateBatch(batchDTO, conn);

                        totalTagNumbers.clear();//added by mshtang
                    } else {
                        batchDTO = new EtsMisfaUpdateBatchDTO();
                        batchDTO.setBatchId(this.getNextSeq(conn));
                        batchDTO.setTransType("ASSETSINFO");
                        batchDTO.setTransStatus(2);
                        batchDTO.setOrganizationId(orgId);
                        batchDTO.setCreatedBy(userAccount.getUserId());
                        batchDTO.setErrmsg("同步失败，请点击行信息查看具体报错信息");
                        batchDTO.setRemark("本次共同步'资产基本信息'" + totalCount + "条记录,同步失败。失败原因："+returnMessage.getErrorMessage());
                        synLogUtil.createMisUpdateBatch(batchDTO, conn);
                    }
                    if (srvMessage.getErrorFlag().equalsIgnoreCase("Y")) {
                        String status = "1";
                        this.getEtsMisfaUpdateLog(orgnization_id, assetIds, batchDTO.getBatchId(), status);
                    } else {
                        String status = "2";
                        List<ErrorItem> errorItemList = updateAssetSrv.getErrorItemList();
                        this.getEtsMisfaUpdateLog(orgnization_id, assetIds, batchDTO.getBatchId(), status);
                        if (errorItemList.size() > 0) {
                            for (int i = 0; i < errorItemList.size(); i++) {
                                ErrorItem item = errorItemList.get(i);
                                String barCode = item.getRECORDNUMBER();
                                String msg = item.getERRORMESSAGE();
                                synLogUtil.getUpdateMisUpdateLogModel(batchDTO.getBatchId(), msg, barCode, conn);

                                totalTagNumbers.remove(barCode);
                            }
                            if(!totalTagNumbers.isEmpty()){ //如果同步失败的记录数少于同步记录数，上述日志记录全部记录为失败，则再次查询时将查询出本已同步成功的记录，再次同步就会报错,唐明胜增加
                                synLogUtil.writeMisFASuccessLog(batchDTO.getBatchId(), totalTagNumbers, conn);
                                totalTagNumbers.clear();
                            }
                        } else {
                            totalTagNumbers.clear();
                        }
                    }
                }
                return isRespExist;
            }
        } finally {
        }
    }

    /**
     * @param conn
     * @return
     * @throws SQLException
     */
    public String getNextSeq(Connection conn) throws SQLException {
        String nextSeq = "";
        SeqProducer seq = new SeqProducer(conn);
        nextSeq = seq.getGUID();
        return nextSeq;
    }

    /**
     * 功能：得到所有资产账簿
     * @return
     * @throws QueryException
     */
    public List getBookTypeCode() throws QueryException {
        SrvAssetsUpdateModel assetsUpdateModel = new SrvAssetsUpdateModel((SfUserDTO) userAccount, (SrvEamSyschronizeDTO) dtoParameter);
        List bookTypeList = new ArrayList();
        SQLModel sqlModel = assetsUpdateModel.getBookTypeCode();
        SimpleQuery simp = new SimpleQuery(sqlModel, conn);
        simp.executeQuery();
        if (simp.hasResult()) {
            RowSet rs = simp.getSearchResult();
            for (int i = 0; i < rs.getSize(); i++) {
                Row row = rs.getRow(i);
                try {
                    bookTypeList.add(row.getStrValue("COMPANY"));
                } catch (ContainerException e) {
                    e.printStackTrace();
                }
            }
        }
        return bookTypeList;
    }

    /**
     * 参数:投资分类ID 获取支撑网设备类型
     */
    public EtsMisfaTransactionRespDTO getCexType(String cexId)
            throws QueryException {
        EtsMisfaTransactionRespDTO dto = null;
        SynAttributeModel logModel = new SynAttributeModel();
        SQLModel sqlModel = logModel.getCexType(cexId);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.setDTOClassName(EtsMisfaTransactionRespDTO.class.getName());
        sq.executeQuery();
        if (sq.hasResult()) {
            dto = (EtsMisfaTransactionRespDTO) sq.getFirstDTO();
        }

        return dto;
    }

    /**
     * 参数: 获取业务平台编码,网络层次编码
     */
    public EtsMisfaTransactionRespDTO getOpe(String opeId)
            throws QueryException {
        EtsMisfaTransactionRespDTO dto = null;
        SynAttributeModel logModel = new SynAttributeModel();
        SQLModel sqlModel = logModel.getOpeModel(opeId);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.setDTOClassName(EtsMisfaTransactionRespDTO.class.getName());
        sq.executeQuery();
        if (sq.hasResult()) {
            dto = (EtsMisfaTransactionRespDTO) sq.getFirstDTO();
        }

        return dto;
    }

    public SrvReturnMessage getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(SrvReturnMessage returnMessage) {
        this.returnMessage = returnMessage;
    }

}
