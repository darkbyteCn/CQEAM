package com.sino.ams.workorder.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.constant.AmsOrderConstant;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dto.EtsWorkorderTmpDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: EtsWorkorderTmpModel</p>
 * <p>Description:程序自动生成SQL构造器“EtsWorkorderTmpModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author zhoujs
 * @version 1.0
 */


public class EtsWorkorderTmpModel extends BaseSQLProducer {

    private SfUserDTO sfUser = null;

    /**
     * 功能：工单临时表(EAM) ETS_WORKORDER_TMP 数据库SQL构造层构造函数
     * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter EtsWorkorderTmpDTO 本次操作的数据
     */
    public EtsWorkorderTmpModel(SfUserDTO userAccount, EtsWorkorderTmpDTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
    }
    /**
     * 功能：框架自动生成工单临时表(EAM) ETS_WORKORDER_TMP数据插入SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据插入用SQLModel
     */
    public SQLModel getDataCreateModel(){
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsWorkorderTmpDTO etsWorkorderTmp = (EtsWorkorderTmpDTO)dtoParameter;
        String sqlStr = "INSERT INTO "
            + " ETS_WORKORDER_TMP("
            + " SYSTEMID,"
            + " WORKORDER_BATCH,"
            + " WORKORDER_NO,"
            + " WORKORDER_TYPE,"
            + " WORKORDER_OBJECT_NO,"
            + " START_DATE,"
            + " IMPLEMENT_DAYS,"
            + " GROUP_ID,"
            + " IMPLEMENT_BY,"
            + " PRJ_ID,"
            + " DISTRIBUTE_DATE,"
            + " DISTRIBUTE_BY,"
            + " DOWNLOAD_DATE,"
            + " DOWNLOAD_BY,"
            + " SCANOVER_DATE,"
            + " SCANOVER_BY,"
            + " UPLOAD_DATE,"
            + " UPLOAD_BY,"
            + " CHECKOVER_DATE,"
            + " CHECKOVER_BY,"
            + " RESPONSIBILITY_USER,"
            + " DIFFERENCE_REASON,"
            + " ORGANIZATION_ID,"
            + " WORKORDER_FLAG,"
            + " REMARK,"
            + " ACTID,"
            + " CASEID,"
            + " ARCHFLAG,"
            + " ATTRIBUTE1,"
            + " ATTRIBUTE2,"
            + " ATTRIBUTE3,"
            + " ATTRIBUTE4,"
            + " ATTRIBUTE5,"
            + " ATTRIBUTE6,"
            + " DISTRIBUTE_GROUP,"
            + " ATTRIBUTE7,"
            + " CREATION_DATE,"
            + " CREATED_BY,"
            + " LAST_UPDATE_DATE,"
            + " LAST_UPDATE_BY,"
            + " COST_CENTER_CODE"
            + ") VALUES ("
            + " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), ?, ?, ?, ?)";

        if(etsWorkorderTmp.getAttribute7().equals("")||etsWorkorderTmp.getAttribute7().equals("ALL")){
            etsWorkorderTmp.setAttribute7(AmsOrderConstant.scanAllItemCategory);
        }

        sqlArgs.add(etsWorkorderTmp.getSystemid());
        sqlArgs.add(etsWorkorderTmp.getWorkorderBatch());
        sqlArgs.add(etsWorkorderTmp.getWorkorderNo());
        sqlArgs.add(etsWorkorderTmp.getWorkorderType());
        sqlArgs.add(etsWorkorderTmp.getWorkorderObjectNo());
        sqlArgs.add(etsWorkorderTmp.getStartDate());
        sqlArgs.add(etsWorkorderTmp.getImplementDays());
        sqlArgs.add(etsWorkorderTmp.getGroupId());
        sqlArgs.add(etsWorkorderTmp.getImplementBy());
        sqlArgs.add(etsWorkorderTmp.getPrjId());
        sqlArgs.add(etsWorkorderTmp.getDistributeDate());
        sqlArgs.add(etsWorkorderTmp.getDistributeBy());
        sqlArgs.add(etsWorkorderTmp.getDownloadDate());
        sqlArgs.add(etsWorkorderTmp.getDownloadBy());
        sqlArgs.add(etsWorkorderTmp.getScanoverDate());
        sqlArgs.add(etsWorkorderTmp.getScanoverBy());
        sqlArgs.add(etsWorkorderTmp.getUploadDate());
        sqlArgs.add(etsWorkorderTmp.getUploadBy());
        sqlArgs.add(etsWorkorderTmp.getCheckoverDate());
        sqlArgs.add(etsWorkorderTmp.getCheckoverBy());
        sqlArgs.add(etsWorkorderTmp.getResponsibilityUser());
        sqlArgs.add(etsWorkorderTmp.getDifferenceReason());
        sqlArgs.add(etsWorkorderTmp.getOrganizationId());
        sqlArgs.add(etsWorkorderTmp.getWorkorderFlag());
        sqlArgs.add(etsWorkorderTmp.getRemark());
        sqlArgs.add(etsWorkorderTmp.getActid());
        sqlArgs.add(etsWorkorderTmp.getCaseid());
        sqlArgs.add(etsWorkorderTmp.getArchflag());
        sqlArgs.add(etsWorkorderTmp.getAttribute1());
        sqlArgs.add(etsWorkorderTmp.getAttribute2());
        sqlArgs.add(etsWorkorderTmp.getAttribute3());
        sqlArgs.add(etsWorkorderTmp.getAttribute4());
        sqlArgs.add(etsWorkorderTmp.getAttribute5());
        sqlArgs.add(etsWorkorderTmp.getAttribute6());
        sqlArgs.add(etsWorkorderTmp.getDistributeGroup());
        sqlArgs.add(etsWorkorderTmp.getAttribute7());
//        sqlArgs.add(etsWorkorderTmp.getCreationDate());
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(etsWorkorderTmp.getLastUpdateDate());
        sqlArgs.add(etsWorkorderTmp.getLastUpdateBy());
        sqlArgs.add(etsWorkorderTmp.getCostCenterCode());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成工单临时表(EAM) ETS_WORKORDER_TMP数据更新SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据更新用SQLModel
     */
    public SQLModel getDataUpdateModel(){
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsWorkorderTmpDTO etsWorkorderTmp = (EtsWorkorderTmpDTO)dtoParameter;
        String sqlStr = "UPDATE ETS_WORKORDER_TMP"
            + " SET"
            + " SYSTEMID = ?,"
            + " WORKORDER_BATCH = ?,"
            + " WORKORDER_NO = ?,"
            + " WORKORDER_TYPE = ?,"
            + " WORKORDER_OBJECT_NO = ?,"
            + " START_DATE = ?,"
            + " IMPLEMENT_DAYS = ?,"
            + " GROUP_ID = ?,"
            + " IMPLEMENT_BY = ?,"
            + " PRJ_ID = ?,"
            + " DISTRIBUTE_DATE = ?,"
            + " DISTRIBUTE_BY = ?,"
            + " DOWNLOAD_DATE = ?,"
            + " DOWNLOAD_BY = ?,"
            + " SCANOVER_DATE = ?,"
            + " SCANOVER_BY = ?,"
            + " UPLOAD_DATE = ?,"
            + " UPLOAD_BY = ?,"
            + " CHECKOVER_DATE = ?,"
            + " CHECKOVER_BY = ?,"
            + " RESPONSIBILITY_USER = ?,"
            + " DIFFERENCE_REASON = ?,"
            + " ORGANIZATION_ID = ?,"
            + " WORKORDER_FLAG = ?,"
            + " REMARK = ?,"
            + " ACTID = ?,"
            + " CASEID = ?,"
            + " ARCHFLAG = ?,"
            + " ATTRIBUTE1 = ?,"
            + " ATTRIBUTE2 = ?,"
            + " ATTRIBUTE3 = ?,"
            + " ATTRIBUTE4 = ?,"
            + " ATTRIBUTE5 = ?,"
            + " ATTRIBUTE6 = ?,"
            + " DISTRIBUTE_GROUP = ?,"
            + " ATTRIBUTE7 = ?,"
            + " CREATION_DATE = ?,"
            + " CREATED_BY = ?,"
            + " LAST_UPDATE_DATE = ?,"
            + " LAST_UPDATE_BY = ?,"
            + " COST_CENTER_CODE = ?"
            ;
        sqlArgs.add(etsWorkorderTmp.getSystemid());
        sqlArgs.add(etsWorkorderTmp.getWorkorderBatch());
        sqlArgs.add(etsWorkorderTmp.getWorkorderNo());
        sqlArgs.add(etsWorkorderTmp.getWorkorderType());
        sqlArgs.add(etsWorkorderTmp.getWorkorderObjectNo());
        sqlArgs.add(etsWorkorderTmp.getStartDate());
        sqlArgs.add(etsWorkorderTmp.getImplementDays());
        sqlArgs.add(etsWorkorderTmp.getGroupId());
        sqlArgs.add(etsWorkorderTmp.getImplementBy());
        sqlArgs.add(etsWorkorderTmp.getPrjId());
        sqlArgs.add(etsWorkorderTmp.getDistributeDate());
        sqlArgs.add(etsWorkorderTmp.getDistributeBy());
        sqlArgs.add(etsWorkorderTmp.getDownloadDate());
        sqlArgs.add(etsWorkorderTmp.getDownloadBy());
        sqlArgs.add(etsWorkorderTmp.getScanoverDate());
        sqlArgs.add(etsWorkorderTmp.getScanoverBy());
        sqlArgs.add(etsWorkorderTmp.getUploadDate());
        sqlArgs.add(etsWorkorderTmp.getUploadBy());
        sqlArgs.add(etsWorkorderTmp.getCheckoverDate());
        sqlArgs.add(etsWorkorderTmp.getCheckoverBy());
        sqlArgs.add(etsWorkorderTmp.getResponsibilityUser());
        sqlArgs.add(etsWorkorderTmp.getDifferenceReason());
        sqlArgs.add(etsWorkorderTmp.getOrganizationId());
        sqlArgs.add(etsWorkorderTmp.getWorkorderFlag());
        sqlArgs.add(etsWorkorderTmp.getRemark());
        sqlArgs.add(etsWorkorderTmp.getActid());
        sqlArgs.add(etsWorkorderTmp.getCaseid());
        sqlArgs.add(etsWorkorderTmp.getArchflag());
        sqlArgs.add(etsWorkorderTmp.getAttribute1());
        sqlArgs.add(etsWorkorderTmp.getAttribute2());
        sqlArgs.add(etsWorkorderTmp.getAttribute3());
        sqlArgs.add(etsWorkorderTmp.getAttribute4());
        sqlArgs.add(etsWorkorderTmp.getAttribute5());
        sqlArgs.add(etsWorkorderTmp.getAttribute6());
        sqlArgs.add(etsWorkorderTmp.getDistributeGroup());
        sqlArgs.add(etsWorkorderTmp.getAttribute7());
        sqlArgs.add(etsWorkorderTmp.getCreationDate());
        sqlArgs.add(etsWorkorderTmp.getCreatedBy());
        sqlArgs.add(etsWorkorderTmp.getLastUpdateDate());
        sqlArgs.add(etsWorkorderTmp.getLastUpdateBy());
        sqlArgs.add(etsWorkorderTmp.getCostCenterCode());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成工单临时表(EAM) ETS_WORKORDER_TMP数据删除SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据删除用SQLModel
     */
    public SQLModel getDataDeleteModel(){
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsWorkorderTmpDTO etsWorkorderTmp = (EtsWorkorderTmpDTO)dtoParameter;
        String sqlStr = "DELETE FROM"
            + " ETS_WORKORDER_TMP";
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成工单临时表(EAM) ETS_WORKORDER_TMP数据详细信息查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    public SQLModel getPrimaryKeyDataModel(){
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsWorkorderTmpDTO etsWorkorderTmp = (EtsWorkorderTmpDTO)dtoParameter;

        String sqlStr = "SELECT SYSTEMID,\n" +
                "       WORKORDER_BATCH,\n" +
                "       WORKORDER_NO,\n" +
                "       WORKORDER_TYPE,\n" +
                "       dbo.APP_GET_FLEX_VALUE(WORKORDER_TYPE, '"+DictConstant.WORKORDER_TYPE+"') WORKORDER_TYPE_DESC,\n" +
                "       WORKORDER_OBJECT_NO,\n" +
                "       dbo.APP_GET_OBJECT_CODE(WORKORDER_OBJECT_NO) WORKORDER_OBJECT_CODE,\n" +
                "       dbo.APP_GET_OBJECT_NAME(WORKORDER_OBJECT_NO) WORKORDER_OBJECT_NAME,\n" +
                "       START_DATE,\n" +
                "       IMPLEMENT_DAYS,\n" +
                "       GROUP_ID,\n" +
                "       dbo.APP_GET_GROUP_NAME(CONVERT(INT,GROUP_ID)) GROUP_NAME,\n" +
                "       IMPLEMENT_BY,\n" +
                "       dbo.APP_GET_USER_NAME(IMPLEMENT_BY) IMPLEMENT_USER,\n" +
                "       PRJ_ID,\n" +
                "       dbo.APP_GET_PROJECT_NAME(CONVERT(VARCHAR,PRJ_ID)) PRJ_NAME,\n" +
                "       DISTRIBUTE_DATE,\n" +
                "       DISTRIBUTE_BY,\n" +
                "       dbo.APP_GET_USER_NAME(DISTRIBUTE_BY) DISTRIBUTE_USER,\n" +
                "       DOWNLOAD_DATE,\n" +
                "       DOWNLOAD_BY,\n" +
                "       dbo.APP_GET_USER_NAME(DOWNLOAD_BY) DOWNLOAD_USER,\n" +
                "       SCANOVER_DATE,\n" +
                "       SCANOVER_BY,\n" +
                "       dbo.APP_GET_USER_NAME(SCANOVER_BY) SCANOVER_USER,\n" +
                "       UPLOAD_DATE,\n" +
                "       UPLOAD_BY,\n" +
                "       dbo.APP_GET_USER_NAME(UPLOAD_BY) UPLOAD_USER,\n" +
                "       CHECKOVER_DATE,\n" +
                "       CHECKOVER_BY,\n" +
                "       dbo.APP_GET_USER_NAME(CHECKOVER_BY) CHECKOVER_USER,\n" +
                "       RESPONSIBILITY_USER,\n" +
                "       DIFFERENCE_REASON,\n" +
                "       ORGANIZATION_ID,\n" +
                "       WORKORDER_FLAG,\n" +
                "       dbo.APP_GET_FLEX_VALUE(CONVERT(VARCHAR,WORKORDER_FLAG), '"+DictConstant.WORKORDER_STATUS+"') WORKORDER_FLAG_DESC,\n" +
                "       REMARK,\n" +
                "       ACTID,\n" +
                "       CASEID,\n" +
                "       ARCHFLAG,\n" +
                "       ATTRIBUTE1,\n" +
                "       dbo.APP_GET_OBJECT_CODE(ATTRIBUTE1) TRANS_OBJECT_CODE,\n" +
                "       dbo.APP_GET_OBJECT_NAME(ATTRIBUTE1) TRANS_OBJECT_NAME,\n" +
                "       ATTRIBUTE2,\n" +
                "       ATTRIBUTE3,\n" +
                "       ATTRIBUTE4,\n" +
                "       ATTRIBUTE5,\n" +
                "       ATTRIBUTE6,\n" +
                "       DISTRIBUTE_GROUP,\n" +
                "       ATTRIBUTE7,\n" +
                "       CREATION_DATE,\n" +
                "       CREATED_BY,\n" +
                "       LAST_UPDATE_DATE,\n" +
                "       LAST_UPDATE_BY,\n" +
                "       COST_CENTER_CODE\n" +
                "  FROM ETS_WORKORDER_TMP\n" +
                " WHERE SYSTEMID = ?";

        sqlArgs.add(etsWorkorderTmp.getSystemid());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成工单临时表(EAM) ETS_WORKORDER_TMP多条数据信息查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回多条数据信息查询用SQLModel
     */
    public SQLModel getDataMuxModel(){
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsWorkorderTmpDTO etsWorkorderTmp = (EtsWorkorderTmpDTO)dtoParameter;
        String sqlStr = "SELECT "
            + " SYSTEMID,"
            + " WORKORDER_BATCH,"
            + " WORKORDER_NO,"
            + " WORKORDER_TYPE,"
            + " WORKORDER_OBJECT_NO,"
            + " START_DATE,"
            + " IMPLEMENT_DAYS,"
            + " GROUP_ID,"
            + " IMPLEMENT_BY,"
            + " PRJ_ID,"
            + " DISTRIBUTE_DATE,"
            + " DISTRIBUTE_BY,"
            + " DOWNLOAD_DATE,"
            + " DOWNLOAD_BY,"
            + " SCANOVER_DATE,"
            + " SCANOVER_BY,"
            + " UPLOAD_DATE,"
            + " UPLOAD_BY,"
            + " CHECKOVER_DATE,"
            + " CHECKOVER_BY,"
            + " RESPONSIBILITY_USER,"
            + " DIFFERENCE_REASON,"
            + " ORGANIZATION_ID,"
            + " WORKORDER_FLAG,"
            + " REMARK,"
            + " ACTID,"
            + " CASEID,"
            + " ARCHFLAG,"
            + " ATTRIBUTE1,"
            + " ATTRIBUTE2,"
            + " ATTRIBUTE3,"
            + " ATTRIBUTE4,"
            + " ATTRIBUTE5,"
            + " ATTRIBUTE6,"
            + " DISTRIBUTE_GROUP,"
            + " ATTRIBUTE7,"
            + " CREATION_DATE,"
            + " CREATED_BY,"
            + " LAST_UPDATE_DATE,"
            + " LAST_UPDATE_BY,"
            + " COST_CENTER_CODE"
            + " FROM"
            + " ETS_WORKORDER_TMP"
            + " WHERE"
            + "( " + SyBaseSQLUtil.isNull() + "  OR SYSTEMID LIKE ?)"
            + "( " + SyBaseSQLUtil.isNull() + "  OR WORKORDER_BATCH LIKE ?)"
            + "( " + SyBaseSQLUtil.isNull() + "  OR WORKORDER_NO LIKE ?)"
            + "( " + SyBaseSQLUtil.isNull() + "  OR WORKORDER_TYPE LIKE ?)"
            + "( " + SyBaseSQLUtil.isNull() + "  OR WORKORDER_OBJECT_NO LIKE ?)"
            + "( " + SyBaseSQLUtil.isNull() + "  OR START_DATE LIKE ?)"
            + "( " + SyBaseSQLUtil.isNull() + "  OR IMPLEMENT_DAYS LIKE ?)"
            + "( " + SyBaseSQLUtil.isNull() + "  OR GROUP_ID LIKE ?)"
            + "( " + SyBaseSQLUtil.isNull() + "  OR IMPLEMENT_BY LIKE ?)"
            + "( " + SyBaseSQLUtil.isNull() + "  OR PRJ_ID LIKE ?)"
            + "( " + SyBaseSQLUtil.isNull() + "  OR DISTRIBUTE_DATE LIKE ?)"
            + "( " + SyBaseSQLUtil.isNull() + "  OR DISTRIBUTE_BY LIKE ?)"
            + "( " + SyBaseSQLUtil.isNull() + "  OR DOWNLOAD_DATE LIKE ?)"
            + "( " + SyBaseSQLUtil.isNull() + "  OR DOWNLOAD_BY LIKE ?)"
            + "( " + SyBaseSQLUtil.isNull() + "  OR SCANOVER_DATE LIKE ?)"
            + "( " + SyBaseSQLUtil.isNull() + "  OR SCANOVER_BY LIKE ?)"
            + "( " + SyBaseSQLUtil.isNull() + "  OR UPLOAD_DATE LIKE ?)"
            + "( " + SyBaseSQLUtil.isNull() + "  OR UPLOAD_BY LIKE ?)"
            + "( " + SyBaseSQLUtil.isNull() + "  OR CHECKOVER_DATE LIKE ?)"
            + "( " + SyBaseSQLUtil.isNull() + "  OR CHECKOVER_BY LIKE ?)"
            + "( " + SyBaseSQLUtil.isNull() + "  OR RESPONSIBILITY_USER LIKE ?)"
            + "( " + SyBaseSQLUtil.isNull() + "  OR DIFFERENCE_REASON LIKE ?)"
            + "( " + SyBaseSQLUtil.isNull() + "  OR ORGANIZATION_ID LIKE ?)"
            + "( " + SyBaseSQLUtil.isNull() + "  OR WORKORDER_FLAG LIKE ?)"
            + "( " + SyBaseSQLUtil.isNull() + "  OR REMARK LIKE ?)"
            + "( " + SyBaseSQLUtil.isNull() + "  OR ACTID LIKE ?)"
            + "( " + SyBaseSQLUtil.isNull() + "  OR CASEID LIKE ?)"
            + "( " + SyBaseSQLUtil.isNull() + "  OR ARCHFLAG LIKE ?)"
            + "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE1 LIKE ?)"
            + "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE2 LIKE ?)"
            + "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE3 LIKE ?)"
            + "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE4 LIKE ?)"
            + "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE5 LIKE ?)"
            + "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE6 LIKE ?)"
            + "( " + SyBaseSQLUtil.isNull() + "  OR DISTRIBUTE_GROUP LIKE ?)"
            + "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE7 LIKE ?)"
            + "( " + SyBaseSQLUtil.isNull() + "  OR CREATION_DATE LIKE ?)"
            + "( " + SyBaseSQLUtil.isNull() + "  OR CREATED_BY LIKE ?)"
            + "( " + SyBaseSQLUtil.isNull() + "  OR LAST_UPDATE_DATE LIKE ?)"
            + "( " + SyBaseSQLUtil.isNull() + "  OR LAST_UPDATE_BY LIKE ?)";
        sqlArgs.add(etsWorkorderTmp.getSystemid());
        sqlArgs.add(etsWorkorderTmp.getSystemid());
        sqlArgs.add(etsWorkorderTmp.getWorkorderBatch());
        sqlArgs.add(etsWorkorderTmp.getWorkorderBatch());
        sqlArgs.add(etsWorkorderTmp.getWorkorderNo());
        sqlArgs.add(etsWorkorderTmp.getWorkorderNo());
        sqlArgs.add(etsWorkorderTmp.getWorkorderType());
        sqlArgs.add(etsWorkorderTmp.getWorkorderType());
        sqlArgs.add(etsWorkorderTmp.getWorkorderObjectNo());
        sqlArgs.add(etsWorkorderTmp.getWorkorderObjectNo());
        sqlArgs.add(etsWorkorderTmp.getStartDate());
        sqlArgs.add(etsWorkorderTmp.getStartDate());
        sqlArgs.add(etsWorkorderTmp.getImplementDays());
        sqlArgs.add(etsWorkorderTmp.getImplementDays());
        sqlArgs.add(etsWorkorderTmp.getGroupId());
        sqlArgs.add(etsWorkorderTmp.getGroupId());
        sqlArgs.add(etsWorkorderTmp.getImplementBy());
        sqlArgs.add(etsWorkorderTmp.getImplementBy());
        sqlArgs.add(etsWorkorderTmp.getPrjId());
        sqlArgs.add(etsWorkorderTmp.getPrjId());
        sqlArgs.add(etsWorkorderTmp.getDistributeDate());
        sqlArgs.add(etsWorkorderTmp.getDistributeDate());
        sqlArgs.add(etsWorkorderTmp.getDistributeBy());
        sqlArgs.add(etsWorkorderTmp.getDistributeBy());
        sqlArgs.add(etsWorkorderTmp.getDownloadDate());
        sqlArgs.add(etsWorkorderTmp.getDownloadDate());
        sqlArgs.add(etsWorkorderTmp.getDownloadBy());
        sqlArgs.add(etsWorkorderTmp.getDownloadBy());
        sqlArgs.add(etsWorkorderTmp.getScanoverDate());
        sqlArgs.add(etsWorkorderTmp.getScanoverDate());
        sqlArgs.add(etsWorkorderTmp.getScanoverBy());
        sqlArgs.add(etsWorkorderTmp.getScanoverBy());
        sqlArgs.add(etsWorkorderTmp.getUploadDate());
        sqlArgs.add(etsWorkorderTmp.getUploadDate());
        sqlArgs.add(etsWorkorderTmp.getUploadBy());
        sqlArgs.add(etsWorkorderTmp.getUploadBy());
        sqlArgs.add(etsWorkorderTmp.getCheckoverDate());
        sqlArgs.add(etsWorkorderTmp.getCheckoverDate());
        sqlArgs.add(etsWorkorderTmp.getCheckoverBy());
        sqlArgs.add(etsWorkorderTmp.getCheckoverBy());
        sqlArgs.add(etsWorkorderTmp.getResponsibilityUser());
        sqlArgs.add(etsWorkorderTmp.getResponsibilityUser());
        sqlArgs.add(etsWorkorderTmp.getDifferenceReason());
        sqlArgs.add(etsWorkorderTmp.getDifferenceReason());
        sqlArgs.add(etsWorkorderTmp.getOrganizationId());
        sqlArgs.add(etsWorkorderTmp.getOrganizationId());
        sqlArgs.add(etsWorkorderTmp.getWorkorderFlag());
        sqlArgs.add(etsWorkorderTmp.getWorkorderFlag());
        sqlArgs.add(etsWorkorderTmp.getRemark());
        sqlArgs.add(etsWorkorderTmp.getRemark());
        sqlArgs.add(etsWorkorderTmp.getActid());
        sqlArgs.add(etsWorkorderTmp.getActid());
        sqlArgs.add(etsWorkorderTmp.getCaseid());
        sqlArgs.add(etsWorkorderTmp.getCaseid());
        sqlArgs.add(etsWorkorderTmp.getArchflag());
        sqlArgs.add(etsWorkorderTmp.getArchflag());
        sqlArgs.add(etsWorkorderTmp.getAttribute1());
        sqlArgs.add(etsWorkorderTmp.getAttribute1());
        sqlArgs.add(etsWorkorderTmp.getAttribute2());
        sqlArgs.add(etsWorkorderTmp.getAttribute2());
        sqlArgs.add(etsWorkorderTmp.getAttribute3());
        sqlArgs.add(etsWorkorderTmp.getAttribute3());
        sqlArgs.add(etsWorkorderTmp.getAttribute4());
        sqlArgs.add(etsWorkorderTmp.getAttribute4());
        sqlArgs.add(etsWorkorderTmp.getAttribute5());
        sqlArgs.add(etsWorkorderTmp.getAttribute5());
        sqlArgs.add(etsWorkorderTmp.getAttribute6());
        sqlArgs.add(etsWorkorderTmp.getAttribute6());
        sqlArgs.add(etsWorkorderTmp.getDistributeGroup());
        sqlArgs.add(etsWorkorderTmp.getDistributeGroup());
        sqlArgs.add(etsWorkorderTmp.getAttribute7());
        sqlArgs.add(etsWorkorderTmp.getAttribute7());
        sqlArgs.add(etsWorkorderTmp.getCreationDate());
        sqlArgs.add(etsWorkorderTmp.getCreationDate());
        sqlArgs.add(etsWorkorderTmp.getCreatedBy());
        sqlArgs.add(etsWorkorderTmp.getCreatedBy());
        sqlArgs.add(etsWorkorderTmp.getLastUpdateDate());
        sqlArgs.add(etsWorkorderTmp.getLastUpdateDate());
        sqlArgs.add(etsWorkorderTmp.getLastUpdateBy());
        sqlArgs.add(etsWorkorderTmp.getLastUpdateBy());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成工单临时表(EAM) ETS_WORKORDER_TMP页面翻页查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getPageQueryModel(){
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsWorkorderTmpDTO etsWorkorderTmp = (EtsWorkorderTmpDTO)dtoParameter;
        String sqlStr = "SELECT "
            + " SYSTEMID,"
            + " WORKORDER_BATCH,"
            + " WORKORDER_NO,"
            + " WORKORDER_TYPE,"
            + " WORKORDER_OBJECT_NO,"
            + " START_DATE,"
            + " IMPLEMENT_DAYS,"
            + " GROUP_ID,"
            + " IMPLEMENT_BY,"
            + " PRJ_ID,"
            + " DISTRIBUTE_DATE,"
            + " DISTRIBUTE_BY,"
            + " DOWNLOAD_DATE,"
            + " DOWNLOAD_BY,"
            + " SCANOVER_DATE,"
            + " SCANOVER_BY,"
            + " UPLOAD_DATE,"
            + " UPLOAD_BY,"
            + " CHECKOVER_DATE,"
            + " CHECKOVER_BY,"
            + " RESPONSIBILITY_USER,"
            + " DIFFERENCE_REASON,"
            + " ORGANIZATION_ID,"
            + " WORKORDER_FLAG,"
            + " REMARK,"
            + " ACTID,"
            + " CASEID,"
            + " ARCHFLAG,"
            + " ATTRIBUTE1,"
            + " ATTRIBUTE2,"
            + " ATTRIBUTE3,"
            + " ATTRIBUTE4,"
            + " ATTRIBUTE5,"
            + " ATTRIBUTE6,"
            + " DISTRIBUTE_GROUP,"
            + " ATTRIBUTE7,"
            + " CREATION_DATE,"
            + " CREATED_BY,"
            + " LAST_UPDATE_DATE,"
            + " LAST_UPDATE_BY,"
            + " COST_CENTER_CODE"
            + " FROM"
            + " ETS_WORKORDER_TMP"
            + " WHERE"
            + " ( " + SyBaseSQLUtil.isNull() + "  OR SYSTEMID LIKE ?)"
            + "AND ( " + SyBaseSQLUtil.isNull() + "  OR WORKORDER_BATCH LIKE ?)"
            + "AND ( " + SyBaseSQLUtil.isNull() + "  OR WORKORDER_NO LIKE ?)"
            + "AND ( " + SyBaseSQLUtil.isNull() + "  OR WORKORDER_TYPE LIKE ?)"
            + "AND ( " + SyBaseSQLUtil.isNull() + "  OR WORKORDER_OBJECT_NO LIKE ?)"
            + "AND ( " + SyBaseSQLUtil.isNull() + "  OR START_DATE LIKE ?)"
            + "AND ( " + SyBaseSQLUtil.isNull() + "  OR IMPLEMENT_DAYS LIKE ?)"
            + "AND ( " + SyBaseSQLUtil.isNull() + "  OR GROUP_ID LIKE ?)"
            + "AND ( " + SyBaseSQLUtil.isNull() + "  OR IMPLEMENT_BY LIKE ?)"
            + "AND ( " + SyBaseSQLUtil.isNull() + "  OR PRJ_ID LIKE ?)"
            + "AND ( " + SyBaseSQLUtil.isNull() + "  OR DISTRIBUTE_DATE LIKE ?)"
            + "AND ( " + SyBaseSQLUtil.isNull() + "  OR DISTRIBUTE_BY LIKE ?)"
            + "AND ( " + SyBaseSQLUtil.isNull() + "  OR DOWNLOAD_DATE LIKE ?)"
            + "AND ( " + SyBaseSQLUtil.isNull() + "  OR DOWNLOAD_BY LIKE ?)"
            + "AND ( " + SyBaseSQLUtil.isNull() + "  OR SCANOVER_DATE LIKE ?)"
            + "AND ( " + SyBaseSQLUtil.isNull() + "  OR SCANOVER_BY LIKE ?)"
            + "AND ( " + SyBaseSQLUtil.isNull() + "  OR UPLOAD_DATE LIKE ?)"
            + "AND ( " + SyBaseSQLUtil.isNull() + "  OR UPLOAD_BY LIKE ?)"
            + "AND ( " + SyBaseSQLUtil.isNull() + "  OR CHECKOVER_DATE LIKE ?)"
            + "AND ( " + SyBaseSQLUtil.isNull() + "  OR CHECKOVER_BY LIKE ?)"
            + "AND ( " + SyBaseSQLUtil.isNull() + "  OR RESPONSIBILITY_USER LIKE ?)"
            + "AND ( " + SyBaseSQLUtil.isNull() + "  OR DIFFERENCE_REASON LIKE ?)"
            + "AND ( " + SyBaseSQLUtil.isNull() + "  OR ORGANIZATION_ID LIKE ?)"
            + "AND ( " + SyBaseSQLUtil.isNull() + "  OR WORKORDER_FLAG LIKE ?)"
            + "AND ( " + SyBaseSQLUtil.isNull() + "  OR REMARK LIKE ?)"
            + "AND ( " + SyBaseSQLUtil.isNull() + "  OR ACTID LIKE ?)"
            + "AND ( " + SyBaseSQLUtil.isNull() + "  OR CASEID LIKE ?)"
            + "AND ( " + SyBaseSQLUtil.isNull() + "  OR ARCHFLAG LIKE ?)"
            + "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE1 LIKE ?)"
            + "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE2 LIKE ?)"
            + "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE3 LIKE ?)"
            + "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE4 LIKE ?)"
            + "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE5 LIKE ?)"
            + "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE6 LIKE ?)"
            + "AND ( " + SyBaseSQLUtil.isNull() + "  OR DISTRIBUTE_GROUP LIKE ?)"
            + "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE7 LIKE ?)"
            + "AND ( " + SyBaseSQLUtil.isNull() + "  OR CREATION_DATE LIKE ?)"
            + "AND ( " + SyBaseSQLUtil.isNull() + "  OR CREATED_BY LIKE ?)"
            + "AND ( " + SyBaseSQLUtil.isNull() + "  OR LAST_UPDATE_DATE LIKE ?)"
            + "AND ( " + SyBaseSQLUtil.isNull() + "  OR LAST_UPDATE_BY LIKE ?)";
        sqlArgs.add(etsWorkorderTmp.getSystemid());
        sqlArgs.add(etsWorkorderTmp.getSystemid());
        sqlArgs.add(etsWorkorderTmp.getWorkorderBatch());
        sqlArgs.add(etsWorkorderTmp.getWorkorderBatch());
        sqlArgs.add(etsWorkorderTmp.getWorkorderNo());
        sqlArgs.add(etsWorkorderTmp.getWorkorderNo());
        sqlArgs.add(etsWorkorderTmp.getWorkorderType());
        sqlArgs.add(etsWorkorderTmp.getWorkorderType());
        sqlArgs.add(etsWorkorderTmp.getWorkorderObjectNo());
        sqlArgs.add(etsWorkorderTmp.getWorkorderObjectNo());
        sqlArgs.add(etsWorkorderTmp.getStartDate());
        sqlArgs.add(etsWorkorderTmp.getStartDate());
        sqlArgs.add(etsWorkorderTmp.getImplementDays());
        sqlArgs.add(etsWorkorderTmp.getImplementDays());
        sqlArgs.add(etsWorkorderTmp.getGroupId());
        sqlArgs.add(etsWorkorderTmp.getGroupId());
        sqlArgs.add(etsWorkorderTmp.getImplementBy());
        sqlArgs.add(etsWorkorderTmp.getImplementBy());
        sqlArgs.add(etsWorkorderTmp.getPrjId());
        sqlArgs.add(etsWorkorderTmp.getPrjId());
        sqlArgs.add(etsWorkorderTmp.getDistributeDate());
        sqlArgs.add(etsWorkorderTmp.getDistributeDate());
        sqlArgs.add(etsWorkorderTmp.getDistributeBy());
        sqlArgs.add(etsWorkorderTmp.getDistributeBy());
        sqlArgs.add(etsWorkorderTmp.getDownloadDate());
        sqlArgs.add(etsWorkorderTmp.getDownloadDate());
        sqlArgs.add(etsWorkorderTmp.getDownloadBy());
        sqlArgs.add(etsWorkorderTmp.getDownloadBy());
        sqlArgs.add(etsWorkorderTmp.getScanoverDate());
        sqlArgs.add(etsWorkorderTmp.getScanoverDate());
        sqlArgs.add(etsWorkorderTmp.getScanoverBy());
        sqlArgs.add(etsWorkorderTmp.getScanoverBy());
        sqlArgs.add(etsWorkorderTmp.getUploadDate());
        sqlArgs.add(etsWorkorderTmp.getUploadDate());
        sqlArgs.add(etsWorkorderTmp.getUploadBy());
        sqlArgs.add(etsWorkorderTmp.getUploadBy());
        sqlArgs.add(etsWorkorderTmp.getCheckoverDate());
        sqlArgs.add(etsWorkorderTmp.getCheckoverDate());
        sqlArgs.add(etsWorkorderTmp.getCheckoverBy());
        sqlArgs.add(etsWorkorderTmp.getCheckoverBy());
        sqlArgs.add(etsWorkorderTmp.getResponsibilityUser());
        sqlArgs.add(etsWorkorderTmp.getResponsibilityUser());
        sqlArgs.add(etsWorkorderTmp.getDifferenceReason());
        sqlArgs.add(etsWorkorderTmp.getDifferenceReason());
        sqlArgs.add(etsWorkorderTmp.getOrganizationId());
        sqlArgs.add(etsWorkorderTmp.getOrganizationId());
        sqlArgs.add(etsWorkorderTmp.getWorkorderFlag());
        sqlArgs.add(etsWorkorderTmp.getWorkorderFlag());
        sqlArgs.add(etsWorkorderTmp.getRemark());
        sqlArgs.add(etsWorkorderTmp.getRemark());
        sqlArgs.add(etsWorkorderTmp.getActid());
        sqlArgs.add(etsWorkorderTmp.getActid());
        sqlArgs.add(etsWorkorderTmp.getCaseid());
        sqlArgs.add(etsWorkorderTmp.getCaseid());
        sqlArgs.add(etsWorkorderTmp.getArchflag());
        sqlArgs.add(etsWorkorderTmp.getArchflag());
        sqlArgs.add(etsWorkorderTmp.getAttribute1());
        sqlArgs.add(etsWorkorderTmp.getAttribute1());
        sqlArgs.add(etsWorkorderTmp.getAttribute2());
        sqlArgs.add(etsWorkorderTmp.getAttribute2());
        sqlArgs.add(etsWorkorderTmp.getAttribute3());
        sqlArgs.add(etsWorkorderTmp.getAttribute3());
        sqlArgs.add(etsWorkorderTmp.getAttribute4());
        sqlArgs.add(etsWorkorderTmp.getAttribute4());
        sqlArgs.add(etsWorkorderTmp.getAttribute5());
        sqlArgs.add(etsWorkorderTmp.getAttribute5());
        sqlArgs.add(etsWorkorderTmp.getAttribute6());
        sqlArgs.add(etsWorkorderTmp.getAttribute6());
        sqlArgs.add(etsWorkorderTmp.getDistributeGroup());
        sqlArgs.add(etsWorkorderTmp.getDistributeGroup());
        sqlArgs.add(etsWorkorderTmp.getAttribute7());
        sqlArgs.add(etsWorkorderTmp.getAttribute7());
        sqlArgs.add(etsWorkorderTmp.getCreationDate());
        sqlArgs.add(etsWorkorderTmp.getCreationDate());
        sqlArgs.add(etsWorkorderTmp.getCreatedBy());
        sqlArgs.add(etsWorkorderTmp.getCreatedBy());
        sqlArgs.add(etsWorkorderTmp.getLastUpdateDate());
        sqlArgs.add(etsWorkorderTmp.getLastUpdateDate());
        sqlArgs.add(etsWorkorderTmp.getLastUpdateBy());
        sqlArgs.add(etsWorkorderTmp.getLastUpdateBy());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

}