package com.sino.ams.match.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.match.dto.EtsItemMatchDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: EtsItemMatchModel</p>
 * <p>Description:程序自动生成SQL构造器“EtsItemMatchModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author V-jiachuanchuan
 * @version 1.0
 */


public class EtsItemMatchModel extends BaseSQLProducer {

    private SfUserDTO sfUser = null;

    /**
     * 功能：资产匹配-匹配数据存储(EAM) ETS_ITEM_MATCH 数据库SQL构造层构造函数
     *
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter EtsItemMatchDTO 本次操作的数据
     */
    public EtsItemMatchModel(SfUserDTO userAccount, EtsItemMatchDTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
    }

    /**
     * 功能：框架自动生成资产匹配-匹配数据存储(EAM) ETS_ITEM_MATCH数据插入SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据插入用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getDataCreateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsItemMatchDTO etsItemMatch = (EtsItemMatchDTO) dtoParameter;
        String sqlStr = "INSERT INTO "
                + " ETS_ITEM_MATCH("
                + " SYSTEMID,"
                + " ITEM_ATTR,"
                + " ASSET_ID,"
                + " QUANTITY,"
                + " MATCH_DATE,"
                + " CREATION_DATE,"
                + " CREATED_BY"
                + ") VALUES ("
                + " ?, 0, ?, '1', GETDATE(), GETDATE(), ?)";

        sqlArgs.add(etsItemMatch.getSystemid());
        sqlArgs.add(etsItemMatch.getAssetId());
        sqlArgs.add(sfUser.getUserId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    /**
     * 功能：框架自动生成资产匹配-匹配数据存储(EAM) ETS_ITEM_MATCH数据更新SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据更新用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getDataUpdateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        EtsItemMatchDTO dto = (EtsItemMatchDTO) dtoParameter;
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE ETS_ITEM_INFO\n" +
                "SET \n" +
                "FINANCE_PROP = ?, \n" +
                "LAST_UPDATE_DATE = GETDATE(), \n" +
                "LAST_UPDATE_BY = ?" +
                " WHERE SYSTEMID = ?";
        sqlArgs.add(DictConstant.FIN_PROP_ASSETS);
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getSystemid());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

     public SQLModel getHasBeenModel() {
         EtsItemMatchDTO dto = (EtsItemMatchDTO) dtoParameter;
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT 1 " +
                "FROM ETS_ITEM_MATCH_REC ER " +
                "WHERE ER.SYSTEM_ID = ?\n";
        sqlArgs.add(dto.getSystemid());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    public SQLModel insertIntoEIMRModel() {
        EtsItemMatchDTO dto = (EtsItemMatchDTO) dtoParameter;
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO \n" +
                "  ETS_ITEM_MATCH_REC\n" +
                "(       \n" +
                "ID,          \n" +
                "MATCH_DATE       ,\n" +
                "MATCH_USER_ID    ,\n" +
                "SYSTEM_ID        ,\n" +
                "MATCH_TYPE       ,\n" +
                "OLD_FINANCE_PROP ,\n" +
                "NEW_FINANCE_PROP  ) " +
                "VALUES(\n" +
                " NEWID() ,GETDATE(),?,?,'6','PRJ_MTL','ASSETS')";
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(dto.getSystemid());
//        sqlArgs.add(dto.getOldFinanceProp());
//        sqlArgs.add(dto.getNewFinanceProp());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel updateEIMRModel() {
        EtsItemMatchDTO dto = (EtsItemMatchDTO) dtoParameter;
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE   " +
                "ETS_ITEM_MATCH_REC \n" +
                "SET \n" +
                "MATCH_DATE = GETDATE(),\n" +
                "MATCH_USER_ID = ?,\n" +
                "MATCH_TYPE = '6',\n" +
                "OLD_FINANCE_PROP = 'PRJ_MTL',\n" +
                "NEW_FINANCE_PROP = 'ASSETS'\n" +
                "WHERE SYSTEM_ID = ?";
        sqlArgs.add(sfUser.getUserId());
//        sqlArgs.add(dto.getOldFinanceProp());
//        sqlArgs.add(dto.getNewFinanceProp());
        sqlArgs.add(dto.getSystemid());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel insertIntoEIMRLModel() {
        EtsItemMatchDTO dto = (EtsItemMatchDTO) dtoParameter;
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO \n" +
                "  ETS_ITEM_MATCH_REC_LOG \n" +
                "(ID              ,\n" +
                "MATCH_DATE       ,\n" +
                "MATCH_USER_ID    ,\n" +
                "SYSTEM_ID        ,\n" +
                "MATCH_TYPE       ,\n" +
                "OLD_FINANCE_PROP ,\n" +
                "NEW_FINANCE_PROP  ) " +
                "VALUES(\n" +
                " NEWID() ,GETDATE(),?,?,'6','PRJ_MTL','ASSETS')";
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(dto.getSystemid());
//        sqlArgs.add(dto.getOldFinanceProp());
//        sqlArgs.add(dto.getNewFinanceProp());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel updateEIMCModel() {
        EtsItemMatchDTO dto = (EtsItemMatchDTO) dtoParameter;
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE ETS_ITEM_MATCH_COMPLET\n" +
                "   SET CURRENT_UNITS = CURRENT_UNITS + 1\n" +
                " WHERE ASSET_ID = ?";

        sqlArgs.add(dto.getAssetId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成资产匹配-匹配数据存储(EAM) ETS_ITEM_MATCH数据删除SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据删除用SQLModel
     */
    public SQLModel getDataDeleteModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM"
                + " ETS_ITEM_MATCH";
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成资产匹配-匹配数据存储(EAM) ETS_ITEM_MATCH数据详细信息查询SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT "
                + " SYSTEMID,"
                + " ITEM_ATTR,"
                + " ASSET_ID,"
                + " QUANTITY,"
                + " BATCHID,"
                + " MATCH_DATE,"
                + " FLAG,"
                + " USER_ID,"
                + " CREATION_DATE,"
                + " CREATED_BY,"
                + " LAST_UPDATE_DATE,"
                + " LAST_UPDATE_BY"
                + " WHERE"
                + " ROWNUM = 1";

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成资产匹配-匹配数据存储(EAM) ETS_ITEM_MATCH多条数据信息查询SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回多条数据信息查询用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getMuxDataModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            EtsItemMatchDTO etsItemMatch = (EtsItemMatchDTO) dtoParameter;
            String sqlStr = "SELECT "
                    + " SYSTEMID,"
                    + " ITEM_ATTR,"
                    + " ASSET_ID,"
                    + " QUANTITY,"
                    + " BATCHID,"
                    + " MATCH_DATE,"
                    + " FLAG,"
                    + " USER_ID,"
                    + " CREATION_DATE,"
                    + " CREATED_BY,"
                    + " LAST_UPDATE_DATE,"
                    + " LAST_UPDATE_BY"
                    + " FROM"
                    + " ETS_ITEM_MATCH"
                    + " WHERE"
                    + " ( " + SyBaseSQLUtil.isNull() + "  OR SYSTEMID LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR ITEM_ATTR LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR ASSET_ID LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR QUANTITY LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR BATCHID LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR MATCH_DATE LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR FLAG LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR USER_ID LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR CREATION_DATE LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR CREATED_BY LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR LAST_UPDATE_DATE LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR LAST_UPDATE_BY LIKE ?)";
            sqlArgs.add(etsItemMatch.getSystemid());
            sqlArgs.add(etsItemMatch.getSystemid());
            sqlArgs.add(etsItemMatch.getItemAttr());
            sqlArgs.add(etsItemMatch.getItemAttr());
            sqlArgs.add(etsItemMatch.getAssetId());
            sqlArgs.add(etsItemMatch.getAssetId());
            sqlArgs.add(etsItemMatch.getQuantity());
            sqlArgs.add(etsItemMatch.getQuantity());
            sqlArgs.add(etsItemMatch.getBatchid());
            sqlArgs.add(etsItemMatch.getBatchid());
            sqlArgs.add(etsItemMatch.getMatchDate());
            sqlArgs.add(etsItemMatch.getMatchDate());
            sqlArgs.add(etsItemMatch.getFlag());
            sqlArgs.add(etsItemMatch.getFlag());
            sqlArgs.add(etsItemMatch.getUserId());
            sqlArgs.add(etsItemMatch.getUserId());
            sqlArgs.add(etsItemMatch.getCreationDate());
            sqlArgs.add(etsItemMatch.getCreationDate());
            sqlArgs.add(etsItemMatch.getCreatedBy());
            sqlArgs.add(etsItemMatch.getCreatedBy());
            sqlArgs.add(etsItemMatch.getLastUpdateDate());
            sqlArgs.add(etsItemMatch.getLastUpdateDate());
            sqlArgs.add(etsItemMatch.getLastUpdateBy());
            sqlArgs.add(etsItemMatch.getLastUpdateBy());

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
        }
        return sqlModel;
    }

    /**
     * 功能：框架自动生成资产匹配-匹配数据存储(EAM) ETS_ITEM_MATCH页面翻页查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回页面翻页查询SQLModel
   */
    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsItemMatchDTO etsItemMatch = (EtsItemMatchDTO) dtoParameter;
        String sqlStr = "SELECT EII.BARCODE,\n" +
                "       ESI.ITEM_NAME,\n" +
                "       EII.SYSTEMID," +
                "       ESI.ITEM_SPEC,\n" +
                "       EO.WORKORDER_OBJECT_LOCATION,\n" +
                "       EPPA.SEGMENT1,\n" +
                "       EII.ITEM_QTY\n" +
                "  FROM ETS_ITEM_INFO      EII,\n" +
                "       ETS_SYSTEM_ITEM    ESI,\n" +
                "       ETS_OBJECT         EO,\n" +
                "       AMS_OBJECT_ADDRESS AOA,\n" +
                "       ETS_PA_PROJECTS_ALL EPPA\n" +
                " WHERE EII.ITEM_CODE = ESI.ITEM_CODE\n" +
                "   AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                "   AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                "   AND EII.PROJECTID *= EPPA.PROJECT_ID\n" +
                "   AND (EO.OBJECT_CATEGORY < = 70 OR EO.OBJECT_CATEGORY = 80)" +   //备件仓库不参加匹配             
                "   AND NOT EXISTS (SELECT 1 FROM ETS_NO_MATCH ENM WHERE ENM.SYSTEMID = EII.SYSTEMID)\n" +  //在ETS_NO_MATCH中有的资产不参加匹配
                "   AND EII.FINANCE_PROP='PRJ_MTL'\n" +
                "   AND EII.ORGANIZATION_ID = ? \n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.WORKORDER_OBJECT_LOCATION = ?) \n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EPPA.SEGMENT1 = ?) \n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_NAME LIKE ?) \n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?) ";

                sqlArgs.add(sfUser.getOrganizationId());
                sqlArgs.add(etsItemMatch.getWorkorderObjectLocation());
                sqlArgs.add(etsItemMatch.getWorkorderObjectLocation());
                sqlArgs.add(etsItemMatch.getSegment1());
                sqlArgs.add(etsItemMatch.getSegment1());
                sqlArgs.add(etsItemMatch.getItemName());
                sqlArgs.add(etsItemMatch.getItemName());
                sqlArgs.add(etsItemMatch.getItemSpec());
                sqlArgs.add(etsItemMatch.getItemSpec());
                sqlModel.setSqlStr(sqlStr);
                sqlModel.setArgs(sqlArgs);
                return sqlModel;
	}

}
