package com.sino.ams.dzyh.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.dzyh.dto.EamDhPriviDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;


/**
 * <p>Title: EamDhPriviModel</p>
 * <p>Description:程序自动生成SQL构造器“EamDhPriviModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 张星
 * @version 1.0
 */


public class EamDhPriviModel extends AMSSQLProducer {


	/**
	 * 功能：权限定义表(EAM) EAM_DH_PRIVI 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EamDhPriviDTO 本次操作的数据
	 */
	public EamDhPriviModel(SfUserDTO userAccount, EamDhPriviDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：框架自动生成权限定义表(EAM) EAM_DH_PRIVI数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataCreateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EamDhPriviDTO dto = (EamDhPriviDTO)dtoParameter;
		String sqlStr = "INSERT INTO "
			+ " EAM_DH_PRIVI("
			+ " PRIVI_ID,"
			+ " USER_ID,"
			+ " DEPT_CODE,"
			+ " ORG_ID,"
			+ " ENABLED,"
			+ " CREATED_BY,"
			+ " CREATION_DATE,"
			+ " DEFAULTFLAG"
			+ ") VALUES ("
			+ "  NEWID() , ?, ?, ?, ?, ?, GETDATE(), ?)";
	
		sqlArgs.add(dto.getUserId());
		sqlArgs.add(dto.getDeptCode());
		sqlArgs.add(dto.getOrgId());
		sqlArgs.add(dto.getEnabled());
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(dto.getDefaultflag());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成权限定义表(EAM) EAM_DH_PRIVI数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据更新用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataUpdateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			EamDhPriviDTO dto = (EamDhPriviDTO)dtoParameter;
			String sqlStr = "UPDATE EAM_DH_PRIVI"
				+ " SET"
				+ " USER_ID = ?,"
				+ " DEPT_CODE = ?,"
				+ " ORG_ID = ?,"
				+ " ENABLED = ?,"
				+ " CREATED_BY = ?,"
				+ " CREATION_DATE = ?,"
				+ " LAST_UPDATE_BY = ?,"
				+ " LAST_UPDATE_DATE = ?,"
				+ " DEFAULTFLAG = ?"
				+ " WHERE"
				+ " PRIVI_ID = ?";
		
			sqlArgs.add(dto.getUserId());
			sqlArgs.add(dto.getDeptCode());
			sqlArgs.add(dto.getOrgId());
			sqlArgs.add(dto.getEnabled());
			sqlArgs.add(dto.getCreatedBy());
			sqlArgs.add(dto.getCreationDate());
			sqlArgs.add(dto.getLastUpdateBy());
			sqlArgs.add(dto.getLastUpdateDate());
			sqlArgs.add(dto.getDefaultflag());
			sqlArgs.add(dto.getPriviId());
		
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成权限定义表(EAM) EAM_DH_PRIVI数据删除SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EamDhPriviDTO dto = (EamDhPriviDTO)dtoParameter;
		String sqlStr = "DELETE FROM"
				+ " EAM_DH_PRIVI"
				+ " WHERE"
				+ " PRIVI_ID = ?";
			sqlArgs.add(dto.getPriviId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成权限定义表(EAM) EAM_DH_PRIVI数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EamDhPriviDTO dto = (EamDhPriviDTO)dtoParameter;
		String sqlStr = "SELECT "
			+ " PRIVI_ID,"
			+ " USER_ID,"
			+ " DEPT_CODE,"
			+ " ORG_ID,"
			+ " ENABLED,"
			+ " CREATED_BY,"
			+ " CREATION_DATE,"
			+ " LAST_UPDATE_BY,"
			+ " LAST_UPDATE_DATE,"
			+ " DEFAULTFLAG"
			+ " FROM"
			+ " EAM_DH_PRIVI"
			+ " WHERE"
			+ " PRIVI_ID = ?";
		sqlArgs.add(dto.getPriviId());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成权限定义表(EAM) EAM_DH_PRIVI页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EamDhPriviDTO dto = (EamDhPriviDTO)dtoParameter;
		String sqlStr = "SELECT "
			+ " EDP.PRIVI_ID,"
			+ " AMS_PUB_PKG.GET_ORGNIZATION_NAME(EDP.ORG_ID) COMPANY_NAME,"
			+ " AMS_PUB_PKG.GET_DEPT_NAME(EDP.DEPT_CODE) DEPT_NAME,"
			+ " AMS_PUB_PKG.GET_USER_NAME(EDP.USER_ID) USER_NAME,"
			+ " CASE WHEN EDP.ENABLED='1' THEN '是' ELSE '否' END  ENABLED, "
			+ " EDP.CREATION_DATE,"
			+ " DEFAULTFLAG"
			+ " FROM"
			+ " EAM_DH_PRIVI EDP"
			+ " WHERE"
			+ " EDP.DEPT_CODE = dbo.NVL(?, EDP.DEPT_CODE)"
			+ " AND EDP.USER_ID = ISNULL(?, EDP.USER_ID)"
			+ " AND EDP.ORG_ID = CONVERT(INT, dbo.NVL(?, CONVERT(VARCHAR, EDP.ORG_ID)))"
			+ " ORDER BY EDP.CREATION_DATE DESC";
		sqlArgs.add(dto.getDeptCode());
		sqlArgs.add(dto.getUserId());
		sqlArgs.add(dto.getOrgId());
	
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		
		return sqlModel;
	}
	public SQLModel getCheckDefaultflagModel(String defaultflag)
	{
		SQLModel sqlModel = new SQLModel();
		List strArg = new ArrayList();
		String strSql = "SELECT 1 FROM EAM_DH_PRIVI EDP WHERE EDP.DEFAULTFLAG = ?";
		strArg.add(defaultflag);
		
		sqlModel.setSqlStr(strSql);
		sqlModel.setArgs(strArg);
		return sqlModel;
	}
}