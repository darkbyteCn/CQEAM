package com.sino.ams.dzyh.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.dzyh.dto.EamDhBillHDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;


/**
 * <p>Title: EamDhBillHModel</p>
 * <p>Description:程序自动生成SQL构造器“EamDhBillHModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 张星
 * @version 1.0
 */


public class EamDhBillHModel extends AMSSQLProducer {

	/**
	 * 功能：表结构定义-H(EAM) EAM_DH_BILL_H 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EamDhBillHDTO 本次操作的数据
	 */
	public EamDhBillHModel(SfUserDTO userAccount, EamDhBillHDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：框架自动生成表结构定义-H(EAM) EAM_DH_BILL_H数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataCreateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EamDhBillHDTO eamDhBillH = (EamDhBillHDTO)dtoParameter;
		String sqlStr = "INSERT INTO "
			+ " EAM_DH_BILL_H("
			+ " BILL_HEADER_ID,"
			+ " BILL_NO,"
			+ " BILL_STATUS,"
			+ " ORG_ID,"
			+ " CREATED_BY,"
			+ " CREATION_DATE,"
			+ " LAST_UPDATE_BY,"
			+ " LAST_UPDATE_DATE"
			+ ") VALUES ("
			+ "  NEWID() , ?, ?, ?, ?, GETDATE(), ?, GETDATE())";
	
		sqlArgs.add(eamDhBillH.getBillNo());
		sqlArgs.add(eamDhBillH.getBillStatus());
		sqlArgs.add(eamDhBillH.getOrgId());
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(userAccount.getUserId());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成表结构定义-H(EAM) EAM_DH_BILL_H数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据更新用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataUpdateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			EamDhBillHDTO eamDhBillH = (EamDhBillHDTO)dtoParameter;
			String sqlStr = "UPDATE EAM_DH_BILL_H"
				+ " SET"
				+ " BILL_NO = ?,"
				+ " BILL_STATUS = ?,"
				+ " ORG_ID = ?,"
				+ " CREATED_BY = ?,"
				+ " CREATION_DATE = ?,"
				+ " LAST_UPDATE_BY = ?,"
				+ " LAST_UPDATE_DATE = ?"
				+ " WHERE"
				+ " BILL_HEADER_ID = ?";
		
			sqlArgs.add(eamDhBillH.getBillNo());
			sqlArgs.add(eamDhBillH.getBillStatus());
			sqlArgs.add(eamDhBillH.getOrgId());
			sqlArgs.add(eamDhBillH.getCreatedBy());
			sqlArgs.add(eamDhBillH.getCreationDate());
			sqlArgs.add(eamDhBillH.getLastUpdateBy());
			sqlArgs.add(eamDhBillH.getLastUpdateDate());
			sqlArgs.add(eamDhBillH.getBillHeaderId());
		
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成表结构定义-H(EAM) EAM_DH_BILL_H数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EamDhBillHDTO eamDhBillH = (EamDhBillHDTO)dtoParameter;
		String sqlStr = "SELECT "
			+ " BILL_HEADER_ID,"
			+ " BILL_NO,"
			+ " BILL_STATUS,"
			+ " ORG_ID,"
			+ " CREATED_BY,"
			+ " CREATION_DATE,"
			+ " LAST_UPDATE_BY,"
			+ " LAST_UPDATE_DATE"
			+ " FROM"
			+ " EAM_DH_BILL_H"
			+ " WHERE"
			+ " BILL_HEADER_ID = ?";
		sqlArgs.add(eamDhBillH.getBillHeaderId());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成表结构定义-H(EAM) EAM_DH_BILL_H页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			EamDhBillHDTO eamDhBillH = (EamDhBillHDTO)dtoParameter;
			String sqlStr = "SELECT "
				+ " EDBH.BILL_HEADER_ID,"
				+ " EDBH.ORG_ID,"
				+ " EOCM.COMPANY EDBH_COMPANY,"
				+ " EDBH.BILL_NO,"
				+ " EDBH.CREATED_BY,"
				+ " SU.USERNAME EDBH_USERNAME,"
				+ " EDBH.CREATION_DATE,"
				+" CASE WHEN CONVERT(VARCHAR,EDBH.BILL_STATUS)='1' THEN '已完成' WHEN CONVERT(VARCHAR,EDBH.BILL_STATUS)='0'THEN '待处理' ELSE '不明' END BILL_STATUS "
				+ " FROM"
				+ " EAM_DH_BILL_H 	EDBH,"
				+ " SF_USER 		SU,"
				+ " ETS_OU_CITY_MAP EOCM"
				+ " WHERE"
				+ " EDBH.CREATED_BY=SU.USER_ID"
				+ " AND EDBH.ORG_ID=EOCM.ORGANIZATION_ID"
				+ " AND EDBH.ORG_ID = CONVERT(INT, dbo.NVL(?, CONVERT(VARCHAR, EDBH.ORG_ID)))"
				+ " AND EDBH.CREATION_DATE >= ISNULL(?, EDBH.CREATION_DATE)"
				+ " AND EDBH.CREATION_DATE <= ISNULL(?, EDBH.CREATION_DATE)"
				+ " AND EDBH.BILL_NO LIKE dbo.NVL(?, EDBH.BILL_NO)"
				+ " AND EDBH.BILL_STATUS = CONVERT(INT, dbo.NVL(?, CONVERT(VARCHAR, EDBH.BILL_STATUS)))";
			sqlArgs.add(eamDhBillH.getOrgId());
			sqlArgs.add(eamDhBillH.getStartDate());
			sqlArgs.add(eamDhBillH.getEndDate());
			sqlArgs.add("%" + eamDhBillH.getBillNo() + "%");
			sqlArgs.add(eamDhBillH.getBillStatus());
		
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

}