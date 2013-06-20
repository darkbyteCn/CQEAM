package com.sino.ams.newasset.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.dto.AmsAssetsRcvHeaderDTO;
import com.sino.ams.newasset.dto.AmsAssetsTransHeaderDTO;
import com.sino.ams.newasset.model.AssetsRcvHeaderModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.data.Row;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class AssetsReceiveDAO extends AMSBaseDAO {
	private String rcvHeaderId = "";

	/**
	 * 功能：资产业务头表(EAM) AMS_ASSETS_TRANS_HEADER 数据访问层构造函数
	 * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsAssetsTransHeaderDTO 本次操作的数据
	 * @param conn         Connection 数据库连接，由调用者传入。
	 */
	public AssetsReceiveDAO(SfUserDTO userAccount, AmsAssetsTransHeaderDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 *
	 * @param userAccount  BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
		sqlProducer = new AssetsRcvHeaderModel((SfUserDTO) userAccount, dto);
	}

	/**
	 * 功能：保存调拨的资产，含接收和分配
	 * @param dtos DTOSet
	 * @return boolean
	 */
	public boolean assignTransAssets(DTOSet dtos) {
		boolean operateResult = false;
		boolean autoCommit = true;
		AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) dtoParameter;
		boolean rcvProcEnabled = servletConfig.isRcvProcEnabled();
		String transferType = headerDTO.getTransferType();
		try {
			if (canAssignOrder()) {
				autoCommit = conn.getAutoCommit();
				conn.setAutoCommit(false);
				AssetsLineReceiveDAO rcvLineDAO = new AssetsLineReceiveDAO(userAccount, null, conn);
				rcvLineDAO.setOrderHeader(headerDTO);
				rcvLineDAO.rcvAssetLines(dtos);
				AssetsRcvHeaderModel modelProducer = (AssetsRcvHeaderModel) sqlProducer;
				SQLModel sqlModel = modelProducer.getAssetsAssignModel();
				DBOperator.updateRecord(sqlModel, conn); //接收分配资产，更改单据状态
				if (rcvProcEnabled & transferType.equals(AssetsDictConstant.TRANS_BTW_DEPT)) {
					//自动生成调拨接收单，用于启动调拨接收审批流程
					AmsAssetsRcvHeaderDAO rcvHeaderDAO = new AmsAssetsRcvHeaderDAO(userAccount, null, conn);
					rcvHeaderDAO.autoCreateRcvDatas(headerDTO, dtos);
					AmsAssetsRcvHeaderDTO rcvHeader = (AmsAssetsRcvHeaderDTO)rcvHeaderDAO.getDTOParameter();
					rcvHeaderId = rcvHeader.getReceiveHeaderId();
				}
				operateResult = true;
			} else {
				prodMessage(AssetsMessageKeys.RCV_INVALID);
			}
		} catch (DataHandleException ex) {
			ex.printLog();
		} catch (SQLException ex) {
			Logger.logError(ex);
		} catch (QueryException ex) {
			ex.printLog();
		} catch (Exception ex) {
			Logger.logError(ex);
		} finally {
			try {
				if (!operateResult) {
					conn.rollback();
					prodMessage(AssetsMessageKeys.RCV_ASSETS_FAILURE);
				} else {
					conn.commit();
					if (rcvProcEnabled) {
						if(transferType.equals(AssetsDictConstant.TRANS_BTW_COMP)){
							this.message.setMessageValue("跨地市资产调拨接收分配成功！");
						} else {
							prodMessage(AssetsMessageKeys.RCV_AUTO_CREATE);
						}
					} else {
						prodMessage(AssetsMessageKeys.RCV_ASSETS_SUCCESS);
					}
				}
				message.setIsError(!operateResult);
				conn.setAutoCommit(autoCommit);
			} catch (SQLException ex1) {
				Logger.logError(ex1);
				prodMessage(AssetsMessageKeys.ROLL_BACK_ERROR);
			}
		}
		return operateResult;
	}

	/**
	 * 功能：判断该单据是否能够接收
	 * @return boolean
	 * @throws QueryException
	 */
	private boolean canAssignOrder() throws QueryException {
		AssetsRcvHeaderModel modelProducer = (AssetsRcvHeaderModel) sqlProducer;
		SQLModel sqlModel = modelProducer.getCanAssignModel();
		SimpleQuery simp = new SimpleQuery(sqlModel, conn);
		simp.executeQuery();
		return!simp.hasResult();
	}

	/**
	 * 功能：判断当前用户是否能够接收指定单据
	 * @return boolean
	 * @throws QueryException
	 */
	public boolean canReceiveOrder() throws QueryException {
		boolean canReceive = true;
		try {
			AssetsRcvHeaderModel modelProducer = (AssetsRcvHeaderModel)
												 sqlProducer;
			SQLModel sqlModel = modelProducer.getCanReceiveModel();
			SimpleQuery simp = new SimpleQuery(sqlModel, conn);
			simp.executeQuery();
			if (simp.hasResult()) {
				Row row = simp.getFirstRow();
				canReceive = (Integer.parseInt(row.getStrValue("NOT_ASSIGNED_NUMBER")) > 0);
			}
		} catch (SQLModelException ex) {
			ex.printLog();
			throw new QueryException(ex);
		} catch (ContainerException ex) {
			ex.printLog();
			throw new QueryException(ex);
		} catch (NumberFormatException ex) {
			Logger.logError(ex);
			throw new QueryException(ex);
		}
		return canReceive;
	}

	/**
	 * 功能：获取本次接收时生成的调拨接收单头ID(在需要启动调拨接收审批流程的情况下才有此值)
	 * @return String
	 */
	public String getRcvHeaderId() {
		return rcvHeaderId;
	}
}
