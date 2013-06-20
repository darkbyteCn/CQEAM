package com.sino.nm.spare2.accept.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.spare.dto.AmsItemAllocateDDTO;
import com.sino.nm.spare2.dto.AmsItemTransHDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * <p>Title: SinoAMS</p>
 * <p>Description: </p>
 * <p>Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2007</p>
 * <p>Company: 北京思诺搏信息技术有限公司</p>
 * @author 何睿
 * @version 0.1
 *          Date: 2008-2-18
 */
public class SpareAcceptModel extends BaseSQLProducer {
    private SfUserDTO sfUser = null;
    private AmsItemTransHDTO headerDTO = null;

    public SpareAcceptModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
        this.sfUser = (SfUserDTO) userAccount;
        this.headerDTO = (AmsItemTransHDTO) dtoParameter;
    }

    public SQLModel getInsertAccModel(AmsItemAllocateDDTO amsItemTransL) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
//        AmsItemAllocateDDTO amsItemTransL = (AmsItemAllocateDDTO)dtoParameter;
        String sqlStr = "INSERT INTO AMS_ITEM_FREIGHT_D\n" +
                "  (FREIGHT_ID,\n" +
                "   TRANS_ID,\n" +
                "   DETAIL_ID,\n" +
                "   ITEM_CODE,\n" +
                "   FREIGHT_DATE,\n" +
                "   FREIGHT_STATUS,\n" +
                "   BARCODE,\n" +
                "   ACCEPT_FLAG,\n" +
                "   ACCEPT_DATE,\n" +
                "   ACCEPT_USER,\n" +
                "   ACCEPT_QTY)\n" +
                "VALUES\n" +
                "  (AMS_ITEM_FREIGHT_D_SQ.NEXTVAL,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   SYSDATE,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   'Y',\n" +
                "   SYSDATE,\n" +
                "   ?,\n" +
                "   ?)";

        sqlArgs.add(amsItemTransL.getTransId());
        sqlArgs.add(amsItemTransL.getDetailId());
        sqlArgs.add(amsItemTransL.getItemCode());
        sqlArgs.add("HAS_RECEIVED");
        sqlArgs.add(amsItemTransL.getBarcode());
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(amsItemTransL.getQuantity());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getInsertNoAccModel(AmsItemAllocateDDTO amsItemTransL) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
//        AmsItemAllocateDDTO amsItemTransL = (AmsItemAllocateDDTO)dtoParameter;
        String sqlStr = "INSERT INTO AMS_ITEM_FREIGHT_D\n" +
                "  (FREIGHT_ID,\n" +
                "   TRANS_ID,\n" +
                "   DETAIL_ID,\n" +
                "   ITEM_CODE,\n" +
                "   FREIGHT_DATE,\n" +
                "   FREIGHT_STATUS,\n" +
                "   BARCODE,\n" +
                "   ACCEPT_FLAG,\n" +
                "   ACCEPT_DATE,\n" +
                "   ACCEPT_USER,\n" +
                "   ACCEPT_QTY)\n" +
                "VALUES\n" +
                "  (AMS_ITEM_FREIGHT_D_SQ.NEXTVAL,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   SYSDATE,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   'Y',\n" +
                "   SYSDATE,\n" +
                "   ?,\n" +
                "   ?)";

        sqlArgs.add(amsItemTransL.getTransId());
        sqlArgs.add(amsItemTransL.getDetailId());
        sqlArgs.add(amsItemTransL.getItemCode());
        sqlArgs.add("NOT_RECEIVED");
        sqlArgs.add(amsItemTransL.getBarcode());
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add("0");

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel searchModel(AmsItemAllocateDDTO amsItemTransL) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT 1\n" +
                "FROM   AMS_ITEM_FREIGHT_D AIFD\n" +
                "WHERE  AIFD.TRANS_ID = ?\n" +
                "       AND AIFD.DETAIL_ID = ?";
        sqlArgs.add(amsItemTransL.getTransId());
        sqlArgs.add(amsItemTransL.getDetailId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel updateAcceptModel(String freightId, String qty) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE AMS_ITEM_FREIGHT_D\n" +
                "       SET    FREIGHT_STATUS = 'HAS_RECEIVED',\n" +
                "              ACCEPT_QTY     = ?," +
                "              ACCEPT_USER=?," +
                "              ACCEPT_FLAG='Y'\n" +
                "       WHERE  FREIGHT_ID = ?\n";
        sqlArgs.add(qty);
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(freightId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel updateNoAcceptModel(String freightId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE AMS_ITEM_FREIGHT_D\n" +
                "       SET    ACCEPT_DATE =GETDATE() ," +
                "              FREIGHT_STATUS='NOT_RECEIVED',\n" +
                "              ACCEPT_QTY     = '0'," +
                "              ACCEPT_USER=?," +
                "              ACCEPT_FLAG='Y'\n" +
                "       WHERE  FREIGHT_ID = ?\n";
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(freightId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT AITH.TRANS_NO,\n" +
                "       AIAH.TRANS_NO ALLOCATE_NO,\n" +
                "       AIFD.FREIGHT_ID,\n" +
                "       AIFD.ITEM_CODE,\n" +
                "       AIFD.DETAIL_ID,\n" +
                "       AIFD.TRANS_ID,\n" +
                "       dbo.APP_GET_ORGNIZATION_NAME(AIAH.FROM_ORGANIZATION_ID) ORGNIZATION_NAME,\n" +
                "       ESI.ITEM_NAME,\n" +
                "       ESI.ITEM_SPEC,\n" +
                "       AIFD.FREIGHT_QUANTITY,\n" +
                "       AIFD.FREIGHT_DATE\n" +
                "FROM   AMS_ITEM_FREIGHT_D  AIFD,\n" +
                "       AMS_ITEM_ALLOCATE_H AIAH,\n" +
                "       AMS_ITEM_TRANS_H    AITH,\n" +
                "       ETS_SYSTEM_ITEM     ESI\n" +
                "WHERE  AIFD.TRANS_ID = AIAH.TRANS_ID\n" +
                "       AND AIAH.SOURCE_ID = AITH.TRANS_ID\n" +
                "       AND ESI.ITEM_CODE = AIFD.ITEM_CODE\n" +
                "       AND AIFD.FREIGHT_STATUS = 'TO_ACCEPT'\n" +
                "       AND (-1=? OR AIAH.FROM_ORGANIZATION_ID = ?)\n" +
                "       AND (''=? OR ? IS NULL OR AITH.TRANS_NO LIKE ?)\n" +
                "       AND (''=? OR AIFD.FREIGHT_DATE >= ?)\n" +
                "       AND (''=? OR AIFD.FREIGHT_DATE <= CONVERT(DATE,?))\n" +
                "ORDER  BY AIFD.FREIGHT_DATE";
        sqlArgs.add(headerDTO.getFromOrganizationId());
        sqlArgs.add(headerDTO.getFromOrganizationId());
        sqlArgs.add(headerDTO.getTransNo());
        sqlArgs.add(headerDTO.getTransNo());
        sqlArgs.add(headerDTO.getTransNo());
        try {
            sqlArgs.add(headerDTO.getFromDate());
            sqlArgs.add(headerDTO.getFromDate());
            sqlArgs.add(headerDTO.getToDate());
            sqlArgs.add(headerDTO.getToDate());
        } catch (CalendarException e) {
            throw new SQLModelException(e);
        }


        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 单据列表
     * @return SQLModel
     */
    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
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
                "       AITH.REMARK,\n" +
                "       EOCM.COMPANY FROM_ORGANIZATION_NAME,\n" +
                "       EOCM2.COMPANY TO_ORGANIZATION_NAME,\n" +
                "       SUV.USERNAME CREATED_USER,\n" +
                "       EO.WORKORDER_OBJECT_NAME FROM_OBJECT_NAME,\n" +
                "       EO.WORKORDER_OBJECT_LOCATION FROM_OBJECT_LOCATION,\n" +
                "       EO2.WORKORDER_OBJECT_NAME TO_OBJECT_NAME,\n" +
                "       EO2.WORKORDER_OBJECT_LOCATION TO_OBJECT_LOCATION,\n" +
                "       EFV.VALUE TRANS_STATUS_NAME\n" +
                "  FROM    ";
        if (headerDTO.getTransType().equals(DictConstant.BJDB) || headerDTO.getTransType().equals(DictConstant.BJFP)) {
            sqlStr += "AMS_ITEM_ALLOCATE_H";
        } else {
            sqlStr += "AMS_ITEM_TRANS_H";
        }
        sqlStr += " AITH,\n" +
                "       ETS_OBJECT         EO,\n" +
                "       ETS_OBJECT         EO2,\n" +
                "       SF_USER_V          SUV,\n" +
                "       ETS_OU_CITY_MAP    EOCM,\n" +
                "       ETS_OU_CITY_MAP    EOCM2,\n" +
                "       ETS_FLEX_VALUES    EFV,\n" +
                "       ETS_FLEX_VALUE_SET EFVS\n" +
                " WHERE AITH.FROM_OBJECT_NO = EO.WORKORDER_OBJECT_NO(+)\n" +
                "   AND AITH.TO_OBJECT_NO = EO2.WORKORDER_OBJECT_NO(+)\n" +
                "   AND AITH.CREATED_BY = SUV.USER_ID\n" +
                "   AND AITH.TRANS_STATUS = EFV.CODE\n" +
                "   AND AITH.FROM_ORGANIZATION_ID = EOCM.ORGANIZATION_ID(+)\n" +
                "   AND AITH.TO_ORGANIZATION_ID = EOCM2.ORGANIZATION_ID(+)\n" +
                "   AND EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID\n" +
                "   AND EFVS.CODE = 'SPARE_ORDER_STATUS'" +
                "   AND TRANS_ID = ?";
        sqlArgs.add(headerDTO.getTransId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 单据行信息
     * @return SQLModel
     */
    public SQLModel getDataByTransIdModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "";
        if (headerDTO.getTransType().equals(DictConstant.BJDB) || headerDTO.getTransType().equals(DictConstant.BJFP)) {
            sqlStr = "SELECT ESI.ITEM_CODE,\n" +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       AIAD.BARCODE,\n" +
                    "       AIAD.QUANTITY,\n" +
                    "       AIAD.ACCEPT_QTY,\n" +
                    "       AIAD.DETAIL_ID \n" +
                    "  FROM ETS_SYSTEM_ITEM ESI, AMS_ITEM_ALLOCATE_D AIAD\n" +
                    " WHERE ESI.ITEM_CODE = AIAD.ITEM_CODE\n" +
                    "   AND AIAD.TRANS_ID = ?";
        } else {
            sqlStr = "SELECT ESI.ITEM_CODE,\n" +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       AIAD.BARCODE,\n" +
                    "       AIAD.QUANTITY,\n" +
                    "       AIAD.DETAIL_ID \n" +
                    "  FROM ETS_SYSTEM_ITEM ESI, AMS_ITEM_ALLOCATE_D AIAD\n" +
                    " WHERE ESI.ITEM_CODE = AIAD.ITEM_CODE\n" +
                    "   AND AIAD.TRANS_ID = ?";
        }

        sqlArgs.add(headerDTO.getTransId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


    public SQLModel getUpdateAcceptQtyModel(String detailId, int acceptQty) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "";
        if (headerDTO.getTransType().equals(DictConstant.BJDB) || headerDTO.getTransType().equals(DictConstant.BJFP)) {
            sqlStr = "UPDATE AMS_ITEM_ALLOCATE_D AIAD\n" +
                    "    SET ACCEPTED = 'Y', ACCEPT_QTY = ? " +
                    " WHERE AIAD.DETAIL_ID = ?";
        } else {
            sqlStr = "";
        }

        sqlArgs.add(acceptQty);
        sqlArgs.add(detailId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getDataUpdateModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "";
        if (headerDTO.getTransType().equals(DictConstant.BJDB) || headerDTO.getTransType().equals(DictConstant.BJFP)) {
            sqlStr = "UPDATE AMS_ITEM_FREIGHT_D AIAH\n" +
                    "    SET FREIGHT_STATUS = ?, " +
                    "        ACCEPT_FLAG = ?," +
                    "        ACCEPT_DATE = GETDATE()," +
                    "        ACCEPT_USER = ?," +
                    "       ACCEPT_QTY=?" +
                    " WHERE AIAH.TRANS_ID = ?";
        } else {
            sqlStr = "";
        }

        sqlArgs.add(headerDTO.getToObjectNo());
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(headerDTO.getTransId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
