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
 * Date: 2008-2-25
 * Time: 11:27:18
 * To change this template use File | Settings | File Templates.
 */
public class AmsInstrVendorRetModel extends AMSSQLProducer {

      public AmsInstrVendorRetModel(SfUserDTO userAccount, AmsInstrumentHDTO dtoParameter) {
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
                  "  ( NEWID(),?,?)";
          sqlArgs.add(barcodeNo);
          sqlArgs.add(transId);
          sqlModel.setSqlStr(sqlStr);
          sqlModel.setArgs(sqlArgs);
          return sqlModel;
      }

    /**
     * 功能:仪器仪表的送修返还头文件的生成model
     * @return
     * @throws SQLModelException
     */
      public SQLModel getDataCreateModel() throws SQLModelException {
          SQLModel sqlModel = new SQLModel();
          try {
              List sqlArgs = new ArrayList();
              AmsInstrumentHDTO amsInstrumentHInfo = (AmsInstrumentHDTO) dtoParameter;
              String sqlStr = "INSERT INTO AMS_INSTRU_TRANS_H\n" +
                      "  (TRANS_ID, TRANS_NO, TRANS_TYPE, TRANS_STATUS,CONFIRM_USER,CONFIRM_DATE, RETURN_USER, RETURN_DATE,REPARI_FACTORY,ORGANIZATION_ID)\n" +
                      "VALUES\n" +
                      "  (?, ?, ?, ?, ?,GETDATE(), ?, ?,?,?)";
              sqlArgs.add(amsInstrumentHInfo.getTransId());
              sqlArgs.add(amsInstrumentHInfo.getTransNo());
              sqlArgs.add(amsInstrumentHInfo.getTransType());
              sqlArgs.add(amsInstrumentHInfo.getTransStatus());
              sqlArgs.add(userAccount.getUserId());
//            sqlArgs.add(amsInstrumentHInfo.getReturnUser());
              sqlArgs.add(amsInstrumentHInfo.getReturnUser());
              sqlArgs.add(amsInstrumentHInfo.getReturnDate());
              sqlArgs.add(amsInstrumentHInfo.getVendorName());    //取供应商编号
              sqlArgs.add(userAccount.getOrganizationId());
              sqlModel.setSqlStr(sqlStr);
              sqlModel.setArgs(sqlArgs);
          } catch (CalendarException e) {
              e.printLog();
              throw new SQLModelException(e);
          }
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

       public SQLModel updateInfoModel( String barcodeNo) {
          SQLModel sqlModel = new SQLModel();
          List sqlArgs = new ArrayList();

          String sqlStr = "UPDATE AMS_INSTRUMENT_INFO SET CURR_KEEP_USER = null WHERE BARCODE = ?";

          sqlArgs.add(barcodeNo);
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
      public SQLModel getDataUpdateModel() throws SQLModelException {
          SQLModel sqlModel = new SQLModel();
          try {
              List sqlArgs = new ArrayList();
              AmsInstrumentHDTO amsInstrumentHInfo = (AmsInstrumentHDTO) dtoParameter;
              String sqlStr = "UPDATE AMS_INSTRU_TRANS_H\n" +
                      "   SET TRANS_ID      = ?,\n" +
                      "       TRANS_NO      = ?,\n" +
                      "       TRANS_TYPE    = ?,\n" +
                      "       TRANS_STATUS  = ?,\n" +
                      "       BORROW_USER   = ?,\n" +
                      "       BORROW_DATE   = ?,\n" +
//                    "       CONFIRM_USER  = ?,\n" +
//                    "       CONFIRM_DATE  = ?,\n" +
                      "       CANCEL_DATE   = ?,\n" +
                      "       CANCEL_REASON = ?\n" +
                      " WHERE TRANS_ID = ?";
              sqlArgs.add(amsInstrumentHInfo.getTransId());
              sqlArgs.add(amsInstrumentHInfo.getTransNo());
              sqlArgs.add(amsInstrumentHInfo.getTransType());
              sqlArgs.add(amsInstrumentHInfo.getTransStatus());
              sqlArgs.add(amsInstrumentHInfo.getBorrowUser());
              sqlArgs.add(amsInstrumentHInfo.getBorrowDate());
//            sqlArgs.add(amsInstrumentHInfo.getConfirmUser());
//            sqlArgs.add(amsInstrumentHInfo.getConfirmDate());
              sqlArgs.add(amsInstrumentHInfo.getCancelDate());
              sqlArgs.add(amsInstrumentHInfo.getCalPattern());
              sqlArgs.add(amsInstrumentHInfo.getTransId());
              sqlModel.setSqlStr(sqlStr);
              sqlModel.setArgs(sqlArgs);
          } catch (CalendarException e) {
              e.printLog();
              throw new SQLModelException(e);
          }
          return sqlModel;
      }

      public SQLModel getPrimaryKeyDataModel() {     //明细找头表
          SQLModel sqlModel = new SQLModel();
          List sqlArgs = new ArrayList();
          AmsInstrumentHDTO amsInstrumentHInfo = (AmsInstrumentHDTO) dtoParameter;
          String sqlStr = "SELECT AITH.TRANS_NO,\n" +
                  " AITH.TRANS_STATUS,\n" +
                  " AMS_PUB_PKG.GET_USER_NAME(AITH.RETURN_USER) RETURN_NAME,\n" +
                  " AITH.RETURN_DATE,\n" +
                  " AITH.TRANS_TYPE,\n" +
                  " AITH.CHECK_USER,\n" +
                  " AITH.REPARI_FACTORY VENDOR_NAME,\n"+
                  " AITH.TRANS_ID,\n" +
                  " AMS_PUB_PKG.GET_STATUS_NAME(AITH.TRANS_STATUS) TRANS_STATUS_NAME\n" +
                  " FROM " +
                  " AMS_INSTRU_TRANS_H AITH\n" +
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
       //归还单查询
      public SQLModel getPageQueryModel() throws SQLModelException {
          SQLModel sqlModel = new SQLModel();
          try {
              List sqlArgs = new ArrayList();
              AmsInstrumentHDTO amsInstrumentHInfo = (AmsInstrumentHDTO) dtoParameter;
              String sqlStr = " SELECT " +
                              " AITH.TRANS_NO,\n" +
                              " AMS_PUB_PKG.GET_USER_NAME(AITH.RETURN_USER) RNAME,\n" +
                              " AITH.RETURN_DATE,\n" +
                              " AMS_PUB_PKG.GET_USER_NAME(AITH.CONFIRM_USER) QNAME,\n" +
                              " AITH.CONFIRM_DATE,\n" +
                              " AITH.TRANS_STATUS,\n" +
                              " AITH.RETURN_USER,\n" +
                              " AITH.TRANS_ID,\n" +
                              " AITH.REPARI_FACTORY VENDOR_NAME,\n"+
                              " AITH.ORGANIZATION_ID,\n" +
                              " EFV.VALUE TRANS_STATUS_NAME\n" +
                              " FROM " +
                              " AMS_INSTRU_TRANS_H AITH,\n" +
                              " ETS_FLEX_VALUES    EFV,\n" +
                              " ETS_FLEX_VALUE_SET EFVS\n" +
//                            " SF_USER SU\n" +
                              " WHERE " +
                              " EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID\n" +
                              " AND EFVS.CODE = 'ORDER_STATUS'\n" +
                              " AND AITH.TRANS_TYPE = 'INS-VRE'\n" +
                              " AND EFV.CODE = AITH.TRANS_STATUS\n" +
//                            " AND SU.USER_ID = AITH.CONFIRM_USER\n" +
                              " AND ( " + SyBaseSQLUtil.isNull() + "  OR  AITH.TRANS_NO  LIKE ?)\n" +
                              " AND AITH.RETURN_DATE >= dbo.NVL(?, AITH.RETURN_DATE)\n" +
                              " AND AITH.RETURN_DATE <= dbo.NVL(?, AITH.RETURN_DATE)\n" ;
//                            " AND AITH.TRANS_STATUS = dbo.NVL(?, AITH.TRANS_STATUS)";
              sqlArgs.add(amsInstrumentHInfo.getTransNo());
              sqlArgs.add(amsInstrumentHInfo.getTransNo());
              sqlArgs.add(amsInstrumentHInfo.getFromDate());
              sqlArgs.add(amsInstrumentHInfo.getToDate());
//            sqlArgs.add(amsInstrumentHInfo.getTransStatus());
               if ((!userAccount.isProvinceUser()) && (!userAccount.isSysAdmin())) {
                  sqlStr += "AND AITH.ORGANIZATION_ID = ?";
                  sqlArgs.add(userAccount.getOrganizationId());
              }
              sqlStr += "ORDER BY AITH.RETURN_DATE DESC";
              sqlModel.setSqlStr(sqlStr);
              sqlModel.setArgs(sqlArgs);
          } catch (CalendarException e) {
              e.printLog();
              throw new SQLModelException(e);
          }
          return sqlModel;
      }

      public SQLModel getByTransIdModel(String transId) {     //明细查找行表
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
//                      " AMS_PUB_PKG.GET_USER_NAME(EII.RESPONSIBILITY_USER) CNAME\n" +
                      " AMS_PUB_PKG.GET_USER_NAME(EII.RESPONSIBILITY_USER) RESPONSIBILITY_NAME\n" +
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

      //归还单查询
//    public SQLModel getPageQueryModel() throws SQLModelException {        //页面查询
//        SQLModel sqlModel = new SQLModel();
//        try {
//            List sqlArgs = new ArrayList();
//            AmsInstrumentHDTO amsInstrumentHInfo = (AmsInstrumentHDTO) dtoParameter;
//            String sqlStr = "SELECT " +
//                    " AITH.TRANS_NO,\n" +
//                    " AMS_PUB_PKG.GET_USER_NAME(AITH.BORROW_USER) BNAME,\n" +
//                    " AITH.BORROW_DATE,\n" +
//                    " AMS_PUB_PKG.GET_USER_NAME(AITH.CONFIRM_USER) QNAME,\n" +
//                    " AITH.CONFIRM_DATE,\n" +
//                    " AITH.RETURN_DATE,\n" +
//                    " AITH.TRANS_STATUS,\n" +
//                    " AITH.TRANS_ID,\n" +
//                    " AMS_PUB_PKG.GET_STATUS_NAME(AITH.TRANS_STATUS) TRANS_STATUS_NAME\n" +
//                    " FROM " +
//                    " AMS_INSTRU_TRANS_H AITH\n" +
//                    " WHERE " +
//                    " AITH.TRANS_TYPE = 'INS-BRW'\n" +
//                    " AND AITH.TRANS_STATUS ='COMPLETED'\n"+
//                    " AND AITH.TRANS_NO LIKE dbo.NVL(?, AITH.TRANS_NO)\n" +
//                    " AND AITH.TRANS_STATUS = dbo.NVL(?, AITH.TRANS_STATUS)\n" +
//                    " AND AITH.BORROW_DATE <= dbo.NVL(?, AITH.BORROW_DATE)\n" +
//                    " AND AITH.BORROW_DATE >= dbo.NVL(?, AITH.BORROW_DATE)";
//            sqlArgs.add(amsInstrumentHInfo.getTransNo());
//            sqlArgs.add(amsInstrumentHInfo.getTransStatus());
//            sqlArgs.add(amsInstrumentHInfo.getToDate());
//            sqlArgs.add(amsInstrumentHInfo.getFromDate());
//            sqlModel.setSqlStr(sqlStr);
//            sqlModel.setArgs(sqlArgs);
//        } catch (CalendarException e) {
//            e.printLog();
//            throw new SQLModelException(e);
//        }
//        return sqlModel;
//    }


    public SQLModel updateItemInfo(String barcode){
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String str = "UPDATE "
              + " ETS_ITEM_INFO"
              + " SET"
              + " ITEM_STATUS ='NORMAL',"
//            + " RESPONSIBILITY_USER = "+userAccount.getUserId()+",\n"
//            + " RESPONSIBILITY_DEPT = '',\n"
//              + " MAINTAIN_USER ='',\n"                            //"+ maintainUser +"
              + " LAST_UPDATE_DATE = GETDATE(),\n"
              + " LAST_UPDATE_BY = "+userAccount.getUserId()+"\n"
              + " WHERE"
              + " BARCODE = '"+barcode+"'\n";
        sqlModel.setSqlStr(str);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

}
