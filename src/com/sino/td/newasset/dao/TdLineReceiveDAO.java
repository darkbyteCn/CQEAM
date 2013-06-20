package com.sino.td.newasset.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.sms.bean.MessageSaver;
import com.sino.sms.constant.SMSConstant;
import com.sino.sms.dto.SfMsgDefineDTO;
import com.sino.td.newasset.dto.TdAssetsTransHeaderDTO;
import com.sino.td.newasset.dto.TdAssetsTransLineDTO;
import com.sino.td.newasset.model.TdRcvLineModel;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class TdLineReceiveDAO extends AMSBaseDAO {
	private TdAssetsTransHeaderDTO headerDTO = null;

	/**
	 * 功能：资产业务头表(EAM) AMS_ASSETS_TRANS_HEADER 数据访问层构造函数
	 * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter TdAssetsTransLineDTO 本次操作的数据
	 * @param conn         Connection 数据库连接，由调用者传入。
	 */
	public TdLineReceiveDAO(SfUserDTO userAccount, TdAssetsTransLineDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount  BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		TdAssetsTransLineDTO dto = (TdAssetsTransLineDTO) dtoParameter;
		SfUserDTO user = (SfUserDTO) userAccount;
		sqlProducer = new TdRcvLineModel(user, dto);
	}

	public void setOrderHeader(TdAssetsTransHeaderDTO headerDTO) {
		this.headerDTO = headerDTO;
	}

	/**
	 * 功能：接收分配资产行
	 * @param assetLines DTOSet
	 * @throws DataHandleException
	 */
	public void rcvAssetLines(DTOSet assetLines) throws DataHandleException {
		TdRcvLineModel modelProducer = new TdRcvLineModel(userAccount, null);
		SQLModel sqlModel = null;
		for (int i = 0; i < assetLines.getSize(); i++) { //分配资产
			TdAssetsTransLineDTO dto = (TdAssetsTransLineDTO) assetLines.
										getDTO(i);
			dto.setTransId(headerDTO.getTransId());
			modelProducer.setDTOParameter(dto);
			sqlModel = modelProducer.getAssetsAssignModel(); //更新调拨行
			DBOperator.updateRecord(sqlModel, conn);
		}
		try {
			assetLines = assetLines.getSubDTOSet("responsibilityUser");
			for (int i = 0; i < assetLines.getSize(); i++) { //分配资产
				try {
					TdAssetsTransLineDTO dto = (TdAssetsTransLineDTO)
												assetLines.getDTO(i);
					saveMessage(dto);
				} catch (DataHandleException ex) { //短信存储失败不抛出异常，继续接收下一条资产
					ex.printLog();
				}
			}
		} catch (DTOException ex1) {
			ex1.printLog();
			throw new DataHandleException(ex1);
		}
	}

	/**
	 * 功能：保存资产接收短信息内容
	 * @param dto TdAssetsTransLineDTO
	 * @throws DataHandleException
	 */
	private void saveMessage(TdAssetsTransLineDTO dto) throws
			DataHandleException {
		try {
			MessageSaver msgSaver = new MessageSaver(conn);
			SfMsgDefineDTO msgDefineDTO = new SfMsgDefineDTO();
			SQLModel sqlModel = new SQLModel();
			List strArg = new ArrayList();
			String strSql = "SELECT"
							+ " SU.MOVETEL"
							+ " FROM SF_USER     SU,"
							+ " AMS_MIS_EMPLOYEE AME"
							+ " WHERE"
							+ " SU.EMPLOYEE_NUMBER = AME.EMPLOYEE_NUMBER"
							+ " AND AME.EMPLOYEE_ID = ?";
			strArg.add(dto.getResponsibilityUser());
			sqlModel.setSqlStr(strSql);
			sqlModel.setArgs(strArg);
			SimpleQuery sq = new SimpleQuery(sqlModel, conn);
			sq.executeQuery();
			if (sq.hasResult()) {
				String userTel = String.valueOf(sq.getFirstRow().getValue(
						"MOVETEL"));
				msgDefineDTO.setMsgCategoryId(SMSConstant.ASSET_CONFIRM_ID);
				msgDefineDTO.setCreatedBy(userAccount.getUserId());
				msgDefineDTO.setCellPhone(userTel);
				msgDefineDTO.setApplyNumber(dto.getLineId());
				msgDefineDTO.setMsgContent(dto.getResponsibilityUserName() +
										   "您有新的资产需要确认。");
				msgSaver.saveMsg(msgDefineDTO);
			}
		} catch (ContainerException ex) {
			ex.printLog();
			throw new DataHandleException(ex);
		} catch (QueryException ex) {
			ex.printLog();
			throw new DataHandleException(ex);
		}
	}
}
