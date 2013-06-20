package com.sino.soa.td.srv.assetLocComb.dao;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sino.base.constant.WorldConstant;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.datatrans.*;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.QueryException;
import com.sino.base.util.StrUtil;
import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.config.SinoConfig;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.soa.common.SrvReturnMessage;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_bimportassetloccombsrv.BImportAssetLocCombSrvInputItem;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_bimportassetloccombsrv.ErrorItem;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_bimportassetloccombsrv.ResponseItem;
import com.sino.soa.td.eip.sy.sy.sb_sy_sy_importvsetvalueinfosrv.ImportVSetValueInfoSrvInputItem;
import com.sino.soa.td.srv.assetLocComb.dto.TDSrvAssetLocCombDTO;
import com.sino.soa.td.srv.assetLocComb.model.TDSrvAssetLocCombModel;
import com.sino.soa.td.srv.assetLocComb.srv.TDImportAssetLocCombSrv;
import com.sino.soa.td.srv.valueinfo.srv.SBSYSYTdImportVSetValueInfoSrv;
import com.sino.soa.util.SynLogUtil;
import com.sino.soa.util.dto.EtsMisfaTransactionRespDTO;
import com.sino.soa.util.dto.EtsMisfaUpdateBatchDTO;
import com.sino.soa.util.dto.MisLocDTO;

//接口相关类

/**
 * date：2011-09-16
 * user：wangzhipeng
 * function：资产地点组合批量导入_TD
 */
public class TDSrvAssetLocCombDAO extends AMSBaseDAO {

    private ResponseItem responseItem = null;
    private ErrorItem errorItem = null;
    private SrvReturnMessage returnMessage = null;

    public TDSrvAssetLocCombDAO(SfUserDTO userAccount, TDSrvAssetLocCombDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        TDSrvAssetLocCombDTO dtoPara = (TDSrvAssetLocCombDTO) dtoParameter;
        super.sqlProducer = new TDSrvAssetLocCombModel((SfUserDTO) userAccount, dtoPara);
    }

    /**
     * EAM地点同步_TD
     *
     * @param objectNos EO表序列
     * @throws QueryException
     * @throws ContainerException
     * @throws SQLException
     */
    public String synLocComb(String objectNos) throws QueryException, ContainerException, SQLException, DataHandleException {
        TDSrvAssetLocCombModel synLocModel = (TDSrvAssetLocCombModel) sqlProducer;
        List<BImportAssetLocCombSrvInputItem> inputItems = new ArrayList<BImportAssetLocCombSrvInputItem>();
        List<ImportVSetValueInfoSrvInputItem> inputItems1 = new ArrayList<ImportVSetValueInfoSrvInputItem>();  //值集
        EtsMisfaUpdateBatchDTO updateBatchDTO = new EtsMisfaUpdateBatchDTO();
        SeqProducer seqProducer = new SeqProducer(conn);
        SynLogUtil synLogUtil = new SynLogUtil();
        String isRespExist = "YES";
        try {
            EtsMisfaTransactionRespDTO respDTO = synLogUtil.getMisfaResp(userAccount.getOrganizationId(), userAccount.getEmployeeNumber(), conn);//获取查询结果(MIS相关人员及职责)
            if (respDTO == null) {
                isRespExist = "NO";
                return isRespExist;
            } else {
                SQLModel sqlModel = synLocModel.getSynLocModel(objectNos);
                SimpleQuery sq = new SimpleQuery(sqlModel, conn);
                sq.executeQuery();
                ImportVSetValueInfoSrvInputItem inputItem1 = null;   //值集
                BImportAssetLocCombSrvInputItem inputItem = null;   //组合地点
                if (sq.hasResult()) {
                    RowSet rs = sq.getSearchResult();
                    String batchSeq = seqProducer.getGUID();
                    updateBatchDTO.setBatchId(batchSeq);
                    updateBatchDTO.setOrganizationId(userAccount.getOrganizationId());
                    updateBatchDTO.setCreatedBy(userAccount.getUserId());
                    updateBatchDTO.setTransType("SYNLOC_TD");
                    updateBatchDTO.setTransStatus(0);
                    updateBatchDTO.setRemark("本次共同步TD地点组合信息" + rs.getSize() + "条");
                    //同步日志 到  ETS_MISFA_UPDATE_BATCH
                    synLogUtil.createMisUpdateBatch(updateBatchDTO, conn);
                    //创建日志 到  ETS_FA_NEW_LOC
//                    DBOperator.updateRecord(synLocModel.getInsertSynLogModel(objectNos, batchSeq, userAccount), conn); //唐明胜注释，将日志记录放到调用SOA服务之后，
                    MisLocDTO locDTO = null;
                    List<MisLocDTO> list = new ArrayList<MisLocDTO>();
                    List<String> totalObjectNos = new ArrayList<String>();
                    for (int i = 0; i < rs.getSize(); i++) {
                        Row row = rs.getRow(i);
                        inputItem = new BImportAssetLocCombSrvInputItem();
                        locDTO = new MisLocDTO();
                        locDTO.setKey(row.getStrValue("WORKORDER_OBJECT_NO"));          //序号
                        locDTO.setCode(row.getStrValue("WORKORDER_OBJECT_CODE"));       //编码
                        locDTO.setName(row.getStrValue("WORKORDER_OBJECT_NAME"));       //名称
                        locDTO.setOrganizationId(row.getStrValue("ORGANIZATION_ID"));   //组织ID
                        locDTO = validateLoc(locDTO);
                        inputItem.setPRIKEY(row.getStrValue("WORKORDER_OBJECT_CODE"));
                        inputItem.setSEGMENT1(locDTO.getSegment1()); //第1段
                        inputItem.setSEGMENT2(locDTO.getSegment2()); //  2
                        inputItem.setSEGMENT3(locDTO.getSegment3()); //  3
                        inputItem.setENABLEDFLAG("Y");
                        inputItem.setCREATEDBY(respDTO.getUserId()); //制单人ID 3920
//                        inputItem.setEMPLOYEENUMBER(respDTO.getEmployeeNumber()); //制单人员工编号
                        inputItem.setRESPONSIBILITYID((BigDecimal) respDTO.getRespId()); //导入职责ID
                        inputItems.add(inputItem);
                        list.add(locDTO);

                        //值集
                        inputItem1 = new ImportVSetValueInfoSrvInputItem();
                        inputItem1.setPRIKEY(row.getStrValue("WORKORDER_OBJECT_NO"));
                        inputItem1.setFLEXVALUESETNAME(SinoConfig.getFlexValueSetNameTD());
                        inputItem1.setVALIDATIONTYPE("I");
                        inputItem1.setFLEXVALUE(locDTO.getSegment2());            //code
                        inputItem1.setDESCRIPTION(locDTO.getSegment2Desc());      //name
                        inputItem1.setENABLEDFLAG("Y");
                        inputItem1.setSUMMARYFLAG("N");
                        inputItem1.setPARENTFLEXVALUE(locDTO.getSegment1());     //地点第一段
//        	        inputItem1.setCREATEDBY(respDTO.getUserId());
                        inputItem1.setEMPLOYEENUMBER(respDTO.getEmployeeNumber());
                        inputItems1.add(inputItem1);

                        totalObjectNos.add(locDTO.getKey());//唐明胜增加
                    }
                    //值集
                    SBSYSYTdImportVSetValueInfoSrv srv1 = new SBSYSYTdImportVSetValueInfoSrv();
                    srv1.setImportVSetValueInfoSrvInputItems(inputItems1);
                    srv1.execute();

                    DBOperator.updateRecord(synLocModel.getInsertSynLogModel(objectNos, batchSeq, userAccount), conn); //唐明胜添加，将日志记录放到调用SOA服务之后，

                    if (srv1.getReturnMessage().getErrorFlag().equalsIgnoreCase("Y")) {
                        //组合地点
                        TDImportAssetLocCombSrv srv = new TDImportAssetLocCombSrv();
                        srv.setSrvInputItems(inputItems);
                        srv.excute();
                        if (srv.getReturnMessage().getErrorFlag().equalsIgnoreCase("Y")) {
                            returnMessage = srv.getReturnMessage();
                            updateBatchDTO.setBatchId(batchSeq);
                            updateBatchDTO.setOrganizationId(userAccount.getOrganizationId());
                            updateBatchDTO.setCreatedBy(userAccount.getUserId());
                            updateBatchDTO.setTransType("SYNLOC_TD");
                            updateBatchDTO.setTransStatus(1);
                            updateBatchDTO.setRemark("本次共同步'TD地点组合信息'" + rs.getSize() + "条,全部同步成功!");
                            synLogUtil.updateMisUpdateBach(updateBatchDTO, conn);
                            DBOperator.updateBatchRecords(synLocModel.getUpdateSynLogModel(batchSeq, list), conn);

                            totalObjectNos.clear();
                        } else {
                            returnMessage = srv.getReturnMessage();
                            updateBatchDTO.setBatchId(batchSeq);
                            updateBatchDTO.setOrganizationId(userAccount.getOrganizationId());
                            updateBatchDTO.setCreatedBy(userAccount.getUserId());
                            updateBatchDTO.setTransType("SYNLOC_TD");
                            updateBatchDTO.setTransStatus(2);
                            updateBatchDTO.setErrmsg("同步失败，请点击行信息查看具体报错信息");
                            updateBatchDTO.setRemark("本次共同步'TD地点组合信息'" + rs.getSize() + "条, 同步失败, 失败原因："+returnMessage.getErrorMessage());
                            synLogUtil.updateMisUpdateBach(updateBatchDTO, conn);
                            List<ErrorItem> errorlist = srv.getErrorItemList();
                            if (errorlist.size() > 0) {
                                for (int i = 0; i < errorlist.size(); i++) {
                                    ErrorItem item1 = errorlist.get(i);
                                    String barCode = item1.getRECORDNUMBER();
                                    String msg = item1.getERRORMESSAGE();
                                    DBOperator.updateRecord(synLocModel.getUpdateSynErrorLogModel3(batchSeq, barCode, msg), conn);

                                    totalObjectNos.remove(barCode);
                                }
                                if(!totalObjectNos.isEmpty()){//如果同步失败的记录数少于同步记录数，上述日志记录全部记录为失败，则再次查询时将查询出本已同步成功的记录，再次同步就会报错.唐明胜增加本处理
                                    List<SQLModel> sqlList = synLocModel.getLocationSynSuccessModel(batchSeq, totalObjectNos);
                                    DBOperator.updateBatchRecords(sqlList, conn);
                                    totalObjectNos.clear();
                                }
                            } else {
                                totalObjectNos.clear();
                            }
                        }
                    } else {
                        returnMessage = srv1.getReturnMessage();
                        updateBatchDTO.setBatchId(batchSeq);
                        updateBatchDTO.setOrganizationId(userAccount.getOrganizationId());
                        updateBatchDTO.setCreatedBy(userAccount.getUserId());
                        updateBatchDTO.setTransType("SYNLOC_TD");
                        updateBatchDTO.setTransStatus(2);
                        updateBatchDTO.setErrmsg("同步失败，请点击行信息查看具体报错信息");
                        updateBatchDTO.setRemark("本次共同步'TD地点组合信息'" + rs.getSize() + "条,导入地点对应的值集失败, 失败原因："+returnMessage.getErrorMessage());
                        synLogUtil.updateMisUpdateBach(updateBatchDTO, conn);
                        List<com.sino.soa.td.eip.sy.sy.sb_sy_sy_importvsetvalueinfosrv.ErrorItem> errorlist1 = srv1.getErrorItemList();
                        if (errorlist1.size() > 0) {
                            for (int i = 0; i < errorlist1.size(); i++) {
                                com.sino.soa.td.eip.sy.sy.sb_sy_sy_importvsetvalueinfosrv.ErrorItem item1 = errorlist1.get(i);
                                String barCode = item1.getRECORDNUMBER();
                                String msg = item1.getERRORMESSAGE();
                                DBOperator.updateRecord(synLocModel.getUpdateSynErrorLogModel3(batchSeq, barCode, msg), conn);

                                totalObjectNos.remove(barCode);
                            }
                        }
                        if(!totalObjectNos.isEmpty()){//如果同步失败的记录数少于同步记录数，上述日志记录全部记录为失败，则再次查询时将查询出本已同步成功的记录，再次同步就会报错.唐明胜增加本处理
                            List<SQLModel> sqlList = synLocModel.getLocationSynSuccessModel(batchSeq, totalObjectNos);
                            DBOperator.updateBatchRecords(sqlList, conn);
                            totalObjectNos.clear();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return isRespExist;
    }

    //分割字符串
    private MisLocDTO validateLoc(MisLocDTO locDTO) {
        String code[] = StrUtil.splitStr(locDTO.getCode(), ".");
        String name[] = StrUtil.splitStr(locDTO.getName(), ".");
        locDTO.setSegment1(code[0]);
        locDTO.setSegment2(code[1]);
        locDTO.setSegment3(code[2]);
        locDTO.setSegment1Desc(name[0]);
        locDTO.setSegment2Desc(name[1]);
        locDTO.setSegment3Desc(name[2]);
        return locDTO;
    }

    public File getExportFile() throws DataTransException {
        TDSrvAssetLocCombModel modelProducer = (TDSrvAssetLocCombModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getPageQueryModel();
        String reportTitle = "EAM系统新增地点";
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
        custData.setReportPerson(userAccount.getUsername());
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
        fieldMap.put("LOCATION_CODE", "地点代码");
        fieldMap.put("WORKORDER_OBJECT_LOCATION", "地点描述");
        fieldMap.put("WORKORDER_CATEGORY", "地点类别");
        fieldMap.put("CREATION_DATE", "创建日期");
        return fieldMap;
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

}
