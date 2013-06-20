package com.sino.ams.expand.model;


import java.util.*;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.*;
import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.expand.dto.EtsItemInfoExQueRenDTO;


/**
 * <p>Title: EtsItemInfoExQueRenModel</p>
 * <p>Description:程序自动生成SQL构造器“EtsItemInfoExQueRenModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Administrator
 * @version 1.0
 */


public class EtsItemInfoExQueRenModel extends AMSSQLProducer {


	/**
	 * 功能：资产拓展信息表－ETS_ITEM_INFO_EX ETS_ITEM_INFO_EX 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EtsItemInfoExQueRenDTO 本次操作的数据
	 */
	public EtsItemInfoExQueRenModel(SfUserDTO userAccount, EtsItemInfoExQueRenDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：框架自动生成资产拓展信息表－ETS_ITEM_INFO_EX ETS_ITEM_INFO_EX数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataCreateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsItemInfoExQueRenDTO dto = (EtsItemInfoExQueRenDTO)dtoParameter;
		String sqlStr = "INSERT INTO "
			+ " ETS_ITEM_INFO_EX("
			+ " ITEM_INFO_EX_ID,"
			+ " SYSTEM_ID,"
			+ " ITEM_TYPE,"
			+ " ATTRIBUTE1,"
			+ " ATTRIBUTE2,"
			+ " ATTRIBUTE3,"
			+ " ATTRIBUTE4,"
			+ " ATTRIBUTE5,"
			
			+ " CREATED_BY,"
			+ " CREATION_DATE"
			+ ") VALUES ("
			+ " NEWID(), ?, ?, ?, ?, ?, ?, ?, ?, GETDATE())";
	
		sqlArgs.add(dto.getSystemid());
		sqlArgs.add(dto.getItemType());
		sqlArgs.add(dto.getAttribute1());
		sqlArgs.add(dto.getAttribute2());
		sqlArgs.add(dto.getAttribute3());
		sqlArgs.add(dto.getAttribute4());
		sqlArgs.add(dto.getAttribute5());
		sqlArgs.add(userAccount.getUserId());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成资产拓展信息表－ETS_ITEM_INFO_EX ETS_ITEM_INFO_EX数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsItemInfoExQueRenDTO dto = (EtsItemInfoExQueRenDTO)dtoParameter;
		String sqlStr = "SELECT "
			+ " EII.SYSTEMID,"
			+ " EII.BARCODE,"
			+ " ESI.ITEM_NAME,"
			+ " ESI.ITEM_SPEC,"
			+ " EII.RESPONSIBILITY_DEPT,"
			+ " dbo.APP_GET_DEPT_NAME(EII.RESPONSIBILITY_DEPT) DEPT_NAME,"
			+ " EII.RESPONSIBILITY_USER,"
			+ " dbo.EAM_MAINTAIN_GET_EMPLOYEE_NAME(EII.RESPONSIBILITY_USER) EMPLOYEE_NAME,"
			+ " EII.FINANCE_PROP,"
			+ " dbo.APP_GET_FLEX_VALUE(EII.FINANCE_PROP,'FINANCE_PROP') FINANCE_PROP_NAME,"
			+ " EFA.DATE_PLACED_IN_SERVICE"
			+ " FROM"
			+ " ETS_ITEM_INFO    EII,"
			+ " ETS_SYSTEM_ITEM  ESI,"
			+ " ETS_ITEM_MATCH   EIM,"
			+ " ETS_FA_ASSETS    EFA"
			+ " WHERE"
			+ " EII.ITEM_CODE = ESI.ITEM_CODE"
			+ " AND EII.SYSTEMID = EIM.SYSTEMID"
			+ " AND EIM.ASSET_ID = EFA.ASSET_ID"
			+ " AND EII.FINANCE_PROP<>'DZYH'"
			+ " AND EII.FINANCE_PROP<>'YQYB'"
			+ " AND EII.SYSTEMID = ?";
		sqlArgs.add(dto.getSystemid());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成资产拓展信息表－ETS_ITEM_INFO_EX ETS_ITEM_INFO_EX页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsItemInfoExQueRenDTO dto = (EtsItemInfoExQueRenDTO)dtoParameter;
		String sqlStr = "SELECT "
			+ " EII.SYSTEMID,"
			+ " EII.BARCODE,"
			+ " ESI.ITEM_NAME,"
			+ " ESI.ITEM_SPEC,"
			+ " EII.RESPONSIBILITY_DEPT,"
			+ " dbo.APP_GET_DEPT_NAME(EII.RESPONSIBILITY_DEPT) DEPT_NAME,"
			+ " EII.RESPONSIBILITY_USER,"
			+ " dbo.EAM_MAINTAIN_GET_EMPLOYEE_NAME(EII.RESPONSIBILITY_USER) EMPLOYEE_NAME,"
			+ " EII.FINANCE_PROP,"
			+ " dbo.APP_GET_FLEX_VALUE(EII.FINANCE_PROP,'FINANCE_PROP') FINANCE_PROP_NAME,"
			+ " EFA.DATE_PLACED_IN_SERVICE"
			+ " FROM"
			+ " ETS_ITEM_INFO    EII,"
			+ " ETS_SYSTEM_ITEM  ESI,"
			+ " ETS_ITEM_MATCH   EIM,"
			+ " ETS_FA_ASSETS    EFA"
			+ " WHERE"
			+ " EII.ITEM_CODE = ESI.ITEM_CODE"
			+ " AND EII.SYSTEMID = EIM.SYSTEMID"
			+ " AND EIM.ASSET_ID = EFA.ASSET_ID"
			+ " AND EII.ORGANIZATION_ID=?"
			+ " AND EII.FINANCE_PROP NOT IN('DZYH','YQYB')"
			+ " AND NOT EXISTS("
			+ " SELECT"
			+ " 'A'"
			+ " FROM"
			+ " ETS_ITEM_INFO_EX EIIE"
			+ " WHERE"
			+ " EIIE.SYSTEM_ID = EII.SYSTEMID)"
			+ " AND (LTRIM(?) IS NULL OR EII.BARCODE LIKE '%' || ? || '%')"
			+ " AND (LTRIM(?) IS NULL OR ESI.ITEM_NAME LIKE '%' || ? || '%')"
			+ " AND (LTRIM(?) IS NULL OR ESI.ITEM_SPEC LIKE '%' || ? || '%')"
			+ " AND (LTRIM(?) IS NULL OR EII.RESPONSIBILITY_DEPT LIKE '%' || ? || '%')"
			+ " AND (LTRIM(?) IS NULL OR EII.RESPONSIBILITY_USER LIKE '%' || ? || '%')"
			+ " AND EII.FINANCE_PROP = dbo.NVL(LTRIM(?), EII.FINANCE_PROP)";

		sqlArgs.add(userAccount.getOrganizationId());
		
		sqlArgs.add(dto.getBarcode());
		sqlArgs.add(dto.getBarcode());
		sqlArgs.add(dto.getItemName());
		sqlArgs.add(dto.getItemName());
		sqlArgs.add(dto.getItemSpec());
		sqlArgs.add(dto.getItemSpec());
		sqlArgs.add(dto.getResponsibilityDept());
		sqlArgs.add(dto.getResponsibilityDept());
		
		System.out.println("\n================================"+dto.getResponsibilityDept());
		System.out.println("\n================================"+dto.getResponsibilityUser());
		
		sqlArgs.add(dto.getResponsibilityUser());
		sqlArgs.add(dto.getResponsibilityUser());
		sqlArgs.add(dto.getFinanceProp());
	
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
	
		return sqlModel;
	}

}