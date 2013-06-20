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
import com.sino.base.log.Logger;

import com.sino.base.util.StrUtil;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.ams.spare.dto.AmsItemAllocateHDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-12-02
 * Time: 00:00:00
 * To change this template use File | Settings | File Templates.
 */
public class SpareDBCancelModel extends BaseSQLProducer {

    private SfUserDTO sfUser = null;

    public SpareDBCancelModel(SfUserDTO userAccount, AmsItemAllocateHDTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
    }
     public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsItemAllocateHDTO amsItemTransH = (AmsItemAllocateHDTO) dtoParameter;
        String sqlStr = "SELECT AIAH.TRANS_NO,\n" +
                "       AIAH.TRANS_ID,\n" +
                "       AIAH.TRANS_TYPE,\n" +
                "       AIAH.CREATION_DATE,\n" +
                "       AIAH.TRANS_STATUS,\n" +
                "       dbo.APP_GET_USER_NAME(AIAH.CREATED_BY) CREATED_USER,\n" +
                "       dbo.APP_GET_ORGNIZATION_NAME(AIAH.TO_ORGANIZATION_ID) TO_ORGANIZATION_NAME,\n" +
                "       dbo.APP_GET_STATUS_NAME(AIAH.TRANS_STATUS) ORDER_STATUS_NAME\n" +
                "  FROM AMS_ITEM_ALLOCATE_H AIAH\n" +
                " WHERE AIAH.FROM_ORGANIZATION_ID = ?\n" +
                "   AND (AIAH.TRANS_STATUS = 'ALLOTING' OR AIAH.TRANS_STATUS = 'RECEIVING')\n" +
                "   AND (? = '' OR AIAH.TRANS_STATUS = ?)\n" +
                "   AND (? = '' OR AIAH.TRANS_NO LIKE ?)\n" +
                "   AND (? = '' OR AIAH.CREATION_DATE >= ?)\n" +
                "   AND (? = '' OR AIAH.CREATION_DATE <= ?)\n" +
                " ORDER BY AIAH.CREATION_DATE DESC";
        sqlArgs.add(sfUser.getOrganizationId());
        sqlArgs.add(amsItemTransH.getTransStatus());
        sqlArgs.add(amsItemTransH.getTransStatus());
        sqlArgs.add(amsItemTransH.getTransNo());
        sqlArgs.add(amsItemTransH.getTransNo());
        sqlArgs.add(amsItemTransH.getFromDate());
        sqlArgs.add(amsItemTransH.getFromDate());
        if (StrUtil.isEmpty(amsItemTransH.getToDate())) {
            sqlArgs.add(amsItemTransH.getToDate());
            sqlArgs.add(amsItemTransH.getToDate());
        } else {
            sqlArgs.add(amsItemTransH.getToDate()+" 23:59:59");
            sqlArgs.add(amsItemTransH.getToDate()+" 23:59:59");
        }
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsItemAllocateHDTO amsItemTransH = (AmsItemAllocateHDTO) dtoParameter;
        String sqlStr = "SELECT AIAH.TRANS_ID,\n" +
                "       AIAH.TRANS_NO,\n" +
                "       AIAH.TRANS_STATUS,\n" +
                "       AIAH.CREATION_DATE,\n" +
                "       AIAH.REMARK,\n" +
                "       dbo.APP_GET_USER_NAME(AIAH.CREATED_BY) CREATED_USER,\n" +
                "       dbo.APP_GET_ORGNIZATION_NAME(AIAH.TO_ORGANIZATION_ID) TO_ORGANIZATION_NAME,\n" +
                "       dbo.APP_GET_STATUS_NAME(AIAH.TRANS_STATUS) TRANS_STATUS_NAME,\n" +
                "       AIAH.*\n" +
                "  FROM AMS_ITEM_ALLOCATE_H AIAH\n" +
                " WHERE AIAH.TRANS_ID = ?";
        sqlArgs.add(amsItemTransH.getTransId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    public SQLModel getDataByTransIdMode2(String transId){
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT AIAD.DETAIL_ID,\n" +
                "       AIAD.BARCODE,\n" +
                "       ASSC.ITEM_NAME,\n" +
                "       ASSC.ITEM_SPEC,\n" +
                "       ASSC.ITEM_CATEGORY,\n" +
                "       ASSC.SPARE_USAGE,\n" +
                "       ASV.VENDOR_NAME,\n" +
                "       AIAD.QUANTITY\n" +
                "  FROM AMS_ITEM_ALLOCATE_D AIAD,\n" +
                "       AMS_SPARE_CATEGORY  ASSC,\n" +
                "       AMS_SPARE_VENDORS   ASV\n" +
                " WHERE ASSC.BARCODE = AIAD.BARCODE\n" +
                "       AND ASSC.VENDOR_ID = ASV.VENDOR_ID\n" +
                "       AND AIAD.TRANS_ID = ?";
        sqlArgs.add(transId);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    public SQLModel updateData(String  transId) {
        SQLModel model = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE AMS_ITEM_ALLOCATE_H SET TRANS_STATUS = 'RECEIVING' WHERE TRANS_ID = ?";
        sqlArgs.add(transId);
        model.setSqlStr(sqlStr);
        model.setArgs(sqlArgs);
        return model;
    }

    /**
     * 功能：找出行信息的Model.
     */
      public SQLModel getLinesData(String  transId) {
        SQLModel model = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = " SELECT " +
                "  AIAD.TRANS_ID,\n" +
                "  AIAD.DETAIL_ID,\n" +
                "  AIAD.ITEM_CODE,\n" +
                "  AIAD.BATCH_NO,\n" +
                "  AIAD.SOURCE_ID,\n" +
                "  AIAD.BARCODE,\n" +
                "  AIAD.QUANTITY\n" +
                "  FROM\n" +
                "  AMS_ITEM_ALLOCATE_D AIAD\n" +
                "  WHERE AIAD.TRANS_ID= ?";
        sqlArgs.add(transId);
        model.setSqlStr(sqlStr);
        model.setArgs(sqlArgs);
        return model;
    }

      public SQLModel getUpdateHStatusModel(String transId){        //更新单据的状态
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr ="UPDATE AMS_ITEM_ALLOCATE_H SET TRANS_STATUS = 'CANCELED' WHERE TRANS_ID = ?";
        sqlArgs.add(transId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
