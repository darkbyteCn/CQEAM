package com.sino.ams.newasset.dao;

import java.sql.Connection;

import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.dto.AmsAssetsCheckHeaderDTO;
import com.sino.ams.newasset.model.ChkOrderUpLoadModel;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class ChkOrderHeaderUploadDAO extends AmsAssetsCheckHeaderDAO {

	public ChkOrderHeaderUploadDAO(BaseUserDTO userAccount, AmsAssetsCheckHeaderDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器baseSQLProducer的初始化。具体的DAO继承时初始化具体的SQL生成器。
	 * @param userAccount BaseUserDTO
	 * @param dtoParameter DTO
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO)dtoParameter;
		sqlProducer = new ChkOrderUpLoadModel(userAccount, dto);
	}

	/**
	 * 功能：更新单据头状态为已上载。
	 * @throws DataHandleException
	 */
	public void uploadOrderHeader() throws DataHandleException{
		try {
			AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
			ChkOrderUpLoadModel modelProducer = (ChkOrderUpLoadModel) sqlProducer;
			dto.setOrderStatus(AssetsDictConstant.CHK_STATUS_UPLOADED);
			dto.setScanoverBy(userAccount.getUserId());
			dto.setUploadBy(userAccount.getUserId());
			dto.setCurrCalendar("uploadDate");
			modelProducer.setDTOParameter(dto);
			SQLModel sqlModel = modelProducer.getUploadChkOrdersModel();
			DBOperator.updateRecord(sqlModel, conn);
		} catch (SQLModelException ex) {
			ex.printLog();
			throw new DataHandleException(ex);
		}
	}
}
