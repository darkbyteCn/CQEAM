package com.sino.sinoflow.dao;


import java.sql.Connection;

import com.sino.base.dto.DTO;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.sinoflow.dto.SfWorkHourDTO;
import com.sino.sinoflow.model.SfWorkHourModel;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;


/**
 * <p>Title: SfWorkHourDAO</p>
 * <p>Description:程序自动生成服务程序“SfWorkHourDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Hing
 * @version 1.0
 */


public class SfWorkHourDAO extends BaseDAO {

	private SfUserBaseDTO sfUser = null;

	/**
	 * 功能：工时定义 SF_WORK_HOUR 数据访问层构造函数
	 * @param userAccount SfUserBaseDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter SfWorkHourDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public SfWorkHourDAO(SfUserBaseDTO userAccount, SfWorkHourDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		sfUser = userAccount;
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) { 
		SfWorkHourDTO dtoPara = (SfWorkHourDTO)dtoParameter;
		super.sqlProducer = new SfWorkHourModel((SfUserBaseDTO)userAccount, dtoPara);
	}

}