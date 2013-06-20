package com.sino.ams.workorder.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dto.EtsWorkorderDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;


/**
 * <p>Title: EtsWorkorderModel</p>
 * <p>Description:程序自动生成SQL构造器“EtsWorkorderModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author zhoujs
 * @version 1.0
 */


public class EtsWorkorderModel extends AMSSQLProducer {

    private EtsWorkorderDTO etsWorkorder = null;

    /**
     * 功能：工单主表(EAM) ETS_WORKORDER 数据库SQL构造层构造函数
     * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter EtsWorkorderDTO 本次操作的数据
     */
    public EtsWorkorderModel(SfUserDTO userAccount, EtsWorkorderDTO dtoParameter) {
        super(userAccount, dtoParameter);
        this.etsWorkorder = (EtsWorkorderDTO)dtoParameter;
    }
    /**
     * 功能：框架自动生成工单主表(EAM) ETS_WORKORDER数据插入SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据插入用SQLModel
     */
	public SQLModel getDataCreateModel() throws SQLModelException{
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			String sqlStr = "INSERT INTO "
							+ " ETS_WORKORDER("
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
							+ " COST_CENTER_CODE"
							+ ") VALUES ("
							+ "  NEWID() , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), ?,?)";

			sqlArgs.add(etsWorkorder.getWorkorderBatch());
			sqlArgs.add(etsWorkorder.getWorkorderNo());
			sqlArgs.add(etsWorkorder.getWorkorderType());
			sqlArgs.add(etsWorkorder.getWorkorderObjectNo());
			sqlArgs.add(etsWorkorder.getStartDate());
			sqlArgs.add(etsWorkorder.getImplementDays());
			sqlArgs.add(etsWorkorder.getGroupId());
			sqlArgs.add(etsWorkorder.getImplementBy());
			sqlArgs.add(etsWorkorder.getPrjId());
			sqlArgs.add(etsWorkorder.getDistributeDate());
			sqlArgs.add(etsWorkorder.getDistributeBy());
			sqlArgs.add(etsWorkorder.getDownloadDate());
			sqlArgs.add(etsWorkorder.getDownloadBy());
			sqlArgs.add(etsWorkorder.getScanoverDate());
			sqlArgs.add(etsWorkorder.getScanoverBy());
			sqlArgs.add(etsWorkorder.getUploadDate());
			sqlArgs.add(etsWorkorder.getUploadBy());
			sqlArgs.add(etsWorkorder.getCheckoverDate());
			sqlArgs.add(etsWorkorder.getCheckoverBy());
			sqlArgs.add(etsWorkorder.getResponsibilityUser());
			sqlArgs.add(etsWorkorder.getDifferenceReason());
			sqlArgs.add(etsWorkorder.getOrganizationId());
			sqlArgs.add(etsWorkorder.getWorkorderFlag());
			sqlArgs.add(etsWorkorder.getRemark());
			sqlArgs.add(etsWorkorder.getActid());
			sqlArgs.add(etsWorkorder.getCaseid());
			sqlArgs.add(etsWorkorder.getArchflag());
			sqlArgs.add(etsWorkorder.getAttribute1());
			sqlArgs.add(etsWorkorder.getAttribute2());
			sqlArgs.add(etsWorkorder.getAttribute3());
			sqlArgs.add(etsWorkorder.getAttribute4());
			sqlArgs.add(etsWorkorder.getAttribute5());
			sqlArgs.add(etsWorkorder.getAttribute6());
			sqlArgs.add(etsWorkorder.getDistributeGroup());
			sqlArgs.add(etsWorkorder.getAttribute7());
			sqlArgs.add(userAccount.getUserId());
			sqlArgs.add(etsWorkorder.getCostCenterCode());

            sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

    /**
     * 功能：框架自动生成工单主表(EAM) ETS_WORKORDER数据更新SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据更新用SQLModel
     */
    public SQLModel getDataUpdateModel() throws SQLModelException{
        SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			String sqlStr = "UPDATE ETS_WORKORDER"
							+ " SET"
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
							+ " LAST_UPDATE_DATE = ?,"
							+ " LAST_UPDATE_BY = ?,"
							+ " COST_CENTER_CODE = ?"
							+ " WHERE"
							+ " SYSTEMID = ?";

			sqlArgs.add(etsWorkorder.getWorkorderBatch());
			sqlArgs.add(etsWorkorder.getWorkorderNo());
			sqlArgs.add(etsWorkorder.getWorkorderType());
			sqlArgs.add(etsWorkorder.getWorkorderObjectNo());
			sqlArgs.add(etsWorkorder.getStartDate());
			sqlArgs.add(etsWorkorder.getImplementDays());
			sqlArgs.add(etsWorkorder.getGroupId());
			sqlArgs.add(etsWorkorder.getImplementBy());
			sqlArgs.add(etsWorkorder.getPrjId());
			sqlArgs.add(etsWorkorder.getDistributeDate());
			sqlArgs.add(etsWorkorder.getDistributeBy());
			sqlArgs.add(etsWorkorder.getDownloadDate());
			sqlArgs.add(etsWorkorder.getDownloadBy());
			sqlArgs.add(etsWorkorder.getScanoverDate());
			sqlArgs.add(etsWorkorder.getScanoverBy());
			sqlArgs.add(etsWorkorder.getUploadDate());
			sqlArgs.add(etsWorkorder.getUploadBy());
			sqlArgs.add(etsWorkorder.getCheckoverDate());
			sqlArgs.add(etsWorkorder.getCheckoverBy());
			sqlArgs.add(etsWorkorder.getResponsibilityUser());
			sqlArgs.add(etsWorkorder.getDifferenceReason());
			sqlArgs.add(etsWorkorder.getOrganizationId());
			sqlArgs.add(etsWorkorder.getWorkorderFlag());
			sqlArgs.add(etsWorkorder.getRemark());
			sqlArgs.add(etsWorkorder.getActid());
			sqlArgs.add(etsWorkorder.getCaseid());
			sqlArgs.add(etsWorkorder.getArchflag());
			sqlArgs.add(etsWorkorder.getAttribute1());
			sqlArgs.add(etsWorkorder.getAttribute2());
			sqlArgs.add(etsWorkorder.getAttribute3());
			sqlArgs.add(etsWorkorder.getAttribute4());
			sqlArgs.add(etsWorkorder.getAttribute5());
			sqlArgs.add(etsWorkorder.getAttribute6());
			sqlArgs.add(etsWorkorder.getDistributeGroup());
			sqlArgs.add(etsWorkorder.getAttribute7());
			sqlArgs.add(etsWorkorder.getLastUpdateDate());
			sqlArgs.add(etsWorkorder.getLastUpdateBy());
			sqlArgs.add(etsWorkorder.getCostCenterCode());
			sqlArgs.add(etsWorkorder.getSystemid());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
        return sqlModel;
    }

    /**
     * 功能：框架自动生成工单主表(EAM) ETS_WORKORDER数据删除SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "DELETE FROM"
						+ " ETS_WORKORDER"
						+ " WHERE"
						+ " SYSTEMID = ?";
		sqlArgs.add(etsWorkorder.getSystemid());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

    /**
     * 功能：框架自动生成工单主表(EAM) ETS_WORKORDER数据详细信息查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    public SQLModel getPrimaryKeyDataModel(){
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
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
                "       dbo.APP_GET_USER_NAME(CONVERT(INT,RESPONSIBILITY_USER)) RESPONSIBILITY_USER,\n" +
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
                "       PRJ_ID,\n" +
                "       COST_CENTER_CODE\n" +
                "  FROM ETS_WORKORDER\n" +
                " WHERE SYSTEMID = ?\n"+
                "   OR WORKORDER_NO = ?";

        sqlArgs.add(etsWorkorder.getSystemid());
        sqlArgs.add(etsWorkorder.getWorkorderNo());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


    public SQLModel getQueryModel(String workorderBatchNo){
       SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT " +
                "       EWT.SYSTEMID," +
                "       EWT.WORKORDER_NO," +
                "       EO.WORKORDER_OBJECT_CODE," +
                "       EO.WORKORDER_OBJECT_NAME," +
                "       EO.ATTRIBUTE59 BSC," +
                "        EWT.START_DATE  START_DATE," +
                "       EWT.IMPLEMENT_DAYS," +
                "       SGV.GROUPNAME || '/' || SUV.USERNAME IMPLEMENT," +
                "CASE WHEN WS.SYSTEMID IS NULL  THEN '无' ELSE '是' END HASCONFIG "+
                "       EWT.WORKORDER_BATCH," +
                "       EWT.COST_CENTER_CODE" +
                "  FROM ETS_WORKORDER_TMP EWT," +
                "       ETS_OBJECT EO," +
                "       SF_USER_V SUV," +
                "       SF_GROUP_V SGV," +
                "       (SELECT DISTINCT (SYSTEMID) FROM ETS_WO_SCHEME_TMP) WS" +
                " WHERE EWT.WORKORDER_OBJECT_NO *= EO.WORKORDER_OBJECT_NO" +
                "   AND EWT.SYSTEMID *= WS.SYSTEMID" +
                "   AND EWT.GROUP_ID *= SGV.GROUP_ID" +
                "   AND EWT.IMPLEMENT_BY *= SUV.USERID" +
                "   AND  " + SyBaseSQLUtil.isNotNull("EWT.WORKORDER_NO") + " " +
                "   AND EWT.WORKORDER_BATCH = ? ";


        sqlArgs.add(workorderBatchNo);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

}
