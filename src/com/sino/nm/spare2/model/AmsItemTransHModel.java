package com.sino.nm.spare2.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.ConvertUtil;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.nm.spare2.dto.AmsItemTransHDTO;


/**
 * <p>Title: AmsItemTransHModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsItemTransHModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class AmsItemTransHModel extends BaseSQLProducer {

    private SfUserDTO sfUser = null;

    /**
     * 功能：备件事务头表(AMS) AMS_ITEM_TRANS_H 数据库SQL构造层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsItemTransHDTO 本次操作的数据
     */
    public AmsItemTransHModel(SfUserDTO userAccount, AmsItemTransHDTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
    }

    /**
     * 功能：框架自动生成备件事务头表(AMS) AMS_ITEM_TRANS_H数据插入SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据插入用SQLModel
     */
    public SQLModel getDataCreateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) dtoParameter;
            String sqlStr = "INSERT INTO "
                    + " AMS_ITEM_TRANS_H("
                    + " TRANS_ID,"
                    + " TRANS_NO,"
                    + " TRANS_TYPE,"
                    + " TRANS_STATUS,"
                    + " FROM_USER,"
                    + " TO_USER,"
                    + " FROM_DEPT,"
                    + " TO_DEPT,"
                    + " FROM_OBJECT_NO,"
                    + " TO_OBJECT_NO,"
                    + " FROM_ORGANIZATION_ID,"
                    + " TO_ORGANIZATION_ID,"
                    + " TRANS_DATE,"
                    + " RCV_USER,"
                    + " CREATION_DATE,"
                    + " CREATED_BY,"
                    + " REMARK, "
                    + " ATTRIBUTE1,"
                    + " ATTRIBUTE2,"
                    + " ATTRIBUTE3,"
                    + " RESPECT_RETURN_DATE,"
                    + " REASON,"
                    + " DEPT_CODE,"
                    + " AUTHORIZATION_USER,"
                    + " INV_MANAGER," +
                    "   FROM_GROUP"
                    + ") VALUES ("
                    + " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

            sqlArgs.add(amsItemTransH.getTransId());
            sqlArgs.add(amsItemTransH.getTransNo());
            sqlArgs.add(amsItemTransH.getTransType());
            sqlArgs.add(amsItemTransH.getTransStatus());
            sqlArgs.add(amsItemTransH.getFromUser());
            sqlArgs.add(amsItemTransH.getToUser());
            sqlArgs.add(amsItemTransH.getFromDept());
            sqlArgs.add(amsItemTransH.getToDept());
            sqlArgs.add(amsItemTransH.getFromObjectNo());
            sqlArgs.add(amsItemTransH.getToObjectNo());
            sqlArgs.add(amsItemTransH.getFromOrganizationId());
            sqlArgs.add(amsItemTransH.getToOrganizationId());
            sqlArgs.add(amsItemTransH.getTransDate());
            sqlArgs.add(amsItemTransH.getRcvUser());
            sqlArgs.add(sfUser.getUserId());
            sqlArgs.add(amsItemTransH.getRemark());
//            sqlArgs.add(amsItemTransH.getLastUpdateDate());
//            sqlArgs.add(amsItemTransH.getLastUpdateBy());
            sqlArgs.add(amsItemTransH.getAttribute1());
            sqlArgs.add(amsItemTransH.getAttribute2());
            sqlArgs.add(amsItemTransH.getAttribute3());
            sqlArgs.add(amsItemTransH.getRespectReturnDate());
            sqlArgs.add(amsItemTransH.getReason());
            sqlArgs.add(amsItemTransH.getDeptCode());
            sqlArgs.add(amsItemTransH.getAuthorizationUser());
            sqlArgs.add(amsItemTransH.getInvManager());
            sqlArgs.add(sfUser.getCurrGroupId());
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException e) {
            throw new SQLModelException(e);
        }
        return sqlModel;
    }

    /**
     * 功能：框架自动生成备件事务头表(AMS) AMS_ITEM_TRANS_H数据更新SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据更新用SQLModel
     */
    public SQLModel getDataUpdateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();        
        List sqlArgs = new ArrayList();
        AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) dtoParameter;
        String sqlStr = "UPDATE AMS_ITEM_TRANS_H"
                + " SET"
                + " TRANS_NO = ?,"
                + " TRANS_TYPE = ?,"
                + " TRANS_STATUS = ?,"
                + " FROM_USER = ?,"
                + " TO_USER = ?,"
                + " FROM_DEPT = ?,"
                + " TO_DEPT = ?,"
                + " FROM_OBJECT_NO = ?,"
                + " TO_OBJECT_NO = ?,"
                + " FROM_ORGANIZATION_ID = ?,"
//                + " TO_ORGANIZATION_ID = ?,"
                + " TRANS_DATE = ?,"
                + " RCV_USER = ?,"
                + " LAST_UPDATE_DATE = ?,"
                + " LAST_UPDATE_BY = ?,"
                + " REMARK = ?, "
                + " RESPECT_RETURN_DATE = ?, "
                + " REASON = ?,"
                + " DEPT_CODE = ?,"
                + " AUTHORIZATION_USER = ?,"
                + " INV_MANAGER = ?," +
                "  FROM_GROUP=?"
                + " WHERE"
                + " TRANS_ID = ?";

        try {
            sqlArgs.add(amsItemTransH.getTransNo());
            sqlArgs.add(amsItemTransH.getTransType());
            sqlArgs.add(amsItemTransH.getTransStatus());
            sqlArgs.add(amsItemTransH.getFromUser());
            sqlArgs.add(amsItemTransH.getToUser());
            sqlArgs.add(amsItemTransH.getFromDept());
            sqlArgs.add(amsItemTransH.getToDept());
            sqlArgs.add(amsItemTransH.getFromObjectNo());
            sqlArgs.add(amsItemTransH.getToObjectNo());
            sqlArgs.add(amsItemTransH.getFromOrganizationId());
//            sqlArgs.add(amsItemTransH.getToOrganizationId());
            sqlArgs.add(amsItemTransH.getTransDate());
            sqlArgs.add(amsItemTransH.getRcvUser());
            sqlArgs.add(amsItemTransH.getLastUpdateDate());
            sqlArgs.add(amsItemTransH.getLastUpdateBy());
            sqlArgs.add(amsItemTransH.getRemark());
            sqlArgs.add(amsItemTransH.getRespectReturnDate());
            sqlArgs.add(amsItemTransH.getReason());
            sqlArgs.add(amsItemTransH.getDeptCode());
            sqlArgs.add(amsItemTransH.getAuthorizationUser());
            sqlArgs.add(amsItemTransH.getInvManager());
            sqlArgs.add(sfUser.getCurrGroupId());
            sqlArgs.add(amsItemTransH.getTransId());
        } catch (CalendarException e) {
            throw new SQLModelException(e);
        }

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成备件事务头表(AMS) AMS_ITEM_TRANS_H数据删除SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据删除用SQLModel
     */
    public SQLModel getDataDeleteModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) dtoParameter;
        String sqlStr = "DELETE FROM"
                + " AMS_ITEM_TRANS_H"
                + " WHERE"
                + " TRANS_ID = ?";
        sqlArgs.add(amsItemTransH.getTransId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成备件事务头表(AMS) AMS_ITEM_TRANS_H数据详细信息查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) dtoParameter;
        String sqlStr = "SELECT AITH.TRANS_ID,\n" +
                "       AITH.TRANS_NO,\n" +
                "       AITH.TRANS_TYPE,\n" +
                "       AITH.TRANS_STATUS,\n" +
                "       AITH.CREATED_BY,\n" +
                "       AITH.CREATION_DATE,\n" +
                "       AITH.TRANS_DATE,\n" +
                "       AITH.FROM_OBJECT_NO,\n" +
                "       AITH.TO_OBJECT_NO,\n" +
                "       AITH.FROM_ORGANIZATION_ID,\n" +
                "       AITH.TO_ORGANIZATION_ID,\n" +
//                "       TO_CHAR(AITH.RESPECT_RETURN_DATE, 'YYYY-MM-DD') RESPECT_RETURN_DATE,\n" +
                "       AITH.RESPECT_RETURN_DATE,\n" +
                "       EOCM.COMPANY FROM_ORGANIZATION_NAME,\n" +
                "       EOCM2.COMPANY TO_ORGANIZATION_NAME,\n" +
                "       SUV.USERNAME CREATED_USER,\n" +
                "       EO.WORKORDER_OBJECT_NAME FROM_OBJECT_NAME,\n" +
                "       EO.WORKORDER_OBJECT_LOCATION FROM_OBJECT_LOCATION,\n" +
                "       EO2.WORKORDER_OBJECT_NAME TO_OBJECT_NAME,\n" +
                "       EO2.WORKORDER_OBJECT_LOCATION TO_OBJECT_LOCATION,\n" +
                "       EFV.VALUE TRANS_STATUS_NAME," +
                "       AITH.REMARK, \n" +
                "       AITH.ATTRIBUTE1, \n" +
                "       AITH.ATTRIBUTE2, \n" +
                "       AITH.ATTRIBUTE3, \n" +
                "       AITH.REASON, \n" +
                "       AITH.DEPT_CODE, \n" +
                "       AITH.AUTHORIZATION_USER, \n" +
                "       AITH.INV_MANAGER, \n" +
                "       AMD.DEPT_NAME," +
                "       AITH.FROM_GROUP," +
                "       dbo.GET_FLEX_VALUE(AITH.SPARE_MANUFACTURER, 'SERVICE_SPARE') SPARE_MANUFACTURER_NAME,\n" +
                "       AITH.SPARE_TYPE,\n" +
                "       AITH.SPARE_MANUFACTURER,\n" +
                "       AITH.FEEDBACK_TYPE,\n" +
                "       AITH.FEEDBACK_INFO \n" +
                "  FROM AMS_ITEM_TRANS_H   AITH,\n" +
                "       ETS_OBJECT         EO,\n" +
                "       ETS_OBJECT         EO2,\n" +
                "       SF_USER_V          SUV,\n" +
                "       ETS_OU_CITY_MAP    EOCM,\n" +
                "       ETS_OU_CITY_MAP    EOCM2,\n" +
                "       ETS_FLEX_VALUES    EFV,\n" +
                "       ETS_FLEX_VALUE_SET EFVS,\n" +
                "       AMS_MIS_DEPT       AMD\n" +
                " WHERE AITH.FROM_OBJECT_NO *= EO.WORKORDER_OBJECT_NO\n" +
                "   AND AITH.TO_OBJECT_NO *= EO2.WORKORDER_OBJECT_NO\n" +
                "   AND AITH.CREATED_BY = SUV.USER_ID\n" +
                "   AND AITH.TRANS_STATUS = EFV.CODE\n" +
                "   AND AITH.FROM_ORGANIZATION_ID *= EOCM.ORGANIZATION_ID\n" +
                "   AND AITH.TO_ORGANIZATION_ID *= EOCM2.ORGANIZATION_ID\n" +
                "   AND EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID\n" +
                "   AND EFVS.CODE = 'ORDER_STATUS'" +
                "   AND AITH.DEPT_CODE *= AMD.DEPT_CODE" +
                "   AND TRANS_ID = ?";
        sqlArgs.add(amsItemTransH.getTransId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成备件事务头表(AMS) AMS_ITEM_TRANS_H多条数据信息查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回多条数据信息查询用SQLModel
     */
    public SQLModel getDataMuxModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) dtoParameter;
        String sqlStr = "SELECT "
                + " TRANS_ID,"
                + " TRANS_NO,"
                + " TRANS_TYPE,"
                + " TRANS_STATUS,"
                + " FROM_USER,"
                + " TO_USER,"
                + " FROM_DEPT,"
                + " TO_DEPT,"
                + " FROM_OBJECT_NO,"
                + " TO_OBJECT_NO,"
                + " FROM_ORGANIZATION_ID,"
                + " TO_ORGANIZATION_ID,"
                + " TRANS_DATE,"
                + " RCV_USER,"
                + " CREATION_DATE,"
                + " CREATED_BY,"
                + " LAST_UPDATE_DATE,"
                + " LAST_UPDATE_BY"
                + " FROM"
                + " AMS_ITEM_TRANS_H"
                + " WHERE"
                + "AND ("+ SyBaseSQLUtil.nullStringParam() +" OR TRANS_ID LIKE ?)"
                + "AND ("+ SyBaseSQLUtil.nullStringParam() +" OR TRANS_NO LIKE ?)"
                + "AND TRANS_TYPE = dbo.NVL(?,TRANS_TYPE)"
                + "AND ("+ SyBaseSQLUtil.nullStringParam() +" OR TRANS_STATUS LIKE ?)"
                + "AND (? = -1 OR FROM_USER LIKE ?)"
                + "AND (? = -1 OR TO_USER LIKE ?)"
                + "AND ("+ SyBaseSQLUtil.nullStringParam() +" OR FROM_DEPT LIKE ?)"
                + "AND ("+ SyBaseSQLUtil.nullStringParam() +" OR TO_DEPT LIKE ?)"
                + "AND ("+ SyBaseSQLUtil.nullStringParam() +" OR FROM_OBJECT_NO LIKE ?)"
                + "AND ("+ SyBaseSQLUtil.nullStringParam() +" OR TO_OBJECT_NO LIKE ?)"
                + "AND (? = -1 OR FROM_ORGANIZATION_ID LIKE ?)"
                + "AND (? = -1 OR TO_ORGANIZATION_ID LIKE ?)"
                + "AND (? IS NULL OR TRANS_DATE LIKE ?)"
                + "AND (? = -1 OR RCV_USER LIKE ?)"
                + "AND (? IS NULL OR CREATION_DATE LIKE ?)"
                + "AND (? = -1 OR CREATED_BY LIKE ?)"
                + "AND (? IS NULL OR LAST_UPDATE_DATE LIKE ?)"
                + "AND (? = -1 OR LAST_UPDATE_BY LIKE ?)";
        sqlArgs.add(amsItemTransH.getTransId());
        sqlArgs.add(amsItemTransH.getTransId());
        sqlArgs.add(amsItemTransH.getTransId());
        sqlArgs.add(amsItemTransH.getTransNo());
        sqlArgs.add(amsItemTransH.getTransNo());
        sqlArgs.add(amsItemTransH.getTransNo());
        sqlArgs.add(amsItemTransH.getTransType());
        sqlArgs.add(amsItemTransH.getTransStatus());
        sqlArgs.add(amsItemTransH.getTransStatus());
        sqlArgs.add(amsItemTransH.getTransStatus());
        sqlArgs.add(amsItemTransH.getFromUser());
        sqlArgs.add(amsItemTransH.getFromUser());
        sqlArgs.add(amsItemTransH.getToUser());
        sqlArgs.add(amsItemTransH.getToUser());
        sqlArgs.add(amsItemTransH.getFromDept());
        sqlArgs.add(amsItemTransH.getFromDept());
        sqlArgs.add(amsItemTransH.getFromDept());
        sqlArgs.add(amsItemTransH.getToDept());
        sqlArgs.add(amsItemTransH.getToDept());
        sqlArgs.add(amsItemTransH.getToDept());
        sqlArgs.add(amsItemTransH.getFromObjectNo());
        sqlArgs.add(amsItemTransH.getFromObjectNo());
        sqlArgs.add(amsItemTransH.getFromObjectNo());
        sqlArgs.add(amsItemTransH.getToObjectNo());
        sqlArgs.add(amsItemTransH.getToObjectNo());
        sqlArgs.add(amsItemTransH.getToObjectNo());
        sqlArgs.add(amsItemTransH.getFromOrganizationId());
        sqlArgs.add(amsItemTransH.getFromOrganizationId());
        sqlArgs.add(amsItemTransH.getToOrganizationId());
        sqlArgs.add(amsItemTransH.getToOrganizationId());
        try {
            sqlArgs.add(amsItemTransH.getTransDate());
            sqlArgs.add(amsItemTransH.getTransDate());
            sqlArgs.add(amsItemTransH.getRcvUser());
            sqlArgs.add(amsItemTransH.getRcvUser());
            sqlArgs.add(amsItemTransH.getCreationDate());
            sqlArgs.add(amsItemTransH.getCreationDate());
            sqlArgs.add(amsItemTransH.getCreatedBy());
            sqlArgs.add(amsItemTransH.getCreatedBy());
            sqlArgs.add(amsItemTransH.getLastUpdateDate());
            sqlArgs.add(amsItemTransH.getLastUpdateDate());
        } catch (CalendarException e) {
            throw new SQLModelException(e);
        }
        sqlArgs.add(amsItemTransH.getLastUpdateBy());
        sqlArgs.add(amsItemTransH.getLastUpdateBy());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成备件事务头表(AMS) AMS_ITEM_TRANS_H页面翻页查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) dtoParameter;
        String sqlStr = "SELECT AITH.TRANS_ID,\n" +
                "       AITH.TRANS_NO,\n" +
                "       AITH.CREATED_BY,\n" +
                "       AITH.CREATION_DATE,\n" +
                "       AITH.TRANS_DATE,\n" +
                "       SUV.USERNAME CREATED_USER,\n" +
                "       EO.WORKORDER_OBJECT_NAME,\n" +
                "       EFV.VALUE ORDER_STATUS_NAME\n" +
                "  FROM AMS_ITEM_TRANS_H   AITH,\n" +
                "       ETS_OBJECT         EO,\n" +
                "       SF_USER_V          SUV,\n" +
                "       ETS_FLEX_VALUES    EFV,\n" +
                "       ETS_FLEX_VALUE_SET EFVS\n" +
                " WHERE AITH.TO_OBJECT_NO *= EO.WORKORDER_OBJECT_NO\n" +
                "   AND AITH.CREATED_BY = SUV.USER_ID\n" +
                "   AND AITH.TRANS_STATUS = EFV.CODE\n" +
                "   AND EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID\n" +
                "   AND EFVS.CODE = 'ORDER_STATUS'\n"
//                + "AND (? IS NULL OR TRANS_NO LIKE ?)"
//                + "AND (? IS NULL OR TRANS_STATUS = ?)"
//                + "AND (? IS NULL OR TO_OBJECT_NO = ?)"
//                + "AND (? IS NULL OR FROM_ORGANIZATION_ID = ?)"
//                + "AND (? IS NULL OR TO_ORGANIZATION_ID = ?)"
                + " AND (TRANS_NO LIKE ? OR "+ SyBaseSQLUtil.nullStringParam() +")"
                + " AND (TRANS_STATUS = ? OR "+ SyBaseSQLUtil.nullStringParam() +")"
                + " AND (TO_OBJECT_NO = ? OR "+ SyBaseSQLUtil.nullStringParam() +")"
                + " AND (FROM_ORGANIZATION_ID = ? OR -1 = ?)"
                + " AND (TO_ORGANIZATION_ID = ? OR -1 = ?)"                
                + " AND (?='' OR ? IS NULL OR AITH.TRANS_TYPE = ?)"+
//                + " AND (? IS NULL OR AITH.CREATION_DATE >= ?)"
////                + "AND (?='' OR ? IS NULL OR AITH.CREATION_DATE <= CONVERT(DATE, ?))" +
//                + " AND (? IS NULL OR AITH.CREATION_DATE < DATEADD(DAY,1,?))" +
                "AND (AITH.CREATION_DATE >= ? OR "+ SyBaseSQLUtil.nullStringParam() +")"+
                "AND (AITH.CREATION_DATE < DATEADD(DAY,1,?) OR "+ SyBaseSQLUtil.nullStringParam() +")"+
                "  ORDER BY AITH.CREATION_DATE DESC";
        sqlArgs.add(amsItemTransH.getTransNo());
        sqlArgs.add(amsItemTransH.getTransNo());
        sqlArgs.add(amsItemTransH.getTransNo());
//		sqlArgs.add(amsItemTransH.getTransType());
//		sqlArgs.add(amsItemTransH.getTransType());
        sqlArgs.add(amsItemTransH.getTransStatus());
        sqlArgs.add(amsItemTransH.getTransStatus());
        sqlArgs.add(amsItemTransH.getTransStatus());
//		sqlArgs.add(amsItemTransH.getFromUser());
//		sqlArgs.add(amsItemTransH.getFromUser());
//		sqlArgs.add(amsItemTransH.getToUser());
//		sqlArgs.add(amsItemTransH.getToUser());
//		sqlArgs.add(amsItemTransH.getFromDept());
//		sqlArgs.add(amsItemTransH.getFromDept());
//		sqlArgs.add(amsItemTransH.getToDept());
//		sqlArgs.add(amsItemTransH.getToDept());
//		sqlArgs.add(amsItemTransH.getFromObjectNo());
//		sqlArgs.add(amsItemTransH.getFromObjectNo());
        sqlArgs.add(amsItemTransH.getToObjectNo());
        sqlArgs.add(amsItemTransH.getToObjectNo());
        sqlArgs.add(amsItemTransH.getToObjectNo());
        sqlArgs.add(amsItemTransH.getFromOrganizationId());
        sqlArgs.add(amsItemTransH.getFromOrganizationId());
        if (amsItemTransH.getTransType().equals(DictConstant.ZKZY)) {     //子库转移
            sqlArgs.add(amsItemTransH.getToOrganizationId());
            sqlArgs.add(amsItemTransH.getToOrganizationId());
        } else {
            sqlArgs.add(sfUser.getOrganizationId());
            sqlArgs.add(sfUser.getOrganizationId());
        }
        sqlArgs.add(amsItemTransH.getTransType());
        sqlArgs.add(amsItemTransH.getTransType());
        sqlArgs.add(amsItemTransH.getTransType());
        try {
            sqlArgs.add(amsItemTransH.getFromDate());
            sqlArgs.add(amsItemTransH.getFromDate());
            sqlArgs.add(amsItemTransH.getFromDate());
            sqlArgs.add(amsItemTransH.getToDate());
            sqlArgs.add(amsItemTransH.getToDate());
            sqlArgs.add(amsItemTransH.getToDate());
        } catch (CalendarException e) {
            throw new SQLModelException(e);
        }
//		sqlArgs.add(amsItemTransH.getRcvUser());
//		sqlArgs.add(amsItemTransH.getRcvUser());
//		sqlArgs.add(amsItemTransH.getCreationDate());
//		sqlArgs.add(amsItemTransH.getCreationDate());
//		sqlArgs.add(amsItemTransH.getCreatedBy());
//		sqlArgs.add(amsItemTransH.getCreatedBy());
//		sqlArgs.add(amsItemTransH.getLastUpdateDate());
//		sqlArgs.add(amsItemTransH.getLastUpdateDate());
//		sqlArgs.add(amsItemTransH.getLastUpdateBy());
//		sqlArgs.add(amsItemTransH.getLastUpdateBy());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

}