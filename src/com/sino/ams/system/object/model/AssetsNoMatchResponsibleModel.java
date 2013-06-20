package com.sino.ams.system.object.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.StrUtil;
import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.newasset.dto.EtsFaAssetsDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class AssetsNoMatchResponsibleModel extends AMSSQLProducer {
	public AssetsNoMatchResponsibleModel(SfUserDTO userAccount, EtsFaAssetsDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：构造翻页查询SQL
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		EtsFaAssetsDTO dto = (EtsFaAssetsDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT EOCM.COMPANY, "						+
							"   AMD1.DEPT_NAME RESPONSIBILITY_DEPT,"+
							"	EII.BARCODE,"						+
							"	ESI.ITEM_NAME,"					    +
							"	ESI.ITEM_SPEC,"						+
							"	AME.USER_NAME,"						+
							"	AMD2.DEPT_NAME,"					+
							"	EII.CONTENT_CODE,"					+
							"	EII.CONTENT_NAME,"					+
							"	EO.WORKORDER_OBJECT_CODE,"			+
							"	EO.WORKORDER_OBJECT_NAME"			+
					     " FROM ETS_ITEM_INFO      EII,"			+
					     	"	ETS_SYSTEM_ITEM	   ESI,"			+
					     	"	AMS_MIS_DEPT       AMD1,"			+
					     	"	AMS_MIS_DEPT       AMD2,"			+
					     	"	AMS_MIS_EMPLOYEE   AME,"			+
					     	"	ETS_OU_CITY_MAP    EOCM,"			+
					     	"	AMS_OBJECT_ADDRESS AOA,"			+
					     	"	ETS_OBJECT         EO,"				+
					     	"	ETS_FLEX_VALUE_SET EFVS,"	        +
					     	"	ETS_FLEX_VALUES    EFV"				+
					    " WHERE ESI.ITEM_CODE = EII.ITEM_CODE	" 	+
					    "	AND (EO.DISABLE_DATE = '' OR EO.DISABLE_DATE IS NULL)"				+
//					    "	AND (EII.DISABLE_DATE = '' OR EII.ITEM_STATUS = 'DISCARDED')" +
					    "	AND EFVS.FLEX_VALUE_SET_ID = EFV.FLEX_VALUE_SET_ID "	+
					    "	AND EFVS.CODE = 'OBJECT_CATEGORY' "	+
					    "	AND EO.OBJECT_CATEGORY = EFV.CODE ";
        if(dto.getUserName().equals("")){
            sqlStr += "   AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID";
        }  else {
            sqlStr += "   AND EII.RESPONSIBILITY_USER = AME.EMPLOYEE_ID";
        }

        sqlStr +=
					    "	AND EII.RESPONSIBILITY_DEPT *= AMD1.DEPT_CODE";
        sqlStr +=
					    "	AND AME.DEPT_CODE *= AMD2.DEPT_CODE"				+
					    "	AND EII.ORGANIZATION_ID *= EOCM.ORGANIZATION_ID"	+
					    "	AND EII.ADDRESS_ID = AOA.ADDRESS_ID"				+
					    "	AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO"		+
					    "	AND EII.ITEM_STATUS <> 'DISCARDED'"		            +
					    "	AND NOT EXISTS"										+
					    "			(SELECT 1"									+
					    "			   FROM (SELECT EII.BARCODE"				+
					    "					   FROM ETS_ITEM_INFO    EII,"		+
					    "							AMS_MIS_EMPLOYEE AME,"		+
					    "							AMS_MIS_DEPT     AMD"		+
					    "			  		  WHERE EII.RESPONSIBILITY_USER = AME.EMPLOYEE_ID"		+
					    "						AND EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE"		+
					    "						AND AME.DEPT_CODE = AMD.DEPT_CODE) TMP"				+
					    "			  WHERE EII.BARCODE = TMP.BARCODE)";
					    if(dto.getOrganizationId() != -1){
					    	sqlStr += "	AND EII.ORGANIZATION_ID = ? "	;
					    	sqlArgs.add(dto.getOrganizationId());
					    }
					    if(!dto.getResponsibilityDept().equals("")){
					    	sqlStr += "	AND AMD1.DEPT_CODE = ? ";
					    	sqlArgs.add(dto.getResponsibilityDept());
					    }
					    if(!dto.getUserName().equals("")){
					    	sqlStr += "	AND AME.USER_NAME LIKE ? ";
					    	sqlArgs.add(dto.getUserName());
					    }
					    if( StrUtil.isNotEmpty(dto.getAddressId())  ){
					    	sqlStr += "	AND EII.ADDRESS_ID = ? ";
					    	sqlArgs.add(dto.getAddressId());
					    }
					    if(!dto.getContentCode().equals("")){
					    	sqlStr += "	AND EII.CONTENT_CODE = ? ";
					    	sqlArgs.add(dto.getContentCode());
					    }
					    if(!dto.getItemName().equals("")){
					    	sqlStr += "	AND ESI.ITEM_NAME LIKE ? ";
					    	sqlArgs.add(dto.getItemName());
					    }
		sqlStr += " ORDER BY EII.SYSTEMID";

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);

		return sqlModel;
	}

}
