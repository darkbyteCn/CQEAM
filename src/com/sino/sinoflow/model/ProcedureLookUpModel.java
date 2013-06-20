package com.sino.sinoflow.model;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.sinoflow.dto.ProcedureLookUpDTO;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;
import com.sino.framework.sql.BaseSQLProducer;

import java.util.ArrayList;
import java.util.List;

public class ProcedureLookUpModel extends BaseSQLProducer {

	public ProcedureLookUpModel(SfUserBaseDTO userAccount, ProcedureLookUpDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();

		List sqlArgs = new ArrayList();
		ProcedureLookUpDTO dto = (ProcedureLookUpDTO) dtoParameter;
		String sqlStr = "SELECT SMO.COMPANY_NAME, T.PROCEDURE_NAME, T.COUNT_NO,"
                        + " dbo.SEC_TO_DAY(CONVERT(INT,T.PROCESS_TIME), ?) PROCESS_TIME,"
                        + " dbo.SEC_TO_DAY(CONVERT(INT,T.LONGEST_TIME), ?) LONGEST_TIME,"
                        + " dbo.SEC_TO_DAY(CONVERT(INT,T.SHORTEST_TIME), ?) SHORTEST_TIME"
                        + " FROM"
                        + " (SELECT"
                        + "  SMD.ORG_ID,"
						+ "  SAA.SFACT_PROC_NAME PROCEDURE_NAME,"
                        + "  COUNT(1) COUNT_NO,"
                        + "  AVG(SAA.SFACT_PRJ_FILE_ID) PROCESS_TIME,"
                        + "  MAX(SAA.SFACT_PRJ_FILE_ID) LONGEST_TIME,"
                        + "  MIN(SAA.SFACT_PRJ_FILE_ID) SHORTEST_TIME"
                        + "  FROM "
						+ "  SF_ACT_ARCHIVE SAA, SF_GROUP SG, SINO_GROUP_MATCH SGM, SINO_MIS_DEPT SMD"
                        + "  WHERE "
                        + "  SAA.SFACT_NEXT_TASK_NAME = 'STOP'"
                        + "  AND SG.GROUP_NAME = SAA.SFACT_HANDLER_GROUP"
                        + "  AND SGM.GROUP_ID = SG.GROUP_ID"
                        + "  AND SGM.DEPT_ID = SMD.DEPT_ID"
                        + "  AND (? <= 0 OR SMD.ORG_ID = ?)"
                        + "  AND (? = '' OR SAA.SFACT_PROC_NAME LIKE ?)"
                        + "  AND (? = '' OR SAA.SFACT_CREATE_DT >= ?)"
                        + "  AND (? = '' OR SAA.SFACT_CREATE_DT <= ?)"
                        + "  GROUP BY SMD.ORG_ID, SAA.SFACT_PROC_NAME) T, SINO_MIS_ORG SMO"
                        + "  WHERE T.ORG_ID = SMO.ORG_ID"
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
        sqlArgs.add("%" + dto.getProcedureName() + "%");
        sqlArgs.add(dto.getStartDate());
        sqlArgs.add(dto.getStartDate());
        sqlArgs.add(dto.getEndDate());
        sqlArgs.add(dto.getEndDate());
        sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);

		return sqlModel;
	}

}
