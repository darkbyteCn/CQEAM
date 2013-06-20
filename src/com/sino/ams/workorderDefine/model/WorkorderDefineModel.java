package com.sino.ams.workorderDefine.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.StrUtil;
import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorderDefine.dto.WorkorderDefineDTO;

public class WorkorderDefineModel extends AMSSQLProducer {
    private SfUserDTO user = null;

    public WorkorderDefineModel(SfUserDTO userAccount,
                                WorkorderDefineDTO dtoParameter) {
        super(userAccount, dtoParameter);
        this.user = (SfUserDTO) userAccount;
    }

    /**
     * 巡检自定义查询所有数据
     */
    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        WorkorderDefineDTO dto = (WorkorderDefineDTO) dtoParameter;
        String sqlStr = "";
//		if (dto.getCostCenterCode().equals("")) {
        sqlStr = "SELECT EWD.WORKORDER_DEFINE_ID,\n"
                + "       EOCM.COMPANY,\n"
                + "       EFV.VALUE OBJECT_CATEGORY,\n"
                + "		  AC2.COUNTY_NAME CITY_NAME,\n"
                + "       AC.COUNTY_NAME COUNTY_NAME,\n"
                + "       EWD.COST_CENTER_CODE COST_CENTER_CODE_NAME,\n"
                + "       SU.USERNAME IMPLEMENT_BY,\n"
                + "       SU2.USERNAME CHECKOVER_BY,\n"
                + "       EFV2.VALUE WORKORDER_CYCLE,\n"
                + "       CASE EWD.ENABLED WHEN 'Y' THEN '是' ELSE '否' END ENABLED,\n"
                + "       EWD.WORKORDER_EXEC_DATE,\n"
                + "       EWD.CREATION_DATE,\n"
                + "       SU3.CREATED_BY\n"
                + "  FROM ETS_WORKORDER_DEFINE EWD,\n"
                + "       ETS_OU_CITY_MAP      EOCM,\n"
                + "       ETS_FLEX_VALUE_SET   EFVS,\n"
                + "       ETS_FLEX_VALUES      EFV,\n"
                + "       AMS_COUNTY           AC,\n"
                + "		  AMS_COUNTY 		   AC2,\n"
                + "       SF_USER              SU,\n"
                + "       SF_USER              SU2,\n"
                + "       SF_USER              SU3,\n"
                + "       SF_USER              SU4,\n"
                + "       ETS_FLEX_VALUE_SET   EFVS2,\n"
                + "       ETS_FLEX_VALUES      EFV2\n"
                + " WHERE EWD.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n"
                + "	  AND EWD.ORGANIZATION_ID = ? \n"
                + "   AND EFVS.FLEX_VALUE_SET_ID = EFV.FLEX_VALUE_SET_ID\n"
                + "   AND EFVS.CODE = 'OBJECT_CATEGORY'\n"
                + "   AND EWD.OBJECT_CATEGORY = EFV.CODE\n"
                + "   AND EWD.COUNTY = AC.COUNTY_CODE\n"
                + "   AND EWD.CITY = AC2.COUNTY_CODE\n"
                + "   AND EWD.IMPLEMENT_BY *= SU.USER_ID\n"
                + "   AND EWD.CHECKOVER_BY *= SU2.USER_ID\n"
                + "   AND EWD.CREATED_BY *= SU3.USER_ID\n"
                + "   AND EWD.LAST_UPDATE_BY *= SU4.USER_ID\n"
                + "   AND EFVS2.FLEX_VALUE_SET_ID = EFV2.FLEX_VALUE_SET_ID\n"
                + "   AND EFVS2.CODE = 'WORKORDER_CYCLE'\n"
                + "   AND EWD.WORKORDER_CYCLE = CONVERT(INT,EFV2.CODE)\n"
                + "	  AND EWD.OBJECT_CATEGORY = dbo.NVL(?,EWD.OBJECT_CATEGORY) \n"
                + "   AND EWD.COST_CENTER_CODE = dbo.NVL(?,EWD.COST_CENTER_CODE) \n";
        sqlArgs.add(userAccount.getOrganizationId());
        sqlArgs.add(dto.getObjectCategory());
        sqlArgs.add(dto.getCostCenterCode());
//		} else {
//			sqlStr = "SELECT EWD.WORKORDER_DEFINE_ID,\n"
//				+ "       EOCM.COMPANY,\n"
//				+ "       EFV.VALUE OBJECT_CATEGORY,\n"
//				+ "       AC.COUNTY_NAME COUNTY_NAME,\n"
//				+ "       EC.COUNTY_NAME COST_CENTER_CODE_NAME,\n"
//				+ "       SU.USERNAME IMPLEMENT_BY,\n"
//				+ "       SU2.USERNAME CHECKOVER_BY,\n"
//				+ "       EFV2.VALUE WORKORDER_CYCLE,\n"
//				+ "       CASE EWD.ENABLED WHEN 'Y' THEN '是' ELSE '否' END ENABLED,\n"
//				+ "       EWD.WORKORDER_EXEC_DATE,\n"
//				+ "       EWD.CREATION_DATE,\n"
//				+ "       SU3.CREATED_BY\n"
//				+ "  FROM ETS_WORKORDER_DEFINE EWD,\n"
//				+ "       ETS_OU_CITY_MAP      EOCM,\n"
//				+ "       ETS_FLEX_VALUE_SET   EFVS,\n"
//				+ "       ETS_FLEX_VALUES      EFV,\n"
//				+ "       AMS_COUNTY           AC,\n"
//				+ "       ETS_COUNTY           EC,\n"
//				+ "       SF_USER              SU,\n"
//				+ "       SF_USER              SU2,\n"
//				+ "       SF_USER              SU3,\n"
//				+ "       SF_USER              SU4,\n"
//				+ "       ETS_FLEX_VALUE_SET   EFVS2,\n"
//				+ "       ETS_FLEX_VALUES      EFV2\n"
//				+ " WHERE EWD.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n"
//				+ "	AND EWD.ORGANIZATION_ID = ? \n"
//				+ "   AND EFVS.FLEX_VALUE_SET_ID = EFV.FLEX_VALUE_SET_ID\n"
//				+ "   AND EFVS.CODE = 'OBJECT_CATEGORY'\n"
//				+ "   AND EWD.OBJECT_CATEGORY = EFV.CODE\n"
//				+ "   AND EWD.COUNTY = AC.COUNTY_CODE\n"
//				+ "   AND EWD.COST_CENTER_CODE = EC.COUNTY_CODE\n"
//				+ "   AND EWD.IMPLEMENT_BY *= SU.USER_ID\n"
//				+ "   AND EWD.CHECKOVER_BY *= SU2.USER_ID\n"
//				+ "   AND EWD.CREATED_BY *= SU3.USER_ID\n"
//				+ "   AND EWD.LAST_UPDATE_BY *= SU4.USER_ID\n"
//				+ "   AND EFVS2.FLEX_VALUE_SET_ID = EFV2.FLEX_VALUE_SET_ID\n"
//				+ "   AND EFVS2.CODE = 'WORKORDER_CYCLE'\n"
//				+ "   AND EWD.WORKORDER_CYCLE = CONVERT(INT,EFV2.CODE)\n"
//				+ "   AND EC.COMPANY_CODE = ? \n"
//				+ "	AND EWD.OBJECT_CATEGORY = dbo.NVL(?,EWD.OBJECT_CATEGORY) \n"
//				+ "   AND EWD.COST_CENTER_CODE = dbo.NVL(?,EWD.COST_CENTER_CODE) \n";
//			sqlArgs.add(userAccount.getOrganizationId());
//			sqlArgs.add(userAccount.getCompanyCode());
//			sqlArgs.add(dto.getObjectCategory());
//			sqlArgs.add(dto.getCostCenterCode());
//		}

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 巡检自定义查询单体数据
     */
    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        WorkorderDefineDTO dto = (WorkorderDefineDTO) dtoParameter;
        String sqlStr = " SELECT EWD.WORKORDER_DEFINE_ID,\n"
                + "       EWD.ORGANIZATION_ID,\n"
                + "       EWD.OBJECT_CATEGORY,\n"
                + "       EWD.COUNTY,\n"
                + "       EWD.CITY,\n"
                + "       EWD.COST_CENTER_CODE,\n"
                + "       EWD.IMPLEMENT_BY,\n"
                + "       EWD.CHECKOVER_BY,\n"
                + "       SU1.USERNAME IMPLEMENT_BY_NAME,\n"
                + "       SU2.USERNAME CHECKOVER_BY_NAME,\n"
                + "       EWD.WORKORDER_CYCLE,\n"
                + "       EWD.ENABLED,\n"
                + "       EWD.WORKORDER_EXEC_DATE, EWD.WORKORDER_TIGGER_TIME, EWD.AUXILIARY_INFO \n"
                + "	 FROM ETS_WORKORDER_DEFINE EWD, \n"
                + "       SF_USER SU1, \n"
                + "       SF_USER SU2  \n"
                + " WHERE EWD.WORKORDER_DEFINE_ID = ? "
                + "   AND EWD.IMPLEMENT_BY *= SU1.USER_ID \n"
                + "   AND EWD.CHECKOVER_BY *= SU2.USER_ID \n"
                + "	  AND EWD.ORGANIZATION_ID = ? \n";

        sqlArgs.add(dto.getWorkorderDefineId());
        sqlArgs.add(userAccount.getOrganizationId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 创建巡检自定义规则
     */
    public SQLModel getDataCreateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        WorkorderDefineDTO dto = (WorkorderDefineDTO) dtoParameter;
        String sqlStr = " INSERT INTO ETS_WORKORDER_DEFINE\n"
                + "  (WORKORDER_DEFINE_ID,\n" + "   ORGANIZATION_ID,\n"
                + "   OBJECT_CATEGORY,\n" + "   CITY,\n" + "   COUNTY,\n"
                + "   COST_CENTER_CODE,\n" + "   IMPLEMENT_BY,\n"
                + "   CHECKOVER_BY,\n" + "   WORKORDER_CYCLE,\n"
                + "   ENABLED,\n" + "   WORKORDER_EXEC_DATE,WORKORDER_TIGGER_TIME,\n"
                + "   CREATION_DATE,WORKORDER_EXEC_DATE\n" + "   CREATED_BY,GROUP_ID,AUXILIARY_INFO)\n" + "VALUES\n"
                + "  (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ?, GETDATE(),GETDATE(), ?, ?, ?)";

        sqlArgs.add(dto.getWorkorderDefineId());
        sqlArgs.add(userAccount.getOrganizationId());
        sqlArgs.add(dto.getObjectCategory());
        sqlArgs.add(dto.getCity());
        sqlArgs.add(dto.getCounty());
        sqlArgs.add(dto.getCostCenterCode());
        sqlArgs.add(dto.getImplementBy());
        sqlArgs.add(dto.getCheckoverBy());
        sqlArgs.add(dto.getWorkorderCycle());
        sqlArgs.add(dto.getEnabled());
        sqlArgs.add(null);
        sqlArgs.add(dto.getWorkorderTiggerTime());
        sqlArgs.add(userAccount.getUserId());
        sqlArgs.add(userAccount.getCurrGroupId());
        sqlArgs.add(dto.getAuxiliaryInfo());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 修改巡检自定义规则
     */
    public SQLModel getDataUpdateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        WorkorderDefineDTO dto = (WorkorderDefineDTO) dtoParameter;
        String sqlStr = " UPDATE ETS_WORKORDER_DEFINE SET \n"
                + "   OBJECT_CATEGORY = ?,\n" + "   CITY = ?,\n"
                + "   COUNTY = ?,\n" + "   COST_CENTER_CODE = ?,\n"
                + "   IMPLEMENT_BY = ?,\n" + "   CHECKOVER_BY = ?,\n"
                + "   WORKORDER_CYCLE = ?,WORKORDER_TIGGER_TIME = ?,\n" + "   ENABLED = ?,\n"
                + "   AUXILIARY_INFO = ?,\n"
                + "   LAST_UPDATE_DATE = GETDATE(),\n"
                + "   LAST_UPDATE_BY = ?\n" + "	WHERE WORKORDER_DEFINE_ID = ?";

        sqlArgs.add(dto.getObjectCategory());
        sqlArgs.add(dto.getCity());
        sqlArgs.add(dto.getCounty());
        sqlArgs.add(dto.getCostCenterCode());
        sqlArgs.add(dto.getImplementBy());
        sqlArgs.add(dto.getCheckoverBy());
        sqlArgs.add(dto.getWorkorderCycle());
        sqlArgs.add(dto.getWorkorderTiggerTime());
        sqlArgs.add(dto.getEnabled());
        sqlArgs.add(dto.getAuxiliaryInfo());
        sqlArgs.add(user.getUserId());
        sqlArgs.add(dto.getWorkorderDefineId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 查询公司、地点、所属区县、成本中心是否存在重复
     * @return
     * @throws SQLModelException
     */
    public SQLModel getValidateDateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        WorkorderDefineDTO dto = (WorkorderDefineDTO) dtoParameter;
        String sqlStr = " SELECT 1 \n" + "	FROM ETS_WORKORDER_DEFINE \n"
                + " WHERE ORGANIZATION_ID = ? " + "	AND OBJECT_CATEGORY = ? "
                + "	AND CITY = ? " + "	AND COUNTY = ? "
                + "	AND COST_CENTER_CODE = ?";

        sqlArgs.add(user.getOrganizationId());
        sqlArgs.add(dto.getObjectCategory());
        sqlArgs.add(dto.getCity());
        sqlArgs.add(dto.getCounty());
        sqlArgs.add(dto.getCostCenterCode());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 下发巡检工单批表
     */
    public SQLModel getCreateWorkorderBatchModel(String workorderBatchNo,
                                                 WorkorderDefineDTO defineDTO) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO " + " ETS_WORKORDER_BATCH(" + " SYSTEMID,"
                + " WORKORDER_BATCH," + " WORKORDER_BATCH_NAME," + " PRJ_ID,"
                + " WORKORDER_TYPE," + " STATUS," + " CREATION_DATE,"
                + " CREATED_BY" + ") VALUES ("
                + " NEWID(), ?, ?, ?, ?, 0, GETDATE(), ?)";

        sqlArgs.add(workorderBatchNo);
        sqlArgs.add("自建巡检工单");
        sqlArgs.add(null);
        sqlArgs.add("12");
        sqlArgs.add(defineDTO.getCreatedBy());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 查询符合下发的工单
     * @param defineDTO
     * @return
     */
    public SQLModel getCountWorkorderModel(WorkorderDefineDTO defineDTO) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = " SELECT TOP 200 WORKORDER_OBJECT_NO "
                + "  FROM ETS_OBJECT " //评审过后去掉TOP 200
                + "  WHERE IS_TEMP_ADDR=0\n"
                + "	AND ORGANIZATION_ID = ?\n"
                + "	  AND (DISABLE_DATE='' OR DISABLE_DATE IS NULL OR DISABLE_DATE >= GETDATE())"
                + "   AND OBJECT_CATEGORY = ?\n"
                + "   AND CITY = ?\n"
                + "   AND COUNTY = ?\n"
                + "   AND ( ? = '' OR SUBSTRING(WORKORDER_OBJECT_NAME,1,CASE CHARINDEX('.',WORKORDER_OBJECT_NAME) WHEN 0 THEN LEN(WORKORDER_OBJECT_NAME) ELSE CHARINDEX('.',WORKORDER_OBJECT_NAME)-1 END) = ?)\n"
                + "   AND ( ? = '' OR AUXILIARY_INFO = ?)";

        sqlArgs.add(defineDTO.getOrganizationId());
        sqlArgs.add(defineDTO.getObjectCategory());
        sqlArgs.add(defineDTO.getCity());
        sqlArgs.add(defineDTO.getCounty());
        sqlArgs.add(defineDTO.getCostCenterCode());
        sqlArgs.add(defineDTO.getCostCenterCode());
        sqlArgs.add(defineDTO.getAuxiliaryInfo());
        sqlArgs.add(defineDTO.getAuxiliaryInfo());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    /**
     * 下发巡检工单
     * @param workorderBatchNo
     * @return
     */
    public SQLModel getCreateWorkorderDataModel(String workorderBatchNo,
                                                String workorderNo, String workorderObjectNo,
                                                WorkorderDefineDTO defineDTO) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr =
                "INSERT INTO ETS_WORKORDER\n" +
                        "  (SYSTEMID,\n" +
                        "   WORKORDER_BATCH,\n" +
                        "   WORKORDER_NO,\n" +
                        "   WORKORDER_TYPE,\n" +
                        "   WORKORDER_OBJECT_NO,\n" +
                        "   START_DATE,\n" +
                        "   IMPLEMENT_DAYS,\n" +
                        "   GROUP_ID,\n" +
                        "   IMPLEMENT_BY,\n" +
                        "   SCANOVER_BY,\n" +
                        "   CHECKOVER_BY,\n" +
                        "   ORGANIZATION_ID,\n" +
                        "   WORKORDER_FLAG,\n" +
                        "   CREATION_DATE,\n" +
                        "   CREATED_BY,\n" +
                        "   COST_CENTER_CODE)\n" +
                        "VALUES\n" +
                        "  (NEWID(),?,?,?,?,GETDATE(),?,?,?,?,?,?,'11',GETDATE(),?,?)";

        sqlArgs.add(workorderBatchNo);
        sqlArgs.add(workorderNo);
        sqlArgs.add("12");
        sqlArgs.add(workorderObjectNo);
        sqlArgs.add(60);
        sqlArgs.add(defineDTO.getGroupId());
        sqlArgs.add(defineDTO.getImplementBy());
        sqlArgs.add(defineDTO.getCreatedBy());
        sqlArgs.add(defineDTO.getCheckoverBy());
        sqlArgs.add(defineDTO.getOrganizationId());
        sqlArgs.add(defineDTO.getCreatedBy());
        sqlArgs.add(defineDTO.getCostCenterCode());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    /**
     * 修改执行时间
     * @return
     */
    public SQLModel getUpdateWorkorderDefine(String workorderDefineId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = " UPDATE ETS_WORKORDER_DEFINE SET WORKORDER_EXEC_DATE = dbo.GET_WORKORDER_EXEC_DATE(WORKORDER_EXEC_DATE,WORKORDER_DEFINE_ID)\n"
                + " WHERE WORKORDER_DEFINE_ID = ?";

        sqlArgs.add(workorderDefineId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }


    public SQLModel getCountDefineModel(WorkorderDefineDTO defineDTO) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr =
                "SELECT COUNT(1) ROW_COUNT\n" +
                        "  FROM ETS_OBJECT EO\n" +
                        " WHERE EO.ORGANIZATION_ID = ?\n" +
                        "   AND EO.OBJECT_CATEGORY = ?\n" +
                        "   AND EO.CITY = ?\n" +
                        "   AND EO.COUNTY = ?\n" ;

       sqlArgs.add(defineDTO.getOrganizationId());
       sqlArgs.add(defineDTO.getObjectCategory());
       sqlArgs.add(defineDTO.getCity());
       sqlArgs.add(defineDTO.getCounty());
        if (StrUtil.isNotEmpty(defineDTO.getAuxiliaryInfo())) {
            sqlStr+="   AND EO.AUXILIARY_INFO = ?\n" ;
            sqlArgs.add(defineDTO.getAuxiliaryInfo());
        }
        if (StrUtil.isNotEmpty(defineDTO.getCostCenterCode())) {
            sqlStr+= "   AND EO.WORKORDER_OBJECT_NAME LIKE ?";
            sqlArgs.add(defineDTO.getCostCenterCode()+"%");
        }
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }
}
