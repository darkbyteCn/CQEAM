package com.sino.ams.workorder.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dto.EtsWorkorderBatchDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: EtsWorkorderBatchModel</p>
 * <p>Description:程序自动生成SQL构造器“EtsWorkorderBatchModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author zhoujs
 * @version 1.0
 */


public class EtsWorkorderBatchModel extends BaseSQLProducer {

    private EtsWorkorderBatchDTO etsWorkorderBatch = null;
    private SfUserDTO sfUser = null;

    /**
     * 功能：工单批表(EAM) ETS_WORKORDER_BATCH 数据库SQL构造层构造函数
     * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter EtsWorkorderBatchDTO 本次操作的数据
     */
    public EtsWorkorderBatchModel(SfUserDTO userAccount, EtsWorkorderBatchDTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
        this.etsWorkorderBatch = (EtsWorkorderBatchDTO)dtoParameter;
    }
    /**
     * 功能：框架自动生成工单批表(EAM) ETS_WORKORDER_BATCH数据插入SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据插入用SQLModel
     */
    public SQLModel getDataCreateModel(){
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO "
            + " ETS_WORKORDER_BATCH("
            + " SYSTEMID,"
            + " WORKORDER_BATCH,"
            + " WORKORDER_BATCH_NAME,"
            + " REMARK,"
            + " PRJ_ID,"
            + " WORKORDER_TYPE,"
            + " STATUS,"
            + " ARCHFLAG,"
            + " ACTID,"
            + " CASEID,"
            + " DISTRIBUTE_GROUP_ID,"
            + " CREATION_DATE,"
            + " CREATED_BY,"
            + " LAST_UPDATE_DATE,"
            + " LAST_UPDATE_BY"
            + ") VALUES ("
            + " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), ?, ?, ?)";

        sqlArgs.add(etsWorkorderBatch.getSystemid());
        sqlArgs.add(etsWorkorderBatch.getWorkorderBatch());
        sqlArgs.add(etsWorkorderBatch.getWorkorderBatchName());
        sqlArgs.add(etsWorkorderBatch.getRemark());
        sqlArgs.add(etsWorkorderBatch.getPrjId());
        sqlArgs.add(etsWorkorderBatch.getWorkorderType());
        sqlArgs.add(etsWorkorderBatch.getStatus());
        sqlArgs.add(etsWorkorderBatch.getArchflag());
        sqlArgs.add(etsWorkorderBatch.getActid());
        sqlArgs.add(etsWorkorderBatch.getCaseid());
        sqlArgs.add(etsWorkorderBatch.getDistributeGroupId());
//        sqlArgs.add(etsWorkorderBatch.getCreationDate());
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(etsWorkorderBatch.getLastUpdateDate());
        sqlArgs.add(etsWorkorderBatch.getLastUpdateBy());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

     /**
     * FUNCTION：    查询针对指定地点、项目创建的未归档交接工单数量。
     * @return SQLModel 返回数据插入用SQLModel
     */
    public SQLModel existHandoverWorkorderNumberModel(){
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT COUNT(1)\n" +
                        "  FROM ETS_WORKORDER EW\n" +
                        " WHERE EW.WORKORDER_TYPE = ?\n" +
                        "   AND EW.WORKORDER_FLAG = 14\n" +
                        "   AND EWB.PRJ_ID = dbo.NVL(?, EWB.PRJ_ID)\n" +
                        "   AND EW.WORKORDER_OBJECT_NO = dbo.NVL(?, EW.WORKORDER_OBJECT_NO)";

        sqlArgs.add(etsWorkorderBatch.getWorkorderType());
        sqlArgs.add(etsWorkorderBatch.getPrjId());
//        sqlArgs.add(etsWorkorderBatch.);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成工单批表(EAM) ETS_WORKORDER_BATCH数据更新SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据更新用SQLModel
     */
    public SQLModel getDataUpdateModel(){
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE ETS_WORKORDER_BATCH"
            + " SET"
            + " WORKORDER_BATCH = ?,"
            + " WORKORDER_BATCH_NAME = ?,"
            + " REMARK = ?,"
            + " PRJ_ID = ?,"
            + " WORKORDER_TYPE = ?,"
            + " STATUS = ?,"
            + " ARCHFLAG = ?,"
            + " ACTID = ?,"
            + " CASEID = ?,"
            + " DISTRIBUTE_GROUP_ID = ?,"
            + " CREATION_DATE = ?,"
            + " CREATED_BY = ?,"
            + " LAST_UPDATE_DATE = ?,"
            + " LAST_UPDATE_BY = ?"
            + " WHERE"
            + " SYSTEMID = ?";

        sqlArgs.add(etsWorkorderBatch.getWorkorderBatch());
        sqlArgs.add(etsWorkorderBatch.getWorkorderBatchName());
        sqlArgs.add(etsWorkorderBatch.getRemark());
        sqlArgs.add(etsWorkorderBatch.getPrjId());
        sqlArgs.add(etsWorkorderBatch.getWorkorderType());
        sqlArgs.add(etsWorkorderBatch.getStatus());
        sqlArgs.add(etsWorkorderBatch.getArchflag());
        sqlArgs.add(etsWorkorderBatch.getActid());
        sqlArgs.add(etsWorkorderBatch.getCaseid());
        sqlArgs.add(etsWorkorderBatch.getDistributeGroupId());
        sqlArgs.add(etsWorkorderBatch.getCreationDate());
        sqlArgs.add(etsWorkorderBatch.getCreatedBy());
        sqlArgs.add(etsWorkorderBatch.getLastUpdateDate());
        sqlArgs.add(etsWorkorderBatch.getLastUpdateBy());
        sqlArgs.add(etsWorkorderBatch.getSystemid());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成工单批表(EAM) ETS_WORKORDER_BATCH数据删除SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据删除用SQLModel
     */
    public SQLModel getDataDeleteModel(){
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM"
            + " ETS_WORKORDER_BATCH"
            + " WHERE"
            + " SYSTEMID = ?";
        sqlArgs.add(etsWorkorderBatch.getSystemid());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成工单批表(EAM) ETS_WORKORDER_BATCH数据详细信息查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    public SQLModel getPrimaryKeyDataModel(){
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = " SELECT SYSTEMID," +
	                "       WORKORDER_BATCH," +
	                "       WORKORDER_BATCH_NAME," +
	                "       REMARK," +
	                "       PRJ_ID," +
	                "       dbo.APP_GET_PROJECT_NAME(CONVERT(VARCHAR,PRJ_ID)) PRJ_NAME," +
	                "       WORKORDER_TYPE," +
	                "       dbo.APP_GET_FLEX_VALUE(WORKORDER_TYPE, '" + DictConstant.WORKORDER_TYPE + "') WORKORDER_TYPE_DESC," +
	                "       STATUS," +
	                "       ARCHFLAG," +
	                "       ACTID," +
	                "       CASEID," +
	                "       DISTRIBUTE_GROUP_ID," +
	                "       dbo.APP_GET_GROUP_NAME(CONVERT(INT,DISTRIBUTE_GROUP_ID)) DISTRIBUTE_GROUP_NAME," +
	                "       CREATION_DATE," +
	                "       CREATED_BY," +
	                "       dbo.APP_GET_USER_NAME(CREATED_BY) CREATE_USER," +
	                "       LAST_UPDATE_DATE," +
	                "       LAST_UPDATE_BY" +
	                "  FROM ETS_WORKORDER_BATCH" +
	                " WHERE SYSTEMID = ?";

        sqlArgs.add(etsWorkorderBatch.getSystemid());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成工单批表(EAM) ETS_WORKORDER_BATCH多条数据信息查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回多条数据信息查询用SQLModel
     */
    public SQLModel getDataMuxModel(){
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT "
            + " SYSTEMID,"
            + " WORKORDER_BATCH,"
            + " WORKORDER_BATCH_NAME,"
            + " REMARK,"
            + " PRJ_ID,"
            + " WORKORDER_TYPE,"
            + " STATUS,"
            + " ARCHFLAG,"
            + " ACTID,"
            + " CASEID,"
            + " DISTRIBUTE_GROUP_ID,"
            + " CREATION_DATE,"
            + " CREATED_BY,"
            + " LAST_UPDATE_DATE,"
            + " LAST_UPDATE_BY"
            + " FROM"
            + " ETS_WORKORDER_BATCH"
            + " WHERE"
            + "( " + SyBaseSQLUtil.isNull() + "  OR SYSTEMID LIKE ?)"
            + "( " + SyBaseSQLUtil.isNull() + "  OR WORKORDER_BATCH LIKE ?)"
            + "( " + SyBaseSQLUtil.isNull() + "  OR WORKORDER_BATCH_NAME LIKE ?)"
            + "( " + SyBaseSQLUtil.isNull() + "  OR REMARK LIKE ?)"
            + "( " + SyBaseSQLUtil.isNull() + "  OR PRJ_ID LIKE ?)"
            + "( " + SyBaseSQLUtil.isNull() + "  OR WORKORDER_TYPE LIKE ?)"
            + "( " + SyBaseSQLUtil.isNull() + "  OR STATUS LIKE ?)"
            + "( " + SyBaseSQLUtil.isNull() + "  OR ARCHFLAG LIKE ?)"
            + "( " + SyBaseSQLUtil.isNull() + "  OR ACTID LIKE ?)"
            + "( " + SyBaseSQLUtil.isNull() + "  OR CASEID LIKE ?)"
            + "( " + SyBaseSQLUtil.isNull() + "  OR DISTRIBUTE_GROUP_ID LIKE ?)"
            + "( " + SyBaseSQLUtil.isNull() + "  OR CREATION_DATE LIKE ?)"
            + "( " + SyBaseSQLUtil.isNull() + "  OR CREATED_BY LIKE ?)"
            + "( " + SyBaseSQLUtil.isNull() + "  OR LAST_UPDATE_DATE LIKE ?)"
            + "( " + SyBaseSQLUtil.isNull() + "  OR LAST_UPDATE_BY LIKE ?)";
        sqlArgs.add(etsWorkorderBatch.getSystemid());
        sqlArgs.add(etsWorkorderBatch.getSystemid());
        sqlArgs.add(etsWorkorderBatch.getWorkorderBatch());
        sqlArgs.add(etsWorkorderBatch.getWorkorderBatch());
        sqlArgs.add(etsWorkorderBatch.getWorkorderBatchName());
        sqlArgs.add(etsWorkorderBatch.getWorkorderBatchName());
        sqlArgs.add(etsWorkorderBatch.getRemark());
        sqlArgs.add(etsWorkorderBatch.getRemark());
        sqlArgs.add(etsWorkorderBatch.getPrjId());
        sqlArgs.add(etsWorkorderBatch.getPrjId());
        sqlArgs.add(etsWorkorderBatch.getWorkorderType());
        sqlArgs.add(etsWorkorderBatch.getWorkorderType());
        sqlArgs.add(etsWorkorderBatch.getStatus());
        sqlArgs.add(etsWorkorderBatch.getStatus());
        sqlArgs.add(etsWorkorderBatch.getArchflag());
        sqlArgs.add(etsWorkorderBatch.getArchflag());
        sqlArgs.add(etsWorkorderBatch.getActid());
        sqlArgs.add(etsWorkorderBatch.getActid());
        sqlArgs.add(etsWorkorderBatch.getCaseid());
        sqlArgs.add(etsWorkorderBatch.getCaseid());
        sqlArgs.add(etsWorkorderBatch.getDistributeGroupId());
        sqlArgs.add(etsWorkorderBatch.getDistributeGroupId());
        sqlArgs.add(etsWorkorderBatch.getCreationDate());
        sqlArgs.add(etsWorkorderBatch.getCreationDate());
        sqlArgs.add(etsWorkorderBatch.getCreatedBy());
        sqlArgs.add(etsWorkorderBatch.getCreatedBy());
        sqlArgs.add(etsWorkorderBatch.getLastUpdateDate());
        sqlArgs.add(etsWorkorderBatch.getLastUpdateDate());
        sqlArgs.add(etsWorkorderBatch.getLastUpdateBy());
        sqlArgs.add(etsWorkorderBatch.getLastUpdateBy());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成工单批表(EAM) ETS_WORKORDER_BATCH页面翻页查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getPageQueryModel(){
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT "
            + " SYSTEMID,"
            + " WORKORDER_BATCH,"
            + " WORKORDER_BATCH_NAME,"
            + " REMARK,"
            + " PRJ_ID,"
            + " WORKORDER_TYPE,"
            + " STATUS,"
            + " ARCHFLAG,"
            + " ACTID,"
            + " CASEID,"
            + " DISTRIBUTE_GROUP_ID,"
            + " CREATION_DATE,"
            + " CREATED_BY,"
            + " LAST_UPDATE_DATE,"
            + " LAST_UPDATE_BY"
            + " FROM"
            + " ETS_WORKORDER_BATCH"
            + " WHERE"
            + " ( " + SyBaseSQLUtil.isNull() + "  OR SYSTEMID LIKE ?)"
            + "AND ( " + SyBaseSQLUtil.isNull() + "  OR WORKORDER_BATCH LIKE ?)"
            + "AND ( " + SyBaseSQLUtil.isNull() + "  OR WORKORDER_BATCH_NAME LIKE ?)"
            + "AND ( " + SyBaseSQLUtil.isNull() + "  OR REMARK LIKE ?)"
            + "AND ( " + SyBaseSQLUtil.isNull() + "  OR PRJ_ID LIKE ?)"
            + "AND ( " + SyBaseSQLUtil.isNull() + "  OR WORKORDER_TYPE LIKE ?)"
            + "AND ( " + SyBaseSQLUtil.isNull() + "  OR STATUS LIKE ?)"
            + "AND ( " + SyBaseSQLUtil.isNull() + "  OR ARCHFLAG LIKE ?)"
            + "AND ( " + SyBaseSQLUtil.isNull() + "  OR ACTID LIKE ?)"
            + "AND ( " + SyBaseSQLUtil.isNull() + "  OR CASEID LIKE ?)"
            + "AND ( " + SyBaseSQLUtil.isNull() + "  OR DISTRIBUTE_GROUP_ID LIKE ?)"
            + "AND ( " + SyBaseSQLUtil.isNull() + "  OR CREATION_DATE LIKE ?)"
            + "AND ( " + SyBaseSQLUtil.isNull() + "  OR CREATED_BY LIKE ?)"
            + "AND ( " + SyBaseSQLUtil.isNull() + "  OR LAST_UPDATE_DATE LIKE ?)"
            + "AND ( " + SyBaseSQLUtil.isNull() + "  OR LAST_UPDATE_BY LIKE ?)";
        sqlArgs.add(etsWorkorderBatch.getSystemid());
        sqlArgs.add(etsWorkorderBatch.getSystemid());
        sqlArgs.add(etsWorkorderBatch.getWorkorderBatch());
        sqlArgs.add(etsWorkorderBatch.getWorkorderBatch());
        sqlArgs.add(etsWorkorderBatch.getWorkorderBatchName());
        sqlArgs.add(etsWorkorderBatch.getWorkorderBatchName());
        sqlArgs.add(etsWorkorderBatch.getRemark());
        sqlArgs.add(etsWorkorderBatch.getRemark());
        sqlArgs.add(etsWorkorderBatch.getPrjId());
        sqlArgs.add(etsWorkorderBatch.getPrjId());
        sqlArgs.add(etsWorkorderBatch.getWorkorderType());
        sqlArgs.add(etsWorkorderBatch.getWorkorderType());
        sqlArgs.add(etsWorkorderBatch.getStatus());
        sqlArgs.add(etsWorkorderBatch.getStatus());
        sqlArgs.add(etsWorkorderBatch.getArchflag());
        sqlArgs.add(etsWorkorderBatch.getArchflag());
        sqlArgs.add(etsWorkorderBatch.getActid());
        sqlArgs.add(etsWorkorderBatch.getActid());
        sqlArgs.add(etsWorkorderBatch.getCaseid());
        sqlArgs.add(etsWorkorderBatch.getCaseid());
        sqlArgs.add(etsWorkorderBatch.getDistributeGroupId());
        sqlArgs.add(etsWorkorderBatch.getDistributeGroupId());
        sqlArgs.add(etsWorkorderBatch.getCreationDate());
        sqlArgs.add(etsWorkorderBatch.getCreationDate());
        sqlArgs.add(etsWorkorderBatch.getCreatedBy());
        sqlArgs.add(etsWorkorderBatch.getCreatedBy());
        sqlArgs.add(etsWorkorderBatch.getLastUpdateDate());
        sqlArgs.add(etsWorkorderBatch.getLastUpdateDate());
        sqlArgs.add(etsWorkorderBatch.getLastUpdateBy());
        sqlArgs.add(etsWorkorderBatch.getLastUpdateBy());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getDataByForeignKeyModel(String foreignKey) throws SQLModelException {
         SQLModel sqlModel = new SQLModel();
        EtsWorkorderBatchDTO workorderBatchDTO=(EtsWorkorderBatchDTO)dtoParameter;
        if(foreignKey.equals("workorderBatch")){
               sqlModel=getDataByBatchNo(workorderBatchDTO.getWorkorderBatch());
        }
        return sqlModel;
    }

    public SQLModel getDataByBatchNo(String workorderBatch){
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT SYSTEMID," +
                "       WORKORDER_BATCH," +
                "       WORKORDER_BATCH_NAME," +
                "       REMARK," +
                "       PRJ_ID," +
                "       dbo.APP_GET_PROJECT_NAME(CONVERT(VARCHAR,PRJ_ID)) PRJ_NAME," +
                "       WORKORDER_TYPE," +
                "       dbo.APP_GET_FLEX_VALUE(WORKORDER_TYPE, '" + DictConstant.WORKORDER_TYPE + "') WORKORDER_TYPE_DESC," +
                "       STATUS," +
                "       ARCHFLAG," +
                "       ACTID," +
                "       CASEID," +
                "       DISTRIBUTE_GROUP_ID," +
                "       dbo.APP_GET_GROUP_NAME(CONVERT(INT,DISTRIBUTE_GROUP_ID)) DISTRIBUTE_GROUP_NAME," +
                "       CREATION_DATE," +
                "       CREATED_BY," +
                "       dbo.APP_GET_USER_NAME(CREATED_BY) CREATE_USER," +
                "       LAST_UPDATE_DATE," +
                "       LAST_UPDATE_BY" +
                "  FROM ETS_WORKORDER_BATCH" +
                " WHERE WORKORDER_BATCH = ?";

        sqlArgs.add(etsWorkorderBatch.getWorkorderBatch());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

}