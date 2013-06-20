package com.sino.ams.newasset.report.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.newasset.report.dto.KpiInputDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.dto.BaseUserDTO;

public class KpiInputModel extends AMSSQLProducer{

	private SfUserDTO user = null;

	public KpiInputModel(BaseUserDTO userAccount, DTO dtoParameter) {
		super(userAccount, dtoParameter);
		this.user  = (SfUserDTO) userAccount;
	}
	
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
	    List sqlArgs = new ArrayList();
	    KpiInputDTO dto = (KpiInputDTO) dtoParameter;
        String sqlStr = "SELECT \n" +
        "   EFV.VALUE KPI_NAME ,\n " +
        "   EFV.DESCRIPTION KPI_DESC,\n "+
        "   SKV.PERIOD, \n " +
        "   SKV.COMPANY_CODE, \n " +
        "   SKV.PERIOD_TYPE , \n " +
        "   EFV.CODE KPI_CODE, \n " +   
        "   SKV.ASSET_TYPE , \n " +
        "   SKV.VALUE " +
        "   from ETS_FLEX_VALUE_SET EFVS, \n " + 
        "   ETS_FLEX_VALUES EFV, \n " +
        "   STAT_INPUT_KPI_VALUES SKV " +   
        "   WHERE EFV.FLEX_VALUE_SET_ID=EFVS.FLEX_VALUE_SET_ID \n " +
        "   AND EFVS.CODE='KPI_INPUT_TYPE' \n " + 
        "   AND EFV.CODE *= SKV.KPI_TYPE   \n " +
        "   AND SKV.PERIOD_TYPE = ?  \n" +
        "   AND SKV.PERIOD = ?  \n" ;
        sqlArgs.add(dto.getPeriodType());
        sqlArgs.add(dto.getPeriod());
        if(dto.getPeriodType().equals("YEAR")){
        	sqlStr +="  AND EFV.VALUE like '%年度%' ";
        }
        if(dto.getPeriodType().equals("MONTH")){
        	sqlStr +="  AND EFV.VALUE not like '%年度%' ";
        }
        if(dto.getIndexType().equals("ADM_INDEX")){
        	sqlStr +="  AND EFV.CODE LIKE '2%'  \n  AND SKV.ASSET_TYPE = ? ";
        	sqlArgs.add(dto.getAssetsType());
        }
        if(dto.getIndexType().equals("SYS_INDEX")){
        	sqlStr +="  AND EFV.CODE LIKE '1%' ";
        }
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
	}
	
	/**
	 * 功能：框架自动生成资产地点表(EAM) ETS_OBJECT数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 */
	public SQLModel getDataUpdateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		KpiInputDTO dto = (KpiInputDTO) dtoParameter;
		String sqlStr = "UPDATE  "
						+ " STAT_INPUT_KPI_VALUES "
						+ " SET VALUE  = ?\n ,"
						+ " LAST_UPDATE_DATE  = GETDATE()\n ,"
						+ " LAST_UPDATE_BY  = ?\n "
						+ " WHERE "
						+ " COMPANY_CODE = dbo.GET_PROVINCE_CODE() "
						+ " AND KPI_TYPE = ? "
						+ " AND PERIOD_TYPE = ? "
						+ " AND PERIOD = ? ";
		sqlArgs.add(dto.getValue());
		sqlArgs.add(user.getUserId());
		sqlArgs.add(dto.getKpiType());
		sqlArgs.add(dto.getPeriodType());
		if(!dto.getPeriod().equals("") ){
			sqlArgs.add(dto.getPeriod());
		}
		if(!"".equals(dto.getAssetsType())){
			sqlStr =  "  AND ASSET_TYPE = ?  ";
			sqlArgs.add(dto.getAssetsType());
		}
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	/**
	 * 功能：判断是否存能失效
	 * @return SQLModel
	 */
	public SQLModel isValidity() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		KpiInputDTO dto = (KpiInputDTO) dtoParameter;
		String sqlStr = "SELECT "
						+ " 1 "
						+ " FROM "
						+ " STAT_INPUT_KPI_VALUES "
						+ " WHERE "
						+ " COMPANY_CODE = dbo.GET_PROVINCE_CODE() "
						+ " AND KPI_TYPE = ? "
						+ " AND PERIOD_TYPE = ? "
						+ " AND PERIOD = ? ";
		sqlArgs.add(dto.getKpiType());
		sqlArgs.add(dto.getPeriodType());
		
		if(!"".equals(dto.getPeriod()) ){
			sqlArgs.add(dto.getPeriod());
		}
		if(!"".equals(dto.getAssetsType())){
			sqlStr =  "  AND ASSET_TYPE = ?  ";
			sqlArgs.add(dto.getAssetsType());
		}
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	/**
	 * 功能：框架自动生成数据插入SQLModel，请根据实际需要修改。
	 *
	 * @return SQLModel 返回数据插入用SQLModel
	 */
	public SQLModel getDataCreateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		KpiInputDTO dto = (KpiInputDTO) dtoParameter;
		String sqlStr = "INSERT INTO "
						+ " STAT_INPUT_KPI_VALUES("
						+ " COMPANY_CODE,\n"
						+ " PERIOD,\n"
						+ " PERIOD_TYPE,\n"
						+ " KPI_TYPE,\n"
						+ " VALUE,\n"
						+ " CREATED_BY,\n"
						+ " ASSET_TYPE,\n"
						+ " CREATED_DATE \n"
						+ ") VALUES ("
						+ " dbo.GET_PROVINCE_CODE(), ?, ?, ?, ?, ?, ?, GETDATE() )";
		if(!dto.getPeriod().equals("")){
			sqlArgs.add(dto.getPeriod());
		}
		sqlArgs.add(dto.getPeriodType());
		sqlArgs.add(dto.getKpiType());
		sqlArgs.add(dto.getValue());
		sqlArgs.add(user.getUserId());
		sqlArgs.add(dto.getAssetsType());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
