package com.sino.ams.match.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.match.dto.EtsItemMatchRecDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.StrUtil;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2008-6-10
 * Time: 16:01:07
 * To change this template use File | Settings | File Templates.
 */
public class AmsRentAssetModel extends BaseSQLProducer {
    private SfUserDTO sfUser = null;

    public AmsRentAssetModel(SfUserDTO userAccount, EtsItemMatchRecDTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
    }

    public SQLModel getPageQueryModel() {
        SQLModel sqlModel = new SQLModel();

        List sqlArgs = new ArrayList();
        EtsItemMatchRecDTO etsItemMatchRec = (EtsItemMatchRecDTO) dtoParameter;
        String matchMode = StrUtil.nullToString(etsItemMatchRec.getMatchType());
        String sqlStr = "";
        if (!matchMode.equals(WebAttrConstant.MATCH_MODE_RENT_RET)) {
            sqlStr = "SELECT EII.BARCODE,\n" +
                    "       EII.SYSTEMID SYSTEM_ID,\n" +
                    "       EII.FINANCE_PROP,\n" +
                    "       EII.ITEM_CODE,\n" +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "      AMS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY,\n" +
                    "       EO.WORKORDER_OBJECT_NAME," +
                    "       AME.USER_NAME,\n" +
                    "       EII.MAINTAIN_USER," +
                    "       EII.START_DATE\n" +
                    "  FROM ETS_ITEM_INFO      EII,\n" +
                    "       ETS_SYSTEM_ITEM    ESI,\n" +
                    "       AMS_OBJECT_ADDRESS AOA,\n" +
                    "       ETS_OBJECT         EO," +
                    "       AMS_MIS_EMPLOYEE   AME\n" +
                    " WHERE EII.ITEM_CODE = ESI.ITEM_CODE\n" +
                    "   AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                    "   AND (EO.OBJECT_CATEGORY < = 70 OR EO.OBJECT_CATEGORY = 80)\n" +
                    "   AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                    "   --AND EII.ATTRIBUTE1 = 'RENT'\n";
            if (etsItemMatchRec.getUserName().equals("")) {
                sqlStr += "   AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID";
            } else {
                sqlStr += "   AND EII.RESPONSIBILITY_USER = AME.EMPLOYEE_ID";
            }

            sqlStr +=
                    "   AND EII.DISABLE_DATE  " + SyBaseSQLUtil.isNullNoParam() + "  " +
                            "   AND EII.FINANCE_PROP = ?\n";

            sqlArgs.add(DictConstant.FIN_PROP_UNKNOW);

        } else {
            sqlStr = "SELECT EII.BARCODE,\n" +
                    "       EII.SYSTEMID SYSTEM_ID,\n" +
                    "       EII.FINANCE_PROP,\n" +
                    "       EII.ITEM_CODE,\n" +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       AMS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY,\n" +
                    "       EO.WORKORDER_OBJECT_NAME," +
                    "       AME.USER_NAME,\n" +
                    "       EII.MAINTAIN_USER," +
                    "       EII.START_DATE\n" +
                    "FROM   ETS_ITEM_INFO      EII,\n" +
                    "       ETS_SYSTEM_ITEM    ESI,\n" +
                    "       AMS_OBJECT_ADDRESS AOA,\n" +
                    "       ETS_OBJECT         EO," +
                    "       AMS_MIS_EMPLOYEE   AME\n" +
                    "WHERE  EII.ITEM_CODE = ESI.ITEM_CODE\n" +
                    "       AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                    "       AND (EO.OBJECT_CATEGORY < = 70 OR EO.OBJECT_CATEGORY = 80)\n" +
                    "       AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                    "       AND EII.FINANCE_PROP = 'OTHER'\n";
            if (etsItemMatchRec.getUserName().equals("")) {
                sqlStr += "      AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID";
            } else {
                sqlStr += "      AND EII.RESPONSIBILITY_USER = AME.EMPLOYEE_ID";
            }
        }
        sqlStr += " AND  ( " + SyBaseSQLUtil.isNull() + "  OR EO.Workorder_Object_Name like ?)\n" +
                " AND    ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_NAME LIKE ?)\n" +
                " AND    ( " + SyBaseSQLUtil.isNull() + "  OR ESI.Item_Spec LIKE ? )\n" +
                " AND    ( " + SyBaseSQLUtil.isNull() + "  OR EII.BARCODE LIKE ? )\n" +
                " AND    EII.ORGANIZATION_ID = ?" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.MAINTAIN_USER LIKE ?)\n";
        sqlArgs.add(etsItemMatchRec.getWorkorderObjectName());
        sqlArgs.add(etsItemMatchRec.getWorkorderObjectName());
        sqlArgs.add(etsItemMatchRec.getItemName());
        sqlArgs.add(etsItemMatchRec.getItemName());
        sqlArgs.add(etsItemMatchRec.getItemSpec());
        sqlArgs.add(etsItemMatchRec.getItemSpec());
        sqlArgs.add(etsItemMatchRec.getBarcode());
        sqlArgs.add(etsItemMatchRec.getBarcode());
        sqlArgs.add(sfUser.getOrganizationId());
        sqlArgs.add(etsItemMatchRec.getMaintainUser());
        sqlArgs.add(etsItemMatchRec.getMaintainUser());

        if (!etsItemMatchRec.getUserName().equals("")) {
            sqlStr += "  AND AME.USER_NAME LIKE ?\n";
        }
        sqlArgs.add(etsItemMatchRec.getUserName());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    public SQLModel deletelog(String systemId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM ETS_ITEM_MATCH_REC WHERE SYSTEM_ID = ?";
        sqlArgs.add(systemId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel insertIntolog(String systemId, String matchType) {
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
                " NEWID() ,GETDATE(),?,?,?,?,?)";
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(systemId);
        sqlArgs.add(matchType);
        sqlArgs.add("UNKNOW");
        sqlArgs.add("OTHER");
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel insertIntoEIMRModel(EtsItemMatchRecDTO dto) {
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
                " NEWID() ,GETDATE(),?,?,?,?,?)";
        sqlArgs.add(sfUser.getUserId());
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
                "ETS_ITEM_MATCH_REC \n" +
                "SET \n" +
                "MATCH_DATE = GETDATE(),\n" +
                "MATCH_USER_ID = ?,\n" +
                "MATCH_TYPE = ?,\n" +
                "OLD_FINANCE_PROP = ?,\n" +
                "NEW_FINANCE_PROP = ?\n" +
                "WHERE SYSTEM_ID = ?";
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(dto.getMatchType());
        sqlArgs.add(dto.getOldFinanceProp());
        sqlArgs.add(dto.getNewFinanceProp());
        sqlArgs.add(dto.getSystemId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel insertIntoRecLog(String systemId) {
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
                " NEWID() ,GETDATE(),?,?,?,?,?)";
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(systemId);
        sqlArgs.add("10");
        sqlArgs.add("UNKNOW");
        sqlArgs.add("OTHER");
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel insertIntoRecLogs(String systemId) {
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
                " NEWID() ,GETDATE(),?,?,?,?,?)";
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(systemId);
        sqlArgs.add("11");
        sqlArgs.add("OTHER");
        sqlArgs.add("UNKNOW");
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel updateRentInfoModel(String inSqlStr, String startDate) throws CalendarException, SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE ETS_ITEM_INFO\n" +
                "   SET ATTRIBUTE1       =?,\n" +
                "       FINANCE_PROP     = ?," +
                "       START_DATE=?,\n" +
                "       LAST_UPDATE_DATE = GETDATE()\n" +
                " WHERE SYSTEMID = ?";
        sqlArgs.add("RENT");
        sqlArgs.add("OTHER");
        sqlArgs.add(new SimpleCalendar(startDate));
        sqlArgs.add(inSqlStr);

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }

    public SQLModel insertRentInfoModel(String barcode) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO AMS_RENT_INFO\n" +
                "  (RENT_ID, BARCODE)\n" +
                "VALUES\n" +
                "  ( NEWID() , ?)";
        sqlArgs.add(barcode);

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }

    public SQLModel deleteRentInfoModel(String barcode) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "  DELETE FROM AMS_RENT_INFO WHERE BARCODE = ?";
        sqlArgs.add(barcode);

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }

    public SQLModel cancleRentInfoModel(String inSqlStr) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE ETS_ITEM_INFO \n" +
                "   SET ATTRIBUTE1       =?,\n" +
                "       FINANCE_PROP     = ?,\n" +
                "       LAST_UPDATE_DATE = GETDATE()\n" +
                " WHERE SYSTEMID = ?";
        sqlArgs.add("");
        sqlArgs.add("UNKNOW");
        sqlArgs.add(inSqlStr);

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }

    public SQLModel getDataCreateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            EtsItemMatchRecDTO etsItemMatchRec = (EtsItemMatchRecDTO) dtoParameter;
            String sqlStr = "INSERT INTO "
                    + " ETS_ITEM_MATCH_REC("
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
     * 功能：框架自动生成ETS_ITEM_MATCH_REC数据更新SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据更新用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getDataUpdateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            EtsItemMatchRecDTO etsItemMatchRec = (EtsItemMatchRecDTO) dtoParameter;
            String sqlStr = "UPDATE ETS_ITEM_MATCH_REC"
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

    public SQLModel getHasBeenModel(EtsItemMatchRecDTO dto) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT 1 " +
                "FROM ETS_ITEM_MATCH_REC ER " +
                "WHERE ER.SYSTEM_ID = ?\n";
        sqlArgs.add(dto.getSystemId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成ETS_ITEM_MATCH_REC数据删除SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据删除用SQLModel
     */
    public SQLModel getDataDeleteModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsItemMatchRecDTO etsItemMatchRec = (EtsItemMatchRecDTO) dtoParameter;
        String sqlStr = "DELETE FROM"
                + " ETS_ITEM_MATCH_REC"
                + " WHERE"
                + " ID = ?";
        sqlArgs.add(etsItemMatchRec.getId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
