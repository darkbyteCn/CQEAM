package com.sino.ams.newasset.report.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.report.dto.ReportInDataDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.StrUtil;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2009-5-14
 * Time: 16:49:50
 * To change this template use File | Settings | File Templates.
 * Changed by 李轶 at 2009-09-21， because add 条码粘贴覆盖率
 */
public class ReportInDataModel extends AMSSQLProducer {

	public ReportInDataModel(SfUserDTO userAccount, ReportInDataDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

    public SQLModel getDataDeleteModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		ReportInDataDTO dto = (ReportInDataDTO) dtoParameter;
        String sqlStr;
        //changed by liyi，because add KPI index
        if(dto.getKpi()){
            sqlStr = "DELETE FROM AMS_KPI_STAT_DATA \n" +
                    " WHERE KPI_CODE = ?\n" +
                    "   AND PERIOD = ?\n" +
                    "   AND COMPANY_CODE = ?";
            sqlArgs.add(dto.getManagerGuideType());
            sqlArgs.add(dto.getPeriod());
            sqlArgs.add(dto.getCompanyCode());
        } else {
            sqlStr = "DELETE FROM AMS_IN_DATA_REPORT WHERE REPORT_ID = ?";
            sqlArgs.add(dto.getReportId());
        }
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

    /**
     * Function:        判断是否属于KPI指标
     * @return          SQLModel
     */
    public SQLModel getIsKpiModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		ReportInDataDTO dto = (ReportInDataDTO) dtoParameter;
        String sqlStr = "SELECT AKSD.KPI_CODE, AKSD.KPI_NAME\n" +
                        "  FROM AMS_KPI_STAT_DEFINE AKSD\n" +
                        " WHERE AKSD.KPI_TYPE = '1' \n" +
                        "   AND AKSD.IS_ENABLE = 'Y'\n" +
                        "   AND AKSD.KPI_CODE = ?";
        sqlArgs.add(dto.getManagerGuideType());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

    public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		ReportInDataDTO dto = (ReportInDataDTO) dtoParameter;
        String sqlStr;
        //changed by liyi，because add KPI index
        if(dto.getKpi()){
            sqlStr = "SELECT AKSD.KPI_CODE MANAGER_GUIDE_TYPE," +
                    "       AKSD.COMPANY_CODE,\n" +
                    "       AKSD.PERIOD,\n" +
                    "       AKSD.KPI_CODE,\n"   +
                    "       AKSDE.KPI_NAME,\n"  + 
                    "       AKSD.CUR_VALUE,\n" +
                    "       AKSD.TOTAL_VALUE\n" +
                    "  FROM AMS_KPI_STAT_DATA AKSD, AMS_KPI_STAT_DEFINE AKSDE\n" +
                    " WHERE AKSD.KPI_CODE = AKSDE.KPI_CODE\n" +
                    "   AND AKSDE.KPI_TYPE = '1'\n" +
                    "   AND AKSDE.IS_ENABLE = 'Y'\n" +
                    "   AND AKSD.KPI_CODE = ?\n" +
                    "   AND AKSD.PERIOD = ?\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR AKSD.COMPANY_CODE = ?)";
            sqlArgs.add(dto.getManagerGuideType());
            sqlArgs.add(dto.getPeriod());
            sqlArgs.add(dto.getCompanyCode());
            sqlArgs.add(dto.getCompanyCode());
        } else {
            sqlStr = "SELECT AIDR.*,AIDR.REPORT_TYPE MANAGER_GUIDE_TYPE FROM AMS_IN_DATA_REPORT AIDR WHERE AIDR.REPORT_ID = ?";
            sqlArgs.add(dto.getReportId());
        }
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

    public SQLModel getDataUpdateModel() {
		SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        ReportInDataDTO dto = (ReportInDataDTO) dtoParameter;
        String sqlStr;
        //changed by liyi，because add 条码粘贴覆盖率
        if(dto.getKpi()){
            sqlStr = "UPDATE AMS_KPI_STAT_DATA\n"
								+ " SET\n"
								+ " CUR_VALUE = ?,\n"
								+ " TOTAL_VALUE = ?,\n"
								+ " PTD_AMOUNT = ?,\n"
                                + " LAST_UPDATE_DATE = GETDATE(),\n"
                                + " LAST_UPDATE_BY = ?\n"
								+ " WHERE\n"
								+ "  	KPI_CODE = ?\n" +
                                    "   AND PERIOD = ?\n" +
                                    "   AND COMPANY_CODE = ?";
            if(dto.getCurValue() !=0){
                sqlArgs.add(dto.getCurValue());
            } else {
                sqlArgs.add("0");
                dto.setCurValue(0);
            }
            if(dto.getTotalValue()!=0){
                sqlArgs.add(dto.getTotalValue());
                sqlArgs.add(dto.getCurValue()/dto.getTotalValue());
            } else {
                sqlArgs.add("0");
                sqlArgs.add("0");
            }
            sqlArgs.add(userAccount.getUserId());
            sqlArgs.add(dto.getManagerGuideType());
            sqlArgs.add(dto.getPeriod());
            sqlArgs.add(dto.getCompanyCode());
        } else {
            sqlStr = "UPDATE AMS_IN_DATA_REPORT \n" +
                "SET    ORGANIZATION_ID       = ISNULL(?, ORGANIZATION_ID),\n" +
                "       PERIOD                = ISNULL(?, PERIOD),\n" +
                "       PROJECT_TRUN_ASSETS   = ISNULL(?, PROJECT_TRUN_ASSETS),\n" +
                "       PROJECT_SUM_ASSETS    = ISNULL(?, PROJECT_SUM_ASSETS),\n" +
                "       NO_TIMELY_REPORT_NUM  = ISNULL(?, NO_TIMELY_REPORT_NUM),\n" +
                "       ASSETSMENT_REPORT_NUM = ISNULL(?, ASSETSMENT_REPORT_NUM),\n" +
                "       ASSETSMENT_FALSE_NUM  = ISNULL(?, ASSETSMENT_FALSE_NUM),\n" +
                "       ASSETSMENT_ASSETS_SUM = ISNULL(?, ASSETSMENT_ASSETS_SUM),\n" +
                "       COMPLETE_CHECK_NUM    = ISNULL(?, COMPLETE_CHECK_NUM),\n" +
                "       PLAN_CHECK_NUM        = ISNULL(?, PLAN_CHECK_NUM),\n" +
                "       ACCOUNT_MATCH_CASE    = ISNULL(?, ACCOUNT_MATCH_CASE),\n" +
                "       CHECK_ASSETS_SUM      = ISNULL(?, CHECK_ASSETS_SUM),\n" +
                "       ASSETS_COP_NUM      = ISNULL(?, ASSETS_COP_NUM),\n" +
                "       ASSETS_COP_SUM      = ISNULL(?, ASSETS_COP_SUM),\n" +
                "       ASSETS_MATCH_CASE      = ISNULL(?, ASSETS_MATCH_CASE),\n" +
                "       ASSETS_CHECK_SUM      = ISNULL(?, ASSETS_CHECK_SUM),\n" +
                "       ACCURATE_ERROR_NUMBER = ISNULL(?, ACCURATE_ERROR_NUMBER),\n" +
                "       LAST_UPDATE_DATE      = GETDATE(),\n" +
                "       VALUE = ISNULL(?, VALUE),\n" +
                "       LAST_UPDATE_BY        = ?\n" +
                "WHERE  REPORT_ID = ?";
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getPeriod());
            sqlArgs.add(dto.getProjectTrunAssets());
            sqlArgs.add(dto.getProjectSumAssets());
            sqlArgs.add(dto.getNoTimelyReportNum());
            sqlArgs.add(dto.getAssetsmentReportNum());
            sqlArgs.add(dto.getAssetsmentFalseNum());
            sqlArgs.add(dto.getAssetsmentAssetsSum());
            sqlArgs.add(dto.getCompleteCheckNum());
            sqlArgs.add(dto.getPlanCheckNum());
            sqlArgs.add(dto.getAccountMatchCase());
            sqlArgs.add(dto.getCheckAssetsSum());
            sqlArgs.add(dto.getAssetsCopNum());
            sqlArgs.add(dto.getAssetsCopSum());
            sqlArgs.add(dto.getAssetsMatchCase());
            sqlArgs.add(dto.getAssetsCheckSum());
            sqlArgs.add(dto.getAccurateErrorNumber());
            sqlArgs.add(dto.getValue());
            sqlArgs.add(userAccount.getUserId());
            sqlArgs.add(dto.getReportId());
        }

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

    public SQLModel getDataCreateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		ReportInDataDTO dto = (ReportInDataDTO) dtoParameter;
        //changed by liyi，because add 条码粘贴覆盖率
        String sqlStr;
      if(dto.getKpi()){
        	//if(false){
            sqlStr = "INSERT INTO AMS_KPI_STAT_DATA\n" +
                        "  (KPI_CODE,\n" +
                        "   COMPANY_CODE,\n" +
                        "   PERIOD,\n" +
                        "   CREATION_DATE,\n" +
                        "   CREATED_BY,\n" +
                        "   CUR_VALUE,\n" +
                        "   TOTAL_VALUE,\n" +
                        "   PTD_AMOUNT\n" +
                        " ) " +
                        "VALUES \n" +
                        "  (?, ?, ?, GETDATE(), ?, ?, ?, ?)";
            sqlArgs.add(dto.getManagerGuideType());
            sqlArgs.add(dto.getCompanyCode());
            sqlArgs.add(dto.getPeriod());
            sqlArgs.add(userAccount.getUserId());
            if(dto.getCurValue()!=0){
                sqlArgs.add(dto.getCurValue());
            } else {
                sqlArgs.add("0");
                dto.setCurValue(0);
            }
            if(dto.getTotalValue()!=0){
                sqlArgs.add(dto.getTotalValue());
                sqlArgs.add(dto.getCurValue()/ dto.getTotalValue());
            } else {
                sqlArgs.add("0");
                sqlArgs.add("0");
            }
        } else {
            sqlStr = "INSERT INTO AMS_IN_DATA_REPORT\n" +
                    "  (REPORT_ID,\n" +
                    "   ORGANIZATION_ID,\n" +
                    "   PERIOD,\n" +
                    "   REPORT_TYPE,\n" +
                    "   PROJECT_TRUN_ASSETS,\n" +
                    "   PROJECT_SUM_ASSETS,\n" +
                    "   NO_TIMELY_REPORT_NUM,\n" +
                    "   ASSETSMENT_REPORT_NUM,\n" +
                    "   ASSETSMENT_FALSE_NUM,\n" +
                    "   ASSETSMENT_ASSETS_SUM,\n" +
                    "   COMPLETE_CHECK_NUM,\n" +
                    "   PLAN_CHECK_NUM,\n" +
                    "   ACCOUNT_MATCH_CASE,\n" +
                    "   CHECK_ASSETS_SUM,\n" +
                    "   ASSETS_COP_NUM,\n" +
                    "   ASSETS_COP_SUM,\n" +
                    "   ASSETS_MATCH_CASE,\n" +
                    "   ASSETS_CHECK_SUM,\n" +
                    "   ACCURATE_ERROR_NUMBER,\n" +
                    "   CREATION_DATE,\n" +
                    "   CREATED_BY ,\n" +
                    "   VALUE)\n" +
                    "VALUES\n" +
                    "  ( NEWID() ,\n" +
                    "   ?,\n" +
                    "   ?,\n" +
                    "   ?,\n" +
                    "   ?,\n" +
                    "   ?,\n" +
                    "   ?,\n" +
                    "   ?,\n" +
                    "   ?,\n" +
                    "   ?,\n" +
                    "   ?,\n" +
                    "   ?,\n" +
                    "   ?,\n" +
                    "   ?,\n" +
                    "   ?,\n" +
                    "   ?,\n" +
                    "   ?,\n" +
                    "   ?,\n" +
                    "   ?,\n" +
                    "   GETDATE(),\n" +
                    "   ?,\n" +
                    "   ?)";
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add( dto.getPeriod() );
            sqlArgs.add(dto.getManagerGuideType());
            sqlArgs.add(dto.getProjectTrunAssets());
            sqlArgs.add(dto.getProjectSumAssets());
            sqlArgs.add(dto.getNoTimelyReportNum());
            sqlArgs.add(dto.getAssetsmentReportNum());
            sqlArgs.add(dto.getAssetsmentFalseNum());
            sqlArgs.add(dto.getAssetsmentAssetsSum());
            sqlArgs.add(dto.getCompleteCheckNum());
            sqlArgs.add(dto.getPlanCheckNum());
            sqlArgs.add(dto.getAccountMatchCase());
            sqlArgs.add(dto.getCheckAssetsSum());
            sqlArgs.add(dto.getAssetsCopNum());
            sqlArgs.add(dto.getAssetsCopSum());
            sqlArgs.add(dto.getAssetsMatchCase());
            sqlArgs.add(dto.getAssetsCheckSum());
            sqlArgs.add(dto.getAccurateErrorNumber());
            sqlArgs.add(userAccount.getUserId());
            sqlArgs.add(dto.getValue());
        }
        sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
	    List sqlArgs = new ArrayList();
        ReportInDataDTO dto = (ReportInDataDTO) dtoParameter;
        String sqlStr;
        if(dto.getKpi()){
            sqlStr = "SELECT AKSDE.KPI_NAME REPORT_TYPE,\n" +
                    "       AKSD.KPI_CODE,\n"   +
                    "       AKSD.COMPANY_CODE,\n" +
                    "       EOCM.COMPANY,\n" +
                    "       AKSD.PERIOD,\n" +
                    "       AKSD.CREATION_DATE\n" +
                    "  FROM AMS_KPI_STAT_DATA   AKSD,\n" +
                    "       ETS_OU_CITY_MAP     EOCM,\n" +
                    "       AMS_KPI_STAT_DEFINE AKSDE\n" +
                    " WHERE AKSD.COMPANY_CODE = EOCM.COMPANY_CODE\n" +
                    "   AND AKSD.KPI_CODE = AKSDE.KPI_CODE\n" +
                    "   AND AKSDE.KPI_TYPE = '1'\n" +
                    "   AND AKSDE.IS_ENABLE = 'Y'\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR AKSD.COMPANY_CODE = ?)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR AKSD.KPI_CODE = ?)\n" +
                    "   AND AKSD.PERIOD = ?\n" +
                    " ORDER BY EOCM.ORGANIZATION_ID";
            sqlArgs.add(dto.getCompanyCode());
            sqlArgs.add(dto.getCompanyCode());
            sqlArgs.add(dto.getManagerGuideType());
            sqlArgs.add(dto.getManagerGuideType());
            if(dto.getPeriod().length() == 6){
                sqlArgs.add(dto.getPeriod().substring(0,4)+ dto.getPeriod().substring(4));
            } else {
                sqlArgs.add(dto.getPeriod());
            }
        } else {
        	String conditon="";
        	if(dto.getAct().equals("POINT_AT_ACTION_QUERY")){//资产实物管理抽查任务完成率，转资率， EAM系统决策分析报表上报及时率
        		conditon=" AND AIDR.REPORT_TYPE IN ('CHECK_RATE','TRUN_RATE','IN_TIME_RATE') ";
        	}
            sqlStr = " SELECT AIDR.REPORT_ID,\n" +
                               " dbo.APP_GET_FLEX_VALUE(AIDR.REPORT_TYPE, 'MANAGE_INDICATORS') REPORT_TYPE,\n" +
                               " AIDR.ORGANIZATION_ID,\n" +
                               " EOCM.COMPANY,\n" +
                               " AIDR.PERIOD,\n" +
                               " AIDR.CREATION_DATE\n" +
                          " FROM AMS_IN_DATA_REPORT AIDR,\n" +
                               " ETS_OU_CITY_MAP EOCM\n" +
                         " WHERE AIDR.ORGANIZATION_ID *= EOCM.ORGANIZATION_ID\n" +
                               " AND ( " + SyBaseSQLUtil.isNull() + "  OR AIDR.REPORT_TYPE = ?)\n" +
                               "   AND ( ?=0 OR ?=-1 OR AIDR.ORGANIZATION_ID = ?)\n" +
                               " AND ( ? = '' OR AIDR.PERIOD = ?) " 
                               +conditon+" ORDER BY AIDR.CREATION_DATE ASC";
            sqlArgs.add(dto.getManagerGuideType());
            sqlArgs.add(dto.getManagerGuideType());
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(  dto.getPeriod() );
            sqlArgs.add(  dto.getPeriod() );
        }
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
	}
}
