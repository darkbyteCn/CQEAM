package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.newasset.dto.AmsMisDeptDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.StrUtil;

public class ItemVillageModel extends AMSSQLProducer {
    private String deptCodes = "";

    /**
     * 功能：构造函数
     *
     * @param userAccount  SfUserDTO
     * @param dtoParameter EtsItemInfoDTO
     */
    public ItemVillageModel(SfUserDTO userAccount, AmsAssetsAddressVDTO dtoParameter) {
        super(userAccount, dtoParameter);
        initDeptCodes();
    }

    /**
     * 功能：初始化当前用户有权限修改资产的所属部门信息
     */
    private void initDeptCodes() {
        deptCodes = "(";
        DTOSet depts = userAccount.getPriviDeptCodes();
        if (depts != null && !depts.isEmpty()) {
            AmsMisDeptDTO dept = null;
            for (int i = 0; i < depts.getSize(); i++) {
                dept = (AmsMisDeptDTO) depts.getDTO(i);
                deptCodes += "'" + dept.getDeptCode() + "', ";
            }
        }
        deptCodes += "'')";
    }

    /**
     * 功能：获取翻页查询SQL
     *
     * @return SQLModel
     */
    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        try {
            AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
            StringBuilder sb = new StringBuilder();
//            String sqlStr = 
            sb.append( " SELECT EOCM.COMPANY,\n");
                    sb.append("       EII.BARCODE,\n");
                    sb.append("       dbo.APP_GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY,\n");
                    sb.append("       ESI.ITEM_CODE,\n");
                    sb.append("       ESI.ITEM_NAME,\n");
                    sb.append("       ESI.ITEM_SPEC,\n");
                    sb.append("       EO.WORKORDER_OBJECT_CODE,\n");
                    sb.append("       EO.WORKORDER_OBJECT_NAME,\n");
                    sb.append("       dbo.APP_GET_FLEX_VALUE(EII.ITEM_STATUS, 'ITEM_STATUS') ITEM_STATUS_NAME,\n");
                    sb.append("       CASE EII.DISABLE_DATE WHEN NULL THEN '生效' ELSE '失效' END IS_ABATE,\n");
                    sb.append("       ESI.ITEM_UNIT,\n");
                    sb.append("       EII.ITEM_QTY,\n");
                    sb.append("       (CASE EII.ACTUAL_QTY WHEN 0 THEN NULL WHEN -1 THEN NULL ELSE EII.ACTUAL_QTY END) ACTUAL_QTY,\n");
                    sb.append("       AMD.DEPT_NAME RESPONSIBILITY_DEPT_NAME,\n");
                    sb.append("       AME.USER_NAME RESPONSIBILITY_USER_NAME,\n");
                    sb.append("       AMD2.DEPT_NAME MAINTAIN_DEPT,\n");
                    sb.append("       EII.MAINTAIN_USER,\n");
                    sb.append("       AMD3.DEPT_NAME SPECIALITY_DEPT,\n");
                    sb.append("       EII.OTHER_INFO,\n");
                    sb.append("       ACD.CONTENT_CODE,\n");
                    sb.append("       ACD.CONTENT_NAME,\n");
                    //sb.append("       EII.START_DATE,\n");
                    sb.append("       STR_REPLACE(CONVERT(VARCHAR, (CASE CHARINDEX('1900', EII.START_DATE) WHEN 8 THEN NULL ELSE EII.START_DATE END), 111), '/', '-') START_DATE,\n");
                    sb.append("       dbo.APP_GET_FLEX_VALUE( EII.FINANCE_PROP, 'FINANCE_PROP' ) FINANCE_PROP,\n");
//                    sb.append("       CASE EII.FINANCE_PROP WHEN 'DG_ASSETS' THEN '村通' ELSE '渠道' END FINANCE_PROP,\n");
                    sb.append("       EPPA.NAME PROJECT_NAME,\n");
                    sb.append("       AL.LOG_NET_ELE,\n");
                    sb.append("       AC.INVEST_CAT_NAME,\n");
                    sb.append("       AO.OPE_NAME,\n");
                    sb.append("       AN.LNE_NAME,\n");
                    sb.append("       AM.MANUFACTURER_NAME,\n");
                    sb.append("       EII.POWER,\n");
                    //sb.append("       dbo.APP_GET_FLEX_VALUE(EII.IS_SHARE, 'SHARE_STATUS') IS_SHARE,\n");
                    sb.append("       dbo.APP_GET_FLEX_VALUE(EII.SHARE_STATUS, 'SHARE_STATUS') IS_SHARE,\n");
                    sb.append("       dbo.APP_GET_FLEX_VALUE(EII.CONSTRUCT_STATUS, 'CONSTRUCT_STATUS') CONSTRUCT_STATUS,\n");
                    sb.append("       EII.PRICE,\n");
                    sb.append("       EII.TF_NET_ASSET_VALUE,\n");
                    sb.append("       EII.TF_DEPRN_COST,\n");
                    sb.append("       EII.TF_DEPRECIATION,\n");
                    sb.append("       EII.REMARK\n" );
            		sb.append("   FROM ETS_ITEM_INFO          EII,\n");
                    sb.append("       ETS_SYSTEM_ITEM        ESI,\n");
                    sb.append("       AMS_OBJECT_ADDRESS     AOA,\n");
                    sb.append("       ETS_OBJECT             EO,\n");
                    sb.append("       ETS_COUNTY             EC,\n");
                    sb.append("       ETS_OU_CITY_MAP        EOCM,\n");
                    sb.append("       AMS_MIS_DEPT           AMD,\n");
                    sb.append("       AMS_MIS_DEPT           AMD2,\n");
                    sb.append("       AMS_MIS_DEPT           AMD3,\n");
                    sb.append("       AMS_MIS_EMPLOYEE       AME,\n");
                    sb.append("       ETS_PA_PROJECTS_ALL    EPPA,\n");
                    sb.append("       AMS_LNE                AL,\n");
                    sb.append("       AMS_CEX                AC,\n");
                    sb.append("       AMS_OPE                AO,\n");
                    sb.append("       AMS_NLE                AN,\n");
                    sb.append("       AMS_MANUFACTURER       AM,\n");
                    sb.append("       AMS_CONTENT_DIC        ACD\n");
                    sb.append(" WHERE EII.ITEM_CODE = ESI.ITEM_CODE\n" );
                    sb.append("   AND EII.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" );
                    sb.append("   AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" );
                    sb.append("   AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" );
                    //TODO 0906
                    sb.append("   AND EO.COUNTY_CODE *= EC.COUNTY_CODE\n" );
                    sb.append("   AND EOCM.COMPANY_CODE *= EC.COMPANY_CODE \n" );
                    
                    if( !StrUtil.isEmpty( dto.getEmployeeNumber() ) || !StrUtil.isEmpty( dto.getResponsibilityUserName() ) ){
                    	sb.append("   AND EII.RESPONSIBILITY_USER = AME.EMPLOYEE_ID\n" );
                    }else{
                    	sb.append("   AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID\n" );
                    }
//                    sb.append("   AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID\n" );
                    sb.append("   AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE\n" );
                    sb.append("   AND EII.MAINTAIN_DEPT *= AMD2.DEPT_CODE\n" );
                    sb.append("   AND EII.SPECIALITY_DEPT *= AMD3.DEPT_CODE\n" );
                    sb.append("   AND EII.PROJECTID *= EPPA.PROJECT_ID\n" );
                    sb.append("   AND EII.LNE_ID *= AL.AMS_LNE_ID\n" );
                    sb.append("   AND EII.CEX_ID *= AC.AMS_CEX_ID\n" );
                    sb.append("   AND EII.OPE_ID *= AO.AMS_OPE_ID\n" );
                    sb.append("   AND EII.NLE_ID *= AN.AMS_LNE_ID\n" );
                    sb.append("   AND EII.MANUFACTURER_ID *= AM.MANUFACTURER_ID\n" );
                    sb.append("   AND EII.CONTENT_CODE *= ACD.CONTENT_CODE\n" );
                    sb.append("   AND EII.ATTRIBUTE1 = 'CT'\n" );
                    //sb.append("   AND ( " ); SyBaseSQLUtil.isNull() ); "  OR EOCM.ORGANIZATION_ID = ?)\n" );
                    //sb.append("   AND ( CONVERT(VARCHAR, ?) = '' OR EOCM.ORGANIZATION_ID = ? )\n" );
                    //sb.append("   AND EII.BARCODE LIKE dbo.NVL(?, EII.BARCODE)\n" );
                    sb.append("   AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.BARCODE LIKE dbo.NVL( ?, EII.BARCODE))\n" );
                    sb.append("   AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_NAME LIKE ?)\n" );
                    sb.append("   AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?)\n" );
                    //sb.append("   AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.START_DATE > = ?)\n" );
                    //sb.append("   AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.START_DATE < = ?)\n" );
                    sb.append("   AND ( " + SyBaseSQLUtil.isNull() + "  OR CONVERT(VARCHAR,EII.START_DATE) > = ?)\n" );
                    sb.append("   AND ( " + SyBaseSQLUtil.isNull() + "  OR CONVERT(VARCHAR,EII.START_DATE) < = ?)\n" );
                    sb.append("   AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.RESPONSIBILITY_DEPT = ?)\n" );
                    
                    if( !StrUtil.isEmpty( dto.getResponsibilityUserName() ) ){
                    	sb.append("   AND AME.USER_NAME LIKE ?\n" );
                    }
//                    sb.append("   AND ( " + SyBaseSQLUtil.isNull() + "  OR AME.EMPLOYEE_NUMBER = ?)\n" );
                    sb.append("   AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.WORKORDER_OBJECT_NAME LIKE ?)\n" );
                    sb.append("   AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.SPECIALITY_DEPT = ?)\n" );
                    sb.append("   AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.ITEM_STATUS = ?)\n" );
                    //sb.append("   AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.CREATION_DATE > = ?)\n" );
                    //sb.append("   AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.CREATION_DATE < = ?)\n" );
                    //sb.append("   AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.DISABLE_DATE > = ?)\n" );
                    //sb.append("   AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.DISABLE_DATE < = ?)\n" );
                    sb.append("   AND ( " + SyBaseSQLUtil.isNull() + "  OR CONVERT(VARCHAR,EII.CREATION_DATE) > = ?)\n" );
                    sb.append("   AND ( " + SyBaseSQLUtil.isNull() + "  OR CONVERT(VARCHAR,EII.CREATION_DATE) < = ?)\n" );
                    sb.append("   AND ( " + SyBaseSQLUtil.isNull() + "  OR CONVERT(VARCHAR,EII.DISABLE_DATE) > = ?)\n" );
                    sb.append("   AND ( " + SyBaseSQLUtil.isNull() + "  OR CONVERT(VARCHAR,EII.DISABLE_DATE) < = ?)\n" );
                    sb.append("   AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.MAINTAIN_USER LIKE ?)\n" );
                    sb.append("   AND ( " + SyBaseSQLUtil.isNull() + "  OR ACD.CONTENT_NAME LIKE ?)\n" );
                    sb.append("   AND ( " + SyBaseSQLUtil.isNull() + "  OR AM.MANUFACTURER_NAME LIKE ?)\n" );
                    //sb.append("   AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.ACTUAL_QTY LIKE ?)\n" );
                    sb.append("   AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.FINANCE_PROP = ?)\n" );
                    sb.append("   AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.IS_SHARE = ?)\n" );
                    sb.append("   AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.CONSTRUCT_STATUS = ?)");
            sqlArgs.add(dto.getBarcode());
            sqlArgs.add(dto.getBarcode());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getStartDate().toString());
            sqlArgs.add(dto.getStartDate().toString());
            sqlArgs.add(dto.getEndDate().toString());
            sqlArgs.add(dto.getEndDate().toString());
            sqlArgs.add(dto.getResponsibilityDept());
            sqlArgs.add(dto.getResponsibilityDept());
            
            if( !StrUtil.isEmpty( dto.getResponsibilityUserName() )){
            	sqlArgs.add(dto.getResponsibilityUserName());
            }
            
            sqlArgs.add(dto.getWorkorderObjectName());
            sqlArgs.add(dto.getWorkorderObjectName());
            sqlArgs.add(dto.getSpecialityDept());
            sqlArgs.add(dto.getSpecialityDept());
            sqlArgs.add(dto.getItemStatus());
            sqlArgs.add(dto.getItemStatus());
            sqlArgs.add(dto.getStartCreationDate().toString());
            sqlArgs.add(dto.getStartCreationDate().toString());
            sqlArgs.add(dto.getEndCreationDate().toString());
            sqlArgs.add(dto.getEndCreationDate().toString());
            sqlArgs.add(dto.getStartDisableDate().toString());
            sqlArgs.add(dto.getStartDisableDate().toString());
            sqlArgs.add(dto.getEndDisableDate().toString());
            sqlArgs.add(dto.getEndDisableDate().toString());
            sqlArgs.add(dto.getMaintainUser());
            sqlArgs.add(dto.getMaintainUser());
            sqlArgs.add(dto.getContentName());
            sqlArgs.add(dto.getContentName());
            sqlArgs.add(dto.getManufacturerName());
            sqlArgs.add(dto.getManufacturerName());
            //sqlArgs.add(dto.getActualQty());
            //sqlArgs.add(dto.getActualQty());
            sqlArgs.add(dto.getTfType());
            sqlArgs.add(dto.getTfType());
            sqlArgs.add(dto.getShare());
            sqlArgs.add(dto.getShare());
            sqlArgs.add(dto.getConstructStatus());
            sqlArgs.add(dto.getConstructStatus());
            int a = dto.getActualQty();
            if (dto.getActualQty() != 0) {
            	sb.append(" AND  ( ? = 0 OR CONVERT(VARCHAR, EII.ACTUAL_QTY) LIKE '%' || CONVERT(VARCHAR, ?) || '%' )\n" ) ;
            	sqlArgs.add(dto.getActualQty());
                sqlArgs.add(dto.getActualQty());
            }
            
            if (!dto.getIsAbate().equals("")) {
                if (dto.getIsAbate().equals("Y")) {
                	sb.append("  AND  " + SyBaseSQLUtil.isNotNull("EII.DISABLE_DATE") + "  " );
                } else {
                    //sqlStr += " AND EII.DISABLE_DATE " + SyBaseSQLUtil.isNull() + "  ";
                	sb.append("  AND EII.DISABLE_DATE IS NULL " );
                }
            }
            if (userAccount.isProvAssetsManager()) {
            } else if (userAccount.isComAssetsManager()) {
            	sb.append(" AND EII.ORGANIZATION_ID = ? "  ) ;
                sqlArgs.add(userAccount.getOrganizationId());
            } else if (userAccount.isDptAssetsManager()) {
            	//测试用先取消
            	sb.append( " AND EII.RESPONSIBILITY_DEPT IN " + deptCodes );
            } else {
                String empliyeeNumber = userAccount.getEmployeeNumber();
                if( !StrUtil.isEmpty( empliyeeNumber ) ){
                	sb.append(" AND EII.RESPONSIBILITY_USER = (SELECT AME.EMPLOYEE_ID FROM AMS_MIS_EMPLOYEE AME WHERE AME.EMPLOYEE_NUMBER='" + empliyeeNumber + "')" ) ;
                }
            }
            int ou = dto.getOrganizationId();
            if (ou != -1 && ou != 0) {
            	sb.append(" AND ( CONVERT(VARCHAR, ?) = '' OR EOCM.ORGANIZATION_ID = ? )\n" ) ;
                sqlArgs.add(dto.getOrganizationId());
                sqlArgs.add(dto.getOrganizationId());
            }
            sqlModel.setSqlStr( sb.toString() );
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
        }
        return sqlModel;
    }

    /**
     * 功能：获取数据更新SQL
     *
     * @return SQLModel
     * @throws com.sino.base.exception.SQLModelException
     *
     */
    public SQLModel getDataUpdateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
        try {
        String sqlStr = "UPDATE ETS_ITEM_INFO \n" +
                "SET    ADDRESS_ID          = ISNULL(LTRIM(?), ADDRESS_ID),\n" +
                "       RESPONSIBILITY_DEPT = ISNULL(LTRIM(?), RESPONSIBILITY_DEPT),\n" +
                "       RESPONSIBILITY_USER = ISNULL(LTRIM(?), RESPONSIBILITY_USER),\n" +
                "       SPECIALITY_DEPT     = ISNULL(LTRIM(?), SPECIALITY_DEPT),\n" +
                "       MAINTAIN_USER       = ISNULL(LTRIM(?), MAINTAIN_USER),\n" +
                "       ITEM_STATUS         = ISNULL(LTRIM(?), ITEM_STATUS),\n" +
                "       DISABLE_DATE        = (CASE LTRIM(?) WHEN 'Y' THEN GETDATE() WHEN 'N' THEN NULL ELSE NULL END),\n" +
                "       LAST_UPDATE_DATE    = GETDATE(),\n" +
                "       LAST_UPDATE_BY      = ?,\n" +
                "       REMARK              = ISNULL(LTRIM(?), REMARK),\n" +
                "       PROJECTID           = ISNULL(LTRIM(?), PROJECTID),\n" +
                "       LNE_ID              = ISNULL(LTRIM(?), LNE_ID),\n" +
                "       CEX_ID              = ISNULL(LTRIM(?), CEX_ID),\n" +
                "       OPE_ID              = ISNULL(LTRIM(?), OPE_ID),\n" +
                "       NLE_ID              = ISNULL(LTRIM(?), NLE_ID),\n" +
                "       MANUFACTURER_ID     = ISNULL(LTRIM(?), MANUFACTURER_ID),\n" +
                "       POWER               = ISNULL(LTRIM(?), POWER),\n" +
                "       IS_SHARE            = ISNULL(LTRIM(?), IS_SHARE),\n" +
                "       CONSTRUCT_STATUS    = ISNULL(LTRIM(?), CONSTRUCT_STATUS),\n" +
                "       CONTENT_CODE        = ISNULL(LTRIM(?), CONTENT_CODE),\n" +
                "       CONTENT_NAME        = ISNULL(LTRIM(?), CONTENT_NAME),\n" +
                "       ACTUAL_QTY          = ISNULL(?, ACTUAL_QTY),\n" +
                "       OTHER_INFO          = ISNULL(LTRIM(?), OTHER_INFO),\n" +
                "       START_DATE          = ISNULL(CONVERT(DATETIME,LTRIM(CONVERT(VARCHAR, ?))), START_DATE)\n" +
                "WHERE  BARCODE = ?";
        List sqlArgs = new ArrayList();
        sqlArgs.add(dto.getAddressId());
        sqlArgs.add(dto.getResponsibilityDept());
        sqlArgs.add(dto.getResponsibilityUser());
        sqlArgs.add(dto.getSpecialityDept());
        sqlArgs.add(dto.getMaintainUser());
        sqlArgs.add(dto.getItemStatus());
        sqlArgs.add(dto.getIsAbate());
        sqlArgs.add(userAccount.getUserId());
        sqlArgs.add(dto.getRemark());
        sqlArgs.add(dto.getProjectid());
        sqlArgs.add(dto.getLneId());
        sqlArgs.add(dto.getCexId());
        sqlArgs.add(dto.getOpeId());
        sqlArgs.add(dto.getNleId());
        sqlArgs.add(dto.getManufacturerId());
        sqlArgs.add(dto.getPower());
        sqlArgs.add(dto.getShare());
        sqlArgs.add(dto.getConstructStatus());
        sqlArgs.add(dto.getContentCode());
        sqlArgs.add(dto.getContentName());
        sqlArgs.add(dto.getActualQty());
        sqlArgs.add(dto.getOtherInfo());
        sqlArgs.add(dto.getStartDate());

        sqlArgs.add(dto.getBarcode());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        } catch (CalendarException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
        }
        return sqlModel;
    }


    /**
     * 功能：返回数据详细信息的SQLModel
     * <B>默认为空实现。可由具体应用选择是否需要实现。继承类需要用dtoParameter构造SQLModel。</B>
     *
     * @return SQLModel
     */
    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
        String sqlStr = "SELECT EII.BARCODE,\n" +
                "       ESI.ITEM_CODE,\n" +
                "       ESI.ITEM_CATEGORY,\n" +
                "       dbo.APP_GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY_NAME,\n" +
                "       ESI.ITEM_NAME,\n" +
                "       ESI.ITEM_SPEC,\n" +
                "       ESI.YEARS,\n" +
                "       ESI.ITEM_UNIT,\n" +
                "       EII.START_DATE,\n" +
                "       AME.USER_NAME RESPONSIBILITY_USER_NAME,\n" +
                "       AME.EMPLOYEE_NUMBER,\n" +
                "       AMD.DEPT_NAME RESPONSIBILITY_DEPT_NAME,\n" +
                "       EMPV.VENDOR_NAME,\n" +
                "       EMPV.SEGMENT1 VENDOR_NUMBER,\n" +
                "       EII.FINANCE_PROP,\n" +
                "       dbo.APP_GET_FLEX_VALUE(EII.FINANCE_PROP, 'FINANCE_PROP') FINANCE_PROP_VALUE,\n" +
                "       EII.RESPONSIBILITY_USER,\n" +
                "       EII.RESPONSIBILITY_DEPT,\n" +
                "       EII.MAINTAIN_USER,\n" +
                "       EII.MAINTAIN_DEPT,\n" +
                "       EII.ADDRESS_ID,\n" +
                "       EOCM.COMPANY_CODE,\n" +
                "       EOCM.COMPANY,\n" +
                "       EII.SPECIALITY_DEPT\n" +
                "FROM   ETS_ITEM_INFO          EII,\n" +
                "       ETS_SYSTEM_ITEM        ESI,\n" +
                "       ETS_OU_CITY_MAP        EOCM,\n" +
                "       AMS_MIS_DEPT           AMD,\n" +
                "       AMS_MIS_EMPLOYEE       AME,\n" +
                "       ETS_MIS_PO_VENDORS     EMPV\n" +
                "WHERE  EII.ITEM_CODE = ESI.ITEM_CODE\n" +
                "       AND ESI.VENDOR_ID *= EMPV.VENDOR_ID\n" +
                "       AND EII.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
                "       AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID\n" +
                "       AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE\n" +
                "       AND EII.BARCODE = ?";
        List sqlArgs = new ArrayList();
        sqlArgs.add(dto.getBarcode());
        sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);

		return sqlModel;
	}

    public SQLModel getCheckItemStatusModel(String barcode, String itemStatus) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
        String sqlStr = "SELECT EII.ITEM_STATUS\n" +
                "  FROM ETS_ITEM_INFO EII\n" +
                " WHERE EII.BARCODE = ?\n" +
                "   AND EII.ITEM_STATUS = ?";
        List sqlArgs = new ArrayList();
        sqlArgs.add(barcode);
        sqlArgs.add(itemStatus);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
