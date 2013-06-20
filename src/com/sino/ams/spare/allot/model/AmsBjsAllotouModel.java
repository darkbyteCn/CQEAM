package com.sino.ams.spare.allot.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;

import com.sino.framework.sql.BaseSQLProducer;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.spare.allot.dto.AmsBjsAllotHDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2007-11-7
 * Time: 23:21:23
 * To change this template use File | Settings | File Templates.
 */
public class AmsBjsAllotouModel extends BaseSQLProducer {
    private SfUserDTO sfUser = null;

    public AmsBjsAllotouModel(SfUserDTO userAccount, AmsBjsAllotHDTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
    }

    /*  AMS_ITEM_TRANS.GET_ONHAND_QTY(?, EOCM.ORGANIZATION_ID) NOW_COUNT*/
    /*public SQLModel getSQLModel(String itemCode, String transId) {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT EOCM.ORGANIZATION_ID,\n" +
                "       EOCM.COMPANY,\n" +
                "       AMS_ITEM_TRANS.GET_SPARE_ONHAND(?, EOCM.ORGANIZATION_ID) NOW_COUNT,\n" +
                "       AMS_ITEM_TRANS.GET_DETAIL_QTY(?, EOCM.ORGANIZATION_ID, ?) HOLD_COUNT,\n" +
                "       AMS_ITEM_TRANS.GET_ALLOT_DETAIL_ID(?, EOCM.ORGANIZATION_ID, ?) DETAIL_ID\n" +
                "  FROM ETS_OU_CITY_MAP EOCM\n" +
                " WHERE EOCM.ORGANIZATION_ID != 82";
        List list = new ArrayList();
        list.add(itemCode);
        list.add(itemCode);
        list.add(transId);
        list.add(itemCode);
        list.add(transId);
        sqlModel.setArgs(list);
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }*/

    /**
     * 方案2
     * @param itemCode  设备型号
     * @param transId  头ID
     * @return SQLModel
     */
    public SQLModel getSQLModel(String itemCode, String transId) {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT EOCM.ORGANIZATION_ID,\n" +
                "       EOCM.COMPANY,\n" +
                "       AMS_ITEM_TRANS.GET_SPARE_ONHAND2(?, EOCM.ORGANIZATION_ID) NOW_COUNT,\n" +
                "       AMS_ITEM_TRANS.GET_DETAIL_QTY(?, EOCM.ORGANIZATION_ID, ?) HOLD_COUNT,\n" +
                "       AMS_ITEM_TRANS.GET_ALLOT_DETAIL_ID(?, EOCM.ORGANIZATION_ID, ?) DETAIL_ID\n" +
                "  FROM ETS_OU_CITY_MAP EOCM\n" +
                " WHERE EOCM.ORGANIZATION_ID != 82";
        List list = new ArrayList();
        list.add(itemCode);
        list.add(itemCode);
        list.add(transId);
        list.add(itemCode);
        list.add(transId);
        sqlModel.setArgs(list);
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }

    public SQLModel getBarcode(String itemCode) {
        SQLModel sqlModel = new SQLModel();
        List list = new ArrayList();
        String sqlStr = "SELECT EII.BARCODE, ESI.ITEM_NAME, ESI.ITEM_SPEC\n" +
                "  FROM ETS_ITEM_INFO      EII,\n" +
                "       ETS_SYSTEM_ITEM    ESI,\n" +
                "       ETS_OBJECT         EO,\n" +
                "       AMS_OBJECT_ADDRESS AOA\n" +
                " WHERE EII.FINANCE_PROP = 'SPARE'\n" +
                "   AND EII.ITEM_CODE = ESI.ITEM_CODE\n" +
                "   AND EII.ITEM_CODE = ?\n" +
                "   AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                "   AND EO.OBJECT_CATEGORY = 71\n" +
                "   AND EII.ORGANIZATION_ID = 82\n" +
                "   AND EII.ADDRESS_ID = AOA.ADDRESS_ID";
        list.add(itemCode);
//        list.add(servletConfig.getProvinceOrgId());
        sqlModel.setArgs(list);
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }
  public SQLModel getDataUpdateModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsBjsAllotHDTO amsInstrumentHInfo = (AmsBjsAllotHDTO) dtoParameter;
        String sqlStr = "UPDATE AMS_ITEM_ALLOCATE_H SET TRANS_NO=?,TRANS_STATUS=? WHERE TRANS_ID=?";

        sqlArgs.add(amsInstrumentHInfo.getTransNo());
        sqlArgs.add(amsInstrumentHInfo.getTransStatus());
        sqlArgs.add(amsInstrumentHInfo.getTransId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    public SQLModel getDataCreateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = null;
        String sqlStr = null;
        sqlArgs = new ArrayList();
        AmsBjsAllotHDTO amsInstrumentHInfo = (AmsBjsAllotHDTO) dtoParameter;
        sqlStr = "INSERT INTO AMS_ITEM_ALLOCATE_H\n" +
                "  (TRANS_ID,\n" +
                "   TRANS_NO,\n" +
                "   TRANS_TYPE,\n" +
                "   TRANS_STATUS,\n" +
                "   CREATION_DATE,\n" +
                "   CREATED_BY," +
                "   FROM_ORGANIZATION_ID)\n" +
                "VALUES\n" +
                "  (?, ?, ?, ?, SYSDATE, ?,?)";
        sqlArgs.add(amsInstrumentHInfo.getTransId());
        sqlArgs.add(amsInstrumentHInfo.getTransNo());
        sqlArgs.add(amsInstrumentHInfo.getTransType());
        sqlArgs.add(amsInstrumentHInfo.getTransStatus());
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(sfUser.getOrganizationId());


        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    public SQLModel insertLData(String organization, String transId, String count, String itemCode) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = null;
        if (count.equals("")) {

        } else {
            sqlStr = "INSERT INTO AMS_ITEM_TRANS_D\n" +
                    "    (TRANS_ID,\n" +
                    "     DETAIL_ID,\n" +
                    "     ORGANIZATION_ID,\n" +
                    "     ITEM_CODE,\n" +
                    "     QUANTITY)\n" +
                    "  VALUES\n" +
                    "    (?,AMS_ITEM_TRANS_D_S.NEXTVAL,?,?,?)";
            sqlArgs.add(transId);
            sqlArgs.add(organization);
            sqlArgs.add(itemCode);
            sqlArgs.add(count);

        }
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel insertRData(String organization, String transId, String count, String itemCode) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = null;
        if (count.equals("")) {

        } else {
            sqlStr = "   INSERT INTO AMS_ITEM_RESERVED\n" +
                    "      (TRANS_ID, RESERVED_DATE, ITEM_CODE, RESERVED_COUNT, ORGANIZATION_ID)\n" +
                    "    VALUES\n" +
                    "      (?,sysdate,?,?,?)";
            sqlArgs.add(transId);
            sqlArgs.add(itemCode);
            sqlArgs.add(count);
            sqlArgs.add(organization);
        }
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel insertDData(String transId, String detailId, String itemCode, String barcode) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = null;
        sqlStr = "   INSERT INTO AMS_ITEM_ALLOCATE_D\n" +
                "     (TRANS_ID, DETAIL_ID, ITEM_CODE,QUANTITY, BARCODE)\n" +
                "   VALUES\n" +
                "     (?, ?, ?,'1', ?)";
        sqlArgs.add(transId);
        sqlArgs.add(detailId);
        sqlArgs.add(itemCode);
        sqlArgs.add(barcode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getDeleteByTransIdModel(String transId, String itemCode) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM AMS_ITEM_TRANS_D\n" +
                " WHERE TRANS_ID = ?\n" +
                "   AND ITEM_CODE = ?";
        sqlArgs.add(transId);
        sqlArgs.add(itemCode);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel updateItemModel(String addressId, String barcode) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();

        String sqlStr = "UPDATE ETS_ITEM_INFO SET ADDRESS_ID = ? WHERE BARCODE = ?";

        sqlArgs.add(addressId);
        sqlArgs.add(barcode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getDeleteByTransIdModel2(String transId, String itemCode) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM AMS_ITEM_RESERVED\n" +
                " WHERE TRANS_ID = ?\n" +
                "   AND ITEM_CODE = ?";
        sqlArgs.add(transId);
        sqlArgs.add(itemCode);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getDataByTransIdModel(String transId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT AITD.ITEM_CODE,\n" +
                "       ESI.ITEM_NAME,\n" +
                "       ESI.ITEM_SPEC,\n" +
                "       COUNT(EII.ITEM_QTY) ITEM_AMOUNT\n" +
                "  FROM AMS_ITEM_TRANS_D AITD, ETS_SYSTEM_ITEM ESI, ETS_ITEM_INFO EII\n" +
                " WHERE AITD.ITEM_CODE = ESI.ITEM_CODE\n" +
                "   AND EII.ITEM_CODE = ESI.ITEM_CODE\n" +
                "   AND AITD.TRANS_ID = ?\n" +
                " GROUP BY AITD.ITEM_CODE, ESI.ITEM_NAME, ESI.ITEM_SPEC";
        sqlArgs.add(transId);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getDataByPrimaryKey() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsBjsAllotHDTO amsItemTransH = (AmsBjsAllotHDTO) dtoParameter;
        String sqlStr = "SELECT AMS_PUB_PKG.GET_USER_NAME(AIAH.CREATED_BY) CREATEDUSER,\n" +
                "       AIAH.TRANS_ID,\n" +
                "       AIAH.TRANS_NO,\n" +
                "       AIAH.CREATION_DATE,\n" +
                "       AIAH.TRANS_TYPE,\n" +
                "       AIAH.TRANS_STATUS,\n" +
                "       EFV.VALUE\n" +
                "  FROM AMS_ITEM_ALLOCATE_H AIAH,\n" +
                "       ETS_FLEX_VALUES     EFV,\n" +
                "       ETS_FLEX_VALUE_SET  EFVS\n" +
                " WHERE AIAH.TRANS_STATUS = EFV.CODE\n" +
                "   AND EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID\n" +
                "   AND EFVS.CODE = 'ORDER_STATUS'\n" +
                "   AND AIAH.TRANS_ID = ?";
        sqlArgs.add(amsItemTransH.getTransId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    public SQLModel getOrderCancelModel(String transId) {
		SQLModel sqlModel = new SQLModel();
		String sqlStr = "UPDATE"
					 + " AMS_ITEM_ALLOCATE_H AATH"
					 + " SET"
					 + " AATH.TRANS_STATUS = ?,"
					 + " AATH.LAST_UPDATE_DATE = SYSDATE,"
					 + " AATH.LAST_UPDATE_BY = ?"
					 + " WHERE"
					 + " AATH.TRANS_ID = ?";
		List sqlArgs = new ArrayList();
		sqlArgs.add(AssetsDictConstant.CANCELED);
		sqlArgs.add(sfUser.getUserId());
		sqlArgs.add(transId);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
    }
}
