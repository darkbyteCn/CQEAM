package com.sino.ams.system.user.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.system.user.dto.EtsOuCityMapDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;


/**
 * <p>Title: EtsOuCityMapModel</p>
 * <p>Description:程序自动生成SQL构造器“EtsOuCityMapModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class EtsOuCityMapModel extends AMSSQLProducer {

	/**
	 * 功能：ETS_OU_CITY_MAP 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EtsOuCityMapDTO 本次操作的数据
	 */
	public EtsOuCityMapModel(SfUserDTO userAccount, EtsOuCityMapDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：框架自动生成数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel(){
		SQLModel sqlModel = new SQLModel();
		EtsOuCityMapDTO dto = (EtsOuCityMapDTO)dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT "
						+ " *"
						+ " FROM"
						+ " ETS_OU_CITY_MAP"
						+ " WHERE"
						+ " ORGANIZATION_ID = ?";
		sqlArgs.add(dto.getOrganizationId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}


	/**
	 * 功能：获取省公司OU本部名称SQL
	 * @return SQLModel
	 */
	public SQLModel getProOuNameModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
						+ " COMPANY"
						+ " FROM"
						+ " ETS_OU_CITY_MAP EOCM"
						+ " WHERE"
						+ " EOCM.ORGANIZATION_ID = ?";
		sqlArgs.add(servletConfig.getProvinceOrgId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	/**
	 * 判断是否TD用户的SQL
	 * @param orgId
	 * @return
	 */
	public SQLModel getOrganizationTdPropModel(int orgId){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
						+ " EOCM.IS_TD"
						+ " FROM"
						+ " ETS_OU_CITY_MAP EOCM"
						+ " WHERE"
						+ " EOCM.ORGANIZATION_ID = ?";
		sqlArgs.add(orgId);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	/**
	 * 获取OrgId对应的上市公司代码
	 * @return
	 */
	public SQLModel getCompanyCodeByOrgIdModel(int orgId){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
						+ " COMPANY_CODE"
						+ " FROM"
						+ " ETS_OU_CITY_MAP EOCM";
		if (userAccount.getIsTd().equals("N")) {
			sqlStr += " WHERE EOCM.MIS_ORGANIZATION_ID = ?";
		} else {
			sqlStr += " WHERE EOCM.MATCH_ORGANIZATION_ID = ?";
		}
		sqlArgs.add(orgId);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	/**
	 * 获取OrgId对应上市公司代码
	 * @param orgId
	 * @return
	 */
	public SQLModel getCompanyCodeModel(int orgId){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
						+ " RIGHT(BOOK_TYPE_CODE,4) COMPANY_CODE"
						+ " FROM"
						+ " ETS_OU_CITY_MAP"
						+ " WHERE 1=1 ";
		
		if(userAccount.getIsTt().equals("Y")) {
			sqlStr += " AND ORGANIZATION_ID= (SELECT MATCH_ORGANIZATION_ID FROM ETS_OU_CITY_MAP WHERE ORGANIZATION_ID= ? )";
		} else {
			if (userAccount.getIsTd().equals("N")) {
	    		sqlStr += " AND ORGANIZATION_ID = ?\n" ;
	    	} else {
	    		sqlStr += " AND MATCH_ORGANIZATION_ID = ?\n";
	    	}
		}
		
		sqlArgs.add(orgId);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
