package com.sino.ams.newasset.bean;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.LookUpConstant;
import com.sino.ams.newasset.assetsharing.dto.AssetSharingLineDTO;
import com.sino.ams.newasset.bean.sql.AssetsLookupSQL;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsLookUpConstant;
import com.sino.ams.newasset.dto.AmsAccountVDTO;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.newasset.dto.AmsAssetsPriviDTO;
import com.sino.ams.newasset.dto.AmsAssetsTransLineDTO;
import com.sino.ams.newasset.dto.AmsCexDTO;
import com.sino.ams.newasset.dto.AmsFaCategoryVDTO;
import com.sino.ams.newasset.dto.AmsLneDTO;
import com.sino.ams.newasset.dto.AmsMisCostDTO;
import com.sino.ams.newasset.dto.AmsMisDeptDTO;
import com.sino.ams.newasset.dto.AmsMisEmployeeDTO;
import com.sino.ams.newasset.dto.AmsNleDTO;
import com.sino.ams.newasset.dto.AmsOpeDTO;
import com.sino.ams.newasset.dto.AmsSnDTO;
import com.sino.ams.newasset.lease.constant.LeaseAppConstant;
import com.sino.ams.newasset.lease.dto.LeaseLineDTO;
import com.sino.ams.newasset.rolequery.dto.SfRoleQueryDTO;
import com.sino.ams.plan.dto.AmsWorkPlanDTO;
import com.sino.ams.system.basepoint.dto.EtsObjectDTO;
import com.sino.ams.system.cost.dto.CostCenterDTO;
import com.sino.ams.system.fixing.dto.EtsItemInfoDTO;
import com.sino.ams.system.item.dto.EtsSystemItemDTO;
import com.sino.ams.system.procedure.dto.MisDeptDTO;
import com.sino.ams.system.project.dto.EtsPaProjectsAllDTO;
import com.sino.ams.system.user.dto.EtsOuCityMapDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dto.ZeroTurnLineDTO;
import com.sino.ams.yearchecktaskmanager.dto.EtsObjectTaskDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.lookup.LookUpModel;
import com.sino.base.lookup.LookUpProp;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.security.dto.ServletConfigDTO;

/**
 * <p>
 * Title: SinoEAM
 * </p>
 * <p>
 * Description: 山西移动实物资产管理系统
 * </p>
 * <p>
 * Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007
 * </p>
 * <p>
 * Company: 北京思诺博信息技术有限公司
 * </p>
 * 
 * @author 唐明胜
 * @version 1.0
 */
public class AssetsLookUpModel extends LookUpModel {
	private SfUserDTO user = null;
	private String proCode = "";

	public AssetsLookUpModel(BaseUserDTO userAccount, DTO dtoParameter,
			LookUpProp lookProp) {
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
			String provinceCode = dto.getProvinceCode();
			sqlStr = "SELECT"
                    + " EOCM.ORGANIZATION_ID TO_ORGANIZATION_ID,"
					+ " AMD.COMPANY_CODE,"
                    + " EOCM.COMPANY TO_COMPANY_NAME,"
					+ " AMD.DEPT_CODE TO_DEPT,"
					+ " AMD.DEPT_NAME TO_DEPT_NAME,"
					+ " SG.GROUP_NAME TO_GROUP"
                    + " FROM"
					+ " AMS_MIS_DEPT    AMD,"
                    + " ETS_OU_CITY_MAP EOCM,"
					+ " SINO_GROUP_MATCH SGM,"
                    + " SF_GROUP SG"
                    + " WHERE"
					+ " AMD.COMPANY_CODE = EOCM.COMPANY_CODE"
					+ " AND SGM.DEPT_ID = AMD.DEPT_CODE"
					+ " AND SG.GROUP_ID = SGM.GROUP_ID"
					+ " AND AMD.ENABLED = 'Y'"
					+ " AND EOCM.COMPANY LIKE dbo.NVL(?, EOCM.COMPANY)"
					+ " AND AMD.DEPT_NAME LIKE dbo.NVL(?, AMD.DEPT_NAME)";
			sqlArgs.add(dto.getCompanyName());
			sqlArgs.add(dto.getDeptName());
			if (transferType.equals(AssetsDictConstant.TRANS_BTW_COMP)) { // 选择其他地市部门
				sqlStr = sqlStr + " AND EOCM.ORGANIZATION_ID = ?";
				sqlArgs.add(dto.getOrganizationId());
			} else if (transferType.equals(AssetsDictConstant.TRANS_BTW_DEPT)
					|| transferType.equals(AssetsDictConstant.SPEC_ASS_RED)) { // 选择本公司部门
				sqlStr = sqlStr + " AND EOCM.ORGANIZATION_ID = ?"
						+ " AND AMD.DEPT_CODE <> ?";
				sqlArgs.add(dto.getOrganizationId());
				sqlArgs.add(dto.getDeptCode());
			}
			sqlStr += " GROUP BY EOCM.ORGANIZATION_ID,\n"
					+ "          AMD.COMPANY_CODE,\n"
					+ "          EOCM.COMPANY,\n"
					+ "          AMD.DEPT_CODE,\n"
					+ "          SG.GROUP_NAME,\n"
                    + "          AMD.DEPT_NAME";
		} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_DEPT_NM)) { // 必须指定专业资产管理员的部门
			// 选择接收部门
			MisDeptDTO dto = (MisDeptDTO) dtoParameter;
			String transferType = dto.getTransferType();
			sqlStr = "SELECT" + " EOCM.ORGANIZATION_ID TO_ORGANIZATION_ID,"
					+ " AMD.COMPANY_CODE," + " EOCM.COMPANY TO_COMPANY_NAME,"
					+ " AMD.DEPT_CODE TO_DEPT," + " AMD.DEPT_NAME TO_DEPT_NAME"
					+ " FROM" + " AMS_MIS_DEPT    AMD,"
					+ " ETS_OU_CITY_MAP EOCM, AMS_MIS_EMPLOYEE AME,\n"
					+ "       SF_ROLE          SR,\n"
					+ "       SF_USER          SU,\n"
					+ "       SF_USER_RIGHT    SUR" + " WHERE"
					+ " AMD.COMPANY_CODE = EOCM.COMPANY_CODE"
					+ "   AND SU.EMPLOYEE_NUMBER = AME.EMPLOYEE_NUMBER\n"
					+ "   AND SU.USER_ID = SUR.USER_ID\n"
					+ "   AND SUR.ROLE_ID = SR.ROLE_ID\n"
					+ "   AND SR.ROLE_NAME = '专业资产管理员'"
					+ " AND EOCM.COMPANY LIKE dbo.NVL(?, EOCM.COMPANY)"
					+ " AND AMD.DEPT_NAME LIKE dbo.NVL(?, AMD.DEPT_NAME)";
			sqlArgs.add(dto.getCompanyName());
			sqlArgs.add(dto.getDeptName());
			if (transferType.equals(AssetsDictConstant.TRANS_BTW_COMP)) { // 选择其他地市部门
				sqlStr = sqlStr + " AND EOCM.ORGANIZATION_ID = ?";
				sqlArgs.add(dto.getOrganizationId());
			} else if (transferType.equals(AssetsDictConstant.TRANS_BTW_DEPT)) { // 选择本公司部门
				sqlStr = sqlStr + " AND EOCM.ORGANIZATION_ID = ?"
						+ " AND AMD.DEPT_CODE <> ?";
				sqlArgs.add(dto.getOrganizationId());
				sqlArgs.add(dto.getDeptCode());
			}
			sqlStr += "AND AME.DEPT_CODE = AMD.DEPT_CODE\n"
					+ " GROUP BY EOCM.ORGANIZATION_ID,\n"
					+ "          AMD.COMPANY_CODE,\n"
					+ "          EOCM.COMPANY,\n"
					+ "          AMD.DEPT_CODE,\n" + "          AMD.DEPT_NAME";
		} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_ADDRESS)) {
			EtsObjectDTO dto = (EtsObjectDTO) dtoParameter;
			sqlStr = "SELECT \n "
					+ " EOCM.COMPANY,\n"
					+ " EOCM.ORGANIZATION_ID,\n"
					+ " EC.COUNTY_NAME,\n"
					+ " EO.WORKORDER_OBJECT_NO TO_OBJECT_NO,\n"
					+ " EO.WORKORDER_OBJECT_CODE,\n"
					+ " EO.WORKORDER_OBJECT_NAME TO_OBJECT_NAME,\n"
					+ " EO.WORKORDER_OBJECT_LOCATION,\n"
					+ " AOA.ADDRESS_ID\n"
					+ " FROM \n "
					+ " ETS_OBJECT EO (INDEX ETS_OBJECT_N5),\n"
					+ " AMS_OBJECT_ADDRESS AOA,\n"
					+ " ETS_OU_CITY_MAP EOCM (INDEX ETS_OU_CITY_MAP_PK),\n"
					+ " ETS_COUNTY EC\n"
					+ " WHERE \n "
					+ " EO.WORKORDER_OBJECT_NO = AOA.OBJECT_NO \n "
					+ " AND EO.ORGANIZATION_ID = EOCM.ORGANIZATION_ID \n "
					+ " AND EO.ORGANIZATION_ID = ? \n "
					+ " AND (EO.OBJECT_CATEGORY <= '73' OR EO.OBJECT_CATEGORY >= '75') \n "
					+ " AND (EO.DISABLE_DATE = NULL OR EO.DISABLE_DATE = '' OR EO.DISABLE_DATE > GETDATE())\n";

			sqlArgs.add(dto.getOrganizationId());

			if("1".equals(dto.getDeptToSite())){
				sqlStr += "AND EXISTS(\n" +
                        "SELECT NULL\n" +
                        "FROM   AMS_COST_DEPT_MATCH ACDM\n" +
                        "WHERE  EC.COUNTY_CODE_MIS = ACDM.COST_CENTER_CODE\n" +
                        "       AND ACDM.COMPANY_CODE = EC.COMPANY_CODE\n" +
                        "       AND EC.COUNTY_CODE = EO.COUNTY_CODE\n" +
                        "       AND ACDM.COMPANY_CODE = EOCM.COMPANY_CODE\n" +
                        "       AND ACDM.DEPT_CODE = ?)\n" +
                        " AND EO.COUNTY_CODE = EC.COUNTY_CODE \n" +
                        " AND EC.ORGANIZATION_ID = EO.ORGANIZATION_ID \n ";
				sqlArgs.add(dto.getDeptCode());
			} else {
                sqlStr += " AND EO.COUNTY_CODE *= EC.COUNTY_CODE \n"
                        + " AND EO.ORGANIZATION_ID *= EC.ORGANIZATION_ID \n ";
            }
			String objectName = dto.getWorkorderObjectName();
			String objectCode = dto.getWorkorderObjectCode();
			if( !StrUtil.isEmpty( objectName ) ){
				sqlStr += " AND EO.WORKORDER_OBJECT_NAME LIKE ?  \n ";
				sqlArgs.add(dto.getWorkorderObjectName());
			}

			if( !StrUtil.isEmpty( objectCode ) ){
				sqlStr += " AND EO.WORKORDER_OBJECT_CODE LIKE ?  \n ";
				sqlArgs.add(dto.getWorkorderObjectCode());
			}
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
					+ " AMD.DEPT_CODE = AME.DEPT_CODE"
					+ " AND AMD.COMPANY_CODE = EOCM.COMPANY_CODE"
					//+ " AND EOCM.ORGANIZATION_ID = ?"
					+ " AND ( ? IS NULL OR ? = -1 OR EOCM.ORGANIZATION_ID = ?)"
					+ " AND AME.DEPT_CODE = dbo.NVL(?, AME.DEPT_CODE) "
					+ " AND AME.USER_NAME LIKE '%' || dbo.NVL(?, AME.USER_NAME) || '%' "
					+ " AND AME.EMPLOYEE_NUMBER LIKE '%' || dbo.NVL(?, AME.EMPLOYEE_NUMBER) || '%' ";
			sqlArgs.add(dto.getOrganizationId());
			sqlArgs.add(dto.getOrganizationId());
			sqlArgs.add(dto.getOrganizationId());
			sqlArgs.add(dto.getDeptCode());
			sqlArgs.add(dto.getUserName());
			sqlArgs.add(dto.getEmployeeNumber());
		} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_RENT)) {
			AmsAssetsTransLineDTO dto = (AmsAssetsTransLineDTO) dtoParameter;
			sqlStr = "SELECT ESI.ITEM_NAME,\n"
					+ "       ESI.ITEM_SPEC,\n"
					+ "       EII.BARCODE,\n"
					+ "       EII.ITEM_QTY,\n"
					+ "       EII.RESPONSIBILITY_USER,\n"
					+ "       AME.USER_NAME,\n"
					+ "       AMD.DEPT_NAME,\n"
					+ "       AMD.DEPT_CODE\n"
					+ "FROM   ETS_ITEM_INFO    EII,\n"
					+ "       ETS_SYSTEM_ITEM  ESI,\n"
					+ "       AMS_MIS_EMPLOYEE AME,\n"
					+ "       AMS_MIS_DEPT     AMD\n"
					+ "WHERE  EII.ITEM_CODE = ESI.ITEM_CODE\n"
					+ "       AND EII.ITEM_STATUS NOT IN ('DISCARDED','TO_DISCARD')\n"
					+ "       AND EII.FINANCE_PROP NOT IN ('ASSETS','TD_ASSETS')\n";
if(dto.getUserName().equals("")){
    sqlStr += "       AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID\n";
} else {
    sqlStr += "       AND EII.RESPONSIBILITY_USER = AME.EMPLOYEE_ID\n";
}

					sqlStr += "       AND EII.ORGANIZATION_ID = ?\n"
					+ "       AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE\n"
					+ "       AND AMD.DEPT_CODE = ?\n"
					+ "       AND ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME)\n"
					+ "       AND ESI.ITEM_SPEC LIKE dbo.NVL(?, ESI.ITEM_SPEC)\n"
					+ "       AND EII.BARCODE LIKE dbo.NVL(?, EII.BARCODE)\n";
            sqlArgs.add(user.getOrganizationId());
            sqlArgs.add(dto.getDeptCode());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getBarcode());
            if(!dto.getUserName().equals("")){
                sqlStr += "       AND AME.USER_NAME LIKE ?\n";
                sqlArgs.add(dto.getUserName());
            }
		} else if (lookUpName
				.equals(AssetsLookUpConstant.LOOK_UP_USER_BY_ORGANAZATION)) {
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
					+ " AND (SU.DISABLE_DATE IS NULL OR SU.DISABLE_DATE = CONVERT(DATE,'1900-1-1 12:00:00') OR SU.DISABLE_DATE > "
					+ SyBaseSQLUtil.getCurDate()
					+ ")"
					+ " AND SU.ORGANIZATION_ID = ?"
					+ " AND SU.USERNAME LIKE dbo.NVL(?, SU.USERNAME)"
					+ " AND SU.LOGIN_NAME LIKE dbo.NVL(UPPER(?), SU.LOGIN_NAME)"
					+ " AND ( "
					+ SyBaseSQLUtil.isNull()
					+ "  OR SU.EMPLOYEE_NUMBER LIKE dbo.NVL(?, SU.EMPLOYEE_NUMBER))";
//					+ " AND EXISTS(" + " SELECT" + " '' " + " FROM"
//					+ " SF_USER_RIGHT SUR" + " WHERE"
//					+ " SUR.USER_ID = SU.USER_ID"
//					+ " AND SUR.GROUP_ID = CONVERT(INT, dbo.NVL(?, CONVERT(VARCHAR, SUR.GROUP_ID))))";
			sqlArgs.add(dto.getOrganizationId());
			sqlArgs.add(dto.getUserName());
			sqlArgs.add(dto.getLoginName());
			sqlArgs.add(dto.getEmployeeNumber());
			sqlArgs.add(dto.getEmployeeNumber());
//			if (proCode.equals("42")) {
//				sqlArgs.add("");
//			} else {
//				sqlArgs.add(dto.getGroupId());
//			}

		} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_SPECIAL_ASSETS)) {// 特殊资产调拨查询
			AmsAssetsTransLineDTO dto = (AmsAssetsTransLineDTO) dtoParameter;
			boolean isDeptMgr = user.isDptAssetsManager();
			boolean isCompMgr = user.isComAssetsManager();
			String mtlMgrProps = user.getMtlMgrProps();
			sqlStr = "SELECT /*+rule */"
					+ "EII.BARCODE,"
					+ "EII.FINANCE_PROP,"
					+ "EII.ITEM_QTY,"
					+ "EII.ITEM_CODE,"
					+ "EII.START_DATE, "
					+ "EII.RESPONSIBILITY_USER OLD_RESPONSIBILITY_USER,"
					+ "EII.MAINTAIN_USER,"
					+ "EII.RESPONSIBILITY_DEPT DEPT_CODE, "
					+ "EII.ITEM_STATUS,"
					+ "EII.MAINTAIN_USER MAINTAIN_USER_NAME,"
					+ "EII.POWER,EII.MANUFACTURER_ID,"
					+ "EII.IS_SHARE,"
					+ "EII.CONTENT_CODE,"
					+ "EII.CONTENT_NAME,"
					+ "AMS_PUB_PKG.GET_FLEX_VALUE(EII.ITEM_STATUS, 'ITEM_STATUS') ITEM_STATUS_NAME,"
					+ "AM.MANUFACTURER_NAME,"
					+ "EOCM.BOOK_TYPE_NAME,"
					+ "EPPA.PROJECT_ID,"
					+ "EPPA.SEGMENT1 PROJECT_NUMBER,"
					+ "EPPA.NAME PROJECT_NAME,"
					+ "ESI.ITEM_CATEGORY,"
					+ "AMS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY_NAME,"
					+ "ESI.ITEM_NAME ASSETS_DESCRIPTION,"
					+ "ESI.ITEM_SPEC MODEL_NUMBER,"
					+ "AMD.DEPT_NAME OLD_RESPONSIBILITY_DEPT_NAME,"
					+ "AME.USER_NAME OLD_RESPONSIBILITY_USER_NAME,"
					+ "AME.EMPLOYEE_NUMBER,"
					+ "EO.WORKORDER_OBJECT_NO OLD_LOCATION, "
					+ "EO.WORKORDER_OBJECT_CODE OLD_LOCATION_CODE,"
					+ "EO.WORKORDER_OBJECT_NAME OLD_LOCATION_NAME,"
					+ "EO.WORKORDER_OBJECT_LOCATION,"
					+ "EOCM.ORGANIZATION_ID, "
					+ "EOCM.COMPANY_CODE,"
					+ "EOCM.COMPANY,"
					+ "EC.COUNTY_NAME,\n"
					+ "AMS_PUB_PKG.GET_FLEX_VALUE(EII.FINANCE_PROP, 'FINANCE_PROP')ASSETS_TYPE_NAME \n"
					+ "FROM \n "
                    + "ETS_COUNTY          EC,"
					+ "ETS_OU_CITY_MAP     EOCM,"
                    + "ETS_OBJECT          EO,"
					+ "AMS_OBJECT_ADDRESS  AOA,"
                    + "AMS_MIS_EMPLOYEE    AME,"
					+ "AMS_MIS_DEPT        AMD, "
                    + "ETS_SYSTEM_ITEM     ESI,"
					+ "ETS_PA_PROJECTS_ALL EPPA,"
                    + "ETS_ITEM_INFO       EII,"
					+ "AMS_MANUFACTURER    AM \n"
                    + "WHERE \n "
					+ "EII.PROJECTID *= EPPA.PROJECT_ID\n "
					+ "AND EII.ITEM_CODE = ESI.ITEM_CODE \n "
					+ "AND EII.MANUFACTURER_ID *= AM.MANUFACTURER_ID\n ";
if(dto.getResponsibilityUserName().equals("")){
    sqlStr += "AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID\n ";
} else {
    sqlStr += "AND EII.RESPONSIBILITY_USER = AME.EMPLOYEE_ID\n ";
}

					sqlStr += "AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE\n "
					+ "AND EII.ADDRESS_ID = AOA.ADDRESS_ID \n "
					+ "AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO \n "
					+ "AND EO.ORGANIZATION_ID = EOCM.ORGANIZATION_ID \n "
					+ " AND EO.COUNTY_CODE *= EC.COUNTY_CODE \n" +
                    "  AND EC.ORGANIZATION_ID =* EO.ORGANIZATION_ID  \n "
					+ "AND (EII.DISABLE_DATE "
					+ SyBaseSQLUtil.isNull()
					+ "  OR EII.DISABLE_DATE > "
					+ SyBaseSQLUtil.getCurDate()
					+ " OR EII.DISABLE_DATE IS NULL ) \n "
					+ "AND EII.SYSTEMID > 0"
					+ "AND ESI.ITEM_CODE || '' > '0'"
					+ "AND EOCM.ORGANIZATION_ID > 0"
					+ "AND EC.COUNTY_CODE > 0"
					+ "AND EII.FINANCE_PROP = dbo.NVL(?, EII.FINANCE_PROP)"
					+ "AND ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME)"
					+ "AND EII.BARCODE LIKE dbo.NVL(?, EII.BARCODE)";
if(!dto.getResponsibilityUserName().equals("")){
					sqlStr += " AND AME.USER_NAME  LIKE ?";
}
					sqlStr += " AND EO.WORKORDER_OBJECT_CODE LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_CODE)"
					+ "AND EOCM.ORGANIZATION_ID  = ?"
					+ "AND NOT EXISTS ( SELECT NULL "
					+ "FROM "
					+ "AMS_ASSETS_RESERVED AAR "
					+ "WHERE "
					+ "AAR.BARCODE = EII.BARCODE)"
					+ "AND NOT EXISTS "
					+ "( SELECT NULL FROM "
					+ "AMS_ASSETS_CHK_LOG AACL "
					+ "WHERE AACL.BARCODE = EII.BARCODE "
					+ "AND AACL.ORDER_TYPE <> 'ASS-CHK' "
					+ "AND (AACL.SYN_STATUS = 0 OR AACL.SYN_STATUS = 2))";
			sqlArgs.add(dto.getAssetsType());
			sqlArgs.add(dto.getItemName());
			sqlArgs.add(dto.getBarcode());
            if(!dto.getResponsibilityUserName().equals("")){
			    sqlArgs.add(dto.getResponsibilityUserName());
            }
			sqlArgs.add(dto.getWorkorderObjectCode());
			sqlArgs.add(user.getOrganizationId());

			if (!StrUtil.isEmpty(dto.getDeptCode())) { // 部门限制
				sqlStr += " AND EII.RESPONSIBILITY_DEPT = ?";
				sqlArgs.add(dto.getDeptCode());
			} else { // 根据操作人员权限限制
				if (!isCompMgr && mtlMgrProps.equals("") && isDeptMgr) {
					DTOSet depts = user.getPriviDeptCodes();
					AmsMisDeptDTO dept = null;
					String deptCodes = "(";
					for (int i = 0; i < depts.getSize(); i++) {
						dept = (AmsMisDeptDTO) depts.getDTO(i);
						deptCodes += "'" + dept.getDeptCode() + "', ";
					}
					deptCodes += "'')";
					sqlStr += " AND EII.RESPONSIBILITY_DEPT IN " + deptCodes;
				}
			}
			if (dto.getTransType().equals(AssetsDictConstant.ASS_RED)) {
				sqlStr += " AND ( EII.ITEM_STATUS " + SyBaseSQLUtil.isNullNoParam()
						+ "  OR  EII.ITEM_STATUS = ? OR  EII.ITEM_STATUS = ?)";
				sqlArgs.add(AssetsDictConstant.ITEM_STATUS_NORMAL);
				sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_FREED);
			}
		}  else if (lookUpName.equals(AssetsLookUpConstant.LOOK_TRANSFER_ASSETS) || lookUpName.equals(AssetsLookUpConstant.LOOK_TRANSFER_DIS_ASSETS )  ) { // 查询选择调拨资产
				AmsAssetsTransLineDTO dto = (AmsAssetsTransLineDTO) dtoParameter;
				String transferType = dto.getTransferType();
				String transType = dto.getTransType();
				boolean isDeptMgr = user.isDptAssetsManager();
				boolean isCompMgr = user.isComAssetsManager();
				String mtlMgrProps = user.getMtlMgrProps();
				
				
	            String fromView = "";
	            if(transferType.equals(AssetsDictConstant.TRANS_INN_DEPT)
	                    || transferType.equals(AssetsDictConstant.TRANS_BTW_DEPT)){
	                fromView = "AMS_ASSETS_ADDRESS_V_EX1";
	            } else if(transferType.equals(AssetsDictConstant.TRANS_BTW_COMP)){ 
	                fromView = "AMS_ASSETS_ADDRESS_V_EX2";
	            } else if(transType.equals(AssetsDictConstant.SPEC_ASS_DIS)){
	                fromView = "AMS_ASSETS_ADDRESS_V_EX1";
	            } else if(transType.equals(AssetsDictConstant.ASS_FREE)){
	                fromView = "AMS_ASSETS_ADDRESS_V_EX1";
	            } else if(transType.equals(AssetsDictConstant.ASS_LOSSRPT)){
	                fromView = "AMS_ASSETS_ADDRESS_V_EX1";
	            } else {
	                fromView = "AMS_ASSETS_ADDRESS_V";
	            }
				sqlStr = "SELECT \n " ///*+rule */
						+ " AAAV.BARCODE, \n  "
						+ " AAAV.ASSET_NUMBER, \n "
						+ " AAAV.ASSET_ID, \n "
						+ " AAAV.ASSETS_DESCRIPTION, \n "
						+ " AAAV.MODEL_NUMBER, \n "
						+ " AAAV.ITEM_NAME, \n "
						+ " AAAV.ITEM_SPEC, \n "
						+ " AAAV.VENDOR_NAME, \n "
						+ " AAAV.UNIT_OF_MEASURE, \n "
						+ " ISNULL(CURRENT_UNITS, 1) CURRENT_UNITS, \n "
						// + " AAAV.IMPAIR_RESERVE, \n " //新加
						+ " AAAV.MANUFACTURER_NAME, \n "
						+ " dbo.IS_IMPORTANT_ASSETS( AAAV.CONTENT_CODE ) IMPORTANT_FLAG, \n "   //是否重要资产
						+ " AAAV.CONTENT_CODE OLD_FA_CATEGORY_CODE, \n "
						+ " AAAV.DEPT_CODE OLD_RESPONSIBILITY_DEPT, \n "
						+ " AAAV.DEPT_NAME OLD_RESPONSIBILITY_DEPT_NAME, \n "

						+ " AAAV.SPECIALITY_DEPT, \n"
						+ " AAAV.SPECIALITY_DEPT_NAME, \n"

						+ " AAAV.WORKORDER_OBJECT_NO OLD_LOCATION, \n "
						+ " AAAV.WORKORDER_OBJECT_CODE OLD_LOCATION_CODE, \n "
						+ " AAAV.WORKORDER_OBJECT_NAME OLD_LOCATION_NAME, \n "
						+ " AAAV.RESPONSIBILITY_USER OLD_RESPONSIBILITY_USER, \n "
						+ " AAAV.SCRAP_VALUE, \n "
						+ " AAAV.RESPONSIBILITY_USER_NAME OLD_RESPONSIBILITY_USER_NAME, \n ";
				if (transferType.equals(AssetsDictConstant.TRANS_INN_DEPT)) { // 部门内调拨
					sqlStr = sqlStr
							+ " CASE AAAV.RESPONSIBILITY_USER WHEN ? THEN RESPONSIBILITY_USER ELSE '' END RESPONSIBILITY_USER, \n "
							+ " CASE AAAV.RESPONSIBILITY_USER WHEN ? THEN RESPONSIBILITY_USER_NAME ELSE '' END RESPONSIBILITY_USER_NAME, \n "
							+ " AAAV.ORGANIZATION_ID TO_ORGANIZATION_ID, \n ";
					sqlArgs.add(user.getEmployeeId());
					sqlArgs.add(user.getEmployeeId());
				}
				sqlStr = sqlStr
						+ " AAAV.DATE_PLACED_IN_SERVICE, \n "
						+ " AAAV.COST, \n "
						+ " AAAV.DEPRN_COST, \n " 
						+ " AAAV.DEPRN_COST RETIREMENT_COST, \n "
						+ " AAAV.DEPRECIATION_ACCOUNT OLD_DEPRECIATION_ACCOUNT, \n "
						+ " AAAV.DEPRECIATION, \n "
						+ " dbo.APP_GET_FLEX_VALUE(CONVERT(VARCHAR,AAAV.ASSETS_STATUS),'ASSETS_STATUS') ASSETS_STATUS_DES , \n "
						+ " AAAV.IMPAIR_RESERVE," +
                        "   AAAV.DEPRN_LEFT_MONTH ," +
                        "   AAAV.DEPRECIATION SUM_DEPRECIATION  \n "
                        + " FROM " + fromView + " AAAV\n ";

	            if (user.getBookTypeCode().indexOf("NMMC") >= 0) { //内蒙的各盟市分公司是不参与资产报废的,报废数据直接由区公司财务部导入到系统
					sqlStr += " WHERE AAAV.ORGANIZATION_ID IN (SELECT EOCM2.ORGANIZATION_ID FROM ETS_OU_CITY_MAP EOCM2 WHERE CHARINDEX('TD', EOCM2.COMPANY) = 0 ) \n ";
				} else {
	            	sqlStr += " WHERE AAAV.ORGANIZATION_ID = ? \n ";
	            	sqlArgs.add(user.getOrganizationId()); 
				}
				
				sqlStr += " AND (LTRIM(?) IS NULL OR AAAV.SPECIALITY_DEPT = ? OR LTRIM(AAAV.SPECIALITY_DEPT) IS NULL) \n " ;
				sqlArgs.add(dto.getSpecialityDept());
				sqlArgs.add(dto.getSpecialityDept());
				
				if (!StrUtil.isEmpty(dto.getDeptCode())) { // 部门限制
					sqlStr += " AND AAAV.DEPT_CODE = ? \n ";
					sqlArgs.add(dto.getDeptCode());
				} else { // 根据操作人员权限限制
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

				if (!user.isDptAssetsManager() && !user.isComAssetsManager() && mtlMgrProps.equals("")) { // 责任人限制
					// if(!dto.getTransferType().equals("BTW_COMP")) //(2009.4.15修改
					// su)
					// {
					sqlStr += " AND AAAV.RESPONSIBILITY_USER = ? \n ";
					sqlArgs.add(user.getEmployeeId());
					// }
				}
				if (dto.getTransType().equals(AssetsDictConstant.ASS_RED)) { // 调拨(2009.4.14
					// su)
					sqlStr += " AND (AAAV.ITEM_STATUS = ? OR AAAV.ITEM_STATUS = ?) \n ";
					sqlArgs.add(AssetsDictConstant.ITEM_STATUS_NORMAL);
					sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_FREED);
				} else if (dto.getTransType().equals(AssetsDictConstant.ASS_DIS)) { // 报废单(2009.4.14
					// su) 
					
//					if( !StrUtil.isEmpty( dto.getIsImportantAssets() )){
//						sqlStr += " AND ( dbo.IS_IMPORTANT_ASSETS( AAAV.CONTENT_CODE ) = ? )  ";
//						sqlArgs.add( dto.getIsImportantAssets() );
//					}
					
					if( !StrUtil.isEmpty( dto.getIsOverageAssets() ) ){
						if( dto.getIsOverageAssets().equals( "N" ) ){
							sqlStr +=  " AND DATEADD(MONTH,AAAV.LIFE_IN_YEARS*12,AAAV.DATE_PLACED_IN_SERVICE)>GETDATE() \n" ;
						}else if( dto.getIsOverageAssets().equals( "Y" ) ){
							sqlStr +=  " AND DATEADD(MONTH,AAAV.LIFE_IN_YEARS*12,AAAV.DATE_PLACED_IN_SERVICE)<=GETDATE() \n" ;
						}
					}
					
					if( !StrUtil.isEmpty( dto.getItemStatus() )){
						if( dto.getItemStatus().equals( "Y" ) ){
							sqlStr += " AND AAAV.ITEM_STATUS = 'PRE_DISCARDE' ";  
						}else{
							sqlStr += " AND (AAAV.ITEM_STATUS = ? OR AAAV.ITEM_STATUS = ? ) \n ";
							 
							sqlArgs.add(AssetsDictConstant.ITEM_STATUS_NORMAL);
							sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_FREED);
						} 
					} else{
						sqlStr += " AND (AAAV.ITEM_STATUS =? OR AAAV.ITEM_STATUS = ? OR AAAV.ITEM_STATUS = ?) \n ";
						 
						sqlArgs.add(AssetsDictConstant.ITEM_STATUS_NORMAL);
						sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_FREED);
						sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_PRE_DISCARDE);
					}
					
				} else if (dto.getTransType().equals(AssetsDictConstant.ASS_CLR)) { // 处置前提：已报废或者闲置
					sqlStr += " AND (AAAV.ITEM_STATUS = ? OR AAAV.ITEM_STATUS = ?) \n ";
					sqlArgs.add(AssetsDictConstant.ITEM_STATUS_DISCARDED);
					sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_FREED);
				} else if (dto.getTransType().equals(AssetsDictConstant.ASS_SUB)) { // 减值前提：正常或者闲置
					sqlStr += " AND (AAAV.ITEM_STATUS = ? OR AAAV.ITEM_STATUS = ?) \n ";
					sqlArgs.add(AssetsDictConstant.ITEM_STATUS_NORMAL);
					sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_FREED);
				} else if (dto.getTransType().equals(AssetsDictConstant.ASS_FREE)) { // 闲置前提：正常
					sqlStr += " AND AAAV.ITEM_STATUS = ? \n ";
					sqlArgs.add(AssetsDictConstant.ITEM_STATUS_NORMAL);
					
				} else if(transType.equals(AssetsDictConstant.ASS_LOSSRPT)){ //挂失
					sqlStr += " AND (AAAV.ITEM_STATUS = ? OR AAAV.ITEM_STATUS = ?  OR AAAV.ITEM_STATUS = ?) \n ";
//					sqlStr += " AND AAAV.DEPT_CODE = ? \n "; 
					sqlArgs.add(AssetsDictConstant.ITEM_STATUS_NORMAL);
					sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_FREED);
					sqlArgs.add(AssetsDictConstant.ITEM_STATUS_DISCARDED);
//					sqlArgs.add(user.getDeptCode() );

					if (!StrUtil.isEmpty(dto.getDeptCode())) { // 部门限制
						sqlStr += " AND AAAV.DEPT_CODE = ? \n ";
						sqlArgs.add(dto.getDeptCode());
					} else { // 根据操作人员权限限制
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
					
	            } else if (dto.getTransType().equals(AssetsDictConstant.ASS_SHARE)) { // 共享前提:
					// 资产状态为正常，并且没有共享过的资产
//					sqlStr += " AND (AAAV.ITEM_STATUS = ? AND AAAV.IS_SHARE <> 'Y')";
					sqlStr += " AND AAAV.ITEM_STATUS = ?  \n ";
					sqlArgs.add(AssetsDictConstant.ITEM_STATUS_NORMAL);
				}
				 
				
				sqlStr = sqlStr
//						+ " AND (AAAV.ASSETS_DESCRIPTION LIKE dbo.NVL(?, AAAV.ASSETS_DESCRIPTION) OR AAAV.ITEM_NAME LIKE dbo.NVL(?, AAAV.ITEM_NAME))"
//						+ " AND AAAV.BARCODE LIKE dbo.NVL(?, AAAV.BARCODE)"
//						+ " AND AAAV.RESPONSIBILITY_USER_NAME LIKE dbo.NVL(?, AAAV.RESPONSIBILITY_USER_NAME)"
//						+ " AND ((AAAV.WORKORDER_OBJECT_CODE LIKE dbo.NVL(?, AAAV.WORKORDER_OBJECT_CODE)) OR (AAAV.WORKORDER_OBJECT_NAME LIKE dbo.NVL(?,AAAV.WORKORDER_OBJECT_NAME)))"
						+ " AND AAAV.ASSET_ID >= 1  \n "
	                    + " AND NOT EXISTS ( \n "
						+ " SELECT" + " ''  \n "
						+ " FROM \n "
						+ " AMS_ASSETS_RESERVED AAR \n "
						+ " WHERE \n "
						+ " AAR.BARCODE = AAAV.BARCODE) \n "
//						+ " AND NOT EXISTS ( \n " // 未同步的设备
//						+ " SELECT" + " ''" + " FROM" + " AMS_ASSETS_CHK_LOG AACL \n "
//						+ " WHERE" + " AACL.BARCODE = AAAV.BARCODE \n "
//						+ " AND AACL.ORDER_TYPE <> 'ASS-CHK' \n "
//						+ " AND (AACL.SYN_STATUS = 0 OR AACL.SYN_STATUS = 2)) \n "
						;
				 
				//sj modify start 2011-09-08
				if( !StrUtil.isEmpty( dto.getAssetsDescription() ) ){
					sqlStr = sqlStr + " AND (AAAV.ASSETS_DESCRIPTION LIKE ? OR AAAV.ITEM_NAME LIKE ? ) \n ";
				}
				
				if( !StrUtil.isEmpty( dto.getBarcode() ) ){
					sqlStr = sqlStr + " AND AAAV.BARCODE LIKE ?  \n ";
				}
				
				if( !StrUtil.isEmpty( dto.getOldResponsibilityUserName() ) ){
					sqlStr = sqlStr + " AND AAAV.RESPONSIBILITY_USER_NAME LIKE ?  \n ";
				}
				
				if( !StrUtil.isEmpty( dto.getOldLocationCode() ) ){
					sqlStr = sqlStr + " AND (AAAV.WORKORDER_OBJECT_CODE LIKE ? OR AAAV.WORKORDER_OBJECT_NAME LIKE ? ) \n ";
				}
				
				//TODO 公司间调拨重新写
				if(transferType.equals(AssetsDictConstant.TRANS_BTW_COMP)){
					boolean searchRespDept = false;
					boolean searchRespUser = false;
					if (!StrUtil.isEmpty(dto.getDeptName())) {
						searchRespDept = true ;
					}
					
					if (!StrUtil.isEmpty(dto.getOldResponsibilityUserName())) {
						searchRespDept = true ;
					}
					
					sqlStr = AssetsLookupSQL.getBTWCompSQL( searchRespDept , searchRespUser ).toString();
					sqlArgs = new ArrayList();
					
					sqlArgs.add(user.getOrganizationId()); 
					sqlArgs.add(dto.getSpecialityDept());
					sqlArgs.add(AssetsDictConstant.ITEM_STATUS_NORMAL);
					sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_FREED);
					
					if (!StrUtil.isEmpty(dto.getDeptCode())) { // 部门限制
						sqlStr += " AND EII.RESPONSIBILITY_DEPT = ? \n ";
						sqlArgs.add(dto.getDeptCode());
					} else { // 根据操作人员权限限制
						if (!isCompMgr && mtlMgrProps.equals("") && isDeptMgr) {
							DTOSet depts = user.getPriviDeptCodes();
							AmsMisDeptDTO dept = null;
							String deptCodes = "(";
							for (int i = 0; i < depts.getSize(); i++) {
								dept = (AmsMisDeptDTO) depts.getDTO(i);
								deptCodes += "'" + dept.getDeptCode() + "', ";
							}
							deptCodes += "'')";
							sqlStr += " AND EII.RESPONSIBILITY_DEPT IN " + deptCodes;
		                }
					}

					if (!user.isDptAssetsManager() && !user.isComAssetsManager() && mtlMgrProps.equals("")) { // 责任人限制
						// if(!dto.getTransferType().equals("BTW_COMP")) //(2009.4.15修改
						// su)
						// {
						sqlStr += " AND EII.RESPONSIBILITY_USER = ? \n ";
						sqlArgs.add(user.getEmployeeId());
						// }
					}
					 
					String planStr = AssetsLookupSQL.getBTWCompSQLPlanForEmpty() ; 
					//sj modify start 2011-09-08
					if( !StrUtil.isEmpty( dto.getAssetsDescription() ) ){
						planStr = AssetsLookupSQL.getBTWCompSQLPlanForItem();
						sqlStr = sqlStr + " AND (EFA.ASSETS_DESCRIPTION LIKE ? OR ESI.ITEM_NAME LIKE ? ) \n ";
					}
					
					if( !StrUtil.isEmpty( dto.getBarcode() ) ){
//						planStr = "";
						sqlStr = sqlStr + " AND EII.BARCODE LIKE ?  \n ";
					}
					
					if( !StrUtil.isEmpty( dto.getOldResponsibilityUserName() ) ){
						sqlStr = sqlStr + " AND AME.USER_NAME LIKE ?  \n ";
					}
					
					if( !StrUtil.isEmpty( dto.getOldLocationCode() ) ){
						sqlStr = sqlStr + " AND (EO.WORKORDER_OBJECT_CODE LIKE ? OR EO.WORKORDER_OBJECT_NAME LIKE ? ) \n ";
					} 
					
					sqlStr = sqlStr ;//+ planStr ; 
					 
				}
				 

				if( lookUpName.equals(AssetsLookUpConstant.LOOK_TRANSFER_DIS_ASSETS ) ){
					String planStr = AssetsLookupSQL.getTransDisPlans() ; 
					if( StrUtil.isEmpty( dto.getBarcode() ) 
						//&& StrUtil.isEmpty( dto.getAssetsDescription() ) 
						&& StrUtil.isEmpty( dto.getOldLocationCode() ) 
						&& StrUtil.isEmpty( dto.getOldResponsibilityUserName() )  
						){
						sqlStr = sqlStr ;//+ planStr ; 
					}else{
						
					}
				}
				//sj modify end 
				
				if( !StrUtil.isEmpty( dto.getAssetsDescription() ) ){
					sqlArgs.add(dto.getAssetsDescription());
					sqlArgs.add(dto.getAssetsDescription());
				} 
				if( !StrUtil.isEmpty( dto.getBarcode() ) ){
					sqlArgs.add(dto.getBarcode());
				} 
				if( !StrUtil.isEmpty( dto.getOldResponsibilityUserName() ) ){
					sqlArgs.add(dto.getOldResponsibilityUserName());
				} 
				if( !StrUtil.isEmpty( dto.getOldLocationCode() ) ){
					sqlArgs.add(dto.getOldLocationCode());
					sqlArgs.add(dto.getOldLocationCode());
				}  
				
//				sqlStr += "	AND NOT EXISTS (SELECT NULL FROM ETS_WORKORDER EW,ETS_WORKORDER_DTL EWD WHERE EW.WORKORDER_NO=EWD.WORKORDER_NO AND EW.WORKORDER_FLAG NOT IN ('14','15') AND EWD.BARCODE = AAAV.BARCODE)";
//				sqlStr += " AND NOT EXISTS (SELECT NULL FROM AMS_ASSETS_CHECK_HEADER AACH,AMS_ASSETS_CHECK_LINE AACL WHERE AACH.HEADER_ID=AACL.HEADER_ID AND AACH.ORDER_STATUS <> 'ARCHIEVED' AND AACL.BARCODE = AAAV.BARCODE)";
//				sqlStr += " AND NOT EXISTS (SELECT NULL FROM AMS_ASSETS_TRANS_HEADER AATH,AMS_ASSETS_TRANS_LINE AATL WHERE AATH.TRANS_ID=AATL.TRANS_ID AND AATH.TRANS_STATUS NOT IN ('APPROVED','ASSIGNED','CONFIRMD') AND AATL.BARCODE = AAAV.BARCODE)";
			}		 else if (lookUpName.equals(AssetsLookUpConstant.LOOK_TRANSFER_ASSETS_RFU) ) { //查找调拨资产(紧急调拨补汇总单据用)
				AmsAssetsTransLineDTO dto = (AmsAssetsTransLineDTO) dtoParameter;
				String transferType = dto.getTransferType();
				String transType = dto.getTransType();
				boolean isDeptMgr = user.isDptAssetsManager();
				boolean isCompMgr = user.isComAssetsManager();
				String mtlMgrProps = user.getMtlMgrProps();
	            String fromView = "";

				sqlStr = 
					" --选紧急调拨单据逻辑(汇总补流程用) \n" +
					" SELECT TH.TRANS_TYPE_DEFINE, TH.TRANS_ID, TH.TRANS_ID REMARK, TH.TRANS_NO, TL.BARCODE, \n" +
					
	                //"      TL.OLD_LOCATION, TL.ASSIGNED_TO_LOCATION, \n" +
	                //"		 TL.OLD_RESPONSIBILITY_DEPT, TL.RESPONSIBILITY_DEPT, \n" +
	                //"	     TL.OLD_RESPONSIBILITY_USER, TL.RESPONSIBILITY_USER, \n" +
					"        TH.FROM_OBJECT_NO OLD_LOCATION, TH.TO_OBJECT_NO ASSIGNED_TO_LOCATION, \n" +
	                "		 TH.FROM_DEPT OLD_RESPONSIBILITY_DEPT, TH.TO_DEPT RESPONSIBILITY_DEPT, \n" +
	                "	     TH.FROM_PERSON OLD_RESPONSIBILITY_USER, TH.TO_PERSON RESPONSIBILITY_USER, \n" +
					
					"		 AV.ASSET_NUMBER, AV.ASSET_ID, AV.ASSETS_DESCRIPTION, \n" +
					"        AV.MODEL_NUMBER, AV.CURRENT_UNITS, AV.UNIT_OF_MEASURE, \n" +
					"        TH.FROM_OBJECT_NO, TH.TO_OBJECT_NO, \n" +
					"        EO1.WORKORDER_OBJECT_NAME FROM_OBJECT_NAME, EO2.WORKORDER_OBJECT_NAME TO_OBJECT_NAME \n" +
					"   FROM AMS_ASSETS_TRANS_HEADER TH, AMS_ASSETS_TRANS_LINE TL, dbo.AMS_ASSETS_ADDRESS_V_EX1 AV, \n" +
					"        ETS_OBJECT EO1, ETS_OBJECT EO2 \n" +
					"  WHERE TH.TRANS_ID = TL.TRANS_ID \n" +
					"    AND TH.TRANS_TYPE = 'ASS-TRS' \n" +
					"    AND (TH.TRANSFER_TYPE = 'INN_DEPT' OR LTRIM(TH.TRANSFER_TYPE) IS NULL) \n" +
					"    AND TH.TRANS_STATUS = 'ARCHIEVED' \n" +
					"    AND AV.BARCODE = TL.BARCODE \n" +
					"    AND TH.FROM_OBJECT_NO = EO1.WORKORDER_OBJECT_NO \n" +
					"    AND TH.TO_OBJECT_NO = EO2.WORKORDER_OBJECT_NO \n" +
					"    AND (CASE TH.TRANS_TYPE_DEFINE WHEN NULL THEN '' ELSE TH.TRANS_TYPE_DEFINE END) <> 'RFUED' \n" +
					"    AND TH.FROM_ORGANIZATION_ID = ? \n" ;
				if(!StrUtil.isEmpty( dto.getTransNo())) {
					sqlStr += 
					" 	 AND TH.TRANS_NO LIKE ? \n";
				}
				if( !StrUtil.isEmpty( dto.getAssetsDescription() ) ){
					sqlStr += 
					" 	 AND AV.ASSETS_DESCRIPTION LIKE ? \n";
				}
				
				if( !StrUtil.isEmpty( dto.getBarcode() ) ){
					sqlStr += 
					"    AND TL.BARCODE LIKE ? \n";
				}
				
				if( !StrUtil.isEmpty( dto.getFromObjectName() ) ){
					sqlStr +=
					"    AND EO1.WORKORDER_OBJECT_NAME LIKE ? \n";
				}
				
				sqlStr += 
					"  ORDER BY TH.TRANS_NO DESC, TL.BARCODE DESC \n" ;
				
				sqlArgs.add(user.getOrganizationId()); 
				//sqlArgs.add(user.getEmployeeId());
				
				if(!StrUtil.isEmpty( dto.getTransNo())) {
					sqlArgs.add(dto.getTransNo());
				}
				if( !StrUtil.isEmpty( dto.getAssetsDescription() ) ){
					sqlArgs.add(dto.getAssetsDescription());
				} 
				if( !StrUtil.isEmpty( dto.getBarcode() ) ){
					sqlArgs.add(dto.getBarcode());
				} 
				if( !StrUtil.isEmpty( dto.getFromObjectName() ) ){
					sqlArgs.add(dto.getFromObjectName());
				}
			}
		
		else if (lookUpName
				.equals(AssetsLookUpConstant.LOOK_TRANSFER_ASSETS_TD)) { // TD查询选择调拨资产
			AmsAssetsTransLineDTO dto = (AmsAssetsTransLineDTO) dtoParameter;
			String transferType = dto.getTransferType();
			String transType = dto.getTransType();
			boolean isDeptMgr = user.isDptAssetsManager();
			boolean isCompMgr = user.isComAssetsManager();
			String mtlMgrProps = user.getMtlMgrProps();
            String fromView = "";
            if(transferType.equals(AssetsDictConstant.TRANS_INN_DEPT)
                    || transferType.equals(AssetsDictConstant.TRANS_BTW_DEPT)){
                fromView = "TD_ASSETS_ADDRESS_V_EX1";
            } else if(transferType.equals(AssetsDictConstant.TRANS_BTW_COMP)){
                fromView = "TD_ASSETS_ADDRESS_V_EX2";
            } else if(transType.equals(AssetsDictConstant.SPEC_ASS_DIS)){
                fromView = "TD_ASSETS_ADDRESS_V_EX1";
            } else if(transType.equals(AssetsDictConstant.ASS_FREE)){
                fromView = "TD_ASSETS_ADDRESS_V_EX1";
            } else {
                fromView = "TD_ASSETS_ADDRESS_V";
            }
			sqlStr = "SELECT /*+rule */"
					+ " AAAV.BARCODE,"
					+ " AAAV.ASSET_NUMBER,"
					+ " AAAV.ASSET_ID,"
					+ " AAAV.ASSETS_DESCRIPTION,"
					+ " AAAV.MODEL_NUMBER,"
					+ " AAAV.ITEM_NAME,"
					+ " AAAV.ITEM_SPEC,"
					+ " AAAV.UNIT_OF_MEASURE,"
					+ " ISNULL(AAAV.CURRENT_UNITS, 1) CURRENT_UNITS,"
					// + " AAAV.IMPAIR_RESERVE," //新加
					+ " AAAV.MANUFACTURER_NAME,"
					+ " AAAV.FA_CATEGORY_CODE OLD_FA_CATEGORY_CODE,"
					+ " AAAV.DEPT_CODE OLD_RESPONSIBILITY_DEPT,"
					+ " AAAV.DEPT_NAME OLD_RESPONSIBILITY_DEPT_NAME,"
					
					+ " AAAV.SPECIALITY_DEPT, \n"
					+ " AAAV.SPECIALITY_DEPT_NAME, \n"
					
					+ " AAAV.WORKORDER_OBJECT_NO OLD_LOCATION,"
					+ " AAAV.WORKORDER_OBJECT_CODE OLD_LOCATION_CODE,"
					+ " AAAV.WORKORDER_OBJECT_NAME OLD_LOCATION_NAME,"
					+ " AAAV.RESPONSIBILITY_USER OLD_RESPONSIBILITY_USER,"
					+ " AAAV.RESPONSIBILITY_USER_NAME OLD_RESPONSIBILITY_USER_NAME,";
			if (transferType.equals(AssetsDictConstant.TRANS_INN_DEPT)) { // 部门内调拨
				sqlStr = sqlStr
						+ " CASE AAAV.RESPONSIBILITY_USER WHEN ? THEN AAAV.RESPONSIBILITY_USER ELSE '' END RESPONSIBILITY_USER,"
						+ " CASE AAAV.RESPONSIBILITY_USER WHEN ? THEN AAAV.RESPONSIBILITY_USER_NAME ELSE '' END RESPONSIBILITY_USER_NAME,"
						+ " AAAV.ORGANIZATION_ID TO_ORGANIZATION_ID,";
				sqlArgs.add(user.getEmployeeId());
				sqlArgs.add(user.getEmployeeId());
			}
			sqlStr = sqlStr
					+ " AAAV.DATE_PLACED_IN_SERVICE,"
					+ " AAAV.COST,"
					+ " AAAV.DEPRN_COST,"
					+ " AAAV.COST RETIREMENT_COST,"
					+ " AAAV.DEPRECIATION_ACCOUNT OLD_DEPRECIATION_ACCOUNT,"
					+ " AAAV.DEPRECIATION,"
					+ " dbo.APP_GET_FLEX_VALUE(CONVERT(VARCHAR,AAAV.ASSETS_STATUS),'ASSETS_STATUS') ASSETS_STATUS_DES ,"
					+ " AAAV.IMPAIR_RESERVE,"
                    + " AAAV.DEPRN_LEFT_MONTH,"
                    + " AAAV.DEPRECIATION SUM_DEPRECIATION "
                    + " FROM "
                    + fromView
					+ " AAAV" ;
                    //+ " WHERE";
					//+ " AAAV.ORGANIZATION_ID = ?";
			//sqlArgs.add(user.getOrganizationId());
			
            if (user.getBookTypeCode().indexOf("NMMC") >= 0) { //内蒙的各盟市分公司是不参与资产报废的,报废数据直接由区公司财务部导入到系统
				sqlStr += " WHERE AAAV.ORGANIZATION_ID IN (SELECT EOCM2.ORGANIZATION_ID FROM ETS_OU_CITY_MAP EOCM2 WHERE CHARINDEX('TD', EOCM2.COMPANY) > 0 ) \n ";
			} else {
            	sqlStr += " WHERE AAAV.ORGANIZATION_ID = ? \n ";
            	sqlArgs.add(user.getOrganizationId()); 
			}
            sqlStr += "AND (LTRIM(?) IS NULL OR AAAV.SPECIALITY_DEPT = ? OR LTRIM(AAAV.SPECIALITY_DEPT) IS NULL)\n " ;

//			sqlStr += " AND (AAAV.SPECIALITY_DEPT = ? OR LTRIM(AAAV.SPECIALITY_DEPT) IS NULL) \n " ;
			sqlArgs.add(dto.getSpecialityDept());
			sqlArgs.add(dto.getSpecialityDept());
			if (!StrUtil.isEmpty(dto.getDeptCode())) { // 部门限制
				sqlStr += " AND AAAV.DEPT_CODE = ?";
				sqlArgs.add(dto.getDeptCode());
			} else { // 根据操作人员权限限制
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
			if (!user.isDptAssetsManager() && !user.isComAssetsManager()
					&& mtlMgrProps.equals("")) { // 责任人限制
				// if(!dto.getTransferType().equals("BTW_COMP")) //(2009.4.15修改
				// su)
				// {
				sqlStr += " AND AAAV.RESPONSIBILITY_USER = ?";
				sqlArgs.add(user.getEmployeeId());
				// }
			}

            if (transType.equals(AssetsDictConstant.ASS_RED)) { // 调拨(2009.4.14
                // su)
                sqlStr += " AND (AAAV.ITEM_STATUS = ? OR AAAV.ITEM_STATUS = ?)";
                sqlArgs.add(AssetsDictConstant.ITEM_STATUS_NORMAL);
                sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_FREED);
            } else if (transType.equals(AssetsDictConstant.ASS_DIS)) { // 报废单(2009.4.14
                // su)
                sqlStr += " AND (AAAV.ITEM_STATUS =? OR AAAV.ITEM_STATUS = ? OR AAAV.ITEM_STATUS = ?)";
                sqlArgs.add(AssetsDictConstant.ITEM_STATUS_NORMAL);
                sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_FREED);
                sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_PRE_DISCARDE);
            } else if (transType.equals(AssetsDictConstant.ASS_CLR)) { // 处置前提：已报废或者闲置
                sqlStr += " AND (AAAV.ITEM_STATUS = ? OR AAAV.ITEM_STATUS = ?)";
                sqlArgs.add(AssetsDictConstant.ITEM_STATUS_DISCARDED);
                sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_FREED);
            } else if (transType.equals(AssetsDictConstant.ASS_SUB)) { // 减值前提：正常或者闲置
                sqlStr += " AND (AAAV.ITEM_STATUS = ? OR AAAV.ITEM_STATUS = ?)";
                sqlArgs.add(AssetsDictConstant.ITEM_STATUS_NORMAL);
                sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_FREED);
            } else if (transType.equals(AssetsDictConstant.ASS_FREE)) { // 闲置前提：正常
                sqlStr += " AND AAAV.ITEM_STATUS = ?";
                sqlArgs.add(AssetsDictConstant.ITEM_STATUS_NORMAL);
            } else if (transType.equals(AssetsDictConstant.ASS_SHARE)) { // 共享前提:
                // 资产状态为正常，并且没有共享过的资产
//				sqlStr += " AND (AAAV.ITEM_STATUS = ? AND AAAV.IS_SHARE <> 'Y')";
                sqlStr += " AND AAAV.ITEM_STATUS = ? ";
                sqlArgs.add(AssetsDictConstant.ITEM_STATUS_NORMAL);
            }

			sqlStr = sqlStr
					+ " AND (AAAV.ASSETS_DESCRIPTION LIKE dbo.NVL(?, AAAV.ASSETS_DESCRIPTION) OR AAAV.ITEM_NAME LIKE dbo.NVL(?, AAAV.ITEM_NAME))"
					+ " AND AAAV.BARCODE LIKE dbo.NVL(?, AAAV.BARCODE)"
					+ " AND AAAV.RESPONSIBILITY_USER_NAME LIKE dbo.NVL(?, AAAV.RESPONSIBILITY_USER_NAME)"
					+ " AND ((AAAV.WORKORDER_OBJECT_CODE LIKE dbo.NVL(?, AAAV.WORKORDER_OBJECT_CODE)) OR (AAAV.WORKORDER_OBJECT_NAME LIKE dbo.NVL(?,AAAV.WORKORDER_OBJECT_NAME)))"
//					+ " AND AAAV.PARENT_BARCODE IS NULL "
					+ " AND NOT EXISTS (" + " SELECT"
					+ " NULL"
					+ " FROM"
					+ " AMS_ASSETS_RESERVED AAR"
					+ " WHERE"
					+ " AAR.BARCODE = AAAV.BARCODE)"
					+ " AND NOT EXISTS (" // 未同步的设备
					+ " SELECT" + " NULL" + " FROM"
					+ " AMS_ASSETS_CHK_LOG AACL" + " WHERE"
					+ " AACL.BARCODE = AAAV.BARCODE"
					+ " AND AACL.ORDER_TYPE <> 'ASS-CHK'"
					+ " AND (AACL.SYN_STATUS =0 OR  AACL.SYN_STATUS=2)" +
							")";
			sqlArgs.add(dto.getAssetsDescription());
			sqlArgs.add(dto.getAssetsDescription());
			sqlArgs.add(dto.getBarcode());
			sqlArgs.add(dto.getOldResponsibilityUserName());
			sqlArgs.add(dto.getOldLocationCode());
			sqlArgs.add(dto.getOldLocationCode());
		} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_ASSETS)) { // 需要加上权限限制
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
					+ " AND NOT EXISTS (" + " SELECT" + " NULL" + " FROM"
					+ " AMS_ASSETS_RESERVED AAR" + " WHERE"
					+ " AAR.BARCODE = AAAV.BARCODE)";
			sqlArgs.add(user.getOrganizationId());
			sqlArgs.add(dto.getDeptCode());
			sqlArgs.add(dto.getItemName());
			sqlArgs.add(dto.getItemName());
			sqlArgs.add(dto.getItemName());
			sqlArgs.add(dto.getBarcode());
			if (transType.equals(AssetsDictConstant.ASS_CLR)) {
				sqlStr += " AND (AAAV.ITEM_STATUS IS NULL  OR AAAV.ITEM_STATUS = ?)";
				sqlArgs.add(AssetsDictConstant.ITEM_STATUS_DISCARDED);
			} else {
				sqlStr += " AND (AAAV.ITEM_STATUS IS NULL  OR AAAV.ITEM_STATUS = ?)";
				sqlArgs.add(AssetsDictConstant.ITEM_STATUS_NORMAL);
			}
		} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_COMPANY)) {
			EtsOuCityMapDTO dto = (EtsOuCityMapDTO) dtoParameter;
			sqlStr = "SELECT" + " EOCM.ORGANIZATION_ID,"
					+ " EOCM.COMPANY_CODE," + " EOCM.COMPANY COMPANY_NAME"
					+ " FROM" + " ETS_OU_CITY_MAP EOCM" + " WHERE"
					+ " EOCM.COMPANY LIKE dbo.NVL(?, EOCM.COMPANY)";
			sqlArgs.add(dto.getCompany());
		} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_MIS_COMPANY)) {
			EtsOuCityMapDTO dto = (EtsOuCityMapDTO) dtoParameter; 
			sqlStr = "SELECT"  
					+ " EOCM.ORGANIZATION_ID,"
					+ " EOCM.COMPANY_CODE,"
					+ " EOCM.COMPANY COMPANY_NAME"
					+ " FROM" 
					+ " ETS_OU_CITY_MAP EOCM" 
					+ " WHERE"
					+ " EOCM.COMPANY LIKE dbo.NVL(?, EOCM.COMPANY)"
					+ " AND ENABLED='Y' "; 
			sqlArgs.add(dto.getCompany());
			
//			if( ( user.isProvinceUser() &&   user.isSysAdmin()  ) || user.isComAssetsManager()  ) {
			if ( ( user.isProvinceUser() && ( user.isSysAdmin() ||  user.isComAssetsManager() ) ) 
					|| user.isProvAssetsManager() ) {
	                
			} else{
	        	sqlStr += " AND ORGANIZATION_ID= ? ";
				sqlArgs.add( user.getOrganizationId() );
	        }
			if (StrUtil.isNotEmpty( user.getIsTd() )) {
				sqlStr += " AND IS_TD=?";
		        sqlArgs.add( user.getIsTd() );
	        } 
		} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_PRI_DEPT)) {
			AmsAssetsPriviDTO dto = (AmsAssetsPriviDTO) dtoParameter;
			sqlStr = "SELECT" + " AMD.COMPANY_CODE,"
					+ " EOCM.COMPANY COMPANY_NAME," + " AMD.DEPT_CODE,"
					+ " AMD.DEPT_NAME" + " FROM" + " AMS_MIS_DEPT    AMD,"
					+ " ETS_OU_CITY_MAP EOCM" + " WHERE"
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
			sqlStr = "SELECT" + " CONVERT(VARCHAR,SR.ROLE_ID) ROLE_ID,"
					+ " SR.ROLE_NAME ROLE_NAME," + " SR.ROLE_DESC" + " FROM"
					+ " SF_ROLE SR" + " WHERE" + " SR.ROLE_NAME IN ("
					+ assetsMgr + ") ";

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
					+ " SU.ENABLED = 'Y'"
					+ " AND SU.ORGANIZATION_ID *= EOCM.ORGANIZATION_ID"
					+ " AND SU.ORGANIZATION_ID = ?" + " AND ("
					+ SyBaseSQLUtil.isNull() + "  OR SU.USERNAME LIKE ?)"
					+ " AND (" + SyBaseSQLUtil.isNull()
					+ " OR SU.LOGIN_NAME LIKE UPPER(?))" + " AND ( "
					+ SyBaseSQLUtil.isNull()
					+ "  OR SU.EMPLOYEE_NUMBER LIKE ?)";
//					+ " AND EXISTS("
//					+ " SELECT"
//					+ " NULL"
//					+ " FROM"
//					+ " SF_USER_RIGHT SUR"
//					+ " WHERE"
//					+ " SUR.USER_ID = SU.USER_ID"
//					+ " AND SUR.GROUP_ID = CONVERT(INT, dbo.NVL( (CASE ? WHEN '-1' THEN NULL WHEN '0' THEN NULL ELSE ? END), CONVERT(VARCHAR, SUR.GROUP_ID))))"
//					+ " ORDER BY SU.LOGIN_NAME ";
			sqlArgs.add(user.getOrganizationId());
			sqlArgs.add(dto.getUserName());
			sqlArgs.add(dto.getUserName());
			sqlArgs.add(dto.getLoginName());
			sqlArgs.add(dto.getLoginName());
			sqlArgs.add(dto.getEmployeeNumber());
			sqlArgs.add(dto.getEmployeeNumber());
//			sqlArgs.add("" + dto.getGroupId());
//			sqlArgs.add("" + dto.getGroupId());
			
		}
		else if(lookUpName.equals(LookUpConstant.LOOK_UP_USER_WITH_DEPT))
		{
			AmsAssetsPriviDTO dto = (AmsAssetsPriviDTO) dtoParameter;
			sqlStr="SELECT SU.USER_ID,SU.USERNAME USER_NAME,SU.EMPLOYEE_NUMBER,SU.LOGIN_NAME,AMD.DEPT_NAME,AMD.DEPT_CODE, SG.GROUP_NAME,SG.GROUP_ID FROM SF_USER SU,SF_GROUP SG,SF_USER_AUTHORITY SUA, SINO_GROUP_MATCH SGM, AMS_MIS_DEPT AMD WHERE AMD.DEPT_CODE=SGM.DEPT_ID AND SGM.GROUP_ID=SG.GROUP_ID AND SG.GROUP_NAME=SUA.GROUP_NAME AND SUA.USER_ID=SU.USER_ID AND SU.LOGIN_NAME LIKE ? AND SU.USERNAME LIKE ? AND SU.EMPLOYEE_NUMBER LIKE ?";
			String loginName=dto.getLoginName();
			String userName=dto.getUserName();
			String employeeNumber=dto.getEmployeeNumber();
			if(loginName.endsWith("%"))
			{
				sqlArgs.add(loginName.trim());
			}
			else
			{
				sqlArgs.add(loginName.trim()+"%");
			}
			if(userName.endsWith("%"))
			{
				sqlArgs.add(userName.trim());
			}
			else
			{
				sqlArgs.add(userName.trim()+"%");
			}	
			if(employeeNumber.endsWith("%"))
			{
				sqlArgs.add(employeeNumber.trim());
			}
			else
			{
				sqlArgs.add(employeeNumber.trim()+"%");
			}
		}
		else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_USER_CHECK_BATCH)) {//资产盘点单任务批，选择归档人
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
					+ " SU.ENABLED = 'Y'"
					+ " AND SU.ORGANIZATION_ID *= EOCM.ORGANIZATION_ID"
					+ " AND SU.ORGANIZATION_ID = ?" + " AND ("
					+ SyBaseSQLUtil.isNull() + "  OR SU.USERNAME LIKE ?)"
					+ " AND (" + SyBaseSQLUtil.isNull()
					+ " OR SU.LOGIN_NAME LIKE UPPER(?))" + " AND ( "
					+ SyBaseSQLUtil.isNull()
					+ "  OR SU.EMPLOYEE_NUMBER LIKE ?)"
					+ " AND EXISTS("
					+ " SELECT"
					+ " NULL"
					+ " FROM"
					+ " SF_USER_RIGHT SUR"
					+ " WHERE"
					+ " SUR.USER_ID = SU.USER_ID"
					+ " AND SUR.ROLE_ID = 1251"//归档人权限
					+ " AND SUR.GROUP_ID = CONVERT(INT, dbo.NVL( (CASE ? WHEN '-1' THEN NULL WHEN '0' THEN NULL ELSE ? END), CONVERT(VARCHAR, SUR.GROUP_ID))))"
					+ " ORDER BY SU.LOGIN_NAME ";
			sqlArgs.add(user.getOrganizationId());
			sqlArgs.add(dto.getUserName());
			sqlArgs.add(dto.getUserName());
			sqlArgs.add(dto.getLoginName());
			sqlArgs.add(dto.getLoginName());
			sqlArgs.add(dto.getEmployeeNumber());
			sqlArgs.add(dto.getEmployeeNumber());
			sqlArgs.add("" + dto.getGroupId());
			sqlArgs.add("" + dto.getGroupId());
			
		}   else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_ZERO_USER)) {
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
					+ " ETS_OU_CITY_MAP EOCM,"
					+ " AMS_MIS_EMPLOYEE AME,"
					+ " AMS_MIS_DEPT AMD "
					+ " WHERE"
					+ " SU.ENABLED = 'Y'"
					+ " AND AME.DEPT_CODE = AMD.DEPT_CODE"
//					+ " AND AME.USER_NAME = SU.USERNAME"
					+ " AND AME.EMPLOYEE_NUMBER = SU.EMPLOYEE_NUMBER "
					+ " AND AMD.COST_CENTER_CODE = (SELECT COST_CENTER_CODE FROM ETS_ITEM_TURN WHERE BARCODE=?)"
					+ " AND AME.COMPANY_CODE = (SELECT COMPANY_CODE FROM ETS_OU_CITY_MAP  EOCM,ETS_ITEM_INFO EII WHERE EII.ORGANIZATION_ID=EOCM.ORGANIZATION_ID AND EII.BARCODE=?)"
					+ " AND SU.ORGANIZATION_ID *= EOCM.ORGANIZATION_ID"
					// + " AND (SU.DISABLE_DATE " +
					// SyBaseSQLUtil.isNullNoParam() + " OR SU.DISABLE_DATE > "
					// + SyBaseSQLUtil.getCurDate() + ")"
					+ " AND SU.ORGANIZATION_ID = ?" + " AND ("
					+ SyBaseSQLUtil.isNull() + "  OR SU.USERNAME LIKE ?)"
					+ " AND (" + SyBaseSQLUtil.isNull()
					+ " OR SU.LOGIN_NAME LIKE UPPER(?))" + " AND ( "
					+ SyBaseSQLUtil.isNull()
					+ "  OR SU.EMPLOYEE_NUMBER LIKE ?)"
//					+ " AND (" +SyBaseSQLUtil.isNull()
//					+ " OR SU.DEPT_CODE = ?)"
					+ " AND EXISTS("
					+ " SELECT"
					+ " NULL"
					+ " FROM"
					+ " SF_USER_RIGHT SUR"
					+ " WHERE"
					+ " SUR.USER_ID = SU.USER_ID"
					+ " AND SUR.GROUP_ID = CONVERT(INT, dbo.NVL( (CASE ? WHEN '-1' THEN NULL WHEN '0' THEN NULL ELSE ? END), CONVERT(VARCHAR, SUR.GROUP_ID))))"
					+ " ORDER BY SU.LOGIN_NAME ";
			sqlArgs.add(dto.getRecord());
			sqlArgs.add(dto.getRecord());
			sqlArgs.add(user.getOrganizationId());
			sqlArgs.add(dto.getUserName());
			sqlArgs.add(dto.getUserName());
			sqlArgs.add(dto.getLoginName());
			sqlArgs.add(dto.getLoginName());
			sqlArgs.add(dto.getEmployeeNumber());
			sqlArgs.add(dto.getEmployeeNumber());
//			sqlArgs.add(dto.getDeptCode());
//			sqlArgs.add(dto.getDeptCode());
			// if (proCode.equals("42")) {
			// sqlArgs.add("");
			// }else{
			sqlArgs.add("" + dto.getGroupId());
			sqlArgs.add("" + dto.getGroupId());
			// }
			
		}  else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_USER_ZERO_ACHIEVE)) { //查询工单归档人
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
					+ " ETS_OU_CITY_MAP EOCM,"
					+ " AMS_MIS_EMPLOYEE AME,"
					+ " AMS_MIS_DEPT AMD "
					+ " WHERE"
					+ "    AME.DEPT_CODE = AMD.DEPT_CODE"
//					+ " AND AME.USER_NAME = SU.USERNAME"
					+ " AND AME.EMPLOYEE_NUMBER = SU.EMPLOYEE_NUMBER "
					+ " AND AMD.COST_CENTER_CODE = (SELECT COST_CENTER_CODE FROM ETS_ITEM_TURN WHERE BARCODE=?)"
					+ " AND AME.COMPANY_CODE = (SELECT COMPANY_CODE FROM ETS_OU_CITY_MAP  EOCM,ETS_ITEM_INFO EII WHERE EII.ORGANIZATION_ID=EOCM.ORGANIZATION_ID AND EII.BARCODE=?)"
					+ " AND SU.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
					+ " AND SU.ORGANIZATION_ID = ?" + " AND ("
					+ SyBaseSQLUtil.isNull() + "  OR SU.USERNAME LIKE ?)"
					+ " AND (" + SyBaseSQLUtil.isNull()
					+ " OR SU.LOGIN_NAME LIKE UPPER(?))" + " AND ( "
					+ SyBaseSQLUtil.isNull()
					+ "  OR SU.EMPLOYEE_NUMBER LIKE ?)"
		            + " AND EXISTS "
		            + "	      (SELECT NULL " 
		            + "       	 FROM SF_USER_AUTHORITY SUA " 
		            + "       	WHERE SU.USER_ID = SUA.USER_ID " 
		            + "            AND SUA.ROLE_NAME = '工单归档人') " 
		            
					+ " AND EXISTS "
					+ " 		(SELECT NULL"
					+ " 		   FROM SF_USER_RIGHT SUR"
					+ " 		  WHERE SUR.USER_ID = SU.USER_ID"
					+ " 			AND SUR.GROUP_ID = CONVERT(INT, dbo.NVL( (CASE ? WHEN '-1' THEN NULL WHEN '0' THEN NULL ELSE ? END), CONVERT(VARCHAR, SUR.GROUP_ID))))"
					+ " ORDER BY SU.LOGIN_NAME ";
			sqlArgs.add(dto.getRecord());
			sqlArgs.add(dto.getRecord());
			sqlArgs.add(user.getOrganizationId());
			sqlArgs.add(dto.getUserName());
			sqlArgs.add(dto.getUserName());
			sqlArgs.add(dto.getLoginName());
			sqlArgs.add(dto.getLoginName());
			sqlArgs.add(dto.getEmployeeNumber());
			sqlArgs.add(dto.getEmployeeNumber());
			sqlArgs.add("" + dto.getGroupId());
			sqlArgs.add("" + dto.getGroupId());
			
		}else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_USER_ACHIEVE)) { //查询工单归档人
			AmsAssetsPriviDTO dto = (AmsAssetsPriviDTO) dtoParameter;
			sqlStr = "SELECT"
					+ " EOCM.COMPANY_CODE,"
					+ " EOCM.COMPANY COMPANY_NAME,"
					+ " SU.LOGIN_NAME,"
					+ " SU.USER_ID,"
					+ " SU.USERNAME USER_NAME,"
					+ " SU.EMPLOYEE_NUMBER"
					+ " FROM"
					+ " SF_USER SU,"
					+ " ETS_OU_CITY_MAP EOCM"
					+ " WHERE"
					+ " SU.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
					+ " AND SU.ORGANIZATION_ID = ?" + " AND ("
					+ SyBaseSQLUtil.isNull() + "  OR SU.USERNAME LIKE ?)"
					+ " AND (" + SyBaseSQLUtil.isNull()
					+ " OR SU.LOGIN_NAME LIKE UPPER(?))" + " AND ( "
					+ SyBaseSQLUtil.isNull()
					+ "  OR SU.EMPLOYEE_NUMBER LIKE ?)"
		            + " AND EXISTS "
		            + "	      (SELECT NULL " 
		            + "       	 FROM SF_USER_AUTHORITY SUA " 
		            + "       	WHERE SU.USER_ID = SUA.USER_ID " 
		            + "            AND SUA.ROLE_NAME = '工单归档人') " 
		            
					+ " AND EXISTS "
					+ " 		(SELECT NULL"
					+ " 		   FROM SF_USER_RIGHT SUR"
					+ " 		  WHERE SUR.USER_ID = SU.USER_ID"
					+ " 			AND SUR.GROUP_ID = CONVERT(INT, dbo.NVL( (CASE ? WHEN '-1' THEN NULL WHEN '0' THEN NULL ELSE ? END), CONVERT(VARCHAR, SUR.GROUP_ID))))"
					+ " ORDER BY SU.LOGIN_NAME ";
			sqlArgs.add(user.getOrganizationId());
			sqlArgs.add(dto.getUserName());
			sqlArgs.add(dto.getUserName());
			sqlArgs.add(dto.getLoginName());
			sqlArgs.add(dto.getLoginName());
			sqlArgs.add(dto.getEmployeeNumber());
			sqlArgs.add(dto.getEmployeeNumber());
			sqlArgs.add("" + dto.getGroupId());
			sqlArgs.add("" + dto.getGroupId());
			
		} else if (lookUpName.equals("LOOK_UP_SAMPLING_USER")) {// 抽查工单中选择执行人
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
					+ " AND (SU.DISABLE_DATE "
					+ SyBaseSQLUtil.isNull()
					+ "  OR SU.DISABLE_DATE > "
					+ SyBaseSQLUtil.getCurDate()
					+ ")"
					// + " AND SU.ORGANIZATION_ID = ?"
					+ " AND SU.USERNAME LIKE dbo.NVL(?, SU.USERNAME)"
					+ " AND SU.LOGIN_NAME LIKE dbo.NVL(UPPER(?), SU.LOGIN_NAME)"
					+ " AND ( "
					+ SyBaseSQLUtil.isNull()
					+ "  OR SU.EMPLOYEE_NUMBER LIKE dbo.NVL(?, SU.EMPLOYEE_NUMBER))"
					+ " AND EXISTS(" + " SELECT" + " NULL" + " FROM"
					+ " SF_USER_RIGHT SUR" + " WHERE"
					+ " SUR.USER_ID = SU.USER_ID"
					+ " AND SUR.GROUP_ID = CONVERT(INT, dbo.NVL(?, CONVERT(VARCHAR, SUR.GROUP_ID))))";

			sqlArgs.add(dto.getUserName());
			sqlArgs.add(dto.getLoginName());
			sqlArgs.add(dto.getEmployeeNumber());
			sqlArgs.add(dto.getEmployeeNumber());
			if (proCode.equals("42")) {
				sqlArgs.add("");
			} else {
				sqlArgs.add(dto.getGroupId());
			}
			if (user.getOrganizationId() != 82) {
				sqlStr += " AND SU.ORGANIZATION_ID = ?";
				sqlArgs.add(user.getOrganizationId());
			}
		} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_PERSON)) {
			SfUserDTO dto = (SfUserDTO) dtoParameter;
			sqlStr = "SELECT"
					+ " AME.EMPLOYEE_ID,"
					+ " AME.USER_NAME,"
					+ " AME.EMPLOYEE_NUMBER,"
					+ " AME.DEPT_CODE,"
					+ " AMD.DEPT_NAME,"
					+ " EOCM.COMPANY,"
					+ " EOCM.ORGANIZATION_ID"
					+ " FROM"
					+ " AMS_MIS_EMPLOYEE AME,"
					+ " AMS_MIS_DEPT     AMD,"
					+ " ETS_OU_CITY_MAP  EOCM"
					+ " WHERE"
					+ " AMD.DEPT_CODE = AME.DEPT_CODE "
					+ " AND AMD.COMPANY_CODE = EOCM.COMPANY_CODE"
					+ " AND AME.USER_NAME LIKE dbo.NVL(?, AME.USER_NAME)"
					+ " AND AME.EMPLOYEE_NUMBER LIKE dbo.NVL(?, AME.EMPLOYEE_NUMBER)"
					+ " AND EOCM.ORGANIZATION_ID = ?" + " AND ( "
					+ SyBaseSQLUtil.isNull() + "  OR AMD.DEPT_CODE = ?)";
			sqlArgs.add(dto.getUsername());
			sqlArgs.add(dto.getEmployeeNumber());
			sqlArgs.add(user.getOrganizationId());
			sqlArgs.add(dto.getDeptCode());
			sqlArgs.add(dto.getDeptCode());
		} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_LOCATION)) {
//			EtsObjectDTO dto = (EtsObjectDTO) dtoParameter;
//			String contentBlurred = dto.getContentBlurred();
//			sqlStr = "SELECT" + " EOCM.COMPANY COMPANY_NAME,"
//					+ " EC.COUNTY_NAME," + " EFV.VALUE OBJECT_CATEGORY,"
//					+ " EO.WORKORDER_OBJECT_NO CHECK_LOCATION,"
//					+ " EO.WORKORDER_OBJECT_CODE OBJECT_CODE,"
//					+ " EO.WORKORDER_OBJECT_NAME OBJECT_NAME,"
//					+ " EO.WORKORDER_OBJECT_LOCATION OBJECT_LOCATION" + " FROM"
//					+ " ETS_OBJECT         EO," + " ETS_COUNTY         EC,"
//					+ " ETS_FLEX_VALUE_SET EFVS," + " ETS_FLEX_VALUES    EFV,"
//					+ " ETS_OU_CITY_MAP    EOCM" + " WHERE"
//					+ " EO.COUNTY_CODE *= EC.COUNTY_CODE"
//					+ " AND EO.ORGANIZATION_ID *= EC.ORGANIZATION_ID"
//					+ " AND EO.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
//					+ " AND EO.OBJECT_CATEGORY = EFV.CODE"
//					+ " AND EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID"
//					// + " AND SUBSTRING(EC.COMPANY_CODE, 0, 2) <> 80"
//					+ " AND EFVS.CODE = ?" + " AND EO.ORGANIZATION_ID = ?"
//					// + " AND (EO.OBJECT_CATEGORY < 70 OR EO.OBJECT_CATEGORY =
//					// 80)"
//					// + " AND (EO.DISABLE_DATE " +
//					// SyBaseSQLUtil.isNullNoParam() + " OR EO.DISABLE_DATE > "
//					// + SyBaseSQLUtil.getCurDate() + ")"
//					+ "";
//			sqlArgs.add(AssetsDictConstant.OBJECT_CATEGORY);
//			sqlArgs.add(dto.getOrganizationId());
//			if (contentBlurred.equals("N")) {
//				sqlStr += " AND ( " + SyBaseSQLUtil.isNull()
//						+ "  OR EC.COUNTY_NAME NOT LIKE ?)" + " AND ( "
//						+ SyBaseSQLUtil.isNull()
//						+ "  OR EO.WORKORDER_OBJECT_CODE NOT LIKE ?)"
//						+ " AND ( " + SyBaseSQLUtil.isNull()
//						+ "  OR EO.WORKORDER_OBJECT_NAME NOT LIKE ?)";
//				sqlArgs.add(dto.getCountyName());
//				sqlArgs.add(dto.getCountyName());
//				sqlArgs.add(dto.getWorkorderObjectCode());
//				sqlArgs.add(dto.getWorkorderObjectCode());
//				sqlArgs.add(dto.getWorkorderObjectName());
//				sqlArgs.add(dto.getWorkorderObjectName());
//			} else {
//				sqlStr += " AND ( " + SyBaseSQLUtil.isNull()
//						+ "  OR EC.COUNTY_NAME LIKE ?)" + " AND ( "
//						+ SyBaseSQLUtil.isNull()
//						+ "  OR EO.WORKORDER_OBJECT_CODE LIKE ?)" + " AND ( "
//						+ SyBaseSQLUtil.isNull()
//						+ "  OR EO.WORKORDER_OBJECT_NAME LIKE ?)";
//				sqlArgs.add(dto.getCountyName());
//				sqlArgs.add(dto.getCountyName());
//				sqlArgs.add(dto.getWorkorderObjectCode());
//				sqlArgs.add(dto.getWorkorderObjectCode());
//				sqlArgs.add(dto.getWorkorderObjectName());
//				sqlArgs.add(dto.getWorkorderObjectName());
//			}

            EtsObjectDTO dto = (EtsObjectDTO) dtoParameter;
            String contentBlurred = dto.getContentBlurred();
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
					 + " AND EO.ORGANIZATION_ID *= EC.ORGANIZATION_ID"
					 + " AND EO.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
					 + " AND EO.OBJECT_CATEGORY = EFV.CODE"
					 + " AND EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID"
                     + " AND EFVS.CODE = ?"
					 + " AND EO.ORGANIZATION_ID = ?"
					 + " AND (EO.DISABLE_DATE "+SyBaseSQLUtil.isNullNoParam()+" OR EO.DISABLE_DATE > GETDATE() OR EO.DISABLE_DATE IS NULL)" +
                    "    AND NOT EXISTS\n" +
                    " (SELECT 'A'\n" +
                    "          FROM AMS_ASSETS_CHECK_HEADER AACH\n" +
                    "         WHERE AACH.CHECK_LOCATION = EO.WORKORDER_OBJECT_NO\n" +
                    "           AND AACH.ORDER_STATUS NOT IN ('CANCELED', 'ARCHIEVED')" +
                    "           AND AACH.CHECK_CATEGORY ='')";
           sqlArgs.add(AssetsDictConstant.OBJECT_CATEGORY);
		   sqlArgs.add(dto.getOrganizationId());
//             if (contentBlurred.equals("N")) {
//                  sqlStr += "AND NOT EXISTS(SELECT 'A'\n" +
//                          "                  FROM AMS_ASSETS_CHECK_HEADER AACH1\n" +
//                          "                 WHERE AACH1.CHECK_LOCATION = EO.WORKORDER_OBJECT_NO\n" +
//                          "                   AND AACH1.ORDER_STATUS  = 'ARCHIEVED')";
//             }else{
//                sqlStr += "AND  EXISTS(SELECT 'A'\n" +
//                        "                  FROM AMS_ASSETS_CHECK_HEADER AACH1\n" +
//                        "                 WHERE AACH1.CHECK_LOCATION = EO.WORKORDER_OBJECT_NO\n" +
//                        "                   AND AACH1.ORDER_STATUS  = 'ARCHIEVED')";
//             }
            sqlStr +=    //" AND (? ='' OR EC.COUNTY_NAME LIKE ?)"
					     " AND (? ='' OR EO.WORKORDER_OBJECT_CODE LIKE ?)"
					   + " AND (? ='' OR EO.WORKORDER_OBJECT_NAME LIKE ?)"
            		   + " AND (? ='' OR EO.COST_CODE = ?)";
            sqlStr+= " AND NOT EXISTS(SELECT 'A'\n" +
                          "                  FROM AMS_ASSETS_CHECK_HEADER AACH1\n" +
                          "                 WHERE AACH1.CHECK_LOCATION = EO.WORKORDER_OBJECT_NO\n" +
                          "                   AND (AACH1.ORDER_STATUS  = 'DOWNLOADED' OR AACH1.ORDER_STATUS  = 'UPLOADED')" +
                          "                   AND AACH1.CHECK_CATEGORY ='')";
               //sqlArgs.add(dto.getCountyName());
               //sqlArgs.add(dto.getCountyName());
			   sqlArgs.add(dto.getWorkorderObjectCode());
			   sqlArgs.add(dto.getWorkorderObjectCode());
			   sqlArgs.add(dto.getWorkorderObjectName());
			   sqlArgs.add(dto.getWorkorderObjectName());
               sqlArgs.add(dto.getCountyName());
               sqlArgs.add(dto.getCountyName());
			   //sqlArgs.add(dto.getCostCenterCode());
			   //sqlArgs.add(dto.getCostCenterCode());
		} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_FACAT_CODE)) { // 选择资产类别
			AmsFaCategoryVDTO dto = (AmsFaCategoryVDTO) dtoParameter;
			sqlStr = "SELECT"
					+ " *"
					+ " FROM"
					+ " AMS_CONTENT_DIC AFCV"
					+ " WHERE"
					+ " (?='' OR AFCV.FA_CAT_NAME_1 LIKE ?)"
					+ " AND (?='' OR AFCV.FA_CAT_NAME_1 LIKE ?)"
					+ " AND  (?='' OR AFCV.FA_CAT_NAME_1 LIKE ?)";
			sqlArgs.add(dto.getFaCatName1());
			sqlArgs.add(dto.getFaCatName1());
			sqlArgs.add(dto.getFaCatName2());
			sqlArgs.add(dto.getFaCatName2());
			sqlArgs.add(dto.getFaCatName3());
			sqlArgs.add(dto.getFaCatName3());
		} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_ACCOUNT)) { // 选择折旧账户
			AmsAccountVDTO dto = (AmsAccountVDTO) dtoParameter;
			sqlStr = "SELECT"
					+ " AAV.*"
					+ " FROM"
					+ " AMS_ACCOUNT_V        AAV,"
					+ " ETS_OU_CITY_MAP      EOCM"
					+ " WHERE"
					+ " AAV.ACCOUNT_CODE_1 = EOCM.COMPANY_CODE"
					+ " AND (AAV.ACCOUNT_CODE_2 LIKE dbo.NVL(?, AAV.ACCOUNT_CODE_2)"
					+ " OR AAV.ACCOUNT_NAME_2 LIKE dbo.NVL(?, AAV.ACCOUNT_NAME_2))"
					+ " AND (AAV.ACCOUNT_NAME_3 LIKE dbo.NVL(?, AAV.ACCOUNT_NAME_3)"
					+ " OR AAV.ACCOUNT_CODE_3 LIKE dbo.NVL(?, AAV.ACCOUNT_CODE_3))"
					+ " AND AAV.ACCOUNT_TYPE = 'E'" // 表示是费用账户(高朗意见)
					+ " AND AAV.ACCOUNT_CODE_4 = '000000'"
					+ " AND AAV.ACCOUNT_CODE_5 = '00000000'"
					+ " AND AAV.ACCOUNT_CODE_6 = '0000'"
					+ " AND AAV.ACCOUNT_CODE_7 = '000000'"
					+ " AND EOCM.ORGANIZATION_ID = ?"; // 徐培政建议增加条件
			sqlArgs.add(dto.getAccountName2());
			sqlArgs.add(dto.getAccountName2());
			sqlArgs.add(dto.getAccountName3());
			sqlArgs.add(dto.getAccountName3());
			sqlArgs.add(dto.getOrganizationId());
		} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_VENDOR)) {
			AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
			sqlStr = "SELECT" + " EMPV.VENDOR_ID," + " EMPV.VENDOR_NAME,"
					+ " EMPV.SEGMENT1 VENDOR_NUMBER," + " EMPV.VENDOR_NAME_ALT"
					+ " FROM" + " ETS_MIS_PO_VENDORS EMPV" + " WHERE"
					+ " ( ? = '' OR EMPV.VENDOR_NAME LIKE ? ) "
					+ " AND ( ? = '' OR  EMPV.SEGMENT1 LIKE ? )";
			sqlArgs.add( StrUtil.nullToString( dto.getVendorName() ) );
			sqlArgs.add( StrUtil.nullToString( dto.getVendorName() ) );
			sqlArgs.add( StrUtil.nullToString(  dto.getVendorNumber() ) );
			sqlArgs.add( StrUtil.nullToString(  dto.getVendorNumber() ) );
		} else if(lookUpName.equals("PROJECT_NO")){
			EtsPaProjectsAllDTO dto = (EtsPaProjectsAllDTO) super.dtoParameter;
			sqlStr = "SELECT EPPA.PROJECT_ID,"
				+ " EPPA.MIS_PROJECT_ID,"         
				+ " EPPA.NAME PROJECT_NAME,"
				+ " EPPA.SEGMENT1 PROJECT_NUMBER,"
				+ " EPPA.PROJECT_TYPE "
				+ " FROM ETS_PA_PROJECTS_ALL EPPA"
				+ " WHERE ORGANIZATION_ID IN" 
				+ " (SELECT ORGANIZATION_ID FROM ETS_OU_CITY_MAP WHERE BOOK_TYPE_CODE=?)";
			sqlStr+= "AND EPPA.MIS_PROJECT_ID LIKE dbo.NVL(?,EPPA.MIS_PROJECT_ID)"
				+ " AND EPPA.SEGMENT1 LIKE dbo.NVL(?, EPPA.SEGMENT1)"
				+ " AND EPPA.PROJECT_TYPE LIKE dbo.NVL(?, EPPA.PROJECT_TYPE)"
				+ " AND EPPA.NAME LIKE dbo.NVL(?, EPPA.NAME) ORDER BY EPPA.PROJECT_ID";
			sqlArgs.add(dto.getBookTypeCode());
			if(dto.getMisProjectId()<=0){
				sqlArgs.add("");
			}else{
				sqlArgs.add(dto.getMisProjectId()+"");
			}
			sqlArgs.add(dto.getProjectNumber());
			sqlArgs.add(dto.getProjectType());
			sqlArgs.add(dto.getName());
		} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_PROJECT)) {
			EtsPaProjectsAllDTO dto = (EtsPaProjectsAllDTO) super.dtoParameter;
			sqlStr = "SELECT" + " EPPA.PROJECT_ID,"
					+ " EPPA.NAME PROJECT_NAME,"
					+ " EPPA.SEGMENT1 PROJECT_NUMBER," + " EPPA.PROJECT_TYPE "
					+ " FROM" + " ETS_PA_PROJECTS_ALL EPPA" + " WHERE"
					+ " EPPA.SEGMENT1 LIKE dbo.NVL(?, EPPA.SEGMENT1)" ;
                       if(user.getIsTd().equals("Y")){
                  sqlStr+= "  AND  EPPA.SOURCE LIKE '%TD%'";
            } else if(user.getIsTt().equals("Y")){
              sqlStr+= "  AND  EPPA.SOURCE LIKE '%TD%'";
            }else{
               sqlStr+= "  AND  EPPA.SOURCE='MIS'";
            }
					 sqlStr+= " AND EPPA.PROJECT_TYPE LIKE dbo.NVL(?, EPPA.PROJECT_TYPE)"
					+ " AND EPPA.NAME LIKE dbo.NVL(?, EPPA.NAME) ORDER BY EPPA.PROJECT_ID";
			sqlArgs.add(dto.getProjectNumber());
			sqlArgs.add(dto.getProjectType());
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
					+ " AND ACCV.COST_CENTER_NAME LIKE dbo.NVL(?, ACCV.COST_CENTER_NAME)"  
					;
			 
			sqlArgs.add(dto.getCostCode());
			sqlArgs.add(dto.getCostName());
			
			if( !StrUtil.isEmpty( dto.getCompanyCode() ) ){
				sqlStr += " AND ACCV.ORGANIZATION_ID IN (" + dto.getCompanyCode() + ")";
			}else{
				sqlStr += " AND ( ? IS NULL OR ? <=0 OR ACCV.ORGANIZATION_ID = ?)";
				sqlArgs.add(dto.getOrganizationId());
				sqlArgs.add(dto.getOrganizationId());
				sqlArgs.add(dto.getOrganizationId());
			}
				
		} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_ITEMNAME)) {
			EtsSystemItemDTO dto = (EtsSystemItemDTO) super.dtoParameter;
			sqlStr = "SELECT ESI.ITEM_NAME,\n" + "       ESI.ITEM_SPEC,\n"
					+ "       EFV.VALUE\n"
					+ "FROM   ETS_SYSTEM_ITEM        ESI,\n"
					+ "       ETS_SYSITEM_DISTRIBUTE ESD,\n"
					+ "       ETS_FLEX_VALUES        EFV\n"
					+ "WHERE  ESI.ITEM_CODE = ESD.ITEM_CODE\n"
					+ "AND    EFV.FLEX_VALUE_SET_ID = '1' \n"
					+ "AND    EFV.CODE = ESI.ITEM_CATEGORY"
					+ " AND ESD.ORGANIZATION_ID = ?"
					+ " AND (? = '' OR ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME) )"
					+ " AND (? = '' OR ESI.ITEM_SPEC LIKE dbo.NVL(?, ESI.ITEM_SPEC) )";
			sqlArgs.add(user.getOrganizationId());
			sqlArgs.add(dto.getItemName());
			sqlArgs.add(dto.getItemName());
			sqlArgs.add(dto.getItemSpec());
			sqlArgs.add(dto.getItemSpec());
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
		} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_TDLOC)) {
			AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
			sqlStr = "SELECT"
					+ " EFAL.ASSETS_LOCATION_CODE,"
					+ " EFAL.ASSETS_LOCATION"
					+ " FROM"
					+ " ETS_FA_ASSETS_LOCATION_TD EFAL"
					+ " WHERE"
					+ " EFAL.ASSETS_LOCATION LIKE dbo.NVL(?, EFAL.ASSETS_LOCATION)"
					+ " AND EFAL.ASSETS_LOCATION_CODE LIKE dbo.NVL(?, EFAL.ASSETS_LOCATION_CODE)"
					+ " AND EFAL.BOOK_TYPE_CODE = ?";
			sqlArgs.add(dto.getAssetsLocation());
			sqlArgs.add(dto.getAssetsLocationCode());
			sqlArgs.add(user.getBookTypeCode());
		} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_LNE)) {
			AmsLneDTO dto = (AmsLneDTO) dtoParameter;
			sqlStr = "SELECT" + " AL.AMS_LNE_ID," + " AL.NET_CATEGORY1,"
					+ " AL.NET_CATEGORY2," + " AL.NET_UNIT_CODE,"
					+ " AL.LOG_NET_ELE," + " AL.ENG_AB" + " FROM"
					+ " AMS_LNE AL" + " WHERE"
					+ " AL.NET_CATEGORY1 LIKE dbo.NVL(?, NET_CATEGORY1)"
					+ " AND AL.NET_CATEGORY2 LIKE dbo.NVL(?, NET_CATEGORY2)"
					+ " AND AL.NET_UNIT_CODE LIKE dbo.NVL(?, NET_UNIT_CODE)"
					+ " AND AL.LOG_NET_ELE LIKE dbo.NVL(?, LOG_NET_ELE)";
			sqlArgs.add(dto.getNetCategory1());
			sqlArgs.add(dto.getNetCategory2());
			sqlArgs.add(dto.getNetUnitCode());
			sqlArgs.add(dto.getLogNetEle());
		} else if (lookUpName
				.equals(AssetsLookUpConstant.LOOK_UP_LNE_NOMATCH_AMS)) {
			AmsLneDTO dto = (AmsLneDTO) dtoParameter;
			sqlStr = "SELECT" + " AL.AMS_LNE_ID," + " AL.NET_CATEGORY1,"
					+ " AL.NET_CATEGORY2," + " AL.NET_UNIT_CODE,"
					+ " AL.LOG_NET_ELE," + " AL.ENG_AB" + " FROM"
					+ " AMS_LNE AL" + " WHERE"
					+ " AL.NET_CATEGORY1 LIKE dbo.NVL(?, NET_CATEGORY1)"
					+ " AND AL.NET_CATEGORY2 LIKE dbo.NVL(?, NET_CATEGORY2)"
					+ " AND AL.NET_UNIT_CODE LIKE dbo.NVL(?, NET_UNIT_CODE)"
					+ " AND AL.LOG_NET_ELE LIKE dbo.NVL(?, LOG_NET_ELE)"
					+ " AND NOT EXISTS" + " 	(SELECT NULL"
					+ " 		FROM AMS_LNE_CONTENT ALC"
					+ " 		WHERE ALC.LNE_CODE = AL.NET_UNIT_CODE)"
					+ " ORDER BY AL.AMS_LNE_ID";
			sqlArgs.add(dto.getNetCategory1());
			sqlArgs.add(dto.getNetCategory2());
			sqlArgs.add(dto.getNetUnitCode());
			sqlArgs.add(dto.getLogNetEle());
		} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_CEX)) {
			AmsCexDTO dto = (AmsCexDTO) dtoParameter;
			sqlStr = "SELECT AC.AMS_CEX_ID,\n"
					+ "       AC.INVEST_CATEGORY1,\n"
					+ "       AC.INVEST_CATEGORY2,\n"
					+ "       AC.INVEST_CAT_CODE,\n"
					+ "       AC.INVEST_CAT_NAME\n"
					+ "  FROM AMS_CEX AC\n"
					+ " WHERE AC.INVEST_CATEGORY1 LIKE dbo.NVL(?, AC.INVEST_CATEGORY1)\n"
					+ "   AND AC.INVEST_CATEGORY2 LIKE dbo.NVL(?, AC.INVEST_CATEGORY2)\n"
					+ "   AND AC.INVEST_CAT_CODE LIKE dbo.NVL(?, AC.INVEST_CAT_CODE)\n"
					+ "   AND AC.INVEST_CAT_NAME LIKE dbo.NVL(?, AC.INVEST_CAT_NAME)";
			sqlArgs.add(dto.getInvestCategory1());
			sqlArgs.add(dto.getInvestCategory2());
			sqlArgs.add(dto.getInvestCatCode());
			sqlArgs.add(dto.getInvestCatName());
		} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_OPE)) {
			AmsOpeDTO dto = (AmsOpeDTO) dtoParameter;
			sqlStr = "SELECT" + " AMS_OPE_ID," + " OPE_CODE," + " OPE_NAME"
					+ " FROM" + " AMS_OPE AO" + " WHERE"
					+ " AO.OPE_CODE LIKE dbo.NVL(?, OPE_CODE)"
					+ " AND AO.OPE_NAME LIKE dbo.NVL(?, OPE_NAME)";
			sqlArgs.add(dto.getOpeCode());
			sqlArgs.add(dto.getOpeName());
		} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_NLE)) {
			AmsNleDTO dto = (AmsNleDTO) dtoParameter;
			sqlStr = "SELECT" + " AMS_LNE_ID," + " LNE_CODE," + " LNE_NAME"
					+ " FROM" + " AMS_NLE"
					+ " WHERE LNE_CODE LIKE dbo.NVL(?, LNE_CODE)\n"
					+ " AND LNE_NAME LIKE dbo.NVL(?, LNE_NAME)";
			sqlArgs.add(dto.getLneCode());
			sqlArgs.add(dto.getLneName());
		} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_SN)) {
			AmsSnDTO dto = (AmsSnDTO) dtoParameter;
			sqlStr = "SELECT SN_ID,\n" + "       SN_CODE,\n"
					+ "       SN_NAME\n" + "  FROM AMS_SN\n"
					+ " WHERE SN_CODE LIKE dbo.NVL(?, SN_CODE)\n"
					+ "   AND SN_NAME LIKE dbo.NVL(?, SN_NAME)";
			sqlArgs.add(dto.getSnCode());
			sqlArgs.add(dto.getSnName());
		} else if (lookUpName
				.equals(AssetsLookUpConstant.LOOK_UP_CEX_NOMATCH_AMS)) {
			AmsCexDTO dto = (AmsCexDTO) dtoParameter;
			sqlStr = "SELECT" + " AMS_CEX_ID," + " INVEST_CATEGORY1,"
					+ " INVEST_CATEGORY2," + " INVEST_CAT_CODE,"
					+ " INVEST_CAT_NAME" + " FROM" + " AMS_CEX AC" + " WHERE"
					+ " INVEST_CATEGORY1 LIKE dbo.NVL(?, INVEST_CATEGORY1)"
					+ " AND INVEST_CATEGORY2 LIKE dbo.NVL(?, INVEST_CATEGORY2)"
					+ " AND INVEST_CAT_CODE LIKE dbo.NVL(?, INVEST_CAT_CODE)"
					+ " AND INVEST_CAT_NAME LIKE dbo.NVL(?, INVEST_CAT_NAME)"
					+ " AND NOT EXISTS" + " 	(SELECT NULL"
					+ " 		FROM AMS_CEX_CONTENT ACC"
					+ " 		WHERE ACC.INVEST_CAT_CODE = AC.INVEST_CAT_CODE)"
					+ " ORDER BY AC.AMS_CEX_ID";
			sqlArgs.add(dto.getInvestCategory1());
			sqlArgs.add(dto.getInvestCategory2());
			sqlArgs.add(dto.getInvestCatCode());
			sqlArgs.add(dto.getInvestCatName());
		} else if (lookUpName
				.equals(AssetsLookUpConstant.LOOK_UP_OPE_NOMATCH_AMS)) {
			AmsOpeDTO dto = (AmsOpeDTO) dtoParameter;
			sqlStr = "SELECT" + " AMS_OPE_ID," + " OPE_CODE," + " OPE_NAME"
					+ " FROM" + " AMS_OPE AO" + " WHERE"
					+ " AO.OPE_CODE LIKE dbo.NVL(?, OPE_CODE)"
					+ " AND AO.OPE_NAME LIKE dbo.NVL(?, OPE_NAME)"
					+ " AND NOT EXISTS" + " 	(SELECT NULL"
					+ " 		FROM AMS_OPE_CONTENT AOC"
					+ " 		WHERE AOC.OPE_CODE = AO.OPE_CODE)"
					+ " ORDER BY AO.AMS_OPE_ID";
			sqlArgs.add(dto.getOpeCode());
			sqlArgs.add(dto.getOpeName());
		} 
		else if (lookUpName
				.equals(AssetsLookUpConstant.LOOK_ZEROTURN_ASSETS)) {//查询生成工单资产【待确认详细需求】
			ZeroTurnLineDTO dto = (ZeroTurnLineDTO) dtoParameter;//RECORD ==BARCODE
			sqlStr=
		   		" SELECT  AMD.RECORD,AMD.COMPANY_CODE,AMD.BARCODE,AMD.CONTENT_CODE,AMD.CONTENT_NAME,AMD.ASSETS_DESCRIPTION,AMD.ITEM_SPEC,AMD.ITEM_QTY,AMD.UNIT_OF_MEASURE,AMD.MANUFACTURER_NAME,AMD.OBJECT_NO,AMD.WORKORDER_OBJECT_NAME,AMD.RESPONSIBILITY_DEPT,AMD.RESPONSIBILITY_USER,AMD.SPECIALITY_DEPT,AMD.START_DATE,AMD.YEARS,AMD.PRICE,AMD.PROCURE_CODE,AMD.COST_CENTER_CODE,AMD.IS_SHARE,AMD.IS_BULID,AMD.LNE_ID,AMD.OPE_ID,AMD.CEX_ID,AMD.NLE_ID,AMD.REMARK,AMD.CREATED_BY,AMD.CREATION_DATE,AMD.EXPECTED_DATE \n" +
		   		" FROM ETS_ITEM_TURN  AMD, \n" +
		   		" ETS_ITEM_INFO EII \n"+
				" WHERE  AMD.COST_CENTER_CODE LIKE dbo.NVL(?, AMD.COST_CENTER_CODE) \n"+
				" AND    AMD.PROCURE_CODE LIKE dbo.NVL(?, AMD.PROCURE_CODE) \n" +
				" AND    AMD.BARCODE=EII.BARCODE \n" +
				" AND    EII.PROJECTID = ? \n" +
				" AND    EII.ITEM_STATUS='PRE_ASSETS' \n" +
				" AND    EII.BARCODE NOT IN (SELECT RECORD FROM ZERO_TURN_ADD_L WHERE TRANS_STATUS NOT IN('TO_END','CANCELED'))\n"+
				" AND    EII.FINANCE_PROP='PRJ_MTL' \n" +
			    " AND    NOT EXISTS" + " 	(SELECT NULL"+
				"              FROM ETS_WORKORDER EW,\n" +
				"                   ETS_WORKORDER_DTL EWDL \n" +//xiugaile 
				"        WHERE    EW.WORKORDER_NO=EWDL.WORKORDER_NO \n" +
				"        AND      EW.ARCHFLAG=0 \n" +
				"        AND      EWDL.BARCODE=EII.BARCODE )\n";
				
			sqlArgs.add(dto.getCostCenterCode());
			sqlArgs.add(dto.getProcureCode());
			sqlArgs.add(DictConstant.ZERO_PRJ_ID);
		}
		else if (lookUpName
				.equals(AssetsLookUpConstant.LOOK_UP_NLE_NOMATCH_AMS)) {
			AmsNleDTO dto = (AmsNleDTO) dtoParameter;
			sqlStr = "SELECT" + " AN.AMS_LNE_ID," + " AN.LNE_CODE,"
					+ " AN.LNE_NAME" + " FROM" + " AMS_NLE AN"
					+ " WHERE AN.LNE_CODE LIKE dbo.NVL(?, AN.LNE_CODE)"
					+ "   AND AN.LNE_NAME LIKE dbo.NVL(?, AN.LNE_NAME)"
					+ " AND NOT EXISTS" + " 	(SELECT NULL"
					+ " 		FROM AMS_NLE_CONTENT ANC"
					+ " 		WHERE ANC.LNE_CODE = AN.LNE_CODE)"
					+ " ORDER BY AN.AMS_LNE_ID";
			sqlArgs.add(dto.getLneCode());
			sqlArgs.add(dto.getLneName());
		}

		else if (lookUpName.equals(AssetsLookUpConstant.LOOK_TRANSFER_ASSETS_OTHER)) { // 查询选择其他实物资产
			AmsAssetsTransLineDTO dto = (AmsAssetsTransLineDTO) dtoParameter;
			StringBuilder sb = new StringBuilder();
			sb.append( " SELECT \n ");
			sb.append( "  EII.BARCODE,         -- 标签 \n "); 
			sb.append( "  ESI.ITEM_NAME ASSETS_DESCRIPTION,  \n "); //名称
			sb.append( "  ESI.ITEM_SPEC MODEL_NUMBER,        \n ");  //规格型号 
			sb.append( "  EII.ITEM_QTY, "  ); 
			sb.append( "  EII.UNIT_OF_MEASURE, \n "); 
			sb.append( "  EII.START_DATE DATE_PLACED_IN_SERVICE, \n "); 
			sb.append( "  EII.ASSET_ID,  \n ");
			sb.append( "  EO.WORKORDER_OBJECT_CODE OLD_LOCATION_CODE, \n "); 
		    sb.append( "  EO.WORKORDER_OBJECT_LOCATION OLD_LOCATION_NAME,   \n ");  
			sb.append( "  EII.RESPONSIBILITY_DEPT OLD_RESPONSIBILITY_DEPT, \n "); 
			sb.append( "  dbo.APP_GET_DEPT_NAME( EII.RESPONSIBILITY_DEPT ) OLD_RESPONSIBILITY_DEPT_NAME, \n "); 
			sb.append( "  EII.RESPONSIBILITY_USER OLD_RESPONSIBILITY_USER,     \n "); 
			sb.append( "  AM.MANUFACTURER_NAME ,    \n "); 
			sb.append( "  AME.USER_NAME OLD_RESPONSIBILITY_USER_NAME  \n "); 
			sb.append( " FROM  ETS_ITEM_INFO  EII,  \n "); 
			sb.append( "        ETS_OBJECT          EO,  \n "); 
			sb.append( "        ETS_SYSTEM_ITEM     ESI,  \n "); 
			sb.append( "        AMS_OBJECT_ADDRESS  AOA, \n "); 
			sb.append( "        AMS_MANUFACTURER    AM, \n ");
			sb.append( "        AMS_MIS_EMPLOYEE AME  \n "); 
			sb.append( " WHERE  EII.ITEM_CODE = ESI.ITEM_CODE   \n "); 
			sb.append( "        AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID   \n "); 
//			sb.append( "        AND EII.FINANCE_PROP = 'SPARE' \n ");
			// 2011-09-02 不要关联厂商，厂商外连接
			sb.append( "        AND EII.MANUFACTURER_ID *= AM.MANUFACTURER_ID  \n ");
			sb.append( "        AND EII.ADDRESS_ID = AOA.ADDRESS_ID \n "); 
			
			sb.append( "        AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO   \n "); 
			sb.append( "        AND EII.ORGANIZATION_ID = ?  \n "); 
			sb.append( "        AND (EII.ITEM_STATUS = ''  \n "); 
			sb.append( "        OR EII.ITEM_STATUS = ? OR EII.ITEM_STATUS = ?)\n "); 
			sb.append( "        AND EII.FINANCE_PROP = ? \n ");

			sqlArgs.add(user.getOrganizationId());
			sqlArgs.add(AssetsDictConstant.ITEM_STATUS_NORMAL);
			sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_FREED);
			sqlArgs.add(dto.getFaContentCode());
			
			if (!StrUtil.isEmpty(dto.getDeptCode())) { // 部门限制
				sb.append( " AND EII.RESPONSIBILITY_DEPT = ? \n ");
				sqlArgs.add(dto.getDeptCode());
			}

			// TODO SJ 暂时屏蔽权限
			// else { //根据操作人员权限限制
			// if (!isCompMgr && mtlMgrProps.equals("") && isDeptMgr) {
			// DTOSet depts = user.getPriviDeptCodes();
			// AmsMisDeptDTO dept = null;
			// String deptCodes = "(";
			// for (int i = 0; i < depts.getSize(); i++) {
			// dept = (AmsMisDeptDTO) depts.getDTO(i);
			// deptCodes += "'" + dept.getDeptCode() + "', ";
			// }
			// deptCodes += "'')";
			// sqlStr += " AND AAAV.DEPT_CODE IN " + deptCodes;
			// }
			// }
			// if (!user.isDptAssetsManager() && !user.isComAssetsManager() &&
			// mtlMgrProps.equals("")) { //责任人限制
			// sqlStr += " AND AAAV.RESPONSIBILITY_USER = ?";
			// sqlArgs.add(user.getEmployeeId());
			// } 
			

			sb.append( "   AND ( ");
			sb.append(      SyBaseSQLUtil.nullStringParam() );
			sb.append( "    OR ESI.ITEM_NAME LIKE ? )\n ");
			sb.append( "   AND ( ");
			sb.append(      SyBaseSQLUtil.nullStringParam() );
			sb.append( "    OR EII.BARCODE LIKE ? ) \n ");
			sb.append( "   AND ( \n ");
			sb.append(      SyBaseSQLUtil.nullStringParam() );
			sb.append( "    OR EO.WORKORDER_OBJECT_CODE LIKE ?  \n ");
			sb.append( "    OR EO.WORKORDER_OBJECT_NAME LIKE ? )\n "); 
			sb.append( "  AND NOT EXISTS (\n ");
			sb.append( "  SELECT \n ");
			sb.append( "  NULL \n ");
			sb.append( "  FROM \n ");
			sb.append( "  AMS_ASSETS_RESERVED AAR \n ");
			sb.append( "  WHERE \n ");
			sb.append( "  AAR.BARCODE = EII.BARCODE) \n ");
			
			//TODO 在途
			sb.append( "  AND ( CONVERT( INT , EO.OBJECT_CATEGORY ) <= 73 OR CONVERT( INT , EO.OBJECT_CATEGORY ) >= 75 ) \n ");

			
			SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getAssetsDescription());
			SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getBarcode());
			SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getOldLocationCode());
			sqlArgs.add(dto.getOldLocationCode());
			
			sqlStr = sb.toString();
		} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_TRANSFER_ITEM)) { // 查询选择调拨资产
			AmsAssetsTransLineDTO dto = (AmsAssetsTransLineDTO) dtoParameter;
			String transferType = dto.getTransferType();
			boolean isDeptMgr = user.isDptAssetsManager();
			boolean isCompMgr = user.isComAssetsManager();
			String mtlMgrProps = user.getMtlMgrProps();
            String transType = dto.getTransType();

			sqlStr = "SELECT EII.BARCODE,\n"
					+ "       '' ASSET_NUMBER,\n"
					+ "       '' ASSET_ID,\n"
					+ "       ESI.ITEM_NAME ASSETS_DESCRIPTION,\n"
					+ "       ESI.ITEM_SPEC MODEL_NUMBER,\n"
					+ "       ESI.ITEM_NAME,\n"
					+ "       ESI.ITEM_SPEC,\n"
					+ "       '' VENDOR_NAME,\n"
					+ "       ESI.ITEM_UNIT UNIT_OF_MEASURE,\n"
					+ "       ISNULL(EII.ITEM_QTY, 1) CURRENT_UNITS,\n"
					+ "       AM.MANUFACTURER_NAME,\n"
					+ "       EII.CONTENT_CODE OLD_FA_CATEGORY_CODE,\n"
					+ "       AMD.DEPT_CODE OLD_RESPONSIBILITY_DEPT,\n"
					+ "       AMD.DEPT_NAME OLD_RESPONSIBILITY_DEPT_NAME,\n"
					+ "       EO.WORKORDER_OBJECT_NO OLD_LOCATION,\n"
					+ "       EO.WORKORDER_OBJECT_CODE OLD_LOCATION_CODE,\n"
					+ "       EO.WORKORDER_OBJECT_NAME OLD_LOCATION_NAME,\n"
					+ "       EII.RESPONSIBILITY_USER OLD_RESPONSIBILITY_USER,\n"
					+ "       AME.USER_NAME OLD_RESPONSIBILITY_USER_NAME,\n"
					+ "       \n"
					+ "       EO.ORGANIZATION_ID TO_ORGANIZATION_ID,\n"
					+ "       EII.START_DATE DATE_PLACED_IN_SERVICE,\n"
					+ "       EII.PRICE COST,\n"
					+ "       '' DEPRN_COST,\n"
					+ "       '' RETIREMENT_COST,\n"
					+ "       '' OLD_DEPRECIATION_ACCOUNT,\n"
					+ "       '' DEPRECIATION,\n"
					+ "       '' ASSETS_STATUS_DES,\n"
					+ "       '' IMPAIR_RESERVE\n"
					+ "  FROM \n"
					+ "       AMS_MANUFACTURER   AM,\n"
					+ "       ETS_OU_CITY_MAP    EOCM,\n"
					+ "       ETS_OBJECT         EO,\n"
					+ "       AMS_OBJECT_ADDRESS AOA,\n"
					+ "       AMS_MIS_EMPLOYEE   AME,\n"
					+ "       AMS_MIS_DEPT       AMD,\n"
					+ "       ETS_SYSTEM_ITEM    ESI,\n"
					+ "       ETS_ITEM_INFO      EII\n"
					+ " WHERE EII.ITEM_CODE = ESI.ITEM_CODE\n";
			if( !StrUtil.isEmpty( dto.getOldResponsibilityUserName() ) ){
				sqlStr +=  "   AND EII.RESPONSIBILITY_USER = AME.EMPLOYEE_ID\n";
			}else{
				sqlStr +=  "   AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID\n";
			}
			
			sqlStr +=  "   AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE\n"
					+ "   AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n"
					+ "   AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n"
					+ "   AND EO.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n"
					+ "   AND EII.MANUFACTURER_ID *= AM.MANUFACTURER_ID\n"
					+ "   AND (EII.DISABLE_DATE = NULL OR EII.DISABLE_DATE = '')\n"
					+ "   AND EII.FINANCE_PROP IN\n"
					+ "       ('DG_ASSETS', 'DH_ASSETS', 'QD_ASSETS', 'RENT_ASSETS', 'SPARE')\n"
					+ "   AND EOCM.IS_TD = 'N'\n"
					+ "   AND EII.ORGANIZATION_ID = ?";

			sqlArgs.add(user.getOrganizationId());
			if (!StrUtil.isEmpty(dto.getDeptCode())) { // 部门限制
				sqlStr += " AND EII.RESPONSIBILITY_DEPT = ?";
				sqlArgs.add(dto.getDeptCode());
			} else { // 根据操作人员权限限制
				if (!isCompMgr && mtlMgrProps.equals("") ) {
                    if(isDeptMgr){
                        DTOSet depts = user.getPriviDeptCodes();
                        AmsMisDeptDTO dept = null;
                        String deptCodes = "(";
                        for (int i = 0; i < depts.getSize(); i++) {
                            dept = (AmsMisDeptDTO) depts.getDTO(i);
                            deptCodes += "'" + dept.getDeptCode() + "', ";
                        }
                        deptCodes += "'')";
                        sqlStr += " AND EII.RESPONSIBILITY_DEPT IN " + deptCodes;
                    } else {
                        sqlStr += " AND EII.RESPONSIBILITY_USER = ?";
                        sqlArgs.add(user.getEmployeeId());
                    }
                }
			}
			sqlStr += "  AND  EII.FINANCE_PROP =? \n"
					+ "   AND ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME)\n"
					+ "   AND EII.BARCODE LIKE dbo.NVL(?, EII.BARCODE)\n"
//					+ "   AND AME.USER_NAME LIKE dbo.NVL(?, AME.USER_NAME)\n"
					+ "   AND ((?='' OR EO.WORKORDER_OBJECT_CODE LIKE ?) OR\n" +
                    "       (? ='' OR EO.WORKORDER_OBJECT_NAME LIKE ?))"
					+ "   AND NOT EXISTS (SELECT NULL\n"
					+ "          FROM AMS_ASSETS_RESERVED AAR\n"
					+ "         WHERE AAR.BARCODE = EII.BARCODE)\n";
			
			if( transferType.equals( "INN_DEPT" ) || transferType.equals( "BTW_DEPT" )  ){
//				sqlStr += " AND EO.OBJECT_CATEGORY <> '74' ";
				sqlStr += " AND ( CONVERT( INT , EO.OBJECT_CATEGORY ) <= 73 OR CONVERT( INT , EO.OBJECT_CATEGORY ) >= 75 )\n" ;
			}

			sqlArgs.add(dto.getFaContentCode());

			sqlArgs.add(dto.getAssetsDescription());
			sqlArgs.add(dto.getBarcode());
//			sqlArgs.add(dto.getOldResponsibilityUserName());
			sqlArgs.add(dto.getOldLocationCode());
			sqlArgs.add(dto.getOldLocationCode());
			sqlArgs.add(dto.getOldLocationCode());
			sqlArgs.add(dto.getOldLocationCode());

            if(transType.equals("ITEM-RED")){
                sqlStr += " AND (EII.ITEM_STATUS = ? OR EII.ITEM_STATUS = ?)";
                sqlArgs.add(AssetsDictConstant.ITEM_STATUS_NORMAL);
                sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_FREED);
            }
            
            if( !StrUtil.isEmpty( dto.getOldResponsibilityUserName() ) ){
            	sqlStr += " AND AME.USER_NAME LIKE ? ";
            	sqlArgs.add(dto.getOldResponsibilityUserName());
            }
		} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_ASSETS_LEASE)) { // 资产续租
			LeaseLineDTO dto = (LeaseLineDTO) dtoParameter;
			 
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT ");
			sb.append(" 	EII.BARCODE, "); // 标签
			sb.append("    ESI.ITEM_NAME,  "); // 名称
			sb.append("    ESI.ITEM_SPEC,  "); // 规格型号
			sb.append("    RESPONSIBILITY_USER, "); // 责任人
			sb.append("    AME.USER_NAME RESPONSIBILITY_USER_NAME, ");
			sb.append("    ESI.MEMO ,  ");
			sb.append("    AOA.ADDRESS_NO , ");
			sb.append("    EO.WORKORDER_OBJECT_LOCATION , "); // 地点
			sb.append("    ARI.RENT_DATE, "); // 起租日期
			sb.append("    ARI.END_DATE, "); // 截至日期
			sb.append("    ARI.RENT_PERSON , "); // 出租人/单位
			sb.append("    ARI.TENANCY, "); // 租期(年)
			sb.append("    ARI.YEAR_RENTAL, "); // 年租金
			sb.append("    ARI.MONTH_REANTAL MONTH_RENTAL, "); // 月租金
			sb.append("    ARI.CONTRACT_NUMBER, "); // 合同编号
			sb.append("    ARI.CONTRACT_NAME, "); // 合同名称
			sb.append("    ARI.REMARK "); // 备注
			sb.append(" FROM   ETS_ITEM_INFO  EII, ");
			sb.append("        ETS_OBJECT          EO, ");
			sb.append("        ETS_SYSTEM_ITEM     ESI, ");
			sb.append("        AMS_RENT_INFO ARI , ");
			sb.append("        AMS_OBJECT_ADDRESS  AOA, ");
			sb.append("        AMS_MIS_EMPLOYEE AME  ");
			sb.append(" WHERE  EII.ITEM_CODE = ESI.ITEM_CODE  ");
			sb.append("   AND EII.BARCODE = ARI.BARCODE  ");
			sb.append("   AND EII.RESPONSIBILITY_USER = AME.EMPLOYEE_ID  ");
			sb.append("   AND EII.FINANCE_PROP = 'RENT_ASSETS'  ");
			sb.append("   AND EII.ADDRESS_ID = AOA.ADDRESS_ID ");
			sb.append("   AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO   ");
			sb.append("   AND EII.ORGANIZATION_ID = ? ");

			// NOT EXISTS 语句是排除了正在续租申请过程中的资产
			sb.append("   AND NOT EXISTS( ");
			sb.append("   SELECT NULL FROM AMS_ASSETS_TRANS_HEADER AATH , AMS_ASSETS_TRANS_LINE AATL ");
			sb.append("   WHERE ");  
			sb.append("   AATH.TRANS_ID = AATL.TRANS_ID ");  
			sb.append("   AND AATH.TRANS_TYPE = ? "); 
			sb.append("   AND ( AATH.TRANS_STATUS <> ? AND AATH.TRANS_STATUS <> ? ) ");  
			sb.append("   AND AATL.BARCODE = EII.BARCODE ) ");
			
			sb.append( "  AND NOT EXISTS (\n ");
			sb.append( "  SELECT \n ");
			sb.append( "  NULL \n ");
			sb.append( "  FROM \n ");
			sb.append( "  AMS_ASSETS_RESERVED AAR \n ");
			sb.append( "  WHERE \n ");
			sb.append( "  AAR.BARCODE = EII.BARCODE) \n ");
			
			sb.append("   AND ( ? = '' OR EO.WORKORDER_OBJECT_LOCATION LIKE ? )  "); 
			sb.append("   AND ( ? = '' OR AME.USER_NAME LIKE ? )  ");
			sb.append("   AND ( ? = '' OR RENT_PERSON LIKE ? ) ");
			
			sqlStr = sb.toString();
			sqlArgs.add(user.getOrganizationId());
			sqlArgs.add( LeaseAppConstant.TRANS_TYPE );
			sqlArgs.add( AssetsDictConstant.COMPLETED );
			sqlArgs.add( AssetsDictConstant.CANCELED );
			
			sqlArgs.add( dto.getWorkorderObjectLocation()  );
			sqlArgs.add( dto.getWorkorderObjectLocation()  );
			sqlArgs.add( dto.getResponsibilityUserName()  );
			sqlArgs.add( dto.getResponsibilityUserName()  );
			sqlArgs.add( dto.getRentPerson()  );
			sqlArgs.add( dto.getRentPerson()  );
			
		} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_ASSETS_SHARE)) {// 选择共享资产
			AssetSharingLineDTO dto = (AssetSharingLineDTO) dtoParameter;
			StringBuilder sb = new StringBuilder();
			sb.append(" SELECT " );
				
			sb.append("  EII.SYSTEMID,EII.BARCODE," );// --资产标签号
			sb.append("  EII.ITEM_CODE," );// 资产CODE,名称
			sb.append("  ESI.ITEM_NAME," );
			sb.append("  ESI.ITEM_SPEC," );
			sb.append("  EII.ADDRESS_ID," );// 资产地点
			sb.append("  EO.LOCATION,EO.WORKORDER_OBJECT_LOCATION," );
			sb.append("  EII.RESPONSIBILITY_USER," );// --责任人
			sb.append("  AME.USER_NAME USER_NAME, ");
//			sb.append("  SU.USERNAME as USER_NAME," );
			sb.append("  AME.EMPLOYEE_NUMBER," );
			
			sb.append("  EII.SPECIALITY_DEPT," );
			sb.append("  AMD.DEPT_NAME SPECIALITY_DEPT_NAME," );
			
			sb.append("  EII.CONTENT_CODE," );// --目录代码
			sb.append("  EII.CONTENT_NAME," );// --名称
			sb.append("  EII.START_DATE" );// --启用日期
			sb.append("  FROM ETS_ITEM_INFO EII ," );
			sb.append("  ETS_SYSTEM_ITEM ESI," );
//					sb.append("  SF_USER SU," );
			sb.append("  AMS_MIS_EMPLOYEE AME,  ");
			
			sb.append("  AMS_MIS_DEPT AMD,  ");
			
			sb.append("  AMS_OBJECT_ADDRESS AOA," );
			sb.append("  ETS_OBJECT EO" );
			sb.append("  WHERE" );
			sb.append("  EII.ORGANIZATION_ID = ? " );
			sb.append("  AND EII.ITEM_STATUS='NORMAL'" );//资产 状态 报废/闲置,正常? 					
			sb.append("  AND EII.ITEM_CODE=ESI.ITEM_CODE" );
			sb.append("  AND EII.ADDRESS_ID=AOA.ADDRESS_ID" );
			sb.append("  AND AOA.OBJECT_NO=EO.WORKORDER_OBJECT_NO" );
			sb.append("  AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID  ");
			
			sb.append("  AND EII.SPECIALITY_DEPT *= AMD.DEPT_CODE  ");

//			sb.append("  AND EII.RESPONSIBILITY_USER *=CONVERT(VARCHAR,SU.USER_ID)" );
			//sb.append("     AND EII.BARCODE LIKE '%505%'"
			//sb.append(" 	AND EII.FINANCE_PROP IN ('UNKNOW','TD_ASSETS','ASSETS' ,'DG_ASSETS','QD_ASSETS' )"
			sb.append("  AND (EII.FINANCE_PROP ='TD_ASSETS'  OR EII.FINANCE_PROP ='ASSETS' OR EII.FINANCE_PROP ='DG_ASSETS' OR EII.FINANCE_PROP ='QD_ASSETS') " );
			
			sb.append("   AND NOT EXISTS (\n "  );
			sb.append("   SELECT \n " );
			sb.append("   NULL \n " );
			sb.append("   FROM \n " );
			sb.append("   AMS_ASSETS_RESERVED AAR \n " );
			sb.append("   WHERE \n " );
			sb.append("   AAR.BARCODE = EII.BARCODE) \n " );
			if( !StrUtil.isEmpty( dto.getResponsibilityDept() ) ){
				sb.append(" AND EII.RESPONSIBILITY_DEPT = ? \n " );
			}

			sb.append(" AND (EII.SPECIALITY_DEPT = ? OR LTRIM(EII.SPECIALITY_DEPT) IS NULL) \n " );

			sb.append(" AND (?='' OR EII.CONTENT_NAME LIKE ?) \n ");
			sb.append(" AND (?='' OR AME.USER_NAME LIKE ?) \n ");
			sb.append(" AND (?='' OR EII.BARCODE LIKE ?) \n ");
			sb.append(" AND (?='' OR EO.WORKORDER_OBJECT_LOCATION LIKE ?) \n ");

			sqlStr = sb.toString();
			sqlArgs.add(user. getOrganizationId());
			if( !StrUtil.isEmpty( dto.getResponsibilityDept() ) ){
				sqlArgs.add(dto.getResponsibilityDept());
			}
	    	
			sqlArgs.add(dto.getSpecialityDept());
			
			sqlArgs.add(dto.getContentName());
			sqlArgs.add(dto.getContentName());
			sqlArgs.add(dto.getUserName());
			sqlArgs.add(dto.getUserName());
			sqlArgs.add(dto.getBarcode());
			sqlArgs.add(dto.getBarcode());
			sqlArgs.add(dto.getWorkorderObjectLocation());
			sqlArgs.add(dto.getWorkorderObjectLocation());
			
		} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_ASSETS_DEVALUE)) {// 选择可减值资产
			AssetSharingLineDTO dto = (AssetSharingLineDTO) dtoParameter;
			StringBuilder sb = new StringBuilder();
			sb.append(" SELECT \n" );
						
			sb.append("  EII.SYSTEMID, EII.BARCODE, EFA.ASSET_NUMBER, \n" );// --资产标签号
			sb.append("  EII.ITEM_CODE, \n" );// 资产CODE,名称
			sb.append("  ESI.ITEM_NAME, \n" );
			sb.append("  ESI.ITEM_SPEC, \n" );
			
			sb.append("  EFA.ASSETS_DESCRIPTION, EFA.MODEL_NUMBER, EFA.COST, EFA.DEPRN_COST, EFA.CURRENT_UNITS, EFA.UNIT_OF_MEASURE, EFA.IMPAIR_RESERVE, EFA.DEPRN_COST RETIREMENT_COST, \n" );
			sb.append("  ISNULL(EFA.COST, 0) - ISNULL(EFA.DEPRN_COST, 0) DEPRECIATION, EFA.DEPRN_LEFT_MONTH, \n" );
			sb.append("  EFA.DATE_PLACED_IN_SERVICE, \n" );
			sb.append("  EO.WORKORDER_OBJECT_NO, EO.WORKORDER_OBJECT_CODE, EO.WORKORDER_OBJECT_NAME, \n" );
			sb.append("  AM.MANUFACTURER_NAME, \n" );

			sb.append("  EII.ADDRESS_ID, \n" );// 资产地点
			sb.append("  EO.LOCATION,EO.WORKORDER_OBJECT_LOCATION, \n" );
			sb.append("  EII.RESPONSIBILITY_USER, \n" );// --责任人
			sb.append("  AME.USER_NAME USER_NAME, \n");
			sb.append("  AME.EMPLOYEE_NUMBER, \n" );
			
			sb.append("  EII.SPECIALITY_DEPT, \n" );
			sb.append("  AMD.DEPT_NAME SPECIALITY_DEPT_NAME, \n" );

			sb.append("  EII.CONTENT_CODE, \n" );// --目录代码
			sb.append("  EII.CONTENT_NAME, \n" );// --名称
			sb.append("  EII.START_DATE \n" );// --启用日期
			sb.append("  FROM ETS_ITEM_INFO EII, \n" );
			sb.append("  ETS_SYSTEM_ITEM ESI, \n" );
			
			sb.append("  ETS_FA_ASSETS EFA, \n" );
			sb.append("  AMS_MANUFACTURER AM, \n" );

			sb.append("  AMS_MIS_EMPLOYEE AME, \n");
			
			sb.append("  AMS_MIS_DEPT AMD, \n");
			
			sb.append("  AMS_OBJECT_ADDRESS AOA, \n" );
			sb.append("  ETS_OBJECT EO, \n" );
			
			sb.append("  ETS_ITEM_MATCH EIM \n" );
			
			sb.append("  WHERE \n" );
			sb.append("  EII.ORGANIZATION_ID = ? \n" );
			sb.append("  AND EII.ITEM_STATUS = 'NORMAL' \n" );//资产状态正常				
			sb.append("  AND EII.ITEM_CODE = ESI.ITEM_CODE \n" );
			
			//sb.append("  AND EII.ASSET_ID *= EFA.ASSET_ID \n" );

			sb.append("  AND EII.MANUFACTURER_ID *= AM.MANUFACTURER_ID \n" );

			sb.append("  AND EII.ADDRESS_ID = AOA.ADDRESS_ID \n" );
			
			sb.append("  AND EII.SYSTEMID = EIM.SYSTEMID \n" );
			sb.append("  AND EIM.ASSET_ID = EFA.ASSET_ID \n" );

			sb.append("  AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO \n" );
			sb.append("  AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID \n");
			
			sb.append("  AND EII.SPECIALITY_DEPT *= AMD.DEPT_CODE \n");

			sb.append("  AND (EII.FINANCE_PROP = 'TD_ASSETS' OR EII.FINANCE_PROP = 'ASSETS' OR EII.FINANCE_PROP ='TT_ASSETS') \n" );

			sb.append("  AND NOT EXISTS ( \n "  );
			sb.append("  SELECT \n " );
			sb.append("  NULL \n " );
			sb.append("  FROM \n " );
			sb.append("  AMS_ASSETS_RESERVED AAR \n " );
			sb.append("  WHERE \n " );
			sb.append("  AAR.BARCODE = EII.BARCODE) \n " );
			if( !StrUtil.isEmpty( dto.getResponsibilityDept() ) ){
				sb.append(" AND EII.RESPONSIBILITY_DEPT = ? \n " );
			}
			
			sb.append(" AND (EII.SPECIALITY_DEPT = ? OR LTRIM(EII.SPECIALITY_DEPT) IS NULL) \n " );
			
			sb.append(" AND ( ? = '' OR EII.CONTENT_NAME LIKE ?) \n ");
			sb.append(" AND ( ? = '' OR AME.USER_NAME LIKE ?) \n ");
			sb.append(" AND ( ? = '' OR EII.BARCODE LIKE ?) \n ");
			sb.append(" AND ( ? = '' OR EO.WORKORDER_OBJECT_LOCATION LIKE ?) \n ");

			sqlStr = sb.toString();
			sqlArgs.add(user. getOrganizationId());
			if( !StrUtil.isEmpty( dto.getResponsibilityDept() ) ){
				sqlArgs.add(dto.getResponsibilityDept());
			}
			
			sqlArgs.add(dto.getSpecialityDept());
			
			sqlArgs.add(dto.getContentName());
			sqlArgs.add(dto.getContentName());
			sqlArgs.add(dto.getUserName());
			sqlArgs.add(dto.getUserName());
			sqlArgs.add(dto.getBarcode());
			sqlArgs.add(dto.getBarcode());
			sqlArgs.add(dto.getWorkorderObjectLocation());
			sqlArgs.add(dto.getWorkorderObjectLocation());

		} else if (lookUpName.equals("LOOK_ASSETS")) {
			AmsAssetsTransLineDTO dto = (AmsAssetsTransLineDTO) dtoParameter;
			String transType =  dto.getTransType();
			String transTypeValue = dto.getTransTypeValue();
			String faContentCode = dto.getFaContentCode();
			String specialityDept = dto.getSpecialityDept();
			sqlStr = 
				"		SELECT   \n" +
				"                EII.SYSTEMID,\n" + 
				"                EII.BARCODE,\n" + 
				"                EII.ITEM_QTY ITEM_QTY ,\n" + 
				"                EII.ITEM_CODE,\n" + 
				"                EII.START_DATE,\n" + 
				
				"                EII.RESPONSIBILITY_USER,\n" + 
				"                EII.MAINTAIN_USER,\n" + 
				"                EII.RESPONSIBILITY_DEPT DEPT_CODE,\n" + 
				"                EII.ITEM_STATUS,\n" + 
				"                EII.MAINTAIN_USER MAINTAIN_USER_NAME,\n" + 
//				"                EII.POWER,\n" +
				"                EII.MANUFACTURER_ID,\n" + 
				"                EII.IS_SHARE,\n" + 
				"                EII.CONTENT_CODE,\n" + 
				"                EII.CONTENT_NAME,\n" + 
				"                EII.CONSTRUCT_STATUS,\n" + 
				"                EII.RESPONSIBILITY_DEPT,\n" + 
				
				"                EII.SPECIALITY_DEPT, \n" +
				"				 AMD.DEPT_NAME SPECIALITY_DEPT_NAME, \n" +
				
				"                EII.FINANCE_PROP,\n" + 
//				"                dbo.APP_GET_FLEX_VALUE(EII.ITEM_STATUS, 'ITEM_STATUS') ITEM_STATUS_NAME,\n" +
				"                AM.MANUFACTURER_NAME,\n" + 
				"                EOCM.BOOK_TYPE_NAME,\n" + 
				"                ESI.ITEM_UNIT UNIT_OF_MEASURE,\n" +
				"                ESI.ITEM_CATEGORY,\n" +
//				"                dbo.APP_GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY_NAME,\n" +
				"                ESI.ITEM_NAME,\n" + 
				"                ESI.ITEM_SPEC,\n" + 
				"				 dbo.APP_GET_DEPT_NAME( EII.RESPONSIBILITY_DEPT ) RESPONSIBILITY_DEPT_NAME, \n " + 
//				"                AMD.DEPT_NAME RESPONSIBILITY_DEPT_NAME,\n" +
				"                AME.USER_NAME RESPONSIBILITY_USER_NAME,\n" + 
				"                AME.EMPLOYEE_NUMBER,\n" + 
				"                EO.WORKORDER_OBJECT_NO,\n" + 
				"                EO.WORKORDER_OBJECT_CODE,\n" + 
				"                EO.WORKORDER_OBJECT_NAME,\n" + 
				"                EO.WORKORDER_OBJECT_LOCATION,\n" + 
				"                EOCM.ORGANIZATION_ID,\n" + 
				"                EOCM.COMPANY_CODE,\n" + 
				"                EOCM.COMPANY \n" + 
				
				"  FROM ETS_OU_CITY_MAP     EOCM,\n" + 
				"       ETS_OBJECT          EO,\n" + 
				"       AMS_OBJECT_ADDRESS  AOA,\n" + 
				"       AMS_MIS_EMPLOYEE    AME,\n" + 
				"       AMS_MIS_DEPT        AMD,\n" + 
				"       ETS_SYSTEM_ITEM     ESI,\n" + 
				"       ETS_ITEM_INFO       EII,\n" +
				"       AMS_MANUFACTURER    AM\n" + 
				" WHERE EII.ITEM_CODE = ESI.ITEM_CODE\n" +
				"   AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" + 
				"   AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" + 
				"   AND EO.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" + 
                "   AND EOCM.ORGANIZATION_ID = ? \n" +
				"   AND EII.MANUFACTURER_ID *= AM.MANUFACTURER_ID\n" +
				"   AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID\n" + 
				
				"   AND EII.SPECIALITY_DEPT *= AMD.DEPT_CODE \n" +
				
				//TODO SJ
				"   AND ( CONVERT( INT , EO.OBJECT_CATEGORY ) <= 73 OR CONVERT( INT , EO.OBJECT_CATEGORY ) >= 75 )\n" +
				"   AND (EII.DISABLE_DATE IS NULL OR EII.DISABLE_DATE = '' OR EII.DISABLE_DATE > GETDATE() )\n" +
				"   AND NOT EXISTS (SELECT NULL\n" +
				"   FROM AMS_ASSETS_RESERVED AAR\n" +
				"   WHERE AAR.BARCODE = EII.BARCODE) \n";
			
			StringBuilder conditionSql = new StringBuilder();
			if( !StrUtil.isEmpty( faContentCode ) ){
				conditionSql.append( "   AND EII.FINANCE_PROP = ? \n" );
			}
			if( !StrUtil.isEmpty( dto.getBarcode() ) ){
				conditionSql.append( "   AND EII.BARCODE LIKE ? \n" );
			}
			if( !StrUtil.isEmpty( dto.getItemName() ) ){
				conditionSql.append( "   AND ESI.ITEM_NAME LIKE ? \n" );
			}
			if( !StrUtil.isEmpty( dto.getWorkorderObjectLocation() ) ){
				conditionSql.append( "   AND EO.WORKORDER_OBJECT_LOCATION LIKE ? \n" );
			}

			sqlStr += conditionSql.toString();
			
            sqlArgs.add(user.getOrganizationId());
            
            if( !StrUtil.isEmpty( faContentCode ) ){
            	sqlArgs.add(faContentCode);
			}
			if( !StrUtil.isEmpty( dto.getBarcode() ) ){
				sqlArgs.add( dto.getBarcode() );
			}
			if( !StrUtil.isEmpty( dto.getItemName() ) ){
				sqlArgs.add( dto.getItemName() );
			}
			if( !StrUtil.isEmpty( dto.getWorkorderObjectLocation() ) ){
				sqlArgs.add( dto.getWorkorderObjectLocation() );
			} 
			if ("ASS-RENT".equals(transType)) { //出租
				sqlStr += " AND (EII.ITEM_STATUS = 'FREE' OR EII.ITEM_STATUS='NORMAL')";
				sqlStr += " AND EII.RESPONSIBILITY_DEPT = ?";
                sqlStr += " AND (EII.SPECIALITY_DEPT = ? OR LTRIM(EII.SPECIALITY_DEPT) IS NULL) \n";
                sqlArgs.add(dto.getResponsibilityDept());
                sqlArgs.add(dto.getSpecialityDept());
	    	} else if ("ASS-DONATE".equals(transType)) { //捐赠
	    		if ("NORMAL".equals(transTypeValue)) {//正常资产捐赠
	    			sqlStr += " AND (EII.ITEM_STATUS = 'FREE' OR EII.ITEM_STATUS='NORMAL')";
	    		} else {
	    			sqlStr += " AND (EII.ITEM_STATUS = 'DISCARDED')";
	    		}
                sqlStr += " AND (EII.SPECIALITY_DEPT = ? OR LTRIM(EII.SPECIALITY_DEPT) IS NULL) \n";
                sqlArgs.add(dto.getSpecialityDept());
	    	} else if ("ASS-REPAIRRETURN".equals(transType)) { //送修返还
	    		sqlStr += " AND EII.ITEM_STATUS = 'SEND_REPAIR'";
	    		sqlStr += " AND EII.RESPONSIBILITY_DEPT = ?";
                sqlStr += " AND (EII.SPECIALITY_DEPT = ? OR LTRIM(EII.SPECIALITY_DEPT) IS NULL) \n";
                sqlArgs.add(dto.getResponsibilityDept());
                sqlArgs.add(dto.getSpecialityDept());
	    	} else if ("ASS-REPAIR".equals(transType)) { //送修
	    		sqlStr += " AND (EII.ITEM_STATUS = 'FREE' OR EII.ITEM_STATUS='NORMAL' OR EII.ITEM_STATUS='DAMAGED')";
	    		sqlStr += " AND EII.RESPONSIBILITY_DEPT = ?";
	    		//sqlStr += " AND AMD.DEPT_CODE = ? \n";
	    		sqlStr += " AND (EII.SPECIALITY_DEPT = ? OR LTRIM(EII.SPECIALITY_DEPT) IS NULL) \n";
	    		sqlArgs.add(dto.getResponsibilityDept());
	    		sqlArgs.add(dto.getSpecialityDept());
	    	} else if ("ASS-SELL".equals(transType)) { //销售
				if ("NORMAL".equals(transTypeValue)) {//正常资产销售
	    			sqlStr += " AND (EII.ITEM_STATUS = 'FREE' OR EII.ITEM_STATUS='NORMAL')";
	    		} else {
	    			sqlStr += " AND (EII.ITEM_STATUS = 'DISCARDED')";
	    		}
                sqlStr += " AND (EII.SPECIALITY_DEPT = ? OR LTRIM(EII.SPECIALITY_DEPT) IS NULL) \n";
                sqlArgs.add(dto.getSpecialityDept());
	    	} else if ("ASS-WASTEFORECAST".equals(transType)) { //预报废
	    		sqlStr += " AND (EII.ITEM_STATUS = 'FREE' OR EII.ITEM_STATUS='NORMAL') \n";
	    		sqlStr += " AND EII.RESPONSIBILITY_DEPT = ? \n";
	    		//sqlStr += " AND AMD.DEPT_CODE = ? \n";
	    		sqlStr += " AND (EII.SPECIALITY_DEPT = ? OR LTRIM(EII.SPECIALITY_DEPT) IS NULL) \n";
	    		sqlArgs.add(dto.getResponsibilityDept());
	    		sqlArgs.add(dto.getSpecialityDept());
	    	} else if ("ASS-EXCHANGE".equals(transType)) { //置换
	    		sqlStr += " AND (EII.ITEM_STATUS = 'FREE' OR EII.ITEM_STATUS='NORMAL' OR EII.ITEM_STATUS = 'TO_DISCARD' OR EII.ITEM_STATUS='DISCARDED')";
	    	} else if ("ASS-BORROW".equals(transType)) { //借用
	    		sqlStr += " AND EII.ITEM_STATUS='NORMAL'";
	    		//sqlStr += " AND AMD.DEPT_CODE = ? \n";
	    		sqlStr += " AND (EII.SPECIALITY_DEPT = ? OR LTRIM(EII.SPECIALITY_DEPT) IS NULL) \n";
	    		sqlArgs.add(dto.getSpecialityDept());
	    	} else if ("ASS-RETURN".equals(transType)) { //送还
	    		sqlStr += " AND EII.ITEM_STATUS = 'BORROW'";
                sqlStr += " AND (EII.SPECIALITY_DEPT = ? OR LTRIM(EII.SPECIALITY_DEPT) IS NULL) \n";
                sqlArgs.add(dto.getSpecialityDept());
	    	} else if ("ASS-REPEAL".equals(transType)) { //预报废撤销
	    		sqlStr += " AND EII.ITEM_STATUS = 'PRE_DISCARDE'";
                sqlStr += " AND (EII.SPECIALITY_DEPT = ? OR LTRIM(EII.SPECIALITY_DEPT) IS NULL) \n";
                sqlArgs.add(dto.getSpecialityDept());
	    	} else if ("ASS-LOSSRPT".equals(transType)) { //挂失
	    		sqlStr += " AND (EII.ITEM_STATUS = 'FREE' OR EII.ITEM_STATUS='NORMAL' OR EII.ITEM_STATUS = 'TO_DISCARD' OR EII.ITEM_STATUS='DISCARDED')";
                sqlStr += " AND (EII.SPECIALITY_DEPT = ? OR LTRIM(EII.SPECIALITY_DEPT) IS NULL) \n";
                sqlArgs.add(dto.getSpecialityDept());
	    	}
		} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_FA_CONTENT)) {
			EtsItemInfoDTO dto = (EtsItemInfoDTO)dtoParameter;
			sqlStr = "SELECT ACD.CONTENT_CODE,\n" +
                    "       ACD.CONTENT_NAME\n" +
                    "  FROM AMS_CONTENT_DIC ACD\n" +
                    " WHERE (SUBSTRING(ACD.CONTENT_CODE, CHAR_LENGTH(ACD.CONTENT_CODE)-3, CHAR_LENGTH(ACD.CONTENT_CODE)) = '0000' OR\n" +
                    "       SUBSTRING(ACD.CONTENT_CODE, CHAR_LENGTH(ACD.CONTENT_CODE)-3, CHAR_LENGTH(ACD.CONTENT_CODE)) = '9999')\n" +
                    "   AND EXISTS\n" +
                    " (SELECT 1\n" +
                    "          FROM AMS_CONTENT_CONTRAST ACC\n" +
                    "         WHERE ACC.CONTENT_CODE = SUBSTRING(ACD.CONTENT_CODE,\n" +
                    "                 CHARINDEX('.', ACD.CONTENT_CODE) + 1,\n" +
                    "                 CHAR_LENGTH(ACD.CONTENT_CODE) - CHARINDEX('.', ACD.CONTENT_CODE) -\n" +
                    "                 CHARINDEX('.', REVERSE(ACD.CONTENT_CODE))))\n" +
                    "   AND ACD.CONTENT_CODE LIKE dbo.NVL(?, ACD.CONTENT_CODE)\n" +
                    "   AND ACD.CONTENT_NAME LIKE dbo.NVL(?, ACD.CONTENT_NAME)";
			sqlArgs.add(dto.getContentCode() );
			sqlArgs.add(dto.getContentName());
		}  else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_FA_CONTENTS)) {
			EtsItemInfoDTO dto = (EtsItemInfoDTO)dtoParameter;
			sqlStr = "SELECT SUBSTRING(ACD.CONTENT_CODE, 1,CHAR_LENGTH(ACD.CONTENT_CODE) - CHARINDEX('.', REVERSE(ACD.CONTENT_CODE))) AS CONTENT_CODE,\n" +
                    "       ACD.CONTENT_NAME\n" +
                    "  FROM AMS_CONTENT_DIC ACD \n" +
                    " WHERE \n" +
                    " 	ACD.CONTENT_CODE LIKE '%.0000' AND\n" +
                    "   SUBSTRING(ACD.CONTENT_CODE, 1, CHAR_LENGTH(ACD.CONTENT_CODE) - CHARINDEX('.', REVERSE(ACD.CONTENT_CODE))) not in (select ANCE.CONTENT_CODE from AMS_NLE_CONTENT_EX ANCE) \n" +
                    "   AND SUBSTRING(ACD.CONTENT_CODE, 1, CHAR_LENGTH(ACD.CONTENT_CODE) - CHARINDEX('.', REVERSE(ACD.CONTENT_CODE))) LIKE dbo.NVL(?, SUBSTRING(ACD.CONTENT_CODE, 1, CHAR_LENGTH(ACD.CONTENT_CODE) - CHARINDEX('.', REVERSE(ACD.CONTENT_CODE))))\n" +
                    "   AND ACD.CONTENT_NAME LIKE dbo.NVL(?, ACD.CONTENT_NAME)";
			sqlArgs.add(dto.getContentCode() );
			sqlArgs.add(dto.getContentName());
		} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_FA_LNE)) {
			EtsItemInfoDTO dto = (EtsItemInfoDTO)dtoParameter;
			sqlStr = "SELECT ACD.CONTENT_CODE,\n" +
                    "       ACD.CONTENT_NAME\n" +
                    "  FROM AMS_CONTENT_DIC ACD\n" +
                    " WHERE EXISTS (SELECT 1\n" +
                    "          FROM AMS_LNE_CONTENT ALC\n" +
                    "         WHERE ALC.CORRESPONDENCE = 'DRIVER'\n" +
                    "           AND SUBSTRING(ACD.CONTENT_CODE,\n" +
                    "                 1,\n" +
                    "                 CHAR_LENGTH(ACD.CONTENT_CODE) -\n" +
                    "                 CHARINDEX('.', REVERSE(ACD.CONTENT_CODE))) =\n" +
                    "               SUBSTRING(ALC.CONTENT_CODE,\n" +
                    "                 1,\n" +
                    "                 CHAR_LENGTH(ALC.CONTENT_CODE) -\n" +
                    "                 CHARINDEX('.', REVERSE(ALC.CONTENT_CODE))))\n" +
                    "   AND ACD.CONTENT_CODE LIKE dbo.NVL(?, ACD.CONTENT_CODE)\n" +
                    "   AND ACD.CONTENT_NAME LIKE dbo.NVL(?, ACD.CONTENT_NAME)";
			sqlArgs.add(dto.getContentCode() );
			sqlArgs.add(dto.getContentName());
		} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_FA_CEX)) {
			EtsItemInfoDTO dto = (EtsItemInfoDTO)dtoParameter;
			sqlStr = "SELECT ACD.CONTENT_CODE,\n" +
                    "       ACD.CONTENT_NAME\n" +
                    "  FROM AMS_CONTENT_DIC ACD\n" +
                    " WHERE EXISTS (SELECT 1\n" +
                    "          FROM AMS_CEX_CONTENT ACC\n" +
                    "         WHERE ACC.CORRESPONDENCE = 'DRIVER'\n" +
                    "           AND SUBSTRING(ACD.CONTENT_CODE,\n" +
                    "                 1,\n" +
                    "                 CHAR_LENGTH(ACD.CONTENT_CODE) -\n" +
                    "                 CHARINDEX('.', REVERSE(ACD.CONTENT_CODE))) =\n" +
                    "               SUBSTRING(ACC.CONTENT_CODE,\n" +
                    "                 1,\n" +
                    "                 CHAR_LENGTH(ACC.CONTENT_CODE) -\n" +
                    "                 CHARINDEX('.', REVERSE(ACC.CONTENT_CODE))))\n" +
                    "   AND ACD.CONTENT_CODE LIKE dbo.NVL(?, ACD.CONTENT_CODE)\n" +
                    "   AND ACD.CONTENT_NAME LIKE dbo.NVL(?, ACD.CONTENT_NAME)";
			sqlArgs.add(dto.getContentCode() );
			sqlArgs.add(dto.getContentName());
		} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_FA_OPE)) {
			EtsItemInfoDTO dto = (EtsItemInfoDTO)dtoParameter;
			sqlStr = "SELECT ACD.CONTENT_CODE,\n" +
                    "       ACD.CONTENT_NAME\n" +
                    "  FROM AMS_CONTENT_DIC ACD\n" +
                    " WHERE EXISTS (SELECT 1\n" +
                    "          FROM AMS_OPE_CONTENT AOC\n" +
                    "         WHERE AOC.CORRESPONDENCE = 'DRIVER'\n" +
                    "           AND SUBSTRING(ACD.CONTENT_CODE,\n" +
                    "                 1,\n" +
                    "                 CHAR_LENGTH(ACD.CONTENT_CODE) -\n" +
                    "                 CHARINDEX('.', REVERSE(ACD.CONTENT_CODE))) =\n" +
                    "               SUBSTRING(AOC.CONTENT_CODE,\n" +
                    "                 1,\n" +
                    "                 CHAR_LENGTH(AOC.CONTENT_CODE) -\n" +
                    "                 CHARINDEX('.', REVERSE(AOC.CONTENT_CODE))))\n" +
                    "   AND ACD.CONTENT_CODE LIKE dbo.NVL(?, ACD.CONTENT_CODE)\n" +
                    "   AND ACD.CONTENT_NAME LIKE dbo.NVL(?, ACD.CONTENT_NAME)";
			sqlArgs.add(dto.getContentCode() );
			sqlArgs.add(dto.getContentName());
		} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_FA_NLE)) {
			EtsItemInfoDTO dto = (EtsItemInfoDTO)dtoParameter;
			sqlStr = "SELECT ACD.CONTENT_CODE,\n" +
                    "       ACD.CONTENT_NAME\n" +
                    "  FROM AMS_CONTENT_DIC ACD\n" +
                    " WHERE EXISTS (SELECT 1\n" +
                    "          FROM AMS_NLE_CONTENT ANC\n" +
                    "         WHERE ANC.CORRESPONDENCE = 'DRIVER'\n" +
                    "           AND SUBSTRING(ACD.CONTENT_CODE,\n" +
                    "                 1,\n" +
                    "                 CHAR_LENGTH(ACD.CONTENT_CODE) -\n" +
                    "                 CHARINDEX('.', REVERSE(ACD.CONTENT_CODE))) =\n" +
                    "               SUBSTRING(ANC.CONTENT_CODE,\n" +
                    "                 1,\n" +
                    "                 CHAR_LENGTH(ANC.CONTENT_CODE) -\n" +
                    "                 CHARINDEX('.', REVERSE(ANC.CONTENT_CODE))))\n" +
                    "   AND ACD.CONTENT_CODE LIKE dbo.NVL(?, ACD.CONTENT_CODE)\n" +
                    "   AND ACD.CONTENT_NAME LIKE dbo.NVL(?, ACD.CONTENT_NAME)";
			sqlArgs.add(dto.getContentCode() );
			sqlArgs.add(dto.getContentName());
		} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_FA_CONTENT_LINE)) {
			AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO)dtoParameter;
			sqlStr = "SELECT ACD.CONTENT_CODE,\n" +
                    "       ACD.CONTENT_NAME\n" +
                    "  FROM AMS_CONTENT_DIC ACD\n" +
                    " WHERE (SUBSTRING(ACD.CONTENT_CODE, CHAR_LENGTH(ACD.CONTENT_CODE)-3, CHAR_LENGTH(ACD.CONTENT_CODE)) = '0000' OR\n" +
                    "       SUBSTRING(ACD.CONTENT_CODE, CHAR_LENGTH(ACD.CONTENT_CODE)-3, CHAR_LENGTH(ACD.CONTENT_CODE)) = '9999')\n" +
                    "   AND SUBSTRING(ACD.CONTENT_CODE,\n" +
                    "                 CHARINDEX('.', ACD.CONTENT_CODE) + 1,\n" +
                    "                 CHAR_LENGTH(ACD.CONTENT_CODE) - CHARINDEX('.', ACD.CONTENT_CODE) -\n" +
                    "                 CHARINDEX('.', REVERSE(ACD.CONTENT_CODE))) IN\n" +
                    "       (SELECT DISTINCT ACC.NEW_CONTENT_CODE\n" +
                    "          FROM AMS_CONTENT_CONTRAST ACC\n" +
                    "         WHERE ACC.CONTENT_CODE = SUBSTRING(?,\n" +
                    "                 CHARINDEX('.', ?) + 1,\n" +
                    "                 CHAR_LENGTH(?) - CHARINDEX('.', ?) -\n" +
                    "                 CHARINDEX('.', REVERSE(?))))\n" +
                    "   AND ACD.CONTENT_CODE LIKE dbo.NVL(?, ACD.CONTENT_CODE)\n" +
                    "   AND ACD.CONTENT_NAME LIKE dbo.NVL(?, ACD.CONTENT_NAME)";
			sqlArgs.add(dto.getFaContentCode());
			sqlArgs.add(dto.getFaContentCode());
			sqlArgs.add(dto.getFaContentCode());
			sqlArgs.add(dto.getFaContentCode());
			sqlArgs.add(dto.getFaContentCode());
			sqlArgs.add(dto.getContentCode() );
			sqlArgs.add(dto.getContentName());
		} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_LNE_LINE)) {
			AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO)dtoParameter;
			sqlStr = "SELECT AL.AMS_LNE_ID    LNE_ID,\n" +
                    "       AL.LOG_NET_ELE   LNE_NAME,\n" +
                    "       AL.NET_UNIT_CODE LNE_CODE\n" +
                    "  FROM AMS_LNE AL\n" +
                    " WHERE AL.NET_UNIT_CODE IN\n" +
                    "       (SELECT ALC.LNE_CODE\n" +
                    "          FROM AMS_LNE_CONTENT ALC\n" +
                    "         WHERE ALC.CORRESPONDENCE = 'DRIVER'\n" +
                    "           AND SUBSTRING(ALC.CONTENT_CODE,\n" +
                    "                 1,\n" +
                    "                 CHAR_LENGTH(ALC.CONTENT_CODE) -\n" +
                    "                 CHARINDEX('.', REVERSE(ALC.CONTENT_CODE))) = SUBSTRING(?,\n" +
                    "                 1,\n" +
                    "                 CHAR_LENGTH(?) -\n" +
                    "                 CHARINDEX('.', REVERSE(?))))\n" +
                    "   AND AL.NET_UNIT_CODE LIKE dbo.NVL(?, AL.NET_UNIT_CODE)\n" +
                    "   AND AL.LOG_NET_ELE LIKE dbo.NVL(?, AL.LOG_NET_ELE)";
			sqlArgs.add(dto.getFaContentCode());
			sqlArgs.add(dto.getFaContentCode());
			sqlArgs.add(dto.getFaContentCode());
			sqlArgs.add(dto.getLneCode() );
			sqlArgs.add(dto.getLneName());
		} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_CEX_LINE)) {
			AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO)dtoParameter;
			sqlStr = "SELECT AC.AMS_CEX_ID      CEX_ID,\n" +
                    "       AC.INVEST_CAT_NAME CEX_NAME,\n" +
                    "       AC.INVEST_CAT_CODE CEX_CODE\n" +
                    "  FROM AMS_CEX AC\n" +
                    " WHERE AC.INVEST_CAT_CODE IN\n" +
                    "       (SELECT ACC.CEX_CODE\n" +
                    "          FROM AMS_CEX_CONTENT ACC\n" +
                    "         WHERE ACC.CORRESPONDENCE = 'DRIVER'\n" +
                    "           AND SUBSTRING(ACC.CONTENT_CODE,\n" +
                    "                 1,\n" +
                    "                 CHAR_LENGTH(ACC.CONTENT_CODE) -\n" +
                    "                 CHARINDEX('.', REVERSE(ACC.CONTENT_CODE))) = SUBSTRING(?,\n" +
                    "                 1,\n" +
                    "                 CHAR_LENGTH(?) -\n" +
                    "                 CHARINDEX('.', REVERSE(?))))\n" +
                    "   AND AC.INVEST_CAT_CODE LIKE dbo.NVL(?, AC.INVEST_CAT_CODE)\n" +
                    "   AND AC.INVEST_CAT_NAME LIKE dbo.NVL(?, AC.INVEST_CAT_NAME)";
			sqlArgs.add(dto.getFaContentCode());
			sqlArgs.add(dto.getFaContentCode());
			sqlArgs.add(dto.getFaContentCode());
			sqlArgs.add(dto.getCexCode() );
			sqlArgs.add(dto.getCexName());
		} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_OPE_LINE)) {
			AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO)dtoParameter;
			sqlStr = "SELECT AO.AMS_OPE_ID OPE_ID,\n" +
                    "       AO.OPE_NAME   OPE_NAME,\n" +
                    "       AO.OPE_CODE   OPE_CODE\n" +
                    "  FROM AMS_OPE AO\n" +
                    " WHERE AO.OPE_CODE IN\n" +
                    "       (SELECT AOC.OPE_CODE\n" +
                    "          FROM AMS_OPE_CONTENT AOC\n" +
                    "         WHERE AOC.CORRESPONDENCE = 'DRIVER'\n" +
                    "           AND SUBSTRING(AOC.CONTENT_CODE,\n" +
                    "                 1,\n" +
                    "                 CHAR_LENGTH(AOC.CONTENT_CODE) -\n" +
                    "                 CHARINDEX('.', REVERSE(AOC.CONTENT_CODE))) = SUBSTRING(?,\n" +
                    "                 1,\n" +
                    "                 CHAR_LENGTH(?) -\n" +
                    "                 CHARINDEX('.', REVERSE(?))))\n" +
                    "   AND AO.OPE_CODE LIKE dbo.NVL(?, AO.OPE_CODE)\n" +
                    "   AND AO.OPE_NAME LIKE dbo.NVL(?, AO.OPE_NAME)";
			sqlArgs.add(dto.getFaContentCode());
			sqlArgs.add(dto.getFaContentCode());
			sqlArgs.add(dto.getFaContentCode());
			sqlArgs.add(dto.getOpeCode() );
			sqlArgs.add(dto.getOpeName());
		} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_NLE_LINE)) {
			AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO)dtoParameter;
			sqlStr = "SELECT AN.AMS_LNE_ID NLE_ID,\n" +
                    "       AN.LNE_NAME   NLE_NAME,\n" +
                    "       AN.LNE_CODE   NLE_CODE\n" +
                    "  FROM AMS_NLE AN\n" +
                    " WHERE AN.LNE_CODE IN\n" +
                    "       (SELECT ANC.NLE_CODE\n" +
                    "          FROM AMS_NLE_CONTENT ANC\n" +
                    "         WHERE ANC.CORRESPONDENCE = 'DRIVER'\n" +
                    "           AND SUBSTRING(ANC.CONTENT_CODE,\n" +
                    "                 1,\n" +
                    "                 CHAR_LENGTH(ANC.CONTENT_CODE) -\n" +
                    "                 CHARINDEX('.', REVERSE(ANC.CONTENT_CODE))) = SUBSTRING(?,\n" +
                    "                 1,\n" +
                    "                 CHAR_LENGTH(?) -\n" +
                    "                 CHARINDEX('.', REVERSE(?))))\n" +
                    "   AND AN.LNE_CODE LIKE dbo.NVL(?, AN.LNE_CODE)\n" +
                    "   AND AN.LNE_NAME LIKE dbo.NVL(?, AN.LNE_NAME)";
			sqlArgs.add(dto.getFaContentCode());
			sqlArgs.add(dto.getFaContentCode());
			sqlArgs.add(dto.getFaContentCode());
			sqlArgs.add(dto.getNleCode() );
			sqlArgs.add(dto.getNleName());
		}else if(lookUpName.equals("SF_RES_DEFINE")){
			SfRoleQueryDTO dto=(SfRoleQueryDTO)dtoParameter;
			sqlStr="SELECT RES_NAME,RES_URL FROM SF_RES_DEFINE WHERE ENABLED='Y' ";
			if(!dto.getResName().trim().equals(""))
			{
				sqlStr=sqlStr+" AND RES_NAME LIKE '%"+dto.getResName()+"%'";
			}
			if(!dto.getResUrl().trim().equals("")){
				sqlStr=sqlStr+" AND RES_URL LIKE '%"+dto.getResUrl()+"%'";				
			}
		}else if(lookUpName.equals("OU_CITY_MAP")){
			SfRoleQueryDTO dto=(SfRoleQueryDTO)dtoParameter;
			sqlStr="SELECT ORGANIZATION_ID,COMPANY FROM ETS_OU_CITY_MAP WHERE 1=1 ";
			if(!dto.getCompany().trim().equals("")){
				sqlStr+="AND COMPANY LIKE '%"+dto.getCompany()+"%'";
			}
		}else if(lookUpName.equals("LOOK_UP_ALL_USER")){
			SfUserDTO dto=(SfUserDTO)dtoParameter;
			StringBuilder sb = new StringBuilder();
			sb.append( " SELECT " );
			sb.append( " CONVERT( INT , SU.USER_ID ) USER_ID, " );
			sb.append( " SU.USERNAME USER_NAME, " );
			sb.append( " SU.LOGIN_NAME " );
			sb.append( " FROM " );
			sb.append( " SF_USER SU " );
			
			if( !StrUtil.isEmpty( dto.getUsername() ) ){
				sb.append( " WHERE " );
				sb.append( " SU.USERNAME LIKE ? " );
				sqlArgs.add( "%" + dto.getUsername() + "%" );
			}
			
			sqlStr = sb.toString();
			
		}else if (lookUpName.equals("LOOK_UP_MANAGER")) { //资产管理员
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
					+ " AND SU.ORGANIZATION_ID = ?" + " AND ("
					+ SyBaseSQLUtil.isNull() + "  OR SU.USERNAME LIKE ?)"
					+ " AND (" + SyBaseSQLUtil.isNull()
					+ " OR SU.LOGIN_NAME LIKE UPPER(?))" + " AND ( "
					+ SyBaseSQLUtil.isNull()
					+ "  OR SU.EMPLOYEE_NUMBER LIKE ?)"
		            + " AND EXISTS "
		            + "	      (SELECT NULL " 
		            + "       	 FROM SF_USER_AUTHORITY SUA " 
		            + "       	WHERE SU.USER_ID = SUA.USER_ID " 
		            + "            AND SUA.ROLE_NAME = '工单归档人') " 
		            
					+ " AND EXISTS "
					+ " 		(SELECT NULL"
					+ " 		   FROM SF_USER_RIGHT SUR"
					+ " 		  WHERE SUR.USER_ID = SU.USER_ID"
					+ " 			AND SUR.GROUP_ID = CONVERT(INT, dbo.NVL( (CASE ? WHEN '-1' THEN NULL WHEN '0' THEN NULL ELSE ? END), CONVERT(VARCHAR, SUR.GROUP_ID))))"
					+ " ORDER BY SU.LOGIN_NAME ";
			sqlArgs.add(user.getOrganizationId());
			sqlArgs.add(dto.getUserName());
			sqlArgs.add(dto.getUserName());
			sqlArgs.add(dto.getLoginName());
			sqlArgs.add(dto.getLoginName());
			sqlArgs.add(dto.getEmployeeNumber());
			sqlArgs.add(dto.getEmployeeNumber());
			sqlArgs.add("" + dto.getGroupId());
			sqlArgs.add("" + dto.getGroupId());
			
		} else if (lookUpName.equals("LOOK_UP_ORUSER")) { //普通用户或者有特殊权限的用户
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
					+ " AND SU.ORGANIZATION_ID = ?" + " AND ("
					+ SyBaseSQLUtil.isNull() + "  OR SU.USERNAME LIKE ?)"
					+ " AND (" + SyBaseSQLUtil.isNull()
					+ " OR SU.LOGIN_NAME LIKE UPPER(?))" + " AND ( "
					+ SyBaseSQLUtil.isNull()
					+ "  OR SU.EMPLOYEE_NUMBER LIKE ?)"
//		            + " AND EXISTS "
//		            + "	      (SELECT NULL " 
//		            + "       	 FROM SF_USER_AUTHORITY SUA " 
//		            + "       	WHERE SU.USER_ID = SUA.USER_ID " 
//		            + "            AND SUA.ROLE_NAME = '工单归档人') " 
		            
					+ " AND EXISTS "
					+ " 		(SELECT NULL"
					+ " 		   FROM SF_USER_RIGHT SUR"
					+ " 		  WHERE SUR.USER_ID = SU.USER_ID"
					+ " 			AND SUR.GROUP_ID = CONVERT(INT, dbo.NVL( (CASE ? WHEN '-1' THEN NULL WHEN '0' THEN NULL ELSE ? END), CONVERT(VARCHAR, SUR.GROUP_ID))))"
					+ " ORDER BY SU.LOGIN_NAME ";
			sqlArgs.add(user.getOrganizationId());
			sqlArgs.add(dto.getUserName());
			sqlArgs.add(dto.getUserName());
			sqlArgs.add(dto.getLoginName());
			sqlArgs.add(dto.getLoginName());
			sqlArgs.add(dto.getEmployeeNumber());
			sqlArgs.add(dto.getEmployeeNumber());
			sqlArgs.add("" + dto.getGroupId());
			sqlArgs.add("" + dto.getGroupId());
			
		} else if (lookUpName.equals("LOOK_UP_CITY")) { //普通用户或者有特殊权限的用户
			AmsAssetsPriviDTO dto = (AmsAssetsPriviDTO) dtoParameter;
			 sqlStr = "SELECT"
		        + " EOC.COMPANY_CODE,EOC.COMPANY,EOC.BOOK_TYPE_CODE, EOC.BOOK_TYPE_NAME,0 RECEIVD_BY,'' RECEIVD_BY_NAME "
		        + " FROM"
		        + " ETS_OU_CITY_MAP EOC"
		        + " WHERE"
		        + " EOC.IS_TD='N' " ;//为了测试注释掉
		}/* else if (lookUpName.equals("LOOK_UP_LOCATION_TASK")) {
            EtsObjectTaskDTO dto = (EtsObjectTaskDTO) dtoParameter;
            String contentBlurred = dto.getContentBlurred();
            String taskId=dto.getTaskId();
            String [] taskIds=taskId.split(",");//query one above address
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
					 + " AND EO.ORGANIZATION_ID *= EC.ORGANIZATION_ID"
					 + " AND EO.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
					 + " AND EO.OBJECT_CATEGORY = EFV.CODE"
					 + " AND EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID"
                     + " AND EFVS.CODE = ?"
					 + " AND EO.ORGANIZATION_ID = ?"
					 + " AND (EO.DISABLE_DATE "+SyBaseSQLUtil.isNullNoParam()+" OR EO.DISABLE_DATE > GETDATE() OR EO.DISABLE_DATE IS NULL)" +
                    "    AND NOT EXISTS\n" +
                    " (SELECT 'A'\n" +
                    "          FROM AMS_ASSETS_CHECK_HEADER AACH\n" +
                    "         WHERE AACH.CHECK_LOCATION = EO.WORKORDER_OBJECT_NO\n" +
                    "           AND AACH.ORDER_STATUS NOT IN ('CANCELED', 'ARCHIEVED')" +
                    "           AND AACH.CHECK_CATEGORY ='')";
           sqlArgs.add(AssetsDictConstant.OBJECT_CATEGORY);
		   sqlArgs.add(dto.getOrganizationId());
            sqlStr +=    //" AND (? ='' OR EC.COUNTY_NAME LIKE ?)"
					     " AND (? ='' OR EO.WORKORDER_OBJECT_CODE LIKE ?)"
					   + " AND (? ='' OR EO.WORKORDER_OBJECT_NAME LIKE ?)"
            		   + " AND (? ='' OR EO.COST_CODE = ?)";
            sqlStr+= " AND NOT EXISTS(SELECT 'A'\n" +
                          "                  FROM AMS_ASSETS_CHECK_HEADER AACH1\n" +
                          "                 WHERE AACH1.CHECK_LOCATION = EO.WORKORDER_OBJECT_NO\n" +
                          "                   AND (AACH1.ORDER_STATUS  = 'DOWNLOADED' OR AACH1.ORDER_STATUS  = 'UPLOADED')" +
                          "                   AND AACH1.CHECK_CATEGORY ='')";
               //sqlArgs.add(dto.getCountyName());
               //sqlArgs.add(dto.getCountyName());
			   sqlArgs.add(dto.getWorkorderObjectCode());
			   sqlArgs.add(dto.getWorkorderObjectCode());
			   sqlArgs.add(dto.getWorkorderObjectName());
			   sqlArgs.add(dto.getWorkorderObjectName());
               sqlArgs.add(dto.getCountyName());
               sqlArgs.add(dto.getCountyName());
			   //sqlArgs.add(dto.getCostCenterCode());
			   //sqlArgs.add(dto.getCostCenterCode());
		}*/ else if (lookUpName.equals(LookUpConstant.COST_CENTER)) { // 查找成本中心
			CostCenterDTO dto = (CostCenterDTO) dtoParameter;
			sqlStr = "SELECT"
					+ " ACCV.COST_CENTER_CODE,"
					+ " ACCV.COST_CENTER_NAME,"
					+ " ACCV.COMPANY_CODE,"
					+ " ACCV.ORGANIZATION_ID"
					+ " FROM"
					+ " AMS_COST_CENTER_V ACCV"
					+ " WHERE"
					+ " ACCV.ORGANIZATION_ID = ?"
					+ " AND ACCV.ENABLED_FLAG = 'Y'"
					// + " AND ACCV.COST_CENTER_CODE LIKE dbo.NVL(?,
					// ACCV.COST_CENTER_CODE)"
					// + " AND ACCV.COST_CENTER_NAME LIKE dbo.NVL(?,
					// ACCV.COST_CENTER_NAME)"
					+ "	AND (? = '' OR CONVERT(VARCHAR,ACCV.COST_CENTER_CODE) LIKE ?)"
					+ " AND (? = '' OR ACCV.COST_CENTER_NAME LIKE ?)"
					+ " ORDER BY" + " ACCV.COST_CENTER_CODE";
			sqlArgs.add(user.getOrganizationId());
			sqlArgs.add(dto.getCostCenterCode());
			sqlArgs.add(dto.getCostCenterCode());
			sqlArgs.add(dto.getCostCenterName());
			sqlArgs.add(dto.getCostCenterName());
		}

		sqlModel.setArgs(sqlArgs);
		sqlModel.setSqlStr(sqlStr);
	}
}
