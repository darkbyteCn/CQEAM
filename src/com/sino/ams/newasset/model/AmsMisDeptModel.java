package com.sino.ams.newasset.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.newasset.dto.AmsMisDeptDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;


/**
 * <p>Title: AmsMisDeptModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsHrDeptModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class AmsMisDeptModel extends AMSSQLProducer {

	/**
	 * 功能：MIS部门(HR) AMS_MIS_DEPT 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsMisDeptDTO 本次操作的数据
	 */
	public AmsMisDeptModel(SfUserDTO userAccount, AmsMisDeptDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	public SQLModel getDeptByNameModel() {
		SQLModel sqlModel = new SQLModel();
		AmsMisDeptDTO dto = (AmsMisDeptDTO) dtoParameter;
		String sqlStr = "SELECT"
						+ " *"
						+ " FROM"
						+ " AMS_MIS_DEPT AMD"
						+ " WHERE"
						+ " AMD.DEPT_NAME = ?"
						+ " AND AMD.COMPANY_CODE = ?";
		List sqlArgs = new ArrayList();
		sqlArgs.add(dto.getDeptName());
		sqlArgs.add(userAccount.getCompanyCode());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}


	/**
	 * 功能：返回数据详细信息的SQLModel
	 * @return SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		AmsMisDeptDTO dto = (AmsMisDeptDTO) dtoParameter;
		String sqlStr = "SELECT"
						+ " *"
						+ " FROM"
						+ " AMS_MIS_DEPT AMD"
						+ " WHERE"
						+ " AMD.DEPT_CODE = ?";
		List sqlArgs = new ArrayList();
		sqlArgs.add(dto.getDeptCode());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：构造获取本OU组织下的所有部门的SQL
	 * @return SQLModel
	 */
	public SQLModel getAllMisDeptModel(){
		SQLModel sqlModel = new SQLModel();
		String sqlStr = "SELECT"
						+ " *"
						+ " FROM"
						+ " AMS_MIS_DEPT AMD"
						+ " WHERE"
						+ " AMD.COMPANY_CODE = ?";
		List sqlArgs = new ArrayList();
		sqlArgs.add(userAccount.getCompanyCode());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
