package com.sino.ams.workorder.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dto.EtsSuggestionDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: EtsSuggestionModel</p>
 * <p>Description:程序自动生成SQL构造器“EtsSuggestionModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author zhoujs
 * @version 1.0
 */


public class EtsSuggestionModel extends BaseSQLProducer {

    private SfUserDTO sfUser = null;

    /**
     * 功能：工单流程意见处理(EAM) ETS_SUGGESTION 数据库SQL构造层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter EtsSuggestionDTO 本次操作的数据
     */
    public EtsSuggestionModel(SfUserDTO userAccount, EtsSuggestionDTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
    }

    /**
     * 功能：框架自动生成工单流程意见处理(EAM) ETS_SUGGESTION数据插入SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据插入用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getDataCreateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsSuggestionDTO etsSuggestion = (EtsSuggestionDTO) dtoParameter;
        String sqlStr = "INSERT INTO "
                + " ETS_SUGGESTION("
                + " SYSTEMID,"
                + " WORKORDER_BATCH,"
                + " TITLE,"
                + " REMARK,"
                + " GROUP_ID,"
                + " HANDLER,"
                + " RECORD_DATE,"
                + " COMPLETE_FLAG,"
                + " ACTID"
                + ") VALUES ("
                + "  NEWID() , ?, ?, ?, ?, ?, GETDATE(), ?, ?)";

        sqlArgs.add(etsSuggestion.getWorkorderBatch());
        sqlArgs.add(etsSuggestion.getTitle());
        sqlArgs.add(etsSuggestion.getRemark());
        sqlArgs.add(etsSuggestion.getGroupId());
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(etsSuggestion.getCompleteFlag());
        sqlArgs.add(etsSuggestion.getActId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    /**
     * 功能：框架自动生成工单流程意见处理(EAM) ETS_SUGGESTION数据更新SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据更新用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getDataUpdateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            EtsSuggestionDTO etsSuggestion = (EtsSuggestionDTO) dtoParameter;
            String sqlStr = "UPDATE ETS_SUGGESTION"
                    + " SET"
                    + " WORKORDER_BATCH = ?,"
                    + " TITLE = ?,"
                    + " REMARK = ?,"
                    + " GROUP_ID = ?,"
                    + " HANDLER = ?,"
                    + " RECORD_DATE = ?,"
                    + " COMPLETE_FLAG = ?"
                    + " WHERE"
                    + " SYSTEMID = ?";

            sqlArgs.add(etsSuggestion.getWorkorderBatch());
            sqlArgs.add(etsSuggestion.getTitle());
            sqlArgs.add(etsSuggestion.getRemark());
            sqlArgs.add(etsSuggestion.getGroupId());
            sqlArgs.add(etsSuggestion.getHandler());
            sqlArgs.add(etsSuggestion.getRecordDate());
            sqlArgs.add(etsSuggestion.getCompleteFlag());
            sqlArgs.add(etsSuggestion.getSystemid());

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
        }
        return sqlModel;
    }

    /**
     * 功能：框架自动生成工单流程意见处理(EAM) ETS_SUGGESTION数据删除SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据删除用SQLModel
     */
    public SQLModel getDataDeleteModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsSuggestionDTO etsSuggestion = (EtsSuggestionDTO) dtoParameter;
        String sqlStr = "DELETE FROM"
                + " ETS_SUGGESTION"
                + " WHERE"
                + " SYSTEMID = ?";
        sqlArgs.add(etsSuggestion.getSystemid());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成工单流程意见处理(EAM) ETS_SUGGESTION数据详细信息查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsSuggestionDTO etsSuggestion = (EtsSuggestionDTO) dtoParameter;
        String sqlStr = "SELECT "
                + " SYSTEMID,"
                + " WORKORDER_BATCH,"
                + " TITLE,"
                + " REMARK,"
                + " GROUP_ID,"
                + " HANDLER,"
                + " RECORD_DATE,"
                + " COMPLETE_FLAG"
                + " FROM"
                + " ETS_SUGGESTION"
                + " WHERE"
                + " SYSTEMID = ?";
        sqlArgs.add(etsSuggestion.getSystemid());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成工单流程意见处理(EAM) ETS_SUGGESTION多条数据信息查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回多条数据信息查询用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getMuxDataModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            EtsSuggestionDTO etsSuggestion = (EtsSuggestionDTO) dtoParameter;
            String sqlStr = "SELECT "
                    + " SYSTEMID,"
                    + " WORKORDER_BATCH,"
                    + " TITLE,"
                    + " REMARK,"
                    + " GROUP_ID,"
                    + " HANDLER,"
                    + " RECORD_DATE,"
                    + " COMPLETE_FLAG"
                    + " FROM"
                    + " ETS_SUGGESTION"
                    + " WHERE"
                    + " ( " + SyBaseSQLUtil.isNull() + "  OR SYSTEMID LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR WORKORDER_BATCH LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR TITLE LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR REMARK LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR GROUP_ID LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR HANDLER LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR RECORD_DATE LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR COMPLETE_FLAG LIKE ?)";
            sqlArgs.add(etsSuggestion.getSystemid());
            sqlArgs.add(etsSuggestion.getSystemid());
            sqlArgs.add(etsSuggestion.getWorkorderBatch());
            sqlArgs.add(etsSuggestion.getWorkorderBatch());
            sqlArgs.add(etsSuggestion.getTitle());
            sqlArgs.add(etsSuggestion.getTitle());
            sqlArgs.add(etsSuggestion.getRemark());
            sqlArgs.add(etsSuggestion.getRemark());
            sqlArgs.add(etsSuggestion.getGroupId());
            sqlArgs.add(etsSuggestion.getGroupId());
            sqlArgs.add(etsSuggestion.getHandler());
            sqlArgs.add(etsSuggestion.getHandler());
            sqlArgs.add(etsSuggestion.getRecordDate());
            sqlArgs.add(etsSuggestion.getRecordDate());
            sqlArgs.add(etsSuggestion.getCompleteFlag());
            sqlArgs.add(etsSuggestion.getCompleteFlag());

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
        }
        return sqlModel;
    }

    /**
     * 功能：框架自动生成工单流程意见处理(EAM) ETS_SUGGESTION页面翻页查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回页面翻页查询SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsSuggestionDTO etsSuggestion = (EtsSuggestionDTO) dtoParameter;
        String sqlStr = "SELECT" +
                "       ES.SYSTEMID,\n" +
                "       ES.TITLE,\n" +
                "       ES.REMARK,\n" +
                "       AMS_PUB_PKG.GET_USER_NAME(ES.HANDLER) USERNAME,\n" +
                "       ES.RECORD_DATE\n" +
                "  FROM ETS_SUGGESTION ES\n" +
                " WHERE ES.WORKORDER_BATCH = ?";


        sqlArgs.add(etsSuggestion.getWorkorderBatch());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
	}

}
