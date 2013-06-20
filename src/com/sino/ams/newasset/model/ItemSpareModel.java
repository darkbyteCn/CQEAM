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

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-6-10
 * Time: 22:42:57
 * To change this template use File | Settings | File Templates.
 */
@SuppressWarnings("unchecked")
public class ItemSpareModel extends AMSSQLProducer {
    private String deptCodes = "";

    public ItemSpareModel(SfUserDTO userAccount, AmsAssetsAddressVDTO dtoParameter) {
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
                    "       dbo.APP_GET_FLEX_VALUE(EII.ITEM_STATUS, 'ITEM_STATUS') ITEM_STATUS_NAME,\n" +
                    "		(CASE EII.DISABLE_DATE WHEN NULL THEN '否' ELSE '是' END) IS_ABATE,\n" +
                    "       ESI.ITEM_UNIT,\n" +
                    "       EII.START_DATE,\n" +
                    "       AMD.DEPT_NAME RESPONSIBILITY_DEPT_NAME,\n" +
                    "       EO.WORKORDER_OBJECT_CODE,\n" +
                    "       EO.WORKORDER_OBJECT_NAME,\n" +
                    "       AM.MANUFACTURER_CODE,\n" +
                    "       AM.MANUFACTURER_NAME,\n" +
                    "       EII.MAINTAIN_USER,\n" +
                    "       AMD2.DEPT_NAME SPECIALITY_DEPT,\n" +
                    "       AME2.USER_NAME USERNAME,\n" +
                    "       ACD.CONTENT_CODE,\n" +
                    "       ACD.CONTENT_NAME,\n" +
                    "       EII.REMARK,\n" +
                    "       dbo.APP_GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY,\n" +
                    "       ESI.ITEM_CODE,\n" +
                    "       EII.ITEM_QTY,\n" +
                    "       EII.PRICE,\n" +
                    "       (CASE EII.IS_TD WHEN 'Y' THEN '是' ELSE '否' END) IS_TD "+
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
                    "       AMS_CONTENT_DIC    ACD\n" +
                    " WHERE EII.ITEM_CODE = ESI.ITEM_CODE\n" +
                    "   AND EII.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
                    "   AND EII.MANUFACTURER_ID *= AM.MANUFACTURER_ID \n" +
                    "   AND EII.ADDRESS_ID =  AOA.ADDRESS_ID\n" +
                    "   AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                    "   AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID\n" +
                    "   AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE\n" +
                    //TODO 
                    "   AND EII.SPECIALITY_USER  *= AME2.EMPLOYEE_ID \n" +
                    
//                    "   AND CONVERT( VARCHAR , EII.SPECIALITY_USER ) *= AME2.EMPLOYEE_ID \n" +
                    "   AND EII.SPECIALITY_DEPT *= AMD2.DEPT_CODE\n" +
                    "   AND EII.CONTENT_CODE *= ACD.CONTENT_CODE\n" +
                    "   AND EII.FINANCE_PROP = 'SPARE'\n" +
                    "   AND ( " + SyBaseSQLUtil.nullIntParam() + "  OR EOCM.ORGANIZATION_ID = ?)\n" +
                    //"   AND EII.BARCODE LIKE ISNULL(?, EII.BARCODE)\n" +
                    "   AND ( " + SyBaseSQLUtil.nullStringParam() + "  OR EII.BARCODE LIKE '%' || ISNULL( ?, EII.BARCODE) || '%' )\n" +
                    "   AND ( ? = '' OR ESI.ITEM_NAME LIKE '%' || ISNULL(?, ESI.ITEM_NAME) || '%' )\n" +
                    "   AND ( ? = '' OR ESI.ITEM_SPEC LIKE '%' || ISNULL(?, ESI.ITEM_SPEC) || '%' )\n" +
                    "   AND ( ? = '' OR ? IS NULL OR EII.START_DATE > = ISNULL(?, EII.START_DATE))\n" +
                    "   AND ( ? = '' OR ? IS NULL OR EII.START_DATE < = ISNULL(?, EII.START_DATE))\n" +
                    "   AND ( ? = '' OR EII.RESPONSIBILITY_DEPT = ISNULL(?, EII.RESPONSIBILITY_DEPT))\n" +
                    "   AND ( ? = '' OR EO.WORKORDER_OBJECT_NAME LIKE '%' || ISNULL(?, EO.WORKORDER_OBJECT_NAME) || '%' )\n" +
                    "   AND ( ? = '' OR EII.SPECIALITY_DEPT = ISNULL(?, EII.SPECIALITY_DEPT))\n" +
                    "   AND ( ? = '' OR EII.ITEM_STATUS = ISNULL(?, EII.ITEM_STATUS))\n" +
                    "   AND ( ? = '' OR AM.MANUFACTURER_NAME LIKE '%' || ISNULL(?, AM.MANUFACTURER_NAME) || '%' )\n" +
                    "   AND ( ? = '' OR ? IS NULL OR EII.CREATION_DATE > = ISNULL(?, EII.CREATION_DATE))\n" +
                    "   AND ( ? = '' OR ? IS NULL OR EII.CREATION_DATE < = ISNULL(?, EII.CREATION_DATE))\n" +
                    "   AND ( ? = '' OR ? IS NULL OR EII.DISABLE_DATE  > = ISNULL(?, EII.DISABLE_DATE))\n" +
                    "   AND ( ? = '' OR ? IS NULL OR EII.DISABLE_DATE < = ISNULL(?, EII.DISABLE_DATE))\n" +
                    "   AND ( ? = '' OR EII.MAINTAIN_USER LIKE '%' || ISNULL(?, EII.MAINTAIN_USER) || '%' )\n" +
                    //TODO 
                    "   AND ( ? = '' OR EII.SPECIALITY_USER = ISNULL(?, EII.SPECIALITY_USER) )\n" +
//                    "   AND ( ? = '' OR CONVERT(VARCHAR,EII.SPECIALITY_USER) = ISNULL(?, CONVERT(VARCHAR,EII.SPECIALITY_USER)))\n" +
                    "   AND ( ? = '' OR ACD.CONTENT_NAME LIKE '%' || ISNULL(?, ACD.CONTENT_NAME) || '%' )\n" +
                    "";

        	sqlArgs.add(dto.getOrganizationId());
        	sqlArgs.add(dto.getOrganizationId());
        	sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getBarcode());
            sqlArgs.add(dto.getBarcode());
            sqlArgs.add(dto.getBarcode());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getStartDate());
            sqlArgs.add(dto.getStartDate());
            sqlArgs.add(dto.getStartDate());
            sqlArgs.add(dto.getEndDate());
            sqlArgs.add(dto.getEndDate());
            sqlArgs.add(dto.getEndDate());
            sqlArgs.add(dto.getResponsibilityDept());
            sqlArgs.add(dto.getResponsibilityDept());
            sqlArgs.add(dto.getWorkorderObjectName());
            sqlArgs.add(dto.getWorkorderObjectName());
            sqlArgs.add(dto.getSpecialityDept());
            sqlArgs.add(dto.getSpecialityDept());
            sqlArgs.add(dto.getItemStatus());
            sqlArgs.add(dto.getItemStatus());
            sqlArgs.add(dto.getManufacturerName());
            sqlArgs.add(dto.getManufacturerName());
            sqlArgs.add(dto.getStartCreationDate());
            sqlArgs.add(dto.getStartCreationDate());
            sqlArgs.add(dto.getStartCreationDate());
            sqlArgs.add(dto.getEndCreationDate());
            sqlArgs.add(dto.getEndCreationDate());
            sqlArgs.add(dto.getEndCreationDate());
            sqlArgs.add(dto.getStartDisableDate());
            sqlArgs.add(dto.getStartDisableDate());
            sqlArgs.add(dto.getStartDisableDate());
            sqlArgs.add(dto.getEndDisableDate());
            sqlArgs.add(dto.getEndDisableDate());
            sqlArgs.add(dto.getEndDisableDate());
            sqlArgs.add(dto.getMaintainUserName());
            sqlArgs.add(dto.getMaintainUserName());
            sqlArgs.add(dto.getSpecialityUser());
            sqlArgs.add(dto.getSpecialityUser());
            sqlArgs.add(dto.getContentName());
            sqlArgs.add(dto.getContentName());
            if (dto.getAttribute1().equals(AssetsDictConstant.STATUS_NO)) {
                sqlStr += " AND EII.ATTRIBUTE1 IS NULL ";
            } else {
                sqlStr += " AND EII.ATTRIBUTE1 = ?";
                sqlArgs.add(dto.getAttribute1());
            }
            if (!dto.getIsAbate().equals("")) {
                if (dto.getIsAbate().equals("Y")) {
                    sqlStr += " AND  " + SyBaseSQLUtil.isNotNull("EII.DISABLE_DATE") + "  ";
                } else {
                    sqlStr += " AND (EII.DISABLE_DATE IS NULL OR EII.DISABLE_DATE = '') ";
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
//        String sqlStr = "UPDATE ETS_ITEM_INFO \n" +
//                "   SET ADDRESS_ID          = ISNULL(?, ADDRESS_ID),\n" +
//                "       RESPONSIBILITY_DEPT = ISNULL(?, RESPONSIBILITY_DEPT),\n" +
//                "       MAINTAIN_USER       = ISNULL(?, MAINTAIN_USER),\n" +
//                "       SPECIALITY_DEPT     = ISNULL(?, SPECIALITY_DEPT),\n" +
//                "       DISABLE_DATE        = (CASE ? WHEN 'Y' THEN GETDATE() WHEN 'N' THEN NULL ELSE NULL END),\n" +
//                
//                "       LAST_UPDATE_DATE    = GETDATE(),\n" +
//                "       LAST_UPDATE_BY      = ?,\n" +
//                "       IS_TD               = ISNULL(?, IS_TD),\n" +
//                "       REMARK              = ISNULL(?, REMARK),\n" +
//                "       MANUFACTURER_ID     = ISNULL(?, MANUFACTURER_ID),\n" +
//                
//                
//               // "       SPECIALITY_USER     = ISNULL(?, SPECIALITY_USER),\n" +
//                "       SPECIALITY_USER     = CONVERT(INT, ISNULL(?,CONVERT(VARCHAR, SPECIALITY_USER))),\n" +
//                "       ITEM_STATUS         = ISNULL(?, ITEM_STATUS),\n" +
//                "       CONTENT_CODE        = ISNULL(?, CONTENT_CODE),\n" +
//                "       CONTENT_NAME        = ISNULL(?, CONTENT_NAME),\n" +
//                "       START_DATE          = ISNULL(?, START_DATE)\n" +
//                " WHERE BARCODE = ?";
        
        StringBuffer sb = new StringBuffer();
        
        sb.append( " UPDATE ETS_ITEM_INFO \n " ); 
        sb.append( "      SET    \n " );
        sb.append( "       ADDRESS_ID          = ( CASE ? WHEN NULL THEN ADDRESS_ID WHEN '' THEN ADDRESS_ID ELSE ? END ), \n " );
        sb.append( "       RESPONSIBILITY_DEPT = ( CASE ? WHEN NULL THEN RESPONSIBILITY_DEPT WHEN '' THEN RESPONSIBILITY_DEPT ELSE ? END ), \n " );
        sb.append( "       MAINTAIN_USER       = ( CASE ? WHEN NULL THEN MAINTAIN_USER WHEN '' THEN MAINTAIN_USER ELSE ? END ),\n" );
        sb.append( "       SPECIALITY_DEPT     = ( CASE ? WHEN NULL THEN SPECIALITY_DEPT WHEN '' THEN SPECIALITY_DEPT ELSE ? END ), \n " );
        sb.append( "       DISABLE_DATE        = ( CASE ? WHEN 'Y' THEN GETDATE() WHEN 'N' THEN NULL ELSE NULL END ),\n" );
       
        sb.append( "       LAST_UPDATE_DATE    = " + SyBaseSQLUtil.getCurDate() + ",\n" );
        sb.append( "       LAST_UPDATE_BY      = ?,\n" );
        sb.append( "       IS_TD 			   = ( CASE ? WHEN NULL THEN IS_TD WHEN '' THEN IS_TD ELSE ? END ), \n " );
        sb.append( "       REMARK              = ( CASE ? WHEN NULL THEN REMARK WHEN '' THEN REMARK ELSE ? END ),\n" );
        sb.append( "       MANUFACTURER_ID     = ( CASE ? WHEN NULL THEN MANUFACTURER_ID WHEN '' THEN MANUFACTURER_ID ELSE ? END ),\n" );
        
        
//        sb.append( "       SPECIALITY_USER     = CONVERT( INT, ( CASE ? WHEN NULL THEN SPECIALITY_USER WHEN '' THEN SPECIALITY_USER ELSE ?  END ) ), \n " );
        //TODO
        sb.append( "       SPECIALITY_USER         = ( CASE ? WHEN NULL THEN SPECIALITY_USER WHEN '' THEN SPECIALITY_USER ELSE ? END ), \n " );
        sb.append( "       ITEM_STATUS         = ( CASE ? WHEN NULL THEN ITEM_STATUS WHEN '' THEN ITEM_STATUS ELSE ? END ), \n " );
        sb.append( "       CONTENT_CODE        = ( CASE ? WHEN NULL THEN CONTENT_CODE WHEN '' THEN CONTENT_CODE ELSE ? END ), \n " );
        sb.append( "       CONTENT_NAME        = ( CASE ? WHEN NULL THEN CONTENT_NAME WHEN '' THEN CONTENT_NAME ELSE ? END ), \n " );
        sb.append( "       START_DATE          = ( CASE ? WHEN NULL THEN START_DATE WHEN '' THEN START_DATE ELSE ? END ) \n " );
          
        sb.append( "   WHERE  BARCODE = ?" );
        
        List sqlArgs = new ArrayList();
        
        sqlArgs.add(dto.getAddressId());
        sqlArgs.add(dto.getAddressId());
        sqlArgs.add(dto.getResponsibilityDept());
        sqlArgs.add(dto.getResponsibilityDept());
        sqlArgs.add(dto.getMaintainUser());
        sqlArgs.add(dto.getMaintainUser());
        sqlArgs.add(dto.getSpecialityDept());
        sqlArgs.add(dto.getSpecialityDept());
        sqlArgs.add(dto.getIsAbate());
        
        sqlArgs.add(userAccount.getUserId());
        sqlArgs.add(dto.getIsTD());
        sqlArgs.add(dto.getIsTD());
        sqlArgs.add(dto.getRemark());
        sqlArgs.add(dto.getRemark());
        sqlArgs.add(dto.getManufacturerId());
        sqlArgs.add(dto.getManufacturerId());
        
        sqlArgs.add(dto.getSpecialityUser());
        sqlArgs.add(dto.getSpecialityUser());
        sqlArgs.add(dto.getItemStatus());
        sqlArgs.add(dto.getItemStatus());
        sqlArgs.add(dto.getContentCode());
        sqlArgs.add(dto.getContentCode());
        sqlArgs.add(dto.getContentName());
        sqlArgs.add(dto.getContentName());
        sqlArgs.add(dto.getStartDate());
        sqlArgs.add(dto.getStartDate());
        
        sqlArgs.add(dto.getBarcode());
        sqlModel.setSqlStr( sb.toString() );
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
//        AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
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