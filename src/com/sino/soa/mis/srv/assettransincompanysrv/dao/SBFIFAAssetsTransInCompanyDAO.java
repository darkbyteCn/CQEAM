package com.sino.soa.mis.srv.assettransincompanysrv.dao;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;

import com.sino.base.constant.WorldConstant;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.datatrans.*;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.DTO;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.soa.common.SrvReturnMessage;
import com.sino.soa.mis.eip.fi.fa.sb_fi_fa_bcreateassettransincompanysrv.BCreateAssetTransInCompanySrvInputCollection;
import com.sino.soa.mis.eip.fi.fa.sb_fi_fa_bcreateassettransincompanysrv.BCreateAssetTransInCompanySrvInputItem;
import com.sino.soa.mis.eip.fi.fa.sb_fi_fa_bcreateassettransincompanysrv.ErrorItem;
import com.sino.soa.mis.eip.fi.fa.sb_fi_fa_bcreateassettransincompanysrv.ResponseItem;
import com.sino.soa.mis.srv.assettransincompanysrv.dto.SBFIFAAssetsTransInCompanyDTO;
import com.sino.soa.mis.srv.assettransincompanysrv.model.SBFIFAAssetsTransInCompanyModel;
import com.sino.soa.mis.srv.assettransincompanysrv.srv.SBFIFAAssetsTransInCompanySrv;
import com.sino.soa.util.SynLogUtil;
import com.sino.soa.util.dto.EtsMisfaTransactionRespDTO;
import com.sino.soa.util.dto.EtsMisfaUpdateBatchDTO;
import com.sino.soa.util.dto.EtsMisfaUpdateLogDTO;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-9-8
 * Time: 17:19:45
 * To change this template use File | Settings | File Templates.
 */
public class SBFIFAAssetsTransInCompanyDAO extends BaseDAO {
    private ResponseItem responseItem = null;
    private ErrorItem errorItem = null;
    private SrvReturnMessage returnMessage = null;
    private SfUserDTO sfUser = null;

    public SBFIFAAssetsTransInCompanyDAO(SfUserDTO userAccount, SBFIFAAssetsTransInCompanyDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        sfUser = userAccount;
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        SBFIFAAssetsTransInCompanyDTO dto = (SBFIFAAssetsTransInCompanyDTO) dtoParameter;
        sfUser = (SfUserDTO) userAccount;
        super.sqlProducer = new SBFIFAAssetsTransInCompanyModel(sfUser, dto);
    }

    public String syschronizeAssets(String systemId) {
        SBFIFAAssetsTransInCompanyModel synLocModel = (SBFIFAAssetsTransInCompanyModel) sqlProducer;
        SQLModel sqlModel = synLocModel.getSynAssModel(systemId);
        EtsMisfaUpdateBatchDTO updateBatchDTO = new EtsMisfaUpdateBatchDTO();
        SeqProducer seqProducer = new SeqProducer(conn);
        SynLogUtil synLogUtil = new SynLogUtil();
        BCreateAssetTransInCompanySrvInputCollection collection = new BCreateAssetTransInCompanySrvInputCollection();
        String userRespExists = "Y";
        try {
        	EtsMisfaTransactionRespDTO respDTO = synLogUtil.getMisfaResp(sfUser.getOrganizationId(), sfUser.getEmployeeNumber(), conn);
            if (respDTO == null) {
                userRespExists = "N";
            } else {
                SimpleQuery sq = new SimpleQuery(sqlModel, conn);
                sq.executeQuery();
                BCreateAssetTransInCompanySrvInputItem item = null;
                int count = 0;
                if (sq.hasResult()) {
                    RowSet rs = sq.getSearchResult();
                    String batchId = seqProducer.getGUID();
                    updateBatchDTO.setBatchId(batchId);
                    updateBatchDTO.setTransType("INTRANSCOMPANY");
//                    updateBatchDTO.setTransStatus(2);
                    updateBatchDTO.setTransStatus(0);//唐明胜修改为0，标示正在同步
                    updateBatchDTO.setOrganizationId(sfUser.getOrganizationId());
                    updateBatchDTO.setCreatedBy(sfUser.getUserId());
                    updateBatchDTO.setErrmsg("");
                    updateBatchDTO.setRemark("本次共同步" + rs.getSize() + "条");
                    String objectCode = null;
//                    synLogUtil.createMisUpdateBatch(updateBatchDTO, conn);
                    EtsMisfaUpdateLogDTO dto = null;
                    List<EtsMisfaUpdateLogDTO> list = new ArrayList<EtsMisfaUpdateLogDTO>();
                    for (int i = 0; i < rs.getSize(); i++) {
                        Row row = rs.getRow(i);
                        dto = new EtsMisfaUpdateLogDTO();
                        item = new BCreateAssetTransInCompanySrvInputItem();
                        item.setPRIKEY(row.getStrValue("OLD_BARCODE"));
                        item.setLINENUMBER(new BigDecimal(Integer.toString(count)));
                        item.setTAGNUMBER(row.getStrValue("NEW_BARCODE"));
                        objectCode = row.getStrValue("WORKORDER_OBJECT_CODE");
                        String arr[]= StrUtil.splitStr(objectCode,".");
                        item.setCOUNTRYTO(arr[0]);
                        item.setLOCATIONTO(arr[1]);
                        item.setCOSTCCTO(row.getStrValue("COST_CENTER_CODE"));
                        item.setASSIGNEDTO(row.getStrValue("NEW_EMPLOYEE_NUMBER"));
//                        item.setASSIGNEDTO("88002308");
                        item.setCREATEDBY(respDTO.getUserId());
//                        item.setEMPLOYEENUMBER(respDTO.getEmployeeNumber());
                        item.setRESPONSIBILITYID(respDTO.getRespId());

                        collection.getBCreateAssetTransInCompanySrvInputItem().add(item);
                        count = count + 1;
                        dto.setAssetId(StrUtil.strToInt(row.getStrValue("ASSET_ID")));
                        dto.setTransactionNo(row.getStrValue("TRANS_NO"));
                        dto.setBarcode(row.getStrValue("OLD_BARCODE"));
                        dto.setLocationFrom(row.getStrValue("OLD_ASSETS_LOCATION"));
                        dto.setLocationTo(row.getStrValue("NEW_ASSETS_LOCATION"));
                        dto.setOwnerFrom(row.getStrValue("OLD_DEPT_CODE"));
                        dto.setOwnerTo(row.getStrValue("NEW_DEPT"));
                        list.add(dto);
                    }

                    SBFIFAAssetsTransInCompanySrv srv = new SBFIFAAssetsTransInCompanySrv();
                    srv.setCollection(collection);
                    srv.excute();

                    synLogUtil.createMisUpdateBatch(updateBatchDTO, conn);//日志记录放在调用SOA服务之后

                    returnMessage = srv.getReturnMessage();
                    
                    if (returnMessage.getErrorFlag().equalsIgnoreCase("Y")) {
                        updateBatchDTO = new EtsMisfaUpdateBatchDTO();
                        updateBatchDTO.setBatchId(batchId);
                        updateBatchDTO.setTransType("INTRANSCOMPANY");
                        updateBatchDTO.setTransStatus(1);
                        updateBatchDTO.setOrganizationId(sfUser.getOrganizationId());
                        updateBatchDTO.setCreatedBy(sfUser.getUserId());
                        updateBatchDTO.setErrmsg("");
                        updateBatchDTO.setRemark("本次共同步'公司内资产调拨同步'" + count + "条记录，全部同步成功");
                        synLogUtil.updateMisUpdateBach(updateBatchDTO, conn);
                    } else {
                        updateBatchDTO = new EtsMisfaUpdateBatchDTO();
                        updateBatchDTO.setBatchId(batchId);
                        updateBatchDTO.setTransType("INTRANSCOMPANY");
                        updateBatchDTO.setTransStatus(2);
                        updateBatchDTO.setOrganizationId(sfUser.getOrganizationId());
                        updateBatchDTO.setCreatedBy(sfUser.getUserId());
                        updateBatchDTO.setErrmsg("同步失败，请点击行信息查看具体报错信息");
                        updateBatchDTO.setRemark("本次共同步'公司内资产调拨同步'" + count + "条记录，同步失败，失败原因："+returnMessage.getErrorMessage());
                        synLogUtil.updateMisUpdateBach(updateBatchDTO, conn);
                    }
                    if (returnMessage.getErrorFlag().equalsIgnoreCase("Y")) {
                        int status = 1;
                        this.getEtsMisfaUpdateLog(sfUser.getOrganizationId(), list, updateBatchDTO.getBatchId(), status);
                    } else {
                        int status = 2;
                        List<ErrorItem> errorItemList = srv.getErrorItemList();
                        this.getEtsMisfaUpdateLog(sfUser.getOrganizationId(), list, updateBatchDTO.getBatchId(), status);
                        if (errorItemList.size() > 0) {
                            for (int i = 0; i < errorItemList.size(); i++) {
                                ErrorItem item1 = errorItemList.get(i);
                                String barCode = item1.getRECORDNUMBER();
                                String msg = item1.getERRORMESSAGE();
                                synLogUtil.getUpdateMisUpdateLogModel(updateBatchDTO.getBatchId(), msg, barCode, conn);
                            }
                        }
                        //为什么只插入同步失败的记录，同步成功的记录却不插？

                    }
                }
            }
        } catch (ContainerException e) {
           e.printLog();
        } catch (DatatypeConfigurationException e) {
            Logger.logError(e);
        } catch (DataHandleException e) {
            e.printLog();
        } catch (QueryException e) {
            e.printLog();
        } catch (SQLException e) {
            Logger.logError(e);
        } catch (Exception e) {
            Logger.logError(e);
        } finally {
        }
        return userRespExists;
    }

    public ResponseItem getResponseItem() {
        return responseItem;
    }

    public void setResponseItem(ResponseItem responseItem) {
        this.responseItem = responseItem;
    }

    public ErrorItem getErrorItem() {
        return errorItem;
    }

    public void setErrorItem(ErrorItem errorItem) {
        this.errorItem = errorItem;
    }

    public SrvReturnMessage getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(SrvReturnMessage returnMessage) {
        this.returnMessage = returnMessage;
    }

    public String getNextSeq(Connection conn) throws SQLException {
        String nextSeq = "";
        SeqProducer seq = new SeqProducer(conn);
        nextSeq = seq.getGUID();
        return nextSeq;
    }

    /**
     * @param orgnization_id
     * @param list
     * @param batchId
     * @param status
     */
    public void getEtsMisfaUpdateLog(int orgnization_id, List<EtsMisfaUpdateLogDTO> list, String batchId, int status) {
        try {
            if (list != null && list.size() > 0) {
                SynLogUtil synLogUtil = new SynLogUtil();
                for (EtsMisfaUpdateLogDTO dto : list) {
                    dto.setUpdateType("INTRANSCOMPANY");
                    dto.setTransStatus(status);
                    dto.setBatchId(batchId);
                    dto.setOrganizationId(orgnization_id);
                    synLogUtil.updateMisUpdateLog(dto, conn);
                }
            }

        } catch (DataHandleException e) {
            e.printStackTrace();
        } catch (CalendarException e) {
            e.printStackTrace();
        }
    }

    public String autoSyschronizeAssets() {
        SBFIFAAssetsTransInCompanyModel synLocModel = (SBFIFAAssetsTransInCompanyModel) sqlProducer;
        SQLModel sqlModel = synLocModel.getAutoSynAssModel();
        EtsMisfaUpdateBatchDTO updateBatchDTO = new EtsMisfaUpdateBatchDTO();
        SeqProducer seqProducer = new SeqProducer(conn);
        SynLogUtil synLogUtil = new SynLogUtil();
        BCreateAssetTransInCompanySrvInputCollection collection = new BCreateAssetTransInCompanySrvInputCollection();
        String userRespExists = "Y";
        try {
        	EtsMisfaTransactionRespDTO respDTO = synLogUtil.getMisfaResp(sfUser.getOrganizationId(), sfUser.getEmployeeNumber(), conn);
            if (respDTO == null) {
                userRespExists = "N";
            } else {
                SimpleQuery sq = new SimpleQuery(sqlModel, conn);
                sq.executeQuery();
                BCreateAssetTransInCompanySrvInputItem item = null;
                int count = 0;
                if (sq.hasResult()) {
                    RowSet rs = sq.getSearchResult();
                    updateBatchDTO.setBatchId(seqProducer.getGUID());
                    updateBatchDTO.setTransType("INTRANSCOMPANY");
                    updateBatchDTO.setTransStatus(2);
                    updateBatchDTO.setOrganizationId(sfUser.getOrganizationId());
                    updateBatchDTO.setCreatedBy(sfUser.getUserId());
                    updateBatchDTO.setErrmsg("");
                    updateBatchDTO.setRemark("本次共同步" + rs.getSize() + "条");
                    String objectCode = null;
                    synLogUtil.createMisUpdateBatch(updateBatchDTO, conn);
                    EtsMisfaUpdateLogDTO dto = null;
                    List<EtsMisfaUpdateLogDTO> list = new ArrayList<EtsMisfaUpdateLogDTO>();
                    List<String> totalTagNumbers = new ArrayList<String>();
                    for (int i = 0; i < rs.getSize(); i++) {
                        Row row = rs.getRow(i);
                        dto = new EtsMisfaUpdateLogDTO();
                        item = new BCreateAssetTransInCompanySrvInputItem();
                        item.setPRIKEY(row.getStrValue("OLD_BARCODE"));
                        item.setLINENUMBER(new BigDecimal(Integer.toString(count)));
                        item.setTAGNUMBER(row.getStrValue("NEW_BARCODE"));
                        objectCode = row.getStrValue("WORKORDER_OBJECT_CODE");
                        String arr[]= StrUtil.splitStr(objectCode,".");
    //                    item.setCOUNTRYTO(arr[0]);
                        item.setLOCATIONTO(arr[1]);
                        item.setCOSTCCTO(row.getStrValue("COST_CENTER_CODE"));
                        item.setASSIGNEDTO(row.getStrValue("NEW_EMPLOYEE_NUMBER"));
//                        item.setCREATEDBY(respDTO.getUserId());
                        item.setEMPLOYEENUMBER(respDTO.getEmployeeNumber());
                        item.setRESPONSIBILITYID(respDTO.getRespId());

                        collection.getBCreateAssetTransInCompanySrvInputItem().add(item);
                        count = count + 1;
                        dto.setAssetId(StrUtil.strToInt(row.getStrValue("ASSET_ID")));
                        dto.setTransactionNo(row.getStrValue("TRANS_NO"));
                        dto.setBarcode(row.getStrValue("OLD_BARCODE"));
                        dto.setLocationFrom(row.getStrValue("OLD_ASSETS_LOCATION"));
                        dto.setLocationTo(row.getStrValue("NEW_ASSETS_LOCATION"));
                        dto.setOwnerFrom(row.getStrValue("OLD_DEPT_CODE"));
                        dto.setOwnerTo(row.getStrValue("NEW_DEPT"));
                        list.add(dto);
                        totalTagNumbers.add(dto.getBarcode());
                        if (i >= 500){//同步数量限制为500，避免SOA同步程序出错
                            break;
                        }
                    }

                    SBFIFAAssetsTransInCompanySrv srv = new SBFIFAAssetsTransInCompanySrv();
                    srv.setCollection(collection);
                    srv.excute();
                    returnMessage = srv.getReturnMessage();

                    if (returnMessage.getErrorFlag().equalsIgnoreCase("Y")) {
                        updateBatchDTO = new EtsMisfaUpdateBatchDTO();
                        updateBatchDTO.setBatchId(this.getNextSeq(conn));
                        updateBatchDTO.setTransType("INTRANSCOMPANY");
                        updateBatchDTO.setTransStatus(2);
                        updateBatchDTO.setOrganizationId(sfUser.getOrganizationId());
                        updateBatchDTO.setCreatedBy(sfUser.getUserId());
                        updateBatchDTO.setErrmsg("");
                        updateBatchDTO.setRemark("本次共同步" + count + "条记录，全部同步成功");
                        synLogUtil.createMisUpdateBatch(updateBatchDTO, conn);

                        totalTagNumbers.clear();//added by mshtang
                    } else {
                        updateBatchDTO = new EtsMisfaUpdateBatchDTO();
                        updateBatchDTO.setBatchId(this.getNextSeq(conn));
                        updateBatchDTO.setTransType("INTRANSCOMPANY");
                        updateBatchDTO.setTransStatus(2);
                        updateBatchDTO.setOrganizationId(sfUser.getOrganizationId());
                        updateBatchDTO.setCreatedBy(sfUser.getUserId());
                        updateBatchDTO.setRemark("本次共同步" + count + "条记录，同步失败");
                        updateBatchDTO.setRemark("");
                        synLogUtil.createMisUpdateBatch(updateBatchDTO, conn);
                    }
                    if (returnMessage.getErrorFlag().equalsIgnoreCase("Y")) {
                        int status = 1;
                        this.getEtsMisfaUpdateLog(sfUser.getOrganizationId(), list, updateBatchDTO.getBatchId(), status);
                    } else {
                        int status = 2;
                        List<ErrorItem> errorItemList = srv.getErrorItemList();
                        this.getEtsMisfaUpdateLog(sfUser.getOrganizationId(), list, updateBatchDTO.getBatchId(), status);
                        if (errorItemList.size() > 0) {
                            for (int i = 0; i < errorItemList.size(); i++) {
                                ErrorItem item1 = errorItemList.get(i);
                                String barCode = item1.getRECORDNUMBER();
                                String msg = item1.getERRORMESSAGE();
                                synLogUtil.getUpdateMisUpdateLogModel(updateBatchDTO.getBatchId(), msg, barCode, conn);

                                totalTagNumbers.remove(barCode);
                            }
                            if(!totalTagNumbers.isEmpty()){//同步成功部分的数据，需要更新其同步日志的同步状态，唐明胜增加
                                synLogUtil.writeMisFASuccessLog(updateBatchDTO.getBatchId(), totalTagNumbers, conn);
                                totalTagNumbers.clear();
                            }
                        } else {
                            totalTagNumbers.clear();
                        }
                    }
                }
            }
        } catch (ContainerException e) {
           e.printLog();
        } catch (DatatypeConfigurationException e) {
            Logger.logError(e);
        } catch (DataHandleException e) {
            e.printLog();
        } catch (QueryException e) {
            e.printLog();
        } catch (SQLException e) {
            Logger.logError(e);
        } catch (Exception e) {
            Logger.logError(e);
        } finally {
        }
        return userRespExists;
    }

    /**
     * 公司内资产调拨Excel导出
     */
    public File getExportFile() throws DataTransException, SQLModelException {
    	SBFIFAAssetsTransInCompanyModel synLocModel = (SBFIFAAssetsTransInCompanyModel) sqlProducer;
        SQLModel sqlModel = synLocModel.getPageQueryModel();
        String reportTitle = "EAM系统公司内资产调拨";
        String fileName = reportTitle + ".xls";
        TransRule rule = new TransRule();
        rule.setDataSource(sqlModel);
        rule.setSourceConn(conn);
        String filePath = WorldConstant.USER_HOME;
        filePath += WorldConstant.FILE_SEPARATOR;
        filePath += fileName;
        rule.setTarFile(filePath);
        DataRange range = new DataRange();
        rule.setDataRange(range);
        rule.setFieldMap(getFieldMap());
        CustomTransData custData = new CustomTransData();
        custData.setReportTitle(reportTitle);
        custData.setReportPerson(sfUser.getUsername());
        custData.setNeedReportDate(true);
        rule.setCustData(custData);
        rule.setCalPattern(CAL_PATT_50);
        TransferFactory factory = new TransferFactory();
        DataTransfer transfer = factory.getTransfer(rule);
        transfer.transData();
        return (File) transfer.getTransResult();
    }

    private Map getFieldMap() {
        Map fieldMap = new HashMap();
        fieldMap.put("TRANSFER_TYPE", "调拨方式");
        fieldMap.put("TRANS_NO", "调拨单号");
        fieldMap.put("NEW_BARCODE", "资产标签");
        fieldMap.put("ASSET_NUMBER", "资产编号");
        fieldMap.put("NEW_ITEM_NAME", "资产描述");
        fieldMap.put("NEW_ITEM_SPEC", "资产型号");
        fieldMap.put("OLD_ASSETS_LOCATION", "原资产地点");
        fieldMap.put("NEW_ASSETS_LOCATION", "新资产地点");
        fieldMap.put("OLD_DEPT_NAME", "原责任部门");
        fieldMap.put("NEW_DEPT_NAME", "新责任部门");
        fieldMap.put("OLD_USER_NAME", "原责任人");
        fieldMap.put("NEW_USER_NAME", "新责任人");
        return fieldMap;
    } 
    
}
