package com.sino.ams.instrument.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.instrument.dto.AmsInstrumentHDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

/**
 * Created by IntelliJ IDEA.
 * User: Zyun
 * Date: 2008-2-13
 * Time: 17:43:43
 * To change this template use File | Settings | File Templates.
 */
public class AmsInstrumentRepairModel extends AMSSQLProducer {

    public AmsInstrumentRepairModel(SfUserDTO userAccount, AmsInstrumentHDTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    public SQLModel insertLData(String barcodeNo, String transId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO AMS_INSTRU_TRANS_L\n" +
                "  (LINE_ID,\n" +
                "   BARCODE,\n" +
                "   TRANS_ID)\n" +
                "VALUES\n" +
                "  ( NEWID() ,?,?)";
        sqlArgs.add(barcodeNo);
        sqlArgs.add(transId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
      public SQLModel insertRData(String barcodeNo, String transId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO AMS_INSTRUMENT_RESERVED\n" +
                "  (BARCODE, TRANS_ID, RESERVED_DATE)\n" +
                "VALUES\n" +
                "  (?, ?, GETDATE())";
        sqlArgs.add(barcodeNo);
        sqlArgs.add(transId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    public SQLModel getDataCreateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
//        try {
            List sqlArgs = new ArrayList();
            AmsInstrumentHDTO amsInstrumentHInfo = (AmsInstrumentHDTO) dtoParameter;
            String sqlStr = "INSERT INTO AMS_INSTRU_TRANS_H\n" +
                    "  (TRANS_ID,\n" +
                    "   TRANS_NO,\n" +
                    "   TRANS_TYPE,\n" +
                    "   TRANS_STATUS,\n" +
//                    "   BORROW_USER,\n" +
//                    "   BORROW_DATE,\n" +
                    "   CONFIRM_USER,\n" +
                    "   CONFIRM_DATE,\n" +
//                    "   CANCEL_DATE," +
//                    "   PRE_RETURN_DATE," +      //预计归还日期
//                    "   BORROW_REASON," +         //借用原因
                    "   REPAIRE_REASON," +         //送修原因
                    "   REPAIRE_USER," +         //送修人
                    "   REPAIRE_DATE," +         //送修日期
                    "   REPARI_FACTORY," +         //送修厂商
                    "   REMARK," +                 //备注
                    "   ORGANIZATION_ID" +                 //OU
                    ")\n" +
                    "VALUES(?,?,?,?,?,GETDATE(),?,?,GETDATE(),?,?,?)";
            sqlArgs.add(amsInstrumentHInfo.getTransId());
            sqlArgs.add(amsInstrumentHInfo.getTransNo());
            sqlArgs.add(amsInstrumentHInfo.getTransType());
            sqlArgs.add(amsInstrumentHInfo.getTransStatus());
//            sqlArgs.add(amsInstrumentHInfo.getUserId());
//            sqlArgs.add(amsInstrumentHInfo.getBorrowDate());
//            sqlArgs.add(amsInstrumentHInfo.getConfirmUser());
            sqlArgs.add(userAccount.getUserId());
//            sqlArgs.add(amsInstrumentHInfo.getConfirmDate());
//            sqlArgs.add(amsInstrumentHInfo.getCancelDate());
//            sqlArgs.add(amsInstrumentHInfo.getPreReturnDate());
//            sqlArgs.add(amsInstrumentHInfo.getBorrowReason());
            sqlArgs.add(amsInstrumentHInfo.getRepaireReason());
//            sqlArgs.add(amsInstrumentHInfo.getRepaireUser());
//            sqlArgs.add(amsInstrumentHInfo.getUserId());
            sqlArgs.add(userAccount.getUserId());
//            sqlArgs.add(amsInstrumentHInfo.getRepaireDate());
//            sqlArgs.add(amsInstrumentHInfo.getRepariFactory());
            sqlArgs.add(amsInstrumentHInfo.getVendorName());    //取供应商编号
            sqlArgs.add(amsInstrumentHInfo.getRemark());
            sqlArgs.add(userAccount.getOrganizationId());
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
//        } catch (CalendarException e) {
//            e.printLog();
//            throw new SQLModelException(e);
//        }
        return sqlModel;
    }

    public SQLModel getDataUpdateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            AmsInstrumentHDTO amsInstrumentHInfo = (AmsInstrumentHDTO) dtoParameter;
            String sqlStr = "UPDATE " +
                            " AMS_INSTRU_TRANS_H\n" +
                            " SET" +
                            " TRANS_ID      = ?,\n" +
                            " TRANS_NO      = ?,\n" +
                            " TRANS_TYPE    = ?,\n" +
                            " TRANS_STATUS  = ?,\n" +
                            " BORROW_USER   = ?,\n" +
                            " BORROW_DATE   = ?,\n" +
                            " CONFIRM_USER  = ?,\n" +
                            " CONFIRM_DATE  = ?,\n" +
                            " CANCEL_DATE   = ?,\n" +
                            " CANCEL_REASON = ?,\n" +
                            " PRE_RETURN_DATE = ?,\n" +
                            " BORROW_REASON = ?,\n" +
                            " REPAIRE_REASON = ?,\n" +
                            " REPAIRE_USER = ?,\n" +
                            " REPAIRE_DATE = ?,\n" +
                            " REPARI_FACTORY = ?,\n" +
                            " REMARK = ?\n" +
                            " WHERE " +
                            " TRANS_ID = ?";
            sqlArgs.add(amsInstrumentHInfo.getTransId());
            sqlArgs.add(amsInstrumentHInfo.getTransNo());
            sqlArgs.add(amsInstrumentHInfo.getTransType());
            sqlArgs.add(amsInstrumentHInfo.getTransStatus());
            sqlArgs.add(amsInstrumentHInfo.getBorrowUser());
            sqlArgs.add(amsInstrumentHInfo.getBorrowDate());
            sqlArgs.add(amsInstrumentHInfo.getConfirmUser());
            sqlArgs.add(amsInstrumentHInfo.getConfirmDate());
            sqlArgs.add(amsInstrumentHInfo.getCancelDate());
            sqlArgs.add(amsInstrumentHInfo.getCalPattern());
            sqlArgs.add(amsInstrumentHInfo.getReturnDate());
            sqlArgs.add(amsInstrumentHInfo.getBorrowReason());
            sqlArgs.add(amsInstrumentHInfo.getRepaireReason());
            sqlArgs.add(amsInstrumentHInfo.getRepaireUser());
            sqlArgs.add(amsInstrumentHInfo.getRepaireDate());
            sqlArgs.add(amsInstrumentHInfo.getRepariFactory());
            sqlArgs.add(amsInstrumentHInfo.getRemark());
            sqlArgs.add(amsInstrumentHInfo.getTransId());
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException e) {
            e.printLog();
            throw new SQLModelException(e);
        }
        return sqlModel;
    }

    public SQLModel getPrimaryKeyDataModel() {      //点明细 头信息
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsInstrumentHDTO amsInstrumentHInfo = (AmsInstrumentHDTO) dtoParameter;
        String sqlStr = "SELECT AITH.TRANS_ID,\n" +
                "       AITH.TRANS_NO,\n" +
                "       AITH.TRANS_TYPE,\n" +
                "       AITH.TRANS_STATUS,\n" +
                "       AMS_PUB_PKG.GET_USER_NAME(AITH.BORROW_USER) BORROW_NAME,\n" +
                "       AITH.BORROW_USER,\n" +
                "       AITH.BORROW_DATE,\n" +
                "       AMS_PUB_PKG.GET_STATUS_NAME(AITH.TRANS_STATUS) TRANS_STATUS_NAME,\n" +
//                "       AMS_PUB_PKG.GET_VENDOR_NAME(AITH.REPARI_FACTORY) REPARI_FACTORY,\n" +
                "       AITH.REPARI_FACTORY,"+
                "       AITH.PRE_RETURN_DATE,"+
                "       AITH.REPAIRE_REASON,"+
                "       AITH.REPAIRE_USER,"+
                "       AITH.REPAIRE_DATE,"+
                "       AITH.REPARI_FACTORY,"+
                "       AITH.BORROW_REASON,"+
                "       AITH.REMARK"+
                "  FROM AMS_INSTRU_TRANS_H AITH\n" +
                " WHERE AITH.TRANS_ID = ?";
        sqlArgs.add(amsInstrumentHInfo.getTransId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getDeleteByTransIdModel(String transId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM"
                + " AMS_INSTRU_TRANS_L"
                + " WHERE"
                + " TRANS_ID = ?";
        sqlArgs.add(transId);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    public SQLModel deleteByBarcodeNoModel(String barcodeNo) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM"
                + " AMS_INSTRUMENT_RESERVED"
                + " WHERE"
                + " BARCODE = ?";
        sqlArgs.add(barcodeNo);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getPageQueryModel() throws SQLModelException {        //送修页面查询
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            AmsInstrumentHDTO amsInstrumentHInfo = (AmsInstrumentHDTO) dtoParameter;
            String sqlStr = "SELECT AITH.TRANS_NO,\n" +
                    "       AMS_PUB_PKG.GET_USER_NAME(AITH.REPAIRE_USER) REPAIRE_USER,\n" +
                    "       AITH.REPAIRE_DATE,\n" +
                    "       AMS_PUB_PKG.GET_USER_NAME(AITH.CONFIRM_USER) CONFIRM_USER,\n" +
                    "       AITH.CONFIRM_DATE,\n" +
                    "       AITH.TRANS_STATUS,\n" +
                    "       AITH.TRANS_ID,\n" +
                    "       AITH.ORGANIZATION_ID,\n" +
                    "       AMS_PUB_PKG.GET_STATUS_NAME(AITH.TRANS_STATUS) TRANS_STATUS_NAME\n" +
                    "  FROM AMS_INSTRU_TRANS_H AITH\n" +
//                    " WHERE AITH.TRANS_TYPE = 'INS-BRW'\n" +
                    " WHERE AITH.TRANS_TYPE = 'INS-REP'\n" +                     //送修
//                    "   AND SU.USER_ID = AITH.CONFIRM_USER\n" +
//                    "   AND AITH.TRANS_NO LIKE dbo.NVL(?, AITH.TRANS_NO)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR  AITH.TRANS_NO  LIKE ?)\n" +
//                    "   AND AITH.TRANS_STATUS = dbo.NVL(?, AITH.TRANS_STATUS)\n" +
                    "   AND AITH.CONFIRM_DATE <= dbo.NVL(?, AITH.CONFIRM_DATE)\n" +
                    "   AND AITH.CONFIRM_DATE >= dbo.NVL(?, AITH.CONFIRM_DATE)";
            sqlArgs.add(amsInstrumentHInfo.getTransNo());
            sqlArgs.add(amsInstrumentHInfo.getTransNo());
//            sqlArgs.add(amsInstrumentHInfo.getTransStatus());
            sqlArgs.add(amsInstrumentHInfo.getToDate());
            sqlArgs.add(amsInstrumentHInfo.getFromDate());
            if ((!userAccount.isProvinceUser()) && (!userAccount.isSysAdmin())) {
                sqlStr += "AND AITH.ORGANIZATION_ID = ?";
                sqlArgs.add(userAccount.getOrganizationId());
            }
             sqlStr += "ORDER BY AITH.CONFIRM_DATE DESC";
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException e) {
            e.printLog();
            throw new SQLModelException(e);
        }
        return sqlModel;
    }

    public SQLModel updateInfoModel(String bUser, String barcodeNo) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();

        String sqlStr = "UPDATE AMS_INSTRUMENT_INFO SET CURR_KEEP_USER = ? WHERE BARCODE = ?";

        sqlArgs.add(bUser);
        sqlArgs.add(barcodeNo);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
     public SQLModel updateHRepalModel(String transId,String transStatus) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();

        String sqlStr = "UPDATE AMS_INSTRU_TRANS_H SET CANCEL_DATE=GETDATE() ,CANCEL_REASON=?,TRANS_STATUS=? WHERE TRANS_ID=?";

        sqlArgs.add(transStatus);
        sqlArgs.add(transStatus);
        sqlArgs.add(transId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    public SQLModel updateHModel(String transId,String transStatus) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();

        String sqlStr = "UPDATE AMS_INSTRU_TRANS_H SET CONFIRM_USER =?   ,CONFIRM_DATE= GETDATE() ," +
                " TRANS_STATUS=?  WHERE TRANS_ID=?";

        sqlArgs.add(userAccount.getUserId());
        sqlArgs.add(transStatus);
        sqlArgs.add(transId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getByTransIdModel(String transId) {      //明细的 行信息
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
//        String str = "SELECT ESI.ITEM_NAME,\n" +
//                "       EII.BARCODE,\n" +
//                "       ESI.ITEM_SPEC,\n" +
//                "       SU.USERNAME CNAME,\n" +
//                "       AII.INSTRU_USAGE,\n" +
//                "       EMPV.VENDOR_NAME\n" +
//                "  FROM AMS_INSTRU_TRANS_L  AITL,\n" +
//                "       ETS_SYSTEM_ITEM     ESI,\n" +
//                "       ETS_ITEM_INFO       EII,\n" +
//                "       SF_USER             SU,\n" +
//                "       AMS_INSTRUMENT_INFO AII,\n" +
//                "       ETS_MIS_PO_VENDORS  EMPV\n" +
//                " WHERE EII.BARCODE = AITL.BARCODE\n" +
//                "   AND EII.ITEM_CODE = ESI.ITEM_CODE\n" +
//                "   AND SU.USER_ID = AII.CREATED_BY\n" +
//                "   AND AII.BARCODE = AITL.BARCODE\n" +
//                "   AND EMPV.VENDOR_ID = ESI.VENDOR_ID\n" +
//                "   AND AITL.TRANS_ID = ?";
        String str = " SELECT " +
                    " ESI.ITEM_NAME,\n" +
                    " EII.BARCODE,\n" +
                    " ESI.ITEM_SPEC ,\n" +
                    " EII.ATTRIBUTE3 INSTRU_USAGE,\n" +
                    " AMS_PUB_PKG.GET_VENDOR_NAME(ESI.VENDOR_ID) VENDOR_NAME,\n" +
                    " AMS_PUB_PKG.GET_USER_NAME(EII.RESPONSIBILITY_USER) RESPONSIBILITY_NAME\n" +
//                    " AMS_PUB_PKG.GET_USER_NAME(EII.RESPONSIBILITY_USER) CNAME\n" +
                    " FROM " +
                    " ETS_SYSTEM_ITEM ESI, " +
                    " ETS_ITEM_INFO EII, " +
                    " AMS_INSTRU_TRANS_L AITL\n" +
                    " WHERE " +
                    " EII.BARCODE = AITL.BARCODE\n" +
                    " AND " +
                    " EII.ITEM_CODE = ESI.ITEM_CODE"+
                    " AND AITL.TRANS_ID = ?";
        sqlArgs.add(transId);

        sqlModel.setSqlStr(str);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


  public SQLModel updateItemInfo(String maintainUser, String barcode){
      SQLModel sqlModel = new SQLModel();
      List sqlArgs = new ArrayList();
      String str = "UPDATE "
            + " ETS_ITEM_INFO"
			+ " SET"
            + " ITEM_STATUS ='SEND_REPAIR',"
//            + " RESPONSIBILITY_USER = "+userAccount.getUserId()+",\n"
//            + " RESPONSIBILITY_DEPT = '',\n"
//            + " MAINTAIN_USER ='"+ maintainUser +"',\n"                            //"+ maintainUser +"
            + " MAINTAIN_USER ='',\n"                            //"+ maintainUser +"
            + " LAST_UPDATE_DATE = GETDATE(),\n"
			+ " LAST_UPDATE_BY = "+userAccount.getUserId()+"\n"
			+ " WHERE"
			+ " BARCODE = '"+barcode+"'\n";
      sqlModel.setSqlStr(str);
      sqlModel.setArgs(sqlArgs);
      return sqlModel;
  }

   public SQLModel tempItemInfo(String maintainUser, String barcode){
      SQLModel sqlModel = new SQLModel();
      List sqlArgs = new ArrayList();
      String str = "UPDATE "
            + " ETS_ITEM_INFO"
			+ " SET"
            + " ITEM_STATUS ='SEND_REPAIR',"
            + " RESPONSIBILITY_USER = "+userAccount.getUserId()+",\n"
            + " RESPONSIBILITY_DEPT = '',\n"
            + " MAINTAIN_USER = '',\n"
            + " LAST_UPDATE_DATE = GETDATE(),\n"
			+ " LAST_UPDATE_BY = "+userAccount.getUserId()+"\n"
			+ " WHERE"
			+ " BARCODE = '"+barcode+"',\n";
      sqlModel.setSqlStr(str);
      sqlModel.setArgs(sqlArgs);
      return sqlModel;
  }


   public SQLModel cancelItemInfo( String barcode){
      SQLModel sqlModel = new SQLModel();
      List sqlArgs = new ArrayList();
      String str = "UPDATE "
            + " ETS_ITEM_INFO"
			+ " SET"
            + " ITEM_STATUS =''NORMAL'',"
//            + " RESPONSIBILITY_USER = "+userAccount.getUserId()+",\n"
//            + " RESPONSIBILITY_DEPT = '',\n"
            + " MAINTAIN_USER = '',\n"
            + " LAST_UPDATE_DATE = GETDATE(),\n"
			+ " LAST_UPDATE_BY = "+userAccount.getUserId()+"\n"
			+ " WHERE"
			+ " BARCODE = '"+barcode+"'\n";
      sqlModel.setSqlStr(str);
      sqlModel.setArgs(sqlArgs);
      return sqlModel;
  }
}
