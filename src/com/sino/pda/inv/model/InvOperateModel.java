package com.sino.pda.inv.model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.conn.DBManager;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2009-1-7
 * Time: 21:27:27
 * To change this template use File | Settings | File Templates.
 */
public class InvOperateModel {
    public String getAssetNumber(Connection conn,String company_code,String assetsType, int quantity) throws SQLException {
        String billNo=null;
        String sqlStr = "";
        sqlStr = "BEGIN ? := AMS_ORDERNO_PKG.GET_ORDER_NO(?,?,?); END;";

        CallableStatement cStmt = null;
        try {
            cStmt = conn.prepareCall(sqlStr);
            cStmt.registerOutParameter(1, Types.VARCHAR);
            cStmt.setString(2, company_code);
            cStmt.setString(3, assetsType);
            cStmt.setInt(4, 1);

            cStmt.execute();
            billNo = cStmt.getString(1);
        } finally {
            DBManager.closeDBStatement(cStmt);
        }

        return billNo;
    }

    public int createInvBillHeader(Connection conn,String transNo,String objCode,
                                   String transType,String itemType,int userid)throws SQLException
    {
        int nRet=-1;
        int retValue=0;
        String sqlStr = "";
        sqlStr = "BEGIN ? := EAM_INV_OPERATE.CREATE_BILL_HEADER_BY_OBJNO(?,?,?,?,?,?,?); END;";

        CallableStatement cStmt = null;
        try {
            cStmt = conn.prepareCall(sqlStr);
            cStmt.registerOutParameter(1, Types.NUMERIC);
            cStmt.setString(2, transNo);
            cStmt.setInt(3, StrUtil.strToInt(objCode));
            cStmt.setString(4, transType);
            cStmt.setString(5, itemType);
            cStmt.setInt(6, userid);
            cStmt.registerOutParameter(7, Types.VARCHAR);
            cStmt.registerOutParameter(8, Types.NUMERIC);

            Logger.logInfo(sqlStr);
            Logger.logInfo("[2]"+transNo+"[3]"+objCode+"[4]"+transType+"[5]"+itemType+"[6]"+String.valueOf(userid));
            cStmt.execute();

            retValue = cStmt.getInt(1);
            nRet=cStmt.getInt(8);

            if(retValue==0)
                Logger.logError(cStmt.getString(7));
        } finally {
            DBManager.closeDBStatement(cStmt);
        }

        return nRet;
    }

    private void logParameter(int trans_id,SfUserDTO sfUser,ItemXmlModel itmModel)
    {
        String tmpStr="";
        try{
            tmpStr=tmpStr+"[2]"+String.valueOf( trans_id);
            tmpStr=tmpStr+"[3]"+itmModel.barcode;
            tmpStr=tmpStr+"[4]"+ itmModel.item_code;
            tmpStr=tmpStr+"[5]"+itmModel.category;
            tmpStr=tmpStr+"[6]"+ itmModel.name;
            tmpStr=tmpStr+"[7]"+ itmModel.spec;
            tmpStr=tmpStr+"[8]"+itmModel.item_category2;
            tmpStr=tmpStr+"[9]"+StrUtil.strToInt(itmModel.Status);
            tmpStr=tmpStr+"[10]"+ itmModel.start_date;
            tmpStr=tmpStr+"[11]"+ itmModel.quantity;
            tmpStr=tmpStr+"[12]"+ itmModel.price;
            tmpStr=tmpStr+"[13]"+itmModel.responsibility_dept;
            tmpStr=tmpStr+"[14]"+itmModel.responsibility_user;
            tmpStr=tmpStr+"[15]"+sfUser.getUserId();
            tmpStr=tmpStr+"[16]"+itmModel.maintain_user;
            tmpStr=tmpStr+"[17]"+itmModel.new_object_no;
            tmpStr=tmpStr+"[18]"+itmModel.attribute3;
            tmpStr=tmpStr+"[19]"+itmModel.manual;
            tmpStr=tmpStr+"[20]"+sfUser.getOrganizationId();
            Logger.logInfo(tmpStr);
        }catch(Exception e)
        {
            Logger.logInfo("Get Procedure Parameter error:"+e.toString());
        }
    }

    public int strToInt(String str) {
        int retValue = -1;
        try {
            retValue = Integer.parseInt(str);
        } catch (NumberFormatException ex) {
        }
        return retValue;
    }

    public int createInvBillLine(Connection conn,String transNo,int trans_id,
                                 //    String transType,String itemType,
                                 SfUserDTO sfUser,ItemXmlModel itmModel)
    {
        int nRet=-1;
        int retValue=0;
        String nPosition="0";
        String sqlStr = "";
        sqlStr = "BEGIN ? := EAM_INV_OPERATE.DEAL_WITH_BILL_LINE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); END;";
        /**
         * FUNCTION DEAL_WITH_BILL_LINE(
         *             2 P_BILL_ID        IN NUMBER,
                       2        P_BARCODE        IN VARCHAR2,
                       3        P_ITEM_CODE      IN NUMBER,
                       4        P_CATEGORY       IN VARCHAR2,
                       5        P_NAME           IN VARCHAR2,
                       6        P_SPEC           IN VARCHAR2,
                       7        P_ITEM_CATEGORY2 IN VARCHAR2,
                       8        P_STATUS         IN NUMBER,
                       9        P_START_DATE     IN DATE,
                       10        P_QUANTITY       IN NUMBER,
                       11        P_PRICE          IN NUMBER,
                       12        P_DEPT_CODE      IN NUMBER,
                       13        P_EMPLOYEE_ID    IN NUMBER,
                       14        P_USER_ID        IN NUMBER,
                       15        P_MAINTAIN_USER  IN VARCHAR2,
                       16        P_NEW_OBJ_NO     IN NUMBER,
                       17        P_MANUFACTORY    IN VARCHAR2,
                       18        P_MANUAL         IN VARCHAR2,
                       19        P_ORG_ID         IN NUMBER,
                       20        ERR_MSG          OUT VARCHAR2) RETURN NUMBER;
         */
        CallableStatement cStmt = null;
        double nPrice=0;
        int user_id=sfUser.getUserId();
        try {
            cStmt = conn.prepareCall(sqlStr);

            nPosition="1";

            cStmt.registerOutParameter(1, Types.NUMERIC);
            cStmt.setInt(2, trans_id);
            cStmt.setString(3, itmModel.barcode);
            cStmt.setString(4, itmModel.item_code);
            cStmt.setString(5, itmModel.category);
            cStmt.setString(6, itmModel.name);
            cStmt.setString(7, itmModel.spec);
            cStmt.setString(8, itmModel.item_category2);
            cStmt.setInt(9,strToInt(itmModel.Status));
            cStmt.setString(10, itmModel.start_date);

            nPosition="2";

            if(!StrUtil.isEmpty(itmModel.price))
                nPrice= Double.valueOf(itmModel.price).doubleValue();

            cStmt.setInt(11, strToInt(itmModel.quantity));
            cStmt.setDouble(12, nPrice);
            nPosition="3";

            cStmt.setInt(13, strToInt(itmModel.responsibility_dept));
            cStmt.setInt(14, strToInt(itmModel.responsibility_user));
            cStmt.setInt(15, user_id);
            cStmt.setString(16, itmModel.maintain_user);
            cStmt.setInt(17, strToInt(itmModel.new_object_no));

            nPosition="4";

            cStmt.setString(18, itmModel.attribute3);
            cStmt.setString(19, itmModel.manual);
            cStmt.setInt(20, sfUser.getOrganizationId());
            cStmt.registerOutParameter(21, Types.VARCHAR);


            Logger.logInfo(sqlStr);
            logParameter(trans_id,sfUser,itmModel);

            cStmt.execute();
            nPosition="5";
            retValue = cStmt.getInt(1);
            nRet=retValue;

            if(retValue==0)
                Logger.logError(cStmt.getString(21));
        }catch(SQLException e)
        {
             Logger.logInfo("Bill line Error["+nPosition+"]:"+e.toString());
        } finally {
            DBManager.closeDBStatement(cStmt);
        }

        return nRet;
    }
}
