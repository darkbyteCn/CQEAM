package com.sino.td.match.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.match.dto.EtsItemMatchRecDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.StrUtil;


/**
 * <p>Title: EtsItemMatchRecModel</p>
 * <p>Description:程序自动生成SQL构造器“EtsItemMatchRecModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author V-yuanshuai
 * @version 1.0
 */


public class EtsItemMatchTdRecModel extends AMSSQLProducer {


    /**
     * 功能：ETS_ITEM_MATCH_TD_REC 数据库SQL构造层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter EtsItemMatchRecDTO 本次操作的数据
     */
    public EtsItemMatchTdRecModel(SfUserDTO userAccount, EtsItemMatchRecDTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    /**
     * 功能：框架自动生成ETS_ITEM_MATCH_TD_REC数据插入SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据插入用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getDataCreateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            EtsItemMatchRecDTO etsItemMatchRec = (EtsItemMatchRecDTO) dtoParameter;
            String sqlStr = "INSERT INTO "
                    + " ETS_ITEM_MATCH_TD_REC("
                    + " ID,"
                    + " MATCH_DATE,"
                    + " MATCH_USER_ID,"
                    + " SYSTEM_ID,"
                    + " ASSET_ID,"
                    + " MATCH_TYPE,"
                    + " OLD_FINANCE_PROP,"
                    + " NEW_FINANCE_PROP"
                    + ") VALUES ("
                    + "  NEWID() , ?, ?, ?, ?, ?, ?, ?)";

            sqlArgs.add(etsItemMatchRec.getMatchDate());
            sqlArgs.add(etsItemMatchRec.getMatchUserId());
            sqlArgs.add(etsItemMatchRec.getSystemId());
            sqlArgs.add(etsItemMatchRec.getAssetId());
            sqlArgs.add(etsItemMatchRec.getMatchType());
            sqlArgs.add(etsItemMatchRec.getOldFinanceProp());
            sqlArgs.add(etsItemMatchRec.getNewFinanceProp());

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
        }
        return sqlModel;
    }

    /**
     * 功能：框架自动生成ETS_ITEM_MATCH_TD_REC数据更新SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据更新用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getDataUpdateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            EtsItemMatchRecDTO etsItemMatchRec = (EtsItemMatchRecDTO) dtoParameter;
            String sqlStr = "UPDATE ETS_ITEM_MATCH_TD_REC"
                    + " SET"
                    + " MATCH_DATE = ?,"
                    + " MATCH_USER_ID = ?,"
                    + " SYSTEM_ID = ?,"
                    + " ASSET_ID = ?,"
                    + " MATCH_TYPE = ?,"
                    + " OLD_FINANCE_PROP = ?,"
                    + " NEW_FINANCE_PROP = ?"
                    + " WHERE"
                    + " ID = ?";

            sqlArgs.add(etsItemMatchRec.getMatchDate());
            sqlArgs.add(etsItemMatchRec.getMatchUserId());
            sqlArgs.add(etsItemMatchRec.getSystemId());
            sqlArgs.add(etsItemMatchRec.getAssetId());
            sqlArgs.add(etsItemMatchRec.getMatchType());
            sqlArgs.add(etsItemMatchRec.getOldFinanceProp());
            sqlArgs.add(etsItemMatchRec.getNewFinanceProp());
            sqlArgs.add(etsItemMatchRec.getId());

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
        }
        return sqlModel;
    }

    /**
     * 功能：框架自动生成ETS_ITEM_MATCH_TD_REC数据删除SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据删除用SQLModel
     */
    public SQLModel getDataDeleteModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsItemMatchRecDTO etsItemMatchRec = (EtsItemMatchRecDTO) dtoParameter;
        String sqlStr = "DELETE FROM"
                + " ETS_ITEM_MATCH_TD_REC"
                + " WHERE"
                + " ID = ?";
        sqlArgs.add(etsItemMatchRec.getId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成ETS_ITEM_MATCH_TD_REC数据详细信息查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsItemMatchRecDTO etsItemMatchRec = (EtsItemMatchRecDTO) dtoParameter;
        String sqlStr = "SELECT "
                + " ID,"
                + " MATCH_DATE,"
                + " MATCH_USER_ID,"
                + " SYSTEM_ID,"
                + " ASSET_ID,"
                + " MATCH_TYPE,"
                + " OLD_FINANCE_PROP,"
                + " NEW_FINANCE_PROP"
                + " FROM"
                + " ETS_ITEM_MATCH_TD_REC"
                + " WHERE"
                + " ID = ?";
        sqlArgs.add(etsItemMatchRec.getId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成ETS_ITEM_MATCH_TD_REC多条数据信息查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回多条数据信息查询用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getMuxDataModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            EtsItemMatchRecDTO etsItemMatchRec = (EtsItemMatchRecDTO) dtoParameter;
            String sqlStr = "SELECT "
                    + " ID,"
                    + " MATCH_DATE,"
                    + " MATCH_USER_ID,"
                    + " SYSTEM_ID,"
                    + " ASSET_ID,"
                    + " MATCH_TYPE,"
                    + " OLD_FINANCE_PROP,"
                    + " NEW_FINANCE_PROP"
                    + " FROM"
                    + " ETS_ITEM_MATCH_TD_REC"
                    + " WHERE"
                    + " (" + SyBaseSQLUtil.isNull() + " OR ID LIKE ?)"
                    + " AND (" + SyBaseSQLUtil.isNull() + " OR MATCH_DATE LIKE ?)"
                    + " AND (" + SyBaseSQLUtil.isNull() + " OR MATCH_USER_ID LIKE ?)"
                    + " AND (" + SyBaseSQLUtil.isNull() + " OR SYSTEM_ID LIKE ?)"
                    + " AND (" + SyBaseSQLUtil.isNull() + " OR ASSET_ID LIKE ?)"
                    + " AND (" + SyBaseSQLUtil.isNull() + " OR MATCH_TYPE LIKE ?)"
                    + " AND (" + SyBaseSQLUtil.isNull() + " OR OLD_FINANCE_PROP LIKE ?)"
                    + " AND (" + SyBaseSQLUtil.isNull() + " OR NEW_FINANCE_PROP LIKE ?)";
            sqlArgs.add(etsItemMatchRec.getId());
            sqlArgs.add(etsItemMatchRec.getId());
            sqlArgs.add(etsItemMatchRec.getMatchDate());
            sqlArgs.add(etsItemMatchRec.getMatchDate());
            sqlArgs.add(etsItemMatchRec.getMatchUserId());
            sqlArgs.add(etsItemMatchRec.getMatchUserId());
            sqlArgs.add(etsItemMatchRec.getSystemId());
            sqlArgs.add(etsItemMatchRec.getSystemId());
            sqlArgs.add(etsItemMatchRec.getAssetId());
            sqlArgs.add(etsItemMatchRec.getAssetId());
            sqlArgs.add(etsItemMatchRec.getMatchType());
            sqlArgs.add(etsItemMatchRec.getMatchType());
            sqlArgs.add(etsItemMatchRec.getOldFinanceProp());
            sqlArgs.add(etsItemMatchRec.getOldFinanceProp());
            sqlArgs.add(etsItemMatchRec.getNewFinanceProp());
            sqlArgs.add(etsItemMatchRec.getNewFinanceProp());

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
        }
        return sqlModel;
    }

    /**
     * 功能：框架自动生成ETS_ITEM_MATCH_TD_REC页面翻页查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getPageQueryModel() {
        SQLModel sqlModel = new SQLModel();

        List sqlArgs = new ArrayList();
        EtsItemMatchRecDTO etsItemMatchRec = (EtsItemMatchRecDTO) dtoParameter;
        String matchMode = StrUtil.nullToString(etsItemMatchRec.getMatchType());
        String sqlStr = "";
        if (!matchMode.equals(WebAttrConstant.MATHC_MODE_CHANGED_ASSETS_RET)) {
            sqlStr = "SELECT EII.BARCODE,\n" +
                    "       EII.SYSTEMID SYSTEM_ID,\n" +
                    "       EII.FINANCE_PROP,\n" +
                    "       EII.ITEM_CODE,\n" +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       AMS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY," +
                    "       EO.WORKORDER_OBJECT_NAME,\n" +
                    "		EPPA.NAME\n"	+
                    "  FROM ETS_ITEM_INFO      EII,\n" +
                    "       ETS_SYSTEM_ITEM    ESI,\n" +
                    "       AMS_OBJECT_ADDRESS AOA,\n" +
                    "       ETS_OBJECT         EO,\n" +
                    "       ETS_PA_PROJECTS_ALL EPPA "	+
                    " WHERE EII.ITEM_CODE = ESI.ITEM_CODE\n" +
                    "   AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                    "	AND EII.PROJECTID *= EPPA.PROJECT_ID"	+
                    "   AND (EO.OBJECT_CATEGORY < = 70 OR EO.OBJECT_CATEGORY = 80)\n" +
                    "   AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                    "   AND EII.DISABLE_DATE  " + SyBaseSQLUtil.isNullNoParam() + " " +                    
                    "   AND EII.FINANCE_PROP = ?\n";
            
            if (matchMode.equals(WebAttrConstant.MATCH_MODE_SPARE)) { //备件确认
                sqlArgs.add(DictConstant.FIN_PROP_UNKNOW);
            } else if (matchMode.equals(WebAttrConstant.MATCH_MODE_SPARE_RET)) { //备件撤销
                sqlArgs.add(DictConstant.FIN_PROP_SPARE);
            } else if (matchMode.equals(WebAttrConstant.MATCH_MODE_PRJMTL)) { //工程物资确认
                sqlArgs.add(DictConstant.FIN_PROP_UNKNOW);
            } else if (matchMode.equals(WebAttrConstant.MATCH_MODE_PRJMTL_RET)) { //工程物资撤销
                sqlArgs.add(DictConstant.FIN_PROP_PRJ);
            } else if (matchMode.equals(WebAttrConstant.MATCH_MODE_OTHER)) { //设备屏蔽
                sqlArgs.add(DictConstant.FIN_PROP_UNKNOW);
            } else if (matchMode.equals(WebAttrConstant.MATCH_MODE_0THER_RET)) { //设备屏蔽撤销
                sqlArgs.add(DictConstant.FIN_PROP_OTHER);
                sqlStr = sqlStr + " AND EII.ATTRIBUTE1  " + SyBaseSQLUtil.isNullNoParam() + " \n";
            } else if (matchMode.equals(WebAttrConstant.MATCH_MODE_RENT)) { //租赁资产确认
                sqlArgs.add(DictConstant.FIN_PROP_UNKNOW);
                sqlStr += " AND EII.BARCODE LIKE'%ZL%'";
            } else if (matchMode.equals(WebAttrConstant.MATCH_MODE_RENT_RET)) { //租赁资产撤销
                sqlArgs.add(DictConstant.FIN_PROP_RENT);
            } else if (matchMode.equals(WebAttrConstant.MATCH_MODE_DG)) { //代管资产确认
                sqlArgs.add(DictConstant.FIN_PROP_UNKNOW);
                sqlStr += " AND EII.BARCODE LIKE'%DG%'";
            } else if (matchMode.equals(WebAttrConstant.MATCH_MODE_DG_RET)) { //代管资产撤销
                sqlArgs.add(DictConstant.FIN_PROP_DG);
            } else if (matchMode.equals(WebAttrConstant.MATCH_MODE_LC)) { //低值易耗资产确认
                sqlArgs.add(DictConstant.FIN_PROP_UNKNOW);
                sqlStr += " AND EII.BARCODE LIKE'%DH%'";
            } else if (matchMode.equals(WebAttrConstant.MATCH_MODE_LC_RET)) { //低值易耗资产撤销
                sqlArgs.add(DictConstant.FIN_PROP_DH);
            } else if (matchMode.equals(WebAttrConstant.MATCH_MODE_TD)) { //TD资产确认
                sqlArgs.add(DictConstant.FIN_PROP_UNKNOW);
                sqlStr += " AND EII.BARCODE LIKE'%TD%'";
            } else if (matchMode.equals(WebAttrConstant.MATCH_MODE_TD_RET)) { //TD资产撤销
                sqlArgs.add(DictConstant.FIN_PROP_TD);
            }
//            else if (matchMode.equals(WebAttrConstant.MATCH_MODE_CT)) { //村通资产确认(现无村通)
//                sqlArgs.add(DictConstant.FIN_PROP_UNKNOW);
//            }
        } else {
            String flag = etsItemMatchRec.getUnyokeFlag();
            sqlStr = "SELECT " +
                    " EII.BARCODE," +
                    " EII.ITEM_CODE, " +
                    " ESI.ITEM_NAME," +
                    " ESI.ITEM_SPEC, " +
                    " ER.SYSTEM_ID, \n" +
                    " EM.MATCH_DATE, " +
                    " EM.USER_ID," +
                    " EM.ASSET_ID," +
                    " EFA.MIS_TAG_NUMBER TAG_NUMBER,\n" +//读取MIS_TAG_NUMBER modified by Jenson 2008-12-29
                    " EFA.ASSETS_DESCRIPTION,\n" +
                    " EFA.MODEL_NUMBER,\n" +
                    " EFA.ASSETS_LOCATION_CODE,\n" +
                    " EO.WORKORDER_OBJECT_CODE,\n" +
                    " AMS_PUB_PKG.GET_FLEX_VALUE(ER.MATCH_TYPE,'MATCH_TYPE') MATCH_TYPE,\n " +
                    " EO.WORKORDER_OBJECT_NAME, " +
                    " AMS_PUB_PKG.GET_FLEX_VALUE(ER.OLD_FINANCE_PROP,'FINANCE_PROP') OLD_FINANCE_PROP, \n" +
                    " AMS_PUB_PKG.GET_FLEX_VALUE(ER.NEW_FINANCE_PROP,'FINANCE_PROP') NEW_FINANCE_PROP,\n" +
                    " AMS_PUB_PKG.GET_USER_NAME(ER.MATCH_USER_ID) MATCH_USER_NAME\n" +
                    " FROM  " +
                    "  ETS_ITEM_INFO      EII, " +
                    "  AMS_OBJECT_ADDRESS AOA, " +
                    "  ETS_OBJECT         EO,\n" +
                    "  ETS_SYSTEM_ITEM    ESI, " +
                    "  ETS_ITEM_MATCH_TD     EM, " +
                    "  ETS_FA_ASSETS_TD      EFA,\n  " +
                    "  ETS_ITEM_MATCH_TD_REC ER" +
                    " WHERE   " +
                    " EII.ADDRESS_ID = AOA.ADDRESS_ID " +
                    " AND EFA.ASSET_ID = EM.ASSET_ID" +
//                    " AND EFA.SEGMENT2 NOT LIKE '07-71%'" +//房屋不出现
//                    " AND EFA.SEGMENT2 NOT LIKE '80-03%'" +//土地不出现
                    " AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO " +
                    " AND  EII.SYSTEMID= ER.SYSTEM_ID \n" +
                    " AND EII.SYSTEMID=EM.SYSTEMID   " +
                    " AND EII.ITEM_CODE = ESI.ITEM_CODE " +
//                    " AND (EO.OBJECT_CATEGORY < = 70 OR EO.OBJECT_CATEGORY = 80)" + //备件仓库不参加匹配
                    " AND ER.NEW_FINANCE_PROP = 'TD_ASSETS'\n"

                    + " AND NOT EXISTS("//调拨过的资产不能撤销
                    + " SELECT"
                    + " NULL"
                    + " FROM"
                    + " TD_ASSETS_TRANS_LINE AATL,"
                    + " TD_ASSETS_TRANS_HEADER AATH"
                    + " WHERE"
                    + " EII.BARCODE = AATL.BARCODE"
                    + " AND AATL.TRANS_ID = AATH.TRANS_ID"
                    + " AND AATH.TRANS_STATUS <> 'CANCELED')"

                    + " AND NOT EXISTS("//已经同步过的资产不能撤销  --正在同步的（不包括同步完成或者同步错误）
                    + " SELECT"
                    + " NULL"
                    + " FROM"
                    + " ETS_MISFA_UPDATE_LOG EMUL"
                    + " WHERE"
                    + " EII.BARCODE = EMUL.BARCODE AND EMUL.TRANS_STATUS=0)";

            if (flag.equals("1")) { //资产匹配撤销
                sqlStr += " AND    ER.OLD_FINANCE_PROP = 'UNKNOW' \n";
            } else if (flag.equals("0")) { //转资匹配撤销
                sqlStr += " AND    ER.OLD_FINANCE_PROP ='PRJ_MTL' \n";
            }
        }
        sqlStr += " AND ( (" + SyBaseSQLUtil.isNull() + " OR EO.WORKORDER_OBJECT_NAME LIKE ?)\n" +
                " OR    ( " + SyBaseSQLUtil.isNull() + " OR EO.WORKORDER_OBJECT_CODE LIKE ?))\n" +
                " AND   (" + SyBaseSQLUtil.isNull() + " OR ESI.ITEM_NAME LIKE ?)\n" +
                " AND   (" + SyBaseSQLUtil.isNull() + " OR ESI.ITEM_SPEC LIKE ? )\n" +
                " AND   (" + SyBaseSQLUtil.isNull() + " OR EII.BARCODE LIKE ? )\n" ;

        if (matchMode.equals(WebAttrConstant.MATHC_MODE_CHANGED_ASSETS_RET)) {
            sqlStr += " AND    (" + SyBaseSQLUtil.isNull() + " OR EFA.TAG_NUMBER LIKE ? )\n" +
                    " AND    (" + SyBaseSQLUtil.isNull() + " OR ER.MATCH_USER_ID LIKE ? )\n";
        }
        sqlStr += " AND    EII.ORGANIZATION_ID = ? ";
        if (matchMode.equals(WebAttrConstant.MATHC_MODE_CHANGED_ASSETS_RET)) {
            sqlStr += " ORDER BY  EM.MATCH_DATE DESC, EO.WORKORDER_OBJECT_CODE, ESI.ITEM_NAME ";
        }
        sqlArgs.add(etsItemMatchRec.getWorkorderObjectName());
        sqlArgs.add(etsItemMatchRec.getWorkorderObjectName());
        sqlArgs.add(etsItemMatchRec.getWorkorderObjectName());
        sqlArgs.add(etsItemMatchRec.getWorkorderObjectName());
        sqlArgs.add(etsItemMatchRec.getItemName());
        sqlArgs.add(etsItemMatchRec.getItemName());
        sqlArgs.add(etsItemMatchRec.getItemSpec());
        sqlArgs.add(etsItemMatchRec.getItemSpec());
        sqlArgs.add(etsItemMatchRec.getBarcode());
        sqlArgs.add(etsItemMatchRec.getBarcode());
        if (matchMode.equals(WebAttrConstant.MATHC_MODE_CHANGED_ASSETS_RET)) {
            sqlArgs.add(etsItemMatchRec.getTagNumber());
            sqlArgs.add(etsItemMatchRec.getTagNumber());
            sqlArgs.add(etsItemMatchRec.getMatchUserId());
            sqlArgs.add(etsItemMatchRec.getMatchUserId());
        }
        sqlArgs.add(userAccount.getOrganizationId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


    public SQLModel getHasBeenModel(EtsItemMatchRecDTO dto) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT 1 " +
                "FROM ETS_ITEM_MATCH_TD_REC ER " +
                "WHERE ER.SYSTEM_ID = ?\n";
        sqlArgs.add(dto.getSystemId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel updateFinanceProp(EtsItemMatchRecDTO dto) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = " UPDATE ETS_ITEM_INFO \n" +
                " SET  FINANCE_PROP = ?\n" +
                " WHERE SYSTEMID= ?";
        sqlArgs.add(dto.getNewFinanceProp());
        sqlArgs.add(dto.getSystemId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel delFromMatchTbl(EtsItemMatchRecDTO dto) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM ETS_ITEM_MATCH_TD WHERE SYSTEMID = ?";
        sqlArgs.add(dto.getSystemId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel updateeimcModel(EtsItemMatchRecDTO dto) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE ETS_ITEM_MATCH_TD_COMPLET\n" +
                "SET    LAST_UPDATE_DATE = GETDATE() ,\n" +
                "       LAST_UPDATE_BY   = ?,\n" +
                "       CURRENT_UNITS    = CURRENT_UNITS - 1\n" +
                "WHERE  EXISTS (SELECT 'a'\n" +
                "        FROM   ETS_ITEM_MATCH_TD EIM\n" +
                "        WHERE  EIM.ASSET_ID = ASSET_ID\n" +
                "               AND EIM.SYSTEMID = ?)";
        sqlArgs.add(userAccount.getUserId());
        sqlArgs.add(dto.getSystemId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel insertIntoEIMRModel(EtsItemMatchRecDTO dto) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO \n" +
                "  ETS_ITEM_MATCH_TD_REC\n" +
                "(       \n" +
                "ID,          \n" +
                "MATCH_DATE       ,\n" +
                "MATCH_USER_ID    ,\n" +
                "SYSTEM_ID        ,\n" +
                "MATCH_TYPE       ,\n" +
                "OLD_FINANCE_PROP ,\n" +
                "NEW_FINANCE_PROP  ) " +
                "VALUES(\n" +
                " NEWID() ,GETDATE() ,?,?,?,?,?)";
        sqlArgs.add(userAccount.getUserId());
        sqlArgs.add(dto.getSystemId());
        sqlArgs.add(dto.getMatchType());
        sqlArgs.add(dto.getOldFinanceProp());
        sqlArgs.add(dto.getNewFinanceProp());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel updateEIMRModel(EtsItemMatchRecDTO dto) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE   " +
                "ETS_ITEM_MATCH_TD_REC \n" +
                "SET \n" +
                "MATCH_DATE = GETDATE() ,\n" +
                "MATCH_USER_ID = ?,\n" +
                "MATCH_TYPE = ?,\n" +
                "OLD_FINANCE_PROP = ?,\n" +
                "NEW_FINANCE_PROP = ?\n" +
                "WHERE SYSTEM_ID = ?";
        sqlArgs.add(userAccount.getUserId());
        sqlArgs.add(dto.getMatchType());
        sqlArgs.add(dto.getOldFinanceProp());
        sqlArgs.add(dto.getNewFinanceProp());
        sqlArgs.add(dto.getSystemId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel insertIntoEIMRLModel(EtsItemMatchRecDTO dto) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO \n" +
                "  ETS_ITEM_MATCH_TD_REC_LOG \n" +
                "(ID              ,\n" +
                "MATCH_DATE       ,\n" +
                "MATCH_USER_ID    ,\n" +
                "SYSTEM_ID        ,\n" +
                "ASSET_ID        ,\n" +
                "MATCH_TYPE       ,\n" +
                "OLD_FINANCE_PROP ,\n" +
                "NEW_FINANCE_PROP  ) " +
                "VALUES(\n" +
                " NEWID() ,GETDATE() ,?,?,?,?,?,?)";
        sqlArgs.add(userAccount.getUserId());
        sqlArgs.add(dto.getSystemId());
        sqlArgs.add(dto.getAssetId());
        sqlArgs.add(dto.getMatchType());
        sqlArgs.add(dto.getOldFinanceProp());
        sqlArgs.add(dto.getNewFinanceProp());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel updateRentInfoModel(String inSqlStr) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE ETS_ITEM_INFO EII\n" +
                "   SET EII.ATTRIBUTE1       =?,\n" +
                "       EII.FINANCE_PROP     = ?,\n" +
                "       EII.LAST_UPDATE_DATE = GETDATE() \n" +
                " WHERE EII.SYSTEMID IN (" + inSqlStr + ")";
        sqlArgs.add("RENT");
        sqlArgs.add("OTHER");

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }

    public SQLModel updateDGModel(String inSqlStr) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE ETS_ITEM_INFO EII\n" +
                "   SET EII.ATTRIBUTE1       =?,\n" +
                "       EII.FINANCE_PROP     = ?,\n" +
                "       EII.LAST_UPDATE_DATE = GETDATE() \n" +
                " WHERE EII.SYSTEMID IN (" + inSqlStr + ")";
        sqlArgs.add("DG");
        sqlArgs.add("OTHER");

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }

    public SQLModel updateLCModel(String inSqlStr) {//低值易耗资产确认
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE ETS_ITEM_INFO EII\n" +
                "   SET EII.ATTRIBUTE1       =?,\n" +
                "       EII.FINANCE_PROP     = ?,\n" +
                "       EII.LAST_UPDATE_DATE = GETDATE() \n" +
                " WHERE EII.SYSTEMID IN (" + inSqlStr + ")";
        sqlArgs.add("DZYH");
        sqlArgs.add("OTHER");

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }

    public SQLModel updateCTModel(String inSqlStr) {//村通资产确认
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE ETS_ITEM_INFO EII\n" +
                "   SET EII.ATTRIBUTE1       =?,\n" +
                "       EII.FINANCE_PROP     = ?,\n" +
                "       EII.LAST_UPDATE_DATE = GETDATE() \n" +
                " WHERE EII.SYSTEMID IN (" + inSqlStr + ")";
        sqlArgs.add("CT");
        sqlArgs.add("OTHER");

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }

    public SQLModel updateFAInfo(int assetId, String tagNumber, String misTagNumber) { //2009.4.23修改（su）
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE ETS_FA_ASSETS_TD\n" +
                "   SET TAG_NUMBER     = ?\n" +
//                "       EFA.MIS_TAG_NUMBER = ?\n" +
                " WHERE ASSET_ID = ?";

//        sqlArgs.add(tagNumber);
        sqlArgs.add(misTagNumber);
        sqlArgs.add(assetId);


        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }


    public SQLModel getTagNumber(int assetId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT EFA.TAG_NUMBER, EFA.MIS_TAG_NUMBER\n" +
                "  FROM ETS_FA_ASSETS_TD EFA\n" +
                " WHERE EFA.ASSET_ID = ?";

        sqlArgs.add(assetId);

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }

    public SQLModel updateFA_MIS(String tagNumber) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE FA_ADDITIONS_B@MIS_FA\n" +
                "   SET TAG_NUMBER=ATTRIBUTE20, LAST_UPDATE_DATE = GETDATE() \n" +
                " WHERE TAG_NUMBER = ? " +
                "    " + SyBaseSQLUtil.isNotNull("AND ATTRIBUTE20") + " ";
        sqlArgs.add(tagNumber);
        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }
    
    public SQLModel getUpdateDistributePrj(String prjId, String params){
    	SQLModel sqlModel = new SQLModel();
    	List sqlArgs =new ArrayList();
    	EtsItemMatchRecDTO dto = (EtsItemMatchRecDTO) dtoParameter;
    	String sqlStr = "UPDATE ETS_ITEM_INFO EII \n";
    	if(dto.getPrjId() != null && !dto.getPrjId().equals("")){
    		sqlStr += "	SET EII.PROJECTID = dbo.NVL(?, EII.PROJECTID) \n";
    		sqlArgs.add(prjId);
    	} else {
    		sqlStr += "	SET EII.PROJECTID = NULL \n";
    	}
		sqlStr += "	WHERE EII.SYSTEMID IN " + params;	
		sqlModel.setArgs(sqlArgs);
		sqlModel.setSqlStr(sqlStr);
		return sqlModel;
    }
    
}


