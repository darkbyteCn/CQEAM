package com.sino.ams.system.user.dao;


import java.sql.Connection;

import com.sino.ams.system.user.dto.SfRoleDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.system.user.model.SfRoleModel;
import com.sino.base.dto.DTO;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;


/**
 * <p>Title: SfRoleDAO</p>
 * <p>Description:程序自动生成服务程序“SfRoleDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class SfRoleDAO extends BaseDAO {

	/**
	 * 功能：SF_ROLE 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter SfRoleDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public SfRoleDAO(SfUserDTO userAccount, SfRoleDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}
	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) {
		SfRoleDTO dtoPara = (SfRoleDTO)dtoParameter;
		super.sqlProducer = new SfRoleModel((SfUserDTO)userAccount, dtoPara);
	}
}
