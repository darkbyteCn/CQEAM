package com.sino.ams.web.ele.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.web.ele.dto.EtsObjectEleDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: EtsObjectEleModel</p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author yuyao
 * @version 1.0
 */


public class EtsObjectEleModel extends BaseSQLProducer {

    private SfUserDTO sfUser = null;

    /**
     * 功能：基站电费维护表(EAM) ETS_OBJECT_ELE 数据库SQL构造层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter EtsObjectEleDTO 本次操作的数据
     */
    public EtsObjectEleModel(SfUserDTO userAccount, EtsObjectEleDTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
    }

    /**
     * 功能：框架自动生成基站电费维护表(EAM) ETS_OBJECT_ELE数据插入SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据插入用SQLModel
     */
    public SQLModel getDataCreateModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsObjectEleDTO etsObjectEle = (EtsObjectEleDTO) dtoParameter;
        String sqlStr = "INSERT INTO ETS_OBJECT_ELE\n" +
                "  (SYSTEMID,\n" +
                "   WORKORDER_OBJECT_NO,\n" +
                "   PERIOD,\n" +
                "   UNIT_PRICE,\n" +
                "   QUANTITY,\n" +
                /*"   START_DATE,\n" +
                "   END_DATE,\n" +*/
                "   REMARK,\n" +
                "   CREATION_DATE,\n" +
                "   CREATED_BY," +
                "   AMMETER_READING)\n" +
                "VALUES\n" +
                "  ( NEWID() ,  ?, ?, ?, ?, ?, GETDATE(), ?, ?)";

        sqlArgs.add(etsObjectEle.getWorkorderObjectNo());
        sqlArgs.add(etsObjectEle.getPeriod());
        sqlArgs.add(etsObjectEle.getUnitPrice());
        sqlArgs.add(etsObjectEle.getQuantity());
      /*  sqlArgs.add(etsObjectEle.getStartDate());
        sqlArgs.add(etsObjectEle.getEndDate());*/
        sqlArgs.add(etsObjectEle.getRemark());
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(etsObjectEle.getAmmeterReading());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成基站电费维护表(EAM) ETS_OBJECT_ELE数据更新SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据更新用SQLModel
     */
    public SQLModel getDataUpdateModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsObjectEleDTO etsObjectEle = (EtsObjectEleDTO) dtoParameter;
        String sqlStr = "UPDATE ETS_OBJECT_ELE"
                + " SET"
                + " WORKORDER_OBJECT_NO = ?,"
                + " PERIOD = ?,"
                + " UNIT_PRICE = ?,"
                + " QUANTITY = ?,"
                + " AMMETER_READING = ?,"
             /*   + " END_DATE = ?,"*/
                + " REMARK = ?,"
                + " LAST_UPDATE_DATE = GETDATE(),"
                + " LAST_UPDATE_BY = ?"
                + " WHERE"
                + " SYSTEMID = ?";

        sqlArgs.add(etsObjectEle.getWorkorderObjectNo());
        sqlArgs.add(etsObjectEle.getPeriod());
        sqlArgs.add(etsObjectEle.getUnitPrice());
        sqlArgs.add(etsObjectEle.getQuantity());
        sqlArgs.add(etsObjectEle.getAmmeterReading());
        /*sqlArgs.add(etsObjectEle.getEndDate());*/
        sqlArgs.add(etsObjectEle.getRemark());
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(etsObjectEle.getSystemid());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成基站电费维护表(EAM) ETS_OBJECT_ELE数据删除SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据删除用SQLModel
     */
    public SQLModel getDataDeleteModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsObjectEleDTO etsObjectEle = (EtsObjectEleDTO) dtoParameter;
        String sqlStr = "DELETE FROM"
                + " ETS_OBJECT_ELE"
                + " WHERE"
                + " WORKORDER_OBJECT_NO = ?";
        sqlArgs.add(etsObjectEle.getWorkorderObjectNo());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成基站电费维护表(EAM) ETS_OBJECT_ELE数据详细信息查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsObjectEleDTO etsObjectEle = (EtsObjectEleDTO) dtoParameter;
        String sqlStr = "SELECT EOE.SYSTEMID,\n" +
                "       EOE.WORKORDER_OBJECT_NO,\n" +
                "       EOE.PERIOD,\n" +
                "       EOE.UNIT_PRICE,\n" +
                "       EOE.QUANTITY,\n" +
                "       EOE.REMARK,\n" +
                "       EOE.AMMETER_READING,\n" +
                "       EO.WORKORDER_OBJECT_NAME,\n" +
                "       EOE.LAST_UPDATE_DATE,\n" +
                "       SU.USERNAME LAST_UPDATE_BY\n" +
                "  FROM ETS_OBJECT_ELE EOE, ETS_OBJECT EO, SF_USER SU\n" +
                " WHERE EO.WORKORDER_OBJECT_NO = EOE.WORKORDER_OBJECT_NO\n" +
                "   AND EOE.LAST_UPDATE_BY *= SU.USER_ID\n" +
                "   AND SYSTEMID = ?";
        sqlArgs.add(etsObjectEle.getSystemid());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成基站电费维护表(EAM) ETS_OBJECT_ELE多条数据信息查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回多条数据信息查询用SQLModel
     */
    public SQLModel getDataMuxModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsObjectEleDTO etsObjectEle = (EtsObjectEleDTO) dtoParameter;
        String sqlStr = "SELECT "
                + " SYSTEMID,"
                + " WORKORDER_OBJECT_NO,"
                + " PERIOD,"
                + " UNIT_PRICE,"
                + " QUANTITY,"
                + " START_DATE,"
                + " END_DATE,"
                + " REMARK,"
                + " CREATION_DATE,"
                + " CREATED_BY,"
                + " LAST_UPDATE_DATE,"
                + " LAST_UPDATE_BY,"
                + " AMMETER_READING"
                + " FROM"
                + " ETS_OBJECT_ELE"
                + " WHERE"
                + "( " + SyBaseSQLUtil.isNull() + "  OR SYSTEMID LIKE ?)"
                + "( " + SyBaseSQLUtil.isNull() + "  OR WORKORDER_OBJECT_NO LIKE ?)"
                + "( " + SyBaseSQLUtil.isNull() + "  OR PERIOD LIKE ?)"
                + "( " + SyBaseSQLUtil.isNull() + "  OR UNIT_PRICE LIKE ?)"
                + "( " + SyBaseSQLUtil.isNull() + "  OR QUANTITY LIKE ?)"
                + "( " + SyBaseSQLUtil.isNull() + "  OR START_DATE LIKE ?)"
                + "( " + SyBaseSQLUtil.isNull() + "  OR END_DATE LIKE ?)"
                + "( " + SyBaseSQLUtil.isNull() + "  OR REMARK LIKE ?)"
                + "( " + SyBaseSQLUtil.isNull() + "  OR CREATION_DATE LIKE ?)"
                + "( " + SyBaseSQLUtil.isNull() + "  OR CREATED_BY LIKE ?)"
                + "( " + SyBaseSQLUtil.isNull() + "  OR LAST_UPDATE_DATE LIKE ?)"
                + "( " + SyBaseSQLUtil.isNull() + "  OR LAST_UPDATE_BY LIKE ?)";
        sqlArgs.add(etsObjectEle.getSystemid());
        sqlArgs.add(etsObjectEle.getSystemid());
        sqlArgs.add(etsObjectEle.getWorkorderObjectNo());
        sqlArgs.add(etsObjectEle.getWorkorderObjectNo());
        sqlArgs.add(etsObjectEle.getPeriod());
        sqlArgs.add(etsObjectEle.getPeriod());
        sqlArgs.add(etsObjectEle.getUnitPrice());
        sqlArgs.add(etsObjectEle.getUnitPrice());
        sqlArgs.add(etsObjectEle.getQuantity());
        sqlArgs.add(etsObjectEle.getQuantity());
        sqlArgs.add(etsObjectEle.getStartDate());
        sqlArgs.add(etsObjectEle.getStartDate());
        sqlArgs.add(etsObjectEle.getEndDate());
        sqlArgs.add(etsObjectEle.getEndDate());
        sqlArgs.add(etsObjectEle.getRemark());
        sqlArgs.add(etsObjectEle.getRemark());
        sqlArgs.add(etsObjectEle.getCreationDate());
        sqlArgs.add(etsObjectEle.getCreationDate());
        sqlArgs.add(etsObjectEle.getCreatedBy());
        sqlArgs.add(etsObjectEle.getCreatedBy());
        sqlArgs.add(etsObjectEle.getLastUpdateDate());
        sqlArgs.add(etsObjectEle.getLastUpdateDate());
        sqlArgs.add(etsObjectEle.getLastUpdateBy());
        sqlArgs.add(etsObjectEle.getLastUpdateBy());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：根据外键关联字段 workorderObjectNo 构造查询数据SQL。
     * 框架自动生成数据基站电费维护表(EAM) ETS_OBJECT_ELE详细信息查询SQLModel，请根据实际需要修改。
     * @param workorderObjectNo String
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    private SQLModel getDataByWorkorderObjectNoModel(String workorderObjectNo) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT "
                + " SYSTEMID,"
                + " PERIOD,"
                + " UNIT_PRICE,"
                + " QUANTITY,"
                + " START_DATE,"
                + " END_DATE,"
                + " REMARK,"
                + " CREATION_DATE,"
                + " CREATED_BY,"
                + " LAST_UPDATE_DATE,"
                + " LAST_UPDATE_BY,"
                + " AMMETER_READING"
                + " FROM"
                + " ETS_OBJECT_ELE"
                + " WHERE"
                + " WORKORDER_OBJECT_NO = ?";
        sqlArgs.add(workorderObjectNo);

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
        EtsObjectEleDTO etsObjectEle = (EtsObjectEleDTO) dtoParameter;
        if (foreignKey.equals("workorderObjectNo")) {
            sqlModel = getDataByWorkorderObjectNoModel(etsObjectEle.getWorkorderObjectNo());
        }
        return sqlModel;
    }

    /**
     * 功能：根据外键关联字段 workorderObjectNo 构造数据删除SQL。
     * 框架自动生成数据基站电费维护表(EAM) ETS_OBJECT_ELE 数据删除SQLModel，请根据实际需要修改。
     * @param workorderObjectNo String
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    private SQLModel getDeleteByWorkorderObjectNoModel(String workorderObjectNo) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM"
                + " ETS_OBJECT_ELE"
                + " WHERE"
                + " WORKORDER_OBJECT_NO = ?";
        sqlArgs.add(workorderObjectNo);

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
        EtsObjectEleDTO etsObjectEle = (EtsObjectEleDTO) dtoParameter;
        if (foreignKey.equals("workorderObjectNo")) {
            sqlModel = getDeleteByWorkorderObjectNoModel(etsObjectEle.getWorkorderObjectNo());
        }
        return sqlModel;
    }

    /**
     * 功能：框架自动生成基站电费维护表(EAM) ETS_OBJECT_ELE页面翻页查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getPageQueryModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsObjectEleDTO etsObjectEle = (EtsObjectEleDTO) dtoParameter;
        String sqlStr = "SELECT EOE.SYSTEMID,\n" +
                "       EO.WORKORDER_OBJECT_NAME,\n" +
                "       EOE.PERIOD,\n" +
                "       EOE.UNIT_PRICE,\n" +
                "       EOE.QUANTITY,\n" +
                "       EOE.CREATION_DATE,\n" +
                "       SU.USERNAME\n" +
                "  FROM ETS_OBJECT_ELE EOE, ETS_OBJECT EO, SF_USER SU\n" +
                " WHERE EOE.WORKORDER_OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                "   AND SU.USER_ID = EOE.CREATED_BY\n" +
                "   AND EO.WORKORDER_OBJECT_NAME LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_NAME)\n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EOE.CREATION_DATE >= TO_DATE(?, 'YYYY-MM-DD'))\n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EOE.CREATION_DATE < TO_DATE(?, 'YYYY-MM-DD')+1)\n" +
                "   AND EOE.PERIOD LIKE dbo.NVL(?, EOE.PERIOD)" +
                "   AND EO.ORGANIZATION_ID = CONVERT(INT, dbo.NVL(?, CONVERT(VARCHAR, EO.ORGANIZATION_ID)))";

        sqlArgs.add(etsObjectEle.getWorkorderObjectName());
        sqlArgs.add(etsObjectEle.getFromDate());
        sqlArgs.add(etsObjectEle.getFromDate());
        sqlArgs.add(etsObjectEle.getToDate());
        sqlArgs.add(etsObjectEle.getToDate());
        sqlArgs.add(etsObjectEle.getPeriod());
        sqlArgs.add(etsObjectEle.getCompany());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

}