package com.sino.soa.td.srv.ouorganization.model;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.soa.td.srv.ouorganization.dto.SBFIGLTdOuOrganizationDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-9-8
 * Time: 16:47:00
 * To change this template use File | Settings | File Templates.
 */
public class SBFIGLTdOuOrganizationModel extends BaseSQLProducer {

	private SfUserDTO sfUser = null;

	public SBFIGLTdOuOrganizationModel(SfUserDTO userAccount, SBFIGLTdOuOrganizationDTO dtoParameter) {
		super(userAccount, dtoParameter);
		sfUser = userAccount;
	}

	public SQLModel getDataCreateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SBFIGLTdOuOrganizationDTO srvOuOrganization = (SBFIGLTdOuOrganizationDTO)dtoParameter;
		String sqlStr = "INSERT INTO "
			+ " ETS_OU_CITY_MAP("
			+ " ORGANIZATION_ID,"
			+ " COMPANY,"
			+ " COMPANY_CODE,"
			+ " ENABLED,"
			+ " CREATION_DATE,"
			+ " CREATED_BY,"
			+ " LAST_UPDATE_DATE,"
			+ " LAST_UPDATE_BY,"
			+ " IS_TD,"
            + " MIS_ORGANIZATION_ID"
			+ ") VALUES ("
			+ " ?, ?, ?, ?, GETDATE(), ?, GETDATE(), ?, 'Y', ?)";

		sqlArgs.add(srvOuOrganization.getOrgId());
		sqlArgs.add(srvOuOrganization.getOrgName());
		sqlArgs.add(srvOuOrganization.getAttribute1());
		sqlArgs.add(srvOuOrganization.getEnableFlag());
		sqlArgs.add(sfUser.getUserId());
		sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(srvOuOrganization.getOrgId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	public SQLModel getDataUpdateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SBFIGLTdOuOrganizationDTO srvOuOrganization = (SBFIGLTdOuOrganizationDTO)dtoParameter;
		String sqlStr = "UPDATE "
			+ " ETS_OU_CITY_MAP SET"
			+ " ORGANIZATION_ID=?,"
            + " MIS_ORGANIZATION_ID=?,"
			+ " COMPANY=?,"
			+ " COMPANY_CODE=?,"
			+ " ENABLED=?,"
			+ " LAST_UPDATE_DATE=GETDATE(),"
			+ " LAST_UPDATE_BY=?"
			+ " WHERE ORGANIZATION_ID=?"
			;
		sqlArgs.add(srvOuOrganization.getOrgId());
		sqlArgs.add(srvOuOrganization.getOrgId());
		sqlArgs.add(srvOuOrganization.getOrgName());
		sqlArgs.add(srvOuOrganization.getAttribute1());
		sqlArgs.add(srvOuOrganization.getEnableFlag());
		sqlArgs.add(sfUser.getUserId());
		sqlArgs.add(srvOuOrganization.getOrgId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	public SQLModel getDataDeleteModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SBFIGLTdOuOrganizationDTO srvOuOrganization = (SBFIGLTdOuOrganizationDTO)dtoParameter;
		String sqlStr = "DELETE FROM"
				+ " SRV_OU_ORGANIZATION"	;
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SBFIGLTdOuOrganizationDTO srvOuOrganization = (SBFIGLTdOuOrganizationDTO)dtoParameter;
		String sqlStr = "SELECT "
			+ " ORG_ID,"
			+ " ORG_NAME,"
			+ " SET_OF_BOOKS_NAME,"
			+ " SET_OF_BOOKS_ID,"
			+ " ATTRIBUTE1,"
			+ " ENABLE_FLAG"
			+ " WHERE"
			+ " ROWNUM = 1";

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	public SQLModel getMuxDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SBFIGLTdOuOrganizationDTO srvOuOrganization = (SBFIGLTdOuOrganizationDTO)dtoParameter;
			String sqlStr = "SELECT "
			+ " ORG_ID,"
			+ " ORG_NAME,"
			+ " SET_OF_BOOKS_NAME,"
			+ " SET_OF_BOOKS_ID,"
			+ " ATTRIBUTE1,"
			+ " ENABLE_FLAG"
			+ " FROM"
			+ " SRV_OU_ORGANIZATION"
			+ " WHERE"
			+ " (? IS NULL OR ORG_ID LIKE ?)"
			+ " AND (? IS NULL OR ORG_NAME LIKE ?)"
			+ " AND (? IS NULL OR SET_OF_BOOKS_NAME LIKE ?)"
			+ " AND (? IS NULL OR SET_OF_BOOKS_ID LIKE ?)"
			+ " AND (? IS NULL OR ATTRIBUTE1 LIKE ?)"
			+ " AND (? IS NULL OR ENABLE_FLAG LIKE ?)";
		sqlArgs.add(srvOuOrganization.getOrgId());
		sqlArgs.add(srvOuOrganization.getOrgId());
		sqlArgs.add(srvOuOrganization.getOrgName());
		sqlArgs.add(srvOuOrganization.getOrgName());
		sqlArgs.add(srvOuOrganization.getSetOfBooksName());
		sqlArgs.add(srvOuOrganization.getSetOfBooksName());
		sqlArgs.add(srvOuOrganization.getSetOfBooksId());
		sqlArgs.add(srvOuOrganization.getSetOfBooksId());
		sqlArgs.add(srvOuOrganization.getAttribute1());
		sqlArgs.add(srvOuOrganization.getAttribute1());
		sqlArgs.add(srvOuOrganization.getEnableFlag());
		sqlArgs.add(srvOuOrganization.getEnableFlag());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SBFIGLTdOuOrganizationDTO srvOuOrganization = (SBFIGLTdOuOrganizationDTO)dtoParameter;
			String sqlStr = "SELECT "
			+ " ORG_ID,"
			+ " ORG_NAME,"
			+ " SET_OF_BOOKS_NAME,"
			+ " SET_OF_BOOKS_ID,"
			+ " ATTRIBUTE1,"
			+ " ENABLE_FLAG"
			+ " FROM"
			+ " SRV_OU_ORGANIZATION"
			+ " WHERE"
			+ " (? IS NULL OR ORG_ID LIKE ?)"
			+ " AND (? IS NULL OR ORG_NAME LIKE ?)"
			+ " AND (? IS NULL OR SET_OF_BOOKS_NAME LIKE ?)"
			+ " AND (? IS NULL OR SET_OF_BOOKS_ID LIKE ?)"
			+ " AND (? IS NULL OR ATTRIBUTE1 LIKE ?)"
			+ " AND (? IS NULL OR ENABLE_FLAG LIKE ?)";
		sqlArgs.add(srvOuOrganization.getOrgId());
		sqlArgs.add(srvOuOrganization.getOrgId());
		sqlArgs.add(srvOuOrganization.getOrgName());
		sqlArgs.add(srvOuOrganization.getOrgName());
		sqlArgs.add(srvOuOrganization.getSetOfBooksName());
		sqlArgs.add(srvOuOrganization.getSetOfBooksName());
		sqlArgs.add(srvOuOrganization.getSetOfBooksId());
		sqlArgs.add(srvOuOrganization.getSetOfBooksId());
		sqlArgs.add(srvOuOrganization.getAttribute1());
		sqlArgs.add(srvOuOrganization.getAttribute1());
		sqlArgs.add(srvOuOrganization.getEnableFlag());
		sqlArgs.add(srvOuOrganization.getEnableFlag());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	public SQLModel getEcouInforModel() {
		SQLModel sqlModel = new SQLModel();
		String sqlStr = "SELECT EOCM.ORGANIZATION_ID, EOCM.COMPANY_CODE FROM ETS_OU_CITY_MAP EOCM";
		sqlModel.setSqlStr(sqlStr);
		return sqlModel;
	}
}