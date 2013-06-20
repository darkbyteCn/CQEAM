package com.sino.ams.match.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.match.dto.HideMisAssetDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.ArrUtil;

/**
 * Created by IntelliJ IDEA.
 * User: Zyun
 * Date: 2007-12-5
 * Time: 10:54:46
 * To change this template use File | Settings | File Templates.
 */
public class HideMisAssetModel extends AMSSQLProducer {
      private SfUserDTO sfUser = null;

    /**
     * 功能：资产匹配-匹配数据存储(EAM) ETS_ITEM_MATCH 数据库SQL构造层构造函数
     *
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter EtsItemMatchDTO 本次操作的数据
     */
    public HideMisAssetModel(SfUserDTO userAccount, HideMisAssetDTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
    }

    /**
     * 功能：框架自动生成资产匹配-匹配数据存储(EAM) ETS_ITEM_MATCH数据插入SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据插入用SQLModel
     * @throws com.sino.base.exception.SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getDataCreateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        HideMisAssetDTO etsItemMatch = (HideMisAssetDTO) dtoParameter;
        String sqlStr = "INSERT INTO ETS_ITEM_MATCH_ASSIST_MIS(ASSET_ID,REMARK) VALUES(?,?)";

        sqlArgs.add(etsItemMatch.getAssetId());
        sqlArgs.add(etsItemMatch.getReMark());
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
        HideMisAssetDTO dto = (HideMisAssetDTO) dtoParameter;
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

     public SQLModel getHasBeenModel(String[] assetIds) {
         HideMisAssetDTO dto = (HideMisAssetDTO) dtoParameter;
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();

         String idStr = ArrUtil.arrToStr(assetIds,", ");
        String sqlStr = "SELECT 1 FROM ETS_ITEM_MATCH_ASSIST_MIS EIMAM WHERE EIMAM.ASSET_ID IN(" + idStr + ")";
//        sqlArgs.add(dto.getSystemid());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    public SQLModel insertIntoEIMRModel() {
        HideMisAssetDTO dto = (HideMisAssetDTO) dtoParameter;
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
                " NEWID() ,GETDATE(),?,?,'8','UNKNOW','ASSETS')";
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(dto.getSystemid());
//        sqlArgs.add(dto.getOldFinanceProp());
//        sqlArgs.add(dto.getNewFinanceProp());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel updateEIMRModel() {
        HideMisAssetDTO dto = (HideMisAssetDTO) dtoParameter;
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE   " +
                "ETS_ITEM_MATCH_REC \n" +
                "SET \n" +
                "MATCH_DATE = GETDATE(),\n" +
                "MATCH_USER_ID = ?,\n" +
                "MATCH_TYPE = '8',\n" +
                "OLD_FINANCE_PROP = 'UNKNOW',\n" +
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
        HideMisAssetDTO dto = (HideMisAssetDTO) dtoParameter;
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
                " NEWID() ,GETDATE(),?,?,'8','UNKNOW','ASSETS')";
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(dto.getSystemid());
//        sqlArgs.add(dto.getOldFinanceProp());
//        sqlArgs.add(dto.getNewFinanceProp());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel updateEIMCModel() {
        HideMisAssetDTO dto = (HideMisAssetDTO) dtoParameter;
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE ETS_ITEM_MATCH_COMPLET\n" +
                "   SET CURRENT_UNITS = CURRENT_UNITS + 1\n" +
                " WHERE ASSET_ID = ?";

//        sqlArgs.add(dto.getAssetId());
        sqlArgs.add(dto.getSystemid());
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
            HideMisAssetDTO etsItemMatch = (HideMisAssetDTO) dtoParameter;
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
        String code = sfUser.getCompanyCode();
        HideMisAssetDTO etsItemMatch = (HideMisAssetDTO) dtoParameter;

        String sqlStr = /*"SELECT *\n" +
                "  FROM (SELECT EFA.ASSET_ID,\n" +
                "               EFA.TAG_NUMBER,\n" +
                "               EFA.ASSETS_DESCRIPTION,\n" +
                "               EFA.MODEL_NUMBER,\n" +
                "               EFA.COST,\n" +
                "               EFA.UNIT_OF_MEASURE,\n" +
                "               EFA.FA_CATEGORY1,\n" +
                "               EFA.FA_CATEGORY2,\n" +
                "               EFA.ASSETS_LOCATION,\n" +
                "               EFA.CURRENT_UNITS,\n" +
                "               EFA.ASSETS_LOCATION MISLOCATION,\n" +
                "               EFA.PROJECT_NAME,\n" +
                "               ISNULL(EFA.CURRENT_UNITS, 0) -\n" +
                "               dbo.NVL((SELECT COUNT(*)\n" +
                "                     FROM ETS_ITEM_MATCH\n" +
                "                    WHERE ASSET_ID = EFA.ASSET_ID),\n" +
                "                   0) UNMAPPED\n" +
                "          FROM ETS_FA_ASSETS EFA\n" +
                "         WHERE SUBSTRING(EFA.BOOK_TYPE_CODE, -4, 4) = "+ code + 
                "           AND NOT EXISTS (SELECT 1\n" +
                "                  FROM ETS_ITEM_MATCH_ASSIST_MIS\n" +
                "                 WHERE ASSET_ID = EFA.ASSET_ID) \n" +        //过滤屏蔽
                "           AND EFA.IS_RETIREMENTS = 0 \n" +              // 非报废
                "         ORDER BY EFA.ASSETS_DESCRIPTION,\n" +
                "                  EFA.MODEL_NUMBER,\n" +
                "                  EFA.ASSETS_LOCATION)\n" +
                " WHERE UNMAPPED > 0"  ;*/
                "SELECT EFA.ASSET_ID,\n" +
                        "       EFA.TAG_NUMBER,\n" +
                        "       EFA.ASSETS_DESCRIPTION,\n" +
                        "       EFA.MODEL_NUMBER,\n" +
                        "       EFA.COST,\n" +
                        "       EFA.UNIT_OF_MEASURE,\n" +
                        "       EFA.FA_CATEGORY1,\n" +
                        "       EFA.FA_CATEGORY2,\n" +
                        "       EFA.ASSETS_LOCATION,\n" +
                        "       EFA.CURRENT_UNITS,\n" +
                        "       EFA.ASSETS_LOCATION MISLOCATION,\n" +
                        "       EFA.PROJECT_NAME,\n" +
                        "       ISNULL(EFA.CURRENT_UNITS, 0) - dbo.NVL((SELECT COUNT(*)\n" +
                        "                                         FROM ETS_ITEM_MATCH\n" +
                        "                                        WHERE ASSET_ID = EFA.ASSET_ID),\n" +
                        "                                       0) UNMAPPED\n" +
                        "  FROM ETS_FA_ASSETS EFA\n" +
                        " WHERE SUBSTRING(EFA.BOOK_TYPE_CODE, -4, 4) = 2521\n" +
                        "   AND NOT EXISTS (SELECT 1\n" +
                        "          FROM ETS_ITEM_MATCH_ASSIST_MIS\n" +
                        "         WHERE ASSET_ID = EFA.ASSET_ID)\n" +
                        "   AND (EFA.IS_RETIREMENTS = 0  OR EFA.IS_RETIREMENTS = 2)" +

                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR  EFA.ASSETS_DESCRIPTION LIKE ?)" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.MODEL_NUMBER LIKE ?)"+
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.ASSETS_LOCATION LIKE ?)"+
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.ASSETS_LOCATION LIKE ?)";

//                sqlArgs.add(sfUser.getOrganizationId());

                sqlArgs.add(etsItemMatch.getAssetsDescription());
                sqlArgs.add(etsItemMatch.getAssetsDescription());
                sqlArgs.add(etsItemMatch.getAssetsDescription());
                sqlArgs.add(etsItemMatch.getAssetsDescription());
                sqlArgs.add(etsItemMatch.getAssetsLocation());
                sqlArgs.add(etsItemMatch.getAssetsLocation());
                sqlArgs.add(etsItemMatch.getWorkorderObjectName());
                sqlArgs.add(etsItemMatch.getWorkorderObjectName());
            sqlModel.setSqlStr(sqlStr);
                sqlModel.setArgs(sqlArgs);

                return sqlModel;
	}
}
