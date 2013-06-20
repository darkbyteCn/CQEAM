package com.sino.ams.inv.storeman.bean;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.AMSLookUpModel;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.constant.LookUpConstant;
import com.sino.ams.ct.bean.LookUpCtConstant;
import com.sino.ams.ct.dto.EtsFaAssetsDTO;
import com.sino.ams.inv.dzyh.dto.EamDhBillLDTO;
import com.sino.ams.inv.storeman.dto.EamInvStoremanDTO;
import com.sino.ams.plan.dto.AmsWorkPlanDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.lookup.LookUpProp;
import com.sino.framework.dto.BaseUserDTO;

public class AMSInvLookUpModel extends AMSLookUpModel {

    private SfUserDTO user = null;

    public AMSInvLookUpModel(BaseUserDTO userAccount, DTO dtoParameter, LookUpProp lookProp) {
        super(userAccount, dtoParameter, lookProp);
        this.user = (SfUserDTO) userAccount;
    }

    /**
     * 功能：构造查询SQL。由具体需要LookUp操作的应用实现
     */
    protected void produceSQLModel() {
        sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "";
        String lookUpName = lookProp.getLookUpName();
        if (lookUpName.equals(LookUpConstant.LOOK_UP_USER)) {
            AmsWorkPlanDTO dto = (AmsWorkPlanDTO) dtoParameter;
            sqlStr = "SELECT"
                    + " SU.USER_ID EXECUTE_USER,"
                    + " SU.LOGIN_NAME,"
                    + " SU.USERNAME EXECUTE_USER_NAME"
                    + " FROM"
                    + " SF_USER SU"
                    + " WHERE"
                    + " SU.LOGIN_NAME LIKE dbo.NVL(?, SU.LOGIN_NAME)"
                    + " AND SU.USERNAME LIKE dbo.NVL(?, SU.USERNAME)"
                    + " AND SU.ORGANIZATION_ID = ?";
            sqlArgs.add(dto.getLoginName().toUpperCase());
            sqlArgs.add(dto.getExecuteUserName());
            sqlArgs.add(user.getOrganizationId());
        } else if (lookUpName.equals(LookUpCtConstant.LOOK_UP_ETS_FA_CT_ASSETS_MODEL_NUMBER)) {
            EtsFaAssetsDTO dto = (EtsFaAssetsDTO) dtoParameter;
            sqlStr = "SELECT"
                    + " EFCA.TAG_NUMBER,"
                    + " EFCA.ASSETS_DESCRIPTION,"
                    + " EFCA.MODEL_NUMBER"
                    + " FROM"
                    + " ETS_FA_CT_ASSETS EFCA"
                    + " WHERE"
                    + " EFCA.TAG_NUMBER LIKE dbo.NVL(?, EFCA.TAG_NUMBER)";
            sqlArgs.add(dto.getTagNumber());
        } else if (lookUpName.equals(LookUpInvConstant.LOOK_UP_WORKORDER_OBJECT_NO)) {
            EamInvStoremanDTO dto = (EamInvStoremanDTO) dtoParameter;
            sqlStr = "SELECT"
                    + " EO.WORKORDER_OBJECT_NO,"
                    + " EO.WORKORDER_OBJECT_NAME,"
                    + " EFV.VALUE INV_CATEGORY_NAME,"
                    + " AMS_PUB_PKG.GET_FLEX_VALUE(EO.BUSINESS_CATEGORY, 'INV_BIZ_CATEGORY') BIZ_CATEGORY_NAME,"
                    + " EO.WORKORDER_OBJECT_LOCATION"
                    + " FROM"
                    + " ETS_OBJECT EO,"
                    + " ETS_FLEX_VALUES EFV,"
                    + " ETS_FLEX_VALUE_SET EFVS"
                    + " WHERE"
                    + " EFVS.CODE = 'INV_TYPE'"
                    + " AND EO.OBJECT_CATEGORY = EFV.CODE"
                    + " AND EFVS.FLEX_VALUE_SET_ID = EFV.FLEX_VALUE_SET_ID"
                    + " AND EO.ORGANIZATION_ID = ?"
                    + " AND EO.WORKORDER_OBJECT_NO LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_NO)"
                    + " AND EO.WORKORDER_OBJECT_NAME LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_NAME)"
                    + " AND EO.WORKORDER_OBJECT_LOCATION LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_LOCATION)";
            sqlArgs.add(user.getOrganizationId());
            sqlArgs.add(dto.getWorkorderObjectNo());
            sqlArgs.add(dto.getWorkorderObjectName());
            sqlArgs.add(dto.getWorkorderObjectLocation());
        } else if (lookUpName.equals(LookUpInvConstant.LOOK_UP_USER_ID)) {
            SfUserDTO dto = (SfUserDTO) dtoParameter;
            sqlStr = "SELECT"
                    + " SU.USER_ID,"
                    + " SU.USERNAME USER_NAME"
                    + " FROM"
                    + " SF_USER SU"
                    + " WHERE"
                    + " SU.USER_ID = ISNULL(?, SU.USER_ID)"
                    + " AND SU.USERNAME LIKE dbo.NVL(?, SU.USERNAME)";
            sqlArgs.add(dto.getUserId());
            sqlArgs.add(dto.getUsername());
        } else if (lookUpName.equals(LookUpInvConstant.LOOK_UP_UPDATED_USER)) {
            SfUserDTO dto = (SfUserDTO) dtoParameter;
            sqlStr = "SELECT"
                    + " SU.USER_ID LAST_UPDATE_BY,"
                    + " SU.USERNAME UPDATED_USER"
                    + " FROM"
                    + " SF_USER SU"
                    + " WHERE"
                    + " SU.USER_ID = ISNULL(?, SU.USER_ID)"
                    + " AND SU.USERNAME LIKE dbo.NVL(?, SU.USERNAME)";
            sqlArgs.add(dto.getUserId());
            sqlArgs.add(dto.getUsername());
        } else if (lookUpName.equals(LookUpInvConstant.LOOK_UP_CREATED_USER)) {
            SfUserDTO dto = (SfUserDTO) dtoParameter;
            sqlStr = "SELECT"
                    + " SU.USER_ID CREATED_BY,"
                    + " SU.USERNAME CREATED_USER"
                    + " FROM"
                    + " SF_USER SU"
                    + " WHERE"
                    + " SU.USER_ID = ISNULL(?, SU.USER_ID)"
                    + " AND SU.USERNAME LIKE dbo.NVL(?, SU,USERNAME)";
            sqlArgs.add(dto.getUserId());
            sqlArgs.add(dto.getUsername());
        } else if (lookUpName.equals(LookUpInvConstant.LOOK_UP_CATALOG_VALUE_ID)) {
            EamDhBillLDTO dto = (EamDhBillLDTO) dtoParameter;
            sqlStr = "SELECT"
                    + " EDCV.CATALOG_VALUE_ID,"
                    + " EDCV.CATALOG_NAME"
                    + " FROM"
                    + " EAM_DH_CATALOG_VALUES EDCV"
                    + " WHERE"
                    + " EDCV.CATALOG_VALUE_ID LIKE dbo.NVL(?, EDCV.CATALOG_VALUE_ID)"
                    + " AND EDCV.CATALOG_NAME LIKE dbo.NVL(?, EDCV.CATALOG_NAME)";
            sqlArgs.add(dto.getCatalogValueId());
            sqlArgs.add(dto.getItemName());
        } else if (lookUpName.equals(LookUpInvConstant.LOOK_UP_RESPONSIBILITY_DEPT)) {
            EamDhBillLDTO dto = (EamDhBillLDTO) dtoParameter;
            sqlStr = "SELECT"
                    + " AMD.DEPT_CODE,"
                    + " AMD.DEPT_NAME"
                    + " FROM"
                    + " AMS_MIS_DEPT AMD"
                    + " WHERE"
                    + " AMD.DEPT_CODE LIKE dbo.NVL(?, AMD.DEPT_CODE)"
                    + " AND AMD.DEPT_NAME LIKE dbo.NVL(?, AMD.DEPT_NAME)";

            sqlArgs.add(dto.getDeptCode());
            sqlArgs.add(dto.getDeptName());
        } else if (lookUpName.equals(LookUpInvConstant.LOOK_UP_RESPONSIBILITY_USER)) {
            EamDhBillLDTO dto = (EamDhBillLDTO) dtoParameter;
            sqlStr = "SELECT"
                    + " EII.BARCODE,"
                    + " EII.RESPONSIBILITY_USER,"
                    + " AME.EMPLOYEE_ID,"
                    + " AME.USER_NAME"
                    + " FROM"
                    + " ETS_ITEM_INFO EII,"
                    + " AMS_MIS_EMPLOYEE AME"
                    + " WHERE";
            if (dto.getUserName().equals("")) {
                sqlStr += " EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID";
            } else {
                sqlStr += " EII.RESPONSIBILITY_USER = AME.EMPLOYEE_ID";
            }
            sqlStr += " AND EII.BARCODE LIKE dbo.NVL(?, EII.BARCODE)"
                    + " AND AME.EMPLOYEE_ID = CONVERT(INT, dbo.NVL(?, CONVERT(VARCHAR, AME.EMPLOYEE_ID)))";
            sqlArgs.add(dto.getBarcode());
            sqlArgs.add(dto.getEmployeeId());
            if (!dto.getUserName().equals("")) {
                sqlStr += " AND AME.USER_NAME LIKE ?";
                sqlArgs.add(dto.getUserName());
            }
        } else if (lookUpName.equals(LookUpConstant.BJSL_ITEM_INFO)) {
            EamDhBillLDTO dto = (EamDhBillLDTO) dtoParameter;
            sqlStr = "SELECT"
                    + " EII.BARCODE,"
                    + " EII.ITEM_CODE,"
                    + " ESI.ITEM_NAME,"
                    + " ESI.ITEM_SPEC"
                    + " FROM"
                    + " ETS_ITEM_INFO EII,"
                    + " ETS_SYSTEM_ITEM ESI,"
                    + " AMS_OBJECT_ADDRESS AOA,"
                    + " ETS_OBJECT EO"
                    + " WHERE"
                    + " EII.ITEM_CODE = ESI.ITEM_CODE"
                    + " AND EII.ADDRESS_ID = AOA.ADDRESS_ID"
                    + " AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO"
                    + " AND EO.WORKORDER_OBJECT_NO = dbo.NVL(?, EO.WORKORDER_OBJECT_NO)"
                    + " AND EO.ORGANIZATION_ID = CONVERT(?, EO.ORGANIZATION_ID)";
            sqlArgs.add(dto.getWorkorderObjectNo());
            sqlArgs.add(dto.getOrganizationId());
        } else if (lookUpName.equals(LookUpConstant.LOOK_UP_SYS_ITEM)) {
            EamDhBillLDTO dto = (EamDhBillLDTO) dtoParameter;
            sqlStr = "SELECT"
                    + " ESI.ITEM_NAME,"
                    + " ESI.ITEM_SPEC,"
                    + " ESI.ITEM_CODE,"
                    + " EII.ORGANIZATION_ID"
                    + " FROM"
                    + " ETS_ITEM_INFO EII,"
                    + " ETS_SYSTEM_ITEM ESI"
                    + " WHERE"
                    + " ESI.ITEM_CODE = EII.ITEM_CODE"
                    + " AND ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?)"
                    + " AND EII.ORGANIZATION_ID = CONVERT(INT, dbo.NVL(?, CONVERT(VARCHAR, EII.ORGANIZATION_ID)))";
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(user.getOrganizationId());
        } else if (lookUpName.equals(LookUpInvConstant.LOOK_UP_BARCODE)) {
            EamDhBillLDTO dto = (EamDhBillLDTO) dtoParameter;
            sqlStr = "SELECT"
                    + " EDCV.BARCODE_FLAG,"
                    + " EDCV.CATALOG_NAME,"
                    + " EDCV.UNIT"
                    + " FROM"
                    + " EAM_DH_CATALOG_VALUES EDCV"
                    + " WHERE"
                    + " ( " + SyBaseSQLUtil.isNull() + "  OR EDCV.BARCODE_FLAG LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR EDCV.CATALOG_NAME LIKE ?)";

            sqlArgs.add(dto.getBarcodeFlag());
            sqlArgs.add(dto.getBarcodeFlag());
            sqlArgs.add(dto.getCatalogName());
            sqlArgs.add(dto.getCatalogName());
        } else if (lookUpName.equals(LookUpInvConstant.LOOK_UP_RESPONSIBILITY_EMPLOYEE)) {
            EamDhBillLDTO dto = (EamDhBillLDTO) dtoParameter;
            sqlStr = "SELECT"
                    + " AME.EMPLOYEE_ID,"
                    + " AME.USER_NAME,"
                    + " AME.EMPLOYEE_NUMBER"
                    + " FROM"
                    + " AMS_MIS_EMPLOYEE AME"
                    + " WHERE"
                    + " ( " + SyBaseSQLUtil.isNull() + "  OR AME.EMPLOYEE_ID = ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR AME.USER_NAME LIKE ?)";

            sqlArgs.add(dto.getEmployeeId());
            sqlArgs.add(dto.getEmployeeId());
            sqlArgs.add(dto.getUserName());
            sqlArgs.add(dto.getUserName());
        } else if (lookUpName.equals(LookUpInvConstant.LOOK_UP_WORKORDER_OBJECT_NO_DZYH)) { //选择低值易耗品仓库
            EamDhBillLDTO dto = (EamDhBillLDTO) dtoParameter;
            /*
               sqlStr = "SELECT"
                        + " EO.WORKORDER_OBJECT_NO,"
                        + " EO.WORKORDER_OBJECT_NAME,"
                        + " EO.WORKORDER_OBJECT_CODE"
                        + " FROM"
                        + " ETS_OBJECT EO,"
                        + " ETS_FLEX_VALUE_SET EFVS,"
                        + " ETS_FLEX_VALUES EFV"
                        + " WHERE"
                        + " EFVS.FLEX_VALUE_SET_ID = EFV.FLEX_VALUE_SET_ID"
                        + " AND EFVS.CODE = 'INV_TYPE'"
                        + " AND EFV.CODE = EO.OBJECT_CATEGORY"
                        + " AND EFV.CODE = '71'"
                        + " AND EO.EO.ORGANIZATION_ID = ?"
                        + " AND EO.WORKORDER_OBJECT_NO LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_NO)"
                        + " AND EO.WORKORDER_OBJECT_NAME LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_NAME)"
                        + " AND EO.WORKORDER_OBJECT_LOCATION LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_CODE)";
               */
            sqlStr = "SELECT"
                    + " EO.WORKORDER_OBJECT_NO,"
                    + " EO.WORKORDER_OBJECT_NAME,"
                    + " EO.WORKORDER_OBJECT_CODE"
                    + " FROM"
                    + " ETS_OBJECT EO"
                    + " WHERE"
                    + " EO.OBJECT_CATEGORY = 71"
                    + " AND EO.BUSINESS_CATEGORY = 'INV_BIZ_DZYH'"
                    + " AND EO.ORGANIZATION_ID = ?"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.WORKORDER_OBJECT_NO LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.WORKORDER_OBJECT_NAME LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.WORKORDER_OBJECT_CODE LIKE ?)";
            sqlArgs.add(user.getOrganizationId());
            sqlArgs.add(dto.getWorkorderObjectNo());
            sqlArgs.add(dto.getWorkorderObjectNo());
            sqlArgs.add(dto.getWorkorderObjectName());
            sqlArgs.add(dto.getWorkorderObjectName());
            sqlArgs.add(dto.getWorkorderObjectCode());
            sqlArgs.add(dto.getWorkorderObjectCode());
        } else if (lookUpName.equals(LookUpInvConstant.LOOK_UP_ITEM_CATEGORY2)) { //选择ETS_SYSTEM_ITEM表中的目录编号
            EamDhBillLDTO dto = (EamDhBillLDTO) dtoParameter;
            sqlStr = "SELECT"
                    + " ESI.ITEM_CODE,"
                    + " ESI.ITEM_NAME,"
                    + " ESI.ITEM_SPEC,"
                    + " ESI.ITEM_UNIT,"
                    + " ESI.ITEM_CATEGORY2"
                    + " FROM"
                    + " ETS_SYSTEM_ITEM ESI"
                    + " WHERE"
                    + " ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_CATEGORY2 LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_NAME LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?)";

            sqlArgs.add(dto.getItemCategory2());
            sqlArgs.add(dto.getItemCategory2());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getItemSpec());
        }

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);
    }
}
