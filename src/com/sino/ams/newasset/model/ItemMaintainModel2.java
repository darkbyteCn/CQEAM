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
 * <p>Title: SinoEAM</p>
 * <p>Description: 陕西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author suhp
 */
public class ItemMaintainModel2 extends AMSSQLProducer {
    private String deptCodes = "";

    /**
     * 功能：构造函数
     * @param userAccount SfUserDTO
     * @param dtoParameter EtsItemInfoDTO
     */
    public ItemMaintainModel2(SfUserDTO userAccount, AmsAssetsAddressVDTO dtoParameter){
        super(userAccount, dtoParameter);
        initDeptCodes();
    }

    /**
     * 功能：初始化当前用户有权限修改资产的所属部门信息
     */
    private void initDeptCodes(){
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
     * @return SQLModel
     */
    public SQLModel getPageQueryModel(){
        SQLModel sqlModel = new SQLModel();
        AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT"
                        + " EII.BARCODE,"
                        + " ESI.ITEM_CODE,"
                        + " ESI.ITEM_CATEGORY,"
                        + " AMS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY_NAME,"
                        + " ESI.ITEM_NAME,"
                        + " ESI.ITEM_SPEC,"
                        + " EFA.LIFE_IN_YEARS,"
                        + " EII.UNIT_OF_MEASURE ITEM_UNIT,"
                        + " EII.START_DATE,"
                        + " EO.WORKORDER_OBJECT_CODE,"
                        + " EO.WORKORDER_OBJECT_NAME,"
                        + " EO.WORKORDER_OBJECT_LOCATION,"
                        + " EC.COUNTY_CODE,"
                        + " EC.COUNTY_NAME,"
                        + " AME.USER_NAME RESPONSIBILITY_USER_NAME,"
                        + " AME.EMPLOYEE_NUMBER,"
                        + " AMD.DEPT_NAME RESPONSIBILITY_DEPT_NAME,"
                        + " EPPA.NAME PROJECT_NAME,"
                        + " EPPA.SEGMENT1 PROJECT_NUMBER,"
                        + " EMPV.VENDOR_NAME,"
                        + " EMPV.SEGMENT1 VENDOR_NUMBER,"
                        + " EII.FINANCE_PROP,"
                        + " AMS_PUB_PKG.GET_FLEX_VALUE(EII.FINANCE_PROP, 'FINANCE_PROP') FINANCE_PROP_VALUE,"
                        + " EII.RESPONSIBILITY_USER,"
                        + " EII.RESPONSIBILITY_DEPT,"
                        + " EII.MAINTAIN_USER,"
                        + " EII.MAINTAIN_DEPT,"
                        + " SG.GROUP_NAME MAINTAIN_DEPT_NAME,"
                        + " EII.ADDRESS_ID,"
                        + " EOCM.COMPANY_CODE,"
                        + " EOCM.COMPANY,"
                        + " AM.MANUFACTURER_CODE,"
                        + " AM.MANUFACTURER_NAME,"
                        + " EII.CONTENT_CODE,"
                        + " EII.CONTENT_NAME,"
                        + " EII.POWER,"
                        +" CASE WHEN EII.IS_SHARE='Y' THEN '是' ELSE  '否' END  IS_SHARE,"
                        + " AMS_PUB_PKG.GET_FLEX_VALUE(EII.ITEM_STATUS, 'ITEM_STATUS') ITEM_STATUS_NAME,"
                        + " AMS_PUB_PKG.GET_FLEX_VALUE(EII.SHARE_STATUS, 'SHARE_STATUS') SHARE_STATUS_NAME,"
                        + " AL.LOG_NET_ELE,"
                        + " AC.INVEST_CAT_NAME,"
                        + " AO.OPE_NAME,"
                        + " AN.LNE_NAME"
                        + " FROM"
                        + " AMS_LNE                     AL,"
                        + " AMS_CEX                     AC,"
                        + " AMS_OPE                     AO,"
                        + " AMS_NLE                     AN,"
                        + " ETS_ITEM_INFO               EII,"
                        + " ETS_SYSTEM_ITEM             ESI,"
                        + " AMS_OBJECT_ADDRESS          AOA,"
                        + " ETS_OBJECT                  EO,"
                        + " ETS_COUNTY                  EC,"
                        + " ETS_OU_CITY_MAP             EOCM,"
                        + " AMS_MIS_DEPT                AMD,"
                        + " AMS_MIS_EMPLOYEE            AME,"
                        + " ETS_PA_PROJECTS_ALL         EPPA,"
                        + " ETS_MIS_PO_VENDORS          EMPV,"
                        + " AMS_MANUFACTURER            AM,"
                        + " SF_GROUP                    SG,";
                        if (userAccount.getIsTd().equals("Y")) {
                            sqlStr = sqlStr + " ETS_ITEM_MATCH_TD          EIM,"
                                    + " ETS_FA_ASSETS_TD           EFA";
                        } else {
                            sqlStr = sqlStr + " ETS_ITEM_MATCH          EIM,"
                                    + " ETS_FA_ASSETS           EFA";
                        }
                        sqlStr = sqlStr +
                        " WHERE"
                        + " EII.ITEM_CODE = ESI.ITEM_CODE"
                        + " AND ESI.VENDOR_ID *= EMPV.VENDOR_ID"
                        + " AND EII.MANUFACTURER_ID *= AM.MANUFACTURER_ID"
                        + " AND EII.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
                        + " AND EII.ADDRESS_ID = AOA.ADDRESS_ID"
                        + " AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO"
                        + " AND EO.COUNTY_CODE *= EC.COUNTY_CODE"
                        + " AND EC.COMPANY_CODE = EOCM.COMPANY_CODE"
                        + " AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID"
                        + " AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE"
                        + " AND EII.PROJECTID *= EPPA.PROJECT_ID"
                        + " AND EII.MAINTAIN_DEPT *= SG.GROUP_ID"
                        + " AND EII.LNE_ID *= AL.AMS_LNE_ID"
                        + " AND EII.CEX_ID *= AC.AMS_CEX_ID"
                        + " AND EII.OPE_ID *= AO.AMS_OPE_ID"
                        + " AND EII.NLE_ID *= AN.AMS_LNE_ID"
                        + " AND EII.SYSTEMID *= EIM.SYSTEMID"
                        + " AND EIM.ASSET_ID *= EFA.ASSET_ID"

                        + " AND EII.DISABLE_DATE " + SyBaseSQLUtil.isNull() + " "
                        + " AND ESI.ITEM_CATEGORY = dbo.NVL(?, ESI.ITEM_CATEGORY)"
                        + " AND ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME)"
                        + " AND (( " + SyBaseSQLUtil.isNull() + "  OR EO.WORKORDER_OBJECT_CODE LIKE ?)\n"
					    + "      OR ( " + SyBaseSQLUtil.isNull() + "  OR EO.WORKORDER_OBJECT_NAME LIKE ?))\n"
                        + " AND ( " + SyBaseSQLUtil.isNull() + "  OR AME.USER_NAME LIKE ?)"
                        + " AND ( " + SyBaseSQLUtil.isNull() + "  OR EPPA.SEGMENT1 LIKE ?)"
                        + " AND ( " + SyBaseSQLUtil.isNull() + "  OR EPPA.NAME LIKE ?)"
                        + " AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.RESPONSIBILITY_DEPT = ?)"
                        + " AND (( " + SyBaseSQLUtil.isNull() + "  OR EII.CONTENT_NAME LIKE ?) OR ( " + SyBaseSQLUtil.isNull() + "  OR EII.CONTENT_CODE LIKE ?))"
                        + " AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.IS_SHARE = ?)"
                        + " AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.POWER LIKE ?)"
                        + " AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.MANUFACTURER_ID = ?)"
                        + " AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.ITEM_STATUS = ?)"
                        + " AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.FINANCE_PROP = ?)\n";
//                        + " AND EII.ORGANIZATION_ID = ?";
        sqlArgs.add(dto.getItemCategory());
        sqlArgs.add(dto.getItemName());
        sqlArgs.add(dto.getWorkorderObjectName());
        sqlArgs.add(dto.getWorkorderObjectName());
        sqlArgs.add(dto.getWorkorderObjectName());
        sqlArgs.add(dto.getWorkorderObjectName());
        sqlArgs.add(dto.getResponsibilityUserName());
        sqlArgs.add(dto.getResponsibilityUserName());
        sqlArgs.add(dto.getProjectNumber());
        sqlArgs.add(dto.getProjectNumber());
        sqlArgs.add(dto.getProjectName());
        sqlArgs.add(dto.getProjectName());
        sqlArgs.add(dto.getResponsibilityDept());
        sqlArgs.add(dto.getResponsibilityDept());
        sqlArgs.add(dto.getContentName());
        sqlArgs.add(dto.getContentName());
        sqlArgs.add(dto.getContentName());
        sqlArgs.add(dto.getContentName());
//        sqlArgs.add(dto.getContentCode());
//        sqlArgs.add(dto.getContentCode());
        sqlArgs.add(dto.getShare());
        sqlArgs.add(dto.getShare());
        sqlArgs.add(dto.getPower());
        sqlArgs.add(dto.getPower());
        sqlArgs.add(dto.getManufacturerId());
        sqlArgs.add(dto.getManufacturerId());
        sqlArgs.add(dto.getItemStatus());
        sqlArgs.add(dto.getItemStatus());
        sqlArgs.add(dto.getFinanceProp());
        sqlArgs.add(dto.getFinanceProp());
//        sqlArgs.add(userAccount.getOrganizationId());

        if((!StrUtil.isEmpty(dto.getFromBarcode()))&&(!StrUtil.isEmpty(dto.getToBarcode()))){
			   sqlStr +=  " AND EII.BARCODE >= dbo.NVL(?, EII.BARCODE)"
						+ " AND EII.BARCODE <= dbo.NVL(?, EII.BARCODE)";
			sqlArgs.add(dto.getFromBarcode());
			sqlArgs.add(dto.getToBarcode());
		} else {
			   sqlStr += "AND EII.BARCODE LIKE dbo.NVL(?,EII.BARCODE)";
			sqlArgs.add(dto.getFromBarcode() + dto.getToBarcode());
		}
        if(dto.getAttribute1().equals(AssetsDictConstant.STATUS_NO)){
            sqlStr += " AND EII.ATTRIBUTE " + SyBaseSQLUtil.isNullNoParam() + " ";
        } else {
            sqlStr += " AND EII.ATTRIBUTE1 = ?";
            sqlArgs.add(dto.getAttribute1());
        }
        if(userAccount.isComAssetsManager()){
            sqlStr += " AND EII.ORGANIZATION_ID = ?";
            sqlArgs.add(userAccount.getOrganizationId());
        } else if (!deptCodes.equals("('')")){
          sqlStr += " AND EII.RESPONSIBILITY_DEPT IN " + deptCodes;
        } else if (userAccount.isProvAssetsManager()){
        } else {
            String empliyeeNumber = userAccount.getEmployeeNumber();
            sqlStr += " AND EII.RESPONSIBILITY_USER = (SELECT AME.EMPLOYEE_ID FROM AMS_MIS_EMPLOYEE AME WHERE AME.EMPLOYEE_NUMBER='"+empliyeeNumber+"')";
        }
        if (dto.getCheck().equals("Y")) {
                sqlStr += " AND EXISTS (SELECT NULL\n" +
                        "          FROM AMS_ASSETS_CHECK_LINE AACL\n" +
                        "         WHERE EII.BARCODE = AACL.BARCODE\n" +
                        "               AND AACL.ARCHIVE_STATU " + SyBaseSQLUtil.isNullNoParam() + " )";
        } else if (dto.getCheck().equals("N")) {
                sqlStr += " AND NOT EXISTS (SELECT NULL\n" +
                        "          FROM AMS_ASSETS_CHECK_LINE AACL\n" +
                        "         WHERE EII.BARCODE = AACL.BARCODE\n" +
                        "               AND AACL.ARCHIVE_STATU " + SyBaseSQLUtil.isNullNoParam() + " )";
        }
            sqlStr += " AND ( EO.OBJECT_CATEGORY<71 OR EO.OBJECT_CATEGORY>=80)  ";

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：获取数据更新SQL
     * @return SQLModel
     * @throws SQLModelException
     */
    public SQLModel getDataUpdateModel() throws SQLModelException{
        SQLModel sqlModel = new SQLModel();
        try {
            AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
            String sqlStr = "UPDATE"
                            + " ETS_ITEM_INFO EII"
                            + " SET"
                            + " EII.ITEM_CODE           = dbo.NVL(?, EII.ITEM_CODE),"
                            + " EII.ADDRESS_ID          = dbo.NVL(?, EII.ADDRESS_ID),"
                            + " EII.RESPONSIBILITY_USER = dbo.NVL(?, EII.RESPONSIBILITY_USER),"
                            + " EII.RESPONSIBILITY_DEPT = dbo.NVL(?, EII.RESPONSIBILITY_DEPT),"
                            + " EII.MAINTAIN_USER       = dbo.NVL(?, EII.MAINTAIN_USER),"
                            + " EII.MAINTAIN_DEPT       = dbo.NVL(?, EII.MAINTAIN_DEPT),"
                            + " EII.START_DATE          = ISNULL(?, EII.START_DATE),"
                            + " EII.REMARK              = dbo.NVL(?, EII.REMARK),"
                            + " EII.LAST_UPDATE_DATE    = GETDATE(),"
                            + " EII.LAST_UPDATE_BY      = ?"
                            + " WHERE"
                            + " EII.BARCODE = ?";
            List sqlArgs = new ArrayList();
            sqlArgs.add(dto.getItemCode());
            sqlArgs.add(dto.getAddressId());
            sqlArgs.add(dto.getResponsibilityUser());
            sqlArgs.add(dto.getResponsibilityDept());
            sqlArgs.add(dto.getMaintainUser());
            sqlArgs.add(dto.getMaintainDept());
            sqlArgs.add(dto.getStartDate());
            sqlArgs.add(dto.getRemark());
            sqlArgs.add(userAccount.getUserId());
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
     * @return SQLModel
     */
    public SQLModel getPrimaryKeyDataModel(){
        SQLModel sqlModel = new SQLModel();
        AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
        String sqlStr = "SELECT"
                        + " EII.BARCODE,"
                        + " ESI.ITEM_CODE,"
                        + " ESI.ITEM_CATEGORY,"
                        + " AMS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY_NAME,"
                        + " ESI.ITEM_NAME,"
                        + " ESI.ITEM_SPEC,"
                        + " ESI.YEARS,"
                        + " ESI.ITEM_UNIT,"
                        + " EII.START_DATE,"
                        + " EO.WORKORDER_OBJECT_CODE,"
                        + " EO.WORKORDER_OBJECT_NAME,"
                        + " EO.WORKORDER_OBJECT_LOCATION,"
                        + " EC.COUNTY_CODE,"
                        + " EC.COUNTY_NAME,"
                        + " AME.USER_NAME RESPONSIBILITY_USER_NAME,"
                        + " AME.EMPLOYEE_NUMBER,"
                        + " AMD.DEPT_NAME RESPONSIBILITY_DEPT_NAME,"
                        + " EPPA.NAME PROJECT_NAME,"
                        + " EPPA.SEGMENT1 PROJECT_NUMBER,"
                        + " EMPV.VENDOR_NAME,"
                        + " EMPV.SEGMENT1 VENDOR_NUMBER,"
                        + " EII.FINANCE_PROP,"
                        + " AMS_PUB_PKG.GET_FLEX_VALUE(EII.FINANCE_PROP, 'FINANCE_PROP') FINANCE_PROP_VALUE,"
                        + " EII.RESPONSIBILITY_USER,"
                        + " EII.RESPONSIBILITY_DEPT,"
                        + " EII.MAINTAIN_USER,"
                        + " EII.MAINTAIN_DEPT,"
                        + " EII.ADDRESS_ID,"
                        + " EOCM.COMPANY_CODE,"
                        + " EOCM.COMPANY"
                        + " FROM"
                        + " ETS_ITEM_INFO          EII,"
                        + " ETS_SYSTEM_ITEM        ESI,"
                        + " AMS_OBJECT_ADDRESS     AOA,"
                        + " ETS_OBJECT             EO,"
                        + " ETS_COUNTY             EC,"
                        + " ETS_OU_CITY_MAP        EOCM,"
                        + " AMS_MIS_DEPT           AMD,"
                        + " AMS_MIS_EMPLOYEE       AME,"
                        + " ETS_PA_PROJECTS_ALL    EPPA,"
                        + " ETS_MIS_PO_VENDORS     EMPV"
                        + " WHERE"
                        + " EII.ITEM_CODE = ESI.ITEM_CODE"
                        + " AND ESI.VENDOR_ID *= EMPV.VENDOR_ID"
                        + " AND EII.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
                        + " AND EII.ADDRESS_ID = AOA.ADDRESS_ID"
                        + " AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO"
                        + " AND EO.COUNTY_CODE *= EC.COUNTY_CODE"
                        + " AND EC.COMPANY_CODE = EOCM.COMPANY_CODE"
                        + " AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID"
                        + " AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE"
                        + " AND EII.PROJECTID *= EPPA.PROJECT_ID"
                        + " AND EII.BARCODE = ?"
                        + " AND EII.RESPONSIBILITY_DEPT IN " + deptCodes;
        List sqlArgs = new ArrayList();
        sqlArgs.add(dto.getBarcode());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getFincePropCount(AmsAssetsAddressVDTO dto){
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT EFV.CODE, \n" +
                              " EFV.VALUE, \n" +
                              " SUM(T.CNT) CNT\n" +
                         " FROM ETS_FLEX_VALUES EFV,\n" +
                              " (SELECT EII.FINANCE_PROP, " +
                                      " COUNT(1) CNT\n" +
                                 " FROM ETS_ITEM_INFO EII, \n" +
                                      " ETS_SYSTEM_ITEM ESI,\n" +
                                      " AMS_OBJECT_ADDRESS AOA, \n" +
                                      " ETS_OBJECT EO, \n" +
                                      " ETS_COUNTY EC, \n" +
                                      " ETS_OU_CITY_MAP EOCM,\n" +
                                      " AMS_MIS_DEPT AMD, \n" +
                                      " AMS_MIS_EMPLOYEE AME, \n" +
                                      " ETS_PA_PROJECTS_ALL EPPA, \n" +
                                      " ETS_MIS_PO_VENDORS EMPV, \n" +
                                      " AMS_MANUFACTURER AM,\n" +
                                      " SF_GROUP SG\n" +
                                " WHERE EII.ITEM_CODE = ESI.ITEM_CODE \n" +
                                  " AND ESI.VENDOR_ID *= EMPV.VENDOR_ID \n" +
                                  " AND EII.MANUFACTURER_ID *= AM.MANUFACTURER_ID \n" +
                                  " AND EII.ORGANIZATION_ID = EOCM.ORGANIZATION_ID \n" +
                                  " AND EII.ADDRESS_ID = AOA.ADDRESS_ID \n" +
                                  " AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO \n" +
                                  " AND EO.COUNTY_CODE *= EC.COUNTY_CODE \n" +
                                  " AND EC.COMPANY_CODE = EOCM.COMPANY_CODE"    +
                                  " AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID \n" +
                                  " AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE \n" +
                                  " AND EII.PROJECTID *= EPPA.PROJECT_ID \n" +
                                  " AND EII.MAINTAIN_DEPT *= SG.GROUP_ID\n" +
                                  " AND EII.DISABLE_DATE " + SyBaseSQLUtil.isNullNoParam() + "  \n" +
                                  " AND EII.ATTRIBUTE " + SyBaseSQLUtil.isNullNoParam() + "  \n" +
                                  " AND (EO.OBJECT_CATEGORY < 71 OR EO.OBJECT_CATEGORY >= 80)\n" +
                                  " AND ESI.ITEM_CATEGORY = dbo.NVL(?, ESI.ITEM_CATEGORY)\n" +
                                  " AND ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME) \n" +
                                  " AND (EO.WORKORDER_OBJECT_CODE LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_CODE) OR\n" +
                                  "      EO.WORKORDER_OBJECT_NAME LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_NAME))\n" +
                                  " AND ( " + SyBaseSQLUtil.isNull() + "  OR AME.USER_NAME LIKE ?)\n" +
                                  " AND ( " + SyBaseSQLUtil.isNull() + "  OR EPPA.SEGMENT1 LIKE ?)\n" +
                                  " AND ( " + SyBaseSQLUtil.isNull() + "  OR EPPA.NAME LIKE ?) \n" +
                                  " AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.RESPONSIBILITY_DEPT = ?) \n" +
                                  " AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.CONTENT_NAME LIKE ?)\n" +
                                  " AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.CONTENT_CODE LIKE ?) \n" +
                                  " AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.IS_SHARE = ?) \n" +
                                  " AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.POWER LIKE ?)\n" +
                                  " AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.MANUFACTURER_ID = ?) \n" +
                                  " AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.ITEM_STATUS = ?) \n" +
                                  " AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.FINANCE_PROP = ?)";
        sqlArgs.add(dto.getItemCategory());
        sqlArgs.add(dto.getItemName());
        sqlArgs.add(dto.getWorkorderObjectName());
        sqlArgs.add(dto.getWorkorderObjectName());
        sqlArgs.add(dto.getResponsibilityUserName());
        sqlArgs.add(dto.getResponsibilityUserName());
        sqlArgs.add(dto.getProjectNumber());
        sqlArgs.add(dto.getProjectNumber());
        sqlArgs.add(dto.getProjectName());
        sqlArgs.add(dto.getProjectName());
        sqlArgs.add(dto.getResponsibilityDept());
        sqlArgs.add(dto.getResponsibilityDept());
        sqlArgs.add(dto.getContentName());
        sqlArgs.add(dto.getContentName());
        sqlArgs.add(dto.getContentCode());
        sqlArgs.add(dto.getContentCode());
        sqlArgs.add(dto.getShare());
        sqlArgs.add(dto.getShare());
        sqlArgs.add(dto.getPower());
        sqlArgs.add(dto.getPower());
        sqlArgs.add(dto.getManufacturerId());
        sqlArgs.add(dto.getManufacturerId());
        sqlArgs.add(dto.getItemStatus());
        sqlArgs.add(dto.getItemStatus());
        sqlArgs.add(dto.getFinanceProp());
        sqlArgs.add(dto.getFinanceProp());
        if((!StrUtil.isEmpty(dto.getFromBarcode()))&&(!StrUtil.isEmpty(dto.getToBarcode()))){
               sqlStr +=  " AND EII.BARCODE >= dbo.NVL(?, EII.BARCODE)"
                        + " AND EII.BARCODE <= dbo.NVL(?, EII.BARCODE)";
            sqlArgs.add(dto.getFromBarcode());
            sqlArgs.add(dto.getToBarcode());
        } else {
               sqlStr += "AND EII.BARCODE LIKE dbo.NVL(?,EII.BARCODE)";
            sqlArgs.add(dto.getFromBarcode() + dto.getToBarcode());
        }
        if(dto.getAttribute1().equals(AssetsDictConstant.STATUS_NO)){
            sqlStr += " AND EII.ATTRIBUTE " + SyBaseSQLUtil.isNullNoParam() + " ";
        } else {
            sqlStr += " AND EII.ATTRIBUTE1 = ?";
            sqlArgs.add(dto.getAttribute1());
        }
        if(userAccount.isComAssetsManager()){
            sqlStr += " AND EII.ORGANIZATION_ID = ?";
            sqlArgs.add(userAccount.getOrganizationId());
        } else if (!deptCodes.equals("('')")){
          sqlStr += " AND EII.RESPONSIBILITY_DEPT IN " + deptCodes;
        } else {
            String empliyeeNumber = userAccount.getEmployeeNumber();
            sqlStr += " AND EII.RESPONSIBILITY_USER = (SELECT AME.EMPLOYEE_ID FROM AMS_MIS_EMPLOYEE AME WHERE AME.EMPLOYEE_NUMBER='"+empliyeeNumber+"')";
        }
               sqlStr +=        " GROUP BY EII.FINANCE_PROP) T\n" +
                          " WHERE EFV.FLEX_VALUE_SET_ID = 1003 \n" +
                            " AND EFV.CODE *= T.FINANCE_PROP " +
                          " GROUP BY EFV.CODE, EFV.VALUE";

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
