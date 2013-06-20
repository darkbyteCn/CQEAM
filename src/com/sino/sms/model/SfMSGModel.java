package com.sino.sms.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.sms.constant.SMSConstant;

public class SfMSGModel {

    public SfMSGModel() {
        super();
    }

    /**
     * 获取收件箱统计的SQLModel
     * @return
     */
    public SQLModel getInBoxModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT SAI.USER_ID, SU.USERNAME,SU.EMAIL MAIL, SU.MOVETEL, COUNT(*) NUM\n" +
                "  FROM SF_ACT_INFO SAI, SF_USER SU\n" +
                " WHERE SAI.SIGN_FLAG = 'N'\n" +
                "   AND SAI.USER_ID = SU.USER_ID\n" +
                "   AND SAI.AGENT_USER_ID " + SyBaseSQLUtil.isNullNoParam() + " \n" +
                "   AND  " + SyBaseSQLUtil.isNotNull("SU.MOVETEL") + " \n" +
                " GROUP BY SAI.USER_ID, SU.USERNAME,SU.EMAIL, SU.MOVETEL";
        List sqlArgs = new ArrayList();
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    /**
     * 获取工单归档统计的SQLModel
     * @return
     */
    public SQLModel getArchieveModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT EW.CHECKOVER_BY ARCHIVED_BY, SU.USERNAME,SU.EMAIL MAIL, SU.MOVETEL, COUNT(*) NUM\n" +
                "  FROM ETS_WORKORDER EW, SF_USER SU\n" +
                " WHERE EW.CHECKOVER_BY = SU.USER_ID\n" +
                "   AND  " + SyBaseSQLUtil.isNotNull("SU.MOVETEL") + " \n" +
                "   AND EW.WORKORDER_FLAG = (SELECT EFV.CODE\n" +
                "                              FROM ETS_FLEX_VALUES EFV\n" +
                "                             WHERE EFV.FLEX_VALUE_SET_ID =\n" +
                "                                   (SELECT EFVS.FLEX_VALUE_SET_ID\n" +
                "                                      FROM ETS_FLEX_VALUE_SET EFVS\n" +
                "                                     WHERE EFVS.CODE = 'WORKORDER_STATUS')\n" +
                "                               AND EFV.VALUE = '已上传')\n" +
                " GROUP BY EW.CHECKOVER_BY, SU.USERNAME,SU.EMAIL, SU.MOVETEL";
        List sqlArgs = new ArrayList();
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 获取盘点工单归档统计的SQLModel
     * @return
     */
    public SQLModel getCheckOrderArchieveModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT ACH.ARCHIVED_BY, SU.USERNAME, SU.MOVETEL,SU.EMAIL MAIL, COUNT(*) NUM\n" +
                "  FROM AMS_ASSETS_CHECK_HEADER ACH, SF_USER SU\n" +
                " WHERE ACH.ARCHIVED_BY = SU.USER_ID\n" +
                "   AND  " + SyBaseSQLUtil.isNotNull("SU.MOVETEL") + " \n" +
                "   AND ACH.ORDER_STATUS = 'UPLOADED'\n" +
                " GROUP BY ACH.ARCHIVED_BY, SU.USERNAME,SU.EMAIL, SU.MOVETEL";
        List sqlArgs = new ArrayList();
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 获取超期租赁资产SQLModel
     * @return
     */
    public SQLModel getRendMindModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT EII.ORGANIZATION_ID,\n" +
                "       SF_MSG.GET_USER_PHONE(EII.ORGANIZATION_ID, '租赁资产管理员') USER_PHONES,\n" +
                "       ARI.BARCODE,\n" +
                "       ARI.END_DATE\n" +
                "  FROM AMS_RENT_INFO ARI, ETS_ITEM_INFO EII\n" +
                " WHERE ARI.BARCODE = EII.BARCODE\n" +
                "   AND ARI.END_DATE < ADD_MONTHS(GETDATE(), 1)";

        List sqlArgs = new ArrayList();
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 获取短信发送的SQLModel
     * @return
     */
    public SQLModel getDefineModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT SMC.MSG_CATEGORY_ID,\n" +
                "       SMC.MSG_CODE,\n" +
                "       SMD.MSG_DEFINE_ID,\n" +
                "       SMD.MSG_CONTENT MSG_CONTENT,\n" +
                "       SMD.USER_ID,\n" +
                "       SU.EMAIL MAIL,\n" +
                "       SU.USERNAME,\n" +
                "       SMI.MSG_SEND_ID,\n" +
                "       SMI.MSG_CELL_PHONE MOVETEL,\n" +
                "       SF_MSG.NEED_SEND_MESSAGE(SMC.MSG_CODE, SMI.MSG_SEND_ID) SEND_FLAG\n" +
                "  FROM SF_MSG_CATEGORY SMC, SF_MSG_DEFINE SMD, SF_MSG_SEND_INFO SMI,SF_USER SU\n" +
                " WHERE SMC.MSG_CATEGORY_ID = SMD.MSG_CATEGORY_ID\n" +
                "   AND SMD.MSG_DEFINE_ID = SMI.MSG_DEFINE_ID\n" +
                "   AND SMD.USER_ID *= SU.USER_ID\n" +
                "   AND SMC.COLLECT_SEND = 'N'\n" +
                "   AND SMD.HAS_PROCESSED = '" + SMSConstant.MSG_PROCESSED_NO + "'\n" +
                "   ORDER BY SMC.MSG_CODE, SMD.USER_ID, SMI.MSG_CELL_PHONE";
        List sqlArgs = new ArrayList();
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

     /**
     * 功能：根据用户电话获取用户名称
     * @return
     */
    public SQLModel getNameModel(String phone) {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "   SELECT SU.USERNAME FROM SF_USER SU WHERE SU.MOVETEL = ?";
        List sqlArgs = new ArrayList();
        sqlArgs.add(phone);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}