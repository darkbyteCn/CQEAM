package com.sino.ams.spare.returnBj.model;

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
import com.sino.ams.spare.allot.dto.AmsBjsAllotHDTO;
import com.sino.ams.spare.dto.AmsItemTransLDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2007-11-12
 * Time: 9:34:04
 */
public class BjReturnRepairModel extends BaseSQLProducer {
    private SfUserDTO sfUser = null;

    public BjReturnRepairModel(SfUserDTO userAccount, AmsBjsAllotHDTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
    }

    public SQLModel insertLData(String transId, AmsItemTransLDTO lineDto) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO "
                 + " AMS_ITEM_TRANS_L("
                 + " TRANS_ID,"
                 + " LINE_ID,"
                 + " BARCODE,"
                 + " ITEM_CODE,"
                 + " QUANTITY,"
                 + " BATCH_NO,"
                 + " STORAGE_ID,"
                 + " REMARK,"
                 + " BFJE,"
                 + " REASONS "
                 + ") VALUES ("
                 + " ?, AMS_ITEM_TRANS_L_S.NEXTVAL, ?,?,?,?,?,?,?,?)";

             sqlArgs.add(transId);
             sqlArgs.add(lineDto.getBarcode());
             sqlArgs.add(lineDto.getItemCode());
             sqlArgs.add(lineDto.getQuantity());
             sqlArgs.add(lineDto.getBatchNo());
             sqlArgs.add(lineDto.getStorageId());
             sqlArgs.add(lineDto.getRemarkl());
             sqlArgs.add(lineDto.getBfje());
             sqlArgs.add(lineDto.getReasons());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel insertRData(String transId, String count, String itemCode) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "   INSERT INTO AMS_ITEM_RESERVED\n" +
                "      (TRANS_ID, RESERVED_DATE, ITEM_CODE, RESERVED_COUNT, ORGANIZATION_ID)\n" +
                "    VALUES\n" +
                "      (?,sysdate,?,?,?)";
        sqlArgs.add(transId);
        sqlArgs.add(itemCode);
        sqlArgs.add(count);
        sqlArgs.add(sfUser.getOrganizationId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getDataCreateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
       try {
        List sqlArgs = null;
        String sqlStr = null;
        sqlArgs = new ArrayList();
        AmsBjsAllotHDTO headerDto = (AmsBjsAllotHDTO) dtoParameter;
         sqlStr = "INSERT INTO "
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
                    + " ATTRIBUTE4,"
                    + " VENDOR_ID"
//                    + " LAST_UPDATE_DATE,"
//                    + " LAST_UPDATE_BY"
                    + ") VALUES ("
                    + " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE, ?,?,?,?,?)";

            sqlArgs.add(headerDto.getTransId());
            sqlArgs.add(headerDto.getTransNo());
            sqlArgs.add(headerDto.getTransType());
            sqlArgs.add(headerDto.getTransStatus());
            sqlArgs.add(headerDto.getFromUser());
            sqlArgs.add(headerDto.getToUser());
            sqlArgs.add(headerDto.getFromDept());
            sqlArgs.add(headerDto.getToDept());
            sqlArgs.add(headerDto.getFromObjectNo());
            sqlArgs.add(headerDto.getToObjectNo());
//            sqlArgs.add(headerDto.getFromOrganizationId());
            sqlArgs.add(sfUser.getOrganizationId());
            sqlArgs.add(headerDto.getToOrganizationId());
            sqlArgs.add(headerDto.getTransDate());
            sqlArgs.add(headerDto.getRcvUser());
            sqlArgs.add(headerDto.getCreatedBy());
            sqlArgs.add(headerDto.getReason());
            sqlArgs.add(headerDto.getRemark());
            sqlArgs.add(headerDto.getAttribute4());     //插入单据的总金额
            sqlArgs.add(headerDto.getVendorId());
//            sqlArgs.add(amsItemTransH.getLastUpdateDate());
//            sqlArgs.add(amsItemTransH.getLastUpdateBy());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
       } catch (CalendarException e) {
            throw new SQLModelException(e);
        }
        return sqlModel;
    }

    public SQLModel getDeleteByTransIdModel(String transId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM"
                + " AMS_ITEM_TRANS_L"
                + " WHERE"
                + " TRANS_ID = ?";
        sqlArgs.add(transId);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel updateInfoModel(String addresId, String barcode) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();

        String sqlStr = "UPDATE ETS_ITEM_INFO SET ADDRESS_ID=? WHERE BARCODE=?";

        sqlArgs.add(addresId);
        sqlArgs.add(barcode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getDataUpdateModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsBjsAllotHDTO headerDto = (AmsBjsAllotHDTO) dtoParameter;
        String sqlStr = "UPDATE AMS_ITEM_TRANS_H SET TRANS_NO=?,TRANS_STATUS=?,TO_OBJECT_NO=?," +
                " LAST_UPDATE_BY = ?, LAST_UPDATE_DATE = SYSDATE WHERE TRANS_ID=?";

        sqlArgs.add(headerDto.getTransNo());
        sqlArgs.add(headerDto.getTransStatus());
        sqlArgs.add(headerDto.getToObjectNo());
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(headerDto.getTransId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getDataByForeignKeyModel(String transId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT AITL.LINE_ID,\n" +
                "       AITL.BARCODE,\n" +
                "       AC.ITEM_NAME,\n" +
                "       AC.ITEM_SPEC,\n" +
                "       AC.ITEM_CATEGORY,\n" +
                "       AC.SPARE_USAGE,\n" +
                "       ASV.VENDOR_NAME,\n" +
                "       AC.ITEM_UNIT,\n" +
                "       AITL.REMARK,\n" +
                "       AITL.QUANTITY,\n" +
                "       AITL.STORAGE_ID,\n" +
                "       AITL.BATCH_NO,\n" +
                "       AITL.BFJE,\n" +
                "       AITL.REASONS,\n" +
//                "       AMS_ITEM_TRANS_SX.GET_SPARE_USERNUM(AC.BARCODE,\n" +
//                "                                           73,\n" +
//                "                                           AITH.FROM_ORGANIZATION_ID) DXK_QTY,\n" +
                "       AMS_ITEM_TRANS_SX.GET_SPARE_ONHAND2(AC.BARCODE,AITH.FROM_ORGANIZATION_ID) ONHAND_QTY\n" +
                "  FROM AMS_ITEM_TRANS_L   AITL,\n" +
                "       AMS_ITEM_TRANS_H   AITH,\n" +
                "       AMS_SPARE_CATEGORY AC,\n" +
                "       AMS_SPARE_VENDORS  ASV\n" +
                " WHERE AITL.BARCODE = AC.BARCODE\n" +
                "       AND AITH.TRANS_ID = AITL.TRANS_ID\n" +
                "       AND AC.VENDOR_ID = ASV.VENDOR_ID\n" +
                "       AND AITL.TRANS_ID = ?";

        sqlArgs.add(transId);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsBjsAllotHDTO amsItemTransH = (AmsBjsAllotHDTO) dtoParameter;
        String sqlStr = "SELECT AMS_PUB_PKG.GET_USER_NAME(AITH.CREATED_BY) CREATED_USER,\n" +
                "       AITH.TRANS_ID,\n" +
                "       AITH.TRANS_NO,\n" +
                "       AMS_PUB_PKG.GET_STATUS_NAME(AITH.TRANS_STATUS) TRANS_STATUS_NAME,\n" +
                "       AMS_PUB_PKG.GET_OBJECT_NAME(AITH.FROM_OBJECT_NO) FROM_OBJECT_NAME,\n" +
                "       AMS_PUB_PKG.GET_OBJECT_NAME(AITH.TO_OBJECT_NO) TO_OBJECT_NAME,\n" +
                "       AITH.FROM_OBJECT_NO,\n" +
                "       AITH.TO_OBJECT_NO,\n" +
                "       AITH.CREATION_DATE,\n" +
                "       AITH.CREATED_BY,\n" +
                "       AITH.TRANS_STATUS,\n" +
                "       AITH.REASON,\n" +
                "       AITH.REMARK,\n" +
                "       AITH.VENDOR_ID,\n" +
                "       ASV.VENDOR_NAME\n" +
                "  FROM AMS_ITEM_TRANS_H  AITH,\n" +
                "       AMS_SPARE_VENDORS ASV\n" +
                " WHERE AITH.VENDOR_ID = ASV.VENDOR_ID(+)\n" +
                "       AND AITH.TRANS_ID = ?";
        sqlArgs.add(amsItemTransH.getTransId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsBjsAllotHDTO amsItemTransH = (AmsBjsAllotHDTO) dtoParameter;
        String sqlStr = "SELECT AITH.TRANS_ID,\n" +
                "       AITH.TRANS_NO,\n" +
                "       AITH.CREATED_BY,\n" +
                "       AITH.ATTRIBUTE4,\n" +
                "       TO_CHAR(AITH.CREATION_DATE,'YYYY-MM-DD') CREATION_DATE,\n" +
                "       TO_CHAR(AITH.TRANS_DATE,'YYYY-MM-DD') TRANS_DATE,\n" +
                "       SUV.USERNAME CREATED_USER,\n" +
                "       EO.WORKORDER_OBJECT_NAME,\n" +
                "       EFV.VALUE ORDER_STATUS_NAME\n" +
                "  FROM AMS_ITEM_TRANS_H   AITH,\n" +
                "       ETS_OBJECT         EO,\n" +
                "       SF_USER_V          SUV,\n" +
                "       ETS_FLEX_VALUES    EFV,\n" +
                "       ETS_FLEX_VALUE_SET EFVS\n" +
                " WHERE AITH.TO_OBJECT_NO = EO.WORKORDER_OBJECT_NO(+)\n" +
                "   AND AITH.CREATED_BY = SUV.USER_ID\n" +
                "   AND AITH.TRANS_STATUS = EFV.CODE\n" +
                "   AND EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID\n" +
                "   AND EFVS.CODE = 'ORDER_STATUS'\n"
                + "AND (? IS NULL OR TRANS_NO = ?)"
                + "AND (? IS NULL OR TRANS_STATUS = ?)";
        if (amsItemTransH.getTransType().equals("FXSQ")) {
            sqlStr += " AND AITH.FROM_ORGANIZATION_ID = ?\n" +
                    "   AND AITH.TRANS_TYPE = NVL(?, AITH.TRANS_TYPE)\n" +
                    "   AND AITH.CREATION_DATE >= NVL(?, AITH.CREATION_DATE)\n" +
                    "   AND AITH.CREATION_DATE <= NVL(?, AITH.CREATION_DATE)\n" +
                    " ORDER BY AITH.CREATION_DATE DESC";
        } else {
            sqlStr += "AND (? IS NULL OR TO_OBJECT_NO = ?)\n" +
                    "   AND (? IS NULL OR FROM_ORGANIZATION_ID = ?)\n" +
                    "   AND AITH.TRANS_TYPE = NVL(?, AITH.TRANS_TYPE)\n" +
                    "   AND AITH.CREATION_DATE >= NVL(?, AITH.CREATION_DATE)\n" +
                    "   AND AITH.CREATION_DATE <= NVL(?, AITH.CREATION_DATE)\n" +
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
        try {
            sqlArgs.add(amsItemTransH.getFromDate());

            SimpleCalendar sc = amsItemTransH.getToDate();
            SimpleDate sd = sc.getSimpleDate();
            if (!sd.getDateValue().equals("")) {
                sd.adjust(DateConstant.DATE, 1);
                sc.setDate(sd);
            }
            sqlArgs.add(sc);
        } catch (CalendarException e) {
            throw new SQLModelException(e);
        } catch (DateException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getFromObjectNameModel(String transId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT EO.WORKORDER_OBJECT_NAME\n" +
                "  FROM AMS_ITEM_TRANS_H AITH,\n" +
                "       ETS_OBJECT       EO\n" +
                " WHERE AITH.FROM_OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                "       AND AITH.TRANS_ID = ?";
        sqlArgs.add(transId);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getToObjectNameModel(String transId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT EOCM.COMPANY\n" +
                "  FROM AMS_ITEM_TRANS_H AITH,\n" +
                "       ETS_OU_CITY_MAP  EOCM\n" +
                " WHERE EOCM.COMPANY_CODE = AITH.TO_OBJECT_NO\n" +
                "       AND AITH.TRANS_ID = ?";
        sqlArgs.add(transId);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
