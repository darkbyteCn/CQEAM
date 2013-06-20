package com.sino.ams.ct.bean;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.lookup.LookUpModel;
import com.sino.base.lookup.LookUpProp;
import com.sino.base.util.StrUtil;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.ct.dto.EtsItemInfoDTO;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsLookUpConstant;
import com.sino.ams.newasset.dto.*;
import com.sino.ams.system.basepoint.dto.EtsObjectDTO;
import com.sino.ams.system.item.dto.EtsSystemItemDTO;
import com.sino.ams.system.procedure.dto.MisDeptDTO;
import com.sino.ams.system.project.dto.EtsPaProjectsAllDTO;
import com.sino.ams.system.user.dto.EtsOuCityMapDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.security.dto.ServletConfigDTO;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author 于士博
 * @version 1.0
 */
public class LookUpCtModel extends LookUpModel {

    private SfUserDTO user = null;
    private String proCode = "";

    public LookUpCtModel(BaseUserDTO userAccount, DTO dtoParameter, LookUpProp lookProp) {
        super(userAccount, dtoParameter, lookProp);
        this.user = (SfUserDTO) userAccount;
    }

    public void setServletConfig(ServletConfigDTO servletConfig) {
        super.setServletConfig(servletConfig);
        this.proCode = servletConfig.getProvinceCode();
    }

    /**
     * 功能：构造查询SQL。由具体需要LookUp操作的应用实现
     */

    protected void produceSQLModel() {
        sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "";
        String lookUpName = lookProp.getLookUpName();
        if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_DEPT)) {
            MisDeptDTO dto = (MisDeptDTO) dtoParameter;
            String transferType = dto.getTransferType();
            sqlStr = "SELECT"
                    + " EOCM.ORGANIZATION_ID TO_ORGANIZATION_ID,"
                    + " AMD.COMPANY_CODE,"
                    + " EOCM.COMPANY TO_COMPANY_NAME,"
                    + " AMD.DEPT_CODE TO_DEPT,"
                    + " AMD.DEPT_NAME TO_DEPT_NAME"
                    + " FROM"
                    + " AMS_MIS_DEPT    AMD,"
                    + " ETS_OU_CITY_MAP EOCM, AMS_MIS_EMPLOYEE AME"
                    + " WHERE"
                    + " AMD.COMPANY_CODE = EOCM.COMPANY_CODE"
                    + " AND EOCM.COMPANY LIKE dbo.NVL(?, EOCM.COMPANY)"
                    + " AND AMD.DEPT_NAME LIKE dbo.NVL(?, AMD.DEPT_NAME)";
            sqlArgs.add(dto.getCompanyName());
            sqlArgs.add(dto.getDeptName());
            if (transferType.equals(AssetsDictConstant.TRANS_BTW_COMP)) { //选择其他地市部门
                sqlStr = sqlStr
                        + " AND EOCM.ORGANIZATION_ID = ?";
                sqlArgs.add(dto.getOrganizationId());
            } else if (transferType.equals(AssetsDictConstant.TRANS_BTW_DEPT)) { //选择本公司部门
                sqlStr = sqlStr
                        + " AND EOCM.ORGANIZATION_ID = ?"
                        + " AND AMD.DEPT_CODE <> ?";
                sqlArgs.add(dto.getOrganizationId());
                sqlArgs.add(dto.getDeptCode());
            }
            sqlStr += "AND AME.DEPT_CODE = AMD.DEPT_CODE\n" +
                    " GROUP BY EOCM.ORGANIZATION_ID,\n" +
                    "          AMD.COMPANY_CODE,\n" +
                    "          EOCM.COMPANY,\n" +
                    "          AMD.DEPT_CODE,\n" +
                    "          AMD.DEPT_NAME";
        } else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_DEPT_NM)) {   //必须指定专业资产管理员的部门 选择接收部门
            MisDeptDTO dto = (MisDeptDTO) dtoParameter;
            String transferType = dto.getTransferType();
            sqlStr = "SELECT"
                    + " EOCM.ORGANIZATION_ID TO_ORGANIZATION_ID,"
                    + " AMD.COMPANY_CODE,"
                    + " EOCM.COMPANY TO_COMPANY_NAME,"
                    + " AMD.DEPT_CODE TO_DEPT,"
                    + " AMD.DEPT_NAME TO_DEPT_NAME"
                    + " FROM"
                    + " AMS_MIS_DEPT    AMD,"
                    + " ETS_OU_CITY_MAP EOCM, AMS_MIS_EMPLOYEE AME,\n" +
                    "       SF_ROLE          SR,\n" +
                    "       SF_USER          SU,\n" +
                    "       SF_USER_RIGHT    SUR"
                    + " WHERE"
                    + " AMD.COMPANY_CODE = EOCM.COMPANY_CODE" +
                    "   AND SU.EMPLOYEE_NUMBER = AME.EMPLOYEE_NUMBER\n" +
                    "   AND SU.USER_ID = SUR.USER_ID\n" +
                    "   AND SUR.ROLE_ID = SR.ROLE_ID\n" +
                    "   AND SR.ROLE_NAME = '专业资产管理员'"
                    + " AND EOCM.COMPANY LIKE dbo.NVL(?, EOCM.COMPANY)"
                    + " AND AMD.DEPT_NAME LIKE dbo.NVL(?, AMD.DEPT_NAME)";
            sqlArgs.add(dto.getCompanyName());
            sqlArgs.add(dto.getDeptName());
            if (transferType.equals(AssetsDictConstant.TRANS_BTW_COMP)) { //选择其他地市部门
                sqlStr = sqlStr
                        + " AND EOCM.ORGANIZATION_ID = ?";
                sqlArgs.add(dto.getOrganizationId());
            } else if (transferType.equals(AssetsDictConstant.TRANS_BTW_DEPT)) { //选择本公司部门
                sqlStr = sqlStr
                        + " AND EOCM.ORGANIZATION_ID = ?"
                        + " AND AMD.DEPT_CODE <> ?";
                sqlArgs.add(dto.getOrganizationId());
                sqlArgs.add(dto.getDeptCode());
            }
            sqlStr += "AND AME.DEPT_CODE = AMD.DEPT_CODE\n" +
                    " GROUP BY EOCM.ORGANIZATION_ID,\n" +
                    "          AMD.COMPANY_CODE,\n" +
                    "          EOCM.COMPANY,\n" +
                    "          AMD.DEPT_CODE,\n" +
                    "          AMD.DEPT_NAME";
        } else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_ADDRESS)) {
            EtsObjectDTO dto = (EtsObjectDTO) dtoParameter;
            sqlStr = "SELECT"
                    + " EOCM.COMPANY,"
                    + " EOCM.ORGANIZATION_ID,"
                    + " EC.COUNTY_NAME,"
                    + " EO.WORKORDER_OBJECT_NO TO_OBJECT_NO,"
                    + " EO.WORKORDER_OBJECT_CODE,"
                    + " EO.WORKORDER_OBJECT_NAME TO_OBJECT_NAME,"
                    + " EO.WORKORDER_OBJECT_LOCATION,"
                    + " (SELECT"
                    + " AOA.ADDRESS_ID"
                    + " FROM"
                    + " AMS_OBJECT_ADDRESS AOA"
                    + " WHERE"
                    + " AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO"
                    + " AND AOA.BOX_NO = '0000'"
                    + " AND AOA.NET_UNIT = '0000'"
                    + " ) ADDRESS_ID"
                    + " FROM"
                    + " ETS_OBJECT EO,"
                    + " ETS_OU_CITY_MAP EOCM,"
                    + " ETS_COUNTY EC"
                    + " WHERE"
                    + " EO.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
                    + " AND EO.COUNTY_CODE *= EC.COUNTY_CODE"
                    + " AND EC.COMPANY_CODE = EOCM.COMPANY_CODE"
                    + " AND EO.ORGANIZATION_ID = ?"
                    + " AND (EO.OBJECT_CATEGORY < ? OR EO.OBJECT_CATEGORY = ?)"
                    + " AND (EO.DISABLE_DATE " + SyBaseSQLUtil.isNullNoParam() + "  OR EO.DISABLE_DATE > GETDATE()) OR EO.DISABLE_DATE IS NULL "
                    + " AND EO.WORKORDER_OBJECT_NAME LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_NAME)"
                    + " AND EO.WORKORDER_OBJECT_CODE LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_CODE)";
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(AssetsDictConstant.INV_NORMAL);
            sqlArgs.add(AssetsDictConstant.NETADDR_OTHERS);
            sqlArgs.add(dto.getWorkorderObjectName());
            sqlArgs.add(dto.getWorkorderObjectCode());
        } else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_RECIIVER)) {
            AmsMisEmployeeDTO dto = (AmsMisEmployeeDTO) dtoParameter;
            sqlStr = "SELECT"
                    + " AME.EMPLOYEE_ID,"
                    + " AME.USER_NAME,"
                    + " AME.EMPLOYEE_NUMBER,"
                    + " AMD.DEPT_NAME,"
                    + " EOCM.COMPANY,"
                    + " EOCM.ORGANIZATION_ID"
                    + " FROM"
                    + " AMS_MIS_EMPLOYEE AME,"
                    + " AMS_MIS_DEPT      AMD,"
                    + " ETS_OU_CITY_MAP  EOCM"
                    + " WHERE"
                    + " AME.DEPT_CODE *= AMD.DEPT_CODE"
                    + " AND AMD.COMPANY_CODE = EOCM.COMPANY_CODE"
                    + " AND EOCM.ORGANIZATION_ID = ?"
                    + " AND AME.DEPT_CODE = ?"
                    + " AND AME.USER_NAME LIKE dbo.NVL(?, AME.USER_NAME)"
                    + " AND AME.EMPLOYEE_NUMBER LIKE dbo.NVL(?, AME.EMPLOYEE_NUMBER)";
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getDeptCode());
            sqlArgs.add(dto.getUserName());
            sqlArgs.add(dto.getEmployeeNumber());
        } else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_RENT)) {
            AmsAssetsTransLineDTO dto = (AmsAssetsTransLineDTO) dtoParameter;
            sqlStr = "SELECT ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       EII.BARCODE,\n" +
                    "       EII.ITEM_QTY,\n" +
                    "       EII.RESPONSIBILITY_USER,\n" +
                    "       AME.USER_NAME,\n" +
                    "       AMD.DEPT_NAME,\n" +
                    "       AMD.DEPT_CODE\n" +
                    "FROM   ETS_ITEM_INFO    EII,\n" +
                    "       ETS_SYSTEM_ITEM  ESI,\n" +
                    "       AMS_MIS_EMPLOYEE AME,\n" +
                    "       AMS_MIS_DEPT     AMD\n" +
                    "WHERE  EII.ITEM_CODE = ESI.ITEM_CODE\n" +
                    "       AND EII.FINANCE_PROP = 'OTHER'\n" +
                    "       AND EII.ATTRIBUTE1 = 'RENT'\n";
            if (dto.getUserName().equals("")) {
                sqlStr += "       AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID\n";
            } else {
                sqlStr += "       AND EII.RESPONSIBILITY_USER = AME.EMPLOYEE_ID\n";
            }

            sqlStr += "       AND EII.ORGANIZATION_ID = ?\n" +
                    "       AND EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE\n" +
                    "       AND AMD.DEPT_CODE = ?\n" +
                    "       AND ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME)\n" +
                    "       AND ESI.ITEM_SPEC LIKE dbo.NVL(?, ESI.ITEM_SPEC)\n" +
                    "       AND EII.BARCODE LIKE dbo.NVL(?, EII.BARCODE)\n";
            sqlArgs.add(user.getOrganizationId());
            sqlArgs.add(dto.getDeptCode());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getBarcode());
            if (!dto.getUserName().equals("")) {
                sqlStr += "       AND AME.USER_NAME LIKE ?\n";
            }

            sqlArgs.add(dto.getUserName());
        } else if (lookUpName.equals(AssetsLookUpConstant.LOOK_TRANSFER_ASSETS)) { //查询选择调拨资产
            AmsAssetsTransLineDTO dto = (AmsAssetsTransLineDTO) dtoParameter;
            String transferType = dto.getTransferType();
            boolean isDeptMgr = user.isDptAssetsManager();
            boolean isCompMgr = user.isComAssetsManager();
            String mtlMgrProps = user.getMtlMgrProps();

            sqlStr = "SELECT /*+rule */"
                    + " AAAV.BARCODE,"
                    + " AAAV.ASSET_NUMBER,"
                    + " AAAV.ASSET_ID,"
                    + " AAAV.ASSETS_DESCRIPTION,"
                    + " AAAV.MODEL_NUMBER,"
                    + " AAAV.ITEM_NAME,"
                    + " AAAV.ITEM_SPEC,"
                    + " AAAV.VENDOR_NAME,"
                    + " AAAV.UNIT_OF_MEASURE,"
                    + " ISNULL(AAAV.CURRENT_UNITS, 1) CURRENT_UNITS,"
                    + " AAAV.FA_CATEGORY_CODE OLD_FA_CATEGORY_CODE,"
                    + " AAAV.DEPT_CODE OLD_RESPONSIBILITY_DEPT,"
                    + " AAAV.DEPT_NAME OLD_RESPONSIBILITY_DEPT_NAME,"
                    + " AAAV.WORKORDER_OBJECT_NO OLD_LOCATION,"
                    + " AAAV.WORKORDER_OBJECT_CODE OLD_LOCATION_CODE,"
                    + " AAAV.WORKORDER_OBJECT_NAME OLD_LOCATION_NAME,"
                    + " AAAV.RESPONSIBILITY_USER OLD_RESPONSIBILITY_USER,"
                    + " AAAV.RESPONSIBILITY_USER_NAME OLD_RESPONSIBILITY_USER_NAME,";
            if (transferType.equals(AssetsDictConstant.TRANS_INN_DEPT)) { //部门内调拨
                sqlStr = sqlStr
                        + " CASE WHEN AAAV.RESPONSIBILITY_USER=? THEN RESPONSIBILITY_USER ELSE '' END RESPONSIBILITY_USER,"
                        + " CASE WHEN AAAV.RESPONSIBILITY_USER=? THEN RESPONSIBILITY_USER_NAME ELSE '' END RESPONSIBILITY_USER_NAME,"
                        + " AAAV.ORGANIZATION_ID TO_ORGANIZATION_ID,";
                sqlArgs.add(user.getEmployeeId());
                sqlArgs.add(user.getEmployeeId());
            }
            sqlStr = sqlStr
                    + " AAAV.DATE_PLACED_IN_SERVICE,"
                    + " AAAV.COST,"
                    + " AAAV.DEPRN_COST,"
                    + " AAAV.DEPRECIATION_ACCOUNT OLD_DEPRECIATION_ACCOUNT,"
                    + " AAAV.DEPRECIATION"
                    + " FROM"
                    + " AMS_ASSETS_ADDRESS_V AAAV"
                    + " WHERE"
                    + " AAAV.ORGANIZATION_ID = ?";
            sqlArgs.add(user.getOrganizationId());
            if (!StrUtil.isEmpty(dto.getDeptCode())) { //部门限制
                sqlStr += " AND AAAV.DEPT_CODE = ?";
                sqlArgs.add(dto.getDeptCode());
            } else { //根据操作人员权限限制
                if (!isCompMgr && mtlMgrProps.equals("") && isDeptMgr) {
                    DTOSet depts = user.getPriviDeptCodes();
                    AmsMisDeptDTO dept = null;
                    String deptCodes = "(";
                    for (int i = 0; i < depts.getSize(); i++) {
                        dept = (AmsMisDeptDTO) depts.getDTO(i);
                        deptCodes += "'" + dept.getDeptCode() + "', ";
                    }
                    deptCodes += "'')";
                    sqlStr += " AND AAAV.DEPT_CODE IN " + deptCodes;
                }
            }
            if (!user.isDptAssetsManager() && !user.isComAssetsManager() && mtlMgrProps.equals("")) { //责任人限制
                if (!dto.getTransferType().equals("BTW_COMP")) {
                    sqlStr += " AND AAAV.RESPONSIBILITY_USER = ?";
                    sqlArgs.add(user.getEmployeeId());
                }
            }
            if (dto.getTransType().equals(AssetsDictConstant.ASS_RED)) { //调拨前提：正常或者闲置
                sqlStr += " AND (AAAV.ITEM_STATUS " + SyBaseSQLUtil.isNullNoParam() + "  OR AAAV.ITEM_STATUS = ? OR AAAV.ITEM_STATUS = ?)";
                sqlArgs.add(AssetsDictConstant.ITEM_STATUS_NORMAL);
                sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_FREED);
            } else if (dto.getTransType().equals(AssetsDictConstant.ASS_DIS)) { //闲置前提：正常
                sqlStr += " AND AAAV.ITEM_STATUS = ?";
                sqlArgs.add(AssetsDictConstant.ITEM_STATUS_NORMAL);
            } else if (dto.getTransType().equals(AssetsDictConstant.ASS_CLR)) { //报废前提：正常或者闲置
                sqlStr += " AND (AAAV.ITEM_STATUS = ? OR AAAV.ITEM_STATUS = ?)";
                sqlArgs.add(AssetsDictConstant.ITEM_STATUS_NORMAL);
                sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_FREED);
            } else if (dto.getTransType().equals(AssetsDictConstant.ASS_CLR)) { //处置前提：已报废或者闲置
                sqlStr += " AND (AAAV.ITEM_STATUS = ? OR AAAV.ITEM_STATUS = ?)";
                sqlArgs.add(AssetsDictConstant.ITEM_STATUS_DISCARDED);
                sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_FREED);
            } else if (dto.getTransType().equals(AssetsDictConstant.ASS_SUB)) { //减值前提：正常或者闲置
                sqlStr += " AND (AAAV.ITEM_STATUS = ? OR AAAV.ITEM_STATUS = ?)";
                sqlArgs.add(AssetsDictConstant.ITEM_STATUS_NORMAL);
                sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_FREED);
            }
            sqlStr = sqlStr
                    + " AND (AAAV.ASSETS_DESCRIPTION LIKE dbo.NVL(?, AAAV.ASSETS_DESCRIPTION) OR AAAV.ITEM_NAME LIKE dbo.NVL(?, AAAV.ITEM_NAME))"
                    + " AND AAAV.BARCODE LIKE dbo.NVL(?, AAAV.BARCODE)"
                    + " AND AAAV.RESPONSIBILITY_USER_NAME LIKE dbo.NVL(?, AAAV.RESPONSIBILITY_USER_NAME)"
                    + " AND AAAV.WORKORDER_OBJECT_CODE LIKE dbo.NVL(?, AAAV.WORKORDER_OBJECT_CODE)"
                    + " AND AAAV.PARENT_BARCODE " + SyBaseSQLUtil.isNullNoParam() + " "
                    + " AND  " + SyBaseSQLUtil.isNotNull("AAAV.ASSET_ID") + " "
                    + " AND NOT EXISTS ("
                    + " SELECT"
                    + " NULL"
                    + " FROM"
                    + " AMS_ASSETS_RESERVED AAR"
                    + " WHERE"
                    + " AAR.BARCODE = AAAV.BARCODE)"
                    + " AND NOT EXISTS (" //未同步的设备
                    + " SELECT"
                    + " NULL"
                    + " FROM"
                    + " AMS_ASSETS_CHK_LOG AACL"
                    + " WHERE"
                    + " AACL.BARCODE = AAAV.BARCODE"
                    + " AND AACL.ORDER_TYPE <> 'ASS-CHK'"
                    + " AND AACL.SYN_STATUS IN (0, 2))";
            sqlArgs.add(dto.getAssetsDescription());
            sqlArgs.add(dto.getAssetsDescription());
            sqlArgs.add(dto.getBarcode());
            sqlArgs.add(dto.getOldResponsibilityUserName());
            sqlArgs.add(dto.getOldLocationCode());
        } else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_ASSETS)) { //需要加上权限限制
            AmsAssetsTransLineDTO dto = (AmsAssetsTransLineDTO) dtoParameter;
            String transType = dto.getTransType();
            sqlStr = "SELECT"
                    + " AAAV.*"
                    + " FROM"
                    + " AMS_ASSETS_ADDRESS_V AAAV"
                    + " WHERE"
                    + " AAAV.ORGANIZATION_ID = ?"
                    + " AND AAAV.DEPT_CODE = ?"
                    + " AND (AAAV.ITEM_CATEGORY_NAME LIKE dbo.NVL(?, AAAV.ITEM_CATEGORY_NAME)"
                    + " OR AAAV.ITEM_NAME LIKE dbo.NVL(?, AAAV.ITEM_NAME)"
                    + " OR AAAV.ITEM_SPEC LIKE dbo.NVL(?, AAAV.ITEM_SPEC))"
                    + " AND AAAV.BARCODE LIKE dbo.NVL(?, AAAV.BARCODE)"
                    + " AND AAAV.PARENT_BARCODE " + SyBaseSQLUtil.isNullNoParam() + " "
                    + " AND NOT EXISTS ("
                    + " SELECT"
                    + " NULL"
                    + " FROM"
                    + " AMS_ASSETS_RESERVED AAR"
                    + " WHERE"
                    + " AAR.BARCODE = AAAV.BARCODE)";
            sqlArgs.add(user.getOrganizationId());
            sqlArgs.add(dto.getDeptCode());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getBarcode());
            if (transType.equals(AssetsDictConstant.ASS_CLR)) {
                sqlStr += " AND (AAAV.ITEM_STATUS " + SyBaseSQLUtil.isNullNoParam() + "  OR AAAV.ITEM_STATUS = ?)";
                sqlArgs.add(AssetsDictConstant.ITEM_STATUS_DISCARDED);
            } else {
                sqlStr += " AND (AAAV.ITEM_STATUS " + SyBaseSQLUtil.isNullNoParam() + "  OR AAAV.ITEM_STATUS = ?)";
                sqlArgs.add(AssetsDictConstant.ITEM_STATUS_NORMAL);
            }
        } else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_COMPANY)) {
            EtsOuCityMapDTO dto = (EtsOuCityMapDTO) dtoParameter;
            sqlStr = "SELECT"
                    + " EOCM.ORGANIZATION_ID,"
                    + " EOCM.COMPANY_CODE,"
                    + " EOCM.COMPANY COMPANY_NAME"
                    + " FROM"
                    + " ETS_OU_CITY_MAP EOCM"
                    + " WHERE"
                    + " EOCM.COMPANY LIKE dbo.NVL(?, EOCM.COMPANY)";
            sqlArgs.add(dto.getCompany());
        } else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_PRI_DEPT)) {
            AmsAssetsPriviDTO dto = (AmsAssetsPriviDTO) dtoParameter;
            sqlStr = "SELECT"
                    + " AMD.COMPANY_CODE,"
                    + " EOCM.COMPANY COMPANY_NAME,"
                    + " AMD.DEPT_CODE,"
                    + " AMD.DEPT_NAME"
                    + " FROM"
                    + " AMS_MIS_DEPT    AMD,"
                    + " ETS_OU_CITY_MAP EOCM"
                    + " WHERE"
                    + " AMD.COMPANY_CODE = EOCM.COMPANY_CODE"
                    + " AND EOCM.COMPANY_CODE = dbo.NVL(?, EOCM.COMPANY_CODE)"
                    + " AND EOCM.COMPANY LIKE dbo.NVL(?, EOCM.COMPANY)"
                    + " AND AMD.DEPT_NAME LIKE dbo.NVL(?, AMD.DEPT_NAME)";
            sqlArgs.add(dto.getCompanyCode());
            sqlArgs.add(dto.getCompanyName());
            sqlArgs.add(dto.getDeptName());
        } else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_PRI_ROLE)) {
            String assetsMgr = "'" + servletConfig.getDeptAssetsMgr() + "', ";
            assetsMgr += "'" + servletConfig.getCompAssetsMgr() + "', ";
            assetsMgr += "'" + servletConfig.getMtlAssetsMgr() + "', ";
            if (!user.isProvinceUser()) {
                assetsMgr += "'" + servletConfig.getProvAssetsMgr() + "'";
            } else {
                assetsMgr = assetsMgr.substring(0, assetsMgr.length() - 2);
            }
            sqlStr = "SELECT"
                    + " SR.ROLE_ID,"
                    + " SR.ROLE_NAME ROLE_NAME,"
                    + " SR.ROLE_DESC"
                    + " FROM"
                    + " SF_ROLE SR"
                    + " WHERE"
                    + " SR.ROLE_NAME IN ("
                    + assetsMgr
                    + ")";
        } else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_USER)) {
            AmsAssetsPriviDTO dto = (AmsAssetsPriviDTO) dtoParameter;
            sqlStr = "SELECT"
                    + " EOCM.COMPANY_CODE,"
                    + " EOCM.COMPANY COMPANY_NAME,"
                    + " SU.LOGIN_NAME,"
                    + " SU.USER_ID,"
                    + " SU.USERNAME USER_NAME,"
                    + " SU.EMPLOYEE_NUMBER"
                    + " FROM"
                    + " SF_USER         SU,"
                    + " ETS_OU_CITY_MAP EOCM"
                    + " WHERE"
                    + " SU.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
                    + " AND (SU.DISABLE_DATE " + SyBaseSQLUtil.isNullNoParam() + "  OR SU.DISABLE_DATE > GETDATE())"
                    + " AND SU.ORGANIZATION_ID = ?"
                    + " AND SU.USERNAME LIKE dbo.NVL(?, SU.USERNAME)"
                    + " AND SU.LOGIN_NAME LIKE dbo.NVL(UPPER(?), SU.LOGIN_NAME)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR SU.EMPLOYEE_NUMBER LIKE dbo.NVL(?, SU.EMPLOYEE_NUMBER))"
                    + " AND EXISTS("
                    + " SELECT"
                    + " NULL"
                    + " FROM"
                    + " SF_USER_RIGHT SUR"
                    + " WHERE"
                    + " SUR.USER_ID = SU.USER_ID"
                    + " AND SUR.GROUP_ID = CONVERT(INT, dbo.NVL(?, CONVERT(VARCHAR, SUR.GROUP_ID))))";
            sqlArgs.add(user.getOrganizationId());
            sqlArgs.add(dto.getUserName());
            sqlArgs.add(dto.getLoginName());
            sqlArgs.add(dto.getEmployeeNumber());
            sqlArgs.add(dto.getEmployeeNumber());
            if (proCode.equals("42")) {
                sqlArgs.add("");
            } else {
                sqlArgs.add(dto.getGroupId());
            }

        } else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_PERSON)) {
            SfUserDTO dto = (SfUserDTO) dtoParameter;
            sqlStr = "SELECT"
                    + " AME.EMPLOYEE_ID,"
                    + " AME.USER_NAME,"
                    + " AME.EMPLOYEE_NUMBER,"
                    + " AMD.DEPT_NAME,"
                    + " EOCM.COMPANY,"
                    + " EOCM.ORGANIZATION_ID"
                    + " FROM"
                    + " AMS_MIS_EMPLOYEE AME,"
                    + " AMS_MIS_DEPT     AMD,"
                    + " ETS_OU_CITY_MAP  EOCM"
                    + " WHERE"
                    + " AME.DEPT_CODE *= AMD.DEPT_CODE"
                    + " AND AMD.COMPANY_CODE = EOCM.COMPANY_CODE"
                    + " AND AME.USER_NAME LIKE dbo.NVL(?, AME.USER_NAME)"
                    + " AND AME.EMPLOYEE_NUMBER LIKE dbo.NVL(?, AME.EMPLOYEE_NUMBER)"
                    + " AND EOCM.ORGANIZATION_ID = ?"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR AMD.DEPT_CODE = ?)";
            sqlArgs.add(dto.getUsername());
            sqlArgs.add(dto.getEmployeeNumber());
            sqlArgs.add(user.getOrganizationId());
            sqlArgs.add(dto.getDeptCode());
            sqlArgs.add(dto.getDeptCode());
        } else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_LOCATION)) {
            EtsObjectDTO dto = (EtsObjectDTO) dtoParameter;
            sqlStr = "SELECT"
                    + " EOCM.COMPANY COMPANY_NAME,"
                    + " EC.COUNTY_NAME,"
                    + " EFV.VALUE OBJECT_CATEGORY,"
                    + " EO.WORKORDER_OBJECT_NO CHECK_LOCATION,"
                    + " EO.WORKORDER_OBJECT_CODE OBJECT_CODE,"
                    + " EO.WORKORDER_OBJECT_NAME OBJECT_NAME,"
                    + " EO.WORKORDER_OBJECT_LOCATION OBJECT_LOCATION"
                    + " FROM"
                    + " ETS_OBJECT         EO,"
                    + " ETS_COUNTY         EC,"
                    + " ETS_FLEX_VALUE_SET EFVS,"
                    + " ETS_FLEX_VALUES    EFV,"
                    + " ETS_OU_CITY_MAP    EOCM"
                    + " WHERE"
                    + " EO.COUNTY_CODE *= EC.COUNTY_CODE"
                    + " AND EO.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
                    + " AND EC.COMPANY_CODE = EOCM.COMPANY_CODE"
                    + " AND EO.OBJECT_CATEGORY = EFV.CODE"
                    + " AND EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID"
                    + " AND EFVS.CODE = ?"
                    + " AND EO.ORGANIZATION_ID = ?"
                    + " AND (EO.OBJECT_CATEGORY < 70 OR EO.OBJECT_CATEGORY = 80)"
                    + " AND ((EO.DISABLE_DATE IS NULL OR EO.DISABLE_DATE='')  OR EO.DISABLE_DATE > GETDATE())"
                    + " AND EC.COUNTY_NAME LIKE dbo.NVL(?, EC.COUNTY_NAME)"
                    + " AND EO.WORKORDER_OBJECT_CODE LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_CODE)"
                    + " AND EO.WORKORDER_OBJECT_NAME LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_NAME)"
//					+ " AND NOT EXISTS("
//					+ " SELECT"
//					+ " NULL"
//					+ " FROM"
//					+ " AMS_ASSETS_CHECK_HEADER AACH"
//					+ " WHERE"
//					+ " EO.WORKORDER_OBJECT_NO = AACH.CHECK_LOCATION"
//					+ " AND AACH.ORDER_STATUS <> ?"
//					+ " AND AACH.ORDER_STATUS <> ?)"
                    ;
            sqlArgs.add(AssetsDictConstant.OBJECT_CATEGORY);
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getCountyName());
            sqlArgs.add(dto.getWorkorderObjectCode());
            sqlArgs.add(dto.getWorkorderObjectName());
//			sqlArgs.add(AssetsDictConstant.CHK_STATUS_ARCHIEVED);
//			sqlArgs.add(AssetsDictConstant.CHK_STATUS_CANCELED);
        } else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_FACAT_CODE)) { //选择资产类别
            AmsFaCategoryVDTO dto = (AmsFaCategoryVDTO) dtoParameter;
            sqlStr = "SELECT"
                    + " *"
                    + " FROM"
                    + " AMS_FA_CATEGORY_V AFCV"
                    + " WHERE"
                    + " AFCV.FA_CAT_NAME_1 LIKE dbo.NVL(?, AFCV.FA_CAT_NAME_1)"
                    + " AND AFCV.FA_CAT_NAME_2 LIKE dbo.NVL(?, AFCV.FA_CAT_NAME_2)"
                    + " AND AFCV.FA_CAT_NAME_3 LIKE dbo.NVL(?, AFCV.FA_CAT_NAME_3)";
            sqlArgs.add(dto.getFaCatName1());
            sqlArgs.add(dto.getFaCatName2());
            sqlArgs.add(dto.getFaCatName3());
        } else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_ACCOUNT)) { //选择折旧账户
            AmsAccountVDTO dto = (AmsAccountVDTO) dtoParameter;
            sqlStr = "SELECT"
                    + " AAV.*"
                    + " FROM"
                    + " AMS_ACCOUNT_V        AAV,"
                    + " GL_CODE_COMBINATIONS GCC,"
                    + " ETS_OU_CITY_MAP      EOCM"
                    + " WHERE"
                    + " AAV.ACCOUNT_CODE_1 = GCC.SEGMENT1"
                    + " AND GCC.SEGMENT1 = EOCM.COMPANY_CODE"
                    + " AND AAV.ACCOUNT_CODE_2 = GCC.SEGMENT2"
                    + " AND AAV.ACCOUNT_CODE_3 = GCC.SEGMENT3"
                    + " AND AAV.ACCOUNT_CODE_4 = GCC.SEGMENT4"
                    + " AND AAV.ACCOUNT_CODE_5 = GCC.SEGMENT5"
                    + " AND AAV.ACCOUNT_CODE_6 = GCC.SEGMENT6"
                    + " AND AAV.ACCOUNT_CODE_7 = GCC.SEGMENT7"
                    + " AND (AAV.ACCOUNT_NAME_2 LIKE dbo.NVL(?, AAV.ACCOUNT_NAME_2)"
                    + " OR GCC.SEGMENT2 LIKE dbo.NVL(?, GCC.SEGMENT2))"
                    + " AND (AAV.ACCOUNT_NAME_3 LIKE dbo.NVL(?, AAV.ACCOUNT_NAME_3)"
                    + " OR GCC.SEGMENT3 LIKE dbo.NVL(?, GCC.SEGMENT3))"
                    + " AND GCC.ACCOUNT_TYPE = 'E'" //表示是费用账户(高朗意见)
                    + " AND GCC.SEGMENT4 = '000000'"
                    + " AND GCC.SEGMENT5 = '00000000'"
                    + " AND GCC.SEGMENT6 = '0000'"
                    + " AND GCC.SEGMENT7 = '000000'"
                    + " AND EOCM.ORGANIZATION_ID = ?"; //徐培政建议增加条件
            sqlArgs.add(dto.getAccountName2());
            sqlArgs.add(dto.getAccountName2());
            sqlArgs.add(dto.getAccountName3());
            sqlArgs.add(dto.getAccountName3());
            sqlArgs.add(dto.getOrganizationId());
        } else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_VENDOR)) {
            AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
            sqlStr = "SELECT"
                    + " EMPV.VENDOR_ID,"
                    + " EMPV.VENDOR_NAME,"
                    + " EMPV.SEGMENT1 VENDOR_NUMBER,"
                    + " EMPV.VENDOR_NAME_ALT"
                    + " FROM"
                    + " ETS_MIS_PO_VENDORS EMPV"
                    + " WHERE"
                    + " EMPV.VENDOR_NAME LIKE dbo.NVL(?, EMPV.VENDOR_NAME)"
                    + " AND EMPV.SEGMENT1 LIKE dbo.NVL(?, EMPV.SEGMENT1)";
            sqlArgs.add(dto.getVendorName());
            sqlArgs.add(dto.getVendorNumber());
        } else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_PROJECT)) {
            EtsPaProjectsAllDTO dto = (EtsPaProjectsAllDTO) super.dtoParameter;
            sqlStr = "SELECT"
                    + " EPPA.PROJECT_ID,"
                    + " EPPA.NAME PROJECT_NAME,"
                    + " EPPA.SEGMENT1 PROJECT_NUMBER,"
                    + " EPPA.PROJECT_TYPE "
                    + " FROM"
                    + " ETS_PA_PROJECTS_ALL EPPA"
                    + " WHERE"
                    + " EPPA.SEGMENT1 LIKE dbo.NVL(?, EPPA.SEGMENT1)"
                    + " AND EPPA.NAME LIKE dbo.NVL(?, EPPA.NAME)";
            sqlArgs.add(dto.getSegment1());
            sqlArgs.add(dto.getName());
        } else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_COST)) {
            AmsMisCostDTO dto = (AmsMisCostDTO) super.dtoParameter;
            sqlStr = "SELECT"
                    + " ACCV.COST_CENTER_CODE COST_CODE,"
                    + " ACCV.COST_CENTER_NAME COST_NAME"
                    + " FROM"
                    + " AMS_COST_CENTER_V   ACCV"
                    + " WHERE"
                    + " EXISTS("
                    + " SELECT"
                    + " NULL"
                    + " FROM"
                    + " AMS_COST_DEPT_MATCH ACDM"
                    + " WHERE"
                    + " ACCV.COST_CENTER_CODE = ACDM.COST_CENTER_CODE"
                    + " AND ACCV.IS_VIRTUAL_COST = 'N')"
                    + " AND ACCV.ENABLED_FLAG = 'Y'"
                    + " AND ACCV.COST_CENTER_CODE LIKE dbo.NVL(?, ACCV.COST_CENTER_CODE)"
                    + " AND ACCV.COST_CENTER_NAME LIKE dbo.NVL(?, ACCV.COST_CENTER_NAME)";
            sqlArgs.add(dto.getCostCode());
            sqlArgs.add(dto.getCostName());
        } else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_ITEMNAME)) {
            EtsSystemItemDTO dto = (EtsSystemItemDTO) super.dtoParameter;
            sqlStr = "SELECT"
                    + " DISTINCT ESI.ITEM_NAME"
                    + " FROM"
                    + " ETS_SYSTEM_ITEM        ESI,"
                    + " ETS_SYSITEM_DISTRIBUTE ESD"
                    + " WHERE"
                    + " ESI.ITEM_CODE = ESD.ITEM_CODE"
                    + " AND ESD.ORGANIZATION_ID = ?"
                    + " AND ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME)";
            sqlArgs.add(user.getOrganizationId());
            sqlArgs.add(dto.getItemName());
        } else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_MISLOC)) {
            AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
            sqlStr = "SELECT"
                    + " EFAL.ASSETS_LOCATION_CODE,"
                    + " EFAL.ASSETS_LOCATION"
                    + " FROM"
                    + " ETS_FA_ASSETS_LOCATION EFAL"
                    + " WHERE"
                    + " EFAL.ASSETS_LOCATION LIKE dbo.NVL(?, EFAL.ASSETS_LOCATION)"
                    + " AND EFAL.ASSETS_LOCATION_CODE LIKE dbo.NVL(?, EFAL.ASSETS_LOCATION_CODE)"
                    + " AND EFAL.BOOK_TYPE_CODE = ?";
            sqlArgs.add(dto.getAssetsLocation());
            sqlArgs.add(dto.getAssetsLocationCode());
            sqlArgs.add(user.getBookTypeCode());
        } else if (lookUpName.equals(LookUpCtConstant.LOOK_UP_SYS_ITEM_NAME)) {
            EtsItemInfoDTO dto = (EtsItemInfoDTO) dtoParameter;
            sqlStr = "SELECT"
                    + " ESI.ITEM_CODE,"
                    + " ESI.ITEM_NAME,"
                    + " ESI.ITEM_SPEC"
                    + " FROM"
                    + " ETS_SYSTEM_ITEM ESI"
                    + " WHERE"
                    + " ESI.ITEM_CODE LIKE dbo.NVL(?, ESI.ITEM_CODE)";
            sqlArgs.add(dto.getItemCode());
        } else if (lookUpName.equals(LookUpCtConstant.LOOK_UP_SYS_ITEM_SPEC)) {
            EtsItemInfoDTO dto = (EtsItemInfoDTO) dtoParameter;
            sqlStr = "SELECT"
                    + " ESI.ITEM_CODE,"
                    + " ESI.ITEM_NAME,"
                    + " ESI.ITEM_SPEC"
                    + " FROM"
                    + " ETS_SYSTEM_ITEM ESI"
                    + " WHERE"
                    + " ESI.ITEM_CODE LIKE dbo.NVL(?, ESI.ITEM_CODE)";
            sqlArgs.add(dto.getItemCode());
        }
        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);
    }

}
