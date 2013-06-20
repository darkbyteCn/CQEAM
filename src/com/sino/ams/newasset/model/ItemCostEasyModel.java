package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.newasset.dto.AmsMisDeptDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.StrUtil;

public class ItemCostEasyModel extends AMSSQLProducer {
    private String deptCodes = "";

    /**
     * 功能：构造函数
     *
     * @param userAccount  SfUserDTO
     * @param dtoParameter EtsItemInfoDTO
     */
    public ItemCostEasyModel(SfUserDTO userAccount, AmsAssetsAddressVDTO dtoParameter) {
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
            String sqlStr = "SELECT EOCM.COMPANY,\n" +
                    "       EII.BARCODE,\n" +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    //"       AMS_PUB_PKG.GET_FLEX_VALUE(EII.ITEM_STATUS, 'ITEM_STATUS') ITEM_STATUS_NAME,\n" +
                    "		dbo.APP_GET_FLEX_VALUE(EII.ITEM_STATUS, 'ITEM_STATUS') ITEM_STATUS_NAME,\n" +
                    "       CASE EII.DISABLE_DATE WHEN NULL THEN '生效' ELSE '失效' END IS_ABATE,\n" +
                    "       ESI.ITEM_UNIT,\n" +
                    "       EII.START_DATE,\n" +
                    "       AMD.DEPT_NAME RESPONSIBILITY_DEPT_NAME,\n" +
                    "       EO.WORKORDER_OBJECT_CODE,\n" +
                    "       EO.WORKORDER_OBJECT_NAME,\n" +
                    "       AM.MANUFACTURER_CODE,\n" +
                    "       AM.MANUFACTURER_NAME,\n" +
                    "       EII.MAINTAIN_USER,\n" +
                    "       AMD2.DEPT_NAME SPECIALITY_DEPT,\n" +
                    "       AME.USER_NAME RESPONSIBILITY_USER_NAME,\n" +
                    "       AME2.USER_NAME USERNAME,\n" +
                    "       ACD.CONTENT_CODE,\n" +
                    "       ACD.CONTENT_NAME,\n" +
                    "       EII.REMARK,\n" +
                    //"       AMS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY,\n" +
                    "		dbo.APP_GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY,\n" +
                    "       ESI.ITEM_CODE,\n" +
                    "       EII.ITEM_QTY,\n" +
                    "       EII.PRICE,\n" +
                    "       CASE EII.IS_TD WHEN 'Y' THEN '是' ELSE '否' END IS_TD\n" +
                    "  FROM ETS_ITEM_INFO      EII,\n" +
                    "       ETS_SYSTEM_ITEM    ESI,\n" +
                    "       AMS_OBJECT_ADDRESS AOA,\n" +
                    "       ETS_OBJECT         EO,\n" +
                    "       ETS_OU_CITY_MAP    EOCM,\n" +
                    "       AMS_MIS_DEPT       AMD,\n" +
                    "       AMS_MIS_EMPLOYEE   AME,\n" +
                    "       AMS_MANUFACTURER   AM,\n" +
                    "       AMS_MIS_EMPLOYEE   AME2,\n" +
                    "       AMS_MIS_DEPT       AMD2,\n" +
                    "       AMS_DZYH_DIC       ACD\n" +
                    " WHERE EII.ITEM_CODE = ESI.ITEM_CODE\n" +
                    "   AND EII.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
                    
                    "   AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                    "   AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" ;
                    if (!dto.getEmployeeNumber().trim().equals("")) {
                    	sqlStr += "   AND EII.RESPONSIBILITY_USER = AME.EMPLOYEE_ID\n" ;
                    } else {
                    	sqlStr += "   AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID\n" ;
                    }
                    sqlStr += 
                    "   AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE\n" +
                    "   AND EII.SPECIALITY_USER *= AME2.EMPLOYEE_ID \n" +
                    "   AND EII.SPECIALITY_DEPT *= AMD2.DEPT_CODE\n" ;
                     
                    if( StrUtil.isEmpty( dto.getManufacturerName() ) ){
                    	sqlStr += "   AND EII.MANUFACTURER_ID *= AM.MANUFACTURER_ID\n";
                    }else{
                    	sqlStr += "   AND EII.MANUFACTURER_ID = AM.MANUFACTURER_ID\n";
                    }
                    if( StrUtil.isEmpty( dto.getContentName() ) ){
                    	sqlStr += "   AND EII.CONTENT_CODE *= ACD.CONTENT_CODE\n";
                    }else{
                    	sqlStr += "   AND EII.CONTENT_CODE = ACD.CONTENT_CODE\n";
                    } 
                    
                    sqlStr += "   AND EII.FINANCE_PROP = 'DH_ASSETS'\n" + 
                    //"   AND ( CONVERT(VARCHAR, ?) = '' OR EOCM.ORGANIZATION_ID = ? )\n" +
                    //"   AND EII.BARCODE LIKE dbo.NVL(?, EII.BARCODE)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.BARCODE LIKE dbo.NVL( ?, EII.BARCODE))\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_NAME LIKE ?)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR CONVERT(VARCHAR,EII.START_DATE) > = ?)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR CONVERT(VARCHAR,EII.START_DATE) < = ?)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.RESPONSIBILITY_DEPT = ?)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR AME.EMPLOYEE_NUMBER = ?)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.WORKORDER_OBJECT_NAME LIKE ?)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.SPECIALITY_DEPT = ?)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.ITEM_STATUS = ?)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR AM.MANUFACTURER_NAME LIKE ?)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR CONVERT(VARCHAR,EII.CREATION_DATE) > = ?)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR CONVERT(VARCHAR,EII.CREATION_DATE) < = ?)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR CONVERT(VARCHAR,EII.DISABLE_DATE) > = ?)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR CONVERT(VARCHAR,EII.DISABLE_DATE) < = ?)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.MAINTAIN_USER LIKE ?)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.SPECIALITY_USER =  ? )\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR ACD.CONTENT_NAME LIKE ?)";
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
            sqlArgs.add(dto.getEmployeeNumber());
            sqlArgs.add(dto.getEmployeeNumber());
            sqlArgs.add(dto.getWorkorderObjectName());
            sqlArgs.add(dto.getWorkorderObjectName());
            sqlArgs.add(dto.getSpecialityDept());
            sqlArgs.add(dto.getSpecialityDept());
            sqlArgs.add(dto.getItemStatus());
            sqlArgs.add(dto.getItemStatus());
            sqlArgs.add(dto.getManufacturerName());
            sqlArgs.add(dto.getManufacturerName());
            sqlArgs.add(dto.getStartCreationDate().toString());
            sqlArgs.add(dto.getStartCreationDate().toString());
            sqlArgs.add(dto.getEndCreationDate().toString());
            sqlArgs.add(dto.getEndCreationDate().toString());
            sqlArgs.add(dto.getStartDisableDate().toString());
            sqlArgs.add(dto.getStartDisableDate().toString());
            sqlArgs.add(dto.getEndDisableDate().toString());
            sqlArgs.add(dto.getEndDisableDate().toString());
            sqlArgs.add(dto.getMaintainUserName());
            sqlArgs.add(dto.getMaintainUserName());
            sqlArgs.add(dto.getSpecialityUser());
            sqlArgs.add(dto.getSpecialityUser());
            sqlArgs.add(dto.getContentName());
            sqlArgs.add(dto.getContentName());
            if (dto.getAttribute1().equals(AssetsDictConstant.STATUS_NO)) {
                //sqlStr += " AND EII.ATTRIBUTE " + SyBaseSQLUtil.isNull() + " ";
            	sqlStr += " AND EII.ATTRIBUTE1 IS NULL ";
            } else {
                sqlStr += " AND EII.ATTRIBUTE1 = ?";
                sqlArgs.add(dto.getAttribute1());
            }
            if (!dto.getIsAbate().equals("")) {
                if (dto.getIsAbate().equals("Y")) {
                    sqlStr += " AND  " + SyBaseSQLUtil.isNotNull("EII.DISABLE_DATE") + "  ";
                } else {
                    //sqlStr += " AND EII.DISABLE_DATE IS NULL ";
                	sqlStr += " AND (EII.DISABLE_DATE IS NULL OR EII.DISABLE_DATE = '')\n" ;
                }
            }
            if (userAccount.isProvAssetsManager()) {
            } else if (userAccount.isComAssetsManager()) {
                sqlStr += " AND EII.ORGANIZATION_ID = ? ";
                sqlArgs.add(userAccount.getOrganizationId());
            } else if (userAccount.isDptAssetsManager()) {
            	//测试用先取消
                sqlStr += " AND EII.RESPONSIBILITY_DEPT IN " + deptCodes;
            } else {
                String empliyeeNumber = userAccount.getEmployeeNumber();
                if( !StrUtil.isEmpty( empliyeeNumber )){
                	 sqlStr += " AND EII.RESPONSIBILITY_USER = (SELECT AME.EMPLOYEE_ID FROM AMS_MIS_EMPLOYEE AME WHERE AME.EMPLOYEE_NUMBER='" + empliyeeNumber + "')";
                }
               
            }
            int ou = dto.getOrganizationId();
            if (ou != -1 && ou != 0) {
            	sqlStr += "   AND ( CONVERT(VARCHAR, ?) = '' OR EOCM.ORGANIZATION_ID = ? )\n" ;
            	//sqlStr += " AND (" + SyBaseSQLUtil.nullIntParam() + " OR EII.ORGANIZATION_ID = ?)\n";
                //sqlArgs.add(dto.getOrganizationId());
                sqlArgs.add(dto.getOrganizationId());
                sqlArgs.add(dto.getOrganizationId());
            }
            sqlModel.setSqlStr(sqlStr);
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
                "   SET ADDRESS_ID          = ISNULL(LTRIM(?), ADDRESS_ID),\n" +
                "       RESPONSIBILITY_DEPT = ISNULL(LTRIM(?), RESPONSIBILITY_DEPT),\n" +
                "       MAINTAIN_USER       = ISNULL(LTRIM(?), MAINTAIN_USER),\n" +
                "       SPECIALITY_DEPT     = ISNULL(LTRIM(?), SPECIALITY_DEPT),\n" +
                "       DISABLE_DATE        = (CASE LTRIM(?) WHEN 'Y' THEN GETDATE() WHEN 'N' THEN NULL ELSE NULL END), \n" +
                "       LAST_UPDATE_DATE    = GETDATE(),\n" +
                "       LAST_UPDATE_BY      = ? ,\n" +
                "       IS_TD               = ISNULL(LTRIM(?), IS_TD),\n" +
                "       REMARK              = ISNULL(LTRIM(?), REMARK),\n" +
                "       MANUFACTURER_ID     = ISNULL(LTRIM(?), MANUFACTURER_ID),\n" +
                "       SPECIALITY_USER     = ISNULL( ?, SPECIALITY_USER),\n" +
                "       ITEM_STATUS         = ISNULL(LTRIM(?), ITEM_STATUS),\n" +
                "       CONTENT_CODE        = ISNULL(LTRIM(?), CONTENT_CODE),\n" +
                "       CONTENT_NAME        = ISNULL(LTRIM(?), CONTENT_NAME),\n" +
                "       START_DATE          = ISNULL(CONVERT(DATETIME,LTRIM(CONVERT(VARCHAR, ?))), START_DATE)\n" +
                " WHERE BARCODE = ?";
        List sqlArgs = new ArrayList();
        sqlArgs.add(dto.getAddressId());
        sqlArgs.add(dto.getResponsibilityDept());
        sqlArgs.add(dto.getMaintainUser());
        sqlArgs.add(dto.getSpecialityDept());
        sqlArgs.add(dto.getIsAbate());
        sqlArgs.add(userAccount.getUserId());
        sqlArgs.add(dto.getIsTD());
        sqlArgs.add(dto.getRemark());
        sqlArgs.add(dto.getManufacturerId());
        sqlArgs.add(dto.getSpecialityUser());
        sqlArgs.add(dto.getItemStatus());
        sqlArgs.add(dto.getContentCode());
        sqlArgs.add(dto.getContentName());
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
                //"       AMS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY_NAME,\n" +
                "		dbo.APP_GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY_NAME,\n" +
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
                //"       AMS_PUB_PKG.GET_FLEX_VALUE(EII.FINANCE_PROP, 'FINANCE_PROP') FINANCE_PROP_VALUE,\n" +
                "		dbo.APP_GET_FLEX_VALUE(EII.FINANCE_PROP, 'FINANCE_PROP') FINANCE_PROP_VALUE,\n" +
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
                "       ETS_SYSITEM_DISTRIBUTE ESD,\n" +
                "       ETS_OU_CITY_MAP        EOCM,\n" +
                "       AMS_MIS_DEPT           AMD,\n" +
                "       AMS_MIS_EMPLOYEE       AME,\n" +
                "       ETS_MIS_PO_VENDORS     EMPV\n" +
                "WHERE  EII.ITEM_CODE = ESI.ITEM_CODE\n" +
                "       AND ESI.ITEM_CODE = ESD.ITEM_CODE\n" +
                "       AND ESI.VENDOR_ID *= EMPV.VENDOR_ID\n" +
                "       AND EII.ORGANIZATION_ID = ESD.ORGANIZATION_ID\n" +
                "       AND ESD.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
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
