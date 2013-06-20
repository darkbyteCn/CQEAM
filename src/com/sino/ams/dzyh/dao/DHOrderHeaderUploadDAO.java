package com.sino.ams.dzyh.dao;

import java.sql.Connection;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.dzyh.constant.LvecDicts;
import com.sino.ams.dzyh.dto.EamDhCheckHeaderDTO;
import com.sino.ams.dzyh.dto.EamDhCheckLineDTO;
import com.sino.ams.dzyh.model.DHOrderUploadModel;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
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
public class DHOrderHeaderUploadDAO extends AMSBaseDAO {

	public DHOrderHeaderUploadDAO(BaseUserDTO userAccount, EamDhCheckHeaderDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器baseSQLProducer的初始化。具体的DAO继承时初始化具体的SQL生成器。
	 * @param userAccount BaseUserDTO
	 * @param dtoParameter DTO
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		EamDhCheckHeaderDTO dto = (EamDhCheckHeaderDTO)dtoParameter;
		sqlProducer = new DHOrderUploadModel(userAccount, dto);
	}

	/**
	 * 功能：归档工单头信息
	 * @throws DataHandleException
	 */
	public void uploadOrderHeader() throws DataHandleException {
		try {
			DHOrderUploadModel modelProducer = (DHOrderUploadModel) sqlProducer;
			EamDhCheckHeaderDTO dto = (EamDhCheckHeaderDTO) dtoParameter;
			dto.setOrderStatus(LvecDicts.CHK_STATUS_UPLOADED);
			if ( dto.getScanoverBy() > -1 ) {
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


	/**
	 * 功能：获取当前工单下的设备
	 * @param includePDA boolean
	 * @return DTOSet
	 * @throws QueryException
	 */
	public DTOSet getOrderBarcodes(boolean includePDA) throws QueryException {
		DHOrderUploadModel modelProducer = (DHOrderUploadModel) sqlProducer;
		SQLModel sqlModel = modelProducer.getOrderBarcodesModel(includePDA);
		SimpleQuery simp = new SimpleQuery(sqlModel, conn);
		simp.setDTOClassName(EamDhCheckLineDTO.class.getName());
		simp.executeQuery();
		return simp.getDTOSet();
	}
}
