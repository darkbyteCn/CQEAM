package com.sino.ams.dzyh.dao;


import java.sql.Connection;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.dzyh.dto.EtsItemInfoDTO;
import com.sino.ams.dzyh.model.DzyhFBTJItemInfoModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;


/**
 * <p>Title: DzyhFBTJItemInfoDAO</p>
 * <p>Description:程序自动生成服务程序“DzyhFBTJItemInfoDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 张星
 * @version 1.0
 */


public class DzyhFBTJItemInfoDAO extends AMSBaseDAO {

	/**
	 * 功能：标签号信息(EAM) ETS_ITEM_INFO 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EtsItemInfoDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public DzyhFBTJItemInfoDAO(SfUserDTO userAccount, EtsItemInfoDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) { 
		EtsItemInfoDTO dtoPara = (EtsItemInfoDTO)dtoParameter;
		super.sqlProducer = new DzyhFBTJItemInfoModel((SfUserDTO)userAccount, dtoPara);
	}

}