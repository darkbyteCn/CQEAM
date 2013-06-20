package com.sino.nm.spare2.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.ams.constant.DictConstant;
import com.sino.nm.spare2.dto.AmsItemTransHDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * <p>Title: SinoEAMS</p>
 * <p>Description: </p>
 * <p>Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2008</p>
 * <p>Company: 北京思诺搏信息技术有限公司</p>
 * @author 何睿
 * @version 0.1
 *          Date: 2008-3-13
 */
public class BjghModel extends BaseSQLProducer {
    private SfUserDTO sfUser = null;
    private AmsItemTransHDTO headerDTO = null;

    public BjghModel(BaseUserDTO userAccount, DTO dtoParameter) {
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
                "       dbo.APP_GET_STATUS_NAME(AIAH.TRANS_STATUS) STATUS_NAME,\n" +
                "       AIAH.CREATION_DATE, \n" +
                "       AIAH.CREATED_BY,\n" +
                "       SU.USERNAME CREATED_USER,\n" +
                "       EFV.VALUE TRANS_TYPE_NAME,\n" +
                "       dbo.APP_GET_ORGNIZATION_NAME(AIAH.FROM_ORGANIZATION_ID) FROM_ORGANIZATION_NAME,\n" +
                "       dbo.APP_GET_ORGNIZATION_NAME(AIAH.TO_ORGANIZATION_ID) TO_ORGANIZATION_NAME\n" +
                "  FROM AMS_ITEM_TRANS_H AIAH, ETS_FLEX_VALUES EFV, SF_USER SU\n" +
                " WHERE AIAH.CREATED_BY = SU.USER_ID\n" +
                "   AND AIAH.TRANS_TYPE = EFV.CODE\n" +
                "   AND EFV.FLEX_VALUE_SET_ID = '12'" +
                "   AND AIAH.TRANS_TYPE = '" + DictConstant.BJGH + "'\n";

        if (headerDTO.getFlag().equals("ACCEPT")) {     //接收确认时,只有借出公司的可以接收
            sqlStr += " AND TO_ORGANIZATION_ID = ?" +
                    "   AND (-1=? OR FROM_ORGANIZATION_ID = ?)\n";    //查询条件
            sqlArgs.add(sfUser.getOrganizationId());
            sqlArgs.add(headerDTO.getFromOrganizationId());
            sqlArgs.add(headerDTO.getFromOrganizationId());
        } else if(headerDTO.getFlag().equals("PRINT")) {  //归还单据打印
            sqlStr += " AND AIAH.TRANS_STATUS NOT IN ('" + DictConstant.SAVE_TEMP + "','" + DictConstant.REJECTED + "')" +
                    "   AND (-1=? OR FROM_ORGANIZATION_ID = ?)" +
                    "   AND (-1=? OR TO_ORGANIZATION_ID = ?)\n";
            sqlArgs.add(sfUser.getOrganizationId());
            sqlArgs.add(sfUser.getOrganizationId());
            sqlArgs.add(headerDTO.getToOrganizationId());
            sqlArgs.add(headerDTO.getToOrganizationId());
        }else {                         //创建归还单
            sqlStr += " AND AIAH.TRANS_STATUS IN ('" + DictConstant.SAVE_TEMP + "','" + DictConstant.REJECTED + "')" +
                    "   AND (-1=?  OR FROM_ORGANIZATION_ID = ?)" +
                    "   AND (-1=? OR TO_ORGANIZATION_ID = ?)\n";
            sqlArgs.add(sfUser.getOrganizationId());
            sqlArgs.add(sfUser.getOrganizationId());
            sqlArgs.add(headerDTO.getToOrganizationId());
            sqlArgs.add(headerDTO.getToOrganizationId());
        }
        sqlStr += "   AND (''=? OR ? IS NULL OR TRANS_NO LIKE ?)\n" +
                "   AND (''=? OR ? IS NULL OR TRANS_STATUS = ?)\n" +
                "   AND (''=? OR AIAH.CREATION_DATE >= ?)\n" +
                "   AND (''=? OR AIAH.CREATION_DATE <= CONVERT(DATE,?))\n" +
                " ORDER BY CREATION_DATE DESC";
        sqlArgs.add(headerDTO.getTransNo());
        sqlArgs.add(headerDTO.getTransNo());
        sqlArgs.add(headerDTO.getTransNo());
        sqlArgs.add(headerDTO.getTransStatus());
        sqlArgs.add(headerDTO.getTransStatus());
        sqlArgs.add(headerDTO.getTransStatus());
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
                "       AITH.TO_OBJECT_NO,\n" +
                "       AITH.FROM_ORGANIZATION_ID,\n" +
                "       AITH.TO_ORGANIZATION_ID,\n" +
                "       AITH.REMARK,\n" +
                "       EOCM.COMPANY FROM_ORGANIZATION_NAME,\n" +
                "       EOCM2.COMPANY TO_ORGANIZATION_NAME,\n" +
                "       SUV.USERNAME CREATED_USER,\n" +
                "       EO.WORKORDER_OBJECT_NAME FROM_OBJECT_NAME,\n" +
                "       EO.WORKORDER_OBJECT_LOCATION FROM_OBJECT_LOCATION,\n" +
                "       EFV.VALUE TRANS_STATUS_NAME\n" +
                "  FROM AMS_ITEM_ALLOCATE_H   AITH,\n" +
                "       ETS_OBJECT         EO,\n" +
                "       SF_USER_V          SUV,\n" +
                "       ETS_OU_CITY_MAP    EOCM,\n" +
                "       ETS_OU_CITY_MAP    EOCM2,\n" +
                "       ETS_FLEX_VALUES    EFV,\n" +
                "       ETS_FLEX_VALUE_SET EFVS\n" +
                " WHERE AITH.FROM_OBJECT_NO = EO.WORKORDER_OBJECT_NO(+)\n" +
                "   AND AITH.CREATED_BY = SUV.USER_ID\n" +
                "   AND AITH.TRANS_STATUS = EFV.CODE\n" +
                "   AND AITH.FROM_ORGANIZATION_ID = EOCM.ORGANIZATION_ID(+)\n" +
                "   AND AITH.TO_ORGANIZATION_ID = EOCM2.ORGANIZATION_ID(+)\n" +
                "   AND EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID\n" +
                "   AND EFVS.CODE = 'ORDER_STATUS'" +
                "   AND TRANS_ID = ?";
        sqlArgs.add(amsItemTransH.getTransId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getDataByForeignKeyModel(String foreignKey) {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT AITL.LINE_ID,\n" +
                "       AITL.BARCODE,\n" +
                "       AITL.BATCH_NO,\n" +
                "       AITL.NORMAL_QUANTITY,\n" +
                "       AITL.BAD_QUANTITY,\n" +
                "       ESI.ITEM_CODE,\n" +
                "       ESI.ITEM_NAME,\n" +
                "       ESI.ITEM_SPEC,\n" +
                "       AIAH.RESPECT_RETURN_DATE RESPECT_RETURN_DATE,\n" +
                "       AIAD.QUANTITY,\n" +
                "       AIAD.RETURN_QTY,\n" +
                "       AIAD.DETAIL_ID STORAGE_ID\n" +
                "  FROM AMS_ITEM_TRANS_H    AITH,\n" +
                "       AMS_ITEM_TRANS_L    AITL,\n" +
                "       ETS_SYSTEM_ITEM     ESI,\n" +
                "       AMS_ITEM_ALLOCATE_H AIAH,\n" +
                "       AMS_ITEM_ALLOCATE_D AIAD\n" +
                " WHERE AITH.TRANS_ID = AITL.TRANS_ID\n" +
                "   AND AITL.ITEM_CODE = ESI.ITEM_CODE\n" +
                "   AND AITL.ITEM_CODE = AIAD.ITEM_CODE\n" +
                "   AND AITL.BATCH_NO = AIAH.TRANS_NO\n" +
                "   AND AIAH.TRANS_ID = AIAD.TRANS_ID\n" +
                "   AND AITH.TRANS_ID = ?";
        List argList = new ArrayList();
        argList.add(foreignKey);
        sqlModel.setArgs(argList);
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }

    public SQLModel getDataUpdateModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "UPDATE AMS_ITEM_TRANS_H"
                + " SET"
                + " TRANS_STATUS = ?,"
                + " CONFIRMED_BY = ?,"
                + " CONFIRMED_DATE = GETDATE()"
                + " WHERE"
                + " TRANS_ID = ?";
        List argList = new ArrayList();
        argList.add(headerDTO.getTransStatus());
        argList.add(sfUser.getUserId());
        argList.add(headerDTO.getTransId());
        sqlModel.setArgs(argList);
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }
}

