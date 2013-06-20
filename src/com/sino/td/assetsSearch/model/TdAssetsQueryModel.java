package com.sino.td.assetsSearch.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.security.dto.ServletConfigDTO;

public class TdAssetsQueryModel extends AMSSQLProducer {

    public TdAssetsQueryModel(SfUserDTO userAccount, AmsAssetsAddressVDTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    public void setServletConfig(ServletConfigDTO servletConfig) {
        super.setServletConfig(servletConfig);
    }

    /**
     * 功能：返回页面翻页查询时所需要的SQLModel
     * @return SQLModel
     * @throws SQLModelException
     */
    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();

        try {
            if (servletConfig.getProvinceCode().equals(DictConstant.PROVINCE_CODE_JIN)) {
                sqlModel = getSXQueryModel();
            } else {
                AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
                String sqlStr = "SELECT"
                        + " EFA.TAG_NUMBER,"
                        + " EFA.MIS_TAG_NUMBER,"
                        + " EFA.ASSET_NUMBER,"
                        + " EFA.SEGMENT1,"
                        + " EFA.FA_CATEGORY1,"
                        + " EFA.SEGMENT2,"
                        + " EFA.FA_CATEGORY2,"
                        + " EFA.ASSETS_DESCRIPTION,"
                        + " EFA.MODEL_NUMBER,"
                        + " EFA.CURRENT_UNITS,"
                        + " EFA.UNIT_OF_MEASURE,"
                        + " EFA.ASSETS_LOCATION_CODE,"
                        + " EFA.ASSETS_LOCATION,"
                        + " EFA.ASSIGNED_TO_NAME,"
                        + " EFA.ASSIGNED_TO_NUMBER,"
                        + " EPPA.SEGMENT1 PROJECT_NUMBER,"
                        + " EFA.PROJECT_NAME,"
                        + " EPPA.PROJECT_TYPE,"
                        + " EFA.LIFE_IN_YEARS,"
                        + " EFA.DATE_PLACED_IN_SERVICE,"
                        + " EFA.ASSETS_CREATE_DATE,"
                        
                        + "	EFA.COST,\n"			//原值
    					+ " EFA.DEPRN_RESERVE,\n"	//累计折旧
    					+ " EFA.NET_ASSET_VALUE,\n" //资产净值
    					+ " EFA.IMPAIR_RESERVE,\n" //累计减值准备 
    					+ " EFA.DEPRN_COST,\n"
                        + " EFA.SCRAP_VALUE,"
                        
                        + " EFA.COMPANY_CODE,"
                        + " EOCM.COMPANY,"
                        + " EFA.BOOK_TYPE_CODE,"
                        + " EOCM.BOOK_TYPE_NAME,"
                        + " EFA.DEPRECIATION_ACCOUNT,"
                        + " 	(SELECT "
                        + " 		AAV.ACCOUNT_NAME DEPRECIATION_ACCOUNT_NAME"
                        + " 	   FROM"
                        + " 			AMS_ACCOUNT_TD_V AAV"
                        + " 	 WHERE"
                        + "			 AAV.ACCOUNT_CODE = EFA.DEPRECIATION_ACCOUNT"
//                        + "			 AAV.ACCOUNT_CODE_1 = SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 1, 4)"
//                        + " 		 AND AAV.ACCOUNT_CODE_2 = SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6)"
//                        + " 		 AND AAV.ACCOUNT_CODE_3 = SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 13, 10)"
//                        + " 		 AND AAV.ACCOUNT_CODE_4 = SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 24, 6)"
//                        + "			 AND AAV.ACCOUNT_CODE_5 = SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 31, 8)"
//                        + "			 AND AAV.ACCOUNT_CODE_6 = SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 40, 4)"
//                        + "			 AND AAV.ACCOUNT_CODE_7 = SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 45, 6)"
                        + ")"
                        + "		 DEPRECIATION_ACCOUNT_NAME"
                        + " FROM"
                        + " ETS_FA_ASSETS_TD    EFA,"
                        + " ETS_PA_PROJECTS_ALL EPPA,"
                        + " ETS_OU_CITY_MAP     EOCM"
                        + " WHERE  "
                        + " EFA.PROJECT_ID *= EPPA.PROJECT_ID"
                        + " AND EFA.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
                        + " AND EFA.ORGANIZATION_ID = ISNULL(?, EFA.ORGANIZATION_ID)"
                        + " AND EFA.FA_CATEGORY1 LIKE dbo.NVL(?, EFA.FA_CATEGORY1)"
                        + " AND (" + SyBaseSQLUtil.isNull() + " OR EFA.FA_CATEGORY2 LIKE ?)"
                        + " AND EFA.TAG_NUMBER LIKE dbo.NVL(?, EFA.TAG_NUMBER)"
                        + " AND EFA.MIS_TAG_NUMBER LIKE dbo.NVL(?, EFA.MIS_TAG_NUMBER)"
                        + " AND EFA.ASSETS_DESCRIPTION LIKE dbo.NVL(?, EFA.ASSETS_DESCRIPTION)"
                        + " AND (" + SyBaseSQLUtil.isNull() + " OR EFA.ASSIGNED_TO_NAME LIKE dbo.NVL(?, EFA.ASSIGNED_TO_NAME))"
                        + " AND EFA.DATE_PLACED_IN_SERVICE >= ISNULL(?, EFA.DATE_PLACED_IN_SERVICE)"
                        + " AND EFA.DATE_PLACED_IN_SERVICE <= ISNULL(?, EFA.DATE_PLACED_IN_SERVICE)"
                        + " AND EFA.ASSETS_CREATE_DATE >= ISNULL(?, EFA.ASSETS_CREATE_DATE)"
                        + " AND EFA.ASSETS_CREATE_DATE <= ISNULL(?, EFA.ASSETS_CREATE_DATE)"
                        + " AND EFA.DEPRECIATION_ACCOUNT LIKE dbo.NVL(?, EFA.DEPRECIATION_ACCOUNT)"
                        + " AND (" + SyBaseSQLUtil.isNull() + " OR EFA.PROJECT_NAME LIKE dbo.NVL(?, EFA.PROJECT_NAME))"
                        + " AND EFA.ASSETS_LOCATION_CODE LIKE dbo.NVL(?, EFA.ASSETS_LOCATION_CODE)";
                List sqlArgs = new ArrayList();
                sqlArgs.add(dto.getOrganizationId());
                sqlArgs.add(dto.getFaCategory1());
                sqlArgs.add(dto.getFaCategory2());
                sqlArgs.add(dto.getFaCategory2());
                sqlArgs.add(dto.getTagNumber());
                sqlArgs.add(dto.getMisTagNumber());
                sqlArgs.add(dto.getAssetsDescription());
                sqlArgs.add(dto.getAssignedToName());
                sqlArgs.add(dto.getAssignedToName());
                sqlArgs.add(dto.getStartDate());
                sqlArgs.add(dto.getSQLEndDate());

                sqlArgs.add(dto.getStartCreationDate());
                sqlArgs.add(dto.getSQLEndCreationDate());
                sqlArgs.add("%" + dto.getCostCenterCode() + "%");
                sqlArgs.add(dto.getProjectName());
                sqlArgs.add(dto.getProjectName());
                sqlArgs.add(dto.getAssetsLocationCode());
                if (!(userAccount.isProvinceUser() && userAccount.isSysAdmin())) {
                    sqlStr += " AND EOCM.ORGANIZATION_ID = ?";
                    sqlArgs.add(userAccount.getOrganizationId());
                }
                sqlModel.setSqlStr(sqlStr);
                sqlModel.setArgs(sqlArgs);
            }
        } catch (CalendarException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
        }
        return sqlModel;
    }

    private SQLModel getSXQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
            String sqlStr = "SELECT"
                    + " EFA.TAG_NUMBER,"
                    + " EFA.MIS_TAG_NUMBER,"
                    + " EFA.ASSET_NUMBER,"
                    + " EFA.SEGMENT1,"
                    + " EFA.FA_CATEGORY1,"
                    + " EFA.SEGMENT2,"
                    + " EFA.FA_CATEGORY2,"
                    + " EFA.ASSETS_DESCRIPTION,"
                    + " EFA.MODEL_NUMBER,"
                    + " EFA.CURRENT_UNITS,"
                    + " EFA.UNIT_OF_MEASURE,"
                    + " EFA.ASSETS_LOCATION_CODE,"
                    + " EFA.ASSETS_LOCATION,"
                    + " EFA.ASSIGNED_TO_NAME,"
                    + " EFA.ASSIGNED_TO_NUMBER,"
                    + " EPPA.SEGMENT1 PROJECT_NUMBER,"
                    + " EFA.PROJECT_NAME,"
                    + " EPPA.PROJECT_TYPE,"
                    + " EFA.LIFE_IN_YEARS,"
                    + " EFA.DATE_PLACED_IN_SERVICE,"
                    + " EFA.ASSETS_CREATE_DATE,"
                    + " EFA.ORIGINAL_COST,"
                    + " EFA.COST,"
                    + " EFA.DEPRN_RESERVE,"
                    + " EFA.DEPRN_COST,"
                    + " EFA.SCRAP_VALUE,"
                    + " EFA.IMPAIR_RESERVE,"
                    + " EFA.COMPANY_CODE,"
                    + " EOCM.COMPANY,"
                    + " EFA.BOOK_TYPE_CODE,"
                    + " EOCM.BOOK_TYPE_NAME,"
                    + " EFA.DEPRECIATION_ACCOUNT,"
                    + " AMS_MIS_PKG.GET_ACCOUNT_NAME(SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 1, 4),'SXMC_COA_CO','TDMIS') || '.' ||"
                    + " AMS_MIS_PKG.GET_ACCOUNT_NAME(SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6),'SXMC_COA_CC','TDMIS') || '.' ||"
                    + " AMS_MIS_PKG.GET_ACCOUNT_NAME(SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 13, 10), 'SXMC_COA_AC','TDMIS') || '.' ||"
                    + " AMS_MIS_PKG.GET_ACCOUNT_NAME(SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 24, 6), 'SXMC_COA_SB','TDMIS') || '.' ||"
                    + " AMS_MIS_PKG.GET_ACCOUNT_NAME(SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 31, 8),'SXMC_COA_IC','TDMIS') || '.' ||"
                    + " AMS_MIS_PKG.GET_ACCOUNT_NAME(SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 40, 4),'SXMC_COA_PJ','TDMIS') || '.' ||"
                    + " AMS_MIS_PKG.GET_ACCOUNT_NAME(SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 45, 6),'SXMC_COA_SP','TDMIS') DEPRECIATION_ACCOUNT_NAME"
                    + " FROM"
                    + " ETS_FA_ASSETS_TD    EFA,"
                    + " ETS_PA_PROJECTS_ALL EPPA,"
                    + " ETS_OU_CITY_MAP     EOCM"
                    + " WHERE  "
                    + " EFA.PROJECT_ID *= EPPA.PROJECT_ID"
                    + " AND EFA.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
                    + " AND EFA.ORGANIZATION_ID = ISNULL(?, EFA.ORGANIZATION_ID)"
                    + " AND EFA.FA_CATEGORY1 LIKE dbo.NVL(?, EFA.FA_CATEGORY1)"
                    + " AND (" + SyBaseSQLUtil.isNull() + " OR EFA.FA_CATEGORY2 LIKE ?)"
                    + " AND EFA.TAG_NUMBER LIKE dbo.NVL(?, EFA.TAG_NUMBER)"
                    + " AND EFA.MIS_TAG_NUMBER LIKE dbo.NVL(?, EFA.MIS_TAG_NUMBER)"
                    + " AND EFA.ASSETS_DESCRIPTION LIKE dbo.NVL(?, EFA.ASSETS_DESCRIPTION)"
                    + " AND (" + SyBaseSQLUtil.isNull() + " OR EFA.ASSIGNED_TO_NAME LIKE dbo.NVL(?, EFA.ASSIGNED_TO_NAME))"
                    + " AND EFA.DATE_PLACED_IN_SERVICE >= ISNULL(?, EFA.DATE_PLACED_IN_SERVICE)"
                    + " AND EFA.DATE_PLACED_IN_SERVICE <= ISNULL(?, EFA.DATE_PLACED_IN_SERVICE)"
                    + " AND EFA.ASSETS_CREATE_DATE >= ISNULL(?, EFA.ASSETS_CREATE_DATE)"
                    + " AND EFA.ASSETS_CREATE_DATE <= ISNULL(?, EFA.ASSETS_CREATE_DATE)"
                    + " AND EFA.DEPRECIATION_ACCOUNT LIKE dbo.NVL(?, EFA.DEPRECIATION_ACCOUNT)"
                    + " AND (" + SyBaseSQLUtil.isNull() + " OR EFA.PROJECT_NAME LIKE dbo.NVL(?, EFA.PROJECT_NAME))"
                    + " AND EFA.ASSETS_LOCATION_CODE LIKE dbo.NVL(?, EFA.ASSETS_LOCATION_CODE)";
            List sqlArgs = new ArrayList();
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getFaCategory1());
            sqlArgs.add(dto.getFaCategory2());
            sqlArgs.add(dto.getFaCategory2());
            sqlArgs.add(dto.getTagNumber());
            sqlArgs.add(dto.getMisTagNumber());
            sqlArgs.add(dto.getAssetsDescription());
            sqlArgs.add(dto.getAssignedToName());
            sqlArgs.add(dto.getAssignedToName());
            sqlArgs.add(dto.getStartDate());
            sqlArgs.add(dto.getSQLEndDate());

            sqlArgs.add(dto.getStartCreationDate());
            sqlArgs.add(dto.getSQLEndCreationDate());
            sqlArgs.add("%" + dto.getCostCenterCode() + "%");
            sqlArgs.add(dto.getProjectName());
            sqlArgs.add(dto.getProjectName());
            sqlArgs.add(dto.getAssetsLocationCode());
            if (!(userAccount.isProvinceUser() && userAccount.isSysAdmin())) {
                sqlStr += " AND EOCM.ORGANIZATION_ID = ?";
                sqlArgs.add(userAccount.getOrganizationId());
            }
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
        }
        return sqlModel;

    }


}
