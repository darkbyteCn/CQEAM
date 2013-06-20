package com.sino.ams.spare.accept.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.spare.dto.AmsItemTransHDTO;
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


    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT AIAH.TRANS_ID,\n" +
                "       AIAH.TRANS_NO,\n" +
                "       AIAH.TRANS_TYPE,\n" +
                "       AIAH.TRANS_STATUS,\n" +
                "       AMS_PUB_PKG.GET_STATUS_NAME(AIAH.TRANS_STATUS) STATUS_NAME,\n" +
                "       TO_CHAR(AIAH.CREATION_DATE, 'YYYY-MM-DD') CREATION_DATE,\n" +
                "       AIAH.CREATED_BY,\n" +
                "       SU.USERNAME CREATED_USER,\n" +
                "       EFV.VALUE TRANS_TYPE_NAME\n" +
                "  FROM AMS_ITEM_ALLOCATE_H AIAH, ETS_FLEX_VALUES EFV, SF_USER SU\n" +
                " WHERE AIAH.CREATED_BY = SU.USER_ID\n" +
                "   AND AIAH.TRANS_TYPE = EFV.CODE\n" +
                "   AND EFV.FLEX_VALUE_SET_ID = 12" +
                "   AND AIAH.TRANS_STATUS = 'COMPLETED'\n" +
                "   AND TRANS_NO LIKE NVL(?, TRANS_NO)\n" +
                "   AND TRANS_STATUS = NVL(?, TRANS_STATUS)\n" +
                "   AND (? IS NULL OR TO_ORGANIZATION_ID = ?)\n" +
                "   AND AIAH.TRANS_TYPE = NVL(?, AIAH.TRANS_TYPE)\n" +
                "   AND AIAH.CREATION_DATE >= NVL(?, AIAH.CREATION_DATE)\n" +
                "   AND AIAH.CREATION_DATE <= NVL(?, AIAH.CREATION_DATE)\n" +
                "UNION ALL\n" +
                "SELECT AITH.TRANS_ID,\n" +
                "       AITH.TRANS_NO,\n" +
                "       AITH.TRANS_TYPE,\n" +
                "       AITH.TRANS_STATUS,\n" +
                "       AMS_PUB_PKG.GET_STATUS_NAME(AITH.TRANS_STATUS) STATUS_NAME,\n" +
                "       TO_CHAR(AITH.CREATION_DATE, 'YYYY-MM-DD') CREATION_DATE,\n" +
                "       AITH.CREATED_BY,\n" +
                "       SU.USERNAME CREATED_USER,\n" +
                "       EFV.VALUE TRANS_TYPE_NAME\n" +
                "  FROM AMS_ITEM_TRANS_H AITH, ETS_FLEX_VALUES EFV, SF_USER SU\n" +
                " WHERE AITH.CREATED_BY = SU.USER_ID\n" +
                "   AND AITH.TRANS_TYPE = EFV.CODE\n" +
                "   AND EFV.FLEX_VALUE_SET_ID = 12\n" +
                "   AND AITH.TRANS_TYPE = 'BJCK'\n" +
                "   AND TRANS_NO LIKE NVL(?, TRANS_NO)\n" +
                "   AND TRANS_STATUS = NVL(?, TRANS_STATUS)\n" +
                "   AND (? IS NULL OR TO_ORGANIZATION_ID = ?)\n" +
                "   AND AITH.TRANS_TYPE = NVL(?, AITH.TRANS_TYPE)\n" +
                "   AND AITH.CREATION_DATE >= NVL(?, AITH.CREATION_DATE)\n" +
                "   AND AITH.CREATION_DATE <= NVL(?, AITH.CREATION_DATE)\n" +
                " ORDER BY CREATION_DATE DESC";
        sqlArgs.add(headerDTO.getTransNo());
        sqlArgs.add(headerDTO.getTransStatus());
        sqlArgs.add(sfUser.getOrganizationId());
        sqlArgs.add(sfUser.getOrganizationId());
        sqlArgs.add(headerDTO.getTransType());
        try {
            sqlArgs.add(headerDTO.getFromDate());
            sqlArgs.add(headerDTO.getToDate());
        } catch (CalendarException e) {
            throw new SQLModelException(e);
        }

        sqlArgs.add(headerDTO.getTransNo());
        sqlArgs.add(headerDTO.getTransStatus());
        sqlArgs.add(sfUser.getOrganizationId());
        sqlArgs.add(sfUser.getOrganizationId());
        sqlArgs.add(headerDTO.getTransType());
        try {
            sqlArgs.add(headerDTO.getFromDate());
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
                "   AND EFVS.CODE = 'ORDER_STATUS'" +
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
                    "    SET ACCEPT_QTY = ? " +
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
            sqlStr = "UPDATE AMS_ITEM_ALLOCATE_H AIAH\n" +
                    "    SET TO_OBJECT_NO = ?, " +
                    "        RCV_USER = ?," +
                    "        TRANS_STATUS = '" + DictConstant.ACCEPTED + "'," +
                    "        TRANS_DATE = SYSDATE " +
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
