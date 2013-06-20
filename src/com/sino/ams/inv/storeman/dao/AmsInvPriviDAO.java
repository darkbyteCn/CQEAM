package com.sino.ams.inv.storeman.dao;

import java.sql.Connection;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.inv.storeman.dto.EamInvStoremanDTO;
import com.sino.ams.inv.storeman.model.AmsInvPriviModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.framework.dto.BaseUserDTO;

public class AmsInvPriviDAO extends AMSBaseDAO {

	AmsInvPriviModel amsInvPriviModel = null;
	
	/**
	 * 功能：默认仓管员表(EAM) EAM_INV_STOREMAN 数据访问层构造函数
	 * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EamInvStoremanDTO 本次操作的数据
	 * @param conn         Connection 数据库连接，由调用者传入。
	 */
	public AmsInvPriviDAO(SfUserDTO userAccount, EamInvStoremanDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		amsInvPriviModel = (AmsInvPriviModel)sqlProducer;
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount  BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		super.sqlProducer = new AmsInvPriviModel((SfUserDTO) userAccount, (EamInvStoremanDTO) dtoParameter);
	}

	/**
	 * 功能：给仓库赋权限(EAM)表“ AMS_INV_PRIVI”数据。
	 */
	public void createData() throws DataHandleException {
		super.createData();
	}
	
	public void createDatas(String code) throws DataHandleException {
		boolean operateResult = false;
		SQLModel sqlModel = null;
		sqlModel = amsInvPriviModel.getDatasCreateModel(code);
		if (sqlModel != null && !sqlModel.isEmpty()) {
			DBOperator.updateRecord(sqlModel, conn);
			operateResult = true;
		}
	}
}
