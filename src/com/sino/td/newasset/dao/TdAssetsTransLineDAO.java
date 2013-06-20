package com.sino.td.newasset.dao;

import java.sql.Connection;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.td.newasset.dto.TdAssetsTransLineDTO;
import com.sino.td.newasset.model.TdAssetsTransLineModel;

/**
 * <p>Title: AmsAssetsTransLineDAO</p>
 * <p>Description:程序自动生成服务程序“AmsAssetsTransLineDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */


public class TdAssetsTransLineDAO extends AMSBaseDAO {

    /**
	 * 功能：TD_ASSETS_TRANS_LINE 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter TdAssetsTransLineDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public TdAssetsTransLineDAO(SfUserDTO userAccount, TdAssetsTransLineDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		TdAssetsTransLineDTO dtoPara = (TdAssetsTransLineDTO) dtoParameter;
		sqlProducer = new TdAssetsTransLineModel((SfUserDTO) userAccount,dtoPara);
	}

	/**
	 * 覆盖写入数据的方法
	 * @throws DataHandleException
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
		TdAssetsTransLineModel modelProducer = (TdAssetsTransLineModel)sqlProducer;
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
		TdAssetsTransLineModel modelProducer = (TdAssetsTransLineModel)sqlProducer;
		SQLModel sqlModel = modelProducer.getHasReservedModel();
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
			TdAssetsTransLineModel modelProducer = (TdAssetsTransLineModel) sqlProducer;
			SQLModel sqlModel = null;
			for (int i = 0; i < lineCount; i++) {
				TdAssetsTransLineDTO dto = (TdAssetsTransLineDTO) lines.getDTO(i);
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
			TdAssetsTransLineModel modelProducer = (TdAssetsTransLineModel) sqlProducer;
			SQLModel sqlModel = null;
			for (int i = 0; i < lineCount; i++) {
				TdAssetsTransLineDTO dto = (TdAssetsTransLineDTO) lines.getDTO(i);
				setDTOParameter(dto);
				sqlModel = modelProducer.getTransLineUpdateModel();
				DBOperator.updateRecord(sqlModel, conn);
			}
		}
	}
}

