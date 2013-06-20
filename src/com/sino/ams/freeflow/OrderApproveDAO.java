package com.sino.ams.freeflow;


import java.sql.Connection;
import java.sql.SQLException;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.dao.AmsAssetsChkLogDAO;
import com.sino.ams.newasset.dao.AmsAssetsReservedDAO;
import com.sino.ams.newasset.dao.AmsAssetsTransLineDAO;
import com.sino.ams.newasset.dto.AmsAssetsChkLogDTO;
import com.sino.ams.newasset.dto.AmsAssetsReservedDTO;
import com.sino.ams.newasset.dto.AmsAssetsTransLineDTO;
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

/**
 * <p>Title: AmsAssetsTransHeaderDAO</p>
 * <p>Description:程序自动生成服务程序“AmsAssetsTransHeaderDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author
 * jiaozhiwei
 */


public class OrderApproveDAO extends AMSBaseDAO {

	/**
	 * 功能：资产业务头表(EAM)--取代原表 AMS_ASSETS_TRANS_HEADER 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsAssetsTransHeaderDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public OrderApproveDAO(SfUserDTO userAccount, FreeFlowDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		 FreeFlowDTO ffDTO=(FreeFlowDTO) dtoParameter;
        super.sqlProducer = new OrderApproveModel((SfUserDTO)userAccount, ffDTO);
	}

	/**
	 * 功能：审批单据，含调拨单，报废单，处置单，共享单
	 * @param flowDTO FlowDTO
	 * @param orderLines 更改调拨单行的折旧费用账户(2008-12-01 17:37)
	 * @return boolean
	 */
	public boolean approveOrder(FlowDTO flowDTO, DTOSet orderLines) {
		boolean operateResult = false;
		boolean autoCommit = true;
		boolean needMsg = true;
		String flowCode = "";
		try {
			if (canApprove()) {
				autoCommit = conn.getAutoCommit();
				conn.setAutoCommit(false);
				FreeFlowDTO dto = (FreeFlowDTO)dtoParameter;
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
						dto.setTransStatus(AssetsDictConstant.COMPLETED);
					} else {
						dto.setTransStatus(AssetsDictConstant.IN_PROCESS);
					}
					flowProcessor.flow();
				} else {
					dto.setTransStatus(AssetsDictConstant.REJECTED);
					flowProcessor.reject2Begin();
				}
				setDTOParameter(dto);
				OrderApproveModel modelProducer = (OrderApproveModel)sqlProducer;
				SQLModel sqlModel = modelProducer.getOrderApproveModel();
				DBOperator.updateRecord(sqlModel, conn);

				String provinceCode = servletConfig.getProvinceCode();
				if(provinceCode.equals(AssetsDictConstant.PROVINCE_CODE_JIN) && flowCode.equals(FlowConstant.FLOW_CODE_NEXT)){//仅山西需要该功能。
					if(dto.getTransferType().equals("BTW_COMP")){
						AmsAssetsTransLineDAO lineDAO = new AmsAssetsTransLineDAO(userAccount, null, conn);
						lineDAO.uodateAccount(orderLines);
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
					} else if (dto.getTransType().equals(AssetsDictConstant.ASS_SHARE)){ //共享
						sqlModel = modelProducer.getAssetsShareModel();
						DBOperator.updateRecord(sqlModel, conn);
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
		FreeFlowDTO dto = (FreeFlowDTO)dtoParameter;
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
		FreeFlowDTO dto = (FreeFlowDTO)dtoParameter;
		reserveDTO.setTransId(dto.getTransId());
		AmsAssetsReservedDAO reserveDAO = new AmsAssetsReservedDAO(userAccount,reserveDTO, conn);
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
			FreeFlowDTO dto = (FreeFlowDTO) dtoParameter;
			AmsAssetsTransLineDTO line = new AmsAssetsTransLineDTO();
			line.setTransId(dto.getTransId());
			AmsAssetsTransLineDAO lineDAO = new AmsAssetsTransLineDAO(userAccount, line, conn);
			lineDAO.setDTOClassName(AmsAssetsTransLineDTO.class.getName());
			DTOSet dtos = (DTOSet) lineDAO.getDataByForeignKey("transId");
			if (dtos != null && !dtos.isEmpty()) {
				int lineCount = dtos.getSize();
				String orderUrl = "";
				AmsAssetsChkLogDTO chkLogDTO = null;
				AmsAssetsChkLogDAO chkLogDAO = new AmsAssetsChkLogDAO(userAccount, null, conn);
				for(int i = 0; i < lineCount; i++){
					line = (AmsAssetsTransLineDTO)dtos.getDTO(i);
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
					orderUrl = URLDefineList.FREE_FLOW_SERVLET;
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
            FreeFlowDTO data = (FreeFlowDTO) primaryKeyData;
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
                OrderApproveModel modelProducer = (OrderApproveModel) sqlProducer;
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
                OrderApproveModel modelProducer = (OrderApproveModel) sqlProducer;
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
           OrderApproveModel modelProducer = (OrderApproveModel) sqlProducer;
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
        OrderApproveModel modelProducer = (OrderApproveModel) sqlProducer;
        for (int i = 0; i < barcodes.length; i++) {
            String barcode = barcodes[i];
            SQLModel sqlModel = modelProducer.deleteTransLineRemark(barcode);
            DBOperator.updateRecord(sqlModel, conn);
        }
    }
    

}
