package com.sino.ams.web.bts.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.web.bts.dto.EtsObjectFixfeeDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: EtsObjectFixfeeModel</p>
 * <p>Description:程序自动生成SQL构造器“EtsObjectFixfeeModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author yuyao
 * @version 1.0
 */


public class EtsObjectFixfeeModel extends BaseSQLProducer {

    private SfUserDTO sfUser = null;

    /**
     * 功能：基站维修成本(EAM) ETS_OBJECT_FIXFEE 数据库SQL构造层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter EtsObjectFixfeeDTO 本次操作的数据
     */
    public EtsObjectFixfeeModel(SfUserDTO userAccount, EtsObjectFixfeeDTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
    }

    /**
     * 功能：框架自动生成基站维修成本(EAM) ETS_OBJECT_FIXFEE数据插入SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据插入用SQLModel
     */
    public SQLModel getDataCreateModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsObjectFixfeeDTO etsObjectFixfee = (EtsObjectFixfeeDTO) dtoParameter;
        String sqlStr = "INSERT INTO ETS_OBJECT_FIXFEE\n" +
                "  (SYSTEM_ID,\n" +
                "   FIX_DATE,\n" +
                "   AMOUNT, \n" +
                "   REMARK,\n" +
                "   CREATION_DATE,\n" +
                "   CREATED_BY,  \n" +
                "   OBJECT_NO)\n" +
                "VALUES\n" +
                "  ( NEWID() , ?, ?, ?, GETDATE(), ?, ?)";

        sqlArgs.add(etsObjectFixfee.getFixDate());
        sqlArgs.add(etsObjectFixfee.getAmount());
        sqlArgs.add(etsObjectFixfee.getRemark());
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(etsObjectFixfee.getWorkorderObjectNo());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成基站维修成本(EAM) ETS_OBJECT_FIXFEE数据更新SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据更新用SQLModel
     */
    public SQLModel getDataUpdateModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsObjectFixfeeDTO etsObjectFixfee = (EtsObjectFixfeeDTO) dtoParameter;
        String sqlStr = "UPDATE ETS_OBJECT_FIXFEE\n" +
                "   SET FIX_DATE         = ?,\n" +
                "       AMOUNT           = ?,\n" +
                "       REMARK           = ?,\n" +
                "       LAST_UPDATE_DATE = GETDATE(),\n" +
                "       LAST_UPDATE_BY   = ?,\n" +
                "       OBJECT_NO        = ?\n" +
                " WHERE SYSTEM_ID = ?";

        sqlArgs.add(etsObjectFixfee.getFixDate());
        sqlArgs.add(etsObjectFixfee.getAmount());
        sqlArgs.add(etsObjectFixfee.getRemark());
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(etsObjectFixfee.getWorkorderObjectNo());
        sqlArgs.add(etsObjectFixfee.getSystemId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成基站维修成本(EAM) ETS_OBJECT_FIXFEE数据删除SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据删除用SQLModel
     */
    public SQLModel getDataDeleteModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsObjectFixfeeDTO etsObjectFixfee = (EtsObjectFixfeeDTO) dtoParameter;
        String sqlStr = "DELETE FROM"
                + " ETS_OBJECT_FIXFEE"
                + " WHERE"
                + " SYSTEM_ID = ?";
        sqlArgs.add(etsObjectFixfee.getSystemId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成基站维修成本(EAM) ETS_OBJECT_FIXFEE数据详细信息查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsObjectFixfeeDTO etsObjectFixfee = (EtsObjectFixfeeDTO) dtoParameter;
        String sqlStr = "SELECT EOF.SYSTEM_ID,\n" +
                "       EOF.FIX_DATE,\n" +
                "       EOF.AMOUNT,\n" +
                "       EOF.REMARK,\n" +
                "       EO.WORKORDER_OBJECT_NO,\n" +
                "       EO.WORKORDER_OBJECT_NAME\n" +
                "  FROM ETS_OBJECT_FIXFEE EOF, ETS_OBJECT EO\n" +
                " WHERE EOF.OBJECT_NO = EO.WORKORDER_OBJECT_NO      \n" +
                "   AND SYSTEM_ID = ?";
        sqlArgs.add(etsObjectFixfee.getSystemId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成基站维修成本(EAM) ETS_OBJECT_FIXFEE多条数据信息查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回多条数据信息查询用SQLModel
     */
    public SQLModel getDataMuxModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsObjectFixfeeDTO etsObjectFixfee = (EtsObjectFixfeeDTO) dtoParameter;
        String sqlStr = "SELECT "
                + " SYSTEM_ID,"
                + " FIX_DATE,"
                + " AMOUNT,"
                + " FIX_NO,"
                + " ATTRIBUTE1,"
                + " ATTRIBUTE2,"
                + " REMARK,"
                + " CREATION_DATE,"
                + " CREATED_BY,"
                + " LAST_UPDATE_DATE,"
                + " LAST_UPDATE_BY,"
                + " OBJECT_NO"
                + " FROM"
                + " ETS_OBJECT_FIXFEE"
                + " WHERE"
                + "( " + SyBaseSQLUtil.isNull() + "  OR SYSTEM_ID LIKE ?)"
                + "( " + SyBaseSQLUtil.isNull() + "  OR FIX_DATE LIKE ?)"
                + "( " + SyBaseSQLUtil.isNull() + "  OR AMOUNT LIKE ?)"
                + "( " + SyBaseSQLUtil.isNull() + "  OR FIX_NO LIKE ?)"
                + "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE1 LIKE ?)"
                + "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE2 LIKE ?)"
                + "( " + SyBaseSQLUtil.isNull() + "  OR REMARK LIKE ?)"
                + "( " + SyBaseSQLUtil.isNull() + "  OR CREATION_DATE LIKE ?)"
                + "( " + SyBaseSQLUtil.isNull() + "  OR CREATED_BY LIKE ?)"
                + "( " + SyBaseSQLUtil.isNull() + "  OR LAST_UPDATE_DATE LIKE ?)"
                + "( " + SyBaseSQLUtil.isNull() + "  OR LAST_UPDATE_BY LIKE ?)"
                + "( " + SyBaseSQLUtil.isNull() + "  OR OBJECT_NO LIKE ?)";
        sqlArgs.add(etsObjectFixfee.getSystemId());
        sqlArgs.add(etsObjectFixfee.getSystemId());
        sqlArgs.add(etsObjectFixfee.getFixDate());
        sqlArgs.add(etsObjectFixfee.getFixDate());
        sqlArgs.add(etsObjectFixfee.getAmount());
        sqlArgs.add(etsObjectFixfee.getAmount());
        sqlArgs.add(etsObjectFixfee.getFixNo());
        sqlArgs.add(etsObjectFixfee.getFixNo());
        sqlArgs.add(etsObjectFixfee.getAttribute1());
        sqlArgs.add(etsObjectFixfee.getAttribute1());
        sqlArgs.add(etsObjectFixfee.getAttribute2());
        sqlArgs.add(etsObjectFixfee.getAttribute2());
        sqlArgs.add(etsObjectFixfee.getRemark());
        sqlArgs.add(etsObjectFixfee.getRemark());
        sqlArgs.add(etsObjectFixfee.getCreationDate());
        sqlArgs.add(etsObjectFixfee.getCreationDate());
        sqlArgs.add(etsObjectFixfee.getCreatedBy());
        sqlArgs.add(etsObjectFixfee.getCreatedBy());
        sqlArgs.add(etsObjectFixfee.getLastUpdateDate());
        sqlArgs.add(etsObjectFixfee.getLastUpdateDate());
        sqlArgs.add(etsObjectFixfee.getLastUpdateBy());
        sqlArgs.add(etsObjectFixfee.getLastUpdateBy());
        sqlArgs.add(etsObjectFixfee.getObjectNo());
        sqlArgs.add(etsObjectFixfee.getObjectNo());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：根据外键关联字段 objectNo 构造查询数据SQL。
     * 框架自动生成数据基站维修成本(EAM) ETS_OBJECT_FIXFEE详细信息查询SQLModel，请根据实际需要修改。
     * @param objectNo String
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    private SQLModel getDataByObjectNoModel(int objectNo) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT "
                + " SYSTEM_ID,"
                + " FIX_DATE,"
                + " AMOUNT,"
                + " FIX_NO,"
                + " ATTRIBUTE1,"
                + " ATTRIBUTE2,"
                + " REMARK,"
                + " CREATION_DATE,"
                + " CREATED_BY,"
                + " LAST_UPDATE_DATE,"
                + " LAST_UPDATE_BY"
                + " FROM"
                + " ETS_OBJECT_FIXFEE"
                + " WHERE"
                + " OBJECT_NO = ?";
        sqlArgs.add(objectNo);

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
        EtsObjectFixfeeDTO etsObjectFixfee = (EtsObjectFixfeeDTO) dtoParameter;
        if (foreignKey.equals("objectNo")) {
            sqlModel = getDataByObjectNoModel(etsObjectFixfee.getObjectNo());
        }
        return sqlModel;
    }

    /**
     * 功能：根据外键关联字段 objectNo 构造数据删除SQL。
     * 框架自动生成数据基站维修成本(EAM) ETS_OBJECT_FIXFEE 数据删除SQLModel，请根据实际需要修改。
     * @param objectNo String
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    private SQLModel getDeleteByObjectNoModel(int objectNo) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM"
                + " ETS_OBJECT_FIXFEE"
                + " WHERE"
                + " OBJECT_NO = ?";
        sqlArgs.add(objectNo);

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
        EtsObjectFixfeeDTO etsObjectFixfee = (EtsObjectFixfeeDTO) dtoParameter;
        if (foreignKey.equals("objectNo")) {
            sqlModel = getDeleteByObjectNoModel(etsObjectFixfee.getObjectNo());
        }
        return sqlModel;
    }

    /**
     * 功能：框架自动生成基站维修成本(EAM) ETS_OBJECT_FIXFEE页面翻页查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getPageQueryModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsObjectFixfeeDTO etsObjectFixfee = (EtsObjectFixfeeDTO) dtoParameter;
        String sqlStr = "SELECT EO.WORKORDER_OBJECT_NAME,\n" +
                "       EOF.FIX_DATE,\n" +
                "       EOF.AMOUNT,\n" +
                "       EOF.REMARK,\n" +
                "       EOF.CREATION_DATE,\n" +
                "       SU.USERNAME," +
                "        EOF.SYSTEM_ID," +
                "       EO.WORKORDER_OBJECT_CODE\n" +
                "  FROM ETS_OBJECT_FIXFEE EOF, ETS_OBJECT EO, SF_USER SU\n" +
                " WHERE EOF.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                "   AND SU.USER_ID = EOF.CREATED_BY\n" +
                "   AND EO.WORKORDER_OBJECT_NAME LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_NAME)\n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EOF.CREATION_DATE >= TO_DATE(?, 'YYYY-MM-DD'))\n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EOF.CREATION_DATE < TO_DATE(?, 'YYYY-MM-DD')+1)" +
                "   AND EO.ORGANIZATION_ID=CONVERT(INT, dbo.NVL(?, CONVERT(VARCHAR, EO.ORGANIZATION_ID)))";

        sqlArgs.add(etsObjectFixfee.getWorkorderObjectName());
        sqlArgs.add(etsObjectFixfee.getFromDate());
        sqlArgs.add(etsObjectFixfee.getFromDate());
        sqlArgs.add(etsObjectFixfee.getToDate());
        sqlArgs.add(etsObjectFixfee.getToDate());
        sqlArgs.add(etsObjectFixfee.getCompany());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

}