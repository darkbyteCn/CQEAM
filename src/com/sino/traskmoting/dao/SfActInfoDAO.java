package com.sino.traskmoting.dao;


import java.sql.Connection;

import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.QueryException;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;
import com.sino.traskmoting.dto.SfActInfoDTO;
import com.sino.traskmoting.model.SfActInfoModel;


/**
 * <p>Title: SfActInfoDAO</p>
 * <p>Description:程序自动生成服务程序“SfActInfoDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Administrator
 * @version 1.0
 */


public class SfActInfoDAO extends BaseDAO {

	private SfUserBaseDTO sfUserBase = null;
    private SfActInfoModel sfActInfoModel=null;
	/**
	 * 功能：流转过程，在办流转信息 SF_ACT_INFO 数据访问层构造函数
	 * @param userAccount SfUserBaseDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter SfActInfoDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public SfActInfoDAO(SfUserBaseDTO userAccount, SfActInfoDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		sfUserBase = userAccount;
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) { 
		SfActInfoDTO dtoPara = (SfActInfoDTO)dtoParameter;
		super.sqlProducer = new SfActInfoModel((SfUserBaseDTO)userAccount, dtoPara);
	}

	//根据用户信息获取结果集
	public DTOSet getTraskUserMotingModel(Connection conn) throws QueryException {
		// TODO Auto-generated method stub
		   DTOSet dtoSet=new DTOSet();
		   SQLModel sqlModel = sfActInfoModel.getTraskUserMotingModel();
		   if(sqlModel==null){
			   
	       SimpleQuery simp = new SimpleQuery(sqlModel, conn);
				simp.setDTOClassName(SfActInfoDTO.class.getName());
				simp.executeQuery();
				dtoSet=simp.getDTOSet();
				}
			 
		return dtoSet;
	 
	}
	
	//根据角色信息获取结果集
	public DTOSet getTraskRoleMotingModel(Connection conn) throws QueryException {
		DTOSet dtoSet = new DTOSet();
		SQLModel sqlModel = sfActInfoModel.getTraskRoleMotingModel();
		if(sqlModel == null){
			SimpleQuery simp = new SimpleQuery(sqlModel, conn);
			simp.setDTOClassName(SfActInfoDTO.class.getName());
			simp.executeQuery();
			dtoSet = simp.getDTOSet();
		}
		return dtoSet;
	}
}