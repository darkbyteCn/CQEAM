package com.sino.ams.match.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.util.StrUtil;
import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.match.dto.AmsNoMactingAssetDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: jiangtao
 * Date: 2007-11-21
 * Time: 11:55:32
 * To change this template use File | Settings | File Templates.
 */
public class AmsNoMactingAssetModel extends AMSSQLProducer {

	/**
	 * 功能：未匹配资产清单 数据库SQL构造层构造函数
	 * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsHouseInfoDTO 本次操作的数据
	 */
	public AmsNoMactingAssetModel(SfUserDTO userAccount,
			AmsNoMactingAssetDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 框架自动生成未匹配资产清单 查询 。
	 * @return SQLModel
	 */
	public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();
		AmsNoMactingAssetDTO dto = (AmsNoMactingAssetDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		if ("Y".equalsIgnoreCase(userAccount.getIsTd())) {
			String sqlStr = "SELECT"
					+ " EFA.TAG_NUMBER,"
					+ " EFA.ASSETS_DESCRIPTION,"
					+ " EFA.MODEL_NUMBER,"
					+ " EFA.ASSETS_LOCATION,"
					+ " EFA.DATE_PLACED_IN_SERVICE,"
					+ " EFA.LIFE_IN_YEARS,"
					+ " EFA.COST,"
					+ " EFA.ASSET_NUMBER,"
					+ " EFA.BOOK_TYPE_CODE,"
					+ " EFA.ASSETS_LOCATION_CODE,"
					+ " ACCV.COST_CENTER_NAME," +
                    "  EFA.FA_CATEGORY2," +
                    "  EFA.SEGMENT1||'.'||EFA.SEGMENT2||'.'||EFA.SEGMENT3 FA_CODE"
					+ " FROM"
					+ " ETS_FA_ASSETS_TD      EFA,"
					+ " AMS_COST_CENTER_V  ACCV"
					+ " WHERE"
					+ " SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) *= ACCV.COST_CENTER_CODE "
					+ " AND (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)"
					+ " AND ( ? = '' OR EFA.TAG_NUMBER LIKE ? )"
					+ " AND ( ? = '' OR EFA.ASSETS_DESCRIPTION LIKE ? )"
					+ " AND ( ? = '' OR EFA.ASSETS_LOCATION_CODE LIKE ? "
					+ " OR EFA.ASSETS_LOCATION LIKE ? )"
					+ "   AND ( ? = '' OR ACCV.COST_CENTER_CODE LIKE ?\n"
					+ "      OR ACCV.COST_CENTER_NAME LIKE ? )"

					+ " AND NOT EXISTS (" + " SELECT" + " NULL" + " FROM"
					+ " ETS_ITEM_MATCH_TD EIM" + " WHERE"
					+ " EIM.ASSET_ID = EFA.ASSET_ID)"

					+ " AND NOT EXISTS (" + " SELECT" + " NULL" + " FROM"
					+ " ETS_ITEM_MATCH_ASSIST_MIS EIMAM" + " WHERE"
					+ " EIMAM.ASSET_ID = EFA.ASSET_ID)"

					+ " AND EFA.ORGANIZATION_ID = ?" + " ORDER BY"
					+ " EFA.ASSETS_LOCATION_CODE DESC";

			sqlArgs.add(dto.getTagNumber());
			sqlArgs.add(dto.getTagNumber());
			sqlArgs.add(dto.getAssetsDescription());
			sqlArgs.add(dto.getAssetsDescription());
			
			sqlArgs.add(dto.getWorkorderObjectName());
			sqlArgs.add(dto.getWorkorderObjectName());
			sqlArgs.add(dto.getWorkorderObjectName());
			sqlArgs.add(dto.getCostCenterName());
			sqlArgs.add(dto.getCostCenterName());
			sqlArgs.add(dto.getCostCenterName());
			
			sqlArgs.add(userAccount.getOrganizationId());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
			return sqlModel;
		} else {
			return this.query1Model();
		} 

	}
	
	
	/**
	 * 框架自动生成未匹配资产清单 查询 。
	 * @return SQLModel
	 */
	public SQLModel getCountModel() {
		SQLModel sqlModel = new SQLModel();
		AmsNoMactingAssetDTO dto = (AmsNoMactingAssetDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		if ("Y".equalsIgnoreCase(userAccount.getIsTd())) {
			String sqlStr = "SELECT"
					+ " COUNT(1) "
					+ " FROM"
					+ " ETS_FA_ASSETS_TD      EFA,"
					+ " AMS_COST_CENTER_V  ACCV"
					+ " WHERE"
					+ " SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) *= ACCV.COST_CENTER_CODE "
					+ " AND (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)" +
                    "  AND ACCV.ORGANIZATION_ID = EFA.ORGANIZATION_ID "
					+ " AND ( ? = '' OR EFA.TAG_NUMBER LIKE ? )"
					+ " AND ( ? = '' OR EFA.ASSETS_DESCRIPTION LIKE ? )"
					+ " AND ( ? = '' OR EFA.ASSETS_LOCATION_CODE LIKE ? "
					+ " OR EFA.ASSETS_LOCATION LIKE ? )"
					+ "   AND ( ? = '' OR ACCV.COST_CENTER_CODE LIKE ?\n"
					+ "      OR ACCV.COST_CENTER_NAME LIKE ? )"

					+ " AND NOT EXISTS (" + " SELECT" + " NULL" + " FROM"
					+ " ETS_ITEM_MATCH_TD EIM" + " WHERE"
					+ " EIM.ASSET_ID = EFA.ASSET_ID)"

					+ " AND NOT EXISTS (" + " SELECT" + " NULL" + " FROM"
					+ " ETS_ITEM_MATCH_ASSIST_MIS EIMAM" + " WHERE"
					+ " EIMAM.ASSET_ID = EFA.ASSET_ID)"

					+ " AND EFA.ORGANIZATION_ID = ?" + " ORDER BY"
					+ " EFA.ASSETS_LOCATION_CODE DESC";

			sqlArgs.add(dto.getTagNumber());
			sqlArgs.add(dto.getTagNumber());
			sqlArgs.add(dto.getAssetsDescription());
			sqlArgs.add(dto.getAssetsDescription());
			
			sqlArgs.add(dto.getWorkorderObjectName());
			sqlArgs.add(dto.getWorkorderObjectName());
			sqlArgs.add(dto.getWorkorderObjectName());
			sqlArgs.add(dto.getCostCenterName());
			sqlArgs.add(dto.getCostCenterName());
			sqlArgs.add(dto.getCostCenterName());
			
			sqlArgs.add(userAccount.getOrganizationId());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
			return sqlModel;
		} else {
			return this.query1CountModel();
		} 

	}
	
	private SQLModel query2Model(){
		SQLModel sqlModel = new SQLModel();
		AmsNoMactingAssetDTO dto = (AmsNoMactingAssetDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
		+ " EFA.TAG_NUMBER,"
		+ " EFA.ASSETS_DESCRIPTION,"
		+ " EFA.MODEL_NUMBER,"
		+ " EFA.ASSETS_LOCATION,"
		+ " EFA.DATE_PLACED_IN_SERVICE,"
		+ " EFA.LIFE_IN_YEARS,"
		+ " EFA.COST,"
		+ " EFA.ASSET_NUMBER,"
		+ " EFA.BOOK_TYPE_CODE,"
		+ " EFA.ASSETS_LOCATION_CODE,"
		+ " ACCV.COST_CENTER_NAME," +
        "   EFA.SEGMENT1||'.'||EFA.SEGMENT2||'.'||EFA.SEGMENT3 FA_CODE," +
        "  EFA.FA_CATEGORY2 "
		+ " FROM"
		+ " ETS_FA_ASSETS      EFA,"
		+ " AMS_COST_CENTER_V  ACCV"
		+ " WHERE"
		+ " SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) *= ACCV.COST_CENTER_CODE "
		+ " AND (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)" +
        "   AND ACCV.ORGANIZATION_ID=EFA.ORGANIZATION_ID"
		
		+ " AND ( ? = '' OR EFA.TAG_NUMBER LIKE ? )"
		+ " AND ( ? = '' OR EFA.ASSETS_DESCRIPTION LIKE ? )"
		+ " AND ( ? = '' OR EFA.ASSETS_LOCATION_CODE LIKE ? "
		+ " OR EFA.ASSETS_LOCATION LIKE ? )"
		+ "   AND ( ? = '' OR ACCV.COST_CENTER_CODE LIKE ?\n"
		+ "      OR ACCV.COST_CENTER_NAME LIKE ? )"
		
//		+ " AND EFA.TAG_NUMBER LIKE NVL(?, EFA.TAG_NUMBER)"
//		+ " AND EFA.ASSETS_DESCRIPTION LIKE NVL(?, EFA.ASSETS_DESCRIPTION)"
//		+ " AND (EFA.ASSETS_LOCATION_CODE LIKE NVL(?, EFA.ASSETS_LOCATION_CODE)"
//		+ " OR EFA.ASSETS_LOCATION LIKE NVL(?, EFA.ASSETS_LOCATION))"
//		+ "   AND ((? IS NULL OR ACCV.COST_CENTER_CODE LIKE ?) OR\n"
//		+ "        (? IS NULL OR ACCV.COST_CENTER_NAME LIKE ?))"

		+ " AND NOT EXISTS (" + " SELECT" + " NULL" + " FROM"
		+ " ETS_ITEM_MATCH EIM" + " WHERE"
		+ " EIM.ASSET_ID = EFA.ASSET_ID)"

		+ " AND NOT EXISTS (" + " SELECT" + " NULL" + " FROM"
		+ " ETS_ITEM_MATCH_ASSIST_MIS EIMAM" + " WHERE"
		+ " EIMAM.ASSET_ID = EFA.ASSET_ID)"

		+ " AND EFA.ORGANIZATION_ID = ?" ;
		
		sqlArgs.add(dto.getTagNumber());
		sqlArgs.add(dto.getTagNumber());
		sqlArgs.add(dto.getAssetsDescription());
		sqlArgs.add(dto.getAssetsDescription());
		
		sqlArgs.add(dto.getWorkorderObjectName());
		sqlArgs.add(dto.getWorkorderObjectName());
		sqlArgs.add(dto.getWorkorderObjectName());
		sqlArgs.add(dto.getCostCenterName());
		sqlArgs.add(dto.getCostCenterName());
		sqlArgs.add(dto.getCostCenterName());
		
		sqlModel.setSqlStr( sqlStr );
		sqlModel.setArgs(sqlArgs);
		
		return sqlModel;
	}
	
	private SQLModel query1Model(){ 
		SQLModel sqlModel = new SQLModel();
		AmsNoMactingAssetDTO dto = (AmsNoMactingAssetDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		
		StringBuffer sb = new StringBuffer();
		String tagNumber = dto.getTagNumber();
		String desc = dto.getAssetsDescription();
		String objectName = dto.getWorkorderObjectName();
		String cost = dto.getCostCenterName();
		 
		sb.append( " SELECT \n " );
		sb.append( " 	EFA.TAG_NUMBER, \n " );
		sb.append( " 	EFA.ASSETS_DESCRIPTION, \n " );
		sb.append( " 	EFA.MODEL_NUMBER, \n " );
		sb.append( " 	EFA.ASSETS_LOCATION, \n " );
		sb.append( " 	EFA.DATE_PLACED_IN_SERVICE, \n " );
		sb.append( " 	EFA.LIFE_IN_YEARS, \n " );
		sb.append( " 	EFA.COST, \n " );
		sb.append( " 	EFA.ASSET_NUMBER, \n " );
		sb.append( " 	EFA.BOOK_TYPE_CODE, \n " );
		sb.append( " 	EFA.ASSETS_LOCATION_CODE, \n " );
		sb.append( " 	ACCV.COST_CENTER_NAME, \n " );
		sb.append( " 	EFA.SEGMENT1||'.'||EFA.SEGMENT2||'.'||EFA.SEGMENT3 FA_CODE, \n " );
		sb.append( " 	EFA.FA_CATEGORY2 \n " ); 
		sb.append( " FROM \n " );
		sb.append( " 	ETS_FA_ASSETS EFA (INDEX ETS_FA_ASSETS_110708_N5), \n " );
		sb.append( " 	AMS_COST_CENTER_V ACCV (INDEX ETS_COUNTRY_ORGID) \n " );
		sb.append( " WHERE \n " );
		sb.append( " SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) = ACCV.COST_CENTER_CODE  \n " );
		sb.append( " AND (EFA.IS_RETIREMENTS = 0  \n " );
		sb.append( " OR EFA.IS_RETIREMENTS = 2)  \n " );
		sb.append( " AND ACCV.ORGANIZATION_ID = EFA.ORGANIZATION_ID  \n " );
		
		if( !StrUtil.isEmpty( tagNumber ) ){
			sb.append( " AND EFA.TAG_NUMBER LIKE ? \n " );
		}
		
		if( !StrUtil.isEmpty( desc ) ){
			sb.append( " AND EFA.ASSETS_DESCRIPTION LIKE ? \n " );
		}
		
		if( !StrUtil.isEmpty( objectName ) ){
			sb.append( " AND ( EFA.ASSETS_LOCATION_CODE LIKE ? OR EFA.ASSETS_LOCATION LIKE ? ) \n " );
		}
		
		if( !StrUtil.isEmpty( cost ) ){
			sb.append( " AND ( ACCV.COST_CENTER_CODE LIKE ? OR ACCV.COST_CENTER_NAME LIKE ? ) \n " );
		}
//		sb.append( " AND ( ? = ''  \n " );
//		sb.append( " OR EFA.TAG_NUMBER LIKE ? )  \n " );
//		sb.append( " AND ( ? = ''  \n " );
//		sb.append( " OR EFA.ASSETS_DESCRIPTION LIKE ? )  \n " );
//		sb.append( " AND ( ? = ''  \n " );
//		sb.append( " OR EFA.ASSETS_LOCATION_CODE LIKE ?  \n " );
//		sb.append( " OR EFA.ASSETS_LOCATION LIKE ? )  \n " );
//		sb.append( " AND ( ? = ''  \n " );
//		sb.append( " OR ACCV.COST_CENTER_CODE LIKE ?  \n " );
//		sb.append( " OR ACCV.COST_CENTER_NAME LIKE ? )  \n " );
		sb.append( " AND NOT EXISTS (SELECT \n " );
		sb.append( " 					NULL  \n " );
		sb.append( " 				FROM \n " );
		sb.append( " 					ETS_ITEM_MATCH EIM  (INDEX ETS_ITEM_MATCH_110708_I2) \n " );
		sb.append( " 				WHERE \n " );
		sb.append( " 					EIM.ASSET_ID = EFA.ASSET_ID)  \n " );
		sb.append( " AND NOT EXISTS (SELECT \n " );
		sb.append( "                     NULL  \n " );
		sb.append( "                 FROM \n " );
		sb.append( "                     ETS_ITEM_MATCH_ASSIST_MIS EIMAM (INDEX ETS_ITEM_MATCH_ASSIST_MIS_PK) \n " );
		sb.append( "                 WHERE \n " );
		sb.append( "                     EIMAM.ASSET_ID = EFA.ASSET_ID) \n " ); 
		sb.append( "                     AND EFA.ORGANIZATION_ID = ?  \n " );
		
		if( StrUtil.isEmpty( cost ) ){
			sb.append( " UNION ALL \n " );
			sb.append( " SELECT \n " );
			sb.append( " 	EFA.TAG_NUMBER, \n " );
			sb.append( " 	EFA.ASSETS_DESCRIPTION, \n " );
			sb.append( " 	EFA.MODEL_NUMBER, \n " );
			sb.append( " 	EFA.ASSETS_LOCATION, \n " );
			sb.append( " 	EFA.DATE_PLACED_IN_SERVICE, \n " );
			sb.append( " 	EFA.LIFE_IN_YEARS, \n " );
			sb.append( " 	EFA.COST, \n " );
			sb.append( " 	EFA.ASSET_NUMBER, \n " );
			sb.append( " 	EFA.BOOK_TYPE_CODE, \n " );
			sb.append( " 	EFA.ASSETS_LOCATION_CODE, \n " );
			sb.append( " 	'' COST_CENTER_NAME, \n " );
			sb.append( " 	EFA.SEGMENT1||'.'||EFA.SEGMENT2||'.'||EFA.SEGMENT3 FA_CODE, \n " );
			sb.append( " 	EFA.FA_CATEGORY2 \n " ); 
			sb.append( " FROM \n " ); 
			sb.append( " 	ETS_FA_ASSETS EFA (INDEX ETS_FA_ASSETS_110708_N5) \n " ); 
			sb.append( " WHERE \n " ); 
			sb.append( " (EFA.IS_RETIREMENTS = 0  \n " );
			sb.append( " OR EFA.IS_RETIREMENTS = 2)  \n " );
			sb.append( " AND NOT EXISTS( SELECT NULL FROM AMS_COST_CENTER_V ACCV (INDEX ETS_COUNTRY_ORGID) WHERE  \n " );
			sb.append( " 	    ACCV.ORGANIZATION_ID = EFA.ORGANIZATION_ID AND SUBSTRING(EFA.DEPRECIATION_ACCOUNT,6,6) = ACCV.COST_CENTER_CODE ) \n " );
			
			if( !StrUtil.isEmpty( tagNumber ) ){
				sb.append( " AND EFA.TAG_NUMBER LIKE ? \n " );
			}
			
			if( !StrUtil.isEmpty( desc ) ){
				sb.append( " AND EFA.ASSETS_DESCRIPTION LIKE ? \n " );
			}
			
			if( !StrUtil.isEmpty( objectName ) ){
				sb.append( " AND ( EFA.ASSETS_LOCATION_CODE LIKE ? OR EFA.ASSETS_LOCATION LIKE ? ) \n " );
			}
			
			sb.append( " AND NOT EXISTS (SELECT \n " );
			sb.append( " 					NULL  \n " );
			sb.append( " 				FROM \n " );
			sb.append( " 					ETS_ITEM_MATCH EIM (INDEX ETS_ITEM_MATCH_110708_I2) \n " );
			sb.append( " 				WHERE \n " );
			sb.append( " 					EIM.ASSET_ID = EFA.ASSET_ID)  \n " );
			sb.append( " AND NOT EXISTS (SELECT \n " );
			sb.append( "                     NULL  \n " );
			sb.append( "                 FROM \n " );
			sb.append( "                     ETS_ITEM_MATCH_ASSIST_MIS EIMAM (INDEX ETS_ITEM_MATCH_ASSIST_MIS_PK) \n " );
			sb.append( "                 WHERE \n " );
			sb.append( "                     EIMAM.ASSET_ID = EFA.ASSET_ID) \n " ); 
			sb.append( "                     AND EFA.ORGANIZATION_ID = ?  \n " ); 
		}
	 
		
//		sb.append( " AND ( ? = ''  \n " );
//		sb.append( " OR EFA.TAG_NUMBER LIKE ? )  \n " );
//		sb.append( " AND ( ? = ''  \n " );
//		sb.append( " OR EFA.ASSETS_DESCRIPTION LIKE ? )  \n " );
//		sb.append( " AND ( ? = ''  \n " );
//		sb.append( " OR EFA.ASSETS_LOCATION_CODE LIKE ?  \n " );
//		sb.append( " OR EFA.ASSETS_LOCATION LIKE ? )  \n " );
//		sb.append( " AND ( ? = ''  \n " );
//		sb.append( " OR 1 <> 1 ) \n " );
		
		if( !StrUtil.isEmpty( tagNumber ) ){
			sqlArgs.add(dto.getTagNumber());
		} 
		
		if( !StrUtil.isEmpty( desc ) ){
			sqlArgs.add(dto.getAssetsDescription());
		}
		
		if( !StrUtil.isEmpty( objectName ) ){
			sqlArgs.add(dto.getWorkorderObjectName());
			sqlArgs.add(dto.getWorkorderObjectName());
		}
		
		if( !StrUtil.isEmpty( cost ) ){
			sqlArgs.add(dto.getCostCenterName());
			sqlArgs.add(dto.getCostCenterName());
		}
		sqlArgs.add(userAccount.getOrganizationId());
		
		
		//UNION ALL 
		if( StrUtil.isEmpty( cost ) ){
			if( !StrUtil.isEmpty( tagNumber ) ){
				sqlArgs.add(dto.getTagNumber());
			} 
			
			if( !StrUtil.isEmpty( desc ) ){
				sqlArgs.add(dto.getAssetsDescription());
			}
			
			if( !StrUtil.isEmpty( objectName ) ){
				sqlArgs.add(dto.getWorkorderObjectName());
				sqlArgs.add(dto.getWorkorderObjectName());
			}
			
			sqlArgs.add(userAccount.getOrganizationId());
		} 	

		sqlModel.setSqlStr( sb.toString() );
		sqlModel.setArgs(sqlArgs);
		
		return sqlModel;
	}

	
	/**
	 * 取总数
	 * @return
	 */
	private SQLModel query1CountModel(){ 
		SQLModel sqlModel = new SQLModel();
		AmsNoMactingAssetDTO dto = (AmsNoMactingAssetDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		
		StringBuffer sb = new StringBuffer();
		String tagNumber = dto.getTagNumber();
		String desc = dto.getAssetsDescription();
		String objectName = dto.getWorkorderObjectName();
		String cost = dto.getCostCenterName();
		 
		sb.append( " SELECT COUNT(1) FROM ( \n " );
		
		sb.append( " SELECT \n " );
		sb.append( " 	1 \n " ); 
		sb.append( " FROM \n " );
		sb.append( " 	ETS_FA_ASSETS EFA, \n " );
		sb.append( " 	AMS_COST_CENTER_V ACCV  \n " );
		sb.append( " WHERE \n " );
		sb.append( " SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) = ACCV.COST_CENTER_CODE  \n " );
		sb.append( " AND (EFA.IS_RETIREMENTS = 0  \n " );
		sb.append( " OR EFA.IS_RETIREMENTS = 2)  \n " );
		sb.append( " AND ACCV.ORGANIZATION_ID = EFA.ORGANIZATION_ID  \n " );
		
		if( !StrUtil.isEmpty( tagNumber ) ){
			sb.append( " AND EFA.TAG_NUMBER LIKE ? \n " );
		}
		
		if( !StrUtil.isEmpty( desc ) ){
			sb.append( " AND EFA.ASSETS_DESCRIPTION LIKE ? \n " );
		}
		
		if( !StrUtil.isEmpty( objectName ) ){
			sb.append( " AND ( EFA.ASSETS_LOCATION_CODE LIKE ? OR EFA.ASSETS_LOCATION LIKE ? ) \n " );
		}
		
		if( !StrUtil.isEmpty( cost ) ){
			sb.append( " AND ( ACCV.COST_CENTER_CODE LIKE ? OR ACCV.COST_CENTER_NAME LIKE ? ) \n " );
		}
//		sb.append( " AND ( ? = ''  \n " );
//		sb.append( " OR EFA.TAG_NUMBER LIKE ? )  \n " );
//		sb.append( " AND ( ? = ''  \n " );
//		sb.append( " OR EFA.ASSETS_DESCRIPTION LIKE ? )  \n " );
//		sb.append( " AND ( ? = ''  \n " );
//		sb.append( " OR EFA.ASSETS_LOCATION_CODE LIKE ?  \n " );
//		sb.append( " OR EFA.ASSETS_LOCATION LIKE ? )  \n " );
//		sb.append( " AND ( ? = ''  \n " );
//		sb.append( " OR ACCV.COST_CENTER_CODE LIKE ?  \n " );
//		sb.append( " OR ACCV.COST_CENTER_NAME LIKE ? )  \n " );
		sb.append( " AND NOT EXISTS (SELECT \n " );
		sb.append( " 					NULL  \n " );
		sb.append( " 				FROM \n " );
		sb.append( " 					ETS_ITEM_MATCH EIM  \n " );
		sb.append( " 				WHERE \n " );
		sb.append( " 					EIM.ASSET_ID = EFA.ASSET_ID)  \n " );
		sb.append( " AND NOT EXISTS (SELECT \n " );
		sb.append( "                     NULL  \n " );
		sb.append( "                 FROM \n " );
		sb.append( "                     ETS_ITEM_MATCH_ASSIST_MIS EIMAM  \n " );
		sb.append( "                 WHERE \n " );
		sb.append( "                     EIMAM.ASSET_ID = EFA.ASSET_ID) \n " ); 
		sb.append( "                     AND EFA.ORGANIZATION_ID = ?  \n " );
		
		if( StrUtil.isEmpty( cost ) ){
			sb.append( " UNION ALL \n " );
			sb.append( " SELECT \n " );
			sb.append( " 	1 \n " ); 
			sb.append( " FROM \n " ); 
			sb.append( " 	ETS_FA_ASSETS EFA  \n " ); 
			sb.append( " WHERE \n " ); 
			sb.append( " (EFA.IS_RETIREMENTS = 0  \n " );
			sb.append( " OR EFA.IS_RETIREMENTS = 2)  \n " );
			sb.append( " AND NOT EXISTS( SELECT NULL FROM AMS_COST_CENTER_V ACCV  WHERE  \n " );
			sb.append( " 	    ACCV.ORGANIZATION_ID = EFA.ORGANIZATION_ID AND SUBSTRING(EFA.DEPRECIATION_ACCOUNT,6,6) = ACCV.COST_CENTER_CODE ) \n " );
			
			if( !StrUtil.isEmpty( tagNumber ) ){
				sb.append( " AND EFA.TAG_NUMBER LIKE ? \n " );
			}
			
			if( !StrUtil.isEmpty( desc ) ){
				sb.append( " AND EFA.ASSETS_DESCRIPTION LIKE ? \n " );
			}
			
			if( !StrUtil.isEmpty( objectName ) ){
				sb.append( " AND ( EFA.ASSETS_LOCATION_CODE LIKE ? OR EFA.ASSETS_LOCATION LIKE ? ) \n " );
			}
			
			sb.append( " AND NOT EXISTS (SELECT \n " );
			sb.append( " 					NULL  \n " );
			sb.append( " 				FROM \n " );
			sb.append( " 					ETS_ITEM_MATCH EIM  \n " );
			sb.append( " 				WHERE \n " );
			sb.append( " 					EIM.ASSET_ID = EFA.ASSET_ID)  \n " );
			sb.append( " AND NOT EXISTS (SELECT \n " );
			sb.append( "                     NULL  \n " );
			sb.append( "                 FROM \n " );
			sb.append( "                     ETS_ITEM_MATCH_ASSIST_MIS EIMAM  \n " );
			sb.append( "                 WHERE \n " );
			sb.append( "                     EIMAM.ASSET_ID = EFA.ASSET_ID) \n " ); 
			sb.append( "                     AND EFA.ORGANIZATION_ID = ?  \n " ); 
		}
	 
		
//		sb.append( " AND ( ? = ''  \n " );
//		sb.append( " OR EFA.TAG_NUMBER LIKE ? )  \n " );
//		sb.append( " AND ( ? = ''  \n " );
//		sb.append( " OR EFA.ASSETS_DESCRIPTION LIKE ? )  \n " );
//		sb.append( " AND ( ? = ''  \n " );
//		sb.append( " OR EFA.ASSETS_LOCATION_CODE LIKE ?  \n " );
//		sb.append( " OR EFA.ASSETS_LOCATION LIKE ? )  \n " );
//		sb.append( " AND ( ? = ''  \n " );
//		sb.append( " OR 1 <> 1 ) \n " );
		
		if( !StrUtil.isEmpty( tagNumber ) ){
			sqlArgs.add(dto.getTagNumber());
		} 
		
		if( !StrUtil.isEmpty( desc ) ){
			sqlArgs.add(dto.getAssetsDescription());
		}
		
		if( !StrUtil.isEmpty( objectName ) ){
			sqlArgs.add(dto.getWorkorderObjectName());
			sqlArgs.add(dto.getWorkorderObjectName());
		}
		
		if( !StrUtil.isEmpty( cost ) ){
			sqlArgs.add(dto.getCostCenterName());
			sqlArgs.add(dto.getCostCenterName());
		}
		sqlArgs.add(userAccount.getOrganizationId());
		
		
		//UNION ALL 
		if( StrUtil.isEmpty( cost ) ){
			if( !StrUtil.isEmpty( tagNumber ) ){
				sqlArgs.add(dto.getTagNumber());
			} 
			
			if( !StrUtil.isEmpty( desc ) ){
				sqlArgs.add(dto.getAssetsDescription());
			}
			
			if( !StrUtil.isEmpty( objectName ) ){
				sqlArgs.add(dto.getWorkorderObjectName());
				sqlArgs.add(dto.getWorkorderObjectName());
			}
			
			sqlArgs.add(userAccount.getOrganizationId());
		} 	
		
		sb.append( " ) tmp " );

		sqlModel.setSqlStr( sb.toString() );
		sqlModel.setArgs(sqlArgs);
		
		return sqlModel;
	}
	
}
