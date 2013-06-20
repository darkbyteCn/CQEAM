package com.sino.sinoflow.model;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.sinoflow.dto.TaskLookUpDTO;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;

import java.util.ArrayList;
import java.util.List;

public class TaskLookUpModel extends BaseSQLProducer {

	public TaskLookUpModel(SfUserBaseDTO userAccount, TaskLookUpDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();

		List sqlArgs = new ArrayList();
		TaskLookUpDTO dto = (TaskLookUpDTO) dtoParameter;
		String sqlStr = "SELECT SMO.COMPANY_NAME, T.PROCEDURE_NAME, T.TASK_NAME, T.COUNT_NO,"
                        + " dbo.SEC_TO_DAY(CONVERT(INT,T.PROCESS_TIME), ?) PROCESS_TIME,"
                        + " dbo.SEC_TO_DAY(CONVERT(INT,T.LONGEST_TIME), ?) LONGEST_TIME,"
                        + " dbo.SEC_TO_DAY(CONVERT(INT,T.SHORTEST_TIME), ?) SHORTEST_TIME"
                        + " FROM ("
                        + " SELECT"
                        + "  SMD.ORG_ID,"
                        + "  SAA.SFACT_PROC_NAME PROCEDURE_NAME,"
                        + "  SAA.SFACT_TASK_NAME TASK_NAME,"
                        + "  COUNT(1) COUNT_NO,"
                        + "  AVG(SAA.SFACT_LAG_WORK + SAA.SFACT_COMPLETE_WORK_DURATION) PROCESS_TIME,"
                        + "  MAX(SAA.SFACT_LAG_WORK + SAA.SFACT_COMPLETE_WORK_DURATION) LONGEST_TIME,"
                        + "  MIN(SAA.SFACT_LAG_WORK + SAA.SFACT_COMPLETE_WORK_DURATION) SHORTEST_TIME"
                        + "  FROM "
						+ "  SF_ACT_ARCHIVE SAA, SINO_GROUP_MATCH SGM, SINO_MIS_DEPT SMD, SF_GROUP SG"
                        + "  WHERE "
                        + "  SG.GROUP_NAME = dbo.SFK_GET_MASK_GROUP(SAA.SFACT_TASK_GROUP, SAA.SFACT_HANDLER_GROUP, SAA.SFACT_PLUS_GROUP)"
                        + "  AND SGM.GROUP_ID = SG.GROUP_ID"
                        + "  AND SGM.DEPT_ID = SMD.DEPT_ID"
                        + "  AND SAA.SFACT_TASK_USERS <> 'SYSTEM'"
                        + "  AND (? <= 0 OR SMD.ORG_ID = ?)"
                        + "  AND (? = '' OR SAA.SFACT_PROC_NAME = ?)"
                        + "  AND (? = '' OR SAA.SFACT_TASK_NAME = ?)"
                        + "  AND (? = '' OR (CASE SAA.SFACT_FROM_DATE WHEN NULL THEN SAA.SFACT_CREATE_DT WHEN '' THEN SAA.SFACT_CREATE_DT ELSE SAA.SFACT_FROM_DATE END) >= ?)"
                        + "  AND (? = '' OR SAA.SFACT_CREATE_DT <= ?)"
                        + "  GROUP BY SMD.ORG_ID, SAA.SFACT_PROC_NAME, SAA.SFACT_TASK_NAME) T,"
                        + "  SINO_MIS_ORG SMO"
                        + "  WHERE SMO.ORG_ID = T.ORG_ID"
                        + "  ORDER BY SMO.DISPLAY_ORDER, SMO.ORG_ID";

        sqlArgs.add(dto.getHoursPerDay());
        sqlArgs.add(dto.getHoursPerDay());
        sqlArgs.add(dto.getHoursPerDay());
        if(dto.getCompanyName().equals("")) {
            sqlArgs.add(0);
            sqlArgs.add(0);
        } else {
            sqlArgs.add(Integer.parseInt(dto.getCompanyName()));
            sqlArgs.add(Integer.parseInt(dto.getCompanyName()));
        }
        sqlArgs.add(dto.getProcedureName());
        sqlArgs.add(dto.getProcedureName());
        sqlArgs.add(dto.getTaskName());
        sqlArgs.add(dto.getTaskName());
        sqlArgs.add(dto.getStartDate());
        sqlArgs.add(dto.getStartDate());
        sqlArgs.add(dto.getEndDate());
        sqlArgs.add(dto.getEndDate());
        sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);

		return sqlModel;
	}

}
