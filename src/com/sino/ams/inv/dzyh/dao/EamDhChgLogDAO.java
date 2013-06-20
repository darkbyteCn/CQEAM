package com.sino.ams.inv.dzyh.dao;

import java.sql.Connection;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.inv.dzyh.dto.EamDhBillLDTO;
import com.sino.ams.inv.dzyh.model.EamDhChgLogModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.framework.dto.BaseUserDTO;

public class EamDhChgLogDAO extends AMSBaseDAO {

	EamDhChgLogModel eamDhChgLogModel = null;
	
	/**
	 * 功能：默认仓管员表(EAM) EAM_DH_CHG_LOG 数据访问层构造函数
	 * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EamDhBillLDTO 本次操作的数据
	 * @param conn         Connection 数据库连接，由调用者传入。
	 */
	public EamDhChgLogDAO(SfUserDTO userAccount, EamDhBillLDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		eamDhChgLogModel = (EamDhChgLogModel)sqlProducer;
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount  BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		super.sqlProducer = new EamDhChgLogModel((SfUserDTO) userAccount, (EamDhBillLDTO) dtoParameter);
	}

	/**
	 * 功能：记录变动历史(EAM)表“ EAM_DH_CHG_LOG”数据。
	 */
	public void createData(String barcode, String catalogValueId, String oldDept, String newDept, String oldAddress, String newAddress, String oldUser, String newUser, String oldMaintain, String newMaintain) throws DataHandleException {
		boolean operateResult = false;
		SQLModel sqlModel = null;
		sqlModel = eamDhChgLogModel.getDataCreateModel(barcode, catalogValueId, oldDept, newDept, oldAddress, newAddress, oldUser, newUser, oldMaintain, newMaintain);
		if (sqlModel != null && !sqlModel.isEmpty()) {
			DBOperator.updateRecord(sqlModel, conn);
			operateResult = true;
		}
	}
}
