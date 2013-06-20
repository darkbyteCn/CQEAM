package com.sino.ams.newasset.dao;
import java.sql.Connection;

import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.dto.AmsAssetsTransLineDTO;
import com.sino.ams.newasset.model.AmsItemTransLineModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 11-7-8
 * Time: 上午6:49
 * To change this template use File | Settings | File Templates.
 */
public class AmsItemAllocationLineDAO extends AMSBaseDAO {

	public AmsItemAllocationLineDAO(SfUserDTO userAccount, AmsAssetsTransLineDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		AmsAssetsTransLineDTO dtoPara = (AmsAssetsTransLineDTO) dtoParameter;
		sqlProducer = new AmsItemTransLineModel((SfUserDTO) userAccount,dtoPara);
	}

	/**
	 * 覆盖写入数据的方法
	 * @throws com.sino.base.exception.DataHandleException
	 */
	public void createData() throws DataHandleException {
		try {
			if (!hasReserved()) {
				super.createData();
			}
		} catch (QueryException ex) {
			ex.printLog();
			throw new DataHandleException(ex);
		}
	}

	/**
	 * 功能：判断该资产是否已经被保留
	 * @return boolean
	 * @throws QueryException
	 */
	public boolean hasReserved() throws QueryException {
		AmsItemTransLineModel modelProducer = (AmsItemTransLineModel)sqlProducer;
		SQLModel sqlModel = modelProducer.getHasReservedModel();
		SimpleQuery simp = new SimpleQuery(sqlModel, conn);
		simp.executeQuery();
		return simp.hasResult();
	}

	/**
	 * 功能：取消单据
	 * @throws DataHandleException
	 */
	public void cancelLinesByHeader() throws DataHandleException {
		AmsItemTransLineModel modelProducer = (AmsItemTransLineModel)sqlProducer;
		SQLModel sqlModel = modelProducer.getCancelLinesByHeaderModel();
		DBOperator.updateRecord(sqlModel, conn);
	}


	/**
	 * 功能：更新调拨单行折旧费用账户(2008-12-01 17：46)
	 * @param lines 调拨单行数据
	 * @throws DataHandleException
	 */
	public void uodateAccount(DTOSet lines) throws DataHandleException {
		int lineCount = lines.getSize();
		if(lineCount > 0){
			AmsItemTransLineModel modelProducer = (AmsItemTransLineModel) sqlProducer;
			SQLModel sqlModel = null;
			for (int i = 0; i < lineCount; i++) {
				AmsAssetsTransLineDTO dto = (AmsAssetsTransLineDTO) lines.getDTO(i);
				setDTOParameter(dto);
				sqlModel = modelProducer.getAccountUpdateModel();
				DBOperator.updateRecord(sqlModel, conn);
			}
		}
	}

    /**
	 * 功能：更新调拨单行折旧费用账户和资产类别(陕西公司间调拨在审批过程中需要用到该功能)
	 * @param lines 调拨单行数据
	 * @throws DataHandleException
	 */
	public void updateTransLine(DTOSet lines) throws DataHandleException {
		int lineCount = lines.getSize();
		if(lineCount > 0){
			AmsItemTransLineModel modelProducer = (AmsItemTransLineModel) sqlProducer;
			SQLModel sqlModel = null;
			for (int i = 0; i < lineCount; i++) {
				AmsAssetsTransLineDTO dto = (AmsAssetsTransLineDTO) lines.getDTO(i);
				setDTOParameter(dto);
				sqlModel = modelProducer.getTransLineUpdateModel();
				DBOperator.updateRecord(sqlModel, conn);
			}
		}
	}
}