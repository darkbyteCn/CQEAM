package com.sino.ams.inv.storeman.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.inv.storeman.dto.EamInvStoremanDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.framework.dto.BaseUserDTO;

public class AmsInvPriviModel extends AMSSQLProducer {

	/**
	 * 功能：仓库权限表(EAM) AMS_INV_PRIVI 数据库SQL构造层构造函数
	 * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
	 * @param eamInvStoremanDTO EamInvStoremanDTO 本次操作的数据
	 */
	public AmsInvPriviModel(BaseUserDTO userAccount, EamInvStoremanDTO eamInvStoremanDTO) {
		super(userAccount, eamInvStoremanDTO);
	}

	/**
	 * 功能：框架自动生成资产盘点记录 AMS_INV_PRIVI数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 */
	public SQLModel getDataCreateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EamInvStoremanDTO dto = (EamInvStoremanDTO) dtoParameter;
		String sqlStr = "INSERT INTO "
						+ " AMS_INV_PRIVI("
						+ " PRIVI_ID,"
						+ " USER_ID,"
						+ " INV_CODE,"
						+ " ACTION_CODE"
						+ ") VALUES ("
						+ " NEWID(), ?, ?, 'INV_IN')";

		sqlArgs.add(dto.getUserId());
		sqlArgs.add(dto.getWorkorderObjectNo());
//		sqlArgs.add(code);
		//sqlArgs.add(dto.getInvCode());
		//sqlArgs.add(dto.getActionCode());
		//sqlArgs.add(userAccount.getOrganizationId());

		//sqlArgs.add(AssetsDictConstant.SYN_STATUS_NO);
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		
		return sqlModel;
	}
	
	/**
	 * 功能：框架自动生成资产盘点记录 AMS_INV_PRIVI数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 */
	public SQLModel getDatasCreateModel(String code) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EamInvStoremanDTO dto = (EamInvStoremanDTO) dtoParameter;
		String sqlStr = "INSERT INTO "
						+ " AMS_INV_PRIVI("
						+ " PRIVI_ID,"
						+ " USER_ID,"
						+ " INV_CODE,"
						+ " ACTION_CODE"
						+ ") VALUES ("
						+ "  NEWID() , ?, ?, ?)";

		sqlArgs.add(dto.getUserId());
		sqlArgs.add(dto.getWorkorderObjectNo());
		sqlArgs.add(code);
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		
		return sqlModel;
	}
}
