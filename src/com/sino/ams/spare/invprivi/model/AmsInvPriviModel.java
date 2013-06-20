package com.sino.ams.spare.invprivi.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;

import com.sino.framework.sql.BaseSQLProducer;
import com.sino.ams.spare.invprivi.dto.AmsInvPriviDTO;
import com.sino.ams.system.user.dto.SfUserDTO;


/**
 * <p>Title: AmsInvPriviModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsInvPriviModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author V-yuanshuai
 * @version 1.0
 */


public class AmsInvPriviModel extends BaseSQLProducer {

    private SfUserDTO sfUser = null;

    /**
     * 功能：仓库权限表(AMS) AMS_INV_PRIVI 数据库SQL构造层构造函数
     *
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsInvPriviDTO 本次操作的数据
     */
    public AmsInvPriviModel(SfUserDTO userAccount, AmsInvPriviDTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
    }

    /**
     * 功能：框架自动生成仓库权限表(AMS) AMS_INV_PRIVI数据插入SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据插入用SQLModel
     */
    public SQLModel getDataCreateModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsInvPriviDTO amsInvPrivi = (AmsInvPriviDTO) dtoParameter;
        String sqlStr = "INSERT INTO "
                + " AMS_INV_PRIVI("
                + " USER_ID,"
                + " INV_CODE,"
                + " ACTION_CODE,"
                + " PRIVI_ID"
                + ") VALUES ("
                + " ?, ?, ?, AMS_INV_PRIVI_S.NEXTVAL)";

        sqlArgs.add(amsInvPrivi.getUserId());
        sqlArgs.add(amsInvPrivi.getInvCode());
        sqlArgs.add(amsInvPrivi.getActionCode());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成仓库权限表(AMS) AMS_INV_PRIVI数据更新SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据更新用SQLModel
     */
    public SQLModel getDataUpdateModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsInvPriviDTO amsInvPrivi = (AmsInvPriviDTO) dtoParameter;
        String sqlStr = "UPDATE AMS_INV_PRIVI"
                + " SET"
                + " USER_ID = ?,"
                + " INV_CODE = ?,"
                + " ACTION_CODE = ?,"
                + " WHERE"
                + " PRIVI_ID = ?";

        sqlArgs.add(amsInvPrivi.getUserId());
        sqlArgs.add(amsInvPrivi.getInvCode());
        sqlArgs.add(amsInvPrivi.getActionCode());
        sqlArgs.add(amsInvPrivi.getPriviId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成仓库权限表(AMS) AMS_INV_PRIVI数据删除SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据删除用SQLModel
     */
    public SQLModel getDataDeleteModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsInvPriviDTO amsInvPrivi = (AmsInvPriviDTO) dtoParameter;
        String sqlStr = "DELETE FROM"
                + " AMS_INV_PRIVI"
                + " WHERE"
                + " USER_ID = ?"
                + " AND INV_CODE = ?";
        sqlArgs.add(amsInvPrivi.getUserId());
        sqlArgs.add(amsInvPrivi.getInvCode());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成仓库权限表(AMS) AMS_INV_PRIVI数据详细信息查询SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsInvPriviDTO amsInvPrivi = (AmsInvPriviDTO) dtoParameter;
        String sqlStr = "SELECT "
                + " USER_ID,"
                + " INV_CODE,"
                + " ACTION_CODE,"
                + " PRIVI_ID"
                + " FROM"
                + " AMS_INV_PRIVI"
                + " WHERE"
                + " PRIVI_ID = ?";
        sqlArgs.add(amsInvPrivi.getPriviId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成仓库权限表(AMS) AMS_INV_PRIVI多条数据信息查询SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回多条数据信息查询用SQLModel
     */
    public SQLModel getDataMuxModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsInvPriviDTO amsInvPrivi = (AmsInvPriviDTO) dtoParameter;
        String sqlStr = "SELECT "
                + " USER_ID,"
                + " INV_CODE,"
                + " ACTION_CODE,"
                + " PRIVI_ID"
                + " FROM"
                + " AMS_INV_PRIVI"
                + " WHERE"
                + "(? IS NULL OR USER_ID LIKE ?)"
                + "(? IS NULL OR INV_CODE LIKE ?)"
                + "(? IS NULL OR ACTION_CODE LIKE ?)"
                + "(? IS NULL OR PRIVI_ID LIKE ?)";
        sqlArgs.add(amsInvPrivi.getUserId());
        sqlArgs.add(amsInvPrivi.getUserId());
        sqlArgs.add(amsInvPrivi.getInvCode());
        sqlArgs.add(amsInvPrivi.getInvCode());
        sqlArgs.add(amsInvPrivi.getActionCode());
        sqlArgs.add(amsInvPrivi.getActionCode());
        sqlArgs.add(amsInvPrivi.getPriviId());
        sqlArgs.add(amsInvPrivi.getPriviId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成仓库权限表(AMS) AMS_INV_PRIVI页面翻页查询SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getPageQueryModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsInvPriviDTO amsInvPrivi = (AmsInvPriviDTO) dtoParameter;
        String sqlStr =
             /*   "SELECT  \n" +
                        " AIPV_2.* , SU_2.USERNAME USERNAME ,AIV_2.INV_NAME INV_NAME \n" +
                        " FROM (SELECT\n" +
                        "       TMP_V.*,\n" +
                        "       DECODE(INV_APPLY, 1, '<INPUT onPropertyChange=changePrivi(this) TYPE=CHECKBOX NAME=invApplyBox id=invApplyBox'||'_'||ROWNUM||' CHECKED>', '<INPUT onPropertyChange=changePrivi(this) TYPE=CHECKBOX NAME=invApplyBox id=invApplyBox'||'_'||ROWNUM||'>') INV_APPLY_BOX,\n" +
                        "       DECODE(INV_BAD_IN, 1, '<INPUT onPropertyChange=changePrivi(this) TYPE=CHECKBOX NAME= invBadInBox  id=invBadInBox'||'_'||ROWNUM||' CHECKED>', '<INPUT onPropertyChange=changePrivi(this) TYPE=CHECKBOX NAME= invBadInBox  id=invBadInBox'||'_'||ROWNUM||'>') INV_BAD_IN_BOX,\n" +
                        "       DECODE(INV_BAD_RETURN, 1, '<INPUT onPropertyChange=changePrivi(this) TYPE=CHECKBOX NAME= invBadReturnBox  id=invBadReturnBox'||'_'||ROWNUM||' CHECKED>', '<INPUT onPropertyChange=changePrivi(this) TYPE=CHECKBOX NAME= invBadReturnBox  id=invBadReturnBox'||'_'||ROWNUM||'>') INV_BAD_RETURN_BOX,\n" +
                        "       DECODE(INV_DISCARD, 1, '<INPUT onPropertyChange=changePrivi(this) TYPE=CHECKBOX NAME= invDiscardBox  id =invDiscardBox'||'_'||ROWNUM||' CHECKED>', '<INPUT onPropertyChange=changePrivi(this) TYPE=CHECKBOX NAME= invDiscardBox  id=invDiscardBox'||'_'||ROWNUM||'>') INV_DISCARD_BOX,\n" +
                        "       DECODE(INV_NEW_IN, 1, '<INPUT onPropertyChange=changePrivi(this) TYPE=CHECKBOX NAME= invNewInBox  id=invNewInBox'||'_'||ROWNUM||' CHECKED>', '<INPUT onPropertyChange=changePrivi(this) TYPE=CHECKBOX NAME= invNewInBox  id=invNewInBox'||'_'||ROWNUM||'>') INV_NEW_IN_BOX,\n" +
                        "       DECODE(INV_ORDER_PRINT, 1, '<INPUT onPropertyChange=changePrivi(this) TYPE=CHECKBOX NAME= invOrderPrintBox  id=invOrderPrintBox'||'_'||ROWNUM||' CHECKED>', '<INPUT onPropertyChange=changePrivi(this) TYPE=CHECKBOX NAME= invOrderPrintBox  id=invOrderPrintBox'||'_'||ROWNUM||'>') INV_ORDER_PRINT_BOX,\n" +
                        "       DECODE(INV_OUT, 1, '<INPUT onPropertyChange=changePrivi(this) TYPE=CHECKBOX NAME= invOutBox  id=invOutBox'||'_'||ROWNUM||' CHECKED>', '<INPUT onPropertyChange=changePrivi(this) TYPE=CHECKBOX NAME= invOutBox  id=invOutBox'||'_'||ROWNUM||'>') INV_OUT_BOX,\n" +
                        "       DECODE(INV_QUERY, 1, '<INPUT onPropertyChange=changePrivi(this) TYPE=CHECKBOX NAME= invQueryBox  id=invQueryBox'||'_'||ROWNUM||' CHECKED>', '<INPUT onPropertyChange=changePrivi(this) TYPE=CHECKBOX NAME= invQueryBox  id=invQueryBox'||'_'||ROWNUM||'>') INV_QUERY_BOX,\n" +
                        "       DECODE(INV_RCV_IN, 1, '<INPUT onPropertyChange=changePrivi(this) TYPE=CHECKBOX NAME= invRcvInBox  id=invRcvInBox'||'_'||ROWNUM||' CHECKED>', '<INPUT onPropertyChange=changePrivi(this) TYPE=CHECKBOX NAME= invRcvInBox  id=invRcvInBox'||'_'||ROWNUM||'>') INV_RCV_IN_BOX,\n" +
                        "       DECODE(INV_REPAIR_IN, 1, '<INPUT onPropertyChange=changePrivi(this) TYPE=CHECKBOX NAME= invRepairInBox  id=invRepairInBox'||'_'||ROWNUM||' CHECKED>', '<INPUT onPropertyChange=changePrivi(this) TYPE=CHECKBOX NAME= invRepairInBox  id=invRepairInBox'||'_'||ROWNUM||'>') INV_REPAIR_IN_BOX,\n" +
                        "       DECODE(INV_SEND_REPAIR, 1, '<INPUT onPropertyChange=changePrivi(this) TYPE=CHECKBOX NAME= invSendRepairBox  id=invSendRepairBox'||'_'||ROWNUM||' CHECKED>', '<INPUT onPropertyChange=changePrivi(this) TYPE=CHECKBOX NAME= invSendRepairBox  id=invSendRepairBox'||'_'||ROWNUM||'>') INV_SEND_REPAIR_BOX,\n" +
                        "       DECODE(INV_TRANSFER, 1, '<INPUT onPropertyChange=changePrivi(this) TYPE=CHECKBOX NAME=invTransferBox id=invTransferBox'||'_'||ROWNUM||' CHECKED>', '<INPUT onPropertyChange=changePrivi(this) TYPE=CHECKBOX NAME=invTransferBox id=invTransferBox'||'_'||ROWNUM||'>') INV_TRANSFER_BOX,\n" +
                        "       ROWNUM\n" +
                        "FROM      (SELECT AIP.USER_ID,\n" +
                        "               AIP.INV_CODE,\n" +
                        "               SUM(DECODE(AIP.ACTION_CODE, 'INV_APPLY', 1, 0)) INV_APPLY,  \n" +
                        "               SUM(DECODE(AIP.ACTION_CODE, 'INV_BAD_IN', 1, 0)) INV_BAD_IN,  \n" +
                        "               SUM(DECODE(AIP.ACTION_CODE, 'INV_BAD_RETURN', 1, 0)) INV_BAD_RETURN,  \n" +
                        "               SUM(DECODE(AIP.ACTION_CODE, 'INV_DISCARD', 1, 0)) INV_DISCARD,  \n" +
                        "               SUM(DECODE(AIP.ACTION_CODE, 'INV_NEW_IN', 1, 0)) INV_NEW_IN,  \n" +
                        "               SUM(DECODE(AIP.ACTION_CODE, 'INV_ORDER_PRINT', 1, 0)) INV_ORDER_PRINT,  \n" +
                        "               SUM(DECODE(AIP.ACTION_CODE, 'INV_OUT', 1, 0)) INV_OUT,  \n" +
                        "               SUM(DECODE(AIP.ACTION_CODE, 'INV_QUERY', 1, 0)) INV_QUERY,  \n" +
                        "               SUM(DECODE(AIP.ACTION_CODE, 'INV_RCV_IN', 1, 0)) INV_RCV_IN, \n" +
                        "               SUM(DECODE(AIP.ACTION_CODE, 'INV_REPAIR_IN', 1, 0)) INV_REPAIR_IN,  \n" +
                        "               SUM(DECODE(AIP.ACTION_CODE, 'INV_SEND_REPAIR', 1, 0)) INV_SEND_REPAIR, \n" +
                        "       SUM(DECODE(AIP.ACTION_CODE, 'INV_TRANSFER', 1, 0)) INV_TRANSFER  \n" +
                        "FROM\n" +
                        "(SELECT t1.USER_ID,t1.INV_CODE,aip_1.ACTION_CODE FROM\n" +
                        "     (SELECT su.USER_ID,aiv.INV_CODE \n" +
                        "     FROM sf_user su ,(SELECT eo.WORKORDER_OBJECT_NO Inv_CODE,eo.ORGANIZATION_ID  ORGANIZATION_ID FROM ETS_OBJECT eo  WHERE EO.OBJECT_CATEGORY BETWEEN '71' AND '75') aiv\n" +
                        "        WHERE su.ORGANIZATION_ID = aiv.ORGANIZATION_ID \n" +
                        "           AND SU.ORGANIZATION_ID = ?\n" +
                        "           AND aiv.INV_CODE=NVL(?,aiv.INV_CODE) \n" +
                        "           AND su.USER_ID = NVL(?,su.USER_ID)) t1 ,\n" +
                        "AMS_INV_PRIVI aip_1\n" +
                        "WHERE t1.USER_ID = aip_1.USER_ID(+)\n" +
                        "AND t1.INV_CODE = aip_1.INV_CODE(+)) AIP\n" +
                        "GROUP  BY AIP.USER_ID,\n" +
                        "          AIP.INV_CODE) TMP_V) AIPV_2  ,SF_USER SU_2, AMS_INV_V  AIV_2 \n" +
                        " WHERE AIPV_2.USER_ID = SU_2.USER_ID   \n" +
                        "   AND AIPV_2.INV_CODE = AIV_2.INV_CODE \n" +
                        "\n" +
                        " "*/
                "SELECT  \n" +
                        " AIPV_2.* , SU_2.USERNAME USERNAME ,AIV_2.INV_NAME INV_NAME, " +
                        " AMS_PUB_PKG.GET_FLEX_VALUE(AIV_2.BUSINESS_CATEGORY, 'INV_BIZ_CATEGORY') BIZ_CATEGORY_NAME \n" +
                        " FROM " +
                        //" SF_USER SU," +
                        " (SELECT\n" +
                        "       TMP_V.*,\n" +
                        "       DECODE(INV_IN, 1, '<INPUT onPropertyChange=changePrivi(this) TYPE=CHECKBOX NAME= invInBox  id=invInBox'||'_'||ROWNUM||' CHECKED>', '<INPUT onPropertyChange=changePrivi(this) TYPE=CHECKBOX NAME= invInBox  id=invInBox'||'_'||ROWNUM||'>') INV_IN_BOX,\n" +
                        "       DECODE(INV_OUT, 1, '<INPUT onPropertyChange=changePrivi(this) TYPE=CHECKBOX NAME= invOutBox  id=invOutBox'||'_'||ROWNUM||' CHECKED>', '<INPUT onPropertyChange=changePrivi(this) TYPE=CHECKBOX NAME= invOutBox  id=invOutBox'||'_'||ROWNUM||'>') INV_OUT_BOX,\n" +
                        "       DECODE(INV_QUERY, 1, '<INPUT onPropertyChange=changePrivi(this) TYPE=CHECKBOX NAME=invQueryBox id=invQueryBox'||'_'||ROWNUM||' CHECKED>', '<INPUT onPropertyChange=changePrivi(this) TYPE=CHECKBOX NAME=invQueryBox id=invQueryBox'||'_'||ROWNUM||'>') INV_QUERY_BOX,\n" +
                        "       ROWNUM\n" +
                        "FROM      (SELECT AIP.USER_ID,\n" +
                        "               AIP.INV_CODE,\n" +
                        "               SUM(DECODE(AIP.ACTION_CODE, 'INV_IN', 1, 0)) INV_IN, /*接收入库*/\n" +
                        "               SUM(DECODE(AIP.ACTION_CODE, 'INV_OUT', 1, 0)) INV_OUT, /*备件出库*/\n" +
                        "               SUM(DECODE(AIP.ACTION_CODE, 'INV_QUERY', 1, 0)) INV_QUERY  /*设备查询*/   \n" +
                        "FROM\n" +
                        "(SELECT t1.USER_ID,t1.INV_CODE,aip_1.ACTION_CODE FROM\n" +
                        "     (SELECT su.USER_ID,aiv.INV_CODE \n" +
                        "     FROM sf_user su ,(SELECT eo.WORKORDER_OBJECT_NO Inv_CODE,eo.ORGANIZATION_ID  ORGANIZATION_ID FROM ETS_OBJECT eo  WHERE eo.OBJECT_CATEGORY BETWEEN '71' AND '75' /*AND eo.BUSINESS_CATEGORY = NVL(?, eo.BUSINESS_CATEGORY)*/ ) aiv\n" +
                        "        WHERE su.ORGANIZATION_ID = aiv.ORGANIZATION_ID \n" +
                        "           AND SU.ORGANIZATION_ID = ?\n" +
                        "           AND aiv.INV_CODE=NVL(?,aiv.INV_CODE) \n" +
                        "           AND su.USER_ID = NVL(?,su.USER_ID)) t1 ,\n" +
                        "AMS_INV_PRIVI aip_1\n" +
                        "WHERE t1.USER_ID = aip_1.USER_ID(+)\n" +
                        "AND t1.INV_CODE = aip_1.INV_CODE(+)) AIP\n" +
                        "GROUP  BY AIP.USER_ID,\n" +
                        "          AIP.INV_CODE) TMP_V) AIPV_2  ,SF_USER SU_2, AMS_INV_V  AIV_2 \n" +
                        " WHERE AIPV_2.USER_ID = SU_2.USER_ID   \n" +
                        "   AND AIPV_2.INV_CODE = AIV_2.INV_CODE" +
                        "   AND AIV_2.BUSINESS_CATEGORY = NVL(?, AIV_2.BUSINESS_CATEGORY)";
        
        
        
        sqlArgs.add(sfUser.getOrganizationId());
        sqlArgs.add(amsInvPrivi.getExecuteInv());
        sqlArgs.add(amsInvPrivi.getExecuteUser());
        sqlArgs.add(amsInvPrivi.getBusinessCategory());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
