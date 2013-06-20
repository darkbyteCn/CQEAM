package com.sino.ams.expand.dao;


import java.sql.Connection;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.expand.dto.EtsItemInfoExSearchDTO;
import com.sino.ams.expand.model.EtsItemInfoExModel;


/**
 * <p>Title: EtsItemInfoExSearchDAO</p>
 * <p>Description:程序自动生成服务程序“EtsItemInfoExSearchDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Administrator
 * @version 1.0
 */


public class EtsItemInfoExSearchDAO extends AMSBaseDAO {


	/**
	 * 功能：资产拓展信息表－ETS_ITEM_INFO_EX ETS_ITEM_INFO_EX 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EtsItemInfoExSearchDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public EtsItemInfoExSearchDAO(SfUserDTO userAccount, EtsItemInfoExSearchDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) { 
		EtsItemInfoExSearchDTO dtoPara = (EtsItemInfoExSearchDTO)dtoParameter;
		super.sqlProducer = new EtsItemInfoExModel((SfUserDTO)userAccount, dtoPara);
	}

}