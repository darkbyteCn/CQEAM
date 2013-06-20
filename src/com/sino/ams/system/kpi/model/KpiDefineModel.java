package com.sino.ams.system.kpi.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.kpi.dto.KpiDefineDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.framework.sql.BaseSQLProducer;

public class KpiDefineModel extends BaseSQLProducer {

	private SfUserDTO sfUser = null;

/**
	 * 功能：项目维护表(EAM) ETS_PA_PROJECTS_ALL 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter KpiDefineDTO 本次操作的数据
	 */
	public KpiDefineModel(SfUserDTO userAccount, KpiDefineDTO dtoParameter) {
		super(userAccount, dtoParameter);
		sfUser = userAccount;
	}
	
	/**
	 * 功能：框架自动生成项目维护表(EAM) ETS_PA_PROJECTS_ALL页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 */
	public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		KpiDefineDTO dto = (KpiDefineDTO) dtoParameter;
		String sqlStr =
			" SELECT AKSD.KPI_CODE,\n" +
			"       AKSD.KPI_NAME,\n" +
			"       AKSD.KPI_DESC,\n" +
			"       AKSD.KPI_VALUE,\n" +
			"       AKSD.CREATION_DATE,\n" +
			"       AKSD.CREATED_BY,\n" +
			"       AKSD.LAST_UPDATE_DATE,\n" +
			"       AKSD.LAST_UPDATE_BY,\n" +
			"       AKSD.KPI_TYPE,\n" +
			"       EFV.VALUE KPI_TYPE_NAME,\n" +
//			"       (SELECT EFV.VALUE\n" +
//			"        FROM   ETS_FLEX_VALUES    EFV,\n" +
//			"               ETS_FLEX_VALUE_SET EFVS\n" +
//			"        WHERE  EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID\n" +
//			"               AND EFV.CODE = AKSD.KPI_TYPE\n" +
//			"               AND EFVS.CODE = 'KPI_TYPE') KPI_TYPE_NAME,\n" +
			"       AKSD.IS_ENABLE\n" +
			" FROM   AMS_KPI_STAT_DEFINE AKSD \n" +
			" LEFT JOIN  \n" +
			" ETS_FLEX_V EFV  \n" +
			" ON ( \n" +
			" EFV.CODE = AKSD.KPI_TYPE \n" +
			" AND EFV.PAR_CODE = 'KPI_TYPE' \n" +
			" ) \n" +
			" WHERE AKSD.IS_ENABLE = 'Y' \n"+ 
			" 		AND ( " + SyBaseSQLUtil.nullStringParam()+ " OR AKSD.KPI_NAME LIKE ?) \n"+ 
			" 		AND ( " + SyBaseSQLUtil.nullStringParam()+ " OR AKSD.KPI_TYPE = ?) \n"+
			" ORDER BY AKSD.KPI_CODE";

		sqlArgs.add(dto.getKpiName());
		sqlArgs.add(dto.getKpiName());
		sqlArgs.add("%"+dto.getKpiName()+"%");
		
		SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getKpiType());
//		sqlArgs.add(dto.getKpiType());
//		sqlArgs.add(dto.getKpiType());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	public SQLModel getAllDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		KpiDefineDTO dto = (KpiDefineDTO) dtoParameter;
		String sqlStr =
			" SELECT AKSD.KPI_CODE,\n" +
			"       AKSD.KPI_NAME,\n" +
			"       AKSD.KPI_DESC,\n" +
			"       AKSD.KPI_VALUE,\n" +
			"       AKSD.CREATION_DATE,\n" +
			"       AKSD.CREATED_BY,\n" +
			"       AKSD.LAST_UPDATE_DATE,\n" +
			"       AKSD.LAST_UPDATE_BY,\n" +
			"       AKSD.KPI_TYPE,\n" +
			"       EFV.VALUE KPI_TYPE_NAME,\n" +
//			"       (SELECT EFV.VALUE\n" +
//			"        FROM   ETS_FLEX_VALUES    EFV,\n" +
//			"               ETS_FLEX_VALUE_SET EFVS\n" +
//			"        WHERE  EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID\n" +
//			"               AND EFV.CODE = AKSD.KPI_TYPE\n" +
//			"               AND EFVS.CODE = 'KPI_TYPE') KPI_TYPE_NAME,\n" +
			"       AKSD.IS_ENABLE \n" +
			" FROM   AMS_KPI_STAT_DEFINE AKSD \n" +
			" LEFT JOIN  \n" +
			" ETS_FLEX_V EFV  \n" +
			" ON ( \n" +
			" EFV.CODE = AKSD.KPI_TYPE \n" +
			" AND EFV.PAR_CODE = 'KPI_TYPE' \n" +
			" ) \n" +
			//" WHERE AKSD.IS_ENABLE = 'Y'"+ 
			//" 		AND ( " + SyBaseSQLUtil.isNull() + "  OR AKSD.KPI_NAME LIKE ?)"+ 
			//" 		AND ( " + SyBaseSQLUtil.isNull() + "  OR AKSD.KPI_TYPE = ?)"+
			" ORDER BY AKSD.KPI_CODE";

		//sqlArgs.add(dto.getKpiName());
		//sqlArgs.add("%"+dto.getKpiName()+"%");
		//sqlArgs.add(dto.getKpiType());
		//sqlArgs.add(dto.getKpiType());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	public SQLModel getDataModel(KpiDefineDTO pdto) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		KpiDefineDTO dto = (KpiDefineDTO) dtoParameter;
		String sqlStr =
			" SELECT AKSD.KPI_CODE,\n" +
			"       AKSD.KPI_NAME,\n" +
			"       AKSD.KPI_DESC,\n" +
			"       AKSD.KPI_VALUE,\n" +
			"       AKSD.CREATION_DATE,\n" +
			"       AKSD.CREATED_BY,\n" +
			"       AKSD.LAST_UPDATE_DATE,\n" +
			"       AKSD.LAST_UPDATE_BY,\n" +
			"       AKSD.KPI_TYPE,\n" +
			"       AKSD.IS_ENABLE\n" +
			" FROM   AMS_KPI_STAT_DEFINE AKSD \n" +
			" WHERE AKSD.KPI_CODE = ?";		
		sqlArgs.add(pdto.getKpiCode());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	public SQLModel insertDataModel(KpiDefineDTO pdto) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		KpiDefineDTO dto = (KpiDefineDTO) dtoParameter;
		String sqlStr =
			" INSERT INTO AMS_KPI_STAT_DEFINE  ( \n" +
			"       KPI_CODE,\n"+
			"       KPI_NAME,\n" +
			"       KPI_DESC,\n" +
			"       KPI_VALUE,\n" +
			"       CREATION_DATE,\n" +
			"       CREATED_BY,\n" +
			"       LAST_UPDATE_DATE,\n" +
			"       LAST_UPDATE_BY,\n" +
			"       KPI_TYPE,\n" +
			"       IS_ENABLE) \n" +
			" VALUES(?,?,?,?, " + SyBaseSQLUtil.getCurDate()+ " ,?, "+ SyBaseSQLUtil.getCurDate()+" ,?,?,?)";			

		sqlArgs.add(pdto.getKpiCode());
		sqlArgs.add(pdto.getKpiName());
		sqlArgs.add(pdto.getKpiDesc());
		sqlArgs.add(pdto.getKpiValue());
		sqlArgs.add(((SfUserDTO)userAccount).getUserId());
		sqlArgs.add(((SfUserDTO)userAccount).getUserId());
		sqlArgs.add(pdto.getKpiType());
		sqlArgs.add(pdto.getIsEnable());
		
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	
	
	public SQLModel updateDataModel(KpiDefineDTO pdto) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		KpiDefineDTO dto = (KpiDefineDTO) dtoParameter;
		String sqlStr =
			" UPDATE AMS_KPI_STAT_DEFINE SET \n" +
			"       KPI_CODE = ?,\n" +
			"       KPI_NAME = ?,\n" +
			"       KPI_DESC = ?,\n" +
			"       KPI_VALUE = CONVERT( DECIMAL , ? ) ,\n" +
			"       LAST_UPDATE_DATE = GETDATE(),\n" +
			"       LAST_UPDATE_BY = ?,\n" +
			"       KPI_TYPE = ?,\n" +			
			"       IS_ENABLE = ?\n" +
			" WHERE KPI_CODE = ?";

		sqlArgs.add(pdto.getKpiCode());
		sqlArgs.add(pdto.getKpiName());
		sqlArgs.add(pdto.getKpiDesc());
		sqlArgs.add(pdto.getKpiValue());
		sqlArgs.add(((SfUserDTO)userAccount).getUserId());
		sqlArgs.add(pdto.getKpiType());
		sqlArgs.add(pdto.getIsEnable());
		
		sqlArgs.add(pdto.getKpiCode());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
}
