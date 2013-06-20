package com.sino.ams.match.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.match.dto.EtsItemMatchDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * Created by IntelliJ IDEA.
 * User: V-jiachuanchuan
 * Date: 2007-11-22
 * Time: 11:32:05
 * To change this template use File | Settings | File Templates.
 */
public class ChangedAssetsModel extends BaseSQLProducer {
    EtsItemMatchDTO itemMatchDTO = null;
    SfUserDTO sfUser = null;
    public ChangedAssetsModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
        this.itemMatchDTO = (EtsItemMatchDTO)dtoParameter;
        sfUser = (SfUserDTO) userAccount;
    }

    public SQLModel getLeftPageQueryModel() throws SQLModelException {
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
                "   AND EII.PROJECTID = EPPA.PROJECT_ID\n" +
                "   AND EII.FINANCE_PROP='PRJ_MTL'\n" +
                "   AND (EO.OBJECT_CATEGORY < = 70 OR EO.OBJECT_CATEGORY = 80)" +   //备件仓库不参加匹配
                "   AND EII.ORGANIZATION_ID = ? \n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.WORKORDER_OBJECT_NO = ?) \n" +
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
     public SQLModel getRightPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
         String sqlStr = "SELECT EFA.TAG_NUMBER,\n" +
                 "       EFA.ASSETS_DESCRIPTION,\n" +
                 "       EFA.ASSET_ID,"+
                 "       EFA.MODEL_NUMBER,\n" +
                 "       EFA.ASSETS_LOCATION,\n" +
                 "       EPPA.SEGMENT1,\n" +
                 "       EFA.CURRENT_UNITS\n" +
                 "  FROM ETS_FA_ASSETS EFA,\n" +
                 "       ETS_PA_PROJECTS_ALL EPPA\n" +
                 " WHERE EFA.ORGANIZATION_ID = ?\n" +
                 "   AND EXISTS (SELECT 1\n" +
                 "          FROM ETS_ITEM_MATCH_COMPLET EIMC\n" +
                 "         WHERE EFA.ASSET_ID = EIMC.ASSET_ID\n" +
                 "           AND EIMC.CURRENT_UNITS < EFA.CURRENT_UNITS)\n" +
                 "   AND EFA.PROJECT_ID *= EPPA.PROJECT_ID\n" +               
                 "   AND NOT EXISTS (SELECT 1 FROM ETS_NO_MATCH ENM WHERE ENM.ASSET_ID = EFA.ASSET_ID)\n" +  //在ETS_NO_MATCH有的资产不参加匹配
                 "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.ASSETS_LOCATION LIKE ?)\n" +
                 "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EPPA.SEGMENT1 = ?)\n" +
                 "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.ASSETS_DESCRIPTION LIKE ?)\n" +
                 "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.MODEL_NUMBER LIKE ?)";


        sqlArgs.add(sfUser.getOrganizationId());
        sqlArgs.add(itemMatchDTO.getAssetsLocation());
        sqlArgs.add(itemMatchDTO.getAssetsLocation());
        sqlArgs.add(itemMatchDTO.getSegment1());
        sqlArgs.add(itemMatchDTO.getSegment1());
        sqlArgs.add(itemMatchDTO.getItemName());
        sqlArgs.add(itemMatchDTO.getItemName());
        sqlArgs.add(itemMatchDTO.getItemSpec());
        sqlArgs.add(itemMatchDTO.getItemSpec());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

 public SQLModel getDataCreateModel(String systemid,String assetId) throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
			List sqlArgs = new ArrayList();
			EtsItemMatchDTO etsItemMatch = (EtsItemMatchDTO)dtoParameter;
			String sqlStr = "INSERT INTO "
				+ " ETS_ITEM_MATCH("
				+ " SYSTEMID,"
				+ " ITEM_ATTR,"
				+ " ASSET_ID,"
				+ " QUANTITY,"
				+ " BATCHID,"
				+ " MATCH_DATE,"
				+ " FLAG,"
				+ " USER_ID,"
				+ " CREATION_DATE,"
				+ " CREATED_BY"
				+ ") VALUES ("
				+ " ?, ?, ?, ?, ?, GETDATE(), ?, ?, GETDATE(), ?)";

			sqlArgs.add(systemid);
			sqlArgs.add(etsItemMatch.getItemAttr());
			sqlArgs.add(assetId);
			sqlArgs.add(etsItemMatch.getQuantity());
			sqlArgs.add(etsItemMatch.getBatchid());
			sqlArgs.add(etsItemMatch.getFlag());
			sqlArgs.add(sfUser.getUserId());
			sqlArgs.add(sfUser.getOrganizationId());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
