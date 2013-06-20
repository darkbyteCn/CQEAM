package com.sino.ams.dzyh.dao;


import java.sql.Connection;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.dzyh.dto.EamDhPriviDTO;
import com.sino.ams.dzyh.model.EamDhPriviModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.dto.DTO;
import com.sino.base.exception.QueryException;
import com.sino.framework.dto.BaseUserDTO;


/**
 * <p>Title: EamDhPriviDAO</p>
 * <p>Description:程序自动生成服务程序“EamDhPriviDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 张星
 * @version 1.0
 */


public class EamDhPriviDAO extends AMSBaseDAO {


	/**
	 * 功能：权限定义表(EAM) EAM_DH_PRIVI 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EamDhPriviDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public EamDhPriviDAO(SfUserDTO userAccount, EamDhPriviDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) { 
		EamDhPriviDTO dtoPara = (EamDhPriviDTO)dtoParameter;
		super.sqlProducer = new EamDhPriviModel((SfUserDTO)userAccount, dtoPara);
	}
	public boolean doCheckDefaultflag(String defaultflag) throws QueryException
	{
		boolean success = false;
		EamDhPriviModel eamPriModel = (EamDhPriviModel)sqlProducer;
		com.sino.base.db.sql.model.SQLModel sqlModel = eamPriModel.getCheckDefaultflagModel(defaultflag);
		SimpleQuery sq = new SimpleQuery(sqlModel, conn);
		sq.executeQuery();
		success = sq.hasResult();
		return success;
	}
}