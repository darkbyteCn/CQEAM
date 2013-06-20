package com.sino.ams.workorder.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dto.EtsWorkorderDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * Author:		李轶
 * Date: 2009-6-3
 * Time: 10:53:55
 * Function: 巡检工单清单差异Model
 */
public class PatrolOrderDifferentModel extends BaseSQLProducer {
    EtsWorkorderDTO workorderDTO = null;
    SfUserDTO sfUser = null;

    public PatrolOrderDifferentModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
        this.workorderDTO = (EtsWorkorderDTO) dtoParameter;
        sfUser = (SfUserDTO) userAccount;
    }

    /**
     * Function:		获取巡检工单清单差异查询的SQLModel
     * @author 			李轶
     * @return			SQLModel
     * @date			2009-6-3
     * @time			10:55:55
     */
    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
			
		String sqlStr = " SELECT AMS_PUB_PKG.GET_ORGNIZATION_NAME(EW.ORGANIZATION_ID) ORG_NAME,\n"
			+			"		 AMS_PUB_PKG.GET_OBJECT_CODE(EW.WORKORDER_OBJECT_NO) WORKORDER_OBJECT_CODE,\n"
			+			"		 AMS_PUB_PKG.GET_OBJECT_NAME(EW.WORKORDER_OBJECT_NO) WORKORDER_OBJECT_NAME,\n"
			+			"		 EW.DIFFERENCE_REASON,\n"
			+			"		 EW.WORKORDER_BATCH,\n"	
			+			"		 EW.WORKORDER_NO,\n"
			+			"		 AMS_PUB_PKG.GET_USER_NAME(EW.IMPLEMENT_BY) IMPLEMENT_USER,\n"
			+			"		 AMS_PUB_PKG.GET_USER_NAME(EW.CHECKOVER_BY) CHECKOVER_USER,\n"
			+			"	  	 EWDD.BARCODE,\n"
			+			"		 ESI.ITEM_NAME,\n"
			+			"		 ESI.ITEM_SPEC,\n"
			+			"		 AMS_PUB_PKG.GET_FLEX_VALUE(EWDD.ITEM_STATUS, 'ORDER_ITEM_STATUS') ITEM_STATUS_NAME,\n"
			+			"		 AMS_PUB_PKG.GET_FLEX_VALUE(EWDD.SCAN_STATUS, 'ORDER_ITEM_STATUS') SCAN_STATUS_NAME,\n"			
			+			"		 '1' ITEM_QTY,\n"
			+			"		 AMS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY_DESC,\n"
			+			"		 AMD.DEPT_NAME,\n"
			+			"		 AMP.USER_NAME,\n"
			+			"		 EPPA.SEGMENT1,\n"
			+			"		 EPPA.NAME,\n"
			+			"		 EW.CHECKOVER_DATE\n"
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
			+			"  WHERE EW.ARCHFLAG = 1\n"				//表示已归档工单
			+			"	 AND EW.WORKORDER_TYPE = 12\n"		//巡检工单
			+			"	 AND EW.WORKORDER_BATCH = EWB.WORKORDER_BATCH\n"
			+	 		"	 AND EW.WORKORDER_OBJECT_NO = EO.WORKORDER_OBJECT_NO\n"
			+			"	 AND EWDD.BARCODE = EII.BARCODE\n"
			+			"	 AND EWDD.WORKORDER_NO *= EW.WORKORDER_NO\n"
			+			"	 AND EWDD.ITEM_CODE *= ESI.ITEM_CODE\n"
			+			"	 AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE\n"
			+			"	 AND EII.RESPONSIBILITY_USER *= AMP.EMPLOYEE_ID\n"
			+			"	 AND EW.PRJ_ID *= EPPA.PROJECT_ID\n"
			+			"	 AND EW.IMPLEMENT_BY *= SU.USER_ID\n"
			+			"	 AND ( " + SyBaseSQLUtil.isNull() + "  OR EW.WORKORDER_BATCH LIKE ?)\n"			
			+			"	 AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.WORKORDER_OBJECT_CODE = ?)\n"				
			+			" 	 AND ( " + SyBaseSQLUtil.isNull() + "  OR SU.USERNAME LIKE ?)\n"
			+			"	 AND ( " + SyBaseSQLUtil.isNull() + "  OR EW.ORGANIZATION_ID = ?)\n"
			+			"	 AND ( " + SyBaseSQLUtil.isNull() + "  OR EPPA.PROJECT_ID = ?)\n"
			+			"	 AND ( " + SyBaseSQLUtil.isNull() + "  OR EW.WORKORDER_NO LIKE ?)\n"
			+			"	 AND ( " + SyBaseSQLUtil.isNull() + "  OR EW.CHECKOVER_DATE >= ?)\n"
			+			"	 AND ( " + SyBaseSQLUtil.isNull() + "  OR EW.CHECKOVER_DATE <= TRUNC(? + 1))\n"
			+			"	 ORDER BY EPPA.SEGMENT1, EW.WORKORDER_NO, EWDD.BARCODE\n";
		try {	
			sqlArgs.add(workorderDTO.getWorkorderBatch());
			sqlArgs.add(workorderDTO.getWorkorderBatch());
			
			sqlArgs.add(workorderDTO.getWorkorderObjectCode());
			sqlArgs.add(workorderDTO.getWorkorderObjectCode());
			
			sqlArgs.add(workorderDTO.getExecuteUserName());
			sqlArgs.add(workorderDTO.getExecuteUserName());
			sqlArgs.add(workorderDTO.getOrganizationId());
			sqlArgs.add(workorderDTO.getOrganizationId());
			
			sqlArgs.add(workorderDTO.getPrjId());
			sqlArgs.add(workorderDTO.getPrjId());
			
			sqlArgs.add(workorderDTO.getWorkorderNo());
			sqlArgs.add(workorderDTO.getWorkorderNo());
		
			sqlArgs.add(workorderDTO.getStartDate());
			sqlArgs.add(workorderDTO.getStartDate());
			sqlArgs.add(workorderDTO.getEndDate());
			sqlArgs.add(workorderDTO.getEndDate());
		} catch (CalendarException e) {
			e.printStackTrace();
		}
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		
        return sqlModel;
    }
}
