package com.sino.ams.log.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.StrUtil;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.log.dto.EtsFavoritesDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: EtsFavoritesModel</p>
 * <p>Description:程序自动生成SQL构造器“EtsFavoritesModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author yuyao
 * @version 1.0
 */


public class EtsFavoritesModel extends BaseSQLProducer {

    private SfUserDTO sfUser = null;

    /**
     * 功能：用户个人收藏夹(EAM) ETS_FAVORITES 数据库SQL构造层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter EtsFavoritesDTO 本次操作的数据
     */
    public EtsFavoritesModel(SfUserDTO userAccount, EtsFavoritesDTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
    }

    /**
     * 功能：框架自动生成用户个人收藏夹(EAM) ETS_FAVORITES数据插入SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据插入用SQLModel
     */
    public SQLModel getDataCreateModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsFavoritesDTO etsFavorites = (EtsFavoritesDTO) dtoParameter;
        String sqlStr = "INSERT INTO "
                + " ETS_FAVORITES("
                + " SYSTEMID,"
                + " SORT_NO,"
                + " USER_ID,"
                + " SF_RES_ID,"
                + " CREATION_DATE,"
                + " CREATED_BY,"
                + " LAST_UPDATE_DATE,"
                + " LAST_UPDATE_BY"
                + ") VALUES ("
                + " NEWID(), ?, ?, ?, ?, ?, ?, ?)";

        sqlArgs.add(etsFavorites.getSortNo());
        sqlArgs.add(etsFavorites.getUserId());
        sqlArgs.add(etsFavorites.getSfResId());
        sqlArgs.add(etsFavorites.getCreationDate());
        sqlArgs.add(etsFavorites.getCreatedBy());
        sqlArgs.add(etsFavorites.getLastUpdateDate());
        sqlArgs.add(etsFavorites.getLastUpdateBy());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成用户个人收藏夹(EAM) ETS_FAVORITES数据更新SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据更新用SQLModel
     */
    public SQLModel getDataUpdateModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsFavoritesDTO etsFavorites = (EtsFavoritesDTO) dtoParameter;
        String sqlStr = "UPDATE ETS_FAVORITES"
                + " SET"
                + " SORT_NO = ?,"
                + " USER_ID = ?,"
                + " SF_RES_ID = ?,"
                + " CREATION_DATE = ?,"
                + " CREATED_BY = ?,"
                + " LAST_UPDATE_DATE = ?,"
                + " LAST_UPDATE_BY = ?"
                + " WHERE"
                + " SYSTEMID = ?";

        sqlArgs.add(etsFavorites.getSortNo());
        sqlArgs.add(etsFavorites.getUserId());
        sqlArgs.add(etsFavorites.getSfResId());
        sqlArgs.add(etsFavorites.getCreationDate());
        sqlArgs.add(etsFavorites.getCreatedBy());
        sqlArgs.add(etsFavorites.getLastUpdateDate());
        sqlArgs.add(etsFavorites.getLastUpdateBy());
        sqlArgs.add(etsFavorites.getSystemid());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成用户个人收藏夹(EAM) ETS_FAVORITES数据删除SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据删除用SQLModel
     */
    public SQLModel getDataDeleteModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsFavoritesDTO etsFavorites = (EtsFavoritesDTO) dtoParameter;
        String sqlStr = "DELETE FROM"
                + " ETS_FAVORITES"
                + " WHERE"
                + " SYSTEMID = ?";
        sqlArgs.add(etsFavorites.getSystemid());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成用户个人收藏夹(EAM) ETS_FAVORITES数据详细信息查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsFavoritesDTO etsFavorites = (EtsFavoritesDTO) dtoParameter;
        String sqlStr = "SELECT "
                + " SYSTEMID,"
                + " SORT_NO,"
                + " USER_ID,"
                + " SF_RES_ID,"
                + " CREATION_DATE,"
                + " CREATED_BY,"
                + " LAST_UPDATE_DATE,"
                + " LAST_UPDATE_BY"
                + " FROM"
                + " ETS_FAVORITES"
                + " WHERE"
                + " SYSTEMID = ?";
        sqlArgs.add(etsFavorites.getSystemid());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成用户个人收藏夹(EAM) ETS_FAVORITES多条数据信息查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回多条数据信息查询用SQLModel
     */
    public SQLModel getDataMuxModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsFavoritesDTO etsFavorites = (EtsFavoritesDTO) dtoParameter;
        String sqlStr = "SELECT "
                + " SYSTEMID,"
                + " SORT_NO,"
                + " USER_ID,"
                + " SF_RES_ID,"
                + " CREATION_DATE,"
                + " CREATED_BY,"
                + " LAST_UPDATE_DATE,"
                + " LAST_UPDATE_BY"
                + " FROM"
                + " ETS_FAVORITES"
                + " WHERE"
                + "( " + SyBaseSQLUtil.isNull() + "  OR SYSTEMID LIKE ?)"
                + "( " + SyBaseSQLUtil.isNull() + "  OR SORT_NO LIKE ?)"
                + "( " + SyBaseSQLUtil.isNull() + "  OR USER_ID LIKE ?)"
                + "( " + SyBaseSQLUtil.isNull() + "  OR SF_RES_ID LIKE ?)"
                + "( " + SyBaseSQLUtil.isNull() + "  OR CREATION_DATE LIKE ?)"
                + "( " + SyBaseSQLUtil.isNull() + "  OR CREATED_BY LIKE ?)"
                + "( " + SyBaseSQLUtil.isNull() + "  OR LAST_UPDATE_DATE LIKE ?)"
                + "( " + SyBaseSQLUtil.isNull() + "  OR LAST_UPDATE_BY LIKE ?)";
        sqlArgs.add(etsFavorites.getSystemid());
        sqlArgs.add(etsFavorites.getSystemid());
        sqlArgs.add(etsFavorites.getSortNo());
        sqlArgs.add(etsFavorites.getSortNo());
        sqlArgs.add(etsFavorites.getUserId());
        sqlArgs.add(etsFavorites.getUserId());
        sqlArgs.add(etsFavorites.getSfResId());
        sqlArgs.add(etsFavorites.getSfResId());
        sqlArgs.add(etsFavorites.getCreationDate());
        sqlArgs.add(etsFavorites.getCreationDate());
        sqlArgs.add(etsFavorites.getCreatedBy());
        sqlArgs.add(etsFavorites.getCreatedBy());
        sqlArgs.add(etsFavorites.getLastUpdateDate());
        sqlArgs.add(etsFavorites.getLastUpdateDate());
        sqlArgs.add(etsFavorites.getLastUpdateBy());
        sqlArgs.add(etsFavorites.getLastUpdateBy());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：根据外键关联字段 sfResId 构造查询数据SQL。
     * 框架自动生成数据用户个人收藏夹(EAM) ETS_FAVORITES详细信息查询SQLModel，请根据实际需要修改。
     * @param sfResId String
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    private SQLModel getDataBySfResIdModel(int sfResId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT "
                + " SYSTEMID,"
                + " SORT_NO,"
                + " USER_ID,"
                + " CREATION_DATE,"
                + " CREATED_BY,"
                + " LAST_UPDATE_DATE,"
                + " LAST_UPDATE_BY"
                + " FROM"
                + " ETS_FAVORITES"
                + " WHERE"
                + " SF_RES_ID = ?";
        sqlArgs.add(sfResId);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：根据外键获取数据
     * @param foreignKey 传入的外键字段名称。
     * @return SQLModel
     */
    public SQLModel getDataByForeignKeyModel(String foreignKey) {
        SQLModel sqlModel = null;
        EtsFavoritesDTO etsFavorites = (EtsFavoritesDTO) dtoParameter;
        if (foreignKey.equals("sfResId")) {
            sqlModel = getDataBySfResIdModel(etsFavorites.getSfResId());
        }
        return sqlModel;
    }

    /**
     * 功能：根据外键关联字段 sfResId 构造数据删除SQL。
     * 框架自动生成数据用户个人收藏夹(EAM) ETS_FAVORITES 数据删除SQLModel，请根据实际需要修改。
     * @param sfResId String
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    private SQLModel getDeleteBySfResIdModel(int sfResId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM"
                + " ETS_FAVORITES"
                + " WHERE"
                + " SF_RES_ID = ?";
        sqlArgs.add(sfResId);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：根据外键字段删除数据
     * @param foreignKey 传入的外键字段名称。
     * @return SQLModel
     */
    public SQLModel getDeleteByForeignKeyModel(String foreignKey) {
        SQLModel sqlModel = null;
        EtsFavoritesDTO etsFavorites = (EtsFavoritesDTO) dtoParameter;
        if (foreignKey.equals("sfResId")) {
            sqlModel = getDeleteBySfResIdModel(etsFavorites.getSfResId());
        }
        return sqlModel;
    }

    /**
     * 功能：框架自动生成用户个人收藏夹(EAM) ETS_FAVORITES页面翻页查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getPageQueryModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsFavoritesDTO etsFavorites = (EtsFavoritesDTO) dtoParameter;
        String sqlStr = "SELECT "
                + " SYSTEMID,"
                + " SORT_NO,"
                + " USER_ID,"
                + " SF_RES_ID,"
                + " CREATION_DATE,"
                + " CREATED_BY,"
                + " LAST_UPDATE_DATE,"
                + " LAST_UPDATE_BY"
                + " FROM"
                + " ETS_FAVORITES"
                + " WHERE"
                + " ( " + SyBaseSQLUtil.isNull() + "  OR SYSTEMID LIKE ?)"
                + "AND ( " + SyBaseSQLUtil.isNull() + "  OR SORT_NO LIKE ?)"
                + "AND ( " + SyBaseSQLUtil.isNull() + "  OR USER_ID LIKE ?)"
                + "AND ( " + SyBaseSQLUtil.isNull() + "  OR SF_RES_ID LIKE ?)"
                + "AND ( " + SyBaseSQLUtil.isNull() + "  OR CREATION_DATE LIKE ?)"
                + "AND ( " + SyBaseSQLUtil.isNull() + "  OR CREATED_BY LIKE ?)"
                + "AND ( " + SyBaseSQLUtil.isNull() + "  OR LAST_UPDATE_DATE LIKE ?)"
                + "AND ( " + SyBaseSQLUtil.isNull() + "  OR LAST_UPDATE_BY LIKE ?)";
        sqlArgs.add(etsFavorites.getSystemid());
        sqlArgs.add(etsFavorites.getSystemid());
        sqlArgs.add(etsFavorites.getSortNo());
        sqlArgs.add(etsFavorites.getSortNo());
        sqlArgs.add(etsFavorites.getUserId());
        sqlArgs.add(etsFavorites.getUserId());
        sqlArgs.add(etsFavorites.getSfResId());
        sqlArgs.add(etsFavorites.getSfResId());
        sqlArgs.add(etsFavorites.getCreationDate());
        sqlArgs.add(etsFavorites.getCreationDate());
        sqlArgs.add(etsFavorites.getCreatedBy());
        sqlArgs.add(etsFavorites.getCreatedBy());
        sqlArgs.add(etsFavorites.getLastUpdateDate());
        sqlArgs.add(etsFavorites.getLastUpdateDate());
        sqlArgs.add(etsFavorites.getLastUpdateBy());
        sqlArgs.add(etsFavorites.getLastUpdateBy());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    //以下是通过选择父栏目  然后传个参数  选择子栏目
    public SQLModel getMenu() {
        SQLModel sqlModel = new SQLModel();
        ArrayList list = new ArrayList();
        String sqlStr = "SELECT SRD.RES_ID, SRD.RES_NAME\n" +
                "  FROM SF_RES_DEFINE SRD\n" +
                " WHERE SRD.RES_PAR_ID " + SyBaseSQLUtil.isNullNoParam() + " \n" +
                "   AND EXISTS (SELECT NULL\n" +
                "          FROM SF_RES_PRIVS SRP, SF_USER_RIGHT SUR\n" +
                "         WHERE SRP.ROLE_NAME = SUR.ROLE_NAME\n" +
                "           AND SRP.SYSTEM_ID = SRD.SYSTEM_ID\n" +
                "           AND SUR.USER_ID = ?)\n" +
                " ORDER BY SRD.RES_ID, SRD.RES_PAR_ID";
        list.add(sfUser.getUserId());
        sqlModel.setArgs(list);
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }

    public SQLModel getSmallMenu(String resParId) {
        SQLModel sqlModel = new SQLModel();
        ArrayList list = new ArrayList();
        String sqlStr = "SELECT SRD.RES_NAME,SRD.RES_ID,SRD.SYSTEM_ID  \n" +
                "  FROM SF_RES_DEFINE SRD\n" +
                " WHERE  " + SyBaseSQLUtil.isNotNull("SRD.RES_PAR_ID") + " \n" +
                "   AND  " + SyBaseSQLUtil.isNotNull("SRD.RES_URL") + " \n" +
                "   AND SRD.ENABLED = 'Y'\n" +        //是否有效
                "   AND SRD.VISIBLE = 'Y'\n" +        //是否可见
                "   AND EXISTS (SELECT NULL\n" +
                "          FROM SF_RES_PRIVS SRP, SF_USER_RIGHT SUR\n" +
                "         WHERE SRP.ROLE_NAME = SUR.ROLE_NAME\n" +
                "           AND SRP.SYSTEM_ID = SRD.SYSTEM_ID\n" +
                "           AND SUR.USER_ID = ?)\n" +
                "   AND (SRD.RES_PAR_ID = ? OR SRD.RES_PAR_ID LIKE ? || '.%')\n" +
                " ORDER BY SRD.RES_ID, SRD.RES_PAR_ID";
        list.add(sfUser.getUserId());
        list.add(resParId);
        list.add(resParId);
        sqlModel.setArgs(list);
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }

    public SQLModel insertData(String menus,int i) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO ETS_FAVORITES\n" +
                "  (SYSTEMID," +
                "   SORT_NO, \n" +
                "   USER_ID,\n" +
                "   SF_RES_ID,\n" +
                "   CREATION_DATE,\n" +
                "   CREATED_BY )\n" +
                "VALUES\n" +
                "  ( NEWID() ,?, ?, ?, GETDATE(), ?)";
        sqlArgs.add(i);
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(Integer.valueOf(menus));
        sqlArgs.add(sfUser.getUserId());
        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }

    public SQLModel updateData() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE ETS_FAVORITES"
                + " SET"
                + " SORT_NO = ?,"
                + " USER_ID = ?,"
                + " SF_RES_ID = ?,"
                + " CREATION_DATE = ?,"
                + " CREATED_BY = ?,"
                + " LAST_UPDATE_DATE = ?,"
                + " LAST_UPDATE_BY = ?"
                + " WHERE"
                + " SYSTEMID = ?";

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }

    public SQLModel getData() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT TOP 6 SRD.SYSTEM_ID,SRD.RES_NAME,SRD.IS_POPUP,SRD.POPSCRIPT,SRD.RES_URL\n" +
                "  FROM ETS_FAVORITES EF, SF_RES_DEFINE SRD\n" +
                " WHERE SRD.SYSTEM_ID = EF.SF_RES_ID" +
                "       AND EF.USER_ID=?" +
                "       ORDER BY EF.SORT_NO";
        sqlArgs.add(sfUser.getUserId());
        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }
    public  SQLModel getNewInboxCountModel(String currUserId) {
        String sql =
               "SELECT COUNT(*) TOTAL\n" +
                       "  FROM SF_ACT_INFO H, FND_USER FU\n" +
                       " WHERE H.SFACT_PICK_USER = FU.USER_NAME\n" +
                       "   AND FU.USER_ID = ?";
        ArrayList al = new ArrayList();
        al.add(sfUser.getUserId());
        SQLModel sm = new SQLModel();
        sm.setArgs(al);
        sm.setSqlStr(sql);
        return sm;
    }
    public SQLModel getInboxNum() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = " SELECT Count(*) INBOX_NUM\n" +
                        " FROM SF_USER          SP,\n" +
                             " SF_USER          SP2,\n" +
                             " SF_ACT           SA,\n" +
                             " SF_ACT_INFO      SAI,\n" +
                             " SF_TASK_DEFINE   STD,\n" +
                             " SF_PROCEDURE_DEF SPD\n" +
                       " WHERE SA.FROM_USER_ID *= SP2.USER_ID\n" +
                         " AND SA.CREATED_BY = SP.USER_ID\n" +
                         " AND SA.ACTID = SAI.ACT_ID\n" +
                         " AND SA.CUR_TASK_ID = STD.TASK_ID\n" +
                         " AND SA.PROC_ID = SPD.PROC_ID\n" +
                         " AND ((SAI.USER_ID = ? AND SAI.AGENT_USER_ID " + SyBaseSQLUtil.isNullNoParam() + " ) OR\n" +
                         " (SAI.AGENT_USER_ID = ?))\n" +
                         " AND SAI.SIGN_FLAG = 'N'";
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(sfUser.getUserId());
        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }

    public SQLModel getHalfBoxNum() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = " SELECT Count(*) HALFBOX_NUM\n" +
                        " FROM SF_USER          SP,\n" +
                             " SF_USER          SP2,\n" +
                             " SF_ACT           SA,\n" +
                             " SF_ACT_INFO      SAI,\n" +
                             " SF_TASK_DEFINE   STD,\n" +
                             " SF_PROCEDURE_DEF SPD\n" +
                        " WHERE SA.FROM_USER_ID *= SP2.USER_ID\n" +
                          " AND SA.CREATED_BY = SP.USER_ID\n" +
                          " AND SA.ACTID = SAI.ACT_ID\n" +
                          " AND SA.CUR_TASK_ID = STD.TASK_ID\n" +
                          " AND SA.PROC_ID = SPD.PROC_ID\n" +
                          " AND ((SAI.USER_ID = ? AND SAI.AGENT_USER_ID " + SyBaseSQLUtil.isNullNoParam() + " ) Or\n" +
                          " (SAI.AGENT_USER_ID = ?))\n" +
                          " AND SAI.SIGN_FLAG = 'Y'";
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(sfUser.getUserId());
        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }
     public  SQLModel getPendingCountModel(String currUserId) {
        String sql =
               "SELECT COUNT(*) TOTAL\n" +
                       "  FROM SF_ACT_INFO H, SF_USER_AUTHORITY SUA \n" +
                       " WHERE H.SFACT_SIGN_USER " + SyBaseSQLUtil.isNullNoParam() + " \n" +
                       "   AND SUA.USER_ID = ?\n" +
                       "   AND SUA.GROUP_NAME = H.SFACT_TASK_GROUP\n" +
                       "   AND SUA.ROLE_NAME = H.SFACT_TASK_ROLE";
        ArrayList al = new ArrayList();
        al.add(sfUser.getUserId() );
        SQLModel sm = new SQLModel();
        sm.setArgs(al);
        sm.setSqlStr(sql);
        return sm;
    }
    public SQLModel getPendingNum() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = " SELECT COUNT(*) PENDING_NUMBER\n" +
                        " FROM ETS_WORKORDER EW,\n" +
                             " SF_USER_V SUV,\n" +
                             " ETS_WORKORDER_BATCH EWB,\n" +
                             " ETS_OBJECT EO\n" +
                        " WHERE EW.WORKORDER_FLAG = '" + DictConstant.WORKORDER_STATUS_UPLOADED + "'\n" +
                          " AND EW.WORKORDER_BATCH = EWB.WORKORDER_BATCH\n" +
                          " AND EW.IMPLEMENT_BY = SUV.USER_ID\n" +
                          " AND EW.WORKORDER_OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                          " AND EW.ORGANIZATION_ID = EO.ORGANIZATION_ID \n" +
                          " AND EW.ORGANIZATION_ID = ? \n" +

                          " AND (\n" +
                          "        EW.CHECKOVER_BY=?\n" +
                          "        OR (EW.CHECKOVER_BY " + SyBaseSQLUtil.isNullNoParam() + " \n" +
                          "             AND EXISTS (SELECT 'A'\n" +
                          "                    FROM SF_USER_RIGHT SUR,SF_ROLE SR\n" +
                          "                   WHERE SUR.USER_ID = ?\n" +
                          "                     AND EW.GROUP_ID = SUR.GROUP_ID" +
                          "                     AND SUR.ROLE_ID=SR.ROLE_ID\n" +
                          "                     AND SR.ROLE_NAME ='工单归档人')\n" +
                          "       ))";
                /*
                          " AND (EW.CHECKOVER_BY = ? OR EW.CHECKOVER_BY " + SyBaseSQLUtil.isNull() + " )\n" +
                          " AND (EXISTS (SELECT 'A' FROM SF_USER_RIGHT SUR\n" +
                               " WHERE SUR.USER_ID = ?\n" +
                                 " AND EW.GROUP_ID = SUR.GROUP_ID))";*/
        sqlArgs.add(sfUser.getOrganizationId());
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(sfUser.getUserId());
        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }

    public SQLModel getFaAssetsNum() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "Select Count(*) ASSETS_NUMBER\n" +
                " FROM\n" +
                " AMS_ASSETS_TRANS_HEADER AATH,\n" +
                " AMS_MIS_EMPLOYEE        AMEO,\n" +
                " AMS_MIS_DEPT            AMDO,\n" +
                " ETS_OBJECT              EOO,\n" +
                " AMS_ASSETS_TRANS_LINE   AATL,\n" +
                " AMS_MIS_EMPLOYEE        AMEN,\n" +
                " AMS_MIS_DEPT            AMDN,\n" +
                " ETS_OBJECT              EON,\n" +
                " ETS_ITEM_INFO           EII,\n";
                if ("Y".equalsIgnoreCase(sfUser.getIsTd())) {
                	sqlStr=sqlStr+
                	" ETS_ITEM_MATCH_TD          EIM,\n" +
                    " ETS_FA_ASSETS_TD           EFA\n";
                } else {
                	sqlStr=sqlStr+
                	" ETS_ITEM_MATCH          EIM,\n" +
                	" ETS_FA_ASSETS           EFA\n";
                }
                sqlStr=sqlStr+
                " WHERE\n" +
                " EFA.ASSET_ID = EIM.ASSET_ID\n" +
                " AND EIM.SYSTEMID = EII.SYSTEMID\n" +
                " AND EII.BARCODE = AATL.BARCODE\n" +
                " AND AATL.OLD_LOCATION = EOO.WORKORDER_OBJECT_NO\n" +
                " AND AATL.OLD_RESPONSIBILITY_USER *= AMEO.EMPLOYEE_ID\n" +
                " AND AATL.OLD_RESPONSIBILITY_DEPT *= AMDO.DEPT_CODE\n" +
                " AND AATL.ASSIGNED_TO_LOCATION *= EON.WORKORDER_OBJECT_NO\n" +
                " AND AATL.RESPONSIBILITY_USER *= AMEN.EMPLOYEE_ID\n" +
                " AND AATL.RESPONSIBILITY_DEPT *= AMDN.DEPT_CODE\n" +
                " AND AATL.TRANS_ID = AATH.TRANS_ID\n" +
                " AND ((AATL.LINE_STATUS = 'APPROVED' AND AATH.TRANSFER_TYPE = 'INN_DEPT') OR (AATL.LINE_STATUS = 'ASSIGNED' AND AATH.TRANSFER_TYPE <> 'INN_DEPT'))\n" +
                " AND AATL.RESPONSIBILITY_USER = ?\n" +
                " AND AATL.CONFIRM_DATE " + SyBaseSQLUtil.isNullNoParam() + " \n" +
                " AND EXISTS(\n" +
                " SELECT\n" +
                " Null\n" +
                " FROM\n" +
                " AMS_ASSETS_RESERVED AAR\n" +
                " WHERE\n" +
                " AAR.TRANS_ID = AATL.TRANS_ID\n" +
                " AND AAR.BARCODE = AATL.BARCODE)";
        sqlArgs.add(sfUser.getEmployeeId());
        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }

    public SQLModel getReceiveNum() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            String sqlStr = "SELECT count(*) RECEIVE_NUMBER"
                            + " FROM"
                            + " AMS_ASSETS_TRANS_HEADER AATH,"
                            + " ETS_OU_CITY_MAP    EOCM,"
                            + " AMS_MIS_DEPT       AMD,"
                            + " SF_USER            SU"
                            + " WHERE"
                            +
                            " AATH.FROM_ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
                            + " AND AATH.FROM_DEPT *= AMD.DEPT_CODE"
                            //+ " AND AATH.FROM_ORGANIZATION_ID = SU.ORGANIZATION_ID"
                            + " AND AATH.CREATED_BY = SU.USER_ID"
                            + " AND AATH.TRANS_TYPE = 'ASS-RED'"
                            + " AND AATH.TRANS_STATUS = 'APPROVED'"
                            + " AND AATH.TRANSFER_TYPE <> ?";

//            sqlArgs.add(AssetsDictConstant.TRANS_INN_DEPT);
            if (!sfUser.isComAssetsManager()) {
                DTOSet depts = sfUser.getPriviDeptCodes();
                String deptCodes = "";
                if (depts != null && !depts.isEmpty()) {
                    List deptList = depts.toList("deptCode");
                    deptCodes = StrUtil.list2String(deptList);
                }
                if (deptCodes.equals("")) {
                    deptCodes = "''";
                }
                sqlStr = sqlStr
                         + " AND EXISTS ("
                         + " SELECT"
                         + " NULL"
                         + " FROM"
                         + " AMS_ASSETS_TRANS_LINE AATL"
                         + " WHERE"
                         + " AATL.TRANS_ID = AATH.TRANS_ID"
                         + " AND AATL.RESPONSIBILITY_DEPT IN (" + deptCodes +
                         "))";
            } else {
                sqlStr = sqlStr + " AND AATH.TO_ORGANIZATION_ID = ?";
                sqlArgs.add(sfUser.getOrganizationId());
            }
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (DTOException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
        }
        return sqlModel;
    }

    public SQLModel deleteData() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM"
                + " ETS_FAVORITES"
                + " WHERE"
                + " USER_ID = ?";

        sqlArgs.add(sfUser.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}