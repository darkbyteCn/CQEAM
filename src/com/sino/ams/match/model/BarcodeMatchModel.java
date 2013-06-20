package com.sino.ams.match.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;
import com.sino.base.dto.DTOSet;
import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.match.dto.BarcodeMatchDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.newasset.dto.AmsMisDeptDTO;

/**
 * User: Zyun Date: 2007-11-29 Time: 9:36:13
 */
public class BarcodeMatchModel extends AMSSQLProducer {

	/**
	 * 功能：资产匹配-匹配数据存储(EAM) ETS_ITEM_MATCH 数据库SQL构造层构造函数
	 * 
	 * @param userAccount
	 *            SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter
	 *            EtsItemMatchDTO 本次操作的数据
	 */
	public BarcodeMatchModel(SfUserDTO userAccount, BarcodeMatchDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	public SQLModel getDataCreateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		BarcodeMatchDTO dto = (BarcodeMatchDTO) dtoParameter;
		if ("Y".equalsIgnoreCase(userAccount.getIsTd())) {
			String sqlStr = "INSERT INTO "
					// + " ETS_ITEM_MATCH("
					+ " ETS_ITEM_MATCH_TD(" + " SYSTEMID," + " ITEM_ATTR,"
					+ " ASSET_ID," + " QUANTITY," + " MATCH_DATE,"
					+ " CREATION_DATE," + " CREATED_BY" + ") VALUES ("
					+ " ?, 0, ?, 1, GETDATE(), GETDATE(), ?)";

			sqlArgs.add(dto.getSystemid());
			sqlArgs.add(dto.getAssetId());
			sqlArgs.add(userAccount.getUserId());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} else if("Y".equalsIgnoreCase(userAccount.getIsTt())){
           String sqlStr = "INSERT INTO "
					// + " ETS_ITEM_MATCH("
					+ " ETS_ITEM_MATCH_TD(" + " SYSTEMID," + " ITEM_ATTR,"
					+ " ASSET_ID," + " QUANTITY," + " MATCH_DATE,"
					+ " CREATION_DATE," + " CREATED_BY" + ") VALUES ("
					+ " ?, 0, ?, 1, GETDATE(), GETDATE(), ?)";

			sqlArgs.add(dto.getSystemid());
			sqlArgs.add(dto.getAssetId());
			sqlArgs.add(userAccount.getUserId());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
        }else
        {
			String sqlStr = "INSERT INTO " + " ETS_ITEM_MATCH(" + " SYSTEMID,"
					+ " ITEM_ATTR," + " ASSET_ID," + " QUANTITY,"
					+ " MATCH_DATE," + " USER_ID," + " CREATION_DATE,"
					+ " CREATED_BY" + ") VALUES ("
					+ " ?, 0, ?, 1, GETDATE(), ?, GETDATE(), ?)";

			sqlArgs.add(dto.getSystemid());
			sqlArgs.add(dto.getAssetId());
			sqlArgs.add(userAccount.getUserId());
			sqlArgs.add(userAccount.getUserId());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成资产匹配-匹配数据存储(EAM) ETS_ITEM_MATCH数据更新SQLModel，请根据实际需要修改。
	 * 
	 * @return SQLModel 返回数据更新用SQLModel
	 * @throws SQLModelException
	 *             发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataUpdateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		BarcodeMatchDTO dto = (BarcodeMatchDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		if ("Y".equalsIgnoreCase(userAccount.getIsTd())) {
			String sqlStr = "UPDATE ETS_ITEM_INFO\n" + " SET \n"
					+ " FINANCE_PROP = ?, \n"
					+ " LAST_UPDATE_DATE = GETDATE(), \n" + " LAST_UPDATE_BY = ?,"
					+ "	CONTENT_CODE = (SELECT EFA.SEGMENT1||'.'||EFA.SEGMENT2||'.'||EFA.SEGMENT3 FROM ETS_FA_ASSETS EFA WHERE EFA.ASSET_ID=?),"
					+ "	CONTENT_NAME = (SELECT FA_CATEGORY2 FROM ETS_FA_ASSETS  WHERE ASSET_ID=?)"
					+ " WHERE SYSTEMID = ?";
			// sqlArgs.add(DictConstant.FIN_PROP_ASSETS);
            if("Y".equalsIgnoreCase(userAccount.getIsTt())){
              sqlArgs.add(DictConstant.FIN_PROP_TT);
            } else{
               sqlArgs.add(DictConstant.FIN_PROP_TD);
            }

			sqlArgs.add(userAccount.getUserId());
			sqlArgs.add(dto.getAssetId());
			sqlArgs.add(dto.getAssetId());
			sqlArgs.add(dto.getSystemid());
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		}else if  ("Y".equalsIgnoreCase(userAccount.getIsTt())) {
           String sqlStr = "UPDATE ETS_ITEM_INFO\n" + " SET \n"
					+ " FINANCE_PROP = ?, \n"
					+ " LAST_UPDATE_DATE = GETDATE(), \n" + " LAST_UPDATE_BY = ?,"
					+ "	CONTENT_CODE = (SELECT EFA.SEGMENT1||'.'||EFA.SEGMENT2||'.'||EFA.SEGMENT3 FROM ETS_FA_ASSETS EFA WHERE EFA.ASSET_ID=?),"
					+ "	CONTENT_NAME = (SELECT FA_CATEGORY2 FROM ETS_FA_ASSETS  WHERE ASSET_ID=?)"
					+ " WHERE SYSTEMID = ?";
			// sqlArgs.add(DictConstant.FIN_PROP_ASSETS);

              sqlArgs.add(DictConstant.FIN_PROP_TT);


			sqlArgs.add(userAccount.getUserId());
			sqlArgs.add(dto.getAssetId());
			sqlArgs.add(dto.getAssetId());
			sqlArgs.add(dto.getSystemid());
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
        }else {
			String sqlStr = "UPDATE ETS_ITEM_INFO\n" + " SET \n"
					+ " FINANCE_PROP = ?, \n"
					+ " LAST_UPDATE_DATE = GETDATE(), \n" + " LAST_UPDATE_BY = ?,"
					+ "	CONTENT_CODE = (SELECT EFA.SEGMENT1||'.'||EFA.SEGMENT2||'.'||EFA.SEGMENT3 FROM ETS_FA_ASSETS EFA WHERE EFA.ASSET_ID=?),"
					+ "	CONTENT_NAME = (SELECT FA_CATEGORY2 FROM ETS_FA_ASSETS  WHERE ASSET_ID=?)"
					+ " WHERE SYSTEMID = ?";
			sqlArgs.add(DictConstant.FIN_PROP_ASSETS);
			sqlArgs.add(userAccount.getUserId());
			sqlArgs.add(dto.getAssetId());
			sqlArgs.add(dto.getAssetId());
			sqlArgs.add(dto.getSystemid());
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		}
		return sqlModel;
	}

	public SQLModel getHasBeenModel() {
		BarcodeMatchDTO dto = (BarcodeMatchDTO) dtoParameter;
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		if ("Y".equalsIgnoreCase(userAccount.getIsTd())) {
			String sqlStr = "SELECT 1 " + "  FROM ETS_ITEM_MATCH_TD_REC ER "
					+ " WHERE ER.SYSTEM_ID = ?\n";
			sqlArgs.add(dto.getSystemid());
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		}else if  ("Y".equalsIgnoreCase(userAccount.getIsTt())) {
          String sqlStr = "SELECT 1 " + "  FROM ETS_ITEM_MATCH_TD_REC ER "
					+ " WHERE ER.SYSTEM_ID = ?\n";
			sqlArgs.add(dto.getSystemid());
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
        } else {
			String sqlStr = "SELECT 1 " + "  FROM ETS_ITEM_MATCH_REC ER "
					+ " WHERE ER.SYSTEM_ID = ?\n";
			sqlArgs.add(dto.getSystemid());
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		}
		return sqlModel;
	}

	public SQLModel insertIntoEIMRModel() {
		BarcodeMatchDTO dto = (BarcodeMatchDTO) dtoParameter;
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		if ("Y".equalsIgnoreCase(userAccount.getIsTd())) {
			String   sqlStr="";
             if("Y".equalsIgnoreCase(userAccount.getIsTt())){
                    sqlStr = "INSERT INTO \n"
					+ "  ETS_ITEM_MATCH_TD_REC\n"
					+ "(       \n"
					+ "ID,          \n"
					+ "MATCH_DATE       ,\n"
					+ "MATCH_USER_ID    ,\n"
					+ "SYSTEM_ID        ,\n"
					+ "ASSET_ID         ,\n"
					+ "MATCH_TYPE       ,\n"
					+ "OLD_FINANCE_PROP ,\n"
					+ "NEW_FINANCE_PROP  ) "
					+ "VALUES(\n"
					+ "NEWID(),GETDATE(),?,?,?,'8','UNKNOW','TT_ASSETS')";
             }else{
                  sqlStr = "INSERT INTO \n"
					+ "  ETS_ITEM_MATCH_TD_REC\n"
					+ "(       \n"
					+ "ID,          \n"
					+ "MATCH_DATE       ,\n"
					+ "MATCH_USER_ID    ,\n"
					+ "SYSTEM_ID        ,\n"
					+ "ASSET_ID         ,\n"
					+ "MATCH_TYPE       ,\n"
					+ "OLD_FINANCE_PROP ,\n"
					+ "NEW_FINANCE_PROP  ) "
					+ "VALUES(\n"
					+ "NEWID(),GETDATE(),?,?,?,'8','UNKNOW','TD_ASSETS')";
             }

			sqlArgs.add(userAccount.getUserId());
			sqlArgs.add(dto.getSystemid());
			sqlArgs.add(dto.getAssetId());
			// sqlArgs.add(dto.getOldFinanceProp());
			// sqlArgs.add(dto.getNewFinanceProp());
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		}else if  ("Y".equalsIgnoreCase(userAccount.getIsTt())) {
           String   sqlStr = "INSERT INTO \n"
					+ "  ETS_ITEM_MATCH_TD_REC\n"
					+ "(       \n"
					+ "ID,          \n"
					+ "MATCH_DATE       ,\n"
					+ "MATCH_USER_ID    ,\n"
					+ "SYSTEM_ID        ,\n"
					+ "ASSET_ID         ,\n"
					+ "MATCH_TYPE       ,\n"
					+ "OLD_FINANCE_PROP ,\n"
					+ "NEW_FINANCE_PROP  ) "
					+ "VALUES(\n"
					+ "NEWID(),GETDATE(),?,?,?,'8','UNKNOW','TD_ASSETS')";
            sqlArgs.add(userAccount.getUserId());
			sqlArgs.add(dto.getSystemid());
			sqlArgs.add(dto.getAssetId());
			// sqlArgs.add(dto.getOldFinanceProp());
			// sqlArgs.add(dto.getNewFinanceProp());
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
             } else {
			String sqlStr = "INSERT INTO \n"
					+ "  ETS_ITEM_MATCH_REC\n"
					+ "(       \n"
					+ "ID,          \n"
					+ "MATCH_DATE       ,\n"
					+ "MATCH_USER_ID    ,\n"
					+ "SYSTEM_ID        ,\n"
					+ "ASSET_ID         ,\n"
					+ "MATCH_TYPE       ,\n"
					+ "OLD_FINANCE_PROP ,\n"
					+ "NEW_FINANCE_PROP  ) "
					+ "VALUES(\n"
					+ "NEWID(),GETDATE(),?,?,?,'8','UNKNOW','ASSETS')";
			sqlArgs.add(userAccount.getUserId());
			sqlArgs.add(dto.getSystemid());
			sqlArgs.add(dto.getAssetId());
			// sqlArgs.add(dto.getOldFinanceProp());
			// sqlArgs.add(dto.getNewFinanceProp());
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		}
		return sqlModel;
	}

	public SQLModel updateEIMRModel() {
		BarcodeMatchDTO dto = (BarcodeMatchDTO) dtoParameter;
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();

		if ("Y".equalsIgnoreCase(userAccount.getIsTd())) {
            	String   sqlStr="";
               if ("Y".equalsIgnoreCase(userAccount.getIsTt())) {
                       sqlStr = "UPDATE   " + "ETS_ITEM_MATCH_TD_REC \n" + "SET \n"
					+ "MATCH_DATE = GETDATE(),\n" + "MATCH_USER_ID = ?,\n"
					+ "MATCH_TYPE = '8',\n" + "OLD_FINANCE_PROP = 'UNKNOW',\n"
					+ "NEW_FINANCE_PROP = 'TT_ASSETS'\n"
					+ "WHERE SYSTEM_ID = ?";
               }else{
                     sqlStr = "UPDATE   " + "ETS_ITEM_MATCH_TD_REC \n" + "SET \n"
					+ "MATCH_DATE = GETDATE(),\n" + "MATCH_USER_ID = ?,\n"
					+ "MATCH_TYPE = '8',\n" + "OLD_FINANCE_PROP = 'UNKNOW',\n"
					+ "NEW_FINANCE_PROP = 'TD_ASSETS'\n"
					+ "WHERE SYSTEM_ID = ?";
               }



			sqlArgs.add(userAccount.getUserId());
			// sqlArgs.add(dto.getOldFinanceProp());
			// sqlArgs.add(dto.getNewFinanceProp());
			sqlArgs.add(dto.getSystemid());
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		}else if  ("Y".equalsIgnoreCase(userAccount.getIsTt())) {
           String  sqlStr = "UPDATE   " + "ETS_ITEM_MATCH_TD_REC \n" + "SET \n"
					+ "MATCH_DATE = GETDATE(),\n" + "MATCH_USER_ID = ?,\n"
					+ "MATCH_TYPE = '8',\n" + "OLD_FINANCE_PROP = 'UNKNOW',\n"
					+ "NEW_FINANCE_PROP = 'TD_ASSETS'\n"
					+ "WHERE SYSTEM_ID = ?";




			sqlArgs.add(userAccount.getUserId());
			// sqlArgs.add(dto.getOldFinanceProp());
			// sqlArgs.add(dto.getNewFinanceProp());
			sqlArgs.add(dto.getSystemid());
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
        } else {
			String sqlStr = "UPDATE   " + "ETS_ITEM_MATCH_REC \n" + "SET \n"
					+ "MATCH_DATE = GETDATE(),\n" + "MATCH_USER_ID = ?,\n"
					+ "MATCH_TYPE = '8',\n" + "OLD_FINANCE_PROP = 'UNKNOW',\n"
					+ "NEW_FINANCE_PROP = 'ASSETS'\n" + "WHERE SYSTEM_ID = ?";
			sqlArgs.add(userAccount.getUserId());
			// sqlArgs.add(dto.getOldFinanceProp());
			// sqlArgs.add(dto.getNewFinanceProp());
			sqlArgs.add(dto.getSystemid());
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		}
		return sqlModel;
	}

	public SQLModel deleteEIMRModel() {
		BarcodeMatchDTO dto = (BarcodeMatchDTO) dtoParameter;
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		if ("Y".equalsIgnoreCase(userAccount.getIsTd())) {
			// 原td程序中该model不存在
		} else {
			String sqlStr = "DELETE ETS_ITEM_MATCH_REC \n"
					+ " WHERE SYSTEM_ID = ?";

			sqlArgs.add(dto.getSystemid());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		}
		return sqlModel;
	}

	public SQLModel insertIntoEIMRLModel() {
		BarcodeMatchDTO dto = (BarcodeMatchDTO) dtoParameter;
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		if ("Y".equalsIgnoreCase(userAccount.getIsTd())) {
            String   sqlStr="";
             if("Y".equalsIgnoreCase(userAccount.getIsTt())){
                  sqlStr = "INSERT INTO \n"
					+ "  ETS_ITEM_MATCH_TD_REC_LOG \n"
					+ "(ID              ,\n"
					+ "MATCH_DATE       ,\n"
					+ "MATCH_USER_ID    ,\n"
					+ "SYSTEM_ID        ,\n"
					+ "ASSET_ID         ,\n"
					+ "MATCH_TYPE       ,\n"
					+ "OLD_FINANCE_PROP ,\n"
					+ "NEW_FINANCE_PROP  ) "
					+ "VALUES(\n"
					+ "NEWID(),GETDATE(),?,?,?,'8','UNKNOW','TT_ASSETS')";
             }  else{
                sqlStr = "INSERT INTO \n"
					+ "  ETS_ITEM_MATCH_TD_REC_LOG \n"
					+ "(ID              ,\n"
					+ "MATCH_DATE       ,\n"
					+ "MATCH_USER_ID    ,\n"
					+ "SYSTEM_ID        ,\n"
					+ "ASSET_ID         ,\n"
					+ "MATCH_TYPE       ,\n"
					+ "OLD_FINANCE_PROP ,\n"
					+ "NEW_FINANCE_PROP  ) "
					+ "VALUES(\n"
					+ "NEWID(),GETDATE(),?,?,?,'8','UNKNOW','TD_ASSETS')";
             }

			sqlArgs.add(userAccount.getUserId());
			sqlArgs.add(dto.getSystemid());
			sqlArgs.add(dto.getAssetId());
			// sqlArgs.add(dto.getOldFinanceProp());
			// sqlArgs.add(dto.getNewFinanceProp());
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		}else if  ("Y".equalsIgnoreCase(userAccount.getIsTt())) {
        String  sqlStr = "INSERT INTO \n"
					+ "  ETS_ITEM_MATCH_TD_REC_LOG \n"
					+ "(ID              ,\n"
					+ "MATCH_DATE       ,\n"
					+ "MATCH_USER_ID    ,\n"
					+ "SYSTEM_ID        ,\n"
					+ "ASSET_ID         ,\n"
					+ "MATCH_TYPE       ,\n"
					+ "OLD_FINANCE_PROP ,\n"
					+ "NEW_FINANCE_PROP  ) "
					+ "VALUES(\n"
					+ "NEWID(),GETDATE(),?,?,?,'8','UNKNOW','TD_ASSETS')";


			sqlArgs.add(userAccount.getUserId());
			sqlArgs.add(dto.getSystemid());
			sqlArgs.add(dto.getAssetId());
			// sqlArgs.add(dto.getOldFinanceProp());
			// sqlArgs.add(dto.getNewFinanceProp());
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
        } else {
			String sqlStr = "INSERT INTO \n"
					+ "  ETS_ITEM_MATCH_REC_LOG \n"
					+ "(ID              ,\n"
					+ "MATCH_DATE       ,\n"
					+ "MATCH_USER_ID    ,\n"
					+ "SYSTEM_ID        ,\n"
					+ "MATCH_TYPE       ,\n"
					+ "OLD_FINANCE_PROP ,\n"
					+ "NEW_FINANCE_PROP  ) "
					+ "VALUES(\n"
					+ "NEWID(),GETDATE(),?,?,'8','UNKNOW','ASSETS')";
			sqlArgs.add(userAccount.getUserId());
			sqlArgs.add(dto.getSystemid());
			// sqlArgs.add(dto.getOldFinanceProp());
			// sqlArgs.add(dto.getNewFinanceProp());
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		}
		return sqlModel;
	}

	public SQLModel updateEIMCModel() {
		BarcodeMatchDTO dto = (BarcodeMatchDTO) dtoParameter;
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		if ("Y".equalsIgnoreCase(userAccount.getIsTd())) {
			String sqlStr = " UPDATE " + " ETS_ITEM_MATCH_TD_COMPLET\n"
					+ " SET " + " CURRENT_UNITS = CURRENT_UNITS + 1,\n"
					+ " LAST_UPDATE_DATE = GETDATE(),\n" + " LAST_UPDATE_BY =? "
					+ " WHERE " + " ASSET_ID = ?";

			sqlArgs.add(userAccount.getUserId());
			sqlArgs.add(dto.getAssetId());
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		}else if  ("Y".equalsIgnoreCase(userAccount.getIsTt())) {
           String sqlStr = " UPDATE " + " ETS_ITEM_MATCH_TD_COMPLET\n"
					+ " SET " + " CURRENT_UNITS = CURRENT_UNITS + 1,\n"
					+ " LAST_UPDATE_DATE = GETDATE(),\n" + " LAST_UPDATE_BY =? "
					+ " WHERE " + " ASSET_ID = ?";

			sqlArgs.add(userAccount.getUserId());
			sqlArgs.add(dto.getAssetId());
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
        } else {
			String sqlStr = " UPDATE " + " ETS_ITEM_MATCH_COMPLET\n" + " SET "
					+ " CURRENT_UNITS = CURRENT_UNITS + 1,\n"
					+ " LAST_UPDATE_DATE = GETDATE(),\n" + " LAST_UPDATE_BY =? "
					+ " WHERE " + " ASSET_ID = ?";

			sqlArgs.add(userAccount.getUserId());
			sqlArgs.add(dto.getAssetId());
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		}
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
		if ("Y".equalsIgnoreCase(userAccount.getIsTd())) {
			String sqlStr = "DELETE FROM" + " ETS_ITEM_MATCH";
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} else {
			String sqlStr = "DELETE FROM" + " ETS_ITEM_MATCH";
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		}
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
		if ("Y".equalsIgnoreCase(userAccount.getIsTd())) {
			String sqlStr = "SELECT " + " SYSTEMID," + " ITEM_ATTR,"
					+ " ASSET_ID," + " QUANTITY," + " BATCHID,"
					+ " MATCH_DATE," + " FLAG," + " USER_ID,"
					+ " CREATION_DATE," + " CREATED_BY," + " LAST_UPDATE_DATE,"
					+ " LAST_UPDATE_BY" + " WHERE" + " ROWNUM = 1";

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} else {
			String sqlStr = "SELECT " + " SYSTEMID," + " ITEM_ATTR,"
					+ " ASSET_ID," + " QUANTITY," + " BATCHID,"
					+ " MATCH_DATE," + " FLAG," + " USER_ID,"
					+ " CREATION_DATE," + " CREATED_BY," + " LAST_UPDATE_DATE,"
					+ " LAST_UPDATE_BY" + " WHERE" + " ROWNUM = 1";

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成资产匹配-匹配数据存储(EAM) ETS_ITEM_MATCH页面翻页查询SQLModel，请根据实际需要修改。
	 * 
	 * @return SQLModel 返回页面翻页查询SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String code = userAccount.getCompanyCode();
		BarcodeMatchDTO dto = (BarcodeMatchDTO) dtoParameter;

		if ("Y".equalsIgnoreCase(userAccount.getIsTd())) { // TD资产
			if (dto.getMatch().equals("N")) {
				String sqlStr = "SELECT \n"
						+ " EII.BARCODE,   \n"
						+ // --AMS条码
						" EFTA.TAG_NUMBER FA_BARCODE,   \n"
						+ // --资产条码号
						" ESI.ITEM_NAME ITEM_DESCRIPTION,  \n"
						+ // -- EAM设备名称
						" ESI.ITEM_SPEC SPEC,  \n"
						+ // --EAM设备型号
						" EFTA.ASSETS_DESCRIPTION, \n"
						+ // MIS资产描述
						" EFTA.CURRENT_UNITS, \n"
						+ // --MIS资产数量
						" EFTA.MODEL_NUMBER MIS_SPEC, \n"
						+ // MIS设备型号
						" EO.WORKORDER_OBJECT_CODE,\n"
						+ // EAM地点代码
						" EFTA.ASSETS_LOCATION_CODE,\n"
						+ // MIS地点代码
						" EO.WORKORDER_OBJECT_LOCATION ETS_LOCATION, \n"
						+ // EAM地点
						" EFTA.ASSETS_LOCATION MIS_LOCATION, \n"
						+ // MIS地点
						" EII.SYSTEMID,\n"
						+ // EAM系统ID
						" EFTA.ASSET_ID,\n"
						+ // MIS资产ID
						" AME.USER_NAME,\n"
						+ // EAM员工姓名
						" EFTA.ASSIGNED_TO_NAME,\n"
						+ // MIS责任人
						" EO.ORGANIZATION_ID," +
                        " EFTA.SEGMENT1||'.'||EFTA.SEGMENT2||'.'||EFTA.SEGMENT3 FA_CODE ,\n" +
                        "         EFTA.FA_CATEGORY2,\n" +
                        "         EII.CONTENT_CODE,\n" +
                        "         EII.CONTENT_NAME \n"
						+ // EAM组织id
						" FROM "
						// " ETS_FA_ASSETS EFA,\n" +
						+ " ETS_FA_ASSETS_TD 	EFTA,\n"

						+ " ETS_OBJECT         	EO,\n"
						+ " AMS_OBJECT_ADDRESS 	AOA,\n"
						+ " ETS_ITEM_INFO      	EII,\n"
						+ " ETS_SYSTEM_ITEM    	ESI,\n"
						+ " AMS_MIS_EMPLOYEE 	AME,\n"
						+ " AMS_MIS_DEPT 		AMD\n"
						+ " WHERE"
						+ " AOA.ADDRESS_ID = EII.ADDRESS_ID\n"
						+ // 组合地点
						" AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n"
						+ // 地点ID
						" AND ESI.ITEM_CODE = EII.ITEM_CODE\n"
						+ " AND EII.BARCODE = EFTA.TAG_NUMBER\n"
						+ " AND EII.RESPONSIBILITY_USER = AME.EMPLOYEE_ID\n"
						+ " AND (EII.DISABLE_DATE IS NULL OR EII.DISABLE_DATE = '')  \n"
						+ // 失效日期为空
						" AND EII.ITEM_STATUS <>'DISCARDED'  \n"
						+ // 非报废（EAM）
                        " AND EFTA.COST <> 0\n"
//						+ " AND EII.BARCODE LIKE '"
//						+ code
//						+ "%'\n"
						+ " AND (EFTA.IS_RETIREMENTS = 0 OR EFTA.IS_RETIREMENTS = 2) \n"
						+ // 非报废
						" AND EO.IS_TEMP_ADDR = 0  \n"
						+ // 非临时地点
						" AND EII.FINANCE_PROP <> 'OTHER'  \n"
						+ " AND AMD.DEPT_CODE = EII.RESPONSIBILITY_DEPT\n";
					    if(userAccount.isDptAssetsManager() && !userAccount.isComAssetsManager()){
                        	DTOSet depts = userAccount.getPriviDeptCodes();
                        	AmsMisDeptDTO dept = null;
                            if( null!= depts ){
	                        	String deptCodes = "(";
	                            for (int i = 0; i < depts.getSize(); i++) {
	                            	dept = (AmsMisDeptDTO) depts.getDTO(i);
	                            	deptCodes += "'" + dept.getDeptCode() + "', ";
	                            }
	                            deptCodes += "'')";
	                            sqlStr += " AND EII.RESPONSIBILITY_DEPT IN " + deptCodes;
                            }
					    }
						// " AND NOT EXISTS( SELECT 1 FROM ETS_ITEM_MATCH WHERE
						// SYSTEMID = EII.SYSTEMID OR ASSET_ID=EFTA.ASSET_ID)\n"
						// +
						sqlStr+=
						" AND NOT EXISTS( SELECT 1 FROM ETS_ITEM_MATCH_TD WHERE  SYSTEMID = EII.SYSTEMID )\n" +
						" AND NOT EXISTS( SELECT 1 FROM ETS_ITEM_MATCH_TD WHERE ASSET_ID = EFTA.ASSET_ID) \n" +
						//" AND NOT EXISTS( SELECT 1 FROM ETS_NO_MATCH ENM
						// WHERE ENM.SYSTEMID = EII.SYSTEMID OR ENM.ASSET_ID =
						// EFTA.ASSET_ID)" + //在ETS_NO_MATCH有的资产不参加匹配
						" AND NOT EXISTS( SELECT 1 FROM ETS_NO_MATCH_TD ENM WHERE ENM.SYSTEMID = EII.SYSTEMID )" +
                        " AND NOT EXISTS( SELECT 1 FROM ETS_ITEM_MATCH_TD WHERE ASSET_ID = EFTA.ASSET_ID) \n" +
						// 在ETS_NO_MATCH有的资产不参加匹配
                        
                        " AND NOT EXISTS (SELECT NULL FROM AMS_IGNORE_DATA H WHERE EFTA.SEGMENT2 = H.SEGMENT AND EFTA.SEGMENT3 = H.NAME) \n" +

						" AND EII.ORGANIZATION_ID = ?\n" + 
						" AND EFTA.ORGANIZATION_ID = ?\n" +
						" AND (CONVERT(INT, EO.OBJECT_CATEGORY) < = 70 OR CONVERT(INT, EO.OBJECT_CATEGORY) = 80)\n";

				if (servletConfig.getProvinceCode().equals(DictConstant.PROVINCE_CODE_JIN)) {
					sqlStr += " AND EII.BARCODE IN (\n"
							+ " SELECT BARCODE\n"
							+ "  FROM (SELECT EWDD.BARCODE\n"
							+ // 巡检
							"          FROM ETS_WORKORDER_DIFF_DTL EWDD, ETS_WORKORDER EW\n"
							+ "         WHERE EWDD.WORKORDER_NO = EW.WORKORDER_NO\n"
							+ "           AND EW.WORKORDER_FLAG = 14\n"
							+ "           AND EWDD.VERIFY_RESULT = '扫描结果为准'\n"
							+ "        UNION ALL\n"
							+ "        SELECT EWDD.BARCODE\n"
							+ "          FROM ETS_WORKORDER_DIFF_DTL EWDD, ETS_WORKORDER EW\n"
							+ "         WHERE EWDD.WORKORDER_NO = EW.WORKORDER_NO\n"
							+ "           AND EWDD.VERIFY_RESULT = '系统数据为准'\n"
							+ "           AND NOT EXISTS (SELECT 1\n"
							+ "                  FROM ETS_WORKORDER_DTL EWD\n"
							+ "                 WHERE EWDD.WORKORDER_NO = EWD.WORKORDER_NO\n"
							+ "                   AND EWDD.BARCODE = EWD.BARCODE)\n"
							+ "        UNION ALL\n"
							+ "        SELECT EWD.BARCODE\n"
							+ "          FROM ETS_WORKORDER_DTL EWD, ETS_WORKORDER EW\n"
							+ "         WHERE EWD.WORKORDER_NO = EW.WORKORDER_NO\n"
							+ "           AND EWD.ITEM_STATUS < 6\n"
							+ "           AND NOT EXISTS (SELECT 1\n"
							+ "                  FROM ETS_WORKORDER_DIFF_DTL EWDD\n"
							+ "                 WHERE EWDD.WORKORDER_NO = EWD.WORKORDER_NO\n"
							+ "                   AND EWD.BARCODE = EWDD.BARCODE)\n"
							+ "         UNION ALL\n"
							+ "         SELECT AACL.BARCODE\n"
							// 盘点
							+ "          FROM AMS_ASSETS_CHK_LOG AACL\n"
							+ "         WHERE AACL.IS_EXIST = 'Y'))\n";
				}
				
                if(dto.getNameSame().equals("Y")){
                    sqlStr+="AND EFTA.ASSETS_DESCRIPTION = ESI.ITEM_NAME \n" ;
                }
                if(dto.getSpecSame().equals("Y")){
                    sqlStr+="AND EFTA.MODEL_NUMBER = ESI.ITEM_SPEC \n" ;
                }
                if(dto.getResSame().equals("Y")){
                    sqlStr+="AND EFTA.ASSIGNED_TO_NAME = AME.USER_NAME \n";
                }
                if(dto.getLocSame().equals("Y")){
                    sqlStr+="AND EFTA.ASSETS_LOCATION = EO.WORKORDER_OBJECT_LOCATION \n" +
                            "AND EFTA.ASSETS_LOCATION_CODE = EO.LOCATION_CODE \n" ;
                }
                
                
                if(dto.getCheck().equals("Y")){
                    sqlStr+="  AND EXISTS (SELECT 'A'\n" +
                            "          FROM AMS_ASSETS_CHK_LOG ACL\n" +
                            "         WHERE ACL.BARCODE = EII.BARCODE\n" +
                            "           AND ACL.IS_EXIST = 'Y')";
                }
                sqlStr += "   AND (? ='' OR  ESI.ITEM_NAME LIKE ?)"
						+ "   AND (? ='' OR ESI.ITEM_SPEC LIKE ?)"
						+ "   AND (? ='' OR EII.BARCODE LIKE ?)"
						+ "   AND ((? ='' OR EFTA.ASSIGNED_TO_NAME LIKE ?) OR (? ='' OR AME.USER_NAME LIKE ?))"
						+ "   AND (? =''OR  EFTA.DEPRECIATION_ACCOUNT LIKE '%'||?||'%')\n"
						+ "   AND ((? ='' OR EO.WORKORDER_OBJECT_CODE LIKE ?)"
						+ "   OR (? ='' OR EO.WORKORDER_OBJECT_LOCATION LIKE ?))"
						+ "   ORDER BY EO.WORKORDER_OBJECT_CODE DESC,ESI.ITEM_NAME,ESI.ITEM_SPEC";

				sqlArgs.add(userAccount.getOrganizationId());
				sqlArgs.add(userAccount.getOrganizationId());
				sqlArgs.add(dto.getItemName());
				sqlArgs.add(dto.getItemName());
				sqlArgs.add(dto.getItemSpec());
				sqlArgs.add(dto.getItemSpec());
				sqlArgs.add(dto.getBarcode());
				sqlArgs.add(dto.getBarcode());
				sqlArgs.add(dto.getResponsibilityUser());
				sqlArgs.add(dto.getResponsibilityUser());
				sqlArgs.add(dto.getResponsibilityUser());
				sqlArgs.add(dto.getResponsibilityUser());
				sqlArgs.add(dto.getCostCenter());
				sqlArgs.add(dto.getCostCenter());

				sqlArgs.add(dto.getWorkorderObjectLocation());
				sqlArgs.add(dto.getWorkorderObjectLocation());
				sqlArgs.add(dto.getWorkorderObjectLocation());
				sqlArgs.add(dto.getWorkorderObjectLocation());
				sqlModel.setSqlStr(sqlStr);
				sqlModel.setArgs(sqlArgs);
			} else if (dto.getMatch().equals("Y")) {
				String sqlStr = "SELECT "
						+ " EII.BARCODE,\n"
						+ " EII.BARCODE FA_BARCODE,\n"
						+ " ESI.ITEM_NAME ITEM_DESCRIPTION,\n"
						+ " ESI.ITEM_SPEC SPEC,\n"
						+ " EFTA.ASSETS_DESCRIPTION,\n"
						+ " EFTA.CURRENT_UNITS,\n"
						+ " EFTA.MODEL_NUMBER MIS_SPEC,\n"
						+ " EO.WORKORDER_OBJECT_CODE,\n"
						+ " EFTA.ASSETS_LOCATION_CODE,\n"
						+ " EO.WORKORDER_OBJECT_LOCATION ETS_LOCATION,\n"
						+ " EFTA.ASSETS_LOCATION MIS_LOCATION,\n"
						+ " EII.SYSTEMID,\n"
						+ " AME.USER_NAME,\n"
						+ " EFTA.ASSIGNED_TO_NAME,\n"
						+ " EFTA.ASSET_ID,\n"
						+ " EO.ORGANIZATION_ID," 
                        + " EFTA.SEGMENT1||'.'||EFTA.SEGMENT2||'.'||EFTA.SEGMENT3 FA_CODE ,\n" 
                        + " EFTA.FA_CATEGORY2,\n" 
                        + " EII.CONTENT_CODE,\n" 
                        + " EII.CONTENT_NAME\n"
						+ " FROM "
						+ " ETS_ITEM_INFO      EII,\n"
						+ " ETS_SYSTEM_ITEM    ESI,\n"
						+ " ETS_FA_ASSETS_TD   EFTA,\n"
						+ " AMS_OBJECT_ADDRESS AOA,\n"
						+ " ETS_OBJECT         EO,\n"
						+ " AMS_MIS_EMPLOYEE   AME,\n"
						+ " AMS_MIS_DEPT       AMD,\n"
						// " ETS_ITEM_MATCH_REC EIM\n" +
						+ " ETS_ITEM_MATCH_TD_REC EIMTR\n"
						+ " WHERE "
						+ " EIMTR.MATCH_TYPE = '8'\n"
						+ " AND EIMTR.SYSTEM_ID = EII.SYSTEMID\n"
						+ " AND ESI.ITEM_CODE = EII.ITEM_CODE\n"
						+ " AND EII.BARCODE = EFTA.TAG_NUMBER\n"
						+ " AND AOA.ADDRESS_ID = EII.ADDRESS_ID\n"
						+ " AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n"
						+ " AND EII.RESPONSIBILITY_USER = AME.EMPLOYEE_ID\n"
						+ " AND AMD.DEPT_CODE = EII.RESPONSIBILITY_DEPT\n"  ;
                        if(userAccount.isDptAssetsManager() && !userAccount.isComAssetsManager()){
                                DTOSet depts = userAccount.getPriviDeptCodes();
                              AmsMisDeptDTO dept = null;
                        	if( null != depts ){
	                        	String deptCodes = "(";
	                        	for (int i = 0; i < depts.getSize(); i++) {
	                        		dept = (AmsMisDeptDTO) depts.getDTO(i);
	                        		deptCodes += "'" + dept.getDeptCode() + "', ";
	                        	}
	                        	deptCodes += "'')";
	                        	sqlStr += " AND EII.RESPONSIBILITY_DEPT IN " + deptCodes;
                            }
                        }
                        sqlStr += " AND EO.ORGANIZATION_ID = ?"
						+ " AND EII.BARCODE LIKE dbo.NVL(?,EII.BARCODE)"
						+ " AND (? ='' OR  ESI.ITEM_NAME LIKE ?)"
						+ " AND (? ='' OR ESI.ITEM_SPEC LIKE ?)"
						+ " AND ((? ='' OR EFTA.ASSIGNED_TO_NAME LIKE ?) OR (? ='' OR AME.USER_NAME LIKE ?))"
						+ " AND (? ='' OR EO.WORKORDER_OBJECT_LOCATION LIKE ?)";
		                /*if(dto.getSame().equals("Y")){
		                	sqlStr+="AND EFTA.ASSETS_DESCRIPTION = ESI.ITEM_NAME \n" +
		                            "AND EFTA.MODEL_NUMBER = ESI.ITEM_SPEC\n" +
		                            "AND EFTA.ASSETS_LOCATION = EO.WORKORDER_OBJECT_LOCATION \n" +
		                            "AND EFTA.ASSETS_LOCATION_CODE = EO.LOCATION_CODE \n" +
		                            "AND EFTA.ASSIGNED_TO_NAME = AME.USER_NAME \n";
		                }*/
                        
                        if(dto.getNameSame().equals("Y")){
                            sqlStr+="AND EFTA.ASSETS_DESCRIPTION = ESI.ITEM_NAME \n" ;
                        }
                        if(dto.getSpecSame().equals("Y")){
                            sqlStr+="AND EFTA.MODEL_NUMBER = ESI.ITEM_SPEC \n" ;
                        }
                        if(dto.getResSame().equals("Y")){
                            sqlStr+="AND EFTA.ASSIGNED_TO_NAME = AME.USER_NAME \n";
                        }
                        if(dto.getLocSame().equals("Y")){
                            sqlStr+="AND EFTA.ASSETS_LOCATION = EO.WORKORDER_OBJECT_LOCATION \n" +
                                    "AND EFTA.ASSETS_LOCATION_CODE = EO.LOCATION_CODE \n" ;
                        }   
                        
                sqlArgs.add(userAccount.getOrganizationId());
				sqlArgs.add(dto.getBarcode());
				sqlArgs.add(dto.getItemName());
				sqlArgs.add(dto.getItemName());
				sqlArgs.add(dto.getItemSpec());
				sqlArgs.add(dto.getItemSpec());
				sqlArgs.add(dto.getResponsibilityUser());
				sqlArgs.add(dto.getResponsibilityUser());
				sqlArgs.add(dto.getResponsibilityUser());
				sqlArgs.add(dto.getResponsibilityUser());
				sqlArgs.add(dto.getWorkorderObjectLocation());
				sqlArgs.add(dto.getWorkorderObjectLocation());

				sqlModel.setSqlStr(sqlStr);
				sqlModel.setArgs(sqlArgs);
			}
		}else if  ("Y".equalsIgnoreCase(userAccount.getIsTt())) {
            if (dto.getMatch().equals("N")) {
                            String sqlStr = "SELECT \n"
                                    + " EII.BARCODE,   \n"
                                    + // --AMS条码
                                    " EFTA.TAG_NUMBER FA_BARCODE,   \n"
                                    + // --资产条码号
                                    " ESI.ITEM_NAME ITEM_DESCRIPTION,  \n"
                                    + // -- EAM设备名称
                                    " ESI.ITEM_SPEC SPEC,  \n"
                                    + // --EAM设备型号
                                    " EFTA.ASSETS_DESCRIPTION, \n"
                                    + // MIS资产描述
                                    " EFTA.CURRENT_UNITS, \n"
                                    + // --MIS资产数量
                                    " EFTA.MODEL_NUMBER MIS_SPEC, \n"
                                    + // MIS设备型号
                                    " EO.WORKORDER_OBJECT_CODE,\n"
                                    + // EAM地点代码
                                    " EFTA.ASSETS_LOCATION_CODE,\n"
                                    + // MIS地点代码
                                    " EO.WORKORDER_OBJECT_LOCATION ETS_LOCATION, \n"
                                    + // EAM地点
                                    " EFTA.ASSETS_LOCATION MIS_LOCATION, \n"
                                    + // MIS地点
                                    " EII.SYSTEMID,\n"
                                    + // EAM系统ID
                                    " EFTA.ASSET_ID,\n"
                                    + // MIS资产ID
                                    " AME.USER_NAME,\n"
                                    + // EAM员工姓名
                                    " EFTA.ASSIGNED_TO_NAME,\n"
                                    + // MIS责任人
                                    " EO.ORGANIZATION_ID," +
                                    " EFTA.SEGMENT1||'.'||EFTA.SEGMENT2||'.'||EFTA.SEGMENT3 FA_CODE ,\n" +
                                    "         EFTA.FA_CATEGORY2,\n" +
                                    "         EII.CONTENT_CODE,\n" +
                                    "         EII.CONTENT_NAME \n"
                                    + // EAM组织id
                                    " FROM "
                                    // " ETS_FA_ASSETS EFA,\n" +
                                    + " ETS_FA_ASSETS_TD 	EFTA,\n"

                                    + " ETS_OBJECT         	EO,\n"
                                    + " AMS_OBJECT_ADDRESS 	AOA,\n"
                                    + " ETS_ITEM_INFO      	EII,\n"
                                    + " ETS_SYSTEM_ITEM    	ESI,\n"
                                    + " AMS_MIS_EMPLOYEE 	AME,\n"
                                    + " AMS_MIS_DEPT 		AMD\n"
                                    + " WHERE"
                                    + " AOA.ADDRESS_ID = EII.ADDRESS_ID\n"
                                    + // 组合地点
                                    " AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n"
                                    + // 地点ID
                                    " AND ESI.ITEM_CODE = EII.ITEM_CODE\n"
                                    + " AND EII.BARCODE = EFTA.TAG_NUMBER\n"
                                    + " AND EII.RESPONSIBILITY_USER = AME.EMPLOYEE_ID\n"
                                    + " AND (EII.DISABLE_DATE IS NULL OR EII.DISABLE_DATE = '')  \n"
                                    + // 失效日期为空
                                    " AND EII.ITEM_STATUS <>'DISCARDED'  \n"
                                    + // 非报废（EAM）
                                    " AND EFTA.COST <> 0\n"
//						+ " AND EII.BARCODE LIKE '"
//						+ code
//						+ "%'\n"
                                    + " AND (EFTA.IS_RETIREMENTS = 0 OR EFTA.IS_RETIREMENTS = 2) \n"
                                    + // 非报废
                                    " AND EO.IS_TEMP_ADDR = 0  \n"
                                    + // 非临时地点
                                    " AND EII.FINANCE_PROP <> 'OTHER'  \n"
                                    + " AND AMD.DEPT_CODE = EII.RESPONSIBILITY_DEPT\n";
                                    if(userAccount.isDptAssetsManager() && !userAccount.isComAssetsManager()){
                                        DTOSet depts = userAccount.getPriviDeptCodes();
                                        AmsMisDeptDTO dept = null;
                                        if( null!= depts ){
                                            String deptCodes = "(";
                                            for (int i = 0; i < depts.getSize(); i++) {
                                                dept = (AmsMisDeptDTO) depts.getDTO(i);
                                                deptCodes += "'" + dept.getDeptCode() + "', ";
                                            }
                                            deptCodes += "'')";
                                            sqlStr += " AND EII.RESPONSIBILITY_DEPT IN " + deptCodes;
                                        }
                                    }
                                    // " AND NOT EXISTS( SELECT 1 FROM ETS_ITEM_MATCH WHERE
                                    // SYSTEMID = EII.SYSTEMID OR ASSET_ID=EFTA.ASSET_ID)\n"
                                    // +
                                    sqlStr+=
                                    " AND NOT EXISTS( SELECT 1 FROM ETS_ITEM_MATCH_TD WHERE  SYSTEMID = EII.SYSTEMID )\n" +
                                    " AND NOT EXISTS( SELECT 1 FROM ETS_ITEM_MATCH_TD WHERE ASSET_ID = EFTA.ASSET_ID) \n" +
                                    //" AND NOT EXISTS( SELECT 1 FROM ETS_NO_MATCH ENM
                                    // WHERE ENM.SYSTEMID = EII.SYSTEMID OR ENM.ASSET_ID =
                                    // EFTA.ASSET_ID)" + //在ETS_NO_MATCH有的资产不参加匹配
                                    " AND NOT EXISTS( SELECT 1 FROM ETS_NO_MATCH_TD ENM WHERE ENM.SYSTEMID = EII.SYSTEMID )" +
                                    " AND NOT EXISTS( SELECT 1 FROM ETS_ITEM_MATCH_TD WHERE ASSET_ID = EFTA.ASSET_ID) \n" +
                                    // 在ETS_NO_MATCH有的资产不参加匹配

                                    " AND NOT EXISTS (SELECT NULL FROM AMS_IGNORE_DATA H WHERE EFTA.SEGMENT2 = H.SEGMENT AND EFTA.SEGMENT3 = H.NAME) \n" +

                                    " AND EII.ORGANIZATION_ID = ?\n" +
                                    " AND EFTA.ORGANIZATION_ID = ?\n" +
                                    " AND (CONVERT(INT, EO.OBJECT_CATEGORY) < = 70 OR CONVERT(INT, EO.OBJECT_CATEGORY) = 80)\n";

                            if (servletConfig.getProvinceCode().equals(DictConstant.PROVINCE_CODE_JIN)) {
                                sqlStr += " AND EII.BARCODE IN (\n"
                                        + " SELECT BARCODE\n"
                                        + "  FROM (SELECT EWDD.BARCODE\n"
                                        + // 巡检
                                        "          FROM ETS_WORKORDER_DIFF_DTL EWDD, ETS_WORKORDER EW\n"
                                        + "         WHERE EWDD.WORKORDER_NO = EW.WORKORDER_NO\n"
                                        + "           AND EW.WORKORDER_FLAG = 14\n"
                                        + "           AND EWDD.VERIFY_RESULT = '扫描结果为准'\n"
                                        + "        UNION ALL\n"
                                        + "        SELECT EWDD.BARCODE\n"
                                        + "          FROM ETS_WORKORDER_DIFF_DTL EWDD, ETS_WORKORDER EW\n"
                                        + "         WHERE EWDD.WORKORDER_NO = EW.WORKORDER_NO\n"
                                        + "           AND EWDD.VERIFY_RESULT = '系统数据为准'\n"
                                        + "           AND NOT EXISTS (SELECT 1\n"
                                        + "                  FROM ETS_WORKORDER_DTL EWD\n"
                                        + "                 WHERE EWDD.WORKORDER_NO = EWD.WORKORDER_NO\n"
                                        + "                   AND EWDD.BARCODE = EWD.BARCODE)\n"
                                        + "        UNION ALL\n"
                                        + "        SELECT EWD.BARCODE\n"
                                        + "          FROM ETS_WORKORDER_DTL EWD, ETS_WORKORDER EW\n"
                                        + "         WHERE EWD.WORKORDER_NO = EW.WORKORDER_NO\n"
                                        + "           AND EWD.ITEM_STATUS < 6\n"
                                        + "           AND NOT EXISTS (SELECT 1\n"
                                        + "                  FROM ETS_WORKORDER_DIFF_DTL EWDD\n"
                                        + "                 WHERE EWDD.WORKORDER_NO = EWD.WORKORDER_NO\n"
                                        + "                   AND EWD.BARCODE = EWDD.BARCODE)\n"
                                        + "         UNION ALL\n"
                                        + "         SELECT AACL.BARCODE\n"
                                        // 盘点
                                        + "          FROM AMS_ASSETS_CHK_LOG AACL\n"
                                        + "         WHERE AACL.IS_EXIST = 'Y'))\n";
                            }

                            if(dto.getNameSame().equals("Y")){
                                sqlStr+="AND EFTA.ASSETS_DESCRIPTION = ESI.ITEM_NAME \n" ;
                            }
                            if(dto.getSpecSame().equals("Y")){
                                sqlStr+="AND EFTA.MODEL_NUMBER = ESI.ITEM_SPEC \n" ;
                            }
                            if(dto.getResSame().equals("Y")){
                                sqlStr+="AND EFTA.ASSIGNED_TO_NAME = AME.USER_NAME \n";
                            }
                            if(dto.getLocSame().equals("Y")){
                                sqlStr+="AND EFTA.ASSETS_LOCATION = EO.WORKORDER_OBJECT_LOCATION \n" +
                                        "AND EFTA.ASSETS_LOCATION_CODE = EO.LOCATION_CODE \n" ;
                            }


                            if(dto.getCheck().equals("Y")){
                                sqlStr+="  AND EXISTS (SELECT 'A'\n" +
                                        "          FROM AMS_ASSETS_CHK_LOG ACL\n" +
                                        "         WHERE ACL.BARCODE = EII.BARCODE\n" +
                                        "           AND ACL.IS_EXIST = 'Y')";
                            }
                            sqlStr += "   AND (? ='' OR  ESI.ITEM_NAME LIKE ?)"
                                    + "   AND (? ='' OR ESI.ITEM_SPEC LIKE ?)"
                                    + "   AND (? ='' OR EII.BARCODE LIKE ?)"
                                    + "   AND ((? ='' OR EFTA.ASSIGNED_TO_NAME LIKE ?) OR (? ='' OR AME.USER_NAME LIKE ?))"
                                    + "   AND (? =''OR  EFTA.DEPRECIATION_ACCOUNT LIKE '%'||?||'%')\n"
                                    + "   AND ((? ='' OR EO.WORKORDER_OBJECT_CODE LIKE ?)"
                                    + "   OR (? ='' OR EO.WORKORDER_OBJECT_LOCATION LIKE ?))"
                                    + "   ORDER BY EO.WORKORDER_OBJECT_CODE DESC,ESI.ITEM_NAME,ESI.ITEM_SPEC";

                            sqlArgs.add(userAccount.getOrganizationId());
                            sqlArgs.add(userAccount.getOrganizationId());
                            sqlArgs.add(dto.getItemName());
                            sqlArgs.add(dto.getItemName());
                            sqlArgs.add(dto.getItemSpec());
                            sqlArgs.add(dto.getItemSpec());
                            sqlArgs.add(dto.getBarcode());
                            sqlArgs.add(dto.getBarcode());
                            sqlArgs.add(dto.getResponsibilityUser());
                            sqlArgs.add(dto.getResponsibilityUser());
                            sqlArgs.add(dto.getResponsibilityUser());
                            sqlArgs.add(dto.getResponsibilityUser());
                            sqlArgs.add(dto.getCostCenter());
                            sqlArgs.add(dto.getCostCenter());

                            sqlArgs.add(dto.getWorkorderObjectLocation());
                            sqlArgs.add(dto.getWorkorderObjectLocation());
                            sqlArgs.add(dto.getWorkorderObjectLocation());
                            sqlArgs.add(dto.getWorkorderObjectLocation());
                            sqlModel.setSqlStr(sqlStr);
                            sqlModel.setArgs(sqlArgs);
                        } else if (dto.getMatch().equals("Y")) {
                            String sqlStr = "SELECT "
                                    + " EII.BARCODE,\n"
                                    + " EII.BARCODE FA_BARCODE,\n"
                                    + " ESI.ITEM_NAME ITEM_DESCRIPTION,\n"
                                    + " ESI.ITEM_SPEC SPEC,\n"
                                    + " EFTA.ASSETS_DESCRIPTION,\n"
                                    + " EFTA.CURRENT_UNITS,\n"
                                    + " EFTA.MODEL_NUMBER MIS_SPEC,\n"
                                    + " EO.WORKORDER_OBJECT_CODE,\n"
                                    + " EFTA.ASSETS_LOCATION_CODE,\n"
                                    + " EO.WORKORDER_OBJECT_LOCATION ETS_LOCATION,\n"
                                    + " EFTA.ASSETS_LOCATION MIS_LOCATION,\n"
                                    + " EII.SYSTEMID,\n"
                                    + " AME.USER_NAME,\n"
                                    + " EFTA.ASSIGNED_TO_NAME,\n"
                                    + " EFTA.ASSET_ID,\n"
                                    + " EO.ORGANIZATION_ID,"
                                    + " EFTA.SEGMENT1||'.'||EFTA.SEGMENT2||'.'||EFTA.SEGMENT3 FA_CODE ,\n"
                                    + " EFTA.FA_CATEGORY2,\n"
                                    + " EII.CONTENT_CODE,\n"
                                    + " EII.CONTENT_NAME\n"
                                    + " FROM "
                                    + " ETS_ITEM_INFO      EII,\n"
                                    + " ETS_SYSTEM_ITEM    ESI,\n"
                                    + " ETS_FA_ASSETS_TD   EFTA,\n"
                                    + " AMS_OBJECT_ADDRESS AOA,\n"
                                    + " ETS_OBJECT         EO,\n"
                                    + " AMS_MIS_EMPLOYEE   AME,\n"
                                    + " AMS_MIS_DEPT       AMD,\n"
                                    // " ETS_ITEM_MATCH_REC EIM\n" +
                                    + " ETS_ITEM_MATCH_TD_REC EIMTR\n"
                                    + " WHERE "
                                    + " EIMTR.MATCH_TYPE = '8'\n"
                                    + " AND EIMTR.SYSTEM_ID = EII.SYSTEMID\n"
                                    + " AND ESI.ITEM_CODE = EII.ITEM_CODE\n"
                                    + " AND EII.BARCODE = EFTA.TAG_NUMBER\n"
                                    + " AND AOA.ADDRESS_ID = EII.ADDRESS_ID\n"
                                    + " AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n"
                                    + " AND EII.RESPONSIBILITY_USER = AME.EMPLOYEE_ID\n"
                                    + " AND AMD.DEPT_CODE = EII.RESPONSIBILITY_DEPT\n"  ;
                                    if(userAccount.isDptAssetsManager() && !userAccount.isComAssetsManager()){
                                            DTOSet depts = userAccount.getPriviDeptCodes();
                                          AmsMisDeptDTO dept = null;
                                        if( null != depts ){
                                            String deptCodes = "(";
                                            for (int i = 0; i < depts.getSize(); i++) {
                                                dept = (AmsMisDeptDTO) depts.getDTO(i);
                                                deptCodes += "'" + dept.getDeptCode() + "', ";
                                            }
                                            deptCodes += "'')";
                                            sqlStr += " AND EII.RESPONSIBILITY_DEPT IN " + deptCodes;
                                        }
                                    }
                                    sqlStr += " AND EO.ORGANIZATION_ID = ?"
                                    + " AND EII.BARCODE LIKE dbo.NVL(?,EII.BARCODE)"
                                    + " AND (? ='' OR  ESI.ITEM_NAME LIKE ?)"
                                    + " AND (? ='' OR ESI.ITEM_SPEC LIKE ?)"
                                    + " AND ((? ='' OR EFTA.ASSIGNED_TO_NAME LIKE ?) OR (? ='' OR AME.USER_NAME LIKE ?))"
                                    + " AND (? ='' OR EO.WORKORDER_OBJECT_LOCATION LIKE ?)";
                                    /*if(dto.getSame().equals("Y")){
                                        sqlStr+="AND EFTA.ASSETS_DESCRIPTION = ESI.ITEM_NAME \n" +
                                                "AND EFTA.MODEL_NUMBER = ESI.ITEM_SPEC\n" +
                                                "AND EFTA.ASSETS_LOCATION = EO.WORKORDER_OBJECT_LOCATION \n" +
                                                "AND EFTA.ASSETS_LOCATION_CODE = EO.LOCATION_CODE \n" +
                                                "AND EFTA.ASSIGNED_TO_NAME = AME.USER_NAME \n";
                                    }*/

                                    if(dto.getNameSame().equals("Y")){
                                        sqlStr+="AND EFTA.ASSETS_DESCRIPTION = ESI.ITEM_NAME \n" ;
                                    }
                                    if(dto.getSpecSame().equals("Y")){
                                        sqlStr+="AND EFTA.MODEL_NUMBER = ESI.ITEM_SPEC \n" ;
                                    }
                                    if(dto.getResSame().equals("Y")){
                                        sqlStr+="AND EFTA.ASSIGNED_TO_NAME = AME.USER_NAME \n";
                                    }
                                    if(dto.getLocSame().equals("Y")){
                                        sqlStr+="AND EFTA.ASSETS_LOCATION = EO.WORKORDER_OBJECT_LOCATION \n" +
                                                "AND EFTA.ASSETS_LOCATION_CODE = EO.LOCATION_CODE \n" ;
                                    }

                            sqlArgs.add(userAccount.getOrganizationId());
                            sqlArgs.add(dto.getBarcode());
                            sqlArgs.add(dto.getItemName());
                            sqlArgs.add(dto.getItemName());
                            sqlArgs.add(dto.getItemSpec());
                            sqlArgs.add(dto.getItemSpec());
                            sqlArgs.add(dto.getResponsibilityUser());
                            sqlArgs.add(dto.getResponsibilityUser());
                            sqlArgs.add(dto.getResponsibilityUser());
                            sqlArgs.add(dto.getResponsibilityUser());
                            sqlArgs.add(dto.getWorkorderObjectLocation());
                            sqlArgs.add(dto.getWorkorderObjectLocation());

                            sqlModel.setSqlStr(sqlStr);
                            sqlModel.setArgs(sqlArgs);
                        }

        } else { // 正常资产
			if (dto.getMatch().equals("N")) {
				String sqlStr = "SELECT \n"
						+ " EII.BARCODE,   \n"
						// --AMS条码
						+ " EFA.TAG_NUMBER FA_BARCODE,   \n"
						// --资产条码号
						+ " ESI.ITEM_NAME ITEM_DESCRIPTION,  \n"
						// -- EAM设备名称
						+ " ESI.ITEM_SPEC SPEC,  \n"
						// --EAM设备型号
						+ " EFA.ASSETS_DESCRIPTION, \n"
						// MIS资产描述
						+ " EFA.CURRENT_UNITS, \n"
						// --MIS资产数量
						+ " EFA.MODEL_NUMBER MIS_SPEC, \n"
						// MIS设备型号
						+ " EO.WORKORDER_OBJECT_CODE,\n"
						+ " EFA.ASSETS_LOCATION_CODE,\n"
						+ " EO.WORKORDER_OBJECT_LOCATION ETS_LOCATION, \n"
						// AMS地点
						+ " EFA.ASSETS_LOCATION MIS_LOCATION, \n"
						// MIS地点
						+ " EII.SYSTEMID,\n"
						+ " EFA.ASSET_ID,\n"
						+ " AME.USER_NAME,\n"
						+ " EFA.ASSIGNED_TO_NAME,\n"
						+ " EO.ORGANIZATION_ID,"
						+ " EFA.SEGMENT1||'.'||EFA.SEGMENT2||'.'||EFA.SEGMENT3 FA_CODE ,\n"
                        + " EFA.FA_CATEGORY2,\n" 
                        + " EII.CONTENT_CODE,\n"
                        + " EII.CONTENT_NAME\n"
						+ " FROM "
						+ " ETS_FA_ASSETS      EFA,\n"
						+ " ETS_OBJECT         EO,\n"
						+ " AMS_OBJECT_ADDRESS AOA,\n"
						+ " ETS_ITEM_INFO      EII,\n"
						+ " ETS_SYSTEM_ITEM    ESI,\n"
						+ " AMS_MIS_EMPLOYEE   AME,\n"
						+ " AMS_MIS_DEPT 	   AMD\n"
						+ " WHERE"
						+ " AOA.ADDRESS_ID = EII.ADDRESS_ID\n"
						+ " AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n"
						+ " AND ESI.ITEM_CODE = EII.ITEM_CODE\n"
						+ " AND EII.BARCODE = EFA.TAG_NUMBER\n"
						+ " AND EII.RESPONSIBILITY_USER = AME.EMPLOYEE_ID\n"
						+ " AND (EII.DISABLE_DATE IS NULL OR EII.DISABLE_DATE = '')  \n"
						// 失效日期为空
						+ " AND EII.ITEM_STATUS <>'DISCARDED'  \n"
						// 非报废（EAM）
						+ " AND EFA.COST <> 0\n"
//						+" AND EII.BARCODE LIKE '"
//						+ code
//						+ "%'\n"
						+ " AND (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2) \n"
						// 非报废
						+ " AND EO.IS_TEMP_ADDR = 0  \n"
						// 非临时地点
						+ " AND EII.FINANCE_PROP <> 'OTHER'  \n" ;
                   		if(userAccount.isDptAssetsManager() && !userAccount.isComAssetsManager()){
                   			DTOSet depts = userAccount.getPriviDeptCodes();
                            AmsMisDeptDTO dept = null;
                            if( null != depts ){
                            String deptCodes = "(";
                            for (int i = 0; i < depts.getSize(); i++) {
                            	dept = (AmsMisDeptDTO) depts.getDTO(i);
                                deptCodes += "'" + dept.getDeptCode() + "', ";
                            }
                            deptCodes += "'')";
                            sqlStr += " AND EII.RESPONSIBILITY_DEPT IN " + deptCodes;
                            }
                   		}
                        sqlStr+= " AND AMD.DEPT_CODE = EII.RESPONSIBILITY_DEPT\n"
						+ " AND NOT EXISTS( SELECT 1 FROM ETS_ITEM_MATCH WHERE  SYSTEMID = EII.SYSTEMID )" 
                        + " AND NOT EXISTS( SELECT 1 FROM ETS_ITEM_MATCH WHERE ASSET_ID = EFA.ASSET_ID) \n"
						+ " AND NOT EXISTS( SELECT 1 FROM ETS_NO_MATCH ENM WHERE ENM.SYSTEMID = EII.SYSTEMID )" 
                        + " AND NOT EXISTS( SELECT 1 FROM ETS_ITEM_MATCH WHERE ASSET_ID = EFA.ASSET_ID) \n"
						// 在ETS_NO_MATCH有的资产不参加匹配
                        
                        + " AND NOT EXISTS (SELECT NULL FROM AMS_IGNORE_DATA H WHERE EFA.SEGMENT2 = H.SEGMENT AND EFA.SEGMENT3 = H.NAME) \n"
                        
						+ " AND EII.ORGANIZATION_ID = ?\n"
						+ " AND EFA.ORGANIZATION_ID = ?\n"
						+ " AND (CONVERT(INT, EO.OBJECT_CATEGORY) < = 70 OR CONVERT(INT, EO.OBJECT_CATEGORY) = 80)\n";

				if (servletConfig.getProvinceCode().equals(DictConstant.PROVINCE_CODE_JIN)) {
					sqlStr += " AND EII.BARCODE IN (\n"
							+ " SELECT C.BARCODE\n"
							+ "  FROM (SELECT EWDD.BARCODE\n"
							// 巡检
							+ "          FROM ETS_WORKORDER_DIFF_DTL EWDD, ETS_WORKORDER EW\n"
							+ "         WHERE EWDD.WORKORDER_NO = EW.WORKORDER_NO\n"
							+ "           AND EW.WORKORDER_FLAG = '14'\n"
							+ "           AND EWDD.VERIFY_RESULT = '扫描结果为准'\n"
							+ "        UNION ALL\n"
							+ "        SELECT EWDD.BARCODE\n"
							+ "          FROM ETS_WORKORDER_DIFF_DTL EWDD, ETS_WORKORDER EW\n"
							+ "         WHERE EWDD.WORKORDER_NO = EW.WORKORDER_NO\n"
							+ "           AND EWDD.VERIFY_RESULT = '系统数据为准'\n"
							+ "           AND NOT EXISTS (SELECT 1\n"
							+ "                  FROM ETS_WORKORDER_DTL EWD\n"
							+ "                 WHERE EWDD.WORKORDER_NO = EWD.WORKORDER_NO\n"
							+ "                   AND EWDD.BARCODE = EWD.BARCODE)\n"
							+ "        UNION ALL\n"
							+ "        SELECT EWD.BARCODE\n"
							+ "          FROM ETS_WORKORDER_DTL EWD, ETS_WORKORDER EW\n"
							+ "         WHERE EWD.WORKORDER_NO = EW.WORKORDER_NO\n"
							+ "           AND EWD.ITEM_STATUS < 6\n"
							+ "           AND NOT EXISTS (SELECT 1\n"
							+ "                  FROM ETS_WORKORDER_DIFF_DTL EWDD\n"
							+ "                 WHERE EWDD.WORKORDER_NO = EWD.WORKORDER_NO\n"
							+ "                   AND EWD.BARCODE = EWDD.BARCODE)\n"
							+ "         UNION ALL\n"
							+ "         SELECT AACL.BARCODE\n"
							// 盘点
							+ "          FROM AMS_ASSETS_CHK_LOG AACL\n"
							+ "         WHERE AACL.IS_EXIST = 'Y') C )\n";
				}
                /*if(dto.getSame().equals("Y")){
                    sqlStr+="AND EFA.ASSETS_DESCRIPTION=ESI.ITEM_NAME\n" +
                            "            AND EFA.MODEL_NUMBER=ESI.ITEM_SPEC\n" +
                            "            AND EFA.ASSETS_LOCATION=EO.WORKORDER_OBJECT_LOCATION\n" +
                            "            AND EFA.ASSETS_LOCATION_CODE=EO.LOCATION_CODE\n" +
                            "            AND EFA.ASSIGNED_TO_NAME=AME.USER_NAME \n";
                }*/
				
                if(dto.getNameSame().equals("Y")){
                    sqlStr+="AND EFA.ASSETS_DESCRIPTION = ESI.ITEM_NAME \n" ;
                }
                if(dto.getSpecSame().equals("Y")){
                    sqlStr+="AND EFA.MODEL_NUMBER = ESI.ITEM_SPEC \n" ;
                }
                if(dto.getResSame().equals("Y")){
                    sqlStr+="AND EFA.ASSIGNED_TO_NAME = AME.USER_NAME \n";
                }
                if(dto.getLocSame().equals("Y")){
                    sqlStr+="AND EFA.ASSETS_LOCATION = EO.WORKORDER_OBJECT_LOCATION \n" +
                            "AND EFA.ASSETS_LOCATION_CODE = EO.LOCATION_CODE \n" ;
                } 
				
                if(dto.getCheck().equals("Y")){
                    sqlStr+="AND EXISTS (SELECT 'A'\n" +
                            "          FROM AMS_ASSETS_CHK_LOG ACL\n" +
                            "         WHERE ACL.BARCODE = EII.BARCODE\n" +
                            "           AND ACL.IS_EXIST = 'Y') ";
                }
                sqlStr += "   AND (? ='' OR  ESI.ITEM_NAME LIKE ?)"
						+ "   AND (? ='' OR ESI.ITEM_SPEC LIKE ?)"
						+ "   AND (? ='' OR EII.BARCODE LIKE ?)"
						+ "   AND ((? ='' OR EFA.ASSIGNED_TO_NAME LIKE ?) OR (? ='' OR AME.USER_NAME LIKE ?))"
						+ "   AND (? =''OR  EFA.DEPRECIATION_ACCOUNT LIKE '%'||?||'%')\n"
						+ "   AND ((? ='' OR EO.WORKORDER_OBJECT_CODE LIKE ?)"
						+ "   OR (? ='' OR EO.WORKORDER_OBJECT_LOCATION LIKE ?))"
						+ "   ORDER BY EO.WORKORDER_OBJECT_CODE DESC,ESI.ITEM_NAME,ESI.ITEM_SPEC";

				sqlArgs.add(userAccount.getOrganizationId());
				sqlArgs.add(userAccount.getOrganizationId());
				sqlArgs.add(dto.getItemName());
				sqlArgs.add(dto.getItemName());
				sqlArgs.add(dto.getItemSpec());
				sqlArgs.add(dto.getItemSpec());
				sqlArgs.add(dto.getBarcode());
				sqlArgs.add(dto.getBarcode());
				sqlArgs.add(dto.getResponsibilityUser());
				sqlArgs.add(dto.getResponsibilityUser());
				sqlArgs.add(dto.getResponsibilityUser());
				sqlArgs.add(dto.getResponsibilityUser());
				sqlArgs.add(dto.getCostCenter());
				sqlArgs.add(dto.getCostCenter());

				sqlArgs.add(dto.getWorkorderObjectLocation());
				sqlArgs.add(dto.getWorkorderObjectLocation());
				sqlArgs.add(dto.getWorkorderObjectLocation());
				sqlArgs.add(dto.getWorkorderObjectLocation());
				sqlModel.setSqlStr(sqlStr);
				sqlModel.setArgs(sqlArgs);
			} else if (dto.getMatch().equals("Y")) {
				String sqlStr = "SELECT "
						+ " EII.BARCODE,\n"
						+ " EII.BARCODE FA_BARCODE,\n"
						+ " ESI.ITEM_NAME ITEM_DESCRIPTION,\n"
						+ " ESI.ITEM_SPEC SPEC,\n"
						+ " EFA.ASSETS_DESCRIPTION,\n"
						+ " EFA.CURRENT_UNITS,\n"
						+ " EFA.MODEL_NUMBER MIS_SPEC,\n"
						+ " EO.WORKORDER_OBJECT_CODE,\n"
						+ " EFA.ASSETS_LOCATION_CODE,\n"
						+ " EO.WORKORDER_OBJECT_LOCATION ETS_LOCATION,\n"
						+ " EFA.ASSETS_LOCATION MIS_LOCATION,\n"
						+ " EII.SYSTEMID,\n"
						+ " AME.USER_NAME,\n"
						+ " EFA.ASSIGNED_TO_NAME,\n"
						+ " EFA.ASSET_ID,\n"
						+ " EO.ORGANIZATION_ID," 
						+ " EFA.SEGMENT1||'.'||EFA.SEGMENT2||'.'||EFA.SEGMENT3 FA_CODE ,\n" 
						+ " EFA.FA_CATEGORY2,\n" 
						+ " EII.CONTENT_CODE,\n" 
						+ " EII.CONTENT_NAME\n"
						+ " FROM "
						+ " ETS_ITEM_INFO      EII,\n"
						+ " ETS_SYSTEM_ITEM    ESI,\n"
						+ " ETS_FA_ASSETS      EFA,\n"
						+ " AMS_OBJECT_ADDRESS AOA,\n"
						+ " ETS_OBJECT         EO,\n"
						+ " AMS_MIS_EMPLOYEE   AME,\n"
						+ " AMS_MIS_DEPT 	   AMD,\n"
						+ " ETS_ITEM_MATCH_REC EIM\n"
						+ " WHERE "
						+ " EIM.MATCH_TYPE = '8'\n"
						+ " AND EIM.SYSTEM_ID = EII.SYSTEMID\n"
						+ " AND ESI.ITEM_CODE = EII.ITEM_CODE\n"
						+ " AND EII.BARCODE = EFA.TAG_NUMBER\n"
						+ " AND AOA.ADDRESS_ID = EII.ADDRESS_ID\n"
						+ " AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n"
						+ " AND EII.RESPONSIBILITY_USER = AME.EMPLOYEE_ID\n"
						+ " AND AMD.DEPT_CODE = EII.RESPONSIBILITY_DEPT\n"  ;
                if(userAccount.isDptAssetsManager() && !userAccount.isComAssetsManager()){
                	DTOSet depts = userAccount.getPriviDeptCodes();
                	AmsMisDeptDTO dept = null;
                	if( null != depts ){
                		String deptCodes = "(";
                		for (int i = 0; i < depts.getSize(); i++) {
                			dept = (AmsMisDeptDTO) depts.getDTO(i);
                			deptCodes += "'" + dept.getDeptCode() + "', ";
                		}
                		deptCodes += "'')";
                		sqlStr += " AND EII.RESPONSIBILITY_DEPT IN " + deptCodes;
                	}
                }
                sqlStr+=  " AND EO.ORGANIZATION_ID = ?"
                		+ " AND EII.BARCODE LIKE dbo.NVL(?,EII.BARCODE)"
                		+ " AND (? ='' OR  ESI.ITEM_NAME LIKE ?)"
                		+ " AND (? ='' OR ESI.ITEM_SPEC LIKE ?)"
                		+ " AND ((? ='' OR EFA.ASSIGNED_TO_NAME LIKE ?) OR (? ='' OR AME.USER_NAME LIKE ?))"
                		+ " AND (? ='' OR EO.WORKORDER_OBJECT_LOCATION LIKE ?) ";
                /*if(dto.getSame().equals("Y")){
                    sqlStr+="AND EFA.ASSETS_DESCRIPTION=ESI.ITEM_NAME\n" +
                            "            AND EFA.MODEL_NUMBER=ESI.ITEM_SPEC\n" +
                            "            AND EFA.ASSETS_LOCATION=EO.WORKORDER_OBJECT_LOCATION\n" +
                            "            AND EFA.ASSETS_LOCATION_CODE=EO.LOCATION_CODE\n" +
                            "            AND EFA.ASSIGNED_TO_NAME=AME.USER_NAME";
                }*/
                
                if(dto.getNameSame().equals("Y")){
                    sqlStr+="AND EFA.ASSETS_DESCRIPTION = ESI.ITEM_NAME \n" ;
                }
                if(dto.getSpecSame().equals("Y")){
                    sqlStr+="AND EFA.MODEL_NUMBER = ESI.ITEM_SPEC \n" ;
                }
                if(dto.getResSame().equals("Y")){
                    sqlStr+="AND EFA.ASSIGNED_TO_NAME = AME.USER_NAME \n";
                }
                if(dto.getLocSame().equals("Y")){
                    sqlStr+="AND EFA.ASSETS_LOCATION = EO.WORKORDER_OBJECT_LOCATION \n" +
                            "AND EFA.ASSETS_LOCATION_CODE = EO.LOCATION_CODE \n" ;
                } 
                
                sqlArgs.add(userAccount.getOrganizationId());
				sqlArgs.add(dto.getBarcode());
				sqlArgs.add(dto.getItemName());
				sqlArgs.add(dto.getItemName());
				sqlArgs.add(dto.getItemSpec());
				sqlArgs.add(dto.getItemSpec());
				sqlArgs.add(dto.getResponsibilityUser());
				sqlArgs.add(dto.getResponsibilityUser());
				sqlArgs.add(dto.getResponsibilityUser());
				sqlArgs.add(dto.getResponsibilityUser());
				sqlArgs.add(dto.getWorkorderObjectLocation());
				sqlArgs.add(dto.getWorkorderObjectLocation());

				sqlModel.setSqlStr(sqlStr);
				sqlModel.setArgs(sqlArgs);
			}
		}
		return sqlModel;
	}
}
