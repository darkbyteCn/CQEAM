package com.sino.sinoflow.dao;


import java.sql.Connection;

import com.sino.base.dto.DTO;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.sinoflow.dto.SfHolidaysDTO;
import com.sino.sinoflow.model.SfHolidaysModel;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;


/**
 * <p>Title: SfHolidaysDAO</p>
 * <p>Description:程序自动生成服务程序“SfHolidaysDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Hing
 * @version 1.0
 */


public class SfHolidaysDAO extends BaseDAO {

	private SfUserBaseDTO sfUser = null;

	/**
	 * 功能：SF_HOLIDAYS 数据访问层构造函数
	 * @param userAccount SfUserBaseDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter SfHolidaysDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public SfHolidaysDAO(SfUserBaseDTO userAccount, SfHolidaysDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		sfUser = userAccount;
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) { 
		SfHolidaysDTO dtoPara = (SfHolidaysDTO)dtoParameter;
		super.sqlProducer = new SfHolidaysModel((SfUserBaseDTO)userAccount, dtoPara);
	}

}