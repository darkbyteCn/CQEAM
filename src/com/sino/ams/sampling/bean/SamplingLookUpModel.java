package com.sino.ams.sampling.bean;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.sampling.constant.SamplingDicts;
import com.sino.ams.sampling.constant.SamplingLookUpConstant;
import com.sino.ams.system.basepoint.dto.EtsObjectDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.lookup.LookUpModel;
import com.sino.base.lookup.LookUpProp;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: SinoAMS</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */
public class SamplingLookUpModel extends LookUpModel {
	private SfUserDTO user = null;

	public SamplingLookUpModel(BaseUserDTO userAccount, DTO dtoParameter, LookUpProp lookProp) {
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
		if (lookUpName.equals(SamplingLookUpConstant.LOOK_SAMPLING_LOC)) {
			EtsObjectDTO dto = (EtsObjectDTO) dtoParameter;
			sqlStr = "SELECT"
					 + " EOCM.COMPANY,"
					 + " EC.COUNTY_NAME,"
					 + " dbo.APP_GET_FLEX_VALUE(EO.OBJECT_CATEGORY, 'OBJECT_CATEGORY') OBJECT_CATEGORY,"
					 + " EO.WORKORDER_OBJECT_NO SAMPLING_LOCATION,"
					 + " EO.WORKORDER_OBJECT_CODE SAMPLING_LOCATION_CODE,"
					 + " EO.WORKORDER_OBJECT_NAME SAMPLING_LOCATION_NAME,"
					 + " EO.WORKORDER_OBJECT_LOCATION,"
					 + " dbo.APP_GET_OBJECT_COUNT(EO.WORKORDER_OBJECT_NO, EO.ORGANIZATION_ID) EII_COUNT"
					 + " FROM"
					 + " ETS_OBJECT         EO,"
					 + " ETS_COUNTY         EC,"
					 + " ETS_OU_CITY_MAP    EOCM,"
					 + " ETS_FLEX_VALUE_SET EFVS,"
					 + " ETS_FLEX_VALUES EFV"
					 + " WHERE"
					 + " EO.COUNTY_CODE *= EC.COUNTY_CODE"
                     + " AND EO.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
					 + " AND EO.OBJECT_CATEGORY = EFV.CODE"
					 + " AND EFVS.FLEX_VALUE_SET_ID = EFV.FLEX_VALUE_SET_ID"
					 + " AND EFVS.CODE = 'OBJECT_CATEGORY'"
//                     + " AND (EO.OBJECT_CATEGORY < '70' OR EO.OBJECT_CATEGORY = '80')"
                     + " AND (EO.DISABLE_DATE = '' OR EO.DISABLE_DATE >= GETDATE() OR EO.DISABLE_DATE IS NULL)"
                     + " AND (" + SyBaseSQLUtil.isNull() + "  OR EO.WORKORDER_OBJECT_CODE LIKE ?)"
                     + " AND (" + SyBaseSQLUtil.isNull() + "  OR EO.WORKORDER_OBJECT_NAME LIKE ?)"
					 + " AND EO.ORGANIZATION_ID = ?"
					 + " AND NOT EXISTS("
					 + " SELECT"
					 + " NULL"
					 + " FROM"
					 + " AMS_ASSETS_SAMPLING_HEADER AASH"
					 + " WHERE"
					 + " EO.WORKORDER_OBJECT_NO = AASH.SAMPLING_LOCATION"
					 + " AND (AASH.ORDER_STATUS = ?"
					 + " OR AASH.ORDER_STATUS = ?))";
			sqlArgs.add(dto.getWorkorderObjectCode());
			sqlArgs.add(dto.getWorkorderObjectCode());
			sqlArgs.add(dto.getWorkorderObjectName());
			sqlArgs.add(dto.getWorkorderObjectName());
			if(user.isProvinceUser() && dto.getOrganizationId()>0){
				sqlArgs.add(dto.getOrganizationId());
			} else {
				sqlArgs.add(user.getOrganizationId());
			}
			sqlArgs.add(SamplingDicts.ORDER_STS1_DISTRIBUTED);
			sqlArgs.add(SamplingDicts.ORDER_STS1_DOWNLOADED);
		} else if (lookUpName.equals(SamplingLookUpConstant.LOOK_QRY_LOC)) {
			EtsObjectDTO dto = (EtsObjectDTO) dtoParameter;
			sqlStr = "SELECT DISTINCT "
					 + " EOCM.COMPANY,"
					 + " EC.COUNTY_NAME,"
					 + " dbo.APP_GET_FLEX_VALUE(EO.OBJECT_CATEGORY, 'OBJECT_CATEGORY') OBJECT_CATEGORY,"
					 + " EO.WORKORDER_OBJECT_NO SAMPLING_LOCATION,"
					 + " EO.WORKORDER_OBJECT_CODE SAMPLING_LOCATION_CODE,"
					 + " EO.WORKORDER_OBJECT_NAME SAMPLING_LOCATION_NAME,"
					 + " EO.WORKORDER_OBJECT_LOCATION"
					 + " FROM"
					 + " ETS_OBJECT         EO,"
					 + " ETS_COUNTY         EC,"
					 + " ETS_OU_CITY_MAP    EOCM"
					 + " WHERE"
					 + " EO.COUNTY_CODE *= EC.COUNTY_CODE"
					 + " AND EO.ORGANIZATION_ID = ISNULL(?, EO.ORGANIZATION_ID)"
					 + " AND EO.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
					 + " AND (DISABLE_DATE='' OR EO.DISABLE_DATE >= GETDATE() OR EO.DISABLE_DATE IS NULL)"
					 + " AND (EO.OBJECT_CATEGORY < '70' OR EO.OBJECT_CATEGORY = '80')"
					// + " AND EO.WORKORDER_OBJECT_CODE LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_CODE)"
					  + " AND (?='' OR EO.WORKORDER_OBJECT_CODE LIKE ?)"
					 //+ " AND EO.WORKORDER_OBJECT_NAME LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_NAME)"
					  + " AND (?='' OR EO.WORKORDER_OBJECT_NAME LIKE ?)"					 
					 ;
			if(user.isProvinceUser() && dto.getOrganizationId()>0){
				sqlArgs.add(dto.getOrganizationId());
			} else {
				sqlArgs.add(user.getOrganizationId());
			}
			sqlArgs.add(dto.getWorkorderObjectCode());
			sqlArgs.add(dto.getWorkorderObjectCode());
			sqlArgs.add(dto.getWorkorderObjectName());
			sqlArgs.add(dto.getWorkorderObjectName());
		} else if (lookUpName.equals(SamplingLookUpConstant.LOOK_QRY_USER)) {
			SfUserDTO dto = (SfUserDTO) dtoParameter;
			sqlStr = "SELECT"
					 + " EOCM.COMPANY,"
					 + " SU.LOGIN_NAME,"
					 + " SU.USER_ID,"
					 + " SU.USERNAME IMPLEMENT_USER,"
					 + " SU.EMPLOYEE_NUMBER"
					 + " FROM"
					 + " SF_USER         SU,"
					 + " ETS_OU_CITY_MAP EOCM"
					 + " WHERE"
					 + " SU.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
					 + " AND (SU.DISABLE_DATE IS NULL OR SU.DISABLE_DATE ='' OR SU.DISABLE_DATE > GETDATE())"
					 + " AND SU.ORGANIZATION_ID = ?"
					 //+ " AND SU.USERNAME LIKE dbo.NVL(?, SU.USERNAME)"
					 + " AND (? ='' OR SU.USERNAME LIKE dbo.NVL(?, SU.USERNAME) )"
					 + " AND (?='' OR SU.LOGIN_NAME LIKE dbo.NVL(UPPER(?), SU.LOGIN_NAME))"
					 + " AND ( " + SyBaseSQLUtil.isNull() + "  OR SU.EMPLOYEE_NUMBER LIKE dbo.NVL(?, SU.EMPLOYEE_NUMBER))";
			sqlArgs.add(user.getOrganizationId());
			sqlArgs.add(dto.getUsername());
			sqlArgs.add(dto.getUsername());
			sqlArgs.add(dto.getLoginName());
			sqlArgs.add(dto.getLoginName());
			sqlArgs.add(dto.getEmployeeNumber());
			sqlArgs.add(dto.getEmployeeNumber());
		}
		
		sqlModel.setArgs(sqlArgs);
		sqlModel.setSqlStr(sqlStr);
	}
}
