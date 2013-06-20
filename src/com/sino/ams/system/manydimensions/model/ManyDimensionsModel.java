package com.sino.ams.system.manydimensions.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.StrUtil;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.newasset.dto.AmsMisDeptDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * <p>Description:  陕西移动资产管理系统</p>
 * <p>Company:      北京思诺博信息技术有限公司</p>
 * @author          李轶
 * @date            2009-08-04
 */
public class ManyDimensionsModel extends AMSSQLProducer {
	private String deptCodes = "";

	/**
	 * 功能：构造函数
	 * @param userAccount SfUserDTO
	 * @param dtoParameter EtsItemInfoDTO
	 */
	public ManyDimensionsModel(SfUserDTO userAccount, AmsAssetsAddressVDTO dtoParameter){
		super(userAccount, dtoParameter);
        initDeptCodes();
    }

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
						+ " dbo.APP_GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY_NAME,"
						+ " ESI.ITEM_NAME,"
						+ " ESI.ITEM_SPEC,"
						+ " EO.WORKORDER_OBJECT_CODE,"
						+ " EO.WORKORDER_OBJECT_NAME,"
						+ " AME.USER_NAME RESPONSIBILITY_USER_NAME,"
						+ " AME.EMPLOYEE_NUMBER,"
						+ " AMD.DEPT_NAME RESPONSIBILITY_DEPT_NAME,"
						+ " EII.RESPONSIBILITY_USER,"
						+ " EII.RESPONSIBILITY_DEPT,"
						+ " EII.ADDRESS_ID,"
						+ " EOCM.COMPANY_CODE,"
						+ " EOCM.COMPANY,"
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
						+ " ETS_FLEX_VALUE_SET          EFVS,"
						+ " ETS_FLEX_VALUES             EFV"
						+ " WHERE"
						+ " EII.ITEM_CODE = ESI.ITEM_CODE"
						+ " AND EII.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
						+ " AND EII.ADDRESS_ID = AOA.ADDRESS_ID"
						+ " AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO"
						+ " AND EO.COUNTY_CODE *= EC.COUNTY_CODE"
                        + " AND EC.COMPANY_CODE = EOCM.COMPANY_CODE"
						+ " AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID"
						+ " AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE"
						+ " AND EII.LNE_ID *= AL.AMS_LNE_ID"
						+ " AND EII.CEX_ID *= AC.AMS_CEX_ID"
						+ " AND EII.OPE_ID *= AO.AMS_OPE_ID"
						+ " AND EII.NLE_ID *= AN.AMS_LNE_ID"
						+ " AND EFVS.FLEX_VALUE_SET_ID = EFV.FLEX_VALUE_SET_ID"
						+ " AND EFVS.CODE = 'OBJECT_CATEGORY'"
						+ " AND EO.OBJECT_CATEGORY = EFV.CODE"
						+ " AND ESI.ITEM_CATEGORY = dbo.NVL(?, ESI.ITEM_CATEGORY)"
						+ " AND ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME)"
						+ " AND (EO.WORKORDER_OBJECT_CODE LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_CODE)"
						+ " OR EO.WORKORDER_OBJECT_NAME LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_NAME))"
                        + " AND ( " + SyBaseSQLUtil.isNull() + " OR AME.USER_NAME LIKE ?)"
                        + " AND ( " + SyBaseSQLUtil.isNull() + " OR EII.RESPONSIBILITY_DEPT = ?)"
                        + " AND EII.ORGANIZATION_ID = ?"
                        + " AND (EII.FINANCE_PROP = 'ASSETS' OR EII.FINANCE_PROP = 'TD_ASSETS' OR EII.FINANCE_PROP = 'TT_ASSETS' OR EII.FINANCE_PROP = 'PRJ_MTL' OR EII.FINANCE_PROP = 'OTHERS' OR EII.FINANCE_PROP = 'UNKNOW')"
						+ " AND NOT EXISTS("
						+ " SELECT"
						+ " NULL"
						+ " FROM"
						+ " AMS_ASSETS_RESERVED ARR"
						+ " WHERE"
						+ " EII.BARCODE = ARR.BARCODE)"

						+ " AND NOT EXISTS("
						+ " SELECT"
						+ " NULL"
						+ " FROM"
						+ " AMS_ASSETS_CHECK_LINE AACL"
						+ " WHERE"
						+ " EII.BARCODE = AACL.BARCODE"
						+ " AND AACL.ARCHIVE_STATUS = '')";
		sqlArgs.add(dto.getItemCategory());
		sqlArgs.add(dto.getItemName());
		sqlArgs.add(dto.getWorkorderObjectCode());
		sqlArgs.add(dto.getWorkorderObjectName());
		sqlArgs.add(dto.getResponsibilityUserName());
		sqlArgs.add(dto.getResponsibilityUserName());
		sqlArgs.add(dto.getResponsibilityDept());
		sqlArgs.add(dto.getResponsibilityDept());
        sqlArgs.add(userAccount.getOrganizationId());

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
			sqlStr += " AND EII.ATTRIBUTE1 = ''";
		} else {
			sqlStr += " AND EII.ATTRIBUTE1 = ?";
			sqlArgs.add(dto.getAttribute1());
		}

        if(dto.isLneIsNull() == true){
            sqlStr += " AND ( EII.LNE_ID = '' ";
        } else {
            sqlStr += " AND ( 1 = 1 ";
        }
        if(dto.isCexIsNull() == true){
            sqlStr += " AND EII.CEX_ID = '' ";
        }
        if(dto.isOpeIsNull() == true){
            sqlStr += " AND EII.OPE_ID = '' ";
        }
        if(dto.isNleIsNull() == true){
            sqlStr += "AND EII.NLE_ID = '' )";
        } else {
            sqlStr += ")";
        }
        if(dto.getResponsibilityDept().equals("") && (!userAccount.isProvAssetsManager() || !userAccount.isSysAdmin()) ){
            sqlStr += " AND EII.RESPONSIBILITY_DEPT IN " + deptCodes;
        }
        sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}


	/**
	 * 功能：获取数据更新
	 * @return SQLModel
	 * @throws com.sino.base.exception.SQLModelException
	 */
	public SQLModel getDataUpdateModel() throws SQLModelException{
		SQLModel sqlModel = new SQLModel();
        AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
        String sqlStr = "UPDATE"
                        + " ETS_ITEM_INFO"
                        + " SET"
                        + " LNE_ID              = dbo.NVL(?, LNE_ID),"
                        + " CEX_ID              = dbo.NVL(?, CEX_ID),"
                        + " OPE_ID              = dbo.NVL(?, OPE_ID),"
                        + " NLE_ID              = dbo.NVL(?, NLE_ID),"
                        + " LAST_UPDATE_DATE    = GETDATE(),"
                        + " LAST_UPDATE_BY      = ?"
                        + " WHERE"
                        + " BARCODE = ?";
        List sqlArgs = new ArrayList();
        sqlArgs.add(dto.getLneId());
        sqlArgs.add(dto.getCexId());
        sqlArgs.add(dto.getOpeId());
        sqlArgs.add(dto.getNleId());

        sqlArgs.add(userAccount.getUserId());
        sqlArgs.add(dto.getBarcode());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
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
						+ " dbo.APP_GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY_NAME,"
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
						+ " dbo.APP_GET_FLEX_VALUE(EII.FINANCE_PROP, 'FINANCE_PROP') FINANCE_PROP_VALUE,"
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
						+ " AND EII.BARCODE = ?";
		List sqlArgs = new ArrayList();
		sqlArgs.add(dto.getBarcode());
		if(!userAccount.isComAssetsManager()){
			sqlStr += " AND EII.RESPONSIBILITY_DEPT IN " + deptCodes;
		} else {
			sqlStr += " AND EII.ORGANIZATION_ID = ? ";
			sqlArgs.add(userAccount.getOrganizationId());
		}
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);

		return sqlModel;
	}
}