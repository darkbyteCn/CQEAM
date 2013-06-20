package com.sino.ams.sampling.dao;

import java.sql.Connection;

import com.sino.ams.sampling.constant.SamplingDicts;
import com.sino.ams.sampling.dto.AmsAssetsSamplingHeaderDTO;
import com.sino.ams.sampling.model.SamplingOrderUploadModel;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: SinoAMS</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class OrderHeaderUploadDAO extends AmsAssetsSamplingHeaderDAO {

	public OrderHeaderUploadDAO(BaseUserDTO userAccount, AmsAssetsSamplingHeaderDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器baseSQLProducer的初始化。具体的DAO继承时初始化具体的SQL生成器。
	 *
	 * @param userAccount BaseUserDTO
	 * @param dtoParameter DTO
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		AmsAssetsSamplingHeaderDTO dto = (AmsAssetsSamplingHeaderDTO)dtoParameter;
		sqlProducer = new SamplingOrderUploadModel(userAccount, dto);
	}


	/**
	 * 功能：归档工单头信息
	 * @throws DataHandleException
	 */
	public void uploadOrderHeader() throws DataHandleException {
		try {
			SamplingOrderUploadModel modelProducer = (SamplingOrderUploadModel) sqlProducer;
			AmsAssetsSamplingHeaderDTO dto = (AmsAssetsSamplingHeaderDTO) dtoParameter;
			dto.setOrderStatus(SamplingDicts.CHK_STATUS_UPLOADED);
			if (dto.getScanoverBy()==0) {
				dto.setScanoverBy(userAccount.getUserId());
				dto.setCurrCalendar("scanoverDate");
			}
			dto.setUploadBy(userAccount.getUserId());
			dto.setCurrCalendar("uploadDate");
			setDTOParameter(dto);
			SQLModel sqlModel = modelProducer.getOrderUploadModel(); //更新单据为已上载状态
			DBOperator.updateRecord(sqlModel, conn);
		} catch (SQLModelException ex) {
			ex.printLog();
			throw new DataHandleException(ex);
		}
	}
}
