package com.sino.ams.newasset.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.newasset.dto.SfGroupMatchDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;


/**
 * <p>Title: GroupMatchModel</p>
 * <p>Description:程序自动生成SQL构造器“GroupMatchModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class SfGroupMatchModel extends AMSSQLProducer {


    /**
     * 功能：GROUP_MATCH 数据库SQL构造层构造函数
     * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter SfGroupMatchDTO 本次操作的数据
     */
    public SfGroupMatchModel(SfUserDTO userAccount,
                             SfGroupMatchDTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    /**
     * 功能：框架自动生成GROUP_MATCH数据插入SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据插入用SQLModel
     */
    public SQLModel getDataCreateModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        SfGroupMatchDTO dto = (SfGroupMatchDTO) dtoParameter;
        String sqlStr = "INSERT INTO "
                        + " SF_GROUP_MATCH("
                        + " DEPT_CODE,"
                        + " GROUP_ID"
                        + ") VALUES ("
                        + " ?, ?)";

        sqlArgs.add(dto.getDeptCode());
        sqlArgs.add(dto.getGroupId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


    /**
     * 功能：框架自动生成GROUP_MATCH数据删除SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据删除用SQLModel
     */
    public SQLModel getDataDeleteModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        SfGroupMatchDTO dto = (SfGroupMatchDTO) dtoParameter;
        String sqlStr = "DELETE FROM"
                        + " SF_GROUP_MATCH"
                        + " WHERE"
                        + " DEPT_CODE = ?"
                        + " AND GROUP_ID = ?";
        sqlArgs.add(dto.getDeptCode());
        sqlArgs.add(dto.getGroupId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成GROUP_MATCH数据详细信息查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        SfGroupMatchDTO dto = (SfGroupMatchDTO) dtoParameter;
        String sqlStr = "SELECT"
                        + " AMD.DEPT_CODE,"
                        + " AMD.DEPT_NAME,"
                        + " SG.GROUP_ID,"
                        + " SG.GROUP_NAME,"
                        + " EOCM.ORGANIZATION_ID,"
                        + " EOCM.COMPANY_CODE,"
                        + " EOCM.COMPANY"
                        + " FROM"
                        + " AMS_MIS_DEPT   AMD,"
                        + " SF_GROUP_MATCH SGM,"
                        + " SF_GROUP       SG,"
                        + " ETS_OU_CITY_MAP  EOCM"
                        + " WHERE"
                        + " AMD.DEPT_CODE = SGM.DEPT_CODE"
                        + " AND SGM.GROUP_ID = SG.GROUP_ID"
                        + " AND SG.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
                        + " AND SGM.DEPT_CODE = ?"
                        + " AND SGM.GROUP_ID = ?";
        sqlArgs.add(dto.getDeptCode());
        sqlArgs.add(dto.getGroupId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成GROUP_MATCH页面翻页查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getPageQueryModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        SfGroupMatchDTO dto = (SfGroupMatchDTO) dtoParameter;
        String sqlStr = "SELECT"
                        + " AMD.DEPT_CODE,"
                        + " AMD.DEPT_NAME,"
                        + " SG.GROUP_ID,"
                        + " SG.GROUP_NAME,"
                        + " EOCM.ORGANIZATION_ID,"
                        + " EOCM.COMPANY_CODE,"
                        + " EOCM.COMPANY"
                        + " FROM"
                        + " AMS_MIS_DEPT   AMD,"
                        + " SF_GROUP_MATCH SGM,"
                        + " SF_GROUP       SG,"
                        + " ETS_OU_CITY_MAP  EOCM"
                        + " WHERE"
                        + " AMD.DEPT_CODE = SGM.DEPT_CODE"
                        + " AND SGM.GROUP_ID = SG.GROUP_ID"
                        + " AND SG.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
                        + " AND SGM.DEPT_CODE = dbo.NVL(?, SGM.DEPT_CODE)"
                        + " AND SGM.GROUP_ID = dbo.NVL(?, SGM.GROUP_ID)";
        sqlArgs.add(dto.getDeptCode());
        sqlArgs.add(dto.getGroupId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

}
