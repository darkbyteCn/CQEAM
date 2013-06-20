package com.sino.ams.system.project.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.project.dto.EtsPaProjectsAllDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.DateException;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * <p>Title: EtsPaProjectsAllModel</p>
 * <p>Description:程序自动生成SQL构造器“EtsPaProjectsAllModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Zyun
 * @version 1.0
 */


public class EtsPaProjectsAllModel extends BaseSQLProducer {

	private SfUserDTO sfUser = null;

/**
	 * 功能：项目维护表(EAM) ETS_PA_PROJECTS_ALL 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EtsPaProjectsAllDTO 本次操作的数据
	 */
	public EtsPaProjectsAllModel(SfUserDTO userAccount, EtsPaProjectsAllDTO dtoParameter) {
		super(userAccount, dtoParameter);
		sfUser = userAccount;
	}

/**
	 * 功能：框架自动生成项目维护表(EAM) ETS_PA_PROJECTS_ALL数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 */
	public SQLModel getDataCreateModel() { //新增项目人员表
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsPaProjectsAllDTO etsPaProjectsAll = (EtsPaProjectsAllDTO) dtoParameter;

		String sqlStr = "INSERT INTO "
						+ " ETS_PA_PROJECTS_ALL("
						+ " PROJECT_ID,"
						+ " NAME,"
						+ " SEGMENT1,"
                        + " PROJECT_TYPE,"
						+ " PROJECT_STATUS_CODE,"
						+ " START_DATE,"
						+ " COMPLETION_DATE,"
                        + " ENABLED_FLAG,"
						+ " SOURCE,"
						+ " CREATION_DATE,"
						+ " CREATED_BY,"
						+ " MIS_PROJECT_ID"
						+ ") VALUES ("
						+ "NEWID() , ?, ?, ?, 'APPROVED', ?, ?, 'Y', 'AMS', GETDATE() , ?,?)";

		sqlArgs.add(etsPaProjectsAll.getName());
		sqlArgs.add(etsPaProjectsAll.getSegment1());
		sqlArgs.add(etsPaProjectsAll.getProjectType());
		sqlArgs.add(etsPaProjectsAll.getStartDate());
		sqlArgs.add(etsPaProjectsAll.getCompletionDate());
		sqlArgs.add(sfUser.getUserId());
		sqlArgs.add(etsPaProjectsAll.getMisProjectId());
		sqlModel.setSqlStr(  sqlStr);
		sqlModel.setArgs(sqlArgs);

		return sqlModel;
	}

/**
	 * 功能：框架自动生成项目维护表(EAM) ETS_PA_PROJECTS_ALL数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据更新用SQLModel
	 */
	public SQLModel getDataUpdateModel() {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			EtsPaProjectsAllDTO etsPaProjectsAll = (EtsPaProjectsAllDTO) dtoParameter;
			String sqlStr = "UPDATE ETS_PA_PROJECTS_ALL"
							+ " SET"
							+ " NAME = ?,"
							+ " SEGMENT1 = ?,"
							+ " PROJECT_TYPE = ?,"
							+ " START_DATE = ?,"
							+ " COMPLETION_DATE = ?,"
							+ " LAST_UPDATE_DATE = GETDATE(),"
							+ " LAST_UPDATE_BY = ?,"
							+ " MIS_PROJECT_ID = ?"
							+ " WHERE"
							+ " PROJECT_ID = ?";

			sqlArgs.add(etsPaProjectsAll.getName());
			sqlArgs.add(etsPaProjectsAll.getSegment1());
			sqlArgs.add(etsPaProjectsAll.getProjectType());
			sqlArgs.add(etsPaProjectsAll.getStartDate1());
			sqlArgs.add(etsPaProjectsAll.getCompletionDate1());
			sqlArgs.add(sfUser.getUserId());
			sqlArgs.add(etsPaProjectsAll.getMisProjectId());
			sqlArgs.add(etsPaProjectsAll.getProjectId());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (DateException e) {
			e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.
		}
		return sqlModel;
	}

/**
	 * 功能：框架自动生成项目维护表(EAM) ETS_PA_PROJECTS_ALL数据删除SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsPaProjectsAllDTO etsPaProjectsAll = (EtsPaProjectsAllDTO) dtoParameter;
		String sqlStr = "DELETE FROM"
						+ " ETS_PA_PROJECTS_ALL"
						+ " WHERE"
						+ " PROJECT_ID = ?";
		sqlArgs.add(etsPaProjectsAll.getProjectId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

/**
	 * 功能：框架自动生成项目维护表(EAM) ETS_PA_PROJECTS_ALL数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() { //查明细
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsPaProjectsAllDTO etsPaProjectsAll = (EtsPaProjectsAllDTO) dtoParameter;
		String sqlStr = "SELECT "
						+ " PROJECT_ID,"
						+ " NAME,"
						+ " SEGMENT1,"
						+ " PROJECT_TYPE,"
						+ " PROJECT_STATUS_CODE,"
						+ " START_DATE,"
						+ " COMPLETION_DATE,"
						+ " ENABLED_FLAG,"
						+ " SOURCE,"
						+ " CREATION_DATE,"
						+ " CREATED_BY,"
						+ " LAST_UPDATE_DATE,"
						+ " LAST_UPDATE_BY,"
						+ " MIS_PROJECT_ID"
						+ " FROM"
						+ " ETS_PA_PROJECTS_ALL"
						+ " WHERE"
						+ " PROJECT_ID = ?";
		sqlArgs.add(etsPaProjectsAll.getProjectId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

/**
	 * 功能：框架自动生成项目维护表(EAM) ETS_PA_PROJECTS_ALL多条数据信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回多条数据信息查询用SQLModel
	 */
	public SQLModel getDataMuxModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsPaProjectsAllDTO etsPaProjectsAll = (EtsPaProjectsAllDTO) dtoParameter;
		String sqlStr = "SELECT "
						+ " PROJECT_ID,"
						+ " NAME," //项目名称
						+ " SEGMENT1," //项目编号
						+ " PROJECT_TYPE," //项目类型
						+ " START_DATE,"
						+ " COMPLETION_DATE,"
                        + " ENABLED_FLAG,"
						+ " SOURCE,"
						+ " CREATION_DATE,"
						+ " CREATED_BY,"
						+ " LAST_UPDATE_DATE,"
						+ " LAST_UPDATE_BY,"
						+ " MIS_PROJECT_ID"
						+ " FROM"
						+ " ETS_PA_PROJECTS_ALL"
						+ " WHERE"
						+ "  ( " + SyBaseSQLUtil.isNull() + "  OR NAME LIKE ?)"
						+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR SEGMENT1 LIKE ?)";
		sqlArgs.add(etsPaProjectsAll.getName());
		sqlArgs.add(etsPaProjectsAll.getName());
		sqlArgs.add(etsPaProjectsAll.getSegment1());
		sqlArgs.add(etsPaProjectsAll.getSegment1());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

/**
	 * 功能：框架自动生成项目维护表(EAM) ETS_PA_PROJECTS_ALL页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 */
	public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsPaProjectsAllDTO etsPaProjectsAll = (EtsPaProjectsAllDTO) dtoParameter;
		String sqlStr =
			"SELECT EPPA.PROJECT_ID,\n" +
			"       EPPA.NAME,\n" +
			"       EPPA.SEGMENT1,\n" +
//			"       (SELECT EFV.CODE \n" +
//			"        FROM   ETS_FLEX_VALUES    EFV,\n" +
//			"               ETS_FLEX_VALUE_SET EFVS\n" +
//			"        WHERE  EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID\n" +
//			"               AND EFV.CODE = EPPA.PROJECT_TYPE\n" +
//			"               AND EFVS.CODE = 'PROJECT_TYPE') PROJECT_TYPE,\n" +
			"       EFV.CODE PROJECT_TYPE ,\n" +
			"       EPPA.START_DATE,\n" +
			"       EPPA.COMPLETION_DATE,\n" +
			"       EPPA.ENABLED_FLAG,\n" +
			"       EPPA.SOURCE,\n" +
			"       EPPA.CREATION_DATE,\n" +
			"       EPPA.CREATED_BY,\n" +
			"       EPPA.LAST_UPDATE_DATE,\n" +
			"       EPPA.LAST_UPDATE_BY,\n" +
			"       EPPA.MIS_PROJECT_ID\n" +
			"FROM   ETS_PA_PROJECTS_ALL EPPA \n" +
			"       LEFT JOIN dbo.ETS_FLEX_V EFV ON EFV.CODE = EPPA.PROJECT_TYPE \n" +
			" WHERE " 
			+ " ( EFV.PAR_CODE = '' OR EFV.PAR_CODE IS NULL OR EFV.PAR_CODE = 'PROJECT_TYPE' )" 
			+ " AND (? = '' OR EPPA.NAME LIKE dbo.NVL(?, EPPA.NAME) )"
			+ " AND (? = '' OR EPPA.SEGMENT1 LIKE dbo.NVL(?, EPPA.SEGMENT1) )"
			+ " AND (? = '' OR EPPA.PROJECT_TYPE LIKE dbo.NVL(?, EPPA.PROJECT_TYPE) )"
			+ " AND (? = '' OR EPPA.PROJECT_STATUS_CODE LIKE dbo.NVL(?, EPPA.PROJECT_STATUS_CODE) )";
		
		sqlArgs.add(etsPaProjectsAll.getName());
		sqlArgs.add(etsPaProjectsAll.getName());
		sqlArgs.add(etsPaProjectsAll.getSegment1());
		sqlArgs.add(etsPaProjectsAll.getSegment1());
		sqlArgs.add(etsPaProjectsAll.getProjectType());
		sqlArgs.add(etsPaProjectsAll.getProjectType());
		sqlArgs.add(etsPaProjectsAll.getProjectStatusCode());
		sqlArgs.add(etsPaProjectsAll.getProjectStatusCode());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

}
