package com.sino.ams.dzyh.bean;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.dzyh.constant.DzyhLookUpConstant;
import com.sino.ams.dzyh.dto.EamCheckTaskDTO;
import com.sino.ams.system.basepoint.dto.EtsObjectDTO;
import com.sino.ams.system.item.dto.EtsSystemItemDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.lookup.LookUpModel;
import com.sino.base.lookup.LookUpProp;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class LvecLookUpModel extends LookUpModel {
	private SfUserDTO user = null;

	public LvecLookUpModel(BaseUserDTO userAccount, DTO dtoParameter, LookUpProp lookProp) {
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
		if (lookUpName.equals(DzyhLookUpConstant.LOOK_UP_TASK)) {
			EamCheckTaskDTO dto = (EamCheckTaskDTO) dtoParameter;
			sqlStr = "SELECT"
					 + " ECT.TASK_NAME,"
					 + " ECT.START_DATE,"
					 + " ECT.END_DATE,"
					 + " ECT.CHECK_TASK_ID,"
					 + " AMS_PUB_PKG.GET_FLEX_VALUE(ECT.CHECK_TYPE, 'CHECK_TYPE') CHECK_TYPE_VALUE"
					 + " FROM"
					 + " EAM_CHECK_TASK ECT"
					 + " WHERE"
					 + " ECT.TASK_STATUS = 'OPENING'"
					 + " AND ECT.TASK_NAME LIKE dbo.NVL(?, ECT.TASK_NAME)"
					 + " AND ECT.CHECK_TYPE = ?"
					 + " AND ECT.ORGANIZATION_ID = ?";
			sqlArgs.add(dto.getTaskName());
			sqlArgs.add(dto.getCheckType());
			sqlArgs.add(user.getOrganizationId());
		} else if (lookUpName.equals(DzyhLookUpConstant.LOOK_UP_USER)) {
			SfUserDTO dto = (SfUserDTO) dtoParameter;
			sqlStr = "SELECT"
					 + " EOCM.COMPANY,"
					 + " SU.USER_ID IMPLEMENT_BY,"
					 + " SU.USERNAME IMPLEMENT_USER,"
					 + " SU.EMPLOYEE_NUMBER"
					 + " FROM"
					 + " SF_USER         SU,"
					 + " ETS_OU_CITY_MAP EOCM"
					 + " WHERE"
					 + " SU.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
					 + " AND (SU.DISABLE_DATE IS NULL OR SU.DISABLE_DATE = '' OR SU.DISABLE_DATE > GETDATE())"
					 + " AND SU.ORGANIZATION_ID = ?"
					 + " AND SU.USERNAME LIKE dbo.NVL(?, SU.USERNAME)"
					 + " AND ( " + SyBaseSQLUtil.isNull() + "  OR SU.EMPLOYEE_NUMBER LIKE dbo.NVL(?, SU.EMPLOYEE_NUMBER))";
			sqlArgs.add(user.getOrganizationId());
			sqlArgs.add(dto.getUsername());
			sqlArgs.add(dto.getEmployeeNumber());
			sqlArgs.add(dto.getEmployeeNumber());
		} else if (lookUpName.equals(DzyhLookUpConstant.LOOK_QRY_LOC)) {
			EtsObjectDTO dto = (EtsObjectDTO) dtoParameter;
			sqlStr = "SELECT"
					 + " EOCM.COMPANY,"
					 + " EC.COUNTY_NAME,"
					 + " EO.WORKORDER_OBJECT_NO,"
					 + " AMS_PUB_PKG.GET_FLEX_VALUE(EO.OBJECT_CATEGORY, 'OBJECT_CATEGORY') OBJECT_CATEGORY,"
					 + " EO.LOCATION_CODE,"
					 + " EO.WORKORDER_OBJECT_NAME LOCATION_NAME"
					 + " FROM"
					 + " ETS_OBJECT         EO,"
					 + " ETS_COUNTY         EC,"
					 + " ETS_OU_CITY_MAP    EOCM"
					 + " WHERE"
					 + " EO.COUNTY_CODE *= EC.COUNTY_CODE"
					 + " AND EO.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
                     + " AND EC.COMPANY_CODE = EOCM.COMPANY_CODE"
					 + " AND (EO.DISABLE_DATE " + SyBaseSQLUtil.isNullNoParam() + "  OR EO.DISABLE_DATE > GETDATE()) OR EO.DISABLE_DATE IS NULL "
					 + " AND (EO.OBJECT_CATEGORY = 98 OR EO.OBJECT_CATEGORY = 99)"
					 + " AND EO.WORKORDER_OBJECT_CODE LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_CODE)"
					 + " AND EO.WORKORDER_OBJECT_NAME LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_NAME)"
					 + " AND EO.ORGANIZATION_ID = ?"
					 + " AND EO.OBJECT_CATEGORY = ?";
			sqlArgs.add(dto.getWorkorderObjectCode());
			sqlArgs.add(dto.getWorkorderObjectName());
			sqlArgs.add(user.getOrganizationId());
			sqlArgs.add(dto.getObjectCategory());
		} else if(lookUpName.equals(DzyhLookUpConstant.LOOK_UP_ITEM)){
			EtsSystemItemDTO dto = (EtsSystemItemDTO) dtoParameter;
			sqlStr = "SELECT DISTINCT"
					 + " ESI.ITEM_NAME"
					 + " FROM"
					 + " ETS_SYSTEM_ITEM ESI"
					 + " WHERE"
					 + " ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME)"
					 + " AND ESI.ITEM_CATEGORY = 'YQYB'"
					 + " AND EXISTS ("
					 + " SELECT"
					 + " NULL"
					 + " FROM"
					 + " ETS_SYSITEM_DISTRIBUTE ESD"
					 + " WHERE"
					 + " ESI.ITEM_CODE = ESD.ITEM_CODE"
					 + " AND ESD.ORGANIZATION_ID = ?)";
			sqlArgs.add(dto.getItemName());
			sqlArgs.add(user.getOrganizationId());
		}
		sqlModel.setArgs(sqlArgs);
		sqlModel.setSqlStr(sqlStr);
	}
}
