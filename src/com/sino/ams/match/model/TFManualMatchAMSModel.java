package com.sino.ams.match.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.newasset.dto.AmsMisDeptDTO;
import com.sino.ams.system.fixing.dto.EtsItemInfoDTO;
import com.sino.framework.dto.BaseUserDTO;

public class TFManualMatchAMSModel extends AMSSQLProducer {
	public TFManualMatchAMSModel(BaseUserDTO userAccount, DTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsItemInfoDTO dto = (EtsItemInfoDTO) dtoParameter;		
		String sqlStr = 
		      "SELECT /*+first_rows*/ \n"
			+ "       EII.SYSTEMID, \n"
			+ "       EII.MAINTAIN_USER, \n"
			+ "       EII.BARCODE, \n"
			+ "       dbo.APP_GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY, \n"
			+ "       ESI.ITEM_NAME, \n"
			+ "       ESI.ITEM_SPEC, \n"
			+ "       EO.WORKORDER_OBJECT_CODE, \n"
			+ "       EO.WORKORDER_OBJECT_NAME, \n"
			+ "       AME.USER_NAME RESPONSIBILITY_USER_NAME, \n"
			+ "       AMD.DEPT_NAME RESPONSIBILITY_DEPT_NAME, \n"
			+ "       EPPA.NAME PROJECT_NAME, \n"
			+ "       EII.START_DATE, \n" 
            + "       EII.CONTENT_CODE, \n" 
            + "		  EII.CONTENT_NAME \n"
			+ "FROM   ETS_ITEM_INFO      EII, \n"
			+ "       ETS_SYSTEM_ITEM    ESI, \n"
			+ "       ETS_OBJECT         EO, \n"
			+ "       AMS_OBJECT_ADDRESS AOA, \n"
			+ "       AMS_MIS_EMPLOYEE   AME, \n"
			+ "       ETS_PA_PROJECTS_ALL EPPA, \n"
			+ "       AMS_MIS_DEPT       AMD \n"
			+ "WHERE  EII.ITEM_CODE = ESI.ITEM_CODE \n"
			+ "       AND EII.ADDRESS_ID = AOA.ADDRESS_ID \n"
			+ "       AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO \n"
			+ "       AND EII.ORGANIZATION_ID = EO.ORGANIZATION_ID \n"
			+ "       AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID \n"
			+ "       AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE \n"
			+ "       AND EII.PROJECTID *= EPPA.PROJECT_ID \n"
			+ "       AND (EO.OBJECT_CATEGORY < = '70' OR EO.OBJECT_CATEGORY = '80') \n"
			//+ "       AND EII.FINANCE_PROP = 'UNKNOW' \n"
			
			+ "       AND (EII.FINANCE_PROP = 'UNKNOW' OR EII.FINANCE_PROP = 'DG_ASSETS' OR EII.FINANCE_PROP = 'QD_ASSETS') \n"
			+ "       AND EII.BARCODE LIKE '%TF%' \n"

			+ "       AND EII.ITEM_STATUS <> 'DISCARDED' \n" ;
            if(userAccount.isDptAssetsManager() && !userAccount.isComAssetsManager()){
            	DTOSet depts = userAccount.getPriviDeptCodes();
            	AmsMisDeptDTO dept = null;
            	String deptCodes = "";
            	if( null != depts ){
	            	deptCodes = "(";
	            	for (int i = 0; i < depts.getSize(); i++) {
	            		dept = (AmsMisDeptDTO) depts.getDTO(i);
	            		deptCodes += "'" + dept.getDeptCode() + "', ";
	            	}
	            	deptCodes += "'')";
            	}
            	//测试用，先屏蔽此条件
            	//sqlStr += " AND EII.RESPONSIBILITY_DEPT IN " + deptCodes;
            }  

           	sqlStr += 
           	  "       AND ESI.ITEM_CATEGORY = dbo.NVL(LTRIM(?), ESI.ITEM_CATEGORY) \n"
			+ "       AND ESI.IS_FIXED_ASSETS = 'Y' \n"
			+ "       AND EII.FINANCE_PROP <> 'OTHER' \n"
			+ "       AND (EII.DISABLE_DATE IS NULL OR EII.DISABLE_DATE = '')  \n"
			+ "       AND (LTRIM(?) IS NULL  OR ESI.ITEM_NAME LIKE LTRIM(?)) \n"
			+ "       AND (LTRIM(?) IS NULL OR ESI.ITEM_SPEC LIKE LTRIM(?)) \n"
			+ "       AND NOT EXISTS \n"
			+ " 		  (SELECT 1 FROM ETS_ITEM_MATCH_TF EIM WHERE EII.SYSTEMID = EIM.SYSTEMID) \n"
//					+ "       AND NOT EXISTS\n"
//					+ " (SELECT 1 FROM ETS_NO_MATCH ENM WHERE ENM.SYSTEMID = EII.SYSTEMID)\n"
			// 在ETS_NO_MATCH有的资产不参加匹配
			+ "       AND EII.ORGANIZATION_ID = ? \n"
			+ "       AND ((LTRIM(?) IS NULL OR EII.BARCODE LIKE LTRIM(?)) OR \n"
			+ "       	   (LTRIM(?) IS NULL OR ESI.ITEM_NAME LIKE LTRIM(?)) OR \n"
			+ "       	   (LTRIM(?) IS NULL OR ESI.ITEM_SPEC LIKE LTRIM(?))) \n"
			+ "       AND (LTRIM(?) IS NULL OR EO.WORKORDER_OBJECT_NAME = LTRIM(?)) \n"
			+ "       AND ((LTRIM(?) IS NULL OR EII.RESPONSIBILITY_DEPT LIKE LTRIM(?)) OR (LTRIM(?) IS NULL OR AMD.DEPT_NAME LIKE LTRIM(?))) \n"
			+ "       AND (LTRIM(?) IS NULL OR EPPA.PROJECT_ID = LTRIM(?)) \n"
			+ "       AND (LTRIM(?) IS NULL OR AME.USER_NAME LIKE LTRIM(?)) \n";

			if (servletConfig.getProvinceCode().equals(DictConstant.PROVINCE_CODE_JIN)) {
			sqlStr += " AND EXISTS ( \n"
			+ " SELECT 1 \n"
			+ "  FROM (SELECT EWDD.BARCODE \n"
			+ // 巡检
			"            FROM ETS_WORKORDER_DIFF_DTL EWDD, ETS_WORKORDER EW \n"
			+ "         WHERE EWDD.WORKORDER_NO = EW.WORKORDER_NO \n"
			+ "           AND EW.WORKORDER_FLAG = '14' \n"
			+ "           AND EWDD.VERIFY_RESULT = '扫描结果为准' \n"
			+ "         UNION ALL \n"
			+ "        SELECT EWDD.BARCODE \n"
			+ "          FROM ETS_WORKORDER_DIFF_DTL EWDD, ETS_WORKORDER EW \n"
			+ "         WHERE EWDD.WORKORDER_NO = EW.WORKORDER_NO \n"
			+ "           AND EWDD.VERIFY_RESULT = '系统数据为准' \n"
			+ "           AND NOT EXISTS \n"
			+ "				  (SELECT 1 \n"
			+ "                  FROM ETS_WORKORDER_DTL EWD \n"
			+ "                 WHERE EWDD.WORKORDER_NO = EWD.WORKORDER_NO \n"
			+ "                   AND EWDD.BARCODE = EWD.BARCODE) \n"
			+ "         UNION ALL \n"
			+ "        SELECT EWD.BARCODE \n"
			+ "          FROM ETS_WORKORDER_DTL EWD, ETS_WORKORDER EW \n"
			+ "         WHERE EWD.WORKORDER_NO = EW.WORKORDER_NO \n"
			+ "           AND EWD.ITEM_STATUS < 6 \n"
			+ "           AND NOT EXISTS \n" 
			+ "				  (SELECT 1 \n"
			+ "                  FROM ETS_WORKORDER_DIFF_DTL EWDD \n"
			+ "                 WHERE EWDD.WORKORDER_NO = EWD.WORKORDER_NO \n"
			+ "                   AND EWD.BARCODE = EWDD.BARCODE) \n"
			+ "         UNION ALL \n"
			+ "        SELECT AACL.BARCODE \n"
			+ // 盘点
			"            FROM AMS_ASSETS_CHK_LOG AACL \n"
			+ "         WHERE AACL.IS_EXIST = 'Y') E \n"
			+ "   WHERE E.BARCODE = EII.BARCODE) \n";
			}
            if(dto.getCheck().equals("Y")){
            	sqlStr+="AND EXISTS \n" +
            			"       (SELECT 'A' \n" +
                        "          FROM AMS_ASSETS_CHK_LOG ACL \n" +
                        "         WHERE ACL.BARCODE = EII.BARCODE \n" +
                        "           AND ACL.IS_EXIST = 'Y') \n";
            }
            sqlStr += " ORDER BY EO.WORKORDER_OBJECT_CODE, ESI.ITEM_NAME DESC \n";

			sqlArgs.add(dto.getItemCategory());
			sqlArgs.add(dto.getItemName());
			sqlArgs.add(dto.getItemName());
			sqlArgs.add(dto.getItemSpec());
			sqlArgs.add(dto.getItemSpec());
			sqlArgs.add(userAccount.getOrganizationId());
			sqlArgs.add(dto.getKey());
			sqlArgs.add(dto.getKey());
			sqlArgs.add(dto.getKey());
			sqlArgs.add(dto.getKey());
			sqlArgs.add(dto.getKey());
			sqlArgs.add(dto.getKey());
//			sqlArgs.add(dto.getWorkorderObjectName());
//			sqlArgs.add(dto.getWorkorderObjectName());
			sqlArgs.add(dto.getWorkorderObjectName());
			sqlArgs.add(dto.getWorkorderObjectName());
			sqlArgs.add(dto.getResponsibilityDept());
			sqlArgs.add(dto.getResponsibilityDept());
			sqlArgs.add(dto.getResponsibilityDept());
			sqlArgs.add(dto.getResponsibilityDept());
			sqlArgs.add(dto.getPrjId());
			sqlArgs.add(dto.getPrjId());
			sqlArgs.add(dto.getMaintainUser());
			sqlArgs.add(dto.getMaintainUser());
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
			
		return sqlModel;
	}
}
