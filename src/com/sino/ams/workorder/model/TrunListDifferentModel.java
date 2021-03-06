package com.sino.ams.workorder.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dto.EtsWorkorderDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * Author:		李轶
 * Date: 2009-6-3
 * Time: 10:53:55
 * Function: 转资清单差异Model
 */
public class TrunListDifferentModel extends BaseSQLProducer {
    EtsWorkorderDTO workorderDTO = null;
    SfUserDTO sfUser = null;

    public TrunListDifferentModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
        this.workorderDTO = (EtsWorkorderDTO) dtoParameter;
        sfUser = (SfUserDTO) userAccount;
    }

    /**
     * Function:		获取转资清单差异查询的SQLModel
     * @author 			李轶
     * @return			SQLModel
     * @date			2009-6-3
     * @time			10:55:55
     */
    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
			
		String sqlStr = "SELECT EPPA.SEGMENT1,\n" +
                "       EPPA.NAME,\n" +
                "       dbo.APP_GET_ORGNIZATION_NAME(EW.ORGANIZATION_ID) ORG_NAME,\n" +
                "       dbo.APP_GET_OBJECT_CODE(EW.WORKORDER_OBJECT_NO) WORKORDER_OBJECT_CODE,\n" +
                "       dbo.APP_GET_OBJECT_NAME(EW.WORKORDER_OBJECT_NO) WORKORDER_OBJECT_NAME,\n" +
                "       EW.DIFFERENCE_REASON,\n" +
                "       EW.WORKORDER_NO,\n" +
                "       dbo.APP_GET_USER_NAME(EW.IMPLEMENT_BY) IMPLEMENT_USER,\n" +
                "       dbo.APP_GET_USER_NAME(EW.CHECKOVER_BY) CHECKOVER_USER,\n" +
                "       EWDD.BARCODE,\n" +
                "       ESI.ITEM_NAME,\n" +
                "       ESI.ITEM_SPEC,\n" +
                "       dbo.APP_GET_FLEX_VALUE(CONVERT(VARCHAR,EWDD.ITEM_STATUS), 'ORDER_ITEM_STATUS') ITEM_STATUS_NAME,\n" +
                "       dbo.APP_GET_FLEX_VALUE(CONVERT(VARCHAR,EWDD.ITEM_STATUS), 'ORDER_ITEM_STATUS') SCAN_STATUS_NAME,\n" +
                "       '1' ITEM_QTY,\n" +
                "       dbo.APP_GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY_DESC,\n" +
                "       AMD.DEPT_NAME,\n" +
                "       AMP.USER_NAME"
			
			+			"   FROM ETS_WORKORDER          EW,\n"
			+			"		 ETS_WORKORDER_BATCH    EWB,\n"
			+			"		 SF_USER                SU,\n"
			+			"		 ETS_OBJECT             EO,\n"
			+			"		 ETS_PA_PROJECTS_ALL    EPPA,\n"
			+			"		 ETS_WORKORDER_DIFF_DTL EWDD,\n"
			+			"		 ETS_SYSTEM_ITEM        ESI,\n"
			+			"		 ETS_ITEM_INFO          EII,\n"
			+			"		 AMS_MIS_DEPT           AMD,\n"
			+			"		 AMS_MIS_EMPLOYEE       AMP\n"
			+			"  WHERE EW.ARCHFLAG = 1\n"
			+			"	 AND EW.WORKORDER_TYPE = '18'\n"
			+			"	 AND EW.WORKORDER_BATCH = EWB.WORKORDER_BATCH\n"
			+	 		"	 AND EW.WORKORDER_OBJECT_NO = EO.WORKORDER_OBJECT_NO\n"
			+			"	 AND EWDD.BARCODE = EII.BARCODE\n"
			+			" 	 AND NOT EXISTS\n"
			+			"			(SELECT '1' \n"
			+			"			   FROM (SELECT EWDP.BARCODE \n" 
			+			"					   FROM ETS_WORKORDER_DIFF_PROCESS EWDP\n" 
			+			"			  		  WHERE EWDP.WORKORDER_TYPE = '18') TMP\n"
			+			"			  WHERE EWDD.BARCODE = TMP.BARCODE) \n" 
			+			"	 AND EWDD.WORKORDER_NO = EW.WORKORDER_NO\n"
			+			"	 AND EWDD.ITEM_CODE *= ESI.ITEM_CODE\n";
			
	        if( !StrUtil.isEmpty( workorderDTO.getDeptCode() ) ){
	        	sqlStr += "   AND EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE \n" ;
	        }else{
	        	sqlStr += "   AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE \n";
	        }
	        if( !StrUtil.isEmpty( workorderDTO.getResponsibilityUser() ) ){
	        	sqlStr += "   AND         EII.RESPONSIBILITY_USER = AMP.EMPLOYEE_ID \n";
	        }else{
	        	sqlStr += "   AND         EII.RESPONSIBILITY_USER *= AMP.EMPLOYEE_ID \n";
	        }
	        
	        sqlStr = sqlStr
			//+			"	 AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE\n"
			//+			"	 AND EII.RESPONSIBILITY_USER *= AMP.EMPLOYEE_ID\n"
			
			+			"	 AND EW.PRJ_ID = EPPA.PROJECT_ID\n"
			+			"	 AND EW.IMPLEMENT_BY = SU.USER_ID\n"
			+			"	 AND ( " + SyBaseSQLUtil.isNull() + "  OR EW.WORKORDER_NO LIKE ?)\n"			
			+			"	 AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.WORKORDER_OBJECT_CODE = ?)\n"				
			+			" 	 AND ( " + SyBaseSQLUtil.isNull() + "  OR SU.USERNAME LIKE ?)\n"
			+			"	 AND ( ? IS NULL OR ? = -1 OR EW.ORGANIZATION_ID = ?)\n"
			+			"	 AND ( ? IS NULL OR ? = '' OR EPPA.PROJECT_ID = ?)\n" ;
			
	        if( !StrUtil.isEmpty( workorderDTO.getDeptCode() ) ){
	        	sqlStr += "   AND  AMD.DEPT_CODE LIKE ? \n" ;
	        }
	        
	        if( !StrUtil.isEmpty( workorderDTO.getResponsibilityUser() ) ){
	        	sqlStr += "   AND  AMP.USER_NAME LIKE ? \n" ;
	        }
	        
	        sqlStr = sqlStr
			+			"	 ORDER BY EPPA.SEGMENT1, EW.WORKORDER_NO, EWDD.BARCODE\n";
			
		sqlArgs.add(workorderDTO.getWorkorderBatch());
		sqlArgs.add(workorderDTO.getWorkorderBatch());
		
		sqlArgs.add(workorderDTO.getWorkorderObjectCode());
		sqlArgs.add(workorderDTO.getWorkorderObjectCode());
		
		sqlArgs.add(workorderDTO.getExecuteUserName());
		sqlArgs.add(workorderDTO.getExecuteUserName());
		sqlArgs.add(workorderDTO.getOrganizationId());
		sqlArgs.add(workorderDTO.getOrganizationId());
		sqlArgs.add(workorderDTO.getOrganizationId());
		sqlArgs.add(workorderDTO.getPrjId());
		sqlArgs.add(workorderDTO.getPrjId());
		sqlArgs.add(workorderDTO.getPrjId());	
		
        if( !StrUtil.isEmpty( workorderDTO.getDeptCode() ) ){
        	sqlArgs.add(workorderDTO.getDeptCode());
        }
        
        if( !StrUtil.isEmpty( workorderDTO.getResponsibilityUser() ) ){
        	sqlArgs.add(workorderDTO.getResponsibilityUser());
        }
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		
        return sqlModel;
    }
    
	/**
	 * 功能：框架自动生成数据插入SQLModel，请根据实际需要修改。
	 *
	 * @return SQLModel 返回数据插入用SQLModel
	 */
	public SQLModel getDataCreateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "INSERT INTO ETS_WORKORDER_DIFF_PROCESS\n" +
									        "  (PROCESS_ID,\n" +
									        "   BARCODE,\n" +
									        "   DIFF_PROCESS_DESC,\n" +
									        "   WORKORDER_TYPE,\n" +
									        "   CREATED_BY,\n" +
									        "   CREATION_DATE)\n" +
									        " VALUES\n" +
									        "  ( NEWID() , ?, ?, ?, ?, GETDATE())";
		sqlArgs.add(workorderDTO.getBarcode());
		sqlArgs.add(workorderDTO.getDiffProcessDesc());
		sqlArgs.add(workorderDTO.getWorkorderType());
		sqlArgs.add(sfUser.getUserId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
