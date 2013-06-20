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

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2009-6-4
 * Time: 16:26:43
 * To change this template use File | Settings | File Templates.
 */
public class ItemLastingModel extends AMSSQLProducer {
    private String deptCodes = "";

    /**
     * 功能：构造函数
     *
     * @param userAccount  SfUserDTO
     * @param dtoParameter EtsItemInfoDTO
     */
    public ItemLastingModel(SfUserDTO userAccount, AmsAssetsAddressVDTO dtoParameter) {
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
    @SuppressWarnings("unchecked")
	public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        try {
            AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
            String sqlStr = "SELECT EOCM.COMPANY,\n" +
                    "       EII.BARCODE,\n" +
                    "       dbo.APP_GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY,\n" +
                    "       ESI.ITEM_CODE,\n" +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       EO.WORKORDER_OBJECT_CODE,\n" +
                    "       EO.WORKORDER_OBJECT_NAME,\n" +
                    "       dbo.APP_GET_FLEX_VALUE(EII.ITEM_STATUS, 'ITEM_STATUS') ITEM_STATUS_NAME,\n" +
                    "       (CASE EII.DISABLE_DATE WHEN NULL THEN '否' ELSE '是' END) IS_ABATE,\n" +
                    "       ESI.ITEM_UNIT,\n" +
                    "       EII.ITEM_QTY,\n" +
                    "       AM.MANUFACTURER_NAME,\n" +
                    "       AMD.DEPT_NAME RESPONSIBILITY_DEPT_NAME,\n" +
                    "       AME.EMPLOYEE_NUMBER,\n" +
                    "       AME.USER_NAME RESPONSIBILITY_USER_NAME,\n" +
                    "       AMD2.DEPT_NAME MAINTAIN_DEPT,\n" +
                    "       EII.MAINTAIN_USER,\n" +
                    "       AMD3.DEPT_CODE SPECIALITY_DEPT,\n" +
                    "       AMD3.DEPT_NAME SPECIALITY_DEPT_NAME,\n" +
                    "       EII.POWER,\n" +
                    "       EII.OTHER_INFO,\n" +
                    "       ACD.CONTENT_CODE,\n" +
                    "       ACD.CONTENT_NAME,\n" +
                    "       ARI.RENT_DATE,\n" +
                    "       ARI.END_DATE,\n" +
                    "       ARI.RENT_PERSON,\n" +
                    "       ARI.TENANCY,\n" +
                    "       ARI.YEAR_RENTAL,\n" +
                    "       ARI.MONTH_REANTAL,\n" +
//                    "		EII.REMARK, " + 
                    "       ARI.REMARK,\n" +
                    "       ARI.CONTRACT_NUMBER,\n" +
                    "       ARI.CONTRACT_NAME\n" +
                    "FROM   ETS_ITEM_INFO      EII,\n" +
                    "       ETS_SYSTEM_ITEM    ESI,\n" +
                    "       AMS_OBJECT_ADDRESS AOA,\n" +
                    "       ETS_OBJECT         EO,\n" +
                    "       ETS_COUNTY         EC,\n" +
                    "       ETS_OU_CITY_MAP    EOCM,\n" +
                    "       AMS_MIS_DEPT       AMD,\n" +
                    "       AMS_MIS_DEPT       AMD2,\n" +
                    "       AMS_MIS_DEPT       AMD3,\n" +
                    "       AMS_MIS_EMPLOYEE   AME,\n" +
                    "       AMS_RENT_INFO      ARI,\n" +
                    "       AMS_CONTENT_DIC    ACD,\n" +
                    "       AMS_MANUFACTURER   AM\n" +
                    "WHERE  EII.ITEM_CODE = ESI.ITEM_CODE\n" +
                    "       AND EII.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
                    "       AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                    "       AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                    "       AND EO.COUNTY_CODE *= EC.COUNTY_CODE\n" +
                    "       AND EOCM.COMPANY_CODE *= EC.COMPANY_CODE\n" +
                    "       AND EII.BARCODE = ARI.BARCODE\n" ;
                    
                    if (!dto.getEmployeeNumber().trim().equals("")) {
                    	sqlStr += "       AND EII.RESPONSIBILITY_USER = AME.EMPLOYEE_ID \n" ;
                    } else {
                    	sqlStr += "       AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID \n" ;
                    }
                    
                    sqlStr += 
                    "       AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE  \n" +
                    "       AND EII.MAINTAIN_DEPT *= AMD2.DEPT_CODE  \n" +
                    "       AND EII.SPECIALITY_DEPT *= AMD3.DEPT_CODE  \n" +
                    "       AND EII.CONTENT_CODE *= ACD.CONTENT_CODE  \n" +
                    "       AND EII.MANUFACTURER_ID *= AM.MANUFACTURER_ID  \n" +
                    "       AND EII.FINANCE_PROP = 'RENT_ASSETS'\n" +
                    "       AND  (" + SyBaseSQLUtil.nullIntParam() + " OR  EOCM.ORGANIZATION_ID =  ? )\n" +
                    "       AND  (" + SyBaseSQLUtil.nullStringParam() + " OR  EII.BARCODE LIKE ? )\n" +
                    "       AND  (" + SyBaseSQLUtil.nullStringParam() + " OR  ESI.ITEM_NAME LIKE ? )\n" +
                    "       AND  (" + SyBaseSQLUtil.nullStringParam() + " OR  ESI.ITEM_SPEC LIKE ?)\n" +
                    "       AND  (" + SyBaseSQLUtil.nullSimpleCalendarParam() + " OR  EII.START_DATE > = ?)\n" +
                    
                    "       AND  (" + SyBaseSQLUtil.nullSimpleCalendarParam() + " OR  EII.START_DATE <= ? )\n" +
                    "       AND  (" + SyBaseSQLUtil.nullStringParam() + " OR  EII.RESPONSIBILITY_DEPT = ?)\n" +
                    "       AND  (" + SyBaseSQLUtil.nullStringParam() + " OR  AME.EMPLOYEE_NUMBER = ?)\n" +
                    "       AND  (" + SyBaseSQLUtil.nullStringParam() + " OR  EO.WORKORDER_OBJECT_NAME LIKE ? )\n" +
                    "       AND  (" + SyBaseSQLUtil.nullStringParam() + " OR  EII.SPECIALITY_DEPT = ?)\n" +
                    
                    "       AND  (" + SyBaseSQLUtil.nullSimpleCalendarParam() + " OR  EII.CREATION_DATE > = ?)\n" +
                    "       AND  (" + SyBaseSQLUtil.nullSimpleCalendarParam() + " OR  EII.CREATION_DATE <= ? )\n" +
                    "       AND  (" + SyBaseSQLUtil.nullSimpleCalendarParam() + " OR  EII.DISABLE_DATE > = ?)\n" +
                    "       AND  (" + SyBaseSQLUtil.nullSimpleCalendarParam() + " OR  EII.DISABLE_DATE <= ?  )\n" +
                   
                    "       AND  (" + SyBaseSQLUtil.nullStringParam() + " OR  EII.MAINTAIN_USER LIKE ?)\n" +
                    "       AND  (" + SyBaseSQLUtil.nullStringParam() + " OR  ACD.CONTENT_CODE = ?)\n" +
                    
                    "       AND (  (? = '' OR ARI.RENT_DATE >= ISNULL(?, ARI.RENT_DATE) AND\n" +
                    "       (? = '' OR  ARI.RENT_DATE <= ISNULL(?, ARI.RENT_DATE) ) ) OR\n" +
                    "       ( (? = '' OR ARI.END_DATE >= ISNULL(?, ARI.END_DATE) ) AND\n" +
                    "       (? = '' OR ARI.END_DATE <= ? ) )    )\n" +
                    
                    "       AND (" + SyBaseSQLUtil.nullStringParam() + " OR ARI.RENT_PERSON LIKE  ? )\n" +
                    "       AND (" + SyBaseSQLUtil.nullStringParam() + " OR ARI.CONTRACT_NUMBER LIKE  ? )\n" +
                    "       AND (" + SyBaseSQLUtil.nullStringParam() + " OR ARI.CONTRACT_NAME LIKE  ? )\n" +
                    "       AND  (" + SyBaseSQLUtil.nullStringParam() + " OR  ACD.CONTENT_NAME LIKE ? )\n" +
                    "       AND  (" + SyBaseSQLUtil.nullStringParam() + " OR  AM.MANUFACTURER_ID = ?)\n";
           
            SyBaseSQLUtil.nullIntParamArgs(sqlArgs, dto.getOrganizationId()); 
//            sqlArgs.add(dto.getOrganizationId());
            SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getBarcode());
            SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getItemName());
            SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getItemSpec());
//            SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getItemSpec());
            SyBaseSQLUtil.nullSimpleCalendarParamArgs(sqlArgs, dto.getStartDate());
            SyBaseSQLUtil.nullSimpleCalendarParamArgs(sqlArgs, dto.getEndDate());
            
            
            SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getResponsibilityDept());
            SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getEmployeeNumber());
            SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getWorkorderObjectName());
            SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getSpecialityDept());
            
            SyBaseSQLUtil.nullSimpleCalendarParamArgs(sqlArgs, dto.getStartCreationDate());
            SyBaseSQLUtil.nullSimpleCalendarParamArgs(sqlArgs, dto.getEndCreationDate());
            SyBaseSQLUtil.nullSimpleCalendarParamArgs(sqlArgs, dto.getStartDisableDate());
            SyBaseSQLUtil.nullSimpleCalendarParamArgs(sqlArgs, dto.getEndDisableDate());
            
//            sqlArgs.add( dto.getStartCreationDate());
//            sqlArgs.add( dto.getStartCreationDate());
//            sqlArgs.add( dto.getEndCreationDate());
//            sqlArgs.add( dto.getEndCreationDate());
//            sqlArgs.add( dto.getStartDisableDate());
//            sqlArgs.add( dto.getStartDisableDate());
//            sqlArgs.add( dto.getEndDisableDate());
//            sqlArgs.add( dto.getEndDisableDate());
            
            SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getMaintainUserName());
            SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getContentCode()); 
            
            sqlArgs.add( dto.getStartRentDate());
            sqlArgs.add( dto.getStartRentDate());
            sqlArgs.add( dto.getEndRentDate());
            sqlArgs.add( dto.getEndRentDate());
            
            sqlArgs.add( dto.getStartRentDate());
            sqlArgs.add( dto.getStartRentDate());
            sqlArgs.add( dto.getEndRentDate());
            sqlArgs.add( dto.getEndRentDate());
            
            
            SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getRentPerson());
            SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getContractNumber());
            SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getContractName());
            SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getContentName()); 
            SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getManufacturerId());
            
            if (!dto.getIsAbate().equals("")) {
                if (dto.getIsAbate().equals("Y")) {
                    sqlStr += " AND  " + SyBaseSQLUtil.isNotNull("EII.DISABLE_DATE") + "  ";
                } else {
                    sqlStr += " AND EII.DISABLE_DATE " + SyBaseSQLUtil.isNullNoParam() + "  ";
                }
            }
            
            if (userAccount.isProvAssetsManager()) {
            } else if (userAccount.isComAssetsManager()) {
                sqlStr += " AND EII.ORGANIZATION_ID = ? ";
                sqlArgs.add(userAccount.getOrganizationId());
            } else if (userAccount.isDptAssetsManager()) {
                sqlStr += " AND EII.RESPONSIBILITY_DEPT IN " + deptCodes;
            } else {
                //String employeeNumber = userAccount.getEmployeeNumber();
                //if( !StrUtil.isEmpty( employeeNumber )){
                //	sqlStr += " AND EII.RESPONSIBILITY_USER = (SELECT AME.EMPLOYEE_ID FROM AMS_MIS_EMPLOYEE AME WHERE AME.EMPLOYEE_NUMBER='" + employeeNumber + "')";
                //}
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
    @SuppressWarnings("unchecked")
	public SQLModel getDataUpdateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
//        String sqlStr = "UPDATE ETS_ITEM_INFO \n" +
//                "SET    ADDRESS_ID          = dbo.NVL(?, ADDRESS_ID),\n" +
//                "       RESPONSIBILITY_DEPT = dbo.NVL(?, RESPONSIBILITY_DEPT),\n" +
//                "       RESPONSIBILITY_USER = dbo.NVL(?, RESPONSIBILITY_USER),\n" +
//                "       SPECIALITY_DEPT     = dbo.NVL(?, SPECIALITY_DEPT),\n" +
//                "       MAINTAIN_DEPT       = dbo.NVL(?, MAINTAIN_DEPT),\n" +
//                "       MAINTAIN_USER       = dbo.NVL(?, MAINTAIN_USER),\n" +
//
//                "       DISABLE_DATE        = (CASE ? WHEN 'Y' THEN " + SyBaseSQLUtil.getCurDate() + " WHEN 'N' THEN NULL ELSE '' END ),\n" +
//                
//                "       LAST_UPDATE_DATE    = " + SyBaseSQLUtil.getCurDate() + ",\n" +
//                "       LAST_UPDATE_BY      = ?,\n" +
//                "       REMARK              = dbo.NVL(?, REMARK),\n" +
//                "       MANUFACTURER_ID     = dbo.NVL(?, MANUFACTURER_ID),\n" +
//                "       POWER               = dbo.NVL(?, POWER)\n" +
//                "WHERE  BARCODE = ?";
        
        String sqlStr = "  UPDATE ETS_ITEM_INFO \n" +
				        "       SET    " +
				        "       ADDRESS_ID          = ( CASE ? WHEN NULL THEN ADDRESS_ID WHEN '' THEN ADDRESS_ID ELSE ? END ),\n" +
				        "       RESPONSIBILITY_DEPT = ( CASE ? WHEN NULL THEN RESPONSIBILITY_DEPT WHEN '' THEN RESPONSIBILITY_DEPT ELSE ? END ),\n" +
				        "       RESPONSIBILITY_USER = ( CASE ? WHEN NULL THEN RESPONSIBILITY_USER WHEN '' THEN RESPONSIBILITY_USER ELSE ? END ),\n" +
				        "       SPECIALITY_DEPT     = ( CASE ? WHEN NULL THEN SPECIALITY_DEPT WHEN '' THEN SPECIALITY_DEPT ELSE ? END ),\n" +
				        "       MAINTAIN_DEPT       = ( CASE ? WHEN NULL THEN MAINTAIN_DEPT WHEN '' THEN MAINTAIN_DEPT ELSE ? END ),\n" +
				        "       MAINTAIN_USER       = ( CASE ? WHEN NULL THEN MAINTAIN_USER WHEN '' THEN MAINTAIN_USER ELSE ? END ),\n";
		        if(!StrUtil.isEmpty(dto.getIsAbate())){
					sqlStr += "       ITEM_STATUS         = (CASE ? WHEN 'Y' THEN 'DISCARDED' WHEN 'N' THEN 'NORMAL' ELSE 'NORMAL' END ),\n" +
			        		  "       DISABLE_DATE        = (CASE ? WHEN 'Y' THEN " + SyBaseSQLUtil.getCurDate() + " WHEN 'N' THEN NULL ELSE '' END ),\n";
		        }
			  sqlStr += "       LAST_UPDATE_DATE    = " + SyBaseSQLUtil.getCurDate() + ",\n" +
				        "       LAST_UPDATE_BY      = ?,\n" +
				        "       REMARK              = ( CASE ? WHEN NULL THEN REMARK WHEN '' THEN REMARK ELSE ? END ),\n" +
				        "       MANUFACTURER_ID     = ( CASE ? WHEN NULL THEN MANUFACTURER_ID WHEN '' THEN MANUFACTURER_ID ELSE ? END ),\n" +
				        "       POWER               = ( CASE ? WHEN NULL THEN POWER WHEN '' THEN POWER ELSE ? END )\n" +
				        "  WHERE  BARCODE = ?";
        
        
        List sqlArgs = new ArrayList();
        sqlArgs.add(dto.getAddressId());
        sqlArgs.add(dto.getAddressId());
        sqlArgs.add(dto.getResponsibilityDept());
        sqlArgs.add(dto.getResponsibilityDept());
        
        sqlArgs.add(dto.getResponsibilityUser());
        sqlArgs.add(dto.getResponsibilityUser());
        sqlArgs.add(dto.getSpecialityDept());
        sqlArgs.add(dto.getSpecialityDept());
        
        sqlArgs.add(dto.getMaintainDept());
        sqlArgs.add(dto.getMaintainDept());
        sqlArgs.add(dto.getMaintainUser());
        sqlArgs.add(dto.getMaintainUser());
        
        if (!StrUtil.isEmpty(dto.getIsAbate())) {
			sqlArgs.add(dto.getIsAbate());
			sqlArgs.add(dto.getIsAbate());
		}
        sqlArgs.add(userAccount.getUserId());
        
        sqlArgs.add(dto.getRemark());
        sqlArgs.add(dto.getRemark());
        sqlArgs.add(dto.getManufacturerId());
        sqlArgs.add(dto.getManufacturerId());
        sqlArgs.add(dto.getPower());
        sqlArgs.add(dto.getPower());

        sqlArgs.add(dto.getBarcode());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：获取数据更新SQL
     * @param barCodes String
     * @return SQLModel
     * @throws com.sino.base.exception.SQLModelException
     *
     */
    @SuppressWarnings("unchecked")
	public SQLModel getItemRentUpdateModel(String barCodes) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
            String sqlStr = "UPDATE AMS_RENT_INFO \n" +
                    "SET    " + 
                    "		RENT_PERSON     = ( CASE ? WHEN NULL THEN RENT_PERSON WHEN '' THEN RENT_PERSON ELSE ? END ),\n" +
                    "       RENT_DATE       = ( CASE ? WHEN NULL THEN RENT_DATE WHEN '' THEN RENT_DATE ELSE ? END ),\n" +
                    "       END_DATE        = ( CASE ? WHEN NULL THEN END_DATE WHEN '' THEN END_DATE ELSE ? END ),\n" +
                    "       CONTRACT_NUMBER = ( CASE ? WHEN NULL THEN CONTRACT_NUMBER WHEN '' THEN CONTRACT_NUMBER ELSE ? END ),\n" +
                    "       CONTRACT_NAME   = ( CASE ? WHEN NULL THEN CONTRACT_NAME WHEN '' THEN CONTRACT_NAME ELSE ? END ),\n" +
                    "       REMARK   		= ( CASE ? WHEN NULL THEN REMARK WHEN '' THEN REMARK ELSE ? END )\n" +
                    
//                    "		RENT_PERSON     = dbo.NVL(?, RENT_PERSON),\n" +
//                    "       RENT_DATE       = ISNULL(?, RENT_DATE),\n" +
//                    "       END_DATE        = ISNULL(?, END_DATE),\n" +
//                    "       CONTRACT_NUMBER = dbo.NVL(?, CONTRACT_NUMBER),\n" +
//                    "       CONTRACT_NAME   = dbo.NVL(?, CONTRACT_NAME)\n" +
                    "WHERE  BARCODE IN ("+barCodes+")";
            List sqlArgs = new ArrayList();
            sqlArgs.add(dto.getRentPerson());
            sqlArgs.add(dto.getRentPerson());
            
            sqlArgs.add(dto.getRentDate());
            sqlArgs.add(dto.getRentDate());
            
            sqlArgs.add(dto.getEndDate());
            sqlArgs.add(dto.getEndDate());
            
            sqlArgs.add(dto.getContractNumber());
            sqlArgs.add(dto.getContractNumber());
            
            sqlArgs.add(dto.getContractName()); 
            sqlArgs.add(dto.getContractName());

            sqlArgs.add(dto.getRemark());
            sqlArgs.add(dto.getRemark());
            
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
    @SuppressWarnings("unchecked")
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
    
	private String getEndDateString(){
//    	return "DATEADD( DAY , 1 , CONVERT( DATE, ? ) )";
    	return " ? ";
    }
}
