package com.sino.ams.newasset.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.dto.AmsHrDeptDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;


/**
 * <p>Title: AmsHrDeptModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsHrDeptModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class AmsHrDeptModel extends AMSSQLProducer {

    /**
     * 功能：MIS部门(HR) AMS_MIS_DEPT 数据库SQL构造层构造函数
     * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsHrDeptDTO 本次操作的数据
     */
    public AmsHrDeptModel(SfUserDTO userAccount, AmsHrDeptDTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    /**
     * 功能：框架自动生成MIS部门(HR) AMS_HR_DEPT数据插入SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据插入用SQLModel
     */
    public SQLModel getDataCreateModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsHrDeptDTO amsHrDept = (AmsHrDeptDTO) dtoParameter;
        String sqlStr = "INSERT INTO "
                        + " AMS_MIS_DEPT("
                        + " DEPT_CODE,"
                        + " DEPT_NAME,"
                        + " COMPANY_CODE"
                        + ") VALUES ("
                        + "  NEWID() , ?, ?)";

        sqlArgs.add(amsHrDept.getHrDeptName());
        sqlArgs.add(amsHrDept.getCompanyCode());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成MIS部门(HR) AMS_HR_DEPT数据更新SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据更新用SQLModel
     */
    public SQLModel getDataUpdateModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsHrDeptDTO amsHrDept = (AmsHrDeptDTO) dtoParameter;
        String sqlStr = "UPDATE AMS_MIS_DEPT"
                        + " SET"
                        + " DEPT_NAME = ?,"
                        + " COMPANY_CODE = ?"
                        + " WHERE"
                        + " DEPT_CODE = ?";

        sqlArgs.add(amsHrDept.getHrDeptName());
        sqlArgs.add(amsHrDept.getCompanyCode());
        sqlArgs.add(amsHrDept.getHrDeptId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成MIS部门(HR) AMS_HR_DEPT数据删除SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据删除用SQLModel
     */
    public SQLModel getDataDeleteModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsHrDeptDTO amsHrDept = (AmsHrDeptDTO) dtoParameter;
        String sqlStr = "DELETE FROM"
                        + " AMS_MIS_DEPT"
                        + " WHERE"
                        + " DEPT_CODE = ?";
        sqlArgs.add(amsHrDept.getHrDeptId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成MIS部门(HR) AMS_HR_DEPT数据详细信息查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsHrDeptDTO amsHrDept = (AmsHrDeptDTO) dtoParameter;
        String sqlStr = "SELECT "
                        + " DEPT_CODE,"
                        + " DEPT_NAME,"
                        + " COMPANY_CODE"
                        + " FROM"
                        + " AMS_MIS_DEPT"
                        + " WHERE"
                        + " DEPT_CODE = ?";
        sqlArgs.add(amsHrDept.getHrDeptId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成MIS部门(HR) AMS_HR_DEPT多条数据信息查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回多条数据信息查询用SQLModel
     */
    public SQLModel getMuxDataModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsHrDeptDTO amsHrDept = (AmsHrDeptDTO) dtoParameter;
        String sqlStr = "SELECT "
                        + " DEPT_CODE,"
                        + " DEPT_NAME,"
                        + " COMPANY_CODE"
                        + " FROM"
                        + " AMS_MIS_DEPT"
                        + " WHERE"
                        + " ( " + SyBaseSQLUtil.isNull() + "  OR DEPT_CODE LIKE ?)"
                        + " AND ( " + SyBaseSQLUtil.isNull() + "  OR DEPT_NAME LIKE ?)"
                        + " AND ( " + SyBaseSQLUtil.isNull() + "  OR COMPANY_CODE LIKE ?)";
        sqlArgs.add(amsHrDept.getHrDeptId());
        sqlArgs.add(amsHrDept.getHrDeptId());
        sqlArgs.add(amsHrDept.getHrDeptName());
        sqlArgs.add(amsHrDept.getHrDeptName());
        sqlArgs.add(amsHrDept.getCompanyCode());
        sqlArgs.add(amsHrDept.getCompanyCode());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：根据外键关联字段 companyCode 构造查询数据SQL。
     * 框架自动生成数据MIS部门(HR) AMS_HR_DEPT详细信息查询SQLModel，请根据实际需要修改。
     * @param companyCode String
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    private SQLModel getDataByCompanyCodeModel(String companyCode) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT "
                        + " DEPT_CODE,"
                        + " DEPT_NAME"
                        + " FROM"
                        + " AMS_MIS_DEPT"
                        + " WHERE"
                        + " COMPANY_CODE = ?";
        sqlArgs.add(companyCode);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：根据外键获取数据
     * @param foreignKey 传入的外键字段名称。
     * @return SQLModel
     */
    public SQLModel getDataByForeignKeyModel(String foreignKey) {
        SQLModel sqlModel = null;
        AmsHrDeptDTO amsHrDept = (AmsHrDeptDTO) dtoParameter;
        if (foreignKey.equals("companyCode")) {
            sqlModel = getDataByCompanyCodeModel(amsHrDept.getCompanyCode());
        }
        return sqlModel;
    }

    /**
     * 功能：根据外键关联字段 companyCode 构造数据删除SQL。
     * 框架自动生成数据MIS部门(HR) AMS_HR_DEPT数据删除SQLModel，请根据实际需要修改。
     * @param companyCode String
     * @return SQLModel 返回数据删除用SQLModel
     */
    private SQLModel getDeleteByCompanyCodeModel(String companyCode) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE "
                        + " DEPT_CODE,"
                        + " DEPT_NAME"
                        + " FROM"
                        + " AMS_MIS_DEPT"
                        + " WHERE"
                        + " COMPANY_CODE = ?";
        sqlArgs.add(companyCode);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：根据外键字段删除数据
     * @param foreignKey 传入的外键字段名称。
     * @return SQLModel
     */
    public SQLModel getDeleteByForeignKeyModel(String foreignKey) {
        SQLModel sqlModel = null;
        AmsHrDeptDTO amsHrDept = (AmsHrDeptDTO) dtoParameter;
        if (foreignKey.equals("companyCode")) {
            sqlModel = getDeleteByCompanyCodeModel(amsHrDept.getCompanyCode());
        }
        return sqlModel;
    }

    /**
     * 功能：框架自动生成MIS部门(HR) AMS_HR_DEPT页面翻页查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getPageQueryModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsHrDeptDTO amsHrDept = (AmsHrDeptDTO) dtoParameter;
        String sqlStr = "SELECT "
                        + " DEPT_CODE,"
                        + " DEPT_NAME,"
                        + " COMPANY_CODE"
                        + " FROM"
                        + " AMS_MIS_DEPT"
                        + " WHERE"
                        + " ( " + SyBaseSQLUtil.isNull() + "  OR DEPT_CODE LIKE ?)"
                        + " AND ( " + SyBaseSQLUtil.isNull() + "  OR DEPT_NAME LIKE ?)"
                        + " AND ( " + SyBaseSQLUtil.isNull() + "  OR COMPANY_CODE LIKE ?)";
        sqlArgs.add(amsHrDept.getHrDeptId());
        sqlArgs.add(amsHrDept.getHrDeptId());
        sqlArgs.add(amsHrDept.getHrDeptName());
        sqlArgs.add(amsHrDept.getHrDeptName());
        sqlArgs.add(amsHrDept.getCompanyCode());
        sqlArgs.add(amsHrDept.getCompanyCode());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

}
