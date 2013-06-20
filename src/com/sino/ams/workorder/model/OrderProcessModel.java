package com.sino.ams.workorder.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.workorder.dto.EtsWorkorderDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * User: V-jiachuanchuan
 * Date: 2007-11-5
 * Time: 16:19:33
 * Function: 查询工单进度SQL
 */
public class OrderProcessModel extends BaseSQLProducer {
     EtsWorkorderDTO workorderDTO=null;

    public OrderProcessModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
        this.workorderDTO=(EtsWorkorderDTO)dtoParameter;
    }

    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
        String sqlStr = "SELECT EP.BATCH_NO,\n" +
                "       EWB.WORKORDER_BATCH_NAME,\n" +
                "       EP.WORKORDER_NO,\n" +
                "       EW.WORKORDER_TYPE,\n" +
                "       dbo.APP_GET_FLEX_VALUE(EW.WORKORDER_TYPE, 'WORKORDER_TYPE') WORKORDER_TYPE_DESC,\n" +
                "       EW.WORKORDER_OBJECT_NO,\n" +
                "       dbo.APP_GET_OBJECT_CODE(EW.WORKORDER_OBJECT_NO) WORKORDER_OBJECT_CODE,\n" +
                "       dbo.APP_GET_OBJECT_NAME(EW.WORKORDER_OBJECT_NO) WORKORDER_OBJECT_LOCATION,\n" +
                "       EP.PROCESS1,\n" +
                "       EP.PROCESS2,\n" +
                "       EP.PROCESS3,\n" +
                "       EP.PROCESS4,\n" +
                "       EP.PROCESS5,\n" +
                "       EP.PROCESS6,\n" +
                "       EP.PROCESS7\n" +
                "  FROM ETS_PROCESSBAR EP, ETS_WORKORDER EW, ETS_WORKORDER_BATCH EWB\n" +
                " WHERE EP.WORKORDER_NO = EW.WORKORDER_NO\n" +
                "   AND EP.BATCH_NO = EWB.WORKORDER_BATCH\n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EWB.WORKORDER_BATCH LIKE ?)\n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EWB.WORKORDER_BATCH_NAME LIKE ?)\n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EW.WORKORDER_NO LIKE ?)\n" +
                "   AND EW.ORGANIZATION_ID = ISNULL(?,EW.ORGANIZATION_ID)" +
                "   ORDER BY EW.CREATION_DATE DESC";

        sqlArgs.add(workorderDTO.getWorkorderBatch());
        sqlArgs.add(workorderDTO.getWorkorderBatch());
        sqlArgs.add(workorderDTO.getWorkorderBatchName());
        sqlArgs.add(workorderDTO.getWorkorderBatchName());
        sqlArgs.add(workorderDTO.getWorkorderNo());
        sqlArgs.add(workorderDTO.getWorkorderNo());
        
        if (workorderDTO.getOrganizationId() == SyBaseSQLUtil.NULL_INT_VALUE) {
        	sqlArgs.add(null);
        } else {
        	sqlArgs.add(workorderDTO.getOrganizationId());
        }
        //sqlArgs.add(workorderDTO.getOrganizationId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
    }

}
