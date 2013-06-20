package com.sino.ams.system.county.dao;


import java.sql.Connection;

import com.sino.ams.system.county.dto.EtsCountyDTO;
import com.sino.ams.system.county.model.sybase.EtsCountyModelImpl;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.dto.DTO;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;


/**
 * <p>Title: EtsCountyDAO</p>
 * <p>Description:程序自动生成服务程序“EtsCountyDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author zz_jlc
 * @version 1.0
 */


public class EtsCountyDAO extends BaseDAO {

	private SfUserDTO sfUser = null;

	/**
	 * 功能：区县表(EAM) ETS_COUNTY 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EtsCountyDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public EtsCountyDAO(SfUserDTO userAccount, EtsCountyDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		sfUser = userAccount;
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) { 
		EtsCountyDTO dtoPara = (EtsCountyDTO)dtoParameter;
		super.sqlProducer = new EtsCountyModelImpl((SfUserDTO)userAccount, dtoPara);
	}

}