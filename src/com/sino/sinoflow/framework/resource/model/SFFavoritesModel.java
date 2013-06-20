package com.sino.sinoflow.framework.resource.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.sinoflow.framework.resource.dto.SFFavoritesDTO;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;


/**
 * <p>Title: EtsFavoritesModel</p>
 * <p>Description:程序自动生成SQL构造器“EtsFavoritesModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author yuyao
 * @version 1.0
 */


public class SFFavoritesModel extends BaseSQLProducer {

    private SfUserBaseDTO sfUser = null;

    /**
     * 功能：用户个人收藏夹(AMS) SF_FAVORITES 数据库SQL构造层构造函数
     * @param userAccount  SfUserBaseDTO 代表本系统的最终操作用户对象
     * @param dtoParameter EtsFavoritesDTO 本次操作的数据
     */
    public SFFavoritesModel(SfUserBaseDTO userAccount, SFFavoritesDTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
    }

    /**
     * 功能：框架自动生成用户个人收藏夹(AMS) SF_FAVORITES数据插入SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据插入用SQLModel
     */
    public SQLModel getDataCreateModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        SFFavoritesDTO etsFavorites = (SFFavoritesDTO) dtoParameter;
        String sqlStr = "INSERT INTO "
                + " SF_FAVORITES("
                + " SORT_NO,"
                + " USER_ID,"
                + " SF_RES_ID,"
                + " CREATION_DATE,"
                + " CREATED_BY,"
                + " LAST_UPDATE_DATE,"
                + " LAST_UPDATE_BY"
                + ") VALUES ("
                + " ?, ?, ?, ?, ?, ?, ?)";

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
     * 功能：框架自动生成用户个人收藏夹(AMS) SF_FAVORITES数据更新SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据更新用SQLModel
     */
    public SQLModel getDataUpdateModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        SFFavoritesDTO etsFavorites = (SFFavoritesDTO) dtoParameter;
        String sqlStr = "UPDATE SF_FAVORITES"
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
     * 功能：框架自动生成用户个人收藏夹(AMS) SF_FAVORITES数据删除SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据删除用SQLModel
     */
    public SQLModel getDataDeleteModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        SFFavoritesDTO etsFavorites = (SFFavoritesDTO) dtoParameter;
        String sqlStr = "DELETE FROM"
                + " SF_FAVORITES"
                + " WHERE"
                + " SYSTEMID = ?";
        sqlArgs.add(etsFavorites.getSystemid());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成用户个人收藏夹(AMS) SF_FAVORITES数据详细信息查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        SFFavoritesDTO etsFavorites = (SFFavoritesDTO) dtoParameter;
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
                + " SF_FAVORITES"
                + " WHERE"
                + " SYSTEMID = ?";
        sqlArgs.add(etsFavorites.getSystemid());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成用户个人收藏夹(AMS) SF_FAVORITES多条数据信息查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回多条数据信息查询用SQLModel
     */
    public SQLModel getDataMuxModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        SFFavoritesDTO etsFavorites = (SFFavoritesDTO) dtoParameter;
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
                + " SF_FAVORITES"
                + " WHERE"
                + "(? <= 0 OR SYSTEMID = ?)"
                + "(? <= 0 OR SORT_NO = ?)"
                + "(? <= 0 OR USER_ID = ?)"
                + "(? <= 0 OR SF_RES_ID = ?)"
                + "(? = '' OR CREATION_DATE LIKE ?)"
                + "(? <= 0 OR CREATED_BY = ?)"
                + "(? = '' OR LAST_UPDATE_DATE LIKE ?)"
                + "(? <= 0 OR LAST_UPDATE_BY = ?)";
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
     * 框架自动生成数据用户个人收藏夹(AMS) SF_FAVORITES详细信息查询SQLModel，请根据实际需要修改。
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
                + " SF_FAVORITES"
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
        SFFavoritesDTO etsFavorites = (SFFavoritesDTO) dtoParameter;
        if (foreignKey.equals("sfResId")) {
            sqlModel = getDataBySfResIdModel(etsFavorites.getSfResId());
        }
        return sqlModel;
    }

    /**
     * 功能：根据外键关联字段 sfResId 构造数据删除SQL。
     * 框架自动生成数据用户个人收藏夹(AMS) SF_FAVORITES 数据删除SQLModel，请根据实际需要修改。
     * @param sfResId String
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    private SQLModel getDeleteBySfResIdModel(int sfResId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM"
                + " SF_FAVORITES"
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
        SFFavoritesDTO etsFavorites = (SFFavoritesDTO) dtoParameter;
        if (foreignKey.equals("sfResId")) {
            sqlModel = getDeleteBySfResIdModel(etsFavorites.getSfResId());
        }
        return sqlModel;
    }

    /**
     * 功能：框架自动生成用户个人收藏夹(AMS) SF_FAVORITES页面翻页查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getPageQueryModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        SFFavoritesDTO etsFavorites = (SFFavoritesDTO) dtoParameter;
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
                + " SF_FAVORITES"
                + " WHERE"
                + " (? <= 0 OR SYSTEMID = ?)"
                + "AND (? <= 0 OR SORT_NO = ?)"
                + "AND (? <= 0 OR USER_ID = ?)"
                + "AND (? <= 0 OR SF_RES_ID = ?)"
                + "AND (? = '' OR CREATION_DATE LIKE ?)"
                + "AND (? <= 0 OR CREATED_BY = ?)"
                + "AND (? = '' OR LAST_UPDATE_DATE LIKE ?)"
                + "AND (? <= 0 OR LAST_UPDATE_BY = ?)";
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
        String sqlStr = "SELECT SRD.RES_ID, SRD.RES_NAME" +
                "  FROM SF_RES_DEFINE SRD" +
                " WHERE SRD.RES_PAR_ID = 0" +
                "   AND EXISTS (SELECT NULL" +
                "          FROM SF_RES_PRIVS SRP, SF_USER_AUTHORITY SUA" +
                "         WHERE SRP.ROLE_NAME = SUA.ROLE_NAME" +
                "           AND SRP.SYSTEM_ID = SRD.SYSTEM_ID" +
                "           AND SUA.USER_ID = ?)" +
                " ORDER BY SRD.RES_ID, SRD.RES_PAR_ID";
        list.add(sfUser.getUserId());
        sqlModel.setArgs(list);
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }

    public SQLModel getSmallMenu(String resParId) {
        SQLModel sqlModel = new SQLModel();
        ArrayList list = new ArrayList();
        String sqlStr = "SELECT SRD.RES_NAME,SRD.RES_ID,SRD.SYSTEM_ID" +
                "  FROM SF_RES_DEFINE SRD" +
                " WHERE  " + SyBaseSQLUtil.isNotNull("SRD.RES_PAR_ID") + " " +
                "   AND  " + SyBaseSQLUtil.isNotNull("SRD.RES_URL") + " " +
                "   AND EXISTS (SELECT NULL" +
                "          FROM SF_RES_PRIVS SRP, SF_USER_AUTHORITY SUA" +
                "         WHERE SRP.ROLE_NAME = SUA.ROLE_NAME" +
                "           AND SRP.SYSTEM_ID = SRD.SYSTEM_ID" +
                "           AND SUA.USER_ID = ?)" +
                "   AND SRD.RES_PAR_ID LIKE ? + '.%'" +
                " ORDER BY SRD.RES_ID, SRD.RES_PAR_ID";
        list.add(sfUser.getUserId());
        list.add(resParId);
        sqlModel.setArgs(list);
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }

    public SQLModel insertData(String menus,String i) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO SF_FAVORITES(" +
                "   SORT_NO, " +
                "   USER_ID," +
                "   SF_RES_ID," +
                "   CREATION_DATE," +
                "   CREATED_BY )" +
                " VALUES" +
                "  (?, ?, ?, GETDATE(), ?)";
        sqlArgs.add(i);
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(menus);
        sqlArgs.add(sfUser.getUserId());
        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }

    public SQLModel updateData() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE SF_FAVORITES"
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
        String sqlStr = "SELECT SRD.SYSTEM_ID,SRD.RES_NAME,SRD.IS_POPUP,SRD.POPSCRIPT,SRD.RES_URL" +
                "  FROM SF_FAVORITES SF, SF_RES_DEFINE SRD" +
                " WHERE SRD.SYSTEM_ID = SF.SF_RES_ID" +
                "       AND SF.USER_ID=?" +
                "       ORDER BY SF.SORT_NO";
        sqlArgs.add(sfUser.getUserId());
        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }

    public SQLModel deleteData() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM"
                + " SF_FAVORITES"
                + " WHERE"
                + " USER_ID = ?";

        sqlArgs.add(sfUser.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}