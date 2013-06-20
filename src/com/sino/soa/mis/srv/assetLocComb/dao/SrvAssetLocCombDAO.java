package com.sino.soa.mis.srv.assetLocComb.dao;

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
import com.sino.soa.mis.eip.fi.fa.sb_fi_fa_bimportassetloccombsrv.BImportAssetLocCombSrvInputItem;
import com.sino.soa.mis.eip.fi.fa.sb_fi_fa_bimportassetloccombsrv.ErrorItem;
import com.sino.soa.mis.eip.fi.fa.sb_fi_fa_bimportassetloccombsrv.ResponseItem;
import com.sino.soa.mis.eip.sy.sy.sb_sy_sy_importvsetvalueinfosrv.ImportVSetValueInfoSrvInputItem;
import com.sino.soa.mis.srv.assetLocComb.dto.SrvAssetLocCombDTO;
import com.sino.soa.mis.srv.assetLocComb.model.SrvAssetLocCombModel;
import com.sino.soa.mis.srv.assetLocComb.srv.ImportAssetLocCombSrv;
import com.sino.soa.mis.srv.valueinfo.srv.SBSYSYImportVSetValueInfoSrv;
import com.sino.soa.util.SynLogUtil;
import com.sino.soa.util.dto.EtsMisfaTransactionRespDTO;
import com.sino.soa.util.dto.EtsMisfaUpdateBatchDTO;
import com.sino.soa.util.dto.MisLocDTO;

//接口相关类

/**
 * date：2011-09-16
 * user：wangzhipeng
 * function：资产地点组合批量导入
 */
public class SrvAssetLocCombDAO extends AMSBaseDAO {

    private ResponseItem responseItem = null;
    private ErrorItem errorItem = null;
    private SrvReturnMessage returnMessage = null;
    //private SrvReturnMessage returnMessage1=null;

    public SrvAssetLocCombDAO(SfUserDTO userAccount, SrvAssetLocCombDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        SrvAssetLocCombDTO dtoPara = (SrvAssetLocCombDTO) dtoParameter;
        super.sqlProducer = new SrvAssetLocCombModel((SfUserDTO) userAccount, dtoPara);
    }

    /**
     * EAM地点同步
     *
     * @param objectNos EO表序列
     * @throws QueryException
     * @throws ContainerException
     * @throws SQLException
     */
    public String synLocComb(String objectNos) throws QueryException, ContainerException, SQLException, DataHandleException {
        SrvAssetLocCombModel synLocModel = (SrvAssetLocCombModel) sqlProducer;
        List<BImportAssetLocCombSrvInputItem> inputItems = new ArrayList<BImportAssetLocCombSrvInputItem>(); //组合地点
        List<ImportVSetValueInfoSrvInputItem> inputItems1 = new ArrayList<ImportVSetValueInfoSrvInputItem>(); //值集
        EtsMisfaUpdateBatchDTO updateBatchDTO = new EtsMisfaUpdateBatchDTO();
        SeqProducer seqProducer = new SeqProducer(conn);
        SynLogUtil synLogUtil = new SynLogUtil();
        String isRespExist = "YES";
        try {
            EtsMisfaTransactionRespDTO respDTO = synLogUtil.getMisfaResp(userAccount.getOrganizationId(), userAccount.getEmployeeNumber(), conn);
            if (respDTO == null) {
                isRespExist = "NO";
                return isRespExist;
            } else {
                SQLModel sqlModel = synLocModel.getSynLocModel(objectNos);
                SimpleQuery sq = new SimpleQuery(sqlModel, conn);
                sq.executeQuery();
                BImportAssetLocCombSrvInputItem inputItem = null;    //组合地点导入对象
                ImportVSetValueInfoSrvInputItem inputItem1 = null;   //值集
                if (sq.hasResult()) {
                    RowSet rs = sq.getSearchResult();
                    String batchSeq = seqProducer.getGUID();
                    updateBatchDTO.setBatchId(batchSeq);
                    updateBatchDTO.setOrganizationId(userAccount.getOrganizationId());
                    updateBatchDTO.setCreatedBy(userAccount.getUserId());
                    updateBatchDTO.setTransType("SYNLOC");
                    updateBatchDTO.setTransStatus(0);
                    updateBatchDTO.setRemark("本次共同步'地点组合信息'" + rs.getSize() + "条");
                    //同步日志 到  ETS_MISFA_UPDATE_BATCH
//                    synLogUtil.createMisUpdateBatch(updateBatchDTO, conn);//唐明胜注释，该处不需要记录日志，否则同一次同步会产生两个批。
                    //创建日志 到  ETS_FA_NEW_LOC
//                    DBOperator.updateRecord(synLocModel.getInsertSynLogModel(objectNos, batchSeq, userAccount), conn);
                    MisLocDTO locDTO = null;
                    List<MisLocDTO> list = new ArrayList<MisLocDTO>();
                    List<String> totalObjectNos = new ArrayList<String>();
                    boolean hasProcessed = false;
                    for (int i = 0; i < rs.getSize(); i++) {
                        Row row = rs.getRow(i);
                        //组合地点
                        inputItem = new BImportAssetLocCombSrvInputItem();
                        locDTO = new MisLocDTO();

                        locDTO.setKey(row.getStrValue("WORKORDER_OBJECT_NO"));         //序号
                        locDTO.setCode(row.getStrValue("WORKORDER_OBJECT_CODE"));      //编码
                        locDTO.setName(row.getStrValue("WORKORDER_OBJECT_NAME"));      //名称
                        locDTO.setOrganizationId(row.getStrValue("ORGANIZATION_ID"));  //组织ID
                        validateLoc(locDTO);
                        if (locDTO.getSegment1().length() == 0) {
                            continue;
                        }
                        hasProcessed = true;
                        inputItem.setPRIKEY(row.getStrValue("WORKORDER_OBJECT_NO"));
                        inputItem.setSEGMENT1(locDTO.getSegment1()); //第1段 代码
                        inputItem.setSEGMENT2(locDTO.getSegment2()); //  2
                        inputItem.setSEGMENT3(locDTO.getSegment3()); //  3
                        inputItem.setENABLEDFLAG("Y");
                        inputItem.setCREATEDBY(respDTO.getUserId()); //制单人ID new BigDecimal(2750)
//                        inputItem.setEMPLOYEENUMBER(respDTO.getEmployeeNumber());      //制单人员工工号
                        inputItem.setRESPONSIBILITYID((BigDecimal) respDTO.getRespId()); //导入职责ID
                        //inputItem.setRESPONSIBILITYNAME("");//导入职责名称
                        inputItems.add(inputItem);
                        list.add(locDTO);

                        //值集
                        inputItem1 = new ImportVSetValueInfoSrvInputItem();
                        inputItem1.setPRIKEY(row.getStrValue("WORKORDER_OBJECT_NO")); //地点同步中为何是WORKORDER_OBJECT_NO,而不是WORKORDER_OBJECT_CODE，需要袁振棠定夺
                        inputItem1.setFLEXVALUESETNAME(SinoConfig.getFlexValueSetNameMis());   //CMCC_FA_LOC_2
                        inputItem1.setVALIDATIONTYPE("I");
                        inputItem1.setFLEXVALUE(locDTO.getSegment2());            //code
                        //为何只传递地点代码的第二段，而不是全部？
                        inputItem1.setDESCRIPTION(locDTO.getSegment2Desc());      //name
                        inputItem1.setENABLEDFLAG("Y");
                        inputItem1.setSUMMARYFLAG("N");
                        inputItem1.setPARENTFLEXVALUE(locDTO.getSegment1());     //地点第一段
//        	        inputItem1.setCREATEDBY(respDTO.getUserId());
                        inputItem1.setEMPLOYEENUMBER(respDTO.getEmployeeNumber());
                        inputItems1.add(inputItem1);

                        totalObjectNos.add(locDTO.getKey());//唐明胜增加
                    }
                    if (!hasProcessed) {
                        return "";
                    }
                    //导入值集
                    SBSYSYImportVSetValueInfoSrv srv1 = new SBSYSYImportVSetValueInfoSrv();
                    srv1.setImportVSetValueInfoSrvInputItems(inputItems1);
                    srv1.excute();

                    DBOperator.updateRecord(synLocModel.getInsertSynLogModel(objectNos, batchSeq, userAccount), conn);//将写入日志放在调用SOA服务之后

                    if (srv1.getReturnMessage().getErrorFlag().equalsIgnoreCase("Y")) { //导入值集成功
                        //导入地点信息
                        ImportAssetLocCombSrv srv2 = new ImportAssetLocCombSrv();
                        srv2.setSrvInputItems(inputItems);
                        srv2.excute();
                        if (srv2.getReturnMessage().getErrorFlag().equalsIgnoreCase("Y")) {
                            returnMessage = srv2.getReturnMessage();
                            updateBatchDTO.setBatchId(batchSeq);
                            updateBatchDTO.setOrganizationId(userAccount.getOrganizationId());
                            updateBatchDTO.setCreatedBy(userAccount.getUserId());
                            updateBatchDTO.setTransType("SYNLOC");
                            updateBatchDTO.setTransStatus(1);
                            updateBatchDTO.setRemark("本次共同步'地点组合信息'" + rs.getSize() + "条,全部同步成功!");
                            synLogUtil.updateMisUpdateBach(updateBatchDTO, conn);
                            DBOperator.updateBatchRecords(synLocModel.getUpdateSynLogModel(batchSeq, list), conn);

                            totalObjectNos.clear();//唐明胜增加
                        } else {
                            returnMessage = srv2.getReturnMessage();
                            updateBatchDTO.setBatchId(batchSeq);
                            updateBatchDTO.setOrganizationId(userAccount.getOrganizationId());
                            updateBatchDTO.setCreatedBy(userAccount.getUserId());
                            updateBatchDTO.setTransType("SYNLOC");
                            updateBatchDTO.setTransStatus(2);
                            updateBatchDTO.setErrmsg("同步失败，请点击行信息查看具体报错信息");
                            updateBatchDTO.setRemark("本次共同步'地点组合信息'" + rs.getSize() + "条,同步失败,失败原因："+returnMessage.getErrorMessage());
                            synLogUtil.updateMisUpdateBach(updateBatchDTO, conn);
                            List<ErrorItem> errorlist = srv2.getErrorItemList();
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
                        updateBatchDTO.setTransType("SYNLOC");
                        updateBatchDTO.setTransStatus(2);
                        updateBatchDTO.setErrmsg("同步失败，请点击行信息查看具体报错信息");
                        updateBatchDTO.setRemark("本次共同步'地点组合信息'" + rs.getSize() + "条,导入地点对应的值集失败,失败原因："+returnMessage.getErrorMessage());
                        synLogUtil.updateMisUpdateBach(updateBatchDTO, conn);
                        List<com.sino.soa.mis.eip.sy.sy.sb_sy_sy_importvsetvalueinfosrv.ErrorItem> errorlist1 = srv1.getErrorItemList();
                        if (errorlist1.size() > 0) {
                            for (int i = 0; i < errorlist1.size(); i++) {
                                com.sino.soa.mis.eip.sy.sy.sb_sy_sy_importvsetvalueinfosrv.ErrorItem item1 = errorlist1.get(i);
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
                            totalObjectNos.clear();//唐明胜增加
                        }
                        //如果同步失败的记录数少于同步记录数，上述日志记录全部记录为失败，则再次查询时将查询出本已同步成功的记录，再次同步就会报错
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
    private void validateLoc(MisLocDTO locDTO) {
        String locName = locDTO.getName();
        int index = locName.indexOf(".");
        int lastIndex = locName.lastIndexOf(".");
        if (lastIndex > index && index > -1) {
            locDTO.setSegment1Desc(locName.substring(0, index));
            locDTO.setSegment2Desc(locName.substring(index + 1, lastIndex));
            locDTO.setSegment3Desc(locName.substring(lastIndex + 1));

            String code[] = StrUtil.splitStr(locDTO.getCode(), ".");     //代码组合
            locDTO.setSegment1(code[0]);
            locDTO.setSegment2(code[1]);
            locDTO.setSegment3(code[2]);
        }
    }

    public File getExportFile() throws DataTransException {
        SrvAssetLocCombModel modelProducer = (SrvAssetLocCombModel) sqlProducer;
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
