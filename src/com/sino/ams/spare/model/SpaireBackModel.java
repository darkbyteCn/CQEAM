package com.sino.ams.spare.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.StrUtil;

import com.sino.framework.sql.BaseSQLProducer;
import com.sino.ams.spare.dto.AmsItemTransHDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * User: srf
 * Date: 2008-3-18
 * Time: 21:20:41
 * Function:备件返修申请（NEW）
 */
public class SpaireBackModel extends BaseSQLProducer {

    private SfUserDTO sfUser = null;

    public SpaireBackModel(SfUserDTO userAccount, AmsItemTransHDTO dtoParameter) {
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
                    + " REMARK, "
                    + " ATTRIBUTE1,"
                    + " ATTRIBUTE2,"
                    + " ATTRIBUTE3"
                    + ") VALUES ("
                    + " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE, ?, ?, ?, ?, ?)";

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
            sqlArgs.add(amsItemTransH.getRemark());
//            sqlArgs.add(amsItemTransH.getLastUpdateDate());
//            sqlArgs.add(amsItemTransH.getLastUpdateBy());
            sqlArgs.add(amsItemTransH.getAttribute1());
            sqlArgs.add(amsItemTransH.getAttribute2());
            sqlArgs.add(amsItemTransH.getAttribute3());
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
//                + " TO_ORGANIZATION_ID = ?,"
                + " TRANS_DATE = ?,"
                + " RCV_USER = ?,"
                + " LAST_UPDATE_DATE = ?,"
                + " LAST_UPDATE_BY = ?," +
                "   REMARK=? "
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
        String sqlStr = "SELECT " +
                "       AITH.TRANS_ID,\n" +
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
                "       AITH.FROM_DEPT,"+
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
                "       AITH.ATTRIBUTE3 \n" +
                "       FROM " +
                "       AMS_ITEM_TRANS_H   AITH,\n" +
                "       ETS_OBJECT         EO,\n" +
                "       ETS_OBJECT         EO2,\n" +
                "       SF_USER_V          SUV,\n" +
                "       ETS_OU_CITY_MAP    EOCM,\n" +
                "       ETS_OU_CITY_MAP    EOCM2,\n" +
                "       ETS_FLEX_VALUES    EFV,\n" +
                "       ETS_FLEX_VALUE_SET EFVS\n" +
                "       WHERE " +
                "       AITH.FROM_OBJECT_NO = EO.WORKORDER_OBJECT_NO(+)\n" +
                "   AND AITH.TO_OBJECT_NO = EO2.WORKORDER_OBJECT_NO(+)\n" +
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

    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) dtoParameter;
        String sqlStr = "SELECT AITH.TRANS_ID,\n" +
                "       AITH.TRANS_NO,\n" +
                "       AITH.CREATED_BY,\n" +
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
                + "AND (? IS NULL OR TRANS_STATUS = ?)"
                + "AND (? IS NULL OR TO_OBJECT_NO = ?)"
                + "AND (? IS NULL OR TO_ORGANIZATION_ID = ?)"
                + "AND AITH.TRANS_TYPE = NVL(?,AITH.TRANS_TYPE)"
                + "AND AITH.CREATION_DATE >= NVL(?, AITH.CREATION_DATE)"
                + "AND AITH.CREATION_DATE <= NVL(?, AITH.CREATION_DATE)" +
                "  ORDER BY AITH.CREATION_DATE DESC";
        sqlArgs.add(amsItemTransH.getTransNo());
        sqlArgs.add(amsItemTransH.getTransNo());
//		sqlArgs.add(amsItemTransH.getTransType());
//		sqlArgs.add(amsItemTransH.getTransType());
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
//		sqlArgs.add(amsItemTransH.getFromOrganizationId());
//		sqlArgs.add(amsItemTransH.getFromOrganizationId());
        sqlArgs.add(sfUser.getOrganizationId());
        sqlArgs.add(sfUser.getOrganizationId());
        sqlArgs.add(amsItemTransH.getTransType());
        try {
            sqlArgs.add(amsItemTransH.getFromDate());
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

    public SQLModel getQty(String barcode,String  transId,String lineId1,String org) {
        SQLModel model = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) dtoParameter;
        String     sqlStr="";
        if(org.equals("0")){     //只省公司分配
               sqlStr  = "SELECT ASI.BARCODE,\n" +
                "       ASI.QUANTITY,\n" +
                "       (ASI.QUANTITY - ASI.RESERVE_QUANTITY) AS USE_QUANTITY,\n" +
                "       EO.WORKORDER_OBJECT_NAME,\n" +
                "       ASPC.ITEM_NAME,\n" +
                "       ASPC.ITEM_SPEC,\n" +
                "       ASPC.ITEM_CATEGORY,\n" +
                "       EOCM.COMPANY,\n" +
                "       ASI.ORGANIZATION_ID,\n" +
                "       AITD.DETAIL_ID,\n" +
                "       AITD.IS_ALLOT," +
                "       AITD.QUANTITY HOLD_COUNT\n" +
                "  FROM AMS_SPARE_INFO     ASI,\n" +
                "       AMS_SPARE_CATEGORY ASPC,\n" +
                "       ETS_OBJECT         EO,\n" +
                "       ETS_OU_CITY_MAP    EOCM,\n" +
                "       AMS_ITEM_TRANS_D   AITD\n" +
                " WHERE ASI.BARCODE = ASPC.BARCODE\n" +
                "   AND ASI.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                "   AND ASI.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
                "   AND EO.OBJECT_CATEGORY = 71\n" +
                "   AND AITD.ORGANIZATION_ID(+) = EOCM.ORGANIZATION_ID\n" +
                "   AND AITD.TRANS_ID(+) = ?\n" +
                "   AND ASI.BARCODE = ?\n" +
                "   AND AITD.LINE_ID(+) = ?\n" +
                "   AND ASI.ORGANIZATION_ID=82 "+
                 "  AND (AITD.IS_ALLOT IS NULL OR AITD.IS_ALLOT = 1)";
        }else if(org.equals("1")){   //省公司和地市分配
               sqlStr  = "SELECT ASI.BARCODE,\n" +
                       "       ASI.QUANTITY,\n" +
                       "       (ASI.QUANTITY - ASI.RESERVE_QUANTITY) AS USE_QUANTITY,\n" +
                       "       EO.WORKORDER_OBJECT_NAME,\n" +
                       "       ASPC.ITEM_NAME,\n" +
                       "       ASPC.ITEM_SPEC,\n" +
                       "       ASPC.ITEM_CATEGORY,\n" +
                       "       EOCM.COMPANY,\n" +
                       "       ASI.ORGANIZATION_ID,\n" +
                       "       AITD.DETAIL_ID,\n" +
                       "       AITD.IS_ALLOT," +
                       "       AITD.QUANTITY HOLD_COUNT\n" +
                       "  FROM AMS_SPARE_INFO     ASI,\n" +
                       "       AMS_SPARE_CATEGORY ASPC,\n" +
                       "       ETS_OBJECT         EO,\n" +
                       "       ETS_OU_CITY_MAP    EOCM,\n" +
                       "       AMS_ITEM_TRANS_D   AITD,\n" +
                       "       AMS_ITEM_TRANS_H   AITH\n" +
                       " WHERE ASI.BARCODE = ASPC.BARCODE\n" +
                       "   AND ASI.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                       "   AND ASI.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
                       "   AND EO.OBJECT_CATEGORY = 71\n" +
                       "   AND AITD.ORGANIZATION_ID(+) = EOCM.ORGANIZATION_ID\n" +
                       "   AND EOCM.ORGANIZATION_ID(+) != AITH.FROM_ORGANIZATION_ID\n" +
                       "   AND AITD.TRANS_ID(+) = ?\n" +
                       "   AND AITH.TRANS_ID(+) = ?\n" +
                       "   AND ASI.BARCODE = ?\n" +
                       "   AND AITD.LINE_ID(+) = ?\n" +
                       "   AND (AITD.IS_ALLOT IS NULL OR  AITD.IS_ALLOT =1)"+
                       " ORDER BY ASI.ORGANIZATION_ID";
             sqlArgs.add(transId);
        }


        sqlArgs.add(transId);
        sqlArgs.add(barcode);
        sqlArgs.add(lineId1);
        model.setSqlStr(sqlStr);
        model.setArgs(sqlArgs);
        return model;
    }
     public SQLModel getNo(String  transId) {
        SQLModel model = new SQLModel();
        List sqlArgs = new ArrayList();
        String  sqlStr="SELECT AITD.SERIAL_NO,\n" +
                "       AITD.BARCODE,\n" +
                "       ASSC.ITEM_NAME,\n" +
                "       ASSC.ITEM_SPEC,\n" +
                "       ASSC.SPARE_USAGE,\n" +
                "       AMS_PUB_PKG.GET_VENDOR_NAME(ASSC.VENDOR_ID) VENDOR_NAME\n" +
                "  FROM AMS_ITEM_TRANS_D AITD, AMS_SPARE_CATEGORY ASSC\n" +
                " WHERE AITD.IS_ALLOT = 0\n" +
                "   AND AITD.BARCODE = ASSC.BARCODE\n" +
                "   AND AITD.TRANS_ID = ?";
        sqlArgs.add(transId);
        model.setSqlStr(sqlStr);
        model.setArgs(sqlArgs);
        return model;
    }
    public SQLModel deleteData(String detailId,String  transId) {
        SQLModel model = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = " DELETE\n" +
                "  FROM AMS_ITEM_TRANS_D AITD\n" +
                " WHERE AITD.TRANS_ID =?\n" +
                "   AND AITD.DETAIL_ID=?" +
                "   AND AITD.IS_ALLOT =1";
        sqlArgs.add(transId);
        sqlArgs.add(detailId);
        model.setSqlStr(sqlStr);
        model.setArgs(sqlArgs);
        return model;
    }
     public SQLModel updateStatusModel(String status) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) dtoParameter;
        String sqlStr ="UPDATE AMS_ITEM_TRANS_H\n" +
                "   SET TRANS_STATUS = ?, LAST_UPDATE_DATE = SYSDATE, LAST_UPDATE_BY = ?\n" +
                " WHERE TRANS_ID = ?";
        sqlArgs.add(status);
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(amsItemTransH.getTransId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
     public SQLModel updateQtyModel(String qty,String actQty,String orgId,String barcode) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) dtoParameter;
        String sqlStr ="UPDATE AMS_SPARE_DIFF_NUM\n" +
                "   SET APPLY_NUMBER = APPLY_NUMBER + ?," +
                "       REC_NUMBER = REC_NUMBER + ?\n" +
                " WHERE ORGANIZATION_ID = ?\n" +
                "   AND BARCODE = ?";
        sqlArgs.add(qty);
//        actQty="22";
        if(StrUtil.isEmpty(actQty)){
           sqlArgs.add("0");
        }else{
         sqlArgs.add(actQty);
        }
//        sqlArgs.add(sfUser.getOrganizationId());
        sqlArgs.add(orgId);
        sqlArgs.add(barcode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    public SQLModel insertQtyModel(String barcode,String qty,String actQty,String orgId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) dtoParameter;
        String sqlStr ="INSERT INTO AMS_SPARE_DIFF_NUM\n" +
                "  (ORGANIZATION_ID, BARCODE, APPLY_NUMBER,REC_NUMBER)\n" +
                "VALUES\n" +
                "  (?, ?, ? ,?)";
//        sqlArgs.add(sfUser.getOrganizationId());
        sqlArgs.add(orgId);
        sqlArgs.add(barcode);
        sqlArgs.add(qty);
       if(StrUtil.isEmpty(actQty)){
         sqlArgs.add("0");
        }else{
         sqlArgs.add(actQty);
        }
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel selectQtyModel(String barcode,String orgId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) dtoParameter;
        String sqlStr ="SELECT 1\n" +
                "  FROM AMS_SPARE_DIFF_NUM ASDN\n" +
                " WHERE ASDN.ORGANIZATION_ID = ?\n" +
                "   AND ASDN.BARCODE = ?";
//        sqlArgs.add(sfUser.getOrganizationId());
        sqlArgs.add(orgId);
        sqlArgs.add(barcode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel doveif(String transId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
//        AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) dtoParameter;
        String sqlStr =  "SELECT " +
                "  SUM(AITD.QUANTITY) QUANTITY\n" +
                "  FROM " +
                "  AMS_ITEM_TRANS_D AITD\n" +
                "  WHERE " +
                "  AITD.SERIAL_NO IS NOT NULL\n" +
                "  AND AITD.IS_ALLOT = '0'\n" +
                "  AND AITD.TRANS_ID = ?";
        sqlArgs.add(transId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


    public SQLModel getSq(String transId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
//        AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) dtoParameter;
        String sqlStr =  " SELECT SUM(AITL.QUANTITY) QUANTITY\n" +
                "  FROM AMS_ITEM_TRANS_L AITL\n" +
                " WHERE AITL.TRANS_ID = ?";
        sqlArgs.add(transId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}