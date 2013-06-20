package com.sino.nm.spare2.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.SQLModelException;

import com.sino.framework.dto.BaseUserDTO;
import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.nm.spare2.constant.SpareConstant;
import com.sino.nm.spare2.dto.AmsItemAllocateDDTO;
import com.sino.nm.spare2.dto.AmsItemAllocateHDTO;

/**
 * <p>Title: SinoEAMS</p>
 * <p>Description: 备件实物借用出库</p>
 * <p>Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2007 - 2008</p>
 * <p>Company: 北京思诺搏信息技术有限公司</p>
 * @author 何睿
 * @version 0.1
 *          Date: 2008-6-20
 */
public class BjswjyckModel extends AMSSQLProducer {
     private AmsItemAllocateHDTO headerDto = null;
    public BjswjyckModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
        headerDto =  (AmsItemAllocateHDTO) dtoParameter;
    }

    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT AITH.TRANS_ID,\n" +
                "       AITH.TRANS_NO,\n" +
//                "       AITH.CREATED_BY,\n" +
                "       AITH.CREATION_DATE,\n" +
                "       AITH.TRANS_DATE,\n" +
                "       SUV.USERNAME CREATED_USER,\n" +
                "       EO.WORKORDER_OBJECT_NAME,\n" +
                "        '待出库' ORDER_STATUS_NAME\n" +
                "  FROM AMS_ITEM_ALLOCATE_H   AITH,\n" +
                "       ETS_OBJECT         EO,\n" +
                "       SF_USER          SUV\n" +
//                "       ETS_FLEX_VALUES    EFV,\n" +
//                "       ETS_FLEX_VALUE_SET EFVS\n" +
                " WHERE AITH.FROM_OBJECT_NO *= EO.WORKORDER_OBJECT_NO\n" +
                "   AND AITH.CREATED_BY = SUV.USER_ID\n" +
                "   AND AITH.TRANS_STATUS = '" + SpareConstant.TO_CONSIGN + "'\n"+     //待出库
//                "   AND EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID\n" +
//                "   AND EFVS.CODE = 'ORDER_STATUS'\n"
                 "AND (? IS NULL OR ?='' OR TRANS_NO = ?)"
//                + "AND (? IS NULL OR TRANS_STATUS = ?)"
                + "AND (? IS NULL OR ?='' OR FROM_OBJECT_NO = ?)"
                + "AND (? IS NULL OR FROM_ORGANIZATION_ID = ?)"
//                + "AND AITH.TRANS_TYPE = NVL(?,AITH.TRANS_TYPE)"
                + "AND ( ? IS NULL OR  ?='' OR AITH.CREATION_DATE>= ?) "
                + "AND ( ? IS NULL OR  ?='' OR AITH.CREATION_DATE<= ?)"
                + "  ORDER BY AITH.CREATION_DATE DESC";
        sqlArgs.add(headerDto.getTransNo());
        sqlArgs.add(headerDto.getTransNo());
        sqlArgs.add(headerDto.getTransNo());
//		sqlArgs.add(headerDto.getTransType());
//		sqlArgs.add(headerDto.getTransType());
//        sqlArgs.add(headerDto.getTransStatus());
//        sqlArgs.add(headerDto.getTransStatus());
//		sqlArgs.add(headerDto.getFromUser());
//		sqlArgs.add(headerDto.getFromUser());
//		sqlArgs.add(headerDto.getToUser());
//		sqlArgs.add(headerDto.getToUser());
//		sqlArgs.add(headerDto.getFromDept());
//		sqlArgs.add(headerDto.getFromDept());
//		sqlArgs.add(headerDto.getToDept());
//		sqlArgs.add(headerDto.getToDept());
		sqlArgs.add(headerDto.getFromObjectNo());
		sqlArgs.add(headerDto.getFromObjectNo());
		sqlArgs.add(headerDto.getFromObjectNo());
//        sqlArgs.add(headerDto.getToObjectNo());
//        sqlArgs.add(headerDto.getToObjectNo());
//		sqlArgs.add(headerDto.getFromOrganizationId());
//		sqlArgs.add(headerDto.getFromOrganizationId());
        sqlArgs.add(userAccount.getOrganizationId());
        sqlArgs.add(userAccount.getOrganizationId());
        sqlArgs.add(headerDto.getFromDate());
        sqlArgs.add(headerDto.getFromDate());
        sqlArgs.add(headerDto.getFromDate());
        sqlArgs.add(headerDto.getSQLToDate());
        sqlArgs.add(headerDto.getSQLToDate());
        sqlArgs.add(headerDto.getSQLToDate());
//        sqlArgs.add(headerDto.getTransType());


        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT AIAH.TRANS_ID,\n" +
                "       AIAH.TRANS_NO,\n" +
                "       AIAH.TRANS_TYPE,\n" +
                "       AIAH.TRANS_STATUS,\n" +
                "       AIAH.CREATED_BY,\n" +
                "       AIAH.CREATION_DATE,\n" +
                "       AIAH.TRANS_DATE,\n" +
                "       AIAH.TO_OBJECT_NO,\n" +
                "       AIAH.FROM_ORGANIZATION_ID,\n" +
                "       AIAH.TO_ORGANIZATION_ID,\n" +
                "       AIAH.REMARK,\n" +
                "       AIAH.RESPECT_RETURN_DATE,\n" +
                "       EOCM.COMPANY TO_ORGANIZATION_NAME,\n" +
                "       SUV.USERNAME CREATED_USER,\n" +
                "       EO.WORKORDER_OBJECT_NAME FROM_OBJECT_NAME,\n" +
                "       EO.WORKORDER_OBJECT_LOCATION FROM_OBJECT_LOCATION,\n" +
                "       '待出库' TRANS_STATUS_NAME,\n" +
                "       AIAH.FREIGHT_USER_ID,\n" +
                "       AIAH.FREIGHT_USER_NAME,\n" +
                "       AIAH.FREIGHT_DEPT_CODE,\n" +
                "       AIAH.FREIGHT_MIS_USER,\n" +
                "       AIAH.RECEIVE_USER_NAME,\n" +
                "       AIAH.RECEIVE_USER_TEL,\n" +
                "       AMD.DEPT_NAME FREIGHT_DEPT_NAME,\n" +
                "       SU.USERNAME FREIGHT_MIS_USER_NAME\n" +
                "  FROM AMS_ITEM_ALLOCATE_H   AIAH,\n" +
                "       ETS_OBJECT         EO,\n" +
                "       SF_USER_V          SUV,\n" +
                "       SF_USER            SU,\n" +
                "       AMS_MIS_DEPT       AMD,\n" +
                "       ETS_OU_CITY_MAP    EOCM\n" +
                " WHERE AIAH.FROM_OBJECT_NO *= EO.WORKORDER_OBJECT_NO\n" +
                "   AND AIAH.CREATED_BY = SUV.USER_ID\n" +
                "   AND AIAH.FREIGHT_MIS_USER *= SU.USER_ID\n" +
                "   AND AIAH.FREIGHT_DEPT_CODE *= AMD.DEPT_CODE\n" +
                "   AND AIAH.TO_ORGANIZATION_ID *= EOCM.ORGANIZATION_ID\n" +
//                "   AND EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID\n" +
//                "   AND EFVS.CODE = 'ORDER_STATUS'" +
                "   AND TRANS_ID = ?";
        sqlArgs.add(headerDto.getTransId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

        public SQLModel getDataByForeignKeyModel(String foreignKey){
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT ESI.MIS_ITEM_CODE BARCODE,\n" +
                "       ESI.ITEM_CODE,\n" +
                "       ESI.ITEM_NAME,\n" +
                "       ESI.ITEM_SPEC,\n" +
//                "       ASI.QUANTITY ONHAND_QUANTITY,\n" +
                "       AIAD.QUANTITY,\n" +
                "       ISNULL(AIAD.ACCEPT_QTY,0) ACCEPT_QTY,\n" +
                "       dbo.AMS_INV_TRANS2_GET_SPARE_ON_WAY(AIAD.DETAIL_ID) QUANTITY_ON_WAY,\n" +
                "       dbo.AMS_INV_TRANS2_GET_SPARE_NOT_RECEIVED(AIAD.DETAIL_ID) QUANTITY_NOT_RECEIVED,\n" +
//                "       AMS_INV_TRANS2.IS_RESENDABLE(AIAD.DETAIL_ID) IS_RESENDABLE,\n" +
                "       AIAD.DETAIL_ID\n" +
                "  FROM ETS_SYSTEM_ITEM     ESI,\n" +
                "       AMS_ITEM_ALLOCATE_D AIAD,\n" +
                "       AMS_ITEM_ALLOCATE_H AIAH\n" +
//                "       AMS_SPARE_INFO      ASI\n" +
                " WHERE ESI.ITEM_CODE = AIAD.ITEM_CODE\n" +
                "   AND AIAD.TRANS_ID = AIAH.TRANS_ID\n" +
//                "   AND AIAH.FROM_OBJECT_NO = ASI.OBJECT_NO\n" +
//                "   AND AIAD.BARCODE = ASI.BARCODE\n" +
                "   AND AIAD.TRANS_ID = ?";
        List argList = new ArrayList();
        argList.add(foreignKey);
        sqlModel.setArgs(argList);
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }

    public SQLModel getDataUpdateModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE AMS_ITEM_ALLOCATE_H"
                + " SET"
//                + " TRANS_NO = ?,"
//                + " TRANS_TYPE = ?,"
//                + " TRANS_STATUS = ?,"
//                + " FROM_USER = ?,"
//                + " TO_USER = ?,"
//                + " FROM_DEPT = ?,"
//                + " TO_DEPT = ?,"
//                + " FROM_OBJECT_NO = ?,"
//                + " TO_OBJECT_NO = ?,"
//                + " FROM_ORGANIZATION_ID = ?,"
//                + " TO_ORGANIZATION_ID = ?,"
//                + " TRANS_DATE = ?,"
//                + " RCV_USER = ?,"
//                + " LAST_UPDATE_DATE = ?,"
//                + " LAST_UPDATE_BY = ?,"
//                + " REMARK = ?, "
//                + " RESPECT_RETURN_DATE = ?, "
                + " FREIGHT_USER_ID = ?,"
                + " FREIGHT_USER_NAME = ?,"
                + " FREIGHT_DEPT_CODE = ?,"
                + " FREIGHT_MIS_USER = ?,"
                + " RECEIVE_USER_NAME = ?,"
                + " RECEIVE_USER_TEL = ?"
                + " WHERE"
                + " TRANS_ID = ?";

            sqlArgs.add(headerDto.getFreightUserId());
            sqlArgs.add(headerDto.getFreightUserName());
            sqlArgs.add(headerDto.getFreightDeptCode());
            sqlArgs.add(headerDto.getFreightMisUser());
            sqlArgs.add(headerDto.getReceiveUserName());
            sqlArgs.add(headerDto.getReceiveUserTel());
            sqlArgs.add(headerDto.getTransId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getFreightModel(AmsItemAllocateDDTO dto){
        String sqlStr = "INSERT INTO AMS_ITEM_FREIGHT_D\n" +
                "      (FREIGHT_ID,\n" +
                "       TRANS_ID,\n" +
                "       DETAIL_ID,\n" +
                "       ITEM_CODE,\n" +
                "       FREIGHT_DATE,\n" +
                "       FREIGHT_STATUS,\n" +
                "       FREIGHT_QUANTITY,\n" +
                "       BARCODE)\n" +
                "    VALUES\n" +
                "      (AMS_ITEM_FREIGHT_D_S.NEXTVAL,\n" +
                "       ?,\n" +
                "       ?,\n" +
                "       ?,\n" +
                "       SYSDATE,\n" +
                "       'TO_ACCEPT',\n" +
                "       ?,\n" +
                "       ?)";
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        sqlArgs.add(headerDto.getTransId());
        sqlArgs.add(dto.getDetailId());
        sqlArgs.add(dto.getItemCode());
        sqlArgs.add(dto.getFreightQty());
        sqlArgs.add(dto.getBarcode());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
