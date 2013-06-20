package com.sino.ams.spare.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.calen.SimpleDate;
import com.sino.base.constant.calen.DateConstant;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DateException;
import com.sino.base.exception.SQLModelException;

import com.sino.framework.sql.BaseSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.spare.dto.AmsItemTransHDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-12-02
 * Time: 00:00:00
 * To change this template use File | Settings | File Templates.
 */

public class AmsItemTransHModel extends BaseSQLProducer {

    private SfUserDTO sfUser = null;

    public AmsItemTransHModel(SfUserDTO userAccount, AmsItemTransHDTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
    }

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
                    + " REASON,"
                    + " REMARK,"
                    + " VENDOR_ID,"
                    + " ATTRIBUTE4"
                    + ") VALUES ("
                    + " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), ?, ?, ?, ?, ?)";

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
            sqlArgs.add(sfUser.getOrganizationId());
            sqlArgs.add(amsItemTransH.getToOrganizationId());
            sqlArgs.add(amsItemTransH.getTransDate());
            sqlArgs.add(amsItemTransH.getRcvUser());
            sqlArgs.add(amsItemTransH.getCreatedBy());
            sqlArgs.add(amsItemTransH.getSpareReason());
            sqlArgs.add(amsItemTransH.getRemark());
            sqlArgs.add(amsItemTransH.getVendorId());
            sqlArgs.add(amsItemTransH.getAttribute4());//插入单据的总金额

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException e) {
            throw new SQLModelException(e);
        }
        return sqlModel;
    }

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
                + " TRANS_DATE = ?,"
                + " RCV_USER = ?,"
                + " LAST_UPDATE_DATE = ?,"
                + " LAST_UPDATE_BY = ?,"
                + " REASON = ?,"
                + " REMARK=? "
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
            sqlArgs.add(amsItemTransH.getTransDate());
            sqlArgs.add(amsItemTransH.getRcvUser());
            sqlArgs.add(amsItemTransH.getLastUpdateDate());
            sqlArgs.add(amsItemTransH.getLastUpdateBy());
            sqlArgs.add(amsItemTransH.getSpareReason());
            sqlArgs.add(amsItemTransH.getRemark());
            sqlArgs.add(amsItemTransH.getTransId());
        } catch (CalendarException e) {
            throw new SQLModelException(e);
        }

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

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
                "       AITH.ATTRIBUTE4,\n" +
                "       AITH.FROM_DEPT,\n" +
                "       dbo.APP_GET_ORGNIZATION_NAME(AITH.FROM_ORGANIZATION_ID) FROM_ORGANIZATION_NAME,\n" +
                "       dbo.APP_GET_ORGNIZATION_NAME(AITH.TO_ORGANIZATION_ID) TO_ORGANIZATION_NAME,\n" +
                "       dbo.APP_GET_USER_NAME(AITH.CREATED_BY) CREATED_USER,\n" +
                "       EO.WORKORDER_OBJECT_NAME FROM_OBJECT_NAME,\n" +
                "       EO.WORKORDER_OBJECT_LOCATION FROM_OBJECT_LOCATION,\n" +
                "       EO2.WORKORDER_OBJECT_NAME TO_OBJECT_NAME,\n" +
                "       EO2.WORKORDER_OBJECT_LOCATION TO_OBJECT_LOCATION,\n" +
                "       dbo.APP_GET_STATUS_NAME(AITH.TRANS_STATUS) TRANS_STATUS_NAME,\n" +
                "       AITH.REASON,\n" +
                "       AITH.REASON SPARE_REASON,\n" +
                "       dbo.APP_GET_FLEX_VALUE(AITH.REASON, 'SPARE_REASON') SPARE_REASON_D,\n" +
                "       AITH.REMARK,\n" +
                "       AITH.VENDOR_ID,\n" +
                "       ASV.VENDOR_NAME\n" +
                "  FROM AMS_ITEM_TRANS_H AITH,\n" +
                "       ETS_OBJECT       EO,\n" +
                "       ETS_OBJECT       EO2,\n" +
                "       AMS_SPARE_VENDORS ASV\n" +
                " WHERE AITH.FROM_OBJECT_NO *= EO.WORKORDER_OBJECT_NO \n" +
                "       AND AITH.TO_OBJECT_NO *= EO2.WORKORDER_OBJECT_NO \n" +
                "       AND AITH.VENDOR_ID *= ASV.VENDOR_ID \n" +
                "       AND TRANS_ID = ?";
        sqlArgs.add(amsItemTransH.getTransId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

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
                + " REASON,"
                + " TRANS_DATE,"
                + " RCV_USER,"
                + " CREATION_DATE,"
                + " CREATED_BY,"
                + " LAST_UPDATE_DATE,"
                + " LAST_UPDATE_BY"
                + " FROM"
                + " AMS_ITEM_TRANS_H"
                + " WHERE"
                + " AND ( " + SyBaseSQLUtil.isNull() + " OR TRANS_ID LIKE ?)"
                + " AND ( " + SyBaseSQLUtil.isNull() + " OR TRANS_NO LIKE ?)"
                + " AND ( " + SyBaseSQLUtil.isNull() + " OR TRANS_TYPE = ?)"
                + " AND ( " + SyBaseSQLUtil.isNull() + " OR TRANS_STATUS LIKE ?)"
                + " AND ( " + SyBaseSQLUtil.isNull() + " OR FROM_USER LIKE ?)"
                + " AND ( " + SyBaseSQLUtil.isNull() + " OR TO_USER LIKE ?)"
                + " AND ( " + SyBaseSQLUtil.isNull() + " OR FROM_DEPT LIKE ?)"
                + " AND ( " + SyBaseSQLUtil.isNull() + " OR TO_DEPT LIKE ?)"
                + " AND ( " + SyBaseSQLUtil.isNull() + " OR FROM_OBJECT_NO LIKE ?)"
                + " AND ( " + SyBaseSQLUtil.isNull() + " OR TO_OBJECT_NO LIKE ?)"
                + "AND (? = -1 OR FROM_ORGANIZATION_ID = ?)"
                + "AND (? = -1 OR TO_ORGANIZATION_ID = ?)"
                + " AND ( " + SyBaseSQLUtil.isNull() + " OR REASON LIKE ?)"
                + " AND ( " + SyBaseSQLUtil.isNull() + " OR TRANS_DATE = ?)"
                + "AND (? = -1 OR RCV_USER = ?)"
                + " AND ( " + SyBaseSQLUtil.isNull() + " OR CREATION_DATE = ?)"
                + "AND (? = -1 OR CREATED_BY = ?)"
                + " AND ( " + SyBaseSQLUtil.isNull() + " OR LAST_UPDATE_DATE = ?)"
                + "AND (? = -1 OR LAST_UPDATE_BY = ?)";
        sqlArgs.add(amsItemTransH.getTransId());
        sqlArgs.add(amsItemTransH.getTransId());
        sqlArgs.add(amsItemTransH.getTransNo());
        sqlArgs.add(amsItemTransH.getTransNo());
        sqlArgs.add(amsItemTransH.getTransType());
        sqlArgs.add(amsItemTransH.getTransType());
        sqlArgs.add(amsItemTransH.getTransStatus());
        sqlArgs.add(amsItemTransH.getTransStatus());
        sqlArgs.add(amsItemTransH.getFromUser());
        sqlArgs.add(amsItemTransH.getFromUser());
        sqlArgs.add(amsItemTransH.getToUser());
        sqlArgs.add(amsItemTransH.getToUser());
        sqlArgs.add(amsItemTransH.getFromDept());
        sqlArgs.add(amsItemTransH.getFromDept());
        sqlArgs.add(amsItemTransH.getToDept());
        sqlArgs.add(amsItemTransH.getToDept());
        sqlArgs.add(amsItemTransH.getFromObjectNo());
        sqlArgs.add(amsItemTransH.getFromObjectNo());
        sqlArgs.add(amsItemTransH.getToObjectNo());
        sqlArgs.add(amsItemTransH.getToObjectNo());
        sqlArgs.add(amsItemTransH.getFromOrganizationId());
        sqlArgs.add(amsItemTransH.getFromOrganizationId());
        sqlArgs.add(amsItemTransH.getToOrganizationId());
        sqlArgs.add(amsItemTransH.getToOrganizationId());
        sqlArgs.add(amsItemTransH.getSpareReason());
        sqlArgs.add(amsItemTransH.getSpareReason());
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

    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) dtoParameter;
        String sqlStr = "SELECT AITH.TRANS_ID,\n" +
                "       AITH.TRANS_NO,\n" +
                "       AITH.CREATED_BY,\n" +
                "       AITH.ATTRIBUTE4,\n" +
                "       AITH.CREATION_DATE CREATION_DATE,\n" +
                "       AITH.TRANS_DATE TRANS_DATE,\n" +
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
                + " AND ( " + SyBaseSQLUtil.isNull() + " OR TRANS_NO LIKE ?)\n"
                + " AND ( " + SyBaseSQLUtil.isNull() + " OR TRANS_STATUS = ?)\n";
        if (amsItemTransH.getTransType().equals("FXSQ")) {
            sqlStr += " AND AITH.FROM_ORGANIZATION_ID = ?\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + " OR AITH.TRANS_TYPE = ?)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + " OR AITH.CREATION_DATE >= ?)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + " OR AITH.CREATION_DATE <= ?)\n" +
                    " ORDER BY AITH.CREATION_DATE DESC";
        } else {
            sqlStr +=" AND ( " + SyBaseSQLUtil.isNull() + " OR TO_OBJECT_NO = ?)\n" +
                    "   AND (? = -1 OR TO_ORGANIZATION_ID = ?)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + " OR AITH.TRANS_TYPE = ?)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + " OR AITH.CREATION_DATE >= ?)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + " OR AITH.CREATION_DATE <= ?)\n" +
                    " ORDER BY AITH.CREATION_DATE DESC";
        }

        sqlArgs.add(amsItemTransH.getTransNo());
        sqlArgs.add(amsItemTransH.getTransNo());
        sqlArgs.add(amsItemTransH.getTransStatus());
        sqlArgs.add(amsItemTransH.getTransStatus());
        if (amsItemTransH.getTransType().equals("FXSQ")) {
            sqlArgs.add(sfUser.getOrganizationId());
        } else {
            sqlArgs.add(amsItemTransH.getToObjectNo());
            sqlArgs.add(amsItemTransH.getToObjectNo());
            sqlArgs.add(sfUser.getOrganizationId());
            sqlArgs.add(sfUser.getOrganizationId());
        }
        sqlArgs.add(amsItemTransH.getTransType());
        sqlArgs.add(amsItemTransH.getTransType());
        try {
            sqlArgs.add(amsItemTransH.getFromDate());
            sqlArgs.add(amsItemTransH.getFromDate());

            SimpleCalendar sc = amsItemTransH.getToDate();
            SimpleDate sd = sc.getSimpleDate();
            if (!sd.getDateValue().equals("")) {
                sd.adjust(DateConstant.DATE, 1);
                sc.setDate(sd);
            }
            sqlArgs.add(sc);
            sqlArgs.add(sc);
        } catch (CalendarException e) {
            throw new SQLModelException(e);
        } catch (DateException e) {
            e.printStackTrace();
        }
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

     public SQLModel updateStatusModel(String status) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		 AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) dtoParameter;
		String sqlStr ="UPDATE AMS_ITEM_TRANS_H\n" +
				"   SET TRANS_STATUS = ?, LAST_UPDATE_DATE = GETDATE(), LAST_UPDATE_BY = ?\n" +
				" WHERE TRANS_ID = ?";
		sqlArgs.add(status);
		sqlArgs.add(sfUser.getUserId());
		sqlArgs.add(amsItemTransH.getTransId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
