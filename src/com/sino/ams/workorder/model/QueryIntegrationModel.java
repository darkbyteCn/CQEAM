package com.sino.ams.workorder.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dto.EtsWorkorderDTO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * Created by IntelliJ IDEA.
 * User: V-jiachuanchuan
 * Date: 2007-11-5
 * Time: 16:36:21
 * Function: 综合查询工单SQL
 * To change this template use File | Settings | File Templates.
 */
public class QueryIntegrationModel extends BaseSQLProducer {
    EtsWorkorderDTO workorderDTO = null;
    SfUserDTO sfUser = null;

    public QueryIntegrationModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
        this.workorderDTO = (EtsWorkorderDTO) dtoParameter;
        sfUser = (SfUserDTO) userAccount;
    }

    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			String sqlStr = "";
			if (workorderDTO.getQueryType().equals(DictConstant.WOR_STATUS_BATCH)) {
				sqlStr = "SELECT EWB.WORKORDER_BATCH WORKORDER_BATCH,\n" +
						 "       EWB.WORKORDER_BATCH_NAME WORKORDER_BATCH_NAME,\n" +
						 "       EWB.REMARK REMARK,\n" +
						 "       EWB.CREATED_BY CREATED_BY,\n" +
						 "       dbo.APP_GET_USER_NAME(EWB.CREATED_BY) CREATE_USER,\n" +
						 "       EWB.STATUS STATUS\n" +
						 "  FROM ETS_WORKORDER_BATCH EWB\n" +
						 " WHERE EWB.WORKORDER_BATCH LIKE ISNULL(LTRIM(?), EWB.WORKORDER_BATCH)\n" +
						 "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EWB.WORKORDER_BATCH_NAME LIKE LTRIM(?))\n" +
						 "   AND EXISTS\n" +
						 " 		 (SELECT 1\n" +
						 "          FROM ETS_WORKORDER EW\n" +
						 "         WHERE EW.ORGANIZATION_ID = ISNULL(?, EW.ORGANIZATION_ID)\n" +
						 "           AND EW.WORKORDER_BATCH = EWB.WORKORDER_BATCH)\n" +
						 "   ORDER BY EWB.CREATION_DATE DESC ";
				sqlArgs.add(workorderDTO.getWorkorderBatch());
				sqlArgs.add(workorderDTO.getWorkorderBatchName());
				sqlArgs.add(workorderDTO.getWorkorderBatchName());
				if (sfUser.getOrganizationId() == SyBaseSQLUtil.NULL_INT_VALUE) {
					sqlArgs.add(null);
				} else {
					sqlArgs.add(sfUser.getOrganizationId());
				}
				//sqlArgs.add(sfUser.getOrganizationId());

			} else {
				sqlStr = "SELECT dbo.APP_GET_ORGNIZATION_NAME(EW.ORGANIZATION_ID) ORG_NAME,\n" +
						 "       EW.WORKORDER_NO,\n" +
						 "       dbo.APP_GET_FLEX_VALUE(EW.WORKORDER_FLAG, 'WORKORDER_STATUS') WORKORDER_FLAG_DESC,\n" +
						 "       EW.ATTRIBUTE4,\n" +
						 "       dbo.APP_GET_FLEX_VALUE(EW.WORKORDER_TYPE, 'WORKORDER_TYPE') WORKORDER_TYPE_DESC,\n" +
						 "       dbo.APP_GET_OBJECT_CODE(EW.WORKORDER_OBJECT_NO) WORKORDER_OBJECT_CODE,\n" +
						 "       dbo.APP_GET_OBJECT_NAME(EW.WORKORDER_OBJECT_NO) WORKORDER_OBJECT_NAME,\n" +
						 "       EW.START_DATE,\n" +
						 "       EW.IMPLEMENT_DAYS,\n" +
						 "       EPPA.SEGMENT1,\n" +
						 "       EPPA.NAME,\n" +
						 "       dbo.APP_GET_USER_NAME(EW.IMPLEMENT_BY) IMPLEMENT_USER,\n" +
						 "       EW.UPLOAD_DATE,\n" +
						 "       CASE EW.DIFFERENCE_REASON WHEN ''THEN '无' ELSE '有' END DIFF,\n" +
						 "		 CASE SIGN(DATEDIFF(dd,dbo.AWP_GET_DEADLINE_DATE(EW.START_DATE,EW.IMPLEMENT_DAYS),ISNULL(EW.UPLOAD_DATE, GETDATE()))) WHEN -1 THEN '否' ELSE '是' END OVERTIME," +
						 "       EW.ORGANIZATION_ID ,\n" +
						 "       EW.WORKORDER_BATCH \n" +
						 "  FROM ETS_WORKORDER EW," +
						 "       ETS_WORKORDER_BATCH EWB ," +
						 "       SF_USER SU ," +
						 "       ETS_OBJECT EO ," +
						 "       ETS_PA_PROJECTS_ALL EPPA " +
						 " WHERE EW.WORKORDER_BATCH=EWB.WORKORDER_BATCH" +
						 "       AND EW.WORKORDER_OBJECT_NO=EO.WORKORDER_OBJECT_NO" +
						 "       AND EW.IMPLEMENT_BY *= SU.USER_ID " +
                        "        AND ( " + SyBaseSQLUtil.isNull() + " OR EO.OBJECT_CATEGORY = ?)"+
						" AND EPPA.PROJECT_ID = EW.PRJ_ID "+
                            " AND EPPA.ORGANIZATION_ID = EW.ORGANIZATION_ID ";
                      	sqlArgs.add(workorderDTO.getObjectCategory());
						sqlArgs.add(workorderDTO.getObjectCategory());
				if (!workorderDTO.getQueryType().equals(DictConstant.WOR_STATUS_OVERTIME)) {
					if (workorderDTO.getQueryType().equals(DictConstant.WOR_STATUS_NEW)) {
						sqlStr = sqlStr +

								 "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EWB.WORKORDER_BATCH_NAME LIKE ?)\n" +
								 "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EW.WORKORDER_BATCH LIKE ?)\n" +
								 "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EW.WORKORDER_NO LIKE ?)\n" +
								 "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EPPA.SEGMENT1 LIKE ?)\n" +
								 "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.WORKORDER_OBJECT_CODE LIKE ?)\n" +
								 "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.WORKORDER_OBJECT_NAME LIKE ?)\n" +
								 "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EW.START_DATE >= ?)\n" +
								 "   AND (EW.WORKORDER_FLAG = ? OR EW.WORKORDER_FLAG = ?)\n" +
								 "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EW.WORKORDER_TYPE = ?)\n" +
								 "   AND ( " + SyBaseSQLUtil.isNull() + "  OR SU.USERNAME LIKE ?)\n" +
								 "   AND EW.ORGANIZATION_ID = ISNULL(?, EW.ORGANIZATION_ID) \n" +
								 "   ORDER BY EW.CREATION_DATE DESC";

						sqlArgs.add(workorderDTO.getWorkorderBatchName());
						sqlArgs.add(workorderDTO.getWorkorderBatchName());
						sqlArgs.add(workorderDTO.getWorkorderBatch());
						sqlArgs.add(workorderDTO.getWorkorderBatch());
						sqlArgs.add(workorderDTO.getWorkorderNo());
						sqlArgs.add(workorderDTO.getWorkorderNo());
						sqlArgs.add(workorderDTO.getPrjId());
						sqlArgs.add(workorderDTO.getPrjId());
						sqlArgs.add(workorderDTO.getWorkorderObjectCode());
						sqlArgs.add(workorderDTO.getWorkorderObjectCode());
						sqlArgs.add(workorderDTO.getWorkorderObjectName());
						sqlArgs.add(workorderDTO.getWorkorderObjectName());
						sqlArgs.add(workorderDTO.getStartDate());
						sqlArgs.add(workorderDTO.getStartDate());
						sqlArgs.add(DictConstant.WOR_STATUS_NEW);
						sqlArgs.add(DictConstant.WOR_STATUS_DEPLOY);
						sqlArgs.add(workorderDTO.getWorkorderType());
						sqlArgs.add(workorderDTO.getWorkorderType());
						sqlArgs.add(workorderDTO.getExecuteUserName());
						sqlArgs.add(workorderDTO.getExecuteUserName());
						if (sfUser.getOrganizationId() == SyBaseSQLUtil.NULL_INT_VALUE) {
							sqlArgs.add(null);
						} else {
							sqlArgs.add(sfUser.getOrganizationId());
						}
						//sqlArgs.add(workorderDTO.getOrganizationId());

					} else {
						sqlStr = sqlStr +

								 "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EWB.WORKORDER_BATCH_NAME LIKE ?)\n" +
								 "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EW.WORKORDER_BATCH LIKE ?)\n" +
								 "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EW.WORKORDER_NO LIKE ?)\n" +
								 "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EPPA.SEGMENT1 LIKE ?)\n" +
								 "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.WORKORDER_OBJECT_CODE LIKE ?)\n" +
								 "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.WORKORDER_OBJECT_NAME LIKE ?)\n" +
								 "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EW.START_DATE >= ?)\n" +
								 "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EW.WORKORDER_FLAG = ?)\n" +
								 "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EW.WORKORDER_TYPE = ?)\n" +
								 "   AND ( " + SyBaseSQLUtil.isNull() + "  OR dbo.APP_GET_USER_NAME(EW.IMPLEMENT_BY) LIKE ?)\n" +
								 "   AND  EW.ORGANIZATION_ID = ISNULL(?, EW.ORGANIZATION_ID) \n" +
								 "   ORDER BY EW.CREATION_DATE DESC";

						sqlArgs.add(workorderDTO.getWorkorderBatchName());
						sqlArgs.add(workorderDTO.getWorkorderBatchName());
						sqlArgs.add(workorderDTO.getWorkorderBatch());
						sqlArgs.add(workorderDTO.getWorkorderBatch());
						sqlArgs.add(workorderDTO.getWorkorderNo());
						sqlArgs.add(workorderDTO.getWorkorderNo());
						sqlArgs.add(workorderDTO.getPrjId());
						sqlArgs.add(workorderDTO.getPrjId());
						sqlArgs.add(workorderDTO.getWorkorderObjectCode());
						sqlArgs.add(workorderDTO.getWorkorderObjectCode());
						sqlArgs.add(workorderDTO.getWorkorderObjectName());
						sqlArgs.add(workorderDTO.getWorkorderObjectName());
						sqlArgs.add(workorderDTO.getStartDate());
						sqlArgs.add(workorderDTO.getStartDate());
						if (!workorderDTO.getQueryType().equals(DictConstant.WOR_STATUS_INTEGAZATION)) {
							sqlArgs.add(workorderDTO.getQueryType());
							sqlArgs.add(workorderDTO.getQueryType());
						} else if (workorderDTO.getQueryType().equals(DictConstant.WOR_STATUS_INTEGAZATION)) {
							sqlArgs.add(workorderDTO.getWorkorderFlag());
							sqlArgs.add(workorderDTO.getWorkorderFlag());
						}
						sqlArgs.add(workorderDTO.getWorkorderType());
						sqlArgs.add(workorderDTO.getWorkorderType());
						sqlArgs.add(workorderDTO.getExecuteUserName());
						sqlArgs.add(workorderDTO.getExecuteUserName());
						if (sfUser.getOrganizationId() == SyBaseSQLUtil.NULL_INT_VALUE) {
							sqlArgs.add(null);
						} else {
							//sqlArgs.add(sfUser.getOrganizationId());
							sqlArgs.add(workorderDTO.getOrganizationId());
						}
						//sqlArgs.add(workorderDTO.getOrganizationId());
					}

				} else if (workorderDTO.getQueryType().equals(DictConstant.WOR_STATUS_OVERTIME)) {
					sqlStr = sqlStr +
							 "   AND (((EW.UPLOAD_DATE " + SyBaseSQLUtil.isNullNoParam() + "  AND\n" +
							 "       GETDATE() >\n" +
							 "       dbo.AWP_GET_DEADLINE_DATE(EW.START_DATE,\n" +
							 "                                             EW.IMPLEMENT_DAYS)))\n" +
							 "    	 OR ( " + SyBaseSQLUtil.isNotNull("EW.UPLOAD_DATE") + "  AND\n" +
							 "       EW.UPLOAD_DATE >\n" +
							 "       dbo.AWP_GET_DEADLINE_DATE(EW.START_DATE,\n" +
							 "                                            EW.IMPLEMENT_DAYS)))\n" +
							 "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EWB.WORKORDER_BATCH_NAME LIKE ?)\n" +
							 "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EPPA.SEGMENT1 LIKE ?)\n" +
							 "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EW.WORKORDER_BATCH LIKE ?)\n" +
							 "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EW.WORKORDER_NO LIKE ?)\n" +
							 "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.WORKORDER_OBJECT_CODE LIKE ?)\n" +
							 "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.WORKORDER_OBJECT_NAME LIKE ?)\n" +
							 "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EW.START_DATE >= ?)\n" +
							 "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EW.WORKORDER_FLAG = ?)\n" +
							 "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EW.WORKORDER_TYPE = ?)\n" +
							 "   AND ( " + SyBaseSQLUtil.isNull() + "  OR SU.USERNAME LIKE ?)\n" +
							 "   AND EW.ORGANIZATION_ID = ISNULL(?, EW.ORGANIZATION_ID) \n" +
							 "   ORDER BY EW.CREATION_DATE DESC";

					sqlArgs.add(workorderDTO.getWorkorderBatchName());
					sqlArgs.add(workorderDTO.getWorkorderBatchName());
					sqlArgs.add(workorderDTO.getWorkorderBatch());
					sqlArgs.add(workorderDTO.getWorkorderBatch());
					sqlArgs.add(workorderDTO.getWorkorderNo());
					sqlArgs.add(workorderDTO.getWorkorderNo());
					sqlArgs.add(workorderDTO.getWorkorderObjectCode());
					sqlArgs.add(workorderDTO.getWorkorderObjectCode());
					sqlArgs.add(workorderDTO.getWorkorderObjectName());
					sqlArgs.add(workorderDTO.getWorkorderObjectName());
					sqlArgs.add(workorderDTO.getStartDate());
					sqlArgs.add(workorderDTO.getStartDate());
					sqlArgs.add(workorderDTO.getPrjId());
					sqlArgs.add(workorderDTO.getPrjId());
					sqlArgs.add(workorderDTO.getStartDate());
					sqlArgs.add(workorderDTO.getWorkorderFlag());
					sqlArgs.add(workorderDTO.getWorkorderFlag());
					sqlArgs.add(workorderDTO.getWorkorderType());
					sqlArgs.add(workorderDTO.getWorkorderType());
					sqlArgs.add(workorderDTO.getExecuteUserName());
					sqlArgs.add(workorderDTO.getExecuteUserName());
					if (sfUser.getOrganizationId() == SyBaseSQLUtil.NULL_INT_VALUE) {
						sqlArgs.add(null);
					} else {
						sqlArgs.add(sfUser.getOrganizationId());
					}
					//sqlArgs.add(workorderDTO.getOrganizationId());
				}
			}
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
        return sqlModel;
    }


    public SQLModel getQueryBatacDtlModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT EWB.WORKORDER_BATCH,\n" +
                "       EWB.WORKORDER_BATCH_NAME,\n" +
                "       EW.WORKORDER_NO,\n" +
                "       dbo.APP_GET_OBJECT_CODE(EW.WORKORDER_OBJECT_NO) WORKORDER_OBJECT_CODE,\n" +
                "       dbo.APP_GET_OBJECT_NAME(EW.WORKORDER_OBJECT_NO) WORKORDER_OBJECT_NAME,\n" +
                "       EW.ATTRIBUTE4,\n" +
                "       dbo.APP_GET_FLEX_VALUE(EW.WORKORDER_TYPE, 'WORKORDER_TYPE') WORKORDER_TYPE_DESC,\n" +
                "       dbo.APP_GET_FLEX_VALUE(EW.WORKORDER_FLAG, 'WORKORDER_STATUS') WORKORDER_FLAG_DESC,\n" +
                "       EW.START_DATE,\n" +
                "       EW.IMPLEMENT_DAYS,\n" +
                "       dbo.APP_GET_USER_NAME(EW.IMPLEMENT_BY) IMPLEMENT_USER,\n" +
                "       EW.REMARK\n" +
                "  FROM ETS_WORKORDER EW, ETS_WORKORDER_BATCH EWB\n" +
                " WHERE EW.WORKORDER_BATCH=EWB.WORKORDER_BATCH\n" +
                "   AND EW.ORGANIZATION_ID = ISNULL(?, EW.ORGANIZATION_ID)\n" +
                "   AND EWB.WORKORDER_BATCH = ?";

		if (sfUser.getOrganizationId() == SyBaseSQLUtil.NULL_INT_VALUE) {
			sqlArgs.add(null);
		} else {
			sqlArgs.add(sfUser.getOrganizationId());
		}
		//sqlArgs.add(workorderDTO.getOrganizationId());
        sqlArgs.add(workorderDTO.getWorkorderBatch());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
