package com.sino.soa.td.srv.assetLocComb.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.exception.CalendarException;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_bimportassetloccombsrv.ErrorItem;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_bimportassetloccombsrv.ResponseItem;
import com.sino.soa.td.srv.assetLocComb.dto.TDSrvAssetLocCombDTO;
import com.sino.soa.util.dto.MisLocDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;

/**
 * date：2011-09-16
 * user：wangzhipeng
 * function：资产地点组合批量导入_TD
 */
public class TDSrvAssetLocCombModel extends AMSSQLProducer{

	private TDSrvAssetLocCombDTO dto = null;
 
    /**
     * 功能：EAM新增地点同步 数据库SQL构造层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter EtsItemMatchDTO 本次操作的数据
     */
    public TDSrvAssetLocCombModel(SfUserDTO userAccount, TDSrvAssetLocCombDTO dtoParameter) {
        super(userAccount, dtoParameter);
        dto = dtoParameter;
    }

    /**
     * 功能：查询发生变化资产地点信息
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getPageQueryModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "";
        try {
            sqlStr = "SELECT   " +
            "                EO.WORKORDER_OBJECT_NO,\n" +
            "                EO.WORKORDER_OBJECT_CODE,\n" +
            "                EO.WORKORDER_OBJECT_LOCATION,\n" +
            "                EO.LOCATION_CODE,\n" +
            "                TT.ASSETS_LOCATION_CODE,\n" +
            "                TT.ASSETS_LOCATION,\n" +
            "                dbo.GET_FLEX_VALUE(EO.OBJECT_CATEGORY,\n" +
            "                                           'OBJECT_CATEGORY') WORKORDER_CATEGORY,\n" +
            "                EO.ORGANIZATION_ID,\n" +
            "                EO.CREATED_BY,\n" +
            "                EO.WORKORDER_OBJECT_NAME,\n" +
            "                EO.LAST_UPDATE_DATE,\n" +
            "                SU.USERNAME,\n" +
            "                EO.REMARK,\n" +
            "                EO.CREATION_DATE\n" +
            "  FROM ETS_OBJECT EO, ETS_FA_ASSETS_LOCATION_TD TT, SF_USER SU, SF_USER SU2\n" +
            " WHERE EO.DISABLE_DATE IS NULL\n" +
            "   AND EO.WORKORDER_OBJECT_NAME NOT LIKE '%.无效'\n" +
    //        "   AND EO.WORKORDER_OBJECT_NAME NOT LIKE '%.缺省'\n" +
            "   AND EO.WORKORDER_OBJECT_CODE *= TT.ASSETS_LOCATION_CODE\n" +
            "   AND EO.ORGANIZATION_ID *= TT.ORG_ID\n" +
            "   AND EO.LAST_UPDATE_BY *= SU.USER_ID\n" +
            "   AND EO.CREATED_BY *= SU2.USER_ID\n" +
            "   AND (CONVERT(INT,EO.OBJECT_CATEGORY) < 70 OR CONVERT(int,EO.OBJECT_CATEGORY) = 80) \n" +
            "   AND (NOT EXISTS\n" +
            "        (SELECT NULL\n" +
            "           FROM ETS_FA_ASSETS_LOCATION_TD T\n" +
            "          WHERE T.ASSETS_LOCATION_CODE = EO.WORKORDER_OBJECT_CODE\n" +
            "            AND T.ORG_ID = EO.ORGANIZATION_ID) OR EXISTS\n" +
            "        (SELECT NULL\n" +
            "           FROM ETS_FA_ASSETS_LOCATION_TD T\n" +
            "          WHERE T.ASSETS_LOCATION_CODE = EO.WORKORDER_OBJECT_CODE\n" +
            "            AND T.ORG_ID = EO.ORGANIZATION_ID\n" +
            "            AND T.ASSETS_LOCATION <> EO.WORKORDER_OBJECT_NAME))\n" +
            "   AND NOT EXISTS\n" +
            " (SELECT NULL\n" +
            "          FROM ETS_FA_NEW_LOC EFC\n" +
            "         WHERE EFC.LOCATION = EO.WORKORDER_OBJECT_LOCATION\n" +
            "           AND EFC.ORGANIZATION_ID = EO.ORGANIZATION_ID\n" +
            "           AND  EFC.STATUS = 1 \n" +
            "           AND CONVERT(INT, CONVERT(CHAR, EFC.CREATION_DATE, 112)) = CONVERT(INT, CONVERT(CHAR, GETDATE(), 112)) ) \n" +
            "   AND EO.WORKORDER_OBJECT_LOCATION LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_LOCATION)\n"+
            "   AND ("+ SyBaseSQLUtil.isNull() +" OR EO.WORKORDER_OBJECT_CODE LIKE ?)\n" +
            "   AND (EO.DISABLE_DATE IS NULL OR EO.DISABLE_DATE >= GETDATE())\n" +
            "   AND EO.ORGANIZATION_ID = ? " +
            "   AND ("+SyBaseSQLUtil.isNull()+" OR SU2.USERNAME LIKE ? )\n" +
            "   AND ("+SyBaseSQLUtil.isNull()+" OR EO.CREATION_DATE >= ?)\n" +
            "   AND ("+SyBaseSQLUtil.isNull()+" OR EO.CREATION_DATE <= ?)\n" +
            "   ORDER BY EO.CREATION_DATE DESC ";

            sqlArgs.add(dto.getNewAssetsLocation());
            sqlArgs.add(dto.getWorkorderObjectCode());
            sqlArgs.add(dto.getWorkorderObjectCode());
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getCreateUser());
            sqlArgs.add(dto.getCreateUser());
            sqlArgs.add(dto.getStartDate());
            sqlArgs.add(dto.getStartDate());
            sqlArgs.add(dto.getSQLEndDate());
            sqlArgs.add(dto.getSQLEndDate());
        } catch (
            CalendarException e) {
            e.printLog();
        }
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * function: 返回符合同步条件的地点信息
     * @param: objectNos(需要同步的地点序列号)
     * @return 符合条件的地点信息_ETS_OBJECT
     */
    public SQLModel getSynLocModel(String objectNos) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT EO.WORKORDER_OBJECT_NO,\n" +
                "       EO.WORKORDER_OBJECT_CODE,\n" +
                "       EO.WORKORDER_OBJECT_NAME,\n" +
                "       EO.ORGANIZATION_ID\n" +
                "  FROM ETS_OBJECT EO\n" +
                " WHERE EO.WORKORDER_OBJECT_NO IN (" + objectNos + ")";
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 增加信息到(ETS_FA_NEW_LOC)网络资产新地点表(AMS)
     * @param objectNos
     * @param batchId
     * @param userAccount
     * @return
     */
    public SQLModel getInsertSynLogModel(String objectNos, String batchId, SfUserDTO userAccount) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr =
		        "INSERT INTO ETS_FA_NEW_LOC\n" +
		        "  (LOCATION_ID,\n" +
		        "   CODE,\n" +
		        "   LOCATION,\n" +
		        "   STATUS,\n" +
		        "   ORGANIZATION_ID,\n" +
		        "   MSG,\n" +
		        "   BATCH_ID,\n" +
		        "   CREATED_BY,\n" +
		        "   CREATION_DATE)\n" +
		        "  (SELECT NEWID(),\n" +
		        "          EO.WORKORDER_OBJECT_NO,\n" +
		        "          EO.WORKORDER_OBJECT_NAME,\n" +
//		        "          2,\n" +//唐明胜注释，
		        "          0,\n" +//唐明胜修改，0标示正在同步
		        "          EO.ORGANIZATION_ID,\n" +
		        "          '',\n" +
		        "          '" + batchId + "',\n" +
		        "          " + userAccount.getUserId() + ",\n" +
		        "          GETDATE()\n" +
		        "     FROM ETS_OBJECT EO\n" +
		        "   WHERE EO.WORKORDER_OBJECT_NO IN (" + objectNos + "))";   
        
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 更改日志表ETS_FA_NEW_LOC 失败:STATUS = 2
     * @param barCode
     * @param errorMessage
     * @return
     */
    public SQLModel getUpdateSynErrorLogModel3(String batchId, String barCode, String errorMessage) {
        List sqlModelList = new ArrayList();
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE ETS_FA_NEW_LOC SET STATUS = 2, MSG = ? WHERE BATCH_ID = ? AND CODE= ?";
        sqlArgs.add(errorMessage);
        sqlArgs.add(batchId);
        sqlArgs.add(barCode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        sqlModelList.add(sqlModel);
        return sqlModel;
    }

    public List<SQLModel> getLocationSynSuccessModel(String batchId, List<String> objectNos) {
        List<SQLModel> sqlModelList = new ArrayList<SQLModel>();
        for (String objectNo : objectNos) {
            SQLModel sqlModel = new SQLModel();
            List<String> sqlArgs = new ArrayList<String>();
            String sqlStr = "UPDATE ETS_FA_NEW_LOC SET STATUS = 1 WHERE BATCH_ID = ? AND CODE= ?";
            sqlArgs.add(batchId);
            sqlArgs.add(objectNo);
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
            sqlModelList.add(sqlModel);
        }
        return sqlModelList;
    }


    /**
     * 修改 ETS_FA_NEW_LOC 状态  STATUS:1  同步成功
     * @param batchId
     * @param responseItemList
     * @return
     */
    public List getUpdateSynLogModel(String batchId, List<MisLocDTO> responseItemList) {
        List sqlModelList = new ArrayList();
        SQLModel sqlModel = null;
        for (int i = 0; i < responseItemList.size(); i++) {
        	MisLocDTO responseItem=responseItemList.get(i);
            sqlModel = new SQLModel();
            List sqlArgs = new ArrayList();
            String sqlStr = "UPDATE ETS_FA_NEW_LOC\n" +
                            "   SET STATUS = 1, MSG = ?\n" +
                            " WHERE ORGANIZATION_ID = CONVERT(INT,?)\n" +
                            "   AND BATCH_ID = ?" +
                            "   AND CODE = ?";
            sqlArgs.add("同步TD地点成功");
            sqlArgs.add(responseItem.getOrganizationId());
            sqlArgs.add(batchId);
            sqlArgs.add(responseItem.getKey());
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
            sqlModelList.add(sqlModel);
        }
        return sqlModelList;
    }
    

}
