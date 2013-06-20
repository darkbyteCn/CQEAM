package com.sino.td.newasset.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.dto.AmsAssetsChkLogDTO;
import com.sino.ams.newasset.dto.AmsAssetsReservedDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.flow.bean.FlowAction;
import com.sino.flow.constant.FlowConstant;
import com.sino.flow.dto.FlowDTO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.td.commom.TdURLDefineList;
import com.sino.td.newasset.dto.TdAssetsTransHeaderDTO;
import com.sino.td.newasset.dto.TdAssetsTransLineDTO;
import com.sino.td.newasset.model.TdOrderApproveModel;

/**
 * <p>Title: AmsAssetsTransHeaderDAO</p>
 * <p>Description:程序自动生成服务程序“AmsAssetsTransHeaderDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */


public class TdOrderApproveDAO extends AMSBaseDAO {

    /**
     * 功能：资产业务头表(EAM)--取代原表 AMS_ASSETS_TRANS_HEADER 数据访问层构造函数
     * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter TdAssetsTransHeaderDTO 本次操作的数据
     * @param conn Connection 数据库连接，由调用者传入。
     */
    public TdOrderApproveDAO(SfUserDTO userAccount, TdAssetsTransHeaderDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     * @param userAccount BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        TdAssetsTransHeaderDTO dto = (TdAssetsTransHeaderDTO)dtoParameter;
        super.sqlProducer = new TdOrderApproveModel((SfUserDTO) userAccount, dto);
    }

    /**
     * 功能：审批单据，含调拨单，报废单，处置单
     * @param flowDTO FlowDTO
     * @param orderLines 更改调拨单行的折旧费用账户(2008-12-01 17:37)
     * @return boolean
     */
    public boolean approveOrder(FlowDTO flowDTO, DTOSet orderLines, String[] barcodes) {
        boolean operateResult = false;
        boolean autoCommit = true;
        boolean needMsg = true;
        String flowCode = "";
        try {
            if (canApprove()) {
                autoCommit = conn.getAutoCommit();
                conn.setAutoCommit(false);
                TdAssetsTransHeaderDTO dto = (TdAssetsTransHeaderDTO)dtoParameter;
                flowCode = dto.getFlowCode();
                flowDTO.setProcName(dto.getProcdureName());
                flowDTO.setActivity(flowCode);
                flowDTO.setApplyId(dto.getTransId());
                flowDTO.setSessionUserId(userAccount.getUserId());
                flowDTO.setSessionUserName(userAccount.getUsername());
                flowDTO.setApplyNo(dto.getTransNo());
                FlowAction flowProcessor = new FlowAction(conn, flowDTO);
                boolean flow2End = flowProcessor.isFlowToEnd();
                dto.setFlow2End(flow2End);
                setDTOParameter(dto);
                if (flowCode.equals(FlowConstant.FLOW_CODE_NEXT)) {
                    if (flow2End) {
                    	if(dto.getTransType().equals(AssetsDictConstant.ASS_DIS) || dto.getTransType().equals(AssetsDictConstant.ASS_SUB) || dto.getTransType().equals(AssetsDictConstant.ASS_SELL) || dto.getTransType().equals(AssetsDictConstant.ASS_RENT) || dto.getTransType().equals(AssetsDictConstant.ASS_DONA)){
                    		dto.setTransStatus(AssetsDictConstant.COMPLETED);
                    	} else {
                    		dto.setTransStatus(AssetsDictConstant.APPROVED);
                    	}                        
                    } else {
                        dto.setTransStatus(AssetsDictConstant.IN_PROCESS);
                    }
                    flowProcessor.flow();
                } else {
                    dto.setTransStatus(AssetsDictConstant.REJECTED);
                    flowProcessor.reject2Begin();
                }
                setDTOParameter(dto);
                TdOrderApproveModel modelProducer = (TdOrderApproveModel)sqlProducer;
                SQLModel sqlModel = modelProducer.getOrderApproveModel();
                DBOperator.updateRecord(sqlModel, conn);

                String provinceCode = servletConfig.getProvinceCode();
                if(provinceCode.equals(AssetsDictConstant.PROVINCE_CODE_JIN) && flowCode.equals(FlowConstant.FLOW_CODE_NEXT)){//仅山西需要该功能。
                    if(dto.getTransferType().equals("BTW_COMP")){
                        TdAssetsTransLineDAO lineDAO = new TdAssetsTransLineDAO(userAccount, null, conn);
                        lineDAO.uodateAccount(orderLines);

                    }
                }
                if(provinceCode.equals(AssetsDictConstant.PROVINCE_CODE_SHAN) && flowCode.equals(FlowConstant.FLOW_CODE_NEXT)){//陕西功能
                    if(dto.getTransferType().equals("BTW_COMP")){
                        TdAssetsTransLineDAO lineDAO = new TdAssetsTransLineDAO(userAccount, null, conn);
//						lineDAO.uodateAccount(orderLines);
                        lineDAO.updateTransLine(orderLines);//更新调入地点和接收人
                    }
                }
                //陕西报废资产处理（省公司专业资产管理员审批时处理）
//                if (dto.getTransType().equals(AssetsDictConstant.ASS_DIS) &&
//                      provinceCode.equals(AssetsDictConstant.PROVINCE_CODE_SHAN) &&
//                      userAccount.getOrganizationId().equals("82") && dto.getServletConfig().getMtlAssetsMgr().equals("专业资产管理员")) {
                if (dto.getTransType().equals(AssetsDictConstant.ASS_DIS) && dto.getAttribute4().equals("MTL_ASSETS")) {
                    if (barcodes != null && barcodes.length != 0) {
                       updateTransLineRemark(barcodes);
                    }
                }
                //陕西报废资产处理（省公司资产会计审批时处理,第二步的操作处理）
//                if (dto.getTransType().equals(AssetsDictConstant.ASS_DIS) &&
//                      provinceCode.equals(AssetsDictConstant.PROVINCE_CODE_SHAN) &&
//                      userAccount.getOrganizationId().equals("82") && dto.getServletConfig().getProvAssetsMgr().equals("全省资产管理员")) {
                if (dto.getTransType().equals(AssetsDictConstant.ASS_DIS) &&  dto.getAttribute4().equals("PROV_ASSETS")) {
                    //特殊处理，先清除选中资产REMARK
                    if (barcodes != null && barcodes.length != 0) {
                        deleteTransLineRemark(barcodes);
                        updateTransLineRemark(barcodes);
                    }
                }
                if (flow2End && flowCode.equals(FlowConstant.FLOW_CODE_NEXT)) {
                    sqlModel = modelProducer.getLineStatusUpdateModel(); //更新资产单据行数据状态为已审批
                    DBOperator.updateRecord(sqlModel, conn);
                    if (dto.getTransType().equals(AssetsDictConstant.ASS_DIS)) { //报废
                        sqlModel = modelProducer.getAssetsDiscardModel();
                        DBOperator.updateRecord(sqlModel, conn);
                        deleteReserveAssets();
                        if(provinceCode.equals(AssetsDictConstant.PROVINCE_CODE_SX)){//山西省报废要同步
                            recordChkLog(AssetsDictConstant.ASS_DIS, AssetsDictConstant.STATUS_NO);
                        }
                    } else if (dto.getTransType().equals(AssetsDictConstant.ASS_CLR)) { //处置
                        sqlModel = modelProducer.getAssetsClearModel();
                        DBOperator.updateRecord(sqlModel, conn);
                        deleteReserveAssets();
                    } else if (dto.getTransType().equals(AssetsDictConstant.ASS_FREE)) { //闲置
                        sqlModel = modelProducer.getAssetsFreeModel();
                        DBOperator.updateRecord(sqlModel, conn);
                        deleteReserveAssets();
                    } else if (dto.getTransType().equals(AssetsDictConstant.ASS_SUB)) { //减值
                        sqlModel = modelProducer.getAssetsSubModel();
                        DBOperator.updateRecord(sqlModel, conn);
                        deleteReserveAssets();
                    } else if (dto.getTransType().equals(AssetsDictConstant.ASS_SELL)){
                    	sqlModel = modelProducer.getAssetsSellModel();
                        DBOperator.updateRecord(sqlModel, conn);
                        deleteReserveAssets();
                    } else if (dto.getTransType().equals(AssetsDictConstant.ASS_RENT)){
                    	sqlModel = modelProducer.getAssetsRentModel();
                        DBOperator.updateRecord(sqlModel, conn);
                        deleteReserveAssets();
                    } else if (dto.getTransType().equals(AssetsDictConstant.ASS_DONA)){
                    	sqlModel = modelProducer.getAssetsDonaModel();
                        DBOperator.updateRecord(sqlModel, conn);
                        deleteReserveAssets();
                    }
                }
                operateResult = true;
            } else {
                prodMessage(AssetsMessageKeys.APPROVE_INVALID);
                message.setIsError(!operateResult);
                needMsg = false;
            }
        } catch (DataHandleException ex) {
            ex.printLog();
        } catch (SQLException ex) {
            Logger.logError(ex);
        } catch (ContainerException ex) {
            ex.printLog();
        } catch (QueryException ex) {
            ex.printLog();
        } finally {
            try {
                if (!operateResult) {
                    conn.rollback();
                } else {
                    conn.commit();
                }
                conn.setAutoCommit(autoCommit);
                if (needMsg) {
                    processMessage(operateResult, flowCode);
                }
            } catch (SQLException ex1) {
                Logger.logError(ex1);
            }
        }
        return operateResult;
    }


    /**
     * 功能：构造单据审批消息提示
     * @param operateResult boolean
     * @param flowCode String
     */
    private void processMessage(boolean operateResult, String flowCode) {
        TdAssetsTransHeaderDTO dto = (TdAssetsTransHeaderDTO)dtoParameter;
        if (flowCode.equals(FlowConstant.FLOW_CODE_NEXT)) {
            if (operateResult) {
                prodMessage(AssetsMessageKeys.PASS_ORDER_SUCCESS);
            } else {
                prodMessage(AssetsMessageKeys.PASS_ORDER_FAILURE);
            }
        } else {
            if (operateResult) {
                prodMessage(AssetsMessageKeys.REJECT_ORDER_SUCCESS);
            } else {
                prodMessage(AssetsMessageKeys.REJECT_ORDER_FAILURE);
            }
        }
        String orderType = dto.getTransTypeValue();
        if (orderType.indexOf("单") > -1) {
            orderType = orderType.substring(0, orderType.length() - 1);
        }
        message.addParameterValue(orderType);
        message.addParameterValue(dto.getTransNo());
        message.setIsError(!operateResult);
    }

    /**
     * 功能：删除本单据保留的资产：仅用于报废和处置单据
     * @throws DataHandleException
     */
    private void deleteReserveAssets() throws DataHandleException {
        AmsAssetsReservedDTO reserveDTO = new AmsAssetsReservedDTO();
        TdAssetsTransHeaderDTO dto = (TdAssetsTransHeaderDTO)dtoParameter;
        reserveDTO.setTransId(dto.getTransId());
        TdAssetsReservedDAO reserveDAO = new TdAssetsReservedDAO(userAccount,reserveDTO, conn);
        reserveDAO.DeleteByForeignKey("transId");
    }

    /**
     * 功能：判断该单据是否可以审批
     * @return boolean
     * @throws QueryException
     */
    private boolean canApprove() throws QueryException {
//		OrderApproveModel modelProducer = (OrderApproveModel)sqlProducer;
//		SQLModel sqlModel = modelProducer.getCanApproveModel();
//		SimpleQuery simp = new SimpleQuery(sqlModel, conn);
//		simp.executeQuery();
//		return simp.hasResult();
        return true;
    }

    /**
     * 功能：记录设备最新一次交易情况：例如报废，其他的视具体需要而定
     * 需要同步到MIS的时候调用该方法。
     * @param orderType String
     * @param isExist String
     * @throws DataHandleException
     */
    private void recordChkLog(String orderType, String isExist) throws DataHandleException {
        try {
            TdAssetsTransHeaderDTO dto = (TdAssetsTransHeaderDTO) dtoParameter;
            TdAssetsTransLineDTO line = new TdAssetsTransLineDTO();
            line.setTransId(dto.getTransId());
            TdAssetsTransLineDAO lineDAO = new TdAssetsTransLineDAO(userAccount, line, conn);
            lineDAO.setDTOClassName(TdAssetsTransLineDTO.class.getName());
            DTOSet dtos = (DTOSet) lineDAO.getDataByForeignKey("transId");
            if (dtos != null && !dtos.isEmpty()) {
                int lineCount = dtos.getSize();
                String orderUrl = "";
                AmsAssetsChkLogDTO chkLogDTO = null;
                TdAssetsChkLogDAO chkLogDAO = new TdAssetsChkLogDAO(userAccount, null, conn);
                for(int i = 0; i < lineCount; i++){
                    line = (TdAssetsTransLineDTO)dtos.getDTO(i);
                    chkLogDTO = new AmsAssetsChkLogDTO();
                    chkLogDTO.setBarcode(line.getBarcode());
                    chkLogDTO.setLastChkNo(line.getTransNo());
                    chkLogDTO.setHeaderId(line.getTransId());
                    chkLogDTO.setResponsibilityUser(line.getResponsibilityUser());
                    chkLogDTO.setResponsibilityDept(line.getDeptCode());
                    chkLogDTO.setAddressId(line.getAddressId());
                    chkLogDTO.setOrganizationId(userAccount.getOrganizationId());
                    chkLogDTO.setCreatedBy(userAccount.getUserId());
                    chkLogDTO.setOrderType(orderType);
                    chkLogDTO.setIsExist(isExist);
                    orderUrl = TdURLDefineList.ASSETS_TRANS_SERVLET_TD;
                    orderUrl += "?act=" + AssetsActionConstant.DETAIL_ACTION;
                    orderUrl += "&transId=" + line.getTransId();
                    chkLogDTO.setOrderDtlUrl(orderUrl);
                    chkLogDAO.setDTOParameter(chkLogDTO);
                    chkLogDAO.saveCheckLogData();
                }
            }
        } catch (QueryException ex) {
            ex.printLog();
            throw new DataHandleException(ex);
        }
    }

    /**
     * 功能：根据主键获取资产调拨单据，并补充当前审批角色
     * @return Object
     * @throws QueryException
     */
    public Object getDataByPrimaryKey() throws QueryException {
        Object primaryKeyData = super.getDataByPrimaryKey();
        TdAssetsTransHeaderDTO data = (TdAssetsTransHeaderDTO) primaryKeyData;
        data.setCurrRoleName(getCurrRoleName());
        data.setServletConfig(servletConfig);
        primaryKeyData = data;
        return primaryKeyData;
    }

    /**
     * 功能：获取当前的审批角色名称
     * @return String
     * @throws QueryException
     */
    public String getCurrRoleName() throws QueryException {
        String currRoleName = "";
        try {
        	TdOrderApproveModel modelProducer = (TdOrderApproveModel) sqlProducer;
            SQLModel sqlModel = modelProducer.getCurrApproveRoleModel();
            SimpleQuery simp = new SimpleQuery(sqlModel, conn);
            simp.executeQuery();
            if (simp.hasResult()) {
                currRoleName = simp.getFirstRow().getStrValue("ROLENAME");
            }
        } catch (ContainerException ex) {
            ex.printLog();
            throw new QueryException(ex);
        }
        return currRoleName;
    }

    /**
     * 功能：获取附件张数
     * @return String
     * @throws QueryException
     */
    public String getAccessSheet() throws QueryException {
        String accessSheet = "";
        try {
        	TdOrderApproveModel modelProducer = (TdOrderApproveModel) sqlProducer;
            SQLModel sqlModel = modelProducer.getAccessSheet();
            SimpleQuery simp = new SimpleQuery(sqlModel, conn);
            simp.executeQuery();
            if (simp.hasResult()) {
                accessSheet = simp.getFirstRow().getStrValue("ACCESS_SHEET");
            }
        } catch (ContainerException ex) {
            ex.printLog();
            throw new QueryException(ex);
        }
        return accessSheet;
    }

    /**
     * 功能：获取并更新不能报废的资产，并更新行表REMARK为“不允许报废”；
     */
    private void updateTransLineRemark(String[] barcodes) throws DataHandleException {
    	TdOrderApproveModel modelProducer = (TdOrderApproveModel) sqlProducer;
        String aggBarcodes = initBarcodes(barcodes);
//        SQLModel sqlModel = modelProducer.updateTransLineRemark(aggBarcodes);
        SQLModel sqlModel = modelProducer.updateTransLineRemark();
        DBOperator.updateRecord(sqlModel, conn);
    }
    //初始化获取的BARCODE
    private String initBarcodes (String[] barcodes) {
        String aggBarcodes = "(";
        for (int i = 0; i < barcodes.length; i++) {
             String barcode = barcodes[i];
             aggBarcodes += "'" + barcode + "', ";
        }
        aggBarcodes += "'aa')";
//        int cc = aggBarcodes.lastIndexOf(",");
//        aggBarcodes = aggBarcodes.substring(0,cc)+")";
        return aggBarcodes;
    }

    /**
     * 功能：特殊处理，先清除选中资产REMARK
     */
    private void deleteTransLineRemark(String[] barcodes) throws DataHandleException {
    	TdOrderApproveModel modelProducer = (TdOrderApproveModel) sqlProducer;
        for (int i = 0; i < barcodes.length; i++) {
            String barcode = barcodes[i];
            SQLModel sqlModel = modelProducer.deleteTransLineRemark(barcode);
            DBOperator.updateRecord(sqlModel, conn);
        }
    }
}
