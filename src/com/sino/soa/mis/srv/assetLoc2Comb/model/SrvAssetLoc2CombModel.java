package com.sino.soa.mis.srv.assetLoc2Comb.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.config.SinoConfig;
import com.sino.soa.mis.srv.assetLoc2Comb.dto.SrvAssetLoc2CombDTO;
import com.sino.soa.util.dto.MisLocDTO;

/**
 * date：2011-12-23
 * user：wangzhipeng
 * function：资产地点第二段同步
 */
public class SrvAssetLoc2CombModel extends AMSSQLProducer{

	private SrvAssetLoc2CombDTO dto = null;
	private SfUserDTO user = null;

    /**
     * 功能：EAM新增地点同步 数据库SQL构造层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter EtsItemMatchDTO 本次操作的数据
     */
    public SrvAssetLoc2CombModel(SfUserDTO userAccount, SrvAssetLoc2CombDTO dtoParameter) {
        super(userAccount, dtoParameter);
        dto = dtoParameter;
        user = userAccount;
    }

    /**
     * 功能：查询发生变化资产地点信息
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getPageQueryModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = 
        	"SELECT TOTAL.LOC2_CODE,\n" +
        	"       TOTAL.LOC2_DESC,\n" + 
        	"       TOTAL.OLD_LOC_DESC,\n" + 
        	"       TOTAL.COMPANY_CODE,\n" + 
        	"       TOTAL.ORGANIZATION_ID,\n" + 
        	"       TOTAL.COMPANY_NAME,\n" + 
        	"       TOTAL.WORKORDER_CATEGORY,\n" + 
        	"       TOTAL.AREA_TYPE_NAEM,\n" + 
        	"       TOTAL.CITY,\n" + 
        	"       TOTAL.COUNTY,\n" + 
        	"       TOTAL.LOC_TYPE\n" + 
        	"  FROM (SELECT EOL.LOC2_CODE,\n" + 
        	"               EOL.LOC2_DESC,\n" + 
        	"               '' OLD_LOC_DESC,\n" + 
        	"               EOL.COMPANY_CODE,\n" ;
        	if (user.getIsTd().equals("N")) {
        		sqlStr += " EOCM.ORGANIZATION_ID,\n" ;
        	} else {
        		sqlStr += " EOCM.MATCH_ORGANIZATION_ID ORGANIZATION_ID,\n";
        	}
        	sqlStr += 
        	"               EOCM.COMPANY COMPANY_NAME,\n" + 
        	"               dbo.GET_FLEX_VALUE(EOL.OBJECT_CATEGORY, 'OBJECT_CATEGORY') WORKORDER_CATEGORY,\n" + 
        	"               dbo.GET_FLEX_VALUE(EOL.AREA_TYPE, 'ADDR_AREA_TYPE') AREA_TYPE_NAEM,\n" + 
        	"               EOL.CITY,\n" + 
        	"               EOL.COUNTY,\n" + 
        	"               '新增' LOC_TYPE\n" + 
        	"          FROM ETS_OBJECT_LOC2 EOL, ETS_OU_CITY_MAP EOCM\n" + 
        	"         WHERE EOL.COMPANY_CODE = EOCM.COMPANY_CODE\n" ; 
        	if (user.getIsTd().equals("N")) {
        		sqlStr +=  "	AND (EOL.SHARE_TYPE='1' OR EOL.SHARE_TYPE='2')\n" ;
        	} else {
        		sqlStr +=  "	AND (EOL.SHARE_TYPE='3' OR EOL.SHARE_TYPE='2')\n" ;
        	}
        	sqlStr +="           AND NOT EXISTS\n" + 
		        	 "         (SELECT 1\n" + 
		        	 "                  FROM M_FND_FLEX_VALUE_SETS MFFVS, M_FND_FLEX_VALUES MFFV\n" + 
		        	 "                 WHERE MFFVS.FLEX_VALUE_SET_ID = MFFV.FLEX_VALUE_SET_ID\n" + 
		        	 "                   AND MFFVS.FLEX_VALUE_SET_NAME = ?\n" + 
		        	 "                   AND EOL.LOC2_CODE = MFFV.FLEX_VALUE \n" ;
	        if (user.getIsTd().equals("N")) {
	        	 sqlStr += "           AND MFFVS.SOURCE='MIS')\n" +
	        		 	   "           AND EOCM.ORGANIZATION_ID = ?\n" ;
	        }else
	        {	
	        	 sqlStr += "           AND MFFVS.SOURCE='TDMIS')\n" +
	        		 	   "           AND EOCM.MATCH_ORGANIZATION_ID = ?\n";
	        }
	       sqlStr +=         	
        	"        UNION ALL\n" + 
        	"        SELECT EOL.LOC2_CODE,\n" + 
        	"               EOL.LOC2_DESC,\n" + 
        	"               MFFV.DESCRIPTION OLD_LOC_DESC,\n" + 
        	"               EOL.COMPANY_CODE,\n";
        	if (user.getIsTd().equals("N")) {
        		sqlStr += " EOCM.ORGANIZATION_ID,\n" ;
        	} else {
        		sqlStr += " EOCM.MATCH_ORGANIZATION_ID ORGANIZATION_ID,\n" ;
        	}        	
        	sqlStr += 
        	"               EOCM.COMPANY COMPANY_NAME,\n" + 
        	"               dbo.GET_FLEX_VALUE(EOL.OBJECT_CATEGORY, 'OBJECT_CATEGORY') WORKORDER_CATEGORY,\n" + 
        	"               dbo.GET_FLEX_VALUE(EOL.AREA_TYPE, 'ADDR_AREA_TYPE') AREA_TYPE_NAEM,\n" + 
        	"               EOL.CITY,\n" + 
        	"               EOL.COUNTY,\n" + 
        	"               '修改' LOC_TYPE\n" + 
        	"          FROM ETS_OBJECT_LOC2       EOL,\n" + 
        	"               M_FND_FLEX_VALUE_SETS MFFVS,\n" + 
        	"               M_FND_FLEX_VALUES     MFFV,\n" + 
        	"               ETS_OU_CITY_MAP       EOCM\n" + 
        	"         WHERE MFFVS.FLEX_VALUE_SET_ID = MFFV.FLEX_VALUE_SET_ID\n" + 
        	"           AND MFFVS.FLEX_VALUE_SET_NAME = ?\n" + 
        	"           AND EOL.LOC2_CODE = MFFV.FLEX_VALUE\n" ;
	        if (user.getIsTd().equals("N")) {
	        	 sqlStr += "         AND MFFVS.SOURCE='MIS'\n" +
	        	 		   "	     AND EOCM.ORGANIZATION_ID = ?"+
	        	           "	     AND (EOL.SHARE_TYPE='1' OR EOL.SHARE_TYPE='2')\n" ;
	        }else
	        {
	        	 sqlStr += "         AND MFFVS.SOURCE='TDMIS'\n" +
	        	 		   "	     AND EOCM.MATCH_ORGANIZATION_ID = ?"+
	        	           "	     AND (EOL.SHARE_TYPE='3' OR EOL.SHARE_TYPE='2')\n" ;
	        }
         sqlStr +="     AND EOL.LOC2_DESC <> MFFV.DESCRIPTION\n" + 
        	"           AND EOL.COMPANY_CODE = EOCM.COMPANY_CODE\n" + 
        	"           ) TOTAL\n" + 
        	" WHERE NOT EXISTS (SELECT NULL\n" + 
        	"          FROM ETS_FA_NEW_LOC EFC\n" + 
        	"         WHERE EFC.LOCATION = TOTAL.LOC2_DESC\n" + 
        	"           AND EFC.ORGANIZATION_ID = TOTAL.ORGANIZATION_ID\n" + 
        	"           AND EFC.STATUS = 1\n" + 
        	"           AND CONVERT(INT, CONVERT(CHAR, EFC.CREATION_DATE, 112)) =\n" + 
        	"               CONVERT(INT, CONVERT(CHAR, GETDATE(), 112)))\n" + 
        	"   AND (? = '' OR TOTAL.LOC2_CODE LIKE ?)\n" + 
        	"   AND (? = '' OR TOTAL.LOC2_DESC LIKE ?)";
        if (user.getIsTd().equals("N")) {
        	sqlArgs.add(SinoConfig.getFlexValueSetNameMis());	
        } else {
        	sqlArgs.add(SinoConfig.getFlexValueSetNameTD());
        }
        sqlArgs.add(dto.getOrganizationId());
        if (user.getIsTd().equals("N")) {
        	sqlArgs.add(SinoConfig.getFlexValueSetNameMis());	
        } else {
        	sqlArgs.add(SinoConfig.getFlexValueSetNameTD());
        }
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getWorkorderObjectCode());
        sqlArgs.add(dto.getWorkorderObjectCode());
        sqlArgs.add(dto.getNewAssetsLocation());
        sqlArgs.add(dto.getNewAssetsLocation());
  
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 增加信息到(ETS_FA_NEW_LOC)网络资产新地点表(AMS)
     * @param objectCode
     * @param batchId
     * @param userAccount
     * @return
     */
    public SQLModel getInsertSynLogModel(String objectCode, String batchId, SfUserDTO userAccount, String priKey) {
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
        	"  (SELECT '" + priKey + "',\n" + 
        	"          EOL.LOC2_CODE,\n" + 
        	"          EOL.LOC2_DESC,\n" + 
//        	"          2,\n" +//唐明胜注释
        	"          0,\n" +//唐明胜修改，1表示正在同步
//        	"          EOCM.ORGANIZATION_ID,\n" +
        	userAccount.getOrganizationId()+ ",\n" +
        	"          '',\n" + 
        	"          '" + batchId + "',\n" +
        	"          " + userAccount.getUserId() + ",\n" +
        	"          GETDATE()\n" + 
        	"     FROM ETS_OBJECT_LOC2 EOL" +
//        	"	, ETS_OU_CITY_MAP EOCM\n" + 
//        	"    WHERE EOL.COMPANY_CODE = EOCM.COMPANY_CODE\n" + 
        	"     WHERE EOL.LOC2_CODE = ?)";

        sqlArgs.add(objectCode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 导入错误日志处理 
     * ETS_FA_NEW_LOC
     */
    public SQLModel getUpdateSynErrorLogModel3(String objectNo, String errorMessage) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE ETS_FA_NEW_LOC SET STATUS = 2, MSG = ? WHERE CODE= ?";
        sqlArgs.add(errorMessage);
        sqlArgs.add(objectNo); 
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    
    /**
     * 功能：获取地点同步成功后，日志更新的SQL模型列表
     * @param batchId 同步批ID
     * @param primaryKeys 同步地点第二段数据时生成的主键
     * @return 获取地点同步成功后，日志更新的SQL模型列表
     */
    public List<SQLModel> getSynLocationSuccessModel(String batchId, List<String> primaryKeys) {
        List<SQLModel> sqlModelList = new ArrayList<SQLModel>();
        SQLModel sqlModel = new SQLModel();
        for(String primaryKey:primaryKeys){
            List<String> sqlArgs = new ArrayList<String>();
            String sqlStr = "UPDATE ETS_FA_NEW_LOC SET STATUS = 1 WHERE CODE = ? AND BATCH_ID = ?";
            sqlArgs.add(primaryKey);
            sqlArgs.add(batchId);
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
            sqlModelList.add(sqlModel);
        }
        return sqlModelList;
    }
    
    /**
     * 批量新增地点信息同步失败
     * 修改 ETS_FA_NEW_LOC 状态  STATUS:2  同步失败
     * @param errorItemList
     * @return
     */
    public List getUpdateSynErrorLogModel(String batchId, List<MisLocDTO> errorItemList) {
        List sqlModelList = new ArrayList();
        SQLModel sqlModel = null;
        for (int i = 0; i < errorItemList.size(); i++) {
        	MisLocDTO errorItem=errorItemList.get(i);
            sqlModel = new SQLModel();
            List sqlArgs = new ArrayList();
            String sqlStr = "UPDATE ETS_FA_NEW_LOC\n" +
                            "   SET STATUS = 2, MSG = ?\n" +
                            " WHERE BATCH_ID = ?\n" +
                            "   AND CODE = ?";
            sqlArgs.add("同步地点失败");
            sqlArgs.add(batchId);
            sqlArgs.add(errorItem.getCode());
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
            sqlModelList.add(sqlModel);
        }
        return sqlModelList;
    }
    
    /**
     * 批量新增地点信息同步成功 batchId:序列
     * 修改 ETS_FA_NEW_LOC 状态  STATUS:1  同步成功
     * @param batchId 序列
     * @param Loglist: list
     * @return
     */
    public List getUpdateSynLogModel(String batchId, List<MisLocDTO> Loglist) {
        List sqlModelList = new ArrayList();
        SQLModel sqlModel = null;
        for (int i = 0; i < Loglist.size(); i++) {
        	MisLocDTO dto=Loglist.get(i);
            sqlModel = new SQLModel();
            List sqlArgs = new ArrayList();
            String sqlStr = "UPDATE ETS_FA_NEW_LOC\n" +
                           "   SET STATUS = 1, MSG = ?\n" +
                           " WHERE BATCH_ID=? \n" +
                           //"   AND CODE = ?";
                           "   AND LOCATION_ID = ?";
            sqlArgs.add("同步成功");
            sqlArgs.add(batchId);
            sqlArgs.add(dto.getKey());
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
            sqlModelList.add(sqlModel);
        }
        return sqlModelList;
    }
    

}
