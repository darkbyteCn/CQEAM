package com.sino.ams.system.user.dao;


import java.sql.Connection;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.system.user.dto.EtsOuCityMapDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.system.user.model.EtsOuCityMapModel;
import com.sino.base.data.Row;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.QueryException;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: EtsOuCityMapDAO</p>
 * <p>Description:程序自动生成服务程序“EtsOuCityMapDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class EtsOuCityMapDAO extends AMSBaseDAO {

	/**
	 * 功能：ETS_OU_CITY_MAP 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EtsOuCityMapDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public EtsOuCityMapDAO(SfUserDTO userAccount, EtsOuCityMapDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}
	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) {
		EtsOuCityMapDTO dtoPara = (EtsOuCityMapDTO)dtoParameter;
		super.sqlProducer = new EtsOuCityMapModel((SfUserDTO)userAccount, dtoPara);
	}

	/**
	 * 功能：获取省公司OU本部名称
	 * @return String
	 * @throws QueryException
	 */
	public String getProvOuName() throws QueryException {
		String companyName = "";
		try {
			EtsOuCityMapModel modelProducer = (EtsOuCityMapModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getProOuNameModel();
			SimpleQuery simp = new SimpleQuery(sqlModel, conn);
			simp.executeQuery();
			if (simp.hasResult()) {
				Row row = simp.getFirstRow();
				companyName = row.getStrValue("COMPANY");
			}
		} catch (ContainerException ex) {
			ex.printLog();
			throw new QueryException(ex);
		}
		return companyName;
	}
	
	/**
	 * 判断是否为TD用户
	 * @return
	 * @throws QueryException
	 */
	public boolean isTdOrganization(int orgId) throws QueryException {
		boolean isTd = false;
		try {
			EtsOuCityMapModel modelProducer = (EtsOuCityMapModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getOrganizationTdPropModel(orgId);
			SimpleQuery simp = new SimpleQuery(sqlModel, conn);
			simp.executeQuery();
			if (simp.hasResult()) {
				Row row = simp.getFirstRow();
				isTd = row.getStrValue("IS_TD").equals("Y");
			}
		} catch (ContainerException ex) {
			ex.printLog();
			throw new QueryException(ex);
		}
		return isTd;
	}
	
	/**
	 * 根据orgId获取公司代码
	 * @param orgId
	 * @return
	 * @throws QueryException
	 */
	public String getCompanyCodeByOrgId(int orgId) throws QueryException {
		String companyCode = "";
		try {
			EtsOuCityMapModel modelProducer = (EtsOuCityMapModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getCompanyCodeByOrgIdModel(orgId);
			SimpleQuery simp = new SimpleQuery(sqlModel, conn);
			simp.executeQuery();
			if (simp.hasResult()) {
				Row row = simp.getFirstRow();
				companyCode = row.getStrValue("COMPANY_CODE");
			}
		} catch (ContainerException ex) {
			ex.printLog();
			throw new QueryException(ex);
		}
		return companyCode;
	}
	
	/**
	 * 获取OrgId对应上市公司代码
	 * @param orgId
	 * @return
	 * @throws QueryException
	 */
	public String getCompanyCode(int orgId) throws QueryException {
		String companyCode = "";
		try {
			EtsOuCityMapModel modelProducer = (EtsOuCityMapModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getCompanyCodeModel(orgId);
			SimpleQuery simp = new SimpleQuery(sqlModel, conn);
			simp.executeQuery();
			if (simp.hasResult()) {
				Row row = simp.getFirstRow();
				companyCode = row.getStrValue("COMPANY_CODE");
			}
		} catch (ContainerException ex) {
			ex.printLog();
			throw new QueryException(ex);
		}
		return companyCode;
	}
}
