package com.sino.ams.newasset.dao;


import java.sql.Connection;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.dto.AmsMisDeptDTO;
import com.sino.ams.newasset.model.AmsMisDeptModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.QueryException;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: AmsHrDeptDAO</p>
 * <p>Description:程序自动生成服务程序“AmsHrDeptDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class AmsMisDeptDAO extends AMSBaseDAO {


	/**
	 * 功能：MIS部门(HR) AMS_MIS_DEPT 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsHrDeptDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public AmsMisDeptDAO(SfUserDTO userAccount, AmsMisDeptDTO dtoParameter,
						 Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		AmsMisDeptDTO dtoPara = (AmsMisDeptDTO) dtoParameter;
		super.sqlProducer = new AmsMisDeptModel((SfUserDTO) userAccount,
												dtoPara);
	}

	/**
	 * 功能：根据部门名称获取部门所有信息
	 * @return AmsMisDeptDTO
	 * @throws QueryException
	 */
	public AmsMisDeptDTO getDeptByName() throws QueryException {
		AmsMisDeptDTO dept = new AmsMisDeptDTO();
		AmsMisDeptModel modelProducer = (AmsMisDeptModel) sqlProducer;
		SQLModel sqlModel = modelProducer.getDeptByNameModel();
		SimpleQuery simp = new SimpleQuery(sqlModel, conn);
		simp.setDTOClassName(AmsMisDeptDTO.class.getName());
		simp.executeQuery();
		if (simp.hasResult()) {
			dept = (AmsMisDeptDTO) simp.getFirstDTO();
		}
		return dept;
	}

	/**
	 * 功能：获取本OU组织下的所有部门
	 * @return DTOSet
	 * @throws QueryException
	 */
	public DTOSet getAllMisDept() throws QueryException {
		AmsMisDeptModel modelProducer = (AmsMisDeptModel) sqlProducer;
		SQLModel sqlModel = modelProducer.getAllMisDeptModel();
		SimpleQuery simp = new SimpleQuery(sqlModel, conn);
		simp.setDTOClassName(AmsMisDeptDTO.class.getName());
		simp.executeQuery();
		return simp.getDTOSet();
	}
}
