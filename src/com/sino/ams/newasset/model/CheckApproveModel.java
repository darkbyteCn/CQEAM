package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.dto.AmsAssetsCheckBatchDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * <p>Title: CheckApproveModel</p>
 * <p>Description:程序自动生成SQL构造器“CheckApproveModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class CheckApproveModel extends AmsAssetsCheckBatchModel {

    /**
     * 功能：资产业务头表(EAM)--取代原表 AMS_ASSETS_CHECK_BATCH 数据库SQL构造层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsAssetsCheckBatchDTO 本次操作的数据
     */
    public CheckApproveModel(SfUserDTO userAccount,
                             AmsAssetsCheckBatchDTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    /**
     * 功能：获取判断该单据是否能够审批
     * @return SQLModel
     */
    public SQLModel getCanApproveModel() {
        SQLModel sqlModel = new SQLModel();
        AmsAssetsCheckBatchDTO dto = (AmsAssetsCheckBatchDTO) dtoParameter;
        String sqlStr = "SELECT"
                + " 1"
                + " FROM"
                + " AMS_ASSETS_CHECK_BATCH AACB"
                + " WHERE"
                + " AACB.BATCH_ID = ?"
                + " AND (AACB.BATCH_STATUS = ? OR AACB.BATCH_STATUS = ?)"
                + " AND (AACB.APPROVED_B " + SyBaseSQLUtil.isNullNoParam() + "  OR AACB.APPROVED_BY <> ?)";
        List sqlArgs = new ArrayList();
        sqlArgs.add(dto.getBatchId());
        sqlArgs.add(AssetsDictConstant.IN_PROCESS);
        sqlArgs.add(AssetsDictConstant.REJECTED);
        sqlArgs.add(userAccount.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：审批工单批
     * @return SQLModel
     */
    public SQLModel getBatchApproveModel() {
        SQLModel sqlModel = new SQLModel();
        AmsAssetsCheckBatchDTO dto = (AmsAssetsCheckBatchDTO) dtoParameter;
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE"
                + " AMS_ASSETS_CHECK_BATCH"
                + " SET"
                + " BATCH_STATUS = ?,"
                + " APPROVED_DATE = GETDATE(),"
                + " APPROVED_BY = ?,"
                + " LAST_UPDATE_DATE = GETDATE(),"
                + " LAST_UPDATE_BY = ?"
                + " WHERE"
                + " BATCH_ID = ?";
        sqlArgs.add(dto.getBatchStatus());
        sqlArgs.add(userAccount.getUserId());
        sqlArgs.add(userAccount.getUserId());
        sqlArgs.add(dto.getBatchId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：审批工单
     * @return SQLModel
     */
    public SQLModel getHeadersApproveModel() {
        SQLModel sqlModel = new SQLModel();
        AmsAssetsCheckBatchDTO dto = (AmsAssetsCheckBatchDTO) dtoParameter;
        List sqlArgs = new ArrayList();
        String sqlStr = "";
        if (dto.getBatchStatus().equals("DISTRIBUTED")) {
            sqlStr = "UPDATE"
                    + " AMS_ASSETS_CHECK_HEADER"
                    + " SET"
                    + " ORDER_STATUS = ?,"
                    + " DISTRIBUTE_DATE=GETDATE() ,"
                    + " DISTRIBUTE_BY=?,"
                    + " APPROVED_DATE = GETDATE(),"
                    + " APPROVED_BY = ?,"
                    + " LAST_UPDATE_DATE = GETDATE(),"
                    + " LAST_UPDATE_BY = ?"
                    + " WHERE"
                    + " BATCH_ID = ?";
            sqlArgs.add(dto.getBatchStatus());
            sqlArgs.add(userAccount.getUserId());
            sqlArgs.add(userAccount.getUserId());
            sqlArgs.add(userAccount.getUserId());
        } else {
            sqlStr = "UPDATE"
                    + " AMS_ASSETS_CHECK_HEADER "
                    + " SET"
                    + " ORDER_STATUS = ?,"
                    + " APPROVED_DATE = GETDATE(),"
                    + " APPROVED_BY = ?,"
                    + " LAST_UPDATE_DATE = GETDATE(),"
                    + " LAST_UPDATE_BY = ?"
                    + " WHERE"
                    + " BATCH_ID = ?";
            sqlArgs.add(dto.getBatchStatus());
            sqlArgs.add(userAccount.getUserId());
            sqlArgs.add(userAccount.getUserId());
        }

        sqlArgs.add(dto.getBatchId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
