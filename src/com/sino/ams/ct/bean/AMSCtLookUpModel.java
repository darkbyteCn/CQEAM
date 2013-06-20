package com.sino.ams.ct.bean;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.lookup.LookUpModel;
import com.sino.base.lookup.LookUpProp;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.LookUpConstant;
import com.sino.ams.ct.dto.EtsFaAssetsDTO;
import com.sino.ams.instrument.dto.AmsInstrumentInfoDTO;
import com.sino.ams.net.statistic.dto.EquipStatDTO;
import com.sino.ams.newasset.dto.AmsMisDeptDTO;
import com.sino.ams.newasset.dto.AmsMisEmployeeDTO;
import com.sino.ams.newasset.dto.AssetsTagNumberQueryDTO;
import com.sino.ams.newasset.dto.GroupDTO;
import com.sino.ams.others.cabel.dto.AmsCabelInfoDTO;
import com.sino.ams.others.dto.AmsInvStorageDTO;
import com.sino.ams.plan.dto.AmsWorkPlanDTO;
import com.sino.ams.spare.allot.dto.AmsBjsAllotDDto;
import com.sino.ams.spare.dto.AmsItemTransLDTO;
import com.sino.ams.spare.dto.AmsSpareCategoryDTO;
import com.sino.ams.spare.dto.AmsSpareInfoDTO;
import com.sino.ams.spare.dto.SpareReturnDTO;
import com.sino.ams.spare.repair.dto.AmsVendorInfoDTO;
import com.sino.ams.spare.version.dto.AmsItemVersionDTO;
import com.sino.ams.system.basepoint.dto.EtsObjectDTO;
import com.sino.ams.system.cost.dto.CostCenterDTO;
import com.sino.ams.system.fixing.dto.EtsItemInfoDTO;
import com.sino.ams.system.house.dto.AmsHouseInfoDTO;
import com.sino.ams.system.house.dto.AmsLandInfoDTO;
import com.sino.ams.system.item.dto.EtsMisPoVendorsDTO;
import com.sino.ams.system.item.dto.EtsSystemItemDTO;
import com.sino.ams.system.note.dto.AmsRentDeadlineDTO;
import com.sino.ams.system.project.dto.EtsPaProjectsAllDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.web.bts.dto.EtsObjectFixfeeDTO;
import com.sino.ams.workorder.dto.EtsWorkorderDTO;
import com.sino.ams.workorder.dto.EtsWorkorderTmpDTO;
import com.sino.framework.dto.BaseUserDTO;

public class AMSCtLookUpModel extends LookUpModel {

    private SfUserDTO user = null;

    public AMSCtLookUpModel(BaseUserDTO userAccount, DTO dtoParameter, LookUpProp lookProp) {
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
                    + "  WHERE "
                    + "  SU.LOGIN_NAME LIKE dbo.NVL(?, SU.LOGIN_NAME)  "
                    + "  AND SU.USERNAME LIKE dbo.NVL(?, SU.USERNAME)" +
                    "   AND SU.ORGANIZATION_ID = ?";
            sqlArgs.add(dto.getLoginName().toUpperCase());
            sqlArgs.add(dto.getExecuteUserName());
            sqlArgs.add(user.getOrganizationId());
        } else if (lookUpName.equals(LookUpConstant.LOOK_UP_KEEP)) {
            AmsWorkPlanDTO dto = (AmsWorkPlanDTO) dtoParameter;
            sqlStr = "SELECT SU.USER_ID CURR_KEEP_USER, SU.USERNAME DNAME, SU.LOGIN_NAME\n" +
                    "  FROM SF_USER SU\n" +
                    " WHERE SU.ORGANIZATION_ID = ?\n" +
                    "   AND SU.LOGIN_NAME LIKE dbo.NVL(?, SU.LOGIN_NAME)\n" +
                    "   AND SU.USERNAME LIKE dbo.NVL(?, SU.USERNAME)";
            sqlArgs.add(user.getOrganizationId());
            sqlArgs.add(dto.getLoginName());
            sqlArgs.add(dto.getExecuteUserName());
        } else if (lookUpName.equals(LookUpConstant.LOOK_UP_VENDOR)) {
            EtsMisPoVendorsDTO dto = (EtsMisPoVendorsDTO) dtoParameter;
            sqlStr = "SELECT"
                    + " EMPV.VENDOR_ID,"
                    + " EMPV.VENDOR_NAME,"
                    + " EMPV.SEGMENT1"
                    + " FROM"
                    + " ETS_MIS_PO_VENDORS EMPV"
                    + " WHERE"
                    + " ( " + SyBaseSQLUtil.isNull() + "  OR EMPV.SEGMENT1 LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR EMPV.VENDOR_NAME LIKE ?)";
            sqlArgs.add(dto.getSegment1());
            sqlArgs.add(dto.getSegment1());
            sqlArgs.add(dto.getVendorName());
            sqlArgs.add(dto.getVendorName());
        } else if (lookUpName.equals(LookUpConstant.LOOK_UP_BTS)) {
            EtsObjectFixfeeDTO dto = (EtsObjectFixfeeDTO) dtoParameter;
            sqlStr = "SELECT EO.WORKORDER_OBJECT_NO,\n" +
                    "       EO.WORKORDER_OBJECT_NAME,\n" +
                    "       EO.WORKORDER_OBJECT_LOCATION," +
                    "       EO.WORKORDER_OBJECT_CODE\n" +
                    "  FROM ETS_OBJECT EO\n" +
                    " WHERE EO.WORKORDER_OBJECT_NAME LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_NAME)\n" +
                    "   AND EO.WORKORDER_OBJECT_LOCATION LIKE  dbo.NVL(?, EO.WORKORDER_OBJECT_LOCATION)\n" +
                    "   AND EO.WORKORDER_OBJECT_CODE LIKE  dbo.NVL(?, EO.WORKORDER_OBJECT_CODE)\n" +
                    "   AND EO.ORGANIZATION_ID = ?";
            sqlArgs.add(dto.getWorkorderObjectName());
            sqlArgs.add(dto.getWorkorderObjectLocation());
            sqlArgs.add(dto.getWorkorderObjectCode());
            sqlArgs.add(user.getOrganizationId());

        } else if (lookUpName.equals(LookUpConstant.LOOK_UP_SYS_ITEM)) {
            EtsSystemItemDTO dto = (EtsSystemItemDTO) dtoParameter;
            sqlStr = "SELECT ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       ESI.ITEM_CODE,\n" +
                    "       ESD.ORGANIZATION_ID,\n" +
                    "       AMS_PUB_PKG.GET_VENDOR_NAME(ESI.VENDOR_ID) VENDOR_NAME,\n" +
                    "       ESI.VENDOR_ID\n" +
                    "  FROM ETS_SYSITEM_DISTRIBUTE ESD,\n" +
                    "       ETS_SYSTEM_ITEM ESI\n" +
                    " WHERE ESI.ITEM_CODE = ESD.ITEM_CODE\n" +
                    "   AND ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?)\n" +
                    "   AND ESI.ITEM_CATEGORY = dbo.NVL(?, ESI.ITEM_CATEGORY)\n" +
                    "   AND ESD.ORGANIZATION_ID = ?";
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getItemCategory());
//			if ((!user.isProvinceUser()) && (!user.isSysAdmin())) {
                //sqlStr += "AND ESD.ORGANIZATION_ID = ?";
                sqlArgs.add(user.getOrganizationId());
            //}
        } else if (lookUpName.equals(LookUpConstant.LOOK_UP_BEIJIAN_ITEM)) {
            EtsSystemItemDTO dto = (EtsSystemItemDTO) dtoParameter;
            sqlStr = "SELECT ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       EII.SYSTEMID,\n" +
                    "       ESI.ITEM_CODE,\n" +
                    "       EII.ORGANIZATION_ID,\n" +
                    "       EII.BARCODE\n" +
                    "  FROM ETS_ITEM_INFO EII, ETS_SYSTEM_ITEM ESI\n" +
                    " WHERE ESI.ITEM_CODE *= EII.ITEM_CODE\n" +
                    "   AND EII.FINANCE_PROP = 'SPARE'\n" +
                    "   AND NOT EXISTS\n" +
                    " (SELECT 1 FROM ETS_ITEM_FIXFEE EIF WHERE EIF.BARCODE = EII.BARCODE)\n" +
                    "   AND ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?)\n" +
                    "   AND ESI.ITEM_CATEGORY = dbo.NVL(?, ESI.ITEM_CATEGORY)\n" +
                    "   AND EII.ORGANIZATION_ID = ISNULL(?, EII.ORGANIZATION_ID)";
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getItemCategory());
            sqlArgs.add(user.getOrganizationId());

        } else if (lookUpName.equals(LookUpConstant.LOOK_UP_PROJECT)) {
            EtsPaProjectsAllDTO dtoParameter = (EtsPaProjectsAllDTO) super.dtoParameter;
            sqlStr = "SELECT " +
                    " PROJECT_ID PRJ_ID, NAME PRJ_NAME, SEGMENT1, PROJECT_TYPE " +
                    " FROM ETS_PA_PROJECTS_ALL " +
                    " WHERE ( " + SyBaseSQLUtil.isNull() + "  OR SEGMENT1 LIKE ?)" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR NAME LIKE ?)"+
                    "  ORDER BY SEGMENT1 ";
            sqlArgs.add(dtoParameter.getSegment1());
            sqlArgs.add(dtoParameter.getSegment1());
            sqlArgs.add(dtoParameter.getName());
            sqlArgs.add(dtoParameter.getName());
        } else if (lookUpName.equals(LookUpConstant.LOOK_UP_ASSETS)) {
            EtsFaAssetsDTO dto = (EtsFaAssetsDTO) dtoParameter;
            sqlStr = "SELECT *\n" +
                    "  FROM AMS_FA_ASSETS EFA\n" +
                    " WHERE ( " + SyBaseSQLUtil.isNull() + "  OR EFA.ASSETS_DESCRIPTION LIKE ?)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.MODEL_NUMBER LIKE ?)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.TAG_NUMBER LIKE ?)\n" +
                    "   AND NOT EXISTS (SELECT 1\n" +
                    "          FROM AMS_ASSETS_RESERVED AAR\n" +
                    "         WHERE AAR.ASSETS_ID = EFA.ASSET_ID)";
            sqlArgs.add(dto.getAssetsDescription());
            sqlArgs.add(dto.getAssetsDescription());
            sqlArgs.add(dto.getModelNumber());
            sqlArgs.add(dto.getModelNumber());
            sqlArgs.add(dto.getTagNumber());
            sqlArgs.add(dto.getTagNumber());
        } else if (lookUpName.equals(LookUpConstant.LOOK_UP_PURVEY)) {
            EtsItemInfoDTO dto = (EtsItemInfoDTO) dtoParameter;
            sqlStr = "SELECT EMPV.VENDOR_ID, EMPV.VENDOR_NAME,EMPV.SEGMENT1\n" +
                    "  FROM ETS_MIS_PO_VENDORS EMPV\n" +
                    " WHERE EMPV.VENDOR_NAME LIKE dbo.NVL(?, EMPV.VENDOR_NAME)";
            sqlArgs.add(dto.getVendorName());
        } else if (lookUpName.equals(LookUpConstant.LOOK_UP_ADDRESS)) {
            EtsItemInfoDTO dto = (EtsItemInfoDTO) dtoParameter;
            sqlStr = "SELECT AOA.ADDRESS_ID, EO.WORKORDER_OBJECT_NAME,EO.WORKORDER_OBJECT_CODE,EO.WORKORDER_OBJECT_LOCATION\n" +
                    "  FROM AMS_OBJECT_ADDRESS AOA, ETS_OBJECT EO\n" +
                    " WHERE AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                    "   AND EO.WORKORDER_OBJECT_CODE LIKE  dbo.NVL(?, EO.WORKORDER_OBJECT_CODE)\n" +
//					"     AND EO.OBJECT_CATEGORY ='71'" +
                    " AND EO.WORKORDER_OBJECT_NAME LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_NAME)\n" +
                    "   AND EO.WORKORDER_OBJECT_LOCATION LIKE  dbo.NVL(?, EO.WORKORDER_OBJECT_LOCATION)\n" +
                     "   AND EO.ORGANIZATION_ID = ?";
            sqlArgs.add(dto.getWorkorderObjectCode());
            sqlArgs.add(dto.getWorkorderObjectName());
            sqlArgs.add(dto.getWorkorderObjectLocation());
             sqlArgs.add(user.getOrganizationId());
        } else if (lookUpName.equals(LookUpConstant.LOOK_UP_PROJECT2)) {
            EtsPaProjectsAllDTO dtoParameter = (EtsPaProjectsAllDTO) super.dtoParameter;
            sqlStr = "SELECT "
                    + " PROJECT_ID,"
                    + " NAME PROJECT_NAME,"
                    + " SEGMENT1,"
                    + " PROJECT_TYPE "
                    + " FROM "
                    + " ETS_PA_PROJECTS_ALL "
                    + " WHERE  "
                    + "( " + SyBaseSQLUtil.isNull() + "  OR SEGMENT1 LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR NAME LIKE ?)"
                    + " ORDER BY PROJECT_ID";
            sqlArgs.add(dtoParameter.getSegment1());
            sqlArgs.add(dtoParameter.getSegment1());
            sqlArgs.add(dtoParameter.getName());
            sqlArgs.add(dtoParameter.getName());
        } else if (lookUpName.equals(LookUpConstant.LOOK_UP_ASSETS_OBJECT)) {
            EtsObjectDTO dto = (EtsObjectDTO) dtoParameter;
            sqlStr = "SELECT EO.WORKORDER_OBJECT_CODE,\n" +
                    "       EO.WORKORDER_OBJECT_NO,\n" +
                    "       EO.WORKORDER_OBJECT_NAME,\n" +
                    "       EO.WORKORDER_OBJECT_LOCATION\n" +
                    "  FROM ETS_OBJECT EO\n" +
                    " WHERE EO.WORKORDER_OBJECT_NAME LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_NAME)\n" +
                    "   AND EO.WORKORDER_OBJECT_CODE LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_CODE)" +
                    "   AND EO.WORKORDER_OBJECT_LOCATION LIKE  dbo.NVL(?, EO.WORKORDER_OBJECT_LOCATION)" +
                    "   AND  (EO.DISABLE_DATE IS NULL OR EO.DISABLE_DATE='') "+    //该地点没有被失效
                    "   AND EO.OBJECT_CATEGORY = dbo.NVL(?, EO.OBJECT_CATEGORY)" +        //added by Herry 2007-10-25
                    "   AND EO.ORGANIZATION_ID = ?";
            sqlArgs.add(dto.getWorkorderObjectName());
            sqlArgs.add(dto.getWorkorderObjectCode());
            sqlArgs.add(dto.getWorkorderObjectLocation());
            sqlArgs.add(dto.getObjectCategory());
            sqlArgs.add(user.getOrganizationId());
        } else if (lookUpName.equals(LookUpConstant.LOOK_UP_ASSETS_RCV)) {
            SfUserDTO dto = (SfUserDTO) dtoParameter;
            sqlStr = "SELECT SU.USER_ID, SU.LOGIN_NAME, SU.USERNAME,SU.EMPLOYEE_NUMBER\n" +
                    "  FROM SF_USER SU\n" +
                    " WHERE SU.LOGIN_NAME LIKE dbo.NVL(?, SU.LOGIN_NAME)\n" +
                    "   AND SU.USERNAME LIKE dbo.NVL(?, SU.USERNAME)\n" +
                    "   AND SU.ORGANIZATION_ID = ?";
            sqlArgs.add(dto.getLoginName());
            sqlArgs.add(dto.getUsername());
            sqlArgs.add(dto.getOrganizationId());
        } else if (lookUpName.equals(LookUpConstant.LOOK_UP_SYSITEM)) {
            EtsSystemItemDTO dto = (EtsSystemItemDTO) dtoParameter;
            sqlStr = "SELECT ITEM_CODE, ITEM_NAME, ITEM_SPEC" +
                    " FROM ETS_SYSTEM_ITEM" +
                    " WHERE ( " + SyBaseSQLUtil.isNull() + "  OR ITEM_NAME LIKE ?)" +
                    " AND ( " + SyBaseSQLUtil.isNull() + "  OR ITEM_SPEC LIKE ?)";
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getItemSpec());
        } else if (lookUpName.equals(LookUpConstant.LOOK_UP_DEPT)) {
            GroupDTO dto = (GroupDTO) dtoParameter;
            sqlStr = "SELECT SG.GROUP_ID, SG.GROUP_CODE, SG.GROUP_NAME\n" +
                    "  FROM SF_GROUP SG\n" +
                    " WHERE SG.ORGANIZATION_ID = ?"+
                    "  AND ( " + SyBaseSQLUtil.isNull() + "  OR SG.GROUP_CODE LIKE ?)"+
                    "  AND ( " + SyBaseSQLUtil.isNull() + "  OR SG.GROUP_NAME LIKE ?)";
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getGroupCode());
            sqlArgs.add(dto.getGroupCode());
            sqlArgs.add(dto.getGroupname());
            sqlArgs.add(dto.getGroupname());
        } else if (lookUpName.equals(LookUpConstant.BJ_SYSTEM_ITEM)) {
            EtsSystemItemDTO dto = (EtsSystemItemDTO) dtoParameter;
            sqlStr = "SELECT  ESI.ITEM_CODE,\n" +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       ESI.ITEM_UNIT,\n" +
                    "       ESI.MIS_ITEM_CODE\n" +
                    "  FROM ETS_SYSTEM_ITEM ESI, ETS_SYSITEM_DISTRIBUTE ESD\n" +
                    " WHERE ESI.ITEM_CODE = ESD.ITEM_CODE\n" +
                    "   AND ESI.ITEM_CATEGORY = 'SPARE'\n" +
                    "   AND ESI.ITEM_NAME LIKE dbo.NVL(?,ESI.ITEM_NAME)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?)\n" +
                    "   AND ESI.BARCODE_NULLABLE = dbo.NVL(?,ESI.BARCODE_NULLABLE)\n" +
                    "   AND ESD.ORGANIZATION_ID = ?";
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getBarcodeNullable());
            sqlArgs.add(user.getOrganizationId());
        } else if (lookUpName.equals(LookUpConstant.BJ_SYSTEM_ITEM_SX)) {
            EtsSystemItemDTO dto = (EtsSystemItemDTO) dtoParameter;
            sqlStr = "SELECT  ESI.ITEM_CODE,\n" +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       EMPV.VENDOR_NAME,\n" +
                    "       ESI.ITEM_UNIT\n" +
                    "  FROM ETS_SYSTEM_ITEM ESI, ETS_SYSITEM_DISTRIBUTE ESD,ETS_MIS_PO_VENDORS EMPV\n" +
                    " WHERE ESI.ITEM_CODE = ESD.ITEM_CODE\n" +
                    "   AND ESI.ITEM_CATEGORY='SPARE'\n" +
                    "   AND ESI.VENDOR_ID *= EMPV.VENDOR_ID\n" +
                    "   AND ESI.ITEM_NAME LIKE dbo.NVL(?,ESI.ITEM_NAME)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?)\n" +
                    "   AND ESI.BARCODE_NULLABLE = dbo.NVL(?,ESI.BARCODE_NULLABLE)\n" +
                    "   AND ESD.ORGANIZATION_ID = ?";
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getBarcodeNullable());
            sqlArgs.add(user.getOrganizationId());
        } else if (lookUpName.equals(LookUpConstant.BJ_ITEM_CATEGORY)) {
            AmsSpareInfoDTO dto = (AmsSpareInfoDTO) dtoParameter;
            sqlStr = "SELECT ESI.ITEM_CODE,\n" +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       ESI.ITEM_UNIT,\n" +
                    "       EMPV.VENDOR_NAME,\n" +
                    "       AMSC.BARCODE\n" +
                    "  FROM ETS_SYSTEM_ITEM        ESI,\n" +
                    "       ETS_SYSITEM_DISTRIBUTE ESD,\n" +
                    "       AMS_SPARE_CATEGORY     AMSC,\n" +
                    "       ETS_MIS_PO_VENDORS     EMPV\n" +
                    " WHERE ESI.ITEM_CODE = ESD.ITEM_CODE\n" +
                    "   AND ESI.VENDOR_ID *= EMPV.VENDOR_ID\n" +
                    "   AND ESI.ITEM_CODE *= AMSC.ITEM_CODE\n" +
                    "   AND ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR AMSC.BARCODE LIKE ?)\n" +
                    "   AND ESD.ORGANIZATION_ID = ?";
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getBarcode());
            sqlArgs.add(dto.getBarcode());
            sqlArgs.add(user.getOrganizationId());
        } else if (lookUpName.equals(LookUpConstant.BJ_ITEM_CATEGORY3)) {
            AmsSpareInfoDTO dto = (AmsSpareInfoDTO) dtoParameter;
            sqlStr = "SELECT ESI.ITEM_CODE,\n" +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       ESI.ITEM_UNIT,\n" +
                    "       ESI.MIS_ITEM_CODE BARCODE,\n" +
                    "       EMPV.VENDOR_NAME\n" +
                    "  FROM ETS_SYSTEM_ITEM        ESI,\n" +
                    "       ETS_SYSITEM_DISTRIBUTE ESD,\n" +
                    "       ETS_MIS_PO_VENDORS     EMPV\n" +
                    " WHERE ESI.ITEM_CODE = ESD.ITEM_CODE\n" +
                    "   AND ESI.VENDOR_ID *= EMPV.VENDOR_ID\n" +
                    "   AND ESI.ITEM_CATEGORY = 'SPARE'\n" +
                    "   AND ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.MIS_ITEM_CODE LIKE ?)\n" +
                    "   AND ESD.ORGANIZATION_ID = ?";
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getBarcode());
            sqlArgs.add(dto.getBarcode());
            sqlArgs.add(user.getOrganizationId());
        } else if (lookUpName.equals(LookUpConstant.BJ_ITEM_CATEGORY_SX)) {
            AmsSpareInfoDTO dto = (AmsSpareInfoDTO) dtoParameter;
            sqlStr = "SELECT ESI.ITEM_CODE,\n" +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       ESI.ITEM_UNIT,\n" +
                    "       EMPV.VENDOR_NAME,\n" +
                    "       AMSC.BARCODE\n" +
                    "  FROM ETS_SYSTEM_ITEM        ESI,\n" +
                    "       ETS_SYSITEM_DISTRIBUTE ESD,\n" +
                    "       AMS_SPARE_CATEGORY     AMSC,\n" +
                    "       ETS_MIS_PO_VENDORS     EMPV\n" +
                    " WHERE ESI.ITEM_CODE = ESD.ITEM_CODE\n" +
                    "   AND ESI.VENDOR_ID *= EMPV.VENDOR_ID\n" +
                    "   AND ESI.ITEM_CODE = AMSC.ITEM_CODE\n" +
                    "   AND ESI.ITEM_CATEGORY='SPARE'\n" +
                    "   AND ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR AMSC.BARCODE LIKE ?)\n" +
                    "   AND ESD.ORGANIZATION_ID = ?";
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getBarcode());
            sqlArgs.add(dto.getBarcode());
            sqlArgs.add(user.getOrganizationId());
        } else if (lookUpName.equals(LookUpConstant.BJ_SPARE_CATEGORY)) {      //备件设备分类查询，无OU限制
            AmsSpareCategoryDTO dto = (AmsSpareCategoryDTO) dtoParameter;
            sqlStr = "SELECT AC.BARCODE,\n" +
                    "    AC.ITEM_NAME,\n" +
                    "    AC.ITEM_SPEC,\n" +
                    "    AC.SPARE_USAGE,\n" +
                    "    EMPV.VENDOR_NAME,\n" +
                    "    1 QUANTITY,\n" +
                    "    AC.ITEM_UNIT\n" +
                    " FROM AMS_SPARE_CATEGORY AC,\n" +
                    "    ETS_MIS_PO_VENDORS EMPV\n" +
                    " WHERE AC.VENDOR_ID *= EMPV.VENDOR_ID\n" +
                    "   AND AC.ITEM_CATEGORY = 'SPARE'\n" +
                    "   AND AC.ENABLED = 'Y'\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR AC.BARCODE LIKE ?)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR AC.ITEM_NAME LIKE ?)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EMPV.VENDOR_NAME LIKE ?)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR AC.ITEM_SPEC LIKE ?)"+
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR AC.SPARE_USAGE LIKE ?)";
            sqlArgs.add(dto.getBarcode());
            sqlArgs.add(dto.getBarcode());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getVendorName());
            sqlArgs.add(dto.getVendorName());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getSpareUsage());
            sqlArgs.add(dto.getSpareUsage());
        } else if (lookUpName.equals(LookUpConstant.BJSL_ITEM_INFO)) {
            AmsItemTransLDTO dto = (AmsItemTransLDTO) dtoParameter;
            sqlStr = "SELECT EII.BARCODE,EII.ITEM_CODE,ESI.ITEM_NAME,ESI.ITEM_SPEC\n" +
                    "  FROM ETS_ITEM_INFO      EII,\n" +
                    "       ETS_SYSTEM_ITEM    ESI,\n" +
                    "       AMS_OBJECT_ADDRESS EOA,\n" +
                    "       ETS_OBJECT         EO\n" +
                    " WHERE EII.ITEM_CODE = ESI.ITEM_CODE\n" +
                    "   AND EII.ADDRESS_ID = EOA.ADDRESS_ID\n" +
                    "   AND EOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                    "   AND EO.WORKORDER_OBJECT_NO = dbo.NVL(?, EO.WORKORDER_OBJECT_NO)\n" +
                    "   AND EO.OBJECT_CATEGORY = " + DictConstant.INV_NORMAL +
                    "   AND EO.ORGANIZATION_ID = ?";
            if (!dto.getItemCodes().equals("")) {
                sqlStr += "   AND ESI.ITEM_CODE IN (" + dto.getItemCodes() + ")";
            }
            sqlArgs.add(dto.getObjectNo());
            sqlArgs.add(dto.getOrganizationId());
        } else if (lookUpName.equals(LookUpConstant.BJSL_ITEM_INFO2)) {   //方案2
            AmsItemTransLDTO dto = (AmsItemTransLDTO) dtoParameter;
            sqlStr = "SELECT ASI.BARCODE,\n" +
                    "       ESI.ITEM_CODE,\n" +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       ASI.OBJECT_NO,\n" +
                    "       ASI.QUANTITY ONHAND_QTY\n" +
                    "  FROM AMS_SPARE_INFO     ASI,\n" +
                    "       AMS_SPARE_CATEGORY AMSC,\n" +
                    "       ETS_SYSTEM_ITEM    ESI\n" +
                    " WHERE ASI.BARCODE = AMSC.BARCODE\n" +
                    "   AND ASI.ITEM_STATUS = '正常' " +
                    "   AND ASI.ORGANIZATION_ID = ?\n" +
                    "   AND AMSC.ITEM_CODE = ESI.ITEM_CODE\n" +
                    "   AND ASI.OBJECT_NO = dbo.NVL(?, ASI.OBJECT_NO)\n";
            if (!dto.getItemCodes().equals("")) {
                sqlStr += "   AND ESI.ITEM_CODE IN (" + dto.getItemCodes() + ")";
            }
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getObjectNo());
        } else if (lookUpName.equals(LookUpConstant.BJSL_ITEM_INFO3)) {   //备件申领查找设备(NM)
            AmsItemTransLDTO dto = (AmsItemTransLDTO) dtoParameter;
            sqlStr = "SELECT ASI.BARCODE,\n" +
                    "       ESI.ITEM_CODE,\n" +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       ASI.OBJECT_NO,\n" +
                    "       ASI.QUANTITY ONHAND_QTY\n" +
                    "  FROM AMS_SPARE_INFO     ASI,\n" +
                    "       ETS_SYSTEM_ITEM    ESI\n" +
                    " WHERE ASI.BARCODE = ESI.MIS_ITEM_CODE\n" +
                    "   AND ESI.ITEM_CATEGORY = 'SPARE'\n" +
                    "   AND ASI.ORGANIZATION_ID = ?\n" +
                    "   AND ASI.OBJECT_NO = dbo.NVL(?, ASI.OBJECT_NO)\n";
            if (!dto.getItemCodes().equals("")) {
                sqlStr += "   AND ESI.ITEM_CODE IN (" + dto.getItemCodes() + ")";
            }
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getObjectNo());
        } else if (lookUpName.equals(LookUpConstant.LOOK_UP_HOUSE)) {  //选房屋类型
            AmsHouseInfoDTO dto = (AmsHouseInfoDTO) dtoParameter;
            sqlStr = "     SELECT" +
                    "       ESI.ITEM_CODE,\n" +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       ESD.ORGANIZATION_ID,\n" +
                    "       AMS_PUB_PKG.GET_VENDOR_NAME(ESI.VENDOR_ID) VENDOR_NAME,\n" +
                    "       ESI.VENDOR_ID\n" +
                    "  FROM ETS_SYSITEM_DISTRIBUTE ESD,\n" +
                    "       ETS_SYSTEM_ITEM ESI\n" +
                    " WHERE ESI.ITEM_CODE = ESD.ITEM_CODE\n" +
                    "   AND ESI.ITEM_CATEGORY = ?" +
                    "   AND ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?)\n";
            sqlArgs.add(DictConstant.HOUSE);
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getItemSpec());
//			if ((!user.isProvinceUser()) && (!user.isSysAdmin())) {
                sqlStr += "AND ESD.ORGANIZATION_ID = ?";
                sqlArgs.add(user.getOrganizationId());
//			}
        } else if (lookUpName.equals(LookUpConstant.FLOW_AGENT_USER)) { //查找流程代理人
            SfUserDTO dto = (SfUserDTO) dtoParameter;
            sqlStr = "SELECT *\n" +
                    "  FROM SF_USER SU\n" +
                    " WHERE SU.LOGIN_NAME LIKE dbo.NVL(?, SU.LOGIN_NAME)\n" +
                    "   AND SU.USERNAME LIKE dbo.NVL(?, SU.USERNAME)\n" +
                    "   AND SU.ORGANIZATION_ID = ?";
            sqlArgs.add(dto.getLoginName());
            sqlArgs.add(dto.getUsername());
            sqlArgs.add(dto.getOrganizationId());
        } else if (lookUpName.equals(LookUpConstant.LOOK_UP_ITEM_SIMPLE)) {
            sqlStr = "SELECT ESI.ITEM_CODE,\n" +
                    "      ESI.ITEM_NAME,\n" +
                    "      ESI.ITEM_SPEC,\n" +
                    "      EFV.VALUE,\n" +
                    "      ESI.ITEM_CATEGORY\n" +
                    " FROM ETS_SYSTEM_ITEM ESI,\n" +
                    "      ETS_FLEX_VALUES EFV\n" +
                    " WHERE EFV.FLEX_VALUE_SET_ID = '1'\n" +
                    "  AND ESI.ITEM_CATEGORY = EFV.CODE\n" +
                    "  AND ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME)\n" +
                    "  AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?)";
            AmsItemVersionDTO dto = (AmsItemVersionDTO) dtoParameter;
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getItemSpec());
        } else if (lookUpName.equals(LookUpConstant.LOOK_UP_LAND)) {  //选土地类型
            AmsLandInfoDTO dto = (AmsLandInfoDTO) dtoParameter;
            sqlStr = "     SELECT" +
                    "       ESI.ITEM_CODE,\n" +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       ESD.ORGANIZATION_ID,\n" +
                    "       AMS_PUB_PKG.GET_VENDOR_NAME(ESI.VENDOR_ID) VENDOR_NAME,\n" +
                    "       ESI.VENDOR_ID\n" +
                    "  FROM ETS_SYSITEM_DISTRIBUTE ESD,\n" +
                    "       ETS_SYSTEM_ITEM ESI\n" +
                    " WHERE ESI.ITEM_CODE = ESD.ITEM_CODE\n" +
                    "   AND ESI.ITEM_CATEGORY = ?" +
                    "   AND ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?)\n";
            sqlArgs.add(DictConstant.CATEGORY_LAND);
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getItemSpec());
            if ((!user.isProvinceUser()) && (!user.isSysAdmin())) {
                sqlStr += "AND ESD.ORGANIZATION_ID = ?";
                sqlArgs.add(user.getOrganizationId());
            }
        } else if (lookUpName.equals(LookUpConstant.LOOK_UP_DAY)) {  //租赁日期短信设置查找截至日期
            AmsRentDeadlineDTO dto = (AmsRentDeadlineDTO) dtoParameter;
            sqlStr = "SELECT"
                    + " AHI.BARCODE,"
                    + " ESI.ITEM_NAME,"
                    + " ESI.ITEM_SPEC,"
                    + " ESI.ITEM_CATEGORY,"
                    + " AHI.END_DATE"
                    + " FROM "
                    + " ETS_ITEM_INFO     EII,"
                    + " ETS_SYSTEM_ITEM   ESI,"
                    + " AMS_HOUSE_INFO    AHI\n"
                    + " WHERE "
                    + " EII.BARCODE = AHI.BARCODE\n"
                    + " AND ESI.ITEM_CODE = EII.ITEM_CODE\n"
                    + " AND  " + SyBaseSQLUtil.isNotNull("AHI.END_DATE") + " \n"
                    + " AND AHI.BARCODE LIKE dbo.NVL(?,AHI.BARCODE) "
                    + " AND ESI.ITEM_NAME LIKE dbo.NVL (?, ESI.ITEM_NAME)"
                    + " UNION ALL\n"
                    + " SELECT"
                    + " ALI.BARCODE,"
                    + " ESI.ITEM_NAME,"
                    + " ESI.ITEM_SPEC,"
                    + " ESI.ITEM_CATEGORY,"
                    + " ALI.END_DATE"
                    + " FROM "
                    + " ETS_ITEM_INFO     EII,"
                    + " ETS_SYSTEM_ITEM   ESI,"
                    + " AMS_LAND_INFO     ALI"
                    + " WHERE "
                    + "  EII.BARCODE = ALI.BARCODE"
                    + " AND ESI.ITEM_CODE = EII.ITEM_CODE"
                    + " AND  " + SyBaseSQLUtil.isNotNull("ALI.END_DATE") + " "
                    + " AND ALI.BARCODE LIKE dbo.NVL(?,ALI.BARCODE) "
                    + " AND ESI.ITEM_NAME LIKE dbo.NVL (?, ESI.ITEM_NAME)";
            sqlArgs.add(dto.getBarcode());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getBarcode());
            sqlArgs.add(dto.getItemName());
        } else if (lookUpName.equals(LookUpConstant.LOOK_UP_CABEL)) {
            sqlStr = " SELECT ESI.ITEM_CODE, " +
                    "         ESI.ITEM_NAME, " +
                    "         ESI.ITEM_SPEC, " +
                    "         ESI.ITEM_CATEGORY " +
                    "    FROM ETS_SYSTEM_ITEM ESI " +
                    "          WHERE  ESI.ITEM_CATEGORY = 'CABEL'" +
                    "             AND ESI.ITEM_NAME LIKE dbo.NVL(?,ESI.ITEM_NAME) " +
                    "             AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?) ";
            AmsCabelInfoDTO dto = (AmsCabelInfoDTO) dtoParameter;
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getItemSpec());
        } else if (lookUpName.equals(LookUpConstant.LOOKE_UP_BEIJIAN)) {
            sqlStr = "SELECT COUNT(EII.ITEM_QTY) ITEM_AMOUNT,\n" +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       ESI.ITEM_CODE\n" +
                    "  FROM ETS_SYSTEM_ITEM    ESI,\n" +
                    "       ETS_ITEM_INFO      EII,\n" +
                    "       AMS_OBJECT_ADDRESS AOA,\n" +
                    "       ETS_OBJECT         EO\n" +
                    " WHERE ESI.ITEM_CODE = EII.ITEM_CODE\n" +
                    "   AND EII.ORGANIZATION_ID = ?\n" +
                    "   AND EII.FINANCE_PROP = 'SPARE'\n" +
                    "   AND EO.OBJECT_CATEGORY = 71\n" +
                    "   AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                    "   AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                    "   AND ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?)\n" +
                    " GROUP BY ESI.ITEM_NAME, ESI.ITEM_SPEC, ESI.ITEM_CODE";
            AmsBjsAllotDDto dto = (AmsBjsAllotDDto) dtoParameter;
            sqlArgs.add(servletConfig.getProvinceOrgId());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getItemSpec());
        } else if (lookUpName.equals(LookUpConstant.LOOKE_UP_BEIJIAN2)) {
            sqlStr = "SELECT ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       ESI.ITEM_CODE,\n" +
                    "       SUM(ASI.QUANTITY) ITEM_AMOUNT\n" +
                    "  FROM AMS_SPARE_INFO ASI, AMS_SPARE_CATEGORY AM, ETS_SYSTEM_ITEM ESI\n" +
                    " WHERE ASI.BARCODE = AM.BARCODE\n" +
                    "   AND AM.ITEM_CODE = ESI.ITEM_CODE\n" +
                    "   AND ASI.ITEM_STATUS = '正常'\n" +
                    "   AND ASI.ORGANIZATION_ID = ?\n" +
                    "   AND ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?)\n" +
                    " GROUP BY ESI.ITEM_NAME, ESI.ITEM_SPEC, ESI.ITEM_CODE";
            AmsBjsAllotDDto dto = (AmsBjsAllotDDto) dtoParameter;
            sqlArgs.add(servletConfig.getProvinceOrgId());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getItemSpec());
        } else if (lookUpName.equals(LookUpConstant.SELECT_BARCODE)) {
            sqlStr = "SELECT EII.BARCODE, ESI.ITEM_NAME, ESI.ITEM_SPEC\n" +
                    "  FROM ETS_ITEM_INFO EII, ETS_SYSTEM_ITEM ESI\n" +
                    " WHERE EII.FINANCE_PROP = 'SPARE'\n" +
                    "   AND ESI.ITEM_CODE = EII.ITEM_CODE\n" +
                    "   AND EII.ITEM_CODE = ?\n" +
                    "   AND ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?)";
            AmsBjsAllotDDto dto = (AmsBjsAllotDDto) dtoParameter;
            sqlArgs.add(dto.getItemCode());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getItemSpec());
        } else if (lookUpName.equals(LookUpConstant.LOOK_UP_FH)) {
            sqlStr = "select EII.ITEM_QTY,\n" +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       EII.BARCODE,\n" +
                    "       EII.ITEM_CODE\n" +
                    "  from ETS_SYSTEM_ITEM    ESI,\n" +
                    "       ETS_ITEM_INFO      EII,\n" +
                    "       AMS_OBJECT_ADDRESS AOA,\n" +
                    "       ETS_OBJECT         EO\n" +
                    " where ESI.ITEM_CODE = EII.ITEM_CODE\n" +
                    "   and EII.FINANCE_PROP = 'SPARE'\n" +
                    "   and EO.OBJECT_CATEGORY = 73\n" +
                    "   and EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                    "   and AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                    "   and EII.ORGANIZATION_ID = ?\n" +
                    "   and ESI.ITEM_NAME like dbo.NVL(?, ESI.ITEM_NAME)\n" +
                    "   aAND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?))";
            AmsBjsAllotDDto dto = (AmsBjsAllotDDto) dtoParameter;
            sqlArgs.add(user.getOrganizationId());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getItemSpec());
        } else if (lookUpName.equals(LookUpConstant.LOOK_UP_FH2)) {
            AmsItemTransLDTO dto = (AmsItemTransLDTO) dtoParameter;
            sqlStr = " SELECT ASI.BARCODE, ESI.ITEM_CODE, ESI.ITEM_NAME, ESI.ITEM_SPEC\n" +
                    "  FROM AMS_SPARE_INFO ASI, AMS_SPARE_CATEGORY AMSC, ETS_SYSTEM_ITEM ESI\n" +
                    " WHERE ASI.BARCODE = AMSC.BARCODE\n" +
                    "   AND AMSC.ITEM_CODE = ESI.ITEM_CODE\n" +
                    "   AND ASI.ITEM_STATUS = '送修'\n" +
                    "   AND ASI.BARCODE LIKE dbo.NVL(?, ASI.BARCODE)\n" +
                    "   AND ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?)" +
                    "   AND ASI.ORGANIZATION_ID = ?";
            sqlArgs.add(dto.getBarcode());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(user.getOrganizationId());
        } else if (lookUpName.equals(LookUpConstant.LOOK_UP_ITEM_BF)) {
            sqlStr = "select EII.ITEM_QTY,\n" +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       EII.BARCODE,\n" +
                    "       EII.ITEM_CODE\n" +
                    "  from ETS_SYSTEM_ITEM    ESI,\n" +
                    "       ETS_ITEM_INFO      EII,\n" +
                    "       AMS_OBJECT_ADDRESS AOA,\n" +
                    "       ETS_OBJECT         EO\n" +
                    " where ESI.ITEM_CODE = EII.ITEM_CODE\n" +
                    "   and AOA.ADDRESS_ID = EII.ADDRESS_ID\n" +
                    "   and AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                    "   and EO.OBJECT_CATEGORY != 76\n" +
                    "   and EII.FINANCE_PROP = 'SPARE'\n" +
                    "   and EII.ORGANIZATION_ID = ?\n" +
                    "   and ESI.ITEM_NAME like dbo.NVL(?, ESI.ITEM_NAME)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?)";
            AmsBjsAllotDDto dto = (AmsBjsAllotDDto) dtoParameter;
            sqlArgs.add(user.getOrganizationId());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getItemSpec());
        } else if (lookUpName.equals(LookUpConstant.LOOK_UP_ITEM_BF2)) {
            sqlStr = "SELECT ASI.BARCODE,\n" +
                    "       ESI.ITEM_CODE,\n" +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       ASI.OBJECT_NO,\n" +
                    "       ASI.QUANTITY ONHAND_QTY\n" +
                    "  FROM AMS_SPARE_INFO     ASI,\n" +
                    "       AMS_SPARE_CATEGORY AMSC,\n" +
                    "       ETS_SYSTEM_ITEM    ESI\n" +
                    " WHERE ASI.BARCODE = AMSC.BARCODE\n" +
                    "   AND AMSC.ITEM_CODE = ESI.ITEM_CODE\n" +
                    "   AND ASI.ITEM_STATUS = '待修' " +
                    "   AND ASI.OBJECT_NO = dbo.NVL(?, ASI.OBJECT_NO)\n" +
                    "   AND ASI.ORGANIZATION_ID = ?\n" +
                    "   and ESI.ITEM_NAME like dbo.NVL(?, ESI.ITEM_NAME)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?)";
            AmsItemTransLDTO dto = (AmsItemTransLDTO) dtoParameter;
            sqlArgs.add(dto.getObjectNo());
            sqlArgs.add(user.getOrganizationId());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getItemSpec());
        } else if (lookUpName.equals(LookUpConstant.LOOK_UP_KUCUN)) {
            sqlStr = "SELECT EOA.ADDRESS_ID,\n" +
                    "       EO.WORKORDER_OBJECT_CODE,\n" +
                    "       EO.WORKORDER_OBJECT_NAME,\n" +
                    "       EO.WORKORDER_OBJECT_NO,\n" +
                    "       EO.WORKORDER_OBJECT_LOCATION\n" +
                    "  FROM AMS_OBJECT_ADDRESS EOA, ETS_OBJECT EO, ETS_FLEX_VALUES EFV\n" +
                    " WHERE EO.WORKORDER_OBJECT_NO = EOA.OBJECT_NO\n" +
                    "   AND EFV.CODE = EO.OBJECT_CATEGORY\n" +
                    "   AND EFV.FLEX_VALUE_SET_ID = 5\n" +
                    "   AND EO.OBJECT_CATEGORY =71\n" +
                    "   AND EO.WORKORDER_OBJECT_NAME LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_NAME)\n" +
                    "   AND EO.WORKORDER_OBJECT_LOCATION LIKE\n" +
                    "       dbo.NVL(?, EO.WORKORDER_OBJECT_LOCATION)";
            AmsBjsAllotDDto dto = (AmsBjsAllotDDto) dtoParameter;
            sqlArgs.add(dto.getWorkorderObjectName());
            sqlArgs.add(dto.getWorkorderObjectLocation());
            if (dto.getType().equals("CHECK")) {
                sqlStr += "AND EO.ORGANIZATION_ID = ?";
                sqlArgs.add(user.getOrganizationId());
            }
        } else if (lookUpName.equals(LookUpConstant.LOOK_UP_BF)) {
            sqlStr = "SELECT EOA.ADDRESS_ID,\n" +
                    "       EO.WORKORDER_OBJECT_CODE,\n" +
                    "       EO.WORKORDER_OBJECT_NAME,\n" +
                    "       EO.WORKORDER_OBJECT_LOCATION," +
                    "       EO.WORKORDER_OBJECT_NO \n" +
                    "  FROM AMS_OBJECT_ADDRESS EOA, ETS_OBJECT EO, ETS_FLEX_VALUES EFV\n" +
                    " WHERE EO.WORKORDER_OBJECT_NO = EOA.OBJECT_NO\n" +
                    "   AND EFV.CODE = EO.OBJECT_CATEGORY\n" +
                    "   AND EFV.FLEX_VALUE_SET_ID = 5\n" +
                    "   AND (EO.DISABLE_DATE IS NULL OR EO.DISABLE_DATE='') \n" +   //失效日期为空
                    "   AND EO.OBJECT_CATEGORY = ?\n" +
                    "   AND EOA.ORGANIZATION_ID=?\n" +
                    "   AND EO.WORKORDER_OBJECT_NAME LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_NAME)\n" +
                    "   AND EO.WORKORDER_OBJECT_LOCATION LIKE\n" +
                    "       dbo.NVL(?, EO.WORKORDER_OBJECT_LOCATION)";
            AmsBjsAllotDDto dto = (AmsBjsAllotDDto) dtoParameter;

            sqlArgs.add(dto.getObjectCategory());
            sqlArgs.add(user.getOrganizationId());
            sqlArgs.add(dto.getWorkorderObjectName());
            sqlArgs.add(dto.getWorkorderObjectLocation());
        } else if (lookUpName.equals(LookUpConstant.LOOK_UP_INSTR)) {
            AmsInstrumentInfoDTO dto = (AmsInstrumentInfoDTO) dtoParameter;
            sqlStr = "SELECT AII.BARCODE,\n" +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_CODE,\n" +
                    "       EFV.VALUE,\n" +
                    "       AMS_PUB_PKG.GET_USER_NAME(AII.CREATED_BY) CNAME,\n" +
                    "       EMPV.VENDOR_NAME,\n" +
                    "       AII.CREATION_DATE,\n" +
                    "       AMS_PUB_PKG.GET_USER_NAME(AII.CURR_KEEP_USER) DNAME,\n" +
                    "       AII.INSTRU_USAGE,\n" +
                    "       ESI.ITEM_CATEGORY,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       AII.CURR_KEEP_USER\n" +
                    "  FROM AMS_INSTRUMENT_INFO AII,\n" +
                    "       ETS_ITEM_INFO       EII,\n" +
                    "       ETS_SYSTEM_ITEM     ESI,\n" +
                    "       ETS_MIS_PO_VENDORS  EMPV,\n" +
                    "       ETS_FLEX_VALUES     EFV,\n" +
                    "       ETS_OBJECT          EO,\n" +
                    "       AMS_OBJECT_ADDRESS  AOA\n" +
                    " WHERE AII.BARCODE = EII.BARCODE\n" +
                    "   AND ESI.ITEM_CODE = EII.ITEM_CODE\n" +
                    "   AND ESI.ITEM_CATEGORY = 'INSTRUMENT'\n" +
                    "   AND EMPV.VENDOR_ID *= ESI.VENDOR_ID\n" +
                    "   AND EFV.CODE = ESI.ITEM_CATEGORY\n" +
                    "   AND EO.WORKORDER_OBJECT_NO = AOA.OBJECT_NO\n" +
                    "   AND AOA.ADDRESS_ID = EII.ADDRESS_ID\n" +
                    "   AND EII.ORGANIZATION_ID = ISNULL(?, EII.ORGANIZATION_ID)\n" +
                    "   AND EO.OBJECT_CATEGORY = dbo.NVL(?, EO.OBJECT_CATEGORY)\n" +
                    "   AND ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?)\n" +
                    "   AND AII.BARCODE LIKE dbo.NVL(?, AII.BARCODE)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR AII.CURR_KEEP_USER = ?)";
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getObjectCategory());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getBarcode());
            sqlArgs.add(dto.getCurrKeepUser());
            sqlArgs.add(dto.getCurrKeepUser());
            if (dto.getTransType().equals("INS-BRW")) {
                sqlStr += "AND  NOT EXISTS(SELECT 1 FROM AMS_INSTRUMENT_RESERVED AIR WHERE AIR.BARCODE =AII.BARCODE)";
            }
        } else if (lookUpName.equals(LookUpConstant.LOOK_UP_BARCODENO)) {  //其它租赁资产选择 BARCODE 相关信息
            AmsHouseInfoDTO dto = (AmsHouseInfoDTO) dtoParameter;
            sqlStr = "SELECT\n"
                    + " EII.SYSTEMID SYSTEM_ID,"
                    + " EII.BARCODE,\n"
                    + " ESI.ITEM_CODE,\n"
                    + " ESI.ITEM_NAME,\n"
                    + " ESI.ITEM_SPEC,\n"
                    + " EO.WORKORDER_OBJECT_CODE,\n"
                    + " EO.WORKORDER_OBJECT_LOCATION\n"
                    + " FROM "
                    + " ETS_OBJECT    EO,\n"
                    + " AMS_OBJECT_ADDRESS AOA,\n"
                    + " ETS_ITEM_INFO      EII, \n"          //--ATTRIBUTE1
                    + " ETS_SYSTEM_ITEM    ESI\n"
                    + " WHERE "
                    + " EO.WORKORDER_OBJECT_NO = AOA.OBJECT_NO\n"
                    + " AND AOA.ADDRESS_ID = EII.ADDRESS_ID\n"
                    + " AND ESI.ITEM_CODE = EII.ITEM_CODE\n"
//                    + " AND EII.BARCODE LIKE dbo.NVL(?, EII.BARCODE)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.BARCODE LIKE ? )"
                    + " AND EO.WORKORDER_OBJECT_CODE LIKE dbo.NVL(?,EO.WORKORDER_OBJECT_CODE) "
                    + " AND EO.WORKORDER_OBJECT_LOCATION LIKE dbo.NVL(?,EO.WORKORDER_OBJECT_LOCATION)\n"
                    + " AND ESI.ITEM_NAME LIKE dbo.NVL(?,ESI.ITEM_NAME)";
//                    + " AND ESI.ITEM_SPEC LIKE dbo.NVL(?,ESI.ITEM_SPEC)";
            sqlArgs.add(dto.getBarcode());
            sqlArgs.add(dto.getBarcode());
            sqlArgs.add(dto.getWorkorderObjectCode());
            sqlArgs.add(dto.getWorkorderObjectLocation());
            sqlArgs.add(dto.getItemName());
//            sqlArgs.add(dto.getItemSpec());
        } else if (lookUpName.equals(LookUpConstant.LOOKE_UP_PORTAGE)) {  //查找搬运地点信息
            EtsWorkorderTmpDTO dto = (EtsWorkorderTmpDTO) dtoParameter;
            sqlStr = "SELECT"
                    + " EO.WORKORDER_OBJECT_NO ATTRIBUTE1,\n"
                    + " EO.WORKORDER_OBJECT_CODE TRANS_OBJECT_CODE,\n"
                    + " EO.WORKORDER_OBJECT_NAME TRANS_OBJECT_NAME,\n"
                    + " EO.WORKORDER_OBJECT_LOCATION,\n"
                    + " EFV.VALUE\n"
                    + " FROM "
                    + " ETS_OBJECT EO,"
                    + " ETS_FLEX_VALUES EFV\n"
                    + " WHERE"
                    + " EFV.FLEX_VALUE_SET_ID = 2\n"
                    + " AND EO.OBJECT_CATEGORY = EFV.CODE\n"
                    + " AND EO.ORGANIZATION_ID = ?\n"
                    + " AND EO.WORKORDER_OBJECT_CODE LIKE dbo.NVL(?,EO.WORKORDER_OBJECT_CODE)\n"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.WORKORDER_OBJECT_NAME LIKE ?)";
            sqlArgs.add(user.getOrganizationId());
            sqlArgs.add(dto.getTransObjectCode());
            sqlArgs.add(dto.getTransObjectName());
            sqlArgs.add(dto.getTransObjectName());
        } else if (lookUpName.equals(LookUpConstant.LOOK_UP_VENDORS)) {  //查找供应商
            EquipStatDTO dto = (EquipStatDTO) dtoParameter;
            sqlStr = " SELECT EMV.VENDOR_ID," +
                    " EMV.VENDOR_NAME " +
                    " FROM  ETS_MIS_PO_VENDORS EMV \n" +
                    " WHERE ( " + SyBaseSQLUtil.isNull() + "  OR EMV.VENDOR_ID LIKE ?) \n" +
                    " AND ( " + SyBaseSQLUtil.isNull() + "  OR EMV.VENDOR_NAME LIKE ?)";
            sqlArgs.add(dto.getVendorId());
            sqlArgs.add(dto.getVendorId());
            sqlArgs.add(dto.getVendorName());
            sqlArgs.add(dto.getVendorName());
        } else if (lookUpName.equals(LookUpConstant.LOOK_UP_BATCH)) {     //查询任务批信息
            EtsWorkorderDTO dto = (EtsWorkorderDTO) dtoParameter;
            sqlStr = "SELECT" +
                    " EWB.WORKORDER_BATCH," +
                    " EWB.WORKORDER_BATCH_NAME" +
                    " FROM ETS_WORKORDER_BATCH EWB\n" +
                    " WHERE EWB.WORKORDER_BATCH LIKE dbo.NVL(?, EWB.WORKORDER_BATCH)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EWB.WORKORDER_BATCH_NAME LIKE ?)\n" +
                    "   AND EXISTS\n" +
                    " (SELECT 1\n" +
                    "          FROM ETS_WORKORDER EW\n" +
                    "         WHERE EW.ORGANIZATION_ID = ?\n" +
                    "           AND EW.WORKORDER_BATCH = EWB.WORKORDER_BATCH)";
            sqlArgs.add(dto.getWorkorderBatch());
            sqlArgs.add(dto.getWorkorderBatchName());
            sqlArgs.add(dto.getWorkorderBatchName());
            sqlArgs.add(user.getOrganizationId());
        } else if (lookUpName.equals(LookUpConstant.FTMCK_OBJECT_NO)) {     ////非条码设备出库选择仓库
            EtsObjectDTO dto = (EtsObjectDTO) dtoParameter;
            sqlStr = "SELECT DISTINCT EO.WORKORDER_OBJECT_NO,\n" +
                    "                EO.WORKORDER_OBJECT_NAME,\n" +
                    "                EO.WORKORDER_OBJECT_LOCATION\n" +
                    "  FROM AMS_INV_STORAGE AIS, ETS_OBJECT EO\n" +
                    " WHERE AIS.INV_CODE = EO.WORKORDER_OBJECT_NO\n" +
                    "   AND EO.WORKORDER_OBJECT_NAME LIKE dbo.NVL(?,EO.WORKORDER_OBJECT_NAME)" +
                    "   AND EO.WORKORDER_OBJECT_LOCATION LIKE dbo.NVL(?,EO.WORKORDER_OBJECT_LOCATION)" +
                    "   AND EO.ORGANIZATION_ID = ?";
            sqlArgs.add(dto.getWorkorderObjectName());
            sqlArgs.add(dto.getWorkorderObjectLocation());
            sqlArgs.add(user.getOrganizationId());
        } else if (lookUpName.equals(LookUpConstant.FTMCK_ITEM)) {     ////非条码设备出库选择设备
            AmsInvStorageDTO dto = (AmsInvStorageDTO) dtoParameter;
            sqlStr ="SELECT ANTL.ITEM_CODE,\n" +
                    "       ANTL.ITEM_NAME,\n" +
                    "       ANTL.ITEM_SPEC,\n" +
                    "       ANTL.ITEM_UNIT,\n" +
                    "       ANTL.BATCH_NO,\n" +
                    "       ANTL.QUANTITY,\n" +
                    "       AMS_DEPRECIATION_PKG.GET_NOBARCODE_NOW_QTY(ANTL.BATCH_NO,\n" +
                    "                                                  ANTL.ITEM_NAME,\n" +
                    "                                                  ANTL.ITEM_SPEC,\n" +
                    "                                                  ANTH.TO_OBJECT_NO) NOW_QTY," +
                    "       ANTL.LINE_ID\n" +
                    "FROM   AMS_NOBARCODE_TRANS_L ANTL,\n" +
                    "       AMS_NOBARCODE_TRANS_H ANTH,\n" +
                    "       ETS_OBJECT            EO\n" +
                    "WHERE  ANTH.TO_OBJECT_NO *= EO.WORKORDER_OBJECT_NO\n" +
                    "       AND ANTH.TRANS_ID = ANTL.TRANS_ID\n" +
                    "       AND ANTL.BATCH_NO = dbo.NVL(?, ANTL.BATCH_NO)\n" +
                    "       AND ANTL.ITEM_NAME = dbo.NVL(?, ANTL.ITEM_NAME)\n" +
                    "       AND ( " + SyBaseSQLUtil.isNull() + "  OR ANTL.ITEM_SPEC LIKE ?)\n" +
                    "       AND EO.WORKORDER_OBJECT_NO = ?";
            sqlArgs.add(dto.getBatchNo());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getInvCode());
        } else if (lookUpName.equals(LookUpConstant.LOOK_UP_PROJECT3)) {
            EtsPaProjectsAllDTO dtoParameter = (EtsPaProjectsAllDTO) super.dtoParameter;
            sqlStr = "SELECT "
                    + " PROJECT_ID,"
                    + " NAME PROJECT_NAME,"
                    + " SEGMENT1,"
                    + " PROJECT_TYPE "
                    + " FROM "
                    + " ETS_PA_PROJECTS_ALL "
                    + " WHERE  "
                    + "( " + SyBaseSQLUtil.isNull() + "  OR SEGMENT1 LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR NAME LIKE ?)";
            sqlArgs.add(dtoParameter.getSegment1());
            sqlArgs.add(dtoParameter.getSegment1());
            sqlArgs.add(dtoParameter.getName());
            sqlArgs.add(dtoParameter.getName());
        } else if (lookUpName.equals(LookUpConstant.LOOK_UP_ASSETS_LOCATION)) {
            EtsFaAssetsDTO dto = (EtsFaAssetsDTO) dtoParameter;
            sqlStr = "SELECT"
                     + " EL.ASSETS_LOCATION_CODE,"
                     + " EL.ASSETS_LOCATION"
                     + " FROM"
                     + " ETS_FA_ASSETS_LOCATION EL"
                     + " WHERE"
                     + " EL.ORG_ID = ?"
                     + " AND EL.BOOK_TYPE_CODE LIKE '%FA%'"
                     + " AND EL.ASSETS_LOCATION_CODE LIKE dbo.NVL(?, EL.ASSETS_LOCATION_CODE)"
                     + " AND EL.ASSETS_LOCATION LIKE dbo.NVL(?, EL.ASSETS_LOCATION)";
            sqlArgs.add(user.getOrganizationId());
            sqlArgs.add(dto.getAssetsLocationCode());
            sqlArgs.add(dto.getAssetsLocation());
        } else if (lookUpName.equals(LookUpConstant.LOOK_UP_ASSETS_ADDRESS)) {
            EtsItemInfoDTO dto = (EtsItemInfoDTO) dtoParameter;
            sqlStr = "SELECT " +
                    " AOA.ADDRESS_ID, " +
                    " EO.WORKORDER_OBJECT_CODE, " +
                    " EO.WORKORDER_OBJECT_NAME,\n" +
                    " EO.WORKORDER_OBJECT_NAME HOUSE_ADDRESS\n" +
                    " FROM " +
                    " AMS_OBJECT_ADDRESS AOA, " +
                    " ETS_OBJECT EO\n" +
                    " WHERE " +
                    " AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                    " AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.WORKORDER_OBJECT_CODE LIKE ?)\n" +
                    " AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.WORKORDER_OBJECT_NAME LIKE ?)";
            sqlArgs.add(dto.getWorkorderObjectCode());
            sqlArgs.add(dto.getWorkorderObjectCode());
            sqlArgs.add(dto.getWorkorderObjectName());
            sqlArgs.add(dto.getWorkorderObjectName());
            if ((!user.isProvinceUser()) && (!user.isSysAdmin())) {
                sqlStr += "AND EO.ORGANIZATION_ID = ?";
                sqlArgs.add(user.getOrganizationId());
            }
        } else if (lookUpName.equals(LookUpConstant.LOOK_UP_EXACT_EQUIP)) {//选择正式设备
            EtsSystemItemDTO dto = (EtsSystemItemDTO) dtoParameter;
            sqlStr = "SELECT"
                     + " ESI.ITEM_CODE,"
                     + " ESI.ITEM_CATEGORY,"
                     + " AMS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_CATEGORY,'ITEM_TYPE') ITEM_TYPE,"
                     + " ESI.ITEM_NAME,"
                     + " ESI.ITEM_SPEC,"
                     + " ESI.ITEM_UNIT"
                     + " FROM ETS_SYSTEM_ITEM ESI"
                     + " WHERE ESI.IS_TMP_CODE = 'N'"
                     + " AND ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME)"
                     + " AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?)"
                     + " AND ESI.ENABLED='Y'";
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getItemSpec());
        } else if (lookUpName.equals(LookUpConstant.LOOK_UP_INSTRUMENT)) {  //仪器仪表
            AmsInstrumentInfoDTO dto = (AmsInstrumentInfoDTO) dtoParameter;
            sqlStr = "  SELECT EII.ITEM_CODE,\n" +
                    "         EII.BARCODE,\n" +
                    "         EII.ORGANIZATION_ID,\n" +
                    "         AMS_PUB_PKG.GET_ORGNIZATION_NAME(EII.ORGANIZATION_ID) ORGNIZATION_NAME ,\n" +
                    "         EII.ATTRIBUTE3 INSTRU_USAGE,\n" +     //仪器仪表用途
                    "         ESI.ITEM_NAME,\n" +
                    "         ESI.ITEM_SPEC,\n" +
                    "         AMS_PUB_PKG.GET_USER_NAME(EII.RESPONSIBILITY_USER) RESPONSIBILITY_NAME,\n" +
                    "         AMS_PUB_PKG.GET_VENDOR_NAME(ESI.VENDOR_ID) VENDOR_NAME," +
                    "         AMS_PUB_PKG.GET_WORKORDER_OBJECT_NAME(EII.ADDRESS_ID) ADDRESSLOC\n" +
                    "    FROM ETS_SYSTEM_ITEM ESI, ETS_ITEM_INFO EII\n" +
                    "   WHERE ESI.ITEM_CODE = EII.ITEM_CODE\n" +
                    "        -- EII.ORGANIZATION_ID =''\n" +
                    "     AND ESI.ITEM_CATEGORY = 'INSTRUMENT'" +
                    "     AND  EII.ITEM_STATUS = 'NORMAL'" +
                //	"     AND EII.MAINTAIN_USER  " + SyBaseSQLUtil.isNullNoParam() + " " +
                    " AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.BARCODE LIKE ?)" +
                    " AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_NAME LIKE ?)\n" +
                    " AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?)\n"+
                    " AND ( " + SyBaseSQLUtil.isNull() + "  OR AMS_PUB_PKG.GET_VENDOR_NAME(ESI.VENDOR_ID) LIKE ?)";
            sqlArgs.add(dto.getBarcode());
            sqlArgs.add(dto.getBarcode());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getVendorName());
            sqlArgs.add(dto.getVendorName());
            if ((!user.isProvinceUser()) && (!user.isSysAdmin())) {
                sqlStr += "AND EII.ORGANIZATION_ID = ?";
                sqlArgs.add(user.getOrganizationId());
            }
        } else if (lookUpName.equals(LookUpConstant.LOOK_UP_USER1)) { //查找人
            SfUserDTO dto = (SfUserDTO) dtoParameter;
            sqlStr = "SELECT SU.USER_ID,SU.LOGIN_NAME,SU.USERNAME\n" +
                    "  FROM SF_USER SU\n" +
                    " WHERE SU.LOGIN_NAME LIKE dbo.NVL(?, SU.LOGIN_NAME)\n" +
                    "   AND SU.USERNAME LIKE dbo.NVL(?, SU.USERNAME)\n";
            sqlArgs.add(dto.getLoginName().toUpperCase());
            sqlArgs.add(dto.getUsername());
            if ((!user.isProvinceUser()) && (!user.isSysAdmin())) {
                sqlStr += "AND SU.ORGANIZATION_ID = ?";
                sqlArgs.add(user.getOrganizationId());
            }
        } else if (lookUpName.equals(LookUpConstant.LOOK_UP_INSTR_ITEM)) {
            EtsSystemItemDTO dto = (EtsSystemItemDTO) dtoParameter;
            sqlStr = "SELECT DISTINCT" +
                    " ESI.ITEM_CODE,\n" +
                    " ESI.ITEM_NAME,\n" +
                    " ESI.ITEM_SPEC,\n" +
                    " ESI.VENDOR_ID,\n" +
                    " AMS_PUB_PKG.GET_VENDOR_NAME(ESI.VENDOR_ID) VENDOR_NAME,\n"+
//                    "       ESI.ITEM_UNIT\n" +
                    " AMS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_UNIT,'UNIT_OF_MEASURE') ITEM_UNIT\n" +
                    " FROM " +
                    " ETS_SYSTEM_ITEM ESI, " +
                    " ETS_SYSITEM_DISTRIBUTE ESD\n" +
                    " WHERE " +
                    " ESI.ITEM_CODE = ESD.ITEM_CODE\n" +
                    " AND ESI.ITEM_NAME LIKE dbo.NVL(?,ESI.ITEM_NAME)\n" +
                    " AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?)\n" +
                    " AND ( " + SyBaseSQLUtil.isNull() + "  OR AMS_PUB_PKG.GET_VENDOR_NAME(ESI.VENDOR_ID) LIKE ?)\n" +
                    " AND ESI.ITEM_CATEGORY = dbo.NVL(?, ESI.ITEM_CATEGORY)\n";
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getVendorName());
            sqlArgs.add(dto.getVendorName());
            sqlArgs.add(dto.getItemCategory());
            if ((!user.isProvinceUser()) && (!user.isSysAdmin())) {
                sqlStr += "AND ESD.ORGANIZATION_ID = ?";
                sqlArgs.add(user.getOrganizationId());
            }
        } else if (lookUpName.equals(LookUpConstant.LOOK_UP_RETURN)) {  //仪器仪表归还人
            AmsInstrumentInfoDTO dto = (AmsInstrumentInfoDTO) dtoParameter;
            sqlStr = "  SELECT EII.ITEM_CODE,\n" +
                    "         EII.BARCODE,\n" +
                    "         EII.ORGANIZATION_ID,\n" +
                    "         EII.ATTRIBUTE3 INSTRU_USAGE,\n" +     //仪器仪表用途
                    "         ESI.ITEM_NAME,\n" +
                    "         ESI.ITEM_SPEC,\n" +
                    "         AMS_PUB_PKG.GET_USER_NAME(EII.RESPONSIBILITY_USER) RESPONSIBILITY_NAME,\n" +
                    "         AMS_PUB_PKG.GET_BORROW_DATE(EII.BARCODE) BORROW_DATE,\n" +
                    "         AMS_PUB_PKG.GET_VENDOR_NAME(ESI.VENDOR_ID) VENDOR_NAME\n" +
                    "    FROM ETS_ITEM_INFO EII, ETS_SYSTEM_ITEM ESI\n" +
                    "   WHERE ESI.ITEM_CODE = EII.ITEM_CODE\n" +
                    "        -- EII.ORGANIZATION_ID =''\n" +
                    "     AND ESI.ITEM_CATEGORY = 'INSTRUMENT'" +
                    " AND EII.ITEM_STATUS = 'BORROW'" +
//                    " AND EII.MAINTAIN_USER = dbo.NVL(?,EII.MAINTAIN_USER)  "+
                    " AND EII.MAINTAIN_USER = ?" +
                    " AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.BARCODE LIKE ?)" +
                    " AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_NAME LIKE ?)\n" +
                    " AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?)\n"+
                    " AND ( " + SyBaseSQLUtil.isNull() + "  OR AMS_PUB_PKG.GET_VENDOR_NAME(ESI.VENDOR_ID) LIKE ?)";
            sqlArgs.add(dto.getReturnName());
            sqlArgs.add(dto.getBarcode());
            sqlArgs.add(dto.getBarcode());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getVendorName());
            sqlArgs.add(dto.getVendorName());
            if ((!user.isProvinceUser()) && (!user.isSysAdmin())) {
                sqlStr += "AND EII.ORGANIZATION_ID = ?";
                sqlArgs.add(user.getOrganizationId());
            }
        } else if (lookUpName.equals(LookUpConstant.LOOK_UP_CITYINSTR)) {  //查找地市仪器仪表管理员
            AmsInstrumentInfoDTO dto = (AmsInstrumentInfoDTO) dtoParameter;
            sqlStr = " SELECT DISTINCT SUR.USER_ID,\n" +
                    "                SU.ORGANIZATION_ID,\n" +
                    "                AOA.ADDRESS_ID,\n" +
                    "                AMS_PUB_PKG.GET_USER_NAME(SUR.USER_ID) USER_NAME,\n" +
                    "                AMS_PUB_PKG.GET_COMPANY_NAME(SUR.USER_ID) COMPANY_NAME\n" +
                    "  FROM SF_USER_RIGHT      SUR,\n" +
                    "       SF_USER            SU,\n" +
                    "       ETS_OBJECT         EO,\n" +
                    "       AMS_OBJECT_ADDRESS AOA,\n" +
                    "       SF_ROLE            SR\n" +
                    " WHERE SUR.ROLE_ID = SR.ROLE_ID\n" +
                    "   AND SUR.USER_ID = SU.USER_ID\n" +
                    "   AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                    "   AND AOA.ORGANIZATION_ID = SU.ORGANIZATION_ID\n" +
                    "   AND EO.OBJECT_CATEGORY = '77'\n" +
                    "   AND SR.ROLE_NAME = '仪器仪表管理员'" +
                    "   AND( " + SyBaseSQLUtil.isNull() + "  OR AMS_PUB_PKG.GET_USER_NAME(SUR.USER_ID) LIKE ?)\n" +
                    "   AND( " + SyBaseSQLUtil.isNull() + "  OR AMS_PUB_PKG.GET_COMPANY_NAME(SUR.USER_ID) LIKE ?)";
            sqlArgs.add(dto.getUserName());
            sqlArgs.add(dto.getUserName());
            sqlArgs.add(dto.getCompanyName());
            sqlArgs.add(dto.getCompanyName());
        } else if (lookUpName.equals(LookUpConstant.BJSX_ITEM_INFO2)) {  //备件送修查找设备(方案2) herry
            AmsItemTransLDTO dto = (AmsItemTransLDTO) dtoParameter;
            sqlStr = " SELECT ASI.BARCODE, ESI.ITEM_CODE, ESI.ITEM_NAME, ESI.ITEM_SPEC, ASI.QUANTITY ONHAND_QTY\n" +
                    "  FROM AMS_SPARE_INFO ASI, AMS_SPARE_CATEGORY AMSC, ETS_SYSTEM_ITEM ESI\n" +
                    " WHERE ASI.BARCODE = AMSC.BARCODE\n" +
                    "   AND AMSC.ITEM_CODE = ESI.ITEM_CODE\n" +
                    "   AND ASI.ITEM_STATUS = '待修'\n" +
                    "   AND ASI.BARCODE LIKE dbo.NVL(?, ASI.BARCODE)\n" +
                    "   AND ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?)" +
                    "   AND ASI.ORGANIZATION_ID = ?";
            sqlArgs.add(dto.getBarcode());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(user.getOrganizationId());
        } else if (lookUpName.equals(LookUpConstant.BJSX_ITEM_INFO3)) {  //备件送修查找设备(NM) herry 2008-03-17
            AmsSpareInfoDTO dto = (AmsSpareInfoDTO) dtoParameter;
            sqlStr = " SELECT ASI.BARCODE,\n" +
                    "       ASI.OBJECT_NO,\n" +
                    "       ESI.ITEM_CODE,\n" +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       ASI.DISREPAIR_QUANTITY \n" +
                    "  FROM AMS_SPARE_INFO ASI, ETS_SYSTEM_ITEM ESI,ETS_OBJECT EO\n" +
                    " WHERE ASI.BARCODE = ESI.MIS_ITEM_CODE\n" +
                    "   AND ASI.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                    "   AND ESI.ITEM_CATEGORY = 'SPARE'\n" +
                    "   AND ASI.DISREPAIR_QUANTITY > 0\n" +
                    "   AND ASI.BARCODE LIKE dbo.NVL(?, ASI.BARCODE)\n" +
                    "   AND ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?)" +
                    "   AND ASI.ORGANIZATION_ID = ?" +
                    "   AND ASI.OBJECT_NO = ?";
            sqlArgs.add(dto.getBarcode());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(user.getOrganizationId());
            sqlArgs.add(dto.getObjectNo());
        } else if (lookUpName.equals(LookUpConstant.LOOK_UP_RESPUSER)) {  //查找责任人
            AmsInstrumentInfoDTO dto = (AmsInstrumentInfoDTO) dtoParameter;
            sqlStr = "  SELECT DISTINCT SUR.USER_ID,\n" +
                    "        SU.ORGANIZATION_ID,\n" +
                    "        SG.GROUP_ID DEPT_ID,\n" +
                    "        SG.GROUP_NAME RESPONSIBILITY_DEPT,\n" +
                    "        AMS_PUB_PKG.GET_USER_NAME(SUR.USER_ID) USERNAME,\n" +
                    "        AMS_PUB_PKG.GET_COMPANY_NAME(SUR.USER_ID) COMPANY_NAME\n" +
                    "   FROM SF_USER_RIGHT SUR, SF_USER SU, SF_GROUP SG\n" +
                    "  WHERE SUR.USER_ID = SU.USER_ID\n" +
                    "    AND SUR.GROUP_ID = SG.GROUP_ID\n" +
//                    "    AND SR.ROLE_NAME = '仪器仪表管理员'" +
                    "   AND( " + SyBaseSQLUtil.isNull() + "  OR AMS_PUB_PKG.GET_USER_NAME(SUR.USER_ID) LIKE ?)\n" +
                    "   AND( " + SyBaseSQLUtil.isNull() + "  OR SG.GROUP_NAME LIKE ?)";
            sqlArgs.add(dto.getUserName());
            sqlArgs.add(dto.getUserName());
            sqlArgs.add(dto.getResponsibilityDept());
            sqlArgs.add(dto.getResponsibilityDept());
            if ((!user.isProvinceUser()) && (!user.isSysAdmin())) {
                sqlStr += "AND SU.ORGANIZATION_ID = ?";
                sqlArgs.add(user.getOrganizationId());
            }
        } else if (lookUpName.equals(LookUpConstant.LOOK_UP_VENRETURN)) {  //送修归还
            AmsInstrumentInfoDTO dto = (AmsInstrumentInfoDTO) dtoParameter;
            sqlStr = " SELECT EII.BARCODE," +
                    " ESI.ITEM_CODE," +
                    " ESI.ITEM_NAME," +
                    " ESI.ITEM_SPEC, " +
                    " EII.ATTRIBUTE3 INSTRU_USAGE,\n" +     //仪器仪表用途
                    " AMS_PUB_PKG.GET_USER_NAME(EII.RESPONSIBILITY_USER) RESPONSIBILITY_NAME,\n" +
                    " AMS_PUB_PKG.GET_REPAIRE_DATE(EII.BARCODE,?) REPAIRE_DATE\n" +
                    " FROM ETS_ITEM_INFO EII, ETS_SYSTEM_ITEM ESI\n" +
                    " WHERE ESI.ITEM_CODE = EII.ITEM_CODE\n" +
                    " AND ESI.ITEM_CATEGORY = 'INSTRUMENT'\n" +
                    " AND EII.ITEM_STATUS = 'SEND_REPAIR'" +
                    " AND AMS_PUB_PKG.GET_REPAIRE_VENDOR(EII.BARCODE) = ?\n"+
//   by xupz 2008-06-20   " AND AMS_PUB_PKG.GET_REPAIRE_DATE(EII.BARCODE,?) IS NOT NULL" +
                    " AND( " + SyBaseSQLUtil.isNull() + "  OR EII.BARCODE LIKE ?)\n" +
                    " AND( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_NAME LIKE ?)" +
                    " AND( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?)";
            sqlArgs.add(dto.getVendorName());
            sqlArgs.add(dto.getVendorName());
            sqlArgs.add(dto.getBarcode());
            sqlArgs.add(dto.getBarcode());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getItemSpec());
        } else if (lookUpName.equals(LookUpConstant.SPARE_RETURN)) {  //备件归还(NM) ADDED BY HERRY 2008-3-13
            SpareReturnDTO dto = (SpareReturnDTO) dtoParameter;
            sqlStr = " SELECT AIAH.TRANS_NO BATCH_NO,\n" +
            "       AIAH.RESPECT_RETURN_DATE RESPECT_RETURN_DATE,\n" +
                    "       AIAD.BARCODE,\n" +
                    "       ESI.ITEM_CODE,\n" +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       AIAD.QUANTITY,\n" +
                    "       AIAD.RETURN_QTY,\n" +
                    "       AIAD.DETAIL_ID STORAGE_ID\n" +
                    "  FROM AMS_ITEM_ALLOCATE_H AIAH,\n" +
                    "       AMS_ITEM_ALLOCATE_D AIAD,\n" +
                    "       ETS_SYSTEM_ITEM     ESI\n" +
                    " WHERE AIAH.TRANS_ID = AIAD.TRANS_ID\n" +
                    "   AND AIAD.ITEM_CODE = ESI.ITEM_CODE\n" +
                    "   AND AIAD.ACCEPTED = 'Y'" +
                    "   AND AIAD.RETURN_QTY < AIAD.ACCEPT_QTY" +
                    "   AND AIAH.FROM_OBJECT_NO = ?" +
                    "   AND AIAH.TRANS_NO LIKE dbo.NVL(?, AIAH.TRANS_NO)\n" +
                    "   AND AIAD.BARCODE LIKE dbo.NVL(?, AIAD.BARCODE)\n" +
                    "   AND( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_NAME LIKE ?)" +
                    "   AND( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?)";
            sqlArgs.add(dto.getObjectNo());
            sqlArgs.add(dto.getBatchNo());
            sqlArgs.add(dto.getBarcode());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getItemSpec());
        } else if (lookUpName.equals(LookUpConstant.SPARE_LOAN_OBJECT)) {  //备件借出仓库和OU(NM) ADDED BY HERRY 2008-3-13
            EtsObjectDTO dto = (EtsObjectDTO) dtoParameter;
            sqlStr = "SELECT EO.WORKORDER_OBJECT_NO,\n" +
                    "       EO.WORKORDER_OBJECT_NAME,\n" +
                    "       EO.WORKORDER_OBJECT_LOCATION,\n" +
                    "       EO.ORGANIZATION_ID,\n" +
                    "       EOCM.COMPANY\n" +
                    "  FROM ETS_OBJECT EO, ETS_OU_CITY_MAP EOCM\n" +
                    " WHERE EO.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
                    "   AND EXISTS (SELECT NULL\n" +
                    "          FROM AMS_ITEM_ALLOCATE_H AIAH, AMS_ITEM_TRANS_H AITH\n" +
                    "         WHERE AIAH.SOURCE_ID = AITH.TRANS_ID\n" +
                    "           AND AIAH.FROM_OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                    "           AND AITH.FROM_ORGANIZATION_ID = ?\n" +
                    "           )\n" +
                    "   AND EO.WORKORDER_OBJECT_NAME LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_NAME)\n" +
                    "   AND EOCM.COMPANY LIKE dbo.NVL(?, EOCM.COMPANY)\n";
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getWorkorderObjectName());
            sqlArgs.add(dto.getCountyName());  //这里表示COMPANY
        } else if (lookUpName.equals(LookUpConstant.LOOK_UP_ASSETS_SYSITEM)) {  //查找管理资产设备名称、规格  ADDED BY ai 2008-3-25
            EtsSystemItemDTO dto = (EtsSystemItemDTO) dtoParameter;
            sqlStr = "SELECT ESI.ITEM_CODE,\n" +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC\n" +
                    "FROM   ETS_SYSTEM_ITEM        ESI,\n" +
                    "       ETS_SYSITEM_DISTRIBUTE ESD\n" +
                    "WHERE  ESI.ITEM_CATEGORY IN ('OTHERS', 'HOUSE', 'LAND')\n" +
                    "       AND ESI.ITEM_CODE = ESD.ITEM_CODE\n" +
                    "       AND ESD.ORGANIZATION_ID = ?\n" +
                    "       AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_NAME LIKE ?)\n" +
                    "       AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?)" ;
            sqlArgs.add(dto.getMasterOrganizationId());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getItemSpec());
        } else if (lookUpName.equals(LookUpConstant.LOOK_UP_MIS_USER)) {  //查找MIS用户
            AmsMisEmployeeDTO dto = (AmsMisEmployeeDTO) dtoParameter;
            sqlStr = "SELECT AME.EMPLOYEE_ID,\n" +
                    "        AME.EMPLOYEE_NUMBER,\n" +
                    "        AME.USER_NAME\n" +
                    "   FROM AMS_MIS_EMPLOYEE  AME\n" +
                    "  WHERE AME.DEPT_CODE = ?" +
                    "    AND AME.EMPLOYEE_NUMBER LIKE dbo.NVL(?, AME.EMPLOYEE_NUMBER)\n" +
                    "    AND AME.USER_NAME LIKE dbo.NVL(?, AME.USER_NAME)\n";
            sqlArgs.add(dto.getDeptCode());
            sqlArgs.add(dto.getEmployeeNumber());
            sqlArgs.add(dto.getUserName());
        } else if (lookUpName.equals(LookUpConstant.LOOK_UP_MIS_DEPT)) {  //查找MIS部门
            AmsMisDeptDTO dto = (AmsMisDeptDTO) dtoParameter;
            sqlStr = "SELECT AMD.COMPANY_CODE,\n" +
                    "        AMD.DEPT_CODE,\n" +
                    "        AMD.DEPT_NAME\n" +
                    "   FROM AMS_MIS_DEPT  AMD\n" +
                    "  WHERE AMD.COMPANY_CODE = ?\n" +
                    "    AND AMD.DEPT_CODE LIKE dbo.NVL(?, AMD.DEPT_CODE)\n" +
                    "    AND AMD.DEPT_NAME LIKE dbo.NVL(?, AMD.DEPT_NAME)\n";
            sqlArgs.add(user.getCompanyCode());
            sqlArgs.add(dto.getDeptCode());
            sqlArgs.add(dto.getDeptName());
        } else if (lookUpName.equals(LookUpConstant.LOOK_UP_BJBF)) {//山西备件出库
            AmsSpareCategoryDTO dto = (AmsSpareCategoryDTO) dtoParameter;
            sqlStr = "SELECT " +
                    "       ASI.SPARE_ID,\n " +
                    "       ASI.BARCODE,\n" +
                    "       AMSC.ITEM_NAME,\n" +
                    "       AMSC.ITEM_SPEC,\n" +
                    "       AMSC.VENDOR_ID,\n" +
                    "       AMSC.SPARE_USAGE,\n" +  //备件用途
                    "       AMS_PUB_PKG.GET_VENDOR_NAME(AMSC.VENDOR_ID) VENDOR_NAME,\n" +
//                    "       ASI.QUANTITY ONHAND_QTY,\n" +
                    "       ASI.QUANTITY - ASI.RESERVE_QUANTITY ONHAND_QTY,\n" +
                    "       '1' QUANTITY,\n" +
                    "       ASI.OBJECT_NO" +
                    "  FROM AMS_SPARE_INFO ASI, AMS_SPARE_CATEGORY AMSC\n" +
                    " WHERE ASI.BARCODE = AMSC.BARCODE" +
                    "   AND ASI.QUANTITY - ASI.RESERVE_QUANTITY >0" +
                    "   AND ASI.ORGANIZATION_ID = ?\n" +
                    "   AND ASI.OBJECT_NO = dbo.NVL(?, ASI.OBJECT_NO)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR AMSC.BARCODE LIKE ?)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR AMSC.ITEM_NAME LIKE ?)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR AMSC.ITEM_SPEC LIKE ?)" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR AMSC.SPARE_USAGE LIKE ?)" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR AMS_PUB_PKG.GET_VENDOR_NAME(AMSC.VENDOR_ID) LIKE ?)";
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getObjectNo());
            sqlArgs.add(dto.getBarcode());
            sqlArgs.add(dto.getBarcode());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getSpareUsage());
            sqlArgs.add(dto.getSpareUsage());
            sqlArgs.add(dto.getVendorName());
            sqlArgs.add(dto.getVendorName());
        } else if (lookUpName.equals(LookUpConstant.LOOK_UP_FXSQ)) {
            AmsSpareCategoryDTO dto = (AmsSpareCategoryDTO) dtoParameter;
            sqlStr = "SELECT AC.BARCODE,\n" +
                    "       AC.ITEM_NAME,\n" +
                    "       AC.ITEM_SPEC,\n" +
                    "       AC.SPARE_USAGE,\n" +
                    "       AMS_PUB_PKG.GET_VENDOR_NAME(AC.VENDOR_ID) VENDOR_NAME,\n" +
                    "       ASI.QUANTITY ONHAND_QTY,\n" +
                    "       '1' QUANTITY,\n" +
                    "       AC.ITEM_UNIT\n" +
                    "  FROM AMS_SPARE_CATEGORY AC, ETS_OBJECT EO, AMS_SPARE_INFO ASI\n" +
                    " WHERE ASI.BARCODE = AC.BARCODE\n" +
                    "   AND AC.ITEM_CATEGORY = 'SPARE'\n" +
                    "   AND EO.OBJECT_CATEGORY = 72\n" +
                    "   AND ASI.ORGANIZATION_ID = ?\n" +
                    "   AND EO.WORKORDER_OBJECT_NO = ASI.OBJECT_NO\n" +
                    "   AND AC.ENABLED = 'Y'\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR AC.BARCODE LIKE ?)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR AC.ITEM_NAME LIKE ?)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR AMS_PUB_PKG.GET_VENDOR_NAME(AC.VENDOR_ID) LIKE ?)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR AC.ITEM_SPEC LIKE ?)"+
                    "  AND ( " + SyBaseSQLUtil.isNull() + "  OR AC.SPARE_USAGE LIKE ?)";
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getBarcode());
            sqlArgs.add(dto.getBarcode());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getVendorName());
            sqlArgs.add(dto.getVendorName());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getSpareUsage());
            sqlArgs.add(dto.getSpareUsage());
        } else if (lookUpName.equals(LookUpConstant.LOOK_UP_DB)) {
            AmsSpareCategoryDTO dto = (AmsSpareCategoryDTO) dtoParameter;
            sqlStr = "SELECT AC.BARCODE,\n" +
                    "       AC.ITEM_NAME,\n" +
                    "       AC.ITEM_SPEC,\n" +
                    "       AC.SPARE_USAGE,\n" +
                    "       AMS_PUB_PKG.GET_VENDOR_NAME(AC.VENDOR_ID) VENDOR_NAME,\n" +
                    "        (ASI.QUANTITY - ASI.RESERVE_QUANTITY) ONHAND_QTY,\n" +
                    "       AC.ITEM_UNIT\n" +
                    "  FROM AMS_SPARE_CATEGORY AC, ETS_OBJECT EO, AMS_SPARE_INFO ASI\n" +
                    " WHERE ASI.BARCODE = AC.BARCODE\n" +
                    "   AND AC.ITEM_CATEGORY = 'SPARE'\n" +
                    "   AND EO.OBJECT_CATEGORY = 71\n" +
                    "   AND ASI.ORGANIZATION_ID = ?\n" +
                    "   AND EO.WORKORDER_OBJECT_NO = ASI.OBJECT_NO\n" +
                    "   AND AC.ENABLED = 'Y'\n" +
                    "   AND (ASI.QUANTITY - ASI.RESERVE_QUANTITY) >0" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR AC.BARCODE LIKE ?)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR AC.ITEM_NAME LIKE ?)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR AMS_PUB_PKG.GET_VENDOR_NAME(AC.VENDOR_ID) LIKE ?)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR AC.ITEM_SPEC LIKE ?)"+
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR AC.SPARE_USAGE LIKE ?)";
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getBarcode());
            sqlArgs.add(dto.getBarcode());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getVendorName());
            sqlArgs.add(dto.getVendorName());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getSpareUsage());
            sqlArgs.add(dto.getSpareUsage());
        } else if (lookUpName.equals(LookUpConstant.LOOK_UP_FXRK)) {
            AmsSpareCategoryDTO dto = (AmsSpareCategoryDTO) dtoParameter;
            sqlStr = "SELECT " +
                    "       ASI.SPARE_ID,\n " +
                    "       ASI.BARCODE,\n" +
                    "       AMSC.ITEM_NAME,\n" +
                    "       AMSC.ITEM_SPEC,\n" +
                    "       AMSC.VENDOR_ID,\n" +
                    "       AMSC.SPARE_USAGE,\n" +  //备件用途
                    "       AMS_PUB_PKG.GET_VENDOR_NAME(AMSC.VENDOR_ID) VENDOR_NAME,\n" +
//                    "       ASI.QUANTITY ONHAND_QTY,\n" +
                    "      AMS_ITEM_TRANS_SX.GET_SPARE_USERNUM(AMSC.BARCODE,\n" +
                    "                                              73,\n" +
                    "                                              ASI.ORGANIZATION_ID) ONHAND_QTY," +  //送待修库数量
                    "       '1' QUANTITY,\n" +
                    "       ASI.OBJECT_NO" +
                    "  FROM AMS_SPARE_INFO ASI, AMS_SPARE_CATEGORY AMSC\n" +
                    " WHERE ASI.BARCODE = AMSC.BARCODE" +
                    "   AND ASI.ORGANIZATION_ID = ?\n" +
                    "   AND ASI.OBJECT_NO = dbo.NVL(?, ASI.OBJECT_NO)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR AMSC.BARCODE LIKE ?)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR AMSC.ITEM_NAME LIKE ?)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR AMSC.ITEM_SPEC LIKE ?)" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR AMSC.SPARE_USAGE LIKE ?) "+
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR AMS_PUB_PKG.GET_VENDOR_NAME(AMSC.VENDOR_ID) LIKE ?)"+
                    "   AND AMS_ITEM_TRANS_SX.GET_SPARE_USERNUM(AMSC.BARCODE,\n" +
                    "                                              73,\n" +
                    "                                              ASI.ORGANIZATION_ID) >0";  //送修库数量;
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getObjectNo());
            sqlArgs.add(dto.getBarcode());
            sqlArgs.add(dto.getBarcode());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getSpareUsage());
            sqlArgs.add(dto.getSpareUsage());
            sqlArgs.add(dto.getVendorName());
            sqlArgs.add(dto.getVendorName());
        } else if (lookUpName.equals(LookUpConstant.LOOK_UP_DEPT1)) {
            AssetsTagNumberQueryDTO dto = (AssetsTagNumberQueryDTO) dtoParameter;
            sqlStr = "SELECT AMD.DEPT_CODE, AMD.DEPT_NAME, SU.USER_ID\n" +
                    "  FROM SF_USER SU, AMS_MIS_EMPLOYEE AME, AMS_MIS_DEPT AMD\n" +
                    " WHERE SU.EMPLOYEE_NUMBER = AME.EMPLOYEE_NUMBER\n" +
                    "   AND AMD.DEPT_CODE = AME.DEPT_CODE\n" +
                    "   AND SU.USER_ID = ?\n" +
                    "   AND AMD.DEPT_CODE LIKE dbo.NVL(?, AMD.DEPT_CODE)\n" +
                    "   AND AMD.DEPT_NAME LIKE dbo.NVL(?, AMD.DEPT_NAME)";
            sqlArgs.add(dto.getUserId());
            sqlArgs.add(dto.getDeptCode());
            sqlArgs.add(dto.getDeptName());
        } else if (lookUpName.equals(LookUpConstant.LOOK_UP_TASK)) {
            AssetsTagNumberQueryDTO dto = (AssetsTagNumberQueryDTO) dtoParameter;
            sqlStr = "SELECT AACB.BATCH_NO WORKORDER_BATCH,\n" +
                    "       AACB.TASK_DESC WORKORDER_BATCH_NAME,\n" +
                    "       AACB.BATCH_ID,\n" +
                    "       AACB.TASK_DESC\n" +
                    "FROM   AMS_ASSETS_CHECK_BATCH AACB\n" +
                    "WHERE  AACB.ORGANIZATION_ID =ISNULL(?, AACB.ORGANIZATION_ID)\n" +
                    "       AND AACB.BATCH_NO LIKE dbo.NVL(?, AACB.BATCH_NO)\n" +
                    "       AND ( " + SyBaseSQLUtil.isNull() + "  OR AACB.TASK_DESC LIKE dbo.NVL(?, AACB.TASK_DESC))";
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getWorkorderBatch());
            sqlArgs.add(dto.getWorkorderBatchName());
            sqlArgs.add(dto.getWorkorderBatchName());
        } else if (lookUpName.equals(LookUpConstant.LOOK_UP_BJSX)) { //备件送修
            AmsSpareCategoryDTO dto = (AmsSpareCategoryDTO) dtoParameter;
            sqlStr = "SELECT " +
                    "       ASI.SPARE_ID,\n " +
                    "       ASI.BARCODE,\n" +
                    "       AMSC.ITEM_NAME,\n" +
                    "       AMSC.ITEM_SPEC,\n" +
                    "       AMSC.VENDOR_ID,\n" +
                    "       AMSC.SPARE_USAGE,\n" +  //备件用途
                    "       AMS_PUB_PKG.GET_VENDOR_NAME(AMSC.VENDOR_ID) VENDOR_NAME,\n" +
//                    "       ASI.QUANTITY ONHAND_QTY,\n" +
                    "      AMS_ITEM_TRANS_SX.GET_SPARE_USERNUM(AMSC.BARCODE,\n" +
                    "                                              72,\n" +
                    "                                              ASI.ORGANIZATION_ID) ONHAND_QTY," +  //待修库数量
                    "       '1' QUANTITY,\n" +
                    "       ASI.OBJECT_NO" +
                    "  FROM AMS_SPARE_INFO ASI, AMS_SPARE_CATEGORY AMSC\n" +
                    " WHERE ASI.BARCODE = AMSC.BARCODE" +
                    "   AND ASI.ORGANIZATION_ID = ?\n" +
                    "   AND ASI.OBJECT_NO = dbo.NVL(?, ASI.OBJECT_NO)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR AMSC.BARCODE LIKE ?)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR AMSC.ITEM_NAME LIKE ?)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR AMSC.ITEM_SPEC LIKE ?)" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR AMS_PUB_PKG.GET_VENDOR_NAME(AMSC.VENDOR_ID) LIKE ?)"+
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR AMSC.SPARE_USAGE LIKE ?)"+
                    "   AND AMS_ITEM_TRANS_SX.GET_SPARE_USERNUM(AMSC.BARCODE,\n" +
                    "                                              72,\n" +
                    "                                              ASI.ORGANIZATION_ID) >0" ;  //待修库数量
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getObjectNo());
            sqlArgs.add(dto.getBarcode());
            sqlArgs.add(dto.getBarcode());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getVendorName());
            sqlArgs.add(dto.getVendorName());
            sqlArgs.add(dto.getSpareUsage());
            sqlArgs.add(dto.getSpareUsage());
        } else if (lookUpName.equals(LookUpConstant.LOOK_UP_BJWXC)) {
            AmsVendorInfoDTO dto = (AmsVendorInfoDTO) dtoParameter;
            sqlStr = " SELECT " +
                    "  AVI.VENDOR_CODE,\n" +
                    "  AVI.VENDOR_NAME,\n" +
                    "  AVI.ADDRESS,\n" +
                    "  AVI.CONTACT,\n" +
                    "  AVI.PHONE,\n" +
                    "  AVI.FAX\n" +
                    "  FROM AMS_VENDOR_INFO AVI" +
                    "  WHERE ( " + SyBaseSQLUtil.isNull() + "  OR AVI.VENDOR_NAME LIKE ?)" +
                    "  AND ( " + SyBaseSQLUtil.isNull() + "  OR AVI.ADDRESS LIKE ?)" +
                    "  AND ( " + SyBaseSQLUtil.isNull() + "  OR AVI.CONTACT LIKE ?)";
            sqlArgs.add(dto.getVendorName());
            sqlArgs.add(dto.getVendorName());
            sqlArgs.add(dto.getAddress());
            sqlArgs.add(dto.getAddress());
            sqlArgs.add(dto.getContact());
            sqlArgs.add(dto.getContact());
        }else if (lookUpName.equals(LookUpConstant.LOOK_UP_RESPONSIBILITY)) {  //查找责任人和责任部门
            AmsInstrumentInfoDTO dto = (AmsInstrumentInfoDTO) dtoParameter;
            sqlStr = "SELECT  distinct" +
                    " SUR.USER_ID,\n" +
                    " SU.ORGANIZATION_ID,\n" +
                    " SG.GROUP_ID DEPT_ID,\n" +
                    " SG.GROUP_NAME RESPONSIBILITY_DEPT,\n" +
                    " AMS_PUB_PKG.GET_USER_NAME(SUR.USER_ID) USERNAME,\n" +
                    " AMS_PUB_PKG.GET_COMPANY_NAME(SUR.USER_ID) COMPANY_NAME\n" +
                    " FROM " +
                    " SF_USER_RIGHT SUR, SF_USER SU, SF_GROUP SG\n" +
                    " WHERE SUR.USER_ID = SU.USER_ID\n" +
                    " AND SUR.GROUP_ID = SG.GROUP_ID\n" +
                    " AND ( " + SyBaseSQLUtil.isNull() + "  OR AMS_PUB_PKG.GET_USER_NAME(SUR.USER_ID) LIKE ?)\n" +
                    " AND ( " + SyBaseSQLUtil.isNull() + "  OR SG.GROUP_NAME LIKE ?)";
            sqlArgs.add(dto.getUserName());
            sqlArgs.add(dto.getUserName());
            sqlArgs.add(dto.getResponsibilityDept());
            sqlArgs.add(dto.getResponsibilityDept());
            if ((!user.isProvinceUser()) && (!user.isSysAdmin())) {
                sqlStr += "AND SU.ORGANIZATION_ID = ?";
                sqlArgs.add(user.getOrganizationId());
            }
        }else if (lookUpName.equals(LookUpConstant.BJ_SPARE_CATEGORY1)) {      //备件设备分类查询，无OU限制
            AmsSpareCategoryDTO dto = (AmsSpareCategoryDTO) dtoParameter;
            sqlStr = "SELECT AC.BARCODE,\n" +
                    "    AC.ITEM_NAME,\n" +
                    "    AC.ITEM_SPEC,\n" +
                    "    AC.SPARE_USAGE,\n" +
                    "    EMPV.VENDOR_NAME,\n" +
                    "    AC.ITEM_UNIT\n" +
                    " FROM AMS_SPARE_CATEGORY AC,\n" +
                    "    ETS_MIS_PO_VENDORS EMPV\n" +
                    " WHERE AC.VENDOR_ID *= EMPV.VENDOR_ID\n" +
                    "   AND AC.ITEM_CATEGORY = 'SPARE'\n" +
                    "   AND AC.ENABLED = 'Y'\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR AC.BARCODE LIKE ?)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR AC.ITEM_NAME LIKE ?)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EMPV.VENDOR_NAME LIKE ?)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR AC.ITEM_SPEC LIKE ?)";
//                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR AC.SPARE_USAGE LIKE ?)";
            sqlArgs.add(dto.getBarcode());
            sqlArgs.add(dto.getBarcode());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getVendorName());
            sqlArgs.add(dto.getVendorName());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getItemSpec());
//            sqlArgs.add(dto.getSpareUsage());
//            sqlArgs.add(dto.getSpareUsage());
         }else if (lookUpName.equals(LookUpConstant.LOOK_UP_MIS_INFO)) {  //查找mis责任人和责任部门
            AmsMisEmployeeDTO dto = (AmsMisEmployeeDTO) dtoParameter;
            sqlStr ="  SELECT " +
                    "  AME.EMPLOYEE_ID, " +
                    "  AME.USER_NAME, " +
                    "  AMD.DEPT_CODE, " +
                    "  AMD.DEPT_NAME\n" +
                    "  FROM " +
                    "  AMS_MIS_DEPT AMD, " +
                    "  AMS_MIS_EMPLOYEE AME\n" +
                    "  WHERE " +
                    "  AMD.DEPT_CODE = AME.DEPT_CODE\n" +
                    "  AND ( " + SyBaseSQLUtil.isNull() + "  OR AME.USER_NAME LIKE ?)\n" +
                    "  AND ( " + SyBaseSQLUtil.isNull() + "  OR AMD.DEPT_NAME LIKE ?)";
            sqlArgs.add(dto.getUserName());
            sqlArgs.add(dto.getUserName());
            sqlArgs.add(dto.getDeptName());
            sqlArgs.add(dto.getDeptName());
          } else if (lookUpName.equals(LookUpConstant.LOOK_MAINTAIN_DEPT)) {
            GroupDTO dto = (GroupDTO) dtoParameter;
            sqlStr = "SELECT" +
                     " SG.GROUP_ID, " +
                     " SG.GROUP_CODE, " +
                     " SG.GROUP_NAME\n" +
                     " FROM " +
                     " SF_GROUP SG\n" +
                     " WHERE " +
                     " SG.ORGANIZATION_ID = ?" +
                     " AND SG.IS_DESIGNER <> 1 " +
                     " AND SG.GROUP_CODE LIKE dbo.NVL(?, SG.GROUP_CODE)" +
                     " AND SG.GROUP_NAME LIKE dbo.NVL(?, SG.GROUP_NAME)";
            sqlArgs.add(user.getOrganizationId());
            sqlArgs.add(dto.getGroupCode());
            sqlArgs.add(dto.getGroupname());
        }else if(lookUpName.equals(LookUpConstant.COST_CENTER)){ //查找成本中心
            CostCenterDTO dto=(CostCenterDTO)dtoParameter;
            sqlStr ="SELECT"
                    + " ACCV.COST_CENTER_CODE,"
                    + " ACCV.COST_CENTER_NAME,"
                    + " ACCV.COMPANY_CODE,"
                    + " ACCV.ORGANIZATION_ID"
                    + " FROM"
                    + " AMS_COST_CENTER_V ACCV"
                    + " WHERE"
                    + " ACCV.ORGANIZATION_ID = ?"
                    + " AND ACCV.ENABLED_FLAG = 'Y'"
                    + " AND ACCV.COST_CENTER_CODE LIKE dbo.NVL(?, ACCV.COST_CENTER_CODE)"
                    + " AND ACCV.COST_CENTER_NAME LIKE dbo.NVL(?, ACCV.COST_CENTER_NAME)"
                    + " ORDER BY"
                    + " ACCV.COST_CENTER_CODE";
            sqlArgs.add(user.getOrganizationId());
            sqlArgs.add(dto.getCostCenterCode());
            sqlArgs.add(dto.getCostCenterName());
        }else if(lookUpName.equals(LookUpCtConstant.LOOK_UP_SYS_ITEM_NAME)) {
            EtsItemInfoDTO dto = (EtsItemInfoDTO)dtoParameter;
            sqlStr = "SELECT"
                     + " ESI.ITEM_CODE,"
                     + " ESI.ITEM_NAME,"
                     + " ESI.ITEM_SPEC"
                     + " FROM"
                     + " ETS_SYSTEM_ITEM ESI,"
                     + " ETS_ITEM_INFO EII"
                     + " WHERE"
                     + " ESI.ITEM_CODE = EII.ITEM_CODE"
                     + " AND EII.ORGANIZATION_ID = ?"
                     + " AND ESI.ITEM_CODE LIKE dbo.NVL(?, ESI.ITEM_CODE)"
                     + " AND ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME)"
                     + " AND ESI.ITEM_SPEC LIKE dbo.NVL(?, ESI.ITEM_SPEC)";
            sqlArgs.add(user.getOrganizationId());
            sqlArgs.add(dto.getItemCode());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemSpec());
        } else if(lookUpName.equals(LookUpCtConstant.LOOK_UP_SYS_ITEM_SPEC)) {
            EtsItemInfoDTO dto = (EtsItemInfoDTO)dtoParameter;
            sqlStr = "SELECT"
                     + " ESI.ITEM_CODE,"
                     + " ESI.ITEM_NAME,"
                     + " ESI.ITEM_SPEC"
                     + " FROM"
                     + " ETS_SYSTEM_ITEM ESI,"
                     + " ETS_ITEM_INFO EII"
                     + " WHERE"
                     + " ESI.ITEM_CODE = EII.ITEM_CODE"
                     + " AND EII.ORGANIZATION_ID = ?"
                     + " AND ESI.ITEM_CODE LIKE dbo.NVL(?, ESI.ITEM_CODE)"
                     + " AND ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME)"
                     + " AND ESI.ITEM_SPEC LIKE dbo.NVL(?, ESI.ITEM_SPEC)";
            sqlArgs.add(user.getOrganizationId());
            sqlArgs.add(dto.getItemCode());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemSpec());
        } else if(lookUpName.equals(LookUpCtConstant.LOOK_UP_ETS_FA_CT_ASSETS_DESCRIPTION)) {
            EtsFaAssetsDTO dto = (EtsFaAssetsDTO)dtoParameter;
            sqlStr = "SELECT"
                     + " EFCA.TAG_NUMBER,"
                     + " EFCA.ASSETS_DESCRIPTION,"
                     + " EFCA.MODEL_NUMBER"
                     + " FROM"
                     + " ETS_FA_CT_ASSETS EFCA"
                     + " WHERE"
                     + " EFCA.ORGANIZATION_ID = ?"
                     + " AND EFCA.TAG_NUMBER LIKE dbo.NVL(?, EFCA.TAG_NUMBER)"
                     + " AND EFCA.ASSETS_DESCRIPTION LIKE dbo.NVL(?, EFCA.ASSETS_DESCRIPTION)"
                     + " AND EFCA.MODEL_NUMBER LIKE dbo.NVL(?, EFCA.MODEL_NUMBER)";
            sqlArgs.add(user.getOrganizationId());
            sqlArgs.add(dto.getTagNumber());
            sqlArgs.add(dto.getAssetsDescription());
            sqlArgs.add(dto.getModelNumber());
        } else if(lookUpName.equals(LookUpCtConstant.LOOK_UP_ETS_FA_CT_ASSETS_MODEL_NUMBER)) {
            EtsFaAssetsDTO dto = (EtsFaAssetsDTO)dtoParameter;
            sqlStr = "SELECT"
                     + " EFCA.TAG_NUMBER,"
                     + " EFCA.ASSETS_DESCRIPTION,"
                     + " EFCA.MODEL_NUMBER"
                     + " FROM"
                     + " ETS_FA_CT_ASSETS EFCA"
                     + " WHERE"
                     + " EFCA.ORGANIZATION_ID = ?"
                     + " AND EFCA.TAG_NUMBER LIKE dbo.NVL(?, EFCA.TAG_NUMBER)"
                     + " AND EFCA.ASSETS_DESCRIPTION LIKE dbo.NVL(?, EFCA.ASSETS_DESCRIPTION)"
                     + " AND EFCA.MODEL_NUMBER LIKE dbo.NVL(?, EFCA.MODEL_NUMBER)";
            sqlArgs.add(user.getOrganizationId());
            sqlArgs.add(dto.getTagNumber());
            sqlArgs.add(dto.getAssetsDescription());
            sqlArgs.add(dto.getModelNumber());
        }
        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);
    }

}
