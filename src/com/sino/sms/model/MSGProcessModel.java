package com.sino.sms.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.sms.constant.SMSConstant;
import com.sino.sms.dto.SfMsgDefineDTO;
import com.sino.sms.dto.SfMsgSendInfoDTO;

/**
 * <p>Title: SinoIES</p>
 * <p>Description: 河南移动库存延伸系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class MSGProcessModel {

    public MSGProcessModel() {
        super();
    }

    /**
     * 功能：获取短信已处理的SQLModel对象。
     * @param actId     String
     * @param cellPhone String
     * @return SQLModel
     */
    public SQLModel getFinishPhoneMessageModel(String actId, String cellPhone) {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "UPDATE"
                + " SF_MSG_SEND_INFO SMS"
                + " SET"
                + " HAS_PROCESSED = '" + SMSConstant.MSG_PROCESSED_YES + "'"
                + " WHERE"
                + " ACT_ID = ?"
                + " AND MSG_CELL_PHONE = ?";
        List sqlArgs = new ArrayList();
        sqlArgs.add(actId);
        sqlArgs.add(cellPhone);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：设置已处理的单据短信处理状态为Y。
     * //     * @param actId String
     * //     * @param cellPhone String
     * @return SQLModel
     */
    public SQLModel getFinishOrderMessageModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "UPDATE SF_MSG_SEND_INFO SMI\n" +
                "   SET SMI.HAS_PROCESSED = '" + SMSConstant.MSG_PROCESSED_YES + "'\n" +
                " WHERE SMI.APPLY_NUMBER IN\n" +
                "       (SELECT DISTINCT EW.WORKORDER_NO\n" +
                "          FROM ETS_WORKORDER EW\n" +
                "         WHERE EW.WORKORDER_FLAG = 12)\n" +
                "    OR SMI.APPLY_NUMBER IN\n" +
                "       (SELECT DISTINCT ACH.TRANS_NO\n" +
                "          FROM AMS_ASSETS_CHECK_HEADER ACH\n" +
                "         WHERE ACH.ORDER_STATUS = 'DOWNLOADED')\n" +
                "     OR SMI.APPLY_NUMBER IN\n" +
                "        (SELECT DISTINCT CONVAERT(VARCHAR,(ATL.LINE_ID)\n" +
                "           FROM AMS_ASSETS_TRANS_LINE ATL\n" +
                "          WHERE  " + SyBaseSQLUtil.isNotNull("ATL.CONFIRM_DATE") + " \n" +
                "            AND  " + SyBaseSQLUtil.isNotNull("ATL.CONFIRMED_BY") + " )";
        List sqlArgs = new ArrayList();
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：停止发送符合条件的消息。
     * @return SQLModel
     */
    public SQLModel getFinishMessageModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr =
                "UPDATE SF_MSG_DEFINE SMD\n" +
                        "   SET SMD.HAS_PROCESSED = '" + SMSConstant.MSG_PROCESSED_YES + "'\n" +
                        " WHERE NOT EXISTS (SELECT NULL\n" +
                        "          FROM SF_MSG_CATEGORY SMC, SF_MSG_SEND_INFO SMS\n" +
                        "         WHERE SMD.MSG_CATEGORY_ID = SMC.MSG_CATEGORY_ID\n" +
                        "           AND SMD.MSG_DEFINE_ID = SMS.MSG_DEFINE_ID\n" +
                        "           AND SMS.HAS_PROCESSED = '" + SMSConstant.MSG_PROCESSED_NO + "')";
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }

    /**
     * 功能：获取需要发送的消息的查询语句。
     * @return SQLModel
     */
    public SQLModel getNeedSendMsgModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT"
                + " SMD.*"
                + " FROM"
                + " SF_MSG_DEFINE SMD,"
                + " SF_MSG_CATEGORY SMC"
                + " WHERE"
                + " SMC.MSG_CATEGORY_ID = SMD.MSG_CATEGORY_ID"
                + " AND SMC.ENABLED = '" + SMSConstant.MSG_ENABLED_YES + "'"
                + " AND SMD.HAS_PROCESSED = '" + SMSConstant.MSG_PROCESSED_NO + "'";
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }

    public SQLModel getMsgSendModel(SfMsgDefineDTO message) {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT"
                + " SMC.NEED_RESEND,"
                + " SMC.RESEND_MAXTIMES,"
                + " SMC.RESEND_DISTANCE,"
                + " SMD.MSG_DEFINE_ID,"
                + " SMD.MSG_CONTENT,"
                + " SMS.MSG_CELL_PHONE,"
                + " SMS.FIRST_SEND_TIME,"
                + " SMS.LAST_SEND_TIME,"
                + " SMS.SEND_TIMES"
                + " FROM"
                + " SF_MSG_CATEGORY SMC,"
                + " SF_MSG_DEFINE SMD,"
                + " SF_MSG_SEND_INFO SMS"
                + " WHERE"
                + " SMC.MSG_CATEGORY_ID = SMD.MSG_CATEGORY_ID"
                + " AND SMD.MSG_DEFINE_ID = SMS.MSG_DEFINE_ID"
                + " AND SMD.MSG_DEFINE_ID = ?"
                + " AND SMS.HAS_PROCESSED = ?";
        List sqlArgs = new ArrayList();
        sqlArgs.add(message.getMsgDefineId());
        sqlArgs.add(SMSConstant.MSG_PROCESSED_NO);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：获取消息发送日志更新语句。
     * @param sendDTO   SfMsgSendInfoDTO
     * @param cellPhone String
     * @return SQLModel
     */
    public SQLModel getSendLogModel(SfMsgSendInfoDTO sendDTO, String cellPhone) {
        SQLModel sqlModel = new SQLModel();
        String sendTimes = sendDTO.getSendTimes();
        String sqlStr = "";
        if (sendTimes.equals("0")) {
            sqlStr = "UPDATE"
                    + " SF_MSG_SEND_INFO"
                    + " SET"
                    + " FIRST_SEND_TIME = GETDATE(),"
                    + " SEND_TIMES = SEND_TIMES + 1"
                    + " WHERE"
                    + " MSG_DEFINE_ID = ?"
                    + " AND MSG_CELL_PHONE = ?";
        } else {
            sqlStr = "UPDATE"
                    + " SF_MSG_SEND_INFO"
                    + " SET"
                    + " LAST_SEND_TIME = GETDATE(),"
                    + " SEND_TIMES = SEND_TIMES + 1"
                    + " WHERE"
                    + " MSG_DEFINE_ID = ?"
                    + " AND MSG_CELL_PHONE = ?";
        }
        List sqlArgs = new ArrayList();
        sqlArgs.add(sendDTO.getMsgDefineId());
        sqlArgs.add(cellPhone);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：获取当前时间的查询语句。
     * @return SQLModel
     */
    public SQLModel getCurrTimeModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT"
        	 + "  GETDATE()  CURR_TIME"
              ;
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }
}
