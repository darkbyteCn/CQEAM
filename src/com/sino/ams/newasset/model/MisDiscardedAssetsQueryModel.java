package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.security.dto.ServletConfigDTO;

public class MisDiscardedAssetsQueryModel extends AMSSQLProducer {

    public MisDiscardedAssetsQueryModel(SfUserDTO userAccount, AmsAssetsAddressVDTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    public void setServletConfig(ServletConfigDTO servletConfig) {
        super.setServletConfig(servletConfig);    //To change body of overridden methods use File | Settings | File Templates.
    }

    /**
     * 功能：返回页面翻页查询"MIS资产报废"时所需要的SQLModel
     * @return SQLModel
     * @throws SQLModelException
     */
    public SQLModel getPageQueryModel() throws SQLModelException {
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
                    + " EFA.DATE_RETIRED,"
                    + " EFA.DATE_EFFECTIVE,"
                    + " EFA.COST_RETIRED,"
                    + " EFA.RETIREMENT_TYPE_CODE,"
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
                    + " (SELECT "
                    + " AAV.ACCOUNT_NAME DEPRECIATION_ACCOUNT_NAME"
                    + " FROM"
                    + " AMS_ACCOUNT_V AAV"
                    + " WHERE"
                    + " AAV.ACCOUNT_CODE_1 = SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 1, 4)"
                    + " AND AAV.ACCOUNT_CODE_2 = SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6)"
                    + " AND AAV.ACCOUNT_CODE_3 = SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 13, 10)"
                    + " AND AAV.ACCOUNT_CODE_4 = SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 24, 6)"
                    + " AND AAV.ACCOUNT_CODE_5 = SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 31, 8)"
                    + " AND AAV.ACCOUNT_CODE_6 = SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 40, 4)"
                    + " AND AAV.ACCOUNT_CODE_7 = SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 45, 6))"
                    + " DEPRECIATION_ACCOUNT_NAME"
                    + " FROM"
                    + " ETS_FA_ASSETS       EFA,"
                    + " ETS_PA_PROJECTS_ALL EPPA,"
                    + " ETS_OU_CITY_MAP     EOCM"
                    + " WHERE  "
                    + " EFA.PROJECT_ID *= EPPA.PROJECT_ID"
                    + " AND EFA.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
                    + " AND EFA.IS_RETIREMENTS = 1 "
                    + " AND EFA.ORGANIZATION_ID = ISNULL(?, EFA.ORGANIZATION_ID)"
                    + " AND EFA.FA_CATEGORY1 LIKE dbo.NVL(?, EFA.FA_CATEGORY1)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.FA_CATEGORY2 LIKE ?)"
                    + " AND EFA.TAG_NUMBER LIKE dbo.NVL(?, EFA.TAG_NUMBER)"
                    + " AND EFA.MIS_TAG_NUMBER LIKE dbo.NVL(?, EFA.MIS_TAG_NUMBER)"
                    + " AND EFA.ASSETS_DESCRIPTION LIKE dbo.NVL(?, EFA.ASSETS_DESCRIPTION)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.ASSIGNED_TO_NAME LIKE dbo.NVL(?, EFA.ASSIGNED_TO_NAME))"
                    + " AND EFA.DATE_PLACED_IN_SERVICE >= ISNULL(?, EFA.DATE_PLACED_IN_SERVICE)"
                    + " AND EFA.DATE_PLACED_IN_SERVICE <= ISNULL(?, EFA.DATE_PLACED_IN_SERVICE)"
                    + " AND EFA.ASSETS_CREATE_DATE >= ISNULL(?, EFA.ASSETS_CREATE_DATE)"
                    + " AND EFA.ASSETS_CREATE_DATE <= ISNULL(?, EFA.ASSETS_CREATE_DATE)"
                    
                    + " AND (  " + SyBaseSQLUtil.isNull() + "  OR EFA.RETIREMENT_TYPE_CODE LIKE ?)"
                    + " AND (  " + SyBaseSQLUtil.isNull() + "  OR EFA.COST_RETIRED LIKE ?)"
                    + " AND (  " + SyBaseSQLUtil.isNull() + "  OR EFA.DATE_EFFECTIVE >= ?)"                 
                    + " AND (  " + SyBaseSQLUtil.isNull() + "  OR EFA.DATE_EFFECTIVE <= ?)"
                    + " AND (  " + SyBaseSQLUtil.isNull() + "  OR EFA.DATE_RETIRED >= ?)"
                    + " AND (  " + SyBaseSQLUtil.isNull() + "  OR EFA.DATE_RETIRED <= ?)"
                    
                    + " AND EFA.DEPRECIATION_ACCOUNT LIKE dbo.NVL(?, EFA.DEPRECIATION_ACCOUNT)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.PROJECT_NAME LIKE dbo.NVL(?, EFA.PROJECT_NAME))"
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
            
            sqlArgs.add(dto.getRetirementTypeCode());
            sqlArgs.add(dto.getRetirementTypeCode());
            sqlArgs.add(dto.getCostRetired());
            sqlArgs.add(dto.getCostRetired());
            
            sqlArgs.add(dto.getDateEffectiveStart());
            sqlArgs.add(dto.getDateEffectiveStart());
            sqlArgs.add(dto.getDateEffectiveEnd());
            sqlArgs.add(dto.getDateEffectiveEnd());
            sqlArgs.add(dto.getDateRetiredStart());
            sqlArgs.add(dto.getDateRetiredStart());
            sqlArgs.add(dto.getDateRetiredEnd());
            sqlArgs.add(dto.getDateRetiredEnd());
            
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
